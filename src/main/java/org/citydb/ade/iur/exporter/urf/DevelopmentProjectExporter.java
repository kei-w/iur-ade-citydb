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
import org.citygml4j.ade.iur.model.urf.DevelopmentProject;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class DevelopmentProjectExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final PreparedStatement ps;

    public DevelopmentProjectExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DEVELOPMENTPROJECT);
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

    public void doExport(DevelopmentProject developmentProject, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(developmentProject, objectId, "uf_", rs);
                zoneExporter.doExport(developmentProject, objectId, "z_", rs);
                doExport(developmentProject, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("status", prefix + "status"),
                table.getColumn("status_codespace", prefix + "status_codespace"),
                table.getColumn("mainpurpose", prefix + "mainpurpose"),
                table.getColumn("mainpurpose_codespace", prefix + "mainpurpose_codespace"),
                table.getColumn("benefitarea", prefix + "benefitarea"),
                table.getColumn("benefitarea_uom", prefix + "benefitarea_uom"),
                table.getColumn("benefitperiod", prefix + "benefitperiod"),
                table.getColumn("cost", prefix + "cost"),
                table.getColumn("plannedarea", prefix + "plannedarea"),
                table.getColumn("plannedarea_uom", prefix + "plannedarea_uom"),
                table.getColumn("ongoingarea", prefix + "ongoingarea"),
                table.getColumn("ongoingarea_uom", prefix + "ongoingarea_uom"),
                table.getColumn("completedarea", prefix + "completedarea"),
                table.getColumn("completedarea_uom", prefix + "completedarea_uom"),
                table.getColumn("dateofdecision", prefix + "dateofdecision"),
                table.getColumn("dateofdesignationfortemporar", prefix + "dateofdesignationfortemporar"));

        return select;
    }

    public void doExport(DevelopmentProject developmentProject, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String status = rs.getString(prefix + "status");
        if (!rs.wasNull()) {
            Code code = new Code(status);
            code.setCodeSpace(rs.getString(prefix + "status_codespace"));
            developmentProject.setStatus(code);
        }

        String mainPurpose = rs.getString(prefix + "mainpurpose");
        if (!rs.wasNull()) {
            Code code = new Code(mainPurpose);
            code.setCodeSpace(rs.getString(prefix + "mainpurpose_codespace"));
            developmentProject.setMainPurpose(code);
        }

        double benefitArea = rs.getDouble(prefix + "benefitarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(benefitArea);
            measure.setUom(rs.getString(prefix + "benefitarea_uom"));
            developmentProject.setBenefitArea(measure);
        }

        developmentProject.setBenefitPeriod(rs.getString(prefix + "benefitperiod"));

        int cost = rs.getInt(prefix + "cost");
        if (!rs.wasNull())
            developmentProject.setCost(cost);

        double plannedArea = rs.getDouble(prefix + "plannedarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(plannedArea);
            measure.setUom(rs.getString(prefix + "plannedarea_uom"));
            developmentProject.setPlannedArea(measure);
        }

        double ongoingArea = rs.getDouble(prefix + "ongoingarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(ongoingArea);
            measure.setUom(rs.getString(prefix + "ongoingarea_uom"));
            developmentProject.setOngoingArea(measure);
        }

        double completedArea = rs.getDouble(prefix + "completedarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(completedArea);
            measure.setUom(rs.getString(prefix + "completedarea_uom"));
            developmentProject.setCompletedArea(measure);
        }

        Date dateOfDecision = rs.getDate(prefix + "dateofdecision");
        if (!rs.wasNull())
            developmentProject.setDateOfDecision(dateOfDecision.toLocalDate());

        Date dateOfDesignationForTemporaryReplotting = rs.getDate(prefix + "dateofdesignationfortemporar");
        if (!rs.wasNull())
            developmentProject.setDateOfDesignationForTemporaryReplotting(dateOfDesignationForTemporaryReplotting.toLocalDate());
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
