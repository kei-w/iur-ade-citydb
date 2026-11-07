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
import org.citygml4j.model.citygml.relief.ReliefFeature;

import java.sql.*;

public class ReliefFeaturePropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;
    private final PreparedStatement psDemKeyValuePairAttribute;

    public ReliefFeaturePropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.RELIEF_FEATURE);
        module = UrbanObjectModule.v3_1.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("demdataqualityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        Table demKeyValuePairAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.RELIEF_FE_TO_KEYVALUEP)));
        Select demKeyValuePairAttributeSelect = new Select().addProjection(demKeyValuePairAttributes.getColumn("keyvaluepairattribute_id"))
                .addSelection(ComparisonFactory.equalTo(demKeyValuePairAttributes.getColumn("relief_feature_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(demKeyValuePairAttributes.getColumn("keyvaluepairattribute_id")));
        psDemKeyValuePairAttribute = connection.prepareStatement(demKeyValuePairAttributeSelect.toString());
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
    }

    public void doExport(ReliefFeature parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long demDataQualityAttributeId = rs.getLong("demdataqualityattribute_id");
                if (demDataQualityAttributeId != 0 && projectionFilter.containsProperty("demDataQualityAttribute", module)) {
                    DataQualityAttribute demDataQualityAttribute = new DataQualityAttribute();
                    dataQualityAttributeExporter.doExport(demDataQualityAttribute, demDataQualityAttributeId);

                    DemDataQualityAttributePropertyElement property = new DemDataQualityAttributePropertyElement(new DataQualityAttributeProperty(demDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfReliefFeature(property);
                }

                if (projectionFilter.containsProperty("demKeyValuePairAttribute", module)) {
                    psDemKeyValuePairAttribute.setLong(1, parentId);
                    try (ResultSet demKeyValuePairAttributeRs = psDemKeyValuePairAttribute.executeQuery()) {
                        while (demKeyValuePairAttributeRs.next()) {
                            KeyValuePairAttribute demKeyValuePairAttribute = new KeyValuePairAttribute();
                            keyValuePairAttributeExporter.doExport(demKeyValuePairAttribute, demKeyValuePairAttributeRs.getLong("keyvaluepairattribute_id"));

                            DemKeyValuePairAttributePropertyElement property = new DemKeyValuePairAttributePropertyElement(new KeyValuePairAttributeProperty(demKeyValuePairAttribute));
                            parent.addGenericApplicationPropertyOfReliefFeature(property);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psDemKeyValuePairAttribute.close();
    }
}
