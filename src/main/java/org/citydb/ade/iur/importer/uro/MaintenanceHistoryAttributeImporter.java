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
import org.citygml4j.ade.iur.model.uro.MaintenanceHistoryAttribute;

import java.sql.*;
import java.time.LocalDate;

public class MaintenanceHistoryAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public MaintenanceHistoryAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.MAINTENANCEHISTORYATTR)) + " " +
                "(id, maintenancetype, maintenancetype_codespace, maintenancefiscalyear, maintenanceyear," +
                "maintenancedate, status, description) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(MaintenanceHistoryAttribute maintenanceHistoryAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (maintenanceHistoryAttribute.getMaintenanceType() != null && maintenanceHistoryAttribute.getMaintenanceType().isSetValue()) {
            ps.setString(2, maintenanceHistoryAttribute.getMaintenanceType().getValue());
            ps.setString(3, maintenanceHistoryAttribute.getMaintenanceType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (maintenanceHistoryAttribute.getMaintenanceFiscalYear() != null)
            ps.setDate(4, Date.valueOf(LocalDate.of(maintenanceHistoryAttribute.getMaintenanceFiscalYear().getValue(), 1, 1)));
        else
            ps.setNull(4, Types.DATE);

        if (maintenanceHistoryAttribute.getMaintenanceYear() != null)
            ps.setDate(5, Date.valueOf(LocalDate.of(maintenanceHistoryAttribute.getMaintenanceYear().getValue(), 1, 1)));
        else
            ps.setNull(5, Types.DATE);

        if (maintenanceHistoryAttribute.getMaintenanceDate() != null)
            ps.setDate(6, Date.valueOf(maintenanceHistoryAttribute.getMaintenanceDate()));
        else
            ps.setNull(6, Types.DATE);

        ps.setString(7, maintenanceHistoryAttribute.getStatus());

        if (maintenanceHistoryAttribute.getDescription() != null && maintenanceHistoryAttribute.getDescription().isSetValue())
            ps.setString(8, maintenanceHistoryAttribute.getDescription().getValue());
        else
            ps.setNull(8, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.MAINTENANCEHISTORYATTR));
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
