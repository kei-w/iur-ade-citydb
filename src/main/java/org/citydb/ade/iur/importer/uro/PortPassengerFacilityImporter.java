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
import org.citygml4j.ade.iur.model.uro.PortPassengerFacility;

import java.sql.*;
import java.time.LocalDate;

public class PortPassengerFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public PortPassengerFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTPASSENGERFACILITY)) + " " +
                "(id, length, length_uom, width, width_uom, mainmaterial, mainmaterial_codespace, totalfloorarea," +
                "totalfloorarea_uom, acquisitionyear, totalcost, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(PortPassengerFacility portPassengerFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (portPassengerFacility.getLength() != null && portPassengerFacility.getLength().isSetValue()) {
            ps.setDouble(2, portPassengerFacility.getLength().getValue());
            ps.setString(3, portPassengerFacility.getLength().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (portPassengerFacility.getWidth() != null && portPassengerFacility.getWidth().isSetValue()) {
            ps.setDouble(4, portPassengerFacility.getWidth().getValue());
            ps.setString(5, portPassengerFacility.getWidth().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (portPassengerFacility.getMainMaterial() != null && portPassengerFacility.getMainMaterial().isSetValue()) {
            ps.setString(6, portPassengerFacility.getMainMaterial().getValue());
            ps.setString(7, portPassengerFacility.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (portPassengerFacility.getTotalFloorArea() != null && portPassengerFacility.getTotalFloorArea().isSetValue()) {
            ps.setDouble(8, portPassengerFacility.getTotalFloorArea().getValue());
            ps.setString(9, portPassengerFacility.getTotalFloorArea().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (portPassengerFacility.getAcquisitionYear() != null)
            ps.setDate(10, Date.valueOf(LocalDate.of(portPassengerFacility.getAcquisitionYear().getValue(), 1, 1)));
        else
            ps.setNull(10, Types.DATE);

        if (portPassengerFacility.getTotalCost() != null)
            ps.setInt(11, portPassengerFacility.getTotalCost());
        else
            ps.setNull(11, Types.INTEGER);

        ps.setString(12, portPassengerFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTPASSENGERFACILITY));
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
