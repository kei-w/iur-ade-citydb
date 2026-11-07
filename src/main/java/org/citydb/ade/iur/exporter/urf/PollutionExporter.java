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
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.ObservationPoint;
import org.citygml4j.ade.iur.model.urf.ObservationPointProperty;
import org.citygml4j.ade.iur.model.urf.Pollution;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.time.Year;

import java.sql.*;

public class PollutionExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final PreparedStatement ps;
    private final ObservationPointExporter observationPointExporter;

    public PollutionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.POLLUTION);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        observationPointExporter = manager.getExporter(ObservationPointExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(Pollution pollution, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(pollution, objectId, "uf_", rs);
                doExport(pollution, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("id_", prefix + "id_"),
                table.getColumn("year", prefix + "year"),
                table.getColumn("cause", prefix + "cause"),
                table.getColumn("damagedarea", prefix + "damagedarea"),
                table.getColumn("damagedarea_uom", prefix + "damagedarea_uom"),
                table.getColumn("observationpoint_id", prefix + "observationpoint_id"));

        return select;
    }

    public void doExport(Pollution pollution, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        pollution.setIdentifier(rs.getString(prefix + "id_"));

        Date year = rs.getDate(prefix + "year");
        if (!rs.wasNull())
            pollution.setYear(Year.of(year.toLocalDate().getYear()));

        pollution.setCause(rs.getString(prefix + "cause"));

        double damagedArea = rs.getDouble(prefix + "damagedarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(damagedArea);
            measure.setUom(rs.getString(prefix + "damagedarea_uom"));
            pollution.setDamagedArea(measure);
        }

        long observationPointId = rs.getLong(prefix + "observationpoint_id");
        if (observationPointId != 0) {
            ObservationPoint observationPoint = new ObservationPoint();
            observationPointExporter.doExport(observationPoint, observationPointId);
            pollution.setObservationPoint(new ObservationPointProperty(observationPoint));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
