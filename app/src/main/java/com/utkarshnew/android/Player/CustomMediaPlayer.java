package com.utkarshnew.android.Player;

import static android.os.Build.VERSION.SDK_INT;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.network.connectionclass.ConnectionQuality;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
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
import com.naveed.ytextractor.ExtractorException;
import com.naveed.ytextractor.YoutubeStreamExtractor;
import com.naveed.ytextractor.model.YoutubeMedia;
import com.naveed.ytextractor.model.YoutubeMeta;
import com.naveed.ytextractor.utils.LogUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.courses.modal.OnlineUser;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.Bookmark;
import com.utkarshnew.android.Model.MediaFile;
import com.utkarshnew.android.Model.PlayerPojo.Addindex;
import com.utkarshnew.android.Model.PlayerPojo.Metadata;
import com.utkarshnew.android.Model.PlayerPojo.Pdf;
import com.utkarshnew.android.Model.PlayerPojo.Polldata;
import com.utkarshnew.android.Model.PlayerPojo.VideoTimeFramePojo;
import com.utkarshnew.android.Model.chatPojo;
import com.utkarshnew.android.OnSingleClickListener;
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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class CustomMediaPlayer extends AppCompatActivity implements AmazonCallBack, NetworkCall.MyNetworkCallBack {

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
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    ProgressBar progressBar;
    String audio_url = "", islockedback;
    private DataSource.Factory mediaDataSourceFactory;
    private String userAgent;
    String vodliveyoutuveurl = "0";

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    MediaSource mediaSource;
    DefaultTrackSelector trackSelector;
    private boolean isShowingTrackSelectionDialog;
    private TrackSelectionHelper trackSelectionHelper;
    private ConnectionQuality mConnectionClass = ConnectionQuality.UNKNOWN;
    String url = "";
    private List<Integer> sparseKeyList, sparseAdaptiveResolutionList, sparseMuxedResolutionList;
    private HashMap<Integer, String> sparseAdaptiveVideoUrlList, sparseMuxedVideoUrlList;
    private List<String> sparseOPUSAudioUrl;
    private static long playPosition = 0;
    ImageView quality;
    private String islive;
    ChatAdapter chatAdapter;
    private String isaudio;
    private ImageView mFullScreenIcon;
    ChildEventListener getchatdata;
    private TextView tvGoLive;
    private RecyclerView recyclerChat;
    ImageView ivSend;
    LinearLayout linearLayout, llll, chatlayout;
    TextView addBookmark;
    ArrayList<chatPojo> arrChat = new ArrayList<>();
    ArrayList<String> keylistonetoone = new ArrayList<>();
    ArrayList<chatPojo> pollarr = new ArrayList<>();
    ArrayList<Polldata> pollarraylist = new ArrayList<>();
    EditText etMessage;
    private ImageView chatAddButton;
    private TextView speedTV;
    String speedx = "";
    private FrameLayout mFullScreenButton;
    private boolean mExoPlayerFullscreen = false;
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private Dialog mFullScreenDialog;
    View rootView;
    String thumbnailurl, video_id, video_name;
    Bitmap image;
    public boolean isActivityLive = false;

    TextView bookmarkBtn, indexBtn, chat_btn;
    private BookmarkAdapter bookmarkAdapter;
    private Adapter_recycleveiw_vedio adapter;
    private PollAdapter pollAdapter;
    private List<VideoTimeFramePojo> indexdata = new ArrayList<>();
    ArrayList<chatPojo> lockarr = new ArrayList<>();
    private List<VideoTimeFramePojo> bookmarkdata = new ArrayList<>();
    private List<Pdf> pdf = new ArrayList<>();
    // add bookmark index
    private DatabaseReference mFirebaseDatabaseReference1;
    private String time = "";
    private String info = "";
    private String state = "";
    private TextView notes, poll;
    private NotesAdapter notesAdapter;
    NetworkCall networkCall;
    ImageView audiocaetimage;
    String Chat_node = "", course_id = "", tileid = "", tiletype = "";
    TextView floatingText, video_name_text;
    Data userdata;
    private DatabaseReference mFirebaseDatabaseReferenceone2one;
    private DatabaseReference mFirebaseDatabaseReferenceone2many;
    private DatabaseReference mFirebaseDatabaseReferencepolldata;
    DataSource.Factory dataSourceFactory;
    ValueEventListener valueEventListener;
    String position = "";
    String parentid = "";
    private boolean isintractavailable = false;
    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_custom_media_player);
        youtubeUri = "";
        utkashRoom = UtkashRoom.getAppDatabase(this);
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(Const.VIDEO_LINK);
            islive = getIntent().getExtras().getString(Const.VIDEO_LIVE);
            isaudio = getIntent().getExtras().getString("isaudio");
            thumbnailurl = "http://img.youtube.com/vi/" + url + "/0.jpg";
            video_id = getIntent().getStringExtra("video_id");
            video_name = getIntent().getStringExtra("video_name");
            Chat_node = getIntent().getStringExtra("Chat_node");
            islockedback = getIntent().getStringExtra("islocked");
            course_id = getIntent().getStringExtra("courseid");
            parentid = getIntent().getStringExtra("parentid");
            tileid = getIntent().getStringExtra("tileid");
            tiletype = getIntent().getStringExtra("tiletype");
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


        if (savedInstanceState != null) {
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view_new);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        quality = findViewById(R.id.quality);
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
        video_name_text.setSelected(true);
        video_name_text.setText(video_name);

        floatingText.setText(SharedPreference.getInstance().getLoggedInUser().getMobile());
        floatingText.measure(0, 0);
        floatingText.setVisibility(View.VISIBLE);


        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
        mediaDataSourceFactory = buildDataSourceFactory(false);

