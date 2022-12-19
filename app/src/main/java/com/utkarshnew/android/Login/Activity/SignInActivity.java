package com.utkarshnew.android.Login.Activity;

import static com.utkarshnew.android.Utils.Helper.getMaintanaceDialog;
import static com.utkarshnew.android.Utils.Helper.isvisible;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Login.Fragment.Login;
import com.utkarshnew.android.Login.Fragment.SignUp;
import com.utkarshnew.android.Login.Fragment.otpverification;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.LinkDialog;
import com.utkarshnew.android.Utils.MyLocation;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SignInActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {
    private static final String TAG = "SignInActivity";
    public ViewPager viewPager;
    public Progress mprogress;
    public static String deviceId;
    ViewPagerAdapter adapter;
    public LinearLayout TabLinearLayout, toolbarLinearLayout;
    SignUp signUpFrag;
    Login loginFrag;
    CharSequence Titles[] = {"Sign Up", "Log In"};
    LinkDialog d;
    NetworkCall NC;
    private TabLayout tabLayout;
    private TextView previousPosition = null;
    ImageView iv_back;
    TextView signInTextView, signUpTextView;
    public LinearLayout signLL;
    LinearLayout signInUpLayout;
    // public ShimmerFrameLayout shimmerView;
    public LinearLayout examPrepLayerRV;

    //permiossion
    private static int STORAGE_REQUEST_CODE = 2;
    private static int LOCATION_REQUEST_CODE = 3;
    private boolean idDenied = false;

    public int notification_code;
    public String message = "", title = "", url = "", message_target = "", type = "", shareType = "", course_id = "",postid="", fieldid = "", topicid = "", testid = "", testname = "", testquestion = "", tiletype = "", tileid = "", revertapi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_sign_in);

        if (getIntent() != null) {
            notification_code = getIntent().getIntExtra(Const.NOTIFICATION_CODE, 0);
            course_id = getIntent().getStringExtra(Const.COURSE_ID);
            postid = getIntent().getStringExtra("postid");
            fieldid = getIntent().getStringExtra(Const.FileID);
            topicid = getIntent().getStringExtra(Const.TOPIC_ID);
            tiletype = getIntent().getStringExtra(Const.TILE_TYPE);
            tileid = getIntent().getStringExtra(Const.TILE_ID);
            revertapi = getIntent().getStringExtra(Const.REVERT_API);
            title = getIntent().getStringExtra("title");
            message_target = getIntent().getStringExtra("target");
            url = getIntent().getStringExtra("url");
            message = getIntent().getStringExtra("description");
            type = getIntent().getStringExtra(Const.TYPE);
            shareType = getIntent().getStringExtra(Const.SHARE_TYPE);
        }

        mprogress = new Progress(this);
        mprogress.setCancelable(false);
        deviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Helper.logUser(this);
       /* if (SharedPreference.getInstance().getString(Const.IP_ADDRESS).equalsIgnoreCase(""))
        new GetIP(SignInActivity.this).execute();*/

        NC = new NetworkCall(this, this);
        NC.NetworkAPICall(API.API_GET_APP_VERSION, "", false, false);

        d = new LinkDialog(SignInActivity.this);

        //   shimmerView = (ShimmerFrameLayout) findViewById(R.id.shimmer_view);
        examPrepLayerRV = findViewById(R.id.activity_sign_in);

        signInTextView = findViewById(R.id.signInTextView);
        signUpTextView = findViewById(R.id.signUpTextView);
        signLL = findViewById(R.id.signLL);
        signInUpLayout = findViewById(R.id.signInUpLayout);

        iv_back = findViewById(R.id.iv_back);
        toolbarLinearLayout = findViewById(R.id.ll_toolbar);
        TabLinearLayout = (LinearLayout) findViewById(R.id.fourFragmentLayout);
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        setupTabs();

        viewPager.setOffscreenPageLimit(Titles.length);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (previousPosition != null) {
                    previousPosition.setTextColor(getResources().getColor(R.color.greayrefcode_dark));
                }

                TextView view = (TextView) tab.getCustomView();
                view.setTextColor(getResources().getColor(R.color.black));
                previousPosition = (TextView) tab.getCustomView();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInClick();
                getSupportFragmentManager().beginTransaction().replace(R.id.fourFragmentLayout, new Login()).commit(); // Sushant
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpClick();
                getSupportFragmentManager().beginTransaction().replace(R.id.fourFragmentLayout, new SignUp()).commit(); // Sushant
            }
        });

        signInCallMethod();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void signInCallMethod() {
        if (type.equals(Const.SIGNUP))
            getSupportFragmentManager().beginTransaction().replace(R.id.fourFragmentLayout, new SignUp()).commit(); // Sushant
        else if (type.equals(Const.SIGNIN))
            getSupportFragmentManager().beginTransaction().replace(R.id.fourFragmentLayout, new Login()).commit(); // Sushant
    }


    private void setupTabs() {
        for (int i = 0; i < adapter.getCount(); i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            textView.setText(Titles[i]);

            tabLayout.getTabAt(i).setCustomView(textView);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), viewPager);

        signUpFrag = new SignUp();
        loginFrag = new Login();

        adapter.addFrag(signUpFrag, Const.SIGNUP);
        adapter.addFrag(loginFrag, Const.LOGIN);
        viewPager.setAdapter(adapter);
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                return service.getAppVersion();

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Log.e("SignUp", "SuccessCallBack " + jsonObject.toString());
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonObject.getJSONObject(Const.DATA);
                    String androidv = data.optString("version");
                    String androidmv = data.optString("min_version");
                    String androidType = data.optString("force_update");
                    String breakFrom = data.optString("break_from");
                    String breakTo = data.optString("break_to");
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
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Toast.makeText(SignInActivity.this, "" + jsonstring, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SignInActivity", "destroyed");
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fourFragmentLayout) instanceof Login) {
            if (getIntent().getExtras().getString(Const.OPEN_WITH).equals(Const.GUEST_OPEN)) {
                finish();
            } else {
                if (Constants.forLiveClass) {
                    finish();
                } else {
                    Intent intent1 = new Intent(SignInActivity.this, LoginCatActivity.class);
                    intent1.putExtra(Const.FRAG_TYPE, Const.CHANGELANGUAGE);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    finish();
                    Helper.activityAnimation(this);
                }
            }
        } else if (getSupportFragmentManager().findFragmentById(R.id.fourFragmentLayout) instanceof SignUp) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fourFragmentLayout, new Login()).commit();
            signInClick();
        } else {
            getSupportFragmentManager().popBackStack();
            setSignInUpLayoutVisibility(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fourFragmentLayout);
        if (frag instanceof SignUp || frag instanceof otpverification || frag instanceof Login) {
            frag.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        ViewPager pager;

        public ViewPagerAdapter(FragmentManager manager, ViewPager pager) {
            super(manager);
            this.pager = pager;
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String lang = SharedPreference.getInstance().getString(Const.APP_LANGUAGE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(CustomContextWrapper.wrap(newBase, lang));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    public void signUpClick() {
        signUpTextView.setTextColor(getResources().getColor(R.color.blackApp));
        signUpTextView.setBackground(getResources().getDrawable(R.drawable.round_black_bg));

        signInTextView.setTextColor(getResources().getColor(R.color.black_overlay));
        signInTextView.setBackground(getResources().getDrawable(R.drawable.round_white_bg));
        setSignInUpLayoutVisibility(true);
    }

    public void signInClick() {
        signInTextView.setTextColor(getResources().getColor(R.color.blackApp));
        signInTextView.setBackground(getResources().getDrawable(R.drawable.round_black_bg));

        signUpTextView.setTextColor(getResources().getColor(R.color.black_overlay));
        signUpTextView.setBackground(getResources().getDrawable(R.drawable.round_white_bg));
        setSignInUpLayoutVisibility(true);
    }

    public void setSignInUpLayoutVisibility(boolean visibility) {
        if (visibility) {
            signLL.setBackground(getResources().getDrawable(R.drawable.round_top_corners));
            signInUpLayout.setVisibility(View.VISIBLE);
        } else {
            signLL.setBackground(getResources().getDrawable(R.drawable.round_top_corners));
            signInUpLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Helper.StopShimmer(shimmerView, examPrepLayerRV);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!checkStoragePerm()) {
            requestStoragePermission();
        } else {
            enableLocation();
        }
//        Helper.checkXposed(this);
    }

    public void enableLocation() {
        if (!checkLocationPerm()) {
            if (!idDenied) {
                requestLocationPermission();
            }
        } else {
            getLocation();
        }
    }

    private long mLastClickTime1 = 0;

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
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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

    //permission
    private boolean checkStoragePerm() {
        return ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
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

    private boolean checkLocationPerm() {
        return ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(SignInActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
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
            } else {
                idDenied = true;
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


}
