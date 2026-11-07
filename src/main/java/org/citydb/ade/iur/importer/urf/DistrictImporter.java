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
import org.citygml4j.ade.iur.model.urf.District;

import java.sql.*;

public class DistrictImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ZoneImporter zoneImporter;

    private int batchCounter;

    public DistrictImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DISTRICT)) + " " +
                "(id, buildingrestrictions, userestrictions, maximumfloorarearate, minimumfloorarearate," +
                "maximumbuildingcoveragerate, minimumbuildingcoveragerate, minimumsitearea, minimumsitearea_uom," +
                "minimumbuildingarea, minimumbuildingarea_uom, minimumgroundheight, minimumgroundheight_uom," +
                "setbacksize, structureplacementrestrictio, maximumbuildingheight, maximumbuildingheight_uom," +
                "minimumbuildingheight, minimumbuildingheight_uom, minimumfloorheight, minimumfloorheight_uom," +
                "buildingdesignrestriction, minimumgreeningrate, fenceguideline, restrictionsforfireprotectio," +
                "restrictionsfornoiseprotecti, minimumfrontagerate) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        zoneImporter = manager.getImporter(ZoneImporter.class);
    }

    public void doImport(District district, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        zoneImporter.doImport(district, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (district.getBuildingRestrictions() != null && district.getBuildingRestrictions().isSetValue())
            ps.setString(2, district.getBuildingRestrictions().getValue());
        else
            ps.setNull(2, Types.VARCHAR);

        if (district.getUseRestrictions() != null && district.getUseRestrictions().isSetValue())
            ps.setString(3, district.getUseRestrictions().getValue());
        else
            ps.setNull(3, Types.VARCHAR);

        if (district.getMaximumFloorAreaRate() != null)
            ps.setDouble(4, district.getMaximumFloorAreaRate());
        else
            ps.setNull(4, Types.DOUBLE);

        if (district.getMinimumFloorAreaRate() != null)
            ps.setDouble(5, district.getMinimumFloorAreaRate());
        else
            ps.setNull(5, Types.DOUBLE);

        if (district.getMaximumBuildingCoverageRate() != null)
            ps.setDouble(6, district.getMaximumBuildingCoverageRate());
        else
            ps.setNull(6, Types.DOUBLE);

        if (district.getMinimumBuildingCoverageRate() != null)
            ps.setDouble(7, district.getMinimumBuildingCoverageRate());
        else
            ps.setNull(7, Types.DOUBLE);

        if (district.getMinimumSiteArea() != null && district.getMinimumSiteArea().isSetValue()) {
            ps.setDouble(8, district.getMinimumSiteArea().getValue());
            ps.setString(9, district.getMinimumSiteArea().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (district.getMinimumBuildingArea() != null && district.getMinimumBuildingArea().isSetValue()) {
            ps.setDouble(10, district.getMinimumBuildingArea().getValue());
            ps.setString(11, district.getMinimumBuildingArea().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (district.getMinimumGroundHeight() != null && district.getMinimumGroundHeight().isSetValue()) {
            ps.setDouble(12, district.getMinimumGroundHeight().getValue());
            ps.setString(13, district.getMinimumGroundHeight().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (district.getSetbackSize() != null && district.getSetbackSize().isSetValue())
            ps.setString(14, district.getSetbackSize().getValue());
        else
            ps.setNull(14, Types.VARCHAR);

        if (district.getStructurePlacementRestrictions() != null && district.getStructurePlacementRestrictions().isSetValue())
            ps.setString(15, district.getStructurePlacementRestrictions().getValue());
        else
            ps.setNull(15, Types.VARCHAR);

        if (district.getMaximumBuildingHeight() != null && district.getMaximumBuildingHeight().isSetValue()) {
            ps.setDouble(16, district.getMaximumBuildingHeight().getValue());
            ps.setString(17, district.getMaximumBuildingHeight().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (district.getMinimumBuildingHeight() != null && district.getMinimumBuildingHeight().isSetValue()) {
            ps.setDouble(18, district.getMinimumBuildingHeight().getValue());
            ps.setString(19, district.getMinimumBuildingHeight().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (district.getMinimumFloorHeight() != null && district.getMinimumFloorHeight().isSetValue()) {
            ps.setDouble(20, district.getMinimumFloorHeight().getValue());
            ps.setString(21, district.getMinimumFloorHeight().getUom());
        } else {
            ps.setNull(20, Types.DOUBLE);
            ps.setNull(21, Types.VARCHAR);
        }

        if (district.getBuildingDesignRestriction() != null && district.getBuildingDesignRestriction().isSetValue())
            ps.setString(22, district.getBuildingDesignRestriction().getValue());
        else
            ps.setNull(22, Types.VARCHAR);

        if (district.getMinimumGreeningRate() != null)
            ps.setDouble(23, district.getMinimumGreeningRate());
        else
            ps.setNull(23, Types.DOUBLE);

        if (district.getFenceGuideline() != null && district.getFenceGuideline().isSetValue())
            ps.setString(24, district.getFenceGuideline().getValue());
        else
            ps.setNull(24, Types.VARCHAR);

        if (district.getRestrictionsForFireProtection() != null && district.getRestrictionsForFireProtection().isSetValue())
            ps.setString(25, district.getRestrictionsForFireProtection().getValue());
        else
            ps.setNull(25, Types.VARCHAR);

        if (district.getRestrictionsForNoiseProtection() != null && district.getRestrictionsForNoiseProtection().isSetValue())
            ps.setString(26, district.getRestrictionsForNoiseProtection().getValue());
        else
            ps.setNull(26, Types.VARCHAR);

        if (district.getMinimumFrontageRate() != null)
            ps.setDouble(27, district.getMinimumFrontageRate());
        else
            ps.setNull(27, Types.DOUBLE);

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
