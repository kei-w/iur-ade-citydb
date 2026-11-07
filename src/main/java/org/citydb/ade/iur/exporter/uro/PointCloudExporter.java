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
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.AbstractPointCloud;
import org.citygml4j.ade.iur.model.uro.PointCloud;
import org.citygml4j.ade.iur.model.uro.PointCloudSpecAttribute;
import org.citygml4j.ade.iur.model.uro.PointCloudSpecAttributeProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class PointCloudExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final PreparedStatement ps;
    private final PointCloudSpecAttributeExporter pointCloudSpecAttributeExporter;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public PointCloudExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.POINTCLOUD);
        pointCloudSpecAttributeExporter = manager.getExporter(PointCloudSpecAttributeExporter.class);
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select()
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(PointCloud pointCloud, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                doExport(pointCloud, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("mimetype", prefix + "mimetype"),
                table.getColumn("mimetype_codespace", prefix + "mimetype_codespace"),
                table.getColumn("pointfile", prefix + "pointfile"),
                table.getColumn("pointfilesrsname", prefix + "pointfilesrsname"),
                table.getColumn("pointcloudspecattribute_id", prefix + "pointcloudspecattribute_id"),
                helper.getGeometryColumn(table.getColumn("points"), prefix + "points"));

        return select;
    }

    public void doExport(AbstractPointCloud abstractPointCloud, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        if (abstractPointCloud instanceof PointCloud) {
            PointCloud pointCloud = (PointCloud) abstractPointCloud;

            String mimeType = rs.getString(prefix + "mimetype");
            if (!rs.wasNull()) {
                Code code = new Code(mimeType);
                code.setCodeSpace(rs.getString(prefix + "mimetype_codespace"));
                pointCloud.setMimeType(code);
            }

            pointCloud.setPointFile(rs.getString(prefix + "pointfile"));

            pointCloud.setPointFileSrsName(rs.getString(prefix + "pointfilesrsname"));

            Object pointsObject = rs.getObject(prefix + "points");
            if (!rs.wasNull()) {
                GeometryObject points = helper.getDatabaseAdapter().getGeometryConverter().getMultiPoint(pointsObject);
                if (points != null)
                    pointCloud.setPoints(gmlConverter.getMultiPointProperty(points));
            }

            long pointCloudSpecAttributeId = rs.getLong(prefix + "pointcloudspecattribute_id");
            if (pointCloudSpecAttributeId != 0) {
                PointCloudSpecAttribute pointCloudSpecAttribute = new PointCloudSpecAttribute();
                pointCloudSpecAttributeExporter.doExport(pointCloudSpecAttribute, pointCloudSpecAttributeId);
                pointCloud.setPointCloudSpecAttribute(new PointCloudSpecAttributeProperty(pointCloudSpecAttribute));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
