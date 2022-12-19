package com.utkarshnew.android.Login.Fragment;

import static android.app.Activity.RESULT_OK;

import static com.utkarshnew.android.Utils.Helper.getFirebaseToken;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.EncryptionModel.LocationInfo;
import com.utkarshnew.android.Intro.Activity.IntroActivity;
import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Sms.SmsBroadcastReceiver;
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
import com.utkarshnew.android.pojo.Userinfo.UserData;
import com.utkarshnew.android.purchasehistory.UpdateProfileUi;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.PigibagTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;


public class otpverification extends MainFragment implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {

    Button verifyBtn;
    String otp = "";
    String otptext = "";
    String userID = "";
    String isReg = "0";
    int type;
    Data user;
    private boolean resetPass = false;
    private boolean isChangePass = false;
    private boolean isphone = false;
    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinfifthDigitEditText;
    private EditText mPinsixthDigitEditText;
    private EditText mPinHiddenEditText;
    private TextView mresendotpTV, mresendotpTV1;
    private TextView verifyTextTV;
    private TextView tvTimer;
    ImageView iv_back;
    BroadcastReceiver broadcastReceiver;

    TextView havepasswordlcick;
    TextView needhelp;
    String deviceId="";

    boolean status = false;

    private CountDownTimer countDownTimer = null;
    long leftmillisUntilFinished;

    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    UtkashRoom utkashRoom;

