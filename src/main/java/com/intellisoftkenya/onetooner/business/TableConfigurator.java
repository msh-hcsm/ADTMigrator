package com.intellisoftkenya.onetooner.business;

import com.intellisoftkenya.onetooner.api.imp.processor.Account99Includer;
import com.intellisoftkenya.onetooner.api.imp.processor.DhisIdMapper;
import com.intellisoftkenya.onetooner.api.imp.processor.DrugSupporterProcessor;
import com.intellisoftkenya.onetooner.api.imp.processor.DrugsInRegimenProcessor;
import com.intellisoftkenya.onetooner.api.imp.processor.IdentifierTypeCreator;
import com.intellisoftkenya.onetooner.api.imp.processor.LookupValuePkProcessor;
import com.intellisoftkenya.onetooner.api.imp.processor.MasterDrugListImporter;
import com.intellisoftkenya.onetooner.api.imp.processor.PatientServiceTypeProcessor;
import com.intellisoftkenya.onetooner.api.imp.processor.UnitsInOutUpdater;
import com.intellisoftkenya.onetooner.api.imp.processor.UnnecessaryARTServiceTypeRemover;
import com.intellisoftkenya.onetooner.api.imp.processor.VisitUpdater;
import com.intellisoftkenya.onetooner.api.imp.translator.AccountTypeValueTranslator;
import com.intellisoftkenya.onetooner.api.imp.translator.AccountValueInferrer;
import com.intellisoftkenya.onetooner.api.imp.translator.DrugCategoryValueTranslator;
import com.intellisoftkenya.onetooner.data.Column;
import com.intellisoftkenya.onetooner.data.LookupValue;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.data.ParameterizedQuery;
import com.intellisoftkenya.onetooner.data.Reference;
import com.intellisoftkenya.onetooner.data.Table;
import com.intellisoftkenya.onetooner.data.WhereCondition;
import java.sql.SQLException;
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

    /**
     * Configure {@link OneToOne} tables for migration.
     *
     * @return a list of the tables configured.
     *
     * @throws java.lang.Exception if anything goes wrong when configuring
     * tables.
     */
    public List<OneToOne> configureTables() throws Exception {
        List<OneToOne> oneToOneTables = new ArrayList<>();
        //look-up tables
        oneToOneTables.add(configurePatientStatus());
        oneToOneTables.add(configureAccount());
        oneToOneTables.add(configureDosage());
        oneToOneTables.add(configureGenericName());
        oneToOneTables.add(configureFacility());
        oneToOneTables.add(configureIndication());
        oneToOneTables.add(configureRegimenChangeReason());
        oneToOneTables.add(configureRegimenType());
        oneToOneTables.add(configureRegimen());
        oneToOneTables.add(configureRegion());
        oneToOneTables.add(configureDistrict());
        oneToOneTables.add(configureSupportingOrganization());
        oneToOneTables.add(configurePatientSource());
        oneToOneTables.add(configureServiceType());
        oneToOneTables.add(configureDispensingUnit());
        oneToOneTables.add(configureVisitType());
        oneToOneTables.add(configureTransactionType());
        //drugs
        oneToOneTables.add(configureDrug());

        //person data
        oneToOneTables.add(configurePerson());
        oneToOneTables.add(configurePersonAddress());

        //patient data
        oneToOneTables.add(configurePatient());
        oneToOneTables.add(configurePatientIdentifier_ArtId());
        oneToOneTables.add(configurePatientIdentifier_OpipdId());

        //visits
        oneToOneTables.add(configureVisit());

        //transactions
        oneToOneTables.add(configureTransaction_Stock());
        oneToOneTables.add(configureTransaction_Patient());
        oneToOneTables.add(configureTransactionItem_Stock());
        oneToOneTables.add(configureTransactionItem_Patient());
        oneToOneTables.add(configureBatchTransactionItem());
        oneToOneTables.add(configurePatientTransactionItem());
        return oneToOneTables;
    }

    private OneToOne configurePatientStatus() {
        OneToOne oto = new OneToOne(31, new Table("tblCurrentStatus", Table.orderBy("CurrentStatusID")),
                new Table("patient_status"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("CurrentStatusID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("CurrentStatus", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureAccount() {
        OneToOne oto = new OneToOne(23, new Table("tblARVStockTranSourceorDestination", Table.orderBy("SDNo")),
                new Table("account"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("SDNo_", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("SourceorDestination_", Types.VARCHAR), new Column("name", Types.VARCHAR));

        Column accountTypeId = new Column("account_type_id", Types.INTEGER);
        accountTypeId.setReference(new Reference("account_type", true, Reference.NullAction.THROW_EXCEPTION, new AccountTypeValueTranslator()));
        columnMappings.put(new Column("SourceorDestination_", Types.VARCHAR), accountTypeId);

        oto.setColumnMappings(columnMappings);
        oto.addPostProcessor(new Account99Includer());

        oto.setParameterizedQuery("SELECT MIN(SDNo) AS SDNo_, MIN(SourceorDestination) AS SourceorDestination_\n"
                + "FROM tblARVStockTranSourceorDestination WHERE SourceorDestination IS NOT NULL\n"
                + "GROUP BY SDNo\n"
                + "UNION\n"
                + "SELECT MIN(SCode) AS SCode_, MIN(Source) AS Source_ FROM\n"
                + "tblSource WHERE Source IS NOT NULL\n"
                + "GROUP BY SCode\n"
                + "UNION\n"
                + "SELECT MIN(DCode) AS DCode_, MIN(Destination) AS Destination_\n"
                + "FROM tblDestination WHERE Destination IS NOT NULL\n"
                + "GROUP BY Dcode");

        return oto;
    }

    private OneToOne configureDosage() {
        OneToOne oto = new OneToOne(27, new Table("tblDose", Table.orderBy("dose")),
                new Table("dosage"));
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

    private OneToOne configureGenericName() {
        OneToOne oto = new OneToOne(13, new Table("tblGenericName", Table.orderBy("GenID")),
                new Table("generic_name"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("GenID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("GenericName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureFacility() {
        OneToOne oto = new OneToOne(30, new Table("tblHealthFacilities", Table.orderBy("MFLCode")),
                new Table("facility"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("MFLCode", Types.INTEGER), new Column("code", Types.INTEGER));
        columnMappings.put(
                new Column("FacilityName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        columnMappings.put(
                new Column("District", Types.VARCHAR), new Column("district", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureIndication() {
        OneToOne oto = new OneToOne(26, new Table("tblIndication", Table.orderBy("indicationCode")),
                new Table("indication"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("indicationCode", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(
                new Column("IndicationName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureRegimenChangeReason() {
        OneToOne oto = new OneToOne(22, new Table("tblReasonforChange", Table.orderBy("ReasonForChangeID")),
                new Table("regimen_change_reason"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("ReasonForChangeID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("ReasonforChange", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureRegimenType() {
        OneToOne oto = new OneToOne(16, new Table("tblRegimenCategory", Table.orderBy("CategoryID")),
                new Table("regimen_type"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("CategoryID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("CategoryName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureRegimen() {
        OneToOne oto = new OneToOne(15, new Table("tblRegimen", Table.orderBy("Regimencode")),
                new Table("regimen"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("Regimencode", Types.VARCHAR), new Column("code", Types.VARCHAR));
        columnMappings.put(new Column("Regimen", Types.VARCHAR), new Column("name", Types.VARCHAR));
        columnMappings.put(new Column("Line", Types.INTEGER), new Column("line", Types.INTEGER));
        columnMappings.put(new Column("Remarks", Types.VARCHAR), new Column("comments", Types.VARCHAR));
        columnMappings.put(new Column("show", Types.BOOLEAN), new Column("visible", Types.BOOLEAN));

        Column serviceType = new Column("service_type_id", Types.INTEGER);
        serviceType.setReference(new Reference("service_type", "legacy_pk"));
        columnMappings.put(new Column("TypeoService", Types.INTEGER), serviceType);

        Column regimenType = new Column("regimen_type_id", Types.INTEGER);
        regimenType.setReference(new Reference("regimen_type", "legacy_pk"));
        columnMappings.put(new Column("Category", Types.INTEGER), regimenType);

        Column regimenStatus = new Column("regimen_status_id", Types.INTEGER);
        regimenStatus.setReference(new Reference("regimen_status", true));
        columnMappings.put(new Column("Status", Types.VARCHAR), regimenStatus);

        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureRegion() {
        OneToOne oto = new OneToOne(29, new Table("tblRegion", Table.orderBy("Rcode")),
                new Table("region"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("Rcode_", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("Region_", Types.VARCHAR), new Column("name", Types.VARCHAR));

        oto.setParameterizedQuery("SELECT MIN(Rcode) AS Rcode_, MIN(Region) AS Region_\n"
                + "FROM tblRegion GROUP BY Rcode ORDER BY Rcode");
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureDistrict() {
        OneToOne oto = new OneToOne(28, new Table("tblDistricts", Table.orderBy("DCode")),
                new Table("district"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("DCode", Types.INTEGER), new Column("code", Types.INTEGER));
        columnMappings.put(
                new Column("DistrictName", Types.VARCHAR), new Column("name", Types.VARCHAR));

        Column region = new Column("region_id", Types.INTEGER);
        region.setReference(new Reference("region", true));
        columnMappings.put(new Column("Region", Types.VARCHAR), region);

        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureSupportingOrganization() {
        OneToOne oto = new OneToOne(21, new Table("tblClientSupportDetails", Table.orderBy("ClientSupportID")),
                new Table("supporting_organization"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("ClientSupportID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("ClientSupportDesciption", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePatientSource() {
        OneToOne oto = new OneToOne(14, new Table("tblSourceOfClient", Table.orderBy("SourceID")),
                new Table("patient_source"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("SourceID", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("SourceOfClient", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureServiceType() {
        OneToOne oto = new OneToOne(18, new Table("tblTypeOfService", Table.orderBy("TypeOfServiceID")),
                new Table("service_type"));
        oto.setExplicitWhereConditions("WHERE id NOT IN (SELECT DISTINCT service_type_id FROM drug WHERE standard = 1)");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("TypeOfServiceID_", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("TypeofService_", Types.VARCHAR), new Column("name", Types.VARCHAR));

        oto.setParameterizedQuery("SELECT MIN(TypeOfServiceID) AS TypeOfServiceID_, MIN(TypeofService) AS TypeofService_\n"
                + "FROM tblTypeOfService GROUP BY TypeOfServiceID ORDER BY TypeOfServiceID");
        oto.setColumnMappings(columnMappings);
        oto.addPostProcessor(new UnnecessaryARTServiceTypeRemover());
        return oto;
    }

    private OneToOne configureDispensingUnit() {
        OneToOne oto = new OneToOne(19, new Table("tblUnit", Table.orderBy("Unit")),
                new Table("dispensing_unit"));
        oto.setExplicitWhereConditions("WHERE id NOT IN (SELECT DISTINCT dispensing_unit_id FROM drug WHERE standard = 1)");
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("Unit", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureVisitType() {
        OneToOne oto = new OneToOne(20, new Table("tblVisitTransaction", Table.orderBy("TransactionCode")),
                new Table("visit_type"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("TransactionCode", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("VisitTranName", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureTransactionType() {
        OneToOne oto = new OneToOne(10, new Table("tblStockTransactionType", Table.orderBy("TransactionType")),
                new Table("transaction_type"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();
        columnMappings.put(
                new Column("TransactionType", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(
                new Column("TransactionDescription", Types.VARCHAR), new Column("name", Types.VARCHAR));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureDrug() {
        OneToOne oto = new OneToOne(11, new Table("tblARVDrugStockMain", Table.orderBy("ARVDrugsID")),
                new Table("drug"));
        oto.addWhereCondition(new WhereCondition("standard =", false, Types.BOOLEAN));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ARVDrugsID", Types.VARCHAR), new Column("name", Types.VARCHAR));
        columnMappings.put(new Column("StdDuration", Types.INTEGER), new Column("duration", Types.INTEGER));
        columnMappings.put(new Column("StdQty", Types.INTEGER), new Column("quantity", Types.DECIMAL));
        columnMappings.put(new Column("Packsizes", Types.INTEGER), new Column("pack_size", Types.INTEGER));
        columnMappings.put(new Column("ReorderLevel", Types.INTEGER), new Column("reorder_point", Types.INTEGER));

        Column category = new Column("drug_category_id", Types.INTEGER);
        category.setReference(new Reference("drug_category", true, new DrugCategoryValueTranslator()));
        columnMappings.put(new Column("DrugCategory", Types.INTEGER), category);

        Column unit = new Column("dispensing_unit_id", Types.INTEGER);
        unit.setReference(new Reference("dispensing_unit"));
        columnMappings.put(new Column("Unit", Types.VARCHAR), unit);

        Column genericName = new Column("generic_name_id", Types.INTEGER);
        genericName.setReference(new Reference("generic_name", "legacy_pk"));
        columnMappings.put(new Column("GenericName", Types.VARCHAR), genericName);

        Column dosage = new Column("dosage_id", Types.INTEGER);
        dosage.setReference(new Reference("dosage"));
        columnMappings.put(new Column("StdDose", Types.VARCHAR), dosage);

        oto.setColumnMappings(columnMappings);
        oto.addPreProcessor(new MasterDrugListImporter());
        oto.addPostProcessor(new DrugSupporterProcessor());
        oto.addPostProcessor(new DrugsInRegimenProcessor());
        oto.addPostProcessor(new DhisIdMapper());

        oto.addPreUpdate("DELETE FROM `drug_supporting_organization`");
        oto.addPreUpdate("DELETE FROM drug_category WHERE id NOT IN (SELECT drug_category_id FROM drug)");
        oto.addPreUpdate("DELETE FROM `regimen_drug`");
        oto.addPreUpdate("UPDATE `drug` SET `dhis_id` = NULL");

        return oto;
    }

    private OneToOne configurePerson() {
        OneToOne oto = new OneToOne(5, new Table("tblARTPatientMasterInformation", Table.orderBy("ArtID")),
                new Table("person"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Firstname", Types.VARCHAR), new Column("first_name", Types.VARCHAR));
        columnMappings.put(new Column("Surname", Types.VARCHAR), new Column("surname", Types.VARCHAR));
        columnMappings.put(new Column("LastName", Types.VARCHAR), new Column("other_names", Types.VARCHAR));
        columnMappings.put(new Column("Sex", Types.VARCHAR), new Column("sex", Types.VARCHAR));
        columnMappings.put(new Column("DateofBirth", Types.DATE), new Column("date_of_birth", Types.DATE));

        Column birthDistrict = new Column("birth_district_id", Types.INTEGER);
        birthDistrict.setReference(new Reference("district", "code"));
        columnMappings.put(new Column("PlaceofBirth", Types.VARCHAR), birthDistrict);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePersonAddress() {
        OneToOne oto = new OneToOne(2, new Table("tblARTPatientMasterInformation", Table.orderBy("ArtID")),
                new Table("person_address"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Address", Types.VARCHAR), new Column("physical_address", Types.VARCHAR));
        columnMappings.put(new Column("PatientCellphone", Types.INTEGER), new Column("tel_no1", Types.VARCHAR));
        columnMappings.put(new Column("AlternateContact", Types.VARCHAR), new Column("tel_no2", Types.VARCHAR));

        Column patientId = new Column("person_id", Types.INTEGER);
        patientId.setReference(new Reference("person", "legacy_pk", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePatient() {
        OneToOne oto = new OneToOne(4, new Table("tblARTPatientMasterInformation", Table.orderBy("ArtID")),
                new Table("patient"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("DateTherapyStarted", Types.DATE), new Column("enrollment_date", Types.DATE));
        columnMappings.put(new Column("OtherDeaseConditions", Types.VARCHAR), new Column("chronic_illnesses", Types.VARCHAR));
        columnMappings.put(new Column("ADRorSideEffects", Types.VARCHAR), new Column("drug_allergies", Types.VARCHAR));
        columnMappings.put(new Column("PatientSmoke", Types.BOOLEAN), new Column("smoker", Types.BOOLEAN));
        columnMappings.put(new Column("PatientDrinkAlcohol", Types.BOOLEAN), new Column("drinker", Types.BOOLEAN));

        Column patientId = new Column("person_id", Types.INTEGER);
        patientId.setReference(new Reference("person", "legacy_pk", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        Column patientSource = new Column("patient_source_id", Types.INTEGER);
        patientSource.setReference(new Reference("patient_source", "legacy_pk"));
        columnMappings.put(new Column("SourceofClient", Types.VARCHAR), patientSource);

        Column supportingOrganization = new Column("supporting_organization_id", Types.INTEGER);
        supportingOrganization.setReference(new Reference("supporting_organization", "legacy_pk"));
        columnMappings.put(new Column("ClientSupportedBy", Types.VARCHAR), supportingOrganization);

        Column transferFromFacility = new Column("from_facility_id", Types.INTEGER);
        transferFromFacility.setReference(new Reference("facility", "code"));
        columnMappings.put(new Column("TransferFrom", Types.VARCHAR), transferFromFacility);

        oto.setColumnMappings(columnMappings);
        oto.addPostProcessor(new PatientServiceTypeProcessor());
        oto.addPreUpdate("DELETE FROM `patient_service_type`");
        return oto;
    }

    private OneToOne configurePatientIdentifier_ArtId() {
        OneToOne oto = new OneToOne(1, new Table("tblARTPatientMasterInformation", Table.orderBy("ArtID")),
                new Table("patient_identifier"));
        oto.addWhereCondition(new WhereCondition("identifier_type_id =", Constants.ART_IDENTIFIER_TYPE_ID, Types.INTEGER));

        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("ArtID", Types.VARCHAR), new Column("identifier", Types.VARCHAR));
        columnMappings.put(new Column(null, Types.INTEGER), new Column("identifier_type_id", Types.INTEGER, Constants.ART_IDENTIFIER_TYPE_ID));

        Column patientId = new Column("patient_id", Types.INTEGER);
        patientId.setReference(new Reference("patient", "legacy_pk", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        oto.addPreProcessor(new IdentifierTypeCreator());
        return oto;
    }

    private OneToOne configurePatientIdentifier_OpipdId() {
        OneToOne oto = new OneToOne(1, new Table("tblARTPatientMasterInformation", Table.orderBy("OPIPNO")),
                new Table("patient_identifier"));
        oto.addWhereCondition(new WhereCondition("identifier_type_id =", Constants.OPIP_IDENTIFIER_TYPE_ID, Types.INTEGER));

        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("OPIPNO", Types.VARCHAR), new Column("identifier", Types.VARCHAR));
        columnMappings.put(new Column(null, Types.INTEGER), new Column("identifier_type_id", Types.INTEGER, Constants.OPIP_IDENTIFIER_TYPE_ID));

        Column patientId = new Column("patient_id", Types.INTEGER);
        patientId.setReference(new Reference("patient", "legacy_pk", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("ArtID", Types.VARCHAR), patientId);

        oto.setColumnMappings(columnMappings);
        oto.setParameterizedQuery("SELECT ArtID, OPIPNO FROM tblARTPatientMasterInformation WHERE OPIPNO IS NOT NULL ORDER BY OPIPNO");
        oto.addPreProcessor(new IdentifierTypeCreator());
        return oto;
    }

    private OneToOne configureVisit() {
        OneToOne oto = new OneToOne(3, new Table("tblARTPatientTransactions", Table.orderBy("MIN(PatientTranNo)")),
                new Table("visit"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("PatientTranNo_", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));
        columnMappings.put(new Column("DateofVisit", Types.DATE), new Column("start_date", Types.DATE));
        columnMappings.put(new Column("DateofVisit", Types.DATE), new Column("end_date", Types.DATE));
        columnMappings.put(new Column("Weight_", Types.DECIMAL), new Column("weight", Types.DECIMAL));
        columnMappings.put(new Column("pillCount_", Types.INTEGER), new Column("pill_count", Types.INTEGER));
        columnMappings.put(new Column("Adherence_", Types.DECIMAL), new Column("adherence", Types.DECIMAL));
        columnMappings.put(new Column("Comment_", Types.VARCHAR), new Column("comments", Types.VARCHAR));

        Column visitType = new Column("visit_type_id", Types.INTEGER);
        visitType.setReference(new Reference("visit_type", "legacy_pk"));
        columnMappings.put(new Column("TransactionCode_", Types.INTEGER), visitType);

        Column regimen = new Column("regimen_id", Types.INTEGER);
        regimen.setReference(new Reference("regimen", "code"));
        columnMappings.put(new Column("Regimen_", Types.VARCHAR), regimen);

        Column regimenChangeReason = new Column("regimen_change_reason_id", Types.INTEGER);
        regimenChangeReason.setReference(new Reference("regimen_change_reason", "name"));
        columnMappings.put(new Column("ReasonsForChange_", Types.VARCHAR), regimenChangeReason);

        Column patientId = new Column("patient_id", Types.INTEGER);
        patientId.setReference(new Reference("patient", "legacy_pk", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("ARTID", Types.VARCHAR), patientId);

        oto.setParameterizedQuery("SELECT\n"
                + "MIN(PatientTranNo) AS PatientTranNo_,\n"
                + "DateofVisit,\n"
                + "MIN(TransactionCode) AS TransactionCode_,\n"
                + "MIN(Weight) AS Weight_,\n"
                + "MIN(pillCount) AS pillCount_,\n"
                + "MIN(Adherence) AS Adherence_,\n"
                + "MIN(Comment) AS Comment_,"
                + "MIN(Indication) AS Indication_,\n"
                + "MIN(Regimen) AS Regimen_,\n"
                + "MIN(ReasonsForChange) AS ReasonsForChange_,\n"
                + "ARTID\n"
                + "FROM\n"
                + "tblARTPatientTransactions\n"
                + "GROUP BY\n"
                + "DateofVisit, ARTID\n"
                + "ORDER BY MIN(PatientTranNo)");
        oto.setColumnMappings(columnMappings);
        oto.addPostProcessor(new VisitUpdater());
        return oto;
    }

    private OneToOne configureTransaction_Stock() {
        OneToOne oto = new OneToOne(9, new Table("tblARVDrugStockTransactions", Table.orderBy("StockTranNo")),
                new Table("transaction"));
        oto.addWhereCondition(new WhereCondition("legacy_pk LIKE", "Stck%", Types.VARCHAR));

        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        Column transactionTypeId = new Column("transaction_type_id", Types.INTEGER);
        transactionTypeId.setReference(new Reference("transaction_type", "legacy_pk", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("TransactionType_", Types.VARCHAR), transactionTypeId);

        columnMappings.put(new Column("StockTranNo_", Types.INTEGER), new Column("legacy_pk", Types.VARCHAR, "Stck"));
        columnMappings.put(new Column("RefOrderNo_", Types.INTEGER), new Column("reference_no", Types.VARCHAR));
        columnMappings.put(new Column("TranDate_", Types.DATE), new Column("date", Types.DATE));
        columnMappings.put(new Column("Remarks_", Types.VARCHAR), new Column("comments", Types.VARCHAR));

        oto.setParameterizedQuery("SELECT MIN(StockTranNo) AS StockTranNo_, MIN(TransactionType) AS TransactionType_,\n"
                + "MIN(RefOrderNo) AS RefOrderNo_, MIN(TranDate) AS TranDate_, MIN(Remarks) AS Remarks_\n"
                + "FROM tblARVDrugStockTransactions WHERE Remarks NOT LIKE 'Dispensed to Patient No: %'\n"
                + "OR Remarks IS NULL\n"
                + "GROUP BY StockTranNo\n"
                + "ORDER BY StockTranNo ASC");
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configureTransaction_Patient() throws SQLException {
        OneToOne oto = new OneToOne(9, new Table("tblARTPatientTransactions", Table.orderBy("MIN(PatientTranNo)")),
                new Table("transaction"));
        oto.addWhereCondition(new WhereCondition("legacy_pk LIKE", "Prsn%", Types.VARCHAR));

        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column(null, Types.VARCHAR),
                new Column("transaction_type_id", Types.INTEGER,
                        new LookupValue("transaction_type", "Dispensed to Patients")));
        columnMappings.put(new Column("PatientTranNo_", Types.INTEGER), new Column("legacy_pk", Types.VARCHAR, "Prsn"));

        Column visitId = new Column("visit_id", Types.INTEGER);
        visitId.setReference(new Reference("visit", "legacy_pk"));
        columnMappings.put(new Column("PatientTranNo_", Types.INTEGER), visitId);

        Column indication = new Column("indication_id", Types.INTEGER);
        indication.setReference(new Reference("indication", "legacy_pk"));
        columnMappings.put(new Column("Indication_", Types.VARCHAR), indication);

        columnMappings.put(new Column("DateofVisit", Types.DATE), new Column("date", Types.DATE));
        columnMappings.put(new Column("Comment_", Types.VARCHAR), new Column("comments", Types.VARCHAR));

        oto.setParameterizedQuery("SELECT\n"
                + "MIN(PatientTranNo) AS PatientTranNo_,\n"
                + "DateofVisit,\n"
                + "MIN(Comment) AS Comment_,\n"
                + "MIN(Indication) AS Indication_,\n"
                + "ARTID\n"
                + "FROM\n"
                + "tblARTPatientTransactions\n"
                + "GROUP BY\n"
                + "DateofVisit, ARTID\n"
                + "ORDER BY MIN(PatientTranNo)");
        oto.setColumnMappings(columnMappings);
        oto.addPreProcessor(new LookupValuePkProcessor());
        return oto;
    }

    private OneToOne configureTransactionItem_Stock() {
        OneToOne oto = new OneToOne(8, new Table("tblARVDrugStockTransactions", Table.orderBy("StockTranNo")),
                new Table("transaction_item"));
        oto.addWhereCondition(new WhereCondition("legacy_pk LIKE", "Stck%", Types.VARCHAR));

        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        Column drugId = new Column("drug_id", Types.INTEGER);
        drugId.setReference(new Reference("drug", "name", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("ARVDrugsID_", Types.VARCHAR), drugId);

        Column transactionId = new Column("transaction_id", Types.INTEGER);
        transactionId.setReference(new Reference("transaction", "legacy_pk", "Stck", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("StockTranNo_", Types.INTEGER), transactionId);

        Column accountId = new Column("account_id", Types.INTEGER);
        Reference reference = new Reference("account", "name", Reference.NullAction.THROW_EXCEPTION);
        reference.setInferable(true);
        reference.setValueInferrer(new AccountValueInferrer());
        accountId.setReference(reference);
        columnMappings.put(new Column("SourceorDestination_", Types.VARCHAR), accountId);

        columnMappings.put(new Column("StockTranNo_", Types.INTEGER), new Column("legacy_pk", Types.VARCHAR, "Stck"));
        columnMappings.put(new Column("BatchNo_", Types.VARCHAR), new Column("batch_no", Types.VARCHAR));
        columnMappings.put(new Column("Qty_", Types.DATE), new Column("units_in", Types.DECIMAL));
        columnMappings.put(new Column("Qty_", Types.VARCHAR), new Column("units_out", Types.DECIMAL));

        String sql = "SELECT MIN(StockTranNo) AS StockTranNo_, MIN(ARVDrugsID) AS ARVDrugsID_, MIN(SourceorDestination)\n"
                + "AS SourceorDestination_, MIN(BatchNo) AS BatchNo_, MIN(Qty) AS Qty_ FROM tblARVDrugStockTransactions\n"
                + "WHERE Remarks NOT LIKE ? OR Remarks IS NULL\n"
                + "GROUP BY StockTranNo\n"
                + "ORDER BY StockTranNo ASC";
        List<Parameter> params = new ArrayList<>();
        params.add(new Parameter("Dispensed to Patient No: %", Types.VARCHAR));
        oto.setParameterizedQuery(new ParameterizedQuery(sql, params));
        oto.setColumnMappings(columnMappings);

        return oto;
    }

    private OneToOne configureTransactionItem_Patient() throws SQLException {
        OneToOne oto = new OneToOne(8, new Table("tblARTPatientTransactions", Table.orderBy("PatientTranNo")),
                new Table("transaction_item"));
        oto.addWhereCondition(new WhereCondition("legacy_pk LIKE", "Prsn%", Types.VARCHAR));

        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        Column drugId = new Column("drug_id", Types.INTEGER);
        drugId.setReference(new Reference("drug", "name", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("Drugname_", Types.VARCHAR), drugId);

        Column transactionId = new Column("transaction_id", Types.INTEGER);
        transactionId.setReference(new Reference("transaction", "legacy_pk", true, "Prsn", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("PatientTranNo_", Types.INTEGER), transactionId);

        columnMappings.put(new Column(null, Types.VARCHAR),
                new Column("account_id", Types.INTEGER,
                        new LookupValue("account", "PATIENTS")));

        columnMappings.put(new Column("PatientTranNo_", Types.INTEGER), new Column("legacy_pk", Types.VARCHAR, "Prsn"));
        columnMappings.put(new Column("BatchNo_", Types.VARCHAR), new Column("batch_no", Types.VARCHAR));
        columnMappings.put(new Column("ARVQty_", Types.DATE), new Column("units_in", Types.DECIMAL));
        columnMappings.put(new Column("ARVQty_", Types.VARCHAR), new Column("units_out", Types.DECIMAL));

        oto.setParameterizedQuery("SELECT\n"
                + "MIN(PatientTranNo) AS PatientTranNo_,\n"
                + "MIN(Drugname) AS Drugname_,\n"
                + "MIN(BatchNo) AS BatchNo_,\n"
                + "MIN(ARVQty) AS ARVQty_\n"
                + "FROM\n"
                + "tblARTPatientTransactions\n"
                + "GROUP BY PatientTranNo\n"
                + "ORDER BY PatientTranNo ASC");
        oto.setColumnMappings(columnMappings);

        oto.addPreProcessor(new LookupValuePkProcessor());
        oto.addPostProcessor(new UnitsInOutUpdater());
        return oto;
    }

    private OneToOne configureBatchTransactionItem() {
        OneToOne oto = new OneToOne(6, new Table("tblARVDrugStockTransactions", Table.orderBy("StockTranNo")),
                new Table("batch_transaction_item"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("StockTranNo", Types.VARCHAR), new Column("legacy_pk", Types.VARCHAR));
        columnMappings.put(new Column("Npacks", Types.INTEGER), new Column("no_of_packs", Types.INTEGER));
        columnMappings.put(new Column("PackSize", Types.INTEGER), new Column("pack_size", Types.INTEGER));
        columnMappings.put(new Column("Expirydate", Types.DATE), new Column("date_of_expiry", Types.DATE));

        Column patientId = new Column("transaction_item_id", Types.INTEGER);
        patientId.setReference(new Reference("transaction_item", "legacy_pk", "Stck", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("StockTranNo", Types.VARCHAR), patientId);

        String sql = "SELECT StockTranNo, Npacks, PackSize, BatchNo, Expirydate \n"
                + "FROM tblARVDrugStockTransactions\n"
                + "WHERE (Remarks NOT LIKE ?\n"
                + "OR Remarks IS NULL)\n"
                + "AND PackSize IS NOT NULL\n"
                + "AND Npacks IS NOT NULL\n"
                + "ORDER BY StockTranNo ASC";
        List<Parameter> params = new ArrayList<>();
        params.add(new Parameter("Dispensed to Patient No: %", Types.VARCHAR));

        oto.setParameterizedQuery(new ParameterizedQuery(sql, params));
        oto.setColumnMappings(columnMappings);
        return oto;
    }

    private OneToOne configurePatientTransactionItem() {
        OneToOne oto = new OneToOne(7, new Table("tblARTPatientTransactions", Table.orderBy("PatientTranNo")),
                new Table("patient_transaction_item"));
        Map<Column, Column> columnMappings = new LinkedHashMap<>();

        columnMappings.put(new Column("PatientTranNo_", Types.INTEGER), new Column("legacy_pk", Types.INTEGER));

        columnMappings.put(new Column("duration_", Types.INTEGER), new Column("duration", Types.INTEGER));

        Column transactionItemId = new Column("transaction_item_id", Types.INTEGER);
        transactionItemId.setReference(new Reference("transaction_item", "legacy_pk", "Prsn", Reference.NullAction.THROW_EXCEPTION));
        columnMappings.put(new Column("PatientTranNo_", Types.VARCHAR), transactionItemId);

        Column dosage = new Column("dosage_id", Types.INTEGER);
        dosage.setReference(new Reference("dosage"));
        columnMappings.put(new Column("Dose_", Types.VARCHAR), dosage);

        columnMappings.put(new Column("Dose_", Types.VARCHAR), new Column("dosage_name", Types.VARCHAR));

        oto.setParameterizedQuery("SELECT\n"
                + "MIN(PatientTranNo) AS PatientTranNo_,\n"
                + "MIN(duration) AS duration_,\n"
                + "MIN(Dose) AS Dose_\n"
                + "FROM\n"
                + "tblARTPatientTransactions\n"
                + "GROUP BY PatientTranNo\n"
                + "ORDER BY PatientTranNo ASC");
        oto.setColumnMappings(columnMappings);
        return oto;
    }
}
