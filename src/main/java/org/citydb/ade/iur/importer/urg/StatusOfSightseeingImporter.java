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
import org.citygml4j.ade.iur.model.urg.StatusOfSightseeing;

import java.sql.*;

public class StatusOfSightseeingImporter implements StatisticalGridModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final StatisticalGridImporter statisticalGridImporter;

    private int batchCounter;

    public StatusOfSightseeingImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.STATUSOFSIGHTSEEING)) + " " +
                "(id, numofhotels, numofryokan, numofpublicinn, numofinn, numofcotage, numoftemplehotel," +
                "numofrentavilla, numofyouthhostel, numofcapsulehotel, numoftrainingcenter, totalaccomodations," +
                "totalcapacity, totalrooms) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statisticalGridImporter = manager.getImporter(StatisticalGridImporter.class);
    }

    public void doImport(StatusOfSightseeing statusOfSightseeing, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        statisticalGridImporter.doImport(statusOfSightseeing, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (statusOfSightseeing.getNumOfHotels() != null)
            ps.setInt(2, statusOfSightseeing.getNumOfHotels());
        else
            ps.setNull(2, Types.INTEGER);

        if (statusOfSightseeing.getNumOfRyokan() != null)
            ps.setInt(3, statusOfSightseeing.getNumOfRyokan());
        else
            ps.setNull(3, Types.INTEGER);

        if (statusOfSightseeing.getNumOfPublicInn() != null)
            ps.setInt(4, statusOfSightseeing.getNumOfPublicInn());
        else
            ps.setNull(4, Types.INTEGER);

        if (statusOfSightseeing.getNumOfInn() != null)
            ps.setInt(5, statusOfSightseeing.getNumOfInn());
        else
            ps.setNull(5, Types.INTEGER);

        if (statusOfSightseeing.getNumOfCotage() != null)
            ps.setInt(6, statusOfSightseeing.getNumOfCotage());
        else
            ps.setNull(6, Types.INTEGER);

        if (statusOfSightseeing.getNumOfTempleHotel() != null)
            ps.setInt(7, statusOfSightseeing.getNumOfTempleHotel());
        else
            ps.setNull(7, Types.INTEGER);

        if (statusOfSightseeing.getNumOfRentaVilla() != null)
            ps.setInt(8, statusOfSightseeing.getNumOfRentaVilla());
        else
            ps.setNull(8, Types.INTEGER);

        if (statusOfSightseeing.getNumOfYouthHostel() != null)
            ps.setInt(9, statusOfSightseeing.getNumOfYouthHostel());
        else
            ps.setNull(9, Types.INTEGER);

        if (statusOfSightseeing.getNumOfCapsuleHotel() != null)
            ps.setInt(10, statusOfSightseeing.getNumOfCapsuleHotel());
        else
            ps.setNull(10, Types.INTEGER);

        if (statusOfSightseeing.getNumOfTrainingCenter() != null)
            ps.setInt(11, statusOfSightseeing.getNumOfTrainingCenter());
        else
            ps.setNull(11, Types.INTEGER);

        if (statusOfSightseeing.getTotalAccomodations() != null)
            ps.setInt(12, statusOfSightseeing.getTotalAccomodations());
        else
            ps.setNull(12, Types.INTEGER);

        if (statusOfSightseeing.getTotalCapacity() != null)
            ps.setInt(13, statusOfSightseeing.getTotalCapacity());
        else
            ps.setNull(13, Types.INTEGER);

        if (statusOfSightseeing.getTotalRooms() != null)
            ps.setInt(14, statusOfSightseeing.getTotalRooms());
        else
            ps.setNull(14, Types.INTEGER);

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
