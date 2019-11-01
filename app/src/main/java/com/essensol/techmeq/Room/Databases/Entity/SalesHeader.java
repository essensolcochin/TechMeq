package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DateTypeConverter;
import com.essensol.techmeq.Room.DecimalConverter;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "Sales_Header")

public class SalesHeader {


    private int CompId;

    private int FinYearId;

    @PrimaryKey(autoGenerate = true)
    private int SaleId;

    private String SaleNo;


    @TypeConverters({DateTypeConverter.class})
    private Date SaleDate;

    private int CustId;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal SubTotal;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal TaxAmt;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal Discount;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal GrandTotal;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal PaidAmt;


    public SalesHeader() {
    }

    public SalesHeader(int compId, int finYearId, String saleNo, Date saleDate, int custId, BigDecimal subTotal, BigDecimal taxAmt, BigDecimal discount, BigDecimal grandTotal, BigDecimal paidAmt) {
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

    public BigDecimal getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        SubTotal = subTotal;
    }

    public BigDecimal getTaxAmt() {
        return TaxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        TaxAmt = taxAmt;
    }

    public BigDecimal getDiscount() {
        return Discount;
    }

    public void setDiscount(BigDecimal discount) {
        Discount = discount;
    }

    public BigDecimal getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        GrandTotal = grandTotal;
    }

    public BigDecimal getPaidAmt() {
        return PaidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        PaidAmt = paidAmt;
    }
}
