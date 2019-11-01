package com.essensol.techmeq.Model;

import java.math.BigDecimal;

public class PurchaseModel {

    private String name;
    private int qty,ProductId;
    private BigDecimal rate,netAmount,linetot;

    public PurchaseModel(String name, int ProductId,int qty, BigDecimal rate, BigDecimal netAmount,BigDecimal linetot) {
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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public BigDecimal getLinetot() {
        return linetot;
    }

    public void setLinetot(BigDecimal linetot) {
        this.linetot = linetot;
    }
}
