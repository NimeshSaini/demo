package com.utkarshnew.android.CreateTest.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.CreateTest.Fragment.FIB_CreateTestSeriesFragment;
import com.utkarshnew.android.CreateTest.Fragment.SC_CreateTestSeriesFragment;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapter;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapterTwo;
import com.utkarshnew.android.testmodule.adapter.PartAdapter;
import com.utkarshnew.android.testmodule.adapter.TestViewPagerAdapter;
import com.utkarshnew.android.testmodule.interfaces.NumberPadOnClick;
import com.utkarshnew.android.testmodule.layoutmanager.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.AnswersResumeResponse;
import com.utkarshnew.android.testmodule.model.Data;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.QuestionDump;
import com.utkarshnew.android.testmodule.model.ResumeDump;
import com.utkarshnew.android.testmodule.model.Social;
import com.utkarshnew.android.testmodule.model.TestBasic;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import java.util.ArrayList;
import java.util.List;

public class TestCreateActivity extends AppCompatActivity implements View.OnClickListener, NumberPadOnClick {
    public String testseriesid;
    public String testSeriesname;
    public String first_attempt;
    public int language;
    public TestViewPagerAdapter pagerAdapter;
    public int currentPage = 0;
    public ArrayList<Answers> answerList;
    public ArrayList<AnswersResumeResponse> answersResumeResponses;
    public Data data;
    public List<Question> questionBankList;
    public List<Question> resumedumpresponselist;
    public List<QuestionDump> questionDumpList;
    ResumeDump resumeDump;
    ResultTestSeries resultTestSeries;
    boolean status = false;
    long count = 0;
    TextView testSeriesName;
    TestseriesBase testseriesBase;
    List<String> questionPart = new ArrayList<>();
    PartAdapter partAdapter;
    TestBasic basicInfo;
    Progress mProgress;
    String state, lastView, LANG;
    int prevSeconds, unattampedcount, attemptCount, skipedQuestion, markforreviewCount, savemarkforreviewCount;
    String PartId;
    TextView gridView, listView, nextTV;
    View viewMarkForReviewCount;
    public TextView changelang;
    private LinearLayout llDrawerRight, llMarkForReview, llMarkForReviewCount;
    private DrawerLayout drawerLayout;
    private ImageView imgTestBack, imgTestMenu, imgPause;
    private RecyclerView rlQuestionPad, rvNumberpad;
    private ViewPager viewPagerTest;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private List<ViewModel> items;
    private Button btn_submit;
    private ImageView langimage;
    private FrameLayout btnNext, btnPrev, btn_finish;
    private TextView tvTime, btnClear, tvQuestionnumber, tvanswerCount, tvUnAnswerCount, tvSkipCount, tv_savemarkforReview_count, tvmarkForReviewCount;
    private boolean isPaused = false;
    private long timeRemaining = 0;
    private MyRecyclerAdapter rvNumberPadAdapter;
    private MyRecyclerAdapterTwo rvQuestionAdapter;
    private NumberPadOnClick numberPadOnClick;
    private boolean isForceFinish = true;
    public List<Social> items1 = new ArrayList<>();
    public List<Social> items2 = new ArrayList<>();
    boolean testsubmit = false;
    FrameLayout testlayout;
    public static int[] SAMPLE_BG = {R.drawable.bg_mcq_selected,
            R.drawable.bg_mcq_selected1,
            R.drawable.bg_mcq_selected2,
            R.drawable.bg_mcq_selected3,
            R.drawable.bg_mcq_selected4,
            R.drawable.bg_mcq_selected5,
            R.drawable.bg_mcq_selected6,
            R.drawable.bg_mcq_selected7,
            R.drawable.bg_mcq_selected8,
            R.drawable.bg_mcq_selected9};

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

