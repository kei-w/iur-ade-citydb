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
import org.citygml4j.ade.iur.model.urf.LandUseDiversion;

import java.sql.*;

public class LandUseDiversionImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionImporter urbanFunctionImporter;
    private final FarmlandConversionTypeImporter farmlandConversionTypeImporter;
    private final ForestConversionTypeImporter forestConversionTypeImporter;

    private int batchCounter;

    public LandUseDiversionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.LANDUSEDIVERSION_2)) + " " +
                "(id, farmlandconversiontype_id, forestconversiontype_id) " +
                "values (?, ?, ?)");

        urbanFunctionImporter = manager.getImporter(UrbanFunctionImporter.class);
        farmlandConversionTypeImporter = manager.getImporter(FarmlandConversionTypeImporter.class);
        forestConversionTypeImporter = manager.getImporter(ForestConversionTypeImporter.class);
    }

    public void doImport(LandUseDiversion landUseDiversion, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFunctionImporter.doImport(landUseDiversion, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        long farmlandConversionTypeId = 0;
        if (landUseDiversion.getFarmlandConversionType() != null && landUseDiversion.getFarmlandConversionType().isSetObject())
            farmlandConversionTypeId = farmlandConversionTypeImporter.doImport(landUseDiversion.getFarmlandConversionType().getObject());

        if (farmlandConversionTypeId != 0)
            ps.setLong(2, farmlandConversionTypeId);
        else
            ps.setNull(2, Types.NULL);

        long forestConversionTypeId = 0;
        if (landUseDiversion.getForestConversionType() != null && landUseDiversion.getForestConversionType().isSetObject())
            forestConversionTypeId = forestConversionTypeImporter.doImport(landUseDiversion.getForestConversionType().getObject());

        if (forestConversionTypeId != 0)
            ps.setLong(3, forestConversionTypeId);
        else
            ps.setNull(3, Types.NULL);

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
