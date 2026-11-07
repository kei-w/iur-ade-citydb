-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2026-11-07 13:00:51 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************** Drop foreign keys ********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
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
-- ur_bridge_to_disasterr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_bridge_to_disasterr
    DROP CONSTRAINT ur_bridge_to_disasterris_fk_2;

ALTER TABLE ur_bridge_to_disasterr
    DROP CONSTRAINT ur_bridge_to_bridge_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_bridge_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_bridge_to_dmattribu
    DROP CONSTRAINT ur_bridge_to_dmattribute_fk_2;

ALTER TABLE ur_bridge_to_dmattribu
    DROP CONSTRAINT ur_bridge_to_bridge_id_fk_4;

-- -------------------------------------------------------------------- 
-- ur_bridge_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_bridge_to_facilitya
    DROP CONSTRAINT ur_bridge_to_facilityatt_fk_2;

ALTER TABLE ur_bridge_to_facilitya
    DROP CONSTRAINT ur_bridge_to_bridge_id_fk_6;

-- -------------------------------------------------------------------- 
-- ur_bridge_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_bridge_to_keyvaluep
    DROP CONSTRAINT ur_bridge_to_keyvaluepai_fk_2;

ALTER TABLE ur_bridge_to_keyvaluep
    DROP CONSTRAINT ur_bridge_to_bridge_id_fk_8;

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
-- ur_building_to_disasterr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_disasterr
    DROP CONSTRAINT ur_building__disasterris_fk_2;

ALTER TABLE ur_building_to_disasterr
    DROP CONSTRAINT ur_building__building_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_building_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_dmattribu
    DROP CONSTRAINT ur_building__dmattribute_fk_2;

ALTER TABLE ur_building_to_dmattribu
    DROP CONSTRAINT ur_building__building_id_fk_4;

-- -------------------------------------------------------------------- 
-- ur_building_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_facilitya
    DROP CONSTRAINT ur_building__facilityatt_fk_2;

ALTER TABLE ur_building_to_facilitya
    DROP CONSTRAINT ur_building__building_id_fk_6;

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_building_to_keyvaluep
    DROP CONSTRAINT ur_building__keyvaluepai_fk_2;

ALTER TABLE ur_building_to_keyvaluep
    DROP CONSTRAINT ur_building__building_id_fk_8;

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
-- ur_cargohandlingfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cargohandlingfacility
    DROP CONSTRAINT ur_cargohandlingfacilit_fk;

-- -------------------------------------------------------------------- 
-- ur_city_furn_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_city_furn_to_dmattribu
    DROP CONSTRAINT ur_city_furn_dmattribute_fk_2;

ALTER TABLE ur_city_furn_to_dmattribu
    DROP CONSTRAINT ur_city_furn_city_furnit_fk_2;

-- -------------------------------------------------------------------- 
-- ur_city_furn_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_city_furn_to_facilitya
    DROP CONSTRAINT ur_city_furn_facilityatt_fk_2;

ALTER TABLE ur_city_furn_to_facilitya
    DROP CONSTRAINT ur_city_furn_city_furnit_fk_4;

-- -------------------------------------------------------------------- 
-- ur_city_furn_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_city_furn_to_keyvaluep
    DROP CONSTRAINT ur_city_furn_keyvaluepai_fk_2;

ALTER TABLE ur_city_furn_to_keyvaluep
    DROP CONSTRAINT ur_city_furn_city_furnit_fk_6;

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
-- ur_cityobjec_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjec_to_dmattribu
    DROP CONSTRAINT ur_cityobjec_dmattribute_fk_2;

ALTER TABLE ur_cityobjec_to_dmattribu
    DROP CONSTRAINT ur_cityobjec_cityobject__fk_2;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjec_to_facilitya
    DROP CONSTRAINT ur_cityobjec_facilityatt_fk_2;

ALTER TABLE ur_cityobjec_to_facilitya
    DROP CONSTRAINT ur_cityobjec_cityobject__fk_4;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobjec_to_keyvaluep
    DROP CONSTRAINT ur_cityobjec_keyvaluepai_fk_2;

ALTER TABLE ur_cityobjec_to_keyvaluep
    DROP CONSTRAINT ur_cityobjec_cityobject__fk_6;

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
    DROP CONSTRAINT ur_cityobject_vegdataqu_fk;

ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobject_vegfacili_fk;

ALTER TABLE ur_cityobject
    DROP CONSTRAINT ur_cityobjec_vegfacil_fk_1;

-- -------------------------------------------------------------------- 
-- ur_cityobject_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_cityobject_2
    DROP CONSTRAINT ur_cityobject_2_fk;

ALTER TABLE ur_cityobject_2
    DROP CONSTRAINT ur_cityobjec_pointcloud__fk;

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
-- ur_construct_to_construct 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_construct
    DROP CONSTRAINT ur_construct_constructio_fk_3;

ALTER TABLE ur_construct_to_construct
    DROP CONSTRAINT ur_construct_constructio_fk_4;

-- -------------------------------------------------------------------- 
-- ur_construct_to_disasterr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_disasterr
    DROP CONSTRAINT ur_construct_disasterris_fk_2;

ALTER TABLE ur_construct_to_disasterr
    DROP CONSTRAINT ur_construct_constructio_fk_16;

