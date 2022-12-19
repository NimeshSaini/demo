package com.utkarshnew.android.Download.Audio;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;
import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
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
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.crypto.AesCipherDataSource;
import com.utkarshnew.android.Download.Audio.PlayerNotificationManager;
import com.utkarshnew.android.Download.Audio.PlayerNotificationManager.BitmapCallback;
import com.utkarshnew.android.Download.Audio.PlayerNotificationManager.MediaDescriptionAdapter;
import com.utkarshnew.android.Download.Audio.PlayerNotificationManager.NotificationListener;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.pallycon.widevinelibrary.PallyconDrmException;
import com.pallycon.widevinelibrary.PallyconWVMSDK;
import com.pallycon.widevinelibrary.PallyconWVMSDKFactory;
import com.utkarshnew.android.Login.Activity.SplashScreen;
import com.utkarshnew.android.Player.DrmVideoPlayerActivity;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.CookieData;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionHelper;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.SharedPreference;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class AudioPlayerService extends Service {
    private static final String TAG = "AudioPlayerService";
    private static final String NOTIFICATION_CHANNEL = "Utkarsh_Channel_New";

    public static SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private MediaSessionCompat mediaSession;
    MediaSource mediaSource;
    private static String youtubeUri, resLink, audioTitle, audioDesc;
    private Bitmap audioThumbnailBitmap;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private Uri videoUri, audioUri;
    private Uri shareAudioUri;
    private static long playPosition;
    private static int currentVideoSource = 0, audioNotificationId;
    private static boolean playStatus = true;
    private boolean isHlsContent, isDashContent, isLiveStream;
    public static boolean isAudioPlaying;
    public static Long video_currentpos = 0L;
    public static String videoid = "", media_id = "", tileid = "", tiletype = "", siteid = "", sitekey = "", token = "";
    CookieData cookieData;
    String userAgent;
    public static String type = "";
    HlsMediaSource hlsMediaSource;
    ProgressiveMediaSource extractorMediaSource;
    DashMediaSource dashMediaSource;
    private List<String> sparseOPUSAudioUrl;
    private boolean isProgressive, isAudioOnly;
    private static String pausedTimeSpent;
    private Context context;
    private DataSource.Factory mediaDataSourceFactory;


    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }


    private boolean setAudioSeekPosition = false;
    private static String histStartTime = null;
    private String SelectedChapter;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        audioThumbnailBitmap = MakeMyExam.bitmap;
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        initNotificationManager();
    }

    private void releaseAll() {
        try {
            if (playerNotificationManager != null) {
                playerNotificationManager.setPlayer(null);
                playerNotificationManager = null;
            }

            if (player != null) {
                video_currentpos = player.getCurrentPosition();
                player.release();
                player = null;
            }

            if (mediaSession != null) {
                if (mediaSession.isActive())
                    mediaSession.setActive(false);
                mediaSession.release();
                mediaSession = null;
            }

            isAudioPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNotificationManager() {
        try {
            if (context == null)
                context = this;
            releaseAll();

            NotificationUtil.createNotificationChannel(context, NOTIFICATION_CHANNEL, R.string.app_name,
                    R.string.app_name, NotificationUtil.IMPORTANCE_NONE);

            if (siteid != null && !siteid.equalsIgnoreCase("")) {
                mediaDataSourceFactory = buildDataSourceFactory(false);

                String FinalUrl = "";
                PallyconWVMSDK WVMAgent = null;
                Uri uri = null;
                try {
                    WVMAgent = PallyconWVMSDKFactory.getInstance(this);
                    WVMAgent.init(this, null, siteid, sitekey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // TODO : 2.set content information
                UUID drmSchemeUuid = UUID.fromString((C.WIDEVINE_UUID).toString());
                FinalUrl = youtubeUri;
                uri = Uri.parse(FinalUrl);
                String drmLicenseUrl = API.API_REQUEST_VIDEO_LICENSE;

                String userId = "UT_" + SharedPreference.getInstance().getLoggedInUser().getId();
                DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
                DefaultRenderersFactory renderersFactory = null;

                try {

                    drmSessionManager = Objects.requireNonNull(WVMAgent).createDrmSessionManagerByToken(drmSchemeUuid, drmLicenseUrl, uri, userId, "", token, false);
                    renderersFactory = new DefaultRenderersFactory(this, drmSessionManager);

                } catch (PallyconDrmException e) {
                    e.printStackTrace();
                }


                player = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, new DefaultTrackSelector());
                player.setPlayWhenReady(true);

            } else {
                player = ExoPlayerFactory.newSimpleInstance(this, new DefaultRenderersFactory(this),
                        new DefaultTrackSelector(), new DefaultLoadControl());
            }

            playerNotificationManager = new PlayerNotificationManager(
                    context,
                    NOTIFICATION_CHANNEL,
                    audioNotificationID(),
                    new MediaDescriptionAdapter() {
                        @Override
                        public String getCurrentContentTitle(Player player) {
                            return audioTitle;
                        }


                        @Nullable
                        @Override
                        public PendingIntent createCurrentContentIntent(Player player) {
                            Intent intent = new Intent(AudioPlayerService.this, SplashScreen.class);
                            intent.putExtra("url", youtubeUri);
                            intent.putExtra("media_id", media_id);
                            intent.putExtra("type", type);
                            intent.putExtra("videoid", videoid);
                            intent.putExtra("tileid", tileid == null ? "" : tileid);
                            intent.putExtra("tiletype", tiletype == null ? "" : tileid);

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                                return PendingIntent.getActivity(
                                        getApplicationContext(), audioNotificationId, intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                                        PendingIntent.FLAG_MUTABLE);
                            } else {
                                return PendingIntent.getActivity(
                                        getApplicationContext(), audioNotificationId, intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                                        0);
                            }
                        }

                        @Nullable
                        @Override
                        public String getCurrentContentText(Player player) {
                            return audioDesc;
                        }

                        @Nullable
                        @Override
                        public Bitmap getCurrentLargeIcon(Player player, BitmapCallback callback) {
                            return audioThumbnailBitmap;
                        }
                    }, new CustomAudioActionReceiver()
            );

            playerNotificationManager.setNotificationListener(new NotificationListener() {
                @Override
                public void onNotificationStarted(int notificationId, Notification notification) {
                    try {
                        if (Build.VERSION.SDK_INT >= 26) {
                            String CHANNEL_ID = "my_channel_01";
                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                    "Channel human readable title",
                                    NotificationManager.IMPORTANCE_DEFAULT);

                            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                startForeground(notificationId, notification, FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
                            } else {
                                startForeground(notificationId, notification);
                            }
                        }


                        //  startForeground(notificationId, notification);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {

                }

                @Override
                public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
                    stopSelf();
                }

                @Override
                public void onNotificationCancelled(int notificationId) {
                    stopSelf();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        if (playerNotificationManager != null) {
            playerNotificationManager.setPlayer(null);
            playerNotificationManager = null;
            if (player != null) {
                player.release();
                player = null;
            }

        }
        pausedTimeSpent = null;
        histStartTime = null;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null && !intent.getAction().isEmpty()) {
            if (intent.getAction().equalsIgnoreCase("Start_Service")) {
                youtubeUri = intent.getStringExtra("videoUrl");
                audioTitle = intent.getStringExtra("audioTitle");
                videoid = intent.getStringExtra("videoid");

                media_id = intent.getStringExtra("media_id");


                cookieData = (CookieData) intent.getSerializableExtra("cookieData");
                token = intent.getStringExtra("token");
                siteid = intent.getStringExtra("siteid");
                sitekey = intent.getStringExtra("sitekey");

                audioDesc = intent.getStringExtra("audioDesc");
                tileid = intent.getStringExtra("tileid");
                tiletype = intent.getStringExtra("tiletype");
                type = intent.getStringExtra("type");
                if (type == null) {
                    type = "";
                }
                isLiveStream = intent.getBooleanExtra("isLive", false);
                video_currentpos = intent.getLongExtra("player_pos", 0L);

                ////////////for youtube live //////////////
                ////////////for jw///////////////
                if (isLiveStream)
                    isHlsContent = true;


                initPlayer();

                return START_STICKY;
            } else if (intent.getAction().equalsIgnoreCase("Stop_Service") && isAudioPlaying) {

                releaseAll();
                videoid = "";
                media_id = "";
                isAudioPlaying = false;
                stopForeground(true);
                stopSelf(startId);

                return START_NOT_STICKY;
            } else {
                return START_NOT_STICKY;
            }
        } else {
            return START_NOT_STICKY;
        }
    }

    private int audioNotificationID() {
        Date now = new Date();
        audioNotificationId = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.getDefault()).format(now));
        return audioNotificationId;
    }

    private void initPlayer() {
        try {
            if (playerNotificationManager == null) {
                initNotificationManager();
            }

            if (siteid != null && !siteid.equalsIgnoreCase("")) {
                player.addListener(playerEventListener);
                playerNotificationManager.setPlayer(player);
                videoUri = Uri.parse(youtubeUri);

                mediaSource = buildMediaSource(videoUri, null);
                player.prepare(mediaSource);


                playerNotificationManager.setColorized(true);
                playerNotificationManager.setUseChronometer(true);
                playerNotificationManager.setSmallIcon(R.drawable.exo_notification_pause);
                playerNotificationManager.setBadgeIconType(NotificationCompat.BADGE_ICON_NONE);
                playerNotificationManager.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                playerNotificationManager.setUseNavigationActions(false);
                playerNotificationManager.setFastForwardIncrementMs(10000);
                playerNotificationManager.setRewindIncrementMs(10000);
                playerNotificationManager.setPriority(NotificationCompat.PRIORITY_HIGH);

            } else {
                playerNotificationManager.setPlayer(player);
                playerNotificationManager.setColorized(true);
                playerNotificationManager.setUseChronometer(true);
                playerNotificationManager.setSmallIcon(R.drawable.exo_notification_pause);
                playerNotificationManager.setBadgeIconType(NotificationCompat.BADGE_ICON_NONE);
                playerNotificationManager.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                playerNotificationManager.setUseNavigationActions(false);
                playerNotificationManager.setFastForwardIncrementMs(10000);
                playerNotificationManager.setRewindIncrementMs(10000);
                playerNotificationManager.setPriority(NotificationCompat.PRIORITY_HIGH);
                videoUri = Uri.parse(youtubeUri);

                if (!type.equalsIgnoreCase("locale")) {
                    videoUri = Uri.parse(youtubeUri);
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getResources().getString(R.string.app_name)));
                   /* if (isHlsContent) {
                        hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                        player.setPlayWhenReady(true);

                        // Prepare the player with the source.
                        player.prepare(hlsMediaSource);
                    } else {
                        extractorMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                        player.setPlayWhenReady(true);

                        player.prepare(extractorMediaSource);
                    }*/
                    if (isDashContent) {
                        dashMediaSource = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                        player.setPlayWhenReady(true);

                        // Prepare the player with the source.
                        player.prepare(dashMediaSource);
                    } else if (isHlsContent) {
                        if (youtubeUri.contains(".m3u8"))
                        {
                            hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                            player.setPlayWhenReady(true);
                            player.prepare(hlsMediaSource);
                        }else
                        if (youtubeUri.contains(".mp4") || youtubeUri.contains(".m4a")) {
                            extractorMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                            player.setPlayWhenReady(true);
                            player.prepare(extractorMediaSource);
                        } else {
                            hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                            player.setPlayWhenReady(true);
                            player.prepare(hlsMediaSource);
                        }
                    } else {
                        if (!youtubeUri.isEmpty()) {
                            if (youtubeUri.contains("jwplayer") || youtubeUri.contains("jwplatform")) {
                                extractorMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                                player.setPlayWhenReady(true);
                                player.prepare(extractorMediaSource);
                            }
                        }
                    }
                }

             /*   if (isHlsContent) {
                    hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                    player.setPlayWhenReady(true);

                    // Prepare the player with the source.
                    player.prepare(hlsMediaSource);
                } else {
                    extractorMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                    player.setPlayWhenReady(true);

                    // Prepare the player with the source.
                    player.prepare(extractorMediaSource);
                }

                if (isDashContent) {
                    dashMediaSource = new DashMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                    player.setPlayWhenReady(true);

                    // Prepare the player with the source.
                    player.prepare(dashMediaSource);
                } else if (isHlsContent) {
                    hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                    player.setPlayWhenReady(true);

                    // Prepare the player with the source.
                    player.prepare(hlsMediaSource);
                }
                else {
                    if (!youtubeUri.isEmpty()) {
                        if (youtubeUri.contains("jwplayer") || youtubeUri.contains("jwplatform")) {
                            extractorMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
                            player.setPlayWhenReady(true);
                            player.prepare(extractorMediaSource);
                        }
                    }
                }
*/
                player.addListener(playerEventListener);
            }


            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.CONTENT_TYPE_MUSIC)
                    .build();

            player.setAudioAttributes(audioAttributes, true);

       /*     MediaMetadataCompat mediaMetadataCompat = new MediaMetadataCompat.Builder()
                    .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, audioThumbnailBitmap)
                    .build();


            mediaSession = new MediaSessionCompat(AudioPlayerService.this, "EU_AUDIO");
            mediaSession.setActive(true);
            mediaSession.setMetadata(mediaMetadataCompat);
            playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());

            */
            isAudioPlaying = true;

            player.seekTo(video_currentpos);

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
            if (isBehindLiveWindow(e)) {

            } else {
                if (isAudioPlaying) {
                    releaseAll();
                    isAudioPlaying = false;
                    stopForeground(true);
                    stopSelf();
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
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("prince", "End");
        UtkashRoom.destroyInstance();
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(TransferListener listener) {
        DefaultDataSourceFactory dataSourceFactory = null;
        if (!TextUtils.isEmpty(cookieData.getCloudFrontSignature()) && !TextUtils.isEmpty(cookieData.getCloudFrontSignature()) && !TextUtils.isEmpty(cookieData.getCloudFrontKeyPairId())) {
            String cookieValue = "CloudFront-Policy=" + cookieData.getCloudFrontExpires() +
                    ";CloudFront-Signature=" + cookieData.getCloudFrontSignature() +
                    ";CloudFront-Key-Pair-Id=" + cookieData.getCloudFrontKeyPairId();
            userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
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

}

