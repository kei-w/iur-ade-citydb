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

package org.citydb.ade.iur.exporter.urf;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.exporter.uro.DataQualityAttributeExporter;
import org.citydb.ade.iur.exporter.uro.KeyValuePairAttributeExporter;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.TargetProperty;
import org.citygml4j.ade.iur.model.urf.UrbanFunction;
import org.citygml4j.ade.iur.model.uro.DataQualityAttribute;
import org.citygml4j.ade.iur.model.uro.DataQualityAttributeProperty;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttribute;
import org.citygml4j.ade.iur.model.uro.KeyValuePairAttributeProperty;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;
import java.time.Year;

public class UrbanFunctionExporter implements UrbanFunctionModuleExporter {
    private final CityGMLExportHelper helper;
    private final ExportManager manager;
    private final PreparedStatement ps;
    private final PreparedStatement psTarget;
    private final PreparedStatement psKeyValuePairAttribute;
    private final KeyValuePairAttributeExporter keyValuePairAttributeExporter;
    private final DataQualityAttributeExporter dataQualityAttributeExporter;
    private final AttributeValueSplitter valueSplitter;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public UrbanFunctionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION);
        keyValuePairAttributeExporter = manager.getExporter(KeyValuePairAttributeExporter.class);
        dataQualityAttributeExporter = manager.getExporter(DataQualityAttributeExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select()
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table targets = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNC_TO_CITYOBJEC)));
        Select targetSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, targets.getColumn("cityobject_id")))
                .addSelection(ComparisonFactory.equalTo(targets.getColumn("urbanfunction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psTarget = connection.prepareStatement(targetSelect.toString());
        Table keyValuePairAttributes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNC_TO_KEYVALUEP)));
        Select keyValuePairAttributeSelect = new Select().addProjection(keyValuePairAttributes.getColumn("keyvaluepairattribute_id"))
                .addSelection(ComparisonFactory.equalTo(keyValuePairAttributes.getColumn("urbanfunction_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(keyValuePairAttributes.getColumn("keyvaluepairattribute_id")));
        psKeyValuePairAttribute = connection.prepareStatement(keyValuePairAttributeSelect.toString());
    }

    public void doExport(UrbanFunction urbanFunction, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                doExport(urbanFunction, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), urbanFunction, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("class", prefix + "class"),
                table.getColumn("class_codespace", prefix + "class_codespace"),
                table.getColumn("function", prefix + "function"),
                table.getColumn("function_codespace", prefix + "function_codespace"),
                table.getColumn("usage", prefix + "usage"),
                table.getColumn("usage_codespace", prefix + "usage_codespace"),
                table.getColumn("validfrom", prefix + "validfrom"),
                table.getColumn("validfromtype", prefix + "validfromtype"),
                table.getColumn("validfromtype_codespace", prefix + "validfromtype_codespace"),
                table.getColumn("enactmentfiscalyear", prefix + "enactmentfiscalyear"),
                table.getColumn("validto", prefix + "validto"),
                table.getColumn("validtotype", prefix + "validtotype"),
                table.getColumn("validtotype_codespace", prefix + "validtotype_codespace"),
                table.getColumn("expirationfiscalyear", prefix + "expirationfiscalyear"),
                table.getColumn("legalgrounds", prefix + "legalgrounds"),
                table.getColumn("custodian", prefix + "custodian"),
                table.getColumn("notificationnumber", prefix + "notificationnumber"),
                table.getColumn("finalnotificationdate", prefix + "finalnotificationdate"),
                table.getColumn("finalnotificationnumber", prefix + "finalnotificationnumber"),
                table.getColumn("urbanplantype", prefix + "urbanplantype"),
                table.getColumn("urbanplantype_codespace", prefix + "urbanplantype_codespace"),
                table.getColumn("areaclassificationtype", prefix + "areaclassificationtype"),
                table.getColumn("areaclassification_codespace", prefix + "areaclassification_codespace"),
                table.getColumn("nominalarea", prefix + "nominalarea"),
                table.getColumn("nominalarea_uom", prefix + "nominalarea_uom"),
                table.getColumn("prefecture", prefix + "prefecture"),
                table.getColumn("prefecture_codespace", prefix + "prefecture_codespace"),
                table.getColumn("city", prefix + "city"),
                table.getColumn("city_codespace", prefix + "city_codespace"),
                table.getColumn("reference", prefix + "reference"),
                table.getColumn("reason", prefix + "reason"),
                table.getColumn("note", prefix + "note"),
                table.getColumn("surveyyear", prefix + "surveyyear"),
                table.getColumn("lod_2multisurface_id", prefix + "lod_2multisurface_id"),
                table.getColumn("lod_1multisurface_id", prefix + "lod_1multisurface_id"),
                table.getColumn("lod0multisurface_id", prefix + "lod0multisurface_id"),
                table.getColumn("lod1multisurface_id", prefix + "lod1multisurface_id"),
                table.getColumn("dataqualityattribute_id", prefix + "dataqualityattribute_id"),
                helper.getGeometryColumn(table.getColumn("lod_2multicurve"), prefix + "lod_2multicurve"),
                helper.getGeometryColumn(table.getColumn("lod_1multicurve"), prefix + "lod_1multicurve"),
                helper.getGeometryColumn(table.getColumn("lod0multicurve"), prefix + "lod0multicurve"),
                helper.getGeometryColumn(table.getColumn("lod1multicurve"), prefix + "lod1multicurve"),
                helper.getGeometryColumn(table.getColumn("lod_2multipoint"), prefix + "lod_2multipoint"),
                helper.getGeometryColumn(table.getColumn("lod_1multipoint"), prefix + "lod_1multipoint"),
                helper.getGeometryColumn(table.getColumn("lod0multipoint"), prefix + "lod0multipoint"));

        return select;
    }

    public void doExport(UrbanFunction urbanFunction, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String classifier = rs.getString(prefix + "class");
        if (!rs.wasNull()) {
            Code code = new Code(classifier);
            code.setCodeSpace(rs.getString(prefix + "class_codespace"));
            urbanFunction.setClassifier(code);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "function"), rs.getString(prefix + "function_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            urbanFunction.getFunction().add(code);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "usage"), rs.getString(prefix + "usage_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            urbanFunction.getUsage().add(code);
        }

        Date validFrom = rs.getDate(prefix + "validfrom");
        if (!rs.wasNull())
            urbanFunction.setValidFrom(validFrom.toLocalDate());

        String validFromType = rs.getString(prefix + "validfromtype");
        if (!rs.wasNull()) {
            Code code = new Code(validFromType);
            code.setCodeSpace(rs.getString(prefix + "validfromtype_codespace"));
            urbanFunction.setValidFromType(code);
        }

        Date enactmentFiscalYear = rs.getDate(prefix + "enactmentfiscalyear");
        if (!rs.wasNull())
            urbanFunction.setEnactmentFiscalYear(Year.of(enactmentFiscalYear.toLocalDate().getYear()));

        Date validTo = rs.getDate(prefix + "validto");
        if (!rs.wasNull())
            urbanFunction.setValidTo(validTo.toLocalDate());

        String validToType = rs.getString(prefix + "validtotype");
        if (!rs.wasNull()) {
            Code code = new Code(validToType);
            code.setCodeSpace(rs.getString(prefix + "validtotype_codespace"));
            urbanFunction.setValidToType(code);
        }

        Date expirationFiscalYear = rs.getDate(prefix + "expirationfiscalyear");
        if (!rs.wasNull())
            urbanFunction.setExpirationFiscalYear(Year.of(expirationFiscalYear.toLocalDate().getYear()));

        urbanFunction.setLegalGrounds(rs.getString(prefix + "legalgrounds"));

        urbanFunction.setCustodian(rs.getString(prefix + "custodian"));

        urbanFunction.setNotificationNumber(rs.getString(prefix + "notificationnumber"));

        Date finalNotificationDate = rs.getDate(prefix + "finalnotificationdate");
        if (!rs.wasNull())
            urbanFunction.setFinalNotificationDate(finalNotificationDate.toLocalDate());

        urbanFunction.setFinalNotificationNumber(rs.getString(prefix + "finalnotificationnumber"));

        String urbanPlanType = rs.getString(prefix + "urbanplantype");
        if (!rs.wasNull()) {
            Code code = new Code(urbanPlanType);
            code.setCodeSpace(rs.getString(prefix + "urbanplantype_codespace"));
            urbanFunction.setUrbanPlanType(code);
        }

        String areaClassificationType = rs.getString(prefix + "areaclassificationtype");
        if (!rs.wasNull()) {
            Code code = new Code(areaClassificationType);
            code.setCodeSpace(rs.getString(prefix + "areaclassification_codespace"));
            urbanFunction.setAreaClassificationType(code);
        }

        double nominalArea = rs.getDouble(prefix + "nominalarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(nominalArea);
            measure.setUom(rs.getString(prefix + "nominalarea_uom"));
            urbanFunction.setNominalArea(measure);
        }

        String prefecture = rs.getString(prefix + "prefecture");
        if (!rs.wasNull()) {
            Code code = new Code(prefecture);
            code.setCodeSpace(rs.getString(prefix + "prefecture_codespace"));
            urbanFunction.setPrefecture(code);
        }

        String city = rs.getString(prefix + "city");
        if (!rs.wasNull()) {
            Code code = new Code(city);
            code.setCodeSpace(rs.getString(prefix + "city_codespace"));
            urbanFunction.setCity(code);
        }

        urbanFunction.setReference(rs.getString(prefix + "reference"));

        String reason = rs.getString(prefix + "reason");
        if (!rs.wasNull())
            urbanFunction.setReason(new StringOrRef(reason));

        String note = rs.getString(prefix + "note");
        if (!rs.wasNull())
            urbanFunction.setNote(new StringOrRef(note));

        Date surveyYear = rs.getDate(prefix + "surveyyear");
        if (!rs.wasNull())
            urbanFunction.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));

        long lodMinus2MultiSurfaceId = rs.getLong(prefix + "lod_2multisurface_id");
        if (!rs.wasNull())
            surfaceGeometryExporter.addBatch(lodMinus2MultiSurfaceId, urbanFunction::setLodMinus2MultiSurface);

        long lodMinus1MultiSurfaceId = rs.getLong(prefix + "lod_1multisurface_id");
        if (!rs.wasNull())
            surfaceGeometryExporter.addBatch(lodMinus1MultiSurfaceId, urbanFunction::setLodMinus1MultiSurface);

        long lod0MultiSurfaceId = rs.getLong(prefix + "lod0multisurface_id");
        if (!rs.wasNull())
            surfaceGeometryExporter.addBatch(lod0MultiSurfaceId, urbanFunction::setLod0MultiSurface);

        long lod1MultiSurfaceId = rs.getLong(prefix + "lod1multisurface_id");
        if (!rs.wasNull())
            surfaceGeometryExporter.addBatch(lod1MultiSurfaceId, urbanFunction::setLod1MultiSurface);

        Object lodMinus2MultiCurveObject = rs.getObject(prefix + "lod_2multicurve");
        if (!rs.wasNull()) {
            GeometryObject lodMinus2MultiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(lodMinus2MultiCurveObject);
            if (lodMinus2MultiCurve != null)
                urbanFunction.setLodMinus2MultiCurve(gmlConverter.getMultiCurveProperty(lodMinus2MultiCurve));
        }

        Object lodMinus1MultiCurveObject = rs.getObject(prefix + "lod_1multicurve");
        if (!rs.wasNull()) {
            GeometryObject lodMinus1MultiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(lodMinus1MultiCurveObject);
            if (lodMinus1MultiCurve != null)
                urbanFunction.setLodMinus1MultiCurve(gmlConverter.getMultiCurveProperty(lodMinus1MultiCurve));
        }

        Object lod0MultiCurveObject = rs.getObject(prefix + "lod0multicurve");
        if (!rs.wasNull()) {
            GeometryObject lod0MultiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(lod0MultiCurveObject);
            if (lod0MultiCurve != null)
                urbanFunction.setLod0MultiCurve(gmlConverter.getMultiCurveProperty(lod0MultiCurve));
        }

        Object lod1MultiCurveObject = rs.getObject(prefix + "lod1multicurve");
        if (!rs.wasNull()) {
            GeometryObject lod1MultiCurve = helper.getDatabaseAdapter().getGeometryConverter().getMultiCurve(lod1MultiCurveObject);
            if (lod1MultiCurve != null)
                urbanFunction.setLod1MultiCurve(gmlConverter.getMultiCurveProperty(lod1MultiCurve));
        }

        Object lodMinus2MultiPointObject = rs.getObject(prefix + "lod_2multipoint");
        if (!rs.wasNull()) {
            GeometryObject lodMinus2MultiPoint = helper.getDatabaseAdapter().getGeometryConverter().getMultiPoint(lodMinus2MultiPointObject);
            if (lodMinus2MultiPoint != null)
                urbanFunction.setLodMinus2MultiPoint(gmlConverter.getMultiPointProperty(lodMinus2MultiPoint));
        }

        Object lodMinus1MultiPointObject = rs.getObject(prefix + "lod_1multipoint");
        if (!rs.wasNull()) {
            GeometryObject lodMinus1MultiPoint = helper.getDatabaseAdapter().getGeometryConverter().getMultiPoint(lodMinus1MultiPointObject);
            if (lodMinus1MultiPoint != null)
                urbanFunction.setLodMinus1MultiPoint(gmlConverter.getMultiPointProperty(lodMinus1MultiPoint));
        }

        Object lod0MultiPointObject = rs.getObject(prefix + "lod0multipoint");
        if (!rs.wasNull()) {
            GeometryObject lod0MultiPoint = helper.getDatabaseAdapter().getGeometryConverter().getMultiPoint(lod0MultiPointObject);
            if (lod0MultiPoint != null)
                urbanFunction.setLod0MultiPoint(gmlConverter.getMultiPointProperty(lod0MultiPoint));
        }

        psTarget.setLong(1, objectId);
        try (ResultSet targetRs = psTarget.executeQuery()) {
            while (targetRs.next()) {
                TargetProperty targetProperty = new TargetProperty();
                targetProperty.setHref("#" + targetRs.getString("gmlid"));
                urbanFunction.getTarget().add(targetProperty);
            }
        }

        psKeyValuePairAttribute.setLong(1, objectId);
        try (ResultSet keyValuePairAttributeRs = psKeyValuePairAttribute.executeQuery()) {
            while (keyValuePairAttributeRs.next()) {
                KeyValuePairAttribute keyValuePairAttribute = new KeyValuePairAttribute();
                keyValuePairAttributeExporter.doExport(keyValuePairAttribute, keyValuePairAttributeRs.getLong("keyvaluepairattribute_id"));
                urbanFunction.getKeyValuePairAttribute().add(new KeyValuePairAttributeProperty(keyValuePairAttribute));
            }
        }

        long dataQualityAttributeId = rs.getLong(prefix + "dataqualityattribute_id");
        if (dataQualityAttributeId != 0) {
            DataQualityAttribute dataQualityAttribute = new DataQualityAttribute();
            dataQualityAttributeExporter.doExport(dataQualityAttribute, dataQualityAttributeId);
            urbanFunction.setDataQualityAttribute(new DataQualityAttributeProperty(dataQualityAttribute));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psTarget.close();
        psKeyValuePairAttribute.close();
    }
}
