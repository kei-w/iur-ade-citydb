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
import org.citygml4j.ade.iur.model.uro.LongevityMeasuresProperty;
import org.citygml4j.ade.iur.model.uro.ParkFacilityLongevityPlanAttribute;
import org.citygml4j.ade.iur.model.uro.ParkHealthAssessmentProperty;

import java.sql.*;
import java.time.LocalDate;

public class ParkFacilityLongevityPlanAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final NumberOfFacilitiesImporter numberOfFacilitiesImporter;
    private final RepairsBeforeParkHealthAssessmentImporter repairsBeforeParkHealthAssessmentImporter;
    private final ParkFacilityLongevityPlanAttributeToParkHealthAssessmentImporter parkFacilityLongevityPlanAttributeToParkHealthAssessmentImporter;
    private final ParkHealthAssessmentImporter parkHealthAssessmentImporter;
    private final ParkFacilityLongevityPlanAttributeToLongevityMeasuresImporter parkFacilityLongevityPlanAttributeToLongevityMeasuresImporter;
    private final LongevityMeasuresImporter longevityMeasuresImporter;

    private int batchCounter;

    public ParkFacilityLongevityPlanAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PARKFACILITYLONGEVITYP)) + " " +
                "(id, parkcode, parkcode_codespace, parkname, parkname_codespace, parktype, parktype_codespace," +
                "facilityname, facilityname_codespace, facilitynameoptional, specificfacilityname," +
                "numberoffacilities_id, size_, mainmaterial, mainmaterial_codespace, mainmaterialoptional," +
                "installationyear, disposallimitperiod, expectedusageperiod, repairsbeforeparkhealthas_id," +
                "managementtype, managementtype_codespace, expectedrenewalyearwithmeasu, noteforlongevity) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        numberOfFacilitiesImporter = manager.getImporter(NumberOfFacilitiesImporter.class);
        repairsBeforeParkHealthAssessmentImporter = manager.getImporter(RepairsBeforeParkHealthAssessmentImporter.class);
        parkFacilityLongevityPlanAttributeToParkHealthAssessmentImporter = manager.getImporter(ParkFacilityLongevityPlanAttributeToParkHealthAssessmentImporter.class);
        parkHealthAssessmentImporter = manager.getImporter(ParkHealthAssessmentImporter.class);
        parkFacilityLongevityPlanAttributeToLongevityMeasuresImporter = manager.getImporter(ParkFacilityLongevityPlanAttributeToLongevityMeasuresImporter.class);
        longevityMeasuresImporter = manager.getImporter(LongevityMeasuresImporter.class);
    }

    public void doImport(ParkFacilityLongevityPlanAttribute parkFacilityLongevityPlanAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (parkFacilityLongevityPlanAttribute.getParkCode() != null && parkFacilityLongevityPlanAttribute.getParkCode().isSetValue()) {
            ps.setString(2, parkFacilityLongevityPlanAttribute.getParkCode().getValue());
            ps.setString(3, parkFacilityLongevityPlanAttribute.getParkCode().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (parkFacilityLongevityPlanAttribute.getParkName() != null && parkFacilityLongevityPlanAttribute.getParkName().isSetValue()) {
            ps.setString(4, parkFacilityLongevityPlanAttribute.getParkName().getValue());
            ps.setString(5, parkFacilityLongevityPlanAttribute.getParkName().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (parkFacilityLongevityPlanAttribute.getParkType() != null && parkFacilityLongevityPlanAttribute.getParkType().isSetValue()) {
            ps.setString(6, parkFacilityLongevityPlanAttribute.getParkType().getValue());
            ps.setString(7, parkFacilityLongevityPlanAttribute.getParkType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (parkFacilityLongevityPlanAttribute.getFacilityName() != null && parkFacilityLongevityPlanAttribute.getFacilityName().isSetValue()) {
            ps.setString(8, parkFacilityLongevityPlanAttribute.getFacilityName().getValue());
            ps.setString(9, parkFacilityLongevityPlanAttribute.getFacilityName().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        ps.setString(10, parkFacilityLongevityPlanAttribute.getFacilityNameOptional());

        ps.setString(11, parkFacilityLongevityPlanAttribute.getSpecificFacilityName());

        long numberOfFacilitiesId = 0;
        if (parkFacilityLongevityPlanAttribute.getNumberOfFacilities() != null && parkFacilityLongevityPlanAttribute.getNumberOfFacilities().isSetObject())
            numberOfFacilitiesId = numberOfFacilitiesImporter.doImport(parkFacilityLongevityPlanAttribute.getNumberOfFacilities().getObject());

        if (numberOfFacilitiesId != 0)
            ps.setLong(12, numberOfFacilitiesId);
        else
            ps.setNull(12, Types.NULL);

        ps.setString(13, parkFacilityLongevityPlanAttribute.getSize());

        if (parkFacilityLongevityPlanAttribute.getMainMaterial() != null && parkFacilityLongevityPlanAttribute.getMainMaterial().isSetValue()) {
            ps.setString(14, parkFacilityLongevityPlanAttribute.getMainMaterial().getValue());
            ps.setString(15, parkFacilityLongevityPlanAttribute.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        ps.setString(16, parkFacilityLongevityPlanAttribute.getMainMaterialOptional());

        if (parkFacilityLongevityPlanAttribute.getInstallationYear() != null)
            ps.setDate(17, Date.valueOf(LocalDate.of(parkFacilityLongevityPlanAttribute.getInstallationYear().getValue(), 1, 1)));
        else
            ps.setNull(17, Types.DATE);

        if (parkFacilityLongevityPlanAttribute.getDisposalLimitPeriod() != null)
            ps.setInt(18, parkFacilityLongevityPlanAttribute.getDisposalLimitPeriod());
        else
            ps.setNull(18, Types.INTEGER);

        if (parkFacilityLongevityPlanAttribute.getExpectedUsagePeriod() != null)
            ps.setInt(19, parkFacilityLongevityPlanAttribute.getExpectedUsagePeriod());
        else
            ps.setNull(19, Types.INTEGER);

        long repairsBeforeParkHealthAssessmentId = 0;
        if (parkFacilityLongevityPlanAttribute.getRepairsBeforeParkHealthAssessment() != null && parkFacilityLongevityPlanAttribute.getRepairsBeforeParkHealthAssessment().isSetObject())
            repairsBeforeParkHealthAssessmentId = repairsBeforeParkHealthAssessmentImporter.doImport(parkFacilityLongevityPlanAttribute.getRepairsBeforeParkHealthAssessment().getObject());

        if (repairsBeforeParkHealthAssessmentId != 0)
            ps.setLong(20, repairsBeforeParkHealthAssessmentId);
        else
            ps.setNull(20, Types.NULL);

        if (parkFacilityLongevityPlanAttribute.getManagementType() != null && parkFacilityLongevityPlanAttribute.getManagementType().isSetValue()) {
            ps.setString(21, parkFacilityLongevityPlanAttribute.getManagementType().getValue());
            ps.setString(22, parkFacilityLongevityPlanAttribute.getManagementType().getCodeSpace());
        } else {
            ps.setNull(21, Types.VARCHAR);
            ps.setNull(22, Types.VARCHAR);
        }

        if (parkFacilityLongevityPlanAttribute.getExpectedRenewalYearWithMeasures() != null)
            ps.setDate(23, Date.valueOf(LocalDate.of(parkFacilityLongevityPlanAttribute.getExpectedRenewalYearWithMeasures().getValue(), 1, 1)));
        else
            ps.setNull(23, Types.DATE);

        ps.setString(24, parkFacilityLongevityPlanAttribute.getNoteForLongevity());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PARKFACILITYLONGEVITYP));

        for (ParkHealthAssessmentProperty property : parkFacilityLongevityPlanAttribute.getParkHealthAssessment()) {
            if (property.isSetObject()) {
                long parkHealthAssessmentId = parkHealthAssessmentImporter.doImport(property.getObject());
                parkFacilityLongevityPlanAttributeToParkHealthAssessmentImporter.doImport(parkHealthAssessmentId, objectId);
            }
        }
        for (LongevityMeasuresProperty property : parkFacilityLongevityPlanAttribute.getLongevityMeasures()) {
            if (property.isSetObject()) {
                long longevityMeasuresId = longevityMeasuresImporter.doImport(property.getObject());
                parkFacilityLongevityPlanAttributeToLongevityMeasuresImporter.doImport(longevityMeasuresId, objectId);
            }
        }
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
