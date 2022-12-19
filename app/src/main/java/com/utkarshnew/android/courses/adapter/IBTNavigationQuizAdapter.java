package com.utkarshnew.android.courses.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.courses.Fragment.Quiz;
import com.utkarshnew.android.Model.Courses.quiz.Questions;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.R;

import java.util.ArrayList;


public class IBTNavigationQuizAdapter extends RecyclerView.Adapter<IBTNavigationQuizAdapter.NavigationHolder> {

    Context context;
    QuizModel quiz;
    ArrayList<Questions> resultTestSeriesArrayList;
    Quiz fragment;
    int currentQues;

    public IBTNavigationQuizAdapter(Activity quizActivity, QuizModel quiz, Fragment fragment) {
        this.context = quizActivity;
        this.quiz = quiz;
        if (fragment instanceof Quiz)
            this.fragment = (Quiz) fragment;

    }

    public IBTNavigationQuizAdapter(Activity quizActivity, ArrayList<Questions> resultTestSeriesArrayList) {
        this.context = quizActivity;
        this.resultTestSeriesArrayList = resultTestSeriesArrayList;
    }

    @Override
    public NavigationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_navigation, parent, false);
        return new NavigationHolder(view);
    }

    @Override
    public void onBindViewHolder(final NavigationHolder holder, @SuppressLint("RecyclerView") final int position) {
        currentQues = fragment.currentQues;
        final TextDrawable textDrawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .textColor(R.color.black)
                .endConfig()
                .buildRound(String.valueOf(position + 1), context.getResources().getColor(R.color.white));

        final TextDrawable blueDrawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .textColor(R.color.darkestblue)
                .endConfig()
                .buildRound(String.valueOf(position + 1), context.getResources().getColor(R.color.white));


        final TextDrawable filledDrawableCorrect = TextDrawable.builder()
                /* .beginConfig()
                 .withBorder(4)
                 .endConfig()*/
                .buildRound(String.valueOf(position + 1), context.getResources().getColor(R.color.green));
        final TextDrawable filledDrawableInCorrect = TextDrawable.builder()
                /*.beginConfig()
                .withBorder(4)
                .endConfig()
                */.buildRound(String.valueOf(position + 1), context.getResources().getColor(R.color.ibt_red));
        if (quiz != null) {
            if (quiz.getQuestion_bank().get(position).isAnswered()) {
                if (quiz.getQuestion_bank().get(position).getUser_answer().equals(quiz.getQuestion_bank().get(position).getAnswer())) {
                    holder.counterIV.setImageDrawable(filledDrawableCorrect);
//                    holder.itemView.setBackground(filledDrawableCorrect);

                } else {
                    holder.counterIV.setImageDrawable(filledDrawableInCorrect);
//                    holder.itemView.setBackground(filledDrawableInCorrect);
                }

            } else {
                if (position == currentQues) {
                    holder.counterIV.setImageDrawable(blueDrawable);
//                    holder.counterIV.setBackground(context.getResources().getDrawable(R.drawable.circle_blue_outline_bg));
                    holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.circle_blue_outline_bg));

                } else {
                    holder.counterIV.setImageDrawable(textDrawable);

                    holder.itemView.setBackground(null);
                }

            }
        } else {
            if (quiz.getQuestion_bank().get(position).isAnswered()) {
                if (quiz.getQuestion_bank().get(position).getUser_answer().equals(quiz.getQuestion_bank().get(position).getAnswer()))
                    holder.counterIV.setImageDrawable(filledDrawableCorrect);
                else
                    holder.counterIV.setImageDrawable(filledDrawableInCorrect);

            } else {
                if (position == currentQues) {
                    holder.counterIV.setImageDrawable(blueDrawable);
                } else {
                    holder.counterIV.setImageDrawable(textDrawable);
//                    holder.counterIV.setBackground(context.getResources().getDrawable(R.drawable.ibt_quiz_circle_grey_border_bg));
                }
            }
        }

        holder.counterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((QuizActivity) context).IBTcounterCallbackListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (quiz != null) {
            return quiz.getQuestion_bank().size();
        } else {
            return resultTestSeriesArrayList.size();
        }
    }

    public class NavigationHolder extends RecyclerView.ViewHolder {
        ImageView counterIV;

        public NavigationHolder(View itemView) {
            super(itemView);
            counterIV = itemView.findViewById(R.id.counterIV);
            setIsRecyclable(false);
        }
    }
}
