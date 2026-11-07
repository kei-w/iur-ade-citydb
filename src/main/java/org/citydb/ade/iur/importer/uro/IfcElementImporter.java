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
import org.citygml4j.ade.iur.model.uro.IfcBuildingElement;
import org.citygml4j.ade.iur.model.uro.IfcElement;
import org.citygml4j.ade.iur.model.uro.IfcOpeningElement;

import java.sql.*;

public class IfcElementImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final IfcBuildingElementImporter ifcBuildingElementImporter;
    private final IfcOpeningElementImporter ifcOpeningElementImporter;

    private int batchCounter;

    public IfcElementImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCELEMENT)) + " " +
                "(id, objectclass_id, tag) " +
                "values (?, ?, ?)");

        ifcBuildingElementImporter = manager.getImporter(IfcBuildingElementImporter.class);
        ifcOpeningElementImporter = manager.getImporter(IfcOpeningElementImporter.class);
    }

    public void doImport(IfcElement ifcElement, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(ifcElement.getClass()));

        ps.setString(3, ifcElement.getTag());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCELEMENT));

        if (ifcElement instanceof IfcBuildingElement)
            ifcBuildingElementImporter.doImport((IfcBuildingElement) ifcElement, objectId);

        if (ifcElement instanceof IfcOpeningElement)
            ifcOpeningElementImporter.doImport((IfcOpeningElement) ifcElement, objectId);
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
