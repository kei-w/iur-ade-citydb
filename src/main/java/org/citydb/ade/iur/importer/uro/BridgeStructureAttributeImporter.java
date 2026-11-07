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
import org.citygml4j.ade.iur.model.uro.BridgeStructureAttribute;

import java.sql.*;

public class BridgeStructureAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public BridgeStructureAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BRIDGESTRUCTUREATTRIBU)) + " " +
                "(id, material, material_codespace, bridgetype, bridgetype_codespace, length, length_uom, width," +
                "width_uom, area, area_uom, weightrestriction, weightrestriction_uom, heightrestriction," +
                "heightrestriction_uom, widthrestriction, widthrestriction_uom, undergirderheight," +
                "undergirderheight_uom, slopetype, slopetype_codespace, escalator) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(BridgeStructureAttribute bridgeStructureAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.BRIDGESTRUCTUREATT_SEQ));
        ps.setLong(1, objectId);

        if (bridgeStructureAttribute.getMaterial() != null && bridgeStructureAttribute.getMaterial().isSetValue()) {
            ps.setString(2, bridgeStructureAttribute.getMaterial().getValue());
            ps.setString(3, bridgeStructureAttribute.getMaterial().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getBridgeType() != null && bridgeStructureAttribute.getBridgeType().isSetValue()) {
            ps.setString(4, bridgeStructureAttribute.getBridgeType().getValue());
            ps.setString(5, bridgeStructureAttribute.getBridgeType().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getLength() != null && bridgeStructureAttribute.getLength().isSetValue()) {
            ps.setDouble(6, bridgeStructureAttribute.getLength().getValue());
            ps.setString(7, bridgeStructureAttribute.getLength().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getWidth() != null && bridgeStructureAttribute.getWidth().isSetValue()) {
            ps.setDouble(8, bridgeStructureAttribute.getWidth().getValue());
            ps.setString(9, bridgeStructureAttribute.getWidth().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getArea() != null && bridgeStructureAttribute.getArea().isSetValue()) {
            ps.setDouble(10, bridgeStructureAttribute.getArea().getValue());
            ps.setString(11, bridgeStructureAttribute.getArea().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getWeightRestriction() != null && bridgeStructureAttribute.getWeightRestriction().isSetValue()) {
            ps.setDouble(12, bridgeStructureAttribute.getWeightRestriction().getValue());
            ps.setString(13, bridgeStructureAttribute.getWeightRestriction().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getHeightRestriction() != null && bridgeStructureAttribute.getHeightRestriction().isSetValue()) {
            ps.setDouble(14, bridgeStructureAttribute.getHeightRestriction().getValue());
            ps.setString(15, bridgeStructureAttribute.getHeightRestriction().getUom());
        } else {
            ps.setNull(14, Types.DOUBLE);
            ps.setNull(15, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getWidthRestriction() != null && bridgeStructureAttribute.getWidthRestriction().isSetValue()) {
            ps.setDouble(16, bridgeStructureAttribute.getWidthRestriction().getValue());
            ps.setString(17, bridgeStructureAttribute.getWidthRestriction().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getUnderGirderHeight() != null && bridgeStructureAttribute.getUnderGirderHeight().isSetValue()) {
            ps.setDouble(18, bridgeStructureAttribute.getUnderGirderHeight().getValue());
            ps.setString(19, bridgeStructureAttribute.getUnderGirderHeight().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getSlopeType() != null && bridgeStructureAttribute.getSlopeType().isSetValue()) {
            ps.setString(20, bridgeStructureAttribute.getSlopeType().getValue());
            ps.setString(21, bridgeStructureAttribute.getSlopeType().getCodeSpace());
        } else {
            ps.setNull(20, Types.VARCHAR);
            ps.setNull(21, Types.VARCHAR);
        }

        if (bridgeStructureAttribute.getEscalator() != null)
            ps.setInt(22, bridgeStructureAttribute.getEscalator() ? 1 : 0);
        else
            ps.setNull(22, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BRIDGESTRUCTUREATTRIBU));

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
