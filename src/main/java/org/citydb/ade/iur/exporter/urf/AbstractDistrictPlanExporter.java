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
import org.citygml4j.ade.iur.model.urf.AbstractDistrictPlan;
import org.citygml4j.ade.iur.model.urf.DistrictDevelopmentPlanProperty;
import org.citygml4j.ade.iur.model.urf.PromotionDistrictProperty;
import org.citygml4j.model.gml.base.StringOrRef;

import java.sql.*;

public class AbstractDistrictPlanExporter implements UrbanFunctionModuleExporter {
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final ZoneExporter zoneExporter;
    private final PreparedStatement ps;
    private final PreparedStatement psDistrictDevelopmentPlan;
    private final PreparedStatement psPromotionDistrict;
    private final AttributeValueSplitter valueSplitter;

    public AbstractDistrictPlanExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.ABSTRACTDISTRICTPLAN);
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
        Table districtDevelopmentPlans = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ABSTRACTD_TO_DISTRICTD)));
        Select districtDevelopmentPlanSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, districtDevelopmentPlans.getColumn("districtdevelopmentpla_id")))
                .addSelection(ComparisonFactory.equalTo(districtDevelopmentPlans.getColumn("abstractdistrictplan_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psDistrictDevelopmentPlan = connection.prepareStatement(districtDevelopmentPlanSelect.toString());
        Table promotionDistricts = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.ABSTRACTD_TO_ZONE)));
        Select promotionDistrictSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, promotionDistricts.getColumn("zone_id")))
                .addSelection(ComparisonFactory.equalTo(promotionDistricts.getColumn("abstractdistrictplan_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psPromotionDistrict = connection.prepareStatement(promotionDistrictSelect.toString());
    }

    public void doExport(AbstractDistrictPlan abstractDistrictPlan, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(abstractDistrictPlan, objectId, "uf_", rs);
                zoneExporter.doExport(abstractDistrictPlan, objectId, "z_", rs);
                doExport(abstractDistrictPlan, objectId, "", rs);
            }
        }
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("objectives", prefix + "objectives"),
                table.getColumn("policy", prefix + "policy"));

        return select;
    }

    public void doExport(AbstractDistrictPlan abstractDistrictPlan, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        String objectives = rs.getString(prefix + "objectives");
        if (!rs.wasNull())
            abstractDistrictPlan.setObjectives(new StringOrRef(objectives));

        String policy = rs.getString(prefix + "policy");
        if (!rs.wasNull())
            abstractDistrictPlan.setPolicy(new StringOrRef(policy));

        psDistrictDevelopmentPlan.setLong(1, objectId);
        try (ResultSet districtDevelopmentPlanRs = psDistrictDevelopmentPlan.executeQuery()) {
            while (districtDevelopmentPlanRs.next()) {
                DistrictDevelopmentPlanProperty districtDevelopmentPlanProperty = new DistrictDevelopmentPlanProperty();
                districtDevelopmentPlanProperty.setHref("#" + districtDevelopmentPlanRs.getString("gmlid"));
                abstractDistrictPlan.getDistrictDevelopmentPlan().add(districtDevelopmentPlanProperty);
            }
        }

        psPromotionDistrict.setLong(1, objectId);
        try (ResultSet promotionDistrictRs = psPromotionDistrict.executeQuery()) {
            while (promotionDistrictRs.next()) {
                PromotionDistrictProperty promotionDistrictProperty = new PromotionDistrictProperty();
                promotionDistrictProperty.setHref("#" + promotionDistrictRs.getString("gmlid"));
                abstractDistrictPlan.getPromotionDistrict().add(promotionDistrictProperty);
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psDistrictDevelopmentPlan.close();
        psPromotionDistrict.close();
    }
}
