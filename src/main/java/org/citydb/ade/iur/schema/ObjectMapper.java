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
import org.citygml4j.ade.iur.model.module.PublicTransitModule;

import java.util.HashMap;
import java.util.Map;

public class ObjectMapper implements ADEObjectMapper {
    private final Map<Class<? extends AbstractGML>, Integer> objectClassIds = new HashMap<>();
    private final Map<Class<? extends AbstractDataType>, Integer> typeClassIds = new HashMap<>();

    public void populateObjectClassIds(SchemaMapping schemaMapping) throws ADEExtensionException {
        for (AbstractObjectType<?> type : schemaMapping.getAbstractObjectTypes()) {
            int objectClassId = type.getObjectClassId();

            switch (type.getPath()) {

                case "Administration":
                    objectClassIds.put(Administration.class, objectClassId);
                    break;
                case "Agency":
                    objectClassIds.put(Agency.class, objectClassId);
                    break;
                case "Agreement":
                    objectClassIds.put(Agreement.class, objectClassId);
                    break;
                case "AircraftNoiseControlZone":
                    objectClassIds.put(AircraftNoiseControlZone.class, objectClassId);
                    break;
                case "Appurtenance":
                    objectClassIds.put(Appurtenance.class, objectClassId);
                    break;
                case "AreaClassification":
                    objectClassIds.put(AreaClassification.class, objectClassId);
                    break;
                case "Attribution":
                    objectClassIds.put(Attribution.class, objectClassId);
                    break;
                case "Cable":
                    objectClassIds.put(Cable.class, objectClassId);
                    break;
                case "Calendar":
                    objectClassIds.put(Calendar.class, objectClassId);
                    break;
                case "CalendarDate":
                    objectClassIds.put(CalendarDate.class, objectClassId);
                    break;
                case "CensusBlock":
                    objectClassIds.put(CensusBlock.class, objectClassId);
                    break;
                case "ClosureSurface":
                    objectClassIds.put(ClosureSurface.class, objectClassId);
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
                case "ConstructionInstallation":
                    objectClassIds.put(ConstructionInstallation.class, objectClassId);
                    break;
                case "DID":
                    objectClassIds.put(DID.class, objectClassId);
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
                case "DistributionOfFloraAndFauna":
                    objectClassIds.put(DistributionOfFloraAndFauna.class, objectClassId);
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
                case "Duct":
                    objectClassIds.put(Duct.class, objectClassId);
                    break;
                case "EducationalAndCulturalFacility":
                    objectClassIds.put(EducationalAndCulturalFacility.class, objectClassId);
                    break;
                case "ElectricityCable":
                    objectClassIds.put(ElectricityCable.class, objectClassId);
                    break;
                case "ExceptionalFloorAreaRateDistrict":
                    objectClassIds.put(ExceptionalFloorAreaRateDistrict.class, objectClassId);
                    break;
                case "FareAttribute":
                    objectClassIds.put(FareAttribute.class, objectClassId);
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
                case "GenericGridCell":
                    objectClassIds.put(GenericGridCell.class, objectClassId);
                    break;
                case "GlobalHubCityDevelopmentProject":
                    objectClassIds.put(GlobalHubCityDevelopmentProject.class, objectClassId);
                    break;
                case "GreenSpace":
                    objectClassIds.put(GreenSpace.class, objectClassId);
                    break;
                case "GreenSpaceConservationDistrict":
                    objectClassIds.put(GreenSpaceConservationDistrict.class, objectClassId);
                    break;
                case "GroundSurface":
                    objectClassIds.put(GroundSurface.class, objectClassId);
                    break;
                case "Handhole":
                    objectClassIds.put(Handhole.class, objectClassId);
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
                case "Households":
                    objectClassIds.put(Households.class, objectClassId);
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
                case "LandPrice":
                    if (type.getSchema().matchesNamespaceURI(UrbanFunctionModule.v3_2.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.urf.LandPrice.class, objectClassId);
                    else if (type.getSchema().matchesNamespaceURI(StatisticalGridModule.v3_2.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.urg.LandPrice.class, objectClassId);
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
                    if (type.getSchema().matchesNamespaceURI(UrbanFunctionModule.v3_2.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.urf.LandUseDiversion.class, objectClassId);
                    else if (type.getSchema().matchesNamespaceURI(StatisticalGridModule.v3_2.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.urg.LandUseDiversion.class, objectClassId);
                    break;
                case "LandscapeZone":
                    objectClassIds.put(LandscapeZone.class, objectClassId);
                    break;
                case "LandslideDisaster":
                    objectClassIds.put(LandslideDisaster.class, objectClassId);
                    break;
                case "Level":
                    objectClassIds.put(Level.class, objectClassId);
                    break;
                case "Manhole":
                    objectClassIds.put(Manhole.class, objectClassId);
                    break;
                case "MarketsSlaughterhousesCrematoria":
                    objectClassIds.put(MarketsSlaughterhousesCrematoria.class, objectClassId);
                    break;
                case "MedicalFacility":
                    objectClassIds.put(MedicalFacility.class, objectClassId);
                    break;
                case "NaturalConditions":
                    objectClassIds.put(NaturalConditions.class, objectClassId);
                    break;
                case "NewHousing":
                    objectClassIds.put(NewHousing.class, objectClassId);
                    break;
                case "NewHousingAndUrbanDevelopmentProject":
                    objectClassIds.put(NewHousingAndUrbanDevelopmentProject.class, objectClassId);
                    break;
                case "NewUrbanInfrastructureProject":
                    objectClassIds.put(NewUrbanInfrastructureProject.class, objectClassId);
                    break;
                case "Office":
                    objectClassIds.put(Office.class, objectClassId);
                    break;
                case "OfficesAndEmployees":
                    objectClassIds.put(OfficesAndEmployees.class, objectClassId);
                    break;
                case "OilGasChemicalsPipe":
                    objectClassIds.put(OilGasChemicalsPipe.class, objectClassId);
                    break;
                case "OpenSpaceForPublicUse":
                    objectClassIds.put(OpenSpaceForPublicUse.class, objectClassId);
                    break;
                case "OtherConstruction":
                    objectClassIds.put(OtherConstruction.class, objectClassId);
                    break;
                case "OuterCeilingSurface":
                    objectClassIds.put(OuterCeilingSurface.class, objectClassId);
                    break;
                case "OuterFloorSurface":
                    objectClassIds.put(OuterFloorSurface.class, objectClassId);
                    break;
                case "ParkingPlaceDevelopmentZone":
                    objectClassIds.put(ParkingPlaceDevelopmentZone.class, objectClassId);
                    break;
                case "Pathway":
                    objectClassIds.put(Pathway.class, objectClassId);
                    break;
                case "Pipe":
                    objectClassIds.put(Pipe.class, objectClassId);
                    break;
                case "PointCloud":
                    objectClassIds.put(PointCloud.class, objectClassId);
                    break;
                case "Pollution":
                    objectClassIds.put(Pollution.class, objectClassId);
                    break;
                case "Population":
                    objectClassIds.put(Population.class, objectClassId);
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
                case "PublicLand":
                    objectClassIds.put(PublicLand.class, objectClassId);
                    break;
                case "PublicTransitAccessibility":
                    objectClassIds.put(PublicTransitAccessibility.class, objectClassId);
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
                case "ResidenceAttractionArea":
                    objectClassIds.put(ResidenceAttractionArea.class, objectClassId);
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
                case "RoofSurface":
                    objectClassIds.put(RoofSurface.class, objectClassId);
                    break;
                case "Route":
                    objectClassIds.put(Route.class, objectClassId);
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
                case "ScenicResource":
                    objectClassIds.put(ScenicResource.class, objectClassId);
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
                case "SewerPipe":
                    objectClassIds.put(SewerPipe.class, objectClassId);
                    break;
                case "Shape":
                    objectClassIds.put(Shape.class, objectClassId);
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
                case "StatusOfSiteSeeing":
                    objectClassIds.put(StatusOfSiteSeeing.class, objectClassId);
                    break;
                case "Stop":
                    objectClassIds.put(Stop.class, objectClassId);
                    break;
                case "SupplyFacility":
                    objectClassIds.put(SupplyFacility.class, objectClassId);
                    break;
                case "Target":
                    objectClassIds.put(Target.class, objectClassId);
                    break;
                case "TelecommunicationFacility":
                    objectClassIds.put(TelecommunicationFacility.class, objectClassId);
                    break;
                case "TelecommunicationsCable":
                    objectClassIds.put(TelecommunicationsCable.class, objectClassId);
                    break;
                case "ThermalPipe":
                    objectClassIds.put(ThermalPipe.class, objectClassId);
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
                case "TranslationJP":
                    objectClassIds.put(TranslationJP.class, objectClassId);
                    break;
                case "TreatmentFacility":
                    objectClassIds.put(TreatmentFacility.class, objectClassId);
                    break;
                case "TreePlantingDistrict":
                    objectClassIds.put(TreePlantingDistrict.class, objectClassId);
                    break;
                case "Trip":
                    objectClassIds.put(Trip.class, objectClassId);
                    break;
                case "UnclassifiedBlankArea":
                    objectClassIds.put(UnclassifiedBlankArea.class, objectClassId);
                    break;
                case "UnclassifiedUseDistrict":
                    objectClassIds.put(UnclassifiedUseDistrict.class, objectClassId);
                    break;
                case "UndergroundBuilding":
                    objectClassIds.put(UndergroundBuilding.class, objectClassId);
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
                case "UrbanFacilityStatus":
                    objectClassIds.put(UrbanFacilityStatus.class, objectClassId);
                    break;
                case "UrbanFacilityStipulatedByCabinetOrder":
                    objectClassIds.put(UrbanFacilityStipulatedByCabinetOrder.class, objectClassId);
                    break;
                case "UrbanFunctionAttractionArea":
                    objectClassIds.put(UrbanFunctionAttractionArea.class, objectClassId);
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
                case "WallSurface":
                    objectClassIds.put(WallSurface.class, objectClassId);
                    break;
                case "WaterPipe":
                    objectClassIds.put(WaterPipe.class, objectClassId);
                    break;
                case "Waterway":
                    if (type.getSchema().matchesNamespaceURI(UrbanObjectModule.v3_2.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.uro.Waterway.class, objectClassId);
                    else if (type.getSchema().matchesNamespaceURI(UrbanFunctionModule.v3_2.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.urf.Waterway.class, objectClassId);
                    break;
                case "WeatherObservation":
                    objectClassIds.put(WeatherObservation.class, objectClassId);
                    break;
                case "WindProtectionFacility":
                    objectClassIds.put(WindProtectionFacility.class, objectClassId);
                    break;
                case "ZonalDisasterPreventionFacility":
                    objectClassIds.put(ZonalDisasterPreventionFacility.class, objectClassId);
                    break;
                case "Zone":
                    objectClassIds.put(Zone.class, objectClassId);
                    break;
                case "ZoneForPreservationOfHistoricalLandscape":
                    objectClassIds.put(ZoneForPreservationOfHistoricalLandscape.class, objectClassId);
                    break;
            }
        }

        for (ComplexType type : schemaMapping.getComplexTypes()) {
            int typeClassId = type.getObjectClassId();

            switch (type.getPath()) {
                case "DataQualityAttribute":
                    typeClassIds.put(DataQualityAttribute.class, typeClassId);
                    break;
                case "PublicSurveyDataQualityAttribute":
                    typeClassIds.put(PublicSurveyDataQualityAttribute.class, typeClassId);
                    break;
                case "DisasterRiskAttribute":
                    typeClassIds.put(DisasterRiskAttribute.class, typeClassId);
                    break;
                case "FloodingRiskAttribute":
                    typeClassIds.put(FloodingRiskAttribute.class, typeClassId);
                    break;
                case "HighTideRiskAttribute":
                    typeClassIds.put(HighTideRiskAttribute.class, typeClassId);
                    break;
                case "InlandFloodingRiskAttribute":
                    typeClassIds.put(InlandFloodingRiskAttribute.class, typeClassId);
                    break;
                case "ReservoirFloodingRiskAttribute":
                    typeClassIds.put(ReservoirFloodingRiskAttribute.class, typeClassId);
                    break;
                case "RiverFloodingRiskAttribute":
                    typeClassIds.put(RiverFloodingRiskAttribute.class, typeClassId);
                    break;
                case "TsunamiRiskAttribute":
                    typeClassIds.put(TsunamiRiskAttribute.class, typeClassId);
                    break;
                case "LandSlideRiskAttribute":
                    typeClassIds.put(LandSlideRiskAttribute.class, typeClassId);
                    break;
                case "DmAttribute":
                    typeClassIds.put(DmAttribute.class, typeClassId);
                    break;
                case "DmAnnotation":
                    typeClassIds.put(DmAnnotation.class, typeClassId);
                    break;
                case "DmElement":
                    typeClassIds.put(DmElement.class, typeClassId);
                    break;
                case "DmGeometricAttribute":
                    typeClassIds.put(DmGeometricAttribute.class, typeClassId);
                    break;
                case "FacilityAttribute":
                    typeClassIds.put(FacilityAttribute.class, typeClassId);
                    break;
                case "FishingPortAttribute":
                    typeClassIds.put(FishingPortAttribute.class, typeClassId);
                    break;
                case "FishingPortCapacity":
                    typeClassIds.put(FishingPortCapacity.class, typeClassId);
                    break;
                case "FishingPortFacility":
                    typeClassIds.put(FishingPortFacility.class, typeClassId);
                    break;
                case "MaintenanceHistoryAttribute":
                    typeClassIds.put(MaintenanceHistoryAttribute.class, typeClassId);
                    break;
                case "ParkFacilityLongevityPlanAttribute":
                    typeClassIds.put(ParkFacilityLongevityPlanAttribute.class, typeClassId);
                    break;
                case "NumberOfFacilities":
                    typeClassIds.put(NumberOfFacilities.class, typeClassId);
                    break;
                case "RepairsBeforeParkHealthAssessment":
                    typeClassIds.put(RepairsBeforeParkHealthAssessment.class, typeClassId);
                    break;
                case "ParkHealthAssessment":
                    typeClassIds.put(ParkHealthAssessment.class, typeClassId);
                    break;
                case "LongevityMeasures":
                    typeClassIds.put(LongevityMeasures.class, typeClassId);
                    break;
                case "CountermeasuresCost":
                    typeClassIds.put(CountermeasuresCost.class, typeClassId);
                    break;
                case "PortAttribute":
                    typeClassIds.put(PortAttribute.class, typeClassId);
                    break;
                case "CargoHandlingFacility":
                    typeClassIds.put(CargoHandlingFacility.class, typeClassId);
                    break;
                case "CyberportMarinaAndPBS":
                    typeClassIds.put(CyberportMarinaAndPBS.class, typeClassId);
                    break;
                case "HarborFacility":
                    typeClassIds.put(HarborFacility.class, typeClassId);
                    break;
                case "MooringFacility":
                    typeClassIds.put(MooringFacility.class, typeClassId);
                    break;
                case "NavigationAssistanceFacility":
                    typeClassIds.put(NavigationAssistanceFacility.class, typeClassId);
                    break;
                case "PortEnvironmentalImprovementFacility":
                    typeClassIds.put(PortEnvironmentalImprovementFacility.class, typeClassId);
                    break;
                case "PortManagementFacility":
                    typeClassIds.put(PortManagementFacility.class, typeClassId);
                    break;
                case "PortPassengerFacility":
                    typeClassIds.put(PortPassengerFacility.class, typeClassId);
                    break;
                case "PortPollutionControlFacility":
                    typeClassIds.put(PortPollutionControlFacility.class, typeClassId);
                    break;
                case "PortProtectiveFacility":
                    typeClassIds.put(PortProtectiveFacility.class, typeClassId);
                    break;
                case "PortStorageFacility":
                    typeClassIds.put(PortStorageFacility.class, typeClassId);
                    break;
                case "PortTransportationFacility":
                    typeClassIds.put(PortTransportationFacility.class, typeClassId);
                    break;
                case "PortWasteTreatmentFacility":
                    typeClassIds.put(PortWasteTreatmentFacility.class, typeClassId);
                    break;
                case "PortWelfareFacility":
                    typeClassIds.put(PortWelfareFacility.class, typeClassId);
                    break;
                case "ShipServiceFacility":
                    typeClassIds.put(ShipServiceFacility.class, typeClassId);
                    break;
                case "FacilityIdAttribute":
                    typeClassIds.put(FacilityIdAttribute.class, typeClassId);
                    break;
                case "RiverFacilityIdAttribute":
                    typeClassIds.put(RiverFacilityIdAttribute.class, typeClassId);
                    break;
                case "FacilityTypeAttribute":
                    typeClassIds.put(FacilityTypeAttribute.class, typeClassId);
                    break;
                case "KeyValuePairAttribute":
                    typeClassIds.put(KeyValuePairAttribute.class, typeClassId);
                    break;
                case "RealEstateIDAttribute":
                    typeClassIds.put(RealEstateIDAttribute.class, typeClassId);
                    break;
                case "BuildingAttribute":
                    typeClassIds.put(BuildingAttribute.class, typeClassId);
                    break;
                case "BuildingUsecaseAttribute":
                    typeClassIds.put(BuildingUsecaseAttribute.class, typeClassId);
                    break;
                case "ConstructionBaseAttribute":
                    typeClassIds.put(ConstructionBaseAttribute.class, typeClassId);
                    break;
                case "BridgeFunctionalAttribute":
                    typeClassIds.put(BridgeFunctionalAttribute.class, typeClassId);
                    break;
                case "ConstructionRiskAssessmentAttribute":
                    typeClassIds.put(ConstructionRiskAssessmentAttribute.class, typeClassId);
                    break;
                case "BridgeStructureAttribute":
                    typeClassIds.put(BridgeStructureAttribute.class, typeClassId);
                    break;
                case "BuildingDetailAttribute":
                    typeClassIds.put(BuildingDetailAttribute.class, typeClassId);
                    break;
                case "BuildingIDAttribute":
                    typeClassIds.put(BuildingIDAttribute.class, typeClassId);
                    break;
                case "CityFurnitureAttribute":
                    typeClassIds.put(CityFurnitureAttribute.class, typeClassId);
                    break;
                case "CityFurnitureDetailAttribute":
                    typeClassIds.put(CityFurnitureDetailAttribute.class, typeClassId);
                    break;
                case "IfcAttribute":
                    typeClassIds.put(IfcAttribute.class, typeClassId);
                    break;
                case "IfcClassificationReference":
                    typeClassIds.put(IfcClassificationReference.class, typeClassId);
                    break;
                case "IfcClassification":
                    typeClassIds.put(IfcClassification.class, typeClassId);
                    break;
                case "IfcCoordinateReferenceSystem":
                    typeClassIds.put(IfcCoordinateReferenceSystem.class, typeClassId);
                    break;
                case "IfcProjectedCRS":
                    typeClassIds.put(IfcProjectedCRS.class, typeClassId);
                    break;
                case "IfcMapConversion":
                    typeClassIds.put(IfcMapConversion.class, typeClassId);
                    break;
                case "IfcCoordinateReferenceSystemSelect":
                    typeClassIds.put(IfcCoordinateReferenceSystemSelect.class, typeClassId);
                    break;
                case "IfcGeometricRepresentationContext":
                    typeClassIds.put(IfcGeometricRepresentationContext.class, typeClassId);
                    break;
                case "IfcAxis2Placement3D":
                    typeClassIds.put(IfcAxis2Placement3D.class, typeClassId);
                    break;
                case "IfcPsetBuildingCommon":
                    typeClassIds.put(IfcPsetBuildingCommon.class, typeClassId);
                    break;
                case "IfcPsetDoorCommon":
                    typeClassIds.put(IfcPsetDoorCommon.class, typeClassId);
                    break;
                case "IfcPsetOpeningElementCommon":
                    typeClassIds.put(IfcPsetOpeningElementCommon.class, typeClassId);
                    break;
                case "IfcPsetSiteCommon":
                    typeClassIds.put(IfcPsetSiteCommon.class, typeClassId);
                    break;
                case "IfcPsetSpaceCommon":
                    typeClassIds.put(IfcPsetSpaceCommon.class, typeClassId);
                    break;
                case "IfcPsetWindowCommon":
                    typeClassIds.put(IfcPsetWindowCommon.class, typeClassId);
                    break;
                case "IfcRoot":
                    typeClassIds.put(IfcRoot.class, typeClassId);
                    break;
                case "IfcObjectDefinition":
                    typeClassIds.put(IfcObjectDefinition.class, typeClassId);
                    break;
                case "IfcObject":
                    typeClassIds.put(IfcObject.class, typeClassId);
                    break;
                case "IfcGroup":
                    typeClassIds.put(IfcGroup.class, typeClassId);
                    break;
                case "IfcZone":
                    typeClassIds.put(IfcZone.class, typeClassId);
                    break;
                case "IfcProduct":
                    typeClassIds.put(IfcProduct.class, typeClassId);
                    break;
                case "IfcElement":
                    typeClassIds.put(IfcElement.class, typeClassId);
                    break;
                case "IfcBuildingElement":
                    typeClassIds.put(IfcBuildingElement.class, typeClassId);
                    break;
                case "IfcCurtainWall":
                    typeClassIds.put(IfcCurtainWall.class, typeClassId);
                    break;
                case "IfcDoor":
                    typeClassIds.put(IfcDoor.class, typeClassId);
                    break;
                case "IfcRoof":
                    typeClassIds.put(IfcRoof.class, typeClassId);
                    break;
                case "IfcWall":
                    typeClassIds.put(IfcWall.class, typeClassId);
                    break;
                case "IfcWallStandardCase":
                    typeClassIds.put(IfcWallStandardCase.class, typeClassId);
                    break;
                case "IfcWindow":
                    typeClassIds.put(IfcWindow.class, typeClassId);
                    break;
                case "IfcFurnishingElement":
                    typeClassIds.put(IfcFurnishingElement.class, typeClassId);
                    break;
                case "IfcOpeningElement":
                    typeClassIds.put(IfcOpeningElement.class, typeClassId);
                    break;
                case "IfcSpatialStructureElement":
                    typeClassIds.put(IfcSpatialStructureElement.class, typeClassId);
                    break;
                case "IfcBuilding":
                    typeClassIds.put(IfcBuilding.class, typeClassId);
                    break;
                case "IfcBuildingStorey":
                    typeClassIds.put(IfcBuildingStorey.class, typeClassId);
                    break;
                case "IfcSite":
                    typeClassIds.put(IfcSite.class, typeClassId);
                    break;
                case "IfcSpace":
                    typeClassIds.put(IfcSpace.class, typeClassId);
                    break;
                case "IfcProject":
                    typeClassIds.put(IfcProject.class, typeClassId);
                    break;
                case "IfcUnit":
                    typeClassIds.put(IfcUnit.class, typeClassId);
                    break;
                case "IfcSpaceBaseQuantity":
                    typeClassIds.put(IfcSpaceBaseQuantity.class, typeClassId);
                    break;
                case "IndoorAttribute":
                    typeClassIds.put(IndoorAttribute.class, typeClassId);
                    break;
                case "IndoorFacilityAttribute":
                    typeClassIds.put(IndoorFacilityAttribute.class, typeClassId);
                    break;
                case "IndoorFurnishingAttribute":
                    typeClassIds.put(IndoorFurnishingAttribute.class, typeClassId);
                    break;
                case "IndoorPublicTagAttribute":
                    typeClassIds.put(IndoorPublicTagAttribute.class, typeClassId);
                    break;
                case "IndoorSpaceAttribute":
                    typeClassIds.put(IndoorSpaceAttribute.class, typeClassId);
                    break;
                case "IndoorStoreyAttribute":
                    typeClassIds.put(IndoorStoreyAttribute.class, typeClassId);
                    break;
                case "IndoorTactileTileAttribute":
                    typeClassIds.put(IndoorTactileTileAttribute.class, typeClassId);
                    break;
                case "IndoorUserDefinedAttribute":
                    typeClassIds.put(IndoorUserDefinedAttribute.class, typeClassId);
                    break;
                case "UserDefinedValue":
                    typeClassIds.put(UserDefinedValue.class, typeClassId);
                    break;
                case "IndoorZoneAttribute":
                    typeClassIds.put(IndoorZoneAttribute.class, typeClassId);
                    break;
                case "LandUseAttribute":
                    typeClassIds.put(LandUseAttribute.class, typeClassId);
                    break;
                case "LandUseDetailAttribute":
                    typeClassIds.put(LandUseDetailAttribute.class, typeClassId);
                    break;
                case "LargeCustomerFacilityAttribute":
                    typeClassIds.put(LargeCustomerFacilityAttribute.class, typeClassId);
                    break;
                case "RailwayRouteAttribute":
                    typeClassIds.put(RailwayRouteAttribute.class, typeClassId);
                    break;
                case "RailwayTrackAttribute":
                    typeClassIds.put(RailwayTrackAttribute.class, typeClassId);
                    break;
                case "ControlPoint":
                    typeClassIds.put(ControlPoint.class, typeClassId);
                    break;
                case "ControlPointType":
                    typeClassIds.put(ControlPointParameter.class, typeClassId);
                    break;
                case "CircularCurveType":
                    typeClassIds.put(CircularCurveType.class, typeClassId);
                    break;
                case "TransitionCurveType":
                    typeClassIds.put(TransitionCurveType.class, typeClassId);
                    break;
                case "SlopeType":
                    typeClassIds.put(SlopeType.class, typeClassId);
                    break;
                case "VerticalCurveType":
                    typeClassIds.put(VerticalCurveType.class, typeClassId);
                    break;
                case "RoadType":
                    typeClassIds.put(RoadType.class, typeClassId);
                    break;
                case "RoadAttribute":
                    typeClassIds.put(RoadAttribute.class, typeClassId);
                    break;
                case "RoadStructureAttribute":
                    typeClassIds.put(RoadStructureAttribute.class, typeClassId);
                    break;
                case "SquareUrbanPlanAttribute":
                    typeClassIds.put(SquareUrbanPlanAttribute.class, typeClassId);
                    break;
                case "StationSquareAttribute":
                    typeClassIds.put(StationSquareAttribute.class, typeClassId);
                    break;
                case "TerminalAttribute":
                    typeClassIds.put(TerminalAttribute.class, typeClassId);
                    break;
                case "TrackAttribute":
                    typeClassIds.put(TrackAttribute.class, typeClassId);
                    break;
                case "TrafficAreaAttribute":
                    typeClassIds.put(TrafficAreaAttribute.class, typeClassId);
                    break;
                case "TrafficAreaStructureAttribute":
                    typeClassIds.put(TrafficAreaStructureAttribute.class, typeClassId);
                    break;
                case "TrafficVolumeAttribute":
                    typeClassIds.put(TrafficVolumeAttribute.class, typeClassId);
                    break;
                case "TrafficObjectUsecaseAttribute":
                    typeClassIds.put(TrafficObjectUsecaseAttribute.class, typeClassId);
                    break;
                case "TunnelFunctionalAttribute":
                    typeClassIds.put(TunnelFunctionalAttribute.class, typeClassId);
                    break;
                case "TunnelStructureAttribute":
                    typeClassIds.put(TunnelStructureAttribute.class, typeClassId);
                    break;
                case "WaterBodyDetailAttribute":
                    typeClassIds.put(WaterBodyDetailAttribute.class, typeClassId);
                    break;
                case "PublicTransitDataType":
                    typeClassIds.put(PublicTransitDataType.class, typeClassId);
                    break;
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
                case "ConstructionEvent":
                    typeClassIds.put(ConstructionEvent.class, typeClassId);
                    break;
                case "Elevation":
                    typeClassIds.put(Elevation.class, typeClassId);
                    break;
                case "Height":
                    typeClassIds.put(Height.class, typeClassId);
                    break;
                case "Occupancy":
                    typeClassIds.put(Occupancy.class, typeClassId);
                    break;
                case "ConstructionStructureAttribute":
                    typeClassIds.put(ConstructionStructureAttribute.class, typeClassId);
                    break;
                case "DamAttribute":
                    typeClassIds.put(DamAttribute.class, typeClassId);
                    break;
                case "EmbankmentAttribute":
                    typeClassIds.put(EmbankmentAttribute.class, typeClassId);
                    break;
                case "OffsetDepth":
                    typeClassIds.put(OffsetDepth.class, typeClassId);
                    break;
                case "ThematicShape":
                    typeClassIds.put(ThematicShape.class, typeClassId);
                    break;
                case "LengthAttribute":
                    typeClassIds.put(LengthAttribute.class, typeClassId);
                    break;
                case "PointCloudSpecAttribute":
                    typeClassIds.put(PointCloudSpecAttribute.class, typeClassId);
                    break;
                case "WaterwayDetailAttribute":
                    typeClassIds.put(WaterwayDetailAttribute.class, typeClassId);
                    break;
                case "Boundary":
                    typeClassIds.put(Boundary.class, typeClassId);
                    break;
                case "UrbanParkAttribute":
                    typeClassIds.put(UrbanParkAttribute.class, typeClassId);
                    break;
                case "BreakdownOfNominalArea":
                    typeClassIds.put(BreakdownOfNominalArea.class, typeClassId);
                    break;
                case "PopulationType":
                    typeClassIds.put(PopulationType.class, typeClassId);
                    break;
                case "PopulationByAgeAndSexType":
                    typeClassIds.put(PopulationByAgeAndSexType.class, typeClassId);
                    break;
                case "DaytimePopulationType":
                    typeClassIds.put(DaytimePopulationType.class, typeClassId);
                    break;
                case "EconomicActivityType":
                    typeClassIds.put(EconomicActivityType.class, typeClassId);
                    break;
                case "EconomicActivitySumType":
                    typeClassIds.put(EconomicActivitySumType.class, typeClassId);
                    break;
                case "ResidentialDevelopmentType":
                    typeClassIds.put(ResidentialDevelopmentType.class, typeClassId);
                    break;
                case "UrbanDevelopmentProjectType":
                    typeClassIds.put(UrbanDevelopmentProjectType.class, typeClassId);
                    break;
                case "AgriculturalDevelopmentProjectType":
                    typeClassIds.put(AgriculturalDevelopmentProjectType.class, typeClassId);
                    break;
                case "ValueByCodes":
                    typeClassIds.put(ValueByCodes.class, typeClassId);
                    break;
                case "FarmlandConversionType":
                    typeClassIds.put(FarmlandConversionType.class, typeClassId);
                    break;
                case "ForestConversionType":
                    typeClassIds.put(ForestConversionType.class, typeClassId);
                    break;
                case "ParkAttribute":
                    typeClassIds.put(ParkAttribute.class, typeClassId);
                    break;
                case "ObservationPoint":
                    typeClassIds.put(ObservationPoint.class, typeClassId);
                    break;
                case "WaterWorksAttribute":
                    typeClassIds.put(WaterWorksAttribute.class, typeClassId);
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
                case "SewerSystemAttribute":
                    typeClassIds.put(SewerSystemAttribute.class, typeClassId);
                    break;
                case "WeatherObservationType":
                    typeClassIds.put(WeatherObservationType.class, typeClassId);
                    break;
                case "PopulationByAgeAndSex":
                    typeClassIds.put(PopulationByAgeAndSex.class, typeClassId);
                    break;
                case "LandPricePerLandUse":
                    typeClassIds.put(LandPricePerLandUse.class, typeClassId);
                    break;
                case "NumberOfAnnualDiversions":
                    typeClassIds.put(NumberOfAnnualDiversions.class, typeClassId);
                    break;
                case "AreaOfAnnualDiversions":
                    typeClassIds.put(AreaOfAnnualDiversions.class, typeClassId);
                    break;
                case "NumberOfHouseholds":
                    typeClassIds.put(NumberOfHouseholds.class, typeClassId);
                    break;
                case "KeyValuePair":
                    typeClassIds.put(KeyValuePair.class, typeClassId);
                    break;
                case "Description":
                    typeClassIds.put(Description.class, typeClassId);
                    break;
                case "Point":
                    typeClassIds.put(Point.class, typeClassId);
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
