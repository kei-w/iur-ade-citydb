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
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.building.AbstractBuilding;

import java.sql.*;

public class BuildingPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final DisasterRiskAttributeExporter disasterRiskAttributeExporter;
    private final DmAttributeExporter dmAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;
    private final RealEstateIDAttributeExporter realEstateIDAttributeExporter;
    private final BuildingUsecaseAttributeExporter buildingUsecaseAttributeExporter;
    private final BuildingDetailAttributeExporter buildingDetailAttributeExporter;
    private final BuildingIDAttributeExporter buildingIDAttributeExporter;
    private final IfcAttributeExporter ifcAttributeExporter;
    private final IndoorAttributeExporter indoorAttributeExporter;
    private final LargeCustomerFacilityAttributeExporter largeCustomerFacilityAttributeExporter;
    private final PreparedStatement psBldgDisasterRiskAttribute;
    private final PreparedStatement psBldgDmAttribute;
    private final PreparedStatement psBldgFacilityAttribute;
    private final PreparedStatement psBldgKeyValuePairAttribute;

    public BuildingPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BUILDING);
        module = UrbanObjectModule.v3_2.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("bldgdataqualityattribute_id", "bldgfacilityidattribute_id", "bldgfacilitytypeattribute_id",
                        "bldgrealestateidattribute_id", "bldgusecaseattribute_id", "buildingdetailattribute_id",
                        "buildingidattribute_id", "ifcbuildingattribute_id", "indoorbuildingattribute_id",
                        "largecustomerfacilityattr_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        Table bldgDisasterRiskAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDING_TO_DISASTERR)));
        Select bldgDisasterRiskAttributeSelect = new Select().addProjection(bldgDisasterRiskAttributes.getColumn("disasterriskattribute_id"))
                .addSelection(ComparisonFactory.equalTo(bldgDisasterRiskAttributes.getColumn("building_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(bldgDisasterRiskAttributes.getColumn("disasterriskattribute_id")));
        psBldgDisasterRiskAttribute = connection.prepareStatement(bldgDisasterRiskAttributeSelect.toString());
        Table bldgDmAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDING_TO_DMATTRIBU)));
        Select bldgDmAttributeSelect = new Select().addProjection(bldgDmAttributes.getColumn("dmattribute_id"))
                .addSelection(ComparisonFactory.equalTo(bldgDmAttributes.getColumn("building_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(bldgDmAttributes.getColumn("dmattribute_id")));
        psBldgDmAttribute = connection.prepareStatement(bldgDmAttributeSelect.toString());
        Table bldgFacilityAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDING_TO_FACILITYA)));
        Select bldgFacilityAttributeSelect = new Select().addProjection(bldgFacilityAttributes.getColumn("facilityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(bldgFacilityAttributes.getColumn("building_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(bldgFacilityAttributes.getColumn("facilityattribute_id")));
        psBldgFacilityAttribute = connection.prepareStatement(bldgFacilityAttributeSelect.toString());
        Table bldgKeyValuePairAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDING_TO_KEYVALUEP)));
        Select bldgKeyValuePairAttributeSelect = new Select().addProjection(bldgKeyValuePairAttributes.getColumn("keyvaluepairattribute_id"))
                .addSelection(ComparisonFactory.equalTo(bldgKeyValuePairAttributes.getColumn("building_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(bldgKeyValuePairAttributes.getColumn("keyvaluepairattribute_id")));
        psBldgKeyValuePairAttribute = connection.prepareStatement(bldgKeyValuePairAttributeSelect.toString());
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        disasterRiskAttributeExporter = manager.getExporter(DisasterRiskAttributeExporter.class);
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
        realEstateIDAttributeExporter = manager.getExporter(RealEstateIDAttributeExporter.class);
        buildingUsecaseAttributeExporter = manager.getExporter(BuildingUsecaseAttributeExporter.class);
        buildingDetailAttributeExporter = manager.getExporter(BuildingDetailAttributeExporter.class);
        buildingIDAttributeExporter = manager.getExporter(BuildingIDAttributeExporter.class);
        ifcAttributeExporter = manager.getExporter(IfcAttributeExporter.class);
        indoorAttributeExporter = manager.getExporter(IndoorAttributeExporter.class);
        largeCustomerFacilityAttributeExporter = manager.getExporter(LargeCustomerFacilityAttributeExporter.class);
    }

    public void doExport(AbstractBuilding parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long bldgDataQualityAttributeId = rs.getLong("bldgdataqualityattribute_id");
                if (bldgDataQualityAttributeId != 0 && projectionFilter.containsProperty("bldgDataQualityAttribute", module)) {
                    DataQualityAttribute bldgDataQualityAttribute = new DataQualityAttribute();
                    dataQualityAttributeExporter.doExport(bldgDataQualityAttribute, bldgDataQualityAttributeId);

                    BldgDataQualityAttributePropertyElement property = new BldgDataQualityAttributePropertyElement(new DataQualityAttributeProperty(bldgDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                if (projectionFilter.containsProperty("bldgDisasterRiskAttribute", module)) {
                    psBldgDisasterRiskAttribute.setLong(1, parentId);
                    try (ResultSet bldgDisasterRiskAttributeRs = psBldgDisasterRiskAttribute.executeQuery()) {
                        while (bldgDisasterRiskAttributeRs.next()) {
                            DisasterRiskAttribute bldgDisasterRiskAttribute = disasterRiskAttributeExporter.doExport(bldgDisasterRiskAttributeRs.getLong("disasterriskattribute_id"));
                            if (bldgDisasterRiskAttribute != null) {
                                BldgDisasterRiskAttributePropertyElement property = new BldgDisasterRiskAttributePropertyElement(new DisasterRiskAttributeProperty(bldgDisasterRiskAttribute));
                                parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                            }
                        }
                    }
                }

                if (projectionFilter.containsProperty("bldgDmAttribute", module)) {
                    psBldgDmAttribute.setLong(1, parentId);
                    try (ResultSet bldgDmAttributeRs = psBldgDmAttribute.executeQuery()) {
                        while (bldgDmAttributeRs.next()) {
                            DmAttribute bldgDmAttribute = dmAttributeExporter.doExport(bldgDmAttributeRs.getLong("dmattribute_id"));
                            if (bldgDmAttribute != null) {
                                BldgDmAttributePropertyElement property = new BldgDmAttributePropertyElement(new DmAttributeProperty(bldgDmAttribute));
                                parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                            }
                        }
                    }
                }

                if (projectionFilter.containsProperty("bldgFacilityAttribute", module)) {
                    psBldgFacilityAttribute.setLong(1, parentId);
                    try (ResultSet bldgFacilityAttributeRs = psBldgFacilityAttribute.executeQuery()) {
                        while (bldgFacilityAttributeRs.next()) {
                            FacilityAttribute bldgFacilityAttribute = facilityAttributeExporter.doExport(bldgFacilityAttributeRs.getLong("facilityattribute_id"));
                            if (bldgFacilityAttribute != null) {
                                BldgFacilityAttributePropertyElement property = new BldgFacilityAttributePropertyElement(new FacilityAttributeProperty(bldgFacilityAttribute));
                                parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                            }
                        }
                    }
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

                if (projectionFilter.containsProperty("bldgKeyValuePairAttribute", module)) {
                    psBldgKeyValuePairAttribute.setLong(1, parentId);
                    try (ResultSet bldgKeyValuePairAttributeRs = psBldgKeyValuePairAttribute.executeQuery()) {
                        while (bldgKeyValuePairAttributeRs.next()) {
                            KeyValuePairAttribute bldgKeyValuePairAttribute = new KeyValuePairAttribute();
                            keyValuePairAttributeExporter.doExport(bldgKeyValuePairAttribute, bldgKeyValuePairAttributeRs.getLong("keyvaluepairattribute_id"));

                            BldgKeyValuePairAttributePropertyElement property = new BldgKeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(bldgKeyValuePairAttribute));
                            parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                        }
                    }
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

                long buildingDetailAttributeId = rs.getLong("buildingdetailattribute_id");
                if (buildingDetailAttributeId != 0 && projectionFilter.containsProperty("buildingDetailAttribute", module)) {
                    BuildingDetailAttribute buildingDetailAttribute = new BuildingDetailAttribute();
                    buildingDetailAttributeExporter.doExport(buildingDetailAttribute, buildingDetailAttributeId);

                    BuildingDetailAttributePropertyElement property = new BuildingDetailAttributePropertyElement(new BuildingDetailAttributeProperty(buildingDetailAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                long buildingIDAttributeId = rs.getLong("buildingidattribute_id");
                if (buildingIDAttributeId != 0 && projectionFilter.containsProperty("buildingIDAttribute", module)) {
                    BuildingIDAttribute buildingIDAttribute = new BuildingIDAttribute();
                    buildingIDAttributeExporter.doExport(buildingIDAttribute, buildingIDAttributeId);

                    BuildingIDAttributePropertyElement property = new BuildingIDAttributePropertyElement(new BuildingIDAttributeProperty(buildingIDAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }

                long ifcBuildingAttributeId = rs.getLong("ifcbuildingattribute_id");
                if (ifcBuildingAttributeId != 0 && projectionFilter.containsProperty("ifcBuildingAttribute", module)) {
                    IfcAttribute ifcBuildingAttribute = ifcAttributeExporter.doExport(ifcBuildingAttributeId);
                    if (ifcBuildingAttribute != null) {
                        IfcBuildingAttributePropertyElement property = new IfcBuildingAttributePropertyElement(new IfcAttributeProperty(ifcBuildingAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                long indoorBuildingAttributeId = rs.getLong("indoorbuildingattribute_id");
                if (indoorBuildingAttributeId != 0 && projectionFilter.containsProperty("indoorBuildingAttribute", module)) {
                    IndoorAttribute indoorBuildingAttribute = indoorAttributeExporter.doExport(indoorBuildingAttributeId);
                    if (indoorBuildingAttribute != null) {
                        IndoorBuildingAttributePropertyElement property = new IndoorBuildingAttributePropertyElement(new IndoorAttributeProperty(indoorBuildingAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                long largeCustomerFacilityAttributeId = rs.getLong("largecustomerfacilityattr_id");
                if (largeCustomerFacilityAttributeId != 0 && projectionFilter.containsProperty("largeCustomerFacilityAttribute", module)) {
                    LargeCustomerFacilityAttribute largeCustomerFacilityAttribute = new LargeCustomerFacilityAttribute();
                    largeCustomerFacilityAttributeExporter.doExport(largeCustomerFacilityAttribute, largeCustomerFacilityAttributeId);

                    LargeCustomerFacilityAttributePropertyElement property = new LargeCustomerFacilityAttributePropertyElement(new LargeCustomerFacilityAttributeProperty(largeCustomerFacilityAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psBldgDisasterRiskAttribute.close();
        psBldgDmAttribute.close();
        psBldgFacilityAttribute.close();
        psBldgKeyValuePairAttribute.close();
    }
}
