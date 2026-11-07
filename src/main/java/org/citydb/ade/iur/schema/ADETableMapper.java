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

package org.citydb.ade.iur.schema;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ADETableMapper {
    private HashMap<String, Set<String>> tableColumns;

    public void populateTableColumns(SchemaMapper schemaMapper) {
        tableColumns = new HashMap<String, Set<String>>() {{
            put(schemaMapper.getTableName(ADETable.ABSTRACTDISTRICTPLAN).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
                add("OBJECTIVES");
                add("POLICY");
            }});
            put(schemaMapper.getTableName(ADETable.ABSTRACTD_TO_DISTRICTD).toUpperCase(), new LinkedHashSet<String>() {{
                add("ABSTRACTDISTRICTPLAN_ID");
                add("DISTRICTDEVELOPMENTPLA_ID");
            }});
            put(schemaMapper.getTableName(ADETable.ABSTRACTD_TO_ZONE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ABSTRACTDISTRICTPLAN_ID");
                add("ZONE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.AGENCY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADDRESS");
                add("EMAIL");
                add("FAREURL");
                add("LANGUAGE");
                add("LANGUAGE_CODESPACE");
                add("NAME");
                add("OFFICIALNAME");
                add("PHONE");
                add("PRESIDENTNAME");
                add("PRESIDENTPOSITION");
                add("TIMEZONE");
                add("TIMEZONE_CODESPACE");
                add("URL");
                add("ZIPNUMBER");
            }});
            put(schemaMapper.getTableName(ADETable.AREACLASSIFICATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("POPULATION");
            }});
            put(schemaMapper.getTableName(ADETable.AREAOFANNUALDIVERSIONS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("LANDUSEDIVER_AREAOFANNUAL_ID");
                add("YEAR");
            }});
            put(schemaMapper.getTableName(ADETable.ATTRIBUTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AGENCY_ID");
                add("EMAIL");
                add("ISAUTHORITY");
                add("ISOPERATOR");
                add("ISPRODUCER");
                add("ORGANIZATIONNAME");
                add("PHONENUMBER");
                add("ROUTE_ID");
                add("TRIP_ID");
                add("URL");
            }});
            put(schemaMapper.getTableName(ADETable.BOUNDARY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OFFSET_");
                add("OFFSET__UOM");
                add("OFFSETDIRECTION");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGDATAQUALITYATTRIB_ID");
                add("BUILDINGDETAILATTRIBUTE_ID");
                add("BUILDINGIDATTRIBUTE_ID");
                add("LARGECUSTOMERFACILITYATTR_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGDATAQUALITYATT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPEARANCESRCDESC");
                add("APPEARANCESRCDESC_CODESPACE");
                add("GEOMETRYSRCDESC");
                add("GEOMETRYSRCDESC_CODESPACE");
                add("LOD1HEIGHTTYPE");
                add("LOD1HEIGHTTYPE_CODESPACE");
                add("LODTYPE");
                add("SRCSCALE");
                add("SRCSCALE_CODESPACE");
                add("THEMATICSRCDESC");
                add("THEMATICSRCDESC_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGDETAILATTRIBUT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREACLASSIFICATION_CODESPACE");
                add("AREACLASSIFICATIONTYPE");
                add("BASEMENTFIRSTUSAGE");
                add("BASEMENTFIRSTUSAGE_CODESPACE");
                add("BASEMENTSECONDUSAG_CODESPACE");
                add("BASEMENTSECONDUSAGE");
                add("BASEMENTUSAGE");
                add("BASEMENTUSAGE_CODESPACE");
                add("BUILDINGCOVERAGERATE");
                add("BUILDINGFOOTPRINTAREA");
                add("BUILDINGFOOTPRINTAREA_UOM");
                add("BUILDINGHEIGHT");
                add("BUILDINGHEIGHT_UOM");
                add("BUILDINGROOFEDGEAREA");
                add("BUILDINGROOFEDGEAREA_UOM");
                add("BUILDINGSTRUCTUREO_CODESPACE");
                add("BUILDINGSTRUCTUREORGTYPE");
                add("BUILDINGSTRUCTURET_CODESPACE");
                add("BUILDINGSTRUCTURETYPE");
                add("DETAILEDUSAGE");
                add("DETAILEDUSAGE2");
                add("DETAILEDUSAGE2_CODESPACE");
                add("DETAILEDUSAGE3");
                add("DETAILEDUSAGE3_CODESPACE");
                add("DETAILEDUSAGE_CODESPACE");
                add("DEVELOPMENTAREA");
                add("DEVELOPMENTAREA_UOM");
                add("DISTRICTSANDZONEST_CODESPACE");
                add("DISTRICTSANDZONESTYPE");
                add("EAVEHEIGHT");
                add("EAVEHEIGHT_UOM");
                add("FIREPROOFSTRUCTURE_CODESPACE");
                add("FIREPROOFSTRUCTURETYPE");
                add("FLOORAREARATE");
                add("GROUNDFLOORUSAGE");
                add("GROUNDFLOORUSAGE_CODESPACE");
                add("IMPLEMENTINGBODY");
                add("LANDUSETYPE");
                add("LANDUSETYPE_CODESPACE");
                add("MAJORUSAGE");
                add("MAJORUSAGE2");
                add("MAJORUSAGE2_CODESPACE");
                add("MAJORUSAGE_CODESPACE");
                add("NOTE");
                add("ORGUSAGE");
                add("ORGUSAGE2");
                add("ORGUSAGE2_CODESPACE");
                add("ORGUSAGE_CODESPACE");
                add("REFERENCE");
                add("SECONDFLOORUSAGE");
                add("SECONDFLOORUSAGE_CODESPACE");
                add("SERIALNUMBEROFBUILDINGCERTIF");
                add("SITEAREA");
                add("SITEAREA_UOM");
                add("SPECIFIEDBUILDINGCOVERAGERAT");
                add("SPECIFIEDFLOORAREARATE");
                add("STANDARDFLOORAREARATE");
                add("SURVEYYEAR");
                add("THIRDFLOORUSAGE");
                add("THIRDFLOORUSAGE_CODESPACE");
                add("TOTALFLOORAREA");
                add("TOTALFLOORAREA_UOM");
                add("URBANPLANTYPE");
                add("URBANPLANTYPE_CODESPACE");
                add("VACANCY");
                add("VACANCY_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGDISASTERRISKAT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREATYPE");
                add("AREATYPE_CODESPACE");
                add("DESCRIPTION");
                add("DESCRIPTION_CODESPACE");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGFLOODINGRISKAT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEPTH");
                add("DEPTH_UOM");
                add("OBJECTCLASS_ID");
                add("RANK");
                add("RANK_CODESPACE");
                add("RANKORG");
                add("RANKORG_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGIDATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BRANCHID");
                add("BUILDINGID");
                add("CITY");
                add("CITY_CODESPACE");
                add("PARTID");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGRIVERFLOODINGR).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADMINTYPE");
                add("ADMINTYPE_CODESPACE");
                add("DURATION");
                add("DURATION_UOM");
                add("SCALE");
                add("SCALE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING_TO_BUILDINGD).toUpperCase(), new LinkedHashSet<String>() {{
                add("BUILDINGDISASTERRISKAT_ID");
                add("BUILDING_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("BUILDING_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CALENDAR).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ENDDATE");
                add("FRIDAY");
                add("MONDAY");
                add("SATURDAY");
                add("STARTDATE");
                add("SUNDAY");
                add("THURSDAY");
                add("TUESDAY");
                add("WEDNESDAY");
            }});
            put(schemaMapper.getTableName(ADETable.CALENDARDATE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CALENDAR_ID");
                add("DATE_");
                add("EXCEPTIONTYPE");
                add("EXCEPTIONTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.CENSUSBLOCK).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DAYTIMEPOPULATION");
                add("DAYTIMEPOPULATIONDENSITY");
                add("NUMBEROFMAINHOUSEHOLDS");
                add("NUMBEROFORDINARYHOUSEHOLDS");
            }});
            put(schemaMapper.getTableName(ADETable.CITYFURNITUREATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITYFURNITUREDATAQUALI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPEARANCESRCDESC");
                add("APPEARANCESRCDESC_CODESPACE");
                add("GEOMETRYSRCDESC");
                add("GEOMETRYSRCDESC_CODESPACE");
                add("LODTYPE");
                add("SRCSCALE");
                add("SRCSCALE_CODESPACE");
                add("THEMATICSRCDESC");
                add("THEMATICSRCDESC_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.CITYFURNITUREDETAILATT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("FACILITYTYPE");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJECT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("VEGETATIONDATAQUALITYATTR_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FISCALYEAROFPUBLICATION");
                add("LANGUAGE");
                add("LANGUAGE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_1).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FISCALYEAROFPUBLICATION");
                add("LANGUAGE");
                add("LANGUAGE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_2).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FISCALYEAROFPUBLICATION");
                add("LANGUAGE");
                add("LANGUAGE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJECTGROUP_3).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITY_FURNITURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CITYFURNITUREDATAQUALITYA_ID");
                add("CITYFURNITUREDETAILATTRIB_ID");
            }});
            put(schemaMapper.getTableName(ADETable.COLLECTIVEFACILITIESFO).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("HOUSINGFACILITIES");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMFLOORAREARATE");
                add("PUBLICFACILITIES");
                add("SUPECIFICBUSINESSFACILITIES");
                add("UTILITYFACILITIES");
            }});
            put(schemaMapper.getTableName(ADETable.COLLECTIVEFACILITIES_2).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("HOUSINGFACILITIES");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMFLOORAREARATE");
                add("PUBLICFACILITIES");
                add("SUPECIFICBUSINESSFACILITIES");
                add("UTILITYFACILITIES");
            }});
            put(schemaMapper.getTableName(ADETable.COLLECTIVEFACILITIES_3).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("HOUSINGFACILITIES");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMFLOORAREARATE");
                add("PUBLICFACILITIES");
                add("SUPECIFICBUSINESSFACILITIES");
                add("UTILITYFACILITIES");
            }});
            put(schemaMapper.getTableName(ADETable.COLLECTIVEGOVERNMENTAN).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGCOVERAGERATE");
                add("FLOORAREARATE");
                add("PUBLICFACILITIESALLOCATIONPO");
                add("SCHEDULEDEXECUTOR");
            }});
            put(schemaMapper.getTableName(ADETable.COLLECTIVEHOUSINGFACIL).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGCOVERAGERATE");
                add("FLOORAREARATE");
                add("NUMBEROFHIGHRISEHOUSING");
                add("NUMBEROFLOWRISEHOUSING");
                add("NUMBEROFMIDDLERISEHOUSING");
                add("PUBLICFACILITIESALLOCATIONPO");
                add("SCHEDULEDEXECUTOR");
                add("TOTALNUMBEROFHOUSING");
            }});
            put(schemaMapper.getTableName(ADETable.COLLECTIVEURBANDISASTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMFLOORAREARATE");
                add("SPECIFICUTILITYANDPUBLICFACI");
            }});
            put(schemaMapper.getTableName(ADETable.DESCRIPTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("FREQUENCYOFSERVICE");
                add("NUMBEROFCUSTOMERS");
            }});
            put(schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BENEFITAREA");
                add("BENEFITAREA_UOM");
                add("BENEFITPERIOD");
                add("COMPLETEDAREA");
                add("COMPLETEDAREA_UOM");
                add("COST");
                add("DATEOFDECISION");
                add("DATEOFDESIGNATIONFORTEMPORAR");
                add("MAINPURPOSE");
                add("MAINPURPOSE_CODESPACE");
                add("ONGOINGAREA");
                add("ONGOINGAREA_UOM");
                add("PLANNEDAREA");
                add("PLANNEDAREA_UOM");
                add("STATUS");
                add("STATUS_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.DISASTERDAMAGE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DAMAGEDAREA");
                add("DAMAGEDAREA_UOM");
                add("MAXIMUMRAINFALLPERHOUR");
                add("NUMBEROFDAMAGEDHOUSES");
                add("NUMBEROFHOUSESFLOODEDABOVEFL");
                add("NUMBEROFHOUSESFLOODEDBELOWFL");
                add("TOTALRAINFALL");
            }});
            put(schemaMapper.getTableName(ADETable.DISASTERPREVENTIONBASE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CAPACITY");
            }});
            put(schemaMapper.getTableName(ADETable.DISASTERPREVENTIONBLOC).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEVELOPMENTPLAN");
                add("DISASTERPREVENTIONPUBLICFACI");
                add("OTHERPUBLICFACILITYALLOCATIO");
            }});
            put(schemaMapper.getTableName(ADETable.DISASTERPREVENTIONBL_2).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("SPECIFIEDZONALDISASTERPREVEN");
                add("ZONALDISASTERPREVENTIONFACIL");
            }});
            put(schemaMapper.getTableName(ADETable.DISASTERP_TO_ZONALDISA).toUpperCase(), new LinkedHashSet<String>() {{
                add("DISASTERPREVENTIONBL_2_ID");
                add("ZONALDISASTERPREVENTIO_ID");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRIBUTIONBUSINESSPA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGCOVERAGERATE");
                add("DISTRIBUTIONBUSINESSPARK");
                add("FLOORAREARATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("PUBLICANDUTILITYFACILITIES");
                add("SCHEDULEDEXECUTOR");
                add("SETBACKSIZE");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRIBUTIONBUSINESSZO).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("GUIDELINEPUBLICATIONDATE");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGDESIGNRESTRICTION");
                add("BUILDINGRESTRICTIONS");
                add("FENCEGUIDELINE");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGAREA");
                add("MINIMUMBUILDINGAREA_UOM");
                add("MINIMUMBUILDINGCOVERAGERATE");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMFLOORAREARATE");
                add("MINIMUMFLOORHEIGHT");
                add("MINIMUMFLOORHEIGHT_UOM");
                add("MINIMUMFRONTAGERATE");
                add("MINIMUMGREENINGRATE");
                add("MINIMUMGROUNDHEIGHT");
                add("MINIMUMGROUNDHEIGHT_UOM");
                add("MINIMUMSITEAREA");
                add("MINIMUMSITEAREA_UOM");
                add("RESTRICTIONSFORFIREPROTECTIO");
                add("RESTRICTIONSFORNOISEPROTECTI");
                add("SETBACKSIZE");
                add("STRUCUTUREPLACEMENTRESTRICTI");
                add("USERESTRICTIONS");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRICTDEVELOPMENTPLA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACTIVITYRESTRICTIONINFARMLAN");
                add("BUILDINGRESTRICTIONS");
                add("DISTRICTFACILITIESALLOCATION");
                add("LANDUSERESTRICTIONS");
                add("OBJECTCLASS_ID");
                add("URBANGREENSPACECONSERVATION");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRICTD_TO_DISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("DISTRICT_ID");
                add("DISTRICTDEVELOPMENTPLA_ID");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRICTD_TO_ZONE).toUpperCase(), new LinkedHashSet<String>() {{
                add("DISTRICTDEVELOPMENTPLA_ID");
                add("ZONE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRICTPLAN).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FACILITYALLOCATION");
                add("LANDUSEPOLICY");
            }});
            put(schemaMapper.getTableName(ADETable.DISTRICTSANDZONES).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREAINTOTAL");
                add("AREAINTOTAL_UOM");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.EXCEPTIONALFLOORAREARA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGHEIGHTLIMITS");
                add("BUILDINGHEIGHTLIMITS_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.FAREATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AGENCY_ID");
                add("CURRENCYTYPE");
                add("CURRENCYTYPE_CODESPACE");
                add("PAYMENTMETHOD");
                add("PAYMENTMETHOD_CODESPACE");
                add("PRICE");
                add("TRANSFERDURATION");
                add("TRANSFERS");
                add("TRANSFERS_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.FARERULE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CONTAINSID");
                add("CONTAINSID_CODESPACE");
                add("DESTINATIONID");
                add("DESTINATIONID_CODESPACE");
                add("FARE_ID");
                add("ORIGINID");
                add("ORIGINID_CODESPACE");
                add("ROUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.FEEDINFO).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CONTACTEMAIL");
                add("CONTACTURL");
                add("DEFAULTLANGUAGE");
                add("DEFAULTLANGUAGE_CODESPACE");
                add("DETAILEDINFO");
                add("ENDDATE");
                add("LANGUAGE");
                add("LANGUAGE_CODESPACE");
                add("PUBLISHERNAME");
                add("PUBLISHERURL");
                add("STARTDATE");
                add("VERSION");
            }});
            put(schemaMapper.getTableName(ADETable.FREQUENCY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ENDTIME");
                add("EXACTTIMES");
                add("EXACTTIMES_CODESPACE");
                add("HEADWAYSECS");
                add("STARTTIME");
                add("TRIP_ID");
            }});
            put(schemaMapper.getTableName(ADETable.GLOBALHUBCITYDEVELOPME).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("IMPLEMENTATIONBODY");
                add("IMPLEMENTATIONPERIOD");
                add("PLAN");
            }});
            put(schemaMapper.getTableName(ADETable.HEIGHTCONTROLDISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.HIGHLEVELUSEDISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGAREA");
                add("MINIMUMBUILDINGAREA_UOM");
                add("MINIMUMFLOORAREARATE");
                add("SETBACKSIZE");
            }});
            put(schemaMapper.getTableName(ADETable.HIGHRISERESIDENTIALATT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FLOORAREARATE");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MINIMUMSITEAREA");
                add("MINIMUMSITEAREA_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.HISTORICSCENERYMAINTEN).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LANDUSEPOLICY");
            }});
            put(schemaMapper.getTableName(ADETable.HOUSEHOLDS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBEROFMAINHOUSEHOLD");
                add("NUMBEROFORDINARYHOUSEHOLD");
            }});
            put(schemaMapper.getTableName(ADETable.INDUSTRIALPARKDEVELOPM).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PUBLICFACILITYALLOCATION");
                add("RESIDENTIALLANDUSEPLAN");
            }});
            put(schemaMapper.getTableName(ADETable.KEYVALUEPAIR).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CODEVALUE");
                add("CODEVALUE_CODESPACE");
                add("DATEVALUE");
                add("DOUBLEVALUE");
                add("INTVALUE");
                add("KEY");
                add("KEY_CODESPACE");
                add("MEASUREDVALUE");
                add("MEASUREDVALUE_UOM");
                add("STATISTICALG_GENERICVALUE_ID");
                add("STRINGVALUE");
                add("URIVALUE");
            }});
            put(schemaMapper.getTableName(ADETable.KEYVALUEPAIRATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CODEVALUE");
                add("CODEVALUE_CODESPACE");
                add("DATEVALUE");
                add("DOUBLEVALUE");
                add("INTVALUE");
                add("KEY");
                add("KEY_CODESPACE");
                add("MEASUREDVALUE");
                add("MEASUREDVALUE_UOM");
                add("STRINGVALUE");
                add("URIVALUE");
            }});
            put(schemaMapper.getTableName(ADETable.LANDPRICE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CURRENCYUNIT");
                add("CURRENCYUNIT_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.LANDPRICEPERLANDUSE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LANDPRICE");
                add("LANDPRICE_LANDPRICE_ID");
                add("LANDUSE");
                add("LANDUSE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.LANDREADJUSTMENTPROJEC).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGLOTDEVELOPMENT");
                add("PUBLICFACILITYALLOCATION");
            }});
            put(schemaMapper.getTableName(ADETable.LANDSCAPEZONE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGDESIGNRESTRICTION");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMSITEAREA");
                add("MINIMUMSITEAREA_UOM");
                add("SETBACKSIZE");
            }});
            put(schemaMapper.getTableName(ADETable.LANDUSEATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREACLASSIFICATION_CODESPACE");
                add("AREACLASSIFICATIONTYPE");
                add("AREAINHA");
                add("AREAINHA_UOM");
                add("AREAINSQUAREMETER");
                add("AREAINSQUAREMETER_UOM");
                add("BUILDINGCOVERAGERATE");
                add("CITY");
                add("CITY_CODESPACE");
                add("DISTRICTSANDZONEST_CODESPACE");
                add("DISTRICTSANDZONESTYPE");
                add("FLOORAREARATE");
                add("NOMINALAREA");
                add("NOMINALAREA_UOM");
                add("NOTE");
                add("ORGLANDUSE");
                add("ORGLANDUSE_CODESPACE");
                add("OWNER");
                add("OWNERTYPE");
                add("OWNERTYPE_CODESPACE");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
                add("REFERENCE");
                add("SPECIFIEDBUILDINGCOVERAGERAT");
                add("SPECIFIEDFLOORAREARATE");
                add("STANDARDFLOORAREARATE");
                add("SURVEYYEAR");
                add("URBANPLANTYPE");
                add("URBANPLANTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.LANDUSEDIVERSION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.LAND_USE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LANDUSEDETAILATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.LARGECUSTOMERFACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREACLASSIFICATION_CODESPACE");
                add("AREACLASSIFICATIONTYPE");
                add("AVAILABILITY");
                add("CAPACITY");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("DISTRICTSANDZONEST_CODESPACE");
                add("DISTRICTSANDZONESTYPE");
                add("INAUGURALDATE");
                add("KEYTENANTS");
                add("LANDUSETYPE");
                add("LANDUSETYPE_CODESPACE");
                add("NAME");
                add("NOTE");
                add("OWNER");
                add("REFERENCE");
                add("SURVEYYEAR");
                add("TOTALFLOORAREA");
                add("TOTALFLOORAREA_UOM");
                add("TOTALSTOREFLOORAREA");
                add("TOTALSTOREFLOORAREA_UOM");
                add("URBANPLANTYPE");
                add("URBANPLANTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.NEWHOUSINGANDURBANDEVE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("HOUSING");
                add("PUBLICFACILITYALLOCATION");
                add("RESIDENTIALLANDUSEPLAN");
            }});
            put(schemaMapper.getTableName(ADETable.NEWURBANINFRASTRUCTURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DISTRICTSALLOCATION");
                add("LANDFORCENTRALPUBLICFACILITI");
                add("LANDUSEPLAN");
            }});
            put(schemaMapper.getTableName(ADETable.NUMBEROFANNUALDIVERSIO).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COUNT");
                add("STATISTICALG_NUMBEROFANNU_ID");
                add("YEAR");
            }});
            put(schemaMapper.getTableName(ADETable.NUMBEROFHOUSEHOLDS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("HOUSEHOLDS_NUMBEROFHOUS_ID_1");
                add("HOUSEHOLDS_NUMBEROFHOUSEH_ID");
                add("NUMBER_");
            }});
            put(schemaMapper.getTableName(ADETable.NUMBEROFHOUSEHOLDS_2).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CENSUSBLOCK_NUMBEROFHOUSE__2");
                add("CENSUSBLOCK_NUMBEROFHOUSE_ID");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("NUMBER_");
            }});
            put(schemaMapper.getTableName(ADETable.OFFICE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NAME");
                add("PHONE");
                add("URL");
            }});
            put(schemaMapper.getTableName(ADETable.OFFICESANDEMPLOYEES).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBEROFEMPLOYEES");
                add("NUMBEROFOFFICES");
            }});
            put(schemaMapper.getTableName(ADETable.OPENSPACEFORPUBLICUSE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PARKATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.PARKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PARKSERIALNUMBER");
                add("PARKSIZENUMBER");
                add("PARKSIZENUMBER_CODESPACE");
                add("PARKTYPENUMBER");
                add("PARKTYPENUMBER_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.PARKINGPLACEATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("STOREYSABOVEGROUND");
                add("STOREYSBELOWGROUND");
            }});
            put(schemaMapper.getTableName(ADETable.PATHWAY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FROM_ID");
                add("ISBIDIRECTIONAL");
                add("ISBIDIRECTIONAL_CODESPACE");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAXSLOPE");
                add("MINWIDTH");
                add("MODE_");
                add("MODE_CODESPACE");
                add("REVERSEDSIGNPOSTEDAS");
                add("SIGNPOSTEDAS");
                add("STAIRCOUNT");
                add("TO_ID");
                add("TRAVERSALTIME");
            }});
            put(schemaMapper.getTableName(ADETable.POINT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LATITUDE");
                add("LONGITUDE");
                add("POINT");
                add("POINTDISTANCETRAVELED");
                add("POINTSEQUENCE");
                add("PUBLICTRANSIT_POINT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.POLLUTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CAUSE");
                add("DAMAGEDAREA");
                add("DAMAGEDAREA_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.POPULATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BIRTHS");
                add("DAYTIMEPOPULATION");
                add("DAYTIMEPOPULATIONDENSITY");
                add("DEATHS");
                add("FEMALEPOPULATION");
                add("INCREASEMENT");
                add("MALEPOPULATION");
                add("MOVEFROM");
                add("MOVETO");
                add("NATURALINCREASE");
                add("SOCIALINCREASE");
                add("TOTAL");
            }});
            put(schemaMapper.getTableName(ADETable.POPULATIONBYAGEANDSEX).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AGEANDSEX");
                add("AGEANDSEX_CODESPACE");
                add("NUMBER_");
                add("POPULATION_POPULATIONBYAG_ID");
            }});
            put(schemaMapper.getTableName(ADETable.PORTZONE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FLOORAREARATE");
            }});
            put(schemaMapper.getTableName(ADETable.PRIVATEURBANRENEWALPRO).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEVELOPER");
                add("PLAN");
            }});
            put(schemaMapper.getTableName(ADETable.PRODUCTIVEGREENZONE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("SPECIFICATION");
                add("SPECIFICATION_CODESPACE");
                add("ZONENUMBER");
            }});
            put(schemaMapper.getTableName(ADETable.PROJECTPROMOTIONAREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEVELOPMENTPOLICY");
                add("OBJECTCLASS_ID");
                add("PUBLICFACILITIESPLANS");
            }});
            put(schemaMapper.getTableName(ADETable.PUBLICTRANSIT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("INDEX_");
                add("OBJECTCLASS_ID");
                add("ORGID");
                add("TARGET_ID");
            }});
            put(schemaMapper.getTableName(ADETable.PUBLICTRANSITDATATYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CITYOBJECTGROUP_DATATYPE_ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.PUBLICTRANSITFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COMPANYNAME");
                add("FREQUENCYOFSERVICE");
                add("NUMBEROFCUSTOMERS");
                add("ROUTENAME");
                add("SECTIONNAME");
            }});
            put(schemaMapper.getTableName(ADETable.QUASIURBANPLANNINGAREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CITYAREA");
                add("CITYAREA_UOM");
                add("CITYPOPULATION");
                add("POPULATION");
            }});
            put(schemaMapper.getTableName(ADETable.RECREATIONS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CAPACITY");
                add("NUMBEROFUSERS");
            }});
            put(schemaMapper.getTableName(ADETable.RESIDENTIALBLOCKCONSTR).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEVELOPMENTPLAN");
                add("PUBLICFACILITYALLOCATION");
                add("SITEAREA");
                add("SITEAREA_UOM");
                add("TOTALFLOORAREA");
                add("TOTALFLOORAREA_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.RESIDENTIALENVIRONMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMFLOORAREARATE");
                add("OTHERRESTRICTIONS");
                add("SETBACKSIZE");
                add("USETOBEINDUCED");
            }});
            put(schemaMapper.getTableName(ADETable.ROADATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.ROADDATAQUALITYATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPEARANCESRCDESC");
                add("APPEARANCESRCDESC_CODESPACE");
                add("GEOMETRYSRCDESC");
                add("GEOMETRYSRCDESC_CODESPACE");
                add("LODTYPE");
                add("SRCSCALE");
                add("SRCSCALE_CODESPACE");
                add("THEMATICSRCDESC");
                add("THEMATICSRCDESC_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.ROADSIDEDISTRICTIMPROV).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ROADSIDEDISTRICTFACILITIESAL");
            }});
            put(schemaMapper.getTableName(ADETable.ROADSIDEDISTRICTPLAN).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FACILITIESALLOCATION");
                add("LANDUSEPOLICY");
            }});
            put(schemaMapper.getTableName(ADETable.ROADSTRUCTUREATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBEROFLANES");
                add("SECTIONTYPE");
                add("SECTIONTYPE_CODESPACE");
                add("WIDTH");
                add("WIDTH_UOM");
                add("WIDTHTYPE");
                add("WIDTHTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.ROUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AGENCY_ID");
                add("COLOR");
                add("CONTINUOUSDROPOFF");
                add("CONTINUOUSDROPOFF_CODESPACE");
                add("CONTINUOUSPICKUP");
                add("CONTINUOUSPICKUP_CODESPACE");
                add("DESCRIPTION_ID");
                add("DESTINATIONSTOP");
                add("LOD0MULTICURVE");
                add("LONGNAME");
                add("ORIGINSTOP");
                add("PARENTROUTE_ID");
                add("ROUTESORTORDER");
                add("SHORTNAME");
                add("TEXTCOLOR");
                add("TYPE");
                add("TYPE_CODESPACE");
                add("UPDATEDATE");
                add("URL");
                add("VIASTOP");
            }});
            put(schemaMapper.getTableName(ADETable.RURALDISTRICTIMPROVEME).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("RURALDISTRICTFACILITIESALLOC");
            }});
            put(schemaMapper.getTableName(ADETable.SCENICDISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGCOVERAGERATE");
                add("BUILDINGHEIGHTLIMITS");
                add("BUILDINGHEIGHTLIMITS_UOM");
                add("WALLSETBACKDISTANCEWITHA_UOM");
                add("WALLSETBACKDISTANCEWITHADJOI");
                add("WALLSETBACKDISTANCEWITHR_UOM");
                add("WALLSETBACKDISTANCEWITHROAD");
            }});
            put(schemaMapper.getTableName(ADETable.SCHEDULEDAREAFORURBAND).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
                add("SCHEDULEDEXECUTOR");
            }});
            put(schemaMapper.getTableName(ADETable.SEDIMENTDISASTERPRONEA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREATYPE");
                add("AREATYPE_CODESPACE");
                add("DISASTERTYPE");
                add("DISASTERTYPE_CODESPACE");
                add("STATUS");
                add("STATUS_CODESPACE");
                add("ZONENAME");
                add("ZONENUMBER");
            }});
            put(schemaMapper.getTableName(ADETable.SEWERSYSTEMATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DRAINAGEAREA");
                add("ENDLOCATION");
                add("STARTLOCATION");
                add("SYSTEMTYPE");
                add("SYSTEMTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIALGREENSPACECONSE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("REQUIREMENT");
                add("REQUIREMENT_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIALURBANRENAISSANC).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMBUILDINGCOVERAGERATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGAREA");
                add("MINIMUMBUILDINGAREA_UOM");
                add("MINIMUMFLOORAREARATE");
                add("OTHERRESTRICTIONS");
                add("SETBACKSIZE");
                add("USETOBEINDUCED");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIALUSEATTRACTIONDI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMFLOORAREARATE");
                add("MINIMUMBUILDINGAREA");
                add("MINIMUMBUILDINGAREA_UOM");
                add("MINIMUMFLOORAREARATE");
                add("OTHERRESTRICTIONS");
                add("USETOBEINDUCED");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIALUSEDISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGRESTRICTIONS");
                add("OTHERRESTRICTIONS");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIALUSERESTRICTIOND).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGRESTRICTIONS");
                add("OTHERRESTRICTIONS");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIFIEDBLOCK).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FLOORAREARATE");
                add("MAXIMUMBUILDINGHEIGHT");
                add("MAXIMUMBUILDINGHEIGHT_UOM");
                add("SETBACKSIZE");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIFIEDDISASTERPREVE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MINIMUMBUILDINGHEIGHT");
                add("MINIMUMBUILDINGHEIGHT_UOM");
                add("MINIMUMFRONTAGERATE");
                add("MINIMUMSITEAREA");
                add("MINIMUMSITEAREA_UOM");
                add("SETBACKSIZE");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIFIEDURGENTURBANRE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.SPECIFIED_TO_GLOBALHUB).toUpperCase(), new LinkedHashSet<String>() {{
                add("GLOBALHUBCITYDEVELOPME_ID");
                add("SPECIFIEDURGENTURBANRE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.STATISTICALGRID).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREACLASSIFICATION_CODESPACE");
                add("AREACLASSIFICATIONTYPE");
                add("AVAILABILITY");
                add("CITY");
                add("CITY_CODESPACE");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("LOD_1MULTISURFACEGEOMETRY_ID");
                add("LOD_2MULTISURFACEGEOMETRY_ID");
                add("OBJECTCLASS_ID");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
                add("SURVEYYEAR");
                add("URBANPLANTYPE");
                add("URBANPLANTYPE_CODESPACE");
                add("VALUE");
            }});
            put(schemaMapper.getTableName(ADETable.STOP).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CODE");
                add("CODE_CODESPACE");
                add("LATITUDE");
                add("LEVEL_ID");
                add("LOCATIONTYPE");
                add("LOCATIONTYPE_CODESPACE");
                add("LONGITUDE");
                add("PARENTSTATION_ID");
                add("PLATFORMCODE");
                add("POINT");
                add("TIMEZONE");
                add("TIMEZONE_CODESPACE");
                add("TTSNAME");
                add("URL");
                add("WHEELCHAIRBOARDING");
                add("WHEELCHAIRBOARDING_CODESPACE");
                add("ZONEID");
                add("ZONEID_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.STOPTIME).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ARRIVALTIME");
                add("CONTINUOUSDROPOFFT_CODESPACE");
                add("CONTINUOUSDROPOFFTYPE");
                add("CONTINUOUSPICKUPTY_CODESPACE");
                add("CONTINUOUSPICKUPTYPE");
                add("DEPARTURETIME");
                add("DROPOFFTYPE");
                add("DROPOFFTYPE_CODESPACE");
                add("HEADSIGN");
                add("PICKUPTYPE");
                add("PICKUPTYPE_CODESPACE");
                add("SHAPEDISTANCETRAVELED");
                add("STOP_ID");
                add("STOPSEQUENCE");
                add("TIMEPOINT");
                add("TIMEPOINT_CODESPACE");
                add("TRIP_ID");
            }});
            put(schemaMapper.getTableName(ADETable.STRUCTUREDETAILS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CROSSTYPE");
                add("CROSSTYPE_CODESPACE");
                add("ENDLOCATION");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAXIMUMWIDTH");
                add("MAXIMUMWIDTH_UOM");
                add("MINIMUMWIDTH");
                add("MINIMUMWIDTH_UOM");
                add("STANDARDWIDTH");
                add("STANDARDWIDTH_UOM");
                add("STARTLOCATION");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("VIALOCATIONS");
            }});
            put(schemaMapper.getTableName(ADETable.SUPPLYFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("WATERWORKSATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.THREEDIMENSIONALEXTENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMLOAD");
                add("MAXIMUMLOAD_UOM");
                add("MINIMUMDISTANCE");
                add("MINIMUMDISTANCE_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFICAREAATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBEROFLANES");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFICFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ENDLOCATION");
                add("LENGTH");
                add("LENGTH_UOM");
                add("PARKINGPLACEATTRIBUTE_ID");
                add("STARTLOCATION");
                add("URBANRAPIDTRANSITRAILROAD_ID");
                add("URBANROADATTRIBUTE_ID");
                add("VEHICLETERMINALATTRIBUTE_ID");
                add("VIALOCATIONS");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFICVOLUMEATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AVERAGEINBOUNDTRAVELSPEEDINC");
                add("AVERAGEOUTBOUNDTRAVELSPEEDIN");
                add("AVERAGETRAVELSPEEDINCONGESTI");
                add("CONGESTIONRATE");
                add("LARGEVEHICLERATE");
                add("OBSERVATIONPOINTNAME");
                add("REFERENCE");
                add("SECTIONID");
                add("SURVEYYEAR");
                add("WEEKDAY12HOURTRAFFICVOLUME");
                add("WEEKDAY24HOURTRAFFICVOLUME");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFIC_AREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("TRAFFICAREASTRUCTUREATTRI_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TRANSFER).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FROM_ID");
                add("MINTRANSFERTIME");
                add("TO_ID");
                add("TRANSFERTYPE");
                add("TRANSFERTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.TRANSLATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FIELDNAME");
                add("FIELDVALUE");
                add("LANGUAGE");
                add("LANGUAGE_CODESPACE");
                add("RECORDID_ID");
                add("RECORDSUBID");
                add("TABLENAME");
                add("TABLENAME_CODESPACE");
                add("TRANSLATION");
            }});
            put(schemaMapper.getTableName(ADETable.TRANSLATIONJP).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LANGUAGE");
                add("LANGUAGE_CODESPACE");
                add("TRANSLATION");
            }});
            put(schemaMapper.getTableName(ADETable.TRANSPORTATION_COMPLEX).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ROADDATAQUALITYATTRIBUTE_ID");
                add("ROADSTRUCTUREATTRIBUTE_ID");
                add("TRAFFICVOLUMEATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TREATMENTFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("SEWERSYSTEMSATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TREEPLANTINGDISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MINIMUMGREENINGRATE");
            }});
            put(schemaMapper.getTableName(ADETable.TRIP).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BIKEALLOWED");
                add("BIKEALLOWED_CODESPACE");
                add("BLOCKID");
                add("CALENDAR_ID");
                add("CALENDARDATE_ID");
                add("DIRECTIONID");
                add("DIRECTIONID_CODESPACE");
                add("HEADSIGN");
                add("LOD0MULTICURVE");
                add("OFFICE_ID");
                add("ROUTE_ID");
                add("SHAPE_ID");
                add("SHORTNAME");
                add("SYMBOL");
                add("WHEELCHAIRACCESSIB_CODESPACE");
                add("WHEELCHAIRACCESSIBLE");
            }});
            put(schemaMapper.getTableName(ADETable.URBANDEVELOPMENTPROJEC).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
                add("SCHEDULEDEXECUTOR");
            }});
            put(schemaMapper.getTableName(ADETable.URBANDISASTERRECOVERYP).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("EMERGENCYRECOVERYPOLICY");
                add("EXPIRATIONDATE");
                add("PLANNEDPROJECTTYPE");
                add("PLANNEDPROJECTTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.URBANFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBER_");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URBANFACILITYSTIPULATE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LENGTH");
                add("LENGTH_UOM");
                add("OBJECTCLASS_ID");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.URBANFACI_TO_THREEDIME).toUpperCase(), new LinkedHashSet<String>() {{
                add("THREEDIMENSIONALEXTENT_ID");
                add("URBANFACILITY_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URBANFUNCTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREACLASSIFICATION_CODESPACE");
                add("AREACLASSIFICATIONTYPE");
                add("CITY");
                add("CITY_CODESPACE");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("CUSTODIAN");
                add("ENACTMENTFISCALYEAR");
                add("EXPIRATIONFISCALYEAR");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
                add("LEGALGROUNDS");
                add("LOD0MULTICURVE");
                add("LOD0MULTIPOINT");
                add("LOD0MULTISURFACE_ID");
                add("LOD1MULTICURVE");
                add("LOD1MULTISURFACE_ID");
                add("LOD_1MULTICURVE");
                add("LOD_1MULTIPOINT");
                add("LOD_1MULTISURFACE_ID");
                add("LOD_2MULTICURVE");
                add("LOD_2MULTIPOINT");
                add("LOD_2MULTISURFACE_ID");
                add("NOMINALAREA");
                add("NOMINALAREA_UOM");
                add("NOTE");
                add("NOTIFICATIONNUMBER");
                add("OBJECTCLASS_ID");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
                add("REASON");
                add("REFERENCE");
                add("SURVEYYEAR");
                add("URBANPLANTYPE");
                add("URBANPLANTYPE_CODESPACE");
                add("USAGE");
                add("USAGE_CODESPACE");
                add("VALIDFROM");
                add("VALIDFROMTYPE");
                add("VALIDFROMTYPE_CODESPACE");
                add("VALIDTO");
                add("VALIDTOTYPE");
                add("VALIDTOTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.URBANFUNC_TO_CITYOBJEC).toUpperCase(), new LinkedHashSet<String>() {{
                add("CITYOBJECT_ID");
                add("URBANFUNCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URBANIZATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PERIOD");
                add("RESOURCES");
            }});
            put(schemaMapper.getTableName(ADETable.URBANPLANNINGAREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREACLASSIFICATION");
                add("AREACLASSIFICATION_CODESPACE");
                add("CITYAREA");
                add("CITYAREA_UOM");
                add("CITYPOPULATION");
                add("POLICYFORAREACLASSIFICATION");
                add("POLICYFORURBANPLANDECISION");
                add("POPULATION");
                add("PURPOSEFORURBANPLAN");
                add("REASONFORAREACLASSIFICATION");
            }});
            put(schemaMapper.getTableName(ADETable.URBANRAPIDTRANSITRAILR).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CROSSTYPE");
                add("CROSSTYPE_CODESPACE");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.URBANRAPI_TO_STRUCTURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("STRUCTUREDETAILS_ID");
                add("URBANRAPIDTRANSITRAILR_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URBANREDEVELOPMENTPROJ).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEVELOPMENTPLAN");
                add("HOUSINGTARGET");
                add("NUMBEROFHOUSING");
                add("PUBLICFACILITYALLOCATION");
                add("SITEAREA");
                add("SITEAREA_UOM");
                add("TOTALFLOORAREA");
                add("TOTALFLOORAREA_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.URBANREDEVELOPMENTPROM).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PUBLICFACILITIES");
                add("UNITAREA");
            }});
            put(schemaMapper.getTableName(ADETable.URBANRENEWALPROJECT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGUSAGE");
                add("FLOORAREARATE");
                add("SETBACKSIZE");
                add("SITEAREA");
                add("SITEAREA_UOM");
                add("STOREYSABOVEGROUND");
                add("STOREYSBELOWGROUND");
            }});
            put(schemaMapper.getTableName(ADETable.URBANROADATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CROSSTYPE");
                add("CROSSTYPE_CODESPACE");
                add("NUMBEROFLANES");
                add("ROADSTRUCTURE");
                add("ROADTYPE");
                add("ROADTYPE_CODESPACE");
                add("ROUTESERIALNUMBER");
                add("ROUTESIZENUMBER");
                add("ROUTESIZENUMBER_CODESPACE");
                add("ROUTETYPENUMBER");
                add("ROUTETYPENUMBER_CODESPACE");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("TRAFFICPLAZAS");
                add("TRAFFICPLAZAS_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.URBANROAD_TO_STRUCTURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("STRUCTUREDETAILS_ID");
                add("URBANROADATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URGENTURBANRENEWALAREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEVELOPMENTPOLICY");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URGENTURB_TO_PRIVATEUR).toUpperCase(), new LinkedHashSet<String>() {{
                add("PRIVATEURBANRENEWALPRO_ID");
                add("URGENTURBANRENEWALAREA_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URGENTURB_TO_SPECIALUR).toUpperCase(), new LinkedHashSet<String>() {{
                add("SPECIALURBANRENAISSANC_ID");
                add("URGENTURBANRENEWALAREA_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URGENTURB_TO_SPECIFIED).toUpperCase(), new LinkedHashSet<String>() {{
                add("SPECIFIEDURGENTURBANRE_ID");
                add("URGENTURBANRENEWALAREA_ID");
            }});
            put(schemaMapper.getTableName(ADETable.USEDISTRICT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGCOVERAGERATE");
                add("BUILDINGHEIGHTLIMITS");
                add("BUILDINGHEIGHTLIMITS_UOM");
                add("BUILDINGRESTRICTIONS");
                add("FLOORAREARATE");
                add("MINIMUMSITEAREA");
                add("MINIMUMSITEAREA_UOM");
                add("OTHERRESTRICTIONS");
                add("SETBACKRESTRICTIONS");
                add("SHADEREGULATION");
                add("WALLSETBACKDISTANCE");
            }});
            put(schemaMapper.getTableName(ADETable.VEGETATIONATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPEARANCESRCDESC");
                add("APPEARANCESRCDESC_CODESPACE");
                add("GEOMETRYSRCDESC");
                add("GEOMETRYSRCDESC_CODESPACE");
                add("SRCSCALE");
                add("SRCSCALE_CODESPACE");
                add("THEMATICSRCDESC");
                add("THEMATICSRCDESC_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.VEHICLETERMINALATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("TERMINALTYPE");
                add("TERMINALTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODYATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEPTH");
                add("DEPTH_UOM");
                add("DESCRIPTION");
                add("DESCRIPTION_CODESPACE");
                add("OBJECTCLASS_ID");
                add("RANK");
                add("RANK_CODESPACE");
                add("RANKORG");
                add("RANKORG_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODYRIVERFLOODING).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADMINTYPE");
                add("ADMINTYPE_CODESPACE");
                add("DURATION");
                add("DURATION_UOM");
                add("SCALE");
                add("SCALE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODY_TO_WATERBODY).toUpperCase(), new LinkedHashSet<String>() {{
                add("WATERBODYATTRIBUTE_ID");
                add("WATERBODY_ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERWAY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ENDLOCATION");
                add("LENGTH");
                add("LENGTH_UOM");
                add("STARTLOCATION");
                add("STRUCTURE");
                add("STRUCTURE_CODESPACE");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.WATERWORKSATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ENDLOCATION");
                add("STARTLOCATION");
            }});
            put(schemaMapper.getTableName(ADETable.ZONALDISASTERPREVENTIO).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FACILITYTYPE");
                add("FACILITYTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.ZONE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LOCATION");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.ZONE_TO_BOUNDARY).toUpperCase(), new LinkedHashSet<String>() {{
                add("BOUNDARY_ID");
                add("ZONE_ID");
            }});
        }};
    }

    public HashMap<String, Set<String>> getTablesAndColumns() {
        return tableColumns;
    }

}
