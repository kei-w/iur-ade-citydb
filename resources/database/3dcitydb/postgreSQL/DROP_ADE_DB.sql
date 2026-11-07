-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2026-11-07 13:00:53 
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
    DROP CONSTRAINT ur_attribution_agency_fk;

ALTER TABLE ur_attribution
    DROP CONSTRAINT ur_attribution_route_fk;

ALTER TABLE ur_attribution
    DROP CONSTRAINT ur_attribution_trip_fk;

-- -------------------------------------------------------------------- 
-- ur_boundarysurface 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_boundarysurface
    DROP CONSTRAINT ur_boundarysu_objectcla_fk;

ALTER TABLE ur_boundarysurface
    DROP CONSTRAINT ur_boundarysurface_fk;

ALTER TABLE ur_boundarysurface
    DROP CONSTRAINT ur_bounda_constr_bounde_fk;

ALTER TABLE ur_boundarysurface
    DROP CONSTRAINT ur_boundarysu_lod2multi_fk;

ALTER TABLE ur_boundarysurface
    DROP CONSTRAINT ur_boundarysu_lod3multi_fk;

-- -------------------------------------------------------------------- 
-- ur_bridge 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_fk;

ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_bridbaseattri_fk;

ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_briddataquali_fk;

ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_bridfacilityi_fk;

ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_bridfacilityt_fk;

ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_bridfunctiona_fk;

ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_bridriskasses_fk;

ALTER TABLE ur_bridge
    DROP CONSTRAINT ur_bridge_bridstructure_fk;

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_bldgdataqua_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_bldgfacilit_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_bldgfacil_fk_1;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_bldgrealest_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_bldgusecase_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_buildingdet_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_buildingida_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_ifcbuilding_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_indoorbuild_fk;

ALTER TABLE ur_building
    DROP CONSTRAINT ur_building_largecustom_fk;

-- -------------------------------------------------------------------- 
-- ur_building_furniture 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_furniture
    DROP CONSTRAINT ur_building_furniture_fk;

ALTER TABLE ur_building_furniture
    DROP CONSTRAINT ur_buildi_furnit_ifcbui_fk;

ALTER TABLE ur_building_furniture
    DROP CONSTRAINT ur_buildi_furnit_indoor_fk;

-- -------------------------------------------------------------------- 
-- ur_building_installation 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_installation
    DROP CONSTRAINT ur_building_installatio_fk;

ALTER TABLE ur_building_installation
    DROP CONSTRAINT ur_buildi_instal_ifcbui_fk;

ALTER TABLE ur_building_installation
    DROP CONSTRAINT ur_buildi_instal_ifcint_fk;

ALTER TABLE ur_building_installation
    DROP CONSTRAINT ur_buildi_instal_indoor_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingattribute
    DROP CONSTRAINT ur_buildingat_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingdetailattribut
    DROP CONSTRAINT ur_buildingdetailattrib_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingidattribute
    DROP CONSTRAINT ur_buildingidattribute_fk;

-- -------------------------------------------------------------------- 
-- ur_buildingusecaseattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_buildingusecaseattribu
    DROP CONSTRAINT ur_buildingusecaseattri_fk;

-- -------------------------------------------------------------------- 
-- ur_cable 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cable
    DROP CONSTRAINT ur_cable_objectclass_fk;

ALTER TABLE ur_cable
    DROP CONSTRAINT ur_cable_fk;

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
-- ur_cargohandlingfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cargohandlingfacility
    DROP CONSTRAINT ur_cargohandlingfacilit_fk;

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
    DROP CONSTRAINT ur_city_furnitu_frndata_fk;

ALTER TABLE ur_city_furniture
    DROP CONSTRAINT ur_city_furnitu_frnfaci_fk;

ALTER TABLE ur_city_furniture
    DROP CONSTRAINT ur_city_furnit_frnfac_fk_1;

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobject_fk;

ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobject_vegdataqu_fk;

ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobject_vegfacili_fk;

ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobjec_vegfacil_fk_1;

ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobject_pointclou_fk;

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

ALTER TABLE ur_cityobjectgroup_1
    DROP CONSTRAINT ur_cityobject_ifcbuildi_fk;

ALTER TABLE ur_cityobjectgroup_1
    DROP CONSTRAINT ur_cityobject_indoorsto_fk;

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
-- ur_construction 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construction
    DROP CONSTRAINT ur_construction_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constructi_consfacil_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constructi_consbasea_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constructi_consstruc_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constructi_consdataq_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constru_lod0geo_brep_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constru_lod1geo_brep_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constru_lod2geo_brep_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constru_lod3geo_brep_fk;

ALTER TABLE ur_construction
    DROP CONSTRAINT ur_constru_lod4geo_brep_fk;

-- -------------------------------------------------------------------- 
-- ur_constructionevent 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_constructionevent
    DROP CONSTRAINT ur_constr_constr_constr_fk;

-- -------------------------------------------------------------------- 
-- ur_constructioninstallati 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_constructioninstallati
    DROP CONSTRAINT ur_constructioninstalla_fk;

ALTER TABLE ur_constructioninstallati
    DROP CONSTRAINT ur_constr_const_const_fk_1;

ALTER TABLE ur_constructioninstallati
    DROP CONSTRAINT ur_constr_lod2ge_brep_fk_1;

ALTER TABLE ur_constructioninstallati
    DROP CONSTRAINT ur_constr_lod3ge_brep_fk_1;

-- -------------------------------------------------------------------- 
-- ur_constructionstructurea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_constructionstructurea
    DROP CONSTRAINT ur_constructi_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_controlpoint 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_controlpoint
    DROP CONSTRAINT ur_contro_railwa_contro_fk;

ALTER TABLE ur_controlpoint
    DROP CONSTRAINT ur_controlpoi_parameter_fk;

-- -------------------------------------------------------------------- 
-- ur_controlpointtype 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_controlpointtype
    DROP CONSTRAINT ur_controlpoi_circularc_fk;

ALTER TABLE ur_controlpointtype
    DROP CONSTRAINT ur_controlpoi_transitio_fk;

ALTER TABLE ur_controlpointtype
    DROP CONSTRAINT ur_controlpoi_slopetype_fk;

ALTER TABLE ur_controlpointtype
    DROP CONSTRAINT ur_controlpoi_verticalc_fk;

-- -------------------------------------------------------------------- 
-- ur_cyberportmarinaandpbs 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cyberportmarinaandpbs
    DROP CONSTRAINT ur_cyberportmarinaandpb_fk;

