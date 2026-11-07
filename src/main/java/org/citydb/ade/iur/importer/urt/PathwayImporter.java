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

package org.citydb.ade.iur.importer.urt;

import org.citydb.ade.iur.importer.ImportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urt.Pathway;
import org.citygml4j.ade.iur.model.urt.Stop;
import org.citygml4j.ade.iur.model.urt.StopProperty;

import java.sql.*;

public class PathwayImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;
    private final PathwayToStopImporter pathwayToStopImporter;
    private final PathwayToToStopImporter pathwayToToStopImporter;

    private int batchCounter;

    public PathwayImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.PATHWAY)) + " " +
                "(id, mode_, mode__codespace, isbidirectional, isbidirectional_codespace, length, length_uom," +
                "traversaltime, staircount, maxslope, minwidth, signpostedas, reversedsignpostedas) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
        pathwayToStopImporter = manager.getImporter(PathwayToStopImporter.class);
        pathwayToToStopImporter = manager.getImporter(PathwayToToStopImporter.class);
    }

    public void doImport(Pathway pathway, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(pathway, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (pathway.getMode() != null && pathway.getMode().isSetValue()) {
            ps.setString(2, pathway.getMode().getValue());
            ps.setString(3, pathway.getMode().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        if (pathway.getIsBidirectional() != null && pathway.getIsBidirectional().isSetValue()) {
            ps.setString(4, pathway.getIsBidirectional().getValue());
            ps.setString(5, pathway.getIsBidirectional().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        if (pathway.getLength() != null && pathway.getLength().isSetValue()) {
            ps.setDouble(6, pathway.getLength().getValue());
            ps.setString(7, pathway.getLength().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (pathway.getTraversalTime() != null)
            ps.setInt(8, pathway.getTraversalTime());
        else
            ps.setNull(8, Types.INTEGER);

        if (pathway.getStairCount() != null)
            ps.setInt(9, pathway.getStairCount());
        else
            ps.setNull(9, Types.INTEGER);

        if (pathway.getMaxSlope() != null)
            ps.setDouble(10, pathway.getMaxSlope());
        else
            ps.setNull(10, Types.DOUBLE);

        if (pathway.getMinWidth() != null)
            ps.setDouble(11, pathway.getMinWidth());
        else
            ps.setNull(11, Types.DOUBLE);

        ps.setString(12, pathway.getSignpostedAs());

        ps.setString(13, pathway.getReversedSignpostedAs());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (pathway.getFrom() != null) {
            StopProperty property = pathway.getFrom();
            Stop cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                pathwayToStopImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.PATHWAY_TO_STOP), objectId, "pathway_id", href, "stop_id");
            }
        }
        if (pathway.getTo() != null) {
            StopProperty property = pathway.getTo();
            Stop cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                pathwayToToStopImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.PATHWAY_TO_STOP_2), objectId, "pathway_id", href, "stop_id");
            }
        }
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
