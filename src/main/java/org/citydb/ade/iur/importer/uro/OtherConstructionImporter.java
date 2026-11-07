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

package org.citydb.ade.iur.importer.uro;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface;
import org.citygml4j.ade.iur.model.uro.AbstractBoundarySurfaceProperty;
import org.citygml4j.ade.iur.model.uro.ConstructionEventProperty;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallationProperty;
import org.citygml4j.ade.iur.model.uro.DisasterRiskAttributeProperty;
import org.citygml4j.ade.iur.model.uro.DmAttributeProperty;
import org.citygml4j.ade.iur.model.uro.ElevationProperty;
import org.citygml4j.ade.iur.model.uro.FacilityAttributeProperty;
import org.citygml4j.ade.iur.model.uro.FacilityTypeAttributeProperty;
import org.citygml4j.ade.iur.model.uro.HeightProperty;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttributeProperty;
import org.citygml4j.ade.iur.model.uro.OccupancyProperty;
import org.citygml4j.ade.iur.model.uro.OtherConstruction;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class OtherConstructionImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final OtherConstructionToConstructionEventImporter otherConstructionToConstructionEventImporter;
    private final ConstructionEventImporter constructionEventImporter;
    private final OtherConstructionToElevationImporter otherConstructionToElevationImporter;
    private final ElevationImporter elevationImporter;
    private final OtherConstructionToHeightImporter otherConstructionToHeightImporter;
    private final HeightImporter heightImporter;
    private final OtherConstructionToOccupancyImporter otherConstructionToOccupancyImporter;
    private final OccupancyImporter occupancyImporter;
    private final OtherConstructionToFacilityTypeAttributeImporter otherConstructionToFacilityTypeAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final OtherConstructionToFacilityAttributeImporter otherConstructionToFacilityAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final ConstructionBaseAttributeImporter constructionBaseAttributeImporter;
    private final ConstructionStructureAttributeImporter constructionStructureAttributeImporter;
    private final OtherConstructionToDisasterRiskAttributeImporter otherConstructionToDisasterRiskAttributeImporter;
    private final DisasterRiskAttributeImporter disasterRiskAttributeImporter;
    private final OtherConstructionToDmAttributeImporter otherConstructionToDmAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final DataQualityAttributeImporter dataQualityAttributeImporter;
    private final OtherConstructionToKeyValuePairAttributeImporter otherConstructionToKeyValuePairAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;
    private final AttributeValueJoiner valueJoiner;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public OtherConstructionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CONSTRUCTION)) + " " +
                "(id, conditionofconstruction, dateofconstruction, dateofdemolition, consfacilityidattribute_id," +
                "consbaseattribute_id, consstructureattribute_id, consdataqualityattribute_id, lod0geometry_brep_id," +
                "lod0geometry_other_geom, lod1geometry_brep_id, lod1geometry_other_geom, lod2geometry_brep_id," +
                "lod2geometry_other_geom, lod3geometry_brep_id, lod3geometry_other_geom, lod4geometry_brep_id," +
                "lod4geometry_other_geom, class, class_codespace, function, function_codespace, usage," +
                "usage_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        otherConstructionToConstructionEventImporter = manager.getImporter(OtherConstructionToConstructionEventImporter.class);
        constructionEventImporter = manager.getImporter(ConstructionEventImporter.class);
        otherConstructionToElevationImporter = manager.getImporter(OtherConstructionToElevationImporter.class);
        elevationImporter = manager.getImporter(ElevationImporter.class);
        otherConstructionToHeightImporter = manager.getImporter(OtherConstructionToHeightImporter.class);
        heightImporter = manager.getImporter(HeightImporter.class);
        otherConstructionToOccupancyImporter = manager.getImporter(OtherConstructionToOccupancyImporter.class);
        occupancyImporter = manager.getImporter(OccupancyImporter.class);
        otherConstructionToFacilityTypeAttributeImporter = manager.getImporter(OtherConstructionToFacilityTypeAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        otherConstructionToFacilityAttributeImporter = manager.getImporter(OtherConstructionToFacilityAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        constructionBaseAttributeImporter = manager.getImporter(ConstructionBaseAttributeImporter.class);
        constructionStructureAttributeImporter = manager.getImporter(ConstructionStructureAttributeImporter.class);
        otherConstructionToDisasterRiskAttributeImporter = manager.getImporter(OtherConstructionToDisasterRiskAttributeImporter.class);
        disasterRiskAttributeImporter = manager.getImporter(DisasterRiskAttributeImporter.class);
        otherConstructionToDmAttributeImporter = manager.getImporter(OtherConstructionToDmAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        dataQualityAttributeImporter = manager.getImporter(DataQualityAttributeImporter.class);
        otherConstructionToKeyValuePairAttributeImporter = manager.getImporter(OtherConstructionToKeyValuePairAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(OtherConstruction otherConstruction, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (otherConstruction.getConditionOfConstruction() != null)
            ps.setString(2, otherConstruction.getConditionOfConstruction().value());
        else
            ps.setNull(2, Types.VARCHAR);

        if (otherConstruction.getDateOfConstruction() != null)
            ps.setDate(3, Date.valueOf(otherConstruction.getDateOfConstruction()));
        else
            ps.setNull(3, Types.DATE);

        if (otherConstruction.getDateOfDemolition() != null)
            ps.setDate(4, Date.valueOf(otherConstruction.getDateOfDemolition()));
        else
            ps.setNull(4, Types.DATE);

        long consFacilityIdAttributeId = 0;
        if (otherConstruction.getConsFacilityIdAttribute() != null && otherConstruction.getConsFacilityIdAttribute().isSetObject())
            consFacilityIdAttributeId = facilityIdAttributeImporter.doImport(otherConstruction.getConsFacilityIdAttribute().getObject());

        if (consFacilityIdAttributeId != 0)
            ps.setLong(5, consFacilityIdAttributeId);
        else
            ps.setNull(5, Types.NULL);

        long consBaseAttributeId = 0;
        if (otherConstruction.getConsBaseAttribute() != null && otherConstruction.getConsBaseAttribute().isSetObject())
            consBaseAttributeId = constructionBaseAttributeImporter.doImport(otherConstruction.getConsBaseAttribute().getObject());

        if (consBaseAttributeId != 0)
            ps.setLong(6, consBaseAttributeId);
        else
            ps.setNull(6, Types.NULL);

        long consStructureAttributeId = 0;
        if (otherConstruction.getConsStructureAttribute() != null && otherConstruction.getConsStructureAttribute().isSetObject())
            consStructureAttributeId = constructionStructureAttributeImporter.doImport(otherConstruction.getConsStructureAttribute().getObject());

        if (consStructureAttributeId != 0)
            ps.setLong(7, consStructureAttributeId);
        else
            ps.setNull(7, Types.NULL);

        long consDataQualityAttributeId = 0;
        if (otherConstruction.getConsDataQualityAttribute() != null && otherConstruction.getConsDataQualityAttribute().isSetObject())
            consDataQualityAttributeId = dataQualityAttributeImporter.doImport(otherConstruction.getConsDataQualityAttribute().getObject());

        if (consDataQualityAttributeId != 0)
            ps.setLong(8, consDataQualityAttributeId);
        else
            ps.setNull(8, Types.NULL);

        long lod0GeometryId = 0;
        Object lod0GeometryGeometry = null;
        if (otherConstruction.getLod0Geometry() != null && otherConstruction.getLod0Geometry().isSetGeometry()) {
            AbstractGeometry geometry = otherConstruction.getLod0Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod0GeometryId = helper.importSurfaceGeometry(geometry, objectId);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod0GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod0GeometryId != 0)
            ps.setLong(9, lod0GeometryId);
        else
            ps.setNull(9, Types.NULL);

        if (lod0GeometryGeometry != null)
            ps.setObject(10, lod0GeometryGeometry);
        else
            ps.setNull(10, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        long lod1GeometryId = 0;
        Object lod1GeometryGeometry = null;
        if (otherConstruction.getLod1Geometry() != null && otherConstruction.getLod1Geometry().isSetGeometry()) {
            AbstractGeometry geometry = otherConstruction.getLod1Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod1GeometryId = helper.importSurfaceGeometry(geometry, objectId);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod1GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod1GeometryId != 0)
            ps.setLong(11, lod1GeometryId);
        else
            ps.setNull(11, Types.NULL);

        if (lod1GeometryGeometry != null)
            ps.setObject(12, lod1GeometryGeometry);
        else
            ps.setNull(12, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        long lod2GeometryId = 0;
        Object lod2GeometryGeometry = null;
        if (otherConstruction.getLod2Geometry() != null && otherConstruction.getLod2Geometry().isSetGeometry()) {
            AbstractGeometry geometry = otherConstruction.getLod2Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod2GeometryId = helper.importSurfaceGeometry(geometry, objectId);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod2GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod2GeometryId != 0)
            ps.setLong(13, lod2GeometryId);
        else
            ps.setNull(13, Types.NULL);

        if (lod2GeometryGeometry != null)
            ps.setObject(14, lod2GeometryGeometry);
        else
            ps.setNull(14, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        long lod3GeometryId = 0;
        Object lod3GeometryGeometry = null;
        if (otherConstruction.getLod3Geometry() != null && otherConstruction.getLod3Geometry().isSetGeometry()) {
            AbstractGeometry geometry = otherConstruction.getLod3Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod3GeometryId = helper.importSurfaceGeometry(geometry, objectId);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod3GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod3GeometryId != 0)
            ps.setLong(15, lod3GeometryId);
        else
            ps.setNull(15, Types.NULL);

        if (lod3GeometryGeometry != null)
            ps.setObject(16, lod3GeometryGeometry);
        else
            ps.setNull(16, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        long lod4GeometryId = 0;
        Object lod4GeometryGeometry = null;
        if (otherConstruction.getLod4Geometry() != null && otherConstruction.getLod4Geometry().isSetGeometry()) {
            AbstractGeometry geometry = otherConstruction.getLod4Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod4GeometryId = helper.importSurfaceGeometry(geometry, objectId);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod4GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod4GeometryId != 0)
            ps.setLong(17, lod4GeometryId);
        else
            ps.setNull(17, Types.NULL);

        if (lod4GeometryGeometry != null)
            ps.setObject(18, lod4GeometryGeometry);
        else
            ps.setNull(18, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        if (otherConstruction.getClassifier() != null && otherConstruction.getClassifier().isSetValue()) {
            ps.setString(19, otherConstruction.getClassifier().getValue());
            ps.setString(20, otherConstruction.getClassifier().getCodeSpace());
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        if (!otherConstruction.getFunction().isEmpty()) {
            valueJoiner.join(otherConstruction.getFunction(), Code::getValue, Code::getCodeSpace);
            ps.setString(21, valueJoiner.result(0));
            ps.setString(22, valueJoiner.result(1));
        } else {
            ps.setNull(21, Types.VARCHAR);
            ps.setNull(22, Types.VARCHAR);
        }

        if (!otherConstruction.getUsage().isEmpty()) {
            valueJoiner.join(otherConstruction.getUsage(), Code::getValue, Code::getCodeSpace);
            ps.setString(23, valueJoiner.result(0));
            ps.setString(24, valueJoiner.result(1));
        } else {
            ps.setNull(23, Types.VARCHAR);
            ps.setNull(24, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (ConstructionEventProperty property : otherConstruction.getConstructionEvent()) {
            if (property.isSetObject()) {
                long constructionEventId = constructionEventImporter.doImport(property.getObject());
                otherConstructionToConstructionEventImporter.doImport(constructionEventId, objectId);
            }
        }
        for (ElevationProperty property : otherConstruction.getElevation()) {
            if (property.isSetObject()) {
                long elevationId = elevationImporter.doImport(property.getObject());
                otherConstructionToElevationImporter.doImport(elevationId, objectId);
            }
        }
        for (HeightProperty property : otherConstruction.getHeight()) {
            if (property.isSetObject()) {
                long heightId = heightImporter.doImport(property.getObject());
                otherConstructionToHeightImporter.doImport(heightId, objectId);
            }
        }
        for (OccupancyProperty property : otherConstruction.getOccupancy()) {
            if (property.isSetObject()) {
                long occupancyId = occupancyImporter.doImport(property.getObject());
                otherConstructionToOccupancyImporter.doImport(occupancyId, objectId);
            }
        }
        for (FacilityTypeAttributeProperty property : otherConstruction.getConsFacilityTypeAttribute()) {
            if (property.isSetObject()) {
                long consFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getObject());
                otherConstructionToFacilityTypeAttributeImporter.doImport(consFacilityTypeAttributeId, objectId);
            }
        }
        for (FacilityAttributeProperty property : otherConstruction.getConsFacilityAttribute()) {
            if (property.isSetObject()) {
                long consFacilityAttributeId = facilityAttributeImporter.doImport(property.getObject());
                otherConstructionToFacilityAttributeImporter.doImport(consFacilityAttributeId, objectId);
            }
        }
        for (DisasterRiskAttributeProperty property : otherConstruction.getConsDisasterRiskAttribute()) {
            if (property.isSetObject()) {
                long consDisasterRiskAttributeId = disasterRiskAttributeImporter.doImport(property.getObject());
                otherConstructionToDisasterRiskAttributeImporter.doImport(consDisasterRiskAttributeId, objectId);
            }
        }
        for (DmAttributeProperty property : otherConstruction.getConsDmAttribute()) {
            if (property.isSetObject()) {
                long consDmAttributeId = dmAttributeImporter.doImport(property.getObject());
                otherConstructionToDmAttributeImporter.doImport(consDmAttributeId, objectId);
            }
        }
        for (KeyValuePairAttributeProperty property : otherConstruction.getConsKeyValuePairAttribute()) {
            if (property.isSetObject()) {
                long consKeyValuePairAttributeId = keyValuePairAttributeImporter.doImport(property.getObject());
                otherConstructionToKeyValuePairAttributeImporter.doImport(consKeyValuePairAttributeId, objectId);
            }
        }
        for (AbstractBoundarySurfaceProperty property : otherConstruction.getBoundedBySurface()) {
            AbstractBoundarySurface boundarySurface = property.getObject();
            if (boundarySurface != null) {
                helper.importObject(boundarySurface, ForeignKeys.create().with("construction_boundedby_id", objectId));
                property.unsetObject();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateReverseObjectXlink(schemaMapper.getTableName(ADETable.BOUNDARYSURFACE), href, objectId, "construction_boundedby_id");
            }
        }
        for (ConstructionInstallationProperty property : otherConstruction.getConstructionInstallation()) {
            ConstructionInstallation constructionInstallation = property.getObject();
            if (constructionInstallation != null) {
                helper.importObject(constructionInstallation, ForeignKeys.create().with("construction_construction_id", objectId));
                property.unsetObject();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateReverseObjectXlink(schemaMapper.getTableName(ADETable.CONSTRUCTIONINSTALLATI), href, objectId, "construction_construction_id");
            }
        }
    }

    @Override
    public void executeBatch() throws CityGMLImportException, SQLException {
        if (batchCounter > 0) {
            ps.executeBatch();
            batchCounter = 0;
        }
    }

    @Override
    public void close() throws CityGMLImportException, SQLException {
        ps.close();
    }
}