-- -------------------------------------------------------------------- 
-- ur_damattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_damattribute
    DROP CONSTRAINT ur_damattribute_fk;

-- -------------------------------------------------------------------- 
-- ur_dataqualityattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_dataqualityattribute
    DROP CONSTRAINT ur_dataqualit_publicsur_fk;

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
-- ur_disasterriskattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterriskattribute
    DROP CONSTRAINT ur_disasterri_objectcla_fk;

ALTER TABLE ur_disasterriskattribute
    DROP CONSTRAINT ur_disast_buildi_bldgdi_fk;

ALTER TABLE ur_disasterriskattribute
    DROP CONSTRAINT ur_disast_bridge_briddi_fk;

ALTER TABLE ur_disasterriskattribute
    DROP CONSTRAINT ur_disast_tunnel_tundis_fk;

ALTER TABLE ur_disasterriskattribute
    DROP CONSTRAINT ur_disast_constr_consdi_fk;

-- -------------------------------------------------------------------- 
-- ur_dmannotation 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_dmannotation
    DROP CONSTRAINT ur_dmannotation_fk;

ALTER TABLE ur_dmannotation
    DROP CONSTRAINT ur_dmannot_lod0anc_brep_fk;

-- -------------------------------------------------------------------- 
-- ur_dmattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattribut_objectcla_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattribut_dmelement_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattr_buildi_bldgdm_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattr_bridge_briddm_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmatt_city_furn_frnd_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmatt_reli_comp_demd_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattr_waterb_wtrdma_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmatt_land_use_lused_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmatt_tran_comp_tran_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattr_tunnel_tundma_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattr_cityob_vegdma_fk;

ALTER TABLE ur_dmattribute
    DROP CONSTRAINT ur_dmattr_constr_consdm_fk;

-- -------------------------------------------------------------------- 
-- ur_dmgeometricattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_dmgeometricattribute
    DROP CONSTRAINT ur_dmgeometricattribute_fk;

ALTER TABLE ur_dmgeometricattribute
    DROP CONSTRAINT ur_dmgeome_lod0geo_brep_fk;

-- -------------------------------------------------------------------- 
-- ur_elevation 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_elevation
    DROP CONSTRAINT ur_elevat_constr_elevat_fk;

-- -------------------------------------------------------------------- 
-- ur_embankmentattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_embankmentattribute
    DROP CONSTRAINT ur_embankmentattribute_fk;

-- -------------------------------------------------------------------- 
-- ur_facilityattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facilityat_objectcla_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facili_buildi_bldgfa_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facili_bridge_bridfa_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facil_city_furn_frnf_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facili_waterb_wtrfac_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facil_land_use_lusef_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facil_tran_comp_tran_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facili_tunnel_tunfac_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facili_cityob_vegfac_fk;

ALTER TABLE ur_facilityattribute
    DROP CONSTRAINT ur_facili_const_consf_fk_1;

-- -------------------------------------------------------------------- 
-- ur_facilitytypeattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_facilitytypeattribute
    DROP CONSTRAINT ur_facili_constr_consfa_fk;

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
    DROP CONSTRAINT ur_farerule_fare_fk;

ALTER TABLE ur_farerule
    DROP CONSTRAINT ur_farerule_route_fk;

-- -------------------------------------------------------------------- 
-- ur_feedinfo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_feedinfo
    DROP CONSTRAINT ur_feedinfo_fk;

-- -------------------------------------------------------------------- 
-- ur_fishingportattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fishingportattribute
    DROP CONSTRAINT ur_fishingportattribute_fk;

ALTER TABLE ur_fishingportattribute
    DROP CONSTRAINT ur_fishingpor_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_fishingportcapacity 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fishingportcapacity
    DROP CONSTRAINT ur_fishingportcapacity_fk;

-- -------------------------------------------------------------------- 
-- ur_fishingportfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_fishingportfacility
    DROP CONSTRAINT ur_fishingportfacility_fk;

-- -------------------------------------------------------------------- 
-- ur_floodingriskattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_floodingriskattribute
    DROP CONSTRAINT ur_floodingri_objectcla_fk;

ALTER TABLE ur_floodingriskattribute
    DROP CONSTRAINT ur_floodingriskattribut_fk;

ALTER TABLE ur_floodingriskattribute
    DROP CONSTRAINT ur_floodi_waterb_floodi_fk;

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_frequency
    DROP CONSTRAINT ur_frequency_fk;

ALTER TABLE ur_frequency
    DROP CONSTRAINT ur_frequency_trip_fk;

-- -------------------------------------------------------------------- 
-- ur_harborfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_harborfacility
    DROP CONSTRAINT ur_harborfacility_fk;

-- -------------------------------------------------------------------- 
-- ur_height 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_height
    DROP CONSTRAINT ur_height_constr_height_fk;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_households
    DROP CONSTRAINT ur_households_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcattribute
    DROP CONSTRAINT ur_ifcattribu_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcbuilding 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcbuilding
    DROP CONSTRAINT ur_ifcbuilding_fk;

ALTER TABLE ur_ifcbuilding
    DROP CONSTRAINT ur_ifcbuildin_buildinga_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcbuildingelement 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcbuildingelement
    DROP CONSTRAINT ur_ifcbuildin_objectcla_fk;

ALTER TABLE ur_ifcbuildingelement
    DROP CONSTRAINT ur_ifcbuildingelement_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcclassificationrefer 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcclassificationrefer
    DROP CONSTRAINT ur_ifcclassificationref_fk;

ALTER TABLE ur_ifcclassificationrefer
    DROP CONSTRAINT ur_ifcclassif_reference_fk;

-- -------------------------------------------------------------------- 
-- ur_ifccoordinatereferen_1 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifccoordinatereferen_1
    DROP CONSTRAINT ur_ifccoordin_objectcla_fk;

ALTER TABLE ur_ifccoordinatereferen_1
    DROP CONSTRAINT ur_ifccoordinat_context_fk;

-- -------------------------------------------------------------------- 
-- ur_ifccoordinatereference 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifccoordinatereference
    DROP CONSTRAINT ur_ifccoordinatereferen_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcdoor 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcdoor
    DROP CONSTRAINT ur_ifcdoor_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcelement 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcelement
    DROP CONSTRAINT ur_ifcelement_fk;

