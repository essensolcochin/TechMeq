package com.essensol.techmeq;

import com.essensol.techmeq.Model.mProductModel;

import java.math.BigDecimal;
import java.util.List;

public interface OnSelectedListener {


//    void getProductDetails(List<mProductModel> items);


    void getProductDetails(int Product_Id, int ProductCatId, BigDecimal TaxPercent, String ProductName, BigDecimal Sales_Price, boolean Status);
}
