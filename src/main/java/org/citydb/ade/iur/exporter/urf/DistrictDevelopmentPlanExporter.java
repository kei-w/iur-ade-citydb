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
import org.citygml4j.ade.iur.model.urf.DistrictDevelopmentPlan;
import org.citygml4j.ade.iur.model.urf.DistrictFacilityProperty;
import org.citygml4j.ade.iur.model.urf.DistrictProperty;
import org.citygml4j.model.gml.base.StringOrRef;

import java.sql.*;

public class DistrictDevelopmentPlanExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psDistrictFacility;
    private final PreparedStatement psDistrict;
    private final AttributeValueSplitter valueSplitter;

    public DistrictDevelopmentPlanExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DISTRICTDEVELOPMENTPLA);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        zoneExporter = manager.getExporter(ZoneExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();

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

        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table districtFacilitys = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.DISTRICTD_TO_ZONE)));
        Select districtFacilitySelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, districtFacilitys.getColumn("zone_id")))
                .addSelection(ComparisonFactory.equalTo(districtFacilitys.getColumn("districtdevelopmentpla_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psDistrictFacility = connection.prepareStatement(districtFacilitySelect.toString());
        Table districts = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.DISTRICTD_TO_DISTRICT)));
        Select districtSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, districts.getColumn("district_id")))
                .addSelection(ComparisonFactory.equalTo(districts.getColumn("districtdevelopmentpla_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psDistrict = connection.prepareStatement(districtSelect.toString());
    }

    public void doExport(DistrictDevelopmentPlan districtDevelopmentPlan, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(districtDevelopmentPlan, objectId, "uf_", rs);
                zoneExporter.doExport(districtDevelopmentPlan, objectId, "z_", rs);
                doExport(districtDevelopmentPlan, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), districtDevelopmentPlan, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("districtfacilitiesallocation", prefix + "districtfacilitiesallocation"),
                table.getColumn("buildingrestrictions", prefix + "buildingrestrictions"),
                table.getColumn("urbangreenspaceconservation", prefix + "urbangreenspaceconservation"),
                table.getColumn("activityrestrictioninfarmlan", prefix + "activityrestrictioninfarmlan"),
                table.getColumn("landuserestrictions", prefix + "landuserestrictions"));

        return select;
    }

    public void doExport(DistrictDevelopmentPlan districtDevelopmentPlan, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String districtFacilitiesAllocation = rs.getString(prefix + "districtfacilitiesallocation");
        if (!rs.wasNull())
            districtDevelopmentPlan.setDistrictFacilitiesAllocation(new StringOrRef(districtFacilitiesAllocation));

        String buildingRestrictions = rs.getString(prefix + "buildingrestrictions");
        if (!rs.wasNull())
            districtDevelopmentPlan.setBuildingRestrictions(new StringOrRef(buildingRestrictions));

        String urbanGreenSpaceConservation = rs.getString(prefix + "urbangreenspaceconservation");
        if (!rs.wasNull())
            districtDevelopmentPlan.setUrbanGreenSpaceConservation(new StringOrRef(urbanGreenSpaceConservation));

        String activityRestrictionInFarmland = rs.getString(prefix + "activityrestrictioninfarmlan");
        if (!rs.wasNull())
            districtDevelopmentPlan.setActivityRestrictionInFarmland(new StringOrRef(activityRestrictionInFarmland));

        String landuseRestrictions = rs.getString(prefix + "landuserestrictions");
        if (!rs.wasNull())
            districtDevelopmentPlan.setLanduseRestrictions(new StringOrRef(landuseRestrictions));

        psDistrictFacility.setLong(1, objectId);
        try (ResultSet districtFacilityRs = psDistrictFacility.executeQuery()) {
            while (districtFacilityRs.next()) {
                DistrictFacilityProperty districtFacilityProperty = new DistrictFacilityProperty();
                districtFacilityProperty.setHref("#" + districtFacilityRs.getString("gmlid"));
                districtDevelopmentPlan.getDistrictFacility().add(districtFacilityProperty);
            }
        }

        psDistrict.setLong(1, objectId);
        try (ResultSet districtRs = psDistrict.executeQuery()) {
            while (districtRs.next()) {
                DistrictProperty districtProperty = new DistrictProperty();
                districtProperty.setHref("#" + districtRs.getString("gmlid"));
                districtDevelopmentPlan.getDistrict().add(districtProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psDistrictFacility.close();
        psDistrict.close();
    }
}
