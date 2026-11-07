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

package org.citydb.ade.iur.importer.urf;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.importer.uro.DataQualityAttributeImporter;
import org.citydb.ade.iur.importer.uro.KeyValuePairAttributeImporter;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.urf.TargetProperty;
import org.citygml4j.ade.iur.model.urf.UrbanFunction;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttributeProperty;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.AbstractGeometry;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;

import java.sql.*;
import java.time.LocalDate;

public class UrbanFunctionImporter implements UrbanFunctionModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UrbanFunctionToCityObjectImporter urbanFunctionToCityObjectImporter;
    private final UrbanFunctionToKeyValuePairAttributeImporter urbanFunctionToKeyValuePairAttributeImporter;
    private final KeyValuePairAttributeImporter keyValuePairAttributeImporter;
    private final DataQualityAttributeImporter dataQualityAttributeImporter;
    private final AttributeValueJoiner valueJoiner;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public UrbanFunctionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URBANFUNCTION)) + " " +
                "(id, objectclass_id, class, class_codespace, function, function_codespace, usage, usage_codespace," +
                "validfrom, validfromtype, validfromtype_codespace, enactmentfiscalyear, validto, validtotype," +
                "validtotype_codespace, expirationfiscalyear, legalgrounds, custodian, notificationnumber," +
                "finalnotificationdate, finalnotificationnumber, urbanplantype, urbanplantype_codespace," +
                "areaclassificationtype, areaclassification_codespace, nominalarea, nominalarea_uom, prefecture," +
                "prefecture_codespace, city, city_codespace, reference, reason, note, surveyyear," +
                "lod_2multisurface_id, lod_1multisurface_id, lod0multisurface_id, lod1multisurface_id," +
                "lod_2multicurve, lod_1multicurve, lod0multicurve, lod1multicurve, lod_2multipoint, lod_1multipoint," +
                "lod0multipoint, dataqualityattribute_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        urbanFunctionToCityObjectImporter = manager.getImporter(UrbanFunctionToCityObjectImporter.class);
        urbanFunctionToKeyValuePairAttributeImporter = manager.getImporter(UrbanFunctionToKeyValuePairAttributeImporter.class);
        keyValuePairAttributeImporter = manager.getImporter(KeyValuePairAttributeImporter.class);
        dataQualityAttributeImporter = manager.getImporter(DataQualityAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(UrbanFunction urbanFunction, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        if (urbanFunction.getClassifier() != null && urbanFunction.getClassifier().isSetValue()) {
            ps.setString(3, urbanFunction.getClassifier().getValue());
            ps.setString(4, urbanFunction.getClassifier().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (!urbanFunction.getFunction().isEmpty()) {
            valueJoiner.join(urbanFunction.getFunction(), Code::getValue, Code::getCodeSpace);
            ps.setString(5, valueJoiner.result(0));
            ps.setString(6, valueJoiner.result(1));
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (!urbanFunction.getUsage().isEmpty()) {
            valueJoiner.join(urbanFunction.getUsage(), Code::getValue, Code::getCodeSpace);
            ps.setString(7, valueJoiner.result(0));
            ps.setString(8, valueJoiner.result(1));
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (urbanFunction.getValidFrom() != null)
            ps.setDate(9, Date.valueOf(urbanFunction.getValidFrom()));
        else
            ps.setNull(9, Types.DATE);

        if (urbanFunction.getValidFromType() != null && urbanFunction.getValidFromType().isSetValue()) {
            ps.setString(10, urbanFunction.getValidFromType().getValue());
            ps.setString(11, urbanFunction.getValidFromType().getCodeSpace());
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        if (urbanFunction.getEnactmentFiscalYear() != null)
            ps.setDate(12, Date.valueOf(LocalDate.of(urbanFunction.getEnactmentFiscalYear().getValue(), 1, 1)));
        else
            ps.setNull(12, Types.DATE);

        if (urbanFunction.getValidTo() != null)
            ps.setDate(13, Date.valueOf(urbanFunction.getValidTo()));
        else
            ps.setNull(13, Types.DATE);

        if (urbanFunction.getValidToType() != null && urbanFunction.getValidToType().isSetValue()) {
            ps.setString(14, urbanFunction.getValidToType().getValue());
            ps.setString(15, urbanFunction.getValidToType().getCodeSpace());
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        if (urbanFunction.getExpirationFiscalYear() != null)
            ps.setDate(16, Date.valueOf(LocalDate.of(urbanFunction.getExpirationFiscalYear().getValue(), 1, 1)));
        else
            ps.setNull(16, Types.DATE);

        ps.setString(17, urbanFunction.getLegalGrounds());

        ps.setString(18, urbanFunction.getCustodian());

        ps.setString(19, urbanFunction.getNotificationNumber());

        if (urbanFunction.getFinalNotificationDate() != null)
            ps.setDate(20, Date.valueOf(urbanFunction.getFinalNotificationDate()));
        else
            ps.setNull(20, Types.DATE);

        ps.setString(21, urbanFunction.getFinalNotificationNumber());

        if (urbanFunction.getUrbanPlanType() != null && urbanFunction.getUrbanPlanType().isSetValue()) {
            ps.setString(22, urbanFunction.getUrbanPlanType().getValue());
            ps.setString(23, urbanFunction.getUrbanPlanType().getCodeSpace());
        } else {
            ps.setNull(22, Types.VARCHAR);
            ps.setNull(23, Types.VARCHAR);
        }

        if (urbanFunction.getAreaClassificationType() != null && urbanFunction.getAreaClassificationType().isSetValue()) {
            ps.setString(24, urbanFunction.getAreaClassificationType().getValue());
            ps.setString(25, urbanFunction.getAreaClassificationType().getCodeSpace());
        } else {
            ps.setNull(24, Types.VARCHAR);
            ps.setNull(25, Types.VARCHAR);
        }

        if (urbanFunction.getNominalArea() != null && urbanFunction.getNominalArea().isSetValue()) {
            ps.setDouble(26, urbanFunction.getNominalArea().getValue());
            ps.setString(27, urbanFunction.getNominalArea().getUom());
        } else {
            ps.setNull(26, Types.DOUBLE);
            ps.setNull(27, Types.VARCHAR);
        }

        if (urbanFunction.getPrefecture() != null && urbanFunction.getPrefecture().isSetValue()) {
            ps.setString(28, urbanFunction.getPrefecture().getValue());
            ps.setString(29, urbanFunction.getPrefecture().getCodeSpace());
        } else {
            ps.setNull(28, Types.VARCHAR);
            ps.setNull(29, Types.VARCHAR);
        }

        if (urbanFunction.getCity() != null && urbanFunction.getCity().isSetValue()) {
            ps.setString(30, urbanFunction.getCity().getValue());
            ps.setString(31, urbanFunction.getCity().getCodeSpace());
        } else {
            ps.setNull(30, Types.VARCHAR);
            ps.setNull(31, Types.VARCHAR);
        }

        ps.setString(32, urbanFunction.getReference());

        if (urbanFunction.getReason() != null && urbanFunction.getReason().isSetValue())
            ps.setString(33, urbanFunction.getReason().getValue());
        else
            ps.setNull(33, Types.VARCHAR);

        if (urbanFunction.getNote() != null && urbanFunction.getNote().isSetValue())
            ps.setString(34, urbanFunction.getNote().getValue());
        else
            ps.setNull(34, Types.VARCHAR);

        if (urbanFunction.getSurveyYear() != null)
            ps.setDate(35, Date.valueOf(LocalDate.of(urbanFunction.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(35, Types.DATE);

        long lodMinus2MultiSurfaceId = 0;
        if (urbanFunction.getLodMinus2MultiSurface() != null) {
            MultiSurfaceProperty property = urbanFunction.getLodMinus2MultiSurface();
            if (property.isSetMultiSurface()) {
                lodMinus2MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.URBANFUNCTION), objectId, "lod_2multisurface_id");
            }
        }

        if (lodMinus2MultiSurfaceId != 0)
            ps.setLong(36, lodMinus2MultiSurfaceId);
        else
            ps.setNull(36, Types.NULL);

        long lodMinus1MultiSurfaceId = 0;
        if (urbanFunction.getLodMinus1MultiSurface() != null) {
            MultiSurfaceProperty property = urbanFunction.getLodMinus1MultiSurface();
            if (property.isSetMultiSurface()) {
                lodMinus1MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.URBANFUNCTION), objectId, "lod_1multisurface_id");
            }
        }

        if (lodMinus1MultiSurfaceId != 0)
            ps.setLong(37, lodMinus1MultiSurfaceId);
        else
            ps.setNull(37, Types.NULL);

        long lod0MultiSurfaceId = 0;
        if (urbanFunction.getLod0MultiSurface() != null) {
            MultiSurfaceProperty property = urbanFunction.getLod0MultiSurface();
            if (property.isSetMultiSurface()) {
                lod0MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.URBANFUNCTION), objectId, "lod0multisurface_id");
            }
        }

        if (lod0MultiSurfaceId != 0)
            ps.setLong(38, lod0MultiSurfaceId);
        else
            ps.setNull(38, Types.NULL);

        long lod1MultiSurfaceId = 0;
        if (urbanFunction.getLod1MultiSurface() != null) {
            MultiSurfaceProperty property = urbanFunction.getLod1MultiSurface();
            if (property.isSetMultiSurface()) {
                lod1MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.URBANFUNCTION), objectId, "lod1multisurface_id");
            }
        }

        if (lod1MultiSurfaceId != 0)
            ps.setLong(39, lod1MultiSurfaceId);
        else
            ps.setNull(39, Types.NULL);

        GeometryObject lodMinus2MultiCurve = null;
        if (urbanFunction.getLodMinus2MultiCurve() != null) {
            lodMinus2MultiCurve = geometryConverter.getMultiCurve(urbanFunction.getLodMinus2MultiCurve());
            urbanFunction.getLodMinus2MultiCurve().unsetMultiCurve();
        }

        if (lodMinus2MultiCurve != null)
            ps.setObject(40, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lodMinus2MultiCurve, connection));
        else
            ps.setNull(40, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        GeometryObject lodMinus1MultiCurve = null;
        if (urbanFunction.getLodMinus1MultiCurve() != null) {
            lodMinus1MultiCurve = geometryConverter.getMultiCurve(urbanFunction.getLodMinus1MultiCurve());
            urbanFunction.getLodMinus1MultiCurve().unsetMultiCurve();
        }

        if (lodMinus1MultiCurve != null)
            ps.setObject(41, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lodMinus1MultiCurve, connection));
        else
            ps.setNull(41, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        GeometryObject lod0MultiCurve = null;
        if (urbanFunction.getLod0MultiCurve() != null) {
            lod0MultiCurve = geometryConverter.getMultiCurve(urbanFunction.getLod0MultiCurve());
            urbanFunction.getLod0MultiCurve().unsetMultiCurve();
        }

        if (lod0MultiCurve != null)
            ps.setObject(42, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lod0MultiCurve, connection));
        else
            ps.setNull(42, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        GeometryObject lod1MultiCurve = null;
        if (urbanFunction.getLod1MultiCurve() != null) {
            lod1MultiCurve = geometryConverter.getMultiCurve(urbanFunction.getLod1MultiCurve());
            urbanFunction.getLod1MultiCurve().unsetMultiCurve();
        }

        if (lod1MultiCurve != null)
            ps.setObject(43, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lod1MultiCurve, connection));
        else
            ps.setNull(43, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        GeometryObject lodMinus2MultiPoint = null;
        if (urbanFunction.getLodMinus2MultiPoint() != null) {
            lodMinus2MultiPoint = geometryConverter.getMultiPoint(urbanFunction.getLodMinus2MultiPoint());
            urbanFunction.getLodMinus2MultiPoint().unsetMultiPoint();
        }

        if (lodMinus2MultiPoint != null)
            ps.setObject(44, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lodMinus2MultiPoint, connection));
        else
            ps.setNull(44, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        GeometryObject lodMinus1MultiPoint = null;
        if (urbanFunction.getLodMinus1MultiPoint() != null) {
            lodMinus1MultiPoint = geometryConverter.getMultiPoint(urbanFunction.getLodMinus1MultiPoint());
            urbanFunction.getLodMinus1MultiPoint().unsetMultiPoint();
        }

        if (lodMinus1MultiPoint != null)
            ps.setObject(45, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lodMinus1MultiPoint, connection));
        else
            ps.setNull(45, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        GeometryObject lod0MultiPoint = null;
        if (urbanFunction.getLod0MultiPoint() != null) {
            lod0MultiPoint = geometryConverter.getMultiPoint(urbanFunction.getLod0MultiPoint());
            urbanFunction.getLod0MultiPoint().unsetMultiPoint();
        }

        if (lod0MultiPoint != null)
            ps.setObject(46, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(lod0MultiPoint, connection));
        else
            ps.setNull(46, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        long dataQualityAttributeId = 0;
        if (urbanFunction.getDataQualityAttribute() != null && urbanFunction.getDataQualityAttribute().isSetObject())
            dataQualityAttributeId = dataQualityAttributeImporter.doImport(urbanFunction.getDataQualityAttribute().getObject());

        if (dataQualityAttributeId != 0)
            ps.setLong(47, dataQualityAttributeId);
        else
            ps.setNull(47, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (TargetProperty property : urbanFunction.getTarget()) {
            AbstractCityObject cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                urbanFunctionToCityObjectImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.URBANFUNC_TO_CITYOBJEC), objectId, "urbanfunction_id", href, "cityobject_id");
            }
        }
        for (KeyValuePairAttributeProperty property : urbanFunction.getKeyValuePairAttribute()) {
            if (property.isSetObject()) {
                long keyValuePairAttributeId = keyValuePairAttributeImporter.doImport(property.getObject());
                urbanFunctionToKeyValuePairAttributeImporter.doImport(keyValuePairAttributeId, objectId);
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
