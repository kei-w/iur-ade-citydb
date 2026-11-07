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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.urf.StructureDetails;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class StructureDetailsExporter implements UrbanFunctionModuleExporter {
    private final PreparedStatement ps;

    public StructureDetailsExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.STRUCTUREDETAILS);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("startlocation", "endlocation", "vialocations", "length", "length_uom", "structuretype",
                        "structuretype_codespace", "minimumwidth", "minimumwidth_uom", "maximumwidth", "maximumwidth_uom",
                        "standardwidth", "standardwidth_uom", "crosstype", "crosstype_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(StructureDetails structureDetails, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                structureDetails.setStartLocation(rs.getString("startlocation"));

                structureDetails.setEndLocation(rs.getString("endlocation"));

                structureDetails.setViaLocations(rs.getString("vialocations"));

                double lengthValue = rs.getDouble("length");
                if (!rs.wasNull()) {
                    Length length = new Length(lengthValue);
                    length.setUom(rs.getString("length_uom"));
                    structureDetails.setLength(length);
                }

                String structureType = rs.getString("structuretype");
                if (!rs.wasNull()) {
                    Code code = new Code(structureType);
                    code.setCodeSpace(rs.getString("structuretype_codespace"));
                    structureDetails.setStructureType(code);
                }

                double minimumWidth = rs.getDouble("minimumwidth");
                if (!rs.wasNull()) {
                    Length length = new Length(minimumWidth);
                    length.setUom(rs.getString("minimumwidth_uom"));
                    structureDetails.setMinimumWidth(length);
                }

                double maximumWidth = rs.getDouble("maximumwidth");
                if (!rs.wasNull()) {
                    Length length = new Length(maximumWidth);
                    length.setUom(rs.getString("maximumwidth_uom"));
                    structureDetails.setMaximumWidth(length);
                }

                double standardWidth = rs.getDouble("standardwidth");
                if (!rs.wasNull()) {
                    Length length = new Length(standardWidth);
                    length.setUom(rs.getString("standardwidth_uom"));
                    structureDetails.setStandardWidth(length);
                }

                String crossType = rs.getString("crosstype");
                if (!rs.wasNull()) {
                    Code code = new Code(crossType);
                    code.setCodeSpace(rs.getString("crosstype_codespace"));
                    structureDetails.setCrossType(code);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
