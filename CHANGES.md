Change Log
==========

### 3.0.0 - 2026-11-07

#### Breaking changes
* Updated the Urban Object module (`uro`) to version 2.0 of the i-UR ADE. The database schema for `uro` was
  regenerated with the ADE Manager Plugin and is not compatible with the 1.4 schema. The `urf`, `urg` and `urt`
  parts of the schema (table names, schema ids) are unchanged.
* Import/export of the i-UR 2.0 building attributes (`BuildingIDAttribute`, `BuildingDetailAttribute`,
  `LargeCustomerFacilityAttribute`, `BuildingDisasterRiskAttribute` subtypes, `KeyValuePairAttribute`,
  `BuildingDataQualityAttribute`).
* Removed the 1.4 `uro` handling of LandUse and TransportationComplex properties. The 2.0 attributes of LandUse,
  Road, TrafficArea, CityFurniture, Vegetation and WaterBody are stored in the schema but not yet imported/exported.
* Updated iur-ade-citygml4j to 2.0.0. The module is currently consumed from `mavenLocal()`
  (`gradlew publishToMavenLocal` in the iur-ade-citygml4j fork).

#### Fixes
* The `urf` and `urg` CityObjectGroup property exporters used the `uro` namespace for projection filtering.
* Repeated ADE properties (disaster risks, key-value pairs) are exported in import order (`ORDER BY id`).

### 2.1.0 - 2024-09-19

#### Changes
* **Breaking:** Java 11 is now the minimum required version for using the i-UR ADE extension.
* Updated impexp-client-gui to 5.5.0.
* Updated energy-ade-citygml4j to 1.5.0.

### 2.0.0 - 2021-10-08

#### Changes
* Updated PostgreSQL database schema to use 64-bit `bigint` as data type instead of 32-bit `integer` for all
  primary key and foreign key columns. Hence, this release can only be used with version 4.2.x of the 3DCityDB.
  It is not compatible with previous versions of the 3DCityDB anymore.
* Updated impexp-client-gui to 5.0.0
* Updated iur-ade-citygml4j to 1.4.2.

### 1.5.0 - 2021-04-30

#### Changes
* Updated impexp-client to 4.3.0
* Updated iur-ade-citygml4j to 1.4.1.