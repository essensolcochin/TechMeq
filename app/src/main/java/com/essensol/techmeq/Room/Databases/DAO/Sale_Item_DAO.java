package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Room.Databases.Entity.SalesItem;

import java.util.List;

@Dao
public interface Sale_Item_DAO {

    @Insert
    void   AddSalesItem(List<SalesItem>item);

    @Update
    void UpdateSalesItem(SalesItem item);

    @Delete
    void DeleteSalesItem(SalesItem item);


    @Query("SELECT * FROM sales_item ORDER BY SaleItemId DESC")
    List<SalesItem>GetAllSales();


//    @Query("SELECT  sales_header.SaleId , sales_header.SaleNo,PM.ProductName FROM sales_item " +
//            "INNER JOIN sales_header " +
//            "ON sales_header.SaleId=sales_item.SaleId "+
//            "INNER JOIN product_master AS PM " +
//            "ON PM.Product_Id=sales_item.ProductId")
//    LiveData<List<SalesItem>> getSalesitem();


}
