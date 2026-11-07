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
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.CircularCurveType;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class CircularCurveTypeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public CircularCurveTypeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CIRCULARCURVETYPE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("radius", "radius_uom", "intersection", "cutlength", "cutlength_uom", "curvelength",
                        "curvelength_uom"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(CircularCurveType circularCurveType, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                double radius = rs.getDouble("radius");
                if (!rs.wasNull()) {
                    Length length = new Length(radius);
                    length.setUom(rs.getString("radius_uom"));
                    circularCurveType.setRadius(length);
                }

                double intersection = rs.getDouble("intersection");
                if (!rs.wasNull())
                    circularCurveType.setIntersection(intersection);

                double cutLength = rs.getDouble("cutlength");
                if (!rs.wasNull()) {
                    Length length = new Length(cutLength);
                    length.setUom(rs.getString("cutlength_uom"));
                    circularCurveType.setCutLength(length);
                }

                double curveLength = rs.getDouble("curvelength");
                if (!rs.wasNull()) {
                    Length length = new Length(curveLength);
                    length.setUom(rs.getString("curvelength_uom"));
                    circularCurveType.setCurveLength(length);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
