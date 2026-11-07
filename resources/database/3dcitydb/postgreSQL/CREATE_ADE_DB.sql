-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2026-11-07 11:28:53 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Sequences *********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

CREATE SEQUENCE ur_areaofannualdivers_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_point_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_numberofhouseholds_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_keyvaluepair_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_trafficareaattribu_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_cityfurnitureattri_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_description_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_publictransitdatat_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_numberofannualdive_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_waterbodyattribute_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_landuseattribute_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_roadattribute_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_landpriceperlandus_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_keyvaluepairattrib_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_buildingattribute_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_legalgrounds_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_vegetationattribut_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_populationbyageand_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_numberofhousehol_seq_1
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create tables ************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_agency
(
    id BIGINT NOT NULL,
    address VARCHAR(1000),
    email VARCHAR(1000),
    fareurl VARCHAR(1000),
    language VARCHAR(1000),
    language_codespace VARCHAR(1000),
    name VARCHAR(1000),
    officialname VARCHAR(1000),
    phone VARCHAR(1000),
    presidentname VARCHAR(1000),
    presidentposition VARCHAR(1000),
    timezone VARCHAR(1000),
    timezone_codespace VARCHAR(1000),
    url VARCHAR(1000),
    zipnumber VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_areaofannualdiversions
(
    id BIGINT NOT NULL DEFAULT nextval('ur_areaofannualdivers_seq'::regclass) NOT NULL,
    area NUMERIC,
    area_uom VARCHAR(1000),
    landusediver_areaofannual_id BIGINT,
    year DATE,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_attribution
(
    id BIGINT NOT NULL,
    agency_id BIGINT,
    email VARCHAR(1000),
    isauthority NUMERIC,
    isoperator NUMERIC,
    isproducer NUMERIC,
    organizationname VARCHAR(1000),
    phonenumber VARCHAR(1000),
    route_id BIGINT,
    trip_id BIGINT,
    url VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_building
(
    id BIGINT NOT NULL,
    buildingdataqualityattrib_id BIGINT,
    buildingdetailattribute_id BIGINT,
    buildingidattribute_id BIGINT,
    largecustomerfacilityattr_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_buildingattribute_seq'::regclass) NOT NULL,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingdataqualityatt 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingdataqualityatt
(
    id BIGINT NOT NULL,
    appearancesrcdesc VARCHAR(1000),
    appearancesrcdesc_codespace VARCHAR(1000),
    geometrysrcdesc VARCHAR(1000),
    geometrysrcdesc_codespace VARCHAR(1000),
    lod1heighttype VARCHAR(1000),
    lod1heighttype_codespace VARCHAR(1000),
    lodtype VARCHAR(1000),
    srcscale VARCHAR(1000),
    srcscale_codespace VARCHAR(1000),
    thematicsrcdesc VARCHAR(1000),
    thematicsrcdesc_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingdetailattribut
(
    id BIGINT NOT NULL,
    areaclassification_codespace VARCHAR(1000),
    areaclassificationtype VARCHAR(1000),
    basementfirstusage VARCHAR(1000),
    basementfirstusage_codespace VARCHAR(1000),
    basementsecondusag_codespace VARCHAR(1000),
    basementsecondusage VARCHAR(1000),
    basementusage VARCHAR(1000),
    basementusage_codespace VARCHAR(1000),
    buildingcoveragerate NUMERIC,
    buildingfootprintarea NUMERIC,
    buildingfootprintarea_uom VARCHAR(1000),
    buildingheight NUMERIC,
    buildingheight_uom VARCHAR(1000),
    buildingroofedgearea NUMERIC,
    buildingroofedgearea_uom VARCHAR(1000),
    buildingstructureo_codespace VARCHAR(1000),
    buildingstructureorgtype VARCHAR(1000),
    buildingstructuret_codespace VARCHAR(1000),
    buildingstructuretype VARCHAR(1000),
    detailedusage VARCHAR(1000),
    detailedusage2 VARCHAR(1000),
    detailedusage2_codespace VARCHAR(1000),
    detailedusage3 VARCHAR(1000),
    detailedusage3_codespace VARCHAR(1000),
    detailedusage_codespace VARCHAR(1000),
    developmentarea NUMERIC,
    developmentarea_uom VARCHAR(1000),
    districtsandzonest_codespace VARCHAR(1000),
    districtsandzonestype VARCHAR(1000),
    eaveheight NUMERIC,
    eaveheight_uom VARCHAR(1000),
    fireproofstructure_codespace VARCHAR(1000),
    fireproofstructuretype VARCHAR(1000),
    floorarearate NUMERIC,
    groundfloorusage VARCHAR(1000),
    groundfloorusage_codespace VARCHAR(1000),
    implementingbody VARCHAR(1000),
    landusetype VARCHAR(1000),
    landusetype_codespace VARCHAR(1000),
    majorusage VARCHAR(1000),
    majorusage2 VARCHAR(1000),
    majorusage2_codespace VARCHAR(1000),
    majorusage_codespace VARCHAR(1000),
    note VARCHAR(1000),
    orgusage VARCHAR(1000),
    orgusage2 VARCHAR(1000),
    orgusage2_codespace VARCHAR(1000),
    orgusage_codespace VARCHAR(1000),
    reference VARCHAR(1000),
    secondfloorusage VARCHAR(1000),
    secondfloorusage_codespace VARCHAR(1000),
    serialnumberofbuildingcertif VARCHAR(1000),
    sitearea NUMERIC,
    sitearea_uom VARCHAR(1000),
    specifiedbuildingcoveragerat NUMERIC,
    specifiedfloorarearate NUMERIC,
    standardfloorarearate NUMERIC,
    surveyyear DATE,
    thirdfloorusage VARCHAR(1000),
    thirdfloorusage_codespace VARCHAR(1000),
    totalfloorarea NUMERIC,
    totalfloorarea_uom VARCHAR(1000),
    urbanplantype VARCHAR(1000),
    urbanplantype_codespace VARCHAR(1000),
    vacancy VARCHAR(1000),
    vacancy_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingdisasterriskat
(
    id BIGINT NOT NULL,
    areatype VARCHAR(1000),
    areatype_codespace VARCHAR(1000),
    building_buildingdisaster_id BIGINT,
    description VARCHAR(1000),
    description_codespace VARCHAR(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingfloodingriskat
(
    id BIGINT NOT NULL,
    depth NUMERIC,
    depth_uom VARCHAR(1000),
    objectclass_id INTEGER,
    rank VARCHAR(1000),
    rank_codespace VARCHAR(1000),
    rankorg VARCHAR(1000),
    rankorg_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingidattribute
(
    id BIGINT NOT NULL,
    branchid INTEGER,
    buildingid VARCHAR(1000),
    city VARCHAR(1000),
    city_codespace VARCHAR(1000),
    partid INTEGER,
    prefecture VARCHAR(1000),
    prefecture_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_buildingriverfloodingr 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_buildingriverfloodingr
(
    id BIGINT NOT NULL,
    admintype VARCHAR(1000),
    admintype_codespace VARCHAR(1000),
    duration NUMERIC,
    duration_uom VARCHAR(1000),
    scale VARCHAR(1000),
    scale_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_calendar
(
    id BIGINT NOT NULL,
    enddate TIMESTAMP WITH TIME ZONE,
    friday NUMERIC,
    monday NUMERIC,
    saturday NUMERIC,
    startdate TIMESTAMP WITH TIME ZONE,
    sunday NUMERIC,
    thursday NUMERIC,
    tuesday NUMERIC,
    wednesday NUMERIC,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_calendardate
(
    id BIGINT NOT NULL,
    calendar_id BIGINT,
    date_ TIMESTAMP WITH TIME ZONE,
    exceptiontype VARCHAR(1000),
    exceptiontype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_censusblock
(
    id BIGINT NOT NULL,
    daytimepopulation INTEGER,
    daytimepopulationdensity NUMERIC,
    numberofmainhouseholds INTEGER,
    numberofordinaryhouseholds INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_city_furniture
(
    id BIGINT NOT NULL,
    cityfurnituredataqualitya_id BIGINT,
    cityfurnituredetailattrib_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityfurnitureattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_cityfurnitureattri_seq'::regclass) NOT NULL,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredataquali 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityfurnituredataquali
(
    id BIGINT NOT NULL,
    appearancesrcdesc VARCHAR(1000),
    appearancesrcdesc_codespace VARCHAR(1000),
    geometrysrcdesc VARCHAR(1000),
    geometrysrcdesc_codespace VARCHAR(1000),
    lodtype VARCHAR(1000),
    srcscale VARCHAR(1000),
    srcscale_codespace VARCHAR(1000),
    thematicsrcdesc VARCHAR(1000),
    thematicsrcdesc_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredetailatt 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityfurnituredetailatt
(
    id BIGINT NOT NULL,
    description VARCHAR(1000),
    facilitytype VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobject
(
    id BIGINT NOT NULL,
    vegetationdataqualityattr_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup
(
    id BIGINT NOT NULL,
    fiscalyearofpublication DATE,
    language VARCHAR(1000),
    language_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_1 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup_1
(
    id BIGINT NOT NULL,
    fiscalyearofpublication DATE,
    language VARCHAR(1000),
    language_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup_2
(
    id BIGINT NOT NULL,
    fiscalyearofpublication DATE,
    language VARCHAR(1000),
    language_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_3 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjectgroup_3
(
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_description 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_description
(
    id BIGINT NOT NULL DEFAULT nextval('ur_description_seq'::regclass) NOT NULL,
    description VARCHAR(1000),
    frequencyofservice INTEGER,
    numberofcustomers INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_developmentproject
(
    id BIGINT NOT NULL,
    benefitarea NUMERIC,
    benefitarea_uom VARCHAR(1000),
    benefitperiod VARCHAR(1000),
    completedarea NUMERIC,
    completedarea_uom VARCHAR(1000),
    cost INTEGER,
    dateofdecision TIMESTAMP WITH TIME ZONE,
    dateofdesignationfortemporar TIMESTAMP WITH TIME ZONE,
    mainpurpose VARCHAR(1000),
    mainpurpose_codespace VARCHAR(1000),
    ongoingarea NUMERIC,
    ongoingarea_uom VARCHAR(1000),
    plannedarea NUMERIC,
    plannedarea_uom VARCHAR(1000),
    status VARCHAR(1000),
    status_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterdamage
(
    id BIGINT NOT NULL,
    damagedarea NUMERIC,
    damagedarea_uom VARCHAR(1000),
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
    id BIGINT NOT NULL,
    agency_id BIGINT,
    currencytype VARCHAR(1000),
    currencytype_codespace VARCHAR(1000),
    paymentmethod VARCHAR(1000),
    paymentmethod_codespace VARCHAR(1000),
    price NUMERIC,
    transferduration INTEGER,
    transfers VARCHAR(1000),
    transfers_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_farerule
(
    id BIGINT NOT NULL,
    containsid VARCHAR(1000),
    containsid_codespace VARCHAR(1000),
    destinationid VARCHAR(1000),
    destinationid_codespace VARCHAR(1000),
    fare_id BIGINT,
    originid VARCHAR(1000),
    originid_codespace VARCHAR(1000),
    route_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_feedinfo 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_feedinfo
(
    id BIGINT NOT NULL,
    contactemail VARCHAR(1000),
    contacturl VARCHAR(1000),
    defaultlanguage VARCHAR(1000),
    defaultlanguage_codespace VARCHAR(1000),
    detailedinfo VARCHAR(1000),
    enddate TIMESTAMP WITH TIME ZONE,
    language VARCHAR(1000),
    language_codespace VARCHAR(1000),
    publishername VARCHAR(1000),
    publisherurl VARCHAR(1000),
    startdate TIMESTAMP WITH TIME ZONE,
    version VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_frequency
(
    id BIGINT NOT NULL,
    endtime TIMESTAMP WITH TIME ZONE,
    exacttimes VARCHAR(1000),
    exacttimes_codespace VARCHAR(1000),
    headwaysecs INTEGER,
    starttime TIMESTAMP WITH TIME ZONE,
    trip_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_households
(
    id BIGINT NOT NULL,
    numberofmainhousehold INTEGER,
    numberofordinaryhousehold INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_keyvaluepair
(
    id BIGINT NOT NULL DEFAULT nextval('ur_keyvaluepair_seq'::regclass) NOT NULL,
    codevalue VARCHAR(1000),
    codevalue_codespace VARCHAR(1000),
    datevalue TIMESTAMP WITH TIME ZONE,
    doublevalue NUMERIC,
    intvalue INTEGER,
    key VARCHAR(1000),
    key_codespace VARCHAR(1000),
    measuredvalue NUMERIC,
    measuredvalue_uom VARCHAR(1000),
    statisticalg_genericvalue_id BIGINT,
    stringvalue VARCHAR(1000),
    urivalue VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_keyvaluepairattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_keyvaluepairattrib_seq'::regclass) NOT NULL,
    building_keyvaluepairattr_id BIGINT,
    codevalue VARCHAR(1000),
    codevalue_codespace VARCHAR(1000),
    datevalue TIMESTAMP WITH TIME ZONE,
    doublevalue NUMERIC,
    intvalue INTEGER,
    key VARCHAR(1000),
    key_codespace VARCHAR(1000),
    measuredvalue NUMERIC,
    measuredvalue_uom VARCHAR(1000),
    stringvalue VARCHAR(1000),
    urivalue VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_land_use
(
    id BIGINT NOT NULL,
    landusedetailattribute_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landprice
(
    id BIGINT NOT NULL,
    currencyunit VARCHAR(1000),
    currencyunit_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landpriceperlanduse
(
    id BIGINT NOT NULL DEFAULT nextval('ur_landpriceperlandus_seq'::regclass) NOT NULL,
    landprice INTEGER,
    landprice_landprice_id BIGINT,
    landuse VARCHAR(1000),
    landuse_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landuseattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landuseattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_landuseattribute_seq'::regclass) NOT NULL,
    areaclassification_codespace VARCHAR(1000),
    areaclassificationtype VARCHAR(1000),
    areainha NUMERIC,
    areainha_uom VARCHAR(1000),
    areainsquaremeter NUMERIC,
    areainsquaremeter_uom VARCHAR(1000),
    buildingcoveragerate NUMERIC,
    city VARCHAR(1000),
    city_codespace VARCHAR(1000),
    districtsandzonest_codespace VARCHAR(1000),
    districtsandzonestype VARCHAR(1000),
    floorarearate NUMERIC,
    nominalarea NUMERIC,
    nominalarea_uom VARCHAR(1000),
    note VARCHAR(1000),
    orglanduse VARCHAR(1000),
    orglanduse_codespace VARCHAR(1000),
    owner VARCHAR(1000),
    ownertype VARCHAR(1000),
    ownertype_codespace VARCHAR(1000),
    prefecture VARCHAR(1000),
    prefecture_codespace VARCHAR(1000),
    reference VARCHAR(1000),
    specifiedbuildingcoveragerat NUMERIC,
    specifiedfloorarearate NUMERIC,
    standardfloorarearate NUMERIC,
    surveyyear DATE,
    urbanplantype VARCHAR(1000),
    urbanplantype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landusediversion
(
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_largecustomerfacilitya
(
    id BIGINT NOT NULL,
    areaclassification_codespace VARCHAR(1000),
    areaclassificationtype VARCHAR(1000),
    availability NUMERIC,
    capacity INTEGER,
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    districtsandzonest_codespace VARCHAR(1000),
    districtsandzonestype VARCHAR(1000),
    inauguraldate TIMESTAMP WITH TIME ZONE,
    keytenants VARCHAR(1000),
    landusetype VARCHAR(1000),
    landusetype_codespace VARCHAR(1000),
    name VARCHAR(1000),
    note VARCHAR(1000),
    owner VARCHAR(1000),
    reference VARCHAR(1000),
    surveyyear DATE,
    totalfloorarea NUMERIC,
    totalfloorarea_uom VARCHAR(1000),
    totalstorefloorarea NUMERIC,
    totalstorefloorarea_uom VARCHAR(1000),
    urbanplantype VARCHAR(1000),
    urbanplantype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_legalgrounds 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_legalgrounds
(
    id BIGINT NOT NULL DEFAULT nextval('ur_legalgrounds_seq'::regclass) NOT NULL,
    articlesofregulati_codespace VARCHAR(1000),
    articlesofregulation VARCHAR(1000),
    date_ TIMESTAMP WITH TIME ZONE,
    nameofregulation VARCHAR(1000),
    nameofregulation_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofannualdiversio
(
    id BIGINT NOT NULL DEFAULT nextval('ur_numberofannualdive_seq'::regclass) NOT NULL,
    count INTEGER,
    landusediver_numberofannu_id BIGINT,
    year DATE,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofhouseholds
(
    id BIGINT NOT NULL DEFAULT nextval('ur_numberofhouseholds_seq'::regclass) NOT NULL,
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    households_numberofhous_id_1 BIGINT,
    households_numberofhouseh_id BIGINT,
    number_ INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofhouseholds_1
(
    id BIGINT NOT NULL DEFAULT nextval('ur_numberofhousehol_seq_1'::regclass) NOT NULL,
    censusblock_numberofhou_id_1 BIGINT,
    censusblock_numberofhouse_id BIGINT,
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    number_ INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_office
(
    id BIGINT NOT NULL,
    name VARCHAR(1000),
    phone VARCHAR(1000),
    url VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_officesandemployees
(
    id BIGINT NOT NULL,
    numberofemployees INTEGER,
    numberofoffices INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_pathway
(
    id BIGINT NOT NULL,
    from_id BIGINT,
    isbidirectional VARCHAR(1000),
    isbidirectional_codespace VARCHAR(1000),
    length NUMERIC,
    length_uom VARCHAR(1000),
    maxslope NUMERIC,
    minwidth NUMERIC,
    mode_ VARCHAR(1000),
    mode_codespace VARCHAR(1000),
    reversedsignpostedas VARCHAR(1000),
    signpostedas VARCHAR(1000),
    staircount INTEGER,
    to_id BIGINT,
    traversaltime INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_point
(
    id BIGINT NOT NULL DEFAULT nextval('ur_point_seq'::regclass) NOT NULL,
    latitude NUMERIC,
    longitude NUMERIC,
    point geometry(GEOMETRYZ),
    pointdistancetraveled NUMERIC,
    pointsequence INTEGER,
    publictransit_point_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_pollution
(
    id BIGINT NOT NULL,
    cause VARCHAR(1000),
    damagedarea NUMERIC,
    damagedarea_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_population
(
    id BIGINT NOT NULL,
    births INTEGER,
    daytimepopulation INTEGER,
    daytimepopulationdensity NUMERIC,
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
    id BIGINT NOT NULL DEFAULT nextval('ur_populationbyageand_seq'::regclass) NOT NULL,
    ageandsex VARCHAR(1000),
    ageandsex_codespace VARCHAR(1000),
    number_ INTEGER,
    population_populationbyag_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransit
(
    id BIGINT NOT NULL,
    index_ NUMERIC,
    objectclass_id INTEGER,
    orgid VARCHAR(1000),
    target_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransitdatatype
(
    id BIGINT NOT NULL DEFAULT nextval('ur_publictransitdatat_seq'::regclass) NOT NULL,
    cityobjectgroup_datatype_id BIGINT,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransportationfa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransportationfa
(
    id BIGINT NOT NULL,
    companyname VARCHAR(1000),
    frequencyofservice INTEGER,
    numberofcustomers NUMERIC,
    routename VARCHAR(1000),
    sectionname VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_recreations
(
    id BIGINT NOT NULL,
    capacity INTEGER,
    numberofusers INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_roadattribute_seq'::regclass) NOT NULL,
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roaddataqualityattribu 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roaddataqualityattribu
(
    id BIGINT NOT NULL,
    appearancesrcdesc VARCHAR(1000),
    appearancesrcdesc_codespace VARCHAR(1000),
    geometrysrcdesc VARCHAR(1000),
    geometrysrcdesc_codespace VARCHAR(1000),
    lodtype VARCHAR(1000),
    srcscale VARCHAR(1000),
    srcscale_codespace VARCHAR(1000),
    thematicsrcdesc VARCHAR(1000),
    thematicsrcdesc_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadstructureattribute
(
    id BIGINT NOT NULL,
    numberoflanes INTEGER,
    sectiontype VARCHAR(1000),
    sectiontype_codespace VARCHAR(1000),
    width NUMERIC,
    width_uom VARCHAR(1000),
    widthtype VARCHAR(1000),
    widthtype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_route
(
    id BIGINT NOT NULL,
    agency_id BIGINT,
    color VARCHAR(1000),
    continuousdropoff VARCHAR(1000),
    continuousdropoff_codespace VARCHAR(1000),
    continuouspickup VARCHAR(1000),
    continuouspickup_codespace VARCHAR(1000),
    description_id BIGINT,
    destinationstop VARCHAR(1000),
    lod0multicurve geometry(GEOMETRYZ),
    longname VARCHAR(1000),
    originstop VARCHAR(1000),
    parentroute_id BIGINT,
    routesortorder INTEGER,
    shortname VARCHAR(1000),
    textcolor VARCHAR(1000),
    type VARCHAR(1000),
    type_codespace VARCHAR(1000),
    updatedate TIMESTAMP WITH TIME ZONE,
    url VARCHAR(1000),
    viastop VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_statisticalgrid
(
    id BIGINT NOT NULL,
    areaclassification_codespace VARCHAR(1000),
    areaclassificationtype VARCHAR(1000),
    availability NUMERIC,
    city VARCHAR(1000),
    city_codespace VARCHAR(1000),
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    lod_1multisurface_id BIGINT,
    lod_2multisurface_id BIGINT,
    objectclass_id INTEGER,
    prefecture VARCHAR(1000),
    prefecture_codespace VARCHAR(1000),
    surveyyear DATE,
    urbanplantype VARCHAR(1000),
    urbanplantype_codespace VARCHAR(1000),
    value TEXT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_stop
(
    id BIGINT NOT NULL,
    code VARCHAR(1000),
    code_codespace VARCHAR(1000),
    latitude NUMERIC,
    level_id BIGINT,
    locationtype VARCHAR(1000),
    locationtype_codespace VARCHAR(1000),
    longitude NUMERIC,
    parentstation_id BIGINT,
    platformcode VARCHAR(1000),
    point geometry(GEOMETRYZ),
    timezone VARCHAR(1000),
    timezone_codespace VARCHAR(1000),
    ttsname VARCHAR(1000),
    url VARCHAR(1000),
    wheelchairboarding VARCHAR(1000),
    wheelchairboarding_codespace VARCHAR(1000),
    zoneid VARCHAR(1000),
    zoneid_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_stoptime
(
    id BIGINT NOT NULL,
    arrivaltime TIMESTAMP WITH TIME ZONE,
    continuousdropofft_codespace VARCHAR(1000),
    continuousdropofftype VARCHAR(1000),
    continuouspickupty_codespace VARCHAR(1000),
    continuouspickuptype VARCHAR(1000),
    departuretime TIMESTAMP WITH TIME ZONE,
    dropofftype VARCHAR(1000),
    dropofftype_codespace VARCHAR(1000),
    headsign VARCHAR(1000),
    pickuptype VARCHAR(1000),
    pickuptype_codespace VARCHAR(1000),
    shapedistancetraveled NUMERIC,
    stop_id BIGINT,
    stopsequence INTEGER,
    timepoint VARCHAR(1000),
    timepoint_codespace VARCHAR(1000),
    trip_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_traffic_area
(
    id BIGINT NOT NULL,
    trafficareastructureattri_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trafficareaattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trafficareaattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_trafficareaattribu_seq'::regclass) NOT NULL,
    numberoflanes INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trafficvolumeattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trafficvolumeattribute
(
    id BIGINT NOT NULL,
    averageinboundtravelspeedinc NUMERIC,
    averageoutboundtravelspeedin NUMERIC,
    averagetravelspeedincongesti NUMERIC,
    congestionrate NUMERIC,
    largevehiclerate NUMERIC,
    observationpointname VARCHAR(1000),
    reference VARCHAR(1000),
    sectionid VARCHAR(1000),
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
    id BIGINT NOT NULL,
    from_id BIGINT,
    mintransfertime INTEGER,
    to_id BIGINT,
    transfertype VARCHAR(1000),
    transfertype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_translation
(
    id BIGINT NOT NULL,
    fieldname VARCHAR(1000),
    fieldvalue VARCHAR(1000),
    language VARCHAR(1000),
    language_codespace VARCHAR(1000),
    recordid_id BIGINT,
    recordsubid VARCHAR(1000),
    tablename VARCHAR(1000),
    tablename_codespace VARCHAR(1000),
    translation VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_translationjp
(
    id BIGINT NOT NULL,
    language VARCHAR(1000),
    language_codespace VARCHAR(1000),
    translation VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_transportation_complex
(
    id BIGINT NOT NULL,
    roaddataqualityattribute_id BIGINT,
    roadstructureattribute_id BIGINT,
    trafficvolumeattribute_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trip
(
    id BIGINT NOT NULL,
    bikeallowed VARCHAR(1000),
    bikeallowed_codespace VARCHAR(1000),
    blockid VARCHAR(1000),
    calendar_id BIGINT,
    calendardate_id BIGINT,
    directionid VARCHAR(1000),
    directionid_codespace VARCHAR(1000),
    headsign VARCHAR(1000),
    lod0multicurve geometry(GEOMETRYZ),
    office_id BIGINT,
    route_id BIGINT,
    shape_id BIGINT,
    shortname VARCHAR(1000),
    symbol VARCHAR(1000),
    wheelchairaccessib_codespace VARCHAR(1000),
    wheelchairaccessible VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfunc_to_cityobjec
(
    cityobject_id BIGINT NOT NULL,
    urbanfunction_id BIGINT NOT NULL,
    PRIMARY KEY (cityobject_id, urbanfunction_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfunction
(
    id BIGINT NOT NULL,
    abstract VARCHAR(1000),
    areaclassification_codespace VARCHAR(1000),
    areaclassificationtype VARCHAR(1000),
    capacity INTEGER,
    city VARCHAR(1000),
    city_codespace VARCHAR(1000),
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    custodian VARCHAR(1000),
    enactmentdate TIMESTAMP WITH TIME ZONE,
    enactmentfiscalyear DATE,
    expirationdate TIMESTAMP WITH TIME ZONE,
    expirationfiscalyear DATE,
    function VARCHAR(1000),
    function_codespace VARCHAR(1000),
    legalgrounds_id BIGINT,
    lod0multicurve geometry(GEOMETRYZ),
    lod0multipoint geometry(GEOMETRYZ),
    lod0multisurface_id BIGINT,
    lod_1multicurve geometry(GEOMETRYZ),
    lod_1multipoint geometry(GEOMETRYZ),
    lod_1multisurface_id BIGINT,
    lod_2multicurve geometry(GEOMETRYZ),
    lod_2multipoint geometry(GEOMETRYZ),
    lod_2multisurface_id BIGINT,
    nominalarea NUMERIC,
    nominalarea_uom VARCHAR(1000),
    note VARCHAR(1000),
    objectclass_id INTEGER,
    prefecture VARCHAR(1000),
    prefecture_codespace VARCHAR(1000),
    reference VARCHAR(1000),
    surveyyear DATE,
    urbanplantype VARCHAR(1000),
    urbanplantype_codespace VARCHAR(1000),
    validity NUMERIC,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanization
(
    id BIGINT NOT NULL,
    period VARCHAR(1000),
    resources VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_vegetationattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_vegetationattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_vegetationattribut_seq'::regclass) NOT NULL,
    appearancesrcdesc VARCHAR(1000),
    appearancesrcdesc_codespace VARCHAR(1000),
    geometrysrcdesc VARCHAR(1000),
    geometrysrcdesc_codespace VARCHAR(1000),
    srcscale VARCHAR(1000),
    srcscale_codespace VARCHAR(1000),
    thematicsrcdesc VARCHAR(1000),
    thematicsrcdesc_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbody
(
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbodyattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_waterbodyattribute_seq'::regclass) NOT NULL,
    depth NUMERIC,
    depth_uom VARCHAR(1000),
    description VARCHAR(1000),
    description_codespace VARCHAR(1000),
    objectclass_id INTEGER,
    rank VARCHAR(1000),
    rank_codespace VARCHAR(1000),
    rankorg VARCHAR(1000),
    rankorg_codespace VARCHAR(1000),
    waterbody_floodingriskatt_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbodyriverflooding
(
    id BIGINT NOT NULL,
    admintype VARCHAR(1000),
    admintype_codespace VARCHAR(1000),
    duration NUMERIC,
    duration_uom VARCHAR(1000),
    scale VARCHAR(1000),
    scale_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zone
(
    id BIGINT NOT NULL,
    areaapplied VARCHAR(1000),
    finalpublicationdate TIMESTAMP WITH TIME ZONE,
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
-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_areaof_landus_areao_fkx ON ur_areaofannualdiversions
    USING btree
    (
      landusediver_areaofannual_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_attribution_agency_fkx ON ur_attribution
    USING btree
    (
      agency_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_attribution_route_fkx ON ur_attribution
    USING btree
    (
      route_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_attribution_trip_fkx ON ur_attribution
    USING btree
    (
      trip_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_building_buildingda_fkx ON ur_building
    USING btree
    (
      buildingdataqualityattrib_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_building_buildingde_fkx ON ur_building
    USING btree
    (
      buildingdetailattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_building_buildingid_fkx ON ur_building
    USING btree
    (
      buildingidattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_building_largecusto_fkx ON ur_building
    USING btree
    (
      largecustomerfacilityattr_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_buildinga_objectcla_fkx ON ur_buildingattribute
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_buildi_buildi_build_fkx ON ur_buildingdisasterriskat
    USING btree
    (
      building_buildingdisaster_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_buildingd_objectcla_fkx ON ur_buildingdisasterriskat
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_buildingf_objectcla_fkx ON ur_buildingfloodingriskat
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_calendarda_calendar_fkx ON ur_calendardate
    USING btree
    (
      calendar_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_city_furnit_cityf_fkx_1 ON ur_city_furniture
    USING btree
    (
      cityfurnituredataqualitya_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_city_furnitu_cityfu_fkx ON ur_city_furniture
    USING btree
    (
      cityfurnituredetailattrib_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_cityfurni_objectcla_fkx ON ur_cityfurnitureattribute
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_cityobjec_vegetatio_fkx ON ur_cityobject
    USING btree
    (
      vegetationdataqualityattr_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_fareattribut_agency_fkx ON ur_fareattribute
    USING btree
    (
      agency_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_farerule_fare_fkx ON ur_farerule
    USING btree
    (
      fare_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_farerule_route_fkx ON ur_farerule
    USING btree
    (
      route_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_frequency_trip_fkx ON ur_frequency
    USING btree
    (
      trip_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_keyval_statis_gener_fkx ON ur_keyvaluepair
    USING btree
    (
      statisticalg_genericvalue_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_keyval_buildi_keyva_fkx ON ur_keyvaluepairattribute
    USING btree
    (
      building_keyvaluepairattr_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_land_use_landusedet_fkx ON ur_land_use
    USING btree
    (
      landusedetailattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landpr_landpr_landp_fkx ON ur_landpriceperlanduse
    USING btree
    (
      landprice_landprice_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_number_landus_numbe_fkx ON ur_numberofannualdiversio
    USING btree
    (
      landusediver_numberofannu_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_numbe_house_numbe_fkx_1 ON ur_numberofhouseholds
    USING btree
    (
      households_numberofhous_id_1 ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_number_househ_numbe_fkx ON ur_numberofhouseholds
    USING btree
    (
      households_numberofhouseh_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_numbe_censu_numbe_fkx_1 ON ur_numberofhouseholds_1
    USING btree
    (
      censusblock_numberofhou_id_1 ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_number_census_numbe_fkx ON ur_numberofhouseholds_1
    USING btree
    (
      censusblock_numberofhouse_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_pathway_from_fkx ON ur_pathway
    USING btree
    (
      from_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_pathway_to_fkx ON ur_pathway
    USING btree
    (
      to_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_point_point_spx ON ur_point
    USING gist
    (
      point
    );

CREATE INDEX ur_point_publict_point_fkx ON ur_point
    USING btree
    (
      publictransit_point_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_popula_popula_popul_fkx ON ur_populationbyageandsex
    USING btree
    (
      population_populationbyag_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_publictra_objectcla_fkx ON ur_publictransit
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_publictransi_target_fkx ON ur_publictransit
    USING btree
    (
      target_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_public_cityob_datat_fkx ON ur_publictransitdatatype
    USING btree
    (
      cityobjectgroup_datatype_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_publictr_objectcl_fkx_1 ON ur_publictransitdatatype
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_roadattri_objectcla_fkx ON ur_roadattribute
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_route_agency_fkx ON ur_route
    USING btree
    (
      agency_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_route_description_fkx ON ur_route
    USING btree
    (
      description_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_route_lod0multicurv_spx ON ur_route
    USING gist
    (
      lod0multicurve
    );

CREATE INDEX ur_route_parentroute_fkx ON ur_route
    USING btree
    (
      parentroute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_statist_lod_1multis_fkx ON ur_statisticalgrid
    USING btree
    (
      lod_1multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_statist_lod_2multis_fkx ON ur_statisticalgrid
    USING btree
    (
      lod_2multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_statistic_objectcla_fkx ON ur_statisticalgrid
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_stop_level_fkx ON ur_stop
    USING btree
    (
      level_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_stop_parentstation_fkx ON ur_stop
    USING btree
    (
      parentstation_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_stop_point_spx ON ur_stop
    USING gist
    (
      point
    );

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_stoptime_stop_fkx ON ur_stoptime
    USING btree
    (
      stop_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_stoptime_trip_fkx ON ur_stoptime
    USING btree
    (
      trip_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_traffic_area_traffi_fkx ON ur_traffic_area
    USING btree
    (
      trafficareastructureattri_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_transfer_from_fkx ON ur_transfer
    USING btree
    (
      from_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_transfer_to_fkx ON ur_transfer
    USING btree
    (
      to_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_translatio_recordid_fkx ON ur_translation
    USING btree
    (
      recordid_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_transp_comple_roadd_fkx ON ur_transportation_complex
    USING btree
    (
      roaddataqualityattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_transp_comple_roads_fkx ON ur_transportation_complex
    USING btree
    (
      roadstructureattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_transp_comple_traff_fkx ON ur_transportation_complex
    USING btree
    (
      trafficvolumeattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_trip_calendar_fkx ON ur_trip
    USING btree
    (
      calendar_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_trip_calendardate_fkx ON ur_trip
    USING btree
    (
      calendardate_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_trip_lod0multicurve_spx ON ur_trip
    USING gist
    (
      lod0multicurve
    );

CREATE INDEX ur_trip_office_fkx ON ur_trip
    USING btree
    (
      office_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_trip_route_fkx ON ur_trip
    USING btree
    (
      route_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_trip_shape_fkx ON ur_trip
    USING btree
    (
      shape_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfu_to_cityobj_fk2x ON ur_urbanfunc_to_cityobjec
    USING btree
    (
      cityobject_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfu_to_cityobj_fk1x ON ur_urbanfunc_to_cityobjec
    USING btree
    (
      urbanfunction_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfunc_legalgrou_fkx ON ur_urbanfunction
    USING btree
    (
      legalgrounds_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfunc_lod0multi_spx ON ur_urbanfunction
    USING gist
    (
      lod0multicurve
    );

CREATE INDEX ur_urbanfun_lod0mult_spx_1 ON ur_urbanfunction
    USING gist
    (
      lod0multipoint
    );

CREATE INDEX ur_urbanfunc_lod0multi_fkx ON ur_urbanfunction
    USING btree
    (
      lod0multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfu_lod_1multic_spx ON ur_urbanfunction
    USING gist
    (
      lod_1multicurve
    );

CREATE INDEX ur_urbanfu_lod_1multip_spx ON ur_urbanfunction
    USING gist
    (
      lod_1multipoint
    );

CREATE INDEX ur_urbanfu_lod_1multis_fkx ON ur_urbanfunction
    USING btree
    (
      lod_1multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfu_lod_2multic_spx ON ur_urbanfunction
    USING gist
    (
      lod_2multicurve
    );

CREATE INDEX ur_urbanfu_lod_2multip_spx ON ur_urbanfunction
    USING gist
    (
      lod_2multipoint
    );

CREATE INDEX ur_urbanfu_lod_2multis_fkx ON ur_urbanfunction
    USING btree
    (
      lod_2multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfunc_objectcla_fkx ON ur_urbanfunction
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_waterbody_objectcla_fkx ON ur_waterbodyattribute
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_waterb_waterb_flood_fkx ON ur_waterbodyattribute
    USING btree
    (
      waterbody_floodingriskatt_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_zone_objectclass_fkx ON ur_zone
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

