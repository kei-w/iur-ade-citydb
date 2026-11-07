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
import org.citygml4j.ade.iur.model.uro.PortWasteTreatmentFacility;

import java.sql.*;
import java.time.LocalDate;

public class PortWasteTreatmentFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public PortWasteTreatmentFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTWASTETREATMENTFACI)) + " " +
                "(id, structuretype, structuretype_codespace, perimeter, perimeter_uom, mainpartlength," +
                "mainpartlength_uom, innershorelength, innershorelength_uom, ceilingheight, ceilingheight_uom," +
                "wavedissipatorlength, wavedissipatorlength_uom, mainmaterial, mainmaterial_codespace, wastetype," +
                "wastetype_codespace, planneddisposalarea, planneddisposalarea_uom, planneddisposalamount," +
                "receivingcapacity, shiptype, unitofreceivingcapacity, unitofreceivingcap_codespace," +
                "acquisitionyear, totalcost, subsidy, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(PortWasteTreatmentFacility portWasteTreatmentFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (portWasteTreatmentFacility.getStructureType() != null && portWasteTreatmentFacility.getStructureType().isSetValue()) {
            ps.setString(2, portWasteTreatmentFacility.getStructureType().getValue());
            ps.setString(3, portWasteTreatmentFacility.getStructureType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getPerimeter() != null && portWasteTreatmentFacility.getPerimeter().isSetValue()) {
            ps.setDouble(4, portWasteTreatmentFacility.getPerimeter().getValue());
            ps.setString(5, portWasteTreatmentFacility.getPerimeter().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getMainPartLength() != null && portWasteTreatmentFacility.getMainPartLength().isSetValue()) {
            ps.setDouble(6, portWasteTreatmentFacility.getMainPartLength().getValue());
            ps.setString(7, portWasteTreatmentFacility.getMainPartLength().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getInnerShoreLength() != null && portWasteTreatmentFacility.getInnerShoreLength().isSetValue()) {
            ps.setDouble(8, portWasteTreatmentFacility.getInnerShoreLength().getValue());
            ps.setString(9, portWasteTreatmentFacility.getInnerShoreLength().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getCeilingHeight() != null && portWasteTreatmentFacility.getCeilingHeight().isSetValue()) {
            ps.setDouble(10, portWasteTreatmentFacility.getCeilingHeight().getValue());
            ps.setString(11, portWasteTreatmentFacility.getCeilingHeight().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getWaveDissipatorLength() != null && portWasteTreatmentFacility.getWaveDissipatorLength().isSetValue()) {
            ps.setDouble(12, portWasteTreatmentFacility.getWaveDissipatorLength().getValue());
            ps.setString(13, portWasteTreatmentFacility.getWaveDissipatorLength().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getMainMaterial() != null && portWasteTreatmentFacility.getMainMaterial().isSetValue()) {
            ps.setString(14, portWasteTreatmentFacility.getMainMaterial().getValue());
            ps.setString(15, portWasteTreatmentFacility.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getWasteType() != null && portWasteTreatmentFacility.getWasteType().isSetValue()) {
            ps.setString(16, portWasteTreatmentFacility.getWasteType().getValue());
            ps.setString(17, portWasteTreatmentFacility.getWasteType().getCodeSpace());
        } else {
            ps.setNull(16, Types.VARCHAR);
            ps.setNull(17, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getPlannedDisposalArea() != null && portWasteTreatmentFacility.getPlannedDisposalArea().isSetValue()) {
            ps.setDouble(18, portWasteTreatmentFacility.getPlannedDisposalArea().getValue());
            ps.setString(19, portWasteTreatmentFacility.getPlannedDisposalArea().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getPlannedDisposalAmount() != null)
            ps.setInt(20, portWasteTreatmentFacility.getPlannedDisposalAmount());
        else
            ps.setNull(20, Types.INTEGER);

        if (portWasteTreatmentFacility.getReceivingCapacity() != null)
            ps.setInt(21, portWasteTreatmentFacility.getReceivingCapacity());
        else
            ps.setNull(21, Types.INTEGER);

        ps.setString(22, portWasteTreatmentFacility.getShipType());

        if (portWasteTreatmentFacility.getUnitOfReceivingCapacity() != null && portWasteTreatmentFacility.getUnitOfReceivingCapacity().isSetValue()) {
            ps.setString(23, portWasteTreatmentFacility.getUnitOfReceivingCapacity().getValue());
            ps.setString(24, portWasteTreatmentFacility.getUnitOfReceivingCapacity().getCodeSpace());
        } else {
            ps.setNull(23, Types.VARCHAR);
            ps.setNull(24, Types.VARCHAR);
        }

        if (portWasteTreatmentFacility.getAcquisitionYear() != null)
            ps.setDate(25, Date.valueOf(LocalDate.of(portWasteTreatmentFacility.getAcquisitionYear().getValue(), 1, 1)));
        else
            ps.setNull(25, Types.DATE);

        if (portWasteTreatmentFacility.getTotalCost() != null)
            ps.setInt(26, portWasteTreatmentFacility.getTotalCost());
        else
            ps.setNull(26, Types.INTEGER);

        if (portWasteTreatmentFacility.getSubsidy() != null)
            ps.setInt(27, portWasteTreatmentFacility.getSubsidy());
        else
            ps.setNull(27, Types.INTEGER);

        ps.setString(28, portWasteTreatmentFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTWASTETREATMENTFACI));
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
