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
import com.utkarshnew.android.Coupon.Activity.CouponActivity;
import com.utkarshnew.android.Coupon.Activity.CouponPurchaseActivity;
import com.utkarshnew.android.Coupon.Activity.EligibleCoursesActivity;
import com.utkarshnew.android.Coupon.Models.Available;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CouponTypeAdapter extends RecyclerView.Adapter<CouponTypeAdapter.ViewHolder> {

    Context context;


    List<Available> availables;
    int view_type;


    public CouponTypeAdapter(Context context, List<Available> availables, int view_type) {
        this.context = context;
        this.availables = availables;
        this.view_type = view_type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = LayoutInflater.from(context).inflate(R.layout.available_coupon_items, parent, false);
                return new ViewHolder(view);

            case 1:
                View view1 = LayoutInflater.from(context).inflate(R.layout.redeemed_coupon_items, parent, false);
                return new RedeemedViewHolder(view1);

            case 2:
                View view2 = LayoutInflater.from(context).inflate(R.layout.expired_coupon_items, parent, false);
                return new ExpiredViewHolder(view2);
        }

        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        switch (getItemViewType(position)) {
            case 0:
                Glide.with(context)
                        .load(availables.get(position).getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.book_logo).error(R.drawable.book_logo))
                        .into(holder.ibt_single_vd_iv);
                holder.coupon_name.setText(availables.get(position).getCoupon_title());
                if (availables.get(position).getCoupon_type().equalsIgnoreCase("2"))
                    holder.coupon_discount.setText(availables.get(position).getCoupon_value() + "% Off  upto " + "\u20B9 " + availables.get(position).getMax_discount());
                else
                    holder.coupon_discount.setText(availables.get(position).getCoupon_value() + " INR Off");


                String start = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US).format(new Date(Long.parseLong(availables.get(position).getEnd()) * 1000));

                holder.coupon_validity.setText("Valid Till: " + start);

                if (availables.get(position).getCourses() != null) {
                    if (availables.get(position).getCoupon_for().equalsIgnoreCase("1")) {
                        holder.eligible_course.setText("Eligible For All Courses");

                    } else {
                        holder.eligible_course.setText(availables.get(position).getCourses().size() + " Eligible Courses");

                    }
                }

                holder.eligible_course.setOnClickListener(new OnSingleClickListener(() -> {
                    if (holder.eligible_course.getText().toString().equalsIgnoreCase("Eligible For All Courses")) {
                        ((CouponActivity) context).finish();

                        Toast.makeText(context, "Eligible For All Courses", Toast.LENGTH_SHORT).show();

                    } else {
                        if (availables.get(position).getExceed_message().equalsIgnoreCase("")) {
                            if (availables.get(position).getCourses() != null && availables.get(position).getCourses().size() > 0) {

                                Intent intent = new Intent(context, EligibleCoursesActivity.class);
                                intent.putExtra(Const.ELIGIBLE_COURSES, (Serializable) availables.get(position).getCourses());
                                intent.putExtra(Const.DISCOUNT, holder.coupon_discount.getText().toString());
                                intent.putExtra(Const.ID, availables.get(position).getId());
                                Helper.gotoActivity(intent, (Activity) context);

                            } else {
                                Toast.makeText(context, "Sorry. No Eligible Courses With This Coupon", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "" + availables.get(position).getExceed_message(), Toast.LENGTH_SHORT).show();
                        }


                    }

                    return null;
                }));


                break;

            case 1:
                RedeemedViewHolder redeemedViewHolder = (RedeemedViewHolder) holder;
                Glide.with(context)
                        .load(availables.get(position).getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.book_logo).error(R.drawable.book_logo))
                        .into(redeemedViewHolder.ibt_single_vd_iv);
                redeemedViewHolder.coupon_name.setText(availables.get(position).getCoupon_title());
                if (availables.get(position).getCoupon_type().equalsIgnoreCase("2"))
                    redeemedViewHolder.coupon_discount.setText(availables.get(position).getCoupon_value() + "% Off  upto " + "\u20B9 " + availables.get(position).getMax_discount());
                else
                    redeemedViewHolder.coupon_discount.setText(availables.get(position).getCoupon_value() + " INR Off");


                if (availables.get(position).getRedeem_json() != null && availables.get(position).getRedeem_json().size() > 0) {
                    String start1 = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US).format(new Date(availables.get(position).getRedeem_json().get(0).getCreated() * 1000));
                    redeemedViewHolder.coupon_redeemed.setText("Redeemed On: " + start1);
                    redeemedViewHolder.course_name.setVisibility(View.VISIBLE);
                    redeemedViewHolder.course_name.setText(availables.get(position).getRedeem_json().get(0).getC_title());
                } else {
                    redeemedViewHolder.course_name.setVisibility(View.GONE);
                    redeemedViewHolder.coupon_redeemed.setText("Redeemed On: ");
                }


                break;


            case 2:
                ExpiredViewHolder expiredViewHolder = (ExpiredViewHolder) holder;
                Glide.with(context)
                        .load(availables.get(position).getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.book_logo).error(R.drawable.book_logo))
                        .into(expiredViewHolder.ibt_single_vd_iv);
                expiredViewHolder.coupon_name.setText(availables.get(position).getCoupon_title());
                if (availables.get(position).getCoupon_type().equalsIgnoreCase("2"))
                    expiredViewHolder.coupon_discount.setText(availables.get(position).getCoupon_value() + "% Off  upto " + "\u20B9 " + availables.get(position).getMax_discount());
                else
                    expiredViewHolder.coupon_discount.setText(availables.get(position).getCoupon_value() + " INR Off");


                String start2 = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US).format(new Date(Long.parseLong(availables.get(position).getEnd()) * 1000));

                expiredViewHolder.coupon_expired.setText("Expired: " + start2);


                break;

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (view_type == 1) {
            return 0;
        } else if (view_type == 2) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return availables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView ibt_single_vd_iv;
        TextView coupon_name, coupon_discount, coupon_validity, eligible_course;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibt_single_vd_iv = itemView.findViewById(R.id.ibt_single_vd_iv);
            coupon_name = itemView.findViewById(R.id.coupon_name);
            coupon_discount = itemView.findViewById(R.id.coupon_discount);
            coupon_validity = itemView.findViewById(R.id.coupon_validity);
            eligible_course = itemView.findViewById(R.id.eligible_course);

        }
    }


    public class RedeemedViewHolder extends ViewHolder {

        RoundedImageView ibt_single_vd_iv;
        TextView coupon_name, coupon_discount, coupon_redeemed, course_name;

        public RedeemedViewHolder(@NonNull View itemView) {
            super(itemView);
            ibt_single_vd_iv = itemView.findViewById(R.id.ibt_single_vd_iv);
            coupon_name = itemView.findViewById(R.id.coupon_name);
            coupon_discount = itemView.findViewById(R.id.coupon_discount);
            coupon_redeemed = itemView.findViewById(R.id.coupon_redeemed);
            course_name = itemView.findViewById(R.id.course_name);
        }
    }


    public class ExpiredViewHolder extends ViewHolder {
        RoundedImageView ibt_single_vd_iv;
        TextView coupon_name, coupon_discount, coupon_expired;

        public ExpiredViewHolder(@NonNull View itemView) {
            super(itemView);
            ibt_single_vd_iv = itemView.findViewById(R.id.ibt_single_vd_iv);
            coupon_name = itemView.findViewById(R.id.coupon_name);
            coupon_discount = itemView.findViewById(R.id.coupon_discount);
            coupon_expired = itemView.findViewById(R.id.coupon_expired);

        }
    }
}
