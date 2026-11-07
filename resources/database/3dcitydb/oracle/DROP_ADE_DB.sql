-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2026-11-07 11:28:53 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop foreign keys ********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
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
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_buildingd
    DROP CONSTRAINT ur_building__buildingdis_fk_2;

ALTER TABLE ur_building_to_buildingd
    DROP CONSTRAINT ur_building__building_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_keyvaluep
    DROP CONSTRAINT ur_building__keyvaluepai_fk_2;

ALTER TABLE ur_building_to_keyvaluep
    DROP CONSTRAINT ur_building__building_id_fk_4;

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
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjec_to_publictra
    DROP CONSTRAINT ur_cityobjec_publictrans_fk_2;

ALTER TABLE ur_cityobjec_to_publictra
    DROP CONSTRAINT ur_cityobjec_cityobjectg_fk_2;

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
-- ur_land_use 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_fk;

ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_landusedeta_fk;

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_largecustomerfacilitya
    DROP CONSTRAINT ur_largecustomerfacilit_fk;

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
-- ur_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody
    DROP CONSTRAINT ur_waterbody_fk;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody_to_waterbody
    DROP CONSTRAINT ur_waterbody_waterbodyat_fk_2;

ALTER TABLE ur_waterbody_to_waterbody
    DROP CONSTRAINT ur_waterbody_waterbody_i_fk_2;

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyattribute
    DROP CONSTRAINT ur_waterbodya_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbodyriverflooding
    DROP CONSTRAINT ur_waterbodyriverfloodi_fk;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_districtd 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractd_to_districtd
    DROP CONSTRAINT ur_abstractd_abstractdis_fk_3;

ALTER TABLE ur_abstractd_to_districtd
    DROP CONSTRAINT ur_abstractd_districtdev_fk_2;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractd_to_zone
    DROP CONSTRAINT ur_abstractd_abstractdis_fk_4;

ALTER TABLE ur_abstractd_to_zone
    DROP CONSTRAINT ur_abstractd_zone_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_abstractdistrictplan
    DROP CONSTRAINT ur_abstractdist_objectclass__2;

ALTER TABLE ur_abstractdistrictplan
    DROP CONSTRAINT ur_abstractdistrictplan_fk_2;

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_areaclassification
    DROP CONSTRAINT ur_areaclassification_fk_2;

-- -------------------------------------------------------------------- 
-- ur_boundary 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_boundary
    DROP CONSTRAINT ur_boundary_fk_2;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_censusblock
    DROP CONSTRAINT ur_censusblock_fk_2;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilities_2
    DROP CONSTRAINT ur_collectivefacilities_2_fk_2;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_3 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilities_3
    DROP CONSTRAINT ur_collectivefacilities_3_fk_2;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilitiesfo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivefacilitiesfo
    DROP CONSTRAINT ur_collectivefacilitiesfo_fk_2;

-- -------------------------------------------------------------------- 
-- ur_collectivegovernmentan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivegovernmentan
    DROP CONSTRAINT ur_collectivegovernmentan_fk_2;

-- -------------------------------------------------------------------- 
-- ur_collectivehousingfacil 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectivehousingfacil
    DROP CONSTRAINT ur_collectivehousingfacil_fk_2;

-- -------------------------------------------------------------------- 
-- ur_collectiveurbandisaste 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_collectiveurbandisaste
    DROP CONSTRAINT ur_collectiveurbandisaste_fk_2;

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_developmentproject
    DROP CONSTRAINT ur_developmentproject_fk_2;

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterdamage
    DROP CONSTRAINT ur_disasterdamage_fk_2;

-- -------------------------------------------------------------------- 
-- ur_disasterp_to_zonaldisa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterp_to_zonaldisa
    DROP CONSTRAINT ur_disasterp_disasterpre_fk_2;

ALTER TABLE ur_disasterp_to_zonaldisa
    DROP CONSTRAINT ur_disasterp_zonaldisast_fk_2;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbase 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbase
    DROP CONSTRAINT ur_disasterpreventionbase_fk_2;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbl_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbl_2
    DROP CONSTRAINT ur_disasterpreventionbl_2_fk_2;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbloc 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterpreventionbloc
    DROP CONSTRAINT ur_disasterpreventionbloc_fk_2;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesspa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_distributionbusinesspa
    DROP CONSTRAINT ur_distributionbusinesspa_fk_2;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesszo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_distributionbusinesszo
    DROP CONSTRAINT ur_distributionbusinesszo_fk_2;

-- -------------------------------------------------------------------- 
-- ur_district 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_district
    DROP CONSTRAINT ur_district_fk_2;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_district 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtd_to_district
    DROP CONSTRAINT ur_districtd_district_id_fk_2;

ALTER TABLE ur_districtd_to_district
    DROP CONSTRAINT ur_districtd_districtdev_fk_3;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtd_to_zone
    DROP CONSTRAINT ur_districtd_districtdev_fk_4;

ALTER TABLE ur_districtd_to_zone
    DROP CONSTRAINT ur_districtd_zone_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtdevelopmentpla
    DROP CONSTRAINT ur_districtdeve_objectclass__2;

ALTER TABLE ur_districtdevelopmentpla
    DROP CONSTRAINT ur_districtdevelopmentpla_fk_2;

-- -------------------------------------------------------------------- 
-- ur_districtplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtplan
    DROP CONSTRAINT ur_districtplan_fk_2;

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_districtsandzones
    DROP CONSTRAINT ur_districtsand_objectclass__2;

ALTER TABLE ur_districtsandzones
    DROP CONSTRAINT ur_districtsandzones_fk_2;

-- -------------------------------------------------------------------- 
-- ur_exceptionalfloorareara 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_exceptionalfloorareara
    DROP CONSTRAINT ur_exceptionalfloorareara_fk_2;

-- -------------------------------------------------------------------- 
-- ur_globalhubcitydevelopme 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_globalhubcitydevelopme
    DROP CONSTRAINT ur_globalhubcitydevelopme_fk_2;

-- -------------------------------------------------------------------- 
-- ur_heightcontroldistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_heightcontroldistrict
    DROP CONSTRAINT ur_heightcontroldistrict_fk_2;

-- -------------------------------------------------------------------- 
-- ur_highlevelusedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_highlevelusedistrict
    DROP CONSTRAINT ur_highlevelusedistrict_fk_2;

