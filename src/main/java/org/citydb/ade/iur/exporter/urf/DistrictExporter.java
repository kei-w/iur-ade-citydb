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
import org.citygml4j.ade.iur.model.urf.District;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class DistrictExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final PreparedStatement ps;

    public DistrictExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DISTRICT);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Table zone = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ZONE)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.inner(zone, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        zoneExporter.addProjection(select, zone, "z_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(District district, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(district, objectId, "uf_", rs);
                zoneExporter.doExport(district, objectId, "z_", rs);
                doExport(district, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("buildingrestrictions", prefix + "buildingrestrictions"),
                table.getColumn("userestrictions", prefix + "userestrictions"),
                table.getColumn("maximumfloorarearate", prefix + "maximumfloorarearate"),
                table.getColumn("minimumfloorarearate", prefix + "minimumfloorarearate"),
                table.getColumn("maximumbuildingcoveragerate", prefix + "maximumbuildingcoveragerate"),
                table.getColumn("minimumbuildingcoveragerate", prefix + "minimumbuildingcoveragerate"),
                table.getColumn("minimumsitearea", prefix + "minimumsitearea"),
                table.getColumn("minimumsitearea_uom", prefix + "minimumsitearea_uom"),
                table.getColumn("minimumbuildingarea", prefix + "minimumbuildingarea"),
                table.getColumn("minimumbuildingarea_uom", prefix + "minimumbuildingarea_uom"),
                table.getColumn("minimumgroundheight", prefix + "minimumgroundheight"),
                table.getColumn("minimumgroundheight_uom", prefix + "minimumgroundheight_uom"),
                table.getColumn("setbacksize", prefix + "setbacksize"),
                table.getColumn("structureplacementrestrictio", prefix + "structureplacementrestrictio"),
                table.getColumn("maximumbuildingheight", prefix + "maximumbuildingheight"),
                table.getColumn("maximumbuildingheight_uom", prefix + "maximumbuildingheight_uom"),
                table.getColumn("minimumbuildingheight", prefix + "minimumbuildingheight"),
                table.getColumn("minimumbuildingheight_uom", prefix + "minimumbuildingheight_uom"),
                table.getColumn("minimumfloorheight", prefix + "minimumfloorheight"),
                table.getColumn("minimumfloorheight_uom", prefix + "minimumfloorheight_uom"),
                table.getColumn("buildingdesignrestriction", prefix + "buildingdesignrestriction"),
                table.getColumn("minimumgreeningrate", prefix + "minimumgreeningrate"),
                table.getColumn("fenceguideline", prefix + "fenceguideline"),
                table.getColumn("restrictionsforfireprotectio", prefix + "restrictionsforfireprotectio"),
                table.getColumn("restrictionsfornoiseprotecti", prefix + "restrictionsfornoiseprotecti"),
                table.getColumn("minimumfrontagerate", prefix + "minimumfrontagerate"));

        return select;
    }

    public void doExport(District district, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String buildingRestrictions = rs.getString(prefix + "buildingrestrictions");
        if (!rs.wasNull())
            district.setBuildingRestrictions(new StringOrRef(buildingRestrictions));

        String useRestrictions = rs.getString(prefix + "userestrictions");
        if (!rs.wasNull())
            district.setUseRestrictions(new StringOrRef(useRestrictions));

        double maximumFloorAreaRate = rs.getDouble(prefix + "maximumfloorarearate");
        if (!rs.wasNull())
            district.setMaximumFloorAreaRate(maximumFloorAreaRate);

        double minimumFloorAreaRate = rs.getDouble(prefix + "minimumfloorarearate");
        if (!rs.wasNull())
            district.setMinimumFloorAreaRate(minimumFloorAreaRate);

        double maximumBuildingCoverageRate = rs.getDouble(prefix + "maximumbuildingcoveragerate");
        if (!rs.wasNull())
            district.setMaximumBuildingCoverageRate(maximumBuildingCoverageRate);

        double minimumBuildingCoverageRate = rs.getDouble(prefix + "minimumbuildingcoveragerate");
        if (!rs.wasNull())
            district.setMinimumBuildingCoverageRate(minimumBuildingCoverageRate);

        double minimumSiteArea = rs.getDouble(prefix + "minimumsitearea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(minimumSiteArea);
            measure.setUom(rs.getString(prefix + "minimumsitearea_uom"));
            district.setMinimumSiteArea(measure);
        }

        double minimumBuildingArea = rs.getDouble(prefix + "minimumbuildingarea");
        if (!rs.wasNull()) {
            Measure measure = new Measure(minimumBuildingArea);
            measure.setUom(rs.getString(prefix + "minimumbuildingarea_uom"));
            district.setMinimumBuildingArea(measure);
        }

        double minimumGroundHeight = rs.getDouble(prefix + "minimumgroundheight");
        if (!rs.wasNull()) {
            Length length = new Length(minimumGroundHeight);
            length.setUom(rs.getString(prefix + "minimumgroundheight_uom"));
            district.setMinimumGroundHeight(length);
        }

        String setbackSize = rs.getString(prefix + "setbacksize");
        if (!rs.wasNull())
            district.setSetbackSize(new StringOrRef(setbackSize));

        String structurePlacementRestrictions = rs.getString(prefix + "structureplacementrestrictio");
        if (!rs.wasNull())
            district.setStructurePlacementRestrictions(new StringOrRef(structurePlacementRestrictions));

        double maximumBuildingHeight = rs.getDouble(prefix + "maximumbuildingheight");
        if (!rs.wasNull()) {
            Length length = new Length(maximumBuildingHeight);
            length.setUom(rs.getString(prefix + "maximumbuildingheight_uom"));
            district.setMaximumBuildingHeight(length);
        }

        double minimumBuildingHeight = rs.getDouble(prefix + "minimumbuildingheight");
        if (!rs.wasNull()) {
            Length length = new Length(minimumBuildingHeight);
            length.setUom(rs.getString(prefix + "minimumbuildingheight_uom"));
            district.setMinimumBuildingHeight(length);
        }

        double minimumFloorHeight = rs.getDouble(prefix + "minimumfloorheight");
        if (!rs.wasNull()) {
            Length length = new Length(minimumFloorHeight);
            length.setUom(rs.getString(prefix + "minimumfloorheight_uom"));
            district.setMinimumFloorHeight(length);
        }

        String buildingDesignRestriction = rs.getString(prefix + "buildingdesignrestriction");
        if (!rs.wasNull())
            district.setBuildingDesignRestriction(new StringOrRef(buildingDesignRestriction));

        double minimumGreeningRate = rs.getDouble(prefix + "minimumgreeningrate");
        if (!rs.wasNull())
            district.setMinimumGreeningRate(minimumGreeningRate);

        String fenceGuideline = rs.getString(prefix + "fenceguideline");
        if (!rs.wasNull())
            district.setFenceGuideline(new StringOrRef(fenceGuideline));

        String restrictionsForFireProtection = rs.getString(prefix + "restrictionsforfireprotectio");
        if (!rs.wasNull())
            district.setRestrictionsForFireProtection(new StringOrRef(restrictionsForFireProtection));

        String restrictionsForNoiseProtection = rs.getString(prefix + "restrictionsfornoiseprotecti");
        if (!rs.wasNull())
            district.setRestrictionsForNoiseProtection(new StringOrRef(restrictionsForNoiseProtection));

        double minimumFrontageRate = rs.getDouble(prefix + "minimumfrontagerate");
        if (!rs.wasNull())
            district.setMinimumFrontageRate(minimumFrontageRate);
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
