package com.utkarshnew.android.feeds.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0096\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0015\u0010\u009e\u0001\u001a\u0004\u0018\u00010A2\n\u0010\u009f\u0001\u001a\u0005\u0018\u00010\u00a0\u0001J\u0014\u0010\u009e\u0001\u001a\u0004\u0018\u00010A2\u0007\u0010\u00a1\u0001\u001a\u00020:H\u0002J\u0016\u0010\u00a2\u0001\u001a\u0005\u0018\u00010\u00a3\u00012\n\u0010\u009f\u0001\u001a\u0005\u0018\u00010\u00a0\u0001J \u0010\u00a4\u0001\u001a\u0004\u0018\u00010C2\b\u0010\u00a5\u0001\u001a\u00030\u00a6\u00012\t\u0010\u00a7\u0001\u001a\u0004\u0018\u00010\u0010H\u0002J\n\u0010\u00a8\u0001\u001a\u00030\u00a9\u0001H\u0002J\u0011\u0010\u00aa\u0001\u001a\u00030\u00a9\u00012\u0007\u0010\u00ab\u0001\u001a\u00020\u0010J\u0017\u0010\u00ac\u0001\u001a\u0005\u0018\u00010\u00ad\u00012\t\u0010\u00ae\u0001\u001a\u0004\u0018\u00010\u0010H\u0017J\n\u0010\u00af\u0001\u001a\u00030\u00a9\u0001H\u0002J\n\u0010\u00b0\u0001\u001a\u00030\u00a9\u0001H\u0002J\u0012\u0010\u00b1\u0001\u001a\u00020:2\u0007\u0010\u00b2\u0001\u001a\u00020IH\u0002J\n\u0010\u00b3\u0001\u001a\u00030\u00a9\u0001H\u0016J\u0014\u0010\u00b4\u0001\u001a\u00030\u00a9\u00012\b\u0010\u00b5\u0001\u001a\u00030\u00b6\u0001H\u0016J\u0016\u0010\u00b7\u0001\u001a\u00030\u00a9\u00012\n\u0010\u00b8\u0001\u001a\u0005\u0018\u00010\u00b9\u0001H\u0014J\n\u0010\u00ba\u0001\u001a\u00030\u00a9\u0001H\u0002J\n\u0010\u00bb\u0001\u001a\u00030\u00a9\u0001H\u0003J\u0013\u0010\u00bc\u0001\u001a\u00030\u00a9\u00012\u0007\u0010\u00bd\u0001\u001a\u00020IH\u0016J\n\u0010\u00be\u0001\u001a\u00030\u00a9\u0001H\u0002R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010\'\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010$\"\u0004\b)\u0010&R\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001c\u00100\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010$\"\u0004\b2\u0010&R\u001c\u00103\u001a\u0004\u0018\u000104X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u000e\u00109\u001a\u00020:X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u0004\u0018\u00010<X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u00010>X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010@\u001a\u0004\u0018\u00010AX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010B\u001a\u0004\u0018\u00010CX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u000e\u0010H\u001a\u00020IX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010J\u001a\b\u0012\u0004\u0012\u00020L0KX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010M\u001a\u00020NX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u001c\u0010Q\u001a\u0004\u0018\u00010RX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u0010T\"\u0004\bU\u0010VR\u000e\u0010W\u001a\u00020XX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010Y\u001a\u0004\u0018\u00010ZX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^R\u0010\u0010_\u001a\u0004\u0018\u00010`X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010a\u001a\u0004\u0018\u00010bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bc\u0010d\"\u0004\be\u0010fR\u001c\u0010g\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bh\u0010$\"\u0004\bi\u0010&R\u001a\u0010j\u001a\u00020\u0016X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bk\u0010\u0018\"\u0004\bl\u0010\u001aR\u001c\u0010m\u001a\u0004\u0018\u00010nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\u000e\u0010s\u001a\u00020IX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010t\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010u\u001a\u0004\u0018\u00010vX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bw\u0010x\"\u0004\by\u0010zR\u001a\u0010{\u001a\u00020IX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b|\u0010}\"\u0004\b~\u0010\u007fR\u001d\u0010\u0080\u0001\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010\u0012\"\u0005\b\u0082\u0001\u0010\u0014R\u001d\u0010\u0083\u0001\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u0012\"\u0005\b\u0085\u0001\u0010\u0014RA\u0010\u0086\u0001\u001a/\u0012\u000f\u0012\r \u0089\u0001*\u0005\u0018\u00010\u0088\u00010\u0088\u0001 \u0089\u0001*\u0016\u0012\u000f\u0012\r \u0089\u0001*\u0005\u0018\u00010\u0088\u00010\u0088\u0001\u0018\u00010\u0087\u00010\u0087\u0001\u00a2\u0006\n\n\u0000\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001R\"\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001\"\u0006\b\u0090\u0001\u0010\u0091\u0001R\u0012\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0095\u0001\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0012\"\u0005\b\u0097\u0001\u0010\u0014R\u001f\u0010\u0098\u0001\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0099\u0001\u0010\u0012\"\u0005\b\u009a\u0001\u0010\u0014R\u001f\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u009c\u0001\u0010\u0012\"\u0005\b\u009d\u0001\u0010\u0014\u00a8\u0006\u00bf\u0001"}, d2 = {"Lcom/utkarshnew/android/feeds/activity/FeedVideoPlayer;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/text/Html$ImageGetter;", "Lcom/utkarshnew/android/JWextractor/SelectSpeedo;", "()V", "BANDWIDTH_METER", "Lcom/google/android/exoplayer2/upstream/DefaultBandwidthMeter;", "getBANDWIDTH_METER", "()Lcom/google/android/exoplayer2/upstream/DefaultBandwidthMeter;", "data_layout", "Landroid/widget/RelativeLayout;", "getData_layout", "()Landroid/widget/RelativeLayout;", "setData_layout", "(Landroid/widget/RelativeLayout;)V", "des", "", "getDes", "()Ljava/lang/String;", "setDes", "(Ljava/lang/String;)V", "descritption", "Landroid/widget/TextView;", "getDescritption", "()Landroid/widget/TextView;", "setDescritption", "(Landroid/widget/TextView;)V", "exoPlayer", "Lcom/google/android/exoplayer2/ui/SimpleExoPlayerView;", "getExoPlayer", "()Lcom/google/android/exoplayer2/ui/SimpleExoPlayerView;", "setExoPlayer", "(Lcom/google/android/exoplayer2/ui/SimpleExoPlayerView;)V", "exo_ffwd", "Landroid/widget/ImageView;", "getExo_ffwd", "()Landroid/widget/ImageView;", "setExo_ffwd", "(Landroid/widget/ImageView;)V", "exo_rew", "getExo_rew", "setExo_rew", "htmldata", "Landroid/text/Spanned;", "getHtmldata", "()Landroid/text/Spanned;", "setHtmldata", "(Landroid/text/Spanned;)V", "icon", "getIcon", "setIcon", "loadControl", "Lcom/google/android/exoplayer2/DefaultLoadControl;", "getLoadControl", "()Lcom/google/android/exoplayer2/DefaultLoadControl;", "setLoadControl", "(Lcom/google/android/exoplayer2/DefaultLoadControl;)V", "mExoPlayerFullscreen", "", "mFullScreenButton", "Landroid/widget/FrameLayout;", "mFullScreenDialog", "Landroid/app/Dialog;", "mFullScreenIcon", "mediaDataSourceFactory", "Lcom/google/android/exoplayer2/upstream/DataSource$Factory;", "mediaSource", "Lcom/google/android/exoplayer2/source/MediaSource;", "getMediaSource", "()Lcom/google/android/exoplayer2/source/MediaSource;", "setMediaSource", "(Lcom/google/android/exoplayer2/source/MediaSource;)V", "newOrientation", "", "onIconMenuItemClickListener", "Lcom/skydoves/powermenu/OnMenuItemClickListener;", "Lcom/skydoves/powermenu/PowerMenuItem;", "playPosition", "", "getPlayPosition", "()J", "player", "Lcom/google/android/exoplayer2/SimpleExoPlayer;", "getPlayer", "()Lcom/google/android/exoplayer2/SimpleExoPlayer;", "setPlayer", "(Lcom/google/android/exoplayer2/SimpleExoPlayer;)V", "playerEventListener", "Lcom/google/android/exoplayer2/Player$EventListener;", "popupWindow", "Landroid/widget/PopupWindow;", "getPopupWindow", "()Landroid/widget/PopupWindow;", "setPopupWindow", "(Landroid/widget/PopupWindow;)V", "powerMenu", "Lcom/skydoves/powermenu/PowerMenu;", "progressBar", "Landroid/widget/ProgressBar;", "getProgressBar", "()Landroid/widget/ProgressBar;", "setProgressBar", "(Landroid/widget/ProgressBar;)V", "quality", "getQuality", "setQuality", "readmore", "getReadmore", "setReadmore", "rootView", "Landroidx/constraintlayout/widget/ConstraintLayout;", "getRootView", "()Landroidx/constraintlayout/widget/ConstraintLayout;", "setRootView", "(Landroidx/constraintlayout/widget/ConstraintLayout;)V", "savedOrientation", "speedTV", "speedoAdapter", "Lcom/utkarshnew/android/JWextractor/SpeedoAdapter;", "getSpeedoAdapter", "()Lcom/utkarshnew/android/JWextractor/SpeedoAdapter;", "setSpeedoAdapter", "(Lcom/utkarshnew/android/JWextractor/SpeedoAdapter;)V", "speedpostiion", "getSpeedpostiion", "()I", "setSpeedpostiion", "(I)V", "speedx", "getSpeedx", "setSpeedx", "spped_title", "getSpped_title", "setSpped_title", "sppedlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Model/UrlObject;", "kotlin.jvm.PlatformType", "getSppedlist", "()Ljava/util/ArrayList;", "trackSelector", "Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector;", "getTrackSelector", "()Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector;", "setTrackSelector", "(Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector;)V", "trackSelectorParameters", "Lcom/google/android/exoplayer2/trackselection/DefaultTrackSelector$Parameters;", "tvGoLive", "url", "getUrl", "setUrl", "userAgent", "getUserAgent", "setUserAgent", "view_type", "getView_type", "setView_type", "buildDataSourceFactory", "listener", "Lcom/google/android/exoplayer2/upstream/TransferListener;", "useBandwidthMeter", "buildHttpDataSourceFactory", "Lcom/google/android/exoplayer2/upstream/HttpDataSource$Factory;", "buildMediaSource", "uri", "Landroid/net/Uri;", "overrideExtension", "closeFullscreenDialog", "", "descriptionCheck", "it", "getDrawable", "Landroid/graphics/drawable/Drawable;", "s", "initFullscreenButton", "initFullscreenDialog", "isPortrait", "orientation", "onBackPressed", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openFullscreenDialog", "popupWindowPart", "selectSpeedPos", "pos", "showSpeedOptions", "app_debug"})
public final class FeedVideoPlayer extends androidx.appcompat.app.AppCompatActivity implements android.text.Html.ImageGetter, com.utkarshnew.android.JWextractor.SelectSpeedo {
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.ui.SimpleExoPlayerView exoPlayer;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String url = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String des;
    private final long playPosition = 0L;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String userAgent;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.source.MediaSource mediaSource;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.SimpleExoPlayer player;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.DefaultLoadControl loadControl;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ProgressBar progressBar;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout data_layout;
    private int newOrientation = 0;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView exo_ffwd;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView exo_rew;
    @org.jetbrains.annotations.Nullable()
    private android.text.Spanned htmldata;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String speedx = "";
    public android.widget.TextView descritption;
    public android.widget.TextView readmore;
    private int savedOrientation = 0;
    private com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters trackSelectorParameters;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.exoplayer2.trackselection.DefaultTrackSelector trackSelector;
    private android.widget.FrameLayout mFullScreenButton;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String spped_title = "";
    private android.app.Dialog mFullScreenDialog;
    private boolean mExoPlayerFullscreen = false;
    private android.widget.ImageView mFullScreenIcon;
    private com.skydoves.powermenu.PowerMenu powerMenu;
    private android.widget.TextView tvGoLive;
    private android.widget.TextView speedTV;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView quality;
    private final java.util.ArrayList<com.utkarshnew.android.Model.UrlObject> sppedlist = null;
    @org.jetbrains.annotations.Nullable()
    private android.widget.PopupWindow popupWindow;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.JWextractor.SpeedoAdapter speedoAdapter;
    private int speedpostiion = 3;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView icon;
    @org.jetbrains.annotations.Nullable()
    private androidx.constraintlayout.widget.ConstraintLayout rootView;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String view_type = "1";
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.exoplayer2.upstream.DefaultBandwidthMeter BANDWIDTH_METER = null;
    private com.google.android.exoplayer2.upstream.DataSource.Factory mediaDataSourceFactory;
    private final com.google.android.exoplayer2.Player.EventListener playerEventListener = null;
    private final com.skydoves.powermenu.OnMenuItemClickListener<com.skydoves.powermenu.PowerMenuItem> onIconMenuItemClickListener = null;
    
