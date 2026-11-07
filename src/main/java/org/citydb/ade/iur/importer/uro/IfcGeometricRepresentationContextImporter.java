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
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.IfcGeometricRepresentationContext;

import java.sql.*;

public class IfcGeometricRepresentationContextImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final IfcAxis2Placement3DImporter ifcAxis2Placement3DImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public IfcGeometricRepresentationContextImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCGEOMETRICREPRESENTA)) + " " +
                "(id, objectclass_id, contextidentifier, contexttype, coordinatespacedimension, precision," +
                "worldcoordinatesystem_id, truenorth) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        ifcAxis2Placement3DImporter = manager.getImporter(IfcAxis2Placement3DImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(IfcGeometricRepresentationContext ifcGeometricRepresentationContext) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.IFCGEOMETRICREPRES_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(ifcGeometricRepresentationContext.getClass()));

        ps.setString(3, ifcGeometricRepresentationContext.getContextIdentifier());

        ps.setString(4, ifcGeometricRepresentationContext.getContextType());

        if (ifcGeometricRepresentationContext.getCoordinateSpaceDimension() != null)
            ps.setInt(5, ifcGeometricRepresentationContext.getCoordinateSpaceDimension());
        else
            ps.setNull(5, Types.INTEGER);

        if (ifcGeometricRepresentationContext.getPrecision() != null)
            ps.setDouble(6, ifcGeometricRepresentationContext.getPrecision());
        else
            ps.setNull(6, Types.DOUBLE);

        long worldCoordinateSystemId = 0;
        if (ifcGeometricRepresentationContext.getWorldCoordinateSystem() != null && ifcGeometricRepresentationContext.getWorldCoordinateSystem().isSetObject())
            worldCoordinateSystemId = ifcAxis2Placement3DImporter.doImport(ifcGeometricRepresentationContext.getWorldCoordinateSystem().getObject());

        if (worldCoordinateSystemId != 0)
            ps.setLong(7, worldCoordinateSystemId);
        else
            ps.setNull(7, Types.NULL);

        if (!ifcGeometricRepresentationContext.getTrueNorth().isEmpty())
            ps.setString(8, valueJoiner.join(ifcGeometricRepresentationContext.getTrueNorth()));
        else
            ps.setNull(8, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCGEOMETRICREPRESENTA));

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
