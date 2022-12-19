package com.utkarshnew.android.testmodule.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
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
import com.utkarshnew.android.Player.CustomDialogPlayer;
import com.utkarshnew.android.Player.CustommediaPlayerDialog;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.model.FIBEdit;
import com.utkarshnew.android.testmodule.model.QuestionDumps;
import com.utkarshnew.android.testmodule.model.Questions2;
import com.utkarshnew.android.testmodule.model.ResultTestSeries_Report;

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


public class ViewSolutionFragment extends MainFragment implements Html.ImageGetter {

    private static final String TAG = "ViewSolutionFragment";
    Progress mProgress;
    LinearLayout mcqoptionsLL;
    int count = 0;
    LinearLayout parentLL;
    ResultTestSeries_Report viewSolutionData;
    String questionType;
    NestedScrollView nestedSV;
    List<View> LinearLayoutList, parentList, tvList;
    int position;
    LinearLayout explanationLL;
    int selectedAnswerposition;
    Questions2 result;
    QuestionDumps questionDump;
    ClickableWebView explanationTV;
    TextView videosolution, userEmailTV, tv_uid, your_ans_text, onscreen_value, question_status, max_marks, min_marks;
    private ClickableWebView tvQuestion,tvQuestionFib;
    RelativeLayout questionsol_layout1;
    LinearLayout LLmatchinquestion;
    RecyclerView rvmatchinquestion1, rvmatchinquestion2;
    private boolean status;
    ArrayList<WebView> textViews = new ArrayList();
    private Drawable empty, empty2;

    public static ViewSolutionFragment newInstance(int position, ResultTestSeries_Report viewSolutionData, String questionType) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("questionType", questionType);
        args.putSerializable("DATA", viewSolutionData);
        ViewSolutionFragment fragment = new ViewSolutionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_solution, null);
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
            viewSolutionData = (ResultTestSeries_Report) bundle.getSerializable("DATA");
            questionDump = viewSolutionData.getData().getQuestion_dump().get(position);
            questionType = bundle.getString("questionType", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        NetworkAPICall(API.API_GET_LANDING_PAGE_DATA_EXAM, true);
    }

    private void initView(View view) {
        mcqoptionsLL = view.findViewById(R.id.mcqoptions);
        videosolution = view.findViewById(R.id.videosolution);
        tvQuestion = view.findViewById(R.id.tv_question);
        questionsol_layout1 = view.findViewById(R.id.questionsol_layout1);
        LLmatchinquestion = view.findViewById(R.id.LLmatchinquestion);
        rvmatchinquestion1 = view.findViewById(R.id.rvmatchinquestion1);
        rvmatchinquestion2 = view.findViewById(R.id.rvmatchinquestion2);
        rvmatchinquestion1.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setHasFixedSize(true);
        explanationLL = view.findViewById(R.id.explanationLL);
        explanationTV = view.findViewById(R.id.explanationTV);
        tv_uid = view.findViewById(R.id.tv_uid);
        tvQuestionFib = view.findViewById(R.id.tv_question_fib);

        your_ans_text = view.findViewById(R.id.your_ans);
        max_marks = view.findViewById(R.id.maxmarks);
        min_marks = view.findViewById(R.id.minmarks);
        onscreen_value = view.findViewById(R.id.onscreen_value);
        question_status = view.findViewById(R.id.question_status);
        nestedSV = view.findViewById(R.id.nestedSV);
        userEmailTV = view.findViewById(R.id.userEmailTV);
        LinearLayoutList = new ArrayList<>();
        parentList = new ArrayList<>();
        tvList = new ArrayList<>();
        // result = ((ViewSolutionActivity) getActivity()).viewSolutionData.getResult().get(position);
        result = viewSolutionData.getData().getQuestions().get(position);

        if ( result.getParagraph_text() ==null ||  TextUtils.isEmpty(result.getParagraph_text())) {
            tvQuestionFib.setVisibility(View.GONE);
        } else {
            tvQuestionFib.setVisibility(View.VISIBLE);
            Helper.TestWebHTMLLoad(tvQuestionFib, result.getParagraph_text());
        }

        //String htmlAsString = ((ViewSolutionActivity) getActivity()).viewSolutionData.getResult().get(position).getQuestion();// used by WebView
        //Spanned spannedExplain = Html.fromHtml( result.getDescription(), this, null);
        // explanationTV.setText(Html.fromHtml(result.getDescription()));
/*        URLImageParser p = new URLImageParser(explanationTV, activity);
        Spanned htmlSpan = Html.fromHtml(result.getDescription(), p, null);
        explanationTV.setText(htmlSpan);*/
/*
        explanationTV.setText(Html.fromHtml(result.getDescription(),new Html.ImageGetter(){
            @Override
            public Drawable getDrawable(String source) {
                LevelListDrawable d = new LevelListDrawable();
                empty2 = getResources().getDrawable(R.drawable.landscape_placeholder);
                d.addLevel(0, 0, empty2);
                d.setBounds(0, 0, empty2.getIntrinsicWidth(), empty2.getIntrinsicHeight());
                new LoadImageExplain().execute(source, d);
                return d;
            }
        },null));
*/


        // explanationTV.requestFocus();
        //explanationTV.getSettings().setLightTouchEnabled(true);
        if (!result.getDescription().isEmpty()) {
            explanationLL.setVisibility(View.VISIBLE);
            explanationTV.setBackgroundColor(Color.TRANSPARENT);
            tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
            tvQuestion.getSettings().setJavaScriptEnabled(true);
            explanationTV.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(explanationTV, result.getDescription());
            //explanationTV.loadData(result.getDescription(), "text/html", "UTF-8");
            explanationTV.setLongClickable(false);
            explanationTV.setOnLongClickListener(new WebViewClickListener_option(explanationTV, explanationLL));

        } else {
            explanationLL.setVisibility(View.GONE);
        }


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
                    return null;
                })
        );


        // userEmailTV.setText(viewSolutionData.ge().getEmail());
        //userEmailTV.setText(((ViewSolutionActivity) getActivity()).viewSolutionData.getUserInfo().getEmail());
        // tv_uid.setText("eMedicoz QUID " + result.getId());

