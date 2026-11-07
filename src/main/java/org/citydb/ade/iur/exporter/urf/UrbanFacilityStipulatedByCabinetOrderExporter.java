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
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.UrbanFacilityStipulatedByCabinetOrder;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class UrbanFacilityStipulatedByCabinetOrderExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final UrbanFacilityExporter urbanFacilityExporter;
    private final PreparedStatement ps;

    public UrbanFacilityStipulatedByCabinetOrderExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.URBANFACILITYSTIPULATE);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        urbanFacilityExporter = manager.getExporter(UrbanFacilityExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Table zone = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE)));
        Table urbanFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFACILITY)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(zone, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(urbanFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        zoneExporter.addProjection(select, zone, "z_");
        urbanFacilityExporter.addProjection(select, urbanFacility, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(UrbanFacilityStipulatedByCabinetOrder urbanFacilityStipulatedByCabinetOrder, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(urbanFacilityStipulatedByCabinetOrder, objectId, "uf_", rs);
                zoneExporter.doExport(urbanFacilityStipulatedByCabinetOrder, objectId, "z_", rs);
                urbanFacilityExporter.doExport(urbanFacilityStipulatedByCabinetOrder, objectId, "uf_", rs);
                doExport(urbanFacilityStipulatedByCabinetOrder, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), urbanFacilityStipulatedByCabinetOrder, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("length", prefix + "length"),
                table.getColumn("length_uom", prefix + "length_uom"),
                table.getColumn("width", prefix + "width"),
                table.getColumn("width_uom", prefix + "width_uom"));

        return select;
    }

    public void doExport(UrbanFacilityStipulatedByCabinetOrder urbanFacilityStipulatedByCabinetOrder, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        double lengthValue = rs.getDouble(prefix + "length");
        if (!rs.wasNull()) {
            Length length = new Length(lengthValue);
            length.setUom(rs.getString(prefix + "length_uom"));
            urbanFacilityStipulatedByCabinetOrder.setLength(length);
        }

        double width = rs.getDouble(prefix + "width");
        if (!rs.wasNull()) {
            Length length = new Length(width);
            length.setUom(rs.getString(prefix + "width_uom"));
            urbanFacilityStipulatedByCabinetOrder.setWidth(length);
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
