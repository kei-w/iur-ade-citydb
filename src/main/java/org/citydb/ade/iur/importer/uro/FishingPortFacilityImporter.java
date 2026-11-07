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
import org.citygml4j.ade.iur.model.uro.FishingPortFacility;

import java.sql.*;

public class FishingPortFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public FishingPortFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FISHINGPORTFACILITY)) + " " +
                "(id, facilitydetailstype, facilitydetailstyp_codespace, portname, porttype, porttype_codespace," +
                "address, designatedarea, designation, designatedadministrator, referencenumber, granttype," +
                "granttype_codespace, administrator, facilitymanager, structuretype, structuretype_codespace," +
                "mainmaterial, mainmaterial_codespace, otherstructure, length, length_uom, width, width_uom," +
                "ceilingheight, ceilingheight_uom, depth, depth_uom, area, area_uom, othersizedescription," +
                "dateofconstructionoracquisit, cost, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(FishingPortFacility fishingPortFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (fishingPortFacility.getFacilityDetailsType() != null && fishingPortFacility.getFacilityDetailsType().isSetValue()) {
            ps.setString(2, fishingPortFacility.getFacilityDetailsType().getValue());
            ps.setString(3, fishingPortFacility.getFacilityDetailsType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, fishingPortFacility.getPortName());

        if (fishingPortFacility.getPortType() != null && fishingPortFacility.getPortType().isSetValue()) {
            ps.setString(5, fishingPortFacility.getPortType().getValue());
            ps.setString(6, fishingPortFacility.getPortType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        ps.setString(7, fishingPortFacility.getAddress());

        ps.setString(8, fishingPortFacility.getDesignatedArea());

        if (!fishingPortFacility.getDesignation().isEmpty())
            ps.setString(9, valueJoiner.join(fishingPortFacility.getDesignation()));
        else
            ps.setNull(9, Types.VARCHAR);

        if (!fishingPortFacility.getDesignatedAdministrator().isEmpty())
            ps.setString(10, valueJoiner.join(fishingPortFacility.getDesignatedAdministrator()));
        else
            ps.setNull(10, Types.VARCHAR);

        if (!fishingPortFacility.getReferenceNumber().isEmpty())
            ps.setString(11, valueJoiner.join(fishingPortFacility.getReferenceNumber()));
        else
            ps.setNull(11, Types.VARCHAR);

        if (fishingPortFacility.getGrantType() != null && fishingPortFacility.getGrantType().isSetValue()) {
            ps.setString(12, fishingPortFacility.getGrantType().getValue());
            ps.setString(13, fishingPortFacility.getGrantType().getCodeSpace());
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        ps.setString(14, fishingPortFacility.getAdministrator());

        ps.setString(15, fishingPortFacility.getFacilityManager());

        if (fishingPortFacility.getStructureType() != null && fishingPortFacility.getStructureType().isSetValue()) {
            ps.setString(16, fishingPortFacility.getStructureType().getValue());
            ps.setString(17, fishingPortFacility.getStructureType().getCodeSpace());
        } else {
            ps.setNull(16, Types.VARCHAR);
            ps.setNull(17, Types.VARCHAR);
        }

        if (fishingPortFacility.getMainMaterial() != null && fishingPortFacility.getMainMaterial().isSetValue()) {
            ps.setString(18, fishingPortFacility.getMainMaterial().getValue());
            ps.setString(19, fishingPortFacility.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(18, Types.VARCHAR);
            ps.setNull(19, Types.VARCHAR);
        }

        ps.setString(20, fishingPortFacility.getOtherStructure());

        if (fishingPortFacility.getLength() != null && fishingPortFacility.getLength().isSetValue()) {
            ps.setDouble(21, fishingPortFacility.getLength().getValue());
            ps.setString(22, fishingPortFacility.getLength().getUom());
        } else {
            ps.setNull(21, Types.DOUBLE);
            ps.setNull(22, Types.VARCHAR);
        }

        if (fishingPortFacility.getWidth() != null && fishingPortFacility.getWidth().isSetValue()) {
            ps.setDouble(23, fishingPortFacility.getWidth().getValue());
            ps.setString(24, fishingPortFacility.getWidth().getUom());
        } else {
            ps.setNull(23, Types.DOUBLE);
            ps.setNull(24, Types.VARCHAR);
        }

        if (fishingPortFacility.getCeilingHeight() != null && fishingPortFacility.getCeilingHeight().isSetValue()) {
            ps.setDouble(25, fishingPortFacility.getCeilingHeight().getValue());
            ps.setString(26, fishingPortFacility.getCeilingHeight().getUom());
        } else {
            ps.setNull(25, Types.DOUBLE);
            ps.setNull(26, Types.VARCHAR);
        }

        if (fishingPortFacility.getDepth() != null && fishingPortFacility.getDepth().isSetValue()) {
            ps.setDouble(27, fishingPortFacility.getDepth().getValue());
            ps.setString(28, fishingPortFacility.getDepth().getUom());
        } else {
            ps.setNull(27, Types.DOUBLE);
            ps.setNull(28, Types.VARCHAR);
        }

        if (fishingPortFacility.getArea() != null && fishingPortFacility.getArea().isSetValue()) {
            ps.setDouble(29, fishingPortFacility.getArea().getValue());
            ps.setString(30, fishingPortFacility.getArea().getUom());
        } else {
            ps.setNull(29, Types.DOUBLE);
            ps.setNull(30, Types.VARCHAR);
        }

        ps.setString(31, fishingPortFacility.getOtherSizeDescription());

        if (fishingPortFacility.getDateOfConstructionOrAcquisition() != null)
            ps.setDate(32, Date.valueOf(fishingPortFacility.getDateOfConstructionOrAcquisition()));
        else
            ps.setNull(32, Types.DATE);

        if (fishingPortFacility.getCost() != null)
            ps.setInt(33, fishingPortFacility.getCost());
        else
            ps.setNull(33, Types.INTEGER);

        ps.setString(34, fishingPortFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FISHINGPORTFACILITY));
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
