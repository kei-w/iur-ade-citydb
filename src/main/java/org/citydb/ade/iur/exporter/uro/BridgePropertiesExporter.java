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
import org.citygml4j.model.citygml.bridge.AbstractBridge;

import java.sql.*;

public class BridgePropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final ConstructionBaseAttributeExporter constructionBaseAttributeExporter;
    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final BridgeStructureAttributeExporter bridgeStructureAttributeExporter;
    private final BridgeFunctionalAttributeExporter bridgeFunctionalAttributeExporter;
    private final ConstructionRiskAssessmentAttributeExporter constructionRiskAssessmentAttributeExporter;
    private final DisasterRiskAttributeExporter disasterRiskAttributeExporter;
    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;

    public BridgePropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BRIDGE);
        module = UrbanObjectModule.v3_1.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("bridbaseattribute_id", "bridfacilityidattribute_id", "bridfacilitytypeattribute_id",
                        "bridstructureattribute_id", "bridfunctionalattribute_id", "bridriskassessmentattribu_id",
                        "briddataqualityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        constructionBaseAttributeExporter = manager.getExporter(ConstructionBaseAttributeExporter.class);
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        bridgeStructureAttributeExporter = manager.getExporter(BridgeStructureAttributeExporter.class);
        bridgeFunctionalAttributeExporter = manager.getExporter(BridgeFunctionalAttributeExporter.class);
        constructionRiskAssessmentAttributeExporter = manager.getExporter(ConstructionRiskAssessmentAttributeExporter.class);
        disasterRiskAttributeExporter = manager.getExporter(DisasterRiskAttributeExporter.class);
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
    }

    public void doExport(AbstractBridge parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long bridBaseAttributeId = rs.getLong("bridbaseattribute_id");
                if (bridBaseAttributeId != 0 && projectionFilter.containsProperty("bridBaseAttribute", module)) {
                    ConstructionBaseAttribute bridBaseAttribute = new ConstructionBaseAttribute();
                    constructionBaseAttributeExporter.doExport(bridBaseAttribute, bridBaseAttributeId);

                    BridBaseAttributePropertyElement property = new BridBaseAttributePropertyElement(new ConstructionBaseAttributeProperty(bridBaseAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBridge(property);
                }

                long bridFacilityIdAttributeId = rs.getLong("bridfacilityidattribute_id");
                if (bridFacilityIdAttributeId != 0 && projectionFilter.containsProperty("bridFacilityIdAttribute", module)) {
                    FacilityIdAttribute bridFacilityIdAttribute = facilityIdAttributeExporter.doExport(bridFacilityIdAttributeId);
                    if (bridFacilityIdAttribute != null) {
                        BridFacilityIdAttributePropertyElement property = new BridFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(bridFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBridge(property);
                    }
                }

                long bridFacilityTypeAttributeId = rs.getLong("bridfacilitytypeattribute_id");
                if (bridFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("bridFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute bridFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(bridFacilityTypeAttribute, bridFacilityTypeAttributeId);

                    BridFacilityTypeAttributePropertyElement property = new BridFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(bridFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBridge(property);
                }

                long bridStructureAttributeId = rs.getLong("bridstructureattribute_id");
                if (bridStructureAttributeId != 0 && projectionFilter.containsProperty("bridStructureAttribute", module)) {
                    BridgeStructureAttribute bridStructureAttribute = new BridgeStructureAttribute();
                    bridgeStructureAttributeExporter.doExport(bridStructureAttribute, bridStructureAttributeId);

                    BridStructureAttributePropertyElement property = new BridStructureAttributePropertyElement(new BridgeStructureAttributeProperty(bridStructureAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBridge(property);
                }

                long bridFunctionalAttributeId = rs.getLong("bridfunctionalattribute_id");
                if (bridFunctionalAttributeId != 0 && projectionFilter.containsProperty("bridFunctionalAttribute", module)) {
                    BridgeFunctionalAttribute bridFunctionalAttribute = new BridgeFunctionalAttribute();
                    bridgeFunctionalAttributeExporter.doExport(bridFunctionalAttribute, bridFunctionalAttributeId);

                    BridFunctionalAttributePropertyElement property = new BridFunctionalAttributePropertyElement(new BridgeFunctionalAttributeProperty(bridFunctionalAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBridge(property);
                }

                long bridRiskAssessmentAttributeId = rs.getLong("bridriskassessmentattribu_id");
                if (bridRiskAssessmentAttributeId != 0 && projectionFilter.containsProperty("bridRiskAssessmentAttribute", module)) {
                    ConstructionRiskAssessmentAttribute bridRiskAssessmentAttribute = new ConstructionRiskAssessmentAttribute();
                    constructionRiskAssessmentAttributeExporter.doExport(bridRiskAssessmentAttribute, bridRiskAssessmentAttributeId);

                    BridRiskAssessmentAttributePropertyElement property = new BridRiskAssessmentAttributePropertyElement(new ConstructionRiskAssessmentAttributeProperty(bridRiskAssessmentAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBridge(property);
                }

                if (projectionFilter.containsProperty("bridDisasterRiskAttribute", module)) {
                    for (DisasterRiskAttribute bridDisasterRiskAttribute : disasterRiskAttributeExporter.doExport(parentId, "bridge_briddisasterriskat_id")) {
                        BridDisasterRiskAttributePropertyElement property = new BridDisasterRiskAttributePropertyElement(new DisasterRiskAttributeProperty(bridDisasterRiskAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBridge(property);
                    }
                }

                long bridDataQualityAttributeId = rs.getLong("briddataqualityattribute_id");
                if (bridDataQualityAttributeId != 0 && projectionFilter.containsProperty("bridDataQualityAttribute", module)) {
                    DataQualityAttribute bridDataQualityAttribute = new DataQualityAttribute();
                    dataQualityAttributeExporter.doExport(bridDataQualityAttribute, bridDataQualityAttributeId);

                    BridDataQualityAttributePropertyElement property = new BridDataQualityAttributePropertyElement(new DataQualityAttributeProperty(bridDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfAbstractBridge(property);
                }

                if (projectionFilter.containsProperty("bridKeyValuePairAttribute", module)) {
                    for (KeyValuePairAttribute bridKeyValuePairAttribute : keyValuePairAttributeExporter.doExport(parentId, "bridge_bridkeyvaluepairat_id")) {
                        BridKeyValuePairAttributePropertyElement property = new BridKeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(bridKeyValuePairAttribute));
                        parent.addGenericApplicationPropertyOfAbstractBridge(property);
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
