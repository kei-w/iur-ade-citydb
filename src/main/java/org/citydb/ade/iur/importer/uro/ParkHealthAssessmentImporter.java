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
import org.citygml4j.ade.iur.model.uro.ParkHealthAssessment;

import java.sql.*;
import java.time.LocalDate;

public class ParkHealthAssessmentImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public ParkHealthAssessmentImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PARKHEALTHASSESSMENT)) + " " +
                "(id, parkfacility_parkhealthas_id, assessmentfiscalyear, deteriorationstatus, condition," +
                "condition_codespace, urgency, urgency_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(ParkHealthAssessment parkHealthAssessment, long parentId) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.PARKHEALTHASSESSME_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, parentId);

        if (parkHealthAssessment.getAssessmentFiscalYear() != null)
            ps.setDate(3, Date.valueOf(LocalDate.of(parkHealthAssessment.getAssessmentFiscalYear().getValue(), 1, 1)));
        else
            ps.setNull(3, Types.DATE);

        ps.setString(4, parkHealthAssessment.getDeteriorationStatus());

        if (parkHealthAssessment.getCondition() != null && parkHealthAssessment.getCondition().isSetValue()) {
            ps.setString(5, parkHealthAssessment.getCondition().getValue());
            ps.setString(6, parkHealthAssessment.getCondition().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (parkHealthAssessment.getUrgency() != null && parkHealthAssessment.getUrgency().isSetValue()) {
            ps.setString(7, parkHealthAssessment.getUrgency().getValue());
            ps.setString(8, parkHealthAssessment.getUrgency().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PARKHEALTHASSESSMENT));

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
