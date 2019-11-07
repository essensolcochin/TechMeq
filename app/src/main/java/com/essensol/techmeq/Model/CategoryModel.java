package com.essensol.techmeq.Model;

public class CategoryModel {


    private  int ProductCatId;


    private String ProductCategory;

    private String Image;

    private  boolean Status;

    public CategoryModel(int productCatId, String productCategory, String image, boolean status) {
        ProductCatId = productCatId;
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
}
