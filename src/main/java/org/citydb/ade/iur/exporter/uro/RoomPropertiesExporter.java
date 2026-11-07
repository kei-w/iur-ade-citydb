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
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.module.UrbanObjectModule;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.building.Room;

import java.sql.*;

public class RoomPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final IfcAttributeExporter ifcAttributeExporter;
    private final RoomDataQualityAttributeExporter roomDataQualityAttributeExporter;
    private final IndoorAttributeExporter indoorAttributeExporter;

    public RoomPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.ROOM);
        module = UrbanObjectModule.v3_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("ifcroomattribute_id", "roomdataqualityattribute_id", "indoorroomattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        ifcAttributeExporter = manager.getExporter(IfcAttributeExporter.class);
        roomDataQualityAttributeExporter = manager.getExporter(RoomDataQualityAttributeExporter.class);
        indoorAttributeExporter = manager.getExporter(IndoorAttributeExporter.class);
    }

    public void doExport(Room parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long ifcRoomAttributeId = rs.getLong("ifcroomattribute_id");
                if (ifcRoomAttributeId != 0 && projectionFilter.containsProperty("ifcRoomAttribute", module)) {
                    IfcAttribute ifcRoomAttribute = ifcAttributeExporter.doExport(ifcRoomAttributeId);
                    if (ifcRoomAttribute != null) {
                        IfcRoomAttributePropertyElement property = new IfcRoomAttributePropertyElement(new IfcAttributeProperty(ifcRoomAttribute));
                        parent.addGenericApplicationPropertyOfRoom(property);
                    }
                }

                long roomDataQualityAttributeId = rs.getLong("roomdataqualityattribute_id");
                if (roomDataQualityAttributeId != 0 && projectionFilter.containsProperty("roomDataQualityAttribute", module)) {
                    RoomDataQualityAttribute roomDataQualityAttribute = new RoomDataQualityAttribute();
                    roomDataQualityAttributeExporter.doExport(roomDataQualityAttribute, roomDataQualityAttributeId);

                    RoomDataQualityAttributePropertyElement property = new RoomDataQualityAttributePropertyElement(new RoomDataQualityAttributeProperty(roomDataQualityAttribute));
                    parent.addGenericApplicationPropertyOfRoom(property);
                }

                long indoorRoomAttributeId = rs.getLong("indoorroomattribute_id");
                if (indoorRoomAttributeId != 0 && projectionFilter.containsProperty("indoorRoomAttribute", module)) {
                    IndoorAttribute indoorRoomAttribute = indoorAttributeExporter.doExport(indoorRoomAttributeId);
                    if (indoorRoomAttribute != null) {
                        IndoorRoomAttributePropertyElement property = new IndoorRoomAttributePropertyElement(new IndoorAttributeProperty(indoorRoomAttribute));
                        parent.addGenericApplicationPropertyOfRoom(property);
                    }
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
