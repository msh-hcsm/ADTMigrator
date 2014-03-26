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

    public static final Integer ART_IDENTIFIERTYPE_ID = 1;

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
        oneToOneTables.add(prepareIndication());
        oneToOneTables.add(prepareRegimenChangeReason());
        oneToOneTables.add(prepareRegimenType());
        oneToOneTables.add(preparePatientSource());
        oneToOneTables.add(prepareServiceType());
        oneToOneTables.add(prepareDispensingUnit());
        oneToOneTables.add(prepareVisitType());
        oneToOneTables.add(prepareDrug());
        oneToOneTables.add(preparePerson());
        oneToOneTables.add(preparePatient());
        oneToOneTables.add(preparePatientIdentifier());
        oneToOneTables.add(prepareVisit());
        oneToOneTables.add(preparePersonAddress());
        oneToOneTables.add(prepareTransaction());
        oneToOneTables.add(prepareTransactionItem());
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
                new Column("ClientSupportID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
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

    private OneToOne prepareIndication() {
        OneToOne oto = new OneToOne("tblIndication", "indication");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("indicationCode", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(
                new Column("IndicationName", Types.VARCHAR), new Column("name", Types.VARCHAR));
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
                new Column("SourceID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("SourceOfClient", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareServiceType() {
        OneToOne oto = new OneToOne("tblTypeOfService", "service_type");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();
        columnMappings.put(
                new Column("TypeOfServiceID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
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

    private OneToOne preparePerson() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "person");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Firstname", Types.VARCHAR), new Column("first_name", Types.VARCHAR));
        columnMappings.put(new Column("Surname", Types.VARCHAR), new Column("surname", Types.VARCHAR));
        columnMappings.put(new Column("Sex", Types.VARCHAR), new Column("sex", Types.VARCHAR));
        columnMappings.put(new Column("DateofBirth", Types.DATE), new Column("date_of_birth", Types.DATE));

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne preparePatient() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "patient");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("DateStartedonART", Types.VARCHAR), new Column("date_of_enrollment", Types.VARCHAR));

        Column patientId = new Column("person_id", Types.INTEGER);
        patientId.setReference(new Reference("person", "legacy_pk"));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        Column patientSource = new Column("patient_source_id", Types.INTEGER);
        patientSource.setReference(new Reference("patient_source", "legacy_pk"));
        columnMappings.put(new Column("SourceofClient", Types.VARCHAR), patientSource);

        Column serviceType = new Column("service_type_id", Types.INTEGER);
        serviceType.setReference(new Reference("service_type", "legacy_pk"));
        columnMappings.put(new Column("TypeOfService", Types.VARCHAR), serviceType);

        Column supportingOrganization = new Column("supporting_organization_id", Types.INTEGER);
        supportingOrganization.setReference(new Reference("supporting_organization", "legacy_pk"));
        columnMappings.put(new Column("ClientSupportedBy", Types.VARCHAR), supportingOrganization);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne preparePatientIdentifier() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "patient_identifier");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("identifier", Types.VARCHAR));
        columnMappings.put(new Column(null, Types.INTEGER), new Column("identifier_type_id", Types.INTEGER, ART_IDENTIFIERTYPE_ID));

        Column patientId = new Column("patient_id", Types.INTEGER);
        patientId.setReference(new Reference("patient", "legacy_pk"));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareVisit() {
        OneToOne oto = new OneToOne("tblARTPatientTransactions", "visit");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("PatientTranNo", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(new Column("DateofVisit", Types.DATE), new Column("start_date", Types.DATE));
        columnMappings.put(new Column("Weight", Types.DECIMAL), new Column("weight", Types.DECIMAL));
        columnMappings.put(new Column("pillCount", Types.INTEGER), new Column("pill_count", Types.INTEGER));
        columnMappings.put(new Column("Adherence", Types.DECIMAL), new Column("adherence", Types.DECIMAL));
        columnMappings.put(new Column("Comment", Types.VARCHAR), new Column("comments", Types.VARCHAR));

//        columnMappings.put(new Column("Regimen", Types.VARCHAR), new Column("regimen_id", Types.INTEGER));
//
        Column indication = new Column("indication_id", Types.INTEGER);
        indication.setReference(new Reference("indication", "legacy_pk"));
        columnMappings.put(new Column("Indication", Types.VARCHAR), indication);

        Column regimenChangeReason = new Column("regimen_change_reason_id", Types.INTEGER);
        regimenChangeReason.setReference(new Reference("regimen_change_reason", "name"));
        columnMappings.put(new Column("ReasonsForChange", Types.VARCHAR), regimenChangeReason);

        Column patientId = new Column("patient_id", Types.INTEGER);
        patientId.setReference(new Reference("patient", "legacy_pk"));
        columnMappings.put(new Column("ARTID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne preparePersonAddress() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "person_address");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Address", Types.VARCHAR), new Column("physical_address", Types.VARCHAR));

        Column patientId = new Column("person_id", Types.INTEGER);
        patientId.setReference(new Reference("person", "legacy_pk"));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareTransaction() {
        OneToOne oto = new OneToOne("tblARVDrugStockTransactions", "transaction");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        Column drugId = new Column("drug_id", Types.INTEGER);
        drugId.setReference(new Reference("drug", "name"));
        columnMappings.put(new Column("ARVDrugsID", Types.VARCHAR), drugId);

        columnMappings.put(new Column("StockTranNo", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(new Column("RefOrderNo", Types.INTEGER), new Column("reference_no", Types.VARCHAR));
        columnMappings.put(new Column("TranDate", Types.DATE), new Column("date", Types.DATE));
        columnMappings.put(new Column("Remarks", Types.VARCHAR), new Column("narrative", Types.VARCHAR));

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareTransactionItem() {
        OneToOne oto = new OneToOne("tblARVDrugStockTransactions", "transaction_item");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        Column transactionId = new Column("transaction_id", Types.INTEGER);
        transactionId.setReference(new Reference("transaction", "legacy_pk"));
        columnMappings.put(new Column("StockTranNo", Types.INTEGER), transactionId);

        columnMappings.put(new Column("StockTranNo", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(new Column("BatchNo", Types.VARCHAR), new Column("batch_no", Types.VARCHAR));
        columnMappings.put(new Column("Qty", Types.DATE), new Column("units_in", Types.DECIMAL));
        columnMappings.put(new Column("Qty", Types.VARCHAR), new Column("units_out", Types.DECIMAL));

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne prepareBatch() {
        OneToOne oto = new OneToOne("tblARVDrugStockTransactions", "batch");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("StockTranNo", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("BatchNo", Types.INTEGER), new Column("batch_no", Types.VARCHAR));
        columnMappings.put(new Column("Npacks", Types.INTEGER), new Column("no_of_packs", Types.INTEGER));
        columnMappings.put(new Column("PackSize", Types.INTEGER), new Column("pack_size", Types.INTEGER));
        columnMappings.put(new Column("TranDate", Types.DATE), new Column("date_of_receipt", Types.DATE));
        columnMappings.put(new Column("Expirydate", Types.DATE), new Column("date_of_expiry", Types.DATE));

        Column patientId = new Column("transaction_id", Types.INTEGER);
        patientId.setReference(new Reference("transaction", "legacy_pk"));
        columnMappings.put(new Column("StockTranNo", Types.VARCHAR), patientId);

        Column drugId = new Column("drug_id", Types.INTEGER);
        drugId.setReference(new Reference("drug", "name"));
        columnMappings.put(new Column("ARVDrugsID", Types.VARCHAR), drugId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne preparePatientTransactionItem() {
        OneToOne oto = new OneToOne("tblARVDrugStockTransactions", "batch");
        Map<Column, Column> columnMappings = new LinkedHashMap<Column, Column>();

        columnMappings.put(new Column("PatientTranNo", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(new Column("BatchNo", Types.INTEGER), new Column("transaction_item_id", Types.INTEGER));
        columnMappings.put(new Column("Npacks", Types.INTEGER), new Column("dosage_id", Types.INTEGER));
        columnMappings.put(new Column("PackSize", Types.INTEGER), new Column("quantity", Types.DECIMAL));
        columnMappings.put(new Column("TranDate", Types.DATE), new Column("duration", Types.INTEGER));
        columnMappings.put(new Column("Expirydate", Types.DATE), new Column("duration_id", Types.INTEGER));

        Column patientId = new Column("transaction_item_id", Types.INTEGER);
        patientId.setReference(new Reference("transaction_item", "legacy_pk"));
        columnMappings.put(new Column("PatientTranNo", Types.VARCHAR), patientId);

        Column drugId = new Column("drug_id", Types.INTEGER);
        drugId.setReference(new Reference("drug", "name"));
        columnMappings.put(new Column("ARVDrugsID", Types.VARCHAR), drugId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }
}
