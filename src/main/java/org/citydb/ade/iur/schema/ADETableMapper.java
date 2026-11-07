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
            put(schemaMapper.getTableName(ADETable.BRIDGE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BRIDBASEATTRIBUTE_ID");
                add("BRIDDATAQUALITYATTRIBUTE_ID");
                add("BRIDFACILITYIDATTRIBUTE_ID");
                add("BRIDFACILITYTYPEATTRIBUTE_ID");
                add("BRIDFUNCTIONALATTRIBUTE_ID");
                add("BRIDRISKASSESSMENTATTRIBU_ID");
                add("BRIDSTRUCTUREATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BRIDGEFUNCTIONALATTRIB).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DIRECTIONTYPE");
                add("DIRECTIONTYPE_CODESPACE");
                add("USERTYPE");
                add("USERTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.BRIDGESTRUCTUREATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("BRIDGETYPE");
                add("BRIDGETYPE_CODESPACE");
                add("ESCALATOR");
                add("HEIGHTRESTRICTION");
                add("HEIGHTRESTRICTION_UOM");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MATERIAL");
                add("MATERIAL_CODESPACE");
                add("SLOPETYPE");
                add("SLOPETYPE_CODESPACE");
                add("UNDERGIRDERHEIGHT");
                add("UNDERGIRDERHEIGHT_UOM");
                add("WEIGHTRESTRICTION");
                add("WEIGHTRESTRICTION_UOM");
                add("WIDTH");
                add("WIDTH_UOM");
                add("WIDTHRESTRICTION");
                add("WIDTHRESTRICTION_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BLDGDATAQUALITYATTRIBUTE_ID");
                add("BLDGFACILITYIDATTRIBUTE_ID");
                add("BLDGFACILITYTYPEATTRIBUTE_ID");
                add("BLDGREALESTATEIDATTRIBUTE_ID");
                add("BLDGUSECASEATTRIBUTE_ID");
                add("BUILDINGDETAILATTRIBUTE_ID");
                add("BUILDINGIDATTRIBUTE_ID");
                add("IFCBUILDINGATTRIBUTE_ID");
                add("INDOORBUILDINGATTRIBUTE_ID");
                add("LARGECUSTOMERFACILITYATTR_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDINGATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
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
            put(schemaMapper.getTableName(ADETable.BUILDINGUSECASEATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FLOORHEIGHT");
                add("FLOORHEIGHT_UOM");
                add("ISGROUNDFLOOROPEN");
                add("ISTEMPORAL");
                add("ISTEMPORAL_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.CIRCULARCURVETYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CURVELENGTH");
                add("CURVELENGTH_UOM");
                add("CUTLENGTH");
                add("CUTLENGTH_UOM");
                add("INTERSECTION");
                add("RADIUS");
                add("RADIUS_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.CITYFURNITUREATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("FACILITYTYPE");
                add("FACILITYTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJECT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("VEGDATAQUALITYATTRIBUTE_ID");
                add("VEGFACILITYIDATTRIBUTE_ID");
                add("VEGFACILITYTYPEATTRIBUTE_ID");
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
                add("CITYFURNITUREDETAILATTRIB_ID");
                add("FRNDATAQUALITYATTRIBUTE_ID");
                add("FRNFACILITYIDATTRIBUTE_ID");
                add("FRNFACILITYTYPEATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCTIONBASEATTRIB).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADMINISTORATOR");
                add("ADMINOFFICE");
                add("ADMINTYPE");
                add("ADMINTYPE_CODESPACE");
                add("COMPLETIONYEAR");
                add("CONSTRUCTIONSTARTYEAR");
                add("FACILITYAGE");
                add("INSTALLER");
                add("INSTALLERTYPE");
                add("INSTALLERTYPE_CODESPACE");
                add("KANA");
                add("OPERATORTYPE");
                add("OPERATORTYPE_CODESPACE");
                add("PURPOSE");
                add("PURPOSE_CODESPACE");
                add("SPECIFICATION");
                add("STRUCTUREORDINANCE");
                add("UPDATE_");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCTIONRISKASSESS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("REFERENCEDATE");
                add("RISKTYPE");
                add("RISKTYPE_CODESPACE");
                add("STATUS");
                add("STATUS_CODESPACE");
                add("SURVEYYEAR");
            }});
            put(schemaMapper.getTableName(ADETable.CONTROLPOINT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ENDPOINT");
                add("ENDPOST");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
                add("PARAMETER_ID");
                add("RAILWAYTRACK_CONTROLPOINT_ID");
                add("STARTPOINT");
                add("STARTPOST");
            }});
            put(schemaMapper.getTableName(ADETable.CONTROLPOINTTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CIRCULARCURVE_ID");
                add("SLOPETYPE_ID");
                add("TRANSITIONCURVE_ID");
                add("VERTICALCURVE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.DATAQUALITYATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPEARANCESRCDES_CODESPACE_1");
                add("APPEARANCESRCDES_CODESPACE_2");
                add("APPEARANCESRCDES_CODESPACE_3");
                add("APPEARANCESRCDES_CODESPACE_4");
                add("APPEARANCESRCDESCL_CODESPACE");
                add("APPEARANCESRCDESCLOD0");
                add("APPEARANCESRCDESCLOD1");
                add("APPEARANCESRCDESCLOD2");
                add("APPEARANCESRCDESCLOD3");
                add("APPEARANCESRCDESCLOD4");
                add("GEOMETRYSRCDESCL_CODESPACE_1");
                add("GEOMETRYSRCDESCL_CODESPACE_2");
                add("GEOMETRYSRCDESCL_CODESPACE_3");
                add("GEOMETRYSRCDESCL_CODESPACE_4");
                add("GEOMETRYSRCDESCLOD0");
                add("GEOMETRYSRCDESCLOD1");
                add("GEOMETRYSRCDESCLOD2");
                add("GEOMETRYSRCDESCLOD3");
                add("GEOMETRYSRCDESCLOD4");
                add("GEOMETRYSRCDESCLOD_CODESPACE");
                add("LOD1HEIGHTTYPE");
                add("LOD1HEIGHTTYPE_CODESPACE");
                add("LODTYPE");
                add("LODTYPE_CODESPACE");
                add("PUBLICSURVEYDATAQUALITYAT_ID");
                add("THEMATICSRCDESC");
                add("THEMATICSRCDESC_CODESPACE");
                add("TRANDATAACQUISITION");
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
            put(schemaMapper.getTableName(ADETable.DISASTERRISKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREATYPE");
                add("AREATYPE_CODESPACE");
                add("BRIDGE_BRIDDISASTERRISKAT_ID");
                add("BUILDING_BLDGDISASTERRISK_ID");
                add("CONSTRUCTION_CONSDISASTER_ID");
                add("DESCRIPTION");
                add("DESCRIPTION_CODESPACE");
                add("OBJECTCLASS_ID");
                add("TUNNEL_TUNDISASTERRISKATT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.FACILITYIDATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ALTERNATIVENAME");
                add("BRANCHID");
                add("CITY");
                add("CITY_CODESPACE");
                add("ENDPOST");
                add("ID_1");
                add("PARTID");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
                add("ROUTE");
                add("STARTLAT");
                add("STARTLONG");
                add("STARTPOST");
            }});
            put(schemaMapper.getTableName(ADETable.FACILITYTYPEATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("CONSTRUCTION_CONSFACILITY_ID");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.FLOODINGRISKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEPTH");
                add("DEPTH_UOM");
                add("OBJECTCLASS_ID");
                add("RANK");
                add("RANK_CODESPACE");
                add("RANKORG");
                add("RANKORG_CODESPACE");
                add("WATERBODY_FLOODINGRISKATT_ID");
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
            put(schemaMapper.getTableName(ADETable.HOUSEHOLDS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBEROFMAINHOUSEHOLD");
                add("NUMBEROFORDINARYHOUSEHOLD");
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
                add("BRIDGE_BRIDKEYVALUEPAIRAT_ID");
                add("BUILDING_BLDGKEYVALUEPAIR_ID");
                add("CITY_FURNITURE_FRNKEYVALU_ID");
                add("CITYOBJECT_VEGKEYVALUEPAI_ID");
                add("CODEVALUE");
                add("CODEVALUE_CODESPACE");
                add("CONSTRUCTION_CONSKEYVALUE_ID");
                add("KEY");
                add("KEY_CODESPACE");
                add("LAND_USE_LUSEKEYVALUEPAIR_ID");
                add("RELIEF_FEATURE_DEMKEYVALU_ID");
                add("TRANSPOR_COMPLEX_TRANKEYV_ID");
                add("TUNNEL_TUNKEYVALUEPAIRATT_ID");
                add("WATERBODY_WTRKEYVALUEPAIR_ID");
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
                add("ID_1");
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
                add("IFCLANDUSEATTRIBUTE_ID");
                add("LANDUSEDETAILATTRIBUTE_ID");
                add("LUSEDATAQUALITYATTRIBUTE_ID");
                add("LUSEFACILITYIDATTRIBUTE_ID");
                add("LUSEFACILITYTYPEATTRIBUTE_ID");
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
                add("YEARCLOSED");
                add("YEAROPENED");
            }});
            put(schemaMapper.getTableName(ADETable.LEGALGROUNDS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ARTICLESOFREGULATI_CODESPACE");
                add("ARTICLESOFREGULATION");
                add("DATE_");
                add("NAMEOFREGULATION");
                add("NAMEOFREGULATION_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.NUMBEROFHOUSEHOLDS_1).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CENSUSBLOCK_NUMBEROFHOU_ID_1");
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
            put(schemaMapper.getTableName(ADETable.PUBLICSURVEYDATAQUALIT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PUBLICSURVEYSRCD_CODESPACE_1");
                add("PUBLICSURVEYSRCD_CODESPACE_2");
                add("PUBLICSURVEYSRCD_CODESPACE_3");
                add("PUBLICSURVEYSRCD_CODESPACE_4");
                add("PUBLICSURVEYSRCDES_CODESPACE");
                add("PUBLICSURVEYSRCDESCLOD0");
                add("PUBLICSURVEYSRCDESCLOD1");
                add("PUBLICSURVEYSRCDESCLOD2");
                add("PUBLICSURVEYSRCDESCLOD3");
                add("PUBLICSURVEYSRCDESCLOD4");
                add("SRCSCALELOD0");
                add("SRCSCALELOD0_CODESPACE");
                add("SRCSCALELOD1");
                add("SRCSCALELOD1_CODESPACE");
                add("SRCSCALELOD2");
                add("SRCSCALELOD2_CODESPACE");
                add("SRCSCALELOD3");
                add("SRCSCALELOD3_CODESPACE");
                add("SRCSCALELOD4");
                add("SRCSCALELOD4_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.PUBLICTRANSPORTATIONFA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COMPANYNAME");
                add("FREQUENCYOFSERVICE");
                add("NUMBEROFCUSTOMERS");
                add("ROUTENAME");
                add("SECTIONNAME");
            }});
            put(schemaMapper.getTableName(ADETable.RAILWAYROUTEATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ALTERNATIVENAME");
                add("ENDSTATION");
                add("OPERATOR");
                add("OPERATORTYPE");
                add("OPERATORTYPE_CODESPACE");
                add("RAILWAYTYPE");
                add("RAILWAYTYPE_CODESPACE");
                add("STARTSTATION");
            }});
            put(schemaMapper.getTableName(ADETable.RAILWAYTRACKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ALIGNMENTTYPE");
                add("ALIGNMENTTYPE_CODESPACE");
                add("DIRECTIONTYPE");
                add("DIRECTIONTYPE_CODESPACE");
                add("ENDPOST");
                add("LOD2NETWORK_BREP_ID");
                add("LOD2NETWORK_OTHER_GEOM");
                add("LOD3NETWORK_BREP_ID");
                add("LOD3NETWORK_OTHER_GEOM");
                add("ROUTENAME");
                add("STARTPOST");
                add("TRACKTYPE");
                add("TRACKTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.REALESTATEIDATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MATCHINGSCORE");
                add("NUMBEROFBUILDINGUNITOWNERSHI");
                add("NUMBEROFREALESTATEIDOFLAND");
                add("REALESTATEIDOFBUILDING");
                add("REALESTATEIDOFBUILDINGUNITOW");
                add("REALESTATEIDOFLAND");
            }});
            put(schemaMapper.getTableName(ADETable.RECREATIONS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CAPACITY");
                add("NUMBEROFUSERS");
            }});
            put(schemaMapper.getTableName(ADETable.RELIEF_FEATURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEMDATAQUALITYATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.RIVERFACILITYIDATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LEFTDISTANCE");
                add("LEFTDISTANCE_UOM");
                add("LEFTENDDISTANCE");
                add("LEFTENDDISTANCE_UOM");
                add("LEFTENDPOST");
                add("LEFTENDPOST_UOM");
                add("LEFTPOST");
                add("LEFTPOST_UOM");
                add("LEFTSTARTDISTANCE");
                add("LEFTSTARTDISTANCE_UOM");
                add("LEFTSTARTPOST");
                add("LEFTSTARTPOST_UOM");
                add("RIGHTDISTANCE");
                add("RIGHTDISTANCE_UOM");
                add("RIGHTENDDISTANCE");
                add("RIGHTENDDISTANCE_UOM");
                add("RIGHTENDPOST");
                add("RIGHTENDPOST_UOM");
                add("RIGHTPOST");
                add("RIGHTPOST_UOM");
                add("RIGHTSTARTDISTANCE");
                add("RIGHTSTARTDISTANCE_UOM");
                add("RIGHTSTARTPOST");
                add("RIGHTSTARTPOST_UOM");
                add("RIVERCODE");
                add("RIVERCODE_CODESPACE");
                add("RIVERNAME");
                add("SIDETYPE");
                add("SIDETYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.RIVERFLOODINGRISKATTRI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADMINTYPE");
                add("ADMINTYPE_CODESPACE");
                add("DURATION");
                add("DURATION_UOM");
                add("SCALE");
                add("SCALE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.ROADATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
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
            put(schemaMapper.getTableName(ADETable.ROADTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CREATIONDATE");
                add("ID_1");
                add("ISHIGHWAY");
                add("ISTEMPORARY");
                add("ISTOLLROAD");
                add("ROADTYPE");
                add("ROADTYPE_CODESPACE");
                add("SEPARATOR");
                add("SEPARATOR_UOM");
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
            put(schemaMapper.getTableName(ADETable.SLOPETYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ANGLE");
                add("ELEVATION");
                add("ELEVATION_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.SQUAREURBANPLANATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREACOMPLETED");
                add("AREACOMPLETED_UOM");
                add("AREAIMPROVED");
                add("AREAIMPROVED_UOM");
                add("AREAINSERVICE");
                add("AREAINSERVICE_UOM");
                add("AREAPLANNED");
                add("AREAPLANNED_UOM");
                add("CITY");
                add("CITY_CODESPACE");
                add("DATEOFDECISION");
                add("DATEOFREVISION");
                add("ENFORCER");
                add("ISAUTHORIZED");
                add("ISCOMPLETED");
                add("NOTE");
                add("OBJECTCLASS_ID");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
                add("PROJECTENDDATE");
                add("PROJECTSTARTDATE");
                add("PURPOSE");
                add("REMARKS");
                add("STATUS");
                add("STATUS_CODESPACE");
                add("URBANPLANNINGAREANAME");
            }});
            put(schemaMapper.getTableName(ADETable.STATIONSQUAREATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("RAILWAYTYPE");
                add("RAILWAYTYPE_CODESPACE");
                add("ROUTE");
                add("STATION");
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
            put(schemaMapper.getTableName(ADETable.TERMINALATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBEROFBERTHSINSERVICE");
                add("NUMBEROFBERTHSPLANNED");
                add("STRUCTURE");
                add("TERMINALTYPE");
                add("TERMINALTYPE_CODESPACE");
                add("USERTYPE");
                add("USERTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.TRACKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADMINTYPE");
                add("ADMINTYPE_CODESPACE");
                add("ALTERNATIVENAME");
                add("ISTOLLROAD");
                add("RELATIVELEVEL");
                add("SEPARATOR");
                add("SEPARATOR_UOM");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("WIDTHTYPE");
                add("WIDTHTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFICAREAATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NUMBEROFLANES");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFICOBJECTUSECASEAT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXWIDTH");
                add("MAXWIDTH_UOM");
                add("MINWIDTH");
                add("MINWIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFICVOLUMEATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AVERAGEINBOUNDTRAVELSPEEDINC");
                add("AVERAGEINBOUNDTRAVELSPEEDNOT");
                add("AVERAGEOUTBOUNDTRAVELSPEEDIN");
                add("AVERAGEOUTBOUNDTRAVELSPEEDNO");
                add("AVERAGETRAVELSPEEDINCONGESTI");
                add("CONGESTIONRATE");
                add("LARGEVEHICLERATE");
                add("OBSERVATIONPOINTNAME");
                add("REFERENCE");
                add("ROUTENAME");
                add("SECTIONID");
                add("SURVEYYEAR");
                add("WEEKDAY12HOURTRAFFICVOLUME");
                add("WEEKDAY24HOURTRAFFICVOLUME");
            }});
            put(schemaMapper.getTableName(ADETable.TRAFFIC_AREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("RAILWAYTRACKATTRIBUTE_ID");
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
            put(schemaMapper.getTableName(ADETable.TRANSITIONCURVETYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CURVELENGTH");
                add("CURVELENGTH_UOM");
                add("DISTANCE");
                add("DISTANCE_UOM");
                add("INTERSECTION");
                add("INTERSECTION_UOM");
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
                add("RAILWAYROUTEATTRIBUTE_ID");
                add("ROADSTATUS_ID");
                add("ROADSTRUCTUREATTRIBUTE_ID");
                add("SQUAREURBANPLANATTRIBUTE_ID");
                add("TRACKATTRIBUTE_ID");
                add("TRAFFICVOLUMEATTRIBUTE_ID");
                add("TRANDATAQUALITYATTRIBUTE_ID");
                add("TRANFACILITYIDATTRIBUTE_ID");
                add("TRANFACILITYTYPEATTRIBUTE_ID");
                add("TRANUSECASEATTRIBUTE_ID");
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
            put(schemaMapper.getTableName(ADETable.TUNNEL).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("TUNBASEATTRIBUTE_ID");
                add("TUNDATAQUALITYATTRIBUTE_ID");
                add("TUNFACILITYIDATTRIBUTE_ID");
                add("TUNFACILITYTYPEATTRIBUTE_ID");
                add("TUNFUNCTIONALATTRIBUTE_ID");
                add("TUNRISKASSESSMENTATTRIBUT_ID");
                add("TUNSTRUCTUREATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TUNNELFUNCTIONALATTRIB).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DIRECTIONTYPE");
                add("DIRECTIONTYPE_CODESPACE");
                add("USERTYPE");
                add("USERTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.TUNNELSTRUCTUREATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("EFFECTIVEHEIGHT");
                add("EFFECTIVEHEIGHT_UOM");
                add("INNERHEIGHT");
                add("INNERHEIGHT_UOM");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MOUTHTYPE");
                add("MOUTHTYPE_CODESPACE");
                add("SLOPETYPE");
                add("SLOPETYPE_CODESPACE");
                add("TUNNELSUBTYPE");
                add("TUNNELSUBTYPE_CODESPACE");
                add("TUNNELTYPE");
                add("TUNNELTYPE_CODESPACE");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.URBANFUNCTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ABSTRACT");
                add("AREACLASSIFICATION_CODESPACE");
                add("AREACLASSIFICATIONTYPE");
                add("CAPACITY");
                add("CITY");
                add("CITY_CODESPACE");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("CUSTODIAN");
                add("ENACTMENTDATE");
                add("ENACTMENTFISCALYEAR");
                add("EXPIRATIONDATE");
                add("EXPIRATIONFISCALYEAR");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
                add("LEGALGROUNDS_ID");
                add("LOD0MULTICURVE");
                add("LOD0MULTIPOINT");
                add("LOD0MULTISURFACE_ID");
                add("LOD_1MULTICURVE");
                add("LOD_1MULTIPOINT");
                add("LOD_1MULTISURFACE_ID");
                add("LOD_2MULTICURVE");
                add("LOD_2MULTIPOINT");
                add("LOD_2MULTISURFACE_ID");
                add("NOMINALAREA");
                add("NOMINALAREA_UOM");
                add("NOTE");
                add("OBJECTCLASS_ID");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
                add("REFERENCE");
                add("SURVEYYEAR");
                add("URBANPLANTYPE");
                add("URBANPLANTYPE_CODESPACE");
                add("VALIDITY");
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
            put(schemaMapper.getTableName(ADETable.VERTICALCURVETYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LENGTH");
                add("LENGTH_UOM");
                add("VERTICALDISTANCE");
                add("VERTICALDISTANCE_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("WATERBODYDETAILATTRIBUTE_ID");
                add("WTRDATAQUALITYATTRIBUTE_ID");
                add("WTRFACILITYIDATTRIBUTE_ID");
                add("WTRFACILITYTYPEATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODYDETAILATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADMINTYPE");
                add("ADMINTYPE_CODESPACE");
                add("AREA");
                add("AREA_UOM");
                add("CITY");
                add("CITY_CODESPACE");
                add("FLOWDIRECTION");
                add("KANA");
                add("MAXIMUMDEPTH");
                add("MAXIMUMDEPTH_UOM");
                add("MEASUREMENTYEARMONTH");
                add("PREFECTURE");
                add("PREFECTURE_CODESPACE");
                add("RIVERCODE");
                add("RIVERCODE_CODESPACE");
                add("WATERSURFACEELEVATION");
                add("WATERSURFACEELEVATION_UOM");
                add("WATERSYSTEMCODE");
                add("WATERSYSTEMCODE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.ZONE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREAAPPLIED");
                add("FINALPUBLICATIONDATE");
                add("OBJECTCLASS_ID");
            }});
        }};
    }

    public HashMap<String, Set<String>> getTablesAndColumns() {
        return tableColumns;
    }

}
