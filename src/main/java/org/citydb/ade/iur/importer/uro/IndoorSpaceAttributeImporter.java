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
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.IndoorSpaceAttribute;

import java.sql.*;

public class IndoorSpaceAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public IndoorSpaceAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.INDOORSPACEATTRIBUTE)) + " " +
                "(id, floorid, isrestricted, suite, ispublic, tolltype, tolltype_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IndoorSpaceAttribute indoorSpaceAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, indoorSpaceAttribute.getFloorId());

        if (indoorSpaceAttribute.getIsRestricted() != null)
            ps.setInt(3, indoorSpaceAttribute.getIsRestricted() ? 1 : 0);
        else
            ps.setNull(3, Types.INTEGER);

        ps.setString(4, indoorSpaceAttribute.getSuite());

        if (indoorSpaceAttribute.getIsPublic() != null)
            ps.setInt(5, indoorSpaceAttribute.getIsPublic() ? 1 : 0);
        else
            ps.setNull(5, Types.INTEGER);

        if (indoorSpaceAttribute.getTollType() != null && indoorSpaceAttribute.getTollType().isSetValue()) {
            ps.setString(6, indoorSpaceAttribute.getTollType().getValue());
            ps.setString(7, indoorSpaceAttribute.getTollType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.INDOORSPACEATTRIBUTE));
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
