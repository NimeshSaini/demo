package com.utkarshnew.android.courses.Fragment;

import static com.utkarshnew.android.Utils.Helper.getHtmlUpdatedData;
import static com.utkarshnew.android.Utils.Helper.showWebData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.courses.adapter.IBTNavigationQuizAdapter;
import com.utkarshnew.android.courses.adapter.NavigationQuizAdapter;
import com.utkarshnew.android.Model.Courses.quiz.Questions;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.OnSwipeTouchListener;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;

public class Quiz extends MainFragment implements View.OnClickListener {
    public QuizModel quiz;
    public TextView correctAns, incorrectAns, coins;
    public int currentQues = 0;
    Activity activity;
    String frag_type = "";
    ImageView resumeBtn;
    Questions questions;
    TextView resumeQuiz, quizTime, questCountTV, optionIconTV, quesNum;
    TestSeriesOptionWebView questionTV, answerTV;
    Button previousBtn, nextBtn;
    RelativeLayout resumeQuizLL, finishQuizLL;
    LinearLayout answerLL, mcqItemLL, shareRankLL;
    int timeinMillis, timeinSeconds;
    ProgressBar quesTimeProgressBar;
    CountDownTimer mcountDown;
    ArrayList<View> viewArrayList;
    ArrayList<String> userAnswered;
    JsonArray question_dump;
    JsonObject questionArray;
    ImageView bookmark_ques;
    RecyclerView quesRV;
    RelativeLayout quiz_swipe_RL;
    RelativeLayout clickBlockView;
    LinearLayout quizLL;
    private IBTNavigationQuizAdapter ibtNavigationQuizAdapter;
    private int correctAnsText;
    private int incorrectAnsText;
    View.OnClickListener optionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for (int i = 0; i < viewArrayList.size(); i++) {
                if (v == viewArrayList.get(i)) {
//                    (((LinearLayout)v).getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.ibt_quiz_bg_blue_border));
                    if (quiz.getBasic_info().getTest_type().equals("1")) {
                        final LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);
                        if ((view.getChildAt(1)).getBackground().getConstantState()
                                .equals(getResources().getDrawable(R.drawable.quiz_options_bg_green).getConstantState())) {


                            (view.getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_transparent));
                            questions.getUserAnswered().remove(String.valueOf(viewArrayList.indexOf(view) + 1));
                            Collections.sort(questions.getUserAnswered());
                            questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));

                        } else {

                            (view.getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
                            if (!questions.getUserAnswered().contains(String.valueOf(viewArrayList.indexOf(view) + 1))) {
                                questions.getUserAnswered().add(String.valueOf(viewArrayList.indexOf(view) + 1));
                            }
                            Collections.sort(questions.getUserAnswered());
                            questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));

                        }
                    } else if (questions.getQuestion_type().equals("SC") || questions.getQuestion_type().equals("TF")) {
                        String options = (String) viewArrayList.get(i).getTag(R.id.questions);
                        final LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);
                        if (questions.getAnswer().equals(String.valueOf(viewArrayList.indexOf(view) + 1))) {

                            (view.getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
                            correctAnsText++;
                            correctAns.setText(String.valueOf(correctAnsText));

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    nextBtn.performClick();
                                    ibtNavigationQuizAdapter.notifyDataSetChanged();

                                }
                            }, 1500);


                        } else {


                            (view.getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_red));
                            LinearLayout view2 = (LinearLayout) viewArrayList.get(Integer.parseInt(questions.getAnswer()) - 1).getTag(R.id.optionsAns);
                            final RelativeLayout v3 = (RelativeLayout) view2.getChildAt(1);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    v3.setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
                                }
                            }, 750);


                            incorrectAnsText++;
                            incorrectAns.setText(String.valueOf(incorrectAnsText));

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    nextBtn.performClick();
                                    ibtNavigationQuizAdapter.notifyDataSetChanged();

                                }
                            }, 1500);

                        }
                        questions.setUser_answer(String.valueOf(viewArrayList.indexOf(view) + 1));
                        Log.e("Position of Index", String.valueOf(viewArrayList.indexOf(view) + 1));
                        break;
                    } else {
                        LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);
                        if ((view.getChildAt(1)).getBackground().getConstantState()
                                .equals(getResources().getDrawable(R.drawable.quiz_options_bg_green).getConstantState())) {
                            (view.getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_transparent));
                            questions.getUserAnswered().remove(String.valueOf(viewArrayList.indexOf(view) + 1));
                            Collections.sort(questions.getUserAnswered());
                            questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));
                        } else {
                            (view.getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_green));
                            if (!questions.getUserAnswered().contains(String.valueOf(viewArrayList.indexOf(view) + 1))) {
                                questions.getUserAnswered().add(String.valueOf(viewArrayList.indexOf(view) + 1));
                            }
                            Collections.sort(questions.getUserAnswered());
                            questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));
                        }
                    }
                } else {
                    if (!quiz.getBasic_info().getTest_type().equals("1") &&
                            (questions.getQuestion_type().equals("SC") || questions.getQuestion_type().equals("TF"))) {
                        String options = (String) viewArrayList.get(i).getTag(R.id.questions);
                        LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);

                        (view.getChildAt(1)).setBackground(getResources().getDrawable(R.drawable.quiz_options_bg_transparent));
                    }
                }
            }
            clickBlockView.setVisibility(View.VISIBLE);
            quizLL.setEnabled(false);
            blockTouchonViews();
        }
    };
    private boolean practiceOrQuiz;
    private CardView questionQuizCV;
    private RelativeLayout ques_coontainerRL;


    public Quiz() {
        // Required empty public constructor
    }

    public static Quiz newInstance(String frag_type, QuizModel quiz, String practiceOrQuiz) {
        Quiz quizFragment = new Quiz();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TESTSERIES, quiz);
        bundle.putSerializable(Const.FRAG_TYPE, frag_type);
        bundle.putString(Const.PRACTICE, practiceOrQuiz);
        quizFragment.setArguments(bundle);
        return quizFragment;
    }