-- -------------------------------------------------------------------- 
-- ur_construct_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_dmattribu
    DROP CONSTRAINT ur_construct_dmattribute_fk_2;

ALTER TABLE ur_construct_to_dmattribu
    DROP CONSTRAINT ur_construct_constructio_fk_18;

-- -------------------------------------------------------------------- 
-- ur_construct_to_elevation 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_elevation
    DROP CONSTRAINT ur_construct_elevation_i_fk_2;

ALTER TABLE ur_construct_to_elevation
    DROP CONSTRAINT ur_construct_constructio_fk_6;

-- -------------------------------------------------------------------- 
-- ur_construct_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_facilitya
    DROP CONSTRAINT ur_construct_facilityatt_fk_2;

ALTER TABLE ur_construct_to_facilitya
    DROP CONSTRAINT ur_construct_constructio_fk_14;

-- -------------------------------------------------------------------- 
-- ur_construct_to_facilityt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_facilityt
    DROP CONSTRAINT ur_construct_facilitytyp_fk_2;

ALTER TABLE ur_construct_to_facilityt
    DROP CONSTRAINT ur_construct_constructio_fk_12;

-- -------------------------------------------------------------------- 
-- ur_construct_to_height 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_height
    DROP CONSTRAINT ur_construct_height_id_fk_2;

ALTER TABLE ur_construct_to_height
    DROP CONSTRAINT ur_construct_constructio_fk_8;

-- -------------------------------------------------------------------- 
-- ur_construct_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_keyvaluep
    DROP CONSTRAINT ur_construct_keyvaluepai_fk_2;

ALTER TABLE ur_construct_to_keyvaluep
    DROP CONSTRAINT ur_construct_constructio_fk_20;

-- -------------------------------------------------------------------- 
-- ur_construct_to_occupancy 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_construct_to_occupancy
    DROP CONSTRAINT ur_construct_occupancy_i_fk_2;

ALTER TABLE ur_construct_to_occupancy
    DROP CONSTRAINT ur_construct_constructio_fk_10;

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
-- ur_disasterriskattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_disasterriskattribute
    DROP CONSTRAINT ur_disasterri_objectcla_fk;

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

-- -------------------------------------------------------------------- 
-- ur_dmgeometricattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_dmgeometricattribute
    DROP CONSTRAINT ur_dmgeometricattribute_fk;

ALTER TABLE ur_dmgeometricattribute
    DROP CONSTRAINT ur_dmgeome_lod0geo_brep_fk;

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

-- -------------------------------------------------------------------- 
-- ur_harborfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_harborfacility
    DROP CONSTRAINT ur_harborfacility_fk;

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
-- ur_ifcprojec_to_ifcunit 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_ifcprojec_to_ifcunit
    DROP CONSTRAINT ur_ifcprojec_ifcunit_id_fk_2;

ALTER TABLE ur_ifcprojec_to_ifcunit
    DROP CONSTRAINT ur_ifcprojec_ifcproject__fk_2;

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
-- ur_land_use_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use_to_dmattribu
    DROP CONSTRAINT ur_land_use__dmattribute_fk_2;

ALTER TABLE ur_land_use_to_dmattribu
    DROP CONSTRAINT ur_land_use__land_use_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_land_use_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use_to_facilitya
    DROP CONSTRAINT ur_land_use__facilityatt_fk_2;

ALTER TABLE ur_land_use_to_facilitya
    DROP CONSTRAINT ur_land_use__land_use_id_fk_4;

-- -------------------------------------------------------------------- 
-- ur_land_use_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_land_use_to_keyvaluep
    DROP CONSTRAINT ur_land_use__keyvaluepai_fk_2;

ALTER TABLE ur_land_use_to_keyvaluep
    DROP CONSTRAINT ur_land_use__land_use_id_fk_6;

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_largecustomerfacilitya
    DROP CONSTRAINT ur_largecustomerfacilit_fk;

-- -------------------------------------------------------------------- 
-- ur_longevitymeasures 
-- -------------------------------------------------------------------- 
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
-- ur_opening 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_opening
    DROP CONSTRAINT ur_opening_fk;

ALTER TABLE ur_opening
    DROP CONSTRAINT ur_opening_ifcopeningat_fk;

ALTER TABLE ur_opening
    DROP CONSTRAINT ur_opening_indooropenin_fk;

-- -------------------------------------------------------------------- 
-- ur_parkfacil_to_longevity 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_parkfacil_to_longevity
    DROP CONSTRAINT ur_parkfacil_longevityme_fk_2;

ALTER TABLE ur_parkfacil_to_longevity
    DROP CONSTRAINT ur_parkfacil_parkfacilit_fk_4;

-- -------------------------------------------------------------------- 
-- ur_parkfacil_to_parkhealt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_parkfacil_to_parkhealt
    DROP CONSTRAINT ur_parkfacil_parkhealtha_fk_2;

ALTER TABLE ur_parkfacil_to_parkhealt
    DROP CONSTRAINT ur_parkfacil_parkfacilit_fk_2;

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
-- ur_pipe 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pipe
    DROP CONSTRAINT ur_pipe_objectclass_fk;

ALTER TABLE ur_pipe
    DROP CONSTRAINT ur_pipe_fk;

