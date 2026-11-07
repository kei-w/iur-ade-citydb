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
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface;
import org.citygml4j.ade.iur.model.uro.AbstractBoundarySurfaceProperty;
import org.citygml4j.ade.iur.model.uro.AbstractConstruction;
import org.citygml4j.ade.iur.model.uro.ConditionOfConstructionValue;
import org.citygml4j.ade.iur.model.uro.ConstructionBaseAttribute;
import org.citygml4j.ade.iur.model.uro.ConstructionBaseAttributeProperty;
import org.citygml4j.ade.iur.model.uro.ConstructionDataQualityAttribute;
import org.citygml4j.ade.iur.model.uro.ConstructionDataQualityAttributeProperty;
import org.citygml4j.ade.iur.model.uro.ConstructionEvent;
import org.citygml4j.ade.iur.model.uro.ConstructionEventProperty;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallationProperty;
import org.citygml4j.ade.iur.model.uro.ConstructionStructureAttribute;
import org.citygml4j.ade.iur.model.uro.ConstructionStructureAttributeProperty;
import org.citygml4j.ade.iur.model.uro.DisasterRiskAttribute;
import org.citygml4j.ade.iur.model.uro.DisasterRiskAttributeProperty;
import org.citygml4j.ade.iur.model.uro.DmAttribute;
import org.citygml4j.ade.iur.model.uro.DmAttributeProperty;
import org.citygml4j.ade.iur.model.uro.Elevation;
import org.citygml4j.ade.iur.model.uro.ElevationProperty;
import org.citygml4j.ade.iur.model.uro.FacilityAttribute;
import org.citygml4j.ade.iur.model.uro.FacilityAttributeProperty;
import org.citygml4j.ade.iur.model.uro.FacilityIdAttribute;
import org.citygml4j.ade.iur.model.uro.FacilityIdAttributeProperty;
import org.citygml4j.ade.iur.model.uro.FacilityTypeAttribute;
import org.citygml4j.ade.iur.model.uro.FacilityTypeAttributeProperty;
import org.citygml4j.ade.iur.model.uro.Height;
import org.citygml4j.ade.iur.model.uro.HeightProperty;
import org.citygml4j.ade.iur.model.uro.Occupancy;
import org.citygml4j.ade.iur.model.uro.OccupancyProperty;

import java.sql.*;

