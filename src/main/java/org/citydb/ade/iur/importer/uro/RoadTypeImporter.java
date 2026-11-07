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
import org.citygml4j.ade.iur.model.uro.RoadType;

import java.sql.*;

public class RoadTypeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public RoadTypeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.ROADTYPE)) + " " +
                "(id, id_1, creationdate, istemporary, roadtype, roadtype_codespace, widthtype, widthtype_codespace," +
                "istollroad, separator, separator_uom, ishighway) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(RoadType roadType) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.ROADTYPE_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, roadType.getId());

        if (roadType.getCreationDate() != null)
            ps.setDate(3, Date.valueOf(roadType.getCreationDate()));
        else
            ps.setNull(3, Types.DATE);

        if (roadType.getIsTemporary() != null)
            ps.setInt(4, roadType.getIsTemporary() ? 1 : 0);
        else
            ps.setNull(4, Types.INTEGER);

        if (roadType.getRoadType() != null && roadType.getRoadType().isSetValue()) {
            ps.setString(5, roadType.getRoadType().getValue());
            ps.setString(6, roadType.getRoadType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (roadType.getWidthType() != null && roadType.getWidthType().isSetValue()) {
            ps.setString(7, roadType.getWidthType().getValue());
            ps.setString(8, roadType.getWidthType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (roadType.getIsTollRoad() != null)
            ps.setInt(9, roadType.getIsTollRoad() ? 1 : 0);
        else
            ps.setNull(9, Types.INTEGER);

        if (roadType.getSeparator() != null && roadType.getSeparator().isSetValue()) {
            ps.setDouble(10, roadType.getSeparator().getValue());
            ps.setString(11, roadType.getSeparator().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (roadType.getIsHighWay() != null)
            ps.setInt(12, roadType.getIsHighWay() ? 1 : 0);
        else
            ps.setNull(12, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.ROADTYPE));

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
