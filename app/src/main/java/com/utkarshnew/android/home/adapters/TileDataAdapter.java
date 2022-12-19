package com.utkarshnew.android.home.adapters;

import static com.utkarshnew.android.Utils.HelperProgress.getScreenWidth;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class TileDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    ArrayList<Courselist> courseDataArrayList;

    public TileDataAdapter(Activity activity, ArrayList<Courselist> courseDataArrayList) {
        this.activity = activity;
        this.courseDataArrayList = courseDataArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHodler(LayoutInflater.from(activity).inflate(R.layout.tile_data_item_adapter, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHodler) holder).setData(courseDataArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return courseDataArrayList.size();
    }

    public class MyViewHodler extends RecyclerView.ViewHolder {

        ImageView videoImage;
        RelativeLayout videoplayerRL;
        LinearLayout tileRL;
        LinearLayout title_ll;
        GifImageView liveImageView;
        TextView titleTV, price, validityTextTV, mrpCutTV;

        public MyViewHodler(View itemView) {
            super(itemView);
            videoImage = itemView.findViewById(R.id.ibt_single_vd_iv);
            titleTV = itemView.findViewById(R.id.ibt_current_affair_title);
            validityTextTV = itemView.findViewById(R.id.validityTextTV);
            mrpCutTV = itemView.findViewById(R.id.mrpCutTV);
            price = itemView.findViewById(R.id.priceTV);
            tileRL = itemView.findViewById(R.id.currentAffairRL);
            title_ll = itemView.findViewById(R.id.title_ll);
            videoplayerRL = itemView.findViewById(R.id.videoplayerRL);
            liveImageView = itemView.findViewById(R.id.liveIV);
        }

        public void setData(final Courselist course, int position) {

            int width = getScreenWidth() / 2;
            int height;
            boolean xlarge = ((activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            if (large) {
                height = (int) ((getScreenWidth() / 2));
            } else if (xlarge) {
                height = (int) ((getScreenWidth() / 2) + 300);
            } else {
                height = (int) ((getScreenWidth() / 2) + 100);
            }
            RelativeLayout.LayoutParams layoutParamsa = new RelativeLayout.LayoutParams(width, height);
            videoImage.setLayoutParams(layoutParamsa);
            videoImage.setClipToOutline(true);

            Glide.with(activity).load(R.mipmap.live).into(liveImageView);
            if (course.getIsLive() != null) {
                if (course.getIsLive().equals("1")) {
                    liveImageView.setVisibility(View.VISIBLE);
                } else {
                    liveImageView.setVisibility(View.GONE);
                }
            } else {
                liveImageView.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(course.getCover_image())) {
                Helper.setThumbnailImage(activity, course.getCover_image(), activity.getResources().getDrawable(R.mipmap.book_placeholder), videoImage);
            } else {
                videoImage.setImageResource(R.mipmap.book_placeholder);
            }

            if (!TextUtils.isEmpty(course.getColorCode())) {
                videoplayerRL.setBackgroundColor(Color.parseColor(course.getColorCode()));
            }

            titleTV.setText(course.getTitle());


            tileRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(course.getMaintenanceText())) {
                        Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                        courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        courseList.putExtra(Const.COURSE_ID_MAIN, course.getId());
                        courseList.putExtra(Const.COURSE_PARENT_ID, "");
                        courseList.putExtra(Const.IS_COMBO, false);
                        courseList.putExtra("course_name", course.getTitle());
                        Helper.gotoActivity(courseList, activity);
                    } else {
                        Helper.getCourseMaintanaceDialog(activity, "", course.getMaintenanceText());
                    }
                }
            });

            if (course.getValidity().equalsIgnoreCase("0")) {
                validityTextTV.setVisibility(View.GONE);
            } else {
                validityTextTV.setVisibility(View.VISIBLE);
            }

            if (course.getCourseSp().equalsIgnoreCase("0")) {
                price.setText(activity.getResources().getString(R.string.free));
                price.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                validityTextTV.setText(String.format("%s %s", activity.getResources().getString(R.string.validity), course.getValidity()));
                mrpCutTV.setVisibility(View.GONE);
            }

            else {
                if (course.getCourseSp().equalsIgnoreCase(course.getMrp())) {
                    mrpCutTV.setVisibility(View.GONE);
                    validityTextTV.setText(String.format("%s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")));
                    price.setText(activity.getResources().getString(R.string.rs) + "" + course.getMrp() + "/-");
                } else {
                    price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getCourseSp(), "/-"));
                    mrpCutTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getMrp(), "/-"), TextView.BufferType.SPANNABLE);
                    StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                    Spannable spannable = (Spannable) mrpCutTV.getText();
                    mrpCutTV.setVisibility(View.VISIBLE);
                    spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    validityTextTV.setText(String.format("%s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")));
                }
            }

        }
    }

}
