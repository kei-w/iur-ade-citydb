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
import org.citygml4j.ade.iur.model.urt.Agency;
import org.citygml4j.ade.iur.model.urt.AgencyProperty;
import org.citygml4j.ade.iur.model.urt.Route;
import org.citygml4j.ade.iur.model.urt.RouteProperty;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class RouteImporter implements PublicTransitModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;
    private final DescriptionImporter descriptionImporter;
    private final RouteToAgencyImporter routeToAgencyImporter;
    private final RouteToRouteImporter routeToRouteImporter;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public RouteImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.ROUTE)) + " " +
                "(id, shortname, longname, description_id, type, type_codespace, url, color, textcolor, updatedate," +
                "originstop, viastop, destinationstop, routesortorder, continuouspickup, continuouspickup_codespace," +
                "continuousdropoff, continuousdropoff_codespace, lod0multicurve) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
        descriptionImporter = manager.getImporter(DescriptionImporter.class);
        routeToAgencyImporter = manager.getImporter(RouteToAgencyImporter.class);
        routeToRouteImporter = manager.getImporter(RouteToRouteImporter.class);
        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(Route route, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(route, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, route.getShortName());

        ps.setString(3, route.getLongName());

        long routeDescriptionId = 0;
        if (route.getRouteDescription() != null && route.getRouteDescription().isSetObject())
            routeDescriptionId = descriptionImporter.doImport(route.getRouteDescription().getObject());

        if (routeDescriptionId != 0)
            ps.setLong(4, routeDescriptionId);
        else
            ps.setNull(4, Types.NULL);

        if (route.getType() != null && route.getType().isSetValue()) {
            ps.setString(5, route.getType().getValue());
            ps.setString(6, route.getType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        ps.setString(7, route.getUrl());

        ps.setString(8, route.getColor());

        ps.setString(9, route.getTextColor());

        if (route.getUpdateDate() != null)
            ps.setDate(10, Date.valueOf(route.getUpdateDate()));
        else
            ps.setNull(10, Types.DATE);

        ps.setString(11, route.getOriginStop());

        ps.setString(12, route.getViaStop());

        ps.setString(13, route.getDestinationStop());

        if (route.getRouteSortOrder() != null)
            ps.setInt(14, route.getRouteSortOrder());
        else
            ps.setNull(14, Types.INTEGER);

        if (route.getContinuousPickup() != null && route.getContinuousPickup().isSetValue()) {
            ps.setString(15, route.getContinuousPickup().getValue());
            ps.setString(16, route.getContinuousPickup().getCodeSpace());
        } else {
            ps.setNull(15, Types.VARCHAR);
            ps.setNull(16, Types.VARCHAR);
        }

        if (route.getContinuousDropOff() != null && route.getContinuousDropOff().isSetValue()) {
            ps.setString(17, route.getContinuousDropOff().getValue());
            ps.setString(18, route.getContinuousDropOff().getCodeSpace());
        } else {
            ps.setNull(17, Types.VARCHAR);
            ps.setNull(18, Types.VARCHAR);
        }

        GeometryObject lod0MultiCurve = null;
        if (route.getLod0MultiCurve() != null) {
            lod0MultiCurve = geometryConverter.getMultiCurve(route.getLod0MultiCurve());
            route.getLod0MultiCurve().unsetMultiCurve();
        }

        if (lod0MultiCurve != null)
            ps.setObject(19, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lod0MultiCurve, connection));
        else
            ps.setNull(19, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (route.getAgency() != null) {
            AgencyProperty property = route.getAgency();
            Agency cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                routeToAgencyImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.ROUTE_TO_AGENCY), objectId, "route_id", href, "agency_id");
            }
        }
        if (route.getParentRoute() != null) {
            RouteProperty property = route.getParentRoute();
            Route cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                routeToRouteImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.ROUTE_TO_ROUTE), objectId, "route_id", href, "route_id_2");
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
