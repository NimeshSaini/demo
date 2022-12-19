package com.utkarshnew.android.courses.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Fragment.ExamPrepLayer1;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.Courses.ExamPrepItem;
import com.utkarshnew.android.Model.Courses.Lists;
import com.utkarshnew.android.Payment.PurchaseActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.home.Constants;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class ExamPrepLayer1Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity activity;
    ExamPrepItem examPrepItem;
    int DEFAULT_SPAN_COUNT = 2;
    ExamPrepLayer1 examPrepLayer1;
    CourseDetail singleStudy;
    boolean isCombo = false;
    String tileTypeAPI;
    String tileIdAPI;
    public Lists lists;
    String revertAPI;

    /*    public ExamPrepLayer1Adapter(Activity activity, ExamPrepItem examPrepItem, ExamPrepLayer1 fragment, CourseDetail singleStudy, boolean isCombo, String tileIdAPI, String tileTypeAPI, String revertAPI) {
            this.examPrepItem = examPrepItem;
            this.activity = activity;
            this.examPrepLayer1 = fragment;
            this.singleStudy = singleStudy;
            this.isCombo = isCombo;
            this.tileTypeAPI = tileTypeAPI;
            this.tileIdAPI = tileIdAPI;
            this.revertAPI = revertAPI;
        }*/
    public ExamPrepLayer1Adapter(Activity activity, ExamPrepItem examPrepItem, Lists lists, ExamPrepLayer1 fragment, CourseDetail singleStudy, boolean isCombo, String tileIdAPI, String tileTypeAPI, String revertAPI) {
        this.examPrepItem = examPrepItem;
        this.lists = lists;
        this.activity = activity;
        this.examPrepLayer1 = fragment;
        this.singleStudy = singleStudy;
        this.isCombo = isCombo;
        this.tileTypeAPI = tileTypeAPI;
        this.tileIdAPI = tileIdAPI;
        this.revertAPI = revertAPI;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(activity).inflate(R.layout.single_study_item_new, null);
        return new SingleStudyVideoListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SingleStudyVideoListHolder) holder).setData(examPrepItem.getList(), position);
    }

    @Override
    public int getItemCount() {
        return examPrepItem.getList().size();
    }

    public class SingleStudyVideoListHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        RelativeLayout lockRL;
        GifImageView liveIV;
        ImageView forward;
        TextView title;
        TextView count,countposition;
        LinearLayout study_single_itemLL;
        CardView ibt_single_sub_vd_RL;
        TextView no_data;

        public SingleStudyVideoListHolder(View itemView) {
            super(itemView);
            no_data = itemView.findViewById(R.id.no_data);
            ibt_single_sub_vd_RL = itemView.findViewById(R.id.ibt_single_sub_vd_RL);
            lockRL = itemView.findViewById(R.id.lockRL);
            courseImage = itemView.findViewById(R.id.courseImage);
            liveIV = itemView.findViewById(R.id.liveIV);
            forward = itemView.findViewById(R.id.forwardIV);
            title = itemView.findViewById(R.id.study_item_titleTV);
            count = itemView.findViewById(R.id.count);
            countposition = itemView.findViewById(R.id.position);
            study_single_itemLL = itemView.findViewById(R.id.study_single_itemLL);

            forward.setVisibility(View.VISIBLE);
        }

        @SuppressLint("SetTextI18n")
        public void setData(final ArrayList<Lists> list, final int position) {
            if (list != null && list.size() > 0) {
                ibt_single_sub_vd_RL.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.GONE);

                if (list.get(position).getIs_live() != null) {
                    if (list.get(position).getIs_live().equals("1")) {
                        liveIV.setVisibility(View.VISIBLE);
                    } else {
                        liveIV.setVisibility(View.GONE);
                    }
                } else {
                    liveIV.setVisibility(View.GONE);
                }

                count.setVisibility(View.GONE);
                count.setText("Total :" + list.get(position).getCount());

                countposition.setVisibility(View.VISIBLE);
                countposition.setText(""+(position+1));
              /*  if (!TextUtils.isEmpty(list.get(position).getImage_icon())) {
                    Helper.setThumbnailImage(activity, list.get(position).getImage_icon(), activity.getResources().getDrawable(R.drawable.square_thumbnail), courseImage);
                } else {
                    courseImage.setImageResource(R.drawable.square_thumbnail);
                }
*/
                title.setText(list.get(position).getTitle());
                if (!TextUtils.isEmpty(list.get(position).getC_code())) {
                    title.setTextColor(Color.parseColor(list.get(position).getC_code()));
                }

                if (TextUtils.isEmpty(list.get(position).getIs_locked())) {
                    list.get(position).setIs_locked("0");
                }
                if (singleStudy != null && singleStudy.getData().getCourseDetail() != null) {
                    if (singleStudy.getData().getCourseDetail().getIsPurchased().equals("1")) {
                        list.get(position).setIs_locked("0");
                    }
                }
                if (list.get(position).getIs_locked().equals("0")) {
                    lockRL.setVisibility(View.GONE);
                } else {
                    lockRL.setVisibility(View.VISIBLE);
                }

                study_single_itemLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list.get(position).getIs_locked().equalsIgnoreCase("1")) {
                            Intent intent = new Intent(activity, PurchaseActivity.class);
                            intent.putExtra("single_study", singleStudy);
                            Helper.gotoActivity(intent, activity);
                            return;
                        }

                        Intent examPrepLayer1 = new Intent(activity, CourseActivity.class);
                      /*  examPrepLayer1.putExtra(Const.SINGLE_STUDY_DATA, singleStudy);
                        examPrepLayer1.putExtra(Const.EXAMPREP, examPrepItem);
*/


                        Constants.examPrepItemNew =examPrepItem;
                        Constants.courseDetail =singleStudy;


                        examPrepLayer1.putExtra(Const.FRAG_TYPE, Const.EXAMPREPLAST);
                        examPrepLayer1.putExtra(Const.IS_COMBO, isCombo);
                        examPrepLayer1.putExtra(Const.LIST_SUBJECT, lists);
                        examPrepLayer1.putExtra(Const.TITLE, ((CourseActivity) activity).lists.getTitle());
                        examPrepLayer1.putExtra(Const.CONTENT_TYPE, ((CourseActivity) activity).contentType);
                        examPrepLayer1.putExtra(Const.TEST_TYPE, examPrepItem.getList().get(position).getCount());
                        examPrepLayer1.putExtra(Const.LIST, examPrepItem.getList().get(position));
                        examPrepLayer1.putExtra(Const.TILE_ID, tileIdAPI);
                        examPrepLayer1.putExtra(Const.TILE_TYPE, tileTypeAPI);
                        examPrepLayer1.putExtra(Const.REVERT_API, revertAPI);
                        examPrepLayer1.putExtra("serach_title", list.get(position).getTitle());
                        Helper.gotoActivity(examPrepLayer1, activity);
                    }
                });
            } else {
                ibt_single_sub_vd_RL.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
            }
        }
    }
}