-- -------------------------------------------------------------------- 
-- ur_pointcloud 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_pointcloud
    DROP CONSTRAINT ur_pointcloud_fk;

ALTER TABLE ur_pointcloud
    DROP CONSTRAINT ur_pointcloud_pointclou_fk;

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
-- ur_railwaytr_to_controlpo 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_railwaytr_to_controlpo
    DROP CONSTRAINT ur_railwaytr_controlpoin_fk_2;

ALTER TABLE ur_railwaytr_to_controlpo
    DROP CONSTRAINT ur_railwaytr_railwaytrac_fk_2;

-- -------------------------------------------------------------------- 
-- ur_railwaytrackattribute 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_railwaytrackattribute
    DROP CONSTRAINT ur_railway_lod2net_brep_fk;

ALTER TABLE ur_railwaytrackattribute
    DROP CONSTRAINT ur_railway_lod3net_brep_fk;

-- -------------------------------------------------------------------- 
-- ur_relief_co_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_relief_co_to_dmattribu
    DROP CONSTRAINT ur_relief_co_dmattribute_fk_2;

ALTER TABLE ur_relief_co_to_dmattribu
    DROP CONSTRAINT ur_relief_co_relief_comp_fk_2;

-- -------------------------------------------------------------------- 
-- ur_relief_component 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_relief_component
    DROP CONSTRAINT ur_relief_component_fk;

-- -------------------------------------------------------------------- 
-- ur_relief_fe_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_relief_fe_to_keyvaluep
    DROP CONSTRAINT ur_relief_fe_keyvaluepai_fk_2;

ALTER TABLE ur_relief_fe_to_keyvaluep
    DROP CONSTRAINT ur_relief_fe_relief_feat_fk_2;

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
-- ur_transport_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transport_to_dmattribu
    DROP CONSTRAINT ur_transport_dmattribute_fk_2;

ALTER TABLE ur_transport_to_dmattribu
    DROP CONSTRAINT ur_transport_transportat_fk_2;

-- -------------------------------------------------------------------- 
-- ur_transport_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transport_to_facilitya
    DROP CONSTRAINT ur_transport_facilityatt_fk_2;

ALTER TABLE ur_transport_to_facilitya
    DROP CONSTRAINT ur_transport_transportat_fk_4;

-- -------------------------------------------------------------------- 
-- ur_transport_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_transport_to_keyvaluep
    DROP CONSTRAINT ur_transport_keyvaluepai_fk_2;

ALTER TABLE ur_transport_to_keyvaluep
    DROP CONSTRAINT ur_transport_transportat_fk_6;

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
-- ur_tunnel_to_disasterr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_tunnel_to_disasterr
    DROP CONSTRAINT ur_tunnel_to_disasterris_fk_2;

ALTER TABLE ur_tunnel_to_disasterr
    DROP CONSTRAINT ur_tunnel_to_tunnel_id_fk_2;

-- -------------------------------------------------------------------- 
-- ur_tunnel_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_tunnel_to_dmattribu
    DROP CONSTRAINT ur_tunnel_to_dmattribute_fk_2;

ALTER TABLE ur_tunnel_to_dmattribu
    DROP CONSTRAINT ur_tunnel_to_tunnel_id_fk_4;

-- -------------------------------------------------------------------- 
-- ur_tunnel_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_tunnel_to_facilitya
    DROP CONSTRAINT ur_tunnel_to_facilityatt_fk_2;

ALTER TABLE ur_tunnel_to_facilitya
    DROP CONSTRAINT ur_tunnel_to_tunnel_id_fk_6;

-- -------------------------------------------------------------------- 
-- ur_tunnel_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_tunnel_to_keyvaluep
    DROP CONSTRAINT ur_tunnel_to_keyvaluepai_fk_2;

ALTER TABLE ur_tunnel_to_keyvaluep
    DROP CONSTRAINT ur_tunnel_to_tunnel_id_fk_8;

-- -------------------------------------------------------------------- 
-- ur_undergroundbuilding 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_undergroundbuilding
    DROP CONSTRAINT ur_undergroundbuilding_fk;

-- -------------------------------------------------------------------- 
-- ur_utilityli_to_lengthatt 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilityli_to_lengthatt
    DROP CONSTRAINT ur_utilityli_lengthattri_fk_2;

ALTER TABLE ur_utilityli_to_lengthatt
    DROP CONSTRAINT ur_utilityli_utilitylink_fk_2;

-- -------------------------------------------------------------------- 
-- ur_utilitylink 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilitylink
    DROP CONSTRAINT ur_utilitylin_objectcla_fk;

ALTER TABLE ur_utilitylink
    DROP CONSTRAINT ur_utilitylink_fk;

-- -------------------------------------------------------------------- 
-- ur_utilityne_to_offsetdep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilityne_to_offsetdep
    DROP CONSTRAINT ur_utilityne_offsetdepth_fk_2;

ALTER TABLE ur_utilityne_to_offsetdep
    DROP CONSTRAINT ur_utilityne_utilitynetw_fk_2;

-- -------------------------------------------------------------------- 
-- ur_utilityne_to_thematics 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_utilityne_to_thematics
    DROP CONSTRAINT ur_utilityne_thematicsha_fk_2;

