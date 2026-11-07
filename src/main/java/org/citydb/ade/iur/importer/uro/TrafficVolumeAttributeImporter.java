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

package org.citydb.ade.iur.importer.uro;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.TrafficVolumeAttribute;

import java.sql.*;
import java.time.LocalDate;

public class TrafficVolumeAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final RoadAttributeImporter roadAttributeImporter;

    private int batchCounter;

    public TrafficVolumeAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRAFFICVOLUMEATTRIBUTE)) + " " +
                "(id, sectionid, routename, weekday12hourtrafficvolume, weekday24hourtrafficvolume, largevehiclerate," +
                "congestionrate, averagetravelspeedincongesti, averageinboundtravelspeedinc," +
                "averageoutboundtravelspeedin, averageinboundtravelspeednot, averageoutboundtravelspeedno," +
                "observationpointname, reference, surveyyear) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        roadAttributeImporter = manager.getImporter(RoadAttributeImporter.class);
    }

    public long doImport(TrafficVolumeAttribute trafficVolumeAttribute) throws CityGMLImportException, SQLException {
        long objectId = roadAttributeImporter.doImport(trafficVolumeAttribute);
        ps.setLong(1, objectId);

        ps.setString(2, trafficVolumeAttribute.getSectionID());

        ps.setString(3, trafficVolumeAttribute.getRouteName());

        if (trafficVolumeAttribute.getWeekday12hourTrafficVolume() != null)
            ps.setInt(4, trafficVolumeAttribute.getWeekday12hourTrafficVolume());
        else
            ps.setNull(4, Types.INTEGER);

        if (trafficVolumeAttribute.getWeekday24hourTrafficVolume() != null)
            ps.setInt(5, trafficVolumeAttribute.getWeekday24hourTrafficVolume());
        else
            ps.setNull(5, Types.INTEGER);

        if (trafficVolumeAttribute.getLargeVehicleRate() != null)
            ps.setDouble(6, trafficVolumeAttribute.getLargeVehicleRate());
        else
            ps.setNull(6, Types.DOUBLE);

        if (trafficVolumeAttribute.getCongestionRate() != null)
            ps.setDouble(7, trafficVolumeAttribute.getCongestionRate());
        else
            ps.setNull(7, Types.DOUBLE);

        if (trafficVolumeAttribute.getAverageTravelSpeedInCongestion() != null)
            ps.setDouble(8, trafficVolumeAttribute.getAverageTravelSpeedInCongestion());
        else
            ps.setNull(8, Types.DOUBLE);

        if (trafficVolumeAttribute.getAverageInboundTravelSpeedInCongestion() != null)
            ps.setDouble(9, trafficVolumeAttribute.getAverageInboundTravelSpeedInCongestion());
        else
            ps.setNull(9, Types.DOUBLE);

        if (trafficVolumeAttribute.getAverageOutboundTravelSpeedInCongestion() != null)
            ps.setDouble(10, trafficVolumeAttribute.getAverageOutboundTravelSpeedInCongestion());
        else
            ps.setNull(10, Types.DOUBLE);

        if (trafficVolumeAttribute.getAverageInboundTravelSpeedNotCongestion() != null)
            ps.setDouble(11, trafficVolumeAttribute.getAverageInboundTravelSpeedNotCongestion());
        else
            ps.setNull(11, Types.DOUBLE);

        if (trafficVolumeAttribute.getAverageOutboundTravelSpeedNotCongestion() != null)
            ps.setDouble(12, trafficVolumeAttribute.getAverageOutboundTravelSpeedNotCongestion());
        else
            ps.setNull(12, Types.DOUBLE);

        ps.setString(13, trafficVolumeAttribute.getObservationPointName());

        ps.setString(14, trafficVolumeAttribute.getReference());

        if (trafficVolumeAttribute.getSurveyYear() != null)
            ps.setDate(15, Date.valueOf(LocalDate.of(trafficVolumeAttribute.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(15, Types.DATE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TRAFFICVOLUMEATTRIBUTE));

        return objectId;
    }

    @Override
    public void executeBatch() throws CityGMLImportException, SQLException {
        if (batchCounter > 0) {
            ps.executeBatch();
            batchCounter = 0;
        }
    }

    @Override
    public void close() throws CityGMLImportException, SQLException {
        ps.close();
    }
}
