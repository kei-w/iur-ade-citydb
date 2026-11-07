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
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.UseDistrict;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class UseDistrictExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final DistrictsAndZonesExporter districtsAndZonesExporter;
    private final PreparedStatement ps;

    public UseDistrictExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.USEDISTRICT);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        districtsAndZonesExporter = manager.getExporter(DistrictsAndZonesExporter.class);

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

    public void doExport(UseDistrict useDistrict, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(useDistrict, objectId, "uf_", rs);
                zoneExporter.doExport(useDistrict, objectId, "z_", rs);
                districtsAndZonesExporter.doExport(useDistrict, objectId, "daz_", rs);
                doExport(useDistrict, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("floorarearate", prefix + "floorarearate"),
                table.getColumn("minimumsitearea", prefix + "minimumsitearea"),
                table.getColumn("minimumsitearea_uom", prefix + "minimumsitearea_uom"),
                table.getColumn("buildingcoveragerate", prefix + "buildingcoveragerate"),
                table.getColumn("wallsetbackdistance", prefix + "wallsetbackdistance"),
                table.getColumn("buildingheightlimits", prefix + "buildingheightlimits"),
                table.getColumn("buildingheightlimits_uom", prefix + "buildingheightlimits_uom"),
                table.getColumn("buildingrestrictions", prefix + "buildingrestrictions"),
                table.getColumn("otherrestrictions", prefix + "otherrestrictions"),
                table.getColumn("setbackrestrictions", prefix + "setbackrestrictions"),
                table.getColumn("shaderegulation", prefix + "shaderegulation"));

        return select;
    }

    public void doExport(UseDistrict useDistrict, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        double floorAreaRate = rs.getDouble(prefix + "floorarearate");
        if (!rs.wasNull())
            useDistrict.setFloorAreaRate(floorAreaRate);

        double minimumSiteArea = rs.getDouble(prefix + "minimumsitearea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(minimumSiteArea);
            measure.setUom(rs.getString(prefix + "minimumsitearea_uom"));
            useDistrict.setMinimumSiteArea(measure);
        }

        double buildingCoverageRate = rs.getDouble(prefix + "buildingcoveragerate");
        if (!rs.wasNull())
            useDistrict.setBuildingCoverageRate(buildingCoverageRate);

        String wallSetbackDistance = rs.getString(prefix + "wallsetbackdistance");
        if (!rs.wasNull())
            useDistrict.setWallSetbackDistance(new StringOrRef(wallSetbackDistance));

        double buildingHeightLimits = rs.getDouble(prefix + "buildingheightlimits");
        if (!rs.wasNull()) {
            Length length = new Length(buildingHeightLimits);
            length.setUom(rs.getString(prefix + "buildingheightlimits_uom"));
            useDistrict.setBuildingHeightLimits(length);
        }

        String buildingRestrictions = rs.getString(prefix + "buildingrestrictions");
        if (!rs.wasNull())
            useDistrict.setBuildingRestrictions(new StringOrRef(buildingRestrictions));

        String otherRestrictions = rs.getString(prefix + "otherrestrictions");
        if (!rs.wasNull())
            useDistrict.setOtherRestrictions(new StringOrRef(otherRestrictions));

        String setbackRestrictions = rs.getString(prefix + "setbackrestrictions");
        if (!rs.wasNull())
            useDistrict.setSetbackRestrictions(new StringOrRef(setbackRestrictions));

        String shadeRegulation = rs.getString(prefix + "shaderegulation");
        if (!rs.wasNull())
            useDistrict.setShadeRegulation(new StringOrRef(shadeRegulation));
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