-- -------------------------------------------------------------------- 
-- ur_highriseresidentialatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_highriseresidentialatt
    DROP CONSTRAINT ur_highriseresidentialatt_fk_2;

-- -------------------------------------------------------------------- 
-- ur_historicscenerymainten 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_historicscenerymainten
    DROP CONSTRAINT ur_historicscenerymainten_fk_2;

-- -------------------------------------------------------------------- 
-- ur_industrialparkdevelopm 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_industrialparkdevelopm
    DROP CONSTRAINT ur_industrialparkdevelopm_fk_2;

-- -------------------------------------------------------------------- 
-- ur_landreadjustmentprojec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landreadjustmentprojec
    DROP CONSTRAINT ur_landreadjustmentprojec_fk_2;

-- -------------------------------------------------------------------- 
-- ur_landscapezone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landscapezone
    DROP CONSTRAINT ur_landscapezone_fk_2;

-- -------------------------------------------------------------------- 
-- ur_newhousingandurbandeve 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_newhousingandurbandeve
    DROP CONSTRAINT ur_newhousingandurbandeve_fk_2;

-- -------------------------------------------------------------------- 
-- ur_newurbaninfrastructure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_newurbaninfrastructure
    DROP CONSTRAINT ur_newurbaninfrastructure_fk_2;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds_2
    DROP CONSTRAINT ur_numberofh_censusblock_fk_3;

ALTER TABLE ur_numberofhouseholds_2
    DROP CONSTRAINT ur_numberofh_censusblock_fk_4;

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_openspaceforpublicuse
    DROP CONSTRAINT ur_openspaceforpublicuse_fk_2;

ALTER TABLE ur_openspaceforpublicuse
    DROP CONSTRAINT ur_openspace_parkattribu_fk_2;

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pollution
    DROP CONSTRAINT ur_pollution_fk_2;

-- -------------------------------------------------------------------- 
-- ur_portzone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portzone
    DROP CONSTRAINT ur_portzone_fk_2;

-- -------------------------------------------------------------------- 
-- ur_privateurbanrenewalpro 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_privateurbanrenewalpro
    DROP CONSTRAINT ur_privateurbanrenewalpro_fk_2;

-- -------------------------------------------------------------------- 
-- ur_productivegreenzone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_productivegreenzone
    DROP CONSTRAINT ur_productivegreenzone_fk_2;

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_projectpromotionarea
    DROP CONSTRAINT ur_projectpromo_objectclass__2;

ALTER TABLE ur_projectpromotionarea
    DROP CONSTRAINT ur_projectpromotionarea_fk_2;

-- -------------------------------------------------------------------- 
-- ur_publictransitfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitfacility
    DROP CONSTRAINT ur_publictransitfacility_fk_2;

-- -------------------------------------------------------------------- 
-- ur_quasiurbanplanningarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_quasiurbanplanningarea
    DROP CONSTRAINT ur_quasiurbanplanningarea_fk_2;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_recreations
    DROP CONSTRAINT ur_recreations_fk_2;

-- -------------------------------------------------------------------- 
-- ur_residentialblockconstr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_residentialblockconstr
    DROP CONSTRAINT ur_residentialblockconstr_fk_2;

-- -------------------------------------------------------------------- 
-- ur_residentialenvironment 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_residentialenvironment
    DROP CONSTRAINT ur_residentialenvironment_fk_2;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictimprov 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadsidedistrictimprov
    DROP CONSTRAINT ur_roadsidedistrictimprov_fk_2;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadsidedistrictplan
    DROP CONSTRAINT ur_roadsidedistrictplan_fk_2;

-- -------------------------------------------------------------------- 
-- ur_ruraldistrictimproveme 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ruraldistrictimproveme
    DROP CONSTRAINT ur_ruraldistrictimproveme_fk_2;

-- -------------------------------------------------------------------- 
-- ur_scenicdistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_scenicdistrict
    DROP CONSTRAINT ur_scenicdistrict_fk_2;

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_scheduledareaforurband
    DROP CONSTRAINT ur_scheduledare_objectclass__2;

ALTER TABLE ur_scheduledareaforurband
    DROP CONSTRAINT ur_scheduledareaforurband_fk_2;

-- -------------------------------------------------------------------- 
-- ur_sedimentdisasterpronea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_sedimentdisasterpronea
    DROP CONSTRAINT ur_sedimentdisasterpronea_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specialgreenspaceconse 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialgreenspaceconse
    DROP CONSTRAINT ur_specialgreenspaceconse_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specialurbanrenaissanc 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialurbanrenaissanc
    DROP CONSTRAINT ur_specialurbanrenaissanc_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specialuseattractiondi 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialuseattractiondi
    DROP CONSTRAINT ur_specialuseattractiondi_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specialusedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialusedistrict
    DROP CONSTRAINT ur_specialusedistrict_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specialuserestrictiond 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specialuserestrictiond
    DROP CONSTRAINT ur_specialuserestrictiond_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specified_to_globalhub 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specified_to_globalhub
    DROP CONSTRAINT ur_specified_globalhubci_fk_2;

ALTER TABLE ur_specified_to_globalhub
    DROP CONSTRAINT ur_specified_specifiedur_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specifiedblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifiedblock
    DROP CONSTRAINT ur_specifiedblock_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specifieddisasterpreve 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifieddisasterpreve
    DROP CONSTRAINT ur_specifieddisasterpreve_fk_2;

-- -------------------------------------------------------------------- 
-- ur_specifiedurgenturbanre 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_specifiedurgenturbanre
    DROP CONSTRAINT ur_specifiedurgenturbanre_fk_2;

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_supplyfacility
    DROP CONSTRAINT ur_supplyfacility_fk_2;

ALTER TABLE ur_supplyfacility
    DROP CONSTRAINT ur_supplyfac_waterworksa_fk_2;

-- -------------------------------------------------------------------- 
-- ur_threedimensionalextent 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_threedimensionalextent
    DROP CONSTRAINT ur_threedimensionalextent_fk_2;

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trafficfacility
    DROP CONSTRAINT ur_trafficfacility_fk_2;

ALTER TABLE ur_trafficfacility
    DROP CONSTRAINT ur_trafficfa_parkingplac_fk_2;

