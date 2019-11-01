package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DecimalConverter;

import java.math.BigDecimal;


@Entity (tableName = "Product_Master"
        , indices = @Index(value = {"ProductCatId", "ProductCatId"}),
        foreignKeys = {@ForeignKey(entity = Sales_Category.class, parentColumns = "ProductCatId", childColumns = "ProductCatId")}
)
public class Products {

    @PrimaryKey(autoGenerate = true)
    private int Product_Id;

    @ColumnInfo(name = "ProductCatId")
    private int ProductCatId;

    @TypeConverters({DecimalConverter.class})
    private BigDecimal TaxPercent;

    private String ProductName;

    @TypeConverters ({DecimalConverter.class})
    private BigDecimal Sales_Price;

    private boolean Status;


    public Products() {
    }

    public Products(int Product_Id,int productCatId, BigDecimal taxPercent, String productName, BigDecimal sales_Price, boolean status) {
        this.Product_Id =Product_Id;
        ProductCatId = productCatId;
        TaxPercent = taxPercent;
        ProductName = productName;
        Sales_Price = sales_Price;
        Status = status;
    }


    public int getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(int product_Id) {
        Product_Id = product_Id;
    }

    public int getProductCatId() {
        return ProductCatId;
    }

    public void setProductCatId(int productCatId) {
        ProductCatId = productCatId;
    }

    public BigDecimal getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        TaxPercent = taxPercent;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public BigDecimal getSales_Price() {
        return Sales_Price;
    }

    public void setSales_Price(BigDecimal sales_Price) {
        Sales_Price = sales_Price;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}


