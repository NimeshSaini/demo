package com.utkarshnew.android.home.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.utkarshnew.android.courses.Fragment.SingleStudy;
import com.utkarshnew.android.Login.Fragment.changepassword;
import com.utkarshnew.android.Login.Fragment.otpverification;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.SharedPreference;

/**
 * Created by Cbc-03 on 06/06/17.
 */

public abstract class BaseABNoNavActivity extends AppCompatActivity {

    public Fragment mFragment;
    public String type = "";
    public SearchView searchView;
    public ImageView quizNavigatorIV, shareIV, attemptedIV, notAttemptedIV;
    public TextView toolbarTitleTV, tv_notification;
    public Button tryAgainBtn;
    public RelativeLayout mFragmentLayout;
    public LinearLayout errorLayout;
    public String apiType;
    public DrawerLayout drawer;
    public NavigationView navigationView2;
    public RecyclerView controllerRV;
    public Toolbar toolbar;
    FragmentManager fragmentManager;
    Fragment fragment;
    ImageView back;
    boolean isMoved = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.quiz_question_control);
        mFragmentLayout = (RelativeLayout) findViewById(R.id.fragment_container);
        errorLayout = (LinearLayout) findViewById(R.id.errorLL);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbarTitleTV = (TextView) findViewById(R.id.toolbarTitleTV);
        toolbarTitleTV.setSelected(true);
        back = findViewById(R.id.image_back);
        tryAgainBtn = (Button) findViewById(R.id.tryAgainBtn);
        quizNavigatorIV = (ImageView) findViewById(R.id.quizNavigatorIV);
        shareIV = (ImageView) findViewById(R.id.shareIV);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        controllerRV = (RecyclerView) findViewById(R.id.controllerRV);
        attemptedIV = (ImageView) findViewById(R.id.attemptedIV);
        notAttemptedIV = (ImageView) findViewById(R.id.notattemptedIV);
        tv_notification = findViewById(R.id.tv_notification);

//        navigationView2.setNavigationItemSelectedListener(this);

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setSupportActionBar(toolbar);
        Helper.logUser(this);

        InitSearchView();
        initViews();

        back.setOnClickListener(v -> {
            onCustomBackPress();

        });

        mFragment = getFragment();

        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retryApiButton();
            }
        });
    }

    public void setToolbarTitle(String str) {
        toolbarTitleTV.setText(str);
    }

    public void moveFromPayment() {
        isMoved = true;
    }

    public void InitSearchView() {

        searchView = (SearchView) findViewById(R.id.searchSV);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
                toolbarTitleTV.setVisibility(View.VISIBLE);
                SharedPreference.getInstance().putString(Const.COURSE_SEARCHED_QUERY, "");
                RefreshFragmentList(fragment, false);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setFocusable(true);
                toolbarTitleTV.setVisibility(View.GONE);
            }
        });

        View searchPlateView = searchView.findViewById(R.id.search_plate);
        searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        // use this method for search process
        searchView.setQueryHint("Search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // use this method when query submitted
                SharedPreference.getInstance().putString(Const.COURSE_SEARCHED_QUERY, query);
                RefreshFragmentList(getSupportFragmentManager().findFragmentById(R.id.fragment_container), true);
                Helper.closeKeyboard(BaseABNoNavActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        fragmentManager = getSupportFragmentManager();

        fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();
        } else if (mFragment != null) {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).addToBackStack(mFragment.getClass().getSimpleName()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        onCustomBackPress();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onCustomBackPress();
                break;
        }
        return true;
    }

    public void onCustomBackPress() {

        Helper.closeKeyboard(BaseABNoNavActivity.this);
        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (!searchView.isIconified()) {
            toolbarTitleTV.setVisibility(View.VISIBLE);
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();

            // use this method when query submitted
            SharedPreference.getInstance().putString(Const.COURSE_SEARCHED_QUERY, "");
            RefreshFragmentList(fragment, false);

        } else if (fragment instanceof changepassword || fragment instanceof otpverification) {
            this.finish();
        } else if (fragment instanceof SingleStudy) {

            if (isMoved) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                this.finish();
            }
        } else {
            this.finish();
        }
    }

    public void RefreshFragmentList(Fragment fragment, boolean isSearch) {

    }

    protected abstract void initViews();

    protected abstract boolean addBackButton();

    protected abstract Fragment getFragment();

    protected void changeStatusBarColor(String colorName) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (colorName.equalsIgnoreCase(Const.RED)) {
                window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark4));
            }
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

}


