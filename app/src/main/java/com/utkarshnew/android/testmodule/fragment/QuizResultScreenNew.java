package com.utkarshnew.android.testmodule.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.LiveTest.Activity.LivetestActivity;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.GenericUtils;
import com.utkarshnew.android.Utils.GridSpacingItemDecoration;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.NonScrollRecyclerView;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.activity.VideoPlayActivity;
import com.utkarshnew.android.testmodule.activity.ViewSolutionActivity;
import com.utkarshnew.android.testmodule.adapter.AnswerListAdapter;
import com.utkarshnew.android.testmodule.adapter.RankAdapter;
import com.utkarshnew.android.testmodule.adapter.SectionAdapter;
import com.utkarshnew.android.testmodule.interfaces.MachingOnDrag;
import com.utkarshnew.android.testmodule.model.InstructionData;
import com.utkarshnew.android.testmodule.model.QuestionDump;
import com.utkarshnew.android.testmodule.model.QuestionDumps;
import com.utkarshnew.android.testmodule.model.Questions2;
import com.utkarshnew.android.testmodule.model.ResultTestSeries_Report;
import com.utkarshnew.android.testmodule.model.TestBasicInst;
import com.utkarshnew.android.testmodule.model.TestSectionInst;
import com.utkarshnew.android.testmodule.model.TestSections;
import com.utkarshnew.android.testmodule.model.TestSeriesResultData;
import com.utkarshnew.android.testmodule.model.TestseriesBase;
import com.utkarshnew.android.testmodule.model.TopTenList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizResultScreenNew extends MainFragment implements MachingOnDrag {

    public Data user;
    Activity activity;
    String frag_type = "", status = "";
    int total_answered = 0;
    QuizModel quiz;
    Button btnPartA, btnPartB, btnPartC, btnPartD, btnPartE, btnPartF, btnPartG, btnPartH,
            btnPartI, btnPartJ, btnPartK, btnPartL, btnPartM, btnPartN, btnPartO;
    String testSeriesName = "";
    String first_attempt = "", show_leader = "";
    ResultTestSeries_Report resultTestSeries2, resultTestSeries2_incorrect, resultTestSeries2_attempt, resultTresultTestSeries2_unattempt;
    long server_time;
    ResultTestSeries_Report resultTestSeries2_correct;
    int lang;  // 2 for Hindi , 1 for English
    LinearLayout resultViewSolution, videosolution, videosolution2, llNewTest;
    Progress progress;
    TextView scoreTV, percentageTV, timeTV, accuracyTV, percentileTV, guessTV, attemptedTV, userAttempt;
    RelativeLayout viewSubjectWiseDetailRL;
    ArrayList<QuestionDump> questionDumps = new ArrayList<>();
    List<TopTenList> topTenLists = new ArrayList<>();
    TextView resultRankTV, reattempts, resultScoreTV, resultNameTV, resultAccuracyTV, resultGuessTV, resultPercentileTV, resultAttemptedTV, resultBestScoreTV, resultAvgScoreTV,
            resultCorrectTV, resultIncorrectTV, resultSkippedTV, resultBookmarkTV, resultUnAttemptTV, resultAttemptTV, resultTimeDuration, leaderboard_text;
    NonScrollRecyclerView recyclerviewrank;
    LinearLayout linearlayout, showMore, showLess, resultOutLL, resultDeclaredLL;
    RankAdapter adapter;
    AnswerListAdapter answerListAdapter;
    NonScrollRecyclerView answerListRV;
    String testseriesid;
    // TestSeriesDbHelper testSeriesDbHelper;
    ArrayList<String> arrayList;
    ArrayList<QuestionDump> newQuestionDump;
    ArrayList<TestSeriesResultData> testSeriesResultDataArrayList;
    Progress mprogress;
    JSONObject jsonResponse = null;
    RecyclerView section_recyclerview;
    RelativeLayout rankRL;
    View viewpercentile, viewaccuracy;
    LinearLayout accuracy_LL;
    LinearLayout scoreLL;
    LinearLayout percentileLL;
    RelativeLayout accuracy_sectionwise;
    LinearLayout correct_layout, incorrect_layout, attemp_layout, unattempt_layout;


    public static Fragment newInstance(String frag_type, ResultTestSeries resultTestSeries2, QuizModel quiz, String testSeriesName, String first_attempt) {
        QuizResultScreenNew quizFragment = new QuizResultScreenNew();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TESTSERIES, resultTestSeries2);
        bundle.putSerializable(Const.FRAG_TYPE, frag_type);
        bundle.putSerializable(Const.RESULT_SCREEN, quiz);
        bundle.putString(Const.NAME, testSeriesName);
        bundle.putString("first_attempt", first_attempt);

        quizFragment.setArguments(bundle);
        return quizFragment;
    }

    public static Fragment newInstance(String frag_type, String status, String testSeriesName, String first_attempt, String show_leader) {
        QuizResultScreenNew quizFragment = new QuizResultScreenNew();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.FRAG_TYPE, frag_type);
        bundle.putSerializable(Const.STATUS, status);
        bundle.putString(Const.NAME, testSeriesName);
        bundle.putString("first_attempt", first_attempt);
        bundle.putString("show_leader", show_leader);

        quizFragment.setArguments(bundle);
        return quizFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_result_screen_new, container, false);
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = SharedPreference.getInstance().getLoggedInUser();
        mprogress = new Progress(activity);
        mprogress.setCancelable(false);
        initViews(view);
        arrayList = new ArrayList<>();
        testSeriesResultDataArrayList = new ArrayList<>();
        newQuestionDump = new ArrayList<>();

