package com.utkarshnew.android.Login.Fragment;


import static com.utkarshnew.android.Utils.Helper.getFirebaseToken;

import android.app.Activity;
import android.content.Context;
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
import com.utkarshnew.android.EncryptionModel.LocationInfo;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Intro.Activity.IntroActivity;
import com.utkarshnew.android.Login.Activity.LoginCatActivity;
import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.Model.User;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.PigibagTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.EmojiFilter;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.pojo.Userinfo.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;

public class Login extends MainFragment {

    public String mobile, password, socialToken = "12345678", deviceId;
    Activity activity;
    EditText memailET, mpasswordET;
    Button mlogInBtn;
    TextView mforgetpasswordTV;
    User user;
    String socialType;
    UtkashRoom utkashRoom;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ibt_fragment_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Resume calling", "");
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
        Log.e("register", "deviceId: " + deviceId);
        deviceId = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        memailET = (EditText) view.findViewById(R.id.et_email);
        mpasswordET = (EditText) view.findViewById(R.id.et_password);
        mpasswordET.setFilters(EmojiFilter.getFilter());
        mforgetpasswordTV = view.findViewById(R.id.forgetpasswordTV);
        mlogInBtn = (Button) view.findViewById(R.id.loginBtn);
        user = User.newInstance();
        mlogInBtn.setOnClickListener(new OnSingleClickListener(() -> {
            socialType = "";
            CheckValidation(false);
            return null;
        }));

        mforgetpasswordTV.setOnClickListener(new OnSingleClickListener(() -> {
            Intent intent = new Intent(activity, LoginCatActivity.class);
            intent.putExtra(Const.FRAG_TYPE, Const.FORGETPASSWORD);
            intent.putExtra(Const.RESET_PASS, false);
            intent.putExtra(Const.IS_CHANGE_PASS, false);
            Helper.gotoActivity(intent, activity);
            return null;
        }));

