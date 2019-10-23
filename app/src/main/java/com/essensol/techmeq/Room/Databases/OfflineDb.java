package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.essensol.techmeq.Room.Databases.DAO.ProductCategory_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Voucher_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;


@Database(entities = {_dbExpenceVouchers.class, Products.class, Sales_Category.class},version = 1)


public abstract class OfflineDb extends RoomDatabase {

    private  static OfflineDb Instance;

    public abstract Voucher_DAO voucher_dao();

    public abstract Product_DAO product_dao();

    public abstract ProductCategory_DAO productCategory_dao();



    public  static  synchronized OfflineDb getInstance(Context context)
    {
        if(Instance==null)
        {
            Instance= Room.databaseBuilder(context.getApplicationContext(), OfflineDb.class,"offline db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }

}
