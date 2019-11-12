package com.essensol.techmeq.Model;

import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DecimalConverter;

import java.math.BigDecimal;
@TypeConverters({DecimalConverter.class})
public class EditSaleModel {

    private String ProductName;
    private int ProductId,SaleId,SaleItemId;
    private BigDecimal Price,Total,LineTotal,TaxPercent,Qty,TaxAmt;


    public EditSaleModel() {
    }

    public EditSaleModel(String productName, int productId, int saleId, int saleItemId, BigDecimal price, BigDecimal total, BigDecimal lineTotal, BigDecimal taxPercent, BigDecimal qty, BigDecimal taxAmt) {
        ProductName = productName;
        ProductId = productId;
        SaleId = saleId;
        SaleItemId = saleItemId;
        Price = price;
        Total = total;
        LineTotal = lineTotal;
        TaxPercent = taxPercent;
        Qty = qty;
        TaxAmt = taxAmt;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getSaleId() {
        return SaleId;
    }

    public void setSaleId(int saleId) {
        SaleId = saleId;
    }

    public int getSaleItemId() {
        return SaleItemId;
    }

    public void setSaleItemId(int saleItemId) {
        SaleItemId = saleItemId;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public BigDecimal getTotal() {
        return Total;
    }

    public void setTotal(BigDecimal total) {
        Total = total;
    }

    public BigDecimal getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        LineTotal = lineTotal;
    }

    public BigDecimal getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        TaxPercent = taxPercent;
    }

    public BigDecimal getQty() {
        return Qty;
    }

    public void setQty(BigDecimal qty) {
        Qty = qty;
    }

    public BigDecimal getTaxAmt() {
        return TaxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        TaxAmt = taxAmt;
    }
}
