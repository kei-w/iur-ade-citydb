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

package org.citydb.ade.iur.balloon;

import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.SchemaMapper;
import org.citydb.core.ade.visExporter.ADEBalloonHandler;

public class UrbanFunctionBalloonHandler implements ADEBalloonHandler {
    private final SchemaMapper schemaMapper;

    public UrbanFunctionBalloonHandler(BalloonManager manager) {
        this.schemaMapper = manager.getSchemaMapper();
    }

    @Override
    public String getSqlStatement(String table,
                                  String tableShortId,
                                  String aggregateColumnsClause,
                                  int lod,
                                  String schemaName) {
        String sqlStatement = null;

        if (schemaMapper.getTableName(ADETable.URBANFUNCTION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.ZONE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.THREEDIMENSIONALEXTENT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANPLANNINGAREA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.QUASIURBANPLANNINGAREA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.AREACLASSIFICATION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISTRICTSANDZONES).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.USEDISTRICT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIALUSEDISTRICT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIALUSERESTRICTIOND).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.EXCEPTIONALFLOORAREARA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.HIGHRISERESIDENTIALATT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.HEIGHTCONTROLDISTRICT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.HIGHLEVELUSEDISTRICT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIFIEDBLOCK).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIALURBANRENAISSANC).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.RESIDENTIALENVIRONMENT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIALUSEATTRACTIONDI).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIFIEDDISASTERPREVE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.LANDSCAPEZONE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SCENICDISTRICT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.PORTZONE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIALGREENSPACECONSE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.TREEPLANTINGDISTRICT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISTRIBUTIONBUSINESSZO).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.PRODUCTIVEGREENZONE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.PROJECTPROMOTIONAREA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANREDEVELOPMENTPROM).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANDISASTERRECOVERYP).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANDEVELOPMENTPROJ_2).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.LANDREADJUSTMENTPROJEC).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.NEWHOUSINGANDURBANDEVE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.INDUSTRIALPARKDEVELOPM).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANREDEVELOPMENTPROJ).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.NEWURBANINFRASTRUCTURE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.RESIDENTIALBLOCKCONSTR).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISASTERPREVENTIONBLOC).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANRENEWALPROJECT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SCHEDULEDAREAFORURBAND).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANFACILITY).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.TRAFFICFACILITY).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.OPENSPACEFORPUBLICUSE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SUPPLYFACILITY).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.TREATMENTFACILITY).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.WATERWAY_2).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.COLLECTIVEHOUSINGFACIL).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.COLLECTIVEGOVERNMENTAN).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.COLLECTIVEURBANDISASTE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISTRIBUTIONBUSINESSPA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANFACILITYSTIPULATE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.COLLECTIVEFACILITIESFO).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.COLLECTIVEFACILITIES_2).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.COLLECTIVEFACILITIES_3).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.ABSTRACTDISTRICTPLAN).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISTRICTPLAN).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISASTERPREVENTIONBL_2).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.HISTORICSCENERYMAINTEN).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.ROADSIDEDISTRICTPLAN).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISTRICTDEVELOPMENTPLA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.ROADSIDEDISTRICTIMPROV).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.RURALDISTRICTIMPROVEME).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISTRICT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.ZONALDISASTERPREVENTIO).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URGENTURBANRENEWALAREA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SPECIFIEDURGENTURBANRE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.PRIVATEURBANRENEWALPRO).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.GLOBALHUBCITYDEVELOPME).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.ADMINISTRATION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.AGREEMENT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISASTERDAMAGE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.POLLUTION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DISASTERPREVENTIONBASE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.RECREATIONS).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.LANDUSEDIVERSION_2).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANIZATION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SEDIMENTDISASTERPRONEA).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DID).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.CENSUSBLOCK).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.NEWHOUSING).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.URBANFACILITYSTATUS).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.PUBLICTRANSITFACILITY).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.LANDPRICE_2).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.WEATHEROBSERVATION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.SCENICRESOURCE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId +
                    " WHERE " + tableShortId + ".id = ?";
        } else if (schemaMapper.getTableName(ADETable.DATAQUALITYATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.URBANFUNCTION) + " p" +
                    " WHERE " + tableShortId + ".id = p.dataqualityattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.URBANPARKATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.ZONE) + " p" +
                    " WHERE " + tableShortId + ".id = p.urbanparkattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.URBANROADATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.TRAFFICFACILITY) + " p" +
                    " WHERE " + tableShortId + ".id = p.urbanroadattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.URBANRAPIDTRANSITRAILR).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.TRAFFICFACILITY) + " p" +
                    " WHERE " + tableShortId + ".id = p.urbanrapidtransitrailroad_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.PARKINGPLACEATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.TRAFFICFACILITY) + " p" +
                    " WHERE " + tableShortId + ".id = p.parkingplaceattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.VEHICLETERMINALATTRIBU).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.TRAFFICFACILITY) + " p" +
                    " WHERE " + tableShortId + ".id = p.vehicleterminalattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.PARKATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.OPENSPACEFORPUBLICUSE) + " p" +
                    " WHERE " + tableShortId + ".id = p.parkattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.WATERWORKSATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.SUPPLYFACILITY) + " p" +
                    " WHERE " + tableShortId + ".id = p.waterworksattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.SEWERSYSTEMATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.TREATMENTFACILITY) + " p" +
                    " WHERE " + tableShortId + ".id = p.sewersystemsattribute_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.OBSERVATIONPOINT).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.POLLUTION) + " p" +
                    " WHERE " + tableShortId + ".id = p.observationpoint_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.FARMLANDCONVERSIONTYPE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.LANDUSEDIVERSION_2) + " p" +
                    " WHERE " + tableShortId + ".id = p.farmlandconversiontype_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.FORESTCONVERSIONTYPE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.LANDUSEDIVERSION_2) + " p" +
                    " WHERE " + tableShortId + ".id = p.forestconversiontype_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.POPULATIONTYPE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.CENSUSBLOCK) + " p" +
                    " WHERE " + tableShortId + ".id = p.populationtype_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.DAYTIMEPOPULATIONTYPE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.CENSUSBLOCK) + " p" +
                    " WHERE " + tableShortId + ".id = p.daytimepopulationtype_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.ECONOMICACTIVITYTYPE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.CENSUSBLOCK) + " p" +
                    " WHERE " + tableShortId + ".id = p.economicactivitytype_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.RESIDENTIALDEVELOPMENT).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT) + " p" +
                    " WHERE " + tableShortId + ".id = p.residentialdevelopmenttyp_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.URBANDEVELOPMENTPROJEC).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT) + " p" +
                    " WHERE " + tableShortId + ".id = p.urbandevelopmenttype_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.AGRICULTURALDEVELOPMEN).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.DEVELOPMENTPROJECT) + " p" +
                    " WHERE " + tableShortId + ".id = p.agriculturaldevelopmentty_id" +
                    " AND p.id = ?";
        } else if (schemaMapper.getTableName(ADETable.VALUEBYCODES).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId +
                    " WHERE " + tableShortId + ".landprice_2_landpricehist_id = ?";
        }

        return sqlStatement;
    }
}
