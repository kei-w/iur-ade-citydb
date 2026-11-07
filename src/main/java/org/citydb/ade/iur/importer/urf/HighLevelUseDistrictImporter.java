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

package org.citydb.ade.iur.importer.urf;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.urf.HighLevelUseDistrict;
import org.citygml4j.model.gml.basicTypes.Measure;

import java.sql.*;

public class HighLevelUseDistrictImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final DistrictsAndZonesImporter districtsAndZonesImporter;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public HighLevelUseDistrictImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.HIGHLEVELUSEDISTRICT)) + " " +
                "(id, maximumfloorarearate, minimumfloorarearate, maximumbuildingcoveragerate, minimumbuildingarea," +
                "minimumbuildingarea_uom, setbacksize) " +
                "values (?, ?, ?, ?, ?, ?, ?)");

        districtsAndZonesImporter = manager.getImporter(DistrictsAndZonesImporter.class);
        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(HighLevelUseDistrict highLevelUseDistrict, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        districtsAndZonesImporter.doImport(highLevelUseDistrict, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (!highLevelUseDistrict.getMaximumFloorAreaRate().isEmpty())
            ps.setString(2, valueJoiner.join(highLevelUseDistrict.getMaximumFloorAreaRate()));
        else
            ps.setNull(2, Types.VARCHAR);

        if (!highLevelUseDistrict.getMinimumFloorAreaRate().isEmpty())
            ps.setString(3, valueJoiner.join(highLevelUseDistrict.getMinimumFloorAreaRate()));
        else
            ps.setNull(3, Types.VARCHAR);

        if (!highLevelUseDistrict.getMaximumBuildingCoverageRate().isEmpty())
            ps.setString(4, valueJoiner.join(highLevelUseDistrict.getMaximumBuildingCoverageRate()));
        else
            ps.setNull(4, Types.VARCHAR);

        if (!highLevelUseDistrict.getMinimumBuildingArea().isEmpty()) {
            valueJoiner.join(highLevelUseDistrict.getMinimumBuildingArea(), measure -> String.valueOf(measure.getValue()), Measure::getUom);
            ps.setString(5, valueJoiner.result(0));
            ps.setString(6, valueJoiner.result(1));
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (highLevelUseDistrict.getSetbackSize() != null && highLevelUseDistrict.getSetbackSize().isSetValue())
            ps.setString(7, highLevelUseDistrict.getSetbackSize().getValue());
        else
            ps.setNull(7, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);
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
