package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Customer_master",indices = @Index(value = {"CompId"})
        ,
        foreignKeys = {@ForeignKey(entity = CompanyMaster.class, parentColumns = "CompId", childColumns = "CompId")}
        )

public class Customers {

    @ColumnInfo(name = "CompId")
    private int CompId;
    @PrimaryKey(autoGenerate = true)
    private  int CustId;
    private String CustName;
    private  String Address;
    private String MobileNo;
    private boolean Status;

    public Customers() {
    }

    public Customers(int compId, String custName, String address, String mobileNo, boolean status) {
        CompId = compId;
        CustName = custName;
        Address = address;
        MobileNo = mobileNo;
        Status = status;
    }

    public int getCompId() {
        return CompId;
    }

    public void setCompId(int compId) {
        CompId = compId;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
//   public static double GetTaxReverse(double price, double taxPerc, double cessPerc)
//        {
//            return Math.Round((price * 10000) / ((10000 + taxPerc * 100) + (cessPerc * taxPerc)), 3);
//        }