package com.utkarshnew.android.home.Activity;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.utkarshnew.android.Utils.Helper.getLeftMenu;
import static com.utkarshnew.android.Utils.Helper.gotoActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.Coupon.Activity.CouponActivity;
import com.utkarshnew.android.CourseTransfer.CourseTransferActivity;
import com.utkarshnew.android.Intro.SubCat;
import com.utkarshnew.android.Login.Activity.SplashScreen;
import com.utkarshnew.android.courses.Activity.Concept_newActivity;
import com.utkarshnew.android.courses.Activity.CourseActivity;
import com.utkarshnew.android.courses.Activity.QuizActivity;
import com.utkarshnew.android.courses.Activity.SearchActivity;
import com.utkarshnew.android.courses.Activity.WebFragActivity;
import com.utkarshnew.android.CreateTest.Activity.CreateTestActivity;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.AudioPlayerActivty;
import com.utkarshnew.android.Download.DownloadActivity;
import com.utkarshnew.android.Download.DownloadVideoPlayer;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.feeds.activity.FeedDetails;
import com.utkarshnew.android.feeds.activity.FeedsActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.home.Fragment.Chatboot;
import com.utkarshnew.android.home.Fragment.ContactBottomSheetFragment;
import com.utkarshnew.android.home.adapters.LeftNavAdapter;
import com.utkarshnew.android.home.adapters.TileDataAdapter;
import com.utkarshnew.android.home.adapters.TileItemsAdapter;
import com.utkarshnew.android.home.interfaces.RefreshHome;
import com.utkarshnew.android.home.interfaces.onButtonClicked;
import com.utkarshnew.android.home.model.CourseResponse;
import com.utkarshnew.android.home.model.FilterData.Filterdata;
import com.utkarshnew.android.home.model.FilterData.Subject;
import com.utkarshnew.android.home.model.Menu;
import com.utkarshnew.android.Intro.Activity.IntroActivity;
import com.utkarshnew.android.LiveClass.Activity.LiveClassActivity;
import com.utkarshnew.android.LiveTest.Activity.LivetestActivity;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.Courses.Cards;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.Notification.Notification;
import com.utkarshnew.android.Notification.NotificationDescription;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Profile.ProfileActivity;
import com.utkarshnew.android.purchasehistory.PurchaseHistory;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.AudioTable;
import com.utkarshnew.android.table.CourseDataTable;
import com.utkarshnew.android.table.CourseTypeMasterTable;
import com.utkarshnew.android.table.HomeApiStatusTable;
import com.utkarshnew.android.table.LanguagesTable;
import com.utkarshnew.android.table.MasteAllCatTable;
import com.utkarshnew.android.table.MasterCat;
import com.utkarshnew.android.table.TestTable;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.table.VideoTable;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.UserHistory.UserHistoryActivity;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.LinearLayoutManagerWithSmoothScroller;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.Utils.StickyView.ui.StickyScrollView;
import com.utkarshnew.android.Webview.WebViewActivty;
import com.utkarshnew.android.helpChat.HelpQueryActivity;
import com.utkarshnew.android.helpChat.HelpSupportActivity;
import com.utkarshnew.android.helpChat.model.HelpSupportChatModel;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.pojo.Userinfo.UserData;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.model.InstructionData;
import com.utkarshnew.android.testmodule.model.TestBasicInst;
import com.utkarshnew.android.testmodule.model.TestSectionInst;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Ref;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;


public class HomeActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, onButtonClicked, PopupMenu.OnMenuItemClickListener, RefreshHome {

    private ReviewManager manager;


    NavigationView navigationView;
    DrawerLayout drawer;
    public Toolbar toolbar;
    long backPressed = 0l;
    private boolean backstatus = false;
    public static Activity homeactivity;
    ActionBarDrawerToggle toggle;
    NetworkCall networkCall;
    public RecyclerView navRV;
    RelativeLayout filter;
    private StickyScrollView scrollView;
    RecyclerView tileRv;
    int showcaseCounter = 1;

    LinearLayout homeLL;
    LinearLayout testLL;
    RecyclerView courseListRV;
    TextView tile_tv;
    RelativeLayout no_data_found_RL;
    public String contentType;
    public String contentType_id = "0";
    LinearLayout nav_headerLL;
    LinearLayout my_library;
    TextView profileName, profileEmail, versionnameTV, toolbartitleTV, filterOne, filterTwo, show_case_txt, show_case_bottom;
    public ImageView profileImage, profileImageText, chatboat, downarrowIV;
    UtkashRoom utkashRoom;
    int lang;  // 0 for Hindi , 1 for English
    RelativeLayout titleinnerRL, filter_one_click, filter_two_click, showcaseLL;
    ImageButton chromecast;
    ImageView searchIV, my_library_showcase, live_class_showcase, live_test_showcase, download_showcase, home_showcase;
    List<CourseTypeMasterTable> courseTypeMasterTables = new ArrayList<>();
    List<MasteAllCatTable> masterAllCatTables = new ArrayList<>();
    List<MasterCat> mastercatlist = new ArrayList<>();
    Data userData;
    Data.Preferences  notifcationPrefence = new Data.Preferences();

    ArrayList<MasteAllCatTable> selected_master_cat = new ArrayList<>();
    ArrayList<MasteAllCatTable> selectedsub_all_cat = new ArrayList<>();
    LinearLayout cartLL;

    String mastercatname = "";
    String mastercatid = "";
    String allcatindex = "";
    String allcatindex_id = "";
    String clicktype = "";
    private int pagecount = 1;
    TileDataAdapter tileDataAdapter;
    String allsubcatindex = "";
    String allsubcatindex_id = "";
    String prefencelocale = "";
    ArrayList<Courselist> courselists = new ArrayList<>();
    ArrayList<Cards> cardsArrayList = new ArrayList<>();
    private SwipeRefreshLayout pullToReferesh;
    ArrayList<Data.Preferences> preferencesArrayList = new ArrayList<>();
    // for advanced filter
    public boolean isfilterchanged = false;
    String launguageindex = "", SelectedLaunguageid_prefix = "", launguageindex_prefix = "";
    String subjectindex = "";
    Filterdata filterdata;
    private Dialog quizBasicInfoDialog;
    String SelectedLaunguageid = "";
    String SelectedSubjectid = "";
    LinearLayout liveclass;
    LinearLayout livetest;
    RelativeLayout profileLL,feedLL;
    boolean ispaginationavailable = false;
    ProgressBar paginationLoader;
    int notification_code;
    String time = "";
    String message = "";
    String title = "";
    String url = "";
    String message_target = "";
    String type = "";
    String course_id = "";
    String postid = "";
    String fieldid = "";
    long ts = 0L;
    String coupon_for = "";
    String topicid = "";
    String testname = "";
    String testquestion = "";
    String tiletype = "";
    String tileid = "";
    String revertapi = "";
    FrameLayout forchatbot;
    private String quiz_id = "";
    private Video videodata;
    private FragmentManager fragmentManager;
    public Fragment fragment;
    LinearLayout cvrNotificationCount,cvrFeedCount;
    TextView notificaionCount;
    boolean pullrefresh = false;
    boolean isfilterapply = false;
    String selectedpositionid = "0";
    Button backBtn;
    public boolean isaudio = false;
    List<CourseDataTable> courseDataTables = new ArrayList<>();
    List<LanguagesTable> LanguagesTable = new ArrayList<>();
    String first_attempt = "", result_date = "", submition_type = "", parentid = "";
    private AudioTable audioTable;
    UserHistroyTable userHistroyTable;
    String video_type = "", histroyaudio = "";
    private AppUpdateManager mAppUpdateManager;
    private int RC_APP_UPDATE = 999;
    Data data;
    private int inAppUpdateType;
    private Task<AppUpdateInfo> appUpdateInfoTask;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        utkashRoom = UtkashRoom.getAppDatabase(this);
        setContentView(R.layout.activity_home);
        homeactivity = this;
        networkCall = new NetworkCall(this, this);
        toolbartitleTV = findViewById(R.id.toolbartitleTV);
        pullToReferesh = findViewById(R.id.pullto_referesh);
        notificaionCount = findViewById(R.id.notificaionCount);
        showcaseLL = findViewById(R.id.showcaseLL);
        my_library_showcase = findViewById(R.id.my_library_showcase);
        show_case_txt = findViewById(R.id.show_case_txt);
        show_case_bottom = findViewById(R.id.show_case_bottom);

        live_class_showcase = findViewById(R.id.live_class_showcase);
        live_test_showcase = findViewById(R.id.live_test_showcase);
        download_showcase = findViewById(R.id.download_showcase);
        home_showcase = findViewById(R.id.home_showcase);
        cvrFeedCount = findViewById(R.id.cvrFeedCount);

