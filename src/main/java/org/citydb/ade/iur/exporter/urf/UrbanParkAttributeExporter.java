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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.urf.BreakdownOfNominalArea;
import org.citygml4j.ade.iur.model.urf.BreakdownOfNominalAreaProperty;
import org.citygml4j.ade.iur.model.urf.UrbanParkAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class UrbanParkAttributeExporter implements UrbanFunctionModuleExporter {
    private final PreparedStatement psBreakdownOfNominalArea;
    private final PreparedStatement ps;
    private final BreakdownOfNominalAreaExporter breakdownOfNominalAreaExporter;
    private final AttributeValueSplitter valueSplitter;

    public UrbanParkAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.URBANPARKATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table breakdownOfNominalAreas = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANPARK_TO_BREAKDOWN)));
        Select breakdownOfNominalAreaSelect = new Select().addProjection(breakdownOfNominalAreas.getColumn("breakdownofnominalarea_id"))
                .addSelection(ComparisonFactory.equalTo(breakdownOfNominalAreas.getColumn("urbanparkattribute_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(breakdownOfNominalAreas.getColumn("breakdownofnominalarea_id")));
        psBreakdownOfNominalArea = connection.prepareStatement(breakdownOfNominalAreaSelect.toString());
        Select select = new Select().addProjection(table.getColumns("parkcode", "parkcode_codespace", "startfrom"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        breakdownOfNominalAreaExporter = manager.getExporter(BreakdownOfNominalAreaExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(UrbanParkAttribute urbanParkAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String parkCode = rs.getString("parkcode");
                if (!rs.wasNull()) {
                    Code code = new Code(parkCode);
                    code.setCodeSpace(rs.getString("parkcode_codespace"));
                    urbanParkAttribute.setParkCode(code);
                }

                Date startFrom = rs.getDate("startfrom");
                if (!rs.wasNull())
                    urbanParkAttribute.setStartFrom(startFrom.toLocalDate());

                psBreakdownOfNominalArea.setLong(1, objectId);
                try (ResultSet breakdownOfNominalAreaRs = psBreakdownOfNominalArea.executeQuery()) {
                    while (breakdownOfNominalAreaRs.next()) {
                        BreakdownOfNominalArea breakdownOfNominalArea = new BreakdownOfNominalArea();
                        breakdownOfNominalAreaExporter.doExport(breakdownOfNominalArea, breakdownOfNominalAreaRs.getLong("breakdownofnominalarea_id"));
                        urbanParkAttribute.getBreakdownOfNominalArea().add(new BreakdownOfNominalAreaProperty(breakdownOfNominalArea));
                    }
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psBreakdownOfNominalArea.close();

    }
}
