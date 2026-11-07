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
import org.citygml4j.model.citygml.cityfurniture.CityFurniture;

import java.sql.*;

public class CityFurniturePropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final CityFurnitureDetailAttributeImporter cityFurnitureDetailAttributeImporter;
    private final CityFurnitureDataQualityAttributeImporter cityFurnitureDataQualityAttributeImporter;

    private int batchCounter;

    public CityFurniturePropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CITY_FURNITURE)) + " " +
                "(id, frnfacilityidattribute_id, frnfacilitytypeattribute_id, cityfurnituredetailattrib_id," +
                "cityfurnituredataqualitya_id) " +
                "values (?, ?, ?, ?, ?)");

        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        cityFurnitureDetailAttributeImporter = manager.getImporter(CityFurnitureDetailAttributeImporter.class);
        cityFurnitureDataQualityAttributeImporter = manager.getImporter(CityFurnitureDataQualityAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, CityFurniture parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long frnFacilityIdAttributeId = 0;
        if (properties.contains(FrnFacilityIdAttributePropertyElement.class)) {
            FrnFacilityIdAttributePropertyElement property = properties.getFirst(FrnFacilityIdAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                frnFacilityIdAttributeId = facilityIdAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (frnFacilityIdAttributeId != 0)
            ps.setLong(2, frnFacilityIdAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long frnFacilityTypeAttributeId = 0;
        if (properties.contains(FrnFacilityTypeAttributePropertyElement.class)) {
            FrnFacilityTypeAttributePropertyElement property = properties.getFirst(FrnFacilityTypeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                frnFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (frnFacilityTypeAttributeId != 0)
            ps.setLong(3, frnFacilityTypeAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long cityFurnitureDetailAttributeId = 0;
        if (properties.contains(CityFurnitureDetailAttributePropertyElement.class)) {
            CityFurnitureDetailAttributePropertyElement property = properties.getFirst(CityFurnitureDetailAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                cityFurnitureDetailAttributeId = cityFurnitureDetailAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (cityFurnitureDetailAttributeId != 0)
            ps.setLong(4, cityFurnitureDetailAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long cityFurnitureDataQualityAttributeId = 0;
        if (properties.contains(CityFurnitureDataQualityAttributePropertyElement.class)) {
            CityFurnitureDataQualityAttributePropertyElement property = properties.getFirst(CityFurnitureDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                cityFurnitureDataQualityAttributeId = cityFurnitureDataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (cityFurnitureDataQualityAttributeId != 0)
            ps.setLong(5, cityFurnitureDataQualityAttributeId);
        else
            ps.setNull(5, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.CITY_FURNITURE));

        if (properties.contains(FrnFacilityAttributePropertyElement.class)) {
            for (FrnFacilityAttributePropertyElement property : properties.getAll(FrnFacilityAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    facilityAttributeImporter.doImport(property.getValue().getObject(), parentId, "city_furniture_frnfacilit_id");
            }
        }

        if (properties.contains(FrnDmAttributePropertyElement.class)) {
            for (FrnDmAttributePropertyElement property : properties.getAll(FrnDmAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    dmAttributeImporter.doImport(property.getValue().getObject(), parentId, "city_furniture_frndmattri_id");
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
