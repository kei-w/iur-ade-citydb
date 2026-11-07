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
    private final BuildingAttributeImporter buildingAttributeImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public LargeCustomerFacilityAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.LARGECUSTOMERFACILITYA)) + " " +
                "(id, areaclassificationtype, areaclassification_codespace, availability, capacity, class," +
                "class_codespace, districtsandzonestype, districtsandzonest_codespace, inauguraldate, keytenants," +
                "landusetype, landusetype_codespace, name, note, owner, reference, surveyyear, totalfloorarea," +
                "totalfloorarea_uom, totalstorefloorarea, totalstorefloorarea_uom, urbanplantype," +
                "urbanplantype_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        buildingAttributeImporter = manager.getImporter(BuildingAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(LargeCustomerFacilityAttribute largeCustomerFacilityAttribute) throws CityGMLImportException, SQLException {
        long objectId = buildingAttributeImporter.doImport(largeCustomerFacilityAttribute);
        ps.setLong(1, objectId);

        if (largeCustomerFacilityAttribute.getAreaClassificationType() != null && largeCustomerFacilityAttribute.getAreaClassificationType().isSetValue()) {
            ps.setString(2, largeCustomerFacilityAttribute.getAreaClassificationType().getValue());
            ps.setString(3, largeCustomerFacilityAttribute.getAreaClassificationType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getAvailability() != null)
            ps.setInt(4, largeCustomerFacilityAttribute.getAvailability() ? 1 : 0);
        else
            ps.setNull(4, Types.INTEGER);

        if (largeCustomerFacilityAttribute.getCapacity() != null)
            ps.setInt(5, largeCustomerFacilityAttribute.getCapacity());
        else
            ps.setNull(5, Types.INTEGER);

        if (largeCustomerFacilityAttribute.getClassifier() != null && largeCustomerFacilityAttribute.getClassifier().isSetValue()) {
            ps.setString(6, largeCustomerFacilityAttribute.getClassifier().getValue());
            ps.setString(7, largeCustomerFacilityAttribute.getClassifier().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (!largeCustomerFacilityAttribute.getDistrictsAndZonesType().isEmpty()) {
            valueJoiner.join(largeCustomerFacilityAttribute.getDistrictsAndZonesType(), Code::getValue, Code::getCodeSpace);
            ps.setString(8, valueJoiner.result(0));
            ps.setString(9, valueJoiner.result(1));
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getInauguralDate() != null)
            ps.setDate(10, Date.valueOf(largeCustomerFacilityAttribute.getInauguralDate()));
        else
            ps.setNull(10, Types.DATE);

        ps.setString(11, largeCustomerFacilityAttribute.getKeyTenants());

        if (largeCustomerFacilityAttribute.getLandUseType() != null && largeCustomerFacilityAttribute.getLandUseType().isSetValue()) {
            ps.setString(12, largeCustomerFacilityAttribute.getLandUseType().getValue());
            ps.setString(13, largeCustomerFacilityAttribute.getLandUseType().getCodeSpace());
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        ps.setString(14, largeCustomerFacilityAttribute.getName());

        ps.setString(15, largeCustomerFacilityAttribute.getNote());

        ps.setString(16, largeCustomerFacilityAttribute.getOwner());

        ps.setString(17, largeCustomerFacilityAttribute.getReference());

        if (largeCustomerFacilityAttribute.getSurveyYear() != null)
            ps.setDate(18, Date.valueOf(LocalDate.of(largeCustomerFacilityAttribute.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(18, Types.DATE);

        if (largeCustomerFacilityAttribute.getTotalFloorArea() != null && largeCustomerFacilityAttribute.getTotalFloorArea().isSetValue()) {
            ps.setDouble(19, largeCustomerFacilityAttribute.getTotalFloorArea().getValue());
            ps.setString(20, largeCustomerFacilityAttribute.getTotalFloorArea().getUom());
        } else {
            ps.setNull(19, Types.DOUBLE);
            ps.setNull(20, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getTotalStoreFloorArea() != null && largeCustomerFacilityAttribute.getTotalStoreFloorArea().isSetValue()) {
            ps.setDouble(21, largeCustomerFacilityAttribute.getTotalStoreFloorArea().getValue());
            ps.setString(22, largeCustomerFacilityAttribute.getTotalStoreFloorArea().getUom());
        } else {
            ps.setNull(21, Types.DOUBLE);
            ps.setNull(22, Types.VARCHAR);
        }

        if (largeCustomerFacilityAttribute.getUrbanPlanType() != null && largeCustomerFacilityAttribute.getUrbanPlanType().isSetValue()) {
            ps.setString(23, largeCustomerFacilityAttribute.getUrbanPlanType().getValue());
            ps.setString(24, largeCustomerFacilityAttribute.getUrbanPlanType().getCodeSpace());
        } else {
            ps.setNull(23, Types.VARCHAR);
            ps.setNull(24, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.LARGECUSTOMERFACILITYA));

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
