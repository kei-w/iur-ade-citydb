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
import org.citygml4j.ade.iur.model.uro.DmElement;

import java.sql.*;

public class DmElementImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public DmElementImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DMELEMENT)) + " " +
                "(id, locationtype, locationtype_codespace, infotype, infotype_codespace, elementkey, hierarchylevel," +
                "datatype, datatype_codespace, annotationtype, annotationtype_codespace, precisiontype," +
                "precisiontype_codespace, dislocationtype, dislocationtype_codespace, breaktype," +
                "breaktype_codespace, attributevalue, attributetype, attributetype_codespace, attributevaluetype," +
                "creationdate, updatedate, terminationdate, freespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(DmElement dmElement) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.DMELEMENT_SEQ));
        ps.setLong(1, objectId);

        if (dmElement.getLocationType() != null && dmElement.getLocationType().isSetValue()) {
            ps.setString(2, dmElement.getLocationType().getValue());
            ps.setString(3, dmElement.getLocationType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (dmElement.getInfoType() != null && dmElement.getInfoType().isSetValue()) {
            ps.setString(4, dmElement.getInfoType().getValue());
            ps.setString(5, dmElement.getInfoType().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        ps.setString(6, dmElement.getElementKey());

        ps.setString(7, dmElement.getHierarchyLevel());

        if (dmElement.getDataType() != null && dmElement.getDataType().isSetValue()) {
            ps.setString(8, dmElement.getDataType().getValue());
            ps.setString(9, dmElement.getDataType().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (dmElement.getAnnotationType() != null && dmElement.getAnnotationType().isSetValue()) {
            ps.setString(10, dmElement.getAnnotationType().getValue());
            ps.setString(11, dmElement.getAnnotationType().getCodeSpace());
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        if (dmElement.getPrecisionType() != null && dmElement.getPrecisionType().isSetValue()) {
            ps.setString(12, dmElement.getPrecisionType().getValue());
            ps.setString(13, dmElement.getPrecisionType().getCodeSpace());
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        if (dmElement.getDislocationType() != null && dmElement.getDislocationType().isSetValue()) {
            ps.setString(14, dmElement.getDislocationType().getValue());
            ps.setString(15, dmElement.getDislocationType().getCodeSpace());
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        if (dmElement.getBreakType() != null && dmElement.getBreakType().isSetValue()) {
            ps.setString(16, dmElement.getBreakType().getValue());
            ps.setString(17, dmElement.getBreakType().getCodeSpace());
        } else {
            ps.setNull(16, Types.VARCHAR);
            ps.setNull(17, Types.VARCHAR);
        }

        ps.setString(18, dmElement.getAttributeValue());

        if (dmElement.getAttributeType() != null && dmElement.getAttributeType().isSetValue()) {
            ps.setString(19, dmElement.getAttributeType().getValue());
            ps.setString(20, dmElement.getAttributeType().getCodeSpace());
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        ps.setString(21, dmElement.getAttributeValueType());

        if (dmElement.getCreationDate() != null)
            ps.setDate(22, Date.valueOf(dmElement.getCreationDate().atDay(1)));
        else
            ps.setNull(22, Types.DATE);

        if (dmElement.getUpdateDate() != null)
            ps.setDate(23, Date.valueOf(dmElement.getUpdateDate().atDay(1)));
        else
            ps.setNull(23, Types.DATE);

        if (dmElement.getTerminationDate() != null)
            ps.setDate(24, Date.valueOf(dmElement.getTerminationDate().atDay(1)));
        else
            ps.setNull(24, Types.DATE);

        ps.setString(25, dmElement.getFreeSpace());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.DMELEMENT));

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
