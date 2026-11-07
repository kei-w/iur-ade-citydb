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

package org.citydb.ade.iur.importer.urt;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urt.Calendar;

import java.sql.*;

public class CalendarImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;

    private int batchCounter;

    public CalendarImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CALENDAR)) + " " +
                "(id, monday, tuesday, wednesday, thursday, friday, saturday, sunday, startdate, enddate) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
    }

    public void doImport(Calendar calendar, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(calendar, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (calendar.getMonday() != null)
            ps.setInt(2, calendar.getMonday() ? 1 : 0);
        else
            ps.setNull(2, Types.INTEGER);

        if (calendar.getTuesday() != null)
            ps.setInt(3, calendar.getTuesday() ? 1 : 0);
        else
            ps.setNull(3, Types.INTEGER);

        if (calendar.getWednesday() != null)
            ps.setInt(4, calendar.getWednesday() ? 1 : 0);
        else
            ps.setNull(4, Types.INTEGER);

        if (calendar.getThursday() != null)
            ps.setInt(5, calendar.getThursday() ? 1 : 0);
        else
            ps.setNull(5, Types.INTEGER);

        if (calendar.getFriday() != null)
            ps.setInt(6, calendar.getFriday() ? 1 : 0);
        else
            ps.setNull(6, Types.INTEGER);

        if (calendar.getSaturday() != null)
            ps.setInt(7, calendar.getSaturday() ? 1 : 0);
        else
            ps.setNull(7, Types.INTEGER);

        if (calendar.getSunday() != null)
            ps.setInt(8, calendar.getSunday() ? 1 : 0);
        else
            ps.setNull(8, Types.INTEGER);

        if (calendar.getStartDate() != null)
            ps.setDate(9, Date.valueOf(calendar.getStartDate()));
        else
            ps.setNull(9, Types.DATE);

        if (calendar.getEndDate() != null)
            ps.setDate(10, Date.valueOf(calendar.getEndDate()));
        else
            ps.setNull(10, Types.DATE);

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
