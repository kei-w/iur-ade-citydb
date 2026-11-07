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
import org.citygml4j.ade.iur.model.urf.BreakdownOfNominalAreaProperty;
import org.citygml4j.ade.iur.model.urf.UrbanParkAttribute;

import java.sql.*;

public class UrbanParkAttributeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanParkAttributeToBreakdownOfNominalAreaImporter urbanParkAttributeToBreakdownOfNominalAreaImporter;
    private final BreakdownOfNominalAreaImporter breakdownOfNominalAreaImporter;

    private int batchCounter;

    public UrbanParkAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URBANPARKATTRIBUTE)) + " " +
                "(id, parkcode, parkcode_codespace, startfrom) " +
                "values (?, ?, ?, ?)");

        urbanParkAttributeToBreakdownOfNominalAreaImporter = manager.getImporter(UrbanParkAttributeToBreakdownOfNominalAreaImporter.class);
        breakdownOfNominalAreaImporter = manager.getImporter(BreakdownOfNominalAreaImporter.class);
    }

    public long doImport(UrbanParkAttribute urbanParkAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.URBANPARKATTRIBUTE_SEQ));
        ps.setLong(1, objectId);

        if (urbanParkAttribute.getParkCode() != null && urbanParkAttribute.getParkCode().isSetValue()) {
            ps.setString(2, urbanParkAttribute.getParkCode().getValue());
            ps.setString(3, urbanParkAttribute.getParkCode().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (urbanParkAttribute.getStartFrom() != null)
            ps.setDate(4, Date.valueOf(urbanParkAttribute.getStartFrom()));
        else
            ps.setNull(4, Types.DATE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.URBANPARKATTRIBUTE));

        for (BreakdownOfNominalAreaProperty property : urbanParkAttribute.getBreakdownOfNominalArea()) {
            if (property.isSetObject()) {
                long breakdownOfNominalAreaId = breakdownOfNominalAreaImporter.doImport(property.getObject());
                urbanParkAttributeToBreakdownOfNominalAreaImporter.doImport(breakdownOfNominalAreaId, objectId);
            }
        }

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
