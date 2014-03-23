package com.intellisoftkenya.adt.migrator.business;

import com.intellisoftkenya.adt.migrator.data.Column;
import com.intellisoftkenya.adt.migrator.data.OneToOne;
import com.intellisoftkenya.adt.migrator.data.Reference;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Prepares {@link OneToOne} tables for migration. This class ensures that
 * {@link OneToOneMigrator} stays clean by not having to define migration table
 * properties.
 *
 * @author gitahi
 */
public class TableKitchen {

    /**
     * Prepare {@link OneToOne} tables for migration.
     *
     * @return a list of the tables prepared.
     */
    public List<OneToOne> prepareTables() {
        List<OneToOne> oneToOneTables = new ArrayList<OneToOne>();
        oneToOneTables.add(prepareDosage());
        oneToOneTables.add(prepareSupportingOrganization());
        oneToOneTables.add(prepareGenericName());
        oneToOneTables.add(prepareRegimenChangeReason());
        oneToOneTables.add(prepareRegimenType());
        oneToOneTables.add(preparePatientSource());
        oneToOneTables.add(prepareServiceType());
        oneToOneTables.add(prepareDispensingUnit());
        oneToOneTables.add(prepareVisitType());
        oneToOneTables.add(prepareDrug());
        return oneToOneTables;
    }

    private OneToOne prepareDosage() {
        OneToOne oto = new OneToOne("tblDose", "dosage");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("dose", Types.VARCHAR), new Column("name", Types.VARCHAR));
        columnMappings.put(
                new Column("value", Types.DECIMAL), new Column("value", Types.DECIMAL));
        columnMappings.put(
                new Column("frequency", Types.INTEGER), new Column("frequency", Types.INTEGER));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareSupportingOrganization() {
        OneToOne oto = new OneToOne("tblClientSupportDetails", "supporting_organization");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("ClientSupportDesciption", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareGenericName() {
        OneToOne oto = new OneToOne("tblGenericName", "generic_name");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("GenID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("GenericName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareRegimenChangeReason() {
        OneToOne oto = new OneToOne("tblReasonforChange", "regimen_change_reason");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("ReasonforChange", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareRegimenType() {
        OneToOne oto = new OneToOne("tblRegimenCategory", "regimen_type");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("CategoryName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne preparePatientSource() {
        OneToOne oto = new OneToOne("tblSourceOfClient", "patient_source");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("SourceOfClient", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareServiceType() {
        OneToOne oto = new OneToOne("tblTypeOfService", "service_type");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("TypeofService", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareDispensingUnit() {
        OneToOne oto = new OneToOne("tblUnit", "dispensing_unit");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("Unit", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareVisitType() {
        OneToOne oto = new OneToOne("tblVisitTransaction", "visit_type");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("VisitTranName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareDrug() {
        OneToOne oto = new OneToOne("tblARVDrugStockMain", "drug");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("ARVDrugsID", Types.VARCHAR), new Column("name", Types.VARCHAR));
        columnMappings.put(new Column("StdDuration", Types.INTEGER), new Column("duration", Types.INTEGER));   
        columnMappings.put(new Column("StdQty", Types.INTEGER), new Column("quantity", Types.DECIMAL));
        columnMappings.put(new Column("Packsizes", Types.INTEGER), new Column("pack_size", Types.INTEGER));
        columnMappings.put(new Column("ReorderLevel", Types.INTEGER), new Column("reorder_point", Types.INTEGER));

        Column category = new Column("drug_category_id", Types.INTEGER);
        category.setReference(new Reference("drug_category", true));
        columnMappings.put(new Column("DrugCategory", Types.INTEGER), category);

        Column unit = new Column("dispensing_unit_id", Types.INTEGER);
        unit.setReference(new Reference("dispensing_unit"));
        columnMappings.put(new Column("Unit", Types.VARCHAR), unit);

        Column genericName = new Column("generic_name_id", Types.INTEGER);
        genericName.setReference(new Reference("generic_name", "legacy_pk"));
        columnMappings.put(new Column("GenericName", Types.VARCHAR), genericName);

        Column dosage = new Column("dosage_id", Types.INTEGER);
        dosage.setReference(new Reference("dosage", true));
        columnMappings.put(new Column("StdDose", Types.VARCHAR), dosage);

        oto.setColumnMappings(columnMappings);
        return oto;
    }
}
