package com.essensol.techmeq.Room.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.essensol.techmeq.Model.CustomerSpinnerModel;
import com.essensol.techmeq.Model.EditSaleModel;
import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.Model.LoginModel;
import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.Room.Databases.DAO.Customer_DAO;
import com.essensol.techmeq.Room.Databases.DAO.ProductCategory_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sale_Item_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.DAO.User_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Voucher_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Customers;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Databases.Entity.Users;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;

import java.util.Date;
import java.util.List;

public class mRepo {


    private Voucher_DAO voucher_dao;
    private LiveData<List<_dbExpenceVouchers>> AllVouchers;

    private Customer_DAO customer_dao;
    private LiveData<List<CustomerSpinnerModel>> GetCustNameAndId;
    private LiveData<List<Customers>>GetAllCustomers;

    private LiveData<List<Products>> AllProductForSale;





    private Sale_Item_DAO sale_item_dao;
//    private List<SalesItem> AllSales;

    private ProductCategory_DAO productCategory_dao;
    private LiveData<List<Sales_Category>> AllCategories;

    private Product_DAO product_dao;
    private LiveData<List<ProductModel>>AllProducts;

    private Sales_Header_DAO sales_header_dao;
    private LiveData<SalesHeader>GetInvoiceId;

    private User_DAO user_dao;
    private List<LoginModel>GetLoginresult;

    public mRepo(Application application) {

        OfflineDb db = OfflineDb.getInstance(application);
        voucher_dao=db.voucher_dao();
        product_dao=db.product_dao();
        productCategory_dao=db.productCategory_dao();
        sale_item_dao=db.sale_item_dao();
        customer_dao=db.customer_dao();
        sales_header_dao=db.sales_header_dao();
        user_dao=db.user_dao();

        AllVouchers=voucher_dao.GetAllVouchers();
        AllProducts=product_dao.GetAllProduct();
        AllCategories=productCategory_dao.GetProductCategory();
        GetCustNameAndId=customer_dao.GetCustNameAndId();
        GetAllCustomers=customer_dao.GetAllCustomers();


        GetInvoiceId =sales_header_dao.getLastProductLive();
        AllProductForSale=product_dao.AllProductForSale();
//        AllSales=sale_item_dao.();

    }



    public  List<Products>getExist(String name)
    {

        return product_dao.getDuplicateIfExist(name);
    }


    public  LiveData<List<Products>>getAllProductForSale()
    {

        return AllProductForSale;
    }

    public  LiveData<List<Customers>>getAllCustomers()
    {

        return GetAllCustomers;
    }

    public  List<Users>CheckLoginValidation(String uname,String pword)
    {

        return user_dao.CheckLogin(uname,pword);
    }





    /**

     Customer Master LiveData Function

     */

    public  LiveData<List<CustomerSpinnerModel>>getGetCustNameAndId()
    {
        return GetCustNameAndId;
    }


    /**

      Expense Voucher Table

     */


    public  void  AddVoucher(_dbExpenceVouchers vouchers)
    {

        new AddVoucherAsync(voucher_dao).execute(vouchers);
    }

    public  void  UpdateVoucher(_dbExpenceVouchers vouchers)
    {
        new UpdateVoucherAsync(voucher_dao).execute(vouchers);
    }

    public  void  DeleteVoucher(_dbExpenceVouchers vouchers)
    {
        new DeleteVoucherAsync(voucher_dao).execute(vouchers);
    }


    public  LiveData<List<Products>>getAllProduct_By_Category(int Id)
    {
        return product_dao.GetProductCategoryByID(Id);
    }

    public  List<ProductModel> getGetExists(String name)
    {
        return product_dao.alreadyinserted(name);
    }

    public  LiveData<SalesHeader> getInvoiceAndSaleID()
    {
        return GetInvoiceId;
    }

    public  LiveData<List<ItemReportModel>> getAllSalesReport(long d1,long d2)
    {
        return sales_header_dao.getSalesitemReport(d1 ,d2);
    }


    public  LiveData<List<_dbExpenceVouchers>> getAllVoucherReport(long date)
    {
        return voucher_dao.GetAllVouchers(date);
    }




    //For Product Table

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


