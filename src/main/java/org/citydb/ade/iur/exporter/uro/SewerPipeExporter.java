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
import org.citygml4j.ade.iur.model.uro.SewerPipe;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class SewerPipeExporter implements UrbanObjectModuleExporter {
    private final UtilityNetworkElementExporter utilityNetworkElementExporter;
    private final UtilityLinkExporter utilityLinkExporter;
    private final PipeExporter pipeExporter;
    private final PreparedStatement ps;

    public SewerPipeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.SEWERPIPE);
        utilityNetworkElementExporter = manager.getExporter(UtilityNetworkElementExporter.class);
        utilityLinkExporter = manager.getExporter(UtilityLinkExporter.class);
        pipeExporter = manager.getExporter(PipeExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table utilityNetworkElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYNETWORKELEMENT)));
        Table utilityLink = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYLINK)));
        Table pipe = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PIPE)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(utilityNetworkElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(utilityLink, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(pipe, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        utilityNetworkElementExporter.addProjection(select, utilityNetworkElement, "une_");
        utilityLinkExporter.addProjection(select, utilityLink, "ul_");
        pipeExporter.addProjection(select, pipe, "p_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(SewerPipe sewerPipe, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                utilityNetworkElementExporter.doExport(sewerPipe, objectId, "une_", rs);
                utilityLinkExporter.doExport(sewerPipe, objectId, "ul_", rs);
                pipeExporter.doExport(sewerPipe, objectId, "p_", rs);
                doExport(sewerPipe, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("slope", prefix + "slope"),
                table.getColumn("slope_uom", prefix + "slope_uom"),
                table.getColumn("invertelevationupstream", prefix + "invertelevationupstream"),
                table.getColumn("invertelevationupstream_uom", prefix + "invertelevationupstream_uom"),
                table.getColumn("invertelevationdownstream", prefix + "invertelevationdownstream"),
                table.getColumn("invertelevationdownstrea_uom", prefix + "invertelevationdownstrea_uom"),
                table.getColumn("flowdirection", prefix + "flowdirection"));

        return select;
    }

    public void doExport(SewerPipe sewerPipe, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        double slope = rs.getDouble(prefix + "slope");
        if (!rs.wasNull()) {
            Measure measure = new Measure(slope);
            measure.setUom(rs.getString(prefix + "slope_uom"));
            sewerPipe.setSlope(measure);
        }

        double invertElevationUpstream = rs.getDouble(prefix + "invertelevationupstream");
        if (!rs.wasNull()) {
            Length length = new Length(invertElevationUpstream);
            length.setUom(rs.getString(prefix + "invertelevationupstream_uom"));
            sewerPipe.setInvertElevationUpstream(length);
        }

        double invertElevationDownstream = rs.getDouble(prefix + "invertelevationdownstream");
        if (!rs.wasNull()) {
            Length length = new Length(invertElevationDownstream);
            length.setUom(rs.getString(prefix + "invertelevationdownstrea_uom"));
            sewerPipe.setInvertElevationDownstream(length);
        }

        boolean flowDirection = rs.getBoolean(prefix + "flowdirection");
        if (!rs.wasNull())
            sewerPipe.setFlowDirection(flowDirection);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