ALTER TABLE ur_trafficfacility
    DROP CONSTRAINT ur_trafficfa_urbanrapidt_fk_2;

ALTER TABLE ur_trafficfacility
    DROP CONSTRAINT ur_trafficfa_urbanroadat_fk_2;

ALTER TABLE ur_trafficfacility
    DROP CONSTRAINT ur_trafficfa_vehicleterm_fk_2;

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_treatmentfacility
    DROP CONSTRAINT ur_treatmentfacility_fk_2;

ALTER TABLE ur_treatmentfacility
    DROP CONSTRAINT ur_treatment_sewersystem_fk_2;

-- -------------------------------------------------------------------- 
-- ur_treeplantingdistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_treeplantingdistrict
    DROP CONSTRAINT ur_treeplantingdistrict_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbandevelopmentprojec
    DROP CONSTRAINT ur_urbandevelop_objectclass__2;

ALTER TABLE ur_urbandevelopmentprojec
    DROP CONSTRAINT ur_urbandevelopmentprojec_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbandisasterrecoveryp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbandisasterrecoveryp
    DROP CONSTRAINT ur_urbandisasterrecoveryp_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanfaci_to_threedime 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfaci_to_threedime
    DROP CONSTRAINT ur_urbanfaci_threedimens_fk_2;

ALTER TABLE ur_urbanfaci_to_threedime
    DROP CONSTRAINT ur_urbanfaci_urbanfacili_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfacility
    DROP CONSTRAINT ur_urbanfacilit_objectclass__3;

ALTER TABLE ur_urbanfacility
    DROP CONSTRAINT ur_urbanfacility_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfacilitystipulate
    DROP CONSTRAINT ur_urbanfacilit_objectclass__4;

ALTER TABLE ur_urbanfacilitystipulate
    DROP CONSTRAINT ur_urbanfacilitystipulate_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunc_to_cityobjec
    DROP CONSTRAINT ur_urbanfunc_cityobject__fk_2;

ALTER TABLE ur_urbanfunc_to_cityobjec
    DROP CONSTRAINT ur_urbanfunc_urbanfuncti_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunctio_objectclass__2;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunction_fk_2;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunc_lod0multisu_fk_2;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunc_lod1multisu_fk_2;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunc_lod_1multis_fk_2;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunc_lod_2multis_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanization
    DROP CONSTRAINT ur_urbanization_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanplanningarea
    DROP CONSTRAINT ur_urbanplanningarea_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanrapi_to_structure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanrapi_to_structure
    DROP CONSTRAINT ur_urbanrapi_structurede_fk_2;

ALTER TABLE ur_urbanrapi_to_structure
    DROP CONSTRAINT ur_urbanrapi_urbanrapidt_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentproj 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanredevelopmentproj
    DROP CONSTRAINT ur_urbanredevelopmentproj_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentprom 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanredevelopmentprom
    DROP CONSTRAINT ur_urbanredevelopmentprom_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanrenewalproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanrenewalproject
    DROP CONSTRAINT ur_urbanrenewalproject_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanroad_to_structure 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanroad_to_structure
    DROP CONSTRAINT ur_urbanroad_structurede_fk_2;

ALTER TABLE ur_urbanroad_to_structure
    DROP CONSTRAINT ur_urbanroad_urbanroadat_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_privateur 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_privateur
    DROP CONSTRAINT ur_urgenturb_privateurba_fk_2;

ALTER TABLE ur_urgenturb_to_privateur
    DROP CONSTRAINT ur_urgenturb_urgenturban_fk_4;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specialur 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_specialur
    DROP CONSTRAINT ur_urgenturb_specialurba_fk_2;

ALTER TABLE ur_urgenturb_to_specialur
    DROP CONSTRAINT ur_urgenturb_urgenturban_fk_5;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specified 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturb_to_specified
    DROP CONSTRAINT ur_urgenturb_specifiedur_fk_2;

ALTER TABLE ur_urgenturb_to_specified
    DROP CONSTRAINT ur_urgenturb_urgenturban_fk_6;

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urgenturbanrenewalarea
    DROP CONSTRAINT ur_urgenturbanr_objectclass__2;

ALTER TABLE ur_urgenturbanrenewalarea
    DROP CONSTRAINT ur_urgenturbanrenewalarea_fk_2;

-- -------------------------------------------------------------------- 
-- ur_usedistrict 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_usedistrict
    DROP CONSTRAINT ur_usedistrict_fk_2;

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterway
    DROP CONSTRAINT ur_waterway_fk_2;

-- -------------------------------------------------------------------- 
-- ur_zonaldisasterpreventio 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zonaldisasterpreventio
    DROP CONSTRAINT ur_zonaldisasterpreventio_fk_2;

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone
    DROP CONSTRAINT ur_zone_objectclass_fk_2;

ALTER TABLE ur_zone
    DROP CONSTRAINT ur_zone_fk_2;

-- -------------------------------------------------------------------- 
-- ur_zone_to_boundary 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_zone_to_boundary
    DROP CONSTRAINT ur_zone_to_b_boundary_id_fk_2;

ALTER TABLE ur_zone_to_boundary
    DROP CONSTRAINT ur_zone_to_b_zone_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_genericgr_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_genericgr_to_keyvaluep
    DROP CONSTRAINT ur_genericgr_genericgrid_fk_2;

ALTER TABLE ur_genericgr_to_keyvaluep
    DROP CONSTRAINT ur_genericgr_keyvaluepai_fk_2;

-- -------------------------------------------------------------------- 
-- ur_genericgridcell 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_genericgridcell
    DROP CONSTRAINT ur_genericgridcell_fk_2;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_households
    DROP CONSTRAINT ur_households_fk_2;

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice
    DROP CONSTRAINT ur_landprice_fk_2;

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice_to_landprice
    DROP CONSTRAINT ur_landprice_landprice_i_fk_2;

ALTER TABLE ur_landprice_to_landprice
    DROP CONSTRAINT ur_landprice_landpricepe_fk_2;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusedi_to_areaofann
    DROP CONSTRAINT ur_landusedi_areaofannua_fk_2;

ALTER TABLE ur_landusedi_to_areaofann
    DROP CONSTRAINT ur_landusedi_landusedive_fk_3;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusedi_to_numberofa
    DROP CONSTRAINT ur_landusedi_landusedive_fk_4;

