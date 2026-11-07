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
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.DmElement;
import org.citygml4j.model.gml.basicTypes.Code;

import java.time.YearMonth;

import java.sql.*;

public class DmElementExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;

    public DmElementExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.DMELEMENT);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("locationtype", "locationtype_codespace", "infotype", "infotype_codespace", "elementkey",
                        "hierarchylevel", "datatype", "datatype_codespace", "annotationtype", "annotationtype_codespace",
                        "precisiontype", "precisiontype_codespace", "dislocationtype", "dislocationtype_codespace",
                        "breaktype", "breaktype_codespace", "attributevalue", "attributetype", "attributetype_codespace",
                        "attributevaluetype", "creationdate", "updatedate", "terminationdate", "freespace"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public void doExport(DmElement dmElement, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String locationType = rs.getString("locationtype");
                if (!rs.wasNull()) {
                    Code code = new Code(locationType);
                    code.setCodeSpace(rs.getString("locationtype_codespace"));
                    dmElement.setLocationType(code);
                }

                String infoType = rs.getString("infotype");
                if (!rs.wasNull()) {
                    Code code = new Code(infoType);
                    code.setCodeSpace(rs.getString("infotype_codespace"));
                    dmElement.setInfoType(code);
                }

                dmElement.setElementKey(rs.getString("elementkey"));

                dmElement.setHierarchyLevel(rs.getString("hierarchylevel"));

                String dataType = rs.getString("datatype");
                if (!rs.wasNull()) {
                    Code code = new Code(dataType);
                    code.setCodeSpace(rs.getString("datatype_codespace"));
                    dmElement.setDataType(code);
                }

                String annotationType = rs.getString("annotationtype");
                if (!rs.wasNull()) {
                    Code code = new Code(annotationType);
                    code.setCodeSpace(rs.getString("annotationtype_codespace"));
                    dmElement.setAnnotationType(code);
                }

                String precisionType = rs.getString("precisiontype");
                if (!rs.wasNull()) {
                    Code code = new Code(precisionType);
                    code.setCodeSpace(rs.getString("precisiontype_codespace"));
                    dmElement.setPrecisionType(code);
                }

                String dislocationType = rs.getString("dislocationtype");
                if (!rs.wasNull()) {
                    Code code = new Code(dislocationType);
                    code.setCodeSpace(rs.getString("dislocationtype_codespace"));
                    dmElement.setDislocationType(code);
                }

                String breakType = rs.getString("breaktype");
                if (!rs.wasNull()) {
                    Code code = new Code(breakType);
                    code.setCodeSpace(rs.getString("breaktype_codespace"));
                    dmElement.setBreakType(code);
                }

                dmElement.setAttributeValue(rs.getString("attributevalue"));

                String attributeType = rs.getString("attributetype");
                if (!rs.wasNull()) {
                    Code code = new Code(attributeType);
                    code.setCodeSpace(rs.getString("attributetype_codespace"));
                    dmElement.setAttributeType(code);
                }

                dmElement.setAttributeValueType(rs.getString("attributevaluetype"));

                Date creationDate = rs.getDate("creationdate");
                if (!rs.wasNull())
                    dmElement.setCreationDate(YearMonth.from(creationDate.toLocalDate()));

                Date updateDate = rs.getDate("updatedate");
                if (!rs.wasNull())
                    dmElement.setUpdateDate(YearMonth.from(updateDate.toLocalDate()));

                Date terminationDate = rs.getDate("terminationdate");
                if (!rs.wasNull())
                    dmElement.setTerminationDate(YearMonth.from(terminationDate.toLocalDate()));

                dmElement.setFreeSpace(rs.getString("freespace"));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
