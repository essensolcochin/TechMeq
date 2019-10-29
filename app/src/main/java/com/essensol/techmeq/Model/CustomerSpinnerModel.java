package com.essensol.techmeq.Model;

import android.support.annotation.NonNull;

public class CustomerSpinnerModel {

    private int CustId;
    private String CustName;


    public CustomerSpinnerModel() {
    }

    public CustomerSpinnerModel(int custId, String custName) {
        CustId = custId;
        CustName = custName;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    @NonNull
    @Override
    public String toString() {
        return CustName;
    }
}
