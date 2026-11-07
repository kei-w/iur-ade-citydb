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
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.CensusBlock;
import org.citygml4j.ade.iur.model.urf.DaytimePopulationType;
import org.citygml4j.ade.iur.model.urf.DaytimePopulationTypeProperty;
import org.citygml4j.ade.iur.model.urf.EconomicActivityType;
import org.citygml4j.ade.iur.model.urf.EconomicActivityTypeProperty;
import org.citygml4j.ade.iur.model.urf.PopulationType;
import org.citygml4j.ade.iur.model.urf.PopulationTypeProperty;

import java.sql.*;

public class CensusBlockExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final PreparedStatement ps;
    private final PopulationTypeExporter populationTypeExporter;
    private final DaytimePopulationTypeExporter daytimePopulationTypeExporter;
    private final EconomicActivityTypeExporter economicActivityTypeExporter;

    public CensusBlockExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CENSUSBLOCK);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        populationTypeExporter = manager.getExporter(PopulationTypeExporter.class);
        daytimePopulationTypeExporter = manager.getExporter(DaytimePopulationTypeExporter.class);
        economicActivityTypeExporter = manager.getExporter(EconomicActivityTypeExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(CensusBlock censusBlock, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(censusBlock, objectId, "uf_", rs);
                doExport(censusBlock, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), censusBlock, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("townname", prefix + "townname"),
                table.getColumn("blockname", prefix + "blockname"),
                table.getColumn("populationtype_id", prefix + "populationtype_id"),
                table.getColumn("daytimepopulationtype_id", prefix + "daytimepopulationtype_id"),
                table.getColumn("economicactivitytype_id", prefix + "economicactivitytype_id"));

        return select;
    }

    public void doExport(CensusBlock censusBlock, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        censusBlock.setTownName(rs.getString(prefix + "townname"));

        censusBlock.setBlockName(rs.getString(prefix + "blockname"));

        long populationTypeId = rs.getLong(prefix + "populationtype_id");
        if (populationTypeId != 0) {
            PopulationType populationType = new PopulationType();
            populationTypeExporter.doExport(populationType, populationTypeId);
            censusBlock.setPopulationType(new PopulationTypeProperty(populationType));
        }

        long daytimePopulationTypeId = rs.getLong(prefix + "daytimepopulationtype_id");
        if (daytimePopulationTypeId != 0) {
            DaytimePopulationType daytimePopulationType = new DaytimePopulationType();
            daytimePopulationTypeExporter.doExport(daytimePopulationType, daytimePopulationTypeId);
            censusBlock.setDaytimePopulationType(new DaytimePopulationTypeProperty(daytimePopulationType));
        }

        long economicActivityTypeId = rs.getLong(prefix + "economicactivitytype_id");
        if (economicActivityTypeId != 0) {
            EconomicActivityType economicActivityType = new EconomicActivityType();
            economicActivityTypeExporter.doExport(economicActivityType, economicActivityTypeId);
            censusBlock.setEconomicActivityType(new EconomicActivityTypeProperty(economicActivityType));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
