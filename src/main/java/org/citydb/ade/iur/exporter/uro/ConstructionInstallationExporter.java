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
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.ConstructionInstallation;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class ConstructionInstallationExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public ConstructionInstallationExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CONSTRUCTIONINSTALLATI);
        valueSplitter = helper.getAttributeValueSplitter();
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select()
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(ConstructionInstallation constructionInstallation, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                doExport(constructionInstallation, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("class", prefix + "class"),
                table.getColumn("class_codespace", prefix + "class_codespace"),
                table.getColumn("function", prefix + "function"),
                table.getColumn("function_codespace", prefix + "function_codespace"),
                table.getColumn("usage", prefix + "usage"),
                table.getColumn("usage_codespace", prefix + "usage_codespace"),
                table.getColumn("lod2geometry_brep_id", prefix + "lod2geometry_brep_id"),
                table.getColumn("lod3geometry_brep_id", prefix + "lod3geometry_brep_id"),
                helper.getGeometryColumn(table.getColumn("lod2geometry_other_geom"), prefix + "lod2geometry_other_geom"),
                helper.getGeometryColumn(table.getColumn("lod3geometry_other_geom"), prefix + "lod3geometry_other_geom"));

        return select;
    }

    public void doExport(ConstructionInstallation constructionInstallation, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String classifier = rs.getString(prefix + "class");
        if (!rs.wasNull()) {
            Code code = new Code(classifier);
            code.setCodeSpace(rs.getString(prefix + "class_codespace"));
            constructionInstallation.setClassifier(code);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "function"), rs.getString(prefix + "function_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            constructionInstallation.getFunction().add(code);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "usage"), rs.getString(prefix + "usage_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            constructionInstallation.getUsage().add(code);
        }

        long lod2GeometryId = rs.getLong(prefix + "lod2geometry_brep_id");
        if (lod2GeometryId != 0)
            surfaceGeometryExporter.addBatch(lod2GeometryId, (GeometrySetter.AbstractGeometry) geometry -> constructionInstallation.setLod2Geometry(geometry));
        else {
            Object lod2GeometryObject = rs.getObject(prefix + "lod2geometry_other_geom");
            if (!rs.wasNull()) {
                GeometryObject lod2Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod2GeometryObject);
                if (lod2Geometry != null)
                    constructionInstallation.setLod2Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod2Geometry));
            }
        }

        long lod3GeometryId = rs.getLong(prefix + "lod3geometry_brep_id");
        if (lod3GeometryId != 0)
            surfaceGeometryExporter.addBatch(lod3GeometryId, (GeometrySetter.AbstractGeometry) geometry -> constructionInstallation.setLod3Geometry(geometry));
        else {
            Object lod3GeometryObject = rs.getObject(prefix + "lod3geometry_other_geom");
            if (!rs.wasNull()) {
                GeometryObject lod3Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod3GeometryObject);
                if (lod3Geometry != null)
                    constructionInstallation.setLod3Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod3Geometry));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
