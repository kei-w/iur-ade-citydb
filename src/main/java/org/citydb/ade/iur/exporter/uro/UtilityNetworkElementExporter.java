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
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.OffsetDepth;
import org.citygml4j.ade.iur.model.uro.OffsetDepthProperty;
import org.citygml4j.ade.iur.model.uro.ThematicShape;
import org.citygml4j.ade.iur.model.uro.ThematicShapeProperty;
import org.citygml4j.ade.iur.model.uro.UtilityNetworkElement;
import org.citygml4j.model.gml.basicTypes.Code;

import java.time.Year;

import java.sql.*;

public class UtilityNetworkElementExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement psOffsetDepth;
    private final PreparedStatement psThematicShape;
    private final OffsetDepthExporter offsetDepthExporter;
    private final ThematicShapeExporter thematicShapeExporter;
    private final AttributeValueSplitter valueSplitter;

    public UtilityNetworkElementExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        offsetDepthExporter = manager.getExporter(OffsetDepthExporter.class);
        thematicShapeExporter = manager.getExporter(ThematicShapeExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table offsetDepths = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYNE_TO_OFFSETDEP)));
        Select offsetDepthSelect = new Select().addProjection(offsetDepths.getColumn("offsetdepth_id"))
                .addSelection(ComparisonFactory.equalTo(offsetDepths.getColumn("utilitynetworkelement_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(offsetDepths.getColumn("offsetdepth_id")));
        psOffsetDepth = connection.prepareStatement(offsetDepthSelect.toString());
        Table thematicShapes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYNE_TO_THEMATICS)));
        Select thematicShapeSelect = new Select().addProjection(thematicShapes.getColumn("thematicshape_id"))
                .addSelection(ComparisonFactory.equalTo(thematicShapes.getColumn("utilitynetworkelement_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(thematicShapes.getColumn("thematicshape_id")));
        psThematicShape = connection.prepareStatement(thematicShapeSelect.toString());
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("occupiertype", prefix + "occupiertype"),
                table.getColumn("occupiertype_codespace", prefix + "occupiertype_codespace"),
                table.getColumn("occupiername", prefix + "occupiername"),
                table.getColumn("occupiername_codespace", prefix + "occupiername_codespace"),
                table.getColumn("year", prefix + "year"),
                table.getColumn("yeartype", prefix + "yeartype"),
                table.getColumn("yeartype_codespace", prefix + "yeartype_codespace"),
                table.getColumn("administrator", prefix + "administrator"),
                table.getColumn("administrator_codespace", prefix + "administrator_codespace"));

        return select;
    }

    public void doExport(UtilityNetworkElement utilityNetworkElement, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String occupierType = rs.getString(prefix + "occupiertype");
        if (!rs.wasNull()) {
            Code code = new Code(occupierType);
            code.setCodeSpace(rs.getString(prefix + "occupiertype_codespace"));
            utilityNetworkElement.setOccupierType(code);
        }

        String occupierName = rs.getString(prefix + "occupiername");
        if (!rs.wasNull()) {
            Code code = new Code(occupierName);
            code.setCodeSpace(rs.getString(prefix + "occupiername_codespace"));
            utilityNetworkElement.setOccupierName(code);
        }

        Date year = rs.getDate(prefix + "year");
        if (!rs.wasNull())
            utilityNetworkElement.setYear(Year.of(year.toLocalDate().getYear()));

        String yearType = rs.getString(prefix + "yeartype");
        if (!rs.wasNull()) {
            Code code = new Code(yearType);
            code.setCodeSpace(rs.getString(prefix + "yeartype_codespace"));
            utilityNetworkElement.setYearType(code);
        }

        String administrator = rs.getString(prefix + "administrator");
        if (!rs.wasNull()) {
            Code code = new Code(administrator);
            code.setCodeSpace(rs.getString(prefix + "administrator_codespace"));
            utilityNetworkElement.setAdministrator(code);
        }

        psOffsetDepth.setLong(1, objectId);
        try (ResultSet offsetDepthRs = psOffsetDepth.executeQuery()) {
            while (offsetDepthRs.next()) {
                OffsetDepth offsetDepth = new OffsetDepth();
                offsetDepthExporter.doExport(offsetDepth, offsetDepthRs.getLong("offsetdepth_id"));
                utilityNetworkElement.getOffsetDepth().add(new OffsetDepthProperty(offsetDepth));
            }
        }

        psThematicShape.setLong(1, objectId);
        try (ResultSet thematicShapeRs = psThematicShape.executeQuery()) {
            while (thematicShapeRs.next()) {
                ThematicShape thematicShape = new ThematicShape();
                thematicShapeExporter.doExport(thematicShape, thematicShapeRs.getLong("thematicshape_id"));
                utilityNetworkElement.getThematicShape().add(new ThematicShapeProperty(thematicShape));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        psOffsetDepth.close();
        psThematicShape.close();
    }
}
