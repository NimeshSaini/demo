package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class RevisionResult extends MainFragment {

    TextView resultAccuracyTV;
    LinearLayout resultViewSolution;
    TextView resultNameTV, resultCorrectTV, resultIncorrectTV, resultAttemptTV, resultUnAttemptTV, not_visited, resultScoreTV, resultAttemptedTV, resultPercentileTV;
    String frag_type = "", questionlist = "";
    List<Answers> answersList = new ArrayList<>();
    List<Question> questionList = new ArrayList<>();
    TestseriesBase testseriesBase;

    int not_visited_count = 0;
    int answer_count = 0;
    int worong_answer = 0;
    int unansweredcount = 0;

    public static Fragment newInstance(String revision_string, String questionlist, TestseriesBase testseriesBase) {
        RevisionResult quizFragment = new RevisionResult();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TESTSERIES, revision_string);
        bundle.putSerializable("questionlist", questionlist);
        bundle.putSerializable("testseriesBase", testseriesBase);
        quizFragment.setArguments(bundle);
        return quizFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.revision_result_scrren, container, false);
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

//        resultNameTV.setText("Great work, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");

    }

    @SuppressLint("SetTextI18n")
    private void initViews(View view) {
        try {

            resultNameTV = view.findViewById(R.id.resultNameTV);
            resultCorrectTV = view.findViewById(R.id.resultCorrectTV);
            resultIncorrectTV = view.findViewById(R.id.resultIncorrectTV);
            resultAttemptTV = view.findViewById(R.id.resultAttemptTV);
            resultAccuracyTV = view.findViewById(R.id.resultAccuracyTV);
            resultUnAttemptTV = view.findViewById(R.id.resultUnAttemptTV);
            not_visited = view.findViewById(R.id.not_visited);
            resultScoreTV = view.findViewById(R.id.resultScoreTV);
            resultAttemptedTV = view.findViewById(R.id.resultAttemptedTV);
            resultPercentileTV = view.findViewById(R.id.resultPercentileTV);
            resultViewSolution = view.findViewById(R.id.resultViewSolution);

            resultScoreTV.setText("" + answer_count);
            resultCorrectTV.setText("" + answer_count + "/" + questionList.size());
            resultIncorrectTV.setText("" + worong_answer + "/" + questionList.size());

            resultAttemptTV.setText("" + (answer_count + worong_answer) + "/" + questionList.size());

            resultAttemptedTV.setText("" + (answer_count + worong_answer) + "/" + questionList.size());

            resultUnAttemptTV.setText("" + unansweredcount + "/" + questionList.size());

            not_visited.setText("" + not_visited_count + "/" + questionList.size());

            int percentage = ((answer_count * 100) / questionList.size());
            resultPercentileTV.setText("" + percentage + "%");

            if (percentage <= 30) {
                resultNameTV.setText("Need to do work hard, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
            } else if (percentage > 30 && percentage <= 70) {
                resultNameTV.setText("You can improve yourself, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
            } else if (percentage > 70 && percentage <= 90) {
                resultNameTV.setText("Good work, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
            } else {
                resultNameTV.setText("Excellent work, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
            }

            if (answer_count == 0 || (answer_count + worong_answer) == 0) {
                resultAccuracyTV.setText("0%");
            } else {
                resultAccuracyTV.setText("" + ((answer_count * 100) / (answer_count + worong_answer)) + "%");

            }


            resultViewSolution.setOnClickListener(v -> {
                Intent intent = new Intent(activity, RevisionSolutionActivity.class);
                intent.putExtra("testseriesBase", testseriesBase);
                intent.putExtra("answerlist", frag_type);
                Helper.gotoActivity(intent, activity);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.TESTSERIES);


            testseriesBase = (TestseriesBase) Objects.requireNonNull(getArguments()).getSerializable("testseriesBase");
            questionList = testseriesBase.getData().getQuestions();
            Type typeOfObjectsList = new TypeToken<ArrayList<Answers>>() {
            }.getType();
            answersList = new Gson().fromJson(frag_type, typeOfObjectsList);


            if (questionList != null && questionList.size() == answersList.size()) {
                for (Question question : questionList) {
                    for (Answers answers : answersList) {
                        if (question.getId().equalsIgnoreCase(answers.getConfig_id())) {
                            if (question.getRight_answer().equalsIgnoreCase(answers.getUser_answer())) {
                                question.setIsCorrect("1");
                                answer_count++;
                                break;
                            } else if (answers.getState().equalsIgnoreCase("not_visited")) {
                                question.setIsCorrect("0");
                                not_visited_count++;
                                break;
                            } else if (answers.getUser_answer().equalsIgnoreCase("")) {
                                if (answers.getState().equalsIgnoreCase("unanswered")) {
                                    question.setIsCorrect("0");

                                    unansweredcount++;
                                    break;
                                }
                                break;
                            } else if (!question.getRight_answer().equalsIgnoreCase(answers.getUser_answer())) {
                                question.setIsCorrect("0");

                                worong_answer++;
                                break;
                            }

                        }
                    }
                }


                Log.d("prince", "" + answer_count + "" + worong_answer + "" + unansweredcount + "" + not_visited);

            }
        }
        activity = getActivity();
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }
}
