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
import org.citygml4j.model.citygml.building.AbstractOpening;

import java.sql.*;

public class OpeningPropertiesExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final String module;

    private final IfcAttributeExporter ifcAttributeExporter;
    private final IndoorAttributeExporter indoorAttributeExporter;

    public OpeningPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.OPENING);
        module = UrbanObjectModule.v3_0.getNamespaceURI();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("ifcopeningattribute_id", "indooropeningattribute_id"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        ifcAttributeExporter = manager.getExporter(IfcAttributeExporter.class);
        indoorAttributeExporter = manager.getExporter(IndoorAttributeExporter.class);
    }

    public void doExport(org.citygml4j.model.citygml.building.AbstractOpening parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, parentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                long ifcOpeningAttributeId = rs.getLong("ifcopeningattribute_id");
                if (ifcOpeningAttributeId != 0 && projectionFilter.containsProperty("ifcOpeningAttribute", module)) {
                    IfcAttribute ifcOpeningAttribute = ifcAttributeExporter.doExport(ifcOpeningAttributeId);
                    if (ifcOpeningAttribute != null) {
                        IfcOpeningAttributePropertyElement property = new IfcOpeningAttributePropertyElement(new IfcAttributeProperty(ifcOpeningAttribute));
                        parent.addGenericApplicationPropertyOfOpening(property);
                    }
                }

                long indoorOpeningAttributeId = rs.getLong("indooropeningattribute_id");
                if (indoorOpeningAttributeId != 0 && projectionFilter.containsProperty("indoorOpeningAttribute", module)) {
                    IndoorAttribute indoorOpeningAttribute = indoorAttributeExporter.doExport(indoorOpeningAttributeId);
                    if (indoorOpeningAttribute != null) {
                        IndoorOpeningAttributePropertyElement property = new IndoorOpeningAttributePropertyElement(new IndoorAttributeProperty(indoorOpeningAttribute));
                        parent.addGenericApplicationPropertyOfOpening(property);
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
