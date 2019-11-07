package com.essensol.techmeq.Model;

import java.math.BigDecimal;

public class PurchaseModel {

    private String name;
    private int ProductId;
    private BigDecimal rate,netAmount,linetot,TaxPer,qty,TaxAmnt;

    public PurchaseModel(String name, int productId, BigDecimal rate, BigDecimal netAmount, BigDecimal linetot, BigDecimal taxPer, BigDecimal qty, BigDecimal taxAmnt) {
        this.name = name;
        ProductId = productId;
        this.rate = rate;
        this.netAmount = netAmount;
        this.linetot = linetot;
        TaxPer = taxPer;
        this.qty = qty;
        TaxAmnt = taxAmnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
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

    public BigDecimal getLinetot() {
        return linetot;
    }

    public void setLinetot(BigDecimal linetot) {
        this.linetot = linetot;
    }

    public BigDecimal getTaxPer() {
        return TaxPer;
    }

    public void setTaxPer(BigDecimal taxPer) {
        TaxPer = taxPer;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getTaxAmnt() {
        return TaxAmnt;
    }

    public void setTaxAmnt(BigDecimal taxAmnt) {
        TaxAmnt = taxAmnt;
    }
}
