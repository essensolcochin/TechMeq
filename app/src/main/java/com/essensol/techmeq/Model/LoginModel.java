package com.essensol.techmeq.Model;

public class LoginModel {

    private  int CompId;
    private  int UserId;
    private String UserName;
    private String CompName;
    private String CompBuilding;
    private String TRN;
    private String MobileNo;


    public LoginModel() {
    }

    public LoginModel(int compId, int userId, String userName, String compName, String compBuilding, String TRN, String mobileNo) {
        CompId = compId;
        UserId = userId;
        UserName = userName;
        CompName = compName;
        CompBuilding = compBuilding;
        this.TRN = TRN;
        MobileNo = mobileNo;
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

    public String getCompBuilding() {
        return CompBuilding;
    }

    public void setCompBuilding(String compBuilding) {
        CompBuilding = compBuilding;
    }

    public String getTRN() {
        return TRN;
    }

    public void setTRN(String TRN) {
        this.TRN = TRN;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
