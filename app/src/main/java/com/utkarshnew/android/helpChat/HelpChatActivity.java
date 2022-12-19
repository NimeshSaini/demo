package com.utkarshnew.android.helpChat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

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
import com.utkarshnew.android.helpChat.adapter.ChatAdapter;
import com.utkarshnew.android.helpChat.model.ChatQuery;
import com.utkarshnew.android.helpChat.model.HelpSupportChatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;

public class HelpChatActivity extends AppCompatActivity implements View.OnClickListener, NetworkCall.MyNetworkCallBack {
    ImageView send_message;
    LinearLayout sending_layout;
    EditText message;
    TextView queryTV;
    ChatAdapter chatAdapter;
    String message_txt = "";
    RecyclerView recyclerView;
    public String quiry_id = "";
    ArrayList<ChatQuery> chat_list;
    HelpSupportChatModel.DataBean chatData;
    NetworkCall networkCall;

    boolean isCourse = false;
    String courseId = "0";
    CourseDetail singleStudy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.help_chat);
        networkCall = new NetworkCall(this, this);

        if (getIntent().getExtras() != null) {
            chatData = (HelpSupportChatModel.DataBean) getIntent().getSerializableExtra("querySelect");
            isCourse = getIntent().getExtras().getBoolean("isCourse");
            singleStudy = (CourseDetail) Objects.requireNonNull(getIntent().getExtras()).getSerializable("courseDetail");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.myProgress_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        initView();
        checkQuiryDecision();
    }

    private void initView() {
        sending_layout = findViewById(R.id.sending_layout);
        queryTV = findViewById(R.id.queryTV);
        send_message = findViewById(R.id.send_message);
        message = findViewById(R.id.help_chat_txt);
        recyclerView = findViewById(R.id.chat_list);
        send_message.setOnClickListener(this);

        if (isCourse) {
            if (singleStudy != null && singleStudy.getData() != null && singleStudy.getData().getCourseDetail() != null && !TextUtils.isEmpty(singleStudy.getData().getCourseDetail().getTitle())) {
                courseId = singleStudy.getData().getCourseDetail().getId();
            } else {
                courseId = "0";
            }
        }

        if (chatData.getClose_date().equalsIgnoreCase("0")) {
            recyclerView.setPadding(0, 0, 0, Helper.getValueInDP(HelpChatActivity.this, 56));
            sending_layout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setPadding(0, 0, 0, 0);
            sending_layout.setVisibility(View.GONE);
        }

        String dStart;
        if (TextUtils.isEmpty(chatData.getTime())) {
            dStart = "N/A";
        } else {
            try {
                long timestamp_start = Long.parseLong(chatData.getTime()) * 1000;
                dStart = chatAdapter.getdate(String.valueOf(timestamp_start));
        /*        Calendar cal_start = Calendar.getInstance(Locale.ENGLISH);
                cal_start.setTimeInMillis(timestamp_start);
                Date date_start=cal_start.getTime();
                dStart= java.text.DateFormat.getDateTimeInstance().format(date_start);*/
            } catch (Exception e) {
                dStart = "N/A";
            }
        }
        queryTV.setText(chatData.getDescription() + "\n" + dStart);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message:
                message_txt = message.getText().toString();
                if (!message_txt.isEmpty()) {
                    sendQuirey();
                } else Toast.makeText(this, "Please enter some Text", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sendQuirey() {
        networkCall.NetworkAPICall(API.GET_SUBMIT_QUIRES_REPLIES, "", true, false);
    }

    private void checkQuiryDecision() {
        networkCall.NetworkAPICall(API.GET_QUIRES_REPLIES, "", true, false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Helper.backButtonClick(HelpChatActivity.this);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.GET_QUIRES_REPLIES:
                EncryptionData encryptionData1 = new EncryptionData();
                encryptionData1.setQuery_id(chatData.getId());
                encryptionData1.setCourse_id(courseId);
                String doseStr = new Gson().toJson(encryptionData1);
                String doseStrScr = AES.encrypt(doseStr);
                return service.getQuiresReply(doseStrScr);

            case API.GET_SUBMIT_QUIRES_REPLIES:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setQuery_id(chatData.getId());
                encryptionData.setText(message_txt);
                encryptionData.setCourse_id(courseId);
                String doseStr1 = new Gson().toJson(encryptionData);
                String doseStrScr1 = AES.encrypt(doseStr1);
                return service.sendSupportQuiry(doseStrScr1);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.GET_QUIRES_REPLIES:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    chat_list = new ArrayList<>();
                    Gson gson = new Gson();
                    JSONArray data = jsonstring.optJSONArray(Const.DATA);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject dataObj = data.optJSONObject(i);
                        ChatQuery chatQuery = gson.fromJson(dataObj.toString(), ChatQuery.class);
                        chat_list.add(chatQuery);
                    }

                    chatAdapter = new ChatAdapter(chat_list);
                    recyclerView.setAdapter(chatAdapter);
                } else {
                    if (jsonstring.has(Const.AUTH_CODE)) {
                        RetrofitResponse.GetApiData(HelpChatActivity.this, jsonstring.optString(Const.AUTH_CODE), jsonstring.optString(Const.MESSAGE), false);
                    }
                }
                break;

            case API.GET_SUBMIT_QUIRES_REPLIES:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    message.setText("");
                    Helper.closeKeyboard(HelpChatActivity.this);
                    checkQuiryDecision();
                } else {
                    if (jsonstring.has(Const.AUTH_CODE)) {
                        RetrofitResponse.GetApiData(HelpChatActivity.this, jsonstring.optString(Const.AUTH_CODE), jsonstring.optString(Const.MESSAGE), false);
                    }
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }
}