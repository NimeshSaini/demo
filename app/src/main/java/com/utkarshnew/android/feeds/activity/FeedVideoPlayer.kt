package com.utkarshnew.android.feeds.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.Size
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.DefaultEventListener
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.utkarshnew.android.JWextractor.CustomMediaPlayerConfig
import com.utkarshnew.android.JWextractor.SelectSpeedo
import com.utkarshnew.android.JWextractor.SpeedoAdapter
import com.utkarshnew.android.Player.Liveawsactivity
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.feeds.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.math.roundToInt


class FeedVideoPlayer : AppCompatActivity(), Html.ImageGetter, SelectSpeedo {
    var exoPlayer: SimpleExoPlayerView? = null
    var url = ""
    var des: String? = null
    val playPosition: Long = 0

    var userAgent: String? = null
    var mediaSource: MediaSource? = null

    var player: SimpleExoPlayer? = null
    var loadControl: DefaultLoadControl? = null
    var progressBar: ProgressBar? = null
    var data_layout: RelativeLayout? = null
    private var newOrientation = 0
    var exo_ffwd: ImageView? = null
    var exo_rew: ImageView? = null

    var htmldata: Spanned? = null
    var speedx = ""
    lateinit var descritption: TextView
    lateinit var readmore: TextView
    private var savedOrientation = 0

    private fun isPortrait(orientation: Int): Boolean {
        Log.d("prince", "" + orientation)
        return orientation < 85 || orientation > 100
    }

    private var trackSelectorParameters: DefaultTrackSelector.Parameters? = null
    var trackSelector: DefaultTrackSelector? = null
    private var mFullScreenButton: FrameLayout? = null
    var spped_title = ""
    private var mFullScreenDialog: Dialog? = null

    private var mExoPlayerFullscreen = false

    private var mFullScreenIcon: ImageView? = null
    private var powerMenu: PowerMenu? = null
    private var tvGoLive: TextView? = null
    private var speedTV: TextView? = null
    var quality: ImageView? = null
    val sppedlist = Helper.speedlist()
    var popupWindow: PopupWindow? = null
    var speedoAdapter:SpeedoAdapter?=null
    var speedpostiion = 3
    var icon: ImageView? = null
    var rootView: ConstraintLayout? = null
    var view_type: String? = "1"


