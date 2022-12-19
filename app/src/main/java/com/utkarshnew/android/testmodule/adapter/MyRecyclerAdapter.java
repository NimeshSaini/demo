package com.utkarshnew.android.testmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.utkarshnew.android.testmodule.model.QuestionDump;
import com.utkarshnew.android.testmodule.model.Questions2;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    public List<Question> questionBankList;
    public List<Questions2> questionBankList1;
    public List<QuestionDump> questionDumpList;
    String type;
    //   ArrayList<ViewSolutionResult> viewSolutionResults;
    private List<ViewModel> items;
    private int itemLayout;
    private Activity activity;
    private NumberPadOnClick numberPadOnClick;
    private int selectedPosition;
    boolean solution = false;

    public MyRecyclerAdapter(List<Question> questionBankList, Activity activity, List<ViewModel> items, int itemLayout, NumberPadOnClick numberPadOnClick) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.activity = activity;
        this.numberPadOnClick = numberPadOnClick;
        this.questionBankList = questionBankList;

    }


    public MyRecyclerAdapter(List<Questions2> questionBankList, Activity activity, int itemLayout, NumberPadOnClick numberPadOnClick, boolean solution) {
        this.itemLayout = itemLayout;
        this.activity = activity;
        this.numberPadOnClick = numberPadOnClick;
        this.questionBankList1 = questionBankList;
        this.solution = solution;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (solution) {
            holder.imageIVText.setVisibility(View.VISIBLE);
            holder.text.setText(String.valueOf(position + 1));
            final int finalPosition = position;


            if (activity instanceof TestBaseActivity || activity instanceof RevisionTest || activity instanceof TestCreateActivity) {
                holder.imageIVText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        numberPadOnClick.sendOnclickInd(finalPosition);
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
                    holder.imageIVText.setImageResource(R.drawable.bg_selected_question);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.blue));
                } else if (questionBankList1.get(finalPosition).getState().equalsIgnoreCase("not_visited")) {
                    holder.imageIVText.setImageResource(R.drawable.circle_skip);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));

                } else if (questionBankList1.get(finalPosition).getState().equalsIgnoreCase("unanswered")) {
                    holder.imageIVText.setImageResource(R.drawable.circle_unanswered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                } else if (!questionBankList1.get(finalPosition).getState().equalsIgnoreCase("not_visited") && !questionBankList1.get(finalPosition).getState().equalsIgnoreCase("unanswered")) {
                    holder.imageIVText.setImageResource(R.drawable.circle_answered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                }

                if (questionBankList1.get(finalPosition).getState().equalsIgnoreCase("marked_for_review")) {
                    holder.rlselected.setVisibility(View.VISIBLE);
                    holder.imageIVText.setImageResource(R.drawable.bg_selected_question);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.blue));
                } else if (questionBankList1.get(finalPosition).getState().equalsIgnoreCase("bookmarked"))
                    holder.rlselected.setVisibility(View.VISIBLE);
                else
                    holder.rlselected.setVisibility(View.GONE);


            } else {
                holder.imageIVText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        numberPadOnClick.sendOnclickInd(finalPosition);
                        //  ((ViewSolutionActivity) activity).changeTextOnNextAndPrevButton();
                    }
                });

                if (selectedPosition == finalPosition) {
                    holder.imageIVText.setImageResource(R.drawable.circle_unanswered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    holder.imageIVText.setImageResource(R.drawable.circle_answered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                }
            }
        } else {
            holder.imageIVText.setVisibility(View.VISIBLE);
            holder.text.setText(String.valueOf(position + 1));
            final int finalPosition = position;

/*        if (type.equalsIgnoreCase("1")){
            holder.questionTV.setVisibility(View.VISIBLE);
            holder.questionTV.setText(questionBankList.get(position).getQuestion());
        }else {
            holder.questionTV.setVisibility(View.GONE);
        }*/
            if (activity instanceof TestBaseActivity || activity instanceof RevisionTest || activity instanceof TestCreateActivity) {
                holder.imageIVText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        numberPadOnClick.sendOnclickInd(finalPosition);
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
                    holder.imageIVText.setImageResource(R.drawable.bg_selected_question);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.blue));
                } else if (questionBankList.get(finalPosition).getAnswerPosttion() == -1) {
                    holder.imageIVText.setImageResource(R.drawable.circle_skip);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));

                } else if (questionBankList.get(finalPosition).getAnswerPosttion() == 0) {
                    holder.imageIVText.setImageResource(R.drawable.circle_unanswered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                } else if (questionBankList.get(finalPosition).getAnswerPosttion() != -1 && questionBankList.get(finalPosition).getAnswerPosttion() != -0) {
                    holder.imageIVText.setImageResource(R.drawable.circle_answered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                }
/*        if (questionDumpList != null && questionDumpList.size() > 0) {
            if (questionDumpList.get(finalPosition).getReview().equalsIgnoreCase("1")) {
                holder.rlselected.setVisibility(View.VISIBLE);
            } else {
                holder.rlselected.setVisibility(View.GONE);
            }
        }else {*/
                if (questionBankList.get(finalPosition).isMarkForReview() && questionBankList.get(finalPosition).getAnswerPosttion() != -1 && questionBankList.get(finalPosition).getAnswerPosttion() != -0) {
                    holder.rlselected.setVisibility(View.VISIBLE);
                    holder.imageIVText.setImageResource(R.drawable.circle_answered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));

                } else if (questionBankList.get(finalPosition).isMarkForReview()) {
                    holder.rlselected.setVisibility(View.VISIBLE);
                    holder.imageIVText.setImageResource(R.drawable.bg_selected_question);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.blue));
                } else if (questionBankList.get(finalPosition).isIssaveMarkForReview())
                    holder.rlselected.setVisibility(View.VISIBLE);
                else
                    holder.rlselected.setVisibility(View.GONE);


            } else {
                holder.imageIVText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        numberPadOnClick.sendOnclickInd(finalPosition);

                        //  ((ViewSolutionActivity) activity).changeTextOnNextAndPrevButton();
                    }
                });

                if (selectedPosition == finalPosition) {
                    holder.imageIVText.setImageResource(R.drawable.circle_unanswered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    holder.imageIVText.setImageResource(R.drawable.circle_answered);
                    holder.text.setTextColor(activity.getResources().getColor(R.color.white));
                }
            }
        }
        // }
    }

    @Override
    public int getItemCount() {
        //if (activity instanceof TestBaseActivity)
        if (solution) {
            return questionBankList1.size();
        } else {
            return questionBankList.size();
        }
        //else
        //return viewSolutionResults.size();
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

        public ViewHolder(View itemView) {
            super(itemView);
            imageIVText = itemView.findViewById(R.id.imageIVText);
            text = itemView.findViewById(R.id.myImageViewText);
            rlselected = itemView.findViewById(R.id.rl_selected);
            questionTV = itemView.findViewById(R.id.questionTV);
        }
    }
}
