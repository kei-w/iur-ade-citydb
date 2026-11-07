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
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public BuildingDetailAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGDETAILATTRIBUT)) + " " +
                "(id, serialnumberofbuildingcertif, sitearea, sitearea_uom, totalfloorarea, totalfloorarea_uom," +
                "buildingfootprintarea, buildingfootprintarea_uom, buildingroofedgearea, buildingroofedgearea_uom," +
                "developmentarea, developmentarea_uom, buildingstructuretype, buildingstructuret_codespace," +
                "buildingstructureorgtype, buildingstructureo_codespace, fireproofstructuretype," +
                "fireproofstructure_codespace, implementingbody, urbanplantype, urbanplantype_codespace," +
                "areaclassificationtype, areaclassification_codespace, districtsandzonestype," +
                "districtsandzonest_codespace, landusetype, landusetype_codespace, reference, majorusage," +
                "majorusage_codespace, majorusage2, majorusage2_codespace, orgusage, orgusage_codespace, orgusage2," +
                "orgusage2_codespace, detailedusage, detailedusage_codespace, detailedusage2," +
                "detailedusage2_codespace, detailedusage3, detailedusage3_codespace, groundfloorusage," +
                "groundfloorusage_codespace, secondfloorusage, secondfloorusage_codespace, thirdfloorusage," +
                "thirdfloorusage_codespace, basementusage, basementusage_codespace, basementfirstusage," +
                "basementfirstusage_codespace, basementsecondusage, basementsecondusag_codespace, vacancy," +
                "vacancy_codespace, buildingcoveragerate, floorarearate, specifiedbuildingcoveragerat," +
                "specifiedfloorarearate, standardfloorarearate, buildingheight, buildingheight_uom, eaveheight," +
                "eaveheight_uom, note, surveyyear) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(BuildingDetailAttribute buildingDetailAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, buildingDetailAttribute.getSerialNumberOfBuildingCertification());

        if (buildingDetailAttribute.getSiteArea() != null && buildingDetailAttribute.getSiteArea().isSetValue()) {
            ps.setDouble(3, buildingDetailAttribute.getSiteArea().getValue());
            ps.setString(4, buildingDetailAttribute.getSiteArea().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getTotalFloorArea() != null && buildingDetailAttribute.getTotalFloorArea().isSetValue()) {
            ps.setDouble(5, buildingDetailAttribute.getTotalFloorArea().getValue());
            ps.setString(6, buildingDetailAttribute.getTotalFloorArea().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingFootprintArea() != null && buildingDetailAttribute.getBuildingFootprintArea().isSetValue()) {
            ps.setDouble(7, buildingDetailAttribute.getBuildingFootprintArea().getValue());
            ps.setString(8, buildingDetailAttribute.getBuildingFootprintArea().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingRoofEdgeArea() != null && buildingDetailAttribute.getBuildingRoofEdgeArea().isSetValue()) {
            ps.setDouble(9, buildingDetailAttribute.getBuildingRoofEdgeArea().getValue());
            ps.setString(10, buildingDetailAttribute.getBuildingRoofEdgeArea().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDevelopmentArea() != null && buildingDetailAttribute.getDevelopmentArea().isSetValue()) {
            ps.setDouble(11, buildingDetailAttribute.getDevelopmentArea().getValue());
            ps.setString(12, buildingDetailAttribute.getDevelopmentArea().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingStructureType() != null && buildingDetailAttribute.getBuildingStructureType().isSetValue()) {
            ps.setString(13, buildingDetailAttribute.getBuildingStructureType().getValue());
            ps.setString(14, buildingDetailAttribute.getBuildingStructureType().getCodeSpace());
        } else {
            ps.setNull(13, Types.VARCHAR);
            ps.setNull(14, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingStructureOrgType() != null && buildingDetailAttribute.getBuildingStructureOrgType().isSetValue()) {
            ps.setString(15, buildingDetailAttribute.getBuildingStructureOrgType().getValue());
            ps.setString(16, buildingDetailAttribute.getBuildingStructureOrgType().getCodeSpace());
        } else {
            ps.setNull(15, Types.VARCHAR);
            ps.setNull(16, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getFireproofStructureType() != null && buildingDetailAttribute.getFireproofStructureType().isSetValue()) {
            ps.setString(17, buildingDetailAttribute.getFireproofStructureType().getValue());
            ps.setString(18, buildingDetailAttribute.getFireproofStructureType().getCodeSpace());
        } else {
            ps.setNull(17, Types.VARCHAR);
            ps.setNull(18, Types.VARCHAR);
        }

        ps.setString(19, buildingDetailAttribute.getImplementingBody());

        if (buildingDetailAttribute.getUrbanPlanType() != null && buildingDetailAttribute.getUrbanPlanType().isSetValue()) {
            ps.setString(20, buildingDetailAttribute.getUrbanPlanType().getValue());
            ps.setString(21, buildingDetailAttribute.getUrbanPlanType().getCodeSpace());
        } else {
            ps.setNull(20, Types.VARCHAR);
            ps.setNull(21, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getAreaClassificationType() != null && buildingDetailAttribute.getAreaClassificationType().isSetValue()) {
            ps.setString(22, buildingDetailAttribute.getAreaClassificationType().getValue());
            ps.setString(23, buildingDetailAttribute.getAreaClassificationType().getCodeSpace());
        } else {
            ps.setNull(22, Types.VARCHAR);
            ps.setNull(23, Types.VARCHAR);
        }

        if (!buildingDetailAttribute.getDistrictsAndZonesType().isEmpty()) {
            valueJoiner.join(buildingDetailAttribute.getDistrictsAndZonesType(), Code::getValue, Code::getCodeSpace);
            ps.setString(24, valueJoiner.result(0));
            ps.setString(25, valueJoiner.result(1));
        } else {
            ps.setNull(24, Types.VARCHAR);
            ps.setNull(25, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getLandUseType() != null && buildingDetailAttribute.getLandUseType().isSetValue()) {
            ps.setString(26, buildingDetailAttribute.getLandUseType().getValue());
            ps.setString(27, buildingDetailAttribute.getLandUseType().getCodeSpace());
        } else {
            ps.setNull(26, Types.VARCHAR);
            ps.setNull(27, Types.VARCHAR);
        }

        ps.setString(28, buildingDetailAttribute.getReference());

        if (buildingDetailAttribute.getMajorUsage() != null && buildingDetailAttribute.getMajorUsage().isSetValue()) {
            ps.setString(29, buildingDetailAttribute.getMajorUsage().getValue());
            ps.setString(30, buildingDetailAttribute.getMajorUsage().getCodeSpace());
        } else {
            ps.setNull(29, Types.VARCHAR);
            ps.setNull(30, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getMajorUsage2() != null && buildingDetailAttribute.getMajorUsage2().isSetValue()) {
            ps.setString(31, buildingDetailAttribute.getMajorUsage2().getValue());
            ps.setString(32, buildingDetailAttribute.getMajorUsage2().getCodeSpace());
        } else {
            ps.setNull(31, Types.VARCHAR);
            ps.setNull(32, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getOrgUsage() != null && buildingDetailAttribute.getOrgUsage().isSetValue()) {
            ps.setString(33, buildingDetailAttribute.getOrgUsage().getValue());
            ps.setString(34, buildingDetailAttribute.getOrgUsage().getCodeSpace());
        } else {
            ps.setNull(33, Types.VARCHAR);
            ps.setNull(34, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getOrgUsage2() != null && buildingDetailAttribute.getOrgUsage2().isSetValue()) {
            ps.setString(35, buildingDetailAttribute.getOrgUsage2().getValue());
            ps.setString(36, buildingDetailAttribute.getOrgUsage2().getCodeSpace());
        } else {
            ps.setNull(35, Types.VARCHAR);
            ps.setNull(36, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDetailedUsage() != null && buildingDetailAttribute.getDetailedUsage().isSetValue()) {
            ps.setString(37, buildingDetailAttribute.getDetailedUsage().getValue());
            ps.setString(38, buildingDetailAttribute.getDetailedUsage().getCodeSpace());
        } else {
            ps.setNull(37, Types.VARCHAR);
            ps.setNull(38, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDetailedUsage2() != null && buildingDetailAttribute.getDetailedUsage2().isSetValue()) {
            ps.setString(39, buildingDetailAttribute.getDetailedUsage2().getValue());
            ps.setString(40, buildingDetailAttribute.getDetailedUsage2().getCodeSpace());
        } else {
            ps.setNull(39, Types.VARCHAR);
            ps.setNull(40, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getDetailedUsage3() != null && buildingDetailAttribute.getDetailedUsage3().isSetValue()) {
            ps.setString(41, buildingDetailAttribute.getDetailedUsage3().getValue());
            ps.setString(42, buildingDetailAttribute.getDetailedUsage3().getCodeSpace());
        } else {
            ps.setNull(41, Types.VARCHAR);
            ps.setNull(42, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getGroundFloorUsage() != null && buildingDetailAttribute.getGroundFloorUsage().isSetValue()) {
            ps.setString(43, buildingDetailAttribute.getGroundFloorUsage().getValue());
            ps.setString(44, buildingDetailAttribute.getGroundFloorUsage().getCodeSpace());
        } else {
            ps.setNull(43, Types.VARCHAR);
            ps.setNull(44, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getSecondFloorUsage() != null && buildingDetailAttribute.getSecondFloorUsage().isSetValue()) {
            ps.setString(45, buildingDetailAttribute.getSecondFloorUsage().getValue());
            ps.setString(46, buildingDetailAttribute.getSecondFloorUsage().getCodeSpace());
        } else {
            ps.setNull(45, Types.VARCHAR);
            ps.setNull(46, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getThirdFloorUsage() != null && buildingDetailAttribute.getThirdFloorUsage().isSetValue()) {
            ps.setString(47, buildingDetailAttribute.getThirdFloorUsage().getValue());
            ps.setString(48, buildingDetailAttribute.getThirdFloorUsage().getCodeSpace());
        } else {
            ps.setNull(47, Types.VARCHAR);
            ps.setNull(48, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBasementUsage() != null && buildingDetailAttribute.getBasementUsage().isSetValue()) {
            ps.setString(49, buildingDetailAttribute.getBasementUsage().getValue());
            ps.setString(50, buildingDetailAttribute.getBasementUsage().getCodeSpace());
        } else {
            ps.setNull(49, Types.VARCHAR);
            ps.setNull(50, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBasementFirstUsage() != null && buildingDetailAttribute.getBasementFirstUsage().isSetValue()) {
            ps.setString(51, buildingDetailAttribute.getBasementFirstUsage().getValue());
            ps.setString(52, buildingDetailAttribute.getBasementFirstUsage().getCodeSpace());
        } else {
            ps.setNull(51, Types.VARCHAR);
            ps.setNull(52, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBasementSecondUsage() != null && buildingDetailAttribute.getBasementSecondUsage().isSetValue()) {
            ps.setString(53, buildingDetailAttribute.getBasementSecondUsage().getValue());
            ps.setString(54, buildingDetailAttribute.getBasementSecondUsage().getCodeSpace());
        } else {
            ps.setNull(53, Types.VARCHAR);
            ps.setNull(54, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getVacancy() != null && buildingDetailAttribute.getVacancy().isSetValue()) {
            ps.setString(55, buildingDetailAttribute.getVacancy().getValue());
            ps.setString(56, buildingDetailAttribute.getVacancy().getCodeSpace());
        } else {
            ps.setNull(55, Types.VARCHAR);
            ps.setNull(56, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getBuildingCoverageRate() != null)
            ps.setDouble(57, buildingDetailAttribute.getBuildingCoverageRate());
        else
            ps.setNull(57, Types.DOUBLE);

        if (buildingDetailAttribute.getFloorAreaRate() != null)
            ps.setDouble(58, buildingDetailAttribute.getFloorAreaRate());
        else
            ps.setNull(58, Types.DOUBLE);

        if (buildingDetailAttribute.getSpecifiedBuildingCoverageRate() != null)
            ps.setDouble(59, buildingDetailAttribute.getSpecifiedBuildingCoverageRate());
        else
            ps.setNull(59, Types.DOUBLE);

        if (buildingDetailAttribute.getSpecifiedFloorAreaRate() != null)
            ps.setDouble(60, buildingDetailAttribute.getSpecifiedFloorAreaRate());
        else
            ps.setNull(60, Types.DOUBLE);

        if (buildingDetailAttribute.getStandardFloorAreaRate() != null)
            ps.setDouble(61, buildingDetailAttribute.getStandardFloorAreaRate());
        else
            ps.setNull(61, Types.DOUBLE);

        if (buildingDetailAttribute.getBuildingHeight() != null && buildingDetailAttribute.getBuildingHeight().isSetValue()) {
            ps.setDouble(62, buildingDetailAttribute.getBuildingHeight().getValue());
            ps.setString(63, buildingDetailAttribute.getBuildingHeight().getUom());
        } else {
            ps.setNull(62, Types.DOUBLE);
            ps.setNull(63, Types.VARCHAR);
        }

        if (buildingDetailAttribute.getEaveHeight() != null && buildingDetailAttribute.getEaveHeight().isSetValue()) {
            ps.setDouble(64, buildingDetailAttribute.getEaveHeight().getValue());
            ps.setString(65, buildingDetailAttribute.getEaveHeight().getUom());
        } else {
            ps.setNull(64, Types.DOUBLE);
            ps.setNull(65, Types.VARCHAR);
        }

        ps.setString(66, buildingDetailAttribute.getNote());

        if (buildingDetailAttribute.getSurveyYear() != null)
            ps.setDate(67, Date.valueOf(LocalDate.of(buildingDetailAttribute.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(67, Types.DATE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDINGDETAILATTRIBUT));
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
