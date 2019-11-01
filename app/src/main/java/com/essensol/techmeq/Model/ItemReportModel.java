package com.essensol.techmeq.Model;

import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DateTypeConverter;
import com.essensol.techmeq.Room.DecimalConverter;

import java.math.BigDecimal;
import java.util.Date;

public class ItemReportModel {

//   private boolean isHeader =false;

    private int SaleId;

    private String SaleNo;

    @TypeConverters({DateTypeConverter.class})
    private Date SaleDate;

    @TypeConverters({DecimalConverter.class})
    private BigDecimal SubTotal;


    @TypeConverters ({DecimalConverter.class})
    private BigDecimal TaxAmt;


    @TypeConverters ({DecimalConverter.class})
    private BigDecimal GrandTotal;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal PaidAmt;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal Credit;


    public ItemReportModel() {
    }

    public ItemReportModel(int saleId, String saleNo, Date saleDate, BigDecimal subTotal, BigDecimal taxAmt, BigDecimal grandTotal, BigDecimal paidAmt, BigDecimal credit) {
        SaleId = saleId;
        SaleNo = saleNo;
        SaleDate = saleDate;
        SubTotal = subTotal;
        TaxAmt = taxAmt;
        GrandTotal = grandTotal;
        PaidAmt = paidAmt;
        Credit = credit;
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

    public BigDecimal getCredit() {
        return Credit;
    }

    public void setCredit(BigDecimal credit) {
        Credit = credit;
    }
}
