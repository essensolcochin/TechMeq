package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "Sales_Header")

public class SalesHeader {


    private int CompId;

    private int FinYearId;

    @PrimaryKey(autoGenerate = true)
    private int SaleId = 0;

    private String SaleNo;


    @TypeConverters({DateTypeConverter.class})
    private Date SaleDate;

    private int CustId;

    private double SubTotal;

    private double TaxAmt;

    private double Discount;

    private double GrandTotal;

    private double PaidAmt;


    public SalesHeader() {
    }

    public SalesHeader(int compId, int finYearId, String saleNo, Date saleDate, int custId, double subTotal, double taxAmt, double discount, double grandTotal, double paidAmt) {
        CompId = compId;
        FinYearId = finYearId;
        SaleNo = saleNo;
        SaleDate = saleDate;
        CustId = custId;
        SubTotal = subTotal;
        TaxAmt = taxAmt;
        Discount = discount;
        GrandTotal = grandTotal;
        PaidAmt = paidAmt;


    }


    public int getCompId() {
        return CompId;
    }

    public void setCompId(int compId) {
        CompId = compId;
    }

    public int getFinYearId() {
        return FinYearId;
    }

    public void setFinYearId(int finYearId) {
        FinYearId = finYearId;
    }

    public int getSaleId() {
        return SaleId;
    }

    public void setSaleId(int saleId) {
        SaleId = saleId;
    }

    public String getSaleNo() {
        return SaleNo;
    }

    public void setSaleNo(String saleNo) {
        SaleNo = saleNo;
    }

    public Date getSaleDate() {
        return SaleDate;
    }

    public void setSaleDate(Date saleDate) {
        SaleDate = saleDate;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }

    public double getTaxAmt() {
        return TaxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        TaxAmt = taxAmt;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        GrandTotal = grandTotal;
    }

    public double getPaidAmt() {
        return PaidAmt;
    }

    public void setPaidAmt(double paidAmt) {
        PaidAmt = paidAmt;
    }
}
