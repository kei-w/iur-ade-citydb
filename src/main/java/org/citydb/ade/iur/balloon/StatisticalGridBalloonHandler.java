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

public class StatisticalGridBalloonHandler implements ADEBalloonHandler {
    private final SchemaMapper schemaMapper;

    public StatisticalGridBalloonHandler(BalloonManager manager) {
        this.schemaMapper = manager.getSchemaMapper();
    }

    @Override
    public String getSqlStatement(String table,
                                  String tableShortId,
                                  String aggregateColumnsClause,
                                  int lod,
                                  String schemaName) {
        String sqlStatement = null;

        if (schemaMapper.getTableName(ADETable.STATISTICALGRID).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.POPULATION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.PUBLICTRANSITACCESSIBI).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.LANDPRICE).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.LANDUSEDIVERSION).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.HOUSEHOLDS).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.OFFICESANDEMPLOYEES).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.GENERICGRIDCELL).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.STATUSOFSIGHTSEEING).equalsIgnoreCase(table)
                || schemaMapper.getTableName(ADETable.LANDSLIDEDISASTER).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId +
                    " WHERE " + tableShortId + ".id = ?";
        } else if (schemaMapper.getTableName(ADETable.NUMBEROFHOUSEHOLDS).equalsIgnoreCase(table)) {
            sqlStatement = "SELECT " + aggregateColumnsClause +
                    " FROM " + schemaName + "." + table + " " + tableShortId +
                    " WHERE " + tableShortId + ".households_numberofhouseh_id = ?";
        }

        return sqlStatement;
    }
}
