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
import org.citydb.core.database.schema.mapping.FeatureType;
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
import org.citygml4j.ade.iur.model.urg.AreaOfAnnualDiversions;
import org.citygml4j.ade.iur.model.urg.AreaOfAnnualDiversionsProperty;
import org.citygml4j.ade.iur.model.urg.LandUseDiversion;
import org.citygml4j.ade.iur.model.urg.NumberOfAnnualDiversions;
import org.citygml4j.ade.iur.model.urg.NumberOfAnnualDiversionsProperty;

import java.sql.*;

public class LandUseDiversionExporter implements StatisticalGridModuleExporter {
    private final ExportManager manager;
    private final StatisticalGridExporter statisticalGridExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psNumberOfAnnualDiversion;
    private final PreparedStatement psAreaOfAnnualDiversion;
    private final NumberOfAnnualDiversionsExporter numberOfAnnualDiversionsExporter;
    private final AreaOfAnnualDiversionsExporter areaOfAnnualDiversionsExporter;
    private final AttributeValueSplitter valueSplitter;

    public LandUseDiversionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LANDUSEDIVERSION);
        statisticalGridExporter = manager.getExporter(StatisticalGridExporter.class);
        numberOfAnnualDiversionsExporter = manager.getExporter(NumberOfAnnualDiversionsExporter.class);
        areaOfAnnualDiversionsExporter = manager.getExporter(AreaOfAnnualDiversionsExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table statisticalGrid = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STATISTICALGRID)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(statisticalGrid, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        statisticalGridExporter.addProjection(select, statisticalGrid, "sg_");
        ps = connection.prepareStatement(select.toString());

        Table numberOfAnnualDiversions = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.LANDUSEDI_TO_NUMBEROFA)));
        Select numberOfAnnualDiversionSelect = new Select().addProjection(numberOfAnnualDiversions.getColumn("numberofannualdiversio_id"))
                .addSelection(ComparisonFactory.equalTo(numberOfAnnualDiversions.getColumn("landusediversion_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(numberOfAnnualDiversions.getColumn("numberofannualdiversio_id")));
        psNumberOfAnnualDiversion = connection.prepareStatement(numberOfAnnualDiversionSelect.toString());
        Table areaOfAnnualDiversions = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.LANDUSEDI_TO_AREAOFANN)));
        Select areaOfAnnualDiversionSelect = new Select().addProjection(areaOfAnnualDiversions.getColumn("areaofannualdiversions_id"))
                .addSelection(ComparisonFactory.equalTo(areaOfAnnualDiversions.getColumn("landusediversion_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(areaOfAnnualDiversions.getColumn("areaofannualdiversions_id")));
        psAreaOfAnnualDiversion = connection.prepareStatement(areaOfAnnualDiversionSelect.toString());
    }

    public void doExport(LandUseDiversion landUseDiversion, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                statisticalGridExporter.doExport(landUseDiversion, objectId, "sg_", rs);
                doExport(landUseDiversion, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), landUseDiversion, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        return select;
    }

    public void doExport(LandUseDiversion landUseDiversion, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        psNumberOfAnnualDiversion.setLong(1, objectId);
        try (ResultSet numberOfAnnualDiversionRs = psNumberOfAnnualDiversion.executeQuery()) {
            while (numberOfAnnualDiversionRs.next()) {
                NumberOfAnnualDiversions numberOfAnnualDiversion = new NumberOfAnnualDiversions();
                numberOfAnnualDiversionsExporter.doExport(numberOfAnnualDiversion, numberOfAnnualDiversionRs.getLong("numberofannualdiversio_id"));
                landUseDiversion.getNumberOfAnnualDiversion().add(new NumberOfAnnualDiversionsProperty(numberOfAnnualDiversion));
            }
        }

        psAreaOfAnnualDiversion.setLong(1, objectId);
        try (ResultSet areaOfAnnualDiversionRs = psAreaOfAnnualDiversion.executeQuery()) {
            while (areaOfAnnualDiversionRs.next()) {
                AreaOfAnnualDiversions areaOfAnnualDiversion = new AreaOfAnnualDiversions();
                areaOfAnnualDiversionsExporter.doExport(areaOfAnnualDiversion, areaOfAnnualDiversionRs.getLong("areaofannualdiversions_id"));
                landUseDiversion.getAreaOfAnnualDiversion().add(new AreaOfAnnualDiversionsProperty(areaOfAnnualDiversion));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psNumberOfAnnualDiversion.close();
        psAreaOfAnnualDiversion.close();
    }
}
