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

}
