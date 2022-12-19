package com.utkarshnew.android.JWextractor;

import static android.view.Gravity.RIGHT;
import static android.view.Gravity.TOP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.media.session.MediaSessionCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pdfview.PDFView;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.PlayerPojo.Addindex;
import com.utkarshnew.android.Model.PlayerPojo.Metadata;
import com.utkarshnew.android.Model.PlayerPojo.Pdf;
import com.utkarshnew.android.Model.PlayerPojo.VideoTimeFramePojo;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Player.Adapter_recycleveiw_vedio;
import com.utkarshnew.android.Player.BookmarkAdapter;
import com.utkarshnew.android.Player.NotesAdapter;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.CookieData;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionDialog;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionHelper;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class JWVideoPlayer extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, GestureDetector.OnDoubleTapListener, SelectSpeedo, SelectQuality {
    private IntentFilter intentFilter;
    private InternetConnectionChecker broadcastReceiver;
    boolean playerError = false;
    private SimpleExoPlayerView simpleExoPlayerView;
    public SimpleExoPlayer player;
    String spped_title = "";
    ProgressBar progressBar;
    private boolean isInitialResChanged;
    private DataSource.Factory mediaDataSourceFactory;
    private String userAgent;
    ArrayList<UrlObject> sppedlist = Helper.speedlist();

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView popupList;
    private PopupWindow popupWindow;
    VideoPopUpMenuAdapter mAdapter;
    public static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    public DefaultTrackSelector.Parameters trackSelectorParameters;

    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    MediaSource mediaSource;
    public DefaultTrackSelector trackSelector;
    private boolean isShowingTrackSelectionDialog;
    public TrackSelectionHelper trackSelectionHelper;
    String url = "", video_id = "", title = "", tile_id = "", tile_type = "", course_id = "";
    Long current_pos = 0L;
    int qulaity_pos = 0;
    String objectcookie;
    private CookieData cookieDatas;
    int speedpostiion = 3;
    int tempspeed = 3;
    private PopupWindow popupWindow_quality;

    private HashMap<Integer, String> sparseAdaptiveVideoUrlList;
    private HashMap<Integer, UrlObject> sparseAdaptiveVideoUrlList_new;
    private List<String> sparseOPUSAudioUrl;
    List<Integer> sparseAdaptiveResolutionList;
    private ConnectionQuality mConnectionClass = ConnectionQuality.UNKNOWN;
    private DeviceBandwidthSampler mDeviceBandwidthSampler;
    private ConnectionClassManager mConnectionClassManager;
    private static long playPosition;
    int tempqulity = 0;
    ImageView quality;
    private ImageView mFullScreenIcon, qualitysection;
    private TextView tvGoLive;
    ImageView icon;
    private TextView speedTV, MarqueeText, video_name;
    String speedx = "";
    private FrameLayout mFullScreenButton;
    private boolean mExoPlayerFullscreen = false;
    DefaultLoadControl loadControl;
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private Dialog mFullScreenDialog;
    private UtkashRoom utkashRoom;
    View rootView;
    RelativeLayout parent_layout;
    String isclicked = "";
    private static int newOrientation;
    private String deletedindex = "";
    public RecyclerView recyclerChat;
    private static final String TAG = "JWVideoPlayer";
    TextView bookmarkBtn, indexBtn, chat_btn;
    private BookmarkAdapter bookmarkAdapter;
    private Adapter_recycleveiw_vedio adapter;
    private List<VideoTimeFramePojo> indexdata = new ArrayList<>();
    private List<VideoTimeFramePojo> bookmarkdata = new ArrayList<>();
    private List<Pdf> pdf = new ArrayList<>();
    // add bookmark index
    private String time = "";
    private String info = "";
    private String state = "";
    private TextView notes, poll;
    Button retry;
    public PDFView webView;
    private NotesAdapter notesAdapter;
    TextView addBookmark;
    NetworkCall networkCall;
    private ConnectionChangedListener mListener;
    private Timer internetSpeedTimer;
    private long internetCheckDelay = 0;
    private final long internetCheckDelayMultiple = 20;
    long mLastClickTime_frame5;
    private ArrayList<UrlObject> qualiity_array = new ArrayList<>();
    ImageView playBtn, pauseBtn;
    public long expire_time;
    ImageView exo_ffwd, exo_rew;

    public ImageView cross, expand;
    public RelativeLayout pdf_view_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Helper.enableScreenShot(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jw_video_player);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        utkashRoom = UtkashRoom.getAppDatabase(this);
        networkCall = new NetworkCall(this, this);
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(Const.VIDEO_LINK);
            expire_time = getIntent().getExtras().getLong("expire_time", 0);
            video_id = getIntent().getExtras().getString("video_id");
            title = getIntent().getExtras().getString("title");
            tile_id = getIntent().getExtras().getString("tile_id");
            tile_type = getIntent().getExtras().getString("tile_type");
            course_id = getIntent().getExtras().getString("course_id");
            cookieDatas = new CookieData();
            if (getIntent().getExtras().getString("objectcookie") != null && !getIntent().getExtras().getString("objectcookie").equals("")) {
                objectcookie = getIntent().getExtras().getString("objectcookie");
                try {
                    JSONObject jsonObject = new JSONObject(objectcookie);
                    String cloudFrontKeyPairId = jsonObject.optString(Const.CloudFront_Key_Pair_Id);
                    String cloudFrontSignature = jsonObject.optString(Const.CloudFront_Signature);
                    String cloudFrontExpires = jsonObject.optString(Const.CloudFront_Expires);
                    cookieDatas.setCloudFrontKeyPairId(cloudFrontKeyPairId);
                    cookieDatas.setCloudFrontSignature(cloudFrontSignature);
                    cookieDatas.setCloudFrontExpires(cloudFrontExpires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                cookieDatas.setCloudFrontKeyPairId("");
                cookieDatas.setCloudFrontSignature("");
                cookieDatas.setCloudFrontExpires("");
            }

            if (!getIntent().getExtras().getString("url_object").equalsIgnoreCase("")) {
                qualiity_array = new Gson().fromJson(getIntent().getExtras().getString("url_object"), new TypeToken<ArrayList<UrlObject>>() {
                }.getType());
            }
            current_pos = getIntent().getExtras().getLong("current_pos", 0L);
            playPosition = current_pos;
        }


        if (savedInstanceState != null) {
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        loadControl = new DefaultLoadControl.Builder()
                .setAllocator(new DefaultAllocator(true, 16))
                .setBufferDurationsMs(CustomMediaPlayerConfig.MAX_BUFFER_DURATION,
                        CustomMediaPlayerConfig.MAX_BUFFER_DURATION,
                        CustomMediaPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                        CustomMediaPlayerConfig.MIN_PLAYBACK_START_BUFFER)
                .setTargetBufferBytes(-1)
                .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl();
        //
        parent_layout = findViewById(R.id.parent_layout);
        recyclerChat = findViewById(R.id.recycler_view);
        bookmarkBtn = findViewById(R.id.bookmark_btn);
        addBookmark = findViewById(R.id.add_bookmark);
        indexBtn = findViewById(R.id.index_btn);
        chat_btn = findViewById(R.id.chat_btn);
        notes = findViewById(R.id.notes);
        poll = findViewById(R.id.poll);
        retry = findViewById(R.id.retry);
        webView = findViewById(R.id.pdfViewPager);
        cross = findViewById(R.id.cross);
        expand = findViewById(R.id.expand);
        mConnectionClassManager = ConnectionClassManager.getInstance();
        mDeviceBandwidthSampler = DeviceBandwidthSampler.getInstance();
        //   mConnectionClass =     mConnectionClassManager.getCurrentBandwidthQuality();
        mListener = new ConnectionChangedListener();



        pdf_view_layout = findViewById(R.id.pdf_view_layout);
        //
        simpleExoPlayerView = findViewById(R.id.player_view_new);
        progressBar = findViewById(R.id.progress_bar);
        quality = findViewById(R.id.quality);
        rootView = findViewById(R.id.root_new);
        MarqueeText = findViewById(R.id.MarqueeText);
        video_name = findViewById(R.id.video_name);
        video_name.setSelected(true);
        video_name.setFocusable(true);
        video_name.setFocusableInTouchMode(true);
        video_name.setText(title);
        MarqueeText.setText(SharedPreference.getInstance().getLoggedInUser().getMobile());
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
        mediaDataSourceFactory = buildDataSourceFactory(false);
        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
        initFullscreenButton();
        initFullscreenDialog();
        try {
            OrientationEventListener orientationEventListener = new
                    OrientationEventListener(getApplicationContext()) {
                        @Override
                        public void onOrientationChanged(int orientation) {
                            try {
                                int result = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
                                if (result == 1) {
                                    boolean isPortrait = isPortrait(orientation);
                                    if (!isPortrait && savedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                                        savedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                                    } else if (isPortrait && savedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                                        savedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
            orientationEventListener.enable();
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  findViewById(R.id.quality).setOnClickListener(view -> {
            if (player != null) {
                showPopUpMenu("quality", quality);
            } else {
                Snackbar.make(rootView, "Reloading video. Please wait...", Snackbar.LENGTH_LONG).show();
            }
        });*/
        indexBtn.setOnClickListener(new OnSingleClickListener(() -> {
            pdf_view_layout.setVisibility(View.GONE);
            setIndex();
            return null;
        }));
        notes.setOnClickListener(new OnSingleClickListener(() -> {
            setNotes();
            return null;
        }));
        bookmarkBtn.setOnClickListener(new OnSingleClickListener(() -> {
            pdf_view_layout.setVisibility(View.GONE);
            setBookMark();
            return null;
        }));
        hit_api_for_meta();
        checkNetwork(new NetworkCheckerInterface() {
            @Override
            public void continueExecution() {
                startInternetSpeedTimer();
                try {
                    Log.d(TAG, "init to be called");
                    if (url != null && !url.equalsIgnoreCase("")) {
                        //  url = url.replace("https", "http");
                        extractJWUrl(url);
                        parent_layout.setVisibility(View.VISIBLE);
                    } else {
                        showRestartAlertDialog();
                    }
                } catch (NullPointerException e) {
                    Log.e(TAG, "init method try catch", e);
                }
            }

            @Override
            public void cancelExecution() {
                onBackPressed();
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerError) {
                    if (player != null) {
                        player.removeListener(playerEventListener);
                        player.release();
                        playerError = false;
                    }
                    playerError = false;
                    retry.setVisibility(View.GONE);
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);
                }
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver = new InternetConnectionChecker() {
            @Override
            public void onReceive(Context context, Intent intent) {
                networkStatusChecker(Helper.isNetworkConnected(JWVideoPlayer.this));
            }
        };
        playBtn.setOnClickListener(view -> {
            if (Helper.isNetworkConnected(this)) {
                if (playerError) {
                    if (player != null) {
                        player.removeListener(playerEventListener);
                        player.release();
                        playerError = false;
                        if (!youtubeUri.equalsIgnoreCase("")) {
                            Loadyoutubeurl(youtubeUri, true);
                        } else {
                            showRestartAlertDialog();
                        }
                    }
                }
                if (player != null && player.getPlaybackState() == Player.STATE_READY) {
                    playBtn.setVisibility(View.GONE);
                    pauseBtn.setVisibility(View.VISIBLE);
                    player.setPlayWhenReady(true);
                }
            } else {
                Snackbar.make(rootView, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();
            }
        });
        pauseBtn.setOnClickListener(view -> {
            if (Helper.isNetworkConnected(this)) {
                if (player != null && player.getPlaybackState() == Player.STATE_READY) {
                    playBtn.setVisibility(View.VISIBLE);
                    pauseBtn.setVisibility(View.GONE);
                    player.setPlayWhenReady(false);
                }
            } else {
                Snackbar.make(rootView, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private int savedOrientation;

    private boolean isPortrait(int orientation) {
        com.google.android.exoplayer2.util.Log.d("prince", "" + orientation);
        if (orientation < 85 || orientation > 100) {
            return true;
        }
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showPopUpMenu(String action, View anchorView) {
        // Inflate the popup_layout.xml
        View layout = getLayoutInflater().inflate(R.layout.video_pop_up_menu_layout, null);
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupList = layout.findViewById(R.id.videopopuplist);
        mLayoutManager = new LinearLayoutManager(JWVideoPlayer.this);
        popupList.setLayoutManager(mLayoutManager);
        popupList.setHasFixedSize(true);
        //Set Pop Up Menu Adapter
        switch (action) {
            case "quality":
                if (sparseAdaptiveVideoUrlList_new != null && !sparseAdaptiveVideoUrlList_new.isEmpty() && player != null && sparseAdaptiveVideoUrlList_new.size() > 0) {

                    mAdapter = new VideoPopUpMenuAdapter(JWVideoPlayer.this, sparseAdaptiveVideoUrlList_new,
                            player, "quality", stringCurrentVideoSource, true, false, false);
                    popupList.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
        popupWindow.setOutsideTouchable(true);
        // Clear the default translucent background
        // Needed specifically for Android 5.0.1 and 5.1.1
        // If this is not there, Touch on Custom Video Player won't work as expected
        // Do not remove this line, or else Custom Video Player will not work accordingly
        // Don't set BackgroundDrawable to null
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        int[] location = new int[2];
        Rect rect = new Rect();
        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationInWindow(location);
        anchorView.getLocalVisibleRect(rect);
        if (newOrientation == Configuration.ORIENTATION_PORTRAIT) {
            popupWindow.showAsDropDown(anchorView);
        } else {
            if (action.equalsIgnoreCase("speed")) {
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, location[0] + (anchorView.getWidth() / 2),
                        location[1] + anchorView.getHeight() + 50);
            } else {
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, location[0] + (anchorView.getWidth() / 2),
                        location[1] + anchorView.getHeight() + 50);
            }
        }
    }

    private Timer getInternetSpeedTimerInstance() {
        if (internetSpeedTimer == null)
            internetSpeedTimer = new Timer();
        return internetSpeedTimer;
    }

    private void checkNetwork(NetworkCheckerInterface networkCheckerInterface) {
        checkNetwork(true, networkCheckerInterface);
    }

    @SuppressLint("SetTextI18n")
    private void showRestartAlertDialog() {
        if (isFinishing() || isDestroyed())
            return;
        final androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(JWVideoPlayer.this);
        dialog.setCancelable(false);
        final androidx.appcompat.app.AlertDialog alertDialog = dialog.create();
        View dialogView = getLayoutInflater().inflate(R.layout.retry_video_dialog, null, false);
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (!((Activity) JWVideoPlayer.this).isFinishing()) {
            alertDialog.show();
        }
        TextView dialogBodyTxtView = dialogView.findViewById(R.id.body);
        TextView dialogTitleTextView = dialogView.findViewById(R.id.title);
        dialogTitleTextView.setText(title);
        dialogBodyTxtView.setText("Video playback has failed. Please wait and try again. Your patience is appreciated.");
        Button retryBtn = dialogView.findViewById(R.id.positiveBtn);
        Button cancelBtn = dialogView.findViewById(R.id.negativeBtn);
        retryBtn.setOnClickListener(view -> {
            if (url == null || url.equalsIgnoreCase("")) {
                Snackbar.make(rootView, "Video Getting issue. Please relaod again", Snackbar.LENGTH_LONG).show();
                onBackPressed();
            } else {
                Snackbar.make(rootView, "Reloading video. Please wait...", Snackbar.LENGTH_LONG).show();
                extractJWUrl(url);
            }
            alertDialog.dismiss();
        });
        cancelBtn.setOnClickListener(view -> {
            alertDialog.dismiss();
            onBackPressed();
        });
    }

    @SuppressLint("MissingPermission")
    private void checkNetwork(boolean showDialogWhenSlow, NetworkCheckerInterface networkCheckerInterface) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        int downSpeed = 0;
        NetworkCapabilities nc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
        }
        if (nc != null) {
            downSpeed = nc.getLinkDownstreamBandwidthKbps();
        }
       /* NetworkInfo info = cm.getActiveNetworkInfo();
        if(info.getType() == ConnectivityManager.TYPE_WIFI){
            // do something
        } else if(info.getType() == ConnectivityManager.TYPE_MOBILE) {
            if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                Log.d("prince", "Bandwidth between 100 kbps and below");
                //
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                // Bandwidth between 50-100 kbps
                Log.d("prince", "Bandwidth 100-120 kbps");
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
                // Bandwidth between 400-1000 kbps
                Log.d("prince", "between 400-1000 kbps");
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "between 600-1400 kbps");
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_1xRTT) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "between 50-100 kbps");
            }
            else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "between 14-64 kbps kbps");
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "between 14-64 kbps kbps");
            } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSDPA) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "between  2-14 Mbps");
            }else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSPA) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "between  0.7-1.7 Mbps");
            }else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_HSUPA) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "between  1-23  Mbps");
            }else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE) {
                // Bandwidth between 600-1400 kbps
                Log.d("prince", "https://riptutorial.com/android/example/18294/how-to-check-network-strength");
            }
        }*/
        String roundedDownSpeed = String.format("%.2f", (((double) downSpeed / 8) / 1000) / 8);
        String downSpeedString = "DownSpeed :" + roundedDownSpeed + " Mb/s";
        if (((((double) downSpeed / 8) / 1000) / 8) <= 5) {
            if (showDialogWhenSlow) {
                networkCheckerInterface.continueExecution();
                //showSlowNetworkAlertDialog(cm, downSpeedString, networkCheckerInterface);
            } else {
                networkCheckerInterface.onUnstableInternet();
            }
        } else {
            networkCheckerInterface.continueExecution();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showSlowNetworkAlertDialog(ConnectivityManager netInfo, String downSpeed, NetworkCheckerInterface networkCheckerInterface) {
        if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
            return;
        }
        mLastClickTime_frame5 = SystemClock.elapsedRealtime();
        Dialog dialog;
        dialog = new Dialog(JWVideoPlayer.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.slow_network_dialog);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if (!isDestroyed() && !isFinishing())
            dialog.show();
        TextView dialogBodyTxtView = dialog.findViewById(R.id.body);
        TextView dialogBody2TxtView = dialog.findViewById(R.id.body2);
        TextView dialogTitleTextView = dialog.findViewById(R.id.title);
        dialogTitleTextView.setText("Network Information");
        dialogBodyTxtView.setText("You are currently connected to: \n" + netInfo.getActiveNetworkInfo().getTypeName() == null ? "Internet" : netInfo.getActiveNetworkInfo().getTypeName() + " Network");
        dialogBody2TxtView.setText("Slow network speed will effect video experience.");
        Button continueBtn = dialog.findViewById(R.id.positiveBtn);
        Button cancelBtn = dialog.findViewById(R.id.negativeBtn);
        continueBtn.setOnClickListener(view -> {
            networkCheckerInterface.continueExecution();
            dialog.dismiss();
        });
        cancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
            networkCheckerInterface.cancelExecution();
        });
    }

    private void startInternetSpeedTimer() {
        if (getInternetSpeedTimerInstance() != null) {
            internetCheckDelay += internetCheckDelayMultiple;
            internetSpeedTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!isDestroyed()) {
                        checkNetwork(false, new NetworkCheckerInterface() {
                            @Override
                            public void onUnstableInternet() {
                                if (!isFinishing() && !isDestroyed()) {
                                    android.util.Log.e(TAG, "onUnstableInternet: " + internetCheckDelay);
                                    //   String msg = "Your internet connection is unstable";
                                    // Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG).show();
                                    resetInternetSpeedTimer();
                                }
                            }

                            @Override
                            public void continueExecution() {
                                if (!isFinishing() && !isDestroyed()) {
                                    android.util.Log.e(TAG, "onUnstableInternet: No" + internetCheckDelay);
                                    resetInternetSpeedTimer();
                                }
                            }

                            @Override
                            public void cancelExecution() {
                            }
                        });
                    }
                }
            }, internetCheckDelay * 1000);
        }
    }

    private void resetInternetSpeedTimer() {
        cancelInternetSpeedTimer();
        startInternetSpeedTimer();
    }

    private void hit_api_for_meta() {
        networkCall.NetworkAPICall(API.get_meta, "", false, false);
    }

    private void cancelInternetSpeedTimer() {
        if (internetSpeedTimer != null) {
            internetSpeedTimer.cancel();
            internetSpeedTimer = null;
        }
    }

    private void setBookMark() {
        isclicked = "2";
        recyclerChat.setVisibility(View.VISIBLE);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        addBookmark.setVisibility(View.VISIBLE);
        disableAll();
        addBookmark.setVisibility(View.VISIBLE);
        bookmarkBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        bookmarkBtn.setTextColor(getResources().getColor(android.R.color.black));
        setbookmarkadapter();
    }

    private void setbookmarkadapter() {
        bookmarkAdapter = new BookmarkAdapter(this, bookmarkdata, "jwplayer");
        recyclerChat.setAdapter(bookmarkAdapter);
    }

    private void setNotes() {
        isclicked = "4";
        addBookmark.setVisibility(View.GONE);
        recyclerChat.setVisibility(View.VISIBLE);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        disableAll();
        notes.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        notes.setTextColor(getResources().getColor(android.R.color.black));
        setNotesAdapter();
    }

    private void setNotesAdapter() {
        notesAdapter = new NotesAdapter(this, pdf, course_id);
        recyclerChat.setAdapter(notesAdapter);
//        recyclerChat.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    void disableAll() {
        bookmarkBtn.setBackgroundColor(getResources().getColor(R.color.off_white));
        indexBtn.setBackgroundColor(getResources().getColor(R.color.off_white));
        chat_btn.setBackgroundColor(getResources().getColor(R.color.off_white));
        poll.setBackgroundColor(getResources().getColor(R.color.off_white));
        notes.setBackgroundColor(getResources().getColor(R.color.off_white));
        addBookmark.setVisibility(View.GONE);
//        vodchatbtn.setBackgroundColor(getResources().getColor(R.color.off_white));
    }

    private void setIndex() {
        isclicked = "3";
        addBookmark.setVisibility(View.GONE);
        recyclerChat.setVisibility(View.VISIBLE);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        disableAll();
        indexBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        indexBtn.setTextColor(getResources().getColor(android.R.color.black));
        setAdapter();
    }

    private void setAdapter() {
        adapter = new Adapter_recycleveiw_vedio(this, indexdata, "jw");
        recyclerChat.setAdapter(adapter);
    }

    private void extractJWUrl(String url) {
/*
        new JWPlayerMediaExtractor(new JWPlayerExtractorCallBack() {
            @Override
            public void onExtractorSuccess(int responseCode, HashMap<Integer, String> mediaResponseMap, boolean isVideoLive) {
                String value = mediaResponseMap.get(-1);
                if (value != null) {
                    sparseAdaptiveResolutionList = new ArrayList<>(mediaResponseMap.keySet());
                    sparseAdaptiveVideoUrlList = new HashMap<>();
                    sparseAdaptiveVideoUrlList = mediaResponseMap;
                    if (sparseAdaptiveVideoUrlList != null && sparseAdaptiveVideoUrlList.size() > 0) {
                        Collections.sort(sparseAdaptiveResolutionList, Collections.reverseOrder());
                        youtubeUri = sparseAdaptiveVideoUrlList.get(180);
                        currentVideoSource = 180;
                        progressBar.setVisibility(View.GONE);
                        Loadyoutubeurl(youtubeUri);
                    }
                }
            }
            @Override
            public void onExtractorError(int responseCode, String error) {
                progressBar.setVisibility(View.GONE);
                showRestartAlertDialog();
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
*/
        if (qualiity_array != null && qualiity_array.size() > 0) {
            sparseAdaptiveVideoUrlList_new = new HashMap<>();
            int i = 0;
            for (UrlObject urlObject : qualiity_array) {
                sparseAdaptiveVideoUrlList_new.put(i, urlObject);
                i++;
            }
            youtubeUri = url;
            stringCurrentVideoSource = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(0)).getTitle();
            progressBar.setVisibility(View.GONE);
            Loadyoutubeurl(youtubeUri, true);
        } else {
            progressBar.setVisibility(View.GONE);
            showRestartAlertDialog();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            newOrientation = newConfig.orientation;
            if (newConfig.orientation == 1) {
                if (isclicked.equalsIgnoreCase("1")) {
                }
                if (isclicked.equalsIgnoreCase("2")) {
                    addBookmark.setVisibility(View.VISIBLE);
                }
                mExoPlayerFullscreen = false;
                mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreen));
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                final float scale = getResources().getDisplayMetrics().density;
                int pixels = (int) (280 * scale + 0.5f);
                boolean xlarge = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
                boolean large = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
                if (large) {
                    pixels = (int) (350 * scale + 0.5f);
                } else if (xlarge) {
                    pixels = (int) (400 * scale + 0.5f);
                } else {
                    pixels = (int) (230 * scale + 0.5f);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
                rootView.setLayoutParams(layoutParams);
            } else {
                mExoPlayerFullscreen = true;
                mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreenexit));
                addBookmark.setVisibility(View.GONE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                rootView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Loadyoutubeurl(String youtubeUri, boolean b) {
        if (isFinishing() || isDestroyed())
            return;
        progressBar.setVisibility(View.VISIBLE);
        Uri videouri = Uri.parse(youtubeUri);
        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultRenderersFactory(this), trackSelector, loadControl);
        player.addListener(playerEventListener);


        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        mediaSource = buildMediaSource(videouri, null);
        player.prepare(mediaSource);
        player.seekTo(playPosition);


        if (!b) {
            if (player != null) {
                if (!spped_title.equalsIgnoreCase("")) {
                    speedTV.setText(spped_title);
                    if (spped_title.equalsIgnoreCase("Normal")) {
                        player.setPlaybackParameters(new PlaybackParameters(Float.valueOf("1"), 1f));
                    } else {
                        player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(spped_title.replace("x", "")), 1f));
                    }
                }

            }
        }
        try {
            UserHistroyTable userHistroyTable = new UserHistroyTable();
            userHistroyTable.setVideo_id(video_id);
            userHistroyTable.setVideo_name(title);
            userHistroyTable.setType("Video");
            userHistroyTable.setCourse_id(course_id);
            userHistroyTable.setUser_id(MakeMyExam.userId);
            userHistroyTable.setTileid(tile_id);
            userHistroyTable.setTopicname(tile_type);
            userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
            utkashRoom.getuserhistorydao().addUser(userHistroyTable);

           /* AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.isWiredHeadsetOn()) {
                MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "exoplayer");
                MediaSessionConnector mediaSessionConnector = new MediaSessionConnector(mediaSessionCompat);
                mediaSessionConnector.setPlayer(player);
            } else {
                MediaSessionCompat mediaSession = new MediaSessionCompat(this, "exoplayer");
                MediaSessionConnector mediaSessionConnector = new MediaSessionConnector(mediaSession);
                mediaSessionConnector.setMediaButtonEventHandler((player1, controlDispatcher, mediaButtonEvent) -> {
                    KeyEvent event = mediaButtonEvent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        switch (event.getKeyCode()) {

                            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                                pausePlayer();
                                break;
                            case KeyEvent.KEYCODE_MEDIA_PLAY:
                                if (player != null) {
                                    player.setPlayWhenReady(true);
                                    player.getPlaybackState();
                                }
                                break;
                        }
                    } else if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (event.getKeyCode()) {

                            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                                pausePlayer();
                                break;
                            case KeyEvent.KEYCODE_MEDIA_PLAY:
                                if (player != null) {
                                    player.setPlayWhenReady(true);
                                    player.getPlaybackState();
                                }
                                break;

                        }
                    }
                    return true;
                });
                mediaSessionConnector.setPlayer(simpleExoPlayerView.getPlayer());
            }*/
            ArrayList<UrlObject> sppedlist = Helper.speedlist();
            if (sppedlist.get(speedpostiion).getTitle().equalsIgnoreCase("Normal")) {
                player.setPlaybackParameters(new PlaybackParameters(Float.valueOf("1"), 1f));
            } else {
                player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(sppedlist.get(speedpostiion).getTitle().replace("x", "")), 1f));
            }


//            MediaSessionCompat mediaSessionCompat =new MediaSessionCompat(this,"exoplayer");
//            MediaSessionConnector mediaSessionConnector =new MediaSessionConnector(mediaSessionCompat);
//            mediaSessionConnector.setPlayer(player);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSpeedOptions() {
        PopupMenu popupMenu = new PopupMenu(this, icon, R.style.MyPopupMenu);
        Menu menu = popupMenu.getMenu();
        final String[] speeds = getResources().getStringArray(R.array.speed_values);
        if (speeds.length != 0) {
            for (String speed : speeds) {
                if (speed.equalsIgnoreCase("1")) {
                    menu.add("Normal");
                } else {
                    menu.add(speed + "x");
                }
            }
            popupMenu.setOnMenuItemClickListener(item -> {
                spped_title = item.getTitle().toString();
                if (player != null) {
                    //speedTV.setText(spped_title);
                    if (spped_title.equalsIgnoreCase("Normal")) {
                        player.setPlaybackParameters(new PlaybackParameters(Float.valueOf("1"), 1f));
                    } else {
                        player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(spped_title.replace("x", "")), 1f));
                    }
                }
                return false;
            });
            popupMenu.show();
        }
    }

    private Player.EventListener playerEventListener = new Player.DefaultEventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            if (isInitialResChanged) {
                isInitialResChanged = false;
                if (TrackSelectionDialog.willHaveContent(trackSelector)) {
                    trackSelectorParameters = trackSelector.buildUponParameters().setForceLowestBitrate(true).build();
                    trackSelector.setParameters(trackSelectorParameters);
                }
            }
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_READY) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            qulaity_pos = 0;
            if (error != null) {
                if (error.getMessage() != null && error.getMessage().contains("Unable to instantiate decoder")) {
                    Snackbar.make(rootView, "" + error.getMessage(), Snackbar.LENGTH_LONG).show();
                    if (player != null)
                        playPosition = player.getCurrentPosition();
                    playerError = true;
                } else {
                    if (!Helper.isNetworkConnected(JWVideoPlayer.this)) {
                        if (progressBar.getVisibility() == View.VISIBLE) {
                            progressBar.setVisibility(View.GONE);
                        }
                        if (player != null)
                            playPosition = player.getCurrentPosition();
                        playerError = true;
                        Snackbar.make(rootView, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();
                    } else if (Objects.requireNonNull(error.getMessage()).contains("302")) {
                        if (player != null)
                            playPosition = player.getCurrentPosition();
                        playerError = true;
                        Snackbar.make(rootView, "No Internet. Please connect device to internet1" + error.getMessage(), Snackbar.LENGTH_LONG).show();
                    } else if (error.type != ExoPlaybackException.TYPE_SOURCE || error.getMessage().contains("IllegalState")) {
                        Snackbar.make(rootView, "Error While Streaming Video, Please wait and we are trying again. Your patience is appreciated.", Snackbar.LENGTH_LONG).show();
                    } else if (error.getSourceException() != null) {
                        if (error.getSourceException() instanceof HttpDataSource.HttpDataSourceException) {
                            Snackbar.make(rootView, "Video buffering issues. Please click on retry button", Snackbar.LENGTH_LONG).show();
                            if (player != null)
                                playPosition = player.getCurrentPosition();
                            playerError = true;
                            retry.setVisibility(View.VISIBLE);
                        } else {
                            Snackbar.make(rootView, "No Internet. Please connect device to internet2" + (error.getMessage() == null ? error : error.getMessage()), Snackbar.LENGTH_LONG).show();
                        }
                    } else if (error.getSourceException() instanceof HttpDataSource.HttpDataSourceException) {
                        if (progressBar.getVisibility() == View.VISIBLE) {
                            progressBar.setVisibility(View.GONE);
                        }
                        playPosition = player.getCurrentPosition();
                        Toast.makeText(JWVideoPlayer.this, "" + error.getSourceException() + "_" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        playerError = true;
                    }
                }
            }
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        @Override
        public void onSeekProcessed() {
        }
    };
/*

    private MediaSource buildMediaSource(Uri uri, @androidx.annotation.Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), buildDataSourceFactory(false)).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), buildDataSourceFactory(false)).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

*/

    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(mediaDataSourceFactory), buildDataSourceFactory(false)).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), buildDataSourceFactory(false)).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);

            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri);

            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }


    private static String youtubeUri = "";
    private static int currentVideoSource = 0;
    private static String stringCurrentVideoSource;

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(TransferListener listener) {
        DefaultDataSourceFactory dataSourceFactory = null;
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");

        if (cookieDatas != null && !TextUtils.isEmpty(cookieDatas.getCloudFrontSignature()) && !TextUtils.isEmpty(cookieDatas.getCloudFrontSignature()) && !TextUtils.isEmpty(cookieDatas.getCloudFrontKeyPairId())) {
            String cookieValue = "CloudFront-Policy=" + cookieDatas.getCloudFrontExpires() +
                    ";CloudFront-Signature=" + cookieDatas.getCloudFrontSignature() +
                    ";CloudFront-Key-Pair-Id=" + cookieDatas.getCloudFrontKeyPairId();
            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, listener);

            defaultHttpDataSourceFactory.setDefaultRequestProperty("Cookie", cookieValue);
            dataSourceFactory = new DefaultDataSourceFactory(this, listener, defaultHttpDataSourceFactory);
        } else {
            dataSourceFactory = new DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener));
        }

        return dataSourceFactory;
    }


    /*  public DataSource.Factory buildDataSourceFactory(TransferListener listener) {
        DefaultDataSourceFactory dataSourceFactory = null;
        dataSourceFactory = new DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener));
        return dataSourceFactory;
    }*/
    public HttpDataSource.Factory buildHttpDataSourceFactory(TransferListener listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onResume() {
        super.onResume();
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            PhoneStateListener callStateListener = new PhoneStateListener() {
                public void onCallStateChanged(int state, String incomingNumber) {
                    if (state == TelephonyManager.CALL_STATE_RINGING) {
                        oncall(true);
                    }
                    if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                        oncall(true);
                    }
                    if (state == TelephonyManager.CALL_STATE_IDLE) {
                        oncall(false);
                        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setMode(AudioManager.MODE_NORMAL);
                    }
                }
            };
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_CURRENT);
            if (audioManager.isMicrophoneMute() == false) {
                audioManager.setMicrophoneMute(true);
            } else {
                audioManager.setMicrophoneMute(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (playerError) {
            playerError = false;
            if (player != null) {
                player.setPlayWhenReady(true);
                player.getPlaybackState();
            } else if (!youtubeUri.equalsIgnoreCase("")) {
                Loadyoutubeurl(youtubeUri, true);
            } else {
                showRestartAlertDialog();
            }
        }
        mConnectionClassManager.register(mListener);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void oncall(final boolean b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b) {
                    // simpleExoPlayerView.Pause();
                    pausePlayer();
                } else {
                    // simpleExoPlayerView.Resume();
                    // adding on crashing issue
                    /// resumePlayer();
                }
            }
        });
    }

    private void networkStatusChecker(boolean networkConnected) {
        if (networkConnected) {
            if (playerError) {
                Snackbar.make(rootView, "Connected with internet, device is online", Snackbar.LENGTH_SHORT).show();
                if (player != null) {
                    player.removeListener(playerEventListener);
                    player.release();
                    playerError = false;
                    Loadyoutubeurl(youtubeUri, true);
                }
            }
        } else {
            Snackbar.make(rootView, "No internet connection found, device is offline.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelInternetSpeedTimer();
    }

    @Override
    public void onBackPressed() {
        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE) {
            closeFullscreenDialog();
        } else {
            if (player != null) {
                playPosition = player.getCurrentPosition();
                player.removeListener(playerEventListener);
                utkashRoom.getvideoDao().update_video_currentpos(video_id, MakeMyExam.userId, playPosition);
                player.release();
                player = null;
                playPosition = 0;
                youtubeUri = "";
            }
            cancelInternetSpeedTimer();
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        if (player != null) {
            playerError = true;
            playPosition = player.getCurrentPosition();
            utkashRoom.getvideoDao().update_video_currentpos(video_id, MakeMyExam.userId, playPosition);

        }
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
        mConnectionClassManager.remove(mListener);
        super.onPause();
    }

    PowerMenu powerMenu;

    private void initFullscreenButton() {
        newOrientation = getResources().getConfiguration().orientation;
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);

        playBtn = controlView.findViewById(R.id.exo_play);
        pauseBtn = controlView.findViewById(R.id.exo_pause);

        exo_ffwd = controlView.findViewById(R.id.exo_ffwd_new);
        exo_rew = controlView.findViewById(R.id.exo_rew_new);

        exo_ffwd.setOnClickListener(view -> {
            if (player.getCurrentPosition() < (player.getDuration() - 10000))
                player.seekTo(player.getCurrentPosition() + 10000);
        });
        exo_rew.setOnClickListener(view -> {
            if (player.getCurrentPosition() > 10000)
                player.seekTo(player.getCurrentPosition() - 10000);
        });


        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        trackSelector = new DefaultTrackSelector();
        trackSelector.setParameters(trackSelectorParameters);

        speedTV = controlView.findViewById(R.id.speedTV);
        tvGoLive = controlView.findViewById(R.id.tv_go_live);

        qualitysection = controlView.findViewById(R.id.qualitysection);
        if (qualiity_array != null && qualiity_array.size() > 0) {
            qualitysection.setVisibility(View.VISIBLE);
        }

        quality = findViewById(R.id.quality);
        quality.setVisibility(View.VISIBLE);

        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trackSelector != null && trackSelector.getCurrentMappedTrackInfo() != null) {
                    popupWindowPart();
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    Size size=new Size(popupWindow.getContentView().getMeasuredWidth(),popupWindow.getContentView().getHeight());

                    if (newOrientation == Configuration.ORIENTATION_PORTRAIT) {
                        popupWindow.showAtLocation(quality,Gravity.TOP|RIGHT,50,quality.getBottom());
                    }else {
                        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.TOP|RIGHT,0, (int) ((location[1]+size.getHeight())/2.8));
                    }

                 //   popupWindow.showAtLocation(view, Gravity.TOP|RIGHT, location[0]+(size.getWidth()-view.getWidth())/2, location[1]+size.getHeight());



                    //  popupWindow.showAsDropDown(view, 100, -view.getHeight() - 400, RIGHT);

                }
            }
        });

        qualitysection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trackSelector != null && trackSelector.getCurrentMappedTrackInfo() != null) {
                    if (sparseAdaptiveVideoUrlList_new != null && sparseAdaptiveVideoUrlList_new.size() > 0) {
                        popupWindowPart_quality();
                        int[] location = new int[2];
                        view.getLocationInWindow(location);
                        Size size=new Size(popupWindow_quality.getContentView().getMeasuredWidth(),popupWindow_quality.getContentView().getHeight());

                        if (!mExoPlayerFullscreen) {
                            popupWindow_quality.showAtLocation(qualitysection,Gravity.TOP|RIGHT,50,qualitysection.getBottom());
                        }else {
                            popupWindow_quality.showAtLocation(getWindow().getDecorView(), Gravity.TOP|RIGHT,0, (int) ((location[1]+size.getHeight())/2.8));
                        }


                       // popupWindow_quality.showAsDropDown(view, 0, -view.getHeight() - 400, RIGHT);
                    }
                }


            }
        });


        speedTV.setVisibility(View.GONE);

        icon = controlView.findViewById(R.id.icon);

        icon.setVisibility(View.GONE);


        if (speedTV != null) {
            speedTV.setText("Normal");
            speedTV.setOnClickListener(v -> showSpeedOptions());
        }
        if (tvGoLive != null) {
            tvGoLive.setOnClickListener(v -> goLive());
        }
       /* if (!TextUtils.isEmpty(speedx)) {
            player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(speedx.replace("x", "")), 1f));
        }

*/
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen) openFullscreenDialog();
                else closeFullscreenDialog();
            }
        });


        icon.setOnClickListener(new OnSingleClickListener(() -> {
            if (trackSelector != null && trackSelector.getCurrentMappedTrackInfo() != null) {
                BottomSheetDialog watchlist = new BottomSheetDialog(JWVideoPlayer.this, R.style.videosheetDialogTheme);
                watchlist.setContentView(R.layout.controller);
                Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                watchlist.setCancelable(false);
                watchlist.setCanceledOnTouchOutside(false);
                FrameLayout bottomSheet = watchlist.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior<FrameLayout> mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                mBottomSheetBehavior.setDraggable(false);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                RecyclerView qualityrecycler = watchlist.findViewById(R.id.qualityrecycler);

                RecyclerView sppedrecycler = watchlist.findViewById(R.id.sppedrecycler);
                Button track_selection_dialog_cancel_button = watchlist.findViewById(R.id.track_selection_dialog_cancel_button);
                track_selection_dialog_cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        watchlist.cancel();
                        watchlist.dismiss();
                    }
                });
                for (int i = 0; i < sparseAdaptiveVideoUrlList_new.size(); i++) {
                    if (i == qulaity_pos) {
                        Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(i)).setSelected(true);
                    } else {
                        Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(i)).setSelected(false);
                    }
                }
                Button track_selection_dialog_ok_button = watchlist.findViewById(R.id.track_selection_dialog_ok_button);
                Objects.requireNonNull(track_selection_dialog_ok_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        speedpostiion = tempspeed;
                        ArrayList<UrlObject> sppedlist = Helper.speedlist();
                        for (int i = 0; i < sppedlist.size(); i++) {
                            if (i == speedpostiion) {
                                sppedlist.get(speedpostiion).setSelected(true);
                            }
                        }
                        if (sppedlist.get(speedpostiion).getTitle().equalsIgnoreCase("Normal")) {
                            player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat("1"), 1f));
                        } else {
                            player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat(sppedlist.get(speedpostiion).getTitle().replace("x", "")), 1f));
                        }
                        if (player != null && tempqulity != qulaity_pos) {
                            playPosition = player.getCurrentPosition();
                            player.removeListener(playerEventListener);
                            if (player != null) {
                                playerError = false;
                                player.removeListener(playerEventListener);
                                player.release();
                                trackSelector = null;
                                player = null;
                            }
                            retry.setVisibility(View.GONE);
                            qulaity_pos = tempqulity;
                            youtubeUri = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(qulaity_pos)).getUrl();
                            Loadyoutubeurl(youtubeUri, false);
                            watchlist.cancel();
                            watchlist.dismiss();
                        } else {
                            watchlist.cancel();
                            watchlist.dismiss();
                        }
                    }
                });
                ImageView cross = watchlist.findViewById(R.id.cross);
                ArrayList<UrlObject> sppedlist = Helper.speedlist();

                for (int i = 0; i < sppedlist.size(); i++) {
                    if (i == speedpostiion) {
                        sppedlist.get(speedpostiion).setSelected(true);
                    }
                }
                Objects.requireNonNull(cross).setOnClickListener(view -> {
                    watchlist.cancel();
                    watchlist.dismiss();
                });


                SpeedoAdapter speedoAdapter = new SpeedoAdapter(JWVideoPlayer.this, sppedlist, this);
                Objects.requireNonNull(sppedrecycler).setAdapter(speedoAdapter);

                if (sparseAdaptiveVideoUrlList_new.size() > 0)
                    Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(qulaity_pos)).setSelected(true);

                ControllerAdapter controllerAdapter = new ControllerAdapter(JWVideoPlayer.this, sparseAdaptiveVideoUrlList_new, this);
                Objects.requireNonNull(qualityrecycler).setAdapter(controllerAdapter);


                if (!watchlist.isShowing()) {
                    watchlist.show();
                }
            } else {
                Toast.makeText(JWVideoPlayer.this, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();
            }

               /*   List<PowerMenuItem> list = new ArrayList<>();
            PowerMenuItem powerMenuItem1 = new PowerMenuItem(spped_title.equals("") ? "Normal" : spped_title, false);
            list.add(0, powerMenuItem1);
            PowerMenuItem powerMenuItem2 = new PowerMenuItem("Quality", false);
            list.add(1, powerMenuItem2);

            int width = getResources().getDisplayMetrics().widthPixels / 4;
            powerMenu = new PowerMenu.Builder(JWVideoPlayer.this)
                    .addItemList(list)
                    .setAnimation(MenuAnimation.SHOWUP_BOTTOM_RIGHT)
                    .setMenuRadius(10f)
                    .setMenuShadow(10f)
                    .setWidth(width)
                    .setTextColor(getResources().getColor(R.color.black))
                    .setMenuColor(Color.WHITE)
                    .setDivider(new ColorDrawable(ContextCompat.getColor(JWVideoPlayer.this, R.color.black))) // sets a divider.
                    .setDividerHeight(2)
                    .setSelectedTextColor(Color.WHITE)
                    .setSelectedMenuColor(ContextCompat.getColor(JWVideoPlayer.this, R.color.colorPrimary))
                    .setOnMenuItemClickListener(onIconMenuItemClickListener)
                    .build();
            powerMenu.showAsAnchorRightBottom(icon);*/
            return null;
        }));


       /* icon.setOnClickListener(view -> {
                if (trackSelector != null && trackSelector.getCurrentMappedTrackInfo() != null) {
                BottomSheetDialog watchlist = new BottomSheetDialog(JWVideoPlayer.this, R.style.videosheetDialogTheme);
                watchlist.setContentView(R.layout.controller);
                Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                watchlist.setCancelable(false);
                watchlist.setCanceledOnTouchOutside(true);
                FrameLayout bottomSheet = watchlist.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                mBottomSheetBehavior.setPeekHeight(300);

                RecyclerView qualityrecycler = watchlist.findViewById(R.id.qualityrecycler);

                RecyclerView sppedrecycler = watchlist.findViewById(R.id.sppedrecycler);
                ArrayList<UrlObject> sppedlist = Helper.speedlist();

                for (int i = 0; i < sppedlist.size(); i++) {
                    if (i == speedpostiion) {
                        sppedlist.get(speedpostiion).setSelected(true);
                    }
                }

                    for (int i = 0; i < sparseAdaptiveVideoUrlList_new.size(); i++) {
                        if (i == qulaity_pos) {
                            Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(i)).setSelected(true);
                        }else {
                            Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(i)).setSelected(false);
                        }
                    }
                SpeedoAdapter speedoAdapter = new SpeedoAdapter(JWVideoPlayer.this, sppedlist, player, this);
                Objects.requireNonNull(sppedrecycler).setAdapter(speedoAdapter);

                sparseAdaptiveVideoUrlList_new.get(qulaity_pos).setSelected(true);

                ControllerAdapter controllerAdapter = new ControllerAdapter(JWVideoPlayer.this, sparseAdaptiveVideoUrlList_new, player,this);
                Objects.requireNonNull(qualityrecycler).setAdapter(controllerAdapter);


                if (!watchlist.isShowing()) {
                    watchlist.show();
                }
            }else {
                Toast.makeText(JWVideoPlayer.this, "Player not ready, Please wait", Toast.LENGTH_SHORT).show();

            }







        });*/

    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void popupWindowPart() {

        popupWindow = new PopupWindow(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_speed, null);
        RecyclerView speed_recyclerview = view.findViewById(R.id.speed_recyclerview);
        RelativeLayout layput = view.findViewById(R.id.layput);

        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (150 * scale + 0.5f);
        int width = (int) (150 * scale + 0.5f);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
        layput.setLayoutParams(layoutParams);


        for (int i = 0; i < sppedlist.size(); i++) {
            if (i == speedpostiion) {
                sppedlist.get(speedpostiion).setSelected(true);
            }
        }

        SpeedoAdapter speedoAdapter = new SpeedoAdapter(JWVideoPlayer.this, sppedlist, this);
        Objects.requireNonNull(speed_recyclerview).setAdapter(speedoAdapter);

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_rectangle_gray));
        popupWindow.setWidth(width - 50);
        popupWindow.showAtLocation(view, Gravity.RIGHT, 0, 0);
        popupWindow.setContentView(view);


    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void popupWindowPart_quality() {

        popupWindow_quality = new PopupWindow(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_speed, null);
        RecyclerView speed_recyclerview = view.findViewById(R.id.speed_recyclerview);
        RelativeLayout layput = view.findViewById(R.id.layput);
        TextView video_speed = view.findViewById(R.id.video_speed);
        video_speed.setText("Video Quality");
        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (150 * scale + 0.5f);
        int width = (int) (150 * scale + 0.5f);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
        layput.setLayoutParams(layoutParams);

        for (int i = 0; i < sparseAdaptiveVideoUrlList_new.size(); i++) {
            if (i == qulaity_pos) {
                Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(i)).setSelected(true);
            } else {
                Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(i)).setSelected(false);
            }
        }
        if (sparseAdaptiveVideoUrlList_new.size() > 0)
            Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(qulaity_pos)).setSelected(true);


        ControllerAdapter controllerAdapter = new ControllerAdapter(JWVideoPlayer.this, sparseAdaptiveVideoUrlList_new, this);
        Objects.requireNonNull(speed_recyclerview).setAdapter(controllerAdapter);


        popupWindow_quality.setFocusable(true);
        popupWindow_quality.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_rectangle_gray));
        popupWindow_quality.setWidth(width - 50);
        popupWindow_quality.showAtLocation(view, RIGHT, 0, 0);
        popupWindow_quality.setContentView(view);


    }


    private final OnMenuItemClickListener<PowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            if (item.getTitle().equals("Quality")) {
                if (player != null) {
                    showPopUpMenu("quality", icon);
                } else {
                    Snackbar.make(rootView, "Reloading video. Please wait...", Snackbar.LENGTH_LONG).show();
                }
            } else {
                showSpeedOptions();
            }
            // item.setIsSelected(true);
            powerMenu.dismiss();
        }
    };


    private void goLive() {
        if (player != null) {
            player.seekTo(player.getDuration());
        }
    }

    private void openFullscreenDialog() {
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreenexit));
        mExoPlayerFullscreen = true;
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (newOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void closeFullscreenDialog() {
        mExoPlayerFullscreen = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreen));
    }

    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    public void showDialog1(View view) {
        if (player != null) {
            pausePlayer();
            final String time = formattedTime(player.getCurrentPosition());
            final Dialog dialog = new Dialog(this, R.style.CustomAlertDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.add_book_dialog);
            TextView timeTV = dialog.findViewById(R.id.text_dialog_time);
            timeTV.setText("Time :" + time);
            final EditText text = dialog.findViewById(R.id.nameTV);
            dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (player != null && player.getPlaybackState() == Player.STATE_READY) {
                        playBtn.setVisibility(View.GONE);
                        pauseBtn.setVisibility(View.VISIBLE);
                        player.setPlayWhenReady(true);
                    }
                    //simpleExoPlayerView.Resume();
                }
            });
            dialog.findViewById(R.id.btn_dialog_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String info = text.getText().toString();
                    if (info.length() > 0) {
                        dialog.dismiss();
                        Helper.hideKeyboard(JWVideoPlayer.this);
                        BookMarkApi("", time, info, "1");
                    } else {
                        Toast.makeText(JWVideoPlayer.this, "Add Title", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        } else {
            Toast.makeText(this, "Player is not initialize yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void BookMarkApi(String id, String time, String info, final String state) {
        this.time = time;
        this.info = info;
        this.state = state;
        networkCall.NetworkAPICall(API.add_video_index, "", false, false);
    }

    private String formattedTime(long millis) {
        if (millis < 1) {
            return "00:00:00";
        }
        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_meta:
                EncryptionData metadataencryptionData = new EncryptionData();
                metadataencryptionData.setUser_id(MakeMyExam.getUserId());
                metadataencryptionData.setToken(video_id);
                String metadataencryptionDatadoseStr = new Gson().toJson(metadataencryptionData);
                String metadatadoseStrScr = AES.encrypt(metadataencryptionDatadoseStr);
                return service.getmetaData(metadatadoseStrScr);
            case API.delete_video_index:
                EncryptionData deleteencryptionData = new EncryptionData();
                deleteencryptionData.setIndex_id(deletedindex);
                String deleteencryptionDatadoseStr = new Gson().toJson(deleteencryptionData);
                String metadeletedatadoseStrScr = AES.encrypt(deleteencryptionDatadoseStr);
                return service.deletevideoindex(metadeletedatadoseStrScr);
            case API.get_video_link:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setName(video_id + "_" + "0" + "_" + "0");
                if (course_id.contains("#")) {
                    String[] arr = course_id.split("#");
                    if (arr.length == 2) {
                        encryptionData.setCourse_id(arr[1]);
                    } else {
                        encryptionData.setCourse_id(arr[0]);
                    }
                } else {
                    encryptionData.setCourse_id(course_id);
                }
                encryptionData.setTile_id(tile_id);
                encryptionData.setType(tile_type);
                String device_id = (Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                String device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
                encryptionData.setDevice_id(device_id);
                encryptionData.setDevice_name(device_name);
                String data1 = new Gson().toJson(encryptionData);
                String encryptjson = AES.encrypt(data1);
                return service.getVideoLink(encryptjson);
            case API.add_video_index:
                EncryptionData metaindexencryptionData = new EncryptionData();
                metaindexencryptionData.setVideo_id(video_id);
                metaindexencryptionData.setTime(time);
                metaindexencryptionData.setInfo(info);
                String metaindexencryptionDatadoseStr = new Gson().toJson(metaindexencryptionData);
                String metaindexdoseStrScr = AES.encrypt(metaindexencryptionDatadoseStr);
                return service.addvideoindex(metaindexdoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        MakeMyExam.setTime_server((Long.parseLong(jsonstring.optString("time"))) * 1000);
        switch (apitype) {
            case API.get_meta:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        Metadata metadata = new Gson().fromJson(jsonstring.toString(), Metadata.class);
                        indexdata.addAll(metadata.getData().getIndex());
                        bookmarkdata.addAll(metadata.getData().getBookmark());
                        pdf.addAll(metadata.getData().getPdf());
                        int ispdf = 0, isindex = 0, poll = 0;
                        if (pdf.size() == 0) {
                            ispdf = 0;
                        } else {
                            ispdf = 1;
                        }
                        if (indexdata.size() == 0) {
                            isindex = 0;
                        } else {
                            isindex = 1;
                        }
                        setBookMark();
                        isvisiblelayouts(0, isindex, 1, 0, ispdf);
                    } else {
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case API.delete_video_index:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        bookmarkdata.remove(pos);
                        bookmarkAdapter.notifyDataSetChanged();
                    } else {
                        this.ErrorCallBack(jsonstring.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(this, jsonstring.getString(Const.AUTH_CODE), jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                }
                break;
            case API.get_video_link:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        retry.setVisibility(View.GONE);

                        progressBar.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = new JSONObject(jsonstring.toString());
                        if (jsonObject.has("data")) {
                            JSONObject dataJsonObject = jsonObject.getJSONObject(Const.DATA);

                            if (Objects.requireNonNull(dataJsonObject).has("cookie") && dataJsonObject.optJSONObject("cookie") != null) {
                                String cloudFrontKeyPairId = dataJsonObject.optJSONObject("cookie").optString(Const.CloudFront_Key_Pair_Id);
                                String cloudFrontSignature = dataJsonObject.optJSONObject("cookie").optString(Const.CloudFront_Signature);
                                String cloudFrontExpires = dataJsonObject.optJSONObject("cookie").optString(Const.CloudFront_Expires);
                                cookieDatas.setCloudFrontKeyPairId(cloudFrontKeyPairId);
                                cookieDatas.setCloudFrontSignature(cloudFrontSignature);
                                cookieDatas.setCloudFrontExpires(cloudFrontExpires);
                            } else {
                                cookieDatas.setCloudFrontKeyPairId("");
                                cookieDatas.setCloudFrontSignature("");
                                cookieDatas.setCloudFrontExpires("");
                            }
                            mediaDataSourceFactory = buildDataSourceFactory(false);


                            expire_time = (Long.parseLong(jsonstring.optString("time"))) + dataJsonObject.optLong("expiry_seconds");
                            youtubeUri = dataJsonObject.optString("link");


                            Log.d("prince_new", "" + youtubeUri);
                            if (dataJsonObject.has("bitrate_urls")) {
                                JSONArray arrJson = dataJsonObject.optJSONArray("bitrate_urls");
                                if (arrJson != null && arrJson.length() > 0) {
                                    ArrayList<UrlObject> urlObjects = new ArrayList<>();
                                    for (int i = 0; i < Objects.requireNonNull(arrJson).length(); i++) {
                                        JSONObject dataObj = arrJson.optJSONObject(i);
                                        UrlObject urlObject = new UrlObject();
                                        urlObject.setTitle(dataObj.optString("title"));
                                        urlObject.setUrl(dataObj.optString("url"));
                                        urlObject.setToken("");
                                        urlObjects.add(urlObject);
                                    }
                                    qualiity_array.clear();
                                    qualiity_array.addAll(urlObjects);
                                }
                            }
                            if (qualiity_array != null && qualiity_array.size() > 0) {
                                sparseAdaptiveVideoUrlList_new = new HashMap<>();
                                int i = 0;
                                for (UrlObject urlObject : qualiity_array) {
                                    sparseAdaptiveVideoUrlList_new.put(i, urlObject);
                                    i++;
                                }
                                if (qulaity_pos != 0) {
                                    stringCurrentVideoSource = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(qulaity_pos)).getTitle();
                                    youtubeUri = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(qulaity_pos)).getUrl();
                                } else {
                                    stringCurrentVideoSource = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(0)).getTitle();
                                }
                                progressBar.setVisibility(View.GONE);
                                Loadyoutubeurl(youtubeUri, false);
                            } else {
                                showRestartAlertDialog();
                            }
                        }
                    } else {
                        this.ErrorCallBack(jsonstring.getString(Const.MESSAGE), apitype, typeApi);
                        RetrofitResponse.GetApiData(this, jsonstring.getString(Const.AUTH_CODE), jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                }
                break;
            case API.add_video_index:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        Addindex metadata = new Gson().fromJson(jsonstring.toString(), Addindex.class);
                        bookmarkdata.add(metadata.getData());
                        bookmarkAdapter.notifyItemChanged(bookmarkdata.size());
                        if (player != null && player.getPlaybackState() == Player.STATE_READY) {
                            playBtn.setVisibility(View.GONE);
                            pauseBtn.setVisibility(View.VISIBLE);
                            player.setPlayWhenReady(true);
                        }
                    } else {
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
    }

    public void isvisiblelayouts(int ispoll, int isindex, int isbookmark, int islivechat, int ispdf) {
        recyclerChat.setVisibility(View.VISIBLE);
        poll.setVisibility(View.VISIBLE);
        bookmarkBtn.setVisibility(View.VISIBLE);
        indexBtn.setVisibility(View.VISIBLE);
        chat_btn.setVisibility(View.VISIBLE);
        notes.setVisibility(View.VISIBLE);
        if (ispoll == 0) {
            poll.setVisibility(View.GONE);
        }
        if (isbookmark == 0) {
            bookmarkBtn.setVisibility(View.GONE);
        }
        if (isindex == 0) {
            indexBtn.setVisibility(View.GONE);
        }
        if (ispdf == 0) {
            notes.setVisibility(View.GONE);
        }
        if (islivechat == 0) {
            chat_btn.setVisibility(View.GONE);
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    int pos;

    public void onDelete(VideoTimeFramePojo data, int position) {
        BookMarkDeleteApi(data.getId(), "", "", "2");
        this.pos = position;
    }

    private void BookMarkDeleteApi(String id, String s, String s1, String s2) {
        this.deletedindex = id;
        networkCall.NetworkAPICall(API.delete_video_index, "", false, false);
    }

    public void setVideoTimeMS(int timeMS) {
        if (player != null && player.getPlayWhenReady()) {
            player.seekTo(timeMS);
        } else {
            Toast.makeText(this, "Please wait player is not ready", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    public void changeVideoQuality(String quality, int position) {
        if (!stringCurrentVideoSource.equalsIgnoreCase(quality)) {
            if (player != null && sparseAdaptiveVideoUrlList_new != null && sparseAdaptiveVideoUrlList_new.size() > 0) {
                playPosition = player.getCurrentPosition();
                player.removeListener(playerEventListener);
                if (player != null) {
                    playerError = false;
                    player.removeListener(playerEventListener);
                    player.release();
                    player = null;
                }
                retry.setVisibility(View.GONE);

                if (MakeMyExam.getTime_server() != 0 && MakeMyExam.getTime_server() >= (expire_time * 1000)) {
                    qulaity_pos = position;
                    networkCall.NetworkAPICall(API.get_video_link, "", true, false);
                } else {
                    if (MakeMyExam.getTime_server() == 0) {
                        qulaity_pos = 0;
                        networkCall.NetworkAPICall(API.get_video_link, "", true, false);
                    } else
                        stringCurrentVideoSource = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(position)).getTitle();
                    ;
                    youtubeUri = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(position)).getUrl();
                    VideoPopUpMenuAdapter.selectedPlayBackSpeed = 3;
                    Loadyoutubeurl(youtubeUri, false);
                }
                if (popupList != null && popupWindow != null && popupWindow.isShowing()) {
                    new Handler().postDelayed(() -> popupWindow.dismiss(), 250);
                }
            }
        }
    }

    @Override
    public void selectSpeedPos(int pos) {
        if (popupWindow != null && popupWindow.isShowing()) {
            new Handler().postDelayed(() -> popupWindow.dismiss(), 250);
        }
        speedpostiion = pos;
        for (int i = 0; i < sppedlist.size(); i++) {
            if (i == speedpostiion) {
                sppedlist.get(speedpostiion).setSelected(true);
            }
        }
        if (sppedlist.get(speedpostiion).getTitle().equalsIgnoreCase("Normal")) {
            player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat("1"), 1f));
        } else {
            player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat(sppedlist.get(speedpostiion).getTitle().replace("x", "")), 1f));
        }
        if (sppedlist.get(speedpostiion).getTitle().equalsIgnoreCase("3x")) {
            Toast.makeText(this, " if internet speed is not proper then video may buffer", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void selectQualityPos(int position) {
        tempqulity = position;

        if (player != null && tempqulity != qulaity_pos) {
            playPosition = player.getCurrentPosition();
            player.removeListener(playerEventListener);
            if (player != null) {
                playerError = false;
                player.removeListener(playerEventListener);
                player.release();
                trackSelector = null;
                player = null;
            }
            retry.setVisibility(View.GONE);
            qulaity_pos = tempqulity;
            youtubeUri = Objects.requireNonNull(sparseAdaptiveVideoUrlList_new.get(qulaity_pos)).getUrl();
            Loadyoutubeurl(youtubeUri, false);

        }
        if (popupWindow_quality != null && popupWindow_quality.isShowing()) {
            new Handler().postDelayed(() -> popupWindow_quality.dismiss(), 250);
        }


    }


    private class ConnectionChangedListener
            implements ConnectionClassManager.ConnectionClassStateChangeListener {
        @Override
        public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
            mConnectionClass = bandwidthState;
            Log.d("prince", "" + mConnectionClass);
        }
    }
}
