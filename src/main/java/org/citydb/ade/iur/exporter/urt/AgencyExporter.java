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

package org.citydb.ade.iur.exporter.urt;

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
import org.citygml4j.ade.iur.model.urt.Agency;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class AgencyExporter implements PublicTransitModuleExporter {
    private final ExportManager manager;
    private final PublicTransitExporter publicTransitExporter;
    private final PreparedStatement ps;

    public AgencyExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.manager = manager;
        String tableName = manager.getSchemaMapper().getTableName(ADETable.AGENCY);
        publicTransitExporter = manager.getExporter(PublicTransitExporter.class);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table publicTransit = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSIT)));
        Select select = new Select()
                .addJoin(JoinFactory.inner(publicTransit, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        addProjection(select, table, "");
        publicTransitExporter.addProjection(select, publicTransit, "pt_");
        ps = connection.prepareStatement(select.toString());
    }

    public void doExport(Agency agency, long objectId, AbstractType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                publicTransitExporter.doExport(agency, objectId, "pt_", rs);
                doExport(agency, objectId, "", rs);
            }
        }

        manager.exportGenericApplicationProperties(manager.getSchemaMapper().getTableName(ADETable.CITYOBJECT_2), agency, objectId, (FeatureType) objectType, projectionFilter);
    }

    public Select addProjection(Select select, Table table, String prefix) {
        select.addProjection(table.getColumn("name", prefix + "name"),
                table.getColumn("url", prefix + "url"),
                table.getColumn("timezone", prefix + "timezone"),
                table.getColumn("timezone_codespace", prefix + "timezone_codespace"),
                table.getColumn("language", prefix + "language"),
                table.getColumn("language_codespace", prefix + "language_codespace"),
                table.getColumn("phone", prefix + "phone"),
                table.getColumn("fareurl", prefix + "fareurl"),
                table.getColumn("email", prefix + "email"),
                table.getColumn("officialname", prefix + "officialname"),
                table.getColumn("zipnumber", prefix + "zipnumber"),
                table.getColumn("address", prefix + "address"),
                table.getColumn("presidentposition", prefix + "presidentposition"),
                table.getColumn("presidentname", prefix + "presidentname"));

        return select;
    }

    public void doExport(Agency agency, long objectId, String prefix, ResultSet rs) throws CityGMLExportException, SQLException {
        agency.setAgencyName(rs.getString(prefix + "name"));

        agency.setUrl(rs.getString(prefix + "url"));

        String timeZone = rs.getString(prefix + "timezone");
        if (!rs.wasNull()) {
            Code code = new Code(timeZone);
            code.setCodeSpace(rs.getString(prefix + "timezone_codespace"));
            agency.setTimeZone(code);
        }

        String language = rs.getString(prefix + "language");
        if (!rs.wasNull()) {
            Code code = new Code(language);
            code.setCodeSpace(rs.getString(prefix + "language_codespace"));
            agency.setLanguage(code);
        }

        agency.setPhone(rs.getString(prefix + "phone"));

        agency.setFareUrl(rs.getString(prefix + "fareurl"));

        agency.setEmail(rs.getString(prefix + "email"));

        agency.setOfficialName(rs.getString(prefix + "officialname"));

        agency.setZipNumber(rs.getString(prefix + "zipnumber"));

        agency.setAddress(rs.getString(prefix + "address"));

        agency.setPresidentPosition(rs.getString(prefix + "presidentposition"));

        agency.setPresidentName(rs.getString(prefix + "presidentname"));
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
