package com.utkarshnew.android.testmodule.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.PopupMenu;
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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.table.PostDataTable;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.feeds.dataclass.Json;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.Model.MediaFile;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.TestTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.AmazonUpload.AmazonCallBack;
import com.utkarshnew.android.Utils.AmazonUpload.s3ImageUploading;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapter;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapterTwo;
import com.utkarshnew.android.testmodule.adapter.PartAdapter;
import com.utkarshnew.android.testmodule.adapter.TestViewPagerAdapter;
import com.utkarshnew.android.testmodule.fragment.FIB_TestSeriesFragment;
import com.utkarshnew.android.testmodule.fragment.SC_TestSeriesFragment;
import com.utkarshnew.android.testmodule.interfaces.NumberPadOnClick;
import com.utkarshnew.android.testmodule.layoutmanager.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.AnswersResumeResponse;
import com.utkarshnew.android.testmodule.model.Data;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.ResumeDump;
import com.utkarshnew.android.testmodule.model.SectioHashMap;
import com.utkarshnew.android.testmodule.model.Social;
import com.utkarshnew.android.testmodule.model.TestBasic;
import com.utkarshnew.android.testmodule.model.TestSection;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestBaseActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, View.OnClickListener, NumberPadOnClick, AmazonCallBack, PopupMenu.OnMenuItemClickListener {

    private long mLastNextClickTime = 0;
    private long mLastPreviousClickTime = 0;
    public String testseriesid;
    public String testSeriesname;
    public String first_attempt, course_id = "", result_date = "", test_submission = "", post_json = "";
    public int language;
    private Long time_server;
    private Long enddate = 0L;
    public TestViewPagerAdapter pagerAdapter;
    public int currentPage = 0;
    String totalQuestion;
    public ArrayList<TestSection> partList = new ArrayList<>();
    public ArrayList<Answers> answerList;
    public ArrayList<AnswersResumeResponse> answersResumeResponses;
    public Data data;
    int lastSection = 0;

    int sectionCount = 1;
    AlertDialog.Builder builder = null;
    AlertDialog alertDialog = null;
    public List<Question> questionBankList;
    public List<Question> resumedumpresponselist;

    ResumeDump resumeDump;
    ResultTestSeries resultTestSeries;
    boolean status = false, isPauseCalled = false;
    ImageView dropdown_icon;
    long count = 0;
    TextView testSeriesName;
    PopupWindow popupWindow;
    ListView section_recyclerview;

    //    PopupMenu popupMenu;
    TestseriesBase testseriesBase;
    List<String> questionPart = new ArrayList<>();
    PartAdapter partAdapter;
    TestBasic basicInfo;
    Progress mProgress;
    String state, lastView, LANG;
    long millisInFuture;
    ImageView calci;
    Dialog quizBasicInfoDialog;
    int prevSeconds, unattampedcount, attemptCount, skipedQuestion, markforreviewCount, savemarkforreviewCount;
    public String PartId;
    public static String currentSectionIdforTest = "";


    public HashMap<String, SectioHashMap> sectioncount = new HashMap<>();

    PopupWindow popUp;
    TextView gridView, listView, nextTV;
    View viewMarkForReviewCount, view1, view2, view3;
    FrameLayout testlayout;
    TextView languageSpinnerTV;
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
    // private AppCompatImageView bookmark_icon_deselect,bookmark_icon_select;
    private boolean isPaused = false;
    private long timeRemaining = 0;
    // private TestSeriesDbHelper testSeriesDbHelper;
    private MyRecyclerAdapter rvNumberPadAdapter;
    private MyRecyclerAdapterTwo rvQuestionAdapter;
    private RelativeLayout clSpinner;
    //    ArrayList<String> sortList = new ArrayList<String>();
    private CountDownTimer countDownTimer = null;
    private NumberPadOnClick numberPadOnClick;
    private boolean isForceFinish = true;
    public List<Social> items1 = new ArrayList<>();
    public List<Social> items2 = new ArrayList<>();
    boolean testsubmit = false;
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
    private static Dialog calculator_dialog;
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
    public TextView changelang;
    ImageView langimage;

    String section_id = "";
    private AppCompatSeekBar seekbar_size;
    private int textSize = 0;
    private Fragment fragmentAttached;
    private String calciUrl = "";

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
        setContentView(R.layout.activity_test_base);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int check = SharedPreference.getInstance().getInt(Const.LANGUAGE);
        if (check == Const.ENGLISH_CODE) {
            Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.ENGLISH), this);
        } else if (check == Const.HINDI_CODE) {
            Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE, Const.HINDI), this);
        }
        context = TestBaseActivity.this;
        SharedPreference.getInstance().putString("VIEW", "0");
        setReference();
        language = getIntent().getExtras().getInt(Const.LANG);
        if (!SharedPreference.getInstance().getString("test_series").equalsIgnoreCase("")) {
            String response = SharedPreference.getInstance().getString("test_series");
            testseriesBase = new Gson().fromJson(response, TestseriesBase.class);
            if (testseriesBase.getData().getTestBasic().getIs_calc_allowed().equalsIgnoreCase("1")) {
                calci.setVisibility(View.VISIBLE);
            } else calci.setVisibility(View.GONE);
            if (testseriesBase.getData().getTestBasic().getTest_assets() != null) {
                if (testseriesBase.getData().getTestBasic().getTest_assets().getDisable_sec_click().equalsIgnoreCase("0")) {
                    dropdown_icon.setOnClickListener(this);
                    clSpinner.setOnClickListener(this);
                }
            } else {
                dropdown_icon.setOnClickListener(this);
                clSpinner.setOnClickListener(this);
            }
            if (testseriesBase.getData().getTestBasic().getLangId().size() == 2) {
                changelang.setVisibility(View.GONE);
                langimage.setVisibility(View.VISIBLE);
                data = testseriesBase.getData();
                if (language == 2) {
                    changelang.setText("E/H");

                    questionBankList = data.getQuestionsHindi();
                } else {
                    changelang.setText("H/E");
                    questionBankList = data.getQuestions();
                }
            } else if (language == 1 || language == 0) {
                changelang.setVisibility(View.GONE);
                langimage.setVisibility(View.GONE);
                changelang.setText("");
                data = testseriesBase.getData();
                questionBankList = data.getQuestions();
            } else {
                if (language == 2)
                    if (testseriesBase.getData().getQuestionsHindi() != null && testseriesBase.getData().getQuestionsHindi().size() > 0) {
                        data = testseriesBase.getData();
                        testseriesBase.getData().setQuestions(testseriesBase.getData().getQuestionsHindi());
                        questionBankList = data.getQuestions();
                        changelang.setVisibility(View.GONE);
                        langimage.setVisibility(View.GONE);
                    }
            }
        } else {
            testseriesBase = (TestseriesBase) Objects.requireNonNull(getIntent().getExtras()).getSerializable(Const.TESTSERIES);
            data = testseriesBase.getData();
            questionBankList = data.getQuestions();
            changelang.setVisibility(View.GONE);
            langimage.setVisibility(View.GONE);
        }
        testseriesid = getIntent().getExtras().getString(Const.TEST_SERIES_ID);
        first_attempt = getIntent().getExtras().getString("first_attempt");

        post_json = getIntent().getStringExtra("post_json");
        if (first_attempt.equalsIgnoreCase("1")) {
            time_server = getIntent().getExtras().getLong("time", 0L);
            if (getIntent().getExtras().getString("enddate") != null && !getIntent().getExtras().getString("enddate").equalsIgnoreCase(""))
                enddate = Long.valueOf(getIntent().getExtras().getString("enddate"));
        }
        result_date = getIntent().getExtras().getString("result_date");
        course_id = getIntent().getExtras().getString("course_id");
        test_submission = getIntent().getExtras().getString("test_submission");
        if (test_submission != null && test_submission.equalsIgnoreCase("0")) {
            test_submission = "0";
        } else {
            test_submission = "1";
        }
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
           /* if (questionBankList.get(i).isIssaveMarkForReview()) {
                ++savemarkforreviewCount;
            }*/
            if (questionBankList.get(i).isMarkForReview() && questionBankList.get(i).getAnswerPosttion() != -1 && questionBankList.get(i).getAnswerPosttion() != -0) {
                ++savemarkforreviewCount;
            }
          /* else if (questionBankList.get(i).isMarkForReview() && questionBankList.get(i).getAnswerPosttion() != -1 && questionBankList.get(i).getAnswerPosttion() != -0) {
                ++attemptCount;
            }*/
            else if (questionBankList.get(i).isMarkForReview()) {
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
        //basicInfo.setAllow_user_move("1");
        if (basicInfo.getDisplay_bubble() != null && !basicInfo.getDisplay_bubble().equalsIgnoreCase("")) {
            if (basicInfo.getDisplay_bubble().equalsIgnoreCase("1")) {
                rvNumberpad.setVisibility(View.VISIBLE);
            } else {
                rvNumberpad.setVisibility(View.GONE);
            }
        } else {
            rvNumberpad.setVisibility(View.VISIBLE);
        }
        //questionDumpList = data.getQuestionDump();
        resumeDump = data.getResume_dump();
        if (resumeDump != null) {
            Gson gson = new Gson();
            answersResumeResponses = gson.fromJson(resumeDump.getQuestion_dump(), new TypeToken<ArrayList<AnswersResumeResponse>>() {
            }.getType());
        }

        PartId = questionBankList.get(0).getSectionId();
        currentSectionIdforTest = questionBankList.get(0).getSectionId();
        imgPause.setVisibility(View.GONE);
        partList = (ArrayList<TestSection>) data.getTestSections();

        rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this, "0");
        rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this);
        rlQuestionPad.setAdapter(rvQuestionAdapter);
        GridLayoutManager manager = new GridLayoutManager(TestBaseActivity.this, 6, GridLayoutManager.VERTICAL, false);
        rlQuestionPad.setLayoutManager(manager);
        rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
        rvNumberpad.setAdapter(rvNumberPadAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(TestBaseActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvNumberpad.setLayoutManager(linearLayoutManager);
        rvNumberpad.setItemAnimator(new DefaultItemAnimator());
        switch (questionBankList.get(0).getQuestionType()) {
            case "FIB":
                btnClear.setVisibility(View.VISIBLE);
                setFragment(FIB_TestSeriesFragment.newInstance(0, textSize), "add", "0");
                break;
            default:
                btnClear.setVisibility(View.VISIBLE);
                setFragment(SC_TestSeriesFragment.newInstance(0, questionBankList.get(0).getQuestionType(), textSize), "add", "0");
        }
        changeTextOnNextAndPrevButton();
        if (partList != null) {
            for (int i = 0; i < partList.size(); i++) {
                SectioHashMap sectioHashMap = new SectioHashMap();
                sectioHashMap.setSectioncount(0);
                if (partList.get(i).getOptional_que() == null) {
                    partList.get(i).setOptional_que("0");
                }
                sectioHashMap.setOptionCount(Integer.parseInt(partList.get(i).getNo_of_questions()) - Integer.parseInt(partList.get(i).getOptional_que()));
                sectioncount.put(partList.get(i).getId(), sectioHashMap);

                partList.get(i).setIndex(getIndexOf(questionBankList, partList.get(i).getId()));
                questionPart.add(partList.get(i).getName() + "-" + partList.get(i).getSection_title() + "( Pos Mark: " + partList.get(i).getMarksPerQuestion() + ": Neg Mark: " + partList.get(i).getNegativeMarks() + ")");
            }
        }
        popUp = popupWindowPart();
        setPart();
        langimage.setOnClickListener(v -> {
            if (changelang.getText().equals("H/E")) {
                questionBankList = data.getQuestionsHindi();
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this, "0");
                rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this);
                rlQuestionPad.setAdapter(rvQuestionAdapter);
                GridLayoutManager manager1 = new GridLayoutManager(TestBaseActivity.this, 6, GridLayoutManager.VERTICAL, false);
                rlQuestionPad.setLayoutManager(manager1);
                rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
                rvNumberpad.setAdapter(rvNumberPadAdapter);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManagerWithSmoothScroller(TestBaseActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rvNumberpad.setLayoutManager(linearLayoutManager1);
                rvNumberpad.setItemAnimator(new DefaultItemAnimator());
                changelang.setText("E/H");
                switch (questionBankList.get(currentPage).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_TestSeriesFragment.newInstance(currentPage, textSize), "selection", "1");
                        break;
                    case "MT":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                }
            } else if (changelang.getText().equals("E/H")) {
                questionBankList = data.getQuestions();
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this, "0");
                rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this);
                rlQuestionPad.setAdapter(rvQuestionAdapter);
                GridLayoutManager manager1 = new GridLayoutManager(TestBaseActivity.this, 6, GridLayoutManager.VERTICAL, false);
                rlQuestionPad.setLayoutManager(manager1);
                rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
                rvNumberpad.setAdapter(rvNumberPadAdapter);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManagerWithSmoothScroller(TestBaseActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rvNumberpad.setLayoutManager(linearLayoutManager1);
                rvNumberpad.setItemAnimator(new DefaultItemAnimator());
                changelang.setText("H/E");
                switch (questionBankList.get(currentPage).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_TestSeriesFragment.newInstance(currentPage, textSize), "selection", "1");
                        break;
                    case "MT":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                }
            }
        });
        changelang.setOnClickListener(v -> {
            if (changelang.getText().equals("H/E")) {
                questionBankList = data.getQuestionsHindi();
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this, "0");
                rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this);
                rlQuestionPad.setAdapter(rvQuestionAdapter);
                GridLayoutManager manager12 = new GridLayoutManager(TestBaseActivity.this, 6, GridLayoutManager.VERTICAL, false);
                rlQuestionPad.setLayoutManager(manager12);
                rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
                rvNumberpad.setAdapter(rvNumberPadAdapter);
                LinearLayoutManager linearLayoutManager12 = new LinearLayoutManagerWithSmoothScroller(TestBaseActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rvNumberpad.setLayoutManager(linearLayoutManager12);
                rvNumberpad.setItemAnimator(new DefaultItemAnimator());
                changelang.setText("E/H");
                switch (questionBankList.get(currentPage).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_TestSeriesFragment.newInstance(currentPage, textSize), "selection", "1");
                        break;
                    case "MT":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                }
            } else if (changelang.getText().equals("E/H")) {
                questionBankList = data.getQuestions();
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this, "0");
                rvNumberPadAdapter = new MyRecyclerAdapter(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this);
                rlQuestionPad.setAdapter(rvQuestionAdapter);
                GridLayoutManager manager12 = new GridLayoutManager(TestBaseActivity.this, 6, GridLayoutManager.VERTICAL, false);
                rlQuestionPad.setLayoutManager(manager12);
                rlQuestionPad.setItemAnimator(new DefaultItemAnimator());
                rvNumberpad.setAdapter(rvNumberPadAdapter);
                LinearLayoutManager linearLayoutManager12 = new LinearLayoutManagerWithSmoothScroller(TestBaseActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rvNumberpad.setLayoutManager(linearLayoutManager12);
                rvNumberpad.setItemAnimator(new DefaultItemAnimator());
                changelang.setText("H/E");
                switch (questionBankList.get(currentPage).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_TestSeriesFragment.newInstance(currentPage, textSize), "selection", "1");
                        break;
                    case "MT":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage, questionBankList.get(currentPage).getQuestionType(), textSize), "selection", "1");
                }
            }
        });
    }

    private void setPart() {
        if (partList != null) {
            PartId = partList.get(sectionCount - 1).getId();
            textSpinner.setText(questionPart.get(sectionCount - 1));
          /*  for (int i = 0; i < partList.size(); i++) {
                if (!(i == partList.size() - 1)) {
                    if (testseriesBase.getLastanswerPosition() >= partList.get(i).getIndexOf() && testseriesBase.getLastanswerPosition() <= partList.get(i + 1).getIndexOf() - 1) {
                        PartId = partList.get(i).getId();
                        textSpinner.setText(questionPart.get(i));
                        Log.d(TAG, "setPart: " + PartId);
                        break;
                    }
                } else {
                    PartId = partList.get(i).getId();
                    textSpinner.setText(questionPart.get(i));
                    break;
                }
            }*/
        }
    }

    protected void setFragment(Fragment fragment, String status, String pos) {
        if (!isPauseCalled) {
            new Handler().post(new Runnable() {
                public void run() {
                    if (!isFinishing()) {
                        fragmentAttached = fragment;
                        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                        t.replace(R.id.testlayout, fragment);
                        t.commit();


                        if (status.equalsIgnoreCase("add")) {
                            if (pos.equalsIgnoreCase("0")) {
                                rvNumberpad.scrollToPosition(0);
                                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), 0);
                                rvNumberPadAdapter.setSelectePosition(0);
                                if (questionBankList.get(0).getAnswerPosttion() == -1) {
                                    questionBankList.get(0).setIsanswer(false, 0);
                                    if (changelang.getText().equals("H/E")) {
                                        testseriesBase.getData().getQuestionsHindi().get(0).setIsanswer(false, 0);
                                    } else if (changelang.getText().equals("E/H")) {
                                        testseriesBase.getData().getQuestions().get(0).setIsanswer(false, 0);
                                    }
                                }
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
                                if (questionBankList.get(currentPage).getAnswerPosttion() == -1) {
                                    questionBankList.get(currentPage).setIsanswer(false, 0);
                                    if (changelang.getText().equals("H/E")) {
                                        testseriesBase.getData().getQuestionsHindi().get(currentPage).setIsanswer(false, 0);
                                    } else if (changelang.getText().equals("E/H")) {
                                        testseriesBase.getData().getQuestions().get(currentPage).setIsanswer(false, 0);
                                    }
                                }
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
                            if (questionBankList.get(currentPage).getAnswerPosttion() == -1) {
                                questionBankList.get(currentPage).setIsanswer(false, 0);
                                if (changelang.getText().equals("H/E")) {
                                    testseriesBase.getData().getQuestionsHindi().get(currentPage).setIsanswer(false, 0);
                                } else if (changelang.getText().equals("E/H")) {
                                    testseriesBase.getData().getQuestions().get(currentPage).setIsanswer(false, 0);
                                }
                            }
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
                                if (questionBankList.get(0).getAnswerPosttion() == -1) {
                                    questionBankList.get(0).setIsanswer(false, 0);
                                    if (changelang.getText().equals("H/E")) {
                                        testseriesBase.getData().getQuestionsHindi().get(0).setIsanswer(false, 0);
                                    } else if (changelang.getText().equals("E/H")) {
                                        testseriesBase.getData().getQuestions().get(0).setIsanswer(false, 0);
                                    }
                                }
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
                                if (questionBankList.get(currentPage).getAnswerPosttion() == -1) {
                                    questionBankList.get(currentPage).setIsanswer(false, 0);
                                    if (changelang.getText().equals("H/E")) {
                                        testseriesBase.getData().getQuestionsHindi().get(currentPage).setIsanswer(false, 0);
                                    } else if (changelang.getText().equals("E/H")) {
                                        testseriesBase.getData().getQuestions().get(currentPage).setIsanswer(false, 0);
                                    }
                                }
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

                    }

                }
            });
        }
    }

    public int getIndexOf(List<Question> list, String sectionId) {
        int pos = 0;
        for (Question myObj : list) {
            if (myObj.getSectionId().equalsIgnoreCase(sectionId))
                return pos;
            pos++;
        }
        return -1;
    }

    private void setUpdatedSize() {
        if (fragmentAttached instanceof SC_TestSeriesFragment) {
            ((SC_TestSeriesFragment) fragmentAttached).onTextSizeChanged(textSize);
        } else if (fragmentAttached instanceof FIB_TestSeriesFragment) {
            ((FIB_TestSeriesFragment) fragmentAttached).onTextSizeChanged(textSize);
        }
    }

    private void setReference() {
        seekbar_size = findViewById(R.id.seekbar_size);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        calci = findViewById(R.id.calci);
        llDrawerRight = findViewById(R.id.llDrawerRight);
        languageSpinnerTV = findViewById(R.id.languageSpinnerTV);
        changelang = findViewById(R.id.changelang);
        langimage = findViewById(R.id.langimage);
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
        clSpinner = findViewById(R.id.cl_part);
        btn_finish = findViewById(R.id.btn_finish);
        llMarkForReviewCount = findViewById(R.id.llMarkForReviewCount);
        viewMarkForReviewCount = findViewById(R.id.viewMarkForReviewCount);
        nextTV = findViewById(R.id.nextTV);
        textSpinner = findViewById(R.id.text_spinner);
        tvQuestionnumber = findViewById(R.id.tv_questionnumber);
        tvanswerCount = findViewById(R.id.tv_answer_count);
        tvUnAnswerCount = findViewById(R.id.tv_unanswer_count);
        testSeriesName = findViewById(R.id.testSeriesName);
        dropdown_icon = findViewById(R.id.dropdown_icon);
        tvSkipCount = findViewById(R.id.tv_skip_count);
        tvmarkForReviewCount = findViewById(R.id.tv_markforReview_count);
        tv_savemarkforReview_count = findViewById(R.id.tv_savemarkforReview_count);
        btn_submit = findViewById(R.id.btn_submit);
        imgPause = findViewById(R.id.img_pause);
        gridView = findViewById(R.id.gridView);
        listView = findViewById(R.id.listView);
        testlayout = findViewById(R.id.testlayout);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekbar_size.setMin(0);
        }
        seekbar_size.setMax(2);
        seekbar_size.setProgress(0);

        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
