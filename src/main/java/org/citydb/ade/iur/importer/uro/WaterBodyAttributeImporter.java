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
import org.citygml4j.ade.iur.model.uro.WaterBodyAttribute;
import org.citygml4j.ade.iur.model.uro.WaterBodyFloodingRiskAttribute;
import org.citygml4j.ade.iur.model.uro.WaterBodyRiverFloodingRiskAttribute;

import java.sql.*;

public class WaterBodyAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final WaterBodyRiverFloodingRiskAttributeImporter waterBodyRiverFloodingRiskAttributeImporter;

    private int batchCounter;

    public WaterBodyAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.WATERBODYATTRIBUTE)) + " " +
                "(id, objectclass_id, description, description_codespace, rank, rank_codespace, rankorg," +
                "rankorg_codespace, depth, depth_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        waterBodyRiverFloodingRiskAttributeImporter = manager.getImporter(WaterBodyRiverFloodingRiskAttributeImporter.class);
    }

    public long doImport(WaterBodyAttribute waterBodyAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.WATERBODYATTRIBUTE_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(waterBodyAttribute.getClass()));

        if (waterBodyAttribute instanceof WaterBodyFloodingRiskAttribute) {
            WaterBodyFloodingRiskAttribute waterBodyFloodingRiskAttribute = (WaterBodyFloodingRiskAttribute) waterBodyAttribute;
            if (waterBodyFloodingRiskAttribute.getDescription() != null && waterBodyFloodingRiskAttribute.getDescription().isSetValue()) {
                ps.setString(3, waterBodyFloodingRiskAttribute.getDescription().getValue());
                ps.setString(4, waterBodyFloodingRiskAttribute.getDescription().getCodeSpace());
            } else {
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.VARCHAR);
            }

            if (waterBodyFloodingRiskAttribute.getRank() != null && waterBodyFloodingRiskAttribute.getRank().isSetValue()) {
                ps.setString(5, waterBodyFloodingRiskAttribute.getRank().getValue());
                ps.setString(6, waterBodyFloodingRiskAttribute.getRank().getCodeSpace());
            } else {
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
            }

            if (waterBodyFloodingRiskAttribute.getRankOrg() != null && waterBodyFloodingRiskAttribute.getRankOrg().isSetValue()) {
                ps.setString(7, waterBodyFloodingRiskAttribute.getRankOrg().getValue());
                ps.setString(8, waterBodyFloodingRiskAttribute.getRankOrg().getCodeSpace());
            } else {
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.VARCHAR);
            }

            if (waterBodyFloodingRiskAttribute.getDepth() != null && waterBodyFloodingRiskAttribute.getDepth().isSetValue()) {
                ps.setDouble(9, waterBodyFloodingRiskAttribute.getDepth().getValue());
                ps.setString(10, waterBodyFloodingRiskAttribute.getDepth().getUom());
            } else {
                ps.setNull(9, Types.DOUBLE);
                ps.setNull(10, Types.VARCHAR);
            }
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.WATERBODYATTRIBUTE));

        if (waterBodyAttribute instanceof WaterBodyRiverFloodingRiskAttribute)
            waterBodyRiverFloodingRiskAttributeImporter.doImport((WaterBodyRiverFloodingRiskAttribute) waterBodyAttribute, objectId);

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
