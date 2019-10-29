package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Company_master")
public class CompanyMaster {

   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "CompId")
   private int CompId;
   private String CompCode;
   private String CompName;
   private String Contact_Person;
   private String CompBuilding;
   private String PhoneNo;
   private String MobileNo;
   private String Email;
   private String Website;
   private String TaxType;
   private String Country;
   private String TRN;
   private boolean Status;

    public CompanyMaster() {

    }

    public CompanyMaster(String compCode, String compName, String contact_Person, String compBuilding, String phoneNo, String mobileNo, String email, String website, String taxType, String country, String TRN, boolean status) {
        CompCode = compCode;
        CompName = compName;
        Contact_Person = contact_Person;
        CompBuilding = compBuilding;
        PhoneNo = phoneNo;
        MobileNo = mobileNo;
        Email = email;
        Website = website;
        TaxType = taxType;
        Country = country;
        this.TRN = TRN;
        Status = status;
    }

    public String getCompCode() {
        return CompCode;
    }

    public void setCompCode(String compCode) {
        CompCode = compCode;
    }

    public String getCompName() {
        return CompName;
    }

    public void setCompName(String compName) {
        CompName = compName;
    }

    public String getContact_Person() {
        return Contact_Person;
    }

    public void setContact_Person(String contact_Person) {
        Contact_Person = contact_Person;
    }

    public String getCompBuilding() {
        return CompBuilding;
    }

    public void setCompBuilding(String compBuilding) {
        CompBuilding = compBuilding;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getTaxType() {
        return TaxType;
    }

    public void setTaxType(String taxType) {
        TaxType = taxType;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTRN() {
        return TRN;
    }

    public void setTRN(String TRN) {
        this.TRN = TRN;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public int getCompId() {
        return CompId;
    }

    public void setCompId(int compId) {
        CompId = compId;
    }
}
