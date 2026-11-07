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
import org.citygml4j.ade.iur.model.uro.UserDefinedValue;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class UserDefinedValueExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public UserDefinedValueExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.USERDEFINEDVALUE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("stringvalue", "intvalue", "doublevalue", "codevalue", "codevalue_codespace", "datevalue",
                        "urivalue", "measuredvalue", "measuredvalue_uom"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(UserDefinedValue userDefinedValue, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                userDefinedValue.setStringValue(rs.getString("stringvalue"));

                int intValue = rs.getInt("intvalue");
                if (!rs.wasNull())
                    userDefinedValue.setIntValue(intValue);

                double doubleValue = rs.getDouble("doublevalue");
                if (!rs.wasNull())
                    userDefinedValue.setDoubleValue(doubleValue);

                String codeValue = rs.getString("codevalue");
                if (!rs.wasNull()) {
                    Code code = new Code(codeValue);
                    code.setCodeSpace(rs.getString("codevalue_codespace"));
                    userDefinedValue.setCodeValue(code);
                }

                Date dateValue = rs.getDate("datevalue");
                if (!rs.wasNull())
                    userDefinedValue.setDateValue(dateValue.toLocalDate());

                userDefinedValue.setUriValue(rs.getString("urivalue"));

                double measuredValue = rs.getDouble("measuredvalue");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(measuredValue);
                    measure.setUom(rs.getString("measuredvalue_uom"));
                    userDefinedValue.setMeasuredValue(measure);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
