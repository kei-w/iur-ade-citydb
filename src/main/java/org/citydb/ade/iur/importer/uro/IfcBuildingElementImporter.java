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
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.IfcBuildingElement;
import org.citygml4j.ade.iur.model.uro.IfcDoor;
import org.citygml4j.ade.iur.model.uro.IfcWall;
import org.citygml4j.ade.iur.model.uro.IfcWindow;

import java.sql.*;

public class IfcBuildingElementImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final IfcWallImporter ifcWallImporter;
    private final IfcWindowImporter ifcWindowImporter;
    private final IfcDoorImporter ifcDoorImporter;

    private int batchCounter;

    public IfcBuildingElementImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCBUILDINGELEMENT)) + " " +
                "(id, objectclass_id, elementtype, elementtype_codespace, predefinedtype, predefinedtype_codespace," +
                "shapetype, shapetype_codespace, numberofriser, numberoftreads, riserheight, riserheight_uom," +
                "treadlength, treadlength_uom, operationtype, capacitybyweight, capacitybyweight_uom," +
                "capacitybynumber) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ifcWallImporter = manager.getImporter(IfcWallImporter.class);
        ifcWindowImporter = manager.getImporter(IfcWindowImporter.class);
        ifcDoorImporter = manager.getImporter(IfcDoorImporter.class);
    }

    public void doImport(IfcBuildingElement ifcBuildingElement, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(ifcBuildingElement.getClass()));

        if (ifcBuildingElement.getElementType() != null && ifcBuildingElement.getElementType().isSetValue()) {
            ps.setString(3, ifcBuildingElement.getElementType().getValue());
            ps.setString(4, ifcBuildingElement.getElementType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (ifcBuildingElement.getPredefinedType() != null && ifcBuildingElement.getPredefinedType().isSetValue()) {
            ps.setString(5, ifcBuildingElement.getPredefinedType().getValue());
            ps.setString(6, ifcBuildingElement.getPredefinedType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (ifcBuildingElement.getShapeType() != null && ifcBuildingElement.getShapeType().isSetValue()) {
            ps.setString(7, ifcBuildingElement.getShapeType().getValue());
            ps.setString(8, ifcBuildingElement.getShapeType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (ifcBuildingElement.getNumberOfRiser() != null)
            ps.setInt(9, ifcBuildingElement.getNumberOfRiser());
        else
            ps.setNull(9, Types.INTEGER);

        if (ifcBuildingElement.getNumberOfTreads() != null)
            ps.setInt(10, ifcBuildingElement.getNumberOfTreads());
        else
            ps.setNull(10, Types.INTEGER);

        if (ifcBuildingElement.getRiserHeight() != null && ifcBuildingElement.getRiserHeight().isSetValue()) {
            ps.setDouble(11, ifcBuildingElement.getRiserHeight().getValue());
            ps.setString(12, ifcBuildingElement.getRiserHeight().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (ifcBuildingElement.getTreadLength() != null && ifcBuildingElement.getTreadLength().isSetValue()) {
            ps.setDouble(13, ifcBuildingElement.getTreadLength().getValue());
            ps.setString(14, ifcBuildingElement.getTreadLength().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (ifcBuildingElement.getOperationType() != null)
            ps.setString(15, ifcBuildingElement.getOperationType().value());
        else
            ps.setNull(15, Types.VARCHAR);

        if (ifcBuildingElement.getCapacityByWeight() != null && ifcBuildingElement.getCapacityByWeight().isSetValue()) {
            ps.setDouble(16, ifcBuildingElement.getCapacityByWeight().getValue());
            ps.setString(17, ifcBuildingElement.getCapacityByWeight().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (ifcBuildingElement.getCapacityByNumber() != null)
            ps.setInt(18, ifcBuildingElement.getCapacityByNumber());
        else
            ps.setNull(18, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCBUILDINGELEMENT));

        if (ifcBuildingElement instanceof IfcWall)
            ifcWallImporter.doImport((IfcWall) ifcBuildingElement, objectId);

        if (ifcBuildingElement instanceof IfcWindow)
            ifcWindowImporter.doImport((IfcWindow) ifcBuildingElement, objectId);

        if (ifcBuildingElement instanceof IfcDoor)
            ifcDoorImporter.doImport((IfcDoor) ifcBuildingElement, objectId);
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
