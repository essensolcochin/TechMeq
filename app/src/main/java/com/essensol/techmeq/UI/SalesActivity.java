package com.essensol.techmeq.UI;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.essensol.techmeq.Adapters.SalesTabAdapter;
import com.essensol.techmeq.R;

public class SalesActivity extends Toolbar {

    TabLayout tabLayout;
    ViewPager TabItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_sales, contentFrameLayout);

        TabItem= findViewById(R.id.mPager);

        tabLayout = (TabLayout) findViewById(R.id.tab);

        tabLayout.addTab(tabLayout.newTab().setText("Credit"));
        tabLayout.addTab(tabLayout.newTab().setText("Cash"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        final SalesTabAdapter mAdapter = new SalesTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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



    }
}
