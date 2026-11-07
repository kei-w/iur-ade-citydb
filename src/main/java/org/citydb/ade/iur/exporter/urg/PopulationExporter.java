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

package org.citydb.ade.iur.exporter.urg;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urg.Population;
import org.citygml4j.ade.iur.model.urg.PopulationByAgeAndSex;
import org.citygml4j.ade.iur.model.urg.PopulationByAgeAndSexProperty;

import java.sql.*;

public class PopulationExporter implements StatisticalGridModuleExporter {
    private final StatisticalGridExporter statisticalGridExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psPopulationByAgeAndSex;
    private final PopulationByAgeAndSexExporter populationByAgeAndSexExporter;
    private final AttributeValueSplitter valueSplitter;

    public PopulationExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.POPULATION);
        statisticalGridExporter = manager.getExporter(StatisticalGridExporter.class);
        populationByAgeAndSexExporter = manager.getExporter(PopulationByAgeAndSexExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table statisticalGrid = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STATISTICALGRID)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(statisticalGrid, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        statisticalGridExporter.addProjection(select, statisticalGrid, "sg_");
        ps = connection.prepareStatement(select.toString());

        Table populationByAgeAndSexs = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.POPULATIO_TO_POPULATIO)));
        Select populationByAgeAndSexSelect = new Select().addProjection(populationByAgeAndSexs.getColumn("populationbyageandsex_id"))
                .addSelection(ComparisonFactory.equalTo(populationByAgeAndSexs.getColumn("population_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(populationByAgeAndSexs.getColumn("populationbyageandsex_id")));
        psPopulationByAgeAndSex = connection.prepareStatement(populationByAgeAndSexSelect.toString());
    }

    public void doExport(Population population, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                statisticalGridExporter.doExport(population, objectId, "sg_", rs);
                doExport(population, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("total", prefix + "total"),
                table.getColumn("daytimepopulation", prefix + "daytimepopulation"),
                table.getColumn("daytimepopulationdensity", prefix + "daytimepopulationdensity"),
                table.getColumn("daytimeofficecommuter", prefix + "daytimeofficecommuter"),
                table.getColumn("daytimeschoolcommuter", prefix + "daytimeschoolcommuter"),
                table.getColumn("daytimeunder15", prefix + "daytimeunder15"),
                table.getColumn("daytimeover15", prefix + "daytimeover15"),
                table.getColumn("agriculturalworker", prefix + "agriculturalworker"),
                table.getColumn("noncommuter", prefix + "noncommuter"),
                table.getColumn("naturalincrease", prefix + "naturalincrease"),
                table.getColumn("births", prefix + "births"),
                table.getColumn("deaths", prefix + "deaths"),
                table.getColumn("socialincrease", prefix + "socialincrease"),
                table.getColumn("movefrom", prefix + "movefrom"),
                table.getColumn("moveto", prefix + "moveto"),
                table.getColumn("increasement", prefix + "increasement"),
                table.getColumn("malepopulation", prefix + "malepopulation"),
                table.getColumn("femalepopulation", prefix + "femalepopulation"));

        return select;
    }

    public void doExport(Population population, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        int total = rs.getInt(prefix + "total");
        if (!rs.wasNull())
            population.setTotal(total);

        int daytimePopulation = rs.getInt(prefix + "daytimepopulation");
        if (!rs.wasNull())
            population.setDaytimePopulation(daytimePopulation);

        double daytimePopulationDensity = rs.getDouble(prefix + "daytimepopulationdensity");
        if (!rs.wasNull())
            population.setDaytimePopulationDensity(daytimePopulationDensity);

        int daytimeOfficeCommuter = rs.getInt(prefix + "daytimeofficecommuter");
        if (!rs.wasNull())
            population.setDaytimeOfficeCommuter(daytimeOfficeCommuter);

        int daytimeSchoolCommuter = rs.getInt(prefix + "daytimeschoolcommuter");
        if (!rs.wasNull())
            population.setDaytimeSchoolCommuter(daytimeSchoolCommuter);

        int daytimeUnder15 = rs.getInt(prefix + "daytimeunder15");
        if (!rs.wasNull())
            population.setDaytimeUnder15(daytimeUnder15);

        int daytimeOver15 = rs.getInt(prefix + "daytimeover15");
        if (!rs.wasNull())
            population.setDaytimeOver15(daytimeOver15);

        int agriculturalWorker = rs.getInt(prefix + "agriculturalworker");
        if (!rs.wasNull())
            population.setAgriculturalWorker(agriculturalWorker);

        int nonCommuter = rs.getInt(prefix + "noncommuter");
        if (!rs.wasNull())
            population.setNonCommuter(nonCommuter);

        int naturalIncrease = rs.getInt(prefix + "naturalincrease");
        if (!rs.wasNull())
            population.setNaturalIncrease(naturalIncrease);

        int births = rs.getInt(prefix + "births");
        if (!rs.wasNull())
            population.setBirths(births);

        int deaths = rs.getInt(prefix + "deaths");
        if (!rs.wasNull())
            population.setDeaths(deaths);

        int socialIncrease = rs.getInt(prefix + "socialincrease");
        if (!rs.wasNull())
            population.setSocialIncrease(socialIncrease);

        int moveFrom = rs.getInt(prefix + "movefrom");
        if (!rs.wasNull())
            population.setMoveFrom(moveFrom);

        int moveTo = rs.getInt(prefix + "moveto");
        if (!rs.wasNull())
            population.setMoveTo(moveTo);

        int increasement = rs.getInt(prefix + "increasement");
        if (!rs.wasNull())
            population.setIncreasement(increasement);

        int malePopulation = rs.getInt(prefix + "malepopulation");
        if (!rs.wasNull())
            population.setMalePopulation(malePopulation);

        int femalePopulation = rs.getInt(prefix + "femalepopulation");
        if (!rs.wasNull())
            population.setFemalePopulation(femalePopulation);

        psPopulationByAgeAndSex.setLong(1, objectId);
        try (ResultSet populationByAgeAndSexRs = psPopulationByAgeAndSex.executeQuery()) {
            while (populationByAgeAndSexRs.next()) {
                PopulationByAgeAndSex populationByAgeAndSex = new PopulationByAgeAndSex();
                populationByAgeAndSexExporter.doExport(populationByAgeAndSex, populationByAgeAndSexRs.getLong("populationbyageandsex_id"));
                population.getPopulationByAgeAndSex().add(new PopulationByAgeAndSexProperty(populationByAgeAndSex));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psPopulationByAgeAndSex.close();
    }
}
