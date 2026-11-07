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

package org.citydb.ade.iur.exporter.uro;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.DisasterRiskAttribute;
import org.citygml4j.ade.iur.model.uro.FloodingRiskAttribute;
import org.citygml4j.ade.iur.model.uro.LandSlideRiskAttribute;
import org.citygml4j.ade.iur.model.uro.RiverFloodingRiskAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.*;

public class DisasterRiskAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final Map<String, PreparedStatement> psByParent = new HashMap<>();

    public DisasterRiskAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DISASTERRISKATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table floodingRiskAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FLOODINGRISKATTRIBUTE)));
        Table riverFloodingRiskAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.RIVERFLOODINGRISKATTRI)));
        for (String parentColumn : Arrays.asList("building_bldgdisasterrisk_id", "bridge_briddisasterriskat_id", "tunnel_tundisasterriskatt_id", "waterbody_floodingriskatt_id")) {
            Select parentSelect = new Select().addProjection(table.getColumns("id", "objectclass_id", "description", "description_codespace", "areatype", "areatype_codespace"))
                    .addProjection(floodingRiskAttribute.getColumns("rank", "rank_codespace", "rankorg", "rankorg_codespace", "depth", "depth_uom"))
                    .addProjection(riverFloodingRiskAttribute.getColumns("admintype", "admintype_codespace", "scale", "scale_codespace", "duration", "duration_uom"))
                    .addJoin(JoinFactory.left(floodingRiskAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(riverFloodingRiskAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addSelection(ComparisonFactory.equalTo("building_bldgdisasterrisk_id".equals(parentColumn) || "bridge_briddisasterriskat_id".equals(parentColumn) || "tunnel_tundisasterriskatt_id".equals(parentColumn) ? table.getColumn(parentColumn) : floodingRiskAttribute.getColumn(parentColumn), new PlaceHolder<>()))
                    .addOrderBy(new OrderByToken(table.getColumn("id")));
            psByParent.put(parentColumn, connection.prepareStatement(parentSelect.toString()));
        }

    }

    public List<DisasterRiskAttribute> doExport(long parentId, String parentColumn) throws CityGMLExportException, SQLException {
        PreparedStatement ps = psByParent.get(parentColumn);
        ps.setLong(1, parentId);

        List<DisasterRiskAttribute> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DisasterRiskAttribute disasterRiskAttribute = doExport(rs.getLong("id"), rs);
                if (disasterRiskAttribute != null)
                    result.add(disasterRiskAttribute);
            }
        }

        return result;
    }

    private DisasterRiskAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        DisasterRiskAttribute disasterRiskAttribute = objectMapper.createType(objectClassId, DisasterRiskAttribute.class);
        if (disasterRiskAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate disaster risk attribute (id: " + objectId + ").");
            return null;
        }

        String description = rs.getString("description");
        if (!rs.wasNull()) {
            Code code = new Code(description);
            code.setCodeSpace(rs.getString("description_codespace"));
            disasterRiskAttribute.setDescription(code);
        }

        if (disasterRiskAttribute instanceof LandSlideRiskAttribute) {
            LandSlideRiskAttribute landSlideRiskAttribute = (LandSlideRiskAttribute) disasterRiskAttribute;

            String areaType = rs.getString("areatype");
            if (!rs.wasNull()) {
                Code code = new Code(areaType);
                code.setCodeSpace(rs.getString("areatype_codespace"));
                landSlideRiskAttribute.setAreaType(code);
            }
        }

        if (disasterRiskAttribute instanceof FloodingRiskAttribute) {
            FloodingRiskAttribute floodingRiskAttribute = (FloodingRiskAttribute) disasterRiskAttribute;

            String rank = rs.getString("rank");
            if (!rs.wasNull()) {
                Code code = new Code(rank);
                code.setCodeSpace(rs.getString("rank_codespace"));
                floodingRiskAttribute.setRank(code);
            }

            String rankOrg = rs.getString("rankorg");
            if (!rs.wasNull()) {
                Code code = new Code(rankOrg);
                code.setCodeSpace(rs.getString("rankorg_codespace"));
                floodingRiskAttribute.setRankOrg(code);
            }

            double depth = rs.getDouble("depth");
            if (!rs.wasNull()) {
                Length length = new Length(depth);
                length.setUom(rs.getString("depth_uom"));
                floodingRiskAttribute.setDepth(length);
            }
        }

        if (disasterRiskAttribute instanceof RiverFloodingRiskAttribute) {
            RiverFloodingRiskAttribute riverFloodingRiskAttribute = (RiverFloodingRiskAttribute) disasterRiskAttribute;

            String adminType = rs.getString("admintype");
            if (!rs.wasNull()) {
                Code code = new Code(adminType);
                code.setCodeSpace(rs.getString("admintype_codespace"));
                riverFloodingRiskAttribute.setAdminType(code);
            }

            String scale = rs.getString("scale");
            if (!rs.wasNull()) {
                Code code = new Code(scale);
                code.setCodeSpace(rs.getString("scale_codespace"));
                riverFloodingRiskAttribute.setScale(code);
            }

            double duration = rs.getDouble("duration");
            if (!rs.wasNull()) {
                Measure measure = new Measure(duration);
                measure.setUom(rs.getString("duration_uom"));
                riverFloodingRiskAttribute.setDuration(measure);
            }
        }
        return disasterRiskAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        for (PreparedStatement ps : psByParent.values())
            ps.close();
    }
}
