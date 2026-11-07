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
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urt.FareAttribute;
import org.citygml4j.ade.iur.model.urt.FareAttributeProperty;
import org.citygml4j.ade.iur.model.urt.FareRule;
import org.citygml4j.ade.iur.model.urt.Route;
import org.citygml4j.ade.iur.model.urt.RouteProperty;

import java.sql.*;

public class FareRuleImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final FareRuleToFareAttributeImporter fareRuleToFareAttributeImporter;
    private final FareRuleToRouteImporter fareRuleToRouteImporter;

    private int batchCounter;

    public FareRuleImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FARERULE)) + " " +
                "(id, originid, originid_codespace, destinationid, destinationid_codespace, containsid," +
                "containsid_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?)");

        fareRuleToFareAttributeImporter = manager.getImporter(FareRuleToFareAttributeImporter.class);
        fareRuleToRouteImporter = manager.getImporter(FareRuleToRouteImporter.class);
    }

    public void doImport(FareRule fareRule, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (fareRule.getOriginId() != null && fareRule.getOriginId().isSetValue()) {
            ps.setString(2, fareRule.getOriginId().getValue());
            ps.setString(3, fareRule.getOriginId().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (fareRule.getDestinationId() != null && fareRule.getDestinationId().isSetValue()) {
            ps.setString(4, fareRule.getDestinationId().getValue());
            ps.setString(5, fareRule.getDestinationId().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (fareRule.getContainsId() != null && fareRule.getContainsId().isSetValue()) {
            ps.setString(6, fareRule.getContainsId().getValue());
            ps.setString(7, fareRule.getContainsId().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FARERULE));

        if (fareRule.getFare() != null) {
            FareAttributeProperty property = fareRule.getFare();
            FareAttribute cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                fareRuleToFareAttributeImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.FARERULE_TO_FAREATTRI), objectId, "farerule_id", href, "fareattribute_id");
            }
        }
        if (fareRule.getRoute() != null) {
            RouteProperty property = fareRule.getRoute();
            Route cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                fareRuleToRouteImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.FARERULE_TO_ROUTE), objectId, "farerule_id", href, "route_id");
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
