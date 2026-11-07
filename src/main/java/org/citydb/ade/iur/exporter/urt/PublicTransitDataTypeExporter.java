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

package org.citydb.ade.iur.exporter.urt;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.database.schema.mapping.MappingConstants;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.OrderByToken;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.urt.FareAttributeProperty;
import org.citygml4j.ade.iur.model.urt.FareRule;
import org.citygml4j.ade.iur.model.urt.FeedInfo;
import org.citygml4j.ade.iur.model.urt.Frequency;
import org.citygml4j.ade.iur.model.urt.PublicTransitDataType;
import org.citygml4j.ade.iur.model.urt.PublicTransitProperty;
import org.citygml4j.ade.iur.model.urt.RouteProperty;
import org.citygml4j.ade.iur.model.urt.StopProperty;
import org.citygml4j.ade.iur.model.urt.StopTime;
import org.citygml4j.ade.iur.model.urt.Transfer;
import org.citygml4j.ade.iur.model.urt.Translation;
import org.citygml4j.ade.iur.model.urt.TripProperty;
import org.citygml4j.model.gml.basicTypes.Code;

import java.sql.*;

public class PublicTransitDataTypeExporter implements PublicTransitModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final PreparedStatement psRecordId;
    private final PreparedStatement psFrom;
    private final PreparedStatement psTo;
    private final PreparedStatement psFrequencyTrip;
    private final PreparedStatement psStopTimeTrip;
    private final PreparedStatement psStop;
    private final PreparedStatement psFare;
    private final PreparedStatement psRoute;
    private final PreparedStatement ps;

    public PublicTransitDataTypeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.PUBLICTRANSITDATATYPE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table feedInfo = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FEEDINFO)));
        Table translation = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRANSLATION)));
        Table transfer = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRANSFER)));
        Table frequency = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FREQUENCY)));
        Table stopTime = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STOPTIME)));
        Table fareRule = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FARERULE)));
        Table cityObject = new Table(helper.getTableNameWithSchema(MappingConstants.CITYOBJECT));
        Table recordIds = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRANSLATI_TO_PUBLICTRA)));
        Select recordIdSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, recordIds.getColumn("publictransit_id")))
                .addSelection(ComparisonFactory.equalTo(recordIds.getColumn("translation_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psRecordId = connection.prepareStatement(recordIdSelect.toString());
        Table froms = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRANSFER_TO_STOP)));
        Select fromSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, froms.getColumn("stop_id")))
                .addSelection(ComparisonFactory.equalTo(froms.getColumn("transfer_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psFrom = connection.prepareStatement(fromSelect.toString());
        Table tos = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.TRANSFER_TO_STOP_2)));
        Select toSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, tos.getColumn("stop_id")))
                .addSelection(ComparisonFactory.equalTo(tos.getColumn("transfer_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psTo = connection.prepareStatement(toSelect.toString());
        Table frequencyTrips = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FREQUENCY_TO_TRIP)));
        Select frequencyTripSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, frequencyTrips.getColumn("trip_id")))
                .addSelection(ComparisonFactory.equalTo(frequencyTrips.getColumn("frequency_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psFrequencyTrip = connection.prepareStatement(frequencyTripSelect.toString());
        Table stopTimeTrips = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STOPTIME_TO_TRIP)));
        Select stopTimeTripSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, stopTimeTrips.getColumn("trip_id")))
                .addSelection(ComparisonFactory.equalTo(stopTimeTrips.getColumn("stoptime_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psStopTimeTrip = connection.prepareStatement(stopTimeTripSelect.toString());
        Table stops = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.STOPTIME_TO_STOP)));
        Select stopSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, stops.getColumn("stop_id")))
                .addSelection(ComparisonFactory.equalTo(stops.getColumn("stoptime_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psStop = connection.prepareStatement(stopSelect.toString());
        Table fares = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FARERULE_TO_FAREATTRI)));
        Select fareSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, fares.getColumn("fareattribute_id")))
                .addSelection(ComparisonFactory.equalTo(fares.getColumn("farerule_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psFare = connection.prepareStatement(fareSelect.toString());
        Table routes = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.FARERULE_TO_ROUTE)));
        Select routeSelect = new Select().addProjection(cityObject.getColumn("gmlid"))
                .addJoin(JoinFactory.inner(cityObject, "id", ComparisonName.EQUAL_TO, routes.getColumn("route_id")))
                .addSelection(ComparisonFactory.equalTo(routes.getColumn("farerule_id"), new PlaceHolder<>()))
                .addOrderBy(new OrderByToken(cityObject.getColumn("id")));
        psRoute = connection.prepareStatement(routeSelect.toString());
        Select select = new Select().addProjection(table.getColumns("id", "objectclass_id"))
                .addProjection(feedInfo.getColumns("publishername", "publisherurl", "defaultlanguage", "defaultlanguage_codespace", "startdate",
                        "enddate", "version", "contactemail", "contacturl", "detailedinfo"))
                .addProjection(translation.getColumns("tablename", "tablename_codespace", "fieldname", "translation", "fieldvalue", "recordsubid"))
                .addProjection(transfer.getColumns("transfertype", "transfertype_codespace", "mintransfertime"))
                .addProjection(frequency.getColumns("starttime", "endtime", "headwaysecs", "exacttimes", "exacttimes_codespace"))
                .addProjection(stopTime.getColumns("arrivaltime", "departuretime", "stopsequence", "headsign", "pickuptype", "pickuptype_codespace",
                        "dropofftype", "dropofftype_codespace", "continuouspickuptype", "continuouspickupty_codespace",
                        "continuousdropofftype", "continuousdropofft_codespace", "shapedistancetraveled", "timepoint",
                        "timepoint_codespace"))
                .addProjection(fareRule.getColumns("originid", "originid_codespace", "destinationid", "destinationid_codespace", "containsid",
                        "containsid_codespace"))
                .addProjection(feedInfo.getColumn("language", "feedinfo_language"))
                .addProjection(feedInfo.getColumn("language_codespace", "feedinfo_language_codespace"))
                .addProjection(translation.getColumn("language", "translation_language"))
                .addProjection(translation.getColumn("language_codespace", "translation_language_codespace"))
                .addJoin(JoinFactory.left(feedInfo, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(translation, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(transfer, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(frequency, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(stopTime, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(fareRule, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

    }

    public PublicTransitDataType doExport(long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? doExport(objectId, rs) : null;
        }
    }

    private PublicTransitDataType doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        PublicTransitDataType publicTransitDataType = objectMapper.createType(objectClassId, PublicTransitDataType.class);
        if (publicTransitDataType == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate public transit data type (id: " + objectId + ").");
            return null;
        }

        if (publicTransitDataType instanceof FeedInfo) {
            FeedInfo feedInfo = (FeedInfo) publicTransitDataType;

            feedInfo.setPublisherName(rs.getString("publishername"));

            feedInfo.setPublisherUrl(rs.getString("publisherurl"));

            String language = rs.getString("feedinfo_language");
            if (!rs.wasNull()) {
                Code code = new Code(language);
                code.setCodeSpace(rs.getString("feedinfo_language_codespace"));
                feedInfo.setLanguage(code);
            }

            String defaultLanguage = rs.getString("defaultlanguage");
            if (!rs.wasNull()) {
                Code code = new Code(defaultLanguage);
                code.setCodeSpace(rs.getString("defaultlanguage_codespace"));
                feedInfo.setDefaultLanguage(code);
            }

            Date startDate = rs.getDate("startdate");
            if (!rs.wasNull())
                feedInfo.setStartDate(startDate.toLocalDate());

            Date endDate = rs.getDate("enddate");
            if (!rs.wasNull())
                feedInfo.setEndDate(endDate.toLocalDate());

            feedInfo.setVersion(rs.getString("version"));

            feedInfo.setContactEmail(rs.getString("contactemail"));

            feedInfo.setContactURL(rs.getString("contacturl"));

            feedInfo.setDetailedInfo(rs.getString("detailedinfo"));
        }

        if (publicTransitDataType instanceof Translation) {
            Translation translation = (Translation) publicTransitDataType;

            String tableName = rs.getString("tablename");
            if (!rs.wasNull()) {
                Code code = new Code(tableName);
                code.setCodeSpace(rs.getString("tablename_codespace"));
                translation.setTableName(code);
            }

            translation.setFieldName(rs.getString("fieldname"));

            String language = rs.getString("translation_language");
            if (!rs.wasNull()) {
                Code code = new Code(language);
                code.setCodeSpace(rs.getString("translation_language_codespace"));
                translation.setLanguage(code);
            }

            translation.setTranslation(rs.getString("translation"));

            translation.setFieldValue(rs.getString("fieldvalue"));

            psRecordId.setLong(1, objectId);
            try (ResultSet recordIdRs = psRecordId.executeQuery()) {
                while (recordIdRs.next()) {
                    PublicTransitProperty recordIdProperty = new PublicTransitProperty();
                    recordIdProperty.setHref("#" + recordIdRs.getString("gmlid"));
                    translation.setRecordId(recordIdProperty);
                }
            }

            translation.setRecordSubId(rs.getString("recordsubid"));
        }

        if (publicTransitDataType instanceof Transfer) {
            Transfer transfer = (Transfer) publicTransitDataType;

            String transferType = rs.getString("transfertype");
            if (!rs.wasNull()) {
                Code code = new Code(transferType);
                code.setCodeSpace(rs.getString("transfertype_codespace"));
                transfer.setTransferType(code);
            }

            int minTransferTime = rs.getInt("mintransfertime");
            if (!rs.wasNull())
                transfer.setMinTransferTime(minTransferTime);

            psFrom.setLong(1, objectId);
            try (ResultSet fromRs = psFrom.executeQuery()) {
                while (fromRs.next()) {
                    StopProperty fromProperty = new StopProperty();
                    fromProperty.setHref("#" + fromRs.getString("gmlid"));
                    transfer.setFrom(fromProperty);
                }
            }

            psTo.setLong(1, objectId);
            try (ResultSet toRs = psTo.executeQuery()) {
                while (toRs.next()) {
                    StopProperty toProperty = new StopProperty();
                    toProperty.setHref("#" + toRs.getString("gmlid"));
                    transfer.setTo(toProperty);
                }
            }
        }

        if (publicTransitDataType instanceof Frequency) {
            Frequency frequency = (Frequency) publicTransitDataType;

            Time startTime = rs.getTime("starttime");
            if (!rs.wasNull())
                frequency.setStartTime(startTime.toLocalTime());

            Time endTime = rs.getTime("endtime");
            if (!rs.wasNull())
                frequency.setEndTime(endTime.toLocalTime());

            int headwaySecs = rs.getInt("headwaysecs");
            if (!rs.wasNull())
                frequency.setHeadwaySecs(headwaySecs);

            String exactTimes = rs.getString("exacttimes");
            if (!rs.wasNull()) {
                Code code = new Code(exactTimes);
                code.setCodeSpace(rs.getString("exacttimes_codespace"));
                frequency.setExactTimes(code);
            }

            psFrequencyTrip.setLong(1, objectId);
            try (ResultSet tripRs = psFrequencyTrip.executeQuery()) {
                while (tripRs.next()) {
                    TripProperty tripProperty = new TripProperty();
                    tripProperty.setHref("#" + tripRs.getString("gmlid"));
                    frequency.setTrip(tripProperty);
                }
            }
        }

        if (publicTransitDataType instanceof StopTime) {
            StopTime stopTime = (StopTime) publicTransitDataType;

            Time arrivalTime = rs.getTime("arrivaltime");
            if (!rs.wasNull())
                stopTime.setArrivalTime(arrivalTime.toLocalTime());

            Time departureTime = rs.getTime("departuretime");
            if (!rs.wasNull())
                stopTime.setDepartureTime(departureTime.toLocalTime());

            int stopSequence = rs.getInt("stopsequence");
            if (!rs.wasNull())
                stopTime.setStopSequence(stopSequence);

            stopTime.setHeadsign(rs.getString("headsign"));

            String pickupType = rs.getString("pickuptype");
            if (!rs.wasNull()) {
                Code code = new Code(pickupType);
                code.setCodeSpace(rs.getString("pickuptype_codespace"));
                stopTime.setPickupType(code);
            }

            String dropoffType = rs.getString("dropofftype");
            if (!rs.wasNull()) {
                Code code = new Code(dropoffType);
                code.setCodeSpace(rs.getString("dropofftype_codespace"));
                stopTime.setDropoffType(code);
            }

            String continuousPickupType = rs.getString("continuouspickuptype");
            if (!rs.wasNull()) {
                Code code = new Code(continuousPickupType);
                code.setCodeSpace(rs.getString("continuouspickupty_codespace"));
                stopTime.setContinuousPickupType(code);
            }

            String continuousDropoffType = rs.getString("continuousdropofftype");
            if (!rs.wasNull()) {
                Code code = new Code(continuousDropoffType);
                code.setCodeSpace(rs.getString("continuousdropofft_codespace"));
                stopTime.setContinuousDropoffType(code);
            }

            double shapeDistanceTraveled = rs.getDouble("shapedistancetraveled");
            if (!rs.wasNull())
                stopTime.setShapeDistanceTraveled(shapeDistanceTraveled);

            String timePoint = rs.getString("timepoint");
            if (!rs.wasNull()) {
                Code code = new Code(timePoint);
                code.setCodeSpace(rs.getString("timepoint_codespace"));
                stopTime.setTimePoint(code);
            }

            psStopTimeTrip.setLong(1, objectId);
            try (ResultSet tripRs = psStopTimeTrip.executeQuery()) {
                while (tripRs.next()) {
                    TripProperty tripProperty = new TripProperty();
                    tripProperty.setHref("#" + tripRs.getString("gmlid"));
                    stopTime.setTrip(tripProperty);
                }
            }

            psStop.setLong(1, objectId);
            try (ResultSet stopRs = psStop.executeQuery()) {
                while (stopRs.next()) {
                    StopProperty stopProperty = new StopProperty();
                    stopProperty.setHref("#" + stopRs.getString("gmlid"));
                    stopTime.setStop(stopProperty);
                }
            }
        }

        if (publicTransitDataType instanceof FareRule) {
            FareRule fareRule = (FareRule) publicTransitDataType;

            String originId = rs.getString("originid");
            if (!rs.wasNull()) {
                Code code = new Code(originId);
                code.setCodeSpace(rs.getString("originid_codespace"));
                fareRule.setOriginId(code);
            }

            String destinationId = rs.getString("destinationid");
            if (!rs.wasNull()) {
                Code code = new Code(destinationId);
                code.setCodeSpace(rs.getString("destinationid_codespace"));
                fareRule.setDestinationId(code);
            }

            String containsId = rs.getString("containsid");
            if (!rs.wasNull()) {
                Code code = new Code(containsId);
                code.setCodeSpace(rs.getString("containsid_codespace"));
                fareRule.setContainsId(code);
            }

            psFare.setLong(1, objectId);
            try (ResultSet fareRs = psFare.executeQuery()) {
                while (fareRs.next()) {
                    FareAttributeProperty fareProperty = new FareAttributeProperty();
                    fareProperty.setHref("#" + fareRs.getString("gmlid"));
                    fareRule.setFare(fareProperty);
                }
            }

            psRoute.setLong(1, objectId);
            try (ResultSet routeRs = psRoute.executeQuery()) {
                while (routeRs.next()) {
                    RouteProperty routeProperty = new RouteProperty();
                    routeProperty.setHref("#" + routeRs.getString("gmlid"));
                    fareRule.setRoute(routeProperty);
                }
            }
        }
        return publicTransitDataType;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
        psRecordId.close();
        psFrom.close();
        psTo.close();
        psFrequencyTrip.close();
        psStopTimeTrip.close();
        psStop.close();
        psFare.close();
        psRoute.close();

    }
}