//        if (SharedPreference.getInstance().getString("TEST_TYPE").equalsIgnoreCase("qbank")) {
//            btnClear.setVisibility(View.GONE);
//            tvmarkForReviewCount.setVisibility(View.GONE);
//            llMarkForReviewCount.setVisibility(View.GONE);
//            viewMarkForReviewCount.setVisibility(View.GONE);
//            llMarkForReview.setVisibility(View.GONE);
//        } else {
       /* bookmark_icon_deselect.setVisibility(View.VISIBLE);
        bookmark_icon_select.setVisibility(View.GONE);*/
        tvmarkForReviewCount.setVisibility(View.VISIBLE);
        tv_savemarkforReview_count.setVisibility(View.VISIBLE);
        llMarkForReviewCount.setVisibility(View.VISIBLE);
        viewMarkForReviewCount.setVisibility(View.VISIBLE);
        llMarkForReview.setVisibility(View.VISIBLE);
        //}
        btn_submit.setOnClickListener(new OnSingleClickListener(() -> {
            if (!isFinishing())
                showPopupSubmitTest();
            return null;
        }));
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

        gridView.setOnClickListener(this);
        listView.setOnClickListener(this);
    }


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private PopupWindow popupWindowPart() {

        popupWindow = new PopupWindow(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_popup, null);
        section_recyclerview = view.findViewById(R.id.section_recyclerview);
        partAdapter = new PartAdapter(this, partList);
        section_recyclerview.setAdapter(partAdapter);
        if (basicInfo.getAllow_user_move().equals("0")) {
            section_recyclerview.setEnabled(false);
        }
        section_recyclerview.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (partList != null && partList.size() > 0) {
                PartId = partList.get(i).getId();
                rvNumberpad.scrollToPosition(partList.get(i).getIndexOf());
                switch (questionBankList.get(partList.get(i).getIndexOf()).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        currentPage = partList.get(i).getIndexOf();
                        setFragment(FIB_TestSeriesFragment.newInstance(partList.get(i).getIndexOf(), textSize), "selection", i == 0 ? "0" : "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        currentPage = partList.get(i).getIndexOf();
                        setFragment(SC_TestSeriesFragment.newInstance(partList.get(i).getIndexOf(), questionBankList.get(partList.get(i).getIndexOf()).getQuestionType(), textSize), "selection", "1");

                }
                changeTextOnNextAndPrevButton();
                textSpinner.setText(partList.get(i).getName() + "-" + partList.get(i).getSection_title() + "( Pos Mark: " + partList.get(i).getMarksPerQuestion() + ": Neg Mark: " + partList.get(i).getNegativeMarks() + ")");
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_rectangle));
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setContentView(view);


        return popupWindow;
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
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
            }
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
        if (isPauseCalled) {
            isPauseCalled = false;
            if (basicInfo.getAllow_user_move().equals("0") && sectionCount <= partList.size()) {
                setPart();
                switch (questionBankList.get(currentPage + 1).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_TestSeriesFragment.newInstance(currentPage + 1, textSize), "add", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(currentPage + 1, questionBankList.get(currentPage + 1).getQuestionType(), textSize), "add", "1");
                }
            }
        }
        Log.e("TestBaseActivity", "onResume");
