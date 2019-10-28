package com.essensol.techmeq.Room.Databases.Entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="Sales_Item"
, indices = @Index(value = {"ProductId", "SaleId"}),
foreignKeys = {@ForeignKey(entity = Products.class, parentColumns = "Product_Id", childColumns = "ProductId"),
@ForeignKey(entity = SalesHeader.class, parentColumns = "SaleId", childColumns = "SaleId")}
)

public class SalesItem {

    @PrimaryKey(autoGenerate = true)
    private int SaleItemId;

    @ColumnInfo(name = "SaleId")
    private int SaleId;

    @ColumnInfo(name = "ProductId")
    private int ProductId;

    private int Qty;

    private double Price;

    private double Total;

    private double TaxPercent;

    private double TaxAmt;

    private double LineTotal;

    public SalesItem() {
    }

    public SalesItem(int saleId, int productId, int qty, double price, double total, double taxPercent, double taxAmt, double lineTotal) {
        SaleId = saleId;
        ProductId = productId;
        Qty = qty;
        Price = price;
        Total = total;
        TaxPercent = taxPercent;
        TaxAmt = taxAmt;
        LineTotal = lineTotal;
    }


    public int getSaleItemId() {
        return SaleItemId;
    }

    public void setSaleItemId(int saleItemId) {
        SaleItemId = saleItemId;
    }

    public int getSaleId() {
        return SaleId;
    }

    public void setSaleId(int saleId) {
        SaleId = saleId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        TaxPercent = taxPercent;
    }

    public double getTaxAmt() {
        return TaxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        TaxAmt = taxAmt;
    }

    public double getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(double lineTotal) {
        LineTotal = lineTotal;
    }
}
