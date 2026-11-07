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
import org.citygml4j.ade.iur.model.uro.PortStorageFacility;

import java.sql.*;

public class PortStorageFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public PortStorageFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTSTORAGEFACILITY)) + " " +
                "(id, innertotalfloorarea, innertotalfloorarea_uom, innerofsitearea, innerofsitearea_uom," +
                "outeroftotalfloorarea, outeroftotalfloorarea_uom, outersitearea, outersitearea_uom, maincargo," +
                "maincargo_codespace, storagecapacity, storagecapacityunit, storagecapacityuni_codespace," +
                "mainmaterial, mainmaterial_codespace, totalcost, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(PortStorageFacility portStorageFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (portStorageFacility.getInnerTotalFloorArea() != null && portStorageFacility.getInnerTotalFloorArea().isSetValue()) {
            ps.setDouble(2, portStorageFacility.getInnerTotalFloorArea().getValue());
            ps.setString(3, portStorageFacility.getInnerTotalFloorArea().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (portStorageFacility.getInnerOfSiteArea() != null && portStorageFacility.getInnerOfSiteArea().isSetValue()) {
            ps.setDouble(4, portStorageFacility.getInnerOfSiteArea().getValue());
            ps.setString(5, portStorageFacility.getInnerOfSiteArea().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (portStorageFacility.getOuterOfTotalFloorArea() != null && portStorageFacility.getOuterOfTotalFloorArea().isSetValue()) {
            ps.setDouble(6, portStorageFacility.getOuterOfTotalFloorArea().getValue());
            ps.setString(7, portStorageFacility.getOuterOfTotalFloorArea().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (portStorageFacility.getOuterSiteArea() != null && portStorageFacility.getOuterSiteArea().isSetValue()) {
            ps.setDouble(8, portStorageFacility.getOuterSiteArea().getValue());
            ps.setString(9, portStorageFacility.getOuterSiteArea().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (portStorageFacility.getMainCargo() != null && portStorageFacility.getMainCargo().isSetValue()) {
            ps.setString(10, portStorageFacility.getMainCargo().getValue());
            ps.setString(11, portStorageFacility.getMainCargo().getCodeSpace());
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        if (portStorageFacility.getStorageCapacity() != null)
            ps.setInt(12, portStorageFacility.getStorageCapacity());
        else
            ps.setNull(12, Types.INTEGER);

        if (portStorageFacility.getStorageCapacityUnit() != null && portStorageFacility.getStorageCapacityUnit().isSetValue()) {
            ps.setString(13, portStorageFacility.getStorageCapacityUnit().getValue());
            ps.setString(14, portStorageFacility.getStorageCapacityUnit().getCodeSpace());
        } else {
            ps.setNull(13, Types.VARCHAR);
            ps.setNull(14, Types.VARCHAR);
        }

        if (portStorageFacility.getMainMaterial() != null && portStorageFacility.getMainMaterial().isSetValue()) {
            ps.setString(15, portStorageFacility.getMainMaterial().getValue());
            ps.setString(16, portStorageFacility.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(15, Types.VARCHAR);
            ps.setNull(16, Types.VARCHAR);
        }

        if (portStorageFacility.getTotalCost() != null)
            ps.setInt(17, portStorageFacility.getTotalCost());
        else
            ps.setNull(17, Types.INTEGER);

        ps.setString(18, portStorageFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTSTORAGEFACILITY));
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
