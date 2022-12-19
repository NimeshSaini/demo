package com.utkarshnew.android.Login.Activity;

import static android.os.Build.VERSION.SDK_INT;
import static com.utkarshnew.android.Utils.Helper.getFirebaseToken;
import static com.utkarshnew.android.Utils.Helper.getMaintanaceDialog;
import static com.utkarshnew.android.Utils.Helper.isvisible;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.EncryptionModel.LocationInfo;
import com.utkarshnew.android.Model.User;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Profile.ProfileActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MyLocation;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.pojo.Userinfo.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Objects;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import retrofit2.Call;

public class LoginWithOtp extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, StoriesProgressView.StoriesListener{


    NetworkCall NC;
    private static int STORAGE_REQUEST_CODE = 2;
    private static int LOCATION_REQUEST_CODE = 3;
    private boolean idDenied = false;
    private long mLastClickTime1 = 0;
    String deviceId="";
    private static int CREDENTIAL_PICKER_REQUEST = 1;
    EditText et_email;
    Button next;
    String is_otp_login="";
    private StoriesProgressView storiesProgressView;
    ImageView backgroundimage;
    private int counter = 0;
    long pressTime = 0L;
    long limit = 500L;
    TextView textdesc,header_txt;


    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    private final int[] resources = new int[]{
            R.mipmap.onboarding_1,
            R.mipmap.onboarding_2,
            R.mipmap.onboarding_3,
    };

    private final String[] text = new String[]
            {
           "along with Top Educators and structured crash \n" +
                   "courses, mock tests and practice section.",
           "Top of the line faculty having an  enriched\n" +
                   "experience of years will teach you in an organized way for effective and effecient understanding\n",
            "You can evaluate your level of preparations\n" +
                    "by participating in live tests, quizzes along with video solutions substantiated by proper explanations.\n"
    };