ALTER TABLE ur_ifcelement
    DROP CONSTRAINT ur_ifcelement_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcgeometricrepresenta 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcgeometricrepresenta
    DROP CONSTRAINT ur_ifcgeometr_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcgroup 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcgroup
    DROP CONSTRAINT ur_ifcgroup_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcmapconversion 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcmapconversion
    DROP CONSTRAINT ur_ifcmapconversion_fk;

ALTER TABLE ur_ifcmapconversion
    DROP CONSTRAINT ur_ifcmapconv_sourcecrs_fk;

ALTER TABLE ur_ifcmapconversion
    DROP CONSTRAINT ur_ifcmapconv_targetcrs_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcobjectdefinition 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcobjectdefinition
    DROP CONSTRAINT ur_ifcobjectd_objectcla_fk;

ALTER TABLE ur_ifcobjectdefinition
    DROP CONSTRAINT ur_ifcobjectdefinition_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcopeningelement 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcopeningelement
    DROP CONSTRAINT ur_ifcopeningelement_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcproduct 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcproduct
    DROP CONSTRAINT ur_ifcproduct_objectcla_fk;

ALTER TABLE ur_ifcproduct
    DROP CONSTRAINT ur_ifcproduct_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcproject 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcproject
    DROP CONSTRAINT ur_ifcproject_fk;

ALTER TABLE ur_ifcproject
    DROP CONSTRAINT ur_ifcproject_represent_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcprojectedcrs 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcprojectedcrs
    DROP CONSTRAINT ur_ifcprojectedcrs_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetbuildingcommon 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcpsetbuildingcommon
    DROP CONSTRAINT ur_ifcpsetbuildingcommo_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetdoorcommon 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcpsetdoorcommon
    DROP CONSTRAINT ur_ifcpsetdoorcommon_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetopeningelementc 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcpsetopeningelementc
    DROP CONSTRAINT ur_ifcpsetopeningelemen_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetsitecommon 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcpsetsitecommon
    DROP CONSTRAINT ur_ifcpsetsitecommon_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetspacecommon 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcpsetspacecommon
    DROP CONSTRAINT ur_ifcpsetspacecommon_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetwindowcommon 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcpsetwindowcommon
    DROP CONSTRAINT ur_ifcpsetwindowcommon_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcroot 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcroot
    DROP CONSTRAINT ur_ifcroot_objectclass_fk;

ALTER TABLE ur_ifcroot
    DROP CONSTRAINT ur_ifcroot_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcsite 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcsite
    DROP CONSTRAINT ur_ifcsite_fk;

ALTER TABLE ur_ifcsite
    DROP CONSTRAINT ur_ifcsite_siteaddress_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcspace 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcspace
    DROP CONSTRAINT ur_ifcspace_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcspacebasequantity 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcspacebasequantity
    DROP CONSTRAINT ur_ifcspacebasequantity_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcspatialstructureele 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcspatialstructureele
    DROP CONSTRAINT ur_ifcspatialstructuree_fk;

ALTER TABLE ur_ifcspatialstructureele
    DROP CONSTRAINT ur_ifcspatial_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcunit 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcunit
    DROP CONSTRAINT ur_ifcuni_ifcpro_unitsi_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcwall 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcwall
    DROP CONSTRAINT ur_ifcwall_objectclass_fk;

ALTER TABLE ur_ifcwall
    DROP CONSTRAINT ur_ifcwall_fk;

-- -------------------------------------------------------------------- 
-- ur_ifcwindow 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcwindow
    DROP CONSTRAINT ur_ifcwindow_fk;

-- -------------------------------------------------------------------- 
-- ur_indoorattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_indoorattribute
    DROP CONSTRAINT ur_indoorattr_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_indoorfacilityattribut 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_indoorfacilityattribut
    DROP CONSTRAINT ur_indoorfacilityattrib_fk;

-- -------------------------------------------------------------------- 
-- ur_indoorspaceattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_indoorspaceattribute
    DROP CONSTRAINT ur_indoorspaceattribute_fk;

-- -------------------------------------------------------------------- 
-- ur_indoorstoreyattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_indoorstoreyattribute
    DROP CONSTRAINT ur_indoorstoreyattribut_fk;

-- -------------------------------------------------------------------- 
-- ur_indoortactiletileattri 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_indoortactiletileattri
    DROP CONSTRAINT ur_indoortactiletileatt_fk;

-- -------------------------------------------------------------------- 
-- ur_indooruserdefinedattri 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_indooruserdefinedattri
    DROP CONSTRAINT ur_indooruserdefinedatt_fk;

ALTER TABLE ur_indooruserdefinedattri
    DROP CONSTRAINT ur_indooruser_nominalva_fk;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepair 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_keyvaluepair
    DROP CONSTRAINT ur_keyval_statis_generi_fk;

-- -------------------------------------------------------------------- 
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyval_buildi_bldgke_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyval_bridge_bridke_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyva_city_furn_frnk_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyva_reli_feat_demk_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyval_waterb_wtrkey_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyva_land_use_lusek_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyva_tran_comp_tran_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyval_tunnel_tunkey_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyval_cityob_vegkey_fk;

ALTER TABLE ur_keyvaluepairattribute
    DROP CONSTRAINT ur_keyval_constr_conske_fk;

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_fk;

ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_ifclandusea_fk;

ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_landusedeta_fk;

ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_lusedataqua_fk;

ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_lusefacilit_fk;

ALTER TABLE ur_land_use
    DROP CONSTRAINT ur_land_use_lusefacil_fk_1;

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
-- ur_lengthattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_lengthattribute
    DROP CONSTRAINT ur_length_utilit_length_fk;

-- -------------------------------------------------------------------- 
-- ur_longevitymeasures 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_longevitymeasures
    DROP CONSTRAINT ur_longev_parkfa_longev_fk;

ALTER TABLE ur_longevitymeasures
    DROP CONSTRAINT ur_longevitym_counterme_fk;

-- -------------------------------------------------------------------- 
-- ur_maintenancehistoryattr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_maintenancehistoryattr
    DROP CONSTRAINT ur_maintenancehistoryat_fk;

-- -------------------------------------------------------------------- 
-- ur_mooringfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_mooringfacility
    DROP CONSTRAINT ur_mooringfacility_fk;

-- -------------------------------------------------------------------- 
-- ur_navigationassistancefa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_navigationassistancefa
    DROP CONSTRAINT ur_navigationassistance_fk;

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
-- ur_occupancy 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_occupancy
    DROP CONSTRAINT ur_occupa_constr_occupa_fk;

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
-- ur_offsetdepth 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_offsetdepth
    DROP CONSTRAINT ur_offset_utilit_offset_fk;

