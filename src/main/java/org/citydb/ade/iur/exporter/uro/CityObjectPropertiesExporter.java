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
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.core.AbstractCityObject;

import java.sql.*;

public class CityObjectPropertiesExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ExportManager manager;
    private final PreparedStatement ps;
    private final String module;

    public CityObjectPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2);
        module = UrbanObjectModule.v3_2.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table pointCloudCityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Select select = new Select().addProjection(table.getColumns("pointcloud_id"))
                .addJoin(JoinFactory.left(pointCloudCityObject, "id", ComparisonName.EQUAL_TO, table.getColumn("pointcloud_id")))
                .addProjection(pointCloudCityObject.getColumn("objectclass_id", "pointcloud_id_objectclass_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(AbstractCityObject parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long pointCloudId = rs.getLong("pointcloud_id");
                if (pointCloudId != 0 && projectionFilter.containsProperty("pointCloud", module)) {
                    int pointCloudObjectClassId = rs.getInt("pointcloud_id_objectclass_id");
                    AbstractObjectType<?> pointCloudType = helper.getAbstractObjectType(pointCloudObjectClassId);
                    AbstractPointCloud pointCloud = helper.createObject(pointCloudId, pointCloudObjectClassId, AbstractPointCloud.class);
                    if (pointCloud != null) {
                        manager.exportObject(pointCloud, pointCloudId, pointCloudType, helper.getProjectionFilter(pointCloudType));
                        PointCloudPropertyElement property = new PointCloudPropertyElement(new AbstractPointCloudProperty(pointCloud));
                        parent.addGenericApplicationPropertyOfCityObject(property);
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
