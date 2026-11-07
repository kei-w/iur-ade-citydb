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
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.UtilityNodeContainer;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class UtilityNodeContainerExporter implements UrbanObjectModuleExporter {
    private final UtilityNetworkElementExporter utilityNetworkElementExporter;
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public UtilityNodeContainerExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.UTILITYNODECONTAINER);
        utilityNetworkElementExporter = manager.getExporter(UtilityNetworkElementExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table utilityNetworkElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYNETWORKELEMENT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(utilityNetworkElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        utilityNetworkElementExporter.addProjection(select, utilityNetworkElement, "une_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(UtilityNodeContainer utilityNodeContainer, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                utilityNetworkElementExporter.doExport(utilityNodeContainer, objectId, "une_", rs);
                doExport(utilityNodeContainer, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("containertype", prefix + "containertype"),
                table.getColumn("containertype_codespace", prefix + "containertype_codespace"),
                table.getColumn("innerdiamiterlong", prefix + "innerdiamiterlong"),
                table.getColumn("innerdiamiterlong_uom", prefix + "innerdiamiterlong_uom"),
                table.getColumn("outerdiamiterlong", prefix + "outerdiamiterlong"),
                table.getColumn("outerdiamiterlong_uom", prefix + "outerdiamiterlong_uom"),
                table.getColumn("innerdiamitershort", prefix + "innerdiamitershort"),
                table.getColumn("innerdiamitershort_uom", prefix + "innerdiamitershort_uom"),
                table.getColumn("outerdiamitershort", prefix + "outerdiamitershort"),
                table.getColumn("outerdiamitershort_uom", prefix + "outerdiamitershort_uom"),
                table.getColumn("depth", prefix + "depth"),
                table.getColumn("depth_uom", prefix + "depth_uom"),
                table.getColumn("appurtenance", prefix + "appurtenance"),
                table.getColumn("rotationangle", prefix + "rotationangle"));

        return select;
    }

    public void doExport(UtilityNodeContainer utilityNodeContainer, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String containerType = rs.getString(prefix + "containertype");
        if (!rs.wasNull()) {
            Code code = new Code(containerType);
            code.setCodeSpace(rs.getString(prefix + "containertype_codespace"));
            utilityNodeContainer.setContainerType(code);
        }

        double innerDiamiterLong = rs.getDouble(prefix + "innerdiamiterlong");
        if (!rs.wasNull()) {
            Length length = new Length(innerDiamiterLong);
            length.setUom(rs.getString(prefix + "innerdiamiterlong_uom"));
            utilityNodeContainer.setInnerDiamiterLong(length);
        }

        double outerDiamiterLong = rs.getDouble(prefix + "outerdiamiterlong");
        if (!rs.wasNull()) {
            Length length = new Length(outerDiamiterLong);
            length.setUom(rs.getString(prefix + "outerdiamiterlong_uom"));
            utilityNodeContainer.setOuterDiamiterLong(length);
        }

        double innerDiamiterShort = rs.getDouble(prefix + "innerdiamitershort");
        if (!rs.wasNull()) {
            Length length = new Length(innerDiamiterShort);
            length.setUom(rs.getString(prefix + "innerdiamitershort_uom"));
            utilityNodeContainer.setInnerDiamiterShort(length);
        }

        double outerDiamiterShort = rs.getDouble(prefix + "outerdiamitershort");
        if (!rs.wasNull()) {
            Length length = new Length(outerDiamiterShort);
            length.setUom(rs.getString(prefix + "outerdiamitershort_uom"));
            utilityNodeContainer.setOuterDiamiterShort(length);
        }

        double depth = rs.getDouble(prefix + "depth");
        if (!rs.wasNull()) {
            Length length = new Length(depth);
            length.setUom(rs.getString(prefix + "depth_uom"));
            utilityNodeContainer.setDepth(length);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "appurtenance")))
            utilityNodeContainer.getAppurtenance().add(splitValue.result(0));

        double rotationAngle = rs.getDouble(prefix + "rotationangle");
        if (!rs.wasNull())
            utilityNodeContainer.setRotationAngle(rotationAngle);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
