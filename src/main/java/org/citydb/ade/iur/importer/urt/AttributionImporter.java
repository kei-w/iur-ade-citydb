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
import org.citygml4j.ade.iur.model.urt.Agency;
import org.citygml4j.ade.iur.model.urt.AgencyProperty;
import org.citygml4j.ade.iur.model.urt.Attribution;
import org.citygml4j.ade.iur.model.urt.Route;
import org.citygml4j.ade.iur.model.urt.RouteProperty;
import org.citygml4j.ade.iur.model.urt.Trip;
import org.citygml4j.ade.iur.model.urt.TripProperty;

import java.sql.*;

public class AttributionImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;
    private final AttributionToAgencyImporter attributionToAgencyImporter;
    private final AttributionToRouteImporter attributionToRouteImporter;
    private final AttributionToTripImporter attributionToTripImporter;

    private int batchCounter;

    public AttributionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.ATTRIBUTION)) + " " +
                "(id, organizationname, isproducer, isoperator, isauthority, url, email, phonenumber) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
        attributionToAgencyImporter = manager.getImporter(AttributionToAgencyImporter.class);
        attributionToRouteImporter = manager.getImporter(AttributionToRouteImporter.class);
        attributionToTripImporter = manager.getImporter(AttributionToTripImporter.class);
    }

    public void doImport(Attribution attribution, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(attribution, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, attribution.getOrganizationName());

        if (attribution.getIsProducer() != null)
            ps.setInt(3, attribution.getIsProducer() ? 1 : 0);
        else
            ps.setNull(3, Types.INTEGER);

        if (attribution.getIsOperator() != null)
            ps.setInt(4, attribution.getIsOperator() ? 1 : 0);
        else
            ps.setNull(4, Types.INTEGER);

        if (attribution.getIsAuthority() != null)
            ps.setInt(5, attribution.getIsAuthority() ? 1 : 0);
        else
            ps.setNull(5, Types.INTEGER);

        ps.setString(6, attribution.getUrl());

        ps.setString(7, attribution.getEmail());

        ps.setString(8, attribution.getPhoneNumber());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (attribution.getAgency() != null) {
            AgencyProperty property = attribution.getAgency();
            Agency cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                attributionToAgencyImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.ATTRIBUTI_TO_AGENCY), objectId, "attribution_id", href, "agency_id");
            }
        }
        if (attribution.getRoute() != null) {
            RouteProperty property = attribution.getRoute();
            Route cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                attributionToRouteImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.ATTRIBUTI_TO_ROUTE), objectId, "attribution_id", href, "route_id");
            }
        }
        if (attribution.getTrip() != null) {
            TripProperty property = attribution.getTrip();
            Trip cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                attributionToTripImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.ATTRIBUTI_TO_TRIP), objectId, "attribution_id", href, "trip_id");
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
