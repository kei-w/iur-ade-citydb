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
import org.citygml4j.ade.iur.model.urf.AgriculturalDevelopmentProjectType;
import org.citygml4j.ade.iur.model.urf.AgriculturalDevelopmentProjectTypeProperty;
import org.citygml4j.ade.iur.model.urf.DevelopmentProject;
import org.citygml4j.ade.iur.model.urf.ResidentialDevelopmentType;
import org.citygml4j.ade.iur.model.urf.ResidentialDevelopmentTypeProperty;
import org.citygml4j.ade.iur.model.urf.UrbanDevelopmentProjectType;
import org.citygml4j.ade.iur.model.urf.UrbanDevelopmentProjectTypeProperty;

import java.sql.*;

public class DevelopmentProjectExporter implements UrbanFunctionModuleExporter {
    private final ExportManager manager;
    private final UrbanFunctionExporter urbanFunctionExporter;
    private final PreparedStatement ps;
    private final ResidentialDevelopmentTypeExporter residentialDevelopmentTypeExporter;
    private final UrbanDevelopmentProjectTypeExporter urbanDevelopmentProjectTypeExporter;
    private final AgriculturalDevelopmentProjectTypeExporter agriculturalDevelopmentProjectTypeExporter;

    public DevelopmentProjectExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DEVELOPMENTPROJECT);
        urbanFunctionExporter = manager.getExporter(UrbanFunctionExporter.class);
        residentialDevelopmentTypeExporter = manager.getExporter(ResidentialDevelopmentTypeExporter.class);
        urbanDevelopmentProjectTypeExporter = manager.getExporter(UrbanDevelopmentProjectTypeExporter.class);
        agriculturalDevelopmentProjectTypeExporter = manager.getExporter(AgriculturalDevelopmentProjectTypeExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table urbanFunction = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.URBANFUNCTION)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(urbanFunction, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        urbanFunctionExporter.addProjection(select, urbanFunction, "uf_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(DevelopmentProject developmentProject, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                urbanFunctionExporter.doExport(developmentProject, objectId, "uf_", rs);
                doExport(developmentProject, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), developmentProject, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("residentialdevelopmenttyp_id", prefix + "residentialdevelopmenttyp_id"),
                table.getColumn("urbandevelopmenttype_id", prefix + "urbandevelopmenttype_id"),
                table.getColumn("agriculturaldevelopmentty_id", prefix + "agriculturaldevelopmentty_id"));

        return select;
    }

    public void doExport(DevelopmentProject developmentProject, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        long residentialDevelopmentTypeId = rs.getLong(prefix + "residentialdevelopmenttyp_id");
        if (residentialDevelopmentTypeId != 0) {
            ResidentialDevelopmentType residentialDevelopmentType = new ResidentialDevelopmentType();
            residentialDevelopmentTypeExporter.doExport(residentialDevelopmentType, residentialDevelopmentTypeId);
            developmentProject.setResidentialDevelopmentType(new ResidentialDevelopmentTypeProperty(residentialDevelopmentType));
        }

        long urbanDevelopmentTypeId = rs.getLong(prefix + "urbandevelopmenttype_id");
        if (urbanDevelopmentTypeId != 0) {
            UrbanDevelopmentProjectType urbanDevelopmentType = new UrbanDevelopmentProjectType();
            urbanDevelopmentProjectTypeExporter.doExport(urbanDevelopmentType, urbanDevelopmentTypeId);
            developmentProject.setUrbanDevelopmentType(new UrbanDevelopmentProjectTypeProperty(urbanDevelopmentType));
        }

        long agriculturalDevelopmentTypeId = rs.getLong(prefix + "agriculturaldevelopmentty_id");
        if (agriculturalDevelopmentTypeId != 0) {
            AgriculturalDevelopmentProjectType agriculturalDevelopmentType = new AgriculturalDevelopmentProjectType();
            agriculturalDevelopmentProjectTypeExporter.doExport(agriculturalDevelopmentType, agriculturalDevelopmentTypeId);
            developmentProject.setAgriculturalDevelopmentType(new AgriculturalDevelopmentProjectTypeProperty(agriculturalDevelopmentType));
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
