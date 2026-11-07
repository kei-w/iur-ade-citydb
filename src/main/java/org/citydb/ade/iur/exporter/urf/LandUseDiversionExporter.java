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
import org.citygml4j.ade.iur.model.urf.FarmlandConversionType;
import org.citygml4j.ade.iur.model.urf.FarmlandConversionTypeProperty;
import org.citygml4j.ade.iur.model.urf.ForestConversionType;
import org.citygml4j.ade.iur.model.urf.ForestConversionTypeProperty;
import org.citygml4j.ade.iur.model.urf.LandUseDiversion;

import java.sql.*;

public class LandUseDiversionExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final PreparedStatement ps;
    private final FarmlandConversionTypeExporter farmlandConversionTypeExporter;
    private final ForestConversionTypeExporter forestConversionTypeExporter;

    public LandUseDiversionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LANDUSEDIVERSION_2);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        farmlandConversionTypeExporter = manager.getExporter(FarmlandConversionTypeExporter.class);
        forestConversionTypeExporter = manager.getExporter(ForestConversionTypeExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(LandUseDiversion landUseDiversion, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(landUseDiversion, objectId, "uf_", rs);
                doExport(landUseDiversion, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), landUseDiversion, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("farmlandconversiontype_id", prefix + "farmlandconversiontype_id"),
                table.getColumn("forestconversiontype_id", prefix + "forestconversiontype_id"));

        return select;
    }

    public void doExport(LandUseDiversion landUseDiversion, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        long farmlandConversionTypeId = rs.getLong(prefix + "farmlandconversiontype_id");
        if (farmlandConversionTypeId != 0) {
            FarmlandConversionType farmlandConversionType = new FarmlandConversionType();
            farmlandConversionTypeExporter.doExport(farmlandConversionType, farmlandConversionTypeId);
            landUseDiversion.setFarmlandConversionType(new FarmlandConversionTypeProperty(farmlandConversionType));
        }

        long forestConversionTypeId = rs.getLong(prefix + "forestconversiontype_id");
        if (forestConversionTypeId != 0) {
            ForestConversionType forestConversionType = new ForestConversionType();
            forestConversionTypeExporter.doExport(forestConversionType, forestConversionTypeId);
            landUseDiversion.setForestConversionType(new ForestConversionTypeProperty(forestConversionType));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
