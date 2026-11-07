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
import org.citygml4j.ade.iur.model.uro.BuildingIDAttribute;

import java.sql.*;

public class BuildingIDAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final BuildingAttributeImporter buildingAttributeImporter;

    private int batchCounter;

    public BuildingIDAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGIDATTRIBUTE)) + " " +
                "(id, buildingid, branchid, partid, prefecture, prefecture_codespace, city, city_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        buildingAttributeImporter = manager.getImporter(BuildingAttributeImporter.class);
    }

    public long doImport(BuildingIDAttribute buildingIDAttribute) throws CityGMLImportException, SQLException {
        long objectId = buildingAttributeImporter.doImport(buildingIDAttribute);
        ps.setLong(1, objectId);

        ps.setString(2, buildingIDAttribute.getBuildingID());

        if (buildingIDAttribute.getBranchID() != null)
            ps.setInt(3, buildingIDAttribute.getBranchID());
        else
            ps.setNull(3, Types.INTEGER);

        if (buildingIDAttribute.getPartID() != null)
            ps.setInt(4, buildingIDAttribute.getPartID());
        else
            ps.setNull(4, Types.INTEGER);

        if (buildingIDAttribute.getPrefecture() != null && buildingIDAttribute.getPrefecture().isSetValue()) {
            ps.setString(5, buildingIDAttribute.getPrefecture().getValue());
            ps.setString(6, buildingIDAttribute.getPrefecture().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (buildingIDAttribute.getCity() != null && buildingIDAttribute.getCity().isSetValue()) {
            ps.setString(7, buildingIDAttribute.getCity().getValue());
            ps.setString(8, buildingIDAttribute.getCity().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDINGIDATTRIBUTE));

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
