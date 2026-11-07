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

public class BuildingBalloonHandler implements ADEBalloonHandler {
    private final SchemaMapper schemaMapper;

    public BuildingBalloonHandler(BalloonManager manager) {
        this.schemaMapper = manager.getSchemaMapper();
    }

    @Override
    public String getSqlStatement(String table,
                                  String tableShortId,
                                  String aggregateColumnsClause,
                                  int lod,
                                  String schemaName) {

        String sqlStatement = null;

        if (schemaMapper.getTableName(ADETable.BUILDING).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId +
                    " WHERE " + tableShortId + ".id = ?";
        } else if (schemaMapper.getTableName(ADETable.BUILDINGIDATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = ub.buildingidattribute_id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.BUILDINGDETAILATTRIBUT).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = ub.buildingdetailattribute_id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.LARGECUSTOMERFACILITYA).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = ub.largecustomerfacilityattr_id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.REALESTATEIDATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = ub.bldgrealestateidattribute_id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.BUILDINGUSECASEATTRIBU).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = ub.bldgusecaseattribute_id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.DATAQUALITYATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = ub.bldgdataqualityattribute_id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.PUBLICSURVEYDATAQUALIT).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.DATAQUALITYATTRIBUTE) + " udq, " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = udq.publicsurveydataqualityat_id" +
                    " AND udq.id = ub.bldgdataqualityattribute_id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.DISASTERRISKATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".building_bldgdisasterrisk_id = ub.id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.FLOODINGRISKATTRIBUTE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.RIVERFLOODINGRISKATTRI).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.DISASTERRISKATTRIBUTE) + " udr, " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".id = udr.id" +
                    " AND udr.building_bldgdisasterrisk_id = ub.id" +
                    " AND ub.id = ?";
        } else if (schemaMapper.getTableName(ADETable.KEYVALUEPAIRATTRIBUTE).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId + ", " + schemaName + "." + schemaMapper.getTableName(ADETable.BUILDING) + " ub" +
                    " WHERE " + tableShortId + ".building_bldgkeyvaluepair_id = ub.id" +
                    " AND ub.id = ?";
        }

        return sqlStatement;
    }

}