        cvrNotificationCount = findViewById(R.id.cvrNotificationCount);
        pullToReferesh.setRefreshing(false);
        liveclass = findViewById(R.id.liveclass);
        livetest = findViewById(R.id.livetest);
        filter = findViewById(R.id.filter);
        forchatbot = findViewById(R.id.forchatbot);
        //pullToReferesh.setEnabled(false);
        filterOne = findViewById(R.id.filterOne);
        backBtn = findViewById(R.id.backBtn);
        profileLL = findViewById(R.id.profileLL);
        filter_one_click = findViewById(R.id.filter_one_click);
        filter_two_click = findViewById(R.id.filter_two_click);
        titleinnerRL = findViewById(R.id.titleinnerRL);
        filterTwo = findViewById(R.id.filterTwo);
        downarrowIV = findViewById(R.id.downarrowIV);
        scrollView = findViewById(R.id.scrollView);
        chromecast = findViewById(R.id.chromecast);
        searchIV = findViewById(R.id.searchIV);
        cartLL = findViewById(R.id.cartLL);
        feedLL = findViewById(R.id.feedRL);
        chatboat = findViewById(R.id.chatboat);
        tileRv = findViewById(R.id.tileRv);
        testLL = findViewById(R.id.testLL);
        homeLL = findViewById(R.id.homeLL);
        my_library = findViewById(R.id.my_library);
        nav_headerLL = findViewById(R.id.nav_headerLL);
        courseListRV = findViewById(R.id.courseListRV);
        tile_tv = findViewById(R.id.tile_tv);
        navRV = findViewById(R.id.navRV);
        no_data_found_RL = findViewById(R.id.no_data_found_RL);
        profileImage = findViewById(R.id.profileImage);
        profileImageText = findViewById(R.id.profileImageText);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        versionnameTV = findViewById(R.id.vNameTV);
        paginationLoader = findViewById(R.id.progressBar);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.activity_in, R.anim.activity_out, R.anim.activity_in, R.anim.activity_out);
        fragment = fragmentManager.findFragmentById(R.id.chatboat);

        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        // Returns an intent object that you use to check for an update.
        appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        //lambda operation used for below listener
        installStateUpdatedListener = installState -> {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            }
        };
        mAppUpdateManager.registerListener(installStateUpdatedListener);
        inAppUpdateType = AppUpdateType.FLEXIBLE;
        checkAppUpdate();
        data = SharedPreference.getInstance().getLoggedInUser();
        MakeMyExam.userId = data.getId();
        MakeMyExam.username = data.getName();
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setUserId(MakeMyExam.userId);
        courselists.clear();
        //  preferencesArrayList = data.getPreferences();
        backBtn.setVisibility(View.GONE);

        if (SharedPreference.getInstance().getString("course_detail").equals("")) {
            utkashRoom.getCourseDetaildata().deletedata();
            SharedPreference.getInstance().putString("course_detail", "" + BuildConfig.VERSION_CODE);
        }
        if (getIntent() != null) {
            notification_code = getIntent().getIntExtra(Const.NOTIFICATION_CODE, 0);
            course_id = getIntent().getStringExtra(Const.COURSE_ID);
            postid = getIntent().getStringExtra("postid");
            Log.d("prince", "" + postid);
            fieldid = getIntent().getStringExtra(Const.FileID);
            coupon_for = getIntent().getStringExtra("coupon_for");
            ts = getIntent().getLongExtra("ts", 0L);
            audioTable = (AudioTable) getIntent().getSerializableExtra("audiotable");
            topicid = getIntent().getStringExtra(Const.TOPIC_ID);
            video_type = getIntent().getStringExtra("type");
            histroyaudio = getIntent().getStringExtra("userHistroyTable");
            tiletype = getIntent().getStringExtra(Const.TILE_TYPE);
            tileid = getIntent().getStringExtra(Const.TILE_ID);
            revertapi = getIntent().getStringExtra(Const.REVERT_API);
            type = getIntent().getStringExtra(Const.TYPE);
            title = getIntent().getStringExtra("title");
            message_target = getIntent().getStringExtra("target");
            url = getIntent().getStringExtra("url");
            message = getIntent().getStringExtra("description");
            parentid = getIntent().getStringExtra(Const.shareparentid);
            parentid = parentid == null ? "" : parentid;
            if (notification_code == 90002) {
                hit_api_for_data();
            } else if (notification_code == 1001000) {
                gotoActivity(HomeActivity.this, LiveClassActivity.class, notification_code);
            } else if (notification_code == 1001001) {
                gotoActivity(HomeActivity.this, LivetestActivity.class, notification_code);
                } else if (notification_code == 1001010) {
                gotoActivity(HomeActivity.this, MyLibraryActivty.class, notification_code);
            } else if (notification_code == 1001011) {
                gotoActivity(HomeActivity.this, DownloadActivity.class, notification_code);
            } else if (notification_code == 90001) {
                Intent intent = null;
                if (message_target.equalsIgnoreCase("7")) {
                    notification_code=0;
                    if (MakeMyExam.getFeeds().equalsIgnoreCase("1")) {
                        feedLL.setVisibility(View.VISIBLE);
                        cartLL.setVisibility(View.GONE);
                    } else {
                        feedLL.setVisibility(View.GONE);
                        cartLL.setVisibility(View.VISIBLE);
                    }
                    getLocaleData( getIntent().getStringExtra("master_cat"),   getIntent().getStringExtra("main_cat"),getIntent().getStringExtra("sub_cat"));
                }else
                if (message_target.equalsIgnoreCase("6")) {
                    intent = new Intent(this, WebFragActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("url", url);
                } else if (message_target.equalsIgnoreCase("2")) { //
                    intent = new Intent(this, CourseActivity.class);
                    intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    intent.putExtra(Const.COURSE_ID_MAIN, course_id);
                    intent.putExtra(Const.COURSE_PARENT_ID, "");
                    intent.putExtra(Const.IS_COMBO, false);
                    SharedPreference.getInstance().putString(Const.ID, course_id);
                } else if (message_target.equalsIgnoreCase("5")) {
                    intent = new Intent(this, NotificationDescription.class);
                    intent.putExtra("urlType", "IMAGE");
                    intent.putExtra("title", title);
                    intent.putExtra("url", url);
                    intent.putExtra("description", message);
                } else if (message_target.equalsIgnoreCase("1")) {


                    intent = new Intent(this, WebFragActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("url", message);
                    intent.putExtra("from", "noti");

                } else if (message_target.equalsIgnoreCase("8")) {
                    if (System.currentTimeMillis() > (ts * 1000)) {
                        if (coupon_for.equalsIgnoreCase("1")) {
                            Toast.makeText(HomeActivity.this, "Eligible For All Courses", Toast.LENGTH_SHORT).show();
                            initialzehomepage();
                        } else if (coupon_for.equalsIgnoreCase("0") || coupon_for.equalsIgnoreCase("2")) {
                            intent = new Intent(this, CouponActivity.class);
                        } else {
                            intent = new Intent(this, WebFragActivity.class);
                            intent.putExtra("title", title);
                            intent.putExtra("url", message);
                            intent.putExtra("from", "noti");
                        }
                    } else {
                        if (ts != 0) {
                            DateFormat fe = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
                            long end_millis = ts * 1000;
                            Date de = new Date(end_millis);
                            String edateString = fe.format(de);
                            Toast.makeText(HomeActivity.this, "This coupon Is Available On " + Helper.changeAMPM(edateString), Toast.LENGTH_SHORT).show();

                        }
                        initialzehomepage();
                    }

                } else {
                    intent = new Intent(this, WebFragActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("url", message);
                    intent.putExtra("from", "noti");
                }
                if (intent != null)
                    gotoActivity(intent, this);

            } else if (notification_code == 123 || notification_code == 124 || notification_code == 125) {
                hit_api_for_data();
            } else if (notification_code == 2022) {
                if (postid != null && !TextUtils.isEmpty(postid)) {
                    Intent intent = new Intent(HomeActivity.this, FeedDetails.class);
                    intent.putExtra("postId", postid);
                    gotoActivity(intent, HomeActivity.this);
                }
            } else if (audioTable != null && audioTable.getVideo_id() != null && !audioTable.getVideo_id().equalsIgnoreCase("")) {
                notification_code = 109010;
                if (video_type != null && video_type.equalsIgnoreCase("jw")) {
                    if (histroyaudio != null && !histroyaudio.equalsIgnoreCase("")) {
                        userHistroyTable = new Gson().fromJson(histroyaudio, UserHistroyTable.class);
                        if (!userHistroyTable.getVideo_id().equalsIgnoreCase("") && userHistroyTable.getCourse_id() != null && userHistroyTable.getCourse_id().contains("#")) {
                            isaudio = true;
                            networkCall.NetworkAPICall(API.get_video_link, "", false, false);
                        } else {
                            AudioPlayerService.videoid = "";
                            initialzehomepage();
                        }
                    } else {
                        AudioPlayerService.videoid = "";
                        initialzehomepage();
                    }
                }
            } else if (video_type != null && video_type.contains("youtube_")) {
                notification_code = 109010;
                UserHistroyTable userHistroyTable = utkashRoom.getuserhistorydao().user_history(MakeMyExam.userId, getIntent().getStringExtra("type").split("_")[1]);
                if (userHistroyTable != null) {
                    String img_url = "http://img.youtube.com/vi/" + userHistroyTable.getYoutube_url() + "/0.jpg";
                    if (userHistroyTable.getTileid() == null || userHistroyTable.getTileid().equalsIgnoreCase("")) {
                        Helper.GoToLiveVideoActivity("", this, userHistroyTable.getYoutube_url(), "1", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", img_url, "1", userHistroyTable.getCourse_id(), String.valueOf(1), parentid, tileid, "Video");
                    } else {
                        networkCall.NetworkAPICall(API.get_video_link, "", false, false);

                        // Helper.GoToLiveVideoActivity("", this, userHistroyTable.getYoutube_url(), "1", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", img_url, "1", userHistroyTable.getCourse_id(), String.valueOf(1), parentid, userHistroyTable.getTileid(), "Video");
                    }
                } else {
                    initialzehomepage();
                }
            } else if (video_type != null && video_type.contains("aws")) {
                notification_code = 109010;
                userHistroyTable = utkashRoom.getuserhistorydao().user_history(MakeMyExam.userId, getIntent().getStringExtra("type").split("_")[1]);
                if (userHistroyTable != null) {

                    if (userHistroyTable.getYoutube_url().split("ayush").length == 2) {

                        networkCall.NetworkAPICall(API.get_video_link, "", false, false);

                        //  Helper.GoToLiveAwsVideoActivity("0", "", HomeActivity.this, userHistroyTable.getYoutube_url().split("ayush")[0], "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", userHistroyTable.getYoutube_url().split("ayush")[1], userHistroyTable.getCourse_id(), userHistroyTable.getTileid(), "Video", "1", "", parentid);
                    } else {
                        networkCall.NetworkAPICall(API.get_video_link, "", false, false);

                        //  Helper.GoToLiveAwsVideoActivity("0", "", HomeActivity.this, userHistroyTable.getYoutube_url().split("ayush")[0], "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", "", userHistroyTable.getCourse_id(), userHistroyTable.getTileid(), "Video", "1", "", parentid);
                    }
                } else {
                    initialzehomepage();
                }
            } else if (notification_code == 0) {
                getLocaleData("","", "");

            } else if (notification_code == 101) {
                checkdeeplick();
            }
        }
        setHomeDrawer();
        createMenus();
        hit_api_for_feed_dot();


        chatboat.setOnClickListener(new OnSingleClickListener(() -> {
            backstatus = false;
            forchatbot.setVisibility(View.VISIBLE);
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            if (savedInstanceState == null) {
                if (fragment == null) {
                    fragment = new Chatboot();
                    getSupportFragmentManager().beginTransaction().add(R.id.forchatbot, fragment).commit();
                }
            }
            return null;
        }));


        profileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.isNetworkConnected(HomeActivity.this)) {
                    Helper.showInternetToast(HomeActivity.this);
                    return;
                }
                gotoActivity(HomeActivity.this, Notification.class);

            }
        });

        my_library.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            gotoActivity(this, MyLibraryActivty.class);
            return null;
        }));

        livetest.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            gotoActivity(this, LivetestActivity.class);


            return null;
        }));

        nav_headerLL.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            gotoActivity(this, ProfileActivity.class);


            if (drawer.isDrawerOpen(GravityCompat.START))
                drawer.closeDrawer(GravityCompat.START);
            return null;
        }));


        liveclass.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }


            gotoActivity(HomeActivity.this, LiveClassActivity.class);

            return null;
        }));

        homeLL.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            return null;
        }));


        testLL.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            gotoActivity(this, CreateTestActivity.class, "1");
            return null;
        }));


        filter.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }

            if (isfilterchanged) {
                subjectindex = "";
                launguageindex = "";
                SelectedLaunguageid = "";
                SelectedSubjectid = "";
                is_paid = "";
                filterdata = new Filterdata();
                com.utkarshnew.android.home.model.FilterData.Data data = new com.utkarshnew.android.home.model.FilterData.Data();
                AsyncTask.execute(() -> {
                    ArrayList<Subject> subject;
                    String subjects = utkashRoom.getMasterAllCatDao().getfilteddata(allsubcatindex_id);
                    subject = new Gson().fromJson(subjects, new TypeToken<ArrayList<Subject>>() {
                    }.getType());
                    data.setSubjects(subject);
                    data.setLanguages(LanguagesTable);
                    filterdata.setData(data);
                    runOnUiThread(() -> {
                        openwatchlist_dailog_resource(this, filterdata.getData().getLanguages(), filterdata.getData().getSubjects());

                    });
                });
            } else {
                if (filterdata != null && filterdata.getData() != null && filterdata.getData().getLanguages() != null) {
                    filterdata = new Filterdata();
                    com.utkarshnew.android.home.model.FilterData.Data data = new com.utkarshnew.android.home.model.FilterData.Data();
                    List<Subject> subject;
                    String subjects = utkashRoom.getMasterAllCatDao().getfilteddata(allsubcatindex_id);
                    subject = new Gson().fromJson(subjects, new TypeToken<List<Subject>>() {
                    }.getType());
                    data.setSubjects(subject);
                    data.setLanguages(LanguagesTable);
                    filterdata.setData(data);
                    openwatchlist_dailog_resource(this, filterdata.getData().getLanguages(), filterdata.getData().getSubjects());
                } else {
                    if (filterdata!=null)
                    {
                        openwatchlist_dailog_resource(this, Objects.requireNonNull(filterdata).getData().getLanguages(), filterdata.getData().getSubjects());
                    }
                }
            }
            return null;
        }));


        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                try {
                    View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                    int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
                    if (diff == 0) {
                        if (notification_code == 0 && ispaginationavailable) {
                            if (!isfilterapply) {
                                if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                                    HomeApiStatusTable homeApiStatusTable = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.getUserId());
                                    if (homeApiStatusTable.getStatus().equalsIgnoreCase("true")) {
                                        paginationLoader.setVisibility(View.VISIBLE);
                                        pagecount = Integer.parseInt(homeApiStatusTable.getPage());
                                        pagecount = pagecount + 1;
                                        getCourseData(false);
                                    }
                                }
                            } else {
                                pagecount = pagecount + 1;
                                getCourseData(false);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        chromecast.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            Helper.startCast(HomeActivity.this);

            return null;
        }));

        searchIV.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }
            Intent mycourse = new Intent(HomeActivity.this, SearchActivity.class);
            mycourse.putExtra("couse_type", contentType_id);
            mycourse.putExtra("allsubcatindex", allsubcatindex_id);
            startActivity(mycourse);
            return null;
        }));

        filter_one_click.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }

            PopupMenu popupMenu = new PopupMenu(HomeActivity.this, filterOne, Gravity.LEFT);
            for (int i = 0; i < selected_master_cat.size(); i++) {
                if (allcatindex_id.equalsIgnoreCase(selected_master_cat.get(i).getId())) {
                    Spannable wordtoSpan = new SpannableString(allcatindex);
                    Typeface typeface = ResourcesCompat.getFont(this, R.font.inter_bold);
                    wordtoSpan.setSpan(new StyleSpan(Objects.requireNonNull(typeface).getStyle()), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blackApp)), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    popupMenu.getMenu().add(wordtoSpan);
                } else {
                    popupMenu.getMenu().add(selected_master_cat.get(i).getName());
                }
            }
            clicktype = "2";
            popupMenu.setOnMenuItemClickListener(HomeActivity.this);
            popupMenu.show();
            return null;
        }));


        downarrowIV.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }

            PopupMenu popupMenu = new PopupMenu(HomeActivity.this, toolbartitleTV, Gravity.LEFT);
            for (int i = 0; i < mastercatlist.size(); i++) {
                popupMenu.getMenu().add(mastercatlist.get(i).getCat());
            }
            clicktype = "1";
            popupMenu.setOnMenuItemClickListener(HomeActivity.this);
            popupMenu.show();
            return null;
        }));


        cartLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(HomeActivity.this, DownloadActivity.class);
            }
        });
        if (MakeMyExam.getFeeds().equalsIgnoreCase("1")) {
            feedLL.setVisibility(View.VISIBLE);
            cartLL.setVisibility(View.GONE);
        } else {
            feedLL.setVisibility(View.GONE);
            cartLL.setVisibility(View.VISIBLE);
        }
        feedLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.isNetworkConnected(HomeActivity.this)) {
                    Helper.showInternetToast(HomeActivity.this);
                    return;
                }
                if (data != null && data.getPreferences() != null && data.getPreferences().size() > 0) {
                    if (SharedPreference.getInstance().getInt(Const.FEED_COUNT) > 0) {
                        utkashRoom.getFeedDao().deletedata();
                    }
                    Intent intent = new Intent(HomeActivity.this, FeedsActivity.class);
                    intent.putExtra("mastercatid", mastercatid);
                    intent.putExtra("maincat", allcatindex_id);
                    intent.putExtra("maincatname", allcatindex);
                    intent.putExtra("subcatname", allsubcatindex);
                    intent.putExtra("subcatid", allsubcatindex_id);
                    gotoActivity(intent, HomeActivity.this);
                    SharedPreference.getInstance().putInt(Const.FEED_COUNT, 0);
                    feedAutoRefresh();
                } else {
                    Intent intent = new Intent(HomeActivity.this, IntroActivity.class);
                    gotoActivity(intent, HomeActivity.this);
                }


            }
        });
        titleinnerRL.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }

            PopupMenu popupMenu = new PopupMenu(HomeActivity.this, toolbartitleTV, Gravity.LEFT);
            for (int i = 0; i < mastercatlist.size(); i++) {
                popupMenu.getMenu().add(mastercatlist.get(i).getCat());
            }
            clicktype = "1";
            popupMenu.setOnMenuItemClickListener(HomeActivity.this);
            popupMenu.show();
            return null;
        }));


        filter_two_click.setOnClickListener(new OnSingleClickListener(() -> {
            if (!Helper.isNetworkConnected(HomeActivity.this)) {
                Helper.showInternetToast(HomeActivity.this);
                return null;
            }

            PopupMenu popupMenu = new PopupMenu(HomeActivity.this, filterTwo, Gravity.LEFT);
            for (int i = 0; i < selectedsub_all_cat.size(); i++) {
                if (allsubcatindex_id.equalsIgnoreCase(selectedsub_all_cat.get(i).getId())) {
                    Spannable wordtoSpan = new SpannableString(allsubcatindex);
                    Typeface typeface = ResourcesCompat.getFont(this, R.font.inter_bold);
                    wordtoSpan.setSpan(new StyleSpan(Objects.requireNonNull(typeface).getStyle()), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blackApp)), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    popupMenu.getMenu().add(wordtoSpan);
                } else {
                    popupMenu.getMenu().add(selectedsub_all_cat.get(i).getName());
                }
            }
            clicktype = "3";

            popupMenu.setOnMenuItemClickListener(HomeActivity.this);
            popupMenu.show();
            return null;
        }));


        pullToReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hit_api_for_feed_dot();
                APITABLE apiMangerTable = utkashRoom.getapidao().getapidetail("ut_009", MakeMyExam.userId);
                if (apiMangerTable != null && apiMangerTable.getTimestamp() != null && !apiMangerTable.getTimestamp().equalsIgnoreCase("")) {
                    if ((Long.parseLong(apiMangerTable.getTimestamp()) + Long.parseLong(apiMangerTable.getInterval())) < (System.currentTimeMillis() / 1000)) {
                        utkashRoom.getLaunguages().deletedata();
                        utkashRoom.getMasterAllCatDao().deletedata();
                        utkashRoom.getMastercatDao().deletedata();
                        utkashRoom.getcoursetypemaster().deletedata();
                        initialState();
                        isfilterchanged = false;
                        subjectindex = "";
                        launguageindex = "";
                        SelectedLaunguageid = "";
                        SelectedSubjectid = "";
                        is_paid = "";
                        prefencelocale = "";
                        ispaginationavailable = false;
                        isfilterapply = false;
                        hit_api_master_data();
                        pullToReferesh.setRefreshing(false);
                    } else {
                        pullToReferesh.setRefreshing(false);
                    }
                } else {
                    utkashRoom.getLaunguages().deletedata();
                    utkashRoom.getMasterAllCatDao().deletedata();
                    utkashRoom.getMastercatDao().deletedata();
                    utkashRoom.getcoursetypemaster().deletedata();
                    initialState();
                    isfilterchanged = false;
                    subjectindex = "";
                    launguageindex = "";
                    SelectedLaunguageid = "";
                    SelectedSubjectid = "";
                    is_paid = "";
                    ispaginationavailable = false;
                    isfilterapply = false;
                    hit_api_master_data();
                    pullToReferesh.setRefreshing(false);
                }

            }
        });


        tileRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                toggleRefreshing(newState == SCROLL_STATE_IDLE);
            }
        });

        if (SharedPreference.getInstance().getBoolean("is_showcase")) {
            InitShowcase(++showcaseCounter);
        }

        show_case_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitShowcase(++showcaseCounter);
            }
        });

        show_case_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitShowcase(++showcaseCounter);
            }
        });
        showcaseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void getLocaleData(String master_cat, String main_cat, String sub_cat) {
        if (Helper.isNetworkConnected(HomeActivity.this)) {
            if (utkashRoom==null)
            {
                utkashRoom = UtkashRoom.getAppDatabase(this);
            }

            if (utkashRoom.getMasterAllCatDao().isRecordExistsUserId(MakeMyExam.userId)) {
                courseTypeMasterTables.clear();
                masterAllCatTables.clear();
                mastercatlist.clear();
                LanguagesTable.clear();
                AsyncTask.execute(() -> {
                    try {
                        if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                            courseTypeMasterTables = utkashRoom.getcoursetypemaster().getcourse_typemaster(MakeMyExam.userId);

                            masterAllCatTables = utkashRoom.getMasterAllCatDao().getmaster_allcat(MakeMyExam.userId);

                            mastercatlist = utkashRoom.getMastercatDao().getmastercat(MakeMyExam.userId);

                            userData = SharedPreference.getInstance().getLoggedInUser();

                            if (!main_cat.equalsIgnoreCase(""))
                            {
                                notifcationPrefence.setMain_cat(main_cat);
                                notifcationPrefence.setSub_cat(sub_cat);
                                String masterrtype ="";
                                for (MasteAllCatTable masterMasteAllCatTable : masterAllCatTables) {
                                    if (sub_cat.equalsIgnoreCase(masterMasteAllCatTable.getId())) {
                                        masterrtype =  masterMasteAllCatTable.getMaster_type();
                                        notifcationPrefence.setMaster_type(masterrtype);
                                        prefencelocale="2";
                                        break;
                                    }
                                }

                                if (!masterrtype.equalsIgnoreCase("")) {
                                    for (MasterCat mastercat : mastercatlist) {
                                        if (masterrtype.equalsIgnoreCase(mastercat.getId())) {
                                            mastercatname = mastercat.getCat();
                                            mastercatid = mastercat.getId();
                                            break;
                                        }
                                    }
                                }

                            }else {
                                if (userData != null && userData.getPreferences().size() > 0) {
                                    Data.Preferences preferences = userData.getPreferences().get(0);
                                    for (MasteAllCatTable masterMasteAllCatTable : masterAllCatTables) {
                                        if (preferences.getSub_cat().equalsIgnoreCase(masterMasteAllCatTable.getId())) {
                                            preferences.setMaster_type(masterMasteAllCatTable.getMaster_type());
                                            userData.getPreferences().set(0, preferences);
                                            prefencelocale = "1";
                                            break;

                                        }
                                    }

                                    for (MasterCat mastercat : mastercatlist) {
                                        if (userData.getPreferences().get(0).getMaster_type().equalsIgnoreCase(mastercat.getId())) {
                                            mastercatname = mastercat.getCat();
                                            mastercatid = mastercat.getId();
                                            break;
                                        }
                                    }
                                }

                            }
                            LanguagesTable = utkashRoom.getLaunguages().getLaunguagedetail();

                        }
                        runOnUiThread(() -> {
                            cardsArrayList.clear();
                            for (CourseTypeMasterTable courseTypeMasterTables : courseTypeMasterTables) {
                                cardsArrayList.add(new Cards(courseTypeMasterTables.getId(), courseTypeMasterTables.getName(), "#000000", courseTypeMasterTables.getName(), "0#0#0#0#0#0", "0"));
                            }
                            if (cardsArrayList.size() > 0) {
                                getTileItems(cardsArrayList, 0);
                            }
                            selecteddata();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                hit_api_master_data();
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private void InitShowcase(int i) {
        showcaseLL.setVisibility(View.VISIBLE);
        switch (i) {
            case 1:
                download_showcase.setVisibility(View.VISIBLE);
                download_showcase.setImageResource(R.mipmap.bottom_right_shape);

                break;
            case 2:
                download_showcase.setVisibility(View.GONE);
                home_showcase.setVisibility(View.VISIBLE);
                home_showcase.setImageResource(R.mipmap.bottom_left_shape);

                break;
            case 3:
                show_case_txt.setVisibility(View.INVISIBLE);
                show_case_bottom.setVisibility(View.VISIBLE);
                home_showcase.setVisibility(View.GONE);
                download_showcase.setVisibility(View.GONE);
                my_library_showcase.setVisibility(View.VISIBLE);
                my_library_showcase.setImageResource(R.mipmap.my_library_tut);
                break;
            case 4:
                home_showcase.setVisibility(View.GONE);
                download_showcase.setVisibility(View.GONE);
                my_library_showcase.setImageResource(R.mipmap.live_classes_tut);
                live_class_showcase.setVisibility(View.GONE);

                break;
            case 5:
                home_showcase.setVisibility(View.GONE);
                download_showcase.setVisibility(View.GONE);
                my_library_showcase.setVisibility(View.GONE);
                live_class_showcase.setVisibility(View.GONE);
                live_test_showcase.setVisibility(View.VISIBLE);
                live_test_showcase.setImageResource(R.mipmap.live_test_tut);
                show_case_bottom.setText("Ok");
                break;
            case 6:
                showcaseLL.setVisibility(View.GONE);
                SharedPreference.getInstance().putBoolean("is_showcase", true);
                break;
        }
    }


    private void hit_api_master_data() {
        networkCall.NetworkAPICall(API.master_content, "", true, false);
    }

    private void initialState() {
        pagecount = 1;
        cardsArrayList.clear();
    }

    private void getTileItems(ArrayList<Cards> cardsArrayList, int pos) {
        contentType = cardsArrayList.get(pos).getType() + cardsArrayList.get(pos).getId();
        contentType_id = cardsArrayList.get(pos).getId();
        tileRv.setVisibility(cardsArrayList.size() == 2 ? View.GONE : View.VISIBLE);
        TileItemsAdapter tileItemsAdapter = new TileItemsAdapter(HomeActivity.this, contentType, cardsArrayList, HomeActivity.this);
        tileRv.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManagerWithSmoothScroller.HORIZONTAL, false));
        tileRv.setAdapter(tileItemsAdapter);
        tileRv.setNestedScrollingEnabled(false);
        tileRv.scrollToPosition(pos);
        getTileData(cardsArrayList.get(pos));
    }

    private void setHomeDrawer() {
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.feeds_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);


        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Helper.HideKeyboard(HomeActivity.this);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {


                String time = SharedPreference.getInstance().getString(Const.SERVER_TIME);
                if (!TextUtils.isEmpty(time) && !time.equals("0")) {
                    if (Long.parseLong(time) <= System.currentTimeMillis())
                        networkCall.NetworkAPICall(API.IS_COUPON_AVAILABLE, "", false, false);
                } else {
                    if (SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE).equalsIgnoreCase("") || SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE).equals("0")) {
                        networkCall.NetworkAPICall(API.IS_COUPON_AVAILABLE, "", false, false);
                    } else if (Integer.parseInt(SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE)) > 0) {
                        createMenus();
                    }
                }


                Helper.HideKeyboard(HomeActivity.this);
                super.onDrawerOpened(drawerView);
            }
        };


        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private boolean ApiToHit() {
        return SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE) != null
                && SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE).length() > 0
                && !SharedPreference.getInstance().getString(Const.IS_COUPON_AVAILABLE).equalsIgnoreCase("0");
    }

    public void hideBackbtn() {
        if (toggle != null) toggle.setDrawerIndicatorEnabled(false);
    }

    @Override
    public void onTitleClicked(Cards cards, ArrayList<Cards> tiles, String contentType, int tilePos) {
        try {
            if (!contentType_id.equalsIgnoreCase(cards.getId())) {
                isfilterapply = false;
                getTileItems(tiles, tilePos);

                if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + "0")) {
                    HomeApiStatusTable homeApiStatusTable = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + "0", MakeMyExam.getUserId());
                    if (homeApiStatusTable.getStatus().equalsIgnoreCase("false")) {
                        pagecount = 1;
                        courselists.clear();
                        List<CourseDataTable> courseDataTables = null;
                        if (contentType_id.equalsIgnoreCase("0")) {
                            courseDataTables = utkashRoom.getCoursedata().getcoursedata(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId);
                        } else {
                            courseDataTables = utkashRoom.getCoursedata().getcoursedatawithfilter(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId, cards.getId());
                        }
                        for (CourseDataTable courseDataTable : courseDataTables) {
                            Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                            courselists.add(courselist);
                        }

                        if (courselists.size() > 0) {
                            courseListRV.setVisibility(View.VISIBLE);
                            no_data_found_RL.setVisibility(View.GONE);
                        } else {
                            courseListRV.setVisibility(View.GONE);
                            no_data_found_RL.setVisibility(View.VISIBLE);
                        }
                        tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
                        courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
                        courseListRV.setAdapter(tileDataAdapter);
                        courseListRV.setNestedScrollingEnabled(false);

                    } else {
                        if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                            HomeApiStatusTable homeApiStatusTable2 = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.getUserId());
                            if (homeApiStatusTable2.getStatus().equalsIgnoreCase("false")) {
                                pagecount = 1;
                                courselists.clear();
                                List<CourseDataTable> courseDataTables = null;
                                if (contentType_id.equalsIgnoreCase("0")) {
                                    courseDataTables = utkashRoom.getCoursedata().getcoursedata(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId);
                                } else {
                                    courseDataTables = utkashRoom.getCoursedata().getcoursedatawithfilter(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId, cards.getId());
                                }
                                for (CourseDataTable courseDataTable : courseDataTables) {
                                    Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                                    courselists.add(courselist);
                                }

                                if (courselists.size() > 0) {
                                    courseListRV.setVisibility(View.VISIBLE);
                                    no_data_found_RL.setVisibility(View.GONE);
                                } else {
                                    courseListRV.setVisibility(View.GONE);
                                    no_data_found_RL.setVisibility(View.VISIBLE);
                                }
                                tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
                                courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
                                courseListRV.setAdapter(tileDataAdapter);
                                courseListRV.setNestedScrollingEnabled(false);
                            } else {
                                pagecount = 1;
                                getCourseData(true);
                            }
                        } else {
                            pagecount = 1;
                            getCourseData(true);
                        }
                    }
                } else {
                    pagecount = 1;
                    getCourseData(true);
                }
