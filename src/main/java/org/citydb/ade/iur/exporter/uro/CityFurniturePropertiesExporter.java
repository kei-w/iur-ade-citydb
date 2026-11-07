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
import org.citygml4j.model.citygml.cityfurniture.CityFurniture;

import java.sql.*;

public class CityFurniturePropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final DmAttributeExporter dmAttributeExporter;
    private final CityFurnitureDetailAttributeExporter cityFurnitureDetailAttributeExporter;
    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;
    private final PreparedStatement psFrnFacilityAttribute;
    private final PreparedStatement psFrnDmAttribute;
    private final PreparedStatement psFrnKeyValuePairAttribute;

    public CityFurniturePropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CITY_FURNITURE);
        module = UrbanObjectModule.v3_1.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("frnfacilityidattribute_id", "frnfacilitytypeattribute_id", "cityfurnituredetailattrib_id",
                        "frndataqualityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        Table frnFacilityAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CITY_FURN_TO_FACILITYA)));
        Select frnFacilityAttributeSelect = new Select().addProjection(frnFacilityAttributes.getColumn("facilityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(frnFacilityAttributes.getColumn("city_furniture_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(frnFacilityAttributes.getColumn("facilityattribute_id")));
        psFrnFacilityAttribute = connection.prepareStatement(frnFacilityAttributeSelect.toString());
        Table frnDmAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CITY_FURN_TO_DMATTRIBU)));
        Select frnDmAttributeSelect = new Select().addProjection(frnDmAttributes.getColumn("dmattribute_id"))
                .addSelection(ComparisonFactory.equalTo(frnDmAttributes.getColumn("city_furniture_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(frnDmAttributes.getColumn("dmattribute_id")));
        psFrnDmAttribute = connection.prepareStatement(frnDmAttributeSelect.toString());
        Table frnKeyValuePairAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CITY_FURN_TO_KEYVALUEP)));
        Select frnKeyValuePairAttributeSelect = new Select().addProjection(frnKeyValuePairAttributes.getColumn("keyvaluepairattribute_id"))
                .addSelection(ComparisonFactory.equalTo(frnKeyValuePairAttributes.getColumn("city_furniture_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(frnKeyValuePairAttributes.getColumn("keyvaluepairattribute_id")));
        psFrnKeyValuePairAttribute = connection.prepareStatement(frnKeyValuePairAttributeSelect.toString());
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        cityFurnitureDetailAttributeExporter = manager.getExporter(CityFurnitureDetailAttributeExporter.class);
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
    }

    public void doExport(CityFurniture parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long frnFacilityIdAttributeId = rs.getLong("frnfacilityidattribute_id");
                if (frnFacilityIdAttributeId != 0 && projectionFilter.containsProperty("frnFacilityIdAttribute", module)) {
                    FacilityIdAttribute frnFacilityIdAttribute = facilityIdAttributeExporter.doExport(frnFacilityIdAttributeId);
                    if (frnFacilityIdAttribute != null) {
                        FrnFacilityIdAttributePropertyElement property = new FrnFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(frnFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfCityFurniture(property);
                    }
                }

                long frnFacilityTypeAttributeId = rs.getLong("frnfacilitytypeattribute_id");
                if (frnFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("frnFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute frnFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(frnFacilityTypeAttribute, frnFacilityTypeAttributeId);

                    FrnFacilityTypeAttributePropertyElement property = new FrnFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(frnFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfCityFurniture(property);
                }

                if (projectionFilter.containsProperty("frnFacilityAttribute", module)) {
                    psFrnFacilityAttribute.setLong(1, parentId);
                    try (ResultSet frnFacilityAttributeRs = psFrnFacilityAttribute.executeQuery()) {
                        while (frnFacilityAttributeRs.next()) {
                            FacilityAttribute frnFacilityAttribute = facilityAttributeExporter.doExport(frnFacilityAttributeRs.getLong("facilityattribute_id"));
                            if (frnFacilityAttribute != null) {
                                FrnFacilityAttributePropertyElement property = new FrnFacilityAttributePropertyElement(new FacilityAttributeProperty(frnFacilityAttribute));
                                parent.addGenericApplicationPropertyOfCityFurniture(property);
                            }
                        }
                    }
                }

                if (projectionFilter.containsProperty("frnDmAttribute", module)) {
                    psFrnDmAttribute.setLong(1, parentId);
                    try (ResultSet frnDmAttributeRs = psFrnDmAttribute.executeQuery()) {
                        while (frnDmAttributeRs.next()) {
                            DmAttribute frnDmAttribute = dmAttributeExporter.doExport(frnDmAttributeRs.getLong("dmattribute_id"));
                            if (frnDmAttribute != null) {
                                FrnDmAttributePropertyElement property = new FrnDmAttributePropertyElement(new DmAttributeProperty(frnDmAttribute));
                                parent.addGenericApplicationPropertyOfCityFurniture(property);
                            }
                        }
                    }
                }

                long cityFurnitureDetailAttributeId = rs.getLong("cityfurnituredetailattrib_id");
                if (cityFurnitureDetailAttributeId != 0 && projectionFilter.containsProperty("cityFurnitureDetailAttribute", module)) {
                    CityFurnitureDetailAttribute cityFurnitureDetailAttribute = new CityFurnitureDetailAttribute();
                    cityFurnitureDetailAttributeExporter.doExport(cityFurnitureDetailAttribute, cityFurnitureDetailAttributeId);

                    CityFurnitureDetailAttributePropertyElement property = new CityFurnitureDetailAttributePropertyElement(new CityFurnitureDetailAttributeProperty(cityFurnitureDetailAttribute));
                    parent.addGenericApplicationPropertyOfCityFurniture(property);
                }

                long frnDataQualityAttributeId = rs.getLong("frndataqualityattribute_id");
                if (frnDataQualityAttributeId != 0 && projectionFilter.containsProperty("frnDataQualityAttribute", module)) {
                    DataQualityAttribute frnDataQualityAttribute = new DataQualityAttribute();
                    dataQualityAttributeExporter.doExport(frnDataQualityAttribute, frnDataQualityAttributeId);

                    FrnDataQualityAttributePropertyElement property = new FrnDataQualityAttributePropertyElement(new DataQualityAttributeProperty(frnDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfCityFurniture(property);
                }

                if (projectionFilter.containsProperty("frnKeyValuePairAttribute", module)) {
                    psFrnKeyValuePairAttribute.setLong(1, parentId);
                    try (ResultSet frnKeyValuePairAttributeRs = psFrnKeyValuePairAttribute.executeQuery()) {
                        while (frnKeyValuePairAttributeRs.next()) {
                            KeyValuePairAttribute frnKeyValuePairAttribute = new KeyValuePairAttribute();
                            keyValuePairAttributeExporter.doExport(frnKeyValuePairAttribute, frnKeyValuePairAttributeRs.getLong("keyvaluepairattribute_id"));

                            FrnKeyValuePairAttributePropertyElement property = new FrnKeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(frnKeyValuePairAttribute));
                            parent.addGenericApplicationPropertyOfCityFurniture(property);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psFrnFacilityAttribute.close();
        psFrnDmAttribute.close();
        psFrnKeyValuePairAttribute.close();
    }
}