    public static int nestbg;
    public static int nestcirclebg;
    public static int matchingPosition = -1;
    public static int matchingPositiondrag = -1;
    public static boolean nestselected = false;
    public static boolean sameselected = false;
    String PREFS_NAME = "memory";
    String currentDisplayedInput = "";
    String inputToBeParsed = "";
    boolean isInverse = false;
    TextView outputResult;
    TextView shiftDisplay;
    String lastResultObtain = "";
    Context context;
    Button buttonRad;
    TextView degreeRad;
    private static final String TAG = "TestBaseActivity";
    public boolean clickedStauts = true;

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
        setContentView(R.layout.activity_test_create);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int check = SharedPreference.getInstance().getInt(Const.LANGUAGE);
        if (check == Const.ENGLISH_CODE) {
            Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.ENGLISH), this);
        } else if (check == Const.HINDI_CODE) {
            Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.HINDI), this);
        }

        SharedPreference.getInstance().putString("VIEW", "0");
        setReference();
        testseriesBase = new Gson().fromJson(MakeMyExam.object, new TypeToken<TestseriesBase>() {
        }.getType());

        // testseriesBase = (TestseriesBase) Objects.requireNonNull(getIntent().getExtras()).getSerializable(Const.TESTSERIES);
        testseriesid = getIntent().getExtras().getString(Const.TEST_SERIES_ID);
        testSeriesname = getIntent().getExtras().getString(Const.TEST_SERIES_Name);
        first_attempt = getIntent().getExtras().getString("first_attempt");
        language = getIntent().getExtras().getInt(Const.LANG);
        if (testseriesBase!=null && testseriesBase.getData() != null) {
            data = testseriesBase.getData();
            basicInfo = testseriesBase.getData().getTestBasic();
            testSeriesName.setText("My Test");

            mProgress = new Progress(this);
            mProgress.setCancelable(false);


            setTestData();
            setTimer();
            CountTotalanswer();
        }

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
        TestBasic basicInfo = testseriesBase.getData().getTestBasic();
        if (basicInfo.getDisplay_bubble() != null && !basicInfo.getDisplay_bubble().equalsIgnoreCase("")) {

            if (basicInfo.getDisplay_bubble().equalsIgnoreCase("1")) {
                rvNumberpad.setVisibility(View.VISIBLE);
            } else {
                rvNumberpad.setVisibility(View.GONE);
            }
        } else {
            rvNumberpad.setVisibility(View.VISIBLE);
        }
        changelang.setVisibility(View.GONE);
        langimage.setVisibility(View.GONE);
        changelang.setText("");

        if (language == 1) {
            questionBankList = data.getQuestions();
            PartId = questionBankList.get(0).getSectionId();
        } else if (language == 2) {
            questionBankList = data.getQuestions();
            PartId = questionBankList.get(0).getSectionId();
        } else if (language == 3) {
            if (data.getQuestions().size() == data.getQuestionsHindi().size()) {
                changelang.setVisibility(View.GONE);
                langimage.setVisibility(View.VISIBLE);
                changelang.setText("E/H");
                questionBankList = data.getQuestionsHindi();
            } else {
                changelang.setVisibility(View.GONE);
                langimage.setVisibility(View.GONE);
                questionBankList = data.getQuestions();
            }
        }
        imgPause.setVisibility(View.GONE);
        rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this, "0");
        rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this);

        rlQuestionPad.setAdapter(rvQuestionAdapter);
        GridLayoutManager manager = new GridLayoutManager(TestCreateActivity.this, 6, GridLayoutManager.VERTICAL, false);
        rlQuestionPad.setLayoutManager(manager);
        rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
        rvNumberpad.setAdapter(rvNumberPadAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(TestCreateActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvNumberpad.setLayoutManager(linearLayoutManager);
        rvNumberpad.setItemAnimator(new DefaultItemAnimator());


        switch (questionBankList.get(0).getQuestionType()) {

            case "FIB":
                btnClear.setVisibility(View.VISIBLE);
                setFragment(FIB_CreateTestSeriesFragment.newInstance(0), "add", "0");
                break;
            case "MT":
                btnClear.setVisibility(View.VISIBLE);
                setFragment(SC_CreateTestSeriesFragment.newInstance(0, questionBankList.get(0).getQuestionType()), "add", "0");
                break;
            default:
                btnClear.setVisibility(View.VISIBLE);
                setFragment(SC_CreateTestSeriesFragment.newInstance(0, questionBankList.get(0).getQuestionType()), "add", "0");
        }
        changeTextOnNextAndPrevButton();


        langimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changelang.getText().equals("H/E")) {
                    questionBankList = data.getQuestionsHindi();
                    rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this, "0");
                    rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this);
                    rlQuestionPad.setAdapter(rvQuestionAdapter);
                    GridLayoutManager manager = new GridLayoutManager(TestCreateActivity.this, 6, GridLayoutManager.VERTICAL, false);
                    rlQuestionPad.setLayoutManager(manager);
                    rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
                    rvNumberpad.setAdapter(rvNumberPadAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(TestCreateActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rvNumberpad.setLayoutManager(linearLayoutManager);
                    rvNumberpad.setItemAnimator(new DefaultItemAnimator());
                    changelang.setText("E/H");
                    switch (questionBankList.get(currentPage).getQuestionType()) {
                        case "FIB":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(FIB_CreateTestSeriesFragment.newInstance(currentPage), "selection", "1");
                            break;
                        case "MT":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType()), "selection", "1");
                            break;
                        default:
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType()), "selection", "1");
                    }
                } else if (changelang.getText().equals("E/H")) {
                    questionBankList = data.getQuestions();
                    rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this, "0");
                    rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this);
                    rlQuestionPad.setAdapter(rvQuestionAdapter);
                    GridLayoutManager manager = new GridLayoutManager(TestCreateActivity.this, 6, GridLayoutManager.VERTICAL, false);
                    rlQuestionPad.setLayoutManager(manager);
                    rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
                    rvNumberpad.setAdapter(rvNumberPadAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(TestCreateActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rvNumberpad.setLayoutManager(linearLayoutManager);
                    rvNumberpad.setItemAnimator(new DefaultItemAnimator());
                    changelang.setText("H/E");
                    switch (questionBankList.get(currentPage).getQuestionType()) {
                        case "FIB":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(FIB_CreateTestSeriesFragment.newInstance(currentPage), "selection", "1");
                            break;
                        case "MT":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType()), "selection", "1");
                            break;
                        default:
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType()), "selection", "1");
                    }
                }
            }
        });


    }

    public int getIndexOf(List<Question> list, String name) {
        int pos = 0;

        for (Question myObj : list) {
            if (name.equalsIgnoreCase(myObj.getSectionId()))
                return pos;
            pos++;
        }
        return -1;
    }

    private void setReference() {
        changelang = findViewById(R.id.changelang);

        llDrawerRight = findViewById(R.id.llDrawerRight);
        langimage = findViewById(R.id.langimage);
        testlayout = findViewById(R.id.testlayout);
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
        tvmarkForReviewCount.setVisibility(View.GONE);
        tv_savemarkforReview_count.setVisibility(View.GONE);
        llMarkForReviewCount.setVisibility(View.GONE);
        viewMarkForReviewCount.setVisibility(View.VISIBLE);
        llMarkForReview.setVisibility(View.VISIBLE);
        btn_submit.setOnClickListener(this);
        btnNext.setOnClickListener(this);
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

    protected void setFragment(Fragment fragment, String status, String pos) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.testlayout, fragment);
        t.commit();
        if (status.equalsIgnoreCase("add")) {
            if (pos.equalsIgnoreCase("0")) {
                rvNumberpad.scrollToPosition(0);
                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), 0);
                rvNumberPadAdapter.setSelectePosition(0);

                if (questionBankList.get(0).getAnswerPosttion() == -1)
                    questionBankList.get(0).setIsanswer(false, 0);
                if (data != null)
                    tvQuestionnumber.setText("Question " + (currentPage + 1) + "/" + questionBankList.size());
                testseriesBase.setLastanswerPosition(0);

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvQuestionAdapter.setSelectePosition(0);
                    }
                }, 1000);
            } else {
                currentPage = currentPage + 1;
                rvNumberpad.scrollToPosition(currentPage);
                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage);
                rvNumberPadAdapter.setSelectePosition(currentPage);
                if (data != null)
                    tvQuestionnumber.setText("Question " + (currentPage + 1) + "/" + questionBankList.size());

                if (questionBankList.get(currentPage).getAnswerPosttion() == -1)
                    questionBankList.get(currentPage).setIsanswer(false, 0);

                testseriesBase.setLastanswerPosition(currentPage);

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvQuestionAdapter.setSelectePosition(currentPage);
                    }
                }, 1000);
            }
        } else if (status.equalsIgnoreCase("remove")) {
            currentPage = currentPage - 1;
            rvNumberpad.scrollToPosition(currentPage);
            rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage);
            rvNumberPadAdapter.setSelectePosition(currentPage);

            if (questionBankList.get(currentPage).getAnswerPosttion() == -1)
                questionBankList.get(currentPage).setIsanswer(false, 0);
            if (data != null)
                tvQuestionnumber.setText("Question " + (currentPage + 1) + "/" + questionBankList.size());
            testseriesBase.setLastanswerPosition(currentPage);

            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    rvQuestionAdapter.setSelectePosition(currentPage);
                }
            }, 1000);
        } else if (status.equalsIgnoreCase("selection")) {
            if (currentPage == 0) {
                rvNumberpad.scrollToPosition(0);
                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), 0);
                rvNumberPadAdapter.setSelectePosition(0);
                if (questionBankList.get(0).getAnswerPosttion() == -1)
                    questionBankList.get(0).setIsanswer(false, 0);
                if (data != null)
                    tvQuestionnumber.setText("Question " + (currentPage + 1) + "/" + questionBankList.size());
                testseriesBase.setLastanswerPosition(0);

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvQuestionAdapter.setSelectePosition(0);
                    }
                }, 1000);
            } else {
                rvNumberpad.scrollToPosition(currentPage);
                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage);
                rvNumberPadAdapter.setSelectePosition(currentPage);
                if (questionBankList.get(currentPage).getAnswerPosttion() == -1)
                    questionBankList.get(currentPage).setIsanswer(false, 0);
                if (data != null)
                    tvQuestionnumber.setText("Question " + (currentPage + 1) + "/" + questionBankList.size());
                testseriesBase.setLastanswerPosition(currentPage);

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvQuestionAdapter.setSelectePosition(currentPage);
                    }
                }, 1000);


            }
        }


