package com.utkarshnew.android.courses.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.Courses.quiz.Questions;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;

public class QuizSolutions extends MainFragment implements View.OnClickListener {
    Activity activity;
    String frag_type = "", quiz_id = "";
    QuizModel quiz;
    ImageView resumeBtn, correctIV;
    Questions questions;
    TextView resumeQuiz, quizTime, questCountTV, optionIconTV;
    TestSeriesOptionWebView answerTV;
    Button previousBtn, nextBtn;
    RelativeLayout resumeQuizLL, finishQuizLL;
    LinearLayout answerLL, mcqItemLL, correctLL, descriptionTVLL;
    int timeinMillis;
    Questions questionsResult;
    ProgressBar quesTimeProgressBar;
    CountDownTimer mcountDown;
    ArrayList<View> viewArrayList;
    ArrayList<Questions> resultTestSeriesArrayList;
    int currentQues = 0;
    ArrayList<String> userAnswered;
    JsonArray question_dump;
    JsonObject questionArray;

    RelativeLayout quiz_description_containerRL;
    CardView questionQuizCV;


    public QuizSolutions() {
        // Required empty public constructor
    }

    public static QuizSolutions newInstance(String frag_type, String quiz) {
        QuizSolutions quizSolutions = new QuizSolutions();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TESTSERIES, quiz);
        bundle.putSerializable(Const.FRAG_TYPE, frag_type);
        quizSolutions.setArguments(bundle);
        return quizSolutions;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            quiz_id = getArguments().getString(Const.TESTSERIES);
        }
        activity = getActivity();
        viewArrayList = new ArrayList<>();
        resultTestSeriesArrayList = new ArrayList<>();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_SOLUTION_TESTSERIES:
                EncryptionData masterdatadetailencryptionData = new EncryptionData();
                masterdatadetailencryptionData.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
                masterdatadetailencryptionData.setTest_segment_id(quiz_id);

                String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                String masterdatadoseStrScr = AES.encrypt(getmasterdataencryptionDatadoseStr);
                return service.API_GET_SOLUTION_TESTSERIES(masterdatadoseStrScr);

        }
        return null;
    }


    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        JSONArray dataArray;
        switch (apitype) {
            case API.API_GET_SOLUTION_TESTSERIES:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    dataArray = jsonobject.getJSONArray(Const.DATA);
                    for (int i = 0; i < dataArray.length(); i++) {
                        questionsResult = gson.fromJson(dataArray.get(i).toString(), Questions.class);
                        resultTestSeriesArrayList.add(questionsResult);
                    }
                    setQuestionData();
                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_solutions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final int valueTime;
        initViews(view);

        NetworkAPICall(API.API_GET_SOLUTION_TESTSERIES, "", true, false, false);

    }

    private void initViews(View view) {
        questCountTV = (TextView) view.findViewById(R.id.quesCount);
        resumeQuiz = (TextView) view.findViewById(R.id.playTextTV);
        resumeBtn = (ImageView) view.findViewById(R.id.playBtn);
        quizTime = (TextView) view.findViewById(R.id.time_slot);
        resumeQuizLL = (RelativeLayout) view.findViewById(R.id.resumeQuizLL);
        finishQuizLL = (RelativeLayout) view.findViewById(R.id.finishQuizLL);
//        questionTV = (TestSeriesOptionWebView) view.findViewById(R.id.questionQuizTV);
        previousBtn = (Button) view.findViewById(R.id.prevQuizBT);
        nextBtn = (Button) view.findViewById(R.id.nextQuizBT);
        answerLL = (LinearLayout) view.findViewById(R.id.quizQuestionLL);
        descriptionTVLL = (LinearLayout) view.findViewById(R.id.descriptionTVLL);
        quesTimeProgressBar = (ProgressBar) view.findViewById(R.id.timer_progress);
        questionQuizCV = (CardView) view.findViewById(R.id.questionQuizCV);
        quiz_description_containerRL = (RelativeLayout) view.findViewById(R.id.quiz_description_containerRL);
        /*  descriptionTV = view.findViewById(R.id.descriptionTV);*/


        previousBtn.setVisibility(View.INVISIBLE);
        previousBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }

    private void setQuestionData() {
        TestSeriesOptionWebView descriptionTV;
        questions = resultTestSeriesArrayList.get(currentQues);
        if (viewArrayList.size() > 0)
            viewArrayList.clear();
        if (currentQues == 0) {
            previousBtn.setVisibility(View.INVISIBLE);
        } else {
            previousBtn.setVisibility(View.VISIBLE);
        }
        if (answerLL.getChildCount() > 0)
            answerLL.removeAllViews();

//        questionTV.loadData(URLDecoder.decode(questions.getQuestion()), "text/html", "utf-8");
        questionQuizCV.removeAllViews();
        TestSeriesOptionWebView questionTV = null;
        questionTV = (TestSeriesOptionWebView) getLayoutInflater().inflate(R.layout.quiz_solution_description, null);
        //questionTV.loadData(questions.getQuestion(), "text/html", "utf-8");
        questionTV.getSettings().setJavaScriptEnabled(true);
        Helper.TestWebHTMLLoad(questionTV, questions.getQuestion());
        questionTV.setDisableWebViewTouchListener(true);
        questionQuizCV.addView(questionTV);
        questCountTV.setText((currentQues + 1) + "/" + resultTestSeriesArrayList.size());

        if (!TextUtils.isEmpty(questions.getDescription())) {
            descriptionTVLL.setVisibility(View.VISIBLE);
//            descriptionTV.loadData(URLDecoder.decode(questions.getDescription()), "text/html", "utf-8");
            descriptionTV = (TestSeriesOptionWebView) getLayoutInflater().inflate(R.layout.quiz_solution_description, null);
            //descriptionTV.loadData(questions.getDescription(), "text/html", "utf-8");
            descriptionTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(descriptionTV, questions.getQuestion());
            quiz_description_containerRL.addView(descriptionTV);
        } else {
            descriptionTVLL.setVisibility(View.GONE);
            quiz_description_containerRL.removeAllViews();
        }

        if (questions.getQuestion_type().equals("1") || questions.getQuestion_type().equalsIgnoreCase("MC")) {
            if (!TextUtils.isEmpty(questions.getOption_1()))
                answerLL.addView(initAnswerMCViews("1", questions.getOption_1(), questions));
            if (!TextUtils.isEmpty(questions.getOption_2()))
                answerLL.addView(initAnswerMCViews("2", questions.getOption_2(), questions));
            if (!TextUtils.isEmpty(questions.getOption_3()))
                answerLL.addView(initAnswerMCViews("3", questions.getOption_3(), questions));
            if (!TextUtils.isEmpty(questions.getOption_4()))
                answerLL.addView(initAnswerMCViews("4", questions.getOption_4(), questions));
            if (!TextUtils.isEmpty(questions.getOption_5()))
                answerLL.addView(initAnswerMCViews("5", questions.getOption_5(), questions));
        } else if (questions.getQuestion_type().equalsIgnoreCase("SC") || questions.getQuestion_type().equalsIgnoreCase("TF")) {
            if (!TextUtils.isEmpty(questions.getOption_1()))
                answerLL.addView(initAnswerSCViews("1", questions.getOption_1(), questions));
            if (!TextUtils.isEmpty(questions.getOption_2()))
                answerLL.addView(initAnswerSCViews("2", questions.getOption_2(), questions));
            if (!TextUtils.isEmpty(questions.getOption_3()))
                answerLL.addView(initAnswerSCViews("3", questions.getOption_3(), questions));
            if (!TextUtils.isEmpty(questions.getOption_4()))
                answerLL.addView(initAnswerSCViews("4", questions.getOption_4(), questions));
            if (!TextUtils.isEmpty(questions.getOption_5()))
                answerLL.addView(initAnswerSCViews("5", questions.getOption_5(), questions));
        }
    }

    private LinearLayout initAnswerMCViews(String text, String questions, Questions questionsModel) {
        LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.mcq_quiz, null);

        answerTV = view.findViewById(R.id.optionTextTV);
        optionIconTV = view.findViewById(R.id.optionIconTV);
        mcqItemLL = view.findViewById(R.id.mcqlayout_LL);
        correctIV = view.findViewById(R.id.correctIV);
        correctLL = view.findViewById(R.id.correctLL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(3, 3, 3, 3);
        mcqItemLL.setLayoutParams(lp);
        view.setBackground(null);

        String[] answer = questionsModel.getUser_answer().split(",");
        String[] correctAnswer = questionsModel.getAnswer().split(",");

        if (Arrays.asList(correctAnswer).contains(text) && Arrays.asList(answer).contains(text)) { // for the correct in both
            correctLL.setVisibility(View.VISIBLE);
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            answerTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(answerTV, questions);
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text);
            correctIV.setVisibility(View.VISIBLE);
            view.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
            correctIV.setPadding(10, 10, 10, 10);
            correctIV.setBackgroundResource(R.drawable.check_on);
        } else if (Arrays.asList(correctAnswer).contains(text) && !Arrays.asList(answer).contains(text)) { // for the correct in answer only
            correctLL.setVisibility(View.VISIBLE);
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            answerTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(answerTV, questions);
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text);
            correctIV.setVisibility(View.VISIBLE);
            view.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
            correctIV.setPadding(10, 10, 10, 10);
