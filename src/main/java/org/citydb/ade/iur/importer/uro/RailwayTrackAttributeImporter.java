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
import org.citygml4j.ade.iur.model.uro.ControlPointProperty;
import org.citygml4j.ade.iur.model.uro.RailwayTrackAttribute;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class RailwayTrackAttributeImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ControlPointImporter controlPointImporter;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public RailwayTrackAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.RAILWAYTRACKATTRIBUTE)) + " " +
                "(id, routename, directiontype, directiontype_codespace, tracktype, tracktype_codespace, startpost," +
                "endpost, alignmenttype, alignmenttype_codespace, lod2network_brep_id, lod2network_other_geom," +
                "lod3network_brep_id, lod3network_other_geom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        controlPointImporter = manager.getImporter(ControlPointImporter.class);
        geometryConverter = helper.getGeometryConverter();
    }

    public long doImport(RailwayTrackAttribute railwayTrackAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.RAILWAYTRACKATTRIB_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, railwayTrackAttribute.getRouteName());

        if (railwayTrackAttribute.getDirectionType() != null && railwayTrackAttribute.getDirectionType().isSetValue()) {
            ps.setString(3, railwayTrackAttribute.getDirectionType().getValue());
            ps.setString(4, railwayTrackAttribute.getDirectionType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (railwayTrackAttribute.getTrackType() != null && railwayTrackAttribute.getTrackType().isSetValue()) {
            ps.setString(5, railwayTrackAttribute.getTrackType().getValue());
            ps.setString(6, railwayTrackAttribute.getTrackType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        ps.setString(7, railwayTrackAttribute.getStartPost());

        ps.setString(8, railwayTrackAttribute.getEndPost());

        if (railwayTrackAttribute.getAlignmentType() != null && railwayTrackAttribute.getAlignmentType().isSetValue()) {
            ps.setString(9, railwayTrackAttribute.getAlignmentType().getValue());
            ps.setString(10, railwayTrackAttribute.getAlignmentType().getCodeSpace());
        } else {
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.VARCHAR);
        }

        long lod2NetworkId = 0;
        Object lod2NetworkGeometry = null;
        if (railwayTrackAttribute.getLod2Network() != null && railwayTrackAttribute.getLod2Network().isSetGeometry()) {
            AbstractGeometry geometry = railwayTrackAttribute.getLod2Network().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod2NetworkId = helper.importSurfaceGeometry(geometry, 0);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod2NetworkGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod2NetworkId != 0)
            ps.setLong(11, lod2NetworkId);
        else
            ps.setNull(11, Types.NULL);

        if (lod2NetworkGeometry != null)
            ps.setObject(12, lod2NetworkGeometry);
        else
            ps.setNull(12, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        long lod3NetworkId = 0;
        Object lod3NetworkGeometry = null;
        if (railwayTrackAttribute.getLod3Network() != null && railwayTrackAttribute.getLod3Network().isSetGeometry()) {
            AbstractGeometry geometry = railwayTrackAttribute.getLod3Network().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod3NetworkId = helper.importSurfaceGeometry(geometry, 0);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod3NetworkGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod3NetworkId != 0)
            ps.setLong(13, lod3NetworkId);
        else
            ps.setNull(13, Types.NULL);

        if (lod3NetworkGeometry != null)
            ps.setObject(14, lod3NetworkGeometry);
        else
            ps.setNull(14, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.RAILWAYTRACKATTRIBUTE));

        for (ControlPointProperty property : railwayTrackAttribute.getControlPoint()) {
            if (property.isSetObject())
                controlPointImporter.doImport(property.getObject(), objectId);
        }

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