    public FeedVideoPlayer() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.ui.SimpleExoPlayerView getExoPlayer() {
        return null;
    }
    
    public final void setExoPlayer(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.ui.SimpleExoPlayerView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrl() {
        return null;
    }
    
    public final void setUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDes() {
        return null;
    }
    
    public final void setDes(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final long getPlayPosition() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getUserAgent() {
        return null;
    }
    
    public final void setUserAgent(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.source.MediaSource getMediaSource() {
        return null;
    }
    
    public final void setMediaSource(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.source.MediaSource p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.SimpleExoPlayer getPlayer() {
        return null;
    }
    
    public final void setPlayer(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.SimpleExoPlayer p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.DefaultLoadControl getLoadControl() {
        return null;
    }
    
    public final void setLoadControl(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.DefaultLoadControl p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ProgressBar getProgressBar() {
        return null;
    }
    
    public final void setProgressBar(@org.jetbrains.annotations.Nullable()
    android.widget.ProgressBar p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getData_layout() {
        return null;
    }
    
    public final void setData_layout(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
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
    
    @org.jetbrains.annotations.Nullable()
    public final android.text.Spanned getHtmldata() {
        return null;
    }
    
    public final void setHtmldata(@org.jetbrains.annotations.Nullable()
    android.text.Spanned p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSpeedx() {
        return null;
    }
    
    public final void setSpeedx(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.TextView getDescritption() {
        return null;
    }
    
    public final void setDescritption(@org.jetbrains.annotations.NotNull()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.TextView getReadmore() {
        return null;
    }
    
    public final void setReadmore(@org.jetbrains.annotations.NotNull()
    android.widget.TextView p0) {
    }
    
    private final boolean isPortrait(int orientation) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.trackselection.DefaultTrackSelector getTrackSelector() {
        return null;
    }
    
    public final void setTrackSelector(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.trackselection.DefaultTrackSelector p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSpped_title() {
        return null;
    }
    
    public final void setSpped_title(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ImageView getQuality() {
        return null;
    }
    
    public final void setQuality(@org.jetbrains.annotations.Nullable()
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
    
    public final int getSpeedpostiion() {
        return 0;
    }
    
    public final void setSpeedpostiion(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ImageView getIcon() {
        return null;
    }
    
    public final void setIcon(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.constraintlayout.widget.ConstraintLayout getRootView() {
        return null;
    }
    
    public final void setRootView(@org.jetbrains.annotations.Nullable()
    androidx.constraintlayout.widget.ConstraintLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getView_type() {
        return null;
    }
    
    public final void setView_type(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.exoplayer2.upstream.DefaultBandwidthMeter getBANDWIDTH_METER() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void descriptionCheck(@org.jetbrains.annotations.NotNull()
    java.lang.String it) {
    }
    
    private final void initFullscreenButton() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n", "UseCompatLoadingForDrawables"})
    private final void popupWindowPart() {
    }
    
    private final void showSpeedOptions() {
    }
    
    private final void openFullscreenDialog() {
    }
    
    private final void closeFullscreenDialog() {
    }
    
    private final void initFullscreenDialog() {
    }
    
    private final com.google.android.exoplayer2.source.MediaSource buildMediaSource(android.net.Uri uri, java.lang.String overrideExtension) {
        return null;
    }
    
    private final com.google.android.exoplayer2.upstream.DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.upstream.DataSource.Factory buildDataSourceFactory(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.upstream.TransferListener listener) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.exoplayer2.upstream.HttpDataSource.Factory buildHttpDataSourceFactory(@org.jetbrains.annotations.Nullable()
    com.google.android.exoplayer2.upstream.TransferListener listener) {
        return null;
    }
    
    @java.lang.Override()
    public void onConfigurationChanged(@org.jetbrains.annotations.NotNull()
    android.content.res.Configuration newConfig) {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables"})
    @java.lang.Override()
    public android.graphics.drawable.Drawable getDrawable(@org.jetbrains.annotations.Nullable()
    java.lang.String s) {
        return null;
    }
    
    @java.lang.Override()
    public void selectSpeedPos(int pos) {
    }
}