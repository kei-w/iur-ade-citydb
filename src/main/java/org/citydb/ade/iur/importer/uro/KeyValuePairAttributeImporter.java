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
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttribute;

import java.sql.*;

public class KeyValuePairAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public KeyValuePairAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.KEYVALUEPAIRATTRIBUTE)) + " " +
                "(id, bridge_bridkeyvaluepairat_id, building_bldgkeyvaluepair_id, city_furniture_frnkeyvalu_id," +
                "land_use_lusekeyvaluepair_id, relief_feature_demkeyvalu_id, transpor_complex_trankeyv_id," +
                "tunnel_tunkeyvaluepairatt_id, cityobject_vegkeyvaluepai_id, waterbody_wtrkeyvaluepair_id, key," +
                "key_codespace, codevalue, codevalue_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(KeyValuePairAttribute keyValuePairAttribute, long parentId, String parentColumn) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.KEYVALUEPAIRATTRIB_SEQ));
        ps.setLong(1, objectId);
        if ("bridge_bridkeyvaluepairat_id".equals(parentColumn))
            ps.setLong(2, parentId);
        else
            ps.setNull(2, Types.NULL);
        if ("building_bldgkeyvaluepair_id".equals(parentColumn))
            ps.setLong(3, parentId);
        else
            ps.setNull(3, Types.NULL);
        if ("city_furniture_frnkeyvalu_id".equals(parentColumn))
            ps.setLong(4, parentId);
        else
            ps.setNull(4, Types.NULL);
        if ("land_use_lusekeyvaluepair_id".equals(parentColumn))
            ps.setLong(5, parentId);
        else
            ps.setNull(5, Types.NULL);
        if ("relief_feature_demkeyvalu_id".equals(parentColumn))
            ps.setLong(6, parentId);
        else
            ps.setNull(6, Types.NULL);
        if ("transpor_complex_trankeyv_id".equals(parentColumn))
            ps.setLong(7, parentId);
        else
            ps.setNull(7, Types.NULL);
        if ("tunnel_tunkeyvaluepairatt_id".equals(parentColumn))
            ps.setLong(8, parentId);
        else
            ps.setNull(8, Types.NULL);
        if ("cityobject_vegkeyvaluepai_id".equals(parentColumn))
            ps.setLong(9, parentId);
        else
            ps.setNull(9, Types.NULL);
        if ("waterbody_wtrkeyvaluepair_id".equals(parentColumn))
            ps.setLong(10, parentId);
        else
            ps.setNull(10, Types.NULL);

        if (keyValuePairAttribute.getKey() != null && keyValuePairAttribute.getKey().isSetValue()) {
            ps.setString(11, keyValuePairAttribute.getKey().getValue());
            ps.setString(12, keyValuePairAttribute.getKey().getCodeSpace());
        } else {
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.VARCHAR);
        }

        if (keyValuePairAttribute.getCodeValue() != null && keyValuePairAttribute.getCodeValue().isSetValue()) {
            ps.setString(13, keyValuePairAttribute.getCodeValue().getValue());
            ps.setString(14, keyValuePairAttribute.getCodeValue().getCodeSpace());
        } else {
            ps.setNull(13, Types.VARCHAR);
            ps.setNull(14, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.KEYVALUEPAIRATTRIBUTE));

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
