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
import org.citygml4j.ade.iur.model.urt.Stop;
import org.citygml4j.ade.iur.model.urt.StopProperty;
import org.citygml4j.ade.iur.model.urt.StopTime;
import org.citygml4j.ade.iur.model.urt.Trip;
import org.citygml4j.ade.iur.model.urt.TripProperty;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StopTimeImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final StopTimeToTripImporter stopTimeToTripImporter;
    private final StopTimeToStopImporter stopTimeToStopImporter;

    private int batchCounter;

    public StopTimeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.STOPTIME)) + " " +
                "(id, arrivaltime, departuretime, stopsequence, headsign, pickuptype, pickuptype_codespace," +
                "dropofftype, dropofftype_codespace, continuouspickuptype, continuouspickupty_codespace," +
                "continuousdropofftype, continuousdropofft_codespace, shapedistancetraveled, timepoint," +
                "timepoint_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        stopTimeToTripImporter = manager.getImporter(StopTimeToTripImporter.class);
        stopTimeToStopImporter = manager.getImporter(StopTimeToStopImporter.class);
    }

    public void doImport(StopTime stopTime, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (stopTime.getArrivalTime() != null)
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(LocalDate.ofEpochDay(0), stopTime.getArrivalTime())));
        else
            ps.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);

        if (stopTime.getDepartureTime() != null)
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(LocalDate.ofEpochDay(0), stopTime.getDepartureTime())));
        else
            ps.setNull(3, Types.TIMESTAMP_WITH_TIMEZONE);

        if (stopTime.getStopSequence() != null)
            ps.setInt(4, stopTime.getStopSequence());
        else
            ps.setNull(4, Types.INTEGER);

        ps.setString(5, stopTime.getHeadsign());

        if (stopTime.getPickupType() != null && stopTime.getPickupType().isSetValue()) {
            ps.setString(6, stopTime.getPickupType().getValue());
            ps.setString(7, stopTime.getPickupType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (stopTime.getDropoffType() != null && stopTime.getDropoffType().isSetValue()) {
            ps.setString(8, stopTime.getDropoffType().getValue());
            ps.setString(9, stopTime.getDropoffType().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (stopTime.getContinuousPickupType() != null && stopTime.getContinuousPickupType().isSetValue()) {
            ps.setString(10, stopTime.getContinuousPickupType().getValue());
            ps.setString(11, stopTime.getContinuousPickupType().getCodeSpace());
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        if (stopTime.getContinuousDropoffType() != null && stopTime.getContinuousDropoffType().isSetValue()) {
            ps.setString(12, stopTime.getContinuousDropoffType().getValue());
            ps.setString(13, stopTime.getContinuousDropoffType().getCodeSpace());
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        if (stopTime.getShapeDistanceTraveled() != null)
            ps.setDouble(14, stopTime.getShapeDistanceTraveled());
        else
            ps.setNull(14, Types.DOUBLE);

        if (stopTime.getTimePoint() != null && stopTime.getTimePoint().isSetValue()) {
            ps.setString(15, stopTime.getTimePoint().getValue());
            ps.setString(16, stopTime.getTimePoint().getCodeSpace());
        } else {
            ps.setNull(15, Types.VARCHAR);
            ps.setNull(16, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.STOPTIME));

        if (stopTime.getTrip() != null) {
            TripProperty property = stopTime.getTrip();
            Trip cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                stopTimeToTripImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.STOPTIME_TO_TRIP), objectId, "stoptime_id", href, "trip_id");
            }
        }
        if (stopTime.getStop() != null) {
            StopProperty property = stopTime.getStop();
            Stop cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                stopTimeToStopImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.STOPTIME_TO_STOP), objectId, "stoptime_id", href, "stop_id");
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
