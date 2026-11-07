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
import org.citygml4j.ade.iur.model.urt.Agency;
import org.citygml4j.ade.iur.model.urt.AgencyProperty;
import org.citygml4j.ade.iur.model.urt.FareAttribute;

import java.sql.*;

public class FareAttributeImporter implements PublicTransitModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final PublicTransitImporter publicTransitImporter;
    private final FareAttributeToAgencyImporter fareAttributeToAgencyImporter;

    private int batchCounter;

    public FareAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.FAREATTRIBUTE)) + " " +
                "(id, price, currencytype, currencytype_codespace, paymentmethod, paymentmethod_codespace, transfers," +
                "transfers_codespace, transferduration) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        publicTransitImporter = manager.getImporter(PublicTransitImporter.class);
        fareAttributeToAgencyImporter = manager.getImporter(FareAttributeToAgencyImporter.class);
    }

    public void doImport(FareAttribute fareAttribute, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        publicTransitImporter.doImport(fareAttribute, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);

        if (fareAttribute.getPrice() != null)
            ps.setDouble(2, fareAttribute.getPrice());
        else
            ps.setNull(2, Types.DOUBLE);

        if (fareAttribute.getCurrencyType() != null && fareAttribute.getCurrencyType().isSetValue()) {
            ps.setString(3, fareAttribute.getCurrencyType().getValue());
            ps.setString(4, fareAttribute.getCurrencyType().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (fareAttribute.getPaymentMethod() != null && fareAttribute.getPaymentMethod().isSetValue()) {
            ps.setString(5, fareAttribute.getPaymentMethod().getValue());
            ps.setString(6, fareAttribute.getPaymentMethod().getCodeSpace());
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.VARCHAR);
        }

        if (fareAttribute.getTransfers() != null && fareAttribute.getTransfers().isSetValue()) {
            ps.setString(7, fareAttribute.getTransfers().getValue());
            ps.setString(8, fareAttribute.getTransfers().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        if (fareAttribute.getTransferDuration() != null)
            ps.setInt(9, fareAttribute.getTransferDuration());
        else
            ps.setNull(9, Types.INTEGER);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        if (fareAttribute.getAgency() != null) {
            AgencyProperty property = fareAttribute.getAgency();
            Agency cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                fareAttributeToAgencyImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.FAREATTRI_TO_AGENCY), objectId, "fareattribute_id", href, "agency_id");
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
