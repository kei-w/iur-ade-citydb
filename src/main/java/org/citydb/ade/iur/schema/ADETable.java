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

package org.citydb.ade.iur.schema;

import org.citydb.ade.iur.importer.urf.*;
import org.citydb.ade.iur.importer.urg.*;
import org.citydb.ade.iur.importer.uro.*;
import org.citydb.ade.iur.importer.urt.*;
import org.citydb.core.ade.importer.ADEImporter;

public enum ADETable {
    ABSTRACTDISTRICTPLAN(AbstractDistrictPlanImporter.class),
    ABSTRACTD_TO_DISTRICTD(AbstractDistrictPlanToDistrictDevelopmentPlanImporter.class),
    ABSTRACTD_TO_ZONE(AbstractDistrictPlanToPromotionDistrictImporter.class),
    AGENCY(AgencyImporter.class),
    AREACLASSIFICATION(AreaClassificationImporter.class),
    AREAOFANNUALDIVERSIONS(AreaOfAnnualDiversionsImporter.class),
    ATTRIBUTION(AttributionImporter.class),
    BOUNDARY(BoundaryImporter.class),
    BUILDING(BuildingPropertiesImporter.class),
    BUILDINGATTRIBUTE(BuildingAttributeImporter.class),
    BUILDINGDATAQUALITYATT(BuildingDataQualityAttributeImporter.class),
    BUILDINGDETAILATTRIBUT(BuildingDetailAttributeImporter.class),
    BUILDINGDISASTERRISKAT(BuildingDisasterRiskAttributeImporter.class),
    BUILDINGFLOODINGRISKAT(BuildingFloodingRiskAttributeImporter.class),
    BUILDINGIDATTRIBUTE(BuildingIDAttributeImporter.class),
    BUILDINGRIVERFLOODINGR(BuildingRiverFloodingRiskAttributeImporter.class),
    BUILDING_TO_BUILDINGD(BuildingToBuildingDisasterRiskAttributeImporter.class),
    BUILDING_TO_KEYVALUEP(BuildingToKeyValuePairAttributeImporter.class),
    CALENDAR(CalendarImporter.class),
    CALENDARDATE(CalendarDateImporter.class),
    CENSUSBLOCK(CensusBlockImporter.class),
    CITYFURNITUREATTRIBUTE(CityFurnitureAttributeImporter.class),
    CITYFURNITUREDATAQUALI(CityFurnitureDataQualityAttributeImporter.class),
    CITYFURNITUREDETAILATT(CityFurnitureDetailAttributeImporter.class),
    CITYOBJECT(VegetationObjectPropertiesImporter.class),
    CITYOBJECTGROUP(org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter.class),
    CITYOBJECTGROUP_1(org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter.class),
    CITYOBJECTGROUP_2(org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter.class),
    CITYOBJECTGROUP_3(org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter.class),
    CITYOBJEC_TO_PUBLICTRA(null),
    CITY_FURNITURE(CityFurniturePropertiesImporter.class),
    COLLECTIVEFACILITIESFO(CollectiveFacilitiesForTsunamiDisasterPreventionImporter.class),
    COLLECTIVEFACILITIES_2(CollectiveFacilitiesForReconstructionAndRevitalizationImporter.class),
    COLLECTIVEFACILITIES_3(CollectiveFacilitiesForReconstructionImporter.class),
    COLLECTIVEGOVERNMENTAN(CollectiveGovernmentAndPublicOfficeFacilitiesImporter.class),
    COLLECTIVEHOUSINGFACIL(CollectiveHousingFacilitiesImporter.class),
    COLLECTIVEURBANDISASTE(CollectiveUrbanDisasterPreventionFacilitiesImporter.class),
    DESCRIPTION(DescriptionImporter.class),
    DEVELOPMENTPROJECT(DevelopmentProjectImporter.class),
    DISASTERDAMAGE(DisasterDamageImporter.class),
    DISASTERPREVENTIONBASE(DisasterPreventionBaseImporter.class),
    DISASTERPREVENTIONBLOC(DisasterPreventionBlockImprovementProjectImporter.class),
    DISASTERPREVENTIONBL_2(DisasterPreventionBlockImprovementZonePlanImporter.class),
    DISASTERP_TO_ZONALDISA(DisasterPreventionBlockImprovementZonePlanToZonalDisasterPreventionFacilityImporter.class),
    DISTRIBUTIONBUSINESSPA(DistributionBusinessParkImporter.class),
    DISTRIBUTIONBUSINESSZO(DistributionBusinessZoneImporter.class),
    DISTRICT(DistrictImporter.class),
    DISTRICTDEVELOPMENTPLA(DistrictDevelopmentPlanImporter.class),
    DISTRICTD_TO_DISTRICT(DistrictDevelopmentPlanToDistrictImporter.class),
    DISTRICTD_TO_ZONE(DistrictDevelopmentPlanToDistrictFacilityImporter.class),
    DISTRICTPLAN(DistrictPlanImporter.class),
    DISTRICTSANDZONES(DistrictsAndZonesImporter.class),
    EXCEPTIONALFLOORAREARA(ExceptionalFloorAreaRateDistrictImporter.class),
    FAREATTRIBUTE(FareAttributeImporter.class),
    FARERULE(FareRuleImporter.class),
    FEEDINFO(FeedInfoImporter.class),
    FREQUENCY(FrequencyImporter.class),
    GLOBALHUBCITYDEVELOPME(GlobalHubCityDevelopmentProjectImporter.class),
    HEIGHTCONTROLDISTRICT(HeightControlDistrictImporter.class),
    HIGHLEVELUSEDISTRICT(HighLevelUseDistrictImporter.class),
    HIGHRISERESIDENTIALATT(HighRiseResidentialAttractionDistrictImporter.class),
    HISTORICSCENERYMAINTEN(HistoricSceneryMaintenanceAndImprovementDistrictPlanImporter.class),
    HOUSEHOLDS(HouseholdsImporter.class),
    INDUSTRIALPARKDEVELOPM(IndustrialParkDevelopmentProjectImporter.class),
    KEYVALUEPAIR(KeyValuePairImporter.class),
    KEYVALUEPAIRATTRIBUTE(KeyValuePairAttributeImporter.class),
    LANDPRICE(LandPriceImporter.class),
    LANDPRICEPERLANDUSE(LandPricePerLandUseImporter.class),
    LANDPRICE_TO_LANDPRICE(null),
    LANDREADJUSTMENTPROJEC(LandReadjustmentProjectImporter.class),
    LANDSCAPEZONE(LandscapeZoneImporter.class),
    LANDUSEATTRIBUTE(LandUseDetailAttributeImporter.class),
    LANDUSEDIVERSION(LandUseDiversionImporter.class),
    LANDUSEDI_TO_AREAOFANN(null),
    LANDUSEDI_TO_NUMBEROFA(null),
    LAND_USE(LandUsePropertiesImporter.class),
    LARGECUSTOMERFACILITYA(LargeCustomerFacilityAttributeImporter.class),
    NEWHOUSINGANDURBANDEVE(NewHousingAndUrbanDevelopmentProjectImporter.class),
    NEWURBANINFRASTRUCTURE(NewUrbanInfrastructureProjectImporter.class),
    NUMBEROFANNUALDIVERSIO(NumberOfAnnualDiversionsImporter.class),
    NUMBEROFHOUSEHOLDS(org.citydb.ade.iur.importer.urg.NumberOfHouseholdsImporter.class),
    NUMBEROFHOUSEHOLDS_2(org.citydb.ade.iur.importer.urf.NumberOfHouseholdsImporter.class),
    OFFICE(OfficeImporter.class),
    OFFICESANDEMPLOYEES(OfficesAndEmployeesImporter.class),
    OPENSPACEFORPUBLICUSE(OpenSpaceForPublicUseImporter.class),
    PARKATTRIBUTE(ParkAttributeImporter.class),
    PARKINGPLACEATTRIBUTE(ParkingPlaceAttributeImporter.class),
    PATHWAY(PathwayImporter.class),
    POINT(PointImporter.class),
    POLLUTION(PollutionImporter.class),
    POPULATION(PopulationImporter.class),
    POPULATIONBYAGEANDSEX(PopulationByAgeAndSexImporter.class),
    POPULATIO_TO_POPULATIO(null),
    PORTZONE(PortZoneImporter.class),
    PRIVATEURBANRENEWALPRO(PrivateUrbanRenewalProjectPlanImporter.class),
    PRODUCTIVEGREENZONE(ProductiveGreenZoneImporter.class),
    PROJECTPROMOTIONAREA(ProjectPromotionAreaImporter.class),
    PUBLICTRANSIT(PublicTransitImporter.class),
    PUBLICTRANSITDATATYPE(PublicTransitDataTypeImporter.class),
    PUBLICTRANSITFACILITY(PublicTransitFacilityImporter.class),
    PUBLICTRA_TO_POINT(null),
    QUASIURBANPLANNINGAREA(QuasiUrbanPlanningAreaImporter.class),
    RECREATIONS(RecreationsImporter.class),
    RESIDENTIALBLOCKCONSTR(ResidentialBlockConstructionProjectImporter.class),
    RESIDENTIALENVIRONMENT(ResidentialEnvironmentImprovementDistrictImporter.class),
    ROADATTRIBUTE(RoadAttributeImporter.class),
    ROADDATAQUALITYATTRIBU(RoadDataQualityAttributeImporter.class),
    ROADSIDEDISTRICTIMPROV(RoadsideDistrictImprovementPlanImporter.class),
    ROADSIDEDISTRICTPLAN(RoadsideDistrictPlanImporter.class),
    ROADSTRUCTUREATTRIBUTE(RoadStructureAttributeImporter.class),
    ROUTE(RouteImporter.class),
    RURALDISTRICTIMPROVEME(RuralDistrictImprovementPlanImporter.class),
    SCENICDISTRICT(ScenicDistrictImporter.class),
    SCHEDULEDAREAFORURBAND(ScheduledAreaForUrbanDevelopmentProjectImporter.class),
    SEDIMENTDISASTERPRONEA(SedimentDisasterProneAreaImporter.class),
    SEWERSYSTEMATTRIBUTE(SewerSystemAttributeImporter.class),
    SPECIALGREENSPACECONSE(SpecialGreenSpaceConservationDistrictImporter.class),
    SPECIALURBANRENAISSANC(SpecialUrbanRenaissanceDistrictImporter.class),
    SPECIALUSEATTRACTIONDI(SpecialUseAttractionDistrictImporter.class),
    SPECIALUSEDISTRICT(SpecialUseDistrictImporter.class),
    SPECIALUSERESTRICTIOND(SpecialUseRestrictionDistrictImporter.class),
    SPECIFIEDBLOCK(SpecifiedBlockImporter.class),
    SPECIFIEDDISASTERPREVE(SpecifiedDisasterPreventionBlockImprovementZoneImporter.class),
    SPECIFIEDURGENTURBANRE(SpecifiedUrgentUrbanRenewalAreaImporter.class),
    SPECIFIED_TO_GLOBALHUB(SpecifiedUrgentUrbanRenewalAreaToGlobalHubCityDevelopmentProjectImporter.class),
    STATISTICALGRID(StatisticalGridImporter.class),
    STATISTIC_TO_KEYVALUEP(null),
    STOP(StopImporter.class),
    STOPTIME(StopTimeImporter.class),
    STRUCTUREDETAILS(StructureDetailsImporter.class),
    SUPPLYFACILITY(SupplyFacilityImporter.class),
    THREEDIMENSIONALEXTENT(ThreeDimensionalExtentImporter.class),
    TRAFFICAREAATTRIBUTE(TrafficAreaStructureAttributeImporter.class),
    TRAFFICFACILITY(TrafficFacilityImporter.class),
    TRAFFICVOLUMEATTRIBUTE(TrafficVolumeAttributeImporter.class),
    TRAFFIC_AREA(TrafficAreaPropertiesImporter.class),
    TRANSFER(TransferImporter.class),
    TRANSLATION(TranslationImporter.class),
    TRANSLATIONJP(TranslationJPImporter.class),
    TRANSPORTATION_COMPLEX(TransportationComplexPropertiesImporter.class),
    TREATMENTFACILITY(TreatmentFacilityImporter.class),
    TREEPLANTINGDISTRICT(TreePlantingDistrictImporter.class),
    TRIP(TripImporter.class),
    URBANDEVELOPMENTPROJEC(UrbanDevelopmentProjectImporter.class),
    URBANDISASTERRECOVERYP(UrbanDisasterRecoveryPromotionAreaImporter.class),
    URBANFACILITY(UrbanFacilityImporter.class),
    URBANFACILITYSTIPULATE(UrbanFacilityStipulatedByCabinetOrderImporter.class),
    URBANFACI_TO_THREEDIME(UrbanFacilityToThreeDimensionalExtentImporter.class),
    URBANFUNCTION(UrbanFunctionImporter.class),
    URBANFUNC_TO_CITYOBJEC(UrbanFunctionToCityObjectImporter.class),
    URBANIZATION(UrbanizationImporter.class),
    URBANPLANNINGAREA(UrbanPlanningAreaImporter.class),
    URBANRAPIDTRANSITRAILR(UrbanRapidTransitRailroadAttributeImporter.class),
    URBANRAPI_TO_STRUCTURE(UrbanRapidTransitRailroadAttributeToStructureDetailsImporter.class),
    URBANREDEVELOPMENTPROJ(UrbanRedevelopmentProjectImporter.class),
    URBANREDEVELOPMENTPROM(UrbanRedevelopmentPromotionAreaImporter.class),
    URBANRENEWALPROJECT(UrbanRenewalProjectImporter.class),
    URBANROADATTRIBUTE(UrbanRoadAttributeImporter.class),
    URBANROAD_TO_STRUCTURE(UrbanRoadAttributeToStructureDetailsImporter.class),
    URGENTURBANRENEWALAREA(UrgentUrbanRenewalAreaImporter.class),
    URGENTURB_TO_PRIVATEUR(UrgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter.class),
    URGENTURB_TO_SPECIALUR(UrgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter.class),
    URGENTURB_TO_SPECIFIED(UrgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter.class),
    USEDISTRICT(UseDistrictImporter.class),
    VEGETATIONATTRIBUTE(VegetationDataQualityAttributeImporter.class),
    VEHICLETERMINALATTRIBU(VehicleTerminalAttributeImporter.class),
    WATERBODY(WaterBodyPropertiesImporter.class),
    WATERBODYATTRIBUTE(WaterBodyAttributeImporter.class),
    WATERBODYRIVERFLOODING(WaterBodyRiverFloodingRiskAttributeImporter.class),
    WATERBODY_TO_WATERBODY(WaterBodyToWaterBodyFloodingRiskAttributeImporter.class),
    WATERWAY(WaterwayImporter.class),
    WATERWORKSATTRIBUTE(WaterWorksAttributeImporter.class),
    ZONALDISASTERPREVENTIO(ZonalDisasterPreventionFacilityImporter.class),
    ZONE(ZoneImporter.class),
    ZONE_TO_BOUNDARY(ZoneToBoundaryImporter.class);

    private final Class<? extends ADEImporter> importerClass;

    ADETable(Class<? extends ADEImporter> importerClass) {
        this.importerClass = importerClass;
    }

    public Class<? extends ADEImporter> getImporterClass() {
        return importerClass;
    }
}
