package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.essensol.techmeq.Tab_Fragments.AddProduct;
import com.essensol.techmeq.Tab_Fragments.ProductList;
import com.essensol.techmeq.Tab_Fragments.AddCategory;

public class mTabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Context context;

    public mTabAdapter(FragmentManager fm, int tabCount, Context context) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {

        if (i == 0) {
            ProductList tab1 = new ProductList();
            return tab1;
//            case 1:
//                AddProduct tab2 = new AddProduct();
//                return tab2;
//            case 2:
//                AddCategory tab3 = new AddCategory();
//                return tab3;
        }
        return null;

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
