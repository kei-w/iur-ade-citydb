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
import org.citygml4j.model.citygml.vegetation.AbstractVegetationObject;

import java.sql.*;

public class VegetationObjectPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final DmAttributeExporter dmAttributeExporter;
    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;

    public VegetationObjectPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT);
        module = UrbanObjectModule.v3_1.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("vegfacilityidattribute_id", "vegfacilitytypeattribute_id", "vegdataqualityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
    }

    public void doExport(AbstractVegetationObject parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long vegFacilityIdAttributeId = rs.getLong("vegfacilityidattribute_id");
                if (vegFacilityIdAttributeId != 0 && projectionFilter.containsProperty("vegFacilityIdAttribute", module)) {
                    FacilityIdAttribute vegFacilityIdAttribute = facilityIdAttributeExporter.doExport(vegFacilityIdAttributeId);
                    if (vegFacilityIdAttribute != null) {
                        VegFacilityIdAttributePropertyElement property = new VegFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(vegFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfVegetationObject(property);
                    }
                }

                long vegFacilityTypeAttributeId = rs.getLong("vegfacilitytypeattribute_id");
                if (vegFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("vegFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute vegFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(vegFacilityTypeAttribute, vegFacilityTypeAttributeId);

                    VegFacilityTypeAttributePropertyElement property = new VegFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(vegFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfVegetationObject(property);
                }

                if (projectionFilter.containsProperty("vegFacilityAttribute", module)) {
                    for (FacilityAttribute vegFacilityAttribute : facilityAttributeExporter.doExport(parentId, "cityobject_vegfacilityatt_id")) {
                        VegFacilityAttributePropertyElement property = new VegFacilityAttributePropertyElement(new FacilityAttributeProperty(vegFacilityAttribute));
                        parent.addGenericApplicationPropertyOfVegetationObject(property);
                    }
                }

                if (projectionFilter.containsProperty("vegDmAttribute", module)) {
                    for (DmAttribute vegDmAttribute : dmAttributeExporter.doExport(parentId, "cityobject_vegdmattribute_id")) {
                        VegDmAttributePropertyElement property = new VegDmAttributePropertyElement(new DmAttributeProperty(vegDmAttribute));
                        parent.addGenericApplicationPropertyOfVegetationObject(property);
                    }
                }

                long vegDataQualityAttributeId = rs.getLong("vegdataqualityattribute_id");
                if (vegDataQualityAttributeId != 0 && projectionFilter.containsProperty("vegDataQualityAttribute", module)) {
                    DataQualityAttribute vegDataQualityAttribute = new DataQualityAttribute();
                    dataQualityAttributeExporter.doExport(vegDataQualityAttribute, vegDataQualityAttributeId);

                    VegDataQualityAttributePropertyElement property = new VegDataQualityAttributePropertyElement(new DataQualityAttributeProperty(vegDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfVegetationObject(property);
                }

                if (projectionFilter.containsProperty("vegKeyValuePairAttribute", module)) {
                    for (KeyValuePairAttribute vegKeyValuePairAttribute : keyValuePairAttributeExporter.doExport(parentId, "cityobject_vegkeyvaluepai_id")) {
                        VegKeyValuePairAttributePropertyElement property = new VegKeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(vegKeyValuePairAttribute));
                        parent.addGenericApplicationPropertyOfVegetationObject(property);
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
