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
import org.citygml4j.ade.iur.model.urf.DaytimePopulationType;

import java.sql.*;

public class DaytimePopulationTypeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public DaytimePopulationTypeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DAYTIMEPOPULATIONTYPE)) + " " +
                "(id, daytimepopulation, daytimepopulationdensity, daytimeofficecommuter, daytimeschoolcommuter," +
                "daytimeunder15, daytimeover15, agriculturalworker, noncommuter) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(DaytimePopulationType daytimePopulationType) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.DAYTIMEPOPULATIONT_SEQ));
        ps.setLong(1, objectId);

        if (daytimePopulationType.getDaytimePopulation() != null)
            ps.setInt(2, daytimePopulationType.getDaytimePopulation());
        else
            ps.setNull(2, Types.INTEGER);

        if (daytimePopulationType.getDaytimePopulationDensity() != null)
            ps.setDouble(3, daytimePopulationType.getDaytimePopulationDensity());
        else
            ps.setNull(3, Types.DOUBLE);

        if (daytimePopulationType.getDaytimeOfficeCommuter() != null)
            ps.setInt(4, daytimePopulationType.getDaytimeOfficeCommuter());
        else
            ps.setNull(4, Types.INTEGER);

        if (daytimePopulationType.getDaytimeSchoolCommuter() != null)
            ps.setInt(5, daytimePopulationType.getDaytimeSchoolCommuter());
        else
            ps.setNull(5, Types.INTEGER);

        if (daytimePopulationType.getDaytimeUnder15() != null)
            ps.setInt(6, daytimePopulationType.getDaytimeUnder15());
        else
            ps.setNull(6, Types.INTEGER);

        if (daytimePopulationType.getDaytimeOver15() != null)
            ps.setInt(7, daytimePopulationType.getDaytimeOver15());
        else
            ps.setNull(7, Types.INTEGER);

        if (daytimePopulationType.getAgriculturalWorker() != null)
            ps.setInt(8, daytimePopulationType.getAgriculturalWorker());
        else
            ps.setNull(8, Types.INTEGER);

        if (daytimePopulationType.getNonCommuter() != null)
            ps.setInt(9, daytimePopulationType.getNonCommuter());
        else
            ps.setNull(9, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.DAYTIMEPOPULATIONTYPE));

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
