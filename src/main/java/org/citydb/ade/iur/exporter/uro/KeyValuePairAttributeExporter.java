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
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttribute;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttributeProperty;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttributePropertyElement;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class KeyValuePairAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public KeyValuePairAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.KEYVALUEPAIRATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("key", "key_codespace", "codevalue", "codevalue_codespace",
                        "datevalue", "doublevalue", "intvalue", "measuredvalue", "measuredvalue_uom", "stringvalue", "urivalue"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("building_keyvaluepairattr_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(table.getColumn("id")));
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(AbstractBuilding parent, long parentId) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KeyValuePairAttribute keyValuePair = new KeyValuePairAttribute();

                String key = rs.getString("key");
                if (!rs.wasNull()) {
                    Code code = new Code(key);
                    code.setCodeSpace(rs.getString("key_codespace"));
                    keyValuePair.setKey(code);
                } else
                    continue;

                String codeValue = rs.getString("codevalue");
                if (!rs.wasNull()) {
                    Code code = new Code(codeValue);
                    code.setCodeSpace(rs.getString("codevalue_codespace"));
                    keyValuePair.setCodeValue(code);
                }

                Date dateValue = rs.getDate("datevalue");
                if (!rs.wasNull())
                    keyValuePair.setDateValue(dateValue.toLocalDate());

                double doubleValue = rs.getDouble("doublevalue");
                if (!rs.wasNull())
                    keyValuePair.setDoubleValue(doubleValue);

                int intValue = rs.getInt("intvalue");
                if (!rs.wasNull())
                    keyValuePair.setIntValue(intValue);

                double measuredValue = rs.getDouble("measuredvalue");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(measuredValue);
                    measure.setUom(rs.getString("measuredvalue_uom"));
                    keyValuePair.setMeasuredValue(measure);
                }

                String stringValue = rs.getString("stringvalue");
                if (!rs.wasNull())
                    keyValuePair.setStringValue(stringValue);

                String uriValue = rs.getString("urivalue");
                if (!rs.wasNull())
                    keyValuePair.setUriValue(uriValue);

                KeyValuePairAttributePropertyElement property = new KeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(keyValuePair));
                parent.addGenericApplicationPropertyOfAbstractBuilding(property);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
