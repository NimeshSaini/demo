package com.utkarshnew.android.testmodule.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.BuildConfig;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.MediaFile;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.AmazonUpload.AmazonCallBack;
import com.utkarshnew.android.Utils.AmazonUpload.s3ImageUploading;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapter;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapterTwo;
import com.utkarshnew.android.testmodule.adapter.TestViewPagerAdapter;
import com.utkarshnew.android.testmodule.fragment.FIB_TestSeriesFragment;
import com.utkarshnew.android.testmodule.fragment.LearnFragmentReport;
import com.utkarshnew.android.testmodule.fragment.SC_TestSeriesFragment;
import com.utkarshnew.android.testmodule.fragment.ViewSolutionFragment;
import com.utkarshnew.android.testmodule.interfaces.NumberPadOnClick;
import com.utkarshnew.android.testmodule.layoutmanager.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.testmodule.model.Challenge_Report;
import com.utkarshnew.android.testmodule.model.ResultTestSeries_Report;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSolutionActivity extends AppCompatActivity implements NumberPadOnClick, View.OnClickListener, AmazonCallBack {

    public TestViewPagerAdapter pagerAdapter;
    public int currentPage = 0;
    public ResultTestSeries_Report resultTestSeries2;
    public ResultTestSeries_Report resultTestSeries;
    String test_segment_id = "", test_series_name = "", frag_type = "", type;
    int lang;
    DrawerLayout drawerLayout;
    TextView testSeriesName;
    TextView tvQuestionnumber, challenges_txt, changelang;
    ConstraintLayout rootConstraint;
    ImageView img_testback;
    Progress mProgress;
    private s3ImageUploading s3IU;
    ImageView langimage;

    FrameLayout btnNext, btnPrev, testlayout;
    AppCompatImageView bookmark_icon_select, bookmark_icon_deselect;
    private ViewPager viewPagerSolution;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private MyRecyclerAdapter rvNumberPadAdapter;
    private RecyclerView rvNumberpad;
    private TextView txt_tapAddNote7, txt_fileName7, txt_fileSize7;
    private LinearLayout layout_fileAttach7;
    private RelativeLayout layout_fileDetail7;
    ImageView img_fileAttached;
    private String selectedFilePath = "", str_qtype = "", file_url = "";
    private static final int PICK_FILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_view_solution);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        initView();
    }

    private void initView() {
        mProgress = new Progress(this);
        mProgress.setCancelable(false);

        rvNumberpad = findViewById(R.id.rvnumberpad);
        changelang = findViewById(R.id.changelang);
        langimage = findViewById(R.id.langimage);
        testlayout = findViewById(R.id.testlayout);
        viewPagerSolution = findViewById(R.id.view_pager_test);
        tvQuestionnumber = findViewById(R.id.tv_questionnumber);
        challenges_txt = findViewById(R.id.challenges_txt);
        img_testback = findViewById(R.id.img_testback);
        testSeriesName = findViewById(R.id.testSeriesName);
        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        bookmark_icon_select = findViewById(R.id.bookmark_icon_select);
        bookmark_icon_deselect = findViewById(R.id.bookmark_icon_deselect);
        rootConstraint = findViewById(R.id.rootConstraint);
        drawerLayout = findViewById(R.id.drawerLayout);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        img_testback.setOnClickListener(this);
        challenges_txt.setOnClickListener(this);
        getBundleData();
        //getViewSolutionData();
    }

    @SuppressLint("SetTextI18n")
    private void getBundleData() {
        test_segment_id = getIntent().getStringExtra(Const.TESTSEGMENT_ID);
        test_series_name = getIntent().getStringExtra(Const.NAME);
        frag_type = getIntent().getStringExtra(Const.FRAG_TYPE);
        type = getIntent().getStringExtra("type");
        lang = getIntent().getIntExtra(Const.LANG, 1);
//        resultTestSeries2 = (ResultTestSeries_Report) getIntent().getExtras().getSerializable(Const.RESULT_SCREEN);

        String data = SharedPreference.getInstance().getString("testresult");
        resultTestSeries2 = new Gson().fromJson(data, ResultTestSeries_Report.class);
        resultTestSeries = new Gson().fromJson(data, ResultTestSeries_Report.class);
        ;

        if (type != null && lang == 2 && resultTestSeries2.getData().getQuestionsHindi() != null && resultTestSeries2.getData().getQuestionsHindi().size() > 0) {
            resultTestSeries2.getData().setQuestions(resultTestSeries2.getData().getQuestionsHindi());
        } else if (type == null && resultTestSeries2.getData().getLang_id().length() == 3) {
            changelang.setVisibility(View.GONE);
            langimage.setVisibility(View.VISIBLE);
            if (lang == 2) {
                changelang.setText("E/H");
            } else {
                changelang.setText("H/E");
            }
        } else if (type == null && lang == 2 && resultTestSeries2.getData().getQuestionsHindi() != null && resultTestSeries2.getData().getQuestionsHindi().size() > 0) {
            resultTestSeries2.getData().setQuestions(resultTestSeries2.getData().getQuestionsHindi());
        }

        if (type != null) {
            if (resultTestSeries2.getData().getLang_id().length() == 3) {
                changelang.setVisibility(View.GONE);
                langimage.setVisibility(View.VISIBLE);
                if (lang == 2) {
                    changelang.setText("E/H");
                } else {
                    changelang.setText("H/E");
                }
            } else {
                changelang.setVisibility(View.GONE);
                langimage.setVisibility(View.GONE);
                changelang.setText("");
            }
        }


        langimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == null) {
                    if (changelang.getText().equals("H/E")) {
                        if (resultTestSeries2.getData().getQuestionsHindi() != null && resultTestSeries2.getData().getQuestionsHindi().size() > 0) {
                            resultTestSeries2.getData().setQuestions(resultTestSeries.getData().getQuestionsHindi());
                        }
                        changelang.setText("E/H");
                        setFragment(ViewSolutionFragment.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType()), "add", "0");

                    } else if (changelang.getText().equals("E/H")) {
                        if (resultTestSeries2.getData().getQuestions() != null && resultTestSeries2.getData().getQuestions().size() > 0) {
                            resultTestSeries2.getData().setQuestions(resultTestSeries.getData().getQuestions());
                        }
                        changelang.setText("H/E");
                        setFragment(ViewSolutionFragment.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType()), "add", "0");
                    }
                } else {
                    if (changelang.getText().equals("H/E")) {
                        if (resultTestSeries2.getData().getQuestionsHindi() != null) {
                            resultTestSeries2.getData().setQuestions(resultTestSeries.getData().getQuestionsHindi());
                        }
                        changelang.setText("E/H");
                        setFragment(LearnFragmentReport.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType(), type), "add", "0");
                    } else if (changelang.getText().equals("E/H")) {
                        if (resultTestSeries2.getData().getQuestions() != null) {
                            resultTestSeries2.getData().setQuestions(resultTestSeries.getData().getQuestions());
                        }
                        changelang.setText("H/E");
                        setFragment(LearnFragmentReport.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType(), type), "add", "0");
                    }
                }

            }
        });


        if (resultTestSeries2.getData().getQuestions() != null && resultTestSeries2.getData().getQuestions().size() > 0) {

            if (test_series_name != null && !test_series_name.equals("")) {
                testSeriesName.setText(test_series_name);
            } else {
                testSeriesName.setText("Solution");
            }
            if (frag_type != null) {
                if (frag_type.equalsIgnoreCase(Const.SOLUTIONREPORT)) {
                    if (type != null && type.equalsIgnoreCase("learn")) {
                        challenges_txt.setVisibility(View.GONE);
                    } else
                        challenges_txt.setVisibility(View.VISIBLE);
                } else {
                    challenges_txt.setVisibility(View.GONE);
                }
            } else {
                challenges_txt.setVisibility(View.GONE);
            }

            if (resultTestSeries2 != null) {
                tvQuestionnumber.setText("Question 1/" + resultTestSeries2.getData().getTotal_questions());
                setViewSolutionData();
            }
            if (lang == 1) {
                Locale myLocale = new Locale("en");
                Locale.setDefault(myLocale);
                Configuration config = new Configuration();
                config.locale = myLocale;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                //totalQuestion = getIntent().getExtras().getString("TOTAL_QUESTIONS");

            } else {
                Locale myLocale = new Locale("hi");
                Locale.setDefault(myLocale);
                Configuration config = new Configuration();
                config.locale = myLocale;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                //totalQuestion = getIntent().getExtras().getString("TOTAL_QUESTIONS");
            }
        }

