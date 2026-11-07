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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urt.Calendar;

import java.sql.*;

public class CalendarExporter implements PublicTransitModuleExporter {
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;

    public CalendarExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CALENDAR);
        publicTransitExporter = manager.getExporter(PublicTransitExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table publicTransit = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSIT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(publicTransit, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        publicTransitExporter.addProjection(select, publicTransit, "pt_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(Calendar calendar, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(calendar, objectId, "pt_", rs);
                doExport(calendar, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("monday", prefix + "monday"),
                table.getColumn("tuesday", prefix + "tuesday"),
                table.getColumn("wednesday", prefix + "wednesday"),
                table.getColumn("thursday", prefix + "thursday"),
                table.getColumn("friday", prefix + "friday"),
                table.getColumn("saturday", prefix + "saturday"),
                table.getColumn("sunday", prefix + "sunday"),
                table.getColumn("startdate", prefix + "startdate"),
                table.getColumn("enddate", prefix + "enddate"));

        return select;
    }

    public void doExport(Calendar calendar, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        boolean monday = rs.getBoolean(prefix + "monday");
        if (!rs.wasNull())
            calendar.setMonday(monday);

        boolean tuesday = rs.getBoolean(prefix + "tuesday");
        if (!rs.wasNull())
            calendar.setTuesday(tuesday);

        boolean wednesday = rs.getBoolean(prefix + "wednesday");
        if (!rs.wasNull())
            calendar.setWednesday(wednesday);

        boolean thursday = rs.getBoolean(prefix + "thursday");
        if (!rs.wasNull())
            calendar.setThursday(thursday);

        boolean friday = rs.getBoolean(prefix + "friday");
        if (!rs.wasNull())
            calendar.setFriday(friday);

        boolean saturday = rs.getBoolean(prefix + "saturday");
        if (!rs.wasNull())
            calendar.setSaturday(saturday);

        boolean sunday = rs.getBoolean(prefix + "sunday");
        if (!rs.wasNull())
            calendar.setSunday(sunday);

        Date startDate = rs.getDate(prefix + "startdate");
        if (!rs.wasNull())
            calendar.setStartDate(startDate.toLocalDate());

        Date endDate = rs.getDate(prefix + "enddate");
        if (!rs.wasNull())
            calendar.setEndDate(endDate.toLocalDate());
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
