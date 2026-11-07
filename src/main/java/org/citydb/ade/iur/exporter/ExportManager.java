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
import org.citygml4j.model.citygml.cityfurniture.CityFurniture;
import org.citygml4j.model.citygml.landuse.LandUse;
import org.citygml4j.model.citygml.transportation.TrafficArea;
import org.citygml4j.model.citygml.transportation.TransportationComplex;
import org.citygml4j.model.citygml.vegetation.AbstractVegetationObject;
import org.citygml4j.model.citygml.waterbody.WaterBody;
import org.citygml4j.ade.iur.model.urf.AbstractDistrictPlan;
import org.citygml4j.ade.iur.model.urf.AreaClassification;
import org.citygml4j.ade.iur.model.urf.Boundary;
import org.citygml4j.ade.iur.model.urf.CensusBlock;
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForReconstruction;
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForReconstructionAndRevitalization;
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForTsunamiDisasterPrevention;
import org.citygml4j.ade.iur.model.urf.CollectiveGovernmentAndPublicOfficeFacilities;
import org.citygml4j.ade.iur.model.urf.CollectiveHousingFacilities;
import org.citygml4j.ade.iur.model.urf.CollectiveUrbanDisasterPreventionFacilities;
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
import org.citygml4j.ade.iur.model.urf.Waterway;
import org.citygml4j.ade.iur.model.urf.ZonalDisasterPreventionFacility;
import org.citygml4j.ade.iur.model.urf.Zone;
import org.citygml4j.ade.iur.model.urg.NumberOfHouseholds;
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
        if (UrbanFunctionModule.v2_0.getModelPackageName().equals(object.getClass().getPackage().getName())) {
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
            else if (object instanceof Waterway)
                getExporter(WaterwayExporter.class).doExport((Waterway) object, objectId, objectType, projectionFilter);
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
            else if (object instanceof DevelopmentProject)
                getExporter(DevelopmentProjectExporter.class).doExport((DevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof SedimentDisasterProneArea)
                getExporter(SedimentDisasterProneAreaExporter.class).doExport((SedimentDisasterProneArea) object, objectId, objectType, projectionFilter);
            else if (object instanceof Zone)
                getExporter(ZoneExporter.class).doExport((Zone) object, objectId, objectType, projectionFilter);
            else if (object instanceof Boundary)
                getExporter(BoundaryExporter.class).doExport((Boundary) object, objectId, objectType, projectionFilter);
            else if (object instanceof ThreeDimensionalExtent)
                getExporter(ThreeDimensionalExtentExporter.class).doExport((ThreeDimensionalExtent) object, objectId, objectType, projectionFilter);
            else if (object instanceof CensusBlock)
                getExporter(CensusBlockExporter.class).doExport((CensusBlock) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterDamage)
                getExporter(DisasterDamageExporter.class).doExport((DisasterDamage) object, objectId, objectType, projectionFilter);
            else if (object instanceof Pollution)
                getExporter(PollutionExporter.class).doExport((Pollution) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterPreventionBase)
                getExporter(DisasterPreventionBaseExporter.class).doExport((DisasterPreventionBase) object, objectId, objectType, projectionFilter);
            else if (object instanceof Recreations)
                getExporter(RecreationsExporter.class).doExport((Recreations) object, objectId, objectType, projectionFilter);
            else if (object instanceof Urbanization)
                getExporter(UrbanizationExporter.class).doExport((Urbanization) object, objectId, objectType, projectionFilter);
            else if (object instanceof PublicTransitFacility)
                getExporter(PublicTransitFacilityExporter.class).doExport((PublicTransitFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof UrbanFunction)
                getExporter(UrbanFunctionExporter.class).doExport((UrbanFunction) object, objectId, objectType, projectionFilter);
        } else if (StatisticalGridModule.v1_4.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof GenericGridCell)
                getExporter(GenericGridCellExporter.class).doExport((GenericGridCell) object, objectId, objectType, projectionFilter);
            else if (object instanceof Households)
                getExporter(HouseholdsExporter.class).doExport((Households) object, objectId, objectType, projectionFilter);
            else if (object instanceof LandPrice)
                getExporter(LandPriceExporter.class).doExport((LandPrice) object, objectId, objectType, projectionFilter);
            else if (object instanceof LandUseDiversion)
                getExporter(LandUseDiversionExporter.class).doExport((LandUseDiversion) object, objectId, objectType, projectionFilter);
            else if (object instanceof OfficesAndEmployees)
                getExporter(OfficesAndEmployeesExporter.class).doExport((OfficesAndEmployees) object, objectId, objectType, projectionFilter);
            else if (object instanceof Population)
                getExporter(PopulationExporter.class).doExport((Population) object, objectId, objectType, projectionFilter);
            else if (object instanceof PublicTransitAccessibility)
                getExporter(PublicTransitAccessibilityExporter.class).doExport((PublicTransitAccessibility) object, objectId, objectType, projectionFilter);
        } else if (PublicTransitModule.v1_4.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof Agency)
                getExporter(AgencyExporter.class).doExport((Agency) object, objectId, objectType, projectionFilter);
            else if (object instanceof Attribution)
                getExporter(AttributionExporter.class).doExport((Attribution) object, objectId, objectType, projectionFilter);
            else if (object instanceof Calendar)
                getExporter(CalendarExporter.class).doExport((Calendar) object, objectId, objectType, projectionFilter);
            else if (object instanceof CalendarDate)
                getExporter(CalendarDateExporter.class).doExport((CalendarDate) object, objectId, objectType, projectionFilter);
            else if (object instanceof FareAttribute)
                getExporter(FareAttributeExporter.class).doExport((FareAttribute) object, objectId, objectType, projectionFilter);
            else if (object instanceof Level)
                getExporter(LevelExporter.class).doExport((Level) object, objectId, objectType, projectionFilter);
            else if (object instanceof Office)
                getExporter(OfficeExporter.class).doExport((Office) object, objectId, objectType, projectionFilter);
            else if (object instanceof Pathway)
                getExporter(PathwayExporter.class).doExport((Pathway) object, objectId, objectType, projectionFilter);
            else if (object instanceof Route)
                getExporter(RouteExporter.class).doExport((Route) object, objectId, objectType, projectionFilter);
            else if (object instanceof Shape)
                getExporter(ShapeExporter.class).doExport((Shape) object, objectId, objectType, projectionFilter);
            else if (object instanceof Stop)
                getExporter(StopExporter.class).doExport((Stop) object, objectId, objectType, projectionFilter);
            else if (object instanceof TranslationJP)
                getExporter(TranslationJPExporter.class).doExport((TranslationJP) object, objectId, objectType, projectionFilter);
            else if (object instanceof Trip)
                getExporter(TripExporter.class).doExport((Trip) object, objectId, objectType, projectionFilter);
        }
    }

    @Override
    public void exportGenericApplicationProperties(String adeHookTable, AbstractFeature parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BUILDING)) && parent instanceof AbstractBuilding)
            getExporter(BuildingPropertiesExporter.class).doExport((AbstractBuilding) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.LAND_USE)) && parent instanceof LandUse)
            getExporter(LandUsePropertiesExporter.class).doExport((LandUse) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TRANSPORTATION_COMPLEX)) && parent instanceof TransportationComplex)
            getExporter(TransportationComplexPropertiesExporter.class).doExport((TransportationComplex) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TRAFFIC_AREA)) && parent instanceof TrafficArea)
            getExporter(TrafficAreaPropertiesExporter.class).doExport((TrafficArea) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITY_FURNITURE)) && parent instanceof CityFurniture)
            getExporter(CityFurniturePropertiesExporter.class).doExport((CityFurniture) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECT)) && parent instanceof AbstractVegetationObject)
            getExporter(VegetationObjectPropertiesExporter.class).doExport((AbstractVegetationObject) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.WATERBODY)) && parent instanceof WaterBody)
            getExporter(WaterBodyPropertiesExporter.class).doExport((WaterBody) parent, parentId, parentType, projectionFilter);
        if (parent instanceof CityObjectGroup) {
            if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP)))
                getExporter(org.citydb.ade.iur.exporter.urg.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
            else if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_1)))
                getExporter(org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
            else if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_3)))
                getExporter(org.citydb.ade.iur.exporter.urt.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
        }
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
                if (type == UrbanRoadAttributeExporter.class)
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
                else if (type == NumberOfHouseholdsExporter.class)
                    exporter = new NumberOfHouseholdsExporter(connection, helper, this);
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
                else if (type == WaterwayExporter.class)
                    exporter = new WaterwayExporter(connection, helper, this);
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
                else if (type == DevelopmentProjectExporter.class)
                    exporter = new DevelopmentProjectExporter(connection, helper, this);
                else if (type == SedimentDisasterProneAreaExporter.class)
                    exporter = new SedimentDisasterProneAreaExporter(connection, helper, this);
                else if (type == ZoneExporter.class)
                    exporter = new ZoneExporter(connection, helper, this);
                else if (type == BoundaryExporter.class)
                    exporter = new BoundaryExporter(connection, helper, this);
                else if (type == ThreeDimensionalExtentExporter.class)
                    exporter = new ThreeDimensionalExtentExporter(connection, helper, this);
                else if (type == CensusBlockExporter.class)
                    exporter = new CensusBlockExporter(connection, helper, this);
                else if (type == DisasterDamageExporter.class)
                    exporter = new DisasterDamageExporter(connection, helper, this);
                else if (type == PollutionExporter.class)
                    exporter = new PollutionExporter(connection, helper, this);
                else if (type == DisasterPreventionBaseExporter.class)
                    exporter = new DisasterPreventionBaseExporter(connection, helper, this);
                else if (type == RecreationsExporter.class)
                    exporter = new RecreationsExporter(connection, helper, this);
                else if (type == UrbanizationExporter.class)
                    exporter = new UrbanizationExporter(connection, helper, this);
                else if (type == PublicTransitFacilityExporter.class)
                    exporter = new PublicTransitFacilityExporter(connection, helper, this);
                else if (type == UrbanFunctionExporter.class)
                    exporter = new UrbanFunctionExporter(connection, helper, this);
            } else if (StatisticalGridModuleExporter.class.isAssignableFrom(type)) {
                // statistical grid module
                if (type == StatisticalGridExporter.class)
                    exporter = new StatisticalGridExporter(connection, helper, this);
                else if (type == GenericGridCellExporter.class)
                    exporter = new GenericGridCellExporter(connection, helper, this);
                else if (type == HouseholdsExporter.class)
                    exporter = new HouseholdsExporter(connection, helper, this);
                else if (type == LandPriceExporter.class)
                    exporter = new LandPriceExporter(connection, helper, this);
                else if (type == LandUseDiversionExporter.class)
                    exporter = new LandUseDiversionExporter(connection, helper, this);
                else if (type == OfficesAndEmployeesExporter.class)
                    exporter = new OfficesAndEmployeesExporter(connection, helper, this);
                else if (type == PopulationExporter.class)
                    exporter = new PopulationExporter(connection, helper, this);
                else if (type == PublicTransitAccessibilityExporter.class)
                    exporter = new PublicTransitAccessibilityExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.urg.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urg.CityObjectGroupPropertiesExporter(connection, helper, this);
            } else if (UrbanObjectModuleExporter.class.isAssignableFrom(type)) {
                // urban object module
                if (type == BuildingPropertiesExporter.class)
                    exporter = new BuildingPropertiesExporter(connection, helper, this);
                else if (type == LandUsePropertiesExporter.class)
                    exporter = new LandUsePropertiesExporter(connection, helper, this);
                else if (type == TransportationComplexPropertiesExporter.class)
                    exporter = new TransportationComplexPropertiesExporter(connection, helper, this);
                else if (type == TrafficAreaPropertiesExporter.class)
                    exporter = new TrafficAreaPropertiesExporter(connection, helper, this);
                else if (type == CityFurniturePropertiesExporter.class)
                    exporter = new CityFurniturePropertiesExporter(connection, helper, this);
                else if (type == VegetationObjectPropertiesExporter.class)
                    exporter = new VegetationObjectPropertiesExporter(connection, helper, this);
                else if (type == WaterBodyPropertiesExporter.class)
                    exporter = new WaterBodyPropertiesExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter(connection, helper, this);
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
                else if (type == RoadDataQualityAttributeExporter.class)
                    exporter = new RoadDataQualityAttributeExporter(connection, helper, this);
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
            } else if (PublicTransitModuleExporter.class.isAssignableFrom(type)) {
                // public transit module
                if (type == AgencyExporter.class)
                    exporter = new AgencyExporter(connection, helper, this);
                else if (type == AttributionExporter.class)
                    exporter = new AttributionExporter(connection, helper, this);
                else if (type == CalendarExporter.class)
                    exporter = new CalendarExporter(connection, helper, this);
                else if (type == CalendarDateExporter.class)
                    exporter = new CalendarDateExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.urt.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urt.CityObjectGroupPropertiesExporter(connection, helper, this);
                else if (type == FareAttributeExporter.class)
                    exporter = new FareAttributeExporter(connection, helper, this);
                else if (type == FareRuleExporter.class)
                    exporter = new FareRuleExporter(connection, helper, this);
                else if (type == FeedInfoExporter.class)
                    exporter = new FeedInfoExporter(connection, helper, this);
                else if (type == FrequencyExporter.class)
                    exporter = new FrequencyExporter(connection, helper, this);
                else if (type == LevelExporter.class)
                    exporter = new LevelExporter(connection, helper, this);
                else if (type == OfficeExporter.class)
                    exporter = new OfficeExporter(connection, helper, this);
                else if (type == PathwayExporter.class)
                    exporter = new PathwayExporter(connection, helper, this);
                else if (type == PublicTransitExporter.class)
                    exporter = new PublicTransitExporter(connection, helper, this);
                else if (type == RouteExporter.class)
                    exporter = new RouteExporter(connection, helper, this);
                else if (type == ShapeExporter.class)
                    exporter = new ShapeExporter(connection, helper, this);
                else if (type == StopExporter.class)
                    exporter = new StopExporter(connection, helper, this);
                else if (type == StopTimeExporter.class)
                    exporter = new StopTimeExporter(connection, helper, this);
                else if (type == TransferExporter.class)
                    exporter = new TransferExporter(connection, helper, this);
                else if (type == TranslationExporter.class)
                    exporter = new TranslationExporter(connection, helper, this);
                else if (type == TranslationJPExporter.class)
                    exporter = new TranslationJPExporter(connection, helper, this);
                else if (type == TripExporter.class)
                    exporter = new TripExporter(connection, helper, this);
            }

            if (exporter == null)
                throw new SQLException("Failed to build ADE exporter of type " + type.getName() + ".");

            exporters.put(type, exporter);
        }

        return type.cast(exporter);
    }
}
