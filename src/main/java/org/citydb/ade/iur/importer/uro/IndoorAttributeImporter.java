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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.IndoorAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorFacilityAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorFurnishingAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorPublicTagAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorSpaceAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorStoreyAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorTacatileTileAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorUserDefinedAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorZoneAttribute;

import java.sql.*;

public class IndoorAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final IndoorSpaceAttributeImporter indoorSpaceAttributeImporter;
    private final IndoorFacilityAttributeImporter indoorFacilityAttributeImporter;
    private final IndoorStoreyAttributeImporter indoorStoreyAttributeImporter;
    private final IndoorTacatileTileAttributeImporter indoorTacatileTileAttributeImporter;
    private final IndoorUserDefinedAttributeImporter indoorUserDefinedAttributeImporter;

    private int batchCounter;

    public IndoorAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.INDOORATTRIBUTE)) + " " +
                "(id, objectclass_id, source, source_codespace, floorid, floorid_1, ucode) " +
                "values (?, ?, ?, ?, ?, ?, ?)");

        indoorSpaceAttributeImporter = manager.getImporter(IndoorSpaceAttributeImporter.class);
        indoorFacilityAttributeImporter = manager.getImporter(IndoorFacilityAttributeImporter.class);
        indoorStoreyAttributeImporter = manager.getImporter(IndoorStoreyAttributeImporter.class);
        indoorTacatileTileAttributeImporter = manager.getImporter(IndoorTacatileTileAttributeImporter.class);
        indoorUserDefinedAttributeImporter = manager.getImporter(IndoorUserDefinedAttributeImporter.class);
    }

    public long doImport(IndoorAttribute indoorAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.INDOORATTRIBUTE_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(indoorAttribute.getClass()));

        if (indoorAttribute.getSource() != null && indoorAttribute.getSource().isSetValue()) {
            ps.setString(3, indoorAttribute.getSource().getValue());
            ps.setString(4, indoorAttribute.getSource().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (indoorAttribute instanceof IndoorFurnishingAttribute) {
            IndoorFurnishingAttribute indoorFurnishingAttribute = (IndoorFurnishingAttribute) indoorAttribute;
            ps.setString(5, indoorFurnishingAttribute.getFloorId());
        } else {
            ps.setNull(5, Types.VARCHAR);
        }

        if (indoorAttribute instanceof IndoorZoneAttribute) {
            IndoorZoneAttribute indoorZoneAttribute = (IndoorZoneAttribute) indoorAttribute;
            ps.setString(6, indoorZoneAttribute.getFloorId());
        } else {
            ps.setNull(6, Types.VARCHAR);
        }

        if (indoorAttribute instanceof IndoorPublicTagAttribute) {
            IndoorPublicTagAttribute indoorPublicTagAttribute = (IndoorPublicTagAttribute) indoorAttribute;
            ps.setString(7, indoorPublicTagAttribute.getUcode());
        } else {
            ps.setNull(7, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.INDOORATTRIBUTE));

        if (indoorAttribute instanceof IndoorSpaceAttribute)
            indoorSpaceAttributeImporter.doImport((IndoorSpaceAttribute) indoorAttribute, objectId);

        if (indoorAttribute instanceof IndoorFacilityAttribute)
            indoorFacilityAttributeImporter.doImport((IndoorFacilityAttribute) indoorAttribute, objectId);

        if (indoorAttribute instanceof IndoorStoreyAttribute)
            indoorStoreyAttributeImporter.doImport((IndoorStoreyAttribute) indoorAttribute, objectId);

        if (indoorAttribute instanceof IndoorTacatileTileAttribute)
            indoorTacatileTileAttributeImporter.doImport((IndoorTacatileTileAttribute) indoorAttribute, objectId);

        if (indoorAttribute instanceof IndoorUserDefinedAttribute)
            indoorUserDefinedAttributeImporter.doImport((IndoorUserDefinedAttribute) indoorAttribute, objectId);

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
