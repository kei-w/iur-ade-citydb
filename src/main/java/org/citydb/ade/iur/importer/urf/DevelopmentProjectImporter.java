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
import org.citygml4j.ade.iur.model.urf.DevelopmentProject;

import java.sql.*;

public class DevelopmentProjectImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ZoneImporter zoneImporter;

    private int batchCounter;

    public DevelopmentProjectImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT)) + " " +
                "(id, status, status_codespace, mainpurpose, mainpurpose_codespace, benefitarea, benefitarea_uom," +
                "benefitperiod, cost, plannedarea, plannedarea_uom, ongoingarea, ongoingarea_uom, completedarea," +
                "completedarea_uom, dateofdecision, dateofdesignationfortemporar) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        zoneImporter = manager.getImporter(ZoneImporter.class);
    }

    public void doImport(DevelopmentProject developmentProject, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        zoneImporter.doImport(developmentProject, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (developmentProject.getStatus() != null && developmentProject.getStatus().isSetValue()) {
            ps.setString(2, developmentProject.getStatus().getValue());
            ps.setString(3, developmentProject.getStatus().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (developmentProject.getMainPurpose() != null && developmentProject.getMainPurpose().isSetValue()) {
            ps.setString(4, developmentProject.getMainPurpose().getValue());
            ps.setString(5, developmentProject.getMainPurpose().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (developmentProject.getBenefitArea() != null && developmentProject.getBenefitArea().isSetValue()) {
            ps.setDouble(6, developmentProject.getBenefitArea().getValue());
            ps.setString(7, developmentProject.getBenefitArea().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        ps.setString(8, developmentProject.getBenefitPeriod());

        if (developmentProject.getCost() != null)
            ps.setInt(9, developmentProject.getCost());
        else
            ps.setNull(9, Types.INTEGER);

        if (developmentProject.getPlannedArea() != null && developmentProject.getPlannedArea().isSetValue()) {
            ps.setDouble(10, developmentProject.getPlannedArea().getValue());
            ps.setString(11, developmentProject.getPlannedArea().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (developmentProject.getOngoingArea() != null && developmentProject.getOngoingArea().isSetValue()) {
            ps.setDouble(12, developmentProject.getOngoingArea().getValue());
            ps.setString(13, developmentProject.getOngoingArea().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (developmentProject.getCompletedArea() != null && developmentProject.getCompletedArea().isSetValue()) {
            ps.setDouble(14, developmentProject.getCompletedArea().getValue());
            ps.setString(15, developmentProject.getCompletedArea().getUom());
        } else {
            ps.setNull(14, Types.DOUBLE);
            ps.setNull(15, Types.VARCHAR);
        }

        if (developmentProject.getDateOfDecision() != null)
            ps.setDate(16, Date.valueOf(developmentProject.getDateOfDecision()));
        else
            ps.setNull(16, Types.DATE);

        if (developmentProject.getDateOfDesignationForTemporaryReplotting() != null)
            ps.setDate(17, Date.valueOf(developmentProject.getDateOfDesignationForTemporaryReplotting()));
        else
            ps.setNull(17, Types.DATE);

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
