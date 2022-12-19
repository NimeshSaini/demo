package com.utkarshnew.android.Login.Activity;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.utkarshnew.android.Login.Fragment.CreatePassword;
import com.utkarshnew.android.home.Activity.BaseABNoNavActivity;
import com.utkarshnew.android.Login.Fragment.ChooseLanguageIBT;
import com.utkarshnew.android.Login.Fragment.changepassword;
import com.utkarshnew.android.Login.Fragment.forgetpassword;
import com.utkarshnew.android.Login.Fragment.otpverification;
import com.utkarshnew.android.Login.Fragment.SignupForm;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Progress;

/**
 * Created by Cbc-03 on 06/08/17.
 */

public class LoginCatActivity extends BaseABNoNavActivity {

    String otp, title;
    String Frag_type = "", subcat = "";
    boolean resetpass = false;
    boolean isChangePass = false;
    boolean isphone = false;
    int type;
    public Progress mprogress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initViews() {
        mprogress = new Progress(this);
        mprogress.setCancelable(false);
        if (getIntent().getExtras() != null) {
            Frag_type = getIntent().getExtras().getString(Const.FRAG_TYPE);
            subcat = getIntent().getExtras().getString(Const.SUB_CAT);
            otp = getIntent().getExtras().getString(Const.OTP);
            type = getIntent().getExtras().getInt(Const.TYPE);
            title = getIntent().getExtras().getString(Const.TITLE);
            resetpass = getIntent().getExtras().getBoolean(Const.RESET_PASS);
            isChangePass = getIntent().getExtras().getBoolean(Const.IS_CHANGE_PASS);
            isphone = getIntent().getExtras().getBoolean("isphone");
        }
    }

    @Override
    protected boolean addBackButton() {
        return true;
    }

    @Override
    protected Fragment getFragment() {
        switch (Frag_type) {
            case Const.CHANGEPASSWORD:
                return changepassword.newInstance(otp, resetpass, isChangePass, isphone);
            case Const.FORGETPASSWORD:
                return forgetpassword.newInstance(resetpass, isChangePass);
            case Const.LOGINWITHOTP:
            case Const.OTPVERIFICATION:
                return otpverification.newInstance(otp, type, resetpass, isChangePass, isphone);
            case Const.REGISTRATION:
                return SignupForm.newInstance(otp);
            case Const.CHANGELANGUAGE:
                toolbar.setVisibility(View.GONE);
                return ChooseLanguageIBT.newInstance(type);
            case Const.CREATEPASSWORD:
                return CreatePassword.newInstance(resetpass);

        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
