package com.essensol.techmeq.Room.Databases.Entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="Sales_Item",

foreignKeys = {@ForeignKey(entity = Products.class, parentColumns = "Product_Id", childColumns = "ProductId"),
@ForeignKey(entity = SalesHeader.class, parentColumns = "SaleId", childColumns = "SaleId")}

)

public class SalesItem {

    @PrimaryKey(autoGenerate = true)
    private int SaleItemId=0;

    private int SaleId;

    private int ProductId;

    private int Qty;

    private int Price;

    private int Total;

    private int TaxPercent;

    private int TaxAmt;

    private int LineTotal;

    public SalesItem(int saleId, int productId, int qty, int price, int total, int taxPercent, int taxAmt, int lineTotal) {
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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(int taxPercent) {
        TaxPercent = taxPercent;
    }

    public int getTaxAmt() {
        return TaxAmt;
    }

    public void setTaxAmt(int taxAmt) {
        TaxAmt = taxAmt;
    }

    public int getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(int lineTotal) {
        LineTotal = lineTotal;
    }
}
