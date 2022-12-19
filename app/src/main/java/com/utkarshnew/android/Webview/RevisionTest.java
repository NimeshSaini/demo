package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapter;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapterTwo;
import com.utkarshnew.android.testmodule.adapter.PartAdapter;
import com.utkarshnew.android.testmodule.adapter.TestViewPagerAdapter;
import com.utkarshnew.android.testmodule.fragment.FIB_TestSeriesFragment;
import com.utkarshnew.android.testmodule.interfaces.NumberPadOnClick;
import com.utkarshnew.android.testmodule.layoutmanager.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.AnswersResumeResponse;
import com.utkarshnew.android.testmodule.model.Data;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.Social;
import com.utkarshnew.android.testmodule.model.TestBasic;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RevisionTest extends AppCompatActivity implements View.OnClickListener, NumberPadOnClick {
    public String testseriesid;
    public String testSeriesname;
    public String first_attempt;
    public int language;
    public TestViewPagerAdapter pagerAdapter;
    public int currentPage;

    public ArrayList<Answers> answerList;
    public ArrayList<AnswersResumeResponse> answersResumeResponses;
    public Data data;
    public List<Question> questionBankList;
    boolean status = false;
    long count = 0;
    TextView testSeriesName;
    PopupWindow popupWindow;
    TestseriesBase testseriesBase;
    List<String> questionPart = new ArrayList<>();
    PartAdapter partAdapter;
    TestBasic basicInfo;
    Progress mProgress;
    String state, lastView, LANG;
    long millisInFuture;
    ImageView calci;
    int prevSeconds, unattampedcount, attemptCount, skipedQuestion, markforreviewCount, savemarkforreviewCount;
    String PartId;
    TextView gridView, listView, nextTV;
    View viewMarkForReviewCount;
    private LinearLayout llDrawerRight, llMarkForReview, llMarkForReviewCount;
    private DrawerLayout drawerLayout;
    private ImageView imgTestBack, imgTestMenu, imgPause;
    private RecyclerView rlQuestionPad, rvNumberpad;
    private ViewPager viewPagerTest;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private List<ViewModel> items;
    private Button btn_submit;
    private FrameLayout btnNext, btnPrev, btn_finish;
    private TextView tvTime, btnClear, textSpinner, tvQuestionnumber, tvanswerCount, tvUnAnswerCount, tvSkipCount, tv_savemarkforReview_count, tvmarkForReviewCount;

    private MyRecyclerAdapter rvNumberPadAdapter;
    private MyRecyclerAdapterTwo rvQuestionAdapter;
    private CountDownTimer countDownTimer = null;

    public List<Social> items1 = new ArrayList<>();
    public List<Social> items2 = new ArrayList<>();
    boolean testsubmit = false;

    public static int[] SAMPLE_CIRCLE = {R.drawable.circle,
            R.drawable.circle1,
            R.drawable.circle2,
            R.drawable.circle3,
            R.drawable.circle4,
            R.drawable.circle5,
            R.drawable.circle6,
            R.drawable.circle7,
            R.drawable.circle8,
            R.drawable.circle9};


    public static boolean nestselected = false;
    public static boolean sameselected = false;
    private static Dialog calculator_dialog;


    @Override
    protected void attachBaseContext(Context newBase) {
        int check = SharedPreference.getInstance().getInt(Const.LANGUAGE);
        String lang;
        if (check == Const.HINDI_CODE) {
            lang = Const.HINDI;
        } else {
            lang = Const.ENGLISH;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(CustomContextWrapper.wrap(newBase, lang));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_revision);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int check = SharedPreference.getInstance().getInt(Const.LANGUAGE);
        if (check == Const.ENGLISH_CODE) {
            Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.ENGLISH), this);
        } else if (check == Const.HINDI_CODE) {
            Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.HINDI), this);
        }

        SharedPreference.getInstance().putString("VIEW", "0");
        setReference();
        testseriesBase = (TestseriesBase) Objects.requireNonNull(getIntent().getExtras()).getSerializable(Const.TESTSERIES);
        testseriesid = getIntent().getExtras().getString(Const.TEST_SERIES_ID);
        testSeriesname = getIntent().getExtras().getString(Const.TEST_SERIES_Name);
        first_attempt = getIntent().getExtras().getString("first_attempt");
        language = getIntent().getExtras().getInt(Const.LANG);

        data = testseriesBase.getData();
        basicInfo = testseriesBase.getData().getTestBasic();
        testSeriesName.setText(basicInfo.getTestSeriesName());
        mProgress = new Progress(this);
        mProgress.setCancelable(false);

        setTestData();


        setTimer();

        CountTotalanswer();
    }

    public void CountTotalanswer() {
        unattampedcount = 0;
        attemptCount = 0;
        skipedQuestion = 0;
        markforreviewCount = 0;
        savemarkforreviewCount = 0;
        for (int i = 0; i < questionBankList.size(); i++) {
            if (questionBankList.get(i).isMarkForReview() && questionBankList.get(i).getAnswerPosttion() != -1 && questionBankList.get(i).getAnswerPosttion() != -0) {
                ++savemarkforreviewCount;
            } else if (questionBankList.get(i).isMarkForReview()) {
                ++markforreviewCount;
            } else if (questionBankList.get(i).getAnswerPosttion() == -1) {
                ++skipedQuestion;
            } else if (questionBankList.get(i).getAnswerPosttion() == 0) {
                ++unattampedcount;
            } else if (questionBankList.get(i).getAnswerPosttion() != -1 && questionBankList.get(i).getAnswerPosttion() != -0) {
                ++attemptCount;
            }
        }
        tvanswerCount.setText(String.valueOf(attemptCount));
        tvUnAnswerCount.setText(String.valueOf(unattampedcount));
        tvSkipCount.setText(String.valueOf(skipedQuestion));
        tvmarkForReviewCount.setText(String.valueOf(markforreviewCount));
        tv_savemarkforReview_count.setText(String.valueOf(savemarkforreviewCount));
    }

    @SuppressLint("SetTextI18n")
    private void setTestData() {

        rvNumberpad.setVisibility(View.VISIBLE);

        questionBankList = data.getQuestions();
        for (int i = 0; i < questionBankList.size(); i++) {
            addFragment(i, questionBankList.get(i).getQuestionType());
        }


        int answerindex = 0;
        if (testseriesBase.getLastanswerPosition() == -1 || testseriesBase.getLastanswerPosition() == 0)
            answerindex = 1;
        else
            answerindex = testseriesBase.getLastanswerPosition();
        tvQuestionnumber.setText("Question " + answerindex + "/" + questionBankList.size());
        pagerAdapter = new TestViewPagerAdapter(getSupportFragmentManager(), RevisionTest.this, mFragmentList, questionBankList.size());
        viewPagerTest.setAdapter(pagerAdapter);

        rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, RevisionTest.this, items, R.layout.single_row_testpad_no, RevisionTest.this, "0");
        rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, RevisionTest.this, items, R.layout.single_row_testpad_no, RevisionTest.this);

        rlQuestionPad.setAdapter(rvQuestionAdapter);
        GridLayoutManager manager = new GridLayoutManager(RevisionTest.this, 6, GridLayoutManager.VERTICAL, false);
        rlQuestionPad.setLayoutManager(manager);


        rlQuestionPad.setItemAnimator(new DefaultItemAnimator());

        rvNumberpad.setAdapter(rvNumberPadAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(RevisionTest.this, LinearLayoutManager.HORIZONTAL, false);

        rvNumberpad.setLayoutManager(linearLayoutManager);
        rvNumberpad.setItemAnimator(new DefaultItemAnimator());
        viewPagerTest.setCurrentItem(0);
    }


    private void setReference() {
        calci = findViewById(R.id.calci);
        llDrawerRight = findViewById(R.id.llDrawerRight);
        drawerLayout = findViewById(R.id.drawerLayout);
        imgTestBack = findViewById(R.id.img_testback);
        imgTestMenu = findViewById(R.id.img_testmenu);
        rlQuestionPad = findViewById(R.id.rl_questionpad);
        rvNumberpad = findViewById(R.id.rvnumberpad);
        viewPagerTest = findViewById(R.id.view_pager_test);
        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        btnClear = findViewById(R.id.btn_clear);
        llMarkForReview = findViewById(R.id.llMarkForReview);
        tvTime = findViewById(R.id.tv_time);

        btn_finish = findViewById(R.id.btn_finish);
        llMarkForReviewCount = findViewById(R.id.llMarkForReviewCount);
        viewMarkForReviewCount = findViewById(R.id.viewMarkForReviewCount);
        nextTV = findViewById(R.id.nextTV);
        textSpinner = findViewById(R.id.text_spinner);
        tvQuestionnumber = findViewById(R.id.tv_questionnumber);
        tvanswerCount = findViewById(R.id.tv_answer_count);
        tvUnAnswerCount = findViewById(R.id.tv_unanswer_count);
        testSeriesName = findViewById(R.id.testSeriesName);
        tvSkipCount = findViewById(R.id.tv_skip_count);
        tvmarkForReviewCount = findViewById(R.id.tv_markforReview_count);
        tv_savemarkforReview_count = findViewById(R.id.tv_savemarkforReview_count);
        btn_submit = findViewById(R.id.btn_submit);
        imgPause = findViewById(R.id.img_pause);
        gridView = findViewById(R.id.gridView);
        listView = findViewById(R.id.listView);

        tvmarkForReviewCount.setVisibility(View.VISIBLE);
        tv_savemarkforReview_count.setVisibility(View.VISIBLE);
        llMarkForReviewCount.setVisibility(View.VISIBLE);
        viewMarkForReviewCount.setVisibility(View.VISIBLE);
        llMarkForReview.setVisibility(View.VISIBLE);

        btn_submit.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        calci.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        imgPause.setOnClickListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        questionBankList = new ArrayList<>();
        imgTestBack.setOnClickListener(this);
        imgTestMenu.setOnClickListener(this);
        btnClear.setOnClickListener(this);


        viewPagerTest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeTextOnNextAndPrevButton();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(final int position) {

                currentPage = position;
                rvNumberpad.scrollToPosition(currentPage);
                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage);
                rvNumberPadAdapter.setSelectePosition(position);

                if (questionBankList.get(position).getAnswerPosttion() == -1)
                    questionBankList.get(position).setIsanswer(false, 0);
                if (data != null)
                    tvQuestionnumber.setText("Question " + (position + 1) + "/" + questionBankList.size());
                testseriesBase.setLastanswerPosition(currentPage);

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvQuestionAdapter.setSelectePosition(position);
                    }
                }, 1000);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("viewpager", "onPageScrollStateChanged");

            }
        });

        gridView.setOnClickListener(this);
        listView.setOnClickListener(this);

    }

    private void addFragment(int i, String questionType) {


        switch (questionType) {

            case "FIB":
                btnClear.setVisibility(View.VISIBLE);
                mFragmentList.add(FIB_TestSeriesFragment.newInstance(i,0));
                break;
            default:
                btnClear.setVisibility(View.VISIBLE);
                mFragmentList.add(RevisionSC_TestSeriesFragment.newInstance(i, questionType));
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return (parent, view, position, id) -> {
            if (position == 0) {
                rvNumberpad.scrollToPosition(0);
                viewPagerTest.setCurrentItem(0);
                textSpinner.setText(questionPart.get(0));
            } else if (position == 1) {
                rvNumberpad.scrollToPosition(100);
                viewPagerTest.setCurrentItem(100);
                textSpinner.setText(questionPart.get(1));
            } else if (position == 2) {
                rvNumberpad.scrollToPosition(200);
                viewPagerTest.setCurrentItem(200);
                textSpinner.setText(questionPart.get(2));
            }
            dismissPopup();
        };
    }

    private void dismissPopup() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Helper.checkXposed(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setTimer() {
        status = true;
        millisInFuture = 60 * 60 * 1000;

        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count = millisUntilFinished / 1000;
                tvTime.setText(formatSeconds((int) count));
                data.getTestBasic().setTimeRemaining(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                status = false;
                tvTime.setText("00:00:00");
                if (!isFinishing()) {
                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(RevisionTest.this);
                    builder.setTitle(getResources().getString(R.string.time_out));
                    builder.setMessage(getResources().getString(R.string.time_over_message));
                    builder.setCancelable(false);

                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Ok</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            testsubmit = true;
                            finishTestSeries();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        Log.e("TestBaseActivity", "onDestroy");
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    private void finishTestSeries() {
        lastView = String.valueOf(currentPage);
        answerList = new ArrayList<Answers>();
        for (int i = 0; i < questionBankList.size(); i++) {
            answerList.add(makeAnswerArray(i));
        }
        String jsonStr = new Gson().toJson(answerList);
        Intent resultScreen = new Intent(RevisionTest.this, QuizActivity.class);
        resultScreen.putExtra(Const.FRAG_TYPE, Const.REVISION_SCRREN);
        resultScreen.putExtra(Const.NAME, testSeriesname);
        resultScreen.putExtra("questionBankList", new Gson().toJson(questionBankList));
        resultScreen.putExtra("revision_string", jsonStr);
        resultScreen.putExtra("testseriesBase", testseriesBase);

        startActivity(resultScreen);
        finish();
    }

    public String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        if (seconds != prevSeconds)
            questionBankList.get(currentPage).setTotalTimeSpent(questionBankList.get(currentPage).getTotalTimeSpent() + 1);
        prevSeconds = seconds;
        String formattedTime = "";
        if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";
        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }

    @Override
    public void onBackPressed() {
        showPopupSubmitTest();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.calci:
                openCalculator_web(RevisionTest.this);
                break;
            case R.id.btn_next:
                testseriesBase.setLastanswerPosition(currentPage);
                viewPagerTest.setCurrentItem(viewPagerTest.getCurrentItem() + 1, true);

                changeTextOnNextAndPrevButton();
                break;
            case R.id.btn_prev:
                testseriesBase.setLastanswerPosition(currentPage);
                viewPagerTest.setCurrentItem(viewPagerTest.getCurrentItem() - 1, true);

                changeTextOnNextAndPrevButton();
                break;
            case R.id.btn_submit:
                if (!isFinishing())
                    showPopupSubmitTest();
                break;
            case R.id.btn_clear:
                if (questionBankList.get(currentPage).isIssaveMarkForReview() || questionBankList.get(currentPage).isMarkForReview()) {
                    questionBankList.get(currentPage).setIssaveMarkForReview(false);
                    questionBankList.get(currentPage).setMarkForReview(false);
                }
                ((RevisionSC_TestSeriesFragment) mFragmentList.get(currentPage)).refereshPage();

                notifynumberApater();
                break;
            case R.id.img_testmenu:
                CountTotalanswer();
                drawerLayout.openDrawer(llDrawerRight);
                break;
            case R.id.cl_part:
            case R.id.img_testback:
            case R.id.img_pause:
                showPopupSubmitTest();
                break;
            case R.id.gridView:
                SharedPreference.getInstance().putString("VIEW", "0");
                GridLayoutManager manager = new GridLayoutManager(RevisionTest.this, 6, GridLayoutManager.VERTICAL, false);
                rlQuestionPad.setLayoutManager(manager);
                gridView.setTextColor(getResources().getColor(R.color.black));
                gridView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.grid_view, 0, 0, 0);
                listView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.list_view_gray, 0, 0, 0);
                listView.setTextColor(getResources().getColor(R.color.colorGray3));
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, RevisionTest.this, items, R.layout.single_row_testpad_no, RevisionTest.this, "0");
                rlQuestionPad.setAdapter(rvQuestionAdapter);
                break;
            case R.id.listView:
                SharedPreference.getInstance().putString("VIEW", "1");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                rlQuestionPad.setLayoutManager(linearLayoutManager);
                listView.setTextColor(getResources().getColor(R.color.black));
                listView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.list_view, 0, 0, 0);
                gridView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.grid_view_gray, 0, 0, 0);
                gridView.setTextColor(getResources().getColor(R.color.colorGray3));
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, RevisionTest.this, items, R.layout.single_row_testpad_no, RevisionTest.this, "1");
                rlQuestionPad.setAdapter(rvQuestionAdapter);
                break;

            case R.id.btn_finish:
                if (!isFinishing())
                    showPopupSubmitTest();
                break;

        }
    }

    public void changeTextOnNextAndPrevButton() {
        int currentPosition = viewPagerTest.getCurrentItem() + 1;
        if (currentPosition == questionBankList.size()) {
            btn_finish.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        } else {
            btn_finish.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }
        if (currentPosition == 1) {
            ((TextView) btnPrev.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
            btnPrev.setBackground(getResources().getDrawable(R.drawable.background_bg_prev));
        } else {
            ((TextView) btnPrev.getChildAt(0)).setTextColor(getResources().getColor(R.color.blackApp));
            btnPrev.setBackground(getResources().getDrawable(R.drawable.background_bg_next));
        }
    }

    public Answers makeAnswerArray(int position) {
        String state = "";
        String bookmarkvalue = "0";
        if (questionBankList.get(position).getIs_bookmarked().equalsIgnoreCase("0")) {
            bookmarkvalue = "0";
        } else {
            bookmarkvalue = "1";
        }
        if (questionBankList.get(position).isIssaveMarkForReview()) {
            state = "answered";
        } else if (questionBankList.get(position).isMarkForReview() && questionBankList.get(position).getAnswerPosttion() != -1 && questionBankList.get(position).getAnswerPosttion() != -0) {
            state = "answered";
        } else if (questionBankList.get(position).getAnswerPosttion() != -1 && questionBankList.get(position).getAnswerPosttion() != -0) {
            state = "answered";
        } else if (questionBankList.get(position).isMarkForReview()) {
            state = "marked_for_review";
        } else if (questionBankList.get(position).getAnswerPosttion() == -1) {
            state = "not_visited";
        } else if (questionBankList.get(position).getAnswerPosttion() == 0) {
            state = "unanswered";
        }
        Answers answer = new Answers();
        answer.setConfig_id(questionBankList.get(position).getId());
        answer.setIndex(String.valueOf(position));
        answer.setState(state);
        answer.setAnswers(questionBankList.get(position).getAnswers());
        answer.setOn_screen(String.valueOf(questionBankList.get(position).getTotalTimeSpent()));
        answer.setUser_answer(questionBankList.get(position).getUser_answer() == null || questionBankList.get(position).getUser_answer().equalsIgnoreCase("") ? "" : questionBankList.get(position).getUser_answer());
        return answer;

    }


    @Override
    public void sendOnclickInd(int index) {
        viewPagerTest.setCurrentItem(index);
    }


    private void showPopupSubmitTest() {
        try {
            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v = li.inflate(R.layout.dialogbox_test_submit, null, false);
            final Dialog quizBasicInfoDialog = new Dialog(this);
            quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            quizBasicInfoDialog.setCanceledOnTouchOutside(false);
            quizBasicInfoDialog.setContentView(v);
            quizBasicInfoDialog.show();
            TextView tvUnattempt, answeredTV, save_markedTV, unansweredTV, markedTV, save_marked1, marked1, not_visted_value;
            Button btnCancel, btnSubmit;
            View markedView1, markedView2;
            btnCancel = v.findViewById(R.id.btn_cancel);
            tvUnattempt = v.findViewById(R.id.tv_unattempt_question);
            answeredTV = v.findViewById(R.id.answeredTV);
            not_visted_value = v.findViewById(R.id.not_visted_value);
            unansweredTV = v.findViewById(R.id.unansweredTV);
            save_markedTV = v.findViewById(R.id.save_markedTV);
            markedTV = v.findViewById(R.id.markedTV);
            marked1 = v.findViewById(R.id.marked1);
            save_marked1 = v.findViewById(R.id.save_marked1);
            markedView1 = v.findViewById(R.id.markedView1);
            markedView2 = v.findViewById(R.id.markedView2);

            markedTV.setVisibility(View.VISIBLE);
            marked1.setVisibility(View.VISIBLE);
            save_marked1.setVisibility(View.VISIBLE);

            markedView1.setVisibility(View.VISIBLE);
            markedView2.setVisibility(View.VISIBLE);

            CountTotalanswer();
            answeredTV.setText(String.valueOf(attemptCount));
            unansweredTV.setText(String.valueOf(unattampedcount));
            save_markedTV.setText(String.valueOf(savemarkforreviewCount));
            markedTV.setText(String.valueOf(markforreviewCount));
            not_visted_value.setText(String.valueOf(skipedQuestion));

            btnSubmit = v.findViewById(R.id.btn_submit);


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quizBasicInfoDialog.dismiss();
                }
            });
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quizBasicInfoDialog.dismiss();
                    testsubmit = true;
                    finishTestSeries();

                }
            });
        } catch (WindowManager.BadTokenException bte) {
            bte.printStackTrace();
        }
    }

    public void notifynumberApater() {
        rvQuestionAdapter.notifyDataSetChanged();
        rvNumberPadAdapter.notifyDataSetChanged();
    }


    public void openCalculator_web(Context context) {
        try {
            calculator_dialog = new Dialog(context, R.style.MyDialogCalculator);
            calculator_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            calculator_dialog.setContentView(R.layout.dialog_calculator_web);
            calculator_dialog.setCancelable(true);
            calculator_dialog.setCanceledOnTouchOutside(true);
            calculator_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            calculator_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            calculator_dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            WebView calcwebView = calculator_dialog.findViewById(R.id.calcwebView);
            RelativeLayout calcpagerl = calculator_dialog.findViewById(R.id.calcpagerl);
            AppCompatButton btn_close = calculator_dialog.findViewById(R.id.btn_close);

            calcwebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            calcwebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            calcwebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            calcwebView.getSettings().setJavaScriptEnabled(true);
            calcwebView.getSettings().setBuiltInZoomControls(true);
            calcwebView.loadUrl(API.MOBILE_CALCULATOR);
            calcwebView.setBackgroundColor(Color.TRANSPARENT);
            //calcwebView.setPadding(0, 0, 0, 0);

            if (calculator_dialog != null && !calculator_dialog.isShowing()) {
                calculator_dialog.show();
            }

            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissCalculatorDialog();
                }
            });
            calcpagerl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissCalculatorDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void dismissCalculatorDialog() {
        if (calculator_dialog != null && calculator_dialog.isShowing()) {
            calculator_dialog.dismiss();
            calculator_dialog.cancel();
            calculator_dialog = null;
        }
    }

    private String removeTrailingZero(String formattingInput) {
        if (!formattingInput.contains(".")) {
            return formattingInput;
        }
        int dotPosition = formattingInput.indexOf(".");
        String newValue = formattingInput.substring(dotPosition, formattingInput.length());
        if (newValue.equals(".0")) {
            return formattingInput.substring(0, dotPosition);
        }
        return formattingInput;
    }


}
