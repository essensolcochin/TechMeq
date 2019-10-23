package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;

import java.util.List;

@Dao
public interface ProductCategory_DAO {



    @Insert
    void AddProductCategory(Sales_Category category);

    @Update
    void UpdateProductCategory(Sales_Category category);

    @Delete
    void DeleteProductCategory(Sales_Category category);


    @Query("SELECT * FROM Sales_Master ORDER BY ProductCatId ASC")
    LiveData<List<Sales_Category>> GetProductCategory();




}
