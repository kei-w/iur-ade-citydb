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

package org.citydb.ade.iur.exporter.urf;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.LandPrice;
import org.citygml4j.ade.iur.model.urf.ValueByCodes;
import org.citygml4j.ade.iur.model.urf.ValueByCodesProperty;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.time.Year;

import java.sql.*;

public class LandPriceExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final PreparedStatement ps;
    private final ValueByCodesExporter valueByCodesExporter;
    private final AttributeValueSplitter valueSplitter;

    public LandPriceExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LANDPRICE_2);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        valueByCodesExporter = manager.getExporter(ValueByCodesExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(LandPrice landPrice, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(landPrice, objectId, "uf_", rs);
                doExport(landPrice, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("pointcode", prefix + "pointcode"),
                table.getColumn("pointcodeoflastyear", prefix + "pointcodeoflastyear"),
                table.getColumn("fiscalyear", prefix + "fiscalyear"),
                table.getColumn("landprice", prefix + "landprice"),
                table.getColumn("variability", prefix + "variability"),
                table.getColumn("isselected", prefix + "isselected"),
                table.getColumn("isaddresschanged", prefix + "isaddresschanged"),
                table.getColumn("isacreagechanged", prefix + "isacreagechanged"),
                table.getColumn("isusechanged", prefix + "isusechanged"),
                table.getColumn("isbuildingstructurechanged", prefix + "isbuildingstructurechanged"),
                table.getColumn("issupplyfacilitieschanged", prefix + "issupplyfacilitieschanged"),
                table.getColumn("isdistancefromstationchanged", prefix + "isdistancefromstationchanged"),
                table.getColumn("isusedistrictchanged", prefix + "isusedistrictchanged"),
                table.getColumn("isfirepreventionchanged", prefix + "isfirepreventionchanged"),
                table.getColumn("isurbanplanareachanged", prefix + "isurbanplanareachanged"),
                table.getColumn("isbuildingcoverageratechange", prefix + "isbuildingcoverageratechange"),
                table.getColumn("isfloorarearatechanged", prefix + "isfloorarearatechanged"),
                table.getColumn("citycode", prefix + "citycode"),
                table.getColumn("citycode_codespace", prefix + "citycode_codespace"),
                table.getColumn("cityname", prefix + "cityname"),
                table.getColumn("location", prefix + "location"),
                table.getColumn("address", prefix + "address"),
                table.getColumn("acreage", prefix + "acreage"),
                table.getColumn("acreage_uom", prefix + "acreage_uom"),
                table.getColumn("usestatus", prefix + "usestatus"),
                table.getColumn("usestatusdescription", prefix + "usestatusdescription"),
                table.getColumn("usetype", prefix + "usetype"),
                table.getColumn("buildingstructure", prefix + "buildingstructure"),
                table.getColumn("water", prefix + "water"),
                table.getColumn("gas", prefix + "gas"),
                table.getColumn("sewerage", prefix + "sewerage"),
                table.getColumn("shape", prefix + "shape"),
                table.getColumn("frontagerate", prefix + "frontagerate"),
                table.getColumn("depthrate", prefix + "depthrate"),
                table.getColumn("storeysaboveground", prefix + "storeysaboveground"),
                table.getColumn("storeysbelowground", prefix + "storeysbelowground"),
                table.getColumn("frontageroad", prefix + "frontageroad"),
                table.getColumn("frontageroaddirection", prefix + "frontageroaddirection"),
                table.getColumn("frontageroadwidth", prefix + "frontageroadwidth"),
                table.getColumn("frontageroadwidth_uom", prefix + "frontageroadwidth_uom"),
                table.getColumn("stationsquare", prefix + "stationsquare"),
                table.getColumn("frontageroadsurface", prefix + "frontageroadsurface"),
                table.getColumn("sideroad", prefix + "sideroad"),
                table.getColumn("sideroaddirection", prefix + "sideroaddirection"),
                table.getColumn("transportationfacilities", prefix + "transportationfacilities"),
                table.getColumn("surroundings", prefix + "surroundings"),
                table.getColumn("stationname", prefix + "stationname"),
                table.getColumn("distancefromstation", prefix + "distancefromstation"),
                table.getColumn("distancefromstation_uom", prefix + "distancefromstation_uom"),
                table.getColumn("usedistrict", prefix + "usedistrict"),
                table.getColumn("usedistrict_codespace", prefix + "usedistrict_codespace"),
                table.getColumn("fireprevention", prefix + "fireprevention"),
                table.getColumn("urbanplanareatype", prefix + "urbanplanareatype"),
                table.getColumn("heightcontrol", prefix + "heightcontrol"),
                table.getColumn("forest", prefix + "forest"),
                table.getColumn("park", prefix + "park"),
                table.getColumn("buildingcoveragerate", prefix + "buildingcoveragerate"),
                table.getColumn("floorarearate", prefix + "floorarearate"),
                table.getColumn("extrafloorarearate", prefix + "extrafloorarearate"),
                table.getColumn("issame", prefix + "issame"),
                table.getColumn("selectionstatus", prefix + "selectionstatus"));

        return select;
    }

    public void doExport(LandPrice landPrice, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        landPrice.setPointCode(rs.getString(prefix + "pointcode"));

        landPrice.setPointCodeOfLastYear(rs.getString(prefix + "pointcodeoflastyear"));

        Date fiscalYear = rs.getDate(prefix + "fiscalyear");
        if (!rs.wasNull())
            landPrice.setFiscalYear(Year.of(fiscalYear.toLocalDate().getYear()));

        int landPriceValue = rs.getInt(prefix + "landprice");
        if (!rs.wasNull())
            landPrice.setLandPrice(landPriceValue);

        double variability = rs.getDouble(prefix + "variability");
        if (!rs.wasNull())
            landPrice.setVariability(variability);

        boolean isSelected = rs.getBoolean(prefix + "isselected");
        if (!rs.wasNull())
            landPrice.setIsSelected(isSelected);

        boolean isAddressChanged = rs.getBoolean(prefix + "isaddresschanged");
        if (!rs.wasNull())
            landPrice.setIsAddressChanged(isAddressChanged);

        boolean isAcreageChanged = rs.getBoolean(prefix + "isacreagechanged");
        if (!rs.wasNull())
            landPrice.setIsAcreageChanged(isAcreageChanged);

        boolean isUseChanged = rs.getBoolean(prefix + "isusechanged");
        if (!rs.wasNull())
            landPrice.setIsUseChanged(isUseChanged);

        boolean isBuildingStructureChanged = rs.getBoolean(prefix + "isbuildingstructurechanged");
        if (!rs.wasNull())
            landPrice.setIsBuildingStructureChanged(isBuildingStructureChanged);

        boolean isSupplyFacilitiesChanged = rs.getBoolean(prefix + "issupplyfacilitieschanged");
        if (!rs.wasNull())
            landPrice.setIsSupplyFacilitiesChanged(isSupplyFacilitiesChanged);

        boolean isDistanceFromStationChanged = rs.getBoolean(prefix + "isdistancefromstationchanged");
        if (!rs.wasNull())
            landPrice.setIsDistanceFromStationChanged(isDistanceFromStationChanged);

        boolean isUseDistrictChanged = rs.getBoolean(prefix + "isusedistrictchanged");
        if (!rs.wasNull())
            landPrice.setIsUseDistrictChanged(isUseDistrictChanged);

        boolean isFirePreventionChanged = rs.getBoolean(prefix + "isfirepreventionchanged");
        if (!rs.wasNull())
            landPrice.setIsFirePreventionChanged(isFirePreventionChanged);

        boolean isUrbanPlanAreaChanged = rs.getBoolean(prefix + "isurbanplanareachanged");
        if (!rs.wasNull())
            landPrice.setIsUrbanPlanAreaChanged(isUrbanPlanAreaChanged);

        boolean isBuildingCoverageRateChanged = rs.getBoolean(prefix + "isbuildingcoverageratechange");
        if (!rs.wasNull())
            landPrice.setIsBuildingCoverageRateChanged(isBuildingCoverageRateChanged);

        boolean isFloorAreaRateChanged = rs.getBoolean(prefix + "isfloorarearatechanged");
        if (!rs.wasNull())
            landPrice.setIsFloorAreaRateChanged(isFloorAreaRateChanged);

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "citycode"), rs.getString(prefix + "citycode_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            landPrice.getCityCode().add(code);
        }

        landPrice.setCityName(rs.getString(prefix + "cityname"));

        landPrice.setLocationDescription(rs.getString(prefix + "location"));

        landPrice.setAddress(rs.getString(prefix + "address"));

        double acreage = rs.getDouble(prefix + "acreage");
        if (!rs.wasNull()) {
            Measure measure = new Measure(acreage);
            measure.setUom(rs.getString(prefix + "acreage_uom"));
            landPrice.setAcreage(measure);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "usestatus")))
            landPrice.getUseStatus().add(splitValue.result(0));

        landPrice.setUseStatusDescription(rs.getString(prefix + "usestatusdescription"));

        landPrice.setUseType(rs.getString(prefix + "usetype"));

        landPrice.setBuildingStructure(rs.getString(prefix + "buildingstructure"));

        boolean water = rs.getBoolean(prefix + "water");
        if (!rs.wasNull())
            landPrice.setWater(water);

        boolean gas = rs.getBoolean(prefix + "gas");
        if (!rs.wasNull())
            landPrice.setGas(gas);

        boolean sewerage = rs.getBoolean(prefix + "sewerage");
        if (!rs.wasNull())
            landPrice.setSewerage(sewerage);

        landPrice.setShape(rs.getString(prefix + "shape"));

        double frontageRate = rs.getDouble(prefix + "frontagerate");
        if (!rs.wasNull())
            landPrice.setFrontageRate(frontageRate);

        double depthRate = rs.getDouble(prefix + "depthrate");
        if (!rs.wasNull())
            landPrice.setDepthRate(depthRate);

        int storeysAboveGround = rs.getInt(prefix + "storeysaboveground");
        if (!rs.wasNull())
            landPrice.setStoreysAboveGround(storeysAboveGround);

        int storeysBelowGround = rs.getInt(prefix + "storeysbelowground");
        if (!rs.wasNull())
            landPrice.setStoreysBelowGround(storeysBelowGround);

        landPrice.setFrontageRoad(rs.getString(prefix + "frontageroad"));

        landPrice.setFrontageRoadDirection(rs.getString(prefix + "frontageroaddirection"));

        double frontageRoadWidth = rs.getDouble(prefix + "frontageroadwidth");
        if (!rs.wasNull()) {
            Length length = new Length(frontageRoadWidth);
            length.setUom(rs.getString(prefix + "frontageroadwidth_uom"));
            landPrice.setFrontageRoadWidth(length);
        }

        landPrice.setStationSquare(rs.getString(prefix + "stationsquare"));

        landPrice.setFrontageRoadSurface(rs.getString(prefix + "frontageroadsurface"));

        landPrice.setSideRoad(rs.getString(prefix + "sideroad"));

        landPrice.setSideRoadDirection(rs.getString(prefix + "sideroaddirection"));

        landPrice.setTransportationFacilities(rs.getString(prefix + "transportationfacilities"));

        landPrice.setSurroundings(rs.getString(prefix + "surroundings"));

        landPrice.setStationName(rs.getString(prefix + "stationname"));

        double distanceFromStation = rs.getDouble(prefix + "distancefromstation");
        if (!rs.wasNull()) {
            Length length = new Length(distanceFromStation);
            length.setUom(rs.getString(prefix + "distancefromstation_uom"));
            landPrice.setDistanceFromStation(length);
        }

        String useDistrict = rs.getString(prefix + "usedistrict");
        if (!rs.wasNull()) {
            Code code = new Code(useDistrict);
            code.setCodeSpace(rs.getString(prefix + "usedistrict_codespace"));
            landPrice.setUseDistrict(code);
        }

        landPrice.setFirePrevention(rs.getString(prefix + "fireprevention"));

        landPrice.setUrbanPlanAreaType(rs.getString(prefix + "urbanplanareatype"));

        landPrice.setHeightControl(rs.getString(prefix + "heightcontrol"));

        landPrice.setForest(rs.getString(prefix + "forest"));

        landPrice.setPark(rs.getString(prefix + "park"));

        int buildingCoverageRate = rs.getInt(prefix + "buildingcoveragerate");
        if (!rs.wasNull())
            landPrice.setBuildingCoverageRate(buildingCoverageRate);

        int floorAreaRate = rs.getInt(prefix + "floorarearate");
        if (!rs.wasNull())
            landPrice.setFloorAreaRate(floorAreaRate);

        landPrice.setExtraFloorAreaRate(rs.getString(prefix + "extrafloorarearate"));

        boolean isSame = rs.getBoolean(prefix + "issame");
        if (!rs.wasNull())
            landPrice.setIsSame(isSame);

        landPrice.setSelectionStatus(rs.getString(prefix + "selectionstatus"));

        for (ValueByCodes landPriceHistory : valueByCodesExporter.doExport(objectId, "landprice_2_landpricehist_id"))
            landPrice.getLandPriceHistory().add(new ValueByCodesProperty(landPriceHistory));

        for (ValueByCodes attributeHistory : valueByCodesExporter.doExport(objectId, "landprice_2_attributehist_id"))
            landPrice.getAttributeHistory().add(new ValueByCodesProperty(attributeHistory));
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