ALTER TABLE ur_landusedi_to_numberofa
    DROP CONSTRAINT ur_landusedi_numberofann_fk_2;

-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusediversion
    DROP CONSTRAINT ur_landusediversion_fk_2;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_numberofhouseholds
    DROP CONSTRAINT ur_numberofh_households__fk_3;

ALTER TABLE ur_numberofhouseholds
    DROP CONSTRAINT ur_numberofh_households__fk_4;

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_officesandemployees
    DROP CONSTRAINT ur_officesandemployees_fk_2;

-- -------------------------------------------------------------------- 
-- ur_populatio_to_populatio 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_populatio_to_populatio
    DROP CONSTRAINT ur_populatio_population__fk_2;

ALTER TABLE ur_populatio_to_populatio
    DROP CONSTRAINT ur_populatio_populationb_fk_2;

-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_population
    DROP CONSTRAINT ur_population_fk_2;

-- -------------------------------------------------------------------- 
-- ur_publictransitaccessibi 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitaccessibi
    DROP CONSTRAINT ur_publictransitaccessibi_fk_2;

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statisticalg_objectclass__2;

ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statisticalgrid_fk_2;

ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statistic_lod_1multis_fk_2;

ALTER TABLE ur_statisticalgrid
    DROP CONSTRAINT ur_statistic_lod_2multis_fk_2;

-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_agency
    DROP CONSTRAINT ur_agency_fk_2;

-- -------------------------------------------------------------------- 
-- ur_attributi_to_agency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_attributi_to_agency
    DROP CONSTRAINT ur_attributi_agency_id_fk_2;

ALTER TABLE ur_attributi_to_agency
    DROP CONSTRAINT ur_attributi_attribution_fk_4;

-- -------------------------------------------------------------------- 
-- ur_attributi_to_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_attributi_to_route
    DROP CONSTRAINT ur_attributi_attribution_fk_5;

ALTER TABLE ur_attributi_to_route
    DROP CONSTRAINT ur_attributi_route_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_attributi_to_trip 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_attributi_to_trip
    DROP CONSTRAINT ur_attributi_attribution_fk_6;

ALTER TABLE ur_attributi_to_trip
    DROP CONSTRAINT ur_attributi_trip_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_attribution
    DROP CONSTRAINT ur_attribution_fk_2;

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendar
    DROP CONSTRAINT ur_calendar_fk_2;

-- -------------------------------------------------------------------- 
-- ur_calendard_to_calendar 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendard_to_calendar
    DROP CONSTRAINT ur_calendard_calendar_id_fk_2;

ALTER TABLE ur_calendard_to_calendar
    DROP CONSTRAINT ur_calendard_calendardat_fk_2;

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_calendardate
    DROP CONSTRAINT ur_calendardate_fk_2;

-- -------------------------------------------------------------------- 
-- ur_fareattri_to_agency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fareattri_to_agency
    DROP CONSTRAINT ur_fareattri_agency_id_fk_2;

ALTER TABLE ur_fareattri_to_agency
    DROP CONSTRAINT ur_fareattri_fareattribu_fk_2;

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fareattribute
    DROP CONSTRAINT ur_fareattribute_fk_2;

-- -------------------------------------------------------------------- 
-- ur_farerule_to_fareattri 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_farerule_to_fareattri
    DROP CONSTRAINT ur_farerule__fareattribu_fk_2;

ALTER TABLE ur_farerule_to_fareattri
    DROP CONSTRAINT ur_farerule__farerule_id_fk_3;

-- -------------------------------------------------------------------- 
-- ur_farerule_to_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_farerule_to_route
    DROP CONSTRAINT ur_farerule__farerule_id_fk_4;

ALTER TABLE ur_farerule_to_route
    DROP CONSTRAINT ur_farerule__route_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_frequency_to_trip 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_frequency_to_trip
    DROP CONSTRAINT ur_frequency_frequency_i_fk_2;

ALTER TABLE ur_frequency_to_trip
    DROP CONSTRAINT ur_frequency_trip_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_level 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_level
    DROP CONSTRAINT ur_level_fk_2;

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_office
    DROP CONSTRAINT ur_office_fk_2;

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pathway
    DROP CONSTRAINT ur_pathway_fk_2;

-- -------------------------------------------------------------------- 
-- ur_pathway_to_stop 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pathway_to_stop
    DROP CONSTRAINT ur_pathway_t_pathway_id_fk_3;

ALTER TABLE ur_pathway_to_stop
    DROP CONSTRAINT ur_pathway_t_stop_id_fk_3;

-- -------------------------------------------------------------------- 
-- ur_pathway_to_stop_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pathway_to_stop_2
    DROP CONSTRAINT ur_pathway_t_pathway_id_fk_4;

ALTER TABLE ur_pathway_to_stop_2
    DROP CONSTRAINT ur_pathway_t_stop_id_fk_4;

-- -------------------------------------------------------------------- 
-- ur_publictra_to_cityobjec 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictra_to_cityobjec
    DROP CONSTRAINT ur_publictra_cityobject__fk_2;

ALTER TABLE ur_publictra_to_cityobjec
    DROP CONSTRAINT ur_publictra_publictrans_fk_2;

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransit
    DROP CONSTRAINT ur_publictransi_objectclass__3;

ALTER TABLE ur_publictransit
    DROP CONSTRAINT ur_publictransit_fk_2;

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publictransitdatatype
    DROP CONSTRAINT ur_publictransi_objectclass__4;

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_route
    DROP CONSTRAINT ur_route_fk_2;

ALTER TABLE ur_route
    DROP CONSTRAINT ur_route_description_fk_2;

-- -------------------------------------------------------------------- 
-- ur_route_to_agency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_route_to_agency
    DROP CONSTRAINT ur_route_to__agency_id_fk_2;

ALTER TABLE ur_route_to_agency
    DROP CONSTRAINT ur_route_to__route_id_fk_3;

-- -------------------------------------------------------------------- 
-- ur_route_to_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_route_to_route
    DROP CONSTRAINT ur_route_to__route_id_fk_4;

ALTER TABLE ur_route_to_route
    DROP CONSTRAINT ur_route_to__route_id_2_fk_2;

-- -------------------------------------------------------------------- 
-- ur_shape 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_shape
    DROP CONSTRAINT ur_shape_fk_2;

