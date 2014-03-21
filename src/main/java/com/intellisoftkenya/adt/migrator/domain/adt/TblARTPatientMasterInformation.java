package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gitahi
 */
public class TblARTPatientMasterInformation implements Serializable {
    private List<TblARTPatientTransactions> tblARTPatientTransactionsList;
    private static final long serialVersionUID = 1L;
    private String artID;
    private String firstname;
    private String surname;
    private String sex;
    private Float age;
    private Boolean pregnant;
    private Date dateTherapyStarted;
    private Float weightOnStart;
    private Integer clientSupportedBy;
    private String otherDeaseConditions;
    private String aDRorSideEffects;
    private String reasonsforChanges;
    private String otherDrugs;
    private Integer typeOfService;
    private Integer daysToNextAppointment;
    private Date dateOfNextAppointment;
    private Integer currentStatus;
    private String currentRegimen;
    private String regimenStarted;
    private String address;
    private Float currentWeight;
    private Float startBSA;
    private Float currentBSA;
    private Boolean ischild;
    private Boolean isadult;
    private Float startHeight;
    private Float currentHeight;
    private Boolean naive;
    private Boolean nonNaive;
    private Boolean cotrimoxazole;
    private Boolean tb;
    private Boolean noCotrimoxazole;
    private Boolean noTB;
    private Date dateStartedonART;
    private Date dateChangedStatus;
    private Integer ncurrentAge;
    private String opipno;
    private String lastName;
    private Date dateofBirth;
    private String placeofBirth;
    private Integer patientCellphone;
    private String alternateContact;
    private Boolean patientSmoke;
    private Boolean patientDrinkAlcohol;
    private Boolean patientDontSmoke;
    private Boolean patientDontDrinkAlcohol;
    private Integer inactiveDays;
    private String transferFrom;
    private TblSourceOfClient tblSourceOfClient;

    public TblARTPatientMasterInformation() {
    }

    public TblARTPatientMasterInformation(String artID) {
        this.artID = artID;
    }

    public String getArtID() {
        return artID;
    }

