package com.utkarshnew.android.CreateTest.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.google.gson.Gson;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Player.CustommediaPlayerDialog;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.FIBEdit;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;


public class ViewSolutuionCreateTestFragment extends MainFragment implements Html.ImageGetter {

    private static final String TAG = "ViewSolutionFragment";
    Progress mProgress;
    LinearLayout mcqoptionsLL;
    int count = 0;
    LinearLayout parentLL;
    Answers answers;
    TestseriesBase viewSolutionData;
    String questionType;
    NestedScrollView nestedSV;
    List<View> LinearLayoutList, parentList, tvList;
    int position;
    LinearLayout explanationLL;
    ClickableWebView explanationTV;
    TextView videosolution;
    int selectedAnswerposition;
    Question result;
    TextView userEmailTV, tv_uid, your_ans_text, question_status;
    private ClickableWebView tvQuestion,tvQuestionFib;
    RelativeLayout questionsol_layout1;
    LinearLayout LLmatchinquestion;
    RecyclerView rvmatchinquestion1, rvmatchinquestion2;
    private boolean status;
    ArrayList<WebView> textViews = new ArrayList();
    private Drawable empty, empty2;

    public static ViewSolutuionCreateTestFragment newInstance(int position, TestseriesBase testseriesBase, String questionType, Answers answers, String s) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("questionType", questionType);
        args.putSerializable("testseriesBase", testseriesBase);
        args.putSerializable("answers", answers);
        args.putSerializable("lang", s);
        ViewSolutuionCreateTestFragment fragment = new ViewSolutuionCreateTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_test_solution, null);
        activity = getActivity();

        getBundleData();
        initView(view);
        mProgress = new Progress(activity);

        return view;
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {

            position = bundle.getInt("position", 0);
            viewSolutionData = (TestseriesBase) bundle.getSerializable("testseriesBase");

            String lang = bundle.getString("lang", "");
            if (lang.equalsIgnoreCase("E/H"))
            {
                result = viewSolutionData.getData().getQuestionsHindi().get(position);
            }else if (lang.equalsIgnoreCase("H/E"))
            {
                result = viewSolutionData.getData().getQuestions().get(position);
            }else {
                result = viewSolutionData.getData().getQuestions().get(position);
            }
            answers = (Answers) bundle.getSerializable("answers");
            questionType = bundle.getString("questionType", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(View view) {
        mcqoptionsLL = view.findViewById(R.id.mcqoptions);
        tvQuestion = view.findViewById(R.id.tv_question);
        videosolution = view.findViewById(R.id.videosolution);
        questionsol_layout1 = view.findViewById(R.id.questionsol_layout1);
        LLmatchinquestion = view.findViewById(R.id.LLmatchinquestion);
        rvmatchinquestion1 = view.findViewById(R.id.rvmatchinquestion1);
        rvmatchinquestion2 = view.findViewById(R.id.rvmatchinquestion2);
        explanationTV = view.findViewById(R.id.explanationTV);
        explanationLL = view.findViewById(R.id.explanationLL);
        rvmatchinquestion1.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setHasFixedSize(true);
      /*  explanationLL = view.findViewById(R.id.explanationLL);
        explanationTV = view.findViewById(R.id.explanationTV);*/
        tv_uid = view.findViewById(R.id.tv_uid);
        your_ans_text = view.findViewById(R.id.your_ans);
        question_status = view.findViewById(R.id.question_status);
        nestedSV = view.findViewById(R.id.nestedSV);
        userEmailTV = view.findViewById(R.id.userEmailTV);
        tvQuestionFib = view.findViewById(R.id.tv_question_fib);

        LinearLayoutList = new ArrayList<>();
        parentList = new ArrayList<>();
        tvList = new ArrayList<>();

        tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

        if (Helper.isStringValid(result.getSolution_url())) {
            videosolution.setVisibility(View.VISIBLE);
        } else {
            videosolution.setVisibility(View.GONE);
        }

        videosolution.setOnClickListener(new OnSingleClickListener(() -> {


                    Intent openVideoExplanationActivity;
                    openVideoExplanationActivity = new Intent(activity, CustommediaPlayerDialog.class);
                    openVideoExplanationActivity.putExtra("videoUrl", result.getSolution_url());
                    openVideoExplanationActivity.putExtra("id", result.getId());
                    openVideoExplanationActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(openVideoExplanationActivity);


//                    CustomDialogPlayer cdd=new CustomDialogPlayer(getContext(),result.getSolution_url());
//                    cdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                    lp.copyFrom(cdd.getWindow().getAttributes());
//                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                    lp.gravity = Gravity.CENTER;
//                    cdd.setCancelable(false);
//                    cdd.setCanceledOnTouchOutside(false);
//                    cdd.show();
//                    cdd.getWindow().setAttributes(lp);
//                    return null;

                    return null;
                })
        );


        if (!TextUtils.isEmpty(result.getDescription())) {
            explanationLL.setVisibility(View.VISIBLE);
            explanationTV.setBackgroundColor(Color.TRANSPARENT);
            tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
            tvQuestion.getSettings().setJavaScriptEnabled(true);
            explanationTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(explanationTV, result.getDescription());
            explanationTV.setLongClickable(false);
            explanationTV.setOnLongClickListener(new WebViewClickListener_option(explanationTV, explanationLL));
        } else {
            explanationLL.setVisibility(View.GONE);
        }
        if ( result.getParagraphText() ==null ||  TextUtils.isEmpty(result.getParagraphText())) {
            tvQuestionFib.setVisibility(View.GONE);
        } else {
            tvQuestionFib.setVisibility(View.VISIBLE);
            Helper.TestWebHTMLLoad(tvQuestionFib, result.getParagraphText());
        }


        switch (questionType) {
            case "FIB":
                String htmlAsString2 = result.getQuestion();// used by WebView
                String replaceFIB = htmlAsString2.replace("FIB", ".....");
                tvQuestion.requestFocus();
                tvQuestion.getSettings().setLightTouchEnabled(true);
                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                //tvQuestion.setSoundEffectsEnabled(true);
                //tvQuestion.loadData("Q-"+(position+1)+". "+replaceFIB, "text/html", "UTF-8");
                Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + replaceFIB);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, questionsol_layout1));
                addQuestionOptionForFIB();
                break;
            default:
                mcqoptionsLL.setVisibility(View.VISIBLE);
                LLmatchinquestion.setVisibility(View.GONE);
                String htmlAsString1 = result.getQuestion();
                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + htmlAsString1);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, questionsol_layout1));
                addSolutionOption();
        }

        displaySolution();

    }

    private void addQuestionOptionForFIB() {
        if (textViews != null) textViews.clear();
        for (int j = 1; j <= countFIBWords(result.getQuestion().trim()); j++) {
            if (status)
                break;
            switch (j) {
                case 1:
                    if (result.getOption1().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("1", result.getOption1(),
                            "11", false, j - 1));
                    break;
                case 2:
                    if (result.getOption2().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("2", result.getOption2(),
                            "11", false, j - 1));
                    break;
                case 3:
                    if (result.getOption3().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("3", result.getOption3(),
                            "11", false, j - 1));
                    break;
                case 4:
                    if (result.getOption4().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("4", result.getOption4(),
                            "11", false, j - 1));
                    break;
                case 5:
                    if (result.getOption5().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("5", result.getOption5(),
                            "11", false, j - 1));
                    break;

                case 6:
                    if (result.getOption6() == null || result.getOption6().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("6", result.getOption6(),
                            "11", false, j - 1));
                    break;

                case 7:
                    if (result.getOption7() == null || result.getOption7().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("7", result.getOption7(),
                            "11", false, j - 1));
                    break;

                case 8:
                    if (result.getOption8() == null || result.getOption8().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("8", result.getOption8(),
                            "11", false, j - 1));
                    break;

                case 9:
                    if (result.getOption9() == null || result.getOption9().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("9", result.getOption9(),
                            "11", false, j - 1));
                    break;

                case 10:
                    if (result.getOption10() == null || result.getOption10().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("10", result.getOption10(),
                            "11", false, j - 1));
                    break;

            }
        }
        status = false;
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
    private void displaySolution() {
        if (answers.getState().equalsIgnoreCase("answered")) {
            String your_ans = "";
            for (int i = 0; i < answers.getAnswers().size(); i++) {
                if (answers.getAnswers().get(i).toString().equalsIgnoreCase("1")) {
                    switch (i) {
                        case 0:
                            your_ans = your_ans + "1,";

                            break;
                        case 1:
                            your_ans = your_ans + "2,";
                            break;
                        case 2:
                            your_ans = your_ans + "3,";
                            break;
                        case 3:

                            your_ans = your_ans + "4,";

                            break;
                        case 4:

                            your_ans = your_ans + "5,";

                            break;
                        case 5:

                            your_ans = your_ans + "6,";

                            break;
                        case 6:

                            your_ans = your_ans + "7,";

                            break;
                        case 7:

                            your_ans = your_ans + "8,";

                            break;

                        case 8:

                            your_ans = your_ans + "9,";

                            break;
                        case 9:

                            your_ans = your_ans + "10,";

                            break;
                        case 10:

                            your_ans = your_ans + "11,";

                            break;

                    }
                }
            }
            your_ans_text.setText(your_ans);
        } else {
            if (answers.getState().equalsIgnoreCase("not_visited")) {
                your_ans_text.setText("Not Visited");
            } else {
                your_ans_text.setText(answers.getState());
            }

        }
        switch (questionType) {
            case "MC":
                if (answers.getState().equalsIgnoreCase("answered")) {
                    String your_ans = "", status = "";

                    for (int i = 0; i < answers.getAnswers().size(); i++) {
                        if (answers.getAnswers().get(i).toString().equalsIgnoreCase("1")) {
                            switch (i) {
                                case 0:
                                    your_ans = your_ans + "1,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }


                                    break;
                                case 1:

                                    your_ans = your_ans + "2,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 2:

                                    your_ans = your_ans + "3,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 3:

                                    your_ans = your_ans + "4,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 4:

                                    your_ans = your_ans + "5,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 5:

                                    your_ans = your_ans + "6,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 6:

                                    your_ans = your_ans + "7,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 7:

                                    your_ans = your_ans + "8,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;

                                case 8:

                                    your_ans = your_ans + "9,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 9:

                                    your_ans = your_ans + "10,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 10:

                                    your_ans = your_ans + "11,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;

                            }
                        }
                    }
                    question_status.setVisibility(View.VISIBLE);
                    your_ans_text.setText(your_ans.substring(0, your_ans.length() - 1));
                    question_status.setText("|| " + status);

                } else {
                    if (answers.getState().equalsIgnoreCase("not_visited")) {
                        your_ans_text.setText("Not Visisted");
                    } else {
                        your_ans_text.setText(answers.getState());
                    }

                }

                String[] ans = result.getAnswer().split(",");
                for (int i = 0; i < ans.length; i++) {
                    for (int j = 0; j < LinearLayoutList.size(); j++) {
                        if (Integer.parseInt(ans[i]) == j + 1)
                            LinearLayoutList.get(j).setSelected(true);

                    }
                }

                break;
            case "FIB":
                if (answers.getState().equalsIgnoreCase("answered")) {
                    String your_ans = "", status = "";
                    for (int i = 0; i < answers.getAnswers().size(); i++) {
                        if (!answers.getAnswers().get(i).toString().equalsIgnoreCase("")) {
                            your_ans = your_ans + result.getAnswers().get(i) + ",";
                            if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                /*parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                tv.setBackgroundResource(R.drawable.circle4);
                                tv.setTextColor(getResources().getColor(R.color.white));*/
                                status = "Incorrect";
                            } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                status = "Correct";
                            }
                        }
                    }
                    question_status.setVisibility(View.VISIBLE);
                    your_ans_text.setText(your_ans.substring(0, your_ans.length() - 1));
                    question_status.setText("|| " + status);

                } else {
                    if (answers.getState().equalsIgnoreCase("not_visited")) {
                        your_ans_text.setText("Not Visited");
                    } else {
                        your_ans_text.setText(answers.getState());
                    }

                }

                String[] answer = result.getAnswer().split(",");
                for (int i = 0; i < answer.length; i++) {
                    for (int j = 0; j < LinearLayoutList.size(); j++) {
                        if (!answer[i].equalsIgnoreCase("")) {
                            LinearLayoutList.get(j).setSelected(true);
                            //textViews.get(j).loadData(answer[i], "text/html", "UTF-8");
                            textViews.get(j).getSettings().setJavaScriptEnabled(true);
                            Helper.TestWebHTMLLoad(textViews.get(j), answer[i]);

                        }
                        break;
                    }
                }
                break;
            case "":
            case "SC":
            case "TF":
                if (answers.getState().equalsIgnoreCase("answered")) {
                    String your_ans = "", status = "";
                    for (int i = 0; i < answers.getAnswers().size(); i++) {
                        if (answers.getAnswers().get(i).toString().equalsIgnoreCase("1")) {
                            switch (i) {
                                case 0:
                                    your_ans = "1";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 1:

                                    your_ans = "2";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 2:

                                    your_ans = "3";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 3:

                                    your_ans = "4";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 4:

                                    your_ans = "5";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 5:

                                    your_ans = "6";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 6:

                                    your_ans = "7";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 7:

                                    your_ans = "8";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }
                                    break;

                                case 8:

                                    your_ans = "9";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 9:

                                    your_ans = "10";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 10:

                                    your_ans = "11";
                                    if (Integer.parseInt(result.getIsCorrect()) == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (Integer.parseInt(result.getIsCorrect()) == 1) {
                                        status = "Correct";
                                    }

                                    break;

                            }
                        }
                    }
                    question_status.setVisibility(View.VISIBLE);
                    your_ans_text.setText(your_ans);
                    question_status.setText("|| " + status);
                } else {
                    if (answers.getState().equalsIgnoreCase("not_visited")) {
                        your_ans_text.setText("Not Visited");
                    } else {
                        your_ans_text.setText(answers.getState());
                    }

                }

                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    if (!TextUtils.isEmpty(result.getAnswer())) {
                        if (i + 1 == Integer.parseInt(result.getAnswer()))
                            LinearLayoutList.get(i).setSelected(true);
                    }
                }
                break;

        }

    }

    @Override
    public Drawable getDrawable(String s) {
        LevelListDrawable d = new LevelListDrawable();
        empty = getResources().getDrawable(R.mipmap.course_placeholder);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        new LoadImage().execute(s, d);
        return d;
    }

    private void addSolutionOption() {
        for (int j = 1; j <= 10; j++) {
            if (status)
                break;
            switch (j) {
                case 1:
                    if (result.getOption1().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("1", result.getOption1(),
                            "11", false, j - 1));
                    break;
                case 2:
                    if (result.getOption2().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("2", result.getOption2(),
                            "11", false, j - 1));
                    break;
                case 3:
                    if (result.getOption3() == null || result.getOption3().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("3", result.getOption3(),
                            "11", false, j - 1));
                    break;
                case 4:
                    if (result.getOption4() == null || result.getOption4().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("4", result.getOption4(),
                            "11", false, j - 1));
                    break;
                case 5:
                    if (result.getOption5() == null || result.getOption5().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("5", result.getOption5(),
                            "11", false, j - 1));
                    break;
                case 6:
                    if (result.getOption6() == null || result.getOption6().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("6", result.getOption6(),
                            "11", false, j - 1));
                    break;
                case 7:
                    if (result.getOption7() == null || result.getOption7().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("7", result.getOption7(),
                            "11", false, j - 1));
                    break;
                case 8:
                    if (result.getOption8() == null || result.getOption8().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("8", result.getOption8(),
                            "11", false, j - 1));
                    break;
                case 9:
                    if (result.getOption9() == null || result.getOption9().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("9", result.getOption9(),
                            "11", false, j - 1));
                    break;
                case 10:
                    if (result.getOption10() == null || result.getOption10().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("10", result.getOption10(),
                            "11", false, j - 1));
                    break;

            }
        }
        status = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("CutPasteId")
    public LinearLayout initMCQOptionView(String text1, String text2, final String pollValue, boolean pollVisiblity, int tag) {

        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_test_view_solution, null);
        TextView tv = v.findViewById(R.id.optionIconTV);
        LinearLayout option_layout = v.findViewById(R.id.viewLL);
        WebView text = v.findViewById(R.id.optionTextTV);
        RadioButton radioRB = v.findViewById(R.id.radioRB);
        parentLL = v.findViewById(R.id.viewLL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);

        if (pollVisiblity) {
            radioRB.setVisibility(View.GONE);
        }
        v.setLayoutParams(lp);
        tv.setText(text1);
        tv.setGravity(Gravity.CENTER);
        if (questionType.equalsIgnoreCase("FIB")) {
            FIBEdit fibEdit = new Gson().fromJson(text2, FIBEdit.class);
            if (fibEdit != null) {

                if (fibEdit.getType().equalsIgnoreCase("number")) {
                    //text.loadData("Min value "+fibEdit.getMin() + " Max value " +fibEdit.getMax(), "text/html", "UTF-8");
                    text.getSettings().setJavaScriptEnabled(true);
                    Helper.TestWebHTMLLoad(text, "Min value " + fibEdit.getMin() + " Max value " + fibEdit.getMax());
                    v.setSelected(true);
                } else if (fibEdit.getType().equalsIgnoreCase("character")) {
                    //text.loadData("Min text length "+fibEdit.getMin() + " Max text length " +fibEdit.getMax(), "text/html", "UTF-8");
                    text.getSettings().setJavaScriptEnabled(true);
                    Helper.TestWebHTMLLoad(text, "Min text length " + fibEdit.getMin() + " Max text length " + fibEdit.getMax());
                    v.setSelected(true);
                } else {
                    //text.loadData(fibEdit.getAnswer(), "text/html", "UTF-8");
                    text.getSettings().setJavaScriptEnabled(true);
                    Helper.TestWebHTMLLoad(text, fibEdit.getAnswer());
                    v.setSelected(true);
                }

                //    textViews.add(text);
            }
        } else {
            //text.loadData(text2, "text/html", "UTF-8");
            text.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(text, text2);
        }
        text.setLongClickable(false);
        text.setOnLongClickListener(new WebViewClickListener_option(text, option_layout));
        v.setTag(tag);
        textViews.add(text);

        LinearLayoutList.add(v);
        parentList.add(parentLL);
        tvList.add(tv);
        return v;
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

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d("TAG", "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                mDrawable.setLevel(1);

            }
        }
    }


    private static class WebViewClickListener implements View.OnLongClickListener {
        WebView web;
        RelativeLayout vgg;

        WebViewClickListener(WebView option1_webview, RelativeLayout ll_option1) {
            web = option1_webview;
            vgg = ll_option1;
            web.setLongClickable(false);
            web.setHapticFeedbackEnabled(false);
        }

        @Override
        public boolean onLongClick(View v) {
            v.setLongClickable(false);
            v.setHapticFeedbackEnabled(false);
            return true;
        }
    }

    private static class WebViewClickListener_option implements View.OnLongClickListener {
        WebView web;
        LinearLayout vgg;

        WebViewClickListener_option(WebView option1_webview, LinearLayout ll_option1) {
            web = option1_webview;
            vgg = ll_option1;
            web.setLongClickable(false);
            web.setHapticFeedbackEnabled(false);
        }

        @Override
        public boolean onLongClick(View v) {
            v.setLongClickable(false);
            v.setHapticFeedbackEnabled(false);
            return true;
        }
    }

}