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
import org.citygml4j.model.citygml.landuse.LandUse;

import java.sql.*;

public class LandUsePropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final LandUseDetailAttributeImporter landUseDetailAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final DataQualityAttributeImporter dataQualityAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;

    private int batchCounter;

    public LandUsePropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.LAND_USE)) + " " +
                "(id, landusedetailattribute_id, lusefacilityidattribute_id, lusefacilitytypeattribute_id," +
                "lusedataqualityattribute_id) " +
                "values (?, ?, ?, ?, ?)");

        landUseDetailAttributeImporter = manager.getImporter(LandUseDetailAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        dataQualityAttributeImporter = manager.getImporter(DataQualityAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, LandUse parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long landUseDetailAttributeId = 0;
        if (properties.contains(LandUseDetailAttributePropertyElement.class)) {
            LandUseDetailAttributePropertyElement property = properties.getFirst(LandUseDetailAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                landUseDetailAttributeId = landUseDetailAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (landUseDetailAttributeId != 0)
            ps.setLong(2, landUseDetailAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long luseFacilityIdAttributeId = 0;
        if (properties.contains(LuseFacilityIdAttributePropertyElement.class)) {
            LuseFacilityIdAttributePropertyElement property = properties.getFirst(LuseFacilityIdAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                luseFacilityIdAttributeId = facilityIdAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (luseFacilityIdAttributeId != 0)
            ps.setLong(3, luseFacilityIdAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long luseFacilityTypeAttributeId = 0;
        if (properties.contains(LuseFacilityTypeAttributePropertyElement.class)) {
            LuseFacilityTypeAttributePropertyElement property = properties.getFirst(LuseFacilityTypeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                luseFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (luseFacilityTypeAttributeId != 0)
            ps.setLong(4, luseFacilityTypeAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long luseDataQualityAttributeId = 0;
        if (properties.contains(LuseDataQualityAttributePropertyElement.class)) {
            LuseDataQualityAttributePropertyElement property = properties.getFirst(LuseDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                luseDataQualityAttributeId = dataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (luseDataQualityAttributeId != 0)
            ps.setLong(5, luseDataQualityAttributeId);
        else
            ps.setNull(5, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.LAND_USE));

        if (properties.contains(LuseFacilityAttributePropertyElement.class)) {
            for (LuseFacilityAttributePropertyElement property : properties.getAll(LuseFacilityAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    facilityAttributeImporter.doImport(property.getValue().getObject(), parentId, "land_use_lusefacilityattr_id");
            }
        }

        if (properties.contains(LuseKeyValuePairAttributePropertyElement.class)) {
            for (LuseKeyValuePairAttributePropertyElement property : properties.getAll(LuseKeyValuePairAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    keyValuePairAttributeImporter.doImport(property.getValue().getObject(), parentId, "land_use_lusekeyvaluepair_id");
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
