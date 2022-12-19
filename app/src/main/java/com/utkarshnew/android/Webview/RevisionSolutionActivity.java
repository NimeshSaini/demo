package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.testmodule.adapter.MyRecyclerAdapter;
import com.utkarshnew.android.testmodule.adapter.TestViewPagerAdapter;
import com.utkarshnew.android.testmodule.interfaces.NumberPadOnClick;
import com.utkarshnew.android.testmodule.layoutmanager.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RevisionSolutionActivity extends AppCompatActivity implements NumberPadOnClick, View.OnClickListener {

    public TestViewPagerAdapter pagerAdapter;
    public int currentPage;
    DrawerLayout drawerLayout;
    TextView testSeriesName;
    TextView tvQuestionnumber, challenges_txt;
    ConstraintLayout rootConstraint;
    ImageView img_testback;

    FrameLayout btnNext, btnPrev;
    AppCompatImageView bookmark_icon_select, bookmark_icon_deselect;
    private ViewPager viewPagerSolution;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private MyRecyclerAdapter rvNumberPadAdapter;
    private RecyclerView rvNumberpad;
    private TestseriesBase testseriesBase;

    List<Answers> answersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_revision_solution);
        initView();
    }

    private void initView() {


        rvNumberpad = findViewById(R.id.rvnumberpad);
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


        String frag_type = getIntent().getStringExtra("answerlist");

        Type typeOfObjectsList = new TypeToken<ArrayList<Answers>>() {
        }.getType();
        answersList = new Gson().fromJson(frag_type, typeOfObjectsList);


        testseriesBase = (TestseriesBase) Objects.requireNonNull(getIntent().getExtras()).getSerializable("testseriesBase");
        testSeriesName.setText("Solution");
        tvQuestionnumber.setText("Question 1/" + testseriesBase.getData().getQuestions().size());
//        setViewSolutionData();
        getBundleData();
        //getViewSolutionData();
    }

    @SuppressLint("SetTextI18n")
    private void getBundleData()
    {
        if (answersList != null && answersList.size() > 0)
        {
            setViewSolutionData();
        }

        viewPagerSolution.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeTextOnNextAndPrevButton();
            }

            @Override
            public void onPageSelected(final int position) {

                currentPage = position;
                rvNumberpad.scrollToPosition(currentPage);
                rvNumberpad.getLayoutManager().smoothScrollToPosition(rvNumberpad, new RecyclerView.State(), currentPage);
                rvNumberPadAdapter.setSelectePosition(position);

                if (answersList != null && answersList.size() > 0)
                    tvQuestionnumber.setText("Question " + (position + 1) + "/" + answersList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("viewpager", "onPageScrollStateChanged");
            }
        });

    }


    private void setViewSolutionData() {

        for (int i = 0; i < testseriesBase.getData().getQuestions().size(); i++) {
            addFragment(i, testseriesBase, testseriesBase.getData().getQuestions().get(i).getQuestionType(), answersList.get(i));
        }
        //rootConstraint.setVisibility(View.VISIBLE);
        drawerLayout.setVisibility(View.VISIBLE);

        pagerAdapter = new TestViewPagerAdapter(getSupportFragmentManager(), RevisionSolutionActivity.this, mFragmentList, testseriesBase.getData().getQuestions().size());
        viewPagerSolution.setAdapter(pagerAdapter);

        rvNumberPadAdapter = new MyRecyclerAdapter(testseriesBase.getData().getQuestion_response(), RevisionSolutionActivity.this, R.layout.single_row_testpad_no, RevisionSolutionActivity.this, true);
        rvNumberpad.setAdapter(rvNumberPadAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(RevisionSolutionActivity.this, LinearLayoutManager.HORIZONTAL, false);

        rvNumberpad.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void sendOnclickInd(int index) {

        viewPagerSolution.setCurrentItem(index);
    }

    private void addFragment(int i, TestseriesBase testseriesBase, String questionType, Answers answers) {
        mFragmentList.add(ViewSolutuionRevisionFragment.newInstance(i, testseriesBase, questionType, answers));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                viewPagerSolution.setCurrentItem(viewPagerSolution.getCurrentItem() + 1, true);
                changeTextOnNextAndPrevButton();
                break;

            case R.id.btn_prev:
                viewPagerSolution.setCurrentItem(viewPagerSolution.getCurrentItem() - 1, true);
                changeTextOnNextAndPrevButton();
                break;
            case R.id.img_testback:
                onBackPressed();
                break;


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void changeTextOnNextAndPrevButton() {
        if (testseriesBase != null) {
            int currentPosition = viewPagerSolution.getCurrentItem() + 1;
            if (currentPosition == testseriesBase.getData().getQuestions().size()) {
                //  btn_finish.setVisibility(View.VISIBLE);
                ((TextView) btnNext.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));

                btnNext.setBackground(getResources().getDrawable(R.drawable.background_bg_prev));
                btnNext.setEnabled(false);
            } else {
                //  btn_finish.setVisibility(View.GONE);
                ((TextView) btnNext.getChildAt(0)).setTextColor(getResources().getColor(R.color.blackApp));
                btnNext.setEnabled(true);
                btnNext.setBackground(getResources().getDrawable(R.drawable.background_bg_next));
            }


            if (currentPosition == 1) {
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


}