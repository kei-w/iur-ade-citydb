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
import org.citygml4j.ade.iur.model.urg.LandPrice;
import org.citygml4j.ade.iur.model.urg.LandPricePerLandUse;
import org.citygml4j.ade.iur.model.urg.LandPricePerLandUseProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class LandPriceExporter implements StatisticalGridModuleExporter {
    private final StatisticalGridExporter statisticalGridExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psLandPrice;
    private final LandPricePerLandUseExporter landPricePerLandUseExporter;
    private final AttributeValueSplitter valueSplitter;

    public LandPriceExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LANDPRICE);
        statisticalGridExporter = manager.getExporter(StatisticalGridExporter.class);
        landPricePerLandUseExporter = manager.getExporter(LandPricePerLandUseExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table statisticalGrid = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STATISTICALGRID)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(statisticalGrid, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        statisticalGridExporter.addProjection(select, statisticalGrid, "sg_");
        ps = connection.prepareStatement(select.toString());

        Table landPrices = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.LANDPRICE_TO_LANDPRICE)));
        Select landPriceSelect = new Select().addProjection(landPrices.getColumn("landpriceperlanduse_id"))
                .addSelection(ComparisonFactory.equalTo(landPrices.getColumn("landprice_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(landPrices.getColumn("landpriceperlanduse_id")));
        psLandPrice = connection.prepareStatement(landPriceSelect.toString());
    }

    public void doExport(LandPrice landPrice, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                statisticalGridExporter.doExport(landPrice, objectId, "sg_", rs);
                doExport(landPrice, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("currencyunit", prefix + "currencyunit"),
                table.getColumn("currencyunit_codespace", prefix + "currencyunit_codespace"));

        return select;
    }

    public void doExport(LandPrice landPrice, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        psLandPrice.setLong(1, objectId);
        try (ResultSet landPriceValueRs = psLandPrice.executeQuery()) {
            while (landPriceValueRs.next()) {
                LandPricePerLandUse landPriceValue = new LandPricePerLandUse();
                landPricePerLandUseExporter.doExport(landPriceValue, landPriceValueRs.getLong("landpriceperlanduse_id"));
                landPrice.getLandPrice().add(new LandPricePerLandUseProperty(landPriceValue));
            }
        }

        String currencyUnit = rs.getString(prefix + "currencyunit");
        if (!rs.wasNull()) {
            Code code = new Code(currencyUnit);
            code.setCodeSpace(rs.getString(prefix + "currencyunit_codespace"));
            landPrice.setCurrencyUnit(code);
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psLandPrice.close();
    }
}
