package com.utkarshnew.android.Download;

import static android.view.Gravity.RIGHT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
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
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.utkarshnew.android.Download.Audio.AudioPlayerService;
import com.utkarshnew.android.Download.Audio.HomeWatcher;
import com.utkarshnew.android.Download.Audio.OnHomePressedListener;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.JWextractor.ControllerAdapter;
import com.utkarshnew.android.JWextractor.JWVideoPlayer;
import com.utkarshnew.android.JWextractor.SelectSpeedo;
import com.utkarshnew.android.JWextractor.SpeedoAdapter;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.Player.Liveawsactivity;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.CookieData;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.JWextractor.InternetConnectionChecker;
import com.utkarshnew.android.JWextractor.NetworkCheckerInterface;
import com.utkarshnew.android.Model.Video;
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
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;


public class AudioPlayerActivty extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, SelectSpeedo {

    private TextView speedTV, video, floatingText_new;
    private boolean mExoPlayerFullscreen = false;
    View rootView;
    ImageView icon;
    PowerMenu powerMenu;
    ImageView exo_ffwd, exo_rew;
    public int speedpostiion = 3;
    int tempspeed = 3;
    SpeedoAdapter speedoAdapter;

    private PopupWindow popupWindow;
    ArrayList<UrlObject> sppedlist = Helper.speedlist();


    boolean playerError = false;

    private FrameLayout mFullScreenButton;
    private static long playPosition;

    String spped_title = "";

    private SimpleExoPlayerView simpleExoPlayerView;
    public SimpleExoPlayer player;
    private DataSource.Factory mediaDataSourceFactory;
    private String userAgent;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    String url = "", image_url = "", videoid = "";
    private ImageView mFullScreenIcon;
    private Long currentpos;
    NetworkCall networkCall;
    private boolean startAutoPlay;
    ArrayList<Video> videoArrayList;
    private CookieData cookieDatas;
    String objectcookie = "";

    private int startWindow;
    private long startPosition;
    private String video_name = "", pos = "", course_id = "", tile_id = "", tile_type = "";
    Uri uri;
    Button retry;
    private Bitmap image;
    private ImageView playBtn, pauseBtn;
    private HomeWatcher homeWatcher;
    private ProgressBar progressBar;
    UtkashRoom utkashRoom;
    RelativeLayout parent_layout;


