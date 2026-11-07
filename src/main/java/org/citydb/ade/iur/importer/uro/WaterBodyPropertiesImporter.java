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
import org.citygml4j.model.citygml.waterbody.WaterBody;

import java.sql.*;

public class WaterBodyPropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final DisasterRiskAttributeImporter disasterRiskAttributeImporter;
    private final WaterBodyToFloodingRiskAttributeImporter waterBodyToFloodingRiskAttributeImporter;
    private final WaterBodyDetailAttributeImporter waterBodyDetailAttributeImporter;
    private final DataQualityAttributeImporter dataQualityAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final WaterBodyToDmAttributeImporter waterBodyToDmAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final WaterBodyToFacilityAttributeImporter waterBodyToFacilityAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;
    private final WaterBodyToKeyValuePairAttributeImporter waterBodyToKeyValuePairAttributeImporter;

    private int batchCounter;

    public WaterBodyPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.WATERBODY)) + " " +
                "(id, waterbodydetailattribute_id, wtrdataqualityattribute_id, wtrfacilityidattribute_id," +
                "wtrfacilitytypeattribute_id) " +
                "values (?, ?, ?, ?, ?)");

        disasterRiskAttributeImporter = manager.getImporter(DisasterRiskAttributeImporter.class);
        waterBodyToFloodingRiskAttributeImporter = manager.getImporter(WaterBodyToFloodingRiskAttributeImporter.class);
        waterBodyDetailAttributeImporter = manager.getImporter(WaterBodyDetailAttributeImporter.class);
        dataQualityAttributeImporter = manager.getImporter(DataQualityAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        waterBodyToDmAttributeImporter = manager.getImporter(WaterBodyToDmAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        waterBodyToFacilityAttributeImporter = manager.getImporter(WaterBodyToFacilityAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
        waterBodyToKeyValuePairAttributeImporter = manager.getImporter(WaterBodyToKeyValuePairAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, WaterBody parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long waterBodyDetailAttributeId = 0;
        if (properties.contains(WaterBodyDetailAttributePropertyElement.class)) {
            WaterBodyDetailAttributePropertyElement property = properties.getFirst(WaterBodyDetailAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                waterBodyDetailAttributeId = waterBodyDetailAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (waterBodyDetailAttributeId != 0)
            ps.setLong(2, waterBodyDetailAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long wtrDataQualityAttributeId = 0;
        if (properties.contains(WtrDataQualityAttributePropertyElement.class)) {
            WtrDataQualityAttributePropertyElement property = properties.getFirst(WtrDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                wtrDataQualityAttributeId = dataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (wtrDataQualityAttributeId != 0)
            ps.setLong(3, wtrDataQualityAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long wtrFacilityIdAttributeId = 0;
        if (properties.contains(WtrFacilityIdAttributePropertyElement.class)) {
            WtrFacilityIdAttributePropertyElement property = properties.getFirst(WtrFacilityIdAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                wtrFacilityIdAttributeId = facilityIdAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (wtrFacilityIdAttributeId != 0)
            ps.setLong(4, wtrFacilityIdAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long wtrFacilityTypeAttributeId = 0;
        if (properties.contains(WtrFacilityTypeAttributePropertyElement.class)) {
            WtrFacilityTypeAttributePropertyElement property = properties.getFirst(WtrFacilityTypeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                wtrFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (wtrFacilityTypeAttributeId != 0)
            ps.setLong(5, wtrFacilityTypeAttributeId);
        else
            ps.setNull(5, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.WATERBODY));

        if (properties.contains(FloodingRiskAttributePropertyElement.class)) {
            for (FloodingRiskAttributePropertyElement property : properties.getAll(FloodingRiskAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long floodingRiskAttributeId = disasterRiskAttributeImporter.doImport(property.getValue().getObject());
                    waterBodyToFloodingRiskAttributeImporter.doImport(floodingRiskAttributeId, parentId);
                }
            }
        }

        if (properties.contains(WtrDmAttributePropertyElement.class)) {
            for (WtrDmAttributePropertyElement property : properties.getAll(WtrDmAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long wtrDmAttributeId = dmAttributeImporter.doImport(property.getValue().getObject());
                    waterBodyToDmAttributeImporter.doImport(wtrDmAttributeId, parentId);
                }
            }
        }

        if (properties.contains(WtrFacilityAttributePropertyElement.class)) {
            for (WtrFacilityAttributePropertyElement property : properties.getAll(WtrFacilityAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long wtrFacilityAttributeId = facilityAttributeImporter.doImport(property.getValue().getObject());
                    waterBodyToFacilityAttributeImporter.doImport(wtrFacilityAttributeId, parentId);
                }
            }
        }

        if (properties.contains(WtrKeyValuePairAttributePropertyElement.class)) {
            for (WtrKeyValuePairAttributePropertyElement property : properties.getAll(WtrKeyValuePairAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long wtrKeyValuePairAttributeId = keyValuePairAttributeImporter.doImport(property.getValue().getObject());
                    waterBodyToKeyValuePairAttributeImporter.doImport(wtrKeyValuePairAttributeId, parentId);
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
