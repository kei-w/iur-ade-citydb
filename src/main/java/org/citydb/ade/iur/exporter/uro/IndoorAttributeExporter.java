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
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.IndoorAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorFacilityAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorFurnishingAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorPublicTagAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorSpaceAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorStoreyAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorTacatileTileAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorUserDefinedAttribute;
import org.citygml4j.ade.iur.model.uro.IndoorZoneAttribute;
import org.citygml4j.ade.iur.model.uro.UserDefinedValue;
import org.citygml4j.ade.iur.model.uro.UserDefinedValueProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class IndoorAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final PreparedStatement ps;
    private final UserDefinedValueExporter userDefinedValueExporter;

    public IndoorAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.INDOORATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table indoorSpaceAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDOORSPACEATTRIBUTE)));
        Table indoorFacilityAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDOORFACILITYATTRIBUT)));
        Table indoorStoreyAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDOORSTOREYATTRIBUTE)));
        Table indoorTacatileTileAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDOORTACATILETILEATTR)));
        Table indoorUserDefinedAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.INDOORUSERDEFINEDATTRI)));
        Select select = new Select().addProjection(table.getColumns("id", "objectclass_id", "source", "source_codespace", "floorid", "floorid_1", "ucode"))
                .addProjection(indoorSpaceAttribute.getColumns("isrestricted", "suite", "ispublic", "tolltype", "tolltype_codespace"))
                .addProjection(indoorFacilityAttribute.getColumns("weekdayhours", "weekendhours", "phone", "website"))
                .addProjection(indoorStoreyAttribute.getColumns("ordinal"))
                .addProjection(indoorTacatileTileAttribute.getColumns("startnode", "endnode", "category_codespace", "roof"))
                .addProjection(indoorUserDefinedAttribute.getColumns("name", "nominalvalue_id", "description", "unit"))
                .addProjection(indoorSpaceAttribute.getColumn("floorid", "indoorspaceattribute_floorid"))
                .addProjection(indoorStoreyAttribute.getColumn("category", "indoorstoreyattribute_category"))
                .addProjection(indoorTacatileTileAttribute.getColumn("category", "indoortacatiletileattribute_category"))
                .addProjection(indoorTacatileTileAttribute.getColumn("floorid", "indoortacatiletileattribute_floorid"))
                .addJoin(JoinFactory.left(indoorSpaceAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(indoorFacilityAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(indoorStoreyAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(indoorTacatileTileAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(indoorUserDefinedAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        userDefinedValueExporter = manager.getExporter(UserDefinedValueExporter.class);
    }

    public IndoorAttribute doExport(long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? doExport(objectId, rs) : null;
        }
    }

    private IndoorAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        IndoorAttribute indoorAttribute = objectMapper.createType(objectClassId, IndoorAttribute.class);
        if (indoorAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate indoor attribute (id: " + objectId + ").");
            return null;
        }

        String source = rs.getString("source");
        if (!rs.wasNull()) {
            Code code = new Code(source);
            code.setCodeSpace(rs.getString("source_codespace"));
            indoorAttribute.setSource(code);
        }

        if (indoorAttribute instanceof IndoorFurnishingAttribute) {
            IndoorFurnishingAttribute indoorFurnishingAttribute = (IndoorFurnishingAttribute) indoorAttribute;

            indoorFurnishingAttribute.setFloorId(rs.getString("floorid"));
        }

        if (indoorAttribute instanceof IndoorZoneAttribute) {
            IndoorZoneAttribute indoorZoneAttribute = (IndoorZoneAttribute) indoorAttribute;

            indoorZoneAttribute.setFloorId(rs.getString("floorid_1"));
        }

        if (indoorAttribute instanceof IndoorPublicTagAttribute) {
            IndoorPublicTagAttribute indoorPublicTagAttribute = (IndoorPublicTagAttribute) indoorAttribute;

            indoorPublicTagAttribute.setUcode(rs.getString("ucode"));
        }

        if (indoorAttribute instanceof IndoorSpaceAttribute) {
            IndoorSpaceAttribute indoorSpaceAttribute = (IndoorSpaceAttribute) indoorAttribute;

            indoorSpaceAttribute.setFloorId(rs.getString("indoorspaceattribute_floorid"));

            boolean isRestricted = rs.getBoolean("isrestricted");
            if (!rs.wasNull())
                indoorSpaceAttribute.setIsRestricted(isRestricted);

            indoorSpaceAttribute.setSuite(rs.getString("suite"));

            boolean isPublic = rs.getBoolean("ispublic");
            if (!rs.wasNull())
                indoorSpaceAttribute.setIsPublic(isPublic);

            String tollType = rs.getString("tolltype");
            if (!rs.wasNull()) {
                Code code = new Code(tollType);
                code.setCodeSpace(rs.getString("tolltype_codespace"));
                indoorSpaceAttribute.setTollType(code);
            }
        }

        if (indoorAttribute instanceof IndoorFacilityAttribute) {
            IndoorFacilityAttribute indoorFacilityAttribute = (IndoorFacilityAttribute) indoorAttribute;

            indoorFacilityAttribute.setWeekdayHours(rs.getString("weekdayhours"));

            indoorFacilityAttribute.setWeekendHours(rs.getString("weekendhours"));

            indoorFacilityAttribute.setPhone(rs.getString("phone"));

            indoorFacilityAttribute.setWebsite(rs.getString("website"));
        }

        if (indoorAttribute instanceof IndoorStoreyAttribute) {
            IndoorStoreyAttribute indoorStoreyAttribute = (IndoorStoreyAttribute) indoorAttribute;

            boolean category = rs.getBoolean("indoorstoreyattribute_category");
            if (!rs.wasNull())
                indoorStoreyAttribute.setCategory(category);

            double ordinal = rs.getDouble("ordinal");
            if (!rs.wasNull())
                indoorStoreyAttribute.setOrdinal(ordinal);
        }

        if (indoorAttribute instanceof IndoorTacatileTileAttribute) {
            IndoorTacatileTileAttribute indoorTacatileTileAttribute = (IndoorTacatileTileAttribute) indoorAttribute;

            indoorTacatileTileAttribute.setStartNode(rs.getString("startnode"));

            indoorTacatileTileAttribute.setEndNode(rs.getString("endnode"));

            String category = rs.getString("indoortacatiletileattribute_category");
            if (!rs.wasNull()) {
                Code code = new Code(category);
                code.setCodeSpace(rs.getString("category_codespace"));
                indoorTacatileTileAttribute.setCategory(code);
            }

            indoorTacatileTileAttribute.setRoof(rs.getString("roof"));

            indoorTacatileTileAttribute.setFloorId(rs.getString("indoortacatiletileattribute_floorid"));
        }

        if (indoorAttribute instanceof IndoorUserDefinedAttribute) {
            IndoorUserDefinedAttribute indoorUserDefinedAttribute = (IndoorUserDefinedAttribute) indoorAttribute;

            indoorUserDefinedAttribute.setName(rs.getString("name"));

            long nominalValueId = rs.getLong("nominalvalue_id");
            if (nominalValueId != 0) {
                UserDefinedValue nominalValue = new UserDefinedValue();
                userDefinedValueExporter.doExport(nominalValue, nominalValueId);
                indoorUserDefinedAttribute.setNominalValue(new UserDefinedValueProperty(nominalValue));
            }

            indoorUserDefinedAttribute.setDescription(rs.getString("description"));

            indoorUserDefinedAttribute.setUnit(rs.getString("unit"));
        }
        return indoorAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
