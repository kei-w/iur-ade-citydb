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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.CircularCurveType;
import org.citygml4j.ade.iur.model.uro.CircularCurveTypeProperty;
import org.citygml4j.ade.iur.model.uro.ControlPointParameter;
import org.citygml4j.ade.iur.model.uro.SlopeType;
import org.citygml4j.ade.iur.model.uro.SlopeTypeProperty;
import org.citygml4j.ade.iur.model.uro.TransitionCurveType;
import org.citygml4j.ade.iur.model.uro.TransitionCurveTypeProperty;
import org.citygml4j.ade.iur.model.uro.VerticalCurveType;
import org.citygml4j.ade.iur.model.uro.VerticalCurveTypeProperty;

import java.sql.*;

public class ControlPointParameterExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final CircularCurveTypeExporter circularCurveTypeExporter;
    private final TransitionCurveTypeExporter transitionCurveTypeExporter;
    private final SlopeTypeExporter slopeTypeExporter;
    private final VerticalCurveTypeExporter verticalCurveTypeExporter;

    public ControlPointParameterExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CONTROLPOINTTYPE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("circularcurve_id", "transitioncurve_id", "slopetype_id", "verticalcurve_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        circularCurveTypeExporter = manager.getExporter(CircularCurveTypeExporter.class);
        transitionCurveTypeExporter = manager.getExporter(TransitionCurveTypeExporter.class);
        slopeTypeExporter = manager.getExporter(SlopeTypeExporter.class);
        verticalCurveTypeExporter = manager.getExporter(VerticalCurveTypeExporter.class);
    }

    public void doExport(ControlPointParameter controlPointParameter, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long circularCurveId = rs.getLong("circularcurve_id");
                if (circularCurveId != 0) {
                    CircularCurveType circularCurve = new CircularCurveType();
                    circularCurveTypeExporter.doExport(circularCurve, circularCurveId);
                    controlPointParameter.setCircularCurve(new CircularCurveTypeProperty(circularCurve));
                }

                long transitionCurveId = rs.getLong("transitioncurve_id");
                if (transitionCurveId != 0) {
                    TransitionCurveType transitionCurve = new TransitionCurveType();
                    transitionCurveTypeExporter.doExport(transitionCurve, transitionCurveId);
                    controlPointParameter.setTransitionCurve(new TransitionCurveTypeProperty(transitionCurve));
                }

                long slopeTypeId = rs.getLong("slopetype_id");
                if (slopeTypeId != 0) {
                    SlopeType slopeType = new SlopeType();
                    slopeTypeExporter.doExport(slopeType, slopeTypeId);
                    controlPointParameter.setSlopeType(new SlopeTypeProperty(slopeType));
                }

                long verticalCurveId = rs.getLong("verticalcurve_id");
                if (verticalCurveId != 0) {
                    VerticalCurveType verticalCurve = new VerticalCurveType();
                    verticalCurveTypeExporter.doExport(verticalCurve, verticalCurveId);
                    controlPointParameter.setVerticalCurve(new VerticalCurveTypeProperty(verticalCurve));
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
