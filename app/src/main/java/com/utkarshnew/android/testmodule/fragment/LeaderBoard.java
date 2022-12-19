package com.utkarshnew.android.testmodule.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.Courses.quiz.QuizModel;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.NonScrollRecyclerView;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.testmodule.adapter.AnswerListAdapter;
import com.utkarshnew.android.testmodule.adapter.RankAdapter;
import com.utkarshnew.android.testmodule.model.QuestionDump;
import com.utkarshnew.android.testmodule.model.ResultTestSeries_Report;
import com.utkarshnew.android.testmodule.model.TestSeriesResultData;
import com.utkarshnew.android.testmodule.model.TopTenList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderBoard extends MainFragment {

    public Data user;
    Activity activity;
    String frag_type = "", status = "";
    QuizModel quiz;
    String testSeriesName = "";
    String first_attempt = "";
    ResultTestSeries_Report resultTestSeries2;
    int lang;  // 2 for Hindi , 1 for English
    LinearLayout videosolution, videosolution2;
    Progress progress;
    RelativeLayout viewSubjectWiseDetailRL;
    List<TopTenList> topTenLists = new ArrayList<>();
    TextView leaderboard_text;
    NonScrollRecyclerView recyclerviewrank;
    LinearLayout resultDeclaredLL;
    RankAdapter adapter;
    AnswerListAdapter answerListAdapter;
    NonScrollRecyclerView answerListRV;
    String testseriesid;
    // TestSeriesDbHelper testSeriesDbHelper;
    ArrayList<String> arrayList;
    ArrayList<QuestionDump> newQuestionDump;
    ArrayList<TestSeriesResultData> testSeriesResultDataArrayList;
    Progress mprogress;
    RelativeLayout rankRL;
    View viewpercentile, viewaccuracy;
    LinearLayout accuracy_LL;
    LinearLayout scoreLL;
    LinearLayout percentileLL;

    NestedScrollView mainLL;
    RelativeLayout noDataFound;
    Button backBtn;


    public static Fragment newInstance(String frag_type, String status, String testSeriesName, String first_attempt) {
        LeaderBoard quizFragment = new LeaderBoard();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.FRAG_TYPE, frag_type);
        bundle.putSerializable(Const.STATUS, status);
        bundle.putString(Const.NAME, testSeriesName);
        bundle.putString("first_attempt", first_attempt);
        quizFragment.setArguments(bundle);
        return quizFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.leader_board, container, false);
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = SharedPreference.getInstance().getLoggedInUser();
        mprogress = new Progress(activity);
        mprogress.setCancelable(false);
        initViews(view);
        arrayList = new ArrayList<>();
        testSeriesResultDataArrayList = new ArrayList<>();
        newQuestionDump = new ArrayList<>();
        netoworkCallForQuizResult2();

    }


    public void netoworkCallForQuizResult2() {
        if (Helper.isNetworkConnected(getActivity())) {
            mprogress.show();
            APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setTest_id(status);
            masterdatadetailencryptionData.setCourse_id(SharedPreference.getInstance().getString(Const.ID));
            String doseStr = new Gson().toJson(masterdatadetailencryptionData);
            String doseStrScr = AES.encrypt(doseStr);

            Call<String> response;
            response = Service.get_test_leaderboard(doseStrScr);

            assert response != null;
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    mprogress.dismiss();
                    if (response.body() != null) {
                        JSONObject jsonResponse = null;
                        String jsonString;
                        try {
                            jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                            jsonResponse = new JSONObject(jsonString);
                        } catch (Exception ignored) {
                        }
                        if (jsonResponse == null) {
                            Helper.showToastSecurity(activity);
                            return;
                        }
                        MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time")))*1000);

                        resultTestSeries2 = new Gson().fromJson(jsonResponse.toString(), ResultTestSeries_Report.class);
                        if (resultTestSeries2 == null) {
                            Helper.showToastSecurity(activity);
                            return;
                        }
                        try {
                            if (resultTestSeries2.getStatus().equalsIgnoreCase("true")) {
                                topTenLists = resultTestSeries2.getData().getTop_ten_list();
                                if (topTenLists.size() > 0) {
                                    mainLL.setVisibility(View.VISIBLE);
                                    noDataFound.setVisibility(View.GONE);
                                    adapter = new RankAdapter(activity, topTenLists);
                                    recyclerviewrank.setAdapter(adapter);
                                } else {
                                    mainLL.setVisibility(View.GONE);
                                    noDataFound.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (!(topTenLists.size() > 0)) {
                                    mainLL.setVisibility(View.GONE);
                                    noDataFound.setVisibility(View.VISIBLE);
                                }
                                mprogress.dismiss();
                                RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mprogress.dismiss();
                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
        }

    }

    private void initViews(View view) {
        recyclerviewrank = view.findViewById(R.id.leaderBoardRV);
        leaderboard_text = view.findViewById(R.id.leaderboard_text);
        resultDeclaredLL = view.findViewById(R.id.resultDeclaredLL);
        mainLL = view.findViewById(R.id.mainLL);
        noDataFound = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setVisibility(View.GONE);

        recyclerviewrank.setLayoutManager(new LinearLayoutManager(activity));
        recyclerviewrank.setHasFixedSize(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            quiz = (QuizModel) getArguments().getSerializable(Const.RESULT_SCREEN);
            status = getArguments().getString(Const.STATUS);
            testSeriesName = getArguments().getString(Const.NAME);
            first_attempt = getArguments().getString("first_attempt");
        }
        activity = getActivity();
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
}