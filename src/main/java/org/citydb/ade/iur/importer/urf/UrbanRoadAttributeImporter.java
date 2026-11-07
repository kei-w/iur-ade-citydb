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
import org.citygml4j.ade.iur.model.urf.StructureDetailsProperty;
import org.citygml4j.ade.iur.model.urf.UrbanRoadAttribute;

import java.sql.*;

public class UrbanRoadAttributeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanRoadAttributeToStructureDetailsImporter urbanRoadAttributeToStructureDetailsImporter;
    private final StructureDetailsImporter structureDetailsImporter;

    private int batchCounter;

    public UrbanRoadAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URBANROADATTRIBUTE)) + " " +
                "(id, routetypenumber, routetypenumber_codespace, routesizenumber, routesizenumber_codespace," +
                "routeserialnumber, roadtype, roadtype_codespace, numberoflanes, roadstructure, structuretype," +
                "structuretype_codespace, crosstype, crosstype_codespace, trafficplazas, trafficplazas_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanRoadAttributeToStructureDetailsImporter = manager.getImporter(UrbanRoadAttributeToStructureDetailsImporter.class);
        structureDetailsImporter = manager.getImporter(StructureDetailsImporter.class);
    }

    public long doImport(UrbanRoadAttribute urbanRoadAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.URBANROADATTRIBUTE_SEQ));
        ps.setLong(1, objectId);

        if (urbanRoadAttribute.getRouteTypeNumber() != null && urbanRoadAttribute.getRouteTypeNumber().isSetValue()) {
            ps.setString(2, urbanRoadAttribute.getRouteTypeNumber().getValue());
            ps.setString(3, urbanRoadAttribute.getRouteTypeNumber().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (urbanRoadAttribute.getRouteSizeNumber() != null && urbanRoadAttribute.getRouteSizeNumber().isSetValue()) {
            ps.setString(4, urbanRoadAttribute.getRouteSizeNumber().getValue());
            ps.setString(5, urbanRoadAttribute.getRouteSizeNumber().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (urbanRoadAttribute.getRouteSerialNumber() != null)
            ps.setInt(6, urbanRoadAttribute.getRouteSerialNumber());
        else
            ps.setNull(6, Types.INTEGER);

        if (urbanRoadAttribute.getRoadType() != null && urbanRoadAttribute.getRoadType().isSetValue()) {
            ps.setString(7, urbanRoadAttribute.getRoadType().getValue());
            ps.setString(8, urbanRoadAttribute.getRoadType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (urbanRoadAttribute.getNumberOfLanes() != null)
            ps.setInt(9, urbanRoadAttribute.getNumberOfLanes());
        else
            ps.setNull(9, Types.INTEGER);

        if (urbanRoadAttribute.getRoadStructure() != null && urbanRoadAttribute.getRoadStructure().isSetValue())
            ps.setString(10, urbanRoadAttribute.getRoadStructure().getValue());
        else
            ps.setNull(10, Types.VARCHAR);

        if (urbanRoadAttribute.getStructureType() != null && urbanRoadAttribute.getStructureType().isSetValue()) {
            ps.setString(11, urbanRoadAttribute.getStructureType().getValue());
            ps.setString(12, urbanRoadAttribute.getStructureType().getCodeSpace());
        } else {
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.VARCHAR);
        }

        if (urbanRoadAttribute.getCrossType() != null && urbanRoadAttribute.getCrossType().isSetValue()) {
            ps.setString(13, urbanRoadAttribute.getCrossType().getValue());
            ps.setString(14, urbanRoadAttribute.getCrossType().getCodeSpace());
        } else {
            ps.setNull(13, Types.VARCHAR);
            ps.setNull(14, Types.VARCHAR);
        }

        if (urbanRoadAttribute.getTrafficPlazas() != null && urbanRoadAttribute.getTrafficPlazas().isSetValue()) {
            ps.setString(15, urbanRoadAttribute.getTrafficPlazas().getValue());
            ps.setString(16, urbanRoadAttribute.getTrafficPlazas().getCodeSpace());
        } else {
            ps.setNull(15, Types.VARCHAR);
            ps.setNull(16, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.URBANROADATTRIBUTE));

        for (StructureDetailsProperty property : urbanRoadAttribute.getStructuralDetails()) {
            if (property.isSetObject()) {
                long structuralDetailsId = structureDetailsImporter.doImport(property.getObject());
                urbanRoadAttributeToStructureDetailsImporter.doImport(structuralDetailsId, objectId);
            }
        }

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
