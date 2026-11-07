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


CREATE SEQUENCE ur_numberofhouseholds_seq_2
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_parkattribute_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_parkingplaceattrib_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_sewersystemattribu_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_structuredetails_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_urbanrapidtransitr_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_urbanroadattribute_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_vehicleterminalatt_seq
INCREMENT BY 1
MINVALUE 0
MAXVALUE 9223372036854775807
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


CREATE SEQUENCE ur_waterworksattribut_seq
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
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofannualdiversio
(
    id BIGINT NOT NULL DEFAULT nextval('ur_numberofannualdive_seq'::regclass) NOT NULL,
    count INTEGER,
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
    objectclass_id INTEGER,
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
-- ur_populatio_to_populatio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_populatio_to_populatio
(
    populationbyageandsex_id BIGINT NOT NULL,
    population_id BIGINT NOT NULL,
    PRIMARY KEY (populationbyageandsex_id, population_id)
);

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landprice_to_landprice
(
    landpriceperlanduse_id BIGINT NOT NULL,
    landprice_id BIGINT NOT NULL,
    PRIMARY KEY (landpriceperlanduse_id, landprice_id)
);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landusedi_to_numberofa
(
    numberofannualdiversio_id BIGINT NOT NULL,
    landusediversion_id BIGINT NOT NULL,
    PRIMARY KEY (numberofannualdiversio_id, landusediversion_id)
);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landusedi_to_areaofann
(
    areaofannualdiversions_id BIGINT NOT NULL,
    landusediversion_id BIGINT NOT NULL,
    PRIMARY KEY (areaofannualdiversions_id, landusediversion_id)
);

-- -------------------------------------------------------------------- 
-- ur_statistic_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_statistic_to_keyvaluep
(
    keyvaluepair_id BIGINT NOT NULL,
    statisticalgrid_id BIGINT NOT NULL,
    PRIMARY KEY (keyvaluepair_id, statisticalgrid_id)
);

-- -------------------------------------------------------------------- 
-- ur_publictra_to_point 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictra_to_point
(
    point_id BIGINT NOT NULL,
    publictransit_id BIGINT NOT NULL,
    PRIMARY KEY (point_id, publictransit_id)
);

-- -------------------------------------------------------------------- 
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_building_to_buildingd
(
    buildingdisasterriskat_id BIGINT NOT NULL,
    building_id BIGINT NOT NULL,
    PRIMARY KEY (buildingdisasterriskat_id, building_id)
);

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_building_to_keyvaluep
(
    keyvaluepairattribute_id BIGINT NOT NULL,
    building_id BIGINT NOT NULL,
    PRIMARY KEY (keyvaluepairattribute_id, building_id)
);

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterbody_to_waterbody
(
    waterbodyattribute_id BIGINT NOT NULL,
    waterbody_id BIGINT NOT NULL,
    PRIMARY KEY (waterbodyattribute_id, waterbody_id)
);

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_cityobjec_to_publictra
(
    publictransitdatatype_id BIGINT NOT NULL,
    cityobjectgroup_3_id BIGINT NOT NULL,
    PRIMARY KEY (publictransitdatatype_id, cityobjectgroup_3_id)
);

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_districtd 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_abstractd_to_districtd
(
    abstractdistrictplan_id BIGINT NOT NULL,
    districtdevelopmentpla_id BIGINT NOT NULL,
    PRIMARY KEY (districtdevelopmentpla_id, abstractdistrictplan_id)
);

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_abstractd_to_zone
(
    abstractdistrictplan_id BIGINT NOT NULL,
    zone_id BIGINT NOT NULL,
    PRIMARY KEY (zone_id, abstractdistrictplan_id)
);

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_abstractdistrictplan
(
    id BIGINT NOT NULL,
    objectclass_id INTEGER,
    objectives VARCHAR(1000),
    policy VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_areaclassification
(
    id BIGINT NOT NULL,
    population INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_boundary 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_boundary
(
    id BIGINT NOT NULL,
    offset_ NUMERIC,
    offset__uom VARCHAR(1000),
    offsetdirection VARCHAR(1000),
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
-- ur_collectivefacilities_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivefacilities_2
(
    id BIGINT NOT NULL,
    housingfacilities VARCHAR(1000),
    maximumbuildingcoveragerate NUMERIC,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    minimumfloorarearate NUMERIC,
    publicfacilities VARCHAR(1000),
    supecificbusinessfacilities VARCHAR(1000),
    utilityfacilities VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_3 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivefacilities_3
(
    id BIGINT NOT NULL,
    housingfacilities VARCHAR(1000),
    maximumbuildingcoveragerate NUMERIC,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    minimumfloorarearate NUMERIC,
    publicfacilities VARCHAR(1000),
    supecificbusinessfacilities VARCHAR(1000),
    utilityfacilities VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivefacilitiesfo 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivefacilitiesfo
(
    id BIGINT NOT NULL,
    housingfacilities VARCHAR(1000),
    maximumbuildingcoveragerate NUMERIC,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    minimumfloorarearate NUMERIC,
    publicfacilities VARCHAR(1000),
    supecificbusinessfacilities VARCHAR(1000),
    utilityfacilities VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivegovernmentan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivegovernmentan
(
    id BIGINT NOT NULL,
    buildingcoveragerate NUMERIC,
    floorarearate NUMERIC,
    publicfacilitiesallocationpo VARCHAR(1000),
    scheduledexecutor VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectivehousingfacil 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectivehousingfacil
(
    id BIGINT NOT NULL,
    buildingcoveragerate NUMERIC,
    floorarearate NUMERIC,
    numberofhighrisehousing INTEGER,
    numberoflowrisehousing INTEGER,
    numberofmiddlerisehousing INTEGER,
    publicfacilitiesallocationpo VARCHAR(1000),
    scheduledexecutor VARCHAR(1000),
    totalnumberofhousing INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_collectiveurbandisaste 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_collectiveurbandisaste
(
    id BIGINT NOT NULL,
    maximumbuildingcoveragerate NUMERIC,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    minimumfloorarearate NUMERIC,
    specificutilityandpublicfaci VARCHAR(1000),
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
-- ur_disasterp_to_zonaldisa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterp_to_zonaldisa
(
    disasterpreventionbl_2_id BIGINT NOT NULL,
    zonaldisasterpreventio_id BIGINT NOT NULL,
    PRIMARY KEY (zonaldisasterpreventio_id, disasterpreventionbl_2_id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbase 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterpreventionbase
(
    id BIGINT NOT NULL,
    capacity INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbl_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterpreventionbl_2
(
    id BIGINT NOT NULL,
    specifiedzonaldisasterpreven VARCHAR(1000),
    zonaldisasterpreventionfacil VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbloc 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_disasterpreventionbloc
(
    id BIGINT NOT NULL,
    developmentplan VARCHAR(1000),
    disasterpreventionpublicfaci VARCHAR(1000),
    otherpublicfacilityallocatio VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesspa 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_distributionbusinesspa
(
    id BIGINT NOT NULL,
    buildingcoveragerate NUMERIC,
    distributionbusinesspark VARCHAR(1000),
    floorarearate NUMERIC,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    publicandutilityfacilities VARCHAR(1000),
    scheduledexecutor VARCHAR(1000),
    setbacksize VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesszo 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_distributionbusinesszo
(
    id BIGINT NOT NULL,
    guidelinepublicationdate TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_district 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_district
(
    id BIGINT NOT NULL,
    buildingdesignrestriction VARCHAR(1000),
    buildingrestrictions VARCHAR(1000),
    fenceguideline VARCHAR(1000),
    maximumbuildingcoveragerate NUMERIC,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    minimumbuildingarea NUMERIC,
    minimumbuildingarea_uom VARCHAR(1000),
    minimumbuildingcoveragerate NUMERIC,
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    minimumfloorarearate NUMERIC,
    minimumfloorheight NUMERIC,
    minimumfloorheight_uom VARCHAR(1000),
    minimumfrontagerate NUMERIC,
    minimumgreeningrate NUMERIC,
    minimumgroundheight NUMERIC,
    minimumgroundheight_uom VARCHAR(1000),
    minimumsitearea NUMERIC,
    minimumsitearea_uom VARCHAR(1000),
    restrictionsforfireprotectio VARCHAR(1000),
    restrictionsfornoiseprotecti VARCHAR(1000),
    setbacksize VARCHAR(1000),
    strucutureplacementrestricti VARCHAR(1000),
    userestrictions VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_districtd_to_district 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtd_to_district
(
    district_id BIGINT NOT NULL,
    districtdevelopmentpla_id BIGINT NOT NULL,
    PRIMARY KEY (district_id, districtdevelopmentpla_id)
);

-- -------------------------------------------------------------------- 
-- ur_districtd_to_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtd_to_zone
(
    districtdevelopmentpla_id BIGINT NOT NULL,
    zone_id BIGINT NOT NULL,
    PRIMARY KEY (zone_id, districtdevelopmentpla_id)
);

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtdevelopmentpla
(
    id BIGINT NOT NULL,
    activityrestrictioninfarmlan VARCHAR(1000),
    buildingrestrictions VARCHAR(1000),
    districtfacilitiesallocation VARCHAR(1000),
    landuserestrictions VARCHAR(1000),
    objectclass_id INTEGER,
    urbangreenspaceconservation VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_districtplan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtplan
(
    id BIGINT NOT NULL,
    facilityallocation VARCHAR(1000),
    landusepolicy VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_districtsandzones
(
    id BIGINT NOT NULL,
    areaintotal NUMERIC,
    areaintotal_uom VARCHAR(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_exceptionalfloorareara 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_exceptionalfloorareara
(
    id BIGINT NOT NULL,
    buildingheightlimits NUMERIC,
    buildingheightlimits_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_globalhubcitydevelopme 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_globalhubcitydevelopme
(
    id BIGINT NOT NULL,
    implementationbody VARCHAR(1000),
    implementationperiod VARCHAR(1000),
    plan VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_heightcontroldistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_heightcontroldistrict
(
    id BIGINT NOT NULL,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_highlevelusedistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_highlevelusedistrict
(
    id BIGINT NOT NULL,
    maximumbuildingcoveragerate VARCHAR(1000),
    maximumfloorarearate VARCHAR(1000),
    minimumbuildingarea VARCHAR(1000),
    minimumbuildingarea_uom VARCHAR(1000),
    minimumfloorarearate VARCHAR(1000),
    setbacksize VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_highriseresidentialatt 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_highriseresidentialatt
(
    id BIGINT NOT NULL,
    floorarearate NUMERIC,
    maximumbuildingcoveragerate NUMERIC,
    minimumsitearea NUMERIC,
    minimumsitearea_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_historicscenerymainten 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_historicscenerymainten
(
    id BIGINT NOT NULL,
    landusepolicy VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_industrialparkdevelopm 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_industrialparkdevelopm
(
    id BIGINT NOT NULL,
    publicfacilityallocation VARCHAR(1000),
    residentiallanduseplan VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landreadjustmentprojec 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landreadjustmentprojec
(
    id BIGINT NOT NULL,
    buildinglotdevelopment VARCHAR(1000),
    publicfacilityallocation VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_landscapezone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_landscapezone
(
    id BIGINT NOT NULL,
    buildingdesignrestriction VARCHAR(1000),
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    minimumsitearea NUMERIC,
    minimumsitearea_uom VARCHAR(1000),
    setbacksize VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_newhousingandurbandeve 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_newhousingandurbandeve
(
    id BIGINT NOT NULL,
    housing VARCHAR(1000),
    publicfacilityallocation VARCHAR(1000),
    residentiallanduseplan VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_newurbaninfrastructure 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_newurbaninfrastructure
(
    id BIGINT NOT NULL,
    districtsallocation VARCHAR(1000),
    landforcentralpublicfaciliti VARCHAR(1000),
    landuseplan VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_numberofhouseholds_2
(
    id BIGINT NOT NULL DEFAULT nextval('ur_numberofhouseholds_seq_2'::regclass) NOT NULL,
    censusblock_numberofhouse__2 BIGINT,
    censusblock_numberofhouse_id BIGINT,
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    number_ INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_openspaceforpublicuse
(
    id BIGINT NOT NULL,
    parkattribute_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_parkattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_parkattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_parkattribute_seq'::regclass) NOT NULL,
    parkserialnumber INTEGER,
    parksizenumber VARCHAR(1000),
    parksizenumber_codespace VARCHAR(1000),
    parktypenumber VARCHAR(1000),
    parktypenumber_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_parkingplaceattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_parkingplaceattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_parkingplaceattrib_seq'::regclass) NOT NULL,
    storeysaboveground INTEGER,
    storeysbelowground INTEGER,
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
-- ur_portzone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_portzone
(
    id BIGINT NOT NULL,
    floorarearate NUMERIC,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_privateurbanrenewalpro 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_privateurbanrenewalpro
(
    id BIGINT NOT NULL,
    developer VARCHAR(1000),
    plan VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_productivegreenzone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_productivegreenzone
(
    id BIGINT NOT NULL,
    specification VARCHAR(1000),
    specification_codespace VARCHAR(1000),
    zonenumber VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_projectpromotionarea
(
    id BIGINT NOT NULL,
    developmentpolicy VARCHAR(1000),
    objectclass_id INTEGER,
    publicfacilitiesplans VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_publictransitfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_publictransitfacility
(
    id BIGINT NOT NULL,
    companyname VARCHAR(1000),
    frequencyofservice INTEGER,
    numberofcustomers INTEGER,
    routename VARCHAR(1000),
    sectionname VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_quasiurbanplanningarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_quasiurbanplanningarea
(
    id BIGINT NOT NULL,
    cityarea NUMERIC,
    cityarea_uom VARCHAR(1000),
    citypopulation INTEGER,
    population INTEGER,
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
-- ur_residentialblockconstr 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_residentialblockconstr
(
    id BIGINT NOT NULL,
    developmentplan VARCHAR(1000),
    publicfacilityallocation VARCHAR(1000),
    sitearea NUMERIC,
    sitearea_uom VARCHAR(1000),
    totalfloorarea NUMERIC,
    totalfloorarea_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_residentialenvironment 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_residentialenvironment
(
    id BIGINT NOT NULL,
    maximumbuildingcoveragerate NUMERIC,
    maximumbuildingheight VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    otherrestrictions VARCHAR(1000),
    setbacksize VARCHAR(1000),
    usetobeinduced VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictimprov 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadsidedistrictimprov
(
    id BIGINT NOT NULL,
    roadsidedistrictfacilitiesal VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictplan 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_roadsidedistrictplan
(
    id BIGINT NOT NULL,
    facilitiesallocation VARCHAR(1000),
    landusepolicy VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_ruraldistrictimproveme 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_ruraldistrictimproveme
(
    id BIGINT NOT NULL,
    ruraldistrictfacilitiesalloc VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_scenicdistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_scenicdistrict
(
    id BIGINT NOT NULL,
    buildingcoveragerate NUMERIC,
    buildingheightlimits NUMERIC,
    buildingheightlimits_uom VARCHAR(1000),
    wallsetbackdistancewitha_uom VARCHAR(1000),
    wallsetbackdistancewithadjoi NUMERIC,
    wallsetbackdistancewithr_uom VARCHAR(1000),
    wallsetbackdistancewithroad NUMERIC,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_scheduledareaforurband
(
    id BIGINT NOT NULL,
    objectclass_id INTEGER,
    scheduledexecutor VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_sedimentdisasterpronea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_sedimentdisasterpronea
(
    id BIGINT NOT NULL,
    areatype VARCHAR(1000),
    areatype_codespace VARCHAR(1000),
    disastertype VARCHAR(1000),
    disastertype_codespace VARCHAR(1000),
    status VARCHAR(1000),
    status_codespace VARCHAR(1000),
    zonename VARCHAR(1000),
    zonenumber VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_sewersystemattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_sewersystemattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_sewersystemattribu_seq'::regclass) NOT NULL,
    drainagearea VARCHAR(1000),
    endlocation VARCHAR(1000),
    startlocation VARCHAR(1000),
    systemtype VARCHAR(1000),
    systemtype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialgreenspaceconse 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialgreenspaceconse
(
    id BIGINT NOT NULL,
    requirement VARCHAR(1000),
    requirement_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialurbanrenaissanc 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialurbanrenaissanc
(
    id BIGINT NOT NULL,
    maximumbuildingcoveragerate NUMERIC,
    maximumbuildingheight VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    minimumbuildingarea NUMERIC,
    minimumbuildingarea_uom VARCHAR(1000),
    minimumfloorarearate NUMERIC,
    otherrestrictions VARCHAR(1000),
    setbacksize VARCHAR(1000),
    usetobeinduced VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialuseattractiondi 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialuseattractiondi
(
    id BIGINT NOT NULL,
    maximumbuildingheight VARCHAR(1000),
    maximumfloorarearate NUMERIC,
    minimumbuildingarea NUMERIC,
    minimumbuildingarea_uom VARCHAR(1000),
    minimumfloorarearate NUMERIC,
    otherrestrictions VARCHAR(1000),
    usetobeinduced VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialusedistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialusedistrict
(
    id BIGINT NOT NULL,
    buildingrestrictions VARCHAR(1000),
    otherrestrictions VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specialuserestrictiond 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specialuserestrictiond
(
    id BIGINT NOT NULL,
    buildingrestrictions VARCHAR(1000),
    otherrestrictions VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specified_to_globalhub 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specified_to_globalhub
(
    globalhubcitydevelopme_id BIGINT NOT NULL,
    specifiedurgenturbanre_id BIGINT NOT NULL,
    PRIMARY KEY (globalhubcitydevelopme_id, specifiedurgenturbanre_id)
);

-- -------------------------------------------------------------------- 
-- ur_specifiedblock 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specifiedblock
(
    id BIGINT NOT NULL,
    floorarearate NUMERIC,
    maximumbuildingheight NUMERIC,
    maximumbuildingheight_uom VARCHAR(1000),
    setbacksize VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specifieddisasterpreve 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specifieddisasterpreve
(
    id BIGINT NOT NULL,
    minimumbuildingheight NUMERIC,
    minimumbuildingheight_uom VARCHAR(1000),
    minimumfrontagerate NUMERIC,
    minimumsitearea NUMERIC,
    minimumsitearea_uom VARCHAR(1000),
    setbacksize VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_specifiedurgenturbanre 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_specifiedurgenturbanre
(
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_structuredetails 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_structuredetails
(
    id BIGINT NOT NULL DEFAULT nextval('ur_structuredetails_seq'::regclass) NOT NULL,
    crosstype VARCHAR(1000),
    crosstype_codespace VARCHAR(1000),
    endlocation VARCHAR(1000),
    length NUMERIC,
    length_uom VARCHAR(1000),
    maximumwidth NUMERIC,
    maximumwidth_uom VARCHAR(1000),
    minimumwidth NUMERIC,
    minimumwidth_uom VARCHAR(1000),
    standardwidth NUMERIC,
    standardwidth_uom VARCHAR(1000),
    startlocation VARCHAR(1000),
    structuretype VARCHAR(1000),
    structuretype_codespace VARCHAR(1000),
    vialocations VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_supplyfacility
(
    id BIGINT NOT NULL,
    waterworksattribute_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_threedimensionalextent 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_threedimensionalextent
(
    id BIGINT NOT NULL,
    maximumload NUMERIC,
    maximumload_uom VARCHAR(1000),
    minimumdistance NUMERIC,
    minimumdistance_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_trafficfacility
(
    id BIGINT NOT NULL,
    endlocation VARCHAR(1000),
    length NUMERIC,
    length_uom VARCHAR(1000),
    parkingplaceattribute_id BIGINT,
    startlocation VARCHAR(1000),
    urbanrapidtransitrailroad_id BIGINT,
    urbanroadattribute_id BIGINT,
    vehicleterminalattribute_id BIGINT,
    vialocations VARCHAR(1000),
    width NUMERIC,
    width_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_treatmentfacility
(
    id BIGINT NOT NULL,
    sewersystemsattribute_id BIGINT,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_treeplantingdistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_treeplantingdistrict
(
    id BIGINT NOT NULL,
    minimumgreeningrate NUMERIC,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbandevelopmentprojec
(
    id BIGINT NOT NULL,
    objectclass_id INTEGER,
    scheduledexecutor VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbandisasterrecoveryp 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbandisasterrecoveryp
(
    id BIGINT NOT NULL,
    emergencyrecoverypolicy VARCHAR(1000),
    expirationdate TIMESTAMP WITH TIME ZONE,
    plannedprojecttype VARCHAR(1000),
    plannedprojecttype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfaci_to_threedime 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfaci_to_threedime
(
    threedimensionalextent_id BIGINT NOT NULL,
    urbanfacility_id BIGINT NOT NULL,
    PRIMARY KEY (threedimensionalextent_id, urbanfacility_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfacility
(
    id BIGINT NOT NULL,
    number_ VARCHAR(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanfacilitystipulate
(
    id BIGINT NOT NULL,
    length NUMERIC,
    length_uom VARCHAR(1000),
    objectclass_id INTEGER,
    width NUMERIC,
    width_uom VARCHAR(1000),
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
    areaclassification_codespace VARCHAR(1000),
    areaclassificationtype VARCHAR(1000),
    city VARCHAR(1000),
    city_codespace VARCHAR(1000),
    class VARCHAR(1000),
    class_codespace VARCHAR(1000),
    custodian VARCHAR(1000),
    enactmentfiscalyear DATE,
    expirationfiscalyear DATE,
    function VARCHAR(1000),
    function_codespace VARCHAR(1000),
    legalgrounds VARCHAR(1000),
    lod0multicurve geometry(GEOMETRYZ),
    lod0multipoint geometry(GEOMETRYZ),
    lod0multisurface_id BIGINT,
    lod1multicurve geometry(GEOMETRYZ),
    lod1multisurface_id BIGINT,
    lod_1multicurve geometry(GEOMETRYZ),
    lod_1multipoint geometry(GEOMETRYZ),
    lod_1multisurface_id BIGINT,
    lod_2multicurve geometry(GEOMETRYZ),
    lod_2multipoint geometry(GEOMETRYZ),
    lod_2multisurface_id BIGINT,
    nominalarea NUMERIC,
    nominalarea_uom VARCHAR(1000),
    note VARCHAR(1000),
    notificationnumber VARCHAR(1000),
    objectclass_id INTEGER,
    prefecture VARCHAR(1000),
    prefecture_codespace VARCHAR(1000),
    reason VARCHAR(1000),
    reference VARCHAR(1000),
    surveyyear DATE,
    urbanplantype VARCHAR(1000),
    urbanplantype_codespace VARCHAR(1000),
    usage VARCHAR(1000),
    usage_codespace VARCHAR(1000),
    validfrom TIMESTAMP WITH TIME ZONE,
    validfromtype VARCHAR(1000),
    validfromtype_codespace VARCHAR(1000),
    validto TIMESTAMP WITH TIME ZONE,
    validtotype VARCHAR(1000),
    validtotype_codespace VARCHAR(1000),
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
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanplanningarea
(
    id BIGINT NOT NULL,
    areaclassification VARCHAR(1000),
    areaclassification_codespace VARCHAR(1000),
    cityarea NUMERIC,
    cityarea_uom VARCHAR(1000),
    citypopulation INTEGER,
    policyforareaclassification VARCHAR(1000),
    policyforurbanplandecision VARCHAR(1000),
    population INTEGER,
    purposeforurbanplan VARCHAR(1000),
    reasonforareaclassification VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanrapi_to_structure 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanrapi_to_structure
(
    structuredetails_id BIGINT NOT NULL,
    urbanrapidtransitrailr_id BIGINT NOT NULL,
    PRIMARY KEY (structuredetails_id, urbanrapidtransitrailr_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanrapidtransitrailr 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanrapidtransitrailr
(
    id BIGINT NOT NULL DEFAULT nextval('ur_urbanrapidtransitr_seq'::regclass) NOT NULL,
    crosstype VARCHAR(1000),
    crosstype_codespace VARCHAR(1000),
    structuretype VARCHAR(1000),
    structuretype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentproj 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanredevelopmentproj
(
    id BIGINT NOT NULL,
    developmentplan VARCHAR(1000),
    housingtarget VARCHAR(1000),
    numberofhousing INTEGER,
    publicfacilityallocation VARCHAR(1000),
    sitearea NUMERIC,
    sitearea_uom VARCHAR(1000),
    totalfloorarea NUMERIC,
    totalfloorarea_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentprom 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanredevelopmentprom
(
    id BIGINT NOT NULL,
    publicfacilities VARCHAR(1000),
    unitarea VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanrenewalproject 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanrenewalproject
(
    id BIGINT NOT NULL,
    buildingusage VARCHAR(1000),
    floorarearate NUMERIC,
    setbacksize VARCHAR(1000),
    sitearea NUMERIC,
    sitearea_uom VARCHAR(1000),
    storeysaboveground INTEGER,
    storeysbelowground INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanroad_to_structure 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanroad_to_structure
(
    structuredetails_id BIGINT NOT NULL,
    urbanroadattribute_id BIGINT NOT NULL,
    PRIMARY KEY (structuredetails_id, urbanroadattribute_id)
);

-- -------------------------------------------------------------------- 
-- ur_urbanroadattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urbanroadattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_urbanroadattribute_seq'::regclass) NOT NULL,
    crosstype VARCHAR(1000),
    crosstype_codespace VARCHAR(1000),
    numberoflanes INTEGER,
    roadstructure VARCHAR(1000),
    roadtype VARCHAR(1000),
    roadtype_codespace VARCHAR(1000),
    routeserialnumber INTEGER,
    routesizenumber VARCHAR(1000),
    routesizenumber_codespace VARCHAR(1000),
    routetypenumber VARCHAR(1000),
    routetypenumber_codespace VARCHAR(1000),
    structuretype VARCHAR(1000),
    structuretype_codespace VARCHAR(1000),
    trafficplazas VARCHAR(1000),
    trafficplazas_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_privateur 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturb_to_privateur
(
    privateurbanrenewalpro_id BIGINT NOT NULL,
    urgenturbanrenewalarea_id BIGINT NOT NULL,
    PRIMARY KEY (privateurbanrenewalpro_id, urgenturbanrenewalarea_id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specialur 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturb_to_specialur
(
    specialurbanrenaissanc_id BIGINT NOT NULL,
    urgenturbanrenewalarea_id BIGINT NOT NULL,
    PRIMARY KEY (specialurbanrenaissanc_id, urgenturbanrenewalarea_id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specified 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturb_to_specified
(
    specifiedurgenturbanre_id BIGINT NOT NULL,
    urgenturbanrenewalarea_id BIGINT NOT NULL,
    PRIMARY KEY (specifiedurgenturbanre_id, urgenturbanrenewalarea_id)
);

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_urgenturbanrenewalarea
(
    id BIGINT NOT NULL,
    developmentpolicy VARCHAR(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_usedistrict 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_usedistrict
(
    id BIGINT NOT NULL,
    buildingcoveragerate NUMERIC,
    buildingheightlimits NUMERIC,
    buildingheightlimits_uom VARCHAR(1000),
    buildingrestrictions VARCHAR(1000),
    floorarearate NUMERIC,
    minimumsitearea NUMERIC,
    minimumsitearea_uom VARCHAR(1000),
    otherrestrictions VARCHAR(1000),
    setbackrestrictions VARCHAR(1000),
    shaderegulation VARCHAR(1000),
    wallsetbackdistance VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_vehicleterminalattribu 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_vehicleterminalattribu
(
    id BIGINT NOT NULL DEFAULT nextval('ur_vehicleterminalatt_seq'::regclass) NOT NULL,
    terminaltype VARCHAR(1000),
    terminaltype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterway
(
    id BIGINT NOT NULL,
    endlocation VARCHAR(1000),
    length NUMERIC,
    length_uom VARCHAR(1000),
    startlocation VARCHAR(1000),
    structure VARCHAR(1000),
    structure_codespace VARCHAR(1000),
    width NUMERIC,
    width_uom VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_waterworksattribute 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_waterworksattribute
(
    id BIGINT NOT NULL DEFAULT nextval('ur_waterworksattribut_seq'::regclass) NOT NULL,
    endlocation VARCHAR(1000),
    startlocation VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zonaldisasterpreventio 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zonaldisasterpreventio
(
    id BIGINT NOT NULL,
    facilitytype VARCHAR(1000),
    facilitytype_codespace VARCHAR(1000),
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zone
(
    id BIGINT NOT NULL,
    location VARCHAR(1000),
    objectclass_id INTEGER,
    PRIMARY KEY (id)
);

-- -------------------------------------------------------------------- 
-- ur_zone_to_boundary 
-- -------------------------------------------------------------------- 
CREATE TABLE ur_zone_to_boundary
(
    boundary_id BIGINT NOT NULL,
    zone_id BIGINT NOT NULL,
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
ALTER TABLE ur_populatio_to_populatio ADD CONSTRAINT ur_populatio_populationb_fk FOREIGN KEY (populationbyageandsex_id)
REFERENCES ur_populationbyageandsex (id)
ON DELETE CASCADE;

ALTER TABLE ur_populatio_to_populatio ADD CONSTRAINT ur_populatio_population__fk FOREIGN KEY (population_id)
REFERENCES ur_population (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice_to_landprice ADD CONSTRAINT ur_landprice_landpricepe_fk FOREIGN KEY (landpriceperlanduse_id)
REFERENCES ur_landpriceperlanduse (id)
ON DELETE CASCADE;

ALTER TABLE ur_landprice_to_landprice ADD CONSTRAINT ur_landprice_landprice_i_fk FOREIGN KEY (landprice_id)
REFERENCES ur_landprice (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusedi_to_numberofa ADD CONSTRAINT ur_landusedi_numberofann_fk FOREIGN KEY (numberofannualdiversio_id)
REFERENCES ur_numberofannualdiversio (id)
ON DELETE CASCADE;

ALTER TABLE ur_landusedi_to_numberofa ADD CONSTRAINT ur_landusedi_landusedive_fk FOREIGN KEY (landusediversion_id)
REFERENCES ur_landusediversion (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusedi_to_areaofann ADD CONSTRAINT ur_landusedi_areaofannua_fk FOREIGN KEY (areaofannualdiversions_id)
REFERENCES ur_areaofannualdiversions (id)
ON DELETE CASCADE;

ALTER TABLE ur_landusedi_to_areaofann ADD CONSTRAINT ur_landusedi_landusedive_fk_3 FOREIGN KEY (landusediversion_id)
REFERENCES ur_landusediversion (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_statistic_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_statistic_to_keyvaluep ADD CONSTRAINT ur_statistic_keyvaluepai_fk FOREIGN KEY (keyvaluepair_id)
REFERENCES ur_keyvaluepair (id)
ON DELETE CASCADE;

ALTER TABLE ur_statistic_to_keyvaluep ADD CONSTRAINT ur_statistic_statistical_fk FOREIGN KEY (statisticalgrid_id)
REFERENCES ur_statisticalgrid (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_publictra_to_point 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictra_to_point ADD CONSTRAINT ur_publictra_point_id_fk FOREIGN KEY (point_id)
REFERENCES ur_point (id)
ON DELETE CASCADE;

ALTER TABLE ur_publictra_to_point ADD CONSTRAINT ur_publictra_publictrans_fk FOREIGN KEY (publictransit_id)
REFERENCES ur_publictransit (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_buildingd ADD CONSTRAINT ur_building__buildingdis_fk FOREIGN KEY (buildingdisasterriskat_id)
REFERENCES ur_buildingdisasterriskat (id)
ON DELETE CASCADE;

ALTER TABLE ur_building_to_buildingd ADD CONSTRAINT ur_building__building_id_fk FOREIGN KEY (building_id)
REFERENCES ur_building (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_keyvaluep ADD CONSTRAINT ur_building__keyvaluepai_fk FOREIGN KEY (keyvaluepairattribute_id)
REFERENCES ur_keyvaluepairattribute (id)
ON DELETE CASCADE;

ALTER TABLE ur_building_to_keyvaluep ADD CONSTRAINT ur_building__building_id_fk_3 FOREIGN KEY (building_id)
REFERENCES ur_building (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody_to_waterbody ADD CONSTRAINT ur_waterbody_waterbodyat_fk FOREIGN KEY (waterbodyattribute_id)
REFERENCES ur_waterbodyattribute (id)
ON DELETE CASCADE;

ALTER TABLE ur_waterbody_to_waterbody ADD CONSTRAINT ur_waterbody_waterbody_i_fk FOREIGN KEY (waterbody_id)
REFERENCES ur_waterbody (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjec_to_publictra ADD CONSTRAINT ur_cityobjec_publictrans_fk FOREIGN KEY (publictransitdatatype_id)
REFERENCES ur_publictransitdatatype (id)
ON DELETE CASCADE;

ALTER TABLE ur_cityobjec_to_publictra ADD CONSTRAINT ur_cityobjec_cityobjectg_fk FOREIGN KEY (cityobjectgroup_3_id)
REFERENCES ur_cityobjectgroup_3 (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_districtd 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractd_to_districtd ADD CONSTRAINT ur_abstractd_abstractdis_fk FOREIGN KEY (abstractdistrictplan_id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

ALTER TABLE ur_abstractd_to_districtd ADD CONSTRAINT ur_abstractd_districtdev_fk FOREIGN KEY (districtdevelopmentpla_id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractd_to_zone ADD CONSTRAINT ur_abstractd_abstractdis_fk_2 FOREIGN KEY (abstractdistrictplan_id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

ALTER TABLE ur_abstractd_to_zone ADD CONSTRAINT ur_abstractd_zone_id_fk FOREIGN KEY (zone_id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractdistrictplan ADD CONSTRAINT ur_abstractdist_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_abstractdistrictplan ADD CONSTRAINT ur_abstractdistrictplan_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_areaclassification ADD CONSTRAINT ur_areaclassification_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_boundary 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_boundary ADD CONSTRAINT ur_boundary_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_censusblock ADD CONSTRAINT ur_censusblock_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilities_2 ADD CONSTRAINT ur_collectivefacilities_2_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_3 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilities_3 ADD CONSTRAINT ur_collectivefacilities_3_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilitiesfo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilitiesfo ADD CONSTRAINT ur_collectivefacilitiesfo_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivegovernmentan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivegovernmentan ADD CONSTRAINT ur_collectivegovernmentan_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectivehousingfacil 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivehousingfacil ADD CONSTRAINT ur_collectivehousingfacil_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_collectiveurbandisaste 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectiveurbandisaste ADD CONSTRAINT ur_collectiveurbandisaste_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_developmentproject ADD CONSTRAINT ur_developmentproject_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterdamage ADD CONSTRAINT ur_disasterdamage_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterp_to_zonaldisa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterp_to_zonaldisa ADD CONSTRAINT ur_disasterp_disasterpre_fk FOREIGN KEY (disasterpreventionbl_2_id)
REFERENCES ur_disasterpreventionbl_2 (id)
ON DELETE CASCADE;

ALTER TABLE ur_disasterp_to_zonaldisa ADD CONSTRAINT ur_disasterp_zonaldisast_fk FOREIGN KEY (zonaldisasterpreventio_id)
REFERENCES ur_zonaldisasterpreventio (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbase 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbase ADD CONSTRAINT ur_disasterpreventionbase_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbl_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbl_2 ADD CONSTRAINT ur_disasterpreventionbl_2_fk FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbloc 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbloc ADD CONSTRAINT ur_disasterpreventionbloc_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesspa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_distributionbusinesspa ADD CONSTRAINT ur_distributionbusinesspa_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesszo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_distributionbusinesszo ADD CONSTRAINT ur_distributionbusinesszo_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_district 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_district ADD CONSTRAINT ur_district_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_district 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtd_to_district ADD CONSTRAINT ur_districtd_district_id_fk FOREIGN KEY (district_id)
REFERENCES ur_district (id)
ON DELETE CASCADE;

ALTER TABLE ur_districtd_to_district ADD CONSTRAINT ur_districtd_districtdev_fk FOREIGN KEY (districtdevelopmentpla_id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtd_to_zone ADD CONSTRAINT ur_districtd_districtdev_fk_2 FOREIGN KEY (districtdevelopmentpla_id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

ALTER TABLE ur_districtd_to_zone ADD CONSTRAINT ur_districtd_zone_id_fk FOREIGN KEY (zone_id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtdevelopmentpla ADD CONSTRAINT ur_districtdeve_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_districtdevelopmentpla ADD CONSTRAINT ur_districtdevelopmentpla_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtplan ADD CONSTRAINT ur_districtplan_fk FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtsandzones ADD CONSTRAINT ur_districtsand_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_districtsandzones ADD CONSTRAINT ur_districtsandzones_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_exceptionalfloorareara 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_exceptionalfloorareara ADD CONSTRAINT ur_exceptionalfloorareara_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_globalhubcitydevelopme 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_globalhubcitydevelopme ADD CONSTRAINT ur_globalhubcitydevelopme_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_heightcontroldistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_heightcontroldistrict ADD CONSTRAINT ur_heightcontroldistrict_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_highlevelusedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_highlevelusedistrict ADD CONSTRAINT ur_highlevelusedistrict_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_highriseresidentialatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_highriseresidentialatt ADD CONSTRAINT ur_highriseresidentialatt_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_historicscenerymainten 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_historicscenerymainten ADD CONSTRAINT ur_historicscenerymainten_fk FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_industrialparkdevelopm 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_industrialparkdevelopm ADD CONSTRAINT ur_industrialparkdevelopm_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landreadjustmentprojec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landreadjustmentprojec ADD CONSTRAINT ur_landreadjustmentprojec_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_landscapezone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landscapezone ADD CONSTRAINT ur_landscapezone_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_newhousingandurbandeve 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_newhousingandurbandeve ADD CONSTRAINT ur_newhousingandurbandeve_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_newurbaninfrastructure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_newurbaninfrastructure ADD CONSTRAINT ur_newurbaninfrastructure_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds_2 ADD CONSTRAINT ur_numberofh_censusblock_fk FOREIGN KEY (censusblock_numberofhouse__2)
REFERENCES ur_censusblock (id);

ALTER TABLE ur_numberofhouseholds_2 ADD CONSTRAINT ur_numberofh_censusblock_fk_2 FOREIGN KEY (censusblock_numberofhouse_id)
REFERENCES ur_censusblock (id);

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_openspaceforpublicuse ADD CONSTRAINT ur_openspaceforpublicuse_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_openspaceforpublicuse ADD CONSTRAINT ur_openspace_parkattribu_fk FOREIGN KEY (parkattribute_id)
REFERENCES ur_parkattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pollution ADD CONSTRAINT ur_pollution_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_portzone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portzone ADD CONSTRAINT ur_portzone_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_privateurbanrenewalpro 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_privateurbanrenewalpro ADD CONSTRAINT ur_privateurbanrenewalpro_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_productivegreenzone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_productivegreenzone ADD CONSTRAINT ur_productivegreenzone_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_projectpromotionarea ADD CONSTRAINT ur_projectpromo_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_projectpromotionarea ADD CONSTRAINT ur_projectpromotionarea_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_publictransitfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitfacility ADD CONSTRAINT ur_publictransitfacility_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_quasiurbanplanningarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_quasiurbanplanningarea ADD CONSTRAINT ur_quasiurbanplanningarea_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_recreations ADD CONSTRAINT ur_recreations_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_residentialblockconstr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_residentialblockconstr ADD CONSTRAINT ur_residentialblockconstr_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_residentialenvironment 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_residentialenvironment ADD CONSTRAINT ur_residentialenvironment_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictimprov 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadsidedistrictimprov ADD CONSTRAINT ur_roadsidedistrictimprov_fk FOREIGN KEY (id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadsidedistrictplan ADD CONSTRAINT ur_roadsidedistrictplan_fk FOREIGN KEY (id)
REFERENCES ur_abstractdistrictplan (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_ruraldistrictimproveme 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ruraldistrictimproveme ADD CONSTRAINT ur_ruraldistrictimproveme_fk FOREIGN KEY (id)
REFERENCES ur_districtdevelopmentpla (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_scenicdistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_scenicdistrict ADD CONSTRAINT ur_scenicdistrict_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_scheduledareaforurband ADD CONSTRAINT ur_scheduledare_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_scheduledareaforurband ADD CONSTRAINT ur_scheduledareaforurband_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_sedimentdisasterpronea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_sedimentdisasterpronea ADD CONSTRAINT ur_sedimentdisasterpronea_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialgreenspaceconse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialgreenspaceconse ADD CONSTRAINT ur_specialgreenspaceconse_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialurbanrenaissanc 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialurbanrenaissanc ADD CONSTRAINT ur_specialurbanrenaissanc_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialuseattractiondi 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialuseattractiondi ADD CONSTRAINT ur_specialuseattractiondi_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialusedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialusedistrict ADD CONSTRAINT ur_specialusedistrict_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specialuserestrictiond 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialuserestrictiond ADD CONSTRAINT ur_specialuserestrictiond_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specified_to_globalhub 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specified_to_globalhub ADD CONSTRAINT ur_specified_globalhubci_fk FOREIGN KEY (globalhubcitydevelopme_id)
REFERENCES ur_globalhubcitydevelopme (id)
ON DELETE CASCADE;

ALTER TABLE ur_specified_to_globalhub ADD CONSTRAINT ur_specified_specifiedur_fk FOREIGN KEY (specifiedurgenturbanre_id)
REFERENCES ur_specifiedurgenturbanre (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specifiedblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifiedblock ADD CONSTRAINT ur_specifiedblock_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specifieddisasterpreve 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifieddisasterpreve ADD CONSTRAINT ur_specifieddisasterpreve_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_specifiedurgenturbanre 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifiedurgenturbanre ADD CONSTRAINT ur_specifiedurgenturbanre_fk FOREIGN KEY (id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_supplyfacility ADD CONSTRAINT ur_supplyfacility_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_supplyfacility ADD CONSTRAINT ur_supplyfac_waterworksa_fk FOREIGN KEY (waterworksattribute_id)
REFERENCES ur_waterworksattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_threedimensionalextent 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_threedimensionalextent ADD CONSTRAINT ur_threedimensionalextent_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfacility_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_parkingplac_fk FOREIGN KEY (parkingplaceattribute_id)
REFERENCES ur_parkingplaceattribute (id)
ON DELETE SET NULL;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_urbanrapidt_fk FOREIGN KEY (urbanrapidtransitrailroad_id)
REFERENCES ur_urbanrapidtransitrailr (id)
ON DELETE SET NULL;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_urbanroadat_fk FOREIGN KEY (urbanroadattribute_id)
REFERENCES ur_urbanroadattribute (id)
ON DELETE SET NULL;

ALTER TABLE ur_trafficfacility ADD CONSTRAINT ur_trafficfa_vehicleterm_fk FOREIGN KEY (vehicleterminalattribute_id)
REFERENCES ur_vehicleterminalattribu (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_treatmentfacility ADD CONSTRAINT ur_treatmentfacility_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

ALTER TABLE ur_treatmentfacility ADD CONSTRAINT ur_treatment_sewersystem_fk FOREIGN KEY (sewersystemsattribute_id)
REFERENCES ur_sewersystemattribute (id)
ON DELETE SET NULL;

-- -------------------------------------------------------------------- 
-- ur_treeplantingdistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_treeplantingdistrict ADD CONSTRAINT ur_treeplantingdistrict_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbandevelopmentprojec ADD CONSTRAINT ur_urbandevelop_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbandevelopmentprojec ADD CONSTRAINT ur_urbandevelopmentprojec_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbandisasterrecoveryp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbandisasterrecoveryp ADD CONSTRAINT ur_urbandisasterrecoveryp_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfaci_to_threedime 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfaci_to_threedime ADD CONSTRAINT ur_urbanfaci_threedimens_fk FOREIGN KEY (threedimensionalextent_id)
REFERENCES ur_threedimensionalextent (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanfaci_to_threedime ADD CONSTRAINT ur_urbanfaci_urbanfacili_fk FOREIGN KEY (urbanfacility_id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfacility ADD CONSTRAINT ur_urbanfacilit_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbanfacility ADD CONSTRAINT ur_urbanfacility_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfacilitystipulate ADD CONSTRAINT ur_urbanfacilit_objectclass__2 FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbanfacilitystipulate ADD CONSTRAINT ur_urbanfacilitystipulate_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunc_to_cityobjec ADD CONSTRAINT ur_urbanfunc_cityobject__fk FOREIGN KEY (cityobject_id)
REFERENCES cityobject (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanfunc_to_cityobjec ADD CONSTRAINT ur_urbanfunc_urbanfuncti_fk FOREIGN KEY (urbanfunction_id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunctio_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunction_fk FOREIGN KEY (id)
REFERENCES cityobject (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod0multisu_fk FOREIGN KEY (lod0multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod1multisu_fk FOREIGN KEY (lod1multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod_1multis_fk FOREIGN KEY (lod_1multisurface_id)
REFERENCES surface_geometry (id);

ALTER TABLE ur_urbanfunction ADD CONSTRAINT ur_urbanfunc_lod_2multis_fk FOREIGN KEY (lod_2multisurface_id)
REFERENCES surface_geometry (id);

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanization ADD CONSTRAINT ur_urbanization_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanplanningarea ADD CONSTRAINT ur_urbanplanningarea_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanrapi_to_structure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanrapi_to_structure ADD CONSTRAINT ur_urbanrapi_structurede_fk FOREIGN KEY (structuredetails_id)
REFERENCES ur_structuredetails (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanrapi_to_structure ADD CONSTRAINT ur_urbanrapi_urbanrapidt_fk FOREIGN KEY (urbanrapidtransitrailr_id)
REFERENCES ur_urbanrapidtransitrailr (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentproj 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanredevelopmentproj ADD CONSTRAINT ur_urbanredevelopmentproj_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentprom 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanredevelopmentprom ADD CONSTRAINT ur_urbanredevelopmentprom_fk FOREIGN KEY (id)
REFERENCES ur_projectpromotionarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanrenewalproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanrenewalproject ADD CONSTRAINT ur_urbanrenewalproject_fk FOREIGN KEY (id)
REFERENCES ur_urbandevelopmentprojec (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urbanroad_to_structure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanroad_to_structure ADD CONSTRAINT ur_urbanroad_structurede_fk FOREIGN KEY (structuredetails_id)
REFERENCES ur_structuredetails (id)
ON DELETE CASCADE;

ALTER TABLE ur_urbanroad_to_structure ADD CONSTRAINT ur_urbanroad_urbanroadat_fk FOREIGN KEY (urbanroadattribute_id)
REFERENCES ur_urbanroadattribute (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_privateur 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_privateur ADD CONSTRAINT ur_urgenturb_privateurba_fk FOREIGN KEY (privateurbanrenewalpro_id)
REFERENCES ur_privateurbanrenewalpro (id)
ON DELETE CASCADE;

ALTER TABLE ur_urgenturb_to_privateur ADD CONSTRAINT ur_urgenturb_urgenturban_fk FOREIGN KEY (urgenturbanrenewalarea_id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specialur 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_specialur ADD CONSTRAINT ur_urgenturb_specialurba_fk FOREIGN KEY (specialurbanrenaissanc_id)
REFERENCES ur_specialurbanrenaissanc (id)
ON DELETE CASCADE;

ALTER TABLE ur_urgenturb_to_specialur ADD CONSTRAINT ur_urgenturb_urgenturban_fk_2 FOREIGN KEY (urgenturbanrenewalarea_id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specified 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_specified ADD CONSTRAINT ur_urgenturb_specifiedur_fk FOREIGN KEY (specifiedurgenturbanre_id)
REFERENCES ur_specifiedurgenturbanre (id)
ON DELETE CASCADE;

ALTER TABLE ur_urgenturb_to_specified ADD CONSTRAINT ur_urgenturb_urgenturban_fk_3 FOREIGN KEY (urgenturbanrenewalarea_id)
REFERENCES ur_urgenturbanrenewalarea (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturbanrenewalarea ADD CONSTRAINT ur_urgenturbanr_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_urgenturbanrenewalarea ADD CONSTRAINT ur_urgenturbanrenewalarea_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_usedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_usedistrict ADD CONSTRAINT ur_usedistrict_fk FOREIGN KEY (id)
REFERENCES ur_districtsandzones (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterway ADD CONSTRAINT ur_waterway_fk FOREIGN KEY (id)
REFERENCES ur_urbanfacility (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_zonaldisasterpreventio 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zonaldisasterpreventio ADD CONSTRAINT ur_zonaldisasterpreventio_fk FOREIGN KEY (id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone ADD CONSTRAINT ur_zone_objectclass_fk FOREIGN KEY (objectclass_id)
REFERENCES objectclass (id);

ALTER TABLE ur_zone ADD CONSTRAINT ur_zone_fk FOREIGN KEY (id)
REFERENCES ur_urbanfunction (id)
ON DELETE CASCADE;

-- -------------------------------------------------------------------- 
-- ur_zone_to_boundary 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone_to_boundary ADD CONSTRAINT ur_zone_to_b_boundary_id_fk FOREIGN KEY (boundary_id)
REFERENCES ur_boundary (id)
ON DELETE CASCADE;

ALTER TABLE ur_zone_to_boundary ADD CONSTRAINT ur_zone_to_b_zone_id_fk FOREIGN KEY (zone_id)
REFERENCES ur_zone (id)
ON DELETE CASCADE;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Create Indexes ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
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
-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
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
-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
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

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
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
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_waterbody_objectcla_fkx ON ur_waterbodyattribute
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_populatio_to_populatio 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_populatio_population_fkx ON ur_populatio_to_populatio
    USING btree
    (
      populationbyageandsex_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_populatio_population_fkx_2 ON ur_populatio_to_populatio
    USING btree
    (
      population_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landprice_landpricep_fkx ON ur_landprice_to_landprice
    USING btree
    (
      landpriceperlanduse_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_landprice_landprice__fkx ON ur_landprice_to_landprice
    USING btree
    (
      landprice_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landusedi_numberofan_fkx ON ur_landusedi_to_numberofa
    USING btree
    (
      numberofannualdiversio_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_landusedi_landusediv_fkx ON ur_landusedi_to_numberofa
    USING btree
    (
      landusediversion_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_landusedi_areaofannu_fkx ON ur_landusedi_to_areaofann
    USING btree
    (
      areaofannualdiversions_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_landusedi_landusediv_fkx_3 ON ur_landusedi_to_areaofann
    USING btree
    (
      landusediversion_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_statistic_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_statistic_keyvaluepa_fkx ON ur_statistic_to_keyvaluep
    USING btree
    (
      keyvaluepair_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_statistic_statistica_fkx ON ur_statistic_to_keyvaluep
    USING btree
    (
      statisticalgrid_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_publictra_to_point 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_publictra_point_id_fkx ON ur_publictra_to_point
    USING btree
    (
      point_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_publictra_publictran_fkx ON ur_publictra_to_point
    USING btree
    (
      publictransit_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_building__buildingdi_fkx ON ur_building_to_buildingd
    USING btree
    (
      buildingdisasterriskat_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_building__building_i_fkx ON ur_building_to_buildingd
    USING btree
    (
      building_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_building__keyvaluepa_fkx ON ur_building_to_keyvaluep
    USING btree
    (
      keyvaluepairattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_building__building_i_fkx_3 ON ur_building_to_keyvaluep
    USING btree
    (
      building_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_waterbody_waterbodya_fkx ON ur_waterbody_to_waterbody
    USING btree
    (
      waterbodyattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_waterbody_waterbody__fkx ON ur_waterbody_to_waterbody
    USING btree
    (
      waterbody_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_cityobjec_publictran_fkx ON ur_cityobjec_to_publictra
    USING btree
    (
      publictransitdatatype_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_cityobjec_cityobject_fkx ON ur_cityobjec_to_publictra
    USING btree
    (
      cityobjectgroup_3_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_abstractd_objectclas_fkx ON ur_abstractdistrictplan
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_districtd_objectclas_fkx ON ur_districtdevelopmentpla
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_districts_objectclas_fkx ON ur_districtsandzones
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_numberofh_censusbloc_fkx ON ur_numberofhouseholds_2
    USING btree
    (
      censusblock_numberofhouse__2 ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_numberofh_censusbloc_fkx_2 ON ur_numberofhouseholds_2
    USING btree
    (
      censusblock_numberofhouse_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_openspace_parkattrib_fkx ON ur_openspaceforpublicuse
    USING btree
    (
      parkattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_projectpr_objectclas_fkx ON ur_projectpromotionarea
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_scheduled_objectclas_fkx ON ur_scheduledareaforurband
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_supplyfac_waterworks_fkx ON ur_supplyfacility
    USING btree
    (
      waterworksattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_trafficfa_parkingpla_fkx ON ur_trafficfacility
    USING btree
    (
      parkingplaceattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_trafficfa_urbanrapid_fkx ON ur_trafficfacility
    USING btree
    (
      urbanrapidtransitrailroad_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_trafficfa_urbanroada_fkx ON ur_trafficfacility
    USING btree
    (
      urbanroadattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_trafficfa_vehicleter_fkx ON ur_trafficfacility
    USING btree
    (
      vehicleterminalattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_treatment_sewersyste_fkx ON ur_treatmentfacility
    USING btree
    (
      sewersystemsattribute_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbandeve_objectclas_fkx ON ur_urbandevelopmentprojec
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfaci_objectclas_fkx ON ur_urbanfacility
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfaci_objectclas_fkx_2 ON ur_urbanfacilitystipulate
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urbanfunc_lod0multic_spx ON ur_urbanfunction
    USING gist
    (
      lod0multicurve
    );

CREATE INDEX ur_urbanfunc_lod0multip_spx ON ur_urbanfunction
    USING gist
    (
      lod0multipoint
    );

CREATE INDEX ur_urbanfunc_lod0multis_fkx ON ur_urbanfunction
    USING btree
    (
      lod0multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfunc_lod1multic_spx ON ur_urbanfunction
    USING gist
    (
      lod1multicurve
    );

CREATE INDEX ur_urbanfunc_lod1multis_fkx ON ur_urbanfunction
    USING btree
    (
      lod1multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfunc_lod_1multi_spx ON ur_urbanfunction
    USING gist
    (
      lod_1multicurve
    );

CREATE INDEX ur_urbanfunc_lod_1multi_spx_2 ON ur_urbanfunction
    USING gist
    (
      lod_1multipoint
    );

CREATE INDEX ur_urbanfunc_lod_1multi_fkx ON ur_urbanfunction
    USING btree
    (
      lod_1multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfunc_lod_2multi_spx ON ur_urbanfunction
    USING gist
    (
      lod_2multicurve
    );

CREATE INDEX ur_urbanfunc_lod_2multi_spx_2 ON ur_urbanfunction
    USING gist
    (
      lod_2multipoint
    );

CREATE INDEX ur_urbanfunc_lod_2multi_fkx ON ur_urbanfunction
    USING btree
    (
      lod_2multisurface_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX ur_urbanfunc_objectclas_fkx ON ur_urbanfunction
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_urgenturb_objectclas_fkx ON ur_urgenturbanrenewalarea
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
CREATE INDEX ur_zone_objectclas_fkx ON ur_zone
    USING btree
    (
      objectclass_id ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

