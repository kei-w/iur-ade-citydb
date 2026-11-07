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
import org.citygml4j.ade.iur.model.uro.BuildingIDAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class BuildingIDAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public BuildingIDAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BUILDINGIDATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("buildingid", "branchid", "partid", "prefecture", "prefecture_codespace", "city", "city_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(BuildingIDAttribute buildingIDAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                buildingIDAttribute.setBuildingID(rs.getString("buildingid"));

                int branchID = rs.getInt("branchid");
                if (!rs.wasNull())
                    buildingIDAttribute.setBranchID(branchID);

                int partID = rs.getInt("partid");
                if (!rs.wasNull())
                    buildingIDAttribute.setPartID(partID);

                String prefecture = rs.getString("prefecture");
                if (!rs.wasNull()) {
                    Code code = new Code(prefecture);
                    code.setCodeSpace(rs.getString("prefecture_codespace"));
                    buildingIDAttribute.setPrefecture(code);
                }

                String city = rs.getString("city");
                if (!rs.wasNull()) {
                    Code code = new Code(city);
                    code.setCodeSpace(rs.getString("city_codespace"));
                    buildingIDAttribute.setCity(code);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
