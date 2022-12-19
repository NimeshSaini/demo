package com.utkarshnew.android.Player;


import static android.view.Gravity.RIGHT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.naveed.ytextractor.ExtractorException;
import com.naveed.ytextractor.YoutubeStreamExtractor;
import com.naveed.ytextractor.model.YoutubeMedia;
import com.naveed.ytextractor.model.YoutubeMeta;
import com.naveed.ytextractor.utils.LogUtils;
import com.utkarshnew.android.Download.AudioPlayerActivty;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.JWextractor.SelectSpeedo;
import com.utkarshnew.android.JWextractor.SpeedoAdapter;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionDialog;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionHelper;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class CustommediaPlayerDialog extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, SelectSpeedo {


    private SimpleExoPlayerView simpleExoPlayerView;
    public SimpleExoPlayer player;
    ProgressBar progressBar;

    private List<Integer> sparseKeyList, sparseAdaptiveResolutionList, sparseMuxedResolutionList;
    private HashMap<Integer, String> sparseAdaptiveVideoUrlList, sparseMuxedVideoUrlList;
    private List<String> sparseOPUSAudioUrl;
    private String userAgent;
    private DataSource.Factory mediaDataSourceFactory;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    public  DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    MediaSource mediaSource;
    ImageView qualityspeed, cross,qualitysection;
    private boolean isShowingTrackSelectionDialog;
    Context context;
    public int speedpostiion = 3;
    SpeedoAdapter speedoAdapter;
    String data[];
    ArrayList<UrlObject> sppedlist = Helper.speedlist();

    String url, id = "";
    private static long playPosition;
    PopupWindow popupWindow;
    PositionListener positionListener;
    ProgressTracker progressTracker;

    long startTimeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ((Build.VERSION.SDK_INT != Build.VERSION_CODES.O && Build.VERSION.SDK_INT != Build.VERSION_CODES.O_MR1)
                && (Build.VERSION.SDK_INT != Build.VERSION_CODES.M)) {
            setTheme(R.style.AppTheme_Dialog);
        } else {
            setTheme(R.style.Opaque);
        }
        super.onCreate(savedInstanceState);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1200);
        /*if (Build.VERSION.SDK_INT != Build.VERSION_CODES.M) {
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }*/
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.setFinishOnTouchOutside(false);
        context =this;
        setContentView(R.layout.activity_custommedia_player_dialog);

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view_new);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        cross = findViewById(R.id.cross);
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
        mediaDataSourceFactory = buildDataSourceFactory(false);

        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();

        if (getIntent() != null) {
            url = getIntent().getStringExtra("videoUrl");
            id = getIntent().getStringExtra("id");
        }


      /*  if (url.contains("jwplatform")) {
            data = url.split("#");
            NetworkCall networkCall = new NetworkCall(this, this);
            networkCall.NetworkAPICall(API.get_sign_test_link, "", true, false);

        } else {
            data = url.split("#");
            if (data.length > 0 && data[0] != null) {
                extractYoutubeUrl(data[0]);
            } else {
                extractYoutubeUrl(url);
            }

        }*/


        initFullscreenButton();
        cross.setOnClickListener(new OnSingleClickListener(() -> {
            youtubeUri = "";
            if (progressTracker != null) {
                progressTracker.purgeHandler();
            }

            if (player != null) {
                player.release();
                player = null;
            }
            simpleExoPlayerView = null;
            finish();
            return null;
        }));

        qualityspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowPart();
                popupWindow.showAsDropDown(view, 100, -view.getHeight() - 550, RIGHT);

            }
        });



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

        speedoAdapter = new SpeedoAdapter(CustommediaPlayerDialog.this, sppedlist, this);
        Objects.requireNonNull(speed_recyclerview).setAdapter(speedoAdapter);

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_rectangle_gray));
        popupWindow.setWidth(width - 50);
        popupWindow.showAtLocation(view, RIGHT, 0, 0);
        popupWindow.setContentView(view);


    }

    
    

    int lastseleted = -1;


    private ImageView mFullScreenIcon;
    private TextView speedTV, exo_position, exo_duration;
    private FrameLayout mFullScreenButton;
    private DefaultTimeBar exo_progress;
    private ImageView exo_rew, exo_ffwd;


    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    private void initFullscreenButton() {
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        trackSelector.setParameters(trackSelectorParameters);
        speedTV = controlView.findViewById(R.id.speedTV);
        qualityspeed = controlView.findViewById(R.id.quality);
        qualitysection = controlView.findViewById(R.id.qualitysection);

        exo_progress = controlView.findViewById(R.id.exo_progress);
        exo_position = controlView.findViewById(R.id.exo_position);
        exo_duration = controlView.findViewById(R.id.exo_duration);



        if (url.contains(".m3u8")) {
            qualitysection.setVisibility(View.GONE);
            data = url.split("#");
            progressBar.setVisibility(View.GONE);
            youtubeUri = data[0];
            Loadyoutubeurl(youtubeUri);
        } else if (url.contains(".mp4")) {
            qualitysection.setVisibility(View.GONE);
            data = url.split("#");
            progressBar.setVisibility(View.GONE);
            youtubeUri = data[0];
            Loadyoutubeurl(youtubeUri);
        } else {
            Toast.makeText(context, "no url found", Toast.LENGTH_SHORT).show();
        }

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

        // exo_rew = controlView.findViewById(R.id.exo_rew);
        //exo_ffwd = controlView.findViewById(R.id.exo_ffwd);

        if (exo_progress != null) {
            exo_progress.setVisibility(View.INVISIBLE);
            exo_position.setVisibility(View.INVISIBLE);
            exo_duration.setVisibility(View.INVISIBLE);
           /* exo_rew.setVisibility(View.GONE);
            exo_ffwd.setVisibility(View.GONE);*/
        }


        if (speedTV != null) {
            speedTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSpeedOptions();
                }
            });
        }

        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setVisibility(View.GONE);
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

    private static String youtubeUri;


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

    public void Loadyoutubeurl(String youtubeUri) {
        try {
            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
            trackSelector.setParameters(trackSelector
                    .buildUponParameters()
                    .setMaxVideoSize(256, 144)
                    .setForceHighestSupportedBitrate(true));
            trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);

            Uri videouri = Uri.parse(youtubeUri);
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            player.addListener(playerEventListener);
            simpleExoPlayerView.setPlayer(player);
            player.setPlayWhenReady(false);
            mediaSource = buildMediaSource(videouri, null);
            player.prepare(mediaSource);


            try {
                //Use start time if it is not null or empty or undefined
                // else start from start
                if (data[1] != null && !data[1].isEmpty() && !data[1].equalsIgnoreCase("undefined")) {
                    long startPos;
                    if (playPosition == 0) {
//                            startPos = Long.parseLong(data[1])*1000;
                        startPos = TimeUnit.SECONDS.toMillis(getSeconds(data[1]));
                    } else {
                        startPos = playPosition;
                    }
                    player.seekTo(startPos);
                    startTimeMillis = startPos;
                } else {
                    player.seekTo(0);
                }
            } catch (Exception e) {

                return;
            }
            try {
                //Use end time if it is not null or empty or undefined
                // else play till the end
                if (data[2] != null && !data[2].isEmpty() && !data[2].equalsIgnoreCase("undefined")) {
//                        long endPos = Long.parseLong(data[2])*1000;
                    long endPos = TimeUnit.SECONDS.toMillis(getSeconds(data[2]));
                    positionListener = new PositionListener(player, endPos, context);
                    progressTracker = new ProgressTracker(player, positionListener);
                    progressTracker.run();
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        }


        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {

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
            if (player != null) {
                long currentseekPosition = player.getCurrentPosition();
                if (currentseekPosition < startTimeMillis) {
                    player.seekTo(startTimeMillis);
                }
            }
        }
    };

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_sign_test_link:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setQuestion_id(id);
                encryptionData.setUrl(url);

                String data1 = new Gson().toJson(encryptionData);
                String encryptjson = AES.encrypt(data1);
                return service.getvideo_test_link(encryptjson);

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        switch (apitype) {
            case API.get_sign_test_link:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        JSONObject jsonObject = new JSONObject(jsonstring.toString());
                        if (jsonObject.has("data")) {
                            String link = jsonObject.getJSONObject("data").getString("hls");
                            progressBar.setVisibility(View.GONE);
                            sparseAdaptiveVideoUrlList = new HashMap<>();
                            youtubeUri = link;
                            Loadyoutubeurl(youtubeUri);

                        }

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



    public class ProgressTracker implements Runnable {

        private final Player player;
        private final Handler handler;
        private CustommediaPlayerDialog.PositionListener positionListener;
        private final static int DELAY_MS = 1;

        protected ProgressTracker(Player player, CustommediaPlayerDialog.PositionListener positionListener) {
            this.player = player;
            this.positionListener = positionListener;
            handler = new Handler();
            handler.post(this);
        }

        public void run() {
            long position = player.getCurrentPosition();
            positionListener.progress(position);
            handler.postDelayed(this, DELAY_MS);
        }

        protected void purgeHandler() {
            handler.removeCallbacks(this);
        }

    }


    public class PositionListener {
        SimpleExoPlayer simpleExoPlayer;
        long videoEndTimeMillis;

        public PositionListener(SimpleExoPlayer player, long endTimeMillis, Context context) {
            simpleExoPlayer = player;
            videoEndTimeMillis = endTimeMillis;
        }

        private void progress(long position) {
            if (videoEndTimeMillis <= position) {
                if (player != null) {
                    youtubeUri = "";
                    if (player != null) {
                        player.release();
                        player = null;
                        if (progressTracker != null) {
                            progressTracker.purgeHandler();
                        }
                    }
                    simpleExoPlayerView = null;
                    finish();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        if (player != null) {
            if (progressTracker != null) {
                progressTracker.purgeHandler();
            }
            playPosition = player.getCurrentPosition();
            player.removeListener(playerEventListener);
            player.release();
            player = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressTracker != null) {
            progressTracker.purgeHandler();
        }
        playPosition = 0;
        if (player != null) {
            player.removeListener(playerEventListener);
            player.release();
            simpleExoPlayerView.setPlayer(null);
            player = null;
        }
    }


    private long getSeconds(@NotNull String time) {
        if (time.contains(":")) {
            //Consider given time format as mm:ss
            long mins = Integer.parseInt(time.substring(0, time.indexOf(":")).trim());
            long secs = Integer.parseInt(time.substring(time.indexOf(":") + 1).trim());
            return (mins * 60L) + secs;
        } else {
            //Consider given time is in seconds (ss)
            return Integer.parseInt(time);
        }
    }


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

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

}