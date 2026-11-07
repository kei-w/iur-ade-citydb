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
import org.citydb.ade.iur.importer.urg.NumberOfHouseholdsImporter;
import org.citygml4j.ade.iur.model.urg.NumberOfHouseholds;
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
        if (UrbanFunctionModule.v2_0.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof UseDistrict)
                getImporter(UseDistrictImporter.class).doImport((UseDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUseDistrict)
                getImporter(SpecialUseDistrictImporter.class).doImport((SpecialUseDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUseRestrictionDistrict)
                getImporter(SpecialUseRestrictionDistrictImporter.class).doImport((SpecialUseRestrictionDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof ExceptionalFloorAreaRateDistrict)
                getImporter(ExceptionalFloorAreaRateDistrictImporter.class).doImport((ExceptionalFloorAreaRateDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof HighRiseResidentialAttractionDistrict)
                getImporter(HighRiseResidentialAttractionDistrictImporter.class).doImport((HighRiseResidentialAttractionDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof HeightControlDistrict)
                getImporter(HeightControlDistrictImporter.class).doImport((HeightControlDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof HighLevelUseDistrict)
                getImporter(HighLevelUseDistrictImporter.class).doImport((HighLevelUseDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecifiedBlock)
                getImporter(SpecifiedBlockImporter.class).doImport((SpecifiedBlock) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUrbanRenaissanceDistrict)
                getImporter(SpecialUrbanRenaissanceDistrictImporter.class).doImport((SpecialUrbanRenaissanceDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof ResidentialEnvironmentImprovementDistrict)
                getImporter(ResidentialEnvironmentImprovementDistrictImporter.class).doImport((ResidentialEnvironmentImprovementDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialUseAttractionDistrict)
                getImporter(SpecialUseAttractionDistrictImporter.class).doImport((SpecialUseAttractionDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecifiedDisasterPreventionBlockImprovementZone)
                getImporter(SpecifiedDisasterPreventionBlockImprovementZoneImporter.class).doImport((SpecifiedDisasterPreventionBlockImprovementZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof LandscapeZone)
                getImporter(LandscapeZoneImporter.class).doImport((LandscapeZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof ScenicDistrict)
                getImporter(ScenicDistrictImporter.class).doImport((ScenicDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof PortZone)
                getImporter(PortZoneImporter.class).doImport((PortZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecialGreenSpaceConservationDistrict)
                getImporter(SpecialGreenSpaceConservationDistrictImporter.class).doImport((SpecialGreenSpaceConservationDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof TreePlantingDistrict)
                getImporter(TreePlantingDistrictImporter.class).doImport((TreePlantingDistrict) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistributionBusinessZone)
                getImporter(DistributionBusinessZoneImporter.class).doImport((DistributionBusinessZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof ProductiveGreenZone)
                getImporter(ProductiveGreenZoneImporter.class).doImport((ProductiveGreenZone) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanRedevelopmentPromotionArea)
                getImporter(UrbanRedevelopmentPromotionAreaImporter.class).doImport((UrbanRedevelopmentPromotionArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof LandReadjustmentProject)
                getImporter(LandReadjustmentProjectImporter.class).doImport((LandReadjustmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof NewHousingAndUrbanDevelopmentProject)
                getImporter(NewHousingAndUrbanDevelopmentProjectImporter.class).doImport((NewHousingAndUrbanDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof IndustrialParkDevelopmentProject)
                getImporter(IndustrialParkDevelopmentProjectImporter.class).doImport((IndustrialParkDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanRedevelopmentProject)
                getImporter(UrbanRedevelopmentProjectImporter.class).doImport((UrbanRedevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof NewUrbanInfrastructureProject)
                getImporter(NewUrbanInfrastructureProjectImporter.class).doImport((NewUrbanInfrastructureProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof ResidentialBlockConstructionProject)
                getImporter(ResidentialBlockConstructionProjectImporter.class).doImport((ResidentialBlockConstructionProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterPreventionBlockImprovementProject)
                getImporter(DisasterPreventionBlockImprovementProjectImporter.class).doImport((DisasterPreventionBlockImprovementProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanRenewalProject)
                getImporter(UrbanRenewalProjectImporter.class).doImport((UrbanRenewalProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof TrafficFacility)
                getImporter(TrafficFacilityImporter.class).doImport((TrafficFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof OpenSpaceForPublicUse)
                getImporter(OpenSpaceForPublicUseImporter.class).doImport((OpenSpaceForPublicUse) object, objectId, objectType, foreignKeys);
            else if (object instanceof SupplyFacility)
                getImporter(SupplyFacilityImporter.class).doImport((SupplyFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof TreatmentFacility)
                getImporter(TreatmentFacilityImporter.class).doImport((TreatmentFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof Waterway)
                getImporter(WaterwayImporter.class).doImport((Waterway) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveHousingFacilities)
                getImporter(CollectiveHousingFacilitiesImporter.class).doImport((CollectiveHousingFacilities) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveGovernmentAndPublicOfficeFacilities)
                getImporter(CollectiveGovernmentAndPublicOfficeFacilitiesImporter.class).doImport((CollectiveGovernmentAndPublicOfficeFacilities) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveUrbanDisasterPreventionFacilities)
                getImporter(CollectiveUrbanDisasterPreventionFacilitiesImporter.class).doImport((CollectiveUrbanDisasterPreventionFacilities) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistributionBusinessPark)
                getImporter(DistributionBusinessParkImporter.class).doImport((DistributionBusinessPark) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanFacilityStipulatedByCabinetOrder)
                getImporter(UrbanFacilityStipulatedByCabinetOrderImporter.class).doImport((UrbanFacilityStipulatedByCabinetOrder) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveFacilitiesForTsunamiDisasterPrevention)
                getImporter(CollectiveFacilitiesForTsunamiDisasterPreventionImporter.class).doImport((CollectiveFacilitiesForTsunamiDisasterPrevention) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveFacilitiesForReconstructionAndRevitalization)
                getImporter(CollectiveFacilitiesForReconstructionAndRevitalizationImporter.class).doImport((CollectiveFacilitiesForReconstructionAndRevitalization) object, objectId, objectType, foreignKeys);
            else if (object instanceof CollectiveFacilitiesForReconstruction)
                getImporter(CollectiveFacilitiesForReconstructionImporter.class).doImport((CollectiveFacilitiesForReconstruction) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistrictPlan)
                getImporter(DistrictPlanImporter.class).doImport((DistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterPreventionBlockImprovementZonePlan)
                getImporter(DisasterPreventionBlockImprovementZonePlanImporter.class).doImport((DisasterPreventionBlockImprovementZonePlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof HistoricSceneryMaintenanceAndImprovementDistrictPlan)
                getImporter(HistoricSceneryMaintenanceAndImprovementDistrictPlanImporter.class).doImport((HistoricSceneryMaintenanceAndImprovementDistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof RoadsideDistrictPlan)
                getImporter(RoadsideDistrictPlanImporter.class).doImport((RoadsideDistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof RoadsideDistrictImprovementPlan)
                getImporter(RoadsideDistrictImprovementPlanImporter.class).doImport((RoadsideDistrictImprovementPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof RuralDistrictImprovementPlan)
                getImporter(RuralDistrictImprovementPlanImporter.class).doImport((RuralDistrictImprovementPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof SpecifiedUrgentUrbanRenewalArea)
                getImporter(SpecifiedUrgentUrbanRenewalAreaImporter.class).doImport((SpecifiedUrgentUrbanRenewalArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanPlanningArea)
                getImporter(UrbanPlanningAreaImporter.class).doImport((UrbanPlanningArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof QuasiUrbanPlanningArea)
                getImporter(QuasiUrbanPlanningAreaImporter.class).doImport((QuasiUrbanPlanningArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof AreaClassification)
                getImporter(AreaClassificationImporter.class).doImport((AreaClassification) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistrictsAndZones)
                getImporter(DistrictsAndZonesImporter.class).doImport((DistrictsAndZones) object, objectId, objectType, foreignKeys);
            else if (object instanceof ProjectPromotionArea)
                getImporter(ProjectPromotionAreaImporter.class).doImport((ProjectPromotionArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanDisasterRecoveryPromotionArea)
                getImporter(UrbanDisasterRecoveryPromotionAreaImporter.class).doImport((UrbanDisasterRecoveryPromotionArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanDevelopmentProject)
                getImporter(UrbanDevelopmentProjectImporter.class).doImport((UrbanDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof ScheduledAreaForUrbanDevelopmentProject)
                getImporter(ScheduledAreaForUrbanDevelopmentProjectImporter.class).doImport((ScheduledAreaForUrbanDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanFacility)
                getImporter(UrbanFacilityImporter.class).doImport((UrbanFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof AbstractDistrictPlan)
                getImporter(AbstractDistrictPlanImporter.class).doImport((AbstractDistrictPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof DistrictDevelopmentPlan)
                getImporter(DistrictDevelopmentPlanImporter.class).doImport((DistrictDevelopmentPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof District)
                getImporter(DistrictImporter.class).doImport((District) object, objectId, objectType, foreignKeys);
            else if (object instanceof ZonalDisasterPreventionFacility)
                getImporter(ZonalDisasterPreventionFacilityImporter.class).doImport((ZonalDisasterPreventionFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrgentUrbanRenewalArea)
                getImporter(UrgentUrbanRenewalAreaImporter.class).doImport((UrgentUrbanRenewalArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof PrivateUrbanRenewalProjectPlan)
                getImporter(PrivateUrbanRenewalProjectPlanImporter.class).doImport((PrivateUrbanRenewalProjectPlan) object, objectId, objectType, foreignKeys);
            else if (object instanceof GlobalHubCityDevelopmentProject)
                getImporter(GlobalHubCityDevelopmentProjectImporter.class).doImport((GlobalHubCityDevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof DevelopmentProject)
                getImporter(DevelopmentProjectImporter.class).doImport((DevelopmentProject) object, objectId, objectType, foreignKeys);
            else if (object instanceof SedimentDisasterProneArea)
                getImporter(SedimentDisasterProneAreaImporter.class).doImport((SedimentDisasterProneArea) object, objectId, objectType, foreignKeys);
            else if (object instanceof Zone)
                getImporter(ZoneImporter.class).doImport((Zone) object, objectId, objectType, foreignKeys);
            else if (object instanceof Boundary)
                getImporter(BoundaryImporter.class).doImport((Boundary) object, objectId, objectType, foreignKeys);
            else if (object instanceof ThreeDimensionalExtent)
                getImporter(ThreeDimensionalExtentImporter.class).doImport((ThreeDimensionalExtent) object, objectId, objectType, foreignKeys);
            else if (object instanceof CensusBlock)
                getImporter(CensusBlockImporter.class).doImport((CensusBlock) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterDamage)
                getImporter(DisasterDamageImporter.class).doImport((DisasterDamage) object, objectId, objectType, foreignKeys);
            else if (object instanceof Pollution)
                getImporter(PollutionImporter.class).doImport((Pollution) object, objectId, objectType, foreignKeys);
            else if (object instanceof DisasterPreventionBase)
                getImporter(DisasterPreventionBaseImporter.class).doImport((DisasterPreventionBase) object, objectId, objectType, foreignKeys);
            else if (object instanceof Recreations)
                getImporter(RecreationsImporter.class).doImport((Recreations) object, objectId, objectType, foreignKeys);
            else if (object instanceof Urbanization)
                getImporter(UrbanizationImporter.class).doImport((Urbanization) object, objectId, objectType, foreignKeys);
            else if (object instanceof PublicTransitFacility)
                getImporter(PublicTransitFacilityImporter.class).doImport((PublicTransitFacility) object, objectId, objectType, foreignKeys);
            else if (object instanceof UrbanFunction)
                getImporter(UrbanFunctionImporter.class).doImport((UrbanFunction) object, objectId, objectType, foreignKeys);
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
        }
    }

    @Override
    public void importGenericApplicationProperties(ADEPropertyCollection properties, AbstractFeature parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        if (parent instanceof AbstractBuilding)
            getImporter(BuildingPropertiesImporter.class).doImport(properties, (AbstractBuilding) parent, parentId, parentType);
        if (parent instanceof LandUse)
            getImporter(LandUsePropertiesImporter.class).doImport(properties, (LandUse) parent, parentId, parentType);
        if (parent instanceof TransportationComplex)
            getImporter(TransportationComplexPropertiesImporter.class).doImport(properties, (TransportationComplex) parent, parentId, parentType);
        if (parent instanceof TrafficArea)
            getImporter(TrafficAreaPropertiesImporter.class).doImport(properties, (TrafficArea) parent, parentId, parentType);
        if (parent instanceof CityFurniture)
            getImporter(CityFurniturePropertiesImporter.class).doImport(properties, (CityFurniture) parent, parentId, parentType);
        if (parent instanceof AbstractVegetationObject)
            getImporter(VegetationObjectPropertiesImporter.class).doImport(properties, (AbstractVegetationObject) parent, parentId, parentType);
        if (parent instanceof WaterBody)
            getImporter(WaterBodyPropertiesImporter.class).doImport(properties, (WaterBody) parent, parentId, parentType);
        if (parent instanceof CityObjectGroup) {
            if (properties.containsOneOf(org.citygml4j.ade.iur.model.urg.FiscalYearOfPublicationProperty.class,
                    org.citygml4j.ade.iur.model.urg.LanguageProperty.class)) {
                getImporter(org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter.class).doImport(properties, (CityObjectGroup) parent, parentId, parentType);
            }
            if (properties.containsOneOf(org.citygml4j.ade.iur.model.uro.FiscalYearOfPublicationProperty.class,
                    org.citygml4j.ade.iur.model.uro.LanguageProperty.class)) {
                getImporter(org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter.class).doImport(properties, (CityObjectGroup) parent, parentId, parentType);
            }
            if (properties.contains(DataTypeProperty.class))
                getImporter(org.citydb.ade.iur.importer.urt.CityObjectGroupPropertiesImporter.class).doImport(properties, (CityObjectGroup) parent, parentId, parentType);
        }
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
                if (type == UrbanRoadAttributeImporter.class)
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
                else if (type == ParkAttributeImporter.class)
                    importer = new ParkAttributeImporter(connection, helper, this);
                else if (type == WaterWorksAttributeImporter.class)
                    importer = new WaterWorksAttributeImporter(connection, helper, this);
                else if (type == SewerSystemAttributeImporter.class)
                    importer = new SewerSystemAttributeImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.urf.NumberOfHouseholdsImporter.class)
                    importer = new org.citydb.ade.iur.importer.urf.NumberOfHouseholdsImporter(connection, helper, this);
                else if (type == UseDistrictImporter.class)
                    importer = new UseDistrictImporter(connection, helper, this);
                else if (type == SpecialUseDistrictImporter.class)
                    importer = new SpecialUseDistrictImporter(connection, helper, this);
                else if (type == SpecialUseRestrictionDistrictImporter.class)
                    importer = new SpecialUseRestrictionDistrictImporter(connection, helper, this);
                else if (type == ExceptionalFloorAreaRateDistrictImporter.class)
                    importer = new ExceptionalFloorAreaRateDistrictImporter(connection, helper, this);
                else if (type == HighRiseResidentialAttractionDistrictImporter.class)
                    importer = new HighRiseResidentialAttractionDistrictImporter(connection, helper, this);
                else if (type == HeightControlDistrictImporter.class)
                    importer = new HeightControlDistrictImporter(connection, helper, this);
                else if (type == HighLevelUseDistrictImporter.class)
                    importer = new HighLevelUseDistrictImporter(connection, helper, this);
                else if (type == SpecifiedBlockImporter.class)
                    importer = new SpecifiedBlockImporter(connection, helper, this);
                else if (type == SpecialUrbanRenaissanceDistrictImporter.class)
                    importer = new SpecialUrbanRenaissanceDistrictImporter(connection, helper, this);
                else if (type == ResidentialEnvironmentImprovementDistrictImporter.class)
                    importer = new ResidentialEnvironmentImprovementDistrictImporter(connection, helper, this);
                else if (type == SpecialUseAttractionDistrictImporter.class)
                    importer = new SpecialUseAttractionDistrictImporter(connection, helper, this);
                else if (type == SpecifiedDisasterPreventionBlockImprovementZoneImporter.class)
                    importer = new SpecifiedDisasterPreventionBlockImprovementZoneImporter(connection, helper, this);
                else if (type == LandscapeZoneImporter.class)
                    importer = new LandscapeZoneImporter(connection, helper, this);
                else if (type == ScenicDistrictImporter.class)
                    importer = new ScenicDistrictImporter(connection, helper, this);
                else if (type == PortZoneImporter.class)
                    importer = new PortZoneImporter(connection, helper, this);
                else if (type == SpecialGreenSpaceConservationDistrictImporter.class)
                    importer = new SpecialGreenSpaceConservationDistrictImporter(connection, helper, this);
                else if (type == TreePlantingDistrictImporter.class)
                    importer = new TreePlantingDistrictImporter(connection, helper, this);
                else if (type == DistributionBusinessZoneImporter.class)
                    importer = new DistributionBusinessZoneImporter(connection, helper, this);
                else if (type == ProductiveGreenZoneImporter.class)
                    importer = new ProductiveGreenZoneImporter(connection, helper, this);
                else if (type == UrbanRedevelopmentPromotionAreaImporter.class)
                    importer = new UrbanRedevelopmentPromotionAreaImporter(connection, helper, this);
                else if (type == LandReadjustmentProjectImporter.class)
                    importer = new LandReadjustmentProjectImporter(connection, helper, this);
                else if (type == NewHousingAndUrbanDevelopmentProjectImporter.class)
                    importer = new NewHousingAndUrbanDevelopmentProjectImporter(connection, helper, this);
                else if (type == IndustrialParkDevelopmentProjectImporter.class)
                    importer = new IndustrialParkDevelopmentProjectImporter(connection, helper, this);
                else if (type == UrbanRedevelopmentProjectImporter.class)
                    importer = new UrbanRedevelopmentProjectImporter(connection, helper, this);
                else if (type == NewUrbanInfrastructureProjectImporter.class)
                    importer = new NewUrbanInfrastructureProjectImporter(connection, helper, this);
                else if (type == ResidentialBlockConstructionProjectImporter.class)
                    importer = new ResidentialBlockConstructionProjectImporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementProjectImporter.class)
                    importer = new DisasterPreventionBlockImprovementProjectImporter(connection, helper, this);
                else if (type == UrbanRenewalProjectImporter.class)
                    importer = new UrbanRenewalProjectImporter(connection, helper, this);
                else if (type == TrafficFacilityImporter.class)
                    importer = new TrafficFacilityImporter(connection, helper, this);
                else if (type == OpenSpaceForPublicUseImporter.class)
                    importer = new OpenSpaceForPublicUseImporter(connection, helper, this);
                else if (type == SupplyFacilityImporter.class)
                    importer = new SupplyFacilityImporter(connection, helper, this);
                else if (type == TreatmentFacilityImporter.class)
                    importer = new TreatmentFacilityImporter(connection, helper, this);
                else if (type == WaterwayImporter.class)
                    importer = new WaterwayImporter(connection, helper, this);
                else if (type == CollectiveHousingFacilitiesImporter.class)
                    importer = new CollectiveHousingFacilitiesImporter(connection, helper, this);
                else if (type == CollectiveGovernmentAndPublicOfficeFacilitiesImporter.class)
                    importer = new CollectiveGovernmentAndPublicOfficeFacilitiesImporter(connection, helper, this);
                else if (type == CollectiveUrbanDisasterPreventionFacilitiesImporter.class)
                    importer = new CollectiveUrbanDisasterPreventionFacilitiesImporter(connection, helper, this);
                else if (type == DistributionBusinessParkImporter.class)
                    importer = new DistributionBusinessParkImporter(connection, helper, this);
                else if (type == UrbanFacilityStipulatedByCabinetOrderImporter.class)
                    importer = new UrbanFacilityStipulatedByCabinetOrderImporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForTsunamiDisasterPreventionImporter.class)
                    importer = new CollectiveFacilitiesForTsunamiDisasterPreventionImporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForReconstructionAndRevitalizationImporter.class)
                    importer = new CollectiveFacilitiesForReconstructionAndRevitalizationImporter(connection, helper, this);
                else if (type == CollectiveFacilitiesForReconstructionImporter.class)
                    importer = new CollectiveFacilitiesForReconstructionImporter(connection, helper, this);
                else if (type == DistrictPlanImporter.class)
                    importer = new DistrictPlanImporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementZonePlanImporter.class)
                    importer = new DisasterPreventionBlockImprovementZonePlanImporter(connection, helper, this);
                else if (type == DisasterPreventionBlockImprovementZonePlanToZonalDisasterPreventionFacilityImporter.class)
                    importer = new DisasterPreventionBlockImprovementZonePlanToZonalDisasterPreventionFacilityImporter(connection, helper, this);
                else if (type == HistoricSceneryMaintenanceAndImprovementDistrictPlanImporter.class)
                    importer = new HistoricSceneryMaintenanceAndImprovementDistrictPlanImporter(connection, helper, this);
                else if (type == RoadsideDistrictPlanImporter.class)
                    importer = new RoadsideDistrictPlanImporter(connection, helper, this);
                else if (type == RoadsideDistrictImprovementPlanImporter.class)
                    importer = new RoadsideDistrictImprovementPlanImporter(connection, helper, this);
                else if (type == RuralDistrictImprovementPlanImporter.class)
                    importer = new RuralDistrictImprovementPlanImporter(connection, helper, this);
                else if (type == SpecifiedUrgentUrbanRenewalAreaImporter.class)
                    importer = new SpecifiedUrgentUrbanRenewalAreaImporter(connection, helper, this);
                else if (type == SpecifiedUrgentUrbanRenewalAreaToGlobalHubCityDevelopmentProjectImporter.class)
                    importer = new SpecifiedUrgentUrbanRenewalAreaToGlobalHubCityDevelopmentProjectImporter(connection, helper, this);
                else if (type == UrbanPlanningAreaImporter.class)
                    importer = new UrbanPlanningAreaImporter(connection, helper, this);
                else if (type == QuasiUrbanPlanningAreaImporter.class)
                    importer = new QuasiUrbanPlanningAreaImporter(connection, helper, this);
                else if (type == AreaClassificationImporter.class)
                    importer = new AreaClassificationImporter(connection, helper, this);
                else if (type == DistrictsAndZonesImporter.class)
                    importer = new DistrictsAndZonesImporter(connection, helper, this);
                else if (type == ProjectPromotionAreaImporter.class)
                    importer = new ProjectPromotionAreaImporter(connection, helper, this);
                else if (type == UrbanDisasterRecoveryPromotionAreaImporter.class)
                    importer = new UrbanDisasterRecoveryPromotionAreaImporter(connection, helper, this);
                else if (type == UrbanDevelopmentProjectImporter.class)
                    importer = new UrbanDevelopmentProjectImporter(connection, helper, this);
                else if (type == ScheduledAreaForUrbanDevelopmentProjectImporter.class)
                    importer = new ScheduledAreaForUrbanDevelopmentProjectImporter(connection, helper, this);
                else if (type == UrbanFacilityImporter.class)
                    importer = new UrbanFacilityImporter(connection, helper, this);
                else if (type == UrbanFacilityToThreeDimensionalExtentImporter.class)
                    importer = new UrbanFacilityToThreeDimensionalExtentImporter(connection, helper, this);
                else if (type == AbstractDistrictPlanImporter.class)
                    importer = new AbstractDistrictPlanImporter(connection, helper, this);
                else if (type == AbstractDistrictPlanToDistrictDevelopmentPlanImporter.class)
                    importer = new AbstractDistrictPlanToDistrictDevelopmentPlanImporter(connection, helper, this);
                else if (type == AbstractDistrictPlanToPromotionDistrictImporter.class)
                    importer = new AbstractDistrictPlanToPromotionDistrictImporter(connection, helper, this);
                else if (type == DistrictDevelopmentPlanImporter.class)
                    importer = new DistrictDevelopmentPlanImporter(connection, helper, this);
                else if (type == DistrictDevelopmentPlanToDistrictFacilityImporter.class)
                    importer = new DistrictDevelopmentPlanToDistrictFacilityImporter(connection, helper, this);
                else if (type == DistrictDevelopmentPlanToDistrictImporter.class)
                    importer = new DistrictDevelopmentPlanToDistrictImporter(connection, helper, this);
                else if (type == DistrictImporter.class)
                    importer = new DistrictImporter(connection, helper, this);
                else if (type == ZonalDisasterPreventionFacilityImporter.class)
                    importer = new ZonalDisasterPreventionFacilityImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaImporter.class)
                    importer = new UrgentUrbanRenewalAreaImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter.class)
                    importer = new UrgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter.class)
                    importer = new UrgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter(connection, helper, this);
                else if (type == UrgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter.class)
                    importer = new UrgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter(connection, helper, this);
                else if (type == PrivateUrbanRenewalProjectPlanImporter.class)
                    importer = new PrivateUrbanRenewalProjectPlanImporter(connection, helper, this);
                else if (type == GlobalHubCityDevelopmentProjectImporter.class)
                    importer = new GlobalHubCityDevelopmentProjectImporter(connection, helper, this);
                else if (type == DevelopmentProjectImporter.class)
                    importer = new DevelopmentProjectImporter(connection, helper, this);
                else if (type == SedimentDisasterProneAreaImporter.class)
                    importer = new SedimentDisasterProneAreaImporter(connection, helper, this);
                else if (type == ZoneImporter.class)
                    importer = new ZoneImporter(connection, helper, this);
                else if (type == ZoneToBoundaryImporter.class)
                    importer = new ZoneToBoundaryImporter(connection, helper, this);
                else if (type == BoundaryImporter.class)
                    importer = new BoundaryImporter(connection, helper, this);
                else if (type == ThreeDimensionalExtentImporter.class)
                    importer = new ThreeDimensionalExtentImporter(connection, helper, this);
                else if (type == CensusBlockImporter.class)
                    importer = new CensusBlockImporter(connection, helper, this);
                else if (type == DisasterDamageImporter.class)
                    importer = new DisasterDamageImporter(connection, helper, this);
                else if (type == PollutionImporter.class)
                    importer = new PollutionImporter(connection, helper, this);
                else if (type == DisasterPreventionBaseImporter.class)
                    importer = new DisasterPreventionBaseImporter(connection, helper, this);
                else if (type == RecreationsImporter.class)
                    importer = new RecreationsImporter(connection, helper, this);
                else if (type == UrbanizationImporter.class)
                    importer = new UrbanizationImporter(connection, helper, this);
                else if (type == PublicTransitFacilityImporter.class)
                    importer = new PublicTransitFacilityImporter(connection, helper, this);
                else if (type == UrbanFunctionImporter.class)
                    importer = new UrbanFunctionImporter(connection, helper, this);
                else if (type == UrbanFunctionToCityObjectImporter.class)
                    importer = new UrbanFunctionToCityObjectImporter(connection, helper, this);
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
                else if (type == LandUsePropertiesImporter.class)
                    importer = new LandUsePropertiesImporter(connection, helper, this);
                else if (type == TransportationComplexPropertiesImporter.class)
                    importer = new TransportationComplexPropertiesImporter(connection, helper, this);
                else if (type == TrafficAreaPropertiesImporter.class)
                    importer = new TrafficAreaPropertiesImporter(connection, helper, this);
                else if (type == CityFurniturePropertiesImporter.class)
                    importer = new CityFurniturePropertiesImporter(connection, helper, this);
                else if (type == VegetationObjectPropertiesImporter.class)
                    importer = new VegetationObjectPropertiesImporter(connection, helper, this);
                else if (type == WaterBodyPropertiesImporter.class)
                    importer = new WaterBodyPropertiesImporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter.class)
                    importer = new org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter(connection, helper, this);
                else if (type == BuildingAttributeImporter.class)
                    importer = new BuildingAttributeImporter(connection, helper, this);
                else if (type == BuildingIDAttributeImporter.class)
                    importer = new BuildingIDAttributeImporter(connection, helper, this);
                else if (type == BuildingDetailAttributeImporter.class)
                    importer = new BuildingDetailAttributeImporter(connection, helper, this);
                else if (type == LargeCustomerFacilityAttributeImporter.class)
                    importer = new LargeCustomerFacilityAttributeImporter(connection, helper, this);
                else if (type == BuildingDisasterRiskAttributeImporter.class)
                    importer = new BuildingDisasterRiskAttributeImporter(connection, helper, this);
                else if (type == BuildingFloodingRiskAttributeImporter.class)
                    importer = new BuildingFloodingRiskAttributeImporter(connection, helper, this);
                else if (type == BuildingRiverFloodingRiskAttributeImporter.class)
                    importer = new BuildingRiverFloodingRiskAttributeImporter(connection, helper, this);
                else if (type == KeyValuePairAttributeImporter.class)
                    importer = new KeyValuePairAttributeImporter(connection, helper, this);
                else if (type == BuildingDataQualityAttributeImporter.class)
                    importer = new BuildingDataQualityAttributeImporter(connection, helper, this);
                else if (type == LandUseDetailAttributeImporter.class)
                    importer = new LandUseDetailAttributeImporter(connection, helper, this);
                else if (type == RoadAttributeImporter.class)
                    importer = new RoadAttributeImporter(connection, helper, this);
                else if (type == TrafficVolumeAttributeImporter.class)
                    importer = new TrafficVolumeAttributeImporter(connection, helper, this);
                else if (type == RoadStructureAttributeImporter.class)
                    importer = new RoadStructureAttributeImporter(connection, helper, this);
                else if (type == RoadDataQualityAttributeImporter.class)
                    importer = new RoadDataQualityAttributeImporter(connection, helper, this);
                else if (type == TrafficAreaStructureAttributeImporter.class)
                    importer = new TrafficAreaStructureAttributeImporter(connection, helper, this);
                else if (type == CityFurnitureAttributeImporter.class)
                    importer = new CityFurnitureAttributeImporter(connection, helper, this);
                else if (type == CityFurnitureDetailAttributeImporter.class)
                    importer = new CityFurnitureDetailAttributeImporter(connection, helper, this);
                else if (type == CityFurnitureDataQualityAttributeImporter.class)
                    importer = new CityFurnitureDataQualityAttributeImporter(connection, helper, this);
                else if (type == VegetationDataQualityAttributeImporter.class)
                    importer = new VegetationDataQualityAttributeImporter(connection, helper, this);
                else if (type == WaterBodyAttributeImporter.class)
                    importer = new WaterBodyAttributeImporter(connection, helper, this);
                else if (type == WaterBodyRiverFloodingRiskAttributeImporter.class)
                    importer = new WaterBodyRiverFloodingRiskAttributeImporter(connection, helper, this);
                else if (type == BuildingToBuildingDisasterRiskAttributeImporter.class)
                    importer = new BuildingToBuildingDisasterRiskAttributeImporter(connection, helper, this);
                else if (type == BuildingToKeyValuePairAttributeImporter.class)
                    importer = new BuildingToKeyValuePairAttributeImporter(connection, helper, this);
                else if (type == WaterBodyToWaterBodyFloodingRiskAttributeImporter.class)
                    importer = new WaterBodyToWaterBodyFloodingRiskAttributeImporter(connection, helper, this);
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
