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
import org.citygml4j.ade.iur.model.urf.ResidentialEnvironmentImprovementDistrict;

import java.sql.*;

public class ResidentialEnvironmentImprovementDistrictImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final DistrictsAndZonesImporter districtsAndZonesImporter;

    private int batchCounter;

    public ResidentialEnvironmentImprovementDistrictImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.RESIDENTIALENVIRONMENT)) + " " +
                "(id, usetobeinduced, maximumfloorarearate, maximumbuildingcoveragerate, maximumbuildingheight," +
                "setbacksize, otherrestrictions) " +
                "values (?, ?, ?, ?, ?, ?, ?)");

        districtsAndZonesImporter = manager.getImporter(DistrictsAndZonesImporter.class);
    }

    public void doImport(ResidentialEnvironmentImprovementDistrict residentialEnvironmentImprovementDistrict, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        districtsAndZonesImporter.doImport(residentialEnvironmentImprovementDistrict, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (residentialEnvironmentImprovementDistrict.getUseToBeInduced() != null && residentialEnvironmentImprovementDistrict.getUseToBeInduced().isSetValue())
            ps.setString(2, residentialEnvironmentImprovementDistrict.getUseToBeInduced().getValue());
        else
            ps.setNull(2, Types.VARCHAR);

        if (residentialEnvironmentImprovementDistrict.getMaximumFloorAreaRate() != null)
            ps.setDouble(3, residentialEnvironmentImprovementDistrict.getMaximumFloorAreaRate());
        else
            ps.setNull(3, Types.DOUBLE);

        if (residentialEnvironmentImprovementDistrict.getMaximumBuildingCoverageRate() != null)
            ps.setDouble(4, residentialEnvironmentImprovementDistrict.getMaximumBuildingCoverageRate());
        else
            ps.setNull(4, Types.DOUBLE);

        if (residentialEnvironmentImprovementDistrict.getMaximumBuildingHeight() != null && residentialEnvironmentImprovementDistrict.getMaximumBuildingHeight().isSetValue())
            ps.setString(5, residentialEnvironmentImprovementDistrict.getMaximumBuildingHeight().getValue());
        else
            ps.setNull(5, Types.VARCHAR);

        if (residentialEnvironmentImprovementDistrict.getSetbackSize() != null && residentialEnvironmentImprovementDistrict.getSetbackSize().isSetValue())
            ps.setString(6, residentialEnvironmentImprovementDistrict.getSetbackSize().getValue());
        else
            ps.setNull(6, Types.VARCHAR);

        if (residentialEnvironmentImprovementDistrict.getOtherRestrictions() != null && residentialEnvironmentImprovementDistrict.getOtherRestrictions().isSetValue())
            ps.setString(7, residentialEnvironmentImprovementDistrict.getOtherRestrictions().getValue());
        else
            ps.setNull(7, Types.VARCHAR);

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
