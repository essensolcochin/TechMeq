package com.essensol.techmeq.Model;

public class ProductRecyclerviewModel {

    private int Product_Id;

    private  String Product_Name;

    private String Product_Category;

    private String Product_Price;

    public ProductRecyclerviewModel(int product_Id, String product_Name, String product_Category, String product_Price) {
        Product_Id = product_Id;
        Product_Name = product_Name;
        Product_Category = product_Category;
        Product_Price = product_Price;
    }

    public int getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(int product_Id) {
        Product_Id = product_Id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Category() {
        return Product_Category;
    }

    public void setProduct_Category(String product_Category) {
        Product_Category = product_Category;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String product_Price) {
        Product_Price = product_Price;
    }
}