//                      rvNumberpad.scrollToPosition(currentPage-1);
//                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage-1);
//                rvNumberPadAdapter.setSelectePosition(currentPage-1);
//
//                if (questionBankList.get(currentPage).getAnswerPosttion() == -1)
//                    questionBankList.get(currentPage).setIsanswer(false, 0);
//                if (data != null)
//                    tvQuestionnumber.setText("Question " + (currentPage) + "/" + questionBankList.size());
//                testseriesBase.setLastanswerPosition(currentPage);
//
//                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        rvQuestionAdapter.setSelectePosition(currentPage);
//                    }
//                }, 1000);
//                }


    }


    private void addFragment(int i, String questionType) {


        switch (questionType) {

            case "FIB":
                btnClear.setVisibility(View.VISIBLE);
                mFragmentList.add(FIB_CreateTestSeriesFragment.newInstance(i));
                break;
            case "MT":
                btnClear.setVisibility(View.VISIBLE);
                mFragmentList.add(SC_CreateTestSeriesFragment.newInstance(i, questionType));
                break;
            default:
                btnClear.setVisibility(View.VISIBLE);
                mFragmentList.add(SC_CreateTestSeriesFragment.newInstance(i, questionType));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if (!testsubmit) {
            state = "0";
            questionBankList.get(currentPage).setPause(true);
            state = "0";
            finishTestSeries();
        }*/

    }

    private void setTimer() {
        tvTime.setText("00:00:00");
        tvTime.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void finishTestSeries() {
        lastView = String.valueOf(currentPage);
        answerList = new ArrayList<Answers>();
        for (int i = 0; i < questionBankList.size(); i++) {
            answerList.add(makeAnswerArray(i));
        }
        String jsonStr = new Gson().toJson(answerList);
        Intent resultScreen = new Intent(TestCreateActivity.this, QuizActivity.class);
        resultScreen.putExtra(Const.FRAG_TYPE, Const.CREATE_RESULT);
        resultScreen.putExtra(Const.NAME, testSeriesname);
        MakeMyExam.questionbanklist = new Gson().toJson(questionBankList);

        // resultScreen.putExtra("questionBankList", );
        resultScreen.putExtra("revision_string", jsonStr);
        if (langimage.getVisibility() == View.VISIBLE) {

            if (changelang.getText().equals("E/H")) {

                ArrayList<Question> eng = new ArrayList<>();
                for (int k = 0; k < questionBankList.size(); k++) {
                    Question question = testseriesBase.getData().getQuestions().get(k);
                    question.setUser_answer(questionBankList.get(k).getUser_answer());
                    question.setAnswers(questionBankList.get(k).getAnswers());
                    eng.add(question);
                }
                testseriesBase.getData().setQuestions(eng);
                testseriesBase.getData().setQuestionsHindi(questionBankList);
                List<String> lang = new ArrayList<>();
                lang.add("2");
                lang.add("1");
                testseriesBase.getData().getTestBasic().setLangId(lang);
            } else if (changelang.getText().equals("H/E")) {
                testseriesBase.getData().setQuestionsHindi(testseriesBase.getData().getQuestionsHindi());
                testseriesBase.getData().setQuestions(questionBankList);
                List<String> lang = new ArrayList<>();
                lang.add("1");
                lang.add("2");
                testseriesBase.getData().getTestBasic().setLangId(lang);
            }
        } else {
            testseriesBase.getData().setQuestions(questionBankList);
        }

        MakeMyExam.object = new Gson().toJson(testseriesBase);

        // resultScreen.putExtra("response", new Gson().toJson(testseriesBase));
        Helper.gotoActivity(resultScreen, this);
        finish();
    }

    @Override
    public void onBackPressed() {
        showPopupSubmitTest();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                testseriesBase.setLastanswerPosition(currentPage);
                switch (questionBankList.get(currentPage + 1).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_CreateTestSeriesFragment.newInstance(currentPage + 1), "add", "1");
                        break;
                    case "MT":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage + 1, questionBankList.get(currentPage + 1).getQuestionType()), "add", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage + 1, questionBankList.get(currentPage + 1).getQuestionType()), "add", "1");
                }
                changeTextOnNextAndPrevButton();
                break;
            case R.id.btn_prev:
                testseriesBase.setLastanswerPosition(currentPage - 1);
