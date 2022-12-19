package com.utkarshnew.android.Player;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaDrm;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.v4.media.session.MediaSessionCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.network.connectionclass.ConnectionQuality;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.KeysExpiredException;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
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
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pallycon.widevinelibrary.DetectedDeviceTimeModifiedException;
import com.pallycon.widevinelibrary.NetworkConnectedException;
import com.pallycon.widevinelibrary.PallyconDrmException;
import com.pallycon.widevinelibrary.PallyconEncrypterException;
import com.pallycon.widevinelibrary.PallyconEventListener;
import com.pallycon.widevinelibrary.PallyconServerResponseException;
import com.pallycon.widevinelibrary.PallyconWVMSDK;
import com.pallycon.widevinelibrary.PallyconWVMSDKFactory;
import com.pdfview.PDFView;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.courses.modal.OnlineUser;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.MediaFile;
import com.utkarshnew.android.Model.PlayerPojo.Addindex;
import com.utkarshnew.android.Model.PlayerPojo.Metadata;
import com.utkarshnew.android.Model.PlayerPojo.Pdf;
import com.utkarshnew.android.Model.PlayerPojo.Polldata;
import com.utkarshnew.android.Model.PlayerPojo.VideoTimeFramePojo;
import com.utkarshnew.android.Model.chatPojo;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.CookieData;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionDialog;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionHelper;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.table.YoutubePlayerTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.AmazonUpload.AmazonCallBack;
import com.utkarshnew.android.Utils.AmazonUpload.s3ImageUploading;
import com.utkarshnew.android.Utils.AppPermissionsRunTime;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.RealPathUtil;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class DrmVideoPlayerActivity extends AppCompatActivity implements AmazonCallBack, NetworkCall.MyNetworkCallBack {
    public int speedpostiion = 3;
    public int temppos = 3;

    UtkashRoom utkashRoom;
    String isclicked = "";
    private String deletedindex = "";
    String str_imgTypeClick = "";
    public ArrayList<AppPermissionsRunTime.MyPermissionConstants> myPermissionConstantsArrayList;
    public final int REQUEST_CODE_PERMISSION_MULTIPLE = 123;
    private s3ImageUploading s3IU;
    private static int newOrientation = 0;
    public boolean ischatload = false;
    private boolean inErrorState;
    private Object imaAdsLoader;
    private ViewGroup adOverlayViewGroup;
// com.google.android.exoplayer2.ext.ima.ImaAdsLoader

    private SimpleExoPlayerView simpleExoPlayerView;
    public SimpleExoPlayer player;
    ProgressBar progressBar;
    String audio_url = "";
    private DataSource.Factory mediaDataSourceFactory;
    private String userAgent;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }


    private DefaultTrackSelector.Parameters trackSelectorParameters;
    MediaSource mediaSource;
    ImageView exo_ffwd, exo_rew;

    private Uri loadedAdTagUri;
    private int resumeWindow;
    private long resumePosition;

    DefaultTrackSelector trackSelector;
    private boolean isShowingTrackSelectionDialog;
    private TrackSelectionHelper trackSelectionHelper;
    private ConnectionQuality mConnectionClass = ConnectionQuality.UNKNOWN;
    String url = "", cid = "", backcid = "", token = "", site_id = "", site_key = "", content_id = "";
    public static final String EXTENSION_EXTRA = "extension";
    public static final String AD_TAG_URI_EXTRA = "ad_tag_uri";
    private List<String> sparseKeyList, sparseAdaptiveResolutionList, sparseMuxedResolutionList;
    private HashMap<String, String> sparseAdaptiveVideoUrlList, sparseMuxedVideoUrlList;
    private List<String> sparseOPUSAudioUrl;
    private static long playPosition = 0;
    boolean is_bg = false;
    ImageView quality;
    public String islive;
    ChatAdapter chatAdapter;
    public String isaudio;
    private ImageView mFullScreenIcon;
    ChildEventListener getchatdata;
    private TextView tvGoLive;
    public RecyclerView recyclerChat;
    ImageView ivSend;
    LinearLayout linearLayout, llll, chatlayout;
    TextView addBookmark;
    ArrayList<chatPojo> arrChat = new ArrayList<>();
    ArrayList<chatPojo> pollarr = new ArrayList<>();
    ArrayList<chatPojo> lockarr = new ArrayList<>();
    ArrayList<Polldata> pollarraylist = new ArrayList<>();
    EditText etMessage;
    private ImageView chatAddButton;
    private TextView speedTV;
    String speedx = "";
    ImageView icon;
    private FrameLayout mFullScreenButton;
    private boolean mExoPlayerFullscreen = false;
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private Dialog mFullScreenDialog;
    View rootView;
    String thumbnailurl = "", video_id, video_name, islockedback;
    Bitmap image;
    public boolean isActivityLive = false;
    private boolean isintractavailable = false;
    TextView bookmarkBtn, indexBtn, chat_btn;
    private BookmarkAdapter bookmarkAdapter;
    private Adapter_recycleveiw_vedio adapter;
    private PollAdapter pollAdapter;
    private List<VideoTimeFramePojo> indexdata = new ArrayList<>();
    private List<VideoTimeFramePojo> bookmarkdata = new ArrayList<>();
    private List<Pdf> pdf = new ArrayList<>();
    // add bookmark index
    private DatabaseReference mFirebaseDatabaseReference1;
    private String time = "";
    private String info = "";
    ArrayList<String> keylistonetoone = new ArrayList<>();

    private String state = "";
    private TextView notes, poll;
    private NotesAdapter notesAdapter;
    NetworkCall networkCall;
    Data userdata;
    boolean is_background = false;
    ImageView audiocaetimage;
    String Chat_node = "", courseid = "", tileid = "", tiletype = "", videotype = "", device_id = "", device_name = "";
    TextView floatingText, video_name_text;
    private DatabaseReference mFirebaseDatabaseReferenceone2one;
    private DatabaseReference mFirebaseDatabaseReferenceone2oneget;
    private DatabaseReference mFirebaseDatabaseReferenceone2many;
    private DatabaseReference mFirebaseDatabaseReferencepolldata;
    DataSource.Factory dataSourceFactory;
    ValueEventListener valueEventListener;
    String link = "";
    String position = "";
    private CookieData cookieData;

    String parentid = "";
    public RelativeLayout pdf_view_layout;
    PDFView webView;
    ImageView cross, expand;
    String username = "";
    String onrequestmedtadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_liveawsactivity);


        utkashRoom = UtkashRoom.getAppDatabase(this);
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(Const.VIDEO_LINK);
            islive = getIntent().getExtras().getString(Const.VIDEO_LIVE);
            isaudio = getIntent().getExtras().getString("isaudio");
            video_id = getIntent().getStringExtra("video_id");
            video_name = getIntent().getStringExtra("video_name");
            Chat_node = getIntent().getStringExtra("Chat_node");
            courseid = getIntent().getStringExtra("courseid");
            tileid = getIntent().getStringExtra("tileid");
            tiletype = getIntent().getStringExtra("tiletype");
            videotype = getIntent().getStringExtra("video_type");
            thumbnailurl = getIntent().getStringExtra("thumbnail");
            islockedback = getIntent().getStringExtra("islocked");
            position = getIntent().getStringExtra("pos");
            parentid = getIntent().getStringExtra("parentid");

            onrequestmedtadata = getIntent().getStringExtra("json");
        }
        if (MakeMyExam.username == null || MakeMyExam.username.equalsIgnoreCase("")) {
            username = SharedPreference.getInstance().getLoggedInUser().getName();
            MakeMyExam.username = username;
        } else {
            username = MakeMyExam.username;

        }

        if (!(courseid == null && courseid.equalsIgnoreCase(""))) {
            if (courseid.contains("#")) {
                String arr[] = courseid.split("#");
                if (arr.length == 2) {
                    courseid = arr[1];

                } else {
                    courseid = arr[0];

                }
            }
        }

        if (MakeMyExam.userId.equalsIgnoreCase("") || MakeMyExam.userId.equalsIgnoreCase("0")) {
            MakeMyExam.userId = SharedPreference.getInstance().getLoggedInUser().getId();
            MakeMyExam.setUserId(SharedPreference.getInstance().getLoggedInUser().getId());
        }
        userdata = SharedPreference.getInstance().getLoggedInUser();
        if (userdata.getChat_block() == null) {
            userdata.setChat_block("0");
        }

        networkCall = new NetworkCall(this, this);


        if (isaudio.equalsIgnoreCase("1")) {
            Thread thread = new Thread(() -> {
                URL url = null;
                try {
                    url = new URL(thumbnailurl);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        if (mFirebaseDatabaseReferenceone2one != null) {
            mFirebaseDatabaseReferenceone2one.removeEventListener(valueEventListener);
            if (query != null) {
                query.removeEventListener(childEventListener);
            }
        }


        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view_new);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        quality = findViewById(R.id.quality);
        quality.setEnabled(false);
        rootView = findViewById(R.id.root_new);

        ivSend = findViewById(R.id.iv_send);
        llll = findViewById(R.id.llll);
        etMessage = findViewById(R.id.et_message);
        chatAddButton = findViewById(R.id.chatAddButton);
        recyclerChat = (RecyclerView) findViewById(R.id.recycler_view);
        bookmarkBtn = (TextView) findViewById(R.id.bookmark_btn);
        linearLayout = (LinearLayout) findViewById(R.id.bottomlayout);
        chatlayout = (LinearLayout) findViewById(R.id.linearLayout);
        addBookmark = findViewById(R.id.add_bookmark);
        indexBtn = findViewById(R.id.index_btn);
        chat_btn = findViewById(R.id.chat_btn);
        notes = findViewById(R.id.notes);
        poll = findViewById(R.id.poll);
        floatingText = (TextView) findViewById(R.id.floatingText_new);
        video_name_text = findViewById(R.id.video_name);
        pdf_view_layout = findViewById(R.id.pdf_view_layout);

        webView = findViewById(R.id.pdfViewPager);
        cross = findViewById(R.id.cross);
        expand = findViewById(R.id.expand);


        video_name_text.setText(video_name);

        floatingText.setText(userdata.getMobile());
        floatingText.measure(0, 0);
        floatingText.setVisibility(View.VISIBLE);

        if (savedInstanceState != null) {
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();

        hit_api_for_meta();





        try {
            JSONObject jsonObject = new JSONObject(onrequestmedtadata);
            if (jsonObject.has("data")) {
                if (isaudio != null && isaudio.equals("1")) {
                    if (jsonObject.getJSONObject("data").has("audio_url")) {
                        link = jsonObject.getJSONObject("data").getString("audio_url");

                    } else {
                        link = jsonObject.getJSONObject("data").getString("link");

                    }

                } else
                    link = jsonObject.getJSONObject("data").getString("link");

                JSONObject jsonObject1 = jsonObject.optJSONObject(Const.DATA);
                if (Objects.requireNonNull(jsonObject1).has("is_track_selector_auto")) {
                    MakeMyExam.is_track_selector_auto = jsonObject1.optString("is_track_selector_auto");
                }
                CookieData cookieDatas = new CookieData();

                token = jsonObject.optJSONObject(Const.DATA).optString("pallycon_token");
                site_id = jsonObject.optJSONObject(Const.DATA).optString("site_id");
                site_key = jsonObject.optJSONObject(Const.DATA).optString("site_key");
                content_id = jsonObject.optJSONObject(Const.DATA).optString("content_id");

                if (Objects.requireNonNull(jsonObject1).has("cookie") && jsonObject1.optJSONObject("cookie") != null && !jsonObject1.optJSONObject("cookie").equals("")) {
                    String cloudFrontKeyPairId = jsonObject1.optJSONObject("cookie").optString(Const.CloudFront_Key_Pair_Id);
                    String cloudFrontSignature = jsonObject1.optJSONObject("cookie").optString(Const.CloudFront_Signature);
                    String cloudFrontExpires = jsonObject1.optJSONObject("cookie").optString(Const.CloudFront_Expires);
                    cookieDatas.setCloudFrontKeyPairId(cloudFrontKeyPairId);
                    cookieDatas.setCloudFrontSignature(cloudFrontSignature);
                    cookieDatas.setCloudFrontExpires(cloudFrontExpires);

                } else {
                    cookieDatas.setCloudFrontKeyPairId("");
                    cookieDatas.setCloudFrontSignature("");
                    cookieDatas.setCloudFrontExpires("");
                }

                cookieData = cookieDatas;

                mediaDataSourceFactory = buildDataSourceFactory(false);

                if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
                    CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
                }
            } else {
                Toast.makeText(this, "url not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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


        poll.setOnClickListener(new OnSingleClickListener(() -> {
            pdf_view_layout.setVisibility(View.GONE);
            setPoll();
            return null;
        }));

        indexBtn.setOnClickListener(new OnSingleClickListener(() -> {
            pdf_view_layout.setVisibility(View.GONE);
            setIndex();
            return null;
        }));

        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf_view_layout.setVisibility(View.GONE);
                setchat();
            }
        });

        notes.setOnClickListener(new OnSingleClickListener(() -> {
            setNotes();
            return null;
        }));

        bookmarkBtn.setOnClickListener(new OnSingleClickListener(() -> {
            pdf_view_layout.setVisibility(View.GONE);
            setBookMark();
            return null;
        }));

        chatAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermission();
                //simpleExoPlayerView.Pause();

//                pausePlayer();
            }
        });