//            correctIV.setBackgroundResource(R.mipmap.green_tick);
        } else if (!Arrays.asList(correctAnswer).contains(text) && Arrays.asList(answer).contains(text)) {  // for the correct in user answer only i.e wrong
            correctLL.setVisibility(View.VISIBLE);
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            answerTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(answerTV, questions);
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text);
            correctIV.setVisibility(View.VISIBLE);
            view.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_red));
            correctIV.setPadding(10, 10, 10, 10);
            correctIV.setBackgroundResource(R.drawable.check_off);
        } else { // for the neutral one not found in any case
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            answerTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(answerTV, questions);
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text);
            correctIV.setVisibility(View.INVISIBLE);
        }

//        for (int i = 0; i < answer.length; i++) {
//            if (answer[i].equals(text)) {
//                for (int j = 0; j < correctAnswer.length; j++) {
//                    if (answer[i].equals(correctAnswer[j])) {
//                        correctLL.setVisibility(View.VISIBLE);
//                        answerTV.loadData(questions, "text/html", "utf-8");
//                        answerTV.setDisableWebViewTouchListener(true);
//                        optionIconTV.setText(text);
//                        correctIV.setVisibility(View.VISIBLE);
//                        view.setBackground(getResources().getDrawable(R.drawable.tick_bg));
//                        correctIV.setPadding(10, 10, 10, 10);
//                        correctIV.setBackgroundResource(R.mipmap.green_tick);
//                    } else {
//                        correctLL.setVisibility(View.VISIBLE);
//                        answerTV.loadData(questions, "text/html", "utf-8");
//                        answerTV.setDisableWebViewTouchListener(true);
//                        optionIconTV.setText(text);
//                        correctIV.setVisibility(View.VISIBLE);
//                        view.setBackground(getResources().getDrawable(R.drawable.wrong_bg));
//                        correctIV.setPadding(10, 10, 10, 10);
//                        correctIV.setBackgroundResource(R.mipmap.red_cross);
//                    }
//                }
//            } else {
//                answerTV.loadData(questions, "text/html", "utf-8");
//                answerTV.setDisableWebViewTouchListener(true);
//                optionIconTV.setText(text);
//                correctLL.setVisibility(View.GONE);
//                correctIV.setVisibility(View.INVISIBLE);
//            }
//        }
       /* } else {
            answerTV.loadData(questions, "text/html", "utf-8");
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text);
            correctIV.setVisibility(View.INVISIBLE);
        }*/

        mcqItemLL.setTag(R.id.questions, optionIconTV.getText().toString());
        mcqItemLL.setTag(R.id.optionsAns, mcqItemLL);
