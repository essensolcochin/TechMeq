package com.essensol.techmeq.Room.Databases.Entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DecimalConverter;

import java.math.BigDecimal;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName="Sales_Item"
, indices = @Index(value = {"ProductId", "SaleId"}),
foreignKeys = {@ForeignKey(entity = Products.class, parentColumns = "Product_Id", childColumns = "ProductId",onDelete = CASCADE),
@ForeignKey(entity = SalesHeader.class, parentColumns = "SaleId", childColumns = "SaleId",onDelete = CASCADE)}
)

public class SalesItem {

    @PrimaryKey(autoGenerate = true)
    private int SaleItemId;

    @ColumnInfo(name = "SaleId")
    private int SaleId;

    @ColumnInfo(name = "ProductId")
    private int ProductId;

    @TypeConverters({DecimalConverter.class})
    private BigDecimal Qty;

    @TypeConverters({DecimalConverter.class})
    private BigDecimal Price;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal Total;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal TaxPercent;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal TaxAmt;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal LineTotal;

    public SalesItem() {
    }

    public SalesItem(int saleItemId, int saleId, int productId, BigDecimal qty, BigDecimal price, BigDecimal total, BigDecimal taxPercent, BigDecimal taxAmt, BigDecimal lineTotal) {
        SaleItemId = saleItemId;
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

    public BigDecimal getQty() {
        return Qty;
    }

    public void setQty(BigDecimal qty) {
        Qty = qty;
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

    public BigDecimal getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        TaxPercent = taxPercent;
    }

    public BigDecimal getTaxAmt() {
        return TaxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        TaxAmt = taxAmt;
    }

    public BigDecimal getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        LineTotal = lineTotal;
    }
}
