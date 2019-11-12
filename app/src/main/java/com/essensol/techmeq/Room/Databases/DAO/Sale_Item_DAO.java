package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Model.EditSaleModel;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface Sale_Item_DAO {

    @Insert
    void   AddSalesItem(SalesItem item);

    @Update
    void UpdateSalesItem(SalesItem item);

    @Query("DELETE FROM Sales_Item WHERE SaleId =:saleId")
    void DeleteSalesItem(int saleId);


    @Query("SELECT  PM.ProductName,Sales_Item.ProductId , Sales_Item.SaleId," +
            "Sales_Item.SaleItemId ,Sales_Item.Price," +
            "Sales_Item.Total,Sales_Item.LineTotal,Sales_Item.TaxPercent,Sales_Item.Qty,Sales_Item.TaxAmt FROM Sales_Item " +
            "INNER JOIN Product_Master AS PM " +
            "ON Sales_Item.ProductId=PM.Product_Id " +
            " WHERE SaleId =:mSaleID ")
        List<EditSaleModel> GetAllSales(int mSaleID);

//    @Query("SELECT * FROM sales_item WHERE SaleId=:mSaleID " +
//            "INNER JOIN Pro AS SM ON SM.ProductCatId=Product_Master.ProductCatId")
//







}
