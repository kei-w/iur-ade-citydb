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
import org.citygml4j.ade.iur.model.urf.UrbanRenewalProject;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class UrbanRenewalProjectExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final UrbanDevelopmentProjectExporter urbanDevelopmentProjectExporter;
    private final PreparedStatement ps;

    public UrbanRenewalProjectExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.URBANRENEWALPROJECT);
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

    public void doExport(UrbanRenewalProject urbanRenewalProject, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(urbanRenewalProject, objectId, "uf_", rs);
                zoneExporter.doExport(urbanRenewalProject, objectId, "z_", rs);
                urbanDevelopmentProjectExporter.doExport(urbanRenewalProject, objectId, "udp_", rs);
                doExport(urbanRenewalProject, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("storeysaboveground", prefix + "storeysaboveground"),
                table.getColumn("storeysbelowground", prefix + "storeysbelowground"),
                table.getColumn("setbacksize", prefix + "setbacksize"),
                table.getColumn("floorarearate", prefix + "floorarearate"),
                table.getColumn("buildingusage", prefix + "buildingusage"),
                table.getColumn("sitearea", prefix + "sitearea"),
                table.getColumn("sitearea_uom", prefix + "sitearea_uom"));

        return select;
    }

    public void doExport(UrbanRenewalProject urbanRenewalProject, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        int storeysAboveGround = rs.getInt(prefix + "storeysaboveground");
        if (!rs.wasNull())
            urbanRenewalProject.setStoreysAboveGround(storeysAboveGround);

        int storeysBelowGround = rs.getInt(prefix + "storeysbelowground");
        if (!rs.wasNull())
            urbanRenewalProject.setStoreysBelowGround(storeysBelowGround);

        String setbackSize = rs.getString(prefix + "setbacksize");
        if (!rs.wasNull())
            urbanRenewalProject.setSetbackSize(new StringOrRef(setbackSize));

        int floorAreaRate = rs.getInt(prefix + "floorarearate");
        if (!rs.wasNull())
            urbanRenewalProject.setFloorAreaRate(floorAreaRate);

        urbanRenewalProject.setBuildingUsage(rs.getString(prefix + "buildingusage"));

        double siteArea = rs.getDouble(prefix + "sitearea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(siteArea);
            measure.setUom(rs.getString(prefix + "sitearea_uom"));
            urbanRenewalProject.setSiteArea(measure);
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