/*
        findViewById(R.id.quality).setOnClickListener(new OnSingleClickListener(() -> {

            try {
                if (Helper.isConnected(DrmVideoPlayerActivity.this)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(
                            trackSelector, dismissedDialog -> isShowingTrackSelectionDialog = false);
                    if (trackSelectionDialog != null) {
                        trackSelectionDialog.show(getSupportFragmentManager(), null);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }));
*/
    }

    private void updateButtonVisibility() {
        if (player != null && TrackSelectionDialog.willHaveContent(trackSelector)) {
            quality.setEnabled(player != null && TrackSelectionDialog.willHaveContent(trackSelector));
          /*  MediaSessionCompat mediaSessionCompat =new MediaSessionCompat(this,"exoplayer");
            MediaSessionConnector mediaSessionConnector =new MediaSessionConnector(mediaSessionCompat);
            mediaSessionConnector.setPlayer(player);*/
        }


    }


    private int savedOrientation;

    private boolean isPortrait(int orientation) {
        // Log.d("prince", "" + orientation);
        if (orientation < 85 || orientation > 100) {
            return true;
        }
        return false;
    }


    private String generateurl(String input, String url) {
        String exacturl = "";
        String spliturl[] = url.split("/");
        for (int i = 0; i < spliturl.length; i++) {
            if (i != (spliturl.length - 1)) {
                if (i == 0) {
                    exacturl = exacturl + spliturl[i];
                } else {
                    exacturl = exacturl + "/" + spliturl[i];
                }

            } else {
                exacturl = exacturl + "/" + input;
            }
        }
        return exacturl;
    }

    @SuppressLint("HardwareIds")
    private void hit_api_for_video_link(boolean is_background) {
        device_id = (Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
        device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
        networkCall.NetworkAPICall(API.get_video_link, "", false, false);


    }


    boolean bitrateapply = false;

    private PallyconEventListener pallyconEventListener = new PallyconEventListener() {
        @Override
        public void onDrmKeysLoaded(Map<String, String> licenseInfo) {
        }

        @Override
        public void onDrmSessionManagerError(Exception e) {
            if (e instanceof NetworkConnectedException) {

            } else if (e instanceof PallyconDrmException) {


            } else if (e instanceof PallyconEncrypterException) {

            } else if (e instanceof PallyconServerResponseException) {


            } else if (e instanceof KeysExpiredException) {


            } else if (e instanceof DetectedDeviceTimeModifiedException) {

            } else {

            }

        }

        @Override
        public void onDrmKeysRestored() {

        }

        @Override
        public void onDrmKeysRemoved() {

        }
    };


    private void initiallizeplayer(String url1) {
        try {
            if (islive.equalsIgnoreCase("0") && !bitrateapply) {
                if (utkashRoom.getyoutubedata().isUserExist(video_id, MakeMyExam.userId, isaudio)) {
                    playPosition = utkashRoom.getyoutubedata().getyoutubedata(MakeMyExam.userId, video_id, isaudio);
                } else {
                    playPosition = 0;
                }
            }

            bitrateapply = false;

            if (simpleExoPlayerView == null) {
                simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view_new);

            }
            if (isaudio.equalsIgnoreCase("1")) {
                simpleExoPlayerView.setControllerShowTimeoutMs(0);
                simpleExoPlayerView.setControllerHideOnTouch(false);
                audiocaetimage.setVisibility(View.VISIBLE);
                Helper.setThumbnailImage(this, thumbnailurl, this.getDrawable(R.mipmap.course_placeholder), audiocaetimage);
            } else {
                audiocaetimage.setVisibility(View.GONE);
            }


            if (islive.equalsIgnoreCase("0")) {

                try {
                    if (isaudio.equalsIgnoreCase("1")) {
                        try {
                            UserHistroyTable userHistroyTable = new UserHistroyTable();
                            userHistroyTable.setVideo_id(video_id);
                            userHistroyTable.setVideo_name(video_name);
                            userHistroyTable.setType("AWS Audio");
                            userHistroyTable.setTileid(tileid);
                            userHistroyTable.setYoutube_url(url + "ayush" + thumbnailurl);
                            userHistroyTable.setUser_id(MakeMyExam.userId);
                            userHistroyTable.setUrl(thumbnailurl);
                            if (parentid.equalsIgnoreCase("")) {
                                userHistroyTable.setCourse_id(courseid + "#" + courseid);
                            } else {
                                userHistroyTable.setCourse_id(parentid + "#" + courseid);
                            }

                            userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                            utkashRoom.getuserhistorydao().addUser(userHistroyTable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            UserHistroyTable userHistroyTable = new UserHistroyTable();
                            userHistroyTable.setVideo_id(video_id);
                            userHistroyTable.setVideo_name(video_name);
                            userHistroyTable.setTileid(tileid);
                            userHistroyTable.setType("AWS Video");
                            userHistroyTable.setYoutube_url(url + "ayush" + thumbnailurl);
                            userHistroyTable.setUser_id(MakeMyExam.userId);
                            if (parentid.equalsIgnoreCase("")) {
                                userHistroyTable.setCourse_id(courseid + "#");
                            } else {
                                userHistroyTable.setCourse_id(parentid + "#" + courseid);
                            }

                            userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                            utkashRoom.getuserhistorydao().addUser(userHistroyTable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Intent intent = getIntent();
            boolean needNewPlayer = player == null;
            if (needNewPlayer) {


                String FinalUrl = "";
                PallyconWVMSDK WVMAgent = null;
                Uri uri = null;
                try {
                    WVMAgent = PallyconWVMSDKFactory.getInstance(DrmVideoPlayerActivity.this);
                    WVMAgent.init(DrmVideoPlayerActivity.this, null, site_id, site_key);
                    WVMAgent.setPallyconEventListener(pallyconEventListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UUID drmSchemeUuid = UUID.fromString((C.WIDEVINE_UUID).toString());
                FinalUrl = link;
                uri = Uri.parse(FinalUrl);
                String drmLicenseUrl = API.API_REQUEST_VIDEO_LICENSE;

                String userId = "UT_" + userdata.getId();
                DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
                DefaultRenderersFactory renderersFactory = null;
                try {
                    drmSessionManager = Objects.requireNonNull(WVMAgent).createDrmSessionManagerByToken(drmSchemeUuid, drmLicenseUrl, uri, userId, "", token, false);
                    renderersFactory = new DefaultRenderersFactory(DrmVideoPlayerActivity.this, drmSessionManager);
                } catch (PallyconDrmException e) {
                    e.printStackTrace();
                }
                TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

                trackSelector.setParameters(trackSelector.buildUponParameters()
                        .setMaxVideoSize(256, 144)
                        .setForceHighestSupportedBitrate(true)
                );
                trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
                player = ExoPlayerFactory.newSimpleInstance(DrmVideoPlayerActivity.this, renderersFactory, trackSelector);
                player.addListener(playerEventListener);
                simpleExoPlayerView.setPlayer(player);
                mediaSource = buildMediaSource(uri, null);
                player.prepare(mediaSource);
                SurfaceView surfaceView = (SurfaceView) simpleExoPlayerView.getVideoSurfaceView();
                surfaceView.setSecure(true);
                player.setPlayWhenReady(true);
                // player.addAnalyticsListener(new EventLogger(trackSelector));


            }
            updateButtonVisibility();

            Uri[] uris;
            String[] extensions;
            uris = new Uri[]{Uri.parse(link.trim())};
            extensions = new String[]{intent.getStringExtra(EXTENSION_EXTRA)};
            if (Util.maybeRequestReadExternalStoragePermission(DrmVideoPlayerActivity.this, uris)) {
                return;
            }

            MediaSource[] mediaSources = new MediaSource[uris.length];

            for (int i = 0; i < uris.length; i++) {

                mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
            }

            mediaSource = mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);
            String adTagUriString = intent.getStringExtra(AD_TAG_URI_EXTRA);


            if (adTagUriString != null) {
                Uri adTagUri = Uri.parse(adTagUriString);
                if (!adTagUri.equals(loadedAdTagUri)) {
                    releaseAdsLoader();
                    loadedAdTagUri = adTagUri;
                }
                try {
                    mediaSource = createAdsMediaSource(mediaSource, Uri.parse(adTagUriString));
                } catch (Exception e) {
                    //showToast(R.string.ima_not_loaded);
                }
            } else {
                releaseAdsLoader();
            }
            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
                player.prepare(mediaSource, !haveResumePosition, true);
            } else {
                player.prepare(mediaSource, !haveResumePosition, false);
            }

          /*  AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.isWiredHeadsetOn()) {
                MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "exoplayer");
                MediaSessionConnector mediaSessionConnector = new MediaSessionConnector(mediaSessionCompat);
                mediaSessionConnector.setPlayer(player);
            } else {
                MediaSessionCompat mediaSession = new MediaSessionCompat(this, "Vion");
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
            }
*/

            if (playPosition != 0) {
                player.seekTo(playPosition);
            }

            inErrorState = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    private void updateResumePosition() {
        if (player != null) {
            resumeWindow = Objects.requireNonNull(player).getCurrentWindowIndex();
            resumePosition = Math.max(0, player.getContentPosition());
        }
    }

    @SuppressWarnings("unchecked")
    private MediaSource buildMediaSource(Uri uri, @androidx.annotation.Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        // android.util.Log.d("prince",""+type);

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

    private MediaSource createAdsMediaSource(MediaSource mediaSource, Uri adTagUri) throws Exception {
        // Load the extension source using reflection so the demo app doesn't have to depend on it.
        // The ads loader is reused for multiple playbacks, so that ad playback can resume.
        Class<?> loaderClass = Class.forName("com.google.android.exoplayer2.ext.ima.ImaAdsLoader");
        if (imaAdsLoader == null) {
            imaAdsLoader = loaderClass.getConstructor(Context.class, Uri.class)
                    .newInstance(this, adTagUri);
            adOverlayViewGroup = new FrameLayout(this);
            // The demo app has a non-null overlay frame layout.
            simpleExoPlayerView.getOverlayFrameLayout().addView(adOverlayViewGroup);
        }
        Class<?> sourceClass = Class.forName("com.google.android.exoplayer2.ext.ima.ImaAdsMediaSource");

        Constructor<?> constructor = sourceClass.getConstructor(MediaSource.class,
                DataSource.Factory.class, loaderClass, ViewGroup.class);

        return (MediaSource) constructor.newInstance(mediaSource, mediaDataSourceFactory, imaAdsLoader, adOverlayViewGroup);
    }


    private void releaseAdsLoader() {
        if (imaAdsLoader != null) {
            try {
                Class<?> loaderClass = Class.forName("com.google.android.exoplayer2.ext.ima.ImaAdsLoader");
                Method releaseMethod = loaderClass.getMethod("release");
                releaseMethod.invoke(imaAdsLoader);
            } catch (Exception e) {
                // Should never happen.
                throw new IllegalStateException(e);
            }
            imaAdsLoader = null;
            loadedAdTagUri = null;
            simpleExoPlayerView.getOverlayFrameLayout().removeAllViews();
        }
    }


/*
    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
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


    private void hit_api_for_meta() {
        networkCall.NetworkAPICall(API.get_meta, "", false, false);
    }


    int lastseleted = -1;


    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            myPermissionConstantsArrayList = new ArrayList<>();
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_READ_EXTERNAL_STORAGE);
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_WRITE_EXTERNAL_STORAGE);
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_CAMERA);
            if (AppPermissionsRunTime.checkPermission(DrmVideoPlayerActivity.this, myPermissionConstantsArrayList, REQUEST_CODE_PERMISSION_MULTIPLE)) {
                //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
                imgClick();
            }
        } else {
            // Pre-Marshmallow
            //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
            imgClick();
        }
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(TransferListener listener) {
        DefaultDataSourceFactory dataSourceFactory = null;
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");

        if (!TextUtils.isEmpty(cookieData.getCloudFrontSignature()) && !TextUtils.isEmpty(cookieData.getCloudFrontSignature()) && !TextUtils.isEmpty(cookieData.getCloudFrontKeyPairId())) {
            String cookieValue = "CloudFront-Policy=" + cookieData.getCloudFrontExpires() +
                    ";CloudFront-Signature=" + cookieData.getCloudFrontSignature() +
                    ";CloudFront-Key-Pair-Id=" + cookieData.getCloudFrontKeyPairId();
            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, listener);

            defaultHttpDataSourceFactory.setDefaultRequestProperty("Cookie", cookieValue);
            dataSourceFactory = new DefaultDataSourceFactory(this, listener, defaultHttpDataSourceFactory);
        } else {
            dataSourceFactory = new DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener));
        }            // Helper.show_Toast(this,"cookie set....");

        return dataSourceFactory;
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(TransferListener listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }


    private void setchat() {
        isclicked = "1";
        addBookmark.setVisibility(View.GONE);
        recyclerChat.setVisibility(View.VISIBLE);
        chatAdapter = new ChatAdapter(this, "", arrChat);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setAutoMeasureEnabled(false);
        llm.setStackFromEnd(true);
        recyclerChat.setLayoutManager(llm);
        recyclerChat.setAdapter(chatAdapter);
        disableAll();


        if (islocked.equalsIgnoreCase("1")) {
            linearLayout.setVisibility(View.VISIBLE);
        } else if (islocked.equalsIgnoreCase("0")) {
            if (islockedback.equalsIgnoreCase("1")) {
                linearLayout.setVisibility(View.GONE);
                chatlayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                chatlayout.setVisibility(View.VISIBLE);
            }
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        chatlayout.setVisibility(View.VISIBLE);
        chat_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        chat_btn.setTextColor(getResources().getColor(android.R.color.black));
    }


    private void setNotes() {
        isclicked = "4";
        linearLayout.setVisibility(View.GONE);
        addBookmark.setVisibility(View.GONE);
        recyclerChat.setVisibility(View.VISIBLE);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        disableAll();
        notes.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        notes.setTextColor(getResources().getColor(android.R.color.black));
        setNotesAdapter();
    }

    private void setPoll() {
        isclicked = "5";
        linearLayout.setVisibility(View.GONE);
        addBookmark.setVisibility(View.GONE);
        recyclerChat.setVisibility(View.VISIBLE);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        disableAll();
        poll.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        poll.setTextColor(getResources().getColor(android.R.color.black));
        poll.setText("Poll");
        setpollAdapter();
    }

    private void setpollAdapter() {
        pollAdapter = new PollAdapter(this, pollarraylist);
        recyclerChat.setAdapter(pollAdapter);
    }


    String checkstatus = "";
    String islocked = "0";

    private void fireBaseOperation() {
        mFirebaseDatabaseReferenceone2many = FirebaseDatabase.getInstance("https://utkarsh-window-app-default-rtdb.firebaseio.com/").getReference().child("chat_master/" + Chat_node + "/1TOM/");
        mFirebaseDatabaseReferenceone2one = FirebaseDatabase.getInstance("https://utkarsh-window-app-default-rtdb.firebaseio.com/").getReference().child("chat_master/" + Chat_node + "/1TO1/" + MakeMyExam.userId);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    checkstatus = "1";
                    getupdatedchatdata(MakeMyExam.getTime_server());
                } else {
                    try {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            chatPojo message = childSnapshot.getValue(chatPojo.class);
                            if (!message.getType().equalsIgnoreCase("poll") && !message.getType().equalsIgnoreCase("is_chat_locked")) {
                                arrChat.add(message);
                                pollarr.add(message);
                                keylistonetoone.add(childSnapshot.getKey());

                            } else {
                                if (message.getType().equalsIgnoreCase("is_chat_locked")) {
                                    lockarr.add(message);
                                }
                                pollarr.add(message);
                            }
                            chatAdapter.notifyDataSetChanged();
                        }
                        recyclerChat.smoothScrollToPosition(arrChat.size());
                        checkstatus = "1";
                        getupdatedchatdata((pollarr.get(pollarr.size() - 1).getDate()));
                        if (lockarr.size() > 0) {
                            if (userdata.getChat_block() != null && userdata.getChat_block().equalsIgnoreCase("1")) {
                                islocked = "2";
                                hidechat();
                            } else {
                                String message = lockarr.get(lockarr.size() - 1).getMessage();
                                if (message.equalsIgnoreCase("0")) {
                                    islocked = "1";
                                    showchat();
                                } else {
                                    islocked = "2";
                                    hidechat();
                                }
                            }

                        }
                    } catch (Exception e) {
                        Toast.makeText(DrmVideoPlayerActivity.this, "null pointer exception", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mFirebaseDatabaseReferenceone2one.addListenerForSingleValueEvent(valueEventListener);

        mFirebaseDatabaseReferenceone2one.limitToLast(500);

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMessage.getText().toString().trim().equals("")) {

                    if (!isintractavailable) {
                        checkintract();
                    }

                    chatPojo message = new chatPojo(MakeMyExam.userId, etMessage.getText().toString(), username, MakeMyExam.getTime_server(), "1", userdata.getProfilePicture(), Const.DEVICE_TYPE_ANDROID, "text", courseid);
                    mFirebaseDatabaseReferenceone2one.push().setValue(message);
                    etMessage.setText("");
                    Helper.hideKeyboard(DrmVideoPlayerActivity.this);
                } else {
                    Helper.showSnackBar(ivSend, "Please enter your query first.");
                }
            }
        });

        arrChat.clear();
        chatAdapter = new ChatAdapter(this, "", arrChat);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setAutoMeasureEnabled(false);
        recyclerChat.setLayoutManager(llm);
        recyclerChat.setAdapter(chatAdapter);
    }

    DatabaseReference rootRef;
    ChildEventListener childEventListener;
    Query query;

    public void getupdatedchatdata(long actualdate) {
        rootRef = FirebaseDatabase.getInstance("https://utkarsh-window-app-default-rtdb.firebaseio.com/").getReference().child("chat_master/" + Chat_node + "/1TO1/" + MakeMyExam.userId);
        ;
        query = rootRef.orderByChild("date").startAt(actualdate);
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!checkstatus.equalsIgnoreCase("1")) {
                    if (dataSnapshot == null) {

                    } else {
                        try {
                            chatPojo message = dataSnapshot.getValue(chatPojo.class);
                            if (message.getType().equalsIgnoreCase("poll")) {
                                if (message.getIs_active().equalsIgnoreCase("2")) {
                                    for (int polldata = 0; polldata < pollarraylist.size(); polldata++) {
                                        if (pollarraylist.get(polldata).getRendomkey().equalsIgnoreCase(message.getFirebase_id())) {
                                            pollarraylist.get(polldata).setStatus("2");
                                            pollAdapter.notifyDataSetChanged();
                                            poll.setText("Poll");
                                        }
                                    }
                                } else {
                                    getpolldatawithid(message.getFirebase_id());
                                }
                            } else {
                                if (!message.getType().equalsIgnoreCase("poll") && !message.getType().equalsIgnoreCase("is_chat_locked")) {
                                    arrChat.add(message);
                                    keylistonetoone.add(dataSnapshot.getKey());
                                    if (message.getPlatform().equalsIgnoreCase("1")) {
                                        message.setFirebase_id(dataSnapshot.getKey());
                                        mFirebaseDatabaseReferenceone2many.push().setValue(message);
                                    }
                                } else {
                                    if (userdata.getChat_block().equalsIgnoreCase("1")) {
                                        islocked = "2";
                                        hidechat();
                                    } else {
                                        if (message.getMessage().equalsIgnoreCase("0")) {
                                            islocked = "1";
                                            showchat();
                                            lockarr.add(message);

                                        } else {
                                            islocked = "2";
                                            hidechat();
                                            lockarr.add(message);

                                        }
                                    }

                                }
                            }
                            chatAdapter.notifyDataSetChanged();
                            recyclerChat.smoothScrollToPosition(arrChat.size());
                        } catch (Exception e) {
                            Toast.makeText(DrmVideoPlayerActivity.this, "null pointer exception", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    chatAdapter.notifyDataSetChanged();
                    recyclerChat.smoothScrollToPosition(arrChat.size());

                    try {
                        chatPojo message = dataSnapshot.getValue(chatPojo.class);
                        if (message.getType().equalsIgnoreCase("poll")) {
                            if (pollarraylist.size() == 0) {
                                getpolldatawithid(message.getFirebase_id());
                            }
                        } else if (message.getType().equalsIgnoreCase("is_chat_locked")) {
                            if (userdata.getChat_block().equalsIgnoreCase("1")) {
                                islocked = "2";
                                hidechat();
                            } else {
                                if (lockarr.size() == 0) {
                                    if (message.getMessage().equalsIgnoreCase("0")) {
                                        islocked = "1";
                                        showchat();

                                    } else {
                                        islocked = "2";
                                        hidechat();

                                    }
                                }
                            }

                        } else {
                            if (arrChat.size() == 0) {
                                arrChat.add(message);
                                keylistonetoone.add(dataSnapshot.getKey());
                                if (message.getPlatform().equalsIgnoreCase("1")) {
                                    message.setFirebase_id(dataSnapshot.getKey());
                                    mFirebaseDatabaseReferenceone2many.push().setValue(message);
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    checkstatus = "0";
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (keylistonetoone.size() == arrChat.size())
                    for (int k = 0; k < keylistonetoone.size(); k++) {
                        if (keylistonetoone.get(k).equalsIgnoreCase(dataSnapshot.getKey())) {
                            keylistonetoone.remove(k);
                            arrChat.remove(k);
                            chatAdapter.notifyItemRemoved(k);
                            chatAdapter.notifyItemRangeChanged(k, arrChat.size());
                            break;
                        }
                    }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addChildEventListener(childEventListener);
    }

    private void checkintract() {
        if (mFirebaseDatabaseReference1 != null) {
            try {
                mFirebaseDatabaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            try {
                                OnlineUser onlineUser = dataSnapshot.getValue(OnlineUser.class);
                                isintractavailable = true;
                                if (Objects.requireNonNull(onlineUser).getInteract() == null) {
                                    mFirebaseDatabaseReference1.child("interact").setValue("1");
                                } else if (onlineUser.getInteract().equalsIgnoreCase("0")) {
                                    mFirebaseDatabaseReference1.child("interact").setValue("1");

                                }
                            } catch (Exception e) {

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                OnlineUser onlineUser=new OnlineUser(username,userdata.getProfilePicture(),Const.DEVICE_TYPE_ANDROID,"true",MakeMyExam.userId,userdata.getMobile());
//                mFirebaseDatabaseReference1.child(MakeMyExam.userId).setValue(onlineUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getpolldatawithid(String randomid) {
        mFirebaseDatabaseReferencepolldata = null;
        mFirebaseDatabaseReferencepolldata = FirebaseDatabase.getInstance("https://utkarsh-window-app-default-rtdb.firebaseio.com/").getReference().child("chat_master/" + Chat_node + "/" + "Poll/" + randomid);
        mFirebaseDatabaseReferencepolldata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {

                } else {


                    Polldata polldata = new Gson().fromJson(new Gson().toJson(dataSnapshot.getValue()), Polldata.class);
                    boolean insert = true;
                    for (int polldatas = 0; polldatas < pollarraylist.size(); polldatas++) {
                        if (pollarraylist.get(polldatas).getRendomkey().equalsIgnoreCase(randomid)) {
                            pollarraylist.get(polldatas).setStatus("1");
                            pollAdapter.notifyDataSetChanged();
                            poll.setText("New Poll");
                            insert = false;
                            break;
                        } else {
                            insert = true;
                        }
                    }
                    if (insert) {
                        try {
                            if (randomid != null) {
                                polldata.setRendomkey(randomid);
                                polldata.setStatus("1");
                                Snackbar.make(rootView.getRootView(), "New Poll Added", Snackbar.LENGTH_SHORT).show();
                                poll.setText("New Poll");
                                Objects.requireNonNull(polldata).setMyAnswer("0");
                                ArrayList<Polldata> pollarraylist1 = new ArrayList<>();
                                pollarraylist1.add(0, polldata);
                                pollarraylist1.addAll(pollarraylist);
                                pollarraylist.clear();
                                pollarraylist.addAll(pollarraylist1);
                                pollAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showDialog(View view) {
        if (player != null) {
            pausePlayer();
            isActivityLive = false;
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
                        Helper.hideKeyboard(DrmVideoPlayerActivity.this);
                        BookMarkApi("", time, info, "1");
                    } else {
                        Toast.makeText(DrmVideoPlayerActivity.this, "Add Title", Toast.LENGTH_SHORT).show();
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {
            if (newConfig.orientation == 1) {

                mExoPlayerFullscreen = false;
                mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreen));


                video_name_text.setVisibility(View.VISIBLE);
                if (isclicked.equalsIgnoreCase("1")) {
                    if (lockarr.size() > 0) {
                        if (islocked.equalsIgnoreCase("1")) {
                            linearLayout.setVisibility(View.VISIBLE);
                            chatlayout.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout.setVisibility(View.GONE);
                            chatlayout.setVisibility(View.GONE);
                        }
                    } else {
                        if (islockedback.equalsIgnoreCase("1")) {
                            linearLayout.setVisibility(View.GONE);
                            chatlayout.setVisibility(View.GONE);
                        } else {
                            linearLayout.setVisibility(View.VISIBLE);
                            chatlayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
                if (isclicked.equalsIgnoreCase("2")) {
                    linearLayout.setVisibility(View.VISIBLE);
                    addBookmark.setVisibility(View.VISIBLE);
                }

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                final float scale = getResources().getDisplayMetrics().density;
                int pixels = (int) (220 * scale + 0.5f);
                boolean xlarge = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
                boolean large = ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
                if (large) {
                    pixels = (int) (350 * scale + 0.5f);
                } else if (xlarge) {
                    pixels = (int) (450 * scale + 0.5f);
                } else {
                    pixels = (int) (230 * scale + 0.5f);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
                rootView.setLayoutParams(layoutParams);
            } else {
                mExoPlayerFullscreen = true;
                mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreenexit));

                video_name_text.setVisibility(View.GONE);
                chatlayout.setVisibility(View.GONE);
                addBookmark.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                rootView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }


    private void setUserOnline() {
        mFirebaseDatabaseReference1 = null;
        if (mFirebaseDatabaseReference1 == null) {
            try {
                mFirebaseDatabaseReference1 = FirebaseDatabase.getInstance("https://utkarsh-window-app-default-rtdb.firebaseio.com/").getReference().child("chat_master/").child(Chat_node).child("User").child(MakeMyExam.userId);

                mFirebaseDatabaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            if (MakeMyExam.getTime_server() != 0) {
                                OnlineUser onlineUser = new OnlineUser(username, SharedPreference.getInstance().getLoggedInUser().getProfilePicture(), Const.DEVICE_TYPE_ANDROID, "true", MakeMyExam.userId, SharedPreference.getInstance().getLoggedInUser().getMobile(), "0", MakeMyExam.getTime_server(), userdata.getChat_block());
                                mFirebaseDatabaseReference1.setValue(onlineUser);
                            }
                        } else {
                            String json = new Gson().toJson(dataSnapshot.getValue());
                            OnlineUser example = new Gson().fromJson(json, OnlineUser.class);
                            if (example.getId() != null && example.getId().equals(MakeMyExam.userId)) {
                                mFirebaseDatabaseReference1.child("online").setValue("true");
                            } else {
                                if (MakeMyExam.getTime_server() != 0) {
                                    OnlineUser onlineUser = new OnlineUser(username, SharedPreference.getInstance().getLoggedInUser().getProfilePicture(), Const.DEVICE_TYPE_ANDROID, "true", MakeMyExam.userId, SharedPreference.getInstance().getLoggedInUser().getMobile(), "0", MakeMyExam.getTime_server(), userdata.getChat_block());
                                    mFirebaseDatabaseReference1.setValue(onlineUser);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setUserOffline() {
        if (mFirebaseDatabaseReference1 != null && ServerValue.TIMESTAMP != null) {
            try {
                mFirebaseDatabaseReference1.child("online").setValue(ServerValue.TIMESTAMP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onS3UploadData(ArrayList<MediaFile> images) {
        if (images != null && !images.isEmpty()) {
            String filetype = "";
            if (images.get(0).getFile().contains(".pdf")) {
                filetype = Const.PDF;
            } else {
                filetype = "image";
            }
            try {
                if (!images.get(0).getFile().trim().equals("")) {
                    if (!isintractavailable) {
                        checkintract();
                    }
                    chatPojo message = new chatPojo(MakeMyExam.userId, images.get(0).getFile(), username, MakeMyExam.getTime_server(), "1", userdata.getProfilePicture(), Const.DEVICE_TYPE_ANDROID, filetype, courseid);
                    mFirebaseDatabaseReferenceone2one.push().setValue(message);
                    mFirebaseDatabaseReferenceone2many.push().setValue(message);
                    Helper.hideKeyboard(DrmVideoPlayerActivity.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

            case API.get_video_link:
                EncryptionData videoencryptionData = new EncryptionData();
                videoencryptionData.setName(video_id + "_" + "0" + "_" + "0");
                videoencryptionData.setCourse_id(courseid);
                videoencryptionData.setTile_id(tileid);
                videoencryptionData.setType(tiletype);
                videoencryptionData.setDevice_id(device_id);
                videoencryptionData.setDevice_name(device_name);
                String metavideoencryptionDatadoseStr = new Gson().toJson(videoencryptionData);
                String videodatadoseStrScr = AES.encrypt(metavideoencryptionDatadoseStr);
                return service.getVideoLink(videodatadoseStrScr);


            case API.delete_video_index:
                EncryptionData deleteencryptionData = new EncryptionData();
                deleteencryptionData.setIndex_id(deletedindex);
                String deleteencryptionDatadoseStr = new Gson().toJson(deleteencryptionData);
                String metadeletedatadoseStrScr = AES.encrypt(deleteencryptionDatadoseStr);
                return service.deletevideoindex(metadeletedatadoseStrScr);


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
        switch (apitype) {
            case API.get_meta:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        MakeMyExam.setTime_server((Long.parseLong(jsonstring.optString("time"))) * 1000);
                        Metadata metadata = new Gson().fromJson(jsonstring.toString(), Metadata.class);
                        indexdata.addAll(metadata.getData().getIndex());
                        bookmarkdata.addAll(metadata.getData().getBookmark());
                        pdf.addAll(metadata.getData().getPdf());
                        pollarraylist.addAll(metadata.getData().getPoll());
                        Collections.reverse(pollarraylist);
                        int ispdf = 0, isindex = 0, poll = 0, islivechat = 0;
                        ;
                        if (islive.equalsIgnoreCase("5") && isaudio.equalsIgnoreCase("0")) {
                            setpollAdapter();
                            fireBaseOperation();
                            setUserOnline();
                            if (pdf.size() == 0) {
                                ispdf = 0;
                            } else {
                                ispdf = 1;
                            }
//                            if(pollarraylist.size()==0)
//                            {
//                                poll=0;
//                            }
//                            else
//                            {
//                                poll=1;
//                            }

                            setchat();
                            isvisiblelayouts(1, 0, 0, 1, ispdf);
                        }
                        if (islive.equalsIgnoreCase("0") && isaudio.equalsIgnoreCase("0")) {
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
                        }
                        if (isaudio.equalsIgnoreCase("1")) {
                            linearLayout.setVisibility(View.GONE);
                            isvisiblelayouts(0, 0, 0, 0, 0);
                        }
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

                        JSONObject jsonObject = new JSONObject(jsonstring.toString());
                        if (jsonObject.has("data")) {
                            if (isaudio != null && isaudio.equals("1")) {
                                if (jsonObject.getJSONObject("data").has("audio_url"))
                                    link = jsonObject.getJSONObject("data").getString("audio_url");
                            } else link = jsonObject.getJSONObject("data").getString("link");

                            JSONObject jsonObject1 = jsonObject.optJSONObject(Const.DATA);
                            if (Objects.requireNonNull(jsonObject1).has("is_track_selector_auto")) {
                                MakeMyExam.is_track_selector_auto = jsonObject1.optString("is_track_selector_auto");
                            }
                            CookieData cookieDatas = new CookieData();

                            token = jsonObject.optJSONObject(Const.DATA).optString("pallycon_token");
                            site_id = jsonObject.optJSONObject(Const.DATA).optString("site_id");
                            site_key = jsonObject.optJSONObject(Const.DATA).optString("site_key");
                            content_id = jsonObject.optJSONObject(Const.DATA).optString("content_id");


                            if (Objects.requireNonNull(jsonObject1).has("cookie") && jsonObject1.optJSONObject("cookie") != null && !jsonObject1.optJSONObject("cookie").equals("")) {
                                String cloudFrontKeyPairId = jsonObject1.optJSONObject("cookie").optString(Const.CloudFront_Key_Pair_Id);
                                String cloudFrontSignature = jsonObject1.optJSONObject("cookie").optString(Const.CloudFront_Signature);
                                String cloudFrontExpires = jsonObject1.optJSONObject("cookie").optString(Const.CloudFront_Expires);
                                cookieDatas.setCloudFrontKeyPairId(cloudFrontKeyPairId);
                                cookieDatas.setCloudFrontSignature(cloudFrontSignature);
                                cookieDatas.setCloudFrontExpires(cloudFrontExpires);

                            } else {
                                cookieDatas.setCloudFrontKeyPairId("");
                                cookieDatas.setCloudFrontSignature("");
                                cookieDatas.setCloudFrontExpires("");
                            }

                            cookieData = cookieDatas;

                            if (is_background) {
                                background_play();
                            } else {
                                mediaDataSourceFactory = buildDataSourceFactory(false);

                                if (videotype.equalsIgnoreCase("0")) {
                                    initiallizeplayer(link);
                                } else if (videotype.equalsIgnoreCase("5")) {
                                    initiallizeplayer(link);
                                }
                                if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
                                    CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
                                }
                            }


                        } else {
                            Toast.makeText(this, "url not found", Toast.LENGTH_SHORT).show();
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


    private void setIndex() {
        isclicked = "3";
        addBookmark.setVisibility(View.GONE);
        recyclerChat.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        disableAll();
        indexBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        indexBtn.setTextColor(getResources().getColor(android.R.color.black));
        setAdapter();
    }

    public void setVideoTimeMS(int timeMS) {
        if (player != null) {
            player.seekTo(timeMS);
        } else {
            Toast.makeText(this, "please wait player is not ready", Toast.LENGTH_SHORT).show();
        }

    }

    void disableAll() {
        bookmarkBtn.setBackgroundColor(getResources().getColor(R.color.off_white));
        indexBtn.setBackgroundColor(getResources().getColor(R.color.off_white));
        chat_btn.setBackgroundColor(getResources().getColor(R.color.off_white));
        poll.setBackgroundColor(getResources().getColor(R.color.off_white));
        notes.setBackgroundColor(getResources().getColor(R.color.off_white));
//        vodchatbtn.setBackgroundColor(getResources().getColor(R.color.off_white));
    }


    private void setAdapter() {
        adapter = new Adapter_recycleveiw_vedio(this, indexdata, "live");
        recyclerChat.setAdapter(adapter);
    }

    private void setbookmarkadapter() {
        bookmarkAdapter = new BookmarkAdapter(this, bookmarkdata, "Liveaws");
        recyclerChat.setAdapter(bookmarkAdapter);
    }

    private void setBookMark() {
        isclicked = "2";
        recyclerChat.setVisibility(View.VISIBLE);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        linearLayout.setVisibility(View.VISIBLE);
        disableAll();
        addBookmark.setVisibility(View.VISIBLE);
        chatlayout.setVisibility(View.GONE);
        bookmarkBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.clickcurveback));
        bookmarkBtn.setTextColor(getResources().getColor(android.R.color.black));
        setbookmarkadapter();
    }

    private void setNotesAdapter() {
        notesAdapter = new NotesAdapter(this, pdf, "");
        recyclerChat.setAdapter(notesAdapter);
//        recyclerChat.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    public void isvisiblelayouts(int ispoll, int isindex, int isbookmark, int islivechat, int ispdf) {

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

    PowerMenu powerMenu;

    private void initFullscreenButton() {
        TextView exo_duration = null, exo_position = null;
        DefaultTimeBar defaultTimeBar = null;

        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        trackSelector.setParameters(trackSelectorParameters);

        speedTV = controlView.findViewById(R.id.speedTV);
        tvGoLive = controlView.findViewById(R.id.tv_go_live);
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
        audiocaetimage = controlView.findViewById(R.id.audiocaetimage);
        defaultTimeBar = controlView.findViewById(R.id.exo_progress);
        exo_position = controlView.findViewById(R.id.exo_position);
        exo_duration = controlView.findViewById(R.id.exo_duration);
        findViewById(R.id.quality).setVisibility(View.GONE);
        speedTV.setVisibility(View.GONE);
        icon = controlView.findViewById(R.id.icon);
        icon.setVisibility(View.VISIBLE);

        if (isaudio != null && isaudio.equalsIgnoreCase("1")) {
            if (islive.equalsIgnoreCase("5")) {
                icon.setVisibility(View.GONE);
            }
            quality.setVisibility(View.GONE);
        }
        if (islive.equalsIgnoreCase("5")) {
            // speedTV.setVisibility(View.GONE);
            tvGoLive.setVisibility(View.VISIBLE);
            defaultTimeBar.setVisibility(View.INVISIBLE);
            exo_position.setVisibility(View.INVISIBLE);
            exo_duration.setVisibility(View.INVISIBLE);
            exo_rew.setVisibility(View.INVISIBLE);
            exo_ffwd.setVisibility(View.INVISIBLE);
        } else {
            //  speedTV.setVisibility(View.VISIBLE);
            tvGoLive.setVisibility(View.GONE);
        }

        if (speedTV != null) {
            speedTV.setText("Normal");
            speedTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSpeedOptions();
                }
            });
        }

        if (tvGoLive != null) {
            tvGoLive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goLive();
                }
            });
        }

     /*   if (!TextUtils.isEmpty(speedx)) {
            player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(speedx.replace("x", "")), 1f));
        }*/


        icon.setOnClickListener(new OnSingleClickListener(() -> {
            if (trackSelector != null && trackSelector.getCurrentMappedTrackInfo() != null) {
                if (Helper.isConnected(DrmVideoPlayerActivity.this)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(
                            trackSelector, dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), null);
                }
            } else {
                Toast.makeText(DrmVideoPlayerActivity.this, "Player not ready, try again", Toast.LENGTH_SHORT).show();
            }
            return null;
        }));


        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

    private final OnMenuItemClickListener<PowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            if (item.getTitle().equals("Quality")) {
                try {
                    if (Helper.isConnected(DrmVideoPlayerActivity.this)) {
                        isShowingTrackSelectionDialog = true;
                        TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(
                                trackSelector, dismissedDialog -> isShowingTrackSelectionDialog = false);
                        if (trackSelectionDialog != null) {
                            trackSelectionDialog.show(getSupportFragmentManager(), null);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showSpeedOptions();
            }
            // item.setIsSelected(true);
            powerMenu.dismiss();
        }
    };

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
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    String title = item.getTitle().toString();
                    if (player != null) {
                        //  speedTV.setText(title);
                        speedx = title;
                        if (title.equalsIgnoreCase("Normal")) {
                            player.setPlaybackParameters(new PlaybackParameters(Float.valueOf("1"), 1f));
                        } else {
                            player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(title.replace("x", "")), 1f));
                        }
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }

    private void goLive() {
        if (player != null) {
            player.seekTo(player.getDuration());
        }
    }


    private void openFullscreenDialog() {
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreenexit));
        mExoPlayerFullscreen = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

    private void closeFullscreenDialog() {
        mExoPlayerFullscreen = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreen));
    }


    public void setpollcount(final String pollkey, String answer) {
        mFirebaseDatabaseReferencepolldata = null;
        mFirebaseDatabaseReferencepolldata = FirebaseDatabase.getInstance("https://utkarsh-window-app-default-rtdb.firebaseio.com/").getReference().child("chat_master/" + Chat_node + "/" + "Poll/" + pollkey);

        mFirebaseDatabaseReferencepolldata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    long count = 0;
                    if (dataSnapshot.getValue() == null) {

                    } else {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String clubkey = childSnapshot.getKey();
                            count = 0;
                            if (answer.equalsIgnoreCase("1")) {
                                if (clubkey.equalsIgnoreCase("attempt_1")) {
                                    count = (long) childSnapshot.getValue();
                                    setcountincrement(count + 1, "attempt_1");
                                }
                            } else if (answer.equalsIgnoreCase("2")) {
                                if (clubkey.equalsIgnoreCase("attempt_2")) {
                                    count = (long) childSnapshot.getValue();
                                    setcountincrement(count + 1, "attempt_2");
                                }
                            } else if (answer.equalsIgnoreCase("3")) {
                                if (clubkey.equalsIgnoreCase("attempt_3")) {
                                    count = (long) childSnapshot.getValue();
                                    setcountincrement(count + 1, "attempt_3");
                                }
                            } else if (answer.equalsIgnoreCase("4")) {
                                if (clubkey.equalsIgnoreCase("attempt_4")) {
                                    count = (long) childSnapshot.getValue();
                                    setcountincrement(count + 1, "attempt_4");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setcountincrement(final long count, final String child) {
        try {
            if (mFirebaseDatabaseReferencepolldata != null) {
                mFirebaseDatabaseReferencepolldata.child(child).setValue(count);
            } else {
                Toast.makeText(this, "key is empty", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String getdate(String timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(timestamp) * 1000);
        return sdf.format(currenTimeZone);
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


    private void imgClick() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DrmVideoPlayerActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image.jpg");
                    Uri photoURI;
                    if (SDK_INT >= 24) {
                        photoURI = FileProvider.getUriForFile(DrmVideoPlayerActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider", f);
                    } else {
                        photoURI = Uri.fromFile(f);
                    }
                    str_imgTypeClick = "PhotoCameraRequest";
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    //   startActivityForResult(intent, 10000);
                } catch (Exception e) {
                    //DebugLogger.Write("Error in Main Activity Profile Take Photo Button " +e);
                }
            } else if (options[item].equals("Choose from Gallery")) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "temp_gallery.jpg");
                Uri photoURI;
                if (SDK_INT >= 24) {
                    photoURI = FileProvider.getUriForFile(DrmVideoPlayerActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider", f);
                } else {
                    photoURI = Uri.fromFile(f);
                }
                str_imgTypeClick = "PhotoGalleryRequest";
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //     startActivityForResult(intent, 20000);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {

            try {
                File f = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp_image.jpg")) {
                        f = temp;
                        break;
                    }
                }

                Uri photoURI;
                if (SDK_INT >= 24) {
                    photoURI = FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID + ".provider", f);
                } else {
                    photoURI = Uri.fromFile(f);
                }

                CropImage.activity(photoURI)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(DrmVideoPlayerActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 20000 && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();

                CropImage.activity(selectedImage)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(DrmVideoPlayerActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String image_path = "";
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (str_imgTypeClick.equalsIgnoreCase("PhotoCameraRequest")) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            + "/Utkarsh/Profile/";
                    File newdir = new File(path);
                    newdir.mkdirs();
                    OutputStream outFile;
                    image_path = MakeMyExam.userId + "_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                    File file = new File(path + File.separator + image_path);
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outFile);
                    outFile.flush();
                    outFile.close();
                    s3IU = new s3ImageUploading(Chat_node, Const.AMAZON_S3_BUCKET_NAME_CHAT + Chat_node + "/" + MakeMyExam.userId, DrmVideoPlayerActivity.this, DrmVideoPlayerActivity.this, null);
                    ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setFile_type(Const.IMAGE);
                    mediaFile.setImage(bitmap);
                    mediaFileArrayList.add(mediaFile);
                    s3IU.execute(mediaFileArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (str_imgTypeClick.equalsIgnoreCase("PhotoGalleryRequest")) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            + "/utkarsh/ProfileImage/";
                    File newdir = new File(path);
                    newdir.mkdirs();
                    OutputStream outFile = null;
                    image_path = MakeMyExam.userId + "_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                    File file = new File(path + File.separator + image_path);
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outFile);
                    outFile.flush();
                    outFile.close();

                    s3IU = new s3ImageUploading(Chat_node, Const.AMAZON_S3_BUCKET_NAME_CHAT + Chat_node + "/" + MakeMyExam.userId, DrmVideoPlayerActivity.this, DrmVideoPlayerActivity.this, null);
                    ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setFile_type(Const.IMAGE);
                    mediaFile.setImage(bitmap);
                    mediaFileArrayList.add(mediaFile);
                    s3IU.execute(mediaFileArrayList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            if (SDK_INT >= 30) {
                setupDoc(copyFileToInternalStorage(data.getData(), "utkarsh_pdf"));
            } else {
                setupDoc(RealPathUtil.getPath(DrmVideoPlayerActivity.this, data.getData()));
            }
        }
    }

    private String copyFileToInternalStorage(Uri uri, String newDirName) {
        Uri returnUri = uri;

        Cursor returnCursor = getContentResolver().query(returnUri, new String[]{
                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
        }, null, null, null);


        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if (!newDirName.equals("")) {
            File dir = new File(getFilesDir() + "/" + newDirName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            output = new File(getFilesDir() + "/" + newDirName + "/" + name);
        } else {
            output = new File(getFilesDir() + "/" + name);
        }
        try {
            if (!output.exists()) {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                FileOutputStream outputStream = new FileOutputStream(output);
                int read = 0;
                int bufferSize = 1024;
                final byte[] buffers = new byte[bufferSize];
                while ((read = inputStream.read(buffers)) != -1) {
                    outputStream.write(buffers, 0, read);
                }
                inputStream.close();
                outputStream.close();
            }
        } catch (Exception e) {

        }

        return output.getPath();
    }

    public void setupDoc(String selectedURI) {
        MediaFile mediaFile = new MediaFile();
        ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();
        String docpath = selectedURI;

        if (!TextUtils.isEmpty(docpath) && !(docpath.contains(getString(R.string.pdf_extension)) || docpath.contains(getString(R.string.doc_extension)) || docpath.contains(getString(R.string.xls_extension))))
            Toast.makeText(this, R.string.file_format_error, Toast.LENGTH_SHORT).show();
        else {
            if (docpath.contains(getString(R.string.pdf_extension))) {
                mediaFile.setImage(BitmapFactory.decodeResource(this.getResources(), R.mipmap.pdf));
                mediaFile.setFile_type(Const.PDF);
            }
            s3IU = new s3ImageUploading(Chat_node, Const.AMAZON_S3_BUCKET_NAME_CHAT, DrmVideoPlayerActivity.this, DrmVideoPlayerActivity.this, null);
            String arr[] = docpath.split("/");
            mediaFile.setFile_name(arr[arr.length - 1]);
            mediaFile.setFile(docpath);
            mediaFile.setFile_type(Const.PDF);
            mediaFileArrayList.add(mediaFile);
            s3IU.execute(mediaFileArrayList);
        }
    }

    private Player.EventListener playerEventListener = new Player.DefaultEventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            //TODO: Please refer to the ExoPlayer guide.
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {


        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            //TODO: Please refer to the ExoPlayer guide.
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            //TODO: Please refer to the ExoPlayer guide.

            if (playbackState == Player.STATE_READY) {
                progressBar.setVisibility(View.GONE);
            } else if (playbackState == Player.STATE_IDLE) {
            } else if (playbackState == Player.STATE_BUFFERING) {
            }
            updateButtonVisibility();

        }


        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
            if (isBehindLiveWindow(e)) {
                clearResumePosition();
                initiallizeplayer("");
            } else {
                String errorString = getString(R.string.error_generic);
                if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                    Exception cause1 = e.getRendererException();
                    if (cause1 instanceof DrmSession.DrmSessionException) {
                        Throwable drmError = cause1.getCause();

                        if (drmError instanceof PallyconServerResponseException) {
                            errorString = drmError + " " + ((PallyconServerResponseException) drmError).getErrorCode();
                        } else if (drmError instanceof MediaDrm.MediaDrmStateException) {
                            if (drmError.getMessage().equalsIgnoreCase("Failed to handle key response: General DRM error")) {
                               /* if (player != null)
                                    playPosition = player.getCurrentPosition();
                                player.removeListener(playerEventListener);
                                player.release();
                                player = null;
                                is_background =false;
                                hit_api_for_video_link(false);*/

                                errorString = drmError + " " + drmError.getMessage();

                            } else {
                                errorString = drmError + " " + ((MediaDrm.MediaDrmStateException) drmError).getMessage();

                            }
                        }
                    } else {
                        Exception cause = e.getRendererException();

                        if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                            // Special case for decoder initialization failures.
                            MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                                    (MediaCodecRenderer.DecoderInitializationException) cause;

                        }

                    }
                } else if (e.type == ExoPlaybackException.TYPE_SOURCE) {
                    Exception cause = e.getSourceException();

                    if (cause instanceof DrmSession.DrmSessionException) {
                        Throwable drmError = cause.getCause();

                        if (drmError instanceof PallyconServerResponseException) {
                            if (((PallyconServerResponseException) drmError).getErrorCode() == 7011) {
                                if (player != null)
                                    playPosition = player.getCurrentPosition();
                                player.removeListener(playerEventListener);
                                player.release();
                                player = null;
                                is_background = false;
                                hit_api_for_video_link(false);

                            } else {
                                errorString = drmError + " " + ((PallyconServerResponseException) drmError).getErrorCode();

                            }
                        }
                    } else if (e.type == ExoPlaybackException.TYPE_UNEXPECTED) {
                        Exception cause1 = e.getUnexpectedException();
                        errorString = cause1.toString();
                    } else {
                        errorString = e.toString();
                    }
                }
                if (errorString != null && !errorString.contains("7011")) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DrmVideoPlayerActivity.this);
                    builder.setTitle("Play Error");
                    builder.setMessage(errorString);
                    builder.setPositiveButton("OK", null);
                    Dialog dialog = builder.create();
                    dialog.show();
                    inErrorState = true;
                }

            }

        }

        /*************************end code on 25-12-2020***********************************************/


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

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            playPosition = player.getCurrentPosition();

        }
    }

    @Override
    protected void onStop() {
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.setPlayWhenReady(false);
            player.getPlaybackState();
            is_bg = true;
        }

        if (islive.equalsIgnoreCase("0")) {
            try {
                if (utkashRoom.getyoutubedata().isUserExist(video_id, MakeMyExam.userId, isaudio)) {
                    int result = utkashRoom.getyoutubedata().updateTime(playPosition, video_id, MakeMyExam.userId, isaudio);
                } else {
                    YoutubePlayerTable youtubePlayerTable = new YoutubePlayerTable();
                    youtubePlayerTable.setYoutubeid(url);
                    youtubePlayerTable.setYoutubetime(playPosition);
                    youtubePlayerTable.setIsaudio(isaudio);
                    youtubePlayerTable.setVideoid(video_id);
                    youtubePlayerTable.setVideoname(video_name);
                    youtubePlayerTable.setUserid(MakeMyExam.userId);
                    utkashRoom.getyoutubedata().addVideo(youtubePlayerTable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE) {
            closeFullscreenDialog();
        } else {
            if (isaudio.equalsIgnoreCase("1") && !(url == null || url.equalsIgnoreCase(""))) {

                is_background = true;
                hit_api_for_video_link(false);
            } else {
                releasePlayer();
                super.onBackPressed();
            }
        }
    }


    private void releasePlayer() {
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.release();
            simpleExoPlayerView.setPlayer(null);
            player = null;
            url = "";
            newOrientation = 0;
        }
    }

    private void background_play() {
        Context context = DrmVideoPlayerActivity.this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.app_name);
        builder.setPositiveButton(Html.fromHtml("<font color='#000000'>Stop</font>"),
                (dialog, id) -> {
                    AudioPlayerService.isAudioPlaying = false;
                    AudioPlayerService.videoid = "";
                    AudioPlayerService.media_id = "";
                    AudioPlayerService.video_currentpos = 0L;
                    MakeMyExam.bitmap = null;
                    if (utkashRoom.getyoutubedata().isUserExist(video_id, MakeMyExam.userId, isaudio)) {
                        if (player != null)
                            utkashRoom.getyoutubedata().updateTime(player.getCurrentPosition(), video_id, MakeMyExam.userId, isaudio);
                    } else {
                        YoutubePlayerTable youtubePlayerTable = new YoutubePlayerTable();
                        youtubePlayerTable.setYoutubeid(url);
                        youtubePlayerTable.setYoutubetime(playPosition);
                        youtubePlayerTable.setIsaudio(isaudio);
                        youtubePlayerTable.setVideoid(video_id);
                        youtubePlayerTable.setVideoname(video_name);
                        youtubePlayerTable.setUserid(MakeMyExam.userId);
                        utkashRoom.getyoutubedata().addVideo(youtubePlayerTable);
                    }
                    //  utkashRoom.getaudiodao().update_videotime(video_id, MakeMyExam.userId,player.getCurrentPosition(),url);
                    releasePlayer();
                    dialog.cancel();
                    super.onBackPressed();
                });

        builder.setNeutralButton(Html.fromHtml("<font color='#000000'>Cancel</font>"),
                (dialog, id) -> {
                    dialog.cancel();
                });

        builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Play In background</font>"),
                (dialog, id) -> {

                    Intent openRelatedRefsIntent = new Intent(DrmVideoPlayerActivity.this, AudioPlayerService.class);
                    openRelatedRefsIntent.putExtra("videoUrl", link);
                    if (islive != null && islive.equalsIgnoreCase("5")) {
                        openRelatedRefsIntent.putExtra("isLive", true);
                    } else {
                        openRelatedRefsIntent.putExtra("isLive", true);
                    }
                    openRelatedRefsIntent.putExtra("shopView", false);
                    openRelatedRefsIntent.putExtra("type", "aws");
                    MakeMyExam.bitmap = image;

                    openRelatedRefsIntent.putExtra("intent", "videos");
                    openRelatedRefsIntent.setAction("Start_Service");
                    openRelatedRefsIntent.putExtra("setAudioSeekPosition", true);
                    try {
                        openRelatedRefsIntent.putExtra("audioTitle", video_name);
                    } catch (Exception e) {
                        openRelatedRefsIntent.putExtra("audioTitle", video_name);
                    }
                    openRelatedRefsIntent.putExtra("audioOnly", true);

                    openRelatedRefsIntent.putExtra("cookieData", cookieData);
                    openRelatedRefsIntent.putExtra("token", token);
                    openRelatedRefsIntent.putExtra("siteid", site_id);
                    openRelatedRefsIntent.putExtra("sitekey", site_key);

                    openRelatedRefsIntent.putExtra("audioDesc", video_name);
                    openRelatedRefsIntent.putExtra("videoid", video_id);
                    openRelatedRefsIntent.putExtra("media_id", url);
                    openRelatedRefsIntent.putExtra("player_pos", player.getCurrentPosition());

                    if (utkashRoom.getyoutubedata().isUserExist(video_id, MakeMyExam.userId, isaudio)) {
                        if (player != null)
                            utkashRoom.getyoutubedata().updateTime(player.getCurrentPosition(), video_id, MakeMyExam.userId, isaudio);
                    } else {
                        YoutubePlayerTable youtubePlayerTable = new YoutubePlayerTable();
                        youtubePlayerTable.setYoutubeid(url);
                        youtubePlayerTable.setYoutubetime(playPosition);
                        youtubePlayerTable.setIsaudio(isaudio);
                        youtubePlayerTable.setVideoid(video_id);
                        youtubePlayerTable.setVideoname(video_name);
                        youtubePlayerTable.setUserid(MakeMyExam.userId);
                        utkashRoom.getyoutubedata().addVideo(youtubePlayerTable);
                    }

                    Util.startForegroundService(DrmVideoPlayerActivity.this, openRelatedRefsIntent);
                    AudioPlayerService.isAudioPlaying = true;

                    releasePlayer();
                    finish();
                    dialog.cancel();

                });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.pos = "1";

        if (mFirebaseDatabaseReferenceone2one != null) {
            mFirebaseDatabaseReferenceone2one.removeEventListener(valueEventListener);
            if (query != null) {
                query.removeEventListener(childEventListener);
            }
        }

        if (islive.equalsIgnoreCase("5")) {
            setUserOffline();
        }
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.release();
            player = null;
            if (playerEventListener != null)
                player.removeListener(playerEventListener);
        }

        simpleExoPlayerView = null;
        utkashRoom = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            if (MakeMyExam.userId.equalsIgnoreCase("") || MakeMyExam.userId.equalsIgnoreCase("0")) {
                MakeMyExam.userId = userdata.getId();
                MakeMyExam.setUserId(userdata.getId());
            }

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
            if (Build.VERSION.SDK_INT >= 31) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }

            } else
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

        if (is_bg) {
            is_bg = false;
            if (player != null) {
                player.setPlayWhenReady(true);
                player.getPlaybackState();
            }
        } else {
            if (!(link == null || link.equalsIgnoreCase("")))
                initiallizeplayer(link);
        }


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

    public void showchat() {
        if (isclicked.equalsIgnoreCase("1")) {
            int orientation1 = getResources().getConfiguration().orientation;
            if (orientation1 != Configuration.ORIENTATION_LANDSCAPE) {
                linearLayout.setVisibility(View.VISIBLE);
                chatlayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hidechat() {
        if (isclicked.equalsIgnoreCase("1")) {
            int orientation1 = getResources().getConfiguration().orientation;
            if (orientation1 != Configuration.ORIENTATION_LANDSCAPE) {
                linearLayout.setVisibility(View.GONE);
                chatlayout.setVisibility(View.GONE);
            }
        }
    }

    float total = 0;
    float attempt_1_count = 0, attempt_2_count = 0, attempt_3_count = 0, attempt_4_count = 0;

    public HashMap<String, Float> getServeyData(Polldata polldata) {
        HashMap<String, Float> servaydata = new HashMap<>();
        mFirebaseDatabaseReferencepolldata = null;
        mFirebaseDatabaseReferencepolldata = FirebaseDatabase.getInstance("https://utkarsh-window-app-default-rtdb.firebaseio.com/").getReference().child("chat_master/" + Chat_node + "/" + "Poll/" + polldata.getRendomkey());
        mFirebaseDatabaseReferencepolldata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() == null) {

                    } else {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String clubkey = childSnapshot.getKey();
                            if (clubkey.equalsIgnoreCase("attempt_1")) {
                                attempt_1_count = Float.parseFloat(String.valueOf(childSnapshot.getValue()));
                            }
                            if (clubkey.equalsIgnoreCase("attempt_2")) {
                                attempt_2_count = Float.parseFloat(String.valueOf(childSnapshot.getValue()));
                            }

                            if (clubkey.equalsIgnoreCase("attempt_3")) {
                                attempt_3_count = Float.parseFloat(String.valueOf(childSnapshot.getValue()));
                            }

                            if (clubkey.equalsIgnoreCase("attempt_4")) {
                                attempt_4_count = Float.parseFloat(String.valueOf(childSnapshot.getValue()));
                            }
                        }
                        total = attempt_1_count + attempt_2_count + attempt_3_count + attempt_4_count;
                        servaydata.put("total", total);
                        if (attempt_1_count != 0) {
                            float data = (attempt_1_count / total) * 100;
                            servaydata.put("perA", data);
                        } else {
                            servaydata.put("perA", 0f);
                        }
                        if (attempt_2_count != 0) {
                            float data = (attempt_2_count / total) * 100;
                            servaydata.put("perB", data);
                        } else {
                            servaydata.put("perB", 0f);
                        }
                        if (attempt_3_count != 0) {
                            float data = (attempt_3_count / total) * 100;
                            servaydata.put("perC", data);
                        } else {
                            servaydata.put("perC", 0f);
                        }
                        if (attempt_4_count != 0) {
                            float data = (attempt_4_count / total) * 100;
                            servaydata.put("perD", data);
                        } else {
                            servaydata.put("perD", 0f);
                        }

                        pollAdapter.SetServeyresult(polldata, servaydata);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return servaydata;
    }
}