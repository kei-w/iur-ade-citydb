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
import org.citygml4j.ade.iur.model.uro.IfcPsetBuildingCommon;

import java.sql.*;
import java.time.LocalDate;

public class IfcPsetBuildingCommonImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public IfcPsetBuildingCommonImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCPSETBUILDINGCOMMON)) + " " +
                "(id, buildingid, ispermanentid, mainfireuse, ancillaryfireuse, sprinklerprotection," +
                "sprinklerprotectionautomatic, occupancytype, occupancytype_codespace, grossplannedarea," +
                "grossplannedarea_uom, numberofstoreys, yearofconstruction, islandmarked) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IfcPsetBuildingCommon ifcPsetBuildingCommon, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, ifcPsetBuildingCommon.getBuildingId());

        if (ifcPsetBuildingCommon.getIsPermanentId() != null)
            ps.setInt(3, ifcPsetBuildingCommon.getIsPermanentId() ? 1 : 0);
        else
            ps.setNull(3, Types.INTEGER);

        ps.setString(4, ifcPsetBuildingCommon.getMainFireUse());

        ps.setString(5, ifcPsetBuildingCommon.getAncillaryFireUse());

        if (ifcPsetBuildingCommon.getSprinklerProtection() != null)
            ps.setInt(6, ifcPsetBuildingCommon.getSprinklerProtection() ? 1 : 0);
        else
            ps.setNull(6, Types.INTEGER);

        if (ifcPsetBuildingCommon.getSprinklerProtectionAutomatic() != null)
            ps.setInt(7, ifcPsetBuildingCommon.getSprinklerProtectionAutomatic() ? 1 : 0);
        else
            ps.setNull(7, Types.INTEGER);

        if (ifcPsetBuildingCommon.getOccupancyType() != null && ifcPsetBuildingCommon.getOccupancyType().isSetValue()) {
            ps.setString(8, ifcPsetBuildingCommon.getOccupancyType().getValue());
            ps.setString(9, ifcPsetBuildingCommon.getOccupancyType().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (ifcPsetBuildingCommon.getGrossPlannedArea() != null && ifcPsetBuildingCommon.getGrossPlannedArea().isSetValue()) {
            ps.setDouble(10, ifcPsetBuildingCommon.getGrossPlannedArea().getValue());
            ps.setString(11, ifcPsetBuildingCommon.getGrossPlannedArea().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (ifcPsetBuildingCommon.getNumberOfStoreys() != null)
            ps.setInt(12, ifcPsetBuildingCommon.getNumberOfStoreys());
        else
            ps.setNull(12, Types.INTEGER);

        if (ifcPsetBuildingCommon.getYearOfConstruction() != null)
            ps.setDate(13, Date.valueOf(LocalDate.of(ifcPsetBuildingCommon.getYearOfConstruction().getValue(), 1, 1)));
        else
            ps.setNull(13, Types.DATE);

        if (ifcPsetBuildingCommon.getIsLandmarked() != null)
            ps.setInt(14, ifcPsetBuildingCommon.getIsLandmarked() ? 1 : 0);
        else
            ps.setNull(14, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCPSETBUILDINGCOMMON));
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
