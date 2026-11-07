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

package org.citydb.ade.iur.importer.uro;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.PortTransportationFacility;

import java.sql.*;

public class PortTransportationFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public PortTransportationFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTTRANSPORTATIONFACI)) + " " +
                "(id, structuretype, structuretype_codespace, startingpoint, length, length_uom, area, area_uom," +
                "beddingwidth, beddingwidth_uom, numberoflanes, parkinglotcapacityofbus, parkinglotcapacityofcars," +
                "routetype, routetype_codespace, heighttodigit, heighttodigit_uom, heightlimit, heightlimit_uom," +
                "minimumwidth, minimumwidth_uom, minimumdepth, minimumdepth_uom, numberofaircraftparkingspace," +
                "pavementtype, pavementtype_codespace, maincargo, maincargo_codespace, totalcost, subsidy, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(PortTransportationFacility portTransportationFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (portTransportationFacility.getStructureType() != null && portTransportationFacility.getStructureType().isSetValue()) {
            ps.setString(2, portTransportationFacility.getStructureType().getValue());
            ps.setString(3, portTransportationFacility.getStructureType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, portTransportationFacility.getStartingPoint());

        if (portTransportationFacility.getLength() != null && portTransportationFacility.getLength().isSetValue()) {
            ps.setDouble(5, portTransportationFacility.getLength().getValue());
            ps.setString(6, portTransportationFacility.getLength().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (portTransportationFacility.getArea() != null && portTransportationFacility.getArea().isSetValue()) {
            ps.setDouble(7, portTransportationFacility.getArea().getValue());
            ps.setString(8, portTransportationFacility.getArea().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (portTransportationFacility.getBeddingWidth() != null && portTransportationFacility.getBeddingWidth().isSetValue()) {
            ps.setDouble(9, portTransportationFacility.getBeddingWidth().getValue());
            ps.setString(10, portTransportationFacility.getBeddingWidth().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (portTransportationFacility.getNumberOfLanes() != null)
            ps.setInt(11, portTransportationFacility.getNumberOfLanes());
        else
            ps.setNull(11, Types.INTEGER);

        if (portTransportationFacility.getParkingLotCapacityOfBus() != null)
            ps.setInt(12, portTransportationFacility.getParkingLotCapacityOfBus());
        else
            ps.setNull(12, Types.INTEGER);

        if (portTransportationFacility.getParkingLotCapacityOfCars() != null)
            ps.setInt(13, portTransportationFacility.getParkingLotCapacityOfCars());
        else
            ps.setNull(13, Types.INTEGER);

        if (portTransportationFacility.getRouteType() != null && portTransportationFacility.getRouteType().isSetValue()) {
            ps.setString(14, portTransportationFacility.getRouteType().getValue());
            ps.setString(15, portTransportationFacility.getRouteType().getCodeSpace());
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        if (portTransportationFacility.getHeightToDigit() != null && portTransportationFacility.getHeightToDigit().isSetValue()) {
            ps.setDouble(16, portTransportationFacility.getHeightToDigit().getValue());
            ps.setString(17, portTransportationFacility.getHeightToDigit().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (portTransportationFacility.getHeightLimit() != null && portTransportationFacility.getHeightLimit().isSetValue()) {
            ps.setDouble(18, portTransportationFacility.getHeightLimit().getValue());
            ps.setString(19, portTransportationFacility.getHeightLimit().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (portTransportationFacility.getMinimumWidth() != null && portTransportationFacility.getMinimumWidth().isSetValue()) {
            ps.setDouble(20, portTransportationFacility.getMinimumWidth().getValue());
            ps.setString(21, portTransportationFacility.getMinimumWidth().getUom());
        } else {
            ps.setNull(20, Types.DOUBLE);
            ps.setNull(21, Types.VARCHAR);
        }

        if (portTransportationFacility.getMinimumDepth() != null && portTransportationFacility.getMinimumDepth().isSetValue()) {
            ps.setDouble(22, portTransportationFacility.getMinimumDepth().getValue());
            ps.setString(23, portTransportationFacility.getMinimumDepth().getUom());
        } else {
            ps.setNull(22, Types.DOUBLE);
            ps.setNull(23, Types.VARCHAR);
        }

        if (portTransportationFacility.getNumberOfAircraftParkingSpaces() != null)
            ps.setInt(24, portTransportationFacility.getNumberOfAircraftParkingSpaces());
        else
            ps.setNull(24, Types.INTEGER);

        if (portTransportationFacility.getPavementType() != null && portTransportationFacility.getPavementType().isSetValue()) {
            ps.setString(25, portTransportationFacility.getPavementType().getValue());
            ps.setString(26, portTransportationFacility.getPavementType().getCodeSpace());
        } else {
            ps.setNull(25, Types.VARCHAR);
            ps.setNull(26, Types.VARCHAR);
        }

        if (portTransportationFacility.getMainCargo() != null && portTransportationFacility.getMainCargo().isSetValue()) {
            ps.setString(27, portTransportationFacility.getMainCargo().getValue());
            ps.setString(28, portTransportationFacility.getMainCargo().getCodeSpace());
        } else {
            ps.setNull(27, Types.VARCHAR);
            ps.setNull(28, Types.VARCHAR);
        }

        if (portTransportationFacility.getTotalCost() != null)
            ps.setInt(29, portTransportationFacility.getTotalCost());
        else
            ps.setNull(29, Types.INTEGER);

        if (portTransportationFacility.getSubsidy() != null)
            ps.setInt(30, portTransportationFacility.getSubsidy());
        else
            ps.setNull(30, Types.INTEGER);

        ps.setString(31, portTransportationFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTTRANSPORTATIONFACI));
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
