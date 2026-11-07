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
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.transportation.Railway;
import org.citygml4j.model.citygml.transportation.Road;
import org.citygml4j.model.citygml.transportation.Square;
import org.citygml4j.model.citygml.transportation.Track;
import org.citygml4j.model.citygml.transportation.TransportationComplex;

import java.sql.*;

public class TransportationComplexPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final TransportationDataQualityAttributeExporter transportationDataQualityAttributeExporter;
    private final RailwayRouteAttributeExporter railwayRouteAttributeExporter;
    private final TrackAttributeExporter trackAttributeExporter;
    private final SquareUrbanPlanAttributeExporter squareUrbanPlanAttributeExporter;
    private final RoadTypeExporter roadTypeExporter;
    private final TrafficVolumeAttributeExporter trafficVolumeAttributeExporter;
    private final RoadStructureAttributeExporter roadStructureAttributeExporter;

    public TransportationComplexPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.TRANSPORTATION_COMPLEX);
        module = UrbanObjectModule.v3_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("tranfacilityidattribute_id", "tranfacilitytypeattribute_id", "trandataqualityattribute_id",
                        "railwayrouteattribute_id", "trackattribute_id", "squareurbanplanattribute_id", "roadstatus_id",
                        "trafficvolumeattribute_id", "roadstructureattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        transportationDataQualityAttributeExporter = manager.getExporter(TransportationDataQualityAttributeExporter.class);
        railwayRouteAttributeExporter = manager.getExporter(RailwayRouteAttributeExporter.class);
        trackAttributeExporter = manager.getExporter(TrackAttributeExporter.class);
        squareUrbanPlanAttributeExporter = manager.getExporter(SquareUrbanPlanAttributeExporter.class);
        roadTypeExporter = manager.getExporter(RoadTypeExporter.class);
        trafficVolumeAttributeExporter = manager.getExporter(TrafficVolumeAttributeExporter.class);
        roadStructureAttributeExporter = manager.getExporter(RoadStructureAttributeExporter.class);
    }

    public void doExport(TransportationComplex parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long tranFacilityIdAttributeId = rs.getLong("tranfacilityidattribute_id");
                if (tranFacilityIdAttributeId != 0 && projectionFilter.containsProperty("tranFacilityIdAttribute", module)) {
                    FacilityIdAttribute tranFacilityIdAttribute = facilityIdAttributeExporter.doExport(tranFacilityIdAttributeId);
                    if (tranFacilityIdAttribute != null) {
                        TranFacilityIdAttributePropertyElement property = new TranFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(tranFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfTransportationComplex(property);
                    }
                }

                long tranFacilityTypeAttributeId = rs.getLong("tranfacilitytypeattribute_id");
                if (tranFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("tranFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute tranFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(tranFacilityTypeAttribute, tranFacilityTypeAttributeId);

                    TranFacilityTypeAttributePropertyElement property = new TranFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(tranFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfTransportationComplex(property);
                }

                if (projectionFilter.containsProperty("tranFacilityAttribute", module)) {
                    for (FacilityAttribute tranFacilityAttribute : facilityAttributeExporter.doExport(parentId, "transpor_complex_tranfaci_id")) {
                        TranFacilityAttributePropertyElement property = new TranFacilityAttributePropertyElement(new FacilityAttributeProperty(tranFacilityAttribute));
                        parent.addGenericApplicationPropertyOfTransportationComplex(property);
                    }
                }

                long tranDataQualityAttributeId = rs.getLong("trandataqualityattribute_id");
                if (tranDataQualityAttributeId != 0 && projectionFilter.containsProperty("tranDataQualityAttribute", module)) {
                    TransportationDataQualityAttribute tranDataQualityAttribute = new TransportationDataQualityAttribute();
                    transportationDataQualityAttributeExporter.doExport(tranDataQualityAttribute, tranDataQualityAttributeId);

                    TranDataQualityAttributePropertyElement property = new TranDataQualityAttributePropertyElement(new TransportationDataQualityAttributeProperty(tranDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfTransportationComplex(property);
                }

                long railwayRouteAttributeId = rs.getLong("railwayrouteattribute_id");
                if (railwayRouteAttributeId != 0 && parent instanceof Railway && projectionFilter.containsProperty("railwayRouteAttribute", module)) {
                    RailwayRouteAttribute railwayRouteAttribute = new RailwayRouteAttribute();
                    railwayRouteAttributeExporter.doExport(railwayRouteAttribute, railwayRouteAttributeId);

                    RailwayRouteAttributePropertyElement property = new RailwayRouteAttributePropertyElement(new RailwayRouteAttributeProperty(railwayRouteAttribute));
                    ((Railway) parent).addGenericApplicationPropertyOfRailway(property);
                }

                long trackAttributeId = rs.getLong("trackattribute_id");
                if (trackAttributeId != 0 && parent instanceof Track && projectionFilter.containsProperty("trackAttribute", module)) {
                    TrackAttribute trackAttribute = new TrackAttribute();
                    trackAttributeExporter.doExport(trackAttribute, trackAttributeId);

                    TrackAttributePropertyElement property = new TrackAttributePropertyElement(new TrackAttributeProperty(trackAttribute));
                    ((Track) parent).addGenericApplicationPropertyOfTrack(property);
                }

                long squareUrbanPlanAttributeId = rs.getLong("squareurbanplanattribute_id");
                if (squareUrbanPlanAttributeId != 0 && parent instanceof Square && projectionFilter.containsProperty("squareUrbanPlanAttribute", module)) {
                    SquareUrbanPlanAttribute squareUrbanPlanAttribute = squareUrbanPlanAttributeExporter.doExport(squareUrbanPlanAttributeId);
                    if (squareUrbanPlanAttribute != null) {
                        SquareUrbanPlanAttributePropertyElement property = new SquareUrbanPlanAttributePropertyElement(new SquareUrbanPlanAttributeProperty(squareUrbanPlanAttribute));
                        ((Square) parent).addGenericApplicationPropertyOfSquare(property);
                    }
                }

                long roadStatusId = rs.getLong("roadstatus_id");
                if (roadStatusId != 0 && parent instanceof Road && projectionFilter.containsProperty("roadStatus", module)) {
                    RoadType roadStatus = new RoadType();
                    roadTypeExporter.doExport(roadStatus, roadStatusId);

                    RoadStatusPropertyElement property = new RoadStatusPropertyElement(new RoadTypeProperty(roadStatus));
                    ((Road) parent).addGenericApplicationPropertyOfRoad(property);
                }

                long trafficVolumeAttributeId = rs.getLong("trafficvolumeattribute_id");
                if (trafficVolumeAttributeId != 0 && parent instanceof Road && projectionFilter.containsProperty("trafficVolumeAttribute", module)) {
                    TrafficVolumeAttribute trafficVolumeAttribute = new TrafficVolumeAttribute();
                    trafficVolumeAttributeExporter.doExport(trafficVolumeAttribute, trafficVolumeAttributeId);

                    TrafficVolumeAttributePropertyElement property = new TrafficVolumeAttributePropertyElement(new TrafficVolumeAttributeProperty(trafficVolumeAttribute));
                    ((Road) parent).addGenericApplicationPropertyOfRoad(property);
                }

                long roadStructureAttributeId = rs.getLong("roadstructureattribute_id");
                if (roadStructureAttributeId != 0 && parent instanceof Road && projectionFilter.containsProperty("roadStructureAttribute", module)) {
                    RoadStructureAttribute roadStructureAttribute = new RoadStructureAttribute();
                    roadStructureAttributeExporter.doExport(roadStructureAttribute, roadStructureAttributeId);

                    RoadStructureAttributePropertyElement property = new RoadStructureAttributePropertyElement(new RoadStructureAttributeProperty(roadStructureAttribute));
                    ((Road) parent).addGenericApplicationPropertyOfRoad(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