-- -------------------------------------------------------------------- 
-- ur_shape_to_point 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_shape_to_point
    DROP CONSTRAINT ur_shape_to__point_id_fk_2;

ALTER TABLE ur_shape_to_point
    DROP CONSTRAINT ur_shape_to__shape_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stop
    DROP CONSTRAINT ur_stop_fk_2;

-- -------------------------------------------------------------------- 
-- ur_stop_to_level 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stop_to_level
    DROP CONSTRAINT ur_stop_to_l_level__id_fk_2;

ALTER TABLE ur_stop_to_level
    DROP CONSTRAINT ur_stop_to_l_stop_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_stop_to_stop 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stop_to_stop
    DROP CONSTRAINT ur_stop_to_s_stop_id_fk_2;

ALTER TABLE ur_stop_to_stop
    DROP CONSTRAINT ur_stop_to_s_stop_id_2_fk_2;

-- -------------------------------------------------------------------- 
-- ur_stoptime_to_stop 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stoptime_to_stop
    DROP CONSTRAINT ur_stoptime__stop_id_fk_2;

ALTER TABLE ur_stoptime_to_stop
    DROP CONSTRAINT ur_stoptime__stoptime_id_fk_3;

-- -------------------------------------------------------------------- 
-- ur_stoptime_to_trip 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stoptime_to_trip
    DROP CONSTRAINT ur_stoptime__stoptime_id_fk_4;

ALTER TABLE ur_stoptime_to_trip
    DROP CONSTRAINT ur_stoptime__trip_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_transfer_to_stop 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transfer_to_stop
    DROP CONSTRAINT ur_transfer__stop_id_fk_3;

ALTER TABLE ur_transfer_to_stop
    DROP CONSTRAINT ur_transfer__transfer_id_fk_3;

-- -------------------------------------------------------------------- 
-- ur_transfer_to_stop_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transfer_to_stop_2
    DROP CONSTRAINT ur_transfer__stop_id_fk_4;

ALTER TABLE ur_transfer_to_stop_2
    DROP CONSTRAINT ur_transfer__transfer_id_fk_4;

-- -------------------------------------------------------------------- 
-- ur_translati_to_publictra 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_translati_to_publictra
    DROP CONSTRAINT ur_translati_publictrans_fk_2;

ALTER TABLE ur_translati_to_publictra
    DROP CONSTRAINT ur_translati_translation_fk_2;

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_translationjp
    DROP CONSTRAINT ur_translationjp_fk_2;

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_fk_2;

-- -------------------------------------------------------------------- 
-- ur_trip_to_calendar 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip_to_calendar
    DROP CONSTRAINT ur_trip_to_c_calendar_id_fk_2;

ALTER TABLE ur_trip_to_calendar
    DROP CONSTRAINT ur_trip_to_c_trip_id_fk_3;

-- -------------------------------------------------------------------- 
-- ur_trip_to_calendard 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip_to_calendard
    DROP CONSTRAINT ur_trip_to_c_calendardat_fk_2;

ALTER TABLE ur_trip_to_calendard
    DROP CONSTRAINT ur_trip_to_c_trip_id_fk_4;

-- -------------------------------------------------------------------- 
-- ur_trip_to_office 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip_to_office
    DROP CONSTRAINT ur_trip_to_o_office_id_fk_2;

ALTER TABLE ur_trip_to_office
    DROP CONSTRAINT ur_trip_to_o_trip_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_trip_to_route 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip_to_route
    DROP CONSTRAINT ur_trip_to_r_route_id_fk_2;

ALTER TABLE ur_trip_to_route
    DROP CONSTRAINT ur_trip_to_r_trip_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_trip_to_shape 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip_to_shape
    DROP CONSTRAINT ur_trip_to_s_shape_id_fk_2;

ALTER TABLE ur_trip_to_shape
    DROP CONSTRAINT ur_trip_to_s_trip_id_fk_2;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop tables *************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building;

-- -------------------------------------------------------------------- 
-- ur_building_to_buildingd 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_to_buildingd;

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_to_keyvaluep;

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
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjec_to_publictra;

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
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_keyvaluepairattribute;

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
DROP TABLE ur_land_use;

-- -------------------------------------------------------------------- 
-- ur_landuseattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landuseattribute;

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_largecustomerfacilitya;

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
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transportation_complex;

-- -------------------------------------------------------------------- 
-- ur_vegetationattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_vegetationattribute;

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_waterbody 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody_to_waterbody;

-- -------------------------------------------------------------------- 
-- ur_waterbodyattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbodyattribute;

-- -------------------------------------------------------------------- 
-- ur_waterbodyriverflooding 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbodyriverflooding;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_districtd 
-- -------------------------------------------------------------------- 
DROP TABLE ur_abstractd_to_districtd;

-- -------------------------------------------------------------------- 
-- ur_abstractd_to_zone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_abstractd_to_zone;

-- -------------------------------------------------------------------- 
-- ur_abstractdistrictplan 
-- -------------------------------------------------------------------- 
DROP TABLE ur_abstractdistrictplan;

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
DROP TABLE ur_areaclassification;

-- -------------------------------------------------------------------- 
-- ur_boundary 
-- -------------------------------------------------------------------- 
DROP TABLE ur_boundary;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
DROP TABLE ur_censusblock;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_collectivefacilities_2;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilities_3 
-- -------------------------------------------------------------------- 
DROP TABLE ur_collectivefacilities_3;

-- -------------------------------------------------------------------- 
-- ur_collectivefacilitiesfo 
-- -------------------------------------------------------------------- 
DROP TABLE ur_collectivefacilitiesfo;

-- -------------------------------------------------------------------- 
-- ur_collectivegovernmentan 
-- -------------------------------------------------------------------- 
DROP TABLE ur_collectivegovernmentan;

-- -------------------------------------------------------------------- 
-- ur_collectivehousingfacil 
-- -------------------------------------------------------------------- 
DROP TABLE ur_collectivehousingfacil;

-- -------------------------------------------------------------------- 
-- ur_collectiveurbandisaste 
-- -------------------------------------------------------------------- 
DROP TABLE ur_collectiveurbandisaste;

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
DROP TABLE ur_developmentproject;

-- -------------------------------------------------------------------- 
-- ur_disasterdamage 
-- -------------------------------------------------------------------- 
DROP TABLE ur_disasterdamage;

