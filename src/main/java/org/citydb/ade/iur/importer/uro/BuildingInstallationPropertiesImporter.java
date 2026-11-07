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
import org.citydb.core.ade.importer.ADEPropertyCollection;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.database.schema.mapping.FeatureType;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.uro.*;
import org.citygml4j.model.citygml.core.AbstractCityObject;

import java.sql.*;

public class BuildingInstallationPropertiesImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private final IfcAttributeImporter ifcAttributeImporter;
    private final IndoorAttributeImporter indoorAttributeImporter;

    private int batchCounter;

    public BuildingInstallationPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.BUILDING_INSTALLATION)) + " " +
                "(id, ifcbuildinginstallationat_id, ifcintbuildinginstallatio_id, indoorintinstallationattr_id) " +
                "values (?, ?, ?, ?)");

        ifcAttributeImporter = manager.getImporter(IfcAttributeImporter.class);
        indoorAttributeImporter = manager.getImporter(IndoorAttributeImporter.class);
    }

    public void doImport(ADEPropertyCollection properties, AbstractCityObject parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
        ps.setLong(1, parentId);

        long ifcBuildingInstallationAttributeId = 0;
        if (properties.contains(IfcBuildingInstallationAttributePropertyElement.class)) {
            IfcBuildingInstallationAttributePropertyElement property = properties.getFirst(IfcBuildingInstallationAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                ifcBuildingInstallationAttributeId = ifcAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (ifcBuildingInstallationAttributeId != 0)
            ps.setLong(2, ifcBuildingInstallationAttributeId);
        else
            ps.setNull(2, Types.NULL);

        long ifcIntBuildingInstallationAttributeId = 0;
        if (properties.contains(IfcIntBuildingInstallationAttributePropertyElement.class)) {
            IfcIntBuildingInstallationAttributePropertyElement property = properties.getFirst(IfcIntBuildingInstallationAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                ifcIntBuildingInstallationAttributeId = ifcAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (ifcIntBuildingInstallationAttributeId != 0)
            ps.setLong(3, ifcIntBuildingInstallationAttributeId);
        else
            ps.setNull(3, Types.NULL);

        long indoorIntInstallationAttributeId = 0;
        if (properties.contains(IndoorIntInstallationAttributePropertyElement.class)) {
            IndoorIntInstallationAttributePropertyElement property = properties.getFirst(IndoorIntInstallationAttributePropertyElement.class);
            if (property.isSetValue() && property.getValue().isSetObject()) {
                indoorIntInstallationAttributeId = indoorAttributeImporter.doImport(property.getValue().getObject());
                property.setValue(null);
            }
        }

        if (indoorIntInstallationAttributeId != 0)
            ps.setLong(4, indoorIntInstallationAttributeId);
        else
            ps.setNull(4, Types.NULL);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.BUILDING_INSTALLATION));
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
