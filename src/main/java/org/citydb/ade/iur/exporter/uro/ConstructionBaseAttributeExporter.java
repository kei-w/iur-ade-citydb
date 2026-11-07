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

package org.citydb.ade.iur.exporter.uro;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.ConstructionBaseAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.time.Year;

import java.sql.*;

public class ConstructionBaseAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public ConstructionBaseAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CONSTRUCTIONBASEATTRIB);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("admintype", "admintype_codespace", "administrator", "adminoffice", "operatortype",
                        "operatortype_codespace", "installertype", "installertype_codespace", "installer",
                        "structureordinance", "specification", "kana", "constructionstartyear", "completionyear",
                        "facilityage", "update_", "purpose", "purpose_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(ConstructionBaseAttribute constructionBaseAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String adminType = rs.getString("admintype");
                if (!rs.wasNull()) {
                    Code code = new Code(adminType);
                    code.setCodeSpace(rs.getString("admintype_codespace"));
                    constructionBaseAttribute.setAdminType(code);
                }

                constructionBaseAttribute.setAdministrator(rs.getString("administrator"));

                constructionBaseAttribute.setAdminOffice(rs.getString("adminoffice"));

                String operatorType = rs.getString("operatortype");
                if (!rs.wasNull()) {
                    Code code = new Code(operatorType);
                    code.setCodeSpace(rs.getString("operatortype_codespace"));
                    constructionBaseAttribute.setOperatorType(code);
                }

                String installerType = rs.getString("installertype");
                if (!rs.wasNull()) {
                    Code code = new Code(installerType);
                    code.setCodeSpace(rs.getString("installertype_codespace"));
                    constructionBaseAttribute.setInstallerType(code);
                }

                constructionBaseAttribute.setInstaller(rs.getString("installer"));

                constructionBaseAttribute.setStructureOrdinance(rs.getString("structureordinance"));

                constructionBaseAttribute.setSpecification(rs.getString("specification"));

                constructionBaseAttribute.setKana(rs.getString("kana"));

                Date constructionStartYear = rs.getDate("constructionstartyear");
                if (!rs.wasNull())
                    constructionBaseAttribute.setConstructionStartYear(Year.of(constructionStartYear.toLocalDate().getYear()));

                Date completionYear = rs.getDate("completionyear");
                if (!rs.wasNull())
                    constructionBaseAttribute.setCompletionYear(Year.of(completionYear.toLocalDate().getYear()));

                int facilityAge = rs.getInt("facilityage");
                if (!rs.wasNull())
                    constructionBaseAttribute.setFacilityAge(facilityAge);

                Date update = rs.getDate("update_");
                if (!rs.wasNull())
                    constructionBaseAttribute.setUpdate(update.toLocalDate());

                String purpose = rs.getString("purpose");
                if (!rs.wasNull()) {
                    Code code = new Code(purpose);
                    code.setCodeSpace(rs.getString("purpose_codespace"));
                    constructionBaseAttribute.setPurpose(code);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
