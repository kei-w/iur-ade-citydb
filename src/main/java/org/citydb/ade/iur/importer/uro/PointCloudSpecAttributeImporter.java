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
import org.citygml4j.ade.iur.model.uro.PointCloudSpecAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class PointCloudSpecAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public PointCloudSpecAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.POINTCLOUDSPECATTRIBUT)) + " " +
                "(id, class, class_codespace, function, function_codespace, srcscale, srcscale_codespace," +
                "srcrequiredhorizontalaccurac, srcrequiredhorizontalacc_uom, srcrequiredverticalaccuracy," +
                "srcrequiredverticalaccur_uom, srcrequiredpointdensity, srcrequiredpointdensity_uom," +
                "srcfilteringcriteria, srcfilteringcriter_codespace, srcgridinterval, srcgridinterval_uom," +
                "isintensity, spectralbandset, spectralbandset_codespace, isreturninfo, isstandardclassification," +
                "customclassificationtype, customclassificati_codespace, pointcloudassociationmethod," +
                "pointcloudassociat_codespace, pointsubsamplinginfo, pointsubsamplingin_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(PointCloudSpecAttribute pointCloudSpecAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.POINTCLOUDSPECATTR_SEQ));
        ps.setLong(1, objectId);

        if (pointCloudSpecAttribute.getClassifier() != null && pointCloudSpecAttribute.getClassifier().isSetValue()) {
            ps.setString(2, pointCloudSpecAttribute.getClassifier().getValue());
            ps.setString(3, pointCloudSpecAttribute.getClassifier().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (!pointCloudSpecAttribute.getFunction().isEmpty()) {
            valueJoiner.join(pointCloudSpecAttribute.getFunction(), Code::getValue, Code::getCodeSpace);
            ps.setString(4, valueJoiner.result(0));
            ps.setString(5, valueJoiner.result(1));
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getSrcScale() != null && pointCloudSpecAttribute.getSrcScale().isSetValue()) {
            ps.setString(6, pointCloudSpecAttribute.getSrcScale().getValue());
            ps.setString(7, pointCloudSpecAttribute.getSrcScale().getCodeSpace());
        } else {
            ps.setNull(6, Types.VARCHAR);
            ps.setNull(7, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getSrcRequiredHorizontalAccuracy() != null && pointCloudSpecAttribute.getSrcRequiredHorizontalAccuracy().isSetValue()) {
            ps.setDouble(8, pointCloudSpecAttribute.getSrcRequiredHorizontalAccuracy().getValue());
            ps.setString(9, pointCloudSpecAttribute.getSrcRequiredHorizontalAccuracy().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getSrcRequiredVerticalAccuracy() != null && pointCloudSpecAttribute.getSrcRequiredVerticalAccuracy().isSetValue()) {
            ps.setDouble(10, pointCloudSpecAttribute.getSrcRequiredVerticalAccuracy().getValue());
            ps.setString(11, pointCloudSpecAttribute.getSrcRequiredVerticalAccuracy().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getSrcRequiredPointDensity() != null && pointCloudSpecAttribute.getSrcRequiredPointDensity().isSetValue()) {
            ps.setDouble(12, pointCloudSpecAttribute.getSrcRequiredPointDensity().getValue());
            ps.setString(13, pointCloudSpecAttribute.getSrcRequiredPointDensity().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (!pointCloudSpecAttribute.getSrcFilteringCriteria().isEmpty()) {
            valueJoiner.join(pointCloudSpecAttribute.getSrcFilteringCriteria(), Code::getValue, Code::getCodeSpace);
            ps.setString(14, valueJoiner.result(0));
            ps.setString(15, valueJoiner.result(1));
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getSrcGridInterval() != null && pointCloudSpecAttribute.getSrcGridInterval().isSetValue()) {
            ps.setDouble(16, pointCloudSpecAttribute.getSrcGridInterval().getValue());
            ps.setString(17, pointCloudSpecAttribute.getSrcGridInterval().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getIsIntensity() != null)
            ps.setInt(18, pointCloudSpecAttribute.getIsIntensity() ? 1 : 0);
        else
            ps.setNull(18, Types.INTEGER);

        if (!pointCloudSpecAttribute.getSpectralBandSet().isEmpty()) {
            valueJoiner.join(pointCloudSpecAttribute.getSpectralBandSet(), Code::getValue, Code::getCodeSpace);
            ps.setString(19, valueJoiner.result(0));
            ps.setString(20, valueJoiner.result(1));
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getIsReturnInfo() != null)
            ps.setInt(21, pointCloudSpecAttribute.getIsReturnInfo() ? 1 : 0);
        else
            ps.setNull(21, Types.INTEGER);

        if (pointCloudSpecAttribute.getIsStandardClassification() != null)
            ps.setInt(22, pointCloudSpecAttribute.getIsStandardClassification() ? 1 : 0);
        else
            ps.setNull(22, Types.INTEGER);

        if (!pointCloudSpecAttribute.getCustomClassificationType().isEmpty()) {
            valueJoiner.join(pointCloudSpecAttribute.getCustomClassificationType(), Code::getValue, Code::getCodeSpace);
            ps.setString(23, valueJoiner.result(0));
            ps.setString(24, valueJoiner.result(1));
        } else {
            ps.setNull(23, Types.VARCHAR);
            ps.setNull(24, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getPointCloudAssociationMethod() != null && pointCloudSpecAttribute.getPointCloudAssociationMethod().isSetValue()) {
            ps.setString(25, pointCloudSpecAttribute.getPointCloudAssociationMethod().getValue());
            ps.setString(26, pointCloudSpecAttribute.getPointCloudAssociationMethod().getCodeSpace());
        } else {
            ps.setNull(25, Types.VARCHAR);
            ps.setNull(26, Types.VARCHAR);
        }

        if (pointCloudSpecAttribute.getPointSubsamplingInfo() != null && pointCloudSpecAttribute.getPointSubsamplingInfo().isSetValue()) {
            ps.setString(27, pointCloudSpecAttribute.getPointSubsamplingInfo().getValue());
            ps.setString(28, pointCloudSpecAttribute.getPointSubsamplingInfo().getCodeSpace());
        } else {
            ps.setNull(27, Types.VARCHAR);
            ps.setNull(28, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.POINTCLOUDSPECATTRIBUT));

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
