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
import org.citygml4j.ade.iur.model.urf.ResidentialDevelopmentType;

import java.sql.*;

public class ResidentialDevelopmentTypeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public ResidentialDevelopmentTypeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.RESIDENTIALDEVELOPMENT)) + " " +
                "(id, id_, area, area_uom, projectperiod, purpose, status, status_codespace, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(ResidentialDevelopmentType residentialDevelopmentType) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.RESIDENTIALDEVELOP_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, residentialDevelopmentType.getId());

        if (residentialDevelopmentType.getArea() != null && residentialDevelopmentType.getArea().isSetValue()) {
            ps.setDouble(3, residentialDevelopmentType.getArea().getValue());
            ps.setString(4, residentialDevelopmentType.getArea().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        ps.setString(5, residentialDevelopmentType.getProjectPeriod());

        ps.setString(6, residentialDevelopmentType.getPurpose());

        if (residentialDevelopmentType.getStatus() != null && residentialDevelopmentType.getStatus().isSetValue()) {
            ps.setString(7, residentialDevelopmentType.getStatus().getValue());
            ps.setString(8, residentialDevelopmentType.getStatus().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        ps.setString(9, residentialDevelopmentType.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.RESIDENTIALDEVELOPMENT));

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
