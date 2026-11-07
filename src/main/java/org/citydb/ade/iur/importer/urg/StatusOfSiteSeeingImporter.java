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
import org.citygml4j.ade.iur.model.urg.StatusOfSiteSeeing;

import java.sql.*;

public class StatusOfSiteSeeingImporter implements StatisticalGridModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final StatisticalGridImporter statisticalGridImporter;

    private int batchCounter;

    public StatusOfSiteSeeingImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.STATUSOFSITESEEING)) + " " +
                "(id, numofhotels, numofryokan, numofpublicinn, numofinn, numofcotage, numoftemplehotel," +
                "numofrentavilla, numofyouthhostel, numofcapsulehotel, numoftrainingcenter, totalaccomodations," +
                "totalcapacity, totalrooms) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statisticalGridImporter = manager.getImporter(StatisticalGridImporter.class);
    }

    public void doImport(StatusOfSiteSeeing statusOfSiteSeeing, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        statisticalGridImporter.doImport(statusOfSiteSeeing, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (statusOfSiteSeeing.getNumOfHotels() != null)
            ps.setInt(2, statusOfSiteSeeing.getNumOfHotels());
        else
            ps.setNull(2, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfRyokan() != null)
            ps.setInt(3, statusOfSiteSeeing.getNumOfRyokan());
        else
            ps.setNull(3, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfPublicInn() != null)
            ps.setInt(4, statusOfSiteSeeing.getNumOfPublicInn());
        else
            ps.setNull(4, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfInn() != null)
            ps.setInt(5, statusOfSiteSeeing.getNumOfInn());
        else
            ps.setNull(5, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfCotage() != null)
            ps.setInt(6, statusOfSiteSeeing.getNumOfCotage());
        else
            ps.setNull(6, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfTempleHotel() != null)
            ps.setInt(7, statusOfSiteSeeing.getNumOfTempleHotel());
        else
            ps.setNull(7, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfRentaVilla() != null)
            ps.setInt(8, statusOfSiteSeeing.getNumOfRentaVilla());
        else
            ps.setNull(8, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfYouthHostel() != null)
            ps.setInt(9, statusOfSiteSeeing.getNumOfYouthHostel());
        else
            ps.setNull(9, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfCapsuleHotel() != null)
            ps.setInt(10, statusOfSiteSeeing.getNumOfCapsuleHotel());
        else
            ps.setNull(10, Types.INTEGER);

        if (statusOfSiteSeeing.getNumOfTrainingCenter() != null)
            ps.setInt(11, statusOfSiteSeeing.getNumOfTrainingCenter());
        else
            ps.setNull(11, Types.INTEGER);

        if (statusOfSiteSeeing.getTotalAccomodations() != null)
            ps.setInt(12, statusOfSiteSeeing.getTotalAccomodations());
        else
            ps.setNull(12, Types.INTEGER);

        if (statusOfSiteSeeing.getTotalCapacity() != null)
            ps.setInt(13, statusOfSiteSeeing.getTotalCapacity());
        else
            ps.setNull(13, Types.INTEGER);

        if (statusOfSiteSeeing.getTotalRooms() != null)
            ps.setInt(14, statusOfSiteSeeing.getTotalRooms());
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
