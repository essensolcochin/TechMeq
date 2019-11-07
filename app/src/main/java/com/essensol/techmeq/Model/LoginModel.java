package com.essensol.techmeq.Model;

public class LoginModel {

    private  int CompId;
    private  int UserId;
    private  String UserName;
    private String CompName;

    public LoginModel() {
    }

    public LoginModel(int compId, int userId, String userName, String compName) {
        CompId = compId;
        UserId = userId;
        UserName = userName;
        CompName = compName;
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

    public String getCompName() {
        return CompName;
    }

    public void setCompName(String compName) {
        CompName = compName;
    }
}
