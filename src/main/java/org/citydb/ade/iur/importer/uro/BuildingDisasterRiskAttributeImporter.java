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
import org.citygml4j.ade.iur.model.uro.BuildingDisasterRiskAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingFloodingRiskAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingLandSlideRiskAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BuildingDisasterRiskAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final BuildingAttributeImporter buildingAttributeImporter;
    private final BuildingFloodingRiskAttributeImporter buildingFloodingRiskAttributeImporter;

    private int batchCounter;

    public BuildingDisasterRiskAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGDISASTERRISKAT)) + " " +
                "(id, objectclass_id, building_buildingdisaster_id, description, description_codespace, areatype, areatype_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?)");

        buildingAttributeImporter = manager.getImporter(BuildingAttributeImporter.class);
        buildingFloodingRiskAttributeImporter = manager.getImporter(BuildingFloodingRiskAttributeImporter.class);
    }

    public long doImport(BuildingDisasterRiskAttribute buildingDisasterRiskAttribute, long parentId) throws CityGMLImportException, SQLException {
        long objectId = buildingAttributeImporter.doImport(buildingDisasterRiskAttribute);
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(buildingDisasterRiskAttribute.getClass()));
        ps.setLong(3, parentId);

        if (buildingDisasterRiskAttribute.getDescription() != null && buildingDisasterRiskAttribute.getDescription().isSetValue()) {
            ps.setString(4, buildingDisasterRiskAttribute.getDescription().getValue());
            ps.setString(5, buildingDisasterRiskAttribute.getDescription().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (buildingDisasterRiskAttribute instanceof BuildingLandSlideRiskAttribute
                && ((BuildingLandSlideRiskAttribute) buildingDisasterRiskAttribute).getAreaType() != null
                && ((BuildingLandSlideRiskAttribute) buildingDisasterRiskAttribute).getAreaType().isSetValue()) {
            ps.setString(6, ((BuildingLandSlideRiskAttribute) buildingDisasterRiskAttribute).getAreaType().getValue());
            ps.setString(7, ((BuildingLandSlideRiskAttribute) buildingDisasterRiskAttribute).getAreaType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDINGDISASTERRISKAT));

        if (buildingDisasterRiskAttribute instanceof BuildingFloodingRiskAttribute)
            buildingFloodingRiskAttributeImporter.doImport((BuildingFloodingRiskAttribute) buildingDisasterRiskAttribute, objectId);

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
