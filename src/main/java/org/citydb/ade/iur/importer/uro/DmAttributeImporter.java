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
import org.citygml4j.ade.iur.model.uro.DmAnnotation;
import org.citygml4j.ade.iur.model.uro.DmAttribute;
import org.citygml4j.ade.iur.model.uro.DmGeometricAttribute;

import java.sql.*;

public class DmAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final DmElementImporter dmElementImporter;
    private final DmGeometricAttributeImporter dmGeometricAttributeImporter;
    private final DmAnnotationImporter dmAnnotationImporter;

    private int batchCounter;

    public DmAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DMATTRIBUTE)) + " " +
                "(id, objectclass_id, cityobject_trandmattribut_id, cityobject_vegdmattribute_id, dmcode," +
                "dmcode_codespace, meshcode, meshcode_codespace, dmelement_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        dmElementImporter = manager.getImporter(DmElementImporter.class);
        dmGeometricAttributeImporter = manager.getImporter(DmGeometricAttributeImporter.class);
        dmAnnotationImporter = manager.getImporter(DmAnnotationImporter.class);
    }

    public long doImport(DmAttribute dmAttribute) throws CityGMLImportException, SQLException {
        return doImport(dmAttribute, 0, null);
    }

    public long doImport(DmAttribute dmAttribute, long parentId, String parentColumn) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.DMATTRIBUTE_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(dmAttribute.getClass()));
        if ("cityobject_trandmattribut_id".equals(parentColumn))
            ps.setLong(3, parentId);
        else
            ps.setNull(3, Types.NULL);
        if ("cityobject_vegdmattribute_id".equals(parentColumn))
            ps.setLong(4, parentId);
        else
            ps.setNull(4, Types.NULL);

        if (dmAttribute.getDmCode() != null && dmAttribute.getDmCode().isSetValue()) {
            ps.setString(5, dmAttribute.getDmCode().getValue());
            ps.setString(6, dmAttribute.getDmCode().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (dmAttribute.getMeshCode() != null && dmAttribute.getMeshCode().isSetValue()) {
            ps.setString(7, dmAttribute.getMeshCode().getValue());
            ps.setString(8, dmAttribute.getMeshCode().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        long dmElementId = 0;
        if (dmAttribute.getDmElement() != null && dmAttribute.getDmElement().isSetObject())
            dmElementId = dmElementImporter.doImport(dmAttribute.getDmElement().getObject());

        if (dmElementId != 0)
            ps.setLong(9, dmElementId);
        else
            ps.setNull(9, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.DMATTRIBUTE));

        if (dmAttribute instanceof DmGeometricAttribute)
            dmGeometricAttributeImporter.doImport((DmGeometricAttribute) dmAttribute, objectId);

        if (dmAttribute instanceof DmAnnotation)
            dmAnnotationImporter.doImport((DmAnnotation) dmAttribute, objectId);

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
