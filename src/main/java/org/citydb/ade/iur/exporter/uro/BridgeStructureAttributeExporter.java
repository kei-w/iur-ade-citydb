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
import org.citygml4j.ade.iur.model.uro.BridgeStructureAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class BridgeStructureAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public BridgeStructureAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BRIDGESTRUCTUREATTRIBU);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("material", "material_codespace", "bridgetype", "bridgetype_codespace", "length", "length_uom",
                        "width", "width_uom", "area", "area_uom", "weightrestriction", "weightrestriction_uom",
                        "heightrestriction", "heightrestriction_uom", "widthrestriction", "widthrestriction_uom",
                        "undergirderheight", "undergirderheight_uom", "slopetype", "slopetype_codespace", "escalator"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(BridgeStructureAttribute bridgeStructureAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String material = rs.getString("material");
                if (!rs.wasNull()) {
                    Code code = new Code(material);
                    code.setCodeSpace(rs.getString("material_codespace"));
                    bridgeStructureAttribute.setMaterial(code);
                }

                String bridgeType = rs.getString("bridgetype");
                if (!rs.wasNull()) {
                    Code code = new Code(bridgeType);
                    code.setCodeSpace(rs.getString("bridgetype_codespace"));
                    bridgeStructureAttribute.setBridgeType(code);
                }

                double lengthValue = rs.getDouble("length");
                if (!rs.wasNull()) {
                    Length length = new Length(lengthValue);
                    length.setUom(rs.getString("length_uom"));
                    bridgeStructureAttribute.setLength(length);
                }

                double width = rs.getDouble("width");
                if (!rs.wasNull()) {
                    Length length = new Length(width);
                    length.setUom(rs.getString("width_uom"));
                    bridgeStructureAttribute.setWidth(length);
                }

                double area = rs.getDouble("area");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(area);
                    measure.setUom(rs.getString("area_uom"));
                    bridgeStructureAttribute.setArea(measure);
                }

                double weightRestriction = rs.getDouble("weightrestriction");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(weightRestriction);
                    measure.setUom(rs.getString("weightrestriction_uom"));
                    bridgeStructureAttribute.setWeightRestriction(measure);
                }

                double heightRestriction = rs.getDouble("heightrestriction");
                if (!rs.wasNull()) {
                    Length length = new Length(heightRestriction);
                    length.setUom(rs.getString("heightrestriction_uom"));
                    bridgeStructureAttribute.setHeightRestriction(length);
                }

                double widthRestriction = rs.getDouble("widthrestriction");
                if (!rs.wasNull()) {
                    Length length = new Length(widthRestriction);
                    length.setUom(rs.getString("widthrestriction_uom"));
                    bridgeStructureAttribute.setWidthRestriction(length);
                }

                double underGirderHeight = rs.getDouble("undergirderheight");
                if (!rs.wasNull()) {
                    Length length = new Length(underGirderHeight);
                    length.setUom(rs.getString("undergirderheight_uom"));
                    bridgeStructureAttribute.setUnderGirderHeight(length);
                }

                String slopeType = rs.getString("slopetype");
                if (!rs.wasNull()) {
                    Code code = new Code(slopeType);
                    code.setCodeSpace(rs.getString("slopetype_codespace"));
                    bridgeStructureAttribute.setSlopeType(code);
                }

                boolean escalator = rs.getBoolean("escalator");
                if (!rs.wasNull())
                    bridgeStructureAttribute.setEscalator(escalator);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
