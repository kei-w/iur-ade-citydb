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
import org.citygml4j.ade.iur.model.urt.AgencyProperty;
import org.citygml4j.ade.iur.model.urt.Description;
import org.citygml4j.ade.iur.model.urt.DescriptionProperty;
import org.citygml4j.ade.iur.model.urt.Route;
import org.citygml4j.ade.iur.model.urt.RouteProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class RouteExporter implements PublicTransitModuleExporter {
    private final CityGMLExportHelper helper;
    private final ExportManager manager;
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psRouteAgency;
    private final PreparedStatement psParentRoute;
    private final DescriptionExporter descriptionExporter;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public RouteExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.ROUTE);
        publicTransitExporter = manager.getExporter(PublicTransitExporter.class);
        descriptionExporter = manager.getExporter(DescriptionExporter.class);
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
        Table routeAgencys = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ROUTE_TO_AGENCY)));
        Select routeAgencySelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, routeAgencys.getColumn("agency_id")))
                .addSelection(ComparisonFactory.equalTo(routeAgencys.getColumn("route_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psRouteAgency = connection.prepareStatement(routeAgencySelect.toString());
        Table parentRoutes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ROUTE_TO_ROUTE)));
        Select parentRouteSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, parentRoutes.getColumn("route_id_2")))
                .addSelection(ComparisonFactory.equalTo(parentRoutes.getColumn("route_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psParentRoute = connection.prepareStatement(parentRouteSelect.toString());
    }

    public void doExport(Route route, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(route, objectId, "pt_", rs);
                doExport(route, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), route, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("shortname", prefix + "shortname"),
                table.getColumn("longname", prefix + "longname"),
                table.getColumn("description_id", prefix + "description_id"),
                table.getColumn("type", prefix + "type"),
                table.getColumn("type_codespace", prefix + "type_codespace"),
                table.getColumn("url", prefix + "url"),
                table.getColumn("color", prefix + "color"),
                table.getColumn("textcolor", prefix + "textcolor"),
                table.getColumn("updatedate", prefix + "updatedate"),
                table.getColumn("originstop", prefix + "originstop"),
                table.getColumn("viastop", prefix + "viastop"),
                table.getColumn("destinationstop", prefix + "destinationstop"),
                table.getColumn("routesortorder", prefix + "routesortorder"),
                table.getColumn("continuouspickup", prefix + "continuouspickup"),
                table.getColumn("continuouspickup_codespace", prefix + "continuouspickup_codespace"),
                table.getColumn("continuousdropoff", prefix + "continuousdropoff"),
                table.getColumn("continuousdropoff_codespace", prefix + "continuousdropoff_codespace"),
                helper.getGeometryColumn(table.getColumn("lod0multicurve"), prefix + "lod0multicurve"));

        return select;
    }

    public void doExport(Route route, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        route.setShortName(rs.getString(prefix + "shortname"));

        route.setLongName(rs.getString(prefix + "longname"));

        long routeDescriptionId = rs.getLong(prefix + "description_id");
        if (routeDescriptionId != 0) {
            Description routeDescription = new Description();
            descriptionExporter.doExport(routeDescription, routeDescriptionId);
            route.setRouteDescription(new DescriptionProperty(routeDescription));
        }

        String type = rs.getString(prefix + "type");
        if (!rs.wasNull()) {
            Code code = new Code(type);
            code.setCodeSpace(rs.getString(prefix + "type_codespace"));
            route.setType(code);
        }

        route.setUrl(rs.getString(prefix + "url"));

        route.setColor(rs.getString(prefix + "color"));

        route.setTextColor(rs.getString(prefix + "textcolor"));

        Date updateDate = rs.getDate(prefix + "updatedate");
        if (!rs.wasNull())
            route.setUpdateDate(updateDate.toLocalDate());

        route.setOriginStop(rs.getString(prefix + "originstop"));

        route.setViaStop(rs.getString(prefix + "viastop"));

        route.setDestinationStop(rs.getString(prefix + "destinationstop"));

        int routeSortOrder = rs.getInt(prefix + "routesortorder");
        if (!rs.wasNull())
            route.setRouteSortOrder(routeSortOrder);

        String continuousPickup = rs.getString(prefix + "continuouspickup");
        if (!rs.wasNull()) {
            Code code = new Code(continuousPickup);
            code.setCodeSpace(rs.getString(prefix + "continuouspickup_codespace"));
            route.setContinuousPickup(code);
        }

        String continuousDropOff = rs.getString(prefix + "continuousdropoff");
        if (!rs.wasNull()) {
            Code code = new Code(continuousDropOff);
            code.setCodeSpace(rs.getString(prefix + "continuousdropoff_codespace"));
            route.setContinuousDropOff(code);
        }

        psRouteAgency.setLong(1, objectId);
        try (ResultSet agencyRs = psRouteAgency.executeQuery()) {
            while (agencyRs.next()) {
                AgencyProperty agencyProperty = new AgencyProperty();
                agencyProperty.setHref("#" + agencyRs.getString("gmlid"));
                route.setAgency(agencyProperty);
            }
        }

        psParentRoute.setLong(1, objectId);
        try (ResultSet parentRouteRs = psParentRoute.executeQuery()) {
            while (parentRouteRs.next()) {
                RouteProperty parentRouteProperty = new RouteProperty();
                parentRouteProperty.setHref("#" + parentRouteRs.getString("gmlid"));
                route.setParentRoute(parentRouteProperty);
            }
        }

        Object lod0MultiCurveObject = rs.getObject(prefix + "lod0multicurve");
        if (!rs.wasNull()) {
            GeometryObject lod0MultiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(lod0MultiCurveObject);
            if (lod0MultiCurve != null)
                route.setLod0MultiCurve(gmlConverter.getMultiCurveProperty(lod0MultiCurve));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psRouteAgency.close();
        psParentRoute.close();
    }
}
