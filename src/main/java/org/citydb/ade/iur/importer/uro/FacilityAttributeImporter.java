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
import org.citygml4j.ade.iur.model.uro.FacilityAttribute;
import org.citygml4j.ade.iur.model.uro.FishingPortAttribute;
import org.citygml4j.ade.iur.model.uro.MaintenanceHistoryAttribute;
import org.citygml4j.ade.iur.model.uro.ParkFacilityLongevityPlanAttribute;
import org.citygml4j.ade.iur.model.uro.PortAttribute;

import java.sql.*;

public class FacilityAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final FishingPortAttributeImporter fishingPortAttributeImporter;
    private final MaintenanceHistoryAttributeImporter maintenanceHistoryAttributeImporter;
    private final ParkFacilityLongevityPlanAttributeImporter parkFacilityLongevityPlanAttributeImporter;
    private final PortAttributeImporter portAttributeImporter;

    private int batchCounter;

    public FacilityAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FACILITYATTRIBUTE)) + " " +
                "(id, objectclass_id, facilityid) " +
                "values (?, ?, ?)");

        fishingPortAttributeImporter = manager.getImporter(FishingPortAttributeImporter.class);
        maintenanceHistoryAttributeImporter = manager.getImporter(MaintenanceHistoryAttributeImporter.class);
        parkFacilityLongevityPlanAttributeImporter = manager.getImporter(ParkFacilityLongevityPlanAttributeImporter.class);
        portAttributeImporter = manager.getImporter(PortAttributeImporter.class);
    }

    public long doImport(FacilityAttribute facilityAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.FACILITYATTRIBUTE_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(facilityAttribute.getClass()));

        ps.setString(3, facilityAttribute.getFacilityId());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FACILITYATTRIBUTE));

        if (facilityAttribute instanceof FishingPortAttribute)
            fishingPortAttributeImporter.doImport((FishingPortAttribute) facilityAttribute, objectId);

        if (facilityAttribute instanceof MaintenanceHistoryAttribute)
            maintenanceHistoryAttributeImporter.doImport((MaintenanceHistoryAttribute) facilityAttribute, objectId);

        if (facilityAttribute instanceof ParkFacilityLongevityPlanAttribute)
            parkFacilityLongevityPlanAttributeImporter.doImport((ParkFacilityLongevityPlanAttribute) facilityAttribute, objectId);

        if (facilityAttribute instanceof PortAttribute)
            portAttributeImporter.doImport((PortAttribute) facilityAttribute, objectId);

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