//                getCourseData(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileData(Cards cards) {
        tile_tv.setText(cards.getTile_name());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MakeMyExam.getFeeds().equalsIgnoreCase("1")) {
            feedLL.setVisibility(View.VISIBLE);
            cartLL.setVisibility(View.GONE);
        } else {
            feedLL.setVisibility(View.GONE);
            cartLL.setVisibility(View.VISIBLE);
        }
        if (utkashRoom == null) {
            utkashRoom = UtkashRoom.getAppDatabase(this);
            utkashRoom.getOpenHelper().getWritableDatabase().enableWriteAheadLogging();
        }

        try {


            //networkCall.NetworkAPICall(API.IS_COUPON_AVAILABLE,"",false,false);


            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // If an in-app update is already running, resume the update.
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                inAppUpdateType,
                                this,
                                RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            });

            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        homeAutoRefresh();
        try {
            Data user = SharedPreference.getInstance().getLoggedInUser();
            Drawable dr = null;
            if (user.getProfilePicture() != null) {
                profileImage.setVisibility(View.VISIBLE);
                Glide.with(HomeActivity.this).load(user.getProfilePicture()).apply(new RequestOptions().placeholder(R.mipmap.course_placeholder))
                        .into(profileImage);

            } else {
                profileImage.setVisibility(View.VISIBLE);
                profileImageText.setVisibility(View.GONE);
                profileImage.setImageResource(R.mipmap.default_pic);
            }

            if (!TextUtils.isEmpty(user.getName()) && !TextUtils.isEmpty(user.getMobile())) {
                profileName.setText(user.getName());
                profileEmail.setText(user.getMobile());
            } else {
                /*profileName.setText("User");
                profileEmail.setText("user@gmail.com");*/
            }
        } catch (Exception e) {
            Log.d("TAG", "onResume: " + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            //In-app update task
            if (resultCode == RESULT_OK) {
                Toast.makeText(HomeActivity.this, "App download starts...", Toast.LENGTH_LONG).show();
            } else if (resultCode != RESULT_CANCELED) {
                //if you want to request the update again just call checkUpdate()
                Toast.makeText(HomeActivity.this, "App download canceled.", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_IN_APP_UPDATE_FAILED) {
                Toast.makeText(HomeActivity.this, "App download failed.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void popupSnackbarForCompleteUpdate() {
        try {
            Snackbar snackbar =
                    Snackbar.make(
                            liveclass,
                            "An update has just been downloaded.",
                            Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("RESTART", view -> mAppUpdateManager.completeUpdate());

            snackbar.show();

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkAppUpdate() {
        try {
            // Checks that the platform will allow the specified type of update.
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            // For a flexible update, use AppUpdateType.FLEXIBLE
                            && appUpdateInfo.isUpdateTypeAllowed(inAppUpdateType)) {
                        // Request the update.

                        try {
                            mAppUpdateManager.startUpdateFlowForResult(
                                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                    appUpdateInfo,
                                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                    inAppUpdateType,
                                    // The current activity making the update request.
                                    HomeActivity.this,
                                    // Include a request code to later monitor this update request.
                                    RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException ignored) {

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createMenus() {

        Data user = SharedPreference.getInstance().getLoggedInUser();
        Drawable dr = null;
        if (user.getProfilePicture() != null) {
            profileImage.setVisibility(View.VISIBLE);
            Glide.with(HomeActivity.this).load(user.getProfilePicture()).apply(new RequestOptions().placeholder(R.mipmap.course_placeholder))
                    .into(profileImage);

        } else {
            profileImage.setVisibility(View.VISIBLE);
            profileImageText.setVisibility(View.GONE);
            profileImage.setImageResource(R.mipmap.default_pic);
        }

        if (!TextUtils.isEmpty(user.getName()) && !TextUtils.isEmpty(user.getMobile())) {
            profileName.setText(user.getName());
            profileEmail.setText(user.getMobile());
        } else {
           /* profileName.setText("User");
            profileEmail.setText("user@gmail.com");*/
        }

        versionnameTV.setText(Html.fromHtml("<b>Version- </b>" + Helper.getVersionName(HomeActivity.this)));

        try {
            final LeftNavAdapter leftNavAdapter = new LeftNavAdapter(this, getLeftMenu());
            navRV.setLayoutManager(new LinearLayoutManager(this));
            navRV.setAdapter(leftNavAdapter);
            leftNavAdapter.setLeftNavAdapterListener(new LeftNavAdapter.LeftNavAdapterListener() {
                @Override
                public void onItemClick(Menu menu) {
                    if (menu.getHaveChild().equalsIgnoreCase("1")) {
                        menu.setExpanded(!menu.isExpanded());
                    }
                    handleLeftMenuClick(menu);
                    leftNavAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLeftMenuClick(Menu menu) {
        if (menu.getType_code() != null) {
            String menuCode = menu.getType_code();
            if (!menuCode.equalsIgnoreCase(Constants.LEFT_NAV_KEY.download)) {
                if (!Helper.isNetworkConnected(this)) {
                    Helper.showInternetToast(HomeActivity.this);
                    return;
                }
            }
            switch (menuCode) {
                case Constants.LEFT_NAV_KEY.home:
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.purchase_histroy:
                    Intent purchase_intent = new Intent(this, PurchaseHistory.class);
                    gotoActivity(purchase_intent, this);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                case Constants.LEFT_NAV_KEY.storage:
                    Intent intent = new Intent(this, IntroActivity.class);
                    gotoActivity(intent, this);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;


                case Constants.LEFT_NAV_KEY.help:
                    getmyQuires();
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.coupon:
                    Intent i = new Intent(this, CouponActivity.class);
                    gotoActivity(i, this);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.download:
                    Intent downloadintent = new Intent(this, DownloadActivity.class);
                    gotoActivity(downloadintent, this);

                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;


                case Constants.LEFT_NAV_KEY.usage_history:
                    Intent intent1 = new Intent(this, UserHistoryActivity.class);
                    startActivity(intent1);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.app_tutorial:
                    Intent app_tutorial = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLoZP2WsNfBSFGsub-jJb_uNW58zQRpDXc"));
                    startActivity(app_tutorial);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.faq:
//                    Intent intent2 = new Intent(this, WebViewActivty.class);
//                    intent2.putExtra("type", "FAQ");
//                    intent2.putExtra("url", "https://utkarsh.com/faqs/");
//                    startActivity(intent2);

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://online.utkarsh.com/faqs"));
                    gotoActivity(browserIntent, this);

                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.course_transfer:
                    Intent intent7 = new Intent(this, CourseTransferActivity.class);
                    gotoActivity(intent7, this);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.contact_us:
                    ContactBottomSheetFragment contactBSF = new ContactBottomSheetFragment();
                    contactBSF.show(getSupportFragmentManager(), contactBSF.getTag());
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.invite_friends:
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Utkarsh");
                        String shareMessage = "\nI recommend you to learn from next-gen Smart courses. Uncover a whole new way of learning with Utkarsh." +
                                " Learn, practice & create custom tests and much more. Download ";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "";
                        shareMessage = shareMessage + " and start learning now";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch (Exception e) {
                        //e.toString();
                    }

//                    Bundle bundleInviteFriends = new Bundle();
//                    bundleInviteFriends.putString(FirebaseAnalytics.Param.ITEM_NAME, "Profile_Screen");
//                    bundleInviteFriends.putString("sign_up_method", "SignUp");
//                    bundleInviteFriends.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "screen");
//                    Intent inviteIntent = new Intent(Intent.ACTION_SEND);
//                    inviteIntent.setType("text/plain");
//                    inviteIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
//                    inviteIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.invitestring));
//                    startActivity(Intent.createChooser(inviteIntent, "Select"));
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.chromecast:
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                case Constants.LEFT_NAV_KEY.tearms_conditions:
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                case Constants.LEFT_NAV_KEY.leave_review:
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                case Constants.LEFT_NAV_KEY.clear_cache:
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                case Constants.LEFT_NAV_KEY.settings:
                    Intent settingsIntent = new Intent(this, SettingsActivity.class);
                    startActivity(settingsIntent);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                case Constants.LEFT_NAV_KEY.Offline_Batch:
                  /*  manager = ReviewManagerFactory.create(this);

                    manager.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                        @Override
                        public void onComplete(@NonNull Task<ReviewInfo> task) {
                            if(task.isSuccessful()){
                                ReviewInfo     reviewInfo = task.getResult();
                                manager.launchReviewFlow(HomeActivity.this, reviewInfo).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(HomeActivity.this, "Rating Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnCompleteListener(task1 -> Toast.makeText(HomeActivity.this, "Review Completed, Thank You!", Toast.LENGTH_SHORT).show());
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(HomeActivity.this, "In-App Request Failed", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    Intent intent4 = new Intent(this, WebViewActivty.class);
                    intent4.putExtra("type", "OFFLINE BATCH");
                    intent4.putExtra("url", "https://courses.utkarsh.com/offlineadmission/home.php");
                    startActivity(intent4);
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                case Constants.LEFT_NAV_KEY.logout:
                    if (SharedPreference.getInstance().getLoggedInUser().getId().equalsIgnoreCase("0")) {
                        Helper.GuestSignOutUser(HomeActivity.this);
                    } else {
                        getLogoutDialog(this, getString(R.string.logout_title), getString(R.string.logout_confirmation_message));
                    }
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;

                case Constants.LEFT_NAV_KEY.chatbot:
                    backstatus = false;
                    forchatbot.setVisibility(View.VISIBLE);
                    if (!Helper.isNetworkConnected(HomeActivity.this)) {
                        Helper.showInternetToast(HomeActivity.this);
                    }
                    if (fragment == null) {
                        fragment = new Chatboot();
                        getSupportFragmentManager().beginTransaction().add(R.id.forchatbot, fragment).commit();
                    }
                    if (drawer.isDrawerOpen(GravityCompat.START))
                        drawer.closeDrawer(GravityCompat.START);
                    break;
                default:
                    //Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void hit_api_for_filter() {
        networkCall.NetworkAPICall(API.get_filter_data, "", true, false);
    }

    public void getLogoutDialog(final Activity ctx, final String title, final String message) {
        DialogUtils.makeDialog(this, title, message,
                getResources().getString(R.string.yes), getResources().getString(R.string.no), true, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {
                        if (Helper.isConnected(HomeActivity.this)) {
                            UserLogout();
                            Helper.SignOutUser(HomeActivity.this);
                        } else {
                            Toast.makeText(HomeActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new DialogUtils.onDialogUtilsCancelClick() {
                    @Override
                    public void onCancelClick() {

                    }
                });
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {

            case API.post_feed_type:
                EncryptionData data = new EncryptionData();
                data.setType("2");
                String datajson = new Gson().toJson(data);
                String encryptjsonnew = AES.encrypt(datajson);
                return service.postFeedDot(encryptjsonnew);


            case API.get_video_link:
                if (video_type != null && video_type.contains("aws")) {
                    EncryptionData encryptionData = new EncryptionData();
                    encryptionData.setName(userHistroyTable.getVideo_id() + "_" + "0" + "_" + "0");
                    encryptionData.setCourse_id(userHistroyTable.getCourse_id().split("#")[0]);
                    encryptionData.setTile_id(userHistroyTable.getTileid());
                    encryptionData.setType(userHistroyTable.getTopicname() == null ? "video" : userHistroyTable.getTopicname());

                    String device_id = (Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    String device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                    device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
                    encryptionData.setDevice_id(device_id);
                    encryptionData.setDevice_name(device_name);


                    String data1 = new Gson().toJson(encryptionData);
                    String encryptjson = AES.encrypt(data1);
                    return service.getVideoLink(encryptjson);
                } else if (isaudio) {
                    EncryptionData encryptionData = new EncryptionData();
                    encryptionData.setName(userHistroyTable.getVideo_id() + "_" + "0" + "_" + "0");
                    encryptionData.setCourse_id(userHistroyTable.getCourse_id().split("#")[0]);
                    encryptionData.setTile_id(userHistroyTable.getTileid());
                    encryptionData.setType(userHistroyTable.getTopicname());
                    String device_id = (Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    String device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                    device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
                    encryptionData.setDevice_id(device_id);
                    encryptionData.setDevice_name(device_name);


                    String data1 = new Gson().toJson(encryptionData);
                    String encryptjson = AES.encrypt(data1);
                    return service.getVideoLink(encryptjson);
                } else {

                    EncryptionData encryptionData = new EncryptionData();
                    encryptionData.setName(videodata.getId() + "_" + "0" + "_" + "0");
                    encryptionData.setCourse_id(videodata.getPayloadData().getCourse_id());
                    encryptionData.setTile_id(videodata.getPayloadData().getTile_id());
                    encryptionData.setType(videodata.getPayloadData().getTile_type());
                    String device_id = (Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                    String device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                    device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
                    encryptionData.setDevice_id(device_id);
                    encryptionData.setDevice_name(device_name);
                    String data1 = new Gson().toJson(encryptionData);
                    String encryptjson = AES.encrypt(data1);
                    return service.getVideoLink(encryptjson);


                }


            case API.IS_COUPON_AVAILABLE:
                return service.IS_COUPON_AVAILABLE("");

            case API.user_logout:
                EncryptionData masterdataencryptionData2 = new EncryptionData();
                masterdataencryptionData2.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
                String masterdataencryptionDatadoseStr3 = new Gson().toJson(masterdataencryptionData2);
                String masterdatadoseStrScr3 = AES.encrypt(masterdataencryptionDatadoseStr3);
                return service.getUserLogout(masterdatadoseStrScr3);


            case API.GET_MY_QUIRES:
                EncryptionData masterdataencryptionData1 = new EncryptionData();
                masterdataencryptionData1.setPage("1");
                masterdataencryptionData1.setCourse_id("0");
                String masterdataencryptionDatadoseStr1 = new Gson().toJson(masterdataencryptionData1);
                String masterdatadoseStrScr1 = AES.encrypt(masterdataencryptionDatadoseStr1);
                return service.getMyHelpQuires(masterdatadoseStrScr1);

            case API.master_content:
                EncryptionData masterdataencryptionData = new EncryptionData();
                masterdataencryptionData.setUser_id(MakeMyExam.getUserId());
                String masterdataencryptionDatadoseStr = new Gson().toJson(masterdataencryptionData);
                String masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr);
                return service.master_content(masterdatadoseStrScr);

            case API.API_GET_INFO_TEST_SERIES:
                EncryptionData masterdatadetailencryptionData1 = new EncryptionData();
                masterdatadetailencryptionData1.setTest_id(quiz_id);
                masterdatadetailencryptionData1.setCourse_id(course_id);
                String getmasterdataencryptionDatadoseStr1 = new Gson().toJson(masterdatadetailencryptionData1);
                String masterdatadoseStrScr1test = AES.encrypt(getmasterdataencryptionDatadoseStr1);
                return service.API_GET_INFO_TEST_SERIES(masterdatadoseStrScr1test);

            case API.API_GET_TEST_INSTRUCTION_DATA:
                EncryptionData masterdatadetailencryptionDatatest = new EncryptionData();
                masterdatadetailencryptionDatatest.setTest_id(quiz_id);
                masterdatadetailencryptionDatatest.setCourse_id(course_id);

                String getmasterdataencryptionDatadoseStrtest = new Gson().toJson(masterdatadetailencryptionDatatest);
                String masterdatadoseStrScrtest = AES.encrypt(getmasterdataencryptionDatadoseStrtest);
                return service.API_GET_TEST_INSTRUCTION_DATA(masterdatadoseStrScrtest);
            case API.get_filter_data:
                EncryptionData getfilterdata = new EncryptionData();
                getfilterdata.setSub_cat_id(allsubcatindex_id);
                String getfilterdataencryptionDatadoseStr = new Gson().toJson(getfilterdata);
                String getfilterdatadoseStrScr = AES.encrypt(getfilterdataencryptionDatadoseStr);
                return service.getFilterData(getfilterdatadoseStrScr);

            case API.get_courses:
                try {
                    checkdeeplick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setCourse_type(contentType_id);
                getcoursedataencryptionData.setSub_cat(allsubcatindex_id);
                getcoursedataencryptionData.setLang(SelectedLaunguageid);
                getcoursedataencryptionData.setPage("" + pagecount);
                getcoursedataencryptionData.setIs_paid(is_paid);
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.get_courses(getcoursedatadoseStrScr);

            case API.API_GET_MASTER_DATA:
                EncryptionData masterdatadetailencryptionData = new EncryptionData();
                masterdatadetailencryptionData.setTile_id(tileid);
                masterdatadetailencryptionData.setType(tiletype);
                masterdatadetailencryptionData.setRevert_api(revertapi);
                masterdatadetailencryptionData.setCourse_id(course_id);
                masterdatadetailencryptionData.setFile_id(fieldid);
                masterdatadetailencryptionData.setParent_id(parentid);
                masterdatadetailencryptionData.setLayer("3");
                masterdatadetailencryptionData.setPage("" + 1);
                masterdatadetailencryptionData.setSubject_id("");
                masterdatadetailencryptionData.setTopic_id(topicid);
                String getmasterdataencryptionDatadoseStr = new Gson().toJson(masterdatadetailencryptionData);
                String masterdatadoseStrScr11 = AES.encrypt(getmasterdataencryptionDatadoseStr);
                return service.getMasterDataVideoThree(masterdatadoseStrScr11);


            case API.mark_as_read:
                EncryptionData readnotification = new EncryptionData();
                readnotification.setId(selectedpositionid);
                String getreadnotification = new Gson().toJson(readnotification);
                String getnotificationdatadoseStrScr = AES.encrypt(getreadnotification);
                return service.setread(getnotificationdatadoseStrScr);
        }
        return null;
    }

    private void checkdeeplick() {

        if (parentid == null || parentid.equalsIgnoreCase("")) {
            if (!SharedPreference.getInstance().getString("maincourseid").equalsIgnoreCase("")) {
                Intent courseList = new Intent(this, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                courseList.putExtra(Const.COURSE_ID_MAIN, SharedPreference.getInstance().getString("maincourseid"));
                courseList.putExtra(Const.COURSE_PARENT_ID, "");
                courseList.putExtra(Const.IS_COMBO, false);
                startActivity(courseList);
                SharedPreference.getInstance().putString("maincourseid", "");
            }
        } else {
            if (!SharedPreference.getInstance().getString("maincourseid").equalsIgnoreCase("")) {
                Intent courseList = new Intent(this, CourseActivity.class);//FRAG_TYPE, Const.SINGLE_COURSE AllCoursesAdapter
                courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                courseList.putExtra(Const.COURSE_ID_MAIN, parentid);
                courseList.putExtra(Const.COURSE_PARENT_ID, "");
                courseList.putExtra(Const.IS_COMBO, false);
                startActivity(courseList);
                SharedPreference.getInstance().putString("maincourseid", "");
            }
        }
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException {

        switch (apitype) {
            case API.post_feed_type:
                try {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONObject datajson = new JSONObject(jsonObject.toString());
                        if (datajson.has("data")) {
                            JSONObject data = datajson.getJSONObject("data");
                            String count = data.has("count") ? data.getString("count") : "0";
                            SharedPreference.getInstance().putInt(Const.FEED_COUNT, Integer.parseInt(count));
                            feedAutoRefresh();
                        }
                    }

                } catch (Exception e) {
                }
                break;

            case API.get_video_link:
                try {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONObject datajson = new JSONObject(jsonObject.toString());
                        if (datajson.has("data")) {
                            String link = datajson.getJSONObject("data").getString("link");
                            String content_type = datajson.getJSONObject("data").optString("content_type");
                            if (userHistroyTable != null && userHistroyTable.getType() != null && userHistroyTable.getType().contains("AWS")) {
                                if (userHistroyTable.getYoutube_url().split("ayush").length == 2) {
                                    if (content_type.equalsIgnoreCase("2")) {
                                        Helper.GoToLiveDrmVideoActivity("0", "", HomeActivity.this, userHistroyTable.getYoutube_url().split("ayush")[0], "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", userHistroyTable.getYoutube_url().split("ayush")[1], userHistroyTable.getCourse_id(), userHistroyTable.getTileid(), "Video", "1", "", parentid, jsonObject.toString());
                                    } else {
                                        Helper.GoToLiveAwsVideoActivity("0", "", HomeActivity.this, userHistroyTable.getYoutube_url().split("ayush")[0], "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", userHistroyTable.getYoutube_url().split("ayush")[1], userHistroyTable.getCourse_id(), userHistroyTable.getTileid(), "Video", "1", "", parentid, jsonObject.toString());

                                    }

                                } else {
                                    if (content_type.equalsIgnoreCase("2")) {
                                        Helper.GoToLiveDrmVideoActivity("0", "", HomeActivity.this, userHistroyTable.getYoutube_url().split("ayush")[0], "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", "", userHistroyTable.getCourse_id(), userHistroyTable.getTileid(), "Video", "1", "", parentid, jsonObject.toString());
                                    } else {
                                        Helper.GoToLiveAwsVideoActivity("0", "", HomeActivity.this, userHistroyTable.getYoutube_url().split("ayush")[0], "0", userHistroyTable.getVideo_id(), userHistroyTable.getVideo_name(), "1", "", userHistroyTable.getCourse_id(), userHistroyTable.getTileid(), "Video", "1", "", parentid, jsonObject.toString());

                                    }
                                }
                            } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("5") && videodata.getLive_status().equalsIgnoreCase("1")) {
                                if (content_type.equalsIgnoreCase("2")) {
                                    Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), HomeActivity.this, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), "", parentid, jsonObject.toString());
                                } else {
                                    Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), HomeActivity.this, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), "", parentid, jsonObject.toString());

                                }

                            } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("0")) {

                                if (content_type.equalsIgnoreCase("")) {
                                    String audio_url = datajson.getJSONObject("data").optString("audio_url");

                                    JSONObject objectcookie = null;
                                    if (datajson.getJSONObject("data").has("cookie") && datajson.getJSONObject("data").optJSONObject("cookie") != null) {
                                        objectcookie = datajson.getJSONObject("data").optJSONObject("cookie");
                                    } else {
                                        objectcookie = new JSONObject("{}");
                                    }
                                    long expire_time = 0;
                                    if (datajson.optLong("expiry_seconds") != 0) {
                                        expire_time = (Long.parseLong(jsonObject.optString("time"))) + datajson.optLong("expiry_seconds");
                                    }


                                    if (datajson.getJSONObject("data").has("bitrate_urls")) {
                                        JSONArray arrJson = datajson.getJSONObject("data").optJSONArray("bitrate_urls");
                                        if (arrJson != null && arrJson.length() > 0) {
                                            ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                            for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                                JSONObject dataObj = arrJson.optJSONObject(i);
                                                UrlObject urlObject = new UrlObject();
                                                urlObject.setTitle(dataObj.optString("title"));
                                                urlObject.setUrl(dataObj.optString("url"));
                                                urlObject.setToken("");
                                                urlObjects.add(urlObject);
                                              /*  UrlObject questionBank = new Gson().fromJson(dataObj.toString(), UrlObject.class);
                                                urlObjects.add(questionBank);*/
                                            }
                                            if (videodata == null)
                                                videodata = new Video();

                                            videodata.setBitrate_urls(urlObjects);
                                        }
                                    }

                                    if (isaudio) {
                                        isaudio = false;
                                        if (audio_url != null && !audio_url.equalsIgnoreCase("")) {
                                            Intent intent = new Intent(HomeActivity.this, AudioPlayerActivty.class);
                                            AudioPlayerService.videoid = "";
                                            intent.putExtra("url", audio_url);
                                            intent.putExtra("videoid", userHistroyTable.getVideo_id());
                                            intent.putExtra("currentpos", audioTable.getAudio_currentpos());
                                            intent.putExtra("video_name", userHistroyTable.getVideo_name());
                                            intent.putExtra("image_url", userHistroyTable.getUrl());

                                            intent.putExtra("tile_id", userHistroyTable.getTileid());
                                            intent.putExtra("tile_type", userHistroyTable.getTopicname());
                                            intent.putExtra("course_id", userHistroyTable.getCourse_id());
                                            intent.putExtra("objectcookie", objectcookie.toString());
                                            Helper.gotoActivity(intent, HomeActivity.this);
                                        }

                                    } else {
                                        isaudio = false;
                                        if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                            Helper.GoToJWVideo_Params_newarrayobject(homeactivity, link, videodata.getId(), videodata.getTitle(), 0L, course_id + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                        } else {
                                            VideoTable videoTable = new VideoTable();
                                            videoTable.setVideo_id(videodata.getId());
                                            videoTable.setVideo_name(videodata.getTitle());
                                            videoTable.setJw_url(link);
                                            videoTable.setVideo_currentpos(0L);
                                            videoTable.setUser_id(MakeMyExam.userId);
                                            videoTable.setCourse_id(course_id + "#");
                                            Helper.GoToJWVideo_Params_newarrayobject(homeactivity, link, videodata.getId(), videodata.getTitle(), 0L, course_id + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                            utkashRoom.getvideoDao().addUser(videoTable);
                                        }
                                    }
                                } else if (content_type.equalsIgnoreCase("2")) {
                                    Helper.GoToLiveDrmVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), HomeActivity.this, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), "", parentid, jsonObject.toString());
                                } else {
                                    Helper.GoToLiveAwsVideoActivity(videodata.getVideo_type(), videodata.getChat_node(), HomeActivity.this, videodata.getFile_url(), videodata.getVideo_type(), videodata.getId(), videodata.getTitle(), "0", videodata.getThumbnail_url(), videodata.getPayloadData().getCourse_id(), videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getIs_chat_lock(), "", parentid, jsonObject.toString());
                                }


                            } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("4")) {
                                if ((videodata.getLive_status().equalsIgnoreCase("1") || videodata.getLive_status().equalsIgnoreCase("0")) && videodata.getOpen_in_app().equalsIgnoreCase("0"))
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                            } else if (videodata != null && videodata.getVideo_type().equalsIgnoreCase("1")) {
                                if (videodata.getOpen_in_app().equalsIgnoreCase("0"))
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + link)));

                            } else {
                                String audio_url = datajson.getJSONObject("data").optString("audio_url");

                                JSONObject objectcookie = null;
                                if (datajson.getJSONObject("data").has("cookie") && datajson.getJSONObject("data").optJSONObject("cookie") != null) {
                                    objectcookie = datajson.getJSONObject("data").optJSONObject("cookie");
                                } else {
                                    objectcookie = new JSONObject("{}");
                                }
                                long expire_time = 0;
                                if (datajson.optLong("expiry_seconds") != 0) {
                                    expire_time = (Long.parseLong(jsonObject.optString("time"))) + datajson.optLong("expiry_seconds");
                                }


                                if (datajson.getJSONObject("data").has("bitrate_urls")) {
                                    JSONArray arrJson = datajson.getJSONObject("data").optJSONArray("bitrate_urls");
                                    if (arrJson != null && arrJson.length() > 0) {
                                        ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                        for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                            JSONObject dataObj = arrJson.optJSONObject(i);
                                            UrlObject urlObject = new UrlObject();
                                            urlObject.setTitle(dataObj.optString("title"));
                                            urlObject.setUrl(dataObj.optString("url"));
                                            urlObjects.add(urlObject);
                                        }
                                        if (videodata == null)
                                            videodata = new Video();

                                        videodata.setBitrate_urls(urlObjects);
                                    }
                                }

                                if (isaudio) {
                                    isaudio = false;
                                    if (audio_url != null && !audio_url.equalsIgnoreCase("")) {
                                        Intent intent = new Intent(HomeActivity.this, AudioPlayerActivty.class);
                                        AudioPlayerService.videoid = "";
                                        intent.putExtra("url", audio_url);
                                        intent.putExtra("videoid", userHistroyTable.getVideo_id());
                                        intent.putExtra("currentpos", audioTable.getAudio_currentpos());
                                        intent.putExtra("video_name", userHistroyTable.getVideo_name());
                                        intent.putExtra("image_url", userHistroyTable.getUrl());

                                        intent.putExtra("tile_id", userHistroyTable.getTileid());
                                        intent.putExtra("tile_type", userHistroyTable.getTopicname());
                                        intent.putExtra("course_id", userHistroyTable.getCourse_id());
                                        intent.putExtra("objectcookie", objectcookie.toString());
                                        Helper.gotoActivity(intent, HomeActivity.this);
                                    }

                                } else {
                                    isaudio = false;
                                    if (utkashRoom.getvideoDao().isvideo_exit(videodata.getId(), MakeMyExam.userId)) {
                                        Helper.GoToJWVideo_Params_newarrayobject(homeactivity, link, videodata.getId(), videodata.getTitle(), 0L, course_id + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                    } else {
                                        VideoTable videoTable = new VideoTable();
                                        videoTable.setVideo_id(videodata.getId());
                                        videoTable.setVideo_name(videodata.getTitle());
                                        videoTable.setJw_url(link);
                                        videoTable.setVideo_currentpos(0L);
                                        videoTable.setUser_id(MakeMyExam.userId);
                                        videoTable.setCourse_id(course_id + "#");
                                        Helper.GoToJWVideo_Params_newarrayobject(homeactivity, link, videodata.getId(), videodata.getTitle(), 0L, course_id + "#", videodata.getPayloadData().getTile_id(), videodata.getPayloadData().getTile_type(), videodata.getBitrate_urls(), expire_time, objectcookie.toString());
                                        utkashRoom.getvideoDao().addUser(videoTable);
                                    }
                                }
                            }


                            isaudio = false;

                        } else {
                            isaudio = false;
                            AudioPlayerService.videoid = "";
                            initialzehomepage();
                            Toast.makeText(this, "url not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        isaudio = false;
                        AudioPlayerService.videoid = "";
                        this.ErrorCallBack(jsonObject.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(this, jsonObject.getString(Const.AUTH_CODE), jsonObject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                }
                break;


            case API.IS_COUPON_AVAILABLE:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    String total_assigned_coupon = Objects.requireNonNull(jsonObject.optJSONObject(Const.DATA)).optString(Const.TOTAL_ASSIGNED_COUPON);

                    // SharedPreference.getInstance().putString(Const.IS_COUPON_AVAILABLE,total_assigned_coupon);

                    if (total_assigned_coupon.equalsIgnoreCase("") || Integer.parseInt(total_assigned_coupon) == 0) {
                        SharedPreference.getInstance().putString(Const.SERVER_TIME, "" + (Long.parseLong(jsonObject.optString("time")) + 120) * 1000);
                    } else {
                        SharedPreference.getInstance().putString(Const.SERVER_TIME, "");
                        SharedPreference.getInstance().putString(Const.IS_COUPON_AVAILABLE, total_assigned_coupon);

                    }

                  /*  if (total_assigned_coupon.equalsIgnoreCase("") && Integer.parseInt(total_assigned_coupon)==0)
                    {
                        SharedPreference.getInstance().putString(Const.SERVER_TIME,jsonObject.optString("time"));

                        SharedPreference.getInstance().putString(Const.IS_COUPON_AVAILABLE,total_assigned_coupon);
                        createMenus();
                    }else {
                        SharedPreference.getInstance().putString(Const.IS_COUPON_AVAILABLE,total_assigned_coupon);
                        SharedPreference.getInstance().putString(Const.SERVER_TIME,jsonObject.optString("time"));
                    }*/
                } else {
                }

                break;

            case API.user_logout:
                break;

            case API.GET_MY_QUIRES:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList<HelpSupportChatModel.DataBean> list = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<HelpSupportChatModel.DataBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        Intent helpSupportIntent = new Intent(this, HelpQueryActivity.class);
                        helpSupportIntent.putExtra("isCourse", false);
                        helpSupportIntent.putExtra("courseDetail", new CourseDetail());
                        startActivity(helpSupportIntent);
                    } else {
                        Intent helpSupportIntent = new Intent(this, HelpSupportActivity.class);
                        helpSupportIntent.putExtra("isCourse", false);
                        helpSupportIntent.putExtra("courseDetail", new CourseDetail());
                        startActivity(helpSupportIntent);
                    }
                } else {
                    Intent helpSupportIntent = new Intent(this, HelpSupportActivity.class);
                    helpSupportIntent.putExtra("isCourse", false);
                    helpSupportIntent.putExtra("courseDetail", new CourseDetail());
                    startActivity(helpSupportIntent);
                }
                break;
            case API.API_GET_INFO_TEST_SERIES:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Long time = jsonObject.optLong("time");

                    TestseriesBase testseriesBase = null;
                    try {
                        Gson gson = new Gson();
                        testseriesBase = gson.fromJson(jsonObject.toString(), TestseriesBase.class);
                        if (testseriesBase.getData().getQuestions() != null && testseriesBase.getData().getQuestions().size() > 0 && lang == 1) {
                            Intent quizView = new Intent(this, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, testname);
                            SharedPreference.getInstance().putString("test_series", jsonObject.toString());

                            quizView.putExtra("course_id", course_id);
                            quizView.putExtra("TOTAL_QUESTIONS", testquestion);
                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", submition_type);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videodata.getEnd_date());
                            quizView.putExtra(Const.LANG, lang);
                            Helper.gotoActivity(quizView, this);
                        } else if (testseriesBase.getData().getQuestionsHindi() != null && testseriesBase.getData().getQuestionsHindi().size() > 0 && lang == 2) {

                            testseriesBase.getData().setQuestions(testseriesBase.getData().getQuestionsHindi());

                            Intent quizView = new Intent(this, TestBaseActivity.class);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, quiz_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, testname);
                            SharedPreference.getInstance().putString("test_series", jsonObject.toString());

                            quizView.putExtra("course_id", course_id);
                            quizView.putExtra("TOTAL_QUESTIONS", testquestion);

                            quizView.putExtra("first_attempt", first_attempt);
                            quizView.putExtra("result_date", result_date);
                            quizView.putExtra("test_submission", submition_type);
                            quizView.putExtra(Const.LANG, lang);
                            quizView.putExtra("time", time);
                            quizView.putExtra("enddate", videodata.getEnd_date());
                            Helper.gotoActivity(quizView, this);
                        } else {
                            initialzehomepage();
                            Toast.makeText(this, "No Question Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        initialzehomepage();
                        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (jsonObject.optString(Const.AUTH_CODE) != null) {
                        if (jsonObject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                            return;
                        }
                        initialzehomepage();
                    }
                    RetrofitResponse.GetApiData(this, jsonObject.has(Const.AUTH_CODE) ? jsonObject.getString(Const.AUTH_CODE) : "", jsonObject.getString(Const.MESSAGE), false);
                }
                break;
            case API.API_GET_TEST_INSTRUCTION_DATA:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    Gson gson = new Gson();
                    JSONObject data1 = jsonObject.getJSONObject("data");
                    InstructionData instructionData = gson.fromJson(data1.toString(), InstructionData.class);
                    showPopUp(instructionData);

                } else if (jsonObject.optString("status").equals(Const.FALSE)) {
                    if (jsonObject.optString(Const.AUTH_CODE) != null) {
                        if (jsonObject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                            return;
                        }
                        initialzehomepage();
                    }
                    RetrofitResponse.GetApiData(this, jsonObject.has(Const.AUTH_CODE) ? jsonObject.getString(Const.AUTH_CODE) : "", jsonObject.getString(Const.MESSAGE), false);
                }
                break;

            case API.mark_as_read:
                try {
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.API_GET_MASTER_DATA:
                try {
                    if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                        Gson gson1 = new Gson();
                        JSONObject data1 = jsonObject.getJSONObject(Const.DATA);
                        ArrayList<Video> videoArrayList = new ArrayList<>();
                        if (videoArrayList == null) videoArrayList = new ArrayList<>();
                        for (int i = 0; i < data1.optJSONArray(Const.LIST).length(); i++) {
                            Video video = gson1.fromJson(data1.optJSONArray(Const.LIST).get(i).toString(), Video.class);
                            videoArrayList.add(video);
                        }
                        if (videoArrayList.size() != 0) {
                            if (notification_code == 125) {

                                inittest(videoArrayList);

                            } else {
                                if (tiletype.equalsIgnoreCase("test")) {
                                    inittest(videoArrayList);


                                } else {
                                    initLivevideo(videoArrayList.get(0));
                                }

                            }
                        }
                    } else {
                        if (jsonObject.optString(Const.AUTH_CODE) != null) {
                            if (jsonObject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                                return;
                            }
                            initialzehomepage();
                        }
                        //ErrorCallBack(jsonobject.optString(Const.MESSAGE), apitype);
                        RetrofitResponse.GetApiData(this, jsonObject.optString(Const.AUTH_CODE), jsonObject.optString(Const.MESSAGE), false);
                    }
                } catch (JSONException e) {
                    if (jsonObject.optString(Const.AUTH_CODE) != null) {
                        if (jsonObject.optString(Const.AUTH_CODE).equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                            return;
                        }
                        initialzehomepage();
                    }
                    e.printStackTrace();
                }

                break;

            case API.master_content:
                try {
                    if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject dataJsonObject = jsonObject.getJSONObject(Const.DATA);

                        try {
                            utkashRoom.getcoursetypemaster().deletedata();
                            utkashRoom.getLaunguages().deletedata();
                            utkashRoom.getMasterAllCatDao().deletedata();
                            utkashRoom.getMastercatDao().deletedata();
                            courseTypeMasterTables.clear();
                            masterAllCatTables.clear();
                            mastercatlist.clear();
                            selectedsub_all_cat.clear();
                            selected_master_cat.clear();
                            if (dataJsonObject.has("languages")) {
                                JSONArray launguage = dataJsonObject.optJSONArray("languages");
                                if (launguage != null && launguage.length() > 0) {

                                    for (int i = 0; i < launguage.length(); i++) {
                                        LanguagesTable languagesTable = new Gson().fromJson(launguage.get(i).toString(), LanguagesTable.class);
                                        LanguagesTable.add(languagesTable);
                                        utkashRoom.getLaunguages().addLaunguage(languagesTable);
                                    }
                                }
                                Log.d("launguage", launguage.toString());
                            }

                            if (dataJsonObject.has("course_type_master")) {
                                JSONArray jsonArray = dataJsonObject.optJSONArray("course_type_master");
                                if (jsonArray != null && jsonArray.length() > 0) {

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        CourseTypeMasterTable courseTypeMasterTable = new Gson().fromJson(jsonArray.get(i).toString(), CourseTypeMasterTable.class);
                                        courseTypeMasterTable.setUser_id(MakeMyExam.userId);
                                        utkashRoom.getcoursetypemaster().addUser(courseTypeMasterTable);
                                        courseTypeMasterTables.add(courseTypeMasterTable);
                                    }
                                }
                            }


                            if (dataJsonObject.has("notification")) {
                                String count = dataJsonObject.getJSONObject("notification").getString("count");
                                SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, Integer.parseInt(count));
                                homeAutoRefresh();
                            }
                            cardsArrayList.clear();

                            for (CourseTypeMasterTable courseTypeMasterTables : courseTypeMasterTables) {
                                cardsArrayList.add(new Cards(courseTypeMasterTables.getId(), courseTypeMasterTables.getName(), "#000000", courseTypeMasterTables.getName(), "0#0#0#0#0#0", "0"));
                            }
                            if (cardsArrayList.size() > 0) {
                                getTileItems(cardsArrayList, 0);
                            }
                            try {
                                if (utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_009")) {
                                    utkashRoom.getapidao().update_api_version("ut_009", MakeMyExam.userId, String.valueOf(jsonObject.optLong("time")), String.valueOf(jsonObject.optLong("interval")), String.valueOf(jsonObject.optLong("cd_time")));
                                } else {
                                    APITABLE apitable = new APITABLE();
                                    apitable.setApicode("ut_009");
                                    apitable.setApiname("master_content");
                                    apitable.setInterval(String.valueOf(jsonObject.optLong("interval")));
                                    apitable.setUser_id(MakeMyExam.userId);
                                    apitable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                                    apitable.setCdtimestamp(String.valueOf(jsonObject.optLong("cd_time")));
                                    apitable.setVersion("0.000");
                                    utkashRoom.getapidao().addUser(apitable);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (dataJsonObject.has("all_cat")) {
                                JSONArray jsonArray = dataJsonObject.optJSONArray("all_cat");
                                if (jsonArray != null && jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        MasteAllCatTable getMasterData_allcat = new Gson().fromJson(jsonArray.get(i).toString(), MasteAllCatTable.class);
                                        getMasterData_allcat.setUser_id(MakeMyExam.userId);
                                        utkashRoom.getMasterAllCatDao().addUser(getMasterData_allcat);
                                        masterAllCatTables.add(getMasterData_allcat);
                                    }
                                }
                            }

                            if (dataJsonObject.has("master_cat")) {
                                JSONArray jsonArray = dataJsonObject.optJSONArray("master_cat");
                                if (jsonArray != null && jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        MasterCat masterCat = new Gson().fromJson(jsonArray.get(i).toString(), MasterCat.class);
                                        masterCat.setUser_id(MakeMyExam.userId);

                                        utkashRoom.getMastercatDao().addUser(masterCat);
                                        mastercatlist.add(masterCat);
                                    }
                                }
                            }

                            userData = SharedPreference.getInstance().getLoggedInUser();

                            if (userData != null && userData.getPreferences().size() > 0) {
                                Data.Preferences preferences = userData.getPreferences().get(0);
                                for (MasteAllCatTable masterMasteAllCatTable : masterAllCatTables) {
                                    if (preferences.getSub_cat().equalsIgnoreCase(masterMasteAllCatTable.getId())) {
                                        preferences.setMaster_type(masterMasteAllCatTable.getMaster_type());
                                        userData.getPreferences().set(0, preferences);
                                        prefencelocale = "1";
                                        break;

                                    }
                                }

                                for (MasterCat mastercat : mastercatlist) {
                                    if (userData.getPreferences().get(0).getMaster_type().equalsIgnoreCase(mastercat.getId())) {
                                        mastercatname = mastercat.getCat();
                                        mastercatid = mastercat.getId();
                                        break;
                                    }
                                }
                            }

                            selecteddata();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        RetrofitResponse.GetApiData(this, jsonObject.has(Const.AUTH_CODE) ? jsonObject.getString(Const.AUTH_CODE) : "", jsonObject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception ex) {

                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
                }
                break;

            case API.get_courses:
                try {
                    if (watchlist != null && watchlist.isShowing()) {
                        watchlist.dismiss();
                    }
                    if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                        if (utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_010")) {

                            utkashRoom.getapidao().update_api_version("ut_010", MakeMyExam.userId, String.valueOf(jsonObject.optLong("time")), String.valueOf(jsonObject.optLong("interval")), String.valueOf(jsonObject.optLong("cd_time")));
                        } else {
                            APITABLE apitable = new APITABLE();
                            apitable.setApicode("ut_010");
                            apitable.setApiname("get_courses");
                            apitable.setInterval(String.valueOf(jsonObject.optLong("interval")));
                            apitable.setUser_id(MakeMyExam.userId);
                            apitable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                            apitable.setCdtimestamp(String.valueOf(jsonObject.optLong("cd_time")));
                            apitable.setVersion("0.000");
                            utkashRoom.getapidao().addUser(apitable);
                        }


                        ispaginationavailable = true;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                        CourseResponse courseResponse = new Gson().fromJson(jsonObject.toString(), CourseResponse.class);
                        if (courseResponse.getData().size() > 0) {
                            ArrayList<Courselist> courseltemp_courselist = new ArrayList<>();
                            courseltemp_courselist.clear();
                            int oldarrysize = 0;
                            courseListRV.setVisibility(View.VISIBLE);
                            no_data_found_RL.setVisibility(View.GONE);

                            if (courseResponse.getData().size() > 0) {
                                if (!isfilterapply) {
                                    if (courseResponse.getData().size() < courseResponse.getLimit()) {
                                        if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                                            ispaginationavailable = false;
                                            utkashRoom.getHomeApiStatusdata().updaterecord(String.valueOf(pagecount), "false", mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.userId);
                                        } else {
                                            ispaginationavailable = false;
                                            HomeApiStatusTable homeApiStatusTable = new HomeApiStatusTable();
                                            homeApiStatusTable.setStatus("false");
                                            homeApiStatusTable.setMain_id(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id);
                                            homeApiStatusTable.setUser_id(MakeMyExam.getUserId());
                                            homeApiStatusTable.setPage(String.valueOf(pagecount));
                                            utkashRoom.getHomeApiStatusdata().addCoursedata(homeApiStatusTable);
                                        }
                                    } else {
                                        if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                                            ispaginationavailable = true;
                                            utkashRoom.getHomeApiStatusdata().updaterecord(String.valueOf(pagecount), "true", mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.userId);
                                        } else {
                                            ispaginationavailable = true;
                                            HomeApiStatusTable homeApiStatusTable = new HomeApiStatusTable();
                                            homeApiStatusTable.setStatus("true");
                                            homeApiStatusTable.setMain_id(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id);
                                            homeApiStatusTable.setUser_id(MakeMyExam.getUserId());
                                            homeApiStatusTable.setPage(String.valueOf(pagecount));
                                            utkashRoom.getHomeApiStatusdata().addCoursedata(homeApiStatusTable);
                                        }
                                    }
                                } else {
                                    if (courseResponse.getData().size() < courseResponse.getLimit()) {
                                        SelectedLaunguageid = "";
                                        SelectedSubjectid = "";
                                        is_paid = "";
                                        ispaginationavailable = false;
                                    } else {
                                        ispaginationavailable = true;
                                    }
                                }

                                if (pagecount == 1) {
                                    courselists.clear();
                                    if (!isfilterapply) {
                                        for (Courselist courselist : courseResponse.getData()) {
                                            if (!utkashRoom.getCoursedata().isRecordExistsUserId(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId, courselist.getId(), courselist.getType_id())) {
                                                CourseDataTable courseDetailTable = new CourseDataTable();
                                                courseDetailTable.setCourse_id(courselist.getId());
                                                courseDetailTable.setMrp(courselist.getMrp());
                                                courseDetailTable.setCover_image(courselist.getCover_image());
                                                courseDetailTable.setTitle(courselist.getTitle());
                                                courseDetailTable.setSubject_id(courselist.getSubject_id());
                                                courseDetailTable.setLang_id(courselist.getLang_id());
                                                courseDetailTable.setValidity(courselist.getValidity());
                                                courseDetailTable.setUser_id(MakeMyExam.getUserId());
                                                courseDetailTable.setCategory(contentType_id);
                                                courseDetailTable.setCourse_sp(courselist.getCourseSp());
                                                courseDetailTable.setType_id(courselist.getType_id());
                                                courseDetailTable.setMain_id(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id);
                                                utkashRoom.getCoursedata().addCoursedata(courseDetailTable);
                                            }
                                        }
                                    }
                                } else {
                                    oldarrysize = courselists.size();
                                    if (!isfilterapply) {
                                        for (Courselist courselist : courseResponse.getData()) {
                                            if (!utkashRoom.getCoursedata().isRecordExistsUserId(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId, courselist.getId(), courselist.getType_id())) {
                                                CourseDataTable courseDetailTable = new CourseDataTable();
                                                courseDetailTable.setCourse_id(courselist.getId());
                                                courseDetailTable.setMrp(courselist.getMrp());
                                                courseDetailTable.setCover_image(courselist.getCover_image());
                                                courseDetailTable.setTitle(courselist.getTitle());
                                                courseDetailTable.setValidity(courselist.getValidity());
                                                courseDetailTable.setUser_id(MakeMyExam.getUserId());
                                                courseDetailTable.setSubject_id(courselist.getSubject_id());
                                                courseDetailTable.setLang_id(courselist.getLang_id());
                                                courseDetailTable.setCategory(contentType_id);
                                                courseDetailTable.setCourse_sp(courselist.getCourseSp());
                                                courseDetailTable.setType_id(courselist.getType_id());
                                                courseDetailTable.setMain_id(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id);
                                                utkashRoom.getCoursedata().addCoursedata(courseDetailTable);
                                            }
                                        }
                                    }
                                }

                                if (!isfilterapply) {
                                    /////

                                   /* List<CourseDataTable> courseDataTables = null;

                                    String queryString = new String();

                                    if (contentType_id.equalsIgnoreCase("0")) {
                                        queryString += "SELECT * FROM CourseDataTable  WHERE mainid = '" + mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "' AND userid ='" + MakeMyExam.userId + "'";

                                    } else {
                                        queryString += "SELECT * FROM CourseDataTable  WHERE mainid = '" + mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "' AND userid ='" + MakeMyExam.userId + "' AND type_id='" + contentType_id + "'";

                                    }
                                    if (!SelectedSubjectid.equalsIgnoreCase("")) {
                                        queryString += " AND subject_id='" + SelectedSubjectid + "'";
                                    }

                                    if (!SelectedLaunguageid.equalsIgnoreCase("")) {
                                        queryString += " AND lang_id='" + SelectedLaunguageid + "'";
                                    }

                                    if (!is_paid.equalsIgnoreCase("")) {

                                        if (is_paid.equalsIgnoreCase("0")) {
                                            queryString += " AND mrp=='" + 0 + "'";
                                        } else if (is_paid.equalsIgnoreCase("1")) {
                                            queryString += " AND mrp>'" + 0 + "'";
                                        }
                                    }

                                    queryString += " ORDER BY autoid" + " ;";

                                    SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
                                    courseDataTables = utkashRoom.getCoursedata().getcoursedatafilterforpaid2(query);
                                    courselists = new ArrayList<>();


                                    for (CourseDataTable courseDataTable : courseDataTables) {
                                        Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                                        courselists.add(courselist);
                                    }

                                    ArrayList<Courselist> newcourselist = new ArrayList<>();
                                    newcourselist = removeDuplicates(courselists);
                                    courselists.clear();
                                    courselists.addAll(newcourselist);*/

                                    courselists.addAll(courseResponse.getData());
                                    ArrayList<Courselist> newcourselist = new ArrayList<>();
                                    newcourselist = removeDuplicates(courselists);
                                    courselists.clear();
                                    if (SelectedLaunguageid_prefix.equalsIgnoreCase("")) {
                                        courselists.addAll(newcourselist);
                                    } else {
                                        for (Courselist courselist : newcourselist) {
                                            if (SelectedLaunguageid_prefix.equalsIgnoreCase(courselist.getLang_id())) {
                                                courselists.add(courselist);
                                            }
                                        }
                                    }

                                    if (courselists.size() > 0) {
                                        courseListRV.setVisibility(View.VISIBLE);
                                        no_data_found_RL.setVisibility(View.GONE);
                                    } else {
                                        courseListRV.setVisibility(View.GONE);
                                        no_data_found_RL.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    courselists.addAll(courseResponse.getData());
                                    ArrayList<Courselist> newcourselist = new ArrayList<>();
                                    newcourselist = removeDuplicates(courselists);
                                    courselists.clear();
                                    courselists.addAll(newcourselist);

                                }

                                if (pagecount == 1) {
                                    tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
                                    courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
                                    courseListRV.setAdapter(tileDataAdapter);
                                    courseListRV.setNestedScrollingEnabled(false);
                                } else {
                                    tileDataAdapter.notifyItemRangeInserted(oldarrysize, courselists.size() - oldarrysize);
                                }
                            }
                        } else {
                            if (courselists != null && pagecount == 1) {
                                if (!isfilterapply) {
                                    HomeApiStatusTable homeApiStatusTable = new HomeApiStatusTable();
                                    homeApiStatusTable.setStatus("false");
                                    homeApiStatusTable.setMain_id(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id);
                                    homeApiStatusTable.setUser_id(MakeMyExam.getUserId());
                                    homeApiStatusTable.setPage(String.valueOf(pagecount));
                                    utkashRoom.getHomeApiStatusdata().addCoursedata(homeApiStatusTable);
                                    courselists.clear();
                                    courseListRV.setVisibility(View.GONE);
                                    no_data_found_RL.setVisibility(View.VISIBLE);
                                } else {
                                    if (courseResponse.getData().size() < courseResponse.getLimit()) {
                                        isfilterapply = false;
                                        courseListRV.setVisibility(View.GONE);
                                        no_data_found_RL.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    } else {
                        if (!isfilterapply) {
                            if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                                utkashRoom.getHomeApiStatusdata().updaterecord(String.valueOf(pagecount), "false", mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.userId);
                            } else {
                                HomeApiStatusTable homeApiStatusTable = new HomeApiStatusTable();
                                homeApiStatusTable.setStatus("false");
                                homeApiStatusTable.setMain_id(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id);
                                homeApiStatusTable.setUser_id(MakeMyExam.getUserId());
                                homeApiStatusTable.setPage(String.valueOf(pagecount));
                                utkashRoom.getHomeApiStatusdata().addCoursedata(homeApiStatusTable);
                            }
                        }

                        isfilterapply = false;
                        ispaginationavailable = false;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                        if (courselists != null && pagecount == 1) {
                            courselists.clear();
                            courseListRV.setVisibility(View.GONE);
                            no_data_found_RL.setVisibility(View.VISIBLE);
                        }
                        if (jsonObject.has(Const.AUTH_CODE)) {
                            RetrofitResponse.GetApiData(this, jsonObject.has(Const.AUTH_CODE) ? jsonObject.getString(Const.AUTH_CODE) : "", jsonObject.getString(Const.MESSAGE), false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case API.get_filter_data:
                try {
                    if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                        filterdata = new Gson().fromJson(jsonObject.toString(), Filterdata.class);
                        if (filterdata.getData().getLanguages() != null && filterdata.getData().getSubjects() != null && isfilterchanged)
                            openwatchlist_dailog_resource(this, filterdata.getData().getLanguages(), filterdata.getData().getSubjects());
                    } else {
                        RetrofitResponse.GetApiData(this, jsonObject.has(Const.AUTH_CODE) ? jsonObject.getString(Const.AUTH_CODE) : "", jsonObject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }


    private void showPopUp(final InstructionData instructionData) {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.popup_basicinfo_quiz_career, null, false);
        //final Dialog quizBasicInfoDialog = new Dialog(activity, R.style.MyDialogTheme);
        final Dialog quizBasicInfoDialog = new Dialog(this, R.style.CustomAlertDialog);
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizBasicInfoDialog.setCanceledOnTouchOutside(true);
        quizBasicInfoDialog.setContentView(v);
        quizBasicInfoDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        quizBasicInfoDialog.show();

        RelativeLayout basicInfoLL, basicInfoQuizLL;
        final TextView quizTitle, quizQuesNumTv, quizTimeTV, quizCorrectValue, quizWrongValue, quizTotalMarks, generalInstrValueTV, reAttempt, languageSpinnerTV, quizsectionValueTV;
        ImageView quizImage;
        final CheckBox check_box;
        Button startQuiz;
        LinearLayout sectionListLL, general_layout, section_time;
        final TestBasicInst testBasicInst = instructionData.getTestBasic();

        basicInfoQuizLL = (RelativeLayout) v.findViewById(R.id.basicInfoDialogLL);
        quizTitle = (TextView) v.findViewById(R.id.quizTitleTV);
        quizImage = (ImageView) v.findViewById(R.id.quizImageIV);
        quizCorrectValue = (TextView) v.findViewById(R.id.marksCorrectValueTV);
        quizWrongValue = (TextView) v.findViewById(R.id.marksWrongValueTV);
        quizTotalMarks = (TextView) v.findViewById(R.id.marksTextValueTV);
        quizQuesNumTv = (TextView) v.findViewById(R.id.numQuesValueTV);
        quizsectionValueTV = (TextView) v.findViewById(R.id.sectionValueTV);
        languageSpinnerTV = (TextView) v.findViewById(R.id.languageSpinnerTV);
        quizTimeTV = (TextView) v.findViewById(R.id.quizTimeValueTV);
        reAttempt = (TextView) v.findViewById(R.id.remarksTV);
        check_box = (CheckBox) v.findViewById(R.id.check_box);
        generalInstrValueTV = (TextView) v.findViewById(R.id.generalInstrValueTV);
        startQuiz = (Button) v.findViewById(R.id.startQuizBtn);
        sectionListLL = (LinearLayout) v.findViewById(R.id.sectionListLL);
        general_layout = (LinearLayout) v.findViewById(R.id.general_layout);
        section_time = (LinearLayout) v.findViewById(R.id.section_time);
        if (testBasicInst.getTest_assets() != null) {
            if (testBasicInst.getTest_assets().getHide_inst_time().equalsIgnoreCase("0")) {
                section_time.setVisibility(View.VISIBLE);
            } else {
                section_time.setVisibility(View.INVISIBLE);
            }
        }

        int total_ques = 0;
        float totalmarks = 0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            total_ques = total_ques + (Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que()));
            totalmarks = totalmarks + ((Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que())) * Float.parseFloat(testSectionInst.getMarksPerQuestion()));
        }
        quizQuesNumTv.setText("" + total_ques);
        quizTotalMarks.setText("" + totalmarks);

        addSectionView(sectionListLL, instructionData);

        if (SharedPreference.getInstance().getBoolean(Const.RE_ATTEMPT)) {
            reAttempt.setVisibility(View.GONE);
        } else
            reAttempt.setVisibility(View.GONE);
        Log.e("langlanth", "" + testBasicInst.getLang_id());
        if (testBasicInst.getLang_id().length() == 3) {
            languageSpinnerTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopMenuForLangauge(languageSpinnerTV, testBasicInst);
                }
            });
        }

        if (testBasicInst.getLang_id().split(",")[0].equals("1")) {
            languageSpinnerTV.setText(getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            lang = Integer.parseInt(testBasicInst.getLang_id().split(",")[0]);
        } else if (testBasicInst.getLang_id().split(",")[0].equals("2")) {
            languageSpinnerTV.setText(getResources().getStringArray(R.array.dialog_choose_language_array)[1]);
            lang = Integer.parseInt(testBasicInst.getLang_id().split(",")[0]);
        }

        quizTitle.setText(testBasicInst.getTestSeriesName());
        // quizQuesNumTv.setText(testBasicInst.getTotalQuestions());
        quizTimeTV.setText(testBasicInst.getTimeInMins());
        // quizTotalMarks.setText(testBasicInst.getTotalMarks());
        if (testBasicInst.getDescription().isEmpty()) {
            general_layout.setVisibility(View.GONE);
        } else {
            general_layout.setVisibility(View.VISIBLE);
            generalInstrValueTV.setText(Html.fromHtml(testBasicInst.getDescription()));

        }
        quizsectionValueTV.setText("" + instructionData.getTestSections().size());

        startQuiz.setTag(testBasicInst);
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testBasicInst.getTotalQuestions().equalsIgnoreCase("0")) {
                    Toast.makeText(HomeActivity.this, "Please add Question.", Toast.LENGTH_SHORT).show();

                } else {
                    if (check_box.isChecked()) {
                        quizBasicInfoDialog.dismiss();
                        quiz_id = testBasicInst.getId();
                        NetworkCall networkCall = new NetworkCall(HomeActivity.this, HomeActivity.this);
                        networkCall.NetworkAPICall(API.API_GET_INFO_TEST_SERIES, "", true, false);
                    } else {
                        Toast.makeText(HomeActivity.this, "Please check following instructions.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        quizBasicInfoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    initialzehomepage();
                    quizBasicInfoDialog.dismiss();
                }
                return true;
            }
        });

    }

    private void addSectionView(LinearLayout sectionListLL, InstructionData instructionData) {
        int count = 0;
        for (TestSectionInst testSectionInst : instructionData.getTestSections()) {
            if (testSectionInst.getOptional_que() == null) {
                testSectionInst.setOptional_que("0");
            }
            sectionListLL.addView(initSectionListView(testSectionInst, count, instructionData.getTestBasic().getTest_assets() == null ? "" : instructionData.getTestBasic().getTest_assets().getHide_inst_time()));
            count++;
        }
    }

    public LinearLayout initSectionListView(TestSectionInst testSectionInst, int tag, String hide_inst_time) {
        List<View> LinearLayoutList = new ArrayList<>();
        LinearLayout v = (LinearLayout) View.inflate(this, R.layout.layout_option_section_list_view, null);
        TextView secNameTV = (TextView) v.findViewById(R.id.secNameTV);
        TextView totQuesTV = (TextView) v.findViewById(R.id.totQuesTV);
        TextView totTimeTV = (TextView) v.findViewById(R.id.totTimeTV);
        TextView maxMarksTV = (TextView) v.findViewById(R.id.maxMarksTV);
        TextView option_count = (TextView) v.findViewById(R.id.option_count);

        TextView markPerQuesTV = (TextView) v.findViewById(R.id.markPerQuesTV);
        TextView negMarkPerQuesTV = (TextView) v.findViewById(R.id.negMarkPerQuesTV);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);

        v.setLayoutParams(lp);


        if (!hide_inst_time.equalsIgnoreCase("")) {
            if (hide_inst_time.equalsIgnoreCase("0")) {
                totTimeTV.setVisibility(View.VISIBLE);
            } else {
                totTimeTV.setVisibility(View.INVISIBLE);
            }
        }
        secNameTV.setText(testSectionInst.getName() + "\n" + "(" + testSectionInst.getSectionPart() + ")");
        totQuesTV.setText(testSectionInst.getTotalQuestions());
        totTimeTV.setText(testSectionInst.getSectionTiming());
        option_count.setText("" + (Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que())));
        maxMarksTV.setText(String.format("%.2f", (Float.parseFloat(testSectionInst.getMarksPerQuestion()) * (Integer.parseInt(testSectionInst.getTotalQuestions()) - Integer.parseInt(testSectionInst.getOptional_que())))));
        markPerQuesTV.setText(testSectionInst.getMarksPerQuestion());
        negMarkPerQuesTV.setText("" + Float.parseFloat(testSectionInst.getNegativeMarks()));

        v.setTag(tag);
        LinearLayoutList.add(v);

        return v;
    }


    public void showPopMenuForLangauge(final View v, TestBasicInst testBasicInst) {
        PopupMenu popup = new PopupMenu(this, v);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) v).setText(item.getTitle().toString());
                if (item.getTitle().toString().equals(getString(R.string.hindi)))
                    lang = 2;
                else if (item.getTitle().toString().equals(getString(R.string.english)))
                    lang = 1;
                Log.e("Language", String.valueOf(lang));
                return false;
            }
        });

        for (int i = 0; i < testBasicInst.getLang_id().split(",").length; i++) {

            if (testBasicInst.getLang_id().split(",")[i].equals("1"))
                popup.getMenu().add(getResources().getStringArray(R.array.dialog_choose_language_array)[0]);
            else if (testBasicInst.getLang_id().split(",")[i].equals("2"))
                popup.getMenu().add(getResources().getStringArray(R.array.dialog_choose_language_array)[1]);
        }
        popup.show();
    }

    private void inittest(Video video) {
        if (notification_code == 125) {
            {
                if (video.getIs_locked().equalsIgnoreCase("1")) {
                    if (parentid == null || parentid.equalsIgnoreCase("")) {
                        Intent intent = new Intent(this, CourseActivity.class);
                        intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                        intent.putExtra(Const.COURSE_PARENT_ID, "");
                        intent.putExtra(Const.IS_COMBO, false);
                        SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                        this.startActivity(intent);
                    } else {
                        Intent intent = new Intent(this, CourseActivity.class);
                        intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                        intent.putExtra(Const.COURSE_ID_MAIN, parentid);
                        intent.putExtra(Const.COURSE_PARENT_ID, "");
                        intent.putExtra(Const.IS_COMBO, false);
                        SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                        this.startActivity(intent);
                    }

                } else {
                    quiz_id = video.getId();
                    first_attempt = "1";
                    result_date = video.getResult_date();
                    SharedPreference.getInstance().putString(Const.ID, course_id);
                    testname = video.getTest_series_name();
                    testquestion = video.getTotal_questions();
                    videodata = video;
                    hit_api_for_iniializetest();
                }
            }
        }
    }

    private void hit_api_for_iniializetest() {
        NetworkCall networkCall = new NetworkCall(this, this);
        networkCall.NetworkAPICall(API.API_GET_TEST_INSTRUCTION_DATA, "", true, false);
    }

    private void initLivevideo(Video video) {
        if (notification_code == 90002) {
            if (video.getIs_locked().equalsIgnoreCase("1")) {
                Intent intent = new Intent(this, CourseActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                intent.putExtra(Const.COURSE_PARENT_ID, "");
                intent.putExtra(Const.IS_COMBO, false);
                SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                Helper.gotoActivity(intent, this);
            } else if (video.getFile_type().equalsIgnoreCase("7")) {
                Intent intent = new Intent(this, Concept_newActivity.class); // AllCourse List
                intent.putExtra("id", video.getId());
                intent.putExtra("name", video.getTitle());
                if (parentid == null) {
                    parentid = "";
                }
                if (parentid.equalsIgnoreCase("")) {
                    intent.putExtra(Const.COURSE_ID, video.getPayloadData().getCourse_id() + "#");
                } else {
                    intent.putExtra(Const.COURSE_ID, parentid + "#" + video.getPayloadData().getCourse_id());
                }
                intent.putExtra("modified", video.getModified());
                intent.putExtra(Const.TILE_ID, video.getPayloadData().getTile_id());
                Helper.gotoActivity(intent, this);
            } else if (video.getFile_type().equalsIgnoreCase("8")) {
                Intent intent4 = new Intent(this, WebViewActivty.class);
                intent4.putExtra("type", video.getTitle());
                intent4.putExtra("url", video.getFile_url());
                intent4.putExtra("video_id", video.getId());
                intent4.putExtra("link", video.getFile_type());
                intent4.putExtra("course_id", video.getPayloadData().getCourse_id());
                Helper.gotoActivity(intent4, this);
            } else if (video.getFile_type().equalsIgnoreCase("1")) {
                boolean isDownload = false;
                if (!TextUtils.isEmpty(video.getIs_download_available()) && video.getIs_download_available().equalsIgnoreCase("1")) {
                    isDownload = true;
                } else {
                    isDownload = false;
                }
                Helper.GoToWebViewPDFActivity(this, video.getId(), video.getFile_url(), isDownload, video.getTitle(), video.getPayloadData().getCourse_id());
            } else {
                if (video.getVideo_type().equalsIgnoreCase("5")) {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {
                        if (parentid == null || parentid.equalsIgnoreCase("")) {
                            Intent intent = new Intent(this, CourseActivity.class);
                            intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                            intent.putExtra(Const.COURSE_PARENT_ID, "");
                            intent.putExtra(Const.IS_COMBO, false);
                            SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(this, CourseActivity.class);
                            intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            intent.putExtra(Const.COURSE_ID_MAIN, parentid);
                            intent.putExtra(Const.COURSE_PARENT_ID, "");
                            intent.putExtra(Const.IS_COMBO, false);
                            SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                            startActivity(intent);
                        }

                    } else {
                        if (video.getLive_status().equalsIgnoreCase("0")) {
                            Toast.makeText(this, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                            initialzehomepage();
                        } else if (video.getLive_status().equalsIgnoreCase("1")) {

                            videodata = video;
                            NetworkCall networkCall = new NetworkCall(this, this);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                            // Helper.GoToLiveAwsVideoActivity(video.getVideo_type(), video.getChat_node(), HomeActivity.this, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getPayloadData().getCourse_id(), video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type(), video.getIs_chat_lock(), "", parentid);
                        } else if (video.getLive_status().equalsIgnoreCase("2")) {
                            Toast.makeText(this, "Live Class is ended", Toast.LENGTH_SHORT).show();
                            initialzehomepage();
                        } else if (video.getLive_status().equalsIgnoreCase("3")) {
                            Toast.makeText(this, "Live Class is cancelled", Toast.LENGTH_SHORT).show();
                            initialzehomepage();
                        }
                    }
                } else if (video.getVideo_type().equalsIgnoreCase("0")) {
                    videodata = video;
                    NetworkCall networkCall = new NetworkCall(this, this);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);


                    //    Helper.GoToLiveAwsVideoActivity(video.getVideo_type(), video.getChat_node(), HomeActivity.this, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getPayloadData().getCourse_id(), video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type(), video.getIs_chat_lock(), "", parentid);
                } else if (video.getVideo_type().equalsIgnoreCase("6") && video.getFile_type().equalsIgnoreCase("3")) {
                    jwvideo(video);
                } else {
                    if (video.getIs_locked().equalsIgnoreCase("1")) {

                        if (parentid == null || parentid.equalsIgnoreCase("")) {
                            Intent intent = new Intent(this, CourseActivity.class);
                            intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                            intent.putExtra(Const.COURSE_PARENT_ID, "");
                            intent.putExtra(Const.IS_COMBO, false);
                            SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(this, CourseActivity.class);
                            intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                            intent.putExtra(Const.COURSE_ID_MAIN, parentid);
                            intent.putExtra(Const.COURSE_PARENT_ID, "");
                            intent.putExtra(Const.IS_COMBO, false);
                            SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                            startActivity(intent);
                        }
                    } else {
                        if (video.getOpen_in_app() != null && video.getOpen_in_app().equalsIgnoreCase("1")) {

                            if (video.getVideo_type().equalsIgnoreCase("4")) {
                                if (video.getLive_status().equalsIgnoreCase("0")) {
                                    Toast.makeText(this, "Live Class will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(video.getStart_date()) * 1000)), Toast.LENGTH_SHORT).show();
                                    initialzehomepage();
                                } else if (video.getLive_status().equalsIgnoreCase("1")) {
                                    Helper.GoToLiveVideoActivity(video.getChat_node(), HomeActivity.this, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), "", parentid, video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                                } else if (video.getLive_status().equalsIgnoreCase("2")) {
                                    Toast.makeText(this, "Live Class is ended", Toast.LENGTH_SHORT).show();
                                    initialzehomepage();
                                } else if (video.getLive_status().equalsIgnoreCase("3")) {
                                    Toast.makeText(this, "Live Class is cancelled", Toast.LENGTH_SHORT).show();
                                    initialzehomepage();
                                }
                            } else if (video.getVideo_type().equalsIgnoreCase("1")) {
                                Helper.GoToLiveVideoActivity(video.getChat_node(), HomeActivity.this, video.getFile_url(), video.getVideo_type(), video.getId(), video.getTitle(), "0", video.getThumbnail_url(), video.getIs_chat_lock(), video.getPayloadData().getCourse_id(), "", parentid, video.getPayloadData().getTile_id(), video.getPayloadData().getTile_type());
                            }
                        } else {

                            videodata = video;
                            NetworkCall networkCall = new NetworkCall(this, this);
                            networkCall.NetworkAPICall(API.get_video_link, "", true, false);


//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getFile_url())));
                        }
                    }
                }
            }


        } else if (notification_code == 123) {
            if (video.getIs_locked().equalsIgnoreCase("1")) {
                if (parentid == null || parentid.equalsIgnoreCase("")) {
                    Intent intent = new Intent(this, CourseActivity.class);
                    intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                    intent.putExtra(Const.COURSE_PARENT_ID, "");
                    intent.putExtra(Const.IS_COMBO, false);
                    SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                    Helper.gotoActivity(intent, this);
                } else {
                    Intent intent = new Intent(this, CourseActivity.class);
                    intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    intent.putExtra(Const.COURSE_ID_MAIN, parentid);
                    intent.putExtra(Const.COURSE_PARENT_ID, "");
                    intent.putExtra(Const.IS_COMBO, false);
                    SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                    Helper.gotoActivity(intent, this);
                }

            } else {
                jwvideo(video);
            }
        } else if (notification_code == 124) {

            if (video.getIs_locked().equalsIgnoreCase("1")) {

                if (parentid == null || parentid.equalsIgnoreCase("")) {
                    Intent intent = new Intent(this, CourseActivity.class);
                    intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    intent.putExtra(Const.COURSE_ID_MAIN, video.getPayloadData().getCourse_id());
                    intent.putExtra(Const.COURSE_PARENT_ID, "");
                    intent.putExtra(Const.IS_COMBO, false);
                    SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                    Helper.gotoActivity(intent, this);
                } else {
                    Intent intent = new Intent(this, CourseActivity.class);
                    intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                    intent.putExtra(Const.COURSE_ID_MAIN, parentid);
                    intent.putExtra(Const.COURSE_PARENT_ID, "");
                    intent.putExtra(Const.IS_COMBO, false);
                    SharedPreference.getInstance().putString(Const.ID, video.getPayloadData().getCourse_id());
                    Helper.gotoActivity(intent, this);
                }


            } else {
                if (!video.getFile_type().equalsIgnoreCase("7"))
                    if (TextUtils.isEmpty(video.getFile_url())) {
                        Toast.makeText(this, "No pdf found!", Toast.LENGTH_SHORT).show();
                        initialzehomepage();
                        return;
                    }
                if (video.getFile_type().equalsIgnoreCase("8")) {
                    Intent intent4 = new Intent(this, WebViewActivty.class);
                    intent4.putExtra("type", video.getTitle());
                    intent4.putExtra("url", video.getFile_url());
                    intent4.putExtra("video_id", video.getId());
                    intent4.putExtra("link", video.getFile_type());
                    intent4.putExtra("course_id", video.getPayloadData().getCourse_id());

                    Helper.gotoActivity(intent4, this);
                }
                if (video.getFile_type().equalsIgnoreCase("7")) {
//                        Intent intent4 = new Intent(this, WebViewActivty.class);
//                        intent4.putExtra("type", video.getTitle());
//                        intent4.putExtra("url", video.getDescription());
//                        Helper.gotoActivity(intent4,this);

                    Intent intent = new Intent(this, Concept_newActivity.class); // AllCourse List
                    intent.putExtra("id", video.getId());
                    intent.putExtra("name", video.getTitle());
                    if (parentid == null) {
                        parentid = "";
                    }
                    if (parentid.equalsIgnoreCase("")) {
                        intent.putExtra(Const.COURSE_ID, video.getPayloadData().getCourse_id() + "#");
                    } else {
                        intent.putExtra(Const.COURSE_ID, parentid + "#" + video.getPayloadData().getCourse_id());
                    }
                    intent.putExtra("modified", video.getModified());

                    intent.putExtra(Const.TILE_ID, video.getPayloadData().getTile_id());
                    Helper.gotoActivity(intent, this);
                } else if (video.getFile_type().equalsIgnoreCase("1")) {
                    boolean isDownload = false;
                    if (!TextUtils.isEmpty(video.getIs_download_available()) && video.getIs_download_available().equalsIgnoreCase("1")) {
                        isDownload = true;
                    } else {
                        isDownload = false;
                    }
                    Helper.GoToWebViewPDFActivity(this, video.getId(), video.getFile_url(), isDownload, video.getTitle(), video.getPayloadData().getCourse_id());
                }
            }
        }
    }

    private void jwvideo(Video videoData) {
        if (utkashRoom.getvideoDownloadao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
            VideosDownload videoDownload = utkashRoom.getvideoDownloadao().getvideo_byuserid(videoData.getId(), MakeMyExam.userId);
            if (videoDownload.getIs_complete().equalsIgnoreCase("1")) {
                Intent i = new Intent(this, DownloadVideoPlayer.class);
                i.putExtra("video_name", videoDownload.getVideo_name());
                i.putExtra("video_id", videoDownload.getVideo_id());
                i.putExtra("current_pos", videoDownload.getVideoCurrentPosition());
                i.putExtra("video", videoDownload.getVideo_history());
                i.putExtra("video_time", videoDownload.getVideotime());
                i.putExtra("video_time", videoDownload.getCourse_id());
                Helper.gotoActivity(i, this);
            } else {
                videodata = videoData;
                networkCall.NetworkAPICall(API.get_video_link, "", false, false);

                /*if (utkashRoom.getvideoDao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
                    Helper.GoToJWVideo_Params(this, mediaId, videoData.getId(), videoData.getTitle(), utkashRoom.getvideoDao().getuser(videoData.getId(), MakeMyExam.userId).getVideo_currentpos(), videoData.getPayloadData().getCourse_id());
                } else {
                    VideoTable videoTable = new VideoTable();
                    videoTable.setVideo_id(videoData.getId());
                    videoTable.setVideo_name(videoData.getTitle());
                    videoTable.setJw_url(mediaId);
                    videoTable.setVideo_currentpos(0L);
                    videoTable.setUser_id(MakeMyExam.userId);
                    videoTable.setCourse_id(videoData.getPayloadData().getCourse_id());

                    utkashRoom.getvideoDao().addUser(videoTable);
                    Helper.GoToJWVideo_Params(this, mediaId, videoData.getId(), videoData.getTitle(), 0L, videoData.getPayloadData().getCourse_id());
                }*/
            }
        } else {
            videodata = videoData;
            networkCall.NetworkAPICall(API.get_video_link, "", false, false);



           /* if (utkashRoom.getvideoDao().isvideo_exit(videoData.getId(), MakeMyExam.userId)) {
                Helper.GoToJWVideo_Params(this, mediaId, videoData.getId(), videoData.getTitle(), utkashRoom.getvideoDao().getuser(videoData.getId(), MakeMyExam.userId).getVideo_currentpos(), videoData.getPayloadData().getCourse_id());
            } else {
                VideoTable videoTable = new VideoTable();
                videoTable.setVideo_id(videoData.getId());
                videoTable.setVideo_name(videoData.getTitle());
                videoTable.setJw_url(mediaId);
                videoTable.setVideo_currentpos(0L);
                videoTable.setUser_id(MakeMyExam.userId);
                videoTable.setCourse_id(videoData.getPayloadData().getCourse_id());
                utkashRoom.getvideoDao().addUser(videoTable);
                Helper.GoToJWVideo_Params(this, mediaId, videoData.getId(), videoData.getTitle(), 0L, videoData.getPayloadData().getCourse_id());
            }*/
        }
    }


    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        isaudio = false;
        if (paginationLoader != null && paginationLoader.isShown()) {
            paginationLoader.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (clicktype.equalsIgnoreCase("3")) {
            if (!item.getTitle().equals(allsubcatindex)) {
                for (MasteAllCatTable masteAllCatTable : selectedsub_all_cat) {
                    if (masteAllCatTable.getName().equals(item.getTitle())) {

                        launguageindex_prefix = "";
                        SelectedLaunguageid_prefix = "";

                        isfilterchanged = true;
                        isfilterapply = false;
                        SelectedLaunguageid = "";
                        SelectedSubjectid = "";
                        is_paid = "";

                        pagecount = 1;
                        allsubcatindex = masteAllCatTable.getName();
                        allsubcatindex_id = masteAllCatTable.getId();
                        filterTwo.setText(item.getTitle());

                        if (cardsArrayList.size() > 0) {
                            getTileItems(cardsArrayList, 0);
                        }

                        if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                            HomeApiStatusTable homeApiStatusTable = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.getUserId());

                            if (homeApiStatusTable.getStatus().equalsIgnoreCase("true")) {
                                getCourseData(true);
                            } else {
                                courselists.clear();
                                List<CourseDataTable> courseDataTables = null;
                                if (contentType_id.equalsIgnoreCase("0")) {
                                    courseDataTables = utkashRoom.getCoursedata().getcoursedata(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId);

                                } else {
                                    courseDataTables = utkashRoom.getCoursedata().getcoursedatawithfilter(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId, contentType_id);

                                }
                                for (CourseDataTable courseDataTable : courseDataTables) {
                                    Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getSubject_id());
                                    courselists.add(courselist);
                                }
                                if (courselists.size() > 0) {
                                    courseListRV.setVisibility(View.VISIBLE);
                                    no_data_found_RL.setVisibility(View.GONE);

                                } else {
                                    courseListRV.setVisibility(View.GONE);
                                    no_data_found_RL.setVisibility(View.VISIBLE);
                                }
                                tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
                                courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
                                courseListRV.setAdapter(tileDataAdapter);
                                courseListRV.setNestedScrollingEnabled(false);
                            }
                        } else {
                            getCourseData(true);
                        }

                        break;
                    }
                }
            }
        } else if (clicktype.equalsIgnoreCase("2")) {
            if (!item.getTitle().equals(allcatindex)) {
                for (MasteAllCatTable masteAllCatTable : selected_master_cat) {
                    if (masteAllCatTable.getName().equals(item.getTitle())) {
                        isfilterchanged = true;

                        SelectedLaunguageid = "";
                        SelectedSubjectid = "";
                        is_paid = "";
                        isfilterapply = false;
                        launguageindex_prefix = "";
                        SelectedLaunguageid_prefix = "";

                        pagecount = 1;
                        allcatindex = item.getTitle().toString();
                        allcatindex_id = masteAllCatTable.getId();
                        filterOne.setText(item.getTitle());
                        selectedsub_all_cat.clear();
                        if (cardsArrayList.size() > 0) {
                            getTileItems(cardsArrayList, 0);
                        }
                        for (MasteAllCatTable masteAllCatTable1 : masterAllCatTables) {
                            if (allcatindex_id.equalsIgnoreCase(masteAllCatTable1.getParent_id())) {
                                selectedsub_all_cat.add(masteAllCatTable1);
                            }
                        }

                        if (selectedsub_all_cat.size() > 0) {
                            allsubcatindex = selectedsub_all_cat.get(0).getName();
                            allsubcatindex_id = selectedsub_all_cat.get(0).getId();
                            filterTwo.setText(selectedsub_all_cat.get(0).getName());
                            courseListRV.setVisibility(View.VISIBLE);
                            no_data_found_RL.setVisibility(View.GONE);

                            if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                                HomeApiStatusTable homeApiStatusTable = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.getUserId());

                                if (homeApiStatusTable.getStatus().equalsIgnoreCase("true")) {
                                    getCourseData(true);

                                } else {
                                    courselists.clear();
                                    List<CourseDataTable> courseDataTables = null;
                                    if (contentType_id.equalsIgnoreCase("0")) {
                                        courseDataTables = utkashRoom.getCoursedata().getcoursedata(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId);

                                    } else {
                                        courseDataTables = utkashRoom.getCoursedata().getcoursedatawithfilter(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId, contentType_id);

                                    }
                                    for (CourseDataTable courseDataTable : courseDataTables) {
                                        Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                                        courselists.add(courselist);
                                    }
                                    if (courselists.size() > 0) {
                                        courseListRV.setVisibility(View.VISIBLE);
                                        no_data_found_RL.setVisibility(View.GONE);

                                    } else {
                                        courseListRV.setVisibility(View.GONE);
                                        no_data_found_RL.setVisibility(View.VISIBLE);
                                    }
                                    tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
                                    courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
                                    courseListRV.setAdapter(tileDataAdapter);
                                    courseListRV.setNestedScrollingEnabled(false);
                                }
                            } else {
                                getCourseData(true);
                            }
                           /* if (cardsArrayList.size()>0){
                                if (myDBClass.getCourseDao().isRecordExistsUserId(MakeMyExam.userId,contentType_id,allsubcatindex_id,mastercatid))
                                {
                                    Toast.makeText(this, "record exist form this"+allsubcatindex+""+contentType, Toast.LENGTH_SHORT).show();

                                }else {
                                    getCourseData();
                                }
                            }*/
                        } else {
                            courseListRV.setVisibility(View.GONE);
                            no_data_found_RL.setVisibility(View.VISIBLE);
                            allsubcatindex = "";
                            allsubcatindex_id = "";
                            filterTwo.setText("");
                        }

                        break;
                    }
                }
            }
        } else if (clicktype.equalsIgnoreCase("4")) {
            if (!item.getTitle().equals(launguageindex)) {
                for (LanguagesTable language : filterdata.getData().getLanguages()) {
                    if (language.getLanguage().equals(item.getTitle())) {
                        launguageindex = item.getTitle().toString();
                        SelectedLaunguageid = language.getId();
                        launguagespinner.setText(item.getTitle());
                        break;
                    } else {
                        SelectedLaunguageid = "";
                        launguageindex = "";
                        launguagespinner.setText("Select language");
                    }
                }
            }
        } else if (clicktype.equalsIgnoreCase("5")) {
            if (!item.getTitle().equals(subjectindex)) {
                for (Subject subject : filterdata.getData().getSubjects()) {
                    if (subject.getTitle().equals(item.getTitle())) {
                        subjectindex = item.getTitle().toString();
                        SelectedSubjectid = subject.getId();
                        subjectspinner.setText(item.getTitle());
                        break;
                    } else {
                        SelectedSubjectid = "";
                        subjectindex = "";
                        subjectspinner.setText("Select subject");
                    }
                }
            }
        } else {
            if (!item.getTitle().equals(mastercatname)) {
                for (MasterCat masterCat : mastercatlist) {
                    if (masterCat.getCat().equals(item.getTitle())) {
                        mastercatname = item.getTitle().toString();
                        mastercatid = masterCat.getId();
                        toolbartitleTV.setText(item.getTitle());
                        launguageindex_prefix = "";
                        SelectedLaunguageid_prefix = "";
                        isfilterchanged = true;
                        SelectedLaunguageid = "";
                        SelectedSubjectid = "";
                        is_paid = "";
                        isfilterapply = false;


                        filterOne.setText("");
                        filterTwo.setText("");
                        allcatindex = "";
                        allcatindex_id = "";
                        clicktype = "";
                        allsubcatindex = "";
                        allsubcatindex_id = "";
                        pagecount = 1;
                        selectedsub_all_cat.clear();
                        selected_master_cat.clear();


                        for (MasteAllCatTable masteAllCatTable : masterAllCatTables) {
                            if (masteAllCatTable.getMaster_type().equals(mastercatid) && masteAllCatTable.getParent_id().equalsIgnoreCase("0")) {
                                selected_master_cat.add(masteAllCatTable);
                            }
                        }

                        if (selected_master_cat.size() > 0) {
                            allcatindex = selected_master_cat.get(0).getName();
                            allcatindex_id = selected_master_cat.get(0).getId();
                            filterOne.setText(selected_master_cat.get(0).getName());

                            for (MasteAllCatTable masteAllCatTable1 : masterAllCatTables) {
                                if (allcatindex_id.equalsIgnoreCase(masteAllCatTable1.getParent_id())) {
                                    selectedsub_all_cat.add(masteAllCatTable1);
                                }
                            }

                            if (selectedsub_all_cat.size() > 0) {
                                allsubcatindex = selectedsub_all_cat.get(0).getName();
                                allsubcatindex_id = selectedsub_all_cat.get(0).getId();
                                filterTwo.setText(allsubcatindex);
                                courseListRV.setVisibility(View.VISIBLE);
                                no_data_found_RL.setVisibility(View.GONE);
                                pagecount = 1;

                                if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                                    HomeApiStatusTable homeApiStatusTable = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.getUserId());

                                    if (homeApiStatusTable.getStatus().equalsIgnoreCase("true")) {
                                        getCourseData(true);

                                    } else {
                                        courselists.clear();
                                        List<CourseDataTable> courseDataTables = null;
                                        if (contentType_id.equalsIgnoreCase("0")) {
                                            courseDataTables = utkashRoom.getCoursedata().getcoursedata(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId);

                                        } else {
                                            courseDataTables = utkashRoom.getCoursedata().getcoursedatawithfilter(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId, contentType_id);

                                        }
                                        for (CourseDataTable courseDataTable : courseDataTables) {
                                            Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                                            courselists.add(courselist);
                                        }
                                        if (courselists.size() > 0) {
                                            if (cardsArrayList.size() > 0) {
                                                getTileItems(cardsArrayList, 0);
                                            }
                                            courseListRV.setVisibility(View.VISIBLE);
                                            no_data_found_RL.setVisibility(View.GONE);
                                        } else {
                                            courseListRV.setVisibility(View.GONE);
                                            no_data_found_RL.setVisibility(View.VISIBLE);
                                        }
                                        tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
                                        courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
                                        courseListRV.setAdapter(tileDataAdapter);
                                        courseListRV.setNestedScrollingEnabled(false);
                                    }
                                } else {
                                    getCourseData(true);
                                }
                             /*   if (cardsArrayList.size()>0){
                                    if (myDBClass.getCourseDao().isRecordExistsUserId(MakeMyExam.userId,contentType_id,allsubcatindex_id,mastercatid))
                                    {
                                        Toast.makeText(this, "record exist form this"+allsubcatindex+""+contentType, Toast.LENGTH_SHORT).show();

                                    }else {
                                        getCourseData();
                                    }
                                }*/


                            } else {
                                courseListRV.setVisibility(View.GONE);
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                allsubcatindex_id = "";
                                allsubcatindex = "";
                                filterTwo.setText("");
                            }
                        } else {
                            allcatindex = "";
                            allcatindex_id = "";
                            filterOne.setText("");
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    private void selecteddata() {
        if (mastercatlist.size() > 0) {
            if (!pullrefresh) {
                if (prefencelocale.equalsIgnoreCase("")) {
                    mastercatname = mastercatlist.get(0).getCat();
                    mastercatid = mastercatlist.get(0).getId();
                }
                toolbartitleTV.setText(mastercatname);
                selected_master_cat.clear();
                selectedsub_all_cat.clear();
            }

            for (MasteAllCatTable masteAllCatTable : masterAllCatTables) {
                if (masteAllCatTable.getMaster_type().equals(mastercatid) && masteAllCatTable.getParent_id().equalsIgnoreCase("0")) {

                    if (prefencelocale.equals("1") && masteAllCatTable.getId().equalsIgnoreCase(userData.getPreferences().get(0).getMain_cat())) {
                        allcatindex = masteAllCatTable.getName();
                        allcatindex_id = masteAllCatTable.getId();
                    }
                    if (prefencelocale.equals("2") && masteAllCatTable.getId().equalsIgnoreCase(notifcationPrefence.getMain_cat())) {
                        allcatindex = masteAllCatTable.getName();
                        allcatindex_id = masteAllCatTable.getId();
                    }
                    selected_master_cat.add(masteAllCatTable);
                }
            }
           /* if (prefencelocale.equalsIgnoreCase("1")) {
                for (MasteAllCatTable masteAllCatTable : selected_master_cat) {

                    if (masteAllCatTable.getId().equalsIgnoreCase(userData.getPreferences().get(0).getMain_cat())) {
                        allcatindex = masteAllCatTable.getName();
                        allcatindex_id = masteAllCatTable.getId();
                        break;
                    }
                }
            }
*/

            if (selected_master_cat.size() > 0) {
                if (!pullrefresh) {
                    if (prefencelocale.equalsIgnoreCase("")) {
                        allcatindex = selected_master_cat.get(0).getName();
                        allcatindex_id = selected_master_cat.get(0).getId();
                    }
                    filterOne.setText(allcatindex);
                }

                for (MasteAllCatTable masteAllCatTable1 : masterAllCatTables) {
                    if (allcatindex_id.equalsIgnoreCase(masteAllCatTable1.getParent_id())) {

                        if (prefencelocale.equalsIgnoreCase("1")) {
                            if (masteAllCatTable1.getId().equalsIgnoreCase(userData.getPreferences().get(0).getSub_cat())) {
                                allsubcatindex = masteAllCatTable1.getName();
                                allsubcatindex_id = masteAllCatTable1.getId();
                            }
                        }  if (prefencelocale.equalsIgnoreCase("2")) {
                            if (masteAllCatTable1.getId().equalsIgnoreCase(notifcationPrefence.getSub_cat())) {
                                allsubcatindex = masteAllCatTable1.getName();
                                allsubcatindex_id = masteAllCatTable1.getId();
                            }
                        }
                        selectedsub_all_cat.add(masteAllCatTable1);
                    }
                }

                if (selectedsub_all_cat.size() > 0) {
                    if (!pullrefresh) {
                        if (prefencelocale.equalsIgnoreCase("")) {
                            allsubcatindex = selectedsub_all_cat.get(0).getName();
                            allsubcatindex_id = selectedsub_all_cat.get(0).getId();
                        }
                    }
                    filterTwo.setText(allsubcatindex);
                    courseListRV.setVisibility(View.VISIBLE);
                    no_data_found_RL.setVisibility(View.GONE);
                    subjectindex = "";
                    launguageindex = "";
                    filterdata = new Filterdata();
                    com.utkarshnew.android.home.model.FilterData.Data data = new com.utkarshnew.android.home.model.FilterData.Data();
                    List<Subject> subject = new ArrayList<>();
                    if (utkashRoom == null) {
                        utkashRoom = UtkashRoom.getAppDatabase(this);
                    }
                    String subjects = utkashRoom.getMasterAllCatDao().getfilteddata(allsubcatindex_id);
                    LanguagesTable = utkashRoom.getLaunguages().getLaunguagedetail();
                    subject = new Gson().fromJson(subjects, new TypeToken<List<Subject>>() {
                    }.getType());
                    data.setSubjects(subject);
                    data.setLanguages(LanguagesTable);
                    filterdata.setData(data);

                    if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
                        HomeApiStatusTable homeApiStatusTable = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id, MakeMyExam.getUserId());

                        if (homeApiStatusTable.getStatus().equalsIgnoreCase("true")) {
                            getCourseData(true);
                        } else {
                            courselists.clear();
                            courseDataTables.clear();
                            if (contentType_id.equalsIgnoreCase("0")) {

                                try {
                                    courseDataTables = utkashRoom.getCoursedata().getcoursedata(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId);
                                    setdata();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    courseDataTables = utkashRoom.getCoursedata().getcoursedata(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id, MakeMyExam.userId);
                                    setdata();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        getCourseData(true);
                    }
                } else {
                    courseListRV.setVisibility(View.GONE);
                    no_data_found_RL.setVisibility(View.VISIBLE);
                    filterTwo.setText("");
                    allsubcatindex = "";
                    allsubcatindex_id = "";
                }
            } else {
                allcatindex = "";
                allcatindex_id = "";
                filterOne.setText("");
                filterTwo.setText("");
                allsubcatindex = "";
                allsubcatindex_id = "";
            }
        } else {
            hit_api_master_data();
        }

    }

    private void setdata() {
        if (SelectedLaunguageid_prefix.equalsIgnoreCase("")) {
            for (CourseDataTable courseDataTable : courseDataTables) {
                Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                courselists.add(courselist);
            }
        } else {
            for (CourseDataTable courseDataTable : courseDataTables) {
                if (SelectedLaunguageid_prefix.equalsIgnoreCase(courseDataTable.getLang_id())) {
                    Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                    courselists.add(courselist);
                }
            }
        }

        if (courselists.size() > 0) {
            courseListRV.setVisibility(View.VISIBLE);
            no_data_found_RL.setVisibility(View.GONE);
        } else {
            courseListRV.setVisibility(View.GONE);
            no_data_found_RL.setVisibility(View.VISIBLE);
        }
        tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
        courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
        courseListRV.setAdapter(tileDataAdapter);
        courseListRV.setNestedScrollingEnabled(false);
    }

    private void getCourseData(boolean showProgress) {
        networkCall.NetworkAPICall(API.get_courses, "", showProgress, false);
    }

    @Override
    public void onBackPressed() {
        if (backstatus) {
            if (backPressed + 3000 > System.currentTimeMillis()) {
                getIntent().setData(null);
                finishAndRemoveTask();
            } else {
                backPressed = System.currentTimeMillis();
                Helper.showSnackBar(drawer, getString(R.string.press_again_to_exit));
            }
        } else if (fragment instanceof Chatboot) {
            backstatus = true;
            forchatbot.setVisibility(View.GONE);
        } else {
            if (backPressed + 3000 > System.currentTimeMillis()) {
                getIntent().setData(null);
                finishAndRemoveTask();
            } else {
                backPressed = System.currentTimeMillis();
                Helper.showSnackBar(drawer, getString(R.string.press_again_to_exit));
            }
        }
        //   super.onBackPressed();
    }

    // advanced filter
    TextView launguagespinner;
    TextView subjectspinner;
    String is_paid = "";
    BottomSheetDialog watchlist;

    public void openwatchlist_dailog_resource(Context context, List<LanguagesTable> languages, List<Subject> subjects) {
        try {
            isfilterchanged = false;
            watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.filter);
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            subjectspinner = watchlist.findViewById(R.id.subjectspinner);
            launguagespinner = watchlist.findViewById(R.id.launguagespinner);
            RelativeLayout launguage = watchlist.findViewById(R.id.launguage);
            RelativeLayout subject = watchlist.findViewById(R.id.subject);
            RelativeLayout rl1 = watchlist.findViewById(R.id.rl1);
            RelativeLayout rl2 = watchlist.findViewById(R.id.rl2);
            Button button = watchlist.findViewById(R.id.filterdata);
            LinearLayout free = watchlist.findViewById(R.id.free);
            LinearLayout paid_ll = watchlist.findViewById(R.id.paid_ll);
            LinearLayout both_ll = watchlist.findViewById(R.id.both_ll);
            TextView freeTextTv = watchlist.findViewById(R.id.freeTextTv);
            TextView paidtextTv = watchlist.findViewById(R.id.paidTextTv);
            TextView txt2 = watchlist.findViewById(R.id.txt2);
            TextView txt3 = watchlist.findViewById(R.id.txt3);
            TextView bothTextTv = watchlist.findViewById(R.id.bothTextTv);

            if (subjects == null || subjects.size() == 0) {
                txt2.setVisibility(View.GONE);
                rl1.setVisibility(View.GONE);
            } else {
                txt2.setVisibility(View.VISIBLE);
                rl1.setVisibility(View.VISIBLE);
            }

            if (launguage == null || languages.size() == 0) {
                txt3.setVisibility(View.GONE);
                rl2.setVisibility(View.GONE);
            } else {
                txt3.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.VISIBLE);
            }
            if (!isfilterchanged) {

                subjectspinner.setText(subjectindex.equalsIgnoreCase("") ? "Select  Subject" : subjectindex);

                if (!launguageindex_prefix.equalsIgnoreCase("")) {
                    launguagespinner.setText(launguageindex_prefix.equalsIgnoreCase("") ? "Select  Language" : launguageindex_prefix);

                } else {
                    launguagespinner.setText(launguageindex.equalsIgnoreCase("") ? "Select  Language" : launguageindex);

                }

                if (is_paid.equalsIgnoreCase("0")) {
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor("#000000"));
                    gradientDrawable.setCornerRadius(60);
                    free.setBackground(gradientDrawable);
                    freeTextTv.setTextColor(Color.WHITE);

                    both_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                    bothTextTv.setTextColor(context.getResources().getColor(R.color.black));

                    paid_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                    paidtextTv.setTextColor(context.getResources().getColor(R.color.black));
                } else if (is_paid.equalsIgnoreCase("1")) {
                    is_paid = "1";
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor("#000000"));
                    gradientDrawable.setCornerRadius(60);
                    paid_ll.setBackground(gradientDrawable);
                    paidtextTv.setTextColor(Color.WHITE);

                    both_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                    bothTextTv.setTextColor(context.getResources().getColor(R.color.black));

                    free.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                    freeTextTv.setTextColor(context.getResources().getColor(R.color.black));
                } else if (is_paid.equalsIgnoreCase("")) {
                    is_paid = "";
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor("#000000"));
                    gradientDrawable.setCornerRadius(60);
                    both_ll.setBackground(gradientDrawable);
                    bothTextTv.setTextColor(Color.WHITE);

                    paid_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                    paidtextTv.setTextColor(context.getResources().getColor(R.color.black));
                    free.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                    freeTextTv.setTextColor(context.getResources().getColor(R.color.black));
                }
            }

            both_ll.setOnClickListener(new OnSingleClickListener(() -> {
                is_paid = "";
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#000000"));
                gradientDrawable.setCornerRadius(60);
                both_ll.setBackground(gradientDrawable);
                bothTextTv.setTextColor(Color.WHITE);
                paid_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                paidtextTv.setTextColor(context.getResources().getColor(R.color.black));
                free.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                freeTextTv.setTextColor(context.getResources().getColor(R.color.black));
                return null;
            }));


            free.setOnClickListener(new OnSingleClickListener(() -> {
                is_paid = "0";
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#000000"));
                gradientDrawable.setCornerRadius(60);
                free.setBackground(gradientDrawable);
                freeTextTv.setTextColor(Color.WHITE);
                paid_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                paidtextTv.setTextColor(context.getResources().getColor(R.color.black));
                both_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                bothTextTv.setTextColor(context.getResources().getColor(R.color.black));
                return null;
            }));

            paid_ll.setOnClickListener(new OnSingleClickListener(() -> {
                is_paid = "1";
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#000000"));
                gradientDrawable.setCornerRadius(60);
                paid_ll.setBackground(gradientDrawable);
                paidtextTv.setTextColor(Color.WHITE);
                both_ll.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                bothTextTv.setTextColor(context.getResources().getColor(R.color.black));
                free.setBackground(context.getResources().getDrawable(R.drawable.bg_tile_unselected));
                freeTextTv.setTextColor(context.getResources().getColor(R.color.black));
                return null;
            }));


            button.setOnClickListener(new OnSingleClickListener(() -> {
                if (SelectedSubjectid.equalsIgnoreCase("") && SelectedLaunguageid.equalsIgnoreCase("") && is_paid == null) {
                    Toast.makeText(context, "Please select filter first", Toast.LENGTH_SHORT).show();
                } else {
                    launguageindex_prefix = "";
                    SelectedLaunguageid_prefix = "";
                    if (utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + "0")) {
                        HomeApiStatusTable homeApiStatusTable = utkashRoom.getHomeApiStatusdata().getcoursedetail(mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + "0", MakeMyExam.getUserId());

                        if (homeApiStatusTable.getStatus().equalsIgnoreCase("true")) {
                            isfilterapply = true;
                            pagecount = 1;
                            getCourseData(true);
                        } else {
                            List<CourseDataTable> courseDataTables = null;

                            String queryString = new String();

                            if (contentType_id.equalsIgnoreCase("0")) {
                                queryString += "SELECT * FROM CourseDataTable  WHERE mainid = '" + mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "' AND userid ='" + MakeMyExam.userId + "'";

                            } else {
                                queryString += "SELECT * FROM CourseDataTable  WHERE mainid = '" + mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "' AND userid ='" + MakeMyExam.userId + "' AND type_id='" + contentType_id + "'";

                            }
                            if (!SelectedSubjectid.equalsIgnoreCase("")) {
                                queryString += " AND subject_id='" + SelectedSubjectid + "'";
                            }

                            if (!SelectedLaunguageid.equalsIgnoreCase("")) {
                                queryString += " AND lang_id='" + SelectedLaunguageid + "'";
                            }

                            if (!is_paid.equalsIgnoreCase("")) {

                                if (is_paid.equalsIgnoreCase("0")) {
                                    queryString += " AND mrp=='" + 0 + "'";
                                } else if (is_paid.equalsIgnoreCase("1")) {
                                    queryString += " AND mrp>'" + 0 + "'";
                                }
                            }

                            queryString += " ORDER BY autoid" + " ;";

                            SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
                            courseDataTables = utkashRoom.getCoursedata().getcoursedatafilterforpaid2(query);
                            courselists = new ArrayList<>();


                            for (CourseDataTable courseDataTable : courseDataTables) {
                                Courselist courselist = new Courselist(courseDataTable.getCourse_id(), courseDataTable.getTitle(), courseDataTable.getCover_image(), courseDataTable.getMrp(), courseDataTable.getCourse_sp(), courseDataTable.getValidity(), courseDataTable.getSubject_id(), courseDataTable.getLang_id());
                                courselists.add(courselist);
                            }

                            ArrayList<Courselist> newcourselist = new ArrayList<>();
                            newcourselist = removeDuplicates(courselists);
                            courselists.clear();
                            courselists.addAll(newcourselist);

                            if (courselists.size() > 0) {
                                courseListRV.setVisibility(View.VISIBLE);
                                no_data_found_RL.setVisibility(View.GONE);
                            } else {
                                courseListRV.setVisibility(View.GONE);
                                no_data_found_RL.setVisibility(View.VISIBLE);
                            }
                            tileDataAdapter = new TileDataAdapter(HomeActivity.this, courselists);
                            courseListRV.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2, GridLayoutManager.VERTICAL, false));
                            courseListRV.setAdapter(tileDataAdapter);
                            courseListRV.setNestedScrollingEnabled(false);
                        }
                    }
                    if (watchlist != null && watchlist.isShowing()) {
                        watchlist.dismiss();
                    }

                    //                  getCourseData(true);
                }
                return null;
            }));


            launguage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicktype = "4";
                    PopupMenu popupMenu = new PopupMenu(context, launguagespinner, Gravity.LEFT);
                    popupMenu.getMenu().add("Select language");
                    for (int i = 0; i < languages.size(); i++) {
                        popupMenu.getMenu().add(languages.get(i).getLanguage());
                    }
                    popupMenu.setOnMenuItemClickListener(HomeActivity.this);
                    popupMenu.show();
                }
            });


            launguagespinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicktype = "4";
                    PopupMenu popupMenu = new PopupMenu(context, launguagespinner, Gravity.LEFT);
                    popupMenu.getMenu().add("Select language");
                    for (int i = 0; i < languages.size(); i++) {
                        popupMenu.getMenu().add(languages.get(i).getLanguage());
                    }
                    popupMenu.setOnMenuItemClickListener(HomeActivity.this);
                    popupMenu.show();
                }
            });

            subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicktype = "5";
                    PopupMenu popupMenu = new PopupMenu(context, subjectspinner, Gravity.LEFT);
                    popupMenu.getMenu().add("Select subject");
                    for (int i = 0; i < subjects.size(); i++) {
                        popupMenu.getMenu().add(subjects.get(i).getTitle());
                    }
                    popupMenu.setOnMenuItemClickListener(HomeActivity.this);
                    popupMenu.show();
                }
            });


            subjectspinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicktype = "5";
                    PopupMenu popupMenu = new PopupMenu(context, subjectspinner, Gravity.LEFT);
                    popupMenu.getMenu().add("Select subject");
                    for (int i = 0; i < subjects.size(); i++) {
                        popupMenu.getMenu().add(subjects.get(i).getTitle());
                    }
                    popupMenu.setOnMenuItemClickListener(HomeActivity.this);
                    popupMenu.show();
                }
            });

            if (!watchlist.isShowing()) {
                watchlist.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hit_api_for_data() {
        networkCall.NetworkAPICall(API.API_GET_MASTER_DATA, contentType, true, false);
    }

    private void hit_read_api() {
        networkCall.NetworkAPICall(API.mark_as_read, "", false, false);
    }


    private void getmyQuires() {
        networkCall.NetworkAPICall(API.GET_MY_QUIRES, "", true, false);
    }

    public void closschatbot() {
        forchatbot.setVisibility(View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            if (notification_code != 0) {

                Constants.REFRESHPAGE = "";
                if (Helper.isNetworkConnected(this)) {
                    if (utkashRoom.getMasterAllCatDao().isRecordExistsUserId(MakeMyExam.userId)) {
                        courseTypeMasterTables.clear();
                        masterAllCatTables.clear();
                        mastercatlist.clear();
                        if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                            courseTypeMasterTables = utkashRoom.getcoursetypemaster().getcourse_typemaster(MakeMyExam.userId);
                            masterAllCatTables = utkashRoom.getMasterAllCatDao().getmaster_allcat(MakeMyExam.userId);
                            mastercatlist = utkashRoom.getMastercatDao().getmastercat(MakeMyExam.userId);

                            userData = SharedPreference.getInstance().getLoggedInUser();

                            if (userData != null && userData.getPreferences().size() > 0) {
                                Data.Preferences preferences = userData.getPreferences().get(0);
                                for (MasteAllCatTable masterMasteAllCatTable : masterAllCatTables) {
                                    if (preferences.getSub_cat().equalsIgnoreCase(masterMasteAllCatTable.getId())) {
                                        preferences.setMaster_type(masterMasteAllCatTable.getMaster_type());
                                        userData.getPreferences().set(0, preferences);
                                        prefencelocale = "1";
                                        break;

                                    }
                                }

                                for (MasterCat mastercat : mastercatlist) {
                                    if (userData.getPreferences().get(0).getMaster_type().equalsIgnoreCase(mastercat.getId())) {
                                        mastercatname = mastercat.getCat();
                                        mastercatid = mastercat.getId();
                                        break;
                                    }
                                }
                            }



                        }
                        runOnUiThread(() -> {
                            cardsArrayList.clear();
                            for (CourseTypeMasterTable courseTypeMasterTables : courseTypeMasterTables) {
                                cardsArrayList.add(new Cards(courseTypeMasterTables.getId(), courseTypeMasterTables.getName(), "#000000", courseTypeMasterTables.getName(), "0#0#0#0#0#0", "0"));
                            }
                            if (cardsArrayList.size() > 0) {
                                getTileItems(cardsArrayList, 0);
                            }
                            selecteddata();
                        });
                    } else {
                        hit_api_master_data();
                    }
                }
            } else {
                if (!utkashRoom.getMasterAllCatDao().isRecordExistsUserId(MakeMyExam.userId)) {
                    hit_api_master_data();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            notification_code = 0;
        }


        if (!utkashRoom.getHomeApiStatusdata().isRecordExistsUserId(MakeMyExam.userId, mastercatid + "_" + allcatindex_id + "_" + allsubcatindex_id + "_" + contentType_id)) {
            getCourseData(true);
        }
    }

    public void feedAutoRefresh() {
        if (SharedPreference.getInstance().getInt(Const.FEED_COUNT) == 0) {
            cvrFeedCount.setBackgroundResource(R.drawable.uncircle_notification_count);
            cvrFeedCount.setVisibility(View.GONE);
        } else {
            cvrFeedCount.setBackgroundResource(R.drawable.circle_notification_count);
            cvrFeedCount.setVisibility(View.VISIBLE);

        }
    }



    public void homeAutoRefresh() {
        if (SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) == 0) {
            ShortcutBadger.applyCount(getApplicationContext(), SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT));
            cvrNotificationCount.setBackgroundResource(R.drawable.uncircle_notification_count
            );
        } else {
            ShortcutBadger.applyCount(getApplicationContext(), SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT));
            cvrNotificationCount.setBackgroundResource(R.drawable.circle_notification_count);
            if (SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) > 99) {
                notificaionCount.setText("99+");

            } else {
                notificaionCount.setText(String.valueOf(SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT)));

            }
            //notificaionCount.setText(String.valueOf(SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT) > 99 ?  ""+SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT)+"+"  :SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT)));
        }
    }

    public void UserLogout() {
        networkCall.NetworkAPICall(API.user_logout, "", true, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        utkashRoom = null;
        mAppUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    public String getdate(String timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(timestamp));
        return sdf.format(currenTimeZone);
    }

    public void initialzehomepage() {
        if (utkashRoom == null) {
            utkashRoom = UtkashRoom.getAppDatabase(this);
        }

        if (notification_code != 0) {
            if (Helper.isNetworkConnected(this)) {
                if (utkashRoom.getMasterAllCatDao().isRecordExistsUserId(MakeMyExam.userId)) {
                    courseTypeMasterTables.clear();
                    masterAllCatTables.clear();
                    mastercatlist.clear();


                    try {
                        if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                            courseTypeMasterTables = utkashRoom.getcoursetypemaster().getcourse_typemaster(MakeMyExam.userId);
                            masterAllCatTables = utkashRoom.getMasterAllCatDao().getmaster_allcat(MakeMyExam.userId);
                            mastercatlist = utkashRoom.getMastercatDao().getmastercat(MakeMyExam.userId);
                            userData = SharedPreference.getInstance().getLoggedInUser();

                            if (userData != null && userData.getPreferences().size() > 0) {
                                Data.Preferences preferences = userData.getPreferences().get(0);
                                for (MasteAllCatTable masterMasteAllCatTable : masterAllCatTables) {
                                    if (preferences.getSub_cat().equalsIgnoreCase(masterMasteAllCatTable.getId())) {
                                        preferences.setMaster_type(masterMasteAllCatTable.getMaster_type());
                                        userData.getPreferences().set(0, preferences);
                                        prefencelocale = "1";
                                        break;

                                    }
                                }

                                for (MasterCat mastercat : mastercatlist) {
                                    if (userData.getPreferences().get(0).getMaster_type().equalsIgnoreCase(mastercat.getId())) {
                                        mastercatname = mastercat.getCat();
                                        mastercatid = mastercat.getId();
                                        break;
                                    }
                                }
                            }


                        }
                        runOnUiThread(() -> {
                            cardsArrayList.clear();
                            for (CourseTypeMasterTable courseTypeMasterTables : courseTypeMasterTables) {
                                cardsArrayList.add(new Cards(courseTypeMasterTables.getId(), courseTypeMasterTables.getName(), "#000000", courseTypeMasterTables.getName(), "0#0#0#0#0#0", "0"));
                            }
                            if (cardsArrayList.size() > 0) {
                                getTileItems(cardsArrayList, 0);
                            }
                            selecteddata();

                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    hit_api_master_data();
                }
            }
            notification_code = 0;
        }
    }

    public <Courselist> ArrayList<com.utkarshnew.android.Model.Courselist> removeDuplicates(ArrayList<com.utkarshnew.android.Model.Courselist> list) {
        ArrayList<com.utkarshnew.android.Model.Courselist> newList = new ArrayList<com.utkarshnew.android.Model.Courselist>();
        for (com.utkarshnew.android.Model.Courselist element : list) {
            if (!newList.isEmpty()) {
                boolean isfind = false;
                for (int i = 0; i < newList.size(); i++) {
                    if (newList.get(i).getId().equalsIgnoreCase(element.getId())) {
                        isfind = true;
                        break;
                    } else {
                        isfind = false;
                    }
                }
                if (!isfind) {
                    newList.add(element);
                }
            } else {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }

    public void toggleRefreshing(boolean enabled) {
        if (pullToReferesh != null) {
            pullToReferesh.setEnabled(enabled);
        }
    }

    private void inittest(ArrayList<Video> videoArrayList) {
        if (videoArrayList.get(0).getIs_locked().equalsIgnoreCase("1")) {
            if (parentid == null || parentid.equalsIgnoreCase("")) {
                Intent intent = new Intent(this, CourseActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                intent.putExtra(Const.COURSE_ID_MAIN, videoArrayList.get(0).getPayloadData().getCourse_id());
                intent.putExtra(Const.COURSE_PARENT_ID, "");
                intent.putExtra(Const.IS_COMBO, false);
                SharedPreference.getInstance().putString(Const.ID, videoArrayList.get(0).getPayloadData().getCourse_id());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, CourseActivity.class);
                intent.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY);
                intent.putExtra(Const.COURSE_ID_MAIN, parentid);
                intent.putExtra(Const.COURSE_PARENT_ID, "");
                intent.putExtra(Const.IS_COMBO, false);
                SharedPreference.getInstance().putString(Const.ID, videoArrayList.get(0).getPayloadData().getCourse_id());
                startActivity(intent);
            }

        } else {
            if (videoArrayList.get(0).getState().equals("1")) {
                if (videoArrayList.get(0).getStart_date().equalsIgnoreCase("") || videoArrayList.get(0).getStart_date().equalsIgnoreCase("0") && videoArrayList.get(0).getEnd_date().equalsIgnoreCase("") || videoArrayList.get(0).getEnd_date().equalsIgnoreCase("0")) {
                    first_attempt = "1";
                    Intent resultScreen = new Intent(this, QuizActivity.class);
                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                    resultScreen.putExtra(Const.STATUS, videoArrayList.get(0).getId());
                    resultScreen.putExtra(Const.NAME, videoArrayList.get(0).getTest_series_name());
                    resultScreen.putExtra("first_attempt", first_attempt);
                    Helper.gotoActivity(resultScreen, this);
                } else {
                    if (MakeMyExam.getTime_server() >= Long.parseLong(videoArrayList.get(0).getEnd_date()) * 1000) {
                        if (MakeMyExam.getTime_server() > Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000) {
                            if (Long.parseLong(videoArrayList.get(0).getEnd_date()) < 1640066737) {
                                submition_type = "1";
                                quiz_id = videoArrayList.get(0).getId();
                                first_attempt = "0";
                                result_date = videoArrayList.get(0).getResult_date();
                                SharedPreference.getInstance().putString(Const.ID, course_id);
                                testname = videoArrayList.get(0).getTest_series_name();
                                testquestion = videoArrayList.get(0).getTotal_questions();
                                videodata = videoArrayList.get(0);
                                hit_api_for_iniializetest();
                            } else {
                                first_attempt = "1";
                                Intent resultScreen = new Intent(this, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN);
                                resultScreen.putExtra(Const.STATUS, videoArrayList.get(0).getId());
                                resultScreen.putExtra(Const.NAME, videoArrayList.get(0).getTest_series_name());
                                resultScreen.putExtra("first_attempt", first_attempt);
                                Helper.gotoActivity(resultScreen, this);
                            }

                        } else {
                            initialzehomepage();
                            Toast.makeText(this, "You have already Attempted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (!videoArrayList.get(0).getIs_reattempt().equalsIgnoreCase("0")) {
                            submition_type = "1";
                            inittest(videoArrayList.get(0));


                        } else {
                            Toast.makeText(this, "You have already Attempted the test", Toast.LENGTH_SHORT).show();
                            initialzehomepage();
                        }
                    }
                }

            } else {
                if (videoArrayList.get(0).getStart_date().equalsIgnoreCase("") || videoArrayList.get(0).getStart_date().equalsIgnoreCase("0") && videoArrayList.get(0).getEnd_date().equalsIgnoreCase("") || videoArrayList.get(0).getEnd_date().equalsIgnoreCase("0")) {

                    TestTable test_data = utkashRoom.getTestDao().test_data(videoArrayList.get(0).getId(), MakeMyExam.userId);
                    if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {

                        Snackbar.make(filter.getRootView(), "You have already Attempted the test", Snackbar.LENGTH_SHORT).show();
                        initialzehomepage();

                    } else {

                        if (notification_code == 90002) {
                            quiz_id = videoArrayList.get(0).getId();
                            first_attempt = "1";
                            result_date = videoArrayList.get(0).getResult_date();
                            SharedPreference.getInstance().putString(Const.ID, course_id);
                            testname = videoArrayList.get(0).getTest_series_name();
                            testquestion = videoArrayList.get(0).getTotal_questions();
                            videodata = videoArrayList.get(0);
                            submition_type = videoArrayList.get(0).getSubmission_type();

                            hit_api_for_iniializetest();
                        } else {
                            first_attempt = "1";
                            submition_type = videoArrayList.get(0).getSubmission_type();
                            inittest(videoArrayList.get(0));
                        }

                    }
                } else {
                    if (MakeMyExam.getTime_server() >= Long.parseLong(videoArrayList.get(0).getEnd_date()) * 1000) {
                        if (Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000 > MakeMyExam.getTime_server()) {
                            Snackbar.make(filter.getRootView(), "Your Result will be declare on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000)), Snackbar.LENGTH_SHORT).show();
                            initialzehomepage();

                        } else if (MakeMyExam.getTime_server() > Long.parseLong(videoArrayList.get(0).getResult_date()) * 1000) {
                            if (Long.parseLong(videoArrayList.get(0).getEnd_date()) < 1640066737) {
                                submition_type = "1";
                                quiz_id = videoArrayList.get(0).getId();
                                first_attempt = "0";
                                result_date = videoArrayList.get(0).getResult_date();
                                SharedPreference.getInstance().putString(Const.ID, course_id);
                                testname = videoArrayList.get(0).getTest_series_name();
                                testquestion = videoArrayList.get(0).getTotal_questions();
                                videodata = videoArrayList.get(0);
                                hit_api_for_iniializetest();
                            } else {
                                Intent resultScreen = new Intent(this, QuizActivity.class);
                                resultScreen.putExtra(Const.FRAG_TYPE, "leader_board");
                                resultScreen.putExtra(Const.STATUS, videoArrayList.get(0).getId());
                                resultScreen.putExtra(Const.NAME, videoArrayList.get(0).getTest_series_name());
                                Helper.gotoActivity(resultScreen, this);
                            }

                        }
                    } else if (MakeMyExam.getTime_server() < Long.parseLong(videoArrayList.get(0).getStart_date()) * 1000) {
                        Snackbar.make(filter.getRootView(), "Test will start on " + new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(Long.parseLong(videoArrayList.get(0).getStart_date()) * 1000)), Snackbar.LENGTH_SHORT).show();
                        initialzehomepage();
                    } else {
                        TestTable test_data = utkashRoom.getTestDao().test_data(videoArrayList.get(0).getId(), MakeMyExam.userId);
                        if (test_data != null && test_data.getStatus() != null && !test_data.getStatus().equalsIgnoreCase("")) {

                            Snackbar.make(filter.getRootView(), "You have already Attempted the test", Snackbar.LENGTH_SHORT).show();
                            initialzehomepage();

                        } else {

                            if (notification_code == 90002) {
                                quiz_id = videoArrayList.get(0).getId();
                                first_attempt = "1";
                                result_date = videoArrayList.get(0).getResult_date();
                                SharedPreference.getInstance().putString(Const.ID, course_id);
                                testname = videoArrayList.get(0).getTest_series_name();
                                testquestion = videoArrayList.get(0).getTotal_questions();
                                videodata = videoArrayList.get(0);
                                submition_type = videoArrayList.get(0).getSubmission_type();

                                hit_api_for_iniializetest();
                            } else {
                                first_attempt = "1";
                                submition_type = videoArrayList.get(0).getSubmission_type();
                                inittest(videoArrayList.get(0));
                            }

                        }
                    }
                }

            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onRefreshHome(String masterCat, String cat, String subcat) {
        getLocaleData( masterCat,  cat,subcat);
    }

    private void hit_api_for_feed_dot() {
        NetworkCall networkCall = new NetworkCall(this, this);
        networkCall.NetworkAPICall(API.post_feed_type, "", false, false);
    }

}