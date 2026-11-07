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

package org.citydb.ade.iur.exporter.urt;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urt.Pathway;
import org.citygml4j.ade.iur.model.urt.StopProperty;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class PathwayExporter implements PublicTransitModuleExporter {
    private final ExportManager manager;
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psFrom;
    private final PreparedStatement psTo;

    public PathwayExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.PATHWAY);
        publicTransitExporter = manager.getExporter(PublicTransitExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table publicTransit = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSIT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(publicTransit, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        publicTransitExporter.addProjection(select, publicTransit, "pt_");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table froms = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PATHWAY_TO_STOP)));
        Select fromSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, froms.getColumn("stop_id")))
                .addSelection(ComparisonFactory.equalTo(froms.getColumn("pathway_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psFrom = connection.prepareStatement(fromSelect.toString());
        Table tos = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PATHWAY_TO_STOP_2)));
        Select toSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, tos.getColumn("stop_id")))
                .addSelection(ComparisonFactory.equalTo(tos.getColumn("pathway_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psTo = connection.prepareStatement(toSelect.toString());
    }

    public void doExport(Pathway pathway, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(pathway, objectId, "pt_", rs);
                doExport(pathway, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), pathway, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("mode_", prefix + "mode_"),
                table.getColumn("mode__codespace", prefix + "mode__codespace"),
                table.getColumn("isbidirectional", prefix + "isbidirectional"),
                table.getColumn("isbidirectional_codespace", prefix + "isbidirectional_codespace"),
                table.getColumn("length", prefix + "length"),
                table.getColumn("length_uom", prefix + "length_uom"),
                table.getColumn("traversaltime", prefix + "traversaltime"),
                table.getColumn("staircount", prefix + "staircount"),
                table.getColumn("maxslope", prefix + "maxslope"),
                table.getColumn("minwidth", prefix + "minwidth"),
                table.getColumn("signpostedas", prefix + "signpostedas"),
                table.getColumn("reversedsignpostedas", prefix + "reversedsignpostedas"));

        return select;
    }

    public void doExport(Pathway pathway, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String mode = rs.getString(prefix + "mode_");
        if (!rs.wasNull()) {
            Code code = new Code(mode);
            code.setCodeSpace(rs.getString(prefix + "mode__codespace"));
            pathway.setMode(code);
        }

        String isBidirectional = rs.getString(prefix + "isbidirectional");
        if (!rs.wasNull()) {
            Code code = new Code(isBidirectional);
            code.setCodeSpace(rs.getString(prefix + "isbidirectional_codespace"));
            pathway.setIsBidirectional(code);
        }

        double lengthValue = rs.getDouble(prefix + "length");
        if (!rs.wasNull()) {
            Length length = new Length(lengthValue);
            length.setUom(rs.getString(prefix + "length_uom"));
            pathway.setLength(length);
        }

        int traversalTime = rs.getInt(prefix + "traversaltime");
        if (!rs.wasNull())
            pathway.setTraversalTime(traversalTime);

        int stairCount = rs.getInt(prefix + "staircount");
        if (!rs.wasNull())
            pathway.setStairCount(stairCount);

        double maxSlope = rs.getDouble(prefix + "maxslope");
        if (!rs.wasNull())
            pathway.setMaxSlope(maxSlope);

        double minWidth = rs.getDouble(prefix + "minwidth");
        if (!rs.wasNull())
            pathway.setMinWidth(minWidth);

        pathway.setSignpostedAs(rs.getString(prefix + "signpostedas"));

        pathway.setReversedSignpostedAs(rs.getString(prefix + "reversedsignpostedas"));

        psFrom.setLong(1, objectId);
        try (ResultSet fromRs = psFrom.executeQuery()) {
            while (fromRs.next()) {
                StopProperty fromProperty = new StopProperty();
                fromProperty.setHref("#" + fromRs.getString("gmlid"));
                pathway.setFrom(fromProperty);
            }
        }

        psTo.setLong(1, objectId);
        try (ResultSet toRs = psTo.executeQuery()) {
            while (toRs.next()) {
                StopProperty toProperty = new StopProperty();
                toProperty.setHref("#" + toRs.getString("gmlid"));
                pathway.setTo(toProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psFrom.close();
        psTo.close();
    }
}
