package com.essensol.techmeq.Model;

public class VoucherModel {

    private  String voucherId,Description,remarks,taxable,Vat;

    public VoucherModel(String voucherId, String description, String remarks, String taxable, String vat) {
        this.voucherId = voucherId;
        Description = description;
        this.remarks = remarks;
        this.taxable = taxable;
        Vat = vat;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getVat() {
        return Vat;
    }

    public void setVat(String vat) {
        Vat = vat;
    }
}
