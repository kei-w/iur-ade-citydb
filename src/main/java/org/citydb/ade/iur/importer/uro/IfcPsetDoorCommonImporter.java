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
import org.citygml4j.ade.iur.model.uro.IfcPsetDoorCommon;

import java.sql.*;

public class IfcPsetDoorCommonImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public IfcPsetDoorCommonImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCPSETDOORCOMMON)) + " " +
                "(id, reference, acousticrating, firerating, securityrating, isexternal, infiltration," +
                "thermaltransmittance, glazingareafraction, handicapaccessible, fireexit, selfclosing, smokestop) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IfcPsetDoorCommon ifcPsetDoorCommon, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, ifcPsetDoorCommon.getReference());

        ps.setString(3, ifcPsetDoorCommon.getAcousticRating());

        ps.setString(4, ifcPsetDoorCommon.getFirerating());

        ps.setString(5, ifcPsetDoorCommon.getSecurityRating());

        if (ifcPsetDoorCommon.getIsExternal() != null)
            ps.setInt(6, ifcPsetDoorCommon.getIsExternal() ? 1 : 0);
        else
            ps.setNull(6, Types.INTEGER);

        if (ifcPsetDoorCommon.getInfiltration() != null)
            ps.setDouble(7, ifcPsetDoorCommon.getInfiltration());
        else
            ps.setNull(7, Types.DOUBLE);

        if (ifcPsetDoorCommon.getThermalTransmittance() != null)
            ps.setDouble(8, ifcPsetDoorCommon.getThermalTransmittance());
        else
            ps.setNull(8, Types.DOUBLE);

        if (ifcPsetDoorCommon.getGlazingAreaFraction() != null)
            ps.setDouble(9, ifcPsetDoorCommon.getGlazingAreaFraction());
        else
            ps.setNull(9, Types.DOUBLE);

        if (ifcPsetDoorCommon.getHandicapAccessible() != null)
            ps.setInt(10, ifcPsetDoorCommon.getHandicapAccessible() ? 1 : 0);
        else
            ps.setNull(10, Types.INTEGER);

        if (ifcPsetDoorCommon.getFireExit() != null)
            ps.setInt(11, ifcPsetDoorCommon.getFireExit() ? 1 : 0);
        else
            ps.setNull(11, Types.INTEGER);

        if (ifcPsetDoorCommon.getSelfClosing() != null)
            ps.setInt(12, ifcPsetDoorCommon.getSelfClosing() ? 1 : 0);
        else
            ps.setNull(12, Types.INTEGER);

        if (ifcPsetDoorCommon.getSmokeStop() != null)
            ps.setInt(13, ifcPsetDoorCommon.getSmokeStop() ? 1 : 0);
        else
            ps.setNull(13, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCPSETDOORCOMMON));
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
