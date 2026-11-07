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
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.IfcAttribute;
import org.citygml4j.ade.iur.model.uro.IfcCoordinateReferenceSystem;
import org.citygml4j.ade.iur.model.uro.IfcCoordinateReferenceSystemProperty;
import org.citygml4j.ade.iur.model.uro.IfcCoordinateReferenceSystemSelect;
import org.citygml4j.ade.iur.model.uro.IfcGeometricRepresentationContext;
import org.citygml4j.ade.iur.model.uro.IfcGeometricRepresentationContextProperty;

import java.sql.*;

public class IfcCoordinateReferenceSystemSelectExporter implements UrbanObjectModuleExporter {
    private final ExportManager manager;
    private final PreparedStatement ps;
    private IfcAttributeExporter ifcAttributeExporter;
    private final IfcGeometricRepresentationContextExporter ifcGeometricRepresentationContextExporter;

    public IfcCoordinateReferenceSystemSelectExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CRS);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("crs_id", "context_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        this.manager = manager;
        ifcGeometricRepresentationContextExporter = manager.getExporter(IfcGeometricRepresentationContextExporter.class);
    }

    public void doExport(IfcCoordinateReferenceSystemSelect ifcCoordinateReferenceSystemSelect, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long crsId = rs.getLong("crs_id");
                if (crsId != 0) {
                    IfcAttribute crs = ifcAttributeExporter().doExport(crsId);
                    if (crs instanceof IfcCoordinateReferenceSystem)
                        ifcCoordinateReferenceSystemSelect.setCrs(new IfcCoordinateReferenceSystemProperty((IfcCoordinateReferenceSystem) crs));
                }

                long contextId = rs.getLong("context_id");
                if (contextId != 0) {
                    IfcGeometricRepresentationContext context = new IfcGeometricRepresentationContext();
                    ifcGeometricRepresentationContextExporter.doExport(context, contextId);
                    ifcCoordinateReferenceSystemSelect.setContext(new IfcGeometricRepresentationContextProperty(context));
                }
            }
        }
    }

    private IfcAttributeExporter ifcAttributeExporter() throws CityGMLExportException, SQLException {
        if (ifcAttributeExporter == null)
            ifcAttributeExporter = manager.getExporter(IfcAttributeExporter.class);

        return ifcAttributeExporter;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
