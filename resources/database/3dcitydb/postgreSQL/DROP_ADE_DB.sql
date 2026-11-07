-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2026-11-07 11:28:53 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop foreign keys ********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_agency
    DROP CONSTRAINT ur_agency_fk;

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_areaofannualdiversions
    DROP CONSTRAINT ur_areaof_landus_areaof_fk;

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_attribution
    DROP CONSTRAINT ur_attribution_fk;

ALTER TABLE ur_attribution
    DROP CONSTRAINT ur_attribution_route_fk;

ALTER TABLE ur_attribution
    DROP CONSTRAINT ur_attribution_agency_fk;

ALTER TABLE ur_attribution
    DROP CONSTRAINT ur_attribution_trip_fk;

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_buildingida_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_buildingdet_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_largecustom_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_buildingdat_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingattribute
    DROP CONSTRAINT ur_buildingat_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingdataqualityatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdataqualityatt
    DROP CONSTRAINT ur_buildingdataqualitya_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdetailattribut
    DROP CONSTRAINT ur_buildingdetailattrib_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdisasterriskat
    DROP CONSTRAINT ur_buildingdi_objectcla_fk;

ALTER TABLE ur_buildingdisasterriskat
    DROP CONSTRAINT ur_buildingdisasterrisk_fk;

ALTER TABLE ur_buildingdisasterriskat
    DROP CONSTRAINT ur_buildi_buildi_buildi_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingfloodingriskat
    DROP CONSTRAINT ur_buildingfl_objectcla_fk;

ALTER TABLE ur_buildingfloodingriskat
    DROP CONSTRAINT ur_buildingfloodingrisk_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingidattribute
    DROP CONSTRAINT ur_buildingidattribute_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingriverfloodingr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingriverfloodingr
    DROP CONSTRAINT ur_buildingriverfloodin_fk;

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendar
    DROP CONSTRAINT ur_calendar_fk;

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendardate
    DROP CONSTRAINT ur_calendardate_fk;

ALTER TABLE ur_calendardate
    DROP CONSTRAINT ur_calendardat_calendar_fk;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_censusblock
    DROP CONSTRAINT ur_censusblock_fk;

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_city_furniture
    DROP CONSTRAINT ur_city_furniture_fk;

ALTER TABLE ur_city_furniture
    DROP CONSTRAINT ur_city_furnitu_cityfur_fk;

ALTER TABLE ur_city_furniture
    DROP CONSTRAINT ur_city_furnit_cityfu_fk_1;

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityfurnitureattribute
    DROP CONSTRAINT ur_cityfurnit_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredataquali 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityfurnituredataquali
    DROP CONSTRAINT ur_cityfurnituredataqua_fk;

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredetailatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityfurnituredetailatt
    DROP CONSTRAINT ur_cityfurnituredetaila_fk;

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobject_fk;

ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobject_vegetatio_fk;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup
    DROP CONSTRAINT ur_cityobjectgroup_fk;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_1 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_1
    DROP CONSTRAINT ur_cityobjectgroup_fk_1;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_2
    DROP CONSTRAINT ur_cityobjectgroup_fk_2;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_3 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjectgroup_3
    DROP CONSTRAINT ur_cityobjectgroup_fk_3;

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_developmentproject
    DROP CONSTRAINT ur_developmentproject_fk;

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterdamage
    DROP CONSTRAINT ur_disasterdamage_fk;

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fareattribute
    DROP CONSTRAINT ur_fareattribute_fk;

ALTER TABLE ur_fareattribute
    DROP CONSTRAINT ur_fareattribute_agency_fk;

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_farerule
    DROP CONSTRAINT ur_farerule_fk;

ALTER TABLE ur_farerule
    DROP CONSTRAINT ur_farerule_route_fk;

ALTER TABLE ur_farerule
    DROP CONSTRAINT ur_farerule_fare_fk;

-- -------------------------------------------------------------------- 
-- ur_feedinfo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_feedinfo
    DROP CONSTRAINT ur_feedinfo_fk;

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_frequency
    DROP CONSTRAINT ur_frequency_fk;

ALTER TABLE ur_frequency
    DROP CONSTRAINT ur_frequency_trip_fk;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_households
    DROP CONSTRAINT ur_households_fk;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_keyvaluepair
    DROP CONSTRAINT ur_keyval_statis_generi_fk;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyval_buildi_keyval_fk;

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_fk;

ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_landusedeta_fk;

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice
    DROP CONSTRAINT ur_landprice_fk;

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landpriceperlanduse
    DROP CONSTRAINT ur_landpr_landpr_landpr_fk;

-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusediversion
    DROP CONSTRAINT ur_landusediversion_fk;

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_largecustomerfacilitya
    DROP CONSTRAINT ur_largecustomerfacilit_fk;

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofannualdiversio
    DROP CONSTRAINT ur_number_landus_number_fk;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds
    DROP CONSTRAINT ur_number_househ_number_fk;

