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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citydb.core.operation.importer.util.AttributeValueJoiner;
import org.citygml4j.ade.iur.model.uro.LandUseDetailAttribute;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;
import java.time.LocalDate;

public class LandUseDetailAttributeImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;
    private final AttributeValueJoiner valueJoiner;

    private int batchCounter;

    public LandUseDetailAttributeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.LANDUSEATTRIBUTE)) + " " +
                "(id, id_1, orglanduse, orglanduse_codespace, nominalarea, nominalarea_uom, ownertype," +
                "ownertype_codespace, owner, areainsquaremeter, areainsquaremeter_uom, areainha, areainha_uom," +
                "buildingcoveragerate, floorarearate, specifiedbuildingcoveragerat, specifiedfloorarearate," +
                "standardfloorarearate, urbanplantype, urbanplantype_codespace, areaclassificationtype," +
                "areaclassification_codespace, districtsandzonestype, districtsandzonest_codespace, prefecture," +
                "prefecture_codespace, city, city_codespace, reference, note, surveyyear) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        valueJoiner = helper.getAttributeValueJoiner();
    }

    public long doImport(LandUseDetailAttribute landUseDetailAttribute) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.LANDUSEATTRIBUTE_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, landUseDetailAttribute.getId());

        if (landUseDetailAttribute.getOrgLandUse() != null && landUseDetailAttribute.getOrgLandUse().isSetValue()) {
            ps.setString(3, landUseDetailAttribute.getOrgLandUse().getValue());
            ps.setString(4, landUseDetailAttribute.getOrgLandUse().getCodeSpace());
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        if (landUseDetailAttribute.getNominalArea() != null && landUseDetailAttribute.getNominalArea().isSetValue()) {
            ps.setDouble(5, landUseDetailAttribute.getNominalArea().getValue());
            ps.setString(6, landUseDetailAttribute.getNominalArea().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (landUseDetailAttribute.getOwnerType() != null && landUseDetailAttribute.getOwnerType().isSetValue()) {
            ps.setString(7, landUseDetailAttribute.getOwnerType().getValue());
            ps.setString(8, landUseDetailAttribute.getOwnerType().getCodeSpace());
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
        }

        ps.setString(9, landUseDetailAttribute.getOwner());

        if (landUseDetailAttribute.getAreaInSquareMeter() != null && landUseDetailAttribute.getAreaInSquareMeter().isSetValue()) {
            ps.setDouble(10, landUseDetailAttribute.getAreaInSquareMeter().getValue());
            ps.setString(11, landUseDetailAttribute.getAreaInSquareMeter().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (landUseDetailAttribute.getAreaInHa() != null && landUseDetailAttribute.getAreaInHa().isSetValue()) {
            ps.setDouble(12, landUseDetailAttribute.getAreaInHa().getValue());
            ps.setString(13, landUseDetailAttribute.getAreaInHa().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (landUseDetailAttribute.getBuildingCoverageRate() != null)
            ps.setDouble(14, landUseDetailAttribute.getBuildingCoverageRate());
        else
            ps.setNull(14, Types.DOUBLE);

        if (landUseDetailAttribute.getFloorAreaRate() != null)
            ps.setDouble(15, landUseDetailAttribute.getFloorAreaRate());
        else
            ps.setNull(15, Types.DOUBLE);

        if (landUseDetailAttribute.getSpecifiedBuildingCoverageRate() != null)
            ps.setDouble(16, landUseDetailAttribute.getSpecifiedBuildingCoverageRate());
        else
            ps.setNull(16, Types.DOUBLE);

        if (landUseDetailAttribute.getSpecifiedFloorAreaRate() != null)
            ps.setDouble(17, landUseDetailAttribute.getSpecifiedFloorAreaRate());
        else
            ps.setNull(17, Types.DOUBLE);

        if (landUseDetailAttribute.getStandardFloorAreaRate() != null)
            ps.setDouble(18, landUseDetailAttribute.getStandardFloorAreaRate());
        else
            ps.setNull(18, Types.DOUBLE);

        if (landUseDetailAttribute.getUrbanPlanType() != null && landUseDetailAttribute.getUrbanPlanType().isSetValue()) {
            ps.setString(19, landUseDetailAttribute.getUrbanPlanType().getValue());
            ps.setString(20, landUseDetailAttribute.getUrbanPlanType().getCodeSpace());
        } else {
            ps.setNull(19, Types.VARCHAR);
            ps.setNull(20, Types.VARCHAR);
        }

        if (landUseDetailAttribute.getAreaClassificationType() != null && landUseDetailAttribute.getAreaClassificationType().isSetValue()) {
            ps.setString(21, landUseDetailAttribute.getAreaClassificationType().getValue());
            ps.setString(22, landUseDetailAttribute.getAreaClassificationType().getCodeSpace());
        } else {
            ps.setNull(21, Types.VARCHAR);
            ps.setNull(22, Types.VARCHAR);
        }

        if (!landUseDetailAttribute.getDistrictsAndZonesType().isEmpty()) {
            valueJoiner.join(landUseDetailAttribute.getDistrictsAndZonesType(), Code::getValue, Code::getCodeSpace);
            ps.setString(23, valueJoiner.result(0));
            ps.setString(24, valueJoiner.result(1));
        } else {
            ps.setNull(23, Types.VARCHAR);
            ps.setNull(24, Types.VARCHAR);
        }

        if (landUseDetailAttribute.getPrefecture() != null && landUseDetailAttribute.getPrefecture().isSetValue()) {
            ps.setString(25, landUseDetailAttribute.getPrefecture().getValue());
            ps.setString(26, landUseDetailAttribute.getPrefecture().getCodeSpace());
        } else {
            ps.setNull(25, Types.VARCHAR);
            ps.setNull(26, Types.VARCHAR);
        }

        if (landUseDetailAttribute.getCity() != null && landUseDetailAttribute.getCity().isSetValue()) {
            ps.setString(27, landUseDetailAttribute.getCity().getValue());
            ps.setString(28, landUseDetailAttribute.getCity().getCodeSpace());
        } else {
            ps.setNull(27, Types.VARCHAR);
            ps.setNull(28, Types.VARCHAR);
        }

        ps.setString(29, landUseDetailAttribute.getReference());

        ps.setString(30, landUseDetailAttribute.getNote());

        if (landUseDetailAttribute.getSurveyYear() != null)
            ps.setDate(31, Date.valueOf(LocalDate.of(landUseDetailAttribute.getSurveyYear().getValue(), 1, 1)));
        else
            ps.setNull(31, Types.DATE);

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.LANDUSEATTRIBUTE));

        return objectId;
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
