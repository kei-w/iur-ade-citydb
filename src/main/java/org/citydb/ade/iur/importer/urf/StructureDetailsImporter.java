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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.StructureDetails;

import java.sql.*;

public class StructureDetailsImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public StructureDetailsImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.STRUCTUREDETAILS)) + " " +
                "(id, startlocation, endlocation, vialocations, length, length_uom, structuretype," +
                "structuretype_codespace, minimumwidth, minimumwidth_uom, maximumwidth, maximumwidth_uom," +
                "standardwidth, standardwidth_uom, crosstype, crosstype_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(StructureDetails structureDetails) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.STRUCTUREDETAILS_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, structureDetails.getStartLocation());

        ps.setString(3, structureDetails.getEndLocation());

        ps.setString(4, structureDetails.getViaLocations());

        if (structureDetails.getLength() != null && structureDetails.getLength().isSetValue()) {
            ps.setDouble(5, structureDetails.getLength().getValue());
            ps.setString(6, structureDetails.getLength().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (structureDetails.getStructureType() != null && structureDetails.getStructureType().isSetValue()) {
            ps.setString(7, structureDetails.getStructureType().getValue());
            ps.setString(8, structureDetails.getStructureType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (structureDetails.getMinimumWidth() != null && structureDetails.getMinimumWidth().isSetValue()) {
            ps.setDouble(9, structureDetails.getMinimumWidth().getValue());
            ps.setString(10, structureDetails.getMinimumWidth().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (structureDetails.getMaximumWidth() != null && structureDetails.getMaximumWidth().isSetValue()) {
            ps.setDouble(11, structureDetails.getMaximumWidth().getValue());
            ps.setString(12, structureDetails.getMaximumWidth().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (structureDetails.getStandardWidth() != null && structureDetails.getStandardWidth().isSetValue()) {
            ps.setDouble(13, structureDetails.getStandardWidth().getValue());
            ps.setString(14, structureDetails.getStandardWidth().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (structureDetails.getCrossType() != null && structureDetails.getCrossType().isSetValue()) {
            ps.setString(15, structureDetails.getCrossType().getValue());
            ps.setString(16, structureDetails.getCrossType().getCodeSpace());
        } else {
            ps.setNull(15, Types.VARCHAR);
            ps.setNull(16, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.STRUCTUREDETAILS));

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
