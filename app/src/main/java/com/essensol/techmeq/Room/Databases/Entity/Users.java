package com.essensol.techmeq.Room.Databases.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;



@Entity(tableName = "User_Master")
public class Users {

    private  int CompId;
    @PrimaryKey(autoGenerate = true)
    private  int UserId;
    private  String UserName;

    private  String Password;

    public Users(int compId, String userName, String password) {
        CompId = compId;
        UserName = userName;
        Password = password;
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
}