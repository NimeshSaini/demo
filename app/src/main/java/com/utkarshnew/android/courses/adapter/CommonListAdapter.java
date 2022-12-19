package com.utkarshnew.android.courses.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.Model.Courses.FAQ;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.HelperProgress;

import java.util.ArrayList;

public class CommonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater layoutInflater;
    String type;
    ArrayList<FAQ> faqArrayList;
    ArrayList<ResultTestSeries> resultTestSeriesArrayList;


    public CommonListAdapter(Context activity, String type, ArrayList<FAQ> faqArrayList) {
        this.context = activity;
        this.type = type;
        this.faqArrayList = faqArrayList;
        this.layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CommonListAdapter(Context activity, ArrayList<ResultTestSeries> resultTestSeriesArrayList) {
        this.context = activity;
        this.resultTestSeriesArrayList = resultTestSeriesArrayList;
        this.layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if (type != null && type.equals(Const.FAQ))
            return 0;
        else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_faq, parent, false);
            return new ViewHolder(view);
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_leaderboard, parent, false);
            return new LeaderboardHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sholder, @SuppressLint("RecyclerView") final int position) {

        if (type != null && type.equals(Const.FAQ)) {
            ViewHolder holder1 = (ViewHolder) sholder;
            holder1.setSingleFAQData(faqArrayList.get(position));
        } else {
            final LeaderboardHolder holder = (LeaderboardHolder) sholder;
            holder.nameTV.setText(resultTestSeriesArrayList.get(position).getTest_series_name());
//            Ion.with(context).load(resultTestSeriesArrayList.get(position).get)

            Helper.setThumbnailImage(context, resultTestSeriesArrayList.get(position).getImage(), context.getResources().getDrawable(R.mipmap.course_placeholder), holder.imageIV);

            /*Ion.with(context).load(resultTestSeriesArrayList.get(position).getImage())
                    .asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            if (e == null && result != null)
                                holder.imageIV.setImageBitmap(result);
                            else
                                holder.imageIV.setImageResource(R.mipmap.test_icon);
                        }
                    });*/

            holder.timeTV.setText(HelperProgress.getFormatDateMillis(Long.parseLong(resultTestSeriesArrayList.get(position).getCreation_time())));
            holder.seeResultLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent resultScreen = new Intent(context, QuizActivity.class);
                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                    resultScreen.putExtra(Const.STATUS, resultTestSeriesArrayList.get(position).getId());
                    resultScreen.putExtra(Const.PRACTICE, "");
                    context.startActivity(resultScreen);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (getItemViewType(0) == 0) {
            return faqArrayList.size();
        } else return resultTestSeriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView answertextTV, questiontextTV;
        private ImageView dropDownIV;
        private View dividerId;
        private LinearLayout mainLL, parentLL;

        public ViewHolder(View itemView) {
            super(itemView);
            questiontextTV = (TextView) itemView.findViewById(R.id.questiontextTV);
            dropDownIV = (ImageView) itemView.findViewById(R.id.dropDownIV);
            answertextTV = (TextView) itemView.findViewById(R.id.answertextTV);
            mainLL = itemView.findViewById(R.id.lowerViewItem);
            parentLL = itemView.findViewById(R.id.parentLL);
            dividerId = itemView.findViewById(R.id.dividerV);
        }

        public void setSingleFAQData(FAQ singlefaqdata) {

            parentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mainLL.getVisibility() == View.GONE) {
                        mainLL.setVisibility(View.VISIBLE);
                        dividerId.setVisibility(View.VISIBLE);
                        dropDownIV.setImageResource(R.mipmap.up_black);
                    } else {
                        mainLL.setVisibility(View.GONE);
                        dividerId.setVisibility(View.GONE);
                        dropDownIV.setImageResource(R.mipmap.down_black);
                    }
                }
            });
            questiontextTV.setText(singlefaqdata.getQuestion());
            answertextTV.setText(singlefaqdata.getDescription());
        }
    }

    public class LeaderboardHolder extends RecyclerView.ViewHolder {
        TextView nameTV, timeTV;
        RelativeLayout seeResultLL;
        ImageView imageIV;

        public LeaderboardHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            seeResultLL = (RelativeLayout) itemView.findViewById(R.id.seeResultLL);
            imageIV = (ImageView) itemView.findViewById(R.id.imageIV);
            timeTV = (TextView) itemView.findViewById(R.id.timeTV);
        }
    }

}
