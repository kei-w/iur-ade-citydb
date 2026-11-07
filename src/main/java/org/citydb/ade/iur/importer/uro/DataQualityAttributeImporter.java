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
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.DataQualityAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class DataQualityAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicSurveyDataQualityAttributeImporter publicSurveyDataQualityAttributeImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public DataQualityAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.DATAQUALITYATTRIBUTE)) + " " +
                "(id, appearancesrcdesclod0, appearancesrcdescl_codespace, appearancesrcdesclod1, " +
                "appearancesrcdes_codespace_1, appearancesrcdesclod2, appearancesrcdes_codespace_2, " +
                "appearancesrcdesclod3, appearancesrcdes_codespace_3, appearancesrcdesclod4, " +
                "appearancesrcdes_codespace_4, geometrysrcdesclod0, geometrysrcdesclod_codespace, " +
                "geometrysrcdesclod1, geometrysrcdescl_codespace_1, geometrysrcdesclod2, " +
                "geometrysrcdescl_codespace_2, geometrysrcdesclod3, geometrysrcdescl_codespace_3, " +
                "geometrysrcdesclod4, geometrysrcdescl_codespace_4, lod1heighttype, lod1heighttype_codespace, " +
                "lodtype, lodtype_codespace, publicsurveydataqualityat_id, thematicsrcdesc, " +
                "thematicsrcdesc_codespace, trandataacquisition) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        publicSurveyDataQualityAttributeImporter = manager.getImporter(PublicSurveyDataQualityAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(DataQualityAttribute dataQualityAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.DATAQUALITYATTRIBU_SEQ));
        ps.setLong(1, objectId);

        if (!dataQualityAttribute.getAppearanceSrcDescLod0().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getAppearanceSrcDescLod0(), Code::getValue, Code::getCodeSpace);
            ps.setString(2, valueJoiner.result(0));
            ps.setString(3, valueJoiner.result(1));
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getAppearanceSrcDescLod1().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getAppearanceSrcDescLod1(), Code::getValue, Code::getCodeSpace);
            ps.setString(4, valueJoiner.result(0));
            ps.setString(5, valueJoiner.result(1));
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getAppearanceSrcDescLod2().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getAppearanceSrcDescLod2(), Code::getValue, Code::getCodeSpace);
            ps.setString(6, valueJoiner.result(0));
            ps.setString(7, valueJoiner.result(1));
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getAppearanceSrcDescLod3().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getAppearanceSrcDescLod3(), Code::getValue, Code::getCodeSpace);
            ps.setString(8, valueJoiner.result(0));
            ps.setString(9, valueJoiner.result(1));
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getAppearanceSrcDescLod4().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getAppearanceSrcDescLod4(), Code::getValue, Code::getCodeSpace);
            ps.setString(10, valueJoiner.result(0));
            ps.setString(11, valueJoiner.result(1));
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getGeometrySrcDescLod0().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getGeometrySrcDescLod0(), Code::getValue, Code::getCodeSpace);
            ps.setString(12, valueJoiner.result(0));
            ps.setString(13, valueJoiner.result(1));
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getGeometrySrcDescLod1().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getGeometrySrcDescLod1(), Code::getValue, Code::getCodeSpace);
            ps.setString(14, valueJoiner.result(0));
            ps.setString(15, valueJoiner.result(1));
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getGeometrySrcDescLod2().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getGeometrySrcDescLod2(), Code::getValue, Code::getCodeSpace);
            ps.setString(16, valueJoiner.result(0));
            ps.setString(17, valueJoiner.result(1));
        } else {
            ps.setNull(16, Types.VARCHAR);
            ps.setNull(17, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getGeometrySrcDescLod3().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getGeometrySrcDescLod3(), Code::getValue, Code::getCodeSpace);
            ps.setString(18, valueJoiner.result(0));
            ps.setString(19, valueJoiner.result(1));
        } else {
            ps.setNull(18, Types.VARCHAR);
            ps.setNull(19, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getGeometrySrcDescLod4().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getGeometrySrcDescLod4(), Code::getValue, Code::getCodeSpace);
            ps.setString(20, valueJoiner.result(0));
            ps.setString(21, valueJoiner.result(1));
        } else {
            ps.setNull(20, Types.VARCHAR);
            ps.setNull(21, Types.VARCHAR);
        }

        if (dataQualityAttribute.getLod1HeightType() != null && dataQualityAttribute.getLod1HeightType().isSetValue()) {
            ps.setString(22, dataQualityAttribute.getLod1HeightType().getValue());
            ps.setString(23, dataQualityAttribute.getLod1HeightType().getCodeSpace());
        } else {
            ps.setNull(22, Types.VARCHAR);
            ps.setNull(23, Types.VARCHAR);
        }

        if (!dataQualityAttribute.getLodType().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getLodType(), Code::getValue, Code::getCodeSpace);
            ps.setString(24, valueJoiner.result(0));
            ps.setString(25, valueJoiner.result(1));
        } else {
            ps.setNull(24, Types.VARCHAR);
            ps.setNull(25, Types.VARCHAR);
        }

        long publicSurveyDataQualityAttributeId = 0;
        if (dataQualityAttribute.getPublicSurveyDataQualityAttribute() != null && dataQualityAttribute.getPublicSurveyDataQualityAttribute().isSetObject())
            publicSurveyDataQualityAttributeId = publicSurveyDataQualityAttributeImporter.doImport(dataQualityAttribute.getPublicSurveyDataQualityAttribute().getObject());

        if (publicSurveyDataQualityAttributeId != 0)
            ps.setLong(26, publicSurveyDataQualityAttributeId);
        else
            ps.setNull(26, Types.NULL);

        if (!dataQualityAttribute.getThematicSrcDesc().isEmpty()) {
            valueJoiner.join(dataQualityAttribute.getThematicSrcDesc(), Code::getValue, Code::getCodeSpace);
            ps.setString(27, valueJoiner.result(0));
            ps.setString(28, valueJoiner.result(1));
        } else {
            ps.setNull(27, Types.VARCHAR);
            ps.setNull(28, Types.VARCHAR);
        }

        ps.setString(29, dataQualityAttribute.getTranDataAcquisition());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.DATAQUALITYATTRIBUTE));

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