-- -------------------------------------------------------------------- 
-- ur_disasterp_to_zonaldisa 
-- -------------------------------------------------------------------- 
DROP TABLE ur_disasterp_to_zonaldisa;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbase 
-- -------------------------------------------------------------------- 
DROP TABLE ur_disasterpreventionbase;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbl_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_disasterpreventionbl_2;

-- -------------------------------------------------------------------- 
-- ur_disasterpreventionbloc 
-- -------------------------------------------------------------------- 
DROP TABLE ur_disasterpreventionbloc;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesspa 
-- -------------------------------------------------------------------- 
DROP TABLE ur_distributionbusinesspa;

-- -------------------------------------------------------------------- 
-- ur_distributionbusinesszo 
-- -------------------------------------------------------------------- 
DROP TABLE ur_distributionbusinesszo;

-- -------------------------------------------------------------------- 
-- ur_district 
-- -------------------------------------------------------------------- 
DROP TABLE ur_district;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_district 
-- -------------------------------------------------------------------- 
DROP TABLE ur_districtd_to_district;

-- -------------------------------------------------------------------- 
-- ur_districtd_to_zone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_districtd_to_zone;

-- -------------------------------------------------------------------- 
-- ur_districtdevelopmentpla 
-- -------------------------------------------------------------------- 
DROP TABLE ur_districtdevelopmentpla;

-- -------------------------------------------------------------------- 
-- ur_districtplan 
-- -------------------------------------------------------------------- 
DROP TABLE ur_districtplan;

-- -------------------------------------------------------------------- 
-- ur_districtsandzones 
-- -------------------------------------------------------------------- 
DROP TABLE ur_districtsandzones;

-- -------------------------------------------------------------------- 
-- ur_exceptionalfloorareara 
-- -------------------------------------------------------------------- 
DROP TABLE ur_exceptionalfloorareara;

-- -------------------------------------------------------------------- 
-- ur_globalhubcitydevelopme 
-- -------------------------------------------------------------------- 
DROP TABLE ur_globalhubcitydevelopme;

-- -------------------------------------------------------------------- 
-- ur_heightcontroldistrict 
-- -------------------------------------------------------------------- 
DROP TABLE ur_heightcontroldistrict;

-- -------------------------------------------------------------------- 
-- ur_highlevelusedistrict 
-- -------------------------------------------------------------------- 
DROP TABLE ur_highlevelusedistrict;

-- -------------------------------------------------------------------- 
-- ur_highriseresidentialatt 
-- -------------------------------------------------------------------- 
DROP TABLE ur_highriseresidentialatt;

-- -------------------------------------------------------------------- 
-- ur_historicscenerymainten 
-- -------------------------------------------------------------------- 
DROP TABLE ur_historicscenerymainten;

-- -------------------------------------------------------------------- 
-- ur_industrialparkdevelopm 
-- -------------------------------------------------------------------- 
DROP TABLE ur_industrialparkdevelopm;

-- -------------------------------------------------------------------- 
-- ur_landreadjustmentprojec 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landreadjustmentprojec;

-- -------------------------------------------------------------------- 
-- ur_landscapezone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landscapezone;

-- -------------------------------------------------------------------- 
-- ur_newhousingandurbandeve 
-- -------------------------------------------------------------------- 
DROP TABLE ur_newhousingandurbandeve;

-- -------------------------------------------------------------------- 
-- ur_newurbaninfrastructure 
-- -------------------------------------------------------------------- 
DROP TABLE ur_newurbaninfrastructure;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofhouseholds_2;

-- -------------------------------------------------------------------- 
-- ur_openspaceforpublicuse 
-- -------------------------------------------------------------------- 
DROP TABLE ur_openspaceforpublicuse;

-- -------------------------------------------------------------------- 
-- ur_parkattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkattribute;

-- -------------------------------------------------------------------- 
-- ur_parkingplaceattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkingplaceattribute;

-- -------------------------------------------------------------------- 
-- ur_pollution 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pollution;

-- -------------------------------------------------------------------- 
-- ur_portzone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portzone;

-- -------------------------------------------------------------------- 
-- ur_privateurbanrenewalpro 
-- -------------------------------------------------------------------- 
DROP TABLE ur_privateurbanrenewalpro;

-- -------------------------------------------------------------------- 
-- ur_productivegreenzone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_productivegreenzone;

-- -------------------------------------------------------------------- 
-- ur_projectpromotionarea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_projectpromotionarea;

-- -------------------------------------------------------------------- 
-- ur_publictransitfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictransitfacility;

-- -------------------------------------------------------------------- 
-- ur_quasiurbanplanningarea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_quasiurbanplanningarea;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
DROP TABLE ur_recreations;

-- -------------------------------------------------------------------- 
-- ur_residentialblockconstr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_residentialblockconstr;

-- -------------------------------------------------------------------- 
-- ur_residentialenvironment 
-- -------------------------------------------------------------------- 
DROP TABLE ur_residentialenvironment;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictimprov 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roadsidedistrictimprov;

-- -------------------------------------------------------------------- 
-- ur_roadsidedistrictplan 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roadsidedistrictplan;

-- -------------------------------------------------------------------- 
-- ur_ruraldistrictimproveme 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ruraldistrictimproveme;

-- -------------------------------------------------------------------- 
-- ur_scenicdistrict 
-- -------------------------------------------------------------------- 
DROP TABLE ur_scenicdistrict;

-- -------------------------------------------------------------------- 
-- ur_scheduledareaforurband 
-- -------------------------------------------------------------------- 
DROP TABLE ur_scheduledareaforurband;

-- -------------------------------------------------------------------- 
-- ur_sedimentdisasterpronea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_sedimentdisasterpronea;

-- -------------------------------------------------------------------- 
-- ur_sewersystemattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_sewersystemattribute;

-- -------------------------------------------------------------------- 
-- ur_specialgreenspaceconse 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specialgreenspaceconse;

-- -------------------------------------------------------------------- 
-- ur_specialurbanrenaissanc 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specialurbanrenaissanc;

-- -------------------------------------------------------------------- 
-- ur_specialuseattractiondi 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specialuseattractiondi;

-- -------------------------------------------------------------------- 
-- ur_specialusedistrict 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specialusedistrict;

-- -------------------------------------------------------------------- 
-- ur_specialuserestrictiond 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specialuserestrictiond;

