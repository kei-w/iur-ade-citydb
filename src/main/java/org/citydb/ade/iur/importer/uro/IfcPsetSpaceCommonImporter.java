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
import org.citygml4j.ade.iur.model.uro.IfcPsetSpaceCommon;

import java.sql.*;

public class IfcPsetSpaceCommonImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public IfcPsetSpaceCommonImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCPSETSPACECOMMON)) + " " +
                "(id, reference, category, floorcovering, wallcovering, ceilingcovering, skirtingboard," +
                "grossplannedarea, grossplannedarea_uom, netplannedarea, netplannedarea_uom, publiclyaccessible," +
                "handicapaccessible, concealedflooring, concealedceiling) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IfcPsetSpaceCommon ifcPsetSpaceCommon, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, ifcPsetSpaceCommon.getReference());

        ps.setString(3, ifcPsetSpaceCommon.getCategory());

        ps.setString(4, ifcPsetSpaceCommon.getFloorCovering());

        ps.setString(5, ifcPsetSpaceCommon.getWallCovering());

        ps.setString(6, ifcPsetSpaceCommon.getCeilingCovering());

        ps.setString(7, ifcPsetSpaceCommon.getSkirtingBoard());

        if (ifcPsetSpaceCommon.getGrossPlannedArea() != null && ifcPsetSpaceCommon.getGrossPlannedArea().isSetValue()) {
            ps.setDouble(8, ifcPsetSpaceCommon.getGrossPlannedArea().getValue());
            ps.setString(9, ifcPsetSpaceCommon.getGrossPlannedArea().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (ifcPsetSpaceCommon.getNetPlannedArea() != null && ifcPsetSpaceCommon.getNetPlannedArea().isSetValue()) {
            ps.setDouble(10, ifcPsetSpaceCommon.getNetPlannedArea().getValue());
            ps.setString(11, ifcPsetSpaceCommon.getNetPlannedArea().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (ifcPsetSpaceCommon.getPubliclyAccessible() != null)
            ps.setInt(12, ifcPsetSpaceCommon.getPubliclyAccessible() ? 1 : 0);
        else
            ps.setNull(12, Types.INTEGER);

        if (ifcPsetSpaceCommon.getHandicapAccessible() != null)
            ps.setInt(13, ifcPsetSpaceCommon.getHandicapAccessible() ? 1 : 0);
        else
            ps.setNull(13, Types.INTEGER);

        if (ifcPsetSpaceCommon.getConcealedFlooring() != null)
            ps.setInt(14, ifcPsetSpaceCommon.getConcealedFlooring() ? 1 : 0);
        else
            ps.setNull(14, Types.INTEGER);

        if (ifcPsetSpaceCommon.getConcealedCeiling() != null)
            ps.setInt(15, ifcPsetSpaceCommon.getConcealedCeiling() ? 1 : 0);
        else
            ps.setNull(15, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCPSETSPACECOMMON));
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
