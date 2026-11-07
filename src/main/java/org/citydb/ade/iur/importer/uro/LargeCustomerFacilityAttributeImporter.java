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
import org.citygml4j.ade.iur.model.uro.LargeCustomerFacilityAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;
import java.time.LocalDate;

public class LargeCustomerFacilityAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public LargeCustomerFacilityAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.LARGECUSTOMERFACILITYA)) + " " +
                "(id, class, class_codespace, name, capacity, owner, totalfloorarea, totalfloorarea_uom," +
                "totalstorefloorarea, totalstorefloorarea_uom, inauguraldate, yearopened, yearclosed, keytenants," +
                "availability, urbanplantype, urbanplantype_codespace, areaclassificationtype," +
                "areaclassification_codespace, districtsandzonestype, districtsandzonest_codespace, landusetype," +
                "landusetype_codespace, reference, note, surveyyear) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(LargeCustomerFacilityAttribute largeCustomerFacilityAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (largeCustomerFacilityAttribute.getClassifier() != null && largeCustomerFacilityAttribute.getClassifier().isSetValue()) {
            ps.setString(2, largeCustomerFacilityAttribute.getClassifier().getValue());
            ps.setString(3, largeCustomerFacilityAttribute.getClassifier().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, largeCustomerFacilityAttribute.getName());

        if (largeCustomerFacilityAttribute.getCapacity() != null)
            ps.setInt(5, largeCustomerFacilityAttribute.getCapacity());
        else
            ps.setNull(5, Types.INTEGER);

        ps.setString(6, largeCustomerFacilityAttribute.getOwner());

        if (largeCustomerFacilityAttribute.getTotalFloorArea() != null && largeCustomerFacilityAttribute.getTotalFloorArea().isSetValue()) {
            ps.setDouble(7, largeCustomerFacilityAttribute.getTotalFloorArea().getValue());
            ps.setString(8, largeCustomerFacilityAttribute.getTotalFloorArea().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getTotalStoreFloorArea() != null && largeCustomerFacilityAttribute.getTotalStoreFloorArea().isSetValue()) {
            ps.setDouble(9, largeCustomerFacilityAttribute.getTotalStoreFloorArea().getValue());
            ps.setString(10, largeCustomerFacilityAttribute.getTotalStoreFloorArea().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getInauguralDate() != null)
            ps.setDate(11, Date.valueOf(largeCustomerFacilityAttribute.getInauguralDate()));
        else
            ps.setNull(11, Types.DATE);

        if (largeCustomerFacilityAttribute.getYearOpened() != null)
            ps.setDate(12, Date.valueOf(LocalDate.of(largeCustomerFacilityAttribute.getYearOpened().getValue(), 1, 1)));
        else
            ps.setNull(12, Types.DATE);

        if (largeCustomerFacilityAttribute.getYearClosed() != null)
            ps.setDate(13, Date.valueOf(LocalDate.of(largeCustomerFacilityAttribute.getYearClosed().getValue(), 1, 1)));
        else
            ps.setNull(13, Types.DATE);

        ps.setString(14, largeCustomerFacilityAttribute.getKeyTenants());

        if (largeCustomerFacilityAttribute.getAvailability() != null)
            ps.setInt(15, largeCustomerFacilityAttribute.getAvailability() ? 1 : 0);
        else
            ps.setNull(15, Types.INTEGER);

        if (largeCustomerFacilityAttribute.getUrbanPlanType() != null && largeCustomerFacilityAttribute.getUrbanPlanType().isSetValue()) {
            ps.setString(16, largeCustomerFacilityAttribute.getUrbanPlanType().getValue());
            ps.setString(17, largeCustomerFacilityAttribute.getUrbanPlanType().getCodeSpace());
        } else {
            ps.setNull(16, Types.VARCHAR);
            ps.setNull(17, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getAreaClassificationType() != null && largeCustomerFacilityAttribute.getAreaClassificationType().isSetValue()) {
            ps.setString(18, largeCustomerFacilityAttribute.getAreaClassificationType().getValue());
            ps.setString(19, largeCustomerFacilityAttribute.getAreaClassificationType().getCodeSpace());
        } else {
            ps.setNull(18, Types.VARCHAR);
            ps.setNull(19, Types.VARCHAR);
        }

        if (!largeCustomerFacilityAttribute.getDistrictsAndZonesType().isEmpty()) {
            valueJoiner.join(largeCustomerFacilityAttribute.getDistrictsAndZonesType(), Code::getValue, Code::getCodeSpace);
            ps.setString(20, valueJoiner.result(0));
            ps.setString(21, valueJoiner.result(1));
        } else {
            ps.setNull(20, Types.VARCHAR);
            ps.setNull(21, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getLandUseType() != null && largeCustomerFacilityAttribute.getLandUseType().isSetValue()) {
            ps.setString(22, largeCustomerFacilityAttribute.getLandUseType().getValue());
            ps.setString(23, largeCustomerFacilityAttribute.getLandUseType().getCodeSpace());
        } else {
            ps.setNull(22, Types.VARCHAR);
            ps.setNull(23, Types.VARCHAR);
        }

        ps.setString(24, largeCustomerFacilityAttribute.getReference());

        ps.setString(25, largeCustomerFacilityAttribute.getNote());

        if (largeCustomerFacilityAttribute.getSurveyYear() != null)
            ps.setDate(26, Date.valueOf(LocalDate.of(largeCustomerFacilityAttribute.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(26, Types.DATE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.LARGECUSTOMERFACILITYA));
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
