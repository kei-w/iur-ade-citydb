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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.HighLevelUseDistrict;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class HighLevelUseDistrictExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final DistrictsAndZonesExporter districtsAndZonesExporter;
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public HighLevelUseDistrictExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.HIGHLEVELUSEDISTRICT);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        districtsAndZonesExporter = manager.getExporter(DistrictsAndZonesExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Table zone = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE)));
        Table districtsAndZones = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.DISTRICTSANDZONES)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(zone, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(districtsAndZones, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        zoneExporter.addProjection(select, zone, "z_");
        districtsAndZonesExporter.addProjection(select, districtsAndZones, "daz_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(HighLevelUseDistrict highLevelUseDistrict, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(highLevelUseDistrict, objectId, "uf_", rs);
                zoneExporter.doExport(highLevelUseDistrict, objectId, "z_", rs);
                districtsAndZonesExporter.doExport(highLevelUseDistrict, objectId, "daz_", rs);
                doExport(highLevelUseDistrict, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("maximumfloorarearate", prefix + "maximumfloorarearate"),
                table.getColumn("minimumfloorarearate", prefix + "minimumfloorarearate"),
                table.getColumn("maximumbuildingcoveragerate", prefix + "maximumbuildingcoveragerate"),
                table.getColumn("minimumbuildingarea", prefix + "minimumbuildingarea"),
                table.getColumn("minimumbuildingarea_uom", prefix + "minimumbuildingarea_uom"),
                table.getColumn("setbacksize", prefix + "setbacksize"));

        return select;
    }

    public void doExport(HighLevelUseDistrict highLevelUseDistrict, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "maximumfloorarearate")))
            highLevelUseDistrict.getMaximumFloorAreaRate().add(splitValue.asDouble(0));

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "minimumfloorarearate")))
            highLevelUseDistrict.getMinimumFloorAreaRate().add(splitValue.asDouble(0));

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "maximumbuildingcoveragerate")))
            highLevelUseDistrict.getMaximumBuildingCoverageRate().add(splitValue.asDouble(0));

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "minimumbuildingarea"), rs.getString(prefix + "minimumbuildingarea_uom"))) {
            Measure measure = new Measure(splitValue.asDouble(0));
            measure.setUom(splitValue.result(1));
            highLevelUseDistrict.getMinimumBuildingArea().add(measure);
        }

        String setbackSize = rs.getString(prefix + "setbacksize");
        if (!rs.wasNull())
            highLevelUseDistrict.setSetbackSize(new StringOrRef(setbackSize));
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
