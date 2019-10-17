package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.essensol.techmeq.Room.Databases.DAO.Tax_DAO;

@Database(entities = {TaxModel.class},version = 1)
public abstract  class TaxDb extends RoomDatabase {

    private  static  TaxDb Instance;

    public abstract Tax_DAO tax_dao();

    public  static  synchronized TaxDb getInstance(Context context)
    {
        if(Instance==null)
        {
            Instance= Room.databaseBuilder(context.getApplicationContext(),TaxDb.class,"Tax_Master_Table")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }




//
//        private static RoomDatabase.Callback roomCallBack =new RoomDatabase.Callback()
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
//        Tax_DAO tax_dao;
//
//        private PopulateAsyncTask(TaxDb taxDb) {
//            this. tax_dao= taxDb.tax_dao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            tax_dao.AddTax(new TaxModel("5%",5));
//            return null;
//        }
//    }


}