//        dataSourceFactory = new DefaultDataSourceFactory(this,
//                new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "ExoPlayerDemo")));

        if (mFirebaseDatabaseReferenceone2one != null) {
            mFirebaseDatabaseReferenceone2one.removeEventListener(valueEventListener);
            if (query != null) {
                query.removeEventListener(childEventListener);
            }
        }

        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();

        hit_api_for_meta();
        hit_api_for_video_link();


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
            setPoll();
            return null;
        }));

        indexBtn.setOnClickListener(new OnSingleClickListener(() -> {
            setIndex();
            return null;
        }));

        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setchat();
            }
        });

        notes.setOnClickListener(new OnSingleClickListener(() -> {
            setNotes();
            return null;
        }));

        bookmarkBtn.setOnClickListener(new OnSingleClickListener(() -> {

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


        findViewById(R.id.quality).setOnClickListener(new OnSingleClickListener(() -> {
            if (islive.equalsIgnoreCase("1")) {
                if (sparseAdaptiveResolutionList == null) {
                    Toast.makeText(CustomMediaPlayer.this, "No quality fouund till yet", Toast.LENGTH_SHORT).show();
                } else {
                    if (player != null)
                        showAlertDialog();
                    else
                        Toast.makeText(CustomMediaPlayer.this, "No quality fouund till yet", Toast.LENGTH_SHORT).show();
                }
            } else if (islive.equalsIgnoreCase("4")) {
                if (sparseAdaptiveResolutionList == null || sparseAdaptiveResolutionList.size() == 0) {
                    if (trackSelector != null && trackSelector.getCurrentMappedTrackInfo() != null) {
                        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                        if (mappedTrackInfo != null) {
                            isShowingTrackSelectionDialog = true;
                            TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(
                                    trackSelector,
                                    /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                            trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
                        }
                    } else {
                        Toast.makeText(CustomMediaPlayer.this, "Player not ready, try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (sparseAdaptiveResolutionList == null) {
                        Toast.makeText(CustomMediaPlayer.this, "No quality fouund till yet", Toast.LENGTH_SHORT).show();
                    } else {
                        if (player != null)
                            showAlertDialogLive();
                        else
                            Toast.makeText(CustomMediaPlayer.this, "No quality fouund till yet", Toast.LENGTH_SHORT).show();
                    }
                }


            }
            return null;
        }));
    }

    private int savedOrientation;

    private boolean isPortrait(int orientation) {
        com.google.android.exoplayer2.util.Log.d("prince", "" + orientation);
        if (orientation < 85 || orientation > 100) {
            return true;
        }
        return false;
    }


    private void showAlertDialogLive() {
        final int[] selectedpos = {-1};
        int checkedItem = 0;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomMediaPlayer.this);
        alertDialog.setTitle("Select Track");
        String[] items = new String[sparseAdaptiveResolutionList.size()];
        for (int i = 0; i < sparseAdaptiveResolutionList.size(); i++) {
            items[i] = String.valueOf(sparseAdaptiveResolutionList.get(i) + "p");
        }
        if (lastseleted == -1) {
            checkedItem = 0;
        } else {
            checkedItem = lastseleted;
        }

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedpos[0] = which;
                lastseleted = which;
            }
        });
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        playPosition = player.getCurrentPosition();

                        if (selectedpos[0] == -1) {
                            Toast.makeText(CustomMediaPlayer.this, "Please change quality first", Toast.LENGTH_SHORT).show();
                        } else {
                            if (player != null) {
                                player.release();
                                player = null;
                            }
                            Loadyoutubeurl(sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(selectedpos[0])));
                        }
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resumePlayer();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

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
            if (AppPermissionsRunTime.checkPermission(CustomMediaPlayer.this, myPermissionConstantsArrayList, REQUEST_CODE_PERMISSION_MULTIPLE)) {
                //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
                imgClick();
            }
        } else {
            // Pre-Marshmallow
            //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
            imgClick();
        }
    }

    private void hit_api_for_video_link() {
        networkCall.NetworkAPICall(API.get_video_link, "", false, false);
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

    private void showAlertDialog() {
        if (vodliveyoutuveurl.equalsIgnoreCase("0")) {
            final int[] selectedpos = {-1};
            int checkedItem = 0;

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomMediaPlayer.this);
            alertDialog.setTitle("Select Track");
            String[] items = new String[sparseAdaptiveResolutionList.size()];
            for (int i = 0; i < sparseAdaptiveResolutionList.size(); i++) {
                items[i] = String.valueOf(sparseAdaptiveResolutionList.get(i) + "p");
            }
            if (lastseleted == -1) {
                checkedItem = (sparseAdaptiveResolutionList.size() - 1);
            } else {
                checkedItem = lastseleted;
            }

            alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedpos[0] = which;
                    lastseleted = which;
                }
            });
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            playPosition = player.getCurrentPosition();

                            if (selectedpos[0] == -1) {
                                Toast.makeText(CustomMediaPlayer.this, "Please change quality first", Toast.LENGTH_SHORT).show();
                            } else {
                                if (player != null) {
                                    player.release();
                                    player = null;
                                }
                                Loadyoutubeurl(sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(selectedpos[0])));
                            }
                        }
                    });
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resumePlayer();
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        } else {
            final int[] selectedpos = {-1};
            int checkedItem = 0;

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomMediaPlayer.this);
            alertDialog.setTitle("Select Track");
            String[] items = new String[sparseAdaptiveResolutionList.size()];
            for (int i = 0; i < sparseAdaptiveResolutionList.size(); i++) {
                items[i] = String.valueOf(sparseAdaptiveResolutionList.get(i) + "p");
            }
            if (lastseleted == -1) {
                checkedItem = 0;
            } else {
                checkedItem = lastseleted;
            }

            alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedpos[0] = which;
                    lastseleted = which;
                }
            });
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            playPosition = player.getCurrentPosition();

                            if (selectedpos[0] == -1) {
                                Toast.makeText(CustomMediaPlayer.this, "Please change quality first", Toast.LENGTH_SHORT).show();
                            } else {
                                if (player != null) {
                                    player.release();
                                    player = null;
                                }
                                Loadyoutubeurl(sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(selectedpos[0])));
                            }
                        }
                    });
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resumePlayer();
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        }
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

  /*  private void setUserOnline() {
        if (mFirebaseDatabaseReference1 == null) {
            try {
                final String[] value = {"0"};
                mFirebaseDatabaseReference1 = FirebaseDatabase.getInstance().getReference().child("chat_master/").child(Chat_node).child("User");
                mFirebaseDatabaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            OnlineUser onlineUser = childSnapshot.getValue(OnlineUser.class);
                            if(Objects.requireNonNull(onlineUser).getOnline().equalsIgnoreCase("1"))
                            {
                                value[0] =onlineUser.getInteract();
                            }
                            else
                            {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                OnlineUser onlineUser=new OnlineUser(SharedPreference.getInstance().getLoggedInUser().getName(),SharedPreference.getInstance().getLoggedInUser().getProfilePicture(),Const.DEVICE_TYPE_ANDROID,"true",SharedPreference.getInstance().getLoggedInUser().getId(),SharedPreference.getInstance().getLoggedInUser().getMobile(), value[0]);
                mFirebaseDatabaseReference1.child(SharedPreference.getInstance().getLoggedInUser().getId()).setValue(onlineUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/


    private void setUserOnline() {
        mFirebaseDatabaseReference1 = null;
        if (mFirebaseDatabaseReference1 == null) {
            try {
                mFirebaseDatabaseReference1 = FirebaseDatabase.getInstance().getReference().child("chat_master/").child(Chat_node).child("User").child(MakeMyExam.userId);

                mFirebaseDatabaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            OnlineUser onlineUser = new OnlineUser(SharedPreference.getInstance().getLoggedInUser().getName(), SharedPreference.getInstance().getLoggedInUser().getProfilePicture(), Const.DEVICE_TYPE_ANDROID, "true", MakeMyExam.userId, SharedPreference.getInstance().getLoggedInUser().getMobile(), "0", MakeMyExam.getTime_server(), userdata.getChat_block());
                            mFirebaseDatabaseReference1.setValue(onlineUser);
                        } else {
                            String json = new Gson().toJson(dataSnapshot.getValue());
                            OnlineUser example = new Gson().fromJson(json, OnlineUser.class);

                            if (example.getId() != null && example.getId().equals(MakeMyExam.userId)) {
                                mFirebaseDatabaseReference1.child("online").setValue("true");
                            } else {
                                if (MakeMyExam.getTime_server() != 0) {
                                    OnlineUser onlineUser = new OnlineUser(SharedPreference.getInstance().getLoggedInUser().getName(), SharedPreference.getInstance().getLoggedInUser().getProfilePicture(), Const.DEVICE_TYPE_ANDROID, "true", MakeMyExam.userId, SharedPreference.getInstance().getLoggedInUser().getMobile(), "0", MakeMyExam.getTime_server(), userdata.getChat_block());
                                    mFirebaseDatabaseReference1.setValue(onlineUser);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                OnlineUser onlineUser=new OnlineUser(SharedPreference.getInstance().getLoggedInUser().getName(),SharedPreference.getInstance().getLoggedInUser().getProfilePicture(),Const.DEVICE_TYPE_ANDROID,"true",SharedPreference.getInstance().getLoggedInUser().getId(),SharedPreference.getInstance().getLoggedInUser().getMobile());
//                mFirebaseDatabaseReference1.child(SharedPreference.getInstance().getLoggedInUser().getId()).setValue(onlineUser);
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


    String checkstatus = "";
    String islocked = "0";

    private void fireBaseOperation() {
        mFirebaseDatabaseReferenceone2many = FirebaseDatabase.getInstance().getReference().child("chat_master/" + Chat_node + "/1TOM/");
        mFirebaseDatabaseReferenceone2one = FirebaseDatabase.getInstance().getReference().child("chat_master/" + Chat_node + "/1TO1/" + MakeMyExam.userId);

        valueEventListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    checkstatus = "1";
                    getupdatedchatdata(MakeMyExam.getTime_server());
                } else {
                    try {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            chatPojo message = childSnapshot.getValue(chatPojo.class);
                            if (!Objects.requireNonNull(message).getType().equalsIgnoreCase("poll") && !message.getType().equalsIgnoreCase("is_chat_locked")) {
                                arrChat.add(message);
                                pollarr.add(message);
                                keylistonetoone.add(childSnapshot.getKey());
                                Log.d("abhi2", "" + arrChat.size());
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
                            String message = lockarr.get(lockarr.size() - 1).getMessage();
                            if (userdata.getChat_block() != null && userdata.getChat_block().equalsIgnoreCase("1")) {
                                islocked = "2";
                                hidechat();
                            } else {
                                if (message.equalsIgnoreCase("0")) {
                                    islocked = "1";
                                    showchat();
                                } else {
                                    islocked = "2";
                                    hidechat();

                                }
                            }

                        } else {
                            islocked = "1";
                            showchat();
                        }
                    } catch (Exception e) {
                        Toast.makeText(CustomMediaPlayer.this, "null pointer exception", Toast.LENGTH_SHORT).show();
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
                    chatPojo message = new chatPojo(MakeMyExam.userId, etMessage.getText().toString(), SharedPreference.getInstance().getLoggedInUser().getName(), System.currentTimeMillis(), "1", SharedPreference.getInstance().getLoggedInUser().getProfilePicture(), Const.DEVICE_TYPE_ANDROID, "text", course_id);
                    mFirebaseDatabaseReferenceone2one.push().setValue(message);
//                    message.setFirebase_id(dataSnapshot.getKey());
                    etMessage.setText("");
                    Helper.hideKeyboard(CustomMediaPlayer.this);
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

    ChildEventListener removeventlistner;

    Query query;

    public void getupdatedchatdata(long actualdate) {
        rootRef = FirebaseDatabase.getInstance().getReference().child("chat_master/" + Chat_node + "/1TO1/" + MakeMyExam.userId);
        query = rootRef.orderByChild("date").startAt(actualdate);

        childEventListener = new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
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
                                    if (userdata.getChat_block() != null && userdata.getChat_block().equalsIgnoreCase("1")) {
                                        islocked = "2";
                                        hidechat();
                                    }
                                    if (message.getMessage().equalsIgnoreCase("0")) {
                                        islocked = "1";
                                        showchat();

                                    } else {
                                        islocked = "2";
                                        hidechat();
                                    }
                                }
                            }
                            Log.d("abhi2", "" + arrChat.size());
                            chatAdapter.notifyDataSetChanged();
                            recyclerChat.smoothScrollToPosition(arrChat.size());
                        } catch (Exception e) {
                            Toast.makeText(CustomMediaPlayer.this, "null pointer exception", Toast.LENGTH_SHORT).show();
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
                            if (lockarr.size() == 0) {
                                if (userdata.getChat_block() != null && userdata.getChat_block().equalsIgnoreCase("1")) {
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
                            OnlineUser onlineUser = dataSnapshot.getValue(OnlineUser.class);
                            isintractavailable = true;
                            if (onlineUser.getInteract() != null && onlineUser.getInteract().equalsIgnoreCase("0")) {
                                mFirebaseDatabaseReference1.child("interact").setValue("1");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                OnlineUser onlineUser=new OnlineUser(SharedPreference.getInstance().getLoggedInUser().getName(),SharedPreference.getInstance().getLoggedInUser().getProfilePicture(),Const.DEVICE_TYPE_ANDROID,"true",SharedPreference.getInstance().getLoggedInUser().getId(),SharedPreference.getInstance().getLoggedInUser().getMobile());
//                mFirebaseDatabaseReference1.child(SharedPreference.getInstance().getLoggedInUser().getId()).setValue(onlineUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getpolldatawithid(String randomid) {
        mFirebaseDatabaseReferencepolldata = null;
        mFirebaseDatabaseReferencepolldata = FirebaseDatabase.getInstance().getReference().child("chat_master/" + Chat_node + "/" + "Poll/" + randomid);
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
                                poll.setText("New Poll");
                                Snackbar.make(rootView.getRootView(), "New Poll Added", Snackbar.LENGTH_SHORT).show();
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
                        Helper.hideKeyboard(CustomMediaPlayer.this);
                        BookMarkApi("", time, info, "1");
                    } else {
                        Toast.makeText(CustomMediaPlayer.this, "Add Title", Toast.LENGTH_SHORT).show();
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


    public void Loadyoutubeurl(String youtubeUri) {
        if (isFinishing() || isDestroyed())
            return;
        progressBar.setVisibility(View.VISIBLE);
        if (sparseAdaptiveVideoUrlList != null && sparseAdaptiveVideoUrlList.size() > 0 && islive.equalsIgnoreCase("1")) {
            Uri videoUri, audioUri = null;
            videoUri = Uri.parse(youtubeUri);
            if (!sparseOPUSAudioUrl.isEmpty()) {
                audioUri = Uri.parse(sparseOPUSAudioUrl.get(0));
            }
            if (isaudio.equalsIgnoreCase("1")) {
                if (vodliveyoutuveurl.equalsIgnoreCase("0")) {
                    if (!sparseOPUSAudioUrl.isEmpty()) {
                        try {
                            quality.setVisibility(View.GONE);
                            youtubeUri = sparseOPUSAudioUrl.get(0);
                            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                            trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
                            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
                            trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
                            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                            player.addListener(playerEventListener);
                            simpleExoPlayerView.setPlayer(player);
                            if (image != null) {
                                Drawable drawable = new BitmapDrawable(getResources(), image);
                                simpleExoPlayerView.setDefaultArtwork(drawable);
                            }
                            player.setPlayWhenReady(true);
                            mediaSource = buildMediaSource(audioUri, null);
                            player.prepare(mediaSource);
                            player.seekTo(playPosition);
                            audio_url = youtubeUri;
                            try {
                                UserHistroyTable userHistroyTable = new UserHistroyTable();
                                userHistroyTable.setVideo_id(video_id);
                                userHistroyTable.setVideo_name(video_name);
                                userHistroyTable.setType("Youtube Audio");
                                userHistroyTable.setYoutube_url(url);
                                userHistroyTable.setTileid(tileid);

                                userHistroyTable.setUser_id(MakeMyExam.userId);
                                if (parentid.equalsIgnoreCase("")) {
                                    userHistroyTable.setCourse_id(course_id + "#");
                                } else {
                                    userHistroyTable.setCourse_id(parentid + "#" + course_id);
                                }
                                userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                                utkashRoom.getuserhistorydao().addUser(userHistroyTable);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    quality.setVisibility(View.GONE);
                    try {
                        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
                        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
                        trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
                        Uri videouri = Uri.parse(youtubeUri);
                        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                        player.addListener(playerEventListener);
                        simpleExoPlayerView.setPlayer(player);
                        player.setPlayWhenReady(true);
                        mediaSource = buildMediaSource(videouri, null);
                        player.prepare(mediaSource);
                        player.seekTo(playPosition);
                        simpleExoPlayerView.setControllerShowTimeoutMs(0);
                        simpleExoPlayerView.setControllerHideOnTouch(false);
                        audiocaetimage.setVisibility(View.VISIBLE);
                        audio_url = youtubeUri;
                        Helper.setThumbnailImage(this, thumbnailurl, this.getDrawable(R.mipmap.course_placeholder), audiocaetimage);

                        try {
                            UserHistroyTable userHistroyTable = new UserHistroyTable();
                            userHistroyTable.setVideo_id(video_id);
                            userHistroyTable.setVideo_name(video_name);
                            userHistroyTable.setType("Youtube Audio");
                            userHistroyTable.setYoutube_url(url);
                            userHistroyTable.setTileid(tileid);

                            userHistroyTable.setUser_id(MakeMyExam.userId);
                            if (parentid.equalsIgnoreCase("")) {
                                userHistroyTable.setCourse_id(course_id + "#");
                            } else {
                                userHistroyTable.setCourse_id(parentid + "#" + course_id);
                            }
                            userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                            utkashRoom.getuserhistorydao().addUser(userHistroyTable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } else {
                try {

                    if (vodliveyoutuveurl.equalsIgnoreCase("0")) {
                        ProgressiveMediaSource videoSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(videoUri);
                        ProgressiveMediaSource audioSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(audioUri);
                        MergingMediaSource resource = new MergingMediaSource(videoSource, audioSource);
                        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
                        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
                        trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
//                    Uri videouri = Uri.parse(youtubeUri);
                        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                        player.addListener(playerEventListener);
                        simpleExoPlayerView.setPlayer(player);
                        if (image != null) {
                            Drawable drawable = new BitmapDrawable(getResources(), image);
                            simpleExoPlayerView.setDefaultArtwork(drawable);
                        }
                        player.setPlayWhenReady(true);
//                    mediaSource = buildMediaSource(videouri, null);
                        player.prepare(resource);


                        player.seekTo(playPosition);
                    } else {
                        try {
                            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                            trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
                            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
                            trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
                            Uri videouri = Uri.parse(youtubeUri);
                            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                            player.addListener(playerEventListener);
                            simpleExoPlayerView.setPlayer(player);
                            player.setPlayWhenReady(true);
                            mediaSource = buildMediaSource(videouri, null);
                            player.prepare(mediaSource);
                            player.seekTo(playPosition);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        UserHistroyTable userHistroyTable = new UserHistroyTable();
                        userHistroyTable.setVideo_id(video_id);
                        userHistroyTable.setVideo_name(video_name);
                        userHistroyTable.setType("Youtube Video");
                        userHistroyTable.setTileid(tileid);
                        userHistroyTable.setYoutube_url(url);
                        userHistroyTable.setUser_id(MakeMyExam.userId);
                        if (parentid.equalsIgnoreCase("")) {
                            userHistroyTable.setCourse_id(course_id + "#");
                        } else {
                            userHistroyTable.setCourse_id(parentid + "#" + course_id);
                        }

                        userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                        utkashRoom.getuserhistorydao().addUser(userHistroyTable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (isaudio.equalsIgnoreCase("1")) {
                quality.setVisibility(View.GONE);
                try {
                    TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                    trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
                    trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
                    trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
                    Uri videouri = Uri.parse(youtubeUri);
                    player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                    player.addListener(playerEventListener);
                    simpleExoPlayerView.setPlayer(player);
                    player.setPlayWhenReady(true);
                    mediaSource = buildMediaSource(videouri, null);
                    player.prepare(mediaSource);
                    simpleExoPlayerView.setControllerShowTimeoutMs(0);
                    simpleExoPlayerView.setControllerHideOnTouch(false);
                    audiocaetimage.setVisibility(View.VISIBLE);
                    audio_url = youtubeUri;
                    Helper.setThumbnailImage(this, thumbnailurl, this.getDrawable(R.mipmap.course_placeholder), audiocaetimage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                    trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
                    trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
                    trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
                    Uri videouri = Uri.parse(youtubeUri);
                    player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                    player.addListener(playerEventListener);
                    simpleExoPlayerView.setPlayer(player);
                    player.setPlayWhenReady(true);
                    mediaSource = buildMediaSource(videouri, null);
                    player.prepare(mediaSource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            player.seekTo(playPosition);
        }
    }

    private void showSpeedOptions() {

        PopupMenu popupMenu = new PopupMenu(this, speedTV, R.style.MyPopupMenu);
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
                        speedTV.setText(title);
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
            }

        }


        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {

            inErrorState = true;
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


    @SuppressWarnings("unchecked")
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

    public void onDelete(Bookmark data) {
        BookMarkApi(data.getId(), "", "", "2");
    }


    private static String youtubeUri;

    private void extractYoutubeUrl(String url) {

        new YoutubeStreamExtractor(new YoutubeStreamExtractor.ExtractorListner() {
            @Override
            public void onExtractionGoesWrong(ExtractorException e, String message, String data) {
                Log.d("extract", "fail");
            }

            @Override
            public void onExtractionDone(List<YoutubeMedia> adativeStream, List<YoutubeMedia> muxedStream, YoutubeMeta meta) {


                Log.d("extract", String.valueOf(meta.getIsLiveContent()));

                sparseAdaptiveResolutionList = new ArrayList<>();
                sparseMuxedResolutionList = new ArrayList<>();
                sparseAdaptiveVideoUrlList = new HashMap<>();
                sparseMuxedVideoUrlList = new HashMap<>();
                sparseOPUSAudioUrl = new ArrayList<>();

                if (meta.getisLive()) {
                    if (muxedStream.isEmpty()) {
                        LogUtils.log("null ha");
                        return;
                    } else {


                        for (int i = 0; i < muxedStream.size(); i++) {
                            YoutubeMedia media = muxedStream.get(i);
                            if (media.isMuxed()) {
                                //Muxed Video URL
                                int resolution = getLiveIndexResolution(i);
                                if (!sparseAdaptiveResolutionList.contains(resolution) && resolution <= 1080) {
                                    if (!(resolution == 144 || resolution == 240)) {
                                        sparseAdaptiveResolutionList.add(resolution);
                                        sparseAdaptiveVideoUrlList.put(resolution, media.getUrl());
                                    }
                                }
                            }
                        }
                        if (muxedStream.size() > 0) {
                            youtubeUri = sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(0));

                        } else {
                            youtubeUri = LogUtils.getLivevideourl();
                        }
                    }
                } else if (adativeStream.size() > 0) {
                    for (YoutubeMedia media : adativeStream) {
                        if (media.isVideoOnly()) {
                            //Adaptive Video URL
                            int resolution = Integer.valueOf(media.getResolution().replace("p", ""));
                            if (resolution > 1080)
                                continue;
                            if (!sparseAdaptiveResolutionList.contains(resolution)) {
                                if (resolution == 720 || resolution == 360) {
                                    sparseAdaptiveResolutionList.add(resolution);
                                    sparseAdaptiveVideoUrlList.put(resolution, media.getUrl());
                                }
                            }
                        } else if (media.isAudioOnly()) {
                            //OPUS Audio URL
                            if (!sparseOPUSAudioUrl.contains(media.getUrl())) {
                                sparseOPUSAudioUrl.add(media.getUrl());
                            }
                        } else if (media.isMuxed()) {
                            //Progressive Video URL
                            int resolution = Integer.valueOf(media.getResolution().replace("p", ""));
                            if (!sparseAdaptiveResolutionList.contains(resolution) && resolution <= 1080) {
                                sparseAdaptiveResolutionList.add(resolution);
                                sparseAdaptiveVideoUrlList.put(resolution, media.getUrl());

                            }
                        }
                    }
                    youtubeUri = sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(sparseAdaptiveResolutionList.size() - 1));
                } else if (muxedStream.size() > 0) {
                    for (int i = 0; i < muxedStream.size(); i++) {
                        YoutubeMedia media = muxedStream.get(i);
                        if (media.isMuxed()) {
                            //Muxed Video URL
                            int resolution = getLiveIndexResolution(i);
                            if (!sparseAdaptiveResolutionList.contains(resolution) && resolution <= 1080) {
                                if (!(resolution == 144)) {
                                    sparseAdaptiveResolutionList.add(resolution);
                                    sparseAdaptiveVideoUrlList.put(resolution, media.getUrl());
                                }
                            }
                        }
                    }
                    vodliveyoutuveurl = "1";
                    youtubeUri = sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(0));
                } else {
                    if (meta.getIsLiveContent() && meta.getIsLowLatencyLiveStream() && !meta.getisLive()) {

                        onBackPressed();
                    } else {
                        onBackPressed();
                    }
                }

                progressBar.setVisibility(View.GONE);

                if (islive.equalsIgnoreCase("1")) {
                    try {
                        if (utkashRoom.getyoutubedata().isUserExist(video_id, MakeMyExam.userId, isaudio)) {
                            playPosition = utkashRoom.getyoutubedata().getyoutubedata(MakeMyExam.userId, video_id, isaudio);
                        } else {
                            playPosition = 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (youtubeUri != null) {
                    Loadyoutubeurl(youtubeUri);
                }
            }
        }).Extract("https://www.youtube.com/watch?v=" + url);
    }


/*
    private void extractYoutubeUrl(String url) {

        new YoutubeStreamExtractor(new YoutubeStreamExtractor.ExtractorListner() {
            @Override
            public void onExtractionGoesWrong(ExtractorException e, String message, String data) {
                Log.d("extract", "fail");
            }

            @Override
            public void onExtractionDone(List<YoutubeMedia> adativeStream, List<YoutubeMedia> muxedStream, YoutubeMeta meta) {


                Log.d("extract", String.valueOf(meta.getIsLiveContent()));

                sparseAdaptiveResolutionList = new ArrayList<>();
                sparseMuxedResolutionList = new ArrayList<>();
                sparseAdaptiveVideoUrlList = new HashMap<>();
                sparseMuxedVideoUrlList = new HashMap<>();
                sparseOPUSAudioUrl = new ArrayList<>();

                if (meta.getisLive()) {
                    if (muxedStream.isEmpty()) {
                        LogUtils.log("null ha");
                        return;
                    } else {


                        for (int i = 0; i < muxedStream.size(); i++) {
                            YoutubeMedia media = muxedStream.get(i);
                            if (media.isMuxed()) {
                                //Muxed Video URL
                                int resolution = getLiveIndexResolution(i);
                                if (!sparseAdaptiveResolutionList.contains(resolution) && resolution <= 1080) {
                                    if(!(resolution== 144 || resolution ==240)) {
                                        sparseAdaptiveResolutionList.add(resolution);
                                        sparseAdaptiveVideoUrlList.put(resolution, media.getUrl());
                                    }
                                }
                            }
                        }
                        if(muxedStream.size()>0)
                        {
                            youtubeUri = sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(0));

                        }
                        else
                        {
                            youtubeUri = LogUtils.getLivevideourl();
                        }
                    }
                }
                else if (adativeStream.size() > 0) {
                    for (YoutubeMedia media : adativeStream) {
                        if (media.isVideoOnly()) {
                            //Adaptive Video URL
                            int resolution = Integer.valueOf(media.getResolution().replace("p", ""));
                            if (resolution > 1080)
                                continue;
                            if (!sparseAdaptiveResolutionList.contains(resolution)) {
                                if(resolution== 720 || resolution ==360) {
                                    sparseAdaptiveResolutionList.add(resolution);
                                    sparseAdaptiveVideoUrlList.put(resolution, media.getUrl());
                                }
                            }
                        } else if (media.isAudioOnly()) {
                            //OPUS Audio URL
                            if (!sparseOPUSAudioUrl.contains(media.getUrl())) {
                                sparseOPUSAudioUrl.add(media.getUrl());
                            }
                        } else if (media.isMuxed()) {
                            //Progressive Video URL
                            int resolution = Integer.valueOf(media.getResolution().replace("p", ""));
                            if (!sparseAdaptiveResolutionList.contains(resolution) && resolution <= 1080) {
                                sparseAdaptiveResolutionList.add(resolution);
                                sparseAdaptiveVideoUrlList.put(resolution, media.getUrl());

                            }
                        }
                    }
                    youtubeUri = sparseAdaptiveVideoUrlList.get(sparseAdaptiveResolutionList.get(sparseAdaptiveResolutionList.size() - 1));

                } else {
                    if (meta.getIsLiveContent() && meta.getIsLowLatencyLiveStream() && !meta.getisLive()) {

                        onBackPressed();
                    } else {
                        onBackPressed();
                    }
                }

                progressBar.setVisibility(View.GONE);

                if (islive.equalsIgnoreCase("1")) {
                    try {
                        if (utkashRoom.getyoutubedata().isUserExist(video_id, MakeMyExam.userId, isaudio)) {
                            playPosition = utkashRoom.getyoutubedata().getyoutubedata(MakeMyExam.userId, video_id, isaudio);
                        }
                        else
                        {
                            playPosition=0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (youtubeUri != null) {
                    Loadyoutubeurl(youtubeUri);
                }
            }
        }).Extract("https://www.youtube.com/watch?v=" + url);
    }
*/


    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(TransferListener listener) {
        DefaultDataSourceFactory dataSourceFactory = null;

        dataSourceFactory = new DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener));
        return dataSourceFactory;
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(TransferListener listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }


    @Override
    protected void onResume() {
        super.onResume();


        try {

            if (MakeMyExam.userId.equalsIgnoreCase("") || MakeMyExam.userId.equalsIgnoreCase("0")) {
                MakeMyExam.userId = SharedPreference.getInstance().getLoggedInUser().getId();
                MakeMyExam.setUserId(SharedPreference.getInstance().getLoggedInUser().getId());
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

        if (!(youtubeUri == null || youtubeUri.equalsIgnoreCase(""))) {
            Loadyoutubeurl(youtubeUri);
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

    @Override
    protected void onStop() {
        if (player != null) {
            player.release();
            player = null;
        }


        if (islive.equalsIgnoreCase("1")) {
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
            if (isaudio.equalsIgnoreCase("1") && !(youtubeUri == null || youtubeUri.equalsIgnoreCase(""))) {
                Constants.pos = "1";
                background_play();
            } else {
                releasePlayer();
                super.onBackPressed();
            }
        }
    }

    private void background_play() {
        Context context = CustomMediaPlayer.this;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(video_name);
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
                    Intent openRelatedRefsIntent = new Intent(CustomMediaPlayer.this, AudioPlayerService.class);
                    openRelatedRefsIntent.putExtra("videoUrl", audio_url);
                    if (islive != null && islive.equalsIgnoreCase("4")) {
                        openRelatedRefsIntent.putExtra("isLive", true);

                    } else {
                        if (vodliveyoutuveurl.equalsIgnoreCase("0")) {
                            openRelatedRefsIntent.putExtra("isLive", false);
                        } else {
                            openRelatedRefsIntent.putExtra("isLive", true);
                        }

                    }
                    openRelatedRefsIntent.putExtra("shopView", false);
                    openRelatedRefsIntent.putExtra("type", "youtube");
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
                    openRelatedRefsIntent.putExtra("audioDesc", video_name);
                    openRelatedRefsIntent.putExtra("videoid", video_id);
                    openRelatedRefsIntent.putExtra("media_id", url);
                    openRelatedRefsIntent.putExtra("tileid", tileid);
                    openRelatedRefsIntent.putExtra("tiletype", tiletype);
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

                    // utkashRoom.getaudiodao().update_videotime(video_id, MakeMyExam.userId,player.getCurrentPosition(),url);
                    Util.startForegroundService(CustomMediaPlayer.this, openRelatedRefsIntent);
                    AudioPlayerService.isAudioPlaying = true;
                    releasePlayer();
                    finish();
                    dialog.cancel();


                });
        builder.create().show();
    }


    private void releasePlayer() {
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.release();
            simpleExoPlayerView.setPlayer(null);
            player = null;
            youtubeUri = "";
            newOrientation = 0;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }


    private void initFullscreenButton() {
        TextView exo_duration, exo_position;
        DefaultTimeBar defaultTimeBar;
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        trackSelector.setParameters(trackSelectorParameters);
        speedTV = controlView.findViewById(R.id.speedTV);
        defaultTimeBar = controlView.findViewById(R.id.exo_progress);
        exo_position = controlView.findViewById(R.id.exo_position);
        exo_duration = controlView.findViewById(R.id.exo_duration);
        tvGoLive = controlView.findViewById(R.id.tv_go_live);
        audiocaetimage = controlView.findViewById(R.id.audiocaetimage);

        if (islive.equalsIgnoreCase("4")) {
            try {
                defaultTimeBar.setVisibility(View.INVISIBLE);
                exo_position.setVisibility(View.INVISIBLE);
                exo_duration.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
                simpleExoPlayerView.findViewById(R.id.exo_ffwd).setLayoutParams(layoutParams);
                simpleExoPlayerView.findViewById(R.id.exo_rew).setLayoutParams(layoutParams);

            } catch (Exception e) {
                e.printStackTrace();
            }

            speedTV.setVisibility(View.GONE);
            tvGoLive.setVisibility(View.VISIBLE);
        } else {
            speedTV.setVisibility(View.VISIBLE);
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

        if (!TextUtils.isEmpty(speedx)) {
            player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(speedx.replace("x", "")), 1f));
        }


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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mFirebaseDatabaseReferenceone2one != null) {
            mFirebaseDatabaseReferenceone2one.removeEventListener(valueEventListener);
            if (query != null) {
                query.removeEventListener(childEventListener);
            }
        }
        if (mFirebaseDatabaseReferenceone2one != null && removeventlistner != null) {
            mFirebaseDatabaseReferenceone2one.removeEventListener(removeventlistner);

        }
        if (islive.equalsIgnoreCase("4")) {
            setUserOffline();
        }
        youtubeUri = "";
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.release();
            player = null;
            player.removeListener(playerEventListener);
        }

        simpleExoPlayerView = null;
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

                    chatPojo message = new chatPojo(MakeMyExam.userId, images.get(0).getFile(), SharedPreference.getInstance().getLoggedInUser().getName(), MakeMyExam.getTime_server(), "1", SharedPreference.getInstance().getLoggedInUser().getProfilePicture(), Const.DEVICE_TYPE_ANDROID, filetype, course_id);
                    mFirebaseDatabaseReferenceone2one.push().setValue(message);
                    mFirebaseDatabaseReferenceone2many.push().setValue(message);
                    Helper.hideKeyboard(CustomMediaPlayer.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    private void imgClick() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CustomMediaPlayer.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image.jpg");
                    Uri photoURI;
                    if (SDK_INT >= 24) {
                        photoURI = FileProvider.getUriForFile(CustomMediaPlayer.this,
                                BuildConfig.APPLICATION_ID + ".provider", f);
                    } else {
                        photoURI = Uri.fromFile(f);
                    }
                    str_imgTypeClick = "PhotoCameraRequest";
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    //startActivityForResult(intent, 10000);
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
                    photoURI = FileProvider.getUriForFile(CustomMediaPlayer.this,
                            BuildConfig.APPLICATION_ID + ".provider", f);
                } else {
                    photoURI = Uri.fromFile(f);
                }
                str_imgTypeClick = "PhotoGalleryRequest";
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
              //  startActivityForResult(intent, 20000);

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
                        .start(CustomMediaPlayer.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 20000 && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();

                CropImage.activity(selectedImage)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(CustomMediaPlayer.this);
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
                    s3IU = new s3ImageUploading(Chat_node, Const.AMAZON_S3_BUCKET_NAME_CHAT + Chat_node + "/" + MakeMyExam.userId, CustomMediaPlayer.this, CustomMediaPlayer.this, null);
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

                    s3IU = new s3ImageUploading(Chat_node, Const.AMAZON_S3_BUCKET_NAME_CHAT + Chat_node + "/" + MakeMyExam.userId, CustomMediaPlayer.this, CustomMediaPlayer.this, null);
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
                setupDoc(RealPathUtil.getPath(CustomMediaPlayer.this, data.getData()));
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
            s3IU = new s3ImageUploading(Chat_node, Const.AMAZON_S3_BUCKET_NAME_CHAT, CustomMediaPlayer.this, CustomMediaPlayer.this, null);
            String arr[] = docpath.split("/");
            mediaFile.setFile_name(arr[arr.length - 1]);
            mediaFile.setFile(docpath);
            mediaFile.setFile_type(Const.PDF);
            mediaFileArrayList.add(mediaFile);
            s3IU.execute(mediaFileArrayList);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_MULTIPLE) {
            if (grantResults.length <= 0) {
                AppPermissionsRunTime.checkPermission(this, myPermissionConstantsArrayList, REQUEST_CODE_PERMISSION_MULTIPLE);
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onSeek(Bookmark data) {
        try {
            if (data.getTime() != null) {
                String[] ar = data.getTime().split(":");
                long time = 0;
                if (ar.length == 3) {
                    time = Integer.parseInt(ar[0]) * 60 * 60 + Integer.parseInt(ar[1]) * 60 + Integer.parseInt(ar[2]);

                } else if (ar.length == 2) {
                    time = Integer.parseInt(ar[0]) * 60 * 60 + Integer.parseInt(ar[1]) * 60;

                } else if (ar.length == 1) {
                    time = Integer.parseInt(ar[0]) * 60 * 60;
                }
                if (inErrorState) {
                    Toast.makeText(this, "Unable to play video", Toast.LENGTH_SHORT).show();
                } else {
                    player.seekTo(time * 1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {


            case API.get_video_link:
                EncryptionData videoencryptionData = new EncryptionData();
                videoencryptionData.setName(video_id + "_" + "0" + "_" + "0");
                videoencryptionData.setCourse_id(course_id);
                videoencryptionData.setTile_id(tileid);
                videoencryptionData.setType(tiletype);
                String device_id = (Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                String device_name = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                device_id = device_id == null && device_id.equalsIgnoreCase("") ? "1234567890" : device_id;
                videoencryptionData.setDevice_id(device_id);
                videoencryptionData.setDevice_name(device_name);

                String metavideoencryptionDatadoseStr = new Gson().toJson(videoencryptionData);
                String videodatadoseStrScr = AES.encrypt(metavideoencryptionDatadoseStr);
                return service.getVideoLink(videodatadoseStrScr);

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
                        Metadata metadata = new Gson().fromJson(jsonstring.toString(), Metadata.class);
                        indexdata.addAll(metadata.getData().getIndex());
                        bookmarkdata.addAll(metadata.getData().getBookmark());
                        pdf.addAll(metadata.getData().getPdf());
                        pollarraylist.addAll(metadata.getData().getPoll());
                        Collections.reverse(pollarraylist);
                        int ispdf = 0, isindex = 0, poll = 0;
                        if (islive.equalsIgnoreCase("4") && isaudio.equalsIgnoreCase("0")) {
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
                        if (islive.equalsIgnoreCase("1") && isaudio.equalsIgnoreCase("0")) {
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


            case API.get_video_link:

                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {

                        JSONObject jsonObject = new JSONObject(jsonstring.toString());
                        if (jsonObject.has("data")) {
                            link = jsonObject.getJSONObject("data").getString("link");
                            thumbnailurl = "http://img.youtube.com/vi/" + url + "/0.jpg";

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

                            extractYoutubeUrl(link);
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
            Toast.makeText(this, "Please wait player is not ready", Toast.LENGTH_SHORT).show();
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
        adapter = new Adapter_recycleveiw_vedio(this, indexdata, "custom");
        recyclerChat.setAdapter(adapter);
    }

    private void setbookmarkadapter() {
        bookmarkAdapter = new BookmarkAdapter(this, bookmarkdata, "customplayer");
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
        notesAdapter = new NotesAdapter(this, pdf, course_id);
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


    public void setpollcount(final String pollkey, String answer) {
        mFirebaseDatabaseReferencepolldata = null;
        mFirebaseDatabaseReferencepolldata = FirebaseDatabase.getInstance().getReference().child("chat_master/" + Chat_node + "/" + "Poll/" + pollkey);

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        java.util.Date currenTimeZone = new java.util.Date((long) Long.parseLong(timestamp) * 1000);
        return Helper.changeAMPM(sdf.format(currenTimeZone));
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
        mFirebaseDatabaseReferencepolldata = FirebaseDatabase.getInstance().getReference().child("chat_master/" + Chat_node + "/" + "Poll/" + polldata.getRendomkey());
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

    private int getLiveIndexResolution(int index) {
        switch (index) {
            case 0:
                return 144;
            case 1:
                return 240;
            case 2:
                return 360;
            case 3:
                return 480;
            case 4:
                return 720;
            case 5:
                return 1080;
            case 6:
                return 1440;
        }
        return 144;
    }
}