package com.essensol.techmeq.Model;

public class PurchaseModel {

    private String name;
    private int qty,ProductId;
    private double rate,netAmount,linetot;

    public PurchaseModel(String name, int ProductId,int qty, double rate, double netAmount,double linetot) {
        this.name = name;
        this.qty = qty;
        this.rate = rate;
        this.netAmount = netAmount;
        this.ProductId = ProductId;
        this.linetot = linetot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public double getLinetot() {
        return linetot;
    }

    public void setLinetot(double linetot) {
        this.linetot = linetot;
    }
}
