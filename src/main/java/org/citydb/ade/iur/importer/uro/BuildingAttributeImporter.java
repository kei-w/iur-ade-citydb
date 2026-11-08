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
import org.citygml4j.ade.iur.model.uro.BuildingAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingDataQualityAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingDetailAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingDisasterRiskAttribute;
import org.citygml4j.ade.iur.model.uro.BuildingIDAttribute;
import org.citygml4j.ade.iur.model.uro.LargeCustomerFacilityAttribute;
import org.citygml4j.ade.iur.model.uro.RealEstateIDAttribute;

import java.sql.*;

public class BuildingAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final ObjectMapper objectMapper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final RealEstateIDAttributeImporter realEstateIDAttributeImporter;
    private final BuildingIDAttributeImporter buildingIDAttributeImporter;
    private final BuildingDetailAttributeImporter buildingDetailAttributeImporter;
    private final LargeCustomerFacilityAttributeImporter largeCustomerFacilityAttributeImporter;
    private final BuildingDisasterRiskAttributeImporter buildingDisasterRiskAttributeImporter;
    private final BuildingDataQualityAttributeImporter buildingDataQualityAttributeImporter;

    private int batchCounter;

    public BuildingAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();
        this.objectMapper = manager.getObjectMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGATTRIBUTE)) + " " +
                "(id, objectclass_id) " +
                "values (?, ?)");

        realEstateIDAttributeImporter = manager.getImporter(RealEstateIDAttributeImporter.class);
        buildingIDAttributeImporter = manager.getImporter(BuildingIDAttributeImporter.class);
        buildingDetailAttributeImporter = manager.getImporter(BuildingDetailAttributeImporter.class);
        largeCustomerFacilityAttributeImporter = manager.getImporter(LargeCustomerFacilityAttributeImporter.class);
        buildingDisasterRiskAttributeImporter = manager.getImporter(BuildingDisasterRiskAttributeImporter.class);
        buildingDataQualityAttributeImporter = manager.getImporter(BuildingDataQualityAttributeImporter.class);
    }

    public long doImport(BuildingAttribute buildingAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.BUILDINGATTRIBUTE_SEQ));
        ps.setLong(1, objectId);
        ps.setLong(2, objectMapper.getTypeClassId(buildingAttribute.getClass()));

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDINGATTRIBUTE));

        if (buildingAttribute instanceof RealEstateIDAttribute)
            realEstateIDAttributeImporter.doImport((RealEstateIDAttribute) buildingAttribute, objectId);

        if (buildingAttribute instanceof BuildingIDAttribute)
            buildingIDAttributeImporter.doImport((BuildingIDAttribute) buildingAttribute, objectId);

        if (buildingAttribute instanceof BuildingDetailAttribute)
            buildingDetailAttributeImporter.doImport((BuildingDetailAttribute) buildingAttribute, objectId);

        if (buildingAttribute instanceof LargeCustomerFacilityAttribute)
            largeCustomerFacilityAttributeImporter.doImport((LargeCustomerFacilityAttribute) buildingAttribute, objectId);

        if (buildingAttribute instanceof BuildingDisasterRiskAttribute)
            buildingDisasterRiskAttributeImporter.doImport((BuildingDisasterRiskAttribute) buildingAttribute, objectId);

        if (buildingAttribute instanceof BuildingDataQualityAttribute)
            buildingDataQualityAttributeImporter.doImport((BuildingDataQualityAttribute) buildingAttribute, objectId);

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
