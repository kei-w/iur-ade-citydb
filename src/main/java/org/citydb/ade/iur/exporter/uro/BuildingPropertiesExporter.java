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

    private final BuildingAttributeExporter buildingAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;

    public BuildingPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BUILDING);
        module = UrbanObjectModule.v2_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("buildingidattribute_id", "buildingdetailattribute_id", "largecustomerfacilityattr_id",
                        "buildingdataqualityattrib_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        buildingAttributeExporter = manager.getExporter(BuildingAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
    }

    public void doExport(AbstractBuilding parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long buildingIDAttributeId = rs.getLong("buildingidattribute_id");
                if (buildingIDAttributeId != 0 && projectionFilter.containsProperty("buildingIDAttribute", module)) {
                    BuildingAttribute buildingIDAttribute = buildingAttributeExporter.doExport(buildingIDAttributeId);
                    if (buildingIDAttribute instanceof BuildingIDAttribute) {
                        BuildingIDAttributePropertyElement property = new BuildingIDAttributePropertyElement(new BuildingIDAttributeProperty((BuildingIDAttribute) buildingIDAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                long buildingDetailAttributeId = rs.getLong("buildingdetailattribute_id");
                if (buildingDetailAttributeId != 0 && projectionFilter.containsProperty("buildingDetailAttribute", module)) {
                    BuildingAttribute buildingDetailAttribute = buildingAttributeExporter.doExport(buildingDetailAttributeId);
                    if (buildingDetailAttribute instanceof BuildingDetailAttribute) {
                        BuildingDetailAttributePropertyElement property = new BuildingDetailAttributePropertyElement(new BuildingDetailAttributeProperty((BuildingDetailAttribute) buildingDetailAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                long largeCustomerFacilityAttributeId = rs.getLong("largecustomerfacilityattr_id");
                if (largeCustomerFacilityAttributeId != 0 && projectionFilter.containsProperty("largeCustomerFacilityAttribute", module)) {
                    BuildingAttribute largeCustomerFacilityAttribute = buildingAttributeExporter.doExport(largeCustomerFacilityAttributeId);
                    if (largeCustomerFacilityAttribute instanceof LargeCustomerFacilityAttribute) {
                        LargeCustomerFacilityAttributePropertyElement property = new LargeCustomerFacilityAttributePropertyElement(new LargeCustomerFacilityAttributeProperty((LargeCustomerFacilityAttribute) largeCustomerFacilityAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                if (projectionFilter.containsProperty("buildingDisasterRiskAttribute", module)) {
                    for (BuildingAttribute buildingDisasterRiskAttribute : buildingAttributeExporter.doExport(parentId, "building_buildingdisaster_id")) {
                        if (buildingDisasterRiskAttribute instanceof BuildingDisasterRiskAttribute) {
                            BuildingDisasterRiskAttributePropertyElement property = new BuildingDisasterRiskAttributePropertyElement(new BuildingDisasterRiskAttributeProperty((BuildingDisasterRiskAttribute) buildingDisasterRiskAttribute));
                            parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                        }
                    }
                }

                if (projectionFilter.containsProperty("keyValuePairAttribute", module)) {
                    for (KeyValuePairAttribute keyValuePairAttribute : keyValuePairAttributeExporter.doExport(parentId)) {
                        KeyValuePairAttributePropertyElement property = new KeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(keyValuePairAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }

                long buildingDataQualityAttributeId = rs.getLong("buildingdataqualityattrib_id");
                if (buildingDataQualityAttributeId != 0 && projectionFilter.containsProperty("buildingDataQualityAttribute", module)) {
                    BuildingAttribute buildingDataQualityAttribute = buildingAttributeExporter.doExport(buildingDataQualityAttributeId);
                    if (buildingDataQualityAttribute instanceof BuildingDataQualityAttribute) {
                        BuildingDataQualityAttributePropertyElement property = new BuildingDataQualityAttributePropertyElement(new BuildingDataQualityAttributeProperty((BuildingDataQualityAttribute) buildingDataQualityAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBuilding(property);
                    }
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
