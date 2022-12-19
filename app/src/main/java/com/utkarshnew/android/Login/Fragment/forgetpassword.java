package com.utkarshnew.android.Login.Fragment;

import android.app.Activity;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Constants;
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
import com.google.gson.Gson;
import com.utkarshnew.android.pojo.Userinfo.Data;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class forgetpassword extends MainFragment {

    EditText mphonenumberET;
    Activity activity;
    String phone, c_code;
    private Button loginBtn;
    private boolean isphone = false;
    private boolean resetPass = false;
    private boolean isChangePass = false;
    Data user;
    ImageView iv_back;
    TextView title;

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public forgetpassword() {
        // Required empty public constructor
    }

    public static forgetpassword newInstance(boolean resetpass, boolean isChangePass) {
        forgetpassword fragment = new forgetpassword();
        Bundle args = new Bundle();
        args.putBoolean(Const.RESET_PASS, resetpass);
        args.putBoolean(Const.IS_CHANGE_PASS, isChangePass);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            resetPass = getArguments().getBoolean(Const.RESET_PASS);
            isChangePass = getArguments().getBoolean(Const.IS_CHANGE_PASS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ibt_fragment_forget_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mphonenumberET = (EditText) view.findViewById(R.id.mobileTV);
        loginBtn = (Button) view.findViewById(R.id.loginBtn);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        title = (TextView) view.findViewById(R.id.title);

        if (isChangePass) {
            mphonenumberET.setEnabled(false);
            mphonenumberET.setFocusable(false);
            mphonenumberET.setText(SharedPreference.getInstance().getLoggedInUser().getMobile());
            title.setText(getString(R.string.change_password));
        } else {
            title.setText(getString(R.string.forget_password));
        }

        iv_back.setOnClickListener(new OnSingleClickListener(() -> {
            activity.finish();
            return null;
        }));

        loginBtn.setOnClickListener(new OnSingleClickListener(() -> {
            CheckValidationNew();
            return null;
        }));
    }

    private void CheckValidationNew() {
        phone = Helper.GetText(mphonenumberET);
        c_code = "+91";

        boolean isDataValid = true;

        if (TextUtils.isDigitsOnly(phone)) {
            isphone = true;
            if (TextUtils.isEmpty(phone))
                isDataValid = Helper.DataNotValid(mphonenumberET);
            else if ((Patterns.PHONE.matcher(phone).matches() != true) || (phone.length() != 10)) {
                isDataValid = Helper.DataNotValid(mphonenumberET, 2);
            }
        } else { // for Email
            isphone = false;
            if (TextUtils.isEmpty(phone))
                isDataValid = Helper.DataNotValid(mphonenumberET);
            else if ((Patterns.PHONE.matcher(phone).matches() != true) || (phone.length() != 10)) {
                isDataValid = Helper.DataNotValid(mphonenumberET, 2);
            }
            // Patterns.EMAIL_ADDRESS.matcher(phone).matches() != true
            else if ((!Patterns.EMAIL_ADDRESS.matcher(phone).matches()))
                isDataValid = Helper.DataNotValid(mphonenumberET);
        }

        if (isChangePass) {
            if (isphone) {
                if (SharedPreference.getInstance() != null && SharedPreference.getInstance().getLoggedInUser() != null && !TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getMobile())) {
                    if (!SharedPreference.getInstance().getLoggedInUser().getMobile().equalsIgnoreCase(phone)) {
                        isDataValid = false;
                        Toast.makeText(activity, "Mobile number not match.", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (SharedPreference.getInstance() != null && SharedPreference.getInstance().getLoggedInUser() != null && !TextUtils.isEmpty(SharedPreference.getInstance().getLoggedInUser().getEmail())) {
                    if (!SharedPreference.getInstance().getLoggedInUser().getEmail().equalsIgnoreCase(phone)) {
                        isDataValid = false;
                        Toast.makeText(activity, "Email id not match.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        if (isDataValid) {

            if (!resetPass) {
                user = Data.getInstance();
            } else {
                user = SharedPreference.getInstance().getLoggedInUser();
            }
            if (isphone) {
                user.setMobile(phone);

            } else {
                user.setEmail(phone);
            }
            SharedPreference.getInstance().setLoggedInUserr(user);
            NetworkAPICall(API.API_SEND_OTP_VERIFICATION, "", true, false, false);
        }
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_SEND_OTP_VERIFICATION:
                EncryptionData forgotpasswordencryptionData = new EncryptionData();
                forgotpasswordencryptionData.setMobile(/*userID,*/!isphone ? user.getEmail() : (TextUtils.isEmpty(user.getMobile()) ? user.getEmail() : user.getMobile()));
                forgotpasswordencryptionData.setOtp("");
                forgotpasswordencryptionData.setIs_registration("0");
                forgotpasswordencryptionData.setResend("0");
                String forgotpasswordencryptionDatadoseStr = new Gson().toJson(forgotpasswordencryptionData);
                String forgotpassworddoseStrScr = AES.encrypt(forgotpasswordencryptionDatadoseStr);
                return service.getOtpVerification(forgotpassworddoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Log.e("json", "API_SEND_OTP_VERIFICATION " + jsonObject.toString());
        switch (apitype) {
            case API.API_SEND_OTP_VERIFICATION:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    //JSONObject dataJsonObject = jsonObject.getJSONObject(Const.DATA);
                    //RetrofitResponse.GetApiData(activity,jsonObject.optString(Const.AUTH_CODE), jsonObject.optString(Const.MESSAGE), false);
                    //String otp = dataJsonObject.getString(Const.OTP);
                    String otp = "";
                    Constants.MOBILE_NO = user.getMobile();
                    SharedPreference.getInstance().putString(Const.FORGETPASSWORD, jsonObject.optString("message"));
                    Helper.GoToOtpVerificationActivity(activity, otp, 2, Const.OTPVERIFICATION, resetPass, isChangePass, isphone);
                } else {
                    RetrofitResponse.GetApiData(activity, jsonObject.optString(Const.AUTH_CODE), jsonObject.optString(Const.MESSAGE), false);
                    //this.ErrorCallBack(jsonObject.getString(Const.MESSAGE), apitype);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        switch (apitype) {
            case API.API_SEND_OTP_VERIFICATION:
                Toast.makeText(activity, jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
