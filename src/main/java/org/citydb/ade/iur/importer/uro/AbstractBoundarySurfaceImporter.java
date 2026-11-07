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
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.AbstractBoundarySurface;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;

import java.sql.*;

public class AbstractBoundarySurfaceImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public AbstractBoundarySurfaceImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BOUNDARYSURFACE)) + " " +
                "(id, objectclass_id, construction_boundedby_id, lod2multisurface_id, lod3multisurface_id) " +
                "values (?, ?, ?, ?, ?)");
    }

    public void doImport(AbstractBoundarySurface abstractBoundarySurface, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());
        if (foreignKeys.contains("construction_boundedby_id"))
            ps.setLong(3, foreignKeys.get("construction_boundedby_id"));
        else
            ps.setNull(3, Types.NULL);

        long lod2MultiSurfaceId = 0;
        if (abstractBoundarySurface.getLod2MultiSurface() != null) {
            MultiSurfaceProperty property = abstractBoundarySurface.getLod2MultiSurface();
            if (property.isSetMultiSurface()) {
                lod2MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.BOUNDARYSURFACE), objectId, "lod2multisurface_id");
            }
        }

        if (lod2MultiSurfaceId != 0)
            ps.setLong(4, lod2MultiSurfaceId);
        else
            ps.setNull(4, Types.NULL);

        long lod3MultiSurfaceId = 0;
        if (abstractBoundarySurface.getLod3MultiSurface() != null) {
            MultiSurfaceProperty property = abstractBoundarySurface.getLod3MultiSurface();
            if (property.isSetMultiSurface()) {
                lod3MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.BOUNDARYSURFACE), objectId, "lod3multisurface_id");
            }
        }

        if (lod3MultiSurfaceId != 0)
            ps.setLong(5, lod3MultiSurfaceId);
        else
            ps.setNull(5, Types.NULL);

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
