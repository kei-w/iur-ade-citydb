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
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.Pipe;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class PipeExporter implements UrbanObjectModuleExporter {
    private final UtilityNetworkElementExporter utilityNetworkElementExporter;
    private final UtilityLinkExporter utilityLinkExporter;
    private final PreparedStatement ps;

    public PipeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.PIPE);
        utilityNetworkElementExporter = manager.getExporter(UtilityNetworkElementExporter.class);
        utilityLinkExporter = manager.getExporter(UtilityLinkExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table utilityNetworkElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYNETWORKELEMENT)));
        Table utilityLink = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYLINK)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(utilityNetworkElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(utilityLink, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        utilityNetworkElementExporter.addProjection(select, utilityNetworkElement, "une_");
        utilityLinkExporter.addProjection(select, utilityLink, "ul_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(Pipe pipe, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                utilityNetworkElementExporter.doExport(pipe, objectId, "une_", rs);
                utilityLinkExporter.doExport(pipe, objectId, "ul_", rs);
                doExport(pipe, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("innerdiameter", prefix + "innerdiameter"),
                table.getColumn("innerdiameter_uom", prefix + "innerdiameter_uom"),
                table.getColumn("outerdiameter", prefix + "outerdiameter"),
                table.getColumn("outerdiameter_uom", prefix + "outerdiameter_uom"),
                table.getColumn("sleevetype", prefix + "sleevetype"),
                table.getColumn("sleevetype_codespace", prefix + "sleevetype_codespace"));

        return select;
    }

    public void doExport(Pipe pipe, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        double innerDiameter = rs.getDouble(prefix + "innerdiameter");
        if (!rs.wasNull()) {
            Length length = new Length(innerDiameter);
            length.setUom(rs.getString(prefix + "innerdiameter_uom"));
            pipe.setInnerDiameter(length);
        }

        double outerDiameter = rs.getDouble(prefix + "outerdiameter");
        if (!rs.wasNull()) {
            Length length = new Length(outerDiameter);
            length.setUom(rs.getString(prefix + "outerdiameter_uom"));
            pipe.setOuterDiameter(length);
        }

        String sleeveType = rs.getString(prefix + "sleevetype");
        if (!rs.wasNull()) {
            Code code = new Code(sleeveType);
            code.setCodeSpace(rs.getString(prefix + "sleevetype_codespace"));
            pipe.setSleeveType(code);
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