//        viewPagerSolution.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                changeTextOnNextAndPrevButton();
//            }
//
//            @Override
//            public void onPageSelected(final int position) {
//
//                currentPage = position;
//                rvNumberpad.scrollToPosition(currentPage);
//                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage);
//                rvNumberPadAdapter.setSelectePosition(position);
//
//                if (resultTestSeries2 != null)
//                    tvQuestionnumber.setText("Question " + (position + 1) + "/" + resultTestSeries2.getData().getTotal_questions());
//                //   testseriesBase.setLastanswerPosition(currentPage);
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                Log.e("viewpager", "onPageScrollStateChanged");
//            }
//        });

    }


    @SuppressLint("SetTextI18n")
    protected void setFragment(Fragment fragment, String status, String pos) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();

        if (status.equalsIgnoreCase("add")) {
            t.replace(R.id.testlayout, fragment);
            t.commit();
        }


        rvNumberpad.scrollToPosition(currentPage);
        Objects.requireNonNull(rvNumberpad.getLayoutManager()).smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage);
        rvNumberPadAdapter.setSelectePosition(currentPage);

        if (resultTestSeries2 != null)
            tvQuestionnumber.setText("Question " + (currentPage + 1) + "/" + resultTestSeries2.getData().getTotal_questions());
        //   testseriesBase.setLastanswerPosition(currentPage);

    }


    private void setViewSolutionData() {
//        for (int i = 0; i < resultTestSeries2.getData().getQuestions().size(); i++) {
//            addFragment(i, resultTestSeries2,resultTestSeries2.getData().getQuestions().get(i).getQuestionType(),type);
//        }
        //rootConstraint.setVisibility(View.VISIBLE);
        drawerLayout.setVisibility(View.VISIBLE);

//        pagerAdapter = new TestViewPagerAdapter(getSupportFragmentManager(), ViewSolutionActivity.this, mFragmentList, resultTestSeries2.getData().getQuestions().size());
//        viewPagerSolution.setAdapter(pagerAdapter);

//        changeTextOnNextAndPrevButton();
        rvNumberPadAdapter = new MyRecyclerAdapter(resultTestSeries2.getData().getQuestions(), ViewSolutionActivity.this, R.layout.single_row_testpad_no, ViewSolutionActivity.this, true);
        rvNumberpad.setAdapter(rvNumberPadAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(ViewSolutionActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvNumberpad.setLayoutManager(linearLayoutManager);

        if (type == null) {
            setFragment(ViewSolutionFragment.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType()), "add", "0");
        } else {
            setFragment(LearnFragmentReport.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType(), type), "add", "0");

        }
        changeTextOnNextAndPrevButton();
    }

    @Override
    public void sendOnclickInd(int index) {
        currentPage = index;
        changeTextOnNextAndPrevButton();

        if (type == null) {
            setFragment(ViewSolutionFragment.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType()), "add", "0");
        } else {
            setFragment(LearnFragmentReport.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType(), type), "add", "0");

        }

