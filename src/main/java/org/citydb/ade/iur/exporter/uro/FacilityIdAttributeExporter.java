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
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.FacilityIdAttribute;
import org.citygml4j.ade.iur.model.uro.RiverFacilityIdAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class FacilityIdAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public FacilityIdAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.FACILITYIDATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table riverFacilityIdAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.RIVERFACILITYIDATTRIBU)));
        Select select = new Select().addProjection(table.getColumns("id", "id_1", "partid", "branchid", "prefecture", "prefecture_codespace", "city", "city_codespace",
                        "route", "startpost", "endpost", "startlat", "startlong", "alternativename"))
                .addProjection(riverFacilityIdAttribute.getColumns("rivercode", "rivercode_codespace", "rivername", "sidetype", "sidetype_codespace", "leftpost",
                        "leftpost_uom", "leftdistance", "leftdistance_uom", "rightpost", "rightpost_uom", "rightdistance",
                        "rightdistance_uom", "leftstartpost", "leftstartpost_uom", "leftstartdistance",
                        "leftstartdistance_uom", "leftendpost", "leftendpost_uom", "leftenddistance",
                        "leftenddistance_uom", "rightstartpost", "rightstartpost_uom", "rightstartdistance",
                        "rightstartdistance_uom", "rightendpost", "rightendpost_uom", "rightenddistance",
                        "rightenddistance_uom"))
                .addProjection(riverFacilityIdAttribute.getColumn("id", "riverfacilityidattribute_id"))
                .addJoin(JoinFactory.left(riverFacilityIdAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public FacilityIdAttribute doExport(long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? doExport(objectId, rs) : null;
        }
    }

    private FacilityIdAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        FacilityIdAttribute facilityIdAttribute = rs.getLong("riverfacilityidattribute_id") != 0 ? new RiverFacilityIdAttribute() : new FacilityIdAttribute();

        facilityIdAttribute.setId(rs.getString("id_1"));

        facilityIdAttribute.setPartId(rs.getString("partid"));

        facilityIdAttribute.setBranchId(rs.getString("branchid"));

        for (SplitValue splitValue : valueSplitter.split(rs.getString("prefecture"), rs.getString("prefecture_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            facilityIdAttribute.getPrefecture().add(code);
        }

        for (SplitValue splitValue : valueSplitter.split(rs.getString("city"), rs.getString("city_codespace"))) {
            Code code = new Code(splitValue.result(0));
            code.setCodeSpace(splitValue.result(1));
            facilityIdAttribute.getCity().add(code);
        }

        facilityIdAttribute.setRoute(rs.getString("route"));

        facilityIdAttribute.setStartPost(rs.getString("startpost"));

        facilityIdAttribute.setEndPost(rs.getString("endpost"));

        double startLat = rs.getDouble("startlat");
        if (!rs.wasNull())
            facilityIdAttribute.setStartLat(startLat);

        double startLong = rs.getDouble("startlong");
        if (!rs.wasNull())
            facilityIdAttribute.setStartLong(startLong);

        for (SplitValue splitValue : valueSplitter.split(rs.getString("alternativename")))
            facilityIdAttribute.getAlternativeName().add(splitValue.result(0));

        if (facilityIdAttribute instanceof RiverFacilityIdAttribute) {
            RiverFacilityIdAttribute riverFacilityIdAttribute = (RiverFacilityIdAttribute) facilityIdAttribute;

            String riverCode = rs.getString("rivercode");
            if (!rs.wasNull()) {
                Code code = new Code(riverCode);
                code.setCodeSpace(rs.getString("rivercode_codespace"));
                riverFacilityIdAttribute.setRiverCode(code);
            }

            riverFacilityIdAttribute.setRiverName(rs.getString("rivername"));

            String sideType = rs.getString("sidetype");
            if (!rs.wasNull()) {
                Code code = new Code(sideType);
                code.setCodeSpace(rs.getString("sidetype_codespace"));
                riverFacilityIdAttribute.setSideType(code);
            }

            double leftPost = rs.getDouble("leftpost");
            if (!rs.wasNull()) {
                Length length = new Length(leftPost);
                length.setUom(rs.getString("leftpost_uom"));
                riverFacilityIdAttribute.setLeftPost(length);
            }

            double leftDistance = rs.getDouble("leftdistance");
            if (!rs.wasNull()) {
                Length length = new Length(leftDistance);
                length.setUom(rs.getString("leftdistance_uom"));
                riverFacilityIdAttribute.setLeftDistance(length);
            }

            double rightPost = rs.getDouble("rightpost");
            if (!rs.wasNull()) {
                Length length = new Length(rightPost);
                length.setUom(rs.getString("rightpost_uom"));
                riverFacilityIdAttribute.setRightPost(length);
            }

            double rightDistance = rs.getDouble("rightdistance");
            if (!rs.wasNull()) {
                Length length = new Length(rightDistance);
                length.setUom(rs.getString("rightdistance_uom"));
                riverFacilityIdAttribute.setRightDistance(length);
            }

            double leftStartPost = rs.getDouble("leftstartpost");
            if (!rs.wasNull()) {
                Length length = new Length(leftStartPost);
                length.setUom(rs.getString("leftstartpost_uom"));
                riverFacilityIdAttribute.setLeftStartPost(length);
            }

            double leftStartDistance = rs.getDouble("leftstartdistance");
            if (!rs.wasNull()) {
                Length length = new Length(leftStartDistance);
                length.setUom(rs.getString("leftstartdistance_uom"));
                riverFacilityIdAttribute.setLeftStartDistance(length);
            }

            double leftEndPost = rs.getDouble("leftendpost");
            if (!rs.wasNull()) {
                Length length = new Length(leftEndPost);
                length.setUom(rs.getString("leftendpost_uom"));
                riverFacilityIdAttribute.setLeftEndPost(length);
            }

            double leftEndDistance = rs.getDouble("leftenddistance");
            if (!rs.wasNull()) {
                Length length = new Length(leftEndDistance);
                length.setUom(rs.getString("leftenddistance_uom"));
                riverFacilityIdAttribute.setLeftEndDistance(length);
            }

            double rightStartPost = rs.getDouble("rightstartpost");
            if (!rs.wasNull()) {
                Length length = new Length(rightStartPost);
                length.setUom(rs.getString("rightstartpost_uom"));
                riverFacilityIdAttribute.setRightStartPost(length);
            }

            double rightStartDistance = rs.getDouble("rightstartdistance");
            if (!rs.wasNull()) {
                Length length = new Length(rightStartDistance);
                length.setUom(rs.getString("rightstartdistance_uom"));
                riverFacilityIdAttribute.setRightStartDistance(length);
            }

            double rightEndPost = rs.getDouble("rightendpost");
            if (!rs.wasNull()) {
                Length length = new Length(rightEndPost);
                length.setUom(rs.getString("rightendpost_uom"));
                riverFacilityIdAttribute.setRightEndPost(length);
            }

            double rightEndDistance = rs.getDouble("rightenddistance");
            if (!rs.wasNull()) {
                Length length = new Length(rightEndDistance);
                length.setUom(rs.getString("rightenddistance_uom"));
                riverFacilityIdAttribute.setRightEndDistance(length);
            }
        }
        return facilityIdAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
