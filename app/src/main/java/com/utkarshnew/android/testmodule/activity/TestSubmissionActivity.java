package com.utkarshnew.android.testmodule.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSubmissionActivity extends AppCompatActivity {
    TextView result, test_name, test_submit;
    ImageView backimag;
    String result_date;
    Button backBtn;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.test_submittion_activity);
        result = findViewById(R.id.result);
        backimag = findViewById(R.id.backimag);
        backBtn = findViewById(R.id.backBtn);
        test_name = findViewById(R.id.test_name);
        result_date = getIntent().getExtras().getString("result_date");
        test_name.setText(getIntent().getExtras().getString("test_name"));
        backimag.setOnClickListener(v -> finish());
        backBtn.setOnClickListener(v -> finish());
        if (result_date != null && !result_date.equalsIgnoreCase("") && !result_date.equalsIgnoreCase("0") && !result_date.equalsIgnoreCase("1")) {
            result.setText("Your Result will be declared on \n " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(result_date) * 1000)));
        } else {
            result.setText("Your Result is Getting Ready Please wait");

        }
    }
}