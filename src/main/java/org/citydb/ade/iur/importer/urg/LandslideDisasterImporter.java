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

package org.citydb.ade.iur.importer.urg;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urg.LandslideDisaster;

import java.sql.*;

public class LandslideDisasterImporter implements StatisticalGridModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final StatisticalGridImporter statisticalGridImporter;

    private int batchCounter;

    public LandslideDisasterImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.LANDSLIDEDISASTER)) + " " +
                "(id, dateofoccurence, landslidetype, maximumrainfallperday, maximumrainfallperday_uom," +
                "maximumrainfallperhour, maximumrainfallperhour_uom, slope, sedimentdischarge, length, length_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statisticalGridImporter = manager.getImporter(StatisticalGridImporter.class);
    }

    public void doImport(LandslideDisaster landslideDisaster, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        statisticalGridImporter.doImport(landslideDisaster, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (landslideDisaster.getDateOfOccurence() != null)
            ps.setDate(2, Date.valueOf(landslideDisaster.getDateOfOccurence()));
        else
            ps.setNull(2, Types.DATE);

        if (landslideDisaster.getLandSlideType() != null)
            ps.setString(3, landslideDisaster.getLandSlideType().value());
        else
            ps.setNull(3, Types.VARCHAR);

        if (landslideDisaster.getMaximumRainfallPerDay() != null && landslideDisaster.getMaximumRainfallPerDay().isSetValue()) {
            ps.setDouble(4, landslideDisaster.getMaximumRainfallPerDay().getValue());
            ps.setString(5, landslideDisaster.getMaximumRainfallPerDay().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (landslideDisaster.getMaximumRainfallPerHour() != null && landslideDisaster.getMaximumRainfallPerHour().isSetValue()) {
            ps.setDouble(6, landslideDisaster.getMaximumRainfallPerHour().getValue());
            ps.setString(7, landslideDisaster.getMaximumRainfallPerHour().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (landslideDisaster.getSlope() != null)
            ps.setInt(8, landslideDisaster.getSlope());
        else
            ps.setNull(8, Types.INTEGER);

        if (landslideDisaster.getSedimentDischarge() != null)
            ps.setInt(9, landslideDisaster.getSedimentDischarge());
        else
            ps.setNull(9, Types.INTEGER);

        if (landslideDisaster.getLength() != null && landslideDisaster.getLength().isSetValue()) {
            ps.setDouble(10, landslideDisaster.getLength().getValue());
            ps.setString(11, landslideDisaster.getLength().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);
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
