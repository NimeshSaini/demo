package com.utkarshnew.android.LiveTest.Activity;

import static com.utkarshnew.android.Utils.Helper.isvisible;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.utkarshnew.android.Login.Activity.SplashScreen;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.LiveTest.Fragment.CompletedTest;
import com.utkarshnew.android.LiveTest.Fragment.Ongoing;
import com.utkarshnew.android.LiveTest.Fragment.UpcomingTest;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.CustomViewPager;
import com.utkarshnew.android.Utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;

public class LivetestActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {

    private TabLayout tabLayout;
    public static CustomViewPager view_pager;
    private CompletedTest completedFragment;
    private Ongoing liveclassesFragment;
    private UpcomingTest upcomingFragment;
    private LiveViewPagerAdapter adapter;
    ImageView image_back;
    NetworkCall networkCall;
    private Timer myTimer;

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(Const.NOTIFICATION_CODE)) {
            if (Helper.isNetworkConnected(this)) {
                networkCall.NetworkAPICall(API.API_GET_APP_VERSION, "", false, false);
              //  super.onBackPressed();

            } else finishAffinity();
        } else super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_livetest);
        try {
            networkCall = new NetworkCall(LivetestActivity.this, LivetestActivity.this);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            tabLayout = findViewById(R.id.tabs);
            view_pager = findViewById(R.id.view_pager);
            view_pager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.none);
            // view_pager.beginFakeDrag();

            image_back = findViewById(R.id.image_back);
            // view_pager.setOffscreenPageLimit(1);


            image_back.setOnClickListener(new OnSingleClickListener(() -> {
                onBackPressed();
                return null;
            }));

            setupViewPager();

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getText().toString().equalsIgnoreCase("Completed")) {
                        completedFragment.chnagevisiblity(true);
                    }
                    if (tab.getText().toString().equalsIgnoreCase("On-Going")) {
                        if (liveclassesFragment != null)
                            liveclassesFragment.chnagevisiblity(true);
                    }
                    if (tab.getText().toString().equalsIgnoreCase("Upcoming")) {
                        if (upcomingFragment != null)
                            upcomingFragment.chnagevisiblity(false);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        if (myTimer != null) {
            myTimer.cancel();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Helper.dismissProgressDialog();
          /*  boolean someValue = intent.getBooleanExtra("response",false);
            Toast.makeText(context, ""+someValue, Toast.LENGTH_SHORT).show();*/
        }
    };

    private void setupViewPager() {
        try {

            adapter = new LiveViewPagerAdapter(getSupportFragmentManager());

            liveclassesFragment = new Ongoing();
            adapter.addFragment(liveclassesFragment, "On-Going");

            upcomingFragment = new UpcomingTest();
            upcomingFragment.chnagevisiblity(false);
            adapter.addFragment(upcomingFragment, "Upcoming");

            completedFragment = new CompletedTest();
            adapter.addFragment(completedFragment, "Completed");

            view_pager.setAdapter(adapter);
            tabLayout.setupWithViewPager(view_pager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //  LocalBroadcastManager.getInstance(LivetestActivity.this).registerReceiver(broadcastReceiver, new IntentFilter(TestService.action));

        try {
            if (Constants.REFRESHPAGE.equalsIgnoreCase("true")) {
                if (LivetestActivity.view_pager.getCurrentItem() == 0) {
                    liveclassesFragment.refresh_data();
                } else if (LivetestActivity.view_pager.getCurrentItem() == 1) {
                    upcomingFragment.refresh_data();
                }
                Constants.REFRESHPAGE = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonObject.getJSONObject(Const.DATA);
                    if (data.has("feeds")) {
                        String feeds = data.optString("feeds");
                        MakeMyExam.setFeeds(feeds);
                    }

                }
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    public class LiveViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public LiveViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}