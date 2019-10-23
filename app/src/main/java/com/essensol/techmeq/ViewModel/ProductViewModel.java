package com.essensol.techmeq.ViewModel;

import android.app.Application;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import android.support.annotation.NonNull;


import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Repository.mRepo;

import java.util.List;



public class ProductViewModel extends AndroidViewModel {

    private mRepo product_repo;
    private LiveData<List<Products>>allProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        product_repo =new mRepo(application);
        allProducts =product_repo.getAllProducts();

    }

    public void AddProduct(Products Product)
    {
        product_repo.AddProduct(Product);
    }

    public void UpdateProduct(Products Product)
    {
        product_repo.UpdateProduct(Product);
    }

    public void DeleteProduct(Products Product)
    {
        product_repo.DeleteProduct(Product);
    }

    public LiveData<List<Products>> GetAllProduct()
    {
      return  allProducts;
    }



}

