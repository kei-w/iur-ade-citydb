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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.IfcAxis2Placement3D;
import org.citygml4j.ade.iur.model.uro.IfcAxis2Placement3DProperty;
import org.citygml4j.ade.iur.model.uro.IfcGeometricRepresentationContext;

import java.sql.*;

public class IfcGeometricRepresentationContextExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final IfcAxis2Placement3DExporter ifcAxis2Placement3DExporter;
    private final AttributeValueSplitter valueSplitter;

    public IfcGeometricRepresentationContextExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.IFCGEOMETRICREPRESENTA);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("contextidentifier", "contexttype", "coordinatespacedimension", "precision",
                        "worldcoordinatesystem_id", "truenorth"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        ifcAxis2Placement3DExporter = manager.getExporter(IfcAxis2Placement3DExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(IfcGeometricRepresentationContext ifcGeometricRepresentationContext, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                ifcGeometricRepresentationContext.setContextIdentifier(rs.getString("contextidentifier"));

                ifcGeometricRepresentationContext.setContextType(rs.getString("contexttype"));

                int coordinateSpaceDimension = rs.getInt("coordinatespacedimension");
                if (!rs.wasNull())
                    ifcGeometricRepresentationContext.setCoordinateSpaceDimension(coordinateSpaceDimension);

                double precision = rs.getDouble("precision");
                if (!rs.wasNull())
                    ifcGeometricRepresentationContext.setPrecision(precision);

                long worldCoordinateSystemId = rs.getLong("worldcoordinatesystem_id");
                if (worldCoordinateSystemId != 0) {
                    IfcAxis2Placement3D worldCoordinateSystem = new IfcAxis2Placement3D();
                    ifcAxis2Placement3DExporter.doExport(worldCoordinateSystem, worldCoordinateSystemId);
                    ifcGeometricRepresentationContext.setWorldCoordinateSystem(new IfcAxis2Placement3DProperty(worldCoordinateSystem));
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("truenorth")))
                    ifcGeometricRepresentationContext.getTrueNorth().add(splitValue.asDouble(0));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
