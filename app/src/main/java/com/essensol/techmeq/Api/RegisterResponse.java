package com.essensol.techmeq.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterResponse {

    @SerializedName("responseCode")
    @Expose
    private String Code;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("result")
    @Expose
    private ArrayList<Result>results;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
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

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public  class Result
    {
        @SerializedName("result")
        @Expose
        private String result;

        @SerializedName("errorcode")
        @Expose
        private String errorcode;

        @SerializedName("msg")
        @Expose
        private String msg;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(String errorcode) {
            this.errorcode = errorcode;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
