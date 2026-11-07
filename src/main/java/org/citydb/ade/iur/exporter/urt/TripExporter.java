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
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urt.CalendarDateProperty;
import org.citygml4j.ade.iur.model.urt.CalendarProperty;
import org.citygml4j.ade.iur.model.urt.OfficeProperty;
import org.citygml4j.ade.iur.model.urt.RouteProperty;
import org.citygml4j.ade.iur.model.urt.ShapeProperty;
import org.citygml4j.ade.iur.model.urt.Trip;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class TripExporter implements PublicTransitModuleExporter {
    private final CityGMLExportHelper helper;
    private final ExportManager manager;
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psTripRoute;
    private final PreparedStatement psTripCalendar;
    private final PreparedStatement psCalendarDate;
    private final PreparedStatement psOffice;
    private final PreparedStatement psShape;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public TripExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.TRIP);
        publicTransitExporter = manager.getExporter(PublicTransitExporter.class);
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table publicTransit = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSIT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(publicTransit, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        publicTransitExporter.addProjection(select, publicTransit, "pt_");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table tripRoutes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRIP_TO_ROUTE)));
        Select tripRouteSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, tripRoutes.getColumn("route_id")))
                .addSelection(ComparisonFactory.equalTo(tripRoutes.getColumn("trip_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psTripRoute = connection.prepareStatement(tripRouteSelect.toString());
        Table tripCalendars = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRIP_TO_CALENDAR)));
        Select tripCalendarSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, tripCalendars.getColumn("calendar_id")))
                .addSelection(ComparisonFactory.equalTo(tripCalendars.getColumn("trip_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psTripCalendar = connection.prepareStatement(tripCalendarSelect.toString());
        Table calendarDates = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRIP_TO_CALENDARD)));
        Select calendarDateSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, calendarDates.getColumn("calendardate_id")))
                .addSelection(ComparisonFactory.equalTo(calendarDates.getColumn("trip_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psCalendarDate = connection.prepareStatement(calendarDateSelect.toString());
        Table offices = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRIP_TO_OFFICE)));
        Select officeSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, offices.getColumn("office_id")))
                .addSelection(ComparisonFactory.equalTo(offices.getColumn("trip_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psOffice = connection.prepareStatement(officeSelect.toString());
        Table shapes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRIP_TO_SHAPE)));
        Select shapeSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, shapes.getColumn("shape_id")))
                .addSelection(ComparisonFactory.equalTo(shapes.getColumn("trip_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psShape = connection.prepareStatement(shapeSelect.toString());
    }

    public void doExport(Trip trip, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(trip, objectId, "pt_", rs);
                doExport(trip, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), trip, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("headsign", prefix + "headsign"),
                table.getColumn("shortname", prefix + "shortname"),
                table.getColumn("directionid", prefix + "directionid"),
                table.getColumn("directionid_codespace", prefix + "directionid_codespace"),
                table.getColumn("blockid", prefix + "blockid"),
                table.getColumn("wheelchairaccessible", prefix + "wheelchairaccessible"),
                table.getColumn("wheelchairaccessib_codespace", prefix + "wheelchairaccessib_codespace"),
                table.getColumn("bikeallowed", prefix + "bikeallowed"),
                table.getColumn("bikeallowed_codespace", prefix + "bikeallowed_codespace"),
                table.getColumn("symbol", prefix + "symbol"),
                helper.getGeometryColumn(table.getColumn("lod0multicurve"), prefix + "lod0multicurve"));

        return select;
    }

    public void doExport(Trip trip, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        trip.setHeadsign(rs.getString(prefix + "headsign"));

        trip.setShortName(rs.getString(prefix + "shortname"));

        String directionId = rs.getString(prefix + "directionid");
        if (!rs.wasNull()) {
            Code code = new Code(directionId);
            code.setCodeSpace(rs.getString(prefix + "directionid_codespace"));
            trip.setDirectionId(code);
        }

        trip.setBlockId(rs.getString(prefix + "blockid"));

        String wheelchairAccessible = rs.getString(prefix + "wheelchairaccessible");
        if (!rs.wasNull()) {
            Code code = new Code(wheelchairAccessible);
            code.setCodeSpace(rs.getString(prefix + "wheelchairaccessib_codespace"));
            trip.setWheelchairAccessible(code);
        }

        String bikeAllowed = rs.getString(prefix + "bikeallowed");
        if (!rs.wasNull()) {
            Code code = new Code(bikeAllowed);
            code.setCodeSpace(rs.getString(prefix + "bikeallowed_codespace"));
            trip.setBikeAllowed(code);
        }

        trip.setSymbol(rs.getString(prefix + "symbol"));

        psTripRoute.setLong(1, objectId);
        try (ResultSet routeRs = psTripRoute.executeQuery()) {
            while (routeRs.next()) {
                RouteProperty routeProperty = new RouteProperty();
                routeProperty.setHref("#" + routeRs.getString("gmlid"));
                trip.setRoute(routeProperty);
            }
        }

        psTripCalendar.setLong(1, objectId);
        try (ResultSet calendarRs = psTripCalendar.executeQuery()) {
            while (calendarRs.next()) {
                CalendarProperty calendarProperty = new CalendarProperty();
                calendarProperty.setHref("#" + calendarRs.getString("gmlid"));
                trip.setCalendar(calendarProperty);
            }
        }

        psCalendarDate.setLong(1, objectId);
        try (ResultSet calendarDateRs = psCalendarDate.executeQuery()) {
            while (calendarDateRs.next()) {
                CalendarDateProperty calendarDateProperty = new CalendarDateProperty();
                calendarDateProperty.setHref("#" + calendarDateRs.getString("gmlid"));
                trip.setCalendarDate(calendarDateProperty);
            }
        }

        psOffice.setLong(1, objectId);
        try (ResultSet officeRs = psOffice.executeQuery()) {
            while (officeRs.next()) {
                OfficeProperty officeProperty = new OfficeProperty();
                officeProperty.setHref("#" + officeRs.getString("gmlid"));
                trip.setOffice(officeProperty);
            }
        }

        psShape.setLong(1, objectId);
        try (ResultSet shapeRs = psShape.executeQuery()) {
            while (shapeRs.next()) {
                ShapeProperty shapeProperty = new ShapeProperty();
                shapeProperty.setHref("#" + shapeRs.getString("gmlid"));
                trip.setShape(shapeProperty);
            }
        }

        Object lod0MultiCurveObject = rs.getObject(prefix + "lod0multicurve");
        if (!rs.wasNull()) {
            GeometryObject lod0MultiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(lod0MultiCurveObject);
            if (lod0MultiCurve != null)
                trip.setLod0MultiCurve(gmlConverter.getMultiCurveProperty(lod0MultiCurve));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psTripRoute.close();
        psTripCalendar.close();
        psCalendarDate.close();
        psOffice.close();
        psShape.close();
    }
}