-- -------------------------------------------------------------------- 
-- ur_opening 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_opening
    DROP CONSTRAINT ur_opening_fk;

ALTER TABLE ur_opening
    DROP CONSTRAINT ur_opening_ifcopeningat_fk;

ALTER TABLE ur_opening
    DROP CONSTRAINT ur_opening_indooropenin_fk;

-- -------------------------------------------------------------------- 
-- ur_parkfacilitylongevityp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_parkfacilitylongevityp
    DROP CONSTRAINT ur_parkfacilitylongevit_fk;

ALTER TABLE ur_parkfacilitylongevityp
    DROP CONSTRAINT ur_parkfacili_numberoff_fk;

ALTER TABLE ur_parkfacilitylongevityp
    DROP CONSTRAINT ur_parkfacili_repairsbe_fk;

-- -------------------------------------------------------------------- 
-- ur_parkhealthassessment 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_parkhealthassessment
    DROP CONSTRAINT ur_parkhe_parkfa_parkhe_fk;

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
-- ur_pipe 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pipe
    DROP CONSTRAINT ur_pipe_objectclass_fk;

ALTER TABLE ur_pipe
    DROP CONSTRAINT ur_pipe_fk;

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_point
    DROP CONSTRAINT ur_point_publictr_point_fk;

-- -------------------------------------------------------------------- 
-- ur_pointcloud 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pointcloud
    DROP CONSTRAINT ur_pointcloud_fk;

ALTER TABLE ur_pointcloud
    DROP CONSTRAINT ur_pointcloud_pointclou_fk;

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
-- ur_portattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portattribute
    DROP CONSTRAINT ur_portattribute_fk;

ALTER TABLE ur_portattribute
    DROP CONSTRAINT ur_portattrib_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_portenvironmentalimpro 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portenvironmentalimpro
    DROP CONSTRAINT ur_portenvironmentalimp_fk;

-- -------------------------------------------------------------------- 
-- ur_portmanagementfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portmanagementfacility
    DROP CONSTRAINT ur_portmanagementfacili_fk;

-- -------------------------------------------------------------------- 
-- ur_portpassengerfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portpassengerfacility
    DROP CONSTRAINT ur_portpassengerfacilit_fk;

-- -------------------------------------------------------------------- 
-- ur_portpollutioncontrolfa 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portpollutioncontrolfa
    DROP CONSTRAINT ur_portpollutioncontrol_fk;

-- -------------------------------------------------------------------- 
-- ur_portprotectivefacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portprotectivefacility
    DROP CONSTRAINT ur_portprotectivefacili_fk;

-- -------------------------------------------------------------------- 
-- ur_portstoragefacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portstoragefacility
    DROP CONSTRAINT ur_portstoragefacility_fk;

-- -------------------------------------------------------------------- 
-- ur_porttransportationfaci 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_porttransportationfaci
    DROP CONSTRAINT ur_porttransportationfa_fk;

-- -------------------------------------------------------------------- 
-- ur_portwastetreatmentfaci 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portwastetreatmentfaci
    DROP CONSTRAINT ur_portwastetreatmentfa_fk;

-- -------------------------------------------------------------------- 
-- ur_portwelfarefacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_portwelfarefacility
    DROP CONSTRAINT ur_portwelfarefacility_fk;

-- -------------------------------------------------------------------- 
-- ur_publicland 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_publicland
    DROP CONSTRAINT ur_publicland_fk;

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
-- ur_railwaytrackattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_railwaytrackattribute
    DROP CONSTRAINT ur_railway_lod2net_brep_fk;

ALTER TABLE ur_railwaytrackattribute
    DROP CONSTRAINT ur_railway_lod3net_brep_fk;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_recreations
    DROP CONSTRAINT ur_recreations_fk;

-- -------------------------------------------------------------------- 
-- ur_relief_component 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_relief_component
    DROP CONSTRAINT ur_relief_component_fk;

-- -------------------------------------------------------------------- 
-- ur_relief_feature 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_relief_feature
    DROP CONSTRAINT ur_relief_feature_fk;

ALTER TABLE ur_relief_feature
    DROP CONSTRAINT ur_relief_featur_demdat_fk;

-- -------------------------------------------------------------------- 
-- ur_riverfacilityidattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_riverfacilityidattribu
    DROP CONSTRAINT ur_riverfacilityidattri_fk;

-- -------------------------------------------------------------------- 
-- ur_riverfloodingriskattri 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_riverfloodingriskattri
    DROP CONSTRAINT ur_riverfloodingriskatt_fk;

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadattribute
    DROP CONSTRAINT ur_roadattrib_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_roadstructureattribute
    DROP CONSTRAINT ur_roadstructureattribu_fk;

-- -------------------------------------------------------------------- 
-- ur_room 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_room
    DROP CONSTRAINT ur_room_fk;

ALTER TABLE ur_room
    DROP CONSTRAINT ur_room_ifcroomattribut_fk;

ALTER TABLE ur_room
    DROP CONSTRAINT ur_room_indoorroomattri_fk;

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
-- ur_sewerpipe 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_sewerpipe
    DROP CONSTRAINT ur_sewerpipe_fk;

-- -------------------------------------------------------------------- 
-- ur_shipservicefacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_shipservicefacility
    DROP CONSTRAINT ur_shipservicefacility_fk;

-- -------------------------------------------------------------------- 
-- ur_squareurbanplanattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_squareurbanplanattribu
    DROP CONSTRAINT ur_squareurba_objectcla_fk;

-- -------------------------------------------------------------------- 
-- ur_stationsquareattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_stationsquareattribute
    DROP CONSTRAINT ur_stationsquareattribu_fk;

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
    DROP CONSTRAINT ur_stoptime_trip_fk;

ALTER TABLE ur_stoptime
    DROP CONSTRAINT ur_stoptime_stop_fk;

-- -------------------------------------------------------------------- 
-- ur_terminalattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_terminalattribute
    DROP CONSTRAINT ur_terminalattribute_fk;

-- -------------------------------------------------------------------- 
-- ur_thematic_surface 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_thematic_surface
    DROP CONSTRAINT ur_thematic_surface_fk;

ALTER TABLE ur_thematic_surface
    DROP CONSTRAINT ur_themat_surfac_ifcbou_fk;

ALTER TABLE ur_thematic_surface
    DROP CONSTRAINT ur_themat_surfac_indoor_fk;

