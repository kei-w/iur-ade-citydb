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
import org.citygml4j.ade.iur.model.urt.FareAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class FareAttributeExporter implements PublicTransitModuleExporter {
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psFareAttributeAgency;

    public FareAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.FAREATTRIBUTE);
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
        Table fareAttributeAgencys = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FAREATTRI_TO_AGENCY)));
        Select fareAttributeAgencySelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, fareAttributeAgencys.getColumn("agency_id")))
                .addSelection(ComparisonFactory.equalTo(fareAttributeAgencys.getColumn("fareattribute_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psFareAttributeAgency = connection.prepareStatement(fareAttributeAgencySelect.toString());
    }

    public void doExport(FareAttribute fareAttribute, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(fareAttribute, objectId, "pt_", rs);
                doExport(fareAttribute, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("price", prefix + "price"),
                table.getColumn("currencytype", prefix + "currencytype"),
                table.getColumn("currencytype_codespace", prefix + "currencytype_codespace"),
                table.getColumn("paymentmethod", prefix + "paymentmethod"),
                table.getColumn("paymentmethod_codespace", prefix + "paymentmethod_codespace"),
                table.getColumn("transfers", prefix + "transfers"),
                table.getColumn("transfers_codespace", prefix + "transfers_codespace"),
                table.getColumn("transferduration", prefix + "transferduration"));

        return select;
    }

    public void doExport(FareAttribute fareAttribute, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        double price = rs.getDouble(prefix + "price");
        if (!rs.wasNull())
            fareAttribute.setPrice(price);

        String currencyType = rs.getString(prefix + "currencytype");
        if (!rs.wasNull()) {
            Code code = new Code(currencyType);
            code.setCodeSpace(rs.getString(prefix + "currencytype_codespace"));
            fareAttribute.setCurrencyType(code);
        }

        String paymentMethod = rs.getString(prefix + "paymentmethod");
        if (!rs.wasNull()) {
            Code code = new Code(paymentMethod);
            code.setCodeSpace(rs.getString(prefix + "paymentmethod_codespace"));
            fareAttribute.setPaymentMethod(code);
        }

        String transfers = rs.getString(prefix + "transfers");
        if (!rs.wasNull()) {
            Code code = new Code(transfers);
            code.setCodeSpace(rs.getString(prefix + "transfers_codespace"));
            fareAttribute.setTransfers(code);
        }

        int transferDuration = rs.getInt(prefix + "transferduration");
        if (!rs.wasNull())
            fareAttribute.setTransferDuration(transferDuration);

        psFareAttributeAgency.setLong(1, objectId);
        try (ResultSet agencyRs = psFareAttributeAgency.executeQuery()) {
            while (agencyRs.next()) {
                AgencyProperty agencyProperty = new AgencyProperty();
                agencyProperty.setHref("#" + agencyRs.getString("gmlid"));
                fareAttribute.setAgency(agencyProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psFareAttributeAgency.close();
    }
}
