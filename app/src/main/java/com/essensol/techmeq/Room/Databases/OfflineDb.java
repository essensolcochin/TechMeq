package com.essensol.techmeq.Room.Databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.essensol.techmeq.Room.Databases.DAO.CompanyMaster_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Customer_DAO;
import com.essensol.techmeq.Room.Databases.DAO.FinancialYear_DAO;
import com.essensol.techmeq.Room.Databases.DAO.ProductCategory_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sale_Item_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Tax_DAO;
import com.essensol.techmeq.Room.Databases.DAO.User_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Voucher_DAO;
import com.essensol.techmeq.Room.Databases.Entity.CompanyMaster;
import com.essensol.techmeq.Room.Databases.Entity.Customers;
import com.essensol.techmeq.Room.Databases.Entity.FinancialYear;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Databases.Entity.TaxModel;
import com.essensol.techmeq.Room.Databases.Entity.Users;
import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;
import com.essensol.techmeq.Room.DateTypeConverter;
import com.essensol.techmeq.Room.DecimalConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.PorterDuff.Mode.ADD;


@Database
(
        entities = {
                _dbExpenceVouchers.class
                , Products.class
                , Sales_Category.class
                , SalesHeader.class
                , SalesItem.class
                , FinancialYear.class
                , Users.class
                , Customers.class
                , CompanyMaster.class
                , TaxModel.class
        }
        ,version = 3
)
@TypeConverters({DateTypeConverter.class, DecimalConverter.class})


public abstract class OfflineDb extends RoomDatabase {




    private  static OfflineDb Instance;

    public abstract Voucher_DAO voucher_dao();

    public abstract Product_DAO product_dao();

    public abstract ProductCategory_DAO productCategory_dao();

    public abstract Sales_Header_DAO sales_header_dao();

    public  abstract Sale_Item_DAO sale_item_dao();

    public  abstract FinancialYear_DAO financialYear_dao();

    public  abstract User_DAO user_dao();

    public  abstract CompanyMaster_DAO companyMaster_dao();

    public abstract Tax_DAO tax_dao();

    public abstract Customer_DAO customer_dao();




    public  static  synchronized OfflineDb getInstance(Context context)
    {
        if(Instance==null)
        {
            Instance= Room.databaseBuilder(context.getApplicationContext(), OfflineDb.class,"offline db")
//                    .fallbackToDestructiveMigration()
                    .addMigrations(FROM_2_TO_3)
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





    private  static class PopulateAsync extends AsyncTask<Void,Void,Void> {
        private ProductCategory_DAO dao;

        private FinancialYear_DAO financialYear_dao;

        private Tax_DAO tax_dao;

        private Customer_DAO customer_dao;

        private CompanyMaster_DAO companyMaster_dao;

        private Sales_Header_DAO sales_header_dao;


        public PopulateAsync(OfflineDb db) {
            this.dao = db.productCategory_dao();
            this.financialYear_dao=db.financialYear_dao();
            this.tax_dao=db.tax_dao();
            this.customer_dao=db.customer_dao();
            this.companyMaster_dao =db.companyMaster_dao();
        }



        @Override
        protected Void doInBackground(Void... voids) {


//            CompanyMaster master= new CompanyMaster("123","Def","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A",true);
//
//            companyMaster_dao.AddCompany(master);

            Sales_Category model =new Sales_Category(0,"Select","null",false);

            dao.AddProductCategory(model);

            Date start=null,end=null;
            String dtStart = "2019-01-01T09:27:37Z";
            String dtEnd = "2019-12-31T09:27:37Z";

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                start = format.parse(dtStart);
                end=format.parse(dtEnd);

                Log.e("FInStart"," "+start);

                Log.e("FInEnd"," "+end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(start!=null&&end!=null)
            {
                FinancialYear year = new FinancialYear("2019",start,end,true,true);

                financialYear_dao.AddFinancialYear(year);

            }
                    //startDate -1st Jan
            //endDate -31 dec





            /**
             * Tax Callback
             */
            TaxModel tax=new TaxModel("5%",5.00);
            tax_dao.AddTax(tax);


//            Customers customers=new Customers(0,"CashCustomer","Nill","N/A",true);
//            customer_dao.AddCustomers(customers);



            return null;
        }
    }



    private static final Migration FROM_2_TO_3 = new Migration(2, 3) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Sales_Header "
                    + " ADD COLUMN Rounded INTEGER ");
        }
    };


}
