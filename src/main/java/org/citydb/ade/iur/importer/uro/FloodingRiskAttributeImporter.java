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
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.FloodingRiskAttribute;
import org.citygml4j.ade.iur.model.uro.RiverFloodingRiskAttribute;

import java.sql.*;

public class FloodingRiskAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final RiverFloodingRiskAttributeImporter riverFloodingRiskAttributeImporter;

    private int batchCounter;

    public FloodingRiskAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FLOODINGRISKATTRIBUTE)) + " " +
                "(id, objectclass_id, waterbody_floodingriskatt_id, rank, rank_codespace, rankorg, rankorg_codespace," +
                "depth, depth_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        riverFloodingRiskAttributeImporter = manager.getImporter(RiverFloodingRiskAttributeImporter.class);
    }

    public void doImport(FloodingRiskAttribute floodingRiskAttribute, long objectId, long parentId, String parentColumn) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(floodingRiskAttribute.getClass()));
        if ("waterbody_floodingriskatt_id".equals(parentColumn))
            ps.setLong(3, parentId);
        else
            ps.setNull(3, Types.NULL);

        if (floodingRiskAttribute.getRank() != null && floodingRiskAttribute.getRank().isSetValue()) {
            ps.setString(4, floodingRiskAttribute.getRank().getValue());
            ps.setString(5, floodingRiskAttribute.getRank().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (floodingRiskAttribute.getRankOrg() != null && floodingRiskAttribute.getRankOrg().isSetValue()) {
            ps.setString(6, floodingRiskAttribute.getRankOrg().getValue());
            ps.setString(7, floodingRiskAttribute.getRankOrg().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (floodingRiskAttribute.getDepth() != null && floodingRiskAttribute.getDepth().isSetValue()) {
            ps.setDouble(8, floodingRiskAttribute.getDepth().getValue());
            ps.setString(9, floodingRiskAttribute.getDepth().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FLOODINGRISKATTRIBUTE));

        if (floodingRiskAttribute instanceof RiverFloodingRiskAttribute)
            riverFloodingRiskAttributeImporter.doImport((RiverFloodingRiskAttribute) floodingRiskAttribute, objectId);
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
