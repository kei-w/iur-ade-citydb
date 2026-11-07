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

package org.citydb.ade.iur.exporter.urt;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urt.CalendarDate;
import org.citygml4j.ade.iur.model.urt.CalendarProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class CalendarDateExporter implements PublicTransitModuleExporter {
    private final ExportManager manager;
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psCalendarDateCalendar;

    public CalendarDateExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CALENDARDATE);
        publicTransitExporter = manager.getExporter(PublicTransitExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table publicTransit = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSIT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(publicTransit, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        publicTransitExporter.addProjection(select, publicTransit, "pt_");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table calendarDateCalendars = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CALENDARD_TO_CALENDAR)));
        Select calendarDateCalendarSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, calendarDateCalendars.getColumn("calendar_id")))
                .addSelection(ComparisonFactory.equalTo(calendarDateCalendars.getColumn("calendardate_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psCalendarDateCalendar = connection.prepareStatement(calendarDateCalendarSelect.toString());
    }

    public void doExport(CalendarDate calendarDate, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(calendarDate, objectId, "pt_", rs);
                doExport(calendarDate, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), calendarDate, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("date_", prefix + "date_"),
                table.getColumn("exceptiontype", prefix + "exceptiontype"),
                table.getColumn("exceptiontype_codespace", prefix + "exceptiontype_codespace"));

        return select;
    }

    public void doExport(CalendarDate calendarDate, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        Date date = rs.getDate(prefix + "date_");
        if (!rs.wasNull())
            calendarDate.setDate(date.toLocalDate());

        String exceptionType = rs.getString(prefix + "exceptiontype");
        if (!rs.wasNull()) {
            Code code = new Code(exceptionType);
            code.setCodeSpace(rs.getString(prefix + "exceptiontype_codespace"));
            calendarDate.setExceptionType(code);
        }

        psCalendarDateCalendar.setLong(1, objectId);
        try (ResultSet calendarRs = psCalendarDateCalendar.executeQuery()) {
            while (calendarRs.next()) {
                CalendarProperty calendarProperty = new CalendarProperty();
                calendarProperty.setHref("#" + calendarRs.getString("gmlid"));
                calendarDate.setCalendar(calendarProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psCalendarDateCalendar.close();
    }
}
