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
import org.citygml4j.ade.iur.model.uro.LandUseDetailAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.time.Year;

import java.sql.*;

public class LandUseDetailAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public LandUseDetailAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LANDUSEATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("id_1", "orglanduse", "orglanduse_codespace", "nominalarea", "nominalarea_uom", "ownertype",
                        "ownertype_codespace", "owner", "areainsquaremeter", "areainsquaremeter_uom", "areainha",
                        "areainha_uom", "buildingcoveragerate", "floorarearate", "specifiedbuildingcoveragerat",
                        "specifiedfloorarearate", "standardfloorarearate", "urbanplantype", "urbanplantype_codespace",
                        "areaclassificationtype", "areaclassification_codespace", "districtsandzonestype",
                        "districtsandzonest_codespace", "prefecture", "prefecture_codespace", "city", "city_codespace",
                        "reference", "note", "surveyyear"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(LandUseDetailAttribute landUseDetailAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                landUseDetailAttribute.setId(rs.getString("id_1"));

                String orgLandUse = rs.getString("orglanduse");
                if (!rs.wasNull()) {
                    Code code = new Code(orgLandUse);
                    code.setCodeSpace(rs.getString("orglanduse_codespace"));
                    landUseDetailAttribute.setOrgLandUse(code);
                }

                double nominalArea = rs.getDouble("nominalarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(nominalArea);
                    measure.setUom(rs.getString("nominalarea_uom"));
                    landUseDetailAttribute.setNominalArea(measure);
                }

                String ownerType = rs.getString("ownertype");
                if (!rs.wasNull()) {
                    Code code = new Code(ownerType);
                    code.setCodeSpace(rs.getString("ownertype_codespace"));
                    landUseDetailAttribute.setOwnerType(code);
                }

                landUseDetailAttribute.setOwner(rs.getString("owner"));

                double areaInSquareMeter = rs.getDouble("areainsquaremeter");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(areaInSquareMeter);
                    measure.setUom(rs.getString("areainsquaremeter_uom"));
                    landUseDetailAttribute.setAreaInSquareMeter(measure);
                }

                double areaInHa = rs.getDouble("areainha");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(areaInHa);
                    measure.setUom(rs.getString("areainha_uom"));
                    landUseDetailAttribute.setAreaInHa(measure);
                }

                double buildingCoverageRate = rs.getDouble("buildingcoveragerate");
                if (!rs.wasNull())
                    landUseDetailAttribute.setBuildingCoverageRate(buildingCoverageRate);

                double floorAreaRate = rs.getDouble("floorarearate");
                if (!rs.wasNull())
                    landUseDetailAttribute.setFloorAreaRate(floorAreaRate);

                double specifiedBuildingCoverageRate = rs.getDouble("specifiedbuildingcoveragerat");
                if (!rs.wasNull())
                    landUseDetailAttribute.setSpecifiedBuildingCoverageRate(specifiedBuildingCoverageRate);

                double specifiedFloorAreaRate = rs.getDouble("specifiedfloorarearate");
                if (!rs.wasNull())
                    landUseDetailAttribute.setSpecifiedFloorAreaRate(specifiedFloorAreaRate);

                double standardFloorAreaRate = rs.getDouble("standardfloorarearate");
                if (!rs.wasNull())
                    landUseDetailAttribute.setStandardFloorAreaRate(standardFloorAreaRate);

                String urbanPlanType = rs.getString("urbanplantype");
                if (!rs.wasNull()) {
                    Code code = new Code(urbanPlanType);
                    code.setCodeSpace(rs.getString("urbanplantype_codespace"));
                    landUseDetailAttribute.setUrbanPlanType(code);
                }

                String areaClassificationType = rs.getString("areaclassificationtype");
                if (!rs.wasNull()) {
                    Code code = new Code(areaClassificationType);
                    code.setCodeSpace(rs.getString("areaclassification_codespace"));
                    landUseDetailAttribute.setAreaClassificationType(code);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("districtsandzonestype"), rs.getString("districtsandzonest_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    landUseDetailAttribute.getDistrictsAndZonesType().add(code);
                }

                String prefecture = rs.getString("prefecture");
                if (!rs.wasNull()) {
                    Code code = new Code(prefecture);
                    code.setCodeSpace(rs.getString("prefecture_codespace"));
                    landUseDetailAttribute.setPrefecture(code);
                }

                String city = rs.getString("city");
                if (!rs.wasNull()) {
                    Code code = new Code(city);
                    code.setCodeSpace(rs.getString("city_codespace"));
                    landUseDetailAttribute.setCity(code);
                }

                landUseDetailAttribute.setReference(rs.getString("reference"));

                landUseDetailAttribute.setNote(rs.getString("note"));

                Date surveyYear = rs.getDate("surveyyear");
                if (!rs.wasNull())
                    landUseDetailAttribute.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
