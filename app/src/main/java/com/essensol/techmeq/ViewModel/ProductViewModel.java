package com.essensol.techmeq.ViewModel;

import android.app.Application;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import android.support.annotation.NonNull;


import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Repository.mRepo;

import java.util.List;



public class ProductViewModel extends AndroidViewModel {

    private mRepo product_repo;
    private LiveData<List<Products>>allProducts;

    private LiveData<List<Sales_Category>> allCategories;

//    private LiveData<List<Sales_Category>> all_Product_By_CatId;


    public ProductViewModel(@NonNull Application application) {
        super(application);
        product_repo =new mRepo(application);
        allProducts =product_repo.getAllProducts();

        allCategories =product_repo.getAllProductsCategory();

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




    public void AddProductCategory(Sales_Category category)
    {
        product_repo.AddProductCategory(category);
    }

    public void UpdateProductCategory(Sales_Category category)
    {
        product_repo.UpdateProductCategory(category);
    }

    public void DeleteProductCategory(Sales_Category category)
    {
        product_repo.DeleteProductCategory(category);
    }

    public LiveData<List<Sales_Category>> GetAllProductCategory()
    {
        return  allCategories;
    }

    public LiveData<List<Products>> GetProduct_By_CategoryId(int Id)
    {
        return  product_repo.getAllProduct_By_Category(Id);
    }

//    public LiveData<List<Sales_Category>> GetProduct_By_CategoryId(int setId){
//        return allFormSets = formDatabase.formSetDao().getAllFilledForms(setId);
//    }



}

