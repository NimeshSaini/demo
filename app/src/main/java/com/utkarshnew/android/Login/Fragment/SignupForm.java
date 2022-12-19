package com.utkarshnew.android.Login.Fragment;

import static com.utkarshnew.android.Utils.Helper.getFirebaseToken;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.EncryptionModel.LocationInfo;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Intro.Activity.IntroActivity;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.PigibagTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCities;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCitiesData;
import com.utkarshnew.android.pojo.Userinfo.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class SignupForm extends MainFragment implements View.OnClickListener {

    String otp;
    EditText nameTV, emailTV, et_mobile, passwordET, confirmPasswordET;
    Button signupBtn;
    String deviceId;
    String devicetoken;
    RelativeLayout City, State;
    StatesCities states;
    StatesCities cities;
    TextView stateSpinner, districtSpinner;
    String clicktype = "";
    String stateindex = "";
    String cityindex = "";
    String SelectedStateid = "";
    String SelectedCityid = "";
    ImageView iv_back;
    ImageView downarrowIV, downarrowIV2;
    private UtkashRoom utkashRoom;
    UtkashRoom myDBClass;

    public SignupForm() {
    }

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


    public static SignupForm newInstance(String otp) {
        SignupForm fragment = new SignupForm();
        Bundle args = new Bundle();
        args.putString(Const.OTP, otp);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            otp = getArguments().getString(Const.OTP);
        }

        myDBClass = UtkashRoom.getAppDatabase(activity);
        this.utkashRoom = myDBClass;

        deviceId = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        devicetoken = TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ? getFirebaseToken() : SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTV = view.findViewById(R.id.nameTV);
        emailTV = view.findViewById(R.id.emailTV);
        et_mobile = view.findViewById(R.id.et_mobile);
        passwordET = view.findViewById(R.id.passwordET);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        iv_back = view.findViewById(R.id.iv_back);
        confirmPasswordET = view.findViewById(R.id.confirmPasswordET);
        districtSpinner = view.findViewById(R.id.districtSpinner);
        signupBtn = view.findViewById(R.id.signupBtn);
        City = view.findViewById(R.id.city);
        State = view.findViewById(R.id.State);
        downarrowIV = view.findViewById(R.id.downarrowIV);
        downarrowIV2 = view.findViewById(R.id.downarrowIV2);
        et_mobile.setEnabled(true);
        et_mobile.setText(Constants.MOBILE_NO);
        State.setOnClickListener(this);
        City.setOnClickListener(this);
        stateSpinner.setOnClickListener(this);
        districtSpinner.setOnClickListener(this);

        iv_back.setOnClickListener(new OnSingleClickListener(() -> {
            activity.finish();
            return null;
        }));

        signupBtn.setOnClickListener(new OnSingleClickListener(() -> {
            checkvalidation();
            return null;
        }));

        hit_api_to_get_state();
    }

    private void hit_api_to_get_state() {
        NetworkAPICall(API.API_STATE, "", false, false, false);
    }

    private void hit_api_to_get_city() {
        NetworkAPICall(API.API_CITY, "", true, false, false);
    }

    private void checkvalidation() {
        if (Helper.isStringValid(nameTV.getText().toString().trim())) {
            if (Helper.isStringValid(emailTV.getText().toString().trim())) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailTV.getText().toString().trim()).matches()) {

                    if (Helper.isStringValid(et_mobile.getText().toString().trim())) {
                        if (((Patterns.PHONE.matcher(et_mobile.getText().toString().trim()).matches() == true) || (et_mobile.getText().toString().trim().length() == 10))) {
                            if (Helper.isStringValid(stateSpinner.getText().toString()) && (!stateSpinner.getText().toString().equalsIgnoreCase("State"))) {
                                if (Helper.isStringValid(districtSpinner.getText().toString()) && (!districtSpinner.getText().toString().equalsIgnoreCase("District"))) {
                                    if (Helper.isStringValid(passwordET.getText().toString().trim())) {
                                        if (passwordET.getText().length() >= 8 && passwordET.getText().length() <= 13) {
                                            if (Helper.isStringValid(confirmPasswordET.getText().toString().trim())) {
                                                if (passwordET.getText().toString().equals(confirmPasswordET.getText().toString().trim())) {
                                                    hitapiforregistration();
                                                } else {
                                                    Toast.makeText(getContext(), "password and confirm password not matched", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "confirm password field is required", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Password must vary from 8 to 13 characters", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "password" + " field is required", Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    Toast.makeText(getContext(), "Please select district", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(getContext(), "Please select state", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Mobile no is not valid", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "mobile field is required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "email is not valid", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(getContext(), "email field is required", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getContext(), "name field is required", Toast.LENGTH_SHORT).show();
        }

    }

    private void hitapiforregistration() {
        NetworkAPICall(API.API_REGISTER_USER, "", true, false, false);
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_REGISTER_USER:


                EncryptionData registrationencryptionData = new EncryptionData();
                registrationencryptionData.setName(nameTV.getText().toString());
                registrationencryptionData.setEmail(emailTV.getText().toString());
                registrationencryptionData.setPassword(passwordET.getText().toString());
                registrationencryptionData.setMobile(et_mobile.getText().toString());
                registrationencryptionData.setIs_social(Const.SOCIAL_TYPE_FALSE);
                registrationencryptionData.setDevice_id(deviceId);
                registrationencryptionData.setState(stateSpinner.getText().toString());
                registrationencryptionData.setCity(districtSpinner.getText().toString());
                registrationencryptionData.setOtp(otp);
                registrationencryptionData.setDevice_tokken(devicetoken);

                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setLat(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.LOCATION_LAT)) ? "N/A" : SharedPreference.getInstance().getString(Const.LOCATION_LAT));
                locationInfo.setLng(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.LOCATION_LNG)) ? "N/A" : SharedPreference.getInstance().getString(Const.LOCATION_LNG));
                locationInfo.setIp("");
                locationInfo.setManufacturer(TextUtils.isEmpty(Build.MANUFACTURER) ? "N/A" : Build.MANUFACTURER);
                locationInfo.setDevice_model(TextUtils.isEmpty(Build.MODEL) ? "N/A" : Build.MODEL);
                locationInfo.setOs_version(TextUtils.isEmpty(Build.VERSION.RELEASE) ? "N/A" : Build.VERSION.RELEASE);
                registrationencryptionData.setLocation(locationInfo);

                String registrationencryptionDatadoseStr = new Gson().toJson(registrationencryptionData);
                String registrationdoseStrScr = AES.encrypt(registrationencryptionDatadoseStr);
                return service.registerUser(registrationdoseStrScr);

            case API.get_my_profile:

                EncryptionData profileencryptionData = new EncryptionData();
                profileencryptionData.setMobile(et_mobile.getText().toString());
                profileencryptionData.setDevice_id(deviceId);
                profileencryptionData.setPassword(passwordET.getText().toString());
                profileencryptionData.setIs_social(Const.SOCIAL_TYPE_FALSE);
                String profileencryptionDatadoseStr = new Gson().toJson(profileencryptionData);
                String profiledoseStrScr = AES.encrypt(profileencryptionDatadoseStr);
                return service.userProfile(profiledoseStrScr);

            case API.API_STATE:
                EncryptionData stateencryptionData = new EncryptionData();
                stateencryptionData.setUser_id(MakeMyExam.getUserId());
                String stateencryptionDatadoseStr = new Gson().toJson(stateencryptionData);
                String statedoseStrScr = AES.encrypt(stateencryptionDatadoseStr);
                return service.GetState(statedoseStrScr);
            case API.API_CITY:
                EncryptionData cityencryptionData = new EncryptionData();
                cityencryptionData.setState_id(SelectedStateid);
                String cityencryptionDatadoseStr = new Gson().toJson(cityencryptionData);
                String citydoseStrScr = AES.encrypt(cityencryptionDatadoseStr);
                return service.GetCity(citydoseStrScr);

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonString, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_REGISTER_USER:
                try {
                    Log.e("String Login", jsonString.toString());

                    if (jsonString.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject dataJsonObject = jsonString.getJSONObject(Const.DATA);
                        SharedPreference.getInstance().putString(Const.JWT, dataJsonObject.optString(Const.JWT));
                        NetworkAPICall(API.get_my_profile, "", false, false, false);
                    } else
                        this.ErrorCallBack(jsonString.getString(Const.MESSAGE), apitype, typeApi);
                } catch (Exception ex) {
                    //Helper.StopShimmer(((SignInActivity) activity).shimmerView,((SignInActivity) activity).examPrepLayerRV);
                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
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
                            if (!pigibag.getUser_id().equalsIgnoreCase(MakeMyExam.userId)) {
//                                Helper.clearappdata(activity);
                            }
                        }

                        if (jsonString.getJSONObject("data").has("change_detector")) {
                            try {
                                if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_009")) {
                                    if (!utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_009")) {

                                        APITABLE apiMangerTable = new APITABLE();
                                        apiMangerTable.setApicode("ut_009");
                                        apiMangerTable.setApiname("master_content");
                                        apiMangerTable.setInterval("0");
                                        apiMangerTable.setUser_id(MakeMyExam.userId);
                                        apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                        apiMangerTable.setCdtimestamp("0");
                                        apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_009"));
                                        utkashRoom.getapidao().addUser(apiMangerTable);
                                    }
                                }
                                if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_012")) {
                                    if (!utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_012")) {
                                        APITABLE apiMangerTable = new APITABLE();
                                        apiMangerTable.setApicode("ut_012");
                                        apiMangerTable.setApiname("get_my_courses");
                                        apiMangerTable.setInterval("0");
                                        apiMangerTable.setUser_id(MakeMyExam.userId);
                                        apiMangerTable.setTimestamp(String.valueOf(jsonString.optLong("time")));
                                        apiMangerTable.setCdtimestamp("0");
                                        apiMangerTable.setVersion(jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").getString("ut_012"));
                                        utkashRoom.getapidao().addUser(apiMangerTable);
                                    }
                                }

                                if (jsonString.getJSONObject("data").getJSONObject("change_detector").getJSONObject("master").has("ut_010")) {
                                    if (!utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_010")) {
                                        APITABLE apiMangerTable = new APITABLE();
                                        apiMangerTable.setApicode("ut_010");
                                        apiMangerTable.setApiname("get_courses");
                                        apiMangerTable.setInterval("0");
                                        apiMangerTable.setUser_id(MakeMyExam.userId);
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
                        Bundle params = new Bundle();
                        params.putString(AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD, "Mobile Number");
                        params.putString("mobile", userData.getData().getMobile());
                        AppEventsLogger.newLogger(activity).logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, params);

                        activity.finish();
                        Helper.createShortcuts(activity);

                        Intent intent = new Intent(activity, IntroActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Helper.gotoActivity(intent, activity);

                        /*Bundle bundle=new Bundle();
                        bundle.putInt(Const.NOTIFICATION_CODE,((SignInActivity) getActivity()).notification_code);
                        bundle.putString(Const.COURSE_ID,((SignInActivity) getActivity()).course_id);
                        bundle.putString(Const.FileID,((SignInActivity) getActivity()).fieldid);
                        bundle.putString(Const.TOPIC_ID,((SignInActivity) getActivity()).topicid);
                        bundle.putString(Const.TILE_ID,((SignInActivity) getActivity()).tileid);
                        bundle.putString(Const.TILE_TYPE,((SignInActivity) getActivity()).tiletype);
                        bundle.putString(Const.REVERT_API,((SignInActivity) getActivity()).revertapi);
                        bundle.putString(Const.TITLE,((SignInActivity) getActivity()).title);
                        bundle.putString("target",((SignInActivity) getActivity()).message_target);
                        bundle.putString(Const.URL,((SignInActivity) getActivity()).url);
                        bundle.putString(Const.MESSAGE,((SignInActivity) getActivity()).message);
                        bundle.putString(Const.SHARE_TYPE,((SignInActivity) getActivity()).shareType);
                        activity.startActivity(new Intent(activity, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));
                        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);*/
                    } else
                        this.ErrorCallBack(jsonString.getString(Const.MESSAGE), apitype, typeApi);
                } catch (Exception ex) {
                    //Helper.StopShimmer(((SignInActivity) activity).shimmerView,((SignInActivity) activity).examPrepLayerRV);
                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
                }
                break;

            case API.API_STATE:
                if (jsonString.getBoolean("status") == true) {
                    states = new Gson().fromJson(jsonString.toString(), StatesCities.class);
                }
                break;

            case API.API_CITY:
                if (jsonString.getBoolean("status") == true) {
                    cities = new Gson().fromJson(jsonString.toString(), StatesCities.class);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        switch (apitype) {
            case API.API_REGISTER_USER:
            case API.API_STATE:
            case API.API_CITY:
                Toast.makeText(getContext(), jsonstring, Toast.LENGTH_SHORT).show();
                break;

            case API.get_my_profile:
                Toast.makeText(getContext(), jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.State:
            case R.id.stateSpinner:
                if (states == null || states.getData().size() == 0) {
                    Toast.makeText(activity, "No State available", Toast.LENGTH_SHORT).show();
                } else {
                    clicktype = "1";
                    filterList(clicktype, states);
                }
                break;

            case R.id.city:
            case R.id.districtSpinner:
                if ((cities == null || cities.getData().size() == 0)) {
                    Toast.makeText(activity, "Please select State First", Toast.LENGTH_SHORT).show();
                    return;
                }

                clicktype = "2";
                filterList(clicktype, cities);

                break;

        }

    }

    //searchable popup
    EditText etSearch;
    ImageView ivClearSearch;
    RecyclerView searchRecyclerview;
    StateCityAdapter stateCityAdapter;

    public void filterList(String searchType, StatesCities countryArrayList) {
        Dialog searchDialog = new Dialog(activity);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        searchDialog.setContentView(R.layout.state_city_dialog);
        searchDialog.setCancelable(true);
        etSearch = searchDialog.findViewById(R.id.et_search);
        if (searchType.equalsIgnoreCase("1")) {
            etSearch.setHint("Search State");
        } else if (searchType.equalsIgnoreCase("2")) {
            etSearch.setHint("Search District");
        }

        ivClearSearch = searchDialog.findViewById(R.id.iv_clear_search);
        TextView tvCancel = searchDialog.findViewById(R.id.tv_cancel);
        ivClearSearch.setOnClickListener(v -> etSearch.setText(""));
        tvCancel.setOnClickListener(v -> searchDialog.cancel());
        searchRecyclerview = searchDialog.findViewById(R.id.search_recyclerview);
        searchRecyclerview.setHasFixedSize(true);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
        stateCityAdapter = new StateCityAdapter(activity, countryArrayList.getData(), searchType, searchDialog);
        searchRecyclerview.setAdapter(stateCityAdapter);

        textWatcher(searchType);
        searchDialog.show();
    }

    public void textWatcher(String searchType) {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    ivClearSearch.setVisibility(View.VISIBLE);
                } else {
                    ivClearSearch.setVisibility(View.GONE);
                }
                //after the change calling the method and passing the search input
                filter(editable.toString(), searchType);
            }
        });
    }

    ArrayList<StatesCitiesData> statesCitiesArrayList = new ArrayList<>();

    private void filter(String text, String searchType) {
        statesCitiesArrayList.clear();
        if (searchType.equalsIgnoreCase("1")) {
            for (StatesCitiesData country : states.getData()) {
                if (country.getName().toLowerCase().contains(text.toLowerCase())) {
                    statesCitiesArrayList.add(country);
                }
            }
        } else if (searchType.equalsIgnoreCase("2")) {
            for (StatesCitiesData country : cities.getData()) {
                if (country.getName().toLowerCase().contains(text.toLowerCase())) {
                    statesCitiesArrayList.add(country);
                }
            }
        }
        if (!statesCitiesArrayList.isEmpty()) {
            searchRecyclerview.setVisibility(View.VISIBLE);
            stateCityAdapter.filterCountryList(statesCitiesArrayList);
        } else {
            searchRecyclerview.setVisibility(View.INVISIBLE);
        }
    }

    public void onStateCityClick(String searchType, StatesCitiesData country) {
        if (searchType.equalsIgnoreCase("1")) {
            if (!country.getName().equals(stateindex)) {
                for (StatesCitiesData states : states.getData()) {
                    if (states.getName().equals(country.getName())) {
                        stateindex = country.getName();
                        SelectedStateid = states.getId();
                        stateSpinner.setText(country.getName());
                        districtSpinner.setText("District");
                        hit_api_to_get_city();
                        break;
                    }
                }
            }
        } else if (searchType.equalsIgnoreCase("2")) {
            if (!country.getName().equals(cityindex)) {
                for (StatesCitiesData city : cities.getData()) {
                    if (city.getName().equals(country.getName())) {
                        cityindex = country.getName();
                        SelectedCityid = city.getId();
                        districtSpinner.setText(country.getName());
                        break;
                    }
                }
            }
        }
    }

    public class StateCityAdapter extends RecyclerView.Adapter<StateCityAdapter.MyViewHolder> {

        Context context;
        List<StatesCitiesData> countryArrayList;
        String searchType;
        Dialog searchDialog;

        public StateCityAdapter(Context context, List<StatesCitiesData> countryArrayList, String searchType, Dialog searchDialog) {
            this.context = context;
            this.countryArrayList = countryArrayList;
            this.searchType = searchType;
            this.searchDialog = searchDialog;
        }

        @NonNull
        @Override
        public StateCityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.state_city_dialog_adapter_item, viewGroup, false);
            return new StateCityAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StateCityAdapter.MyViewHolder myViewHolder, final int i) {
            StatesCitiesData country = countryArrayList.get(i);
            myViewHolder.tvName.setText(country.getName());

            myViewHolder.tvName.setOnClickListener(v -> {
                if (searchDialog != null) {
                    searchDialog.dismiss();
                }
                switch (searchType) {
                    case "1":
                        onStateCityClick(searchType, country);
                        break;
                    case "2":
                        onStateCityClick(searchType, country);
                        break;
                    default:
                        break;
                }
            });
        }

        @Override
        public int getItemCount() {
            return countryArrayList.size();
        }

        public void filterCountryList(List<StatesCitiesData> newCountryArrayList) {
            countryArrayList = newCountryArrayList;
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.nameTv);
            }
        }
    }

}