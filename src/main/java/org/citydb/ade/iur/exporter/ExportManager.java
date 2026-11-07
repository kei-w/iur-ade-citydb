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
import org.citygml4j.model.citygml.cityfurniture.CityFurniture;
import org.citygml4j.model.citygml.landuse.LandUse;
import org.citygml4j.model.citygml.relief.ReliefFeature;
import org.citygml4j.model.citygml.transportation.TrafficArea;
import org.citygml4j.model.citygml.transportation.TransportationComplex;
import org.citygml4j.model.citygml.tunnel.AbstractTunnel;
import org.citygml4j.model.citygml.vegetation.AbstractVegetationObject;
import org.citygml4j.model.citygml.waterbody.WaterBody;
import org.citygml4j.model.citygml.relief.AbstractReliefComponent;
import org.citygml4j.model.citygml.building.BuildingFurniture;
import org.citygml4j.model.citygml.building.BuildingInstallation;
import org.citygml4j.model.citygml.building.IntBuildingInstallation;
import org.citygml4j.model.citygml.building.Room;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;
import org.citygml4j.ade.iur.model.uro.AbstractConstruction;
import org.citygml4j.ade.iur.model.uro.Appurtenance;
import org.citygml4j.ade.iur.model.uro.Cable;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.ade.iur.model.uro.OtherConstruction;
import org.citygml4j.ade.iur.model.uro.Pipe;
import org.citygml4j.ade.iur.model.uro.PublicLand;
import org.citygml4j.ade.iur.model.uro.SewerPipe;
import org.citygml4j.ade.iur.model.uro.UtilityLink;
import org.citygml4j.ade.iur.model.uro.UtilityNodeContainer;
import org.citygml4j.ade.iur.model.uro.Waterway;
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
        if (UrbanFunctionModule.v1_4.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof CensusBlock)
                getExporter(CensusBlockExporter.class).doExport((CensusBlock) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterDamage)
                getExporter(DisasterDamageExporter.class).doExport((DisasterDamage) object, objectId, objectType, projectionFilter);
            else if (object instanceof DisasterPreventionBase)
                getExporter(DisasterPreventionBaseExporter.class).doExport((DisasterPreventionBase) object, objectId, objectType, projectionFilter);
            else if (object instanceof Pollution)
                getExporter(PollutionExporter.class).doExport((Pollution) object, objectId, objectType, projectionFilter);
            else if (object instanceof PublicTransportationFacility)
                getExporter(PublicTransportationFacilityExporter.class).doExport((PublicTransportationFacility) object, objectId, objectType, projectionFilter);
            else if (object instanceof Recreations)
                getExporter(RecreationsExporter.class).doExport((Recreations) object, objectId, objectType, projectionFilter);
            else if (object instanceof Urbanization)
                getExporter(UrbanizationExporter.class).doExport((Urbanization) object, objectId, objectType, projectionFilter);
            else if (object instanceof DevelopmentProject)
                getExporter(DevelopmentProjectExporter.class).doExport((DevelopmentProject) object, objectId, objectType, projectionFilter);
            else if (object instanceof Zone)
                getExporter(ZoneExporter.class).doExport((Zone) object, objectId, objectType, projectionFilter);
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
        } else if (UrbanObjectModule.v3_1.getModelPackageName().equals(object.getClass().getPackage().getName())) {
            if (object instanceof SewerPipe)
                getExporter(SewerPipeExporter.class).doExport((SewerPipe) object, objectId, objectType, projectionFilter);
            else if (object instanceof Pipe)
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
            else if (object instanceof Waterway)
                getExporter(WaterwayExporter.class).doExport((Waterway) object, objectId, objectType, projectionFilter);
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
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.BUILDING)) && parent instanceof AbstractBuilding)
            getExporter(BuildingPropertiesExporter.class).doExport((AbstractBuilding) parent, parentId, parentType, projectionFilter);
        if (parent instanceof CityObjectGroup) {
            if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP)))
                getExporter(org.citydb.ade.iur.exporter.urg.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
            else if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_1)))
                getExporter(org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
            else if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_2)))
                getExporter(org.citydb.ade.iur.exporter.urf.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
            else if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_3)))
                getExporter(org.citydb.ade.iur.exporter.urt.CityObjectGroupPropertiesExporter.class).doExport((CityObjectGroup) parent, parentId, parentType, projectionFilter);
        }
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.LAND_USE)) && parent instanceof LandUse)
            getExporter(LandUsePropertiesExporter.class).doExport((LandUse) parent, parentId, parentType, projectionFilter);
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
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITY_FURNITURE)) && parent instanceof CityFurniture)
            getExporter(CityFurniturePropertiesExporter.class).doExport((CityFurniture) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.RELIEF_COMPONENT)) && parent instanceof AbstractReliefComponent)
            getExporter(ReliefComponentPropertiesExporter.class).doExport((AbstractReliefComponent) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.RELIEF_FEATURE)) && parent instanceof ReliefFeature)
            getExporter(ReliefFeaturePropertiesExporter.class).doExport((ReliefFeature) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TRANSPORTATION_COMPLEX)) && parent instanceof TransportationComplex)
            getExporter(TransportationComplexPropertiesExporter.class).doExport((TransportationComplex) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TRAFFIC_AREA)) && parent instanceof TrafficArea)
            getExporter(TrafficAreaPropertiesExporter.class).doExport((TrafficArea) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.TUNNEL)) && parent instanceof AbstractTunnel)
            getExporter(TunnelPropertiesExporter.class).doExport((AbstractTunnel) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.CITYOBJECT)) && parent instanceof AbstractVegetationObject)
            getExporter(VegetationObjectPropertiesExporter.class).doExport((AbstractVegetationObject) parent, parentId, parentType, projectionFilter);
        if (adeHookTable.equals(schemaMapper.getTableName(ADETable.WATERBODY)) && parent instanceof WaterBody)
            getExporter(WaterBodyPropertiesExporter.class).doExport((WaterBody) parent, parentId, parentType, projectionFilter);
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
                if (type == UrbanFunctionExporter.class)
                    exporter = new UrbanFunctionExporter(connection, helper, this);
                else if (type == DevelopmentProjectExporter.class)
                    exporter = new DevelopmentProjectExporter(connection, helper, this);
                else if (type == CensusBlockExporter.class)
                    exporter = new CensusBlockExporter(connection, helper, this);
                else if (type == DisasterDamageExporter.class)
                    exporter = new DisasterDamageExporter(connection, helper, this);
                else if (type == DisasterPreventionBaseExporter.class)
                    exporter = new DisasterPreventionBaseExporter(connection, helper, this);
                else if (type == PollutionExporter.class)
                    exporter = new PollutionExporter(connection, helper, this);
                else if (type == PublicTransportationFacilityExporter.class)
                    exporter = new PublicTransportationFacilityExporter(connection, helper, this);
                else if (type == RecreationsExporter.class)
                    exporter = new RecreationsExporter(connection, helper, this);
                else if (type == UrbanizationExporter.class)
                    exporter = new UrbanizationExporter(connection, helper, this);
                else if (type == ZoneExporter.class)
                    exporter = new ZoneExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.urf.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.urf.CityObjectGroupPropertiesExporter(connection, helper, this);
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
                if (type == BridgePropertiesExporter.class)
                    exporter = new BridgePropertiesExporter(connection, helper, this);
                else if (type == BuildingPropertiesExporter.class)
                    exporter = new BuildingPropertiesExporter(connection, helper, this);
                else if (type == org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter.class)
                    exporter = new org.citydb.ade.iur.exporter.uro.CityObjectGroupPropertiesExporter(connection, helper, this);
                else if (type == LandUsePropertiesExporter.class)
                    exporter = new LandUsePropertiesExporter(connection, helper, this);
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
                else if (type == CityFurniturePropertiesExporter.class)
                    exporter = new CityFurniturePropertiesExporter(connection, helper, this);
                else if (type == ReliefComponentPropertiesExporter.class)
                    exporter = new ReliefComponentPropertiesExporter(connection, helper, this);
                else if (type == ReliefFeaturePropertiesExporter.class)
                    exporter = new ReliefFeaturePropertiesExporter(connection, helper, this);
                else if (type == TransportationComplexPropertiesExporter.class)
                    exporter = new TransportationComplexPropertiesExporter(connection, helper, this);
                else if (type == TrafficAreaPropertiesExporter.class)
                    exporter = new TrafficAreaPropertiesExporter(connection, helper, this);
                else if (type == TunnelPropertiesExporter.class)
                    exporter = new TunnelPropertiesExporter(connection, helper, this);
                else if (type == VegetationObjectPropertiesExporter.class)
                    exporter = new VegetationObjectPropertiesExporter(connection, helper, this);
                else if (type == WaterBodyPropertiesExporter.class)
                    exporter = new WaterBodyPropertiesExporter(connection, helper, this);
                else if (type == ConstructionBaseAttributeExporter.class)
                    exporter = new ConstructionBaseAttributeExporter(connection, helper, this);
                else if (type == FacilityIdAttributeExporter.class)
                    exporter = new FacilityIdAttributeExporter(connection, helper, this);
                else if (type == FacilityTypeAttributeExporter.class)
                    exporter = new FacilityTypeAttributeExporter(connection, helper, this);
                else if (type == FacilityAttributeExporter.class)
                    exporter = new FacilityAttributeExporter(connection, helper, this);
                else if (type == NumberOfFacilitiesExporter.class)
                    exporter = new NumberOfFacilitiesExporter(connection, helper, this);
                else if (type == RepairsBeforeParkHealthAssessmentExporter.class)
                    exporter = new RepairsBeforeParkHealthAssessmentExporter(connection, helper, this);
                else if (type == ParkHealthAssessmentExporter.class)
                    exporter = new ParkHealthAssessmentExporter(connection, helper, this);
                else if (type == LongevityMeasuresExporter.class)
                    exporter = new LongevityMeasuresExporter(connection, helper, this);
                else if (type == CountermeasuresCostExporter.class)
                    exporter = new CountermeasuresCostExporter(connection, helper, this);
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
                else if (type == DataQualityAttributeExporter.class)
                    exporter = new DataQualityAttributeExporter(connection, helper, this);
                else if (type == PublicSurveyDataQualityAttributeExporter.class)
                    exporter = new PublicSurveyDataQualityAttributeExporter(connection, helper, this);
                else if (type == KeyValuePairAttributeExporter.class)
                    exporter = new KeyValuePairAttributeExporter(connection, helper, this);
                else if (type == BuildingIDAttributeExporter.class)
                    exporter = new BuildingIDAttributeExporter(connection, helper, this);
                else if (type == BuildingDetailAttributeExporter.class)
                    exporter = new BuildingDetailAttributeExporter(connection, helper, this);
                else if (type == LargeCustomerFacilityAttributeExporter.class)
                    exporter = new LargeCustomerFacilityAttributeExporter(connection, helper, this);
                else if (type == RealEstateIDAttributeExporter.class)
                    exporter = new RealEstateIDAttributeExporter(connection, helper, this);
                else if (type == BuildingUsecaseAttributeExporter.class)
                    exporter = new BuildingUsecaseAttributeExporter(connection, helper, this);
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
                else if (type == IndoorAttributeExporter.class)
                    exporter = new IndoorAttributeExporter(connection, helper, this);
                else if (type == UserDefinedValueExporter.class)
                    exporter = new UserDefinedValueExporter(connection, helper, this);
                else if (type == CityFurnitureDetailAttributeExporter.class)
                    exporter = new CityFurnitureDetailAttributeExporter(connection, helper, this);
                else if (type == LandUseDetailAttributeExporter.class)
                    exporter = new LandUseDetailAttributeExporter(connection, helper, this);
                else if (type == TrafficObjectUsecaseAttributeExporter.class)
                    exporter = new TrafficObjectUsecaseAttributeExporter(connection, helper, this);
                else if (type == TrafficVolumeAttributeExporter.class)
                    exporter = new TrafficVolumeAttributeExporter(connection, helper, this);
                else if (type == RoadStructureAttributeExporter.class)
                    exporter = new RoadStructureAttributeExporter(connection, helper, this);
                else if (type == RoadTypeExporter.class)
                    exporter = new RoadTypeExporter(connection, helper, this);
                else if (type == RailwayRouteAttributeExporter.class)
                    exporter = new RailwayRouteAttributeExporter(connection, helper, this);
                else if (type == TrackAttributeExporter.class)
                    exporter = new TrackAttributeExporter(connection, helper, this);
                else if (type == SquareUrbanPlanAttributeExporter.class)
                    exporter = new SquareUrbanPlanAttributeExporter(connection, helper, this);
                else if (type == RailwayTrackAttributeExporter.class)
                    exporter = new RailwayTrackAttributeExporter(connection, helper, this);
                else if (type == ControlPointExporter.class)
                    exporter = new ControlPointExporter(connection, helper, this);
                else if (type == ControlPointParameterExporter.class)
                    exporter = new ControlPointParameterExporter(connection, helper, this);
                else if (type == CircularCurveTypeExporter.class)
                    exporter = new CircularCurveTypeExporter(connection, helper, this);
                else if (type == TransitionCurveTypeExporter.class)
                    exporter = new TransitionCurveTypeExporter(connection, helper, this);
                else if (type == SlopeTypeExporter.class)
                    exporter = new SlopeTypeExporter(connection, helper, this);
                else if (type == VerticalCurveTypeExporter.class)
                    exporter = new VerticalCurveTypeExporter(connection, helper, this);
                else if (type == TrafficAreaStructureAttributeExporter.class)
                    exporter = new TrafficAreaStructureAttributeExporter(connection, helper, this);
                else if (type == TunnelStructureAttributeExporter.class)
                    exporter = new TunnelStructureAttributeExporter(connection, helper, this);
                else if (type == TunnelFunctionalAttributeExporter.class)
                    exporter = new TunnelFunctionalAttributeExporter(connection, helper, this);
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
                else if (type == SewerPipeExporter.class)
                    exporter = new SewerPipeExporter(connection, helper, this);
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
                else if (type == WaterwayExporter.class)
                    exporter = new WaterwayExporter(connection, helper, this);
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
