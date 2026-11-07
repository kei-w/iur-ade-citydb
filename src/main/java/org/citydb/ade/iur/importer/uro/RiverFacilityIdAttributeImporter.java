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
import org.citygml4j.ade.iur.model.uro.RiverFacilityIdAttribute;

import java.sql.*;

public class RiverFacilityIdAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public RiverFacilityIdAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.RIVERFACILITYIDATTRIBU)) + " " +
                "(id, rivercode, rivercode_codespace, rivername, sidetype, sidetype_codespace, leftpost," +
                "leftpost_uom, leftdistance, leftdistance_uom, rightpost, rightpost_uom, rightdistance," +
                "rightdistance_uom, leftstartpost, leftstartpost_uom, leftstartdistance, leftstartdistance_uom," +
                "leftendpost, leftendpost_uom, leftenddistance, leftenddistance_uom, rightstartpost," +
                "rightstartpost_uom, rightstartdistance, rightstartdistance_uom, rightendpost, rightendpost_uom," +
                "rightenddistance, rightenddistance_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(RiverFacilityIdAttribute riverFacilityIdAttribute, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (riverFacilityIdAttribute.getRiverCode() != null && riverFacilityIdAttribute.getRiverCode().isSetValue()) {
            ps.setString(2, riverFacilityIdAttribute.getRiverCode().getValue());
            ps.setString(3, riverFacilityIdAttribute.getRiverCode().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, riverFacilityIdAttribute.getRiverName());

        if (riverFacilityIdAttribute.getSideType() != null && riverFacilityIdAttribute.getSideType().isSetValue()) {
            ps.setString(5, riverFacilityIdAttribute.getSideType().getValue());
            ps.setString(6, riverFacilityIdAttribute.getSideType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getLeftPost() != null && riverFacilityIdAttribute.getLeftPost().isSetValue()) {
            ps.setDouble(7, riverFacilityIdAttribute.getLeftPost().getValue());
            ps.setString(8, riverFacilityIdAttribute.getLeftPost().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getLeftDistance() != null && riverFacilityIdAttribute.getLeftDistance().isSetValue()) {
            ps.setDouble(9, riverFacilityIdAttribute.getLeftDistance().getValue());
            ps.setString(10, riverFacilityIdAttribute.getLeftDistance().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getRightPost() != null && riverFacilityIdAttribute.getRightPost().isSetValue()) {
            ps.setDouble(11, riverFacilityIdAttribute.getRightPost().getValue());
            ps.setString(12, riverFacilityIdAttribute.getRightPost().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getRightDistance() != null && riverFacilityIdAttribute.getRightDistance().isSetValue()) {
            ps.setDouble(13, riverFacilityIdAttribute.getRightDistance().getValue());
            ps.setString(14, riverFacilityIdAttribute.getRightDistance().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getLeftStartPost() != null && riverFacilityIdAttribute.getLeftStartPost().isSetValue()) {
            ps.setDouble(15, riverFacilityIdAttribute.getLeftStartPost().getValue());
            ps.setString(16, riverFacilityIdAttribute.getLeftStartPost().getUom());
        } else {
            ps.setNull(15, Types.DOUBLE);
            ps.setNull(16, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getLeftStartDistance() != null && riverFacilityIdAttribute.getLeftStartDistance().isSetValue()) {
            ps.setDouble(17, riverFacilityIdAttribute.getLeftStartDistance().getValue());
            ps.setString(18, riverFacilityIdAttribute.getLeftStartDistance().getUom());
        } else {
            ps.setNull(17, Types.DOUBLE);
            ps.setNull(18, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getLeftEndPost() != null && riverFacilityIdAttribute.getLeftEndPost().isSetValue()) {
            ps.setDouble(19, riverFacilityIdAttribute.getLeftEndPost().getValue());
            ps.setString(20, riverFacilityIdAttribute.getLeftEndPost().getUom());
        } else {
            ps.setNull(19, Types.DOUBLE);
            ps.setNull(20, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getLeftEndDistance() != null && riverFacilityIdAttribute.getLeftEndDistance().isSetValue()) {
            ps.setDouble(21, riverFacilityIdAttribute.getLeftEndDistance().getValue());
            ps.setString(22, riverFacilityIdAttribute.getLeftEndDistance().getUom());
        } else {
            ps.setNull(21, Types.DOUBLE);
            ps.setNull(22, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getRightStartPost() != null && riverFacilityIdAttribute.getRightStartPost().isSetValue()) {
            ps.setDouble(23, riverFacilityIdAttribute.getRightStartPost().getValue());
            ps.setString(24, riverFacilityIdAttribute.getRightStartPost().getUom());
        } else {
            ps.setNull(23, Types.DOUBLE);
            ps.setNull(24, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getRightStartDistance() != null && riverFacilityIdAttribute.getRightStartDistance().isSetValue()) {
            ps.setDouble(25, riverFacilityIdAttribute.getRightStartDistance().getValue());
            ps.setString(26, riverFacilityIdAttribute.getRightStartDistance().getUom());
        } else {
            ps.setNull(25, Types.DOUBLE);
            ps.setNull(26, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getRightEndPost() != null && riverFacilityIdAttribute.getRightEndPost().isSetValue()) {
            ps.setDouble(27, riverFacilityIdAttribute.getRightEndPost().getValue());
            ps.setString(28, riverFacilityIdAttribute.getRightEndPost().getUom());
        } else {
            ps.setNull(27, Types.DOUBLE);
            ps.setNull(28, Types.VARCHAR);
        }

        if (riverFacilityIdAttribute.getRightEndDistance() != null && riverFacilityIdAttribute.getRightEndDistance().isSetValue()) {
            ps.setDouble(29, riverFacilityIdAttribute.getRightEndDistance().getValue());
            ps.setString(30, riverFacilityIdAttribute.getRightEndDistance().getUom());
        } else {
            ps.setNull(29, Types.DOUBLE);
            ps.setNull(30, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.RIVERFACILITYIDATTRIBU));
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