//                viewPagerTest.setCurrentItem(viewPagerTest.getCurrentItem() - 1, true);
                switch (questionBankList.get(currentPage - 1).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_CreateTestSeriesFragment.newInstance(currentPage - 1), "remove", "1");
                        break;
                    case "MT":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage - 1, questionBankList.get(currentPage - 1).getQuestionType()), "remove", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_CreateTestSeriesFragment.newInstance(currentPage - 1, questionBankList.get(currentPage - 1).getQuestionType()), "remove", "1");
                }
                changeTextOnNextAndPrevButton();
                break;


            case R.id.btn_clear:
                if (questionBankList.get(currentPage).isIssaveMarkForReview() || questionBankList.get(currentPage).isMarkForReview()) {
                    questionBankList.get(currentPage).setIssaveMarkForReview(false);
                    questionBankList.get(currentPage).setMarkForReview(false);
                }
                if (data.getQuestionsHindi() != null && data.getQuestionsHindi().size() > 0) {
                    if (data.getQuestionsHindi().get(currentPage).isIssaveMarkForReview() || data.getQuestionsHindi().get(currentPage).isMarkForReview()) {
                        data.getQuestionsHindi().get(currentPage).setIssaveMarkForReview(false);
                        data.getQuestionsHindi().get(currentPage).setMarkForReview(false);
                    }
                }
                if (data.getQuestions() != null && data.getQuestions().size() > 0) {
                    if (data.getQuestions().get(currentPage).isIssaveMarkForReview() || data.getQuestions().get(currentPage).isMarkForReview()) {
                        data.getQuestions().get(currentPage).setIssaveMarkForReview(false);
                        data.getQuestions().get(currentPage).setMarkForReview(false);
                    }
                }

                if (questionBankList.get(currentPage).getQuestionType().equalsIgnoreCase("FIB")) {
                    FIB_CreateTestSeriesFragment test = (FIB_CreateTestSeriesFragment) getSupportFragmentManager().findFragmentById(R.id.testlayout);
                    test.refereshPage();
                } else {
                    SC_CreateTestSeriesFragment test = (SC_CreateTestSeriesFragment) getSupportFragmentManager().findFragmentById(R.id.testlayout);
                    test.refereshPage();
                }

                notifynumberApater();
                break;
            case R.id.img_testmenu:
                CountTotalanswer();
                drawerLayout.openDrawer(llDrawerRight);
                break;
            case R.id.img_testback:
            case R.id.img_pause:
            case R.id.btn_submit:
                showPopupSubmitTest();
                break;
            case R.id.gridView:
                SharedPreference.getInstance().putString("VIEW", "0");
                GridLayoutManager manager = new GridLayoutManager(TestCreateActivity.this, 6, GridLayoutManager.VERTICAL, false);
                rlQuestionPad.setLayoutManager(manager);
                gridView.setTextColor(getResources().getColor(R.color.black));
                gridView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.grid_view, 0, 0, 0);
                listView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.list_view_gray, 0, 0, 0);
                listView.setTextColor(getResources().getColor(R.color.colorGray3));
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this, "0");
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
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestCreateActivity.this, items, R.layout.single_row_testpad_no, TestCreateActivity.this, "1");
                rlQuestionPad.setAdapter(rvQuestionAdapter);
                break;

            case R.id.btn_finish:
                if (!isFinishing())
                    showPopupSubmitTest();
                break;

        }
    }

    public void changeTextOnNextAndPrevButton() {
        if (drawerLayout.isDrawerOpen(llDrawerRight))
            drawerLayout.closeDrawer(llDrawerRight);
        int currentPosition = currentPage;
        if (currentPosition == questionBankList.size() - 1) {
            btn_finish.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        } else {
            btn_finish.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        }

        if (currentPosition == questionBankList.size() - 1) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }

        if (currentPosition == 0) {
            ((TextView) btnPrev.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
            btnPrev.setBackground(getResources().getDrawable(R.drawable.background_bg_prev));
            btnPrev.setEnabled(false);
        } else {
            ((TextView) btnPrev.getChildAt(0)).setTextColor(getResources().getColor(R.color.blackApp));
            btnPrev.setBackground(getResources().getDrawable(R.drawable.background_bg_next));
            btnPrev.setEnabled(true);
        }
    }

    public Answers makeAnswerArray(int position) {
        String answerPosition = "";
        if (questionBankList.get(position).getAnswer() != null) {
            if (questionBankList.get(position).getAnswer().contains(",")) {

            } else {
                if (questionBankList.get(position).getAnswerPosttion() == -1) {
                    answerPosition = "";
                } else if (questionBankList.get(position).getAnswerPosttion() == 0) {
                    answerPosition = "";
                } else
                    answerPosition = String.valueOf(questionBankList.get(position).getAnswerPosttion());
            }
        } else {
            if (questionBankList.get(position).getAnswerPosttion() == -1) {
                answerPosition = "";
            } else if (questionBankList.get(position).getAnswerPosttion() == 0) {
                answerPosition = "";
            } else
                answerPosition = String.valueOf(questionBankList.get(position).getAnswerPosttion());
        }

        String state = "";
        String bookmarkvalue = "0";
        if (questionBankList.get(position).getIs_bookmarked().equalsIgnoreCase("0")) {
            bookmarkvalue = "0";
        } else {
            bookmarkvalue = "1";
        }
        if (questionBankList.get(position).isIssaveMarkForReview()) {
            state = "answered";
            // bookmarkvalue="1";
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

        answer.setConfig_id(questionBankList.get(position).getConfigId());
        answer.setSection_id(questionBankList.get(position).getId());
        answer.setIndex(String.valueOf(position));
        answer.setState(state);
        answer.setIs_bookmarked(bookmarkvalue);
        answer.setAnswer(questionBankList.get(position).getAnswer());
        answer.setAnswers(questionBankList.get(position).getAnswers());
        answer.setOn_screen(String.valueOf(questionBankList.get(position).getTotalTimeSpent()));
        answer.setUser_answer(questionBankList.get(position).getUser_answer() == null || questionBankList.get(position).getUser_answer().equalsIgnoreCase("") ? "" : questionBankList.get(position).getUser_answer());

        return answer;
    }

    @Override
    public void sendOnclickInd(int index) {
//        viewPagerTest.setCurrentItem(index);
        currentPage = index;
        switch (questionBankList.get(index).getQuestionType()) {
            case "FIB":
                btnClear.setVisibility(View.VISIBLE);
                setFragment(FIB_CreateTestSeriesFragment.newInstance(index), "selection", "1");
                break;
            case "MT":
                btnClear.setVisibility(View.VISIBLE);
                setFragment(SC_CreateTestSeriesFragment.newInstance(index, questionBankList.get(index).getQuestionType()), "selection", "1");
                break;
            default:
                btnClear.setVisibility(View.VISIBLE);
                setFragment(SC_CreateTestSeriesFragment.newInstance(index, questionBankList.get(index).getQuestionType()), "selection", "1");
        }
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
            save_markedTV.setVisibility(View.GONE);

            markedTV.setVisibility(View.GONE);
            marked1.setVisibility(View.GONE);
            save_marked1.setVisibility(View.GONE);


            markedView1.setVisibility(View.VISIBLE);
            markedView2.setVisibility(View.VISIBLE);
            //  }

            CountTotalanswer();
            // int unAttemptCount = questionBankList.size() - attemptCount;
            answeredTV.setText(String.valueOf(attemptCount));
            unansweredTV.setText(String.valueOf(unattampedcount));
            save_markedTV.setText(String.valueOf(savemarkforreviewCount));
            markedTV.setText(String.valueOf(markforreviewCount));
            not_visted_value.setText(String.valueOf(skipedQuestion));

            btnSubmit = v.findViewById(R.id.btn_submit);


            //tvUnattempt.setText("You have " + totalCount + " unattempted questions");
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quizBasicInfoDialog.dismiss();
                }
            });
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isForceFinish = false;
                    quizBasicInfoDialog.dismiss();
                    state = "1";
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

    public int countFIBWords(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i).startsWith("FIB")) {
                count++;
            }
        }
        return count;
    }
}