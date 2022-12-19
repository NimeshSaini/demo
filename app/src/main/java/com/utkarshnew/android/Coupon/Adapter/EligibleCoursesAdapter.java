package com.utkarshnew.android.Coupon.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.utkarshnew.android.Coupon.Activity.CouponPurchaseActivity;
import com.utkarshnew.android.Coupon.Models.CoursesCoupon;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.courses.Activity.CourseActivity;

import java.io.Serializable;
import java.util.List;

public class EligibleCoursesAdapter extends RecyclerView.Adapter<EligibleCoursesAdapter.EligibleCourseHolder> {

    Context context;
    List<CoursesCoupon> coursesCouponList;
    String discount;
    String id;

    public EligibleCoursesAdapter(Context context, List<CoursesCoupon> coursesCouponList, String discount, String id) {
        this.context = context;
        this.coursesCouponList = coursesCouponList;
        this.discount = discount;
        this.id = id;
    }


    @NonNull
    @Override
    public EligibleCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.eligible_courses_item, parent, false);
        return new EligibleCourseHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull EligibleCourseHolder holder, int position) {

        Glide.with(context)
                .load(coursesCouponList.get(position).getCover_image())
                .apply(new RequestOptions().placeholder(R.mipmap.course_placeholder).error(R.mipmap.course_placeholder))
                .into(holder.ibt_single_vd_iv);

        holder.coupon_name.setText(coursesCouponList.get(position).getTitle());
        holder.coupon_discount.setText(discount);

        if (coursesCouponList.get(position).getIs_purchased() != null && coursesCouponList.get(position).getIs_purchased().equalsIgnoreCase("1")) {
            holder.buy_now_btn.setTextColor(context.getResources().getColor(R.color.black));
            holder.buy_now_btn.setBackground(context.getResources().getDrawable(R.drawable.green_is_purchase));
            holder.buy_now_btn.setText("PURCHASED");

        } else {
            holder.buy_now_btn.setBackground(context.getResources().getDrawable(R.drawable.background_coupon_black));
            holder.buy_now_btn.setTextColor(context.getResources().getColor(R.color.white));
            holder.buy_now_btn.setText("BUY NOW");
        }

        holder.buy_now_btn.setOnClickListener(new OnSingleClickListener(() -> {
            if (coursesCouponList.get(position).getIs_purchased() != null && coursesCouponList.get(position).getIs_purchased().equalsIgnoreCase("1")) {
                Toast.makeText(context, "This Course Is Already Purchased You Can Directly Access To My Library.", Toast.LENGTH_SHORT).show();

            } else {
                Intent courseList = new Intent(context, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                courseList.putExtra(Const.COURSE_ID_MAIN, coursesCouponList.get(position).getId());
                courseList.putExtra(Const.COURSE_PARENT_ID, "");
                courseList.putExtra(Const.IS_COMBO, false);
                courseList.putExtra("course_name", coursesCouponList.get(position).getTitle());
                Helper.gotoActivity(courseList, (Activity) context);

             /*   Intent intent = new Intent(context, CouponPurchaseActivity.class);
                intent.putExtra(Const.COURSE_DATA, (Serializable) coursesCouponList.get(position));
                intent.putExtra(Const.DISCOUNT, discount);
                intent.putExtra(Const.ID, id);
                Helper.gotoActivity(intent, (Activity) context);*/
            }

            return null;
        }));
    }

    @Override
    public int getItemCount() {
        return coursesCouponList.size();
    }

    public class EligibleCourseHolder extends RecyclerView.ViewHolder {
        RoundedImageView ibt_single_vd_iv;
        TextView coupon_name, coupon_discount, buy_now_btn;

        public EligibleCourseHolder(@NonNull View itemView) {
            super(itemView);
            ibt_single_vd_iv = itemView.findViewById(R.id.ibt_single_vd_iv);
            coupon_name = itemView.findViewById(R.id.coupon_name);
            coupon_discount = itemView.findViewById(R.id.coupon_discount);
            buy_now_btn = itemView.findViewById(R.id.buy_now_btn);

        }
    }
}
