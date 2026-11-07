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

package org.citydb.ade.iur.importer.uro;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.FacilityIdAttribute;
import org.citygml4j.ade.iur.model.uro.RiverFacilityIdAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class FacilityIdAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final RiverFacilityIdAttributeImporter riverFacilityIdAttributeImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public FacilityIdAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FACILITYIDATTRIBUTE)) + " " +
                "(id, id_1, partid, branchid, prefecture, prefecture_codespace, city, city_codespace, route," +
                "startpost, endpost, startlat, startlong, alternativename) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        riverFacilityIdAttributeImporter = manager.getImporter(RiverFacilityIdAttributeImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(FacilityIdAttribute facilityIdAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.FACILITYIDATTRIBUT_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, facilityIdAttribute.getId());

        ps.setString(3, facilityIdAttribute.getPartId());

        ps.setString(4, facilityIdAttribute.getBranchId());

        if (!facilityIdAttribute.getPrefecture().isEmpty()) {
            valueJoiner.join(facilityIdAttribute.getPrefecture(), Code::getValue, Code::getCodeSpace);
            ps.setString(5, valueJoiner.result(0));
            ps.setString(6, valueJoiner.result(1));
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (!facilityIdAttribute.getCity().isEmpty()) {
            valueJoiner.join(facilityIdAttribute.getCity(), Code::getValue, Code::getCodeSpace);
            ps.setString(7, valueJoiner.result(0));
            ps.setString(8, valueJoiner.result(1));
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        ps.setString(9, facilityIdAttribute.getRoute());

        ps.setString(10, facilityIdAttribute.getStartPost());

        ps.setString(11, facilityIdAttribute.getEndPost());

        if (facilityIdAttribute.getStartLat() != null)
            ps.setDouble(12, facilityIdAttribute.getStartLat());
        else
            ps.setNull(12, Types.DOUBLE);

        if (facilityIdAttribute.getStartLong() != null)
            ps.setDouble(13, facilityIdAttribute.getStartLong());
        else
            ps.setNull(13, Types.DOUBLE);

        if (!facilityIdAttribute.getAlternativeName().isEmpty())
            ps.setString(14, valueJoiner.join(facilityIdAttribute.getAlternativeName()));
        else
            ps.setNull(14, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.FACILITYIDATTRIBUTE));

        if (facilityIdAttribute instanceof RiverFacilityIdAttribute)
            riverFacilityIdAttributeImporter.doImport((RiverFacilityIdAttribute) facilityIdAttribute, objectId);

        return objectId;
    }

    @Override
    public void executeBatch() throws CityGMLImportException, SQLException {
        if (batchCounter > 0) {
            ps.executeBatch();
            batchCounter = 0;
        }
    }

    @Override
    public void close() throws CityGMLImportException, SQLException {
        ps.close();
    }
}