ALTER TABLE ur_numberofhouseholds
    DROP CONSTRAINT ur_number_house_numbe_fk_1;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds_1
    DROP CONSTRAINT ur_number_census_number_fk;

ALTER TABLE ur_numberofhouseholds_1
    DROP CONSTRAINT ur_number_censu_numbe_fk_1;

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_office
    DROP CONSTRAINT ur_office_fk;

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_officesandemployees
    DROP CONSTRAINT ur_officesandemployees_fk;

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pathway
    DROP CONSTRAINT ur_pathway_fk;

ALTER TABLE ur_pathway
    DROP CONSTRAINT ur_pathway_from_fk;

ALTER TABLE ur_pathway
    DROP CONSTRAINT ur_pathway_to_fk;

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_point
    DROP CONSTRAINT ur_point_publictr_point_fk;

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pollution
    DROP CONSTRAINT ur_pollution_fk;

-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_population
    DROP CONSTRAINT ur_population_fk;

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_populationbyageandsex
    DROP CONSTRAINT ur_popula_popula_popula_fk;

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransit
    DROP CONSTRAINT ur_publictran_objectcla_fk;

ALTER TABLE ur_publictransit
    DROP CONSTRAINT ur_publictransit_fk;

ALTER TABLE ur_publictransit
    DROP CONSTRAINT ur_publictransit_target_fk;

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitdatatype
    DROP CONSTRAINT ur_publictra_objectcl_fk_1;

ALTER TABLE ur_publictransitdatatype
    DROP CONSTRAINT ur_public_cityob_dataty_fk;

-- -------------------------------------------------------------------- 
-- ur_publictransportationfa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransportationfa
    DROP CONSTRAINT ur_publictransportation_fk;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_recreations
    DROP CONSTRAINT ur_recreations_fk;

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadattribute
    DROP CONSTRAINT ur_roadattrib_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_roaddataqualityattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roaddataqualityattribu
    DROP CONSTRAINT ur_roaddataqualityattri_fk;

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadstructureattribute
    DROP CONSTRAINT ur_roadstructureattribu_fk;

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_route
    DROP CONSTRAINT ur_route_fk;

ALTER TABLE ur_route
    DROP CONSTRAINT ur_route_parentroute_fk;

ALTER TABLE ur_route
    DROP CONSTRAINT ur_route_description_fk;

ALTER TABLE ur_route
    DROP CONSTRAINT ur_route_agency_fk;

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statisticalgrid_fk;

ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statistica_objectcla_fk;

ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statisti_lod_1multis_fk;

ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statisti_lod_2multis_fk;

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stop
    DROP CONSTRAINT ur_stop_fk;

ALTER TABLE ur_stop
    DROP CONSTRAINT ur_stop_parentstation_fk;

ALTER TABLE ur_stop
    DROP CONSTRAINT ur_stop_level_fk;

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stoptime
    DROP CONSTRAINT ur_stoptime_fk;

ALTER TABLE ur_stoptime
    DROP CONSTRAINT ur_stoptime_stop_fk;

ALTER TABLE ur_stoptime
    DROP CONSTRAINT ur_stoptime_trip_fk;

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_traffic_area
    DROP CONSTRAINT ur_traffic_area_fk;

ALTER TABLE ur_traffic_area
    DROP CONSTRAINT ur_traffic_area_traffic_fk;

-- -------------------------------------------------------------------- 
-- ur_trafficvolumeattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trafficvolumeattribute
    DROP CONSTRAINT ur_trafficvolumeattribu_fk;

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transfer
    DROP CONSTRAINT ur_transfer_fk;

ALTER TABLE ur_transfer
    DROP CONSTRAINT ur_transfer_from_fk;

ALTER TABLE ur_transfer
    DROP CONSTRAINT ur_transfer_to_fk;

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_translation
    DROP CONSTRAINT ur_translation_fk;

ALTER TABLE ur_translation
    DROP CONSTRAINT ur_translation_recordid_fk;

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_translationjp
    DROP CONSTRAINT ur_translationjp_fk;

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transportati_complex_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_traffi_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_roadst_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_roadda_fk;

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_shape_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_route_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_calendar_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_calendardate_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_office_fk;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunc_to_cityobjec
    DROP CONSTRAINT ur_urbanfun_to_cityobj_fk1;

ALTER TABLE ur_urbanfunc_to_cityobjec
    DROP CONSTRAINT ur_urbanfun_to_cityobj_fk2;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunct_objectcla_fk;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunction_fk;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunct_legalgrou_fk;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunct_lod0multi_fk;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfun_lod_1multis_fk;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfun_lod_2multis_fk;

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanization
    DROP CONSTRAINT ur_urbanization_fk;

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody
    DROP CONSTRAINT ur_waterbody_fk;

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyattribute
    DROP CONSTRAINT ur_waterbodya_objectcla_fk;

ALTER TABLE ur_waterbodyattribute
    DROP CONSTRAINT ur_waterb_waterb_floodi_fk;

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyriverflooding
    DROP CONSTRAINT ur_waterbodyriverfloodi_fk;

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone
    DROP CONSTRAINT ur_zone_objectclass_fk;

