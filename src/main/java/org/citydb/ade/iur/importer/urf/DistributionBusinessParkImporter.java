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
import org.citygml4j.ade.iur.model.urf.DistributionBusinessPark;

import java.sql.*;

public class DistributionBusinessParkImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFacilityImporter urbanFacilityImporter;

    private int batchCounter;

    public DistributionBusinessParkImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DISTRIBUTIONBUSINESSPA)) + " " +
                "(id, distributionbusinesspark, publicandutilityfacilities, buildingcoveragerate, floorarearate," +
                "maximumbuildingheight, maximumbuildingheight_uom, minimumbuildingheight, minimumbuildingheight_uom," +
                "setbacksize, scheduledexecutor) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFacilityImporter = manager.getImporter(UrbanFacilityImporter.class);
    }

    public void doImport(DistributionBusinessPark distributionBusinessPark, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFacilityImporter.doImport(distributionBusinessPark, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (distributionBusinessPark.getDistributionBusinessPark() != null && distributionBusinessPark.getDistributionBusinessPark().isSetValue())
            ps.setString(2, distributionBusinessPark.getDistributionBusinessPark().getValue());
        else
            ps.setNull(2, Types.VARCHAR);

        if (distributionBusinessPark.getPublicAndUtilityFacilities() != null && distributionBusinessPark.getPublicAndUtilityFacilities().isSetValue())
            ps.setString(3, distributionBusinessPark.getPublicAndUtilityFacilities().getValue());
        else
            ps.setNull(3, Types.VARCHAR);

        if (distributionBusinessPark.getBuildingCoverageRate() != null)
            ps.setDouble(4, distributionBusinessPark.getBuildingCoverageRate());
        else
            ps.setNull(4, Types.DOUBLE);

        if (distributionBusinessPark.getFloorAreaRate() != null)
            ps.setDouble(5, distributionBusinessPark.getFloorAreaRate());
        else
            ps.setNull(5, Types.DOUBLE);

        if (distributionBusinessPark.getMaximumBuildingHeight() != null && distributionBusinessPark.getMaximumBuildingHeight().isSetValue()) {
            ps.setDouble(6, distributionBusinessPark.getMaximumBuildingHeight().getValue());
            ps.setString(7, distributionBusinessPark.getMaximumBuildingHeight().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (distributionBusinessPark.getMinimumBuildingHeight() != null && distributionBusinessPark.getMinimumBuildingHeight().isSetValue()) {
            ps.setDouble(8, distributionBusinessPark.getMinimumBuildingHeight().getValue());
            ps.setString(9, distributionBusinessPark.getMinimumBuildingHeight().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (distributionBusinessPark.getSetbackSize() != null && distributionBusinessPark.getSetbackSize().isSetValue())
            ps.setString(10, distributionBusinessPark.getSetbackSize().getValue());
        else
            ps.setNull(10, Types.VARCHAR);

        ps.setString(11, distributionBusinessPark.getScheduledExecutor());

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
