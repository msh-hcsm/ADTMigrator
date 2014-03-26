package com.intellisoftkenya.onetooner.business;

import com.intellisoftkenya.onetooner.data.Column;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Reference;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Prepares {@link OneToOne} tables for migration. This class ensures that
 * {@link OneToOneMigrator} stays clean by not having to define migration table
 * properties. Think of it as a configuration class. In future this content can
 * be migrated to an out-of-code configuration file.
 *
 * @author gitahi
 */
public class TableConfigurator {

    public static final Integer ART_IDENTIFIERTYPE_ID = 1;

    /**
     * Configure {@link OneToOne} tables for migration.
     *
     * @return a list of the tables configured.
     */
    public List<OneToOne> configureTables() {
        List<OneToOne> oneToOneTables = new ArrayList<>();
        oneToOneTables.add(configureDosage());
        oneToOneTables.add(configureSupportingOrganization());
        oneToOneTables.add(configureAccount());
        oneToOneTables.add(configureGenericName());
        oneToOneTables.add(configureIndication());
        oneToOneTables.add(configureRegimenChangeReason());
        oneToOneTables.add(configureRegimenType());
        oneToOneTables.add(configurePatientSource());
        oneToOneTables.add(configureServiceType());
        oneToOneTables.add(configureDispensingUnit());
        oneToOneTables.add(configureVisitType());
        oneToOneTables.add(configureDrug());
        oneToOneTables.add(configurePerson());
        oneToOneTables.add(configurePatient());
        oneToOneTables.add(configurePatientIdentifier());
        oneToOneTables.add(configureVisit());
        oneToOneTables.add(configurePersonAddress());
        oneToOneTables.add(configureTransaction());
        oneToOneTables.add(configureTransactionItem());
        oneToOneTables.add(configurePatientTransactionItem());
        oneToOneTables.add(configureBatchTransactionItem());
        return oneToOneTables;
    }

