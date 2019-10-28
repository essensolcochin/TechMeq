package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DateTypeConverter;

import java.util.Date;

@Entity(tableName = "Financial_Year")
public class FinancialYear {

    @PrimaryKey(autoGenerate = true)
    private  int FinyearId;

    private String FinYear;

    @TypeConverters ({DateTypeConverter.class})
    private Date StartingDate;

    @TypeConverters ({DateTypeConverter.class})
    private Date EndingDate;

    private  boolean LoadSettings;

    private boolean Status;

    public FinancialYear() {
    }

    public FinancialYear( String finYear, Date startingDate, Date endingDate, boolean loadSettings, boolean status) {
        FinYear = finYear;
        StartingDate = startingDate;
        EndingDate = endingDate;
        LoadSettings = loadSettings;
        Status = status;
    }


    public int getFinyearId() {
        return FinyearId;
    }

    public void setFinyearId(int finyearId) {
        FinyearId = finyearId;
    }

    public String getFinYear() {
        return FinYear;
    }

    public void setFinYear(String finYear) {
        FinYear = finYear;
    }

    public Date getStartingDate() {
        return StartingDate;
    }

    public void setStartingDate(Date startingDate) {
        StartingDate = startingDate;
    }

    public Date getEndingDate() {
        return EndingDate;
    }

    public void setEndingDate(Date endingDate) {
        EndingDate = endingDate;
    }

    public boolean isLoadSettings() {
        return LoadSettings;
    }

    public void setLoadSettings(boolean loadSettings) {
        LoadSettings = loadSettings;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