ALTER TABLE ur_utilityne_to_thematics
    DROP CONSTRAINT ur_utilityne_utilitynetw_fk_4;

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
-- ur_waterbody_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody_to_dmattribu
    DROP CONSTRAINT ur_waterbody_dmattribute_fk_2;

ALTER TABLE ur_waterbody_to_dmattribu
    DROP CONSTRAINT ur_waterbody_waterbody_i_fk_4;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_facilitya 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody_to_facilitya
    DROP CONSTRAINT ur_waterbody_facilityatt_fk_2;

ALTER TABLE ur_waterbody_to_facilitya
    DROP CONSTRAINT ur_waterbody_waterbody_i_fk_6;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_floodingr 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody_to_floodingr
    DROP CONSTRAINT ur_waterbody_floodingris_fk_2;

ALTER TABLE ur_waterbody_to_floodingr
    DROP CONSTRAINT ur_waterbody_waterbody_i_fk_2;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterbody_to_keyvaluep
    DROP CONSTRAINT ur_waterbody_keyvaluepai_fk_2;

ALTER TABLE ur_waterbody_to_keyvaluep
    DROP CONSTRAINT ur_waterbody_waterbody_i_fk_8;

-- -------------------------------------------------------------------- 
-- ur_waterway 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterway
    DROP CONSTRAINT ur_waterway_fk;

ALTER TABLE ur_waterway
    DROP CONSTRAINT ur_waterway_waterwaydet_fk;

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
-- ur_administr_to_dmattribu 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_administr_to_dmattribu
    DROP CONSTRAINT ur_administr_administrat_fk_2;

ALTER TABLE ur_administr_to_dmattribu
    DROP CONSTRAINT ur_administr_dmattribute_fk_2;

-- -------------------------------------------------------------------- 
-- ur_administration 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_administration
    DROP CONSTRAINT ur_administration_fk_2;

-- -------------------------------------------------------------------- 
-- ur_agreement 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_agreement
    DROP CONSTRAINT ur_agreement_fk_2;

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_areaclassification
    DROP CONSTRAINT ur_areaclassification_fk_2;

-- -------------------------------------------------------------------- 
-- ur_censusblock 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_censusblock
    DROP CONSTRAINT ur_censusblock_fk_2;

ALTER TABLE ur_censusblock
    DROP CONSTRAINT ur_censusblo_daytimepopu_fk_2;

ALTER TABLE ur_censusblock
    DROP CONSTRAINT ur_censusblo_economicact_fk_2;

ALTER TABLE ur_censusblock
    DROP CONSTRAINT ur_censusblo_populationt_fk_2;

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

ALTER TABLE ur_developmentproject
    DROP CONSTRAINT ur_developme_agricultura_fk_2;

ALTER TABLE ur_developmentproject
    DROP CONSTRAINT ur_developme_residential_fk_2;

ALTER TABLE ur_developmentproject
    DROP CONSTRAINT ur_developme_urbandevelo_fk_2;

-- -------------------------------------------------------------------- 
-- ur_did 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_did
    DROP CONSTRAINT ur_did_fk_2;

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
-- ur_economicactivitysumtyp 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_economicactivitysumtyp
    DROP CONSTRAINT ur_economica_economicact_fk_5;

ALTER TABLE ur_economicactivitysumtyp
    DROP CONSTRAINT ur_economica_economicact_fk_6;

ALTER TABLE ur_economicactivitysumtyp
    DROP CONSTRAINT ur_economica_economicact_fk_7;

ALTER TABLE ur_economicactivitysumtyp
    DROP CONSTRAINT ur_economica_economicact_fk_8;

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
-- ur_itemrecor_to_valuebyco 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_itemrecor_to_valuebyco
    DROP CONSTRAINT ur_itemrecor_itemrecordt_fk_2;

ALTER TABLE ur_itemrecor_to_valuebyco
    DROP CONSTRAINT ur_itemrecor_valuebycode_fk_2;

-- -------------------------------------------------------------------- 
-- ur_landprice_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landprice_2
    DROP CONSTRAINT ur_landprice_2_fk_2;

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
-- ur_landusediversion_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landusediversion_2
    DROP CONSTRAINT ur_landusediversion_2_fk_2;

ALTER TABLE ur_landusediversion_2
    DROP CONSTRAINT ur_landusedi_farmlandcon_fk_2;

ALTER TABLE ur_landusediversion_2
    DROP CONSTRAINT ur_landusedi_forestconve_fk_2;

-- -------------------------------------------------------------------- 
-- ur_newhousing 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_newhousing
    DROP CONSTRAINT ur_newhousing_fk_2;

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

ALTER TABLE ur_pollution
    DROP CONSTRAINT ur_pollution_observation_fk_2;

-- -------------------------------------------------------------------- 
-- ur_populatio_to_populat_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_populatio_to_populat_2
    DROP CONSTRAINT ur_populatio_populationb_fk_3;

ALTER TABLE ur_populatio_to_populat_2
    DROP CONSTRAINT ur_populatio_populationt_fk_2;

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
-- ur_scenicresource 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_scenicresource
    DROP CONSTRAINT ur_scenicresource_fk_2;

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
-- ur_summaryre_to_valuebyco 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_summaryre_to_valuebyco
    DROP CONSTRAINT ur_summaryre_summaryreco_fk_2;

