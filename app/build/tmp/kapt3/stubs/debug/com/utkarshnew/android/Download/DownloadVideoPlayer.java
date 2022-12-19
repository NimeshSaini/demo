package com.utkarshnew.android.Download;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0086\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 \u009c\u00012\u00020\u00012\u00020\u0002:\u0002\u009c\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010y\u001a\u00020zH\u0002J\u0006\u0010{\u001a\u00020zJ\u0010\u0010|\u001a\u00020\u001e2\b\u0010}\u001a\u0004\u0018\u00010~J\u0011\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010}\u001a\u0004\u0018\u00010~J\u001c\u0010\u0081\u0001\u001a\u00020 2\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0007\u0010\u0084\u0001\u001a\u00020\u001eH\u0002J\t\u0010\u0085\u0001\u001a\u00020zH\u0002J\t\u0010\u0086\u0001\u001a\u00020zH\u0002J\n\u0010\u0087\u0001\u001a\u0005\u0018\u00010\u0088\u0001J\u0012\u0010\u0089\u0001\u001a\u00020\u00182\u0007\u0010\u008a\u0001\u001a\u00020=H\u0002J\u0007\u0010\u008b\u0001\u001a\u00020zJ\t\u0010\u008c\u0001\u001a\u00020zH\u0016J\u0013\u0010\u008d\u0001\u001a\u00020z2\b\u0010\u008e\u0001\u001a\u00030\u008f\u0001H\u0016J\u0015\u0010\u0090\u0001\u001a\u00020z2\n\u0010\u0091\u0001\u001a\u0005\u0018\u00010\u0092\u0001H\u0014J\t\u0010\u0093\u0001\u001a\u00020zH\u0014J\t\u0010\u0094\u0001\u001a\u00020zH\u0014J\t\u0010\u0095\u0001\u001a\u00020zH\u0014J\t\u0010\u0096\u0001\u001a\u00020zH\u0002J\t\u0010\u0097\u0001\u001a\u00020zH\u0003J\u0007\u0010\u0098\u0001\u001a\u00020zJ\u0012\u0010\u0099\u0001\u001a\u00020z2\u0007\u0010\u009a\u0001\u001a\u00020=H\u0016J\t\u0010\u009b\u0001\u001a\u00020zH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\'0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010(\u001a\u0004\u0018\u00010)X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020/X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u00100\u001a\u0004\u0018\u000101X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0010\u00106\u001a\u0004\u0018\u000107X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00108\u001a\u0004\u0018\u000109X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010:\u001a\u0004\u0018\u00010;X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020=X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010>\u001a\u0004\u0018\u00010?X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010@\u001a\n\u0012\u0004\u0012\u00020=\u0018\u00010AX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010B\u001a\u0010\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\t\u0018\u00010CX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010D\u001a\n\u0012\u0004\u0012\u00020=\u0018\u00010AX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010E\u001a\n\u0012\u0004\u0012\u00020=\u0018\u00010AX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010F\u001a\u0010\u0012\u0004\u0012\u00020=\u0012\u0004\u0012\u00020\t\u0018\u00010CX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010G\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010AX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010H\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010I\u001a\u0004\u0018\u00010JX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u001a\u0010O\u001a\u00020=X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001a\u0010T\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR5\u0010Y\u001a&\u0012\f\u0012\n \\*\u0004\u0018\u00010[0[ \\*\u0012\u0012\f\u0012\n \\*\u0004\u0018\u00010[0[\u0018\u00010Z0Z\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010^R\u001a\u0010_\u001a\u00020=X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b`\u0010Q\"\u0004\ba\u0010SR\u0010\u0010b\u001a\u0004\u0018\u00010cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010d\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010e\u001a\u0004\u0018\u00010fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bg\u0010h\"\u0004\bi\u0010jR\u0010\u0010k\u001a\u0004\u0018\u00010lX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010m\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bn\u0010V\"\u0004\bo\u0010XR\u0010\u0010p\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010q\u001a\u0004\u0018\u00010rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010s\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010t\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010u\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010v\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010w\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010x\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u009d\u0001"}, d2 = {"Lcom/utkarshnew/android/Download/DownloadVideoPlayer;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/utkarshnew/android/JWextractor/SelectSpeedo;", "()V", "BANDWIDTH_METER", "Lcom/google/android/exoplayer2/upstream/DefaultBandwidthMeter;", "MarqueeText", "Landroid/widget/TextView;", "course_id", "", "current_pos", "", "Ljava/lang/Long;", "exo_ffwd", "Landroid/widget/ImageView;", "getExo_ffwd", "()Landroid/widget/ImageView;", "setExo_ffwd", "(Landroid/widget/ImageView;)V", "exo_rew", "getExo_rew", "setExo_rew", "icon", "isShowingTrackSelectionDialog", "", "mExoPlayerFullscreen", "mFullScreenButton", "Landroid/widget/FrameLayout;", "mFullScreenIcon", "mediaDataSourceFactory", "Lcom/google/android/exoplayer2/upstream/DataSource$Factory;", "mediaSource", "Lcom/google/android/exoplayer2/source/MediaSource;", "getMediaSource", "()Lcom/google/android/exoplayer2/source/MediaSource;", "setMediaSource", "(Lcom/google/android/exoplayer2/source/MediaSource;)V", "onIconMenuItemClickListener", "Lcom/skydoves/powermenu/OnMenuItemClickListener;", "Lcom/skydoves/powermenu/PowerMenuItem;", "player", "Lcom/google/android/exoplayer2/SimpleExoPlayer;", "getPlayer", "()Lcom/google/android/exoplayer2/SimpleExoPlayer;", "setPlayer", "(Lcom/google/android/exoplayer2/SimpleExoPlayer;)V", "playerEventListener", "Lcom/google/android/exoplayer2/Player$EventListener;", "popupWindow", "Landroid/widget/PopupWindow;", "getPopupWindow", "()Landroid/widget/PopupWindow;", "setPopupWindow", "(Landroid/widget/PopupWindow;)V", "powerMenu", "Lcom/skydoves/powermenu/PowerMenu;", "progressBar", "Landroid/widget/ProgressBar;", "root_new", "Landroid/widget/RelativeLayout;", "savedOrientation", "", "simpleExoPlayerView", "Lcom/google/android/exoplayer2/ui/SimpleExoPlayerView;", "sparseAdaptiveResolutionList", "", "sparseAdaptiveVideoUrlList", "Ljava/util/HashMap;", "sparseKeyList", "sparseMuxedResolutionList", "sparseMuxedVideoUrlList", "sparseOPUSAudioUrl", "speedTV", "speedoAdapter", "Lcom/utkarshnew/android/JWextractor/SpeedoAdapter;", "getSpeedoAdapter", "()Lcom/utkarshnew/android/JWextractor/SpeedoAdapter;", "setSpeedoAdapter", "(Lcom/utkarshnew/android/JWextractor/SpeedoAdapter;)V", "speedpostiion", "getSpeedpostiion", "()I", "setSpeedpostiion", "(I)V", "speedx", "getSpeedx", "()Ljava/lang/String;", "setSpeedx", "(Ljava/lang/String;)V", "sppedlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Model/UrlObject;", "kotlin.jvm.PlatformType", "getSppedlist", "()Ljava/util/ArrayList;", "tempspeed", "getTempspeed", "setTempspeed", "thread", "Ljava/lang/Thread;", "token", "trackSelector", "Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector;", "getTrackSelector", "()Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector;", "setTrackSelector", "(Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector;)V", "trackSelectorParameters", "Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector$Parameters;", "url", "getUrl", "setUrl", "userAgent", "utkashRoom", "Lcom/utkarshnew/android/Room/UtkashRoom;", "valid_to", "video_id", "video_name", "video_name_txt", "video_play", "video_time", "LoadVideoEncryoted", "", "Loadyoutubeurl", "buildDataSourceFactory", "listener", "Lcom/google/android/exoplayer2/upstream/TransferListener;", "buildHttpDataSourceFactory", "Lcom/google/android/exoplayer2/upstream/HttpDataSource$Factory;", "buildMediaSource", "uri", "Landroid/net/Uri;", "factory", "closeFullscreenDialog", "earphone", "getCipher", "Ljavax/crypto/Cipher;", "isPortrait", "orientation", "limmedia", "onBackPressed", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "openFullscreenDialog", "popupWindowPart", "releasePlayer", "selectSpeedPos", "pos", "showSpeedOptions", "Companion", "app_debug"})
public final class DownloadVideoPlayer extends androidx.appcompat.app.AppCompatActivity implements com.utkarshnew.android.JWextractor.SelectSpeedo {
    private android.widget.TextView speedTV;
    private android.widget.ImageView icon;
    private com.skydoves.powermenu.PowerMenu powerMenu;
    private boolean mExoPlayerFullscreen = false;
    private final com.google.android.exoplayer2.upstream.DefaultBandwidthMeter BANDWIDTH_METER = null;
    private int speedpostiion = 3;
    private int tempspeed = 3;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView exo_ffwd;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView exo_rew;
    private final java.util.ArrayList<com.utkarshnew.android.Model.UrlObject> sppedlist = null;
    @org.jetbrains.annotations.Nullable()
    private android.widget.PopupWindow popupWindow;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.JWextractor.SpeedoAdapter speedoAdapter;
    private android.widget.FrameLayout mFullScreenButton;
    private com.google.android.exoplayer2.ui.SimpleExoPlayerView simpleExoPlayerView;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.SimpleExoPlayer player;
    private final com.google.android.exoplayer2.upstream.DataSource.Factory mediaDataSourceFactory = null;
    private java.lang.String userAgent;
    private final com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters trackSelectorParameters = null;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.source.MediaSource mediaSource;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.trackselection.DefaultTrackSelector trackSelector;
    private boolean isShowingTrackSelectionDialog = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String url = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String speedx = "";
    private android.widget.ImageView mFullScreenIcon;
    private android.widget.TextView video_name_txt;
    private android.widget.TextView MarqueeText;
    private final java.util.List<java.lang.Integer> sparseKeyList = null;
    private final java.util.List<java.lang.Integer> sparseAdaptiveResolutionList = null;
    private final java.util.List<java.lang.Integer> sparseMuxedResolutionList = null;
    private final java.util.HashMap<java.lang.Integer, java.lang.String> sparseAdaptiveVideoUrlList = null;
    private final java.util.HashMap<java.lang.Integer, java.lang.String> sparseMuxedVideoUrlList = null;
    private final java.util.List<java.lang.String> sparseOPUSAudioUrl = null;
    private java.lang.String video_name;
    private java.lang.String video_id = "";
    private java.lang.String video_play = "";
    private java.lang.String video_time;
    private java.lang.String course_id = "";
    private java.lang.String token = "";
    private java.lang.String valid_to = "";
    private android.widget.RelativeLayout root_new;
    private android.widget.ProgressBar progressBar;
    private com.utkarshnew.android.Room.UtkashRoom utkashRoom;
    private java.lang.Long current_pos;
    private final java.lang.Thread thread = null;
    private int savedOrientation = 0;
    private final com.skydoves.powermenu.OnMenuItemClickListener<com.skydoves.powermenu.PowerMenuItem> onIconMenuItemClickListener = null;
    private final com.google.android.exoplayer2.Player.EventListener playerEventListener = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.utkarshnew.android.Download.DownloadVideoPlayer.Companion Companion = null;
    private static final java.lang.String LOCAL_ENCRYPTION_KEY = "abcdefgh123456yz";
    