    public void setArtID(String artID) {
        this.artID = artID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getAge() {
        return age;
    }

    public void setAge(Float age) {
        this.age = age;
    }

    public Boolean getPregnant() {
        return pregnant;
    }

    public void setPregnant(Boolean pregnant) {
        this.pregnant = pregnant;
    }

    public Date getDateTherapyStarted() {
        return dateTherapyStarted;
    }

    public void setDateTherapyStarted(Date dateTherapyStarted) {
        this.dateTherapyStarted = dateTherapyStarted;
    }

    public Float getWeightOnStart() {
        return weightOnStart;
    }

    public void setWeightOnStart(Float weightOnStart) {
        this.weightOnStart = weightOnStart;
    }

    public Integer getClientSupportedBy() {
        return clientSupportedBy;
    }

    public void setClientSupportedBy(Integer clientSupportedBy) {
        this.clientSupportedBy = clientSupportedBy;
    }

    public String getOtherDeaseConditions() {
        return otherDeaseConditions;
    }

    public void setOtherDeaseConditions(String otherDeaseConditions) {
        this.otherDeaseConditions = otherDeaseConditions;
    }

    public String getADRorSideEffects() {
        return aDRorSideEffects;
    }

    public void setADRorSideEffects(String aDRorSideEffects) {
        this.aDRorSideEffects = aDRorSideEffects;
    }

    public String getReasonsforChanges() {
        return reasonsforChanges;
    }

    public void setReasonsforChanges(String reasonsforChanges) {
        this.reasonsforChanges = reasonsforChanges;
    }

    public String getOtherDrugs() {
        return otherDrugs;
    }

    public void setOtherDrugs(String otherDrugs) {
        this.otherDrugs = otherDrugs;
    }

    public Integer getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(Integer typeOfService) {
        this.typeOfService = typeOfService;
    }

    public Integer getDaysToNextAppointment() {
        return daysToNextAppointment;
    }

    public void setDaysToNextAppointment(Integer daysToNextAppointment) {
        this.daysToNextAppointment = daysToNextAppointment;
    }

    public Date getDateOfNextAppointment() {
        return dateOfNextAppointment;
    }

    public void setDateOfNextAppointment(Date dateOfNextAppointment) {
        this.dateOfNextAppointment = dateOfNextAppointment;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCurrentRegimen() {
        return currentRegimen;
    }

    public void setCurrentRegimen(String currentRegimen) {
        this.currentRegimen = currentRegimen;
    }

    public String getRegimenStarted() {
        return regimenStarted;
    }

    public void setRegimenStarted(String regimenStarted) {
        this.regimenStarted = regimenStarted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Float getStartBSA() {
        return startBSA;
    }

    public void setStartBSA(Float startBSA) {
        this.startBSA = startBSA;
    }

    public Float getCurrentBSA() {
        return currentBSA;
    }

    public void setCurrentBSA(Float currentBSA) {
        this.currentBSA = currentBSA;
    }

    public Boolean getIschild() {
        return ischild;
    }

    public void setIschild(Boolean ischild) {
        this.ischild = ischild;
    }

    public Boolean getIsadult() {
        return isadult;
    }

    public void setIsadult(Boolean isadult) {
        this.isadult = isadult;
    }

    public Float getStartHeight() {
        return startHeight;
    }

    public void setStartHeight(Float startHeight) {
        this.startHeight = startHeight;
    }

    public Float getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(Float currentHeight) {
        this.currentHeight = currentHeight;
    }

    public Boolean getNaive() {
        return naive;
    }

    public void setNaive(Boolean naive) {
        this.naive = naive;
    }

    public Boolean getNonNaive() {
        return nonNaive;
    }

    public void setNonNaive(Boolean nonNaive) {
        this.nonNaive = nonNaive;
    }

    public Boolean getCotrimoxazole() {
        return cotrimoxazole;
    }

    public void setCotrimoxazole(Boolean cotrimoxazole) {
        this.cotrimoxazole = cotrimoxazole;
    }

    public Boolean getTb() {
        return tb;
    }

    public void setTb(Boolean tb) {
        this.tb = tb;
    }

    public Boolean getNoCotrimoxazole() {
        return noCotrimoxazole;
    }

    public void setNoCotrimoxazole(Boolean noCotrimoxazole) {
        this.noCotrimoxazole = noCotrimoxazole;
    }

    public Boolean getNoTB() {
        return noTB;
    }

    public void setNoTB(Boolean noTB) {
        this.noTB = noTB;
    }

    public Date getDateStartedonART() {
        return dateStartedonART;
    }

    public void setDateStartedonART(Date dateStartedonART) {
        this.dateStartedonART = dateStartedonART;
    }

    public Date getDateChangedStatus() {
        return dateChangedStatus;
    }

    public void setDateChangedStatus(Date dateChangedStatus) {
        this.dateChangedStatus = dateChangedStatus;
    }

    public Integer getNcurrentAge() {
        return ncurrentAge;
    }

    public void setNcurrentAge(Integer ncurrentAge) {
        this.ncurrentAge = ncurrentAge;
    }

    public String getOpipno() {
        return opipno;
    }

    public void setOpipno(String opipno) {
        this.opipno = opipno;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getPlaceofBirth() {
        return placeofBirth;
    }

    public void setPlaceofBirth(String placeofBirth) {
        this.placeofBirth = placeofBirth;
    }

    public Integer getPatientCellphone() {
        return patientCellphone;
    }

    public void setPatientCellphone(Integer patientCellphone) {
        this.patientCellphone = patientCellphone;
    }

    public String getAlternateContact() {
        return alternateContact;
    }

    public void setAlternateContact(String alternateContact) {
        this.alternateContact = alternateContact;
    }

    public Boolean getPatientSmoke() {
        return patientSmoke;
    }

    public void setPatientSmoke(Boolean patientSmoke) {
        this.patientSmoke = patientSmoke;
    }

    public Boolean getPatientDrinkAlcohol() {
        return patientDrinkAlcohol;
    }

    public void setPatientDrinkAlcohol(Boolean patientDrinkAlcohol) {
        this.patientDrinkAlcohol = patientDrinkAlcohol;
    }

    public Boolean getPatientDontSmoke() {
        return patientDontSmoke;
    }

    public void setPatientDontSmoke(Boolean patientDontSmoke) {
        this.patientDontSmoke = patientDontSmoke;
    }

    public Boolean getPatientDontDrinkAlcohol() {
        return patientDontDrinkAlcohol;
    }

    public void setPatientDontDrinkAlcohol(Boolean patientDontDrinkAlcohol) {
        this.patientDontDrinkAlcohol = patientDontDrinkAlcohol;
    }

    public Integer getInactiveDays() {
        return inactiveDays;
    }

    public void setInactiveDays(Integer inactiveDays) {
        this.inactiveDays = inactiveDays;
    }

    public String getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    public TblSourceOfClient getTblSourceOfClient() {
        return tblSourceOfClient;
    }

    public void setTblSourceOfClient(TblSourceOfClient tblSourceOfClient) {
        this.tblSourceOfClient = tblSourceOfClient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artID != null ? artID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblARTPatientMasterInformation)) {
            return false;
        }
        TblARTPatientMasterInformation other = (TblARTPatientMasterInformation) object;
        if ((this.artID == null && other.artID != null) || (this.artID != null && !this.artID.equals(other.artID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblARTPatientMasterInformation[ artID=" + artID + " ]";
    }

    public List<TblARTPatientTransactions> getTblARTPatientTransactionsList() {
        return tblARTPatientTransactionsList;
    }

    public void setTblARTPatientTransactionsList(List<TblARTPatientTransactions> tblARTPatientTransactionsList) {
        this.tblARTPatientTransactionsList = tblARTPatientTransactionsList;
    }
    
}
