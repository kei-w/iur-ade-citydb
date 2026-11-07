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

package org.citydb.ade.iur.importer.urt;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urt.Frequency;
import org.citygml4j.ade.iur.model.urt.Trip;
import org.citygml4j.ade.iur.model.urt.TripProperty;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FrequencyImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final FrequencyToTripImporter frequencyToTripImporter;

    private int batchCounter;

    public FrequencyImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FREQUENCY)) + " " +
                "(id, starttime, endtime, headwaysecs, exacttimes, exacttimes_codespace) " +
                "values (?, ?, ?, ?, ?, ?)");

        frequencyToTripImporter = manager.getImporter(FrequencyToTripImporter.class);
    }

    public void doImport(Frequency frequency, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (frequency.getStartTime() != null)
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(LocalDate.ofEpochDay(0), frequency.getStartTime())));
        else
            ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);

        if (frequency.getEndTime() != null)
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(LocalDate.ofEpochDay(0), frequency.getEndTime())));
        else
            ps.setNull(3, Types.TIMESTAMP_WITH_TIMEZONE);

        if (frequency.getHeadwaySecs() != null)
            ps.setInt(4, frequency.getHeadwaySecs());
        else
            ps.setNull(4, Types.INTEGER);

        if (frequency.getExactTimes() != null && frequency.getExactTimes().isSetValue()) {
            ps.setString(5, frequency.getExactTimes().getValue());
            ps.setString(6, frequency.getExactTimes().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FREQUENCY));

        if (frequency.getTrip() != null) {
            TripProperty property = frequency.getTrip();
            Trip cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                frequencyToTripImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.FREQUENCY_TO_TRIP), objectId, "frequency_id", href, "trip_id");
            }
        }
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