    private OneToOne configureDosage() {
        OneToOne oto = new OneToOne("tblDose", "dosage");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("dose", Types.VARCHAR), new Column("name", Types.VARCHAR));
        columnMappings.put(
                new Column("value", Types.DECIMAL), new Column("value", Types.DECIMAL));
        columnMappings.put(
                new Column("frequency", Types.INTEGER), new Column("frequency", Types.INTEGER));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureSupportingOrganization() {
        OneToOne oto = new OneToOne("tblClientSupportDetails", "supporting_organization");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("ClientSupportID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("ClientSupportDesciption", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureAccount() {
        OneToOne oto = new OneToOne("tblARVStockTranSourceorDestination", "account");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("SDNo", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("SourceorDestination", Types.VARCHAR), new Column("name", Types.VARCHAR));

        Column accountTypeId = new Column("account_type_id", Types.INTEGER);
        accountTypeId.setReference(new Reference("account_type", true, new AccountTypeReferenceProcessor()));
        columnMappings.put(new Column("SourceorDestination", Types.VARCHAR), accountTypeId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureGenericName() {
        OneToOne oto = new OneToOne("tblGenericName", "generic_name");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("GenID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("GenericName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureIndication() {
        OneToOne oto = new OneToOne("tblIndication", "indication");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("indicationCode", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(
                new Column("IndicationName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureRegimenChangeReason() {
        OneToOne oto = new OneToOne("tblReasonforChange", "regimen_change_reason");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("ReasonforChange", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureRegimenType() {
        OneToOne oto = new OneToOne("tblRegimenCategory", "regimen_type");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("CategoryName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePatientSource() {
        OneToOne oto = new OneToOne("tblSourceOfClient", "patient_source");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("SourceID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("SourceOfClient", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureServiceType() {
        OneToOne oto = new OneToOne("tblTypeOfService", "service_type");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("TypeOfServiceID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("TypeofService", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureDispensingUnit() {
        OneToOne oto = new OneToOne("tblUnit", "dispensing_unit");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("Unit", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureVisitType() {
        OneToOne oto = new OneToOne("tblVisitTransaction", "visit_type");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("VisitTranName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureDrug() {
        OneToOne oto = new OneToOne("tblARVDrugStockMain", "drug");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

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

    private OneToOne configurePerson() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "person");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Firstname", Types.VARCHAR), new Column("first_name", Types.VARCHAR));
        columnMappings.put(new Column("Surname", Types.VARCHAR), new Column("surname", Types.VARCHAR));
        columnMappings.put(new Column("Sex", Types.VARCHAR), new Column("sex", Types.VARCHAR));
        columnMappings.put(new Column("DateofBirth", Types.DATE), new Column("date_of_birth", Types.DATE));

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePatient() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "patient");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

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

    private OneToOne configurePatientIdentifier() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "patient_identifier");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("identifier", Types.VARCHAR));
        columnMappings.put(new Column(null, Types.INTEGER), new Column("identifier_type_id", Types.INTEGER, ART_IDENTIFIERTYPE_ID));

        Column patientId = new Column("patient_id", Types.INTEGER);
        patientId.setReference(new Reference("patient", "legacy_pk"));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureVisit() {
        OneToOne oto = new OneToOne("tblARTPatientTransactions", "visit");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("PatientTranNo_", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(new Column("DateofVisit", Types.DATE), new Column("start_date", Types.DATE));
        columnMappings.put(new Column("Weight_", Types.DECIMAL), new Column("weight", Types.DECIMAL));
        columnMappings.put(new Column("pillCount_", Types.INTEGER), new Column("pill_count", Types.INTEGER));
        columnMappings.put(new Column("Adherence_", Types.DECIMAL), new Column("adherence", Types.DECIMAL));
        columnMappings.put(new Column("Comment_", Types.VARCHAR), new Column("comments", Types.VARCHAR));

//        columnMappings.put(new Column("Regimen", Types.VARCHAR), new Column("regimen_id", Types.INTEGER));
//
        Column indication = new Column("indication_id", Types.INTEGER);
        indication.setReference(new Reference("indication", "legacy_pk"));
        columnMappings.put(new Column("Indication_", Types.VARCHAR), indication);

        Column regimenChangeReason = new Column("regimen_change_reason_id", Types.INTEGER);
        regimenChangeReason.setReference(new Reference("regimen_change_reason", "name"));
        columnMappings.put(new Column("ReasonsForChange_", Types.VARCHAR), regimenChangeReason);

        Column patientId = new Column("patient_id", Types.INTEGER);
        patientId.setReference(new Reference("patient", "legacy_pk"));
        columnMappings.put(new Column("ARTID", Types.VARCHAR), patientId);

        oto.setQuery("SELECT "
                + "MIN(PatientTranNo) AS PatientTranNo_, "
                + "DateofVisit, "
                + "MIN(Weight) AS Weight_, "
                + "MIN(pillCount) AS pillCount_, "
                + "MIN(Adherence) AS Adherence_, "
                + "MIN(Comment) AS Comment_,"
                + " MIN(Indication) AS Indication_, "
                + "MIN(ReasonsForChange) AS ReasonsForChange_, "
                + "ARTID "
                + "FROM "
                + "tblARTPatientTransactions "
                + "GROUP BY "
                + "DateofVisit, ARTID");
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePersonAddress() {
        OneToOne oto = new OneToOne("tblARTPatientMasterInformation", "person_address");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Address", Types.VARCHAR), new Column("physical_address", Types.VARCHAR));

        Column patientId = new Column("person_id", Types.INTEGER);
        patientId.setReference(new Reference("person", "legacy_pk"));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureTransaction() {
        OneToOne oto = new OneToOne("tblARVDrugStockTransactions", "transaction");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

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

    private OneToOne configureTransactionItem() {
        OneToOne oto = new OneToOne("tblARVDrugStockTransactions", "transaction_item");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        Column transactionId = new Column("transaction_id", Types.INTEGER);
        transactionId.setReference(new Reference("transaction", "legacy_pk"));
        columnMappings.put(new Column("StockTranNo", Types.INTEGER), transactionId);

        Column account_id = new Column("account_id", Types.INTEGER);
        account_id.setReference(new Reference("account", "name"));
        columnMappings.put(new Column("SourceorDestination", Types.VARCHAR), account_id);

        columnMappings.put(new Column("StockTranNo", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(new Column("BatchNo", Types.VARCHAR), new Column("batch_no", Types.VARCHAR));
        columnMappings.put(new Column("Qty", Types.DATE), new Column("units_in", Types.DECIMAL));
        columnMappings.put(new Column("Qty", Types.VARCHAR), new Column("units_out", Types.DECIMAL));

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureBatchTransactionItem() {
        OneToOne oto = new OneToOne("tblARVDrugStockTransactions", "batch_transaction_item");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("StockTranNo", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Npacks", Types.INTEGER), new Column("no_of_packs", Types.INTEGER));
        columnMappings.put(new Column("PackSize", Types.INTEGER), new Column("pack_size", Types.INTEGER));
        columnMappings.put(new Column("Expirydate", Types.DATE), new Column("date_of_expiry", Types.DATE));

        Column patientId = new Column("transaction_item_id", Types.INTEGER);
        patientId.setReference(new Reference("transaction_item", "legacy_pk"));
        columnMappings.put(new Column("StockTranNo", Types.VARCHAR), patientId);

        oto.setQuery("SELECT StockTranNo, Npacks, PackSize, Expirydate "
                + "FROM tblARVDrugStockTransactions "
                + "WHERE PackSize IS NOT NULL");
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePatientTransactionItem() {
        OneToOne oto = new OneToOne("tblARTPatientTransactions", "patient_transaction_item");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("PatientTranNo", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));

        columnMappings.put(new Column("duration", Types.DATE), new Column("duration", Types.INTEGER));

        Column transactionItemId = new Column("transaction_item_id", Types.INTEGER);
        transactionItemId.setReference(new Reference("transaction_item", "legacy_pk"));
        columnMappings.put(new Column("PatientTranNo", Types.VARCHAR), transactionItemId);

        Column dosage = new Column("dosage_id", Types.INTEGER);
        dosage.setReference(new Reference("dosage", true));
        columnMappings.put(new Column("Dose", Types.VARCHAR), dosage);

        Column visit = new Column("visit_id", Types.INTEGER);
        visit.setReference(new Reference("visit", "legacy_pk"));
        columnMappings.put(new Column("PatientTranNo", Types.VARCHAR), visit);

        oto.setColumnMappings(columnMappings);
        return oto;
    }
}
