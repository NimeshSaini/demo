package com.utkarshnew.android.courses.overview.adapter;

import static com.utkarshnew.android.Utils.Helper.getHtmlUpdatedData;
import static com.utkarshnew.android.Utils.Helper.getHtmlUpdatedDatas;
import static com.utkarshnew.android.Utils.Helper.showWebData;
import static com.utkarshnew.android.Utils.Helper.showWebDatas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetailData;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.Overview.Description;
import com.utkarshnew.android.Model.Overview.OverviewData;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.LinearLayoutManagerWithSmoothScroller;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class OverviewRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    CourseDetailData basic;
    private OverviewData basicData;
    public int position;
    public boolean isHindi = false;
    public boolean isBoth = false;
    RecyclerView recyclerView;

    public OverviewRVAdapter(Context context, CourseDetailData basic, OverviewData basicData, RecyclerView recyclerView, boolean isHindi, boolean isBoth) {
        this.context = context;
        this.basic = basic;
        this.basicData = basicData;
        this.recyclerView = recyclerView;
        this.isHindi = isHindi;
        this.isBoth = isBoth;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.overview_header_layout, null);
        return new HeaderViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder sholder, @SuppressLint("RecyclerView") final int position) {
        this.position = position;
        final HeaderViewHolder headerViewHolder = (HeaderViewHolder) sholder;
        headerViewHolder.setData(basic, basicData, isHindi == false ? basicData.getData().getDescription() : basicData.getData().getDescription2(), isHindi, isBoth);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView titleOneTV, courseid;
        LinearLayout ceoOneLL, relatedCoursesLL, bookidLL;
        WebView descriptionOneTV, ceoTwoTV;
        TextView hindiTextView, engTextView;
        RecyclerView relatedCoursesRv;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            hindiTextView = itemView.findViewById(R.id.hindiTextView);
            engTextView = itemView.findViewById(R.id.engTextView);
            relatedCoursesRv = itemView.findViewById(R.id.relatedCoursesRv);
            titleOneTV = itemView.findViewById(R.id.titleOneTV);
            bookidLL = itemView.findViewById(R.id.bookidLL);
            courseid = itemView.findViewById(R.id.courseid);
            descriptionOneTV = itemView.findViewById(R.id.descriptionOneTV);
            descriptionOneTV.setBackgroundColor(Color.TRANSPARENT);
            descriptionOneTV.setHapticFeedbackEnabled(false);
            descriptionOneTV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            descriptionOneTV.setLongClickable(false);

            ceoOneLL = itemView.findViewById(R.id.ceoOneLL);
            relatedCoursesLL = itemView.findViewById(R.id.relatedCoursesLL);
            ceoTwoTV = itemView.findViewById(R.id.ceoTwoTV);
            ceoTwoTV.setBackgroundColor(Color.TRANSPARENT);
            ceoTwoTV.setHapticFeedbackEnabled(false);
            ceoTwoTV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            ceoTwoTV.setLongClickable(false);
        }

        public void setData(final CourseDetailData basicData, final OverviewData data, Description descriptions, boolean isHindi, boolean isBoth) {

            if (basicData.getIsPurchased().equalsIgnoreCase("1")) {
                bookidLL.setVisibility(View.VISIBLE);
                courseid.setText(TextUtils.isEmpty(basicData.getId()) ? "N/A" : basicData.getId());
            } else {
                bookidLL.setVisibility(View.GONE);
            }

            if (descriptions.getHeading() != null && !TextUtils.isEmpty(descriptions.getHeading())) {
                titleOneTV.setVisibility(View.VISIBLE);
                titleOneTV.setText(TextUtils.isEmpty(descriptions.getHeading()) ? "" : descriptions.getHeading());
            } else {
                titleOneTV.setVisibility(View.GONE);
            }

            if (descriptions.getData() != null && !TextUtils.isEmpty(descriptions.getData())) {
                descriptionOneTV.setVisibility(View.VISIBLE);
                showWebDatas((Activity) context, getHtmlUpdatedDatas(descriptions.getData()), descriptionOneTV);
            } else {
                descriptionOneTV.setVisibility(View.GONE);
            }

            if (isHindi) {
                if (data.getData().getCeoMessage() != null && !TextUtils.isEmpty(data.getData().getCeoMessage().getCeoMessageHindi())) {
                    ceoOneLL.setVisibility(View.VISIBLE);
                    showWebData((Activity) context, getHtmlUpdatedData(data.getData().getCeoMessage().getCeoMessageHindi()), ceoTwoTV);
                } else {
                    ceoOneLL.setVisibility(View.GONE);
                }
            } else {
                if (data.getData().getCeoMessage() != null && !TextUtils.isEmpty(data.getData().getCeoMessage().getCeoMessageEnglish())) {
                    ceoOneLL.setVisibility(View.VISIBLE);
                    showWebData((Activity) context, getHtmlUpdatedData(data.getData().getCeoMessage().getCeoMessageEnglish()), ceoTwoTV);
                } else {
                    ceoOneLL.setVisibility(View.GONE);
                }
            }

            if (data.getData().getRelatedCourses() != null && data.getData().getRelatedCourses().size() > 0) {
                relatedCoursesLL.setVisibility(View.VISIBLE);
                RelatedDataAdapter relatedDataAdapter = new RelatedDataAdapter((Activity) context, data.getData().getRelatedCourses());
                relatedCoursesRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManagerWithSmoothScroller.HORIZONTAL, false));
                relatedCoursesRv.setAdapter(relatedDataAdapter);
                relatedCoursesRv.setNestedScrollingEnabled(false);
            } else {
                relatedCoursesLL.setVisibility(View.GONE);
            }

            if (isHindi) {
                signInClick(hindiTextView, engTextView, isBoth);
            } else {
                signUpClick(hindiTextView, engTextView, isBoth);
            }

            hindiTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isBoth = false;
                    if (data.getData().getVisibility().equalsIgnoreCase("1")) {
                        isBoth = false;
                    } else if (data.getData().getVisibility().equalsIgnoreCase("2")) {
                        isBoth = false;
                    } else {
                        isBoth = true;
                    }
                    signInClick(hindiTextView, engTextView, isBoth);
                    OverviewRVAdapter overviewRVAdapter = new OverviewRVAdapter(context, basic, data, recyclerView, true, isBoth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(overviewRVAdapter);
                }
            });

            engTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isBoth = false;
                    if (data.getData().getVisibility().equalsIgnoreCase("1")) {
                        isBoth = false;
                    } else if (data.getData().getVisibility().equalsIgnoreCase("2")) {
                        isBoth = false;
                    } else {
                        isBoth = true;
                    }
                    signUpClick(hindiTextView, engTextView, isBoth);
                    OverviewRVAdapter overviewRVAdapter = new OverviewRVAdapter(context, basic, data, recyclerView, false, isBoth);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(overviewRVAdapter);
                }
            });
        }
    }

    public void signUpClick(TextView hindiTextView, TextView engTextView, boolean isBoth) {
        engTextView.setTextColor(context.getResources().getColor(R.color.blackApp));
        engTextView.setBackground(context.getResources().getDrawable(R.drawable.round_black_bg));

        hindiTextView.setTextColor(context.getResources().getColor(R.color.black_overlay));
        hindiTextView.setBackground(context.getResources().getDrawable(R.drawable.round_white_bg));

        if (isBoth) {
            engTextView.setVisibility(View.VISIBLE);
            hindiTextView.setVisibility(View.VISIBLE);
        } else {
            //engTextView.setVisibility(View.VISIBLE);
            engTextView.setVisibility(View.GONE);
            hindiTextView.setVisibility(View.GONE);
        }
    }

    public void signInClick(TextView hindiTextView, TextView engTextView, boolean isBoth) {
        hindiTextView.setTextColor(context.getResources().getColor(R.color.blackApp));
        hindiTextView.setBackground(context.getResources().getDrawable(R.drawable.round_black_bg));

        engTextView.setTextColor(context.getResources().getColor(R.color.black_overlay));
        engTextView.setBackground(context.getResources().getDrawable(R.drawable.round_white_bg));

        if (isBoth) {
            hindiTextView.setVisibility(View.VISIBLE);
            engTextView.setVisibility(View.VISIBLE);
        } else {
            //hindiTextView.setVisibility(View.VISIBLE);
            hindiTextView.setVisibility(View.GONE);
            engTextView.setVisibility(View.GONE);
        }
    }

    public class RelatedDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Activity activity;
        ArrayList<Courselist> courseDataArrayList;

        public RelatedDataAdapter(Activity activity, ArrayList<Courselist> courseDataArrayList) {
            this.activity = activity;
            this.courseDataArrayList = courseDataArrayList;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RelatedDataAdapter.MyViewHodler(LayoutInflater.from(activity).inflate(R.layout.related_course_item_adapter, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RelatedDataAdapter.MyViewHodler) holder).setData(courseDataArrayList.get(position), position);
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
            CardView card_CV;
            GifImageView liveImageView;
            TextView titleTV, price, validityTextTV, mrpCutTV;

            public MyViewHodler(View itemView) {
                super(itemView);
                videoImage = itemView.findViewById(R.id.ibt_single_vd_iv);
                titleTV = itemView.findViewById(R.id.ibt_current_affair_title);
                validityTextTV = itemView.findViewById(R.id.validityTextTV);
                mrpCutTV = itemView.findViewById(R.id.mrpCutTV);
                price = itemView.findViewById(R.id.priceTV);
                card_CV = itemView.findViewById(R.id.card_CV);
                tileRL = itemView.findViewById(R.id.currentAffairRL);
                title_ll = itemView.findViewById(R.id.title_ll);
                videoplayerRL = itemView.findViewById(R.id.videoplayerRL);
                liveImageView = itemView.findViewById(R.id.liveIV);
            }

            public void setData(final Courselist course, int position) {
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

                titleTV.setText(course.getTitle());

                if (!TextUtils.isEmpty(course.getColorCode())) {
                    videoplayerRL.setBackgroundColor(Color.parseColor(course.getColorCode()));
                }

                tileRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(course.getMaintenanceText())) {
                            ((CourseActivity) activity).isrelated = 1;
                            Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                            courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            courseList.putExtra(Const.COURSE_ID_MAIN, course.getId());
                            courseList.putExtra(Const.COURSE_PARENT_ID, "");
                            courseList.putExtra(Const.IS_COMBO, false);
                            courseList.putExtra("course_name", course.getTitle());
                            activity.startActivity(courseList);
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
                } else {
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

}