    public DownloadVideoPlayer() {
        super();
    }
    
    public final int getSpeedpostiion() {
        return 0;
    }
    
    public final void setSpeedpostiion(int p0) {
    }
    
    public final int getTempspeed() {
        return 0;
    }
    
    public final void setTempspeed(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ImageView getExo_ffwd() {
        return null;
    }
    
    public final void setExo_ffwd(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ImageView getExo_rew() {
        return null;
    }
    
    public final void setExo_rew(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView p0) {
    }
    
    public final java.util.ArrayList<com.utkarshnew.android.Model.UrlObject> getSppedlist() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.PopupWindow getPopupWindow() {
        return null;
    }
    
    public final void setPopupWindow(@org.jetbrains.annotations.Nullable()
    android.widget.PopupWindow p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.JWextractor.SpeedoAdapter getSpeedoAdapter() {
        return null;
    }
    
    public final void setSpeedoAdapter(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.JWextractor.SpeedoAdapter p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.SimpleExoPlayer getPlayer() {
        return null;
    }
    
    public final void setPlayer(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.SimpleExoPlayer p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.source.MediaSource getMediaSource() {
        return null;
    }
    
    public final void setMediaSource(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.source.MediaSource p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.trackselection.DefaultTrackSelector getTrackSelector() {
        return null;
    }
    
    public final void setTrackSelector(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.trackselection.DefaultTrackSelector p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrl() {
        return null;
    }
    
    public final void setUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSpeedx() {
        return null;
    }
    
    public final void setSpeedx(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n", "UseCompatLoadingForDrawables"})
    private final void popupWindowPart() {
    }
    
    private final void openFullscreenDialog() {
    }
    
    private final boolean isPortrait(int orientation) {
        return false;
    }
    
    @java.lang.Override()
    public void onConfigurationChanged(@org.jetbrains.annotations.NotNull()
    android.content.res.Configuration newConfig) {
    }
    
    private final void closeFullscreenDialog() {
    }
    
    public final void Loadyoutubeurl() {
    }
    
    private final void earphone() {
    }
    
    private final void showSpeedOptions() {
    }
    
    private final com.google.android.exoplayer2.source.MediaSource buildMediaSource(android.net.Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.exoplayer2.upstream.DataSource.Factory buildDataSourceFactory(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.upstream.TransferListener listener) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.exoplayer2.upstream.HttpDataSource.Factory buildHttpDataSourceFactory(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.upstream.TransferListener listener) {
        return null;
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void LoadVideoEncryoted() {
    }
    
    public final void limmedia() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    public final void releasePlayer() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @kotlin.jvm.Throws(exceptionClasses = {java.security.GeneralSecurityException.class})
    public final javax.crypto.Cipher getCipher() throws java.security.GeneralSecurityException {
        return null;
    }
    
    @java.lang.Override()
    public void selectSpeedPos(int pos) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/utkarshnew/android/Download/DownloadVideoPlayer$Companion;", "", "()V", "LOCAL_ENCRYPTION_KEY", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}