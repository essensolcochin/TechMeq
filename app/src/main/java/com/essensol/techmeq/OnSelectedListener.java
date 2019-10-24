package com.essensol.techmeq;

import com.essensol.techmeq.Model.mProductModel;

import java.util.List;

public interface OnSelectedListener {


    void getProductDetails(List<mProductModel> items);


//    void getProductDetails(int Product_Id,int ProductCatId,double TaxPercent,String ProductName,double Sales_Price,boolean Status);
}