ALTER TABLE ur_summaryre_to_valuebyco
    DROP CONSTRAINT ur_summaryre_valuebycode_fk_2;

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_supplyfacility
    DROP CONSTRAINT ur_supplyfacility_fk_2;

ALTER TABLE ur_supplyfacility
    DROP CONSTRAINT ur_supplyfac_waterworksa_fk_2;

-- -------------------------------------------------------------------- 
-- ur_target 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_target
    DROP CONSTRAINT ur_target_fk_2;

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
-- ur_urbandevelopmentproj_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbandevelopmentproj_2
    DROP CONSTRAINT ur_urbandevelop_objectclass__2;

ALTER TABLE ur_urbandevelopmentproj_2
    DROP CONSTRAINT ur_urbandevelopmentproj_2_fk_2;

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
-- ur_urbanfacilitystatus 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfacilitystatus
    DROP CONSTRAINT ur_urbanfacilitystatus_fk_2;

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
    DROP CONSTRAINT ur_urbanfunc_urbanfuncti_fk_3;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_keyvaluep 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunc_to_keyvaluep
    DROP CONSTRAINT ur_urbanfunc_keyvaluepai_fk_2;

ALTER TABLE ur_urbanfunc_to_keyvaluep
    DROP CONSTRAINT ur_urbanfunc_urbanfuncti_fk_4;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunctio_objectclass__2;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunction_fk_2;

ALTER TABLE ur_urbanfunction
    DROP CONSTRAINT ur_urbanfunc_dataquality_fk_2;

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
-- ur_urbanpark_to_breakdown 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanpark_to_breakdown
    DROP CONSTRAINT ur_urbanpark_breakdownof_fk_2;

ALTER TABLE ur_urbanpark_to_breakdown
    DROP CONSTRAINT ur_urbanpark_urbanparkat_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanplan_to_urbanplan 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanplan_to_urbanplan
    DROP CONSTRAINT ur_urbanplan_urbanplanni_fk_3;

ALTER TABLE ur_urbanplan_to_urbanplan
    DROP CONSTRAINT ur_urbanplan_urbanplanni_fk_4;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanplanningarea
    DROP CONSTRAINT ur_urbanplanningarea_fk_2;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningbasicsurv 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_urbanplanningbasicsurv
    DROP CONSTRAINT ur_urbanplannin_objectclass__2;

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
-- ur_valuebycodes 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_valuebycodes
    DROP CONSTRAINT ur_valuebyco_landprice_2_fk_3;

ALTER TABLE ur_valuebycodes
    DROP CONSTRAINT ur_valuebyco_landprice_2_fk_4;

-- -------------------------------------------------------------------- 
-- ur_waterway_2 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_waterway_2
    DROP CONSTRAINT ur_waterway_2_fk_2;

-- -------------------------------------------------------------------- 
-- ur_weatherob_to_weatherob 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_weatherob_to_weatherob
    DROP CONSTRAINT ur_weatherob_weatherobse_fk_3;

ALTER TABLE ur_weatherob_to_weatherob
    DROP CONSTRAINT ur_weatherob_weatherobse_fk_4;

-- -------------------------------------------------------------------- 
-- ur_weatherobservation 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_weatherobservation
    DROP CONSTRAINT ur_weatherobservation_fk_2;

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

ALTER TABLE ur_zone
    DROP CONSTRAINT ur_zone_urbanparkat_fk_2;

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
-- ur_landslidedisaster 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_landslidedisaster
    DROP CONSTRAINT ur_landslidedisaster_fk_2;

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
    DROP CONSTRAINT ur_populatio_populationb_fk_3;

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
-- ur_statusofsiteseeing 
-- -------------------------------------------------------------------- 
ALTER TABLE ur_statusofsiteseeing
    DROP CONSTRAINT ur_statusofsiteseeing_fk_2;

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
-- ur_boundarysurface 
-- -------------------------------------------------------------------- 
DROP TABLE ur_boundarysurface;

-- -------------------------------------------------------------------- 
-- ur_bridge 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridge;

-- -------------------------------------------------------------------- 
-- ur_bridge_to_disasterr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridge_to_disasterr;

-- -------------------------------------------------------------------- 
-- ur_bridge_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridge_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_bridge_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridge_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_bridge_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_bridge_to_keyvaluep;

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
-- ur_building_to_disasterr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_to_disasterr;

-- -------------------------------------------------------------------- 
-- ur_building_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_building_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_building_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_building_to_keyvaluep;

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
-- ur_cargohandlingfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cargohandlingfacility;

-- -------------------------------------------------------------------- 
-- ur_circularcurvetype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_circularcurvetype;

-- -------------------------------------------------------------------- 
-- ur_city_furn_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_city_furn_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_city_furn_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_city_furn_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_city_furn_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_city_furn_to_keyvaluep;

-- -------------------------------------------------------------------- 
-- ur_city_furniture 
-- -------------------------------------------------------------------- 
DROP TABLE ur_city_furniture;

-- -------------------------------------------------------------------- 
-- ur_cityfurnitureattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityfurnitureattribute;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjec_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjec_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjec_to_keyvaluep;

