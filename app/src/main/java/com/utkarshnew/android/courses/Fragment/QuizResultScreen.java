package com.utkarshnew.android.courses.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class QuizResultScreen extends MainFragment implements View.OnClickListener {
    Activity activity;
    String frag_type = "", status = "";
    QuizModel quiz;
    ResultTestSeries resultTestSeries;
    ImageView rankreloadIV, leaderboardIV;
    TextView name, score, rank, timeSpent, titleTestSeries, correctTV, wrongTV, unattemptedTV, rewardsValueTV;
    LinearLayout shareRank, questionDistrLL, rankLL;
    NestedScrollView mainLL;
//    private BarChart mChart;

    public QuizResultScreen() {
        // Required empty public constructor
    }

    public static QuizResultScreen newInstance(String frag_type, ResultTestSeries resultTestSeries, QuizModel quiz) {
        QuizResultScreen quizFragment = new QuizResultScreen();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TESTSERIES, resultTestSeries);
        bundle.putSerializable(Const.FRAG_TYPE, frag_type);
        bundle.putSerializable(Const.RESULT_SCREEN, quiz);
        quizFragment.setArguments(bundle);
        return quizFragment;
    }

    public static Fragment newInstance(String frag_type, String status) {
        QuizResultScreen quizFragment = new QuizResultScreen();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.FRAG_TYPE, frag_type);
        bundle.putSerializable(Const.STATUS, status);
        quizFragment.setArguments(bundle);
        return quizFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        if (!TextUtils.isEmpty(status)) {
            NetworkAPICall(API.API_GET_USER_LEADERBOARD_RESULT, "", true, false, false);
        } else {
            generateResultData();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_result_screen, container, false);
    }

    private void generateResultData() {
        if (mainLL.getVisibility() == View.GONE) mainLL.setVisibility(View.VISIBLE);
        status = resultTestSeries.getId();
        titleTestSeries.setText(TextUtils.isEmpty(status) ? quiz.getBasic_info().getTest_series_name() : resultTestSeries.getTest_series_name());
        score.setText((resultTestSeries.getMarks() + "/" + resultTestSeries.getTest_series_marks()));
        rank.setText((resultTestSeries.getUser_rank() + "/" + resultTestSeries.getTotal_user_attempt()));

        timeSpent.setText((String.format("%sm %ss", (Integer.parseInt(resultTestSeries.getTime_spent()) / 60), (Integer.parseInt(resultTestSeries.getTime_spent()) % 60))
                + "/" + String.format("%sm", resultTestSeries.getTotal_test_series_time())));
        name.setText(SharedPreference.getInstance().getLoggedInUser().getName());

        correctTV.setText(String.format("%s Correct", resultTestSeries.getCorrect_count()));
        wrongTV.setText(String.format("%s Wrong", resultTestSeries.getIncorrect_count()));
        unattemptedTV.setText(String.format("%s Unattempted", resultTestSeries.getNon_attempt()));
        rewardsValueTV.setText(String.format("%s Points", resultTestSeries.getReward_points()));

        // to hide rak CardView
        if (resultTestSeries.getSkip_rank().equals("1")) {
            rankLL.setVisibility(View.GONE);
        } else {
            rankLL.setVisibility(View.VISIBLE);
        }

        /*// To manage Bar Data
        if (!resultTestSeries.getTest_type().equals("1")) {
            if (questionDistrLL.getVisibility() == View.GONE)
                questionDistrLL.setVisibility(View.VISIBLE);
            generateBarData();
        } else {
            questionDistrLL.setVisibility(View.GONE);
        }*/
    }

