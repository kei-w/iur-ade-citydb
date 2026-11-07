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
import org.citygml4j.ade.iur.model.urf.GlobalHubCityDevelopmentProjectProperty;
import org.citygml4j.ade.iur.model.urf.SpecifiedUrgentUrbanRenewalArea;

import java.sql.*;

public class SpecifiedUrgentUrbanRenewalAreaExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final UrgentUrbanRenewalAreaExporter urgentUrbanRenewalAreaExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psDevelopmentProject;
    private final AttributeValueSplitter valueSplitter;

    public SpecifiedUrgentUrbanRenewalAreaExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.SPECIFIEDURGENTURBANRE);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        urgentUrbanRenewalAreaExporter = manager.getExporter(UrgentUrbanRenewalAreaExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Table zone = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE)));
        Table urgentUrbanRenewalArea = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URGENTURBANRENEWALAREA)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(zone, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(urgentUrbanRenewalArea, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        zoneExporter.addProjection(select, zone, "z_");
        urgentUrbanRenewalAreaExporter.addProjection(select, urgentUrbanRenewalArea, "uura_");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table developmentProjects = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.SPECIFIED_TO_GLOBALHUB)));
        Select developmentProjectSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, developmentProjects.getColumn("globalhubcitydevelopme_id")))
                .addSelection(ComparisonFactory.equalTo(developmentProjects.getColumn("specifiedurgenturbanre_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psDevelopmentProject = connection.prepareStatement(developmentProjectSelect.toString());
    }

    public void doExport(SpecifiedUrgentUrbanRenewalArea specifiedUrgentUrbanRenewalArea, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(specifiedUrgentUrbanRenewalArea, objectId, "uf_", rs);
                zoneExporter.doExport(specifiedUrgentUrbanRenewalArea, objectId, "z_", rs);
                urgentUrbanRenewalAreaExporter.doExport(specifiedUrgentUrbanRenewalArea, objectId, "uura_", rs);
                doExport(specifiedUrgentUrbanRenewalArea, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), specifiedUrgentUrbanRenewalArea, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        return select;
    }

    public void doExport(SpecifiedUrgentUrbanRenewalArea specifiedUrgentUrbanRenewalArea, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        psDevelopmentProject.setLong(1, objectId);
        try (ResultSet developmentProjectRs = psDevelopmentProject.executeQuery()) {
            while (developmentProjectRs.next()) {
                GlobalHubCityDevelopmentProjectProperty developmentProjectProperty = new GlobalHubCityDevelopmentProjectProperty();
                developmentProjectProperty.setHref("#" + developmentProjectRs.getString("gmlid"));
                specifiedUrgentUrbanRenewalArea.getDevelopmentProject().add(developmentProjectProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psDevelopmentProject.close();
    }
}
