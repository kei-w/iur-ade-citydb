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
import org.citygml4j.ade.iur.model.urg.GenericGridCell;
import org.citygml4j.ade.iur.model.urg.KeyValuePair;
import org.citygml4j.ade.iur.model.urg.KeyValuePairProperty;

import java.sql.*;

public class GenericGridCellExporter implements StatisticalGridModuleExporter {
    private final StatisticalGridExporter statisticalGridExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psGenericValue;
    private final KeyValuePairExporter keyValuePairExporter;
    private final AttributeValueSplitter valueSplitter;

    public GenericGridCellExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.GENERICGRIDCELL);
        statisticalGridExporter = manager.getExporter(StatisticalGridExporter.class);
        keyValuePairExporter = manager.getExporter(KeyValuePairExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table statisticalGrid = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STATISTICALGRID)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(statisticalGrid, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        statisticalGridExporter.addProjection(select, statisticalGrid, "sg_");
        ps = connection.prepareStatement(select.toString());

        Table genericValues = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.GENERICGR_TO_KEYVALUEP)));
        Select genericValueSelect = new Select().addProjection(genericValues.getColumn("keyvaluepair_id"))
                .addSelection(ComparisonFactory.equalTo(genericValues.getColumn("genericgridcell_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(genericValues.getColumn("keyvaluepair_id")));
        psGenericValue = connection.prepareStatement(genericValueSelect.toString());
    }

    public void doExport(GenericGridCell genericGridCell, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                statisticalGridExporter.doExport(genericGridCell, objectId, "sg_", rs);
                doExport(genericGridCell, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        return select;
    }

    public void doExport(GenericGridCell genericGridCell, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        psGenericValue.setLong(1, objectId);
        try (ResultSet genericValueRs = psGenericValue.executeQuery()) {
            while (genericValueRs.next()) {
                KeyValuePair genericValue = new KeyValuePair();
                keyValuePairExporter.doExport(genericValue, genericValueRs.getLong("keyvaluepair_id"));
                genericGridCell.getGenericValue().add(new KeyValuePairProperty(genericValue));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psGenericValue.close();
    }
}
