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
import org.citygml4j.ade.iur.model.urf.UrbanPlanningArea;

import java.sql.*;

public class UrbanPlanningAreaImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ZoneImporter zoneImporter;

    private int batchCounter;

    public UrbanPlanningAreaImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URBANPLANNINGAREA)) + " " +
                "(id, areaclassification, areaclassification_codespace, reasonforareaclassification," +
                "policyforareaclassification, purposeforurbanplan, policyforurbanplandecision, population, cityarea," +
                "cityarea_uom, citypopulation) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        zoneImporter = manager.getImporter(ZoneImporter.class);
    }

    public void doImport(UrbanPlanningArea urbanPlanningArea, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        zoneImporter.doImport(urbanPlanningArea, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (urbanPlanningArea.getAreaClassification() != null && urbanPlanningArea.getAreaClassification().isSetValue()) {
            ps.setString(2, urbanPlanningArea.getAreaClassification().getValue());
            ps.setString(3, urbanPlanningArea.getAreaClassification().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (urbanPlanningArea.getReasonForAreaClassification() != null && urbanPlanningArea.getReasonForAreaClassification().isSetValue())
            ps.setString(4, urbanPlanningArea.getReasonForAreaClassification().getValue());
        else
            ps.setNull(4, Types.VARCHAR);

        if (urbanPlanningArea.getPolicyForAreaClassification() != null && urbanPlanningArea.getPolicyForAreaClassification().isSetValue())
            ps.setString(5, urbanPlanningArea.getPolicyForAreaClassification().getValue());
        else
            ps.setNull(5, Types.VARCHAR);

        if (urbanPlanningArea.getPurposeForUrbanPlan() != null && urbanPlanningArea.getPurposeForUrbanPlan().isSetValue())
            ps.setString(6, urbanPlanningArea.getPurposeForUrbanPlan().getValue());
        else
            ps.setNull(6, Types.VARCHAR);

        if (urbanPlanningArea.getPolicyForUrbanPlanDecision() != null && urbanPlanningArea.getPolicyForUrbanPlanDecision().isSetValue())
            ps.setString(7, urbanPlanningArea.getPolicyForUrbanPlanDecision().getValue());
        else
            ps.setNull(7, Types.VARCHAR);

        if (urbanPlanningArea.getPopulation() != null)
            ps.setInt(8, urbanPlanningArea.getPopulation());
        else
            ps.setNull(8, Types.INTEGER);

        if (urbanPlanningArea.getCityArea() != null && urbanPlanningArea.getCityArea().isSetValue()) {
            ps.setDouble(9, urbanPlanningArea.getCityArea().getValue());
            ps.setString(10, urbanPlanningArea.getCityArea().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (urbanPlanningArea.getCityPopulation() != null)
            ps.setInt(11, urbanPlanningArea.getCityPopulation());
        else
            ps.setNull(11, Types.INTEGER);

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
