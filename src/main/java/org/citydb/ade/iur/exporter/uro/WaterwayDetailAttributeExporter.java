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
import org.citygml4j.ade.iur.model.uro.WaterwayDetailAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class WaterwayDetailAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public WaterwayDetailAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.WATERWAYDETAILATTRIBUT);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("routeid", "routedirection", "routedirection_codespace", "minimumwidth", "minimumwidth_uom",
                        "maximumwidth", "maximumwidth_uom", "length", "length_uom", "navigation", "planneddepth",
                        "planneddepth_uom", "speedlimit", "speedlimit_uom", "targetshiptype"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(WaterwayDetailAttribute waterwayDetailAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                waterwayDetailAttribute.setRouteId(rs.getString("routeid"));

                String routeDirection = rs.getString("routedirection");
                if (!rs.wasNull()) {
                    Code code = new Code(routeDirection);
                    code.setCodeSpace(rs.getString("routedirection_codespace"));
                    waterwayDetailAttribute.setRouteDirection(code);
                }

                double minimumWidth = rs.getDouble("minimumwidth");
                if (!rs.wasNull()) {
                    Length length = new Length(minimumWidth);
                    length.setUom(rs.getString("minimumwidth_uom"));
                    waterwayDetailAttribute.setMinimumWidth(length);
                }

                double maximumWidth = rs.getDouble("maximumwidth");
                if (!rs.wasNull()) {
                    Length length = new Length(maximumWidth);
                    length.setUom(rs.getString("maximumwidth_uom"));
                    waterwayDetailAttribute.setMaximumWidth(length);
                }

                double lengthValue = rs.getDouble("length");
                if (!rs.wasNull()) {
                    Length length = new Length(lengthValue);
                    length.setUom(rs.getString("length_uom"));
                    waterwayDetailAttribute.setLength(length);
                }

                waterwayDetailAttribute.setNavigation(rs.getString("navigation"));

                double plannedDepth = rs.getDouble("planneddepth");
                if (!rs.wasNull()) {
                    Length length = new Length(plannedDepth);
                    length.setUom(rs.getString("planneddepth_uom"));
                    waterwayDetailAttribute.setPlannedDepth(length);
                }

                double speedLimit = rs.getDouble("speedlimit");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(speedLimit);
                    measure.setUom(rs.getString("speedlimit_uom"));
                    waterwayDetailAttribute.setSpeedLimit(measure);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("targetshiptype")))
                    waterwayDetailAttribute.getTargetShipType().add(splitValue.result(0));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
