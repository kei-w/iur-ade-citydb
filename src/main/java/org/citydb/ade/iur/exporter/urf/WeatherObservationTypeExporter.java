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
import org.citygml4j.ade.iur.model.urf.WeatherObservationType;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class WeatherObservationTypeExporter implements UrbanFunctionModuleExporter {
    private final PreparedStatement ps;

    public WeatherObservationTypeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.WEATHEROBSERVATIONTYPE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("date_", "totalrainfall", "totalrainfall_uom", "maxrainfallperhour", "maxrainfallperhour_uom",
                        "averagetemperature", "maxtemperature", "mintemperature", "averagewindspeed", "maxwinddirection",
                        "maxwinddirection_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(WeatherObservationType weatherObservationType, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Date date = rs.getDate("date_");
                if (!rs.wasNull())
                    weatherObservationType.setDate(date.toLocalDate());

                double totalRainfall = rs.getDouble("totalrainfall");
                if (!rs.wasNull()) {
                    Length length = new Length(totalRainfall);
                    length.setUom(rs.getString("totalrainfall_uom"));
                    weatherObservationType.setTotalRainfall(length);
                }

                double maxRainfallPerHour = rs.getDouble("maxrainfallperhour");
                if (!rs.wasNull()) {
                    Length length = new Length(maxRainfallPerHour);
                    length.setUom(rs.getString("maxrainfallperhour_uom"));
                    weatherObservationType.setMaxRainfallPerHour(length);
                }

                double averageTemperature = rs.getDouble("averagetemperature");
                if (!rs.wasNull())
                    weatherObservationType.setAverageTemperature(averageTemperature);

                double maxTemperature = rs.getDouble("maxtemperature");
                if (!rs.wasNull())
                    weatherObservationType.setMaxTemperature(maxTemperature);

                double minTemperature = rs.getDouble("mintemperature");
                if (!rs.wasNull())
                    weatherObservationType.setMinTemperature(minTemperature);

                double averageWindSpeed = rs.getDouble("averagewindspeed");
                if (!rs.wasNull())
                    weatherObservationType.setAverageWindSpeed(averageWindSpeed);

                String maxWindDirection = rs.getString("maxwinddirection");
                if (!rs.wasNull()) {
                    Code code = new Code(maxWindDirection);
                    code.setCodeSpace(rs.getString("maxwinddirection_codespace"));
                    weatherObservationType.setMaxWindDirection(code);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
