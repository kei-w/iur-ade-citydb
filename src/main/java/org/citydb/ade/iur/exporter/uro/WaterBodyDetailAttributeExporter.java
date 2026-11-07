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
import org.citygml4j.ade.iur.model.uro.WaterBodyDetailAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.time.YearMonth;

import java.sql.*;

public class WaterBodyDetailAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public WaterBodyDetailAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.WATERBODYDETAILATTRIBU);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("kana", "watersystemcode", "watersystemcode_codespace", "rivercode", "rivercode_codespace",
                        "admintype", "admintype_codespace", "flowdirection", "maximumdepth", "maximumdepth_uom",
                        "watersurfaceelevation", "watersurfaceelevation_uom", "area", "area_uom", "measurementyearmonth",
                        "prefecture", "prefecture_codespace", "city", "city_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(WaterBodyDetailAttribute waterBodyDetailAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                waterBodyDetailAttribute.setKana(rs.getString("kana"));

                String waterSystemCode = rs.getString("watersystemcode");
                if (!rs.wasNull()) {
                    Code code = new Code(waterSystemCode);
                    code.setCodeSpace(rs.getString("watersystemcode_codespace"));
                    waterBodyDetailAttribute.setWaterSystemCode(code);
                }

                String riverCode = rs.getString("rivercode");
                if (!rs.wasNull()) {
                    Code code = new Code(riverCode);
                    code.setCodeSpace(rs.getString("rivercode_codespace"));
                    waterBodyDetailAttribute.setRiverCode(code);
                }

                String adminType = rs.getString("admintype");
                if (!rs.wasNull()) {
                    Code code = new Code(adminType);
                    code.setCodeSpace(rs.getString("admintype_codespace"));
                    waterBodyDetailAttribute.setAdminType(code);
                }

                boolean flowDirection = rs.getBoolean("flowdirection");
                if (!rs.wasNull())
                    waterBodyDetailAttribute.setFlowDirection(flowDirection);

                double maximumDepth = rs.getDouble("maximumdepth");
                if (!rs.wasNull()) {
                    Length length = new Length(maximumDepth);
                    length.setUom(rs.getString("maximumdepth_uom"));
                    waterBodyDetailAttribute.setMaximumDepth(length);
                }

                double waterSurfaceElevation = rs.getDouble("watersurfaceelevation");
                if (!rs.wasNull()) {
                    Length length = new Length(waterSurfaceElevation);
                    length.setUom(rs.getString("watersurfaceelevation_uom"));
                    waterBodyDetailAttribute.setWaterSurfaceElevation(length);
                }

                double area = rs.getDouble("area");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(area);
                    measure.setUom(rs.getString("area_uom"));
                    waterBodyDetailAttribute.setArea(measure);
                }

                Date measurementYearMonth = rs.getDate("measurementyearmonth");
                if (!rs.wasNull())
                    waterBodyDetailAttribute.setMeasurementYearMonth(YearMonth.from(measurementYearMonth.toLocalDate()));

                for (SplitValue splitValue : valueSplitter.split(rs.getString("prefecture"), rs.getString("prefecture_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    waterBodyDetailAttribute.getPrefecture().add(code);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("city"), rs.getString("city_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    waterBodyDetailAttribute.getCity().add(code);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
