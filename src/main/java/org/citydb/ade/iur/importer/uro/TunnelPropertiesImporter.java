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
import org.citygml4j.model.citygml.tunnel.AbstractTunnel;

import java.sql.*;

public class TunnelPropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final ConstructionBaseAttributeImporter constructionBaseAttributeImporter;
    private final TunnelStructureAttributeImporter tunnelStructureAttributeImporter;
    private final TunnelFunctionalAttributeImporter tunnelFunctionalAttributeImporter;
    private final ConstructionRiskAssessmentAttributeImporter constructionRiskAssessmentAttributeImporter;
    private final DisasterRiskAttributeImporter disasterRiskAttributeImporter;
    private final DmAttributeImporter dmAttributeImporter;
    private final DataQualityAttributeImporter dataQualityAttributeImporter;
    private final FacilityIdAttributeImporter facilityIdAttributeImporter;
    private final FacilityTypeAttributeImporter facilityTypeAttributeImporter;
    private final FacilityAttributeImporter facilityAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;

    private int batchCounter;

    public TunnelPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.TUNNEL)) + " " +
                "(id, tunbaseattribute_id, tunstructureattribute_id, tunfunctionalattribute_id," +
                "tunriskassessmentattribut_id, tundataqualityattribute_id, tunfacilityidattribute_id," +
                "tunfacilitytypeattribute_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        constructionBaseAttributeImporter = manager.getImporter(ConstructionBaseAttributeImporter.class);
        tunnelStructureAttributeImporter = manager.getImporter(TunnelStructureAttributeImporter.class);
        tunnelFunctionalAttributeImporter = manager.getImporter(TunnelFunctionalAttributeImporter.class);
        constructionRiskAssessmentAttributeImporter = manager.getImporter(ConstructionRiskAssessmentAttributeImporter.class);
        disasterRiskAttributeImporter = manager.getImporter(DisasterRiskAttributeImporter.class);
        dmAttributeImporter = manager.getImporter(DmAttributeImporter.class);
        dataQualityAttributeImporter = manager.getImporter(DataQualityAttributeImporter.class);
        facilityIdAttributeImporter = manager.getImporter(FacilityIdAttributeImporter.class);
        facilityTypeAttributeImporter = manager.getImporter(FacilityTypeAttributeImporter.class);
        facilityAttributeImporter = manager.getImporter(FacilityAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, AbstractTunnel parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long tunBaseAttributeId = 0;
        if (properties.contains(TunBaseAttributePropertyElement.class)) {
            TunBaseAttributePropertyElement property = properties.getFirst(TunBaseAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tunBaseAttributeId = constructionBaseAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tunBaseAttributeId != 0)
            ps.setLong(2, tunBaseAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long tunStructureAttributeId = 0;
        if (properties.contains(TunStructureAttributePropertyElement.class)) {
            TunStructureAttributePropertyElement property = properties.getFirst(TunStructureAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tunStructureAttributeId = tunnelStructureAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tunStructureAttributeId != 0)
            ps.setLong(3, tunStructureAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long tunFunctionalAttributeId = 0;
        if (properties.contains(TunFunctionalAttributePropertyElement.class)) {
            TunFunctionalAttributePropertyElement property = properties.getFirst(TunFunctionalAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tunFunctionalAttributeId = tunnelFunctionalAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tunFunctionalAttributeId != 0)
            ps.setLong(4, tunFunctionalAttributeId);
        else
            ps.setNull(4, Types.NULL);

        long tunRiskAssessmentAttributeId = 0;
        if (properties.contains(TunRiskAssessmentAttributePropertyElement.class)) {
            TunRiskAssessmentAttributePropertyElement property = properties.getFirst(TunRiskAssessmentAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tunRiskAssessmentAttributeId = constructionRiskAssessmentAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tunRiskAssessmentAttributeId != 0)
            ps.setLong(5, tunRiskAssessmentAttributeId);
        else
            ps.setNull(5, Types.NULL);

        long tunDataQualityAttributeId = 0;
        if (properties.contains(TunDataQualityAttributePropertyElement.class)) {
            TunDataQualityAttributePropertyElement property = properties.getFirst(TunDataQualityAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tunDataQualityAttributeId = dataQualityAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tunDataQualityAttributeId != 0)
            ps.setLong(6, tunDataQualityAttributeId);
        else
            ps.setNull(6, Types.NULL);

        long tunFacilityIdAttributeId = 0;
        if (properties.contains(TunFacilityIdAttributePropertyElement.class)) {
            TunFacilityIdAttributePropertyElement property = properties.getFirst(TunFacilityIdAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tunFacilityIdAttributeId = facilityIdAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tunFacilityIdAttributeId != 0)
            ps.setLong(7, tunFacilityIdAttributeId);
        else
            ps.setNull(7, Types.NULL);

        long tunFacilityTypeAttributeId = 0;
        if (properties.contains(TunFacilityTypeAttributePropertyElement.class)) {
            TunFacilityTypeAttributePropertyElement property = properties.getFirst(TunFacilityTypeAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                tunFacilityTypeAttributeId = facilityTypeAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (tunFacilityTypeAttributeId != 0)
            ps.setLong(8, tunFacilityTypeAttributeId);
        else
            ps.setNull(8, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.TUNNEL));

        if (properties.contains(TunDisasterRiskAttributePropertyElement.class)) {
            for (TunDisasterRiskAttributePropertyElement property : properties.getAll(TunDisasterRiskAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    disasterRiskAttributeImporter.doImport(property.getValue().getObject(), parentId, "tunnel_tundisasterriskatt_id");
            }
        }

        if (properties.contains(TunDmAttributePropertyElement.class)) {
            for (TunDmAttributePropertyElement property : properties.getAll(TunDmAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    dmAttributeImporter.doImport(property.getValue().getObject(), parentId, "tunnel_tundmattribute_id");
            }
        }

        if (properties.contains(TunFacilityAttributePropertyElement.class)) {
            for (TunFacilityAttributePropertyElement property : properties.getAll(TunFacilityAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    facilityAttributeImporter.doImport(property.getValue().getObject(), parentId, "tunnel_tunfacilityattribu_id");
            }
        }

        if (properties.contains(TunKeyValuePairAttributePropertyElement.class)) {
            for (TunKeyValuePairAttributePropertyElement property : properties.getAll(TunKeyValuePairAttributePropertyElement.class)) {
                if (property.isSetValue() && property.getValue().isSetObject())
                    keyValuePairAttributeImporter.doImport(property.getValue().getObject(), parentId, "tunnel_tunkeyvaluepairatt_id");
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
