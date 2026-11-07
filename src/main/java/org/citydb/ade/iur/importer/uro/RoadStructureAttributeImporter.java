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
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.RoadStructureAttribute;

import java.sql.*;

public class RoadStructureAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final RoadAttributeImporter roadAttributeImporter;

    private int batchCounter;

    public RoadStructureAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.ROADSTRUCTUREATTRIBUTE)) + " " +
                "(id, widthtype, widthtype_codespace, width, width_uom, numberoflanes, sectiontype," +
                "sectiontype_codespace) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)");

        roadAttributeImporter = manager.getImporter(RoadAttributeImporter.class);
    }

    public long doImport(RoadStructureAttribute roadStructureAttribute) throws CityGMLImportException, SQLException {
        long objectId = roadAttributeImporter.doImport(roadStructureAttribute);
        ps.setLong(1, objectId);

        if (roadStructureAttribute.getWidthType() != null && roadStructureAttribute.getWidthType().isSetValue()) {
            ps.setString(2, roadStructureAttribute.getWidthType().getValue());
            ps.setString(3, roadStructureAttribute.getWidthType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (roadStructureAttribute.getWidth() != null && roadStructureAttribute.getWidth().isSetValue()) {
            ps.setDouble(4, roadStructureAttribute.getWidth().getValue());
            ps.setString(5, roadStructureAttribute.getWidth().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (roadStructureAttribute.getNumberOfLanes() != null)
            ps.setInt(6, roadStructureAttribute.getNumberOfLanes());
        else
            ps.setNull(6, Types.INTEGER);

        if (roadStructureAttribute.getSectionType() != null && roadStructureAttribute.getSectionType().isSetValue()) {
            ps.setString(7, roadStructureAttribute.getSectionType().getValue());
            ps.setString(8, roadStructureAttribute.getSectionType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.ROADSTRUCTUREATTRIBUTE));

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