    public  LiveData<List<ProductModel>>getAllProducts()
    {
        return AllProducts;
    }



    public  LiveData<List<_dbExpenceVouchers>>getAllVouchers()
    {
        return AllVouchers;
    }


    /**
     *Add Customer Async Exec
     */
   public void AddCustomer(Customers customers)
   {
       new AddCustomerAsync(customer_dao).execute(customers);
   }




    /**

      Sales_Category Table

     */


    public  void  AddProductCategory(Sales_Category category)
    {

        new AddProductCatAsync(productCategory_dao).execute(category);
    }

    public  void  UpdateProductCategory(Sales_Category category)
    {
        new UpdateProductCatAsync(productCategory_dao).execute(category);
    }

    public  void  DeleteProductCategory(Sales_Category category)
    {
        new DeleteProductCatAsync(productCategory_dao).execute(category);
    }


    public  LiveData<List<Sales_Category>>getAllProductsCategory()
    {
        return AllCategories;
    }



    // SaleItem


    public  List<EditSaleModel>getAllSales(int SaleId)
    {
        return sale_item_dao.GetAllSales(SaleId);
    }





    private  static class AddVoucherAsync extends AsyncTask<_dbExpenceVouchers,Void,Void> {

        private Voucher_DAO voucher_dao;

        public AddVoucherAsync(Voucher_DAO voucher_dao) {
            this.voucher_dao = voucher_dao;
        }

        @Override
        protected Void doInBackground(_dbExpenceVouchers... vouchers) {

            voucher_dao.AddVoucher(vouchers[0]);

            return null;
        }
    }



    private  static class UpdateVoucherAsync extends AsyncTask<_dbExpenceVouchers,Void,Void> {

        private Voucher_DAO voucher_dao;

        public UpdateVoucherAsync(Voucher_DAO voucher_dao) {
            this.voucher_dao = voucher_dao;
        }

        @Override
        protected Void doInBackground(_dbExpenceVouchers... vouchers) {

            voucher_dao.UpdateVoucher(vouchers[0]);

            return null;
        }
    }


    private  static class DeleteVoucherAsync extends AsyncTask<_dbExpenceVouchers,Void,Void> {

        private Voucher_DAO voucher_dao;

        public DeleteVoucherAsync(Voucher_DAO voucher_dao) {
            this.voucher_dao = voucher_dao;
        }

        @Override
        protected Void doInBackground(_dbExpenceVouchers... vouchers) {

            voucher_dao.DeleteVoucher(vouchers[0]);

            return null;
        }
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


    /**
     * Add Customer Asyctask
     */

    private  static class AddCustomerAsync extends AsyncTask<Customers,Void,Void> {

        private Customer_DAO customer_dao;

        public AddCustomerAsync(Customer_DAO customer_dao) {
            this.customer_dao = customer_dao;
        }

        @Override
        protected Void doInBackground(Customers... vouchers) {

            customer_dao.AddCustomers(vouchers[0]);

            return null;
        }
    }



    private  static class AddProductCatAsync extends AsyncTask<Sales_Category,Void,Void>{

        private ProductCategory_DAO productCategory_dao;

        public AddProductCatAsync(ProductCategory_DAO category_dao) {
            this.productCategory_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Sales_Category... categories) {

            productCategory_dao. AddProductCategory(categories[0]);

            return null;
        }
    }


    private  static class UpdateProductCatAsync extends AsyncTask<Sales_Category,Void,Void>{

        private ProductCategory_DAO productCategory_dao;

        public UpdateProductCatAsync(ProductCategory_DAO category_dao) {
            this.productCategory_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Sales_Category... categories) {

            productCategory_dao.UpdateProductCategory(categories[0]);

            return null;
        }
    }


    private  static class DeleteProductCatAsync extends AsyncTask<Sales_Category,Void,Void>{

        private ProductCategory_DAO productCategory_dao;

        public DeleteProductCatAsync(ProductCategory_DAO category_dao) {
            this.productCategory_dao = category_dao;
        }

        @Override
        protected Void doInBackground(Sales_Category... categories) {

            productCategory_dao.DeleteProductCategory(categories[0]);

            return null;
        }
    }





}
