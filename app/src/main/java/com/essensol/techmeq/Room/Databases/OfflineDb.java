package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;

import com.essensol.techmeq.Room.Databases.DAO.ProductCategory_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sale_Item_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Voucher_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;
import com.essensol.techmeq.Room.DateTypeConverter;


@Database
(
        entities = {_dbExpenceVouchers.class, Products.class, Sales_Category.class, SalesHeader.class, SalesItem.class}
        ,version = 1
)
@TypeConverters(DateTypeConverter.class)

public abstract class OfflineDb extends RoomDatabase {

    private  static OfflineDb Instance;

    public abstract Voucher_DAO voucher_dao();

    public abstract Product_DAO product_dao();

    public abstract ProductCategory_DAO productCategory_dao();

    public abstract Sales_Header_DAO sales_header_dao();

    public  abstract Sale_Item_DAO sale_item_dao();



    public  static  synchronized OfflineDb getInstance(Context context)
    {
        if(Instance==null)
        {
            Instance= Room.databaseBuilder(context.getApplicationContext(), OfflineDb.class,"offline db")
                    .fallbackToDestructiveMigration()
                    .addCallback(rdc)
                    .build();



        }
        return Instance;
    }

   private static RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {

            new PopulateAsync(Instance).execute();


        }

        public void onOpen(SupportSQLiteDatabase db) {
            // do something every time database is open
        }
    };


    private  static class PopulateAsync extends AsyncTask<Void,Void,Void>
    {
        private ProductCategory_DAO dao;

        public PopulateAsync(OfflineDb db) {
            this.dao = db.productCategory_dao();
        }



        @Override
        protected Void doInBackground(Void... voids) {


            Sales_Category model =new Sales_Category("Select","null",false);

            dao.AddProductCategory(model);
            return null;
        }
    }


}
