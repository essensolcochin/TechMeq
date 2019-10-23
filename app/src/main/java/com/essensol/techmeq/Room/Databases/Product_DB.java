package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Products;

@Database(entities = {Products.class,_dbExpenceVouchers.class},version = 1)
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


}
