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
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.transportation.AbstractTransportationObject;
import org.citygml4j.model.citygml.vegetation.AbstractVegetationObject;

import java.sql.*;

public class TransportationObjectPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final DmAttributeExporter dmAttributeExporter;
    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final VegetationDataQualityAttributeExporter vegetationDataQualityAttributeExporter;
    private final PreparedStatement psVegFacilityAttribute;

    public TransportationObjectPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT);
        module = UrbanObjectModule.v3_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("vegfacilityidattribute_id", "vegfacilitytypeattribute_id", "vegetationdataqualityattr_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        Table vegFacilityAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CITYOBJEC_TO_FACILITYA)));
        Select vegFacilityAttributeSelect = new Select().addProjection(vegFacilityAttributes.getColumn("facilityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(vegFacilityAttributes.getColumn("cityobject_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(vegFacilityAttributes.getColumn("facilityattribute_id")));
        psVegFacilityAttribute = connection.prepareStatement(vegFacilityAttributeSelect.toString());
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        vegetationDataQualityAttributeExporter = manager.getExporter(VegetationDataQualityAttributeExporter.class);
    }

    public void doExport(AbstractCityObject parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                if (parent instanceof AbstractTransportationObject && projectionFilter.containsProperty("tranDmAttribute", module)) {
                    for (DmAttribute tranDmAttribute : dmAttributeExporter.doExport(parentId, "cityobject_trandmattribut_id")) {
                        TranDmAttributePropertyElement property = new TranDmAttributePropertyElement(new DmAttributeProperty(tranDmAttribute));
                        ((AbstractTransportationObject) parent).addGenericApplicationPropertyOfTransportationObject(property);
                    }
                }

                long vegFacilityIdAttributeId = rs.getLong("vegfacilityidattribute_id");
                if (vegFacilityIdAttributeId != 0 && parent instanceof AbstractVegetationObject && projectionFilter.containsProperty("vegFacilityIdAttribute", module)) {
                    FacilityIdAttribute vegFacilityIdAttribute = facilityIdAttributeExporter.doExport(vegFacilityIdAttributeId);
                    if (vegFacilityIdAttribute != null) {
                        VegFacilityIdAttributePropertyElement property = new VegFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(vegFacilityIdAttribute));
                        ((AbstractVegetationObject) parent).addGenericApplicationPropertyOfVegetationObject(property);
                    }
                }

                long vegFacilityTypeAttributeId = rs.getLong("vegfacilitytypeattribute_id");
                if (vegFacilityTypeAttributeId != 0 && parent instanceof AbstractVegetationObject && projectionFilter.containsProperty("vegFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute vegFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(vegFacilityTypeAttribute, vegFacilityTypeAttributeId);

                    VegFacilityTypeAttributePropertyElement property = new VegFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(vegFacilityTypeAttribute));
                    ((AbstractVegetationObject) parent).addGenericApplicationPropertyOfVegetationObject(property);
                }

                if (parent instanceof AbstractVegetationObject && projectionFilter.containsProperty("vegFacilityAttribute", module)) {
                    psVegFacilityAttribute.setLong(1, parentId);
                    try (ResultSet vegFacilityAttributeRs = psVegFacilityAttribute.executeQuery()) {
                        while (vegFacilityAttributeRs.next()) {
                            FacilityAttribute vegFacilityAttribute = facilityAttributeExporter.doExport(vegFacilityAttributeRs.getLong("facilityattribute_id"));
                            if (vegFacilityAttribute != null) {
                                VegFacilityAttributePropertyElement property = new VegFacilityAttributePropertyElement(new FacilityAttributeProperty(vegFacilityAttribute));
                                ((AbstractVegetationObject) parent).addGenericApplicationPropertyOfVegetationObject(property);
                            }
                        }
                    }
                }

                if (parent instanceof AbstractVegetationObject && projectionFilter.containsProperty("vegDmAttribute", module)) {
                    for (DmAttribute vegDmAttribute : dmAttributeExporter.doExport(parentId, "cityobject_vegdmattribute_id")) {
                        VegDmAttributePropertyElement property = new VegDmAttributePropertyElement(new DmAttributeProperty(vegDmAttribute));
                        ((AbstractVegetationObject) parent).addGenericApplicationPropertyOfVegetationObject(property);
                    }
                }

                long vegetationDataQualityAttributeId = rs.getLong("vegetationdataqualityattr_id");
                if (vegetationDataQualityAttributeId != 0 && parent instanceof AbstractVegetationObject && projectionFilter.containsProperty("vegetationDataQualityAttribute", module)) {
                    VegetationDataQualityAttribute vegetationDataQualityAttribute = new VegetationDataQualityAttribute();
                    vegetationDataQualityAttributeExporter.doExport(vegetationDataQualityAttribute, vegetationDataQualityAttributeId);

                    VegetationDataQualityAttributePropertyElement property = new VegetationDataQualityAttributePropertyElement(new VegetationDataQualityAttributeProperty(vegetationDataQualityAttribute));
                    ((AbstractVegetationObject) parent).addGenericApplicationPropertyOfVegetationObject(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psVegFacilityAttribute.close();
    }
}
