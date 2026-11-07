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
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.DmAnnotation;
import org.citygml4j.ade.iur.model.uro.DmAttribute;
import org.citygml4j.ade.iur.model.uro.DmElement;
import org.citygml4j.ade.iur.model.uro.DmElementProperty;
import org.citygml4j.ade.iur.model.uro.DmGeometricAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.*;

public class DmAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final Map<String, PreparedStatement> psByParent = new HashMap<>();
    private final DmElementExporter dmElementExporter;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public DmAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DMATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table dmGeometricAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.DMGEOMETRICATTRIBUTE)));
        Table dmAnnotation = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.DMANNOTATION)));
        for (String parentColumn : Arrays.asList("bridge_briddmattribute_id", "city_furniture_frndmattri_id", "cityobject_trandmattribut_id", "cityobject_vegdmattribute_id", "tunnel_tundmattribute_id", "building_bldgdmattribute_id", "land_use_lusedmattribute_id", "waterbody_wtrdmattribute_id", "relief_component_demdmatt_id")) {
            Select parentSelect = new Select().addProjection(table.getColumns("id", "objectclass_id", "dmcode", "dmcode_codespace", "meshcode", "meshcode_codespace",
                            "dmelement_id"))
                    .addProjection(dmGeometricAttribute.getColumns("maplevel", "maplevel_codespace", "visibility", "is3d", "isinstallation", "isedited",
                            "issupplementarysymbol", "angle", "elevation", "elevation_uom", "lod0geometry_brep_id"))
                    .addProjection(dmAnnotation.getColumns("label", "isvertical", "size_", "orientation", "linewidth", "spacing", "lod0anchorpoint_brep_id"))
                    .addProjection(dmGeometricAttribute.getColumn("geometrytype", "dmgeometricattribute_geometrytype"))
                    .addProjection(dmGeometricAttribute.getColumn("geometrytype_codespace", "dmgeometricattribute_geometrytype_codespace"))
                    .addProjection(dmGeometricAttribute.getColumn("shapetype", "dmgeometricattribute_shapetype"))
                    .addProjection(dmGeometricAttribute.getColumn("shapetype_codespace", "dmgeometricattribute_shapetype_codespace"))
                    .addProjection(dmAnnotation.getColumn("geometrytype", "dmannotation_geometrytype"))
                    .addProjection(dmAnnotation.getColumn("geometrytype_codespace", "dmannotation_geometrytype_codespace"))
                    .addProjection(dmAnnotation.getColumn("shapetype", "dmannotation_shapetype"))
                    .addProjection(dmAnnotation.getColumn("shapetype_codespace", "dmannotation_shapetype_codespace"))
                    .addProjection(helper.getGeometryColumn(dmGeometricAttribute.getColumn("lod0geometry_other_geom")))
                    .addProjection(helper.getGeometryColumn(dmAnnotation.getColumn("lod0anchorpoint_other_geom")))
                    .addJoin(JoinFactory.left(dmGeometricAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(dmAnnotation, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addSelection(ComparisonFactory.equalTo(table.getColumn(parentColumn), new PlaceHolder<>()))
                    .addOrderBy(new OrderByToken(table.getColumn("id")));
            psByParent.put(parentColumn, connection.prepareStatement(parentSelect.toString()));
        }

        dmElementExporter = manager.getExporter(DmElementExporter.class);
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();
    }

    public List<DmAttribute> doExport(long parentId, String parentColumn) throws CityGMLExportException, SQLException {
        PreparedStatement ps = psByParent.get(parentColumn);
        ps.setLong(1, parentId);

        List<DmAttribute> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DmAttribute dmAttribute = doExport(rs.getLong("id"), rs);
                if (dmAttribute != null)
                    result.add(dmAttribute);
            }
        }

        return result;
    }

    private DmAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        DmAttribute dmAttribute = objectMapper.createType(objectClassId, DmAttribute.class);
        if (dmAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate dm attribute (id: " + objectId + ").");
            return null;
        }

        String dmCode = rs.getString("dmcode");
        if (!rs.wasNull()) {
            Code code = new Code(dmCode);
            code.setCodeSpace(rs.getString("dmcode_codespace"));
            dmAttribute.setDmCode(code);
        }

        String meshCode = rs.getString("meshcode");
        if (!rs.wasNull()) {
            Code code = new Code(meshCode);
            code.setCodeSpace(rs.getString("meshcode_codespace"));
            dmAttribute.setMeshCode(code);
        }

        long dmElementId = rs.getLong("dmelement_id");
        if (dmElementId != 0) {
            DmElement dmElement = new DmElement();
            dmElementExporter.doExport(dmElement, dmElementId);
            dmAttribute.setDmElement(new DmElementProperty(dmElement));
        }

        if (dmAttribute instanceof DmGeometricAttribute) {
            DmGeometricAttribute dmGeometricAttribute = (DmGeometricAttribute) dmAttribute;

            String geometryType = rs.getString("dmgeometricattribute_geometrytype");
            if (!rs.wasNull()) {
                Code code = new Code(geometryType);
                code.setCodeSpace(rs.getString("dmgeometricattribute_geometrytype_codespace"));
                dmGeometricAttribute.setGeometryType(code);
            }

            String mapLevel = rs.getString("maplevel");
            if (!rs.wasNull()) {
                Code code = new Code(mapLevel);
                code.setCodeSpace(rs.getString("maplevel_codespace"));
                dmGeometricAttribute.setMapLevel(code);
            }

            String shapeType = rs.getString("dmgeometricattribute_shapetype");
            if (!rs.wasNull()) {
                Code code = new Code(shapeType);
                code.setCodeSpace(rs.getString("dmgeometricattribute_shapetype_codespace"));
                dmGeometricAttribute.setShapeType(code);
            }

            boolean visibility = rs.getBoolean("visibility");
            if (!rs.wasNull())
                dmGeometricAttribute.setVisibility(visibility);

            boolean is3d = rs.getBoolean("is3d");
            if (!rs.wasNull())
                dmGeometricAttribute.setIs3d(is3d);

            boolean isInstallation = rs.getBoolean("isinstallation");
            if (!rs.wasNull())
                dmGeometricAttribute.setIsInstallation(isInstallation);

            boolean isEdited = rs.getBoolean("isedited");
            if (!rs.wasNull())
                dmGeometricAttribute.setIsEdited(isEdited);

            boolean isSupplementarySymbol = rs.getBoolean("issupplementarysymbol");
            if (!rs.wasNull())
                dmGeometricAttribute.setIsSupplementarySymbol(isSupplementarySymbol);

            double angle = rs.getDouble("angle");
            if (!rs.wasNull())
                dmGeometricAttribute.setAngle(angle);

            double elevation = rs.getDouble("elevation");
            if (!rs.wasNull()) {
                Length length = new Length(elevation);
                length.setUom(rs.getString("elevation_uom"));
                dmGeometricAttribute.setElevation(length);
            }

            long lod0GeometryId = rs.getLong("lod0geometry_brep_id");
            if (lod0GeometryId != 0)
                surfaceGeometryExporter.addBatch(lod0GeometryId, (GeometrySetter.AbstractGeometry) geometry -> dmGeometricAttribute.setLod0Geometry(geometry));
            else {
                Object lod0GeometryObject = rs.getObject("lod0geometry_other_geom");
                if (!rs.wasNull()) {
                    GeometryObject lod0Geometry = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod0GeometryObject);
                    if (lod0Geometry != null)
                        dmGeometricAttribute.setLod0Geometry(gmlConverter.getPointOrCurveGeometryProperty(lod0Geometry));
                }
            }
        }

        if (dmAttribute instanceof DmAnnotation) {
            DmAnnotation dmAnnotation = (DmAnnotation) dmAttribute;

            String geometryType = rs.getString("dmannotation_geometrytype");
            if (!rs.wasNull()) {
                Code code = new Code(geometryType);
                code.setCodeSpace(rs.getString("dmannotation_geometrytype_codespace"));
                dmAnnotation.setGeometryType(code);
            }

            String shapeType = rs.getString("dmannotation_shapetype");
            if (!rs.wasNull()) {
                Code code = new Code(shapeType);
                code.setCodeSpace(rs.getString("dmannotation_shapetype_codespace"));
                dmAnnotation.setShapeType(code);
            }

            dmAnnotation.setLabel(rs.getString("label"));

            boolean isVertical = rs.getBoolean("isvertical");
            if (!rs.wasNull())
                dmAnnotation.setIsVertical(isVertical);

            int size = rs.getInt("size_");
            if (!rs.wasNull())
                dmAnnotation.setSize(size);

            int orientation = rs.getInt("orientation");
            if (!rs.wasNull())
                dmAnnotation.setOrientation(orientation);

            int linewidth = rs.getInt("linewidth");
            if (!rs.wasNull())
                dmAnnotation.setLinewidth(linewidth);

            int spacing = rs.getInt("spacing");
            if (!rs.wasNull())
                dmAnnotation.setSpacing(spacing);

            long lod0anchorPointId = rs.getLong("lod0anchorpoint_brep_id");
            if (lod0anchorPointId != 0)
                surfaceGeometryExporter.addBatch(lod0anchorPointId, (GeometrySetter.AbstractGeometry) geometry -> dmAnnotation.setLod0anchorPoint(geometry));
            else {
                Object lod0anchorPointObject = rs.getObject("lod0anchorpoint_other_geom");
                if (!rs.wasNull()) {
                    GeometryObject lod0anchorPoint = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod0anchorPointObject);
                    if (lod0anchorPoint != null)
                        dmAnnotation.setLod0anchorPoint(gmlConverter.getPointOrCurveGeometryProperty(lod0anchorPoint));
                }
            }
        }
        return dmAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        for (PreparedStatement ps : psByParent.values())
            ps.close();
    }
}
