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
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.HarborFacility;

import java.sql.*;

public class HarborFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public HarborFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.HARBORFACILITY)) + " " +
                "(id, geologicaltype, geologicaltype_codespace, obstructingstructures, structurallimitations," +
                "structurallimitations_uom, length, length_uom, minimumwidth, minimumwidth_uom, maximumwidth," +
                "maximumwidth_uom, planneddepth, planneddepth_uom, currentdepth, currentdepth_uom, isdredged," +
                "areatype, areatype_codespace, innerarea, innerarea_uom, outerarea, outerarea_uom, totalcost," +
                "subsidy, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public void doImport(HarborFacility harborFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (harborFacility.getGeologicalType() != null && harborFacility.getGeologicalType().isSetValue()) {
            ps.setString(2, harborFacility.getGeologicalType().getValue());
            ps.setString(3, harborFacility.getGeologicalType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, harborFacility.getObstructingStructures());

        if (harborFacility.getStructuralLimitations() != null && harborFacility.getStructuralLimitations().isSetValue()) {
            ps.setDouble(5, harborFacility.getStructuralLimitations().getValue());
            ps.setString(6, harborFacility.getStructuralLimitations().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (harborFacility.getLength() != null && harborFacility.getLength().isSetValue()) {
            ps.setDouble(7, harborFacility.getLength().getValue());
            ps.setString(8, harborFacility.getLength().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (harborFacility.getMinimumWidth() != null && harborFacility.getMinimumWidth().isSetValue()) {
            ps.setDouble(9, harborFacility.getMinimumWidth().getValue());
            ps.setString(10, harborFacility.getMinimumWidth().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (harborFacility.getMaximumWidth() != null && harborFacility.getMaximumWidth().isSetValue()) {
            ps.setDouble(11, harborFacility.getMaximumWidth().getValue());
            ps.setString(12, harborFacility.getMaximumWidth().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (harborFacility.getPlannedDepth() != null && harborFacility.getPlannedDepth().isSetValue()) {
            ps.setDouble(13, harborFacility.getPlannedDepth().getValue());
            ps.setString(14, harborFacility.getPlannedDepth().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (harborFacility.getCurrentDepth() != null && harborFacility.getCurrentDepth().isSetValue()) {
            ps.setDouble(15, harborFacility.getCurrentDepth().getValue());
            ps.setString(16, harborFacility.getCurrentDepth().getUom());
        } else {
            ps.setNull(15, Types.DOUBLE);
            ps.setNull(16, Types.VARCHAR);
        }

        if (harborFacility.getIsDredged() != null)
            ps.setInt(17, harborFacility.getIsDredged() ? 1 : 0);
        else
            ps.setNull(17, Types.INTEGER);

        if (harborFacility.getAreaType() != null && harborFacility.getAreaType().isSetValue()) {
            ps.setString(18, harborFacility.getAreaType().getValue());
            ps.setString(19, harborFacility.getAreaType().getCodeSpace());
        } else {
            ps.setNull(18, Types.VARCHAR);
            ps.setNull(19, Types.VARCHAR);
        }

        if (harborFacility.getInnerArea() != null && harborFacility.getInnerArea().isSetValue()) {
            ps.setDouble(20, harborFacility.getInnerArea().getValue());
            ps.setString(21, harborFacility.getInnerArea().getUom());
        } else {
            ps.setNull(20, Types.DOUBLE);
            ps.setNull(21, Types.VARCHAR);
        }

        if (harborFacility.getOuterArea() != null && harborFacility.getOuterArea().isSetValue()) {
            ps.setDouble(22, harborFacility.getOuterArea().getValue());
            ps.setString(23, harborFacility.getOuterArea().getUom());
        } else {
            ps.setNull(22, Types.DOUBLE);
            ps.setNull(23, Types.VARCHAR);
        }

        if (harborFacility.getTotalCost() != null)
            ps.setInt(24, harborFacility.getTotalCost());
        else
            ps.setNull(24, Types.INTEGER);

        if (harborFacility.getSubsidy() != null)
            ps.setInt(25, harborFacility.getSubsidy());
        else
            ps.setNull(25, Types.INTEGER);

        if (!harborFacility.getNote().isEmpty())
            ps.setString(26, valueJoiner.join(harborFacility.getNote()));
        else
            ps.setNull(26, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.HARBORFACILITY));
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
