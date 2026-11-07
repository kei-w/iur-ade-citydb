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
import org.citygml4j.ade.iur.model.urt.AgencyProperty;
import org.citygml4j.ade.iur.model.urt.Attribution;
import org.citygml4j.ade.iur.model.urt.RouteProperty;
import org.citygml4j.ade.iur.model.urt.TripProperty;

import java.sql.*;

public class AttributionExporter implements PublicTransitModuleExporter {
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psAttributionAgency;
    private final PreparedStatement psAttributionRoute;
    private final PreparedStatement psTrip;

    public AttributionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.ATTRIBUTION);
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
        Table attributionAgencys = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ATTRIBUTI_TO_AGENCY)));
        Select attributionAgencySelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, attributionAgencys.getColumn("agency_id")))
                .addSelection(ComparisonFactory.equalTo(attributionAgencys.getColumn("attribution_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psAttributionAgency = connection.prepareStatement(attributionAgencySelect.toString());
        Table attributionRoutes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ATTRIBUTI_TO_ROUTE)));
        Select attributionRouteSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, attributionRoutes.getColumn("route_id")))
                .addSelection(ComparisonFactory.equalTo(attributionRoutes.getColumn("attribution_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psAttributionRoute = connection.prepareStatement(attributionRouteSelect.toString());
        Table trips = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ATTRIBUTI_TO_TRIP)));
        Select tripSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, trips.getColumn("trip_id")))
                .addSelection(ComparisonFactory.equalTo(trips.getColumn("attribution_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psTrip = connection.prepareStatement(tripSelect.toString());
    }

    public void doExport(Attribution attribution, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(attribution, objectId, "pt_", rs);
                doExport(attribution, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("organizationname", prefix + "organizationname"),
                table.getColumn("isproducer", prefix + "isproducer"),
                table.getColumn("isoperator", prefix + "isoperator"),
                table.getColumn("isauthority", prefix + "isauthority"),
                table.getColumn("url", prefix + "url"),
                table.getColumn("email", prefix + "email"),
                table.getColumn("phonenumber", prefix + "phonenumber"));

        return select;
    }

    public void doExport(Attribution attribution, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        attribution.setOrganizationName(rs.getString(prefix + "organizationname"));

        boolean isProducer = rs.getBoolean(prefix + "isproducer");
        if (!rs.wasNull())
            attribution.setIsProducer(isProducer);

        boolean isOperator = rs.getBoolean(prefix + "isoperator");
        if (!rs.wasNull())
            attribution.setIsOperator(isOperator);

        boolean isAuthority = rs.getBoolean(prefix + "isauthority");
        if (!rs.wasNull())
            attribution.setIsAuthority(isAuthority);

        attribution.setUrl(rs.getString(prefix + "url"));

        attribution.setEmail(rs.getString(prefix + "email"));

        attribution.setPhoneNumber(rs.getString(prefix + "phonenumber"));

        psAttributionAgency.setLong(1, objectId);
        try (ResultSet agencyRs = psAttributionAgency.executeQuery()) {
            while (agencyRs.next()) {
                AgencyProperty agencyProperty = new AgencyProperty();
                agencyProperty.setHref("#" + agencyRs.getString("gmlid"));
                attribution.setAgency(agencyProperty);
            }
        }

        psAttributionRoute.setLong(1, objectId);
        try (ResultSet routeRs = psAttributionRoute.executeQuery()) {
            while (routeRs.next()) {
                RouteProperty routeProperty = new RouteProperty();
                routeProperty.setHref("#" + routeRs.getString("gmlid"));
                attribution.setRoute(routeProperty);
            }
        }

        psTrip.setLong(1, objectId);
        try (ResultSet tripRs = psTrip.executeQuery()) {
            while (tripRs.next()) {
                TripProperty tripProperty = new TripProperty();
                tripProperty.setHref("#" + tripRs.getString("gmlid"));
                attribution.setTrip(tripProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psAttributionAgency.close();
        psAttributionRoute.close();
        psTrip.close();
    }
}
