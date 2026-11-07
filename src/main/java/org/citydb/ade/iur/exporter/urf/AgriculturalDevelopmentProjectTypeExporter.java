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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.urf.AgriculturalDevelopmentProjectType;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.time.Year;

import java.sql.*;

public class AgriculturalDevelopmentProjectTypeExporter implements UrbanFunctionModuleExporter {
    private final PreparedStatement ps;

    public AgriculturalDevelopmentProjectTypeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.AGRICULTURALDEVELOPMEN);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("id_", "blockname", "projecttype", "projecttype_codespace", "implementationbody", "benefitarea",
                        "benefitarea_uom", "benefitareainurbanarea", "benefitareainurbanarea_uom", "startfiscalyear",
                        "status", "status_codespace", "measuretype", "measuretype_codespace", "benefitperiod", "note"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(AgriculturalDevelopmentProjectType agriculturalDevelopmentProjectType, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                agriculturalDevelopmentProjectType.setId(rs.getString("id_"));

                agriculturalDevelopmentProjectType.setBlockName(rs.getString("blockname"));

                String projectType = rs.getString("projecttype");
                if (!rs.wasNull()) {
                    Code code = new Code(projectType);
                    code.setCodeSpace(rs.getString("projecttype_codespace"));
                    agriculturalDevelopmentProjectType.setProjectType(code);
                }

                agriculturalDevelopmentProjectType.setImplementationBody(rs.getString("implementationbody"));

                double benefitArea = rs.getDouble("benefitarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(benefitArea);
                    measure.setUom(rs.getString("benefitarea_uom"));
                    agriculturalDevelopmentProjectType.setBenefitArea(measure);
                }

                double benefitAreaInUrbanArea = rs.getDouble("benefitareainurbanarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(benefitAreaInUrbanArea);
                    measure.setUom(rs.getString("benefitareainurbanarea_uom"));
                    agriculturalDevelopmentProjectType.setBenefitAreaInUrbanArea(measure);
                }

                Date startFiscalYear = rs.getDate("startfiscalyear");
                if (!rs.wasNull())
                    agriculturalDevelopmentProjectType.setStartFiscalYear(Year.of(startFiscalYear.toLocalDate().getYear()));

                String status = rs.getString("status");
                if (!rs.wasNull()) {
                    Code code = new Code(status);
                    code.setCodeSpace(rs.getString("status_codespace"));
                    agriculturalDevelopmentProjectType.setStatus(code);
                }

                String measureType = rs.getString("measuretype");
                if (!rs.wasNull()) {
                    Code code = new Code(measureType);
                    code.setCodeSpace(rs.getString("measuretype_codespace"));
                    agriculturalDevelopmentProjectType.setMeasureType(code);
                }

                agriculturalDevelopmentProjectType.setBenefitPeriod(rs.getString("benefitperiod"));

                agriculturalDevelopmentProjectType.setNote(rs.getString("note"));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
