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
import org.citygml4j.ade.iur.model.urf.PublicTransitFacility;

import java.sql.*;

public class PublicTransitFacilityImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionImporter urbanFunctionImporter;

    private int batchCounter;

    public PublicTransitFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PUBLICTRANSITFACILITY)) + " " +
                "(id, id_, companyname, companytype, companytype_codespace, facilitytype, facilitytype_codespace," +
                "sectionname, frequencyofservice, numberofcustomers) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFunctionImporter = manager.getImporter(UrbanFunctionImporter.class);
    }

    public void doImport(PublicTransitFacility publicTransitFacility, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        urbanFunctionImporter.doImport(publicTransitFacility, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        ps.setString(2, publicTransitFacility.getIdentifier());

        ps.setString(3, publicTransitFacility.getCompanyName());

        if (publicTransitFacility.getCompanyType() != null && publicTransitFacility.getCompanyType().isSetValue()) {
            ps.setString(4, publicTransitFacility.getCompanyType().getValue());
            ps.setString(5, publicTransitFacility.getCompanyType().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (publicTransitFacility.getFacilityType() != null && publicTransitFacility.getFacilityType().isSetValue()) {
            ps.setString(6, publicTransitFacility.getFacilityType().getValue());
            ps.setString(7, publicTransitFacility.getFacilityType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        ps.setString(8, publicTransitFacility.getSectionName());

        if (publicTransitFacility.getFrequencyOfService() != null)
            ps.setInt(9, publicTransitFacility.getFrequencyOfService());
        else
            ps.setNull(9, Types.INTEGER);

        if (publicTransitFacility.getNumberOfCustomers() != null)
            ps.setInt(10, publicTransitFacility.getNumberOfCustomers());
        else
            ps.setNull(10, Types.INTEGER);

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
