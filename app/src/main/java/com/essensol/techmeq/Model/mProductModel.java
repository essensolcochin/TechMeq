package com.essensol.techmeq.Model;

public class mProductModel {

    private int Product_Id;

    private int ProductCatId;

    private double TaxPercent;

    private String ProductName;

    private double Sales_Price;

    private boolean Status;

    public mProductModel(int product_Id, int productCatId, double taxPercent, String productName, double sales_Price, boolean status) {
        Product_Id = product_Id;
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

    public double getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        TaxPercent = taxPercent;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getSales_Price() {
        return Sales_Price;
    }

    public void setSales_Price(double sales_Price) {
        Sales_Price = sales_Price;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