-- -------------------------------------------------------------------- 
-- ur_thematicshape 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_thematicshape
    DROP CONSTRAINT ur_themat_utilit_themat_fk;

ALTER TABLE ur_thematicshape
    DROP CONSTRAINT ur_thematics_shape_brep_fk;

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_traffic_area
    DROP CONSTRAINT ur_traffic_area_fk;

ALTER TABLE ur_traffic_area
    DROP CONSTRAINT ur_traffic_area_railway_fk;

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
    DROP CONSTRAINT ur_transp_comple_railwa_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_roadst_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_compl_roads_fk_1;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_square_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_tracka_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_traffi_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_tranda_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_tranfa_fk;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_compl_tranf_fk_1;

ALTER TABLE ur_transportation_complex
    DROP CONSTRAINT ur_transp_comple_tranus_fk;

-- -------------------------------------------------------------------- 
-- ur_trip 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_route_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_calendar_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_calendardate_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_office_fk;

ALTER TABLE ur_trip
    DROP CONSTRAINT ur_trip_shape_fk;

-- -------------------------------------------------------------------- 
-- ur_tunnel 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_fk;

ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_tunbaseattrib_fk;

ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_tundataqualit_fk;

ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_tunfacilityid_fk;

ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_tunfacilityty_fk;

ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_tunfunctional_fk;

ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_tunriskassess_fk;

ALTER TABLE ur_tunnel
    DROP CONSTRAINT ur_tunnel_tunstructurea_fk;

-- -------------------------------------------------------------------- 
-- ur_undergroundbuilding 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_undergroundbuilding
    DROP CONSTRAINT ur_undergroundbuilding_fk;

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
-- ur_utilitylink 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilitylink
    DROP CONSTRAINT ur_utilitylin_objectcla_fk;

ALTER TABLE ur_utilitylink
    DROP CONSTRAINT ur_utilitylink_fk;

-- -------------------------------------------------------------------- 
-- ur_utilitynetworkelement 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilitynetworkelement
    DROP CONSTRAINT ur_utilitynet_objectcla_fk;

ALTER TABLE ur_utilitynetworkelement
    DROP CONSTRAINT ur_utilitynetworkelemen_fk;

-- -------------------------------------------------------------------- 
-- ur_utilitynode 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilitynode
    DROP CONSTRAINT ur_utilitynode_fk;

-- -------------------------------------------------------------------- 
-- ur_utilitynodecontainer 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilitynodecontainer
    DROP CONSTRAINT ur_utilitynod_objectcla_fk;

ALTER TABLE ur_utilitynodecontainer
    DROP CONSTRAINT ur_utilitynodecontainer_fk;

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody
    DROP CONSTRAINT ur_waterbody_fk;

ALTER TABLE ur_waterbody
    DROP CONSTRAINT ur_waterbody_waterbodyd_fk;

ALTER TABLE ur_waterbody
    DROP CONSTRAINT ur_waterbody_wtrdataqua_fk;

ALTER TABLE ur_waterbody
    DROP CONSTRAINT ur_waterbody_wtrfacilit_fk;

ALTER TABLE ur_waterbody
    DROP CONSTRAINT ur_waterbody_wtrfacil_fk_1;

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterway
    DROP CONSTRAINT ur_waterway_fk;

ALTER TABLE ur_waterway
    DROP CONSTRAINT ur_waterway_waterwaydet_fk;

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
-- ur_boundarysurface 
-- -------------------------------------------------------------------- 
DROP TABLE ur_boundarysurface;

-- -------------------------------------------------------------------- 
-- ur_bridge 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridge;

-- -------------------------------------------------------------------- 
-- ur_bridgefunctionalattrib 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridgefunctionalattrib;

-- -------------------------------------------------------------------- 
-- ur_bridgestructureattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridgestructureattribu;

-- -------------------------------------------------------------------- 
-- ur_building 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building;

-- -------------------------------------------------------------------- 
-- ur_building_furniture 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_furniture;

-- -------------------------------------------------------------------- 
-- ur_building_installation 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_installation;

-- -------------------------------------------------------------------- 
-- ur_buildingattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingattribute;

-- -------------------------------------------------------------------- 
-- ur_buildingdetailattribut 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingdetailattribut;

-- -------------------------------------------------------------------- 
-- ur_buildingidattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingidattribute;

-- -------------------------------------------------------------------- 
-- ur_buildingusecaseattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_buildingusecaseattribu;

-- -------------------------------------------------------------------- 
-- ur_cable 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cable;

-- -------------------------------------------------------------------- 
-- ur_calendar 
-- -------------------------------------------------------------------- 
DROP TABLE ur_calendar;

-- -------------------------------------------------------------------- 
-- ur_calendardate 
-- -------------------------------------------------------------------- 
DROP TABLE ur_calendardate;

-- -------------------------------------------------------------------- 
-- ur_cargohandlingfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cargohandlingfacility;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
DROP TABLE ur_censusblock;

-- -------------------------------------------------------------------- 
-- ur_circularcurvetype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_circularcurvetype;

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
DROP TABLE ur_city_furniture;

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityfurnitureattribute;

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
-- ur_construction 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construction;

-- -------------------------------------------------------------------- 
-- ur_constructionbaseattrib 
-- -------------------------------------------------------------------- 
DROP TABLE ur_constructionbaseattrib;

-- -------------------------------------------------------------------- 
-- ur_constructionevent 
-- -------------------------------------------------------------------- 
DROP TABLE ur_constructionevent;

-- -------------------------------------------------------------------- 
-- ur_constructioninstallati 
-- -------------------------------------------------------------------- 
DROP TABLE ur_constructioninstallati;

-- -------------------------------------------------------------------- 
-- ur_constructionriskassess 
-- -------------------------------------------------------------------- 
DROP TABLE ur_constructionriskassess;

-- -------------------------------------------------------------------- 
-- ur_constructionstructurea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_constructionstructurea;

-- -------------------------------------------------------------------- 
-- ur_context 
-- -------------------------------------------------------------------- 
DROP TABLE ur_context;

-- -------------------------------------------------------------------- 
-- ur_controlpoint 
-- -------------------------------------------------------------------- 
DROP TABLE ur_controlpoint;

-- -------------------------------------------------------------------- 
-- ur_controlpointtype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_controlpointtype;

-- -------------------------------------------------------------------- 
-- ur_countermeasurescost 
-- -------------------------------------------------------------------- 
DROP TABLE ur_countermeasurescost;

