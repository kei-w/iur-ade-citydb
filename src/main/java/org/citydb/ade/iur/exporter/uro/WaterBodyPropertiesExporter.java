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
import org.citygml4j.model.citygml.waterbody.WaterBody;

import java.sql.*;

public class WaterBodyPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final DmAttributeExporter dmAttributeExporter;
    private final WaterBodyAttributeExporter waterBodyAttributeExporter;
    private final WaterBodyDetailAttributeExporter waterBodyDetailAttributeExporter;

    public WaterBodyPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.WATERBODY);
        module = UrbanObjectModule.v3_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("wtrfacilityidattribute_id", "wtrfacilitytypeattribute_id", "waterbodydetailattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        waterBodyAttributeExporter = manager.getExporter(WaterBodyAttributeExporter.class);
        waterBodyDetailAttributeExporter = manager.getExporter(WaterBodyDetailAttributeExporter.class);
    }

    public void doExport(WaterBody parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long wtrFacilityIdAttributeId = rs.getLong("wtrfacilityidattribute_id");
                if (wtrFacilityIdAttributeId != 0 && projectionFilter.containsProperty("wtrFacilityIdAttribute", module)) {
                    FacilityIdAttribute wtrFacilityIdAttribute = facilityIdAttributeExporter.doExport(wtrFacilityIdAttributeId);
                    if (wtrFacilityIdAttribute != null) {
                        WtrFacilityIdAttributePropertyElement property = new WtrFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(wtrFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfWaterBody(property);
                    }
                }

                long wtrFacilityTypeAttributeId = rs.getLong("wtrfacilitytypeattribute_id");
                if (wtrFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("wtrFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute wtrFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(wtrFacilityTypeAttribute, wtrFacilityTypeAttributeId);

                    WtrFacilityTypeAttributePropertyElement property = new WtrFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(wtrFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfWaterBody(property);
                }

                if (projectionFilter.containsProperty("wtrFacilityAttribute", module)) {
                    for (FacilityAttribute wtrFacilityAttribute : facilityAttributeExporter.doExport(parentId, "waterbody_wtrfacilityattr_id")) {
                        WtrFacilityAttributePropertyElement property = new WtrFacilityAttributePropertyElement(new FacilityAttributeProperty(wtrFacilityAttribute));
                        parent.addGenericApplicationPropertyOfWaterBody(property);
                    }
                }

                if (projectionFilter.containsProperty("wtrDmAttribute", module)) {
                    for (DmAttribute wtrDmAttribute : dmAttributeExporter.doExport(parentId, "waterbody_wtrdmattribute_id")) {
                        WtrDmAttributePropertyElement property = new WtrDmAttributePropertyElement(new DmAttributeProperty(wtrDmAttribute));
                        parent.addGenericApplicationPropertyOfWaterBody(property);
                    }
                }

                if (projectionFilter.containsProperty("floodingRiskAttribute", module)) {
                    for (WaterBodyAttribute floodingRiskAttribute : waterBodyAttributeExporter.doExport(parentId)) {
                        if (floodingRiskAttribute instanceof WaterBodyFloodingRiskAttribute) {
                            FloodingRiskAttributePropertyElement property = new FloodingRiskAttributePropertyElement(new WaterBodyFloodingRiskAttributeProperty((WaterBodyFloodingRiskAttribute) floodingRiskAttribute));
                            parent.addGenericApplicationPropertyOfWaterBody(property);
                        }
                    }
                }

                long waterBodyDetailAttributeId = rs.getLong("waterbodydetailattribute_id");
                if (waterBodyDetailAttributeId != 0 && projectionFilter.containsProperty("waterBodyDetailAttribute", module)) {
                    WaterBodyDetailAttribute waterBodyDetailAttribute = new WaterBodyDetailAttribute();
                    waterBodyDetailAttributeExporter.doExport(waterBodyDetailAttribute, waterBodyDetailAttributeId);

                    WaterBodyDetailAttributePropertyElement property = new WaterBodyDetailAttributePropertyElement(new WaterBodyDetailAttributeProperty(waterBodyDetailAttribute));
                    parent.addGenericApplicationPropertyOfWaterBody(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
