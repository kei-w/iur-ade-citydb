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

CREATE SEQUENCE ur_vegetationattribut_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_populationbyageand_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_numberofhouseholds_seq_2 INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_parkattribute_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_parkingplaceattrib_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_sewersystemattribu_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_structuredetails_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_urbanrapidtransitr_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_urbanroadattribute_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_vehicleterminalatt_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

CREATE SEQUENCE ur_waterworksattribut_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 10000;

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
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofannualdiversio
(
    id NUMBER(38) NOT NULL,
    count INTEGER,
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
    objectclass_id INTEGER,
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
-- ur_populatio_to_populatio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_populatio_to_populatio
(
    populationbyageandsex_id NUMBER(38) NOT NULL,
    population_id NUMBER(38) NOT NULL,
    PRIMARY KEY (populationbyageandsex_id, population_id)
);

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landprice_to_landprice
(
    landpriceperlanduse_id NUMBER(38) NOT NULL,
    landprice_id NUMBER(38) NOT NULL,
    PRIMARY KEY (landpriceperlanduse_id, landprice_id)
);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landusedi_to_numberofa
(
    numberofannualdiversio_id NUMBER(38) NOT NULL,
    landusediversion_id NUMBER(38) NOT NULL,
    PRIMARY KEY (numberofannualdiversio_id, landusediversion_id)
);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landusedi_to_areaofann
(
    areaofannualdiversions_id NUMBER(38) NOT NULL,
    landusediversion_id NUMBER(38) NOT NULL,
    PRIMARY KEY (areaofannualdiversions_id, landusediversion_id)
);

-- -------------------------------------------------------------------- 
-- ur_statistic_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_statistic_to_keyvaluep
(
    keyvaluepair_id NUMBER(38) NOT NULL,
    statisticalgrid_id NUMBER(38) NOT NULL,
    PRIMARY KEY (keyvaluepair_id, statisticalgrid_id)
);

-- -------------------------------------------------------------------- 
-- ur_publictra_to_point 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictra_to_point
(
    point_id NUMBER(38) NOT NULL,
    publictransit_id NUMBER(38) NOT NULL,
    PRIMARY KEY (point_id, publictransit_id)
);

-- -------------------------------------------------------------------- 
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_building_to_buildingd
(
    buildingdisasterriskat_id NUMBER(38) NOT NULL,
    building_id NUMBER(38) NOT NULL,
    PRIMARY KEY (buildingdisasterriskat_id, building_id)
);

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_building_to_keyvaluep
(
    keyvaluepairattribute_id NUMBER(38) NOT NULL,
    building_id NUMBER(38) NOT NULL,
    PRIMARY KEY (keyvaluepairattribute_id, building_id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbody_to_waterbody
(
    waterbodyattribute_id NUMBER(38) NOT NULL,
    waterbody_id NUMBER(38) NOT NULL,
    PRIMARY KEY (waterbodyattribute_id, waterbody_id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjec_to_publictra
(
    publictransitdatatype_id NUMBER(38) NOT NULL,
    cityobjectgroup_3_id NUMBER(38) NOT NULL,
    PRIMARY KEY (publictransitdatatype_id, cityobjectgroup_3_id)
);

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_districtd 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_abstractd_to_districtd
(
    abstractdistrictplan_id NUMBER(38) NOT NULL,
    districtdevelopmentpla_id NUMBER(38) NOT NULL,
    PRIMARY KEY (districtdevelopmentpla_id, abstractdistrictplan_id)
);

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_abstractd_to_zone
(
    abstractdistrictplan_id NUMBER(38) NOT NULL,
    zone_id NUMBER(38) NOT NULL,
    PRIMARY KEY (zone_id, abstractdistrictplan_id)
);

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_abstractdistrictplan
(
    id NUMBER(38) NOT NULL,
    objectclass_id INTEGER,
    objectives VARCHAR2(1000),
    policy VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_areaclassification
(
    id NUMBER(38) NOT NULL,
    population INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_boundary 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_boundary
(
    id NUMBER(38) NOT NULL,
    offset_ NUMBER,
    offset__uom VARCHAR2(1000),
    offsetdirection VARCHAR2(1000),
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
-- ur_collectivefacilities_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivefacilities_2
(
    id NUMBER(38) NOT NULL,
    housingfacilities VARCHAR2(1000),
    maximumbuildingcoveragerate NUMBER,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    minimumfloorarearate NUMBER,
    publicfacilities VARCHAR2(1000),
    supecificbusinessfacilities VARCHAR2(1000),
    utilityfacilities VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_3 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivefacilities_3
(
    id NUMBER(38) NOT NULL,
    housingfacilities VARCHAR2(1000),
    maximumbuildingcoveragerate NUMBER,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    minimumfloorarearate NUMBER,
    publicfacilities VARCHAR2(1000),
    supecificbusinessfacilities VARCHAR2(1000),
    utilityfacilities VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivefacilitiesfo 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivefacilitiesfo
(
    id NUMBER(38) NOT NULL,
    housingfacilities VARCHAR2(1000),
    maximumbuildingcoveragerate NUMBER,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    minimumfloorarearate NUMBER,
    publicfacilities VARCHAR2(1000),
    supecificbusinessfacilities VARCHAR2(1000),
    utilityfacilities VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivegovernmentan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivegovernmentan
(
    id NUMBER(38) NOT NULL,
    buildingcoveragerate NUMBER,
    floorarearate NUMBER,
    publicfacilitiesallocationpo VARCHAR2(1000),
    scheduledexecutor VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivehousingfacil 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivehousingfacil
(
    id NUMBER(38) NOT NULL,
    buildingcoveragerate NUMBER,
    floorarearate NUMBER,
    numberofhighrisehousing INTEGER,
    numberoflowrisehousing INTEGER,
    numberofmiddlerisehousing INTEGER,
    publicfacilitiesallocationpo VARCHAR2(1000),
    scheduledexecutor VARCHAR2(1000),
    totalnumberofhousing INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectiveurbandisaste 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectiveurbandisaste
(
    id NUMBER(38) NOT NULL,
    maximumbuildingcoveragerate NUMBER,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    minimumfloorarearate NUMBER,
    specificutilityandpublicfaci VARCHAR2(1000),
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
-- ur_disasterp_to_zonaldisa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterp_to_zonaldisa
(
    disasterpreventionbl_2_id NUMBER(38) NOT NULL,
    zonaldisasterpreventio_id NUMBER(38) NOT NULL,
    PRIMARY KEY (zonaldisasterpreventio_id, disasterpreventionbl_2_id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbase 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterpreventionbase
(
    id NUMBER(38) NOT NULL,
    capacity INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbl_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterpreventionbl_2
(
    id NUMBER(38) NOT NULL,
    specifiedzonaldisasterpreven VARCHAR2(1000),
    zonaldisasterpreventionfacil VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbloc 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterpreventionbloc
(
    id NUMBER(38) NOT NULL,
    developmentplan VARCHAR2(1000),
    disasterpreventionpublicfaci VARCHAR2(1000),
    otherpublicfacilityallocatio VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesspa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_distributionbusinesspa
(
    id NUMBER(38) NOT NULL,
    buildingcoveragerate NUMBER,
    distributionbusinesspark VARCHAR2(1000),
    floorarearate NUMBER,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    publicandutilityfacilities VARCHAR2(1000),
    scheduledexecutor VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesszo 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_distributionbusinesszo
(
    id NUMBER(38) NOT NULL,
    guidelinepublicationdate TIMESTAMP,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_district 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_district
(
    id NUMBER(38) NOT NULL,
    buildingdesignrestriction VARCHAR2(1000),
    buildingrestrictions VARCHAR2(1000),
    fenceguideline VARCHAR2(1000),
    maximumbuildingcoveragerate NUMBER,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    minimumbuildingarea NUMBER,
    minimumbuildingarea_uom VARCHAR2(1000),
    minimumbuildingcoveragerate NUMBER,
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    minimumfloorarearate NUMBER,
    minimumfloorheight NUMBER,
    minimumfloorheight_uom VARCHAR2(1000),
    minimumfrontagerate NUMBER,
    minimumgreeningrate NUMBER,
    minimumgroundheight NUMBER,
    minimumgroundheight_uom VARCHAR2(1000),
    minimumsitearea NUMBER,
    minimumsitearea_uom VARCHAR2(1000),
    restrictionsforfireprotectio VARCHAR2(1000),
    restrictionsfornoiseprotecti VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    strucutureplacementrestricti VARCHAR2(1000),
    userestrictions VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_districtd_to_district 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtd_to_district
(
    district_id NUMBER(38) NOT NULL,
    districtdevelopmentpla_id NUMBER(38) NOT NULL,
    PRIMARY KEY (district_id, districtdevelopmentpla_id)
);

-- -------------------------------------------------------------------- 
-- ur_districtd_to_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtd_to_zone
(
    districtdevelopmentpla_id NUMBER(38) NOT NULL,
    zone_id NUMBER(38) NOT NULL,
    PRIMARY KEY (zone_id, districtdevelopmentpla_id)
);

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtdevelopmentpla
(
    id NUMBER(38) NOT NULL,
    activityrestrictioninfarmlan VARCHAR2(1000),
    buildingrestrictions VARCHAR2(1000),
    districtfacilitiesallocation VARCHAR2(1000),
    landuserestrictions VARCHAR2(1000),
    objectclass_id INTEGER,
    urbangreenspaceconservation VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_districtplan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtplan
(
    id NUMBER(38) NOT NULL,
    facilityallocation VARCHAR2(1000),
    landusepolicy VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtsandzones
(
    id NUMBER(38) NOT NULL,
    areaintotal NUMBER,
    areaintotal_uom VARCHAR2(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_exceptionalfloorareara 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_exceptionalfloorareara
(
    id NUMBER(38) NOT NULL,
    buildingheightlimits NUMBER,
    buildingheightlimits_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_globalhubcitydevelopme 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_globalhubcitydevelopme
(
    id NUMBER(38) NOT NULL,
    implementationbody VARCHAR2(1000),
    implementationperiod VARCHAR2(1000),
    plan VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_heightcontroldistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_heightcontroldistrict
(
    id NUMBER(38) NOT NULL,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_highlevelusedistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_highlevelusedistrict
(
    id NUMBER(38) NOT NULL,
    maximumbuildingcoveragerate VARCHAR2(1000),
    maximumfloorarearate VARCHAR2(1000),
    minimumbuildingarea VARCHAR2(1000),
    minimumbuildingarea_uom VARCHAR2(1000),
    minimumfloorarearate VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_highriseresidentialatt 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_highriseresidentialatt
(
    id NUMBER(38) NOT NULL,
    floorarearate NUMBER,
    maximumbuildingcoveragerate NUMBER,
    minimumsitearea NUMBER,
    minimumsitearea_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_historicscenerymainten 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_historicscenerymainten
(
    id NUMBER(38) NOT NULL,
    landusepolicy VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_industrialparkdevelopm 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_industrialparkdevelopm
(
    id NUMBER(38) NOT NULL,
    publicfacilityallocation VARCHAR2(1000),
    residentiallanduseplan VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landreadjustmentprojec 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landreadjustmentprojec
(
    id NUMBER(38) NOT NULL,
    buildinglotdevelopment VARCHAR2(1000),
    publicfacilityallocation VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landscapezone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landscapezone
(
    id NUMBER(38) NOT NULL,
    buildingdesignrestriction VARCHAR2(1000),
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    minimumsitearea NUMBER,
    minimumsitearea_uom VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_newhousingandurbandeve 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_newhousingandurbandeve
(
    id NUMBER(38) NOT NULL,
    housing VARCHAR2(1000),
    publicfacilityallocation VARCHAR2(1000),
    residentiallanduseplan VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_newurbaninfrastructure 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_newurbaninfrastructure
(
    id NUMBER(38) NOT NULL,
    districtsallocation VARCHAR2(1000),
    landforcentralpublicfaciliti VARCHAR2(1000),
    landuseplan VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofhouseholds_2
(
    id NUMBER(38) NOT NULL,
    censusblock_numberofhouse__2 NUMBER(38),
    censusblock_numberofhouse_id NUMBER(38),
    class VARCHAR2(1000),
    class_codespace VARCHAR2(1000),
    number_ INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_openspaceforpublicuse
(
    id NUMBER(38) NOT NULL,
    parkattribute_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_parkattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_parkattribute
(
    id NUMBER(38) NOT NULL,
    parkserialnumber INTEGER,
    parksizenumber VARCHAR2(1000),
    parksizenumber_codespace VARCHAR2(1000),
    parktypenumber VARCHAR2(1000),
    parktypenumber_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_parkingplaceattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_parkingplaceattribute
(
    id NUMBER(38) NOT NULL,
    storeysaboveground INTEGER,
    storeysbelowground INTEGER,
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
-- ur_portzone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_portzone
(
    id NUMBER(38) NOT NULL,
    floorarearate NUMBER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_privateurbanrenewalpro 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_privateurbanrenewalpro
(
    id NUMBER(38) NOT NULL,
    developer VARCHAR2(1000),
    plan VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_productivegreenzone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_productivegreenzone
(
    id NUMBER(38) NOT NULL,
    specification VARCHAR2(1000),
    specification_codespace VARCHAR2(1000),
    zonenumber VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_projectpromotionarea
(
    id NUMBER(38) NOT NULL,
    developmentpolicy VARCHAR2(1000),
    objectclass_id INTEGER,
    publicfacilitiesplans VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransitfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransitfacility
(
    id NUMBER(38) NOT NULL,
    companyname VARCHAR2(1000),
    frequencyofservice INTEGER,
    numberofcustomers INTEGER,
    routename VARCHAR2(1000),
    sectionname VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_quasiurbanplanningarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_quasiurbanplanningarea
(
    id NUMBER(38) NOT NULL,
    cityarea NUMBER,
    cityarea_uom VARCHAR2(1000),
    citypopulation INTEGER,
    population INTEGER,
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
-- ur_residentialblockconstr 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_residentialblockconstr
(
    id NUMBER(38) NOT NULL,
    developmentplan VARCHAR2(1000),
    publicfacilityallocation VARCHAR2(1000),
    sitearea NUMBER,
    sitearea_uom VARCHAR2(1000),
    totalfloorarea NUMBER,
    totalfloorarea_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_residentialenvironment 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_residentialenvironment
(
    id NUMBER(38) NOT NULL,
    maximumbuildingcoveragerate NUMBER,
    maximumbuildingheight VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    otherrestrictions VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    usetobeinduced VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictimprov 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadsidedistrictimprov
(
    id NUMBER(38) NOT NULL,
    roadsidedistrictfacilitiesal VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictplan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadsidedistrictplan
(
    id NUMBER(38) NOT NULL,
    facilitiesallocation VARCHAR2(1000),
    landusepolicy VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_ruraldistrictimproveme 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_ruraldistrictimproveme
(
    id NUMBER(38) NOT NULL,
    ruraldistrictfacilitiesalloc VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_scenicdistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_scenicdistrict
(
    id NUMBER(38) NOT NULL,
    buildingcoveragerate NUMBER,
    buildingheightlimits NUMBER,
    buildingheightlimits_uom VARCHAR2(1000),
    wallsetbackdistancewitha_uom VARCHAR2(1000),
    wallsetbackdistancewithadjoi NUMBER,
    wallsetbackdistancewithr_uom VARCHAR2(1000),
    wallsetbackdistancewithroad NUMBER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_scheduledareaforurband
(
    id NUMBER(38) NOT NULL,
    objectclass_id INTEGER,
    scheduledexecutor VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_sedimentdisasterpronea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_sedimentdisasterpronea
(
    id NUMBER(38) NOT NULL,
    areatype VARCHAR2(1000),
    areatype_codespace VARCHAR2(1000),
    disastertype VARCHAR2(1000),
    disastertype_codespace VARCHAR2(1000),
    status VARCHAR2(1000),
    status_codespace VARCHAR2(1000),
    zonename VARCHAR2(1000),
    zonenumber VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_sewersystemattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_sewersystemattribute
(
    id NUMBER(38) NOT NULL,
    drainagearea VARCHAR2(1000),
    endlocation VARCHAR2(1000),
    startlocation VARCHAR2(1000),
    systemtype VARCHAR2(1000),
    systemtype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialgreenspaceconse 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialgreenspaceconse
(
    id NUMBER(38) NOT NULL,
    requirement VARCHAR2(1000),
    requirement_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialurbanrenaissanc 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialurbanrenaissanc
(
    id NUMBER(38) NOT NULL,
    maximumbuildingcoveragerate NUMBER,
    maximumbuildingheight VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    minimumbuildingarea NUMBER,
    minimumbuildingarea_uom VARCHAR2(1000),
    minimumfloorarearate NUMBER,
    otherrestrictions VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    usetobeinduced VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialuseattractiondi 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialuseattractiondi
(
    id NUMBER(38) NOT NULL,
    maximumbuildingheight VARCHAR2(1000),
    maximumfloorarearate NUMBER,
    minimumbuildingarea NUMBER,
    minimumbuildingarea_uom VARCHAR2(1000),
    minimumfloorarearate NUMBER,
    otherrestrictions VARCHAR2(1000),
    usetobeinduced VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialusedistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialusedistrict
(
    id NUMBER(38) NOT NULL,
    buildingrestrictions VARCHAR2(1000),
    otherrestrictions VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialuserestrictiond 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialuserestrictiond
(
    id NUMBER(38) NOT NULL,
    buildingrestrictions VARCHAR2(1000),
    otherrestrictions VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specified_to_globalhub 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specified_to_globalhub
(
    globalhubcitydevelopme_id NUMBER(38) NOT NULL,
    specifiedurgenturbanre_id NUMBER(38) NOT NULL,
    PRIMARY KEY (globalhubcitydevelopme_id, specifiedurgenturbanre_id)
);

-- -------------------------------------------------------------------- 
-- ur_specifiedblock 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specifiedblock
(
    id NUMBER(38) NOT NULL,
    floorarearate NUMBER,
    maximumbuildingheight NUMBER,
    maximumbuildingheight_uom VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specifieddisasterpreve 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specifieddisasterpreve
(
    id NUMBER(38) NOT NULL,
    minimumbuildingheight NUMBER,
    minimumbuildingheight_uom VARCHAR2(1000),
    minimumfrontagerate NUMBER,
    minimumsitearea NUMBER,
    minimumsitearea_uom VARCHAR2(1000),
    setbacksize VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specifiedurgenturbanre 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specifiedurgenturbanre
(
    id NUMBER(38) NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_structuredetails 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_structuredetails
(
    id NUMBER(38) NOT NULL,
    crosstype VARCHAR2(1000),
    crosstype_codespace VARCHAR2(1000),
    endlocation VARCHAR2(1000),
    length NUMBER,
    length_uom VARCHAR2(1000),
    maximumwidth NUMBER,
    maximumwidth_uom VARCHAR2(1000),
    minimumwidth NUMBER,
    minimumwidth_uom VARCHAR2(1000),
    standardwidth NUMBER,
    standardwidth_uom VARCHAR2(1000),
    startlocation VARCHAR2(1000),
    structuretype VARCHAR2(1000),
    structuretype_codespace VARCHAR2(1000),
    vialocations VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_supplyfacility
(
    id NUMBER(38) NOT NULL,
    waterworksattribute_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_threedimensionalextent 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_threedimensionalextent
(
    id NUMBER(38) NOT NULL,
    maximumload NUMBER,
    maximumload_uom VARCHAR2(1000),
    minimumdistance NUMBER,
    minimumdistance_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trafficfacility
(
    id NUMBER(38) NOT NULL,
    endlocation VARCHAR2(1000),
    length NUMBER,
    length_uom VARCHAR2(1000),
    parkingplaceattribute_id NUMBER(38),
    startlocation VARCHAR2(1000),
    urbanrapidtransitrailroad_id NUMBER(38),
    urbanroadattribute_id NUMBER(38),
    vehicleterminalattribute_id NUMBER(38),
    vialocations VARCHAR2(1000),
    width NUMBER,
    width_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_treatmentfacility
(
    id NUMBER(38) NOT NULL,
    sewersystemsattribute_id NUMBER(38),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_treeplantingdistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_treeplantingdistrict
(
    id NUMBER(38) NOT NULL,
    minimumgreeningrate NUMBER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbandevelopmentprojec
(
    id NUMBER(38) NOT NULL,
    objectclass_id INTEGER,
    scheduledexecutor VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbandisasterrecoveryp 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbandisasterrecoveryp
(
    id NUMBER(38) NOT NULL,
    emergencyrecoverypolicy VARCHAR2(1000),
    expirationdate TIMESTAMP,
    plannedprojecttype VARCHAR2(1000),
    plannedprojecttype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfaci_to_threedime 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfaci_to_threedime
(
    threedimensionalextent_id NUMBER(38) NOT NULL,
    urbanfacility_id NUMBER(38) NOT NULL,
    PRIMARY KEY (threedimensionalextent_id, urbanfacility_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfacility
(
    id NUMBER(38) NOT NULL,
    number_ VARCHAR2(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfacilitystipulate
(
    id NUMBER(38) NOT NULL,
    length NUMBER,
    length_uom VARCHAR2(1000),
    objectclass_id INTEGER,
    width NUMBER,
    width_uom VARCHAR2(1000),
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
    areaclassification_codespace VARCHAR2(1000),
    areaclassificationtype VARCHAR2(1000),
    city VARCHAR2(1000),
    city_codespace VARCHAR2(1000),
    class VARCHAR2(1000),
    class_codespace VARCHAR2(1000),
    custodian VARCHAR2(1000),
    enactmentfiscalyear DATE,
    expirationfiscalyear DATE,
    function VARCHAR2(1000),
    function_codespace VARCHAR2(1000),
    legalgrounds VARCHAR2(1000),
    lod0multicurve MDSYS.SDO_GEOMETRY,
    lod0multipoint MDSYS.SDO_GEOMETRY,
    lod0multisurface_id NUMBER(38),
    lod1multicurve MDSYS.SDO_GEOMETRY,
    lod1multisurface_id NUMBER(38),
    lod_1multicurve MDSYS.SDO_GEOMETRY,
    lod_1multipoint MDSYS.SDO_GEOMETRY,
    lod_1multisurface_id NUMBER(38),
    lod_2multicurve MDSYS.SDO_GEOMETRY,
    lod_2multipoint MDSYS.SDO_GEOMETRY,
    lod_2multisurface_id NUMBER(38),
    nominalarea NUMBER,
    nominalarea_uom VARCHAR2(1000),
    note VARCHAR2(1000),
    notificationnumber VARCHAR2(1000),
    objectclass_id INTEGER,
    prefecture VARCHAR2(1000),
    prefecture_codespace VARCHAR2(1000),
    reason VARCHAR2(1000),
    reference VARCHAR2(1000),
    surveyyear DATE,
    urbanplantype VARCHAR2(1000),
    urbanplantype_codespace VARCHAR2(1000),
    usage VARCHAR2(1000),
    usage_codespace VARCHAR2(1000),
    validfrom TIMESTAMP,
    validfromtype VARCHAR2(1000),
    validfromtype_codespace VARCHAR2(1000),
    validto TIMESTAMP,
    validtotype VARCHAR2(1000),
    validtotype_codespace VARCHAR2(1000),
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
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanplanningarea
(
    id NUMBER(38) NOT NULL,
    areaclassification VARCHAR2(1000),
    areaclassification_codespace VARCHAR2(1000),
    cityarea NUMBER,
    cityarea_uom VARCHAR2(1000),
    citypopulation INTEGER,
    policyforareaclassification VARCHAR2(1000),
    policyforurbanplandecision VARCHAR2(1000),
    population INTEGER,
    purposeforurbanplan VARCHAR2(1000),
    reasonforareaclassification VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanrapi_to_structure 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanrapi_to_structure
(
    structuredetails_id NUMBER(38) NOT NULL,
    urbanrapidtransitrailr_id NUMBER(38) NOT NULL,
    PRIMARY KEY (structuredetails_id, urbanrapidtransitrailr_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanrapidtransitrailr 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanrapidtransitrailr
(
    id NUMBER(38) NOT NULL,
    crosstype VARCHAR2(1000),
    crosstype_codespace VARCHAR2(1000),
    structuretype VARCHAR2(1000),
    structuretype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentproj 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanredevelopmentproj
(
    id NUMBER(38) NOT NULL,
    developmentplan VARCHAR2(1000),
    housingtarget VARCHAR2(1000),
    numberofhousing INTEGER,
    publicfacilityallocation VARCHAR2(1000),
    sitearea NUMBER,
    sitearea_uom VARCHAR2(1000),
    totalfloorarea NUMBER,
    totalfloorarea_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentprom 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanredevelopmentprom
(
    id NUMBER(38) NOT NULL,
    publicfacilities VARCHAR2(1000),
    unitarea VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanrenewalproject 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanrenewalproject
(
    id NUMBER(38) NOT NULL,
    buildingusage VARCHAR2(1000),
    floorarearate NUMBER,
    setbacksize VARCHAR2(1000),
    sitearea NUMBER,
    sitearea_uom VARCHAR2(1000),
    storeysaboveground INTEGER,
    storeysbelowground INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanroad_to_structure 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanroad_to_structure
(
    structuredetails_id NUMBER(38) NOT NULL,
    urbanroadattribute_id NUMBER(38) NOT NULL,
    PRIMARY KEY (structuredetails_id, urbanroadattribute_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanroadattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanroadattribute
(
    id NUMBER(38) NOT NULL,
    crosstype VARCHAR2(1000),
    crosstype_codespace VARCHAR2(1000),
    numberoflanes INTEGER,
    roadstructure VARCHAR2(1000),
    roadtype VARCHAR2(1000),
    roadtype_codespace VARCHAR2(1000),
    routeserialnumber INTEGER,
    routesizenumber VARCHAR2(1000),
    routesizenumber_codespace VARCHAR2(1000),
    routetypenumber VARCHAR2(1000),
    routetypenumber_codespace VARCHAR2(1000),
    structuretype VARCHAR2(1000),
    structuretype_codespace VARCHAR2(1000),
    trafficplazas VARCHAR2(1000),
    trafficplazas_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_privateur 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturb_to_privateur
(
    privateurbanrenewalpro_id NUMBER(38) NOT NULL,
    urgenturbanrenewalarea_id NUMBER(38) NOT NULL,
    PRIMARY KEY (privateurbanrenewalpro_id, urgenturbanrenewalarea_id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specialur 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturb_to_specialur
(
    specialurbanrenaissanc_id NUMBER(38) NOT NULL,
    urgenturbanrenewalarea_id NUMBER(38) NOT NULL,
    PRIMARY KEY (specialurbanrenaissanc_id, urgenturbanrenewalarea_id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specified 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturb_to_specified
(
    specifiedurgenturbanre_id NUMBER(38) NOT NULL,
    urgenturbanrenewalarea_id NUMBER(38) NOT NULL,
    PRIMARY KEY (specifiedurgenturbanre_id, urgenturbanrenewalarea_id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturbanrenewalarea
(
    id NUMBER(38) NOT NULL,
    developmentpolicy VARCHAR2(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_usedistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_usedistrict
(
    id NUMBER(38) NOT NULL,
    buildingcoveragerate NUMBER,
    buildingheightlimits NUMBER,
    buildingheightlimits_uom VARCHAR2(1000),
    buildingrestrictions VARCHAR2(1000),
    floorarearate NUMBER,
    minimumsitearea NUMBER,
    minimumsitearea_uom VARCHAR2(1000),
    otherrestrictions VARCHAR2(1000),
    setbackrestrictions VARCHAR2(1000),
    shaderegulation VARCHAR2(1000),
    wallsetbackdistance VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_vehicleterminalattribu 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_vehicleterminalattribu
(
    id NUMBER(38) NOT NULL,
    terminaltype VARCHAR2(1000),
    terminaltype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterway
(
    id NUMBER(38) NOT NULL,
    endlocation VARCHAR2(1000),
    length NUMBER,
    length_uom VARCHAR2(1000),
    startlocation VARCHAR2(1000),
    structure VARCHAR2(1000),
    structure_codespace VARCHAR2(1000),
    width NUMBER,
    width_uom VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterworksattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterworksattribute
(
    id NUMBER(38) NOT NULL,
    endlocation VARCHAR2(1000),
    startlocation VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zonaldisasterpreventio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zonaldisasterpreventio
(
    id NUMBER(38) NOT NULL,
    facilitytype VARCHAR2(1000),
    facilitytype_codespace VARCHAR2(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zone
(
    id NUMBER(38) NOT NULL,
    location VARCHAR2(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zone_to_boundary 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zone_to_boundary
(
    boundary_id NUMBER(38) NOT NULL,
    zone_id NUMBER(38) NOT NULL,
    PRIMARY KEY (boundary_id, zone_id)
);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create foreign keys ******************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_agency ADD CONSTRAINT ur_agency_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_attribution ADD CONSTRAINT ur_attribution_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

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
REFERENCES building (id)
ON DELETE CASCADE;

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
REFERENCES ur_buildingattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdetailattribut ADD CONSTRAINT ur_buildingdetailattrib_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdisasterriskat ADD CONSTRAINT ur_buildingdi_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_buildingdisasterriskat ADD CONSTRAINT ur_buildingdisasterrisk_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingfloodingriskat ADD CONSTRAINT ur_buildingfl_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_buildingfloodingriskat ADD CONSTRAINT ur_buildingfloodingrisk_fk FOREIGN KEY (id)
REFERENCES ur_buildingdisasterriskat (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingidattribute ADD CONSTRAINT ur_buildingidattribute_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_buildingriverfloodingr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingriverfloodingr ADD CONSTRAINT ur_buildingriverfloodin_fk FOREIGN KEY (id)
REFERENCES ur_buildingfloodingriskat (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendar ADD CONSTRAINT ur_calendar_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendardate ADD CONSTRAINT ur_calendardate_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

ALTER TABLE ur_calendardate ADD CONSTRAINT ur_calendardat_calendar_fk FOREIGN KEY (calendar_id)
REFERENCES ur_calendar (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_city_furniture ADD CONSTRAINT ur_city_furniture_fk FOREIGN KEY (id)
REFERENCES city_furniture (id)
ON DELETE CASCADE;

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
REFERENCES ur_cityfurnitureattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredetailatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityfurnituredetailatt ADD CONSTRAINT ur_cityfurnituredetaila_fk FOREIGN KEY (id)
REFERENCES ur_cityfurnitureattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobject ADD CONSTRAINT ur_cityobject_fk FOREIGN KEY (id)
REFERENCES cityobject (id)
ON DELETE CASCADE;

ALTER TABLE ur_cityobject ADD CONSTRAINT ur_cityobject_vegetatio_fk FOREIGN KEY (vegetationdataqualityattr_id)
REFERENCES ur_vegetationattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup ADD CONSTRAINT ur_cityobjectgroup_fk FOREIGN KEY (id)
REFERENCES cityobjectgroup (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_1 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_1 ADD CONSTRAINT ur_cityobjectgroup_fk_1 FOREIGN KEY (id)
REFERENCES cityobjectgroup (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_2 ADD CONSTRAINT ur_cityobjectgroup_fk_2 FOREIGN KEY (id)
REFERENCES cityobjectgroup (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_3 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_3 ADD CONSTRAINT ur_cityobjectgroup_fk_3 FOREIGN KEY (id)
REFERENCES cityobjectgroup (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fareattribute ADD CONSTRAINT ur_fareattribute_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

ALTER TABLE ur_fareattribute ADD CONSTRAINT ur_fareattribute_agency_fk FOREIGN KEY (agency_id)
REFERENCES ur_agency (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_farerule ADD CONSTRAINT ur_farerule_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

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
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_frequency ADD CONSTRAINT ur_frequency_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

ALTER TABLE ur_frequency ADD CONSTRAINT ur_frequency_trip_fk FOREIGN KEY (trip_id)
REFERENCES ur_trip (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_households ADD CONSTRAINT ur_households_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use ADD CONSTRAINT ur_land_use_fk FOREIGN KEY (id)
REFERENCES land_use (id)
ON DELETE CASCADE;

ALTER TABLE ur_land_use ADD CONSTRAINT ur_land_use_landusedeta_fk FOREIGN KEY (landusedetailattribute_id)
REFERENCES ur_landuseattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice ADD CONSTRAINT ur_landprice_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusediversion ADD CONSTRAINT ur_landusediversion_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_largecustomerfacilitya ADD CONSTRAINT ur_largecustomerfacilit_fk FOREIGN KEY (id)
REFERENCES ur_buildingattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds ADD CONSTRAINT ur_number_househ_number_fk FOREIGN KEY (households_numberofhouseh_id)
REFERENCES ur_households (id);

ALTER TABLE ur_numberofhouseholds ADD CONSTRAINT ur_number_house_numbe_fk_1 FOREIGN KEY (households_numberofhous_id_1)
REFERENCES ur_households (id);

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_office ADD CONSTRAINT ur_office_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_officesandemployees ADD CONSTRAINT ur_officesandemployees_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pathway ADD CONSTRAINT ur_pathway_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

ALTER TABLE ur_pathway ADD CONSTRAINT ur_pathway_from_fk FOREIGN KEY (from_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

ALTER TABLE ur_pathway ADD CONSTRAINT ur_pathway_to_fk FOREIGN KEY (to_id)
REFERENCES ur_stop (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_population ADD CONSTRAINT ur_population_fk FOREIGN KEY (id)
REFERENCES ur_statisticalgrid (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransit ADD CONSTRAINT ur_publictran_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_publictransit ADD CONSTRAINT ur_publictransit_fk FOREIGN KEY (id)
REFERENCES cityobject (id)
ON DELETE CASCADE;

ALTER TABLE ur_publictransit ADD CONSTRAINT ur_publictransit_target_fk FOREIGN KEY (target_id)
REFERENCES cityobject (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitdatatype ADD CONSTRAINT ur_publictra_objectcl_fk_1 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadattribute ADD CONSTRAINT ur_roadattrib_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

-- -------------------------------------------------------------------- 
-- ur_roaddataqualityattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roaddataqualityattribu ADD CONSTRAINT ur_roaddataqualityattri_fk FOREIGN KEY (id)
REFERENCES ur_roadattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadstructureattribute ADD CONSTRAINT ur_roadstructureattribu_fk FOREIGN KEY (id)
REFERENCES ur_roadattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_route ADD CONSTRAINT ur_route_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

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
REFERENCES cityobject (id)
ON DELETE CASCADE;

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
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

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
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

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
REFERENCES traffic_area (id)
ON DELETE CASCADE;

ALTER TABLE ur_traffic_area ADD CONSTRAINT ur_traffic_area_traffic_fk FOREIGN KEY (trafficareastructureattri_id)
REFERENCES ur_trafficareaattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_trafficvolumeattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trafficvolumeattribute ADD CONSTRAINT ur_trafficvolumeattribu_fk FOREIGN KEY (id)
REFERENCES ur_roadattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transfer ADD CONSTRAINT ur_transfer_fk FOREIGN KEY (id)
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

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
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

ALTER TABLE ur_translation ADD CONSTRAINT ur_translation_recordid_fk FOREIGN KEY (recordid_id)
REFERENCES ur_publictransit (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_translationjp ADD CONSTRAINT ur_translationjp_fk FOREIGN KEY (id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transportation_complex ADD CONSTRAINT ur_transportati_complex_fk FOREIGN KEY (id)
REFERENCES transportation_complex (id)
ON DELETE CASCADE;

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
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

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
-- ur_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody ADD CONSTRAINT ur_waterbody_fk FOREIGN KEY (id)
REFERENCES waterbody (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyattribute ADD CONSTRAINT ur_waterbodya_objectcla_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyriverflooding ADD CONSTRAINT ur_waterbodyriverfloodi_fk FOREIGN KEY (id)
REFERENCES ur_waterbodyattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_populatio_to_populatio 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_populatio_to_populatio ADD CONSTRAINT ur_populatio_populationb_fk_2 FOREIGN KEY (populationbyageandsex_id)
REFERENCES ur_populationbyageandsex (id)
ON DELETE CASCADE;

ALTER TABLE ur_populatio_to_populatio ADD CONSTRAINT ur_populatio_population__fk_2 FOREIGN KEY (population_id)
REFERENCES ur_population (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice_to_landprice ADD CONSTRAINT ur_landprice_landpricepe_fk_2 FOREIGN KEY (landpriceperlanduse_id)
REFERENCES ur_landpriceperlanduse (id)
ON DELETE CASCADE;

ALTER TABLE ur_landprice_to_landprice ADD CONSTRAINT ur_landprice_landprice_i_fk_2 FOREIGN KEY (landprice_id)
REFERENCES ur_landprice (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusedi_to_numberofa ADD CONSTRAINT ur_landusedi_numberofann_fk_2 FOREIGN KEY (numberofannualdiversio_id)
REFERENCES ur_numberofannualdiversio (id)
ON DELETE CASCADE;

ALTER TABLE ur_landusedi_to_numberofa ADD CONSTRAINT ur_landusedi_landusedive_fk_2 FOREIGN KEY (landusediversion_id)
REFERENCES ur_landusediversion (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusedi_to_areaofann ADD CONSTRAINT ur_landusedi_areaofannua_fk_2 FOREIGN KEY (areaofannualdiversions_id)
REFERENCES ur_areaofannualdiversions (id)
ON DELETE CASCADE;

ALTER TABLE ur_landusedi_to_areaofann ADD CONSTRAINT ur_landusedi_landusedive_fk_4 FOREIGN KEY (landusediversion_id)
REFERENCES ur_landusediversion (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_statistic_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_statistic_to_keyvaluep ADD CONSTRAINT ur_statistic_keyvaluepai_fk_2 FOREIGN KEY (keyvaluepair_id)
REFERENCES ur_keyvaluepair (id)
ON DELETE CASCADE;

ALTER TABLE ur_statistic_to_keyvaluep ADD CONSTRAINT ur_statistic_statistical_fk_2 FOREIGN KEY (statisticalgrid_id)
REFERENCES ur_statisticalgrid (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_publictra_to_point 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictra_to_point ADD CONSTRAINT ur_publictra_point_id_fk_2 FOREIGN KEY (point_id)
REFERENCES ur_point (id)
ON DELETE CASCADE;

ALTER TABLE ur_publictra_to_point ADD CONSTRAINT ur_publictra_publictrans_fk_2 FOREIGN KEY (publictransit_id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_buildingd ADD CONSTRAINT ur_building__buildingdis_fk_2 FOREIGN KEY (buildingdisasterriskat_id)
REFERENCES ur_buildingdisasterriskat (id)
ON DELETE CASCADE;

ALTER TABLE ur_building_to_buildingd ADD CONSTRAINT ur_building__building_id_fk_2 FOREIGN KEY (building_id)
REFERENCES ur_building (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_keyvaluep ADD CONSTRAINT ur_building__keyvaluepai_fk_2 FOREIGN KEY (keyvaluepairattribute_id)
REFERENCES ur_keyvaluepairattribute (id)
ON DELETE CASCADE;

ALTER TABLE ur_building_to_keyvaluep ADD CONSTRAINT ur_building__building_id_fk_4 FOREIGN KEY (building_id)
REFERENCES ur_building (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody_to_waterbody ADD CONSTRAINT ur_waterbody_waterbodyat_fk_2 FOREIGN KEY (waterbodyattribute_id)
REFERENCES ur_waterbodyattribute (id)
ON DELETE CASCADE;

ALTER TABLE ur_waterbody_to_waterbody ADD CONSTRAINT ur_waterbody_waterbody_i_fk_2 FOREIGN KEY (waterbody_id)
REFERENCES ur_waterbody (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjec_to_publictra ADD CONSTRAINT ur_cityobjec_publictrans_fk_2 FOREIGN KEY (publictransitdatatype_id)
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

ALTER TABLE ur_cityobjec_to_publictra ADD CONSTRAINT ur_cityobjec_cityobjectg_fk_2 FOREIGN KEY (cityobjectgroup_3_id)
REFERENCES ur_cityobjectgroup_3 (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_districtd 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractd_to_districtd ADD CONSTRAINT ur_abstractd_abstractdis_fk_3 FOREIGN KEY (abstractdistrictplan_id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

ALTER TABLE ur_abstractd_to_districtd ADD CONSTRAINT ur_abstractd_districtdev_fk_2 FOREIGN KEY (districtdevelopmentpla_id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractd_to_zone ADD CONSTRAINT ur_abstractd_abstractdis_fk_4 FOREIGN KEY (abstractdistrictplan_id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

ALTER TABLE ur_abstractd_to_zone ADD CONSTRAINT ur_abstractd_zone_id_fk_2 FOREIGN KEY (zone_id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractdistrictplan ADD CONSTRAINT ur_abstractdist_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_abstractdistrictplan ADD CONSTRAINT ur_abstractdistrictplan_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_areaclassification ADD CONSTRAINT ur_areaclassification_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_boundary 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_boundary ADD CONSTRAINT ur_boundary_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_censusblock ADD CONSTRAINT ur_censusblock_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilities_2 ADD CONSTRAINT ur_collectivefacilities_2_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_3 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilities_3 ADD CONSTRAINT ur_collectivefacilities_3_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilitiesfo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilitiesfo ADD CONSTRAINT ur_collectivefacilitiesfo_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivegovernmentan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivegovernmentan ADD CONSTRAINT ur_collectivegovernmentan_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivehousingfacil 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivehousingfacil ADD CONSTRAINT ur_collectivehousingfacil_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectiveurbandisaste 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectiveurbandisaste ADD CONSTRAINT ur_collectiveurbandisaste_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_developmentproject ADD CONSTRAINT ur_developmentproject_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterdamage ADD CONSTRAINT ur_disasterdamage_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterp_to_zonaldisa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterp_to_zonaldisa ADD CONSTRAINT ur_disasterp_disasterpre_fk_2 FOREIGN KEY (disasterpreventionbl_2_id)
REFERENCES ur_disasterpreventionbl_2 (id)
ON DELETE CASCADE;

ALTER TABLE ur_disasterp_to_zonaldisa ADD CONSTRAINT ur_disasterp_zonaldisast_fk_2 FOREIGN KEY (zonaldisasterpreventio_id)
REFERENCES ur_zonaldisasterpreventio (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbase 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbase ADD CONSTRAINT ur_disasterpreventionbase_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbl_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbl_2 ADD CONSTRAINT ur_disasterpreventionbl_2_fk_2 FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbloc 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbloc ADD CONSTRAINT ur_disasterpreventionbloc_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesspa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_distributionbusinesspa ADD CONSTRAINT ur_distributionbusinesspa_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesszo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_distributionbusinesszo ADD CONSTRAINT ur_distributionbusinesszo_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_district 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_district ADD CONSTRAINT ur_district_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_district 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtd_to_district ADD CONSTRAINT ur_districtd_district_id_fk_2 FOREIGN KEY (district_id)
REFERENCES ur_district (id)
ON DELETE CASCADE;

ALTER TABLE ur_districtd_to_district ADD CONSTRAINT ur_districtd_districtdev_fk_3 FOREIGN KEY (districtdevelopmentpla_id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtd_to_zone ADD CONSTRAINT ur_districtd_districtdev_fk_4 FOREIGN KEY (districtdevelopmentpla_id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

ALTER TABLE ur_districtd_to_zone ADD CONSTRAINT ur_districtd_zone_id_fk_2 FOREIGN KEY (zone_id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtdevelopmentpla ADD CONSTRAINT ur_districtdeve_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_districtdevelopmentpla ADD CONSTRAINT ur_districtdevelopmentpla_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtplan ADD CONSTRAINT ur_districtplan_fk_2 FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtsandzones ADD CONSTRAINT ur_districtsand_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_districtsandzones ADD CONSTRAINT ur_districtsandzones_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_exceptionalfloorareara 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_exceptionalfloorareara ADD CONSTRAINT ur_exceptionalfloorareara_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_globalhubcitydevelopme 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_globalhubcitydevelopme ADD CONSTRAINT ur_globalhubcitydevelopme_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_heightcontroldistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_heightcontroldistrict ADD CONSTRAINT ur_heightcontroldistrict_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_highlevelusedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_highlevelusedistrict ADD CONSTRAINT ur_highlevelusedistrict_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_highriseresidentialatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_highriseresidentialatt ADD CONSTRAINT ur_highriseresidentialatt_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_historicscenerymainten 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_historicscenerymainten ADD CONSTRAINT ur_historicscenerymainten_fk_2 FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_industrialparkdevelopm 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_industrialparkdevelopm ADD CONSTRAINT ur_industrialparkdevelopm_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landreadjustmentprojec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landreadjustmentprojec ADD CONSTRAINT ur_landreadjustmentprojec_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landscapezone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landscapezone ADD CONSTRAINT ur_landscapezone_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_newhousingandurbandeve 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_newhousingandurbandeve ADD CONSTRAINT ur_newhousingandurbandeve_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_newurbaninfrastructure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_newurbaninfrastructure ADD CONSTRAINT ur_newurbaninfrastructure_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds_2 ADD CONSTRAINT ur_numberofh_censusblock_fk_3 FOREIGN KEY (censusblock_numberofhouse__2)
REFERENCES ur_censusblock (id);

ALTER TABLE ur_numberofhouseholds_2 ADD CONSTRAINT ur_numberofh_censusblock_fk_4 FOREIGN KEY (censusblock_numberofhouse_id)
REFERENCES ur_censusblock (id);

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_openspaceforpublicuse ADD CONSTRAINT ur_openspaceforpublicuse_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_openspaceforpublicuse ADD CONSTRAINT ur_openspace_parkattribu_fk_2 FOREIGN KEY (parkattribute_id)
REFERENCES ur_parkattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pollution ADD CONSTRAINT ur_pollution_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_portzone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portzone ADD CONSTRAINT ur_portzone_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_privateurbanrenewalpro 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_privateurbanrenewalpro ADD CONSTRAINT ur_privateurbanrenewalpro_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_productivegreenzone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_productivegreenzone ADD CONSTRAINT ur_productivegreenzone_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_projectpromotionarea ADD CONSTRAINT ur_projectpromo_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_projectpromotionarea ADD CONSTRAINT ur_projectpromotionarea_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_publictransitfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitfacility ADD CONSTRAINT ur_publictransitfacility_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_quasiurbanplanningarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_quasiurbanplanningarea ADD CONSTRAINT ur_quasiurbanplanningarea_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_recreations ADD CONSTRAINT ur_recreations_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_residentialblockconstr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_residentialblockconstr ADD CONSTRAINT ur_residentialblockconstr_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_residentialenvironment 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_residentialenvironment ADD CONSTRAINT ur_residentialenvironment_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictimprov 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadsidedistrictimprov ADD CONSTRAINT ur_roadsidedistrictimprov_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadsidedistrictplan ADD CONSTRAINT ur_roadsidedistrictplan_fk_2 FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_ruraldistrictimproveme 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ruraldistrictimproveme ADD CONSTRAINT ur_ruraldistrictimproveme_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_scenicdistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_scenicdistrict ADD CONSTRAINT ur_scenicdistrict_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_scheduledareaforurband ADD CONSTRAINT ur_scheduledare_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_scheduledareaforurband ADD CONSTRAINT ur_scheduledareaforurband_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_sedimentdisasterpronea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_sedimentdisasterpronea ADD CONSTRAINT ur_sedimentdisasterpronea_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialgreenspaceconse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialgreenspaceconse ADD CONSTRAINT ur_specialgreenspaceconse_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialurbanrenaissanc 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialurbanrenaissanc ADD CONSTRAINT ur_specialurbanrenaissanc_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialuseattractiondi 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialuseattractiondi ADD CONSTRAINT ur_specialuseattractiondi_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialusedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialusedistrict ADD CONSTRAINT ur_specialusedistrict_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialuserestrictiond 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialuserestrictiond ADD CONSTRAINT ur_specialuserestrictiond_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specified_to_globalhub 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specified_to_globalhub ADD CONSTRAINT ur_specified_globalhubci_fk_2 FOREIGN KEY (globalhubcitydevelopme_id)
REFERENCES ur_globalhubcitydevelopme (id)
ON DELETE CASCADE;

ALTER TABLE ur_specified_to_globalhub ADD CONSTRAINT ur_specified_specifiedur_fk_2 FOREIGN KEY (specifiedurgenturbanre_id)
REFERENCES ur_specifiedurgenturbanre (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specifiedblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifiedblock ADD CONSTRAINT ur_specifiedblock_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specifieddisasterpreve 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifieddisasterpreve ADD CONSTRAINT ur_specifieddisasterpreve_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specifiedurgenturbanre 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifiedurgenturbanre ADD CONSTRAINT ur_specifiedurgenturbanre_fk_2 FOREIGN KEY (id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_supplyfacility ADD CONSTRAINT ur_supplyfacility_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_supplyfacility ADD CONSTRAINT ur_supplyfac_waterworksa_fk_2 FOREIGN KEY (waterworksattribute_id)
REFERENCES ur_waterworksattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_threedimensionalextent 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_threedimensionalextent ADD CONSTRAINT ur_threedimensionalextent_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfacility_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_parkingplac_fk_2 FOREIGN KEY (parkingplaceattribute_id)
REFERENCES ur_parkingplaceattribute (id)
ON DELETE SET NULL;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_urbanrapidt_fk_2 FOREIGN KEY (urbanrapidtransitrailroad_id)
REFERENCES ur_urbanrapidtransitrailr (id)
ON DELETE SET NULL;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_urbanroadat_fk_2 FOREIGN KEY (urbanroadattribute_id)
REFERENCES ur_urbanroadattribute (id)
ON DELETE SET NULL;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_vehicleterm_fk_2 FOREIGN KEY (vehicleterminalattribute_id)
REFERENCES ur_vehicleterminalattribu (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_treatmentfacility ADD CONSTRAINT ur_treatmentfacility_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_treatmentfacility ADD CONSTRAINT ur_treatment_sewersystem_fk_2 FOREIGN KEY (sewersystemsattribute_id)
REFERENCES ur_sewersystemattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_treeplantingdistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_treeplantingdistrict ADD CONSTRAINT ur_treeplantingdistrict_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbandevelopmentprojec ADD CONSTRAINT ur_urbandevelop_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbandevelopmentprojec ADD CONSTRAINT ur_urbandevelopmentprojec_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbandisasterrecoveryp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbandisasterrecoveryp ADD CONSTRAINT ur_urbandisasterrecoveryp_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfaci_to_threedime 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfaci_to_threedime ADD CONSTRAINT ur_urbanfaci_threedimens_fk_2 FOREIGN KEY (threedimensionalextent_id)
REFERENCES ur_threedimensionalextent (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanfaci_to_threedime ADD CONSTRAINT ur_urbanfaci_urbanfacili_fk_2 FOREIGN KEY (urbanfacility_id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfacility ADD CONSTRAINT ur_urbanfacilit_objectclass__3 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbanfacility ADD CONSTRAINT ur_urbanfacility_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfacilitystipulate ADD CONSTRAINT ur_urbanfacilit_objectclass__4 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbanfacilitystipulate ADD CONSTRAINT ur_urbanfacilitystipulate_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunc_to_cityobjec ADD CONSTRAINT ur_urbanfunc_cityobject__fk_2 FOREIGN KEY (cityobject_id)
REFERENCES cityobject (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanfunc_to_cityobjec ADD CONSTRAINT ur_urbanfunc_urbanfuncti_fk_2 FOREIGN KEY (urbanfunction_id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunctio_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunction_fk_2 FOREIGN KEY (id)
REFERENCES cityobject (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod0multisu_fk_2 FOREIGN KEY (lod0multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod1multisu_fk_2 FOREIGN KEY (lod1multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod_1multis_fk_2 FOREIGN KEY (lod_1multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod_2multis_fk_2 FOREIGN KEY (lod_2multisurface_id)
REFERENCES surface_geometry (id);

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanization ADD CONSTRAINT ur_urbanization_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanplanningarea ADD CONSTRAINT ur_urbanplanningarea_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanrapi_to_structure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanrapi_to_structure ADD CONSTRAINT ur_urbanrapi_structurede_fk_2 FOREIGN KEY (structuredetails_id)
REFERENCES ur_structuredetails (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanrapi_to_structure ADD CONSTRAINT ur_urbanrapi_urbanrapidt_fk_2 FOREIGN KEY (urbanrapidtransitrailr_id)
REFERENCES ur_urbanrapidtransitrailr (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentproj 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanredevelopmentproj ADD CONSTRAINT ur_urbanredevelopmentproj_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentprom 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanredevelopmentprom ADD CONSTRAINT ur_urbanredevelopmentprom_fk_2 FOREIGN KEY (id)
REFERENCES ur_projectpromotionarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanrenewalproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanrenewalproject ADD CONSTRAINT ur_urbanrenewalproject_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanroad_to_structure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanroad_to_structure ADD CONSTRAINT ur_urbanroad_structurede_fk_2 FOREIGN KEY (structuredetails_id)
REFERENCES ur_structuredetails (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanroad_to_structure ADD CONSTRAINT ur_urbanroad_urbanroadat_fk_2 FOREIGN KEY (urbanroadattribute_id)
REFERENCES ur_urbanroadattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_privateur 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_privateur ADD CONSTRAINT ur_urgenturb_privateurba_fk_2 FOREIGN KEY (privateurbanrenewalpro_id)
REFERENCES ur_privateurbanrenewalpro (id)
ON DELETE CASCADE;

ALTER TABLE ur_urgenturb_to_privateur ADD CONSTRAINT ur_urgenturb_urgenturban_fk_4 FOREIGN KEY (urgenturbanrenewalarea_id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specialur 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_specialur ADD CONSTRAINT ur_urgenturb_specialurba_fk_2 FOREIGN KEY (specialurbanrenaissanc_id)
REFERENCES ur_specialurbanrenaissanc (id)
ON DELETE CASCADE;

ALTER TABLE ur_urgenturb_to_specialur ADD CONSTRAINT ur_urgenturb_urgenturban_fk_5 FOREIGN KEY (urgenturbanrenewalarea_id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specified 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_specified ADD CONSTRAINT ur_urgenturb_specifiedur_fk_2 FOREIGN KEY (specifiedurgenturbanre_id)
REFERENCES ur_specifiedurgenturbanre (id)
ON DELETE CASCADE;

ALTER TABLE ur_urgenturb_to_specified ADD CONSTRAINT ur_urgenturb_urgenturban_fk_6 FOREIGN KEY (urgenturbanrenewalarea_id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturbanrenewalarea ADD CONSTRAINT ur_urgenturbanr_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urgenturbanrenewalarea ADD CONSTRAINT ur_urgenturbanrenewalarea_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_usedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_usedistrict ADD CONSTRAINT ur_usedistrict_fk_2 FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterway ADD CONSTRAINT ur_waterway_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_zonaldisasterpreventio 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zonaldisasterpreventio ADD CONSTRAINT ur_zonaldisasterpreventio_fk_2 FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone ADD CONSTRAINT ur_zone_objectclass_fk_2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_zone ADD CONSTRAINT ur_zone_fk_2 FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_zone_to_boundary 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone_to_boundary ADD CONSTRAINT ur_zone_to_b_boundary_id_fk_2 FOREIGN KEY (boundary_id)
REFERENCES ur_boundary (id)
ON DELETE CASCADE;

ALTER TABLE ur_zone_to_boundary ADD CONSTRAINT ur_zone_to_b_zone_id_fk_2 FOREIGN KEY (zone_id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

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
-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_land_use_landusedet_fkx ON ur_land_use (landusedetailattribute_id);

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_numbe_house_numbe_fkx_1 ON ur_numberofhouseholds (households_numberofhous_id_1);

CREATE INDEX ur_number_househ_numbe_fkx ON ur_numberofhouseholds (households_numberofhouseh_id);

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

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_publictra_objectcla_fkx ON ur_publictransit (objectclass_id);

CREATE INDEX ur_publictransi_target_fkx ON ur_publictransit (target_id);

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
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
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_waterbody_objectcla_fkx ON ur_waterbodyattribute (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_populatio_to_populatio 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_populatio_population_fkx_3 ON ur_populatio_to_populatio (populationbyageandsex_id);

CREATE INDEX ur_populatio_population_fkx_4 ON ur_populatio_to_populatio (population_id);

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landprice_landpricep_fkx_2 ON ur_landprice_to_landprice (landpriceperlanduse_id);

CREATE INDEX ur_landprice_landprice__fkx_2 ON ur_landprice_to_landprice (landprice_id);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landusedi_numberofan_fkx_2 ON ur_landusedi_to_numberofa (numberofannualdiversio_id);

CREATE INDEX ur_landusedi_landusediv_fkx_2 ON ur_landusedi_to_numberofa (landusediversion_id);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landusedi_areaofannu_fkx_2 ON ur_landusedi_to_areaofann (areaofannualdiversions_id);

CREATE INDEX ur_landusedi_landusediv_fkx_4 ON ur_landusedi_to_areaofann (landusediversion_id);

-- -------------------------------------------------------------------- 
-- ur_statistic_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_statistic_keyvaluepa_fkx_2 ON ur_statistic_to_keyvaluep (keyvaluepair_id);

CREATE INDEX ur_statistic_statistica_fkx_2 ON ur_statistic_to_keyvaluep (statisticalgrid_id);

-- -------------------------------------------------------------------- 
-- ur_publictra_to_point 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_publictra_point_id_fkx_2 ON ur_publictra_to_point (point_id);

CREATE INDEX ur_publictra_publictran_fkx_2 ON ur_publictra_to_point (publictransit_id);

-- -------------------------------------------------------------------- 
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_building__buildingdi_fkx_2 ON ur_building_to_buildingd (buildingdisasterriskat_id);

CREATE INDEX ur_building__building_i_fkx_2 ON ur_building_to_buildingd (building_id);

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_building__keyvaluepa_fkx_2 ON ur_building_to_keyvaluep (keyvaluepairattribute_id);

CREATE INDEX ur_building__building_i_fkx_4 ON ur_building_to_keyvaluep (building_id);

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_waterbody_waterbodya_fkx_2 ON ur_waterbody_to_waterbody (waterbodyattribute_id);

CREATE INDEX ur_waterbody_waterbody__fkx_2 ON ur_waterbody_to_waterbody (waterbody_id);

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_cityobjec_publictran_fkx_2 ON ur_cityobjec_to_publictra (publictransitdatatype_id);

CREATE INDEX ur_cityobjec_cityobject_fkx_2 ON ur_cityobjec_to_publictra (cityobjectgroup_3_id);

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_abstractd_objectclas_fkx_2 ON ur_abstractdistrictplan (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_districtd_objectclas_fkx_2 ON ur_districtdevelopmentpla (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_districts_objectclas_fkx_2 ON ur_districtsandzones (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_numberofh_censusbloc_fkx_3 ON ur_numberofhouseholds_2 (censusblock_numberofhouse__2);

CREATE INDEX ur_numberofh_censusbloc_fkx_4 ON ur_numberofhouseholds_2 (censusblock_numberofhouse_id);

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_openspace_parkattrib_fkx_2 ON ur_openspaceforpublicuse (parkattribute_id);

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_projectpr_objectclas_fkx_2 ON ur_projectpromotionarea (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_scheduled_objectclas_fkx_2 ON ur_scheduledareaforurband (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_supplyfac_waterworks_fkx_2 ON ur_supplyfacility (waterworksattribute_id);

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_trafficfa_parkingpla_fkx_2 ON ur_trafficfacility (parkingplaceattribute_id);

CREATE INDEX ur_trafficfa_urbanrapid_fkx_2 ON ur_trafficfacility (urbanrapidtransitrailroad_id);

CREATE INDEX ur_trafficfa_urbanroada_fkx_2 ON ur_trafficfacility (urbanroadattribute_id);

CREATE INDEX ur_trafficfa_vehicleter_fkx_2 ON ur_trafficfacility (vehicleterminalattribute_id);

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_treatment_sewersyste_fkx_2 ON ur_treatmentfacility (sewersystemsattribute_id);

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbandeve_objectclas_fkx_2 ON ur_urbandevelopmentprojec (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfaci_objectclas_fkx_3 ON ur_urbanfacility (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfaci_objectclas_fkx_4 ON ur_urbanfacilitystipulate (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD0MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD0MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod0multic_spx_2 ON ur_urbanfunction (lod0multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD0MULTIPOINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD0MULTIPOINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod0multip_spx_2 ON ur_urbanfunction (lod0multipoint) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_urbanfunc_lod0multis_fkx_2 ON ur_urbanfunction (lod0multisurface_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD1MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD1MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod1multic_spx_2 ON ur_urbanfunction (lod1multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_urbanfunc_lod1multis_fkx_2 ON ur_urbanfunction (lod1multisurface_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_1MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_1MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod_1multi_spx_3 ON ur_urbanfunction (lod_1multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_1MULTIPOINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_1MULTIPOINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod_1multi_spx_4 ON ur_urbanfunction (lod_1multipoint) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_urbanfunc_lod_1multi_fkx_2 ON ur_urbanfunction (lod_1multisurface_id);

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_2MULTICURVE';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_2MULTICURVE',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod_2multi_spx_3 ON ur_urbanfunction (lod_2multicurve) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

DELETE FROM USER_SDO_GEOM_METADATA WHERE TABLE_NAME='UR_URBANFUNCTION' AND COLUMN_NAME='LOD_2MULTIPOINT';
INSERT INTO USER_SDO_GEOM_METADATA (TABLE_NAME, COLUMN_NAME, DIMINFO, SRID)
VALUES ('UR_URBANFUNCTION','LOD_2MULTIPOINT',
MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 0.000, 10000000.000, 0.0005), MDSYS.SDO_DIM_ELEMENT('Y', 0.000, 10000000.000, 0.0005),MDSYS.SDO_DIM_ELEMENT('Z', -1000, 10000, 0.0005)), &SRSNO);
CREATE INDEX ur_urbanfunc_lod_2multi_spx_4 ON ur_urbanfunction (lod_2multipoint) INDEXTYPE IS MDSYS.SPATIAL_INDEX;

CREATE INDEX ur_urbanfunc_lod_2multi_fkx_2 ON ur_urbanfunction (lod_2multisurface_id);

CREATE INDEX ur_urbanfunc_objectclas_fkx_2 ON ur_urbanfunction (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urgenturb_objectclas_fkx_2 ON ur_urgenturbanrenewalarea (objectclass_id);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_zone_objectclas_fkx_2 ON ur_zone (objectclass_id);

