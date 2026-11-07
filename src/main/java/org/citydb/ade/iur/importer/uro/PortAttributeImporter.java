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
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.CargoHandlingFacility;
import org.citygml4j.ade.iur.model.uro.CyberportMarinaAndPBS;
import org.citygml4j.ade.iur.model.uro.HarborFacility;
import org.citygml4j.ade.iur.model.uro.MooringFacility;
import org.citygml4j.ade.iur.model.uro.NavigationAssistanceFacility;
import org.citygml4j.ade.iur.model.uro.PortAttribute;
import org.citygml4j.ade.iur.model.uro.PortEnvironmentalImprovementFacility;
import org.citygml4j.ade.iur.model.uro.PortManagementFacility;
import org.citygml4j.ade.iur.model.uro.PortPassengerFacility;
import org.citygml4j.ade.iur.model.uro.PortPollutionControlFacility;
import org.citygml4j.ade.iur.model.uro.PortProtectiveFacility;
import org.citygml4j.ade.iur.model.uro.PortStorageFacility;
import org.citygml4j.ade.iur.model.uro.PortTransportationFacility;
import org.citygml4j.ade.iur.model.uro.PortWasteTreatmentFacility;
import org.citygml4j.ade.iur.model.uro.PortWelfareFacility;
import org.citygml4j.ade.iur.model.uro.ShipServiceFacility;

import java.sql.*;

