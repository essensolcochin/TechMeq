package com.essensol.techmeq.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.essensol.techmeq.Adapters.ProductsAdapter;
import com.essensol.techmeq.DialogFragments.AddCategoryFragment;
import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.Adapters.HomeTabAdapter_;
import com.essensol.techmeq.Adapters.PurchaseListAdapter;
import com.essensol.techmeq.Model.CategoryModel;
import com.essensol.techmeq.Model.CustomerSpinnerModel;
import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.POS_Printer_Util.PortConfigurationActivity;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.Customer_DAO;
import com.essensol.techmeq.Room.Databases.DAO.FinancialYear_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sale_Item_DAO;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.ViewModel.ProductViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;



public class MainActivity extends Toolbar implements _AddProductDetailsDailog.OnCompleteListener  {


    //from tab

    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    ProductsAdapter adapter;
    List<CategoryModel> items = new ArrayList<>();
//    RecyclerView purchase;
//    Realm mRealm;
    private ProductViewModel model;

    ImageView add;

    ArrayAdapter<CustomerSpinnerModel> CustomerAdapter;

    List<CustomerSpinnerModel>customerSpinnerList=new ArrayList<>();



    GridLayoutManager layoutManager;
    RecyclerView products,purchase;

    PurchaseListAdapter purchaseListAdapter;
//    ArrayList<String> items = new ArrayList<>();

    CardView bottom;


    LinearLayout pay;

    private  static int FinYearId;

    private static   int SaleId;

    TextView tot,vat,taxable;

     ArrayList<PurchaseModel> puchase = new ArrayList<>();
    List<SalesItem> addSaleList =new ArrayList<>();


    int CustId;

    HomeTabAdapter_ adapter_;
    String mName,mQty,mPrice;

    private  ProgressDialog dialog;

    private SearchableSpinner CustomerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        purchase =  findViewById(R.id.purchase);


        CustomerName= findViewById(R.id.CustomerName);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabMode);

        bottom =findViewById(R.id.frame);

        pay =findViewById(R.id.pay);

        vat=findViewById(R.id.vat);

        tot=findViewById(R.id.tot);

        taxable=findViewById(R.id.taxable);






////from tab
        products = findViewById(R.id.products);

        add = findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm =getSupportFragmentManager();


                final AddCategoryFragment dialog= new AddCategoryFragment();

                dialog.show(fm,"TAG");
            }
        });

        layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.number_of_grid_items));
        products.setLayoutManager(layoutManager);
        products.setItemViewCacheSize(6);
        products.setDrawingCacheEnabled(true);
        products.setHasFixedSize(true);
        products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


//

        adapter = new ProductsAdapter(items,this);

        products.setAdapter(adapter);




        model = ViewModelProviders.of(this).get(ProductViewModel.class);

        model.GetAllProductCategory().observe(this, new Observer<List<Sales_Category>>() {
            @Override
            public void onChanged(@Nullable List<Sales_Category> sales_categories) {
                if(sales_categories !=null) {
                    items.clear();
                    for (int i=0;i<sales_categories.size();i++)
                    {
                        if(sales_categories.get(i).isStatus())
                        {

                            CategoryModel item =new CategoryModel(sales_categories.get(i).getProductCatId(),
                                    sales_categories.get(i).getProductCategory(),
                                    sales_categories.get(i).getImage(),
                                    sales_categories.get(i).isStatus());

                            items.add(item);
                        }
                    }


                    adapter.SetProducts(items);
                }

            }
        });

        model.GetCustNameAndId().observe(this, new Observer<List<CustomerSpinnerModel>>() {
            @Override
            public void onChanged(@Nullable List<CustomerSpinnerModel> customerSpinnerModels) {

                Log.e("Size"," "+customerSpinnerModels.size());
                customerSpinnerList =customerSpinnerModels;

                CustomerAdapter = new ArrayAdapter<CustomerSpinnerModel>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,customerSpinnerList);
                CustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                CustomerName.setAdapter(CustomerAdapter);


            }
        });



        CustomerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustId =customerSpinnerList.get(position).getCustId();
                Log.e("CustId Iddddd"," "+CustId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//////////////////

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);


        int devicewidth = displaymetrics.widthPixels/2;

        int deviceheight = displaymetrics.heightPixels /4;




        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(puchase.size()>0)
                {
                    AddSale();
                }

            }
        });







        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        purchase.setLayoutManager(linearLayoutManager);