//    private void normalTestListener(View v) {
//        if (questions.getQuestion_type().equals("SC") || questions.getQuestion_type().equals("TF")) {
//            String options = (String) viewArrayList.get(i).getTag(R.id.questions);
//            LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);
//            (view.getChildAt(0)).setBackground(getResources().getDrawable(R.drawable.circle_bg_true));
//            questions.setUser_answer(String.valueOf(viewArrayList.indexOf(view) + 1));
//            Log.e("Position of Index", String.valueOf(viewArrayList.indexOf(view) + 1));
//        } else {
//            LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);
//            if ((view.getChildAt(0)).getBackground().getConstantState()
//                    .equals(getResources().getDrawable(R.drawable.circle_bg_true).getConstantState())) {
//                (view.getChildAt(0)).setBackground(getResources().getDrawable(R.drawable.circle_bg));
//                questions.getUserAnswered().remove(String.valueOf(viewArrayList.indexOf(view) + 1));
//                Collections.sort(questions.getUserAnswered());
//                questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));
//            } else {
//                (view.getChildAt(0)).setBackground(getResources().getDrawable(R.drawable.circle_bg_true));
//                if (!questions.getUserAnswered().contains(String.valueOf(viewArrayList.indexOf(view) + 1))) {
//                    questions.getUserAnswered().add(String.valueOf(viewArrayList.indexOf(view) + 1));
//                }
//                Collections.sort(questions.getUserAnswered());
//                questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));
//            }
//        }

    //                    if (quiz.getBasic_info().getTest_type().equals("1") || questions.getQuestion_type().equals("MC")) {