public class PortAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final HarborFacilityImporter harborFacilityImporter;
    private final PortProtectiveFacilityImporter portProtectiveFacilityImporter;
    private final MooringFacilityImporter mooringFacilityImporter;
    private final PortTransportationFacilityImporter portTransportationFacilityImporter;
    private final NavigationAssistanceFacilityImporter navigationAssistanceFacilityImporter;
    private final CargoHandlingFacilityImporter cargoHandlingFacilityImporter;
    private final PortPassengerFacilityImporter portPassengerFacilityImporter;
    private final PortStorageFacilityImporter portStorageFacilityImporter;
    private final ShipServiceFacilityImporter shipServiceFacilityImporter;
    private final PortWasteTreatmentFacilityImporter portWasteTreatmentFacilityImporter;
    private final PortEnvironmentalImprovementFacilityImporter portEnvironmentalImprovementFacilityImporter;
    private final PortPollutionControlFacilityImporter portPollutionControlFacilityImporter;
    private final PortWelfareFacilityImporter portWelfareFacilityImporter;
    private final PortManagementFacilityImporter portManagementFacilityImporter;
    private final CyberportMarinaAndPBSImporter cyberportMarinaAndPBSImporter;

    private int batchCounter;

    public PortAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTATTRIBUTE)) + " " +
                "(id, objectclass_id, portfacilitydetailstype, portfacilitydetail_codespace, portname, portstatus," +
                "portstatus_codespace, district, granttype, granttype_codespace, isdesignated, degradationlevel) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        harborFacilityImporter = manager.getImporter(HarborFacilityImporter.class);
        portProtectiveFacilityImporter = manager.getImporter(PortProtectiveFacilityImporter.class);
        mooringFacilityImporter = manager.getImporter(MooringFacilityImporter.class);
        portTransportationFacilityImporter = manager.getImporter(PortTransportationFacilityImporter.class);
        navigationAssistanceFacilityImporter = manager.getImporter(NavigationAssistanceFacilityImporter.class);
        cargoHandlingFacilityImporter = manager.getImporter(CargoHandlingFacilityImporter.class);
        portPassengerFacilityImporter = manager.getImporter(PortPassengerFacilityImporter.class);
        portStorageFacilityImporter = manager.getImporter(PortStorageFacilityImporter.class);
        shipServiceFacilityImporter = manager.getImporter(ShipServiceFacilityImporter.class);
        portWasteTreatmentFacilityImporter = manager.getImporter(PortWasteTreatmentFacilityImporter.class);
        portEnvironmentalImprovementFacilityImporter = manager.getImporter(PortEnvironmentalImprovementFacilityImporter.class);
        portPollutionControlFacilityImporter = manager.getImporter(PortPollutionControlFacilityImporter.class);
        portWelfareFacilityImporter = manager.getImporter(PortWelfareFacilityImporter.class);
        portManagementFacilityImporter = manager.getImporter(PortManagementFacilityImporter.class);
        cyberportMarinaAndPBSImporter = manager.getImporter(CyberportMarinaAndPBSImporter.class);
    }

    public void doImport(PortAttribute portAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(portAttribute.getClass()));

        if (portAttribute.getPortFacilityDetailsType() != null && portAttribute.getPortFacilityDetailsType().isSetValue()) {
            ps.setString(3, portAttribute.getPortFacilityDetailsType().getValue());
            ps.setString(4, portAttribute.getPortFacilityDetailsType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        ps.setString(5, portAttribute.getPortName());

        if (portAttribute.getPortStatus() != null && portAttribute.getPortStatus().isSetValue()) {
            ps.setString(6, portAttribute.getPortStatus().getValue());
            ps.setString(7, portAttribute.getPortStatus().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        ps.setString(8, portAttribute.getDistrict());

        if (portAttribute.getGrantType() != null && portAttribute.getGrantType().isSetValue()) {
            ps.setString(9, portAttribute.getGrantType().getValue());
            ps.setString(10, portAttribute.getGrantType().getCodeSpace());
        } else {
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.VARCHAR);
        }

        if (portAttribute.getIsDesignated() != null)
            ps.setInt(11, portAttribute.getIsDesignated() ? 1 : 0);
        else
            ps.setNull(11, Types.INTEGER);

        if (portAttribute.getDegradationLevel() != null)
            ps.setInt(12, portAttribute.getDegradationLevel());
        else
            ps.setNull(12, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTATTRIBUTE));

        if (portAttribute instanceof HarborFacility)
            harborFacilityImporter.doImport((HarborFacility) portAttribute, objectId);

        if (portAttribute instanceof PortProtectiveFacility)
            portProtectiveFacilityImporter.doImport((PortProtectiveFacility) portAttribute, objectId);

        if (portAttribute instanceof MooringFacility)
            mooringFacilityImporter.doImport((MooringFacility) portAttribute, objectId);

        if (portAttribute instanceof PortTransportationFacility)
            portTransportationFacilityImporter.doImport((PortTransportationFacility) portAttribute, objectId);

        if (portAttribute instanceof NavigationAssistanceFacility)
            navigationAssistanceFacilityImporter.doImport((NavigationAssistanceFacility) portAttribute, objectId);

        if (portAttribute instanceof CargoHandlingFacility)
            cargoHandlingFacilityImporter.doImport((CargoHandlingFacility) portAttribute, objectId);

        if (portAttribute instanceof PortPassengerFacility)
            portPassengerFacilityImporter.doImport((PortPassengerFacility) portAttribute, objectId);

        if (portAttribute instanceof PortStorageFacility)
            portStorageFacilityImporter.doImport((PortStorageFacility) portAttribute, objectId);

        if (portAttribute instanceof ShipServiceFacility)
            shipServiceFacilityImporter.doImport((ShipServiceFacility) portAttribute, objectId);

        if (portAttribute instanceof PortWasteTreatmentFacility)
            portWasteTreatmentFacilityImporter.doImport((PortWasteTreatmentFacility) portAttribute, objectId);

        if (portAttribute instanceof PortEnvironmentalImprovementFacility)
            portEnvironmentalImprovementFacilityImporter.doImport((PortEnvironmentalImprovementFacility) portAttribute, objectId);

        if (portAttribute instanceof PortPollutionControlFacility)
            portPollutionControlFacilityImporter.doImport((PortPollutionControlFacility) portAttribute, objectId);

        if (portAttribute instanceof PortWelfareFacility)
            portWelfareFacilityImporter.doImport((PortWelfareFacility) portAttribute, objectId);

        if (portAttribute instanceof PortManagementFacility)
            portManagementFacilityImporter.doImport((PortManagementFacility) portAttribute, objectId);

        if (portAttribute instanceof CyberportMarinaAndPBS)
            cyberportMarinaAndPBSImporter.doImport((CyberportMarinaAndPBS) portAttribute, objectId);
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
