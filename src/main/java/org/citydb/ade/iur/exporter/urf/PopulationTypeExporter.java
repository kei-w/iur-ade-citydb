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
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.urf.PopulationByAgeAndSexType;
import org.citygml4j.ade.iur.model.urf.PopulationByAgeAndSexTypeProperty;
import org.citygml4j.ade.iur.model.urf.PopulationType;

import java.sql.*;

public class PopulationTypeExporter implements UrbanFunctionModuleExporter {
    private final PreparedStatement psPopulation;
    private final PreparedStatement ps;
    private final PopulationByAgeAndSexTypeExporter populationByAgeAndSexTypeExporter;
    private final AttributeValueSplitter valueSplitter;

    public PopulationTypeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.POPULATIONTYPE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table populations = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.POPULATIO_TO_POPULAT_2)));
        Select populationSelect = new Select().addProjection(populations.getColumn("populationbyageandsext_id"))
                .addSelection(ComparisonFactory.equalTo(populations.getColumn("populationtype_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(populations.getColumn("populationbyageandsext_id")));
        psPopulation = connection.prepareStatement(populationSelect.toString());
        Select select = new Select().addProjection(table.getColumns("populationdensity"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        populationByAgeAndSexTypeExporter = manager.getExporter(PopulationByAgeAndSexTypeExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(PopulationType populationType, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                psPopulation.setLong(1, objectId);
                try (ResultSet populationRs = psPopulation.executeQuery()) {
                    while (populationRs.next()) {
                        PopulationByAgeAndSexType population = new PopulationByAgeAndSexType();
                        populationByAgeAndSexTypeExporter.doExport(population, populationRs.getLong("populationbyageandsext_id"));
                        populationType.getPopulation().add(new PopulationByAgeAndSexTypeProperty(population));
                    }
                }

                double populationDensity = rs.getDouble("populationdensity");
                if (!rs.wasNull())
                    populationType.setPopulationDensity(populationDensity);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psPopulation.close();

    }
}
