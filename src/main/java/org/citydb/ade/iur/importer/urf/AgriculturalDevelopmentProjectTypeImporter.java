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
import org.citydb.ade.iur.schema.ADESequence;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.importer.CityGMLImportHelper;
import org.citydb.core.operation.importer.CityGMLImportException;
import org.citygml4j.ade.iur.model.urf.AgriculturalDevelopmentProjectType;

import java.sql.*;
import java.time.LocalDate;

public class AgriculturalDevelopmentProjectTypeImporter implements UrbanFunctionModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public AgriculturalDevelopmentProjectTypeImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.AGRICULTURALDEVELOPMEN)) + " " +
                "(id, id_, blockname, projecttype, projecttype_codespace, implementationbody, benefitarea," +
                "benefitarea_uom, benefitareainurbanarea, benefitareainurbanarea_uom, startfiscalyear, status," +
                "status_codespace, measuretype, measuretype_codespace, benefitperiod, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public long doImport(AgriculturalDevelopmentProjectType agriculturalDevelopmentProjectType) throws CityGMLImportException, SQLException {
        long objectId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequence.AGRICULTURALDEVELO_SEQ));
        ps.setLong(1, objectId);

        ps.setString(2, agriculturalDevelopmentProjectType.getId());

        ps.setString(3, agriculturalDevelopmentProjectType.getBlockName());

        if (agriculturalDevelopmentProjectType.getProjectType() != null && agriculturalDevelopmentProjectType.getProjectType().isSetValue()) {
            ps.setString(4, agriculturalDevelopmentProjectType.getProjectType().getValue());
            ps.setString(5, agriculturalDevelopmentProjectType.getProjectType().getCodeSpace());
        } else {
            ps.setNull(4, Types.VARCHAR);
            ps.setNull(5, Types.VARCHAR);
        }

        ps.setString(6, agriculturalDevelopmentProjectType.getImplementationBody());

        if (agriculturalDevelopmentProjectType.getBenefitArea() != null && agriculturalDevelopmentProjectType.getBenefitArea().isSetValue()) {
            ps.setDouble(7, agriculturalDevelopmentProjectType.getBenefitArea().getValue());
            ps.setString(8, agriculturalDevelopmentProjectType.getBenefitArea().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (agriculturalDevelopmentProjectType.getBenefitAreaInUrbanArea() != null && agriculturalDevelopmentProjectType.getBenefitAreaInUrbanArea().isSetValue()) {
            ps.setDouble(9, agriculturalDevelopmentProjectType.getBenefitAreaInUrbanArea().getValue());
            ps.setString(10, agriculturalDevelopmentProjectType.getBenefitAreaInUrbanArea().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (agriculturalDevelopmentProjectType.getStartFiscalYear() != null)
            ps.setDate(11, Date.valueOf(LocalDate.of(agriculturalDevelopmentProjectType.getStartFiscalYear().getValue(), 1, 1)));
        else
            ps.setNull(11, Types.DATE);

        if (agriculturalDevelopmentProjectType.getStatus() != null && agriculturalDevelopmentProjectType.getStatus().isSetValue()) {
            ps.setString(12, agriculturalDevelopmentProjectType.getStatus().getValue());
            ps.setString(13, agriculturalDevelopmentProjectType.getStatus().getCodeSpace());
        } else {
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.VARCHAR);
        }

        if (agriculturalDevelopmentProjectType.getMeasureType() != null && agriculturalDevelopmentProjectType.getMeasureType().isSetValue()) {
            ps.setString(14, agriculturalDevelopmentProjectType.getMeasureType().getValue());
            ps.setString(15, agriculturalDevelopmentProjectType.getMeasureType().getCodeSpace());
        } else {
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
        }

        ps.setString(16, agriculturalDevelopmentProjectType.getBenefitPeriod());

        ps.setString(17, agriculturalDevelopmentProjectType.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.AGRICULTURALDEVELOPMEN));

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
