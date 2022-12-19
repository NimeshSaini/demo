package com.utkarshnew.android.courses.Activity;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.utkarshnew.android.courses.adapter.NavigationQuizAdapter;
import com.utkarshnew.android.courses.Fragment.LeaderBoardFragment;
import com.utkarshnew.android.courses.Fragment.Quiz;
import com.utkarshnew.android.courses.Fragment.QuizSolutions;
import com.utkarshnew.android.CreateTest.Fragment.TestCreateResult;
import com.utkarshnew.android.home.Activity.BaseABNoNavActivity;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Webview.RevisionResult;
import com.utkarshnew.android.testmodule.fragment.LeaderBoard;
import com.utkarshnew.android.testmodule.fragment.QuizResultScreenNew;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

public class QuizActivity extends BaseABNoNavActivity {
    public int DEFAULT_SPAN_COUNT = 4;
    public NavigationQuizAdapter navigationQuizAdapter;
    String frag_type = "", status = "", revision_string = "", questionBankList = "";
    QuizModel quiz;
    ResultTestSeries resultTestSeries;
    TestseriesBase testseriesBase;
    String testName = "", first_attempt = "", show_leader = "",response="";


    View.OnClickListener navigatorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
            ((Quiz) mFragment).initalizeNavigationView();
        }
    };
    private String practiceOrQuiz;

    public void counterCallbackListener(int i) {
        ((Quiz) mFragment).controlQuizQuestion(i);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    public void IBTcounterCallbackListener(int i) {
        ((Quiz) mFragment).controlQuizQuestion(i);

    }

    @Override
    protected void initViews() {
        if (getIntent().getExtras() != null) {
            frag_type = getIntent().getExtras().getString(Const.FRAG_TYPE);
            quiz = (QuizModel) getIntent().getExtras().getSerializable(Const.TESTSERIES);
            resultTestSeries = (ResultTestSeries) getIntent().getExtras().getSerializable(Const.RESULT_SCREEN);
            testseriesBase = (TestseriesBase) getIntent().getExtras().getSerializable("testseriesBase");
            revision_string = getIntent().getExtras().getString("revision_string");
         //   response = getIntent().getExtras().getString("response");
            response = MakeMyExam.object;

          //  questionBankList = getIntent().getExtras().getString("questionBankList");
            questionBankList =MakeMyExam.questionbanklist;
            status = getIntent().getExtras().getString(Const.STATUS);
            testName = getIntent().getExtras().getString(Const.NAME);
            show_leader = getIntent().getExtras().getString("show_leader");
            first_attempt = getIntent().getExtras().getString("first_attempt");


            if (getIntent().getExtras().containsKey(Const.PRACTICE)) {
                practiceOrQuiz = getIntent().getExtras().getString(Const.PRACTICE);
            }

        }
    }

    @Override
    protected boolean addBackButton() {
        return true;
    }

    @Override
    protected Fragment getFragment() {
        quizNavigatorIV.setVisibility(View.GONE);
        switch (frag_type) {
            case Const.TESTSERIES:
                setToolbarTitle("Quiz");
                if (quizNavigatorIV.getVisibility() == View.GONE)
                    // TODO: 7/25/2018 changed to remove side drawer icon
//                    quizNavigatorIV.setVisibility(View.VISIBLE);
                    quizNavigatorIV.setVisibility(View.GONE);
                quizNavigatorIV.setOnClickListener(navigatorClickListener);
                return Quiz.newInstance(frag_type, quiz, practiceOrQuiz);

            case Const.LEADERBOARD:
                setToolbarTitle("Leaderboard");
                return LeaderBoardFragment.newInstance(frag_type, practiceOrQuiz);


            case Const.SOLUTION:
                setToolbarTitle("Solutions");

                return QuizSolutions.newInstance(frag_type, status);
            case Const.REVISION_SCRREN:
                setToolbarTitle("Revision Result");
                return RevisionResult.newInstance(revision_string, questionBankList, testseriesBase);

            case Const.CREATE_RESULT:
                setToolbarTitle("My Result");
                return TestCreateResult.newInstance(revision_string, response, testseriesBase);


            case "leader_board":
                setToolbarTitle("Leaderboard");
                return LeaderBoard.newInstance(frag_type, status, testName, first_attempt);

            case Const.RESULT_SCREEN:
                setToolbarTitle("R" + "esult");
                if (!TextUtils.isEmpty(status)) {
                    return QuizResultScreenNew.newInstance(frag_type, status, testName, first_attempt, show_leader);
                } else {
                    return QuizResultScreenNew.newInstance(frag_type, resultTestSeries, quiz, testName, first_attempt);
                }
            case Const.SOLUTIONREPORT:
                setToolbarTitle("Solution Report");
                if (!TextUtils.isEmpty(status)) {
                    return QuizResultScreenNew.newInstance(frag_type, status, testName, first_attempt, show_leader);
                } else {
                    return QuizResultScreenNew.newInstance(frag_type, resultTestSeries, quiz, testName, first_attempt);
                }

        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(CustomContextWrapper.wrap(newBase, ""));
        } else {
            super.attachBaseContext(newBase);
        }
    }
}
