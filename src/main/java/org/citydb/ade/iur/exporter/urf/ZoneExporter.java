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
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.BoundaryProperty;
import org.citygml4j.ade.iur.model.urf.Zone;

import java.sql.*;

public class ZoneExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psBoundary;
    private final AttributeValueSplitter valueSplitter;

    public ZoneExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.ZONE);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        ps = connection.prepareStatement(select.toString());

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table boundarys = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE_TO_BOUNDARY)));
        Select boundarySelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, boundarys.getColumn("boundary_id")))
                .addSelection(ComparisonFactory.equalTo(boundarys.getColumn("zone_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psBoundary = connection.prepareStatement(boundarySelect.toString());
    }

    public void doExport(Zone zone, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(zone, objectId, "uf_", rs);
                doExport(zone, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("location", prefix + "location"));

        return select;
    }

    public void doExport(Zone zone, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        psBoundary.setLong(1, objectId);
        try (ResultSet boundaryRs = psBoundary.executeQuery()) {
            while (boundaryRs.next()) {
                BoundaryProperty boundaryProperty = new BoundaryProperty();
                boundaryProperty.setHref("#" + boundaryRs.getString("gmlid"));
                zone.getBoundary().add(boundaryProperty);
            }
        }

        zone.setLocationDescription(rs.getString(prefix + "location"));
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psBoundary.close();
    }
}
