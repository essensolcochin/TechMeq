package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;

import java.math.BigDecimal;
import java.util.List;

@Dao
public interface Product_DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void AddProduct(Products products);

    @Update
    void UpdateProduct(Products products);

    @Delete
    void DeleteProduct(Products products);


    @Query("SELECT * FROM Product_Master  WHERE ProductName = :name")
    List<ProductModel>alreadyinserted(String name);


    @Query("SELECT * FROM Product_Master")
    LiveData<List<Products>>AllProductForSale();

    @Query("SELECT  Product_Master.Product_Id,Product_Master.ProductCatId , Product_Master.ProductName," +
            "SM.ProductCategory ,Product_Master.Sales_Price," +
            "Product_Master.TaxPercent FROM Product_Master " +
            "INNER JOIN Sales_Master AS SM " +
            "ON SM.ProductCatId=Product_Master.ProductCatId ")

    LiveData<List<ProductModel>> GetAllProduct();

    @Query("SELECT * FROM Product_Master WHERE ProductName LIKE '%' || :name  || '%'")
    List<Products>getDuplicateIfExist(String name);



    @Query("SELECT * FROM Product_Master WHERE ProductCatId = :Id")
    LiveData<List<Products>> GetProductCategoryByID(int Id);

}
