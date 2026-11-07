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
import org.citygml4j.ade.iur.model.uro.PublicSurveyDataQualityAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class PublicSurveyDataQualityAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public PublicSurveyDataQualityAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PUBLICSURVEYDATAQUALIT)) + " " +
                "(id, srcscalelod0, srcscalelod0_codespace, srcscalelod1, srcscalelod1_codespace, srcscalelod2," +
                "srcscalelod2_codespace, srcscalelod3, srcscalelod3_codespace, srcscalelod4, srcscalelod4_codespace," +
                "publicsurveysrcdesclod0, publicsurveysrcdes_codespace, publicsurveysrcdesclod1," +
                "publicsurveysrcd_codespace_1, publicsurveysrcdesclod2, publicsurveysrcd_codespace_2," +
                "publicsurveysrcdesclod3, publicsurveysrcd_codespace_3, publicsurveysrcdesclod4," +
                "publicsurveysrcd_codespace_4) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(PublicSurveyDataQualityAttribute publicSurveyDataQualityAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.PUBLICSURVEYDATAQU_SEQ));
        ps.setLong(1, objectId);

        if (publicSurveyDataQualityAttribute.getSrcScaleLod0() != null && publicSurveyDataQualityAttribute.getSrcScaleLod0().isSetValue()) {
            ps.setString(2, publicSurveyDataQualityAttribute.getSrcScaleLod0().getValue());
            ps.setString(3, publicSurveyDataQualityAttribute.getSrcScaleLod0().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (publicSurveyDataQualityAttribute.getSrcScaleLod1() != null && publicSurveyDataQualityAttribute.getSrcScaleLod1().isSetValue()) {
            ps.setString(4, publicSurveyDataQualityAttribute.getSrcScaleLod1().getValue());
            ps.setString(5, publicSurveyDataQualityAttribute.getSrcScaleLod1().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (publicSurveyDataQualityAttribute.getSrcScaleLod2() != null && publicSurveyDataQualityAttribute.getSrcScaleLod2().isSetValue()) {
            ps.setString(6, publicSurveyDataQualityAttribute.getSrcScaleLod2().getValue());
            ps.setString(7, publicSurveyDataQualityAttribute.getSrcScaleLod2().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (publicSurveyDataQualityAttribute.getSrcScaleLod3() != null && publicSurveyDataQualityAttribute.getSrcScaleLod3().isSetValue()) {
            ps.setString(8, publicSurveyDataQualityAttribute.getSrcScaleLod3().getValue());
            ps.setString(9, publicSurveyDataQualityAttribute.getSrcScaleLod3().getCodeSpace());
        } else {
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
        }

        if (publicSurveyDataQualityAttribute.getSrcScaleLod4() != null && publicSurveyDataQualityAttribute.getSrcScaleLod4().isSetValue()) {
            ps.setString(10, publicSurveyDataQualityAttribute.getSrcScaleLod4().getValue());
            ps.setString(11, publicSurveyDataQualityAttribute.getSrcScaleLod4().getCodeSpace());
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        if (!publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod0().isEmpty()) {
            valueJoiner.join(publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod0(), Code::getValue, Code::getCodeSpace);
            ps.setString(12, valueJoiner.result(0));
            ps.setString(13, valueJoiner.result(1));
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        if (!publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod1().isEmpty()) {
            valueJoiner.join(publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod1(), Code::getValue, Code::getCodeSpace);
            ps.setString(14, valueJoiner.result(0));
            ps.setString(15, valueJoiner.result(1));
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        if (!publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod2().isEmpty()) {
            valueJoiner.join(publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod2(), Code::getValue, Code::getCodeSpace);
            ps.setString(16, valueJoiner.result(0));
            ps.setString(17, valueJoiner.result(1));
        } else {
            ps.setNull(16, Types.VARCHAR);
            ps.setNull(17, Types.VARCHAR);
        }

        if (!publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod3().isEmpty()) {
            valueJoiner.join(publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod3(), Code::getValue, Code::getCodeSpace);
            ps.setString(18, valueJoiner.result(0));
            ps.setString(19, valueJoiner.result(1));
        } else {
            ps.setNull(18, Types.VARCHAR);
            ps.setNull(19, Types.VARCHAR);
        }

        if (!publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod4().isEmpty()) {
            valueJoiner.join(publicSurveyDataQualityAttribute.getPublicSurveySrcDescLod4(), Code::getValue, Code::getCodeSpace);
            ps.setString(20, valueJoiner.result(0));
            ps.setString(21, valueJoiner.result(1));
        } else {
            ps.setNull(20, Types.VARCHAR);
            ps.setNull(21, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.PUBLICSURVEYDATAQUALIT));

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
