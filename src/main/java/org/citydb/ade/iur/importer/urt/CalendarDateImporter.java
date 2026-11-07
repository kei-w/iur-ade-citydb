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
import org.citygml4j.ade.iur.model.urt.CalendarDate;
import org.citygml4j.ade.iur.model.urt.CalendarProperty;

import java.sql.*;

public class CalendarDateImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;
    private final CalendarDateToCalendarImporter calendarDateToCalendarImporter;

    private int batchCounter;

    public CalendarDateImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CALENDARDATE)) + " " +
                "(id, date_, exceptiontype, exceptiontype_codespace) " +
                "values (?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
        calendarDateToCalendarImporter = manager.getImporter(CalendarDateToCalendarImporter.class);
    }

    public void doImport(CalendarDate calendarDate, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(calendarDate, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (calendarDate.getDate() != null)
            ps.setDate(2, Date.valueOf(calendarDate.getDate()));
        else
            ps.setNull(2, Types.DATE);

        if (calendarDate.getExceptionType() != null && calendarDate.getExceptionType().isSetValue()) {
            ps.setString(3, calendarDate.getExceptionType().getValue());
            ps.setString(4, calendarDate.getExceptionType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (calendarDate.getCalendar() != null) {
            CalendarProperty property = calendarDate.getCalendar();
            Calendar cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                calendarDateToCalendarImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.CALENDARD_TO_CALENDAR), objectId, "calendardate_id", href, "calendar_id");
            }
        }
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
