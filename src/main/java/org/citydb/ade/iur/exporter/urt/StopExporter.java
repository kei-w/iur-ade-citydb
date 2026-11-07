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

package org.citydb.ade.iur.exporter.urt;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.database.content.GMLConverter;
import org.citydb.core.operation.exporter.database.content.SurfaceGeometryExporter;
import org.citydb.core.operation.exporter.util.GeometrySetter;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urt.LevelProperty;
import org.citygml4j.ade.iur.model.urt.Stop;
import org.citygml4j.ade.iur.model.urt.StopProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class StopExporter implements PublicTransitModuleExporter {
    private final CityGMLExportHelper helper;
    private final ExportManager manager;
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psParentStation;
    private final PreparedStatement psLevel;
    private final SurfaceGeometryExporter surfaceGeometryExporter;
    private final GMLConverter gmlConverter;

    public StopExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.STOP);
        publicTransitExporter = manager.getExporter(PublicTransitExporter.class);
        surfaceGeometryExporter = helper.getSurfaceGeometryExporter();
        gmlConverter = helper.getGMLConverter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table publicTransit = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSIT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(publicTransit, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        publicTransitExporter.addProjection(select, publicTransit, "pt_");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table parentStations = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STOP_TO_STOP)));
        Select parentStationSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, parentStations.getColumn("stop_id_2")))
                .addSelection(ComparisonFactory.equalTo(parentStations.getColumn("stop_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psParentStation = connection.prepareStatement(parentStationSelect.toString());
        Table levels = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STOP_TO_LEVEL)));
        Select levelSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, levels.getColumn("level__id")))
                .addSelection(ComparisonFactory.equalTo(levels.getColumn("stop_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psLevel = connection.prepareStatement(levelSelect.toString());
    }

    public void doExport(Stop stop, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(stop, objectId, "pt_", rs);
                doExport(stop, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), stop, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("code", prefix + "code"),
                table.getColumn("code_codespace", prefix + "code_codespace"),
                table.getColumn("ttsname", prefix + "ttsname"),
                table.getColumn("latitude", prefix + "latitude"),
                table.getColumn("longitude", prefix + "longitude"),
                table.getColumn("zoneid", prefix + "zoneid"),
                table.getColumn("zoneid_codespace", prefix + "zoneid_codespace"),
                table.getColumn("url", prefix + "url"),
                table.getColumn("locationtype", prefix + "locationtype"),
                table.getColumn("locationtype_codespace", prefix + "locationtype_codespace"),
                table.getColumn("timezone", prefix + "timezone"),
                table.getColumn("timezone_codespace", prefix + "timezone_codespace"),
                table.getColumn("wheelchairboarding", prefix + "wheelchairboarding"),
                table.getColumn("wheelchairboarding_codespace", prefix + "wheelchairboarding_codespace"),
                table.getColumn("platformcode", prefix + "platformcode"),
                helper.getGeometryColumn(table.getColumn("point"), prefix + "point"));

        return select;
    }

    public void doExport(Stop stop, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String codeValue = rs.getString(prefix + "code");
        if (!rs.wasNull()) {
            Code code = new Code(codeValue);
            code.setCodeSpace(rs.getString(prefix + "code_codespace"));
            stop.setCode(code);
        }

        stop.setTtsName(rs.getString(prefix + "ttsname"));

        double latitude = rs.getDouble(prefix + "latitude");
        if (!rs.wasNull())
            stop.setLatitude(latitude);

        double longitude = rs.getDouble(prefix + "longitude");
        if (!rs.wasNull())
            stop.setLongitude(longitude);

        String zoneId = rs.getString(prefix + "zoneid");
        if (!rs.wasNull()) {
            Code code = new Code(zoneId);
            code.setCodeSpace(rs.getString(prefix + "zoneid_codespace"));
            stop.setZoneId(code);
        }

        stop.setUrl(rs.getString(prefix + "url"));

        String locationType = rs.getString(prefix + "locationtype");
        if (!rs.wasNull()) {
            Code code = new Code(locationType);
            code.setCodeSpace(rs.getString(prefix + "locationtype_codespace"));
            stop.setLocationType(code);
        }

        String timeZone = rs.getString(prefix + "timezone");
        if (!rs.wasNull()) {
            Code code = new Code(timeZone);
            code.setCodeSpace(rs.getString(prefix + "timezone_codespace"));
            stop.setTimeZone(code);
        }

        String wheelchairBoarding = rs.getString(prefix + "wheelchairboarding");
        if (!rs.wasNull()) {
            Code code = new Code(wheelchairBoarding);
            code.setCodeSpace(rs.getString(prefix + "wheelchairboarding_codespace"));
            stop.setWheelchairBoarding(code);
        }

        stop.setPlatformCode(rs.getString(prefix + "platformcode"));

        Object pointObject = rs.getObject(prefix + "point");
        if (!rs.wasNull()) {
            GeometryObject point = helper.getDatabaseAdapter().getGeometryConverter().getPoint(pointObject);
            if (point != null)
                stop.setPoint(gmlConverter.getPointProperty(point));
        }

        psParentStation.setLong(1, objectId);
        try (ResultSet parentStationRs = psParentStation.executeQuery()) {
            while (parentStationRs.next()) {
                StopProperty parentStationProperty = new StopProperty();
                parentStationProperty.setHref("#" + parentStationRs.getString("gmlid"));
                stop.setParentStation(parentStationProperty);
            }
        }

        psLevel.setLong(1, objectId);
        try (ResultSet levelRs = psLevel.executeQuery()) {
            while (levelRs.next()) {
                LevelProperty levelProperty = new LevelProperty();
                levelProperty.setHref("#" + levelRs.getString("gmlid"));
                stop.setLevel(levelProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psParentStation.close();
        psLevel.close();
    }
}
