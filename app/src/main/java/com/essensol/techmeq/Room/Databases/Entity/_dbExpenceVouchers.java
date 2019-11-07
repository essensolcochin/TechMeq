package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DateTypeConverter;
import com.essensol.techmeq.Room.DecimalConverter;

import java.math.BigDecimal;
import java.util.Date;


@Entity(tableName = "Voucher_Master")
public class _dbExpenceVouchers {

    @PrimaryKey(autoGenerate = true)
    private int Voucher_Id;

    private  String Description;

    private  String Remarks;


    @TypeConverters({DecimalConverter.class})
    private BigDecimal Taxable;

    @TypeConverters({DecimalConverter.class})
    private  BigDecimal vat;

    @TypeConverters({DecimalConverter.class})
    private  BigDecimal total;

    @TypeConverters({DateTypeConverter.class})
    private Date Created_On;

    public _dbExpenceVouchers() {


    }

    public _dbExpenceVouchers( String description, String remarks, BigDecimal taxable, BigDecimal vat, BigDecimal total, Date created_On) {
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

    public BigDecimal getTaxable() {
        return Taxable;
    }

    public void setTaxable(BigDecimal taxable) {
        Taxable = taxable;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getCreated_On() {
        return Created_On;
    }

    public void setCreated_On(Date created_On) {
        Created_On = created_On;
    }
}