-- -------------------------------------------------------------------- 
-- ur_cityobjec_to_publictra 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobjec_to_publictra;

-- -------------------------------------------------------------------- 
-- ur_cityobject 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobject;

-- -------------------------------------------------------------------- 
-- ur_cityobject_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_cityobject_2;

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
-- ur_construct_to_construct 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_construct;

-- -------------------------------------------------------------------- 
-- ur_construct_to_disasterr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_disasterr;

-- -------------------------------------------------------------------- 
-- ur_construct_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_construct_to_elevation 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_elevation;

-- -------------------------------------------------------------------- 
-- ur_construct_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_construct_to_facilityt 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_facilityt;

-- -------------------------------------------------------------------- 
-- ur_construct_to_height 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_height;

-- -------------------------------------------------------------------- 
-- ur_construct_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_keyvaluep;

-- -------------------------------------------------------------------- 
-- ur_construct_to_occupancy 
-- -------------------------------------------------------------------- 
DROP TABLE ur_construct_to_occupancy;

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
-- ur_harborfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_harborfacility;

-- -------------------------------------------------------------------- 
-- ur_height 
-- -------------------------------------------------------------------- 
DROP TABLE ur_height;

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
-- ur_ifcprojec_to_ifcunit 
-- -------------------------------------------------------------------- 
DROP TABLE ur_ifcprojec_to_ifcunit;

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
-- ur_keyvaluepairattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_keyvaluepairattribute;

-- -------------------------------------------------------------------- 
-- ur_land_use 
-- -------------------------------------------------------------------- 
DROP TABLE ur_land_use;

-- -------------------------------------------------------------------- 
-- ur_land_use_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_land_use_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_land_use_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_land_use_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_land_use_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_land_use_to_keyvaluep;

-- -------------------------------------------------------------------- 
-- ur_landuseattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landuseattribute;

-- -------------------------------------------------------------------- 
-- ur_largecustomerfacilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_largecustomerfacilitya;

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
-- ur_numberoffacilities 
-- -------------------------------------------------------------------- 
DROP TABLE ur_numberoffacilities;

-- -------------------------------------------------------------------- 
-- ur_occupancy 
-- -------------------------------------------------------------------- 
DROP TABLE ur_occupancy;

-- -------------------------------------------------------------------- 
-- ur_offsetdepth 
-- -------------------------------------------------------------------- 
DROP TABLE ur_offsetdepth;

-- -------------------------------------------------------------------- 
-- ur_opening 
-- -------------------------------------------------------------------- 
DROP TABLE ur_opening;

-- -------------------------------------------------------------------- 
-- ur_parkfacil_to_longevity 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkfacil_to_longevity;

-- -------------------------------------------------------------------- 
-- ur_parkfacil_to_parkhealt 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkfacil_to_parkhealt;

-- -------------------------------------------------------------------- 
-- ur_parkfacilitylongevityp 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkfacilitylongevityp;

-- -------------------------------------------------------------------- 
-- ur_parkhealthassessment 
-- -------------------------------------------------------------------- 
DROP TABLE ur_parkhealthassessment;

-- -------------------------------------------------------------------- 
-- ur_pipe 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pipe;

-- -------------------------------------------------------------------- 
-- ur_pointcloud 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pointcloud;

-- -------------------------------------------------------------------- 
-- ur_pointcloudspecattribut 
-- -------------------------------------------------------------------- 
DROP TABLE ur_pointcloudspecattribut;

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
-- ur_railwayrouteattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_railwayrouteattribute;

-- -------------------------------------------------------------------- 
-- ur_railwaytr_to_controlpo 
-- -------------------------------------------------------------------- 
DROP TABLE ur_railwaytr_to_controlpo;

-- -------------------------------------------------------------------- 
-- ur_railwaytrackattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_railwaytrackattribute;

-- -------------------------------------------------------------------- 
-- ur_realestateidattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_realestateidattribute;

-- -------------------------------------------------------------------- 
-- ur_relief_co_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_relief_co_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_relief_component 
-- -------------------------------------------------------------------- 
DROP TABLE ur_relief_component;

-- -------------------------------------------------------------------- 
-- ur_relief_fe_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_relief_fe_to_keyvaluep;

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
-- ur_transitioncurvetype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transitioncurvetype;

-- -------------------------------------------------------------------- 
-- ur_transport_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transport_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_transport_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transport_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_transport_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transport_to_keyvaluep;

-- -------------------------------------------------------------------- 
-- ur_transportation_complex 
-- -------------------------------------------------------------------- 
DROP TABLE ur_transportation_complex;

-- -------------------------------------------------------------------- 
-- ur_tunnel 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnel;

-- -------------------------------------------------------------------- 
-- ur_tunnel_to_disasterr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnel_to_disasterr;

-- -------------------------------------------------------------------- 
-- ur_tunnel_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnel_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_tunnel_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnel_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_tunnel_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_tunnel_to_keyvaluep;

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
-- ur_userdefinedvalue 
-- -------------------------------------------------------------------- 
DROP TABLE ur_userdefinedvalue;

-- -------------------------------------------------------------------- 
-- ur_utilityli_to_lengthatt 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilityli_to_lengthatt;

-- -------------------------------------------------------------------- 
-- ur_utilitylink 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilitylink;

