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
import org.citygml4j.ade.iur.model.uro.IfcWall;

import java.sql.*;

public class IfcWallImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public IfcWallImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCWALL)) + " " +
                "(id, objectclass_id, nominallength, nominallength_uom, nominalwidth, nominalwidth_uom," +
                "nominalheight, nominalheight_uom, grossfootprintarea, grossfootprintarea_uom, netfootprintarea," +
                "netfootprintarea_uom, grosssidearea, grosssidearea_uom, netsidearea, netsidearea_uom," +
                "grosssidearealeft, grosssidearealeft_uom, netsidearealeft, netsidearealeft_uom, grosssidearearight," +
                "grosssidearearight_uom, netsidearearight, netsidearearight_uom, grossvolume, grossvolume_uom," +
                "netvolume, netvolume_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IfcWall ifcWall, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(ifcWall.getClass()));

        if (ifcWall.getNominalLength() != null && ifcWall.getNominalLength().isSetValue()) {
            ps.setDouble(3, ifcWall.getNominalLength().getValue());
            ps.setString(4, ifcWall.getNominalLength().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (ifcWall.getNominalWidth() != null && ifcWall.getNominalWidth().isSetValue()) {
            ps.setDouble(5, ifcWall.getNominalWidth().getValue());
            ps.setString(6, ifcWall.getNominalWidth().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (ifcWall.getNominalHeight() != null && ifcWall.getNominalHeight().isSetValue()) {
            ps.setDouble(7, ifcWall.getNominalHeight().getValue());
            ps.setString(8, ifcWall.getNominalHeight().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (ifcWall.getGrossFootPrintArea() != null && ifcWall.getGrossFootPrintArea().isSetValue()) {
            ps.setDouble(9, ifcWall.getGrossFootPrintArea().getValue());
            ps.setString(10, ifcWall.getGrossFootPrintArea().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (ifcWall.getNetFootPrintArea() != null && ifcWall.getNetFootPrintArea().isSetValue()) {
            ps.setDouble(11, ifcWall.getNetFootPrintArea().getValue());
            ps.setString(12, ifcWall.getNetFootPrintArea().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (ifcWall.getGrossSideArea() != null && ifcWall.getGrossSideArea().isSetValue()) {
            ps.setDouble(13, ifcWall.getGrossSideArea().getValue());
            ps.setString(14, ifcWall.getGrossSideArea().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (ifcWall.getNetSideArea() != null && ifcWall.getNetSideArea().isSetValue()) {
            ps.setDouble(15, ifcWall.getNetSideArea().getValue());
            ps.setString(16, ifcWall.getNetSideArea().getUom());
        } else {
            ps.setNull(15, Types.DOUBLE);
            ps.setNull(16, Types.VARCHAR);
        }

        if (ifcWall.getGrossSideAreaLeft() != null && ifcWall.getGrossSideAreaLeft().isSetValue()) {
            ps.setDouble(17, ifcWall.getGrossSideAreaLeft().getValue());
            ps.setString(18, ifcWall.getGrossSideAreaLeft().getUom());
        } else {
            ps.setNull(17, Types.DOUBLE);
            ps.setNull(18, Types.VARCHAR);
        }

        if (ifcWall.getNetSideAreaLeft() != null && ifcWall.getNetSideAreaLeft().isSetValue()) {
            ps.setDouble(19, ifcWall.getNetSideAreaLeft().getValue());
            ps.setString(20, ifcWall.getNetSideAreaLeft().getUom());
        } else {
            ps.setNull(19, Types.DOUBLE);
            ps.setNull(20, Types.VARCHAR);
        }

        if (ifcWall.getGrossSideAreaRight() != null && ifcWall.getGrossSideAreaRight().isSetValue()) {
            ps.setDouble(21, ifcWall.getGrossSideAreaRight().getValue());
            ps.setString(22, ifcWall.getGrossSideAreaRight().getUom());
        } else {
            ps.setNull(21, Types.DOUBLE);
            ps.setNull(22, Types.VARCHAR);
        }

        if (ifcWall.getNetSideAreaRight() != null && ifcWall.getNetSideAreaRight().isSetValue()) {
            ps.setDouble(23, ifcWall.getNetSideAreaRight().getValue());
            ps.setString(24, ifcWall.getNetSideAreaRight().getUom());
        } else {
            ps.setNull(23, Types.DOUBLE);
            ps.setNull(24, Types.VARCHAR);
        }

        if (ifcWall.getGrossVolume() != null && ifcWall.getGrossVolume().isSetValue()) {
            ps.setDouble(25, ifcWall.getGrossVolume().getValue());
            ps.setString(26, ifcWall.getGrossVolume().getUom());
        } else {
            ps.setNull(25, Types.DOUBLE);
            ps.setNull(26, Types.VARCHAR);
        }

        if (ifcWall.getNetVolume() != null && ifcWall.getNetVolume().isSetValue()) {
            ps.setDouble(27, ifcWall.getNetVolume().getValue());
            ps.setString(28, ifcWall.getNetVolume().getUom());
        } else {
            ps.setNull(27, Types.DOUBLE);
            ps.setNull(28, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCWALL));
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