-- -------------------------------------------------------------------- 
-- ur_crs 
-- -------------------------------------------------------------------- 
DROP TABLE ur_crs;

-- -------------------------------------------------------------------- 
-- ur_cyberportmarinaandpbs 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cyberportmarinaandpbs;

-- -------------------------------------------------------------------- 
-- ur_damattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_damattribute;

-- -------------------------------------------------------------------- 
-- ur_dataqualityattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_dataqualityattribute;

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
-- ur_disasterriskattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_disasterriskattribute;

-- -------------------------------------------------------------------- 
-- ur_dmannotation 
-- -------------------------------------------------------------------- 
DROP TABLE ur_dmannotation;

-- -------------------------------------------------------------------- 
-- ur_dmattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_dmattribute;

-- -------------------------------------------------------------------- 
-- ur_dmelement 
-- -------------------------------------------------------------------- 
DROP TABLE ur_dmelement;

-- -------------------------------------------------------------------- 
-- ur_dmgeometricattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_dmgeometricattribute;

-- -------------------------------------------------------------------- 
-- ur_elevation 
-- -------------------------------------------------------------------- 
DROP TABLE ur_elevation;

-- -------------------------------------------------------------------- 
-- ur_embankmentattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_embankmentattribute;

-- -------------------------------------------------------------------- 
-- ur_facilityattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_facilityattribute;

-- -------------------------------------------------------------------- 
-- ur_facilityidattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_facilityidattribute;

-- -------------------------------------------------------------------- 
-- ur_facilitytypeattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_facilitytypeattribute;

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
-- ur_fishingportattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_fishingportattribute;

-- -------------------------------------------------------------------- 
-- ur_fishingportcapacity 
-- -------------------------------------------------------------------- 
DROP TABLE ur_fishingportcapacity;

-- -------------------------------------------------------------------- 
-- ur_fishingportfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_fishingportfacility;

-- -------------------------------------------------------------------- 
-- ur_floodingriskattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_floodingriskattribute;

-- -------------------------------------------------------------------- 
-- ur_frequency 
-- -------------------------------------------------------------------- 
DROP TABLE ur_frequency;

-- -------------------------------------------------------------------- 
-- ur_harborfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_harborfacility;

-- -------------------------------------------------------------------- 
-- ur_height 
-- -------------------------------------------------------------------- 
DROP TABLE ur_height;

-- -------------------------------------------------------------------- 
-- ur_households 
-- -------------------------------------------------------------------- 
DROP TABLE ur_households;

-- -------------------------------------------------------------------- 
-- ur_ifcattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcattribute;

-- -------------------------------------------------------------------- 
-- ur_ifcaxis2placement3d 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcaxis2placement3d;

-- -------------------------------------------------------------------- 
-- ur_ifcbuilding 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcbuilding;

-- -------------------------------------------------------------------- 
-- ur_ifcbuildingelement 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcbuildingelement;

-- -------------------------------------------------------------------- 
-- ur_ifcclassification 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcclassification;

-- -------------------------------------------------------------------- 
-- ur_ifcclassificationrefer 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcclassificationrefer;

-- -------------------------------------------------------------------- 
-- ur_ifccoordinatereferen_1 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifccoordinatereferen_1;

-- -------------------------------------------------------------------- 
-- ur_ifccoordinatereference 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifccoordinatereference;

-- -------------------------------------------------------------------- 
-- ur_ifcdoor 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcdoor;

-- -------------------------------------------------------------------- 
-- ur_ifcelement 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcelement;

-- -------------------------------------------------------------------- 
-- ur_ifcgeometricrepresenta 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcgeometricrepresenta;

-- -------------------------------------------------------------------- 
-- ur_ifcgroup 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcgroup;

-- -------------------------------------------------------------------- 
-- ur_ifcmapconversion 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcmapconversion;

-- -------------------------------------------------------------------- 
-- ur_ifcobjectdefinition 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcobjectdefinition;

-- -------------------------------------------------------------------- 
-- ur_ifcopeningelement 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcopeningelement;

-- -------------------------------------------------------------------- 
-- ur_ifcproduct 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcproduct;

-- -------------------------------------------------------------------- 
-- ur_ifcproject 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcproject;

-- -------------------------------------------------------------------- 
-- ur_ifcprojectedcrs 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcprojectedcrs;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetbuildingcommon 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcpsetbuildingcommon;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetdoorcommon 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcpsetdoorcommon;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetopeningelementc 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcpsetopeningelementc;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetsitecommon 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcpsetsitecommon;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetspacecommon 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcpsetspacecommon;

-- -------------------------------------------------------------------- 
-- ur_ifcpsetwindowcommon 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcpsetwindowcommon;

-- -------------------------------------------------------------------- 
-- ur_ifcroot 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcroot;

-- -------------------------------------------------------------------- 
-- ur_ifcsite 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcsite;

-- -------------------------------------------------------------------- 
-- ur_ifcspace 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcspace;

-- -------------------------------------------------------------------- 
-- ur_ifcspacebasequantity 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcspacebasequantity;

-- -------------------------------------------------------------------- 
-- ur_ifcspatialstructureele 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcspatialstructureele;

-- -------------------------------------------------------------------- 
-- ur_ifcunit 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcunit;

-- -------------------------------------------------------------------- 
-- ur_ifcwall 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcwall;

-- -------------------------------------------------------------------- 
-- ur_ifcwindow 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcwindow;

-- -------------------------------------------------------------------- 
-- ur_indoorattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_indoorattribute;

-- -------------------------------------------------------------------- 
-- ur_indoorfacilityattribut 
-- -------------------------------------------------------------------- 
DROP TABLE ur_indoorfacilityattribut;

-- -------------------------------------------------------------------- 
-- ur_indoorspaceattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_indoorspaceattribute;

-- -------------------------------------------------------------------- 
-- ur_indoorstoreyattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_indoorstoreyattribute;

-- -------------------------------------------------------------------- 
-- ur_indoortactiletileattri 
-- -------------------------------------------------------------------- 
DROP TABLE ur_indoortactiletileattri;

-- -------------------------------------------------------------------- 
-- ur_indooruserdefinedattri 
-- -------------------------------------------------------------------- 
DROP TABLE ur_indooruserdefinedattri;

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
-- ur_lengthattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_lengthattribute;

-- -------------------------------------------------------------------- 
-- ur_longevitymeasures 
-- -------------------------------------------------------------------- 
DROP TABLE ur_longevitymeasures;