-- -------------------------------------------------------------------- 
-- ur_utilityne_to_offsetdep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilityne_to_offsetdep;

-- -------------------------------------------------------------------- 
-- ur_utilityne_to_thematics 
-- -------------------------------------------------------------------- 
DROP TABLE ur_utilityne_to_thematics;

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
-- ur_waterbody_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_facilitya 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody_to_facilitya;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_floodingr 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody_to_floodingr;

-- -------------------------------------------------------------------- 
-- ur_waterbody_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterbody_to_keyvaluep;

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
-- ur_administr_to_dmattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_administr_to_dmattribu;

-- -------------------------------------------------------------------- 
-- ur_administration 
-- -------------------------------------------------------------------- 
DROP TABLE ur_administration;

-- -------------------------------------------------------------------- 
-- ur_agreement 
-- -------------------------------------------------------------------- 
DROP TABLE ur_agreement;

-- -------------------------------------------------------------------- 
-- ur_agriculturaldevelopmen 
-- -------------------------------------------------------------------- 
DROP TABLE ur_agriculturaldevelopmen;

-- -------------------------------------------------------------------- 
-- ur_areaclassification 
-- -------------------------------------------------------------------- 
DROP TABLE ur_areaclassification;

-- -------------------------------------------------------------------- 
-- ur_boundary 
-- -------------------------------------------------------------------- 
DROP TABLE ur_boundary;

-- -------------------------------------------------------------------- 
-- ur_breakdownofnominalarea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_breakdownofnominalarea;

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
-- ur_daytimepopulationtype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_daytimepopulationtype;

-- -------------------------------------------------------------------- 
-- ur_developmentproject 
-- -------------------------------------------------------------------- 
DROP TABLE ur_developmentproject;

-- -------------------------------------------------------------------- 
-- ur_did 
-- -------------------------------------------------------------------- 
DROP TABLE ur_did;

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
-- ur_economicactivitysumtyp 
-- -------------------------------------------------------------------- 
DROP TABLE ur_economicactivitysumtyp;

-- -------------------------------------------------------------------- 
-- ur_economicactivitytype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_economicactivitytype;

-- -------------------------------------------------------------------- 
-- ur_exceptionalfloorareara 
-- -------------------------------------------------------------------- 
DROP TABLE ur_exceptionalfloorareara;

-- -------------------------------------------------------------------- 
-- ur_farmlandconversiontype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_farmlandconversiontype;

-- -------------------------------------------------------------------- 
-- ur_forestconversiontype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_forestconversiontype;

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
-- ur_itemrecor_to_valuebyco 
-- -------------------------------------------------------------------- 
DROP TABLE ur_itemrecor_to_valuebyco;

-- -------------------------------------------------------------------- 
-- ur_itemrecordtype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_itemrecordtype;

-- -------------------------------------------------------------------- 
-- ur_landprice_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landprice_2;

-- -------------------------------------------------------------------- 
-- ur_landreadjustmentprojec 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landreadjustmentprojec;

-- -------------------------------------------------------------------- 
-- ur_landscapezone 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landscapezone;

-- -------------------------------------------------------------------- 
-- ur_landusediversion_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landusediversion_2;

-- -------------------------------------------------------------------- 
-- ur_newhousing 
-- -------------------------------------------------------------------- 
DROP TABLE ur_newhousing;

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
-- ur_observationpoint 
-- -------------------------------------------------------------------- 
DROP TABLE ur_observationpoint;

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
-- ur_populatio_to_populat_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_populatio_to_populat_2;

-- -------------------------------------------------------------------- 
-- ur_populationbyageandsext 
-- -------------------------------------------------------------------- 
DROP TABLE ur_populationbyageandsext;

-- -------------------------------------------------------------------- 
-- ur_populationtype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_populationtype;

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
-- ur_residentialdevelopment 
-- -------------------------------------------------------------------- 
DROP TABLE ur_residentialdevelopment;

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
-- ur_scenicresource 
-- -------------------------------------------------------------------- 
DROP TABLE ur_scenicresource;

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
-- ur_summaryre_to_valuebyco 
-- -------------------------------------------------------------------- 
DROP TABLE ur_summaryre_to_valuebyco;

-- -------------------------------------------------------------------- 
-- ur_summaryrecordtype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_summaryrecordtype;

-- -------------------------------------------------------------------- 
-- ur_supplyfacility 
-- -------------------------------------------------------------------- 
DROP TABLE ur_supplyfacility;

-- -------------------------------------------------------------------- 
-- ur_target 
-- -------------------------------------------------------------------- 
DROP TABLE ur_target;

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
-- ur_urbandevelopmentproj_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbandevelopmentproj_2;

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
-- ur_urbanfacilitystatus 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfacilitystatus;

-- -------------------------------------------------------------------- 
-- ur_urbanfacilitystipulate 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfacilitystipulate;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_cityobjec 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfunc_to_cityobjec;

-- -------------------------------------------------------------------- 
-- ur_urbanfunc_to_keyvaluep 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfunc_to_keyvaluep;

-- -------------------------------------------------------------------- 
-- ur_urbanfunction 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanfunction;

-- -------------------------------------------------------------------- 
-- ur_urbanization 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanization;

