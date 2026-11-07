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
import org.citygml4j.ade.iur.model.uro.ConstructionRiskAssessmentAttribute;

import java.sql.*;
import java.time.LocalDate;

public class ConstructionRiskAssessmentAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public ConstructionRiskAssessmentAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CONSTRUCTIONRISKASSESS)) + " " +
                "(id, surveyyear, risktype, risktype_codespace, status, status_codespace, referencedate) " +
                "values (?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(ConstructionRiskAssessmentAttribute constructionRiskAssessmentAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.CONSTRUCTIONRISKAS_SEQ));
        ps.setLong(1, objectId);

        if (constructionRiskAssessmentAttribute.getSurveyYear() != null)
            ps.setDate(2, Date.valueOf(LocalDate.of(constructionRiskAssessmentAttribute.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(2, Types.DATE);

        if (constructionRiskAssessmentAttribute.getRiskType() != null && constructionRiskAssessmentAttribute.getRiskType().isSetValue()) {
            ps.setString(3, constructionRiskAssessmentAttribute.getRiskType().getValue());
            ps.setString(4, constructionRiskAssessmentAttribute.getRiskType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (constructionRiskAssessmentAttribute.getStatus() != null && constructionRiskAssessmentAttribute.getStatus().isSetValue()) {
            ps.setString(5, constructionRiskAssessmentAttribute.getStatus().getValue());
            ps.setString(6, constructionRiskAssessmentAttribute.getStatus().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (constructionRiskAssessmentAttribute.getReferenceDate() != null)
            ps.setDate(7, Date.valueOf(constructionRiskAssessmentAttribute.getReferenceDate()));
        else
            ps.setNull(7, Types.DATE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.CONSTRUCTIONRISKASSESS));

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
