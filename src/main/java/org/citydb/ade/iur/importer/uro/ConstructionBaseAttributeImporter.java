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
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.ConstructionBaseAttribute;

import java.sql.*;
import java.time.LocalDate;

public class ConstructionBaseAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public ConstructionBaseAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CONSTRUCTIONBASEATTRIB)) + " " +
                "(id, admintype, admintype_codespace, administorator, adminoffice, operatortype," +
                "operatortype_codespace, installertype, installertype_codespace, installer, structureordinance," +
                "specification, kana, constructionstartyear, completionyear, facilityage, update_, purpose," +
                "purpose_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(ConstructionBaseAttribute constructionBaseAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.CONSTRUCTIONBASEAT_SEQ));
        ps.setLong(1, objectId);

        if (constructionBaseAttribute.getAdminType() != null && constructionBaseAttribute.getAdminType().isSetValue()) {
            ps.setString(2, constructionBaseAttribute.getAdminType().getValue());
            ps.setString(3, constructionBaseAttribute.getAdminType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, constructionBaseAttribute.getAdministorator());

        ps.setString(5, constructionBaseAttribute.getAdminOffice());

        if (constructionBaseAttribute.getOperatorType() != null && constructionBaseAttribute.getOperatorType().isSetValue()) {
            ps.setString(6, constructionBaseAttribute.getOperatorType().getValue());
            ps.setString(7, constructionBaseAttribute.getOperatorType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (constructionBaseAttribute.getInstallerType() != null && constructionBaseAttribute.getInstallerType().isSetValue()) {
            ps.setString(8, constructionBaseAttribute.getInstallerType().getValue());
            ps.setString(9, constructionBaseAttribute.getInstallerType().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        ps.setString(10, constructionBaseAttribute.getInstaller());

        ps.setString(11, constructionBaseAttribute.getStructureOrdinance());

        ps.setString(12, constructionBaseAttribute.getSpecification());

        ps.setString(13, constructionBaseAttribute.getKana());

        if (constructionBaseAttribute.getConstructionStartYear() != null)
            ps.setDate(14, Date.valueOf(LocalDate.of(constructionBaseAttribute.getConstructionStartYear().getValue(), 1, 1)));
        else
            ps.setNull(14, Types.DATE);

        if (constructionBaseAttribute.getCompletionYear() != null)
            ps.setDate(15, Date.valueOf(LocalDate.of(constructionBaseAttribute.getCompletionYear().getValue(), 1, 1)));
        else
            ps.setNull(15, Types.DATE);

        if (constructionBaseAttribute.getFacilityAge() != null)
            ps.setInt(16, constructionBaseAttribute.getFacilityAge());
        else
            ps.setNull(16, Types.INTEGER);

        if (constructionBaseAttribute.getUpdate() != null)
            ps.setDate(17, Date.valueOf(constructionBaseAttribute.getUpdate()));
        else
            ps.setNull(17, Types.DATE);

        if (constructionBaseAttribute.getPurpose() != null && constructionBaseAttribute.getPurpose().isSetValue()) {
            ps.setString(18, constructionBaseAttribute.getPurpose().getValue());
            ps.setString(19, constructionBaseAttribute.getPurpose().getCodeSpace());
        } else {
            ps.setNull(18, Types.VARCHAR);
            ps.setNull(19, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.CONSTRUCTIONBASEATTRIB));

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
