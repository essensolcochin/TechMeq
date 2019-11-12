package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.essensol.techmeq.Room.DateTypeConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "User_Master"
        , indices = @Index(value = {"CompId"}),
        foreignKeys = {@ForeignKey(entity = CompanyMaster.class, parentColumns = "CompId", childColumns = "CompId",onDelete = CASCADE)}
)
public class Users {

    @ColumnInfo(name = "CompId")
    private  int CompId;
    @PrimaryKey(autoGenerate = true)
    private  int UserId;
    private  String UserName;

    private  String Password;

    @TypeConverters({DateTypeConverter.class})
    private Date InstalledDate;

    private  boolean paidStatus;

    public Users(int compId, int userId, String userName, String password, Date installedDate, boolean paidStatus) {
        CompId = compId;
        UserId = userId;
        UserName = userName;
        Password = password;
        InstalledDate = installedDate;
        this.paidStatus = paidStatus;
    }

    public Users() {
    }

    public int getCompId() {
        return CompId;
    }

    public void setCompId(int compId) {
        CompId = compId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Date getInstalledDate() {
        return InstalledDate;
    }

    public void setInstalledDate(Date installedDate) {
        InstalledDate = installedDate;
    }

    public boolean isPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(boolean paidStatus) {
        this.paidStatus = paidStatus;
    }
}
