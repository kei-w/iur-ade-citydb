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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.PointCloudSpecAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class PointCloudSpecAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public PointCloudSpecAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.POINTCLOUDSPECATTRIBUT);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("class", "class_codespace", "function", "function_codespace", "srcscale", "srcscale_codespace",
                        "srcrequiredhorizontalaccurac", "srcrequiredhorizontalacc_uom", "srcrequiredverticalaccuracy",
                        "srcrequiredverticalaccur_uom", "srcrequiredpointdensity", "srcrequiredpointdensity_uom",
                        "srcfilteringcriteria", "srcfilteringcriter_codespace", "srcgridinterval", "srcgridinterval_uom",
                        "isintensity", "spectralbandset", "spectralbandset_codespace", "isreturninfo",
                        "isstandardclassification", "customclassificationtype", "customclassificati_codespace",
                        "pointcloudassociationmethod", "pointcloudassociat_codespace", "pointsubsamplinginfo",
                        "pointsubsamplingin_codespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(PointCloudSpecAttribute pointCloudSpecAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String classifier = rs.getString("class");
                if (!rs.wasNull()) {
                    Code code = new Code(classifier);
                    code.setCodeSpace(rs.getString("class_codespace"));
                    pointCloudSpecAttribute.setClassifier(code);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("function"), rs.getString("function_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    pointCloudSpecAttribute.getFunction().add(code);
                }

                String srcScale = rs.getString("srcscale");
                if (!rs.wasNull()) {
                    Code code = new Code(srcScale);
                    code.setCodeSpace(rs.getString("srcscale_codespace"));
                    pointCloudSpecAttribute.setSrcScale(code);
                }

                double srcRequiredHorizontalAccuracy = rs.getDouble("srcrequiredhorizontalaccurac");
                if (!rs.wasNull()) {
                    Length length = new Length(srcRequiredHorizontalAccuracy);
                    length.setUom(rs.getString("srcrequiredhorizontalacc_uom"));
                    pointCloudSpecAttribute.setSrcRequiredHorizontalAccuracy(length);
                }

                double srcRequiredVerticalAccuracy = rs.getDouble("srcrequiredverticalaccuracy");
                if (!rs.wasNull()) {
                    Length length = new Length(srcRequiredVerticalAccuracy);
                    length.setUom(rs.getString("srcrequiredverticalaccur_uom"));
                    pointCloudSpecAttribute.setSrcRequiredVerticalAccuracy(length);
                }

                double srcRequiredPointDensity = rs.getDouble("srcrequiredpointdensity");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(srcRequiredPointDensity);
                    measure.setUom(rs.getString("srcrequiredpointdensity_uom"));
                    pointCloudSpecAttribute.setSrcRequiredPointDensity(measure);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("srcfilteringcriteria"), rs.getString("srcfilteringcriter_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    pointCloudSpecAttribute.getSrcFilteringCriteria().add(code);
                }

                double srcGridInterval = rs.getDouble("srcgridinterval");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(srcGridInterval);
                    measure.setUom(rs.getString("srcgridinterval_uom"));
                    pointCloudSpecAttribute.setSrcGridInterval(measure);
                }

                boolean isIntensity = rs.getBoolean("isintensity");
                if (!rs.wasNull())
                    pointCloudSpecAttribute.setIsIntensity(isIntensity);

                for (SplitValue splitValue : valueSplitter.split(rs.getString("spectralbandset"), rs.getString("spectralbandset_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    pointCloudSpecAttribute.getSpectralBandSet().add(code);
                }

                boolean isReturnInfo = rs.getBoolean("isreturninfo");
                if (!rs.wasNull())
                    pointCloudSpecAttribute.setIsReturnInfo(isReturnInfo);

                boolean isStandardClassification = rs.getBoolean("isstandardclassification");
                if (!rs.wasNull())
                    pointCloudSpecAttribute.setIsStandardClassification(isStandardClassification);

                for (SplitValue splitValue : valueSplitter.split(rs.getString("customclassificationtype"), rs.getString("customclassificati_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    pointCloudSpecAttribute.getCustomClassificationType().add(code);
                }

                String pointCloudAssociationMethod = rs.getString("pointcloudassociationmethod");
                if (!rs.wasNull()) {
                    Code code = new Code(pointCloudAssociationMethod);
                    code.setCodeSpace(rs.getString("pointcloudassociat_codespace"));
                    pointCloudSpecAttribute.setPointCloudAssociationMethod(code);
                }

                String pointSubsamplingInfo = rs.getString("pointsubsamplinginfo");
                if (!rs.wasNull()) {
                    Code code = new Code(pointSubsamplingInfo);
                    code.setCodeSpace(rs.getString("pointsubsamplingin_codespace"));
                    pointCloudSpecAttribute.setPointSubsamplingInfo(code);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
