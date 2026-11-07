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

package org.citydb.ade.iur.importer.urf;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.urf.Boundary;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class BoundaryImporter implements UrbanFunctionModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public BoundaryImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BOUNDARY)) + " " +
                "(id, class, class_codespace, function, function_codespace, usage, usage_codespace, offset_," +
                "offset__uom, offsetdirection, location) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
        geometryConverter = helper.getGeometryConverter();
    }

    public long doImport(Boundary boundary) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.BOUNDARY_SEQ));
        ps.setLong(1, objectId);

        if (boundary.getClassifier() != null && boundary.getClassifier().isSetValue()) {
            ps.setString(2, boundary.getClassifier().getValue());
            ps.setString(3, boundary.getClassifier().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (!boundary.getFunction().isEmpty()) {
            valueJoiner.join(boundary.getFunction(), Code::getValue, Code::getCodeSpace);
            ps.setString(4, valueJoiner.result(0));
            ps.setString(5, valueJoiner.result(1));
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (!boundary.getUsage().isEmpty()) {
            valueJoiner.join(boundary.getUsage(), Code::getValue, Code::getCodeSpace);
            ps.setString(6, valueJoiner.result(0));
            ps.setString(7, valueJoiner.result(1));
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (boundary.getOffset() != null && boundary.getOffset().isSetValue()) {
            ps.setDouble(8, boundary.getOffset().getValue());
            ps.setString(9, boundary.getOffset().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        ps.setString(10, boundary.getOffsetDirection());

        GeometryObject location = null;
        if (boundary.getLocation() != null) {
            location = geometryConverter.getMultiCurve(boundary.getLocation());
            boundary.getLocation().unsetMultiCurve();
        }

        if (location != null)
            ps.setObject(11, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(location, connection));
        else
            ps.setNull(11, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BOUNDARY));

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
