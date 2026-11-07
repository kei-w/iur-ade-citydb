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
import org.citygml4j.ade.iur.model.urf.AbstractDistrictPlan;
import org.citygml4j.ade.iur.model.urf.DistrictDevelopmentPlan;
import org.citygml4j.ade.iur.model.urf.DistrictDevelopmentPlanProperty;
import org.citygml4j.ade.iur.model.urf.PromotionDistrict;
import org.citygml4j.ade.iur.model.urf.PromotionDistrictProperty;

import java.sql.*;

public class AbstractDistrictPlanImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final ZoneImporter zoneImporter;
    private final AbstractDistrictPlanToDistrictDevelopmentPlanImporter abstractDistrictPlanToDistrictDevelopmentPlanImporter;
    private final AbstractDistrictPlanToPromotionDistrictImporter abstractDistrictPlanToPromotionDistrictImporter;

    private int batchCounter;

    public AbstractDistrictPlanImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.ABSTRACTDISTRICTPLAN)) + " " +
                "(id, objectclass_id, objectives, policy) " +
                "values (?, ?, ?, ?)");

        zoneImporter = manager.getImporter(ZoneImporter.class);
        abstractDistrictPlanToDistrictDevelopmentPlanImporter = manager.getImporter(AbstractDistrictPlanToDistrictDevelopmentPlanImporter.class);
        abstractDistrictPlanToPromotionDistrictImporter = manager.getImporter(AbstractDistrictPlanToPromotionDistrictImporter.class);
    }

    public void doImport(AbstractDistrictPlan abstractDistrictPlan, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
        zoneImporter.doImport(abstractDistrictPlan, objectId, objectType, foreignKeys);

        ps.setLong(1, objectId);
        ps.setInt(2, objectType.getObjectClassId());

        if (abstractDistrictPlan.getObjectives() != null && abstractDistrictPlan.getObjectives().isSetValue())
            ps.setString(3, abstractDistrictPlan.getObjectives().getValue());
        else
            ps.setNull(3, Types.VARCHAR);

        if (abstractDistrictPlan.getPolicy() != null && abstractDistrictPlan.getPolicy().isSetValue())
            ps.setString(4, abstractDistrictPlan.getPolicy().getValue());
        else
            ps.setNull(4, Types.VARCHAR);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(objectType);

        for (DistrictDevelopmentPlanProperty property : abstractDistrictPlan.getDistrictDevelopmentPlan()) {
            DistrictDevelopmentPlan cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                abstractDistrictPlanToDistrictDevelopmentPlanImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.ABSTRACTD_TO_DISTRICTD), objectId, "abstractdistrictplan_id", href, "districtdevelopmentpla_id");
            }
        }
        for (PromotionDistrictProperty property : abstractDistrictPlan.getPromotionDistrict()) {
            PromotionDistrict cityObject = property.getObject();
            if (cityObject != null) {
                long cityObjectId = helper.importObject(cityObject);
                property.unsetObject();
                abstractDistrictPlanToPromotionDistrictImporter.doImport(cityObjectId, objectId);
            } else {
                String href = property.getHref();
                if (href != null && !href.isEmpty())
                    helper.propagateObjectXlink(schemaMapper.getTableName(ADETable.ABSTRACTD_TO_ZONE), objectId, "abstractdistrictplan_id", href, "zone_id");
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
