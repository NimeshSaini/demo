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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Fragment.CommonFragForList;
import com.utkarshnew.android.Model.Courses.Course;
import com.utkarshnew.android.Model.Courses.CoursesData;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.SharedPreference;

import java.util.ArrayList;

public class AllCoursesAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CoursesData> coursesDatasArrayList;
    Context context;
    CommonFragForList commonFragForList;
    ViewHolder TagHolder1;
    int width = 0;
    LayoutInflater layoutInflater;
    CourseListAdapter courseListAdapter;


    View.OnClickListener onCourseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Course course = (Course) v.getTag();
            Intent courseList = new Intent(context, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
            courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_COURSE);
            courseList.putExtra(Const.COURSES, course);
            context.startActivity(courseList);
        }
    };
    View.OnClickListener onseeAllClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CoursesData coursesData = (CoursesData) v.getTag();
//            commonFragForList.setCourseListAdapter(coursesData.getCourse_list(), Integer.parseInt(v.getTag(1).toString()));
            Intent courseList = new Intent(context, CourseActivity.class);//FRAG_TYPE, Const.SEEALL_COURSE AllCoursesAdapter
            courseList.putExtra(Const.FRAG_TYPE, Const.SEEALL_COURSE);
            courseList.putExtra(Const.COURSE_CATEGORY, coursesData.getCategory_info());
            context.startActivity(courseList);
        }
    };

    public AllCoursesAdapater(Context activity, ArrayList<CoursesData> coursesDataArrayList, CommonFragForList commonFragForList) {
        this.context = activity;
        this.coursesDatasArrayList = coursesDataArrayList;
        this.commonFragForList = commonFragForList;
        this.layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if (coursesDatasArrayList.get(position).getCategory_info().getApp_view_type().equals("1"))
            return 0;
        else return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecourse_category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sholder, int position) {
        ViewHolder holder1 = (ViewHolder) sholder;
        CoursesData coursesData = coursesDatasArrayList.get(position);

        if (coursesData.getCourse_list().size() > 0) {
            holder1.seeAll.setVisibility(View.VISIBLE);
            holder1.courseCategoryTitle.setText(coursesData.getCategory_info().getName());

            holder1.courseLL.removeAllViews();
            if (getItemViewType(position) == 0) {
                int i = 0;   // to show only first three courses
                for (Course courses : coursesData.getCourse_list()) {
                    if (i == 3) break;
                    else {
                        holder1.courseLL.setVisibility(View.VISIBLE);
                        holder1.courseVerticalRV.setVisibility(View.GONE);
                        holder1.courseLL.setOrientation(LinearLayout.VERTICAL);
                        holder1.courseLL.addView(initCourseHorView(courses, getItemViewType(position)));
                        i++;
                    }
                }
            } else {
                //TODO OLD code to set only three item

//                int i = 0; // to show only first three courses
//                for (Course courses : coursesData.getCourse_list()) {
//                    if (i == 3) break;
//                    else {
//                        holder1.courseLL.setOrientation(LinearLayout.HORIZONTAL);
//                        holder1.courseLL.setWeightSum(3);
//                        width = holder1.courseLL.getWidth();
//                        holder1.courseLL.addView(initCourseHorView(courses, getItemViewType(position)));
//                        i++;
//                    }
//                }

                // TODO New Code to set All item
                courseListAdapter = new CourseListAdapter(context, coursesData.getCourse_list(), Const.COURSE_CATEGORY);
                holder1.courseLL.setVisibility(View.GONE);
                holder1.courseVerticalRV.setVisibility(View.VISIBLE);
                holder1.courseVerticalRV.setAdapter(courseListAdapter);
            }

            holder1.seeAll.setTag(coursesData);
            holder1.seeAll.setOnClickListener(onseeAllClickListener);
        } else {
            holder1.topViewItem.setVisibility(View.GONE);
        }
    }

    public LinearLayout initCourseHorView(Course course, int itemViewType) {
        TextView courseTV, priceTV, learnerTV, ratingTV;
        final ImageView imageIV;
        RatingBar ratingRB;
        switch (itemViewType) {
            case 0:
                LinearLayout view = (LinearLayout) View.inflate(context, R.layout.single_row_course_hor, null);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                courseTV = (TextView) view.findViewById(R.id.nameTV);
                priceTV = (TextView) view.findViewById(R.id.priceTV);
                learnerTV = (TextView) view.findViewById(R.id.learnerTV);
                ratingTV = (TextView) view.findViewById(R.id.ratingTV);
                ratingRB = (RatingBar) view.findViewById(R.id.ratingRB);
                imageIV = (ImageView) view.findViewById(R.id.imageIV);

                courseTV.setText(course.getTitle());

                if (course.getMrp().equals("0")) {
                    priceTV.setText("Free");
                } else {
                    if (!TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getId())) {
                        if (course.getFor_dams().equals(course.getMrp())) {
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp());
                        } else {
                            StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp() + " " +
                                    course.getFor_dams(), TextView.BufferType.SPANNABLE);
                            Spannable spannable = (Spannable) priceTV.getText();
                            spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else {
                        if (course.getNon_dams().equals(course.getMrp())) {
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp());
                        } else {
                            StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp() + " " +
                                    course.getNon_dams(), TextView.BufferType.SPANNABLE);
                            Spannable spannable = (Spannable) priceTV.getText();
                            spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }

                learnerTV.setText((course.getLearner() + ((course.getLearner().equals("1")) ? Const.LEARNER : Const.LEARNERS)));
                ratingTV.setText(course.getRating());
                ratingRB.setRating(Float.parseFloat(course.getRating()));

                Helper.setThumbnailImage(context, course.getCover_image(), context.getResources().getDrawable(R.mipmap.course_placeholder), imageIV);
                view.setTag(course);
                view.setOnClickListener(onCourseClickListener);
                return view;

            case 1:
                LinearLayout verView = (LinearLayout) View.inflate(context, R.layout.single_row_course_ver, null);
                LinearLayout.LayoutParams verlp = new LinearLayout.LayoutParams(width / 3, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                courseTV = (TextView) verView.findViewById(R.id.nameTV);
                priceTV = (TextView) verView.findViewById(R.id.priceTV);
                learnerTV = (TextView) verView.findViewById(R.id.learnerTV);
                ratingTV = (TextView) verView.findViewById(R.id.ratingTV);
                ratingRB = (RatingBar) verView.findViewById(R.id.ratingRB);
                imageIV = (ImageView) verView.findViewById(R.id.imageIV);

                courseTV.setText(course.getTitle());

                if (course.getMrp().equals("0")) {
                    priceTV.setText("Free");
                } else {
                    if (!TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getId())) {
                        if (course.getFor_dams().equals(course.getMrp())) {
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp());
                        } else {
                            StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp() + " " +
                                    course.getFor_dams(), TextView.BufferType.SPANNABLE);
                            Spannable spannable = (Spannable) priceTV.getText();
                            spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else {
                        if (course.getNon_dams().equals(course.getMrp())) {
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp());
                        } else {
                            StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                            priceTV.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp() + " " +
                                    course.getNon_dams(), TextView.BufferType.SPANNABLE);
                            Spannable spannable = (Spannable) priceTV.getText();
                            spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }

                learnerTV.setText((course.getLearner() + (course.getLearner().equals("1") ? Const.LEARNER : Const.LEARNERS)));
                ratingTV.setText(course.getRating());
                ratingRB.setRating(Float.parseFloat(course.getRating()));

                Helper.setThumbnailImage(context, course.getCover_image(), context.getResources().getDrawable(R.mipmap.course_placeholder), imageIV);
                /*Ion.with(context).load(course.getCover_image()).asBitmap().setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        if (e == null && result != null) imageIV.setImageBitmap(result);
                        else imageIV.setImageResource(R.mipmap.courses_blue);
                    }
                });*/
                verView.setLayoutParams(verlp);
                verView.setTag(course);
                verView.setOnClickListener(onCourseClickListener);
                return verView;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return coursesDatasArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout courseLL;
        private RelativeLayout topViewItem;
        private Button seeAll;
        private TextView courseCategoryTitle;
        RecyclerView courseVerticalRV;

        public ViewHolder(View itemView) {
            super(itemView);
            courseCategoryTitle = (TextView) itemView.findViewById(R.id.tv1);
            seeAll = (Button) itemView.findViewById(R.id.courseCatseeAllBtn);
            topViewItem = itemView.findViewById(R.id.topViewItem);
            courseLL = (LinearLayout) itemView.findViewById(R.id.coursesoptionLL);
            courseVerticalRV = itemView.findViewById(R.id.categoryRV);
            courseVerticalRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//            if (seeAll.getVisibility() == View.GONE) seeAll.setVisibility(View.VISIBLE);
            if (courseCategoryTitle.getVisibility() == View.GONE)
                courseCategoryTitle.setVisibility(View.VISIBLE);
        }
    }
}
