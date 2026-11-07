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
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForTsunamiDisasterPrevention;

import java.sql.*;

public class CollectiveFacilitiesForTsunamiDisasterPreventionImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFacilityImporter urbanFacilityImporter;

    private int batchCounter;

    public CollectiveFacilitiesForTsunamiDisasterPreventionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.COLLECTIVEFACILITIESFO)) + " " +
                "(id, housingfacilities, supecificbusinessfacilities, publicfacilities, utilityfacilities," +
                "maximumbuildingheight, maximumbuildingheight_uom, minimumbuildingheight, minimumbuildingheight_uom," +
                "maximumfloorarearate, minimumfloorarearate, maximumbuildingcoveragerate) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFacilityImporter = manager.getImporter(UrbanFacilityImporter.class);
    }

    public void doImport(CollectiveFacilitiesForTsunamiDisasterPrevention collectiveFacilitiesForTsunamiDisasterPrevention, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFacilityImporter.doImport(collectiveFacilitiesForTsunamiDisasterPrevention, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getHousingFacilities() != null && collectiveFacilitiesForTsunamiDisasterPrevention.getHousingFacilities().isSetValue())
            ps.setString(2, collectiveFacilitiesForTsunamiDisasterPrevention.getHousingFacilities().getValue());
        else
            ps.setNull(2, Types.VARCHAR);

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getSupecificBusinessFacilities() != null && collectiveFacilitiesForTsunamiDisasterPrevention.getSupecificBusinessFacilities().isSetValue())
            ps.setString(3, collectiveFacilitiesForTsunamiDisasterPrevention.getSupecificBusinessFacilities().getValue());
        else
            ps.setNull(3, Types.VARCHAR);

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getPublicFacilities() != null && collectiveFacilitiesForTsunamiDisasterPrevention.getPublicFacilities().isSetValue())
            ps.setString(4, collectiveFacilitiesForTsunamiDisasterPrevention.getPublicFacilities().getValue());
        else
            ps.setNull(4, Types.VARCHAR);

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getUtilityFacilities() != null && collectiveFacilitiesForTsunamiDisasterPrevention.getUtilityFacilities().isSetValue())
            ps.setString(5, collectiveFacilitiesForTsunamiDisasterPrevention.getUtilityFacilities().getValue());
        else
            ps.setNull(5, Types.VARCHAR);

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumBuildingHeight() != null && collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumBuildingHeight().isSetValue()) {
            ps.setDouble(6, collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumBuildingHeight().getValue());
            ps.setString(7, collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumBuildingHeight().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getMinimumBuildingHeight() != null && collectiveFacilitiesForTsunamiDisasterPrevention.getMinimumBuildingHeight().isSetValue()) {
            ps.setDouble(8, collectiveFacilitiesForTsunamiDisasterPrevention.getMinimumBuildingHeight().getValue());
            ps.setString(9, collectiveFacilitiesForTsunamiDisasterPrevention.getMinimumBuildingHeight().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumFloorAreaRate() != null)
            ps.setDouble(10, collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumFloorAreaRate());
        else
            ps.setNull(10, Types.DOUBLE);

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getMinimumFloorAreaRate() != null)
            ps.setDouble(11, collectiveFacilitiesForTsunamiDisasterPrevention.getMinimumFloorAreaRate());
        else
            ps.setNull(11, Types.DOUBLE);

        if (collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumBuildingCoverageRate() != null)
            ps.setDouble(12, collectiveFacilitiesForTsunamiDisasterPrevention.getMaximumBuildingCoverageRate());
        else
            ps.setNull(12, Types.DOUBLE);

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
