package com.essensol.techmeq.Room.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;
import com.essensol.techmeq.Room.Databases.Product_DB;
import com.essensol.techmeq.Room.Databases.Entity.Products;

import java.util.List;

public class Product_Repo {

    private Product_DAO product_dao;
    private LiveData<List<Products>>AllProducts;

    public Product_Repo(Application application) {

        Product_DB db =Product_DB.getInstance(application);
        product_dao=db.product_dao();
        AllProducts=product_dao.GetAllProduct();

    }

    public  void  AddProduct(Products products)
    {

        new AddProductAsync(product_dao).execute(products);
    }

    public  void  UpdateProduct(Products products)
    {
        new UpdateProductAsync(product_dao).execute(products);
    }

    public  void  DeleteProduct(Products products)
    {
        new DeleteProductAsync(product_dao).execute(products);
    }

//ToDo.
//    public  void  DeleteAllProducts()
//    {
//
//    }

    public  LiveData<List<Products>>getAllProducts()
    {
     return AllProducts;
    }


    private  static class AddProductAsync extends AsyncTask<Products,Void,Void>{

        private Product_DAO product_dao;

        public AddProductAsync(Product_DAO product_dao) {
            this.product_dao = product_dao;
        }

        @Override
        protected Void doInBackground(Products... products) {

            product_dao.AddProduct(products[0]);

            return null;
        }
    }


    private  static class UpdateProductAsync extends AsyncTask<Products,Void,Void>{

        private Product_DAO product_dao;

        public UpdateProductAsync(Product_DAO product_dao) {
            this.product_dao = product_dao;
        }

        @Override
        protected Void doInBackground(Products... products) {

            product_dao.UpdateProduct(products[0]);

            return null;
        }
    }


    private  static class DeleteProductAsync extends AsyncTask<Products,Void,Void>{

        private Product_DAO product_dao;

        public DeleteProductAsync(Product_DAO product_dao) {
            this.product_dao = product_dao;
        }

        @Override
        protected Void doInBackground(Products... products) {

            product_dao.DeleteProduct(products[0]);

            return null;
        }
    }

//ToDo Delete all
//    private  static class DeleteAllProductAsync extends AsyncTask<Void,Void,Void>{
//
//        private Product_DAO product_dao;
//
//        public DeleteAllProductAsync(Product_DAO product_dao) {
//            this.product_dao = product_dao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... Void) {
//
//            product_dao.DeleteAllProduct();
//
//            return null;
//        }
//    }






}
