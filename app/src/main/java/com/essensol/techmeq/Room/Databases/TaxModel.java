package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Tax_master")

public class TaxModel {

    @PrimaryKey(autoGenerate = true)

    private int Tax_Id;


    private String Tax_Name;

    private int Tax_Percentage;

    public TaxModel() {
    }

    public TaxModel( String tax_Name, int tax_Percentage) {
        Tax_Name = tax_Name;
        Tax_Percentage = tax_Percentage;
    }

    public int getTax_Id() {
        return Tax_Id;
    }

    public void setTax_Id(int tax_Id) {
        Tax_Id = tax_Id;
    }

    public String getTax_Name() {
        return Tax_Name;
    }

    public void setTax_Name(String tax_Name) {
        Tax_Name = tax_Name;
    }

    public int getTax_Percentage() {
        return Tax_Percentage;
    }

    public void setTax_Percentage(int tax_Percentage) {
        Tax_Percentage = tax_Percentage;
    }
}
