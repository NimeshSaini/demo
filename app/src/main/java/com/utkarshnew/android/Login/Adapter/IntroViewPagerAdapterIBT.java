package com.utkarshnew.android.Login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.utkarshnew.android.R;


/**
 * Created by NIKHIL on 4/8/2018.
 */

public class IntroViewPagerAdapterIBT extends PagerAdapter {
    Context context;
    String title;
    int[] layoutdata = {R.layout.ibt_intro_bluelayout1, R.layout.ibt_intro_orangelayout2, R.layout.ibt_intro_purplelayout3, R.layout.ibt_intro_greenlayout4};

    public IntroViewPagerAdapterIBT(Context context, String title) {
        this.context = context;
        this.title = title;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layoutdata[position], container, false);
        if (position == 0)
            ((TextView) v.findViewById(R.id.firstTitle)).setText(title);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return layoutdata.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
