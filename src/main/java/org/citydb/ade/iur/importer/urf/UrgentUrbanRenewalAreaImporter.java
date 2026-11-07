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
import org.citygml4j.ade.iur.model.urf.PrivateUrbanRenewalProjectPlan;
import org.citygml4j.ade.iur.model.urf.PrivateUrbanRenewalProjectPlanProperty;
import org.citygml4j.ade.iur.model.urf.SpecialUrbanRenaissanceDistrict;
import org.citygml4j.ade.iur.model.urf.SpecialUrbanRenaissanceDistrictProperty;
import org.citygml4j.ade.iur.model.urf.SpecifiedUrgentUrbanRenewalArea;
import org.citygml4j.ade.iur.model.urf.SpecifiedUrgentUrbanRenewalAreaProperty;
import org.citygml4j.ade.iur.model.urf.UrgentUrbanRenewalArea;

import java.sql.*;

public class UrgentUrbanRenewalAreaImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ZoneImporter zoneImporter;
    private final UrgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter urgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter;
    private final UrgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter urgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter;
    private final UrgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter urgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter;

    private int batchCounter;

    public UrgentUrbanRenewalAreaImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.URGENTURBANRENEWALAREA)) + " " +
                "(id, objectclass_id, developmentpolicy) " +
                "values (?, ?, ?)");

        zoneImporter = manager.getImporter(ZoneImporter.class);
        urgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter = manager.getImporter(UrgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter.class);
        urgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter = manager.getImporter(UrgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter.class);
        urgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter = manager.getImporter(UrgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter.class);
    }

    public void doImport(UrgentUrbanRenewalArea urgentUrbanRenewalArea, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        zoneImporter.doImport(urgentUrbanRenewalArea, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        if (urgentUrbanRenewalArea.getDevelopmentPolicy() != null && urgentUrbanRenewalArea.getDevelopmentPolicy().isSetValue())
            ps.setString(3, urgentUrbanRenewalArea.getDevelopmentPolicy().getValue());
        else
            ps.setNull(3, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (PrivateUrbanRenewalProjectPlanProperty property : urgentUrbanRenewalArea.getPrivateProject()) {
            PrivateUrbanRenewalProjectPlan cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                urgentUrbanRenewalAreaToPrivateUrbanRenewalProjectPlanImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.URGENTURB_TO_PRIVATEUR), objectId, "urgenturbanrenewalarea_id", href, "privateurbanrenewalpro_id");
            }
        }
        for (SpecifiedUrgentUrbanRenewalAreaProperty property : urgentUrbanRenewalArea.getSpecifiedArea()) {
            SpecifiedUrgentUrbanRenewalArea cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                urgentUrbanRenewalAreaToSpecifiedUrgentUrbanRenewalAreaImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.URGENTURB_TO_SPECIFIED), objectId, "urgenturbanrenewalarea_id", href, "specifiedurgenturbanre_id");
            }
        }
        for (SpecialUrbanRenaissanceDistrictProperty property : urgentUrbanRenewalArea.getSpecialDistrict()) {
            SpecialUrbanRenaissanceDistrict cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                urgentUrbanRenewalAreaToSpecialUrbanRenaissanceDistrictImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.URGENTURB_TO_SPECIALUR), objectId, "urgenturbanrenewalarea_id", href, "specialurbanrenaissanc_id");
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
