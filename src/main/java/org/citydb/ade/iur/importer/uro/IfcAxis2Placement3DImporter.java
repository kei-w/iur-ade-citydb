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
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.uro.IfcAxis2Placement3D;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class IfcAxis2Placement3DImporter implements UrbanObjectModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public IfcAxis2Placement3DImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCAXIS2PLACEMENT3D)) + " " +
                "(id, location, axis, refdirection) " +
                "values (?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
        geometryConverter = helper.getGeometryConverter();
    }

    public long doImport(IfcAxis2Placement3D ifcAxis2Placement3D) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.IFCAXIS2PLACEMENT3_SEQ));
        ps.setLong(1, objectId);

        GeometryObject location = null;
        if (ifcAxis2Placement3D.getLocation() != null)
            location = geometryConverter.getPoint(ifcAxis2Placement3D.getLocation());

        if (location != null)
            ps.setObject(2, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(location, connection));
        else
            ps.setNull(2, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        if (!ifcAxis2Placement3D.getAxis().isEmpty())
            ps.setString(3, valueJoiner.join(ifcAxis2Placement3D.getAxis()));
        else
            ps.setNull(3, Types.VARCHAR);

        if (!ifcAxis2Placement3D.getRefDirection().isEmpty())
            ps.setString(4, valueJoiner.join(ifcAxis2Placement3D.getRefDirection()));
        else
            ps.setNull(4, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCAXIS2PLACEMENT3D));

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
