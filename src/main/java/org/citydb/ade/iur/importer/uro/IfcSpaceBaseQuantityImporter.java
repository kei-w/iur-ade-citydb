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
import org.citygml4j.ade.iur.model.uro.IfcSpaceBaseQuantity;

import java.sql.*;

public class IfcSpaceBaseQuantityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public IfcSpaceBaseQuantityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCSPACEBASEQUANTITY)) + " " +
                "(id, nominalheight, nominalheight_uom, clearheight, clearheight_uom, finishceilingheight," +
                "finishceilingheight_uom, grossperimeter, grossperimeter_uom, netperimeter, netperimeter_uom," +
                "grossceilingarea, grossceilingarea_uom, grossfloorarea, grossfloorarea_uom, netceilingarea," +
                "netceilingarea_uom, netfloorarea, netfloorarea_uom, grosswallarea, grosswallarea_uom, netwallarea," +
                "netwallarea_uom, grossvolume, grossvolume_uom, netvolume, netvolume_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IfcSpaceBaseQuantity ifcSpaceBaseQuantity, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (ifcSpaceBaseQuantity.getNominalHeight() != null && ifcSpaceBaseQuantity.getNominalHeight().isSetValue()) {
            ps.setDouble(2, ifcSpaceBaseQuantity.getNominalHeight().getValue());
            ps.setString(3, ifcSpaceBaseQuantity.getNominalHeight().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getClearHeight() != null && ifcSpaceBaseQuantity.getClearHeight().isSetValue()) {
            ps.setDouble(4, ifcSpaceBaseQuantity.getClearHeight().getValue());
            ps.setString(5, ifcSpaceBaseQuantity.getClearHeight().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getFinishCeilingHeight() != null && ifcSpaceBaseQuantity.getFinishCeilingHeight().isSetValue()) {
            ps.setDouble(6, ifcSpaceBaseQuantity.getFinishCeilingHeight().getValue());
            ps.setString(7, ifcSpaceBaseQuantity.getFinishCeilingHeight().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getGrossPerimeter() != null && ifcSpaceBaseQuantity.getGrossPerimeter().isSetValue()) {
            ps.setDouble(8, ifcSpaceBaseQuantity.getGrossPerimeter().getValue());
            ps.setString(9, ifcSpaceBaseQuantity.getGrossPerimeter().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getNetPerimeter() != null && ifcSpaceBaseQuantity.getNetPerimeter().isSetValue()) {
            ps.setDouble(10, ifcSpaceBaseQuantity.getNetPerimeter().getValue());
            ps.setString(11, ifcSpaceBaseQuantity.getNetPerimeter().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getGrossCeilingArea() != null && ifcSpaceBaseQuantity.getGrossCeilingArea().isSetValue()) {
            ps.setDouble(12, ifcSpaceBaseQuantity.getGrossCeilingArea().getValue());
            ps.setString(13, ifcSpaceBaseQuantity.getGrossCeilingArea().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getGrossFloorArea() != null && ifcSpaceBaseQuantity.getGrossFloorArea().isSetValue()) {
            ps.setDouble(14, ifcSpaceBaseQuantity.getGrossFloorArea().getValue());
            ps.setString(15, ifcSpaceBaseQuantity.getGrossFloorArea().getUom());
        } else {
            ps.setNull(14, Types.DOUBLE);
            ps.setNull(15, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getNetCeilingArea() != null && ifcSpaceBaseQuantity.getNetCeilingArea().isSetValue()) {
            ps.setDouble(16, ifcSpaceBaseQuantity.getNetCeilingArea().getValue());
            ps.setString(17, ifcSpaceBaseQuantity.getNetCeilingArea().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getNetFloorArea() != null && ifcSpaceBaseQuantity.getNetFloorArea().isSetValue()) {
            ps.setDouble(18, ifcSpaceBaseQuantity.getNetFloorArea().getValue());
            ps.setString(19, ifcSpaceBaseQuantity.getNetFloorArea().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getGrossWallArea() != null && ifcSpaceBaseQuantity.getGrossWallArea().isSetValue()) {
            ps.setDouble(20, ifcSpaceBaseQuantity.getGrossWallArea().getValue());
            ps.setString(21, ifcSpaceBaseQuantity.getGrossWallArea().getUom());
        } else {
            ps.setNull(20, Types.DOUBLE);
            ps.setNull(21, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getNetWallArea() != null && ifcSpaceBaseQuantity.getNetWallArea().isSetValue()) {
            ps.setDouble(22, ifcSpaceBaseQuantity.getNetWallArea().getValue());
            ps.setString(23, ifcSpaceBaseQuantity.getNetWallArea().getUom());
        } else {
            ps.setNull(22, Types.DOUBLE);
            ps.setNull(23, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getGrossVolume() != null && ifcSpaceBaseQuantity.getGrossVolume().isSetValue()) {
            ps.setDouble(24, ifcSpaceBaseQuantity.getGrossVolume().getValue());
            ps.setString(25, ifcSpaceBaseQuantity.getGrossVolume().getUom());
        } else {
            ps.setNull(24, Types.DOUBLE);
            ps.setNull(25, Types.VARCHAR);
        }

        if (ifcSpaceBaseQuantity.getNetVolume() != null && ifcSpaceBaseQuantity.getNetVolume().isSetValue()) {
            ps.setDouble(26, ifcSpaceBaseQuantity.getNetVolume().getValue());
            ps.setString(27, ifcSpaceBaseQuantity.getNetVolume().getUom());
        } else {
            ps.setNull(26, Types.DOUBLE);
            ps.setNull(27, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCSPACEBASEQUANTITY));
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
