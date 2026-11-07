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

package org.citydb.ade.iur.exporter.urg;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citygml4j.ade.iur.model.urg.StatisticalGrid;
import org.citygml4j.model.gml.basicTypes.Code;

import java.time.Year;

import java.sql.*;

public class StatisticalGridExporter implements StatisticalGridModuleExporter {
    private final CityGMLExportHelper helper;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public StatisticalGridExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();

    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("class", prefix + "class"),
                table.getColumn("class_codespace", prefix + "class_codespace"),
                table.getColumn("urbanplantype", prefix + "urbanplantype"),
                table.getColumn("urbanplantype_codespace", prefix + "urbanplantype_codespace"),
                table.getColumn("areaclassificationtype", prefix + "areaclassificationtype"),
                table.getColumn("areaclassification_codespace", prefix + "areaclassification_codespace"),
                table.getColumn("prefecture", prefix + "prefecture"),
                table.getColumn("prefecture_codespace", prefix + "prefecture_codespace"),
                table.getColumn("city", prefix + "city"),
                table.getColumn("city_codespace", prefix + "city_codespace"),
                table.getColumn("surveyyear", prefix + "surveyyear"),
                table.getColumn("referencedate", prefix + "referencedate"),
                table.getColumn("lod_1multisurface_id", prefix + "lod_1multisurface_id"),
                table.getColumn("lod_2multisurface_id", prefix + "lod_2multisurface_id"));

        return select;
    }

    public void doExport(StatisticalGrid statisticalGrid, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String classifier = rs.getString(prefix + "class");
        if (!rs.wasNull()) {
            Code code = new Code(classifier);
            code.setCodeSpace(rs.getString(prefix + "class_codespace"));
            statisticalGrid.setClassifier(code);
        }

        String urbanPlanType = rs.getString(prefix + "urbanplantype");
        if (!rs.wasNull()) {
            Code code = new Code(urbanPlanType);
            code.setCodeSpace(rs.getString(prefix + "urbanplantype_codespace"));
            statisticalGrid.setUrbanPlanType(code);
        }

        String areaClassificationType = rs.getString(prefix + "areaclassificationtype");
        if (!rs.wasNull()) {
            Code code = new Code(areaClassificationType);
            code.setCodeSpace(rs.getString(prefix + "areaclassification_codespace"));
            statisticalGrid.setAreaClassificationType(code);
        }

        String prefecture = rs.getString(prefix + "prefecture");
        if (!rs.wasNull()) {
            Code code = new Code(prefecture);
            code.setCodeSpace(rs.getString(prefix + "prefecture_codespace"));
            statisticalGrid.setPrefecture(code);
        }

        String city = rs.getString(prefix + "city");
        if (!rs.wasNull()) {
            Code code = new Code(city);
            code.setCodeSpace(rs.getString(prefix + "city_codespace"));
            statisticalGrid.setCity(code);
        }

        Date surveyYear = rs.getDate(prefix + "surveyyear");
        if (!rs.wasNull())
            statisticalGrid.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));

        Date referenceDate = rs.getDate(prefix + "referencedate");
        if (!rs.wasNull())
            statisticalGrid.setReferenceDate(referenceDate.toLocalDate());

        long lodMinus1MultiSurfaceId = rs.getLong(prefix + "lod_1multisurface_id");
        if (!rs.wasNull())
            surfaceGeometryExporter.addBatch(lodMinus1MultiSurfaceId, statisticalGrid::setLodMinus1MultiSurface);

        long lodMinus2MultiSurfaceId = rs.getLong(prefix + "lod_2multisurface_id");
        if (!rs.wasNull())
            surfaceGeometryExporter.addBatch(lodMinus2MultiSurfaceId, statisticalGrid::setLodMinus2MultiSurface);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
    }
}
