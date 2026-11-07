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

package org.citydb.ade.iur.importer;

import org.citydb.ade.iur.importer.urf.*;
import org.citydb.ade.iur.importer.urg.*;
import org.citydb.ade.iur.importer.uro.*;
import org.citydb.ade.iur.importer.urt.*;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.ADEExtension;
import org.citydb.core.ade.importer.*;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.importer.CityGMLImportException;
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
import org.citygml4j.model.citygml.relief.ReliefFeature;
import org.citygml4j.model.citygml.transportation.TrafficArea;
import org.citygml4j.model.citygml.transportation.TransportationComplex;
import org.citygml4j.model.citygml.tunnel.AbstractTunnel;
import org.citygml4j.model.citygml.vegetation.AbstractVegetationObject;
import org.citygml4j.model.citygml.waterbody.WaterBody;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;
import org.citygml4j.ade.iur.model.uro.Appurtenance;
import org.citygml4j.ade.iur.model.uro.Cable;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.ade.iur.model.uro.OtherConstruction;
import org.citygml4j.ade.iur.model.uro.Pipe;
import org.citygml4j.ade.iur.model.uro.PointCloud;
import org.citygml4j.ade.iur.model.uro.PublicLand;
import org.citygml4j.ade.iur.model.uro.SewerPipe;
import org.citygml4j.ade.iur.model.uro.UndergroundBuilding;
import org.citygml4j.ade.iur.model.uro.UtilityLink;
import org.citygml4j.ade.iur.model.uro.UtilityNetworkElement;
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
import org.citygml4j.ade.iur.model.urf.Target;
import org.citygml4j.ade.iur.model.urf.ThreeDimensionalExtent;
import org.citygml4j.ade.iur.model.urf.TrafficFacility;
import org.citygml4j.ade.iur.model.urf.TreatmentFacility;
import org.citygml4j.ade.iur.model.urf.TreePlantingDistrict;
import org.citygml4j.ade.iur.model.urf.UrbanDevelopmentProject;
import org.citygml4j.ade.iur.model.urf.UrbanDisasterRecoveryPromotionArea;
import org.citygml4j.ade.iur.model.urf.UrbanFacility;
import org.citygml4j.ade.iur.model.urf.UrbanFacilityStatus;
import org.citygml4j.ade.iur.model.urf.UrbanFacilityStipulatedByCabinetOrder;
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
import org.citydb.ade.iur.importer.urg.LandPriceImporter;
import org.citydb.ade.iur.importer.urg.LandUseDiversionImporter;
import org.citygml4j.ade.iur.model.urg.LandPrice;
import org.citygml4j.ade.iur.model.urf.UrbanFunction;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ImportManager implements ADEImportManager {
    private final ADEExtension adeExtension;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final Map<Class<? extends ADEImporter>, ADEImporter> importers;

    private Connection connection;
    private CityGMLImportHelper helper;
    private Transformer transformer;

    public ImportManager(ADEExtension adeExtension, ObjectMapper objectMapper, SchemaMapper schemaMapper) {
        this.adeExtension = adeExtension;
        this.objectMapper = objectMapper;
        this.schemaMapper = schemaMapper;
        importers = new HashMap<>();
    }

    @Override
    public void init(Connection connection, CityGMLImportHelper helper) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
    }

    @Override
    public void importObject(ADEModelObject object, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        if (UrbanFunctionModule.v3_2.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof CollectiveFacilitiesForReconstruction)
                getImporter(CollectiveFacilitiesForReconstructionImporter.class).doImport((CollectiveFacilitiesForReconstruction) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveFacilitiesForReconstructionAndRevitalization)
                getImporter(CollectiveFacilitiesForReconstructionAndRevitalizationImporter.class).doImport((CollectiveFacilitiesForReconstructionAndRevitalization) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveFacilitiesForTsunamiDisasterPrevention)
                getImporter(CollectiveFacilitiesForTsunamiDisasterPreventionImporter.class).doImport((CollectiveFacilitiesForTsunamiDisasterPrevention) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveGovernmentAndPublicOfficeFacilities)
                getImporter(CollectiveGovernmentAndPublicOfficeFacilitiesImporter.class).doImport((CollectiveGovernmentAndPublicOfficeFacilities) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveHousingFacilities)
                getImporter(CollectiveHousingFacilitiesImporter.class).doImport((CollectiveHousingFacilities) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveUrbanDisasterPreventionFacilities)
                getImporter(CollectiveUrbanDisasterPreventionFacilitiesImporter.class).doImport((CollectiveUrbanDisasterPreventionFacilities) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterPreventionBlockImprovementProject)
                getImporter(DisasterPreventionBlockImprovementProjectImporter.class).doImport((DisasterPreventionBlockImprovementProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterPreventionBlockImprovementZonePlan)
                getImporter(DisasterPreventionBlockImprovementZonePlanImporter.class).doImport((DisasterPreventionBlockImprovementZonePlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistributionBusinessPark)
                getImporter(DistributionBusinessParkImporter.class).doImport((DistributionBusinessPark) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistributionBusinessZone)
                getImporter(DistributionBusinessZoneImporter.class).doImport((DistributionBusinessZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistrictPlan)
                getImporter(DistrictPlanImporter.class).doImport((DistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof ExceptionalFloorAreaRateDistrict)
                getImporter(ExceptionalFloorAreaRateDistrictImporter.class).doImport((ExceptionalFloorAreaRateDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanFacilityStipulatedByCabinetOrder)
                getImporter(UrbanFacilityStipulatedByCabinetOrderImporter.class).doImport((UrbanFacilityStipulatedByCabinetOrder) object, objectId, objectType, foreignKeys);
            else if (object instanceof HeightControlDistrict)
                getImporter(HeightControlDistrictImporter.class).doImport((HeightControlDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof HighLevelUseDistrict)
                getImporter(HighLevelUseDistrictImporter.class).doImport((HighLevelUseDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof HighRiseResidentialAttractionDistrict)
                getImporter(HighRiseResidentialAttractionDistrictImporter.class).doImport((HighRiseResidentialAttractionDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof HistoricSceneryMaintenanceAndImprovementDistrictPlan)
                getImporter(HistoricSceneryMaintenanceAndImprovementDistrictPlanImporter.class).doImport((HistoricSceneryMaintenanceAndImprovementDistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof IndustrialParkDevelopmentProject)
                getImporter(IndustrialParkDevelopmentProjectImporter.class).doImport((IndustrialParkDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof LandReadjustmentProject)
                getImporter(LandReadjustmentProjectImporter.class).doImport((LandReadjustmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof LandscapeZone)
                getImporter(LandscapeZoneImporter.class).doImport((LandscapeZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof NewHousingAndUrbanDevelopmentProject)
                getImporter(NewHousingAndUrbanDevelopmentProjectImporter.class).doImport((NewHousingAndUrbanDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof NewUrbanInfrastructureProject)
                getImporter(NewUrbanInfrastructureProjectImporter.class).doImport((NewUrbanInfrastructureProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof OpenSpaceForPublicUse)
                getImporter(OpenSpaceForPublicUseImporter.class).doImport((OpenSpaceForPublicUse) object, objectId, objectType, foreignKeys);
            else if (object instanceof PortZone)
                getImporter(PortZoneImporter.class).doImport((PortZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof ProductiveGreenZone)
                getImporter(ProductiveGreenZoneImporter.class).doImport((ProductiveGreenZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof ResidentialBlockConstructionProject)
                getImporter(ResidentialBlockConstructionProjectImporter.class).doImport((ResidentialBlockConstructionProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof ResidentialEnvironmentImprovementDistrict)
                getImporter(ResidentialEnvironmentImprovementDistrictImporter.class).doImport((ResidentialEnvironmentImprovementDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof RoadsideDistrictImprovementPlan)
                getImporter(RoadsideDistrictImprovementPlanImporter.class).doImport((RoadsideDistrictImprovementPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof RoadsideDistrictPlan)
                getImporter(RoadsideDistrictPlanImporter.class).doImport((RoadsideDistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof RuralDistrictImprovementPlan)
                getImporter(RuralDistrictImprovementPlanImporter.class).doImport((RuralDistrictImprovementPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof ScenicDistrict)
                getImporter(ScenicDistrictImporter.class).doImport((ScenicDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialGreenSpaceConservationDistrict)
                getImporter(SpecialGreenSpaceConservationDistrictImporter.class).doImport((SpecialGreenSpaceConservationDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUrbanRenaissanceDistrict)
                getImporter(SpecialUrbanRenaissanceDistrictImporter.class).doImport((SpecialUrbanRenaissanceDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUseAttractionDistrict)
                getImporter(SpecialUseAttractionDistrictImporter.class).doImport((SpecialUseAttractionDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUseDistrict)
                getImporter(SpecialUseDistrictImporter.class).doImport((SpecialUseDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUseRestrictionDistrict)
                getImporter(SpecialUseRestrictionDistrictImporter.class).doImport((SpecialUseRestrictionDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecifiedBlock)
                getImporter(SpecifiedBlockImporter.class).doImport((SpecifiedBlock) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecifiedDisasterPreventionBlockImprovementZone)
                getImporter(SpecifiedDisasterPreventionBlockImprovementZoneImporter.class).doImport((SpecifiedDisasterPreventionBlockImprovementZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecifiedUrgentUrbanRenewalArea)
                getImporter(SpecifiedUrgentUrbanRenewalAreaImporter.class).doImport((SpecifiedUrgentUrbanRenewalArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof SupplyFacility)
                getImporter(SupplyFacilityImporter.class).doImport((SupplyFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof TrafficFacility)
                getImporter(TrafficFacilityImporter.class).doImport((TrafficFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof TreatmentFacility)
                getImporter(TreatmentFacilityImporter.class).doImport((TreatmentFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof TreePlantingDistrict)
                getImporter(TreePlantingDistrictImporter.class).doImport((TreePlantingDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanRedevelopmentProject)
                getImporter(UrbanRedevelopmentProjectImporter.class).doImport((UrbanRedevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanRedevelopmentPromotionArea)
                getImporter(UrbanRedevelopmentPromotionAreaImporter.class).doImport((UrbanRedevelopmentPromotionArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanRenewalProject)
                getImporter(UrbanRenewalProjectImporter.class).doImport((UrbanRenewalProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof UseDistrict)
                getImporter(UseDistrictImporter.class).doImport((UseDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof org.citygml4j.ade.iur.model.urf.Waterway)
                getImporter(org.citydb.ade.iur.importer.urf.WaterwayImporter.class).doImport((org.citygml4j.ade.iur.model.urf.Waterway) object, objectId, objectType, foreignKeys);
            else if (object instanceof Agreement)
                getImporter(AgreementImporter.class).doImport((Agreement) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistrictsAndZones)
                getImporter(DistrictsAndZonesImporter.class).doImport((DistrictsAndZones) object, objectId, objectType, foreignKeys);
            else if (object instanceof AreaClassification)
                getImporter(AreaClassificationImporter.class).doImport((AreaClassification) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanFacility)
                getImporter(UrbanFacilityImporter.class).doImport((UrbanFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanDevelopmentProject)
                getImporter(UrbanDevelopmentProjectImporter.class).doImport((UrbanDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof AbstractDistrictPlan)
                getImporter(AbstractDistrictPlanImporter.class).doImport((AbstractDistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof District)
                getImporter(DistrictImporter.class).doImport((District) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistrictDevelopmentPlan)
                getImporter(DistrictDevelopmentPlanImporter.class).doImport((DistrictDevelopmentPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof GlobalHubCityDevelopmentProject)
                getImporter(GlobalHubCityDevelopmentProjectImporter.class).doImport((GlobalHubCityDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof ProjectPromotionArea)
                getImporter(ProjectPromotionAreaImporter.class).doImport((ProjectPromotionArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof PrivateUrbanRenewalProjectPlan)
                getImporter(PrivateUrbanRenewalProjectPlanImporter.class).doImport((PrivateUrbanRenewalProjectPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof QuasiUrbanPlanningArea)
                getImporter(QuasiUrbanPlanningAreaImporter.class).doImport((QuasiUrbanPlanningArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof ScheduledAreaForUrbanDevelopmentProject)
                getImporter(ScheduledAreaForUrbanDevelopmentProjectImporter.class).doImport((ScheduledAreaForUrbanDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof SedimentDisasterProneArea)
                getImporter(SedimentDisasterProneAreaImporter.class).doImport((SedimentDisasterProneArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrgentUrbanRenewalArea)
                getImporter(UrgentUrbanRenewalAreaImporter.class).doImport((UrgentUrbanRenewalArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanDisasterRecoveryPromotionArea)
                getImporter(UrbanDisasterRecoveryPromotionAreaImporter.class).doImport((UrbanDisasterRecoveryPromotionArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanPlanningArea)
                getImporter(UrbanPlanningAreaImporter.class).doImport((UrbanPlanningArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof ZonalDisasterPreventionFacility)
                getImporter(ZonalDisasterPreventionFacilityImporter.class).doImport((ZonalDisasterPreventionFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof Administration)
                getImporter(AdministrationImporter.class).doImport((Administration) object, objectId, objectType, foreignKeys);
            else if (object instanceof Zone)
                getImporter(ZoneImporter.class).doImport((Zone) object, objectId, objectType, foreignKeys);
            else if (object instanceof CensusBlock)
                getImporter(CensusBlockImporter.class).doImport((CensusBlock) object, objectId, objectType, foreignKeys);
            else if (object instanceof DID)
                getImporter(DIDImporter.class).doImport((DID) object, objectId, objectType, foreignKeys);
            else if (object instanceof DevelopmentProject)
                getImporter(DevelopmentProjectImporter.class).doImport((DevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterDamage)
                getImporter(DisasterDamageImporter.class).doImport((DisasterDamage) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterPreventionBase)
                getImporter(DisasterPreventionBaseImporter.class).doImport((DisasterPreventionBase) object, objectId, objectType, foreignKeys);
            else if (object instanceof org.citygml4j.ade.iur.model.urf.LandPrice)
                getImporter(org.citydb.ade.iur.importer.urf.LandPriceImporter.class).doImport((org.citygml4j.ade.iur.model.urf.LandPrice) object, objectId, objectType, foreignKeys);
            else if (object instanceof org.citygml4j.ade.iur.model.urf.LandUseDiversion)
                getImporter(org.citydb.ade.iur.importer.urf.LandUseDiversionImporter.class).doImport((org.citygml4j.ade.iur.model.urf.LandUseDiversion) object, objectId, objectType, foreignKeys);
            else if (object instanceof NewHousing)
                getImporter(NewHousingImporter.class).doImport((NewHousing) object, objectId, objectType, foreignKeys);
            else if (object instanceof Pollution)
                getImporter(PollutionImporter.class).doImport((Pollution) object, objectId, objectType, foreignKeys);
            else if (object instanceof PublicTransitFacility)
                getImporter(PublicTransitFacilityImporter.class).doImport((PublicTransitFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof Recreations)
                getImporter(RecreationsImporter.class).doImport((Recreations) object, objectId, objectType, foreignKeys);
            else if (object instanceof ScenicResource)
                getImporter(ScenicResourceImporter.class).doImport((ScenicResource) object, objectId, objectType, foreignKeys);
            else if (object instanceof ThreeDimensionalExtent)
                getImporter(ThreeDimensionalExtentImporter.class).doImport((ThreeDimensionalExtent) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanFacilityStatus)
                getImporter(UrbanFacilityStatusImporter.class).doImport((UrbanFacilityStatus) object, objectId, objectType, foreignKeys);
            else if (object instanceof Urbanization)
                getImporter(UrbanizationImporter.class).doImport((Urbanization) object, objectId, objectType, foreignKeys);
            else if (object instanceof WeatherObservation)
                getImporter(WeatherObservationImporter.class).doImport((WeatherObservation) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanFunction)
                getImporter(UrbanFunctionImporter.class).doImport((UrbanFunction) object, objectId, objectType, foreignKeys);
            else if (object instanceof Target)
                getImporter(TargetImporter.class).doImport((Target) object, objectId, objectType, foreignKeys);
        } else if (StatisticalGridModule.v1_4.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof Households)
                getImporter(HouseholdsImporter.class).doImport((Households) object, objectId, objectType, foreignKeys);
            else if (object instanceof LandPrice)
                getImporter(LandPriceImporter.class).doImport((LandPrice) object, objectId, objectType, foreignKeys);
            else if (object instanceof LandUseDiversion)
                getImporter(LandUseDiversionImporter.class).doImport((LandUseDiversion) object, objectId, objectType, foreignKeys);
            else if (object instanceof OfficesAndEmployees)
                getImporter(OfficesAndEmployeesImporter.class).doImport((OfficesAndEmployees) object, objectId, objectType, foreignKeys);
            else if (object instanceof Population)
                getImporter(PopulationImporter.class).doImport((Population) object, objectId, objectType, foreignKeys);
            else if (object instanceof StatisticalGrid)
                getImporter(StatisticalGridImporter.class).doImport((StatisticalGrid) object, objectId, objectType, foreignKeys);
        } else if (PublicTransitModule.v1_4.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof Agency)
                getImporter(AgencyImporter.class).doImport((Agency) object, objectId, objectType, foreignKeys);
            else if (object instanceof Attribution)
                getImporter(AttributionImporter.class).doImport((Attribution) object, objectId, objectType, foreignKeys);
            else if (object instanceof Calendar)
                getImporter(CalendarImporter.class).doImport((Calendar) object, objectId, objectType, foreignKeys);
            else if (object instanceof CalendarDate)
                getImporter(CalendarDateImporter.class).doImport((CalendarDate) object, objectId, objectType, foreignKeys);
            else if (object instanceof FareAttribute)
                getImporter(FareAttributeImporter.class).doImport((FareAttribute) object, objectId, objectType, foreignKeys);
            else if (object instanceof Office)
                getImporter(OfficeImporter.class).doImport((Office) object, objectId, objectType, foreignKeys);
            else if (object instanceof Pathway)
                getImporter(PathwayImporter.class).doImport((Pathway) object, objectId, objectType, foreignKeys);
            else if (object instanceof Route)
                getImporter(RouteImporter.class).doImport((Route) object, objectId, objectType, foreignKeys);
            else if (object instanceof Stop)
                getImporter(StopImporter.class).doImport((Stop) object, objectId, objectType, foreignKeys);
            else if (object instanceof TranslationJP)
                getImporter(TranslationJPImporter.class).doImport((TranslationJP) object, objectId, objectType, foreignKeys);
            else if (object instanceof Trip)
                getImporter(TripImporter.class).doImport((Trip) object, objectId, objectType, foreignKeys);
            else if (object instanceof PublicTransit)
                getImporter(PublicTransitImporter.class).doImport((PublicTransit) object, objectId, objectType, foreignKeys);
        } else if (UrbanObjectModule.v3_2.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof SewerPipe)
                getImporter(SewerPipeImporter.class).doImport((SewerPipe) object, objectId, objectType, foreignKeys);
            else if (object instanceof Cable)
                getImporter(CableImporter.class).doImport((Cable) object, objectId, objectType, foreignKeys);
            else if (object instanceof Pipe)
                getImporter(PipeImporter.class).doImport((Pipe) object, objectId, objectType, foreignKeys);
            else if (object instanceof Appurtenance)
                getImporter(AppurtenanceImporter.class).doImport((Appurtenance) object, objectId, objectType, foreignKeys);
            else if (object instanceof UtilityLink)
                getImporter(UtilityLinkImporter.class).doImport((UtilityLink) object, objectId, objectType, foreignKeys);
            else if (object instanceof UtilityNodeContainer)
                getImporter(UtilityNodeContainerImporter.class).doImport((UtilityNodeContainer) object, objectId, objectType, foreignKeys);
            else if (object instanceof OtherConstruction)
                getImporter(OtherConstructionImporter.class).doImport((OtherConstruction) object, objectId, objectType, foreignKeys);
            else if (object instanceof PointCloud)
                getImporter(PointCloudImporter.class).doImport((PointCloud) object, objectId, objectType, foreignKeys);
            else if (object instanceof org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface)
                getImporter(AbstractBoundarySurfaceImporter.class).doImport((org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface) object, objectId, objectType, foreignKeys);
            else if (object instanceof ConstructionInstallation)
                getImporter(ConstructionInstallationImporter.class).doImport((ConstructionInstallation) object, objectId, objectType, foreignKeys);
            else if (object instanceof PublicLand)
                getImporter(PublicLandImporter.class).doImport((PublicLand) object, objectId, objectType, foreignKeys);
            else if (object instanceof UndergroundBuilding)
                getImporter(UndergroundBuildingImporter.class).doImport((UndergroundBuilding) object, objectId, objectType, foreignKeys);
            else if (object instanceof org.citygml4j.ade.iur.model.uro.Waterway)
                getImporter(org.citydb.ade.iur.importer.uro.WaterwayImporter.class).doImport((org.citygml4j.ade.iur.model.uro.Waterway) object, objectId, objectType, foreignKeys);
        }
    }

    @Override
    public void importGenericApplicationProperties(ADEPropertyCollection properties, AbstractFeature parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        if (parent instanceof AbstractBuilding)
            getImporter(BuildingPropertiesImporter.class).doImport(properties, (AbstractBuilding) parent, parentId, parentType);
        if (parent instanceof AbstractBridge)
            getImporter(BridgePropertiesImporter.class).doImport(properties, (AbstractBridge) parent, parentId, parentType);
        if (parent instanceof CityFurniture)
            getImporter(CityFurniturePropertiesImporter.class).doImport(properties, (CityFurniture) parent, parentId, parentType);
        if (parent instanceof ReliefFeature)
            getImporter(ReliefFeaturePropertiesImporter.class).doImport(properties, (ReliefFeature) parent, parentId, parentType);
        if (parent instanceof AbstractReliefComponent)
            getImporter(ReliefComponentPropertiesImporter.class).doImport(properties, (AbstractReliefComponent) parent, parentId, parentType);
        if (parent instanceof WaterBody)
            getImporter(WaterBodyPropertiesImporter.class).doImport(properties, (WaterBody) parent, parentId, parentType);
        if (parent instanceof org.citygml4j.model.citygml.building.AbstractBoundarySurface)
            getImporter(BoundarySurfacePropertiesImporter.class).doImport(properties, (org.citygml4j.model.citygml.building.AbstractBoundarySurface) parent, parentId, parentType);
        if (parent instanceof BuildingFurniture)
            getImporter(BuildingFurniturePropertiesImporter.class).doImport(properties, (BuildingFurniture) parent, parentId, parentType);
        if (parent instanceof BuildingInstallation || parent instanceof IntBuildingInstallation)
            getImporter(BuildingInstallationPropertiesImporter.class).doImport(properties, (AbstractCityObject) parent, parentId, parentType);
        if (parent instanceof CityObjectGroup) {
            if (properties.containsOneOf(org.citygml4j.ade.iur.model.urg.FiscalYearOfPublicationProperty.class,
                    org.citygml4j.ade.iur.model.urg.LanguageProperty.class)) {
                getImporter(org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter.class).doImport(properties, (CityObjectGroup) parent, parentId, parentType);
            }
            if (properties.containsOneOf(org.citygml4j.ade.iur.model.uro.IfcBuildingStoreyAttributePropertyElement.class,
                    org.citygml4j.ade.iur.model.uro.IndoorStoreyAttributePropertyElement.class,
                    org.citygml4j.ade.iur.model.uro.FiscalYearOfPublicationProperty.class,
                    org.citygml4j.ade.iur.model.uro.LanguageProperty.class)) {
                getImporter(org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter.class).doImport(properties, (CityObjectGroup) parent, parentId, parentType);
            }
            if (properties.contains(DataTypeProperty.class))
                getImporter(org.citydb.ade.iur.importer.urt.CityObjectGroupPropertiesImporter.class).doImport(properties, (CityObjectGroup) parent, parentId, parentType);
        }
        if (parent instanceof LandUse)
            getImporter(LandUsePropertiesImporter.class).doImport(properties, (LandUse) parent, parentId, parentType);
        if (parent instanceof org.citygml4j.model.citygml.building.AbstractOpening)
            getImporter(OpeningPropertiesImporter.class).doImport(properties, (org.citygml4j.model.citygml.building.AbstractOpening) parent, parentId, parentType);
        if (parent instanceof Room)
            getImporter(RoomPropertiesImporter.class).doImport(properties, (Room) parent, parentId, parentType);
        if (parent instanceof TransportationComplex)
            getImporter(TransportationComplexPropertiesImporter.class).doImport(properties, (TransportationComplex) parent, parentId, parentType);
        if (parent instanceof TrafficArea)
            getImporter(TrafficAreaPropertiesImporter.class).doImport(properties, (TrafficArea) parent, parentId, parentType);
        if (parent instanceof AbstractTunnel)
            getImporter(TunnelPropertiesImporter.class).doImport(properties, (AbstractTunnel) parent, parentId, parentType);
        if (parent instanceof AbstractVegetationObject)
            getImporter(VegetationObjectPropertiesImporter.class).doImport(properties, (AbstractVegetationObject) parent, parentId, parentType);
    }

    @Override
    public void executeBatch(String tableName) throws CityGMLImportException, SQLException {
        ADETable adeTable = schemaMapper.fromTableName(tableName);
        if (adeTable != null) {
            ADEImporter importer = importers.get(adeTable.getImporterClass());
            if (importer != null)
                importer.executeBatch();
        } else
            throw new CityGMLImportException("The table " + tableName + " is not managed by the ADE extension for '" + adeExtension.getMetadata().getIdentifier() + "'.");
    }

    @Override
    public void close() throws CityGMLImportException, SQLException {
        for (ADEImporter importer : importers.values())
            importer.close();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public SchemaMapper getSchemaMapper() {
        return schemaMapper;
    }

    public String marshal(Node node) throws TransformerException {
        if (transformer == null) {
            SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
            transformer = factory.newTransformer();
        }

        StringWriter writer = new StringWriter();
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(node), new StreamResult(writer));
        transformer.reset();

        return writer.toString();
    }

    public <T extends ADEImporter> T getImporter(Class<T> type) throws CityGMLImportException, SQLException {
        ADEImporter importer = importers.get(type);

        if (importer == null) {
            if (UrbanFunctionModuleImporter.class.isAssignableFrom(type)) {
                // urban function module
                if (type == BoundaryImporter.class)
                    importer = new BoundaryImporter(connection, helper, this);
                else if (type == UrbanParkAttributeImporter.class)
                    importer = new UrbanParkAttributeImporter(connection, helper, this);
                else if (type == UrbanParkAttributeToBreakdownOfNominalAreaImporter.class)
                    importer = new UrbanParkAttributeToBreakdownOfNominalAreaImporter(connection, helper, this);
                else if (type == BreakdownOfNominalAreaImporter.class)
                    importer = new BreakdownOfNominalAreaImporter(connection, helper, this);
                else if (type == PopulationTypeImporter.class)
                    importer = new PopulationTypeImporter(connection, helper, this);
                else if (type == PopulationTypeToPopulationByAgeAndSexTypeImporter.class)
                    importer = new PopulationTypeToPopulationByAgeAndSexTypeImporter(connection, helper, this);
                else if (type == PopulationByAgeAndSexTypeImporter.class)
                    importer = new PopulationByAgeAndSexTypeImporter(connection, helper, this);
                else if (type == DaytimePopulationTypeImporter.class)
                    importer = new DaytimePopulationTypeImporter(connection, helper, this);
                else if (type == EconomicActivityTypeImporter.class)
                    importer = new EconomicActivityTypeImporter(connection, helper, this);
                else if (type == EconomicActivitySumTypeImporter.class)
                    importer = new EconomicActivitySumTypeImporter(connection, helper, this);
                else if (type == ResidentialDevelopmentTypeImporter.class)
                    importer = new ResidentialDevelopmentTypeImporter(connection, helper, this);
                else if (type == UrbanDevelopmentProjectTypeImporter.class)
                    importer = new UrbanDevelopmentProjectTypeImporter(connection, helper, this);
                else if (type == AgriculturalDevelopmentProjectTypeImporter.class)
                    importer = new AgriculturalDevelopmentProjectTypeImporter(connection, helper, this);
                else if (type == ValueByCodesImporter.class)
                    importer = new ValueByCodesImporter(connection, helper, this);
                else if (type == FarmlandConversionTypeImporter.class)
                    importer = new FarmlandConversionTypeImporter(connection, helper, this);
                else if (type == ForestConversionTypeImporter.class)
                    importer = new ForestConversionTypeImporter(connection, helper, this);
                else if (type == ParkAttributeImporter.class)
                    importer = new ParkAttributeImporter(connection, helper, this);
                else if (type == ObservationPointImporter.class)
                    importer = new ObservationPointImporter(connection, helper, this);
                else if (type == WaterWorksAttributeImporter.class)
                    importer = new WaterWorksAttributeImporter(connection, helper, this);
                else if (type == UrbanRoadAttributeImporter.class)
                    importer = new UrbanRoadAttributeImporter(connection, helper, this);
                else if (type == UrbanRoadAttributeToStructureDetailsImporter.class)
                    importer = new UrbanRoadAttributeToStructureDetailsImporter(connection, helper, this);
                else if (type == StructureDetailsImporter.class)
                    importer = new StructureDetailsImporter(connection, helper, this);
                else if (type == UrbanRapidTransitRailroadAttributeImporter.class)
                    importer = new UrbanRapidTransitRailroadAttributeImporter(connection, helper, this);
                else if (type == UrbanRapidTransitRailroadAttributeToStructureDetailsImporter.class)
                    importer = new UrbanRapidTransitRailroadAttributeToStructureDetailsImporter(connection, helper, this);
                else if (type == ParkingPlaceAttributeImporter.class)
                    importer = new ParkingPlaceAttributeImporter(connection, helper, this);
                else if (type == VehicleTerminalAttributeImporter.class)
                    importer = new VehicleTerminalAttributeImporter(connection, helper, this);
                else if (type == SewerSystemAttributeImporter.class)
                    importer = new SewerSystemAttributeImporter(connection, helper, this);
                else if (type == WeatherObservationTypeImporter.class)
                    importer = new WeatherObservationTypeImporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForReconstructionImporter.class)
                    importer = new CollectiveFacilitiesForReconstructionImporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForReconstructionAndRevitalizationImporter.class)
                    importer = new CollectiveFacilitiesForReconstructionAndRevitalizationImporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForTsunamiDisasterPreventionImporter.class)
                    importer = new CollectiveFacilitiesForTsunamiDisasterPreventionImporter(connection, helper, this);
                else if (type == CollectiveGovernmentAndPublicOfficeFacilitiesImporter.class)
                    importer = new CollectiveGovernmentAndPublicOfficeFacilitiesImporter(connection, helper, this);
                else if (type == CollectiveHousingFacilitiesImporter.class)
                    importer = new CollectiveHousingFacilitiesImporter(connection, helper, this);
                else if (type == CollectiveUrbanDisasterPreventionFacilitiesImporter.class)
                    importer = new CollectiveUrbanDisasterPreventionFacilitiesImporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementProjectImporter.class)
                    importer = new DisasterPreventionBlockImprovementProjectImporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementZonePlanImporter.class)
                    importer = new DisasterPreventionBlockImprovementZonePlanImporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementZonePlanToZonalDisasterPreventionFacilityImporter.class)
                    importer = new DisasterPreventionBlockImprovementZonePlanToZonalDisasterPreventionFacilityImporter(connection, helper, this);
                else if (type == DistributionBusinessParkImporter.class)
                    importer = new DistributionBusinessParkImporter(connection, helper, this);
                else if (type == DistributionBusinessZoneImporter.class)
                    importer = new DistributionBusinessZoneImporter(connection, helper, this);
                else if (type == DistrictPlanImporter.class)
                    importer = new DistrictPlanImporter(connection, helper, this);
                else if (type == ExceptionalFloorAreaRateDistrictImporter.class)
                    importer = new ExceptionalFloorAreaRateDistrictImporter(connection, helper, this);
                else if (type == UrbanFacilityStipulatedByCabinetOrderImporter.class)
                    importer = new UrbanFacilityStipulatedByCabinetOrderImporter(connection, helper, this);
                else if (type == HeightControlDistrictImporter.class)
                    importer = new HeightControlDistrictImporter(connection, helper, this);
                else if (type == HighLevelUseDistrictImporter.class)
                    importer = new HighLevelUseDistrictImporter(connection, helper, this);
                else if (type == HighRiseResidentialAttractionDistrictImporter.class)
                    importer = new HighRiseResidentialAttractionDistrictImporter(connection, helper, this);
                else if (type == HistoricSceneryMaintenanceAndImprovementDistrictPlanImporter.class)
                    importer = new HistoricSceneryMaintenanceAndImprovementDistrictPlanImporter(connection, helper, this);
                else if (type == IndustrialParkDevelopmentProjectImporter.class)
                    importer = new IndustrialParkDevelopmentProjectImporter(connection, helper, this);
                else if (type == LandReadjustmentProjectImporter.class)
                    importer = new LandReadjustmentProjectImporter(connection, helper, this);
                else if (type == LandscapeZoneImporter.class)
                    importer = new LandscapeZoneImporter(connection, helper, this);
                else if (type == NewHousingAndUrbanDevelopmentProjectImporter.class)
                    importer = new NewHousingAndUrbanDevelopmentProjectImporter(connection, helper, this);
                else if (type == NewUrbanInfrastructureProjectImporter.class)
                    importer = new NewUrbanInfrastructureProjectImporter(connection, helper, this);
                else if (type == OpenSpaceForPublicUseImporter.class)
                    importer = new OpenSpaceForPublicUseImporter(connection, helper, this);
                else if (type == PortZoneImporter.class)
                    importer = new PortZoneImporter(connection, helper, this);
                else if (type == ProductiveGreenZoneImporter.class)
                    importer = new ProductiveGreenZoneImporter(connection, helper, this);
                else if (type == ResidentialBlockConstructionProjectImporter.class)
                    importer = new ResidentialBlockConstructionProjectImporter(connection, helper, this);
                else if (type == ResidentialEnvironmentImprovementDistrictImporter.class)
                    importer = new ResidentialEnvironmentImprovementDistrictImporter(connection, helper, this);
                else if (type == RoadsideDistrictImprovementPlanImporter.class)
                    importer = new RoadsideDistrictImprovementPlanImporter(connection, helper, this);
                else if (type == RoadsideDistrictPlanImporter.class)
                    importer = new RoadsideDistrictPlanImporter(connection, helper, this);
                else if (type == RuralDistrictImprovementPlanImporter.class)
                    importer = new RuralDistrictImprovementPlanImporter(connection, helper, this);
                else if (type == ScenicDistrictImporter.class)
                    importer = new ScenicDistrictImporter(connection, helper, this);
                else if (type == SpecialGreenSpaceConservationDistrictImporter.class)
                    importer = new SpecialGreenSpaceConservationDistrictImporter(connection, helper, this);
                else if (type == SpecialUrbanRenaissanceDistrictImporter.class)
                    importer = new SpecialUrbanRenaissanceDistrictImporter(connection, helper, this);
                else if (type == SpecialUseAttractionDistrictImporter.class)
                    importer = new SpecialUseAttractionDistrictImporter(connection, helper, this);
                else if (type == SpecialUseDistrictImporter.class)
                    importer = new SpecialUseDistrictImporter(connection, helper, this);
                else if (type == SpecialUseRestrictionDistrictImporter.class)
                    importer = new SpecialUseRestrictionDistrictImporter(connection, helper, this);
                else if (type == SpecifiedBlockImporter.class)
                    importer = new SpecifiedBlockImporter(connection, helper, this);
                else if (type == SpecifiedDisasterPreventionBlockImprovementZoneImporter.class)
                    importer = new SpecifiedDisasterPreventionBlockImprovementZoneImporter(connection, helper, this);
                else if (type == SpecifiedUrgentUrbanRenewalAreaImporter.class)
                    importer = new SpecifiedUrgentUrbanRenewalAreaImporter(connection, helper, this);
                else if (type == SpecifiedUrgentUrbanRenewalAreaToGlobalHubCityDevelopmentProjectImporter.class)
                    importer = new SpecifiedUrgentUrbanRenewalAreaToGlobalHubCityDevelopmentProjectImporter(connection, helper, this);
                else if (type == SupplyFacilityImporter.class)
                    importer = new SupplyFacilityImporter(connection, helper, this);
                else if (type == TrafficFacilityImporter.class)
                    importer = new TrafficFacilityImporter(connection, helper, this);
                else if (type == TreatmentFacilityImporter.class)
                    importer = new TreatmentFacilityImporter(connection, helper, this);
                else if (type == TreePlantingDistrictImporter.class)
                    importer = new TreePlantingDistrictImporter(connection, helper, this);
                else if (type == UrbanRedevelopmentProjectImporter.class)
                    importer = new UrbanRedevelopmentProjectImporter(connection, helper, this);
                else if (type == UrbanRedevelopmentPromotionAreaImporter.class)
                    importer = new UrbanRedevelopmentPromotionAreaImporter(connection, helper, this);
                else if (type == UrbanRenewalProjectImporter.class)
                    importer = new UrbanRenewalProjectImporter(connection, helper, this);
                else if (type == UseDistrictImporter.class)
                    importer = new UseDistrictImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urf.WaterwayImporter.class)
                    importer = new org.citydb.ade.iur.importer.urf.WaterwayImporter(connection, helper, this);
                else if (type == AgreementImporter.class)
                    importer = new AgreementImporter(connection, helper, this);
                else if (type == DistrictsAndZonesImporter.class)
                    importer = new DistrictsAndZonesImporter(connection, helper, this);
                else if (type == AreaClassificationImporter.class)
                    importer = new AreaClassificationImporter(connection, helper, this);
                else if (type == UrbanFacilityImporter.class)
                    importer = new UrbanFacilityImporter(connection, helper, this);
                else if (type == UrbanFacilityToThreeDimensionalExtentImporter.class)
                    importer = new UrbanFacilityToThreeDimensionalExtentImporter(connection, helper, this);
                else if (type == UrbanDevelopmentProjectImporter.class)
                    importer = new UrbanDevelopmentProjectImporter(connection, helper, this);
                else if (type == AbstractDistrictPlanImporter.class)
                    importer = new AbstractDistrictPlanImporter(connection, helper, this);
                else if (type == AbstractDistrictPlanToDistrictDevelopmentPlanImporter.class)
                    importer = new AbstractDistrictPlanToDistrictDevelopmentPlanImporter(connection, helper, this);
                else if (type == AbstractDistrictPlanToPromotionDistrictImporter.class)
                    importer = new AbstractDistrictPlanToPromotionDistrictImporter(connection, helper, this);
                else if (type == DistrictImporter.class)
                    importer = new DistrictImporter(connection, helper, this);
                else if (type == DistrictDevelopmentPlanImporter.class)
                    importer = new DistrictDevelopmentPlanImporter(connection, helper, this);
                else if (type == DistrictDevelopmentPlanToDistrictFacilityImporter.class)
                    importer = new DistrictDevelopmentPlanToDistrictFacilityImporter(connection, helper, this);
                else if (type == DistrictDevelopmentPlanToDistrictImporter.class)
                    importer = new DistrictDevelopmentPlanToDistrictImporter(connection, helper, this);
                else if (type == GlobalHubCityDevelopmentProjectImporter.class)
                    importer = new GlobalHubCityDevelopmentProjectImporter(connection, helper, this);
                else if (type == ProjectPromotionAreaImporter.class)
                    importer = new ProjectPromotionAreaImporter(connection, helper, this);
                else if (type == PrivateUrbanRenewalProjectPlanImporter.class)
                    importer = new PrivateUrbanRenewalProjectPlanImporter(connection, helper, this);
                else if (type == QuasiUrbanPlanningAreaImporter.class)
                    importer = new QuasiUrbanPlanningAreaImporter(connection, helper, this);
                else if (type == ScheduledAreaForUrbanDevelopmentProjectImporter.class)
                    importer = new ScheduledAreaForUrbanDevelopmentProjectImporter(connection, helper, this);
                else if (type == SedimentDisasterProneAreaImporter.class)
                    importer = new SedimentDisasterProneAreaImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaImporter.class)
                    importer = new UrgentUrbanRenewalAreaImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter.class)
                    importer = new UrgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter.class)
                    importer = new UrgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter.class)
                    importer = new UrgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter(connection, helper, this);
                else if (type == UrbanDisasterRecoveryPromotionAreaImporter.class)
                    importer = new UrbanDisasterRecoveryPromotionAreaImporter(connection, helper, this);
                else if (type == UrbanPlanningAreaImporter.class)
                    importer = new UrbanPlanningAreaImporter(connection, helper, this);
                else if (type == ZonalDisasterPreventionFacilityImporter.class)
                    importer = new ZonalDisasterPreventionFacilityImporter(connection, helper, this);
                else if (type == AdministrationImporter.class)
                    importer = new AdministrationImporter(connection, helper, this);
                else if (type == AdministrationToDmAttributeImporter.class)
                    importer = new AdministrationToDmAttributeImporter(connection, helper, this);
                else if (type == ZoneImporter.class)
                    importer = new ZoneImporter(connection, helper, this);
                else if (type == ZoneToBoundaryImporter.class)
                    importer = new ZoneToBoundaryImporter(connection, helper, this);
                else if (type == CensusBlockImporter.class)
                    importer = new CensusBlockImporter(connection, helper, this);
                else if (type == DIDImporter.class)
                    importer = new DIDImporter(connection, helper, this);
                else if (type == DevelopmentProjectImporter.class)
                    importer = new DevelopmentProjectImporter(connection, helper, this);
                else if (type == DisasterDamageImporter.class)
                    importer = new DisasterDamageImporter(connection, helper, this);
                else if (type == DisasterPreventionBaseImporter.class)
                    importer = new DisasterPreventionBaseImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urf.LandPriceImporter.class)
                    importer = new org.citydb.ade.iur.importer.urf.LandPriceImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urf.LandUseDiversionImporter.class)
                    importer = new org.citydb.ade.iur.importer.urf.LandUseDiversionImporter(connection, helper, this);
                else if (type == NewHousingImporter.class)
                    importer = new NewHousingImporter(connection, helper, this);
                else if (type == PollutionImporter.class)
                    importer = new PollutionImporter(connection, helper, this);
                else if (type == PublicTransitFacilityImporter.class)
                    importer = new PublicTransitFacilityImporter(connection, helper, this);
                else if (type == RecreationsImporter.class)
                    importer = new RecreationsImporter(connection, helper, this);
                else if (type == ScenicResourceImporter.class)
                    importer = new ScenicResourceImporter(connection, helper, this);
                else if (type == ThreeDimensionalExtentImporter.class)
                    importer = new ThreeDimensionalExtentImporter(connection, helper, this);
                else if (type == UrbanFacilityStatusImporter.class)
                    importer = new UrbanFacilityStatusImporter(connection, helper, this);
                else if (type == UrbanizationImporter.class)
                    importer = new UrbanizationImporter(connection, helper, this);
                else if (type == WeatherObservationImporter.class)
                    importer = new WeatherObservationImporter(connection, helper, this);
                else if (type == WeatherObservationToWeatherObservationTypeImporter.class)
                    importer = new WeatherObservationToWeatherObservationTypeImporter(connection, helper, this);
                else if (type == UrbanFunctionImporter.class)
                    importer = new UrbanFunctionImporter(connection, helper, this);
                else if (type == UrbanFunctionToCityObjectImporter.class)
                    importer = new UrbanFunctionToCityObjectImporter(connection, helper, this);
                else if (type == UrbanFunctionToKeyValuePairAttributeImporter.class)
                    importer = new UrbanFunctionToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == TargetImporter.class)
                    importer = new TargetImporter(connection, helper, this);
            } else if (StatisticalGridModuleImporter.class.isAssignableFrom(type)) {
                // statistical grid module
                if (type == StatisticalGridImporter.class)
                    importer = new StatisticalGridImporter(connection, helper, this);
                else if (type == HouseholdsImporter.class)
                    importer = new HouseholdsImporter(connection, helper, this);
                else if (type == LandPriceImporter.class)
                    importer = new LandPriceImporter(connection, helper, this);
                else if (type == LandUseDiversionImporter.class)
                    importer = new LandUseDiversionImporter(connection, helper, this);
                else if (type == OfficesAndEmployeesImporter.class)
                    importer = new OfficesAndEmployeesImporter(connection, helper, this);
                else if (type == PopulationImporter.class)
                    importer = new PopulationImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urg.NumberOfHouseholdsImporter.class)
                    importer = new org.citydb.ade.iur.importer.urg.NumberOfHouseholdsImporter(connection, helper, this);
                else if (type == LandPricePerLandUseImporter.class)
                    importer = new LandPricePerLandUseImporter(connection, helper, this);
                else if (type == AreaOfAnnualDiversionsImporter.class)
                    importer = new AreaOfAnnualDiversionsImporter(connection, helper, this);
                else if (type == NumberOfAnnualDiversionsImporter.class)
                    importer = new NumberOfAnnualDiversionsImporter(connection, helper, this);
                else if (type == PopulationByAgeAndSexImporter.class)
                    importer = new PopulationByAgeAndSexImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urg.KeyValuePairImporter.class)
                    importer = new org.citydb.ade.iur.importer.urg.KeyValuePairImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter.class)
                    importer = new org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter(connection, helper, this);
            } else if (UrbanObjectModuleImporter.class.isAssignableFrom(type)) {
                // urban object module
                if (type == BuildingPropertiesImporter.class)
                    importer = new BuildingPropertiesImporter(connection, helper, this);
                else if (type == BridgePropertiesImporter.class)
                    importer = new BridgePropertiesImporter(connection, helper, this);
                else if (type == CityFurniturePropertiesImporter.class)
                    importer = new CityFurniturePropertiesImporter(connection, helper, this);
                else if (type == ReliefFeaturePropertiesImporter.class)
                    importer = new ReliefFeaturePropertiesImporter(connection, helper, this);
                else if (type == ReliefComponentPropertiesImporter.class)
                    importer = new ReliefComponentPropertiesImporter(connection, helper, this);
                else if (type == WaterBodyPropertiesImporter.class)
                    importer = new WaterBodyPropertiesImporter(connection, helper, this);
                else if (type == BoundarySurfacePropertiesImporter.class)
                    importer = new BoundarySurfacePropertiesImporter(connection, helper, this);
                else if (type == BuildingFurniturePropertiesImporter.class)
                    importer = new BuildingFurniturePropertiesImporter(connection, helper, this);
                else if (type == BuildingInstallationPropertiesImporter.class)
                    importer = new BuildingInstallationPropertiesImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter.class)
                    importer = new org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter(connection, helper, this);
                else if (type == LandUsePropertiesImporter.class)
                    importer = new LandUsePropertiesImporter(connection, helper, this);
                else if (type == OpeningPropertiesImporter.class)
                    importer = new OpeningPropertiesImporter(connection, helper, this);
                else if (type == RoomPropertiesImporter.class)
                    importer = new RoomPropertiesImporter(connection, helper, this);
                else if (type == TransportationComplexPropertiesImporter.class)
                    importer = new TransportationComplexPropertiesImporter(connection, helper, this);
                else if (type == TrafficAreaPropertiesImporter.class)
                    importer = new TrafficAreaPropertiesImporter(connection, helper, this);
                else if (type == TunnelPropertiesImporter.class)
                    importer = new TunnelPropertiesImporter(connection, helper, this);
                else if (type == VegetationObjectPropertiesImporter.class)
                    importer = new VegetationObjectPropertiesImporter(connection, helper, this);
                else if (type == DataQualityAttributeImporter.class)
                    importer = new DataQualityAttributeImporter(connection, helper, this);
                else if (type == PublicSurveyDataQualityAttributeImporter.class)
                    importer = new PublicSurveyDataQualityAttributeImporter(connection, helper, this);
                else if (type == DisasterRiskAttributeImporter.class)
                    importer = new DisasterRiskAttributeImporter(connection, helper, this);
                else if (type == FloodingRiskAttributeImporter.class)
                    importer = new FloodingRiskAttributeImporter(connection, helper, this);
                else if (type == RiverFloodingRiskAttributeImporter.class)
                    importer = new RiverFloodingRiskAttributeImporter(connection, helper, this);
                else if (type == DmAttributeImporter.class)
                    importer = new DmAttributeImporter(connection, helper, this);
                else if (type == DmAnnotationImporter.class)
                    importer = new DmAnnotationImporter(connection, helper, this);
                else if (type == DmElementImporter.class)
                    importer = new DmElementImporter(connection, helper, this);
                else if (type == DmGeometricAttributeImporter.class)
                    importer = new DmGeometricAttributeImporter(connection, helper, this);
                else if (type == FacilityAttributeImporter.class)
                    importer = new FacilityAttributeImporter(connection, helper, this);
                else if (type == FishingPortAttributeImporter.class)
                    importer = new FishingPortAttributeImporter(connection, helper, this);
                else if (type == FishingPortCapacityImporter.class)
                    importer = new FishingPortCapacityImporter(connection, helper, this);
                else if (type == FishingPortFacilityImporter.class)
                    importer = new FishingPortFacilityImporter(connection, helper, this);
                else if (type == MaintenanceHistoryAttributeImporter.class)
                    importer = new MaintenanceHistoryAttributeImporter(connection, helper, this);
                else if (type == ParkFacilityLongevityPlanAttributeImporter.class)
                    importer = new ParkFacilityLongevityPlanAttributeImporter(connection, helper, this);
                else if (type == ParkFacilityLongevityPlanAttributeToParkHealthAssessmentImporter.class)
                    importer = new ParkFacilityLongevityPlanAttributeToParkHealthAssessmentImporter(connection, helper, this);
                else if (type == ParkFacilityLongevityPlanAttributeToLongevityMeasuresImporter.class)
                    importer = new ParkFacilityLongevityPlanAttributeToLongevityMeasuresImporter(connection, helper, this);
                else if (type == NumberOfFacilitiesImporter.class)
                    importer = new NumberOfFacilitiesImporter(connection, helper, this);
                else if (type == RepairsBeforeParkHealthAssessmentImporter.class)
                    importer = new RepairsBeforeParkHealthAssessmentImporter(connection, helper, this);
                else if (type == ParkHealthAssessmentImporter.class)
                    importer = new ParkHealthAssessmentImporter(connection, helper, this);
                else if (type == LongevityMeasuresImporter.class)
                    importer = new LongevityMeasuresImporter(connection, helper, this);
                else if (type == CountermeasuresCostImporter.class)
                    importer = new CountermeasuresCostImporter(connection, helper, this);
                else if (type == PortAttributeImporter.class)
                    importer = new PortAttributeImporter(connection, helper, this);
                else if (type == CargoHandlingFacilityImporter.class)
                    importer = new CargoHandlingFacilityImporter(connection, helper, this);
                else if (type == CyberportMarinaAndPBSImporter.class)
                    importer = new CyberportMarinaAndPBSImporter(connection, helper, this);
                else if (type == HarborFacilityImporter.class)
                    importer = new HarborFacilityImporter(connection, helper, this);
                else if (type == MooringFacilityImporter.class)
                    importer = new MooringFacilityImporter(connection, helper, this);
                else if (type == NavigationAssistanceFacilityImporter.class)
                    importer = new NavigationAssistanceFacilityImporter(connection, helper, this);
                else if (type == PortEnvironmentalImprovementFacilityImporter.class)
                    importer = new PortEnvironmentalImprovementFacilityImporter(connection, helper, this);
                else if (type == PortManagementFacilityImporter.class)
                    importer = new PortManagementFacilityImporter(connection, helper, this);
                else if (type == PortPassengerFacilityImporter.class)
                    importer = new PortPassengerFacilityImporter(connection, helper, this);
                else if (type == PortPollutionControlFacilityImporter.class)
                    importer = new PortPollutionControlFacilityImporter(connection, helper, this);
                else if (type == PortProtectiveFacilityImporter.class)
                    importer = new PortProtectiveFacilityImporter(connection, helper, this);
                else if (type == PortStorageFacilityImporter.class)
                    importer = new PortStorageFacilityImporter(connection, helper, this);
                else if (type == PortTransportationFacilityImporter.class)
                    importer = new PortTransportationFacilityImporter(connection, helper, this);
                else if (type == PortWasteTreatmentFacilityImporter.class)
                    importer = new PortWasteTreatmentFacilityImporter(connection, helper, this);
                else if (type == PortWelfareFacilityImporter.class)
                    importer = new PortWelfareFacilityImporter(connection, helper, this);
                else if (type == ShipServiceFacilityImporter.class)
                    importer = new ShipServiceFacilityImporter(connection, helper, this);
                else if (type == FacilityIdAttributeImporter.class)
                    importer = new FacilityIdAttributeImporter(connection, helper, this);
                else if (type == RiverFacilityIdAttributeImporter.class)
                    importer = new RiverFacilityIdAttributeImporter(connection, helper, this);
                else if (type == FacilityTypeAttributeImporter.class)
                    importer = new FacilityTypeAttributeImporter(connection, helper, this);
                else if (type == KeyValuePairAttributeImporter.class)
                    importer = new KeyValuePairAttributeImporter(connection, helper, this);
                else if (type == RealEstateIDAttributeImporter.class)
                    importer = new RealEstateIDAttributeImporter(connection, helper, this);
                else if (type == BuildingAttributeImporter.class)
                    importer = new BuildingAttributeImporter(connection, helper, this);
                else if (type == BuildingUsecaseAttributeImporter.class)
                    importer = new BuildingUsecaseAttributeImporter(connection, helper, this);
                else if (type == ConstructionBaseAttributeImporter.class)
                    importer = new ConstructionBaseAttributeImporter(connection, helper, this);
                else if (type == BridgeFunctionalAttributeImporter.class)
                    importer = new BridgeFunctionalAttributeImporter(connection, helper, this);
                else if (type == ConstructionRiskAssessmentAttributeImporter.class)
                    importer = new ConstructionRiskAssessmentAttributeImporter(connection, helper, this);
                else if (type == BridgeStructureAttributeImporter.class)
                    importer = new BridgeStructureAttributeImporter(connection, helper, this);
                else if (type == BuildingDetailAttributeImporter.class)
                    importer = new BuildingDetailAttributeImporter(connection, helper, this);
                else if (type == BuildingIDAttributeImporter.class)
                    importer = new BuildingIDAttributeImporter(connection, helper, this);
                else if (type == CityFurnitureDetailAttributeImporter.class)
                    importer = new CityFurnitureDetailAttributeImporter(connection, helper, this);
                else if (type == IfcAttributeImporter.class)
                    importer = new IfcAttributeImporter(connection, helper, this);
                else if (type == IfcClassificationReferenceImporter.class)
                    importer = new IfcClassificationReferenceImporter(connection, helper, this);
                else if (type == IfcClassificationImporter.class)
                    importer = new IfcClassificationImporter(connection, helper, this);
                else if (type == IfcCoordinateReferenceSystemImporter.class)
                    importer = new IfcCoordinateReferenceSystemImporter(connection, helper, this);
                else if (type == IfcProjectedCRSImporter.class)
                    importer = new IfcProjectedCRSImporter(connection, helper, this);
                else if (type == IfcMapConversionImporter.class)
                    importer = new IfcMapConversionImporter(connection, helper, this);
                else if (type == IfcCoordinateReferenceSystemSelectImporter.class)
                    importer = new IfcCoordinateReferenceSystemSelectImporter(connection, helper, this);
                else if (type == IfcGeometricRepresentationContextImporter.class)
                    importer = new IfcGeometricRepresentationContextImporter(connection, helper, this);
                else if (type == IfcAxis2Placement3DImporter.class)
                    importer = new IfcAxis2Placement3DImporter(connection, helper, this);
                else if (type == IfcPsetBuildingCommonImporter.class)
                    importer = new IfcPsetBuildingCommonImporter(connection, helper, this);
                else if (type == IfcPsetDoorCommonImporter.class)
                    importer = new IfcPsetDoorCommonImporter(connection, helper, this);
                else if (type == IfcPsetOpeningElementCommonImporter.class)
                    importer = new IfcPsetOpeningElementCommonImporter(connection, helper, this);
                else if (type == IfcPsetSiteCommonImporter.class)
                    importer = new IfcPsetSiteCommonImporter(connection, helper, this);
                else if (type == IfcPsetSpaceCommonImporter.class)
                    importer = new IfcPsetSpaceCommonImporter(connection, helper, this);
                else if (type == IfcPsetWindowCommonImporter.class)
                    importer = new IfcPsetWindowCommonImporter(connection, helper, this);
                else if (type == IfcRootImporter.class)
                    importer = new IfcRootImporter(connection, helper, this);
                else if (type == IfcObjectImporter.class)
                    importer = new IfcObjectImporter(connection, helper, this);
                else if (type == IfcZoneImporter.class)
                    importer = new IfcZoneImporter(connection, helper, this);
                else if (type == IfcProductImporter.class)
                    importer = new IfcProductImporter(connection, helper, this);
                else if (type == IfcElementImporter.class)
                    importer = new IfcElementImporter(connection, helper, this);
                else if (type == IfcBuildingElementImporter.class)
                    importer = new IfcBuildingElementImporter(connection, helper, this);
                else if (type == IfcDoorImporter.class)
                    importer = new IfcDoorImporter(connection, helper, this);
                else if (type == IfcWallImporter.class)
                    importer = new IfcWallImporter(connection, helper, this);
                else if (type == IfcWindowImporter.class)
                    importer = new IfcWindowImporter(connection, helper, this);
                else if (type == IfcOpeningElementImporter.class)
                    importer = new IfcOpeningElementImporter(connection, helper, this);
                else if (type == IfcSpatialStructureElementImporter.class)
                    importer = new IfcSpatialStructureElementImporter(connection, helper, this);
                else if (type == IfcBuildingImporter.class)
                    importer = new IfcBuildingImporter(connection, helper, this);
                else if (type == IfcSiteImporter.class)
                    importer = new IfcSiteImporter(connection, helper, this);
                else if (type == IfcSpaceImporter.class)
                    importer = new IfcSpaceImporter(connection, helper, this);
                else if (type == IfcProjectImporter.class)
                    importer = new IfcProjectImporter(connection, helper, this);
                else if (type == IfcProjectToIfcUnitImporter.class)
                    importer = new IfcProjectToIfcUnitImporter(connection, helper, this);
                else if (type == IfcUnitImporter.class)
                    importer = new IfcUnitImporter(connection, helper, this);
                else if (type == IfcSpaceBaseQuantityImporter.class)
                    importer = new IfcSpaceBaseQuantityImporter(connection, helper, this);
                else if (type == IndoorAttributeImporter.class)
                    importer = new IndoorAttributeImporter(connection, helper, this);
                else if (type == IndoorFacilityAttributeImporter.class)
                    importer = new IndoorFacilityAttributeImporter(connection, helper, this);
                else if (type == IndoorSpaceAttributeImporter.class)
                    importer = new IndoorSpaceAttributeImporter(connection, helper, this);
                else if (type == IndoorStoreyAttributeImporter.class)
                    importer = new IndoorStoreyAttributeImporter(connection, helper, this);
                else if (type == IndoorTactileTileAttributeImporter.class)
                    importer = new IndoorTactileTileAttributeImporter(connection, helper, this);
                else if (type == IndoorUserDefinedAttributeImporter.class)
                    importer = new IndoorUserDefinedAttributeImporter(connection, helper, this);
                else if (type == UserDefinedValueImporter.class)
                    importer = new UserDefinedValueImporter(connection, helper, this);
                else if (type == LandUseDetailAttributeImporter.class)
                    importer = new LandUseDetailAttributeImporter(connection, helper, this);
                else if (type == LargeCustomerFacilityAttributeImporter.class)
                    importer = new LargeCustomerFacilityAttributeImporter(connection, helper, this);
                else if (type == RailwayRouteAttributeImporter.class)
                    importer = new RailwayRouteAttributeImporter(connection, helper, this);
                else if (type == RailwayTrackAttributeImporter.class)
                    importer = new RailwayTrackAttributeImporter(connection, helper, this);
                else if (type == RailwayTrackAttributeToControlPointImporter.class)
                    importer = new RailwayTrackAttributeToControlPointImporter(connection, helper, this);
                else if (type == ControlPointImporter.class)
                    importer = new ControlPointImporter(connection, helper, this);
                else if (type == ControlPointParameterImporter.class)
                    importer = new ControlPointParameterImporter(connection, helper, this);
                else if (type == CircularCurveTypeImporter.class)
                    importer = new CircularCurveTypeImporter(connection, helper, this);
                else if (type == TransitionCurveTypeImporter.class)
                    importer = new TransitionCurveTypeImporter(connection, helper, this);
                else if (type == SlopeTypeImporter.class)
                    importer = new SlopeTypeImporter(connection, helper, this);
                else if (type == VerticalCurveTypeImporter.class)
                    importer = new VerticalCurveTypeImporter(connection, helper, this);
                else if (type == RoadTypeImporter.class)
                    importer = new RoadTypeImporter(connection, helper, this);
                else if (type == RoadAttributeImporter.class)
                    importer = new RoadAttributeImporter(connection, helper, this);
                else if (type == RoadStructureAttributeImporter.class)
                    importer = new RoadStructureAttributeImporter(connection, helper, this);
                else if (type == SquareUrbanPlanAttributeImporter.class)
                    importer = new SquareUrbanPlanAttributeImporter(connection, helper, this);
                else if (type == StationSquareAttributeImporter.class)
                    importer = new StationSquareAttributeImporter(connection, helper, this);
                else if (type == TerminalAttributeImporter.class)
                    importer = new TerminalAttributeImporter(connection, helper, this);
                else if (type == TrackAttributeImporter.class)
                    importer = new TrackAttributeImporter(connection, helper, this);
                else if (type == TrafficAreaStructureAttributeImporter.class)
                    importer = new TrafficAreaStructureAttributeImporter(connection, helper, this);
                else if (type == TrafficVolumeAttributeImporter.class)
                    importer = new TrafficVolumeAttributeImporter(connection, helper, this);
                else if (type == TrafficObjectUsecaseAttributeImporter.class)
                    importer = new TrafficObjectUsecaseAttributeImporter(connection, helper, this);
                else if (type == TunnelFunctionalAttributeImporter.class)
                    importer = new TunnelFunctionalAttributeImporter(connection, helper, this);
                else if (type == TunnelStructureAttributeImporter.class)
                    importer = new TunnelStructureAttributeImporter(connection, helper, this);
                else if (type == WaterBodyDetailAttributeImporter.class)
                    importer = new WaterBodyDetailAttributeImporter(connection, helper, this);
                else if (type == ConstructionEventImporter.class)
                    importer = new ConstructionEventImporter(connection, helper, this);
                else if (type == ElevationImporter.class)
                    importer = new ElevationImporter(connection, helper, this);
                else if (type == HeightImporter.class)
                    importer = new HeightImporter(connection, helper, this);
                else if (type == OccupancyImporter.class)
                    importer = new OccupancyImporter(connection, helper, this);
                else if (type == ConstructionStructureAttributeImporter.class)
                    importer = new ConstructionStructureAttributeImporter(connection, helper, this);
                else if (type == DamAttributeImporter.class)
                    importer = new DamAttributeImporter(connection, helper, this);
                else if (type == EmbankmentAttributeImporter.class)
                    importer = new EmbankmentAttributeImporter(connection, helper, this);
                else if (type == OffsetDepthImporter.class)
                    importer = new OffsetDepthImporter(connection, helper, this);
                else if (type == ThematicShapeImporter.class)
                    importer = new ThematicShapeImporter(connection, helper, this);
                else if (type == LengthAttributeImporter.class)
                    importer = new LengthAttributeImporter(connection, helper, this);
                else if (type == PointCloudSpecAttributeImporter.class)
                    importer = new PointCloudSpecAttributeImporter(connection, helper, this);
                else if (type == WaterwayDetailAttributeImporter.class)
                    importer = new WaterwayDetailAttributeImporter(connection, helper, this);
                else if (type == SewerPipeImporter.class)
                    importer = new SewerPipeImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.uro.WaterwayImporter.class)
                    importer = new org.citydb.ade.iur.importer.uro.WaterwayImporter(connection, helper, this);
                else if (type == CableImporter.class)
                    importer = new CableImporter(connection, helper, this);
                else if (type == PipeImporter.class)
                    importer = new PipeImporter(connection, helper, this);
                else if (type == AppurtenanceImporter.class)
                    importer = new AppurtenanceImporter(connection, helper, this);
                else if (type == UtilityLinkImporter.class)
                    importer = new UtilityLinkImporter(connection, helper, this);
                else if (type == UtilityLinkToLengthAttributeImporter.class)
                    importer = new UtilityLinkToLengthAttributeImporter(connection, helper, this);
                else if (type == UtilityNodeContainerImporter.class)
                    importer = new UtilityNodeContainerImporter(connection, helper, this);
                else if (type == OtherConstructionImporter.class)
                    importer = new OtherConstructionImporter(connection, helper, this);
                else if (type == OtherConstructionToConstructionEventImporter.class)
                    importer = new OtherConstructionToConstructionEventImporter(connection, helper, this);
                else if (type == OtherConstructionToElevationImporter.class)
                    importer = new OtherConstructionToElevationImporter(connection, helper, this);
                else if (type == OtherConstructionToHeightImporter.class)
                    importer = new OtherConstructionToHeightImporter(connection, helper, this);
                else if (type == OtherConstructionToOccupancyImporter.class)
                    importer = new OtherConstructionToOccupancyImporter(connection, helper, this);
                else if (type == OtherConstructionToFacilityTypeAttributeImporter.class)
                    importer = new OtherConstructionToFacilityTypeAttributeImporter(connection, helper, this);
                else if (type == OtherConstructionToFacilityAttributeImporter.class)
                    importer = new OtherConstructionToFacilityAttributeImporter(connection, helper, this);
                else if (type == OtherConstructionToDisasterRiskAttributeImporter.class)
                    importer = new OtherConstructionToDisasterRiskAttributeImporter(connection, helper, this);
                else if (type == OtherConstructionToDmAttributeImporter.class)
                    importer = new OtherConstructionToDmAttributeImporter(connection, helper, this);
                else if (type == OtherConstructionToKeyValuePairAttributeImporter.class)
                    importer = new OtherConstructionToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == PointCloudImporter.class)
                    importer = new PointCloudImporter(connection, helper, this);
                else if (type == UtilityNetworkElementImporter.class)
                    importer = new UtilityNetworkElementImporter(connection, helper, this);
                else if (type == UtilityNetworkElementToOffsetDepthImporter.class)
                    importer = new UtilityNetworkElementToOffsetDepthImporter(connection, helper, this);
                else if (type == UtilityNetworkElementToThematicShapeImporter.class)
                    importer = new UtilityNetworkElementToThematicShapeImporter(connection, helper, this);
                else if (type == AbstractBoundarySurfaceImporter.class)
                    importer = new AbstractBoundarySurfaceImporter(connection, helper, this);
                else if (type == ConstructionInstallationImporter.class)
                    importer = new ConstructionInstallationImporter(connection, helper, this);
                else if (type == PublicLandImporter.class)
                    importer = new PublicLandImporter(connection, helper, this);
                else if (type == UndergroundBuildingImporter.class)
                    importer = new UndergroundBuildingImporter(connection, helper, this);
                else if (type == BuildingToDisasterRiskAttributeImporter.class)
                    importer = new BuildingToDisasterRiskAttributeImporter(connection, helper, this);
                else if (type == BuildingToDmAttributeImporter.class)
                    importer = new BuildingToDmAttributeImporter(connection, helper, this);
                else if (type == BuildingToFacilityAttributeImporter.class)
                    importer = new BuildingToFacilityAttributeImporter(connection, helper, this);
                else if (type == BuildingToKeyValuePairAttributeImporter.class)
                    importer = new BuildingToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == BridgeToDisasterRiskAttributeImporter.class)
                    importer = new BridgeToDisasterRiskAttributeImporter(connection, helper, this);
                else if (type == BridgeToDmAttributeImporter.class)
                    importer = new BridgeToDmAttributeImporter(connection, helper, this);
                else if (type == BridgeToFacilityAttributeImporter.class)
                    importer = new BridgeToFacilityAttributeImporter(connection, helper, this);
                else if (type == BridgeToKeyValuePairAttributeImporter.class)
                    importer = new BridgeToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == CityFurnitureToDmAttributeImporter.class)
                    importer = new CityFurnitureToDmAttributeImporter(connection, helper, this);
                else if (type == CityFurnitureToFacilityAttributeImporter.class)
                    importer = new CityFurnitureToFacilityAttributeImporter(connection, helper, this);
                else if (type == CityFurnitureToKeyValuePairAttributeImporter.class)
                    importer = new CityFurnitureToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == ReliefFeatureToKeyValuePairAttributeImporter.class)
                    importer = new ReliefFeatureToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == ReliefComponentToDmAttributeImporter.class)
                    importer = new ReliefComponentToDmAttributeImporter(connection, helper, this);
                else if (type == WaterBodyToFloodingRiskAttributeImporter.class)
                    importer = new WaterBodyToFloodingRiskAttributeImporter(connection, helper, this);
                else if (type == WaterBodyToDmAttributeImporter.class)
                    importer = new WaterBodyToDmAttributeImporter(connection, helper, this);
                else if (type == WaterBodyToFacilityAttributeImporter.class)
                    importer = new WaterBodyToFacilityAttributeImporter(connection, helper, this);
                else if (type == WaterBodyToKeyValuePairAttributeImporter.class)
                    importer = new WaterBodyToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == LandUseToDmAttributeImporter.class)
                    importer = new LandUseToDmAttributeImporter(connection, helper, this);
                else if (type == LandUseToFacilityAttributeImporter.class)
                    importer = new LandUseToFacilityAttributeImporter(connection, helper, this);
                else if (type == LandUseToKeyValuePairAttributeImporter.class)
                    importer = new LandUseToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == TransportationComplexToDmAttributeImporter.class)
                    importer = new TransportationComplexToDmAttributeImporter(connection, helper, this);
                else if (type == TransportationComplexToFacilityAttributeImporter.class)
                    importer = new TransportationComplexToFacilityAttributeImporter(connection, helper, this);
                else if (type == TransportationComplexToKeyValuePairAttributeImporter.class)
                    importer = new TransportationComplexToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == TunnelToDisasterRiskAttributeImporter.class)
                    importer = new TunnelToDisasterRiskAttributeImporter(connection, helper, this);
                else if (type == TunnelToDmAttributeImporter.class)
                    importer = new TunnelToDmAttributeImporter(connection, helper, this);
                else if (type == TunnelToFacilityAttributeImporter.class)
                    importer = new TunnelToFacilityAttributeImporter(connection, helper, this);
                else if (type == TunnelToKeyValuePairAttributeImporter.class)
                    importer = new TunnelToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == VegetationObjectToDmAttributeImporter.class)
                    importer = new VegetationObjectToDmAttributeImporter(connection, helper, this);
                else if (type == VegetationObjectToFacilityAttributeImporter.class)
                    importer = new VegetationObjectToFacilityAttributeImporter(connection, helper, this);
                else if (type == VegetationObjectToKeyValuePairAttributeImporter.class)
                    importer = new VegetationObjectToKeyValuePairAttributeImporter(connection, helper, this);
            } else if (PublicTransitModuleImporter.class.isAssignableFrom(type)) {
                // public transit module
                if (type == AgencyImporter.class)
                    importer = new AgencyImporter(connection, helper, this);
                else if (type == AttributionImporter.class)
                    importer = new AttributionImporter(connection, helper, this);
                else if (type == CalendarImporter.class)
                    importer = new CalendarImporter(connection, helper, this);
                else if (type == CalendarDateImporter.class)
                    importer = new CalendarDateImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urt.CityObjectGroupPropertiesImporter.class)
                    importer = new org.citydb.ade.iur.importer.urt.CityObjectGroupPropertiesImporter(connection, helper, this);
                else if (type == DescriptionImporter.class)
                    importer = new DescriptionImporter(connection, helper, this);
                else if (type == FareAttributeImporter.class)
                    importer = new FareAttributeImporter(connection, helper, this);
                else if (type == FareRuleImporter.class)
                    importer = new FareRuleImporter(connection, helper, this);
                else if (type == FeedInfoImporter.class)
                    importer = new FeedInfoImporter(connection, helper, this);
                else if (type == FrequencyImporter.class)
                    importer = new FrequencyImporter(connection, helper, this);
                else if (type == OfficeImporter.class)
                    importer = new OfficeImporter(connection, helper, this);
                else if (type == PathwayImporter.class)
                    importer = new PathwayImporter(connection, helper, this);
                else if (type == PointImporter.class)
                    importer = new PointImporter(connection, helper, this);
                else if (type == PublicTransitDataTypeImporter.class)
                    importer = new PublicTransitDataTypeImporter(connection, helper, this);
                else if (type == RouteImporter.class)
                    importer = new RouteImporter(connection, helper, this);
                else if (type == StopImporter.class)
                    importer = new StopImporter(connection, helper, this);
                else if (type == StopTimeImporter.class)
                    importer = new StopTimeImporter(connection, helper, this);
                else if (type == TransferImporter.class)
                    importer = new TransferImporter(connection, helper, this);
                else if (type == TranslationImporter.class)
                    importer = new TranslationImporter(connection, helper, this);
                else if (type == TranslationJPImporter.class)
                    importer = new TranslationJPImporter(connection, helper, this);
                else if (type == TripImporter.class)
                    importer = new TripImporter(connection, helper, this);
                else if (type == PublicTransitImporter.class)
                    importer = new PublicTransitImporter(connection, helper, this);
            }

            if (importer == null)
                throw new SQLException("Failed to build ADE importer of type " + type.getName() + ".");

            importers.put(type, importer);
        }

        return type.cast(importer);
    }
}
