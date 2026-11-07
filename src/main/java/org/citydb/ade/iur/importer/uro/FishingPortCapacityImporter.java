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
import org.citygml4j.ade.iur.model.uro.FishingPortCapacity;

import java.sql.*;

public class FishingPortCapacityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public FishingPortCapacityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FISHINGPORTCAPACITY)) + " " +
                "(id, capacity, weightcapacity, weightcapacity_uom, hullform, shipnumber, waterdepth_2m," +
                "waterdepth_2m_uom, waterdepth2_3m, waterdepth2_3m_uom, waterdepth3_6m, waterdepth3_6m_uom," +
                "waterdepth6_m, waterdepth6_m_uom, heightaboveawl, heightaboveawl_uom, heightonfoundations," +
                "heightonfoundations_uom, luminousrange, luminousrange_uom, luminouscolor, candlepower, lighttype," +
                "period, maximumgroundingweight, handleablepower, maximumwatersupply, maximumrefueling, people," +
                "other) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(FishingPortCapacity fishingPortCapacity, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, fishingPortCapacity.getCapacity());

        if (fishingPortCapacity.getWeightCapacity() != null && fishingPortCapacity.getWeightCapacity().isSetValue()) {
            ps.setDouble(3, fishingPortCapacity.getWeightCapacity().getValue());
            ps.setString(4, fishingPortCapacity.getWeightCapacity().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (fishingPortCapacity.getHullForm() != null)
            ps.setInt(5, fishingPortCapacity.getHullForm());
        else
            ps.setNull(5, Types.INTEGER);

        if (fishingPortCapacity.getShipNumber() != null)
            ps.setInt(6, fishingPortCapacity.getShipNumber());
        else
            ps.setNull(6, Types.INTEGER);

        if (fishingPortCapacity.getWaterDepth2m() != null && fishingPortCapacity.getWaterDepth2m().isSetValue()) {
            ps.setDouble(7, fishingPortCapacity.getWaterDepth2m().getValue());
            ps.setString(8, fishingPortCapacity.getWaterDepth2m().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (fishingPortCapacity.getWaterDepth23m() != null && fishingPortCapacity.getWaterDepth23m().isSetValue()) {
            ps.setDouble(9, fishingPortCapacity.getWaterDepth23m().getValue());
            ps.setString(10, fishingPortCapacity.getWaterDepth23m().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (fishingPortCapacity.getWaterDepth36m() != null && fishingPortCapacity.getWaterDepth36m().isSetValue()) {
            ps.setDouble(11, fishingPortCapacity.getWaterDepth36m().getValue());
            ps.setString(12, fishingPortCapacity.getWaterDepth36m().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (fishingPortCapacity.getWaterDepth6m() != null && fishingPortCapacity.getWaterDepth6m().isSetValue()) {
            ps.setDouble(13, fishingPortCapacity.getWaterDepth6m().getValue());
            ps.setString(14, fishingPortCapacity.getWaterDepth6m().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (fishingPortCapacity.getHeightAboveAWL() != null && fishingPortCapacity.getHeightAboveAWL().isSetValue()) {
            ps.setDouble(15, fishingPortCapacity.getHeightAboveAWL().getValue());
            ps.setString(16, fishingPortCapacity.getHeightAboveAWL().getUom());
        } else {
            ps.setNull(15, Types.DOUBLE);
            ps.setNull(16, Types.VARCHAR);
        }

        if (fishingPortCapacity.getHeightOnFoundations() != null && fishingPortCapacity.getHeightOnFoundations().isSetValue()) {
            ps.setDouble(17, fishingPortCapacity.getHeightOnFoundations().getValue());
            ps.setString(18, fishingPortCapacity.getHeightOnFoundations().getUom());
        } else {
            ps.setNull(17, Types.DOUBLE);
            ps.setNull(18, Types.VARCHAR);
        }

        if (fishingPortCapacity.getLuminousRange() != null && fishingPortCapacity.getLuminousRange().isSetValue()) {
            ps.setDouble(19, fishingPortCapacity.getLuminousRange().getValue());
            ps.setString(20, fishingPortCapacity.getLuminousRange().getUom());
        } else {
            ps.setNull(19, Types.DOUBLE);
            ps.setNull(20, Types.VARCHAR);
        }

        ps.setString(21, fishingPortCapacity.getLuminousColor());

        if (fishingPortCapacity.getCandlePower() != null)
            ps.setInt(22, fishingPortCapacity.getCandlePower());
        else
            ps.setNull(22, Types.INTEGER);

        ps.setString(23, fishingPortCapacity.getLightType());

        ps.setString(24, fishingPortCapacity.getPeriod());

        if (fishingPortCapacity.getMaximumGroundingWeight() != null)
            ps.setInt(25, fishingPortCapacity.getMaximumGroundingWeight());
        else
            ps.setNull(25, Types.INTEGER);

        if (fishingPortCapacity.getHandleablePower() != null)
            ps.setInt(26, fishingPortCapacity.getHandleablePower());
        else
            ps.setNull(26, Types.INTEGER);

        if (fishingPortCapacity.getMaximumWaterSupply() != null)
            ps.setInt(27, fishingPortCapacity.getMaximumWaterSupply());
        else
            ps.setNull(27, Types.INTEGER);

        ps.setString(28, fishingPortCapacity.getMaximumRefueling());

        if (fishingPortCapacity.getPeople() != null)
            ps.setInt(29, fishingPortCapacity.getPeople());
        else
            ps.setNull(29, Types.INTEGER);

        ps.setString(30, fishingPortCapacity.getOther());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FISHINGPORTCAPACITY));
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
