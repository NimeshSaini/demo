package com.utkarshnew.android.Coupon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.utkarshnew.android.Coupon.Fragments.AvailableCouponFragment;
import com.utkarshnew.android.Coupon.Fragments.ExpiredCouponFragment;
import com.utkarshnew.android.Coupon.Fragments.RedeemedCouponFragment;
import com.utkarshnew.android.Coupon.Models.CouponPojo;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class CouponActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {

    private TabLayout tabLayout;
    private ViewPager view_pager;
    private AvailableCouponFragment availableCouponFragment;
    private ExpiredCouponFragment expiredCouponFragment;
    private RedeemedCouponFragment redeemedCouponFragment;
    private CouponViewPagerAdapter adapter;
    public UtkashRoom myDBClass;
    ImageView image_back;
    NetworkCall networkCall;
    public CouponPojo couponPojo;

    public int stackPlace = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        Helper.enableScreenShot(this);

        //setContentView(R.layout.activity_live_class);
        fetchData();

        tabLayout = findViewById(R.id.tabs);
        view_pager = findViewById(R.id.view_pager);
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
            finish();
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

    }


    public void fetchData() {
        networkCall = new NetworkCall(this, this);
        networkCall.NetworkAPICall(API.API_GET_COUPON, "", true, false);
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        if (apitype.equalsIgnoreCase(API.API_GET_COUPON)) {
            return service.API_GET_COUPON("");
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        if (jsonstring.getString("status").equalsIgnoreCase("true")) {
            JSONObject jsonObject = jsonstring.optJSONObject(Const.DATA);
            if (jsonObject != null) {
                couponPojo = new Gson().fromJson(jsonObject.toString(), CouponPojo.class);
            }
            setupViewPager();
        }else {
            RetrofitResponse.GetApiData(CouponActivity.this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);

        }



    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    public class CouponViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CouponViewPagerAdapter(FragmentManager fm) {
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
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.COUPON_DATA, couponPojo);
            adapter = new CouponViewPagerAdapter(getSupportFragmentManager());
            availableCouponFragment = new AvailableCouponFragment();
            availableCouponFragment.setArguments(bundle);
            adapter.addFragment(availableCouponFragment, "Available");

            redeemedCouponFragment = new RedeemedCouponFragment();
            redeemedCouponFragment.setArguments(bundle);
            adapter.addFragment(redeemedCouponFragment, "Redeemed");

            expiredCouponFragment = new ExpiredCouponFragment();
            expiredCouponFragment.setArguments(bundle);
            adapter.addFragment(expiredCouponFragment, "Expired");

            view_pager.setAdapter(adapter);
            tabLayout.setupWithViewPager(view_pager);

            if (stackPlace != -1) {
                view_pager.setCurrentItem(stackPlace);
                stackPlace = -1;
            }
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
}