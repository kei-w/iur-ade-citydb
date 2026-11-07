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
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.RealEstateIDAttribute;

import java.sql.*;

public class RealEstateIDAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final BuildingAttributeImporter buildingAttributeImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public RealEstateIDAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.REALESTATEIDATTRIBUTE)) + " " +
                "(id, matchingscore, numberofbuildingunitownershi, numberofrealestateidofland, " +
                "realestateidofbuilding, realestateidofbuildingunitow, realestateidofland) " +
                "values (?, ?, ?, ?, ?, ?, ?)");

        buildingAttributeImporter = manager.getImporter(BuildingAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(RealEstateIDAttribute realEstateIDAttribute) throws CityGMLImportException, SQLException {
        long objectId = buildingAttributeImporter.doImport(realEstateIDAttribute);
        ps.setLong(1, objectId);

        if (realEstateIDAttribute.getMatchingScore() != null)
            ps.setInt(2, realEstateIDAttribute.getMatchingScore());
        else
            ps.setNull(2, Types.INTEGER);

        if (realEstateIDAttribute.getNumberOfBuildingUnitOwnership() != null)
            ps.setInt(3, realEstateIDAttribute.getNumberOfBuildingUnitOwnership());
        else
            ps.setNull(3, Types.INTEGER);

        if (realEstateIDAttribute.getNumberOfRealEstateIDOfLand() != null)
            ps.setInt(4, realEstateIDAttribute.getNumberOfRealEstateIDOfLand());
        else
            ps.setNull(4, Types.INTEGER);

        ps.setString(5, realEstateIDAttribute.getRealEstateIDOfBuilding());

        if (!realEstateIDAttribute.getRealEstateIDOfBuildingUnitOwnership().isEmpty())
            ps.setString(6, valueJoiner.join(realEstateIDAttribute.getRealEstateIDOfBuildingUnitOwnership()));
        else
            ps.setNull(6, Types.VARCHAR);

        if (!realEstateIDAttribute.getRealEstateIDOfLand().isEmpty())
            ps.setString(7, valueJoiner.join(realEstateIDAttribute.getRealEstateIDOfLand()));
        else
            ps.setNull(7, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.REALESTATEIDATTRIBUTE));

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
