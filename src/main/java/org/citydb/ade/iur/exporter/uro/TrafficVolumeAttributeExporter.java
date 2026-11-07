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

package org.citydb.ade.iur.exporter.uro;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.TrafficVolumeAttribute;

import java.time.Year;

import java.sql.*;

public class TrafficVolumeAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public TrafficVolumeAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.TRAFFICVOLUMEATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("sectionid", "weekday12hourtrafficvolume", "weekday24hourtrafficvolume", "largevehiclerate",
                        "congestionrate", "averagetravelspeedincongesti", "averageinboundtravelspeedinc",
                        "averageoutboundtravelspeedin", "observationpointname", "reference", "surveyyear"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(TrafficVolumeAttribute trafficVolumeAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                trafficVolumeAttribute.setSectionID(rs.getString("sectionid"));

                int weekday12hourTrafficVolume = rs.getInt("weekday12hourtrafficvolume");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setWeekday12hourTrafficVolume(weekday12hourTrafficVolume);

                int weekday24hourTrafficVolume = rs.getInt("weekday24hourtrafficvolume");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setWeekday24hourTrafficVolume(weekday24hourTrafficVolume);

                double largeVehicleRate = rs.getDouble("largevehiclerate");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setLargeVehicleRate(largeVehicleRate);

                double congestionRate = rs.getDouble("congestionrate");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setCongestionRate(congestionRate);

                double averageTravelSpeedInCongestion = rs.getDouble("averagetravelspeedincongesti");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setAverageTravelSpeedInCongestion(averageTravelSpeedInCongestion);

                double averageInboundTravelSpeedInCongestion = rs.getDouble("averageinboundtravelspeedinc");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setAverageInboundTravelSpeedInCongestion(averageInboundTravelSpeedInCongestion);

                double averageOutboundTravelSpeedInCongestion = rs.getDouble("averageoutboundtravelspeedin");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setAverageOutboundTravelSpeedInCongestion(averageOutboundTravelSpeedInCongestion);

                trafficVolumeAttribute.setObservationPointName(rs.getString("observationpointname"));

                trafficVolumeAttribute.setReference(rs.getString("reference"));

                Date surveyYear = rs.getDate("surveyyear");
                if (!rs.wasNull())
                    trafficVolumeAttribute.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