    private Timer internetSpeedTimer;
    private long internetCheckDelay = 0;
    private final long internetCheckDelayMultiple = 20;
    long mLastClickTime_frame5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_custom_audio_player);
        try {
            utkashRoom = UtkashRoom.getAppDatabase(AudioPlayerActivty.this);
            userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
            mediaDataSourceFactory = buildDataSourceFactory(false);

            if (getIntent().getExtras() != null) {
                image_url = getIntent().getStringExtra("image_url");
                Log.d("princeimage", "" + image_url);
                Thread thread = new Thread(() -> {
                    URL url = null;
                    try {
                        url = new URL(image_url);
                        image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();

                videoid = getIntent().getStringExtra("videoid");
                currentpos = getIntent().getLongExtra("currentpos", 0L);
                url = getIntent().getStringExtra("url");
                video_name = getIntent().getStringExtra("video_name");
                uri = Uri.parse(getIntent().getStringExtra("url"));
                pos = getIntent().getStringExtra("pos");
                course_id = getIntent().getStringExtra("course_id");
                course_id = course_id == null ? "" : course_id;
                tile_id = getIntent().getExtras().getString("tile_id");
                tile_type = getIntent().getExtras().getString("tile_type");

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
            }

            homeWatcher = new HomeWatcher(this);
            homeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
                @Override
                public void onHomePressed() {
                    try {
                        if (player != null) {
                            try {
                                Intent openRelatedRefsIntent = new Intent(AudioPlayerActivty.this, AudioPlayerService.class);

                                openRelatedRefsIntent.putExtra("videoUrl", url);
                                openRelatedRefsIntent.putExtra("isLive", false);
                                openRelatedRefsIntent.putExtra("shopView", false);
                                MakeMyExam.bitmap = image;
                                openRelatedRefsIntent.putExtra("intent", "videos");
                                openRelatedRefsIntent.putExtra("type", "Jw");
                                openRelatedRefsIntent.setAction("Start_Service");
                                openRelatedRefsIntent.putExtra("setAudioSeekPosition", true);
                                try {
                                    openRelatedRefsIntent.putExtra("audioTitle", video_name);
                                } catch (Exception e) {
                                    openRelatedRefsIntent.putExtra("audioTitle", video_name);
                                }
                                openRelatedRefsIntent.putExtra("cookieData", cookieDatas);

                                openRelatedRefsIntent.putExtra("audioOnly", true);
                                openRelatedRefsIntent.putExtra("audioDesc", video_name);
                                openRelatedRefsIntent.putExtra("videoid", videoid);
                                openRelatedRefsIntent.putExtra("player_pos", player.getCurrentPosition());

                                utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, player.getCurrentPosition(), url);

                                Util.startForegroundService(AudioPlayerActivty.this, openRelatedRefsIntent);
                                AudioPlayerService.isAudioPlaying = true;
                                releasePlayer();
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onHomeLongPressed() {
                    Log.d("HomeWatcher", "**************  onHomeLongPressed  ******************");
                }
            });
            networkCall = new NetworkCall(this, this);

            homeWatcher.startWatch();
            retry = findViewById(R.id.retry);
            parent_layout = findViewById(R.id.parent_layout);
            playBtn = findViewById(R.id.exo_play);

            pauseBtn = findViewById(R.id.exo_pause);
            simpleExoPlayerView = findViewById(R.id.player_view_new);
            progressBar = findViewById(R.id.progress_bar);
            video = findViewById(R.id.video_name);
            video.setSelected(true);
            floatingText_new = findViewById(R.id.floatingText_new);
            rootView = findViewById(R.id.root_new);



            PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
            mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);

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


            speedTV = controlView.findViewById(R.id.speedTV);
            ImageView qualityspeed = controlView.findViewById(R.id.quality);
            qualityspeed.setVisibility(View.VISIBLE);
            speedTV.setVisibility(View.GONE);
            icon = controlView.findViewById(R.id.icon);
            icon.setVisibility(View.GONE);

            video.setText(video_name);
            qualityspeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindowPart();
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    Size size=new Size(popupWindow.getContentView().getMeasuredWidth(),popupWindow.getContentView().getHeight());

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        popupWindow.showAtLocation(getWindow().getDecorView(),Gravity.TOP|RIGHT,50,qualityspeed.getBottom());
                    }else {
                        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.TOP|RIGHT,0, (int) ((location[1]+size.getHeight())/2.8));
                    }
                   // popupWindow.showAsDropDown(view, 100, -view.getHeight() - 550, RIGHT);

                }
            });


            floatingText_new.setText(SharedPreference.getInstance().getLoggedInUser().getMobile());
            if (speedTV != null) {
                speedTV.setOnClickListener(v -> showSpeedOptions());
            }



            mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
            mFullScreenButton.setOnClickListener(v -> {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
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


            playBtn.setOnClickListener(v -> {

                if (player != null && player.getPlaybackState() == Player.STATE_READY) {
                    playBtn.setVisibility(View.GONE);
                    pauseBtn.setVisibility(View.VISIBLE);

                    player.setPlayWhenReady(true);
                }
            });
            pauseBtn.setOnClickListener(v -> {

                if (player != null && player.getPlaybackState() == Player.STATE_READY) {
                    playBtn.setVisibility(View.VISIBLE);
                    pauseBtn.setVisibility(View.GONE);
                    player.setPlayWhenReady(false);
                }
            });


            checkNetwork(new NetworkCheckerInterface() {
                @Override
                public void continueExecution() {
                  //  startInternetSpeedTimer();

                    try {
                        if (url != null && !url.equalsIgnoreCase("")) {
                            parent_layout.setVisibility(View.VISIBLE);
                            initializePlayer();
                        } else {
                            showRestartAlertDialog();
                        }

                    } catch (NullPointerException e) {
                    }

                }

                @Override
                public void cancelExecution() {
                    onBackPressed();
                }
            });




            //   initializePlayer();

        } catch (Exception e) {
            e.printStackTrace();
        }


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

        speedoAdapter = new SpeedoAdapter(AudioPlayerActivty.this, sppedlist, this);
        Objects.requireNonNull(speed_recyclerview).setAdapter(speedoAdapter);

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_rectangle_gray));
        popupWindow.setWidth(width - 50);
        popupWindow.showAtLocation(view, RIGHT, 0, 0);
        popupWindow.setContentView(view);


    }




    private final OnMenuItemClickListener<PowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            showSpeedOptions();
            // item.setIsSelected(true);
            powerMenu.dismiss();
        }
    };

    private void networkStatusChecker(boolean networkConnected) {
        if (networkConnected) {
            if (playerError) {
                Snackbar.make(rootView, "Connected with internet, device is online", Snackbar.LENGTH_SHORT).show();
                if (player != null) {
                    player.removeListener(playerEventListener);
                    player.release();
                    playerError = false;
                    initializePlayer();
                    return;
                }
            }
        } else {
            Snackbar.make(rootView, "No internet connection found, device is offline.", Snackbar.LENGTH_SHORT).show();
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

        final androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(AudioPlayerActivty.this);
        dialog.setCancelable(false);
        final androidx.appcompat.app.AlertDialog alertDialog = dialog.create();
        View dialogView = getLayoutInflater().inflate(R.layout.retry_video_dialog, null, false);
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (!((Activity) AudioPlayerActivty.this).isFinishing()) {
            alertDialog.show();
        }

        TextView dialogBodyTxtView = dialogView.findViewById(R.id.body);
        TextView dialogTitleTextView = dialogView.findViewById(R.id.title);
        dialogTitleTextView.setText(video_name);
        dialogBodyTxtView.setText("Video playback has failed. Please wait and try again. Your patience is appreciated.");
        Button retryBtn = dialogView.findViewById(R.id.positiveBtn);
        Button cancelBtn = dialogView.findViewById(R.id.negativeBtn);

        retryBtn.setOnClickListener(view -> {

            if (url == null || url.equalsIgnoreCase("")) {
                Snackbar.make(rootView, "Video Getting issue. Please relaod again", Snackbar.LENGTH_LONG).show();
                onBackPressed();
            } else {
                Snackbar.make(rootView, "Reloading video. Please wait...", Snackbar.LENGTH_LONG).show();
                initializePlayer();
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
                //  showSlowNetworkAlertDialog(cm, downSpeedString, networkCheckerInterface);
                networkCheckerInterface.continueExecution();

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
        dialog = new Dialog(AudioPlayerActivty.this);
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
        dialogBodyTxtView.setText("You are currently connected to: \n" + netInfo.getActiveNetworkInfo().getTypeName() + " Network");
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
                                    String msg = "Your internet connection is unstable";
                                    Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG).show();
                                    resetInternetSpeedTimer();
                                }
                            }

                            @Override
                            public void continueExecution() {
                                if (!isFinishing() && !isDestroyed()) {
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
     //   startInternetSpeedTimer();
    }


    private void initializePlayer() {
        if (isFinishing() || isDestroyed())
            return;
        showProgressBar();
        MediaSource mediaSource = buildMediaSource(uri, mediaDataSourceFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());
        player.addListener(playerEventListener);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.prepare(mediaSource);
        updateButtonVisibility();
        player.seekTo(currentpos);
        try {
            UserHistroyTable userHistroyTable = new UserHistroyTable();
            userHistroyTable.setVideo_id(videoid);
            userHistroyTable.setVideo_name(video_name);
            userHistroyTable.setType("audio");
            userHistroyTable.setUser_id(MakeMyExam.userId);
            userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
            userHistroyTable.setCourse_id(course_id);
            userHistroyTable.setTileid(tile_id);
            userHistroyTable.setTopicname(tile_type);
            userHistroyTable.setUrl(image_url);
            utkashRoom.getuserhistorydao().addUser(userHistroyTable);
        } catch (Exception e) {
            hideProgressBar();
            e.printStackTrace();
        }

    }


    private void openFullscreenDialog() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreenexit));
        mExoPlayerFullscreen = true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            if (newConfig.orientation == 1) {
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
                    pixels = (int) (220 * scale + 0.5f);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels);
                rootView.setLayoutParams(layoutParams);

            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                rootView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return simpleExoPlayerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    private void closeFullscreenDialog() {

        mExoPlayerFullscreen = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreen));
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
                        spped_title = title;
                        // speedTV.setText(title);
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



     /*   @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            updateButtonVisibility();

            switch (playbackState) {
                case Player.STATE_ENDED:
                    player.setPlayWhenReady(false);
                    break;
                case Player.STATE_READY:
                    hideProgressBar();
                    break;
                case Player.STATE_BUFFERING:
                    showProgressBar();
                    break;
                case Player.STATE_IDLE:

                    break;
            }
        }*/

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            updateButtonVisibility();
            if (playbackState == Player.STATE_READY) {
                hideProgressBar();
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
            hideProgressBar();
            if (isBehindLiveWindow(error)) {
                clearStartPosition();
            } else {
                updateButtonVisibility();
                if (!Helper.isNetworkConnected(AudioPlayerActivty.this)) {
                    if ((player.getCurrentPosition() != player.getDuration()) && (player.getCurrentPosition() != 0)) {
                        utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, player.getCurrentPosition(), url);
                    }
                    if (player != null)
                        playPosition = player.getCurrentPosition();
                    playerError = true;
                    Snackbar.make(rootView, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();

                } else if (error.getSourceException() instanceof HttpDataSource.HttpDataSourceException) {
                    if (!Helper.isNetworkConnected(AudioPlayerActivty.this)) {
                        if ((player.getCurrentPosition() != player.getDuration()) && (player.getCurrentPosition() != 0)) {
                            utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, player.getCurrentPosition(), url);
                        }

                        playPosition = player.getCurrentPosition();
                        Snackbar.make(rootView, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();
                        playerError = true;
                    } else {
                        //Toast.makeText(AudioPlayerActivty.this, "Error While Streaming Video, Trying Again...", Toast.LENGTH_SHORT).show();
                      /*  if (player != null) {
                            player.removeListener(playerEventListener);
                            player.release();
                            player = null;
                            initializePlayer();
                        }*/
                        Snackbar.make(rootView, "Video biffering issues. Please click on retry button", Snackbar.LENGTH_LONG).show();
                        if (player != null)
                            playPosition = player.getCurrentPosition();
                        playerError = true;
                        retry.setVisibility(View.VISIBLE);
                    }
                } else if (error.getMessage().contains("302")) {
                    if (player != null)
                        playPosition = player.getCurrentPosition();
                    playerError = true;
                    Snackbar.make(rootView, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();
                } else if (error.getMessage().contains("IllegalState")) {
                    Snackbar.make(rootView, "Error While Streaming Video, Please wait and we are trying again. Your patience is appreciated.", Snackbar.LENGTH_LONG).show();
                } else if (error.getSourceException() instanceof HlsPlaylistTracker.PlaylistStuckException) {
                    Snackbar.make(rootView, "No Internet. Please connect device to internet", Snackbar.LENGTH_LONG).show();
                }
            }
        }

        protected void clearStartPosition() {
            startAutoPlay = true;
            startWindow = C.INDEX_UNSET;
            startPosition = C.TIME_UNSET;
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


    private void showProgressBar() {
        if (progressBar != null) {
            if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);

            }
        }
        updateButtonVisibility();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        if (player != null) {
            playerError = true;
            playPosition = player.getCurrentPosition();
            player.removeListener(playerEventListener);
            utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, playPosition, url);
            player.release();
            player = null;
        }



        super.onPause();
    }

    private MediaSource buildMediaSource(Uri uri, DataSource.Factory factory) {
        int type = Util.inferContentType(uri.getLastPathSegment());
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(factory)
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(factory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ProgressiveMediaSource.Factory(factory)
                        .createMediaSource(uri);

            case C.TYPE_SS:
                return new SsMediaSource.Factory(factory).createMediaSource(uri);


            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }


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

    public HttpDataSource.Factory buildHttpDataSourceFactory(TransferListener listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (playerError) {
            playerError = false;
            if (!url.equalsIgnoreCase("")) {
                initializePlayer();
            } else {
                showRestartAlertDialog();
            }
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.removeListener(playerEventListener);
            player.release();
            player = null;
        }
    }


    private void cancelInternetSpeedTimer() {
        if (internetSpeedTimer != null) {
            internetSpeedTimer.cancel();
            internetSpeedTimer = null;
        }
    }


    public void releasePlayer() {
        if (player != null) {
            player.stop();
            if (homeWatcher != null)
                homeWatcher.stopWatch();

            updateStartPosition();

            player.clearVideoSurface();
            player.removeListener(playerEventListener);
            simpleExoPlayerView.getPlayer().release();
            player.release();
            player.release();
            player = null;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.pos = "1";

        cancelInternetSpeedTimer();
        releasePlayer();
    }

    private void updateButtonVisibility() {
        if (player != null) {
            if (image != null) {
                Drawable drawable = new BitmapDrawable(getResources(), image);
                simpleExoPlayerView.setDefaultArtwork(drawable);
            }

        }

    }

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


    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }


    @Override
    public void onBackPressed() {
        int orientation1 = getResources().getConfiguration().orientation;
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fullscreen));
        } else {
            if (player != null && !playerError) {
                background_play();
            } else {

                AudioPlayerService.isAudioPlaying = false;
                AudioPlayerService.videoid = "";
                AudioPlayerService.media_id = "";
                AudioPlayerService.video_currentpos = 0L;
                MakeMyExam.bitmap = null;
                if (player != null)
                    utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, player.getCurrentPosition(), url);
                releasePlayer();
                super.onBackPressed();
            }

        }
    }

    private void background_play() {
        Context context = AudioPlayerActivty.this;
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
                    utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, player.getCurrentPosition(), url);
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
                    Intent openRelatedRefsIntent = new Intent(AudioPlayerActivty.this, AudioPlayerService.class);
                    openRelatedRefsIntent.putExtra("videoUrl", url);
                    openRelatedRefsIntent.putExtra("isLive", true);
                    openRelatedRefsIntent.putExtra("shopView", false);
                    openRelatedRefsIntent.putExtra("type", "Jw");

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
                    openRelatedRefsIntent.putExtra("videoid", videoid);
                    openRelatedRefsIntent.putExtra("player_pos", player.getCurrentPosition());
                    utkashRoom.getaudiodao().update_videotime(videoid, MakeMyExam.userId, player.getCurrentPosition(), url);
                    Util.startForegroundService(AudioPlayerActivty.this, openRelatedRefsIntent);
                    AudioPlayerService.isAudioPlaying = true;

                    releasePlayer();
                    finish();
                    dialog.cancel();


                    /*if (player != null){
                        utkashRoom.getvideoDownloadao().update_videotime(videoid, MakeMyExam.userId,player.getCurrentPosition(),url);
                    }
                    Intent openRelatedRefsIntent = new Intent(AudiPlayerActivty.this, AudioPlayerService_new.class);
                    openRelatedRefsIntent.putExtra("videoUrl", getIntent().getStringExtra("url"));
                    openRelatedRefsIntent.putExtra("isLive", false);
                    openRelatedRefsIntent.putExtra("shopView", false);
                    openRelatedRefsIntent.putExtra("intent", "videos");
                    openRelatedRefsIntent.putExtra("image", image);
                    openRelatedRefsIntent.setAction("Start_Service");
                    openRelatedRefsIntent.putExtra("setAudioSeekPosition", true);
                    try {
                        openRelatedRefsIntent.putExtra("audioTitle",video_name);
                    } catch (Exception e) {
                        openRelatedRefsIntent.putExtra("audioTitle",video_name);
                    }
                    openRelatedRefsIntent.putExtra("audioOnly", true);
                    openRelatedRefsIntent.putExtra("player_pos", player.getCurrentPosition());
                    Util.startForegroundService(AudiPlayerActivty.this, openRelatedRefsIntent);

                    utkashRoom =null;
                    AudioPlayerService_new.isAudioPlaying = true;
                    releasePlayer();
                    finish();
                    dialog.cancel();*/
                });
        builder.create().show();
    }

    public void stopAudio() {
        if (AudioPlayerService.isAudioPlaying) {
            Intent audioPlayerIntent = new Intent(AudioPlayerActivty.this, AudioPlayerService.class);
            audioPlayerIntent.setAction("Stop_Service");
            Util.startForegroundService(AudioPlayerActivty.this, audioPlayerIntent);

        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_video_link:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setName(videoid + "_" + "0" + "_" + "0");
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
                String data1 = new Gson().toJson(encryptionData);
                String encryptjson = AES.encrypt(data1);
                return service.getVideoLink(encryptjson);
        }
        return null;


    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.get_video_link:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        JSONObject dataJsonObject = jsonstring.getJSONObject(Const.DATA);
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

                        url = dataJsonObject.optString("audio_url");
                        uri = Uri.parse(url);
                        initializePlayer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }


    @Override
    public void selectSpeedPos(int pos) {
        speedpostiion = pos;
        if (popupWindow != null && popupWindow.isShowing()) {
            new Handler().postDelayed(() -> popupWindow.dismiss(), 250);
        }
        for (int i = 0; i < sppedlist.size(); i++) {
            if (i == speedpostiion) {
                sppedlist.get(speedpostiion).setSelected(true);
            }
        }
        speedoAdapter.notifyItemChanged(pos);
        if (sppedlist.get(speedpostiion).getTitle().equalsIgnoreCase("Normal")) {
            player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat("1"), 1f));
        } else {
            player.setPlaybackParameters(new PlaybackParameters(Float.parseFloat(sppedlist.get(speedpostiion).getTitle().replace("x", "")), 1f));
        }
        if (sppedlist.get(speedpostiion).getTitle().equalsIgnoreCase("3x"))
        {
            Toast.makeText(this, " if internet speed is not proper then video may buffer", Toast.LENGTH_SHORT).show();
        }
    }



}