-- -------------------------------------------------------------------- 
-- ur_specified_to_globalhub 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specified_to_globalhub;

-- -------------------------------------------------------------------- 
-- ur_specifiedblock 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specifiedblock;

-- -------------------------------------------------------------------- 
-- ur_specifieddisasterpreve 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specifieddisasterpreve;

-- -------------------------------------------------------------------- 
-- ur_specifiedurgenturbanre 
-- -------------------------------------------------------------------- 
DROP TABLE ur_specifiedurgenturbanre;

-- -------------------------------------------------------------------- 
-- ur_structuredetails 
-- -------------------------------------------------------------------- 
DROP TABLE ur_structuredetails;

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_supplyfacility;

-- -------------------------------------------------------------------- 
-- ur_threedimensionalextent 
-- -------------------------------------------------------------------- 
DROP TABLE ur_threedimensionalextent;

-- -------------------------------------------------------------------- 
-- ur_trafficfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trafficfacility;

-- -------------------------------------------------------------------- 
-- ur_treatmentfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_treatmentfacility;

-- -------------------------------------------------------------------- 
-- ur_treeplantingdistrict 
-- -------------------------------------------------------------------- 
DROP TABLE ur_treeplantingdistrict;

-- -------------------------------------------------------------------- 
-- ur_urbandevelopmentprojec 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbandevelopmentprojec;

-- -------------------------------------------------------------------- 
-- ur_urbandisasterrecoveryp 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbandisasterrecoveryp;

-- -------------------------------------------------------------------- 
-- ur_urbanfaci_to_threedime 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfaci_to_threedime;

-- -------------------------------------------------------------------- 
-- ur_urbanfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfacility;

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfacilitystipulate;

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
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanplanningarea;

-- -------------------------------------------------------------------- 
-- ur_urbanrapi_to_structure 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanrapi_to_structure;

-- -------------------------------------------------------------------- 
-- ur_urbanrapidtransitrailr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanrapidtransitrailr;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentproj 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanredevelopmentproj;

-- -------------------------------------------------------------------- 
-- ur_urbanredevelopmentprom 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanredevelopmentprom;

-- -------------------------------------------------------------------- 
-- ur_urbanrenewalproject 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanrenewalproject;

-- -------------------------------------------------------------------- 
-- ur_urbanroad_to_structure 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanroad_to_structure;

-- -------------------------------------------------------------------- 
-- ur_urbanroadattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanroadattribute;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_privateur 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urgenturb_to_privateur;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specialur 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urgenturb_to_specialur;

-- -------------------------------------------------------------------- 
-- ur_urgenturb_to_specified 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urgenturb_to_specified;

-- -------------------------------------------------------------------- 
-- ur_urgenturbanrenewalarea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urgenturbanrenewalarea;

-- -------------------------------------------------------------------- 
-- ur_usedistrict 
-- -------------------------------------------------------------------- 
DROP TABLE ur_usedistrict;

-- -------------------------------------------------------------------- 
-- ur_vehicleterminalattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_vehicleterminalattribu;

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterway;

-- -------------------------------------------------------------------- 
-- ur_waterworksattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterworksattribute;

-- -------------------------------------------------------------------- 
-- ur_zonaldisasterpreventio 
-- -------------------------------------------------------------------- 
DROP TABLE ur_zonaldisasterpreventio;

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_zone;

-- -------------------------------------------------------------------- 
-- ur_zone_to_boundary 
-- -------------------------------------------------------------------- 
DROP TABLE ur_zone_to_boundary;

-- -------------------------------------------------------------------- 
-- ur_areaofannualdiversions 
-- -------------------------------------------------------------------- 
DROP TABLE ur_areaofannualdiversions;

-- -------------------------------------------------------------------- 
-- ur_genericgr_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_genericgr_to_keyvaluep;

-- -------------------------------------------------------------------- 
-- ur_genericgridcell 
-- -------------------------------------------------------------------- 
DROP TABLE ur_genericgridcell;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
DROP TABLE ur_households;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
DROP TABLE ur_keyvaluepair;

-- -------------------------------------------------------------------- 
-- ur_landprice 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landprice;

-- -------------------------------------------------------------------- 
-- ur_landprice_to_landprice 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landprice_to_landprice;

-- -------------------------------------------------------------------- 
-- ur_landpriceperlanduse 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landpriceperlanduse;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_areaofann 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landusedi_to_areaofann;

-- -------------------------------------------------------------------- 
-- ur_landusedi_to_numberofa 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landusedi_to_numberofa;

-- -------------------------------------------------------------------- 
-- ur_landusediversion 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landusediversion;

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofannualdiversio;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofhouseholds;

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
DROP TABLE ur_officesandemployees;

-- -------------------------------------------------------------------- 
-- ur_populatio_to_populatio 
-- -------------------------------------------------------------------- 
DROP TABLE ur_populatio_to_populatio;

-- -------------------------------------------------------------------- 
-- ur_population 
-- -------------------------------------------------------------------- 
DROP TABLE ur_population;

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsex 
-- -------------------------------------------------------------------- 
DROP TABLE ur_populationbyageandsex;

-- -------------------------------------------------------------------- 
-- ur_publictransitaccessibi 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictransitaccessibi;

-- -------------------------------------------------------------------- 
-- ur_statisticalgrid 
-- -------------------------------------------------------------------- 
DROP TABLE ur_statisticalgrid;

-- -------------------------------------------------------------------- 
-- ur_agency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_agency;

-- -------------------------------------------------------------------- 
-- ur_attributi_to_agency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_attributi_to_agency;

-- -------------------------------------------------------------------- 
-- ur_attributi_to_route 
-- -------------------------------------------------------------------- 
DROP TABLE ur_attributi_to_route;

-- -------------------------------------------------------------------- 
-- ur_attributi_to_trip 
-- -------------------------------------------------------------------- 
DROP TABLE ur_attributi_to_trip;

-- -------------------------------------------------------------------- 
-- ur_attribution 
-- -------------------------------------------------------------------- 
DROP TABLE ur_attribution;

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
DROP TABLE ur_calendar;

-- -------------------------------------------------------------------- 
-- ur_calendard_to_calendar 
-- -------------------------------------------------------------------- 
DROP TABLE ur_calendard_to_calendar;

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
DROP TABLE ur_calendardate;

