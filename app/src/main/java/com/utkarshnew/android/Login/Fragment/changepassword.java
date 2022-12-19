package com.utkarshnew.android.Login.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Login.Activity.LoginWithOtp;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class changepassword extends MainFragment {

    Button submitBtn;
    Activity activity;
    EditText newPassowordET, retryPasswordET;
    String retrypassword, newpassword;
    String otp;
    private boolean resetPass = false;
    private boolean isChangePass = false;
    private boolean isphone = false;
    ImageView iv_back;


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

    public changepassword() {
    }

    public static changepassword newInstance(String otp, boolean resetpass, boolean isChangePass, boolean isphone) {
        changepassword fragment = new changepassword();
        Bundle args = new Bundle();
        args.putString(Const.OTP, otp);
        args.putBoolean(Const.RESET_PASS, resetpass);
        args.putBoolean(Const.IS_CHANGE_PASS, isChangePass);
        args.putBoolean("isphone", isphone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            otp = getArguments().getString(Const.OTP);
            resetPass = getArguments().getBoolean(Const.RESET_PASS);
            isChangePass = getArguments().getBoolean(Const.IS_CHANGE_PASS);
            isphone = getArguments().getBoolean("isphone");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_changepassword, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submitBtn = (Button) view.findViewById(R.id.submitBtn);
        newPassowordET = (EditText) view.findViewById(R.id.newpasswordET);
        retryPasswordET = (EditText) view.findViewById(R.id.retrypasswordET);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);

        submitBtn.setOnClickListener(new OnSingleClickListener(() -> {
            CheckValidations();
            return null;
        }));

        iv_back.setOnClickListener(new OnSingleClickListener(() -> {
            activity.finish();
            return null;
        }));
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_UPDATE_PASSWORD_WITH_OTP:

                EncryptionData updatepasswordencryptionData = new EncryptionData();
                updatepasswordencryptionData.setOtp(otp);
                updatepasswordencryptionData.setMobile(Constants.MOBILE_NO);
                updatepasswordencryptionData.setPassword(retryPasswordET.getText().toString());
                String profileencryptionDatadoseStr = new Gson().toJson(updatepasswordencryptionData);
                String profiledoseStrScr = AES.encrypt(profileencryptionDatadoseStr);
                return service.getSingleCalVideoData(profiledoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Log.e("json", jsonObject.toString());
        switch (apitype) {
            case API.API_UPDATE_PASSWORD_WITH_OTP:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    if (!isChangePass) {
                        DialogUtils.makeCompleteCPDialog(activity, "Congratulations !", "you have successfully changed your password", "Go Back To Login",
                                getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                                    @Override
                                    public void onOKClick() {

                                        if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
                                        {
                                            Intent intent = new Intent(activity, SignInActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra(Const.TYPE, Const.SIGNIN);
                                            intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(activity, LoginWithOtp.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra(Const.TYPE, Const.SIGNIN);
                                            intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    } else {
                        DialogUtils.makeCompleteCPDialog(activity, "Congratulations !", "you have successfully changed your password", "Go Back To Home",
                                getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                                    @Override
                                    public void onOKClick() {
                                        Intent intent = new Intent(activity, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                });
                    }
                } else {
                    RetrofitResponse.GetApiData(activity, jsonObject.optString(Const.AUTH_CODE), jsonObject.optString(Const.MESSAGE), false);
                }
                break;


        }
    }


    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Log.e("json", jsonstring.toString());
        switch (apitype) {
            case API.API_UPDATE_PASSWORD_WITH_OTP:
                Toast.makeText(activity, jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void CheckValidations() {
        newpassword = Helper.GetText(newPassowordET);
        retrypassword = Helper.GetText(retryPasswordET);

        boolean isDataValid = true;

        if (TextUtils.isEmpty(newpassword)) {
            isDataValid = false;
            Toast.makeText(activity, "Please fill change password", Toast.LENGTH_SHORT).show();
        } else if (newpassword.length() < 8 || newpassword.length() > 13) {
            isDataValid = false;
            Toast.makeText(activity, "Change password must vary from 8 to 13 characters", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(retrypassword)) {
            isDataValid = false;
            Toast.makeText(activity, "Please fill confirm password", Toast.LENGTH_SHORT).show();
        } else if (retrypassword.length() <= 8 && retrypassword.length() >= 13) {
            isDataValid = false;
            Toast.makeText(activity, "Change password must vary from 8 to 13 characters", Toast.LENGTH_SHORT).show();
        } else if (!newpassword.equals(retrypassword)) {
            isDataValid = false;
            Toast.makeText(activity, "Password does not match", Toast.LENGTH_SHORT).show();
        }
        if (isDataValid) {
            NetworkAPICall(API.API_UPDATE_PASSWORD_WITH_OTP, "", true, false, false);
        }
    }
}
