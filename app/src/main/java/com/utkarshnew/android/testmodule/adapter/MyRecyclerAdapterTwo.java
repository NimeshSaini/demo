package com.utkarshnew.android.testmodule.adapter;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.CreateTest.Activity.TestCreateActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Webview.RevisionTest;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.interfaces.NumberPadOnClick;
import com.utkarshnew.android.testmodule.model.Question;

import java.util.List;

public class MyRecyclerAdapterTwo extends RecyclerView.Adapter<MyRecyclerAdapterTwo.ViewHolder> {

    private List<ViewModel> items;
    private int itemLayout;
    private Activity activity;
    private NumberPadOnClick numberPadOnClick;
    private int selectedPosition;
    public List<Question> questionBankList;
    String type;

    public MyRecyclerAdapterTwo(List<Question> questionBankList, Activity activity, List<ViewModel> items, int itemLayout, NumberPadOnClick numberPadOnClick, String type) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.activity = activity;
        this.numberPadOnClick = numberPadOnClick;
        this.questionBankList = questionBankList;
        this.type = type;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageIVText.setVisibility(View.VISIBLE);
        holder.text.setText(String.valueOf(position + 1));
        final int finalPosition = position;

        // Spanned spanned = Html.fromHtml(questionBankList.get(position).getQuestion());

        if (questionBankList.get(position).getQuestion() != null) {
            //String[] text = questionBankList.get(position).getQuestion().split("<img");
            String text = String.valueOf(Html.fromHtml(questionBankList.get(position).getQuestion().replaceAll("<img.+?>", "")));

            if (type.equalsIgnoreCase("1")) {
                holder.questionTV.setVisibility(View.VISIBLE);
                //holder.questionTV.setText(Html.fromHtml(text[0]));
                holder.questionTV.setText(Html.fromHtml(text));
            } else {
                holder.questionTV.setVisibility(View.GONE);
            }
        }
        holder.parentLLTestpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numberPadOnClick.sendOnclickInd(finalPosition);
                if (activity instanceof RevisionTest || activity instanceof TestBaseActivity || activity instanceof TestCreateActivity)
                    if (activity instanceof RevisionTest) {
                        ((RevisionTest) activity).changeTextOnNextAndPrevButton();
                    } else if (activity instanceof TestCreateActivity) {
                        ((TestCreateActivity) activity).changeTextOnNextAndPrevButton();
                    } else {
                        ((TestBaseActivity) activity).changeTextOnNextAndPrevButton();
                    }

            }
        });

        if (selectedPosition == finalPosition) {
            Log.e("FINAL_POSITION_1", String.valueOf(finalPosition));
            holder.imageIVText.setImageResource(R.drawable.bg_selected_question);
            holder.text.setTextColor(activity.getResources().getColor(R.color.blue));
        } else if (questionBankList.get(finalPosition).getAnswerPosttion() == -1) {
            Log.e("FINAL_POSITION_2", String.valueOf(finalPosition));
            holder.imageIVText.setImageResource(R.drawable.circle_skip);
            holder.text.setTextColor(activity.getResources().getColor(R.color.white));

        } else if (questionBankList.get(finalPosition).getAnswerPosttion() == 0) {
            Log.e("FINAL_POSITION_3", String.valueOf(finalPosition));
            holder.imageIVText.setImageResource(R.drawable.circle_unanswered);
            holder.text.setTextColor(activity.getResources().getColor(R.color.white));
        } else if (questionBankList.get(finalPosition).getAnswerPosttion() != -1 && questionBankList.get(finalPosition).getAnswerPosttion() != 0) {
            Log.e("FINAL_POSITION_4", String.valueOf(finalPosition));
            holder.imageIVText.setImageResource(R.drawable.circle_answered);
            holder.text.setTextColor(activity.getResources().getColor(R.color.white));
        }
        if (questionBankList.get(finalPosition).isMarkForReview() && questionBankList.get(finalPosition).getAnswerPosttion() != -1 && questionBankList.get(finalPosition).getAnswerPosttion() != -0) {
            holder.rlselected.setVisibility(View.VISIBLE);
            holder.imageIVText.setImageResource(R.drawable.circle_answered);
            holder.text.setTextColor(activity.getResources().getColor(R.color.white));

        } else if (questionBankList.get(finalPosition).isMarkForReview()) {
            holder.imageIVText.setImageResource(R.drawable.bg_selected_question);
            holder.text.setTextColor(activity.getResources().getColor(R.color.blue));
            holder.rlselected.setVisibility(View.VISIBLE);
        } else if (questionBankList.get(finalPosition).isIssaveMarkForReview())
            holder.rlselected.setVisibility(View.VISIBLE);
        else
            holder.rlselected.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return questionBankList.size();
    }

    public void setSelectePosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public int getselectePosition() {
        return selectedPosition;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageIVText;
        public TextView text, questionTV;
        private RelativeLayout rlselected;
        LinearLayout parentLLTestpad;

        public ViewHolder(View itemView) {
            super(itemView);
            imageIVText = itemView.findViewById(R.id.imageIVText);
            text = itemView.findViewById(R.id.myImageViewText);
            rlselected = itemView.findViewById(R.id.rl_selected);
            questionTV = itemView.findViewById(R.id.questionTV);
            parentLLTestpad = itemView.findViewById(R.id.parentLLTestpad);
        }
    }
}
