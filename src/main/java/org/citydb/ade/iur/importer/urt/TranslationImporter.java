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
import org.citygml4j.ade.iur.model.urt.PublicTransit;
import org.citygml4j.ade.iur.model.urt.PublicTransitProperty;
import org.citygml4j.ade.iur.model.urt.Translation;

import java.sql.*;

public class TranslationImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final TranslationToPublicTransitImporter translationToPublicTransitImporter;

    private int batchCounter;

    public TranslationImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRANSLATION)) + " " +
                "(id, tablename, tablename_codespace, fieldname, language, language_codespace, translation," +
                "fieldvalue, recordsubid) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        translationToPublicTransitImporter = manager.getImporter(TranslationToPublicTransitImporter.class);
    }

    public void doImport(Translation translation, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (translation.getTableName() != null && translation.getTableName().isSetValue()) {
            ps.setString(2, translation.getTableName().getValue());
            ps.setString(3, translation.getTableName().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, translation.getFieldName());

        if (translation.getLanguage() != null && translation.getLanguage().isSetValue()) {
            ps.setString(5, translation.getLanguage().getValue());
            ps.setString(6, translation.getLanguage().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        ps.setString(7, translation.getTranslation());

        ps.setString(8, translation.getFieldValue());

        ps.setString(9, translation.getRecordSubId());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TRANSLATION));

        if (translation.getRecordId() != null) {
            PublicTransitProperty property = translation.getRecordId();
            PublicTransit cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                translationToPublicTransitImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRANSLATI_TO_PUBLICTRA), objectId, "translation_id", href, "publictransit_id");
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