    val BANDWIDTH_METER = DefaultBandwidthMeter()
    private var mediaDataSourceFactory: DataSource.Factory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_video_player)
        Helper.enableScreenShot(this@FeedVideoPlayer)

        try {
            progressBar = findViewById(R.id.progress_bar)
            data_layout = findViewById(R.id.data_layout)
            descritption = findViewById(R.id.descritption)
            readmore = findViewById(R.id.read_more)
            rootView = findViewById(R.id.root_new)

            progressBar!!.visibility = View.VISIBLE

            exoPlayer = findViewById(R.id.exoplayer)
            url = intent.getStringExtra("url")!!
            des = intent.getStringExtra("des")

            intent.getStringExtra("view_type")?.let {
                view_type = it
            }


            des?.let {
                descriptionCheck(it)
            }



            userAgent = Util.getUserAgent(this, "ExoPlayerDemo")

            trackSelectorParameters = ParametersBuilder().build()
            initFullscreenButton()
            initFullscreenDialog()







            if (view_type.equals("2")) {
                mFullScreenButton!!.visibility = View.GONE
                (exoPlayer?.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "9:16"

            } else {
                try {
                    val orientationEventListener: OrientationEventListener =
                        object : OrientationEventListener(applicationContext) {
                            override fun onOrientationChanged(orientation: Int) {
                                try {
                                    val result = Settings.System.getInt(
                                        contentResolver,
                                        Settings.System.ACCELEROMETER_ROTATION,
                                        0
                                    )
                                    if (result == 1) {
                                        val isPortrait: Boolean = isPortrait(orientation)
                                        if (!isPortrait && savedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                                            savedOrientation =
                                                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                            requestedOrientation =
                                                ActivityInfo.SCREEN_ORIENTATION_USER
                                        } else if (isPortrait && savedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                                            savedOrientation =
                                                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                            requestedOrientation =
                                                ActivityInfo.SCREEN_ORIENTATION_USER
                                        }
                                    }
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    orientationEventListener.enable()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }


            loadControl = DefaultLoadControl.Builder()
                .setAllocator(DefaultAllocator(true, 16))
                .setBufferDurationsMs(
                    CustomMediaPlayerConfig.MAX_BUFFER_DURATION,
                    CustomMediaPlayerConfig.MAX_BUFFER_DURATION,
                    CustomMediaPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    CustomMediaPlayerConfig.MIN_PLAYBACK_START_BUFFER
                )
                .setTargetBufferBytes(-1)
                .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl()

            mediaDataSourceFactory = buildDataSourceFactory(false)


            val videouri = Uri.parse(url)
            player = ExoPlayerFactory.newSimpleInstance(
                this,
                DefaultRenderersFactory(this),
                trackSelector,
                loadControl
            )
            player!!.addListener(playerEventListener)
            exoPlayer!!.setPlayer(player)
            player!!.setPlayWhenReady(true)
            mediaSource = buildMediaSource(videouri, null)
            player!!.prepare(mediaSource)
            player!!.seekTo(playPosition)
           /* val audioManager: AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if (audioManager.isWiredHeadsetOn) {
                val mediaSessionCompat = MediaSessionCompat(this, "exoplayer")
                val mediaSessionConnector1 = MediaSessionConnector(mediaSessionCompat)
                mediaSessionConnector1.setPlayer(player)
            }else{
                val mediaSession = MediaSessionCompat(this, "exoplayer")
                val mediaSessionConnector = MediaSessionConnector(mediaSession)
                mediaSessionConnector.setMediaButtonEventHandler { player1: Player?, controlDispatcher: ControlDispatcher?, mediaButtonEvent: Intent ->
                    val event =
                        mediaButtonEvent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
                    if (event!!.action == KeyEvent.ACTION_UP) {
                        when (event.keyCode) {
                            KeyEvent.KEYCODE_MEDIA_PAUSE ->{
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
                mediaSessionConnector.setPlayer(player)
            }*/

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    fun descriptionCheck(it: String) {
        if (it.isEmpty()) {
            descritption.visibility = View.GONE
        } else {
            descritption.visibility = View.VISIBLE
            data_layout!!.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                htmldata = Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY, this@FeedVideoPlayer, null)
            } else {
                htmldata = Html.fromHtml(it)
            }
            htmldata?.let {
                if (htmldata!!.length > 200) {
                    descritption.visibility = View.VISIBLE
                    descritption.text = htmldata!!.substring(0, 200) + "..."
                    readmore.visibility = View.VISIBLE
                } else {
                    descritption.text = htmldata
                    readmore.visibility = View.GONE
                }
            }

            descritption.movementMethod = LinkMovementMethod.getInstance();

            readmore.setOnClickListener {
                if (readmore.text.equals("Read More")) {
                    descritption.text = htmldata
                    readmore.text = "Read Less"
                } else {
                    descritption.text = htmldata!!.substring(0, 200) + "..."
                    readmore.text = "Read More"
                }
            }
        }
    }

    private val playerEventListener: Player.EventListener = object : DefaultEventListener() {
        override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {}
        override fun onTracksChanged(
            trackGroups: TrackGroupArray,
            trackSelections: TrackSelectionArray
        ) {

        }

        override fun onLoadingChanged(isLoading: Boolean) {}
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_READY) {
                progressBar!!.setVisibility(View.GONE)
            }
        }

        override fun onRepeatModeChanged(repeatMode: Int) {}
        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
        override fun onPlayerError(error: ExoPlaybackException) {
            progressBar!!.setVisibility(View.GONE)
        }

        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
        override fun onSeekProcessed() {}
    }

    private fun initFullscreenButton() {
        newOrientation = resources.configuration.orientation
        val controlView: PlayerControlView = exoPlayer!!.findViewById(R.id.exo_controller)
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon)
        val adaptiveTrackSelectionFactory: TrackSelection.Factory =
            AdaptiveTrackSelection.Factory(BANDWIDTH_METER)
        trackSelector = DefaultTrackSelector(adaptiveTrackSelectionFactory)
        trackSelector!!.setParameters(trackSelectorParameters)
        speedTV = controlView.findViewById(R.id.speedTV)

        exo_ffwd = controlView.findViewById(R.id.exo_ffwd_new)
        exo_rew = controlView.findViewById(R.id.exo_rew_new)


        exo_ffwd?.setOnClickListener { view: View? ->
            if (player!!.currentPosition < (player!!.duration - 10000)) player!!.seekTo(
                player!!.currentPosition + 10000
            )
        }
        exo_rew?.setOnClickListener { view: View? ->
            if (player!!.currentPosition > 10000) player!!.seekTo(
                player!!.currentPosition - 10000
            )
        }

        speedTV!!.visibility = View.GONE
        tvGoLive = controlView.findViewById(R.id.tv_go_live)
        quality = controlView.findViewById(R.id.quality)
        icon = controlView.findViewById(R.id.icon)
        icon!!.visibility = View.GONE

        quality!!.setOnClickListener {
            popupWindowPart();
            val location = IntArray(2)
            quality!!.getLocationInWindow(location)
            val size = Size(popupWindow!!.contentView.measuredWidth, popupWindow!!.contentView.height)

            if (newOrientation == Configuration.ORIENTATION_PORTRAIT) {
                popupWindow!!.showAsDropDown(quality, 0, (- quality!!.height*7.0).roundToInt(), Gravity.RIGHT)
            } else {
                popupWindow!!.showAtLocation(
                    window.decorView,
                    Gravity.TOP or Gravity.RIGHT,
                    0,
                    ((location[1] + size.height) / 2.8).toInt()
                )
            }
        }



        if (speedTV != null) {
            speedTV!!.setText("Normal")
            speedTV!!.setOnClickListener({ v: View? -> showSpeedOptions() })
        }

        if (!TextUtils.isEmpty(speedx)) {
            player!!.setPlaybackParameters(
                PlaybackParameters(
                    java.lang.Float.valueOf(
                        speedx.replace(
                            "x",
                            ""
                        )
                    ), 1f
                )
            )
        }
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button)

        mFullScreenButton!!.setOnClickListener(View.OnClickListener { if (!mExoPlayerFullscreen) openFullscreenDialog() else closeFullscreenDialog() })
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
        speedoAdapter = SpeedoAdapter(this@FeedVideoPlayer, sppedlist, this)
        Objects.requireNonNull(speed_recyclerview).adapter = speedoAdapter

        popupWindow!!.isFocusable = true
        popupWindow!!.setBackgroundDrawable(/* background = */ resources.getDrawable(R.drawable.background_rectangle_gray))
        popupWindow!!.width = width - 50
        popupWindow!!.showAtLocation(view, Gravity.RIGHT, 0, 0)
        popupWindow!!.contentView = view
    }



    private fun showSpeedOptions() {
        val popupMenu = PopupMenu(this, icon, R.style.MyPopupMenu)
        val menu = popupMenu.menu
        val speeds = resources.getStringArray(R.array.speed_values)
        if (speeds.size != 0) {
            for (speed in speeds) {
                if (speed.equals("1", ignoreCase = true)) {
                    menu.add("Normal")
                } else {
                    menu.add(speed + "x")
                }
            }
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                spped_title = item.title.toString()
                if (player != null) {
                    if (spped_title.equals("Normal", ignoreCase = true)) {
                        player!!.setPlaybackParameters(
                            PlaybackParameters(
                                java.lang.Float.valueOf("1"),
                                1f
                            )
                        )
                    } else {
                        player!!.setPlaybackParameters(
                            PlaybackParameters(
                                java.lang.Float.valueOf(
                                    spped_title.replace("x", "")
                                ), 1f
                            )
                        )
                    }
                }
                false
            }
            popupMenu.show()
        }
    }

    private val onIconMenuItemClickListener =
        OnMenuItemClickListener<PowerMenuItem> { position, item ->
            showSpeedOptions()
            powerMenu!!.dismiss()
        }


    private fun openFullscreenDialog() {
        mFullScreenIcon!!.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.fullscreenexit
            )
        )
        mExoPlayerFullscreen = true
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (newOrientation == Configuration.ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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

    private fun initFullscreenDialog() {
        mFullScreenDialog =
            object : Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
                override fun onBackPressed() {
                    if (mExoPlayerFullscreen) closeFullscreenDialog()
                    super.onBackPressed()
                }
            }
    }

    private fun buildMediaSource(uri: Uri, overrideExtension: String?): MediaSource? {
        @C.ContentType val type = Util.inferContentType(uri, overrideExtension)
        return when (type) {
            C.TYPE_DASH -> DashMediaSource.Factory(
                DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                buildDataSourceFactory(false)
            ).createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(
                DefaultSsChunkSource.Factory(mediaDataSourceFactory), buildDataSourceFactory(false)
            ).createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(uri)
            else -> {
                throw IllegalStateException("Unsupported type: $type")
            }
        }
    }

    private fun buildDataSourceFactory(useBandwidthMeter: Boolean): DataSource.Factory? {
        return buildDataSourceFactory(if (useBandwidthMeter) BANDWIDTH_METER else null)
    }

    fun buildDataSourceFactory(listener: TransferListener?): DataSource.Factory? {
        var dataSourceFactory: DefaultDataSourceFactory? = null
        dataSourceFactory =
            DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener))
        return dataSourceFactory
    }

    fun buildHttpDataSourceFactory(listener: TransferListener?): HttpDataSource.Factory? {
        return DefaultHttpDataSourceFactory(userAgent, listener)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        try {
            newOrientation = newConfig.orientation
            if (newConfig.orientation == 1) {

                mExoPlayerFullscreen = false
                mFullScreenIcon!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.fullscreen
                    )
                )

                htmldata?.let {
                    if (htmldata!!.isNotEmpty()) {
                        data_layout!!.isVisible = true

                    }
                }

                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                exoPlayer?.layoutParams =
                    ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 650)
            } else {
                mExoPlayerFullscreen = true
                mFullScreenIcon!!.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.fullscreenexit)
                )
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )

                data_layout!!.isVisible = false


                exoPlayer?.layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        val orientation1 = resources.configuration.orientation
        if (orientation1 == Configuration.ORIENTATION_LANDSCAPE) {
            closeFullscreenDialog()
        } else {
            if (player != null) {
                player!!.removeListener(playerEventListener)
                player!!.release()
                player = null
            }
            super.onBackPressed()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(s: String?): Drawable? {
        val d = LevelListDrawable()
        val empty = getResources().getDrawable(R.mipmap.course_placeholder)
        d.addLevel(0, 0, empty)
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val `is` = URL(s).openStream()
                val bitmap = BitmapFactory.decodeStream(`is`)
                if (bitmap != null) {
                    val bitmapDrawable = BitmapDrawable(bitmap)
                    d.addLevel(1, 1, bitmapDrawable)
                    d.setBounds(0, 0, bitmap.width, bitmap.height)
                    d.level = 1
                    withContext(Dispatchers.Main)
                    {
                        val t = descritption!!.text
                        descritption!!.text = t
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return d
    }



    override fun selectSpeedPos(pos: Int) {
        speedpostiion = pos

        if (sppedlist[speedpostiion].title.equals("Normal", ignoreCase = true)) {
            player!!.playbackParameters = PlaybackParameters("1".toFloat(), 1f)
        } else
            player!!.playbackParameters = PlaybackParameters(sppedlist.get(speedpostiion).title.replace("x", "").toFloat(), 1f)

        if (sppedlist.get(speedpostiion).title.equals("3x"))
            showToast("if internet speed is not proper then video may buffer")

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