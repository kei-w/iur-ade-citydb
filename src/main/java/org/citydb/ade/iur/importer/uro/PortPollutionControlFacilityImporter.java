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
import org.citygml4j.ade.iur.model.uro.PortPollutionControlFacility;

import java.sql.*;

public class PortPollutionControlFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public PortPollutionControlFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTPOLLUTIONCONTROLFA)) + " " +
                "(id, length, length_uom, width, width_uom, crosssectionalarea, crosssectionalarea_uom, area," +
                "area_uom, height, height_uom, mainmaterial, mainmaterial_codespace, totalcost, subsidy, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(PortPollutionControlFacility portPollutionControlFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (portPollutionControlFacility.getLength() != null && portPollutionControlFacility.getLength().isSetValue()) {
            ps.setDouble(2, portPollutionControlFacility.getLength().getValue());
            ps.setString(3, portPollutionControlFacility.getLength().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (portPollutionControlFacility.getWidth() != null && portPollutionControlFacility.getWidth().isSetValue()) {
            ps.setDouble(4, portPollutionControlFacility.getWidth().getValue());
            ps.setString(5, portPollutionControlFacility.getWidth().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (portPollutionControlFacility.getCrossSectionalArea() != null && portPollutionControlFacility.getCrossSectionalArea().isSetValue()) {
            ps.setDouble(6, portPollutionControlFacility.getCrossSectionalArea().getValue());
            ps.setString(7, portPollutionControlFacility.getCrossSectionalArea().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (portPollutionControlFacility.getArea() != null && portPollutionControlFacility.getArea().isSetValue()) {
            ps.setDouble(8, portPollutionControlFacility.getArea().getValue());
            ps.setString(9, portPollutionControlFacility.getArea().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (portPollutionControlFacility.getHeight() != null && portPollutionControlFacility.getHeight().isSetValue()) {
            ps.setDouble(10, portPollutionControlFacility.getHeight().getValue());
            ps.setString(11, portPollutionControlFacility.getHeight().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (portPollutionControlFacility.getMainMaterial() != null && portPollutionControlFacility.getMainMaterial().isSetValue()) {
            ps.setString(12, portPollutionControlFacility.getMainMaterial().getValue());
            ps.setString(13, portPollutionControlFacility.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        if (portPollutionControlFacility.getTotalCost() != null)
            ps.setInt(14, portPollutionControlFacility.getTotalCost());
        else
            ps.setNull(14, Types.INTEGER);

        if (portPollutionControlFacility.getSubsidy() != null)
            ps.setInt(15, portPollutionControlFacility.getSubsidy());
        else
            ps.setNull(15, Types.INTEGER);

        ps.setString(16, portPollutionControlFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTPOLLUTIONCONTROLFA));
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
