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
import org.citygml4j.ade.iur.model.urt.FeedInfo;

import java.sql.*;

public class FeedInfoImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public FeedInfoImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FEEDINFO)) + " " +
                "(id, publishername, publisherurl, language, language_codespace, defaultlanguage," +
                "defaultlanguage_codespace, startdate, enddate, version, contactemail, contacturl, detailedinfo) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(FeedInfo feedInfo, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, feedInfo.getPublisherName());

        ps.setString(3, feedInfo.getPublisherUrl());

        if (feedInfo.getLanguage() != null && feedInfo.getLanguage().isSetValue()) {
            ps.setString(4, feedInfo.getLanguage().getValue());
            ps.setString(5, feedInfo.getLanguage().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (feedInfo.getDefaultLanguage() != null && feedInfo.getDefaultLanguage().isSetValue()) {
            ps.setString(6, feedInfo.getDefaultLanguage().getValue());
            ps.setString(7, feedInfo.getDefaultLanguage().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (feedInfo.getStartDate() != null)
            ps.setDate(8, Date.valueOf(feedInfo.getStartDate()));
        else
            ps.setNull(8, Types.DATE);

        if (feedInfo.getEndDate() != null)
            ps.setDate(9, Date.valueOf(feedInfo.getEndDate()));
        else
            ps.setNull(9, Types.DATE);

        ps.setString(10, feedInfo.getVersion());

        ps.setString(11, feedInfo.getContactEmail());

        ps.setString(12, feedInfo.getContactURL());

        ps.setString(13, feedInfo.getDetailedInfo());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FEEDINFO));
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