-- -------------------------------------------------------------------- 
-- ur_description 
-- -------------------------------------------------------------------- 
DROP TABLE ur_description;

-- -------------------------------------------------------------------- 
-- ur_fareattri_to_agency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_fareattri_to_agency;

-- -------------------------------------------------------------------- 
-- ur_fareattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_fareattribute;

-- -------------------------------------------------------------------- 
-- ur_farerule 
-- -------------------------------------------------------------------- 
DROP TABLE ur_farerule;

-- -------------------------------------------------------------------- 
-- ur_farerule_to_fareattri 
-- -------------------------------------------------------------------- 
DROP TABLE ur_farerule_to_fareattri;

-- -------------------------------------------------------------------- 
-- ur_farerule_to_route 
-- -------------------------------------------------------------------- 
DROP TABLE ur_farerule_to_route;

-- -------------------------------------------------------------------- 
-- ur_feedinfo 
-- -------------------------------------------------------------------- 
DROP TABLE ur_feedinfo;

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_frequency;

-- -------------------------------------------------------------------- 
-- ur_frequency_to_trip 
-- -------------------------------------------------------------------- 
DROP TABLE ur_frequency_to_trip;

-- -------------------------------------------------------------------- 
-- ur_level 
-- -------------------------------------------------------------------- 
DROP TABLE ur_level;

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
DROP TABLE ur_office;

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pathway;

-- -------------------------------------------------------------------- 
-- ur_pathway_to_stop 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pathway_to_stop;

-- -------------------------------------------------------------------- 
-- ur_pathway_to_stop_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pathway_to_stop_2;

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
DROP TABLE ur_point;

-- -------------------------------------------------------------------- 
-- ur_publictra_to_cityobjec 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictra_to_cityobjec;

-- -------------------------------------------------------------------- 
-- ur_publictransit 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictransit;

-- -------------------------------------------------------------------- 
-- ur_publictransitdatatype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publictransitdatatype;

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
DROP TABLE ur_route;

-- -------------------------------------------------------------------- 
-- ur_route_to_agency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_route_to_agency;

-- -------------------------------------------------------------------- 
-- ur_route_to_route 
-- -------------------------------------------------------------------- 
DROP TABLE ur_route_to_route;

-- -------------------------------------------------------------------- 
-- ur_shape 
-- -------------------------------------------------------------------- 
DROP TABLE ur_shape;

-- -------------------------------------------------------------------- 
-- ur_shape_to_point 
-- -------------------------------------------------------------------- 
DROP TABLE ur_shape_to_point;

-- -------------------------------------------------------------------- 
-- ur_stop 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stop;

-- -------------------------------------------------------------------- 
-- ur_stop_to_level 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stop_to_level;

-- -------------------------------------------------------------------- 
-- ur_stop_to_stop 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stop_to_stop;

-- -------------------------------------------------------------------- 
-- ur_stoptime 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stoptime;

-- -------------------------------------------------------------------- 
-- ur_stoptime_to_stop 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stoptime_to_stop;

-- -------------------------------------------------------------------- 
-- ur_stoptime_to_trip 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stoptime_to_trip;

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transfer;

-- -------------------------------------------------------------------- 
-- ur_transfer_to_stop 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transfer_to_stop;

-- -------------------------------------------------------------------- 
-- ur_transfer_to_stop_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transfer_to_stop_2;

-- -------------------------------------------------------------------- 
-- ur_translati_to_publictra 
-- -------------------------------------------------------------------- 
DROP TABLE ur_translati_to_publictra;

-- -------------------------------------------------------------------- 
-- ur_translation 
-- -------------------------------------------------------------------- 
DROP TABLE ur_translation;

-- -------------------------------------------------------------------- 
-- ur_translationjp 
-- -------------------------------------------------------------------- 
DROP TABLE ur_translationjp;

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trip;

-- -------------------------------------------------------------------- 
-- ur_trip_to_calendar 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trip_to_calendar;

-- -------------------------------------------------------------------- 
-- ur_trip_to_calendard 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trip_to_calendard;

-- -------------------------------------------------------------------- 
-- ur_trip_to_office 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trip_to_office;

-- -------------------------------------------------------------------- 
-- ur_trip_to_route 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trip_to_route;

-- -------------------------------------------------------------------- 
-- ur_trip_to_shape 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trip_to_shape;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop Sequences ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

DROP SEQUENCE ur_trafficareaattribu_seq;

DROP SEQUENCE ur_cityfurnitureattri_seq;

DROP SEQUENCE ur_waterbodyattribute_seq;

DROP SEQUENCE ur_landuseattribute_seq;

DROP SEQUENCE ur_roadattribute_seq;

DROP SEQUENCE ur_keyvaluepairattrib_seq;

DROP SEQUENCE ur_buildingattribute_seq;

DROP SEQUENCE ur_vegetationattribut_seq;

PURGE RECYCLEBIN;
DROP SEQUENCE ur_numberofhouseholds_seq_2;

DROP SEQUENCE ur_parkattribute_seq;

DROP SEQUENCE ur_parkingplaceattrib_seq;

DROP SEQUENCE ur_sewersystemattribu_seq;

DROP SEQUENCE ur_structuredetails_seq;

DROP SEQUENCE ur_urbanrapidtransitr_seq;

DROP SEQUENCE ur_urbanroadattribute_seq;

DROP SEQUENCE ur_vehicleterminalatt_seq;

DROP SEQUENCE ur_waterworksattribut_seq;

DROP SEQUENCE ur_areaofannualdivers_seq;

DROP SEQUENCE ur_keyvaluepair_seq;

DROP SEQUENCE ur_landpriceperlandus_seq;

DROP SEQUENCE ur_numberofannualdive_seq;

DROP SEQUENCE ur_numberofhouseholds_seq;

DROP SEQUENCE ur_populationbyageand_seq;

DROP SEQUENCE ur_description_seq;

DROP SEQUENCE ur_farerule_seq;

DROP SEQUENCE ur_feedinfo_seq;

DROP SEQUENCE ur_frequency_seq;

DROP SEQUENCE ur_point_seq;

DROP SEQUENCE ur_publictransitdatat_seq;

DROP SEQUENCE ur_stoptime_seq;

DROP SEQUENCE ur_transfer_seq;

DROP SEQUENCE ur_translation_seq;

