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
import org.citygml4j.ade.iur.model.urf.SedimentDisasterProneArea;

import java.sql.*;

public class SedimentDisasterProneAreaImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ZoneImporter zoneImporter;

    private int batchCounter;

    public SedimentDisasterProneAreaImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.SEDIMENTDISASTERPRONEA)) + " " +
                "(id, disastertype, disastertype_codespace, areatype, areatype_codespace, zonenumber, zonename," +
                "status, status_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        zoneImporter = manager.getImporter(ZoneImporter.class);
    }

    public void doImport(SedimentDisasterProneArea sedimentDisasterProneArea, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        zoneImporter.doImport(sedimentDisasterProneArea, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (sedimentDisasterProneArea.getDisasterType() != null && sedimentDisasterProneArea.getDisasterType().isSetValue()) {
            ps.setString(2, sedimentDisasterProneArea.getDisasterType().getValue());
            ps.setString(3, sedimentDisasterProneArea.getDisasterType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (sedimentDisasterProneArea.getAreaType() != null && sedimentDisasterProneArea.getAreaType().isSetValue()) {
            ps.setString(4, sedimentDisasterProneArea.getAreaType().getValue());
            ps.setString(5, sedimentDisasterProneArea.getAreaType().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        ps.setString(6, sedimentDisasterProneArea.getZoneNumber());

        ps.setString(7, sedimentDisasterProneArea.getZoneName());

        if (sedimentDisasterProneArea.getStatus() != null && sedimentDisasterProneArea.getStatus().isSetValue()) {
            ps.setString(8, sedimentDisasterProneArea.getStatus().getValue());
            ps.setString(9, sedimentDisasterProneArea.getStatus().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
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
