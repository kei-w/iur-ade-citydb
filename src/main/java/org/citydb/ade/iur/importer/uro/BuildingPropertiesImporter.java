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

import java.sql.*;

public class BuildingPropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final DataQualityAttributeImporter dataQualityAttributeImporter;
    private final DisasterRiskAttributeImporter disasterRiskAttributeImporter;
    private final BuildingToDisasterRiskAttributeImporter buildingToDisasterRiskAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final BuildingToDmAttributeImporter buildingToDmAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final BuildingToFacilityAttributeImporter buildingToFacilityAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;
    private final BuildingToKeyValuePairAttributeImporter buildingToKeyValuePairAttributeImporter;
    private final RealEstateIDAttributeImporter realEstateIDAttributeImporter;
    private final BuildingUsecaseAttributeImporter buildingUsecaseAttributeImporter;
    private final BuildingDetailAttributeImporter buildingDetailAttributeImporter;
    private final BuildingIDAttributeImporter buildingIDAttributeImporter;
    private final IfcAttributeImporter ifcAttributeImporter;
    private final IndoorAttributeImporter indoorAttributeImporter;
    private final LargeCustomerFacilityAttributeImporter largeCustomerFacilityAttributeImporter;

    private int batchCounter;

    public BuildingPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDING)) + " " +
                "(id, bldgdataqualityattribute_id, bldgfacilityidattribute_id, bldgfacilitytypeattribute_id," +
                "bldgrealestateidattribute_id, bldgusecaseattribute_id, buildingdetailattribute_id," +
                "buildingidattribute_id, ifcbuildingattribute_id, indoorbuildingattribute_id," +
                "largecustomerfacilityattr_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        dataQualityAttributeImporter = manager.getImporter(DataQualityAttributeImporter.class);
        disasterRiskAttributeImporter = manager.getImporter(DisasterRiskAttributeImporter.class);
        buildingToDisasterRiskAttributeImporter = manager.getImporter(BuildingToDisasterRiskAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        buildingToDmAttributeImporter = manager.getImporter(BuildingToDmAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        buildingToFacilityAttributeImporter = manager.getImporter(BuildingToFacilityAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
        buildingToKeyValuePairAttributeImporter = manager.getImporter(BuildingToKeyValuePairAttributeImporter.class);
        realEstateIDAttributeImporter = manager.getImporter(RealEstateIDAttributeImporter.class);
        buildingUsecaseAttributeImporter = manager.getImporter(BuildingUsecaseAttributeImporter.class);
        buildingDetailAttributeImporter = manager.getImporter(BuildingDetailAttributeImporter.class);
        buildingIDAttributeImporter = manager.getImporter(BuildingIDAttributeImporter.class);
        ifcAttributeImporter = manager.getImporter(IfcAttributeImporter.class);
        indoorAttributeImporter = manager.getImporter(IndoorAttributeImporter.class);
        largeCustomerFacilityAttributeImporter = manager.getImporter(LargeCustomerFacilityAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, AbstractBuilding parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long bldgDataQualityAttributeId = 0;
        if (properties.contains(BldgDataQualityAttributePropertyElement.class)) {
            BldgDataQualityAttributePropertyElement property = properties.getFirst(BldgDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bldgDataQualityAttributeId = dataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bldgDataQualityAttributeId != 0)
            ps.setLong(2, bldgDataQualityAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long bldgFacilityIdAttributeId = 0;
        if (properties.contains(BldgFacilityIdAttributePropertyElement.class)) {
            BldgFacilityIdAttributePropertyElement property = properties.getFirst(BldgFacilityIdAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bldgFacilityIdAttributeId = facilityIdAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bldgFacilityIdAttributeId != 0)
            ps.setLong(3, bldgFacilityIdAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long bldgFacilityTypeAttributeId = 0;
        if (properties.contains(BldgFacilityTypeAttributePropertyElement.class)) {
            BldgFacilityTypeAttributePropertyElement property = properties.getFirst(BldgFacilityTypeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bldgFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bldgFacilityTypeAttributeId != 0)
            ps.setLong(4, bldgFacilityTypeAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long bldgRealEstateIDAttributeId = 0;
        if (properties.contains(BldgRealEstateIDAttributePropertyElement.class)) {
            BldgRealEstateIDAttributePropertyElement property = properties.getFirst(BldgRealEstateIDAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bldgRealEstateIDAttributeId = realEstateIDAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bldgRealEstateIDAttributeId != 0)
            ps.setLong(5, bldgRealEstateIDAttributeId);
        else
            ps.setNull(5, Types.NULL);

        long bldgUsecaseAttributeId = 0;
        if (properties.contains(BldgUsecaseAttributePropertyElement.class)) {
            BldgUsecaseAttributePropertyElement property = properties.getFirst(BldgUsecaseAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bldgUsecaseAttributeId = buildingUsecaseAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bldgUsecaseAttributeId != 0)
            ps.setLong(6, bldgUsecaseAttributeId);
        else
            ps.setNull(6, Types.NULL);

        long buildingDetailAttributeId = 0;
        if (properties.contains(BuildingDetailAttributePropertyElement.class)) {
            BuildingDetailAttributePropertyElement property = properties.getFirst(BuildingDetailAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                buildingDetailAttributeId = buildingDetailAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (buildingDetailAttributeId != 0)
            ps.setLong(7, buildingDetailAttributeId);
        else
            ps.setNull(7, Types.NULL);

        long buildingIDAttributeId = 0;
        if (properties.contains(BuildingIDAttributePropertyElement.class)) {
            BuildingIDAttributePropertyElement property = properties.getFirst(BuildingIDAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                buildingIDAttributeId = buildingIDAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (buildingIDAttributeId != 0)
            ps.setLong(8, buildingIDAttributeId);
        else
            ps.setNull(8, Types.NULL);

        long ifcBuildingAttributeId = 0;
        if (properties.contains(IfcBuildingAttributePropertyElement.class)) {
            IfcBuildingAttributePropertyElement property = properties.getFirst(IfcBuildingAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                ifcBuildingAttributeId = ifcAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (ifcBuildingAttributeId != 0)
            ps.setLong(9, ifcBuildingAttributeId);
        else
            ps.setNull(9, Types.NULL);

        long indoorBuildingAttributeId = 0;
        if (properties.contains(IndoorBuildingAttributePropertyElement.class)) {
            IndoorBuildingAttributePropertyElement property = properties.getFirst(IndoorBuildingAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                indoorBuildingAttributeId = indoorAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (indoorBuildingAttributeId != 0)
            ps.setLong(10, indoorBuildingAttributeId);
        else
            ps.setNull(10, Types.NULL);

        long largeCustomerFacilityAttributeId = 0;
        if (properties.contains(LargeCustomerFacilityAttributePropertyElement.class)) {
            LargeCustomerFacilityAttributePropertyElement property = properties.getFirst(LargeCustomerFacilityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                largeCustomerFacilityAttributeId = largeCustomerFacilityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (largeCustomerFacilityAttributeId != 0)
            ps.setLong(11, largeCustomerFacilityAttributeId);
        else
            ps.setNull(11, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDING));

        if (properties.contains(BldgDisasterRiskAttributePropertyElement.class)) {
            for (BldgDisasterRiskAttributePropertyElement property : properties.getAll(BldgDisasterRiskAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long bldgDisasterRiskAttributeId = disasterRiskAttributeImporter.doImport(property.getValue().getObject());
                    buildingToDisasterRiskAttributeImporter.doImport(bldgDisasterRiskAttributeId, parentId);
                }
            }
        }

        if (properties.contains(BldgDmAttributePropertyElement.class)) {
            for (BldgDmAttributePropertyElement property : properties.getAll(BldgDmAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long bldgDmAttributeId = dmAttributeImporter.doImport(property.getValue().getObject());
                    buildingToDmAttributeImporter.doImport(bldgDmAttributeId, parentId);
                }
            }
        }

        if (properties.contains(BldgFacilityAttributePropertyElement.class)) {
            for (BldgFacilityAttributePropertyElement property : properties.getAll(BldgFacilityAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long bldgFacilityAttributeId = facilityAttributeImporter.doImport(property.getValue().getObject());
                    buildingToFacilityAttributeImporter.doImport(bldgFacilityAttributeId, parentId);
                }
            }
        }

        if (properties.contains(BldgKeyValuePairAttributePropertyElement.class)) {
            for (BldgKeyValuePairAttributePropertyElement property : properties.getAll(BldgKeyValuePairAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject()) {
                    long bldgKeyValuePairAttributeId = keyValuePairAttributeImporter.doImport(property.getValue().getObject());
                    buildingToKeyValuePairAttributeImporter.doImport(bldgKeyValuePairAttributeId, parentId);
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
