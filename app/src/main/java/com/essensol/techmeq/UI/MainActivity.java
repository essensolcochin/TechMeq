package com.essensol.techmeq.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.Adapters.HomeTabAdapter_;
import com.essensol.techmeq.Adapters.PurchaseListAdapter;
import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.Model.mProductModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Adapters.mTabAdapter;
import com.essensol.techmeq.Room.Databases.DAO.Sales_Header_DAO;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.Room.Databases.TaxModel;
import com.essensol.techmeq.ViewModel.TaxViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends Toolbar implements _AddProductDetailsDailog.OnCompleteListener  {

    GridLayoutManager layoutManager;
    RecyclerView products,purchase;

    PurchaseListAdapter purchaseListAdapter;
    ArrayList<String> items = new ArrayList<>();

    CardView bottom;

    TabLayout tabLayout;
    ViewPager TabItem;
    LinearLayout pay;

    TextView tot,vat;

    ArrayList<PurchaseModel> puchase = new ArrayList<>();
    List<mProductModel> newlist =new ArrayList<>();

//    UltimateTabLayout tabLayout;
    HomeTabAdapter_ adapter_;
//    ViewPager TabItem;
    String mName,mQty,mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        purchase =  findViewById(R.id.purchase);


        TabItem= findViewById(R.id.mPager);

        tabLayout = (TabLayout) findViewById(R.id.tabMode);

        bottom =findViewById(R.id.frame);

        pay =findViewById(R.id.pay);

        vat=findViewById(R.id.vat);

        tot=findViewById(R.id.tot);

        tabLayout.addTab(tabLayout.newTab().setText("Categories"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);


        int devicewidth = displaymetrics.widthPixels/2;

        int deviceheight = displaymetrics.heightPixels /4;
////        matchesAdapter_viewHolder.activityImage.getLayoutParams().width = devicewidth;
//
//        bottom.getLayoutParams().height = deviceheight;


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AddSale();
            }
        });




        final mTabAdapter mAdapter = new mTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),MainActivity.this);
        TabItem.setAdapter(mAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TabItem.setCurrentItem(tab.getPosition());
//
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        purchase.setLayoutManager(linearLayoutManager);
        purchase.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));




    }









    private void CalculateItemPrice()
    {
        try
        {
            double amount = 0, netAmount = 0, taxAmt = 0, totalAmount = 0, itemQty = 0, itemPurRate = 0, discPerc = 0, discountAmt = 0, taxPerc = 0, margin = 0, itemWAmt = 0, itemamountWOT = 0, cost = 0;


            //Amount
            amount = itemQty * itemPurRate;
            itemamountWOT = amount;
            double _ItemAmt = amount;

            //Tax Amt with net Amt
            taxAmt = ((itemamountWOT * taxPerc) / 100);
            itemWAmt = taxAmt;
            double _ItemTaxAmt = taxAmt;

            //Discount Amt
            //discountAmt = ((amount * discPerc) / 100).TruncateDoublePlaces(3);
            //_ItemDiscAmt = discountAmt;

            //Net Amt
            //netAmount = amount - discountAmt;
            //_ItemNetAmount = netAmount;



            //Total Amt
            totalAmount = itemamountWOT + itemWAmt;



        }
        catch (Exception ex)
        {

        }
    }


    @Override
    public void getProductDetails(List<mProductModel> list) {


        newlist=list;

        purchaseListAdapter = new PurchaseListAdapter(newlist, this);

        purchase.setAdapter(purchaseListAdapter);
        purchaseListAdapter.notifyDataSetChanged();





        int total = 0;
        double netAmnt = 0;

        for(int i=0;i<puchase.size();i++)
        {

            netAmnt=Double.parseDouble(puchase.get(i).getNetAmount());

            Log.e("percentage()","percentage "+netAmnt);

            double  percentage = (netAmnt/100.0f)*5 ;

            vat.setText(String.valueOf(percentage));

            Log.e("percentage()","percentage "+(netAmnt / 100));



            total = total+ Integer.parseInt(puchase.get(i).getNetAmount());
            tot.setText(Integer.toString(total));


        }




    }


    private  void AddSale()
    {



//        CompId = compId;
//        FinYearId = finYearId;
//        SaleNo = saleNo;
//        SaleDate = saleDate;
//        CustId = custId;
//        SubTotal = subTotal;
//        TaxAmt = taxAmt;
//        Discount = discount;
//        GrandTotal = grandTotal;
//        PaidAmt = paidAmt;


      new  AddProductAsync(OfflineDb.getInstance(this).sales_header_dao()).execute();





    }



    private  static class AddProductAsync extends AsyncTask<Void,Void,Void> {

        private Sales_Header_DAO header_dao;

        public AddProductAsync(Sales_Header_DAO header_dao) {
            this.header_dao = header_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

//            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//            String formattedDate = df.format(c);

            SalesHeader salesHeader =new SalesHeader(1,2,"ww",c,2,100.80,5.0,20.0,150.0,100);


            header_dao.AddSalesHeader(salesHeader);


           int Id = header_dao.getId();

            Log.e("LastAdded","Id _--> "+Id );


            return null;
        }
    }


}

