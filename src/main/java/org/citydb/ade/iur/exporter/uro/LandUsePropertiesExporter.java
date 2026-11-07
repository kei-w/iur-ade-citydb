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
import org.citygml4j.model.citygml.landuse.LandUse;

import java.sql.*;

public class LandUsePropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final DmAttributeExporter dmAttributeExporter;
    private final IfcAttributeExporter ifcAttributeExporter;
    private final LandUseDataQualityAttributeExporter landUseDataQualityAttributeExporter;
    private final LandUseDetailAttributeExporter landUseDetailAttributeExporter;
    private final PreparedStatement psLuseFacilityAttribute;
    private final PreparedStatement psLuseDmAttribute;

    public LandUsePropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LAND_USE);
        module = UrbanObjectModule.v3_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("lusefacilityidattribute_id", "lusefacilitytypeattribute_id", "ifclanduseattribute_id",
                        "lusedataqualityattribute_id", "landusedetailattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        Table luseFacilityAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.LAND_USE_TO_FACILITYA)));
        Select luseFacilityAttributeSelect = new Select().addProjection(luseFacilityAttributes.getColumn("facilityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(luseFacilityAttributes.getColumn("land_use_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(luseFacilityAttributes.getColumn("facilityattribute_id")));
        psLuseFacilityAttribute = connection.prepareStatement(luseFacilityAttributeSelect.toString());
        Table luseDmAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.LAND_USE_TO_DMATTRIBU)));
        Select luseDmAttributeSelect = new Select().addProjection(luseDmAttributes.getColumn("dmattribute_id"))
                .addSelection(ComparisonFactory.equalTo(luseDmAttributes.getColumn("land_use_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(luseDmAttributes.getColumn("dmattribute_id")));
        psLuseDmAttribute = connection.prepareStatement(luseDmAttributeSelect.toString());
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        ifcAttributeExporter = manager.getExporter(IfcAttributeExporter.class);
        landUseDataQualityAttributeExporter = manager.getExporter(LandUseDataQualityAttributeExporter.class);
        landUseDetailAttributeExporter = manager.getExporter(LandUseDetailAttributeExporter.class);
    }

    public void doExport(LandUse parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long luseFacilityIdAttributeId = rs.getLong("lusefacilityidattribute_id");
                if (luseFacilityIdAttributeId != 0 && projectionFilter.containsProperty("luseFacilityIdAttribute", module)) {
                    FacilityIdAttribute luseFacilityIdAttribute = facilityIdAttributeExporter.doExport(luseFacilityIdAttributeId);
                    if (luseFacilityIdAttribute != null) {
                        LuseFacilityIdAttributePropertyElement property = new LuseFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(luseFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfLandUse(property);
                    }
                }

                long luseFacilityTypeAttributeId = rs.getLong("lusefacilitytypeattribute_id");
                if (luseFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("luseFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute luseFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(luseFacilityTypeAttribute, luseFacilityTypeAttributeId);

                    LuseFacilityTypeAttributePropertyElement property = new LuseFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(luseFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfLandUse(property);
                }

                if (projectionFilter.containsProperty("luseFacilityAttribute", module)) {
                    psLuseFacilityAttribute.setLong(1, parentId);
                    try (ResultSet luseFacilityAttributeRs = psLuseFacilityAttribute.executeQuery()) {
                        while (luseFacilityAttributeRs.next()) {
                            FacilityAttribute luseFacilityAttribute = facilityAttributeExporter.doExport(luseFacilityAttributeRs.getLong("facilityattribute_id"));
                            if (luseFacilityAttribute != null) {
                                LuseFacilityAttributePropertyElement property = new LuseFacilityAttributePropertyElement(new FacilityAttributeProperty(luseFacilityAttribute));
                                parent.addGenericApplicationPropertyOfLandUse(property);
                            }
                        }
                    }
                }

                if (projectionFilter.containsProperty("luseDmAttribute", module)) {
                    psLuseDmAttribute.setLong(1, parentId);
                    try (ResultSet luseDmAttributeRs = psLuseDmAttribute.executeQuery()) {
                        while (luseDmAttributeRs.next()) {
                            DmAttribute luseDmAttribute = dmAttributeExporter.doExport(luseDmAttributeRs.getLong("dmattribute_id"));
                            if (luseDmAttribute != null) {
                                LuseDmAttributePropertyElement property = new LuseDmAttributePropertyElement(new DmAttributeProperty(luseDmAttribute));
                                parent.addGenericApplicationPropertyOfLandUse(property);
                            }
                        }
                    }
                }

                long ifcLandUseAttributeId = rs.getLong("ifclanduseattribute_id");
                if (ifcLandUseAttributeId != 0 && projectionFilter.containsProperty("ifcLandUseAttribute", module)) {
                    IfcAttribute ifcLandUseAttribute = ifcAttributeExporter.doExport(ifcLandUseAttributeId);
                    if (ifcLandUseAttribute != null) {
                        IfcLandUseAttributePropertyElement property = new IfcLandUseAttributePropertyElement(new IfcAttributeProperty(ifcLandUseAttribute));
                        parent.addGenericApplicationPropertyOfLandUse(property);
                    }
                }

                long luseDataQualityAttributeId = rs.getLong("lusedataqualityattribute_id");
                if (luseDataQualityAttributeId != 0 && projectionFilter.containsProperty("luseDataQualityAttribute", module)) {
                    LandUseDataQualityAttribute luseDataQualityAttribute = new LandUseDataQualityAttribute();
                    landUseDataQualityAttributeExporter.doExport(luseDataQualityAttribute, luseDataQualityAttributeId);

                    LuseDataQualityAttributePropertyElement property = new LuseDataQualityAttributePropertyElement(new LandUseDataQualityAttributeProperty(luseDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfLandUse(property);
                }

                long landUseDetailAttributeId = rs.getLong("landusedetailattribute_id");
                if (landUseDetailAttributeId != 0 && projectionFilter.containsProperty("landUseDetailAttribute", module)) {
                    LandUseDetailAttribute landUseDetailAttribute = new LandUseDetailAttribute();
                    landUseDetailAttributeExporter.doExport(landUseDetailAttribute, landUseDetailAttributeId);

                    LandUseDetailAttributePropertyElement property = new LandUseDetailAttributePropertyElement(new LandUseDetailAttributeProperty(landUseDetailAttribute));
                    parent.addGenericApplicationPropertyOfLandUse(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psLuseFacilityAttribute.close();
        psLuseDmAttribute.close();
    }
}
