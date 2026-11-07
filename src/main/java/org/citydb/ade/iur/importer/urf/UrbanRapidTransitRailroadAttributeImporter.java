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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.StructureDetailsProperty;
import org.citygml4j.ade.iur.model.urf.UrbanRapidTransitRailroadAttribute;

import java.sql.*;

public class UrbanRapidTransitRailroadAttributeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanRapidTransitRailroadAttributeToStructureDetailsImporter urbanRapidTransitRailroadAttributeToStructureDetailsImporter;
    private final StructureDetailsImporter structureDetailsImporter;

    private int batchCounter;

    public UrbanRapidTransitRailroadAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URBANRAPIDTRANSITRAILR)) + " " +
                "(id, structuretype, structuretype_codespace, crosstype, crosstype_codespace) " +
                "values (?, ?, ?, ?, ?)");

        urbanRapidTransitRailroadAttributeToStructureDetailsImporter = manager.getImporter(UrbanRapidTransitRailroadAttributeToStructureDetailsImporter.class);
        structureDetailsImporter = manager.getImporter(StructureDetailsImporter.class);
    }

    public long doImport(UrbanRapidTransitRailroadAttribute urbanRapidTransitRailroadAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.URBANRAPIDTRANSITR_SEQ));
        ps.setLong(1, objectId);

        if (urbanRapidTransitRailroadAttribute.getStructureType() != null && urbanRapidTransitRailroadAttribute.getStructureType().isSetValue()) {
            ps.setString(2, urbanRapidTransitRailroadAttribute.getStructureType().getValue());
            ps.setString(3, urbanRapidTransitRailroadAttribute.getStructureType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (urbanRapidTransitRailroadAttribute.getCrossType() != null && urbanRapidTransitRailroadAttribute.getCrossType().isSetValue()) {
            ps.setString(4, urbanRapidTransitRailroadAttribute.getCrossType().getValue());
            ps.setString(5, urbanRapidTransitRailroadAttribute.getCrossType().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.URBANRAPIDTRANSITRAILR));

        for (StructureDetailsProperty property : urbanRapidTransitRailroadAttribute.getStructuralDetails()) {
            if (property.isSetObject()) {
                long structuralDetailsId = structureDetailsImporter.doImport(property.getObject());
                urbanRapidTransitRailroadAttributeToStructureDetailsImporter.doImport(structuralDetailsId, objectId);
            }
        }

        return objectId;
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
