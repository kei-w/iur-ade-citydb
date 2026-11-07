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
import org.citygml4j.ade.iur.model.urf.SedimentDisasterProneArea;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class SedimentDisasterProneAreaExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final PreparedStatement ps;

    public SedimentDisasterProneAreaExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.SEDIMENTDISASTERPRONEA);
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

    public void doExport(SedimentDisasterProneArea sedimentDisasterProneArea, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(sedimentDisasterProneArea, objectId, "uf_", rs);
                zoneExporter.doExport(sedimentDisasterProneArea, objectId, "z_", rs);
                doExport(sedimentDisasterProneArea, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("disastertype", prefix + "disastertype"),
                table.getColumn("disastertype_codespace", prefix + "disastertype_codespace"),
                table.getColumn("areatype", prefix + "areatype"),
                table.getColumn("areatype_codespace", prefix + "areatype_codespace"),
                table.getColumn("zonenumber", prefix + "zonenumber"),
                table.getColumn("zonename", prefix + "zonename"),
                table.getColumn("status", prefix + "status"),
                table.getColumn("status_codespace", prefix + "status_codespace"));

        return select;
    }

    public void doExport(SedimentDisasterProneArea sedimentDisasterProneArea, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String disasterType = rs.getString(prefix + "disastertype");
        if (!rs.wasNull()) {
            Code code = new Code(disasterType);
            code.setCodeSpace(rs.getString(prefix + "disastertype_codespace"));
            sedimentDisasterProneArea.setDisasterType(code);
        }

        String areaType = rs.getString(prefix + "areatype");
        if (!rs.wasNull()) {
            Code code = new Code(areaType);
            code.setCodeSpace(rs.getString(prefix + "areatype_codespace"));
            sedimentDisasterProneArea.setAreaType(code);
        }

        sedimentDisasterProneArea.setZoneNumber(rs.getString(prefix + "zonenumber"));

        sedimentDisasterProneArea.setZoneName(rs.getString(prefix + "zonename"));

        String status = rs.getString(prefix + "status");
        if (!rs.wasNull()) {
            Code code = new Code(status);
            code.setCodeSpace(rs.getString(prefix + "status_codespace"));
            sedimentDisasterProneArea.setStatus(code);
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
