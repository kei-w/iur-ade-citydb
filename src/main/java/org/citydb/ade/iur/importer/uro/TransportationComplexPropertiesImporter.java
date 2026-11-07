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
import org.citydb.core.ade.importer.ADEPropertyCollection;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.transportation.TransportationComplex;

import java.sql.*;

public class TransportationComplexPropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final RailwayRouteAttributeImporter railwayRouteAttributeImporter;
    private final RoadTypeImporter roadTypeImporter;
    private final RoadStructureAttributeImporter roadStructureAttributeImporter;
    private final SquareUrbanPlanAttributeImporter squareUrbanPlanAttributeImporter;
    private final TrackAttributeImporter trackAttributeImporter;
    private final TrafficVolumeAttributeImporter trafficVolumeAttributeImporter;
    private final DataQualityAttributeImporter dataQualityAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final TransportationComplexToDmAttributeImporter transportationComplexToDmAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final TransportationComplexToFacilityAttributeImporter transportationComplexToFacilityAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;
    private final TransportationComplexToKeyValuePairAttributeImporter transportationComplexToKeyValuePairAttributeImporter;
    private final TrafficObjectUsecaseAttributeImporter trafficObjectUsecaseAttributeImporter;

    private int batchCounter;

    public TransportationComplexPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRANSPORTATION_COMPLEX)) + " " +
                "(id, railwayrouteattribute_id, roadstatus_id, roadstructureattribute_id," +
                "squareurbanplanattribute_id, trackattribute_id, trafficvolumeattribute_id," +
                "trandataqualityattribute_id, tranfacilityidattribute_id, tranfacilitytypeattribute_id," +
                "tranusecaseattribute_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        railwayRouteAttributeImporter = manager.getImporter(RailwayRouteAttributeImporter.class);
        roadTypeImporter = manager.getImporter(RoadTypeImporter.class);
        roadStructureAttributeImporter = manager.getImporter(RoadStructureAttributeImporter.class);
        squareUrbanPlanAttributeImporter = manager.getImporter(SquareUrbanPlanAttributeImporter.class);
        trackAttributeImporter = manager.getImporter(TrackAttributeImporter.class);
        trafficVolumeAttributeImporter = manager.getImporter(TrafficVolumeAttributeImporter.class);
        dataQualityAttributeImporter = manager.getImporter(DataQualityAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        transportationComplexToDmAttributeImporter = manager.getImporter(TransportationComplexToDmAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        transportationComplexToFacilityAttributeImporter = manager.getImporter(TransportationComplexToFacilityAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
        transportationComplexToKeyValuePairAttributeImporter = manager.getImporter(TransportationComplexToKeyValuePairAttributeImporter.class);
        trafficObjectUsecaseAttributeImporter = manager.getImporter(TrafficObjectUsecaseAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, TransportationComplex parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long railwayRouteAttributeId = 0;
        if (properties.contains(RailwayRouteAttributePropertyElement.class)) {
            RailwayRouteAttributePropertyElement property = properties.getFirst(RailwayRouteAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                railwayRouteAttributeId = railwayRouteAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (railwayRouteAttributeId != 0)
            ps.setLong(2, railwayRouteAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long roadStatusId = 0;
        if (properties.contains(RoadStatusPropertyElement.class)) {
            RoadStatusPropertyElement property = properties.getFirst(RoadStatusPropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                roadStatusId = roadTypeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (roadStatusId != 0)
            ps.setLong(3, roadStatusId);
        else
            ps.setNull(3, Types.NULL);

        long roadStructureAttributeId = 0;
        if (properties.contains(RoadStructureAttributePropertyElement.class)) {
            RoadStructureAttributePropertyElement property = properties.getFirst(RoadStructureAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                roadStructureAttributeId = roadStructureAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (roadStructureAttributeId != 0)
            ps.setLong(4, roadStructureAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long squareUrbanPlanAttributeId = 0;
        if (properties.contains(SquareUrbanPlanAttributePropertyElement.class)) {
            SquareUrbanPlanAttributePropertyElement property = properties.getFirst(SquareUrbanPlanAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                squareUrbanPlanAttributeId = squareUrbanPlanAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (squareUrbanPlanAttributeId != 0)
            ps.setLong(5, squareUrbanPlanAttributeId);
        else
            ps.setNull(5, Types.NULL);

        long trackAttributeId = 0;
        if (properties.contains(TrackAttributePropertyElement.class)) {
            TrackAttributePropertyElement property = properties.getFirst(TrackAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                trackAttributeId = trackAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (trackAttributeId != 0)
            ps.setLong(6, trackAttributeId);
        else
            ps.setNull(6, Types.NULL);

        long trafficVolumeAttributeId = 0;
        if (properties.contains(TrafficVolumeAttributePropertyElement.class)) {
            TrafficVolumeAttributePropertyElement property = properties.getFirst(TrafficVolumeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                trafficVolumeAttributeId = trafficVolumeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (trafficVolumeAttributeId != 0)
            ps.setLong(7, trafficVolumeAttributeId);
        else
            ps.setNull(7, Types.NULL);

        long tranDataQualityAttributeId = 0;
        if (properties.contains(TranDataQualityAttributePropertyElement.class)) {
            TranDataQualityAttributePropertyElement property = properties.getFirst(TranDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tranDataQualityAttributeId = dataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tranDataQualityAttributeId != 0)
            ps.setLong(8, tranDataQualityAttributeId);
        else
            ps.setNull(8, Types.NULL);

        long tranFacilityIdAttributeId = 0;
        if (properties.contains(TranFacilityIdAttributePropertyElement.class)) {
            TranFacilityIdAttributePropertyElement property = properties.getFirst(TranFacilityIdAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tranFacilityIdAttributeId = facilityIdAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tranFacilityIdAttributeId != 0)
            ps.setLong(9, tranFacilityIdAttributeId);
        else
            ps.setNull(9, Types.NULL);

        long tranFacilityTypeAttributeId = 0;
        if (properties.contains(TranFacilityTypeAttributePropertyElement.class)) {
            TranFacilityTypeAttributePropertyElement property = properties.getFirst(TranFacilityTypeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tranFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tranFacilityTypeAttributeId != 0)
            ps.setLong(10, tranFacilityTypeAttributeId);
        else
            ps.setNull(10, Types.NULL);

        long tranUsecaseAttributeId = 0;
        if (properties.contains(TranUsecaseAttributePropertyElement.class)) {
            TranUsecaseAttributePropertyElement property = properties.getFirst(TranUsecaseAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tranUsecaseAttributeId = trafficObjectUsecaseAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tranUsecaseAttributeId != 0)
            ps.setLong(11, tranUsecaseAttributeId);
        else
            ps.setNull(11, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TRANSPORTATION_COMPLEX));

        if (properties.contains(TranDmAttributePropertyElement.class)) {
            for (TranDmAttributePropertyElement property : properties.getAll(TranDmAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long tranDmAttributeId = dmAttributeImporter.doImport(property.getValue().getObject());
                    transportationComplexToDmAttributeImporter.doImport(tranDmAttributeId, parentId);
                }
            }
        }

        if (properties.contains(TranFacilityAttributePropertyElement.class)) {
            for (TranFacilityAttributePropertyElement property : properties.getAll(TranFacilityAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long tranFacilityAttributeId = facilityAttributeImporter.doImport(property.getValue().getObject());
                    transportationComplexToFacilityAttributeImporter.doImport(tranFacilityAttributeId, parentId);
                }
            }
        }

        if (properties.contains(TranKeyValuePairAttributePropertyElement.class)) {
            for (TranKeyValuePairAttributePropertyElement property : properties.getAll(TranKeyValuePairAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long tranKeyValuePairAttributeId = keyValuePairAttributeImporter.doImport(property.getValue().getObject());
                    transportationComplexToKeyValuePairAttributeImporter.doImport(tranKeyValuePairAttributeId, parentId);
                }
            }
        }
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