    private final String[] text2 = new String[]
            {
                    "Competitive Exams",
                   "Quality Video Lectures",
                    "Online Assessments"
            };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_login_with_otp);
        et_email = findViewById(R.id.et_email);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        backgroundimage = (ImageView) findViewById(R.id.backgroundimage);
        textdesc = (TextView) findViewById(R.id.textdesc);
        storiesProgressView.setStoriesCount(resources.length); // <- set stories
        storiesProgressView.setStoryDuration(3000L); // <- set a story duration
        storiesProgressView.setStoriesListener(this); // <- set listener
        storiesProgressView.startStories(); // <- start progress
        next = findViewById(R.id.next);
        header_txt = findViewById(R.id.header_txt);
        backgroundimage.setImageResource(resources[counter]);
        textdesc.setText(text[counter]);
        header_txt.setText(text2[counter]);
        user = User.newInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        NC = new NetworkCall(this, this);
        NC.NetworkAPICall(API.API_GET_APP_VERSION, "", false, false);

        next.setOnClickListener(new OnSingleClickListener(()->
        {
            if(!(et_email.getText().toString()==null && et_email.getText().toString().equals("")))
            {
                hitApiForOtp();
            }
            return null;
        }));




        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
        backgroundimage.setOnTouchListener(onTouchListener);
        et_email.addTextChangedListener(mTextEditorWatcher);


        if (!checkStoragePerm()) {
            requestStoragePermission();
        } else {
            enableLocation();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!checkStoragePerm()) {
            requestStoragePermission();
        } else {
            enableLocation();
        }
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s!=null && s.length()==10)
            {
                next.setBackgroundResource(R.drawable.bg_round_corners_button);

            }
            else
            {
                next.setBackgroundResource(R.drawable.bg_login);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                return service.getAppVersion();

            case API.loginAuthenticationWithOtp:
                EncryptionData loginwithotpdata = new EncryptionData();
                loginwithotpdata.setMobile(et_email.getText().toString());
                loginwithotpdata.setDevice_id(deviceId);
                loginwithotpdata.setIs_social("0");
                loginwithotpdata.setOtp("");
                loginwithotpdata.setIs_registration("1");
                loginwithotpdata.setDevice_tokken(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ? getFirebaseToken() : SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID));
                loginwithotpdata.setResend("0");
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
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonObject.getJSONObject(Const.DATA);
                    String androidv = data.optString("version");
                    String androidmv = data.optString("min_version");
                    String androidType = data.optString("force_update");
                    String breakFrom = data.optString("break_from");
                    String breakTo = data.optString("break_to");
                    is_otp_login = data.optString("is_otp_login");
                    SharedPreference.getInstance().putString("is_otp_login",is_otp_login);
                    int aCode = Integer.parseInt(androidv);
                    int minVersion = Integer.parseInt(androidmv);
                    if (Long.parseLong(breakFrom) < System.currentTimeMillis() && Long.parseLong(breakTo) > System.currentTimeMillis()) {
                        getMaintanaceDialog(((Activity) this), breakFrom, breakTo);
                    } else {
                        if (androidType.equalsIgnoreCase("0")) {
                            if (isvisible.equalsIgnoreCase("0")) {
                                if (aCode > Helper.getVersionCode(((Activity) this)) || Helper.getVersionCode(((Activity) this)) < minVersion) {
                                    Helper.getVersionUpdateDialog(((Activity) this), (Helper.getVersionCode(((Activity) this)) < minVersion ? "1" : androidType));
                                }
                            }
                        } else {
                            isvisible = "0";
                            if (aCode > Helper.getVersionCode(((Activity) this)) || Helper.getVersionCode(((Activity) this)) < minVersion) {
                                Helper.getVersionUpdateDialog(((Activity) this), (Helper.getVersionCode(((Activity) this)) < minVersion ? "1" : androidType));
                            }
                        }
                    }
                } else {

                }
                break;

            case API.loginAuthenticationWithOtp:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Data user = SharedPreference.getInstance().getLoggedInUser();
                    Constants.MOBILE_NO = user.getMobile();
                    Helper.GoToOtpVerificationActivity1(LoginWithOtp.this, "", 9, Const.LOGINWITHOTP, false);
                } else {
                    RetrofitResponse.GetApiData(LoginWithOtp.this, jsonObject.optString(Const.AUTH_CODE), jsonObject.optString(Const.MESSAGE), false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //permission
    private boolean checkStoragePerm() {
        return ContextCompat.checkSelfPermission(LoginWithOtp.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(LoginWithOtp.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);

        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_REQUEST_CODE);
        }
    }

    public void enableLocation() {
        if (!checkLocationPerm()) {
            if (!idDenied) {
                requestLocationPermission();
            }
            else
            {
                phoneNumberSelection();
            }
        } else {
            getLocation();
            phoneNumberSelection();
        }
    }

    private boolean checkLocationPerm() {
        return ContextCompat.checkSelfPermission(LoginWithOtp.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(LoginWithOtp.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }

    public void getLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            final WifiManager wm = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                @Override
                public void gotLocation(Location location) {
                    //Got the location!
                    if (location != null) {
                        double eventLat = location.getLatitude();
                        double eventLong = location.getLongitude();
                        SharedPreference.getInstance().putString(Const.LOCATION_LAT, "" + eventLat);
                        SharedPreference.getInstance().putString(Const.LOCATION_LNG, "" + eventLong);
                    } else {
                        SharedPreference.getInstance().putString(Const.LOCATION_LAT, "");
                        SharedPreference.getInstance().putString(Const.LOCATION_LNG, "");
                    }
                }
            };
            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(this, locationResult);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission Granted, can continue
                requestLocationPermission();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //show "ask me" button for permission
                    startActivity(new Intent(this, PermissionSettingActivity.class));
                    finish();
                } else {
                    //start permission settings activity
                    startActivity(new Intent(this, PermissionSettingActivity.class));
                    finish();
                }
            }
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission Granted, can continue
                idDenied = false;
                phoneNumberSelection();
            } else {
                idDenied = true;
                phoneNumberSelection();
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                        || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //show "ask me" button for permission
                } else {
                    //start permission settings activity
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void buildAlertMessageNoGps() {
        try {
            String msg = "Your GPS seems to be disabled, do you want to enable it?";
            DialogUtils.makeDialog(this, getResources().getString(R.string.app_name), msg,
                    "Allow", "Don’t Allow", false, new DialogUtils.onDialogUtilsOkClick() {
                        @Override
                        public void onOKClick() {
                            if (SystemClock.elapsedRealtime() - mLastClickTime1 < 1000) {
                                return;
                            }
                            mLastClickTime1 = SystemClock.elapsedRealtime();
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }, new DialogUtils.onDialogUtilsCancelClick() {
                        @Override
                        public void onCancelClick() {
                            if (SystemClock.elapsedRealtime() - mLastClickTime1 < 1000) {
                                return;
                            }
                            mLastClickTime1 = SystemClock.elapsedRealtime();
                        }
                    });
        } catch (Exception e) {
            Log.d("TAG", "buildAlertMessageNoGps: " + e.getMessage());
        }
    }



    private void phoneNumberSelection() {

        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        CredentialsOptions credentialsOptions = new CredentialsOptions.Builder()
                .forceEnableSaveDialog()
                .build();

        CredentialsClient credentialsClient = Credentials.getClient(this, credentialsOptions);
        PendingIntent intent = credentialsClient.getHintPickerIntent(hintRequest);
        IntentSenderRequest intentSenderRequest =new IntentSenderRequest.Builder(intent.getIntentSender()).build();
        imageorResult.launch(intentSenderRequest);

      /*  try {
            startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0, new Bundle());
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }*/

    }



    ActivityResultLauncher<IntentSenderRequest> imageorResult = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                if (result!=null && result.getData()!=null)
                {
                    try {
                        Credential credential = result.getData().getParcelableExtra(Credential.EXTRA_KEY);
                        credential.getId();
                        if(credential.getId().length()>10)
                        {
                            int startidx=credential.getId().length()-10;
                            String getnumber=credential.getId().substring(startidx);
                            et_email.setText(getnumber);
                            et_email.getText().toString();
                            hitApiForOtp();
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });
 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK)
        {
            Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
            if(credential.getId()!=null && credential.getId().length()>10)
            {
                int startidx=credential.getId().length()-10;
                String getnumber=credential.getId().substring(startidx,credential.getId().length());
                et_email.setText(getnumber);
                if(!(et_email.getText().toString()==null && et_email.getText().toString().equals("")))
                {
                    hitApiForOtp();
                }
            }
        }
        else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
        {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }
*/

    public String mobile, socialToken = "12345678";
    User user;
    public void hitApiForOtp() {
        mobile = Helper.GetText(et_email);
        boolean isDataValid = true;
        if (TextUtils.isEmpty(mobile))
        {
            isDataValid = Helper.DataNotValid(et_email);
        }
        else if ((Patterns.PHONE.matcher(mobile).matches() != true) || (mobile.length() != 10))
        {
            isDataValid = Helper.DataNotValid(et_email, 2);
        }

        if (isDataValid) {
            if (deviceId == null) {
                deviceId = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            user.setEmail(mobile);
            user.setMobile(mobile);
            user.setPassword("");
            user.setIs_social(Const.SOCIAL_TYPE_FALSE);
            user.setSocial_type("");
            user.setSocial_tokken(socialToken);
            user.setDevice_type(Const.DEVICE_TYPE_ANDROID);
            user.setDevice_tokken(TextUtils.isEmpty(SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID)) ? getFirebaseToken() : SharedPreference.getInstance().getString(Const.FIREBASE_TOKEN_ID));
            SharedPreference.getInstance().setLoggedInUser(user);
            Helper.hideKeyboard(LoginWithOtp.this);
            NC = new NetworkCall(this, this);
            NC.NetworkAPICall(API.loginAuthenticationWithOtp, "", true, false);
        }
    }

    @Override
    public void onNext() {
        backgroundimage.setImageResource(resources[++counter]);
        textdesc.setText(text[counter]);
        header_txt.setText(text2[counter]);
    }

    @Override
    public void onPrev()
    {
        if ((counter - 1) < 0)
        {
            return;
        }
        else
        {
            backgroundimage.setImageResource(resources[--counter]);
            textdesc.setText(text[counter]);
            header_txt.setText(text2[counter]);
        }

    }

    @Override
    public void onComplete()
    {
        counter=0;
        storiesProgressView.setStoriesCount(resources.length); // <- set stories
        storiesProgressView.setStoryDuration(3000L); // <- set a story duration
        storiesProgressView.startStories();
        backgroundimage.setImageResource(resources[counter]);
        textdesc.setText(text[counter]);
        header_txt.setText(text2[counter]);
        storiesProgressView.setStoriesListener(this); // <- set listener

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        storiesProgressView.destroy();
    }
}