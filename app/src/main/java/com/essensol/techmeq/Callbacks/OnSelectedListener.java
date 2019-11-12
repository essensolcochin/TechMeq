package com.essensol.techmeq.Callbacks;



import java.math.BigDecimal;

public interface OnSelectedListener {


//    void getProductDetails(List<mProductModel> items);


    void getProductDetails(int Product_Id, int ProductCatId, BigDecimal TaxPercent, String ProductName, BigDecimal Sales_Price, boolean Status);
}