-- -------------------------------------------------------------------- 
-- ur_maintenancehistoryattr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_maintenancehistoryattr;

-- -------------------------------------------------------------------- 
-- ur_mooringfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_mooringfacility;

-- -------------------------------------------------------------------- 
-- ur_navigationassistancefa 
-- -------------------------------------------------------------------- 
DROP TABLE ur_navigationassistancefa;

-- -------------------------------------------------------------------- 
-- ur_numberofannualdiversio 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofannualdiversio;

-- -------------------------------------------------------------------- 
-- ur_numberoffacilities 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberoffacilities;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofhouseholds;

-- -------------------------------------------------------------------- 
-- ur_numberofhouseholds_1 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberofhouseholds_1;

-- -------------------------------------------------------------------- 
-- ur_occupancy 
-- -------------------------------------------------------------------- 
DROP TABLE ur_occupancy;

-- -------------------------------------------------------------------- 
-- ur_office 
-- -------------------------------------------------------------------- 
DROP TABLE ur_office;

-- -------------------------------------------------------------------- 
-- ur_officesandemployees 
-- -------------------------------------------------------------------- 
DROP TABLE ur_officesandemployees;

-- -------------------------------------------------------------------- 
-- ur_offsetdepth 
-- -------------------------------------------------------------------- 
DROP TABLE ur_offsetdepth;

-- -------------------------------------------------------------------- 
-- ur_opening 
-- -------------------------------------------------------------------- 
DROP TABLE ur_opening;

-- -------------------------------------------------------------------- 
-- ur_parkfacilitylongevityp 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkfacilitylongevityp;

-- -------------------------------------------------------------------- 
-- ur_parkhealthassessment 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkhealthassessment;

-- -------------------------------------------------------------------- 
-- ur_pathway 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pathway;

-- -------------------------------------------------------------------- 
-- ur_pipe 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pipe;

-- -------------------------------------------------------------------- 
-- ur_point 
-- -------------------------------------------------------------------- 
DROP TABLE ur_point;

-- -------------------------------------------------------------------- 
-- ur_pointcloud 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pointcloud;

-- -------------------------------------------------------------------- 
-- ur_pointcloudspecattribut 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pointcloudspecattribut;

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
-- ur_portattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portattribute;

-- -------------------------------------------------------------------- 
-- ur_portenvironmentalimpro 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portenvironmentalimpro;

-- -------------------------------------------------------------------- 
-- ur_portmanagementfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portmanagementfacility;

-- -------------------------------------------------------------------- 
-- ur_portpassengerfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portpassengerfacility;

-- -------------------------------------------------------------------- 
-- ur_portpollutioncontrolfa 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portpollutioncontrolfa;

-- -------------------------------------------------------------------- 
-- ur_portprotectivefacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portprotectivefacility;

-- -------------------------------------------------------------------- 
-- ur_portstoragefacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portstoragefacility;

-- -------------------------------------------------------------------- 
-- ur_porttransportationfaci 
-- -------------------------------------------------------------------- 
DROP TABLE ur_porttransportationfaci;

-- -------------------------------------------------------------------- 
-- ur_portwastetreatmentfaci 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portwastetreatmentfaci;

-- -------------------------------------------------------------------- 
-- ur_portwelfarefacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_portwelfarefacility;

-- -------------------------------------------------------------------- 
-- ur_publicland 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publicland;

-- -------------------------------------------------------------------- 
-- ur_publicsurveydataqualit 
-- -------------------------------------------------------------------- 
DROP TABLE ur_publicsurveydataqualit;

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
-- ur_railwayrouteattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_railwayrouteattribute;

-- -------------------------------------------------------------------- 
-- ur_railwaytrackattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_railwaytrackattribute;

-- -------------------------------------------------------------------- 
-- ur_realestateidattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_realestateidattribute;

-- -------------------------------------------------------------------- 
-- ur_recreations 
-- -------------------------------------------------------------------- 
DROP TABLE ur_recreations;

-- -------------------------------------------------------------------- 
-- ur_relief_component 
-- -------------------------------------------------------------------- 
DROP TABLE ur_relief_component;

-- -------------------------------------------------------------------- 
-- ur_relief_feature 
-- -------------------------------------------------------------------- 
DROP TABLE ur_relief_feature;

-- -------------------------------------------------------------------- 
-- ur_repairsbeforeparkhealt 
-- -------------------------------------------------------------------- 
DROP TABLE ur_repairsbeforeparkhealt;

-- -------------------------------------------------------------------- 
-- ur_riverfacilityidattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_riverfacilityidattribu;

-- -------------------------------------------------------------------- 
-- ur_riverfloodingriskattri 
-- -------------------------------------------------------------------- 
DROP TABLE ur_riverfloodingriskattri;

-- -------------------------------------------------------------------- 
-- ur_roadattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roadattribute;

-- -------------------------------------------------------------------- 
-- ur_roadstructureattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roadstructureattribute;

-- -------------------------------------------------------------------- 
-- ur_roadtype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roadtype;

-- -------------------------------------------------------------------- 
-- ur_room 
-- -------------------------------------------------------------------- 
DROP TABLE ur_room;

-- -------------------------------------------------------------------- 
-- ur_roomattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_roomattribute;

-- -------------------------------------------------------------------- 
-- ur_route 
-- -------------------------------------------------------------------- 
DROP TABLE ur_route;

-- -------------------------------------------------------------------- 
-- ur_sewerpipe 
-- -------------------------------------------------------------------- 
DROP TABLE ur_sewerpipe;

-- -------------------------------------------------------------------- 
-- ur_shipservicefacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_shipservicefacility;

-- -------------------------------------------------------------------- 
-- ur_slopetype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_slopetype;

-- -------------------------------------------------------------------- 
-- ur_squareurbanplanattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_squareurbanplanattribu;

-- -------------------------------------------------------------------- 
-- ur_stationsquareattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_stationsquareattribute;

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
-- ur_terminalattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_terminalattribute;

-- -------------------------------------------------------------------- 
-- ur_thematic_surface 
-- -------------------------------------------------------------------- 
DROP TABLE ur_thematic_surface;

-- -------------------------------------------------------------------- 
-- ur_thematicshape 
-- -------------------------------------------------------------------- 
DROP TABLE ur_thematicshape;

-- -------------------------------------------------------------------- 
-- ur_trackattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trackattribute;

-- -------------------------------------------------------------------- 
-- ur_traffic_area 
-- -------------------------------------------------------------------- 
DROP TABLE ur_traffic_area;

