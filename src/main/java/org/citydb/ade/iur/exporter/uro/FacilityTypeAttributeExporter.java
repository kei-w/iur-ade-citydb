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
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.FacilityTypeAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.*;

public class FacilityTypeAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final Map<String, PreparedStatement> psByParent = new HashMap<>();
    private final AttributeValueSplitter valueSplitter;

    public FacilityTypeAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.FACILITYTYPEATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("id", "class", "class_codespace", "function", "function_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        for (String parentColumn : Arrays.asList("construction_consfacility_id")) {
            Select parentSelect = new Select().addProjection(table.getColumns("id", "class", "class_codespace", "function", "function_codespace"))
                    .addSelection(ComparisonFactory.equalTo(table.getColumn(parentColumn), new PlaceHolder<>()))
                    .addOrderBy(new OrderByToken(table.getColumn("id")));
            psByParent.put(parentColumn, connection.prepareStatement(parentSelect.toString()));
        }

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(FacilityTypeAttribute facilityTypeAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                doExport(facilityTypeAttribute, objectId, rs);
        }
    }

    public List<FacilityTypeAttribute> doExport(long parentId, String parentColumn) throws CityGMLExportException, SQLException {
        PreparedStatement ps = psByParent.get(parentColumn);
        ps.setLong(1, parentId);

        List<FacilityTypeAttribute> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FacilityTypeAttribute facilityTypeAttribute = new FacilityTypeAttribute();
                long objectId = rs.getLong("id");
                doExport(facilityTypeAttribute, objectId, rs);

                result.add(facilityTypeAttribute);
            }
        }

        return result;
    }

    private void doExport(FacilityTypeAttribute facilityTypeAttribute, long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        String classifier = rs.getString("class");
        if (!rs.wasNull()) {
            Code code = new Code(classifier);
            code.setCodeSpace(rs.getString("class_codespace"));
            facilityTypeAttribute.setClassifier(code);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString("function"), rs.getString("function_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            facilityTypeAttribute.getFunction().add(code);
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        for (PreparedStatement ps : psByParent.values())
            ps.close();
    }
}
