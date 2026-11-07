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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.UrbanPlanningArea;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class UrbanPlanningAreaExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final PreparedStatement ps;

    public UrbanPlanningAreaExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.URBANPLANNINGAREA);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);

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
    }

    public void doExport(UrbanPlanningArea urbanPlanningArea, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(urbanPlanningArea, objectId, "uf_", rs);
                zoneExporter.doExport(urbanPlanningArea, objectId, "z_", rs);
                doExport(urbanPlanningArea, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), urbanPlanningArea, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("areaclassification", prefix + "areaclassification"),
                table.getColumn("areaclassification_codespace", prefix + "areaclassification_codespace"),
                table.getColumn("reasonforareaclassification", prefix + "reasonforareaclassification"),
                table.getColumn("policyforareaclassification", prefix + "policyforareaclassification"),
                table.getColumn("purposeforurbanplan", prefix + "purposeforurbanplan"),
                table.getColumn("policyforurbanplandecision", prefix + "policyforurbanplandecision"),
                table.getColumn("population", prefix + "population"),
                table.getColumn("cityarea", prefix + "cityarea"),
                table.getColumn("cityarea_uom", prefix + "cityarea_uom"),
                table.getColumn("citypopulation", prefix + "citypopulation"));

        return select;
    }

    public void doExport(UrbanPlanningArea urbanPlanningArea, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String areaClassification = rs.getString(prefix + "areaclassification");
        if (!rs.wasNull()) {
            Code code = new Code(areaClassification);
            code.setCodeSpace(rs.getString(prefix + "areaclassification_codespace"));
            urbanPlanningArea.setAreaClassification(code);
        }

        String reasonForAreaClassification = rs.getString(prefix + "reasonforareaclassification");
        if (!rs.wasNull())
            urbanPlanningArea.setReasonForAreaClassification(new StringOrRef(reasonForAreaClassification));

        String policyForAreaClassification = rs.getString(prefix + "policyforareaclassification");
        if (!rs.wasNull())
            urbanPlanningArea.setPolicyForAreaClassification(new StringOrRef(policyForAreaClassification));

        String purposeForUrbanPlan = rs.getString(prefix + "purposeforurbanplan");
        if (!rs.wasNull())
            urbanPlanningArea.setPurposeForUrbanPlan(new StringOrRef(purposeForUrbanPlan));

        String policyForUrbanPlanDecision = rs.getString(prefix + "policyforurbanplandecision");
        if (!rs.wasNull())
            urbanPlanningArea.setPolicyForUrbanPlanDecision(new StringOrRef(policyForUrbanPlanDecision));

        int population = rs.getInt(prefix + "population");
        if (!rs.wasNull())
            urbanPlanningArea.setPopulation(population);

        double cityArea = rs.getDouble(prefix + "cityarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(cityArea);
            measure.setUom(rs.getString(prefix + "cityarea_uom"));
            urbanPlanningArea.setCityArea(measure);
        }

        int cityPopulation = rs.getInt(prefix + "citypopulation");
        if (!rs.wasNull())
            urbanPlanningArea.setCityPopulation(cityPopulation);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
