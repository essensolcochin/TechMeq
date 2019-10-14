package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.essensol.techmeq.Room.Databases.DAO.Voucher_DAO;


@Database(entities = {_dbExpenceVouchers.class},version = 1)
public abstract class ExpenceVoucher_DB extends RoomDatabase {

    private  static  ExpenceVoucher_DB Instance;

    public abstract Voucher_DAO voucher_dao();


    public  static  synchronized ExpenceVoucher_DB getInstance(Context context)
    {
        if(Instance==null)
        {
            Instance= Room.databaseBuilder(context.getApplicationContext(),ExpenceVoucher_DB.class,"Voucher_Master_Table")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }

}
