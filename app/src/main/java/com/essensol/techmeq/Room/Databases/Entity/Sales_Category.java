package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Sales_Master")
public class Sales_Category {

    @PrimaryKey(autoGenerate = true)

    private  int ProductCatId;


    private  String ProductCategory;

    private String Image;

    private  boolean Status;


    public Sales_Category(String productCategory, String image, boolean status) {
        ProductCategory = productCategory;
        Image = image;
        Status = status;
    }


    public int getProductCatId() {
        return ProductCatId;
    }

    public void setProductCatId(int productCatId) {
        ProductCatId = productCatId;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }


    @NonNull
    @Override
    public String toString() {
        return ProductCategory;
    }
}
