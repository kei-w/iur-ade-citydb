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

package org.citydb.ade.iur.importer.urf;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.UrbanRenewalProject;

import java.sql.*;

public class UrbanRenewalProjectImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanDevelopmentProjectImporter urbanDevelopmentProjectImporter;

    private int batchCounter;

    public UrbanRenewalProjectImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URBANRENEWALPROJECT)) + " " +
                "(id, storeysaboveground, storeysbelowground, setbacksize, floorarearate, buildingusage, sitearea," +
                "sitearea_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        urbanDevelopmentProjectImporter = manager.getImporter(UrbanDevelopmentProjectImporter.class);
    }

    public void doImport(UrbanRenewalProject urbanRenewalProject, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanDevelopmentProjectImporter.doImport(urbanRenewalProject, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (urbanRenewalProject.getStoreysAboveGround() != null)
            ps.setInt(2, urbanRenewalProject.getStoreysAboveGround());
        else
            ps.setNull(2, Types.INTEGER);

        if (urbanRenewalProject.getStoreysBelowGround() != null)
            ps.setInt(3, urbanRenewalProject.getStoreysBelowGround());
        else
            ps.setNull(3, Types.INTEGER);

        if (urbanRenewalProject.getSetbackSize() != null && urbanRenewalProject.getSetbackSize().isSetValue())
            ps.setString(4, urbanRenewalProject.getSetbackSize().getValue());
        else
            ps.setNull(4, Types.VARCHAR);

        if (urbanRenewalProject.getFloorAreaRate() != null)
            ps.setDouble(5, urbanRenewalProject.getFloorAreaRate());
        else
            ps.setNull(5, Types.DOUBLE);

        ps.setString(6, urbanRenewalProject.getBuildingUsage());

        if (urbanRenewalProject.getSiteArea() != null && urbanRenewalProject.getSiteArea().isSetValue()) {
            ps.setDouble(7, urbanRenewalProject.getSiteArea().getValue());
            ps.setString(8, urbanRenewalProject.getSiteArea().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
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
