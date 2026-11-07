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

package org.citydb.ade.iur.importer.urt;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.urt.Point;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class PointImporter implements PublicTransitModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public PointImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.POINT)) + " " +
                "(id, latitude, longitude, point, pointsequence, pointdistancetraveled) " +
                "values (?, ?, ?, ?, ?, ?)");

        geometryConverter = helper.getGeometryConverter();
    }

    public long doImport(Point point) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.POINT_SEQ));
        ps.setLong(1, objectId);

        if (point.getLatitude() != null)
            ps.setDouble(2, point.getLatitude());
        else
            ps.setNull(2, Types.DOUBLE);

        if (point.getLongitude() != null)
            ps.setDouble(3, point.getLongitude());
        else
            ps.setNull(3, Types.DOUBLE);

        GeometryObject pointValue = null;
        if (point.getPoint() != null)
            pointValue = geometryConverter.getPoint(point.getPoint());

        if (pointValue != null)
            ps.setObject(4, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(pointValue, connection));
        else
            ps.setNull(4, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        if (point.getPointSequence() != null)
            ps.setInt(5, point.getPointSequence());
        else
            ps.setNull(5, Types.INTEGER);

        if (point.getPointDistanceTraveled() != null)
            ps.setDouble(6, point.getPointDistanceTraveled());
        else
            ps.setNull(6, Types.DOUBLE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.POINT));

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
