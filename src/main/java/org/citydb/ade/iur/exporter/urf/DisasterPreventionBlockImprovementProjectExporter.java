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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.DisasterPreventionBlockImprovementProject;
import org.citygml4j.model.gml.base.StringOrRef;

import java.sql.*;

public class DisasterPreventionBlockImprovementProjectExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final UrbanDevelopmentProjectExporter urbanDevelopmentProjectExporter;
    private final PreparedStatement ps;

    public DisasterPreventionBlockImprovementProjectExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DISASTERPREVENTIONBLOC);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        urbanDevelopmentProjectExporter = manager.getExporter(UrbanDevelopmentProjectExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Table zone = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE)));
        Table urbanDevelopmentProject = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANDEVELOPMENTPROJEC)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(zone, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(urbanDevelopmentProject, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        zoneExporter.addProjection(select, zone, "z_");
        urbanDevelopmentProjectExporter.addProjection(select, urbanDevelopmentProject, "udp_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(DisasterPreventionBlockImprovementProject disasterPreventionBlockImprovementProject, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(disasterPreventionBlockImprovementProject, objectId, "uf_", rs);
                zoneExporter.doExport(disasterPreventionBlockImprovementProject, objectId, "z_", rs);
                urbanDevelopmentProjectExporter.doExport(disasterPreventionBlockImprovementProject, objectId, "udp_", rs);
                doExport(disasterPreventionBlockImprovementProject, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("disasterpreventionpublicfaci", prefix + "disasterpreventionpublicfaci"),
                table.getColumn("otherpublicfacilityallocatio", prefix + "otherpublicfacilityallocatio"),
                table.getColumn("developmentplan", prefix + "developmentplan"));

        return select;
    }

    public void doExport(DisasterPreventionBlockImprovementProject disasterPreventionBlockImprovementProject, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String disasterPreventionPublicFacilityAllocation = rs.getString(prefix + "disasterpreventionpublicfaci");
        if (!rs.wasNull())
            disasterPreventionBlockImprovementProject.setDisasterPreventionPublicFacilityAllocation(new StringOrRef(disasterPreventionPublicFacilityAllocation));

        String otherPublicFacilityAllocation = rs.getString(prefix + "otherpublicfacilityallocatio");
        if (!rs.wasNull())
            disasterPreventionBlockImprovementProject.setOtherPublicFacilityAllocation(new StringOrRef(otherPublicFacilityAllocation));

        String developmentPlan = rs.getString(prefix + "developmentplan");
        if (!rs.wasNull())
            disasterPreventionBlockImprovementProject.setDevelopmentPlan(new StringOrRef(developmentPlan));
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
