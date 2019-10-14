package com.essensol.techmeq.Model;

public class PurchaseModel {

    private String name,qty,rate,netAmount;

    public PurchaseModel(String name, String qty, String rate, String netAmount) {
        this.name = name;
        this.qty = qty;
        this.rate = rate;
        this.netAmount = netAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }
}
