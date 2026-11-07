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
import org.citygml4j.ade.iur.model.urt.Transfer;

import java.sql.*;

public class TransferImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final TransferToStopImporter transferToStopImporter;
    private final TransferToToStopImporter transferToToStopImporter;

    private int batchCounter;

    public TransferImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRANSFER)) + " " +
                "(id, transfertype, transfertype_codespace, mintransfertime) " +
                "values (?, ?, ?, ?)");

        transferToStopImporter = manager.getImporter(TransferToStopImporter.class);
        transferToToStopImporter = manager.getImporter(TransferToToStopImporter.class);
    }

    public void doImport(Transfer transfer, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (transfer.getTransferType() != null && transfer.getTransferType().isSetValue()) {
            ps.setString(2, transfer.getTransferType().getValue());
            ps.setString(3, transfer.getTransferType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (transfer.getMinTransferTime() != null)
            ps.setInt(4, transfer.getMinTransferTime());
        else
            ps.setNull(4, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TRANSFER));

        if (transfer.getFrom() != null) {
            StopProperty property = transfer.getFrom();
            Stop cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                transferToStopImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRANSFER_TO_STOP), objectId, "transfer_id", href, "stop_id");
            }
        }
        if (transfer.getTo() != null) {
            StopProperty property = transfer.getTo();
            Stop cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                transferToToStopImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRANSFER_TO_STOP_2), objectId, "transfer_id", href, "stop_id");
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
