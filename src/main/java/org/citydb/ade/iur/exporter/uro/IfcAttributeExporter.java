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

package org.citydb.ade.iur.exporter.uro;

import org.citydb.ade.iur.exporter.ExportManager;
import org.citydb.ade.iur.schema.ADETable;
import org.citydb.ade.iur.schema.ObjectMapper;
import org.citydb.core.ade.exporter.CityGMLExportHelper;
import org.citydb.core.operation.exporter.CityGMLExportException;
import org.citydb.core.operation.exporter.util.AttributeValueSplitter;
import org.citydb.core.operation.exporter.util.SplitValue;
import org.citydb.core.util.Util;
import org.citydb.sqlbuilder.expression.PlaceHolder;
import org.citydb.sqlbuilder.schema.Table;
import org.citydb.sqlbuilder.select.Select;
import org.citydb.sqlbuilder.select.join.JoinFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonFactory;
import org.citydb.sqlbuilder.select.operator.comparison.ComparisonName;
import org.citygml4j.ade.iur.model.uro.IfcAttribute;
import org.citygml4j.ade.iur.model.uro.IfcBuilding;
import org.citygml4j.ade.iur.model.uro.IfcBuildingElement;
import org.citygml4j.ade.iur.model.uro.IfcBuildingStorey;
import org.citygml4j.ade.iur.model.uro.IfcClassification;
import org.citygml4j.ade.iur.model.uro.IfcClassificationProperty;
import org.citygml4j.ade.iur.model.uro.IfcClassificationReference;
import org.citygml4j.ade.iur.model.uro.IfcCoordinateReferenceSystem;
import org.citygml4j.ade.iur.model.uro.IfcCoordinateReferenceSystemProperty;
import org.citygml4j.ade.iur.model.uro.IfcCoordinateReferenceSystemSelect;
import org.citygml4j.ade.iur.model.uro.IfcDoor;
import org.citygml4j.ade.iur.model.uro.IfcElement;
import org.citygml4j.ade.iur.model.uro.IfcElementCompositionEnum;
import org.citygml4j.ade.iur.model.uro.IfcGeometricRepresentationContext;
import org.citygml4j.ade.iur.model.uro.IfcGeometricRepresentationContextProperty;
import org.citygml4j.ade.iur.model.uro.IfcInternalOrExternalEnum;
import org.citygml4j.ade.iur.model.uro.IfcMapConversion;
import org.citygml4j.ade.iur.model.uro.IfcObject;
import org.citygml4j.ade.iur.model.uro.IfcOpeningElement;
import org.citygml4j.ade.iur.model.uro.IfcProject;
import org.citygml4j.ade.iur.model.uro.IfcProjectedCRS;
import org.citygml4j.ade.iur.model.uro.IfcPsetBuildingCommon;
import org.citygml4j.ade.iur.model.uro.IfcPsetDoorCommon;
import org.citygml4j.ade.iur.model.uro.IfcPsetOpeningElementCommon;
import org.citygml4j.ade.iur.model.uro.IfcPsetSiteCommon;
import org.citygml4j.ade.iur.model.uro.IfcPsetSpaceCommon;
import org.citygml4j.ade.iur.model.uro.IfcPsetWindowCommon;
import org.citygml4j.ade.iur.model.uro.IfcRoot;
import org.citygml4j.ade.iur.model.uro.IfcSite;
import org.citygml4j.ade.iur.model.uro.IfcSpace;
import org.citygml4j.ade.iur.model.uro.IfcSpaceBaseQuantity;
import org.citygml4j.ade.iur.model.uro.IfcSpatialStructureElement;
import org.citygml4j.ade.iur.model.uro.IfcTransportElementTypeEnum;
import org.citygml4j.ade.iur.model.uro.IfcUnit;
import org.citygml4j.ade.iur.model.uro.IfcUnitProperty;
import org.citygml4j.ade.iur.model.uro.IfcWall;
import org.citygml4j.ade.iur.model.uro.IfcWindow;
import org.citygml4j.model.citygml.core.Address;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.basicTypes.Measure;
import org.citygml4j.model.gml.measures.Length;

import java.time.Year;

import java.sql.*;

public class IfcAttributeExporter implements UrbanObjectModuleExporter {
    private final CityGMLExportHelper helper;
    private final ObjectMapper objectMapper;
    private final ExportManager manager;
    private final PreparedStatement ps;
    private final IfcGeometricRepresentationContextExporter ifcGeometricRepresentationContextExporter;
    private final IfcUnitExporter ifcUnitExporter;
    private IfcCoordinateReferenceSystemSelectExporter ifcCoordinateReferenceSystemSelectExporter;
    private IfcAttributeExporter ifcAttributeExporter;
    private final IfcClassificationExporter ifcClassificationExporter;
    private final AttributeValueSplitter valueSplitter;

    public IfcAttributeExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
        this.helper = helper;
        this.objectMapper = manager.getObjectMapper();
        String tableName = manager.getSchemaMapper().getTableName(ADETable.IFCATTRIBUTE);

