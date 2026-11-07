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

package org.citydb.ade.iur.importer.urg;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urg.StatisticalGrid;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;

import java.sql.*;
import java.time.LocalDate;

public class StatisticalGridImporter implements StatisticalGridModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public StatisticalGridImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.STATISTICALGRID)) + " " +
                "(id, objectclass_id, class, class_codespace, urbanplantype, urbanplantype_codespace," +
                "areaclassificationtype, areaclassification_codespace, prefecture, prefecture_codespace, city," +
                "city_codespace, surveyyear, referencedate, lod_1multisurface_id, lod_2multisurface_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(StatisticalGrid statisticalGrid, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        if (statisticalGrid.getClassifier() != null && statisticalGrid.getClassifier().isSetValue()) {
            ps.setString(3, statisticalGrid.getClassifier().getValue());
            ps.setString(4, statisticalGrid.getClassifier().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (statisticalGrid.getUrbanPlanType() != null && statisticalGrid.getUrbanPlanType().isSetValue()) {
            ps.setString(5, statisticalGrid.getUrbanPlanType().getValue());
            ps.setString(6, statisticalGrid.getUrbanPlanType().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (statisticalGrid.getAreaClassificationType() != null && statisticalGrid.getAreaClassificationType().isSetValue()) {
            ps.setString(7, statisticalGrid.getAreaClassificationType().getValue());
            ps.setString(8, statisticalGrid.getAreaClassificationType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (statisticalGrid.getPrefecture() != null && statisticalGrid.getPrefecture().isSetValue()) {
            ps.setString(9, statisticalGrid.getPrefecture().getValue());
            ps.setString(10, statisticalGrid.getPrefecture().getCodeSpace());
        } else {
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.VARCHAR);
        }

        if (statisticalGrid.getCity() != null && statisticalGrid.getCity().isSetValue()) {
            ps.setString(11, statisticalGrid.getCity().getValue());
            ps.setString(12, statisticalGrid.getCity().getCodeSpace());
        } else {
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.VARCHAR);
        }

        if (statisticalGrid.getSurveyYear() != null)
            ps.setDate(13, Date.valueOf(LocalDate.of(statisticalGrid.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(13, Types.DATE);

        if (statisticalGrid.getReferenceDate() != null)
            ps.setDate(14, Date.valueOf(statisticalGrid.getReferenceDate()));
        else
            ps.setNull(14, Types.DATE);

        long lodMinus1MultiSurfaceId = 0;
        if (statisticalGrid.getLodMinus1MultiSurface() != null) {
            MultiSurfaceProperty property = statisticalGrid.getLodMinus1MultiSurface();
            if (property.isSetMultiSurface()) {
                lodMinus1MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.STATISTICALGRID), objectId, "lod_1multisurface_id");
            }
        }

        if (lodMinus1MultiSurfaceId != 0)
            ps.setLong(15, lodMinus1MultiSurfaceId);
        else
            ps.setNull(15, Types.NULL);

        long lodMinus2MultiSurfaceId = 0;
        if (statisticalGrid.getLodMinus2MultiSurface() != null) {
            MultiSurfaceProperty property = statisticalGrid.getLodMinus2MultiSurface();
            if (property.isSetMultiSurface()) {
                lodMinus2MultiSurfaceId = helper.importSurfaceGeometry(property.getMultiSurface(), objectId);
                property.unsetMultiSurface();
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateSurfaceGeometryXlink(href, schemaMapper.getTableName(ADETable.STATISTICALGRID), objectId, "lod_2multisurface_id");
            }
        }

        if (lodMinus2MultiSurfaceId != 0)
            ps.setLong(16, lodMinus2MultiSurfaceId);
        else
            ps.setNull(16, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);
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
