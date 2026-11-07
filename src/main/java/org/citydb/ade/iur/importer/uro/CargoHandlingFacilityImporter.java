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
import org.citygml4j.ade.iur.model.uro.CargoHandlingFacility;

import java.sql.*;
import java.time.LocalDate;

public class CargoHandlingFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public CargoHandlingFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CARGOHANDLINGFACILITY)) + " " +
                "(id, maincargo, maincargo_codespace, mooringfacility, liftableload, liftableload_uom, ability," +
                "packingname, packingname_codespace, acquisitionyear, innertotalfloorarea, innertotalfloorarea_uom," +
                "innerofsitearea, innerofsitearea_uom, outeroftotalfloorarea, outeroftotalfloorarea_uom," +
                "outersitearea, outersitearea_uom, mainmaterial, mainmaterial_codespace, totalcost, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(CargoHandlingFacility cargoHandlingFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (cargoHandlingFacility.getMainCargo() != null && cargoHandlingFacility.getMainCargo().isSetValue()) {
            ps.setString(2, cargoHandlingFacility.getMainCargo().getValue());
            ps.setString(3, cargoHandlingFacility.getMainCargo().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, cargoHandlingFacility.getMooringFacility());

        if (cargoHandlingFacility.getLiftableLoad() != null && cargoHandlingFacility.getLiftableLoad().isSetValue()) {
            ps.setDouble(5, cargoHandlingFacility.getLiftableLoad().getValue());
            ps.setString(6, cargoHandlingFacility.getLiftableLoad().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (cargoHandlingFacility.getAbility() != null)
            ps.setInt(7, cargoHandlingFacility.getAbility());
        else
            ps.setNull(7, Types.INTEGER);

        if (cargoHandlingFacility.getPackingName() != null && cargoHandlingFacility.getPackingName().isSetValue()) {
            ps.setString(8, cargoHandlingFacility.getPackingName().getValue());
            ps.setString(9, cargoHandlingFacility.getPackingName().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (cargoHandlingFacility.getAcquisitionYear() != null)
            ps.setDate(10, Date.valueOf(LocalDate.of(cargoHandlingFacility.getAcquisitionYear().getValue(), 1, 1)));
        else
            ps.setNull(10, Types.DATE);

        if (cargoHandlingFacility.getInnerTotalFloorArea() != null && cargoHandlingFacility.getInnerTotalFloorArea().isSetValue()) {
            ps.setDouble(11, cargoHandlingFacility.getInnerTotalFloorArea().getValue());
            ps.setString(12, cargoHandlingFacility.getInnerTotalFloorArea().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (cargoHandlingFacility.getInnerOfSiteArea() != null && cargoHandlingFacility.getInnerOfSiteArea().isSetValue()) {
            ps.setDouble(13, cargoHandlingFacility.getInnerOfSiteArea().getValue());
            ps.setString(14, cargoHandlingFacility.getInnerOfSiteArea().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (cargoHandlingFacility.getOuterOfTotalFloorArea() != null && cargoHandlingFacility.getOuterOfTotalFloorArea().isSetValue()) {
            ps.setDouble(15, cargoHandlingFacility.getOuterOfTotalFloorArea().getValue());
            ps.setString(16, cargoHandlingFacility.getOuterOfTotalFloorArea().getUom());
        } else {
            ps.setNull(15, Types.DOUBLE);
            ps.setNull(16, Types.VARCHAR);
        }

        if (cargoHandlingFacility.getOuterSiteArea() != null && cargoHandlingFacility.getOuterSiteArea().isSetValue()) {
            ps.setDouble(17, cargoHandlingFacility.getOuterSiteArea().getValue());
            ps.setString(18, cargoHandlingFacility.getOuterSiteArea().getUom());
        } else {
            ps.setNull(17, Types.DOUBLE);
            ps.setNull(18, Types.VARCHAR);
        }

        if (cargoHandlingFacility.getMainMaterial() != null && cargoHandlingFacility.getMainMaterial().isSetValue()) {
            ps.setString(19, cargoHandlingFacility.getMainMaterial().getValue());
            ps.setString(20, cargoHandlingFacility.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        if (cargoHandlingFacility.getTotalCost() != null)
            ps.setInt(21, cargoHandlingFacility.getTotalCost());
        else
            ps.setNull(21, Types.INTEGER);

        ps.setString(22, cargoHandlingFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.CARGOHANDLINGFACILITY));
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
