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
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.urt.Calendar;
import org.citygml4j.ade.iur.model.urt.CalendarDate;
import org.citygml4j.ade.iur.model.urt.CalendarDateProperty;
import org.citygml4j.ade.iur.model.urt.CalendarProperty;
import org.citygml4j.ade.iur.model.urt.Office;
import org.citygml4j.ade.iur.model.urt.OfficeProperty;
import org.citygml4j.ade.iur.model.urt.Route;
import org.citygml4j.ade.iur.model.urt.RouteProperty;
import org.citygml4j.ade.iur.model.urt.Shape;
import org.citygml4j.ade.iur.model.urt.ShapeProperty;
import org.citygml4j.ade.iur.model.urt.Trip;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class TripImporter implements PublicTransitModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;
    private final TripToRouteImporter tripToRouteImporter;
    private final TripToCalendarImporter tripToCalendarImporter;
    private final TripToCalendarDateImporter tripToCalendarDateImporter;
    private final TripToOfficeImporter tripToOfficeImporter;
    private final TripToShapeImporter tripToShapeImporter;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public TripImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRIP)) + " " +
                "(id, headsign, shortname, directionid, directionid_codespace, blockid, wheelchairaccessible," +
                "wheelchairaccessib_codespace, bikeallowed, bikeallowed_codespace, symbol, lod0multicurve) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
        tripToRouteImporter = manager.getImporter(TripToRouteImporter.class);
        tripToCalendarImporter = manager.getImporter(TripToCalendarImporter.class);
        tripToCalendarDateImporter = manager.getImporter(TripToCalendarDateImporter.class);
        tripToOfficeImporter = manager.getImporter(TripToOfficeImporter.class);
        tripToShapeImporter = manager.getImporter(TripToShapeImporter.class);
        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(Trip trip, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(trip, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, trip.getHeadsign());

        ps.setString(3, trip.getShortName());

        if (trip.getDirectionId() != null && trip.getDirectionId().isSetValue()) {
            ps.setString(4, trip.getDirectionId().getValue());
            ps.setString(5, trip.getDirectionId().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        ps.setString(6, trip.getBlockId());

        if (trip.getWheelchairAccessible() != null && trip.getWheelchairAccessible().isSetValue()) {
            ps.setString(7, trip.getWheelchairAccessible().getValue());
            ps.setString(8, trip.getWheelchairAccessible().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (trip.getBikeAllowed() != null && trip.getBikeAllowed().isSetValue()) {
            ps.setString(9, trip.getBikeAllowed().getValue());
            ps.setString(10, trip.getBikeAllowed().getCodeSpace());
        } else {
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.VARCHAR);
        }

        ps.setString(11, trip.getSymbol());

        GeometryObject lod0MultiCurve = null;
        if (trip.getLod0MultiCurve() != null) {
            lod0MultiCurve = geometryConverter.getMultiCurve(trip.getLod0MultiCurve());
            trip.getLod0MultiCurve().unsetMultiCurve();
        }

        if (lod0MultiCurve != null)
            ps.setObject(12, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lod0MultiCurve, connection));
        else
            ps.setNull(12, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (trip.getRoute() != null) {
            RouteProperty property = trip.getRoute();
            Route cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                tripToRouteImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRIP_TO_ROUTE), objectId, "trip_id", href, "route_id");
            }
        }
        if (trip.getCalendar() != null) {
            CalendarProperty property = trip.getCalendar();
            Calendar cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                tripToCalendarImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRIP_TO_CALENDAR), objectId, "trip_id", href, "calendar_id");
            }
        }
        if (trip.getCalendarDate() != null) {
            CalendarDateProperty property = trip.getCalendarDate();
            CalendarDate cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                tripToCalendarDateImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRIP_TO_CALENDARD), objectId, "trip_id", href, "calendardate_id");
            }
        }
        if (trip.getOffice() != null) {
            OfficeProperty property = trip.getOffice();
            Office cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                tripToOfficeImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRIP_TO_OFFICE), objectId, "trip_id", href, "office_id");
            }
        }
        if (trip.getShape() != null) {
            ShapeProperty property = trip.getShape();
            Shape cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                tripToShapeImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.TRIP_TO_SHAPE), objectId, "trip_id", href, "shape_id");
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