//        tvQuestion.requestFocus();
//        tvQuestion.getSettings().setLightTouchEnabled(true);
//        tvQuestion.setBackgroundColor(Color.TRANSPARENT);
////        tvQuestion.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//        tvQuestion.getSettings().setJavaScriptEnabled(true);
//        tvQuestion.getSettings().setGeolocationEnabled(true);
//        tvQuestion.setSoundEffectsEnabled(true);
//        tvQuestion.loadData("Q-"+(position+1)+". "+htmlAsString, "text/html", "UTF-8");
        //  Spanned spanned = Html.fromHtml("Q-" + (position + 1) + ". " + htmlAsString, this, null);
        //   tvQuestion.setText(spanned);

        switch (questionType) {

            case "MT":
                mcqoptionsLL.setVisibility(View.GONE);
                LLmatchinquestion.setVisibility(View.VISIBLE);
                String htmlAsString = "";
                // if (viewSolutionData.getQuestions()!=null && viewSolutionData.getQuestions().size()>0) {
                htmlAsString = viewSolutionData.getData().getQuestions().get(position).getQuestion();
                // }
                //Spanned spanned = Html.fromHtml("Q-" + (position + 1) + ". " + htmlAsString, this, null);
                // tvQuestion.setText(spanned);

                tvQuestion.requestFocus();
                tvQuestion.getSettings().setLightTouchEnabled(true);
                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
//        tvQuestion.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                //tvQuestion.setSoundEffectsEnabled(true);
                //tvQuestion.loadData("Q-"+(position+1)+". "+htmlAsString, "text/html", "UTF-8");
                Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + htmlAsString);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, questionsol_layout1));
                // addMachingQuestionOption();


                break;
            case "FIB":
                String htmlAsString2 = viewSolutionData.getData().getQuestions().get(position).getQuestion();// used by WebView
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
                String htmlAsString1 = viewSolutionData.getData().getQuestions().get(position).getQuestion();// used by WebView



                //  Spanned spanned1 = Html.fromHtml("Q-" + (position + 1) + ". " + htmlAsString1, this, null);
                // tvQuestion.setText(spanned1);
                //tvQuestion.requestFocus();
                //tvQuestion.getSettings().setLightTouchEnabled(true);
                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                //tvQuestion.setSoundEffectsEnabled(true);
                //tvQuestion.loadData("Q-"+(position+1)+". "+htmlAsString1, "text/html", "UTF-8");
                Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + htmlAsString1);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, questionsol_layout1));
                addSolutionOption();
        }

        displaySolution();

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

    @SuppressLint("SetTextI18n")
    private void displaySolution() {
        if (result.getState().equalsIgnoreCase("answered")) {
            String your_ans = "";
            for (int i = 0; i < result.getAnswers().size(); i++) {
                if (result.getAnswers().get(i).equalsIgnoreCase("1")) {
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
            if (result.getState().equalsIgnoreCase("not_visited")) {
                your_ans_text.setText("Not Visited");
            } else {
                your_ans_text.setText(result.getState());
            }
        }
        switch (questionType) {
            case "MC":
                if (result.getState().equalsIgnoreCase("answered")) {
                    String your_ans = "", status = "";

                    for (int i = 0; i < result.getAnswers().size(); i++) {
                        if (result.getAnswers().get(i).equalsIgnoreCase("1")) {
                            switch (i) {
                                case 0:
                                    your_ans = your_ans + "1,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }


                                    break;
                                case 1:

                                    your_ans = your_ans + "2,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 2:

                                    your_ans = your_ans + "3,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 3:

                                    your_ans = your_ans + "4,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 4:

                                    your_ans = your_ans + "5,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 5:

                                    your_ans = your_ans + "6,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 6:

                                    your_ans = your_ans + "7,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 7:

                                    your_ans = your_ans + "8,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;

                                case 8:

                                    your_ans = your_ans + "9,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 9:

                                    your_ans = your_ans + "10,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    }

                                    break;
                                case 10:

                                    your_ans = your_ans + "11,";
                                    if (result.getAnswer().contains("" + (i + 1))) {
                                        if (result.getIsCorrect() == 0) {
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
                                            status = "Correct";
                                        }
                                    } else {
                                        if (result.getIsCorrect() == 0) {
                                            parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                            TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                            tv.setBackgroundResource(R.drawable.circle4);
                                            tv.setTextColor(getResources().getColor(R.color.white));
                                            status = "Incorrect";
                                        } else if (result.getIsCorrect() == 1) {
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
                    if (result.getState().equalsIgnoreCase("not_visited")) {
                        your_ans_text.setText("Not Visited");
                    } else {
                        your_ans_text.setText(result.getState());
                    }

                }
                // if (result.getIsCorrect()==0) {
                String[] ans = result.getAnswer().split(",");
                for (int i = 0; i < ans.length; i++) {
                    for (int j = 0; j < LinearLayoutList.size(); j++) {
                        if (Integer.parseInt(ans[i]) == j + 1)
                            LinearLayoutList.get(j).setSelected(true);

                    }
                }
                // qtype.setText("MSQ");
                max_marks.setText("Pos. Marks " + result.getMarksPerQuestion());
                min_marks.setText("  Neg. Marks " + result.getNegativeMarks());
                long minutes_mcq = (((Integer.parseInt(questionDump.getOnScreen())) * 1000) / 1000) / 60;
                long seconds_mcq = (((Integer.parseInt(questionDump.getOnScreen())) * 1000) / 1000) % 60;
                String min_mcq = "", sec_mcq = "";
                if (minutes_mcq < 10 && seconds_mcq < 10) {
                    min_mcq = "0" + minutes_mcq;
                    sec_mcq = "0" + seconds_mcq;
                    onscreen_value.setText(min_mcq + ":" + sec_mcq);
                } else if (minutes_mcq < 10 || seconds_mcq > 10) {
                    min_mcq = "0" + minutes_mcq;
                    onscreen_value.setText(min_mcq + ":" + seconds_mcq);
                } else if (minutes_mcq > 10 || seconds_mcq < 10) {
                    sec_mcq = "0" + seconds_mcq;
                    onscreen_value.setText(minutes_mcq + ":" + sec_mcq);
                }
                break;
            case "FIB":
                if (result.getState().equalsIgnoreCase("answered")) {
                    String your_ans = "", status = "";
                    for (int i = 0; i < result.getAnswers().size(); i++) {
                        if (!result.getAnswers().get(i).equalsIgnoreCase("")) {
                            your_ans = your_ans + result.getAnswers().get(i) + ",";
                            if (result.getIsCorrect() == 0) {
                                /*parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                tv.setBackgroundResource(R.drawable.circle4);
                                tv.setTextColor(getResources().getColor(R.color.white));*/
                                status = "Incorrect";
                            } else if (result.getIsCorrect() == 1) {
                                status = "Correct";
                            }
                        }
                    }
                    question_status.setVisibility(View.VISIBLE);
                    your_ans_text.setText(your_ans.substring(0, your_ans.length() - 1));
                    question_status.setText("|| " + status);

                } else {
                    if (result.getState().equalsIgnoreCase("not_visited")) {
                        your_ans_text.setText("Not Visited");
                    } else {
                        your_ans_text.setText(result.getState());
                    }

                }
                //qtype.setText("NAT");

                max_marks.setText("Pos. Marks " + result.getMarksPerQuestion());
                min_marks.setText("  Neg. Marks " + result.getNegativeMarks());
                long minutes1 = (((Integer.parseInt(questionDump.getOnScreen())) * 1000) / 1000) / 60;
                long seconds1 = (((Integer.parseInt(questionDump.getOnScreen())) * 1000) / 1000) % 60;
                String min1 = "", sec1 = "";
                if (minutes1 < 10 && seconds1 < 10) {
                    min1 = "0" + minutes1;
                    sec1 = "0" + seconds1;
                    onscreen_value.setText(min1 + ":" + sec1);
                } else if (minutes1 < 10 || seconds1 > 10) {
                    min1 = "0" + minutes1;
                    onscreen_value.setText(min1 + ":" + seconds1);
                } else if (minutes1 > 10 || seconds1 < 10) {
                    sec1 = "0" + seconds1;
                    onscreen_value.setText(minutes1 + ":" + sec1);
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
                if (result.getState().equalsIgnoreCase("answered")) {
                    String your_ans = "", status = "";
                    for (int i = 0; i < result.getAnswers().size(); i++) {
                        if (result.getAnswers().get(i).equalsIgnoreCase("1")) {
                            switch (i) {
                                case 0:
                                    your_ans = "1";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 1:

                                    your_ans = "2";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 2:

                                    your_ans = "3";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 3:

                                    your_ans = "4";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 4:

                                    your_ans = "5";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 5:

                                    your_ans = "6";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 6:

                                    your_ans = "7";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }
                                    break;
                                case 7:

                                    your_ans = "8";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }
                                    break;

                                case 8:

                                    your_ans = "9";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 9:

                                    your_ans = "10";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
                                        status = "Correct";
                                    }

                                    break;
                                case 10:

                                    your_ans = "11";
                                    if (result.getIsCorrect() == 0) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect() == 1) {
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
                    if (result.getState().equalsIgnoreCase("not_visited")) {
                        your_ans_text.setText("Not Visited");
                    } else {
                        your_ans_text.setText(result.getState());
                    }

                }
                max_marks.setText("Pos. Marks " + result.getMarksPerQuestion());
                min_marks.setText("Neg. Marks " + result.getNegativeMarks());
                long minutes_sc = (((Integer.parseInt(questionDump.getOnScreen())) * 1000) / 1000) / 60;
                long seconds_sc = (((Integer.parseInt(questionDump.getOnScreen())) * 1000) / 1000) % 60;
                String min_sc = "", sec_sc = "";
                if (minutes_sc < 10 && seconds_sc < 10) {
                    min_sc = "0" + minutes_sc;
                    sec_sc = "0" + seconds_sc;
                    onscreen_value.setText(min_sc + ":" + sec_sc);
                } else if (minutes_sc < 10 || seconds_sc > 10) {
                    min_sc = "0" + minutes_sc;
                    onscreen_value.setText(min_sc + ":" + seconds_sc);
                } else if (minutes_sc > 10 || seconds_sc < 10) {
                    sec_sc = "0" + seconds_sc;
                    onscreen_value.setText(minutes_sc + ":" + sec_sc);
                }


                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    if (!TextUtils.isEmpty(result.getAnswer())) {
                        if (i + 1 == Integer.parseInt(result.getAnswer()))
                            LinearLayoutList.get(i).setSelected(true);
                    }
                }
                break;

        }
       /* if (result.getAnswer().contains(",")) {

        } else{
            if (result.getUserAnswer().equalsIgnoreCase("")) {

            LinearLayoutList.get(Integer.parseInt(result.getAnswer()) - 1).setSelected(true);
        } else {
            if (result.getUserAnswer().equalsIgnoreCase(result.getAnswer())) {
                parentList.get(Integer.parseInt(result.getUserAnswer()) - 1).setBackground(activity.getResources().getDrawable(R.drawable.bg_mcq_selected));
                tvList.get(Integer.parseInt(result.getUserAnswer()) - 1).setBackground(activity.getResources().getDrawable(R.drawable.circle_right));
                ((TextView) (tvList.get(Integer.parseInt(result.getUserAnswer()) - 1))).setTextColor(activity.getResources().getColor(R.color.white));
            } else {
                parentList.get(Integer.parseInt(result.getAnswer()) - 1).setBackground(activity.getResources().getDrawable(R.drawable.bg_mcq_selected));
                tvList.get(Integer.parseInt(result.getAnswer()) - 1).setBackground(activity.getResources().getDrawable(R.drawable.circle_right));
                ((TextView) (tvList.get(Integer.parseInt(result.getAnswer()) - 1))).setTextColor(activity.getResources().getColor(R.color.white));
                parentList.get(Integer.parseInt(result.getUserAnswer()) - 1).setBackground(activity.getResources().getDrawable(R.drawable.bg_mcq_wrong_answer));
                tvList.get(Integer.parseInt(result.getUserAnswer()) - 1).setBackground(activity.getResources().getDrawable(R.drawable.circle_wrong));
                ((TextView) (tvList.get(Integer.parseInt(result.getUserAnswer()) - 1))).setTextColor(activity.getResources().getColor(R.color.white));
            }
        }*/

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
//                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                mDrawable.setLevel(1);
                //     CharSequence t = tvQuestion.getText();
                // tvQuestion.setText(t);
            }
        }
    }

    class LoadImageExplain extends AsyncTask<Object, Void, Bitmap> {
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
//                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setBounds(0, 0, empty2.getIntrinsicWidth(), empty2.getIntrinsicHeight());
                mDrawable.setLevel(1);
                // CharSequence t = explanationTV.getText();
                //   explanationTV.setText(t);
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
