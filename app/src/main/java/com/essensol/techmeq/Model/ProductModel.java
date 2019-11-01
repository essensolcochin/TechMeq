package com.essensol.techmeq.Model;

import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DecimalConverter;

import java.math.BigDecimal;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ProductModel {

    private int Product_Id;

    private  String ProductName;

    private String ProductCategory;

    @TypeConverters({DecimalConverter.class})
    private BigDecimal Sales_Price;

    @TypeConverters({DecimalConverter.class})
    private BigDecimal TaxPercent;

    public ProductModel() {
    }

    public ProductModel(int product_Id, String productName, String productCategory, BigDecimal sales_Price, BigDecimal taxPercent) {
        Product_Id = product_Id;
        ProductName = productName;
        ProductCategory = productCategory;
        Sales_Price = sales_Price;
        TaxPercent = taxPercent;
    }

    public int getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(int product_Id) {
        Product_Id = product_Id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public BigDecimal getSales_Price() {
        return Sales_Price;
    }

    public void setSales_Price(BigDecimal sales_Price) {
        Sales_Price = sales_Price;
    }

    public BigDecimal getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        TaxPercent = taxPercent;
    }
}
