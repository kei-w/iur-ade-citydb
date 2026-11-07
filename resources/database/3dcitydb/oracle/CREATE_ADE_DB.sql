-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2026-11-07 11:28:53 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Sequences *********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

CREATE SEQUENCE ur_areaofannualdivers_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_point_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_numberofhouseholds_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_keyvaluepair_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_trafficareaattribu_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_cityfurnitureattri_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_description_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_publictransitdatat_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_numberofannualdive_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_waterbodyattribute_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_landuseattribute_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_roadattribute_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_landpriceperlandus_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_keyvaluepairattrib_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_buildingattribute_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_legalgrounds_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_vegetationattribut_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_populationbyageand_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_numberofhousehol_seq_1 INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create tables ************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_agency
(
    id NUMBER(38) NOT NULL,
    address VARCHAR2(1000),
    email VARCHAR2(1000),
    fareurl VARCHAR2(1000),
    language VARCHAR2(1000),
    language_codespace VARCHAR2(1000),
    name VARCHAR2(1000),
    officialname VARCHAR2(1000),
    phone VARCHAR2(1000),
    presidentname VARCHAR2(1000),
    presidentposition VARCHAR2(1000),
    timezone VARCHAR2(1000),
    timezone_codespace VARCHAR2(1000),
    url VARCHAR2(1000),
    zipnumber VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_areaofannualdiversions
(
    id NUMBER(38) NOT NULL,
    area NUMBER,
    area_uom VARCHAR2(1000),
    landusediver_areaofannual_id NUMBER(38),
    year DATE,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_attribution
(
    id NUMBER(38) NOT NULL,
    agency_id NUMBER(38),
    email VARCHAR2(1000),
    isauthority NUMBER,
    isoperator NUMBER,
    isproducer NUMBER,
    organizationname VARCHAR2(1000),
    phonenumber VARCHAR2(1000),
    route_id NUMBER(38),
    trip_id NUMBER(38),
    url VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_building
(
    id NUMBER(38) NOT NULL,
    buildingdataqualityattrib_id NUMBER(38),
    buildingdetailattribute_id NUMBER(38),
    buildingidattribute_id NUMBER(38),
    largecustomerfacilityattr_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingattribute
(
    id NUMBER(38) NOT NULL,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingdataqualityatt 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingdataqualityatt
(
    id NUMBER(38) NOT NULL,
    appearancesrcdesc VARCHAR2(1000),
    appearancesrcdesc_codespace VARCHAR2(1000),
    geometrysrcdesc VARCHAR2(1000),
    geometrysrcdesc_codespace VARCHAR2(1000),
    lod1heighttype VARCHAR2(1000),
    lod1heighttype_codespace VARCHAR2(1000),
    lodtype VARCHAR2(1000),
    srcscale VARCHAR2(1000),
    srcscale_codespace VARCHAR2(1000),
    thematicsrcdesc VARCHAR2(1000),
    thematicsrcdesc_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingdetailattribut
(
    id NUMBER(38) NOT NULL,
    areaclassification_codespace VARCHAR2(1000),
    areaclassificationtype VARCHAR2(1000),
    basementfirstusage VARCHAR2(1000),
    basementfirstusage_codespace VARCHAR2(1000),
    basementsecondusag_codespace VARCHAR2(1000),
    basementsecondusage VARCHAR2(1000),
    basementusage VARCHAR2(1000),
    basementusage_codespace VARCHAR2(1000),
    buildingcoveragerate NUMBER,
    buildingfootprintarea NUMBER,
    buildingfootprintarea_uom VARCHAR2(1000),
    buildingheight NUMBER,
    buildingheight_uom VARCHAR2(1000),
    buildingroofedgearea NUMBER,
    buildingroofedgearea_uom VARCHAR2(1000),
    buildingstructureo_codespace VARCHAR2(1000),
    buildingstructureorgtype VARCHAR2(1000),
    buildingstructuret_codespace VARCHAR2(1000),
    buildingstructuretype VARCHAR2(1000),
    detailedusage VARCHAR2(1000),
    detailedusage2 VARCHAR2(1000),
    detailedusage2_codespace VARCHAR2(1000),
    detailedusage3 VARCHAR2(1000),
    detailedusage3_codespace VARCHAR2(1000),
    detailedusage_codespace VARCHAR2(1000),
    developmentarea NUMBER,
    developmentarea_uom VARCHAR2(1000),
    districtsandzonest_codespace VARCHAR2(1000),
    districtsandzonestype VARCHAR2(1000),
    eaveheight NUMBER,
    eaveheight_uom VARCHAR2(1000),
    fireproofstructure_codespace VARCHAR2(1000),
    fireproofstructuretype VARCHAR2(1000),
    floorarearate NUMBER,
    groundfloorusage VARCHAR2(1000),
    groundfloorusage_codespace VARCHAR2(1000),
    implementingbody VARCHAR2(1000),
    landusetype VARCHAR2(1000),
    landusetype_codespace VARCHAR2(1000),
    majorusage VARCHAR2(1000),
    majorusage2 VARCHAR2(1000),
    majorusage2_codespace VARCHAR2(1000),
    majorusage_codespace VARCHAR2(1000),
    note VARCHAR2(1000),
    orgusage VARCHAR2(1000),
    orgusage2 VARCHAR2(1000),
    orgusage2_codespace VARCHAR2(1000),
    orgusage_codespace VARCHAR2(1000),
    reference VARCHAR2(1000),
    secondfloorusage VARCHAR2(1000),
    secondfloorusage_codespace VARCHAR2(1000),
    serialnumberofbuildingcertif VARCHAR2(1000),
    sitearea NUMBER,
    sitearea_uom VARCHAR2(1000),
    specifiedbuildingcoveragerat NUMBER,
    specifiedfloorarearate NUMBER,
    standardfloorarearate NUMBER,
    surveyyear DATE,
    thirdfloorusage VARCHAR2(1000),
    thirdfloorusage_codespace VARCHAR2(1000),
    totalfloorarea NUMBER,
    totalfloorarea_uom VARCHAR2(1000),
    urbanplantype VARCHAR2(1000),
    urbanplantype_codespace VARCHAR2(1000),
    vacancy VARCHAR2(1000),
    vacancy_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingdisasterriskat
(
    id NUMBER(38) NOT NULL,
    areatype VARCHAR2(1000),
    areatype_codespace VARCHAR2(1000),
    building_buildingdisaster_id NUMBER(38),
    description VARCHAR2(1000),
    description_codespace VARCHAR2(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingfloodingriskat
(
    id NUMBER(38) NOT NULL,
    depth NUMBER,
    depth_uom VARCHAR2(1000),
    objectclass_id INTEGER,
    rank VARCHAR2(1000),
    rank_codespace VARCHAR2(1000),
    rankorg VARCHAR2(1000),
    rankorg_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingidattribute
(
    id NUMBER(38) NOT NULL,
    branchid INTEGER,
    buildingid VARCHAR2(1000),
    city VARCHAR2(1000),
    city_codespace VARCHAR2(1000),
    partid INTEGER,
    prefecture VARCHAR2(1000),
    prefecture_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingriverfloodingr 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingriverfloodingr
(
    id NUMBER(38) NOT NULL,
    admintype VARCHAR2(1000),
    admintype_codespace VARCHAR2(1000),
    duration NUMBER,
    duration_uom VARCHAR2(1000),
    scale VARCHAR2(1000),
    scale_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_calendar
(
    id NUMBER(38) NOT NULL,
    enddate TIMESTAMP,
    friday NUMBER,
    monday NUMBER,
    saturday NUMBER,
    startdate TIMESTAMP,
    sunday NUMBER,
    thursday NUMBER,
    tuesday NUMBER,
    wednesday NUMBER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_calendardate
(
    id NUMBER(38) NOT NULL,
    calendar_id NUMBER(38),
    date_ TIMESTAMP,
    exceptiontype VARCHAR2(1000),
    exceptiontype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_censusblock
(
    id NUMBER(38) NOT NULL,
    daytimepopulation INTEGER,
    daytimepopulationdensity NUMBER,
    numberofmainhouseholds INTEGER,
    numberofordinaryhouseholds INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_city_furniture
(
    id NUMBER(38) NOT NULL,
    cityfurnituredataqualitya_id NUMBER(38),
    cityfurnituredetailattrib_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityfurnitureattribute
(
    id NUMBER(38) NOT NULL,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredataquali 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityfurnituredataquali
(
    id NUMBER(38) NOT NULL,
    appearancesrcdesc VARCHAR2(1000),
    appearancesrcdesc_codespace VARCHAR2(1000),
    geometrysrcdesc VARCHAR2(1000),
    geometrysrcdesc_codespace VARCHAR2(1000),
    lodtype VARCHAR2(1000),
    srcscale VARCHAR2(1000),
    srcscale_codespace VARCHAR2(1000),
    thematicsrcdesc VARCHAR2(1000),
    thematicsrcdesc_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredetailatt 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityfurnituredetailatt
(
    id NUMBER(38) NOT NULL,
    description VARCHAR2(1000),
    facilitytype VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobject
(
    id NUMBER(38) NOT NULL,
    vegetationdataqualityattr_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup
(
    id NUMBER(38) NOT NULL,
    fiscalyearofpublication DATE,
    language VARCHAR2(1000),
    language_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_1 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup_1
(
    id NUMBER(38) NOT NULL,
    fiscalyearofpublication DATE,
    language VARCHAR2(1000),
    language_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup_2
(
    id NUMBER(38) NOT NULL,
    fiscalyearofpublication DATE,
    language VARCHAR2(1000),
    language_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_3 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup_3
(
    id NUMBER(38) NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_description 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_description
(
    id NUMBER(38) NOT NULL,
    description VARCHAR2(1000),
    frequencyofservice INTEGER,
    numberofcustomers INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_developmentproject
(
    id NUMBER(38) NOT NULL,
    benefitarea NUMBER,
    benefitarea_uom VARCHAR2(1000),
    benefitperiod VARCHAR2(1000),
    completedarea NUMBER,
    completedarea_uom VARCHAR2(1000),
    cost INTEGER,
    dateofdecision TIMESTAMP,
    dateofdesignationfortemporar TIMESTAMP,
    mainpurpose VARCHAR2(1000),
    mainpurpose_codespace VARCHAR2(1000),
    ongoingarea NUMBER,
    ongoingarea_uom VARCHAR2(1000),
    plannedarea NUMBER,
    plannedarea_uom VARCHAR2(1000),
    status VARCHAR2(1000),
    status_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterdamage
(
    id NUMBER(38) NOT NULL,
    damagedarea NUMBER,
    damagedarea_uom VARCHAR2(1000),
    maximumrainfallperhour INTEGER,
    numberofdamagedhouses INTEGER,
    numberofhousesfloodedabovefl INTEGER,
    numberofhousesfloodedbelowfl INTEGER,
    totalrainfall INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_fareattribute
(
    id NUMBER(38) NOT NULL,
    agency_id NUMBER(38),
    currencytype VARCHAR2(1000),
    currencytype_codespace VARCHAR2(1000),
    paymentmethod VARCHAR2(1000),
    paymentmethod_codespace VARCHAR2(1000),
    price NUMBER,
    transferduration INTEGER,
    transfers VARCHAR2(1000),
    transfers_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_farerule
(
    id NUMBER(38) NOT NULL,
    containsid VARCHAR2(1000),
    containsid_codespace VARCHAR2(1000),
    destinationid VARCHAR2(1000),
    destinationid_codespace VARCHAR2(1000),
    fare_id NUMBER(38),
    originid VARCHAR2(1000),
    originid_codespace VARCHAR2(1000),
    route_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_feedinfo 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_feedinfo
(
    id NUMBER(38) NOT NULL,
    contactemail VARCHAR2(1000),
    contacturl VARCHAR2(1000),
    defaultlanguage VARCHAR2(1000),
    defaultlanguage_codespace VARCHAR2(1000),
    detailedinfo VARCHAR2(1000),
    enddate TIMESTAMP,
    language VARCHAR2(1000),
    language_codespace VARCHAR2(1000),
    publishername VARCHAR2(1000),
    publisherurl VARCHAR2(1000),
    startdate TIMESTAMP,
    version VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_frequency
(
    id NUMBER(38) NOT NULL,
    endtime TIMESTAMP,
    exacttimes VARCHAR2(1000),
    exacttimes_codespace VARCHAR2(1000),
    headwaysecs INTEGER,
    starttime TIMESTAMP,
    trip_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_households
(
    id NUMBER(38) NOT NULL,
    numberofmainhousehold INTEGER,
    numberofordinaryhousehold INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_keyvaluepair
(
    id NUMBER(38) NOT NULL,
    codevalue VARCHAR2(1000),
    codevalue_codespace VARCHAR2(1000),
    datevalue TIMESTAMP,
    doublevalue NUMBER,
    intvalue INTEGER,
    key VARCHAR2(1000),
    key_codespace VARCHAR2(1000),
    measuredvalue NUMBER,
    measuredvalue_uom VARCHAR2(1000),
    statisticalg_genericvalue_id NUMBER(38),
    stringvalue VARCHAR2(1000),
    urivalue VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_keyvaluepairattribute
(
    id NUMBER(38) NOT NULL,
    building_keyvaluepairattr_id NUMBER(38),
    codevalue VARCHAR2(1000),
    codevalue_codespace VARCHAR2(1000),
    datevalue TIMESTAMP,
    doublevalue NUMBER,
    intvalue INTEGER,
    key VARCHAR2(1000),
    key_codespace VARCHAR2(1000),
    measuredvalue NUMBER,
    measuredvalue_uom VARCHAR2(1000),
    stringvalue VARCHAR2(1000),
    urivalue VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_land_use
(
    id NUMBER(38) NOT NULL,
    landusedetailattribute_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landprice
(
    id NUMBER(38) NOT NULL,
    currencyunit VARCHAR2(1000),
    currencyunit_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landpriceperlanduse
(
    id NUMBER(38) NOT NULL,
    landprice INTEGER,
    landprice_landprice_id NUMBER(38),
    landuse VARCHAR2(1000),
    landuse_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landuseattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landuseattribute
(
    id NUMBER(38) NOT NULL,
    areaclassification_codespace VARCHAR2(1000),
    areaclassificationtype VARCHAR2(1000),
    areainha NUMBER,
    areainha_uom VARCHAR2(1000),
    areainsquaremeter NUMBER,
    areainsquaremeter_uom VARCHAR2(1000),
    buildingcoveragerate NUMBER,
    city VARCHAR2(1000),
    city_codespace VARCHAR2(1000),
    districtsandzonest_codespace VARCHAR2(1000),
    districtsandzonestype VARCHAR2(1000),
    floorarearate NUMBER,
    nominalarea NUMBER,
    nominalarea_uom VARCHAR2(1000),
    note VARCHAR2(1000),
    orglanduse VARCHAR2(1000),
    orglanduse_codespace VARCHAR2(1000),
    owner VARCHAR2(1000),
    ownertype VARCHAR2(1000),
    ownertype_codespace VARCHAR2(1000),
    prefecture VARCHAR2(1000),
    prefecture_codespace VARCHAR2(1000),
    reference VARCHAR2(1000),
    specifiedbuildingcoveragerat NUMBER,
    specifiedfloorarearate NUMBER,
    standardfloorarearate NUMBER,
    surveyyear DATE,
    urbanplantype VARCHAR2(1000),
    urbanplantype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landusediversion
(
    id NUMBER(38) NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_largecustomerfacilitya
(
    id NUMBER(38) NOT NULL,
    areaclassification_codespace VARCHAR2(1000),
    areaclassificationtype VARCHAR2(1000),
    availability NUMBER,
    capacity INTEGER,
    class VARCHAR2(1000),
    class_codespace VARCHAR2(1000),
    districtsandzonest_codespace VARCHAR2(1000),
    districtsandzonestype VARCHAR2(1000),
    inauguraldate TIMESTAMP,
    keytenants VARCHAR2(1000),
    landusetype VARCHAR2(1000),
    landusetype_codespace VARCHAR2(1000),
    name VARCHAR2(1000),
    note VARCHAR2(1000),
    owner VARCHAR2(1000),
    reference VARCHAR2(1000),
    surveyyear DATE,
    totalfloorarea NUMBER,
    totalfloorarea_uom VARCHAR2(1000),
    totalstorefloorarea NUMBER,
    totalstorefloorarea_uom VARCHAR2(1000),
    urbanplantype VARCHAR2(1000),
    urbanplantype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_legalgrounds 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_legalgrounds
(
    id NUMBER(38) NOT NULL,
    articlesofregulati_codespace VARCHAR2(1000),
    articlesofregulation VARCHAR2(1000),
    date_ TIMESTAMP,
    nameofregulation VARCHAR2(1000),
    nameofregulation_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofannualdiversio
(
    id NUMBER(38) NOT NULL,
    count INTEGER,
    landusediver_numberofannu_id NUMBER(38),
    year DATE,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofhouseholds
(
    id NUMBER(38) NOT NULL,
    class VARCHAR2(1000),
    class_codespace VARCHAR2(1000),
    households_numberofhous_id_1 NUMBER(38),
    households_numberofhouseh_id NUMBER(38),
    number_ INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofhouseholds_1
(
    id NUMBER(38) NOT NULL,
    censusblock_numberofhou_id_1 NUMBER(38),
    censusblock_numberofhouse_id NUMBER(38),
    class VARCHAR2(1000),
    class_codespace VARCHAR2(1000),
    number_ INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_office
(
    id NUMBER(38) NOT NULL,
    name VARCHAR2(1000),
    phone VARCHAR2(1000),
    url VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_officesandemployees
(
    id NUMBER(38) NOT NULL,
    numberofemployees INTEGER,
    numberofoffices INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_pathway
(
    id NUMBER(38) NOT NULL,
    from_id NUMBER(38),
    isbidirectional VARCHAR2(1000),
    isbidirectional_codespace VARCHAR2(1000),
    length NUMBER,
    length_uom VARCHAR2(1000),
    maxslope NUMBER,
    minwidth NUMBER,
    mode_ VARCHAR2(1000),
    mode_codespace VARCHAR2(1000),
    reversedsignpostedas VARCHAR2(1000),
    signpostedas VARCHAR2(1000),
    staircount INTEGER,
    to_id NUMBER(38),
    traversaltime INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_point
(
    id NUMBER(38) NOT NULL,
    latitude NUMBER,
    longitude NUMBER,
    point MDSYS.SDO_GEOMETRY,
    pointdistancetraveled NUMBER,
    pointsequence INTEGER,
    publictransit_point_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_pollution
(
    id NUMBER(38) NOT NULL,
    cause VARCHAR2(1000),
    damagedarea NUMBER,
    damagedarea_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_population
(
    id NUMBER(38) NOT NULL,
    births INTEGER,
    daytimepopulation INTEGER,
    daytimepopulationdensity NUMBER,
    deaths INTEGER,
    femalepopulation INTEGER,
    increasement INTEGER,
    malepopulation INTEGER,
    movefrom INTEGER,
    moveto INTEGER,
    naturalincrease INTEGER,
    socialincrease INTEGER,
    total INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_populationbyageandsex
(
    id NUMBER(38) NOT NULL,
    ageandsex VARCHAR2(1000),
    ageandsex_codespace VARCHAR2(1000),
    number_ INTEGER,
    population_populationbyag_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransit
(
    id NUMBER(38) NOT NULL,
    index_ NUMBER,
    objectclass_id INTEGER,
    orgid VARCHAR2(1000),
    target_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransitdatatype
(
    id NUMBER(38) NOT NULL,
    cityobjectgroup_datatype_id NUMBER(38),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransportationfa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransportationfa
(
    id NUMBER(38) NOT NULL,
    companyname VARCHAR2(1000),
    frequencyofservice INTEGER,
    numberofcustomers NUMBER,
    routename VARCHAR2(1000),
    sectionname VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_recreations
(
    id NUMBER(38) NOT NULL,
    capacity INTEGER,
    numberofusers INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadattribute
(
    id NUMBER(38) NOT NULL,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roaddataqualityattribu 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roaddataqualityattribu
(
    id NUMBER(38) NOT NULL,
    appearancesrcdesc VARCHAR2(1000),
    appearancesrcdesc_codespace VARCHAR2(1000),
    geometrysrcdesc VARCHAR2(1000),
    geometrysrcdesc_codespace VARCHAR2(1000),
    lodtype VARCHAR2(1000),
    srcscale VARCHAR2(1000),
    srcscale_codespace VARCHAR2(1000),
    thematicsrcdesc VARCHAR2(1000),
    thematicsrcdesc_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadstructureattribute
(
    id NUMBER(38) NOT NULL,
    numberoflanes INTEGER,
    sectiontype VARCHAR2(1000),
    sectiontype_codespace VARCHAR2(1000),
    width NUMBER,
    width_uom VARCHAR2(1000),
    widthtype VARCHAR2(1000),
    widthtype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_route
(
    id NUMBER(38) NOT NULL,
    agency_id NUMBER(38),
    color VARCHAR2(1000),
    continuousdropoff VARCHAR2(1000),
    continuousdropoff_codespace VARCHAR2(1000),
    continuouspickup VARCHAR2(1000),
    continuouspickup_codespace VARCHAR2(1000),
    description_id NUMBER(38),
    destinationstop VARCHAR2(1000),
    lod0multicurve MDSYS.SDO_GEOMETRY,
    longname VARCHAR2(1000),
    originstop VARCHAR2(1000),
    parentroute_id NUMBER(38),
    routesortorder INTEGER,
    shortname VARCHAR2(1000),
    textcolor VARCHAR2(1000),
    type VARCHAR2(1000),
    type_codespace VARCHAR2(1000),
    updatedate TIMESTAMP,
    url VARCHAR2(1000),
    viastop VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_statisticalgrid
(
    id NUMBER(38) NOT NULL,
    areaclassification_codespace VARCHAR2(1000),
    areaclassificationtype VARCHAR2(1000),
    availability NUMBER,
    city VARCHAR2(1000),
    city_codespace VARCHAR2(1000),
    class VARCHAR2(1000),
    class_codespace VARCHAR2(1000),
    lod_1multisurface_id NUMBER(38),
    lod_2multisurface_id NUMBER(38),
    objectclass_id INTEGER,
    prefecture VARCHAR2(1000),
    prefecture_codespace VARCHAR2(1000),
    surveyyear DATE,
    urbanplantype VARCHAR2(1000),
    urbanplantype_codespace VARCHAR2(1000),
    value CLOB,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_stop
(
    id NUMBER(38) NOT NULL,
    code VARCHAR2(1000),
    code_codespace VARCHAR2(1000),
    latitude NUMBER,
    level_id NUMBER(38),
    locationtype VARCHAR2(1000),
    locationtype_codespace VARCHAR2(1000),
    longitude NUMBER,
    parentstation_id NUMBER(38),
    platformcode VARCHAR2(1000),
    point MDSYS.SDO_GEOMETRY,
    timezone VARCHAR2(1000),
    timezone_codespace VARCHAR2(1000),
    ttsname VARCHAR2(1000),
    url VARCHAR2(1000),
    wheelchairboarding VARCHAR2(1000),
    wheelchairboarding_codespace VARCHAR2(1000),
    zoneid VARCHAR2(1000),
    zoneid_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_stoptime
(
    id NUMBER(38) NOT NULL,
    arrivaltime TIMESTAMP,
    continuousdropofft_codespace VARCHAR2(1000),
    continuousdropofftype VARCHAR2(1000),
    continuouspickupty_codespace VARCHAR2(1000),
    continuouspickuptype VARCHAR2(1000),
    departuretime TIMESTAMP,
    dropofftype VARCHAR2(1000),
    dropofftype_codespace VARCHAR2(1000),
    headsign VARCHAR2(1000),
    pickuptype VARCHAR2(1000),
    pickuptype_codespace VARCHAR2(1000),
    shapedistancetraveled NUMBER,
    stop_id NUMBER(38),
    stopsequence INTEGER,
    timepoint VARCHAR2(1000),
    timepoint_codespace VARCHAR2(1000),
    trip_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_traffic_area
(
    id NUMBER(38) NOT NULL,
    trafficareastructureattri_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trafficareaattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trafficareaattribute
(
    id NUMBER(38) NOT NULL,
    numberoflanes INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trafficvolumeattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trafficvolumeattribute
(
    id NUMBER(38) NOT NULL,
    averageinboundtravelspeedinc NUMBER,
    averageoutboundtravelspeedin NUMBER,
    averagetravelspeedincongesti NUMBER,
    congestionrate NUMBER,
    largevehiclerate NUMBER,
    observationpointname VARCHAR2(1000),
    reference VARCHAR2(1000),
    sectionid VARCHAR2(1000),
    surveyyear DATE,
    weekday12hourtrafficvolume INTEGER,
    weekday24hourtrafficvolume INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_transfer
(
    id NUMBER(38) NOT NULL,
    from_id NUMBER(38),
    mintransfertime INTEGER,
    to_id NUMBER(38),
    transfertype VARCHAR2(1000),
    transfertype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_translation
(
    id NUMBER(38) NOT NULL,
    fieldname VARCHAR2(1000),
    fieldvalue VARCHAR2(1000),
    language VARCHAR2(1000),
    language_codespace VARCHAR2(1000),
    recordid_id NUMBER(38),
    recordsubid VARCHAR2(1000),
    tablename VARCHAR2(1000),
    tablename_codespace VARCHAR2(1000),
    translation VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_translationjp
(
    id NUMBER(38) NOT NULL,
    language VARCHAR2(1000),
    language_codespace VARCHAR2(1000),
    translation VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_transportation_complex
(
    id NUMBER(38) NOT NULL,
    roaddataqualityattribute_id NUMBER(38),
    roadstructureattribute_id NUMBER(38),
    trafficvolumeattribute_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trip
(
    id NUMBER(38) NOT NULL,
    bikeallowed VARCHAR2(1000),
    bikeallowed_codespace VARCHAR2(1000),
    blockid VARCHAR2(1000),
    calendar_id NUMBER(38),
    calendardate_id NUMBER(38),
    directionid VARCHAR2(1000),
    directionid_codespace VARCHAR2(1000),
    headsign VARCHAR2(1000),
    lod0multicurve MDSYS.SDO_GEOMETRY,
    office_id NUMBER(38),
    route_id NUMBER(38),
    shape_id NUMBER(38),
    shortname VARCHAR2(1000),
    symbol VARCHAR2(1000),
    wheelchairaccessib_codespace VARCHAR2(1000),
    wheelchairaccessible VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfunc_to_cityobjec
(
    cityobject_id NUMBER(38) NOT NULL,
    urbanfunction_id NUMBER(38) NOT NULL,
    PRIMARY KEY (cityobject_id, urbanfunction_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfunction
(
    id NUMBER(38) NOT NULL,
    abstract VARCHAR2(1000),
    areaclassification_codespace VARCHAR2(1000),
    areaclassificationtype VARCHAR2(1000),
    capacity INTEGER,
    city VARCHAR2(1000),
    city_codespace VARCHAR2(1000),
    class VARCHAR2(1000),
    class_codespace VARCHAR2(1000),
    custodian VARCHAR2(1000),
    enactmentdate TIMESTAMP,
    enactmentfiscalyear DATE,
    expirationdate TIMESTAMP,
    expirationfiscalyear DATE,
    function VARCHAR2(1000),
    function_codespace VARCHAR2(1000),
    legalgrounds_id NUMBER(38),
    lod0multicurve MDSYS.SDO_GEOMETRY,
    lod0multipoint MDSYS.SDO_GEOMETRY,
    lod0multisurface_id NUMBER(38),
    lod_1multicurve MDSYS.SDO_GEOMETRY,
    lod_1multipoint MDSYS.SDO_GEOMETRY,
    lod_1multisurface_id NUMBER(38),
    lod_2multicurve MDSYS.SDO_GEOMETRY,
    lod_2multipoint MDSYS.SDO_GEOMETRY,
    lod_2multisurface_id NUMBER(38),
    nominalarea NUMBER,
    nominalarea_uom VARCHAR2(1000),
    note VARCHAR2(1000),
    objectclass_id INTEGER,
    prefecture VARCHAR2(1000),
    prefecture_codespace VARCHAR2(1000),
    reference VARCHAR2(1000),
    surveyyear DATE,
    urbanplantype VARCHAR2(1000),
    urbanplantype_codespace VARCHAR2(1000),
    validity NUMBER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanization
(
    id NUMBER(38) NOT NULL,
    period VARCHAR2(1000),
    resources VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_vegetationattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_vegetationattribute
(
    id NUMBER(38) NOT NULL,
    appearancesrcdesc VARCHAR2(1000),
    appearancesrcdesc_codespace VARCHAR2(1000),
    geometrysrcdesc VARCHAR2(1000),
    geometrysrcdesc_codespace VARCHAR2(1000),
    srcscale VARCHAR2(1000),
    srcscale_codespace VARCHAR2(1000),
    thematicsrcdesc VARCHAR2(1000),
    thematicsrcdesc_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbody
(
    id NUMBER(38) NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbodyattribute
(
    id NUMBER(38) NOT NULL,
    depth NUMBER,
    depth_uom VARCHAR2(1000),
    description VARCHAR2(1000),
    description_codespace VARCHAR2(1000),
    objectclass_id INTEGER,
    rank VARCHAR2(1000),
    rank_codespace VARCHAR2(1000),
    rankorg VARCHAR2(1000),
    rankorg_codespace VARCHAR2(1000),
    waterbody_floodingriskatt_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbodyriverflooding
(
    id NUMBER(38) NOT NULL,
    admintype VARCHAR2(1000),
    admintype_codespace VARCHAR2(1000),
    duration NUMBER,
    duration_uom VARCHAR2(1000),
    scale VARCHAR2(1000),
    scale_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zone
(
    id NUMBER(38) NOT NULL,
    areaapplied VARCHAR2(1000),
    finalpublicationdate TIMESTAMP,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create foreign keys ******************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_agency ADD CONSTRAINT ur_agency_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_areaofannualdiversions ADD CONSTRAINT ur_areaof_landus_areaof_fk FOREIGN KEY (landusediver_areaofannual_id)
REFERENCES ur_landusediversion (id);

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_attribution ADD CONSTRAINT ur_attribution_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

ALTER TABLE ur_attribution ADD CONSTRAINT ur_attribution_route_fk FOREIGN KEY (route_id)
REFERENCES ur_route (id)
ON DELETE SET NULL;

ALTER TABLE ur_attribution ADD CONSTRAINT ur_attribution_agency_fk FOREIGN KEY (agency_id)
REFERENCES ur_agency (id)
ON DELETE SET NULL;

ALTER TABLE ur_attribution ADD CONSTRAINT ur_attribution_trip_fk FOREIGN KEY (trip_id)
REFERENCES ur_trip (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building ADD CONSTRAINT ur_building_fk FOREIGN KEY (id)
REFERENCES building (id);

ALTER TABLE ur_building ADD CONSTRAINT ur_building_buildingida_fk FOREIGN KEY (buildingidattribute_id)
REFERENCES ur_buildingidattribute (id)
ON DELETE SET NULL;

ALTER TABLE ur_building ADD CONSTRAINT ur_building_buildingdet_fk FOREIGN KEY (buildingdetailattribute_id)
REFERENCES ur_buildingdetailattribut (id)
ON DELETE SET NULL;

ALTER TABLE ur_building ADD CONSTRAINT ur_building_largecustom_fk FOREIGN KEY (largecustomerfacilityattr_id)
REFERENCES ur_largecustomerfacilitya (id)
ON DELETE SET NULL;

ALTER TABLE ur_building ADD CONSTRAINT ur_building_buildingdat_fk FOREIGN KEY (buildingdataqualityattrib_id)
REFERENCES ur_buildingdataqualityatt (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingattribute ADD CONSTRAINT ur_buildingat_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

-- -------------------------------------------------------------------- 
-- ur_buildingdataqualityatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdataqualityatt ADD CONSTRAINT ur_buildingdataqualitya_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id);

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdetailattribut ADD CONSTRAINT ur_buildingdetailattrib_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id);

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdisasterriskat ADD CONSTRAINT ur_buildingdi_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_buildingdisasterriskat ADD CONSTRAINT ur_buildingdisasterrisk_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id);

ALTER TABLE ur_buildingdisasterriskat ADD CONSTRAINT ur_buildi_buildi_buildi_fk FOREIGN KEY (building_buildingdisaster_id)
REFERENCES ur_building (id);

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingfloodingriskat ADD CONSTRAINT ur_buildingfl_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_buildingfloodingriskat ADD CONSTRAINT ur_buildingfloodingrisk_fk FOREIGN KEY (id)
REFERENCES ur_buildingdisasterriskat (id);

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingidattribute ADD CONSTRAINT ur_buildingidattribute_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id);

-- -------------------------------------------------------------------- 
-- ur_buildingriverfloodingr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingriverfloodingr ADD CONSTRAINT ur_buildingriverfloodin_fk FOREIGN KEY (id)
REFERENCES ur_buildingfloodingriskat (id);

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendar ADD CONSTRAINT ur_calendar_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendardate ADD CONSTRAINT ur_calendardate_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

ALTER TABLE ur_calendardate ADD CONSTRAINT ur_calendardat_calendar_fk FOREIGN KEY (calendar_id)
REFERENCES ur_calendar (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_censusblock ADD CONSTRAINT ur_censusblock_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id);

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_city_furniture ADD CONSTRAINT ur_city_furniture_fk FOREIGN KEY (id)
REFERENCES city_furniture (id);

ALTER TABLE ur_city_furniture ADD CONSTRAINT ur_city_furnitu_cityfur_fk FOREIGN KEY (cityfurnituredetailattrib_id)
REFERENCES ur_cityfurnituredetailatt (id)
ON DELETE SET NULL;

ALTER TABLE ur_city_furniture ADD CONSTRAINT ur_city_furnit_cityfu_fk_1 FOREIGN KEY (cityfurnituredataqualitya_id)
REFERENCES ur_cityfurnituredataquali (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityfurnitureattribute ADD CONSTRAINT ur_cityfurnit_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredataquali 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityfurnituredataquali ADD CONSTRAINT ur_cityfurnituredataqua_fk FOREIGN KEY (id)
REFERENCES ur_cityfurnitureattribute (id);

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredetailatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityfurnituredetailatt ADD CONSTRAINT ur_cityfurnituredetaila_fk FOREIGN KEY (id)
REFERENCES ur_cityfurnitureattribute (id);

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobject ADD CONSTRAINT ur_cityobject_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

ALTER TABLE ur_cityobject ADD CONSTRAINT ur_cityobject_vegetatio_fk FOREIGN KEY (vegetationdataqualityattr_id)
REFERENCES ur_vegetationattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup ADD CONSTRAINT ur_cityobjectgroup_fk FOREIGN KEY (id)
REFERENCES cityobjectgroup (id);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_1 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_1 ADD CONSTRAINT ur_cityobjectgroup_fk_1 FOREIGN KEY (id)
REFERENCES cityobjectgroup (id);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_2 ADD CONSTRAINT ur_cityobjectgroup_fk_2 FOREIGN KEY (id)
REFERENCES cityobjectgroup (id);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_3 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_3 ADD CONSTRAINT ur_cityobjectgroup_fk_3 FOREIGN KEY (id)
REFERENCES cityobjectgroup (id);

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_developmentproject ADD CONSTRAINT ur_developmentproject_fk FOREIGN KEY (id)
REFERENCES ur_zone (id);

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterdamage ADD CONSTRAINT ur_disasterdamage_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id);

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fareattribute ADD CONSTRAINT ur_fareattribute_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

ALTER TABLE ur_fareattribute ADD CONSTRAINT ur_fareattribute_agency_fk FOREIGN KEY (agency_id)
REFERENCES ur_agency (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_farerule ADD CONSTRAINT ur_farerule_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id);

ALTER TABLE ur_farerule ADD CONSTRAINT ur_farerule_route_fk FOREIGN KEY (route_id)
REFERENCES ur_route (id)
ON DELETE SET NULL;

ALTER TABLE ur_farerule ADD CONSTRAINT ur_farerule_fare_fk FOREIGN KEY (fare_id)
REFERENCES ur_fareattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_feedinfo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_feedinfo ADD CONSTRAINT ur_feedinfo_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id);

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_frequency ADD CONSTRAINT ur_frequency_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id);

ALTER TABLE ur_frequency ADD CONSTRAINT ur_frequency_trip_fk FOREIGN KEY (trip_id)
REFERENCES ur_trip (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_households ADD CONSTRAINT ur_households_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_keyvaluepair ADD CONSTRAINT ur_keyval_statis_generi_fk FOREIGN KEY (statisticalg_genericvalue_id)
REFERENCES ur_statisticalgrid (id);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_keyvaluepairattribute ADD CONSTRAINT ur_keyval_buildi_keyval_fk FOREIGN KEY (building_keyvaluepairattr_id)
REFERENCES ur_building (id);

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use ADD CONSTRAINT ur_land_use_fk FOREIGN KEY (id)
REFERENCES land_use (id);

ALTER TABLE ur_land_use ADD CONSTRAINT ur_land_use_landusedeta_fk FOREIGN KEY (landusedetailattribute_id)
REFERENCES ur_landuseattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice ADD CONSTRAINT ur_landprice_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id);

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landpriceperlanduse ADD CONSTRAINT ur_landpr_landpr_landpr_fk FOREIGN KEY (landprice_landprice_id)
REFERENCES ur_landprice (id);

-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusediversion ADD CONSTRAINT ur_landusediversion_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id);

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_largecustomerfacilitya ADD CONSTRAINT ur_largecustomerfacilit_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id);

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofannualdiversio ADD CONSTRAINT ur_number_landus_number_fk FOREIGN KEY (landusediver_numberofannu_id)
REFERENCES ur_landusediversion (id);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds ADD CONSTRAINT ur_number_househ_number_fk FOREIGN KEY (households_numberofhouseh_id)
REFERENCES ur_households (id);

ALTER TABLE ur_numberofhouseholds ADD CONSTRAINT ur_number_house_numbe_fk_1 FOREIGN KEY (households_numberofhous_id_1)
REFERENCES ur_households (id);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds_1 ADD CONSTRAINT ur_number_census_number_fk FOREIGN KEY (censusblock_numberofhouse_id)
REFERENCES ur_censusblock (id);

ALTER TABLE ur_numberofhouseholds_1 ADD CONSTRAINT ur_number_censu_numbe_fk_1 FOREIGN KEY (censusblock_numberofhou_id_1)
REFERENCES ur_censusblock (id);

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_office ADD CONSTRAINT ur_office_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_officesandemployees ADD CONSTRAINT ur_officesandemployees_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id);

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pathway ADD CONSTRAINT ur_pathway_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

ALTER TABLE ur_pathway ADD CONSTRAINT ur_pathway_from_fk FOREIGN KEY (from_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

ALTER TABLE ur_pathway ADD CONSTRAINT ur_pathway_to_fk FOREIGN KEY (to_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_point ADD CONSTRAINT ur_point_publictr_point_fk FOREIGN KEY (publictransit_point_id)
REFERENCES ur_publictransit (id);

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pollution ADD CONSTRAINT ur_pollution_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id);

-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_population ADD CONSTRAINT ur_population_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id);

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_populationbyageandsex ADD CONSTRAINT ur_popula_popula_popula_fk FOREIGN KEY (population_populationbyag_id)
REFERENCES ur_population (id);

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransit ADD CONSTRAINT ur_publictran_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_publictransit ADD CONSTRAINT ur_publictransit_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

ALTER TABLE ur_publictransit ADD CONSTRAINT ur_publictransit_target_fk FOREIGN KEY (target_id)
REFERENCES cityobject (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitdatatype ADD CONSTRAINT ur_publictra_objectcl_fk_1 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_publictransitdatatype ADD CONSTRAINT ur_public_cityob_dataty_fk FOREIGN KEY (cityobjectgroup_datatype_id)
REFERENCES ur_cityobjectgroup_3 (id);

-- -------------------------------------------------------------------- 
-- ur_publictransportationfa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransportationfa ADD CONSTRAINT ur_publictransportation_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id);

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_recreations ADD CONSTRAINT ur_recreations_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id);

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadattribute ADD CONSTRAINT ur_roadattrib_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

-- -------------------------------------------------------------------- 
-- ur_roaddataqualityattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roaddataqualityattribu ADD CONSTRAINT ur_roaddataqualityattri_fk FOREIGN KEY (id)
REFERENCES ur_roadattribute (id);

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadstructureattribute ADD CONSTRAINT ur_roadstructureattribu_fk FOREIGN KEY (id)
REFERENCES ur_roadattribute (id);

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_route ADD CONSTRAINT ur_route_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

ALTER TABLE ur_route ADD CONSTRAINT ur_route_parentroute_fk FOREIGN KEY (parentroute_id)
REFERENCES ur_route (id)
ON DELETE SET NULL;

ALTER TABLE ur_route ADD CONSTRAINT ur_route_description_fk FOREIGN KEY (description_id)
REFERENCES ur_description (id)
ON DELETE SET NULL;

ALTER TABLE ur_route ADD CONSTRAINT ur_route_agency_fk FOREIGN KEY (agency_id)
REFERENCES ur_agency (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_statisticalgrid ADD CONSTRAINT ur_statisticalgrid_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

ALTER TABLE ur_statisticalgrid ADD CONSTRAINT ur_statistica_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_statisticalgrid ADD CONSTRAINT ur_statisti_lod_1multis_fk FOREIGN KEY (lod_1multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_statisticalgrid ADD CONSTRAINT ur_statisti_lod_2multis_fk FOREIGN KEY (lod_2multisurface_id)
REFERENCES surface_geometry (id);

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stop ADD CONSTRAINT ur_stop_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

ALTER TABLE ur_stop ADD CONSTRAINT ur_stop_parentstation_fk FOREIGN KEY (parentstation_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

ALTER TABLE ur_stop ADD CONSTRAINT ur_stop_level_fk FOREIGN KEY (level_id)
REFERENCES ur_publictransit (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stoptime ADD CONSTRAINT ur_stoptime_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id);

ALTER TABLE ur_stoptime ADD CONSTRAINT ur_stoptime_stop_fk FOREIGN KEY (stop_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

ALTER TABLE ur_stoptime ADD CONSTRAINT ur_stoptime_trip_fk FOREIGN KEY (trip_id)
REFERENCES ur_trip (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_traffic_area ADD CONSTRAINT ur_traffic_area_fk FOREIGN KEY (id)
REFERENCES traffic_area (id);

ALTER TABLE ur_traffic_area ADD CONSTRAINT ur_traffic_area_traffic_fk FOREIGN KEY (trafficareastructureattri_id)
REFERENCES ur_trafficareaattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_trafficvolumeattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trafficvolumeattribute ADD CONSTRAINT ur_trafficvolumeattribu_fk FOREIGN KEY (id)
REFERENCES ur_roadattribute (id);

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transfer ADD CONSTRAINT ur_transfer_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id);

ALTER TABLE ur_transfer ADD CONSTRAINT ur_transfer_from_fk FOREIGN KEY (from_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

ALTER TABLE ur_transfer ADD CONSTRAINT ur_transfer_to_fk FOREIGN KEY (to_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_translation ADD CONSTRAINT ur_translation_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id);

ALTER TABLE ur_translation ADD CONSTRAINT ur_translation_recordid_fk FOREIGN KEY (recordid_id)
REFERENCES ur_publictransit (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_translationjp ADD CONSTRAINT ur_translationjp_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transportation_complex ADD CONSTRAINT ur_transportati_complex_fk FOREIGN KEY (id)
REFERENCES transportation_complex (id);

ALTER TABLE ur_transportation_complex ADD CONSTRAINT ur_transp_comple_traffi_fk FOREIGN KEY (trafficvolumeattribute_id)
REFERENCES ur_trafficvolumeattribute (id)
ON DELETE SET NULL;

ALTER TABLE ur_transportation_complex ADD CONSTRAINT ur_transp_comple_roadst_fk FOREIGN KEY (roadstructureattribute_id)
REFERENCES ur_roadstructureattribute (id)
ON DELETE SET NULL;

ALTER TABLE ur_transportation_complex ADD CONSTRAINT ur_transp_comple_roadda_fk FOREIGN KEY (roaddataqualityattribute_id)
REFERENCES ur_roaddataqualityattribu (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip ADD CONSTRAINT ur_trip_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id);

ALTER TABLE ur_trip ADD CONSTRAINT ur_trip_shape_fk FOREIGN KEY (shape_id)
REFERENCES ur_publictransit (id)
ON DELETE SET NULL;

ALTER TABLE ur_trip ADD CONSTRAINT ur_trip_route_fk FOREIGN KEY (route_id)
REFERENCES ur_route (id)
ON DELETE SET NULL;

ALTER TABLE ur_trip ADD CONSTRAINT ur_trip_calendar_fk FOREIGN KEY (calendar_id)
REFERENCES ur_calendar (id)
ON DELETE SET NULL;

ALTER TABLE ur_trip ADD CONSTRAINT ur_trip_calendardate_fk FOREIGN KEY (calendardate_id)
REFERENCES ur_calendardate (id)
ON DELETE SET NULL;

ALTER TABLE ur_trip ADD CONSTRAINT ur_trip_office_fk FOREIGN KEY (office_id)
REFERENCES ur_office (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunc_to_cityobjec ADD CONSTRAINT ur_urbanfun_to_cityobj_fk1 FOREIGN KEY (urbanfunction_id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanfunc_to_cityobjec ADD CONSTRAINT ur_urbanfun_to_cityobj_fk2 FOREIGN KEY (cityobject_id)
REFERENCES cityobject (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunct_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunction_fk FOREIGN KEY (id)
REFERENCES cityobject (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunct_legalgrou_fk FOREIGN KEY (legalgrounds_id)
REFERENCES ur_legalgrounds (id)
ON DELETE SET NULL;

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunct_lod0multi_fk FOREIGN KEY (lod0multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfun_lod_1multis_fk FOREIGN KEY (lod_1multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfun_lod_2multis_fk FOREIGN KEY (lod_2multisurface_id)
REFERENCES surface_geometry (id);

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanization ADD CONSTRAINT ur_urbanization_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id);

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody ADD CONSTRAINT ur_waterbody_fk FOREIGN KEY (id)
REFERENCES waterbody (id);

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyattribute ADD CONSTRAINT ur_waterbodya_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_waterbodyattribute ADD CONSTRAINT ur_waterb_waterb_floodi_fk FOREIGN KEY (waterbody_floodingriskatt_id)
REFERENCES ur_waterbody (id);

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyriverflooding ADD CONSTRAINT ur_waterbodyriverfloodi_fk FOREIGN KEY (id)
REFERENCES ur_waterbodyattribute (id);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone ADD CONSTRAINT ur_zone_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_zone ADD CONSTRAINT ur_zone_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Indexes ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

SET SERVEROUTPUT ON
SET FEEDBACK ON
SET VER OFF
VARIABLE SRID NUMBER;
BEGIN
  SELECT SRID INTO :SRID FROM DATABASE_SRS;
END;
/

column mc new_value SRSNO print
select :SRID mc from dual;

prompt Used SRID for spatial indexes: &SRSNO; 

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_areaof_landus_areao_fkx ON ur_areaofannualdiversions (landusediver_areaofannual_id);

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_attribution_agency_fkx ON ur_attribution (agency_id);

CREATE INDEX ur_attribution_route_fkx ON ur_attribution (route_id);

CREATE INDEX ur_attribution_trip_fkx ON ur_attribution (trip_id);

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_building_buildingda_fkx ON ur_building (buildingdataqualityattrib_id);

CREATE INDEX ur_building_buildingde_fkx ON ur_building (buildingdetailattribute_id);

CREATE INDEX ur_building_buildingid_fkx ON ur_building (buildingidattribute_id);

CREATE INDEX ur_building_largecusto_fkx ON ur_building (largecustomerfacilityattr_id);

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_buildinga_objectcla_fkx ON ur_buildingattribute (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_buildi_buildi_build_fkx ON ur_buildingdisasterriskat (building_buildingdisaster_id);

CREATE INDEX ur_buildingd_objectcla_fkx ON ur_buildingdisasterriskat (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_buildingf_objectcla_fkx ON ur_buildingfloodingriskat (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_calendarda_calendar_fkx ON ur_calendardate (calendar_id);

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_city_furnit_cityf_fkx_1 ON ur_city_furniture (cityfurnituredataqualitya_id);

CREATE INDEX ur_city_furnitu_cityfu_fkx ON ur_city_furniture (cityfurnituredetailattrib_id);

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_cityfurni_objectcla_fkx ON ur_cityfurnitureattribute (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_cityobjec_vegetatio_fkx ON ur_cityobject (vegetationdataqualityattr_id);

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_fareattribut_agency_fkx ON ur_fareattribute (agency_id);

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_farerule_fare_fkx ON ur_farerule (fare_id);

CREATE INDEX ur_farerule_route_fkx ON ur_farerule (route_id);

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_frequency_trip_fkx ON ur_frequency (trip_id);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_keyval_statis_gener_fkx ON ur_keyvaluepair (statisticalg_genericvalue_id);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_keyval_buildi_keyva_fkx ON ur_keyvaluepairattribute (building_keyvaluepairattr_id);

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_land_use_landusedet_fkx ON ur_land_use (landusedetailattribute_id);

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landpr_landpr_landp_fkx ON ur_landpriceperlanduse (landprice_landprice_id);

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_number_landus_numbe_fkx ON ur_numberofannualdiversio (landusediver_numberofannu_id);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_numbe_house_numbe_fkx_1 ON ur_numberofhouseholds (households_numberofhous_id_1);

CREATE INDEX ur_number_househ_numbe_fkx ON ur_numberofhouseholds (households_numberofhouseh_id);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_numbe_censu_numbe_fkx_1 ON ur_numberofhouseholds_1 (censusblock_numberofhou_id_1);

CREATE INDEX ur_number_census_numbe_fkx ON ur_numberofhouseholds_1 (censusblock_numberofhouse_id);

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_pathway_from_fkx ON ur_pathway (from_id);

CREATE INDEX ur_pathway_to_fkx ON ur_pathway (to_id);

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_POINT' AND COLUMN_NAME='POINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_POINT','POINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_point_point_spx ON ur_point (point) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_point_publict_point_fkx ON ur_point (publictransit_point_id);

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_popula_popula_popul_fkx ON ur_populationbyageandsex (population_populationbyag_id);

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_publictra_objectcla_fkx ON ur_publictransit (objectclass_id);

CREATE INDEX ur_publictransi_target_fkx ON ur_publictransit (target_id);

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_public_cityob_datat_fkx ON ur_publictransitdatatype (cityobjectgroup_datatype_id);

CREATE INDEX ur_publictr_objectcl_fkx_1 ON ur_publictransitdatatype (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_roadattri_objectcla_fkx ON ur_roadattribute (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_route_agency_fkx ON ur_route (agency_id);

CREATE INDEX ur_route_description_fkx ON ur_route (description_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_ROUTE' AND COLUMN_NAME='LOD0MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_ROUTE','LOD0MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_route_lod0multicurv_spx ON ur_route (lod0multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_route_parentroute_fkx ON ur_route (parentroute_id);

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_statist_lod_1multis_fkx ON ur_statisticalgrid (lod_1multisurface_id);

CREATE INDEX ur_statist_lod_2multis_fkx ON ur_statisticalgrid (lod_2multisurface_id);

CREATE INDEX ur_statistic_objectcla_fkx ON ur_statisticalgrid (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_stop_level_fkx ON ur_stop (level_id);

CREATE INDEX ur_stop_parentstation_fkx ON ur_stop (parentstation_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_STOP' AND COLUMN_NAME='POINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_STOP','POINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_stop_point_spx ON ur_stop (point) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_stoptime_stop_fkx ON ur_stoptime (stop_id);

CREATE INDEX ur_stoptime_trip_fkx ON ur_stoptime (trip_id);

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_traffic_area_traffi_fkx ON ur_traffic_area (trafficareastructureattri_id);

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_transfer_from_fkx ON ur_transfer (from_id);

CREATE INDEX ur_transfer_to_fkx ON ur_transfer (to_id);

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_translatio_recordid_fkx ON ur_translation (recordid_id);

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_transp_comple_roadd_fkx ON ur_transportation_complex (roaddataqualityattribute_id);

CREATE INDEX ur_transp_comple_roads_fkx ON ur_transportation_complex (roadstructureattribute_id);

CREATE INDEX ur_transp_comple_traff_fkx ON ur_transportation_complex (trafficvolumeattribute_id);

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_trip_calendar_fkx ON ur_trip (calendar_id);

CREATE INDEX ur_trip_calendardate_fkx ON ur_trip (calendardate_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_TRIP' AND COLUMN_NAME='LOD0MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_TRIP','LOD0MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_trip_lod0multicurve_spx ON ur_trip (lod0multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_trip_office_fkx ON ur_trip (office_id);

CREATE INDEX ur_trip_route_fkx ON ur_trip (route_id);

CREATE INDEX ur_trip_shape_fkx ON ur_trip (shape_id);

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfu_to_cityobj_fk2x ON ur_urbanfunc_to_cityobjec (cityobject_id);

CREATE INDEX ur_urbanfu_to_cityobj_fk1x ON ur_urbanfunc_to_cityobjec (urbanfunction_id);

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfunc_legalgrou_fkx ON ur_urbanfunction (legalgrounds_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD0MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD0MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod0multi_spx ON ur_urbanfunction (lod0multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD0MULTIPOINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD0MULTIPOINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfun_lod0mult_spx_1 ON ur_urbanfunction (lod0multipoint) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_urbanfunc_lod0multi_fkx ON ur_urbanfunction (lod0multisurface_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_1MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_1MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfu_lod_1multic_spx ON ur_urbanfunction (lod_1multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_1MULTIPOINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_1MULTIPOINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfu_lod_1multip_spx ON ur_urbanfunction (lod_1multipoint) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_urbanfu_lod_1multis_fkx ON ur_urbanfunction (lod_1multisurface_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_2MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_2MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfu_lod_2multic_spx ON ur_urbanfunction (lod_2multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_2MULTIPOINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_2MULTIPOINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfu_lod_2multip_spx ON ur_urbanfunction (lod_2multipoint) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_urbanfu_lod_2multis_fkx ON ur_urbanfunction (lod_2multisurface_id);

CREATE INDEX ur_urbanfunc_objectcla_fkx ON ur_urbanfunction (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_waterbody_objectcla_fkx ON ur_waterbodyattribute (objectclass_id);

CREATE INDEX ur_waterb_waterb_flood_fkx ON ur_waterbodyattribute (waterbody_floodingriskatt_id);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_zone_objectclass_fkx ON ur_zone (objectclass_id);

