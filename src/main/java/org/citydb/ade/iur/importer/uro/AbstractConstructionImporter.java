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
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface;
import org.citygml4j.ade.iur.model.uro.AbstractBoundarySurfaceProperty;
import org.citygml4j.ade.iur.model.uro.AbstractConstruction;
import org.citygml4j.ade.iur.model.uro.ConstructionEventProperty;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallationProperty;
import org.citygml4j.ade.iur.model.uro.DisasterRiskAttributeProperty;
import org.citygml4j.ade.iur.model.uro.DmAttributeProperty;
import org.citygml4j.ade.iur.model.uro.ElevationProperty;
import org.citygml4j.ade.iur.model.uro.FacilityAttributeProperty;
import org.citygml4j.ade.iur.model.uro.FacilityTypeAttributeProperty;
import org.citygml4j.ade.iur.model.uro.HeightProperty;
import org.citygml4j.ade.iur.model.uro.OccupancyProperty;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class AbstractConstructionImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ConstructionEventImporter constructionEventImporter;
    private final ElevationImporter elevationImporter;
    private final HeightImporter heightImporter;
    private final OccupancyImporter occupancyImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final ConstructionBaseAttributeImporter constructionBaseAttributeImporter;
    private final ConstructionStructureAttributeImporter constructionStructureAttributeImporter;
    private final DisasterRiskAttributeImporter disasterRiskAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final ConstructionDataQualityAttributeImporter constructionDataQualityAttributeImporter;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public AbstractConstructionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CONSTRUCTION)) + " " +
                "(id, conditionofconstruction, dateofconstruction, dateofdemolition, consfacilityidattribute_id," +
                "consbaseattribute_id, consstructureattribute_id, consdataqualityattribute_id, lod0geometry_brep_id," +
                "lod0geometry_other_geom, lod1geometry_brep_id, lod1geometry_other_geom, lod2geometry_brep_id," +
                "lod2geometry_other_geom, lod3geometry_brep_id, lod3geometry_other_geom, lod4geometry_brep_id," +
                "lod4geometry_other_geom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        constructionEventImporter = manager.getImporter(ConstructionEventImporter.class);
        elevationImporter = manager.getImporter(ElevationImporter.class);
        heightImporter = manager.getImporter(HeightImporter.class);
        occupancyImporter = manager.getImporter(OccupancyImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        constructionBaseAttributeImporter = manager.getImporter(ConstructionBaseAttributeImporter.class);
        constructionStructureAttributeImporter = manager.getImporter(ConstructionStructureAttributeImporter.class);
        disasterRiskAttributeImporter = manager.getImporter(DisasterRiskAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        constructionDataQualityAttributeImporter = manager.getImporter(ConstructionDataQualityAttributeImporter.class);
        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(AbstractConstruction abstractConstruction, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (abstractConstruction.getConditionOfConstruction() != null)
            ps.setString(2, abstractConstruction.getConditionOfConstruction().value());
        else
            ps.setNull(2, Types.VARCHAR);

        if (abstractConstruction.getDateOfConstruction() != null)
            ps.setDate(3, Date.valueOf(abstractConstruction.getDateOfConstruction()));
        else
            ps.setNull(3, Types.DATE);

        if (abstractConstruction.getDateOfDemolition() != null)
            ps.setDate(4, Date.valueOf(abstractConstruction.getDateOfDemolition()));
        else
            ps.setNull(4, Types.DATE);

        long consFacilityIdAttributeId = 0;
        if (abstractConstruction.getConsFacilityIdAttribute() != null && abstractConstruction.getConsFacilityIdAttribute().isSetObject())
            consFacilityIdAttributeId = facilityIdAttributeImporter.doImport(abstractConstruction.getConsFacilityIdAttribute().getObject());

        if (consFacilityIdAttributeId != 0)
            ps.setLong(5, consFacilityIdAttributeId);
        else
            ps.setNull(5, Types.NULL);

        long consBaseAttributeId = 0;
        if (abstractConstruction.getConsBaseAttribute() != null && abstractConstruction.getConsBaseAttribute().isSetObject())
            consBaseAttributeId = constructionBaseAttributeImporter.doImport(abstractConstruction.getConsBaseAttribute().getObject());

        if (consBaseAttributeId != 0)
            ps.setLong(6, consBaseAttributeId);
        else
            ps.setNull(6, Types.NULL);

        long consStructureAttributeId = 0;
        if (abstractConstruction.getConsStructureAttribute() != null && abstractConstruction.getConsStructureAttribute().isSetObject())
            consStructureAttributeId = constructionStructureAttributeImporter.doImport(abstractConstruction.getConsStructureAttribute().getObject());

        if (consStructureAttributeId != 0)
            ps.setLong(7, consStructureAttributeId);
        else
            ps.setNull(7, Types.NULL);

        long consDataQualityAttributeId = 0;
        if (abstractConstruction.getConsDataQualityAttribute() != null && abstractConstruction.getConsDataQualityAttribute().isSetObject())
            consDataQualityAttributeId = constructionDataQualityAttributeImporter.doImport(abstractConstruction.getConsDataQualityAttribute().getObject());

        if (consDataQualityAttributeId != 0)
            ps.setLong(8, consDataQualityAttributeId);
        else
            ps.setNull(8, Types.NULL);

        long lod0GeometryId = 0;
        Object lod0GeometryGeometry = null;
        if (abstractConstruction.getLod0Geometry() != null && abstractConstruction.getLod0Geometry().isSetGeometry()) {
            AbstractGeometry geometry = abstractConstruction.getLod0Geometry().getGeometry();
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
        if (abstractConstruction.getLod1Geometry() != null && abstractConstruction.getLod1Geometry().isSetGeometry()) {
            AbstractGeometry geometry = abstractConstruction.getLod1Geometry().getGeometry();
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
        if (abstractConstruction.getLod2Geometry() != null && abstractConstruction.getLod2Geometry().isSetGeometry()) {
            AbstractGeometry geometry = abstractConstruction.getLod2Geometry().getGeometry();
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
        if (abstractConstruction.getLod3Geometry() != null && abstractConstruction.getLod3Geometry().isSetGeometry()) {
            AbstractGeometry geometry = abstractConstruction.getLod3Geometry().getGeometry();
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
        if (abstractConstruction.getLod4Geometry() != null && abstractConstruction.getLod4Geometry().isSetGeometry()) {
            AbstractGeometry geometry = abstractConstruction.getLod4Geometry().getGeometry();
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

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (ConstructionEventProperty property : abstractConstruction.getConstructionEvent()) {
            if (property.isSetObject())
                constructionEventImporter.doImport(property.getObject(), objectId);
        }
        for (ElevationProperty property : abstractConstruction.getElevation()) {
            if (property.isSetObject())
                elevationImporter.doImport(property.getObject(), objectId);
        }
        for (HeightProperty property : abstractConstruction.getHeight()) {
            if (property.isSetObject())
                heightImporter.doImport(property.getObject(), objectId);
        }
        for (OccupancyProperty property : abstractConstruction.getOccupancy()) {
            if (property.isSetObject())
                occupancyImporter.doImport(property.getObject(), objectId);
        }
        for (FacilityTypeAttributeProperty property : abstractConstruction.getConsFacilityTypeAttribute()) {
            if (property.isSetObject())
                facilityTypeAttributeImporter.doImport(property.getObject(), objectId, "construction_consfacility_id");
        }
        for (FacilityAttributeProperty property : abstractConstruction.getConsFacilityAttribute()) {
            if (property.isSetObject())
                facilityAttributeImporter.doImport(property.getObject(), objectId, "construction_consfacility_id");
        }
        for (DisasterRiskAttributeProperty property : abstractConstruction.getConsDisasterRiskAttribute()) {
            if (property.isSetObject())
                disasterRiskAttributeImporter.doImport(property.getObject(), objectId, "construction_consdisaster_id");
        }
        for (DmAttributeProperty property : abstractConstruction.getConsDmAttribute()) {
            if (property.isSetObject())
                dmAttributeImporter.doImport(property.getObject(), objectId, "construction_consdmattrib_id");
        }
        for (AbstractBoundarySurfaceProperty property : abstractConstruction.getBoundedBySurface()) {
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
        for (ConstructionInstallationProperty property : abstractConstruction.getConstructionInstallation()) {
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
