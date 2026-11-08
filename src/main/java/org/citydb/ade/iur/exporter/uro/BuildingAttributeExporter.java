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

package org.citydb.ade.iur.exporter.uro;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.BuildingAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingDataQualityAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingDetailAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingDisasterRiskAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingFloodingRiskAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingIDAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingLandSlideRiskAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingRiverFloodingRiskAttribute;
import org.citygml4j.ade.iur.model.uro.LargeCustomerFacilityAttribute;
import org.citygml4j.ade.iur.model.uro.RealEstateIDAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.time.Year;

import java.sql.*;

public class BuildingAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public BuildingAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BUILDINGATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table realEstateIDAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.REALESTATEIDATTRIBUTE)));
        Table buildingIDAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGIDATTRIBUTE)));
        Table buildingDetailAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGDETAILATTRIBUT)));
        Table largeCustomerFacilityAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.LARGECUSTOMERFACILITYA)));
        Table buildingDisasterRiskAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGDISASTERRISKAT)));
        Table buildingFloodingRiskAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGFLOODINGRISKAT)));
        Table buildingRiverFloodingRiskAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGRIVERFLOODINGR)));
        Table buildingDataQualityAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGDATAQUALITYATT)));
        Select select = new Select().addProjection(table.getColumns("id", "objectclass_id"))
                .addProjection(realEstateIDAttribute.getColumns("realestateidofbuilding", "numberofbuildingunitownershi", "realestateidofbuildingunitow",
                        "numberofrealestateidofland", "realestateidofland", "matchingscore"))
                .addProjection(buildingIDAttribute.getColumns("buildingid", "branchid", "partid", "prefecture", "prefecture_codespace", "city", "city_codespace"))
                .addProjection(buildingDetailAttribute.getColumns("serialnumberofbuildingcertif", "sitearea", "sitearea_uom", "buildingfootprintarea",
                        "buildingfootprintarea_uom", "buildingroofedgearea", "buildingroofedgearea_uom", "developmentarea",
                        "developmentarea_uom", "buildingstructuretype", "buildingstructuret_codespace",
                        "buildingstructureorgtype", "buildingstructureo_codespace", "fireproofstructuretype",
                        "fireproofstructure_codespace", "implementingbody", "majorusage", "majorusage_codespace",
                        "majorusage2", "majorusage2_codespace", "orgusage", "orgusage_codespace", "orgusage2",
                        "orgusage2_codespace", "detailedusage", "detailedusage_codespace", "detailedusage2",
                        "detailedusage2_codespace", "detailedusage3", "detailedusage3_codespace", "groundfloorusage",
                        "groundfloorusage_codespace", "secondfloorusage", "secondfloorusage_codespace", "thirdfloorusage",
                        "thirdfloorusage_codespace", "basementusage", "basementusage_codespace", "basementfirstusage",
                        "basementfirstusage_codespace", "basementsecondusage", "basementsecondusag_codespace", "vacancy",
                        "vacancy_codespace", "buildingcoveragerate", "floorarearate", "specifiedbuildingcoveragerat",
                        "specifiedfloorarearate", "standardfloorarearate", "buildingheight", "buildingheight_uom",
                        "eaveheight", "eaveheight_uom"))
                .addProjection(largeCustomerFacilityAttribute.getColumns("class", "class_codespace", "name", "capacity", "owner", "totalstorefloorarea",
                        "totalstorefloorarea_uom", "inauguraldate", "yearopened", "yearclosed", "keytenants",
                        "availability"))
                .addProjection(buildingDisasterRiskAttribute.getColumns("description", "description_codespace", "areatype", "areatype_codespace"))
                .addProjection(buildingFloodingRiskAttribute.getColumns("rank", "rank_codespace", "rankorg", "rankorg_codespace", "depth", "depth_uom"))
                .addProjection(buildingRiverFloodingRiskAttribute.getColumns("admintype", "admintype_codespace", "scale", "scale_codespace", "duration", "duration_uom"))
                .addProjection(buildingDataQualityAttribute.getColumns("srcscale", "srcscale_codespace", "geometrysrcdesc", "geometrysrcdesc_codespace",
                        "thematicsrcdesc", "thematicsrcdesc_codespace", "appearancesrcdesc", "appearancesrcdesc_codespace",
                        "lod1heighttype", "lod1heighttype_codespace", "lodtype"))
                .addProjection(buildingDetailAttribute.getColumn("totalfloorarea", "buildingdetailattribute_totalfloorarea"))
                .addProjection(buildingDetailAttribute.getColumn("totalfloorarea_uom", "buildingdetailattribute_totalfloorarea_uom"))
                .addProjection(buildingDetailAttribute.getColumn("urbanplantype", "buildingdetailattribute_urbanplantype"))
                .addProjection(buildingDetailAttribute.getColumn("urbanplantype_codespace", "buildingdetailattribute_urbanplantype_codespace"))
                .addProjection(buildingDetailAttribute.getColumn("areaclassificationtype", "buildingdetailattribute_areaclassificationtype"))
                .addProjection(buildingDetailAttribute.getColumn("areaclassification_codespace", "buildingdetailattribute_areaclassification_codespace"))
                .addProjection(buildingDetailAttribute.getColumn("districtsandzonestype", "buildingdetailattribute_districtsandzonestype"))
                .addProjection(buildingDetailAttribute.getColumn("districtsandzonest_codespace", "buildingdetailattribute_districtsandzonest_codespace"))
                .addProjection(buildingDetailAttribute.getColumn("landusetype", "buildingdetailattribute_landusetype"))
                .addProjection(buildingDetailAttribute.getColumn("landusetype_codespace", "buildingdetailattribute_landusetype_codespace"))
                .addProjection(buildingDetailAttribute.getColumn("reference", "buildingdetailattribute_reference"))
                .addProjection(buildingDetailAttribute.getColumn("note", "buildingdetailattribute_note"))
                .addProjection(buildingDetailAttribute.getColumn("surveyyear", "buildingdetailattribute_surveyyear"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("totalfloorarea", "largecustomerfacilityattribute_totalfloorarea"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("totalfloorarea_uom", "largecustomerfacilityattribute_totalfloorarea_uom"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("urbanplantype", "largecustomerfacilityattribute_urbanplantype"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("urbanplantype_codespace", "largecustomerfacilityattribute_urbanplantype_codespace"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("areaclassificationtype", "largecustomerfacilityattribute_areaclassificationtype"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("areaclassification_codespace", "largecustomerfacilityattribute_areaclassification_codespace"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("districtsandzonestype", "largecustomerfacilityattribute_districtsandzonestype"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("districtsandzonest_codespace", "largecustomerfacilityattribute_districtsandzonest_codespace"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("landusetype", "largecustomerfacilityattribute_landusetype"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("landusetype_codespace", "largecustomerfacilityattribute_landusetype_codespace"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("reference", "largecustomerfacilityattribute_reference"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("note", "largecustomerfacilityattribute_note"))
                .addProjection(largeCustomerFacilityAttribute.getColumn("surveyyear", "largecustomerfacilityattribute_surveyyear"))
                .addJoin(JoinFactory.left(realEstateIDAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(buildingIDAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(buildingDetailAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(largeCustomerFacilityAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(buildingDisasterRiskAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(buildingFloodingRiskAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(buildingRiverFloodingRiskAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(buildingDataQualityAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public BuildingAttribute doExport(long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? doExport(objectId, rs) : null;
        }
    }

    private BuildingAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        BuildingAttribute buildingAttribute = objectMapper.createType(objectClassId, BuildingAttribute.class);
        if (buildingAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate building attribute (id: " + objectId + ").");
            return null;
        }

        if (buildingAttribute instanceof RealEstateIDAttribute) {
            RealEstateIDAttribute realEstateIDAttribute = (RealEstateIDAttribute) buildingAttribute;

            realEstateIDAttribute.setRealEstateIDOfBuilding(rs.getString("realestateidofbuilding"));

            int numberOfBuildingUnitOwnership = rs.getInt("numberofbuildingunitownershi");
            if (!rs.wasNull())
                realEstateIDAttribute.setNumberOfBuildingUnitOwnership(numberOfBuildingUnitOwnership);

            for (SplitValue splitValue : valueSplitter.split(rs.getString("realestateidofbuildingunitow")))
                realEstateIDAttribute.getRealEstateIDOfBuildingUnitOwnership().add(splitValue.result(0));

            int numberOfRealEstateIDOfLand = rs.getInt("numberofrealestateidofland");
            if (!rs.wasNull())
                realEstateIDAttribute.setNumberOfRealEstateIDOfLand(numberOfRealEstateIDOfLand);

            for (SplitValue splitValue : valueSplitter.split(rs.getString("realestateidofland")))
                realEstateIDAttribute.getRealEstateIDOfLand().add(splitValue.result(0));

            int matchingScore = rs.getInt("matchingscore");
            if (!rs.wasNull())
                realEstateIDAttribute.setMatchingScore(matchingScore);
        }

        if (buildingAttribute instanceof BuildingIDAttribute) {
            BuildingIDAttribute buildingIDAttribute = (BuildingIDAttribute) buildingAttribute;

            buildingIDAttribute.setBuildingID(rs.getString("buildingid"));

            int branchID = rs.getInt("branchid");
            if (!rs.wasNull())
                buildingIDAttribute.setBranchID(branchID);

            int partID = rs.getInt("partid");
            if (!rs.wasNull())
                buildingIDAttribute.setPartID(partID);

            String prefecture = rs.getString("prefecture");
            if (!rs.wasNull()) {
                Code code = new Code(prefecture);
                code.setCodeSpace(rs.getString("prefecture_codespace"));
                buildingIDAttribute.setPrefecture(code);
            }

            String city = rs.getString("city");
            if (!rs.wasNull()) {
                Code code = new Code(city);
                code.setCodeSpace(rs.getString("city_codespace"));
                buildingIDAttribute.setCity(code);
            }
        }

        if (buildingAttribute instanceof BuildingDetailAttribute) {
            BuildingDetailAttribute buildingDetailAttribute = (BuildingDetailAttribute) buildingAttribute;

            buildingDetailAttribute.setSerialNumberOfBuildingCertification(rs.getString("serialnumberofbuildingcertif"));

            double siteArea = rs.getDouble("sitearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(siteArea);
                measure.setUom(rs.getString("sitearea_uom"));
                buildingDetailAttribute.setSiteArea(measure);
            }

            double totalFloorArea = rs.getDouble("buildingdetailattribute_totalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalFloorArea);
                measure.setUom(rs.getString("buildingdetailattribute_totalfloorarea_uom"));
                buildingDetailAttribute.setTotalFloorArea(measure);
            }

            double buildingFootprintArea = rs.getDouble("buildingfootprintarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(buildingFootprintArea);
                measure.setUom(rs.getString("buildingfootprintarea_uom"));
                buildingDetailAttribute.setBuildingFootprintArea(measure);
            }

            double buildingRoofEdgeArea = rs.getDouble("buildingroofedgearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(buildingRoofEdgeArea);
                measure.setUom(rs.getString("buildingroofedgearea_uom"));
                buildingDetailAttribute.setBuildingRoofEdgeArea(measure);
            }

            double developmentArea = rs.getDouble("developmentarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(developmentArea);
                measure.setUom(rs.getString("developmentarea_uom"));
                buildingDetailAttribute.setDevelopmentArea(measure);
            }

            String buildingStructureType = rs.getString("buildingstructuretype");
            if (!rs.wasNull()) {
                Code code = new Code(buildingStructureType);
                code.setCodeSpace(rs.getString("buildingstructuret_codespace"));
                buildingDetailAttribute.setBuildingStructureType(code);
            }

            String buildingStructureOrgType = rs.getString("buildingstructureorgtype");
            if (!rs.wasNull()) {
                Code code = new Code(buildingStructureOrgType);
                code.setCodeSpace(rs.getString("buildingstructureo_codespace"));
                buildingDetailAttribute.setBuildingStructureOrgType(code);
            }

            String fireproofStructureType = rs.getString("fireproofstructuretype");
            if (!rs.wasNull()) {
                Code code = new Code(fireproofStructureType);
                code.setCodeSpace(rs.getString("fireproofstructure_codespace"));
                buildingDetailAttribute.setFireproofStructureType(code);
            }

            buildingDetailAttribute.setImplementingBody(rs.getString("implementingbody"));

            String urbanPlanType = rs.getString("buildingdetailattribute_urbanplantype");
            if (!rs.wasNull()) {
                Code code = new Code(urbanPlanType);
                code.setCodeSpace(rs.getString("buildingdetailattribute_urbanplantype_codespace"));
                buildingDetailAttribute.setUrbanPlanType(code);
            }

            String areaClassificationType = rs.getString("buildingdetailattribute_areaclassificationtype");
            if (!rs.wasNull()) {
                Code code = new Code(areaClassificationType);
                code.setCodeSpace(rs.getString("buildingdetailattribute_areaclassification_codespace"));
                buildingDetailAttribute.setAreaClassificationType(code);
            }

            for (SplitValue splitValue : valueSplitter.split(rs.getString("buildingdetailattribute_districtsandzonestype"), rs.getString("buildingdetailattribute_districtsandzonest_codespace"))) {
                Code code = new Code(splitValue.result(0));
                code.setCodeSpace(splitValue.result(1));
                buildingDetailAttribute.getDistrictsAndZonesType().add(code);
            }

            String landUseType = rs.getString("buildingdetailattribute_landusetype");
            if (!rs.wasNull()) {
                Code code = new Code(landUseType);
                code.setCodeSpace(rs.getString("buildingdetailattribute_landusetype_codespace"));
                buildingDetailAttribute.setLandUseType(code);
            }

            buildingDetailAttribute.setReference(rs.getString("buildingdetailattribute_reference"));

            String majorUsage = rs.getString("majorusage");
            if (!rs.wasNull()) {
                Code code = new Code(majorUsage);
                code.setCodeSpace(rs.getString("majorusage_codespace"));
                buildingDetailAttribute.setMajorUsage(code);
            }

            String majorUsage2 = rs.getString("majorusage2");
            if (!rs.wasNull()) {
                Code code = new Code(majorUsage2);
                code.setCodeSpace(rs.getString("majorusage2_codespace"));
                buildingDetailAttribute.setMajorUsage2(code);
            }

            String orgUsage = rs.getString("orgusage");
            if (!rs.wasNull()) {
                Code code = new Code(orgUsage);
                code.setCodeSpace(rs.getString("orgusage_codespace"));
                buildingDetailAttribute.setOrgUsage(code);
            }

            String orgUsage2 = rs.getString("orgusage2");
            if (!rs.wasNull()) {
                Code code = new Code(orgUsage2);
                code.setCodeSpace(rs.getString("orgusage2_codespace"));
                buildingDetailAttribute.setOrgUsage2(code);
            }

            String detailedUsage = rs.getString("detailedusage");
            if (!rs.wasNull()) {
                Code code = new Code(detailedUsage);
                code.setCodeSpace(rs.getString("detailedusage_codespace"));
                buildingDetailAttribute.setDetailedUsage(code);
            }

            String detailedUsage2 = rs.getString("detailedusage2");
            if (!rs.wasNull()) {
                Code code = new Code(detailedUsage2);
                code.setCodeSpace(rs.getString("detailedusage2_codespace"));
                buildingDetailAttribute.setDetailedUsage2(code);
            }

            String detailedUsage3 = rs.getString("detailedusage3");
            if (!rs.wasNull()) {
                Code code = new Code(detailedUsage3);
                code.setCodeSpace(rs.getString("detailedusage3_codespace"));
                buildingDetailAttribute.setDetailedUsage3(code);
            }

            String groundFloorUsage = rs.getString("groundfloorusage");
            if (!rs.wasNull()) {
                Code code = new Code(groundFloorUsage);
                code.setCodeSpace(rs.getString("groundfloorusage_codespace"));
                buildingDetailAttribute.setGroundFloorUsage(code);
            }

            String secondFloorUsage = rs.getString("secondfloorusage");
            if (!rs.wasNull()) {
                Code code = new Code(secondFloorUsage);
                code.setCodeSpace(rs.getString("secondfloorusage_codespace"));
                buildingDetailAttribute.setSecondFloorUsage(code);
            }

            String thirdFloorUsage = rs.getString("thirdfloorusage");
            if (!rs.wasNull()) {
                Code code = new Code(thirdFloorUsage);
                code.setCodeSpace(rs.getString("thirdfloorusage_codespace"));
                buildingDetailAttribute.setThirdFloorUsage(code);
            }

            String basementUsage = rs.getString("basementusage");
            if (!rs.wasNull()) {
                Code code = new Code(basementUsage);
                code.setCodeSpace(rs.getString("basementusage_codespace"));
                buildingDetailAttribute.setBasementUsage(code);
            }

            String basementFirstUsage = rs.getString("basementfirstusage");
            if (!rs.wasNull()) {
                Code code = new Code(basementFirstUsage);
                code.setCodeSpace(rs.getString("basementfirstusage_codespace"));
                buildingDetailAttribute.setBasementFirstUsage(code);
            }

            String basementSecondUsage = rs.getString("basementsecondusage");
            if (!rs.wasNull()) {
                Code code = new Code(basementSecondUsage);
                code.setCodeSpace(rs.getString("basementsecondusag_codespace"));
                buildingDetailAttribute.setBasementSecondUsage(code);
            }

            String vacancy = rs.getString("vacancy");
            if (!rs.wasNull()) {
                Code code = new Code(vacancy);
                code.setCodeSpace(rs.getString("vacancy_codespace"));
                buildingDetailAttribute.setVacancy(code);
            }

            double buildingCoverageRate = rs.getDouble("buildingcoveragerate");
            if (!rs.wasNull())
                buildingDetailAttribute.setBuildingCoverageRate(buildingCoverageRate);

            double floorAreaRate = rs.getDouble("floorarearate");
            if (!rs.wasNull())
                buildingDetailAttribute.setFloorAreaRate(floorAreaRate);

            double specifiedBuildingCoverageRate = rs.getDouble("specifiedbuildingcoveragerat");
            if (!rs.wasNull())
                buildingDetailAttribute.setSpecifiedBuildingCoverageRate(specifiedBuildingCoverageRate);

            double specifiedFloorAreaRate = rs.getDouble("specifiedfloorarearate");
            if (!rs.wasNull())
                buildingDetailAttribute.setSpecifiedFloorAreaRate(specifiedFloorAreaRate);

            double standardFloorAreaRate = rs.getDouble("standardfloorarearate");
            if (!rs.wasNull())
                buildingDetailAttribute.setStandardFloorAreaRate(standardFloorAreaRate);

            double buildingHeight = rs.getDouble("buildingheight");
            if (!rs.wasNull()) {
                Length length = new Length(buildingHeight);
                length.setUom(rs.getString("buildingheight_uom"));
                buildingDetailAttribute.setBuildingHeight(length);
            }

            double eaveHeight = rs.getDouble("eaveheight");
            if (!rs.wasNull()) {
                Length length = new Length(eaveHeight);
                length.setUom(rs.getString("eaveheight_uom"));
                buildingDetailAttribute.setEaveHeight(length);
            }

            buildingDetailAttribute.setNote(rs.getString("buildingdetailattribute_note"));

            Date surveyYear = rs.getDate("buildingdetailattribute_surveyyear");
            if (!rs.wasNull())
                buildingDetailAttribute.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));
        }

        if (buildingAttribute instanceof LargeCustomerFacilityAttribute) {
            LargeCustomerFacilityAttribute largeCustomerFacilityAttribute = (LargeCustomerFacilityAttribute) buildingAttribute;

            String classifier = rs.getString("class");
            if (!rs.wasNull()) {
                Code code = new Code(classifier);
                code.setCodeSpace(rs.getString("class_codespace"));
                largeCustomerFacilityAttribute.setClassifier(code);
            }

            largeCustomerFacilityAttribute.setName(rs.getString("name"));

            int capacity = rs.getInt("capacity");
            if (!rs.wasNull())
                largeCustomerFacilityAttribute.setCapacity(capacity);

            largeCustomerFacilityAttribute.setOwner(rs.getString("owner"));

            double totalFloorArea = rs.getDouble("largecustomerfacilityattribute_totalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalFloorArea);
                measure.setUom(rs.getString("largecustomerfacilityattribute_totalfloorarea_uom"));
                largeCustomerFacilityAttribute.setTotalFloorArea(measure);
            }

            double totalStoreFloorArea = rs.getDouble("totalstorefloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalStoreFloorArea);
                measure.setUom(rs.getString("totalstorefloorarea_uom"));
                largeCustomerFacilityAttribute.setTotalStoreFloorArea(measure);
            }

            Date inauguralDate = rs.getDate("inauguraldate");
            if (!rs.wasNull())
                largeCustomerFacilityAttribute.setInauguralDate(inauguralDate.toLocalDate());

            Date yearOpened = rs.getDate("yearopened");
            if (!rs.wasNull())
                largeCustomerFacilityAttribute.setYearOpened(Year.of(yearOpened.toLocalDate().getYear()));

            Date yearClosed = rs.getDate("yearclosed");
            if (!rs.wasNull())
                largeCustomerFacilityAttribute.setYearClosed(Year.of(yearClosed.toLocalDate().getYear()));

            largeCustomerFacilityAttribute.setKeyTenants(rs.getString("keytenants"));

            boolean availability = rs.getBoolean("availability");
            if (!rs.wasNull())
                largeCustomerFacilityAttribute.setAvailability(availability);

            String urbanPlanType = rs.getString("largecustomerfacilityattribute_urbanplantype");
            if (!rs.wasNull()) {
                Code code = new Code(urbanPlanType);
                code.setCodeSpace(rs.getString("largecustomerfacilityattribute_urbanplantype_codespace"));
                largeCustomerFacilityAttribute.setUrbanPlanType(code);
            }

            String areaClassificationType = rs.getString("largecustomerfacilityattribute_areaclassificationtype");
            if (!rs.wasNull()) {
                Code code = new Code(areaClassificationType);
                code.setCodeSpace(rs.getString("largecustomerfacilityattribute_areaclassification_codespace"));
                largeCustomerFacilityAttribute.setAreaClassificationType(code);
            }

            for (SplitValue splitValue : valueSplitter.split(rs.getString("largecustomerfacilityattribute_districtsandzonestype"), rs.getString("largecustomerfacilityattribute_districtsandzonest_codespace"))) {
                Code code = new Code(splitValue.result(0));
                code.setCodeSpace(splitValue.result(1));
                largeCustomerFacilityAttribute.getDistrictsAndZonesType().add(code);
            }

            String landUseType = rs.getString("largecustomerfacilityattribute_landusetype");
            if (!rs.wasNull()) {
                Code code = new Code(landUseType);
                code.setCodeSpace(rs.getString("largecustomerfacilityattribute_landusetype_codespace"));
                largeCustomerFacilityAttribute.setLandUseType(code);
            }

            largeCustomerFacilityAttribute.setReference(rs.getString("largecustomerfacilityattribute_reference"));

            largeCustomerFacilityAttribute.setNote(rs.getString("largecustomerfacilityattribute_note"));

            Date surveyYear = rs.getDate("largecustomerfacilityattribute_surveyyear");
            if (!rs.wasNull())
                largeCustomerFacilityAttribute.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));
        }

        if (buildingAttribute instanceof BuildingDisasterRiskAttribute) {
            BuildingDisasterRiskAttribute buildingDisasterRiskAttribute = (BuildingDisasterRiskAttribute) buildingAttribute;

            String description = rs.getString("description");
            if (!rs.wasNull()) {
                Code code = new Code(description);
                code.setCodeSpace(rs.getString("description_codespace"));
                buildingDisasterRiskAttribute.setDescription(code);
            }
        }

        if (buildingAttribute instanceof BuildingLandSlideRiskAttribute) {
            BuildingLandSlideRiskAttribute buildingLandSlideRiskAttribute = (BuildingLandSlideRiskAttribute) buildingAttribute;

            String areaType = rs.getString("areatype");
            if (!rs.wasNull()) {
                Code code = new Code(areaType);
                code.setCodeSpace(rs.getString("areatype_codespace"));
                buildingLandSlideRiskAttribute.setAreaType(code);
            }
        }

        if (buildingAttribute instanceof BuildingFloodingRiskAttribute) {
            BuildingFloodingRiskAttribute buildingFloodingRiskAttribute = (BuildingFloodingRiskAttribute) buildingAttribute;

            String rank = rs.getString("rank");
            if (!rs.wasNull()) {
                Code code = new Code(rank);
                code.setCodeSpace(rs.getString("rank_codespace"));
                buildingFloodingRiskAttribute.setRank(code);
            }

            String rankOrg = rs.getString("rankorg");
            if (!rs.wasNull()) {
                Code code = new Code(rankOrg);
                code.setCodeSpace(rs.getString("rankorg_codespace"));
                buildingFloodingRiskAttribute.setRankOrg(code);
            }

            double depth = rs.getDouble("depth");
            if (!rs.wasNull()) {
                Length length = new Length(depth);
                length.setUom(rs.getString("depth_uom"));
                buildingFloodingRiskAttribute.setDepth(length);
            }
        }

        if (buildingAttribute instanceof BuildingRiverFloodingRiskAttribute) {
            BuildingRiverFloodingRiskAttribute buildingRiverFloodingRiskAttribute = (BuildingRiverFloodingRiskAttribute) buildingAttribute;

            String adminType = rs.getString("admintype");
            if (!rs.wasNull()) {
                Code code = new Code(adminType);
                code.setCodeSpace(rs.getString("admintype_codespace"));
                buildingRiverFloodingRiskAttribute.setAdminType(code);
            }

            String scale = rs.getString("scale");
            if (!rs.wasNull()) {
                Code code = new Code(scale);
                code.setCodeSpace(rs.getString("scale_codespace"));
                buildingRiverFloodingRiskAttribute.setScale(code);
            }

            double duration = rs.getDouble("duration");
            if (!rs.wasNull()) {
                Measure measure = new Measure(duration);
                measure.setUom(rs.getString("duration_uom"));
                buildingRiverFloodingRiskAttribute.setDuration(measure);
            }
        }

        if (buildingAttribute instanceof BuildingDataQualityAttribute) {
            BuildingDataQualityAttribute buildingDataQualityAttribute = (BuildingDataQualityAttribute) buildingAttribute;

            for (SplitValue splitValue : valueSplitter.split(rs.getString("srcscale"), rs.getString("srcscale_codespace"))) {
                Code code = new Code(splitValue.result(0));
                code.setCodeSpace(splitValue.result(1));
                buildingDataQualityAttribute.getSrcScale().add(code);
            }

            for (SplitValue splitValue : valueSplitter.split(rs.getString("geometrysrcdesc"), rs.getString("geometrysrcdesc_codespace"))) {
                Code code = new Code(splitValue.result(0));
                code.setCodeSpace(splitValue.result(1));
                buildingDataQualityAttribute.getGeometrySrcDesc().add(code);
            }

            for (SplitValue splitValue : valueSplitter.split(rs.getString("thematicsrcdesc"), rs.getString("thematicsrcdesc_codespace"))) {
                Code code = new Code(splitValue.result(0));
                code.setCodeSpace(splitValue.result(1));
                buildingDataQualityAttribute.getThematicSrcDesc().add(code);
            }

            for (SplitValue splitValue : valueSplitter.split(rs.getString("appearancesrcdesc"), rs.getString("appearancesrcdesc_codespace"))) {
                Code code = new Code(splitValue.result(0));
                code.setCodeSpace(splitValue.result(1));
                buildingDataQualityAttribute.getAppearanceSrcDesc().add(code);
            }

            String lod1HeightType = rs.getString("lod1heighttype");
            if (!rs.wasNull()) {
                Code code = new Code(lod1HeightType);
                code.setCodeSpace(rs.getString("lod1heighttype_codespace"));
                buildingDataQualityAttribute.setLod1HeightType(code);
            }

            for (SplitValue splitValue : valueSplitter.split(rs.getString("lodtype")))
                buildingDataQualityAttribute.getLodType().add(splitValue.result(0));
        }
        return buildingAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
