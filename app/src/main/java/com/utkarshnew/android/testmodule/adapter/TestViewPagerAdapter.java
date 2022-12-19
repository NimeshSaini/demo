package com.utkarshnew.android.testmodule.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.fragment.SC_TestSeriesFragment;
import com.utkarshnew.android.testmodule.model.Question;

import java.util.ArrayList;
import java.util.List;

public class TestViewPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    int num;
    public ArrayList<Fragment> mFragmentList = new ArrayList<>();
    public ArrayList<String> mFragmentTitleList = new ArrayList<>();
    FragmentManager fragmentManager;


    public TestViewPagerAdapter(FragmentManager fragmentManager, Activity context, ArrayList<Fragment> mFragmentList, int num) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.mFragmentList = mFragmentList;
        this.num = num;
    }


//    public Fragment getCurrentFragment() {
//        return mFragmentList.get();
//    }

//    @Override
//    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        super.setPrimaryItem(container, position, object);
//        mCurrentFragment = (Fragment) object;
//    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return mFragmentTitleList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }
    public void notifay(  ArrayList<Fragment> mFragmentList)
    {
        this.mFragmentList =mFragmentList;
        notifyDataSetChanged();
    }



}
