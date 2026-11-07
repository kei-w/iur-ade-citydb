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
import org.citygml4j.ade.iur.model.urf.DaytimePopulationType;

import java.sql.*;

public class DaytimePopulationTypeExporter implements UrbanFunctionModuleExporter {
    private final PreparedStatement ps;

    public DaytimePopulationTypeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DAYTIMEPOPULATIONTYPE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("daytimepopulation", "daytimepopulationdensity", "daytimeofficecommuter", "daytimeschoolcommuter",
                        "daytimeunder15", "daytimeover15", "agriculturalworker", "noncommuter"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(DaytimePopulationType daytimePopulationType, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int daytimePopulation = rs.getInt("daytimepopulation");
                if (!rs.wasNull())
                    daytimePopulationType.setDaytimePopulation(daytimePopulation);

                double daytimePopulationDensity = rs.getDouble("daytimepopulationdensity");
                if (!rs.wasNull())
                    daytimePopulationType.setDaytimePopulationDensity(daytimePopulationDensity);

                int daytimeOfficeCommuter = rs.getInt("daytimeofficecommuter");
                if (!rs.wasNull())
                    daytimePopulationType.setDaytimeOfficeCommuter(daytimeOfficeCommuter);

                int daytimeSchoolCommuter = rs.getInt("daytimeschoolcommuter");
                if (!rs.wasNull())
                    daytimePopulationType.setDaytimeSchoolCommuter(daytimeSchoolCommuter);

                int daytimeUnder15 = rs.getInt("daytimeunder15");
                if (!rs.wasNull())
                    daytimePopulationType.setDaytimeUnder15(daytimeUnder15);

                int daytimeOver15 = rs.getInt("daytimeover15");
                if (!rs.wasNull())
                    daytimePopulationType.setDaytimeOver15(daytimeOver15);

                int agriculturalWorker = rs.getInt("agriculturalworker");
                if (!rs.wasNull())
                    daytimePopulationType.setAgriculturalWorker(agriculturalWorker);

                int nonCommuter = rs.getInt("noncommuter");
                if (!rs.wasNull())
                    daytimePopulationType.setNonCommuter(nonCommuter);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
