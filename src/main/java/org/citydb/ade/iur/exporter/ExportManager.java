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

package org.citydb.ade.iur.exporter;

import org.citydb.ade.iur.exporter.urf.*;
import org.citydb.ade.iur.exporter.urg.*;
import org.citydb.ade.iur.exporter.uro.*;
import org.citydb.ade.iur.exporter.urt.*;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.exporter.ADEExportManager;
import org.citydb.core.ade.exporter.ADEExporter;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citygml4j.ade.iur.model.module.PublicTransitModule;
import org.citygml4j.ade.iur.model.module.StatisticalGridModule;
import org.citygml4j.ade.iur.model.module.UrbanFunctionModule;
import org.citygml4j.ade.iur.model.urf.*;
import org.citygml4j.ade.iur.model.urg.LandUseDiversion;
import org.citygml4j.ade.iur.model.urg.*;
import org.citygml4j.ade.iur.model.urt.*;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.cityobjectgroup.CityObjectGroup;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.citygml.bridge.AbstractBridge;
import org.citygml4j.model.citygml.building.BuildingFurniture;
import org.citygml4j.model.citygml.building.BuildingInstallation;
import org.citygml4j.model.citygml.building.IntBuildingInstallation;
import org.citygml4j.model.citygml.building.Room;
import org.citygml4j.model.citygml.cityfurniture.CityFurniture;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.landuse.LandUse;
import org.citygml4j.model.citygml.relief.AbstractReliefComponent;
import org.citygml4j.model.citygml.transportation.AbstractTransportationObject;
import org.citygml4j.model.citygml.transportation.TrafficArea;
import org.citygml4j.model.citygml.transportation.TransportationComplex;
import org.citygml4j.model.citygml.tunnel.AbstractTunnel;
import org.citygml4j.model.citygml.vegetation.AbstractVegetationObject;
import org.citygml4j.model.citygml.waterbody.WaterBody;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;
import org.citygml4j.ade.iur.model.uro.AbstractConstruction;
import org.citygml4j.ade.iur.model.uro.Appurtenance;
import org.citygml4j.ade.iur.model.uro.Cable;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.ade.iur.model.uro.OtherConstruction;
import org.citygml4j.ade.iur.model.uro.Pipe;
import org.citygml4j.ade.iur.model.uro.PublicLand;
import org.citygml4j.ade.iur.model.uro.UtilityLink;
import org.citygml4j.ade.iur.model.uro.UtilityNodeContainer;
import org.citygml4j.ade.iur.model.uro.Waterway;
import org.citygml4j.ade.iur.model.urf.AbstractDistrictPlan;
import org.citygml4j.ade.iur.model.urf.Administration;
import org.citygml4j.ade.iur.model.urf.Agreement;
import org.citygml4j.ade.iur.model.urf.AreaClassification;
import org.citygml4j.ade.iur.model.urf.CensusBlock;
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForReconstruction;
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForReconstructionAndRevitalization;
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForTsunamiDisasterPrevention;
import org.citygml4j.ade.iur.model.urf.CollectiveGovernmentAndPublicOfficeFacilities;
import org.citygml4j.ade.iur.model.urf.CollectiveHousingFacilities;
import org.citygml4j.ade.iur.model.urf.CollectiveUrbanDisasterPreventionFacilities;
import org.citygml4j.ade.iur.model.urf.DID;
import org.citygml4j.ade.iur.model.urf.DevelopmentProject;
import org.citygml4j.ade.iur.model.urf.DisasterDamage;
import org.citygml4j.ade.iur.model.urf.DisasterPreventionBase;
import org.citygml4j.ade.iur.model.urf.DisasterPreventionBlockImprovementProject;
import org.citygml4j.ade.iur.model.urf.DisasterPreventionBlockImprovementZonePlan;
import org.citygml4j.ade.iur.model.urf.DistributionBusinessPark;
import org.citygml4j.ade.iur.model.urf.DistributionBusinessZone;
import org.citygml4j.ade.iur.model.urf.District;
import org.citygml4j.ade.iur.model.urf.DistrictDevelopmentPlan;
import org.citygml4j.ade.iur.model.urf.DistrictPlan;
import org.citygml4j.ade.iur.model.urf.DistrictsAndZones;
import org.citygml4j.ade.iur.model.urf.ExceptionalFloorAreaRateDistrict;
import org.citygml4j.ade.iur.model.urf.GlobalHubCityDevelopmentProject;
import org.citygml4j.ade.iur.model.urf.HeightControlDistrict;
import org.citygml4j.ade.iur.model.urf.HighLevelUseDistrict;
import org.citygml4j.ade.iur.model.urf.HighRiseResidentialAttractionDistrict;
import org.citygml4j.ade.iur.model.urf.HistoricSceneryMaintenanceAndImprovementDistrictPlan;
import org.citygml4j.ade.iur.model.urf.IndustrialParkDevelopmentProject;
import org.citygml4j.ade.iur.model.urf.LandReadjustmentProject;
import org.citygml4j.ade.iur.model.urf.LandscapeZone;
import org.citygml4j.ade.iur.model.urf.NewHousing;
import org.citygml4j.ade.iur.model.urf.NewHousingAndUrbanDevelopmentProject;
import org.citygml4j.ade.iur.model.urf.NewUrbanInfrastructureProject;
import org.citygml4j.ade.iur.model.urf.OpenSpaceForPublicUse;
import org.citygml4j.ade.iur.model.urf.Pollution;
import org.citygml4j.ade.iur.model.urf.PortZone;
import org.citygml4j.ade.iur.model.urf.PrivateUrbanRenewalProjectPlan;
import org.citygml4j.ade.iur.model.urf.ProductiveGreenZone;
import org.citygml4j.ade.iur.model.urf.ProjectPromotionArea;
import org.citygml4j.ade.iur.model.urf.PublicTransitFacility;
import org.citygml4j.ade.iur.model.urf.QuasiUrbanPlanningArea;
import org.citygml4j.ade.iur.model.urf.Recreations;
import org.citygml4j.ade.iur.model.urf.ResidentialBlockConstructionProject;
import org.citygml4j.ade.iur.model.urf.ResidentialEnvironmentImprovementDistrict;
import org.citygml4j.ade.iur.model.urf.RoadsideDistrictImprovementPlan;
import org.citygml4j.ade.iur.model.urf.RoadsideDistrictPlan;
import org.citygml4j.ade.iur.model.urf.RuralDistrictImprovementPlan;
import org.citygml4j.ade.iur.model.urf.ScenicDistrict;
import org.citygml4j.ade.iur.model.urf.ScenicResource;
import org.citygml4j.ade.iur.model.urf.ScheduledAreaForUrbanDevelopmentProject;
import org.citygml4j.ade.iur.model.urf.SedimentDisasterProneArea;
import org.citygml4j.ade.iur.model.urf.SpecialGreenSpaceConservationDistrict;
import org.citygml4j.ade.iur.model.urf.SpecialUrbanRenaissanceDistrict;
import org.citygml4j.ade.iur.model.urf.SpecialUseAttractionDistrict;
import org.citygml4j.ade.iur.model.urf.SpecialUseDistrict;
import org.citygml4j.ade.iur.model.urf.SpecialUseRestrictionDistrict;
import org.citygml4j.ade.iur.model.urf.SpecifiedBlock;
import org.citygml4j.ade.iur.model.urf.SpecifiedDisasterPreventionBlockImprovementZone;
import org.citygml4j.ade.iur.model.urf.SpecifiedUrgentUrbanRenewalArea;
import org.citygml4j.ade.iur.model.urf.SupplyFacility;
import org.citygml4j.ade.iur.model.urf.ThreeDimensionalExtent;
import org.citygml4j.ade.iur.model.urf.TrafficFacility;
import org.citygml4j.ade.iur.model.urf.TreatmentFacility;
import org.citygml4j.ade.iur.model.urf.TreePlantingDistrict;
import org.citygml4j.ade.iur.model.urf.UrbanDevelopmentProject;
import org.citygml4j.ade.iur.model.urf.UrbanDisasterRecoveryPromotionArea;
import org.citygml4j.ade.iur.model.urf.UrbanFacility;
import org.citygml4j.ade.iur.model.urf.UrbanFacilityStipulatedByCabinetOrder;
import org.citygml4j.ade.iur.model.urf.UrbanFunction;
import org.citygml4j.ade.iur.model.urf.UrbanPlanningArea;
import org.citygml4j.ade.iur.model.urf.UrbanRedevelopmentProject;
import org.citygml4j.ade.iur.model.urf.UrbanRedevelopmentPromotionArea;
import org.citygml4j.ade.iur.model.urf.UrbanRenewalProject;
import org.citygml4j.ade.iur.model.urf.Urbanization;
import org.citygml4j.ade.iur.model.urf.UrgentUrbanRenewalArea;
import org.citygml4j.ade.iur.model.urf.UseDistrict;
import org.citygml4j.ade.iur.model.urf.WeatherObservation;
import org.citygml4j.ade.iur.model.urf.ZonalDisasterPreventionFacility;
import org.citygml4j.ade.iur.model.urf.Zone;
import org.citygml4j.ade.iur.model.urg.GenericGridCell;
import org.citygml4j.ade.iur.model.urg.Households;
import org.citygml4j.ade.iur.model.urg.LandPrice;
import org.citygml4j.ade.iur.model.urg.LandslideDisaster;
import org.citygml4j.ade.iur.model.urg.OfficesAndEmployees;
import org.citygml4j.ade.iur.model.urg.Population;
import org.citygml4j.ade.iur.model.urg.PublicTransitAccessibility;
import org.citygml4j.ade.iur.model.urg.StatusOfSiteSeeing;
import org.citygml4j.ade.iur.model.urt.Agency;
import org.citygml4j.ade.iur.model.urt.Attribution;
import org.citygml4j.ade.iur.model.urt.Calendar;
import org.citygml4j.ade.iur.model.urt.CalendarDate;
import org.citygml4j.ade.iur.model.urt.FareAttribute;
import org.citygml4j.ade.iur.model.urt.Level;
import org.citygml4j.ade.iur.model.urt.Office;
import org.citygml4j.ade.iur.model.urt.Pathway;
import org.citygml4j.ade.iur.model.urt.Route;
import org.citygml4j.ade.iur.model.urt.Shape;
import org.citygml4j.ade.iur.model.urt.Stop;
import org.citygml4j.ade.iur.model.urt.TranslationJP;
import org.citygml4j.ade.iur.model.urt.Trip;
import org.citydb.ade.iur.exporter.urg.LandUseDiversionExporter;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExportManager implements ADEExportManager {
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final Map<Class<? extends ADEExporter>, ADEExporter> exporters;

    private Connection connection;
    private CityGMLExportHelper helper;
    private DocumentBuilder builder;

    public ExportManager(ObjectMapper objectMapper, SchemaMapper schemaMapper) {
        this.objectMapper = objectMapper;
        this.schemaMapper = schemaMapper;
        exporters = new HashMap<>();
    }

    @Override
    public void init(Connection connection, CityGMLExportHelper helper) throws CityGMLExportException, SQLException {
        this.connection = connection;
        this.helper = helper;
    }

    @Override
    public void exportObject(ADEModelObject object, long objectId, AbstractObjectType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        if (UrbanFunctionModule.v3_0.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof UseDistrict)
                getExporter(UseDistrictExporter.class).doExport((UseDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecialUseDistrict)
                getExporter(SpecialUseDistrictExporter.class).doExport((SpecialUseDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecialUseRestrictionDistrict)
                getExporter(SpecialUseRestrictionDistrictExporter.class).doExport((SpecialUseRestrictionDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof ExceptionalFloorAreaRateDistrict)
                getExporter(ExceptionalFloorAreaRateDistrictExporter.class).doExport((ExceptionalFloorAreaRateDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof HighRiseResidentialAttractionDistrict)
                getExporter(HighRiseResidentialAttractionDistrictExporter.class).doExport((HighRiseResidentialAttractionDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof HeightControlDistrict)
                getExporter(HeightControlDistrictExporter.class).doExport((HeightControlDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof HighLevelUseDistrict)
                getExporter(HighLevelUseDistrictExporter.class).doExport((HighLevelUseDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecifiedBlock)
                getExporter(SpecifiedBlockExporter.class).doExport((SpecifiedBlock) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecialUrbanRenaissanceDistrict)
                getExporter(SpecialUrbanRenaissanceDistrictExporter.class).doExport((SpecialUrbanRenaissanceDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof ResidentialEnvironmentImprovementDistrict)
                getExporter(ResidentialEnvironmentImprovementDistrictExporter.class).doExport((ResidentialEnvironmentImprovementDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecialUseAttractionDistrict)
                getExporter(SpecialUseAttractionDistrictExporter.class).doExport((SpecialUseAttractionDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecifiedDisasterPreventionBlockImprovementZone)
                getExporter(SpecifiedDisasterPreventionBlockImprovementZoneExporter.class).doExport((SpecifiedDisasterPreventionBlockImprovementZone) object, objectId, objectType, projectionFilter);
            else if (object instanceof LandscapeZone)
                getExporter(LandscapeZoneExporter.class).doExport((LandscapeZone) object, objectId, objectType, projectionFilter);
            else if (object instanceof ScenicDistrict)
                getExporter(ScenicDistrictExporter.class).doExport((ScenicDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof PortZone)
                getExporter(PortZoneExporter.class).doExport((PortZone) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecialGreenSpaceConservationDistrict)
                getExporter(SpecialGreenSpaceConservationDistrictExporter.class).doExport((SpecialGreenSpaceConservationDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof TreePlantingDistrict)
                getExporter(TreePlantingDistrictExporter.class).doExport((TreePlantingDistrict) object, objectId, objectType, projectionFilter);
            else if (object instanceof DistributionBusinessZone)
                getExporter(DistributionBusinessZoneExporter.class).doExport((DistributionBusinessZone) object, objectId, objectType, projectionFilter);
            else if (object instanceof ProductiveGreenZone)
                getExporter(ProductiveGreenZoneExporter.class).doExport((ProductiveGreenZone) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanRedevelopmentPromotionArea)
                getExporter(UrbanRedevelopmentPromotionAreaExporter.class).doExport((UrbanRedevelopmentPromotionArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof LandReadjustmentProject)
                getExporter(LandReadjustmentProjectExporter.class).doExport((LandReadjustmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof NewHousingAndUrbanDevelopmentProject)
                getExporter(NewHousingAndUrbanDevelopmentProjectExporter.class).doExport((NewHousingAndUrbanDevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof IndustrialParkDevelopmentProject)
                getExporter(IndustrialParkDevelopmentProjectExporter.class).doExport((IndustrialParkDevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanRedevelopmentProject)
                getExporter(UrbanRedevelopmentProjectExporter.class).doExport((UrbanRedevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof NewUrbanInfrastructureProject)
                getExporter(NewUrbanInfrastructureProjectExporter.class).doExport((NewUrbanInfrastructureProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof ResidentialBlockConstructionProject)
                getExporter(ResidentialBlockConstructionProjectExporter.class).doExport((ResidentialBlockConstructionProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterPreventionBlockImprovementProject)
                getExporter(DisasterPreventionBlockImprovementProjectExporter.class).doExport((DisasterPreventionBlockImprovementProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanRenewalProject)
                getExporter(UrbanRenewalProjectExporter.class).doExport((UrbanRenewalProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof TrafficFacility)
                getExporter(TrafficFacilityExporter.class).doExport((TrafficFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof OpenSpaceForPublicUse)
                getExporter(OpenSpaceForPublicUseExporter.class).doExport((OpenSpaceForPublicUse) object, objectId, objectType, projectionFilter);
            else if (object instanceof SupplyFacility)
                getExporter(SupplyFacilityExporter.class).doExport((SupplyFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof TreatmentFacility)
                getExporter(TreatmentFacilityExporter.class).doExport((TreatmentFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof org.citygml4j.ade.iur.model.urf.Waterway)
                getExporter(org.citydb.ade.iur.exporter.urf.WaterwayExporter.class).doExport((org.citygml4j.ade.iur.model.urf.Waterway) object, objectId, objectType, projectionFilter);
            else if (object instanceof CollectiveHousingFacilities)
                getExporter(CollectiveHousingFacilitiesExporter.class).doExport((CollectiveHousingFacilities) object, objectId, objectType, projectionFilter);
            else if (object instanceof CollectiveGovernmentAndPublicOfficeFacilities)
                getExporter(CollectiveGovernmentAndPublicOfficeFacilitiesExporter.class).doExport((CollectiveGovernmentAndPublicOfficeFacilities) object, objectId, objectType, projectionFilter);
            else if (object instanceof CollectiveUrbanDisasterPreventionFacilities)
                getExporter(CollectiveUrbanDisasterPreventionFacilitiesExporter.class).doExport((CollectiveUrbanDisasterPreventionFacilities) object, objectId, objectType, projectionFilter);
            else if (object instanceof DistributionBusinessPark)
                getExporter(DistributionBusinessParkExporter.class).doExport((DistributionBusinessPark) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanFacilityStipulatedByCabinetOrder)
                getExporter(UrbanFacilityStipulatedByCabinetOrderExporter.class).doExport((UrbanFacilityStipulatedByCabinetOrder) object, objectId, objectType, projectionFilter);
            else if (object instanceof CollectiveFacilitiesForTsunamiDisasterPrevention)
                getExporter(CollectiveFacilitiesForTsunamiDisasterPreventionExporter.class).doExport((CollectiveFacilitiesForTsunamiDisasterPrevention) object, objectId, objectType, projectionFilter);
            else if (object instanceof CollectiveFacilitiesForReconstructionAndRevitalization)
                getExporter(CollectiveFacilitiesForReconstructionAndRevitalizationExporter.class).doExport((CollectiveFacilitiesForReconstructionAndRevitalization) object, objectId, objectType, projectionFilter);
            else if (object instanceof CollectiveFacilitiesForReconstruction)
                getExporter(CollectiveFacilitiesForReconstructionExporter.class).doExport((CollectiveFacilitiesForReconstruction) object, objectId, objectType, projectionFilter);
            else if (object instanceof DistrictPlan)
                getExporter(DistrictPlanExporter.class).doExport((DistrictPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterPreventionBlockImprovementZonePlan)
                getExporter(DisasterPreventionBlockImprovementZonePlanExporter.class).doExport((DisasterPreventionBlockImprovementZonePlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof HistoricSceneryMaintenanceAndImprovementDistrictPlan)
                getExporter(HistoricSceneryMaintenanceAndImprovementDistrictPlanExporter.class).doExport((HistoricSceneryMaintenanceAndImprovementDistrictPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof RoadsideDistrictPlan)
                getExporter(RoadsideDistrictPlanExporter.class).doExport((RoadsideDistrictPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof RoadsideDistrictImprovementPlan)
                getExporter(RoadsideDistrictImprovementPlanExporter.class).doExport((RoadsideDistrictImprovementPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof RuralDistrictImprovementPlan)
                getExporter(RuralDistrictImprovementPlanExporter.class).doExport((RuralDistrictImprovementPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof SpecifiedUrgentUrbanRenewalArea)
                getExporter(SpecifiedUrgentUrbanRenewalAreaExporter.class).doExport((SpecifiedUrgentUrbanRenewalArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanPlanningArea)
                getExporter(UrbanPlanningAreaExporter.class).doExport((UrbanPlanningArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof QuasiUrbanPlanningArea)
                getExporter(QuasiUrbanPlanningAreaExporter.class).doExport((QuasiUrbanPlanningArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof AreaClassification)
                getExporter(AreaClassificationExporter.class).doExport((AreaClassification) object, objectId, objectType, projectionFilter);
            else if (object instanceof DistrictsAndZones)
                getExporter(DistrictsAndZonesExporter.class).doExport((DistrictsAndZones) object, objectId, objectType, projectionFilter);
            else if (object instanceof ProjectPromotionArea)
                getExporter(ProjectPromotionAreaExporter.class).doExport((ProjectPromotionArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanDisasterRecoveryPromotionArea)
                getExporter(UrbanDisasterRecoveryPromotionAreaExporter.class).doExport((UrbanDisasterRecoveryPromotionArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanDevelopmentProject)
                getExporter(UrbanDevelopmentProjectExporter.class).doExport((UrbanDevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof ScheduledAreaForUrbanDevelopmentProject)
                getExporter(ScheduledAreaForUrbanDevelopmentProjectExporter.class).doExport((ScheduledAreaForUrbanDevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanFacility)
                getExporter(UrbanFacilityExporter.class).doExport((UrbanFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof AbstractDistrictPlan)
                getExporter(AbstractDistrictPlanExporter.class).doExport((AbstractDistrictPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof DistrictDevelopmentPlan)
                getExporter(DistrictDevelopmentPlanExporter.class).doExport((DistrictDevelopmentPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof District)
                getExporter(DistrictExporter.class).doExport((District) object, objectId, objectType, projectionFilter);
            else if (object instanceof ZonalDisasterPreventionFacility)
                getExporter(ZonalDisasterPreventionFacilityExporter.class).doExport((ZonalDisasterPreventionFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrgentUrbanRenewalArea)
                getExporter(UrgentUrbanRenewalAreaExporter.class).doExport((UrgentUrbanRenewalArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof PrivateUrbanRenewalProjectPlan)
                getExporter(PrivateUrbanRenewalProjectPlanExporter.class).doExport((PrivateUrbanRenewalProjectPlan) object, objectId, objectType, projectionFilter);
            else if (object instanceof GlobalHubCityDevelopmentProject)
                getExporter(GlobalHubCityDevelopmentProjectExporter.class).doExport((GlobalHubCityDevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof Agreement)
                getExporter(AgreementExporter.class).doExport((Agreement) object, objectId, objectType, projectionFilter);
            else if (object instanceof SedimentDisasterProneArea)
                getExporter(SedimentDisasterProneAreaExporter.class).doExport((SedimentDisasterProneArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof Zone)
                getExporter(ZoneExporter.class).doExport((Zone) object, objectId, objectType, projectionFilter);
            else if (object instanceof ThreeDimensionalExtent)
                getExporter(ThreeDimensionalExtentExporter.class).doExport((ThreeDimensionalExtent) object, objectId, objectType, projectionFilter);
            else if (object instanceof Administration)
                getExporter(AdministrationExporter.class).doExport((Administration) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterDamage)
                getExporter(DisasterDamageExporter.class).doExport((DisasterDamage) object, objectId, objectType, projectionFilter);
            else if (object instanceof Pollution)
                getExporter(PollutionExporter.class).doExport((Pollution) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterPreventionBase)
                getExporter(DisasterPreventionBaseExporter.class).doExport((DisasterPreventionBase) object, objectId, objectType, projectionFilter);
            else if (object instanceof Recreations)
                getExporter(RecreationsExporter.class).doExport((Recreations) object, objectId, objectType, projectionFilter);
            else if (object instanceof org.citygml4j.ade.iur.model.urf.LandUseDiversion)
                getExporter(org.citydb.ade.iur.exporter.urf.LandUseDiversionExporter.class).doExport((org.citygml4j.ade.iur.model.urf.LandUseDiversion) object, objectId, objectType, projectionFilter);
            else if (object instanceof Urbanization)
                getExporter(UrbanizationExporter.class).doExport((Urbanization) object, objectId, objectType, projectionFilter);
            else if (object instanceof DID)
                getExporter(DIDExporter.class).doExport((DID) object, objectId, objectType, projectionFilter);
            else if (object instanceof CensusBlock)
                getExporter(CensusBlockExporter.class).doExport((CensusBlock) object, objectId, objectType, projectionFilter);
            else if (object instanceof DevelopmentProject)
                getExporter(DevelopmentProjectExporter.class).doExport((DevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof NewHousing)
                getExporter(NewHousingExporter.class).doExport((NewHousing) object, objectId, objectType, projectionFilter);
            else if (object instanceof PublicTransitFacility)
                getExporter(PublicTransitFacilityExporter.class).doExport((PublicTransitFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof WeatherObservation)
                getExporter(WeatherObservationExporter.class).doExport((WeatherObservation) object, objectId, objectType, projectionFilter);
            else if (object instanceof ScenicResource)
                getExporter(ScenicResourceExporter.class).doExport((ScenicResource) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanFunction)
                getExporter(UrbanFunctionExporter.class).doExport((UrbanFunction) object, objectId, objectType, projectionFilter);
        } else if (StatisticalGridModule.v3_0.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof Population)
                getExporter(PopulationExporter.class).doExport((Population) object, objectId, objectType, projectionFilter);
            else if (object instanceof PublicTransitAccessibility)
                getExporter(PublicTransitAccessibilityExporter.class).doExport((PublicTransitAccessibility) object, objectId, objectType, projectionFilter);
            else if (object instanceof LandPrice)
                getExporter(LandPriceExporter.class).doExport((LandPrice) object, objectId, objectType, projectionFilter);
            else if (object instanceof org.citygml4j.ade.iur.model.urg.LandUseDiversion)
                getExporter(org.citydb.ade.iur.exporter.urg.LandUseDiversionExporter.class).doExport((org.citygml4j.ade.iur.model.urg.LandUseDiversion) object, objectId, objectType, projectionFilter);
            else if (object instanceof Households)
                getExporter(HouseholdsExporter.class).doExport((Households) object, objectId, objectType, projectionFilter);
            else if (object instanceof OfficesAndEmployees)
                getExporter(OfficesAndEmployeesExporter.class).doExport((OfficesAndEmployees) object, objectId, objectType, projectionFilter);
            else if (object instanceof GenericGridCell)
                getExporter(GenericGridCellExporter.class).doExport((GenericGridCell) object, objectId, objectType, projectionFilter);
            else if (object instanceof StatusOfSiteSeeing)
                getExporter(StatusOfSiteSeeingExporter.class).doExport((StatusOfSiteSeeing) object, objectId, objectType, projectionFilter);
            else if (object instanceof LandslideDisaster)
                getExporter(LandslideDisasterExporter.class).doExport((LandslideDisaster) object, objectId, objectType, projectionFilter);
        } else if (PublicTransitModule.v3_0.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof Route)
                getExporter(RouteExporter.class).doExport((Route) object, objectId, objectType, projectionFilter);
            else if (object instanceof Agency)
                getExporter(AgencyExporter.class).doExport((Agency) object, objectId, objectType, projectionFilter);
            else if (object instanceof Stop)
                getExporter(StopExporter.class).doExport((Stop) object, objectId, objectType, projectionFilter);
            else if (object instanceof Level)
                getExporter(LevelExporter.class).doExport((Level) object, objectId, objectType, projectionFilter);
            else if (object instanceof Trip)
                getExporter(TripExporter.class).doExport((Trip) object, objectId, objectType, projectionFilter);
            else if (object instanceof Shape)
                getExporter(ShapeExporter.class).doExport((Shape) object, objectId, objectType, projectionFilter);
            else if (object instanceof Office)
                getExporter(OfficeExporter.class).doExport((Office) object, objectId, objectType, projectionFilter);
            else if (object instanceof Calendar)
                getExporter(CalendarExporter.class).doExport((Calendar) object, objectId, objectType, projectionFilter);
            else if (object instanceof CalendarDate)
                getExporter(CalendarDateExporter.class).doExport((CalendarDate) object, objectId, objectType, projectionFilter);
            else if (object instanceof FareAttribute)
                getExporter(FareAttributeExporter.class).doExport((FareAttribute) object, objectId, objectType, projectionFilter);
            else if (object instanceof Pathway)
                getExporter(PathwayExporter.class).doExport((Pathway) object, objectId, objectType, projectionFilter);
            else if (object instanceof TranslationJP)
                getExporter(TranslationJPExporter.class).doExport((TranslationJP) object, objectId, objectType, projectionFilter);
            else if (object instanceof Attribution)
                getExporter(AttributionExporter.class).doExport((Attribution) object, objectId, objectType, projectionFilter);
        } else if (UrbanObjectModule.v3_0.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof Pipe)
                getExporter(PipeExporter.class).doExport((Pipe) object, objectId, objectType, projectionFilter);
            else if (object instanceof Cable)
                getExporter(CableExporter.class).doExport((Cable) object, objectId, objectType, projectionFilter);
            else if (object instanceof OtherConstruction)
                getExporter(OtherConstructionExporter.class).doExport((OtherConstruction) object, objectId, objectType, projectionFilter);
            else if (object instanceof UtilityLink)
                getExporter(UtilityLinkExporter.class).doExport((UtilityLink) object, objectId, objectType, projectionFilter);
            else if (object instanceof Appurtenance)
                getExporter(AppurtenanceExporter.class).doExport((Appurtenance) object, objectId, objectType, projectionFilter);
            else if (object instanceof UtilityNodeContainer)
                getExporter(UtilityNodeContainerExporter.class).doExport((UtilityNodeContainer) object, objectId, objectType, projectionFilter);
            else if (object instanceof org.citygml4j.ade.iur.model.uro.Waterway)
                getExporter(org.citydb.ade.iur.exporter.uro.WaterwayExporter.class).doExport((org.citygml4j.ade.iur.model.uro.Waterway) object, objectId, objectType, projectionFilter);
            else if (object instanceof AbstractConstruction)
                getExporter(AbstractConstructionExporter.class).doExport((AbstractConstruction) object, objectId, objectType, projectionFilter);
            else if (object instanceof org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface)
                getExporter(AbstractBoundarySurfaceExporter.class).doExport((org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface) object, objectId, objectType, projectionFilter);
            else if (object instanceof ConstructionInstallation)
                getExporter(ConstructionInstallationExporter.class).doExport((ConstructionInstallation) object, objectId, objectType, projectionFilter);
            else if (object instanceof PublicLand)
                getExporter(PublicLandExporter.class).doExport((PublicLand) object, objectId, objectType, projectionFilter);
        }
    }

    @Override
    public void exportGenericApplicationProperties(String adeHookTable, AbstractFeature parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BRIDGE)) && parent instanceof AbstractBridge)
            getExporter(BridgePropertiesExporter.class).doExport((AbstractBridge) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITY_FURNITURE)) && parent instanceof CityFurniture)
            getExporter(CityFurniturePropertiesExporter.class).doExport((CityFurniture) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TRANSPORTATION_COMPLEX)) && parent instanceof TransportationComplex)
            getExporter(TransportationComplexPropertiesExporter.class).doExport((TransportationComplex) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECT)) && (parent instanceof AbstractTransportationObject || parent instanceof AbstractVegetationObject))
            getExporter(TransportationObjectPropertiesExporter.class).doExport((AbstractCityObject) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TUNNEL)) && parent instanceof AbstractTunnel)
            getExporter(TunnelPropertiesExporter.class).doExport((AbstractTunnel) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BUILDING)) && parent instanceof AbstractBuilding)
            getExporter(BuildingPropertiesExporter.class).doExport((AbstractBuilding) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.LAND_USE)) && parent instanceof LandUse)
            getExporter(LandUsePropertiesExporter.class).doExport((LandUse) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.WATERBODY)) && parent instanceof WaterBody)
            getExporter(WaterBodyPropertiesExporter.class).doExport((WaterBody) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.RELIEF_COMPONENT)) && parent instanceof AbstractReliefComponent)
            getExporter(ReliefComponentPropertiesExporter.class).doExport((AbstractReliefComponent) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TRAFFIC_AREA)) && parent instanceof TrafficArea)
            getExporter(TrafficAreaPropertiesExporter.class).doExport((TrafficArea) parent, parentId, parentType, projectionFilter);
        if (parent instanceof CityObjectGroup) {
            if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_1)))
                getExporter(org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
            else if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_3)))
                getExporter(org.citydb.ade.iur.exporter.urt.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
            else if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP)))
                getExporter(org.citydb.ade.iur.exporter.urg.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
        }
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.ROOM)) && parent instanceof Room)
            getExporter(RoomPropertiesExporter.class).doExport((Room) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.OPENING)) && parent instanceof org.citygml4j.model.citygml.building.AbstractOpening)
            getExporter(OpeningPropertiesExporter.class).doExport((org.citygml4j.model.citygml.building.AbstractOpening) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.THEMATIC_SURFACE)) && parent instanceof org.citygml4j.model.citygml.building.AbstractBoundarySurface)
            getExporter(BoundarySurfacePropertiesExporter.class).doExport((org.citygml4j.model.citygml.building.AbstractBoundarySurface) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BUILDING_INSTALLATION)) && (parent instanceof BuildingInstallation || parent instanceof IntBuildingInstallation))
            getExporter(BuildingInstallationPropertiesExporter.class).doExport((AbstractCityObject) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BUILDING_FURNITURE)) && parent instanceof BuildingFurniture)
            getExporter(BuildingFurniturePropertiesExporter.class).doExport((BuildingFurniture) parent, parentId, parentType, projectionFilter);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        for (ADEExporter exporter : exporters.values())
            exporter.close();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public SchemaMapper getSchemaMapper() {
        return schemaMapper;
    }

    public Element unmarshal(String element) throws ParserConfigurationException, IOException, SAXException {
        if (builder == null) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        }

        try {
            return builder.parse(new InputSource(new StringReader(element))).getDocumentElement();
        } finally {
            builder.reset();
        }
    }

    public <T extends ADEExporter> T getExporter(Class<T> type) throws CityGMLExportException, SQLException {
        ADEExporter exporter = exporters.get(type);

        if (exporter == null) {
            if (UrbanFunctionModuleExporter.class.isAssignableFrom(type)) {
                // urban function module
                if (type == BoundaryExporter.class)
                    exporter = new BoundaryExporter(connection, helper, this);
                else if (type == UrbanRoadAttributeExporter.class)
                    exporter = new UrbanRoadAttributeExporter(connection, helper, this);
                else if (type == StructureDetailsExporter.class)
                    exporter = new StructureDetailsExporter(connection, helper, this);
                else if (type == UrbanRapidTransitRailroadAttributeExporter.class)
                    exporter = new UrbanRapidTransitRailroadAttributeExporter(connection, helper, this);
                else if (type == ParkingPlaceAttributeExporter.class)
                    exporter = new ParkingPlaceAttributeExporter(connection, helper, this);
                else if (type == VehicleTerminalAttributeExporter.class)
                    exporter = new VehicleTerminalAttributeExporter(connection, helper, this);
                else if (type == ParkAttributeExporter.class)
                    exporter = new ParkAttributeExporter(connection, helper, this);
                else if (type == WaterWorksAttributeExporter.class)
                    exporter = new WaterWorksAttributeExporter(connection, helper, this);
                else if (type == SewerSystemAttributeExporter.class)
                    exporter = new SewerSystemAttributeExporter(connection, helper, this);
                else if (type == ObservationPointExporter.class)
                    exporter = new ObservationPointExporter(connection, helper, this);
                else if (type == FarmlandConversionTypeExporter.class)
                    exporter = new FarmlandConversionTypeExporter(connection, helper, this);
                else if (type == ForestConversionTypeExporter.class)
                    exporter = new ForestConversionTypeExporter(connection, helper, this);
                else if (type == PopulationTypeExporter.class)
                    exporter = new PopulationTypeExporter(connection, helper, this);
                else if (type == PopulationByAgeAndSexTypeExporter.class)
                    exporter = new PopulationByAgeAndSexTypeExporter(connection, helper, this);
                else if (type == DaytimePopulationTypeExporter.class)
                    exporter = new DaytimePopulationTypeExporter(connection, helper, this);
                else if (type == EconomicActivityTypeExporter.class)
                    exporter = new EconomicActivityTypeExporter(connection, helper, this);
                else if (type == EconomicActivitySumTypeExporter.class)
                    exporter = new EconomicActivitySumTypeExporter(connection, helper, this);
                else if (type == ResidentialDevelopmentTypeExporter.class)
                    exporter = new ResidentialDevelopmentTypeExporter(connection, helper, this);
                else if (type == UrbanDevelopmentProjectTypeExporter.class)
                    exporter = new UrbanDevelopmentProjectTypeExporter(connection, helper, this);
                else if (type == AgriculturalDevelopmentProjectTypeExporter.class)
                    exporter = new AgriculturalDevelopmentProjectTypeExporter(connection, helper, this);
                else if (type == WeatherObservationTypeExporter.class)
                    exporter = new WeatherObservationTypeExporter(connection, helper, this);
                else if (type == UseDistrictExporter.class)
                    exporter = new UseDistrictExporter(connection, helper, this);
                else if (type == SpecialUseDistrictExporter.class)
                    exporter = new SpecialUseDistrictExporter(connection, helper, this);
                else if (type == SpecialUseRestrictionDistrictExporter.class)
                    exporter = new SpecialUseRestrictionDistrictExporter(connection, helper, this);
                else if (type == ExceptionalFloorAreaRateDistrictExporter.class)
                    exporter = new ExceptionalFloorAreaRateDistrictExporter(connection, helper, this);
                else if (type == HighRiseResidentialAttractionDistrictExporter.class)
                    exporter = new HighRiseResidentialAttractionDistrictExporter(connection, helper, this);
                else if (type == HeightControlDistrictExporter.class)
                    exporter = new HeightControlDistrictExporter(connection, helper, this);
                else if (type == HighLevelUseDistrictExporter.class)
                    exporter = new HighLevelUseDistrictExporter(connection, helper, this);
                else if (type == SpecifiedBlockExporter.class)
                    exporter = new SpecifiedBlockExporter(connection, helper, this);
                else if (type == SpecialUrbanRenaissanceDistrictExporter.class)
                    exporter = new SpecialUrbanRenaissanceDistrictExporter(connection, helper, this);
                else if (type == ResidentialEnvironmentImprovementDistrictExporter.class)
                    exporter = new ResidentialEnvironmentImprovementDistrictExporter(connection, helper, this);
                else if (type == SpecialUseAttractionDistrictExporter.class)
                    exporter = new SpecialUseAttractionDistrictExporter(connection, helper, this);
                else if (type == SpecifiedDisasterPreventionBlockImprovementZoneExporter.class)
                    exporter = new SpecifiedDisasterPreventionBlockImprovementZoneExporter(connection, helper, this);
                else if (type == LandscapeZoneExporter.class)
                    exporter = new LandscapeZoneExporter(connection, helper, this);
                else if (type == ScenicDistrictExporter.class)
                    exporter = new ScenicDistrictExporter(connection, helper, this);
                else if (type == PortZoneExporter.class)
                    exporter = new PortZoneExporter(connection, helper, this);
                else if (type == SpecialGreenSpaceConservationDistrictExporter.class)
                    exporter = new SpecialGreenSpaceConservationDistrictExporter(connection, helper, this);
                else if (type == TreePlantingDistrictExporter.class)
                    exporter = new TreePlantingDistrictExporter(connection, helper, this);
                else if (type == DistributionBusinessZoneExporter.class)
                    exporter = new DistributionBusinessZoneExporter(connection, helper, this);
                else if (type == ProductiveGreenZoneExporter.class)
                    exporter = new ProductiveGreenZoneExporter(connection, helper, this);
                else if (type == UrbanRedevelopmentPromotionAreaExporter.class)
                    exporter = new UrbanRedevelopmentPromotionAreaExporter(connection, helper, this);
                else if (type == LandReadjustmentProjectExporter.class)
                    exporter = new LandReadjustmentProjectExporter(connection, helper, this);
                else if (type == NewHousingAndUrbanDevelopmentProjectExporter.class)
                    exporter = new NewHousingAndUrbanDevelopmentProjectExporter(connection, helper, this);
                else if (type == IndustrialParkDevelopmentProjectExporter.class)
                    exporter = new IndustrialParkDevelopmentProjectExporter(connection, helper, this);
                else if (type == UrbanRedevelopmentProjectExporter.class)
                    exporter = new UrbanRedevelopmentProjectExporter(connection, helper, this);
                else if (type == NewUrbanInfrastructureProjectExporter.class)
                    exporter = new NewUrbanInfrastructureProjectExporter(connection, helper, this);
                else if (type == ResidentialBlockConstructionProjectExporter.class)
                    exporter = new ResidentialBlockConstructionProjectExporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementProjectExporter.class)
                    exporter = new DisasterPreventionBlockImprovementProjectExporter(connection, helper, this);
                else if (type == UrbanRenewalProjectExporter.class)
                    exporter = new UrbanRenewalProjectExporter(connection, helper, this);
                else if (type == TrafficFacilityExporter.class)
                    exporter = new TrafficFacilityExporter(connection, helper, this);
                else if (type == OpenSpaceForPublicUseExporter.class)
                    exporter = new OpenSpaceForPublicUseExporter(connection, helper, this);
                else if (type == SupplyFacilityExporter.class)
                    exporter = new SupplyFacilityExporter(connection, helper, this);
                else if (type == TreatmentFacilityExporter.class)
                    exporter = new TreatmentFacilityExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.urf.WaterwayExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urf.WaterwayExporter(connection, helper, this);
                else if (type == CollectiveHousingFacilitiesExporter.class)
                    exporter = new CollectiveHousingFacilitiesExporter(connection, helper, this);
                else if (type == CollectiveGovernmentAndPublicOfficeFacilitiesExporter.class)
                    exporter = new CollectiveGovernmentAndPublicOfficeFacilitiesExporter(connection, helper, this);
                else if (type == CollectiveUrbanDisasterPreventionFacilitiesExporter.class)
                    exporter = new CollectiveUrbanDisasterPreventionFacilitiesExporter(connection, helper, this);
                else if (type == DistributionBusinessParkExporter.class)
                    exporter = new DistributionBusinessParkExporter(connection, helper, this);
                else if (type == UrbanFacilityStipulatedByCabinetOrderExporter.class)
                    exporter = new UrbanFacilityStipulatedByCabinetOrderExporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForTsunamiDisasterPreventionExporter.class)
                    exporter = new CollectiveFacilitiesForTsunamiDisasterPreventionExporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForReconstructionAndRevitalizationExporter.class)
                    exporter = new CollectiveFacilitiesForReconstructionAndRevitalizationExporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForReconstructionExporter.class)
                    exporter = new CollectiveFacilitiesForReconstructionExporter(connection, helper, this);
                else if (type == DistrictPlanExporter.class)
                    exporter = new DistrictPlanExporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementZonePlanExporter.class)
                    exporter = new DisasterPreventionBlockImprovementZonePlanExporter(connection, helper, this);
                else if (type == HistoricSceneryMaintenanceAndImprovementDistrictPlanExporter.class)
                    exporter = new HistoricSceneryMaintenanceAndImprovementDistrictPlanExporter(connection, helper, this);
                else if (type == RoadsideDistrictPlanExporter.class)
                    exporter = new RoadsideDistrictPlanExporter(connection, helper, this);
                else if (type == RoadsideDistrictImprovementPlanExporter.class)
                    exporter = new RoadsideDistrictImprovementPlanExporter(connection, helper, this);
                else if (type == RuralDistrictImprovementPlanExporter.class)
                    exporter = new RuralDistrictImprovementPlanExporter(connection, helper, this);
                else if (type == SpecifiedUrgentUrbanRenewalAreaExporter.class)
                    exporter = new SpecifiedUrgentUrbanRenewalAreaExporter(connection, helper, this);
                else if (type == UrbanPlanningAreaExporter.class)
                    exporter = new UrbanPlanningAreaExporter(connection, helper, this);
                else if (type == QuasiUrbanPlanningAreaExporter.class)
                    exporter = new QuasiUrbanPlanningAreaExporter(connection, helper, this);
                else if (type == AreaClassificationExporter.class)
                    exporter = new AreaClassificationExporter(connection, helper, this);
                else if (type == DistrictsAndZonesExporter.class)
                    exporter = new DistrictsAndZonesExporter(connection, helper, this);
                else if (type == ProjectPromotionAreaExporter.class)
                    exporter = new ProjectPromotionAreaExporter(connection, helper, this);
                else if (type == UrbanDisasterRecoveryPromotionAreaExporter.class)
                    exporter = new UrbanDisasterRecoveryPromotionAreaExporter(connection, helper, this);
                else if (type == UrbanDevelopmentProjectExporter.class)
                    exporter = new UrbanDevelopmentProjectExporter(connection, helper, this);
                else if (type == ScheduledAreaForUrbanDevelopmentProjectExporter.class)
                    exporter = new ScheduledAreaForUrbanDevelopmentProjectExporter(connection, helper, this);
                else if (type == UrbanFacilityExporter.class)
                    exporter = new UrbanFacilityExporter(connection, helper, this);
                else if (type == AbstractDistrictPlanExporter.class)
                    exporter = new AbstractDistrictPlanExporter(connection, helper, this);
                else if (type == DistrictDevelopmentPlanExporter.class)
                    exporter = new DistrictDevelopmentPlanExporter(connection, helper, this);
                else if (type == DistrictExporter.class)
                    exporter = new DistrictExporter(connection, helper, this);
                else if (type == ZonalDisasterPreventionFacilityExporter.class)
                    exporter = new ZonalDisasterPreventionFacilityExporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaExporter.class)
                    exporter = new UrgentUrbanRenewalAreaExporter(connection, helper, this);
                else if (type == PrivateUrbanRenewalProjectPlanExporter.class)
                    exporter = new PrivateUrbanRenewalProjectPlanExporter(connection, helper, this);
                else if (type == GlobalHubCityDevelopmentProjectExporter.class)
                    exporter = new GlobalHubCityDevelopmentProjectExporter(connection, helper, this);
                else if (type == AgreementExporter.class)
                    exporter = new AgreementExporter(connection, helper, this);
                else if (type == SedimentDisasterProneAreaExporter.class)
                    exporter = new SedimentDisasterProneAreaExporter(connection, helper, this);
                else if (type == ZoneExporter.class)
                    exporter = new ZoneExporter(connection, helper, this);
                else if (type == ThreeDimensionalExtentExporter.class)
                    exporter = new ThreeDimensionalExtentExporter(connection, helper, this);
                else if (type == AdministrationExporter.class)
                    exporter = new AdministrationExporter(connection, helper, this);
                else if (type == DisasterDamageExporter.class)
                    exporter = new DisasterDamageExporter(connection, helper, this);
                else if (type == PollutionExporter.class)
                    exporter = new PollutionExporter(connection, helper, this);
                else if (type == DisasterPreventionBaseExporter.class)
                    exporter = new DisasterPreventionBaseExporter(connection, helper, this);
                else if (type == RecreationsExporter.class)
                    exporter = new RecreationsExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.urf.LandUseDiversionExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urf.LandUseDiversionExporter(connection, helper, this);
                else if (type == UrbanizationExporter.class)
                    exporter = new UrbanizationExporter(connection, helper, this);
                else if (type == DIDExporter.class)
                    exporter = new DIDExporter(connection, helper, this);
                else if (type == CensusBlockExporter.class)
                    exporter = new CensusBlockExporter(connection, helper, this);
                else if (type == DevelopmentProjectExporter.class)
                    exporter = new DevelopmentProjectExporter(connection, helper, this);
                else if (type == NewHousingExporter.class)
                    exporter = new NewHousingExporter(connection, helper, this);
                else if (type == PublicTransitFacilityExporter.class)
                    exporter = new PublicTransitFacilityExporter(connection, helper, this);
                else if (type == WeatherObservationExporter.class)
                    exporter = new WeatherObservationExporter(connection, helper, this);
                else if (type == ScenicResourceExporter.class)
                    exporter = new ScenicResourceExporter(connection, helper, this);
                else if (type == UrbanFunctionExporter.class)
                    exporter = new UrbanFunctionExporter(connection, helper, this);
            } else if (StatisticalGridModuleExporter.class.isAssignableFrom(type)) {
                // statistical grid module
                if (type == org.citydb.ade.iur.exporter.urg.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urg.CityObjectGroupPropertiesExporter(connection, helper, this);
                else if (type == PopulationByAgeAndSexExporter.class)
                    exporter = new PopulationByAgeAndSexExporter(connection, helper, this);
                else if (type == LandPricePerLandUseExporter.class)
                    exporter = new LandPricePerLandUseExporter(connection, helper, this);
                else if (type == NumberOfAnnualDiversionsExporter.class)
                    exporter = new NumberOfAnnualDiversionsExporter(connection, helper, this);
                else if (type == AreaOfAnnualDiversionsExporter.class)
                    exporter = new AreaOfAnnualDiversionsExporter(connection, helper, this);
                else if (type == NumberOfHouseholdsExporter.class)
                    exporter = new NumberOfHouseholdsExporter(connection, helper, this);
                else if (type == KeyValuePairExporter.class)
                    exporter = new KeyValuePairExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.urg.LandUseDiversionExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urg.LandUseDiversionExporter(connection, helper, this);
                else if (type == PopulationExporter.class)
                    exporter = new PopulationExporter(connection, helper, this);
                else if (type == PublicTransitAccessibilityExporter.class)
                    exporter = new PublicTransitAccessibilityExporter(connection, helper, this);
                else if (type == LandPriceExporter.class)
                    exporter = new LandPriceExporter(connection, helper, this);
                else if (type == HouseholdsExporter.class)
                    exporter = new HouseholdsExporter(connection, helper, this);
                else if (type == OfficesAndEmployeesExporter.class)
                    exporter = new OfficesAndEmployeesExporter(connection, helper, this);
                else if (type == GenericGridCellExporter.class)
                    exporter = new GenericGridCellExporter(connection, helper, this);
                else if (type == StatusOfSiteSeeingExporter.class)
                    exporter = new StatusOfSiteSeeingExporter(connection, helper, this);
                else if (type == LandslideDisasterExporter.class)
                    exporter = new LandslideDisasterExporter(connection, helper, this);
                else if (type == StatisticalGridExporter.class)
                    exporter = new StatisticalGridExporter(connection, helper, this);
            } else if (UrbanObjectModuleExporter.class.isAssignableFrom(type)) {
                // urban object module
                if (type == BridgePropertiesExporter.class)
                    exporter = new BridgePropertiesExporter(connection, helper, this);
                else if (type == CityFurniturePropertiesExporter.class)
                    exporter = new CityFurniturePropertiesExporter(connection, helper, this);
                else if (type == TransportationComplexPropertiesExporter.class)
                    exporter = new TransportationComplexPropertiesExporter(connection, helper, this);
                else if (type == TransportationObjectPropertiesExporter.class)
                    exporter = new TransportationObjectPropertiesExporter(connection, helper, this);
                else if (type == TunnelPropertiesExporter.class)
                    exporter = new TunnelPropertiesExporter(connection, helper, this);
                else if (type == BuildingPropertiesExporter.class)
                    exporter = new BuildingPropertiesExporter(connection, helper, this);
                else if (type == LandUsePropertiesExporter.class)
                    exporter = new LandUsePropertiesExporter(connection, helper, this);
                else if (type == WaterBodyPropertiesExporter.class)
                    exporter = new WaterBodyPropertiesExporter(connection, helper, this);
                else if (type == ReliefComponentPropertiesExporter.class)
                    exporter = new ReliefComponentPropertiesExporter(connection, helper, this);
                else if (type == TrafficAreaPropertiesExporter.class)
                    exporter = new TrafficAreaPropertiesExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter(connection, helper, this);
                else if (type == RoomPropertiesExporter.class)
                    exporter = new RoomPropertiesExporter(connection, helper, this);
                else if (type == OpeningPropertiesExporter.class)
                    exporter = new OpeningPropertiesExporter(connection, helper, this);
                else if (type == BoundarySurfacePropertiesExporter.class)
                    exporter = new BoundarySurfacePropertiesExporter(connection, helper, this);
                else if (type == BuildingInstallationPropertiesExporter.class)
                    exporter = new BuildingInstallationPropertiesExporter(connection, helper, this);
                else if (type == BuildingFurniturePropertiesExporter.class)
                    exporter = new BuildingFurniturePropertiesExporter(connection, helper, this);
                else if (type == ConstructionBaseAttributeExporter.class)
                    exporter = new ConstructionBaseAttributeExporter(connection, helper, this);
                else if (type == FacilityIdAttributeExporter.class)
                    exporter = new FacilityIdAttributeExporter(connection, helper, this);
                else if (type == FacilityTypeAttributeExporter.class)
                    exporter = new FacilityTypeAttributeExporter(connection, helper, this);
                else if (type == FacilityAttributeExporter.class)
                    exporter = new FacilityAttributeExporter(connection, helper, this);
                else if (type == BridgeStructureAttributeExporter.class)
                    exporter = new BridgeStructureAttributeExporter(connection, helper, this);
                else if (type == BridgeFunctionalAttributeExporter.class)
                    exporter = new BridgeFunctionalAttributeExporter(connection, helper, this);
                else if (type == ConstructionRiskAssessmentAttributeExporter.class)
                    exporter = new ConstructionRiskAssessmentAttributeExporter(connection, helper, this);
                else if (type == DisasterRiskAttributeExporter.class)
                    exporter = new DisasterRiskAttributeExporter(connection, helper, this);
                else if (type == DmAttributeExporter.class)
                    exporter = new DmAttributeExporter(connection, helper, this);
                else if (type == DmElementExporter.class)
                    exporter = new DmElementExporter(connection, helper, this);
                else if (type == ConstructionDataQualityAttributeExporter.class)
                    exporter = new ConstructionDataQualityAttributeExporter(connection, helper, this);
                else if (type == TransportationDataQualityAttributeExporter.class)
                    exporter = new TransportationDataQualityAttributeExporter(connection, helper, this);
                else if (type == TunnelStructureAttributeExporter.class)
                    exporter = new TunnelStructureAttributeExporter(connection, helper, this);
                else if (type == TunnelFunctionalAttributeExporter.class)
                    exporter = new TunnelFunctionalAttributeExporter(connection, helper, this);
                else if (type == RailwayRouteAttributeExporter.class)
                    exporter = new RailwayRouteAttributeExporter(connection, helper, this);
                else if (type == RailwayTrackAttributeExporter.class)
                    exporter = new RailwayTrackAttributeExporter(connection, helper, this);
                else if (type == ControlPointExporter.class)
                    exporter = new ControlPointExporter(connection, helper, this);
                else if (type == TrackAttributeExporter.class)
                    exporter = new TrackAttributeExporter(connection, helper, this);
                else if (type == SquareUrbanPlanAttributeExporter.class)
                    exporter = new SquareUrbanPlanAttributeExporter(connection, helper, this);
                else if (type == IfcAttributeExporter.class)
                    exporter = new IfcAttributeExporter(connection, helper, this);
                else if (type == IfcGeometricRepresentationContextExporter.class)
                    exporter = new IfcGeometricRepresentationContextExporter(connection, helper, this);
                else if (type == IfcAxis2Placement3DExporter.class)
                    exporter = new IfcAxis2Placement3DExporter(connection, helper, this);
                else if (type == IfcUnitExporter.class)
                    exporter = new IfcUnitExporter(connection, helper, this);
                else if (type == IfcCoordinateReferenceSystemSelectExporter.class)
                    exporter = new IfcCoordinateReferenceSystemSelectExporter(connection, helper, this);
                else if (type == IfcClassificationExporter.class)
                    exporter = new IfcClassificationExporter(connection, helper, this);
                else if (type == RoomDataQualityAttributeExporter.class)
                    exporter = new RoomDataQualityAttributeExporter(connection, helper, this);
                else if (type == IndoorAttributeExporter.class)
                    exporter = new IndoorAttributeExporter(connection, helper, this);
                else if (type == UserDefinedValueExporter.class)
                    exporter = new UserDefinedValueExporter(connection, helper, this);
                else if (type == LandUseDataQualityAttributeExporter.class)
                    exporter = new LandUseDataQualityAttributeExporter(connection, helper, this);
                else if (type == RoadTypeExporter.class)
                    exporter = new RoadTypeExporter(connection, helper, this);
                else if (type == BuildingAttributeExporter.class)
                    exporter = new BuildingAttributeExporter(connection, helper, this);
                else if (type == KeyValuePairAttributeExporter.class)
                    exporter = new KeyValuePairAttributeExporter(connection, helper, this);
                else if (type == LandUseDetailAttributeExporter.class)
                    exporter = new LandUseDetailAttributeExporter(connection, helper, this);
                else if (type == TrafficVolumeAttributeExporter.class)
                    exporter = new TrafficVolumeAttributeExporter(connection, helper, this);
                else if (type == RoadStructureAttributeExporter.class)
                    exporter = new RoadStructureAttributeExporter(connection, helper, this);
                else if (type == TrafficAreaStructureAttributeExporter.class)
                    exporter = new TrafficAreaStructureAttributeExporter(connection, helper, this);
                else if (type == CityFurnitureDetailAttributeExporter.class)
                    exporter = new CityFurnitureDetailAttributeExporter(connection, helper, this);
                else if (type == CityFurnitureDataQualityAttributeExporter.class)
                    exporter = new CityFurnitureDataQualityAttributeExporter(connection, helper, this);
                else if (type == VegetationDataQualityAttributeExporter.class)
                    exporter = new VegetationDataQualityAttributeExporter(connection, helper, this);
                else if (type == WaterBodyAttributeExporter.class)
                    exporter = new WaterBodyAttributeExporter(connection, helper, this);
                else if (type == WaterBodyDetailAttributeExporter.class)
                    exporter = new WaterBodyDetailAttributeExporter(connection, helper, this);
                else if (type == WaterwayDetailAttributeExporter.class)
                    exporter = new WaterwayDetailAttributeExporter(connection, helper, this);
                else if (type == ConstructionEventExporter.class)
                    exporter = new ConstructionEventExporter(connection, helper, this);
                else if (type == ElevationExporter.class)
                    exporter = new ElevationExporter(connection, helper, this);
                else if (type == HeightExporter.class)
                    exporter = new HeightExporter(connection, helper, this);
                else if (type == OccupancyExporter.class)
                    exporter = new OccupancyExporter(connection, helper, this);
                else if (type == ConstructionStructureAttributeExporter.class)
                    exporter = new ConstructionStructureAttributeExporter(connection, helper, this);
                else if (type == OffsetDepthExporter.class)
                    exporter = new OffsetDepthExporter(connection, helper, this);
                else if (type == ThematicShapeExporter.class)
                    exporter = new ThematicShapeExporter(connection, helper, this);
                else if (type == LengthAttributeExporter.class)
                    exporter = new LengthAttributeExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.uro.WaterwayExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.uro.WaterwayExporter(connection, helper, this);
                else if (type == PipeExporter.class)
                    exporter = new PipeExporter(connection, helper, this);
                else if (type == CableExporter.class)
                    exporter = new CableExporter(connection, helper, this);
                else if (type == OtherConstructionExporter.class)
                    exporter = new OtherConstructionExporter(connection, helper, this);
                else if (type == UtilityLinkExporter.class)
                    exporter = new UtilityLinkExporter(connection, helper, this);
                else if (type == AppurtenanceExporter.class)
                    exporter = new AppurtenanceExporter(connection, helper, this);
                else if (type == UtilityNodeContainerExporter.class)
                    exporter = new UtilityNodeContainerExporter(connection, helper, this);
                else if (type == AbstractConstructionExporter.class)
                    exporter = new AbstractConstructionExporter(connection, helper, this);
                else if (type == AbstractBoundarySurfaceExporter.class)
                    exporter = new AbstractBoundarySurfaceExporter(connection, helper, this);
                else if (type == ConstructionInstallationExporter.class)
                    exporter = new ConstructionInstallationExporter(connection, helper, this);
                else if (type == UtilityNetworkElementExporter.class)
                    exporter = new UtilityNetworkElementExporter(connection, helper, this);
                else if (type == PublicLandExporter.class)
                    exporter = new PublicLandExporter(connection, helper, this);
            } else if (PublicTransitModuleExporter.class.isAssignableFrom(type)) {
                // public transit module
                if (type == org.citydb.ade.iur.exporter.urt.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urt.CityObjectGroupPropertiesExporter(connection, helper, this);
                else if (type == PublicTransitDataTypeExporter.class)
                    exporter = new PublicTransitDataTypeExporter(connection, helper, this);
                else if (type == DescriptionExporter.class)
                    exporter = new DescriptionExporter(connection, helper, this);
                else if (type == PointExporter.class)
                    exporter = new PointExporter(connection, helper, this);
                else if (type == RouteExporter.class)
                    exporter = new RouteExporter(connection, helper, this);
                else if (type == AgencyExporter.class)
                    exporter = new AgencyExporter(connection, helper, this);
                else if (type == StopExporter.class)
                    exporter = new StopExporter(connection, helper, this);
                else if (type == LevelExporter.class)
                    exporter = new LevelExporter(connection, helper, this);
                else if (type == TripExporter.class)
                    exporter = new TripExporter(connection, helper, this);
                else if (type == ShapeExporter.class)
                    exporter = new ShapeExporter(connection, helper, this);
                else if (type == OfficeExporter.class)
                    exporter = new OfficeExporter(connection, helper, this);
                else if (type == CalendarExporter.class)
                    exporter = new CalendarExporter(connection, helper, this);
                else if (type == CalendarDateExporter.class)
                    exporter = new CalendarDateExporter(connection, helper, this);
                else if (type == FareAttributeExporter.class)
                    exporter = new FareAttributeExporter(connection, helper, this);
                else if (type == PathwayExporter.class)
                    exporter = new PathwayExporter(connection, helper, this);
                else if (type == TranslationJPExporter.class)
                    exporter = new TranslationJPExporter(connection, helper, this);
                else if (type == AttributionExporter.class)
                    exporter = new AttributionExporter(connection, helper, this);
                else if (type == PublicTransitExporter.class)
                    exporter = new PublicTransitExporter(connection, helper, this);
            }

            if (exporter == null)
                throw new SQLException("Failed to build ADE exporter of type " + type.getName() + ".");

            exporters.put(type, exporter);
        }

        return type.cast(exporter);
    }
}
