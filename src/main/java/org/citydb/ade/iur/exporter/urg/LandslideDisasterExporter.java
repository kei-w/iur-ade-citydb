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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urg.LandSlideTypeValue;
import org.citygml4j.ade.iur.model.urg.LandslideDisaster;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class LandslideDisasterExporter implements StatisticalGridModuleExporter {
    private final ExportManager manager;
    private final StatisticalGridExporter statisticalGridExporter;
    private final PreparedStatement ps;

    public LandslideDisasterExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LANDSLIDEDISASTER);
        statisticalGridExporter = manager.getExporter(StatisticalGridExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table statisticalGrid = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STATISTICALGRID)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(statisticalGrid, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        statisticalGridExporter.addProjection(select, statisticalGrid, "sg_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(LandslideDisaster landslideDisaster, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                statisticalGridExporter.doExport(landslideDisaster, objectId, "sg_", rs);
                doExport(landslideDisaster, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), landslideDisaster, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("dateofoccurence", prefix + "dateofoccurence"),
                table.getColumn("landslidetype", prefix + "landslidetype"),
                table.getColumn("maximumrainfallperday", prefix + "maximumrainfallperday"),
                table.getColumn("maximumrainfallperday_uom", prefix + "maximumrainfallperday_uom"),
                table.getColumn("maximumrainfallperhour", prefix + "maximumrainfallperhour"),
                table.getColumn("maximumrainfallperhour_uom", prefix + "maximumrainfallperhour_uom"),
                table.getColumn("slope", prefix + "slope"),
                table.getColumn("sedimentdischarge", prefix + "sedimentdischarge"),
                table.getColumn("length", prefix + "length"),
                table.getColumn("length_uom", prefix + "length_uom"));

        return select;
    }

    public void doExport(LandslideDisaster landslideDisaster, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        Date dateOfOccurence = rs.getDate(prefix + "dateofoccurence");
        if (!rs.wasNull())
            landslideDisaster.setDateOfOccurence(dateOfOccurence.toLocalDate());

        String landSlideType = rs.getString(prefix + "landslidetype");
        if (!rs.wasNull())
            landslideDisaster.setLandSlideType(LandSlideTypeValue.fromValue(landSlideType));

        double maximumRainfallPerDay = rs.getDouble(prefix + "maximumrainfallperday");
        if (!rs.wasNull()) {
            Length length = new Length(maximumRainfallPerDay);
            length.setUom(rs.getString(prefix + "maximumrainfallperday_uom"));
            landslideDisaster.setMaximumRainfallPerDay(length);
        }

        double maximumRainfallPerHour = rs.getDouble(prefix + "maximumrainfallperhour");
        if (!rs.wasNull()) {
            Length length = new Length(maximumRainfallPerHour);
            length.setUom(rs.getString(prefix + "maximumrainfallperhour_uom"));
            landslideDisaster.setMaximumRainfallPerHour(length);
        }

        int slope = rs.getInt(prefix + "slope");
        if (!rs.wasNull())
            landslideDisaster.setSlope(slope);

        int sedimentDischarge = rs.getInt(prefix + "sedimentdischarge");
        if (!rs.wasNull())
            landslideDisaster.setSedimentDischarge(sedimentDischarge);

        double lengthValue = rs.getDouble(prefix + "length");
        if (!rs.wasNull()) {
            Length length = new Length(lengthValue);
            length.setUom(rs.getString(prefix + "length_uom"));
            landslideDisaster.setLength(length);
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
