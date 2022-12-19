package com.utkarshnew.android.Login.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.SharedPreference;

public class PermissionSettingActivity extends AppCompatActivity implements View.OnClickListener {
    Activity context;
    private TextView settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        context = PermissionSettingActivity.this;
        setContentView(R.layout.permission_setting_layout);

        init();
    }

    private void init() {
        settingsBtn = findViewById(R.id.settingsbtn);
        settingsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.settingsbtn:
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkStoragePerm()) {
            if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
            {
                Intent intent = new Intent(PermissionSettingActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Const.TYPE, Const.SIGNIN);
                intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(PermissionSettingActivity.this, LoginWithOtp.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Const.TYPE, Const.SIGNIN);
                intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                startActivity(intent);
                finish();
            }
        }
    }

    private boolean checkStoragePerm() {
        return ContextCompat.checkSelfPermission(PermissionSettingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(PermissionSettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
}

