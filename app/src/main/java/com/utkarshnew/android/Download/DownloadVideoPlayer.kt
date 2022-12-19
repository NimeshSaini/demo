package com.utkarshnew.android.Download

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.support.v4.media.session.MediaSessionCompat
import android.util.Size
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.crypto.AesCipherDataSource
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.utkarshnew.android.JWextractor.SelectSpeedo
import com.utkarshnew.android.JWextractor.SpeedoAdapter
import com.utkarshnew.android.Player.Liveawsactivity
import com.utkarshnew.android.R
import com.utkarshnew.android.Room.UtkashRoom
import com.utkarshnew.android.Utils.AES
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.Utils.MakeMyExam
import com.utkarshnew.android.Utils.SharedPreference
import com.utkarshnew.android.table.UserHistroyTable
import fr.maxcom.http.LocalSingleHttpServer
import java.io.File
import java.security.GeneralSecurityException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class DownloadVideoPlayer : AppCompatActivity(), SelectSpeedo {
    private var speedTV: TextView? = null
    private var icon: ImageView? = null
    private var powerMenu: PowerMenu? = null
    private var mExoPlayerFullscreen = false
    private val BANDWIDTH_METER = DefaultBandwidthMeter()
    var speedpostiion = 3
    var tempspeed = 3
    var exo_ffwd: ImageView? = null
    var exo_rew: ImageView? = null
    val sppedlist = Helper.speedlist()


    var popupWindow: PopupWindow? = null
    var speedoAdapter:SpeedoAdapter?=null
    private var mFullScreenButton: FrameLayout? = null
    private var simpleExoPlayerView: SimpleExoPlayerView? = null
    var player: SimpleExoPlayer? = null
    private val mediaDataSourceFactory: DataSource.Factory? = null
    private var userAgent: String? = null
    private val trackSelectorParameters: DefaultTrackSelector.Parameters? = null
    var mediaSource: MediaSource? = null
    var trackSelector: DefaultTrackSelector? = null
    private var isShowingTrackSelectionDialog = false
    var url = ""
    var speedx = ""
    private var mFullScreenIcon: ImageView? = null
    private var video_name_txt: TextView? = null
    private var MarqueeText: TextView? = null
    private val sparseKeyList: List<Int>? = null
    private val sparseAdaptiveResolutionList: List<Int>? = null
    private val sparseMuxedResolutionList: List<Int>? = null
    private val sparseAdaptiveVideoUrlList: HashMap<Int, String>? = null
    private val sparseMuxedVideoUrlList: HashMap<Int, String>? = null
    private val sparseOPUSAudioUrl: List<String>? = null
    private var video_name: String? = null
    private var video_id: String? = ""
    private var video_play: String? = ""
    private var video_time: String? = null
    private var course_id: String? = ""
    private var token: String? = ""
    private var valid_to: String? = ""
    private var root_new: RelativeLayout? = null
    private var progressBar: ProgressBar? = null
    private var utkashRoom: UtkashRoom? = null
    private var current_pos: Long? = null
    private val thread: Thread? = null
    private var savedOrientation = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Helper.enableScreenShot(this)
        setContentView(R.layout.activity_download_video_player)
        utkashRoom = UtkashRoom.getAppDatabase(this)
        //   utkashRoom!!.getOpenHelper().writableDatabase.enableWriteAheadLogging()
        if (intent.extras != null) {
            video_name = intent.getStringExtra("video_name")
            video_id = intent.extras!!.getString("video_id")
            video_play = intent.extras!!.getString("video")
            video_time = intent.extras!!.getString("video_time")
            course_id = intent.extras!!.getString("course_id")
            token = intent.extras!!.getString("token")
            valid_to = intent.extras!!.getString("valid_to")
            video_time = if (video_time == null) "" else video_time
            current_pos = intent.extras!!.getLong("current_pos", 0L)
            video_name = if (video_name == null) "" else video_name
        }
        progressBar = findViewById(R.id.progress_bar)
        simpleExoPlayerView = findViewById<View>(R.id.player_view_new) as SimpleExoPlayerView
        root_new = findViewById(R.id.root_new)
        video_name_txt = findViewById(R.id.video_name)
        MarqueeText = findViewById(R.id.MarqueeText)
        video_name_txt!!.setText(video_name)
        video_name_txt!!.setSelected(true)
        MarqueeText!!.setText(SharedPreference.getInstance().loggedInUser.mobile)
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo")
        val controlView = simpleExoPlayerView!!.findViewById<PlayerControlView>(R.id.exo_controller)
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon)
        speedTV = controlView.findViewById(R.id.speedTV)
        speedTV!!.visibility = View.GONE

        val quality = controlView.findViewById<ImageView>(R.id.quality)

        quality.visibility = View.VISIBLE

        quality.setOnClickListener {
            popupWindowPart();

            val location = IntArray(2)
            quality.getLocationInWindow(location)
            val size = Size(popupWindow!!.contentView.measuredWidth, popupWindow!!.contentView.height)

            if (savedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                popupWindow!!.showAtLocation(quality, Gravity.TOP or Gravity.RIGHT, 50, quality.bottom)
            } else {
                popupWindow!!.showAtLocation(window.decorView, Gravity.TOP or Gravity.RIGHT, 0, ((location[1] + size.height) / 2.8).toInt())
            }
          //  popupWindow!!.showAsDropDown(it, 100, -it.height - 550, Gravity.RIGHT);
        }





        exo_ffwd =controlView.findViewById(R.id.exo_ffwd_new)
        exo_rew = controlView.findViewById(R.id.exo_rew_new)


        exo_ffwd!!.setOnClickListener { view: View? ->
            if (player!!.currentPosition < (player!!.duration - 10000)) player!!.seekTo(
                player!!.currentPosition + 10000
            )
        }
        exo_rew!!.setOnClickListener { view: View? ->
            if (player!!.currentPosition > 10000) player!!.seekTo(
                player!!.currentPosition - 10000
            )
        }

       /*
        exo_ffwd = findViewById(R.id.exo_forward)
        exo_rew = findViewById(R.id.exo_rewind)*/
        icon = controlView.findViewById(R.id.icon)
        icon!!.setVisibility(View.GONE)

        /*   if (!TextUtils.isEmpty(speedx)) {
            player.setPlaybackParameters(new PlaybackParameters(Float.valueOf(speedx.replace("x", "")), 1f));
        }*/



        try {
            val orientationEventListener: OrientationEventListener =
                object : OrientationEventListener(
                    applicationContext
                ) {
                    override fun onOrientationChanged(orientation: Int) {
                        try {
                            val result = Settings.System.getInt(
                                contentResolver,
                                Settings.System.ACCELEROMETER_ROTATION,
                                0
                            )
                            if (result == 1) {
                                val isPortrait = isPortrait(orientation)
                                if (!isPortrait && savedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                                    savedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                                } else if (isPortrait && savedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                                    savedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            orientationEventListener.enable()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (speedTV != null) {
            speedTV!!.setOnClickListener { showSpeedOptions() }
        }
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button)
        mFullScreenButton!!.setOnClickListener(View.OnClickListener { v: View? -> if (!mExoPlayerFullscreen) openFullscreenDialog() else closeFullscreenDialog() })
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun popupWindowPart() {
        popupWindow = PopupWindow(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.dialog_speed, null)
        val speed_recyclerview = view.findViewById<RecyclerView>(R.id.speed_recyclerview)
        val layput = view.findViewById<RelativeLayout>(R.id.layput)

        val scale: Float = resources.displayMetrics.density
        val pixels = (150 * scale + 0.5f).toInt()
        val width = (150 * scale + 0.5f).toInt()
        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels)
        layput!!.layoutParams = layoutParams

        for (i in sppedlist.indices) {
            if (i == speedpostiion) {
                sppedlist[speedpostiion].isSelected = true
            }
        }
        speedoAdapter = SpeedoAdapter(this@DownloadVideoPlayer, sppedlist, this)
        Objects.requireNonNull(speed_recyclerview).adapter = speedoAdapter

        popupWindow!!.isFocusable = true
        popupWindow!!.setBackgroundDrawable(/* background = */ resources.getDrawable(R.drawable.background_rectangle_gray))
        popupWindow!!.width = width - 50
        popupWindow!!.showAtLocation(view, Gravity.RIGHT, 0, 0)
        popupWindow!!.contentView = view
    }



    private fun openFullscreenDialog() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        mFullScreenIcon!!.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.fullscreenexit
            )
        )
        mExoPlayerFullscreen = true
    }

    private fun isPortrait(orientation: Int): Boolean {
        Log.d("prince", "" + orientation)
        return orientation < 85 || orientation > 100
    }

    private val onIconMenuItemClickListener =
        OnMenuItemClickListener<PowerMenuItem> { position, item ->
            showSpeedOptions()
            // item.setIsSelected(true);
            powerMenu!!.dismiss()
        }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        try {
            if (newConfig.orientation == 1) {
                mExoPlayerFullscreen = false
                mFullScreenIcon!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.fullscreen
                    )
                )
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                val scale = resources.displayMetrics.density
                val pixels: Int
                val xlarge =
                    ((resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) == 4)
                val large =
                    ((resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
                if (large) {
                    pixels = (350 * scale + 0.5f).toInt()
                } else if (xlarge) {
                    pixels = (400 * scale + 0.5f).toInt()
                } else {
                    pixels = (230 * scale + 0.5f).toInt()
                }
                val layoutParams =
                    RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels)
                root_new!!.layoutParams = layoutParams
            } else {
                mExoPlayerFullscreen = true
                mFullScreenIcon!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.fullscreenexit
                    )
                )
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
                val layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                root_new!!.layoutParams = layoutParams
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun closeFullscreenDialog() {
        mExoPlayerFullscreen = false
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mFullScreenIcon!!.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.fullscreen
            )
        )
    }

    fun Loadyoutubeurl() {
        val mediaSource: MediaSource
        val dataSpec = DataSpec(
            Uri.fromFile(
                File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/.Videos/",
                    "$video_play.mp4"
                )
            )
        )

        var aesCipherDataSource: AesCipherDataSource? = null
        try {
            aesCipherDataSource =
                AesCipherDataSource(LOCAL_ENCRYPTION_KEY.toByteArray(), FileDataSource())
            aesCipherDataSource.open(dataSpec)
            val finalAesCipherDataSource: AesCipherDataSource = aesCipherDataSource
            val factory = DataSource.Factory { finalAesCipherDataSource }
            mediaSource =
                buildMediaSource(Objects.requireNonNull(aesCipherDataSource.getUri())!!, factory)
            val adaptiveTrackSelectionFactory: TrackSelection.Factory =
                AdaptiveTrackSelection.Factory(BANDWIDTH_METER)
            trackSelector = DefaultTrackSelector(adaptiveTrackSelectionFactory)

            player = ExoPlayerFactory.newSimpleInstance(
                this,
                DefaultRenderersFactory(this),
                trackSelector,
                DefaultLoadControl()
            )
            player!!.addListener(playerEventListener)
            simpleExoPlayerView!!.setPlayer(player)
            player!!.setPlayWhenReady(true)
            player!!.prepare(mediaSource)
            player!!.seekTo((current_pos)!!)
            val sppedlist = Helper.speedlist()


            if (sppedlist.get(speedpostiion).getTitle()
                    .equals("Normal", ignoreCase = true)
            ) {
                player!!.playbackParameters = PlaybackParameters("1".toFloat(), 1f)
            } else player!!.playbackParameters = PlaybackParameters(
                sppedlist.get(
                    speedpostiion
                ).title.replace("x", "").toFloat(), 1f
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val userHistroyTable = UserHistroyTable()
            userHistroyTable.video_id = video_id
            userHistroyTable.video_name = video_name
            userHistroyTable.type = "Video"
            userHistroyTable.user_id = MakeMyExam.userId
            userHistroyTable.course_id = course_id
            userHistroyTable.valid_to = valid_to
            userHistroyTable.current_time = "" + MakeMyExam.getTime_server()
            utkashRoom!!.getuserhistorydao().addUser(userHistroyTable)


            // earphone()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun earphone() {
        val audioManager: AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioManager.isWiredHeadsetOn) {
            val mediaSessionCompat = MediaSessionCompat(this, "exoplayer")
            val mediaSessionConnector1 = MediaSessionConnector(mediaSessionCompat)
            mediaSessionConnector1.setPlayer(player)
        } else {
            val mediaSession = MediaSessionCompat(this, "exoplayer")
            val mediaSessionConnector = MediaSessionConnector(mediaSession)
            mediaSessionConnector.setMediaButtonEventHandler { player1: Player?, controlDispatcher: ControlDispatcher?, mediaButtonEvent: Intent ->
                val event =
                    mediaButtonEvent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
                if (event!!.action == KeyEvent.ACTION_UP) {
                    when (event.keyCode) {
                        KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                            if (player != null) {
                                player!!.playWhenReady = false
                                player!!.playbackState
                            }
                        }
                        KeyEvent.KEYCODE_MEDIA_PLAY ->
                            if (player != null) {
                                player!!.playWhenReady = true
                                player!!.playbackState
                            }
                    }
                } else if (event.action == KeyEvent.ACTION_DOWN) {
                    when (event.keyCode) {
                        KeyEvent.KEYCODE_MEDIA_PAUSE ->
                            if (player != null) {
                                player!!.setPlayWhenReady(false)
                                player!!.getPlaybackState()
                            }
                        KeyEvent.KEYCODE_MEDIA_PLAY -> if (player != null) {
                            player!!.playWhenReady = true
                            player!!.playbackState
                        }
                    }
                }
                true
            }
            mediaSessionConnector.setPlayer(simpleExoPlayerView!!.player)
        }
    }

    private fun showSpeedOptions() {
        val popupMenu = PopupMenu(this, icon, R.style.MyPopupMenu)
        val menu = popupMenu.menu
        val speeds = resources.getStringArray(R.array.speed_values)
        if (speeds.isNotEmpty()) {
            for (speed: String in speeds) {
                if (speed.equals("1", ignoreCase = true)) {
                    menu.add("Normal")
                } else {
                    menu.add(speed + "x")
                }
            }
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    val title = item.title.toString()
                    if (player != null) {
                        //    speedTV!!.text = title
                        speedx = title
                        if (title.equals("Normal", ignoreCase = true)) {
                            player!!.playbackParameters =
                                PlaybackParameters(java.lang.Float.valueOf("1"), 1f)
                        } else {
                            player!!.playbackParameters = PlaybackParameters(
                                java.lang.Float.valueOf(title.replace("x", "")),
                                1f
                            )
                        }
                    }
                    return false
                }
            })
            popupMenu.show()
        }
    }

    private val playerEventListener: Player.EventListener = object : Player.DefaultEventListener() {
        override fun onTimelineChanged(timeline: Timeline, manifest: Any?) {
            //TODO: Please refer to the ExoPlayer guide.
        }

        override fun onTracksChanged(
            trackGroups: TrackGroupArray,
            trackSelections: TrackSelectionArray
        ) {
        }

        override fun onLoadingChanged(isLoading: Boolean) {
            //TODO: Please refer to the ExoPlayer guide.
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_ENDED -> player!!.playWhenReady = false
                Player.STATE_READY -> hideProgressBar()
                Player.STATE_BUFFERING -> showProgressBar()
                Player.STATE_IDLE -> {}
            }
        }

        private fun showProgressBar() {
            if (progressBar != null) {
                progressBar!!.visibility = View.VISIBLE
            }
        }

        private fun hideProgressBar() {
            progressBar!!.visibility = View.GONE
        }

        override fun onRepeatModeChanged(repeatMode: Int) {}
        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
        override fun onPlayerError(e: ExoPlaybackException) {
            android.util.Log.d("onPlayerError", "onPlayerError: " + e.message)
            e.printStackTrace()
        }

        /*************************end code on 25-12-2020 */
        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
        override fun onSeekProcessed() {}
    }

    private fun buildMediaSource(uri: Uri, factory: DataSource.Factory): MediaSource {
        val type = Util.inferContentType(uri.lastPathSegment)
        when (type) {
            C.TYPE_DASH -> return DashMediaSource.Factory(factory)
                .createMediaSource(uri)
            C.TYPE_HLS -> return HlsMediaSource.Factory(factory).createMediaSource(uri)
            C.TYPE_OTHER -> return ProgressiveMediaSource.Factory(factory)
                .createMediaSource(uri)
            C.TYPE_SS -> return SsMediaSource.Factory(factory).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $type")
        }
    }


    fun buildDataSourceFactory(listener: TransferListener?): DataSource.Factory {
        var dataSourceFactory: DefaultDataSourceFactory? = null
        dataSourceFactory =
            DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener))
        return dataSourceFactory
    }

    fun buildHttpDataSourceFactory(listener: TransferListener?): HttpDataSource.Factory {
        return DefaultHttpDataSourceFactory(userAgent, listener)
    }

    override fun onResume() {
        super.onResume()
        if (token.isNullOrEmpty()) {
            Loadyoutubeurl()

        } else {
            // LoadVideoEncryoted();
            limmedia()
        }

    }

    private fun LoadVideoEncryoted() {
        val aesDataSource = AesDataSource(getCipher()!!)
        val factory = DataSource.Factory { aesDataSource }

        mediaSource = buildMediaSource(
            Uri.fromFile(
                File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/.Videos/",
                    "$video_play.mp4"
                )
            ), factory
        )
        player = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(),
            DefaultLoadControl()
        )
        player!!.addListener(playerEventListener)
        simpleExoPlayerView!!.setPlayer(player)
        player!!.setPlayWhenReady(true)
        player!!.prepare(mediaSource)
        player!!.seekTo((current_pos)!!)
        try {
            val userHistroyTable = UserHistroyTable()
            userHistroyTable.video_id = video_id
            userHistroyTable.video_name = video_name
            userHistroyTable.type = "Video"
            userHistroyTable.user_id = MakeMyExam.userId
            userHistroyTable.course_id = course_id
            userHistroyTable.valid_to = valid_to
            userHistroyTable.current_time = "" + MakeMyExam.getTime_server()
            utkashRoom!!.getuserhistorydao().addUser(userHistroyTable)
            /* val mediaSessionCompat = MediaSessionCompat(this, "exoplayer")
             val mediaSessionConnector = MediaSessionConnector(mediaSessionCompat)
             mediaSessionConnector.setPlayer(player)*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun limmedia() {
        var path = ""
        val mServer = LocalSingleHttpServer();
        val cipher = getCipher()
        mServer.setCipher(cipher)
        mServer.start()

        path = mServer.getURL(
            File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/.Videos/",
                "$video_play.mp4"
            ).toString()
        )
        val factory = DefaultHttpDataSourceFactory("exoplayer_video")
        val extractorsFactory = DefaultExtractorsFactory()
        val adaptiveTrackSelectionFactory: TrackSelection.Factory =
            AdaptiveTrackSelection.Factory(BANDWIDTH_METER)
        trackSelector = DefaultTrackSelector(adaptiveTrackSelectionFactory)

        mediaSource = ExtractorMediaSource(Uri.parse(path), factory, extractorsFactory, null, null)
        player = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            trackSelector,
            DefaultLoadControl()
        )
        player!!.addListener(playerEventListener)
        simpleExoPlayerView!!.setPlayer(player)
        player!!.setPlayWhenReady(true)
        player!!.prepare(mediaSource)
        player!!.seekTo((current_pos)!!)
        val sppedlist = Helper.speedlist()

        if (sppedlist.get(speedpostiion).getTitle()
                .equals("Normal", ignoreCase = true)
        ) {
            player!!.playbackParameters = PlaybackParameters("1".toFloat(), 1f)
        } else player!!.playbackParameters = PlaybackParameters(
            sppedlist.get(
                speedpostiion
            ).title.replace("x", "").toFloat(), 1f
        )
        //earphone()
    }


    override fun onBackPressed() {
        val orientation1 = resources.configuration.orientation
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            mFullScreenIcon!!.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.fullscreen
                )
            )
        } else {
            releasePlayer()
            super.onBackPressed()
        }
    }

    fun releasePlayer() {
        if (player != null) {
            utkashRoom!!.getvideoDownloadao().update_videocurrenttime(
                video_id,
                MakeMyExam.userId,
                player!!.currentPosition,
                video_time
            )
            utkashRoom = null
            player!!.stop()
            player!!.clearVideoSurface()
            player!!.removeListener(playerEventListener)
            simpleExoPlayerView!!.player.release()
            player!!.release()
            player!!.release()
            player = null
            trackSelector = null
        }
    }

    override fun onPause() {
        super.onPause()
        if (player != null) {
            current_pos = player!!.currentPosition
            utkashRoom!!.getvideoDownloadao().update_videocurrenttime(
                video_id,
                MakeMyExam.userId,
                player!!.currentPosition,
                video_time
            )
            player!!.release()
            player = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    @Throws(GeneralSecurityException::class)
    fun getCipher(): Cipher? {
        val iv = AES.generateLibVectorAPI(token)
        val AesKeyData = AES.generateLibkeyAPI(token).toByteArray()
        val InitializationVectorData = iv.toByteArray()
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val keySpec = SecretKeySpec(AesKeyData, "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, IvParameterSpec(InitializationVectorData))
        return cipher
    }

    companion object {
        private val LOCAL_ENCRYPTION_KEY = "abcdefgh123456yz"
    }

    override fun selectSpeedPos(pos: Int) {
        speedpostiion = pos

        if (sppedlist[speedpostiion].title.equals("Normal", ignoreCase = true)) {
            player!!.playbackParameters = PlaybackParameters("1".toFloat(), 1f)
        } else
            player!!.playbackParameters = PlaybackParameters(sppedlist.get(speedpostiion).title.replace("x", "").toFloat(), 1f)


        for (i in sppedlist.indices) {
            if (i == speedpostiion) {
                sppedlist[speedpostiion].isSelected = true
            }
        }

        if (popupWindow != null && popupWindow!!.isShowing) {
            Handler().postDelayed({ popupWindow!!.dismiss() }, 250)
        }

    }



}