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
import org.citygml4j.ade.iur.model.urf.SpecifiedDisasterPreventionBlockImprovementZone;

import java.sql.*;

public class SpecifiedDisasterPreventionBlockImprovementZoneImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final DistrictsAndZonesImporter districtsAndZonesImporter;

    private int batchCounter;

    public SpecifiedDisasterPreventionBlockImprovementZoneImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.SPECIFIEDDISASTERPREVE)) + " " +
                "(id, minimumsitearea, minimumsitearea_uom, setbacksize, minimumfrontagerate, minimumbuildingheight," +
                "minimumbuildingheight_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?)");

        districtsAndZonesImporter = manager.getImporter(DistrictsAndZonesImporter.class);
    }

    public void doImport(SpecifiedDisasterPreventionBlockImprovementZone specifiedDisasterPreventionBlockImprovementZone, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        districtsAndZonesImporter.doImport(specifiedDisasterPreventionBlockImprovementZone, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (specifiedDisasterPreventionBlockImprovementZone.getMinimumSiteArea() != null && specifiedDisasterPreventionBlockImprovementZone.getMinimumSiteArea().isSetValue()) {
            ps.setDouble(2, specifiedDisasterPreventionBlockImprovementZone.getMinimumSiteArea().getValue());
            ps.setString(3, specifiedDisasterPreventionBlockImprovementZone.getMinimumSiteArea().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (specifiedDisasterPreventionBlockImprovementZone.getSetbackSize() != null && specifiedDisasterPreventionBlockImprovementZone.getSetbackSize().isSetValue())
            ps.setString(4, specifiedDisasterPreventionBlockImprovementZone.getSetbackSize().getValue());
        else
            ps.setNull(4, Types.VARCHAR);

        if (specifiedDisasterPreventionBlockImprovementZone.getMinimumFrontageRate() != null)
            ps.setInt(5, specifiedDisasterPreventionBlockImprovementZone.getMinimumFrontageRate());
        else
            ps.setNull(5, Types.INTEGER);

        if (specifiedDisasterPreventionBlockImprovementZone.getMinimumBuildingHeight() != null && specifiedDisasterPreventionBlockImprovementZone.getMinimumBuildingHeight().isSetValue()) {
            ps.setDouble(6, specifiedDisasterPreventionBlockImprovementZone.getMinimumBuildingHeight().getValue());
            ps.setString(7, specifiedDisasterPreventionBlockImprovementZone.getMinimumBuildingHeight().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

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