//        viewPagerSolution.setCurrentItem(index);
    }

    private void addFragment(int i, ResultTestSeries_Report viewSolutionData, String questionType, String type) {
        if (type == null) {
            mFragmentList.add(ViewSolutionFragment.newInstance(i, viewSolutionData, questionType));
        } else {
            mFragmentList.add(LearnFragmentReport.newInstance(i, viewSolutionData, questionType, type));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_next:
                currentPage = currentPage + 1;
                if (type == null) {
                    setFragment(ViewSolutionFragment.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType()), "add", "0");
                } else {
                    setFragment(LearnFragmentReport.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType(), type), "add", "0");

                }
                changeTextOnNextAndPrevButton();
                break;

            case R.id.btn_prev:
                currentPage = currentPage - 1;
                if (type == null) {
                    setFragment(ViewSolutionFragment.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType()), "add", "0");
                } else {
                    setFragment(LearnFragmentReport.newInstance(currentPage, resultTestSeries2, resultTestSeries2.getData().getQuestions().get(currentPage).getQuestionType(), type), "add", "0");

                }
                changeTextOnNextAndPrevButton();
                break;

            case R.id.img_testback:
                onBackPressed();
                break;
            case R.id.challenges_txt:
                challeneg_dialog();
                break;
        }
    }

    private void challeneg_dialog() {
        final Dialog dialog2 = new Dialog(this, R.style.CustomAlertDialog);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.challenge_dialog);
        dialog2.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCancelable(false);
        dialog2.setCanceledOnTouchOutside(false);

        final EditText edit_query = dialog2.findViewById(R.id.edit_query);
        txt_tapAddNote7 = dialog2.findViewById(R.id.txt_tapAddNote);
        layout_fileAttach7 = dialog2.findViewById(R.id.layout_fileAttach);
        layout_fileDetail7 = dialog2.findViewById(R.id.layout_fileDetail);
        img_fileAttached = dialog2.findViewById(R.id.img_fileAttached);
        txt_fileName7 = dialog2.findViewById(R.id.txt_fileName);
        txt_fileSize7 = dialog2.findViewById(R.id.txt_fileSize);
        ImageView img_crossFile = dialog2.findViewById(R.id.img_crossFile);
        Button btn_submit = dialog2.findViewById(R.id.btn_submit);
        ImageView txt_resume = dialog2.findViewById(R.id.txt_resume);

        img_crossFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilePath = "";
                str_qtype = "";
                txt_tapAddNote7.setVisibility(View.VISIBLE);
                layout_fileAttach7.setVisibility(View.VISIBLE);
                layout_fileDetail7.setVisibility(View.GONE);
                txt_fileSize7.setText("");
                txt_fileName7.setText("");
            }
        });


        layout_fileAttach7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "temp_gallery.jpg");
                    Uri photoURI;
                    if (Build.VERSION.SDK_INT >= 24) {
                        photoURI = FileProvider.getUriForFile(ViewSolutionActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider", f);
                    } else {
                        photoURI = Uri.fromFile(f);
                    }
                    str_qtype = "type7";

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    //startActivityForResult(intent, PICK_FILE_REQUEST);

                    /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    //sets the select file to all types of files
                    intent.setType("image/jpg,image/png,image/jpeg,application/pdf,application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    String[] mimetypes = {"image/png", "image/jpeg", "image/jpg", "application/pdf","application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                    str_qtype = "type7";
                    startActivityForResult(intent,
                            PICK_FILE_REQUEST);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (TextUtils.isEmpty(edit_query.getText().toString().trim())) {
                        Toast.makeText(ViewSolutionActivity.this, "Please Enter Query", Toast.LENGTH_SHORT).show();
                        edit_query.requestFocus();
                    } else {
                        String str_query = edit_query.getText().toString();
                        if (Helper.isNetworkConnected(ViewSolutionActivity.this)) {
                            uploadChallenge(dialog2, currentPage, str_query);
                        } else {
                            Toast.makeText(ViewSolutionActivity.this, "No Intent connection !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txt_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    dialog2.dismiss();
                    dialog2.cancel();
                }
            }
        });

        dialog2.show();
        dialog2.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {


                dialog2.dismiss();
                dialog2.cancel();
            }
        });
    }

    private void uploadChallenge(final Dialog dialog2, final int currentPage, String str_query) {
        if (Helper.isNetworkConnected(ViewSolutionActivity.this)) {
            mProgress.show();
            APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);

            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
            masterdatadetailencryptionData.setTest_id(test_segment_id);
            masterdatadetailencryptionData.setCourse_id(SharedPreference.getInstance().getString(Const.ID));
            masterdatadetailencryptionData.setChallenge_text(str_query);
            masterdatadetailencryptionData.setChallenge_image(file_url);
            masterdatadetailencryptionData.setQuestion_id(resultTestSeries2.getData().getQuestions().get(currentPage).getConfigId());
            masterdatadetailencryptionData.setType("1");
            String doseStr = new Gson().toJson(masterdatadetailencryptionData);
            String doseStrScr = AES.encrypt(doseStr);

            Call<String> response = null;
            response = Service.challenge_submission(doseStrScr);

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

                            Gson gson = new Gson();
                            Challenge_Report challenge_report = gson.fromJson(jsonResponse.toString(), Challenge_Report.class);

                            if (jsonResponse.optString("status").equalsIgnoreCase("true")) {
                                mProgress.dismiss();
                                dialog2.dismiss();
                                dialog2.cancel();
                                resultTestSeries2.getData().getQuestion_dump().get(currentPage).setIs_challenge(challenge_report.getData().get(currentPage).getIs_challenge());
                                Toast.makeText(ViewSolutionActivity.this, challenge_report.getMessage(), Toast.LENGTH_SHORT).show();
                                challenges_txt.setText("Challenge Done");
                                challenges_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.challenge_done, 0, 0, 0);
                                challenges_txt.setClickable(false);

                            } else {
                                if (resultTestSeries2.getMessage().equalsIgnoreCase(getResources().getString(R.string.internet_error_message)))
                                    // Helper.showErrorLayoutForNoNav(API.API_GET_USER_LEADERBOARD_RESULT, ViewSolutionActivity.this, 1, 1);
                                    if (resultTestSeries2.getMessage().contains("Something went wrong")) {
                                    }
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
            Toast.makeText(ViewSolutionActivity.this, R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    public void changeTextOnNextAndPrevButton() {
        if (resultTestSeries2 != null) {
            if (currentPage == (resultTestSeries2.getData().getQuestions().size() - 1)) {
                ((TextView) btnNext.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
                btnNext.setBackground(getResources().getDrawable(R.drawable.background_bg_prev));
                btnNext.setEnabled(false);
            } else {
                ((TextView) btnNext.getChildAt(0)).setTextColor(getResources().getColor(R.color.blackApp));
                btnNext.setBackground(getResources().getDrawable(R.drawable.background_bg_next));
                btnNext.setEnabled(true);
            }


            if (frag_type != null) {
                if (frag_type.equalsIgnoreCase(Const.SOLUTIONREPORT)) {
                    if (resultTestSeries2.getData().getQuestion_dump() != null) {
                        if (resultTestSeries2.getData().getQuestion_dump().get(currentPage).getIs_challenge() == 1) {
                            challenges_txt.setClickable(false);
                            challenges_txt.setText("Challenge Done");
                            challenges_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.challenge_done, 0, 0, 0);
                        } else {
                            challenges_txt.setText("Challenge");
                            challenges_txt.setClickable(true);
                            challenges_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.challenge, 0, 0, 0);
                        }
                    }
                }
            }
            if (type != null && type.equalsIgnoreCase("learn")) {
                bookmark_icon_select.setVisibility(View.GONE);
                bookmark_icon_deselect.setVisibility(View.GONE);
            } else {
                if (resultTestSeries2.getData().getQuestions().get(currentPage).getIs_bookmarked().equalsIgnoreCase("1")) {
                    bookmark_icon_select.setVisibility(View.VISIBLE);
                    bookmark_icon_deselect.setVisibility(View.GONE);
                } else {
                    bookmark_icon_select.setVisibility(View.GONE);
                    bookmark_icon_deselect.setVisibility(View.VISIBLE);
                }
            }


            if (currentPage == 0) {
                ((TextView) btnPrev.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
                btnPrev.setBackground(getResources().getDrawable(R.drawable.background_bg_prev));
                btnPrev.setEnabled(false);
            } else {
                ((TextView) btnPrev.getChildAt(0)).setTextColor(getResources().getColor(R.color.blackApp));
                btnPrev.setBackground(getResources().getDrawable(R.drawable.background_bg_next));
                btnPrev.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }

                selectedFilePath = "";
                Uri selectedFileUri = data.getData();
                selectedFilePath = getFullPathFromContentUri(this, selectedFileUri);
                Log.i("", "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    if (str_qtype.equalsIgnoreCase("type7")) {
                        if (selectedFilePath.contains(".png") || selectedFilePath.contains(".jpeg") || selectedFilePath.contains(".jpg")) {
                            txt_tapAddNote7.setVisibility(View.GONE);
                            layout_fileAttach7.setVisibility(View.GONE);
                            layout_fileDetail7.setVisibility(View.VISIBLE);
                            //   txt_fileSize7.setText(getFileSize(selectedFilePath));
                            txt_fileName7.setText(selectedFilePath.substring(selectedFilePath.lastIndexOf("/") + 1));
                            MediaFile mediaFile = new MediaFile();
                            ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();

                            if (selectedFilePath.contains(".png") || selectedFilePath.contains(".jpeg") || selectedFilePath.contains(".jpg")) {
                                Bitmap bitmap = BitmapFactory.decodeFile(selectedFilePath);
                                mediaFile.setFile_type(Const.IMAGE);
                                mediaFile.setImage(bitmap);
                                img_fileAttached.setImageResource(R.drawable.challenge_image_icon);
                                mediaFile.setFile_name(txt_fileName7.getText().toString());
                                s3IU = new s3ImageUploading("", Const.AMAZON_S3_BUCKET_NAME_PROFILE_IMAGES, ViewSolutionActivity.this, ViewSolutionActivity.this, null);
                                mediaFileArrayList.add(mediaFile);
                                s3IU.execute(mediaFileArrayList);
                            }

                        } else {
                            Toast.makeText(this, "Please attach only Image ", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    if (str_qtype.equalsIgnoreCase("type7")) {
                        txt_tapAddNote7.setVisibility(View.VISIBLE);
                        layout_fileAttach7.setVisibility(View.VISIBLE);
                        layout_fileDetail7.setVisibility(View.GONE);
                        txt_fileSize7.setText("");
                        txt_fileName7.setText("");
                    }
                }
            }
        }

    }

    @Override
    public void onS3UploadData(ArrayList<MediaFile> images) {
        if (images != null && images.size() > 0) {
            file_url = images.get(0).getFile();
        } else if (str_qtype.equalsIgnoreCase("type7")) {
            txt_tapAddNote7.setVisibility(View.VISIBLE);
            layout_fileAttach7.setVisibility(View.VISIBLE);
            layout_fileDetail7.setVisibility(View.GONE);
            txt_fileSize7.setText("");
            txt_fileName7.setText("");
        }
    }

    public static String getFullPathFromContentUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                Cursor cursor = null;
                final String column = "_data";
                final String[] projection = {
                        column
                };

                try {
                    cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                            null);
                    if (cursor != null && cursor.moveToFirst()) {
                        final int column_index = cursor.getColumnIndexOrThrow(column);
                        return cursor.getString(column_index);
                    }
                } finally {
                    if (cursor != null)
                        cursor.close();
                }
                return null;
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}