package com.utkarshnew.android.home.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.razorpay.PaymentResultListener;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.utkarshnew.android.courses.Interfaces.OnSuccessListner;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Fragment.BatchFragment;
import com.utkarshnew.android.home.Fragment.FreeFragment;
import com.utkarshnew.android.home.Fragment.PaidFragment;
import com.utkarshnew.android.home.interfaces.sortClickListner;
import com.utkarshnew.android.home.model.MyCourse;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.MycourseTable;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.GenericUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;

import static com.utkarshnew.android.Utils.Helper.dismissProgressDialog;

public class MyLibraryActivty extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, PaymentResultListener {

    public static String razorkey = "";
    private ViewPagerAdapter adapter;
    private ImageView image_back;
    public PaidFragment paidFragment;
    private FreeFragment freeFragment;
    private BatchFragment batchFragment;
    private ViewPager view_pager;
    public OnSuccessListner onSuccessListner;

    private TabLayout tabLayout;
    private PowerMenu powerMenu;
    private sortClickListner listner;
    private com.utkarshnew.android.home.interfaces.freesortClickListner freesortClickListner;
    private com.utkarshnew.android.home.interfaces.batchsortClickListner batchsortClickListner;
    private TextView sort_by;
    public UtkashRoom myDBClass;
    private NetworkCall networkCall;
    public MyCourse myCourse;
    public DatabaseReference firebaseDatabase;

    public static String selectedTab = "Paid Course";
    private SwipeRefreshLayout pullToReferesh;
    RelativeLayout mainCover, no_data_found_RL, layout;
    Button backBtn;
    ImageView serac_image;
    androidx.appcompat.widget.SearchView serach_view;
    TextView toolbarTitleTV;
    List<MycourseTable> mycourseTables = new ArrayList<>();


