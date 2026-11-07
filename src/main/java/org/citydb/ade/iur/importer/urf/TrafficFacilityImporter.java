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
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.TrafficFacility;

import java.sql.*;

public class TrafficFacilityImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFacilityImporter urbanFacilityImporter;
    private final UrbanRoadAttributeImporter urbanRoadAttributeImporter;
    private final UrbanRapidTransitRailroadAttributeImporter urbanRapidTransitRailroadAttributeImporter;
    private final ParkingPlaceAttributeImporter parkingPlaceAttributeImporter;
    private final VehicleTerminalAttributeImporter vehicleTerminalAttributeImporter;

    private int batchCounter;

    public TrafficFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRAFFICFACILITY)) + " " +
                "(id, startlocation, endlocation, vialocations, length, length_uom, width, width_uom," +
                "urbanroadattribute_id, urbanrapidtransitrailroad_id, parkingplaceattribute_id," +
                "vehicleterminalattribute_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFacilityImporter = manager.getImporter(UrbanFacilityImporter.class);
        urbanRoadAttributeImporter = manager.getImporter(UrbanRoadAttributeImporter.class);
        urbanRapidTransitRailroadAttributeImporter = manager.getImporter(UrbanRapidTransitRailroadAttributeImporter.class);
        parkingPlaceAttributeImporter = manager.getImporter(ParkingPlaceAttributeImporter.class);
        vehicleTerminalAttributeImporter = manager.getImporter(VehicleTerminalAttributeImporter.class);
    }

    public void doImport(TrafficFacility trafficFacility, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFacilityImporter.doImport(trafficFacility, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, trafficFacility.getStartLocation());

        ps.setString(3, trafficFacility.getEndLocation());

        ps.setString(4, trafficFacility.getViaLocations());

        if (trafficFacility.getLength() != null && trafficFacility.getLength().isSetValue()) {
            ps.setDouble(5, trafficFacility.getLength().getValue());
            ps.setString(6, trafficFacility.getLength().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (trafficFacility.getWidth() != null && trafficFacility.getWidth().isSetValue()) {
            ps.setDouble(7, trafficFacility.getWidth().getValue());
            ps.setString(8, trafficFacility.getWidth().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        long urbanRoadAttributeId = 0;
        if (trafficFacility.getUrbanRoadAttribute() != null && trafficFacility.getUrbanRoadAttribute().isSetObject())
            urbanRoadAttributeId = urbanRoadAttributeImporter.doImport(trafficFacility.getUrbanRoadAttribute().getObject());

        if (urbanRoadAttributeId != 0)
            ps.setLong(9, urbanRoadAttributeId);
        else
            ps.setNull(9, Types.NULL);

        long urbanRapidTransitRailroadAttributeId = 0;
        if (trafficFacility.getUrbanRapidTransitRailroadAttribute() != null && trafficFacility.getUrbanRapidTransitRailroadAttribute().isSetObject())
            urbanRapidTransitRailroadAttributeId = urbanRapidTransitRailroadAttributeImporter.doImport(trafficFacility.getUrbanRapidTransitRailroadAttribute().getObject());

        if (urbanRapidTransitRailroadAttributeId != 0)
            ps.setLong(10, urbanRapidTransitRailroadAttributeId);
        else
            ps.setNull(10, Types.NULL);

        long parkingPlaceAttributeId = 0;
        if (trafficFacility.getParkingPlaceAttribute() != null && trafficFacility.getParkingPlaceAttribute().isSetObject())
            parkingPlaceAttributeId = parkingPlaceAttributeImporter.doImport(trafficFacility.getParkingPlaceAttribute().getObject());

        if (parkingPlaceAttributeId != 0)
            ps.setLong(11, parkingPlaceAttributeId);
        else
            ps.setNull(11, Types.NULL);

        long vehicleTerminalAttributeId = 0;
        if (trafficFacility.getVehicleTerminalAttribute() != null && trafficFacility.getVehicleTerminalAttribute().isSetObject())
            vehicleTerminalAttributeId = vehicleTerminalAttributeImporter.doImport(trafficFacility.getVehicleTerminalAttribute().getObject());

        if (vehicleTerminalAttributeId != 0)
            ps.setLong(12, vehicleTerminalAttributeId);
        else
            ps.setNull(12, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);
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
