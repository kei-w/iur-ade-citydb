/*
 * 3D City Database - The Open Source CityGML Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2013 - 2024
 * Chair of Geoinformatics
 * Technical University of Munich, Germany
 * https://www.lrg.tum.de/gis/
 *
 * The 3D City Database is jointly developed with the following
 * cooperation partners:
 *
 * Virtual City Systems, Berlin <https://vc.systems/>
 * M.O.S.S. Computer Grafik Systeme GmbH, Taufkirchen <http://www.moss.de/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.citydb.ade.iur.exporter.urf;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.PrivateUrbanRenewalProjectPlanProperty;
import org.citygml4j.ade.iur.model.urf.SpecialUrbanRenaissanceDistrictProperty;
import org.citygml4j.ade.iur.model.urf.SpecifiedUrgentUrbanRenewalAreaProperty;
import org.citygml4j.ade.iur.model.urf.UrgentUrbanRenewalArea;
import org.citygml4j.model.gml.base.StringOrRef;

import java.sql.*;

public class UrgentUrbanRenewalAreaExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psPrivateProject;
    private final PreparedStatement psSpecifiedArea;
    private final PreparedStatement psSpecialDistrict;
    private final AttributeValueSplitter valueSplitter;

    public UrgentUrbanRenewalAreaExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.URGENTURBANRENEWALAREA);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Table zone = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(zone, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        zoneExporter.addProjection(select, zone, "z_");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table privateProjects = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URGENTURB_TO_PRIVATEUR)));
        Select privateProjectSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, privateProjects.getColumn("privateurbanrenewalpro_id")))
                .addSelection(ComparisonFactory.equalTo(privateProjects.getColumn("urgenturbanrenewalarea_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psPrivateProject = connection.prepareStatement(privateProjectSelect.toString());
        Table specifiedAreas = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URGENTURB_TO_SPECIFIED)));
        Select specifiedAreaSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, specifiedAreas.getColumn("specifiedurgenturbanre_id")))
                .addSelection(ComparisonFactory.equalTo(specifiedAreas.getColumn("urgenturbanrenewalarea_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psSpecifiedArea = connection.prepareStatement(specifiedAreaSelect.toString());
        Table specialDistricts = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URGENTURB_TO_SPECIALUR)));
        Select specialDistrictSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, specialDistricts.getColumn("specialurbanrenaissanc_id")))
                .addSelection(ComparisonFactory.equalTo(specialDistricts.getColumn("urgenturbanrenewalarea_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psSpecialDistrict = connection.prepareStatement(specialDistrictSelect.toString());
    }

    public void doExport(UrgentUrbanRenewalArea urgentUrbanRenewalArea, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(urgentUrbanRenewalArea, objectId, "uf_", rs);
                zoneExporter.doExport(urgentUrbanRenewalArea, objectId, "z_", rs);
                doExport(urgentUrbanRenewalArea, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), urgentUrbanRenewalArea, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("developmentpolicy", prefix + "developmentpolicy"));

        return select;
    }

    public void doExport(UrgentUrbanRenewalArea urgentUrbanRenewalArea, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String developmentPolicy = rs.getString(prefix + "developmentpolicy");
        if (!rs.wasNull())
            urgentUrbanRenewalArea.setDevelopmentPolicy(new StringOrRef(developmentPolicy));

        psPrivateProject.setLong(1, objectId);
        try (ResultSet privateProjectRs = psPrivateProject.executeQuery()) {
            while (privateProjectRs.next()) {
                PrivateUrbanRenewalProjectPlanProperty privateProjectProperty = new PrivateUrbanRenewalProjectPlanProperty();
                privateProjectProperty.setHref("#" + privateProjectRs.getString("gmlid"));
                urgentUrbanRenewalArea.getPrivateProject().add(privateProjectProperty);
            }
        }

        psSpecifiedArea.setLong(1, objectId);
        try (ResultSet specifiedAreaRs = psSpecifiedArea.executeQuery()) {
            while (specifiedAreaRs.next()) {
                SpecifiedUrgentUrbanRenewalAreaProperty specifiedAreaProperty = new SpecifiedUrgentUrbanRenewalAreaProperty();
                specifiedAreaProperty.setHref("#" + specifiedAreaRs.getString("gmlid"));
                urgentUrbanRenewalArea.getSpecifiedArea().add(specifiedAreaProperty);
            }
        }

        psSpecialDistrict.setLong(1, objectId);
        try (ResultSet specialDistrictRs = psSpecialDistrict.executeQuery()) {
            while (specialDistrictRs.next()) {
                SpecialUrbanRenaissanceDistrictProperty specialDistrictProperty = new SpecialUrbanRenaissanceDistrictProperty();
                specialDistrictProperty.setHref("#" + specialDistrictRs.getString("gmlid"));
                urgentUrbanRenewalArea.getSpecialDistrict().add(specialDistrictProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psPrivateProject.close();
        psSpecifiedArea.close();
        psSpecialDistrict.close();
    }
}
