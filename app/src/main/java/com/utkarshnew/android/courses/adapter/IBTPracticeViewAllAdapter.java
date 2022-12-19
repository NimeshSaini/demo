package com.utkarshnew.android.courses.adapter;

import static com.utkarshnew.android.Utils.Helper.buyNowCourses;
import static com.utkarshnew.android.Utils.Helper.isEMIPayImmediate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Interfaces.OnItemClickListener;
import com.utkarshnew.android.courses.Interfaces.OnMyCartItemListener;
import com.utkarshnew.android.Model.Course_Data;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.Courses.Basic;
import com.utkarshnew.android.Model.Courses.Course;
import com.utkarshnew.android.Model.Courses.EMIInfo;
import com.utkarshnew.android.Model.Courses.SinglestudyModel;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.HelperProgress;
import com.utkarshnew.android.Utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class IBTPracticeViewAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnMyCartItemListener mListener;
    private OnItemClickListener onItemClickListener;
    Context context;
    Activity activity;
    ArrayList<Course> coursesData;
    boolean aBoolean;
    ArrayList<Courselist> courseDataArrayList;
    boolean isCardFlag;
    String frag_type;
    Course_Data course_data_view;
    String leadPayType = "2";
    String leadPayNote = "";

    public IBTPracticeViewAllAdapter(Context context, ArrayList<Course> coursesData) {
        this.context = context;
        this.coursesData = coursesData;
    }

    public IBTPracticeViewAllAdapter(Activity activity, ArrayList<Courselist> courseDataArrayList, boolean b, boolean isCardFlag, String frag_type, OnMyCartItemListener mListener) {
        this.activity = activity;
        this.courseDataArrayList = courseDataArrayList;
        this.aBoolean = b;
        this.isCardFlag = isCardFlag;
        this.frag_type = frag_type;
        this.mListener = mListener;
    }

    public IBTPracticeViewAllAdapter(Activity activity, Course_Data course_data_view, ArrayList<Courselist> courseDataArrayList, boolean b, boolean isCardFlag, String frag_type, OnItemClickListener onItemClickListener) {
        this.activity = activity;
        this.courseDataArrayList = courseDataArrayList;
        this.aBoolean = b;
        this.isCardFlag = isCardFlag;
        this.frag_type = frag_type;
        this.course_data_view = course_data_view;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (aBoolean) {
            if (isCardFlag)
                return new MyViewHodler(LayoutInflater.from(activity).inflate(R.layout.ibt_single_item_exam_prep_viewall_verticale, null));
            else
                return new MyViewHodler1(LayoutInflater.from(activity).inflate(R.layout.ibt_single_item_exam_prep_viewall, null));

        } else {
            return new VIewAllViewHolder(LayoutInflater.from(context).inflate(R.layout.ibt_single_item_test_series1, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (aBoolean) {

            if (isCardFlag) {

                ((MyViewHodler) holder).setData(courseDataArrayList.get(position), position);
            } else {

                ((MyViewHodler1) holder).setData(courseDataArrayList.get(position), position);
            }

        } else {

            ((VIewAllViewHolder) holder).setData(coursesData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (aBoolean)
            return courseDataArrayList.size();
        else
            return coursesData.size();
    }

    protected class VIewAllViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title, description, price;
        RelativeLayout ibt_single_test_series_RL;

        public VIewAllViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.ibt_single_vd_iv);
            title = itemView.findViewById(R.id.ibt_single_vd_tv_title);
            description = itemView.findViewById(R.id.ibt_single_vd_tv_day);
            price = itemView.findViewById(R.id.ibt_single_vd_tv_likes);
            ibt_single_test_series_RL = itemView.findViewById(R.id.ibt_single_test_series_RL);
        }

        public void setData(final Course course) {
            Helper.setThumbnailImage(context, course.getCover_image(), context.getDrawable(R.mipmap.course_placeholder), thumbnail);
            title.setText(course.getTitle());
            if (course.getMrp().equalsIgnoreCase("0")) {
                price.setText(context.getResources().getString(R.string.free));
            } else {
                if (!TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getId())) {
                    if (course.getFor_dams().equalsIgnoreCase(course.getMrp())) {
                        price.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp());
                    } else {

                        String freeORnot = course.getFor_dams().equalsIgnoreCase("0") ? context.getResources().getString(R.string.free) : course.getFor_dams();
                        StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                        price.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp() + " " +
                                freeORnot, TextView.BufferType.SPANNABLE);
                        Spannable spannable = (Spannable) price.getText();
                        spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } else {
                    if (course.getNon_dams().equalsIgnoreCase(course.getMrp())) {
                        price.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp());
                    } else {
                        String freeORnot = course.getNon_dams().equalsIgnoreCase("0") ? context.getResources().getString(R.string.free) : course.getFor_dams();
                        StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                        price.setText(context.getResources().getString(R.string.rs) + " " + course.getMrp() + " " +
                                freeORnot, TextView.BufferType.SPANNABLE);
                        Spannable spannable = (Spannable) price.getText();
                        spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }

            ibt_single_test_series_RL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                    courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_COURSE);
                    courseList.putExtra(Const.COURSES, course);

                    activity.startActivity(courseList);
                }
            });
        }
    }

    public class MyViewHodler extends RecyclerView.ViewHolder {

        ImageView videoImage;
        RelativeLayout videoplayerRL;
        RelativeLayout tileRL;
        LinearLayout title_ll;
        CardView card_CV;
        GifImageView liveImageView;
        TextView titleTV, descriptionTV, price, validityTextTV, mrpCutTV;

        public MyViewHodler(View itemView) {
            super(itemView);
            videoImage = itemView.findViewById(R.id.ibt_single_vd_iv);
            titleTV = itemView.findViewById(R.id.ibt_current_affair_title);
            descriptionTV = itemView.findViewById(R.id.ibt_single_vd_tv_day);
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
            if (course.getHolderType() != null && course.getHolderType().equalsIgnoreCase("1")) {
                int width = (int) activity.getResources().getDimension(R.dimen.dp140);
                int height = (int) activity.getResources().getDimension(R.dimen.dp180);
                title_ll.setVisibility(View.VISIBLE);
                card_CV.setRadius(0);

                card_CV.setMaxCardElevation(0);
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(width, height);
                videoImage.setLayoutParams(layoutParams1);
                videoImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int width, height;
                        // Ensure you call it only once :
                        videoImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        // Here you can get the size :)
                        width = videoImage.getWidth();
                        height = videoImage.getHeight();
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
                        videoplayerRL.setLayoutParams(layoutParams);
                    }
                });

                Helper.setThumbnailImage(activity, course.getCover_image(), activity.getDrawable(R.mipmap.book_placeholder), videoImage);

            } else {
                int width = (int) activity.getResources().getDimension(R.dimen.dp180);
                //int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = (int) activity.getResources().getDimension(R.dimen.dp120);
                title_ll.setVisibility(View.GONE);

                card_CV.setMaxCardElevation(0);
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(width, height);
                videoImage.setLayoutParams(layoutParams1);
                videoImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int width, height;
                        // Ensure you call it only once :
                        videoImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        // Here you can get the size :)
                        width = videoImage.getWidth();
                        height = videoImage.getHeight();
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
                        videoplayerRL.setLayoutParams(layoutParams);
                    }
                });

                Helper.setThumbnailImage(activity, course.getCover_image(), activity.getDrawable(R.mipmap.course_placeholder), videoImage);
            }

            Log.d("response:", "course:" + new Gson().toJson(course) + course.getViewType());
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

            titleTV.setText(course.getTitle() + "\n");
            String descriptionText = course.getCourseAttribute().replace(",", " " +
                    activity.getResources().getString(R.string.blackdot) + " ");
            //descriptionTV.setText(descriptionText);
            descriptionTV.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(course.getColorCode())) {
                videoplayerRL.setBackgroundColor(Color.parseColor(course.getColorCode()));
            }

            tileRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 15) {
                        onItemClickListener.onItemClick(course_data_view.getCategory_info().getId(), course_data_view);
                    } else {
                        if (TextUtils.isEmpty(course.getMaintenanceText())) {
                            if (course.getHolderType() != null && course.getHolderType().equalsIgnoreCase("2")) {
                                Helper.GoWebViewPDFActivity(activity, course.getTitle(), course.getUrl());
                            } else if (course.getHolderType() != null && course.getHolderType().equalsIgnoreCase("3")) {
                                //Helper.GoToWebViewPDFActivity(activity, course.getTitle(), course.getUrl(), false);
                            } else {
                                Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                                courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                                courseList.putExtra(Const.COURSE_ID_MAIN, course.getId());
                                courseList.putExtra(Const.COURSE_PARENT_ID, "");
                                courseList.putExtra(Const.IS_COMBO, false);
                                activity.startActivity(courseList);
                            }
                        } else {
                            Helper.getCourseMaintanaceDialog(activity, "", course.getMaintenanceText());
                        }
                    }

                }
            });

            if (course.getValidity().equalsIgnoreCase("0")) {
                validityTextTV.setVisibility(View.GONE);
            } else {
                validityTextTV.setVisibility(View.GONE);
            }
            //else {
            if (course.getCourseSp().equalsIgnoreCase("0")) {
                price.setText(activity.getResources().getString(R.string.free));
                validityTextTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")
                        || course.getValidity().equalsIgnoreCase("1") ? activity.getString(R.string.month) : activity.getString(R.string.months)));
                mrpCutTV.setVisibility(View.GONE);
            } else {
                if (course.getCourseSp().equalsIgnoreCase(course.getMrp())) {
                    mrpCutTV.setVisibility(View.GONE);
                    validityTextTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")
                            || course.getValidity().equalsIgnoreCase("1") ? activity.getString(R.string.month) : activity.getString(R.string.months)));
                    price.setText(activity.getResources().getString(R.string.rs) + "" + course.getMrp() + "/-");
                } else {
                    price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getCourseSp(), "/-"));
                    mrpCutTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getMrp(), "/-"), TextView.BufferType.SPANNABLE);
                    StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                    Spannable spannable = (Spannable) mrpCutTV.getText();
                    mrpCutTV.setVisibility(View.VISIBLE);
                    spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    validityTextTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")
                            || course.getValidity().equalsIgnoreCase("1") ? activity.getString(R.string.month) : activity.getString(R.string.months)));
                }
            }
            //}
        }
    }

    public class MyViewHodler1 extends RecyclerView.ViewHolder {

        ImageView videoImage;
        LinearLayout remove_cart;
        LinearLayout payCompleteLL, payNowLL, payNowBtn;
        TextView next_payment_txt;
        TextView titleTV, descriptionTV, price, mrpCutTV, validityTextTV, expire_date;
        RelativeLayout videoplayerRL;
        RelativeLayout tileRL;

        public MyViewHodler1(View itemView) {
            super(itemView);
            videoImage = itemView.findViewById(R.id.ibt_single_vd_iv);
            remove_cart = itemView.findViewById(R.id.remove_cart);
            titleTV = itemView.findViewById(R.id.ibt_current_affair_title);
            descriptionTV = itemView.findViewById(R.id.ibt_single_vd_tv_day);
            validityTextTV = itemView.findViewById(R.id.validityTextTV);
            mrpCutTV = itemView.findViewById(R.id.mrpCutTV);
            expire_date = itemView.findViewById(R.id.expire_date);
            price = itemView.findViewById(R.id.priceTV);
            tileRL = itemView.findViewById(R.id.currentAffairRL);
            videoplayerRL = itemView.findViewById(R.id.videoplayerRL);
            payCompleteLL = itemView.findViewById(R.id.payCompleteLL);
            payNowLL = itemView.findViewById(R.id.payNowLL);
            next_payment_txt = itemView.findViewById(R.id.next_payment_txt);
            payNowBtn = itemView.findViewById(R.id.payNowBtn);
        }

        public SinglestudyModel getStudyData(Courselist courselist) {
            Basic basic = new Basic(courselist.getCover_image(),
                    courselist.getId(),
                    courselist.getTitle(),
                    courselist.getCourseSp(),
                    courselist.getDescHeaderImage(),
                    courselist.getCourseAttribute(),
                    courselist.getValidity(),
                    courselist.getPayment_type(),
                    courselist.getColorCode(),
                    "",
                    courselist.getMrp(),
                    courselist.getCourseAttribute(),
                    "",
                    "",
                    courselist.getIs_postal_available(),
                    courselist.getHolderType(),
                    ""
            );
            SinglestudyModel singleStudy = new SinglestudyModel(basic);
            return singleStudy;
        }

        public void setData(final Courselist course, final int position) {

            if (frag_type.equals(Const.MYCART)) {
                remove_cart.setVisibility(View.VISIBLE);
            } else {
                remove_cart.setVisibility(View.GONE);
            }

            if (course.getHolderType() != null && course.getHolderType().equalsIgnoreCase("1")) {
                int height = (int) activity.getResources().getDimension(R.dimen.dp100);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                videoImage.setLayoutParams(layoutParams);
            } else {
                //int width = (int) activity.getResources().getDimension(R.dimen.dp150);
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = (int) activity.getResources().getDimension(R.dimen.dp100);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                videoImage.setLayoutParams(layoutParams);
            }

            Log.d("response:", "course:" + new Gson().toJson(course));

            String descriptionText = course.getCourseAttribute().replace(",", " " +
                    activity.getResources().getString(R.string.blackdot) + " ");
            descriptionTV.setText(descriptionText);

            if (!TextUtils.isEmpty(course.getColorCode())) {
                videoplayerRL.setBackgroundColor(Color.parseColor(course.getColorCode()));
            }

      /*Ion.with(activity).load(course.getCoverImage()).asBitmap().setCallback(new FutureCallback<Bitmap>() {
        @Override
        public void onCompleted(Exception e, Bitmap result) {
          videoImage.setImageBitmap(result);
        }
      });*/

            Helper.setThumbnailImage(activity, course.getCover_image(), activity.getDrawable(R.mipmap.course_placeholder), videoImage);

            titleTV.setText(course.getTitle());

            if (frag_type.equals(Const.MYCOURSES)) {
                expire_date.setVisibility(View.VISIBLE);
                String t1 = HelperProgress.getFormatDateMillis(Long.parseLong(course.getExpiry_date()) * 1000);
                String ff = activity.getResources().getString(R.string.expire_on) + " " + "<font color='red'>" + t1 + "</font>";
                expire_date.setText(Html.fromHtml(ff));

                SinglestudyModel singleStudy = getStudyData(course);
                boolean isOpenEMIDialog = false;
                List<EMIInfo> emiInfoList = new ArrayList<>();
                if (singleStudy.getBasic().getEmiPrices() != null && singleStudy.getBasic().getEmiPrices().size() > 0) {
                    for (EMIInfo emiInfo : singleStudy.getBasic().getEmiPrices()) {
                        if (!emiInfo.getTxnStatus().equalsIgnoreCase("1")) {
                            emiInfoList.add(emiInfo);
                            isOpenEMIDialog = true;
                            break;
                        }
                    }
                }

                boolean isEMIvalidTo = false;
                String validToDate = "";
                String emiNumber = "";
                if (singleStudy.getBasic().getEmiPrices() != null && singleStudy.getBasic().getEmiPrices().size() > 0) {
                    for (EMIInfo emiInfo : singleStudy.getBasic().getEmiPrices()) {
                        if (emiInfo.getTxnStatus().equalsIgnoreCase("1")) {
                            emiNumber = emiInfo.getEmiNo();
                            validToDate = emiInfo.getValidTo();
                            isEMIvalidTo = true;
                        }
                    }
                }

                if (isOpenEMIDialog) {
                    if (isEMIvalidTo && isEMIPayImmediate(activity, validToDate)) {
                        //Toast.makeText(activity, ""+emiNumber, Toast.LENGTH_SHORT).show();
                        next_payment_txt.setTextColor(activity.getResources().getColor(R.color.red_met));
                        payNowBtn.setBackground(activity.getResources().getDrawable(R.drawable.round_corner_border_mycourse_immde_btn));
                        String validDate = HelperProgress.getFormatDateMillis(Long.parseLong(emiInfoList.get(0).getValidTo()) * 1000);
                        next_payment_txt.setText("Over Due Payment " + activity.getResources().getString(R.string.rs) + " " + emiInfoList.get(0).getEmiMrp() + "/- since " + validDate);
                    } else {
                        //Toast.makeText(activity, "Ooops! No date found!", Toast.LENGTH_SHORT).show();
                        next_payment_txt.setTextColor(activity.getResources().getColor(R.color.app_bg));
                        payNowBtn.setBackground(activity.getResources().getDrawable(R.drawable.round_corner_border_mycourse_btn));
                        String validDate = HelperProgress.getFormatDateMillis(Long.parseLong(emiInfoList.get(0).getValidTo()) * 1000);
                        next_payment_txt.setText("Next Payment " + activity.getResources().getString(R.string.rs) + " " + emiInfoList.get(0).getEmiMrp() + "/- due on " + validDate);
                    }

                    payCompleteLL.setVisibility(View.GONE);
                    payNowLL.setVisibility(View.VISIBLE);

                    payNowLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (emiInfoList != null && emiInfoList.size() > 0) {
                                DialogUtils.makeEmiPaymentDialog(activity, "Next EMI Details", "", emiInfoList, new DialogUtils.onDialogUtilsListItemClick() {
                                    @Override
                                    public void onListItemClick(int position) {
                                        buyNowCourses(activity, singleStudy);
                                        activity.finish();
                                    }
                                }, new DialogUtils.onDialogUtilsCancelClick() {
                                    @Override
                                    public void onCancelClick() {
                                        buyNowCourses(activity, singleStudy);
                                        activity.finish();
                                    }
                                });
                            } else {
                                Toast.makeText(activity, "No EMI Found!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    payCompleteLL.setVisibility(View.VISIBLE);
                    payNowLL.setVisibility(View.GONE);
                }
            } else {
                expire_date.setVisibility(View.GONE);
                payCompleteLL.setVisibility(View.GONE);
                payNowLL.setVisibility(View.GONE);
            }

            remove_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onMyCartDeleteClick(course);
                    }
          /*CartItems cartItems = new CartItems(SharedPreference.getInstance().getLoggedInUser().getId(), course.getId());
          Helper.addToMyCartCourses(activity, cartItems,false, false, null);

          courseDataArrayList.remove(position);
          notifyItemRemoved(position);
          notifyItemRangeChanged(position, courseDataArrayList.size());*/
                }
            });

            tileRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (frag_type.equals(Const.MYCART)) {
                        return;
                    }

                    if (TextUtils.isEmpty(course.getMaintenanceText())) {
                        if (course.getHolderType() != null && course.getHolderType().equalsIgnoreCase("2")) {
                            Helper.GoWebViewPDFActivity(activity, course.getTitle(), course.getUrl());
                        } else if (course.getHolderType() != null && course.getHolderType().equalsIgnoreCase("3")) {
                            //Helper.GoToWebViewPDFActivity(activity, course.getTitle(), course.getUrl(), false);
                        } else {
                            Intent courseList = new Intent(activity, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                            courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            courseList.putExtra(Const.COURSE_ID_MAIN, course.getId());
                            courseList.putExtra(Const.COURSE_PARENT_ID, "");
                            courseList.putExtra(Const.IS_COMBO, false);
                            activity.startActivity(courseList);
                        }
                    } else {
                        Helper.getCourseMaintanaceDialog(activity, "", course.getMaintenanceText());
                    }

                }
            });

            if (course.getValidity().equalsIgnoreCase("0")) {
                validityTextTV.setVisibility(View.GONE);
            } else {
                validityTextTV.setVisibility(View.GONE);
            }
            //else {
            if (course.getCourseSp().equalsIgnoreCase("0")) {
                price.setText(activity.getResources().getString(R.string.free));
                validityTextTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")
                        || course.getValidity().equalsIgnoreCase("1") ? activity.getString(R.string.month) : activity.getString(R.string.months)));
                mrpCutTV.setVisibility(View.GONE);
            } else {
                if (course.getCourseSp().equalsIgnoreCase(course.getMrp())) {
                    mrpCutTV.setVisibility(View.GONE);
                    validityTextTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")
                            || course.getValidity().equalsIgnoreCase("1") ? activity.getString(R.string.month) : activity.getString(R.string.months)));
                    price.setText(activity.getResources().getString(R.string.rs) + "" + course.getMrp() + "/-");
                } else {
                    price.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getCourseSp(), "/-"));
                    mrpCutTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.rs), course.getMrp(), "/-"), TextView.BufferType.SPANNABLE);
                    StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();
                    Spannable spannable = (Spannable) mrpCutTV.getText();
                    mrpCutTV.setVisibility(View.VISIBLE);
                    spannable.setSpan(STRIKE_THROUGH_SPAN, 2, new String(course.getMrp()).length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    validityTextTV.setText(String.format("%s %s %s", activity.getResources().getString(R.string.validity), course.getValidity(), course.getValidity().equalsIgnoreCase("0")
                            || course.getValidity().equalsIgnoreCase("1") ? activity.getString(R.string.month) : activity.getString(R.string.months)));
                }
            }
            //}

            if (frag_type.equals(Const.MYCOURSES)) {
                price.setText(activity.getResources().getString(R.string.purchased));
                mrpCutTV.setVisibility(View.GONE);
            }

        }

    }

}
