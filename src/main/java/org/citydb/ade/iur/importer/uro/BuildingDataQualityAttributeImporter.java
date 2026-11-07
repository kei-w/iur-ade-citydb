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
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.BuildingDataQualityAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class BuildingDataQualityAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final BuildingAttributeImporter buildingAttributeImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public BuildingDataQualityAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDINGDATAQUALITYATT)) + " " +
                "(id, appearancesrcdesc, appearancesrcdesc_codespace, geometrysrcdesc, geometrysrcdesc_codespace," +
                "lod1heighttype, lod1heighttype_codespace, lodtype, srcscale, srcscale_codespace, thematicsrcdesc," +
                "thematicsrcdesc_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        buildingAttributeImporter = manager.getImporter(BuildingAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(BuildingDataQualityAttribute buildingDataQualityAttribute) throws CityGMLImportException, SQLException {
        long objectId = buildingAttributeImporter.doImport(buildingDataQualityAttribute);
        ps.setLong(1, objectId);

        if (!buildingDataQualityAttribute.getAppearanceSrcDesc().isEmpty()) {
            valueJoiner.join(buildingDataQualityAttribute.getAppearanceSrcDesc(), Code::getValue, Code::getCodeSpace);
            ps.setString(2, valueJoiner.result(0));
            ps.setString(3, valueJoiner.result(1));
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (!buildingDataQualityAttribute.getGeometrySrcDesc().isEmpty()) {
            valueJoiner.join(buildingDataQualityAttribute.getGeometrySrcDesc(), Code::getValue, Code::getCodeSpace);
            ps.setString(4, valueJoiner.result(0));
            ps.setString(5, valueJoiner.result(1));
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (buildingDataQualityAttribute.getLod1HeightType() != null && buildingDataQualityAttribute.getLod1HeightType().isSetValue()) {
            ps.setString(6, buildingDataQualityAttribute.getLod1HeightType().getValue());
            ps.setString(7, buildingDataQualityAttribute.getLod1HeightType().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (!buildingDataQualityAttribute.getLodType().isEmpty())
            ps.setString(8, valueJoiner.join(buildingDataQualityAttribute.getLodType()));
        else
            ps.setNull(8, Types.VARCHAR);

        if (!buildingDataQualityAttribute.getSrcScale().isEmpty()) {
            valueJoiner.join(buildingDataQualityAttribute.getSrcScale(), Code::getValue, Code::getCodeSpace);
            ps.setString(9, valueJoiner.result(0));
            ps.setString(10, valueJoiner.result(1));
        } else {
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.VARCHAR);
        }

        if (!buildingDataQualityAttribute.getThematicSrcDesc().isEmpty()) {
            valueJoiner.join(buildingDataQualityAttribute.getThematicSrcDesc(), Code::getValue, Code::getCodeSpace);
            ps.setString(11, valueJoiner.result(0));
            ps.setString(12, valueJoiner.result(1));
        } else {
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDINGDATAQUALITYATT));

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
