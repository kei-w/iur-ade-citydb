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
import org.citygml4j.ade.iur.model.urf.UseDistrict;

import java.sql.*;

public class UseDistrictImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final DistrictsAndZonesImporter districtsAndZonesImporter;

    private int batchCounter;

    public UseDistrictImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.USEDISTRICT)) + " " +
                "(id, floorarearate, minimumsitearea, minimumsitearea_uom, buildingcoveragerate, wallsetbackdistance," +
                "buildingheightlimits, buildingheightlimits_uom, buildingrestrictions, otherrestrictions," +
                "setbackrestrictions, frontroadrestrictions, adjacentlandrestrictions, northdirectionrestrictions," +
                "shaderegulation) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        districtsAndZonesImporter = manager.getImporter(DistrictsAndZonesImporter.class);
    }

    public void doImport(UseDistrict useDistrict, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        districtsAndZonesImporter.doImport(useDistrict, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (useDistrict.getFloorAreaRate() != null)
            ps.setInt(2, useDistrict.getFloorAreaRate());
        else
            ps.setNull(2, Types.INTEGER);

        if (useDistrict.getMinimumSiteArea() != null && useDistrict.getMinimumSiteArea().isSetValue()) {
            ps.setDouble(3, useDistrict.getMinimumSiteArea().getValue());
            ps.setString(4, useDistrict.getMinimumSiteArea().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (useDistrict.getBuildingCoverageRate() != null)
            ps.setInt(5, useDistrict.getBuildingCoverageRate());
        else
            ps.setNull(5, Types.INTEGER);

        if (useDistrict.getWallSetbackDistance() != null && useDistrict.getWallSetbackDistance().isSetValue())
            ps.setString(6, useDistrict.getWallSetbackDistance().getValue());
        else
            ps.setNull(6, Types.VARCHAR);

        if (useDistrict.getBuildingHeightLimits() != null && useDistrict.getBuildingHeightLimits().isSetValue()) {
            ps.setDouble(7, useDistrict.getBuildingHeightLimits().getValue());
            ps.setString(8, useDistrict.getBuildingHeightLimits().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (useDistrict.getBuildingRestrictions() != null && useDistrict.getBuildingRestrictions().isSetValue())
            ps.setString(9, useDistrict.getBuildingRestrictions().getValue());
        else
            ps.setNull(9, Types.VARCHAR);

        if (useDistrict.getOtherRestrictions() != null && useDistrict.getOtherRestrictions().isSetValue())
            ps.setString(10, useDistrict.getOtherRestrictions().getValue());
        else
            ps.setNull(10, Types.VARCHAR);

        if (useDistrict.getSetbackRestrictions() != null && useDistrict.getSetbackRestrictions().isSetValue())
            ps.setString(11, useDistrict.getSetbackRestrictions().getValue());
        else
            ps.setNull(11, Types.VARCHAR);

        if (useDistrict.getFrontRoadRestrictions() != null && useDistrict.getFrontRoadRestrictions().isSetValue())
            ps.setString(12, useDistrict.getFrontRoadRestrictions().getValue());
        else
            ps.setNull(12, Types.VARCHAR);

        if (useDistrict.getAdjacentLandRestrictions() != null && useDistrict.getAdjacentLandRestrictions().isSetValue())
            ps.setString(13, useDistrict.getAdjacentLandRestrictions().getValue());
        else
            ps.setNull(13, Types.VARCHAR);

        if (useDistrict.getNorthDirectionRestrictions() != null && useDistrict.getNorthDirectionRestrictions().isSetValue())
            ps.setString(14, useDistrict.getNorthDirectionRestrictions().getValue());
        else
            ps.setNull(14, Types.VARCHAR);

        if (useDistrict.getShadeRegulation() != null && useDistrict.getShadeRegulation().isSetValue())
            ps.setString(15, useDistrict.getShadeRegulation().getValue());
        else
            ps.setNull(15, Types.VARCHAR);

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