//        Helper.checkXposed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPauseCalled = true;
        Log.e("TestBaseActivity", "onPause");
    }

    private void setTimer() {
        status = true;
        if (resumeDump.getTime_remain() != null) {
            if (!TextUtils.isEmpty(resumeDump.getTime_remain())) {
                // millisInFuture =  ((Integer.parseInt(data.getTestBasic().getTimeInMins())*60)*1000) - ((Integer.parseInt(resumeDump.getTime_remain()))*1000);
                millisInFuture = (Integer.parseInt(resumeDump.getTime_remain())) * 1000L;
                // millisInFuture = 6000000;
            }
        } else {
            if (questionBankList.get(currentPage).isPause()) {
                millisInFuture = data.getTestBasic().getTimeRemaining();
            } else {
                if (first_attempt.equalsIgnoreCase("1")) {
                    millisInFuture = (Integer.parseInt(data.getTestBasic().getTimeInMins()) * 60L) * 1000;
                    if (enddate == 0) {

                    } else {
                        if ((enddate - time_server) < (Integer.parseInt(data.getTestBasic().getTimeInMins()) * 60L)) {
                            millisInFuture = (enddate - time_server);
                            Log.d("timing", "Remaning time" + millisInFuture);

                            if (basicInfo.getAllow_user_move().equals("0")) {
                                for (int i = 0; i < partList.size(); i++) {
                                    long sectiontime = Long.parseLong(partList.get(i).getSectionTiming()) * 60L;
                                    if (millisInFuture >= sectiontime) {
                                        partList.get(i).setSectionTiming(String.valueOf(sectiontime / 60));
                                        long remaning_time = millisInFuture - sectiontime;
                                        millisInFuture = remaning_time;

                                        Log.d("timing", "Section " + (i + 1) + " " + (remaning_time / 60));

                                    } else {
                                        if (millisInFuture > 0) {
                                            partList.get(i).setSectionTiming(String.valueOf(millisInFuture / 60));
                                            Log.d("timing", "Section " + (i + 1) + " " + (millisInFuture / 60));

                                            millisInFuture = 0;
                                        } else {
                                            partList.get(i).setSectionTiming(String.valueOf(0));
                                        }
                                    }

                                }
                                millisInFuture = ((Long.parseLong(partList.get(lastSection).getSectionTiming()) * 60) * 1000);
                                // Log.d("prince", "" + millisInFuture);
                            } else {
                                millisInFuture = millisInFuture * 1000;

                            }

                         /*   Log.d("prince", "" + enddate);
                            Log.d("prince", "" + time_server);
                            Log.d("prince", "" + millisInFuture);*/
                        } else {
                            if (basicInfo.getAllow_user_move().equals("0")) {
                                millisInFuture = ((Long.parseLong(partList.get(lastSection).getSectionTiming()) * 60) * 1000);
                            }
                        }
                    }

                } else {
                    if (basicInfo.getAllow_user_move().equals("0")) {
                        millisInFuture = ((Long.parseLong("1") * 30) * 1000);

                    } else {
                        millisInFuture = (Integer.parseInt(data.getTestBasic().getTimeInMins()) * 60L) * 1000;
                    }

                }
            }
        }
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count = millisUntilFinished / 1000;
                tvTime.setText(formatSeconds((int) count));
                data.getTestBasic().setTimeRemaining(millisUntilFinished);
            }

            @Override
            public void onFinish() {


                if (basicInfo.getAllow_user_move().equals("0") && sectionCount < partList.size()) {
                    Log.e(TAG, "onFinish: " + sectionCount);
                    btnNext.setVisibility(View.VISIBLE);

                    rvNumberpad.scrollToPosition(partList.get(sectionCount).getIndexOf());
                    testseriesBase.setLastanswerPosition(currentPage);

                    currentPage = partList.get(sectionCount).getIndexOf() - 1;

                    currentSectionIdforTest = partList.get(sectionCount).getId();

                    switch (questionBankList.get(currentPage + 1).getQuestionType()) {
                        case "FIB":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(FIB_TestSeriesFragment.newInstance(currentPage + 1, textSize), "add", "1");
                            break;
                        default:
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_TestSeriesFragment.newInstance(currentPage + 1, questionBankList.get(currentPage + 1).getQuestionType(), textSize), "add", "1");
                    }
                    lastSection = getLastSection(questionBankList.get(currentPage + 1).getSectionId());

                    sectionCount++;
                    setPart();
                    changeTextOnNextAndPrevButton();
                    countDownTimer.cancel();
                    setTimer();
                    return;
                }
                status = false;
                questionBankList.get(currentPage).setPause(false);
                state = "1";
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                    builder = new AlertDialog.Builder(TestBaseActivity.this);
                    builder.setTitle(getResources().getString(R.string.time_out));
                    builder.setMessage(getResources().getString(R.string.time_over_message));
                    builder.setCancelable(false);
                    builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Ok</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            dialog.dismiss();
                            testsubmit = true;
                            finishTestSeries();
                        }
                    });
                    alertDialog = builder.create();
                    if (!(TestBaseActivity.this).isFinishing() && !TestBaseActivity.this.isDestroyed()) {
                        try {
                            alertDialog.show();
                        } catch (Exception ignored) {
                        }
                    }
                }


                //finishTestSeries();
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        Log.e("TestBaseActivity", "onDestroy");
        if (builder != null && alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
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
        HashMap<String, String> finalResponse = new HashMap<>();
        finalResponse.put(Const.USER_ID, SharedPreference.getInstance().getLoggedInUser().getId());
        finalResponse.put(Const.TEST_ID, testseriesid);
        finalResponse.put(Const.STATE, state);
        finalResponse.put(Const.LAST_VIEW, lastView);
        finalResponse.put("first_attempt", first_attempt);
        finalResponse.put(Const.LANG_USED, String.valueOf(language));
        if (course_id != null && !course_id.equalsIgnoreCase("")) {
            finalResponse.put(Const.COURSE_ID, course_id);
        } else {
            course_id = SharedPreference.getInstance().getString(Const.ID);
            finalResponse.put(Const.COURSE_ID, course_id);
        }
        finalResponse.put(Const.TIME_REMAIN, String.valueOf((data.getTestBasic().getTimeRemaining()) / 1000));
        finalResponse.put(Const.QUESTION_DUMP, jsonStr);
        if (first_attempt.equalsIgnoreCase("1")) {
            if (test_submission.equalsIgnoreCase("0")) {
                submit_s3(finalResponse);
            } else {
                netoworkCallSubmitTestseries(finalResponse);
            }
        } else {
            netoworkCallSubmitTestseries(finalResponse);
        }
        // netoworkCallSubmitTestseries(finalResponse);
    }

    private void submit_s3(HashMap<String, String> finalResponse) {
        try {
            s3ImageUploading s3IU;
            Helper.showProgressDialog(this);
            String path = "", filename = "";
            String userid = SharedPreference.getInstance().getLoggedInUser().getId();
            MakeMyExam.userId = userid;
            MakeMyExam.setUserId(userid);
            HashMap<String, String> header_response = new HashMap<>();
            header_response.put(Const.DEVICE_TYPE, Const.DEVICE_TYPE_ANDROID);
            header_response.put(Const.USERID, userid);
            header_response.put(Const.Jwt, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.JWT)) ?
                    SharedPreference.getInstance().getString(Const.JWT) : "");
            header_response.put(Const.LANG, String.valueOf(SharedPreference.getInstance().getInt(Const.LANGUAGE)));
            header_response.put(Const.VERSION, !TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.VERSION)) ?
                    SharedPreference.getInstance().getString(Const.VERSION) : "");
            header_response.put(Const.AUTHORIZATION, API.Bearer);
            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setHeader(header_response);
            masterdatadetailencryptionData.setBody(finalResponse);
            filename = "U" + userid + "_T" + testseriesid + "_C" + course_id + ".json";
            s3ImageUploading.test_file_name = filename;
            path = generateNoteOnSD(this, filename, new Gson().toJson(masterdatadetailencryptionData));
            Log.d("prince", "" + new Gson().toJson(masterdatadetailencryptionData));
            if (Helper.isNetworkConnected(this)) {
                s3IU = new s3ImageUploading(testseriesid, Const.AMAZON_S3_BUCKET_TEST, TestBaseActivity.this, TestBaseActivity.this, null);
                ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();
                MediaFile mediaFile = new MediaFile();
                mediaFile.setFile_name(filename);
                mediaFile.setFile_type(Const.json);
                mediaFile.setFile(path);
                mediaFileArrayList.add(mediaFile);
                s3IU.execute(mediaFileArrayList);
            } else {
                Snackbar.make(drawerLayout.getRootView(), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                if (result_date != null && !result_date.equalsIgnoreCase("0") && !result_date.equalsIgnoreCase("1") && !result_date.equalsIgnoreCase("")) {
                    Constants.REFRESHPAGE = "true";
                    Intent intent = new Intent(TestBaseActivity.this, TestSubmissionActivity.class);
                    intent.putExtra("result_date", result_date);
                    intent.putExtra("test_name", testSeriesName.getText().toString());
                    Helper.gotoActivity_finish(intent, TestBaseActivity.this);
                } else {
                    Constants.REFRESHPAGE = "true";
                    Intent intent = new Intent(TestBaseActivity.this, TestSubmissionActivity.class);
                    intent.putExtra("result_date", result_date);
                    intent.putExtra("test_name", testSeriesName.getText().toString());
                    Helper.gotoActivity_finish(intent, TestBaseActivity.this);
                }
            }
        } catch (Exception e) {
            netoworkCallSubmitTestseries(finalResponse);
            e.printStackTrace();
        }
    }

    public String formatSeconds(int timeInSeconds) {
        Log.d("prince", "" + timeInSeconds);
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        if (seconds != prevSeconds)
            prevSeconds = seconds;
        if (testseriesBase.getData().getTestBasic().getLangId().size() == 2) {
            questionBankList.get(currentPage).setTotalTimeSpent(questionBankList.get(currentPage).getTotalTimeSpent() + 1);
            if (changelang.getText().equals("E/H")) {
                if (data.getQuestions() != null && data.getQuestions().size() > 0)
                    data.getQuestions().get(currentPage).setTotalTimeSpent(questionBankList.get(currentPage).getTotalTimeSpent());

            } else if (changelang.getText().equals("H/E")) {

                if (data.getQuestionsHindi() != null && data.getQuestionsHindi().size() > 0)
                    data.getQuestionsHindi().get(currentPage).setTotalTimeSpent(questionBankList.get(currentPage).getTotalTimeSpent());

            }
        } else {
            questionBankList.get(currentPage).setTotalTimeSpent(questionBankList.get(currentPage).getTotalTimeSpent() + 1);
        }
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
        if (drawerLayout.isDrawerOpen(llDrawerRight)) {
            drawerLayout.closeDrawer(llDrawerRight);
        } else {
            showPopupSubmitTest();
        }
//        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view1:
                textSize = 0;
                seekbar_size.setProgress(0);
                setUpdatedSize();
                break;

            case R.id.view2:
                textSize = 1;
                seekbar_size.setProgress(1);
                setUpdatedSize();
                break;

            case R.id.view3:
                textSize = 2;
                seekbar_size.setProgress(2);
                setUpdatedSize();
                break;
            case R.id.calci:
                // openCalculator(TestBaseActivity.this);
                calci.setOnClickListener(null);
                if (TextUtils.isEmpty(calciUrl))
                    hit_api_for_calculator();
                else openCalculator_web(TestBaseActivity.this, calciUrl);
                break;
            case R.id.btn_next:
                if (SystemClock.elapsedRealtime() - mLastNextClickTime < 1000) {
                    return;
                }
                mLastNextClickTime = SystemClock.elapsedRealtime();
                Log.e(TAG, "onClick: ");
                if (basicInfo.getAllow_user_move().equals("0")) {
                    if (currentSectionIdforTest.equals(questionBankList.get(currentPage + 1).getSectionId())) {
                        testseriesBase.setLastanswerPosition(currentPage);
                        switch (questionBankList.get(currentPage + 1).getQuestionType()) {
                            case "FIB":
                                btnClear.setVisibility(View.VISIBLE);
                                setFragment(FIB_TestSeriesFragment.newInstance(currentPage + 1, textSize), "add", "1");
                                break;
                            default:
                                btnClear.setVisibility(View.VISIBLE);
                                setFragment(SC_TestSeriesFragment.newInstance(currentPage + 1, questionBankList.get(currentPage + 1).getQuestionType(), textSize), "add", "1");
                        }
                        setPart();
                        changeTextOnNextAndPrevButton();
                    } else {
                        Toast.makeText(TestBaseActivity.this, getResources().getString(R.string.switching_section_is_not_allowed), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    testseriesBase.setLastanswerPosition(currentPage);
                    switch (questionBankList.get(currentPage + 1).getQuestionType()) {
                        case "FIB":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(FIB_TestSeriesFragment.newInstance(currentPage + 1, textSize), "add", "1");
                            break;
                        default:
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_TestSeriesFragment.newInstance(currentPage + 1, questionBankList.get(currentPage + 1).getQuestionType(), textSize), "add", "1");
                    }
                    setPart();
                    changeTextOnNextAndPrevButton();
                }


                break;
            case R.id.btn_prev:
                if (SystemClock.elapsedRealtime() - mLastPreviousClickTime < 1000) {
                    return;
                }
                mLastPreviousClickTime = SystemClock.elapsedRealtime();
                Log.e(TAG, "onClick: ");
                if (basicInfo.getAllow_user_move().equals("0")) {
                    if (currentSectionIdforTest.equals(questionBankList.get(currentPage - 1).getSectionId())) {

                        testseriesBase.setLastanswerPosition(currentPage - 1);
                        switch (questionBankList.get(currentPage - 1).getQuestionType()) {
                            case "FIB":
                                btnClear.setVisibility(View.VISIBLE);
                                setFragment(FIB_TestSeriesFragment.newInstance(currentPage - 1, textSize), "remove", "1");
                                break;
                            default:
                                btnClear.setVisibility(View.VISIBLE);
                                setFragment(SC_TestSeriesFragment.newInstance(currentPage - 1, questionBankList.get(currentPage - 1).getQuestionType(), textSize), "remove", "1");
                        }
                        setPart();
                        changeTextOnNextAndPrevButton();
                    } else {
                        Toast.makeText(TestBaseActivity.this, getResources().getString(R.string.switching_section_is_not_allowed), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    testseriesBase.setLastanswerPosition(currentPage - 1);
                    switch (questionBankList.get(currentPage - 1).getQuestionType()) {
                        case "FIB":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(FIB_TestSeriesFragment.newInstance(currentPage - 1, textSize), "remove", "1");
                            break;
                        default:
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_TestSeriesFragment.newInstance(currentPage - 1, questionBankList.get(currentPage - 1).getQuestionType(), textSize), "remove", "1");
                    }
                    setPart();
                    changeTextOnNextAndPrevButton();
                }

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
                    FIB_TestSeriesFragment test = (FIB_TestSeriesFragment) getSupportFragmentManager().findFragmentById(R.id.testlayout);
                    test.refereshPage();
                } else {
                    SC_TestSeriesFragment test = (SC_TestSeriesFragment) getSupportFragmentManager().findFragmentById(R.id.testlayout);
                    test.refereshPage();
                }
                notifynumberApater();
                break;
            case R.id.img_testmenu:
                CountTotalanswer();
                drawerLayout.openDrawer(llDrawerRight);
                break;
            case R.id.cl_part:
            case R.id.dropdown_icon:
                popUp.showAsDropDown(view, 0, 0);
                break;
            case R.id.img_testback:
            case R.id.img_pause:
                showPopupSubmitTest();
                break;
            case R.id.gridView:
                SharedPreference.getInstance().putString("VIEW", "0");
                GridLayoutManager manager = new GridLayoutManager(TestBaseActivity.this, 6, GridLayoutManager.VERTICAL, false);
                rlQuestionPad.setLayoutManager(manager);
                gridView.setTextColor(getResources().getColor(R.color.black));
                gridView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.grid_view, 0, 0, 0);
                listView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.list_view_gray, 0, 0, 0);
                listView.setTextColor(getResources().getColor(R.color.colorGray3));
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this, "0");
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
                rvQuestionAdapter = new MyRecyclerAdapterTwo(questionBankList, TestBaseActivity.this, items, R.layout.single_row_testpad_no, TestBaseActivity.this, "1");
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
        answer.setConfig_id(questionBankList.get(position).getConfigId());
        answer.setSection_id(questionBankList.get(position).getSectionId());
        answer.setIndex(String.valueOf(position));
        answer.setState(state);
        answer.setIs_bookmarked(bookmarkvalue);
        answer.setAnswers(questionBankList.get(position).getAnswers());
        answer.setOn_screen(String.valueOf(questionBankList.get(position).getTotalTimeSpent()));
        return answer;
    }

    public String generateNoteOnSD(Context context, String sFileName, String sBody) {
        File root = null;
        try {
            root = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    + "/UTKARSH_TEST/", MakeMyExam.userId);
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(root + File.separator + sFileName).getAbsolutePath();
    }


    private int getLastSection(String sectionId) {
        int lastPos = 0;
        for (int i = 0; i < data.getTestSections().size(); i++) {
            if (sectionId.equalsIgnoreCase(data.getTestSections().get(i).getId())) {
                lastPos = i;
                Log.e("hghjcghvgh", "getLastSection: " + i);
            }
        }
        return lastPos;
    }

    @Override
    public void sendOnclickInd(int index) {
        if (currentPage != index) {
            if (testseriesBase.getData().getTestBasic().getAllow_user_move().equalsIgnoreCase("0")) {
                lastSection = getLastSection(currentSectionIdforTest);
                if (currentSectionIdforTest.equalsIgnoreCase(questionBankList.get(index).getSectionId())) {
                    currentPage = index;
                    switch (questionBankList.get(index).getQuestionType()) {
                        case "FIB":
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(FIB_TestSeriesFragment.newInstance(index, textSize), "selection", "1");
                            break;
                        default:
                            btnClear.setVisibility(View.VISIBLE);
                            setFragment(SC_TestSeriesFragment.newInstance(index, questionBankList.get(index).getQuestionType(), textSize), "selection", "1");
                    }
                    setPart();
                    return;
                }
                Toast.makeText(TestBaseActivity.this, getResources().getString(R.string.switching_section_is_not_allowed), Toast.LENGTH_SHORT).show();

            } else {
                currentPage = index;
                switch (questionBankList.get(index).getQuestionType()) {
                    case "FIB":
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(FIB_TestSeriesFragment.newInstance(index, textSize), "selection", "1");
                        break;
                    default:
                        btnClear.setVisibility(View.VISIBLE);
                        setFragment(SC_TestSeriesFragment.newInstance(index, questionBankList.get(index).getQuestionType(), textSize), "selection", "1");
                }
                setPart();

            }


        }
    }

    private void netoworkCallSubmitTestseries(HashMap<String, String> finalResponse) {
        if (Helper.isNetworkConnected(TestBaseActivity.this)) {
            mProgress.show();
            APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setFinalResponse(finalResponse);
            HashMap<String, String> finalResponse1 = new HashMap<>();
            finalResponse1.putAll(finalResponse);
            Gson gson = new Gson();
            String doseStr = gson.toJson(finalResponse1);
            String doseStrScr = AES.encrypt(doseStr);
            Call<String> response = null;
            response = Service.submitTestSeries(doseStrScr);
            assert response != null;
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    mProgress.dismiss();
                    if (response.body() != null) {
                        JSONObject jsonResponse = null;
                        String jsonString;
                        try {
                            jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                        } catch (Exception e) {
                            jsonString = response.body();
                        }
                        try {
                            jsonResponse = new JSONObject(jsonString);
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time"))) * 1000);
                            if (jsonResponse.optString(Const.STATUS).equalsIgnoreCase("true")) {

                                if (post_json != null) {
                                    PostDataTable postDataTable = new Gson().fromJson(post_json, PostDataTable.class);
                                    if (postDataTable != null && postDataTable.getJson() != null && !postDataTable.getJson().isEmpty()) {
                                        Json json = new Gson().fromJson(postDataTable.getJson(), Json.class);
                                        json.setState("1");
                                        if (UtkashRoom.getAppDatabase(TestBaseActivity.this).getFeedDao().isFeedExist(postDataTable.getId()))
                                            UtkashRoom.getAppDatabase(TestBaseActivity.this).getFeedDao().update_result(new Gson().toJson(json), postDataTable.getId(), postDataTable.getMasterCat());
                                    }
                                }

                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
                                JSONObject data = jsonResponse.optJSONObject(Const.DATA);
                                if (data.optString("result_page").equals("1")) {
                                    Intent resultScreen = new Intent(TestBaseActivity.this, QuizActivity.class);
                                    if (first_attempt.equalsIgnoreCase("1")) {
                                        Constants.REFRESHPAGE = "true";
                                    }

                                    if (enddate == 0 && !first_attempt.equalsIgnoreCase("1")) {
                                        resultScreen.putExtra("show_leader", "0");
                                    } else if (enddate > 0 && !first_attempt.equalsIgnoreCase("1")) {
                                        resultScreen.putExtra("show_leader", "0");
                                    }

                                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                    resultScreen.putExtra(Const.STATUS, testseriesid);
                                    resultScreen.putExtra(Const.NAME, testSeriesname);
                                    resultScreen.putExtra("first_attempt", first_attempt);
                                    Helper.gotoActivity(resultScreen, TestBaseActivity.this);
                                    finish();
                                } else if (data.optString("result_page").equals("0")) {
                                    if (first_attempt.equalsIgnoreCase("1")) {
                                        Constants.REFRESHPAGE = "true";
                                    }
                                    showPopupResult_date(result_date);
                                } else if (data.optString("result_page").equals("2")) {
                                    if (first_attempt.equalsIgnoreCase("1")) {
                                        Constants.REFRESHPAGE = "true";
                                    }
                                    showPopupResult_date(result_date);
                                }
                            } else if (jsonResponse.optString(Const.STATUS).equalsIgnoreCase("false")) {
                                RetrofitResponse.GetApiData(TestBaseActivity.this, jsonResponse.has(Const.AUTH_CODE) ? jsonResponse.getString(Const.AUTH_CODE) : "", jsonResponse.getString(Const.MESSAGE), false);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mProgress.dismiss();
                }
            });
        } else {
            Toast.makeText(TestBaseActivity.this, R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showPopupResult_date(String result_date) {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.result_date_layout, null, false);
        final Dialog quizBasicInfoDialog = new Dialog(this);
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizBasicInfoDialog.setCancelable(false);
        quizBasicInfoDialog.setCanceledOnTouchOutside(false);
        quizBasicInfoDialog.setContentView(v);
        quizBasicInfoDialog.show();
        Button btnSubmit;
        TextView message;
        btnSubmit = v.findViewById(R.id.btn_submit);
        message = v.findViewById(R.id.message);
        message.setText("Your Result will be declared on" + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(result_date) * 1000)));
        btnSubmit.setOnClickListener(view -> {
            quizBasicInfoDialog.dismiss();
            finish();
        });
    }

    private void showPopupSubmitTest() {
        try {
            if (Helper.isNetworkConnected(this)) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View v = li.inflate(R.layout.dialogbox_test_submit, null, false);
                if (quizBasicInfoDialog == null) {
                    quizBasicInfoDialog = new Dialog(this);
                    quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    quizBasicInfoDialog.setCanceledOnTouchOutside(false);
                    quizBasicInfoDialog.setCancelable(false);
                    quizBasicInfoDialog.setContentView(v);
                    if (!TestBaseActivity.this.isFinishing()) {
                        quizBasicInfoDialog.show();
                    }
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
                    btnCancel.setOnClickListener(view -> {
                        quizBasicInfoDialog.dismiss();
                        quizBasicInfoDialog = null;
                    });
                    btnSubmit.setOnClickListener(view -> {
                        if (Helper.isNetworkConnected(this)) {

                            if (testseriesBase.getData().getTestBasic().getTimeBoundation().equalsIgnoreCase("1")) {
                                Toast.makeText(TestBaseActivity.this, "You Can submit only after test time is over", Toast.LENGTH_SHORT).show();
                                quizBasicInfoDialog.dismiss();

                            } else {
                                isForceFinish = false;
                                quizBasicInfoDialog.dismiss();
                                state = "1";
                                testsubmit = true;
                                finishTestSeries();
                            }

                        } else {
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    quizBasicInfoDialog.show();
                }
            } else {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } catch (WindowManager.BadTokenException bte) {
            bte.printStackTrace();
        }
    }

    public void notifynumberApater() {
        rvQuestionAdapter.notifyDataSetChanged();
        rvNumberPadAdapter.notifyDataSetChanged();
    }

    private void hit_api_for_calculator() {
        NetworkCall networkCall = new NetworkCall(this, this);
        networkCall.NetworkAPICall(API.MOBILE_CALCULATOR, "", true, false);
    }

    public void openCalculator_web(Context context, String typeApi) {
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
            // chromium, enable hardware acceleration
            calcwebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            calcwebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            calcwebView.getSettings().setJavaScriptEnabled(true);
            calcwebView.getSettings().setBuiltInZoomControls(true);
            calcwebView.getSettings().setDisplayZoomControls(false);
            calcwebView.loadDataWithBaseURL("null", typeApi, "text/html",
                    "UTF-8", "about:blank");
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

            calculator_dialog.setOnDismissListener(v -> {
                calci.setOnClickListener(this);
            });
        } catch (Exception e) {
            calci.setOnClickListener(this);
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

    private void obtainInputValues(String input) {
        switch (input) {
            case "0":
                currentDisplayedInput += "0";
                inputToBeParsed += "0";
                break;
            case "1":
                if (isInverse) {
                    currentDisplayedInput += "π";
                    inputToBeParsed += "pi";
                } else {
                    currentDisplayedInput += "1";
                    inputToBeParsed += "1";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "2":
                if (isInverse) {
                    currentDisplayedInput += "e";
                    inputToBeParsed += "e";
                } else {
                    currentDisplayedInput += "2";
                    inputToBeParsed += "2";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "3":
                if (isInverse) {
                    currentDisplayedInput += ",";
                    inputToBeParsed += ",";
                } else {
                    currentDisplayedInput += "3";
                    inputToBeParsed += "3";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "4":
                if (isInverse) {
                    currentDisplayedInput += "!(";
                    inputToBeParsed += "!(";
                } else {
                    currentDisplayedInput += "4";
                    inputToBeParsed += "4";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "5":
                if (isInverse) {
                    currentDisplayedInput += "comb(";
                    inputToBeParsed += "comb(";
                } else {
                    currentDisplayedInput += "5";
                    inputToBeParsed += "5";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "6":
                if (isInverse) {
                    currentDisplayedInput += "permu(";
                    inputToBeParsed += "permu(";
                } else {
                    currentDisplayedInput += "6";
                    inputToBeParsed += "6";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "7":
                currentDisplayedInput += "7";
                inputToBeParsed += "7";
                break;
            case "8":
                currentDisplayedInput += "8";
                inputToBeParsed += "8";
                break;
            case "9":
                currentDisplayedInput += "9";
                inputToBeParsed += "9";
                break;
            case ".":
                currentDisplayedInput += ".";
                inputToBeParsed += ".";
                break;
            case "+":
                currentDisplayedInput += "+";
                inputToBeParsed += "+";
                break;
            case "-":
                currentDisplayedInput += "-";
                inputToBeParsed += "-";
                break;
            case "÷":
                currentDisplayedInput += "÷";
                inputToBeParsed += "/";
                break;
            case "x":
                currentDisplayedInput += "*";
                inputToBeParsed += "*";
                break;
            case "(":
                currentDisplayedInput += "(";
                inputToBeParsed += "(";
                break;
            case ")":
                currentDisplayedInput += ")";
                inputToBeParsed += ")";
                break;
            case "%":
                if (isInverse) {
                    currentDisplayedInput += "1÷";
                    inputToBeParsed += "1÷";
                } else {
                    currentDisplayedInput += "%";
                    inputToBeParsed += "%";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "ln":
                if (isInverse) {
                    currentDisplayedInput += "e^";
                    inputToBeParsed += "e^";
                } else {
                    currentDisplayedInput += "ln(";
                    inputToBeParsed += "ln(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "log":
                if (isInverse) {
                    currentDisplayedInput += "10^";
                    inputToBeParsed += "10^";
                } else {
                    currentDisplayedInput += "log(";
                    inputToBeParsed += "log(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "√":
                if (isInverse) {
                    currentDisplayedInput += "3√(";
                    inputToBeParsed += "crt(";
                } else {
                    currentDisplayedInput += "√(";
                    inputToBeParsed += "sqrt(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "Yx":
                currentDisplayedInput += "^";
                inputToBeParsed += "^";
                break;
            case "sin":
                if (isInverse) {
                    currentDisplayedInput += "asin(";
                    inputToBeParsed += "asin(";
                } else {
                    currentDisplayedInput += "sin(";
                    inputToBeParsed += "sin(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "cos":
                if (isInverse) {
                    currentDisplayedInput += "acos(";
                    inputToBeParsed += "acos(";
                } else {
                    currentDisplayedInput += "cos(";
                    inputToBeParsed += "cos(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "tan":
                if (isInverse) {
                    currentDisplayedInput += "atan(";
                    inputToBeParsed += "atan(";
                } else {
                    currentDisplayedInput += "tan(";
                    inputToBeParsed += "tan(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "exp":
                currentDisplayedInput += "E";
                inputToBeParsed += "E0";
                break;
            case "x2":
                if (isInverse) {
                    currentDisplayedInput += "^3";
                    inputToBeParsed += "^3";
                } else {
                    currentDisplayedInput += "^2";
                    inputToBeParsed += "^2";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "rnd":
                double ran = Math.random();
                currentDisplayedInput += String.valueOf(ran);
                inputToBeParsed += String.valueOf(ran);
                break;
            case "ABS":
                currentDisplayedInput += "abs(";
                inputToBeParsed += "abs(";
                break;
            case "MR":
                String mValue = getStoredPreferenceValue(context);
                String result = removeTrailingZero(mValue);
                if (!result.equals("0")) {
                    currentDisplayedInput += result;
                    inputToBeParsed += result;
                }
                break;
            case "MS":
                clearMemoryStorage(context);
                break;
            case "M+":
                if (isInverse) {
                    double inputValueMinus = isANumber(outputResult.getText().toString());
                    if (!Double.isNaN(inputValueMinus)) {
                        subtractMemoryStorage(context, inputValueMinus);
                    }
                } else {
                    double inputValue = isANumber(outputResult.getText().toString());
                    if (!Double.isNaN(inputValue)) {
                        addToMemoryStorage(context, inputValue);
                    }
                }
                toggleInverse();
                toggleShiftButton();
                break;
        }
        outputResult.setText(currentDisplayedInput);
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

    private void toggleInverse() {
        if (isInverse) {
            isInverse = false;
        }
    }

    private void toggleShiftButton() {
        if (isInverse) {
            shiftDisplay.setText("SHIFT");
        } else {
            shiftDisplay.setText("");
        }
    }

    private double isANumber(String numberInput) {
        double result = Double.NaN;
        try {
            result = Double.parseDouble(numberInput);
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

    private void addToMemoryStorage(Context context, double inputToStore) {
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue + (float) inputToStore;
        setPreference(context, newValue);
    }

    private void subtractMemoryStorage(Context context, double inputToStore) {
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue - (float) inputToStore;
        setPreference(context, newValue);
    }

    private void clearMemoryStorage(Context context) {
        setPreference(context, 0);
    }

    private String getStoredPreferenceValue(Context context) {
        float returnedValue = getPreference(context);
        return String.valueOf(returnedValue);
    }

    public boolean setPreference(Context c, float value) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("key", value);
        return editor.commit();
    }

    public float getPreference(Context c) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        float value = settings.getFloat("key", 0);
        return value;
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

    @Override
    public void onS3UploadData(ArrayList<MediaFile> images) {
        Helper.dismissProgressDialog();
        if (images != null && !images.isEmpty()) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            TestTable testTable = new TestTable();
            testTable.setCourse_id(course_id);
            testTable.setStatus("ATTEMPTED");
            testTable.setTest_id(testseriesid);
            testTable.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
            UtkashRoom.getAppDatabase(TestBaseActivity.this).getTestDao().addUser(testTable);
            Log.d("prince", "" + images.get(0).getFile());
            String filename = "U" + MakeMyExam.userId + "_T" + testseriesid + "_C" + course_id + ".json";
            File root = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    + "/UTKARSH_TEST/" + MakeMyExam.userId + "/" + filename);
            if (root.exists()) {
                root.delete();
            }
        }
        if (result_date != null && !result_date.equalsIgnoreCase("0") && !result_date.equalsIgnoreCase("1") && !result_date.equalsIgnoreCase("")) {
            if (first_attempt.equalsIgnoreCase("1")) {
                Constants.REFRESHPAGE = "true";
            }
            Intent intent = new Intent(TestBaseActivity.this, TestSubmissionActivity.class);
            intent.putExtra("result_date", result_date);
            intent.putExtra("test_name", testSeriesName.getText().toString());
            Helper.gotoActivity_finish(intent, TestBaseActivity.this);
        } else {
            if (first_attempt.equalsIgnoreCase("1")) {
                Constants.REFRESHPAGE = "true";
            }
            Intent intent = new Intent(TestBaseActivity.this, TestSubmissionActivity.class);
            intent.putExtra("result_date", result_date);
            intent.putExtra("test_name", testSeriesName.getText().toString());
            Helper.gotoActivity_finish(intent, TestBaseActivity.this);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
    /*    if (partList!=null && partList.size()>0)
        {
            if (!item.getTitle().equals(textSpinner.getText()))
            {
                for (int i=0;i<partList.size();i++)
                {
                    if (item.getTitle().equals(partList.get(i).getName() +"-"+ partList.get(i).getSection_title()))
                    {
                        rvNumberpad.scrollToPosition(partList.get(i).getIndexOf());
                        switch (questionBankList.get(partList.get(i).getIndexOf()).getQuestionType()) {
                            case "FIB":
                                btnClear.setVisibility(View.VISIBLE);
                                currentPage = partList.get(i).getIndexOf();
                                setFragment(FIB_TestSeriesFragment.newInstance(partList.get(i).getIndexOf()), "selection", i==0? "0":"1");
                                break;
                            default:
                                btnClear.setVisibility(View.VISIBLE);
                                currentPage = partList.get(i).getIndexOf();
                                setFragment(SC_TestSeriesFragment.newInstance(i, questionBankList.get(partList.get(i).getIndexOf()).getQuestionType()), "selection", i==0? "0":"1");
                        }
                        changeTextOnNextAndPrevButton();
                        textSpinner.setText(partList.get(i).getName()+"-"+ partList.get(i).getSection_title());
                        popupMenu.dismiss();
                        break;
                    }

                }

            }

        }*/

        return false;
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {
            case API.MOBILE_CALCULATOR:
                return service.GET_CALCULATOR();
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.MOBILE_CALCULATOR:
                try {
                    if (!TextUtils.isEmpty(typeApi)) {
                        calciUrl = typeApi;
                        openCalculator_web(context, typeApi);
                    } else {
                        calci.setOnClickListener(this);
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    calci.setOnClickListener(this);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Toast.makeText(context, jsonstring, Toast.LENGTH_SHORT).show();
        calci.setOnClickListener(this);

    }
}
