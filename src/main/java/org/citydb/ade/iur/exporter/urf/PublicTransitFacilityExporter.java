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

package org.citydb.ade.iur.exporter.urf;

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
import org.citygml4j.ade.iur.model.urf.PublicTransitFacility;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class PublicTransitFacilityExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final PreparedStatement ps;

    public PublicTransitFacilityExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSITFACILITY);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(PublicTransitFacility publicTransitFacility, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(publicTransitFacility, objectId, "uf_", rs);
                doExport(publicTransitFacility, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("id_", prefix + "id_"),
                table.getColumn("companyname", prefix + "companyname"),
                table.getColumn("companytype", prefix + "companytype"),
                table.getColumn("companytype_codespace", prefix + "companytype_codespace"),
                table.getColumn("facilitytype", prefix + "facilitytype"),
                table.getColumn("facilitytype_codespace", prefix + "facilitytype_codespace"),
                table.getColumn("sectionname", prefix + "sectionname"),
                table.getColumn("frequencyofservice", prefix + "frequencyofservice"),
                table.getColumn("numberofcustomers", prefix + "numberofcustomers"));

        return select;
    }

    public void doExport(PublicTransitFacility publicTransitFacility, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        publicTransitFacility.setIdentifier(rs.getString(prefix + "id_"));

        publicTransitFacility.setCompanyName(rs.getString(prefix + "companyname"));

        String companyType = rs.getString(prefix + "companytype");
        if (!rs.wasNull()) {
            Code code = new Code(companyType);
            code.setCodeSpace(rs.getString(prefix + "companytype_codespace"));
            publicTransitFacility.setCompanyType(code);
        }

        String facilityType = rs.getString(prefix + "facilitytype");
        if (!rs.wasNull()) {
            Code code = new Code(facilityType);
            code.setCodeSpace(rs.getString(prefix + "facilitytype_codespace"));
            publicTransitFacility.setFacilityType(code);
        }

        publicTransitFacility.setSectionName(rs.getString(prefix + "sectionname"));

        int frequencyOfService = rs.getInt(prefix + "frequencyofservice");
        if (!rs.wasNull())
            publicTransitFacility.setFrequencyOfService(frequencyOfService);

        int numberOfCustomers = rs.getInt(prefix + "numberofcustomers");
        if (!rs.wasNull())
            publicTransitFacility.setNumberOfCustomers(numberOfCustomers);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
