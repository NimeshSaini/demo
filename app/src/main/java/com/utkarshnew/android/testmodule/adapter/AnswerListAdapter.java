package com.utkarshnew.android.testmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.testmodule.model.QuestionDump;

import java.util.List;

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.AnswerListViewHolder> {

    Activity activity;
    List<QuestionDump> answerCount;
    //   List<QuestionDump> newAnswerCount;

    public AnswerListAdapter(Activity activity, List<QuestionDump> answerCount) {

        this.activity = activity;
        this.answerCount = answerCount;
        //  newAnswerCount = new ArrayList<>();
        //Log.e("AnswerListAdapter: ",answerCount.toString() );
    }

    @NonNull
    @Override
    public AnswerListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item_answer_list, viewGroup, false);
        return new AnswerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerListViewHolder answerListViewHolder, int position) {

        //   Log.e("POSITION",String.valueOf(position));
/*
        for (int i=answerCount.size()-1;i>=0;i--){
            newAnswerCount.add(answerCount.get(i));
        }
*/
        // answerListViewHolder.imgAnswerCount.setImageDrawable(Helper.GetDrawableWithCustomColor(String.valueOf(position+1), activity, activity.getResources().getColor(R.color.dark_green)));
        if (answerCount.get(position).getAnswer() != null) {
            if (answerCount.get(position).getAnswer().equals("")) {
                answerListViewHolder.imgAnswerCount.setImageDrawable(Helper.GetDrawableWithCustomColor(String.valueOf(position + 1), activity, activity.getResources().getColor(R.color.skipped)));
            } else {
                answerListViewHolder.imgAnswerCount.setImageDrawable(Helper.GetDrawableWithCustomColor(String.valueOf(position + 1), activity, activity.getResources().getColor(R.color.correct)));
                /*if (answerCount.get(position).getIsCorrect()!=null) {
                    if (answerCount.get(position).getIsCorrect().equals("1")) {
                        answerListViewHolder.imgAnswerCount.setImageDrawable(Helper.GetDrawableWithCustomColor(String.valueOf(position + 1), activity, activity.getResources().getColor(R.color.correct)));
                    } else {
                        answerListViewHolder.imgAnswerCount.setImageDrawable(Helper.GetDrawableWithCustomColor(String.valueOf(position + 1), activity, activity.getResources().getColor(R.color.incorrect)));
                    }
                }*/
            }
        }
    }

    @Override
    public int getItemCount() {
        return answerCount.size();
    }

    public class AnswerListViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAnswerCount;

        public AnswerListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnswerCount = itemView.findViewById(R.id.imgAnswerCount);
        }
    }
}
