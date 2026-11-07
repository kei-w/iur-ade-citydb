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
import org.citygml4j.model.citygml.transportation.TrafficArea;

import java.sql.*;

public class TrafficAreaPropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final RailwayTrackAttributeImporter railwayTrackAttributeImporter;
    private final TrafficAreaStructureAttributeImporter trafficAreaStructureAttributeImporter;

    private int batchCounter;

    public TrafficAreaPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TRAFFIC_AREA)) + " " +
                "(id, railwaytrackattribute_id, trafficareastructureattri_id) " +
                "values (?, ?, ?)");

        railwayTrackAttributeImporter = manager.getImporter(RailwayTrackAttributeImporter.class);
        trafficAreaStructureAttributeImporter = manager.getImporter(TrafficAreaStructureAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, TrafficArea parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long railwayTrackAttributeId = 0;
        if (properties.contains(RailwayTrackAttributePropertyElement.class)) {
            RailwayTrackAttributePropertyElement property = properties.getFirst(RailwayTrackAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                railwayTrackAttributeId = railwayTrackAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (railwayTrackAttributeId != 0)
            ps.setLong(2, railwayTrackAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long trafficAreaStructureAttributeId = 0;
        if (properties.contains(TrafficAreaStructureAttributePropertyElement.class)) {
            TrafficAreaStructureAttributePropertyElement property = properties.getFirst(TrafficAreaStructureAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                trafficAreaStructureAttributeId = trafficAreaStructureAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (trafficAreaStructureAttributeId != 0)
            ps.setLong(3, trafficAreaStructureAttributeId);
        else
            ps.setNull(3, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TRAFFIC_AREA));
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
