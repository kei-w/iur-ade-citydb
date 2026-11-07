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

package org.citydb.ade.iur.importer.urf;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.DevelopmentProject;

import java.sql.*;

public class DevelopmentProjectImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionImporter urbanFunctionImporter;
    private final ResidentialDevelopmentTypeImporter residentialDevelopmentTypeImporter;
    private final UrbanDevelopmentProjectTypeImporter urbanDevelopmentProjectTypeImporter;
    private final AgriculturalDevelopmentProjectTypeImporter agriculturalDevelopmentProjectTypeImporter;

    private int batchCounter;

    public DevelopmentProjectImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT)) + " " +
                "(id, residentialdevelopmenttyp_id, urbandevelopmenttype_id, agriculturaldevelopmentty_id) " +
                "values (?, ?, ?, ?)");

        urbanFunctionImporter = manager.getImporter(UrbanFunctionImporter.class);
        residentialDevelopmentTypeImporter = manager.getImporter(ResidentialDevelopmentTypeImporter.class);
        urbanDevelopmentProjectTypeImporter = manager.getImporter(UrbanDevelopmentProjectTypeImporter.class);
        agriculturalDevelopmentProjectTypeImporter = manager.getImporter(AgriculturalDevelopmentProjectTypeImporter.class);
    }

    public void doImport(DevelopmentProject developmentProject, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFunctionImporter.doImport(developmentProject, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        long residentialDevelopmentTypeId = 0;
        if (developmentProject.getResidentialDevelopmentType() != null && developmentProject.getResidentialDevelopmentType().isSetObject())
            residentialDevelopmentTypeId = residentialDevelopmentTypeImporter.doImport(developmentProject.getResidentialDevelopmentType().getObject());

        if (residentialDevelopmentTypeId != 0)
            ps.setLong(2, residentialDevelopmentTypeId);
        else
            ps.setNull(2, Types.NULL);

        long urbanDevelopmentTypeId = 0;
        if (developmentProject.getUrbanDevelopmentType() != null && developmentProject.getUrbanDevelopmentType().isSetObject())
            urbanDevelopmentTypeId = urbanDevelopmentProjectTypeImporter.doImport(developmentProject.getUrbanDevelopmentType().getObject());

        if (urbanDevelopmentTypeId != 0)
            ps.setLong(3, urbanDevelopmentTypeId);
        else
            ps.setNull(3, Types.NULL);

        long agriculturalDevelopmentTypeId = 0;
        if (developmentProject.getAgriculturalDevelopmentType() != null && developmentProject.getAgriculturalDevelopmentType().isSetObject())
            agriculturalDevelopmentTypeId = agriculturalDevelopmentProjectTypeImporter.doImport(developmentProject.getAgriculturalDevelopmentType().getObject());

        if (agriculturalDevelopmentTypeId != 0)
            ps.setLong(4, agriculturalDevelopmentTypeId);
        else
            ps.setNull(4, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);
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