    public otpverification() {
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
        mPinFirstDigitEditText.setEnabled(true);
        mPinSecondDigitEditText.setEnabled(true);
        mPinThirdDigitEditText.setEnabled(true);
        mPinForthDigitEditText.setEnabled(true);
        mPinfifthDigitEditText.setEnabled(true);
        mPinsixthDigitEditText.setEnabled(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // getActivity().unregisterReceiver(broadcastReceiver);

    }

    @Override
    public void onStop() {
        super.onStop();
        activity.unregisterReceiver(smsBroadcastReceiver);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public static otpverification newInstance(String otp, int type, boolean resetpass, boolean isChangePass, boolean isphone) {
        otpverification fragment = new otpverification();
        Bundle args = new Bundle();
        args.putString(Const.OTP, otp);
        args.putInt(Const.TYPE, type);
        args.putBoolean(Const.RESET_PASS, resetpass);
        args.putBoolean(Const.IS_CHANGE_PASS, isChangePass);
        args.putBoolean("isphone", isphone);
        fragment.setArguments(args);
        return fragment;
    }

    public static otpverification newInstance(String otp, int type, boolean isphone) {
        otpverification fragment = new otpverification();
        Bundle args = new Bundle();
        args.putString(Const.OTP, otp);
        args.putInt(Const.TYPE, type);
        args.putBoolean("isphone", isphone);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setFocus(EditText editText) {
        if (editText == null)
            return;
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        // requestReadSms();
        if (getArguments() != null) {
            otp = getArguments().getString(Const.OTP);
            type = getArguments().getInt(Const.TYPE);
            resetPass = getArguments().getBoolean(Const.RESET_PASS);
            isChangePass = getArguments().getBoolean(Const.IS_CHANGE_PASS);
            isphone = getArguments().getBoolean("isphone");
        }
        utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

        deviceId = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        startSmartUserConsent();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otpverifcation, container, false);
    }

    private void startSmartUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(activity);
        client.startSmsUserConsent(null);
    }
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPinFirstDigitEditText = (EditText) view.findViewById(R.id.OPT1ET);
        mPinSecondDigitEditText = (EditText) view.findViewById(R.id.OPT2ET);
        mPinThirdDigitEditText = (EditText) view.findViewById(R.id.OPT3ET);
        mPinForthDigitEditText = (EditText) view.findViewById(R.id.OPT4ET);
        mPinfifthDigitEditText = (EditText) view.findViewById(R.id.OPT5ET);
        mPinsixthDigitEditText = (EditText) view.findViewById(R.id.OPT6ET);
        mPinHiddenEditText = (EditText) view.findViewById(R.id.pin_hidden_edittext);
        mresendotpTV1 = (TextView) view.findViewById(R.id.resendotpTV1);
        mresendotpTV = (TextView) view.findViewById(R.id.resendotpTV);
        verifyTextTV = (TextView) view.findViewById(R.id.verifyTextTV);
        iv_back = view.findViewById(R.id.iv_back);
        verifyBtn = (Button) view.findViewById(R.id.verifyBtn);
        tvTimer = (TextView) view.findViewById(R.id.tv_timer);
        havepasswordlcick = (TextView) view.findViewById(R.id.havepasswordlcick);
        needhelp = view.findViewById(R.id.needhelp);
        setPINListeners();
        //AutoReadMessage();
        iv_back.setOnClickListener(new OnSingleClickListener(() -> {
            activity.finish();
            return null;
        }));

        user = SharedPreference.getInstance().getLoggedInUser();


        if (type == 7) {
            isReg = "1";
        } else {
            isReg = "0";
        }

        if (type == 7 || type==9) {
            isphone = true;
        }

        if (type == 2) {
            verifyTextTV.setText("Verify number - " + SharedPreference.getInstance().getLoggedInUser().getMobile());
        } else {
            verifyTextTV.setText("Verify number - " + SharedPreference.getInstance().getLoggedInUser().getMobile());
        }

        verifyBtn.setOnClickListener(new OnSingleClickListener(() -> {
            otptext = mPinHiddenEditText.getText().toString().trim();
            if (TextUtils.isEmpty(otptext)) {
                Toast.makeText(activity, "OTP does not blank.", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (type == 7 || type == 2 || type ==9) {
                userID = SharedPreference.getInstance().getLoggedInUser().getId();
                user.setMobile(Constants.MOBILE_NO);
            }
            Log.e("OtpActivity", "opt:  " + otptext);
            otp = otptext;
            if(type==9)
            {
                NetworkAPICall(API.loginAuthenticationWithOtp, "", true, false, false);
            }
            else
            {
                NetworkAPICall(API.API_SEND_OTP_VERIFICATION, "", true, false, false);
            }
            return null;
        }));


        needhelp.setOnClickListener(new OnSingleClickListener(()->{
            UpdateProfileUi updateProfileUi=new UpdateProfileUi(getContext());
            updateProfileUi.ShowContactLayout();
            return null;
        }));


        havepasswordlcick.setOnClickListener(new OnSingleClickListener(()->{
            Intent intent = new Intent(activity, SignInActivity.class);
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.GUEST_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            activity.startActivity(intent);
            return null;
        }));


        mresendotpTV.setOnClickListener(new OnSingleClickListener(() -> {
            mPinFirstDigitEditText.getText().clear();
            mPinSecondDigitEditText.getText().clear();
            mPinThirdDigitEditText.getText().clear();
            mPinForthDigitEditText.getText().clear();
            mPinfifthDigitEditText.getText().clear();
            mPinsixthDigitEditText.getText().clear();
            setTimer();
            if (type == 7) {
                userID = SharedPreference.getInstance().getLoggedInUser().getId();
                user.setMobile(Constants.MOBILE_NO);
            }
            if (user != null) {
                if (type == 7) {
                    otp = "";
                    NetworkAPICall(API.API_SEND_OTP_VERIFICATION, "", true, false, false);
                } else if (type == 2) {
                    otp = "";
                    NetworkAPICall(API.API_SEND_OTP_VERIFICATION, "", true, false, false);
                }
                else
                    if(type ==9)
                    {
                        otp = "";
                        NetworkAPICall(API.loginAuthenticationWithOtp, "", true, false, false);
                    }
            }
            return null;
        }));
        tvTimer.setPaintFlags(tvTimer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        setTimer();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_SEND_OTP_VERIFICATION:
                EncryptionData registrationencryptionData = new EncryptionData();
                registrationencryptionData.setMobile(!isphone ? user.getEmail() : (TextUtils.isEmpty(user.getMobile()) ? user.getEmail() : user.getMobile()));
                registrationencryptionData.setOtp(otp);
                registrationencryptionData.setIs_registration(isReg);
                registrationencryptionData.setResend("1");
                String registrationencryptionDatadoseStr = new Gson().toJson(registrationencryptionData);
                String registrationdoseStrScr = AES.encrypt(registrationencryptionDatadoseStr);
                return service.getOtpVerification(registrationdoseStrScr);

            case API.get_my_profile:
                EncryptionData profileencryptionData = new EncryptionData();
                profileencryptionData.setMobile(!isphone ? user.getEmail() : (TextUtils.isEmpty(user.getMobile()) ? user.getEmail() : user.getMobile()));
                profileencryptionData.setDevice_id(deviceId);
                profileencryptionData.setPassword("");
                profileencryptionData.setIs_social("0");
                String profileencryptionDatadoseStr = new Gson().toJson(profileencryptionData);
                String profiledoseStrScr = AES.encrypt(profileencryptionDatadoseStr);
                return service.userProfile(profiledoseStrScr);

            case API.loginAuthenticationWithOtp:
                EncryptionData loginwithotpdata = new EncryptionData();
                loginwithotpdata.setMobile(!isphone ? user.getEmail() : (TextUtils.isEmpty(user.getMobile()) ? user.getEmail() : user.getMobile()));
                loginwithotpdata.setDevice_id(deviceId);
                loginwithotpdata.setIs_social("0");
                loginwithotpdata.setOtp(otp);
                loginwithotpdata.setIs_registration("1");
                loginwithotpdata.setDevice_tokken(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ? getFirebaseToken() : SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID));
                loginwithotpdata.setResend("1");
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setLat(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.LOCATION_LAT)) ? "N/A" : SharedPreference.getInstance().getString(Const.LOCATION_LAT));
                locationInfo.setLng(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.LOCATION_LNG)) ? "N/A" : SharedPreference.getInstance().getString(Const.LOCATION_LNG));
                locationInfo.setIp("");
                locationInfo.setManufacturer(TextUtils.isEmpty(Build.MANUFACTURER) ? "N/A" : Build.MANUFACTURER);
                locationInfo.setDevice_model(TextUtils.isEmpty(Build.MODEL) ? "N/A" : Build.MODEL);
                locationInfo.setOs_version(TextUtils.isEmpty(Build.VERSION.RELEASE) ? "N/A" : Build.VERSION.RELEASE);
                loginwithotpdata.setLocation(locationInfo);
                return service.userLoginAuthenticationWithOtp(AES.encrypt(new Gson().toJson(loginwithotpdata)));
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonString, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Log.e("json", jsonString.toString());
        switch (apitype) {
            case API.API_SEND_OTP_VERIFICATION:
                if (jsonString.optString(Const.STATUS).equals(Const.TRUE)) {
                    claerdata();
                    setTimer();
                    if (!TextUtils.isEmpty(otp)) {
                        if (type == 2) {
                            Helper.GoToRegistrationActivity(activity, Const.CHANGEPASSWORD, otp, isChangePass);
                        } else if (type == 7) {
                            Helper.GoToRegistrationActivity(activity, Const.REGISTRATION, otp, isChangePass);
                            activity.finish();
                        }
                    } else {
                        //RetrofitResponse.GetApiData(activity, jsonString.optString(Const.AUTH_CODE), jsonString.optString(Const.MESSAGE), false);
                    }

                } else {
                    RetrofitResponse.GetApiData(activity, jsonString.optString(Const.AUTH_CODE), jsonString.optString(Const.MESSAGE), false);
                }
                break;

            case API.loginAuthenticationWithOtp:
                if (jsonString.optString(Const.STATUS).equals(Const.TRUE)) {
                    claerdata();
                    setTimer();
                    if (!TextUtils.isEmpty(otp)) {
                        if (deviceId == null) {
                            deviceId = Settings.Secure.getString(activity.getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                        }
                        SharedPreference.getInstance().putString(Const.JWT, jsonString.getJSONObject("data").optString(Const.JWT));
                        NetworkAPICall(API.get_my_profile, "", false, false, false);
                    }
                    else
                    {
                        //RetrofitResponse.GetApiData(activity, jsonString.optString(Const.AUTH_CODE), jsonString.optString(Const.MESSAGE), false);

                    }
                } else {
                    RetrofitResponse.GetApiData(activity, jsonString.optString(Const.AUTH_CODE), jsonString.optString(Const.MESSAGE), false);
                }
                break;

            case API.get_my_profile:
                try {
                    Log.e("String Login", jsonString.toString());

                    if (jsonString.optString(Const.STATUS).equals(Const.TRUE)) {
                        SharedPreference.getInstance().putBoolean(Const.IS_USER_LOGGED_IN, true);
                        SharedPreference.getInstance().putBoolean(Const.IS_USER_REGISTRATION_DONE, true);

                        UserData userData = new Gson().fromJson(jsonString.toString(), UserData.class);
                        MakeMyExam.setUserId(userData.getData().getId());

                        SharedPreference.getInstance().setLoggedInUserr(userData.getData());
                        PigibagTable pigibag = new PigibagTable();
                        if (utkashRoom.getpigibag().getuserid() != null) {
                            pigibag = utkashRoom.getpigibag().getuserid();
                            Helper.clearappdata(activity);
                            if (!pigibag.getUser_id().equalsIgnoreCase(userData.getData().getId())) {
//
                                if (jsonString.getJSONObject("data").has("change_detector")) {
                                    try {
                                        if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_009")) {
                                            if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_009")) {
                                                APITABLE apiMangerTable = new APITABLE();
                                                apiMangerTable.setApicode("ut_009");
                                                apiMangerTable.setApiname("master_content");
                                                apiMangerTable.setInterval("0");
                                                apiMangerTable.setUser_id(userData.getData().getId());
                                                apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                                apiMangerTable.setCdtimestamp("0");
                                                apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_009"));
                                                utkashRoom.getapidao().addUser(apiMangerTable);
                                            }
                                        }
                                        if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_012")) {
                                            if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_012")) {

                                                APITABLE apiMangerTable = new APITABLE();
                                                apiMangerTable.setApicode("ut_012");
                                                apiMangerTable.setApiname("get_my_courses");
                                                apiMangerTable.setInterval("0");
                                                apiMangerTable.setUser_id(userData.getData().getId());
                                                apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                                apiMangerTable.setCdtimestamp("0");
                                                apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_012"));
                                                utkashRoom.getapidao().addUser(apiMangerTable);
                                            }
                                        }

                                        if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_010")) {
                                            if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_010")) {
                                                APITABLE apiMangerTable = new APITABLE();
                                                apiMangerTable.setApicode("ut_010");
                                                apiMangerTable.setApiname("get_courses");
                                                apiMangerTable.setInterval("0");
                                                apiMangerTable.setUser_id(userData.getData().getId());
                                                apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                                apiMangerTable.setCdtimestamp("0");
                                                apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_010"));
                                                utkashRoom.getapidao().addUser(apiMangerTable);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            if (jsonString.getJSONObject("data").has("change_detector")) {
                                try {
                                    if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_009")) {
                                        if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_009")) {
                                            APITABLE apiMangerTable = new APITABLE();
                                            apiMangerTable.setApicode("ut_009");
                                            apiMangerTable.setApiname("master_content");
                                            apiMangerTable.setInterval("0");
                                            apiMangerTable.setUser_id(userData.getData().getId());
                                            apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                            apiMangerTable.setCdtimestamp("0");
                                            apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_009"));
                                            utkashRoom.getapidao().addUser(apiMangerTable);
                                        }
                                    }
                                    if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_012")) {
                                        if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_012")) {

                                            APITABLE apiMangerTable = new APITABLE();
                                            apiMangerTable.setApicode("ut_012");
                                            apiMangerTable.setApiname("get_my_courses");
                                            apiMangerTable.setInterval("0");
                                            apiMangerTable.setUser_id(userData.getData().getId());
                                            apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                            apiMangerTable.setCdtimestamp("0");
                                            apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_012"));
                                            utkashRoom.getapidao().addUser(apiMangerTable);
                                        }
                                    }

                                    if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_010")) {
                                        if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_010")) {
                                            APITABLE apiMangerTable = new APITABLE();
                                            apiMangerTable.setApicode("ut_010");
                                            apiMangerTable.setApiname("get_courses");
                                            apiMangerTable.setInterval("0");
                                            apiMangerTable.setUser_id(userData.getData().getId());
                                            apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                            apiMangerTable.setCdtimestamp("0");
                                            apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_010"));
                                            utkashRoom.getapidao().addUser(apiMangerTable);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        activity.finish();
                        Bundle bundle = new Bundle();
                        if (userData.getData().getPreferences()!=null&& userData.getData().getPreferences().size()>0)
                        {
                            activity.startActivity(new Intent(activity, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));
                        }
                        else
                        {
                            activity.startActivity(new Intent(activity, IntroActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));
                        }
                        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else
                        this.ErrorCallBack(jsonString.getString(Const.MESSAGE), apitype, typeApi);
                } catch (Exception ex) {
                    //Helper.StopShimmer(((SignInActivity) activity).shimmerView, ((SignInActivity) activity).examPrepLayerRV);
                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
                }
                break;
        }
    }

    private void claerdata() {
        activity.runOnUiThread(() -> {

            mPinHiddenEditText.getText().clear();
            mPinFirstDigitEditText.getText().clear();
            mPinSecondDigitEditText.getText().clear();
            mPinThirdDigitEditText.getText().clear();
            mPinForthDigitEditText.getText().clear();
            mPinfifthDigitEditText.getText().clear();
            mPinsixthDigitEditText.getText().clear();


            mPinFirstDigitEditText.setEnabled(true);
            mPinSecondDigitEditText.setEnabled(true);
            mPinThirdDigitEditText.setEnabled(true);
            mPinForthDigitEditText.setEnabled(true);
            mPinfifthDigitEditText.setEnabled(true);
            mPinsixthDigitEditText.setEnabled(true);
        });

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        //Toast.makeText(activity, jsonstring, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if (s.length() == 0) {
            mPinFirstDigitEditText.setText("");
        } else if (s.length() == 1) {
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 2) {
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 3) {
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText("");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 4) {
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 5) {
            mPinfifthDigitEditText.setText(s.charAt(4) + "");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 6) {
            mPinsixthDigitEditText.setText(s.charAt(5) + "");
            hideSoftKeyboard(mPinsixthDigitEditText);
        }
    }

    /**
     * Sets listeners for EditText fields.
     */
    private void setPINListeners() {
        mPinHiddenEditText.addTextChangedListener(this);
        mPinFirstDigitEditText.setOnFocusChangeListener(this);
        mPinSecondDigitEditText.setOnFocusChangeListener(this);
        mPinThirdDigitEditText.setOnFocusChangeListener(this);
        mPinForthDigitEditText.setOnFocusChangeListener(this);
        mPinfifthDigitEditText.setOnFocusChangeListener(this);
        mPinsixthDigitEditText.setOnFocusChangeListener(this);
        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinfifthDigitEditText.setOnKeyListener(this);
        mPinsixthDigitEditText.setOnKeyListener(this);
        mPinHiddenEditText.setOnKeyListener(this);
    }

    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        final int id = view.getId();
        switch (id) {
            case R.id.OPT1ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT2ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT3ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT4ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT5ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT6ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = view.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (mPinHiddenEditText.getText().length() == 6)
                            mPinsixthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 5)
                            mPinfifthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 4)
                            mPinForthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 3)
                            mPinThirdDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 2)
                            mPinSecondDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 1)
                            mPinFirstDigitEditText.setText("");
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                if (type == 7 || type == 2 || type ==9) {
                    userID = SharedPreference.getInstance().getLoggedInUser().getId();
                    user.setMobile(Constants.MOBILE_NO);
                }
                getOtpFromMessage(message);
            } else {
                mPinFirstDigitEditText.setEnabled(true);
                mPinSecondDigitEditText.setEnabled(true);
                mPinThirdDigitEditText.setEnabled(true);
                mPinForthDigitEditText.setEnabled(true);
                mPinfifthDigitEditText.setEnabled(true);
                mPinsixthDigitEditText.setEnabled(true);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        if (message != null && !message.equalsIgnoreCase("")) {
            Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
            Matcher matcher = otpPattern.matcher(message);
            if (matcher.find()) {
                otptext = parseCode(message);
                Log.e("OTP MESSAGE", otptext);
                mPinHiddenEditText.setText(otptext);
                mPinFirstDigitEditText.setText(String.valueOf(otptext.charAt(0)));
                mPinSecondDigitEditText.setText(String.valueOf(otptext.charAt(1)));
                mPinThirdDigitEditText.setText(String.valueOf(otptext.charAt(2)));
                mPinForthDigitEditText.setText(String.valueOf(otptext.charAt(3)));
                mPinfifthDigitEditText.setText(String.valueOf(otptext.charAt(4)));
                mPinsixthDigitEditText.setText(String.valueOf(otptext.charAt(5)));
                mPinFirstDigitEditText.setEnabled(true);
                mPinSecondDigitEditText.setEnabled(true);
                mPinThirdDigitEditText.setEnabled(true);
                mPinForthDigitEditText.setEnabled(true);
                mPinfifthDigitEditText.setEnabled(true);
                mPinsixthDigitEditText.setEnabled(true);
                otp = otptext;
                if(type==9)
                {
                    NetworkAPICall(API.loginAuthenticationWithOtp, "", true, false, false);
                }
                else
                {
                    NetworkAPICall(API.API_SEND_OTP_VERIFICATION, "", true, false, false);
                }
            }
        }
    }

    public void setTimer() {
        status = true;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    tvTimer.setVisibility(View.VISIBLE);
                    mresendotpTV1.setVisibility(View.GONE);
                    mresendotpTV.setVisibility(View.GONE);
                    tvTimer.setText("0" + String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    leftmillisUntilFinished = millisUntilFinished;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                try {
                    status = false;
                    tvTimer.setText("");
                    tvTimer.setVisibility(View.GONE);
                    mresendotpTV.setVisibility(View.VISIBLE);
                    mresendotpTV1.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.d("TAG", "onFinish: " + e.getMessage());
                }
            }
        };
        countDownTimer.start();
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                if (intent != null) {
                    otplauncher.launch(intent);
                } else {
                    intent = new Intent();
                    otplauncher.launch(intent);
                }
            }

            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        activity.registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    ActivityResultLauncher<Intent> otplauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                    Intent data = result.getData();
                    if (data != null) {
                        String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                        if (type == 7 || type == 2) {
                            userID = SharedPreference.getInstance().getLoggedInUser().getId();
                            user.setMobile(Constants.MOBILE_NO);
                        }
                        getOtpFromMessage(message);
                    } else {
                        mPinFirstDigitEditText.setEnabled(true);
                        mPinSecondDigitEditText.setEnabled(true);
                        mPinThirdDigitEditText.setEnabled(true);
                        mPinForthDigitEditText.setEnabled(true);
                        mPinfifthDigitEditText.setEnabled(true);
                        mPinsixthDigitEditText.setEnabled(true);
                    }

                }
            });


    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
}
