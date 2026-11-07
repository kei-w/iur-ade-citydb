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
import org.citygml4j.model.citygml.building.AbstractBuilding;

import java.sql.*;

public class BuildingPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final BuildingIDAttributeExporter buildingIDAttributeExporter;
    private final BuildingDetailAttributeExporter buildingDetailAttributeExporter;
    private final LargeCustomerFacilityAttributeExporter largeCustomerFacilityAttributeExporter;
    private final DisasterRiskAttributeExporter disasterRiskAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;
    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final RealEstateIDAttributeExporter realEstateIDAttributeExporter;
    private final BuildingUsecaseAttributeExporter buildingUsecaseAttributeExporter;

    public BuildingPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BUILDING);
        module = UrbanObjectModule.v3_1.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("buildingidattribute_id", "buildingdetailattribute_id", "largecustomerfacilityattr_id",
                        "bldgdataqualityattribute_id", "bldgfacilityidattribute_id", "bldgfacilitytypeattribute_id",
                        "bldgrealestateidattribute_id", "bldgusecaseattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        buildingIDAttributeExporter = manager.getExporter(BuildingIDAttributeExporter.class);
        buildingDetailAttributeExporter = manager.getExporter(BuildingDetailAttributeExporter.class);
        largeCustomerFacilityAttributeExporter = manager.getExporter(LargeCustomerFacilityAttributeExporter.class);
        disasterRiskAttributeExporter = manager.getExporter(DisasterRiskAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        realEstateIDAttributeExporter = manager.getExporter(RealEstateIDAttributeExporter.class);
        buildingUsecaseAttributeExporter = manager.getExporter(BuildingUsecaseAttributeExporter.class);
    }

    public void doExport(AbstractBuilding parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long buildingIDAttributeId = rs.getLong("buildingidattribute_id");
                if (buildingIDAttributeId != 0 && projectionFilter.containsProperty("buildingIDAttribute", module)) {
                    BuildingIDAttribute buildingIDAttribute = new BuildingIDAttribute();
                    buildingIDAttributeExporter.doExport(buildingIDAttribute, buildingIDAttributeId);

                    BuildingIDAttributePropertyElement property = new BuildingIDAttributePropertyElement(new BuildingIDAttributeProperty(buildingIDAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                long buildingDetailAttributeId = rs.getLong("buildingdetailattribute_id");
                if (buildingDetailAttributeId != 0 && projectionFilter.containsProperty("buildingDetailAttribute", module)) {
                    BuildingDetailAttribute buildingDetailAttribute = new BuildingDetailAttribute();
                    buildingDetailAttributeExporter.doExport(buildingDetailAttribute, buildingDetailAttributeId);

                    BuildingDetailAttributePropertyElement property = new BuildingDetailAttributePropertyElement(new BuildingDetailAttributeProperty(buildingDetailAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                long largeCustomerFacilityAttributeId = rs.getLong("largecustomerfacilityattr_id");
                if (largeCustomerFacilityAttributeId != 0 && projectionFilter.containsProperty("largeCustomerFacilityAttribute", module)) {
                    LargeCustomerFacilityAttribute largeCustomerFacilityAttribute = new LargeCustomerFacilityAttribute();
                    largeCustomerFacilityAttributeExporter.doExport(largeCustomerFacilityAttribute, largeCustomerFacilityAttributeId);

                    LargeCustomerFacilityAttributePropertyElement property = new LargeCustomerFacilityAttributePropertyElement(new LargeCustomerFacilityAttributeProperty(largeCustomerFacilityAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                if (projectionFilter.containsProperty("bldgDisasterRiskAttribute", module)) {
                    for (DisasterRiskAttribute bldgDisasterRiskAttribute : disasterRiskAttributeExporter.doExport(parentId, "building_bldgdisasterrisk_id")) {
                        BldgDisasterRiskAttributePropertyElement property = new BldgDisasterRiskAttributePropertyElement(new DisasterRiskAttributeProperty(bldgDisasterRiskAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                if (projectionFilter.containsProperty("bldgKeyValuePairAttribute", module)) {
                    for (KeyValuePairAttribute bldgKeyValuePairAttribute : keyValuePairAttributeExporter.doExport(parentId, "building_bldgkeyvaluepair_id")) {
                        BldgKeyValuePairAttributePropertyElement property = new BldgKeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(bldgKeyValuePairAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                long bldgDataQualityAttributeId = rs.getLong("bldgdataqualityattribute_id");
                if (bldgDataQualityAttributeId != 0 && projectionFilter.containsProperty("bldgDataQualityAttribute", module)) {
                    DataQualityAttribute bldgDataQualityAttribute = new DataQualityAttribute();
                    dataQualityAttributeExporter.doExport(bldgDataQualityAttribute, bldgDataQualityAttributeId);

                    BldgDataQualityAttributePropertyElement property = new BldgDataQualityAttributePropertyElement(new DataQualityAttributeProperty(bldgDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                long bldgFacilityIdAttributeId = rs.getLong("bldgfacilityidattribute_id");
                if (bldgFacilityIdAttributeId != 0 && projectionFilter.containsProperty("bldgFacilityIdAttribute", module)) {
                    FacilityIdAttribute bldgFacilityIdAttribute = facilityIdAttributeExporter.doExport(bldgFacilityIdAttributeId);
                    if (bldgFacilityIdAttribute != null) {
                        BldgFacilityIdAttributePropertyElement property = new BldgFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(bldgFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                long bldgFacilityTypeAttributeId = rs.getLong("bldgfacilitytypeattribute_id");
                if (bldgFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("bldgFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute bldgFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(bldgFacilityTypeAttribute, bldgFacilityTypeAttributeId);

                    BldgFacilityTypeAttributePropertyElement property = new BldgFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(bldgFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                long bldgRealEstateIDAttributeId = rs.getLong("bldgrealestateidattribute_id");
                if (bldgRealEstateIDAttributeId != 0 && projectionFilter.containsProperty("bldgRealEstateIDAttribute", module)) {
                    RealEstateIDAttribute bldgRealEstateIDAttribute = new RealEstateIDAttribute();
                    realEstateIDAttributeExporter.doExport(bldgRealEstateIDAttribute, bldgRealEstateIDAttributeId);

                    BldgRealEstateIDAttributePropertyElement property = new BldgRealEstateIDAttributePropertyElement(new RealEstateIDAttributeProperty(bldgRealEstateIDAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                long bldgUsecaseAttributeId = rs.getLong("bldgusecaseattribute_id");
                if (bldgUsecaseAttributeId != 0 && projectionFilter.containsProperty("bldgUsecaseAttribute", module)) {
                    BuildingUsecaseAttribute bldgUsecaseAttribute = new BuildingUsecaseAttribute();
                    buildingUsecaseAttributeExporter.doExport(bldgUsecaseAttribute, bldgUsecaseAttributeId);

                    BldgUsecaseAttributePropertyElement property = new BldgUsecaseAttributePropertyElement(new BuildingUsecaseAttributeProperty(bldgUsecaseAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
