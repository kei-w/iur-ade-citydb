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

package org.citydb.ade.iur.schema;

import org.citydb.ade.iur.importer.urf.*;
import org.citydb.ade.iur.importer.urg.*;
import org.citydb.ade.iur.importer.uro.*;
import org.citydb.ade.iur.importer.urt.*;
import org.citydb.core.ade.importer.ADEImporter;

public enum ADETable {
    AGENCY(AgencyImporter.class),
    AREAOFANNUALDIVERSIONS(AreaOfAnnualDiversionsImporter.class),
    ATTRIBUTION(AttributionImporter.class),
    BUILDING(BuildingPropertiesImporter.class),
    BUILDINGATTRIBUTE(BuildingAttributeImporter.class),
    BUILDINGDATAQUALITYATT(BuildingDataQualityAttributeImporter.class),
    BUILDINGDETAILATTRIBUT(BuildingDetailAttributeImporter.class),
    BUILDINGDISASTERRISKAT(BuildingDisasterRiskAttributeImporter.class),
    BUILDINGFLOODINGRISKAT(BuildingFloodingRiskAttributeImporter.class),
    BUILDINGIDATTRIBUTE(BuildingIDAttributeImporter.class),
    BUILDINGRIVERFLOODINGR(BuildingRiverFloodingRiskAttributeImporter.class),
    CALENDAR(CalendarImporter.class),
    CALENDARDATE(CalendarDateImporter.class),
    CENSUSBLOCK(CensusBlockImporter.class),
    CITYFURNITUREATTRIBUTE(null),
    CITYFURNITUREDATAQUALI(null),
    CITYFURNITUREDETAILATT(null),
    CITYOBJECT(null),
    CITYOBJECTGROUP(org.citydb.ade.iur.importer.urg.CityObjectGroupPropertiesImporter.class),
    CITYOBJECTGROUP_1(org.citydb.ade.iur.importer.uro.CityObjectGroupPropertiesImporter.class),
    CITYOBJECTGROUP_2(org.citydb.ade.iur.importer.urf.CityObjectGroupPropertiesImporter.class),
    CITYOBJECTGROUP_3(org.citydb.ade.iur.importer.urt.CityObjectGroupPropertiesImporter.class),
    CITY_FURNITURE(null),
    DESCRIPTION(DescriptionImporter.class),
    DEVELOPMENTPROJECT(DevelopmentProjectImporter.class),
    DISASTERDAMAGE(DisasterDamageImporter.class),
    FAREATTRIBUTE(FareAttributeImporter.class),
    FARERULE(FareRuleImporter.class),
    FEEDINFO(FeedInfoImporter.class),
    FREQUENCY(FrequencyImporter.class),
    HOUSEHOLDS(HouseholdsImporter.class),
    KEYVALUEPAIR(org.citydb.ade.iur.importer.urg.KeyValuePairImporter.class),
    KEYVALUEPAIRATTRIBUTE(KeyValuePairAttributeImporter.class),
    LANDPRICE(LandPriceImporter.class),
    LANDPRICEPERLANDUSE(LandPricePerLandUseImporter.class),
    LANDUSEATTRIBUTE(null),
    LANDUSEDIVERSION(LandUseDiversionImporter.class),
    LAND_USE(null),
    LARGECUSTOMERFACILITYA(LargeCustomerFacilityAttributeImporter.class),
    LEGALGROUNDS(LegalGroundsImporter.class),
    NUMBEROFANNUALDIVERSIO(NumberOfAnnualDiversionsImporter.class),
    NUMBEROFHOUSEHOLDS(org.citydb.ade.iur.importer.urg.NumberOfHouseholdsImporter.class),
    NUMBEROFHOUSEHOLDS_1(org.citydb.ade.iur.importer.urf.NumberOfHouseholdsImporter.class),
    OFFICE(OfficeImporter.class),
    OFFICESANDEMPLOYEES(OfficesAndEmployeesImporter.class),
    PATHWAY(PathwayImporter.class),
    POINT(PointImporter.class),
    POLLUTION(PollutionImporter.class),
    POPULATION(PopulationImporter.class),
    POPULATIONBYAGEANDSEX(PopulationByAgeAndSexImporter.class),
    PUBLICTRANSIT(PublicTransitImporter.class),
    PUBLICTRANSITDATATYPE(PublicTransitDataTypeImporter.class),
    PUBLICTRANSPORTATIONFA(PublicTransportationFacilityImporter.class),
    RECREATIONS(RecreationsImporter.class),
    ROADATTRIBUTE(null),
    ROADDATAQUALITYATTRIBU(null),
    ROADSTRUCTUREATTRIBUTE(null),
    ROUTE(RouteImporter.class),
    STATISTICALGRID(StatisticalGridImporter.class),
    STOP(StopImporter.class),
    STOPTIME(StopTimeImporter.class),
    TRAFFICAREAATTRIBUTE(null),
    TRAFFICVOLUMEATTRIBUTE(null),
    TRAFFIC_AREA(null),
    TRANSFER(TransferImporter.class),
    TRANSLATION(TranslationImporter.class),
    TRANSLATIONJP(TranslationJPImporter.class),
    TRANSPORTATION_COMPLEX(null),
    TRIP(TripImporter.class),
    URBANFUNCTION(UrbanFunctionImporter.class),
    URBANFUNC_TO_CITYOBJEC(UrbanFunctionToCityObjectImporter.class),
    URBANIZATION(UrbanizationImporter.class),
    VEGETATIONATTRIBUTE(null),
    WATERBODY(null),
    WATERBODYATTRIBUTE(null),
    WATERBODYRIVERFLOODING(null),
    ZONE(ZoneImporter.class);

    private final Class<? extends ADEImporter> importerClass;

    ADETable(Class<? extends ADEImporter> importerClass) {
        this.importerClass = importerClass;
    }

    public Class<? extends ADEImporter> getImporterClass() {
        return importerClass;
    }
}
