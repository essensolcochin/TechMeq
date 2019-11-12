package com.essensol.techmeq.ViewModel;

import android.app.Application;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import android.support.annotation.NonNull;


import com.essensol.techmeq.Model.CustomerSpinnerModel;
import com.essensol.techmeq.Model.EditSaleModel;
import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Repository.mRepo;

import java.util.List;



public class ProductViewModel extends AndroidViewModel {

    private mRepo product_repo;
    private LiveData<List<ProductModel>>allProducts;

    private LiveData<List<Products>>allProductsForSale;

    private LiveData<SalesHeader>getInvoiceId;

    private LiveData<List<Sales_Category>> allCategories;

    private LiveData<List<CustomerSpinnerModel>> getGetCustNameAndId;

    private  String exists;

    public ProductViewModel(@NonNull Application application) {
        super(application);

        product_repo =new mRepo(application);
        allProducts =product_repo.getAllProducts();
        getGetCustNameAndId=product_repo.getGetCustNameAndId();
        allCategories =product_repo.getAllProductsCategory();
        getInvoiceId =product_repo.getInvoiceAndSaleID();
        allProductsForSale=product_repo.getAllProductForSale();
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

    public LiveData<List<ProductModel>> GetAllProduct()
    {
      return  allProducts;
    }

    public LiveData<List<Products>> GetAllProductForSale()
    {
        return  allProductsForSale;
    }

    public LiveData<List<CustomerSpinnerModel>> GetCustNameAndId()
    {
        return getGetCustNameAndId;
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

    public LiveData<SalesHeader> GetInvoiceandSaleId()
    {
        return  getInvoiceId;
    }

    public  List<EditSaleModel>getAllSalesById(int SaleId)
    {
     return product_repo.getAllSales(SaleId);
    }


    public List<Products> GetAllreadyExist(String name)
    {
        return  product_repo.getExist(name);
    }





}

