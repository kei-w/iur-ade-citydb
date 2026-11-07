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
import org.citygml4j.ade.iur.model.uro.MooringFacility;

import java.sql.*;

public class MooringFacilityImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public MooringFacilityImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.MOORINGFACILITY)) + " " +
                "(id, mainpartlength, mainpartlength_uom, totallength, totallength_uom, facilitywidth," +
                "facilitywidth_uom, apronwidth, apronwidth_uom, planneddepth, planneddepth_uom, currentdepth," +
                "currentdepth_uom, area, area_uom, ceilingheight, ceilingheight_uom, gravityresistant," +
                "gravityresistant_uom, form, form_codespace, mainvessels, mainvessels_codespace, mooringpostweight," +
                "mooringpostweight_uom, numberofmooringposts, resistantmaterial, lighting, stairs," +
                "lifesavingappliances, numberoflifesavingappliances, bumper, bumper_uom, numberofvehicleboardings," +
                "vehicleboardingwidth, vehicleboardingwidth_uom, shiptype, numberofseats, maincargo," +
                "maincargo_codespace, structuretype, structuretype_codespace, mainmaterial, mainmaterial_codespace," +
                "totalcost, subsidy, note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(MooringFacility mooringFacility, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (mooringFacility.getMainPartLength() != null && mooringFacility.getMainPartLength().isSetValue()) {
            ps.setDouble(2, mooringFacility.getMainPartLength().getValue());
            ps.setString(3, mooringFacility.getMainPartLength().getUom());
        } else {
            ps.setNull(2, Types.DOUBLE);
            ps.setNull(3, Types.VARCHAR);
        }

        if (mooringFacility.getTotalLength() != null && mooringFacility.getTotalLength().isSetValue()) {
            ps.setDouble(4, mooringFacility.getTotalLength().getValue());
            ps.setString(5, mooringFacility.getTotalLength().getUom());
        } else {
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.VARCHAR);
        }

        if (mooringFacility.getFacilityWidth() != null && mooringFacility.getFacilityWidth().isSetValue()) {
            ps.setDouble(6, mooringFacility.getFacilityWidth().getValue());
            ps.setString(7, mooringFacility.getFacilityWidth().getUom());
        } else {
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.VARCHAR);
        }

        if (mooringFacility.getApronWidth() != null && mooringFacility.getApronWidth().isSetValue()) {
            ps.setDouble(8, mooringFacility.getApronWidth().getValue());
            ps.setString(9, mooringFacility.getApronWidth().getUom());
        } else {
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.VARCHAR);
        }

        if (mooringFacility.getPlannedDepth() != null && mooringFacility.getPlannedDepth().isSetValue()) {
            ps.setDouble(10, mooringFacility.getPlannedDepth().getValue());
            ps.setString(11, mooringFacility.getPlannedDepth().getUom());
        } else {
            ps.setNull(10, Types.DOUBLE);
            ps.setNull(11, Types.VARCHAR);
        }

        if (mooringFacility.getCurrentDepth() != null && mooringFacility.getCurrentDepth().isSetValue()) {
            ps.setDouble(12, mooringFacility.getCurrentDepth().getValue());
            ps.setString(13, mooringFacility.getCurrentDepth().getUom());
        } else {
            ps.setNull(12, Types.DOUBLE);
            ps.setNull(13, Types.VARCHAR);
        }

        if (mooringFacility.getArea() != null && mooringFacility.getArea().isSetValue()) {
            ps.setDouble(14, mooringFacility.getArea().getValue());
            ps.setString(15, mooringFacility.getArea().getUom());
        } else {
            ps.setNull(14, Types.DOUBLE);
            ps.setNull(15, Types.VARCHAR);
        }

        if (mooringFacility.getCeilingHeight() != null && mooringFacility.getCeilingHeight().isSetValue()) {
            ps.setDouble(16, mooringFacility.getCeilingHeight().getValue());
            ps.setString(17, mooringFacility.getCeilingHeight().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (mooringFacility.getGravityResistant() != null && mooringFacility.getGravityResistant().isSetValue()) {
            ps.setDouble(18, mooringFacility.getGravityResistant().getValue());
            ps.setString(19, mooringFacility.getGravityResistant().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (mooringFacility.getForm() != null && mooringFacility.getForm().isSetValue()) {
            ps.setString(20, mooringFacility.getForm().getValue());
            ps.setString(21, mooringFacility.getForm().getCodeSpace());
        } else {
            ps.setNull(20, Types.VARCHAR);
            ps.setNull(21, Types.VARCHAR);
        }

        if (mooringFacility.getMainVessels() != null && mooringFacility.getMainVessels().isSetValue()) {
            ps.setString(22, mooringFacility.getMainVessels().getValue());
            ps.setString(23, mooringFacility.getMainVessels().getCodeSpace());
        } else {
            ps.setNull(22, Types.VARCHAR);
            ps.setNull(23, Types.VARCHAR);
        }

        if (mooringFacility.getMooringPostWeight() != null && mooringFacility.getMooringPostWeight().isSetValue()) {
            ps.setDouble(24, mooringFacility.getMooringPostWeight().getValue());
            ps.setString(25, mooringFacility.getMooringPostWeight().getUom());
        } else {
            ps.setNull(24, Types.DOUBLE);
            ps.setNull(25, Types.VARCHAR);
        }

        if (mooringFacility.getNumberOfMooringPosts() != null)
            ps.setInt(26, mooringFacility.getNumberOfMooringPosts());
        else
            ps.setNull(26, Types.INTEGER);

        if (mooringFacility.getResistantMaterial() != null)
            ps.setInt(27, mooringFacility.getResistantMaterial());
        else
            ps.setNull(27, Types.INTEGER);

        if (mooringFacility.getLighting() != null)
            ps.setInt(28, mooringFacility.getLighting());
        else
            ps.setNull(28, Types.INTEGER);

        if (mooringFacility.getStairs() != null)
            ps.setInt(29, mooringFacility.getStairs());
        else
            ps.setNull(29, Types.INTEGER);

        ps.setString(30, mooringFacility.getLifesavingAppliances());

        if (mooringFacility.getNumberOfLifesavingAppliances() != null)
            ps.setInt(31, mooringFacility.getNumberOfLifesavingAppliances());
        else
            ps.setNull(31, Types.INTEGER);

        if (mooringFacility.getBumper() != null && mooringFacility.getBumper().isSetValue()) {
            ps.setDouble(32, mooringFacility.getBumper().getValue());
            ps.setString(33, mooringFacility.getBumper().getUom());
        } else {
            ps.setNull(32, Types.DOUBLE);
            ps.setNull(33, Types.VARCHAR);
        }

        if (mooringFacility.getNumberOfVehicleBoardings() != null)
            ps.setInt(34, mooringFacility.getNumberOfVehicleBoardings());
        else
            ps.setNull(34, Types.INTEGER);

        if (mooringFacility.getVehicleBoardingWidth() != null && mooringFacility.getVehicleBoardingWidth().isSetValue()) {
            ps.setDouble(35, mooringFacility.getVehicleBoardingWidth().getValue());
            ps.setString(36, mooringFacility.getVehicleBoardingWidth().getUom());
        } else {
            ps.setNull(35, Types.DOUBLE);
            ps.setNull(36, Types.VARCHAR);
        }

        ps.setString(37, mooringFacility.getShipType());

        if (mooringFacility.getNumberOfSeats() != null)
            ps.setInt(38, mooringFacility.getNumberOfSeats());
        else
            ps.setNull(38, Types.INTEGER);

        if (mooringFacility.getMainCargo() != null && mooringFacility.getMainCargo().isSetValue()) {
            ps.setString(39, mooringFacility.getMainCargo().getValue());
            ps.setString(40, mooringFacility.getMainCargo().getCodeSpace());
        } else {
            ps.setNull(39, Types.VARCHAR);
            ps.setNull(40, Types.VARCHAR);
        }

        if (mooringFacility.getStructureType() != null && mooringFacility.getStructureType().isSetValue()) {
            ps.setString(41, mooringFacility.getStructureType().getValue());
            ps.setString(42, mooringFacility.getStructureType().getCodeSpace());
        } else {
            ps.setNull(41, Types.VARCHAR);
            ps.setNull(42, Types.VARCHAR);
        }

        if (mooringFacility.getMainMaterial() != null && mooringFacility.getMainMaterial().isSetValue()) {
            ps.setString(43, mooringFacility.getMainMaterial().getValue());
            ps.setString(44, mooringFacility.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(43, Types.VARCHAR);
            ps.setNull(44, Types.VARCHAR);
        }

        if (mooringFacility.getTotalCost() != null)
            ps.setInt(45, mooringFacility.getTotalCost());
        else
            ps.setNull(45, Types.INTEGER);

        if (mooringFacility.getSubsidy() != null)
            ps.setInt(46, mooringFacility.getSubsidy());
        else
            ps.setNull(46, Types.INTEGER);

        ps.setString(47, mooringFacility.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.MOORINGFACILITY));
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
