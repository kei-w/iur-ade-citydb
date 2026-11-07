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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.ControlPoint;
import org.citygml4j.ade.iur.model.uro.ControlPointProperty;
import org.citygml4j.ade.iur.model.uro.RailwayTrackAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.complexes.GeometricComplex;
import org.citygml4j.model.gml.geometry.complexes.GeometricComplexProperty;

import java.sql.*;

public class RailwayTrackAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final PreparedStatement ps;
    private final ControlPointExporter controlPointExporter;
    private final AttributeValueSplitter valueSplitter;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public RailwayTrackAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.RAILWAYTRACKATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("routename", "directiontype", "directiontype_codespace", "tracktype", "tracktype_codespace",
                        "startpost", "endpost", "alignmenttype", "alignmenttype_codespace", "lod2network_brep_id",
                        "lod3network_brep_id"))
                .addProjection(helper.getGeometryColumn(table.getColumn("lod2network_other_geom")))
                .addProjection(helper.getGeometryColumn(table.getColumn("lod3network_other_geom")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        controlPointExporter = manager.getExporter(ControlPointExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();
    }

    public void doExport(RailwayTrackAttribute railwayTrackAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                railwayTrackAttribute.setRouteName(rs.getString("routename"));

                String directionType = rs.getString("directiontype");
                if (!rs.wasNull()) {
                    Code code = new Code(directionType);
                    code.setCodeSpace(rs.getString("directiontype_codespace"));
                    railwayTrackAttribute.setDirectionType(code);
                }

                String trackType = rs.getString("tracktype");
                if (!rs.wasNull()) {
                    Code code = new Code(trackType);
                    code.setCodeSpace(rs.getString("tracktype_codespace"));
                    railwayTrackAttribute.setTrackType(code);
                }

                railwayTrackAttribute.setStartPost(rs.getString("startpost"));

                railwayTrackAttribute.setEndPost(rs.getString("endpost"));

                String alignmentType = rs.getString("alignmenttype");
                if (!rs.wasNull()) {
                    Code code = new Code(alignmentType);
                    code.setCodeSpace(rs.getString("alignmenttype_codespace"));
                    railwayTrackAttribute.setAlignmentType(code);
                }

                long lod2NetworkId = rs.getLong("lod2network_brep_id");
                if (lod2NetworkId != 0)
                    surfaceGeometryExporter.addBatch(lod2NetworkId, (GeometrySetter.AbstractGeometry) geometry -> {
                        if (geometry.getGeometry() instanceof GeometricComplex)
                            railwayTrackAttribute.setLod2Network(new GeometricComplexProperty((GeometricComplex) geometry.getGeometry()));
                    });
                else {
                    Object lod2NetworkObject = rs.getObject("lod2network_other_geom");
                    if (!rs.wasNull()) {
                        GeometryObject lod2Network = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod2NetworkObject);
                        if (lod2Network != null)
                            railwayTrackAttribute.setLod2Network(gmlConverter.getPointOrCurveComplexProperty(lod2Network));
                    }
                }

                long lod3NetworkId = rs.getLong("lod3network_brep_id");
                if (lod3NetworkId != 0)
                    surfaceGeometryExporter.addBatch(lod3NetworkId, (GeometrySetter.AbstractGeometry) geometry -> {
                        if (geometry.getGeometry() instanceof GeometricComplex)
                            railwayTrackAttribute.setLod3Network(new GeometricComplexProperty((GeometricComplex) geometry.getGeometry()));
                    });
                else {
                    Object lod3NetworkObject = rs.getObject("lod3network_other_geom");
                    if (!rs.wasNull()) {
                        GeometryObject lod3Network = helper.getDatabaseAdapter().getGeometryConverter().getGeometry(lod3NetworkObject);
                        if (lod3Network != null)
                            railwayTrackAttribute.setLod3Network(gmlConverter.getPointOrCurveComplexProperty(lod3Network));
                    }
                }

                for (ControlPoint controlPoint : controlPointExporter.doExport(objectId))
                    railwayTrackAttribute.getControlPoint().add(new ControlPointProperty(controlPoint));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
