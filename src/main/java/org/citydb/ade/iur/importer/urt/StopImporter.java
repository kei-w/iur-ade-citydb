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

package org.citydb.ade.iur.importer.urt;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.config.geometry.GeometryObject;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.GeometryConverter;
import org.citygml4j.ade.iur.model.urt.Level;
import org.citygml4j.ade.iur.model.urt.LevelProperty;
import org.citygml4j.ade.iur.model.urt.Stop;
import org.citygml4j.ade.iur.model.urt.StopProperty;
import org.citygml4j.model.gml.geometry.AbstractGeometry;

import java.sql.*;

public class StopImporter implements PublicTransitModuleImporter {
    private final Connection connection;
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;
    private final StopToStopImporter stopToStopImporter;
    private final StopToLevelImporter stopToLevelImporter;
    private final GeometryConverter geometryConverter;

    private int batchCounter;

    public StopImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.connection = connection;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.STOP)) + " " +
                "(id, code, code_codespace, ttsname, latitude, longitude, zoneid, zoneid_codespace, url," +
                "locationtype, locationtype_codespace, timezone, timezone_codespace, wheelchairboarding," +
                "wheelchairboarding_codespace, platformcode, point) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
        stopToStopImporter = manager.getImporter(StopToStopImporter.class);
        stopToLevelImporter = manager.getImporter(StopToLevelImporter.class);
        geometryConverter = helper.getGeometryConverter();
    }

    public void doImport(Stop stop, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(stop, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (stop.getCode() != null && stop.getCode().isSetValue()) {
            ps.setString(2, stop.getCode().getValue());
            ps.setString(3, stop.getCode().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, stop.getTtsName());

        if (stop.getLatitude() != null)
            ps.setDouble(5, stop.getLatitude());
        else
            ps.setNull(5, Types.DOUBLE);

        if (stop.getLongitude() != null)
            ps.setDouble(6, stop.getLongitude());
        else
            ps.setNull(6, Types.DOUBLE);

        if (stop.getZoneId() != null && stop.getZoneId().isSetValue()) {
            ps.setString(7, stop.getZoneId().getValue());
            ps.setString(8, stop.getZoneId().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        ps.setString(9, stop.getUrl());

        if (stop.getLocationType() != null && stop.getLocationType().isSetValue()) {
            ps.setString(10, stop.getLocationType().getValue());
            ps.setString(11, stop.getLocationType().getCodeSpace());
        } else {
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.VARCHAR);
        }

        if (stop.getTimeZone() != null && stop.getTimeZone().isSetValue()) {
            ps.setString(12, stop.getTimeZone().getValue());
            ps.setString(13, stop.getTimeZone().getCodeSpace());
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        if (stop.getWheelchairBoarding() != null && stop.getWheelchairBoarding().isSetValue()) {
            ps.setString(14, stop.getWheelchairBoarding().getValue());
            ps.setString(15, stop.getWheelchairBoarding().getCodeSpace());
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        ps.setString(16, stop.getPlatformCode());

        GeometryObject point = null;
        if (stop.getPoint() != null)
            point = geometryConverter.getPoint(stop.getPoint());

        if (point != null)
            ps.setObject(17, helper.getDatabaseAdapter().getGeometryConverter().getDatabaseObject(point, connection));
        else
            ps.setNull(17, helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryType(),
                    helper.getDatabaseAdapter().getGeometryConverter().getNullGeometryTypeName());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (stop.getParentStation() != null) {
            StopProperty property = stop.getParentStation();
            Stop cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                stopToStopImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.STOP_TO_STOP), objectId, "stop_id", href, "stop_id_2");
            }
        }
        if (stop.getLevel() != null) {
            LevelProperty property = stop.getLevel();
            Level cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                stopToLevelImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.STOP_TO_LEVEL), objectId, "stop_id", href, "level__id");
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
