package com.utkarshnew.android.CreateTest.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.CreateTest.Activity.CreateTestSolutionActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.FIBEdit;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.Questions2;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class TestCreateResult extends MainFragment {

    TextView resultAccuracyTV;
    LinearLayout resultViewSolution;
    TextView resultNameTV, resultCorrectTV, resultIncorrectTV, resultAttemptTV, resultUnAttemptTV, not_visited, resultScoreTV, resultAttemptedTV, resultPercentileTV, resultBookmarkTV;
    String frag_type = "", questionlist = "", response="";
    List<Answers> answersList = new ArrayList<>();
    List<Question> questionList = new ArrayList<>();
    List<Question> questionListhindi = new ArrayList<>();
    TestseriesBase testseriesBase;
    TestseriesBase correct_testseriesBase,incorrect_testbase,attempt_testbase,unattempt_testbase,not_visited_testbase;

    LinearLayout correct_layout,incorrect_layout,attempted_layout,unattempted_layout,non_visited_layout;



    int not_visited_count = 0;
    int answer_count = 0;
    int worong_answer = 0;
    int unansweredcount = 0;
    int sectionwisebookmarkcount = 0;

    public static Fragment newInstance(String revision_string, String questionlist, TestseriesBase testseriesBase) {
        TestCreateResult quizFragment = new TestCreateResult();
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
        return inflater.inflate(R.layout.create_test_result_screen, container, false);
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

            correct_layout = view.findViewById(R.id.correct_layout);
            incorrect_layout = view.findViewById(R.id.incorrect_layout);
            attempted_layout = view.findViewById(R.id.attempted_layout);
            unattempted_layout = view.findViewById(R.id.unattempt_layout);
            non_visited_layout = view.findViewById(R.id.non_visited_layout);

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
            resultBookmarkTV = view.findViewById(R.id.resultBookmarkTV);

            resultScoreTV.setText("" + answer_count);
            resultCorrectTV.setText("" + answer_count + "/" + questionList.size());
            resultIncorrectTV.setText("" + worong_answer + "/" + questionList.size());

            resultAttemptTV.setText("" + (answer_count + worong_answer) + "/" + questionList.size());

            resultAttemptedTV.setText("" + (answer_count + worong_answer) + "/" + questionList.size());

            resultUnAttemptTV.setText("" + unansweredcount + "/" + questionList.size());

            not_visited.setText("" + not_visited_count + "/" + questionList.size());

            resultBookmarkTV.setText("" + sectionwisebookmarkcount + "/" + questionList.size());

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

                SharedPreference.getInstance().putString("testseriesBase", new Gson().toJson(testseriesBase));
                Intent intent = new Intent(activity, CreateTestSolutionActivity.class);

                intent.putExtra("answerlist", frag_type);
                Helper.gotoActivity(intent, activity);

            });


            correct_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            Type typeOfObjectsList1 = new TypeToken<TestseriesBase>() {}.getType();
                    correct_testseriesBase = new Gson().fromJson(new Gson().toJson(testseriesBase), typeOfObjectsList1);

                    List<Answers> answersArrayList = new ArrayList<>();


                    if (correct_testseriesBase.getData().getQuestions()!=null &&  correct_testseriesBase.getData().getQuestions().size()>0)
                    {
                        ArrayList<Question> questionArrayList =new ArrayList<>();
                        ArrayList<Questions2> response =new ArrayList<>();
                        int k=0;
                        for (Question question :correct_testseriesBase.getData().getQuestions())
                        {
                            if (question.getIsCorrect().equalsIgnoreCase("1"))
                            {
                                answersArrayList.add(answersList.get(k));

                                questionArrayList.add(question);
                                response.add(correct_testseriesBase.getData().getQuestion_response().get(k));
                            }
                            k++;
                        }
                        correct_testseriesBase.getData().setQuestions(questionArrayList);
                        correct_testseriesBase.getData().setQuestion_response(response);
                    }

                    if (correct_testseriesBase.getData().getQuestionsHindi()!=null &&  correct_testseriesBase.getData().getQuestionsHindi().size()>0)
                    {
                        ArrayList<Question> questionArrayList =new ArrayList<>();
                        ArrayList<Questions2> response =new ArrayList<>();

                        if (answersArrayList.size()>0)
                        {
                            if (correct_testseriesBase.getData().getQuestion_response()!=null && correct_testseriesBase.getData().getQuestion_response().size()>0)
                            {
                                int k=0;
                                for (Question question :correct_testseriesBase.getData().getQuestionsHindi())
                                {
                                    if (question.getIsCorrect().equalsIgnoreCase("1"))
                                    {
                                        answersArrayList.add(answersList.get(k));
                                        questionArrayList.add(question);
                                    }
                                    k++;
                                }
                                correct_testseriesBase.getData().setQuestionsHindi(questionArrayList);
                            }else {
                                int k=0;
                                for (Question question :correct_testseriesBase.getData().getQuestionsHindi())
                                {
                                    if (question.getIsCorrect().equalsIgnoreCase("1"))
                                    {
                                        answersArrayList.add(answersList.get(k));

                                        questionArrayList.add(question);
                                        response.add(correct_testseriesBase.getData().getQuestion_response().get(k));
                                    }
                                    k++;
                                }
                                correct_testseriesBase.getData().setQuestionsHindi(questionArrayList);
                                correct_testseriesBase.getData().setQuestion_response(response);
                            }
                        }else {
                            if (correct_testseriesBase.getData().getQuestion_response()!=null && correct_testseriesBase.getData().getQuestion_response().size()>0)
                            {
                                for (Question question :correct_testseriesBase.getData().getQuestionsHindi())
                                {
                                    if (question.getIsCorrect().equalsIgnoreCase("1"))
                                    {
                                        questionArrayList.add(question);
                                    }
                                }
                                correct_testseriesBase.getData().setQuestionsHindi(questionArrayList);
                            }else {
                                int k=0;
                                for (Question question :correct_testseriesBase.getData().getQuestionsHindi())
                                {
                                    if (question.getIsCorrect().equalsIgnoreCase("1"))
                                    {
                                        questionArrayList.add(question);
                                        response.add(correct_testseriesBase.getData().getQuestion_response().get(k));
                                    }
                                    k++;
                                }
                                correct_testseriesBase.getData().setQuestionsHindi(questionArrayList);
                                correct_testseriesBase.getData().setQuestion_response(response);
                            }
                        }

                    }

                    if (correct_testseriesBase.getData().getQuestion_response()!=null && correct_testseriesBase.getData().getQuestion_response().size()>0)
                    {
                        SharedPreference.getInstance().putString("testseriesBase", new Gson().toJson(correct_testseriesBase));
                        Intent intent = new Intent(activity, CreateTestSolutionActivity.class);

                        intent.putExtra("answerlist", new Gson().toJson(answersArrayList));
                        Helper.gotoActivity(intent, activity);
                    }else
                    {
                        Toast.makeText(activity, "No correct found", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        
            non_visited_layout.setOnClickListener(v -> {

                Type typeOfObjectsList1 = new TypeToken<TestseriesBase>() {}.getType();
                not_visited_testbase = new Gson().fromJson(new Gson().toJson(testseriesBase), typeOfObjectsList1);

                List<Answers> answersArrayList = new ArrayList<>();

                if ( not_visited_testbase.getData().getQuestions()!=null &&   not_visited_testbase.getData().getQuestions().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();

                    int k=0;
                    for (Question question : not_visited_testbase.getData().getQuestions())
                    {
                        if ( question.getState().equalsIgnoreCase("not_visited"))
                        {
                            answersArrayList.add(answersList.get(k));

                            questionArrayList.add(question);
                            response.add( not_visited_testbase.getData().getQuestion_response().get(k));
                        }
                        k++;
                    }
                    not_visited_testbase.getData().setQuestions(questionArrayList);
                    not_visited_testbase.getData().setQuestion_response(response);
                }

                if ( not_visited_testbase.getData().getQuestionsHindi()!=null &&   not_visited_testbase.getData().getQuestionsHindi().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();

                    if ( not_visited_testbase.getData().getQuestion_response()!=null && not_visited_testbase.getData().getQuestion_response().size()>0)
                    {
                        if (answersArrayList.size()==0){
                            int k=0;
                            for (Question question :not_visited_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("not_visited"))
                                {
                                    answersArrayList.add(answersList.get(k));

                                    questionArrayList.add(question);
                                }
                                k++;
                            }
                        }else {
                            for (Question question :not_visited_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("not_visited"))
                                {
                                    questionArrayList.add(question);
                                }
                            }
                        }
                        not_visited_testbase.getData().setQuestionsHindi(questionArrayList);
                    }else {
                        int k=0;

                        if (answersArrayList.size()==0){
                            for (Question question :not_visited_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("not_visited"))
                                {
                                    answersArrayList.add(answersList.get(k));
                                    questionArrayList.add(question);
                                    response.add(not_visited_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }

                        }else {
                            for (Question question :not_visited_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("not_visited"))
                                {
                                    questionArrayList.add(question);
                                    response.add(not_visited_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }
                        }



                        not_visited_testbase.getData().setQuestionsHindi(questionArrayList);
                        not_visited_testbase.getData().setQuestion_response(response);
                    }
                }

                if (not_visited_testbase.getData().getQuestion_response()!=null && not_visited_testbase.getData().getQuestion_response().size()>0)
                {
                    SharedPreference.getInstance().putString("testseriesBase", new Gson().toJson(not_visited_testbase));
                    Intent intent = new Intent(activity, CreateTestSolutionActivity.class);

                    intent.putExtra("answerlist", new Gson().toJson(answersArrayList));
                    Helper.gotoActivity(intent, activity);
                }else {
                    Toast.makeText(activity, "No Not visited question found", Toast.LENGTH_SHORT).show();
                }

            });





            incorrect_layout.setOnClickListener(v -> {

                Type typeOfObjectsList1 = new TypeToken<TestseriesBase>() {}.getType();
                incorrect_testbase = new Gson().fromJson(new Gson().toJson(testseriesBase), typeOfObjectsList1);

                List<Answers> answersArrayList = new ArrayList<>();

                if (incorrect_testbase.getData().getQuestions()!=null &&  incorrect_testbase.getData().getQuestions().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();
                    int k=0;
                    for (Question question :incorrect_testbase.getData().getQuestions())
                    {
                        if (question.getIsCorrect()!=null && question.getIsCorrect().equalsIgnoreCase("0") && question.getState().equalsIgnoreCase("answered"))
                        {
                            answersArrayList.add(answersList.get(k));

                            questionArrayList.add(question);

                            response.add(incorrect_testbase.getData().getQuestion_response().get(k));
                        }
                        k++;
                    }
                    incorrect_testbase.getData().setQuestions(questionArrayList);
                    incorrect_testbase.getData().setQuestion_response(response);
                }

                if (incorrect_testbase.getData().getQuestionsHindi()!=null &&  incorrect_testbase.getData().getQuestionsHindi().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();
                    if (answersArrayList.size()==0)
                    {
                        if (incorrect_testbase.getData().getQuestion_response()!=null && incorrect_testbase.getData().getQuestion_response().size()>0)
                        {
                            int k =0;
                            for (Question question :incorrect_testbase.getData().getQuestionsHindi())
                            {
                                if (question.getIsCorrect()!=null && question.getIsCorrect().equalsIgnoreCase("0") && question.getState().equalsIgnoreCase("answered"))
                                {
                                    answersArrayList.add(answersList.get(k));

                                    questionArrayList.add(question);
                                }
                                k++;
                            }
                            incorrect_testbase.getData().setQuestionsHindi(questionArrayList);
                        }else {
                            int k=0;
                            for (Question question :incorrect_testbase.getData().getQuestionsHindi())
                            {
                                if (question.getIsCorrect()!=null && question.getIsCorrect().equalsIgnoreCase("0") && question.getState().equalsIgnoreCase("answered"))
                                {
                                    answersArrayList.add(answersList.get(k));

                                    questionArrayList.add(question);
                                    response.add(incorrect_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }
                            incorrect_testbase.getData().setQuestionsHindi(questionArrayList);
                            incorrect_testbase.getData().setQuestion_response(response);
                        }

                    }else {
                        if (incorrect_testbase.getData().getQuestion_response()!=null && incorrect_testbase.getData().getQuestion_response().size()>0)
                        {
                            for (Question question :incorrect_testbase.getData().getQuestionsHindi())
                            {
                                if (question.getIsCorrect()!=null && question.getIsCorrect().equalsIgnoreCase("0") && question.getState().equalsIgnoreCase("answered"))
                                {
                                    questionArrayList.add(question);
                                }
                            }
                            incorrect_testbase.getData().setQuestionsHindi(questionArrayList);
                        }else {
                            int k=0;
                            for (Question question :incorrect_testbase.getData().getQuestionsHindi())
                            {
                                if (question.getIsCorrect()!=null && question.getIsCorrect().equalsIgnoreCase("0") && question.getState().equalsIgnoreCase("answered"))
                                {
                                    questionArrayList.add(question);
                                    response.add(incorrect_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }
                            incorrect_testbase.getData().setQuestionsHindi(questionArrayList);
                            incorrect_testbase.getData().setQuestion_response(response);
                        }
                    }

                }

                if (incorrect_testbase.getData().getQuestion_response()!=null && incorrect_testbase.getData().getQuestion_response().size()>0)
                {
                    SharedPreference.getInstance().putString("testseriesBase", new Gson().toJson(incorrect_testbase));
                    Intent intent = new Intent(activity, CreateTestSolutionActivity.class);

                    intent.putExtra("answerlist", new Gson().toJson(answersArrayList));
                    Helper.gotoActivity(intent, activity);
                }else {
                    Toast.makeText(activity, "No Incorrect found", Toast.LENGTH_SHORT).show();
                }

            });


            unattempted_layout.setOnClickListener(v -> {

                Type typeOfObjectsList1 = new TypeToken<TestseriesBase>() {}.getType();
                 unattempt_testbase = new Gson().fromJson(new Gson().toJson(testseriesBase), typeOfObjectsList1);

                List<Answers> answersArrayList = new ArrayList<>();

                if ( unattempt_testbase.getData().getQuestions()!=null &&   unattempt_testbase.getData().getQuestions().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();

                    int k=0;
                    for (Question question : unattempt_testbase.getData().getQuestions())
                    {
                        if ( question.getState().equalsIgnoreCase("unanswered"))
                        {
                            answersArrayList.add(answersList.get(k));

                            questionArrayList.add(question);
                            response.add( unattempt_testbase.getData().getQuestion_response().get(k));
                        }
                        k++;
                    }
                     unattempt_testbase.getData().setQuestions(questionArrayList);
                     unattempt_testbase.getData().setQuestion_response(response);
                }

                if ( unattempt_testbase.getData().getQuestionsHindi()!=null &&   unattempt_testbase.getData().getQuestionsHindi().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();

                    if ( unattempt_testbase.getData().getQuestion_response()!=null && unattempt_testbase.getData().getQuestion_response().size()>0)
                    {
                        if (answersArrayList.size()==0){
                            int k=0;
                            for (Question question :unattempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("unanswered"))
                                {
                                    answersArrayList.add(answersList.get(k));

                                    questionArrayList.add(question);
                                }
                                k++;
                            }
                        }else {
                            for (Question question :unattempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("unanswered"))
                                {
                                    questionArrayList.add(question);
                                }
                            }
                        }
                        unattempt_testbase.getData().setQuestionsHindi(questionArrayList);
                    }else {
                        int k=0;

                        if (answersArrayList.size()==0){
                            for (Question question :unattempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("unanswered"))
                                {
                                    answersArrayList.add(answersList.get(k));
                                    questionArrayList.add(question);
                                    response.add(unattempt_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }

                        }else {
                            for (Question question :unattempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("unanswered"))
                                {
                                    questionArrayList.add(question);
                                    response.add(unattempt_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }
                        }



                        unattempt_testbase.getData().setQuestionsHindi(questionArrayList);
                        unattempt_testbase.getData().setQuestion_response(response);
                    }
                }

                if (unattempt_testbase.getData().getQuestion_response()!=null && unattempt_testbase.getData().getQuestion_response().size()>0)
                {
                    SharedPreference.getInstance().putString("testseriesBase", new Gson().toJson(unattempt_testbase));
                    Intent intent = new Intent(activity, CreateTestSolutionActivity.class);

                    intent.putExtra("answerlist", new Gson().toJson(answersArrayList));
                    Helper.gotoActivity(intent, activity);
                }else {
                    Toast.makeText(activity, "No Incorrect found", Toast.LENGTH_SHORT).show();
                }

            });

            attempted_layout.setOnClickListener(v -> {

                Type typeOfObjectsList1 = new TypeToken<TestseriesBase>() {}.getType();
                attempt_testbase = new Gson().fromJson(new Gson().toJson(testseriesBase), typeOfObjectsList1);
                List<Answers> answersArrayList = new ArrayList<>();


                if (attempt_testbase.getData().getQuestions()!=null &&  attempt_testbase.getData().getQuestions().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();
                    int k=0;
                    for (Question question :attempt_testbase.getData().getQuestions())
                    {
                        if ( question.getState().equalsIgnoreCase("answered"))
                        {
                            answersArrayList.add(answersList.get(k));

                            questionArrayList.add(question);
                            response.add(attempt_testbase.getData().getQuestion_response().get(k));
                        }
                        k++;
                    }
                    attempt_testbase.getData().setQuestions(questionArrayList);
                    attempt_testbase.getData().setQuestion_response(response);
                }

                if (attempt_testbase.getData().getQuestionsHindi()!=null &&  attempt_testbase.getData().getQuestionsHindi().size()>0)
                {
                    ArrayList<Question> questionArrayList =new ArrayList<>();
                    ArrayList<Questions2> response =new ArrayList<>();
                    if (answersArrayList.size()==0)
                    {
                        if (attempt_testbase.getData().getQuestion_response()!=null && attempt_testbase.getData().getQuestion_response().size()>0)
                        {
                            int k=0;
                            for (Question question :attempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("answered"))
                                {
                                    answersArrayList.add(answersList.get(k));

                                    questionArrayList.add(question);
                                }
                            }
                            attempt_testbase.getData().setQuestionsHindi(questionArrayList);
                        }else {
                            int k=0;
                            for (Question question :attempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("answered"))
                                {
                                    answersArrayList.add(answersList.get(k));

                                    questionArrayList.add(question);
                                    response.add(attempt_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }
                            attempt_testbase.getData().setQuestionsHindi(questionArrayList);
                            attempt_testbase.getData().setQuestion_response(response);
                        }

                    }else {
                        if (attempt_testbase.getData().getQuestion_response()!=null && attempt_testbase.getData().getQuestion_response().size()>0)
                        {
                            for (Question question :attempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("answered"))
                                {
                                    questionArrayList.add(question);
                                }
                            }
                            attempt_testbase.getData().setQuestionsHindi(questionArrayList);
                        }else {
                            int k=0;
                            for (Question question :attempt_testbase.getData().getQuestionsHindi())
                            {
                                if ( question.getState().equalsIgnoreCase("answered"))
                                {
                                    questionArrayList.add(question);
                                    response.add(attempt_testbase.getData().getQuestion_response().get(k));
                                }
                                k++;
                            }
                            attempt_testbase.getData().setQuestionsHindi(questionArrayList);
                            attempt_testbase.getData().setQuestion_response(response);
                        }
                    }

                }

                if (attempt_testbase.getData().getQuestion_response()!=null && attempt_testbase.getData().getQuestion_response().size()>0)
                {
                    SharedPreference.getInstance().putString("testseriesBase", new Gson().toJson(attempt_testbase));
                    Intent intent = new Intent(activity, CreateTestSolutionActivity.class);

                    intent.putExtra("answerlist", new Gson().toJson(answersArrayList));
                    Helper.gotoActivity(intent, activity);
                }else {
                    Toast.makeText(activity, "No Incorrect found", Toast.LENGTH_SHORT).show();
                }

            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int countFIBWords(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i).startsWith("FIB")) {
                count++;
            }
        }
        return count;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.TESTSERIES);
             response = getArguments().getString("questionlist");


          //  testseriesBase = (TestseriesBase) Objects.requireNonNull(getArguments()).getSerializable("testseriesBase");

            Type typeOfObjectsList1 = new TypeToken<TestseriesBase>() {}.getType();
            testseriesBase = new Gson().fromJson(response, typeOfObjectsList1);

            Type typeOfObjectsList = new TypeToken<ArrayList<Answers>>() {
            }.getType();
            answersList = new Gson().fromJson(frag_type, typeOfObjectsList);



            if (testseriesBase.getData().getTestBasic()!=null && testseriesBase.getData().getTestBasic().getLangId()!=null && testseriesBase.getData().getTestBasic().getLangId().size()==2 )
            {
                if (testseriesBase.getData().getTestBasic().getLangId().get(0).equalsIgnoreCase("1"))
                {
                    questionList = testseriesBase.getData().getQuestions();
                    questionListhindi = testseriesBase.getData().getQuestionsHindi();

                }else  if (testseriesBase.getData().getTestBasic().getLangId().get(0).equalsIgnoreCase("2"))
                {

                    questionList = testseriesBase.getData().getQuestionsHindi();

                    questionListhindi = testseriesBase.getData().getQuestions();
                }

                for (int l =0;l<questionList.size();l++){
                    for (int k =0;k<answersList.size();k++) {
                        Question question=    questionList.get(l);
                        Question questionhindi=    questionListhindi.get(l);
                        Answers answers=    answersList.get(k);
                        if (question.getConfigId().equalsIgnoreCase(answers.getConfig_id())) {
                            if (question.getQuestionType().equalsIgnoreCase("FIB")) {
                                List<FIBEdit> fibEdits = new ArrayList<>();
                                for (int i = 0; i < countFIBWords(question.getQuestion().trim()); i++) {
                                    FIBEdit fibEdit = null;
                                    if (i == 0) {
                                        fibEdit = new Gson().fromJson(question.getOption1().trim(), FIBEdit.class);
                                    } else if (i == 1) {
                                        fibEdit = new Gson().fromJson(question.getOption2().trim(), FIBEdit.class);
                                    } else if (i == 2) {
                                        fibEdit = new Gson().fromJson(question.getOption3().trim(), FIBEdit.class);
                                    } else if (i == 3) {
                                        fibEdit = new Gson().fromJson(question.getOption4().trim(), FIBEdit.class);
                                    } else if (i == 4) {
                                        fibEdit = new Gson().fromJson(question.getOption5().trim(), FIBEdit.class);
                                    } else if (i == 5) {
                                        fibEdit = new Gson().fromJson(question.getOption6().trim(), FIBEdit.class);
                                    }

                                    if (fibEdit != null && !fibEdit.getType().equalsIgnoreCase("")) {
                                        fibEdits.add(fibEdit);
                                    }
                                }

                                String mainAns = "";
                                for (int i = 0; i < fibEdits.size(); i++) {
                                    if (!fibEdits.get(i).getAnswer().toString().equalsIgnoreCase("")) {
                                        mainAns = mainAns + fibEdits.get(i).getAnswer();
                                    }
                                }

                                String yourAns = "";
                                for (int i = 0; i < answers.getAnswers().size(); i++) {
                                    if (!answers.getAnswers().get(i).toString().equalsIgnoreCase("")) {
                                        yourAns = yourAns + answers.getAnswers().get(i);
                                    }
                                }

                                if (!TextUtils.isEmpty(yourAns)) {
                                    if (mainAns.trim().equalsIgnoreCase(yourAns.trim())) {
                                        question.setIsCorrect("1");
                                        questionhindi.setIsCorrect("1");
                                        question.setState(answers.getState());
                                        questionhindi.setState(answers.getState());
                                        answer_count++;
                                        break;
                                    } else {
                                        question.setIsCorrect("0");
                                        questionhindi.setIsCorrect("0");
                                        question.setState(answers.getState());
                                        questionhindi.setState(answers.getState());
                                        worong_answer++;
                                        break;
                                    }
                                } else if (answers.getIs_bookmarked().equalsIgnoreCase("1")) {
                                    question.setIsCorrect("0");
                                    questionhindi.setIsCorrect("0");
                                    question.setState(answers.getState());
                                    questionhindi.setState(answers.getState());
                                    sectionwisebookmarkcount++;
                                    break;
                                } else if (answers.getState().equalsIgnoreCase("not_visited")) {
                                    question.setIsCorrect("0");
                                    questionhindi.setIsCorrect("0");
                                    question.setState(answers.getState());
                                    questionhindi.setState(answers.getState());
                                    not_visited_count++;
                                    break;
                                } else if (answers.getAnswers().size() <= 0) {
                                    if (answers.getState().equalsIgnoreCase("unanswered")) {
                                        question.setIsCorrect("0");
                                        questionhindi.setIsCorrect("0");
                                        question.setState(answers.getState());
                                        questionhindi.setState(answers.getState());
                                        unansweredcount++;
                                        break;
                                    }
                                    break;
                                } else if (answers.getState().equalsIgnoreCase("unanswered")) {
                                    question.setIsCorrect("0");
                                    questionhindi.setIsCorrect("0");
                                    question.setState(answers.getState());
                                    questionhindi.setState(answers.getState());
                                    unansweredcount++;
                                    break;
                                }
                            } else {
                                if (question.getAnswer().equalsIgnoreCase(answers.getUser_answer())) {
                                    question.setIsCorrect("1");
                                    questionhindi.setIsCorrect("1");
                                    question.setState(answers.getState());
                                    questionhindi.setState(answers.getState());

                                    answer_count++;
                                    break;
                                } else if (answers.getIs_bookmarked().equalsIgnoreCase("1")) {
                                    question.setIsCorrect("0");
                                    questionhindi.setIsCorrect("0");

                                    question.setState(answers.getState());
                                    questionhindi.setState(answers.getState());
                                    sectionwisebookmarkcount++;
                                    break;
                                } else if (answers.getState().equalsIgnoreCase("not_visited")) {
                                    question.setIsCorrect("0");
                                    questionhindi.setIsCorrect("0");
                                    question.setState(answers.getState());
                                    questionhindi.setState(answers.getState());
                                    not_visited_count++;
                                    break;
                                } else if (answers.getUser_answer().equalsIgnoreCase("")) {
                                    if (answers.getState().equalsIgnoreCase("unanswered")) {
                                        question.setIsCorrect("0");
                                        questionhindi.setIsCorrect("0");
                                        question.setState(answers.getState());
                                        questionhindi.setState(answers.getState());
                                        unansweredcount++;
                                        break;
                                    }
                                    break;
                                } else if (!question.getAnswer().equalsIgnoreCase(answers.getUser_answer())) {
                                    question.setIsCorrect("0");
                                    questionhindi.setIsCorrect("0");
                                    question.setState(answers.getState());
                                    questionhindi.setState(answers.getState());
                                    worong_answer++;
                                    break;
                                }
                            }
                        }
                    }
                }

            }else {
                questionList = testseriesBase.getData().getQuestions();
                if (questionList != null && questionList.size() == answersList.size()) {
                    for (Question question : questionList) {
                        for (Answers answers : answersList) {
                            if (question.getConfigId().equalsIgnoreCase(answers.getConfig_id())) {
                                if (question.getQuestionType().equalsIgnoreCase("FIB")) {
                                    List<FIBEdit> fibEdits = new ArrayList<>();
                                    for (int i = 0; i < countFIBWords(question.getQuestion().trim()); i++) {
                                        FIBEdit fibEdit = null;
                                        if (i == 0) {
                                            fibEdit = new Gson().fromJson(question.getOption1().trim(), FIBEdit.class);
                                        } else if (i == 1) {
                                            fibEdit = new Gson().fromJson(question.getOption2().trim(), FIBEdit.class);
                                        } else if (i == 2) {
                                            fibEdit = new Gson().fromJson(question.getOption3().trim(), FIBEdit.class);
                                        } else if (i == 3) {
                                            fibEdit = new Gson().fromJson(question.getOption4().trim(), FIBEdit.class);
                                        } else if (i == 4) {
                                            fibEdit = new Gson().fromJson(question.getOption5().trim(), FIBEdit.class);
                                        } else if (i == 5) {
                                            fibEdit = new Gson().fromJson(question.getOption6().trim(), FIBEdit.class);
                                        }

                                        if (fibEdit != null && !fibEdit.getType().equalsIgnoreCase("")) {
                                            fibEdits.add(fibEdit);
                                        }
                                    }

                                    String mainAns = "";
                                    for (int i = 0; i < fibEdits.size(); i++) {
                                        if (!fibEdits.get(i).getAnswer().toString().equalsIgnoreCase("")) {
                                            mainAns = mainAns + fibEdits.get(i).getAnswer();
                                        }
                                    }

                                    String yourAns = "";
                                    for (int i = 0; i < answers.getAnswers().size(); i++) {
                                        if (!answers.getAnswers().get(i).toString().equalsIgnoreCase("")) {
                                            yourAns = yourAns + answers.getAnswers().get(i);
                                        }
                                    }

                                    if (!TextUtils.isEmpty(yourAns)) {
                                        if (mainAns.trim().equalsIgnoreCase(yourAns.trim())) {
                                            question.setIsCorrect("1");
                                            question.setState(answers.getState());

                                            answer_count++;
                                            break;
                                        } else {
                                            question.setIsCorrect("0");
                                            question.setState(answers.getState());

                                            worong_answer++;
                                            break;
                                        }
                                    } else if (answers.getIs_bookmarked().equalsIgnoreCase("1")) {
                                        question.setIsCorrect("0");
                                        question.setState(answers.getState());

                                        sectionwisebookmarkcount++;
                                        break;
                                    } else if (answers.getState().equalsIgnoreCase("not_visited")) {
                                        question.setIsCorrect("0");
                                        question.setState(answers.getState());

                                        not_visited_count++;
                                        break;
                                    } else if (answers.getAnswers().size() <= 0) {
                                        if (answers.getState().equalsIgnoreCase("unanswered")) {
                                            question.setIsCorrect("0");
                                            question.setState(answers.getState());

                                            unansweredcount++;
                                            break;
                                        }
                                        break;
                                    } else if (answers.getState().equalsIgnoreCase("unanswered")) {
                                        question.setIsCorrect("0");
                                        question.setState(answers.getState());

                                        unansweredcount++;
                                        break;
                                    }
                                } else {
                                    if (question.getAnswer().equalsIgnoreCase(answers.getUser_answer())) {
                                        question.setIsCorrect("1");
                                        question.setState(answers.getState());
                                        answer_count++;
                                        break;
                                    } else if (answers.getIs_bookmarked().equalsIgnoreCase("1")) {
                                        question.setIsCorrect("0");
                                        question.setState(answers.getState());
                                        sectionwisebookmarkcount++;
                                        break;
                                    } else if (answers.getState().equalsIgnoreCase("not_visited")) {
                                        question.setIsCorrect("0");
                                        question.setState(answers.getState());

                                        not_visited_count++;
                                        break;
                                    } else if (answers.getUser_answer().equalsIgnoreCase("")) {
                                        if (answers.getState().equalsIgnoreCase("unanswered")) {
                                            question.setIsCorrect("0");
                                            question.setState(answers.getState());

                                            unansweredcount++;
                                            break;
                                        }
                                        break;
                                    } else if (!question.getAnswer().equalsIgnoreCase(answers.getUser_answer())) {
                                        question.setIsCorrect("0");
                                        question.setState(answers.getState());

                                        worong_answer++;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    Log.d("prince", "" + answer_count + "" + worong_answer + "" + unansweredcount + "" + not_visited_count);

                }
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