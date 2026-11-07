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

import org.citydb.core.ade.ADEExtensionException;
import org.citydb.core.ade.ADEObjectMapper;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.ComplexType;
import org.citydb.core.database.schema.mapping.SchemaMapping;
import org.citygml4j.ade.iur.model.common.AbstractDataType;
import org.citygml4j.ade.iur.model.module.StatisticalGridModule;
import org.citygml4j.ade.iur.model.module.UrbanFunctionModule;
import org.citygml4j.ade.iur.model.urf.*;
import org.citygml4j.ade.iur.model.urg.LandUseDiversion;
import org.citygml4j.ade.iur.model.urg.*;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.ade.iur.model.urt.*;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;

import java.util.HashMap;
import java.util.Map;

public class ObjectMapper implements ADEObjectMapper {
    private final Map<Class<? extends AbstractGML>, Integer> objectClassIds = new HashMap<>();
    private final Map<Class<? extends AbstractDataType>, Integer> typeClassIds = new HashMap<>();

    public void populateObjectClassIds(SchemaMapping schemaMapping) throws ADEExtensionException {
        for (AbstractObjectType<?> type : schemaMapping.getAbstractObjectTypes()) {
            int objectClassId = type.getObjectClassId();

            switch (type.getPath()) {
                case "Population":
                    objectClassIds.put(Population.class, objectClassId);
                    break;
                case "PublicTransitAccessibility":
                    objectClassIds.put(PublicTransitAccessibility.class, objectClassId);
                    break;
                case "LandPrice":
                    objectClassIds.put(LandPrice.class, objectClassId);
                    break;
                case "Households":
                    objectClassIds.put(Households.class, objectClassId);
                    break;
                case "OfficesAndEmployees":
                    objectClassIds.put(OfficesAndEmployees.class, objectClassId);
                    break;
                case "GenericGridCell":
                    objectClassIds.put(GenericGridCell.class, objectClassId);
                    break;
                case "Route":
                    objectClassIds.put(Route.class, objectClassId);
                    break;
                case "Agency":
                    objectClassIds.put(Agency.class, objectClassId);
                    break;
                case "Stop":
                    objectClassIds.put(Stop.class, objectClassId);
                    break;
                case "Level":
                    objectClassIds.put(Level.class, objectClassId);
                    break;
                case "Trip":
                    objectClassIds.put(Trip.class, objectClassId);
                    break;
                case "Calendar":
                    objectClassIds.put(Calendar.class, objectClassId);
                    break;
                case "CalendarDate":
                    objectClassIds.put(CalendarDate.class, objectClassId);
                    break;
                case "Office":
                    objectClassIds.put(Office.class, objectClassId);
                    break;
                case "Shape":
                    objectClassIds.put(Shape.class, objectClassId);
                    break;
                case "FareAttribute":
                    objectClassIds.put(FareAttribute.class, objectClassId);
                    break;
                case "Pathway":
                    objectClassIds.put(Pathway.class, objectClassId);
                    break;
                case "TranslationJP":
                    objectClassIds.put(TranslationJP.class, objectClassId);
                    break;
                case "Attribution":
                    objectClassIds.put(Attribution.class, objectClassId);
                    break;
                case "Administration":
                    objectClassIds.put(Administration.class, objectClassId);
                    break;
                case "Agreement":
                    objectClassIds.put(Agreement.class, objectClassId);
                    break;
                case "AircraftNoiseControlZone":
                    objectClassIds.put(AircraftNoiseControlZone.class, objectClassId);
                    break;
                case "AreaClassification":
                    objectClassIds.put(AreaClassification.class, objectClassId);
                    break;
                case "Boundary":
                    objectClassIds.put(Boundary.class, objectClassId);
                    break;
                case "CensusBlock":
                    objectClassIds.put(CensusBlock.class, objectClassId);
                    break;
                case "CollectiveFacilitiesForReconstruction":
                    objectClassIds.put(CollectiveFacilitiesForReconstruction.class, objectClassId);
                    break;
                case "CollectiveFacilitiesForReconstructionAndRevitalization":
                    objectClassIds.put(CollectiveFacilitiesForReconstructionAndRevitalization.class, objectClassId);
                    break;
                case "CollectiveFacilitiesForTsunamiDisasterPrevention":
                    objectClassIds.put(CollectiveFacilitiesForTsunamiDisasterPrevention.class, objectClassId);
                    break;
                case "CollectiveGovernmentAndPublicOfficeFacilities":
                    objectClassIds.put(CollectiveGovernmentAndPublicOfficeFacilities.class, objectClassId);
                    break;
                case "CollectiveHousingFacilities":
                    objectClassIds.put(CollectiveHousingFacilities.class, objectClassId);
                    break;
                case "CollectiveUrbanDisasterPreventionFacilities":
                    objectClassIds.put(CollectiveUrbanDisasterPreventionFacilities.class, objectClassId);
                    break;
                case "ConservationZoneForClustersOfTraditionalStructures":
                    objectClassIds.put(ConservationZoneForClustersOfTraditionalStructures.class, objectClassId);
                    break;
                case "DevelopmentProject":
                    objectClassIds.put(DevelopmentProject.class, objectClassId);
                    break;
                case "DisasterDamage":
                    objectClassIds.put(DisasterDamage.class, objectClassId);
                    break;
                case "DisasterPreventionBase":
                    objectClassIds.put(DisasterPreventionBase.class, objectClassId);
                    break;
                case "DisasterPreventionBlockImprovementProject":
                    objectClassIds.put(DisasterPreventionBlockImprovementProject.class, objectClassId);
                    break;
                case "DisasterPreventionBlockImprovementZonePlan":
                    objectClassIds.put(DisasterPreventionBlockImprovementZonePlan.class, objectClassId);
                    break;
                case "DistributionBusinessPark":
                    objectClassIds.put(DistributionBusinessPark.class, objectClassId);
                    break;
                case "DistributionBusinessZone":
                    objectClassIds.put(DistributionBusinessZone.class, objectClassId);
                    break;
                case "District":
                    objectClassIds.put(District.class, objectClassId);
                    break;
                case "DistrictDevelopmentPlan":
                    objectClassIds.put(DistrictDevelopmentPlan.class, objectClassId);
                    break;
                case "DistrictFacility":
                    objectClassIds.put(DistrictFacility.class, objectClassId);
                    break;
                case "DistrictImprovementPlanForDisasterPreventionBlockImprovementZonePlan":
                    objectClassIds.put(DistrictImprovementPlanForDisasterPreventionBlockImprovementZonePlan.class, objectClassId);
                    break;
                case "DistrictImprovementPlanForHistoricSceneryMaintenanceAndImprovementDistrict":
                    objectClassIds.put(DistrictImprovementPlanForHistoricSceneryMaintenanceAndImprovementDistrict.class, objectClassId);
                    break;
                case "DistrictPlan":
                    objectClassIds.put(DistrictPlan.class, objectClassId);
                    break;
                case "DistrictsAndZones":
                    objectClassIds.put(DistrictsAndZones.class, objectClassId);
                    break;
                case "EducationalAndCulturalFacility":
                    objectClassIds.put(EducationalAndCulturalFacility.class, objectClassId);
                    break;
                case "ExceptionalFloorAreaRateDistrict":
                    objectClassIds.put(ExceptionalFloorAreaRateDistrict.class, objectClassId);
                    break;
                case "FirePreventionDistrict":
                    objectClassIds.put(FirePreventionDistrict.class, objectClassId);
                    break;
                case "FireProtectionFacility":
                    objectClassIds.put(FireProtectionFacility.class, objectClassId);
                    break;
                case "FloodPreventionFacility":
                    objectClassIds.put(FloodPreventionFacility.class, objectClassId);
                    break;
                case "GlobalHubCityDevelopmentProject":
                    objectClassIds.put(GlobalHubCityDevelopmentProject.class, objectClassId);
                    break;
                case "GreenSpaceConservationDistrict":
                    objectClassIds.put(GreenSpaceConservationDistrict.class, objectClassId);
                    break;
                case "HeightControlDistrict":
                    objectClassIds.put(HeightControlDistrict.class, objectClassId);
                    break;
                case "HighLevelUseDistrict":
                    objectClassIds.put(HighLevelUseDistrict.class, objectClassId);
                    break;
                case "HighRiseResidentialAttractionDistrict":
                    objectClassIds.put(HighRiseResidentialAttractionDistrict.class, objectClassId);
                    break;
                case "HistoricSceneryMaintenanceAndImprovementDistrictPlan":
                    objectClassIds.put(HistoricSceneryMaintenanceAndImprovementDistrictPlan.class, objectClassId);
                    break;
                case "HousingControlArea":
                    objectClassIds.put(HousingControlArea.class, objectClassId);
                    break;
                case "HubCity":
                    objectClassIds.put(HubCity.class, objectClassId);
                    break;
                case "IndustrialParkDevelopmentProject":
                    objectClassIds.put(IndustrialParkDevelopmentProject.class, objectClassId);
                    break;
                case "LandReadjustmentProject":
                    objectClassIds.put(LandReadjustmentProject.class, objectClassId);
                    break;
                case "LandReadjustmentPromotionArea":
                    objectClassIds.put(LandReadjustmentPromotionArea.class, objectClassId);
                    break;
                case "LandReadjustmentPromotionAreasForCoreBusinessUrbanDevelopment":
                    objectClassIds.put(LandReadjustmentPromotionAreasForCoreBusinessUrbanDevelopment.class, objectClassId);
                    break;
                case "LandUseDiversion":
                    if (type.getSchema().matchesNamespaceURI(UrbanFunctionModule.v2_0.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.urf.LandUseDiversion.class, objectClassId);
                    else if (type.getSchema().matchesNamespaceURI(StatisticalGridModule.v1_4.getNamespaceURI()))
                        objectClassIds.put(LandUseDiversion.class, objectClassId);
                    break;
                case "LandscapeZone":
                    objectClassIds.put(LandscapeZone.class, objectClassId);
                    break;
                case "MarketsSlaughterhousesCrematoria":
                    objectClassIds.put(MarketsSlaughterhousesCrematoria.class, objectClassId);
                    break;
                case "MedicalFacility":
                    objectClassIds.put(MedicalFacility.class, objectClassId);
                    break;
                case "NewHousingAndUrbanDevelopmentProject":
                    objectClassIds.put(NewHousingAndUrbanDevelopmentProject.class, objectClassId);
                    break;
                case "NewUrbanInfrastructureProject":
                    objectClassIds.put(NewUrbanInfrastructureProject.class, objectClassId);
                    break;
                case "OpenSpaceForPublicUse":
                    objectClassIds.put(OpenSpaceForPublicUse.class, objectClassId);
                    break;
                case "ParkingPlaceDevelopmentZone":
                    objectClassIds.put(ParkingPlaceDevelopmentZone.class, objectClassId);
                    break;
                case "Pollution":
                    objectClassIds.put(Pollution.class, objectClassId);
                    break;
                case "PortZone":
                    objectClassIds.put(PortZone.class, objectClassId);
                    break;
                case "PrivateUrbanRenewalProjectPlan":
                    objectClassIds.put(PrivateUrbanRenewalProjectPlan.class, objectClassId);
                    break;
                case "ProductiveGreenZone":
                    objectClassIds.put(ProductiveGreenZone.class, objectClassId);
                    break;
                case "ProjectPromotionArea":
                    objectClassIds.put(ProjectPromotionArea.class, objectClassId);
                    break;
                case "PromotionDistrict":
                    objectClassIds.put(PromotionDistrict.class, objectClassId);
                    break;
                case "PublicTransitFacility":
                    objectClassIds.put(PublicTransitFacility.class, objectClassId);
                    break;
                case "QuasiUrbanPlanningArea":
                    objectClassIds.put(QuasiUrbanPlanningArea.class, objectClassId);
                    break;
                case "Recreations":
                    objectClassIds.put(Recreations.class, objectClassId);
                    break;
                case "Regulation":
                    objectClassIds.put(Regulation.class, objectClassId);
                    break;
                case "ResidentialBlockConstructionProject":
                    objectClassIds.put(ResidentialBlockConstructionProject.class, objectClassId);
                    break;
                case "ResidentialBlockConstructionPromotionArea":
                    objectClassIds.put(ResidentialBlockConstructionPromotionArea.class, objectClassId);
                    break;
                case "ResidentialEnvironmentImprovementDistrict":
                    objectClassIds.put(ResidentialEnvironmentImprovementDistrict.class, objectClassId);
                    break;
                case "RoadsideDistrictFacility":
                    objectClassIds.put(RoadsideDistrictFacility.class, objectClassId);
                    break;
                case "RoadsideDistrictImprovementPlan":
                    objectClassIds.put(RoadsideDistrictImprovementPlan.class, objectClassId);
                    break;
                case "RoadsideDistrictPlan":
                    objectClassIds.put(RoadsideDistrictPlan.class, objectClassId);
                    break;
                case "RuralDistrictFacility":
                    objectClassIds.put(RuralDistrictFacility.class, objectClassId);
                    break;
                case "RuralDistrictImprovementPlan":
                    objectClassIds.put(RuralDistrictImprovementPlan.class, objectClassId);
                    break;
                case "RuralDistrictPlan":
                    objectClassIds.put(RuralDistrictPlan.class, objectClassId);
                    break;
                case "SandControlFacility":
                    objectClassIds.put(SandControlFacility.class, objectClassId);
                    break;
                case "ScenicDistrict":
                    objectClassIds.put(ScenicDistrict.class, objectClassId);
                    break;
                case "ScheduledAreaForCollectiveGovernmentAndPublicOfficeFacilities":
                    objectClassIds.put(ScheduledAreaForCollectiveGovernmentAndPublicOfficeFacilities.class, objectClassId);
                    break;
                case "ScheduledAreaForCollectiveHousingFacilities":
                    objectClassIds.put(ScheduledAreaForCollectiveHousingFacilities.class, objectClassId);
                    break;
                case "ScheduledAreaForDistributionBusinessPark":
                    objectClassIds.put(ScheduledAreaForDistributionBusinessPark.class, objectClassId);
                    break;
                case "ScheduledAreaForIndustrialParkDevelopmentProjects":
                    objectClassIds.put(ScheduledAreaForIndustrialParkDevelopmentProjects.class, objectClassId);
                    break;
                case "ScheduledAreaForNewHousingAndUrbanDevelopmentProjects":
                    objectClassIds.put(ScheduledAreaForNewHousingAndUrbanDevelopmentProjects.class, objectClassId);
                    break;
                case "ScheduledAreaForNewUrbanInfrastructureProjects":
                    objectClassIds.put(ScheduledAreaForNewUrbanInfrastructureProjects.class, objectClassId);
                    break;
                case "ScheduledAreaForUrbanDevelopmentProject":
                    objectClassIds.put(ScheduledAreaForUrbanDevelopmentProject.class, objectClassId);
                    break;
                case "SedimentDisasterProneArea":
                    objectClassIds.put(SedimentDisasterProneArea.class, objectClassId);
                    break;
                case "SnowProtectionFacility":
                    objectClassIds.put(SnowProtectionFacility.class, objectClassId);
                    break;
                case "SocialWelfareFacility":
                    objectClassIds.put(SocialWelfareFacility.class, objectClassId);
                    break;
                case "SpecialGreenSpaceConservationDistrict":
                    objectClassIds.put(SpecialGreenSpaceConservationDistrict.class, objectClassId);
                    break;
                case "SpecialUrbanRenaissanceDistrict":
                    objectClassIds.put(SpecialUrbanRenaissanceDistrict.class, objectClassId);
                    break;
                case "SpecialUseAttractionDistrict":
                    objectClassIds.put(SpecialUseAttractionDistrict.class, objectClassId);
                    break;
                case "SpecialUseDistrict":
                    objectClassIds.put(SpecialUseDistrict.class, objectClassId);
                    break;
                case "SpecialUseRestrictionDistrict":
                    objectClassIds.put(SpecialUseRestrictionDistrict.class, objectClassId);
                    break;
                case "SpecialZoneForPreservationOfHistoricalLandscape":
                    objectClassIds.put(SpecialZoneForPreservationOfHistoricalLandscape.class, objectClassId);
                    break;
                case "SpecifiedBlock":
                    objectClassIds.put(SpecifiedBlock.class, objectClassId);
                    break;
                case "SpecifiedBuildingZoneImprovementPlan":
                    objectClassIds.put(SpecifiedBuildingZoneImprovementPlan.class, objectClassId);
                    break;
                case "SpecifiedDisasterPreventionBlockImprovementZone":
                    objectClassIds.put(SpecifiedDisasterPreventionBlockImprovementZone.class, objectClassId);
                    break;
                case "SpecifiedUrgentUrbanRenewalArea":
                    objectClassIds.put(SpecifiedUrgentUrbanRenewalArea.class, objectClassId);
                    break;
                case "SupplyFacility":
                    objectClassIds.put(SupplyFacility.class, objectClassId);
                    break;
                case "TelecommunicationFacility":
                    objectClassIds.put(TelecommunicationFacility.class, objectClassId);
                    break;
                case "ThreeDimensionalExtent":
                    objectClassIds.put(ThreeDimensionalExtent.class, objectClassId);
                    break;
                case "TideFacility":
                    objectClassIds.put(TideFacility.class, objectClassId);
                    break;
                case "TrafficFacility":
                    objectClassIds.put(TrafficFacility.class, objectClassId);
                    break;
                case "TreatmentFacility":
                    objectClassIds.put(TreatmentFacility.class, objectClassId);
                    break;
                case "TreePlantingDistrict":
                    objectClassIds.put(TreePlantingDistrict.class, objectClassId);
                    break;
                case "UnusedLandUsePromotionArea":
                    objectClassIds.put(UnusedLandUsePromotionArea.class, objectClassId);
                    break;
                case "UrbanDevelopmentProject":
                    objectClassIds.put(UrbanDevelopmentProject.class, objectClassId);
                    break;
                case "UrbanDisasterRecoveryPromotionArea":
                    objectClassIds.put(UrbanDisasterRecoveryPromotionArea.class, objectClassId);
                    break;
                case "UrbanFacility":
                    objectClassIds.put(UrbanFacility.class, objectClassId);
                    break;
                case "UrbanFacilityStipulatedByCabinetOrder":
                    objectClassIds.put(UrbanFacilityStipulatedByCabinetOrder.class, objectClassId);
                    break;
                case "UrbanPlanningArea":
                    objectClassIds.put(UrbanPlanningArea.class, objectClassId);
                    break;
                case "UrbanRedevelopmentProject":
                    objectClassIds.put(UrbanRedevelopmentProject.class, objectClassId);
                    break;
                case "UrbanRedevelopmentPromotionArea":
                    objectClassIds.put(UrbanRedevelopmentPromotionArea.class, objectClassId);
                    break;
                case "UrbanRenewalProject":
                    objectClassIds.put(UrbanRenewalProject.class, objectClassId);
                    break;
                case "Urbanization":
                    objectClassIds.put(Urbanization.class, objectClassId);
                    break;
                case "UrgentUrbanRenewalArea":
                    objectClassIds.put(UrgentUrbanRenewalArea.class, objectClassId);
                    break;
                case "UseDistrict":
                    objectClassIds.put(UseDistrict.class, objectClassId);
                    break;
                case "Waterway":
                    objectClassIds.put(Waterway.class, objectClassId);
                    break;
                case "WindProtectionFacility":
                    objectClassIds.put(WindProtectionFacility.class, objectClassId);
                    break;
                case "ZonalDisasterPreventionFacility":
                    objectClassIds.put(ZonalDisasterPreventionFacility.class, objectClassId);
                    break;
                case "ZoneForPreservationOfHistoricalLandscape":
                    objectClassIds.put(ZoneForPreservationOfHistoricalLandscape.class, objectClassId);
                    break;
            }
        }

        for (ComplexType type : schemaMapping.getComplexTypes()) {
            int typeClassId = type.getObjectClassId();

            switch (type.getPath()) {
                case "FeedInfo":
                    typeClassIds.put(FeedInfo.class, typeClassId);
                    break;
                case "Translation":
                    typeClassIds.put(Translation.class, typeClassId);
                    break;
                case "Transfer":
                    typeClassIds.put(Transfer.class, typeClassId);
                    break;
                case "Frequency":
                    typeClassIds.put(Frequency.class, typeClassId);
                    break;
                case "StopTime":
                    typeClassIds.put(StopTime.class, typeClassId);
                    break;
                case "FareRule":
                    typeClassIds.put(FareRule.class, typeClassId);
                    break;
                case "BuildingAttribute":
                    typeClassIds.put(BuildingAttribute.class, typeClassId);
                    break;
                case "BuildingIDAttribute":
                    typeClassIds.put(BuildingIDAttribute.class, typeClassId);
                    break;
                case "BuildingDetailAttribute":
                    typeClassIds.put(BuildingDetailAttribute.class, typeClassId);
                    break;
                case "LargeCustomerFacilityAttribute":
                    typeClassIds.put(LargeCustomerFacilityAttribute.class, typeClassId);
                    break;
                case "BuildingDisasterRiskAttribute":
                    typeClassIds.put(BuildingDisasterRiskAttribute.class, typeClassId);
                    break;
                case "BuildingFloodingRiskAttribute":
                    typeClassIds.put(BuildingFloodingRiskAttribute.class, typeClassId);
                    break;
                case "BuildingRiverFloodingRiskAttribute":
                    typeClassIds.put(BuildingRiverFloodingRiskAttribute.class, typeClassId);
                    break;
                case "BuildingTsunamiRiskAttribute":
                    typeClassIds.put(BuildingTsunamiRiskAttribute.class, typeClassId);
                    break;
                case "BuildingInlandFloodingRiskAttribute":
                    typeClassIds.put(BuildingInlandFloodingRiskAttribute.class, typeClassId);
                    break;
                case "BuildingHighTideRiskAttribute":
                    typeClassIds.put(BuildingHighTideRiskAttribute.class, typeClassId);
                    break;
                case "BuildingLandSlideRiskAttribute":
                    typeClassIds.put(BuildingLandSlideRiskAttribute.class, typeClassId);
                    break;
                case "KeyValuePairAttribute":
                    typeClassIds.put(KeyValuePairAttribute.class, typeClassId);
                    break;
                case "BuildingDataQualityAttribute":
                    typeClassIds.put(BuildingDataQualityAttribute.class, typeClassId);
                    break;
                case "LandUseAttribute":
                    typeClassIds.put(LandUseAttribute.class, typeClassId);
                    break;
                case "LandUseDetailAttribute":
                    typeClassIds.put(LandUseDetailAttribute.class, typeClassId);
                    break;
                case "RoadAttribute":
                    typeClassIds.put(RoadAttribute.class, typeClassId);
                    break;
                case "TrafficVolumeAttribute":
                    typeClassIds.put(TrafficVolumeAttribute.class, typeClassId);
                    break;
                case "RoadStructureAttribute":
                    typeClassIds.put(RoadStructureAttribute.class, typeClassId);
                    break;
                case "RoadDataQualityAttribute":
                    typeClassIds.put(RoadDataQualityAttribute.class, typeClassId);
                    break;
                case "TrafficAreaAttribute":
                    typeClassIds.put(TrafficAreaAttribute.class, typeClassId);
                    break;
                case "TrafficAreaStructureAttribute":
                    typeClassIds.put(TrafficAreaStructureAttribute.class, typeClassId);
                    break;
                case "CityFurnitureAttribute":
                    typeClassIds.put(CityFurnitureAttribute.class, typeClassId);
                    break;
                case "CityFurnitureDetailAttribute":
                    typeClassIds.put(CityFurnitureDetailAttribute.class, typeClassId);
                    break;
                case "CityFurnitureDataQualityAttribute":
                    typeClassIds.put(CityFurnitureDataQualityAttribute.class, typeClassId);
                    break;
                case "VegetationAttribute":
                    typeClassIds.put(VegetationAttribute.class, typeClassId);
                    break;
                case "VegetationDataQualityAttribute":
                    typeClassIds.put(VegetationDataQualityAttribute.class, typeClassId);
                    break;
                case "WaterBodyAttribute":
                    typeClassIds.put(WaterBodyAttribute.class, typeClassId);
                    break;
                case "WaterBodyFloodingRiskAttribute":
                    typeClassIds.put(WaterBodyFloodingRiskAttribute.class, typeClassId);
                    break;
                case "WaterBodyRiverFloodingRiskAttribute":
                    typeClassIds.put(WaterBodyRiverFloodingRiskAttribute.class, typeClassId);
                    break;
                case "WaterBodyTsunamiRiskAttribute":
                    typeClassIds.put(WaterBodyTsunamiRiskAttribute.class, typeClassId);
                    break;
                case "WaterBodyHighTideRiskAttribute":
                    typeClassIds.put(WaterBodyHighTideRiskAttribute.class, typeClassId);
                    break;
                case "WaterBodyInlandFloodingRiskAttribute":
                    typeClassIds.put(WaterBodyInlandFloodingRiskAttribute.class, typeClassId);
                    break;
                case "UrbanRoadAttribute":
                    typeClassIds.put(UrbanRoadAttribute.class, typeClassId);
                    break;
                case "StructureDetails":
                    typeClassIds.put(StructureDetails.class, typeClassId);
                    break;
                case "UrbanRapidTransitRailroadAttribute":
                    typeClassIds.put(UrbanRapidTransitRailroadAttribute.class, typeClassId);
                    break;
                case "ParkingPlaceAttribute":
                    typeClassIds.put(ParkingPlaceAttribute.class, typeClassId);
                    break;
                case "VehicleTerminalAttribute":
                    typeClassIds.put(VehicleTerminalAttribute.class, typeClassId);
                    break;
                case "ParkAttribute":
                    typeClassIds.put(ParkAttribute.class, typeClassId);
                    break;
                case "WaterWorksAttribute":
                    typeClassIds.put(WaterWorksAttribute.class, typeClassId);
                    break;
                case "SewerSystemAttribute":
                    typeClassIds.put(SewerSystemAttribute.class, typeClassId);
                    break;
                case "NumberOfHouseholds":
                    typeClassIds.put(org.citygml4j.ade.iur.model.urf.NumberOfHouseholds.class, typeClassId);
                    break;
            }
        }
    }

    @Override
    public AbstractGML createObject(int objectClassId, CityGMLVersion version) {
        if (version == CityGMLVersion.v2_0_0) {
            for (Map.Entry<Class<? extends AbstractGML>, Integer> entry : objectClassIds.entrySet()) {
                if (entry.getValue() == objectClassId) {
                    try {
                        return entry.getKey().getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        //
                    }
                }
            }
        }

        return null;
    }

    @Override
    public int getObjectClassId(Class<? extends AbstractGML> adeObjectClass) {
        Integer objectClassId = objectClassIds.get(adeObjectClass);
        return objectClassId != null ? objectClassId : 0;
    }

    public <T> T createType(int objectClassId, Class<T> type) {
        for (Map.Entry<Class<? extends AbstractDataType>, Integer> entry : typeClassIds.entrySet()) {
            if (entry.getValue() == objectClassId) {
                try {
                    AbstractDataType dataType = entry.getKey().getDeclaredConstructor().newInstance();
                    return type.isInstance(dataType) ? type.cast(dataType) : null;
                } catch (Exception e) {
                    //
                }
            }
        }

        return null;
    }

    public int getTypeClassId(Class<? extends AbstractDataType> adeObjectClass) {
        Integer objectClassId = typeClassIds.get(adeObjectClass);
        return objectClassId != null ? objectClassId : 0;
    }
}
