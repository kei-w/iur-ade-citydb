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
import org.citygml4j.ade.iur.model.urf.ParkingPlaceAttribute;
import org.citygml4j.ade.iur.model.urf.ParkingPlaceAttributeProperty;
import org.citygml4j.ade.iur.model.urf.TrafficFacility;
import org.citygml4j.ade.iur.model.urf.UrbanRapidTransitRailroadAttribute;
import org.citygml4j.ade.iur.model.urf.UrbanRapidTransitRailroadAttributeProperty;
import org.citygml4j.ade.iur.model.urf.UrbanRoadAttribute;
import org.citygml4j.ade.iur.model.urf.UrbanRoadAttributeProperty;
import org.citygml4j.ade.iur.model.urf.VehicleTerminalAttribute;
import org.citygml4j.ade.iur.model.urf.VehicleTerminalAttributeProperty;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class TrafficFacilityExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final UrbanFacilityExporter urbanFacilityExporter;
    private final PreparedStatement ps;
    private final UrbanRoadAttributeExporter urbanRoadAttributeExporter;
    private final UrbanRapidTransitRailroadAttributeExporter urbanRapidTransitRailroadAttributeExporter;
    private final ParkingPlaceAttributeExporter parkingPlaceAttributeExporter;
    private final VehicleTerminalAttributeExporter vehicleTerminalAttributeExporter;

    public TrafficFacilityExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.TRAFFICFACILITY);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        urbanFacilityExporter = manager.getExporter(UrbanFacilityExporter.class);
        urbanRoadAttributeExporter = manager.getExporter(UrbanRoadAttributeExporter.class);
        urbanRapidTransitRailroadAttributeExporter = manager.getExporter(UrbanRapidTransitRailroadAttributeExporter.class);
        parkingPlaceAttributeExporter = manager.getExporter(ParkingPlaceAttributeExporter.class);
        vehicleTerminalAttributeExporter = manager.getExporter(VehicleTerminalAttributeExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Table zone = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE)));
        Table urbanFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFACILITY)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(zone, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(urbanFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        zoneExporter.addProjection(select, zone, "z_");
        urbanFacilityExporter.addProjection(select, urbanFacility, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(TrafficFacility trafficFacility, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(trafficFacility, objectId, "uf_", rs);
                zoneExporter.doExport(trafficFacility, objectId, "z_", rs);
                urbanFacilityExporter.doExport(trafficFacility, objectId, "uf_", rs);
                doExport(trafficFacility, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("startlocation", prefix + "startlocation"),
                table.getColumn("endlocation", prefix + "endlocation"),
                table.getColumn("vialocations", prefix + "vialocations"),
                table.getColumn("length", prefix + "length"),
                table.getColumn("length_uom", prefix + "length_uom"),
                table.getColumn("width", prefix + "width"),
                table.getColumn("width_uom", prefix + "width_uom"),
                table.getColumn("urbanroadattribute_id", prefix + "urbanroadattribute_id"),
                table.getColumn("urbanrapidtransitrailroad_id", prefix + "urbanrapidtransitrailroad_id"),
                table.getColumn("parkingplaceattribute_id", prefix + "parkingplaceattribute_id"),
                table.getColumn("vehicleterminalattribute_id", prefix + "vehicleterminalattribute_id"));

        return select;
    }

    public void doExport(TrafficFacility trafficFacility, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        trafficFacility.setStartLocation(rs.getString(prefix + "startlocation"));

        trafficFacility.setEndLocation(rs.getString(prefix + "endlocation"));

        trafficFacility.setViaLocations(rs.getString(prefix + "vialocations"));

        double lengthValue = rs.getDouble(prefix + "length");
        if (!rs.wasNull()) {
            Length length = new Length(lengthValue);
            length.setUom(rs.getString(prefix + "length_uom"));
            trafficFacility.setLength(length);
        }

        double width = rs.getDouble(prefix + "width");
        if (!rs.wasNull()) {
            Length length = new Length(width);
            length.setUom(rs.getString(prefix + "width_uom"));
            trafficFacility.setWidth(length);
        }

        long urbanRoadAttributeId = rs.getLong(prefix + "urbanroadattribute_id");
        if (urbanRoadAttributeId != 0) {
            UrbanRoadAttribute urbanRoadAttribute = new UrbanRoadAttribute();
            urbanRoadAttributeExporter.doExport(urbanRoadAttribute, urbanRoadAttributeId);
            trafficFacility.setUrbanRoadAttribute(new UrbanRoadAttributeProperty(urbanRoadAttribute));
        }

        long urbanRapidTransitRailroadAttributeId = rs.getLong(prefix + "urbanrapidtransitrailroad_id");
        if (urbanRapidTransitRailroadAttributeId != 0) {
            UrbanRapidTransitRailroadAttribute urbanRapidTransitRailroadAttribute = new UrbanRapidTransitRailroadAttribute();
            urbanRapidTransitRailroadAttributeExporter.doExport(urbanRapidTransitRailroadAttribute, urbanRapidTransitRailroadAttributeId);
            trafficFacility.setUrbanRapidTransitRailroadAttribute(new UrbanRapidTransitRailroadAttributeProperty(urbanRapidTransitRailroadAttribute));
        }

        long parkingPlaceAttributeId = rs.getLong(prefix + "parkingplaceattribute_id");
        if (parkingPlaceAttributeId != 0) {
            ParkingPlaceAttribute parkingPlaceAttribute = new ParkingPlaceAttribute();
            parkingPlaceAttributeExporter.doExport(parkingPlaceAttribute, parkingPlaceAttributeId);
            trafficFacility.setParkingPlaceAttribute(new ParkingPlaceAttributeProperty(parkingPlaceAttribute));
        }

        long vehicleTerminalAttributeId = rs.getLong(prefix + "vehicleterminalattribute_id");
        if (vehicleTerminalAttributeId != 0) {
            VehicleTerminalAttribute vehicleTerminalAttribute = new VehicleTerminalAttribute();
            vehicleTerminalAttributeExporter.doExport(vehicleTerminalAttribute, vehicleTerminalAttributeId);
            trafficFacility.setVehicleTerminalAttribute(new VehicleTerminalAttributeProperty(vehicleTerminalAttribute));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
