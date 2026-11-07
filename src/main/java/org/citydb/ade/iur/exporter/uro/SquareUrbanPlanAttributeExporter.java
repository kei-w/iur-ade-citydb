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
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.SquareUrbanPlanAttribute;
import org.citygml4j.ade.iur.model.uro.StationSquareAttribute;
import org.citygml4j.ade.iur.model.uro.TerminalAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.time.LocalDate;

import java.sql.*;

public class SquareUrbanPlanAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public SquareUrbanPlanAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.SQUAREURBANPLANATTRIBU);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table stationSquareAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STATIONSQUAREATTRIBUTE)));
        Table terminalAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TERMINALATTRIBUTE)));
        Select select = new Select().addProjection(table.getColumns("id", "objectclass_id", "prefecture", "prefecture_codespace", "city", "city_codespace",
                        "urbanplanningareaname", "enforcer", "dateofdecision", "dateofrevision", "areaplanned",
                        "areaplanned_uom", "areainservice", "areainservice_uom", "remarks", "status", "status_codespace",
                        "areaimproved", "areaimproved_uom", "areacompleted", "areacompleted_uom", "projectstartdate",
                        "projectenddate", "iscompleted", "isauthorized", "purpose", "note"))
                .addProjection(stationSquareAttribute.getColumns("station", "route", "railwaytype", "railwaytype_codespace"))
                .addProjection(terminalAttribute.getColumns("terminaltype", "terminaltype_codespace", "structure", "numberofberthsplanned",
                        "numberofberthsinservice", "usertype", "usertype_codespace"))
                .addJoin(JoinFactory.left(stationSquareAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(terminalAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public SquareUrbanPlanAttribute doExport(long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? doExport(objectId, rs) : null;
        }
    }

    private SquareUrbanPlanAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        SquareUrbanPlanAttribute squareUrbanPlanAttribute = objectMapper.createType(objectClassId, SquareUrbanPlanAttribute.class);
        if (squareUrbanPlanAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate square urban plan attribute (id: " + objectId + ").");
            return null;
        }

        String prefecture = rs.getString("prefecture");
        if (!rs.wasNull()) {
            Code code = new Code(prefecture);
            code.setCodeSpace(rs.getString("prefecture_codespace"));
            squareUrbanPlanAttribute.setPrefecture(code);
        }

        String city = rs.getString("city");
        if (!rs.wasNull()) {
            Code code = new Code(city);
            code.setCodeSpace(rs.getString("city_codespace"));
            squareUrbanPlanAttribute.setCity(code);
        }

        squareUrbanPlanAttribute.setUrbanPlanningAreaName(rs.getString("urbanplanningareaname"));

        for (SplitValue splitValue : valueSplitter.split(rs.getString("enforcer")))
            squareUrbanPlanAttribute.getEnforcer().add(splitValue.result(0));

        Date dateOfDecision = rs.getDate("dateofdecision");
        if (!rs.wasNull())
            squareUrbanPlanAttribute.setDateOfDecision(dateOfDecision.toLocalDate());

        for (SplitValue splitValue : valueSplitter.split(rs.getString("dateofrevision")))
            squareUrbanPlanAttribute.getDateOfRevision().add(LocalDate.parse(splitValue.result(0)));

        double areaPlanned = rs.getDouble("areaplanned");
        if (!rs.wasNull()) {
            Measure measure = new Measure(areaPlanned);
            measure.setUom(rs.getString("areaplanned_uom"));
            squareUrbanPlanAttribute.setAreaPlanned(measure);
        }

        double areaInService = rs.getDouble("areainservice");
        if (!rs.wasNull()) {
            Measure measure = new Measure(areaInService);
            measure.setUom(rs.getString("areainservice_uom"));
            squareUrbanPlanAttribute.setAreaInService(measure);
        }

        squareUrbanPlanAttribute.setRemarks(rs.getString("remarks"));

        String status = rs.getString("status");
        if (!rs.wasNull()) {
            Code code = new Code(status);
            code.setCodeSpace(rs.getString("status_codespace"));
            squareUrbanPlanAttribute.setStatus(code);
        }

        double areaImproved = rs.getDouble("areaimproved");
        if (!rs.wasNull()) {
            Measure measure = new Measure(areaImproved);
            measure.setUom(rs.getString("areaimproved_uom"));
            squareUrbanPlanAttribute.setAreaImproved(measure);
        }

        double areaCompleted = rs.getDouble("areacompleted");
        if (!rs.wasNull()) {
            Measure measure = new Measure(areaCompleted);
            measure.setUom(rs.getString("areacompleted_uom"));
            squareUrbanPlanAttribute.setAreaCompleted(measure);
        }

        Date projectStartDate = rs.getDate("projectstartdate");
        if (!rs.wasNull())
            squareUrbanPlanAttribute.setProjectStartDate(projectStartDate.toLocalDate());

        Date projectEndDate = rs.getDate("projectenddate");
        if (!rs.wasNull())
            squareUrbanPlanAttribute.setProjectEndDate(projectEndDate.toLocalDate());

        boolean isCompleted = rs.getBoolean("iscompleted");
        if (!rs.wasNull())
            squareUrbanPlanAttribute.setIsCompleted(isCompleted);

        boolean isAuthorized = rs.getBoolean("isauthorized");
        if (!rs.wasNull())
            squareUrbanPlanAttribute.setIsAuthorized(isAuthorized);

        squareUrbanPlanAttribute.setPurpose(rs.getString("purpose"));

        squareUrbanPlanAttribute.setNote(rs.getString("note"));

        if (squareUrbanPlanAttribute instanceof StationSquareAttribute) {
            StationSquareAttribute stationSquareAttribute = (StationSquareAttribute) squareUrbanPlanAttribute;

            for (SplitValue splitValue : valueSplitter.split(rs.getString("station")))
                stationSquareAttribute.getStation().add(splitValue.result(0));

            for (SplitValue splitValue : valueSplitter.split(rs.getString("route")))
                stationSquareAttribute.getRoute().add(splitValue.result(0));

            for (SplitValue splitValue : valueSplitter.split(rs.getString("railwaytype"), rs.getString("railwaytype_codespace"))) {
                Code code = new Code(splitValue.result(0));
                code.setCodeSpace(splitValue.result(1));
                stationSquareAttribute.getRailwayType().add(code);
            }
        }

        if (squareUrbanPlanAttribute instanceof TerminalAttribute) {
            TerminalAttribute terminalAttribute = (TerminalAttribute) squareUrbanPlanAttribute;

            String terminalType = rs.getString("terminaltype");
            if (!rs.wasNull()) {
                Code code = new Code(terminalType);
                code.setCodeSpace(rs.getString("terminaltype_codespace"));
                terminalAttribute.setTerminalType(code);
            }

            terminalAttribute.setStructure(rs.getString("structure"));

            int numberOfBerthsPlanned = rs.getInt("numberofberthsplanned");
            if (!rs.wasNull())
                terminalAttribute.setNumberOfBerthsPlanned(numberOfBerthsPlanned);

            int numberOfBerthsInService = rs.getInt("numberofberthsinservice");
            if (!rs.wasNull())
                terminalAttribute.setNumberOfBerthsInService(numberOfBerthsInService);

            String userType = rs.getString("usertype");
            if (!rs.wasNull()) {
                Code code = new Code(userType);
                code.setCodeSpace(rs.getString("usertype_codespace"));
                terminalAttribute.setUserType(code);
            }
        }
        return squareUrbanPlanAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
