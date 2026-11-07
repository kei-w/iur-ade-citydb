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
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class ConstructionInstallationImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public ConstructionInstallationImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CONSTRUCTIONINSTALLATI)) + " " +
                "(id, construction_construction_id, class, class_codespace, function, function_codespace, usage," +
                "usage_codespace, lod2geometry_brep_id, lod2geometry_other_geom, lod3geometry_brep_id," +
                "lod3geometry_other_geom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(ConstructionInstallation constructionInstallation, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        if (foreignKeys.contains("construction_construction_id"))
            ps.setLong(2, foreignKeys.get("construction_construction_id"));
        else
            ps.setNull(2, Types.NULL);

        if (constructionInstallation.getClassifier() != null && constructionInstallation.getClassifier().isSetValue()) {
            ps.setString(3, constructionInstallation.getClassifier().getValue());
            ps.setString(4, constructionInstallation.getClassifier().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (!constructionInstallation.getFunction().isEmpty()) {
            valueJoiner.join(constructionInstallation.getFunction(), Code::getValue, Code::getCodeSpace);
            ps.setString(5, valueJoiner.result(0));
            ps.setString(6, valueJoiner.result(1));
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (!constructionInstallation.getUsage().isEmpty()) {
            valueJoiner.join(constructionInstallation.getUsage(), Code::getValue, Code::getCodeSpace);
            ps.setString(7, valueJoiner.result(0));
            ps.setString(8, valueJoiner.result(1));
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        long lod2GeometryId = 0;
        Object lod2GeometryGeometry = null;
        if (constructionInstallation.getLod2Geometry() != null && constructionInstallation.getLod2Geometry().isSetGeometry()) {
            AbstractGeometry geometry = constructionInstallation.getLod2Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod2GeometryId = helper.importSurfaceGeometry(geometry, objectId);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod2GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod2GeometryId != 0)
            ps.setLong(9, lod2GeometryId);
        else
            ps.setNull(9, Types.NULL);

        if (lod2GeometryGeometry != null)
            ps.setObject(10, lod2GeometryGeometry);
        else
            ps.setNull(10, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        long lod3GeometryId = 0;
        Object lod3GeometryGeometry = null;
        if (constructionInstallation.getLod3Geometry() != null && constructionInstallation.getLod3Geometry().isSetGeometry()) {
            AbstractGeometry geometry = constructionInstallation.getLod3Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod3GeometryId = helper.importSurfaceGeometry(geometry, objectId);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod3GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod3GeometryId != 0)
            ps.setLong(11, lod3GeometryId);
        else
            ps.setNull(11, Types.NULL);

        if (lod3GeometryGeometry != null)
            ps.setObject(12, lod3GeometryGeometry);
        else
            ps.setNull(12, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);
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
