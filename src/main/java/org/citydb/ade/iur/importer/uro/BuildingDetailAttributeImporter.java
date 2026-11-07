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

package org.citydb.ade.iur.importer.uro;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.BuildingDetailAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;
import java.time.LocalDate;

public class BuildingDetailAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final BuildingAttributeImporter buildingAttributeImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public BuildingDetailAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGDETAILATTRIBUT)) + " " +
                "(id, areaclassificationtype, areaclassification_codespace, basementfirstusage," +
                "basementfirstusage_codespace, basementsecondusage, basementsecondusag_codespace, basementusage," +
                "basementusage_codespace, buildingcoveragerate, buildingfootprintarea, buildingfootprintarea_uom," +
                "buildingheight, buildingheight_uom, buildingroofedgearea, buildingroofedgearea_uom," +
                "buildingstructureorgtype, buildingstructureo_codespace, buildingstructuretype," +
                "buildingstructuret_codespace, detailedusage, detailedusage_codespace, detailedusage2," +
                "detailedusage2_codespace, detailedusage3, detailedusage3_codespace, developmentarea," +
                "developmentarea_uom, districtsandzonestype, districtsandzonest_codespace, eaveheight," +
                "eaveheight_uom, fireproofstructuretype, fireproofstructure_codespace, floorarearate," +
                "groundfloorusage, groundfloorusage_codespace, implementingbody, landusetype, landusetype_codespace," +
                "majorusage, majorusage_codespace, majorusage2, majorusage2_codespace, note, orgusage," +
                "orgusage_codespace, orgusage2, orgusage2_codespace, reference, secondfloorusage," +
                "secondfloorusage_codespace, serialnumberofbuildingcertif, sitearea, sitearea_uom," +
                "specifiedbuildingcoveragerat, specifiedfloorarearate, standardfloorarearate, surveyyear," +
                "thirdfloorusage, thirdfloorusage_codespace, totalfloorarea, totalfloorarea_uom, urbanplantype," +
                "urbanplantype_codespace, vacancy, vacancy_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        buildingAttributeImporter = manager.getImporter(BuildingAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(BuildingDetailAttribute buildingDetailAttribute) throws CityGMLImportException, SQLException {
        long objectId = buildingAttributeImporter.doImport(buildingDetailAttribute);
        ps.setLong(1, objectId);

        if (buildingDetailAttribute.getAreaClassificationType() != null && buildingDetailAttribute.getAreaClassificationType().isSetValue()) {
            ps.setString(2, buildingDetailAttribute.getAreaClassificationType().getValue());
            ps.setString(3, buildingDetailAttribute.getAreaClassificationType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBasementFirstUsage() != null && buildingDetailAttribute.getBasementFirstUsage().isSetValue()) {
            ps.setString(4, buildingDetailAttribute.getBasementFirstUsage().getValue());
            ps.setString(5, buildingDetailAttribute.getBasementFirstUsage().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBasementSecondUsage() != null && buildingDetailAttribute.getBasementSecondUsage().isSetValue()) {
            ps.setString(6, buildingDetailAttribute.getBasementSecondUsage().getValue());
            ps.setString(7, buildingDetailAttribute.getBasementSecondUsage().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBasementUsage() != null && buildingDetailAttribute.getBasementUsage().isSetValue()) {
            ps.setString(8, buildingDetailAttribute.getBasementUsage().getValue());
            ps.setString(9, buildingDetailAttribute.getBasementUsage().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingCoverageRate() != null)
            ps.setDouble(10, buildingDetailAttribute.getBuildingCoverageRate());
        else
            ps.setNull(10, Types.DOUBLE);

        if (buildingDetailAttribute.getBuildingFootprintArea() != null && buildingDetailAttribute.getBuildingFootprintArea().isSetValue()) {
            ps.setDouble(11, buildingDetailAttribute.getBuildingFootprintArea().getValue());
            ps.setString(12, buildingDetailAttribute.getBuildingFootprintArea().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingHeight() != null && buildingDetailAttribute.getBuildingHeight().isSetValue()) {
            ps.setDouble(13, buildingDetailAttribute.getBuildingHeight().getValue());
            ps.setString(14, buildingDetailAttribute.getBuildingHeight().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingRoofEdgeArea() != null && buildingDetailAttribute.getBuildingRoofEdgeArea().isSetValue()) {
            ps.setDouble(15, buildingDetailAttribute.getBuildingRoofEdgeArea().getValue());
            ps.setString(16, buildingDetailAttribute.getBuildingRoofEdgeArea().getUom());
        } else {
            ps.setNull(15, Types.DOUBLE);
            ps.setNull(16, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingStructureOrgType() != null && buildingDetailAttribute.getBuildingStructureOrgType().isSetValue()) {
            ps.setString(17, buildingDetailAttribute.getBuildingStructureOrgType().getValue());
            ps.setString(18, buildingDetailAttribute.getBuildingStructureOrgType().getCodeSpace());
        } else {
            ps.setNull(17, Types.VARCHAR);
            ps.setNull(18, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingStructureType() != null && buildingDetailAttribute.getBuildingStructureType().isSetValue()) {
            ps.setString(19, buildingDetailAttribute.getBuildingStructureType().getValue());
            ps.setString(20, buildingDetailAttribute.getBuildingStructureType().getCodeSpace());
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDetailedUsage() != null && buildingDetailAttribute.getDetailedUsage().isSetValue()) {
            ps.setString(21, buildingDetailAttribute.getDetailedUsage().getValue());
            ps.setString(22, buildingDetailAttribute.getDetailedUsage().getCodeSpace());
        } else {
            ps.setNull(21, Types.VARCHAR);
            ps.setNull(22, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDetailedUsage2() != null && buildingDetailAttribute.getDetailedUsage2().isSetValue()) {
            ps.setString(23, buildingDetailAttribute.getDetailedUsage2().getValue());
            ps.setString(24, buildingDetailAttribute.getDetailedUsage2().getCodeSpace());
        } else {
            ps.setNull(23, Types.VARCHAR);
            ps.setNull(24, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDetailedUsage3() != null && buildingDetailAttribute.getDetailedUsage3().isSetValue()) {
            ps.setString(25, buildingDetailAttribute.getDetailedUsage3().getValue());
            ps.setString(26, buildingDetailAttribute.getDetailedUsage3().getCodeSpace());
        } else {
            ps.setNull(25, Types.VARCHAR);
            ps.setNull(26, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDevelopmentArea() != null && buildingDetailAttribute.getDevelopmentArea().isSetValue()) {
            ps.setDouble(27, buildingDetailAttribute.getDevelopmentArea().getValue());
            ps.setString(28, buildingDetailAttribute.getDevelopmentArea().getUom());
        } else {
            ps.setNull(27, Types.DOUBLE);
            ps.setNull(28, Types.VARCHAR);
        }

        if (!buildingDetailAttribute.getDistrictsAndZonesType().isEmpty()) {
            valueJoiner.join(buildingDetailAttribute.getDistrictsAndZonesType(), Code::getValue, Code::getCodeSpace);
            ps.setString(29, valueJoiner.result(0));
            ps.setString(30, valueJoiner.result(1));
        } else {
            ps.setNull(29, Types.VARCHAR);
            ps.setNull(30, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getEaveHeight() != null && buildingDetailAttribute.getEaveHeight().isSetValue()) {
            ps.setDouble(31, buildingDetailAttribute.getEaveHeight().getValue());
            ps.setString(32, buildingDetailAttribute.getEaveHeight().getUom());
        } else {
            ps.setNull(31, Types.DOUBLE);
            ps.setNull(32, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getFireproofStructureType() != null && buildingDetailAttribute.getFireproofStructureType().isSetValue()) {
            ps.setString(33, buildingDetailAttribute.getFireproofStructureType().getValue());
            ps.setString(34, buildingDetailAttribute.getFireproofStructureType().getCodeSpace());
        } else {
            ps.setNull(33, Types.VARCHAR);
            ps.setNull(34, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getFloorAreaRate() != null)
            ps.setDouble(35, buildingDetailAttribute.getFloorAreaRate());
        else
            ps.setNull(35, Types.DOUBLE);

        if (buildingDetailAttribute.getGroundFloorUsage() != null && buildingDetailAttribute.getGroundFloorUsage().isSetValue()) {
            ps.setString(36, buildingDetailAttribute.getGroundFloorUsage().getValue());
            ps.setString(37, buildingDetailAttribute.getGroundFloorUsage().getCodeSpace());
        } else {
            ps.setNull(36, Types.VARCHAR);
            ps.setNull(37, Types.VARCHAR);
        }

        ps.setString(38, buildingDetailAttribute.getImplementingBody());

        if (buildingDetailAttribute.getLandUseType() != null && buildingDetailAttribute.getLandUseType().isSetValue()) {
            ps.setString(39, buildingDetailAttribute.getLandUseType().getValue());
            ps.setString(40, buildingDetailAttribute.getLandUseType().getCodeSpace());
        } else {
            ps.setNull(39, Types.VARCHAR);
            ps.setNull(40, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getMajorUsage() != null && buildingDetailAttribute.getMajorUsage().isSetValue()) {
            ps.setString(41, buildingDetailAttribute.getMajorUsage().getValue());
            ps.setString(42, buildingDetailAttribute.getMajorUsage().getCodeSpace());
        } else {
            ps.setNull(41, Types.VARCHAR);
            ps.setNull(42, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getMajorUsage2() != null && buildingDetailAttribute.getMajorUsage2().isSetValue()) {
            ps.setString(43, buildingDetailAttribute.getMajorUsage2().getValue());
            ps.setString(44, buildingDetailAttribute.getMajorUsage2().getCodeSpace());
        } else {
            ps.setNull(43, Types.VARCHAR);
            ps.setNull(44, Types.VARCHAR);
        }

        ps.setString(45, buildingDetailAttribute.getNote());

        if (buildingDetailAttribute.getOrgUsage() != null && buildingDetailAttribute.getOrgUsage().isSetValue()) {
            ps.setString(46, buildingDetailAttribute.getOrgUsage().getValue());
            ps.setString(47, buildingDetailAttribute.getOrgUsage().getCodeSpace());
        } else {
            ps.setNull(46, Types.VARCHAR);
            ps.setNull(47, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getOrgUsage2() != null && buildingDetailAttribute.getOrgUsage2().isSetValue()) {
            ps.setString(48, buildingDetailAttribute.getOrgUsage2().getValue());
            ps.setString(49, buildingDetailAttribute.getOrgUsage2().getCodeSpace());
        } else {
            ps.setNull(48, Types.VARCHAR);
            ps.setNull(49, Types.VARCHAR);
        }

        ps.setString(50, buildingDetailAttribute.getReference());

        if (buildingDetailAttribute.getSecondFloorUsage() != null && buildingDetailAttribute.getSecondFloorUsage().isSetValue()) {
            ps.setString(51, buildingDetailAttribute.getSecondFloorUsage().getValue());
            ps.setString(52, buildingDetailAttribute.getSecondFloorUsage().getCodeSpace());
        } else {
            ps.setNull(51, Types.VARCHAR);
            ps.setNull(52, Types.VARCHAR);
        }

        ps.setString(53, buildingDetailAttribute.getSerialNumberOfBuildingCertification());

        if (buildingDetailAttribute.getSiteArea() != null && buildingDetailAttribute.getSiteArea().isSetValue()) {
            ps.setDouble(54, buildingDetailAttribute.getSiteArea().getValue());
            ps.setString(55, buildingDetailAttribute.getSiteArea().getUom());
        } else {
            ps.setNull(54, Types.DOUBLE);
            ps.setNull(55, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getSpecifiedBuildingCoverageRate() != null)
            ps.setDouble(56, buildingDetailAttribute.getSpecifiedBuildingCoverageRate());
        else
            ps.setNull(56, Types.DOUBLE);

        if (buildingDetailAttribute.getSpecifiedFloorAreaRate() != null)
            ps.setDouble(57, buildingDetailAttribute.getSpecifiedFloorAreaRate());
        else
            ps.setNull(57, Types.DOUBLE);

        if (buildingDetailAttribute.getStandardFloorAreaRate() != null)
            ps.setDouble(58, buildingDetailAttribute.getStandardFloorAreaRate());
        else
            ps.setNull(58, Types.DOUBLE);

        if (buildingDetailAttribute.getSurveyYear() != null)
            ps.setDate(59, Date.valueOf(LocalDate.of(buildingDetailAttribute.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(59, Types.DATE);

        if (buildingDetailAttribute.getThirdFloorUsage() != null && buildingDetailAttribute.getThirdFloorUsage().isSetValue()) {
            ps.setString(60, buildingDetailAttribute.getThirdFloorUsage().getValue());
            ps.setString(61, buildingDetailAttribute.getThirdFloorUsage().getCodeSpace());
        } else {
            ps.setNull(60, Types.VARCHAR);
            ps.setNull(61, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getTotalFloorArea() != null && buildingDetailAttribute.getTotalFloorArea().isSetValue()) {
            ps.setDouble(62, buildingDetailAttribute.getTotalFloorArea().getValue());
            ps.setString(63, buildingDetailAttribute.getTotalFloorArea().getUom());
        } else {
            ps.setNull(62, Types.DOUBLE);
            ps.setNull(63, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getUrbanPlanType() != null && buildingDetailAttribute.getUrbanPlanType().isSetValue()) {
            ps.setString(64, buildingDetailAttribute.getUrbanPlanType().getValue());
            ps.setString(65, buildingDetailAttribute.getUrbanPlanType().getCodeSpace());
        } else {
            ps.setNull(64, Types.VARCHAR);
            ps.setNull(65, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getVacancy() != null && buildingDetailAttribute.getVacancy().isSetValue()) {
            ps.setString(66, buildingDetailAttribute.getVacancy().getValue());
            ps.setString(67, buildingDetailAttribute.getVacancy().getCodeSpace());
        } else {
            ps.setNull(66, Types.VARCHAR);
            ps.setNull(67, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDINGDETAILATTRIBUT));

        return objectId;
    }

    @Override
    public void executeBatch() throws CityGMLImportException, SQLException {
        if (batchCounter > 0) {
            ps.executeBatch();
            batchCounter = 0;
        }
    }

    @Override
    public void close() throws CityGMLImportException, SQLException {
        ps.close();
    }
}
