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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.query.filter.projection.ProjectionFilter;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urf.CollectiveFacilitiesForTsunamiDisasterPrevention;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class CollectiveFacilitiesForTsunamiDisasterPreventionExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final UrbanFacilityExporter urbanFacilityExporter;
    private final PreparedStatement ps;

    public CollectiveFacilitiesForTsunamiDisasterPreventionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.COLLECTIVEFACILITIESFO);
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

    public void doExport(CollectiveFacilitiesForTsunamiDisasterPrevention collectiveFacilitiesForTsunamiDisasterPrevention, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(collectiveFacilitiesForTsunamiDisasterPrevention, objectId, "uf_", rs);
                zoneExporter.doExport(collectiveFacilitiesForTsunamiDisasterPrevention, objectId, "z_", rs);
                urbanFacilityExporter.doExport(collectiveFacilitiesForTsunamiDisasterPrevention, objectId, "uf_", rs);
                doExport(collectiveFacilitiesForTsunamiDisasterPrevention, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("housingfacilities", prefix + "housingfacilities"),
                table.getColumn("supecificbusinessfacilities", prefix + "supecificbusinessfacilities"),
                table.getColumn("publicfacilities", prefix + "publicfacilities"),
                table.getColumn("utilityfacilities", prefix + "utilityfacilities"),
                table.getColumn("maximumbuildingheight", prefix + "maximumbuildingheight"),
                table.getColumn("maximumbuildingheight_uom", prefix + "maximumbuildingheight_uom"),
                table.getColumn("minimumbuildingheight", prefix + "minimumbuildingheight"),
                table.getColumn("minimumbuildingheight_uom", prefix + "minimumbuildingheight_uom"),
                table.getColumn("maximumfloorarearate", prefix + "maximumfloorarearate"),
                table.getColumn("minimumfloorarearate", prefix + "minimumfloorarearate"),
                table.getColumn("maximumbuildingcoveragerate", prefix + "maximumbuildingcoveragerate"));

        return select;
    }

    public void doExport(CollectiveFacilitiesForTsunamiDisasterPrevention collectiveFacilitiesForTsunamiDisasterPrevention, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String housingFacilities = rs.getString(prefix + "housingfacilities");
        if (!rs.wasNull())
            collectiveFacilitiesForTsunamiDisasterPrevention.setHousingFacilities(new StringOrRef(housingFacilities));

        String supecificBusinessFacilities = rs.getString(prefix + "supecificbusinessfacilities");
        if (!rs.wasNull())
            collectiveFacilitiesForTsunamiDisasterPrevention.setSupecificBusinessFacilities(new StringOrRef(supecificBusinessFacilities));

        String publicFacilities = rs.getString(prefix + "publicfacilities");
        if (!rs.wasNull())
            collectiveFacilitiesForTsunamiDisasterPrevention.setPublicFacilities(new StringOrRef(publicFacilities));

        String utilityFacilities = rs.getString(prefix + "utilityfacilities");
        if (!rs.wasNull())
            collectiveFacilitiesForTsunamiDisasterPrevention.setUtilityFacilities(new StringOrRef(utilityFacilities));

        double maximumBuildingHeight = rs.getDouble(prefix + "maximumbuildingheight");
        if (!rs.wasNull()) {
            Length length = new Length(maximumBuildingHeight);
            length.setUom(rs.getString(prefix + "maximumbuildingheight_uom"));
            collectiveFacilitiesForTsunamiDisasterPrevention.setMaximumBuildingHeight(length);
        }

        double minimumBuildingHeight = rs.getDouble(prefix + "minimumbuildingheight");
        if (!rs.wasNull()) {
            Length length = new Length(minimumBuildingHeight);
            length.setUom(rs.getString(prefix + "minimumbuildingheight_uom"));
            collectiveFacilitiesForTsunamiDisasterPrevention.setMinimumBuildingHeight(length);
        }

        double maximumFloorAreaRate = rs.getDouble(prefix + "maximumfloorarearate");
        if (!rs.wasNull())
            collectiveFacilitiesForTsunamiDisasterPrevention.setMaximumFloorAreaRate(maximumFloorAreaRate);

        double minimumFloorAreaRate = rs.getDouble(prefix + "minimumfloorarearate");
        if (!rs.wasNull())
            collectiveFacilitiesForTsunamiDisasterPrevention.setMinimumFloorAreaRate(minimumFloorAreaRate);

        double maximumBuildingCoverageRate = rs.getDouble(prefix + "maximumbuildingcoveragerate");
        if (!rs.wasNull())
            collectiveFacilitiesForTsunamiDisasterPrevention.setMaximumBuildingCoverageRate(maximumBuildingCoverageRate);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
