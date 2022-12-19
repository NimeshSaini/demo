package com.utkarshnew.android.Webview;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.utkarshnew.android.Model.FlashData;
import com.utkarshnew.android.R;

import java.util.ArrayList;

public class Banner_ViewPager extends PagerAdapter {

    private final Context context;
    public ArrayList<FlashData> list_banners;
    AnimatorSet animatorSet_front, animatorSet_back;
    public static boolean is_front = false;

    @SuppressLint("ResourceType")
    public Banner_ViewPager(Context context, ArrayList<FlashData> list_banners) {
        this.context = context;
        this.list_banners = list_banners;

    }

    @Override
    public int getCount() {
        if (list_banners != null && !list_banners.isEmpty()) {
            return list_banners.size();
        } else {
            return 0;
        }
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.flash_view_adapter, container, false);
            RelativeLayout layout_front = view.findViewById(R.id.layout_front);
            TextView text_front = view.findViewById(R.id.text_front);
            TextView text_back = view.findViewById(R.id.text_back);
            RelativeLayout layout_back = view.findViewById(R.id.layout_back);
            RelativeLayout flashCover = view.findViewById(R.id.flashCover);
            TextView turn = view.findViewById(R.id.turn);

            animatorSet_front = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.front_animator);
            animatorSet_back = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.back_animator);
            float scale = context.getResources().getDisplayMetrics().density;
            layout_front.setCameraDistance(8000 * scale);
            text_front.setCameraDistance(8000 * scale);
            layout_back.setCameraDistance(8000 * scale);
            text_back.setCameraDistance(8000 * scale);

            text_front.setText(list_banners.get(position).getTerms());
            text_back.setText(list_banners.get(position).getDescription());

            flashCover.setOnClickListener(v -> {

                if (is_front) {
                    animatorSet_back.setTarget(layout_front);
                    animatorSet_front.setTarget(layout_back);
                    animatorSet_front.start();
                    animatorSet_back.start();
                    is_front = false;
                    list_banners.get(position).setIs_selectecd(false);
                    turn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rotate_right, 0, 0, 0);
                } else {
                    animatorSet_front.setTarget(layout_front);
                    animatorSet_back.setTarget(layout_back);
                    animatorSet_back.start();
                    animatorSet_front.start();
                    is_front = true;
                    turn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rotate_left, 0, 0, 0);
                }
            });


            container.addView(view);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

/*
    @Override
    public float getPageWidth(int position) {
        return 0.95f;
    }*/
}