//    private void generateBarData() {
//        // create BarEntry for Bar Group 1
//        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
//        bargroup1.add(new BarEntry(Float.parseFloat(resultTestSeries.getCorrect_count()), 0));
//        bargroup1.add(new BarEntry(Float.parseFloat(resultTestSeries.getIncorrect_count()), 1));
//        bargroup1.add(new BarEntry(Float.parseFloat(resultTestSeries.getNon_attempt()), 2));
//
//        // creating dataset for Bar Group1
//        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Bar Group 1");
//
//        //barDataSet1.setColor(Color.rgb(0, 155, 0));
//        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
//        dataSets.add(barDataSet1);
//
//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add(resultTestSeries.getCorrect_count() + " Correct");
//        labels.add(resultTestSeries.getIncorrect_count() + " Wrong");
//        labels.add(resultTestSeries.getNon_attempt() + " Unattempted");
//
//        BarData data = new BarData(labels, dataSets);
//        data.setDrawValues(false);
//
//        mChart.setData(data);
//        mChart.invalidate();
//    }

    private void initViews(View view) {
        name = (TextView) view.findViewById(R.id.nameValueTV);
        score = (TextView) view.findViewById(R.id.scoreValueTV);
        rank = (TextView) view.findViewById(R.id.rankValueTV);
        timeSpent = (TextView) view.findViewById(R.id.timeSpentValueTV);
        titleTestSeries = (TextView) view.findViewById(R.id.testSeriesnameTV);
        shareRank = (LinearLayout) view.findViewById(R.id.shareRankLL);
        rankLL = (LinearLayout) view.findViewById(R.id.rankLL);
//        columnChartView = (ColumnChartView) view.findViewById(R.id.barChart);
        correctTV = (TextView) view.findViewById(R.id.correctTv);
        wrongTV = (TextView) view.findViewById(R.id.wrongTv);
        unattemptedTV = (TextView) view.findViewById(R.id.unattemptedTv);
//        mChart = (BarChart) view.findViewById(R.id.chart1);
        rewardsValueTV = (TextView) view.findViewById(R.id.rewardsValueTV);
        rankreloadIV = view.findViewById(R.id.rankreloadIV);
        questionDistrLL = view.findViewById(R.id.questionDistrLL);
        mainLL = view.findViewById(R.id.mainLL);
        leaderboardIV = view.findViewById(R.id.leaderboardIV);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
//        mChart.setMaxVisibleValueCount(60);
//
//        mChart.setDescription("");
//
//        // scaling can now only be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//        mChart.setDoubleTapToZoomEnabled(false);
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawGridBackground(false);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        mChart.getAxisLeft().setDrawGridLines(false);
//        mChart.getAxisLeft().setDrawLabels(false);
//        mChart.getAxisRight().setDrawLabels(false);
//        mChart.getXAxis().setDrawLabels(false);
//        // add a nice and smooth animation
//        mChart.animateY(2500);
//
//        mChart.getLegend().setEnabled(false);

        rankreloadIV.setOnClickListener(this);
        leaderboardIV.setOnClickListener(this);
        shareRank.setOnClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            resultTestSeries = (ResultTestSeries) getArguments().getSerializable(Const.TESTSERIES);
            quiz = (QuizModel) getArguments().getSerializable(Const.RESULT_SCREEN);
            status = getArguments().getString(Const.STATUS);
        }
        activity = getActivity();
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_USER_LEADERBOARD_RESULT:
                return service.getUserLeaderBoardResult(SharedPreference.getInstance().getLoggedInUser().getId(),
                        status);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        JSONArray dataArray;
        switch (apitype) {
            case API.API_GET_USER_LEADERBOARD_RESULT:
                if (jsonobject.optBoolean(Const.STATUS)) {
                    JSONObject data = jsonobject.getJSONObject(Const.DATA);
                    resultTestSeries = gson.fromJson(data.toString(), ResultTestSeries.class);
                    generateResultData();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rankreloadIV:
                NetworkAPICall(API.API_GET_USER_LEADERBOARD_RESULT, "", true, false, false);
                break;
            case R.id.leaderboardIV:
                Intent leaderBoard = new Intent(activity, QuizActivity.class);
                leaderBoard.putExtra(Const.FRAG_TYPE, Const.LEADERBOARD);
                leaderBoard.putExtra(Const.RESULT_SCREEN, resultTestSeries);
                leaderBoard.putExtra(Const.PRACTICE, "");
                activity.startActivity(leaderBoard);
                break;
            case R.id.shareRankLL:
//                Intent viewSolution = new Intent(activity, QuizActivity.class);
//                viewSolution.putExtra(Const.FRAG_TYPE, Const.SOLUTION);
//                viewSolution.putExtra(Const.STATUS, resultTestSeries.getId());
//                viewSolution.putExtra(Const.PRACTICE, "");
//                activity.startActivity(viewSolution);

                Intent quizWebView = new Intent(activity, QuizWebView.class);
                quizWebView.putExtra(Const.RESULT_SCREEN, resultTestSeries);
                quizWebView.putExtra(Const.STATUS, true);
                activity.startActivity(quizWebView);
                break;
        }
    }
}
