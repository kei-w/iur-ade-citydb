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
import org.citygml4j.ade.iur.model.uro.IfcPsetOpeningElementCommon;

import java.sql.*;

public class IfcPsetOpeningElementCommonImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public IfcPsetOpeningElementCommonImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCPSETOPENINGELEMENTC)) + " " +
                "(id, reference, purpose, fireexit, protectedopening, paralleljambs) " +
                "values (?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IfcPsetOpeningElementCommon ifcPsetOpeningElementCommon, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, ifcPsetOpeningElementCommon.getReference());

        ps.setString(3, ifcPsetOpeningElementCommon.getPurpose());

        if (ifcPsetOpeningElementCommon.getFireExit() != null)
            ps.setInt(4, ifcPsetOpeningElementCommon.getFireExit() ? 1 : 0);
        else
            ps.setNull(4, Types.INTEGER);

        if (ifcPsetOpeningElementCommon.getProtectedOpening() != null)
            ps.setInt(5, ifcPsetOpeningElementCommon.getProtectedOpening() ? 1 : 0);
        else
            ps.setNull(5, Types.INTEGER);

        if (ifcPsetOpeningElementCommon.getParallelJambs() != null)
            ps.setInt(6, ifcPsetOpeningElementCommon.getParallelJambs() ? 1 : 0);
        else
            ps.setNull(6, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCPSETOPENINGELEMENTC));
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
