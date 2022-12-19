package com.utkarshnew.android.helpChat;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;

public class HelpSupportActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {
    Activity context;
    AppCompatSpinner feedtype;
    RelativeLayout courseRL;
    TextView courseIdTxt;
    EditText comment_et, title;
    Button submitBtn;
    String[] type = {"Select Type", "Suggestion", "Complaint", "Query", "Other"};
    ArrayAdapter<String> arrayAdapter;
    String typestr;
    String title_txt;
    NetworkCall networkCall;
    boolean isCourse = false;
    String courseId = "0";
    String category, comment, titleStr;
    CourseDetail singleStudy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        context = HelpSupportActivity.this;
        setContentView(R.layout.activity_help_support);
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

    private void initView() {
        courseRL = (RelativeLayout) findViewById(R.id.courseRL);
        courseIdTxt = (TextView) findViewById(R.id.courseIdTxt);
        feedtype = (AppCompatSpinner) findViewById(R.id.feedtype);
        title = (EditText) findViewById(R.id.title);
        comment_et = (EditText) findViewById(R.id.comment_et);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        if (isCourse) {
            courseRL.setVisibility(View.VISIBLE);
            if (singleStudy != null && singleStudy.getData() != null && singleStudy.getData().getCourseDetail() != null && !TextUtils.isEmpty(singleStudy.getData().getCourseDetail().getTitle())) {
                courseIdTxt.setText(singleStudy.getData().getCourseDetail().getTitle());
                courseId = singleStudy.getData().getCourseDetail().getId();
            } else {
                courseIdTxt.setText("N/A");
                courseId = "0";
            }
        }

        arrayAdapter = new ArrayAdapter<String>(context, R.layout.item_view, type);
        feedtype.setAdapter(arrayAdapter);
        //feedtype.setSelection(0);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typepos = feedtype.getSelectedItemPosition();
                comment = comment_et.getText().toString().trim();

                typestr = type[typepos];
                //String type=typeTsp.getText().toString().trim();
                if (typestr.equals("Select Type")) {
                    Toast.makeText(context, "Please select Type.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (comment.equals("")) {
                    Toast.makeText(context, "Please fill text Box.", Toast.LENGTH_SHORT).show();
                    return;
                }

                title_txt = title.getText().toString();
                if (!comment.isEmpty() && !title_txt.isEmpty() && !comment.isEmpty()) {
                    networkcallForSubmitquery(title_txt, type[feedtype.getSelectedItemPosition()]);
                } else {
                    Toast.makeText(HelpSupportActivity.this, "All Field is Mandatory.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.GET_MY_QUIRES:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setCategory(category);
                encryptionData.setTitle(comment);
                encryptionData.setDescription(titleStr);
                encryptionData.setCourse_id(courseId);
                String doseStr1 = new Gson().toJson(encryptionData);
                String doseStrScr1 = AES.encrypt(doseStr1);
                return service.submitHelpQuires(doseStrScr1);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.GET_MY_QUIRES:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    feedtype.setSelection(0);
                    title.setText("");
                    comment_et.setText("");
                    Toast.makeText(HelpSupportActivity.this, jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    RetrofitResponse.GetApiData(HelpSupportActivity.this, jsonstring.optString(Const.AUTH_CODE), jsonstring.optString(Const.MESSAGE), false);
                }
                break;
        }
    }


    public void networkcallForSubmitquery(String titles, String quiry_type) {
        category = quiry_type;
        titleStr = titles;
        networkCall.NetworkAPICall(API.GET_MY_QUIRES, "", true, false);
    }


    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
    }


    @Override
    public boolean onSupportNavigateUp() {
        Helper.backButtonClick(HelpSupportActivity.this);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

