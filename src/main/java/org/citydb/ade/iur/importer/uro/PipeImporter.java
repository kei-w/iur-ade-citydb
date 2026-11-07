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
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.Pipe;
import org.citygml4j.ade.iur.model.uro.SewerPipe;

import java.sql.*;

public class PipeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UtilityLinkImporter utilityLinkImporter;

    private int batchCounter;

    public PipeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PIPE)) + " " +
                "(id, objectclass_id, innerdiamiter, innerdiamiter_uom, outerdiamiter, outerdiamiter_uom, sleevetype," +
                "sleevetype_codespace, slope, slope_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        utilityLinkImporter = manager.getImporter(UtilityLinkImporter.class);
    }

    public void doImport(Pipe pipe, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        utilityLinkImporter.doImport(pipe, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        if (pipe.getInnerDiamiter() != null && pipe.getInnerDiamiter().isSetValue()) {
            ps.setDouble(3, pipe.getInnerDiamiter().getValue());
            ps.setString(4, pipe.getInnerDiamiter().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (pipe.getOuterDiamiter() != null && pipe.getOuterDiamiter().isSetValue()) {
            ps.setDouble(5, pipe.getOuterDiamiter().getValue());
            ps.setString(6, pipe.getOuterDiamiter().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (pipe.getSleeveType() != null && pipe.getSleeveType().isSetValue()) {
            ps.setString(7, pipe.getSleeveType().getValue());
            ps.setString(8, pipe.getSleeveType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (pipe instanceof SewerPipe) {
            SewerPipe sewerPipe = (SewerPipe) pipe;
            if (sewerPipe.getSlope() != null && sewerPipe.getSlope().isSetValue()) {
                ps.setDouble(9, sewerPipe.getSlope().getValue());
                ps.setString(10, sewerPipe.getSlope().getUom());
            } else {
                ps.setNull(9, Types.DOUBLE);
                ps.setNull(10, Types.VARCHAR);
            }
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
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
