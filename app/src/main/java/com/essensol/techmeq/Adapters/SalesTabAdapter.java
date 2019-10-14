package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.essensol.techmeq.Tab_Fragments.AddProduct;
import com.essensol.techmeq.Tab_Fragments.CashSales;
import com.essensol.techmeq.Tab_Fragments.CreditSales;
import com.essensol.techmeq.Tab_Fragments.ProductList;
import com.essensol.techmeq.Tab_Fragments.ReportTab;

public class SalesTabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Context context;

    public SalesTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                CreditSales tab1 = new CreditSales();
                return tab1;
            case 1:
                CashSales tab2 = new CashSales();
                return tab2;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