//        new GetCustIdAsync(OfflineDb.getInstance(this).customer_dao()).execute();


    }






    private  void AddSale() {


        dialog =new ProgressDialog(this);
        dialog.setTitle("Adding Sale Record.");
        dialog.setMessage("Saving....");
        dialog.setCancelable(false);
        dialog.show();


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        new GetFinancialAsync(OfflineDb.getInstance(this).financialYear_dao()).execute();

        SalesHeader header =new SalesHeader(1,FinYearId,Integer.toString(SaleId+1),c,CustId
                ,Double.parseDouble(tot.getText().toString())
                ,Double.parseDouble(vat.getText().toString()),0,Double.parseDouble(tot.getText().toString()),0);

        new  AddSalesHeaderAsync(OfflineDb.getInstance(this).sales_header_dao()).execute(header);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                AddSaleItem();

            }
        }, 1000);


    }




    private  void AddSaleItem() {

        for (int i=0;i<puchase.size();i++)
        {

//            SaleId = saleId;
//            ProductId = productId;
//            Qty = qty;
//            Price = price;
//            Total = total;
//            TaxPercent = taxPercent;
//            TaxAmt = taxAmt;
//            LineTotal = lineTotal;



            PurchaseModel items =puchase.get(i);

            Log.e("Mainnnn"," "+items.getProductId());

            SalesItem model=new SalesItem(SaleId,items.getProductId(),items.getQty(),items.getRate(),Double.parseDouble(tot.getText().toString())
                                            ,5.0,Double.parseDouble(vat.getText().toString()),items.getLinetot());

            addSaleList.add(model);

        }

        try {
            String result = new AddSaleItemAsync(OfflineDb.getInstance(this).sale_item_dao()).execute(addSaleList).get();

            if(result.equalsIgnoreCase("Completed"))
            {
                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                puchase.clear();
                purchaseListAdapter.notifyDataSetChanged();
                taxable.setText("");
                vat.setText("");
                tot.setText("");
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    @Override
    public void getProductListItem(int Qty, int Product_Id, int ProductCatId, double TaxPercent, String ProductName, double Sales_Price, double rate) {


        double lineTot=((Sales_Price/100.0f)*5)+Qty;

       PurchaseModel model =new PurchaseModel(ProductName,Product_Id,Qty,rate,rate,lineTot);

       puchase.add(model);

        purchaseListAdapter = new PurchaseListAdapter(puchase, this);

        purchase.setAdapter(purchaseListAdapter);
        purchaseListAdapter.notifyDataSetChanged();





        double  total = 0;
        double netAmnt = 0;
        double  percentage=0;
        double  _taxable =0;

        for(int i=0;i<puchase.size();i++)
        {

            netAmnt=puchase.get(i).getNetAmount();

            _taxable =_taxable+netAmnt;

            taxable.setText(Double.toString(_taxable));

            percentage = percentage+(netAmnt/100.0f)*5 ;

            vat.setText(String.valueOf(percentage));

            Log.e("percentage()","percentage "+percentage);



            total = total+ puchase.get(i).getNetAmount()+percentage;
            tot.setText(Double.toString(total));


        }


    }


    private  static class AddSalesHeaderAsync extends AsyncTask<SalesHeader,Void,Void> {

        private Sales_Header_DAO header_dao;


        public AddSalesHeaderAsync(Sales_Header_DAO header_dao) {
            this.header_dao = header_dao;
        }

        @Override
        protected Void doInBackground(SalesHeader... salesHeaders) {


//            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//            String formattedDate = df.format(c);



            header_dao.AddSalesHeader(salesHeaders[0]);

            SaleId = header_dao.getId();

            Log.e("LastAdded","Id _--> "+SaleId );







            return null;
        }
    }


    private  static class AddSaleItemAsync extends AsyncTask<List<SalesItem>,Void,String> {

        private Sale_Item_DAO item_dao;

        public AddSaleItemAsync(Sale_Item_DAO Saleitemdao) {
            this.item_dao = Saleitemdao;
        }

        @Override
        protected String doInBackground(List<SalesItem>... items) {




           item_dao.AddSalesItem(items[0]);



//            Log.e("GetAllSales","Id _--> "+item_dao.GetAllSales().size() );







            String result="Completed";

            return result;
        }
    }



    private  static  class GetFinancialAsync extends AsyncTask<Void,Void,Void> {

        private FinancialYear_DAO dao;



        public GetFinancialAsync(FinancialYear_DAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            FinYearId=dao.GetMaxFinId();

            Log.e("GetFinancialAsync","Id --> "+FinYearId);

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            //do stuff

        }

    }


    /**
     *
     * Getting Bluetooth Devices
     */
//    protected void getDeviceList() {
//        // Initialize array adapters. One for already paired devices and
//        // one for newly discovered devices
//        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
//                R.layout.bluetooth_device_name_item);
//        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
//                R.layout.bluetooth_device_name_item);
//        lvPairedDevice.setAdapter(mPairedDevicesArrayAdapter);
//        lvPairedDevice.setOnItemClickListener(mDeviceClickListener);
//        lvNewDevice.setAdapter(mNewDevicesArrayAdapter);
//        lvNewDevice.setOnItemClickListener(mDeviceClickListener);
//        // Register for broadcasts when a device is discovered
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        this.registerReceiver(mFindBlueToothReceiver, filter);
//        // Register for broadcasts when discovery has finished
//        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        this.registerReceiver(mFindBlueToothReceiver, filter);
//        // Get the local Bluetooth adapter
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        // Get a set of currently paired devices
//        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//        // If there are paired devices, add each one to the ArrayAdapter
//        if (pairedDevices.size() > 0) {
//            tvPairedDevice.setVisibility(View.VISIBLE);
//            for (BluetoothDevice device : pairedDevices) {
//                mPairedDevicesArrayAdapter.add(device.getName() + "\n"
//                        + device.getAddress());
//            }
//        } else {
//            String noDevices = getResources().getText(R.string.none_paired)
//                    .toString();
//            mPairedDevicesArrayAdapter.add(noDevices);
//        }
//    }


    /**
     * Device Click Listeners
     *
     */

//    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
//            // Cancel discovery because it's costly and we're about to connect
//            mBluetoothAdapter.cancelDiscovery();
//            // Get the device MAC address, which is the last 17 chars in the View
//            String info = ((TextView) v).getText().toString();
//            String noDevices = getResources().getText(R.string.none_paired).toString();
//            String noNewDevice = getResources().getText(R.string.none_bluetooth_device_found).toString();
//            Log.i("tag", info);
//            if (! info.equals(noDevices) && ! info.equals(noNewDevice)) {
//                String address = info.substring(info.length() - 17);
//                // Create the result Intent and include the MAC address
//                Intent intent = new Intent();
//                intent.putExtra(PortConfigurationActivity.EXTRA_DEVICE_ADDRESS, address);
//                // Set result and finish this Activity
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//            }
//        }
//    };


}

