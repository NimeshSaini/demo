package com.utkarshnew.android.Login.Fragment;

import static com.utkarshnew.android.Utils.Helper.getFirebaseToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.Model.User;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.pojo.Userinfo.Data;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class SignUp extends MainFragment {

    Activity activity;
    EditText et_mobile;
    Button msignUpBtn;
    TextView tv_term_and_condition;
    String c_code, mobile = "", deviceId = "";
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        ((SignInActivity) getActivity()).toolbarLinearLayout.setVisibility(View.GONE);
        deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);

        et_mobile = view.findViewById(R.id.et_mobile);
        tv_term_and_condition = view.findViewById(R.id.tv_term_and_condition);
        msignUpBtn = (Button) view.findViewById(R.id.signupBtn);
        user = User.newInstance();
        msignUpBtn.setOnClickListener(new OnSingleClickListener(() -> {
            CheckValidation();
            return null;
        }));

        tv_term_and_condition.setOnClickListener(new OnSingleClickListener(() -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fourFragmentLayout, CommonWebViewFragment.newInstance(API.TERMS_AND_CONDITIONS, false))
                    .addToBackStack(null)
                    .commit();

            ((SignInActivity) getActivity()).setSignInUpLayoutVisibility(false);
            return null;
        }));
    }


    public void CheckValidation() {
        mobile = Helper.GetText(et_mobile);
        c_code = "+91";

        boolean isDataValid = true;

        if (TextUtils.isEmpty(mobile)) {
            isDataValid = Helper.DataNotValid(et_mobile);
        } else if ((Patterns.PHONE.matcher(mobile).matches() != true) || (mobile.length() != 10)) {
            isDataValid = Helper.DataNotValid(et_mobile, 2);
        }

        if (isDataValid) {
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            user.setMobile(mobile);
            user.setC_code(c_code);
            user.setDevice_type(Const.DEVICE_TYPE_ANDROID);
            user.setDevice_tokken(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ? getFirebaseToken() : SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID));
            SharedPreference.getInstance().setLoggedInUser(user);

            Helper.hideKeyboard(activity);

            //Helper.StartShimmer(((SignInActivity) activity).shimmerView,((SignInActivity) activity).examPrepLayerRV);
            NetworkAPICall(API.API_SEND_OTP_VERIFICATION, "", true, false, false);
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_SEND_OTP_VERIFICATION:
                EncryptionData registrationencryptionData = new EncryptionData();
                registrationencryptionData.setMobile(mobile);
                registrationencryptionData.setOtp("");
                registrationencryptionData.setIs_registration("1");
                registrationencryptionData.setResend("0");
                String registrationencryptionDatadoseStr = new Gson().toJson(registrationencryptionData);
                String registrationdoseStrScr = AES.encrypt(registrationencryptionDatadoseStr);
                return service.getOtpVerification(registrationdoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Log.e("SignUp", "SuccessCallBack " + jsonObject.toString());
        switch (apitype) {
            case API.API_SEND_OTP_VERIFICATION:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Data user = SharedPreference.getInstance().getLoggedInUser();
                    Constants.MOBILE_NO = user.getMobile();
                    String otp = "";
                    //Toast.makeText(activity,jsonObject.optString(Const.MESSAGE),Toast.LENGTH_SHORT).show();
                    Helper.GoToOtpVerificationActivity1(activity, otp, 7, Const.OTPVERIFICATION, false);
                } else {
                    //Helper.StopShimmer(((SignInActivity) activity).shimmerView,((SignInActivity) activity).examPrepLayerRV);
                    RetrofitResponse.GetApiData(activity, jsonObject.optString(Const.AUTH_CODE), jsonObject.optString(Const.MESSAGE), false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        //Helper.StopShimmer(((SignInActivity) activity).shimmerView,((SignInActivity) activity).examPrepLayerRV);
        Toast.makeText(activity, jsonstring, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Helper.StopShimmer(((SignInActivity) activity).shimmerView,((SignInActivity) activity).examPrepLayerRV);
    }
}