        Table table = new Table(helper.getTableNameWithSchema(tableName));
        Table ifcRoot = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCROOT)));
        Table ifcProduct = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPRODUCT)));
        Table ifcSpatialStructureElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCSPATIALSTRUCTUREELE)));
        Table ifcBuilding = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCBUILDING)));
        Table ifcSite = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCSITE)));
        Table ifcSpace = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCSPACE)));
        Table ifcElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCELEMENT)));
        Table ifcBuildingElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCBUILDINGELEMENT)));
        Table ifcWall = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCWALL)));
        Table ifcWindow = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCWINDOW)));
        Table ifcDoor = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCDOOR)));
        Table ifcOpeningElement = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCOPENINGELEMENT)));
        Table ifcProject = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPROJECT)));
        Table ifcPsetBuildingCommon = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPSETBUILDINGCOMMON)));
        Table ifcPsetSiteCommon = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPSETSITECOMMON)));
        Table ifcCoordinateReferenceSystem = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCCOORDINATEREFERENCE)));
        Table ifcProjectedCRS = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPROJECTEDCRS)));
        Table ifcMapConversion = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCMAPCONVERSION)));
        Table ifcClassificationReference = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCCLASSIFICATIONREFER)));
        Table ifcPsetSpaceCommon = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPSETSPACECOMMON)));
        Table ifcSpaceBaseQuantity = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCSPACEBASEQUANTITY)));
        Table ifcPsetWindowCommon = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPSETWINDOWCOMMON)));
        Table ifcPsetDoorCommon = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPSETDOORCOMMON)));
        Table ifcPsetOpeningElementCommon = new Table(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETable.IFCPSETOPENINGELEMENTC)));
        Select select = new Select().addProjection(table.getColumns("id", "objectclass_id"))
                .addProjection(ifcRoot.getColumns("globalid", "objecttype"))
                .addProjection(ifcSpatialStructureElement.getColumns("compositiontype", "elevation", "elevation_uom"))
                .addProjection(ifcBuilding.getColumns("elevationofrefheight", "elevationofrefheight_uom", "elevationofterrain", "elevationofterrain_uom",
                        "buildingaddress_id"))
                .addProjection(ifcSite.getColumns("reflongitude", "reflatitude", "refelevation", "refelevation_uom", "landtitlenumber",
                        "siteaddress_id"))
                .addProjection(ifcSpace.getColumns("interiororexteriorspace", "elevationwithflooring", "elevationwithflooring_uom"))
                .addProjection(ifcElement.getColumns("tag"))
                .addProjection(ifcBuildingElement.getColumns("elementtype", "elementtype_codespace", "predefinedtype", "predefinedtype_codespace", "shapetype",
                        "shapetype_codespace", "numberofriser", "numberoftreads", "riserheight", "riserheight_uom",
                        "treadlength", "treadlength_uom", "operationtype", "capacitybyweight", "capacitybyweight_uom",
                        "capacitybynumber"))
                .addProjection(ifcWall.getColumns("nominallength", "nominallength_uom", "nominalwidth", "nominalwidth_uom", "grossfootprintarea",
                        "grossfootprintarea_uom", "netfootprintarea", "netfootprintarea_uom", "grosssidearea",
                        "grosssidearea_uom", "netsidearea", "netsidearea_uom", "grosssidearealeft",
                        "grosssidearealeft_uom", "netsidearealeft", "netsidearealeft_uom", "grosssidearearight",
                        "grosssidearearight_uom", "netsidearearight", "netsidearearight_uom"))
                .addProjection(ifcOpeningElement.getColumns("nominalarea", "nominalarea_uom", "nominalvolume", "nominalvolume_uom"))
                .addProjection(ifcProject.getColumns("phase", "representationcontexts_id"))
                .addProjection(ifcPsetBuildingCommon.getColumns("buildingid", "ispermanentid", "mainfireuse", "ancillaryfireuse", "sprinklerprotection",
                        "sprinklerprotectionautomatic", "occupancytype", "occupancytype_codespace", "numberofstoreys",
                        "yearofconstruction", "islandmarked"))
                .addProjection(ifcPsetSiteCommon.getColumns("buildablearea", "buildablearea_uom", "totalarea", "totalarea_uom", "buildingheightlimit",
                        "buildingheightlimit_uom"))
                .addProjection(ifcCoordinateReferenceSystem.getColumns("geodeticdatum", "verticaldatum"))
                .addProjection(ifcProjectedCRS.getColumns("mapunit", "mapprojection", "mapzone"))
                .addProjection(ifcMapConversion.getColumns("sourcecrs_id", "targetcrs_id", "eastings", "eastings_uom", "northings", "northings_uom",
                        "orthogonalheight", "orthogonalheight_uom", "xaxisabscissa", "xaxisordinate", "scale"))
                .addProjection(ifcClassificationReference.getColumns("location", "itemreference", "itemreference_codespace", "referencedsource_id"))
                .addProjection(ifcPsetSpaceCommon.getColumns("category", "floorcovering", "wallcovering", "ceilingcovering", "skirtingboard", "netplannedarea",
                        "netplannedarea_uom", "publiclyaccessible", "concealedflooring", "concealedceiling"))
                .addProjection(ifcSpaceBaseQuantity.getColumns("clearheight", "clearheight_uom", "finishceilingheight", "finishceilingheight_uom",
                        "grossperimeter", "grossperimeter_uom", "netperimeter", "netperimeter_uom", "grossceilingarea",
                        "grossceilingarea_uom", "grossfloorarea", "grossfloorarea_uom", "netceilingarea",
                        "netceilingarea_uom", "netfloorarea", "netfloorarea_uom", "grosswallarea", "grosswallarea_uom",
                        "netwallarea", "netwallarea_uom"))
                .addProjection(ifcPsetDoorCommon.getColumns("selfclosing"))
                .addProjection(ifcPsetOpeningElementCommon.getColumns("purpose", "protectedopening", "paralleljambs"))
                .addProjection(ifcRoot.getColumn("name", "ifcroot_name"))
                .addProjection(ifcRoot.getColumn("description", "ifcroot_description"))
                .addProjection(ifcSpatialStructureElement.getColumn("longname", "ifcspatialstructureelement_longname"))
                .addProjection(ifcWall.getColumn("nominalheight", "ifcwall_nominalheight"))
                .addProjection(ifcWall.getColumn("nominalheight_uom", "ifcwall_nominalheight_uom"))
                .addProjection(ifcWall.getColumn("grossvolume", "ifcwall_grossvolume"))
                .addProjection(ifcWall.getColumn("grossvolume_uom", "ifcwall_grossvolume_uom"))
                .addProjection(ifcWall.getColumn("netvolume", "ifcwall_netvolume"))
                .addProjection(ifcWall.getColumn("netvolume_uom", "ifcwall_netvolume_uom"))
                .addProjection(ifcWindow.getColumn("overallheight", "ifcwindow_overallheight"))
                .addProjection(ifcWindow.getColumn("overallheight_uom", "ifcwindow_overallheight_uom"))
                .addProjection(ifcWindow.getColumn("overallwidth", "ifcwindow_overallwidth"))
                .addProjection(ifcWindow.getColumn("overallwidth_uom", "ifcwindow_overallwidth_uom"))
                .addProjection(ifcDoor.getColumn("overallheight", "ifcdoor_overallheight"))
                .addProjection(ifcDoor.getColumn("overallheight_uom", "ifcdoor_overallheight_uom"))
                .addProjection(ifcDoor.getColumn("overallwidth", "ifcdoor_overallwidth"))
                .addProjection(ifcDoor.getColumn("overallwidth_uom", "ifcdoor_overallwidth_uom"))
                .addProjection(ifcProject.getColumn("longname", "ifcproject_longname"))
                .addProjection(ifcPsetBuildingCommon.getColumn("grossplannedarea", "ifcpsetbuildingcommon_grossplannedarea"))
                .addProjection(ifcPsetBuildingCommon.getColumn("grossplannedarea_uom", "ifcpsetbuildingcommon_grossplannedarea_uom"))
                .addProjection(ifcCoordinateReferenceSystem.getColumn("name", "ifccoordinatereferencesystem_name"))
                .addProjection(ifcCoordinateReferenceSystem.getColumn("description", "ifccoordinatereferencesystem_description"))
                .addProjection(ifcClassificationReference.getColumn("name", "ifcclassificationreference_name"))
                .addProjection(ifcPsetSpaceCommon.getColumn("reference", "ifcpsetspacecommon_reference"))
                .addProjection(ifcPsetSpaceCommon.getColumn("grossplannedarea", "ifcpsetspacecommon_grossplannedarea"))
                .addProjection(ifcPsetSpaceCommon.getColumn("grossplannedarea_uom", "ifcpsetspacecommon_grossplannedarea_uom"))
                .addProjection(ifcPsetSpaceCommon.getColumn("handicapaccessible", "ifcpsetspacecommon_handicapaccessible"))
                .addProjection(ifcSpaceBaseQuantity.getColumn("nominalheight", "ifcspacebasequantity_nominalheight"))
                .addProjection(ifcSpaceBaseQuantity.getColumn("nominalheight_uom", "ifcspacebasequantity_nominalheight_uom"))
                .addProjection(ifcSpaceBaseQuantity.getColumn("grossvolume", "ifcspacebasequantity_grossvolume"))
                .addProjection(ifcSpaceBaseQuantity.getColumn("grossvolume_uom", "ifcspacebasequantity_grossvolume_uom"))
                .addProjection(ifcSpaceBaseQuantity.getColumn("netvolume", "ifcspacebasequantity_netvolume"))
                .addProjection(ifcSpaceBaseQuantity.getColumn("netvolume_uom", "ifcspacebasequantity_netvolume_uom"))
                .addProjection(ifcPsetWindowCommon.getColumn("reference", "ifcpsetwindowcommon_reference"))
                .addProjection(ifcPsetWindowCommon.getColumn("acousticrating", "ifcpsetwindowcommon_acousticrating"))
                .addProjection(ifcPsetWindowCommon.getColumn("firerating", "ifcpsetwindowcommon_firerating"))
                .addProjection(ifcPsetWindowCommon.getColumn("securityrating", "ifcpsetwindowcommon_securityrating"))
                .addProjection(ifcPsetWindowCommon.getColumn("isexternal", "ifcpsetwindowcommon_isexternal"))
                .addProjection(ifcPsetWindowCommon.getColumn("infiltration", "ifcpsetwindowcommon_infiltration"))
                .addProjection(ifcPsetWindowCommon.getColumn("thermaltransmittance", "ifcpsetwindowcommon_thermaltransmittance"))
                .addProjection(ifcPsetWindowCommon.getColumn("glazingareafraction", "ifcpsetwindowcommon_glazingareafraction"))
                .addProjection(ifcPsetWindowCommon.getColumn("smokestop", "ifcpsetwindowcommon_smokestop"))
                .addProjection(ifcPsetDoorCommon.getColumn("reference", "ifcpsetdoorcommon_reference"))
                .addProjection(ifcPsetDoorCommon.getColumn("acousticrating", "ifcpsetdoorcommon_acousticrating"))
                .addProjection(ifcPsetDoorCommon.getColumn("firerating", "ifcpsetdoorcommon_firerating"))
                .addProjection(ifcPsetDoorCommon.getColumn("securityrating", "ifcpsetdoorcommon_securityrating"))
                .addProjection(ifcPsetDoorCommon.getColumn("isexternal", "ifcpsetdoorcommon_isexternal"))
                .addProjection(ifcPsetDoorCommon.getColumn("infiltration", "ifcpsetdoorcommon_infiltration"))
                .addProjection(ifcPsetDoorCommon.getColumn("thermaltransmittance", "ifcpsetdoorcommon_thermaltransmittance"))
                .addProjection(ifcPsetDoorCommon.getColumn("glazingareafraction", "ifcpsetdoorcommon_glazingareafraction"))
                .addProjection(ifcPsetDoorCommon.getColumn("handicapaccessible", "ifcpsetdoorcommon_handicapaccessible"))
                .addProjection(ifcPsetDoorCommon.getColumn("fireexit", "ifcpsetdoorcommon_fireexit"))
                .addProjection(ifcPsetDoorCommon.getColumn("smokestop", "ifcpsetdoorcommon_smokestop"))
                .addProjection(ifcPsetOpeningElementCommon.getColumn("reference", "ifcpsetopeningelementcommon_reference"))
                .addProjection(ifcPsetOpeningElementCommon.getColumn("fireexit", "ifcpsetopeningelementcommon_fireexit"))
                .addJoin(JoinFactory.left(ifcRoot, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcProduct, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcSpatialStructureElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcBuilding, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcSite, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcSpace, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcBuildingElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcWall, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcWindow, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcDoor, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcOpeningElement, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcProject, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcPsetBuildingCommon, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcPsetSiteCommon, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcCoordinateReferenceSystem, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcProjectedCRS, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcMapConversion, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcClassificationReference, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcPsetSpaceCommon, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcSpaceBaseQuantity, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcPsetWindowCommon, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcPsetDoorCommon, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addJoin(JoinFactory.left(ifcPsetOpeningElementCommon, "id", ComparisonName.EQUAL_TO, table.getColumn("id")))
                .addSelection(ComparisonFactory.equalTo(table.getColumn("id"), new PlaceHolder<>()));
        ps = connection.prepareStatement(select.toString());

        this.manager = manager;
        ifcGeometricRepresentationContextExporter = manager.getExporter(IfcGeometricRepresentationContextExporter.class);
        ifcUnitExporter = manager.getExporter(IfcUnitExporter.class);
        ifcClassificationExporter = manager.getExporter(IfcClassificationExporter.class);
        valueSplitter = helper.getAttributeValueSplitter();
    }

    public IfcAttribute doExport(long objectId) throws CityGMLExportException, SQLException {
        ps.setLong(1, objectId);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? doExport(objectId, rs) : null;
        }
    }

    private IfcAttribute doExport(long objectId, ResultSet rs) throws CityGMLExportException, SQLException {
        int objectClassId = rs.getInt("objectclass_id");
        if (rs.wasNull())
            return null;

        IfcAttribute ifcAttribute = objectMapper.createType(objectClassId, IfcAttribute.class);
        if (ifcAttribute == null) {
            helper.logOrThrowErrorMessage("Failed to instantiate ifc attribute (id: " + objectId + ").");
            return null;
        }

        if (ifcAttribute instanceof IfcRoot) {
            IfcRoot ifcRoot = (IfcRoot) ifcAttribute;

            ifcRoot.setGlobalId(rs.getString("globalid"));

            ifcRoot.setName(rs.getString("ifcroot_name"));

            ifcRoot.setDescription(rs.getString("ifcroot_description"));
        }

        if (ifcAttribute instanceof IfcObject) {
            IfcObject ifcObject = (IfcObject) ifcAttribute;

            ifcObject.setObjectType(rs.getString("objecttype"));
        }

        if (ifcAttribute instanceof IfcSpatialStructureElement) {
            IfcSpatialStructureElement ifcSpatialStructureElement = (IfcSpatialStructureElement) ifcAttribute;

            ifcSpatialStructureElement.setLongName(rs.getString("ifcspatialstructureelement_longname"));

            String compositionType = rs.getString("compositiontype");
            if (!rs.wasNull())
                ifcSpatialStructureElement.setCompositionType(IfcElementCompositionEnum.fromValue(compositionType));
        }

        if (ifcAttribute instanceof IfcBuildingStorey) {
            IfcBuildingStorey ifcBuildingStorey = (IfcBuildingStorey) ifcAttribute;

            double elevation = rs.getDouble("elevation");
            if (!rs.wasNull()) {
                Length length = new Length(elevation);
                length.setUom(rs.getString("elevation_uom"));
                ifcBuildingStorey.setElevation(length);
            }
        }

        if (ifcAttribute instanceof IfcBuilding) {
            IfcBuilding ifcBuilding = (IfcBuilding) ifcAttribute;

            double elevationOfRefHeight = rs.getDouble("elevationofrefheight");
            if (!rs.wasNull()) {
                Length length = new Length(elevationOfRefHeight);
                length.setUom(rs.getString("elevationofrefheight_uom"));
                ifcBuilding.setElevationOfRefHeight(length);
            }

            double elevationOfTerrain = rs.getDouble("elevationofterrain");
            if (!rs.wasNull()) {
                Length length = new Length(elevationOfTerrain);
                length.setUom(rs.getString("elevationofterrain_uom"));
                ifcBuilding.setElevationOfTerrain(length);
            }

            long buildingAddressId = rs.getLong("buildingaddress_id");
            if (buildingAddressId != 0) {
                Address buildingAddress = helper.createObject(buildingAddressId, Util.getObjectClassId(Address.class), Address.class);
                if (buildingAddress != null)
                    ifcBuilding.setBuildingAddress(new AddressProperty(buildingAddress));
            }
        }

        if (ifcAttribute instanceof IfcSite) {
            IfcSite ifcSite = (IfcSite) ifcAttribute;

            double refLongitude = rs.getDouble("reflongitude");
            if (!rs.wasNull())
                ifcSite.setRefLongitude(refLongitude);

            double refLatitude = rs.getDouble("reflatitude");
            if (!rs.wasNull())
                ifcSite.setRefLatitude(refLatitude);

            double refElevation = rs.getDouble("refelevation");
            if (!rs.wasNull()) {
                Length length = new Length(refElevation);
                length.setUom(rs.getString("refelevation_uom"));
                ifcSite.setRefElevation(length);
            }

            ifcSite.setLandTitleNumber(rs.getString("landtitlenumber"));

            long siteAddressId = rs.getLong("siteaddress_id");
            if (siteAddressId != 0) {
                Address siteAddress = helper.createObject(siteAddressId, Util.getObjectClassId(Address.class), Address.class);
                if (siteAddress != null)
                    ifcSite.setSiteAddress(new AddressProperty(siteAddress));
            }
        }

        if (ifcAttribute instanceof IfcSpace) {
            IfcSpace ifcSpace = (IfcSpace) ifcAttribute;

            String interiorOrExteriorSpace = rs.getString("interiororexteriorspace");
            if (!rs.wasNull())
                ifcSpace.setInteriorOrExteriorSpace(IfcInternalOrExternalEnum.fromValue(interiorOrExteriorSpace));

            double elevationWithFlooring = rs.getDouble("elevationwithflooring");
            if (!rs.wasNull()) {
                Length length = new Length(elevationWithFlooring);
                length.setUom(rs.getString("elevationwithflooring_uom"));
                ifcSpace.setElevationWithFlooring(length);
            }
        }

        if (ifcAttribute instanceof IfcElement) {
            IfcElement ifcElement = (IfcElement) ifcAttribute;

            ifcElement.setTag(rs.getString("tag"));
        }

        if (ifcAttribute instanceof IfcBuildingElement) {
            IfcBuildingElement ifcBuildingElement = (IfcBuildingElement) ifcAttribute;

            String elementType = rs.getString("elementtype");
            if (!rs.wasNull()) {
                Code code = new Code(elementType);
                code.setCodeSpace(rs.getString("elementtype_codespace"));
                ifcBuildingElement.setElementType(code);
            }

            String predefinedType = rs.getString("predefinedtype");
            if (!rs.wasNull()) {
                Code code = new Code(predefinedType);
                code.setCodeSpace(rs.getString("predefinedtype_codespace"));
                ifcBuildingElement.setPredefinedType(code);
            }

            String shapeType = rs.getString("shapetype");
            if (!rs.wasNull()) {
                Code code = new Code(shapeType);
                code.setCodeSpace(rs.getString("shapetype_codespace"));
                ifcBuildingElement.setShapeType(code);
            }

            int numberOfRiser = rs.getInt("numberofriser");
            if (!rs.wasNull())
                ifcBuildingElement.setNumberOfRiser(numberOfRiser);

            int numberOfTreads = rs.getInt("numberoftreads");
            if (!rs.wasNull())
                ifcBuildingElement.setNumberOfTreads(numberOfTreads);

            double riserHeight = rs.getDouble("riserheight");
            if (!rs.wasNull()) {
                Length length = new Length(riserHeight);
                length.setUom(rs.getString("riserheight_uom"));
                ifcBuildingElement.setRiserHeight(length);
            }

            double treadLength = rs.getDouble("treadlength");
            if (!rs.wasNull()) {
                Length length = new Length(treadLength);
                length.setUom(rs.getString("treadlength_uom"));
                ifcBuildingElement.setTreadLength(length);
            }

            String operationType = rs.getString("operationtype");
            if (!rs.wasNull())
                ifcBuildingElement.setOperationType(IfcTransportElementTypeEnum.fromValue(operationType));

            double capacityByWeight = rs.getDouble("capacitybyweight");
            if (!rs.wasNull()) {
                Measure measure = new Measure(capacityByWeight);
                measure.setUom(rs.getString("capacitybyweight_uom"));
                ifcBuildingElement.setCapacityByWeight(measure);
            }

            int capacityByNumber = rs.getInt("capacitybynumber");
            if (!rs.wasNull())
                ifcBuildingElement.setCapacityByNumber(capacityByNumber);
        }

        if (ifcAttribute instanceof IfcWall) {
            IfcWall ifcWall = (IfcWall) ifcAttribute;

            double nominalLength = rs.getDouble("nominallength");
            if (!rs.wasNull()) {
                Length length = new Length(nominalLength);
                length.setUom(rs.getString("nominallength_uom"));
                ifcWall.setNominalLength(length);
            }

            double nominalWidth = rs.getDouble("nominalwidth");
            if (!rs.wasNull()) {
                Length length = new Length(nominalWidth);
                length.setUom(rs.getString("nominalwidth_uom"));
                ifcWall.setNominalWidth(length);
            }

            double nominalHeight = rs.getDouble("ifcwall_nominalheight");
            if (!rs.wasNull()) {
                Length length = new Length(nominalHeight);
                length.setUom(rs.getString("ifcwall_nominalheight_uom"));
                ifcWall.setNominalHeight(length);
            }

            double grossFootPrintArea = rs.getDouble("grossfootprintarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossFootPrintArea);
                measure.setUom(rs.getString("grossfootprintarea_uom"));
                ifcWall.setGrossFootPrintArea(measure);
            }

            double netFootPrintArea = rs.getDouble("netfootprintarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netFootPrintArea);
                measure.setUom(rs.getString("netfootprintarea_uom"));
                ifcWall.setNetFootPrintArea(measure);
            }

            double grossSideArea = rs.getDouble("grosssidearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossSideArea);
                measure.setUom(rs.getString("grosssidearea_uom"));
                ifcWall.setGrossSideArea(measure);
            }

            double netSideArea = rs.getDouble("netsidearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netSideArea);
                measure.setUom(rs.getString("netsidearea_uom"));
                ifcWall.setNetSideArea(measure);
            }

            double grossSideAreaLeft = rs.getDouble("grosssidearealeft");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossSideAreaLeft);
                measure.setUom(rs.getString("grosssidearealeft_uom"));
                ifcWall.setGrossSideAreaLeft(measure);
            }

            double netSideAreaLeft = rs.getDouble("netsidearealeft");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netSideAreaLeft);
                measure.setUom(rs.getString("netsidearealeft_uom"));
                ifcWall.setNetSideAreaLeft(measure);
            }

            double grossSideAreaRight = rs.getDouble("grosssidearearight");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossSideAreaRight);
                measure.setUom(rs.getString("grosssidearearight_uom"));
                ifcWall.setGrossSideAreaRight(measure);
            }

            double netSideAreaRight = rs.getDouble("netsidearearight");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netSideAreaRight);
                measure.setUom(rs.getString("netsidearearight_uom"));
                ifcWall.setNetSideAreaRight(measure);
            }

            double grossVolume = rs.getDouble("ifcwall_grossvolume");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossVolume);
                measure.setUom(rs.getString("ifcwall_grossvolume_uom"));
                ifcWall.setGrossVolume(measure);
            }

            double netVolume = rs.getDouble("ifcwall_netvolume");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netVolume);
                measure.setUom(rs.getString("ifcwall_netvolume_uom"));
                ifcWall.setNetVolume(measure);
            }
        }

        if (ifcAttribute instanceof IfcWindow) {
            IfcWindow ifcWindow = (IfcWindow) ifcAttribute;

            double overallHeight = rs.getDouble("ifcwindow_overallheight");
            if (!rs.wasNull()) {
                Length length = new Length(overallHeight);
                length.setUom(rs.getString("ifcwindow_overallheight_uom"));
                ifcWindow.setOverallHeight(length);
            }

            double overallWidth = rs.getDouble("ifcwindow_overallwidth");
            if (!rs.wasNull()) {
                Length length = new Length(overallWidth);
                length.setUom(rs.getString("ifcwindow_overallwidth_uom"));
                ifcWindow.setOverallWidth(length);
            }
        }

        if (ifcAttribute instanceof IfcDoor) {
            IfcDoor ifcDoor = (IfcDoor) ifcAttribute;

            double overallHeight = rs.getDouble("ifcdoor_overallheight");
            if (!rs.wasNull()) {
                Length length = new Length(overallHeight);
                length.setUom(rs.getString("ifcdoor_overallheight_uom"));
                ifcDoor.setOverallHeight(length);
            }

            double overallWidth = rs.getDouble("ifcdoor_overallwidth");
            if (!rs.wasNull()) {
                Length length = new Length(overallWidth);
                length.setUom(rs.getString("ifcdoor_overallwidth_uom"));
                ifcDoor.setOverallWidth(length);
            }
        }

        if (ifcAttribute instanceof IfcOpeningElement) {
            IfcOpeningElement ifcOpeningElement = (IfcOpeningElement) ifcAttribute;

            double nominalArea = rs.getDouble("nominalarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(nominalArea);
                measure.setUom(rs.getString("nominalarea_uom"));
                ifcOpeningElement.setNominalArea(measure);
            }

            double nominalVolume = rs.getDouble("nominalvolume");
            if (!rs.wasNull()) {
                Measure measure = new Measure(nominalVolume);
                measure.setUom(rs.getString("nominalvolume_uom"));
                ifcOpeningElement.setNominalVolume(measure);
            }
        }

        if (ifcAttribute instanceof IfcProject) {
            IfcProject ifcProject = (IfcProject) ifcAttribute;

            ifcProject.setLongName(rs.getString("ifcproject_longname"));

            ifcProject.setPhase(rs.getString("phase"));

            long representationContextsId = rs.getLong("representationcontexts_id");
            if (representationContextsId != 0) {
                IfcGeometricRepresentationContext representationContexts = new IfcGeometricRepresentationContext();
                ifcGeometricRepresentationContextExporter.doExport(representationContexts, representationContextsId);
                ifcProject.setRepresentationContexts(new IfcGeometricRepresentationContextProperty(representationContexts));
            }

            for (IfcUnit unitsInContext : ifcUnitExporter.doExport(objectId))
                ifcProject.getUnitsInContext().add(new IfcUnitProperty(unitsInContext));
        }

        if (ifcAttribute instanceof IfcPsetBuildingCommon) {
            IfcPsetBuildingCommon ifcPsetBuildingCommon = (IfcPsetBuildingCommon) ifcAttribute;

            ifcPsetBuildingCommon.setBuildingId(rs.getString("buildingid"));

            boolean isPermanentId = rs.getBoolean("ispermanentid");
            if (!rs.wasNull())
                ifcPsetBuildingCommon.setIsPermanentId(isPermanentId);

            ifcPsetBuildingCommon.setMainFireUse(rs.getString("mainfireuse"));

            ifcPsetBuildingCommon.setAncillaryFireUse(rs.getString("ancillaryfireuse"));

            boolean sprinklerProtection = rs.getBoolean("sprinklerprotection");
            if (!rs.wasNull())
                ifcPsetBuildingCommon.setSprinklerProtection(sprinklerProtection);

            boolean sprinklerProtectionAutomatic = rs.getBoolean("sprinklerprotectionautomatic");
            if (!rs.wasNull())
                ifcPsetBuildingCommon.setSprinklerProtectionAutomatic(sprinklerProtectionAutomatic);

            String occupancyType = rs.getString("occupancytype");
            if (!rs.wasNull()) {
                Code code = new Code(occupancyType);
                code.setCodeSpace(rs.getString("occupancytype_codespace"));
                ifcPsetBuildingCommon.setOccupancyType(code);
            }

            double grossPlannedArea = rs.getDouble("ifcpsetbuildingcommon_grossplannedarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossPlannedArea);
                measure.setUom(rs.getString("ifcpsetbuildingcommon_grossplannedarea_uom"));
                ifcPsetBuildingCommon.setGrossPlannedArea(measure);
            }

            int numberOfStoreys = rs.getInt("numberofstoreys");
            if (!rs.wasNull())
                ifcPsetBuildingCommon.setNumberOfStoreys(numberOfStoreys);

            Date yearOfConstruction = rs.getDate("yearofconstruction");
            if (!rs.wasNull())
                ifcPsetBuildingCommon.setYearOfConstruction(Year.of(yearOfConstruction.toLocalDate().getYear()));

            boolean isLandmarked = rs.getBoolean("islandmarked");
            if (!rs.wasNull())
                ifcPsetBuildingCommon.setIsLandmarked(isLandmarked);
        }

        if (ifcAttribute instanceof IfcPsetSiteCommon) {
            IfcPsetSiteCommon ifcPsetSiteCommon = (IfcPsetSiteCommon) ifcAttribute;

            double buildableArea = rs.getDouble("buildablearea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(buildableArea);
                measure.setUom(rs.getString("buildablearea_uom"));
                ifcPsetSiteCommon.setBuildableArea(measure);
            }

            double totalArea = rs.getDouble("totalarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(totalArea);
                measure.setUom(rs.getString("totalarea_uom"));
                ifcPsetSiteCommon.setTotalArea(measure);
            }

            double buildingHeightLimit = rs.getDouble("buildingheightlimit");
            if (!rs.wasNull()) {
                Length length = new Length(buildingHeightLimit);
                length.setUom(rs.getString("buildingheightlimit_uom"));
                ifcPsetSiteCommon.setBuildingHeightLimit(length);
            }
        }

        if (ifcAttribute instanceof IfcCoordinateReferenceSystem) {
            IfcCoordinateReferenceSystem ifcCoordinateReferenceSystem = (IfcCoordinateReferenceSystem) ifcAttribute;

            ifcCoordinateReferenceSystem.setName(rs.getString("ifccoordinatereferencesystem_name"));

            ifcCoordinateReferenceSystem.setDescription(rs.getString("ifccoordinatereferencesystem_description"));

            ifcCoordinateReferenceSystem.setGeodeticDatum(rs.getString("geodeticdatum"));

            ifcCoordinateReferenceSystem.setVerticalDatum(rs.getString("verticaldatum"));
        }

        if (ifcAttribute instanceof IfcProjectedCRS) {
            IfcProjectedCRS ifcProjectedCRS = (IfcProjectedCRS) ifcAttribute;

            ifcProjectedCRS.setMapUnit(rs.getString("mapunit"));

            ifcProjectedCRS.setMapProjection(rs.getString("mapprojection"));

            ifcProjectedCRS.setMapZone(rs.getString("mapzone"));
        }

        if (ifcAttribute instanceof IfcMapConversion) {
            IfcMapConversion ifcMapConversion = (IfcMapConversion) ifcAttribute;

            long sourceCRSId = rs.getLong("sourcecrs_id");
            if (sourceCRSId != 0) {
                IfcCoordinateReferenceSystemSelect sourceCRS = new IfcCoordinateReferenceSystemSelect();
                ifcCoordinateReferenceSystemSelectExporter().doExport(sourceCRS, sourceCRSId);
                ifcMapConversion.setSourceCRS(sourceCRS);
            }

            long targetCRSId = rs.getLong("targetcrs_id");
            if (targetCRSId != 0) {
                IfcAttribute targetCRS = ifcAttributeExporter().doExport(targetCRSId);
                if (targetCRS instanceof IfcCoordinateReferenceSystem)
                    ifcMapConversion.setTargetCRS(new IfcCoordinateReferenceSystemProperty((IfcCoordinateReferenceSystem) targetCRS));
            }

            double eastings = rs.getDouble("eastings");
            if (!rs.wasNull()) {
                Length length = new Length(eastings);
                length.setUom(rs.getString("eastings_uom"));
                ifcMapConversion.setEastings(length);
            }

            double northings = rs.getDouble("northings");
            if (!rs.wasNull()) {
                Length length = new Length(northings);
                length.setUom(rs.getString("northings_uom"));
                ifcMapConversion.setNorthings(length);
            }

            double orthogonalHeight = rs.getDouble("orthogonalheight");
            if (!rs.wasNull()) {
                Length length = new Length(orthogonalHeight);
                length.setUom(rs.getString("orthogonalheight_uom"));
                ifcMapConversion.setOrthogonalHeight(length);
            }

            double xAxisAbscissa = rs.getDouble("xaxisabscissa");
            if (!rs.wasNull())
                ifcMapConversion.setXAxisAbscissa(xAxisAbscissa);

            double xAxisOrdinate = rs.getDouble("xaxisordinate");
            if (!rs.wasNull())
                ifcMapConversion.setXAxisOrdinate(xAxisOrdinate);

            double scale = rs.getDouble("scale");
            if (!rs.wasNull())
                ifcMapConversion.setScale(scale);
        }

        if (ifcAttribute instanceof IfcClassificationReference) {
            IfcClassificationReference ifcClassificationReference = (IfcClassificationReference) ifcAttribute;

            ifcClassificationReference.setLocation(rs.getString("location"));

            String itemReference = rs.getString("itemreference");
            if (!rs.wasNull()) {
                Code code = new Code(itemReference);
                code.setCodeSpace(rs.getString("itemreference_codespace"));
                ifcClassificationReference.setItemReference(code);
            }

            ifcClassificationReference.setName(rs.getString("ifcclassificationreference_name"));

            long referencedSourceId = rs.getLong("referencedsource_id");
            if (referencedSourceId != 0) {
                IfcClassification referencedSource = new IfcClassification();
                ifcClassificationExporter.doExport(referencedSource, referencedSourceId);
                ifcClassificationReference.setReferencedSource(new IfcClassificationProperty(referencedSource));
            }
        }

        if (ifcAttribute instanceof IfcPsetSpaceCommon) {
            IfcPsetSpaceCommon ifcPsetSpaceCommon = (IfcPsetSpaceCommon) ifcAttribute;

            ifcPsetSpaceCommon.setReference(rs.getString("ifcpsetspacecommon_reference"));

            ifcPsetSpaceCommon.setCategory(rs.getString("category"));

            ifcPsetSpaceCommon.setFloorCovering(rs.getString("floorcovering"));

            ifcPsetSpaceCommon.setWallCovering(rs.getString("wallcovering"));

            ifcPsetSpaceCommon.setCeilingCovering(rs.getString("ceilingcovering"));

            ifcPsetSpaceCommon.setSkirtingBoard(rs.getString("skirtingboard"));

            double grossPlannedArea = rs.getDouble("ifcpsetspacecommon_grossplannedarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossPlannedArea);
                measure.setUom(rs.getString("ifcpsetspacecommon_grossplannedarea_uom"));
                ifcPsetSpaceCommon.setGrossPlannedArea(measure);
            }

            double netPlannedArea = rs.getDouble("netplannedarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netPlannedArea);
                measure.setUom(rs.getString("netplannedarea_uom"));
                ifcPsetSpaceCommon.setNetPlannedArea(measure);
            }

            boolean publiclyAccessible = rs.getBoolean("publiclyaccessible");
            if (!rs.wasNull())
                ifcPsetSpaceCommon.setPubliclyAccessible(publiclyAccessible);

            boolean handicapAccessible = rs.getBoolean("ifcpsetspacecommon_handicapaccessible");
            if (!rs.wasNull())
                ifcPsetSpaceCommon.setHandicapAccessible(handicapAccessible);

            boolean concealedFlooring = rs.getBoolean("concealedflooring");
            if (!rs.wasNull())
                ifcPsetSpaceCommon.setConcealedFlooring(concealedFlooring);

            boolean concealedCeiling = rs.getBoolean("concealedceiling");
            if (!rs.wasNull())
                ifcPsetSpaceCommon.setConcealedCeiling(concealedCeiling);
        }

        if (ifcAttribute instanceof IfcSpaceBaseQuantity) {
            IfcSpaceBaseQuantity ifcSpaceBaseQuantity = (IfcSpaceBaseQuantity) ifcAttribute;

            double nominalHeight = rs.getDouble("ifcspacebasequantity_nominalheight");
            if (!rs.wasNull()) {
                Length length = new Length(nominalHeight);
                length.setUom(rs.getString("ifcspacebasequantity_nominalheight_uom"));
                ifcSpaceBaseQuantity.setNominalHeight(length);
            }

            double clearHeight = rs.getDouble("clearheight");
            if (!rs.wasNull()) {
                Length length = new Length(clearHeight);
                length.setUom(rs.getString("clearheight_uom"));
                ifcSpaceBaseQuantity.setClearHeight(length);
            }

            double finishCeilingHeight = rs.getDouble("finishceilingheight");
            if (!rs.wasNull()) {
                Length length = new Length(finishCeilingHeight);
                length.setUom(rs.getString("finishceilingheight_uom"));
                ifcSpaceBaseQuantity.setFinishCeilingHeight(length);
            }

            double grossPerimeter = rs.getDouble("grossperimeter");
            if (!rs.wasNull()) {
                Length length = new Length(grossPerimeter);
                length.setUom(rs.getString("grossperimeter_uom"));
                ifcSpaceBaseQuantity.setGrossPerimeter(length);
            }

            double netPerimeter = rs.getDouble("netperimeter");
            if (!rs.wasNull()) {
                Length length = new Length(netPerimeter);
                length.setUom(rs.getString("netperimeter_uom"));
                ifcSpaceBaseQuantity.setNetPerimeter(length);
            }

            double grossCeilingArea = rs.getDouble("grossceilingarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossCeilingArea);
                measure.setUom(rs.getString("grossceilingarea_uom"));
                ifcSpaceBaseQuantity.setGrossCeilingArea(measure);
            }

            double grossFloorArea = rs.getDouble("grossfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossFloorArea);
                measure.setUom(rs.getString("grossfloorarea_uom"));
                ifcSpaceBaseQuantity.setGrossFloorArea(measure);
            }

            double netCeilingArea = rs.getDouble("netceilingarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netCeilingArea);
                measure.setUom(rs.getString("netceilingarea_uom"));
                ifcSpaceBaseQuantity.setNetCeilingArea(measure);
            }

            double netFloorArea = rs.getDouble("netfloorarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netFloorArea);
                measure.setUom(rs.getString("netfloorarea_uom"));
                ifcSpaceBaseQuantity.setNetFloorArea(measure);
            }

            double grossWallArea = rs.getDouble("grosswallarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossWallArea);
                measure.setUom(rs.getString("grosswallarea_uom"));
                ifcSpaceBaseQuantity.setGrossWallArea(measure);
            }

            double netWallArea = rs.getDouble("netwallarea");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netWallArea);
                measure.setUom(rs.getString("netwallarea_uom"));
                ifcSpaceBaseQuantity.setNetWallArea(measure);
            }

            double grossVolume = rs.getDouble("ifcspacebasequantity_grossvolume");
            if (!rs.wasNull()) {
                Measure measure = new Measure(grossVolume);
                measure.setUom(rs.getString("ifcspacebasequantity_grossvolume_uom"));
                ifcSpaceBaseQuantity.setGrossVolume(measure);
            }

            double netVolume = rs.getDouble("ifcspacebasequantity_netvolume");
            if (!rs.wasNull()) {
                Measure measure = new Measure(netVolume);
                measure.setUom(rs.getString("ifcspacebasequantity_netvolume_uom"));
                ifcSpaceBaseQuantity.setNetVolume(measure);
            }
        }

        if (ifcAttribute instanceof IfcPsetWindowCommon) {
            IfcPsetWindowCommon ifcPsetWindowCommon = (IfcPsetWindowCommon) ifcAttribute;

            ifcPsetWindowCommon.setReference(rs.getString("ifcpsetwindowcommon_reference"));

            ifcPsetWindowCommon.setAcousticRating(rs.getString("ifcpsetwindowcommon_acousticrating"));

            ifcPsetWindowCommon.setFireRating(rs.getString("ifcpsetwindowcommon_firerating"));

            ifcPsetWindowCommon.setSecurityRating(rs.getString("ifcpsetwindowcommon_securityrating"));

            boolean isExternal = rs.getBoolean("ifcpsetwindowcommon_isexternal");
            if (!rs.wasNull())
                ifcPsetWindowCommon.setIsExternal(isExternal);

            double infiltration = rs.getDouble("ifcpsetwindowcommon_infiltration");
            if (!rs.wasNull())
                ifcPsetWindowCommon.setInfiltration(infiltration);

            double thermalTransmittance = rs.getDouble("ifcpsetwindowcommon_thermaltransmittance");
            if (!rs.wasNull())
                ifcPsetWindowCommon.setThermalTransmittance(thermalTransmittance);

            double glazingAreaFraction = rs.getDouble("ifcpsetwindowcommon_glazingareafraction");
            if (!rs.wasNull())
                ifcPsetWindowCommon.setGlazingAreaFraction(glazingAreaFraction);

            boolean smokeStop = rs.getBoolean("ifcpsetwindowcommon_smokestop");
            if (!rs.wasNull())
                ifcPsetWindowCommon.setSmokeStop(smokeStop);
        }

        if (ifcAttribute instanceof IfcPsetDoorCommon) {
            IfcPsetDoorCommon ifcPsetDoorCommon = (IfcPsetDoorCommon) ifcAttribute;

            ifcPsetDoorCommon.setReference(rs.getString("ifcpsetdoorcommon_reference"));

            ifcPsetDoorCommon.setAcousticRating(rs.getString("ifcpsetdoorcommon_acousticrating"));

            ifcPsetDoorCommon.setFirerating(rs.getString("ifcpsetdoorcommon_firerating"));

            ifcPsetDoorCommon.setSecurityRating(rs.getString("ifcpsetdoorcommon_securityrating"));

            boolean isExternal = rs.getBoolean("ifcpsetdoorcommon_isexternal");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setIsExternal(isExternal);

            double infiltration = rs.getDouble("ifcpsetdoorcommon_infiltration");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setInfiltration(infiltration);

            double thermalTransmittance = rs.getDouble("ifcpsetdoorcommon_thermaltransmittance");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setThermalTransmittance(thermalTransmittance);

            double glazingAreaFraction = rs.getDouble("ifcpsetdoorcommon_glazingareafraction");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setGlazingAreaFraction(glazingAreaFraction);

            boolean handicapAccessible = rs.getBoolean("ifcpsetdoorcommon_handicapaccessible");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setHandicapAccessible(handicapAccessible);

            boolean fireExit = rs.getBoolean("ifcpsetdoorcommon_fireexit");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setFireExit(fireExit);

            boolean selfClosing = rs.getBoolean("selfclosing");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setSelfClosing(selfClosing);

            boolean smokeStop = rs.getBoolean("ifcpsetdoorcommon_smokestop");
            if (!rs.wasNull())
                ifcPsetDoorCommon.setSmokeStop(smokeStop);
        }

        if (ifcAttribute instanceof IfcPsetOpeningElementCommon) {
            IfcPsetOpeningElementCommon ifcPsetOpeningElementCommon = (IfcPsetOpeningElementCommon) ifcAttribute;

            ifcPsetOpeningElementCommon.setReference(rs.getString("ifcpsetopeningelementcommon_reference"));

            ifcPsetOpeningElementCommon.setPurpose(rs.getString("purpose"));

            boolean fireExit = rs.getBoolean("ifcpsetopeningelementcommon_fireexit");
            if (!rs.wasNull())
                ifcPsetOpeningElementCommon.setFireExit(fireExit);

            boolean protectedOpening = rs.getBoolean("protectedopening");
            if (!rs.wasNull())
                ifcPsetOpeningElementCommon.setProtectedOpening(protectedOpening);

            boolean parallelJambs = rs.getBoolean("paralleljambs");
            if (!rs.wasNull())
                ifcPsetOpeningElementCommon.setParallelJambs(parallelJambs);
        }
        return ifcAttribute;
    }

    private IfcCoordinateReferenceSystemSelectExporter ifcCoordinateReferenceSystemSelectExporter() throws CityGMLExportException, SQLException {
        if (ifcCoordinateReferenceSystemSelectExporter == null)
            ifcCoordinateReferenceSystemSelectExporter = manager.getExporter(IfcCoordinateReferenceSystemSelectExporter.class);

        return ifcCoordinateReferenceSystemSelectExporter;
    }

    private IfcAttributeExporter ifcAttributeExporter() throws CityGMLExportException, SQLException {
        if (ifcAttributeExporter == null)
            ifcAttributeExporter = manager.getExporter(IfcAttributeExporter.class);

        return ifcAttributeExporter;
    }

    @Override
    public void close() throws CityGMLExportException, SQLException {
        ps.close();
    }
}
