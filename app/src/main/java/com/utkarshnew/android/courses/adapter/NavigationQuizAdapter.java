package com.utkarshnew.android.courses.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.Model.Courses.quiz.Questions;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.R;

import java.util.ArrayList;


public class NavigationQuizAdapter extends RecyclerView.Adapter<NavigationQuizAdapter.NavigationHolder> {

    Context context;
    QuizModel quiz;
    ArrayList<Questions> resultTestSeriesArrayList;

    public NavigationQuizAdapter(Activity quizActivity, QuizModel quiz) {
        this.context = quizActivity;
        this.quiz = quiz;
    }

    public NavigationQuizAdapter(Activity quizActivity, ArrayList<Questions> resultTestSeriesArrayList) {
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
        final TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(String.valueOf(position + 1), context.getResources().getColor(R.color.greayrefcode_dark));

        final TextDrawable filledDrawable = TextDrawable.builder()
                .buildRound(String.valueOf(position + 1), context.getResources().getColor(R.color.colorPrimary));
        if (quiz != null) {
            if (quiz.getQuestion_bank().get(position).isAnswered()) {
                holder.counterIV.setImageDrawable(filledDrawable);
            } else {
                holder.counterIV.setImageDrawable(textDrawable);
            }
        } else {
            if (resultTestSeriesArrayList.get(position).isAnswered()) {
                holder.counterIV.setImageDrawable(filledDrawable);
            } else {
                holder.counterIV.setImageDrawable(textDrawable);
            }
        }
        holder.counterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((QuizActivity) context).counterCallbackListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (quiz != null) {
//            return Integer.parseInt(quiz.getBasic_info().getTotal_questions());
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
        }
    }
}
