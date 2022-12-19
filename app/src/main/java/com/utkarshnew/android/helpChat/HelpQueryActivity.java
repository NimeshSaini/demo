package com.utkarshnew.android.helpChat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.helpChat.adapter.HelpQueryRecyclerAdapter;
import com.utkarshnew.android.helpChat.helper.OnQueryItemListener;
import com.utkarshnew.android.helpChat.model.HelpSupportChatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;

public class HelpQueryActivity extends AppCompatActivity implements View.OnClickListener, OnQueryItemListener, NetworkCall.MyNetworkCallBack {
    TextView queryTV;
    HelpQueryRecyclerAdapter chatAdapter;
    String message_txt = "";
    RecyclerView recyclerView;
    public String quiry_id = "";
    FloatingActionButton floatingActionButton;
    ArrayList<HelpSupportChatModel.DataBean> chat_list = new ArrayList<>();
    NetworkCall networkCall;

    boolean isCourse = false;
    String courseId = "0";
    CourseDetail singleStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.help_query);
        networkCall = new NetworkCall(this, this);

        if (getIntent().getExtras() != null) {
            isCourse = getIntent().getExtras().getBoolean("isCourse");
            singleStudy = (CourseDetail) Objects.requireNonNull(getIntent().getExtras()).getSerializable("courseDetail");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.myProgress_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        initView();
    }

    private void checkMyQuiry() {
        networkCall.NetworkAPICall(API.GET_MY_QUIRES, "", true, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMyQuiry();
    }

    private void initView() {
        queryTV = findViewById(R.id.queryTV);
        recyclerView = findViewById(R.id.chat_list);
        floatingActionButton = findViewById(R.id.floating_button);
        floatingActionButton.setOnClickListener(this);

        if (isCourse) {
            if (singleStudy != null && singleStudy.getData() != null && singleStudy.getData().getCourseDetail() != null && !TextUtils.isEmpty(singleStudy.getData().getCourseDetail().getTitle())) {
                courseId = singleStudy.getData().getCourseDetail().getId();
            } else {
                courseId = "0";
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_button:
                Intent helpSupportIntent = new Intent(HelpQueryActivity.this, HelpSupportActivity.class);
                helpSupportIntent.putExtra("isCourse", isCourse);
                helpSupportIntent.putExtra("courseDetail", singleStudy);
                startActivity(helpSupportIntent);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Helper.backButtonClick(HelpQueryActivity.this);
        return true;
    }

    @Override
    public void onQueryItemClick(HelpSupportChatModel.DataBean myQuery) {
        Intent helpSupportIntent = new Intent(this, HelpChatActivity.class);
        helpSupportIntent.putExtra("querySelect", myQuery);
        helpSupportIntent.putExtra("isCourse", isCourse);
        helpSupportIntent.putExtra("courseDetail", singleStudy);
        startActivity(helpSupportIntent);
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.GET_MY_QUIRES:
                EncryptionData masterdataencryptionData1 = new EncryptionData();
                masterdataencryptionData1.setPage("1");
                masterdataencryptionData1.setCourse_id(courseId);
                String masterdataencryptionDatadoseStr1 = new Gson().toJson(masterdataencryptionData1);
                String masterdatadoseStrScr1 = AES.encrypt(masterdataencryptionDatadoseStr1);
                return service.getMyHelpQuires(masterdatadoseStrScr1);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.GET_MY_QUIRES:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    chat_list = new ArrayList<>();
                    Gson gson = new Gson();
                    JSONArray data = jsonstring.optJSONArray(Const.DATA);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject dataObj = data.optJSONObject(i);
                        HelpSupportChatModel.DataBean questionBank = gson.fromJson(dataObj.toString(), HelpSupportChatModel.DataBean.class);
                        chat_list.add(questionBank);
                    }

                    chatAdapter = new HelpQueryRecyclerAdapter(HelpQueryActivity.this, chat_list);
                    recyclerView.setAdapter(chatAdapter);
                } else {
                    RetrofitResponse.GetApiData(HelpQueryActivity.this, jsonstring.optString(Const.AUTH_CODE), jsonstring.optString(Const.MESSAGE), false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }
}