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
import org.citygml4j.ade.iur.model.uro.IfcMapConversion;

import java.sql.*;

public class IfcMapConversionImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ImportManager manager;
    private IfcCoordinateReferenceSystemSelectImporter ifcCoordinateReferenceSystemSelectImporter;
    private IfcAttributeImporter ifcAttributeImporter;

    private int batchCounter;

    public IfcMapConversionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.manager = manager;
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.IFCMAPCONVERSION)) + " " +
                "(id, sourcecrs_id, targetcrs_id, eastings, eastings_uom, northings, northings_uom, orthogonalheight," +
                "orthogonalheight_uom, xaxisabscissa, xaxisordinate, scale) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(IfcMapConversion ifcMapConversion, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        long sourceCRSId = 0;
        if (ifcMapConversion.getSourceCRS() != null)
            sourceCRSId = ifcCoordinateReferenceSystemSelectImporter().doImport(ifcMapConversion.getSourceCRS());

        if (sourceCRSId != 0)
            ps.setLong(2, sourceCRSId);
        else
            ps.setNull(2, Types.NULL);

        long targetCRSId = 0;
        if (ifcMapConversion.getTargetCRS() != null && ifcMapConversion.getTargetCRS().isSetObject())
            targetCRSId = ifcAttributeImporter().doImport(ifcMapConversion.getTargetCRS().getObject());

        if (targetCRSId != 0)
            ps.setLong(3, targetCRSId);
        else
            ps.setNull(3, Types.NULL);

        if (ifcMapConversion.getEastings() != null && ifcMapConversion.getEastings().isSetValue()) {
            ps.setDouble(4, ifcMapConversion.getEastings().getValue());
            ps.setString(5, ifcMapConversion.getEastings().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (ifcMapConversion.getNorthings() != null && ifcMapConversion.getNorthings().isSetValue()) {
            ps.setDouble(6, ifcMapConversion.getNorthings().getValue());
            ps.setString(7, ifcMapConversion.getNorthings().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (ifcMapConversion.getOrthogonalHeight() != null && ifcMapConversion.getOrthogonalHeight().isSetValue()) {
            ps.setDouble(8, ifcMapConversion.getOrthogonalHeight().getValue());
            ps.setString(9, ifcMapConversion.getOrthogonalHeight().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (ifcMapConversion.getXAxisAbscissa() != null)
            ps.setDouble(10, ifcMapConversion.getXAxisAbscissa());
        else
            ps.setNull(10, Types.DOUBLE);

        if (ifcMapConversion.getXAxisOrdinate() != null)
            ps.setDouble(11, ifcMapConversion.getXAxisOrdinate());
        else
            ps.setNull(11, Types.DOUBLE);

        if (ifcMapConversion.getScale() != null)
            ps.setDouble(12, ifcMapConversion.getScale());
        else
            ps.setNull(12, Types.DOUBLE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.IFCMAPCONVERSION));
    }

    private IfcCoordinateReferenceSystemSelectImporter ifcCoordinateReferenceSystemSelectImporter() throws CityGMLImportException, SQLException {
        if (ifcCoordinateReferenceSystemSelectImporter == null)
            ifcCoordinateReferenceSystemSelectImporter = manager.getImporter(IfcCoordinateReferenceSystemSelectImporter.class);

        return ifcCoordinateReferenceSystemSelectImporter;
    }

    private IfcAttributeImporter ifcAttributeImporter() throws CityGMLImportException, SQLException {
        if (ifcAttributeImporter == null)
            ifcAttributeImporter = manager.getImporter(IfcAttributeImporter.class);

        return ifcAttributeImporter;
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
