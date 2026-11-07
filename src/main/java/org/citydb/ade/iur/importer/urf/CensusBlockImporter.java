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
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.CensusBlock;

import java.sql.*;

public class CensusBlockImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionImporter urbanFunctionImporter;
    private final PopulationTypeImporter populationTypeImporter;
    private final DaytimePopulationTypeImporter daytimePopulationTypeImporter;
    private final EconomicActivityTypeImporter economicActivityTypeImporter;

    private int batchCounter;

    public CensusBlockImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CENSUSBLOCK)) + " " +
                "(id, townname, blockname, populationtype_id, daytimepopulationtype_id, economicactivitytype_id) " +
                "values (?, ?, ?, ?, ?, ?)");

        urbanFunctionImporter = manager.getImporter(UrbanFunctionImporter.class);
        populationTypeImporter = manager.getImporter(PopulationTypeImporter.class);
        daytimePopulationTypeImporter = manager.getImporter(DaytimePopulationTypeImporter.class);
        economicActivityTypeImporter = manager.getImporter(EconomicActivityTypeImporter.class);
    }

    public void doImport(CensusBlock censusBlock, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFunctionImporter.doImport(censusBlock, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, censusBlock.getTownName());

        ps.setString(3, censusBlock.getBlockName());

        long populationTypeId = 0;
        if (censusBlock.getPopulationType() != null && censusBlock.getPopulationType().isSetObject())
            populationTypeId = populationTypeImporter.doImport(censusBlock.getPopulationType().getObject());

        if (populationTypeId != 0)
            ps.setLong(4, populationTypeId);
        else
            ps.setNull(4, Types.NULL);

        long daytimePopulationTypeId = 0;
        if (censusBlock.getDaytimePopulationType() != null && censusBlock.getDaytimePopulationType().isSetObject())
            daytimePopulationTypeId = daytimePopulationTypeImporter.doImport(censusBlock.getDaytimePopulationType().getObject());

        if (daytimePopulationTypeId != 0)
            ps.setLong(5, daytimePopulationTypeId);
        else
            ps.setNull(5, Types.NULL);

        long economicActivityTypeId = 0;
        if (censusBlock.getEconomicActivityType() != null && censusBlock.getEconomicActivityType().isSetObject())
            economicActivityTypeId = economicActivityTypeImporter.doImport(censusBlock.getEconomicActivityType().getObject());

        if (economicActivityTypeId != 0)
            ps.setLong(6, economicActivityTypeId);
        else
            ps.setNull(6, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);
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
