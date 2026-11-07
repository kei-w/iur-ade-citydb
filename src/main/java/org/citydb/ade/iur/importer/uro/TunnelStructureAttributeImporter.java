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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.TunnelStructureAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class TunnelStructureAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public TunnelStructureAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TUNNELSTRUCTUREATTRIBU)) + " " +
                "(id, tunneltype, tunneltype_codespace, tunnelsubtype, tunnelsubtype_codespace, mouthtype," +
                "mouthtype_codespace, length, length_uom, width, width_uom, area, area_uom, innerheight," +
                "innerheight_uom, effectiveheight, effectiveheight_uom, slopetype, slopetype_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(TunnelStructureAttribute tunnelStructureAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.TUNNELSTRUCTUREATT_SEQ));
        ps.setLong(1, objectId);

        if (tunnelStructureAttribute.getTunnelType() != null && tunnelStructureAttribute.getTunnelType().isSetValue()) {
            ps.setString(2, tunnelStructureAttribute.getTunnelType().getValue());
            ps.setString(3, tunnelStructureAttribute.getTunnelType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (tunnelStructureAttribute.getTunnelSubtype() != null && tunnelStructureAttribute.getTunnelSubtype().isSetValue()) {
            ps.setString(4, tunnelStructureAttribute.getTunnelSubtype().getValue());
            ps.setString(5, tunnelStructureAttribute.getTunnelSubtype().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (!tunnelStructureAttribute.getMouthType().isEmpty()) {
            valueJoiner.join(tunnelStructureAttribute.getMouthType(), Code::getValue, Code::getCodeSpace);
            ps.setString(6, valueJoiner.result(0));
            ps.setString(7, valueJoiner.result(1));
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (tunnelStructureAttribute.getLength() != null && tunnelStructureAttribute.getLength().isSetValue()) {
            ps.setDouble(8, tunnelStructureAttribute.getLength().getValue());
            ps.setString(9, tunnelStructureAttribute.getLength().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (tunnelStructureAttribute.getWidth() != null && tunnelStructureAttribute.getWidth().isSetValue()) {
            ps.setDouble(10, tunnelStructureAttribute.getWidth().getValue());
            ps.setString(11, tunnelStructureAttribute.getWidth().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (tunnelStructureAttribute.getArea() != null && tunnelStructureAttribute.getArea().isSetValue()) {
            ps.setDouble(12, tunnelStructureAttribute.getArea().getValue());
            ps.setString(13, tunnelStructureAttribute.getArea().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (tunnelStructureAttribute.getInnerHeight() != null && tunnelStructureAttribute.getInnerHeight().isSetValue()) {
            ps.setDouble(14, tunnelStructureAttribute.getInnerHeight().getValue());
            ps.setString(15, tunnelStructureAttribute.getInnerHeight().getUom());
        } else {
            ps.setNull(14, Types.DOUBLE);
            ps.setNull(15, Types.VARCHAR);
        }

        if (tunnelStructureAttribute.getEffectiveHeight() != null && tunnelStructureAttribute.getEffectiveHeight().isSetValue()) {
            ps.setDouble(16, tunnelStructureAttribute.getEffectiveHeight().getValue());
            ps.setString(17, tunnelStructureAttribute.getEffectiveHeight().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (tunnelStructureAttribute.getSlopeType() != null && tunnelStructureAttribute.getSlopeType().isSetValue()) {
            ps.setString(18, tunnelStructureAttribute.getSlopeType().getValue());
            ps.setString(19, tunnelStructureAttribute.getSlopeType().getCodeSpace());
        } else {
            ps.setNull(18, Types.VARCHAR);
            ps.setNull(19, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TUNNELSTRUCTUREATTRIBU));

        return objectId;
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
