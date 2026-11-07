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
import org.citygml4j.ade.iur.model.uro.ConstructionRiskAssessmentAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.time.Year;

import java.sql.*;

public class ConstructionRiskAssessmentAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public ConstructionRiskAssessmentAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CONSTRUCTIONRISKASSESS);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("surveyyear", "risktype", "risktype_codespace", "status", "status_codespace", "referencedate"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(ConstructionRiskAssessmentAttribute constructionRiskAssessmentAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Date surveyYear = rs.getDate("surveyyear");
                if (!rs.wasNull())
                    constructionRiskAssessmentAttribute.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));

                String riskType = rs.getString("risktype");
                if (!rs.wasNull()) {
                    Code code = new Code(riskType);
                    code.setCodeSpace(rs.getString("risktype_codespace"));
                    constructionRiskAssessmentAttribute.setRiskType(code);
                }

                String status = rs.getString("status");
                if (!rs.wasNull()) {
                    Code code = new Code(status);
                    code.setCodeSpace(rs.getString("status_codespace"));
                    constructionRiskAssessmentAttribute.setStatus(code);
                }

                Date referenceDate = rs.getDate("referencedate");
                if (!rs.wasNull())
                    constructionRiskAssessmentAttribute.setReferenceDate(referenceDate.toLocalDate());
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