-- -------------------------------------------------------------------- 
-- ur_urbanpark_to_breakdown 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanpark_to_breakdown;

-- -------------------------------------------------------------------- 
-- ur_urbanparkattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanparkattribute;

-- -------------------------------------------------------------------- 
-- ur_urbanplan_to_urbanplan 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanplan_to_urbanplan;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningarea 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanplanningarea;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningbasicsu_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanplanningbasicsu_2;

-- -------------------------------------------------------------------- 
-- ur_urbanplanningbasicsurv 
-- -------------------------------------------------------------------- 
DROP TABLE ur_urbanplanningbasicsurv;

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
-- ur_valuebycodes 
-- -------------------------------------------------------------------- 
DROP TABLE ur_valuebycodes;

-- -------------------------------------------------------------------- 
-- ur_vehicleterminalattribu 
-- -------------------------------------------------------------------- 
DROP TABLE ur_vehicleterminalattribu;

-- -------------------------------------------------------------------- 
-- ur_waterway_2 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterway_2;

-- -------------------------------------------------------------------- 
-- ur_waterworksattribute 
-- -------------------------------------------------------------------- 
DROP TABLE ur_waterworksattribute;

-- -------------------------------------------------------------------- 
-- ur_weatherob_to_weatherob 
-- -------------------------------------------------------------------- 
DROP TABLE ur_weatherob_to_weatherob;

-- -------------------------------------------------------------------- 
-- ur_weatherobservation 
-- -------------------------------------------------------------------- 
DROP TABLE ur_weatherobservation;

-- -------------------------------------------------------------------- 
-- ur_weatherobservationtype 
-- -------------------------------------------------------------------- 
DROP TABLE ur_weatherobservationtype;

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
-- ur_landslidedisaster 
-- -------------------------------------------------------------------- 
DROP TABLE ur_landslidedisaster;

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
-- ur_statusofsiteseeing 
-- -------------------------------------------------------------------- 
DROP TABLE ur_statusofsiteseeing;

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

DROP SEQUENCE ur_trackattribute_seq;

DROP SEQUENCE ur_ifcunit_seq;

DROP SEQUENCE ur_squareurbanplanatt_seq;

DROP SEQUENCE ur_ifcattribute_seq;

DROP SEQUENCE ur_dmattribute_seq;

DROP SEQUENCE ur_buildingattribute_seq;

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

DROP SEQUENCE ur_ifccoordinaterefer_seq;

DROP SEQUENCE ur_transitioncurvetyp_seq;

DROP SEQUENCE ur_crs_seq;

DROP SEQUENCE ur_tunnelstructureatt_seq;

DROP SEQUENCE ur_lengthattribute_seq;

DROP SEQUENCE ur_waterwaydetailattr_seq;

DROP SEQUENCE ur_context_seq;

DROP SEQUENCE ur_pointcloudspecattr_seq;

DROP SEQUENCE ur_constructionstruct_seq;

DROP SEQUENCE ur_numberoffacilities_seq;

DROP SEQUENCE ur_publicsurveydataqu_seq;

DROP SEQUENCE ur_bridgefunctionalat_seq;

DROP SEQUENCE ur_thematicshape_seq;

DROP SEQUENCE ur_cityfurnitureattri_seq;

DROP SEQUENCE ur_controlpoint_seq;

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

DROP SEQUENCE ur_slopetype_seq;

PURGE RECYCLEBIN;
DROP SEQUENCE ur_agriculturaldevelo_seq;

DROP SEQUENCE ur_boundary_seq;

DROP SEQUENCE ur_breakdownofnominal_seq;

DROP SEQUENCE ur_daytimepopulationt_seq;

DROP SEQUENCE ur_economicactivitysu_seq;

DROP SEQUENCE ur_economicactivityty_seq;

DROP SEQUENCE ur_farmlandconversion_seq;

DROP SEQUENCE ur_forestconversionty_seq;

DROP SEQUENCE ur_itemrecordtype_seq;

DROP SEQUENCE ur_numberofhouseholds_seq_2;

DROP SEQUENCE ur_observationpoint_seq;

DROP SEQUENCE ur_parkattribute_seq;

DROP SEQUENCE ur_parkingplaceattrib_seq;

DROP SEQUENCE ur_populationbyageand_seq_2;

DROP SEQUENCE ur_populationtype_seq;

DROP SEQUENCE ur_residentialdevelop_seq;

DROP SEQUENCE ur_sewersystemattribu_seq;

DROP SEQUENCE ur_structuredetails_seq;

DROP SEQUENCE ur_summaryrecordtype_seq;

DROP SEQUENCE ur_urbandevelopmentpr_seq;

DROP SEQUENCE ur_urbanparkattribute_seq;

DROP SEQUENCE ur_urbanplanningbasic_seq_2;

DROP SEQUENCE ur_urbanplanningbasic_seq;

DROP SEQUENCE ur_urbanrapidtransitr_seq;

DROP SEQUENCE ur_urbanroadattribute_seq;

DROP SEQUENCE ur_valuebycodes_seq;

DROP SEQUENCE ur_vehicleterminalatt_seq;

DROP SEQUENCE ur_waterworksattribut_seq;

DROP SEQUENCE ur_weatherobservation_seq;

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

