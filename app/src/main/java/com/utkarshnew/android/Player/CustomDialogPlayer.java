package com.utkarshnew.android.Player;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.naveed.ytextractor.ExtractorException;
import com.naveed.ytextractor.YoutubeStreamExtractor;
import com.naveed.ytextractor.model.YoutubeMedia;
import com.naveed.ytextractor.model.YoutubeMeta;
import com.naveed.ytextractor.utils.LogUtils;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionDialog;
import com.utkarshnew.android.Player.customview.ExoSpeedDemo.TrackSelectionHelper;
import com.utkarshnew.android.R;
import com.utkarshnew.android.testmodule.activity.ViewSolutionActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomDialogPlayer extends Dialog {


    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    ProgressBar progressBar;

    private List<Integer> sparseKeyList, sparseAdaptiveResolutionList, sparseMuxedResolutionList;
    private HashMap<Integer, String> sparseAdaptiveVideoUrlList, sparseMuxedVideoUrlList;
    private List<String> sparseOPUSAudioUrl;
    private String userAgent;
    private DataSource.Factory mediaDataSourceFactory;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    MediaSource mediaSource;
    ImageView quality, cross;
    private boolean isShowingTrackSelectionDialog;
    Context context;
    String data[];
    String url;

    Handler handler;
    Runnable runnable;

    public CustomDialogPlayer(@NonNull Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }

    public CustomDialogPlayer(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialogPlayer(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog_player);
        handler = new Handler();
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view_new);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        quality = findViewById(R.id.quality);
        cross = findViewById(R.id.cross);
        //url="AANWPChVAFU#0#10000";
        userAgent = Util.getUserAgent(getContext(), "ExoPlayerDemo");
        mediaDataSourceFactory = buildDataSourceFactory(false);

//        dataSourceFactory = new DefaultDataSourceFactory(this,
//                new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "ExoPlayerDemo")));


        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
        data = url.split("#");
        if (data[0] != null) {
            extractYoutubeUrl(data[0]);
        } else {
            extractYoutubeUrl(url);
        }

        initFullscreenButton();
        cross.setOnClickListener(new OnSingleClickListener(() -> {
            youtubeUri = "";
            if (player != null) {
                player.release();
                player = null;
            }
            simpleExoPlayerView = null;
            dismiss();
            return null;
        }));

        findViewById(R.id.quality).setOnClickListener(new OnSingleClickListener(() -> {
            if (trackSelector != null && trackSelector.getCurrentMappedTrackInfo() != null && player.getPlayWhenReady()) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(trackSelector, dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(((ViewSolutionActivity) context).getSupportFragmentManager(), null);
                }
            } else {
                Toast.makeText(getContext(), "Player not ready, try again", Toast.LENGTH_SHORT).show();
            }
            return null;
        }));
    }


    private ImageView mFullScreenIcon;
    private TextView speedTV, exo_position, exo_duration;
    private FrameLayout mFullScreenButton;
    private DefaultTimeBar exo_progress;
    private ImageButton exo_rew, exo_ffwd;

    private void initFullscreenButton() {
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        trackSelector.setParameters(trackSelectorParameters);
        speedTV = controlView.findViewById(R.id.speedTV);
        exo_progress = controlView.findViewById(R.id.exo_progress);
        exo_position = controlView.findViewById(R.id.exo_position);
        exo_duration = controlView.findViewById(R.id.exo_duration);
        exo_rew = controlView.findViewById(R.id.exo_rew);
        exo_ffwd = controlView.findViewById(R.id.exo_ffwd);

        if (exo_progress != null) {
            exo_progress.setVisibility(View.INVISIBLE);
            exo_position.setVisibility(View.INVISIBLE);
            exo_duration.setVisibility(View.INVISIBLE);
            exo_rew.setVisibility(View.GONE);
            exo_ffwd.setVisibility(View.GONE);
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
        PopupMenu popupMenu = new PopupMenu(getContext(), speedTV, R.style.MyPopupMenu);
        Menu menu = popupMenu.getMenu();

        final String[] speeds = getContext().getResources().getStringArray(R.array.speed_values);
        if (speeds.length != 0) {
            for (String speed : speeds) {
                menu.add(speed + "x");
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    String title = item.getTitle().toString();
                    if (player != null) {
                        speedTV.setText(title);
                        player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(title.replace("x", "")), 1f));
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
                        youtubeUri = LogUtils.getLivevideourl();
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

                } else {
                    if (meta.getIsLiveContent() && meta.getIsLowLatencyLiveStream() && !meta.getisLive()) {

                        onBackPressed();
                    } else {
                        onBackPressed();
                    }
                }

                progressBar.setVisibility(View.GONE);

                if (youtubeUri != null) {
                    Loadyoutubeurl(youtubeUri);
                }
            }
        }).Extract("https://www.youtube.com/watch?v=" + url);
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    public DataSource.Factory buildDataSourceFactory(TransferListener listener) {
        DefaultDataSourceFactory dataSourceFactory = null;

        dataSourceFactory = new DefaultDataSourceFactory(getContext(), listener, buildHttpDataSourceFactory(listener));
        return dataSourceFactory;
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(TransferListener listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }

    public void Loadyoutubeurl(String youtubeUri) {
        progressBar.setVisibility(View.VISIBLE);
        if (sparseAdaptiveVideoUrlList != null && sparseAdaptiveVideoUrlList.size() > 0) {
            Uri videoUri, audioUri = null;
            videoUri = Uri.parse(youtubeUri);
            if (!sparseOPUSAudioUrl.isEmpty()) {
                audioUri = Uri.parse(sparseOPUSAudioUrl.get(0));
            }

            try {
                ProgressiveMediaSource videoSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(videoUri);
                ProgressiveMediaSource audioSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(audioUri);
                MergingMediaSource resource = new MergingMediaSource(videoSource, audioSource);
                TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
                trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
                trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
                trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
//                    Uri videouri = Uri.parse(youtubeUri);
                player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                player.addListener(playerEventListener);
                simpleExoPlayerView.setPlayer(player);
                player.setPlayWhenReady(false);
//                    mediaSource = buildMediaSource(videouri, null);
                player.prepare(resource);
                if (data[1] != null) {
                    player.seekTo(Long.parseLong(data[1]));
                }
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
                player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                player.addListener(playerEventListener);
                simpleExoPlayerView.setPlayer(player);
                player.setPlayWhenReady(false);
                mediaSource = buildMediaSource(videouri, null);
                player.prepare(mediaSource);
            } catch (Exception e) {
                e.printStackTrace();
            }

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

                if (data[1] != null && data[2] != null) {
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if ((player.getCurrentPosition() >= Long.parseLong(data[2])) || (player.getCurrentPosition() < Long.parseLong(data[1]))) { // After reaching destination time
                                if (player != null && player.getPlayWhenReady()) {
                                    youtubeUri = "";
                                    if (player != null) {
                                        player.release();
                                        player = null;
                                    }
                                    simpleExoPlayerView = null;
                                    dismiss();
                                }
                            }
                        }
                    };
                    handler.postDelayed(runnable, 0);
                }


//                if(data[2]!=null) {
//                    new CountDownTimer(Long.parseLong(data[2]), 1000) {
//                        public void onTick(long millisUntilFinished) {
//                            data[2] = String.valueOf(Long.parseLong(data[2]) - 1000);
//                        }
//
//                        public void onFinish() {
//                            if (player != null && player.getPlayWhenReady()) {
//                                youtubeUri="";
//                                if (player != null) {
//                                    player.release();
//                                    player = null;
//                                }
//                                simpleExoPlayerView=null;
//                                dismiss();
//                            }
//                        }
//                    }.start();
//                }
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


}
