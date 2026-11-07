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
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urg.Population;
import org.citygml4j.ade.iur.model.urg.PopulationByAgeAndSexProperty;

import java.sql.*;

public class PopulationImporter implements StatisticalGridModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final StatisticalGridImporter statisticalGridImporter;
    private final PopulationToPopulationByAgeAndSexImporter populationToPopulationByAgeAndSexImporter;
    private final PopulationByAgeAndSexImporter populationByAgeAndSexImporter;

    private int batchCounter;

    public PopulationImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.POPULATION)) + " " +
                "(id, total, daytimepopulation, daytimepopulationdensity, daytimeofficecommuter," +
                "daytimeschoolcommuter, daytimeunder15, daytimeover15, agriculturalworker, noncommuter," +
                "naturalincrease, births, deaths, socialincrease, movefrom, moveto, increasement, malepopulation," +
                "femalepopulation) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statisticalGridImporter = manager.getImporter(StatisticalGridImporter.class);
        populationToPopulationByAgeAndSexImporter = manager.getImporter(PopulationToPopulationByAgeAndSexImporter.class);
        populationByAgeAndSexImporter = manager.getImporter(PopulationByAgeAndSexImporter.class);
    }

    public void doImport(Population population, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        statisticalGridImporter.doImport(population, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (population.getTotal() != null)
            ps.setInt(2, population.getTotal());
        else
            ps.setNull(2, Types.INTEGER);

        if (population.getDaytimePopulation() != null)
            ps.setInt(3, population.getDaytimePopulation());
        else
            ps.setNull(3, Types.INTEGER);

        if (population.getDaytimePopulationDensity() != null)
            ps.setDouble(4, population.getDaytimePopulationDensity());
        else
            ps.setNull(4, Types.DOUBLE);

        if (population.getDaytimeOfficeCommuter() != null)
            ps.setInt(5, population.getDaytimeOfficeCommuter());
        else
            ps.setNull(5, Types.INTEGER);

        if (population.getDaytimeSchoolCommuter() != null)
            ps.setInt(6, population.getDaytimeSchoolCommuter());
        else
            ps.setNull(6, Types.INTEGER);

        if (population.getDaytimeUnder15() != null)
            ps.setInt(7, population.getDaytimeUnder15());
        else
            ps.setNull(7, Types.INTEGER);

        if (population.getDaytimeOver15() != null)
            ps.setInt(8, population.getDaytimeOver15());
        else
            ps.setNull(8, Types.INTEGER);

        if (population.getAgriculturalWorker() != null)
            ps.setInt(9, population.getAgriculturalWorker());
        else
            ps.setNull(9, Types.INTEGER);

        if (population.getNonCommuter() != null)
            ps.setInt(10, population.getNonCommuter());
        else
            ps.setNull(10, Types.INTEGER);

        if (population.getNaturalIncrease() != null)
            ps.setInt(11, population.getNaturalIncrease());
        else
            ps.setNull(11, Types.INTEGER);

        if (population.getBirths() != null)
            ps.setInt(12, population.getBirths());
        else
            ps.setNull(12, Types.INTEGER);

        if (population.getDeaths() != null)
            ps.setInt(13, population.getDeaths());
        else
            ps.setNull(13, Types.INTEGER);

        if (population.getSocialIncrease() != null)
            ps.setInt(14, population.getSocialIncrease());
        else
            ps.setNull(14, Types.INTEGER);

        if (population.getMoveFrom() != null)
            ps.setInt(15, population.getMoveFrom());
        else
            ps.setNull(15, Types.INTEGER);

        if (population.getMoveTo() != null)
            ps.setInt(16, population.getMoveTo());
        else
            ps.setNull(16, Types.INTEGER);

        if (population.getIncreasement() != null)
            ps.setInt(17, population.getIncreasement());
        else
            ps.setNull(17, Types.INTEGER);

        if (population.getMalePopulation() != null)
            ps.setInt(18, population.getMalePopulation());
        else
            ps.setNull(18, Types.INTEGER);

        if (population.getFemalePopulation() != null)
            ps.setInt(19, population.getFemalePopulation());
        else
            ps.setNull(19, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (PopulationByAgeAndSexProperty property : population.getPopulationByAgeAndSex()) {
            if (property.isSetObject()) {
                long populationByAgeAndSexId = populationByAgeAndSexImporter.doImport(property.getObject());
                populationToPopulationByAgeAndSexImporter.doImport(populationByAgeAndSexId, objectId);
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
