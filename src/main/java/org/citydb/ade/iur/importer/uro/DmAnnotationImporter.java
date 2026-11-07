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
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.uro.DmAnnotation;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class DmAnnotationImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public DmAnnotationImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DMANNOTATION)) + " " +
                "(id, geometrytype, geometrytype_codespace, shapetype, shapetype_codespace, label, isvertical, size_," +
                "orientation, linewidth, spacing, lod0anchorpoint_brep_id, lod0anchorpoint_other_geom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(DmAnnotation dmAnnotation, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (dmAnnotation.getGeometryType() != null && dmAnnotation.getGeometryType().isSetValue()) {
            ps.setString(2, dmAnnotation.getGeometryType().getValue());
            ps.setString(3, dmAnnotation.getGeometryType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (dmAnnotation.getShapeType() != null && dmAnnotation.getShapeType().isSetValue()) {
            ps.setString(4, dmAnnotation.getShapeType().getValue());
            ps.setString(5, dmAnnotation.getShapeType().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        ps.setString(6, dmAnnotation.getLabel());

        if (dmAnnotation.getIsVertical() != null)
            ps.setInt(7, dmAnnotation.getIsVertical() ? 1 : 0);
        else
            ps.setNull(7, Types.INTEGER);

        if (dmAnnotation.getSize() != null)
            ps.setInt(8, dmAnnotation.getSize());
        else
            ps.setNull(8, Types.INTEGER);

        if (dmAnnotation.getOrientation() != null)
            ps.setInt(9, dmAnnotation.getOrientation());
        else
            ps.setNull(9, Types.INTEGER);

        if (dmAnnotation.getLinewidth() != null)
            ps.setInt(10, dmAnnotation.getLinewidth());
        else
            ps.setNull(10, Types.INTEGER);

        if (dmAnnotation.getSpacing() != null)
            ps.setInt(11, dmAnnotation.getSpacing());
        else
            ps.setNull(11, Types.INTEGER);

        long lod0anchorPointId = 0;
        Object lod0anchorPointGeometry = null;
        if (dmAnnotation.getLod0anchorPoint() != null && dmAnnotation.getLod0anchorPoint().isSetGeometry()) {
            AbstractGeometry geometry = dmAnnotation.getLod0anchorPoint().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod0anchorPointId = helper.importSurfaceGeometry(geometry, 0);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod0anchorPointGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod0anchorPointId != 0)
            ps.setLong(12, lod0anchorPointId);
        else
            ps.setNull(12, Types.NULL);

        if (lod0anchorPointGeometry != null)
            ps.setObject(13, lod0anchorPointGeometry);
        else
            ps.setNull(13, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.DMANNOTATION));
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
