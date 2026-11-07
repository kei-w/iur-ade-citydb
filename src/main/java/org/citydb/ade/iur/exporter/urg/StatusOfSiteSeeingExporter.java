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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urg.StatusOfSiteSeeing;

import java.sql.*;

public class StatusOfSiteSeeingExporter implements StatisticalGridModuleExporter {
    private final StatisticalGridExporter statisticalGridExporter;
    private final PreparedStatement ps;

    public StatusOfSiteSeeingExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.STATUSOFSITESEEING);
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

    public void doExport(StatusOfSiteSeeing statusOfSiteSeeing, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                statisticalGridExporter.doExport(statusOfSiteSeeing, objectId, "sg_", rs);
                doExport(statusOfSiteSeeing, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("numofhotels", prefix + "numofhotels"),
                table.getColumn("numofryokan", prefix + "numofryokan"),
                table.getColumn("numofpublicinn", prefix + "numofpublicinn"),
                table.getColumn("numofinn", prefix + "numofinn"),
                table.getColumn("numofcotage", prefix + "numofcotage"),
                table.getColumn("numoftemplehotel", prefix + "numoftemplehotel"),
                table.getColumn("numofrentavilla", prefix + "numofrentavilla"),
                table.getColumn("numofyouthhostel", prefix + "numofyouthhostel"),
                table.getColumn("numofcapsulehotel", prefix + "numofcapsulehotel"),
                table.getColumn("numoftrainingcenter", prefix + "numoftrainingcenter"),
                table.getColumn("totalaccomodations", prefix + "totalaccomodations"),
                table.getColumn("totalcapacity", prefix + "totalcapacity"),
                table.getColumn("totalrooms", prefix + "totalrooms"));

        return select;
    }

    public void doExport(StatusOfSiteSeeing statusOfSiteSeeing, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        int numOfHotels = rs.getInt(prefix + "numofhotels");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfHotels(numOfHotels);

        int numOfRyokan = rs.getInt(prefix + "numofryokan");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfRyokan(numOfRyokan);

        int numOfPublicInn = rs.getInt(prefix + "numofpublicinn");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfPublicInn(numOfPublicInn);

        int numOfInn = rs.getInt(prefix + "numofinn");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfInn(numOfInn);

        int numOfCotage = rs.getInt(prefix + "numofcotage");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfCotage(numOfCotage);

        int numOfTempleHotel = rs.getInt(prefix + "numoftemplehotel");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfTempleHotel(numOfTempleHotel);

        int numOfRentaVilla = rs.getInt(prefix + "numofrentavilla");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfRentaVilla(numOfRentaVilla);

        int numOfYouthHostel = rs.getInt(prefix + "numofyouthhostel");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfYouthHostel(numOfYouthHostel);

        int numOfCapsuleHotel = rs.getInt(prefix + "numofcapsulehotel");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfCapsuleHotel(numOfCapsuleHotel);

        int numOfTrainingCenter = rs.getInt(prefix + "numoftrainingcenter");
        if (!rs.wasNull())
            statusOfSiteSeeing.setNumOfTrainingCenter(numOfTrainingCenter);

        int totalAccomodations = rs.getInt(prefix + "totalaccomodations");
        if (!rs.wasNull())
            statusOfSiteSeeing.setTotalAccomodations(totalAccomodations);

        int totalCapacity = rs.getInt(prefix + "totalcapacity");
        if (!rs.wasNull())
            statusOfSiteSeeing.setTotalCapacity(totalCapacity);

        int totalRooms = rs.getInt(prefix + "totalrooms");
        if (!rs.wasNull())
            statusOfSiteSeeing.setTotalRooms(totalRooms);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