-- -------------------------------------------------------------------- 
-- ur_trafficareaattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trafficareaattribute;

-- -------------------------------------------------------------------- 
-- ur_trafficobjectusecaseat 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trafficobjectusecaseat;

-- -------------------------------------------------------------------- 
-- ur_trafficvolumeattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_trafficvolumeattribute;

-- -------------------------------------------------------------------- 
-- ur_transfer 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transfer;

-- -------------------------------------------------------------------- 
-- ur_transitioncurvetype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transitioncurvetype;

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
-- ur_tunnel 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnel;

-- -------------------------------------------------------------------- 
-- ur_tunnelfunctionalattrib 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnelfunctionalattrib;

-- -------------------------------------------------------------------- 
-- ur_tunnelstructureattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnelstructureattribu;

-- -------------------------------------------------------------------- 
-- ur_undergroundbuilding 
-- -------------------------------------------------------------------- 
DROP TABLE ur_undergroundbuilding;

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
-- ur_userdefinedvalue 
-- -------------------------------------------------------------------- 
DROP TABLE ur_userdefinedvalue;

-- -------------------------------------------------------------------- 
-- ur_utilitylink 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilitylink;

-- -------------------------------------------------------------------- 
-- ur_utilitynetworkelement 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilitynetworkelement;

-- -------------------------------------------------------------------- 
-- ur_utilitynode 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilitynode;

-- -------------------------------------------------------------------- 
-- ur_utilitynodecontainer 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilitynodecontainer;

-- -------------------------------------------------------------------- 
-- ur_verticalcurvetype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_verticalcurvetype;

-- -------------------------------------------------------------------- 
-- ur_waterbody 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody;

-- -------------------------------------------------------------------- 
-- ur_waterbodydetailattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbodydetailattribu;

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterway;

-- -------------------------------------------------------------------- 
-- ur_waterwaydetailattribut 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterwaydetailattribut;

-- -------------------------------------------------------------------- 
-- ur_zone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_zone;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop Sequences ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

DROP SEQUENCE ur_areaofannualdivers_seq;

DROP SEQUENCE ur_point_seq;

DROP SEQUENCE ur_trackattribute_seq;

DROP SEQUENCE ur_numberofhouseholds_seq;

DROP SEQUENCE ur_ifcunit_seq;

DROP SEQUENCE ur_squareurbanplanatt_seq;

DROP SEQUENCE ur_ifcattribute_seq;

DROP SEQUENCE ur_description_seq;

DROP SEQUENCE ur_dmattribute_seq;

DROP SEQUENCE ur_buildingattribute_seq;

DROP SEQUENCE ur_legalgrounds_seq;

DROP SEQUENCE ur_repairsbeforeparkh_seq;

DROP SEQUENCE ur_offsetdepth_seq;

DROP SEQUENCE ur_indoorattribute_seq;

DROP SEQUENCE ur_facilityidattribut_seq;

DROP SEQUENCE ur_railwaytrackattrib_seq;

DROP SEQUENCE ur_constructionevent_seq;

DROP SEQUENCE ur_constructionriskas_seq;

DROP SEQUENCE ur_trafficareaattribu_seq;

DROP SEQUENCE ur_realestateidattrib_seq;

DROP SEQUENCE ur_facilityattribute_seq;

DROP SEQUENCE ur_bridgestructureatt_seq;

DROP SEQUENCE ur_trafficobjectuseca_seq;

DROP SEQUENCE ur_landpriceperlandus_seq;

DROP SEQUENCE ur_keyvaluepairattrib_seq;

DROP SEQUENCE ur_dataqualityattribu_seq;

DROP SEQUENCE ur_longevitymeasures_seq;

DROP SEQUENCE ur_occupancy_seq;

DROP SEQUENCE ur_height_seq;

DROP SEQUENCE ur_countermeasurescos_seq;

DROP SEQUENCE ur_roomattribute_seq;

DROP SEQUENCE ur_elevation_seq;

DROP SEQUENCE ur_ifcclassification_seq;

DROP SEQUENCE ur_parkhealthassessme_seq;

DROP SEQUENCE ur_controlpointtype_seq;

DROP SEQUENCE ur_ifcgeometricrepres_seq;

DROP SEQUENCE ur_roadtype_seq;

DROP SEQUENCE ur_disasterriskattrib_seq;

DROP SEQUENCE ur_railwayrouteattrib_seq;

DROP SEQUENCE ur_numberofannualdive_seq;

DROP SEQUENCE ur_ifccoordinaterefer_seq;

DROP SEQUENCE ur_transitioncurvetyp_seq;

DROP SEQUENCE ur_crs_seq;

DROP SEQUENCE ur_tunnelstructureatt_seq;

DROP SEQUENCE ur_lengthattribute_seq;

DROP SEQUENCE ur_waterwaydetailattr_seq;

DROP SEQUENCE ur_context_seq;

DROP SEQUENCE ur_pointcloudspecattr_seq;

DROP SEQUENCE ur_constructionstruct_seq;

DROP SEQUENCE ur_numberofhousehol_seq_1;

DROP SEQUENCE ur_numberoffacilities_seq;

DROP SEQUENCE ur_publicsurveydataqu_seq;

DROP SEQUENCE ur_bridgefunctionalat_seq;

DROP SEQUENCE ur_thematicshape_seq;

DROP SEQUENCE ur_keyvaluepair_seq;

DROP SEQUENCE ur_cityfurnitureattri_seq;

DROP SEQUENCE ur_controlpoint_seq;

DROP SEQUENCE ur_publictransitdatat_seq;

DROP SEQUENCE ur_ifcaxis2placement3_seq;

DROP SEQUENCE ur_verticalcurvetype_seq;

DROP SEQUENCE ur_facilitytypeattrib_seq;

DROP SEQUENCE ur_constructionbaseat_seq;

DROP SEQUENCE ur_waterbodydetailatt_seq;

DROP SEQUENCE ur_tunnelfunctionalat_seq;

DROP SEQUENCE ur_dmelement_seq;

DROP SEQUENCE ur_landuseattribute_seq;

DROP SEQUENCE ur_roadattribute_seq;

DROP SEQUENCE ur_circularcurvetype_seq;

DROP SEQUENCE ur_userdefinedvalue_seq;

DROP SEQUENCE ur_populationbyageand_seq;

DROP SEQUENCE ur_slopetype_seq;
