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
import org.citygml4j.ade.iur.model.uro.WaterBodyDetailAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class WaterBodyDetailAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public WaterBodyDetailAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.WATERBODYDETAILATTRIBU)) + " " +
                "(id, kana, watersystemcode, watersystemcode_codespace, rivercode, rivercode_codespace, admintype," +
                "admintype_codespace, flowdirection, maximumdepth, maximumdepth_uom, watersurfaceelevation," +
                "watersurfaceelevation_uom, area, area_uom, measurementyearmonth, prefecture, prefecture_codespace," +
                "city, city_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(WaterBodyDetailAttribute waterBodyDetailAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.WATERBODYDETAILATT_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, waterBodyDetailAttribute.getKana());

        if (waterBodyDetailAttribute.getWaterSystemCode() != null && waterBodyDetailAttribute.getWaterSystemCode().isSetValue()) {
            ps.setString(3, waterBodyDetailAttribute.getWaterSystemCode().getValue());
            ps.setString(4, waterBodyDetailAttribute.getWaterSystemCode().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (waterBodyDetailAttribute.getRiverCode() != null && waterBodyDetailAttribute.getRiverCode().isSetValue()) {
            ps.setString(5, waterBodyDetailAttribute.getRiverCode().getValue());
            ps.setString(6, waterBodyDetailAttribute.getRiverCode().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (waterBodyDetailAttribute.getAdminType() != null && waterBodyDetailAttribute.getAdminType().isSetValue()) {
            ps.setString(7, waterBodyDetailAttribute.getAdminType().getValue());
            ps.setString(8, waterBodyDetailAttribute.getAdminType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (waterBodyDetailAttribute.getFlowDirection() != null)
            ps.setInt(9, waterBodyDetailAttribute.getFlowDirection() ? 1 : 0);
        else
            ps.setNull(9, Types.INTEGER);

        if (waterBodyDetailAttribute.getMaximumDepth() != null && waterBodyDetailAttribute.getMaximumDepth().isSetValue()) {
            ps.setDouble(10, waterBodyDetailAttribute.getMaximumDepth().getValue());
            ps.setString(11, waterBodyDetailAttribute.getMaximumDepth().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (waterBodyDetailAttribute.getWaterSurfaceElevation() != null && waterBodyDetailAttribute.getWaterSurfaceElevation().isSetValue()) {
            ps.setDouble(12, waterBodyDetailAttribute.getWaterSurfaceElevation().getValue());
            ps.setString(13, waterBodyDetailAttribute.getWaterSurfaceElevation().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (waterBodyDetailAttribute.getArea() != null && waterBodyDetailAttribute.getArea().isSetValue()) {
            ps.setDouble(14, waterBodyDetailAttribute.getArea().getValue());
            ps.setString(15, waterBodyDetailAttribute.getArea().getUom());
        } else {
            ps.setNull(14, Types.DOUBLE);
            ps.setNull(15, Types.VARCHAR);
        }

        if (waterBodyDetailAttribute.getMeasurementYearMonth() != null)
            ps.setDate(16, Date.valueOf(waterBodyDetailAttribute.getMeasurementYearMonth().atDay(1)));
        else
            ps.setNull(16, Types.DATE);

        if (!waterBodyDetailAttribute.getPrefecture().isEmpty()) {
            valueJoiner.join(waterBodyDetailAttribute.getPrefecture(), Code::getValue, Code::getCodeSpace);
            ps.setString(17, valueJoiner.result(0));
            ps.setString(18, valueJoiner.result(1));
        } else {
            ps.setNull(17, Types.VARCHAR);
            ps.setNull(18, Types.VARCHAR);
        }

        if (!waterBodyDetailAttribute.getCity().isEmpty()) {
            valueJoiner.join(waterBodyDetailAttribute.getCity(), Code::getValue, Code::getCodeSpace);
            ps.setString(19, valueJoiner.result(0));
            ps.setString(20, valueJoiner.result(1));
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.WATERBODYDETAILATTRIBU));

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
