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
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.CountermeasuresCost;
import org.citygml4j.ade.iur.model.uro.CountermeasuresCostProperty;
import org.citygml4j.ade.iur.model.uro.LongevityMeasures;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class LongevityMeasuresExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement psByParent;
    private final CountermeasuresCostExporter countermeasuresCostExporter;

    public LongevityMeasuresExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LONGEVITYMEASURES);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("fiscalyearforcountermeasures", "countermeasurescost_id", "description"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("parkfacility_longevitymea_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(table.getColumn("id")));
        psByParent = connection.prepareStatement(select.toString());

        countermeasuresCostExporter = manager.getExporter(CountermeasuresCostExporter.class);
    }

    public List<LongevityMeasures> doExport(long parentId) throws CityGMLExportException, SQLException {
        PreparedStatement ps = psByParent;
        ps.setLong(1, parentId);

        List<LongevityMeasures> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LongevityMeasures longevityMeasures = new LongevityMeasures();
                Date fiscalYearForCountermeasures = rs.getDate("fiscalyearforcountermeasures");
                if (!rs.wasNull())
                    longevityMeasures.setFiscalYearForCountermeasures(Year.of(fiscalYearForCountermeasures.toLocalDate().getYear()));

                long countermeasuresCostId = rs.getLong("countermeasurescost_id");
                if (countermeasuresCostId != 0) {
                    CountermeasuresCost countermeasuresCost = new CountermeasuresCost();
                    countermeasuresCostExporter.doExport(countermeasuresCost, countermeasuresCostId);
                    longevityMeasures.setCountermeasuresCost(new CountermeasuresCostProperty(countermeasuresCost));
                }

                longevityMeasures.setDescription(rs.getString("description"));

                result.add(longevityMeasures);
            }
        }

        return result;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        psByParent.close();
    }
}
