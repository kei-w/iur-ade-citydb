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
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.TrackAttribute;

import java.sql.*;

public class TrackAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public TrackAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRACKATTRIBUTE)) + " " +
                "(id, alternativename, admintype, admintype_codespace, relativelevel, widthtype, widthtype_codespace," +
                "structuretype, structuretype_codespace, istollroad, separator, separator_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(TrackAttribute trackAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.TRACKATTRIBUTE_SEQ));
        ps.setLong(1, objectId);

        if (!trackAttribute.getAlternativeName().isEmpty())
            ps.setString(2, valueJoiner.join(trackAttribute.getAlternativeName()));
        else
            ps.setNull(2, Types.VARCHAR);

        if (trackAttribute.getAdminType() != null && trackAttribute.getAdminType().isSetValue()) {
            ps.setString(3, trackAttribute.getAdminType().getValue());
            ps.setString(4, trackAttribute.getAdminType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (trackAttribute.getRelativeLevel() != null)
            ps.setInt(5, trackAttribute.getRelativeLevel());
        else
            ps.setNull(5, Types.INTEGER);

        if (trackAttribute.getWidthType() != null && trackAttribute.getWidthType().isSetValue()) {
            ps.setString(6, trackAttribute.getWidthType().getValue());
            ps.setString(7, trackAttribute.getWidthType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (trackAttribute.getStructureType() != null && trackAttribute.getStructureType().isSetValue()) {
            ps.setString(8, trackAttribute.getStructureType().getValue());
            ps.setString(9, trackAttribute.getStructureType().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (trackAttribute.getIsTollRoad() != null)
            ps.setInt(10, trackAttribute.getIsTollRoad() ? 1 : 0);
        else
            ps.setNull(10, Types.INTEGER);

        if (trackAttribute.getSeparator() != null && trackAttribute.getSeparator().isSetValue()) {
            ps.setDouble(11, trackAttribute.getSeparator().getValue());
            ps.setString(12, trackAttribute.getSeparator().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TRACKATTRIBUTE));

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
