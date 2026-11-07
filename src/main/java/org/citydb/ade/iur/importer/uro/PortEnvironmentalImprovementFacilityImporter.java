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
import org.citygml4j.ade.iur.model.uro.PortEnvironmentalImprovementFacility;

import java.sql.*;

public class PortEnvironmentalImprovementFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public PortEnvironmentalImprovementFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PORTENVIRONMENTALIMPRO)) + " " +
                "(id, usage, length, length_uom, area, area_uom, totalfoorarea, totalfoorarea_uom, totalcost," +
                "subsidy, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(PortEnvironmentalImprovementFacility portEnvironmentalImprovementFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        ps.setString(2, portEnvironmentalImprovementFacility.getUsage());

        if (portEnvironmentalImprovementFacility.getLength() != null && portEnvironmentalImprovementFacility.getLength().isSetValue()) {
            ps.setDouble(3, portEnvironmentalImprovementFacility.getLength().getValue());
            ps.setString(4, portEnvironmentalImprovementFacility.getLength().getUom());
        } else {
            ps.setNull(3, Types.DOUBLE);
            ps.setNull(4, Types.VARCHAR);
        }

        if (portEnvironmentalImprovementFacility.getArea() != null && portEnvironmentalImprovementFacility.getArea().isSetValue()) {
            ps.setDouble(5, portEnvironmentalImprovementFacility.getArea().getValue());
            ps.setString(6, portEnvironmentalImprovementFacility.getArea().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (portEnvironmentalImprovementFacility.getTotalFoorArea() != null && portEnvironmentalImprovementFacility.getTotalFoorArea().isSetValue()) {
            ps.setDouble(7, portEnvironmentalImprovementFacility.getTotalFoorArea().getValue());
            ps.setString(8, portEnvironmentalImprovementFacility.getTotalFoorArea().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (portEnvironmentalImprovementFacility.getTotalCost() != null)
            ps.setInt(9, portEnvironmentalImprovementFacility.getTotalCost());
        else
            ps.setNull(9, Types.INTEGER);

        if (portEnvironmentalImprovementFacility.getSubsidy() != null)
            ps.setInt(10, portEnvironmentalImprovementFacility.getSubsidy());
        else
            ps.setNull(10, Types.INTEGER);

        ps.setString(11, portEnvironmentalImprovementFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PORTENVIRONMENTALIMPRO));
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
