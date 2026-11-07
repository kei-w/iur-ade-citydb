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
import org.citydb.core.ade.importer.ADEPropertyCollection;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.bridge.AbstractBridge;

import java.sql.*;

public class BridgePropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final ConstructionBaseAttributeImporter constructionBaseAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final BridgeStructureAttributeImporter bridgeStructureAttributeImporter;
    private final BridgeFunctionalAttributeImporter bridgeFunctionalAttributeImporter;
    private final ConstructionRiskAssessmentAttributeImporter constructionRiskAssessmentAttributeImporter;
    private final DisasterRiskAttributeImporter disasterRiskAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final ConstructionDataQualityAttributeImporter constructionDataQualityAttributeImporter;

    private int batchCounter;

    public BridgePropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BRIDGE)) + " " +
                "(id, bridbaseattribute_id, bridfacilityidattribute_id, bridfacilitytypeattribute_id," +
                "bridstructureattribute_id, bridfunctionalattribute_id, bridriskassessmentattribu_id," +
                "briddataqualityattribute_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        constructionBaseAttributeImporter = manager.getImporter(ConstructionBaseAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        bridgeStructureAttributeImporter = manager.getImporter(BridgeStructureAttributeImporter.class);
        bridgeFunctionalAttributeImporter = manager.getImporter(BridgeFunctionalAttributeImporter.class);
        constructionRiskAssessmentAttributeImporter = manager.getImporter(ConstructionRiskAssessmentAttributeImporter.class);
        disasterRiskAttributeImporter = manager.getImporter(DisasterRiskAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        constructionDataQualityAttributeImporter = manager.getImporter(ConstructionDataQualityAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, AbstractBridge parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long bridBaseAttributeId = 0;
        if (properties.contains(BridBaseAttributePropertyElement.class)) {
            BridBaseAttributePropertyElement property = properties.getFirst(BridBaseAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bridBaseAttributeId = constructionBaseAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bridBaseAttributeId != 0)
            ps.setLong(2, bridBaseAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long bridFacilityIdAttributeId = 0;
        if (properties.contains(BridFacilityIdAttributePropertyElement.class)) {
            BridFacilityIdAttributePropertyElement property = properties.getFirst(BridFacilityIdAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bridFacilityIdAttributeId = facilityIdAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bridFacilityIdAttributeId != 0)
            ps.setLong(3, bridFacilityIdAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long bridFacilityTypeAttributeId = 0;
        if (properties.contains(BridFacilityTypeAttributePropertyElement.class)) {
            BridFacilityTypeAttributePropertyElement property = properties.getFirst(BridFacilityTypeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bridFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bridFacilityTypeAttributeId != 0)
            ps.setLong(4, bridFacilityTypeAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long bridStructureAttributeId = 0;
        if (properties.contains(BridStructureAttributePropertyElement.class)) {
            BridStructureAttributePropertyElement property = properties.getFirst(BridStructureAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bridStructureAttributeId = bridgeStructureAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bridStructureAttributeId != 0)
            ps.setLong(5, bridStructureAttributeId);
        else
            ps.setNull(5, Types.NULL);

        long bridFunctionalAttributeId = 0;
        if (properties.contains(BridFunctionalAttributePropertyElement.class)) {
            BridFunctionalAttributePropertyElement property = properties.getFirst(BridFunctionalAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bridFunctionalAttributeId = bridgeFunctionalAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bridFunctionalAttributeId != 0)
            ps.setLong(6, bridFunctionalAttributeId);
        else
            ps.setNull(6, Types.NULL);

        long bridRiskAssessmentAttributeId = 0;
        if (properties.contains(BridRiskAssessmentAttributePropertyElement.class)) {
            BridRiskAssessmentAttributePropertyElement property = properties.getFirst(BridRiskAssessmentAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bridRiskAssessmentAttributeId = constructionRiskAssessmentAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bridRiskAssessmentAttributeId != 0)
            ps.setLong(7, bridRiskAssessmentAttributeId);
        else
            ps.setNull(7, Types.NULL);

        long bridDataQualityAttributeId = 0;
        if (properties.contains(BridDataQualityAttributePropertyElement.class)) {
            BridDataQualityAttributePropertyElement property = properties.getFirst(BridDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                bridDataQualityAttributeId = constructionDataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (bridDataQualityAttributeId != 0)
            ps.setLong(8, bridDataQualityAttributeId);
        else
            ps.setNull(8, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BRIDGE));

        if (properties.contains(BridFacilityAttributePropertyElement.class)) {
            for (BridFacilityAttributePropertyElement property : properties.getAll(BridFacilityAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    facilityAttributeImporter.doImport(property.getValue().getObject(), parentId, "bridge_bridfacilityattrib_id");
            }
        }

        if (properties.contains(BridDisasterRiskAttributePropertyElement.class)) {
            for (BridDisasterRiskAttributePropertyElement property : properties.getAll(BridDisasterRiskAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    disasterRiskAttributeImporter.doImport(property.getValue().getObject(), parentId, "bridge_briddisasterriskat_id");
            }
        }

        if (properties.contains(BridDmAttributePropertyElement.class)) {
            for (BridDmAttributePropertyElement property : properties.getAll(BridDmAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    dmAttributeImporter.doImport(property.getValue().getObject(), parentId, "bridge_briddmattribute_id");
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
