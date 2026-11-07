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
import org.citygml4j.ade.iur.model.urf.CollectiveUrbanDisasterPreventionFacilities;

import java.sql.*;

public class CollectiveUrbanDisasterPreventionFacilitiesImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFacilityImporter urbanFacilityImporter;

    private int batchCounter;

    public CollectiveUrbanDisasterPreventionFacilitiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.COLLECTIVEURBANDISASTE)) + " " +
                "(id, specificutilityandpublicfaci, maximumbuildingheight, maximumbuildingheight_uom," +
                "minimumbuildingheight, minimumbuildingheight_uom, maximumfloorarearate, minimumfloorarearate," +
                "maximumbuildingcoveragerate) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFacilityImporter = manager.getImporter(UrbanFacilityImporter.class);
    }

    public void doImport(CollectiveUrbanDisasterPreventionFacilities collectiveUrbanDisasterPreventionFacilities, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFacilityImporter.doImport(collectiveUrbanDisasterPreventionFacilities, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (collectiveUrbanDisasterPreventionFacilities.getSpecificUtilityAndPublicFacilities() != null && collectiveUrbanDisasterPreventionFacilities.getSpecificUtilityAndPublicFacilities().isSetValue())
            ps.setString(2, collectiveUrbanDisasterPreventionFacilities.getSpecificUtilityAndPublicFacilities().getValue());
        else
            ps.setNull(2, Types.VARCHAR);

        if (collectiveUrbanDisasterPreventionFacilities.getMaximumBuildingHeight() != null && collectiveUrbanDisasterPreventionFacilities.getMaximumBuildingHeight().isSetValue()) {
            ps.setDouble(3, collectiveUrbanDisasterPreventionFacilities.getMaximumBuildingHeight().getValue());
            ps.setString(4, collectiveUrbanDisasterPreventionFacilities.getMaximumBuildingHeight().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (collectiveUrbanDisasterPreventionFacilities.getMinimumBuildingHeight() != null && collectiveUrbanDisasterPreventionFacilities.getMinimumBuildingHeight().isSetValue()) {
            ps.setDouble(5, collectiveUrbanDisasterPreventionFacilities.getMinimumBuildingHeight().getValue());
            ps.setString(6, collectiveUrbanDisasterPreventionFacilities.getMinimumBuildingHeight().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (collectiveUrbanDisasterPreventionFacilities.getMaximumFloorAreaRate() != null)
            ps.setDouble(7, collectiveUrbanDisasterPreventionFacilities.getMaximumFloorAreaRate());
        else
            ps.setNull(7, Types.DOUBLE);

        if (collectiveUrbanDisasterPreventionFacilities.getMinimumFloorAreaRate() != null)
            ps.setDouble(8, collectiveUrbanDisasterPreventionFacilities.getMinimumFloorAreaRate());
        else
            ps.setNull(8, Types.DOUBLE);

        if (collectiveUrbanDisasterPreventionFacilities.getMaximumBuildingCoverageRate() != null)
            ps.setDouble(9, collectiveUrbanDisasterPreventionFacilities.getMaximumBuildingCoverageRate());
        else
            ps.setNull(9, Types.DOUBLE);

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
