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
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.OffsetDepth;
import org.citygml4j.model.gml.geometry.primitives.DirectPosition;
import org.citygml4j.model.gml.measures.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.sql.*;

public class OffsetDepthExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement psByParent;
    private final AttributeValueSplitter valueSplitter;

    public OffsetDepthExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.OFFSETDEPTH);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("pos", "offset_", "offset_uom", "depth", "depth_uom", "mindepth", "mindepth_uom", "maxdepth",
                        "maxdepth_uom"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("utilitynetwor_offsetdepth_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(table.getColumn("id")));
        psByParent = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public List<OffsetDepth> doExport(long parentId) throws CityGMLExportException, SQLException {
        PreparedStatement ps = psByParent;
        ps.setLong(1, parentId);

        List<OffsetDepth> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OffsetDepth offsetDepth = new OffsetDepth();
                String pos = rs.getString("pos");
                if (!rs.wasNull()) {
                    DirectPosition directPosition = new DirectPosition();
                    directPosition.setValue(valueSplitter.splitDoubleList(Pattern.compile(" "), pos));
                    offsetDepth.setPos(directPosition);
                }

                double offset = rs.getDouble("offset_");
                if (!rs.wasNull()) {
                    Length length = new Length(offset);
                    length.setUom(rs.getString("offset_uom"));
                    offsetDepth.setOffset(length);
                }

                double depth = rs.getDouble("depth");
                if (!rs.wasNull()) {
                    Length length = new Length(depth);
                    length.setUom(rs.getString("depth_uom"));
                    offsetDepth.setDepth(length);
                }

                double minDepth = rs.getDouble("mindepth");
                if (!rs.wasNull()) {
                    Length length = new Length(minDepth);
                    length.setUom(rs.getString("mindepth_uom"));
                    offsetDepth.setMinDepth(length);
                }

                double maxDepth = rs.getDouble("maxdepth");
                if (!rs.wasNull()) {
                    Length length = new Length(maxDepth);
                    length.setUom(rs.getString("maxdepth_uom"));
                    offsetDepth.setMaxDepth(length);
                }

                result.add(offsetDepth);
            }
        }

        return result;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        psByParent.close();
    }
}
