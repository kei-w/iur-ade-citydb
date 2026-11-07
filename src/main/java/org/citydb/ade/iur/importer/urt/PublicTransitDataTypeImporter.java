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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urt.FareRule;
import org.citygml4j.ade.iur.model.urt.FeedInfo;
import org.citygml4j.ade.iur.model.urt.Frequency;
import org.citygml4j.ade.iur.model.urt.PublicTransitDataType;
import org.citygml4j.ade.iur.model.urt.StopTime;
import org.citygml4j.ade.iur.model.urt.Transfer;
import org.citygml4j.ade.iur.model.urt.Translation;

import java.sql.*;

public class PublicTransitDataTypeImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final FeedInfoImporter feedInfoImporter;
    private final TranslationImporter translationImporter;
    private final TransferImporter transferImporter;
    private final FrequencyImporter frequencyImporter;
    private final StopTimeImporter stopTimeImporter;
    private final FareRuleImporter fareRuleImporter;

    private int batchCounter;

    public PublicTransitDataTypeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PUBLICTRANSITDATATYPE)) + " " +
                "(id, objectclass_id) " +
                "values (?, ?)");

        feedInfoImporter = manager.getImporter(FeedInfoImporter.class);
        translationImporter = manager.getImporter(TranslationImporter.class);
        transferImporter = manager.getImporter(TransferImporter.class);
        frequencyImporter = manager.getImporter(FrequencyImporter.class);
        stopTimeImporter = manager.getImporter(StopTimeImporter.class);
        fareRuleImporter = manager.getImporter(FareRuleImporter.class);
    }

    public long doImport(PublicTransitDataType publicTransitDataType) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.PUBLICTRANSITDATAT_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(publicTransitDataType.getClass()));

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PUBLICTRANSITDATATYPE));

        if (publicTransitDataType instanceof FeedInfo)
            feedInfoImporter.doImport((FeedInfo) publicTransitDataType, objectId);

        if (publicTransitDataType instanceof Translation)
            translationImporter.doImport((Translation) publicTransitDataType, objectId);

        if (publicTransitDataType instanceof Transfer)
            transferImporter.doImport((Transfer) publicTransitDataType, objectId);

        if (publicTransitDataType instanceof Frequency)
            frequencyImporter.doImport((Frequency) publicTransitDataType, objectId);

        if (publicTransitDataType instanceof StopTime)
            stopTimeImporter.doImport((StopTime) publicTransitDataType, objectId);

        if (publicTransitDataType instanceof FareRule)
            fareRuleImporter.doImport((FareRule) publicTransitDataType, objectId);

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
