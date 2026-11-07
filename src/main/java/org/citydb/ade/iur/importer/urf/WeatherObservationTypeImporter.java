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

package org.citydb.ade.iur.importer.urf;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.WeatherObservationType;

import java.sql.*;

public class WeatherObservationTypeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public WeatherObservationTypeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.WEATHEROBSERVATIONTYPE)) + " " +
                "(id, date_, totalrainfall, totalrainfall_uom, maxrainfallperhour, maxrainfallperhour_uom," +
                "averagetemperature, maxtemperature, mintemperature, averagewindspeed, maxwinddirection," +
                "maxwinddirection_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(WeatherObservationType weatherObservationType) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.WEATHEROBSERVATION_SEQ));
        ps.setLong(1, objectId);

        if (weatherObservationType.getDate() != null)
            ps.setDate(2, Date.valueOf(weatherObservationType.getDate()));
        else
            ps.setNull(2, Types.DATE);

        if (weatherObservationType.getTotalRainfall() != null && weatherObservationType.getTotalRainfall().isSetValue()) {
            ps.setDouble(3, weatherObservationType.getTotalRainfall().getValue());
            ps.setString(4, weatherObservationType.getTotalRainfall().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (weatherObservationType.getMaxRainfallPerHour() != null && weatherObservationType.getMaxRainfallPerHour().isSetValue()) {
            ps.setDouble(5, weatherObservationType.getMaxRainfallPerHour().getValue());
            ps.setString(6, weatherObservationType.getMaxRainfallPerHour().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (weatherObservationType.getAverageTemperature() != null)
            ps.setDouble(7, weatherObservationType.getAverageTemperature());
        else
            ps.setNull(7, Types.DOUBLE);

        if (weatherObservationType.getMaxTemperature() != null)
            ps.setDouble(8, weatherObservationType.getMaxTemperature());
        else
            ps.setNull(8, Types.DOUBLE);

        if (weatherObservationType.getMinTemperature() != null)
            ps.setDouble(9, weatherObservationType.getMinTemperature());
        else
            ps.setNull(9, Types.DOUBLE);

        if (weatherObservationType.getAverageWindSpeed() != null)
            ps.setDouble(10, weatherObservationType.getAverageWindSpeed());
        else
            ps.setNull(10, Types.DOUBLE);

        if (weatherObservationType.getMaxWindDirection() != null && weatherObservationType.getMaxWindDirection().isSetValue()) {
            ps.setString(11, weatherObservationType.getMaxWindDirection().getValue());
            ps.setString(12, weatherObservationType.getMaxWindDirection().getCodeSpace());
        } else {
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.WEATHEROBSERVATIONTYPE));

        return objectId;
    }

    @Override
    public void executeBatch() throws CityGMLImportException, SQLException {
        if (batchCounter > 0) {
            ps.executeBatch();
            batchCounter = 0;
        }
    }

    @Override
    public void close() throws CityGMLImportException, SQLException {
        ps.close();
    }
}
