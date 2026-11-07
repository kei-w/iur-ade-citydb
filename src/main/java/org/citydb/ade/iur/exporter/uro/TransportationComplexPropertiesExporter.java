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
import org.citygml4j.model.citygml.transportation.Road;
import org.citygml4j.model.citygml.transportation.TransportationComplex;

import java.sql.*;

public class TransportationComplexPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final TrafficVolumeAttributeExporter trafficVolumeAttributeExporter;
    private final RoadStructureAttributeExporter roadStructureAttributeExporter;
    private final RoadDataQualityAttributeExporter roadDataQualityAttributeExporter;

    public TransportationComplexPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.TRANSPORTATION_COMPLEX);
        module = UrbanObjectModule.v2_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("trafficvolumeattribute_id", "roadstructureattribute_id", "roaddataqualityattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        trafficVolumeAttributeExporter = manager.getExporter(TrafficVolumeAttributeExporter.class);
        roadStructureAttributeExporter = manager.getExporter(RoadStructureAttributeExporter.class);
        roadDataQualityAttributeExporter = manager.getExporter(RoadDataQualityAttributeExporter.class);
    }

    public void doExport(TransportationComplex parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long trafficVolumeAttributeId = rs.getLong("trafficvolumeattribute_id");
                if (trafficVolumeAttributeId != 0 && parent instanceof Road && projectionFilter.containsProperty("trafficVolumeAttribute", module)) {
                    TrafficVolumeAttribute trafficVolumeAttribute = new TrafficVolumeAttribute();
                    trafficVolumeAttributeExporter.doExport(trafficVolumeAttribute, trafficVolumeAttributeId);

                    TrafficVolumeAttributePropertyElement property = new TrafficVolumeAttributePropertyElement(new TrafficVolumeAttributeProperty(trafficVolumeAttribute));
                    ((Road) parent).addGenericApplicationPropertyOfRoad(property);
                }

                long roadStructureAttributeId = rs.getLong("roadstructureattribute_id");
                if (roadStructureAttributeId != 0 && parent instanceof Road && projectionFilter.containsProperty("roadStructureAttribute", module)) {
                    RoadStructureAttribute roadStructureAttribute = new RoadStructureAttribute();
                    roadStructureAttributeExporter.doExport(roadStructureAttribute, roadStructureAttributeId);

                    RoadStructureAttributePropertyElement property = new RoadStructureAttributePropertyElement(new RoadStructureAttributeProperty(roadStructureAttribute));
                    ((Road) parent).addGenericApplicationPropertyOfRoad(property);
                }

                long roadDataQualityAttributeId = rs.getLong("roaddataqualityattribute_id");
                if (roadDataQualityAttributeId != 0 && parent instanceof Road && projectionFilter.containsProperty("roadDataQualityAttribute", module)) {
                    RoadDataQualityAttribute roadDataQualityAttribute = new RoadDataQualityAttribute();
                    roadDataQualityAttributeExporter.doExport(roadDataQualityAttribute, roadDataQualityAttributeId);

                    RoadDataQualityAttributePropertyElement property = new RoadDataQualityAttributePropertyElement(new RoadDataQualityAttributeProperty(roadDataQualityAttribute));
                    ((Road) parent).addGenericApplicationPropertyOfRoad(property);
                }
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
