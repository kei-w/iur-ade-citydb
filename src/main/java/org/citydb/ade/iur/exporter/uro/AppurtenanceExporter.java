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
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.Appurtenance;
import org.citygml4j.ade.iur.model.uro.UtilityNode;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class AppurtenanceExporter implements UrbanObjectModuleExporter {
    private final UtilityNetworkElementExporter utilityNetworkElementExporter;
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public AppurtenanceExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.UTILITYNODE);
        utilityNetworkElementExporter = manager.getExporter(UtilityNetworkElementExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table utilityNetworkElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.UTILITYNETWORKELEMENT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(utilityNetworkElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        utilityNetworkElementExporter.addProjection(select, utilityNetworkElement, "une_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(Appurtenance appurtenance, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                utilityNetworkElementExporter.doExport(appurtenance, objectId, "une_", rs);
                doExport(appurtenance, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("previouslink", prefix + "previouslink"),
                table.getColumn("nextlink", prefix + "nextlink"),
                table.getColumn("rotationangle", prefix + "rotationangle"),
                table.getColumn("appurtenancetype", prefix + "appurtenancetype"),
                table.getColumn("appurtenancetype_codespace", prefix + "appurtenancetype_codespace"));

        return select;
    }

    public void doExport(UtilityNode utilityNode, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "previouslink")))
            utilityNode.getPreviousLink().add(splitValue.result(0));

        for (SplitValue splitValue : valueSplitter.split(rs.getString(prefix + "nextlink")))
            utilityNode.getNextLink().add(splitValue.result(0));

        double rotationAngle = rs.getDouble(prefix + "rotationangle");
        if (!rs.wasNull())
            utilityNode.setRotationAngle(rotationAngle);

        if (utilityNode instanceof Appurtenance) {
            Appurtenance appurtenance = (Appurtenance) utilityNode;

            String appurtenanceType = rs.getString(prefix + "appurtenancetype");
            if (!rs.wasNull()) {
                Code code = new Code(appurtenanceType);
                code.setCodeSpace(rs.getString(prefix + "appurtenancetype_codespace"));
                appurtenance.setAppurtenanceType(code);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
