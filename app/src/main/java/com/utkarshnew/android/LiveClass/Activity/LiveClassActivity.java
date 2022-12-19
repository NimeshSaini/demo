package com.utkarshnew.android.LiveClass.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.utkarshnew.android.LiveClass.Fragment.Completed;

import com.utkarshnew.android.LiveClass.Fragment.LiveClasses;
import com.utkarshnew.android.LiveClass.Fragment.Upcoming;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class LiveClassActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {

    private TabLayout tabLayout;
    private ViewPager view_pager;
    private Completed completedFragment;
    private LiveClasses liveclassesFragment;
    private Upcoming upcomingFragment;
    private LiveViewPagerAdapter adapter;
    public UtkashRoom myDBClass;
    NetworkCall networkCall;
    ImageView image_back;

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(Const.NOTIFICATION_CODE)) {
            if (Helper.isNetworkConnected(this)) {
                networkCall =new NetworkCall(LiveClassActivity.this,LiveClassActivity.this);
                networkCall.NetworkAPICall(API.API_GET_APP_VERSION, "", false, false);
                 // super.onBackPressed();

            } else finishAffinity();
        } else super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_live_class);
        tabLayout = findViewById(R.id.tabs);
        view_pager = findViewById(R.id.view_pager);
       // Log.d("123456789", "onCreate: " + getIntent().getBooleanExtra("liveclass", false));
        image_back = findViewById(R.id.image_back);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        image_back.setOnClickListener(new OnSingleClickListener(() -> {
            onBackPressed();
            return null;
        }));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setupViewPager();
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


    private void setupViewPager() {
        try {
            adapter = new LiveViewPagerAdapter(getSupportFragmentManager());
            liveclassesFragment = new LiveClasses();
            adapter.addFragment(liveclassesFragment, "Live classes");

            upcomingFragment = new Upcoming();
            adapter.addFragment(upcomingFragment, "Upcoming");

            completedFragment = new Completed();
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
        if (myDBClass == null) {
            myDBClass = UtkashRoom.getAppDatabase(this);
            myDBClass.getOpenHelper().getWritableDatabase().enableWriteAheadLogging();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDBClass = null;
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
}