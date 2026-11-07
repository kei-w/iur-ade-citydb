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

package org.citydb.ade.iur.exporter.uro;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.CargoHandlingFacility;
import org.citygml4j.ade.iur.model.uro.CyberportMarinaAndPBS;
import org.citygml4j.ade.iur.model.uro.FacilityAttribute;
import org.citygml4j.ade.iur.model.uro.FishingPortCapacity;
import org.citygml4j.ade.iur.model.uro.FishingPortFacility;
import org.citygml4j.ade.iur.model.uro.HarborFacility;
import org.citygml4j.ade.iur.model.uro.LongevityMeasures;
import org.citygml4j.ade.iur.model.uro.LongevityMeasuresProperty;
import org.citygml4j.ade.iur.model.uro.MaintenanceHistoryAttribute;
import org.citygml4j.ade.iur.model.uro.MooringFacility;
import org.citygml4j.ade.iur.model.uro.NavigationAssistanceFacility;
import org.citygml4j.ade.iur.model.uro.NumberOfFacilities;
import org.citygml4j.ade.iur.model.uro.NumberOfFacilitiesProperty;
import org.citygml4j.ade.iur.model.uro.ParkFacilityLongevityPlanAttribute;
import org.citygml4j.ade.iur.model.uro.ParkHealthAssessment;
import org.citygml4j.ade.iur.model.uro.ParkHealthAssessmentProperty;
import org.citygml4j.ade.iur.model.uro.PortAttribute;
import org.citygml4j.ade.iur.model.uro.PortEnvironmentalImprovementFacility;
import org.citygml4j.ade.iur.model.uro.PortManagementFacility;
import org.citygml4j.ade.iur.model.uro.PortPassengerFacility;
import org.citygml4j.ade.iur.model.uro.PortPollutionControlFacility;
import org.citygml4j.ade.iur.model.uro.PortProtectiveFacility;
import org.citygml4j.ade.iur.model.uro.PortStorageFacility;
import org.citygml4j.ade.iur.model.uro.PortTransportationFacility;
import org.citygml4j.ade.iur.model.uro.PortWasteTreatmentFacility;
import org.citygml4j.ade.iur.model.uro.PortWelfareFacility;
import org.citygml4j.ade.iur.model.uro.RepairsBeforeParkHealthAssessment;
import org.citygml4j.ade.iur.model.uro.RepairsBeforeParkHealthAssessmentProperty;
import org.citygml4j.ade.iur.model.uro.ShipServiceFacility;
import org.citygml4j.model.gml.base.StringOrRef;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.*;

