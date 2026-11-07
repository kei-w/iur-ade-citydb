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
import org.citygml4j.ade.iur.model.uro.RiverFloodingRiskAttribute;

import java.sql.*;

public class RiverFloodingRiskAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public RiverFloodingRiskAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.RIVERFLOODINGRISKATTRI)) + " " +
                "(id, admintype, admintype_codespace, duration, duration_uom, scale, scale_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(RiverFloodingRiskAttribute riverFloodingRiskAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (riverFloodingRiskAttribute.getAdminType() != null && riverFloodingRiskAttribute.getAdminType().isSetValue()) {
            ps.setString(2, riverFloodingRiskAttribute.getAdminType().getValue());
            ps.setString(3, riverFloodingRiskAttribute.getAdminType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (riverFloodingRiskAttribute.getDuration() != null && riverFloodingRiskAttribute.getDuration().isSetValue()) {
            ps.setDouble(4, riverFloodingRiskAttribute.getDuration().getValue());
            ps.setString(5, riverFloodingRiskAttribute.getDuration().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (riverFloodingRiskAttribute.getScale() != null && riverFloodingRiskAttribute.getScale().isSetValue()) {
            ps.setString(6, riverFloodingRiskAttribute.getScale().getValue());
            ps.setString(7, riverFloodingRiskAttribute.getScale().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.RIVERFLOODINGRISKATTRI));
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
