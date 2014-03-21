/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblARTPatientMasterInformation")
@NamedQueries({
    @NamedQuery(name = "TblARTPatientMasterInformation.findAll", query = "SELECT t FROM TblARTPatientMasterInformation t")})
public class TblARTPatientMasterInformation implements Serializable {
    @OneToMany(mappedBy = "tblARTPatientMasterInformation")
    private List<TblARTPatientTransactions> tblARTPatientTransactionsList;
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ArtID")
    private String artID;
    @Column(name = "Firstname")
    private String firstname;
    @Column(name = "Surname")
    private String surname;
    @Column(name = "Sex")
    private String sex;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Age")
    private Float age;
    @Column(name = "Pregnant")
    private Boolean pregnant;
    @Column(name = "DateTherapyStarted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTherapyStarted;
    @Column(name = "WeightOnStart")
    private Float weightOnStart;
    @Column(name = "ClientSupportedBy")
    private Integer clientSupportedBy;
    @Lob
    @Column(name = "OtherDeaseConditions")
    private String otherDeaseConditions;
    @Lob
    @Column(name = "ADRorSideEffects")
    private String aDRorSideEffects;
    @Lob
    @Column(name = "ReasonsforChanges")
    private String reasonsforChanges;
    @Lob
    @Column(name = "OtherDrugs")
    private String otherDrugs;
    @Column(name = "TypeOfService")
    private Integer typeOfService;
    @Column(name = "DaysToNextAppointment")
    private Integer daysToNextAppointment;
    @Column(name = "DateOfNextAppointment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfNextAppointment;
    @Column(name = "CurrentStatus")
    private Integer currentStatus;
    @Column(name = "CurrentRegimen")
    private String currentRegimen;
    @Column(name = "RegimenStarted")
    private String regimenStarted;
    @Column(name = "Address")
    private String address;
    @Column(name = "CurrentWeight")
    private Float currentWeight;
    @Column(name = "startBSA")
    private Float startBSA;
    @Column(name = "currentBSA")
    private Float currentBSA;
    @Column(name = "ischild")
    private Boolean ischild;
    @Column(name = "isadult")
    private Boolean isadult;
    @Column(name = "StartHeight")
    private Float startHeight;
    @Column(name = "CurrentHeight")
    private Float currentHeight;
    @Column(name = "Naive")
    private Boolean naive;
    @Column(name = "NonNaive")
    private Boolean nonNaive;
    @Column(name = "Cotrimoxazole")
    private Boolean cotrimoxazole;
    @Column(name = "TB")
    private Boolean tb;
    @Column(name = "NoCotrimoxazole")
    private Boolean noCotrimoxazole;
    @Column(name = "NoTB")
    private Boolean noTB;
    @Column(name = "DateStartedonART")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStartedonART;
    @Column(name = "DateChangedStatus")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChangedStatus;
    @Column(name = "NcurrentAge")
    private Integer ncurrentAge;
    @Column(name = "OPIPNO")
    private String opipno;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "DateofBirth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofBirth;
    @Column(name = "PlaceofBirth")
    private String placeofBirth;
    @Column(name = "PatientCellphone")
    private Integer patientCellphone;
    @Column(name = "AlternateContact")
    private String alternateContact;
    @Column(name = "PatientSmoke")
    private Boolean patientSmoke;
    @Column(name = "PatientDrinkAlcohol")
    private Boolean patientDrinkAlcohol;
    @Column(name = "PatientDontSmoke")
    private Boolean patientDontSmoke;
    @Column(name = "PatientDontDrinkAlcohol")
    private Boolean patientDontDrinkAlcohol;
    @Column(name = "InactiveDays")
    private Integer inactiveDays;
    @Column(name = "TransferFrom")
    private String transferFrom;
    @JoinColumn(name = "SourceofClient", referencedColumnName = "SourceID")
    @ManyToOne
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