//        resultNameTV.setText("Great work, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
        if (!TextUtils.isEmpty(status)) {
            netoworkCallForQuizResult2();//NetworkAPICall(API.API_GET_USER_LEADERBOARD_RESULT, true);
        } else {
        }
    }

    public void netoworkCallForQuizResult2() {
        if (Helper.isNetworkConnected(getActivity())) {
            mprogress.show();
            APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
            masterdatadetailencryptionData.setTest_id(status);
            masterdatadetailencryptionData.setCourse_id(SharedPreference.getInstance().getString(Const.ID));
            masterdatadetailencryptionData.setFirst_attempt(first_attempt);
            String doseStr = new Gson().toJson(masterdatadetailencryptionData);
            String doseStrScr = AES.encrypt(doseStr);

            Call<String> response;
            response = Service.getTestResult(doseStrScr);

            assert response != null;
            response.enqueue(new Callback<String>() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    mprogress.dismiss();
                    if (response.body() != null) {
                        String jsonString;
                        try {
                            jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                            jsonResponse = new JSONObject(jsonString);
                        } catch (Exception e) {
                        }

                        if (jsonResponse == null) {
                            Helper.showToastSecurity(activity);
                            return;
                        }

                        resultTestSeries2 = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);


                        if (resultTestSeries2 == null) {
                            Helper.showToastSecurity(activity);
                            return;
                        }

                        try {
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time"))) * 1000);

                            if (resultTestSeries2.getStatus().equalsIgnoreCase("true")) {
                                server_time = jsonResponse.optLong("time");
                                if (resultTestSeries2.getData().getResult_date().equalsIgnoreCase("0") || resultTestSeries2.getData().getResult_date().equalsIgnoreCase("") || resultTestSeries2.getData().getResult_date().equalsIgnoreCase("1")) {
                                    resultViewSolution.setVisibility(View.VISIBLE);
                                } else {
                                    if (server_time >= Long.parseLong(resultTestSeries2.getData().getResult_date())) {
                                        resultViewSolution.setVisibility(View.VISIBLE);
                                    } else if (Long.parseLong(resultTestSeries2.getData().getResult_date()) > server_time) {
                                        resultViewSolution.setVisibility(View.GONE);
                                    } else {
                                        resultViewSolution.setVisibility(View.GONE);
                                    }
                                }
                                if (frag_type.equalsIgnoreCase(Const.SOLUTIONREPORT)) {
                                    SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2));

                                    Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                    intent.putExtra(Const.TESTSEGMENT_ID, status);
                                    intent.putExtra(Const.FRAG_TYPE, frag_type);
//                                    intent.putExtra(Const.RESULT_SCREEN, resultTestSeries2);
                                    intent.putExtra(Const.NAME, testSeriesName);
                                    intent.putExtra(Const.LANG, TextUtils.isEmpty(resultTestSeries2.getData().getLang_id()) ? 1 : Integer.parseInt(resultTestSeries2.getData().getLang_id()));
                                    Helper.gotoActivity(intent, activity);
                                    getActivity().finish();
                                } else {
                                    if (resultTestSeries2.getData().getTest_report_videos() != null && resultTestSeries2.getData().getTest_report_videos().size() > 0) {
                                        if (resultTestSeries2.getData().getTest_report_videos().size() > 1) {
                                            videosolution.setVisibility(View.VISIBLE);
                                            videosolution2.setVisibility(View.VISIBLE);

                                        } else {
                                            videosolution.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        videosolution.setVisibility(View.GONE);
                                        videosolution2.setVisibility(View.GONE);

                                    }


                                    if (show_leader == null) {
                                        topTenLists = resultTestSeries2.getData().getTop_ten_list();
                                        if (topTenLists.size() > 0) {
                                            leaderboard_text.setVisibility(View.VISIBLE);
                                            adapter = new RankAdapter(activity, topTenLists);
                                            recyclerviewrank.setAdapter(adapter);
                                        }
                                    } else {
                                        leaderboard_text.setVisibility(View.GONE);
                                        recyclerviewrank.setVisibility(View.GONE);
                                    }


                                    if (show_leader == null) {
                                        if (first_attempt.equalsIgnoreCase("1")) {
                                            rankRL.setVisibility(View.VISIBLE);
                                            accuracy_LL.setVisibility(View.VISIBLE);
                                            percentileLL.setVisibility(View.VISIBLE);
                                            scoreLL.setVisibility(View.VISIBLE);
                                            viewpercentile.setVisibility(View.VISIBLE);
                                            viewaccuracy.setVisibility(View.VISIBLE);
                                        } else {
                                            rankRL.setVisibility(View.GONE);
                                            accuracy_LL.setVisibility(View.GONE);
                                            percentileLL.setVisibility(View.GONE);
                                            scoreLL.setVisibility(View.GONE);
                                            viewpercentile.setVisibility(View.GONE);
                                            viewaccuracy.setVisibility(View.GONE);
                                        }

                                    } else {
                                        if (first_attempt.equalsIgnoreCase("1")) {
                                            rankRL.setVisibility(View.GONE);
                                            accuracy_LL.setVisibility(View.VISIBLE);
                                            percentileLL.setVisibility(View.VISIBLE);
                                            scoreLL.setVisibility(View.VISIBLE);
                                            viewpercentile.setVisibility(View.VISIBLE);
                                            viewaccuracy.setVisibility(View.VISIBLE);


                                        } else {

                                            accuracy_sectionwise.setVisibility(View.GONE);
                                            userAttempt.setVisibility(View.GONE);
                                            rankRL.setVisibility(View.GONE);
                                            accuracy_LL.setVisibility(View.VISIBLE);
                                            percentileLL.setVisibility(View.VISIBLE);
                                            scoreLL.setVisibility(View.GONE);
                                            accuracy_LL.setVisibility(View.GONE);
                                            viewpercentile.setVisibility(View.VISIBLE);
                                            viewaccuracy.setVisibility(View.VISIBLE);


                                        }

                                    }
                                    setSection();

                                    if (!resultTestSeries2.getData().getUser_rank().equalsIgnoreCase("")) {
                                        resultRankTV.setText(resultTestSeries2.getData().getUser_rank());
                                    } else {
                                        resultRankTV.setText("N/A");
                                    }
                                    // String marks = String.valueOf(Float.parseFloat(resultTestSeries2.getData().getMarks()));
                                 //   resultScoreTV.setText(resultTestSeries2.getData().getMarks() + "/" + resultTestSeries2.getData().getTotal_marks());


                                    if (resultTestSeries2.getData().getPercentile() != null && !resultTestSeries2.getData().getPercentile().equalsIgnoreCase("")) {
                                        resultAccuracyTV.setText(resultTestSeries2.getData().getPercentile());
                                    }
                                    if (resultTestSeries2.getData().getPercentage() != null && !resultTestSeries2.getData().getPercentage().equalsIgnoreCase("")) {
                                        resultPercentileTV.setText(resultTestSeries2.getData().getPercentage());
                                    }

                                    if (Float.parseFloat(resultTestSeries2.getData().getPercentage()) <= 30) {
                                        resultNameTV.setText("Need to do work hard, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
                                    } else if (Float.parseFloat(resultTestSeries2.getData().getPercentage()) > 30 && Float.parseFloat(resultTestSeries2.getData().getPercentage()) <= 70) {
                                        resultNameTV.setText("You can improve yourself, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
                                    } else if (Float.parseFloat(resultTestSeries2.getData().getPercentage()) > 70 && Float.parseFloat(resultTestSeries2.getData().getPercentage()) <= 90) {
                                        resultNameTV.setText("Good work, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
                                    } else {
                                        resultNameTV.setText("Excellent work, " + SharedPreference.getInstance().getLoggedInUser().getName() + "!");
                                    }


                                    if (resultTestSeries2.getData().getBest_score() != null)
                                        resultBestScoreTV.setText(resultTestSeries2.getData().getBest_score());
                                    if (resultTestSeries2.getData().getAvg_score() != null) {
                                        resultAvgScoreTV.setText(resultTestSeries2.getData().getAvg_score());
                                    }
                                    if (resultTestSeries2.getData().getCorrect_count() != null)
                                        resultCorrectTV.setText(resultTestSeries2.getData().getCorrect_count());

                                    if (resultTestSeries2.getData().getNon_attempt() != null)
                                        resultUnAttemptTV.setText(resultTestSeries2.getData().getNon_attempt());

                                    if (resultTestSeries2.getData().getIncorrect_count() != null)
                                        resultIncorrectTV.setText(resultTestSeries2.getData().getIncorrect_count());


                                    if (resultTestSeries2.getData().getIncorrect_count().equalsIgnoreCase("")) {
                                        resultTestSeries2.getData().setIncorrect_count("0");
                                    }
                                    if (resultTestSeries2.getData().getCorrect_count().equalsIgnoreCase("")) {
                                        resultTestSeries2.getData().setCorrect_count("0");
                                    }

                                    int totla_attempyt = Integer.parseInt(resultTestSeries2.getData().getCorrect_count()) + Integer.parseInt(resultTestSeries2.getData().getIncorrect_count());
                                    resultAttemptTV.setText("" + totla_attempyt);


                                    if (resultTestSeries2.getData().getTime_in_mins() != null)
                                        resultTimeDuration.setText(resultTestSeries2.getData().getTime_in_mins() + "Min");


                                    if (resultTestSeries2.getData().getTotal_user_attempt() != null && !resultTestSeries2.getData().getTotal_user_attempt().equalsIgnoreCase(""))
                                        if (Integer.parseInt(resultTestSeries2.getData().getTotal_user_attempt()) > 0) {
                                            userAttempt.setText("Total Students Appeared : " + resultTestSeries2.getData().getTotal_user_attempt());
                                        }

                                    correct_layout.setOnClickListener(v -> {
                                        resultTestSeries2_correct = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);

                                        if (resultTestSeries2_correct != null && resultTestSeries2_correct.getData() != null && resultTestSeries2_correct.getData().getQuestion_dump() != null && resultTestSeries2_correct.getData().getQuestion_dump().size() > 0) {
                                            ArrayList<QuestionDumps> questionDumpsArrayList = new ArrayList<>();

                                            if (resultTestSeries2_correct.getData().getQuestions() != null && resultTestSeries2_correct.getData().getQuestions().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                int k = 0;
                                                for (Questions2 questions2 : resultTestSeries2_correct.getData().getQuestions()) {
                                                    if (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(1)) {
                                                        questions2ArrayList.add(questions2);
                                                        questionDumpsArrayList.add(resultTestSeries2_correct.getData().getQuestion_dump().get(k));
                                                    }

                                                    k++;
                                                }
                                                resultTestSeries2_correct.getData().setQuestions(questions2ArrayList);
                                                resultTestSeries2_correct.getData().setQuestion_dump(questionDumpsArrayList);
                                            }

                                            if (resultTestSeries2_correct.getData().getQuestionsHindi() != null && resultTestSeries2_correct.getData().getQuestionsHindi().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                if (questionDumpsArrayList.size() > 0) {
                                                    for (Questions2 questions2 : resultTestSeries2_correct.getData().getQuestionsHindi()) {
                                                        if (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(1)) {
                                                            questions2ArrayList.add(questions2);
                                                        }
                                                    }
                                                    resultTestSeries2_correct.getData().setQuestionsHindi(questions2ArrayList);

                                                } else {
                                                    int k = 0;
                                                    for (Questions2 questions2 : resultTestSeries2_correct.getData().getQuestionsHindi()) {
                                                        if (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(1)) {
                                                            questions2ArrayList.add(questions2);
                                                            questionDumpsArrayList.add(resultTestSeries2_correct.getData().getQuestion_dump().get(k));

                                                        }
                                                        k++;
                                                    }
                                                    resultTestSeries2_correct.getData().setQuestion_dump(questionDumpsArrayList);
                                                    resultTestSeries2_correct.getData().setQuestionsHindi(questions2ArrayList);
                                                }
                                            }

                                            if (resultTestSeries2_correct.getData().getQuestion_dump() != null && resultTestSeries2_correct.getData().getQuestion_dump().size() > 0) {
                                                resultTestSeries2_correct.getData().setTotal_questions("" + resultTestSeries2_correct.getData().getQuestion_dump().size());

                                                SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2_correct));
                                                Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                                intent.putExtra(Const.TESTSEGMENT_ID, status);
                                                intent.putExtra(Const.NAME, testSeriesName);

                                                if (resultTestSeries2_correct.getData().getLang_id().split(",")[0].equals("1")) {
                                                    lang = Integer.parseInt(resultTestSeries2_correct.getData().getLang_id().split(",")[0]);
                                                } else if (resultTestSeries2_correct.getData().getLang_id().split(",")[0].equals("2")) {
                                                    lang = Integer.parseInt(resultTestSeries2_correct.getData().getLang_id().split(",")[0]);
                                                }
                                                intent.putExtra(Const.LANG, lang);
                                                Helper.gotoActivity(intent, activity);
                                            } else {
                                                Toast.makeText(activity, "No Correct found", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            Toast.makeText(activity, "No View Solution Found Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    incorrect_layout.setOnClickListener(v -> {
                                        resultTestSeries2_incorrect = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);

                                        if (resultTestSeries2_incorrect != null && resultTestSeries2_incorrect.getData() != null && resultTestSeries2_incorrect.getData().getQuestion_dump() != null && resultTestSeries2_incorrect.getData().getQuestion_dump().size() > 0) {
                                            ArrayList<QuestionDumps> questionDumpsArrayList = new ArrayList<>();

                                            if (resultTestSeries2_incorrect.getData().getQuestions() != null && resultTestSeries2_incorrect.getData().getQuestions().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                int k = 0;
                                                for (Questions2 questions2 : resultTestSeries2_incorrect.getData().getQuestions()) {
                                                    if (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && questions2.getState().equalsIgnoreCase("answered")) {
                                                        questions2ArrayList.add(questions2);
                                                        questionDumpsArrayList.add(resultTestSeries2_incorrect.getData().getQuestion_dump().get(k));
                                                    }

                                                    k++;
                                                }
                                                resultTestSeries2_incorrect.getData().setQuestions(questions2ArrayList);
                                                resultTestSeries2_incorrect.getData().setQuestion_dump(questionDumpsArrayList);
                                            }

                                            if (resultTestSeries2_incorrect.getData().getQuestionsHindi() != null && resultTestSeries2_incorrect.getData().getQuestionsHindi().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                if (questionDumpsArrayList.size() > 0) {
                                                    for (Questions2 questions2 : resultTestSeries2_incorrect.getData().getQuestionsHindi()) {
                                                        if (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && questions2.getState().equalsIgnoreCase("answered")) {
                                                            questions2ArrayList.add(questions2);
                                                        }
                                                    }
                                                    resultTestSeries2_incorrect.getData().setQuestionsHindi(questions2ArrayList);

                                                } else {
                                                    int k = 0;
                                                    for (Questions2 questions2 : resultTestSeries2_incorrect.getData().getQuestionsHindi()) {
                                                        if (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && questions2.getState().equalsIgnoreCase("answered")) {
                                                            questions2ArrayList.add(questions2);
                                                            questionDumpsArrayList.add(resultTestSeries2_incorrect.getData().getQuestion_dump().get(k));

                                                        }
                                                        k++;
                                                    }
                                                    resultTestSeries2_incorrect.getData().setQuestion_dump(questionDumpsArrayList);
                                                    resultTestSeries2_incorrect.getData().setQuestionsHindi(questions2ArrayList);
                                                }
                                            }

                                            if (resultTestSeries2_incorrect.getData().getQuestion_dump() != null && resultTestSeries2_incorrect.getData().getQuestion_dump().size() > 0) {
                                                resultTestSeries2_incorrect.getData().setTotal_questions("" + resultTestSeries2_incorrect.getData().getQuestion_dump().size());

                                                SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2_incorrect));
                                                Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                                intent.putExtra(Const.TESTSEGMENT_ID, status);
                                                intent.putExtra(Const.NAME, testSeriesName);

                                                if (resultTestSeries2_incorrect.getData().getLang_id().split(",")[0].equals("1")) {
                                                    lang = Integer.parseInt(resultTestSeries2_incorrect.getData().getLang_id().split(",")[0]);
                                                } else if (resultTestSeries2_incorrect.getData().getLang_id().split(",")[0].equals("2")) {
                                                    lang = Integer.parseInt(resultTestSeries2_incorrect.getData().getLang_id().split(",")[0]);
                                                }
                                                intent.putExtra(Const.LANG, lang);
                                                Helper.gotoActivity(intent, activity);
                                            } else {
                                                Toast.makeText(activity, "No InCorrect found", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            Toast.makeText(activity, "No View Solution Found Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    attemp_layout.setOnClickListener(v -> {
                                        resultTestSeries2_attempt = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);

                                        if (resultTestSeries2_attempt != null && resultTestSeries2_attempt.getData() != null && resultTestSeries2_attempt.getData().getQuestion_dump() != null && resultTestSeries2_attempt.getData().getQuestion_dump().size() > 0) {
                                            ArrayList<QuestionDumps> questionDumpsArrayList = new ArrayList<>();

                                            if (resultTestSeries2_attempt.getData().getQuestions() != null && resultTestSeries2_attempt.getData().getQuestions().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                int k = 0;
                                                for (Questions2 questions2 : resultTestSeries2_attempt.getData().getQuestions()) {
                                                    if ((questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(1)) || (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && questions2.getState().equalsIgnoreCase("answered"))) {
                                                        questions2ArrayList.add(questions2);
                                                        questionDumpsArrayList.add(resultTestSeries2_attempt.getData().getQuestion_dump().get(k));
                                                    }

                                                    k++;
                                                }
                                                resultTestSeries2_attempt.getData().setQuestions(questions2ArrayList);
                                                resultTestSeries2_attempt.getData().setQuestion_dump(questionDumpsArrayList);
                                            }

                                            if (resultTestSeries2_attempt.getData().getQuestionsHindi() != null && resultTestSeries2_attempt.getData().getQuestionsHindi().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                if (questionDumpsArrayList.size() > 0) {
                                                    for (Questions2 questions2 : resultTestSeries2_attempt.getData().getQuestionsHindi()) {
                                                        if ((questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(1)) || (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && questions2.getState().equalsIgnoreCase("answered"))) {
                                                            questions2ArrayList.add(questions2);
                                                        }
                                                    }
                                                    resultTestSeries2_attempt.getData().setQuestionsHindi(questions2ArrayList);

                                                } else {
                                                    int k = 0;
                                                    for (Questions2 questions2 : resultTestSeries2_attempt.getData().getQuestionsHindi()) {
                                                        if ((questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(1)) || (questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && questions2.getState().equalsIgnoreCase("answered"))) {
                                                            questions2ArrayList.add(questions2);
                                                            questionDumpsArrayList.add(resultTestSeries2_attempt.getData().getQuestion_dump().get(k));

                                                        }
                                                        k++;
                                                    }
                                                    resultTestSeries2_attempt.getData().setQuestion_dump(questionDumpsArrayList);
                                                    resultTestSeries2_attempt.getData().setQuestionsHindi(questions2ArrayList);
                                                }
                                            }

                                            if (resultTestSeries2_attempt.getData().getQuestion_dump() != null && resultTestSeries2_attempt.getData().getQuestion_dump().size() > 0) {
                                                resultTestSeries2_attempt.getData().setTotal_questions("" + resultTestSeries2_attempt.getData().getQuestion_dump().size());

                                                SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2_attempt));
                                                Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                                intent.putExtra(Const.TESTSEGMENT_ID, status);
                                                intent.putExtra(Const.NAME, testSeriesName);

                                                if (resultTestSeries2_attempt.getData().getLang_id().split(",")[0].equals("1")) {
                                                    lang = Integer.parseInt(resultTestSeries2_attempt.getData().getLang_id().split(",")[0]);
                                                } else if (resultTestSeries2_attempt.getData().getLang_id().split(",")[0].equals("2")) {
                                                    lang = Integer.parseInt(resultTestSeries2_attempt.getData().getLang_id().split(",")[0]);
                                                }
                                                intent.putExtra(Const.LANG, lang);
                                                Helper.gotoActivity(intent, activity);
                                            } else {
                                                Toast.makeText(activity, "No Attempted found", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            Toast.makeText(activity, "No View Solution Found Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    unattempt_layout.setOnClickListener(v -> {

                                        resultTresultTestSeries2_unattempt = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);

                                        if (resultTresultTestSeries2_unattempt != null && resultTresultTestSeries2_unattempt.getData() != null && resultTresultTestSeries2_unattempt.getData().getQuestion_dump() != null && resultTresultTestSeries2_unattempt.getData().getQuestion_dump().size() > 0) {
                                            ArrayList<QuestionDumps> questionDumpsArrayList = new ArrayList<>();

                                            if (resultTresultTestSeries2_unattempt.getData().getQuestions() != null && resultTresultTestSeries2_unattempt.getData().getQuestions().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                int k = 0;
                                                for (Questions2 questions2 : resultTresultTestSeries2_unattempt.getData().getQuestions()) {
                                                    if ((questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && !questions2.getState().equalsIgnoreCase("answered"))) {
                                                        questions2ArrayList.add(questions2);
                                                        questionDumpsArrayList.add(resultTresultTestSeries2_unattempt.getData().getQuestion_dump().get(k));
                                                    }

                                                    k++;
                                                }
                                                resultTresultTestSeries2_unattempt.getData().setQuestions(questions2ArrayList);
                                                resultTresultTestSeries2_unattempt.getData().setQuestion_dump(questionDumpsArrayList);
                                            }

                                            if (resultTresultTestSeries2_unattempt.getData().getQuestionsHindi() != null && resultTresultTestSeries2_unattempt.getData().getQuestionsHindi().size() > 0) {
                                                ArrayList<Questions2> questions2ArrayList = new ArrayList<>();
                                                if (questionDumpsArrayList.size() > 0) {
                                                    for (Questions2 questions2 : resultTresultTestSeries2_unattempt.getData().getQuestionsHindi()) {
                                                        if ((questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && !questions2.getState().equalsIgnoreCase("answered"))) {
                                                            questions2ArrayList.add(questions2);
                                                        }
                                                    }
                                                    resultTresultTestSeries2_unattempt.getData().setQuestionsHindi(questions2ArrayList);

                                                } else {
                                                    int k = 0;
                                                    for (Questions2 questions2 : resultTresultTestSeries2_unattempt.getData().getQuestionsHindi()) {
                                                        if ((questions2.getIsCorrect() != null && questions2.getIsCorrect().equals(0) && questions2.getState() != null && !questions2.getState().equalsIgnoreCase("answered"))) {
                                                            questions2ArrayList.add(questions2);
                                                            questionDumpsArrayList.add(resultTresultTestSeries2_unattempt.getData().getQuestion_dump().get(k));

                                                        }
                                                        k++;
                                                    }
                                                    resultTresultTestSeries2_unattempt.getData().setQuestion_dump(questionDumpsArrayList);
                                                    resultTresultTestSeries2_unattempt.getData().setQuestionsHindi(questions2ArrayList);
                                                }
                                            }

                                            if (resultTresultTestSeries2_unattempt.getData().getQuestion_dump() != null && resultTresultTestSeries2_unattempt.getData().getQuestion_dump().size() > 0) {
                                                resultTresultTestSeries2_unattempt.getData().setTotal_questions("" + resultTresultTestSeries2_unattempt.getData().getQuestion_dump().size());

                                                SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTresultTestSeries2_unattempt));
                                                Intent intent = new Intent(activity, ViewSolutionActivity.class);
                                                intent.putExtra(Const.TESTSEGMENT_ID, status);
                                                intent.putExtra(Const.NAME, testSeriesName);

                                                if (resultTresultTestSeries2_unattempt.getData().getLang_id().split(",")[0].equals("1")) {
                                                    lang = Integer.parseInt(resultTresultTestSeries2_unattempt.getData().getLang_id().split(",")[0]);
                                                } else if (resultTresultTestSeries2_unattempt.getData().getLang_id().split(",")[0].equals("2")) {
                                                    lang = Integer.parseInt(resultTresultTestSeries2_unattempt.getData().getLang_id().split(",")[0]);
                                                }
                                                intent.putExtra(Const.LANG, lang);
                                                Helper.gotoActivity(intent, activity);
                                            } else {
                                                Toast.makeText(activity, "No UnAttempted found", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            Toast.makeText(activity, "No View Solution Found Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                    if (resultTestSeries2.getData().getResult_date().equalsIgnoreCase("0") || resultTestSeries2.getData().getResult_date().equalsIgnoreCase("") || resultTestSeries2.getData().getResult_date().equalsIgnoreCase("1")) {

                                        correct_layout.setEnabled(true);
                                        incorrect_layout.setEnabled(true);
                                        attemp_layout.setEnabled(true);
                                        unattempt_layout.setEnabled(true);
                                    } else {
                                        if (server_time >= Long.parseLong(resultTestSeries2.getData().getResult_date())) {
                                            correct_layout.setEnabled(true);
                                            incorrect_layout.setEnabled(true);
                                            attemp_layout.setEnabled(true);
                                            unattempt_layout.setEnabled(true);

                                        } else if (Long.parseLong(resultTestSeries2.getData().getResult_date()) > server_time) {

                                            correct_layout.setEnabled(false);
                                            incorrect_layout.setEnabled(false);
                                            attemp_layout.setEnabled(false);
                                            unattempt_layout.setEnabled(false);
                                        } else {

                                            correct_layout.setEnabled(false);
                                            incorrect_layout.setEnabled(false);
                                            attemp_layout.setEnabled(false);
                                            unattempt_layout.setEnabled(false);
                                        }
                                    }

                                }


                            } else {
                                mprogress.dismiss();
                                if (!GenericUtils.isEmpty(jsonResponse.getString(Const.AUTH_CODE)))
                                    RetrofitResponse.GetApiData(getContext(), jsonResponse.has(Const.AUTH_CODE) ? jsonResponse.getString(Const.AUTH_CODE) : "", jsonResponse.getString(Const.MESSAGE), false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mprogress.dismiss();

                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    @SuppressLint("DefaultLocale")
    private void setSection() {
        if (resultTestSeries2.getData().getTest_sections() != null && resultTestSeries2.getData().getTest_sections().size() > 0) {

            float total=0;
            int totalques=0;
            for (TestSections testSections :resultTestSeries2.getData().getTest_sections()) {
                if (testSections.getOptional_que()==null)
                {
                    testSections.setOptional_que("0");
                }
                total = total + (Integer.parseInt(testSections.getNoOfQuestions()) - Integer.parseInt(testSections.getOptional_que())) * Float.parseFloat(testSections.getMarksPerQuestion());
                totalques = totalques + (Integer.parseInt(testSections.getNoOfQuestions()) - Integer.parseInt(testSections.getOptional_que()));
            }
            resultScoreTV.setText(resultTestSeries2.getData().getMarks() + "/" + String.format("%.2f", total));

            if (resultTestSeries2.getData().getTotal_questions() != null && resultTestSeries2.getData().getTotal_user_attempt() != null) {
                resultAttemptedTV.setText(Integer.parseInt(resultTestSeries2.getData().getTotal_questions()) - Integer.parseInt(resultTestSeries2.getData().getNon_attempt()) + "/" + totalques);
            }

            llNewTest.setVisibility(View.VISIBLE);
            SectionAdapter sectionAdapter = new SectionAdapter(activity, resultTestSeries2.getData().getTest_sections(), this);
            section_recyclerview.setAdapter(sectionAdapter);
            sendOnclickInd(0);
        } else {
            llNewTest.setVisibility(View.GONE);
        }
    }

    private void initViews(View view) {
        correct_layout = view.findViewById(R.id.correct_layout);
        incorrect_layout = view.findViewById(R.id.incorrect_layout);
        attemp_layout = view.findViewById(R.id.attempt_layout);
        unattempt_layout = view.findViewById(R.id.unattempt_layout);

        showMore = view.findViewById(R.id.showMore);
        section_recyclerview = view.findViewById(R.id.section_recyclerview);
        showLess = view.findViewById(R.id.showLess);
        answerListRV = view.findViewById(R.id.answerListRV);
        videosolution = view.findViewById(R.id.videosolution);
        videosolution2 = view.findViewById(R.id.videosolution2);
        accuracy_sectionwise = view.findViewById(R.id.accuracy_sectionwise);

        resultViewSolution = view.findViewById(R.id.resultViewSolution);
        rankRL = view.findViewById(R.id.rankRL);
        accuracy_LL = view.findViewById(R.id.accuracy_LL);
        percentileLL = view.findViewById(R.id.percentileLL);
        scoreLL = view.findViewById(R.id.scoreLL);
        viewpercentile = view.findViewById(R.id.viewpercentile);
        viewaccuracy = view.findViewById(R.id.viewaccuracy);

        resultNameTV = view.findViewById(R.id.resultNameTV);
        resultAccuracyTV = view.findViewById(R.id.resultAccuracyTV);
        userAttempt = view.findViewById(R.id.userAttempt);
        resultGuessTV = view.findViewById(R.id.resultGuessTV);
        llNewTest = view.findViewById(R.id.llNewTest);
        resultPercentileTV = view.findViewById(R.id.resultPercentileTV);
        resultAttemptedTV = view.findViewById(R.id.resultAttemptedTV);
        resultBestScoreTV = view.findViewById(R.id.resultBestScoreTv);
        resultAvgScoreTV = view.findViewById(R.id.resultAvgScoreTV);
        resultCorrectTV = view.findViewById(R.id.resultCorrectTV);
        resultIncorrectTV = view.findViewById(R.id.resultIncorrectTV);



        scoreTV = view.findViewById(R.id.scoreTV);
        percentageTV = view.findViewById(R.id.percentageTV);
        timeTV = view.findViewById(R.id.timeTV);
        accuracyTV = view.findViewById(R.id.accuracyTV);
        percentileTV = view.findViewById(R.id.percentileTV);
        guessTV = view.findViewById(R.id.guessTV);
        attemptedTV = view.findViewById(R.id.attemptedTV);
        resultSkippedTV = view.findViewById(R.id.resultSkippedTV);
        resultBookmarkTV = view.findViewById(R.id.resultBookmarkTV);
        resultAttemptTV = view.findViewById(R.id.resultAttemptTV);
        resultUnAttemptTV = view.findViewById(R.id.resultUnAttemptTV);
        resultTimeDuration = view.findViewById(R.id.resultTimeDuration);
        viewSubjectWiseDetailRL = view.findViewById(R.id.viewSubjectWiseDetailRL);
        resultRankTV = view.findViewById(R.id.resultRankTV);
        resultScoreTV = view.findViewById(R.id.resultScoreTV);
        recyclerviewrank = view.findViewById(R.id.leaderBoardRV);
        resultOutLL = view.findViewById(R.id.resultOutLL);
        leaderboard_text = view.findViewById(R.id.leaderboard_text);
        resultDeclaredLL = view.findViewById(R.id.resultDeclaredLL);
        recyclerviewrank.setLayoutManager(new LinearLayoutManager(activity));
        recyclerviewrank.setHasFixedSize(true);

        answerListRV.setLayoutManager(new GridLayoutManager(activity, 7));
        answerListRV.addItemDecoration(new GridSpacingItemDecoration(7, 20, false));

        resultViewSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultTestSeries2 != null && resultTestSeries2.getData() != null && resultTestSeries2.getData().getQuestion_dump() != null && resultTestSeries2.getData().getQuestion_dump().size() > 0) {
                    SharedPreference.getInstance().putString("testresult", new Gson().toJson(resultTestSeries2));
                    Intent intent = new Intent(activity, ViewSolutionActivity.class);
                    intent.putExtra(Const.TESTSEGMENT_ID, status);
                    intent.putExtra(Const.NAME, testSeriesName);

                    if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("1")) {
                        lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                    } else if (resultTestSeries2.getData().getLang_id().split(",")[0].equals("2")) {
                        lang = Integer.parseInt(resultTestSeries2.getData().getLang_id().split(",")[0]);
                    }
                    intent.putExtra(Const.LANG, lang);

                    Helper.gotoActivity(intent, activity);
                } else {
                    Toast.makeText(activity, "No View Solution Found Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

        videosolution.setOnClickListener(view1 -> {
            if (Helper.isNetworkConnected(getActivity())) {
                if (resultTestSeries2.getData().getTest_report_videos() != null && resultTestSeries2.getData().getTest_report_videos().get(0).getVideo_url().contains(".mp4")) {
                    Intent i = new Intent(getActivity(), VideoPlayActivity.class);
                    i.putExtra("videodata", resultTestSeries2.getData().getTest_report_videos().get(0));
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Incrroupt Video Url ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }


        });
        videosolution2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultTestSeries2.getData().getTest_report_videos() != null && resultTestSeries2.getData().getTest_report_videos().get(1).getVideo_url().contains(".mp4")) {
                    Intent i = new Intent(getActivity(), VideoPlayActivity.class);
                    i.putExtra("videodata", resultTestSeries2.getData().getTest_report_videos().get(1));
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Incrroupt Video Url ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewSubjectWiseDetailRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent resultScreen = new Intent(activity, QuizActivity.class);
//                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SUBJECT_WISE);
//                resultScreen.putExtra(Const.STATUS, status);
//                activity.startActivity(resultScreen);
            }
        });


    }

    private void addSectionView(LinearLayout sectionListLL, InstructionData instructionData) {
        int count = 0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            if (testSectionInst.getOptional_que()==null)
            {
                testSectionInst.setOptional_que("0");
            }
            sectionListLL.addView(initSectionListView(testSectionInst, count));
            count++;
        }
    }

    public LinearLayout initSectionListView(TestSectionInst testSectionInst, int tag) {
        List<View> LinearLayoutList = new ArrayList<>();
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_section_list_view, null);
        TextView secNameTV = (TextView) v.findViewById(R.id.secNameTV);
        TextView totQuesTV = (TextView) v.findViewById(R.id.totQuesTV);
        TextView totTimeTV = (TextView) v.findViewById(R.id.totTimeTV);
        TextView option_count = (TextView) v.findViewById(R.id.option_count);

        TextView maxMarksTV = (TextView) v.findViewById(R.id.maxMarksTV);
        TextView markPerQuesTV = (TextView) v.findViewById(R.id.markPerQuesTV);
        TextView negMarkPerQuesTV = (TextView) v.findViewById(R.id.negMarkPerQuesTV);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);

        v.setLayoutParams(lp);
        secNameTV.setText(testSectionInst.getName() + "\n" + "(" + testSectionInst.getSectionPart() + ")");
        totQuesTV.setText(testSectionInst.getTotalQuestions());
        totTimeTV.setText(testSectionInst.getSectionTiming());
        option_count.setText(""+(Integer.parseInt(testSectionInst.getTotalQuestions())- Integer.parseInt(testSectionInst.getOptional_que())));
        maxMarksTV.setText(String.format("%.2f",(Float.parseFloat(testSectionInst.getMarksPerQuestion()) * (Integer.parseInt(testSectionInst.getTotalQuestions()) -Integer.parseInt(testSectionInst.getOptional_que())))));
        markPerQuesTV.setText(testSectionInst.getMarksPerQuestion());
        negMarkPerQuesTV.setText("" + Float.parseFloat(testSectionInst.getNegativeMarks()));

        v.setTag(tag);
        LinearLayoutList.add(v);

        return v;
    }

    public void showPopMenuForLangauge(final View v, TestBasicInst testBasicInst) {
        PopupMenu popup = new PopupMenu(activity, v);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) v).setText(item.getTitle().toString());
                if (item.getTitle().toString().equals(activity.getString(R.string.hindi)))
                    lang = 2;
                else if (item.getTitle().toString().equals(activity.getString(R.string.english)))
                    lang = 1;
//                    else lang = -1;
                Log.e("Language", String.valueOf(lang));
                return false;
            }
        });

        for (int i = 0; i < testBasicInst.getLang_id().split(",").length; i++) {
//            if (quiz.getBasic_info().getLang_id().split(",").length >= 2)
//                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[i]);
//            else {
            if (testBasicInst.getLang_id().split(",")[i].equals("1"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            else if (testBasicInst.getLang_id().split(",")[i].equals("2"))
                popup.getMenu().add(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[1]);
//            }
        }
        popup.show();
    }

    private void showPopUp(final InstructionData instructionData) {
        LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.popup_basicinfo_quiz_career, null, false);
        //final Dialog quizBasicInfoDialog = new Dialog(activity, R.style.MyDialogTheme);
        final Dialog quizBasicInfoDialog = new Dialog(activity, R.style.CustomAlertDialog);
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizBasicInfoDialog.setCanceledOnTouchOutside(true);
        quizBasicInfoDialog.setContentView(v);
        quizBasicInfoDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        quizBasicInfoDialog.show();

        RelativeLayout basicInfoLL, basicInfoQuizLL;
        final TextView quizTitle, quizQuesNumTv, quizTimeTV, quizCorrectValue, quizWrongValue, quizTotalMarks, generalInstrValueTV, reAttempt, languageSpinnerTV, quizsectionValueTV;
        ImageView quizImage;
        final CheckBox check_box;
        Button startQuiz;
        LinearLayout sectionListLL;
        final TestBasicInst testBasicInst = instructionData.getTestBasic();

        basicInfoQuizLL = (RelativeLayout) v.findViewById(R.id.basicInfoDialogLL);
        quizTitle = (TextView) v.findViewById(R.id.quizTitleTV);
        quizImage = (ImageView) v.findViewById(R.id.quizImageIV);
        quizCorrectValue = (TextView) v.findViewById(R.id.marksCorrectValueTV);
        quizWrongValue = (TextView) v.findViewById(R.id.marksWrongValueTV);
        quizTotalMarks = (TextView) v.findViewById(R.id.marksTextValueTV);
        quizQuesNumTv = (TextView) v.findViewById(R.id.numQuesValueTV);
        quizsectionValueTV = (TextView) v.findViewById(R.id.sectionValueTV);
        languageSpinnerTV = (TextView) v.findViewById(R.id.languageSpinnerTV);
        quizTimeTV = (TextView) v.findViewById(R.id.quizTimeValueTV);
        reAttempt = (TextView) v.findViewById(R.id.remarksTV);
        check_box = (CheckBox) v.findViewById(R.id.check_box);
        generalInstrValueTV = (TextView) v.findViewById(R.id.generalInstrValueTV);
        startQuiz = (Button) v.findViewById(R.id.startQuizBtn);
        sectionListLL = (LinearLayout) v.findViewById(R.id.sectionListLL);

        int total_ques =0;
        float totalmarks =0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            if (testSectionInst.getOptional_que()==null)
            {
                testSectionInst.setOptional_que("0");
            }
            total_ques =total_ques+(Integer.parseInt(testSectionInst.getTotalQuestions())-Integer.parseInt(testSectionInst.getOptional_que()));
            totalmarks =totalmarks+((Integer.parseInt(testSectionInst.getTotalQuestions())-Integer.parseInt(testSectionInst.getOptional_que()))* Float.parseFloat(testSectionInst.getMarksPerQuestion()));
        }
        quizQuesNumTv.setText(""+total_ques);
        quizTotalMarks.setText(""+totalmarks);


        addSectionView(sectionListLL, instructionData);

        if (SharedPreference.getInstance().getBoolean(Const.RE_ATTEMPT)) {
            reAttempt.setVisibility(View.GONE);
        } else
            reAttempt.setVisibility(View.GONE);
        Log.e("langlanth", "" + testBasicInst.getLang_id());
        if (testBasicInst.getLang_id().length() == 3) {
            languageSpinnerTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopMenuForLangauge(languageSpinnerTV, testBasicInst);
                }
            });
        }

        if (testBasicInst.getLang_id().split(",")[0].equals("1")) {
            languageSpinnerTV.setText(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            lang = Integer.parseInt(testBasicInst.getLang_id().split(",")[0]);
        } else if (testBasicInst.getLang_id().split(",")[0].equals("2")) {
            languageSpinnerTV.setText(activity.getResources().getStringArray(R.array.dialog_choose_language_array)[1]);
            lang = Integer.parseInt(testBasicInst.getLang_id().split(",")[0]);
        }


        quizTitle.setText(testBasicInst.getTestSeriesName());
        //quizCorrectValue.setText(testBasicInst.getTotalMarks());
        //quizWrongValue.setText(testBasicInst.getWatermark());
       // quizQuesNumTv.setText(testBasicInst.getTotalQuestions());
        quizTimeTV.setText(testBasicInst.getTimeInMins());
      //  quizTotalMarks.setText(testBasicInst.getTotalMarks());
        generalInstrValueTV.setText(Html.fromHtml(testBasicInst.getDescription()));
        quizsectionValueTV.setText("" + instructionData.getTestSections().size());

        startQuiz.setTag(testBasicInst);
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check_box.isChecked()) {
/*                Intent quizWebView = new Intent(activity, QuizWebView.class);
                quizWebView.putExtra(Const.QUIZSTATUS, quiz.getBasic_info().getId());
                quizWebView.putExtra(Const.STATUS, false);
                quizWebView.putExtra(Const.ID, !TextUtils.isEmpty(String.valueOf(lang)) ? lang : "");
                Constants.REFRESHPAGE = "true";
                activity.startActivity(quizWebView);
                quizBasicInfoDialog.dismiss();*/


                    API_GET_COMPLETE_INFO_TEST_SERIES2();
                    quizBasicInfoDialog.dismiss();
                } else {
                    Toast.makeText(activity, "Please check following instructions.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void API_GET_COMPLETE_INFO_TEST_SERIES() {
        if (Helper.isNetworkConnected(getActivity())) {
            progress = new Progress(activity);
            progress.show();
            APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);

            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
            masterdatadetailencryptionData.setTest_id(status);
            masterdatadetailencryptionData.setCourse_id(SharedPreference.getInstance().getString(Const.ID));
            String doseStr = new Gson().toJson(masterdatadetailencryptionData);
            String doseStrScr = AES.encrypt(doseStr);

            Call<String> response = null;
            response = Service.API_GET_TEST_INSTRUCTION_DATA(doseStrScr);

            assert response != null;
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    mprogress.dismiss();
                    if (response.body() != null) {
                        JSONObject jsonResponse = null;
                        String jsonString;
                        try {
                            jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                            jsonResponse = new JSONObject(jsonString);
                        } catch (Exception e) {
                            jsonString = response.body();
                        }

                        if (jsonResponse == null) {
                            Helper.showToastSecurity(activity);
                            return;
                        }

                        try {
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time"))) * 1000);

                            if (jsonResponse.optString("status").equals(Const.TRUE)) {
                                JSONObject data1 = jsonResponse.getJSONObject("data");
                                InstructionData instructionData = new Gson().fromJson(data1.toString(), InstructionData.class);

                                showPopUp(instructionData);

                            } else {
                                RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progress.hide();
                    Toast.makeText(activity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    public void API_GET_COMPLETE_INFO_TEST_SERIES2() {
        if (Helper.isNetworkConnected(getActivity())) {
            progress = new Progress(activity);
            progress.show();
            APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);

            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
            masterdatadetailencryptionData.setTest_id(status);
            masterdatadetailencryptionData.setCourse_id(SharedPreference.getInstance().getString(Const.ID));
            String doseStr = new Gson().toJson(masterdatadetailencryptionData);
            String doseStrScr = AES.encrypt(doseStr);

            Call<String> response = null;
            response = Service.API_GET_INFO_TEST_SERIES(doseStrScr);

            assert response != null;
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    mprogress.dismiss();
                    if (response.body() != null) {
                        JSONObject jsonResponse = null;
                        String jsonString;
                        try {
                            jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                            jsonResponse = new JSONObject(jsonString);
                        } catch (Exception e) {
                            jsonString = response.body();
                        }

                        if (jsonResponse == null) {
                            Helper.showToastSecurity(activity);
                            return;
                        }

                        try {
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time"))) * 1000);

                            if (jsonResponse.optString("status").equals(Const.TRUE)) {
                                TestseriesBase testseriesBase = null;
                                try {
                                    testseriesBase = new Gson().fromJson(jsonResponse.toString(), TestseriesBase.class);
                                    Intent quizView = new Intent(activity, TestBaseActivity.class);
                                    quizView.putExtra(Const.STATUS, false);
                                    quizView.putExtra(Const.TEST_SERIES_ID, status);
                                    quizView.putExtra(Const.TEST_SERIES_Name, testSeriesName);
                                    quizView.putExtra(Const.TESTSERIES, testseriesBase);
                                    quizView.putExtra("first_attempt", "0");
                                    quizView.putExtra(Const.LANG, lang);

                                    activity.startActivity(quizView);
                                    activity.finish();
                                } catch (Exception e) {
                                    Toast.makeText(activity, "Something went wrong.", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progress.hide();
                    Toast.makeText(activity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            quiz = (QuizModel) getArguments().getSerializable(Const.RESULT_SCREEN);
            status = getArguments().getString(Const.STATUS);
            testSeriesName = getArguments().getString(Const.NAME);
            first_attempt = getArguments().getString("first_attempt");
            show_leader = getArguments().getString("show_leader");
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

    @Override
    public void sendOnclickInd(int position) {

        int sectionwisereviewcount = 0;
        if (resultTestSeries2.getData().getTest_sections().get(position) != null) {
            if (resultTestSeries2.getData().getTest_sections().get(position).getSectionPart() == null) {
                resultTestSeries2.getData().getTest_sections().get(position).setSectionPart("");
            }
            for (int k = 0; k < resultTestSeries2.getData().getQuestions().size(); k++) {
                if (resultTestSeries2.getData().getTest_sections().get(position).getId().equalsIgnoreCase(resultTestSeries2.getData().getQuestions().get(k).getSectionId())) {

                    if (resultTestSeries2.getData().getQuestions().get(k).getState().equalsIgnoreCase("marked_for_review")) {
                        sectionwisereviewcount++;
                    }
                }
            }
            if (sectionwisereviewcount == 0) {
                resultSkippedTV.setText("" + sectionwisereviewcount);
            } else {
                resultSkippedTV.setText("" + sectionwisereviewcount);
            }

            if (resultTestSeries2.getData().getTest_sections().get(position) != null) {
                if (resultTestSeries2.getData().getTest_sections().get(position).getTotalAttempt() != null)
                    attemptedTV.setText(resultTestSeries2.getData().getTest_sections().get(position).getTotalAttempt() + "/" + (Integer.parseInt(resultTestSeries2.getData().getTest_sections().get(position).getNoOfQuestions())-Integer.parseInt(resultTestSeries2.getData().getTest_sections().get(position).getOptional_que())));
                if (resultTestSeries2.getData().getTest_sections().get(position).getAccuracy() != null)
                    accuracyTV.setText(resultTestSeries2.getData().getTest_sections().get(position).getAccuracy() + " %");
                if (resultTestSeries2.getData().getTest_sections().get(position).getTimeSpent() != null)
                    timeTV.setText(Helper.convertSeconds(resultTestSeries2.getData().getTest_sections().get(position).getTimeSpent()));

                if (resultTestSeries2.getData().getTest_sections().get(position).getNoOfQuestions() != null && resultTestSeries2.getData().getTest_sections().get(position).getMarksPerQuestion() != null)
                    scoreTV.setText(resultTestSeries2.getData().getTest_sections().get(position).getUserMarks() + "/" + String.format("%.2f", (Integer.parseInt(resultTestSeries2.getData().getTest_sections().get(position).getNoOfQuestions())-Integer.parseInt(resultTestSeries2.getData().getTest_sections().get(position).getOptional_que())) * Float.parseFloat(resultTestSeries2.getData().getTest_sections().get(position).getMarksPerQuestion())));
            }

        }
    }
}