ALTER TABLE ur_zone
    DROP CONSTRAINT ur_zone_fk;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop tables *************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_agency;

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
DROP TABLE ur_areaofannualdiversions;

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
DROP TABLE ur_attribution;

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building;

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingattribute;

-- -------------------------------------------------------------------- 
-- ur_buildingdataqualityatt 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingdataqualityatt;

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingdetailattribut;

-- -------------------------------------------------------------------- 
-- ur_buildingdisasterriskat 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingdisasterriskat;

-- -------------------------------------------------------------------- 
-- ur_buildingfloodingriskat 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingfloodingriskat;

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingidattribute;

-- -------------------------------------------------------------------- 
-- ur_buildingriverfloodingr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingriverfloodingr;

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
DROP TABLE ur_calendar;

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
DROP TABLE ur_calendardate;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
DROP TABLE ur_censusblock;

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
DROP TABLE ur_city_furniture;

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityfurnitureattribute;

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredataquali 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityfurnituredataquali;

-- -------------------------------------------------------------------- 
-- ur_cityfurnituredetailatt 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityfurnituredetailatt;

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobject;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjectgroup;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_1 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjectgroup_1;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjectgroup_2;

-- -------------------------------------------------------------------- 
-- ur_cityobjectgroup_3 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjectgroup_3;

-- -------------------------------------------------------------------- 
-- ur_description 
-- -------------------------------------------------------------------- 
DROP TABLE ur_description;

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
DROP TABLE ur_developmentproject;

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
DROP TABLE ur_disasterdamage;

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_fareattribute;

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
DROP TABLE ur_farerule;

-- -------------------------------------------------------------------- 
-- ur_feedinfo 
-- -------------------------------------------------------------------- 
DROP TABLE ur_feedinfo;

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_frequency;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
DROP TABLE ur_households;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
DROP TABLE ur_keyvaluepair;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_keyvaluepairattribute;

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
DROP TABLE ur_land_use;

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landprice;

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landpriceperlanduse;

-- -------------------------------------------------------------------- 
-- ur_landuseattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landuseattribute;

-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landusediversion;

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_largecustomerfacilitya;

-- -------------------------------------------------------------------- 
-- ur_legalgrounds 
-- -------------------------------------------------------------------- 
DROP TABLE ur_legalgrounds;

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofannualdiversio;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofhouseholds;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofhouseholds_1;

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
DROP TABLE ur_office;

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
DROP TABLE ur_officesandemployees;

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pathway;

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
DROP TABLE ur_point;

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pollution;

-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
DROP TABLE ur_population;

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
DROP TABLE ur_populationbyageandsex;

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictransit;

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictransitdatatype;

-- -------------------------------------------------------------------- 
-- ur_publictransportationfa 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictransportationfa;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
DROP TABLE ur_recreations;

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roadattribute;

-- -------------------------------------------------------------------- 
-- ur_roaddataqualityattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roaddataqualityattribu;

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roadstructureattribute;

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
DROP TABLE ur_route;

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
DROP TABLE ur_statisticalgrid;

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stop;

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stoptime;

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
DROP TABLE ur_traffic_area;

-- -------------------------------------------------------------------- 
-- ur_trafficareaattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trafficareaattribute;

-- -------------------------------------------------------------------- 
-- ur_trafficvolumeattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trafficvolumeattribute;

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transfer;

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
DROP TABLE ur_translation;

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
DROP TABLE ur_translationjp;

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transportation_complex;

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trip;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfunc_to_cityobjec;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfunction;

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanization;

-- -------------------------------------------------------------------- 
-- ur_vegetationattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_vegetationattribute;

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody;

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbodyattribute;

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbodyriverflooding;

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_zone;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop Sequences ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

DROP SEQUENCE ur_areaofannualdivers_seq;

DROP SEQUENCE ur_point_seq;

DROP SEQUENCE ur_numberofhouseholds_seq;

DROP SEQUENCE ur_keyvaluepair_seq;

DROP SEQUENCE ur_trafficareaattribu_seq;

DROP SEQUENCE ur_cityfurnitureattri_seq;

DROP SEQUENCE ur_description_seq;

DROP SEQUENCE ur_publictransitdatat_seq;

DROP SEQUENCE ur_numberofannualdive_seq;

DROP SEQUENCE ur_waterbodyattribute_seq;

DROP SEQUENCE ur_landuseattribute_seq;

DROP SEQUENCE ur_roadattribute_seq;

DROP SEQUENCE ur_landpriceperlandus_seq;

DROP SEQUENCE ur_keyvaluepairattrib_seq;

DROP SEQUENCE ur_buildingattribute_seq;

DROP SEQUENCE ur_legalgrounds_seq;

DROP SEQUENCE ur_vegetationattribut_seq;

DROP SEQUENCE ur_populationbyageand_seq;

DROP SEQUENCE ur_numberofhousehol_seq_1;
