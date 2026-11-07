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
import org.citydb.core.ade.importer.ForeignKeys;
import org.citydb.core.database.schema.mapping.AbstractObjectType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.Duct;
import org.citygml4j.ade.iur.model.uro.LengthAttributeProperty;
import org.citygml4j.ade.iur.model.uro.UtilityLink;

import java.sql.*;

public class UtilityLinkImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final UtilityNetworkElementImporter utilityNetworkElementImporter;
    private final UtilityLinkToLengthAttributeImporter utilityLinkToLengthAttributeImporter;
    private final LengthAttributeImporter lengthAttributeImporter;

    private int batchCounter;

    public UtilityLinkImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.UTILITYLINK)) + " " +
                "(id, objectclass_id, routestartnode, startnode, routeendnode, endnode, depth, depth_uom, mindepth," +
                "mindepth_uom, maxdepth, maxdepth_uom, maxwidth, maxwidth_uom, offset_, offset_uom, material," +
                "material_codespace, width, width_uom) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        utilityNetworkElementImporter = manager.getImporter(UtilityNetworkElementImporter.class);
        utilityLinkToLengthAttributeImporter = manager.getImporter(UtilityLinkToLengthAttributeImporter.class);
        lengthAttributeImporter = manager.getImporter(LengthAttributeImporter.class);
    }

    public void doImport(UtilityLink utilityLink, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        utilityNetworkElementImporter.doImport(utilityLink, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        ps.setString(3, utilityLink.getRouteStartNode());

        ps.setString(4, utilityLink.getStartNode());

        ps.setString(5, utilityLink.getRouteEndNode());

        ps.setString(6, utilityLink.getEndNode());

        if (utilityLink.getDepth() != null && utilityLink.getDepth().isSetValue()) {
            ps.setDouble(7, utilityLink.getDepth().getValue());
            ps.setString(8, utilityLink.getDepth().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (utilityLink.getMinDepth() != null && utilityLink.getMinDepth().isSetValue()) {
            ps.setDouble(9, utilityLink.getMinDepth().getValue());
            ps.setString(10, utilityLink.getMinDepth().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (utilityLink.getMaxDepth() != null && utilityLink.getMaxDepth().isSetValue()) {
            ps.setDouble(11, utilityLink.getMaxDepth().getValue());
            ps.setString(12, utilityLink.getMaxDepth().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (utilityLink.getMaxWidth() != null && utilityLink.getMaxWidth().isSetValue()) {
            ps.setDouble(13, utilityLink.getMaxWidth().getValue());
            ps.setString(14, utilityLink.getMaxWidth().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        if (utilityLink.getOffset() != null && utilityLink.getOffset().isSetValue()) {
            ps.setDouble(15, utilityLink.getOffset().getValue());
            ps.setString(16, utilityLink.getOffset().getUom());
        } else {
            ps.setNull(15, Types.DOUBLE);
            ps.setNull(16, Types.VARCHAR);
        }

        if (utilityLink.getMaterial() != null && utilityLink.getMaterial().isSetValue()) {
            ps.setString(17, utilityLink.getMaterial().getValue());
            ps.setString(18, utilityLink.getMaterial().getCodeSpace());
        } else {
            ps.setNull(17, Types.VARCHAR);
            ps.setNull(18, Types.VARCHAR);
        }

        if (utilityLink instanceof Duct) {
            Duct duct = (Duct) utilityLink;
            if (duct.getWidth() != null && duct.getWidth().isSetValue()) {
                ps.setDouble(19, duct.getWidth().getValue());
                ps.setString(20, duct.getWidth().getUom());
            } else {
                ps.setNull(19, Types.DOUBLE);
                ps.setNull(20, Types.VARCHAR);
            }
        } else {
            ps.setNull(19, Types.DOUBLE);
            ps.setNull(20, Types.VARCHAR);
        }

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (LengthAttributeProperty property : utilityLink.getLengthAttribute()) {
            if (property.isSetObject()) {
                long lengthAttributeId = lengthAttributeImporter.doImport(property.getObject());
                utilityLinkToLengthAttributeImporter.doImport(lengthAttributeId, objectId);
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
