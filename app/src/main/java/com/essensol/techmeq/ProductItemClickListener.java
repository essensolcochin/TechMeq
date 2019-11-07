package com.essensol.techmeq;

import java.math.BigDecimal;

public interface ProductItemClickListener {


    void getProductDetailsForEdit(int Product_Id, int ProductCatId, BigDecimal TaxPercent, String ProductName, BigDecimal Sales_Price, boolean Status);



}
