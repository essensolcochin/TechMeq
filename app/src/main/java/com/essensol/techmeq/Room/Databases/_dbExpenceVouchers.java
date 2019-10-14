package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "Voucher_Master")
public class _dbExpenceVouchers {

    @PrimaryKey(autoGenerate = true)
    private int Voucher_Id;

    private  String Description;

    private  String Remarks;

    private  String Taxable;

    private  String vat;

    private  String total;

    private  String Created_On;

    public _dbExpenceVouchers() {


    }

    public _dbExpenceVouchers( String description, String remarks, String taxable, String vat, String total, String created_On) {
        Description = description;
        Remarks = remarks;
        Taxable = taxable;
        this.vat = vat;
        this.total = total;
        Created_On = created_On;
    }

    public int getVoucher_Id() {
        return Voucher_Id;
    }

    public void setVoucher_Id(int voucher_Id) {
        Voucher_Id = voucher_Id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getTaxable() {
        return Taxable;
    }

    public void setTaxable(String taxable) {
        Taxable = taxable;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCreated_On() {
        return Created_On;
    }

    public void setCreated_On(String created_On) {
        Created_On = created_On;
    }
}
