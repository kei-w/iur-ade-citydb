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
import org.citygml4j.ade.iur.model.uro.DmGeometricAttribute;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class DmGeometricAttributeImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public DmGeometricAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DMGEOMETRICATTRIBUTE)) + " " +
                "(id, geometrytype, geometrytype_codespace, maplevel, maplevel_codespace, shapetype," +
                "shapetype_codespace, visibility, is3d, isinstallation, isedited, issupplementarysymbol, angle," +
                "elevation, elevation_uom, lod0geometry_brep_id, lod0geometry_other_geom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(DmGeometricAttribute dmGeometricAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (dmGeometricAttribute.getGeometryType() != null && dmGeometricAttribute.getGeometryType().isSetValue()) {
            ps.setString(2, dmGeometricAttribute.getGeometryType().getValue());
            ps.setString(3, dmGeometricAttribute.getGeometryType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (dmGeometricAttribute.getMapLevel() != null && dmGeometricAttribute.getMapLevel().isSetValue()) {
            ps.setString(4, dmGeometricAttribute.getMapLevel().getValue());
            ps.setString(5, dmGeometricAttribute.getMapLevel().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (dmGeometricAttribute.getShapeType() != null && dmGeometricAttribute.getShapeType().isSetValue()) {
            ps.setString(6, dmGeometricAttribute.getShapeType().getValue());
            ps.setString(7, dmGeometricAttribute.getShapeType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (dmGeometricAttribute.getVisibility() != null)
            ps.setInt(8, dmGeometricAttribute.getVisibility() ? 1 : 0);
        else
            ps.setNull(8, Types.INTEGER);

        if (dmGeometricAttribute.getIs3d() != null)
            ps.setInt(9, dmGeometricAttribute.getIs3d() ? 1 : 0);
        else
            ps.setNull(9, Types.INTEGER);

        if (dmGeometricAttribute.getIsInstallation() != null)
            ps.setInt(10, dmGeometricAttribute.getIsInstallation() ? 1 : 0);
        else
            ps.setNull(10, Types.INTEGER);

        if (dmGeometricAttribute.getIsEdited() != null)
            ps.setInt(11, dmGeometricAttribute.getIsEdited() ? 1 : 0);
        else
            ps.setNull(11, Types.INTEGER);

        if (dmGeometricAttribute.getIsSupplementarySymbol() != null)
            ps.setInt(12, dmGeometricAttribute.getIsSupplementarySymbol() ? 1 : 0);
        else
            ps.setNull(12, Types.INTEGER);

        if (dmGeometricAttribute.getAngle() != null)
            ps.setDouble(13, dmGeometricAttribute.getAngle());
        else
            ps.setNull(13, Types.DOUBLE);

        if (dmGeometricAttribute.getElevation() != null && dmGeometricAttribute.getElevation().isSetValue()) {
            ps.setDouble(14, dmGeometricAttribute.getElevation().getValue());
            ps.setString(15, dmGeometricAttribute.getElevation().getUom());
        } else {
            ps.setNull(14, Types.DOUBLE);
            ps.setNull(15, Types.VARCHAR);
        }

        long lod0GeometryId = 0;
        Object lod0GeometryGeometry = null;
        if (dmGeometricAttribute.getLod0Geometry() != null && dmGeometricAttribute.getLod0Geometry().isSetGeometry()) {
            AbstractGeometry geometry = dmGeometricAttribute.getLod0Geometry().getGeometry();
            if (helper.isSurfaceGeometry(geometry))
                lod0GeometryId = helper.importSurfaceGeometry(geometry, 0);
            else if (helper.isPointOrLineGeometry(geometry)) {
                GeometryObject geometryObject = geometryConverter.getPointOrCurveGeometry(geometry);
                if (geometryObject != null)
                    lod0GeometryGeometry = helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(geometryObject, connection);
            }
        }

        if (lod0GeometryId != 0)
            ps.setLong(16, lod0GeometryId);
        else
            ps.setNull(16, Types.NULL);

        if (lod0GeometryGeometry != null)
            ps.setObject(17, lod0GeometryGeometry);
        else
            ps.setNull(17, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.DMGEOMETRICATTRIBUTE));
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
