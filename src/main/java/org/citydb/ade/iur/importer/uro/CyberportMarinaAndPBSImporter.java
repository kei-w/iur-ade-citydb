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
import org.citygml4j.ade.iur.model.uro.CyberportMarinaAndPBS;

import java.sql.*;

public class CyberportMarinaAndPBSImporter implements UrbanObjectModuleImporter {
    private final CityGMLImportHelper helper;
    private final SchemaMapper schemaMapper;
    private final PreparedStatement ps;

    private int batchCounter;

    public CyberportMarinaAndPBSImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
        this.helper = helper;
        this.schemaMapper = manager.getSchemaMapper();

        ps = connection.prepareStatement("insert into " +
                helper.getTableNameWithSchema(schemaMapper.getTableName(ADETable.CYBERPORTMARINAANDPBS)) + " " +
                "(id, geologicaltype, geologicaltype_codespace, obstructingstructures, mainpartlength," +
                "mainpartlength_uom, totallength, totallength_uom, wavedissipatorlength, wavedissipatorlength_uom," +
                "facilitywidth, facilitywidth_uom, apronwidth, apronwidth_uom, restrictionstructure, planneddepth," +
                "planneddepth_uom, currentdepth, currentdepth_uom, innertotalfloorarea, innertotalfloorarea_uom," +
                "innerofsitearea, innerofsitearea_uom, outeroftotalfloorarea, outeroftotalfloorarea_uom," +
                "outersitearea, outersitearea_uom, ceilingheight, ceilingheight_uom, gravityresistant," +
                "gravityresistant_uom, form, form_codespace, areatype, areatype_codespace, mainvessels," +
                "mainvessels_codespace, isdredged, mooringpostweight, mooringpostweight_uom, numberofmooringposts," +
                "resistantmaterial, lighting, stairs, lifesaving, lifesavingnumber, bumper, bumper_uom," +
                "numberofvehicleboardings, vehicleboardingwidth, vehicleboardingwidth_uom, shiptype, numberofseats," +
                "maincargo, maincargo_codespace, storagecapacity, storagecapacityunit, storagecapacityuni_codespace," +
                "structuretype, structuretype_codespace, mainmaterial, mainmaterial_codespace, totalcost, subsidy," +
                "note) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }

    public void doImport(CyberportMarinaAndPBS cyberportMarinaAndPBS, long objectId) throws CityGMLImportException, SQLException {
        ps.setLong(1, objectId);

        if (cyberportMarinaAndPBS.getGeologicalType() != null && cyberportMarinaAndPBS.getGeologicalType().isSetValue()) {
            ps.setString(2, cyberportMarinaAndPBS.getGeologicalType().getValue());
            ps.setString(3, cyberportMarinaAndPBS.getGeologicalType().getCodeSpace());
        } else {
            ps.setNull(2, Types.VARCHAR);
            ps.setNull(3, Types.VARCHAR);
        }

        ps.setString(4, cyberportMarinaAndPBS.getObstructingStructures());

        if (cyberportMarinaAndPBS.getMainPartLength() != null && cyberportMarinaAndPBS.getMainPartLength().isSetValue()) {
            ps.setDouble(5, cyberportMarinaAndPBS.getMainPartLength().getValue());
            ps.setString(6, cyberportMarinaAndPBS.getMainPartLength().getUom());
        } else {
            ps.setNull(5, Types.DOUBLE);
            ps.setNull(6, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getTotalLength() != null && cyberportMarinaAndPBS.getTotalLength().isSetValue()) {
            ps.setDouble(7, cyberportMarinaAndPBS.getTotalLength().getValue());
            ps.setString(8, cyberportMarinaAndPBS.getTotalLength().getUom());
        } else {
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getWaveDissipatorLength() != null && cyberportMarinaAndPBS.getWaveDissipatorLength().isSetValue()) {
            ps.setDouble(9, cyberportMarinaAndPBS.getWaveDissipatorLength().getValue());
            ps.setString(10, cyberportMarinaAndPBS.getWaveDissipatorLength().getUom());
        } else {
            ps.setNull(9, Types.DOUBLE);
            ps.setNull(10, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getFacilityWidth() != null && cyberportMarinaAndPBS.getFacilityWidth().isSetValue()) {
            ps.setDouble(11, cyberportMarinaAndPBS.getFacilityWidth().getValue());
            ps.setString(12, cyberportMarinaAndPBS.getFacilityWidth().getUom());
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getApronWidth() != null && cyberportMarinaAndPBS.getApronWidth().isSetValue()) {
            ps.setDouble(13, cyberportMarinaAndPBS.getApronWidth().getValue());
            ps.setString(14, cyberportMarinaAndPBS.getApronWidth().getUom());
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.VARCHAR);
        }

        ps.setString(15, cyberportMarinaAndPBS.getRestrictionStructure());

        if (cyberportMarinaAndPBS.getPlannedDepth() != null && cyberportMarinaAndPBS.getPlannedDepth().isSetValue()) {
            ps.setDouble(16, cyberportMarinaAndPBS.getPlannedDepth().getValue());
            ps.setString(17, cyberportMarinaAndPBS.getPlannedDepth().getUom());
        } else {
            ps.setNull(16, Types.DOUBLE);
            ps.setNull(17, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getCurrentDepth() != null && cyberportMarinaAndPBS.getCurrentDepth().isSetValue()) {
            ps.setDouble(18, cyberportMarinaAndPBS.getCurrentDepth().getValue());
            ps.setString(19, cyberportMarinaAndPBS.getCurrentDepth().getUom());
        } else {
            ps.setNull(18, Types.DOUBLE);
            ps.setNull(19, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getInnerTotalFloorArea() != null && cyberportMarinaAndPBS.getInnerTotalFloorArea().isSetValue()) {
            ps.setDouble(20, cyberportMarinaAndPBS.getInnerTotalFloorArea().getValue());
            ps.setString(21, cyberportMarinaAndPBS.getInnerTotalFloorArea().getUom());
        } else {
            ps.setNull(20, Types.DOUBLE);
            ps.setNull(21, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getInnerOfSiteArea() != null && cyberportMarinaAndPBS.getInnerOfSiteArea().isSetValue()) {
            ps.setDouble(22, cyberportMarinaAndPBS.getInnerOfSiteArea().getValue());
            ps.setString(23, cyberportMarinaAndPBS.getInnerOfSiteArea().getUom());
        } else {
            ps.setNull(22, Types.DOUBLE);
            ps.setNull(23, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getOuterOfTotalFloorArea() != null && cyberportMarinaAndPBS.getOuterOfTotalFloorArea().isSetValue()) {
            ps.setDouble(24, cyberportMarinaAndPBS.getOuterOfTotalFloorArea().getValue());
            ps.setString(25, cyberportMarinaAndPBS.getOuterOfTotalFloorArea().getUom());
        } else {
            ps.setNull(24, Types.DOUBLE);
            ps.setNull(25, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getOuterSiteArea() != null && cyberportMarinaAndPBS.getOuterSiteArea().isSetValue()) {
            ps.setDouble(26, cyberportMarinaAndPBS.getOuterSiteArea().getValue());
            ps.setString(27, cyberportMarinaAndPBS.getOuterSiteArea().getUom());
        } else {
            ps.setNull(26, Types.DOUBLE);
            ps.setNull(27, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getCeilingHeight() != null && cyberportMarinaAndPBS.getCeilingHeight().isSetValue()) {
            ps.setDouble(28, cyberportMarinaAndPBS.getCeilingHeight().getValue());
            ps.setString(29, cyberportMarinaAndPBS.getCeilingHeight().getUom());
        } else {
            ps.setNull(28, Types.DOUBLE);
            ps.setNull(29, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getGravityResistant() != null && cyberportMarinaAndPBS.getGravityResistant().isSetValue()) {
            ps.setDouble(30, cyberportMarinaAndPBS.getGravityResistant().getValue());
            ps.setString(31, cyberportMarinaAndPBS.getGravityResistant().getUom());
        } else {
            ps.setNull(30, Types.DOUBLE);
            ps.setNull(31, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getForm() != null && cyberportMarinaAndPBS.getForm().isSetValue()) {
            ps.setString(32, cyberportMarinaAndPBS.getForm().getValue());
            ps.setString(33, cyberportMarinaAndPBS.getForm().getCodeSpace());
        } else {
            ps.setNull(32, Types.VARCHAR);
            ps.setNull(33, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getAreaType() != null && cyberportMarinaAndPBS.getAreaType().isSetValue()) {
            ps.setString(34, cyberportMarinaAndPBS.getAreaType().getValue());
            ps.setString(35, cyberportMarinaAndPBS.getAreaType().getCodeSpace());
        } else {
            ps.setNull(34, Types.VARCHAR);
            ps.setNull(35, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getMainVessels() != null && cyberportMarinaAndPBS.getMainVessels().isSetValue()) {
            ps.setString(36, cyberportMarinaAndPBS.getMainVessels().getValue());
            ps.setString(37, cyberportMarinaAndPBS.getMainVessels().getCodeSpace());
        } else {
            ps.setNull(36, Types.VARCHAR);
            ps.setNull(37, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getIsDredged() != null)
            ps.setInt(38, cyberportMarinaAndPBS.getIsDredged() ? 1 : 0);
        else
            ps.setNull(38, Types.INTEGER);

        if (cyberportMarinaAndPBS.getMooringPostWeight() != null && cyberportMarinaAndPBS.getMooringPostWeight().isSetValue()) {
            ps.setDouble(39, cyberportMarinaAndPBS.getMooringPostWeight().getValue());
            ps.setString(40, cyberportMarinaAndPBS.getMooringPostWeight().getUom());
        } else {
            ps.setNull(39, Types.DOUBLE);
            ps.setNull(40, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getNumberOfMooringPosts() != null)
            ps.setInt(41, cyberportMarinaAndPBS.getNumberOfMooringPosts());
        else
            ps.setNull(41, Types.INTEGER);

        if (cyberportMarinaAndPBS.getResistantMaterial() != null)
            ps.setInt(42, cyberportMarinaAndPBS.getResistantMaterial());
        else
            ps.setNull(42, Types.INTEGER);

        if (cyberportMarinaAndPBS.getLighting() != null)
            ps.setInt(43, cyberportMarinaAndPBS.getLighting());
        else
            ps.setNull(43, Types.INTEGER);

        if (cyberportMarinaAndPBS.getStairs() != null)
            ps.setInt(44, cyberportMarinaAndPBS.getStairs());
        else
            ps.setNull(44, Types.INTEGER);

        ps.setString(45, cyberportMarinaAndPBS.getLifesaving());

        if (cyberportMarinaAndPBS.getLifesavingNumber() != null)
            ps.setInt(46, cyberportMarinaAndPBS.getLifesavingNumber());
        else
            ps.setNull(46, Types.INTEGER);

        if (cyberportMarinaAndPBS.getBumper() != null && cyberportMarinaAndPBS.getBumper().isSetValue()) {
            ps.setDouble(47, cyberportMarinaAndPBS.getBumper().getValue());
            ps.setString(48, cyberportMarinaAndPBS.getBumper().getUom());
        } else {
            ps.setNull(47, Types.DOUBLE);
            ps.setNull(48, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getNumberOfVehicleBoardings() != null)
            ps.setInt(49, cyberportMarinaAndPBS.getNumberOfVehicleBoardings());
        else
            ps.setNull(49, Types.INTEGER);

        if (cyberportMarinaAndPBS.getVehicleBoardingWidth() != null && cyberportMarinaAndPBS.getVehicleBoardingWidth().isSetValue()) {
            ps.setDouble(50, cyberportMarinaAndPBS.getVehicleBoardingWidth().getValue());
            ps.setString(51, cyberportMarinaAndPBS.getVehicleBoardingWidth().getUom());
        } else {
            ps.setNull(50, Types.DOUBLE);
            ps.setNull(51, Types.VARCHAR);
        }

        ps.setString(52, cyberportMarinaAndPBS.getShipType());

        if (cyberportMarinaAndPBS.getNumberOfSeats() != null)
            ps.setInt(53, cyberportMarinaAndPBS.getNumberOfSeats());
        else
            ps.setNull(53, Types.INTEGER);

        if (cyberportMarinaAndPBS.getMainCargo() != null && cyberportMarinaAndPBS.getMainCargo().isSetValue()) {
            ps.setString(54, cyberportMarinaAndPBS.getMainCargo().getValue());
            ps.setString(55, cyberportMarinaAndPBS.getMainCargo().getCodeSpace());
        } else {
            ps.setNull(54, Types.VARCHAR);
            ps.setNull(55, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getStorageCapacity() != null)
            ps.setInt(56, cyberportMarinaAndPBS.getStorageCapacity());
        else
            ps.setNull(56, Types.INTEGER);

        if (cyberportMarinaAndPBS.getStorageCapacityUnit() != null && cyberportMarinaAndPBS.getStorageCapacityUnit().isSetValue()) {
            ps.setString(57, cyberportMarinaAndPBS.getStorageCapacityUnit().getValue());
            ps.setString(58, cyberportMarinaAndPBS.getStorageCapacityUnit().getCodeSpace());
        } else {
            ps.setNull(57, Types.VARCHAR);
            ps.setNull(58, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getStructureType() != null && cyberportMarinaAndPBS.getStructureType().isSetValue()) {
            ps.setString(59, cyberportMarinaAndPBS.getStructureType().getValue());
            ps.setString(60, cyberportMarinaAndPBS.getStructureType().getCodeSpace());
        } else {
            ps.setNull(59, Types.VARCHAR);
            ps.setNull(60, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getMainMaterial() != null && cyberportMarinaAndPBS.getMainMaterial().isSetValue()) {
            ps.setString(61, cyberportMarinaAndPBS.getMainMaterial().getValue());
            ps.setString(62, cyberportMarinaAndPBS.getMainMaterial().getCodeSpace());
        } else {
            ps.setNull(61, Types.VARCHAR);
            ps.setNull(62, Types.VARCHAR);
        }

        if (cyberportMarinaAndPBS.getTotalCost() != null)
            ps.setInt(63, cyberportMarinaAndPBS.getTotalCost());
        else
            ps.setNull(63, Types.INTEGER);

        if (cyberportMarinaAndPBS.getSubsidy() != null)
            ps.setInt(64, cyberportMarinaAndPBS.getSubsidy());
        else
            ps.setNull(64, Types.INTEGER);

        ps.setString(65, cyberportMarinaAndPBS.getNote());

        ps.addBatch();
        if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
            helper.executeBatch(schemaMapper.getTableName(ADETable.CYBERPORTMARINAANDPBS));
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
