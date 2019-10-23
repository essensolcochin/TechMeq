package com.essensol.techmeq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Repository.mRepo;

import java.util.List;

public class ProductCategoryViewModel extends AndroidViewModel {


    private mRepo category_repo;
    private LiveData<List<Sales_Category>> allCategories;

    public ProductCategoryViewModel(@NonNull Application application) {
        super(application);

        category_repo =new mRepo(application);
        allCategories =category_repo.getAllProductsCategory();


    }


    public void AddProductCategory(Sales_Category category)
    {
        category_repo.AddProductCategory(category);
    }

    public void UpdateProductCategory(Sales_Category category)
    {
        category_repo.UpdateProductCategory(category);
    }

    public void DeleteProductCategory(Sales_Category category)
    {
        category_repo.DeleteProductCategory(category);
    }

    public LiveData<List<Sales_Category>> GetAllProductCategory()
    {
        return  allCategories;
    }


}
