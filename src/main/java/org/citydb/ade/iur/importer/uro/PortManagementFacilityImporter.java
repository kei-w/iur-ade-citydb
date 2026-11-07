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
import org.citygml4j.ade.iur.model.uro.PortManagementFacility;

import java.sql.*;
import java.time.LocalDate;

public class PortManagementFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public PortManagementFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTMANAGEMENTFACILITY)) + " " +
                "(id, totalfloorarea, totalfloorarea_uom, numberofshiptypes, unitofshiptype," +
                "unitofshiptype_codespace, loadingcapacity, acquisitionyear, usage, totalcost, subsidy, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(PortManagementFacility portManagementFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (portManagementFacility.getTotalFloorArea() != null && portManagementFacility.getTotalFloorArea().isSetValue()) {
            ps.setDouble(2, portManagementFacility.getTotalFloorArea().getValue());
            ps.setString(3, portManagementFacility.getTotalFloorArea().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (portManagementFacility.getNumberOfShipTypes() != null)
            ps.setInt(4, portManagementFacility.getNumberOfShipTypes());
        else
            ps.setNull(4, Types.INTEGER);

        if (portManagementFacility.getUnitOfShipType() != null && portManagementFacility.getUnitOfShipType().isSetValue()) {
            ps.setString(5, portManagementFacility.getUnitOfShipType().getValue());
            ps.setString(6, portManagementFacility.getUnitOfShipType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (portManagementFacility.getLoadingCapacity() != null)
            ps.setInt(7, portManagementFacility.getLoadingCapacity());
        else
            ps.setNull(7, Types.INTEGER);

        if (portManagementFacility.getAcquisitionYear() != null)
            ps.setDate(8, Date.valueOf(LocalDate.of(portManagementFacility.getAcquisitionYear().getValue(), 1, 1)));
        else
            ps.setNull(8, Types.DATE);

        ps.setString(9, portManagementFacility.getUsage());

        if (portManagementFacility.getTotalCost() != null)
            ps.setInt(10, portManagementFacility.getTotalCost());
        else
            ps.setNull(10, Types.INTEGER);

        if (portManagementFacility.getSubsidy() != null)
            ps.setInt(11, portManagementFacility.getSubsidy());
        else
            ps.setNull(11, Types.INTEGER);

        ps.setString(12, portManagementFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTMANAGEMENTFACILITY));
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
