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
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.Duct;
import org.citygml4j.ade.iur.model.uro.LengthAttribute;
import org.citygml4j.ade.iur.model.uro.LengthAttributeProperty;
import org.citygml4j.ade.iur.model.uro.UtilityLink;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class UtilityLinkExporter implements UrbanObjectModuleExporter {
    private final UtilityNetworkElementExporter utilityNetworkElementExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psLengthAttribute;
    private final LengthAttributeExporter lengthAttributeExporter;
    private final AttributeValueSplitter valueSplitter;

    public UtilityLinkExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.UTILITYLINK);
        utilityNetworkElementExporter = manager.getExporter(UtilityNetworkElementExporter.class);
        lengthAttributeExporter = manager.getExporter(LengthAttributeExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table utilityNetworkElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYNETWORKELEMENT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(utilityNetworkElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        utilityNetworkElementExporter.addProjection(select, utilityNetworkElement, "une_");
        ps = connection.prepareStatement(select.toString());

        Table lengthAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYLI_TO_LENGTHATT)));
        Select lengthAttributeSelect = new Select().addProjection(lengthAttributes.getColumn("lengthattribute_id"))
                .addSelection(ComparisonFactory.equalTo(lengthAttributes.getColumn("utilitylink_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(lengthAttributes.getColumn("lengthattribute_id")));
        psLengthAttribute = connection.prepareStatement(lengthAttributeSelect.toString());
    }

    public void doExport(UtilityLink utilityLink, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                utilityNetworkElementExporter.doExport(utilityLink, objectId, "une_", rs);
                doExport(utilityLink, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("routestartnode", prefix + "routestartnode"),
                table.getColumn("startnode", prefix + "startnode"),
                table.getColumn("routeendnode", prefix + "routeendnode"),
                table.getColumn("endnode", prefix + "endnode"),
                table.getColumn("depth", prefix + "depth"),
                table.getColumn("depth_uom", prefix + "depth_uom"),
                table.getColumn("mindepth", prefix + "mindepth"),
                table.getColumn("mindepth_uom", prefix + "mindepth_uom"),
                table.getColumn("maxdepth", prefix + "maxdepth"),
                table.getColumn("maxdepth_uom", prefix + "maxdepth_uom"),
                table.getColumn("maxwidth", prefix + "maxwidth"),
                table.getColumn("maxwidth_uom", prefix + "maxwidth_uom"),
                table.getColumn("offset_", prefix + "offset_"),
                table.getColumn("offset_uom", prefix + "offset_uom"),
                table.getColumn("material", prefix + "material"),
                table.getColumn("material_codespace", prefix + "material_codespace"),
                table.getColumn("width", prefix + "width"),
                table.getColumn("width_uom", prefix + "width_uom"));

        return select;
    }

    public void doExport(UtilityLink utilityLink, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        utilityLink.setRouteStartNode(rs.getString(prefix + "routestartnode"));

        utilityLink.setStartNode(rs.getString(prefix + "startnode"));

        utilityLink.setRouteEndNode(rs.getString(prefix + "routeendnode"));

        utilityLink.setEndNode(rs.getString(prefix + "endnode"));

        double depth = rs.getDouble(prefix + "depth");
        if (!rs.wasNull()) {
            Length length = new Length(depth);
            length.setUom(rs.getString(prefix + "depth_uom"));
            utilityLink.setDepth(length);
        }

        double minDepth = rs.getDouble(prefix + "mindepth");
        if (!rs.wasNull()) {
            Length length = new Length(minDepth);
            length.setUom(rs.getString(prefix + "mindepth_uom"));
            utilityLink.setMinDepth(length);
        }

        double maxDepth = rs.getDouble(prefix + "maxdepth");
        if (!rs.wasNull()) {
            Length length = new Length(maxDepth);
            length.setUom(rs.getString(prefix + "maxdepth_uom"));
            utilityLink.setMaxDepth(length);
        }

        double maxWidth = rs.getDouble(prefix + "maxwidth");
        if (!rs.wasNull()) {
            Length length = new Length(maxWidth);
            length.setUom(rs.getString(prefix + "maxwidth_uom"));
            utilityLink.setMaxWidth(length);
        }

        double offset = rs.getDouble(prefix + "offset_");
        if (!rs.wasNull()) {
            Length length = new Length(offset);
            length.setUom(rs.getString(prefix + "offset_uom"));
            utilityLink.setOffset(length);
        }

        String material = rs.getString(prefix + "material");
        if (!rs.wasNull()) {
            Code code = new Code(material);
            code.setCodeSpace(rs.getString(prefix + "material_codespace"));
            utilityLink.setMaterial(code);
        }

        psLengthAttribute.setLong(1, objectId);
        try (ResultSet lengthAttributeRs = psLengthAttribute.executeQuery()) {
            while (lengthAttributeRs.next()) {
                LengthAttribute lengthAttribute = new LengthAttribute();
                lengthAttributeExporter.doExport(lengthAttribute, lengthAttributeRs.getLong("lengthattribute_id"));
                utilityLink.getLengthAttribute().add(new LengthAttributeProperty(lengthAttribute));
            }
        }

        if (utilityLink instanceof Duct) {
            Duct duct = (Duct) utilityLink;

            double width = rs.getDouble(prefix + "width");
            if (!rs.wasNull()) {
                Length length = new Length(width);
                length.setUom(rs.getString(prefix + "width_uom"));
                duct.setWidth(length);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psLengthAttribute.close();
    }
}
