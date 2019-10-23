package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Room.Databases.Entity.Products;

import java.util.List;

@Dao
public interface Product_DAO {

    @Insert
    void AddProduct(Products products);

    @Update
    void UpdateProduct(Products products);

    @Delete
    void DeleteProduct(Products products);


    @Query("SELECT * FROM Product_Master ORDER BY Product_Id DESC")
    LiveData<List<Products>> GetAllProduct();

}
