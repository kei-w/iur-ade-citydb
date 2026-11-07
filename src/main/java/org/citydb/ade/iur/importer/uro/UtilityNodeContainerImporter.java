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
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.UtilityNodeContainer;

import java.sql.*;

public class UtilityNodeContainerImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UtilityNetworkElementImporter utilityNetworkElementImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public UtilityNodeContainerImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.UTILITYNODECONTAINER)) + " " +
                "(id, objectclass_id, containertype, containertype_codespace, innerdiamiterlong," +
                "innerdiamiterlong_uom, outerdiamiterlong, outerdiamiterlong_uom, innerdiamitershort," +
                "innerdiamitershort_uom, outerdiamitershort, outerdiamitershort_uom, depth, depth_uom, appurtenance," +
                "rotationangle) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        utilityNetworkElementImporter = manager.getImporter(UtilityNetworkElementImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(UtilityNodeContainer utilityNodeContainer, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        utilityNetworkElementImporter.doImport(utilityNodeContainer, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        if (utilityNodeContainer.getContainerType() != null && utilityNodeContainer.getContainerType().isSetValue()) {
            ps.setString(3, utilityNodeContainer.getContainerType().getValue());
            ps.setString(4, utilityNodeContainer.getContainerType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (utilityNodeContainer.getInnerDiamiterLong() != null && utilityNodeContainer.getInnerDiamiterLong().isSetValue()) {
            ps.setDouble(5, utilityNodeContainer.getInnerDiamiterLong().getValue());
            ps.setString(6, utilityNodeContainer.getInnerDiamiterLong().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (utilityNodeContainer.getOuterDiamiterLong() != null && utilityNodeContainer.getOuterDiamiterLong().isSetValue()) {
            ps.setDouble(7, utilityNodeContainer.getOuterDiamiterLong().getValue());
            ps.setString(8, utilityNodeContainer.getOuterDiamiterLong().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (utilityNodeContainer.getInnerDiamiterShort() != null && utilityNodeContainer.getInnerDiamiterShort().isSetValue()) {
            ps.setDouble(9, utilityNodeContainer.getInnerDiamiterShort().getValue());
            ps.setString(10, utilityNodeContainer.getInnerDiamiterShort().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (utilityNodeContainer.getOuterDiamiterShort() != null && utilityNodeContainer.getOuterDiamiterShort().isSetValue()) {
            ps.setDouble(11, utilityNodeContainer.getOuterDiamiterShort().getValue());
            ps.setString(12, utilityNodeContainer.getOuterDiamiterShort().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (utilityNodeContainer.getDepth() != null && utilityNodeContainer.getDepth().isSetValue()) {
            ps.setDouble(13, utilityNodeContainer.getDepth().getValue());
            ps.setString(14, utilityNodeContainer.getDepth().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (!utilityNodeContainer.getAppurtenance().isEmpty())
            ps.setString(15, valueJoiner.join(utilityNodeContainer.getAppurtenance()));
        else
            ps.setNull(15, Types.VARCHAR);

        if (utilityNodeContainer.getRotationAngle() != null)
            ps.setDouble(16, utilityNodeContainer.getRotationAngle());
        else
            ps.setNull(16, Types.DOUBLE);

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
