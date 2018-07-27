package com.hx.huixing.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * <br> Description 上面导航的tab
 * <br> Author: 谭俊
 * <br> PackageName com.hx.huixing.adapter
 * <br> Date: 2018/7/18
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public class BasePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> titles;
    ArrayList<Fragment> fragment;
    public BasePagerAdapter(FragmentManager fm, ArrayList<String> titles,ArrayList<Fragment> fragment) {
        super(fm);
        this.titles = titles;
        this.fragment = fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