//        mcqItemLL.setOnClickListener(optionClickListener);
        viewArrayList.add(mcqItemLL);
        return view;
    }

    private LinearLayout initAnswerSCViews(String text, String questions, Questions questionsModel) {

        LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.mcq_quiz, null);

        answerTV = view.findViewById(R.id.optionTextTV);
        optionIconTV = view.findViewById(R.id.optionIconTV);
        mcqItemLL = view.findViewById(R.id.mcqlayout_LL);
        correctIV = view.findViewById(R.id.correctIV);
        correctLL = view.findViewById(R.id.correctLL);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(3, 3, 3, 3);
        mcqItemLL.setLayoutParams(lp);

        view.setBackground(null);

        if (questionsModel.getUser_answer().equals(text)) {
            if (questionsModel.getAnswer().equals(questionsModel.getUser_answer())) {
                correctLL.setVisibility(View.VISIBLE);
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
                //answerTV.loadData(questions, "text/html", "utf-8");
                answerTV.getSettings().setJavaScriptEnabled(true);
                Helper.TestWebHTMLLoad(answerTV, questions);
                answerTV.setDisableWebViewTouchListener(true);
                optionIconTV.setText(text);
                correctIV.setVisibility(View.VISIBLE);
                view.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
                correctIV.setPadding(10, 10, 10, 10);
                correctIV.setBackgroundResource(R.drawable.check_on);
            } else {
                correctLL.setVisibility(View.VISIBLE);
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
                //answerTV.loadData(questions, "text/html", "utf-8");
                answerTV.getSettings().setJavaScriptEnabled(true);
                Helper.TestWebHTMLLoad(answerTV, questions);
                answerTV.setDisableWebViewTouchListener(true);
                optionIconTV.setText(text);
                correctIV.setVisibility(View.VISIBLE);
                view.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_red));
                correctIV.setPadding(10, 10, 10, 10);
                correctIV.setBackgroundResource(R.drawable.check_off);
            }
        } else if (questionsModel.getAnswer().equals(text)) {
            correctLL.setVisibility(View.VISIBLE);
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            answerTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(answerTV, questions);
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text);
            correctIV.setVisibility(View.VISIBLE);
            view.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
            correctIV.setPadding(10, 10, 10, 10);
            correctIV.setBackgroundResource(R.drawable.check_on);
        } else {
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            answerTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(answerTV, questions);
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text);
            correctLL.setVisibility(View.GONE);
            correctIV.setVisibility(View.INVISIBLE);
        }

        mcqItemLL.setTag(R.id.questions, optionIconTV.getText().toString());
        mcqItemLL.setTag(R.id.optionsAns, mcqItemLL);
//        mcqItemLL.setOnClickListener(optionClickListener);
        viewArrayList.add(mcqItemLL);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextQuizBT:

                if (resultTestSeriesArrayList.size() == (currentQues + 1)) {
                    question_dump = new JsonArray();
                    /*for (Questions questions : quiz.getQuestion_bank()) {
                        JsonObject question = new JsonObject();
                        question.addProperty("question_id", questions.getId());
                        question.addProperty("answer", (TextUtils.isEmpty(questions.getUser_answer()) ? "" : questions.getUser_answer()));
                        question_dump.add(question);
                    }*/
//                    NetworkAPICall(API.API_COMPLETE_INFO_TESTSERIES, true);
                    activity.finish();
                } else if (resultTestSeriesArrayList.size() == (currentQues + 2)) {
                    currentQues++;
                    nextBtn.setText(getString(R.string.finish));
                    setQuestionData();
                } else {
                    currentQues++;
                    setQuestionData();
                }
                break;

            case R.id.prevQuizBT:
                if (currentQues != 0) {
                    currentQues--;
                }
                nextBtn.setText(getString(R.string.next));
                setQuestionData();
                break;
        }
    }
}
