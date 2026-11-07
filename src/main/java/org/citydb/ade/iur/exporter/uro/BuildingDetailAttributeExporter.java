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
import org.citygml4j.ade.iur.model.uro.BuildingDetailAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.time.Year;

import java.sql.*;

public class BuildingDetailAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public BuildingDetailAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.BUILDINGDETAILATTRIBUT);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("serialnumberofbuildingcertif", "sitearea", "sitearea_uom", "totalfloorarea", "totalfloorarea_uom",
                        "buildingfootprintarea", "buildingfootprintarea_uom", "buildingroofedgearea",
                        "buildingroofedgearea_uom", "developmentarea", "developmentarea_uom", "buildingstructuretype",
                        "buildingstructuret_codespace", "buildingstructureorgtype", "buildingstructureo_codespace",
                        "fireproofstructuretype", "fireproofstructure_codespace", "implementingbody", "urbanplantype",
                        "urbanplantype_codespace", "areaclassificationtype", "areaclassification_codespace",
                        "districtsandzonestype", "districtsandzonest_codespace", "landusetype", "landusetype_codespace",
                        "reference", "majorusage", "majorusage_codespace", "majorusage2", "majorusage2_codespace",
                        "orgusage", "orgusage_codespace", "orgusage2", "orgusage2_codespace", "detailedusage",
                        "detailedusage_codespace", "detailedusage2", "detailedusage2_codespace", "detailedusage3",
                        "detailedusage3_codespace", "groundfloorusage", "groundfloorusage_codespace", "secondfloorusage",
                        "secondfloorusage_codespace", "thirdfloorusage", "thirdfloorusage_codespace", "basementusage",
                        "basementusage_codespace", "basementfirstusage", "basementfirstusage_codespace",
                        "basementsecondusage", "basementsecondusag_codespace", "vacancy", "vacancy_codespace",
                        "buildingcoveragerate", "floorarearate", "specifiedbuildingcoveragerat", "specifiedfloorarearate",
                        "standardfloorarearate", "buildingheight", "buildingheight_uom", "eaveheight", "eaveheight_uom",
                        "note", "surveyyear"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(BuildingDetailAttribute buildingDetailAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                buildingDetailAttribute.setSerialNumberOfBuildingCertification(rs.getString("serialnumberofbuildingcertif"));

                double siteArea = rs.getDouble("sitearea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(siteArea);
                    measure.setUom(rs.getString("sitearea_uom"));
                    buildingDetailAttribute.setSiteArea(measure);
                }

                double totalFloorArea = rs.getDouble("totalfloorarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(totalFloorArea);
                    measure.setUom(rs.getString("totalfloorarea_uom"));
                    buildingDetailAttribute.setTotalFloorArea(measure);
                }

                double buildingFootprintArea = rs.getDouble("buildingfootprintarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(buildingFootprintArea);
                    measure.setUom(rs.getString("buildingfootprintarea_uom"));
                    buildingDetailAttribute.setBuildingFootprintArea(measure);
                }

                double buildingRoofEdgeArea = rs.getDouble("buildingroofedgearea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(buildingRoofEdgeArea);
                    measure.setUom(rs.getString("buildingroofedgearea_uom"));
                    buildingDetailAttribute.setBuildingRoofEdgeArea(measure);
                }

                double developmentArea = rs.getDouble("developmentarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(developmentArea);
                    measure.setUom(rs.getString("developmentarea_uom"));
                    buildingDetailAttribute.setDevelopmentArea(measure);
                }

                String buildingStructureType = rs.getString("buildingstructuretype");
                if (!rs.wasNull()) {
                    Code code = new Code(buildingStructureType);
                    code.setCodeSpace(rs.getString("buildingstructuret_codespace"));
                    buildingDetailAttribute.setBuildingStructureType(code);
                }

                String buildingStructureOrgType = rs.getString("buildingstructureorgtype");
                if (!rs.wasNull()) {
                    Code code = new Code(buildingStructureOrgType);
                    code.setCodeSpace(rs.getString("buildingstructureo_codespace"));
                    buildingDetailAttribute.setBuildingStructureOrgType(code);
                }

                String fireproofStructureType = rs.getString("fireproofstructuretype");
                if (!rs.wasNull()) {
                    Code code = new Code(fireproofStructureType);
                    code.setCodeSpace(rs.getString("fireproofstructure_codespace"));
                    buildingDetailAttribute.setFireproofStructureType(code);
                }

                buildingDetailAttribute.setImplementingBody(rs.getString("implementingbody"));

                String urbanPlanType = rs.getString("urbanplantype");
                if (!rs.wasNull()) {
                    Code code = new Code(urbanPlanType);
                    code.setCodeSpace(rs.getString("urbanplantype_codespace"));
                    buildingDetailAttribute.setUrbanPlanType(code);
                }

                String areaClassificationType = rs.getString("areaclassificationtype");
                if (!rs.wasNull()) {
                    Code code = new Code(areaClassificationType);
                    code.setCodeSpace(rs.getString("areaclassification_codespace"));
                    buildingDetailAttribute.setAreaClassificationType(code);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("districtsandzonestype"), rs.getString("districtsandzonest_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    buildingDetailAttribute.getDistrictsAndZonesType().add(code);
                }

                String landUseType = rs.getString("landusetype");
                if (!rs.wasNull()) {
                    Code code = new Code(landUseType);
                    code.setCodeSpace(rs.getString("landusetype_codespace"));
                    buildingDetailAttribute.setLandUseType(code);
                }

                buildingDetailAttribute.setReference(rs.getString("reference"));

                String majorUsage = rs.getString("majorusage");
                if (!rs.wasNull()) {
                    Code code = new Code(majorUsage);
                    code.setCodeSpace(rs.getString("majorusage_codespace"));
                    buildingDetailAttribute.setMajorUsage(code);
                }

                String majorUsage2 = rs.getString("majorusage2");
                if (!rs.wasNull()) {
                    Code code = new Code(majorUsage2);
                    code.setCodeSpace(rs.getString("majorusage2_codespace"));
                    buildingDetailAttribute.setMajorUsage2(code);
                }

                String orgUsage = rs.getString("orgusage");
                if (!rs.wasNull()) {
                    Code code = new Code(orgUsage);
                    code.setCodeSpace(rs.getString("orgusage_codespace"));
                    buildingDetailAttribute.setOrgUsage(code);
                }

                String orgUsage2 = rs.getString("orgusage2");
                if (!rs.wasNull()) {
                    Code code = new Code(orgUsage2);
                    code.setCodeSpace(rs.getString("orgusage2_codespace"));
                    buildingDetailAttribute.setOrgUsage2(code);
                }

                String detailedUsage = rs.getString("detailedusage");
                if (!rs.wasNull()) {
                    Code code = new Code(detailedUsage);
                    code.setCodeSpace(rs.getString("detailedusage_codespace"));
                    buildingDetailAttribute.setDetailedUsage(code);
                }

                String detailedUsage2 = rs.getString("detailedusage2");
                if (!rs.wasNull()) {
                    Code code = new Code(detailedUsage2);
                    code.setCodeSpace(rs.getString("detailedusage2_codespace"));
                    buildingDetailAttribute.setDetailedUsage2(code);
                }

                String detailedUsage3 = rs.getString("detailedusage3");
                if (!rs.wasNull()) {
                    Code code = new Code(detailedUsage3);
                    code.setCodeSpace(rs.getString("detailedusage3_codespace"));
                    buildingDetailAttribute.setDetailedUsage3(code);
                }

                String groundFloorUsage = rs.getString("groundfloorusage");
                if (!rs.wasNull()) {
                    Code code = new Code(groundFloorUsage);
                    code.setCodeSpace(rs.getString("groundfloorusage_codespace"));
                    buildingDetailAttribute.setGroundFloorUsage(code);
                }

                String secondFloorUsage = rs.getString("secondfloorusage");
                if (!rs.wasNull()) {
                    Code code = new Code(secondFloorUsage);
                    code.setCodeSpace(rs.getString("secondfloorusage_codespace"));
                    buildingDetailAttribute.setSecondFloorUsage(code);
                }

                String thirdFloorUsage = rs.getString("thirdfloorusage");
                if (!rs.wasNull()) {
                    Code code = new Code(thirdFloorUsage);
                    code.setCodeSpace(rs.getString("thirdfloorusage_codespace"));
                    buildingDetailAttribute.setThirdFloorUsage(code);
                }

                String basementUsage = rs.getString("basementusage");
                if (!rs.wasNull()) {
                    Code code = new Code(basementUsage);
                    code.setCodeSpace(rs.getString("basementusage_codespace"));
                    buildingDetailAttribute.setBasementUsage(code);
                }

                String basementFirstUsage = rs.getString("basementfirstusage");
                if (!rs.wasNull()) {
                    Code code = new Code(basementFirstUsage);
                    code.setCodeSpace(rs.getString("basementfirstusage_codespace"));
                    buildingDetailAttribute.setBasementFirstUsage(code);
                }

                String basementSecondUsage = rs.getString("basementsecondusage");
                if (!rs.wasNull()) {
                    Code code = new Code(basementSecondUsage);
                    code.setCodeSpace(rs.getString("basementsecondusag_codespace"));
                    buildingDetailAttribute.setBasementSecondUsage(code);
                }

                String vacancy = rs.getString("vacancy");
                if (!rs.wasNull()) {
                    Code code = new Code(vacancy);
                    code.setCodeSpace(rs.getString("vacancy_codespace"));
                    buildingDetailAttribute.setVacancy(code);
                }

                double buildingCoverageRate = rs.getDouble("buildingcoveragerate");
                if (!rs.wasNull())
                    buildingDetailAttribute.setBuildingCoverageRate(buildingCoverageRate);

                double floorAreaRate = rs.getDouble("floorarearate");
                if (!rs.wasNull())
                    buildingDetailAttribute.setFloorAreaRate(floorAreaRate);

                double specifiedBuildingCoverageRate = rs.getDouble("specifiedbuildingcoveragerat");
                if (!rs.wasNull())
                    buildingDetailAttribute.setSpecifiedBuildingCoverageRate(specifiedBuildingCoverageRate);

                double specifiedFloorAreaRate = rs.getDouble("specifiedfloorarearate");
                if (!rs.wasNull())
                    buildingDetailAttribute.setSpecifiedFloorAreaRate(specifiedFloorAreaRate);

                double standardFloorAreaRate = rs.getDouble("standardfloorarearate");
                if (!rs.wasNull())
                    buildingDetailAttribute.setStandardFloorAreaRate(standardFloorAreaRate);

                double buildingHeight = rs.getDouble("buildingheight");
                if (!rs.wasNull()) {
                    Length length = new Length(buildingHeight);
                    length.setUom(rs.getString("buildingheight_uom"));
                    buildingDetailAttribute.setBuildingHeight(length);
                }

                double eaveHeight = rs.getDouble("eaveheight");
                if (!rs.wasNull()) {
                    Length length = new Length(eaveHeight);
                    length.setUom(rs.getString("eaveheight_uom"));
                    buildingDetailAttribute.setEaveHeight(length);
                }

                buildingDetailAttribute.setNote(rs.getString("note"));

                Date surveyYear = rs.getDate("surveyyear");
                if (!rs.wasNull())
                    buildingDetailAttribute.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
