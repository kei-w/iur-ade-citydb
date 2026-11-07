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
import org.citygml4j.ade.iur.model.urf.ThreeDimensionalExtent;

import java.sql.*;

public class ThreeDimensionalExtentImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionImporter urbanFunctionImporter;

    private int batchCounter;

    public ThreeDimensionalExtentImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.THREEDIMENSIONALEXTENT)) + " " +
                "(id, minimumdistance, minimumdistance_uom, maximumload, maximumload_uom) " +
                "values (?, ?, ?, ?, ?)");

        urbanFunctionImporter = manager.getImporter(UrbanFunctionImporter.class);
    }

    public void doImport(ThreeDimensionalExtent threeDimensionalExtent, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFunctionImporter.doImport(threeDimensionalExtent, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (threeDimensionalExtent.getMinimumDistance() != null && threeDimensionalExtent.getMinimumDistance().isSetValue()) {
            ps.setDouble(2, threeDimensionalExtent.getMinimumDistance().getValue());
            ps.setString(3, threeDimensionalExtent.getMinimumDistance().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (threeDimensionalExtent.getMaximumLoad() != null && threeDimensionalExtent.getMaximumLoad().isSetValue()) {
            ps.setDouble(4, threeDimensionalExtent.getMaximumLoad().getValue());
            ps.setString(5, threeDimensionalExtent.getMaximumLoad().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

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