        memailET.setText(Constants.MOBILE_NO);
    }

    public void CheckValidation(boolean isGuest) {
        mobile = Helper.GetText(memailET);
        password = Helper.GetText(mpasswordET);
        boolean isDataValid = true;
        if (TextUtils.isEmpty(mobile)) {
            isDataValid = Helper.DataNotValid(memailET);
        } else if ((Patterns.PHONE.matcher(mobile).matches() != true) || (mobile.length() != 10)) {
            isDataValid = Helper.DataNotValid(memailET, 2);
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "Password is required", Toast.LENGTH_SHORT).show();
            isDataValid = false;
        }

        if (isDataValid) {
            if (deviceId == null) {
                deviceId = Settings.Secure.getString(activity.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            user.setEmail(mobile);
            user.setMobile(mobile);
            user.setPassword(password);
            user.setIs_social(Const.SOCIAL_TYPE_FALSE);
            user.setSocial_type(socialType);
            user.setSocial_tokken(socialToken);
            user.setDevice_type(Const.DEVICE_TYPE_ANDROID);
            user.setDevice_tokken(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ? getFirebaseToken() : SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID));
            SharedPreference.getInstance().setLoggedInUser(user);
            Helper.hideKeyboard(activity);
            //Helper.StartShimmer(((SignInActivity) activity).shimmerView, ((SignInActivity) activity).examPrepLayerRV);
            NetworkAPICall(API.API_USER_LOGIN_AUTHENTICATION, "", true, false, false);
        }
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_USER_LOGIN_AUTHENTICATION:

                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setMobile(mobile);
                encryptionData.setDevice_id(deviceId);
                encryptionData.setDevice_tokken(user.getDevice_tokken());
                encryptionData.setPassword(password);
                encryptionData.setIs_social(user.getIs_social());

                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setLat(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.LOCATION_LAT)) ? "N/A" : SharedPreference.getInstance().getString(Const.LOCATION_LAT));
                locationInfo.setLng(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.LOCATION_LNG)) ? "N/A" : SharedPreference.getInstance().getString(Const.LOCATION_LNG));
                locationInfo.setIp("");
                locationInfo.setManufacturer(TextUtils.isEmpty(Build.MANUFACTURER) ? "N/A" : Build.MANUFACTURER);
                locationInfo.setDevice_model(TextUtils.isEmpty(Build.MODEL) ? "N/A" : Build.MODEL);
                locationInfo.setOs_version(TextUtils.isEmpty(Build.VERSION.RELEASE) ? "N/A" : Build.VERSION.RELEASE);
                encryptionData.setLocation(locationInfo);

                String doseStr = new Gson().toJson(encryptionData);
                String doseStrScr = AES.encrypt(doseStr);
                return service.userLoginAuthentication(doseStrScr);

            case API.get_my_profile:

                EncryptionData profileencryptionData = new EncryptionData();
                profileencryptionData.setMobile(mobile);
                profileencryptionData.setDevice_id(deviceId);
                profileencryptionData.setPassword(password);
                profileencryptionData.setIs_social(user.getIs_social());
                String profileencryptionDatadoseStr = new Gson().toJson(profileencryptionData);
                String profiledoseStrScr = AES.encrypt(profileencryptionDatadoseStr);
                return service.userProfile(profiledoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {
            case API.API_USER_LOGIN_AUTHENTICATION:
                try {
                    Log.e("String Login", jsonObject.toString());

                    if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject dataJsonObject = jsonObject.getJSONObject(Const.DATA);
                        SharedPreference.getInstance().putString(Const.JWT, dataJsonObject.optString(Const.JWT));

                        NetworkAPICall(API.get_my_profile, "", false, false, false);

                    } else
                        this.ErrorCallBack(jsonObject.getString(Const.MESSAGE), apitype, typeApi);
                } catch (Exception ex) {
                    //Helper.StopShimmer(((SignInActivity) activity).shimmerView, ((SignInActivity) activity).examPrepLayerRV);
                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
                }
                break;

            case API.get_my_profile:
                try {
                    Log.e("String Login", jsonObject.toString());

                    if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                        SharedPreference.getInstance().putBoolean(Const.IS_USER_LOGGED_IN, true);
                        SharedPreference.getInstance().putBoolean(Const.IS_USER_REGISTRATION_DONE, true);

                        UserData userData = new Gson().fromJson(jsonObject.toString(), UserData.class);
                        MakeMyExam.setUserId(userData.getData().getId());

                        SharedPreference.getInstance().setLoggedInUserr(userData.getData());
                        PigibagTable pigibag = new PigibagTable();
                        if (utkashRoom.getpigibag().getuserid() != null) {
                            pigibag = utkashRoom.getpigibag().getuserid();
                            Helper.clearappdata(activity);
                            if (!pigibag.getUser_id().equalsIgnoreCase(userData.getData().getId())) {
//
                                if (jsonObject.getJSONObject("data").has("change_detector")) {
                                    try {
                                        if (jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_009")) {
                                            if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_009")) {
                                                APITABLE apiMangerTable = new APITABLE();
                                                apiMangerTable.setApicode("ut_009");
                                                apiMangerTable.setApiname("master_content");
                                                apiMangerTable.setInterval("0");
                                                apiMangerTable.setUser_id(userData.getData().getId());
                                                apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                                                apiMangerTable.setCdtimestamp("0");
                                                apiMangerTable.setVersion(jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_009"));
                                                utkashRoom.getapidao().addUser(apiMangerTable);
                                            }
                                        }
                                        if (jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_012")) {
                                            if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_012")) {

                                                APITABLE apiMangerTable = new APITABLE();
                                                apiMangerTable.setApicode("ut_012");
                                                apiMangerTable.setApiname("get_my_courses");
                                                apiMangerTable.setInterval("0");
                                                apiMangerTable.setUser_id(userData.getData().getId());
                                                apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                                                apiMangerTable.setCdtimestamp("0");
                                                apiMangerTable.setVersion(jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_012"));
                                                utkashRoom.getapidao().addUser(apiMangerTable);
                                            }
                                        }

                                        if (jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_010")) {
                                            if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_010")) {
                                                APITABLE apiMangerTable = new APITABLE();
                                                apiMangerTable.setApicode("ut_010");
                                                apiMangerTable.setApiname("get_courses");
                                                apiMangerTable.setInterval("0");
                                                apiMangerTable.setUser_id(userData.getData().getId());
                                                apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                                                apiMangerTable.setCdtimestamp("0");
                                                apiMangerTable.setVersion(jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_010"));
                                                utkashRoom.getapidao().addUser(apiMangerTable);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            if (jsonObject.getJSONObject("data").has("change_detector")) {
                                try {
                                    if (jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_009")) {
                                        if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_009")) {
                                            APITABLE apiMangerTable = new APITABLE();
                                            apiMangerTable.setApicode("ut_009");
                                            apiMangerTable.setApiname("master_content");
                                            apiMangerTable.setInterval("0");
                                            apiMangerTable.setUser_id(userData.getData().getId());
                                            apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                                            apiMangerTable.setCdtimestamp("0");
                                            apiMangerTable.setVersion(jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_009"));
                                            utkashRoom.getapidao().addUser(apiMangerTable);
                                        }
                                    }
                                    if (jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_012")) {
                                        if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_012")) {

                                            APITABLE apiMangerTable = new APITABLE();
                                            apiMangerTable.setApicode("ut_012");
                                            apiMangerTable.setApiname("get_my_courses");
                                            apiMangerTable.setInterval("0");
                                            apiMangerTable.setUser_id(userData.getData().getId());
                                            apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                                            apiMangerTable.setCdtimestamp("0");
                                            apiMangerTable.setVersion(jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_012"));
                                            utkashRoom.getapidao().addUser(apiMangerTable);
                                        }
                                    }

                                    if (jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_010")) {
                                        if (!utkashRoom.getapidao().is_api_code_exits(userData.getData().getId(), "ut_010")) {
                                            APITABLE apiMangerTable = new APITABLE();
                                            apiMangerTable.setApicode("ut_010");
                                            apiMangerTable.setApiname("get_courses");
                                            apiMangerTable.setInterval("0");
                                            apiMangerTable.setUser_id(userData.getData().getId());
                                            apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                                            apiMangerTable.setCdtimestamp("0");
                                            apiMangerTable.setVersion(jsonObject.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_010"));
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
                        bundle.putInt(Const.NOTIFICATION_CODE, ((SignInActivity) requireActivity()).notification_code);
                        bundle.putString(Const.COURSE_ID, ((SignInActivity) getActivity()).course_id);
                        bundle.putString("postid", ((SignInActivity) getActivity()).postid);
                        bundle.putString(Const.FileID, ((SignInActivity) getActivity()).fieldid);
                        bundle.putString(Const.TOPIC_ID, ((SignInActivity) getActivity()).topicid);
                        bundle.putString(Const.TILE_ID, ((SignInActivity) getActivity()).tileid);
                        bundle.putString(Const.TILE_TYPE, ((SignInActivity) getActivity()).tiletype);
                        bundle.putString(Const.REVERT_API, ((SignInActivity) getActivity()).revertapi);
                        bundle.putString(Const.TITLE, ((SignInActivity) getActivity()).title);
                        bundle.putString("target", ((SignInActivity) getActivity()).message_target);
                        bundle.putString(Const.URL, ((SignInActivity) getActivity()).url);
                        bundle.putString(Const.MESSAGE, ((SignInActivity) getActivity()).message);
                        bundle.putString(Const.SHARE_TYPE, ((SignInActivity) getActivity()).shareType);

                        Helper.createShortcuts(activity);

                        if (userData.getData().getPreferences()!=null&& userData.getData().getPreferences().size()>0)
                        {
                            activity.startActivity(new Intent(activity, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));
                        }else {
                            activity.startActivity(new Intent(activity, IntroActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));
                        }
                        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else
                        this.ErrorCallBack(jsonObject.getString(Const.MESSAGE), apitype, typeApi);
                } catch (Exception ex) {
                    //Helper.StopShimmer(((SignInActivity) activity).shimmerView, ((SignInActivity) activity).examPrepLayerRV);
                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        //Helper.StopShimmer(((SignInActivity) activity).shimmerView, ((SignInActivity) activity).examPrepLayerRV);
        switch (apitype) {
            case API.get_my_profile:
            case API.API_USER_LOGIN_AUTHENTICATION:
                Toast.makeText(activity, jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult: ", String.valueOf(Constants.frontImageClicked));
    }

    @Override
    public void onPause() {
        super.onPause();
        //Helper.StopShimmer(((SignInActivity) activity).shimmerView, ((SignInActivity) activity).examPrepLayerRV);
    }
}