//                        LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);
//                        if ((view.getChildAt(0)).getBackground().getConstantState()
//                                .equals(getResources().getDrawable(R.drawable.circle_bg_true).getConstantState())) {
//                            (view.getChildAt(0)).setBackground(getResources().getDrawable(R.drawable.circle_bg));
//                            questions.getUserAnswered().remove(String.valueOf(viewArrayList.indexOf(view) + 1));
//                            Collections.sort(questions.getUserAnswered());
//                            questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));
//                        } else {
//                            (view.getChildAt(0)).setBackground(getResources().getDrawable(R.drawable.circle_bg_true));
//                            if (!questions.getUserAnswered().contains(String.valueOf(viewArrayList.indexOf(view) + 1))) {
//                                questions.getUserAnswered().add(String.valueOf(viewArrayList.indexOf(view) + 1));
//                            }
//                            Collections.sort(questions.getUserAnswered());
//                            questions.setUser_answer(TextUtils.join(",", questions.getUserAnswered()));
//                        }
//                    } else {
//                        String options = (String) viewArrayList.get(i).getTag(R.id.questions);
//                        LinearLayout view = (LinearLayout) viewArrayList.get(i).getTag(R.id.optionsAns);
//                        (view.getChildAt(0)).setBackgroundResource(R.drawable.circle_bg_true);
//                        questions.setUser_answer(String.valueOf(viewArrayList.indexOf(view) + 1));
//                        Log.e("Position of Index", String.valueOf(viewArrayList.indexOf(view) + 1));
//                    }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    public void initalizeNavigationView() {
        ((QuizActivity) activity).controllerRV.setLayoutManager(new GridLayoutManager(activity, ((QuizActivity) activity).DEFAULT_SPAN_COUNT, LinearLayoutManager.VERTICAL, false));
        ((QuizActivity) activity).navigationQuizAdapter = new NavigationQuizAdapter(activity, quiz);
        ((QuizActivity) activity).controllerRV.setAdapter(((QuizActivity) activity).navigationQuizAdapter);

        ((QuizActivity) activity).attemptedIV.setImageDrawable(TextDrawable.builder()
                .buildRound("A", activity.getResources().getColor(R.color.colorPrimary)));
        ((QuizActivity) activity).notAttemptedIV.setImageDrawable(TextDrawable.builder()
                .buildRound("NA", activity.getResources().getColor(R.color.greayrefcode_dark)));
    }

    public void initalizeHorizontalNavigationView() {
        quesRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        ibtNavigationQuizAdapter = new IBTNavigationQuizAdapter(activity, quiz, this);
        quesRV.setAdapter(ibtNavigationQuizAdapter);

        ((QuizActivity) activity).attemptedIV.setImageDrawable(TextDrawable.builder()
                .buildRound("A", activity.getResources().getColor(R.color.ibt_green)));
        ((QuizActivity) activity).notAttemptedIV.setImageDrawable(TextDrawable.builder()
                .buildRound("NA", activity.getResources().getColor(R.color.greayrefcode_dark)));
    }


    public void controlQuizQuestion(int currentQuesCount) {
        Log.e("Clicked position", String.valueOf(currentQuesCount));

        clearQuestionView();

        if (!TextUtils.isEmpty(quiz.getQuestion_bank().get(currentQues).getUser_answer())) {
            quiz.getQuestion_bank().get(currentQues).setAnswered(true);
            ((QuizActivity) activity).navigationQuizAdapter.notifyDataSetChanged();
            ibtNavigationQuizAdapter.notifyDataSetChanged();

        }

        if (quiz.getQuestion_bank().size() == (currentQuesCount + 1)) {
            currentQues = currentQuesCount;
            nextBtn.setText(getString(R.string.submit_rev));
            setQuestionData(quiz.getQuestion_bank().get(currentQues));
            ibtNavigationQuizAdapter.notifyDataSetChanged();
        } else {
            currentQues = currentQuesCount;
            nextBtn.setText(getString(R.string.next));
            setQuestionData(quiz.getQuestion_bank().get(currentQues));
            ibtNavigationQuizAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int valueTime;
        initViews(view);

        if (((QuizActivity) activity).navigationQuizAdapter == null) {
            initalizeNavigationView();

        }

        setQuestionData(quiz.getQuestion_bank().get(currentQues));
        valueTime = Integer.parseInt(quiz.getBasic_info().getTime_in_mins()) * 60 * 1000;

        for (int i = 0; i < quiz.getQuestion_bank().size(); i++) {
            Questions questions = quiz.getQuestion_bank().get(i);
            if (questions.isAnswered()) {
                if (questions.getAnswer().equalsIgnoreCase(questions.getUser_answer()))
                    correctAnsText++;
                else
                    incorrectAnsText++;
            }
        }
        correctAns.setText(String.valueOf(correctAnsText));
        incorrectAns.setText(String.valueOf(incorrectAnsText));

        initalizeHorizontalNavigationView();

        quesTimeProgressBar.setMax(valueTime);
        quesTimeProgressBar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
//        quesTimeProgressBar.setScaleY(2.25f);
        mcountDown = new CountDownTimer(valueTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                quesTimeProgressBar.setMax(valueTime);
                quesTimeProgressBar.setProgress(valueTime - (int) millisUntilFinished);
//                Log.e("Log_tag", String.valueOf(quesTimeProgressBar.getProgress()));

                String minute = String.valueOf(((millisUntilFinished / 1000) / 60));
                String sec = String.valueOf((millisUntilFinished / 100 / 1000));
                int seconds = (int) ((millisUntilFinished / 1000) % 60);
                int minutes = (int) ((millisUntilFinished / 1000) / 60);
                quizTime.setText(minutes + "mins " + ":" + seconds + "secs " + "");
                timeinMillis = (int) ((millisUntilFinished / 1000));
                timeinSeconds = (int) ((quesTimeProgressBar.getProgress() / 1000) % 60);
                Log.e("timeinMillis", String.valueOf(timeinSeconds));
            }

            @Override
            public void onFinish() {
                quizTime.setText("0" + ":" + "0" + "\''");
                quesTimeProgressBar.setMax(valueTime * 1000);
                timeinMillis = ((valueTime / 1000) % 60);
                timeinSeconds = (int) ((quesTimeProgressBar.getProgress() / 1000) % 60);
                Log.e("timeinMillis", String.valueOf(timeinMillis));
                onTimerFinished();
            }
        }.start();

        RelativeLayout swipeRL = view.findViewById(R.id.swipeRL);

        View.OnTouchListener onTouchListener = new OnSwipeTouchListener(activity) {

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                previousBtn.performClick();
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                nextBtn.performClick();
            }
        };

        answerLL.setOnTouchListener(onTouchListener);
        swipeRL.setOnTouchListener(onTouchListener);
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(onTouchListener);


    }


    private void onTimerFinished() {
        question_dump = new JsonArray();
        for (Questions questions : quiz.getQuestion_bank()) {
            JsonObject question = new JsonObject();
            question.addProperty("question_id", questions.getId());
            question.addProperty("answer", (TextUtils.isEmpty(questions.getUser_answer()) ? "" : questions.getUser_answer()));
            question_dump.add(question);
        }
        mcountDown.cancel();
        NetworkAPICall(API.API_COMPLETE_INFO_TESTSERIES, "", true, false, false);
    }

    private void initViews(View view) {
        questCountTV = (TextView) view.findViewById(R.id.quesCount);
        quesNum = (TextView) view.findViewById(R.id.quiz_question_num);
        resumeQuiz = (TextView) view.findViewById(R.id.playTextTV);
        resumeBtn = (ImageView) view.findViewById(R.id.playBtn);
//        correctAns = (TextView) view.findViewById(R.id.correctAns);
//        incorrectAns = (TextView) view.findViewById(R.id.incorrectAns);
        quizTime = (TextView) view.findViewById(R.id.time_slot);
        resumeQuizLL = (RelativeLayout) view.findViewById(R.id.resumeQuizLL);
        finishQuizLL = (RelativeLayout) view.findViewById(R.id.finishQuizLL);
        questionTV = (TestSeriesOptionWebView) view.findViewById(R.id.questionQuizTV);
        previousBtn = (Button) view.findViewById(R.id.prevQuizBT);
        nextBtn = (Button) view.findViewById(R.id.nextQuizBT);
        answerLL = (LinearLayout) view.findViewById(R.id.quizQuestionLL);
        quesTimeProgressBar = (ProgressBar) view.findViewById(R.id.timer_progress);
        quesRV = view.findViewById(R.id.quesRV);
        bookmark_ques = view.findViewById(R.id.bookmark_ques);
        correctAns = view.findViewById(R.id.correctAns);
        incorrectAns = view.findViewById(R.id.incorrectAns);
        coins = view.findViewById(R.id.coins);
        quiz_swipe_RL = view.findViewById(R.id.quiz_swipe_RL);
        clickBlockView = view.findViewById(R.id.clickBlockView);
        quizLL = view.findViewById(R.id.quizLL);
        questionQuizCV = view.findViewById(R.id.questionQuizCV);
        ques_coontainerRL = view.findViewById(R.id.ques_coontainerRL);


        previousBtn.setVisibility(View.INVISIBLE);
        previousBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        finishQuizLL.setOnClickListener(this);
        resumeQuizLL.setOnClickListener(this);
        bookmark_ques.setOnClickListener(this);


    }

    private void setQuestionData(Questions questionText) {

        questions = quiz.getQuestion_bank().get(currentQues);
        if (viewArrayList.size() > 0) {
            viewArrayList.clear();
        }
        if (currentQues == 0) {
            previousBtn.setVisibility(View.INVISIBLE);
        } else {
            previousBtn.setVisibility(View.VISIBLE);
        }
        if (answerLL.getChildCount() > 0)
            answerLL.removeAllViews();

//        questionTV.loadData(URLDecoder.decode(questionText.getQuestion()), "text/html", "utf-8");
        ques_coontainerRL.removeAllViews();
        TestSeriesOptionWebView questionTV = null;
        questionTV = (TestSeriesOptionWebView) getLayoutInflater().inflate(R.layout.quiz_solution_description, null);
        //questionTV.loadData(questions.getQuestion(), "text/html", "utf-8");
        showWebData(activity, getHtmlUpdatedData(questions.getQuestion()), questionTV);
        //questionTV.loadData(getHtmlUpdatedData(questions.getQuestion()), "text/html; charset=UTF-8", "base64");
        questionTV.setDisableWebViewTouchListener(true);
        ques_coontainerRL.addView(questionTV);
        questCountTV.setText((currentQues + 1) + "/" + quiz.getQuestion_bank().size());
        quesNum.setText("Q." + (currentQues + 1));

        if (quiz.getBasic_info().getTest_type().equals("1") || questionText.getQuestion_type().equalsIgnoreCase("MC")) {
            if (!TextUtils.isEmpty(questionText.getOption_1()))
                answerLL.addView(initAnswerMCViews("1", questionText.getOption_1(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_2()))
                answerLL.addView(initAnswerMCViews("2", questionText.getOption_2(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_3()))
                answerLL.addView(initAnswerMCViews("3", questionText.getOption_3(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_4()))
                answerLL.addView(initAnswerMCViews("4", questionText.getOption_4(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_5()))
                answerLL.addView(initAnswerMCViews("5", questionText.getOption_5(), questions));
        } else if (questionText.getQuestion_type().equalsIgnoreCase("SC") || questionText.getQuestion_type().equalsIgnoreCase("TF")) {
            if (!TextUtils.isEmpty(questionText.getOption_1()))
                answerLL.addView(initAnswerSCViews("1", questionText.getOption_1(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_2()))
                answerLL.addView(initAnswerSCViews("2", questionText.getOption_2(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_3()))
                answerLL.addView(initAnswerSCViews("3", questionText.getOption_3(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_4()))
                answerLL.addView(initAnswerSCViews("4", questionText.getOption_4(), questions));
            if (!TextUtils.isEmpty(questionText.getOption_5()))
                answerLL.addView(initAnswerSCViews("5", questionText.getOption_5(), questions));
        }

        if (questionText.isAnswered()) {
            blockTouchonViews();
            clickBlockView.setVisibility(View.VISIBLE);
            quizLL.setEnabled(false);
        }
    }

    private LinearLayout initAnswerMCViews(String text, String questions, Questions questionsModel) {
        LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.mcq_quiz, null);

        answerTV = (TestSeriesOptionWebView) view.findViewById(R.id.optionTextTV);
        optionIconTV = (TextView) view.findViewById(R.id.optionIconTV);
        mcqItemLL = (LinearLayout) view.findViewById(R.id.mcqlayout_LL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(3, 3, 3, 3);
        mcqItemLL.setLayoutParams(lp);

        if (questionsModel.isAnswered()) {
            String[] answer = questionsModel.getUser_answer().split(",");
            for (int i = 0; i < answer.length; i++) {
                if (answer[i].equals(text)) {
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
                    //answerTV.loadData(questions, "text/html", "utf-8");
                    showWebData(activity, getHtmlUpdatedData(questions), answerTV);
                    //answerTV.loadData(getHtmlUpdatedData(questions), "text/html; charset=UTF-8", "base64");
                    answerTV.setDisableWebViewTouchListener(true);
                    optionIconTV.setText(text + ".");
                    view.setBackgroundResource(R.drawable.quiz_options_bg_green);
                } else {
//                    answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
                    //answerTV.loadData(questions, "text/html", "utf-8");
                    showWebData(activity, getHtmlUpdatedData(questions), answerTV);
                    //answerTV.loadData(getHtmlUpdatedData(questions), "text/html; charset=UTF-8", "base64");
                    answerTV.setDisableWebViewTouchListener(true);
                    optionIconTV.setText(text + ".");
                }
            }
        } else {
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            showWebData(activity, getHtmlUpdatedData(questions), answerTV);
            //answerTV.loadData(getHtmlUpdatedData(questions), "text/html; charset=UTF-8", "base64");
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text + ".");
        }

        mcqItemLL.setTag(R.id.questions, optionIconTV.getText().toString());
        mcqItemLL.setTag(R.id.optionsAns, mcqItemLL);
        mcqItemLL.setOnClickListener(optionClickListener);
        viewArrayList.add(mcqItemLL);
        return view;
    }

    private LinearLayout initAnswerSCViews(String text, String questions, Questions questionsModel) {

        LinearLayout view = (LinearLayout) View.inflate(activity, R.layout.mcq_quiz, null);

        RelativeLayout mcqlayout_LL = (RelativeLayout) view.getChildAt(1);
        answerTV = (TestSeriesOptionWebView) view.findViewById(R.id.optionTextTV);
        optionIconTV = (TextView) view.findViewById(R.id.optionIconTV);
        mcqItemLL = (LinearLayout) view.findViewById(R.id.mcqlayout_LL);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(3, 3, 3, 3);
        mcqItemLL.setLayoutParams(lp);

        if (questionsModel.isAnswered()) {
            if (questionsModel.getUser_answer().equals(text)) {
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
                //answerTV.loadData(questions, "text/html", "utf-8");
                showWebData(activity, getHtmlUpdatedData(questions), answerTV);
                //answerTV.loadData(getHtmlUpdatedData(questions), "text/html; charset=UTF-8", "base64");
                answerTV.setDisableWebViewTouchListener(true);
                optionIconTV.setText(text + ".");
//                optionIconTV.setBackgroundResource(R.drawable.circle_bg_true);
                if (questionsModel.getAnswer().equals(text))
                    mcqlayout_LL.setBackgroundResource(R.drawable.quiz_options_bg_green);
                if (questionsModel.getUser_answer().equals(text)) {
                    if (questionsModel.getUser_answer().equals(questionsModel.getAnswer()))
                        mcqlayout_LL.setBackgroundResource(R.drawable.quiz_options_bg_green);
                    else
                        mcqlayout_LL.setBackgroundResource(R.drawable.quiz_options_bg_red);
                }
            } else {
//                answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
                //answerTV.loadData(questions, "text/html", "utf-8");
                showWebData(activity, getHtmlUpdatedData(questions), answerTV);
                //answerTV.loadData(getHtmlUpdatedData(questions), "text/html; charset=UTF-8", "base64");
                answerTV.setDisableWebViewTouchListener(true);
                optionIconTV.setText(text + ".");
            }
        } else {
//            answerTV.loadData(URLDecoder.decode(questions), "text/html", "utf-8");
            //answerTV.loadData(questions, "text/html", "utf-8");
            showWebData(activity, getHtmlUpdatedData(questions), answerTV);
            //answerTV.loadData(getHtmlUpdatedData(questions), "text/html; charset=UTF-8", "base64");
            answerTV.setDisableWebViewTouchListener(true);
            optionIconTV.setText(text + ".");
        }

        mcqItemLL.setTag(R.id.questions, optionIconTV.getText().toString());
        mcqItemLL.setTag(R.id.optionsAns, mcqItemLL);
        mcqItemLL.setOnClickListener(optionClickListener);
        viewArrayList.add(mcqItemLL);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            quiz = (QuizModel) getArguments().getSerializable(Const.TESTSERIES);
            if (getArguments().containsKey(Const.PRACTICE)) {
                if (getArguments().getString(Const.PRACTICE).equalsIgnoreCase(Const.QUIZ))
                    practiceOrQuiz = false;
                else
                    practiceOrQuiz = true;
            } else
                practiceOrQuiz = true;
        }
        activity = getActivity();
        viewArrayList = new ArrayList<>();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_COMPLETE_INFO_TESTSERIES:
                return service.completeInfoTestSeries(SharedPreference.getInstance().getLoggedInUser().getId(),
                        quiz.getBasic_info().getId(),
                        String.valueOf(timeinSeconds),
                        question_dump.toString());
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        JSONArray dataArray;
        switch (apitype) {
            case API.API_COMPLETE_INFO_TESTSERIES:
                if (jsonobject.optBoolean(Const.STATUS)) {
                    JSONObject data = jsonobject.getJSONObject(Const.DATA);
                    ResultTestSeries resultTestSeries = gson.fromJson(data.toString(), ResultTestSeries.class);
                    SharedPreference.getInstance().putString(Const.QUIZSTATUS, Const.FINISH);
                    mcountDown.cancel();
                    Intent resultScreen = new Intent(activity, QuizActivity.class);
                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                    resultScreen.putExtra(Const.TESTSERIES, quiz);
                    resultScreen.putExtra(Const.RESULT_SCREEN, resultTestSeries);
                    activity.startActivity(resultScreen);
                    activity.finish();
                } else {
                    ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype, typeApi);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finishQuizLL:
                showFinishPopup();
                break;


            case R.id.resumeQuizLL:
                //TODO resume and pause functionality
                resumeBtn.setImageResource(R.mipmap.download_pause);
                showResumePopup(true);

                break;

            case R.id.nextQuizBT:

                clearQuestionView();

                if (!TextUtils.isEmpty(quiz.getQuestion_bank().get(currentQues).getUser_answer())) {
                    quiz.getQuestion_bank().get(currentQues).setAnswered(true);
                    ((QuizActivity) activity).navigationQuizAdapter.notifyDataSetChanged();
                    ibtNavigationQuizAdapter.notifyDataSetChanged();
                }

                if (quiz.getQuestion_bank().size() == (currentQues + 1)) {
                    question_dump = new JsonArray();
                    for (Questions questions : quiz.getQuestion_bank()) {
                        JsonObject question = new JsonObject();
                        question.addProperty("question_id", questions.getId());
                        question.addProperty("answer", (TextUtils.isEmpty(questions.getUser_answer()) ? "" : questions.getUser_answer()));
                        question_dump.add(question);
                    }
                    NetworkAPICall(API.API_COMPLETE_INFO_TESTSERIES, "", true, false, false);
                } else if (quiz.getQuestion_bank().size() == (currentQues + 2)) {
                    currentQues++;
                    nextBtn.setText(getString(R.string.submit_rev));
                    setQuestionData(quiz.getQuestion_bank().get(currentQues));

                } else {
                    currentQues++;
                    setQuestionData(quiz.getQuestion_bank().get(currentQues));

                }
                ibtNavigationQuizAdapter.notifyDataSetChanged();
                break;

            case R.id.prevQuizBT:

                if (!TextUtils.isEmpty(quiz.getQuestion_bank().get(currentQues).getUser_answer())) {
                    quiz.getQuestion_bank().get(currentQues).setAnswered(true);
                    ((QuizActivity) activity).navigationQuizAdapter.notifyDataSetChanged();
                    ibtNavigationQuizAdapter.notifyDataSetChanged();
                }
                if (currentQues != 0) {
                    currentQues--;
                }
                nextBtn.setText(getString(R.string.next));
                setQuestionData(quiz.getQuestion_bank().get(currentQues));
                ibtNavigationQuizAdapter.notifyDataSetChanged();
                break;

            case R.id.bookmark_ques:
                break;
        }
    }

    private void clearQuestionView() {

    }

    private void showFinishPopup() {
        DialogUtils.makeDialog(activity, activity.getString(R.string.app_name), activity.getString(R.string.finishQuiz),
                activity.getResources().getString(R.string.submits), activity.getResources().getString(R.string.resume), true, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {
                        question_dump = new JsonArray();
                        for (Questions questions : quiz.getQuestion_bank()) {
                            JsonObject question = new JsonObject();
                            question.addProperty("question_id", questions.getId());
                            question.addProperty("answer", (TextUtils.isEmpty(questions.getUser_answer()) ? "" : questions.getUser_answer()));
                            question_dump.add(question);
                        }
                        mcountDown.cancel();
                        NetworkAPICall(API.API_COMPLETE_INFO_TESTSERIES, "", true, false, false);
                    }
                }, new DialogUtils.onDialogUtilsCancelClick() {
                    @Override
                    public void onCancelClick() {

                    }
                });

        /*AlertDialog.Builder alertBuild = new AlertDialog.Builder(activity);
        alertBuild
                .setTitle(activity.getString(R.string.app_name))
                .setMessage(activity.getString(R.string.finishQuiz))
                .setCancelable(true)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        question_dump = new JsonArray();
                        for (Questions questions : quiz.getQuestion_bank()) {
                            JsonObject question = new JsonObject();
                            question.addProperty("question_id", questions.getId());
                            question.addProperty("answer", (TextUtils.isEmpty(questions.getUser_answer()) ? "" : questions.getUser_answer()));
                            question_dump.add(question);
                        }
                        mcountDown.cancel();
                        NetworkAPICall(API.API_COMPLETE_INFO_TESTSERIES, true);
                    }
                })
                .setNegativeButton("Resume", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = alertBuild.create();
        dialog.show();
        TextView tv_message = (TextView) dialog.findViewById(android.R.id.message);*/

    }

    public void showResumePopup(boolean callingMethod) {
        if (callingMethod) {
            DialogUtils.makeDialog(activity, activity.getString(R.string.app_name), activity.getString(R.string.pauseQuiz),
                    activity.getResources().getString(R.string.pause), activity.getResources().getString(R.string.cancel), true, new DialogUtils.onDialogUtilsOkClick() {
                        @Override
                        public void onOKClick() {
                            quiz.getBasic_info().setTime_in_mins(String.valueOf(timeinMillis));
                            quiz.setResume(true);
                            quiz.setQuesCount(currentQues);
                            mcountDown.cancel();
                            activity.finish();
                        }
                    }, new DialogUtils.onDialogUtilsCancelClick() {
                        @Override
                        public void onCancelClick() {

                        }
                    });

            /*AlertDialog.Builder alertBuild = new AlertDialog.Builder(activity);
            alertBuild
                    .setTitle(activity.getString(R.string.app_name))
                    .setMessage(activity.getString(R.string.pauseQuiz))
                    .setCancelable(true)
                    .setPositiveButton("Pause", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            quiz.getBasic_info().setTime_in_mins(String.valueOf(timeinMillis));
                            quiz.setResume(true);
                            quiz.setQuesCount(currentQues);
                            Helper.getStorageInstance(activity).addRecordStore(quiz.getBasic_info().getId(), quiz);
                            mcountDown.cancel();
                            activity.finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = alertBuild.create();
            dialog.show();*/

        } else {
            DialogUtils.makeDialog(activity, activity.getString(R.string.app_name), activity.getString(R.string.quitQuiz),
                    activity.getResources().getString(android.R.string.yes), activity.getResources().getString(android.R.string.no), true, new DialogUtils.onDialogUtilsOkClick() {
                        @Override
                        public void onOKClick() {
                            quiz.getBasic_info().setTime_in_mins(String.valueOf(timeinMillis));
                            quiz.setResume(true);
                            quiz.setQuesCount(currentQues);
                            mcountDown.cancel();
                            activity.finish();
                        }
                    }, new DialogUtils.onDialogUtilsCancelClick() {
                        @Override
                        public void onCancelClick() {

                        }
                    });

            /*AlertDialog.Builder alertBuild = new AlertDialog.Builder(activity);
            alertBuild
                    .setTitle(this.getString(R.string.app_name))
                    .setMessage("Are you sure you want to quit the Quiz?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            quiz.getBasic_info().setTime_in_mins(String.valueOf(timeinMillis));
                            quiz.setResume(true);
                            quiz.setQuesCount(currentQues);
                            Helper.getStorageInstance(activity).addRecordStore(quiz.getBasic_info().getId(), quiz);
                            mcountDown.cancel();
                            activity.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = alertBuild.create();
            dialog.show();*/
        }

    }

    void blockTouchonViews() {

        for (View view : viewArrayList) {
            view.setOnClickListener(null);
        }

    }
}
