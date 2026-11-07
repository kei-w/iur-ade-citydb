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

package org.citydb.ade.iur.importer.urg;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urg.NumberOfHouseholds;

import java.sql.*;

public class NumberOfHouseholdsImporter implements StatisticalGridModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public NumberOfHouseholdsImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.NUMBEROFHOUSEHOLDS)) + " " +
                "(id, households_numberofhouseh_id, households_numberofhouseh__2, class, class_codespace, number_) " +
                "values (?, ?, ?, ?, ?, ?)");
    }

    public long doImport(NumberOfHouseholds numberOfHouseholds, long parentId, String parentColumn) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.NUMBEROFHOUSEHOLDS_SEQ));
        ps.setLong(1, objectId);
        if ("households_numberofhouseh_id".equals(parentColumn))
            ps.setLong(2, parentId);
        else
            ps.setNull(2, Types.NULL);
        if ("households_numberofhouseh__2".equals(parentColumn))
            ps.setLong(3, parentId);
        else
            ps.setNull(3, Types.NULL);

        if (numberOfHouseholds.getClassifier() != null && numberOfHouseholds.getClassifier().isSetValue()) {
            ps.setString(4, numberOfHouseholds.getClassifier().getValue());
            ps.setString(5, numberOfHouseholds.getClassifier().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (numberOfHouseholds.getNumber() != null)
            ps.setInt(6, numberOfHouseholds.getNumber());
        else
            ps.setNull(6, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.NUMBEROFHOUSEHOLDS));

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
