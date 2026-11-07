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
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citygml4j.ade.iur.model.uro.LargeCustomerFacilityAttribute;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.time.Year;

import java.sql.*;

public class LargeCustomerFacilityAttributeExporter implements UrbanObjectModuleExporter {
    private final PreparedStatement ps;
    private final AttributeValueSplitter valueSplitter;

    public LargeCustomerFacilityAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        String tableName = manager.getSchemaMapper().getTableName(ADETable.LARGECUSTOMERFACILITYA);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Select select = new Select().addProjection(table.getColumns("class", "class_codespace", "name", "capacity", "owner", "totalfloorarea", "totalfloorarea_uom",
                        "totalstorefloorarea", "totalstorefloorarea_uom", "inauguraldate", "yearopened", "yearclosed",
                        "keytenants", "availability", "urbanplantype", "urbanplantype_codespace", "areaclassificationtype",
                        "areaclassification_codespace", "districtsandzonestype", "districtsandzonest_codespace",
                        "landusetype", "landusetype_codespace", "reference", "note", "surveyyear"))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        valueSplitter = helper.getAttributeValueSplitter();
    }

    public void doExport(LargeCustomerFacilityAttribute largeCustomerFacilityAttribute, long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String classifier = rs.getString("class");
                if (!rs.wasNull()) {
                    Code code = new Code(classifier);
                    code.setCodeSpace(rs.getString("class_codespace"));
                    largeCustomerFacilityAttribute.setClassifier(code);
                }

                largeCustomerFacilityAttribute.setName(rs.getString("name"));

                int capacity = rs.getInt("capacity");
                if (!rs.wasNull())
                    largeCustomerFacilityAttribute.setCapacity(capacity);

                largeCustomerFacilityAttribute.setOwner(rs.getString("owner"));

                double totalFloorArea = rs.getDouble("totalfloorarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(totalFloorArea);
                    measure.setUom(rs.getString("totalfloorarea_uom"));
                    largeCustomerFacilityAttribute.setTotalFloorArea(measure);
                }

                double totalStoreFloorArea = rs.getDouble("totalstorefloorarea");
                if (!rs.wasNull()) {
                    Measure measure = new Measure(totalStoreFloorArea);
                    measure.setUom(rs.getString("totalstorefloorarea_uom"));
                    largeCustomerFacilityAttribute.setTotalStoreFloorArea(measure);
                }

                Date inauguralDate = rs.getDate("inauguraldate");
                if (!rs.wasNull())
                    largeCustomerFacilityAttribute.setInauguralDate(inauguralDate.toLocalDate());

                Date yearOpened = rs.getDate("yearopened");
                if (!rs.wasNull())
                    largeCustomerFacilityAttribute.setYearOpened(Year.of(yearOpened.toLocalDate().getYear()));

                Date yearClosed = rs.getDate("yearclosed");
                if (!rs.wasNull())
                    largeCustomerFacilityAttribute.setYearClosed(Year.of(yearClosed.toLocalDate().getYear()));

                largeCustomerFacilityAttribute.setKeyTenants(rs.getString("keytenants"));

                boolean availability = rs.getBoolean("availability");
                if (!rs.wasNull())
                    largeCustomerFacilityAttribute.setAvailability(availability);

                String urbanPlanType = rs.getString("urbanplantype");
                if (!rs.wasNull()) {
                    Code code = new Code(urbanPlanType);
                    code.setCodeSpace(rs.getString("urbanplantype_codespace"));
                    largeCustomerFacilityAttribute.setUrbanPlanType(code);
                }

                String areaClassificationType = rs.getString("areaclassificationtype");
                if (!rs.wasNull()) {
                    Code code = new Code(areaClassificationType);
                    code.setCodeSpace(rs.getString("areaclassification_codespace"));
                    largeCustomerFacilityAttribute.setAreaClassificationType(code);
                }

                for (SplitValue splitValue : valueSplitter.split(rs.getString("districtsandzonestype"), rs.getString("districtsandzonest_codespace"))) {
                    Code code = new Code(splitValue.result(0));
                    code.setCodeSpace(splitValue.result(1));
                    largeCustomerFacilityAttribute.getDistrictsAndZonesType().add(code);
                }

                String landUseType = rs.getString("landusetype");
                if (!rs.wasNull()) {
                    Code code = new Code(landUseType);
                    code.setCodeSpace(rs.getString("landusetype_codespace"));
                    largeCustomerFacilityAttribute.setLandUseType(code);
                }

                largeCustomerFacilityAttribute.setReference(rs.getString("reference"));

                largeCustomerFacilityAttribute.setNote(rs.getString("note"));

                Date surveyYear = rs.getDate("surveyyear");
                if (!rs.wasNull())
                    largeCustomerFacilityAttribute.setSurveyYear(Year.of(surveyYear.toLocalDate().getYear()));
            }
        }
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
