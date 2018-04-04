package com.raspberyl.mynews.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private final List<Fragment> mFragment = new ArrayList<>();
    private final List<String> mTitles = new ArrayList<>();



    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }


    public void AddFragment (Fragment fragment, String title) {

        mFragment.add(fragment);
        mTitles.add(title);
    }
}
