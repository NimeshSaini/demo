package com.utkarshnew.android.Coupon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.utkarshnew.android.Coupon.Models.CoursesCoupon;
import com.utkarshnew.android.Model.ExtendValidity;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appsquadz on 25/9/17.
 */

public class CouponPurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    public List<CoursesCoupon> couponArrayList;

    private BottomSheetDialog bottomSheetDialog;


    public CouponPurchaseAdapter(Context context, ArrayList<CoursesCoupon> couponArrayList, BottomSheetDialog bottomSheetDialog) {
        this.context = context;
        this.couponArrayList = couponArrayList;
        this.bottomSheetDialog = bottomSheetDialog;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_purchase_adapter, parent, false);
        return new StreamHolder(view);

    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sholder, final int position) {

        try {

            final StreamHolder holder = (StreamHolder) sholder;
            holder.price.setText(couponArrayList.get(holder.getAdapterPosition()).getCoupon().getCoupon_title());

            if (couponArrayList.get(holder.getAdapterPosition()).getCoupon().getCoupon_type().equalsIgnoreCase("1")) {
                if (!couponArrayList.get(holder.getAdapterPosition()).getCoupon().getMax_discount().equalsIgnoreCase("")) {
                    holder.days.setText(couponArrayList.get(holder.getAdapterPosition()).getCoupon().getMax_discount() + " ₹ Off");
                }
            } else {
                if (!couponArrayList.get(holder.getAdapterPosition()).getCoupon().getMax_discount().equalsIgnoreCase("")) {
                    holder.days.setText(couponArrayList.get(holder.getAdapterPosition()).getCoupon().getCoupon_value() + "% Off upto " + couponArrayList.get(holder.getAdapterPosition()).getCoupon().getMax_discount() + " ₹");

                }
            }


            if (couponArrayList.get(holder.getAdapterPosition()).isIs_select()) {
                holder.selected.setImageResource(R.mipmap.topup_selected);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.price.setTextColor(context.getResources().getColor(R.color.white, null));
                    holder.days.setTextColor(context.getResources().getColor(R.color.white, null));
                } else {
                    holder.days.setTextColor(context.getResources().getColor(R.color.white));
                    holder.price.setTextColor(context.getResources().getColor(R.color.white));
                }
                holder.select_plan.setImageResource(R.drawable.radio_select_plan);
            } else {
                holder.selected.setImageResource(R.mipmap.topup_default);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    holder.days.setTextColor(context.getResources().getColor(R.color.shadowMsgInput, null));
                    holder.price.setTextColor(context.getResources().getColor(R.color.black, null));
                } else {
                    holder.days.setTextColor(context.getResources().getColor(R.color.shadowMsgInput));
                    holder.price.setTextColor(context.getResources().getColor(R.color.black));
                }
                holder.select_plan.setImageResource(R.drawable.radio_deselct_plan);
            }
            holder.layout_select.setOnClickListener(new OnSingleClickListener(() -> {
                for (int j = 0; j < couponArrayList.size(); j++) {
                    if (couponArrayList.get(j).isIs_select()) {
                        couponArrayList.get(j).setIs_select(false);
                    }
                }
                couponArrayList.get(holder.getAdapterPosition()).setIs_select(true);
                notifyDataSetChanged();
                return null;
            }));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        if (couponArrayList != null && couponArrayList.size() > 0)
            return couponArrayList.size();
        else return 0;
    }


    public class StreamHolder extends RecyclerView.ViewHolder {
        TextView price, days;
        ImageView select_plan, selected;
        RelativeLayout layout_select;

        public StreamHolder(View itemView) {
            super(itemView);

            days = itemView.findViewById(R.id.days);
            price = itemView.findViewById(R.id.price);
            select_plan = itemView.findViewById(R.id.select_plan);
            selected = itemView.findViewById(R.id.selected);
            layout_select = itemView.findViewById(R.id.layout_select);


        }
    }

}
