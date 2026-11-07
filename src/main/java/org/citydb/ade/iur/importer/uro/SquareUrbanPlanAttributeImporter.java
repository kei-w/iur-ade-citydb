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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.SquareUrbanPlanAttribute;
import org.citygml4j.ade.iur.model.uro.StationSquareAttribute;
import org.citygml4j.ade.iur.model.uro.TerminalAttribute;

import java.sql.*;

public class SquareUrbanPlanAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final StationSquareAttributeImporter stationSquareAttributeImporter;
    private final TerminalAttributeImporter terminalAttributeImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public SquareUrbanPlanAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.SQUAREURBANPLANATTRIBU)) + " " +
                "(id, objectclass_id, prefecture, prefecture_codespace, city, city_codespace, urbanplanningareaname," +
                "enforcer, dateofdecision, dateofrevision, areaplanned, areaplanned_uom, areainservice," +
                "areainservice_uom, remarks, status, status_codespace, areaimproved, areaimproved_uom," +
                "areacompleted, areacompleted_uom, projectstartdate, projectenddate, iscompleted, isauthorized," +
                "purpose, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        stationSquareAttributeImporter = manager.getImporter(StationSquareAttributeImporter.class);
        terminalAttributeImporter = manager.getImporter(TerminalAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(SquareUrbanPlanAttribute squareUrbanPlanAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.SQUAREURBANPLANATT_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(squareUrbanPlanAttribute.getClass()));

        if (squareUrbanPlanAttribute.getPrefecture() != null && squareUrbanPlanAttribute.getPrefecture().isSetValue()) {
            ps.setString(3, squareUrbanPlanAttribute.getPrefecture().getValue());
            ps.setString(4, squareUrbanPlanAttribute.getPrefecture().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (squareUrbanPlanAttribute.getCity() != null && squareUrbanPlanAttribute.getCity().isSetValue()) {
            ps.setString(5, squareUrbanPlanAttribute.getCity().getValue());
            ps.setString(6, squareUrbanPlanAttribute.getCity().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        ps.setString(7, squareUrbanPlanAttribute.getUrbanPlanningAreaName());

        if (!squareUrbanPlanAttribute.getEnforcer().isEmpty())
            ps.setString(8, valueJoiner.join(squareUrbanPlanAttribute.getEnforcer()));
        else
            ps.setNull(8, Types.VARCHAR);

        if (squareUrbanPlanAttribute.getDateOfDecision() != null)
            ps.setDate(9, Date.valueOf(squareUrbanPlanAttribute.getDateOfDecision()));
        else
            ps.setNull(9, Types.DATE);

        if (!squareUrbanPlanAttribute.getDateOfRevision().isEmpty())
            ps.setString(10, valueJoiner.join(squareUrbanPlanAttribute.getDateOfRevision()));
        else
            ps.setNull(10, Types.VARCHAR);

        if (squareUrbanPlanAttribute.getAreaPlanned() != null && squareUrbanPlanAttribute.getAreaPlanned().isSetValue()) {
            ps.setDouble(11, squareUrbanPlanAttribute.getAreaPlanned().getValue());
            ps.setString(12, squareUrbanPlanAttribute.getAreaPlanned().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (squareUrbanPlanAttribute.getAreaInService() != null && squareUrbanPlanAttribute.getAreaInService().isSetValue()) {
            ps.setDouble(13, squareUrbanPlanAttribute.getAreaInService().getValue());
            ps.setString(14, squareUrbanPlanAttribute.getAreaInService().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        ps.setString(15, squareUrbanPlanAttribute.getRemarks());

        if (squareUrbanPlanAttribute.getStatus() != null && squareUrbanPlanAttribute.getStatus().isSetValue()) {
            ps.setString(16, squareUrbanPlanAttribute.getStatus().getValue());
            ps.setString(17, squareUrbanPlanAttribute.getStatus().getCodeSpace());
        } else {
            ps.setNull(16, Types.VARCHAR);
            ps.setNull(17, Types.VARCHAR);
        }

        if (squareUrbanPlanAttribute.getAreaImproved() != null && squareUrbanPlanAttribute.getAreaImproved().isSetValue()) {
            ps.setDouble(18, squareUrbanPlanAttribute.getAreaImproved().getValue());
            ps.setString(19, squareUrbanPlanAttribute.getAreaImproved().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (squareUrbanPlanAttribute.getAreaCompleted() != null && squareUrbanPlanAttribute.getAreaCompleted().isSetValue()) {
            ps.setDouble(20, squareUrbanPlanAttribute.getAreaCompleted().getValue());
            ps.setString(21, squareUrbanPlanAttribute.getAreaCompleted().getUom());
        } else {
            ps.setNull(20, Types.DOUBLE);
            ps.setNull(21, Types.VARCHAR);
        }

        if (squareUrbanPlanAttribute.getProjectStartDate() != null)
            ps.setDate(22, Date.valueOf(squareUrbanPlanAttribute.getProjectStartDate()));
        else
            ps.setNull(22, Types.DATE);

        if (squareUrbanPlanAttribute.getProjectEndDate() != null)
            ps.setDate(23, Date.valueOf(squareUrbanPlanAttribute.getProjectEndDate()));
        else
            ps.setNull(23, Types.DATE);

        if (squareUrbanPlanAttribute.getIsCompleted() != null)
            ps.setInt(24, squareUrbanPlanAttribute.getIsCompleted() ? 1 : 0);
        else
            ps.setNull(24, Types.INTEGER);

        if (squareUrbanPlanAttribute.getIsAuthorized() != null)
            ps.setInt(25, squareUrbanPlanAttribute.getIsAuthorized() ? 1 : 0);
        else
            ps.setNull(25, Types.INTEGER);

        ps.setString(26, squareUrbanPlanAttribute.getPurpose());

        ps.setString(27, squareUrbanPlanAttribute.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.SQUAREURBANPLANATTRIBU));

        if (squareUrbanPlanAttribute instanceof StationSquareAttribute)
            stationSquareAttributeImporter.doImport((StationSquareAttribute) squareUrbanPlanAttribute, objectId);

        if (squareUrbanPlanAttribute instanceof TerminalAttribute)
            terminalAttributeImporter.doImport((TerminalAttribute) squareUrbanPlanAttribute, objectId);

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
