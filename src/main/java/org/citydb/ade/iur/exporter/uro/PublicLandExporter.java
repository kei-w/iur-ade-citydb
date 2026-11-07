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
import org.citydb.core.database.schema.mapping.AbstractType;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.PublicLand;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class PublicLandExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public PublicLandExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.PUBLICLAND);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select()
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(PublicLand publicLand, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                doExport(publicLand, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("id_1", prefix + "id_1"),
                table.getColumn("ownername", prefix + "ownername"),
                table.getColumn("ownertype", prefix + "ownertype"),
                table.getColumn("ownertype_codespace", prefix + "ownertype_codespace"),
                table.getColumn("landcategory", prefix + "landcategory"),
                table.getColumn("area", prefix + "area"),
                table.getColumn("area_uom", prefix + "area_uom"),
                table.getColumn("status", prefix + "status"),
                table.getColumn("note", prefix + "note"));

        return select;
    }

    public void doExport(PublicLand publicLand, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        publicLand.setIdentifier(rs.getString(prefix + "id_1"));

        publicLand.setOwnerName(rs.getString(prefix + "ownername"));

        String ownerType = rs.getString(prefix + "ownertype");
        if (!rs.wasNull()) {
            Code code = new Code(ownerType);
            code.setCodeSpace(rs.getString(prefix + "ownertype_codespace"));
            publicLand.setOwnerType(code);
        }

        publicLand.setLandCategory(rs.getString(prefix + "landcategory"));

        double area = rs.getDouble(prefix + "area");
        if (!rs.wasNull()) {
            Measure measure = new Measure(area);
            measure.setUom(rs.getString(prefix + "area_uom"));
            publicLand.setArea(measure);
        }

        publicLand.setStatus(rs.getString(prefix + "status"));

        publicLand.setNote(rs.getString(prefix + "note"));
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
