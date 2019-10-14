package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.athbk.ultimatetablayout.IFTabAdapter;

public class HomeTabAdapter_ extends FragmentStatePagerAdapter implements IFTabAdapter {

   private int tabCount;
   private Context context;

    public HomeTabAdapter_(FragmentManager fm, int tabCount, Context context) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {


        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public String getTitle(int i) {
        return null;
    }

    @Override
    public int getIcon(int i) {
        return 0;
    }

    @Override
    public boolean isEnableBadge(int i) {
        return false;
    }
}