public class FacilityAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final Map<String, PreparedStatement> psByParent = new HashMap<>();
    private final NumberOfFacilitiesExporter numberOfFacilitiesExporter;
    private final RepairsBeforeParkHealthAssessmentExporter repairsBeforeParkHealthAssessmentExporter;
    private final ParkHealthAssessmentExporter parkHealthAssessmentExporter;
    private final LongevityMeasuresExporter longevityMeasuresExporter;
    private final AttributeValueSplitter valueSplitter;

    public FacilityAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.FACILITYATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table fishingPortAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FISHINGPORTATTRIBUTE)));
        Table fishingPortCapacity = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FISHINGPORTCAPACITY)));
        Table fishingPortFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FISHINGPORTFACILITY)));
        Table maintenanceHistoryAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.MAINTENANCEHISTORYATTR)));
        Table parkFacilityLongevityPlanAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PARKFACILITYLONGEVITYP)));
        Table portAttribute = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTATTRIBUTE)));
        Table cargoHandlingFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CARGOHANDLINGFACILITY)));
        Table cyberportMarinaAndPBS = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.CYBERPORTMARINAANDPBS)));
        Table harborFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.HARBORFACILITY)));
        Table mooringFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.MOORINGFACILITY)));
        Table navigationAssistanceFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.NAVIGATIONASSISTANCEFA)));
        Table portEnvironmentalImprovementFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTENVIRONMENTALIMPRO)));
        Table portManagementFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTMANAGEMENTFACILITY)));
        Table portPassengerFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTPASSENGERFACILITY)));
        Table portPollutionControlFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTPOLLUTIONCONTROLFA)));
        Table portProtectiveFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTPROTECTIVEFACILITY)));
        Table portStorageFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTSTORAGEFACILITY)));
        Table portTransportationFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTTRANSPORTATIONFACI)));
        Table portWasteTreatmentFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTWASTETREATMENTFACI)));
        Table portWelfareFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.PORTWELFAREFACILITY)));
        Table shipServiceFacility = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.SHIPSERVICEFACILITY)));
        for (String parentColumn : Arrays.asList("construction_consfacility_id", "building_bldgfacilityattr_id", "bridge_bridfacilityattrib_id", "city_furniture_frnfacilit_id", "land_use_lusefacilityattr_id", "transpor_complex_tranfaci_id", "tunnel_tunfacilityattribu_id", "cityobject_vegfacilityatt_id", "waterbody_wtrfacilityattr_id")) {
            Select parentSelect = new Select().addProjection(table.getColumns("id", "objectclass_id", "facilityid"))
                    .addProjection(fishingPortCapacity.getColumns("capacity", "weightcapacity", "weightcapacity_uom", "hullform", "shipnumber", "waterdepth_2m",
                            "waterdepth_2m_uom", "waterdepth2_3m", "waterdepth2_3m_uom", "waterdepth3_6m",
                            "waterdepth3_6m_uom", "waterdepth6_m", "waterdepth6_m_uom", "heightaboveawl", "heightaboveawl_uom",
                            "heightonfoundations", "heightonfoundations_uom", "luminousrange", "luminousrange_uom",
                            "luminouscolor", "candlepower", "lighttype", "period", "maximumgroundingweight", "handleablepower",
                            "maximumwatersupply", "maximumrefueling", "people", "other"))
                    .addProjection(fishingPortFacility.getColumns("facilitydetailstype", "facilitydetailstyp_codespace", "porttype", "porttype_codespace", "address",
                            "designatedarea", "designation", "designatedadministrator", "referencenumber", "administrator",
                            "facilitymanager", "otherstructure", "depth", "depth_uom", "othersizedescription",
                            "dateofconstructionoracquisit", "cost"))
                    .addProjection(maintenanceHistoryAttribute.getColumns("maintenancetype", "maintenancetype_codespace", "maintenancefiscalyear", "maintenanceyear",
                            "maintenancedate", "status", "description"))
                    .addProjection(parkFacilityLongevityPlanAttribute.getColumns("parkcode", "parkcode_codespace", "parkname", "parkname_codespace", "parktype",
                            "parktype_codespace", "facilityname", "facilityname_codespace", "facilitynameoptional",
                            "specificfacilityname", "numberoffacilities_id", "size_", "mainmaterialoptional",
                            "installationyear", "disposallimitperiod", "expectedusageperiod", "repairsbeforeparkhealthas_id",
                            "managementtype", "managementtype_codespace", "expectedrenewalyearwithmeasu", "noteforlongevity"))
                    .addProjection(portAttribute.getColumns("portfacilitydetailstype", "portfacilitydetail_codespace", "portstatus", "portstatus_codespace",
                            "district", "isdesignated", "degradationlevel"))
                    .addProjection(cargoHandlingFacility.getColumns("mooringfacility", "liftableload", "liftableload_uom", "ability", "packingname",
                            "packingname_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumns("restrictionstructure", "lifesaving", "lifesavingnumber"))
                    .addProjection(harborFacility.getColumns("structurallimitations", "structurallimitations_uom", "maximumwidth", "maximumwidth_uom",
                            "innerarea", "innerarea_uom", "outerarea", "outerarea_uom"))
                    .addProjection(mooringFacility.getColumns("lifesavingappliances", "numberoflifesavingappliances"))
                    .addProjection(portManagementFacility.getColumns("numberofshiptypes", "unitofshiptype", "unitofshiptype_codespace", "loadingcapacity"))
                    .addProjection(portPollutionControlFacility.getColumns("crosssectionalarea", "crosssectionalarea_uom", "height", "height_uom"))
                    .addProjection(portTransportationFacility.getColumns("startingpoint", "beddingwidth", "beddingwidth_uom", "numberoflanes", "parkinglotcapacityofbus",
                            "parkinglotcapacityofcars", "routetype", "routetype_codespace", "heighttodigit",
                            "heighttodigit_uom", "heightlimit", "heightlimit_uom", "minimumdepth", "minimumdepth_uom",
                            "numberofaircraftparkingspace", "pavementtype", "pavementtype_codespace"))
                    .addProjection(portWasteTreatmentFacility.getColumns("perimeter", "perimeter_uom", "innershorelength", "innershorelength_uom", "wastetype",
                            "wastetype_codespace", "planneddisposalarea", "planneddisposalarea_uom", "planneddisposalamount",
                            "receivingcapacity", "unitofreceivingcapacity", "unitofreceivingcap_codespace"))
                    .addProjection(shipServiceFacility.getColumns("supplyability", "supplyabilityunit", "supplyabilityunit_codespace", "mooringplace"))
                    .addProjection(fishingPortFacility.getColumn("portname", "fishingportfacility_portname"))
                    .addProjection(fishingPortFacility.getColumn("granttype", "fishingportfacility_granttype"))
                    .addProjection(fishingPortFacility.getColumn("granttype_codespace", "fishingportfacility_granttype_codespace"))
                    .addProjection(fishingPortFacility.getColumn("structuretype", "fishingportfacility_structuretype"))
                    .addProjection(fishingPortFacility.getColumn("structuretype_codespace", "fishingportfacility_structuretype_codespace"))
                    .addProjection(fishingPortFacility.getColumn("mainmaterial", "fishingportfacility_mainmaterial"))
                    .addProjection(fishingPortFacility.getColumn("mainmaterial_codespace", "fishingportfacility_mainmaterial_codespace"))
                    .addProjection(fishingPortFacility.getColumn("length", "fishingportfacility_length"))
                    .addProjection(fishingPortFacility.getColumn("length_uom", "fishingportfacility_length_uom"))
                    .addProjection(fishingPortFacility.getColumn("width", "fishingportfacility_width"))
                    .addProjection(fishingPortFacility.getColumn("width_uom", "fishingportfacility_width_uom"))
                    .addProjection(fishingPortFacility.getColumn("ceilingheight", "fishingportfacility_ceilingheight"))
                    .addProjection(fishingPortFacility.getColumn("ceilingheight_uom", "fishingportfacility_ceilingheight_uom"))
                    .addProjection(fishingPortFacility.getColumn("area", "fishingportfacility_area"))
                    .addProjection(fishingPortFacility.getColumn("area_uom", "fishingportfacility_area_uom"))
                    .addProjection(fishingPortFacility.getColumn("note", "fishingportfacility_note"))
                    .addProjection(parkFacilityLongevityPlanAttribute.getColumn("mainmaterial", "parkfacilitylongevityplanattribute_mainmaterial"))
                    .addProjection(parkFacilityLongevityPlanAttribute.getColumn("mainmaterial_codespace", "parkfacilitylongevityplanattribute_mainmaterial_codespace"))
                    .addProjection(portAttribute.getColumn("portname", "portattribute_portname"))
                    .addProjection(portAttribute.getColumn("granttype", "portattribute_granttype"))
                    .addProjection(portAttribute.getColumn("granttype_codespace", "portattribute_granttype_codespace"))
                    .addProjection(cargoHandlingFacility.getColumn("maincargo", "cargohandlingfacility_maincargo"))
                    .addProjection(cargoHandlingFacility.getColumn("maincargo_codespace", "cargohandlingfacility_maincargo_codespace"))
                    .addProjection(cargoHandlingFacility.getColumn("acquisitionyear", "cargohandlingfacility_acquisitionyear"))
                    .addProjection(cargoHandlingFacility.getColumn("innertotalfloorarea", "cargohandlingfacility_innertotalfloorarea"))
                    .addProjection(cargoHandlingFacility.getColumn("innertotalfloorarea_uom", "cargohandlingfacility_innertotalfloorarea_uom"))
                    .addProjection(cargoHandlingFacility.getColumn("innerofsitearea", "cargohandlingfacility_innerofsitearea"))
                    .addProjection(cargoHandlingFacility.getColumn("innerofsitearea_uom", "cargohandlingfacility_innerofsitearea_uom"))
                    .addProjection(cargoHandlingFacility.getColumn("outeroftotalfloorarea", "cargohandlingfacility_outeroftotalfloorarea"))
                    .addProjection(cargoHandlingFacility.getColumn("outeroftotalfloorarea_uom", "cargohandlingfacility_outeroftotalfloorarea_uom"))
                    .addProjection(cargoHandlingFacility.getColumn("outersitearea", "cargohandlingfacility_outersitearea"))
                    .addProjection(cargoHandlingFacility.getColumn("outersitearea_uom", "cargohandlingfacility_outersitearea_uom"))
                    .addProjection(cargoHandlingFacility.getColumn("mainmaterial", "cargohandlingfacility_mainmaterial"))
                    .addProjection(cargoHandlingFacility.getColumn("mainmaterial_codespace", "cargohandlingfacility_mainmaterial_codespace"))
                    .addProjection(cargoHandlingFacility.getColumn("totalcost", "cargohandlingfacility_totalcost"))
                    .addProjection(cargoHandlingFacility.getColumn("note", "cargohandlingfacility_note"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("geologicaltype", "cyberportmarinaandpbs_geologicaltype"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("geologicaltype_codespace", "cyberportmarinaandpbs_geologicaltype_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("obstructingstructures", "cyberportmarinaandpbs_obstructingstructures"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mainpartlength", "cyberportmarinaandpbs_mainpartlength"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mainpartlength_uom", "cyberportmarinaandpbs_mainpartlength_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("totallength", "cyberportmarinaandpbs_totallength"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("totallength_uom", "cyberportmarinaandpbs_totallength_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("wavedissipatorlength", "cyberportmarinaandpbs_wavedissipatorlength"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("wavedissipatorlength_uom", "cyberportmarinaandpbs_wavedissipatorlength_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("facilitywidth", "cyberportmarinaandpbs_facilitywidth"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("facilitywidth_uom", "cyberportmarinaandpbs_facilitywidth_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("apronwidth", "cyberportmarinaandpbs_apronwidth"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("apronwidth_uom", "cyberportmarinaandpbs_apronwidth_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("planneddepth", "cyberportmarinaandpbs_planneddepth"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("planneddepth_uom", "cyberportmarinaandpbs_planneddepth_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("currentdepth", "cyberportmarinaandpbs_currentdepth"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("currentdepth_uom", "cyberportmarinaandpbs_currentdepth_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("innertotalfloorarea", "cyberportmarinaandpbs_innertotalfloorarea"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("innertotalfloorarea_uom", "cyberportmarinaandpbs_innertotalfloorarea_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("innerofsitearea", "cyberportmarinaandpbs_innerofsitearea"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("innerofsitearea_uom", "cyberportmarinaandpbs_innerofsitearea_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("outeroftotalfloorarea", "cyberportmarinaandpbs_outeroftotalfloorarea"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("outeroftotalfloorarea_uom", "cyberportmarinaandpbs_outeroftotalfloorarea_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("outersitearea", "cyberportmarinaandpbs_outersitearea"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("outersitearea_uom", "cyberportmarinaandpbs_outersitearea_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("ceilingheight", "cyberportmarinaandpbs_ceilingheight"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("ceilingheight_uom", "cyberportmarinaandpbs_ceilingheight_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("gravityresistant", "cyberportmarinaandpbs_gravityresistant"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("gravityresistant_uom", "cyberportmarinaandpbs_gravityresistant_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("form", "cyberportmarinaandpbs_form"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("form_codespace", "cyberportmarinaandpbs_form_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("areatype", "cyberportmarinaandpbs_areatype"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("areatype_codespace", "cyberportmarinaandpbs_areatype_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mainvessels", "cyberportmarinaandpbs_mainvessels"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mainvessels_codespace", "cyberportmarinaandpbs_mainvessels_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("isdredged", "cyberportmarinaandpbs_isdredged"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mooringpostweight", "cyberportmarinaandpbs_mooringpostweight"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mooringpostweight_uom", "cyberportmarinaandpbs_mooringpostweight_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("numberofmooringposts", "cyberportmarinaandpbs_numberofmooringposts"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("resistantmaterial", "cyberportmarinaandpbs_resistantmaterial"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("lighting", "cyberportmarinaandpbs_lighting"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("stairs", "cyberportmarinaandpbs_stairs"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("bumper", "cyberportmarinaandpbs_bumper"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("bumper_uom", "cyberportmarinaandpbs_bumper_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("numberofvehicleboardings", "cyberportmarinaandpbs_numberofvehicleboardings"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("vehicleboardingwidth", "cyberportmarinaandpbs_vehicleboardingwidth"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("vehicleboardingwidth_uom", "cyberportmarinaandpbs_vehicleboardingwidth_uom"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("shiptype", "cyberportmarinaandpbs_shiptype"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("numberofseats", "cyberportmarinaandpbs_numberofseats"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("maincargo", "cyberportmarinaandpbs_maincargo"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("maincargo_codespace", "cyberportmarinaandpbs_maincargo_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("storagecapacity", "cyberportmarinaandpbs_storagecapacity"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("storagecapacityunit", "cyberportmarinaandpbs_storagecapacityunit"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("storagecapacityuni_codespace", "cyberportmarinaandpbs_storagecapacityuni_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("structuretype", "cyberportmarinaandpbs_structuretype"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("structuretype_codespace", "cyberportmarinaandpbs_structuretype_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mainmaterial", "cyberportmarinaandpbs_mainmaterial"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("mainmaterial_codespace", "cyberportmarinaandpbs_mainmaterial_codespace"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("totalcost", "cyberportmarinaandpbs_totalcost"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("subsidy", "cyberportmarinaandpbs_subsidy"))
                    .addProjection(cyberportMarinaAndPBS.getColumn("note", "cyberportmarinaandpbs_note"))
                    .addProjection(harborFacility.getColumn("geologicaltype", "harborfacility_geologicaltype"))
                    .addProjection(harborFacility.getColumn("geologicaltype_codespace", "harborfacility_geologicaltype_codespace"))
                    .addProjection(harborFacility.getColumn("obstructingstructures", "harborfacility_obstructingstructures"))
                    .addProjection(harborFacility.getColumn("length", "harborfacility_length"))
                    .addProjection(harborFacility.getColumn("length_uom", "harborfacility_length_uom"))
                    .addProjection(harborFacility.getColumn("minimumwidth", "harborfacility_minimumwidth"))
                    .addProjection(harborFacility.getColumn("minimumwidth_uom", "harborfacility_minimumwidth_uom"))
                    .addProjection(harborFacility.getColumn("planneddepth", "harborfacility_planneddepth"))
                    .addProjection(harborFacility.getColumn("planneddepth_uom", "harborfacility_planneddepth_uom"))
                    .addProjection(harborFacility.getColumn("currentdepth", "harborfacility_currentdepth"))
                    .addProjection(harborFacility.getColumn("currentdepth_uom", "harborfacility_currentdepth_uom"))
                    .addProjection(harborFacility.getColumn("isdredged", "harborfacility_isdredged"))
                    .addProjection(harborFacility.getColumn("areatype", "harborfacility_areatype"))
                    .addProjection(harborFacility.getColumn("areatype_codespace", "harborfacility_areatype_codespace"))
                    .addProjection(harborFacility.getColumn("totalcost", "harborfacility_totalcost"))
                    .addProjection(harborFacility.getColumn("subsidy", "harborfacility_subsidy"))
                    .addProjection(harborFacility.getColumn("note", "harborfacility_note"))
                    .addProjection(mooringFacility.getColumn("mainpartlength", "mooringfacility_mainpartlength"))
                    .addProjection(mooringFacility.getColumn("mainpartlength_uom", "mooringfacility_mainpartlength_uom"))
                    .addProjection(mooringFacility.getColumn("totallength", "mooringfacility_totallength"))
                    .addProjection(mooringFacility.getColumn("totallength_uom", "mooringfacility_totallength_uom"))
                    .addProjection(mooringFacility.getColumn("facilitywidth", "mooringfacility_facilitywidth"))
                    .addProjection(mooringFacility.getColumn("facilitywidth_uom", "mooringfacility_facilitywidth_uom"))
                    .addProjection(mooringFacility.getColumn("apronwidth", "mooringfacility_apronwidth"))
                    .addProjection(mooringFacility.getColumn("apronwidth_uom", "mooringfacility_apronwidth_uom"))
                    .addProjection(mooringFacility.getColumn("planneddepth", "mooringfacility_planneddepth"))
                    .addProjection(mooringFacility.getColumn("planneddepth_uom", "mooringfacility_planneddepth_uom"))
                    .addProjection(mooringFacility.getColumn("currentdepth", "mooringfacility_currentdepth"))
                    .addProjection(mooringFacility.getColumn("currentdepth_uom", "mooringfacility_currentdepth_uom"))
                    .addProjection(mooringFacility.getColumn("area", "mooringfacility_area"))
                    .addProjection(mooringFacility.getColumn("area_uom", "mooringfacility_area_uom"))
                    .addProjection(mooringFacility.getColumn("ceilingheight", "mooringfacility_ceilingheight"))
                    .addProjection(mooringFacility.getColumn("ceilingheight_uom", "mooringfacility_ceilingheight_uom"))
                    .addProjection(mooringFacility.getColumn("gravityresistant", "mooringfacility_gravityresistant"))
                    .addProjection(mooringFacility.getColumn("gravityresistant_uom", "mooringfacility_gravityresistant_uom"))
                    .addProjection(mooringFacility.getColumn("form", "mooringfacility_form"))
                    .addProjection(mooringFacility.getColumn("form_codespace", "mooringfacility_form_codespace"))
                    .addProjection(mooringFacility.getColumn("mainvessels", "mooringfacility_mainvessels"))
                    .addProjection(mooringFacility.getColumn("mainvessels_codespace", "mooringfacility_mainvessels_codespace"))
                    .addProjection(mooringFacility.getColumn("mooringpostweight", "mooringfacility_mooringpostweight"))
                    .addProjection(mooringFacility.getColumn("mooringpostweight_uom", "mooringfacility_mooringpostweight_uom"))
                    .addProjection(mooringFacility.getColumn("numberofmooringposts", "mooringfacility_numberofmooringposts"))
                    .addProjection(mooringFacility.getColumn("resistantmaterial", "mooringfacility_resistantmaterial"))
                    .addProjection(mooringFacility.getColumn("lighting", "mooringfacility_lighting"))
                    .addProjection(mooringFacility.getColumn("stairs", "mooringfacility_stairs"))
                    .addProjection(mooringFacility.getColumn("bumper", "mooringfacility_bumper"))
                    .addProjection(mooringFacility.getColumn("bumper_uom", "mooringfacility_bumper_uom"))
                    .addProjection(mooringFacility.getColumn("numberofvehicleboardings", "mooringfacility_numberofvehicleboardings"))
                    .addProjection(mooringFacility.getColumn("vehicleboardingwidth", "mooringfacility_vehicleboardingwidth"))
                    .addProjection(mooringFacility.getColumn("vehicleboardingwidth_uom", "mooringfacility_vehicleboardingwidth_uom"))
                    .addProjection(mooringFacility.getColumn("shiptype", "mooringfacility_shiptype"))
                    .addProjection(mooringFacility.getColumn("numberofseats", "mooringfacility_numberofseats"))
                    .addProjection(mooringFacility.getColumn("maincargo", "mooringfacility_maincargo"))
                    .addProjection(mooringFacility.getColumn("maincargo_codespace", "mooringfacility_maincargo_codespace"))
                    .addProjection(mooringFacility.getColumn("structuretype", "mooringfacility_structuretype"))
                    .addProjection(mooringFacility.getColumn("structuretype_codespace", "mooringfacility_structuretype_codespace"))
                    .addProjection(mooringFacility.getColumn("mainmaterial", "mooringfacility_mainmaterial"))
                    .addProjection(mooringFacility.getColumn("mainmaterial_codespace", "mooringfacility_mainmaterial_codespace"))
                    .addProjection(mooringFacility.getColumn("totalcost", "mooringfacility_totalcost"))
                    .addProjection(mooringFacility.getColumn("subsidy", "mooringfacility_subsidy"))
                    .addProjection(mooringFacility.getColumn("note", "mooringfacility_note"))
                    .addProjection(navigationAssistanceFacility.getColumn("totalcost", "navigationassistancefacility_totalcost"))
                    .addProjection(navigationAssistanceFacility.getColumn("subsidy", "navigationassistancefacility_subsidy"))
                    .addProjection(navigationAssistanceFacility.getColumn("note", "navigationassistancefacility_note"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("usage", "portenvironmentalimprovementfacility_usage"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("length", "portenvironmentalimprovementfacility_length"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("length_uom", "portenvironmentalimprovementfacility_length_uom"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("area", "portenvironmentalimprovementfacility_area"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("area_uom", "portenvironmentalimprovementfacility_area_uom"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("totalfloorarea", "portenvironmentalimprovementfacility_totalfloorarea"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("totalfloorarea_uom", "portenvironmentalimprovementfacility_totalfloorarea_uom"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("totalcost", "portenvironmentalimprovementfacility_totalcost"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("subsidy", "portenvironmentalimprovementfacility_subsidy"))
                    .addProjection(portEnvironmentalImprovementFacility.getColumn("note", "portenvironmentalimprovementfacility_note"))
                    .addProjection(portManagementFacility.getColumn("totalfloorarea", "portmanagementfacility_totalfloorarea"))
                    .addProjection(portManagementFacility.getColumn("totalfloorarea_uom", "portmanagementfacility_totalfloorarea_uom"))
                    .addProjection(portManagementFacility.getColumn("acquisitionyear", "portmanagementfacility_acquisitionyear"))
                    .addProjection(portManagementFacility.getColumn("usage", "portmanagementfacility_usage"))
                    .addProjection(portManagementFacility.getColumn("totalcost", "portmanagementfacility_totalcost"))
                    .addProjection(portManagementFacility.getColumn("subsidy", "portmanagementfacility_subsidy"))
                    .addProjection(portManagementFacility.getColumn("note", "portmanagementfacility_note"))
                    .addProjection(portPassengerFacility.getColumn("length", "portpassengerfacility_length"))
                    .addProjection(portPassengerFacility.getColumn("length_uom", "portpassengerfacility_length_uom"))
                    .addProjection(portPassengerFacility.getColumn("width", "portpassengerfacility_width"))
                    .addProjection(portPassengerFacility.getColumn("width_uom", "portpassengerfacility_width_uom"))
                    .addProjection(portPassengerFacility.getColumn("mainmaterial", "portpassengerfacility_mainmaterial"))
                    .addProjection(portPassengerFacility.getColumn("mainmaterial_codespace", "portpassengerfacility_mainmaterial_codespace"))
                    .addProjection(portPassengerFacility.getColumn("totalfloorarea", "portpassengerfacility_totalfloorarea"))
                    .addProjection(portPassengerFacility.getColumn("totalfloorarea_uom", "portpassengerfacility_totalfloorarea_uom"))
                    .addProjection(portPassengerFacility.getColumn("acquisitionyear", "portpassengerfacility_acquisitionyear"))
                    .addProjection(portPassengerFacility.getColumn("totalcost", "portpassengerfacility_totalcost"))
                    .addProjection(portPassengerFacility.getColumn("note", "portpassengerfacility_note"))
                    .addProjection(portPollutionControlFacility.getColumn("length", "portpollutioncontrolfacility_length"))
                    .addProjection(portPollutionControlFacility.getColumn("length_uom", "portpollutioncontrolfacility_length_uom"))
                    .addProjection(portPollutionControlFacility.getColumn("width", "portpollutioncontrolfacility_width"))
                    .addProjection(portPollutionControlFacility.getColumn("width_uom", "portpollutioncontrolfacility_width_uom"))
                    .addProjection(portPollutionControlFacility.getColumn("area", "portpollutioncontrolfacility_area"))
                    .addProjection(portPollutionControlFacility.getColumn("area_uom", "portpollutioncontrolfacility_area_uom"))
                    .addProjection(portPollutionControlFacility.getColumn("mainmaterial", "portpollutioncontrolfacility_mainmaterial"))
                    .addProjection(portPollutionControlFacility.getColumn("mainmaterial_codespace", "portpollutioncontrolfacility_mainmaterial_codespace"))
                    .addProjection(portPollutionControlFacility.getColumn("totalcost", "portpollutioncontrolfacility_totalcost"))
                    .addProjection(portPollutionControlFacility.getColumn("subsidy", "portpollutioncontrolfacility_subsidy"))
                    .addProjection(portPollutionControlFacility.getColumn("note", "portpollutioncontrolfacility_note"))
                    .addProjection(portProtectiveFacility.getColumn("structuretype", "portprotectivefacility_structuretype"))
                    .addProjection(portProtectiveFacility.getColumn("structuretype_codespace", "portprotectivefacility_structuretype_codespace"))
                    .addProjection(portProtectiveFacility.getColumn("mainmaterial", "portprotectivefacility_mainmaterial"))
                    .addProjection(portProtectiveFacility.getColumn("mainmaterial_codespace", "portprotectivefacility_mainmaterial_codespace"))
                    .addProjection(portProtectiveFacility.getColumn("totalcost", "portprotectivefacility_totalcost"))
                    .addProjection(portProtectiveFacility.getColumn("subsidy", "portprotectivefacility_subsidy"))
                    .addProjection(portProtectiveFacility.getColumn("note", "portprotectivefacility_note"))
                    .addProjection(portStorageFacility.getColumn("innertotalfloorarea", "portstoragefacility_innertotalfloorarea"))
                    .addProjection(portStorageFacility.getColumn("innertotalfloorarea_uom", "portstoragefacility_innertotalfloorarea_uom"))
                    .addProjection(portStorageFacility.getColumn("innerofsitearea", "portstoragefacility_innerofsitearea"))
                    .addProjection(portStorageFacility.getColumn("innerofsitearea_uom", "portstoragefacility_innerofsitearea_uom"))
                    .addProjection(portStorageFacility.getColumn("outeroftotalfloorarea", "portstoragefacility_outeroftotalfloorarea"))
                    .addProjection(portStorageFacility.getColumn("outeroftotalfloorarea_uom", "portstoragefacility_outeroftotalfloorarea_uom"))
                    .addProjection(portStorageFacility.getColumn("outersitearea", "portstoragefacility_outersitearea"))
                    .addProjection(portStorageFacility.getColumn("outersitearea_uom", "portstoragefacility_outersitearea_uom"))
                    .addProjection(portStorageFacility.getColumn("maincargo", "portstoragefacility_maincargo"))
                    .addProjection(portStorageFacility.getColumn("maincargo_codespace", "portstoragefacility_maincargo_codespace"))
                    .addProjection(portStorageFacility.getColumn("storagecapacity", "portstoragefacility_storagecapacity"))
                    .addProjection(portStorageFacility.getColumn("storagecapacityunit", "portstoragefacility_storagecapacityunit"))
                    .addProjection(portStorageFacility.getColumn("storagecapacityuni_codespace", "portstoragefacility_storagecapacityuni_codespace"))
                    .addProjection(portStorageFacility.getColumn("mainmaterial", "portstoragefacility_mainmaterial"))
                    .addProjection(portStorageFacility.getColumn("mainmaterial_codespace", "portstoragefacility_mainmaterial_codespace"))
                    .addProjection(portStorageFacility.getColumn("totalcost", "portstoragefacility_totalcost"))
                    .addProjection(portStorageFacility.getColumn("note", "portstoragefacility_note"))
                    .addProjection(portTransportationFacility.getColumn("structuretype", "porttransportationfacility_structuretype"))
                    .addProjection(portTransportationFacility.getColumn("structuretype_codespace", "porttransportationfacility_structuretype_codespace"))
                    .addProjection(portTransportationFacility.getColumn("length", "porttransportationfacility_length"))
                    .addProjection(portTransportationFacility.getColumn("length_uom", "porttransportationfacility_length_uom"))
                    .addProjection(portTransportationFacility.getColumn("area", "porttransportationfacility_area"))
                    .addProjection(portTransportationFacility.getColumn("area_uom", "porttransportationfacility_area_uom"))
                    .addProjection(portTransportationFacility.getColumn("minimumwidth", "porttransportationfacility_minimumwidth"))
                    .addProjection(portTransportationFacility.getColumn("minimumwidth_uom", "porttransportationfacility_minimumwidth_uom"))
                    .addProjection(portTransportationFacility.getColumn("maincargo", "porttransportationfacility_maincargo"))
                    .addProjection(portTransportationFacility.getColumn("maincargo_codespace", "porttransportationfacility_maincargo_codespace"))
                    .addProjection(portTransportationFacility.getColumn("totalcost", "porttransportationfacility_totalcost"))
                    .addProjection(portTransportationFacility.getColumn("subsidy", "porttransportationfacility_subsidy"))
                    .addProjection(portTransportationFacility.getColumn("note", "porttransportationfacility_note"))
                    .addProjection(portWasteTreatmentFacility.getColumn("structuretype", "portwastetreatmentfacility_structuretype"))
                    .addProjection(portWasteTreatmentFacility.getColumn("structuretype_codespace", "portwastetreatmentfacility_structuretype_codespace"))
                    .addProjection(portWasteTreatmentFacility.getColumn("mainpartlength", "portwastetreatmentfacility_mainpartlength"))
                    .addProjection(portWasteTreatmentFacility.getColumn("mainpartlength_uom", "portwastetreatmentfacility_mainpartlength_uom"))
                    .addProjection(portWasteTreatmentFacility.getColumn("ceilingheight", "portwastetreatmentfacility_ceilingheight"))
                    .addProjection(portWasteTreatmentFacility.getColumn("ceilingheight_uom", "portwastetreatmentfacility_ceilingheight_uom"))
                    .addProjection(portWasteTreatmentFacility.getColumn("wavedissipatorlength", "portwastetreatmentfacility_wavedissipatorlength"))
                    .addProjection(portWasteTreatmentFacility.getColumn("wavedissipatorlength_uom", "portwastetreatmentfacility_wavedissipatorlength_uom"))
                    .addProjection(portWasteTreatmentFacility.getColumn("mainmaterial", "portwastetreatmentfacility_mainmaterial"))
                    .addProjection(portWasteTreatmentFacility.getColumn("mainmaterial_codespace", "portwastetreatmentfacility_mainmaterial_codespace"))
                    .addProjection(portWasteTreatmentFacility.getColumn("shiptype", "portwastetreatmentfacility_shiptype"))
                    .addProjection(portWasteTreatmentFacility.getColumn("acquisitionyear", "portwastetreatmentfacility_acquisitionyear"))
                    .addProjection(portWasteTreatmentFacility.getColumn("totalcost", "portwastetreatmentfacility_totalcost"))
                    .addProjection(portWasteTreatmentFacility.getColumn("subsidy", "portwastetreatmentfacility_subsidy"))
                    .addProjection(portWasteTreatmentFacility.getColumn("note", "portwastetreatmentfacility_note"))
                    .addProjection(portWelfareFacility.getColumn("totalfloorarea", "portwelfarefacility_totalfloorarea"))
                    .addProjection(portWelfareFacility.getColumn("totalfloorarea_uom", "portwelfarefacility_totalfloorarea_uom"))
                    .addProjection(portWelfareFacility.getColumn("totalcost", "portwelfarefacility_totalcost"))
                    .addProjection(portWelfareFacility.getColumn("note", "portwelfarefacility_note"))
                    .addProjection(shipServiceFacility.getColumn("shiptype", "shipservicefacility_shiptype"))
                    .addProjection(shipServiceFacility.getColumn("length", "shipservicefacility_length"))
                    .addProjection(shipServiceFacility.getColumn("length_uom", "shipservicefacility_length_uom"))
                    .addProjection(shipServiceFacility.getColumn("width", "shipservicefacility_width"))
                    .addProjection(shipServiceFacility.getColumn("width_uom", "shipservicefacility_width_uom"))
                    .addProjection(shipServiceFacility.getColumn("area", "shipservicefacility_area"))
                    .addProjection(shipServiceFacility.getColumn("area_uom", "shipservicefacility_area_uom"))
                    .addProjection(shipServiceFacility.getColumn("acquisitionyear", "shipservicefacility_acquisitionyear"))
                    .addProjection(shipServiceFacility.getColumn("totalcost", "shipservicefacility_totalcost"))
                    .addProjection(shipServiceFacility.getColumn("note", "shipservicefacility_note"))
                    .addJoin(JoinFactory.left(fishingPortAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(fishingPortCapacity, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(fishingPortFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(maintenanceHistoryAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(parkFacilityLongevityPlanAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portAttribute, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(cargoHandlingFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(cyberportMarinaAndPBS, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(harborFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(mooringFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(navigationAssistanceFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portEnvironmentalImprovementFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portManagementFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portPassengerFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portPollutionControlFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portProtectiveFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portStorageFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portTransportationFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portWasteTreatmentFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(portWelfareFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addJoin(JoinFactory.left(shipServiceFacility, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                    .addSelection(ComparisonFactory.equalTo(table.getColumn(parentColumn), new PlaceHolder<>()))
                    .addOrderBy(new OrderByToken(table.getColumn("id")));
            psByParent.put(parentColumn, connection.prepareStatement(parentSelect.toString()));
        }

        numberOfFacilitiesExporter = manager.getExporter(NumberOfFacilitiesExporter.class);
        repairsBeforeParkHealthAssessmentExporter = manager.getExporter(RepairsBeforeParkHealthAssessmentExporter.class);
        parkHealthAssessmentExporter = manager.getExporter(ParkHealthAssessmentExporter.class);
        longevityMeasuresExporter = manager.getExporter(LongevityMeasuresExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
    }

    public List<FacilityAttribute> doExport(long parentId, String parentColumn) throws CityGMLExportException, SQLException {
        PreparedStatement ps = psByParent.get(parentColumn);
        ps.setLong(1, parentId);

        List<FacilityAttribute> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FacilityAttribute facilityAttribute = doExport(rs.getLong("id"), rs);
                if (facilityAttribute != null)
                    result.add(facilityAttribute);
            }
        }

        return result;
    }

    private FacilityAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        FacilityAttribute facilityAttribute = objectMapper.createType(objectClassId, FacilityAttribute.class);
        if (facilityAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate facility attribute (id: " + objectId + ").");
            return null;
        }

        facilityAttribute.setFacilityId(rs.getString("facilityid"));

        if (facilityAttribute instanceof FishingPortCapacity) {
            FishingPortCapacity fishingPortCapacity = (FishingPortCapacity) facilityAttribute;

            fishingPortCapacity.setCapacity(rs.getString("capacity"));

            double weightCapacity = rs.getDouble("weightcapacity");
            if (!rs.wasNull()) {
                Measure measure = new Measure(weightCapacity);
                measure.setUom(rs.getString("weightcapacity_uom"));
                fishingPortCapacity.setWeightCapacity(measure);
            }

            int hullForm = rs.getInt("hullform");
            if (!rs.wasNull())
                fishingPortCapacity.setHullForm(hullForm);

            int shipNumber = rs.getInt("shipnumber");
            if (!rs.wasNull())
                fishingPortCapacity.setShipNumber(shipNumber);

            double waterDepth2m = rs.getDouble("waterdepth_2m");
            if (!rs.wasNull()) {
                Measure measure = new Measure(waterDepth2m);
                measure.setUom(rs.getString("waterdepth_2m_uom"));
                fishingPortCapacity.setWaterDepth2m(measure);
            }

            double waterDepth23m = rs.getDouble("waterdepth2_3m");
            if (!rs.wasNull()) {
                Measure measure = new Measure(waterDepth23m);
                measure.setUom(rs.getString("waterdepth2_3m_uom"));
                fishingPortCapacity.setWaterDepth23m(measure);
            }

            double waterDepth36m = rs.getDouble("waterdepth3_6m");
            if (!rs.wasNull()) {
                Measure measure = new Measure(waterDepth36m);
                measure.setUom(rs.getString("waterdepth3_6m_uom"));
                fishingPortCapacity.setWaterDepth36m(measure);
            }

            double waterDepth6m = rs.getDouble("waterdepth6_m");
            if (!rs.wasNull()) {
                Measure measure = new Measure(waterDepth6m);
                measure.setUom(rs.getString("waterdepth6_m_uom"));
                fishingPortCapacity.setWaterDepth6m(measure);
            }

            double heightAboveAWL = rs.getDouble("heightaboveawl");
            if (!rs.wasNull()) {
                Length length = new Length(heightAboveAWL);
                length.setUom(rs.getString("heightaboveawl_uom"));
                fishingPortCapacity.setHeightAboveAWL(length);
            }

            double heightOnFoundations = rs.getDouble("heightonfoundations");
            if (!rs.wasNull()) {
                Length length = new Length(heightOnFoundations);
                length.setUom(rs.getString("heightonfoundations_uom"));
                fishingPortCapacity.setHeightOnFoundations(length);
            }

            double luminousRange = rs.getDouble("luminousrange");
            if (!rs.wasNull()) {
                Length length = new Length(luminousRange);
                length.setUom(rs.getString("luminousrange_uom"));
                fishingPortCapacity.setLuminousRange(length);
            }

            fishingPortCapacity.setLuminousColor(rs.getString("luminouscolor"));

            int candlePower = rs.getInt("candlepower");
            if (!rs.wasNull())
                fishingPortCapacity.setCandlePower(candlePower);

            fishingPortCapacity.setLightType(rs.getString("lighttype"));

            fishingPortCapacity.setPeriod(rs.getString("period"));

            int maximumGroundingWeight = rs.getInt("maximumgroundingweight");
            if (!rs.wasNull())
                fishingPortCapacity.setMaximumGroundingWeight(maximumGroundingWeight);

            int handleablePower = rs.getInt("handleablepower");
            if (!rs.wasNull())
                fishingPortCapacity.setHandleablePower(handleablePower);

            int maximumWaterSupply = rs.getInt("maximumwatersupply");
            if (!rs.wasNull())
                fishingPortCapacity.setMaximumWaterSupply(maximumWaterSupply);

            fishingPortCapacity.setMaximumRefueling(rs.getString("maximumrefueling"));

            int people = rs.getInt("people");
            if (!rs.wasNull())
                fishingPortCapacity.setPeople(people);

            fishingPortCapacity.setOther(rs.getString("other"));
        }

        if (facilityAttribute instanceof FishingPortFacility) {
            FishingPortFacility fishingPortFacility = (FishingPortFacility) facilityAttribute;

            String facilityDetailsType = rs.getString("facilitydetailstype");
            if (!rs.wasNull()) {
                Code code = new Code(facilityDetailsType);
                code.setCodeSpace(rs.getString("facilitydetailstyp_codespace"));
                fishingPortFacility.setFacilityDetailsType(code);
            }

            fishingPortFacility.setPortName(rs.getString("fishingportfacility_portname"));

            String portType = rs.getString("porttype");
            if (!rs.wasNull()) {
                Code code = new Code(portType);
                code.setCodeSpace(rs.getString("porttype_codespace"));
                fishingPortFacility.setPortType(code);
            }

            fishingPortFacility.setAddress(rs.getString("address"));

            fishingPortFacility.setDesignatedArea(rs.getString("designatedarea"));

            for (SplitValue splitValue : valueSplitter.split(rs.getString("designation")))
                fishingPortFacility.getDesignation().add(splitValue.result(0));

            for (SplitValue splitValue : valueSplitter.split(rs.getString("designatedadministrator")))
                fishingPortFacility.getDesignatedAdministrator().add(splitValue.result(0));

            for (SplitValue splitValue : valueSplitter.split(rs.getString("referencenumber")))
                fishingPortFacility.getReferenceNumber().add(splitValue.result(0));

            String grantType = rs.getString("fishingportfacility_granttype");
            if (!rs.wasNull()) {
                Code code = new Code(grantType);
                code.setCodeSpace(rs.getString("fishingportfacility_granttype_codespace"));
                fishingPortFacility.setGrantType(code);
            }

            fishingPortFacility.setAdministrator(rs.getString("administrator"));

            fishingPortFacility.setFacilityManager(rs.getString("facilitymanager"));

            String structureType = rs.getString("fishingportfacility_structuretype");
            if (!rs.wasNull()) {
                Code code = new Code(structureType);
                code.setCodeSpace(rs.getString("fishingportfacility_structuretype_codespace"));
                fishingPortFacility.setStructureType(code);
            }

            String mainMaterial = rs.getString("fishingportfacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("fishingportfacility_mainmaterial_codespace"));
                fishingPortFacility.setMainMaterial(code);
            }

            fishingPortFacility.setOtherStructure(rs.getString("otherstructure"));

            double lengthValue = rs.getDouble("fishingportfacility_length");
            if (!rs.wasNull()) {
                Length length = new Length(lengthValue);
                length.setUom(rs.getString("fishingportfacility_length_uom"));
                fishingPortFacility.setLength(length);
            }

            double width = rs.getDouble("fishingportfacility_width");
            if (!rs.wasNull()) {
                Length length = new Length(width);
                length.setUom(rs.getString("fishingportfacility_width_uom"));
                fishingPortFacility.setWidth(length);
            }

            double ceilingHeight = rs.getDouble("fishingportfacility_ceilingheight");
            if (!rs.wasNull()) {
                Length length = new Length(ceilingHeight);
                length.setUom(rs.getString("fishingportfacility_ceilingheight_uom"));
                fishingPortFacility.setCeilingHeight(length);
            }

            double depth = rs.getDouble("depth");
            if (!rs.wasNull()) {
                Length length = new Length(depth);
                length.setUom(rs.getString("depth_uom"));
                fishingPortFacility.setDepth(length);
            }

            double area = rs.getDouble("fishingportfacility_area");
            if (!rs.wasNull()) {
                Measure measure = new Measure(area);
                measure.setUom(rs.getString("fishingportfacility_area_uom"));
                fishingPortFacility.setArea(measure);
            }

            fishingPortFacility.setOtherSizeDescription(rs.getString("othersizedescription"));

            Date dateOfConstructionOrAcquisition = rs.getDate("dateofconstructionoracquisit");
            if (!rs.wasNull())
                fishingPortFacility.setDateOfConstructionOrAcquisition(dateOfConstructionOrAcquisition.toLocalDate());

            int cost = rs.getInt("cost");
            if (!rs.wasNull())
                fishingPortFacility.setCost(cost);

            fishingPortFacility.setNote(rs.getString("fishingportfacility_note"));
        }

        if (facilityAttribute instanceof MaintenanceHistoryAttribute) {
            MaintenanceHistoryAttribute maintenanceHistoryAttribute = (MaintenanceHistoryAttribute) facilityAttribute;

            String maintenanceType = rs.getString("maintenancetype");
            if (!rs.wasNull()) {
                Code code = new Code(maintenanceType);
                code.setCodeSpace(rs.getString("maintenancetype_codespace"));
                maintenanceHistoryAttribute.setMaintenanceType(code);
            }

            Date maintenanceFiscalYear = rs.getDate("maintenancefiscalyear");
            if (!rs.wasNull())
                maintenanceHistoryAttribute.setMaintenanceFiscalYear(Year.of(maintenanceFiscalYear.toLocalDate().getYear()));

            Date maintenanceYear = rs.getDate("maintenanceyear");
            if (!rs.wasNull())
                maintenanceHistoryAttribute.setMaintenanceYear(Year.of(maintenanceYear.toLocalDate().getYear()));

            Date maintenanceDate = rs.getDate("maintenancedate");
            if (!rs.wasNull())
                maintenanceHistoryAttribute.setMaintenanceDate(maintenanceDate.toLocalDate());

            maintenanceHistoryAttribute.setStatus(rs.getString("status"));

            String description = rs.getString("description");
            if (!rs.wasNull())
                maintenanceHistoryAttribute.setDescription(new StringOrRef(description));
        }

        if (facilityAttribute instanceof ParkFacilityLongevityPlanAttribute) {
            ParkFacilityLongevityPlanAttribute parkFacilityLongevityPlanAttribute = (ParkFacilityLongevityPlanAttribute) facilityAttribute;

            String parkCode = rs.getString("parkcode");
            if (!rs.wasNull()) {
                Code code = new Code(parkCode);
                code.setCodeSpace(rs.getString("parkcode_codespace"));
                parkFacilityLongevityPlanAttribute.setParkCode(code);
            }

            String parkName = rs.getString("parkname");
            if (!rs.wasNull()) {
                Code code = new Code(parkName);
                code.setCodeSpace(rs.getString("parkname_codespace"));
                parkFacilityLongevityPlanAttribute.setParkName(code);
            }

            String parkType = rs.getString("parktype");
            if (!rs.wasNull()) {
                Code code = new Code(parkType);
                code.setCodeSpace(rs.getString("parktype_codespace"));
                parkFacilityLongevityPlanAttribute.setParkType(code);
            }

            String facilityName = rs.getString("facilityname");
            if (!rs.wasNull()) {
                Code code = new Code(facilityName);
                code.setCodeSpace(rs.getString("facilityname_codespace"));
                parkFacilityLongevityPlanAttribute.setFacilityName(code);
            }

            parkFacilityLongevityPlanAttribute.setFacilityNameOptional(rs.getString("facilitynameoptional"));

            parkFacilityLongevityPlanAttribute.setSpecificFacilityName(rs.getString("specificfacilityname"));

            long numberOfFacilitiesId = rs.getLong("numberoffacilities_id");
            if (numberOfFacilitiesId != 0) {
                NumberOfFacilities numberOfFacilities = new NumberOfFacilities();
                numberOfFacilitiesExporter.doExport(numberOfFacilities, numberOfFacilitiesId);
                parkFacilityLongevityPlanAttribute.setNumberOfFacilities(new NumberOfFacilitiesProperty(numberOfFacilities));
            }

            parkFacilityLongevityPlanAttribute.setSize(rs.getString("size_"));

            String mainMaterial = rs.getString("parkfacilitylongevityplanattribute_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("parkfacilitylongevityplanattribute_mainmaterial_codespace"));
                parkFacilityLongevityPlanAttribute.setMainMaterial(code);
            }

            parkFacilityLongevityPlanAttribute.setMainMaterialOptional(rs.getString("mainmaterialoptional"));

            Date installationYear = rs.getDate("installationyear");
            if (!rs.wasNull())
                parkFacilityLongevityPlanAttribute.setInstallationYear(Year.of(installationYear.toLocalDate().getYear()));

            int disposalLimitPeriod = rs.getInt("disposallimitperiod");
            if (!rs.wasNull())
                parkFacilityLongevityPlanAttribute.setDisposalLimitPeriod(disposalLimitPeriod);

            int expectedUsagePeriod = rs.getInt("expectedusageperiod");
            if (!rs.wasNull())
                parkFacilityLongevityPlanAttribute.setExpectedUsagePeriod(expectedUsagePeriod);

            long repairsBeforeParkHealthAssessmentId = rs.getLong("repairsbeforeparkhealthas_id");
            if (repairsBeforeParkHealthAssessmentId != 0) {
                RepairsBeforeParkHealthAssessment repairsBeforeParkHealthAssessment = new RepairsBeforeParkHealthAssessment();
                repairsBeforeParkHealthAssessmentExporter.doExport(repairsBeforeParkHealthAssessment, repairsBeforeParkHealthAssessmentId);
                parkFacilityLongevityPlanAttribute.setRepairsBeforeParkHealthAssessment(new RepairsBeforeParkHealthAssessmentProperty(repairsBeforeParkHealthAssessment));
            }

            for (ParkHealthAssessment parkHealthAssessment : parkHealthAssessmentExporter.doExport(objectId))
                parkFacilityLongevityPlanAttribute.getParkHealthAssessment().add(new ParkHealthAssessmentProperty(parkHealthAssessment));

            String managementType = rs.getString("managementtype");
            if (!rs.wasNull()) {
                Code code = new Code(managementType);
                code.setCodeSpace(rs.getString("managementtype_codespace"));
                parkFacilityLongevityPlanAttribute.setManagementType(code);
            }

            Date expectedRenewalYearWithMeasures = rs.getDate("expectedrenewalyearwithmeasu");
            if (!rs.wasNull())
                parkFacilityLongevityPlanAttribute.setExpectedRenewalYearWithMeasures(Year.of(expectedRenewalYearWithMeasures.toLocalDate().getYear()));

            for (LongevityMeasures longevityMeasures : longevityMeasuresExporter.doExport(objectId))
                parkFacilityLongevityPlanAttribute.getLongevityMeasures().add(new LongevityMeasuresProperty(longevityMeasures));

            parkFacilityLongevityPlanAttribute.setNoteForLongevity(rs.getString("noteforlongevity"));
        }

        if (facilityAttribute instanceof PortAttribute) {
            PortAttribute portAttribute = (PortAttribute) facilityAttribute;

            String portFacilityDetailsType = rs.getString("portfacilitydetailstype");
            if (!rs.wasNull()) {
                Code code = new Code(portFacilityDetailsType);
                code.setCodeSpace(rs.getString("portfacilitydetail_codespace"));
                portAttribute.setPortFacilityDetailsType(code);
            }

            portAttribute.setPortName(rs.getString("portattribute_portname"));

            String portStatus = rs.getString("portstatus");
            if (!rs.wasNull()) {
                Code code = new Code(portStatus);
                code.setCodeSpace(rs.getString("portstatus_codespace"));
                portAttribute.setPortStatus(code);
            }

            portAttribute.setDistrict(rs.getString("district"));

            String grantType = rs.getString("portattribute_granttype");
            if (!rs.wasNull()) {
                Code code = new Code(grantType);
                code.setCodeSpace(rs.getString("portattribute_granttype_codespace"));
                portAttribute.setGrantType(code);
            }

            boolean isDesignated = rs.getBoolean("isdesignated");
            if (!rs.wasNull())
                portAttribute.setIsDesignated(isDesignated);

            int degradationLevel = rs.getInt("degradationlevel");
            if (!rs.wasNull())
                portAttribute.setDegradationLevel(degradationLevel);
        }

        if (facilityAttribute instanceof CargoHandlingFacility) {
            CargoHandlingFacility cargoHandlingFacility = (CargoHandlingFacility) facilityAttribute;

            String mainCargo = rs.getString("cargohandlingfacility_maincargo");
            if (!rs.wasNull()) {
                Code code = new Code(mainCargo);
                code.setCodeSpace(rs.getString("cargohandlingfacility_maincargo_codespace"));
                cargoHandlingFacility.setMainCargo(code);
            }

            cargoHandlingFacility.setMooringFacility(rs.getString("mooringfacility"));

            double liftableLoad = rs.getDouble("liftableload");
            if (!rs.wasNull()) {
                Measure measure = new Measure(liftableLoad);
                measure.setUom(rs.getString("liftableload_uom"));
                cargoHandlingFacility.setLiftableLoad(measure);
            }

            int ability = rs.getInt("ability");
            if (!rs.wasNull())
                cargoHandlingFacility.setAbility(ability);

            String packingName = rs.getString("packingname");
            if (!rs.wasNull()) {
                Code code = new Code(packingName);
                code.setCodeSpace(rs.getString("packingname_codespace"));
                cargoHandlingFacility.setPackingName(code);
            }

            Date acquisitionYear = rs.getDate("cargohandlingfacility_acquisitionyear");
            if (!rs.wasNull())
                cargoHandlingFacility.setAcquisitionYear(Year.of(acquisitionYear.toLocalDate().getYear()));

            double innerTotalFloorArea = rs.getDouble("cargohandlingfacility_innertotalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(innerTotalFloorArea);
                measure.setUom(rs.getString("cargohandlingfacility_innertotalfloorarea_uom"));
                cargoHandlingFacility.setInnerTotalFloorArea(measure);
            }

            double innerOfSiteArea = rs.getDouble("cargohandlingfacility_innerofsitearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(innerOfSiteArea);
                measure.setUom(rs.getString("cargohandlingfacility_innerofsitearea_uom"));
                cargoHandlingFacility.setInnerOfSiteArea(measure);
            }

            double outerOfTotalFloorArea = rs.getDouble("cargohandlingfacility_outeroftotalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(outerOfTotalFloorArea);
                measure.setUom(rs.getString("cargohandlingfacility_outeroftotalfloorarea_uom"));
                cargoHandlingFacility.setOuterOfTotalFloorArea(measure);
            }

            double outerSiteArea = rs.getDouble("cargohandlingfacility_outersitearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(outerSiteArea);
                measure.setUom(rs.getString("cargohandlingfacility_outersitearea_uom"));
                cargoHandlingFacility.setOuterSiteArea(measure);
            }

            String mainMaterial = rs.getString("cargohandlingfacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("cargohandlingfacility_mainmaterial_codespace"));
                cargoHandlingFacility.setMainMaterial(code);
            }

            int totalCost = rs.getInt("cargohandlingfacility_totalcost");
            if (!rs.wasNull())
                cargoHandlingFacility.setTotalCost(totalCost);

            cargoHandlingFacility.setNote(rs.getString("cargohandlingfacility_note"));
        }

        if (facilityAttribute instanceof CyberportMarinaAndPBS) {
            CyberportMarinaAndPBS cyberportMarinaAndPBS = (CyberportMarinaAndPBS) facilityAttribute;

            String geologicalType = rs.getString("cyberportmarinaandpbs_geologicaltype");
            if (!rs.wasNull()) {
                Code code = new Code(geologicalType);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_geologicaltype_codespace"));
                cyberportMarinaAndPBS.setGeologicalType(code);
            }

            cyberportMarinaAndPBS.setObstructingStructures(rs.getString("cyberportmarinaandpbs_obstructingstructures"));

            double mainPartLength = rs.getDouble("cyberportmarinaandpbs_mainpartlength");
            if (!rs.wasNull()) {
                Length length = new Length(mainPartLength);
                length.setUom(rs.getString("cyberportmarinaandpbs_mainpartlength_uom"));
                cyberportMarinaAndPBS.setMainPartLength(length);
            }

            double totalLength = rs.getDouble("cyberportmarinaandpbs_totallength");
            if (!rs.wasNull()) {
                Length length = new Length(totalLength);
                length.setUom(rs.getString("cyberportmarinaandpbs_totallength_uom"));
                cyberportMarinaAndPBS.setTotalLength(length);
            }

            double waveDissipatorLength = rs.getDouble("cyberportmarinaandpbs_wavedissipatorlength");
            if (!rs.wasNull()) {
                Length length = new Length(waveDissipatorLength);
                length.setUom(rs.getString("cyberportmarinaandpbs_wavedissipatorlength_uom"));
                cyberportMarinaAndPBS.setWaveDissipatorLength(length);
            }

            double facilityWidth = rs.getDouble("cyberportmarinaandpbs_facilitywidth");
            if (!rs.wasNull()) {
                Length length = new Length(facilityWidth);
                length.setUom(rs.getString("cyberportmarinaandpbs_facilitywidth_uom"));
                cyberportMarinaAndPBS.setFacilityWidth(length);
            }

            double apronWidth = rs.getDouble("cyberportmarinaandpbs_apronwidth");
            if (!rs.wasNull()) {
                Length length = new Length(apronWidth);
                length.setUom(rs.getString("cyberportmarinaandpbs_apronwidth_uom"));
                cyberportMarinaAndPBS.setApronWidth(length);
            }

            cyberportMarinaAndPBS.setRestrictionStructure(rs.getString("restrictionstructure"));

            double plannedDepth = rs.getDouble("cyberportmarinaandpbs_planneddepth");
            if (!rs.wasNull()) {
                Length length = new Length(plannedDepth);
                length.setUom(rs.getString("cyberportmarinaandpbs_planneddepth_uom"));
                cyberportMarinaAndPBS.setPlannedDepth(length);
            }

            double currentDepth = rs.getDouble("cyberportmarinaandpbs_currentdepth");
            if (!rs.wasNull()) {
                Length length = new Length(currentDepth);
                length.setUom(rs.getString("cyberportmarinaandpbs_currentdepth_uom"));
                cyberportMarinaAndPBS.setCurrentDepth(length);
            }

            double innerTotalFloorArea = rs.getDouble("cyberportmarinaandpbs_innertotalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(innerTotalFloorArea);
                measure.setUom(rs.getString("cyberportmarinaandpbs_innertotalfloorarea_uom"));
                cyberportMarinaAndPBS.setInnerTotalFloorArea(measure);
            }

            double innerOfSiteArea = rs.getDouble("cyberportmarinaandpbs_innerofsitearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(innerOfSiteArea);
                measure.setUom(rs.getString("cyberportmarinaandpbs_innerofsitearea_uom"));
                cyberportMarinaAndPBS.setInnerOfSiteArea(measure);
            }

            double outerOfTotalFloorArea = rs.getDouble("cyberportmarinaandpbs_outeroftotalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(outerOfTotalFloorArea);
                measure.setUom(rs.getString("cyberportmarinaandpbs_outeroftotalfloorarea_uom"));
                cyberportMarinaAndPBS.setOuterOfTotalFloorArea(measure);
            }

            double outerSiteArea = rs.getDouble("cyberportmarinaandpbs_outersitearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(outerSiteArea);
                measure.setUom(rs.getString("cyberportmarinaandpbs_outersitearea_uom"));
                cyberportMarinaAndPBS.setOuterSiteArea(measure);
            }

            double ceilingHeight = rs.getDouble("cyberportmarinaandpbs_ceilingheight");
            if (!rs.wasNull()) {
                Length length = new Length(ceilingHeight);
                length.setUom(rs.getString("cyberportmarinaandpbs_ceilingheight_uom"));
                cyberportMarinaAndPBS.setCeilingHeight(length);
            }

            double gravityResistant = rs.getDouble("cyberportmarinaandpbs_gravityresistant");
            if (!rs.wasNull()) {
                Measure measure = new Measure(gravityResistant);
                measure.setUom(rs.getString("cyberportmarinaandpbs_gravityresistant_uom"));
                cyberportMarinaAndPBS.setGravityResistant(measure);
            }

            String form = rs.getString("cyberportmarinaandpbs_form");
            if (!rs.wasNull()) {
                Code code = new Code(form);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_form_codespace"));
                cyberportMarinaAndPBS.setForm(code);
            }

            String areaType = rs.getString("cyberportmarinaandpbs_areatype");
            if (!rs.wasNull()) {
                Code code = new Code(areaType);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_areatype_codespace"));
                cyberportMarinaAndPBS.setAreaType(code);
            }

            String mainVessels = rs.getString("cyberportmarinaandpbs_mainvessels");
            if (!rs.wasNull()) {
                Code code = new Code(mainVessels);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_mainvessels_codespace"));
                cyberportMarinaAndPBS.setMainVessels(code);
            }

            boolean isDredged = rs.getBoolean("cyberportmarinaandpbs_isdredged");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setIsDredged(isDredged);

            double mooringPostWeight = rs.getDouble("cyberportmarinaandpbs_mooringpostweight");
            if (!rs.wasNull()) {
                Measure measure = new Measure(mooringPostWeight);
                measure.setUom(rs.getString("cyberportmarinaandpbs_mooringpostweight_uom"));
                cyberportMarinaAndPBS.setMooringPostWeight(measure);
            }

            int numberOfMooringPosts = rs.getInt("cyberportmarinaandpbs_numberofmooringposts");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setNumberOfMooringPosts(numberOfMooringPosts);

            int resistantMaterial = rs.getInt("cyberportmarinaandpbs_resistantmaterial");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setResistantMaterial(resistantMaterial);

            int lighting = rs.getInt("cyberportmarinaandpbs_lighting");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setLighting(lighting);

            int stairs = rs.getInt("cyberportmarinaandpbs_stairs");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setStairs(stairs);

            cyberportMarinaAndPBS.setLifesaving(rs.getString("lifesaving"));

            int lifesavingNumber = rs.getInt("lifesavingnumber");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setLifesavingNumber(lifesavingNumber);

            double bumper = rs.getDouble("cyberportmarinaandpbs_bumper");
            if (!rs.wasNull()) {
                Length length = new Length(bumper);
                length.setUom(rs.getString("cyberportmarinaandpbs_bumper_uom"));
                cyberportMarinaAndPBS.setBumper(length);
            }

            int numberOfVehicleBoardings = rs.getInt("cyberportmarinaandpbs_numberofvehicleboardings");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setNumberOfVehicleBoardings(numberOfVehicleBoardings);

            double vehicleBoardingWidth = rs.getDouble("cyberportmarinaandpbs_vehicleboardingwidth");
            if (!rs.wasNull()) {
                Length length = new Length(vehicleBoardingWidth);
                length.setUom(rs.getString("cyberportmarinaandpbs_vehicleboardingwidth_uom"));
                cyberportMarinaAndPBS.setVehicleBoardingWidth(length);
            }

            cyberportMarinaAndPBS.setShipType(rs.getString("cyberportmarinaandpbs_shiptype"));

            int numberOfSeats = rs.getInt("cyberportmarinaandpbs_numberofseats");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setNumberOfSeats(numberOfSeats);

            String mainCargo = rs.getString("cyberportmarinaandpbs_maincargo");
            if (!rs.wasNull()) {
                Code code = new Code(mainCargo);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_maincargo_codespace"));
                cyberportMarinaAndPBS.setMainCargo(code);
            }

            int storageCapacity = rs.getInt("cyberportmarinaandpbs_storagecapacity");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setStorageCapacity(storageCapacity);

            String storageCapacityUnit = rs.getString("cyberportmarinaandpbs_storagecapacityunit");
            if (!rs.wasNull()) {
                Code code = new Code(storageCapacityUnit);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_storagecapacityuni_codespace"));
                cyberportMarinaAndPBS.setStorageCapacityUnit(code);
            }

            String structureType = rs.getString("cyberportmarinaandpbs_structuretype");
            if (!rs.wasNull()) {
                Code code = new Code(structureType);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_structuretype_codespace"));
                cyberportMarinaAndPBS.setStructureType(code);
            }

            String mainMaterial = rs.getString("cyberportmarinaandpbs_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("cyberportmarinaandpbs_mainmaterial_codespace"));
                cyberportMarinaAndPBS.setMainMaterial(code);
            }

            int totalCost = rs.getInt("cyberportmarinaandpbs_totalcost");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setTotalCost(totalCost);

            int subsidy = rs.getInt("cyberportmarinaandpbs_subsidy");
            if (!rs.wasNull())
                cyberportMarinaAndPBS.setSubsidy(subsidy);

            cyberportMarinaAndPBS.setNote(rs.getString("cyberportmarinaandpbs_note"));
        }

        if (facilityAttribute instanceof HarborFacility) {
            HarborFacility harborFacility = (HarborFacility) facilityAttribute;

            String geologicalType = rs.getString("harborfacility_geologicaltype");
            if (!rs.wasNull()) {
                Code code = new Code(geologicalType);
                code.setCodeSpace(rs.getString("harborfacility_geologicaltype_codespace"));
                harborFacility.setGeologicalType(code);
            }

            harborFacility.setObstructingStructures(rs.getString("harborfacility_obstructingstructures"));

            double structuralLimitations = rs.getDouble("structurallimitations");
            if (!rs.wasNull()) {
                Length length = new Length(structuralLimitations);
                length.setUom(rs.getString("structurallimitations_uom"));
                harborFacility.setStructuralLimitations(length);
            }

            double lengthValue = rs.getDouble("harborfacility_length");
            if (!rs.wasNull()) {
                Length length = new Length(lengthValue);
                length.setUom(rs.getString("harborfacility_length_uom"));
                harborFacility.setLength(length);
            }

            double minimumWidth = rs.getDouble("harborfacility_minimumwidth");
            if (!rs.wasNull()) {
                Length length = new Length(minimumWidth);
                length.setUom(rs.getString("harborfacility_minimumwidth_uom"));
                harborFacility.setMinimumWidth(length);
            }

            double maximumWidth = rs.getDouble("maximumwidth");
            if (!rs.wasNull()) {
                Length length = new Length(maximumWidth);
                length.setUom(rs.getString("maximumwidth_uom"));
                harborFacility.setMaximumWidth(length);
            }

            double plannedDepth = rs.getDouble("harborfacility_planneddepth");
            if (!rs.wasNull()) {
                Length length = new Length(plannedDepth);
                length.setUom(rs.getString("harborfacility_planneddepth_uom"));
                harborFacility.setPlannedDepth(length);
            }

            double currentDepth = rs.getDouble("harborfacility_currentdepth");
            if (!rs.wasNull()) {
                Length length = new Length(currentDepth);
                length.setUom(rs.getString("harborfacility_currentdepth_uom"));
                harborFacility.setCurrentDepth(length);
            }

            boolean isDredged = rs.getBoolean("harborfacility_isdredged");
            if (!rs.wasNull())
                harborFacility.setIsDredged(isDredged);

            String areaType = rs.getString("harborfacility_areatype");
            if (!rs.wasNull()) {
                Code code = new Code(areaType);
                code.setCodeSpace(rs.getString("harborfacility_areatype_codespace"));
                harborFacility.setAreaType(code);
            }

            double innerArea = rs.getDouble("innerarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(innerArea);
                measure.setUom(rs.getString("innerarea_uom"));
                harborFacility.setInnerArea(measure);
            }

            double outerArea = rs.getDouble("outerarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(outerArea);
                measure.setUom(rs.getString("outerarea_uom"));
                harborFacility.setOuterArea(measure);
            }

            int totalCost = rs.getInt("harborfacility_totalcost");
            if (!rs.wasNull())
                harborFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("harborfacility_subsidy");
            if (!rs.wasNull())
                harborFacility.setSubsidy(subsidy);

            for (SplitValue splitValue : valueSplitter.split(rs.getString("harborfacility_note")))
                harborFacility.getNote().add(splitValue.result(0));
        }

        if (facilityAttribute instanceof MooringFacility) {
            MooringFacility mooringFacility = (MooringFacility) facilityAttribute;

            double mainPartLength = rs.getDouble("mooringfacility_mainpartlength");
            if (!rs.wasNull()) {
                Length length = new Length(mainPartLength);
                length.setUom(rs.getString("mooringfacility_mainpartlength_uom"));
                mooringFacility.setMainPartLength(length);
            }

            double totalLength = rs.getDouble("mooringfacility_totallength");
            if (!rs.wasNull()) {
                Length length = new Length(totalLength);
                length.setUom(rs.getString("mooringfacility_totallength_uom"));
                mooringFacility.setTotalLength(length);
            }

            double facilityWidth = rs.getDouble("mooringfacility_facilitywidth");
            if (!rs.wasNull()) {
                Length length = new Length(facilityWidth);
                length.setUom(rs.getString("mooringfacility_facilitywidth_uom"));
                mooringFacility.setFacilityWidth(length);
            }

            double apronWidth = rs.getDouble("mooringfacility_apronwidth");
            if (!rs.wasNull()) {
                Length length = new Length(apronWidth);
                length.setUom(rs.getString("mooringfacility_apronwidth_uom"));
                mooringFacility.setApronWidth(length);
            }

            double plannedDepth = rs.getDouble("mooringfacility_planneddepth");
            if (!rs.wasNull()) {
                Length length = new Length(plannedDepth);
                length.setUom(rs.getString("mooringfacility_planneddepth_uom"));
                mooringFacility.setPlannedDepth(length);
            }

            double currentDepth = rs.getDouble("mooringfacility_currentdepth");
            if (!rs.wasNull()) {
                Length length = new Length(currentDepth);
                length.setUom(rs.getString("mooringfacility_currentdepth_uom"));
                mooringFacility.setCurrentDepth(length);
            }

            double area = rs.getDouble("mooringfacility_area");
            if (!rs.wasNull()) {
                Measure measure = new Measure(area);
                measure.setUom(rs.getString("mooringfacility_area_uom"));
                mooringFacility.setArea(measure);
            }

            double ceilingHeight = rs.getDouble("mooringfacility_ceilingheight");
            if (!rs.wasNull()) {
                Length length = new Length(ceilingHeight);
                length.setUom(rs.getString("mooringfacility_ceilingheight_uom"));
                mooringFacility.setCeilingHeight(length);
            }

            double gravityResistant = rs.getDouble("mooringfacility_gravityresistant");
            if (!rs.wasNull()) {
                Measure measure = new Measure(gravityResistant);
                measure.setUom(rs.getString("mooringfacility_gravityresistant_uom"));
                mooringFacility.setGravityResistant(measure);
            }

            String form = rs.getString("mooringfacility_form");
            if (!rs.wasNull()) {
                Code code = new Code(form);
                code.setCodeSpace(rs.getString("mooringfacility_form_codespace"));
                mooringFacility.setForm(code);
            }

            String mainVessels = rs.getString("mooringfacility_mainvessels");
            if (!rs.wasNull()) {
                Code code = new Code(mainVessels);
                code.setCodeSpace(rs.getString("mooringfacility_mainvessels_codespace"));
                mooringFacility.setMainVessels(code);
            }

            double mooringPostWeight = rs.getDouble("mooringfacility_mooringpostweight");
            if (!rs.wasNull()) {
                Measure measure = new Measure(mooringPostWeight);
                measure.setUom(rs.getString("mooringfacility_mooringpostweight_uom"));
                mooringFacility.setMooringPostWeight(measure);
            }

            int numberOfMooringPosts = rs.getInt("mooringfacility_numberofmooringposts");
            if (!rs.wasNull())
                mooringFacility.setNumberOfMooringPosts(numberOfMooringPosts);

            int resistantMaterial = rs.getInt("mooringfacility_resistantmaterial");
            if (!rs.wasNull())
                mooringFacility.setResistantMaterial(resistantMaterial);

            int lighting = rs.getInt("mooringfacility_lighting");
            if (!rs.wasNull())
                mooringFacility.setLighting(lighting);

            int stairs = rs.getInt("mooringfacility_stairs");
            if (!rs.wasNull())
                mooringFacility.setStairs(stairs);

            mooringFacility.setLifesavingAppliances(rs.getString("lifesavingappliances"));

            int numberOfLifesavingAppliances = rs.getInt("numberoflifesavingappliances");
            if (!rs.wasNull())
                mooringFacility.setNumberOfLifesavingAppliances(numberOfLifesavingAppliances);

            double bumper = rs.getDouble("mooringfacility_bumper");
            if (!rs.wasNull()) {
                Length length = new Length(bumper);
                length.setUom(rs.getString("mooringfacility_bumper_uom"));
                mooringFacility.setBumper(length);
            }

            int numberOfVehicleBoardings = rs.getInt("mooringfacility_numberofvehicleboardings");
            if (!rs.wasNull())
                mooringFacility.setNumberOfVehicleBoardings(numberOfVehicleBoardings);

            double vehicleBoardingWidth = rs.getDouble("mooringfacility_vehicleboardingwidth");
            if (!rs.wasNull()) {
                Length length = new Length(vehicleBoardingWidth);
                length.setUom(rs.getString("mooringfacility_vehicleboardingwidth_uom"));
                mooringFacility.setVehicleBoardingWidth(length);
            }

            mooringFacility.setShipType(rs.getString("mooringfacility_shiptype"));

            int numberOfSeats = rs.getInt("mooringfacility_numberofseats");
            if (!rs.wasNull())
                mooringFacility.setNumberOfSeats(numberOfSeats);

            String mainCargo = rs.getString("mooringfacility_maincargo");
            if (!rs.wasNull()) {
                Code code = new Code(mainCargo);
                code.setCodeSpace(rs.getString("mooringfacility_maincargo_codespace"));
                mooringFacility.setMainCargo(code);
            }

            String structureType = rs.getString("mooringfacility_structuretype");
            if (!rs.wasNull()) {
                Code code = new Code(structureType);
                code.setCodeSpace(rs.getString("mooringfacility_structuretype_codespace"));
                mooringFacility.setStructureType(code);
            }

            String mainMaterial = rs.getString("mooringfacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("mooringfacility_mainmaterial_codespace"));
                mooringFacility.setMainMaterial(code);
            }

            int totalCost = rs.getInt("mooringfacility_totalcost");
            if (!rs.wasNull())
                mooringFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("mooringfacility_subsidy");
            if (!rs.wasNull())
                mooringFacility.setSubsidy(subsidy);

            mooringFacility.setNote(rs.getString("mooringfacility_note"));
        }

        if (facilityAttribute instanceof NavigationAssistanceFacility) {
            NavigationAssistanceFacility navigationAssistanceFacility = (NavigationAssistanceFacility) facilityAttribute;

            int totalCost = rs.getInt("navigationassistancefacility_totalcost");
            if (!rs.wasNull())
                navigationAssistanceFacility.setTotalCost(totalCost);

            navigationAssistanceFacility.setSubsidy(rs.getString("navigationassistancefacility_subsidy"));

            navigationAssistanceFacility.setNote(rs.getString("navigationassistancefacility_note"));
        }

        if (facilityAttribute instanceof PortEnvironmentalImprovementFacility) {
            PortEnvironmentalImprovementFacility portEnvironmentalImprovementFacility = (PortEnvironmentalImprovementFacility) facilityAttribute;

            portEnvironmentalImprovementFacility.setUsage(rs.getString("portenvironmentalimprovementfacility_usage"));

            double lengthValue = rs.getDouble("portenvironmentalimprovementfacility_length");
            if (!rs.wasNull()) {
                Length length = new Length(lengthValue);
                length.setUom(rs.getString("portenvironmentalimprovementfacility_length_uom"));
                portEnvironmentalImprovementFacility.setLength(length);
            }

            double area = rs.getDouble("portenvironmentalimprovementfacility_area");
            if (!rs.wasNull()) {
                Measure measure = new Measure(area);
                measure.setUom(rs.getString("portenvironmentalimprovementfacility_area_uom"));
                portEnvironmentalImprovementFacility.setArea(measure);
            }

            double totalFloorArea = rs.getDouble("portenvironmentalimprovementfacility_totalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalFloorArea);
                measure.setUom(rs.getString("portenvironmentalimprovementfacility_totalfloorarea_uom"));
                portEnvironmentalImprovementFacility.setTotalFloorArea(measure);
            }

            int totalCost = rs.getInt("portenvironmentalimprovementfacility_totalcost");
            if (!rs.wasNull())
                portEnvironmentalImprovementFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("portenvironmentalimprovementfacility_subsidy");
            if (!rs.wasNull())
                portEnvironmentalImprovementFacility.setSubsidy(subsidy);

            portEnvironmentalImprovementFacility.setNote(rs.getString("portenvironmentalimprovementfacility_note"));
        }

        if (facilityAttribute instanceof PortManagementFacility) {
            PortManagementFacility portManagementFacility = (PortManagementFacility) facilityAttribute;

            double totalFloorArea = rs.getDouble("portmanagementfacility_totalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalFloorArea);
                measure.setUom(rs.getString("portmanagementfacility_totalfloorarea_uom"));
                portManagementFacility.setTotalFloorArea(measure);
            }

            int numberOfShipTypes = rs.getInt("numberofshiptypes");
            if (!rs.wasNull())
                portManagementFacility.setNumberOfShipTypes(numberOfShipTypes);

            String unitOfShipType = rs.getString("unitofshiptype");
            if (!rs.wasNull()) {
                Code code = new Code(unitOfShipType);
                code.setCodeSpace(rs.getString("unitofshiptype_codespace"));
                portManagementFacility.setUnitOfShipType(code);
            }

            int loadingCapacity = rs.getInt("loadingcapacity");
            if (!rs.wasNull())
                portManagementFacility.setLoadingCapacity(loadingCapacity);

            Date acquisitionYear = rs.getDate("portmanagementfacility_acquisitionyear");
            if (!rs.wasNull())
                portManagementFacility.setAcquisitionYear(Year.of(acquisitionYear.toLocalDate().getYear()));

            portManagementFacility.setUsage(rs.getString("portmanagementfacility_usage"));

            int totalCost = rs.getInt("portmanagementfacility_totalcost");
            if (!rs.wasNull())
                portManagementFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("portmanagementfacility_subsidy");
            if (!rs.wasNull())
                portManagementFacility.setSubsidy(subsidy);

            portManagementFacility.setNote(rs.getString("portmanagementfacility_note"));
        }

        if (facilityAttribute instanceof PortPassengerFacility) {
            PortPassengerFacility portPassengerFacility = (PortPassengerFacility) facilityAttribute;

            double lengthValue = rs.getDouble("portpassengerfacility_length");
            if (!rs.wasNull()) {
                Length length = new Length(lengthValue);
                length.setUom(rs.getString("portpassengerfacility_length_uom"));
                portPassengerFacility.setLength(length);
            }

            double width = rs.getDouble("portpassengerfacility_width");
            if (!rs.wasNull()) {
                Length length = new Length(width);
                length.setUom(rs.getString("portpassengerfacility_width_uom"));
                portPassengerFacility.setWidth(length);
            }

            String mainMaterial = rs.getString("portpassengerfacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("portpassengerfacility_mainmaterial_codespace"));
                portPassengerFacility.setMainMaterial(code);
            }

            double totalFloorArea = rs.getDouble("portpassengerfacility_totalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalFloorArea);
                measure.setUom(rs.getString("portpassengerfacility_totalfloorarea_uom"));
                portPassengerFacility.setTotalFloorArea(measure);
            }

            Date acquisitionYear = rs.getDate("portpassengerfacility_acquisitionyear");
            if (!rs.wasNull())
                portPassengerFacility.setAcquisitionYear(Year.of(acquisitionYear.toLocalDate().getYear()));

            int totalCost = rs.getInt("portpassengerfacility_totalcost");
            if (!rs.wasNull())
                portPassengerFacility.setTotalCost(totalCost);

            portPassengerFacility.setNote(rs.getString("portpassengerfacility_note"));
        }

        if (facilityAttribute instanceof PortPollutionControlFacility) {
            PortPollutionControlFacility portPollutionControlFacility = (PortPollutionControlFacility) facilityAttribute;

            double lengthValue = rs.getDouble("portpollutioncontrolfacility_length");
            if (!rs.wasNull()) {
                Length length = new Length(lengthValue);
                length.setUom(rs.getString("portpollutioncontrolfacility_length_uom"));
                portPollutionControlFacility.setLength(length);
            }

            double width = rs.getDouble("portpollutioncontrolfacility_width");
            if (!rs.wasNull()) {
                Length length = new Length(width);
                length.setUom(rs.getString("portpollutioncontrolfacility_width_uom"));
                portPollutionControlFacility.setWidth(length);
            }

            double crossSectionalArea = rs.getDouble("crosssectionalarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(crossSectionalArea);
                measure.setUom(rs.getString("crosssectionalarea_uom"));
                portPollutionControlFacility.setCrossSectionalArea(measure);
            }

            double area = rs.getDouble("portpollutioncontrolfacility_area");
            if (!rs.wasNull()) {
                Measure measure = new Measure(area);
                measure.setUom(rs.getString("portpollutioncontrolfacility_area_uom"));
                portPollutionControlFacility.setArea(measure);
            }

            double height = rs.getDouble("height");
            if (!rs.wasNull()) {
                Length length = new Length(height);
                length.setUom(rs.getString("height_uom"));
                portPollutionControlFacility.setHeight(length);
            }

            String mainMaterial = rs.getString("portpollutioncontrolfacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("portpollutioncontrolfacility_mainmaterial_codespace"));
                portPollutionControlFacility.setMainMaterial(code);
            }

            int totalCost = rs.getInt("portpollutioncontrolfacility_totalcost");
            if (!rs.wasNull())
                portPollutionControlFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("portpollutioncontrolfacility_subsidy");
            if (!rs.wasNull())
                portPollutionControlFacility.setSubsidy(subsidy);

            portPollutionControlFacility.setNote(rs.getString("portpollutioncontrolfacility_note"));
        }

        if (facilityAttribute instanceof PortProtectiveFacility) {
            PortProtectiveFacility portProtectiveFacility = (PortProtectiveFacility) facilityAttribute;

            String structureType = rs.getString("portprotectivefacility_structuretype");
            if (!rs.wasNull()) {
                Code code = new Code(structureType);
                code.setCodeSpace(rs.getString("portprotectivefacility_structuretype_codespace"));
                portProtectiveFacility.setStructureType(code);
            }

            String mainMaterial = rs.getString("portprotectivefacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("portprotectivefacility_mainmaterial_codespace"));
                portProtectiveFacility.setMainMaterial(code);
            }

            int totalCost = rs.getInt("portprotectivefacility_totalcost");
            if (!rs.wasNull())
                portProtectiveFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("portprotectivefacility_subsidy");
            if (!rs.wasNull())
                portProtectiveFacility.setSubsidy(subsidy);

            for (SplitValue splitValue : valueSplitter.split(rs.getString("portprotectivefacility_note")))
                portProtectiveFacility.getNote().add(splitValue.result(0));
        }

        if (facilityAttribute instanceof PortStorageFacility) {
            PortStorageFacility portStorageFacility = (PortStorageFacility) facilityAttribute;

            double innerTotalFloorArea = rs.getDouble("portstoragefacility_innertotalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(innerTotalFloorArea);
                measure.setUom(rs.getString("portstoragefacility_innertotalfloorarea_uom"));
                portStorageFacility.setInnerTotalFloorArea(measure);
            }

            double innerOfSiteArea = rs.getDouble("portstoragefacility_innerofsitearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(innerOfSiteArea);
                measure.setUom(rs.getString("portstoragefacility_innerofsitearea_uom"));
                portStorageFacility.setInnerOfSiteArea(measure);
            }

            double outerOfTotalFloorArea = rs.getDouble("portstoragefacility_outeroftotalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(outerOfTotalFloorArea);
                measure.setUom(rs.getString("portstoragefacility_outeroftotalfloorarea_uom"));
                portStorageFacility.setOuterOfTotalFloorArea(measure);
            }

            double outerSiteArea = rs.getDouble("portstoragefacility_outersitearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(outerSiteArea);
                measure.setUom(rs.getString("portstoragefacility_outersitearea_uom"));
                portStorageFacility.setOuterSiteArea(measure);
            }

            String mainCargo = rs.getString("portstoragefacility_maincargo");
            if (!rs.wasNull()) {
                Code code = new Code(mainCargo);
                code.setCodeSpace(rs.getString("portstoragefacility_maincargo_codespace"));
                portStorageFacility.setMainCargo(code);
            }

            int storageCapacity = rs.getInt("portstoragefacility_storagecapacity");
            if (!rs.wasNull())
                portStorageFacility.setStorageCapacity(storageCapacity);

            String storageCapacityUnit = rs.getString("portstoragefacility_storagecapacityunit");
            if (!rs.wasNull()) {
                Code code = new Code(storageCapacityUnit);
                code.setCodeSpace(rs.getString("portstoragefacility_storagecapacityuni_codespace"));
                portStorageFacility.setStorageCapacityUnit(code);
            }

            String mainMaterial = rs.getString("portstoragefacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("portstoragefacility_mainmaterial_codespace"));
                portStorageFacility.setMainMaterial(code);
            }

            int totalCost = rs.getInt("portstoragefacility_totalcost");
            if (!rs.wasNull())
                portStorageFacility.setTotalCost(totalCost);

            portStorageFacility.setNote(rs.getString("portstoragefacility_note"));
        }

        if (facilityAttribute instanceof PortTransportationFacility) {
            PortTransportationFacility portTransportationFacility = (PortTransportationFacility) facilityAttribute;

            String structureType = rs.getString("porttransportationfacility_structuretype");
            if (!rs.wasNull()) {
                Code code = new Code(structureType);
                code.setCodeSpace(rs.getString("porttransportationfacility_structuretype_codespace"));
                portTransportationFacility.setStructureType(code);
            }

            portTransportationFacility.setStartingPoint(rs.getString("startingpoint"));

            double lengthValue = rs.getDouble("porttransportationfacility_length");
            if (!rs.wasNull()) {
                Length length = new Length(lengthValue);
                length.setUom(rs.getString("porttransportationfacility_length_uom"));
                portTransportationFacility.setLength(length);
            }

            double area = rs.getDouble("porttransportationfacility_area");
            if (!rs.wasNull()) {
                Measure measure = new Measure(area);
                measure.setUom(rs.getString("porttransportationfacility_area_uom"));
                portTransportationFacility.setArea(measure);
            }

            double beddingWidth = rs.getDouble("beddingwidth");
            if (!rs.wasNull()) {
                Length length = new Length(beddingWidth);
                length.setUom(rs.getString("beddingwidth_uom"));
                portTransportationFacility.setBeddingWidth(length);
            }

            int numberOfLanes = rs.getInt("numberoflanes");
            if (!rs.wasNull())
                portTransportationFacility.setNumberOfLanes(numberOfLanes);

            int parkingLotCapacityOfBus = rs.getInt("parkinglotcapacityofbus");
            if (!rs.wasNull())
                portTransportationFacility.setParkingLotCapacityOfBus(parkingLotCapacityOfBus);

            int parkingLotCapacityOfCars = rs.getInt("parkinglotcapacityofcars");
            if (!rs.wasNull())
                portTransportationFacility.setParkingLotCapacityOfCars(parkingLotCapacityOfCars);

            String routeType = rs.getString("routetype");
            if (!rs.wasNull()) {
                Code code = new Code(routeType);
                code.setCodeSpace(rs.getString("routetype_codespace"));
                portTransportationFacility.setRouteType(code);
            }

            double heightToDigit = rs.getDouble("heighttodigit");
            if (!rs.wasNull()) {
                Length length = new Length(heightToDigit);
                length.setUom(rs.getString("heighttodigit_uom"));
                portTransportationFacility.setHeightToDigit(length);
            }

            double heightLimit = rs.getDouble("heightlimit");
            if (!rs.wasNull()) {
                Length length = new Length(heightLimit);
                length.setUom(rs.getString("heightlimit_uom"));
                portTransportationFacility.setHeightLimit(length);
            }

            double minimumWidth = rs.getDouble("porttransportationfacility_minimumwidth");
            if (!rs.wasNull()) {
                Length length = new Length(minimumWidth);
                length.setUom(rs.getString("porttransportationfacility_minimumwidth_uom"));
                portTransportationFacility.setMinimumWidth(length);
            }

            double minimumDepth = rs.getDouble("minimumdepth");
            if (!rs.wasNull()) {
                Length length = new Length(minimumDepth);
                length.setUom(rs.getString("minimumdepth_uom"));
                portTransportationFacility.setMinimumDepth(length);
            }

            int numberOfAircraftParkingSpaces = rs.getInt("numberofaircraftparkingspace");
            if (!rs.wasNull())
                portTransportationFacility.setNumberOfAircraftParkingSpaces(numberOfAircraftParkingSpaces);

            String pavementType = rs.getString("pavementtype");
            if (!rs.wasNull()) {
                Code code = new Code(pavementType);
                code.setCodeSpace(rs.getString("pavementtype_codespace"));
                portTransportationFacility.setPavementType(code);
            }

            String mainCargo = rs.getString("porttransportationfacility_maincargo");
            if (!rs.wasNull()) {
                Code code = new Code(mainCargo);
                code.setCodeSpace(rs.getString("porttransportationfacility_maincargo_codespace"));
                portTransportationFacility.setMainCargo(code);
            }

            int totalCost = rs.getInt("porttransportationfacility_totalcost");
            if (!rs.wasNull())
                portTransportationFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("porttransportationfacility_subsidy");
            if (!rs.wasNull())
                portTransportationFacility.setSubsidy(subsidy);

            portTransportationFacility.setNote(rs.getString("porttransportationfacility_note"));
        }

        if (facilityAttribute instanceof PortWasteTreatmentFacility) {
            PortWasteTreatmentFacility portWasteTreatmentFacility = (PortWasteTreatmentFacility) facilityAttribute;

            String structureType = rs.getString("portwastetreatmentfacility_structuretype");
            if (!rs.wasNull()) {
                Code code = new Code(structureType);
                code.setCodeSpace(rs.getString("portwastetreatmentfacility_structuretype_codespace"));
                portWasteTreatmentFacility.setStructureType(code);
            }

            double perimeter = rs.getDouble("perimeter");
            if (!rs.wasNull()) {
                Length length = new Length(perimeter);
                length.setUom(rs.getString("perimeter_uom"));
                portWasteTreatmentFacility.setPerimeter(length);
            }

            double mainPartLength = rs.getDouble("portwastetreatmentfacility_mainpartlength");
            if (!rs.wasNull()) {
                Length length = new Length(mainPartLength);
                length.setUom(rs.getString("portwastetreatmentfacility_mainpartlength_uom"));
                portWasteTreatmentFacility.setMainPartLength(length);
            }

            double innerShoreLength = rs.getDouble("innershorelength");
            if (!rs.wasNull()) {
                Length length = new Length(innerShoreLength);
                length.setUom(rs.getString("innershorelength_uom"));
                portWasteTreatmentFacility.setInnerShoreLength(length);
            }

            double ceilingHeight = rs.getDouble("portwastetreatmentfacility_ceilingheight");
            if (!rs.wasNull()) {
                Length length = new Length(ceilingHeight);
                length.setUom(rs.getString("portwastetreatmentfacility_ceilingheight_uom"));
                portWasteTreatmentFacility.setCeilingHeight(length);
            }

            double waveDissipatorLength = rs.getDouble("portwastetreatmentfacility_wavedissipatorlength");
            if (!rs.wasNull()) {
                Length length = new Length(waveDissipatorLength);
                length.setUom(rs.getString("portwastetreatmentfacility_wavedissipatorlength_uom"));
                portWasteTreatmentFacility.setWaveDissipatorLength(length);
            }

            String mainMaterial = rs.getString("portwastetreatmentfacility_mainmaterial");
            if (!rs.wasNull()) {
                Code code = new Code(mainMaterial);
                code.setCodeSpace(rs.getString("portwastetreatmentfacility_mainmaterial_codespace"));
                portWasteTreatmentFacility.setMainMaterial(code);
            }

            String wasteType = rs.getString("wastetype");
            if (!rs.wasNull()) {
                Code code = new Code(wasteType);
                code.setCodeSpace(rs.getString("wastetype_codespace"));
                portWasteTreatmentFacility.setWasteType(code);
            }

            double plannedDisposalArea = rs.getDouble("planneddisposalarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(plannedDisposalArea);
                measure.setUom(rs.getString("planneddisposalarea_uom"));
                portWasteTreatmentFacility.setPlannedDisposalArea(measure);
            }

            int plannedDisposalAmount = rs.getInt("planneddisposalamount");
            if (!rs.wasNull())
                portWasteTreatmentFacility.setPlannedDisposalAmount(plannedDisposalAmount);

            int receivingCapacity = rs.getInt("receivingcapacity");
            if (!rs.wasNull())
                portWasteTreatmentFacility.setReceivingCapacity(receivingCapacity);

            portWasteTreatmentFacility.setShipType(rs.getString("portwastetreatmentfacility_shiptype"));

            String unitOfReceivingCapacity = rs.getString("unitofreceivingcapacity");
            if (!rs.wasNull()) {
                Code code = new Code(unitOfReceivingCapacity);
                code.setCodeSpace(rs.getString("unitofreceivingcap_codespace"));
                portWasteTreatmentFacility.setUnitOfReceivingCapacity(code);
            }

            Date acquisitionYear = rs.getDate("portwastetreatmentfacility_acquisitionyear");
            if (!rs.wasNull())
                portWasteTreatmentFacility.setAcquisitionYear(Year.of(acquisitionYear.toLocalDate().getYear()));

            int totalCost = rs.getInt("portwastetreatmentfacility_totalcost");
            if (!rs.wasNull())
                portWasteTreatmentFacility.setTotalCost(totalCost);

            int subsidy = rs.getInt("portwastetreatmentfacility_subsidy");
            if (!rs.wasNull())
                portWasteTreatmentFacility.setSubsidy(subsidy);

            portWasteTreatmentFacility.setNote(rs.getString("portwastetreatmentfacility_note"));
        }

        if (facilityAttribute instanceof PortWelfareFacility) {
            PortWelfareFacility portWelfareFacility = (PortWelfareFacility) facilityAttribute;

            double totalFloorArea = rs.getDouble("portwelfarefacility_totalfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalFloorArea);
                measure.setUom(rs.getString("portwelfarefacility_totalfloorarea_uom"));
                portWelfareFacility.setTotalFloorArea(measure);
            }

            int totalCost = rs.getInt("portwelfarefacility_totalcost");
            if (!rs.wasNull())
                portWelfareFacility.setTotalCost(totalCost);

            portWelfareFacility.setNote(rs.getString("portwelfarefacility_note"));
        }

        if (facilityAttribute instanceof ShipServiceFacility) {
            ShipServiceFacility shipServiceFacility = (ShipServiceFacility) facilityAttribute;

            shipServiceFacility.setShipType(rs.getString("shipservicefacility_shiptype"));

            int supplyAbility = rs.getInt("supplyability");
            if (!rs.wasNull())
                shipServiceFacility.setSupplyAbility(supplyAbility);

            String supplyAbilityUnit = rs.getString("supplyabilityunit");
            if (!rs.wasNull()) {
                Code code = new Code(supplyAbilityUnit);
                code.setCodeSpace(rs.getString("supplyabilityunit_codespace"));
                shipServiceFacility.setSupplyAbilityUnit(code);
            }

            shipServiceFacility.setMooringPlace(rs.getString("mooringplace"));

            double lengthValue = rs.getDouble("shipservicefacility_length");
            if (!rs.wasNull()) {
                Length length = new Length(lengthValue);
                length.setUom(rs.getString("shipservicefacility_length_uom"));
                shipServiceFacility.setLength(length);
            }

            double width = rs.getDouble("shipservicefacility_width");
            if (!rs.wasNull()) {
                Length length = new Length(width);
                length.setUom(rs.getString("shipservicefacility_width_uom"));
                shipServiceFacility.setWidth(length);
            }

            double area = rs.getDouble("shipservicefacility_area");
            if (!rs.wasNull()) {
                Measure measure = new Measure(area);
                measure.setUom(rs.getString("shipservicefacility_area_uom"));
                shipServiceFacility.setArea(measure);
            }

            Date acquisitionYear = rs.getDate("shipservicefacility_acquisitionyear");
            if (!rs.wasNull())
                shipServiceFacility.setAcquisitionYear(Year.of(acquisitionYear.toLocalDate().getYear()));

            int totalCost = rs.getInt("shipservicefacility_totalcost");
            if (!rs.wasNull())
                shipServiceFacility.setTotalCost(totalCost);

            shipServiceFacility.setNote(rs.getString("shipservicefacility_note"));
        }
        return facilityAttribute;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        for (PreparedStatement ps : psByParent.values())
            ps.close();
    }
}
