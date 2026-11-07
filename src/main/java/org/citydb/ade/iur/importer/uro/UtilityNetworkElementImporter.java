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
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.OffsetDepthProperty;
import org.citygml4j.ade.iur.model.uro.ThematicShapeProperty;
import org.citygml4j.ade.iur.model.uro.UtilityNetworkElement;

import java.sql.*;
import java.time.LocalDate;

public class UtilityNetworkElementImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UtilityNetworkElementToOffsetDepthImporter utilityNetworkElementToOffsetDepthImporter;
    private final OffsetDepthImporter offsetDepthImporter;
    private final UtilityNetworkElementToThematicShapeImporter utilityNetworkElementToThematicShapeImporter;
    private final ThematicShapeImporter thematicShapeImporter;

    private int batchCounter;

    public UtilityNetworkElementImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.UTILITYNETWORKELEMENT)) + " " +
                "(id, objectclass_id, occupiertype, occupiertype_codespace, occupiername, occupiername_codespace," +
                "year, yeartype, yeartype_codespace, administrator, administrator_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        utilityNetworkElementToOffsetDepthImporter = manager.getImporter(UtilityNetworkElementToOffsetDepthImporter.class);
        offsetDepthImporter = manager.getImporter(OffsetDepthImporter.class);
        utilityNetworkElementToThematicShapeImporter = manager.getImporter(UtilityNetworkElementToThematicShapeImporter.class);
        thematicShapeImporter = manager.getImporter(ThematicShapeImporter.class);
    }

    public void doImport(UtilityNetworkElement utilityNetworkElement, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        if (utilityNetworkElement.getOccupierType() != null && utilityNetworkElement.getOccupierType().isSetValue()) {
            ps.setString(3, utilityNetworkElement.getOccupierType().getValue());
            ps.setString(4, utilityNetworkElement.getOccupierType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (utilityNetworkElement.getOccupierName() != null && utilityNetworkElement.getOccupierName().isSetValue()) {
            ps.setString(5, utilityNetworkElement.getOccupierName().getValue());
            ps.setString(6, utilityNetworkElement.getOccupierName().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (utilityNetworkElement.getYear() != null)
            ps.setDate(7, Date.valueOf(LocalDate.of(utilityNetworkElement.getYear().getValue(), 1, 1)));
        else
            ps.setNull(7, Types.DATE);

        if (utilityNetworkElement.getYearType() != null && utilityNetworkElement.getYearType().isSetValue()) {
            ps.setString(8, utilityNetworkElement.getYearType().getValue());
            ps.setString(9, utilityNetworkElement.getYearType().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (utilityNetworkElement.getAdministrator() != null && utilityNetworkElement.getAdministrator().isSetValue()) {
            ps.setString(10, utilityNetworkElement.getAdministrator().getValue());
            ps.setString(11, utilityNetworkElement.getAdministrator().getCodeSpace());
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (OffsetDepthProperty property : utilityNetworkElement.getOffsetDepth()) {
            if (property.isSetObject()) {
                long offsetDepthId = offsetDepthImporter.doImport(property.getObject());
                utilityNetworkElementToOffsetDepthImporter.doImport(offsetDepthId, objectId);
            }
        }
        for (ThematicShapeProperty property : utilityNetworkElement.getThematicShape()) {
            if (property.isSetObject()) {
                long thematicShapeId = thematicShapeImporter.doImport(property.getObject());
                utilityNetworkElementToThematicShapeImporter.doImport(thematicShapeId, objectId);
            }
        }
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
