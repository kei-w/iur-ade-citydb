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
            put(schemaMapper.getTableName(ADETable.ADMINISTRATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.ADMINISTR_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ADMINISTRATION_ID");
                add("DMATTRIBUTE_ID");
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
            put(schemaMapper.getTableName(ADETable.AGREEMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPLICABLEAREA");
                add("APPLICABLEAREA_UOM");
                add("EXPIRATION");
            }});
            put(schemaMapper.getTableName(ADETable.AGRICULTURALDEVELOPMEN).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BENEFITAREA");
                add("BENEFITAREA_UOM");
                add("BENEFITAREAINURBANAREA");
                add("BENEFITAREAINURBANAREA_UOM");
                add("BENEFITPERIOD");
                add("BLOCKNAME");
                add("ID_");
                add("IMPLEMENTATIONBODY");
                add("MEASURETYPE");
                add("MEASURETYPE_CODESPACE");
                add("NOTE");
                add("PROJECTTYPE");
                add("PROJECTTYPE_CODESPACE");
                add("STARTFISCALYEAR");
                add("STATUS");
                add("STATUS_CODESPACE");
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
                add("CLASS");
                add("CLASS_CODESPACE");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
                add("LOCATION");
                add("OFFSET_");
                add("OFFSET__UOM");
                add("OFFSETDIRECTION");
                add("USAGE");
                add("USAGE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.BOUNDARYSURFACE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CONSTRUCTION_BOUNDEDBY_ID");
                add("LOD2MULTISURFACE_ID");
                add("LOD3MULTISURFACE_ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BREAKDOWNOFNOMINALAREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREAINSQUAREMETER");
                add("AREAINSQUAREMETER_UOM");
                add("BREAKDOWN");
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
            put(schemaMapper.getTableName(ADETable.BRIDGE_TO_DISASTERR).toUpperCase(), new LinkedHashSet<String>() {{
                add("DISASTERRISKATTRIBUTE_ID");
                add("BRIDGE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BRIDGE_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("BRIDGE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BRIDGE_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("BRIDGE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BRIDGE_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("BRIDGE_ID");
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
            put(schemaMapper.getTableName(ADETable.BUILDING_FURNITURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("IFCBUILDINGFURNITUREATTRI_ID");
                add("INDOORFURNITUREATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING_INSTALLATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("IFCBUILDINGINSTALLATIONAT_ID");
                add("IFCINTBUILDINGINSTALLATIO_ID");
                add("INDOORINTINSTALLATIONATTR_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING_TO_DISASTERR).toUpperCase(), new LinkedHashSet<String>() {{
                add("DISASTERRISKATTRIBUTE_ID");
                add("BUILDING_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("BUILDING_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("BUILDING_ID");
            }});
            put(schemaMapper.getTableName(ADETable.BUILDING_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("BUILDING_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CABLE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CABLES");
                add("COLUMNS");
                add("OBJECTCLASS_ID");
                add("ROWS_");
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
            put(schemaMapper.getTableName(ADETable.CARGOHANDLINGFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ABILITY");
                add("ACQUISITIONYEAR");
                add("INNEROFSITEAREA");
                add("INNEROFSITEAREA_UOM");
                add("INNERTOTALFLOORAREA");
                add("INNERTOTALFLOORAREA_UOM");
                add("LIFTABLELOAD");
                add("LIFTABLELOAD_UOM");
                add("MAINCARGO");
                add("MAINCARGO_CODESPACE");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("MOORINGFACILITY");
                add("NOTE");
                add("OUTEROFTOTALFLOORAREA");
                add("OUTEROFTOTALFLOORAREA_UOM");
                add("OUTERSITEAREA");
                add("OUTERSITEAREA_UOM");
                add("PACKINGNAME");
                add("PACKINGNAME_CODESPACE");
                add("TOTALCOST");
            }});
            put(schemaMapper.getTableName(ADETable.CENSUSBLOCK).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BLOCKNAME");
                add("DAYTIMEPOPULATIONTYPE_ID");
                add("ECONOMICACTIVITYTYPE_ID");
                add("POPULATIONTYPE_ID");
                add("TOWNNAME");
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
                add("IFCBUILDINGSTOREYATTRIBUT_ID");
                add("INDOORSTOREYATTRIBUTE_ID");
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
            put(schemaMapper.getTableName(ADETable.CITYOBJEC_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("CITYOBJECT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJEC_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("CITYOBJECT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITYOBJEC_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("CITYOBJECT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITY_FURNITURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CITYFURNITUREDETAILATTRIB_ID");
                add("FRNDATAQUALITYATTRIBUTE_ID");
                add("FRNFACILITYIDATTRIBUTE_ID");
                add("FRNFACILITYTYPEATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITY_FURN_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("CITY_FURNITURE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITY_FURN_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("CITY_FURNITURE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CITY_FURN_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("CITY_FURNITURE_ID");
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
            put(schemaMapper.getTableName(ADETable.CONSTRUCTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CONDITIONOFCONSTRUCTION");
                add("CONSBASEATTRIBUTE_ID");
                add("CONSDATAQUALITYATTRIBUTE_ID");
                add("CONSFACILITYIDATTRIBUTE_ID");
                add("CONSSTRUCTUREATTRIBUTE_ID");
                add("DATEOFCONSTRUCTION");
                add("DATEOFDEMOLITION");
                add("LOD0GEOMETRY_BREP_ID");
                add("LOD0GEOMETRY_OTHER_GEOM");
                add("LOD1GEOMETRY_BREP_ID");
                add("LOD1GEOMETRY_OTHER_GEOM");
                add("LOD2GEOMETRY_BREP_ID");
                add("LOD2GEOMETRY_OTHER_GEOM");
                add("LOD3GEOMETRY_BREP_ID");
                add("LOD3GEOMETRY_OTHER_GEOM");
                add("LOD4GEOMETRY_BREP_ID");
                add("LOD4GEOMETRY_OTHER_GEOM");
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
            put(schemaMapper.getTableName(ADETable.CONSTRUCTIONEVENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DATEOFEVENT");
                add("DESCRIPTION");
                add("EVENT");
                add("EVENT_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCTIONINSTALLATI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("CONSTRUCTION_CONSTRUCTION_ID");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
                add("LOD2GEOMETRY_BREP_ID");
                add("LOD2GEOMETRY_OTHER_GEOM");
                add("LOD3GEOMETRY_BREP_ID");
                add("LOD3GEOMETRY_OTHER_GEOM");
                add("USAGE");
                add("USAGE_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.CONSTRUCTIONSTRUCTUREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEPTH");
                add("DEPTH_UOM");
                add("LENGTH");
                add("LENGTH_UOM");
                add("OBJECTCLASS_ID");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("VOLUME");
                add("VOLUME_UOM");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_CONSTRUCT).toUpperCase(), new LinkedHashSet<String>() {{
                add("CONSTRUCTIONEVENT_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_DISASTERR).toUpperCase(), new LinkedHashSet<String>() {{
                add("DISASTERRISKATTRIBUTE_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_ELEVATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ELEVATION_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_FACILITYT).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYTYPEATTRIBUTE_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_HEIGHT).toUpperCase(), new LinkedHashSet<String>() {{
                add("HEIGHT_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONSTRUCT_TO_OCCUPANCY).toUpperCase(), new LinkedHashSet<String>() {{
                add("OCCUPANCY_ID");
                add("CONSTRUCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CONTROLPOINT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ENDPOINT");
                add("ENDPOST");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
                add("PARAMETER_ID");
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
            put(schemaMapper.getTableName(ADETable.COUNTERMEASURESCOST).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COST");
                add("COSTUNIT");
            }});
            put(schemaMapper.getTableName(ADETable.CRS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CRS_ID");
                add("CONTEXT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.CYBERPORTMARINAANDPBS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APRONWIDTH");
                add("APRONWIDTH_UOM");
                add("AREATYPE");
                add("AREATYPE_CODESPACE");
                add("BUMPER");
                add("BUMPER_UOM");
                add("CEILINGHEIGHT");
                add("CEILINGHEIGHT_UOM");
                add("CURRENTDEPTH");
                add("CURRENTDEPTH_UOM");
                add("FACILITYWIDTH");
                add("FACILITYWIDTH_UOM");
                add("FORM");
                add("FORM_CODESPACE");
                add("GEOLOGICALTYPE");
                add("GEOLOGICALTYPE_CODESPACE");
                add("GRAVITYRESISTANT");
                add("GRAVITYRESISTANT_UOM");
                add("INNEROFSITEAREA");
                add("INNEROFSITEAREA_UOM");
                add("INNERTOTALFLOORAREA");
                add("INNERTOTALFLOORAREA_UOM");
                add("ISDREDGED");
                add("LIFESAVING");
                add("LIFESAVINGNUMBER");
                add("LIGHTING");
                add("MAINCARGO");
                add("MAINCARGO_CODESPACE");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("MAINPARTLENGTH");
                add("MAINPARTLENGTH_UOM");
                add("MAINVESSELS");
                add("MAINVESSELS_CODESPACE");
                add("MOORINGPOSTWEIGHT");
                add("MOORINGPOSTWEIGHT_UOM");
                add("NOTE");
                add("NUMBEROFMOORINGPOSTS");
                add("NUMBEROFSEATS");
                add("NUMBEROFVEHICLEBOARDINGS");
                add("OBSTRUCTINGSTRUCTURES");
                add("OUTEROFTOTALFLOORAREA");
                add("OUTEROFTOTALFLOORAREA_UOM");
                add("OUTERSITEAREA");
                add("OUTERSITEAREA_UOM");
                add("PLANNEDDEPTH");
                add("PLANNEDDEPTH_UOM");
                add("RESISTANTMATERIAL");
                add("RESTRICTIONSTRUCTURE");
                add("SHIPTYPE");
                add("STAIRS");
                add("STORAGECAPACITY");
                add("STORAGECAPACITYUNI_CODESPACE");
                add("STORAGECAPACITYUNIT");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("SUBSIDY");
                add("TOTALCOST");
                add("TOTALLENGTH");
                add("TOTALLENGTH_UOM");
                add("VEHICLEBOARDINGWIDTH");
                add("VEHICLEBOARDINGWIDTH_UOM");
                add("WAVEDISSIPATORLENGTH");
                add("WAVEDISSIPATORLENGTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.DAMATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DAMCODE");
                add("DAMCODE_CODESPACE");
                add("TOTALWATERSTORAGE");
                add("TOTALWATERSTORAGE_UOM");
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
            put(schemaMapper.getTableName(ADETable.DAYTIMEPOPULATIONTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AGRICULTURALWORKER");
                add("DAYTIMEOFFICECOMMUTER");
                add("DAYTIMEOVER15");
                add("DAYTIMEPOPULATION");
                add("DAYTIMEPOPULATIONDENSITY");
                add("DAYTIMESCHOOLCOMMUTER");
                add("DAYTIMEUNDER15");
                add("NONCOMMUTER");
            }});
            put(schemaMapper.getTableName(ADETable.DESCRIPTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("FREQUENCYOFSERVICE");
                add("NUMBEROFCUSTOMERS");
            }});
            put(schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AGRICULTURALDEVELOPMENTTY_ID");
                add("RESIDENTIALDEVELOPMENTTYP_ID");
                add("URBANDEVELOPMENTTYPE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.DID).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ID_");
                add("POPULATION");
                add("POPULATIONDENSITY");
            }});
            put(schemaMapper.getTableName(ADETable.DISASTERDAMAGE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DAMAGEDAREA");
                add("DAMAGEDAREA_UOM");
                add("DATE_");
                add("ID_");
                add("MAXIMUMRAINFALLPERHOUR");
                add("NUMBEROFDAMAGEDHOUSES");
                add("NUMBEROFHOUSESFLOODEDABOVEFL");
                add("NUMBEROFHOUSESFLOODEDBELOWFL");
                add("TOTALRAINFALL");
            }});
            put(schemaMapper.getTableName(ADETable.DISASTERPREVENTIONBASE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CAPACITY");
                add("ID_");
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
            put(schemaMapper.getTableName(ADETable.DISASTERRISKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREATYPE");
                add("AREATYPE_CODESPACE");
                add("DESCRIPTION");
                add("DESCRIPTION_CODESPACE");
                add("OBJECTCLASS_ID");
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
                add("STRUCTUREPLACEMENTRESTRICTIO");
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
            put(schemaMapper.getTableName(ADETable.DMANNOTATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("GEOMETRYTYPE");
                add("GEOMETRYTYPE_CODESPACE");
                add("ISVERTICAL");
                add("LABEL");
                add("LINEWIDTH");
                add("LOD0ANCHORPOINT_BREP_ID");
                add("LOD0ANCHORPOINT_OTHER_GEOM");
                add("ORIENTATION");
                add("SHAPETYPE");
                add("SHAPETYPE_CODESPACE");
                add("SIZE_");
                add("SPACING");
            }});
            put(schemaMapper.getTableName(ADETable.DMATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DMCODE");
                add("DMCODE_CODESPACE");
                add("DMELEMENT_ID");
                add("MESHCODE");
                add("MESHCODE_CODESPACE");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.DMELEMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ANNOTATIONTYPE");
                add("ANNOTATIONTYPE_CODESPACE");
                add("ATTRIBUTETYPE");
                add("ATTRIBUTETYPE_CODESPACE");
                add("ATTRIBUTEVALUE");
                add("ATTRIBUTEVALUETYPE");
                add("BREAKTYPE");
                add("BREAKTYPE_CODESPACE");
                add("CREATIONDATE");
                add("DATATYPE");
                add("DATATYPE_CODESPACE");
                add("DISLOCATIONTYPE");
                add("DISLOCATIONTYPE_CODESPACE");
                add("ELEMENTKEY");
                add("FREESPACE");
                add("HIERARCHYLEVEL");
                add("INFOTYPE");
                add("INFOTYPE_CODESPACE");
                add("LOCATIONTYPE");
                add("LOCATIONTYPE_CODESPACE");
                add("PRECISIONTYPE");
                add("PRECISIONTYPE_CODESPACE");
                add("TERMINATIONDATE");
                add("UPDATEDATE");
            }});
            put(schemaMapper.getTableName(ADETable.DMGEOMETRICATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ANGLE");
                add("ELEVATION");
                add("ELEVATION_UOM");
                add("GEOMETRYTYPE");
                add("GEOMETRYTYPE_CODESPACE");
                add("IS3D");
                add("ISEDITED");
                add("ISINSTALLATION");
                add("ISSUPPLEMENTARYSYMBOL");
                add("LOD0GEOMETRY_BREP_ID");
                add("LOD0GEOMETRY_OTHER_GEOM");
                add("MAPLEVEL");
                add("MAPLEVEL_CODESPACE");
                add("SHAPETYPE");
                add("SHAPETYPE_CODESPACE");
                add("VISIBILITY");
            }});
            put(schemaMapper.getTableName(ADETable.ECONOMICACTIVITYSUMTYP).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ECONOMICACTIVITYTYPE_MANU_ID");
                add("ECONOMICACTIVITYTYPE_NUMB__2");
                add("ECONOMICACTIVITYTYPE_NUMB_ID");
                add("ECONOMICACTIVITYTYPE_RETA_ID");
                add("INDUSTRYTYPE");
                add("INDUSTRYTYPE_CODESPACE");
                add("NUMBER_");
            }});
            put(schemaMapper.getTableName(ADETable.ECONOMICACTIVITYTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.ELEVATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ELEVATIONREFERENCE");
                add("ELEVATIONREFERENCE_CODESPACE");
                add("ELEVATIONVALUE");
            }});
            put(schemaMapper.getTableName(ADETable.EMBANKMENTATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CEILINGHEIGHT");
                add("CEILINGHEIGHT_UOM");
                add("MAINPARTLENGTH");
                add("MAINPARTLENGTH_UOM");
                add("WAVEDISSIPATORLENGTH");
                add("WAVEDISSIPATORLENGTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.EXCEPTIONALFLOORAREARA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGHEIGHTLIMITS");
                add("BUILDINGHEIGHTLIMITS_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.FACILITYATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FACILITYID");
                add("OBJECTCLASS_ID");
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
            put(schemaMapper.getTableName(ADETable.FARMLANDCONVERSIONTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ID_");
                add("ISDESIGNATED");
                add("YEAROFDIVERSION");
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
            put(schemaMapper.getTableName(ADETable.FISHINGPORTATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.FISHINGPORTCAPACITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CANDLEPOWER");
                add("CAPACITY");
                add("HANDLEABLEPOWER");
                add("HEIGHTABOVEAWL");
                add("HEIGHTABOVEAWL_UOM");
                add("HEIGHTONFOUNDATIONS");
                add("HEIGHTONFOUNDATIONS_UOM");
                add("HULLFORM");
                add("LIGHTTYPE");
                add("LUMINOUSCOLOR");
                add("LUMINOUSRANGE");
                add("LUMINOUSRANGE_UOM");
                add("MAXIMUMGROUNDINGWEIGHT");
                add("MAXIMUMREFUELING");
                add("MAXIMUMWATERSUPPLY");
                add("OTHER");
                add("PEOPLE");
                add("PERIOD");
                add("SHIPNUMBER");
                add("WATERDEPTH2_3M");
                add("WATERDEPTH2_3M_UOM");
                add("WATERDEPTH3_6M");
                add("WATERDEPTH3_6M_UOM");
                add("WATERDEPTH6_M");
                add("WATERDEPTH6_M_UOM");
                add("WATERDEPTH_2M");
                add("WATERDEPTH_2M_UOM");
                add("WEIGHTCAPACITY");
                add("WEIGHTCAPACITY_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.FISHINGPORTFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADDRESS");
                add("ADMINISTRATOR");
                add("AREA");
                add("AREA_UOM");
                add("CEILINGHEIGHT");
                add("CEILINGHEIGHT_UOM");
                add("COST");
                add("DATEOFCONSTRUCTIONORACQUISIT");
                add("DEPTH");
                add("DEPTH_UOM");
                add("DESIGNATEDADMINISTRATOR");
                add("DESIGNATEDAREA");
                add("DESIGNATION");
                add("FACILITYDETAILSTYP_CODESPACE");
                add("FACILITYDETAILSTYPE");
                add("FACILITYMANAGER");
                add("GRANTTYPE");
                add("GRANTTYPE_CODESPACE");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("NOTE");
                add("OTHERSIZEDESCRIPTION");
                add("OTHERSTRUCTURE");
                add("PORTNAME");
                add("PORTTYPE");
                add("PORTTYPE_CODESPACE");
                add("REFERENCENUMBER");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("WIDTH");
                add("WIDTH_UOM");
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
            }});
            put(schemaMapper.getTableName(ADETable.FORESTCONVERSIONTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ID_");
                add("YEAROFDIVERSION");
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
            put(schemaMapper.getTableName(ADETable.HARBORFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREATYPE");
                add("AREATYPE_CODESPACE");
                add("CURRENTDEPTH");
                add("CURRENTDEPTH_UOM");
                add("GEOLOGICALTYPE");
                add("GEOLOGICALTYPE_CODESPACE");
                add("INNERAREA");
                add("INNERAREA_UOM");
                add("ISDREDGED");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAXIMUMWIDTH");
                add("MAXIMUMWIDTH_UOM");
                add("MINIMUMWIDTH");
                add("MINIMUMWIDTH_UOM");
                add("NOTE");
                add("OBSTRUCTINGSTRUCTURES");
                add("OUTERAREA");
                add("OUTERAREA_UOM");
                add("PLANNEDDEPTH");
                add("PLANNEDDEPTH_UOM");
                add("STRUCTURALLIMITATIONS");
                add("STRUCTURALLIMITATIONS_UOM");
                add("SUBSIDY");
                add("TOTALCOST");
            }});
            put(schemaMapper.getTableName(ADETable.HEIGHT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("HIGHREFERENCE");
                add("HIGHREFERENCE_CODESPACE");
                add("LOWREFERENCE");
                add("LOWREFERENCE_CODESPACE");
                add("STATUS");
                add("VALUE");
                add("VALUE_UOM");
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
            put(schemaMapper.getTableName(ADETable.IFCATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCAXIS2PLACEMENT3D).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AXIS");
                add("LOCATION");
                add("REFDIRECTION");
            }});
            put(schemaMapper.getTableName(ADETable.IFCBUILDING).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDINGADDRESS_ID");
                add("ELEVATIONOFREFHEIGHT");
                add("ELEVATIONOFREFHEIGHT_UOM");
                add("ELEVATIONOFTERRAIN");
                add("ELEVATIONOFTERRAIN_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.IFCBUILDINGELEMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CAPACITYBYNUMBER");
                add("CAPACITYBYWEIGHT");
                add("CAPACITYBYWEIGHT_UOM");
                add("ELEMENTTYPE");
                add("ELEMENTTYPE_CODESPACE");
                add("NUMBEROFRISER");
                add("NUMBEROFTREADS");
                add("OBJECTCLASS_ID");
                add("OPERATIONTYPE");
                add("PREDEFINEDTYPE");
                add("PREDEFINEDTYPE_CODESPACE");
                add("RISERHEIGHT");
                add("RISERHEIGHT_UOM");
                add("SHAPETYPE");
                add("SHAPETYPE_CODESPACE");
                add("TREADLENGTH");
                add("TREADLENGTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.IFCCLASSIFICATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("EDITION");
                add("EDITIONDATE");
                add("NAME");
                add("SOURCE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCCLASSIFICATIONREFER).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ITEMREFERENCE");
                add("ITEMREFERENCE_CODESPACE");
                add("LOCATION");
                add("NAME");
                add("REFERENCEDSOURCE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCCOORDINATEREFERENCE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("GEODETICDATUM");
                add("NAME");
                add("VERTICALDATUM");
            }});
            put(schemaMapper.getTableName(ADETable.IFCDOOR).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OVERALLHEIGHT");
                add("OVERALLHEIGHT_UOM");
                add("OVERALLWIDTH");
                add("OVERALLWIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.IFCELEMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
                add("TAG");
            }});
            put(schemaMapper.getTableName(ADETable.IFCGEOMETRICREPRESENTA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CONTEXTIDENTIFIER");
                add("CONTEXTTYPE");
                add("COORDINATESPACEDIMENSION");
                add("IFCAXIS2PLACEME_REFDIRECTION");
                add("IFCAXIS2PLACEMENT3DTYPE_AXIS");
                add("OBJECTCLASS_ID");
                add("PRECISION");
                add("TRUENORTH");
                add("WORLDCOORDINATESYSTEM_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCMAPCONVERSION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("EASTINGS");
                add("EASTINGS_UOM");
                add("NORTHINGS");
                add("NORTHINGS_UOM");
                add("ORTHOGONALHEIGHT");
                add("ORTHOGONALHEIGHT_UOM");
                add("SCALE");
                add("SOURCECRS_ID");
                add("TARGETCRS_ID");
                add("XAXISABSCISSA");
                add("XAXISORDINATE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCOPENINGELEMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NOMINALAREA");
                add("NOMINALAREA_UOM");
                add("NOMINALVOLUME");
                add("NOMINALVOLUME_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPRODUCT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPROJECT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LONGNAME");
                add("PHASE");
                add("REPRESENTATIONCONTEXTS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPROJECTEDCRS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAPPROJECTION");
                add("MAPUNIT");
                add("MAPZONE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPROJEC_TO_IFCUNIT).toUpperCase(), new LinkedHashSet<String>() {{
                add("IFCUNIT_ID");
                add("IFCPROJECT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPSETBUILDINGCOMMON).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ANCILLARYFIREUSE");
                add("BUILDINGID");
                add("GROSSPLANNEDAREA");
                add("GROSSPLANNEDAREA_UOM");
                add("ISLANDMARKED");
                add("ISPERMANENTID");
                add("MAINFIREUSE");
                add("NUMBEROFSTOREYS");
                add("OCCUPANCYTYPE");
                add("OCCUPANCYTYPE_CODESPACE");
                add("SPRINKLERPROTECTION");
                add("SPRINKLERPROTECTIONAUTOMATIC");
                add("YEAROFCONSTRUCTION");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPSETDOORCOMMON).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACOUSTICRATING");
                add("FIREEXIT");
                add("FIRERATING");
                add("GLAZINGAREAFRACTION");
                add("HANDICAPACCESSIBLE");
                add("INFILTRATION");
                add("ISEXTERNAL");
                add("REFERENCE");
                add("SECURITYRATING");
                add("SELFCLOSING");
                add("SMOKESTOP");
                add("THERMALTRANSMITTANCE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPSETOPENINGELEMENTC).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FIREEXIT");
                add("PARALLELJAMBS");
                add("PROTECTEDOPENING");
                add("PURPOSE");
                add("REFERENCE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPSETSITECOMMON).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("BUILDABLEAREA");
                add("BUILDABLEAREA_UOM");
                add("BUILDINGHEIGHTLIMIT");
                add("BUILDINGHEIGHTLIMIT_UOM");
                add("TOTALAREA");
                add("TOTALAREA_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPSETSPACECOMMON).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CATEGORY");
                add("CEILINGCOVERING");
                add("CONCEALEDCEILING");
                add("CONCEALEDFLOORING");
                add("FLOORCOVERING");
                add("GROSSPLANNEDAREA");
                add("GROSSPLANNEDAREA_UOM");
                add("HANDICAPACCESSIBLE");
                add("NETPLANNEDAREA");
                add("NETPLANNEDAREA_UOM");
                add("PUBLICLYACCESSIBLE");
                add("REFERENCE");
                add("SKIRTINGBOARD");
                add("WALLCOVERING");
            }});
            put(schemaMapper.getTableName(ADETable.IFCPSETWINDOWCOMMON).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACOUSTICRATING");
                add("FIRERATING");
                add("GLAZINGAREAFRACTION");
                add("INFILTRATION");
                add("ISEXTERNAL");
                add("REFERENCE");
                add("SECURITYRATING");
                add("SMOKESTOP");
                add("THERMALTRANSMITTANCE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCROOT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("GLOBALID");
                add("NAME");
                add("OBJECTCLASS_ID");
                add("OBJECTTYPE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCSITE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LANDTITLENUMBER");
                add("REFELEVATION");
                add("REFELEVATION_UOM");
                add("REFLATITUDE");
                add("REFLONGITUDE");
                add("SITEADDRESS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCSPACE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ELEVATIONWITHFLOORING");
                add("ELEVATIONWITHFLOORING_UOM");
                add("INTERIOROREXTERIORSPACE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCSPACEBASEQUANTITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CLEARHEIGHT");
                add("CLEARHEIGHT_UOM");
                add("FINISHCEILINGHEIGHT");
                add("FINISHCEILINGHEIGHT_UOM");
                add("GROSSCEILINGAREA");
                add("GROSSCEILINGAREA_UOM");
                add("GROSSFLOORAREA");
                add("GROSSFLOORAREA_UOM");
                add("GROSSPERIMETER");
                add("GROSSPERIMETER_UOM");
                add("GROSSVOLUME");
                add("GROSSVOLUME_UOM");
                add("GROSSWALLAREA");
                add("GROSSWALLAREA_UOM");
                add("NETCEILINGAREA");
                add("NETCEILINGAREA_UOM");
                add("NETFLOORAREA");
                add("NETFLOORAREA_UOM");
                add("NETPERIMETER");
                add("NETPERIMETER_UOM");
                add("NETVOLUME");
                add("NETVOLUME_UOM");
                add("NETWALLAREA");
                add("NETWALLAREA_UOM");
                add("NOMINALHEIGHT");
                add("NOMINALHEIGHT_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.IFCSPATIALSTRUCTUREELE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COMPOSITIONTYPE");
                add("ELEVATION");
                add("ELEVATION_UOM");
                add("LONGNAME");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCUNIT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DIMENSIONS");
                add("NAME");
                add("PREFIX");
                add("UNITTYPE");
            }});
            put(schemaMapper.getTableName(ADETable.IFCWALL).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("GROSSFOOTPRINTAREA");
                add("GROSSFOOTPRINTAREA_UOM");
                add("GROSSSIDEAREA");
                add("GROSSSIDEAREA_UOM");
                add("GROSSSIDEAREALEFT");
                add("GROSSSIDEAREALEFT_UOM");
                add("GROSSSIDEAREARIGHT");
                add("GROSSSIDEAREARIGHT_UOM");
                add("GROSSVOLUME");
                add("GROSSVOLUME_UOM");
                add("NETFOOTPRINTAREA");
                add("NETFOOTPRINTAREA_UOM");
                add("NETSIDEAREA");
                add("NETSIDEAREA_UOM");
                add("NETSIDEAREALEFT");
                add("NETSIDEAREALEFT_UOM");
                add("NETSIDEAREARIGHT");
                add("NETSIDEAREARIGHT_UOM");
                add("NETVOLUME");
                add("NETVOLUME_UOM");
                add("NOMINALHEIGHT");
                add("NOMINALHEIGHT_UOM");
                add("NOMINALLENGTH");
                add("NOMINALLENGTH_UOM");
                add("NOMINALWIDTH");
                add("NOMINALWIDTH_UOM");
                add("OBJECTCLASS_ID");
            }});
            put(schemaMapper.getTableName(ADETable.IFCWINDOW).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("OVERALLHEIGHT");
                add("OVERALLHEIGHT_UOM");
                add("OVERALLWIDTH");
                add("OVERALLWIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.INDOORATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FLOORID");
                add("FLOORID_1");
                add("OBJECTCLASS_ID");
                add("SOURCE");
                add("SOURCE_CODESPACE");
                add("UCODE");
            }});
            put(schemaMapper.getTableName(ADETable.INDOORFACILITYATTRIBUT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PHONE");
                add("WEBSITE");
                add("WEEKDAYHOURS");
                add("WEEKENDHOURS");
            }});
            put(schemaMapper.getTableName(ADETable.INDOORSPACEATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FLOORID");
                add("ISPUBLIC");
                add("ISRESTRICTED");
                add("SUITE");
                add("TOLLTYPE");
                add("TOLLTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.INDOORSTOREYATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CATEGORY");
                add("ORDINAL");
            }});
            put(schemaMapper.getTableName(ADETable.INDOORTACTILETILEATTRI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CATEGORY");
                add("CATEGORY_CODESPACE");
                add("ENDNODE");
                add("FLOORID");
                add("ROOF");
                add("STARTNODE");
            }});
            put(schemaMapper.getTableName(ADETable.INDOORUSERDEFINEDATTRI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("NAME");
                add("NOMINALVALUE_ID");
                add("UNIT");
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
                add("KEY");
                add("KEY_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.LANDPRICE_2).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACREAGE");
                add("ACREAGE_UOM");
                add("ADDRESS");
                add("BUILDINGCOVERAGERATE");
                add("BUILDINGSTRUCTURE");
                add("CITYCODE");
                add("CITYCODE_CODESPACE");
                add("CITYNAME");
                add("DEPTHRATE");
                add("DISTANCEFROMSTATION");
                add("DISTANCEFROMSTATION_UOM");
                add("EXTRAFLOORAREARATE");
                add("FIREPREVENTION");
                add("FISCALYEAR");
                add("FLOORAREARATE");
                add("FOREST");
                add("FRONTAGERATE");
                add("FRONTAGEROAD");
                add("FRONTAGEROADDIRECTION");
                add("FRONTAGEROADSURFACE");
                add("FRONTAGEROADWIDTH");
                add("FRONTAGEROADWIDTH_UOM");
                add("GAS");
                add("HEIGHTCONTROL");
                add("ISACREAGECHANGED");
                add("ISADDRESSCHANGED");
                add("ISBUILDINGCOVERAGERATECHANGE");
                add("ISBUILDINGSTRUCTURECHANGED");
                add("ISDISTANCEFROMSTATIONCHANGED");
                add("ISFIREPREVENTIONCHANGED");
                add("ISFLOORAREARATECHANGED");
                add("ISSAME");
                add("ISSELECTED");
                add("ISSUPPLYFACILITIESCHANGED");
                add("ISURBANPLANAREACHANGED");
                add("ISUSECHANGED");
                add("ISUSEDISTRICTCHANGED");
                add("LANDPRICE");
                add("LOCATION");
                add("PARK");
                add("POINTCODE");
                add("POINTCODEOFLASTYEAR");
                add("SELECTIONSTATUS");
                add("SEWERAGE");
                add("SHAPE");
                add("SIDEROAD");
                add("SIDEROADDIRECTION");
                add("STATIONNAME");
                add("STATIONSQUARE");
                add("STOREYSABOVEGROUND");
                add("STOREYSBELOWGROUND");
                add("SURROUNDINGS");
                add("TRANSPORTATIONFACILITIES");
                add("URBANPLANAREATYPE");
                add("USEDISTRICT");
                add("USEDISTRICT_CODESPACE");
                add("USESTATUS");
                add("USESTATUSDESCRIPTION");
                add("USETYPE");
                add("VARIABILITY");
                add("WATER");
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
            put(schemaMapper.getTableName(ADETable.LANDUSEDIVERSION_2).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FARMLANDCONVERSIONTYPE_ID");
                add("FORESTCONVERSIONTYPE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.LAND_USE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("IFCLANDUSEATTRIBUTE_ID");
                add("LANDUSEDETAILATTRIBUTE_ID");
                add("LUSEDATAQUALITYATTRIBUTE_ID");
                add("LUSEFACILITYIDATTRIBUTE_ID");
                add("LUSEFACILITYTYPEATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.LAND_USE_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("LAND_USE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.LAND_USE_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("LAND_USE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.LAND_USE_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("LAND_USE_ID");
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
            put(schemaMapper.getTableName(ADETable.LENGTHATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MEASURETYPE");
                add("MEASURETYPE_CODESPACE");
                add("PHASETYPE");
                add("PHASETYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.LONGEVITYMEASURES).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COUNTERMEASURESCOST_ID");
                add("DESCRIPTION");
                add("FISCALYEARFORCOUNTERMEASURES");
            }});
            put(schemaMapper.getTableName(ADETable.MAINTENANCEHISTORYATTR).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DESCRIPTION");
                add("MAINTENANCEDATE");
                add("MAINTENANCEFISCALYEAR");
                add("MAINTENANCETYPE");
                add("MAINTENANCETYPE_CODESPACE");
                add("MAINTENANCEYEAR");
                add("STATUS");
            }});
            put(schemaMapper.getTableName(ADETable.MOORINGFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APRONWIDTH");
                add("APRONWIDTH_UOM");
                add("AREA");
                add("AREA_UOM");
                add("BUMPER");
                add("BUMPER_UOM");
                add("CEILINGHEIGHT");
                add("CEILINGHEIGHT_UOM");
                add("CURRENTDEPTH");
                add("CURRENTDEPTH_UOM");
                add("FACILITYWIDTH");
                add("FACILITYWIDTH_UOM");
                add("FORM");
                add("FORM_CODESPACE");
                add("GRAVITYRESISTANT");
                add("GRAVITYRESISTANT_UOM");
                add("LIFESAVINGAPPLIANCES");
                add("LIGHTING");
                add("MAINCARGO");
                add("MAINCARGO_CODESPACE");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("MAINPARTLENGTH");
                add("MAINPARTLENGTH_UOM");
                add("MAINVESSELS");
                add("MAINVESSELS_CODESPACE");
                add("MOORINGPOSTWEIGHT");
                add("MOORINGPOSTWEIGHT_UOM");
                add("NOTE");
                add("NUMBEROFLIFESAVINGAPPLIANCES");
                add("NUMBEROFMOORINGPOSTS");
                add("NUMBEROFSEATS");
                add("NUMBEROFVEHICLEBOARDINGS");
                add("PLANNEDDEPTH");
                add("PLANNEDDEPTH_UOM");
                add("RESISTANTMATERIAL");
                add("SHIPTYPE");
                add("STAIRS");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("SUBSIDY");
                add("TOTALCOST");
                add("TOTALLENGTH");
                add("TOTALLENGTH_UOM");
                add("VEHICLEBOARDINGWIDTH");
                add("VEHICLEBOARDINGWIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.NAVIGATIONASSISTANCEFA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NOTE");
                add("SUBSIDY");
                add("TOTALCOST");
            }});
            put(schemaMapper.getTableName(ADETable.NEWHOUSING).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEVELOPER");
                add("ID_");
                add("PURPOSE");
                add("PURPOSE_CODESPACE");
                add("YEAROFCONSTRUCTION");
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
            put(schemaMapper.getTableName(ADETable.NUMBEROFFACILITIES).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("QUANTITY");
                add("QUANTITYUNIT");
                add("QUANTITYUNIT_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.NUMBEROFHOUSEHOLDS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("HOUSEHOLDS_NUMBEROFHOUS_ID_1");
                add("HOUSEHOLDS_NUMBEROFHOUSEH_ID");
                add("NUMBER_");
            }});
            put(schemaMapper.getTableName(ADETable.OBSERVATIONPOINT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LOD0MULTIPOINT");
            }});
            put(schemaMapper.getTableName(ADETable.OCCUPANCY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("INTERVAL");
                add("INTERVAL_CODESPACE");
                add("NUMBEROFOCCUPANTS");
                add("OCCUPANTTYPE");
                add("OCCUPANTTYPE_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.OFFSETDEPTH).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEPTH");
                add("DEPTH_UOM");
                add("MAXDEPTH");
                add("MAXDEPTH_UOM");
                add("MINDEPTH");
                add("MINDEPTH_UOM");
                add("OFFSET_");
                add("OFFSET_UOM");
                add("POS");
            }});
            put(schemaMapper.getTableName(ADETable.OPENING).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("IFCOPENINGATTRIBUTE_ID");
                add("INDOOROPENINGATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.OPENSPACEFORPUBLICUSE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PARKATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.OTHERCONSTRUCTION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("FUNCTION");
                add("FUNCTION_CODESPACE");
                add("USAGE");
                add("USAGE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.PARKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PARKSERIALNUMBER");
                add("PARKSIZENUMBER");
                add("PARKSIZENUMBER_CODESPACE");
                add("PARKTYPENUMBER");
                add("PARKTYPENUMBER_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.PARKFACILITYLONGEVITYP).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DISPOSALLIMITPERIOD");
                add("EXPECTEDRENEWALYEARWITHMEASU");
                add("EXPECTEDUSAGEPERIOD");
                add("FACILITYNAME");
                add("FACILITYNAME_CODESPACE");
                add("FACILITYNAMEOPTIONAL");
                add("INSTALLATIONYEAR");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("MAINMATERIALOPTIONAL");
                add("MANAGEMENTTYPE");
                add("MANAGEMENTTYPE_CODESPACE");
                add("NOTEFORLONGEVITY");
                add("NUMBEROFFACILITIES_ID");
                add("PARKCODE");
                add("PARKCODE_CODESPACE");
                add("PARKNAME");
                add("PARKNAME_CODESPACE");
                add("PARKTYPE");
                add("PARKTYPE_CODESPACE");
                add("REPAIRSBEFOREPARKHEALTHAS_ID");
                add("SIZE_");
                add("SPECIFICFACILITYNAME");
            }});
            put(schemaMapper.getTableName(ADETable.PARKFACIL_TO_LONGEVITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("LONGEVITYMEASURES_ID");
                add("PARKFACILITYLONGEVITYP_ID");
            }});
            put(schemaMapper.getTableName(ADETable.PARKFACIL_TO_PARKHEALT).toUpperCase(), new LinkedHashSet<String>() {{
                add("PARKHEALTHASSESSMENT_ID");
                add("PARKFACILITYLONGEVITYP_ID");
            }});
            put(schemaMapper.getTableName(ADETable.PARKHEALTHASSESSMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ASSESSMENTFISCALYEAR");
                add("CONDITION");
                add("CONDITION_CODESPACE");
                add("DETERIORATIONSTATUS");
                add("URGENCY");
                add("URGENCY_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.PIPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("INNERDIAMETER");
                add("INNERDIAMETER_UOM");
                add("OBJECTCLASS_ID");
                add("OUTERDIAMETER");
                add("OUTERDIAMETER_UOM");
                add("SLEEVETYPE");
                add("SLEEVETYPE_CODESPACE");
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
                add("ID_");
                add("OBSERVATIONPOINT_ID");
                add("YEAR");
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
            put(schemaMapper.getTableName(ADETable.POPULATIONBYAGEANDSEXT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AGEANDSEX");
                add("AGEANDSEX_CODESPACE");
                add("NUMBER_");
            }});
            put(schemaMapper.getTableName(ADETable.POPULATIONTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("POPULATIONDENSITY");
            }});
            put(schemaMapper.getTableName(ADETable.POPULATIO_TO_POPULAT_2).toUpperCase(), new LinkedHashSet<String>() {{
                add("POPULATIONBYAGEANDSEXT_ID");
                add("POPULATIONTYPE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.PORTATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEGRADATIONLEVEL");
                add("DISTRICT");
                add("GRANTTYPE");
                add("GRANTTYPE_CODESPACE");
                add("ISDESIGNATED");
                add("OBJECTCLASS_ID");
                add("PORTFACILITYDETAIL_CODESPACE");
                add("PORTFACILITYDETAILSTYPE");
                add("PORTNAME");
                add("PORTSTATUS");
                add("PORTSTATUS_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.PORTENVIRONMENTALIMPRO).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("LENGTH");
                add("LENGTH_UOM");
                add("NOTE");
                add("SUBSIDY");
                add("TOTALCOST");
                add("TOTALFOORAREA");
                add("TOTALFOORAREA_UOM");
                add("USAGE");
            }});
            put(schemaMapper.getTableName(ADETable.PORTMANAGEMENTFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACQUISITIONYEAR");
                add("LOADINGCAPACITY");
                add("NOTE");
                add("NUMBEROFSHIPTYPES");
                add("SUBSIDY");
                add("TOTALCOST");
                add("TOTALFLOORAREA");
                add("TOTALFLOORAREA_UOM");
                add("UNITOFSHIPTYPE");
                add("UNITOFSHIPTYPE_CODESPACE");
                add("USAGE");
            }});
            put(schemaMapper.getTableName(ADETable.PORTPASSENGERFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACQUISITIONYEAR");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("NOTE");
                add("TOTALCOST");
                add("TOTALFLOORAREA");
                add("TOTALFLOORAREA_UOM");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.PORTPOLLUTIONCONTROLFA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("CROSSSECTIONALAREA");
                add("CROSSSECTIONALAREA_UOM");
                add("HEIGHT");
                add("HEIGHT_UOM");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("NOTE");
                add("SUBSIDY");
                add("TOTALCOST");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.PORTPROTECTIVEFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("NOTE");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("SUBSIDY");
                add("TOTALCOST");
            }});
            put(schemaMapper.getTableName(ADETable.PORTSTORAGEFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("INNEROFSITEAREA");
                add("INNEROFSITEAREA_UOM");
                add("INNERTOTALFLOORAREA");
                add("INNERTOTALFLOORAREA_UOM");
                add("MAINCARGO");
                add("MAINCARGO_CODESPACE");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("NOTE");
                add("OUTEROFTOTALFLOORAREA");
                add("OUTEROFTOTALFLOORAREA_UOM");
                add("OUTERSITEAREA");
                add("OUTERSITEAREA_UOM");
                add("STORAGECAPACITY");
                add("STORAGECAPACITYUNI_CODESPACE");
                add("STORAGECAPACITYUNIT");
                add("TOTALCOST");
            }});
            put(schemaMapper.getTableName(ADETable.PORTTRANSPORTATIONFACI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("BEDDINGWIDTH");
                add("BEDDINGWIDTH_UOM");
                add("HEIGHTLIMIT");
                add("HEIGHTLIMIT_UOM");
                add("HEIGHTTODIGIT");
                add("HEIGHTTODIGIT_UOM");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAINCARGO");
                add("MAINCARGO_CODESPACE");
                add("MINIMUMDEPTH");
                add("MINIMUMDEPTH_UOM");
                add("MINIMUMWIDTH");
                add("MINIMUMWIDTH_UOM");
                add("NOTE");
                add("NUMBEROFAIRCRAFTPARKINGSPACE");
                add("NUMBEROFLANES");
                add("PARKINGLOTCAPACITYOFBUS");
                add("PARKINGLOTCAPACITYOFCARS");
                add("PAVEMENTTYPE");
                add("PAVEMENTTYPE_CODESPACE");
                add("ROUTETYPE");
                add("ROUTETYPE_CODESPACE");
                add("STARTINGPOINT");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("SUBSIDY");
                add("TOTALCOST");
            }});
            put(schemaMapper.getTableName(ADETable.PORTWASTETREATMENTFACI).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACQUISITIONYEAR");
                add("CEILINGHEIGHT");
                add("CEILINGHEIGHT_UOM");
                add("INNERSHORELENGTH");
                add("INNERSHORELENGTH_UOM");
                add("MAINMATERIAL");
                add("MAINMATERIAL_CODESPACE");
                add("MAINPARTLENGTH");
                add("MAINPARTLENGTH_UOM");
                add("NOTE");
                add("PERIMETER");
                add("PERIMETER_UOM");
                add("PLANNEDDISPOSALAMOUNT");
                add("PLANNEDDISPOSALAREA");
                add("PLANNEDDISPOSALAREA_UOM");
                add("RECEIVINGCAPACITY");
                add("SHIPTYPE");
                add("STRUCTURETYPE");
                add("STRUCTURETYPE_CODESPACE");
                add("SUBSIDY");
                add("TOTALCOST");
                add("UNITOFRECEIVINGCAP_CODESPACE");
                add("UNITOFRECEIVINGCAPACITY");
                add("WASTETYPE");
                add("WASTETYPE_CODESPACE");
                add("WAVEDISSIPATORLENGTH");
                add("WAVEDISSIPATORLENGTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.PORTWELFAREFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("NOTE");
                add("TOTALCOST");
                add("TOTALFLOORAREA");
                add("TOTALFLOORAREA_UOM");
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
            put(schemaMapper.getTableName(ADETable.PUBLICLAND).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("ID_1");
                add("LANDCATEGORY");
                add("NOTE");
                add("OWNERNAME");
                add("OWNERTYPE");
                add("OWNERTYPE_CODESPACE");
                add("STATUS");
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
            put(schemaMapper.getTableName(ADETable.PUBLICTRANSITFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COMPANYNAME");
                add("COMPANYTYPE");
                add("FACILITYTYPE");
                add("FREQUENCYOFSERVICE");
                add("ID_");
                add("NUMBEROFCUSTOMERS");
                add("SECIONNAME");
            }});
            put(schemaMapper.getTableName(ADETable.QUASIURBANPLANNINGAREA).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CITYAREA");
                add("CITYAREA_UOM");
                add("CITYPOPULATION");
                add("POPULATION");
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
            put(schemaMapper.getTableName(ADETable.RAILWAYTR_TO_CONTROLPO).toUpperCase(), new LinkedHashSet<String>() {{
                add("CONTROLPOINT_ID");
                add("RAILWAYTRACKATTRIBUTE_ID");
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
                add("ID_");
                add("NUMBEROFUSERS");
            }});
            put(schemaMapper.getTableName(ADETable.RELIEF_COMPONENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.RELIEF_CO_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("RELIEF_COMPONENT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.RELIEF_FEATURE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEMDATAQUALITYATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.RELIEF_FE_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("RELIEF_FEATURE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.REPAIRSBEFOREPARKHEALT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("REPAIR");
                add("REPAIR_CODESPACE");
                add("REPAIRFISCALYEAR");
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
            put(schemaMapper.getTableName(ADETable.RESIDENTIALDEVELOPMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AREA");
                add("AREA_UOM");
                add("ID_");
                add("NOTE");
                add("PROJECTPERIOD");
                add("PURPOSE");
                add("STATUS");
                add("STATUS_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.ROOM).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("IFCROOMATTRIBUTE_ID");
                add("INDOORROOMATTRIBUTE_ID");
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
            put(schemaMapper.getTableName(ADETable.SCENICRESOURCE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADDRESS");
                add("ID_");
                add("TYPENAME");
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
            put(schemaMapper.getTableName(ADETable.SEWERPIPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("FLOWDIRECTION");
                add("INVERTELEVATIONDOWNSTREA_UOM");
                add("INVERTELEVATIONDOWNSTREAM");
                add("INVERTELEVATIONUPSTREAM");
                add("INVERTELEVATIONUPSTREAM_UOM");
                add("SLOPE");
                add("SLOPE_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.SEWERSYSTEMATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DRAINAGEAREA");
                add("ENDLOCATION");
                add("STARTLOCATION");
                add("SYSTEMTYPE");
                add("SYSTEMTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.SHIPSERVICEFACILITY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ACQUISITIONYEAR");
                add("AREA");
                add("AREA_UOM");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MOORINGPLACE");
                add("NOTE");
                add("SHIPTYPE");
                add("SUPPLYABILITY");
                add("SUPPLYABILITYUNIT");
                add("SUPPLYABILITYUNIT_CODESPACE");
                add("TOTALCOST");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.SLOPETYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ANGLE");
                add("ELEVATION");
                add("ELEVATION_UOM");
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
            put(schemaMapper.getTableName(ADETable.THEMATICSHAPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("HEIGHTTYPE");
                add("HEIGHTTYPE_CODESPACE");
                add("HORIZONTALTYPE");
                add("HORIZONTALTYPE_CODESPACE");
                add("SHAPE_BREP_ID");
                add("SHAPE_OTHER_GEOM");
            }});
            put(schemaMapper.getTableName(ADETable.THEMATIC_SURFACE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("IFCBOUNDARYSURFACEATTRIBU_ID");
                add("INDOORBOUNDARYSURFACEATTR_ID");
            }});
            put(schemaMapper.getTableName(ADETable.THREEDIMENSIONALEXTENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("MAXIMUMLOAD");
                add("MAXIMUMLOAD_UOM");
                add("MINIMUMDISTANCE");
                add("MINIMUMDISTANCE_UOM");
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
            put(schemaMapper.getTableName(ADETable.TRANSPORT_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("TRANSPORTATION_COMPLEX_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TRANSPORT_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("TRANSPORTATION_COMPLEX_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TRANSPORT_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("TRANSPORTATION_COMPLEX_ID");
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
            put(schemaMapper.getTableName(ADETable.TUNNEL_TO_DISASTERR).toUpperCase(), new LinkedHashSet<String>() {{
                add("DISASTERRISKATTRIBUTE_ID");
                add("TUNNEL_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TUNNEL_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("TUNNEL_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TUNNEL_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("TUNNEL_ID");
            }});
            put(schemaMapper.getTableName(ADETable.TUNNEL_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("TUNNEL_ID");
            }});
            put(schemaMapper.getTableName(ADETable.UNDERGROUNDBUILDING).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.URBANDEVELOPMENTPROJEC).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COMPLETEDAREA");
                add("COMPLETEDAREA_UOM");
                add("COST");
                add("COSTUPTOLASTFISCALYEAR");
                add("DATEOFDECISION");
                add("ID_");
                add("NOTE");
                add("ONGOINGAREA");
                add("ONGOINGAREA_UOM");
                add("PLANNEDAREA");
                add("PLANNEDAREA_UOM");
                add("PROJECTNAME");
                add("PROJECTPERIOD");
            }});
            put(schemaMapper.getTableName(ADETable.URBANDEVELOPMENTPROJ_2).toUpperCase(), new LinkedHashSet<String>() {{
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
            put(schemaMapper.getTableName(ADETable.URBANFACILITYSTATUS).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("COMPLETEDDATE");
                add("COSTUPTTOLASTYEAR");
                add("DATEOFDECISION");
                add("FACILITYNAME");
                add("ID_");
                add("ISCOMPLETED");
                add("ISIMPLEMENTED");
                add("ISPLANNED");
                add("STARTDATE");
                add("TOTALCOST");
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
                add("DATAQUALITYATTRIBUTE_ID");
                add("ENACTMENTFISCALYEAR");
                add("EXPIRATIONFISCALYEAR");
                add("FINALNOTIFICATIONDATE");
                add("FINALNOTIFICATIONNUMBER");
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
            put(schemaMapper.getTableName(ADETable.URBANFUNC_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("URBANFUNCTION_ID");
            }});
            put(schemaMapper.getTableName(ADETable.URBANIZATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PERIOD");
                add("RESOURCES");
            }});
            put(schemaMapper.getTableName(ADETable.URBANPARKATTRIBUTE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("PARKCODE");
                add("PARKCODE_CODESPACE");
                add("STARTFROM");
            }});
            put(schemaMapper.getTableName(ADETable.URBANPARK_TO_BREAKDOWN).toUpperCase(), new LinkedHashSet<String>() {{
                add("BREAKDOWNOFNOMINALAREA_ID");
                add("URBANPARKATTRIBUTE_ID");
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
                add("ADJACENTLANDRESTRICTIONS");
                add("BUILDINGCOVERAGERATE");
                add("BUILDINGHEIGHTLIMITS");
                add("BUILDINGHEIGHTLIMITS_UOM");
                add("BUILDINGRESTRICTIONS");
                add("FLOORAREARATE");
                add("FRONTROADRESTRICTIONS");
                add("MINIMUMSITEAREA");
                add("MINIMUMSITEAREA_UOM");
                add("NORTHDIRECTIONRESTRICTIONS");
                add("OTHERRESTRICTIONS");
                add("SETBACKRESTRICTIONS");
                add("SHADEREGULATION");
                add("WALLSETBACKDISTANCE");
            }});
            put(schemaMapper.getTableName(ADETable.USERDEFINEDVALUE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CODEVALUE");
                add("CODEVALUE_CODESPACE");
                add("DATEVALUE");
                add("DOUBLEVALUE");
                add("INTVALUE");
                add("MEASUREDVALUE");
                add("MEASUREDVALUE_UOM");
                add("STRINGVALUE");
                add("URIVALUE");
            }});
            put(schemaMapper.getTableName(ADETable.UTILITYLINK).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("DEPTH");
                add("DEPTH_UOM");
                add("ENDNODE");
                add("MATERIAL");
                add("MATERIAL_CODESPACE");
                add("MAXDEPTH");
                add("MAXDEPTH_UOM");
                add("MAXWIDTH");
                add("MAXWIDTH_UOM");
                add("MINDEPTH");
                add("MINDEPTH_UOM");
                add("OBJECTCLASS_ID");
                add("OFFSET_");
                add("OFFSET_UOM");
                add("ROUTEENDNODE");
                add("ROUTESTARTNODE");
                add("STARTNODE");
                add("WIDTH");
                add("WIDTH_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.UTILITYLI_TO_LENGTHATT).toUpperCase(), new LinkedHashSet<String>() {{
                add("LENGTHATTRIBUTE_ID");
                add("UTILITYLINK_ID");
            }});
            put(schemaMapper.getTableName(ADETable.UTILITYNETWORKELEMENT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("ADMINISTRATOR");
                add("ADMINISTRATOR_CODESPACE");
                add("OBJECTCLASS_ID");
                add("OCCUPIERNAME");
                add("OCCUPIERNAME_CODESPACE");
                add("OCCUPIERTYPE");
                add("OCCUPIERTYPE_CODESPACE");
                add("YEAR");
                add("YEARTYPE");
                add("YEARTYPE_CODESPACE");
            }});
            put(schemaMapper.getTableName(ADETable.UTILITYNE_TO_OFFSETDEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("OFFSETDEPTH_ID");
                add("UTILITYNETWORKELEMENT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.UTILITYNE_TO_THEMATICS).toUpperCase(), new LinkedHashSet<String>() {{
                add("THEMATICSHAPE_ID");
                add("UTILITYNETWORKELEMENT_ID");
            }});
            put(schemaMapper.getTableName(ADETable.UTILITYNODE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPURTENANCETYPE");
                add("APPURTENANCETYPE_CODESPACE");
                add("NEXTLINK");
                add("PREVIOUSLINK");
                add("ROTATIONANGLE");
            }});
            put(schemaMapper.getTableName(ADETable.UTILITYNODECONTAINER).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("APPURTENANCE");
                add("CONTAINERTYPE");
                add("CONTAINERTYPE_CODESPACE");
                add("DEPTH");
                add("DEPTH_UOM");
                add("ELEVATION");
                add("ELEVATION_UOM");
                add("INNERDIAMETERLONG");
                add("INNERDIAMETERLONG_UOM");
                add("INNERDIAMETERSHORT");
                add("INNERDIAMETERSHORT_UOM");
                add("OBJECTCLASS_ID");
                add("OUTERDIAMETERLONG");
                add("OUTERDIAMETERLONG_UOM");
                add("OUTERDIAMETERSHORT");
                add("OUTERDIAMETERSHORT_UOM");
                add("ROTATIONANGLE");
            }});
            put(schemaMapper.getTableName(ADETable.VALUEBYCODES).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("CLASS");
                add("CLASS_CODESPACE");
                add("DOUBLEVALUE");
                add("INTVALUE");
                add("LANDPRICE_2_ATTRIBUTEHIST_ID");
                add("LANDPRICE_2_LANDPRICEHIST_ID");
                add("MEASUREVALUE");
                add("MEASUREVALUE_UOM");
                add("STRINGVALUE");
            }});
            put(schemaMapper.getTableName(ADETable.VEHICLETERMINALATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("TERMINALTYPE");
                add("TERMINALTYPE_CODESPACE");
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
            put(schemaMapper.getTableName(ADETable.WATERBODY_TO_DMATTRIBU).toUpperCase(), new LinkedHashSet<String>() {{
                add("DMATTRIBUTE_ID");
                add("WATERBODY_ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODY_TO_FACILITYA).toUpperCase(), new LinkedHashSet<String>() {{
                add("FACILITYATTRIBUTE_ID");
                add("WATERBODY_ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODY_TO_FLOODINGR).toUpperCase(), new LinkedHashSet<String>() {{
                add("FLOODINGRISKATTRIBUTE_ID");
                add("WATERBODY_ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERBODY_TO_KEYVALUEP).toUpperCase(), new LinkedHashSet<String>() {{
                add("KEYVALUEPAIRATTRIBUTE_ID");
                add("WATERBODY_ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERWAY).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("WATERWAYDETAILATTRIBUTE_ID");
            }});
            put(schemaMapper.getTableName(ADETable.WATERWAYDETAILATTRIBUT).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("LENGTH");
                add("LENGTH_UOM");
                add("MAXIMUMWIDTH");
                add("MAXIMUMWIDTH_UOM");
                add("MINIMUMWIDTH");
                add("MINIMUMWIDTH_UOM");
                add("NAVIGATION");
                add("PLANNEDDEPTH");
                add("PLANNEDDEPTH_UOM");
                add("ROUTEDIRECTION");
                add("ROUTEDIRECTION_CODESPACE");
                add("ROUTEID");
                add("SPEEDLIMIT");
                add("SPEEDLIMIT_UOM");
                add("TARGETSHIPTYPE");
            }});
            put(schemaMapper.getTableName(ADETable.WATERWAY_2).toUpperCase(), new LinkedHashSet<String>() {{
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
            put(schemaMapper.getTableName(ADETable.WEATHEROBSERVATION).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
            }});
            put(schemaMapper.getTableName(ADETable.WEATHEROBSERVATIONTYPE).toUpperCase(), new LinkedHashSet<String>() {{
                add("ID");
                add("AVERAGETEMPERATURE");
                add("AVERAGEWINDSPEED");
                add("DATE_");
                add("MAXRAINFALLPERHOUR");
                add("MAXRAINFALLPERHOUR_UOM");
                add("MAXTEMPERATURE");
                add("MAXWINDDIRECTION");
                add("MAXWINDDIRECTION_CODESPACE");
                add("MINTEMPERATURE");
                add("TOTALRAINFALL");
                add("TOTALRAINFALL_UOM");
            }});
            put(schemaMapper.getTableName(ADETable.WEATHEROB_TO_WEATHEROB).toUpperCase(), new LinkedHashSet<String>() {{
                add("WEATHEROBSERVATION_ID");
                add("WEATHEROBSERVATIONTYPE_ID");
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
                add("URBANPARKATTRIBUTE_ID");
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
