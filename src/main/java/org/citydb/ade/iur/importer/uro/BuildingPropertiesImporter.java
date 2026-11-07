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
import org.citygml4j.model.citygml.building.AbstractBuilding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BuildingPropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final BuildingDataQualityAttributeImporter buildingDataQualityAttributeImporter;
    private final BuildingDetailAttributeImporter buildingDetailAttributeImporter;
    private final BuildingIDAttributeImporter buildingIDAttributeImporter;
    private final LargeCustomerFacilityAttributeImporter largeCustomerFacilityAttributeImporter;
    private final BuildingDisasterRiskAttributeImporter buildingDisasterRiskAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;

    private int batchCounter;

    public BuildingPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDING)) + " " +
                "(id, buildingdataqualityattrib_id, buildingdetailattribute_id, buildingidattribute_id, largecustomerfacilityattr_id) " +
                "values (?, ?, ?, ?, ?)");

        buildingDataQualityAttributeImporter = manager.getImporter(BuildingDataQualityAttributeImporter.class);
        buildingDetailAttributeImporter = manager.getImporter(BuildingDetailAttributeImporter.class);
        buildingIDAttributeImporter = manager.getImporter(BuildingIDAttributeImporter.class);
        largeCustomerFacilityAttributeImporter = manager.getImporter(LargeCustomerFacilityAttributeImporter.class);
        buildingDisasterRiskAttributeImporter = manager.getImporter(BuildingDisasterRiskAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, AbstractBuilding parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long buildingDataQualityAttributeId = 0;
        if (properties.contains(BuildingDataQualityAttributePropertyElement.class)) {
            BuildingDataQualityAttributePropertyElement property = properties.getFirst(BuildingDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                buildingDataQualityAttributeId = buildingDataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (buildingDataQualityAttributeId != 0)
            ps.setLong(2, buildingDataQualityAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long buildingDetailAttributeId = 0;
        if (properties.contains(BuildingDetailAttributePropertyElement.class)) {
            BuildingDetailAttributePropertyElement property = properties.getFirst(BuildingDetailAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                buildingDetailAttributeId = buildingDetailAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (buildingDetailAttributeId != 0)
            ps.setLong(3, buildingDetailAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long buildingIDAttributeId = 0;
        if (properties.contains(BuildingIDAttributePropertyElement.class)) {
            BuildingIDAttributePropertyElement property = properties.getFirst(BuildingIDAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                buildingIDAttributeId = buildingIDAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (buildingIDAttributeId != 0)
            ps.setLong(4, buildingIDAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long largeCustomerFacilityAttributeId = 0;
        if (properties.contains(LargeCustomerFacilityAttributePropertyElement.class)) {
            LargeCustomerFacilityAttributePropertyElement property = properties.getFirst(LargeCustomerFacilityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                largeCustomerFacilityAttributeId = largeCustomerFacilityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (largeCustomerFacilityAttributeId != 0)
            ps.setLong(5, largeCustomerFacilityAttributeId);
        else
            ps.setNull(5, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDING));

        if (properties.contains(BuildingDisasterRiskAttributePropertyElement.class)) {
            for (BuildingDisasterRiskAttributePropertyElement property : properties.getAll(BuildingDisasterRiskAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    buildingDisasterRiskAttributeImporter.doImport(property.getValue().getObject(), parentId);
            }
        }

        if (properties.contains(KeyValuePairAttributePropertyElement.class)) {
            for (KeyValuePairAttributePropertyElement property : properties.getAll(KeyValuePairAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    keyValuePairAttributeImporter.doImport(property.getValue().getObject(), parentId);
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
