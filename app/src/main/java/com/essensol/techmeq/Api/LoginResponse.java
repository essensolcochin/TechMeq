package com.essensol.techmeq.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {


    @SerializedName("responseCode")
    @Expose
    private String responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("result")
    @Expose
    private ArrayList<Result>results;

    public String getCode() {
        return responseCode;
    }

    public void setCode(String code) {
        responseCode = code;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ArrayList<Result> getResults() {
        return results;
    }



    public  class Result
    {


        //"UserId": 1,
        //            "UserName": "bill",
        //            "PayStatus": false,
        //            "RoleId": 5,
        //            "Admin": false

        @SerializedName("LoginResult")
        @Expose
        private String LoginResult;

        @SerializedName("LoginMsg")
        @Expose
        private String LoginMsg;

        @SerializedName("UserCode")
        @Expose
        private String UserCode;

        @SerializedName("UserId")
        @Expose
        private String UserId;

        @SerializedName("UserName")
        @Expose
        private String UserName;


        @SerializedName("RoleId")
        @Expose
        private String RoleId;


        @SerializedName("Admin")
        @Expose
        private boolean Admin;


        @SerializedName("PayStatus")
        @Expose
        private boolean PayStatus;


        public String getLoginResult() {
            return LoginResult;
        }

        public void setLoginResult(String loginResult) {
            LoginResult = loginResult;
        }

        public String getLoginMsg() {
            return LoginMsg;
        }

        public void setLoginMsg(String loginMsg) {
            LoginMsg = loginMsg;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public boolean getPayStatus() {
            return PayStatus;
        }

        public void setPayStatus(boolean payStatus) {
            PayStatus = payStatus;
        }
    }
}
