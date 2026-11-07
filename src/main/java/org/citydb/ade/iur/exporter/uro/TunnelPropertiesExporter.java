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
import org.citygml4j.model.citygml.tunnel.AbstractTunnel;

import java.sql.*;

public class TunnelPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final ConstructionBaseAttributeExporter constructionBaseAttributeExporter;
    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final DisasterRiskAttributeExporter disasterRiskAttributeExporter;
    private final DmAttributeExporter dmAttributeExporter;
    private final FacilityAttributeExporter facilityAttributeExporter;
    private final FacilityIdAttributeExporter facilityIdAttributeExporter;
    private final FacilityTypeAttributeExporter facilityTypeAttributeExporter;
    private final TunnelFunctionalAttributeExporter tunnelFunctionalAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;
    private final ConstructionRiskAssessmentAttributeExporter constructionRiskAssessmentAttributeExporter;
    private final TunnelStructureAttributeExporter tunnelStructureAttributeExporter;
    private final PreparedStatement psTunDisasterRiskAttribute;
    private final PreparedStatement psTunDmAttribute;
    private final PreparedStatement psTunFacilityAttribute;
    private final PreparedStatement psTunKeyValuePairAttribute;

    public TunnelPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.TUNNEL);
        module = UrbanObjectModule.v3_2.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("tunbaseattribute_id", "tundataqualityattribute_id", "tunfacilityidattribute_id",
                        "tunfacilitytypeattribute_id", "tunfunctionalattribute_id", "tunriskassessmentattribut_id",
                        "tunstructureattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        Table tunDisasterRiskAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TUNNEL_TO_DISASTERR)));
        Select tunDisasterRiskAttributeSelect = new Select().addProjection(tunDisasterRiskAttributes.getColumn("disasterriskattribute_id"))
                .addSelection(ComparisonFactory.equalTo(tunDisasterRiskAttributes.getColumn("tunnel_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(tunDisasterRiskAttributes.getColumn("disasterriskattribute_id")));
        psTunDisasterRiskAttribute = connection.prepareStatement(tunDisasterRiskAttributeSelect.toString());
        Table tunDmAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TUNNEL_TO_DMATTRIBU)));
        Select tunDmAttributeSelect = new Select().addProjection(tunDmAttributes.getColumn("dmattribute_id"))
                .addSelection(ComparisonFactory.equalTo(tunDmAttributes.getColumn("tunnel_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(tunDmAttributes.getColumn("dmattribute_id")));
        psTunDmAttribute = connection.prepareStatement(tunDmAttributeSelect.toString());
        Table tunFacilityAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TUNNEL_TO_FACILITYA)));
        Select tunFacilityAttributeSelect = new Select().addProjection(tunFacilityAttributes.getColumn("facilityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(tunFacilityAttributes.getColumn("tunnel_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(tunFacilityAttributes.getColumn("facilityattribute_id")));
        psTunFacilityAttribute = connection.prepareStatement(tunFacilityAttributeSelect.toString());
        Table tunKeyValuePairAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TUNNEL_TO_KEYVALUEP)));
        Select tunKeyValuePairAttributeSelect = new Select().addProjection(tunKeyValuePairAttributes.getColumn("keyvaluepairattribute_id"))
                .addSelection(ComparisonFactory.equalTo(tunKeyValuePairAttributes.getColumn("tunnel_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(tunKeyValuePairAttributes.getColumn("keyvaluepairattribute_id")));
        psTunKeyValuePairAttribute = connection.prepareStatement(tunKeyValuePairAttributeSelect.toString());
        constructionBaseAttributeExporter = manager.getExporter(ConstructionBaseAttributeExporter.class);
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        disasterRiskAttributeExporter = manager.getExporter(DisasterRiskAttributeExporter.class);
        dmAttributeExporter = manager.getExporter(DmAttributeExporter.class);
        facilityAttributeExporter = manager.getExporter(FacilityAttributeExporter.class);
        facilityIdAttributeExporter = manager.getExporter(FacilityIdAttributeExporter.class);
        facilityTypeAttributeExporter = manager.getExporter(FacilityTypeAttributeExporter.class);
        tunnelFunctionalAttributeExporter = manager.getExporter(TunnelFunctionalAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
        constructionRiskAssessmentAttributeExporter = manager.getExporter(ConstructionRiskAssessmentAttributeExporter.class);
        tunnelStructureAttributeExporter = manager.getExporter(TunnelStructureAttributeExporter.class);
    }

    public void doExport(AbstractTunnel parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long tunBaseAttributeId = rs.getLong("tunbaseattribute_id");
                if (tunBaseAttributeId != 0 && projectionFilter.containsProperty("tunBaseAttribute", module)) {
                    ConstructionBaseAttribute tunBaseAttribute = new ConstructionBaseAttribute();
                    constructionBaseAttributeExporter.doExport(tunBaseAttribute, tunBaseAttributeId);

                    TunBaseAttributePropertyElement property = new TunBaseAttributePropertyElement(new ConstructionBaseAttributeProperty(tunBaseAttribute));
                    parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                }

                long tunDataQualityAttributeId = rs.getLong("tundataqualityattribute_id");
                if (tunDataQualityAttributeId != 0 && projectionFilter.containsProperty("tunDataQualityAttribute", module)) {
                    DataQualityAttribute tunDataQualityAttribute = new DataQualityAttribute();
                    dataQualityAttributeExporter.doExport(tunDataQualityAttribute, tunDataQualityAttributeId);

                    TunDataQualityAttributePropertyElement property = new TunDataQualityAttributePropertyElement(new DataQualityAttributeProperty(tunDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                }

                if (projectionFilter.containsProperty("tunDisasterRiskAttribute", module)) {
                    psTunDisasterRiskAttribute.setLong(1, parentId);
                    try (ResultSet tunDisasterRiskAttributeRs = psTunDisasterRiskAttribute.executeQuery()) {
                        while (tunDisasterRiskAttributeRs.next()) {
                            DisasterRiskAttribute tunDisasterRiskAttribute = disasterRiskAttributeExporter.doExport(tunDisasterRiskAttributeRs.getLong("disasterriskattribute_id"));
                            if (tunDisasterRiskAttribute != null) {
                                TunDisasterRiskAttributePropertyElement property = new TunDisasterRiskAttributePropertyElement(new DisasterRiskAttributeProperty(tunDisasterRiskAttribute));
                                parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                            }
                        }
                    }
                }

                if (projectionFilter.containsProperty("tunDmAttribute", module)) {
                    psTunDmAttribute.setLong(1, parentId);
                    try (ResultSet tunDmAttributeRs = psTunDmAttribute.executeQuery()) {
                        while (tunDmAttributeRs.next()) {
                            DmAttribute tunDmAttribute = dmAttributeExporter.doExport(tunDmAttributeRs.getLong("dmattribute_id"));
                            if (tunDmAttribute != null) {
                                TunDmAttributePropertyElement property = new TunDmAttributePropertyElement(new DmAttributeProperty(tunDmAttribute));
                                parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                            }
                        }
                    }
                }

                if (projectionFilter.containsProperty("tunFacilityAttribute", module)) {
                    psTunFacilityAttribute.setLong(1, parentId);
                    try (ResultSet tunFacilityAttributeRs = psTunFacilityAttribute.executeQuery()) {
                        while (tunFacilityAttributeRs.next()) {
                            FacilityAttribute tunFacilityAttribute = facilityAttributeExporter.doExport(tunFacilityAttributeRs.getLong("facilityattribute_id"));
                            if (tunFacilityAttribute != null) {
                                TunFacilityAttributePropertyElement property = new TunFacilityAttributePropertyElement(new FacilityAttributeProperty(tunFacilityAttribute));
                                parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                            }
                        }
                    }
                }

                long tunFacilityIdAttributeId = rs.getLong("tunfacilityidattribute_id");
                if (tunFacilityIdAttributeId != 0 && projectionFilter.containsProperty("tunFacilityIdAttribute", module)) {
                    FacilityIdAttribute tunFacilityIdAttribute = facilityIdAttributeExporter.doExport(tunFacilityIdAttributeId);
                    if (tunFacilityIdAttribute != null) {
                        TunFacilityIdAttributePropertyElement property = new TunFacilityIdAttributePropertyElement(new FacilityIdAttributeProperty(tunFacilityIdAttribute));
                        parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                    }
                }

                long tunFacilityTypeAttributeId = rs.getLong("tunfacilitytypeattribute_id");
                if (tunFacilityTypeAttributeId != 0 && projectionFilter.containsProperty("tunFacilityTypeAttribute", module)) {
                    FacilityTypeAttribute tunFacilityTypeAttribute = new FacilityTypeAttribute();
                    facilityTypeAttributeExporter.doExport(tunFacilityTypeAttribute, tunFacilityTypeAttributeId);

                    TunFacilityTypeAttributePropertyElement property = new TunFacilityTypeAttributePropertyElement(new FacilityTypeAttributeProperty(tunFacilityTypeAttribute));
                    parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                }

                long tunFunctionalAttributeId = rs.getLong("tunfunctionalattribute_id");
                if (tunFunctionalAttributeId != 0 && projectionFilter.containsProperty("tunFunctionalAttribute", module)) {
                    TunnelFunctionalAttribute tunFunctionalAttribute = new TunnelFunctionalAttribute();
                    tunnelFunctionalAttributeExporter.doExport(tunFunctionalAttribute, tunFunctionalAttributeId);

                    TunFunctionalAttributePropertyElement property = new TunFunctionalAttributePropertyElement(new TunnelFunctionalAttributeProperty(tunFunctionalAttribute));
                    parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                }

                if (projectionFilter.containsProperty("tunKeyValuePairAttribute", module)) {
                    psTunKeyValuePairAttribute.setLong(1, parentId);
                    try (ResultSet tunKeyValuePairAttributeRs = psTunKeyValuePairAttribute.executeQuery()) {
                        while (tunKeyValuePairAttributeRs.next()) {
                            KeyValuePairAttribute tunKeyValuePairAttribute = new KeyValuePairAttribute();
                            keyValuePairAttributeExporter.doExport(tunKeyValuePairAttribute, tunKeyValuePairAttributeRs.getLong("keyvaluepairattribute_id"));

                            TunKeyValuePairAttributePropertyElement property = new TunKeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(tunKeyValuePairAttribute));
                            parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                        }
                    }
                }

                long tunRiskAssessmentAttributeId = rs.getLong("tunriskassessmentattribut_id");
                if (tunRiskAssessmentAttributeId != 0 && projectionFilter.containsProperty("tunRiskAssessmentAttribute", module)) {
                    ConstructionRiskAssessmentAttribute tunRiskAssessmentAttribute = new ConstructionRiskAssessmentAttribute();
                    constructionRiskAssessmentAttributeExporter.doExport(tunRiskAssessmentAttribute, tunRiskAssessmentAttributeId);

                    TunRiskAssessmentAttributePropertyElement property = new TunRiskAssessmentAttributePropertyElement(new ConstructionRiskAssessmentAttributeProperty(tunRiskAssessmentAttribute));
                    parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                }

                long tunStructureAttributeId = rs.getLong("tunstructureattribute_id");
                if (tunStructureAttributeId != 0 && projectionFilter.containsProperty("tunStructureAttribute", module)) {
                    TunnelStructureAttribute tunStructureAttribute = new TunnelStructureAttribute();
                    tunnelStructureAttributeExporter.doExport(tunStructureAttribute, tunStructureAttributeId);

                    TunStructureAttributePropertyElement property = new TunStructureAttributePropertyElement(new TunnelStructureAttributeProperty(tunStructureAttribute));
                    parent.addGenericApplicationPropertyOfAbstractTunnel(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psTunDisasterRiskAttribute.close();
        psTunDmAttribute.close();
        psTunFacilityAttribute.close();
        psTunKeyValuePairAttribute.close();
    }
}
