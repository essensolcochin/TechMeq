package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;

@Database(entities = {Products.class},version = 1)
public abstract class Product_DB extends RoomDatabase {

    private  static  Product_DB Instance;

    public abstract Product_DAO product_dao();

    public  static  synchronized Product_DB getInstance(Context context)
    {
        if(Instance==null)
        {
            Instance= Room.databaseBuilder(context.getApplicationContext(),Product_DB.class,"Product_Master_Table")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }

//    private static RoomDatabase.Callback roomCallBack =new RoomDatabase.Callback()
//    {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            new PopulateAsyncTask(Instance).execute();
//
//
//        }
//    };
//
//    private   static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>
//    {
//        Product_DAO product_dao;
//
//        private PopulateAsyncTask(Product_DB product_db) {
//            this.product_dao = product_db.product_dao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            product_dao.AddProduct(new Products("Bread","food","100 AED","09-oct-2019"));
//            return null;
//        }
//    }

}
