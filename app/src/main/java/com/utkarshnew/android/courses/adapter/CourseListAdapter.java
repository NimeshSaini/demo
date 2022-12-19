package com.utkarshnew.android.courses.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.Model.Courses.Course;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.SharedPreference;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Course> coursesArrayList;
    String type;
    View.OnClickListener itemclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Course course = (Course) v.getTag();
            Intent courseList = new Intent(context, CourseActivity.class); // FRAG_TYPE, Const.SINGLE_COURSE from CourseListAdapter
            courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_COURSE);
            courseList.putExtra(Const.COURSES, course);
            context.startActivity(courseList);
        }
    };

    public CourseListAdapter(Context context, ArrayList<Course> coursesDataArrayList, String type) {
        this.context = context;
        this.coursesArrayList = coursesDataArrayList;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_course_ver, parent, false);
            return new CourseHolder(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_course_hor, parent, false);
            return new CourseHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (type.equals(Const.COURSE_CATEGORY))
            return 1;
        else if (type.equals(Const.MYCOURSES) || type.equals(Const.SEEALL_COURSE))
            return 2;
        else return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final CourseHolder holder1 = (CourseHolder) holder;
        holder1.courseTV.setText(coursesArrayList.get(position).getTitle());

        if (coursesArrayList.get(position).getMrp().equals("0")) {
            holder1.priceTV.setText("Free");
        } else {
            if (!TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getId())) {
                if (coursesArrayList.get(position).getFor_dams().equals(coursesArrayList.get(position).getMrp())) {
                    holder1.priceTV.setText(context.getResources().getString(R.string.rs) + " " + coursesArrayList.get(position).getMrp());
                } else {
                    StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                    holder1.priceTV.setText(context.getResources().getString(R.string.rs) + " " + coursesArrayList.get(position).getMrp() + " " +
                            coursesArrayList.get(position).getFor_dams(), TextView.BufferType.SPANNABLE);
                    Spannable spannable = (Spannable) holder1.priceTV.getText();
                    spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(coursesArrayList.get(position).getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                if (coursesArrayList.get(position).getNon_dams().equals(coursesArrayList.get(position).getMrp())) {
                    holder1.priceTV.setText(context.getResources().getString(R.string.rs) + " " + coursesArrayList.get(position).getMrp());
                } else {
                    StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                    holder1.priceTV.setText(context.getResources().getString(R.string.rs) + " " + coursesArrayList.get(position).getMrp() + " " +
                            coursesArrayList.get(position).getNon_dams(), TextView.BufferType.SPANNABLE);
                    Spannable spannable = (Spannable) holder1.priceTV.getText();
                    spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(coursesArrayList.get(position).getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
//            holder1.priceTV.setText(context.getResources().getString(R.string.rs) + " " + coursesArrayList.get(position).getMrp());


        holder1.learnerTV.setText((coursesArrayList.get(position).getLearner() +
                (coursesArrayList.get(position).getLearner().equals("1") ? Const.LEARNER : Const.LEARNERS)));
        holder1.ratingTV.setText(coursesArrayList.get(position).getRating());
        holder1.ratingRB.setRating(Float.parseFloat(coursesArrayList.get(position).getRating()));

        Helper.setThumbnailImage(context, coursesArrayList.get(position).getCover_image(), context.getResources().getDrawable(R.mipmap.camera_blue), holder1.imageIV);

        holder1.parentLL.setTag(coursesArrayList.get(position));
        holder1.parentLL.setOnClickListener(itemclickListener);

    }

    @Override
    public int getItemCount() {
        return coursesArrayList.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        TextView courseTV, priceTV, learnerTV, ratingTV;
        ImageView imageIV;
        RatingBar ratingRB;
        LinearLayout parentLL;

        public CourseHolder(View itemView) {
            super(itemView);

            courseTV = (TextView) itemView.findViewById(R.id.nameTV);
            priceTV = (TextView) itemView.findViewById(R.id.priceTV);
            learnerTV = (TextView) itemView.findViewById(R.id.learnerTV);
            ratingTV = (TextView) itemView.findViewById(R.id.ratingTV);
            ratingRB = (RatingBar) itemView.findViewById(R.id.ratingRB);
            imageIV = (ImageView) itemView.findViewById(R.id.imageIV);
            parentLL = (LinearLayout) itemView.findViewById(R.id.parentLL);
        }
    }


}