public class AbstractConstructionExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ExportManager manager;
    private final PreparedStatement ps;
    private final PreparedStatement psConstructionEvent;
    private final PreparedStatement psElevation;
    private final PreparedStatement psHeight;
    private final PreparedStatement psOccupancy;
    private final PreparedStatement psConsFacilityTypeAttribute;
    private final PreparedStatement psConsFacilityAttribute;
    private final PreparedStatement psConsDisasterRiskAttribute;
    private final PreparedStatement psConsDmAttribute;
    private final PreparedStatement psBoundedBySurface;
    private final PreparedStatement psConstructionInstallation;
    private final ConstructionEventExporter constructionEventExporter;
    private final ElevationExporter elevationExporter;
    private final HeightExporter heightExporter;
    private final OccupancyExporter occupancyExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final ConstructionBaseAttributeExporter constructionBaseAttributeExporter;
    private final ConstructionStructureAttributeExporter constructionStructureAttributeExporter;
    private final DisasterRiskAttributeExporter disasterRiskAttributeExporter;
    private final DmAttributeExporter dmAttributeExporter;
    private final ConstructionDataQualityAttributeExporter constructionDataQualityAttributeExporter;
    private final AttributeValueSplitter valueSplitter;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public AbstractConstructionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CONSTRUCTION);
        constructionEventExporter = manager.getExporter(ConstructionEventExporter.class);
        elevationExporter = manager.getExporter(ElevationExporter.class);
        heightExporter = manager.getExporter(HeightExporter.class);
        occupancyExporter = manager.getExporter(OccupancyExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        constructionBaseAttributeExporter = manager.getExporter(ConstructionBaseAttributeExporter.class);
        constructionStructureAttributeExporter = manager.getExporter(ConstructionStructureAttributeExporter.class);
        disasterRiskAttributeExporter = manager.getExporter(DisasterRiskAttributeExporter.class);
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        constructionDataQualityAttributeExporter = manager.getExporter(ConstructionDataQualityAttributeExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select()
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table constructionEvents = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_CONSTRUCT)));
        Select constructionEventSelect = new Select().addProjection(constructionEvents.getColumn("constructionevent_id"))
                .addSelection(ComparisonFactory.equalTo(constructionEvents.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(constructionEvents.getColumn("constructionevent_id")));
        psConstructionEvent = connection.prepareStatement(constructionEventSelect.toString());
        Table elevations = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_ELEVATION)));
        Select elevationSelect = new Select().addProjection(elevations.getColumn("elevation_id"))
                .addSelection(ComparisonFactory.equalTo(elevations.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(elevations.getColumn("elevation_id")));
        psElevation = connection.prepareStatement(elevationSelect.toString());
        Table heights = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_HEIGHT)));
        Select heightSelect = new Select().addProjection(heights.getColumn("height_id"))
                .addSelection(ComparisonFactory.equalTo(heights.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(heights.getColumn("height_id")));
        psHeight = connection.prepareStatement(heightSelect.toString());
        Table occupancys = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_OCCUPANCY)));
        Select occupancySelect = new Select().addProjection(occupancys.getColumn("occupancy_id"))
                .addSelection(ComparisonFactory.equalTo(occupancys.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(occupancys.getColumn("occupancy_id")));
        psOccupancy = connection.prepareStatement(occupancySelect.toString());
        Table consFacilityTypeAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_FACILITYT)));
        Select consFacilityTypeAttributeSelect = new Select().addProjection(consFacilityTypeAttributes.getColumn("facilitytypeattribute_id"))
                .addSelection(ComparisonFactory.equalTo(consFacilityTypeAttributes.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(consFacilityTypeAttributes.getColumn("facilitytypeattribute_id")));
        psConsFacilityTypeAttribute = connection.prepareStatement(consFacilityTypeAttributeSelect.toString());
        Table consFacilityAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_FACILITYA)));
        Select consFacilityAttributeSelect = new Select().addProjection(consFacilityAttributes.getColumn("facilityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(consFacilityAttributes.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(consFacilityAttributes.getColumn("facilityattribute_id")));
        psConsFacilityAttribute = connection.prepareStatement(consFacilityAttributeSelect.toString());
        Table consDisasterRiskAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_DISASTERR)));
        Select consDisasterRiskAttributeSelect = new Select().addProjection(consDisasterRiskAttributes.getColumn("disasterriskattribute_id"))
                .addSelection(ComparisonFactory.equalTo(consDisasterRiskAttributes.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(consDisasterRiskAttributes.getColumn("disasterriskattribute_id")));
        psConsDisasterRiskAttribute = connection.prepareStatement(consDisasterRiskAttributeSelect.toString());
        Table consDmAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCT_TO_DMATTRIBU)));
        Select consDmAttributeSelect = new Select().addProjection(consDmAttributes.getColumn("dmattribute_id"))
                .addSelection(ComparisonFactory.equalTo(consDmAttributes.getColumn("construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(consDmAttributes.getColumn("dmattribute_id")));
        psConsDmAttribute = connection.prepareStatement(consDmAttributeSelect.toString());
        Table boundarySurface = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BOUNDARYSURFACE)));
        Select boundedBySurfaceSelect = new Select().addProjection(boundarySurface.getColumn("id"), cityObject.getColumn("objectclass_id"), cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, boundarySurface.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(boundarySurface.getColumn("construction_boundedby_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(boundarySurface.getColumn("id")));
        psBoundedBySurface = connection.prepareStatement(boundedBySurfaceSelect.toString());
        Table constructionInstallation = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CONSTRUCTIONINSTALLATI)));
        Select constructionInstallationSelect = new Select().addProjection(constructionInstallation.getColumn("id"), cityObject.getColumn("objectclass_id"), cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, constructionInstallation.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(constructionInstallation.getColumn("construction_construction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(constructionInstallation.getColumn("id")));
        psConstructionInstallation = connection.prepareStatement(constructionInstallationSelect.toString());
    }

    public void doExport(AbstractConstruction abstractConstruction, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                doExport(abstractConstruction, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("conditionofconstruction", prefix + "conditionofconstruction"),
                table.getColumn("dateofconstruction", prefix + "dateofconstruction"),
                table.getColumn("dateofdemolition", prefix + "dateofdemolition"),
                table.getColumn("consfacilityidattribute_id", prefix + "consfacilityidattribute_id"),
                table.getColumn("consbaseattribute_id", prefix + "consbaseattribute_id"),
                table.getColumn("consstructureattribute_id", prefix + "consstructureattribute_id"),
                table.getColumn("consdataqualityattribute_id", prefix + "consdataqualityattribute_id"),
                table.getColumn("lod0geometry_brep_id", prefix + "lod0geometry_brep_id"),
                table.getColumn("lod1geometry_brep_id", prefix + "lod1geometry_brep_id"),
                table.getColumn("lod2geometry_brep_id", prefix + "lod2geometry_brep_id"),
                table.getColumn("lod3geometry_brep_id", prefix + "lod3geometry_brep_id"),
                table.getColumn("lod4geometry_brep_id", prefix + "lod4geometry_brep_id"),
                helper.getGeometryColumn(table.getColumn("lod0geometry_other_geom"), prefix + "lod0geometry_other_geom"),
                helper.getGeometryColumn(table.getColumn("lod1geometry_other_geom"), prefix + "lod1geometry_other_geom"),
                helper.getGeometryColumn(table.getColumn("lod2geometry_other_geom"), prefix + "lod2geometry_other_geom"),
                helper.getGeometryColumn(table.getColumn("lod3geometry_other_geom"), prefix + "lod3geometry_other_geom"),
                helper.getGeometryColumn(table.getColumn("lod4geometry_other_geom"), prefix + "lod4geometry_other_geom"));

        return select;
    }

    public void doExport(AbstractConstruction abstractConstruction, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        psBoundedBySurface.setLong(1, objectId);
        try (ResultSet boundedBySurfaceRs = psBoundedBySurface.executeQuery()) {
            while (boundedBySurfaceRs.next()) {
                long boundarySurfaceId = boundedBySurfaceRs.getLong("id");
                AbstractObjectType<?> boundarySurfaceType = helper.getAbstractObjectType(boundedBySurfaceRs.getInt("objectclass_id"));
                AbstractBoundarySurface boundarySurface = helper.createObject(boundarySurfaceId, boundarySurfaceType.getObjectClassId(), AbstractBoundarySurface.class);
                if (boundarySurface != null) {
                    manager.exportObject(boundarySurface, boundarySurfaceId, boundarySurfaceType, helper.getProjectionFilter(boundarySurfaceType));
                    abstractConstruction.getBoundedBySurface().add(new AbstractBoundarySurfaceProperty(boundarySurface));
                }
            }
        }

        psConstructionInstallation.setLong(1, objectId);
        try (ResultSet constructionInstallationRs = psConstructionInstallation.executeQuery()) {
            while (constructionInstallationRs.next()) {
                long constructionInstallationId = constructionInstallationRs.getLong("id");
                AbstractObjectType<?> constructionInstallationType = helper.getAbstractObjectType(constructionInstallationRs.getInt("objectclass_id"));
                ConstructionInstallation constructionInstallation = helper.createObject(constructionInstallationId, constructionInstallationType.getObjectClassId(), ConstructionInstallation.class);
                if (constructionInstallation != null) {
                    manager.exportObject(constructionInstallation, constructionInstallationId, constructionInstallationType, helper.getProjectionFilter(constructionInstallationType));
                    abstractConstruction.getConstructionInstallation().add(new ConstructionInstallationProperty(constructionInstallation));
                }
            }
        }

        String conditionOfConstruction = rs.getString(prefix + "conditionofconstruction");
        if (!rs.wasNull())
            abstractConstruction.setConditionOfConstruction(ConditionOfConstructionValue.fromValue(conditionOfConstruction));

        Date dateOfConstruction = rs.getDate(prefix + "dateofconstruction");
        if (!rs.wasNull())
            abstractConstruction.setDateOfConstruction(dateOfConstruction.toLocalDate());

        Date dateOfDemolition = rs.getDate(prefix + "dateofdemolition");
        if (!rs.wasNull())
            abstractConstruction.setDateOfDemolition(dateOfDemolition.toLocalDate());

        psConstructionEvent.setLong(1, objectId);
        try (ResultSet constructionEventRs = psConstructionEvent.executeQuery()) {
            while (constructionEventRs.next()) {
                ConstructionEvent constructionEvent = new ConstructionEvent();
                constructionEventExporter.doExport(constructionEvent, constructionEventRs.getLong("constructionevent_id"));
                abstractConstruction.getConstructionEvent().add(new ConstructionEventProperty(constructionEvent));
            }
        }

        psElevation.setLong(1, objectId);
        try (ResultSet elevationRs = psElevation.executeQuery()) {
            while (elevationRs.next()) {
                Elevation elevation = new Elevation();
                elevationExporter.doExport(elevation, elevationRs.getLong("elevation_id"));
                abstractConstruction.getElevation().add(new ElevationProperty(elevation));
            }
        }

        psHeight.setLong(1, objectId);
        try (ResultSet heightRs = psHeight.executeQuery()) {
            while (heightRs.next()) {
                Height height = new Height();
                heightExporter.doExport(height, heightRs.getLong("height_id"));
                abstractConstruction.getHeight().add(new HeightProperty(height));
            }
        }

        psOccupancy.setLong(1, objectId);
        try (ResultSet occupancyRs = psOccupancy.executeQuery()) {
            while (occupancyRs.next()) {
                Occupancy occupancy = new Occupancy();
                occupancyExporter.doExport(occupancy, occupancyRs.getLong("occupancy_id"));
                abstractConstruction.getOccupancy().add(new OccupancyProperty(occupancy));
            }
        }

        psConsFacilityTypeAttribute.setLong(1, objectId);
        try (ResultSet consFacilityTypeAttributeRs = psConsFacilityTypeAttribute.executeQuery()) {
            while (consFacilityTypeAttributeRs.next()) {
                FacilityTypeAttribute consFacilityTypeAttribute = new FacilityTypeAttribute();
                facilityTypeAttributeExporter.doExport(consFacilityTypeAttribute, consFacilityTypeAttributeRs.getLong("facilitytypeattribute_id"));
                abstractConstruction.getConsFacilityTypeAttribute().add(new FacilityTypeAttributeProperty(consFacilityTypeAttribute));
            }
        }

        long consFacilityIdAttributeId = rs.getLong(prefix + "consfacilityidattribute_id");
        if (consFacilityIdAttributeId != 0) {
            FacilityIdAttribute consFacilityIdAttribute = facilityIdAttributeExporter.doExport(consFacilityIdAttributeId);
            if (consFacilityIdAttribute != null)
                abstractConstruction.setConsFacilityIdAttribute(new FacilityIdAttributeProperty(consFacilityIdAttribute));
        }

        psConsFacilityAttribute.setLong(1, objectId);
        try (ResultSet consFacilityAttributeRs = psConsFacilityAttribute.executeQuery()) {
            while (consFacilityAttributeRs.next()) {
                FacilityAttribute consFacilityAttribute = facilityAttributeExporter.doExport(consFacilityAttributeRs.getLong("facilityattribute_id"));
                if (consFacilityAttribute != null)
                    abstractConstruction.getConsFacilityAttribute().add(new FacilityAttributeProperty(consFacilityAttribute));
            }
        }

        long consBaseAttributeId = rs.getLong(prefix + "consbaseattribute_id");
        if (consBaseAttributeId != 0) {
            ConstructionBaseAttribute consBaseAttribute = new ConstructionBaseAttribute();
            constructionBaseAttributeExporter.doExport(consBaseAttribute, consBaseAttributeId);
            abstractConstruction.setConsBaseAttribute(new ConstructionBaseAttributeProperty(consBaseAttribute));
        }

        long consStructureAttributeId = rs.getLong(prefix + "consstructureattribute_id");
        if (consStructureAttributeId != 0) {
            ConstructionStructureAttribute consStructureAttribute = constructionStructureAttributeExporter.doExport(consStructureAttributeId);
            if (consStructureAttribute != null)
                abstractConstruction.setConsStructureAttribute(new ConstructionStructureAttributeProperty(consStructureAttribute));
        }

        psConsDisasterRiskAttribute.setLong(1, objectId);
        try (ResultSet consDisasterRiskAttributeRs = psConsDisasterRiskAttribute.executeQuery()) {
            while (consDisasterRiskAttributeRs.next()) {
                DisasterRiskAttribute consDisasterRiskAttribute = disasterRiskAttributeExporter.doExport(consDisasterRiskAttributeRs.getLong("disasterriskattribute_id"));
                if (consDisasterRiskAttribute != null)
                    abstractConstruction.getConsDisasterRiskAttribute().add(new DisasterRiskAttributeProperty(consDisasterRiskAttribute));
            }
        }

        psConsDmAttribute.setLong(1, objectId);
        try (ResultSet consDmAttributeRs = psConsDmAttribute.executeQuery()) {
            while (consDmAttributeRs.next()) {
                DmAttribute consDmAttribute = dmAttributeExporter.doExport(consDmAttributeRs.getLong("dmattribute_id"));
                if (consDmAttribute != null)
                    abstractConstruction.getConsDmAttribute().add(new DmAttributeProperty(consDmAttribute));
            }
        }

        long consDataQualityAttributeId = rs.getLong(prefix + "consdataqualityattribute_id");
        if (consDataQualityAttributeId != 0) {
            ConstructionDataQualityAttribute consDataQualityAttribute = new ConstructionDataQualityAttribute();
            constructionDataQualityAttributeExporter.doExport(consDataQualityAttribute, consDataQualityAttributeId);
            abstractConstruction.setConsDataQualityAttribute(new ConstructionDataQualityAttributeProperty(consDataQualityAttribute));
        }

        long lod0GeometryId = rs.getLong(prefix + "lod0geometry_brep_id");
        if (lod0GeometryId != 0)
            surfaceGeometryExporter.addBatch(lod0GeometryId, (GeometrySetter.AbstractGeometry) geometry -> abstractConstruction.setLod0Geometry(geometry));
        else {
            Object lod0GeometryObject = rs.getObject(prefix + "lod0geometry_other_geom");
            if (!rs.wasNull()) {
                GeometryObject lod0Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod0GeometryObject);
                if (lod0Geometry != null)
                    abstractConstruction.setLod0Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod0Geometry));
            }
        }

        long lod1GeometryId = rs.getLong(prefix + "lod1geometry_brep_id");
        if (lod1GeometryId != 0)
            surfaceGeometryExporter.addBatch(lod1GeometryId, (GeometrySetter.AbstractGeometry) geometry -> abstractConstruction.setLod1Geometry(geometry));
        else {
            Object lod1GeometryObject = rs.getObject(prefix + "lod1geometry_other_geom");
            if (!rs.wasNull()) {
                GeometryObject lod1Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod1GeometryObject);
                if (lod1Geometry != null)
                    abstractConstruction.setLod1Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod1Geometry));
            }
        }

        long lod2GeometryId = rs.getLong(prefix + "lod2geometry_brep_id");
        if (lod2GeometryId != 0)
            surfaceGeometryExporter.addBatch(lod2GeometryId, (GeometrySetter.AbstractGeometry) geometry -> abstractConstruction.setLod2Geometry(geometry));
        else {
            Object lod2GeometryObject = rs.getObject(prefix + "lod2geometry_other_geom");
            if (!rs.wasNull()) {
                GeometryObject lod2Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod2GeometryObject);
                if (lod2Geometry != null)
                    abstractConstruction.setLod2Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod2Geometry));
            }
        }

        long lod3GeometryId = rs.getLong(prefix + "lod3geometry_brep_id");
        if (lod3GeometryId != 0)
            surfaceGeometryExporter.addBatch(lod3GeometryId, (GeometrySetter.AbstractGeometry) geometry -> abstractConstruction.setLod3Geometry(geometry));
        else {
            Object lod3GeometryObject = rs.getObject(prefix + "lod3geometry_other_geom");
            if (!rs.wasNull()) {
                GeometryObject lod3Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod3GeometryObject);
                if (lod3Geometry != null)
                    abstractConstruction.setLod3Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod3Geometry));
            }
        }

        long lod4GeometryId = rs.getLong(prefix + "lod4geometry_brep_id");
        if (lod4GeometryId != 0)
            surfaceGeometryExporter.addBatch(lod4GeometryId, (GeometrySetter.AbstractGeometry) geometry -> abstractConstruction.setLod4Geometry(geometry));
        else {
            Object lod4GeometryObject = rs.getObject(prefix + "lod4geometry_other_geom");
            if (!rs.wasNull()) {
                GeometryObject lod4Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod4GeometryObject);
                if (lod4Geometry != null)
                    abstractConstruction.setLod4Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod4Geometry));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psConstructionEvent.close();
        psElevation.close();
        psHeight.close();
        psOccupancy.close();
        psConsFacilityTypeAttribute.close();
        psConsFacilityAttribute.close();
        psConsDisasterRiskAttribute.close();
        psConsDmAttribute.close();
        psBoundedBySurface.close();
        psConstructionInstallation.close();
    }
}
