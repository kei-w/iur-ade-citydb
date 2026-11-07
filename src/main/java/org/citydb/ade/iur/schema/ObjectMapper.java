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
                case "LandUseDiversion":
                    if (type.getSchema().matchesNamespaceURI(StatisticalGridModule.v1_4.getNamespaceURI()))
                        objectClassIds.put(LandUseDiversion.class, objectClassId);
                    else if (type.getSchema().matchesNamespaceURI(UrbanFunctionModule.v1_4.getNamespaceURI()))
                        objectClassIds.put(org.citygml4j.ade.iur.model.urf.LandUseDiversion.class, objectClassId);
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
                case "Administration":
                    objectClassIds.put(Administration.class, objectClassId);
                    break;
                case "LandUsePlan":
                    objectClassIds.put(LandUsePlan.class, objectClassId);
                    break;
                case "UrbanPlan":
                    objectClassIds.put(UrbanPlan.class, objectClassId);
                    break;
                case "Agreement":
                    objectClassIds.put(Agreement.class, objectClassId);
                    break;
                case "Regulation":
                    objectClassIds.put(Regulation.class, objectClassId);
                    break;
                case "AreaClassification":
                    objectClassIds.put(AreaClassification.class, objectClassId);
                    break;
                case "DistrictsAndZones":
                    objectClassIds.put(DistrictsAndZones.class, objectClassId);
                    break;
                case "DevelopmentProject":
                    objectClassIds.put(DevelopmentProject.class, objectClassId);
                    break;
                case "CensusBlock":
                    objectClassIds.put(CensusBlock.class, objectClassId);
                    break;
                case "DisasterDamage":
                    objectClassIds.put(DisasterDamage.class, objectClassId);
                    break;
                case "Pollution":
                    objectClassIds.put(Pollution.class, objectClassId);
                    break;
                case "DisasterPreventionBase":
                    objectClassIds.put(DisasterPreventionBase.class, objectClassId);
                    break;
                case "Recreations":
                    objectClassIds.put(Recreations.class, objectClassId);
                    break;
                case "HubCity":
                    objectClassIds.put(HubCity.class, objectClassId);
                    break;
                case "Urbanization":
                    objectClassIds.put(Urbanization.class, objectClassId);
                    break;
                case "PublicTransportationFacility":
                    objectClassIds.put(PublicTransportationFacility.class, objectClassId);
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
                case "Waterway":
                    objectClassIds.put(Waterway.class, objectClassId);
                    break;
                case "AbstractConstruction":
                    objectClassIds.put(AbstractConstruction.class, objectClassId);
                    break;
                case "OtherConstruction":
                    objectClassIds.put(OtherConstruction.class, objectClassId);
                    break;
                case "RoofSurface":
                    objectClassIds.put(RoofSurface.class, objectClassId);
                    break;
                case "WallSurface":
                    objectClassIds.put(WallSurface.class, objectClassId);
                    break;
                case "GroundSurface":
                    objectClassIds.put(GroundSurface.class, objectClassId);
                    break;
                case "OuterCeilingSurface":
                    objectClassIds.put(OuterCeilingSurface.class, objectClassId);
                    break;
                case "OuterFloorSurface":
                    objectClassIds.put(OuterFloorSurface.class, objectClassId);
                    break;
                case "ClosureSurface":
                    objectClassIds.put(ClosureSurface.class, objectClassId);
                    break;
                case "ConstructionInstallation":
                    objectClassIds.put(ConstructionInstallation.class, objectClassId);
                    break;
                case "UndergroundBuilding":
                    objectClassIds.put(UndergroundBuilding.class, objectClassId);
                    break;
                case "Appurtenance":
                    objectClassIds.put(Appurtenance.class, objectClassId);
                    break;
                case "Manhole":
                    objectClassIds.put(Manhole.class, objectClassId);
                    break;
                case "Handhole":
                    objectClassIds.put(Handhole.class, objectClassId);
                    break;
                case "Pipe":
                    objectClassIds.put(Pipe.class, objectClassId);
                    break;
                case "SewerPipe":
                    objectClassIds.put(SewerPipe.class, objectClassId);
                    break;
                case "ThermalPipe":
                    objectClassIds.put(ThermalPipe.class, objectClassId);
                    break;
                case "WaterPipe":
                    objectClassIds.put(WaterPipe.class, objectClassId);
                    break;
                case "OilGasChemicalsPipe":
                    objectClassIds.put(OilGasChemicalsPipe.class, objectClassId);
                    break;
                case "Duct":
                    objectClassIds.put(Duct.class, objectClassId);
                    break;
                case "Cable":
                    objectClassIds.put(Cable.class, objectClassId);
                    break;
                case "ElectricityCable":
                    objectClassIds.put(ElectricityCable.class, objectClassId);
                    break;
                case "TelecommunicationsCable":
                    objectClassIds.put(TelecommunicationsCable.class, objectClassId);
                    break;
                case "PublicLand":
                    objectClassIds.put(PublicLand.class, objectClassId);
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
                case "ConstructionBaseAttribute":
                    typeClassIds.put(ConstructionBaseAttribute.class, typeClassId);
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
                case "FacilityAttribute":
                    typeClassIds.put(FacilityAttribute.class, typeClassId);
                    break;
                case "PortAttribute":
                    typeClassIds.put(PortAttribute.class, typeClassId);
                    break;
                case "HarborFacility":
                    typeClassIds.put(HarborFacility.class, typeClassId);
                    break;
                case "PortProtectiveFacility":
                    typeClassIds.put(PortProtectiveFacility.class, typeClassId);
                    break;
                case "MooringFacility":
                    typeClassIds.put(MooringFacility.class, typeClassId);
                    break;
                case "PortTransportationFacility":
                    typeClassIds.put(PortTransportationFacility.class, typeClassId);
                    break;
                case "NavigationAssistanceFacility":
                    typeClassIds.put(NavigationAssistanceFacility.class, typeClassId);
                    break;
                case "CargoHandlingFacility":
                    typeClassIds.put(CargoHandlingFacility.class, typeClassId);
                    break;
                case "PortPassengerFacility":
                    typeClassIds.put(PortPassengerFacility.class, typeClassId);
                    break;
                case "PortStorageFacility":
                    typeClassIds.put(PortStorageFacility.class, typeClassId);
                    break;
                case "ShipServiceFacility":
                    typeClassIds.put(ShipServiceFacility.class, typeClassId);
                    break;
                case "PortWasteTreatmentFacility":
                    typeClassIds.put(PortWasteTreatmentFacility.class, typeClassId);
                    break;
                case "PortEnvironmentalImprovementFacility":
                    typeClassIds.put(PortEnvironmentalImprovementFacility.class, typeClassId);
                    break;
                case "PortPollutionControlFacility":
                    typeClassIds.put(PortPollutionControlFacility.class, typeClassId);
                    break;
                case "PortWelfareFacility":
                    typeClassIds.put(PortWelfareFacility.class, typeClassId);
                    break;
                case "PortManagementFacility":
                    typeClassIds.put(PortManagementFacility.class, typeClassId);
                    break;
                case "CyberportMarinaAndPBS":
                    typeClassIds.put(CyberportMarinaAndPBS.class, typeClassId);
                    break;
                case "FishingPortAttribute":
                    typeClassIds.put(FishingPortAttribute.class, typeClassId);
                    break;
                case "FishingPortFacility":
                    typeClassIds.put(FishingPortFacility.class, typeClassId);
                    break;
                case "FishingPortCapacity":
                    typeClassIds.put(FishingPortCapacity.class, typeClassId);
                    break;
                case "MaintenanceHistoryAttribute":
                    typeClassIds.put(MaintenanceHistoryAttribute.class, typeClassId);
                    break;
                case "BridgeStructureAttribute":
                    typeClassIds.put(BridgeStructureAttribute.class, typeClassId);
                    break;
                case "BridgeFunctionalAttribute":
                    typeClassIds.put(BridgeFunctionalAttribute.class, typeClassId);
                    break;
                case "ConstructionRiskAssessmentAttribute":
                    typeClassIds.put(ConstructionRiskAssessmentAttribute.class, typeClassId);
                    break;
                case "DisasterRiskAttribute":
                    typeClassIds.put(DisasterRiskAttribute.class, typeClassId);
                    break;
                case "FloodingRiskAttribute":
                    typeClassIds.put(FloodingRiskAttribute.class, typeClassId);
                    break;
                case "RiverFloodingRiskAttribute":
                    typeClassIds.put(RiverFloodingRiskAttribute.class, typeClassId);
                    break;
                case "TsunamiRiskAttribute":
                    typeClassIds.put(TsunamiRiskAttribute.class, typeClassId);
                    break;
                case "InlandFloodingRiskAttribute":
                    typeClassIds.put(InlandFloodingRiskAttribute.class, typeClassId);
                    break;
                case "HighTideRiskAttribute":
                    typeClassIds.put(HighTideRiskAttribute.class, typeClassId);
                    break;
                case "LandSlideRiskAttribute":
                    typeClassIds.put(LandSlideRiskAttribute.class, typeClassId);
                    break;
                case "DmAttribute":
                    typeClassIds.put(DmAttribute.class, typeClassId);
                    break;
                case "DmGeometricAttribute":
                    typeClassIds.put(DmGeometricAttribute.class, typeClassId);
                    break;
                case "DmElement":
                    typeClassIds.put(DmElement.class, typeClassId);
                    break;
                case "DmAnnotation":
                    typeClassIds.put(DmAnnotation.class, typeClassId);
                    break;
                case "ConstructionDataQualityAttribute":
                    typeClassIds.put(ConstructionDataQualityAttribute.class, typeClassId);
                    break;
                case "TransportationDataQualityAttribute":
                    typeClassIds.put(TransportationDataQualityAttribute.class, typeClassId);
                    break;
                case "TunnelStructureAttribute":
                    typeClassIds.put(TunnelStructureAttribute.class, typeClassId);
                    break;
                case "TunnelFunctionalAttribute":
                    typeClassIds.put(TunnelFunctionalAttribute.class, typeClassId);
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
                case "TrackAttribute":
                    typeClassIds.put(TrackAttribute.class, typeClassId);
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
                case "IfcAttribute":
                    typeClassIds.put(IfcAttribute.class, typeClassId);
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
                case "IfcProduct":
                    typeClassIds.put(IfcProduct.class, typeClassId);
                    break;
                case "IfcSpatialStructureElement":
                    typeClassIds.put(IfcSpatialStructureElement.class, typeClassId);
                    break;
                case "IfcBuilding":
                    typeClassIds.put(IfcBuilding.class, typeClassId);
                    break;
                case "IfcSite":
                    typeClassIds.put(IfcSite.class, typeClassId);
                    break;
                case "IfcSpace":
                    typeClassIds.put(IfcSpace.class, typeClassId);
                    break;
                case "IfcBuildingStorey":
                    typeClassIds.put(IfcBuildingStorey.class, typeClassId);
                    break;
                case "IfcElement":
                    typeClassIds.put(IfcElement.class, typeClassId);
                    break;
                case "IfcBuildingElement":
                    typeClassIds.put(IfcBuildingElement.class, typeClassId);
                    break;
                case "IfcWall":
                    typeClassIds.put(IfcWall.class, typeClassId);
                    break;
                case "IfcWallStandardCase":
                    typeClassIds.put(IfcWallStandardCase.class, typeClassId);
                    break;
                case "IfcCurtainWall":
                    typeClassIds.put(IfcCurtainWall.class, typeClassId);
                    break;
                case "IfcRoof":
                    typeClassIds.put(IfcRoof.class, typeClassId);
                    break;
                case "IfcWindow":
                    typeClassIds.put(IfcWindow.class, typeClassId);
                    break;
                case "IfcDoor":
                    typeClassIds.put(IfcDoor.class, typeClassId);
                    break;
                case "IfcOpeningElement":
                    typeClassIds.put(IfcOpeningElement.class, typeClassId);
                    break;
                case "IfcFurnishingElement":
                    typeClassIds.put(IfcFurnishingElement.class, typeClassId);
                    break;
                case "IfcProject":
                    typeClassIds.put(IfcProject.class, typeClassId);
                    break;
                case "IfcGeometricRepresentationContext":
                    typeClassIds.put(IfcGeometricRepresentationContext.class, typeClassId);
                    break;
                case "IfcAxis2Placement3D":
                    typeClassIds.put(IfcAxis2Placement3D.class, typeClassId);
                    break;
                case "IfcUnit":
                    typeClassIds.put(IfcUnit.class, typeClassId);
                    break;
                case "IfcGroup":
                    typeClassIds.put(IfcGroup.class, typeClassId);
                    break;
                case "IfcZone":
                    typeClassIds.put(IfcZone.class, typeClassId);
                    break;
                case "IfcPsetBuildingCommon":
                    typeClassIds.put(IfcPsetBuildingCommon.class, typeClassId);
                    break;
                case "IfcPsetSiteCommon":
                    typeClassIds.put(IfcPsetSiteCommon.class, typeClassId);
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
                case "IfcClassificationReference":
                    typeClassIds.put(IfcClassificationReference.class, typeClassId);
                    break;
                case "IfcClassification":
                    typeClassIds.put(IfcClassification.class, typeClassId);
                    break;
                case "IfcPsetSpaceCommon":
                    typeClassIds.put(IfcPsetSpaceCommon.class, typeClassId);
                    break;
                case "IfcSpaceBaseQuantity":
                    typeClassIds.put(IfcSpaceBaseQuantity.class, typeClassId);
                    break;
                case "IfcPsetWindowCommon":
                    typeClassIds.put(IfcPsetWindowCommon.class, typeClassId);
                    break;
                case "IfcPsetDoorCommon":
                    typeClassIds.put(IfcPsetDoorCommon.class, typeClassId);
                    break;
                case "IfcPsetOpeningElementCommon":
                    typeClassIds.put(IfcPsetOpeningElementCommon.class, typeClassId);
                    break;
                case "RoomAttribute":
                    typeClassIds.put(RoomAttribute.class, typeClassId);
                    break;
                case "RoomDataQualityAttribute":
                    typeClassIds.put(RoomDataQualityAttribute.class, typeClassId);
                    break;
                case "IndoorAttribute":
                    typeClassIds.put(IndoorAttribute.class, typeClassId);
                    break;
                case "IndoorFurnishingAttribute":
                    typeClassIds.put(IndoorFurnishingAttribute.class, typeClassId);
                    break;
                case "IndoorZoneAttribute":
                    typeClassIds.put(IndoorZoneAttribute.class, typeClassId);
                    break;
                case "IndoorPublicTagAttribute":
                    typeClassIds.put(IndoorPublicTagAttribute.class, typeClassId);
                    break;
                case "IndoorSpaceAttribute":
                    typeClassIds.put(IndoorSpaceAttribute.class, typeClassId);
                    break;
                case "IndoorFacilityAttribute":
                    typeClassIds.put(IndoorFacilityAttribute.class, typeClassId);
                    break;
                case "IndoorStoreyAttribute":
                    typeClassIds.put(IndoorStoreyAttribute.class, typeClassId);
                    break;
                case "IndoorTacatileTileAttribute":
                    typeClassIds.put(IndoorTacatileTileAttribute.class, typeClassId);
                    break;
                case "IndoorUserDefinedAttribute":
                    typeClassIds.put(IndoorUserDefinedAttribute.class, typeClassId);
                    break;
                case "UserDefinedValue":
                    typeClassIds.put(UserDefinedValue.class, typeClassId);
                    break;
                case "LandUseDataQualityAttribute":
                    typeClassIds.put(LandUseDataQualityAttribute.class, typeClassId);
                    break;
                case "RoadType":
                    typeClassIds.put(RoadType.class, typeClassId);
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
                case "WaterBodyDetailAttribute":
                    typeClassIds.put(WaterBodyDetailAttribute.class, typeClassId);
                    break;
                case "WaterwayDetailAttribute":
                    typeClassIds.put(WaterwayDetailAttribute.class, typeClassId);
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
                case "EmbankmentAttribute":
                    typeClassIds.put(EmbankmentAttribute.class, typeClassId);
                    break;
                case "DamAttribute":
                    typeClassIds.put(DamAttribute.class, typeClassId);
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
