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
import org.citygml4j.ade.iur.model.urf.DisasterDamage;

import java.sql.*;

public class DisasterDamageImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionImporter urbanFunctionImporter;

    private int batchCounter;

    public DisasterDamageImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DISASTERDAMAGE)) + " " +
                "(id, id_, date_, damagedarea, damagedarea_uom, numberofdamagedhouses, numberofhousesfloodedabovefl," +
                "numberofhousesfloodedbelowfl, maximumrainfallperhour, totalrainfall) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFunctionImporter = manager.getImporter(UrbanFunctionImporter.class);
    }

    public void doImport(DisasterDamage disasterDamage, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFunctionImporter.doImport(disasterDamage, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, disasterDamage.getIdentifier());

        if (disasterDamage.getDate() != null)
            ps.setDate(3, Date.valueOf(disasterDamage.getDate()));
        else
            ps.setNull(3, Types.DATE);

        if (disasterDamage.getDamagedArea() != null && disasterDamage.getDamagedArea().isSetValue()) {
            ps.setDouble(4, disasterDamage.getDamagedArea().getValue());
            ps.setString(5, disasterDamage.getDamagedArea().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (disasterDamage.getNumberOfDamagedHouses() != null)
            ps.setInt(6, disasterDamage.getNumberOfDamagedHouses());
        else
            ps.setNull(6, Types.INTEGER);

        if (disasterDamage.getNumberOfHousesFloodedAboveFloorLevel() != null)
            ps.setInt(7, disasterDamage.getNumberOfHousesFloodedAboveFloorLevel());
        else
            ps.setNull(7, Types.INTEGER);

        if (disasterDamage.getNumberOfHousesFloodedBelowFloorLevel() != null)
            ps.setInt(8, disasterDamage.getNumberOfHousesFloodedBelowFloorLevel());
        else
            ps.setNull(8, Types.INTEGER);

        if (disasterDamage.getMaximumRainfallPerHour() != null)
            ps.setInt(9, disasterDamage.getMaximumRainfallPerHour());
        else
            ps.setNull(9, Types.INTEGER);

        if (disasterDamage.getTotalRainfall() != null)
            ps.setInt(10, disasterDamage.getTotalRainfall());
        else
            ps.setNull(10, Types.INTEGER);

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
