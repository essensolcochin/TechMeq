package com.essensol.techmeq.UI;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.Adapters.HomeTabAdapter_;
import com.essensol.techmeq.Adapters.PurchaseListAdapter;
import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Adapters.mTabAdapter;

import java.util.ArrayList;

public class MainActivity extends Toolbar implements _AddProductDetailsDailog.OnCompleteListener  {

    GridLayoutManager layoutManager;
    RecyclerView products,purchase;

    PurchaseListAdapter purchaseListAdapter;
    ArrayList<String> items = new ArrayList<>();

    CardView bottom;

    TabLayout tabLayout;
    ViewPager TabItem;

    TextView tot;

    ArrayList<PurchaseModel> puchase = new ArrayList<>();


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


        tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Item"));
        tabLayout.addTab(tabLayout.newTab().setText("Report"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);


        int devicewidth = displaymetrics.widthPixels/2;

        int deviceheight = displaymetrics.heightPixels /3;
//        matchesAdapter_viewHolder.activityImage.getLayoutParams().width = devicewidth;

        bottom.getLayoutParams().height = deviceheight;



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




//        for (int i = 0; i < 40; i++) {
//            items.add("Item" + i);
//        }

//        adapter = new ProductsAdapter(items, MainActivity.this);
//
//        products.setAdapter(adapter);


    }

//    @SuppressLint("SetTextI18n")
//    @Override
//    public void getProductDetails(String name, String Qty, String amnt) {
//
////        Log.e("getProductDetails()","Called"+name+" "+Qty+" "+amnt);
//
//    }





    @Override
    public void getProductDetails(String name, String Qty, String rate, String amnt) {


        Log.e("getProductDetails()","Called");


        PurchaseModel model=new PurchaseModel(name,Qty,rate,amnt);
        puchase.add(model);

        purchaseListAdapter = new PurchaseListAdapter(puchase, this);

        purchase.setAdapter(purchaseListAdapter);
        purchaseListAdapter.notifyDataSetChanged();


        //         percentage = (float)((scored / total_marks) * 100);


        //        int total =0;
//        for(int i=0;i<puchase.size();i++)
//        {
//            total = total+ Integer.parseInt(puchase.get(i).getPrice());
//            tot.setText(Integer.toString(total));
//        }

    }
}

