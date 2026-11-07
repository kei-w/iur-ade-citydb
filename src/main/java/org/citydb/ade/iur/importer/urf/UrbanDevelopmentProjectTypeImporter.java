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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.UrbanDevelopmentProjectType;

import java.sql.*;

public class UrbanDevelopmentProjectTypeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public UrbanDevelopmentProjectTypeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URBANDEVELOPMENTPROJEC)) + " " +
                "(id, id_, dateofdecision, projectname, plannedarea, plannedarea_uom, ongoingarea, ongoingarea_uom," +
                "costuptolastfiscalyear, cost, completedarea, completedarea_uom, projectperiod, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(UrbanDevelopmentProjectType urbanDevelopmentProjectType) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.URBANDEVELOPMENTPR_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, urbanDevelopmentProjectType.getId());

        if (urbanDevelopmentProjectType.getDateOfDecision() != null)
            ps.setDate(3, Date.valueOf(urbanDevelopmentProjectType.getDateOfDecision()));
        else
            ps.setNull(3, Types.DATE);

        ps.setString(4, urbanDevelopmentProjectType.getProjectName());

        if (urbanDevelopmentProjectType.getPlannedArea() != null && urbanDevelopmentProjectType.getPlannedArea().isSetValue()) {
            ps.setDouble(5, urbanDevelopmentProjectType.getPlannedArea().getValue());
            ps.setString(6, urbanDevelopmentProjectType.getPlannedArea().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (urbanDevelopmentProjectType.getOngoingArea() != null && urbanDevelopmentProjectType.getOngoingArea().isSetValue()) {
            ps.setDouble(7, urbanDevelopmentProjectType.getOngoingArea().getValue());
            ps.setString(8, urbanDevelopmentProjectType.getOngoingArea().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (urbanDevelopmentProjectType.getCostUpToLastFiscalYear() != null)
            ps.setDouble(9, urbanDevelopmentProjectType.getCostUpToLastFiscalYear());
        else
            ps.setNull(9, Types.DOUBLE);

        if (urbanDevelopmentProjectType.getCost() != null)
            ps.setDouble(10, urbanDevelopmentProjectType.getCost());
        else
            ps.setNull(10, Types.DOUBLE);

        if (urbanDevelopmentProjectType.getCompletedArea() != null && urbanDevelopmentProjectType.getCompletedArea().isSetValue()) {
            ps.setDouble(11, urbanDevelopmentProjectType.getCompletedArea().getValue());
            ps.setString(12, urbanDevelopmentProjectType.getCompletedArea().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        ps.setString(13, urbanDevelopmentProjectType.getProjectPeriod());

        ps.setString(14, urbanDevelopmentProjectType.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.URBANDEVELOPMENTPROJEC));

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
