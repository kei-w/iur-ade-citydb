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

package org.citydb.ade.iur.exporter.uro;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.ControlPoint;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class ControlPointExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final PreparedStatement ps;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public ControlPointExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CONTROLPOINT);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("startpost", "endpost", "function", "function_codespace"))
                .addProjection(helper.getGeometryColumn(table.getColumn("startpoint")))
                .addProjection(helper.getGeometryColumn(table.getColumn("endpoint")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();
    }

    public void doExport(ControlPoint controlPoint, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                controlPoint.setStartPost(rs.getString("startpost"));

                controlPoint.setEndPost(rs.getString("endpost"));

                String function = rs.getString("function");
                if (!rs.wasNull()) {
                    Code code = new Code(function);
                    code.setCodeSpace(rs.getString("function_codespace"));
                    controlPoint.setFunction(code);
                }

                Object startPointObject = rs.getObject("startpoint");
                if (!rs.wasNull()) {
                    GeometryObject startPoint = helper.getDatabaseAdapter().getGeometryConverter().getPoint(startPointObject);
                    if (startPoint != null)
                        controlPoint.setStartPoint(gmlConverter.getPointProperty(startPoint));
                }

                Object endPointObject = rs.getObject("endpoint");
                if (!rs.wasNull()) {
                    GeometryObject endPoint = helper.getDatabaseAdapter().getGeometryConverter().getPoint(endPointObject);
                    if (endPoint != null)
                        controlPoint.setEndPoint(gmlConverter.getPointProperty(endPoint));
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
