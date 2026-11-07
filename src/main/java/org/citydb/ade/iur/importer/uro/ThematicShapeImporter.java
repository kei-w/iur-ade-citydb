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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.uro.ThematicShape;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class ThematicShapeImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public ThematicShapeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.THEMATICSHAPE)) + " " +
                "(id, utilitynetwo_thematicshap_id, horizontaltype, horizontaltype_codespace, heighttype," +
                "heighttype_codespace, shape_brep_id, shape_other_geom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        geometryConverter = helper.getGeometryConverter();
    }

    public long doImport(ThematicShape thematicShape, long parentId) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.THEMATICSHAPE_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, parentId);

        if (thematicShape.getHorizontalType() != null && thematicShape.getHorizontalType().isSetValue()) {
            ps.setString(3, thematicShape.getHorizontalType().getValue());
            ps.setString(4, thematicShape.getHorizontalType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (thematicShape.getHeightType() != null && thematicShape.getHeightType().isSetValue()) {
            ps.setString(5, thematicShape.getHeightType().getValue());
            ps.setString(6, thematicShape.getHeightType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        long shapeId = 0;
        Object shapeGeometry = null;
        if (thematicShape.getShape() != null && thematicShape.getShape().isSetGeometry()) {
            AbstractGeometry geometry = thematicShape.getShape().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                shapeId = helper.importSurfaceGeometry(geometry, 0);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    shapeGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (shapeId != 0)
            ps.setLong(7, shapeId);
        else
            ps.setNull(7, Types.NULL);

        if (shapeGeometry != null)
            ps.setObject(8, shapeGeometry);
        else
            ps.setNull(8, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.THEMATICSHAPE));

        return objectId;
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
