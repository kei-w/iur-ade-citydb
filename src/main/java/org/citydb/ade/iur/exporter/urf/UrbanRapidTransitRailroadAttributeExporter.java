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
import org.citygml4j.ade.iur.model.urf.StructureDetails;
import org.citygml4j.ade.iur.model.urf.StructureDetailsProperty;
import org.citygml4j.ade.iur.model.urf.UrbanRapidTransitRailroadAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class UrbanRapidTransitRailroadAttributeExporter implements UrbanFunctionModuleExporter {
    private final PreparedStatement psStructuralDetails;
    private final PreparedStatement ps;
    private final StructureDetailsExporter structureDetailsExporter;
    private final AttributeValueSplitter valueSplitter;

    public UrbanRapidTransitRailroadAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.URBANRAPIDTRANSITRAILR);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table structuralDetailss = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANRAPI_TO_STRUCTURE)));
        Select structuralDetailsSelect = new Select().addProjection(structuralDetailss.getColumn("structuredetails_id"))
                .addSelection(ComparisonFactory.equalTo(structuralDetailss.getColumn("urbanrapidtransitrailr_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(structuralDetailss.getColumn("structuredetails_id")));
        psStructuralDetails = connection.prepareStatement(structuralDetailsSelect.toString());
        Select select = new Select().addProjection(table.getColumns("structuretype", "structuretype_codespace", "crosstype", "crosstype_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        structureDetailsExporter = manager.getExporter(StructureDetailsExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(UrbanRapidTransitRailroadAttribute urbanRapidTransitRailroadAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String structureType = rs.getString("structuretype");
                if (!rs.wasNull()) {
                    Code code = new Code(structureType);
                    code.setCodeSpace(rs.getString("structuretype_codespace"));
                    urbanRapidTransitRailroadAttribute.setStructureType(code);
                }

                String crossType = rs.getString("crosstype");
                if (!rs.wasNull()) {
                    Code code = new Code(crossType);
                    code.setCodeSpace(rs.getString("crosstype_codespace"));
                    urbanRapidTransitRailroadAttribute.setCrossType(code);
                }

                psStructuralDetails.setLong(1, objectId);
                try (ResultSet structuralDetailsRs = psStructuralDetails.executeQuery()) {
                    while (structuralDetailsRs.next()) {
                        StructureDetails structuralDetails = new StructureDetails();
                        structureDetailsExporter.doExport(structuralDetails, structuralDetailsRs.getLong("structuredetails_id"));
                        urbanRapidTransitRailroadAttribute.getStructuralDetails().add(new StructureDetailsProperty(structuralDetails));
                    }
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psStructuralDetails.close();

    }
}
