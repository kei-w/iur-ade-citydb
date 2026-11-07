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
import org.citygml4j.ade.iur.model.uro.ConstructionStructureAttribute;
import org.citygml4j.ade.iur.model.uro.DamAttribute;
import org.citygml4j.ade.iur.model.uro.EmbankmentAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.sql.*;

public class ConstructionStructureAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final PreparedStatement ps;

    public ConstructionStructureAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.CONSTRUCTIONSTRUCTUREA);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table damAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.DAMATTRIBUTE)));
        Table embankmentAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.EMBANKMENTATTRIBUTE)));
        Select select = new Select().addProjection(table.getColumns("id", "objectclass_id", "structuretype", "structuretype_codespace", "length", "length_uom",
                        "width", "width_uom", "depth", "depth_uom", "volume", "volume_uom"))
                .addProjection(damAttribute.getColumns("damcode", "damcode_codespace", "totalwaterstorage", "totalwaterstorage_uom"))
                .addProjection(embankmentAttribute.getColumns("mainpartlength", "mainpartlength_uom", "ceilingheight", "ceilingheight_uom",
                        "wavedissipatorlength", "wavedissipatorlength_uom"))
                .addJoin(JoinFactory.left(damAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(embankmentAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public ConstructionStructureAttribute doExport(long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? doExport(objectId, rs) : null;
        }
    }

    private ConstructionStructureAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        ConstructionStructureAttribute constructionStructureAttribute = objectMapper.createType(objectClassId, ConstructionStructureAttribute.class);
        if (constructionStructureAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate construction structure attribute (id: " + objectId + ").");
            return null;
        }

        String structureType = rs.getString("structuretype");
        if (!rs.wasNull()) {
            Code code = new Code(structureType);
            code.setCodeSpace(rs.getString("structuretype_codespace"));
            constructionStructureAttribute.setStructureType(code);
        }

        double lengthValue = rs.getDouble("length");
        if (!rs.wasNull()) {
            Length length = new Length(lengthValue);
            length.setUom(rs.getString("length_uom"));
            constructionStructureAttribute.setLength(length);
        }

        double width = rs.getDouble("width");
        if (!rs.wasNull()) {
            Length length = new Length(width);
            length.setUom(rs.getString("width_uom"));
            constructionStructureAttribute.setWidth(length);
        }

        double depth = rs.getDouble("depth");
        if (!rs.wasNull()) {
            Length length = new Length(depth);
            length.setUom(rs.getString("depth_uom"));
            constructionStructureAttribute.setDepth(length);
        }

        double volume = rs.getDouble("volume");
        if (!rs.wasNull()) {
            Measure measure = new Measure(volume);
            measure.setUom(rs.getString("volume_uom"));
            constructionStructureAttribute.setVolume(measure);
        }

        if (constructionStructureAttribute instanceof DamAttribute) {
            DamAttribute damAttribute = (DamAttribute) constructionStructureAttribute;

            String damCode = rs.getString("damcode");
            if (!rs.wasNull()) {
                Code code = new Code(damCode);
                code.setCodeSpace(rs.getString("damcode_codespace"));
                damAttribute.setDamCode(code);
            }

            double totalWaterStorage = rs.getDouble("totalwaterstorage");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalWaterStorage);
                measure.setUom(rs.getString("totalwaterstorage_uom"));
                damAttribute.setTotalWaterStorage(measure);
            }
        }

        if (constructionStructureAttribute instanceof EmbankmentAttribute) {
            EmbankmentAttribute embankmentAttribute = (EmbankmentAttribute) constructionStructureAttribute;

            double mainPartLength = rs.getDouble("mainpartlength");
            if (!rs.wasNull()) {
                Length length = new Length(mainPartLength);
                length.setUom(rs.getString("mainpartlength_uom"));
                embankmentAttribute.setMainPartLength(length);
            }

            double ceilingHeight = rs.getDouble("ceilingheight");
            if (!rs.wasNull()) {
                Length length = new Length(ceilingHeight);
                length.setUom(rs.getString("ceilingheight_uom"));
                embankmentAttribute.setCeilingHeight(length);
            }

            double waveDissipatorLength = rs.getDouble("wavedissipatorlength");
            if (!rs.wasNull()) {
                Length length = new Length(waveDissipatorLength);
                length.setUom(rs.getString("wavedissipatorlength_uom"));
                embankmentAttribute.setWaveDissipatorLength(length);
            }
        }
        return constructionStructureAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
