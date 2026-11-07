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
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingDisasterRiskAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final PreparedStatement ps;

    public BuildingDisasterRiskAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();

        String tableName = manager.getSchemaMapper().getTableName(ADETable.BUILDINGDISASTERRISKAT);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table flooding = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGFLOODINGRISKAT)));
        Table riverFlooding = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.BUILDINGRIVERFLOODINGR)));
        Select select = new Select().addProjection(table.getColumns("id", "objectclass_id", "description", "description_codespace",
                        "areatype", "areatype_codespace"))
                .addProjection(flooding.getColumns("depth", "depth_uom", "rank", "rank_codespace", "rankorg", "rankorg_codespace"))
                .addProjection(riverFlooding.getColumns("admintype", "admintype_codespace", "duration", "duration_uom", "scale", "scale_codespace"))
                .addJoin(JoinFactory.left(flooding, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(riverFlooding, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("building_buildingdisaster_id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(AbstractBuilding parent, long parentId) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long objectId = rs.getLong("id");
                int objectClassId = rs.getInt("objectclass_id");
                if (rs.wasNull())
                    continue;

                BuildingDisasterRiskAttribute buildingDisasterRiskAttribute = objectMapper.createType(objectClassId, BuildingDisasterRiskAttribute.class);
                if (buildingDisasterRiskAttribute == null) {
                    helper.logOrThrowErrorMessage("Failed to instantiate building disaster risk attribute (id: " + objectId + ").");
                    continue;
                }

                String description = rs.getString("description");
                if (!rs.wasNull()) {
                    Code code = new Code(description);
                    code.setCodeSpace(rs.getString("description_codespace"));
                    buildingDisasterRiskAttribute.setDescription(code);
                }

                if (buildingDisasterRiskAttribute instanceof BuildingLandSlideRiskAttribute) {
                    String areaType = rs.getString("areatype");
                    if (!rs.wasNull()) {
                        Code code = new Code(areaType);
                        code.setCodeSpace(rs.getString("areatype_codespace"));
                        ((BuildingLandSlideRiskAttribute) buildingDisasterRiskAttribute).setAreaType(code);
                    }
                }

                if (buildingDisasterRiskAttribute instanceof BuildingFloodingRiskAttribute) {
                    BuildingFloodingRiskAttribute buildingFloodingRiskAttribute = (BuildingFloodingRiskAttribute) buildingDisasterRiskAttribute;

                    double depth = rs.getDouble("depth");
                    if (!rs.wasNull()) {
                        Length length = new Length(depth);
                        length.setUom(rs.getString("depth_uom"));
                        buildingFloodingRiskAttribute.setDepth(length);
                    }

                    String rank = rs.getString("rank");
                    if (!rs.wasNull()) {
                        Code code = new Code(rank);
                        code.setCodeSpace(rs.getString("rank_codespace"));
                        buildingFloodingRiskAttribute.setRank(code);
                    }

                    String rankOrg = rs.getString("rankorg");
                    if (!rs.wasNull()) {
                        Code code = new Code(rankOrg);
                        code.setCodeSpace(rs.getString("rankorg_codespace"));
                        buildingFloodingRiskAttribute.setRankOrg(code);
                    }
                }

                if (buildingDisasterRiskAttribute instanceof BuildingRiverFloodingRiskAttribute) {
                    BuildingRiverFloodingRiskAttribute buildingRiverFloodingRiskAttribute = (BuildingRiverFloodingRiskAttribute) buildingDisasterRiskAttribute;

                    String adminType = rs.getString("admintype");
                    if (!rs.wasNull()) {
                        Code code = new Code(adminType);
                        code.setCodeSpace(rs.getString("admintype_codespace"));
                        buildingRiverFloodingRiskAttribute.setAdminType(code);
                    }

                    double duration = rs.getDouble("duration");
                    if (!rs.wasNull()) {
                        Measure measure = new Measure(duration);
                        measure.setUom(rs.getString("duration_uom"));
                        buildingRiverFloodingRiskAttribute.setDuration(measure);
                    }

                    String scale = rs.getString("scale");
                    if (!rs.wasNull()) {
                        Code code = new Code(scale);
                        code.setCodeSpace(rs.getString("scale_codespace"));
                        buildingRiverFloodingRiskAttribute.setScale(code);
                    }
                }

                BuildingDisasterRiskAttributePropertyElement property = new BuildingDisasterRiskAttributePropertyElement(new BuildingDisasterRiskAttributeProperty(buildingDisasterRiskAttribute));
                parent.addGenericApplicationPropertyOfAbstractBuilding(property);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