    private final OnMenuItemClickListener<PowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {

            item.setIsSelected(true);
            if (selectedTab.contains("Paid")) {
                item_select_dialog_paid(item.getTitle().toString());
            } else if (selectedTab.contains("Free")) {
                item_select_dialog_free(item.getTitle().toString());
            } else if (selectedTab.contains("Batch")) {
                item_select_dialog_batch(item.getTitle().toString());
            }
            powerMenu.dismiss();
        }
    };

    private void item_select_dialog_paid(String titile) {
        myCourse.setPaid_course(myDBClass.getMyCourseDao().getpaidcourse("1"));
        if (titile.equals("Z-A(Title)")) {
            try {
                Collections.sort(myCourse.getPaid_course(), (o1, o2) -> o2.getTitle().toLowerCase().trim().compareTo(o1.getTitle().toLowerCase().trim()));
                listner.onTitleClicked(myCourse.getPaid_course(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("A-Z(Title)")) {
            try {
                Collections.sort(myCourse.getPaid_course(), (o1, o2) -> o1.getTitle().toLowerCase().trim().compareTo(o2.getTitle().toLowerCase().trim()));

                listner.onTitleClicked(myCourse.getPaid_course(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("Date Added")) {
            try {
                Collections.sort(myCourse.getPaid_course(), (o1, o2) -> o1.getPurchase_date().compareTo(o2.getPurchase_date()));
                listner.onTitleClicked(myCourse.getPaid_course(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("Last Read")) {
            try {

                Collections.sort(myCourse.getPaid_course(), (o1, o2) -> o1.getLastread().compareTo(o2.getLastread()));
                listner.onTitleClicked(myCourse.getPaid_course(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void item_select_dialog_free(String titile) {
        myCourse.setFreecourse(myDBClass.getMyCourseDao().getFreecourse("0", "0"));
        if (titile.equals("Z-A(Title)")) {
            try {
                Collections.sort(myCourse.getFreecourse(), (o1, o2) -> o2.getTitle().toLowerCase().trim().compareTo(o1.getTitle().toLowerCase().trim()));
                freesortClickListner.onTitleClicked(myCourse.getFreecourse(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("A-Z(Title)")) {
            try {
                Collections.sort(myCourse.getFreecourse(), (o1, o2) -> o1.getTitle().toLowerCase().trim().compareTo(o2.getTitle().toLowerCase().trim()));
                freesortClickListner.onTitleClicked(myCourse.getFreecourse(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("Date Added")) {
            try {
                Collections.sort(myCourse.getFreecourse(), (o1, o2) -> o1.getPurchase_date().compareTo(o2.getPurchase_date()));
                freesortClickListner.onTitleClicked(myCourse.getFreecourse(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("Last Read")) {
            try {
                Collections.sort(myCourse.getFreecourse(), (o1, o2) -> o1.getLastread().compareTo(o2.getLastread()));
                freesortClickListner.onTitleClicked(myCourse.getFreecourse(), titile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void item_select_dialog_batch(String titile) {
        if (titile.equals("Z-A(Title)")) {
            try {
                Collections.sort(myCourse.getBatchcourse(), (o1, o2) -> o2.getTitle().toLowerCase().trim().compareTo(o1.getTitle().toLowerCase().trim()));
                batchsortClickListner.onTitleClicked(myCourse.getBatchcourse());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("A-Z(Title)")) {
            try {
                Collections.sort(myCourse.getBatchcourse(), (o1, o2) -> o1.getTitle().toLowerCase().trim().compareTo(o2.getTitle().toLowerCase().trim()));

                batchsortClickListner.onTitleClicked(myCourse.getBatchcourse());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (titile.equals("Date Added")) {
            try {
                Collections.sort(myCourse.getBatchcourse(), (o1, o2) -> o1.getPurchase_date().compareTo(o2.getPurchase_date()));
                batchsortClickListner.onTitleClicked(myCourse.getBatchcourse());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.my_library_activty);
        try {
            Log.d("prince", "" + MakeMyExam.getTime_server());
            view_pager = findViewById(R.id.view_pager);
            sort_by = findViewById(R.id.sort_by);
            image_back = findViewById(R.id.image_back);
            tabLayout = findViewById(R.id.tabs);
            pullToReferesh = findViewById(R.id.pullto_referesh);
            mainCover = findViewById(R.id.mainCover);
            serach_view = findViewById(R.id.sv_search);
            serac_image = findViewById(R.id.serac_image);
            toolbarTitleTV = findViewById(R.id.toolbarTitleTV);


             /*   toolbarTitleTV.setVisibility(View.GONE);
                EditText searchEditText = serach_view.findViewById(androidx.appcompat.R.id.search_src_text);
                searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
                searchEditText.setTextColor(getResources().getColor(R.color.blackApp));
                searchEditText.setHintTextColor(getResources().getColor(R.color.blackApp));
                serach_view.setActivated(true);
                serach_view.setIconified(false);
                serach_view.setIconifiedByDefault(false);
                serach_view.setQueryHint("Search My library");
                serach_view.onActionViewExpanded();
                serach_view.clearFocus();

                ImageView searchIcon = (ImageView)serach_view.findViewById(androidx.appcompat.R.id.search_mag_icon);
                searchIcon.setImageResource(R.drawable.search);*/

            no_data_found_RL = findViewById(R.id.no_data_found_RL);
            backBtn = findViewById(R.id.backBtn);
            layout = findViewById(R.id.layout);
            myDBClass = UtkashRoom.getAppDatabase(this);
            networkCall = new NetworkCall(this, this);
            try {
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = this.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
                }
                //firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("DeleteVideo/" +MakeMyExam.userId);

                if (myDBClass.getMyCourseDao().isRecordExists(MakeMyExam.userId)) {
                    mycourseTables = myDBClass.getMyCourseDao().getAllUser();
                    myCourse = new MyCourse();

                    ArrayList<Courselist> courselists = new ArrayList<>();
                    ArrayList<Courselist> paidcourse = new ArrayList<>();
                    ArrayList<Courselist> freecourse = new ArrayList<>();
                    ArrayList<Courselist> batchcourse = new ArrayList<>();
                    for (MycourseTable mycourseTable : mycourseTables) {
                        Courselist courselist = new Courselist();
                        courselist.setId(mycourseTable.getId());
                        courselist.setTitle(mycourseTable.getTitle());
                        courselist.setBatch_id(mycourseTable.getBatch_id());
                        courselist.setCover_image(mycourseTable.getCover_image());
                        courselist.setExpiry_date(mycourseTable.getExpiry_date());
                        courselist.setPurchase_date(mycourseTable.getPurchase_date());
                        courselist.setMrp(mycourseTable.getMrp());
                        courselist.setTxn_id(mycourseTable.getTxn_id());

                        if (!TextUtils.isEmpty(courselist.getBatch_id()) && !courselist.getBatch_id().equalsIgnoreCase("0")) {
                            courselist.setPrices(null);
                            courselist.setExpiry_date("0");
                            courselist.setDelete(0);
                            batchcourse.add(courselist);
                        } else if (courselist.getMrp().equalsIgnoreCase("0")) {
                            courselist.setDelete(1);
                            if (mycourseTable.getPrices() != null && mycourseTable.getPrices().size() > 0)
                                courselist.setPrices(mycourseTable.getPrices());

                            courselist.setLastread(mycourseTable.getLastread());
                            ;
                            freecourse.add(courselist);
                        } else if (Integer.parseInt(courselist.getMrp()) > 0) {
                            courselist.setDelete(0);
                            if (mycourseTable.getPrices() != null && mycourseTable.getPrices().size() > 0)
                                courselist.setPrices(mycourseTable.getPrices());

                            courselist.setLastread(mycourseTable.getLastread());

                            paidcourse.add(courselist);
                        }

                        courselists.add(courselist);
                    }
                    myCourse.setData(courselists);

                    myCourse.setBatchcourse(batchcourse);
                    myCourse.setFreecourse(freecourse);
                    myCourse.setPaid_course(paidcourse);
                    setupViewPager();
                } else {
                    networkCall.NetworkAPICall(API.get_my_courses, "", true, false);
                }

                if (myCourse != null) {
                    if (myCourse.getData() != null && myCourse.getData().size() > 0) {
                        mainCover.setVisibility(View.VISIBLE);
                        no_data_found_RL.setVisibility(View.GONE);
                    } else {
                        mainCover.setVisibility(View.GONE);
                        no_data_found_RL.setVisibility(View.VISIBLE);
                    }
                }

                //networkCall.NetworkAPICall(API.get_my_courses, "", true, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    selectedTab = (String) tab.getText();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


            pullToReferesh.setOnRefreshListener(() -> {
                APITABLE apiMangerTable = myDBClass.getapidao().getapidetail("ut_012", MakeMyExam.userId);
                if ((Long.parseLong(apiMangerTable.getTimestamp()) + Long.parseLong(apiMangerTable.getInterval())) < (MakeMyExam.getTime_server() / 1000)) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        if (imm.isAcceptingText()) {
                            imm.hideSoftInputFromWindow(pullToReferesh.getRootView().getWindowToken(), 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    serac_image.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                    toolbarTitleTV.setVisibility(View.VISIBLE);
                    networkCall.NetworkAPICall(API.get_my_courses, "", true, false);
                    pullToReferesh.setRefreshing(false);
                } else {
                    if (paidFragment != null) {
                        if (freeFragment.paidCourseAdapter != null)
                            freeFragment.paidCourseAdapter.notifidata(myCourse.getFreecourse());

                        if (batchFragment.paidCourseAdapter != null)
                            batchFragment.paidCourseAdapter.notifidata(myCourse.getBatchcourse());

                        if (paidFragment.paidCourseAdapter != null)
                            paidFragment.paidCourseAdapter.notifidata(myCourse.getPaid_course());

                    }
                    pullToReferesh.setRefreshing(false);
                }
            });


            image_back.setOnClickListener(new OnSingleClickListener(() -> {
                Helper.closeKeyboard(this);
                finish();
                return null;
            }));
            backBtn.setOnClickListener(new OnSingleClickListener(() -> {
                if (Helper.isConnected(this)) finish();
                else finishAffinity();
                return null;
            }));

            sort_by.setOnClickListener(new OnSingleClickListener(() -> {
                sort_dialog(sort_by);
                return null;
            }));

            view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    toggleRefreshing(state == ViewPager.SCROLL_STATE_IDLE);
                }
            });


            serac_image.setOnClickListener(v -> {
                serac_image.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                toolbarTitleTV.setVisibility(View.GONE);
                EditText searchEditText = serach_view.findViewById(androidx.appcompat.R.id.search_src_text);

                searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
                searchEditText.setTextColor(getResources().getColor(R.color.blackApp));
                searchEditText.setHintTextColor(getResources().getColor(R.color.black_lite));
                serach_view.setActivated(true);
                serach_view.setIconified(false);
                serach_view.setIconifiedByDefault(false);
                serach_view.setQueryHint("Search My library");
                serach_view.onActionViewExpanded();
                serach_view.clearFocus();
                ImageView searchIcon = (ImageView) serach_view.findViewById(androidx.appcompat.R.id.search_mag_icon);
                ImageView searchIcon_cross = (ImageView) serach_view.findViewById(androidx.appcompat.R.id.search_close_btn);
                searchIcon_cross.setImageResource(R.drawable.white_cross);
                searchIcon.setVisibility(View.GONE);
                searchEditText.setBackgroundColor(Color.TRANSPARENT);

                searchIcon_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchEditText.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        serac_image.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.GONE);
                        toolbarTitleTV.setVisibility(View.VISIBLE);


                        if (paidFragment != null) {
                            if (freeFragment.paidCourseAdapter != null)
                                freeFragment.paidCourseAdapter.notifidata(myCourse.getFreecourse());

                            if (batchFragment.paidCourseAdapter != null)
                                batchFragment.paidCourseAdapter.notifidata(myCourse.getBatchcourse());

                            if (paidFragment.paidCourseAdapter != null)
                                paidFragment.paidCourseAdapter.notifidata(myCourse.getPaid_course());

                        }


                    }
                });

            });

            serach_view.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equalsIgnoreCase("") && view_pager.getAdapter() != null) {

                        Fragment viewPagerFragment = (Fragment) view_pager.getAdapter().instantiateItem(view_pager, view_pager.getCurrentItem());
                        if (viewPagerFragment != null && viewPagerFragment.isAdded()) {

                            if (view_pager.getCurrentItem() == 0) {
                                if (viewPagerFragment instanceof PaidFragment) {
                                    PaidFragment paidFragment = (PaidFragment) viewPagerFragment;
                                    if (paidFragment != null) {
                                        paidFragment.beginSearch(newText);
                                    }
                                }
                            } else if (view_pager.getCurrentItem() == 1) {
                                if (viewPagerFragment instanceof FreeFragment) {
                                    FreeFragment freeFragment = (FreeFragment) viewPagerFragment;
                                    if (freeFragment != null) {
                                        freeFragment.beginSearch(newText);
                                    }
                                }
                            } else {
                                if (viewPagerFragment instanceof BatchFragment) {
                                    BatchFragment batchFragment = (BatchFragment) viewPagerFragment;
                                    if (batchFragment != null) {
                                        batchFragment.beginSearch(newText);
                                    }
                                }
                            }

                        }

                    } else {
                        if (paidFragment != null) {
                            if (freeFragment.paidCourseAdapter != null)
                                freeFragment.paidCourseAdapter.notifidata(myCourse.getFreecourse());

                            if (batchFragment.paidCourseAdapter != null)
                                batchFragment.paidCourseAdapter.notifidata(myCourse.getBatchcourse());

                            if (paidFragment.paidCourseAdapter != null)
                                paidFragment.paidCourseAdapter.notifidata(myCourse.getPaid_course());

                        }
                    }

                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra(Const.NOTIFICATION_CODE)) {
            if (Helper.isConnected(this)) {
                networkCall.NetworkAPICall(API.API_GET_APP_VERSION, "", true, false);

                //super.onBackPressed();
            } else finishAffinity();
        } else {
            super.onBackPressed();
            Helper.closeKeyboard(this);
        }
    }


    public void toggleRefreshing(boolean enabled) {
        if (pullToReferesh != null) {
            pullToReferesh.setEnabled(enabled);
        }
    }


    public void updatesortlistner(sortClickListner listner) {
        this.listner = listner;
    }

    public void updatesortlistner_free(com.utkarshnew.android.home.interfaces.freesortClickListner freesortClickListner) {
        this.freesortClickListner = freesortClickListner;
    }

    public void updatesortlistner_batch(com.utkarshnew.android.home.interfaces.batchsortClickListner batchsortClickListner) {
        this.batchsortClickListner = batchsortClickListner;
    }


    private void sort_dialog(TextView sort_by) {


        if (!myDBClass.getMyCourseDao().isRecordExists(MakeMyExam.userId)) {
            networkCall.NetworkAPICall(API.get_my_courses, "", true, false);

        }

        List<PowerMenuItem> list = new ArrayList<>();
        PowerMenuItem powerMenuItem1 = new PowerMenuItem("A-Z(Title)", false);
        list.add(0, powerMenuItem1);
        PowerMenuItem powerMenuItem2 = new PowerMenuItem("Z-A(Title)", false);
        list.add(1, powerMenuItem2);
        PowerMenuItem powerMenuItem3 = new PowerMenuItem("Date Added", false);
        list.add(2, powerMenuItem3);
        if (!selectedTab.contains("Batch")) {

            PowerMenuItem powerMenuItem = new PowerMenuItem("Last Read", false);
            list.add(3, powerMenuItem);
        }

        powerMenu = new PowerMenu.Builder(this)
                .addItemList(list)
                .setAnimation(MenuAnimation.SHOWUP_BOTTOM_RIGHT) // Animation start point (TOP | LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setWidth(300)
                .setTextColor(getResources().getColor(R.color.black))
                .setMenuColor(Color.WHITE)
                .setSelectedTextColor(Color.WHITE) // sets the color of the selected item text.
                .setSelectedMenuColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setOnMenuItemClickListener(onIconMenuItemClickListener)
                .build();
        powerMenu.showAsAnchorRightBottom(sort_by);


    }

    private void setupViewPager() {
        try {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            paidFragment = new PaidFragment();
            adapter.addFragment(paidFragment, "Paid Course");
            freeFragment = new FreeFragment();
            adapter.addFragment(freeFragment, "Free Course");
            batchFragment = new BatchFragment();
            adapter.addFragment(batchFragment, "Batch Course");
            view_pager.setAdapter(adapter);
            tabLayout.setupWithViewPager(view_pager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                return service.getAppVersion();

            case API.get_my_courses:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setUser_id(MakeMyExam.userId);
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.get_my_courses(getcoursedatadoseStrScr);

            case API.payment_gateway_credentials:
                EncryptionData encryptionData1 = new EncryptionData();
                encryptionData1.setUser_id(MakeMyExam.userId);

                String datajson = new Gson().toJson(encryptionData1);
                String getweay = AES.encrypt(datajson);
                return service.payment_gateway_credentials(getweay);


        }
        return null;
    }


    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_GET_APP_VERSION:
                if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonstring.getJSONObject(Const.DATA);
                    if (data.has("feeds")) {
                        String feeds = data.optString("feeds");
                        MakeMyExam.setFeeds(feeds);
                    }

                }
                super.onBackPressed();
                break;
            case API.payment_gateway_credentials:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject data = jsonstring.getJSONObject(Const.DATA);
                        JSONObject rzpdata = data.getJSONObject("rzp");
                        razorkey = rzpdata.optString("key");
                        SharedPreference.getInstance().putString("key", razorkey);
                    } else {
                        if (!GenericUtils.isEmpty(jsonstring.getString(Const.AUTH_CODE)))
                            RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case API.get_my_courses:
                try {
                    dismissProgressDialog();
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        if (myDBClass.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_012")) {
                            myDBClass.getapidao().update_api_version("ut_012", MakeMyExam.userId, String.valueOf(jsonstring.optLong("time")), String.valueOf(jsonstring.optLong("interval")), String.valueOf(jsonstring.optLong("cd_time")));

                        } else {
                            APITABLE apiMangerTable = new APITABLE();
                            apiMangerTable.setApicode("ut_012");
                            apiMangerTable.setApiname("get_my_courses");
                            apiMangerTable.setInterval(String.valueOf(jsonstring.optLong("interval")));
                            apiMangerTable.setUser_id(MakeMyExam.getUserId());
                            apiMangerTable.setTimestamp(String.valueOf(jsonstring.optLong("time")));
                            apiMangerTable.setCdtimestamp(String.valueOf(jsonstring.optLong("cd_time")));
                            apiMangerTable.setVersion("0.000");
                            myDBClass.getapidao().addUser(apiMangerTable);
                        }

                        mainCover.setVisibility(View.VISIBLE);
                        no_data_found_RL.setVisibility(View.GONE);
                        myCourse = new Gson().fromJson(jsonstring.toString(), MyCourse.class);
                        if (myCourse.getData().size() > 0) {
                            myDBClass.getMyCourseDao().deletedata();
                            ArrayList<Courselist> paidcourse = new ArrayList<>();
                            ArrayList<Courselist> freecourse = new ArrayList<>();
                            ArrayList<Courselist> batchcourse = new ArrayList<>();
                            for (Courselist courselist : myCourse.getData()) {
                                if (!TextUtils.isEmpty(courselist.getBatch_id()) && !courselist.getBatch_id().equalsIgnoreCase("0")) {

                                    if (!myDBClass.getMyCourseDao().isRecordExistsUserId(MakeMyExam.userId, "2", courselist.getId())) {
                                        MycourseTable mycourseTable = new MycourseTable();
                                        mycourseTable.setBatch_id(courselist.getBatch_id());
                                        mycourseTable.setBatchtype("2");
                                        mycourseTable.setCover_image(courselist.getCover_image());
                                        mycourseTable.setId(courselist.getId());

                                        mycourseTable.setExpiry_date("0");

                                        mycourseTable.setPurchase_date(courselist.getPurchase_date());
                                        mycourseTable.setLastread("" + MakeMyExam.getTime_server());
                                        mycourseTable.setTitle(courselist.getTitle());
                                        mycourseTable.setTxn_id(courselist.getTxn_id());
                                        mycourseTable.setMrp(courselist.getMrp());
                                        mycourseTable.setUserid(MakeMyExam.userId);
                                        courselist.setPrices(null);
                                        courselist.setExpiry_date("0");

                                        mycourseTable.setIsExpand(courselist.getCombo_course_ids());

                                        myDBClass.getMyCourseDao().addUser(mycourseTable);
                                        batchcourse.add(courselist);
                                    } else {

                                        courselist.setPrices(null);
                                        courselist.setExpiry_date("0");
                                        batchcourse.add(courselist);
                                    }

                                } else if (courselist.getMrp().equalsIgnoreCase("0")) {
                                    if (!myDBClass.getMyCourseDao().isRecordExistsUserId(MakeMyExam.userId, "0", courselist.getId())) {
                                        MycourseTable mycourseTable = new MycourseTable();
                                        mycourseTable.setBatch_id("");
                                        mycourseTable.setBatchtype("0");
                                        mycourseTable.setCover_image(courselist.getCover_image());
                                        mycourseTable.setId(courselist.getId());
                                        mycourseTable.setExpiry_date(courselist.getExpiry_date());
                                        mycourseTable.setPurchase_date(courselist.getPurchase_date());
                                        mycourseTable.setLastread("" + MakeMyExam.getTime_server());
                                        mycourseTable.setTitle(courselist.getTitle());
                                        mycourseTable.setTxn_id(courselist.getTxn_id());
                                        mycourseTable.setMrp(courselist.getMrp());
                                        mycourseTable.setUserid(MakeMyExam.userId);
                                        mycourseTable.setDelete(1);

                                        if (courselist.getPrices() != null) {
                                            if (courselist.getPrices().size() > 0) {
                                                mycourseTable.setPrices(courselist.getPrices());
                                            }
                                        }
                                        courselist.setDelete(1);
                                        mycourseTable.setIsExpand(courselist.getCombo_course_ids());

                                        myDBClass.getMyCourseDao().addUser(mycourseTable);
                                        freecourse.add(courselist);
                                    } else {
                                        courselist.setDelete(1);
                                        courselist.setLastread(myDBClass.getMyCourseDao().getuser(MakeMyExam.userId, courselist.getId(), "0").getLastread());
                                        freecourse.add(courselist);
                                    }
                                } else if (Integer.parseInt(courselist.getMrp()) > 0) {

                                    if (!myDBClass.getMyCourseDao().isRecordExistsUserId(MakeMyExam.userId, "1", courselist.getId())) {
                                        MycourseTable mycourseTable = new MycourseTable();
                                        mycourseTable.setBatch_id("");
                                        mycourseTable.setBatchtype("1");
                                        mycourseTable.setCover_image(courselist.getCover_image());
                                        mycourseTable.setId(courselist.getId());
                                        mycourseTable.setExpiry_date(courselist.getExpiry_date());
                                        mycourseTable.setPurchase_date(courselist.getPurchase_date());
                                        mycourseTable.setLastread("" + MakeMyExam.getTime_server());
                                        mycourseTable.setTitle(courselist.getTitle());
                                        mycourseTable.setTxn_id(courselist.getTxn_id());
                                        mycourseTable.setMrp(courselist.getMrp());
                                        mycourseTable.setUserid(MakeMyExam.userId);

                                        mycourseTable.setIsExpand(courselist.getCombo_course_ids());
                                        if (courselist.getPrices() != null) {
                                            if (courselist.getPrices().size() > 0) {
                                                mycourseTable.setPrices(courselist.getPrices());
                                            }
                                        }
                                        myDBClass.getMyCourseDao().addUser(mycourseTable);
                                        paidcourse.add(courselist);
                                    } else {
                                        courselist.setLastread(myDBClass.getMyCourseDao().getuser(MakeMyExam.userId, courselist.getId(), "1").getLastread());
                                        paidcourse.add(courselist);
                                    /*    myDBClass.getMyCourseDao().update_course_lastread()
                                        paidcourse.add(myDBClass.getMyCourseDao().getuser(MakeMyExam.userId,courselist.getId(),"1"));*/
                                    }
                                }
                            }
                            myCourse.setBatchcourse(batchcourse);
                            myCourse.setFreecourse(freecourse);
                            myCourse.setPaid_course(paidcourse);

                            if (paidFragment != null) {
                                if (view_pager.getCurrentItem() == 0) {
                                    paidFragment.updatedata(paidcourse);

                                }
                                if (view_pager.getCurrentItem() == 1) {
                                    freeFragment.updatedata(freecourse);

                                }
                                if (view_pager.getCurrentItem() == 2) {
                                    batchFragment.updatedata(batchcourse);
                                }
                            } else {
                                setupViewPager();

                            }
                            if (myCourse != null && myCourse.getData().size() > 0) {
                                List<String> courseidlist = myDBClass.getvideoDownloadao().courseids(MakeMyExam.userId);
                                List<String> courseidlist_userhistory = myDBClass.getuserhistorydao().courseids(MakeMyExam.userId);


                                HashSet<String> courseidlist_userhistory_hashset = new HashSet<>();

                                for (String item : courseidlist_userhistory) {
                                    String[] itemstring = item.split("#");
                                    if (itemstring.length == 2) {
                                        courseidlist_userhistory_hashset.add(itemstring[1]);
                                    } else if (itemstring.length > 0) {
                                        courseidlist_userhistory_hashset.add(itemstring[0]);
                                    }
                                }


                                HashSet<String> coursehassetid = new HashSet<>();
                                for (String item : courseidlist) {
                                    String[] itemstring = item.split("#");
                                    if (itemstring.length == 2) {
                                        coursehassetid.add(itemstring[1]);
                                    } else if (itemstring.length > 0) {
                                        coursehassetid.add(itemstring[0]);
                                    }
                                }

                                HashSet<String> courseids = new HashSet<>();
                                for (int i = 0; i < myCourse.getData().size(); i++) {
                                    courseids.add(myCourse.getData().get(i).getId());
                                    if (myCourse.getData().get(i).getCombo_course_ids() != null && !myCourse.getData().get(i).getCombo_course_ids().equalsIgnoreCase("")) {
                                        List<String> listParts = Arrays.asList(myCourse.getData().get(i).getCombo_course_ids().split(","));
                                        if (listParts.size() > 0)
                                            courseids.addAll(listParts);
                                    }
                                }

                                Log.d("prince", "" + new Gson().toJson(coursehassetid));
                                Log.d("prince", "" + new Gson().toJson(courseids));

                                List<String> base;
                                base = new ArrayList<String>(coursehassetid);
                                base.removeAll(courseids);


                                List<String> base_userhistory;
                                base_userhistory = new ArrayList<String>(courseidlist_userhistory_hashset);
                                base_userhistory.removeAll(courseids);

                                if (base_userhistory != null && base_userhistory.size() > 0) {
                                    for (String courseid : base_userhistory) {
                                        myDBClass.getuserhistorydao().delete(courseid + "#", MakeMyExam.userId);
                                        myDBClass.getuserhistorydao().delete_right("#" + courseid, MakeMyExam.userId);

                                    }

                                }


                                Log.d("prince", "" + new Gson().toJson(base));
                                if (base != null && base.size() > 0) {
                                    for (String downloadCourse_id : base) {
                                        List<VideosDownload> videosDownloads_combo = myDBClass.getvideoDownloadao().getcourse_expire("#" + downloadCourse_id, MakeMyExam.userId);

                                        Log.d("prince", "" + videosDownloads_combo.size());
                                        if (videosDownloads_combo != null && videosDownloads_combo.size() > 0) {
                                            for (VideosDownload videosDownload : videosDownloads_combo) {
                                                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                                File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                                if (file.exists()) {
                                                    file.delete();
                                                }
                                                if (file1.exists()) {
                                                    file1.delete();
                                                }
                                                myDBClass.getvideoDownloadao().delete(videosDownload.getVideo_id(), videosDownload.getCourse_id(), MakeMyExam.userId);
                                            }
                                        }

                                        List<VideosDownload> videosDownloads_combo_new = myDBClass.getvideoDownloadao().getcourse_expire_left(downloadCourse_id + "#", MakeMyExam.userId);
                                        Log.d("prince", "" + videosDownloads_combo_new.size());
                                        if (videosDownloads_combo_new != null && videosDownloads_combo_new.size() > 0) {
                                            for (VideosDownload videosDownload : videosDownloads_combo_new) {
                                                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                                File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                                if (file.exists()) {
                                                    file.delete();
                                                }
                                                if (file1.exists()) {
                                                    file1.delete();
                                                }
                                                myDBClass.getvideoDownloadao().delete(videosDownload.getVideo_id(), videosDownload.getCourse_id(), MakeMyExam.userId);
                                            }
                                        }
                                    }
                                }
                            }

                         /*   if (myCourse!=null && myCourse.getData().size()>0)
                            {
                                List<String> dbcopurseids= myDBClass.getuserhistorydao().courseids(MakeMyExam.userId);
                                for (String getid:dbcopurseids)
                                {
                                    boolean isavalable=false;
                                    for (Courselist courselist:myCourse.getData())
                                    {
                                        if (getid.startsWith(courselist.getId()+"#"))
                                        {
                                            isavalable=true;
                                            break;
                                        }
                                        else
                                        {
                                            isavalable=false;
                                        }
                                    }

                                    if(!isavalable)
                                    {
                                        myDBClass.getuserhistorydao().delete(getid,MakeMyExam.userId);

                                        List<VideosDownload> videosDownloads = myDBClass.getvideoDownloadao().getallcourse_id(getid,MakeMyExam.userId);
                                        if (videosDownloads!=null && videosDownloads.size()>0)
                                        {
                                            for (VideosDownload videosDownload:videosDownloads)
                                            {
                                                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                                File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                                if (file.exists())
                                                {
                                                    file.delete();
                                                }
                                                if (file1.exists())
                                                {
                                                    file1.delete();
                                                }
                                                myDBClass.getvideoDownloadao().delete(videosDownload.getVideo_id(),videosDownload.getCourse_id(),MakeMyExam.userId);
                                            }
                                        }

                                    }
                                }



                                List<String> downloadvideo= myDBClass.getvideoDownloadao().courseids(MakeMyExam.userId);
                                for (String getid:downloadvideo)
                                {
                                    boolean isavalable=false;
                                    for (Courselist courselist:myCourse.getData())
                                    {
                                        if (getid.startsWith(courselist.getId()+"#"))
                                        {
                                            isavalable=true;
                                            break;
                                        }
                                        else
                                        {
                                            isavalable=false;
                                        }
                                    }

                                    if(!isavalable)
                                    {
                                        List<VideosDownload> videosDownloads = myDBClass.getvideoDownloadao().getallcourse_id(getid,MakeMyExam.userId);
                                        if (videosDownloads!=null && videosDownloads.size()>0)
                                        {
                                            for (VideosDownload videosDownload:videosDownloads)
                                            {
                                                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                                File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                                if (file.exists())
                                                {
                                                    file.delete();
                                                }
                                                if (file1.exists())
                                                {
                                                    file1.delete();
                                                }
                                                myDBClass.getvideoDownloadao().delete(videosDownload.getVideo_id(),videosDownload.getCourse_id(),MakeMyExam.userId);
                                            }
                                        }
                                    }
                                }


                            }


*/

                        }
                    } else {
                        myDBClass.getMyCourseDao().deletedata();
                        if (myDBClass.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_012")) {
                            myDBClass.getapidao().update_api_version("ut_012", MakeMyExam.userId, String.valueOf(jsonstring.optLong("time")), String.valueOf(jsonstring.optLong("interval")), String.valueOf(jsonstring.optLong("cd_time")));
                        } else {
                            APITABLE apiMangerTable = new APITABLE();
                            apiMangerTable.setApicode("ut_012");
                            apiMangerTable.setApiname("get_my_courses");
                            apiMangerTable.setInterval(String.valueOf(jsonstring.optLong("interval")));
                            apiMangerTable.setUser_id(MakeMyExam.getUserId());
                            apiMangerTable.setTimestamp(String.valueOf(jsonstring.optLong("time")));
                            apiMangerTable.setCdtimestamp(String.valueOf(jsonstring.optLong("cd_time")));
                            apiMangerTable.setVersion("0.000");
                            myDBClass.getapidao().addUser(apiMangerTable);
                        }

                        mainCover.setVisibility(View.GONE);
                        no_data_found_RL.setVisibility(View.VISIBLE);
                        if (!GenericUtils.isEmpty(jsonstring.getString(Const.AUTH_CODE)))
                            RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);


                        // RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    Log.d("prince", "" + e.getMessage());
                    e.printStackTrace();
                }

        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Helper.dismissProgressDialog();

        if (mainCover != null && no_data_found_RL != null) {
            mainCover.setVisibility(View.GONE);
            no_data_found_RL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            if (view_pager.getCurrentItem() == 1) {
                FreeFragment.paidCourseAdapter.update_payment("" + i + "~!@#$%^&" + s);
            } else if (view_pager.getCurrentItem() == 0) {
                PaidFragment.paidCourseAdapter.update_payment("" + i + "~!@#$%^&" + s);
            } else if (view_pager.getCurrentItem() == 2) {
                BatchFragment.paidCourseAdapter.update_payment("" + i + "~!@#$%^&" + s);
            }
            Toast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        try {
            if (view_pager.getCurrentItem() == 1) {
                FreeFragment.paidCourseAdapter.update_payment(s);
            } else if (view_pager.getCurrentItem() == 0) {
                PaidFragment.paidCourseAdapter.update_payment(s);
            } else if (view_pager.getCurrentItem() == 2) {
                BatchFragment.paidCourseAdapter.update_payment(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
            razorkey = SharedPreference.getInstance().getString("key");
        } else {
            networkCall.NetworkAPICall(API.payment_gateway_credentials, "", false, false);
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (myDBClass.getMyCourseDao().isRecordExists(MakeMyExam.userId)) {
            mycourseTables = myDBClass.getMyCourseDao().getAllUser();
            myCourse = new MyCourse();

            ArrayList<Courselist> courselists = new ArrayList<>();
            ArrayList<Courselist> paidcourse = new ArrayList<>();
            ArrayList<Courselist> freecourse = new ArrayList<>();
            ArrayList<Courselist> batchcourse = new ArrayList<>();
            for (MycourseTable mycourseTable : mycourseTables) {
                Courselist courselist = new Courselist();
                courselist.setId(mycourseTable.getId());
                courselist.setTitle(mycourseTable.getTitle());
                courselist.setBatch_id(mycourseTable.getBatch_id());
                courselist.setCover_image(mycourseTable.getCover_image());
                courselist.setExpiry_date(mycourseTable.getExpiry_date());
                courselist.setPurchase_date(mycourseTable.getPurchase_date());
                courselist.setMrp(mycourseTable.getMrp());
                courselist.setTxn_id(mycourseTable.getTxn_id());

                if (!TextUtils.isEmpty(courselist.getBatch_id()) && !courselist.getBatch_id().equalsIgnoreCase("0")) {
                    courselist.setPrices(null);
                    courselist.setExpiry_date("0");
                    courselist.setDelete(0);
                    batchcourse.add(courselist);
                } else if (courselist.getMrp().equalsIgnoreCase("0")) {
                    courselist.setDelete(1);
                    if (mycourseTable.getPrices() != null && mycourseTable.getPrices().size() > 0)
                        courselist.setPrices(mycourseTable.getPrices());

                    courselist.setLastread(mycourseTable.getLastread());
                    ;
                    freecourse.add(courselist);
                } else if (Integer.parseInt(courselist.getMrp()) > 0) {
                    courselist.setDelete(0);
                    if (mycourseTable.getPrices() != null && mycourseTable.getPrices().size() > 0)
                        courselist.setPrices(mycourseTable.getPrices());

                    courselist.setLastread(mycourseTable.getLastread());


                    paidcourse.add(courselist);
                }

                courselists.add(courselist);
            }
            myCourse.setData(courselists);

            myCourse.setBatchcourse(batchcourse);
            myCourse.setFreecourse(freecourse);
            myCourse.setPaid_course(paidcourse);
            setupViewPager();
        } else {
            networkCall.NetworkAPICall(API.get_my_courses, "", true, false);
        }

        if (myCourse != null) {
            if (myCourse.getData() != null && myCourse.getData().size() > 0) {
                mainCover.setVisibility(View.VISIBLE);
                no_data_found_RL.setVisibility(View.GONE);
            } else {
                mainCover.setVisibility(View.GONE);
                no_data_found_RL.setVisibility(View.VISIBLE);
            }
        } else {
            networkCall.NetworkAPICall(API.get_my_courses, "", true, false);
        }

    }
}
