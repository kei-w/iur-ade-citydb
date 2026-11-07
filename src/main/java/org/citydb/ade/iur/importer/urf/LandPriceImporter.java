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
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.urf.LandPrice;
import org.citygml4j.ade.iur.model.urf.ValueByCodesProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;
import java.time.LocalDate;

public class LandPriceImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionImporter urbanFunctionImporter;
    private final ValueByCodesImporter valueByCodesImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public LandPriceImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.LANDPRICE_2)) + " " +
                "(id, pointcode, pointcodeoflastyear, fiscalyear, landprice, variability, isselected," +
                "isaddresschanged, isacreagechanged, isusechanged, isbuildingstructurechanged," +
                "issupplyfacilitieschanged, isdistancefromstationchanged, isusedistrictchanged," +
                "isfirepreventionchanged, isurbanplanareachanged, isbuildingcoverageratechange," +
                "isfloorarearatechanged, citycode, citycode_codespace, cityname, location, address, acreage," +
                "acreage_uom, usestatus, usestatusdescription, usetype, buildingstructure, water, gas, sewerage," +
                "shape, frontagerate, depthrate, storeysaboveground, storeysbelowground, frontageroad," +
                "frontageroaddirection, frontageroadwidth, frontageroadwidth_uom, stationsquare," +
                "frontageroadsurface, sideroad, sideroaddirection, transportationfacilities, surroundings," +
                "stationname, distancefromstation, distancefromstation_uom, usedistrict, usedistrict_codespace," +
                "fireprevention, urbanplanareatype, heightcontrol, forest, park, buildingcoveragerate," +
                "floorarearate, extrafloorarearate, issame, selectionstatus) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFunctionImporter = manager.getImporter(UrbanFunctionImporter.class);
        valueByCodesImporter = manager.getImporter(ValueByCodesImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(LandPrice landPrice, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFunctionImporter.doImport(landPrice, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, landPrice.getPointCode());

        ps.setString(3, landPrice.getPointCodeOfLastYear());

        if (landPrice.getFiscalYear() != null)
            ps.setDate(4, Date.valueOf(LocalDate.of(landPrice.getFiscalYear().getValue(), 1, 1)));
        else
            ps.setNull(4, Types.DATE);

        if (landPrice.getLandPrice() != null)
            ps.setInt(5, landPrice.getLandPrice());
        else
            ps.setNull(5, Types.INTEGER);

        if (landPrice.getVariability() != null)
            ps.setDouble(6, landPrice.getVariability());
        else
            ps.setNull(6, Types.DOUBLE);

        if (landPrice.getIsSelected() != null)
            ps.setInt(7, landPrice.getIsSelected() ? 1 : 0);
        else
            ps.setNull(7, Types.INTEGER);

        if (landPrice.getIsAddressChanged() != null)
            ps.setInt(8, landPrice.getIsAddressChanged() ? 1 : 0);
        else
            ps.setNull(8, Types.INTEGER);

        if (landPrice.getIsAcreageChanged() != null)
            ps.setInt(9, landPrice.getIsAcreageChanged() ? 1 : 0);
        else
            ps.setNull(9, Types.INTEGER);

        if (landPrice.getIsUseChanged() != null)
            ps.setInt(10, landPrice.getIsUseChanged() ? 1 : 0);
        else
            ps.setNull(10, Types.INTEGER);

        if (landPrice.getIsBuildingStructureChanged() != null)
            ps.setInt(11, landPrice.getIsBuildingStructureChanged() ? 1 : 0);
        else
            ps.setNull(11, Types.INTEGER);

        if (landPrice.getIsSupplyFacilitiesChanged() != null)
            ps.setInt(12, landPrice.getIsSupplyFacilitiesChanged() ? 1 : 0);
        else
            ps.setNull(12, Types.INTEGER);

        if (landPrice.getIsDistanceFromStationChanged() != null)
            ps.setInt(13, landPrice.getIsDistanceFromStationChanged() ? 1 : 0);
        else
            ps.setNull(13, Types.INTEGER);

        if (landPrice.getIsUseDistrictChanged() != null)
            ps.setInt(14, landPrice.getIsUseDistrictChanged() ? 1 : 0);
        else
            ps.setNull(14, Types.INTEGER);

        if (landPrice.getIsFirePreventionChanged() != null)
            ps.setInt(15, landPrice.getIsFirePreventionChanged() ? 1 : 0);
        else
            ps.setNull(15, Types.INTEGER);

        if (landPrice.getIsUrbanPlanAreaChanged() != null)
            ps.setInt(16, landPrice.getIsUrbanPlanAreaChanged() ? 1 : 0);
        else
            ps.setNull(16, Types.INTEGER);

        if (landPrice.getIsBuildingCoverageRateChanged() != null)
            ps.setInt(17, landPrice.getIsBuildingCoverageRateChanged() ? 1 : 0);
        else
            ps.setNull(17, Types.INTEGER);

        if (landPrice.getIsFloorAreaRateChanged() != null)
            ps.setInt(18, landPrice.getIsFloorAreaRateChanged() ? 1 : 0);
        else
            ps.setNull(18, Types.INTEGER);

        if (!landPrice.getCityCode().isEmpty()) {
            valueJoiner.join(landPrice.getCityCode(), Code::getValue, Code::getCodeSpace);
            ps.setString(19, valueJoiner.result(0));
            ps.setString(20, valueJoiner.result(1));
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        ps.setString(21, landPrice.getCityName());

        ps.setString(22, landPrice.getLocationDescription());

        ps.setString(23, landPrice.getAddress());

        if (landPrice.getAcreage() != null && landPrice.getAcreage().isSetValue()) {
            ps.setDouble(24, landPrice.getAcreage().getValue());
            ps.setString(25, landPrice.getAcreage().getUom());
        } else {
            ps.setNull(24, Types.DOUBLE);
            ps.setNull(25, Types.VARCHAR);
        }

        if (!landPrice.getUseStatus().isEmpty())
            ps.setString(26, valueJoiner.join(landPrice.getUseStatus()));
        else
            ps.setNull(26, Types.VARCHAR);

        ps.setString(27, landPrice.getUseStatusDescription());

        ps.setString(28, landPrice.getUseType());

        ps.setString(29, landPrice.getBuildingStructure());

        if (landPrice.getWater() != null)
            ps.setInt(30, landPrice.getWater() ? 1 : 0);
        else
            ps.setNull(30, Types.INTEGER);

        if (landPrice.getGas() != null)
            ps.setInt(31, landPrice.getGas() ? 1 : 0);
        else
            ps.setNull(31, Types.INTEGER);

        if (landPrice.getSewerage() != null)
            ps.setInt(32, landPrice.getSewerage() ? 1 : 0);
        else
            ps.setNull(32, Types.INTEGER);

        ps.setString(33, landPrice.getShape());

        if (landPrice.getFrontageRate() != null)
            ps.setDouble(34, landPrice.getFrontageRate());
        else
            ps.setNull(34, Types.DOUBLE);

        if (landPrice.getDepthRate() != null)
            ps.setDouble(35, landPrice.getDepthRate());
        else
            ps.setNull(35, Types.DOUBLE);

        if (landPrice.getStoreysAboveGround() != null)
            ps.setInt(36, landPrice.getStoreysAboveGround());
        else
            ps.setNull(36, Types.INTEGER);

        if (landPrice.getStoreysBelowGround() != null)
            ps.setInt(37, landPrice.getStoreysBelowGround());
        else
            ps.setNull(37, Types.INTEGER);

        ps.setString(38, landPrice.getFrontageRoad());

        ps.setString(39, landPrice.getFrontageRoadDirection());

        if (landPrice.getFrontageRoadWidth() != null && landPrice.getFrontageRoadWidth().isSetValue()) {
            ps.setDouble(40, landPrice.getFrontageRoadWidth().getValue());
            ps.setString(41, landPrice.getFrontageRoadWidth().getUom());
        } else {
            ps.setNull(40, Types.DOUBLE);
            ps.setNull(41, Types.VARCHAR);
        }

        ps.setString(42, landPrice.getStationSquare());

        ps.setString(43, landPrice.getFrontageRoadSurface());

        ps.setString(44, landPrice.getSideRoad());

        ps.setString(45, landPrice.getSideRoadDirection());

        ps.setString(46, landPrice.getTransportationFacilities());

        ps.setString(47, landPrice.getSurroundings());

        ps.setString(48, landPrice.getStationName());

        if (landPrice.getDistanceFromStation() != null && landPrice.getDistanceFromStation().isSetValue()) {
            ps.setDouble(49, landPrice.getDistanceFromStation().getValue());
            ps.setString(50, landPrice.getDistanceFromStation().getUom());
        } else {
            ps.setNull(49, Types.DOUBLE);
            ps.setNull(50, Types.VARCHAR);
        }

        if (landPrice.getUseDistrict() != null && landPrice.getUseDistrict().isSetValue()) {
            ps.setString(51, landPrice.getUseDistrict().getValue());
            ps.setString(52, landPrice.getUseDistrict().getCodeSpace());
        } else {
            ps.setNull(51, Types.VARCHAR);
            ps.setNull(52, Types.VARCHAR);
        }

        ps.setString(53, landPrice.getFirePrevention());

        ps.setString(54, landPrice.getUrbanPlanAreaType());

        ps.setString(55, landPrice.getHeightControl());

        ps.setString(56, landPrice.getForest());

        ps.setString(57, landPrice.getPark());

        if (landPrice.getBuildingCoverageRate() != null)
            ps.setInt(58, landPrice.getBuildingCoverageRate());
        else
            ps.setNull(58, Types.INTEGER);

        if (landPrice.getFloorAreaRate() != null)
            ps.setInt(59, landPrice.getFloorAreaRate());
        else
            ps.setNull(59, Types.INTEGER);

        ps.setString(60, landPrice.getExtraFloorAreaRate());

        if (landPrice.getIsSame() != null)
            ps.setInt(61, landPrice.getIsSame() ? 1 : 0);
        else
            ps.setNull(61, Types.INTEGER);

        ps.setString(62, landPrice.getSelectionStatus());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (ValueByCodesProperty property : landPrice.getLandPriceHistory()) {
            if (property.isSetObject())
                valueByCodesImporter.doImport(property.getObject(), objectId, "landprice_2_landpricehist_id");
        }
        for (ValueByCodesProperty property : landPrice.getAttributeHistory()) {
            if (property.isSetObject())
                valueByCodesImporter.doImport(property.getObject(), objectId, "landprice_2_attributehist_id");
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
