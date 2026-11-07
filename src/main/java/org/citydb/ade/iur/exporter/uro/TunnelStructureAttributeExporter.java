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
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.TunnelStructureAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class TunnelStructureAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public TunnelStructureAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.TUNNELSTRUCTUREATTRIBU);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("tunneltype", "tunneltype_codespace", "tunnelsubtype", "tunnelsubtype_codespace", "mouthtype",
                        "mouthtype_codespace", "length", "length_uom", "width", "width_uom", "area", "area_uom",
                        "innerheight", "innerheight_uom", "effectiveheight", "effectiveheight_uom", "slopetype",
                        "slopetype_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(TunnelStructureAttribute tunnelStructureAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String tunnelType = rs.getString("tunneltype");
                if (!rs.wasNull()) {
                    Code code = new Code(tunnelType);
                    code.setCodeSpace(rs.getString("tunneltype_codespace"));
                    tunnelStructureAttribute.setTunnelType(code);
                }

                String tunnelSubtype = rs.getString("tunnelsubtype");
                if (!rs.wasNull()) {
                    Code code = new Code(tunnelSubtype);
                    code.setCodeSpace(rs.getString("tunnelsubtype_codespace"));
                    tunnelStructureAttribute.setTunnelSubtype(code);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("mouthtype"), rs.getString("mouthtype_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    tunnelStructureAttribute.getMouthType().add(code);
                }

                double lengthValue = rs.getDouble("length");
                if (!rs.wasNull()) {
                    Length length = new Length(lengthValue);
                    length.setUom(rs.getString("length_uom"));
                    tunnelStructureAttribute.setLength(length);
                }

                double width = rs.getDouble("width");
                if (!rs.wasNull()) {
                    Length length = new Length(width);
                    length.setUom(rs.getString("width_uom"));
                    tunnelStructureAttribute.setWidth(length);
                }

                double area = rs.getDouble("area");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(area);
                    measure.setUom(rs.getString("area_uom"));
                    tunnelStructureAttribute.setArea(measure);
                }

                double innerHeight = rs.getDouble("innerheight");
                if (!rs.wasNull()) {
                    Length length = new Length(innerHeight);
                    length.setUom(rs.getString("innerheight_uom"));
                    tunnelStructureAttribute.setInnerHeight(length);
                }

                double effectiveHeight = rs.getDouble("effectiveheight");
                if (!rs.wasNull()) {
                    Length length = new Length(effectiveHeight);
                    length.setUom(rs.getString("effectiveheight_uom"));
                    tunnelStructureAttribute.setEffectiveHeight(length);
                }

                String slopeType = rs.getString("slopetype");
                if (!rs.wasNull()) {
                    Code code = new Code(slopeType);
                    code.setCodeSpace(rs.getString("slopetype_codespace"));
                    tunnelStructureAttribute.setSlopeType(code);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
