package com.utkarshnew.android.feeds.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.utkarshnew.android.EncryptionModel.EncryptionData
import com.utkarshnew.android.LiveClass.Activity.LiveClassActivity
import com.utkarshnew.android.LiveTest.Activity.LivetestActivity
import com.utkarshnew.android.OnSingleClickListener
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.*
import com.utkarshnew.android.Utils.Network.API
import com.utkarshnew.android.Utils.Network.APIInterface
import com.utkarshnew.android.Utils.Network.NetworkCall
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse
import com.utkarshnew.android.courses.Activity.QuizActivity
import com.utkarshnew.android.databinding.*
import com.utkarshnew.android.feeds.OptionItem
import com.utkarshnew.android.feeds.activity.FeedVideoPlayer
import com.utkarshnew.android.feeds.activity.FeedsActivity
import com.utkarshnew.android.feeds.activity.PinnedPostActivity
import com.utkarshnew.android.feeds.dataclass.Data
import com.utkarshnew.android.feeds.dataclass.Option
import com.utkarshnew.android.testmodule.activity.TestBaseActivity
import com.utkarshnew.android.testmodule.model.InstructionData
import com.utkarshnew.android.testmodule.model.TestBasicInst
import com.utkarshnew.android.testmodule.model.TestSectionInst
import com.utkarshnew.android.testmodule.model.TestseriesBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import java.io.FileNotFoundException
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class FeedAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Html.ImageGetter,
    NetworkCall.MyNetworkCallBack, OptionItem {
    var feedatalist: ArrayList<Data> = ArrayList()
    var networkCall: NetworkCall? = null
    var item_pos = 0
    var lang = 0
    var comment_txt = ""
    var commentAdapter: CommentAdapter? = null
    var option_index = 0
    var booleanlike = false

    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500
    val PERIOD_MS: Long = 3000
    var textView: TextView? = null
    var comment_recyerler: RecyclerView? = null
    val commentlist: ArrayList<com.utkarshnew.android.feeds.dataclass.comment.Data> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1089 -> {
                val binding =
                    BannerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return Banner_Vm(binding)
            }
            1090 -> {
                val binding = NewCourseVmBinding.inflate(LayoutInflater.from(parent.context))
                return NewCourseVm(binding)
            }
            1091 -> {
                val binding = NewTestresultVmBinding.inflate(LayoutInflater.from(parent.context))
                return NewTestResultVm(binding)
            }
            1092 -> {
                val binding = LiveTestVmBinding.inflate(LayoutInflater.from(parent.context))
                return NewLiveTestVm(binding)
            }
            1093 -> {
                val binding = LiveClassVmBinding.inflate(LayoutInflater.from(parent.context))
                return NewLiveclassVm(binding)
            }
            1 -> {
                val binding = ArticleVmBinding.inflate(LayoutInflater.from(parent.context))
                return ArticleVm(binding)
            }
            2 -> {
                val binding = PostImageBinding.inflate(LayoutInflater.from(parent.context))
                return ImageVm(binding)
            }
            3 -> {
                val videoPostBinding =
                    (VideoPostBinding.inflate(LayoutInflater.from(parent.context)))
                return VideoVm(videoPostBinding)
            }
            4 -> {
                val audioPostBinding =
                    (AudioPostBinding.inflate(LayoutInflater.from(parent.context)))
                return AudioVM(audioPostBinding)
            }
            5 -> {
                val linkViewBinding = (LinkViewBinding.inflate(LayoutInflater.from(parent.context)))
                return LinkVM(linkViewBinding)
            }
            6, 8 -> {
                val questionViewBinding =
                    (QuestionViewBinding.inflate(LayoutInflater.from(parent.context)))
                return QuestionVM(questionViewBinding)
            }

            7 -> {
                val questionViewBinding =
                    (QuizViewBinding.inflate(LayoutInflater.from(parent.context)))
                return QuizVM(questionViewBinding)
            }
            else -> {
                val binding = ArticleVmBinding.inflate(LayoutInflater.from(parent.context))
                return ArticleVm(binding)
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder: " + position)
        when (getItemViewType(position)) {
            1089 -> {
                val bannerVm = holder as Banner_Vm
                bannerVm.bind(feedatalist[position])
            }
            1090 -> {
                val courseVm = holder as NewCourseVm
                courseVm.bind(feedatalist[position])
            }
            1091 -> {
                val testResultVm = holder as NewTestResultVm
                testResultVm.bind(feedatalist[position])
            }
            1092 -> {
                val liveTestVm = holder as NewLiveTestVm
                liveTestVm.bind(feedatalist[position])
            }
            1093 -> {
                val liveclassVm = holder as NewLiveclassVm
                liveclassVm.bind(feedatalist[position])
            }
            1 -> {
                val articleVm = holder as ArticleVm
                articleVm.bind(feedatalist[position])
            }
            2 -> {
                val imageVm = holder as ImageVm
                imageVm.bind(feedatalist[position])
            }
            3 -> {
                val videoVm = holder as VideoVm
                videoVm.bind(feedatalist[position])
            }
            4 -> {
                val audioVM = holder as AudioVM
                audioVM.bind(feedatalist[position])
            }
            5 -> {
                val linkvm = holder as LinkVM
                linkvm.bind(feedatalist[position])
            }
            6, 8 -> {
                val questionVM = holder as QuestionVM
                questionVM.bind(feedatalist[position])
            }
            7 -> {
                val quizVM = holder as QuizVM
                quizVM.bind(feedatalist[position])
            }
        }
    }

    private fun createBodyData(type: String) {
        try {
            val masterdataencryptionData = EncryptionData()
            var result = ""
            when (type) {
                "Like" -> {

                    val like_unlike = if (feedatalist[item_pos].my_like == "1") {
                        "0"
                    } else {
                        "1"
                    }
                    masterdataencryptionData.my_like = like_unlike
                    masterdataencryptionData.post_id = feedatalist[item_pos].id
                    result = Gson().toJson(masterdataencryptionData)
                }
                "GetComment" -> {
                    masterdataencryptionData.post_id = feedatalist[item_pos].id
                    masterdataencryptionData.parent_id = "0"
                    result = Gson().toJson(masterdataencryptionData)
                }
                "AddComment" -> {
                    masterdataencryptionData.id = ""
                    masterdataencryptionData.parent_id = "0"
                    masterdataencryptionData.post_id = feedatalist[item_pos].id
                    masterdataencryptionData.comment = comment_txt
                    result = Gson().toJson(masterdataencryptionData)
                }
                "Attempt Mcq" -> {
                    masterdataencryptionData.post_id = feedatalist[item_pos].id
                    masterdataencryptionData.index = (option_index + 1).toString()
                    result = Gson().toJson(masterdataencryptionData)
                }
                "Pin" -> {
                    masterdataencryptionData.post_id = feedatalist[item_pos].id
                    masterdataencryptionData.post_pin = "1"
                    result = Gson().toJson(masterdataencryptionData)
                }
                "Unpin" -> {
                    masterdataencryptionData.post_id = feedatalist[item_pos].id
                    masterdataencryptionData.post_pin = "0"
                    result = Gson().toJson(masterdataencryptionData)
                }
                else -> {

                }
            }
            val masterdatadoseStrScr = AES.encrypt(result)
            if (context is FeedsActivity) {
                (context as FeedsActivity).feedViewModel.type.value = type
                (context as FeedsActivity).feedViewModel.adapter_bodydata.value =
                    masterdatadoseStrScr
            } else if (context is PinnedPostActivity) {
                (context as PinnedPostActivity).feedViewModel.type.value = type
                (context as PinnedPostActivity).feedViewModel.adapter_bodydata.value =
                    masterdatadoseStrScr
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    fun open_comment_layout(context: Context, position: Int) {
        try {
            val watchlist = BottomSheetDialog(context, R.style.videosheetDialogTheme)
            watchlist.setContentView(R.layout.comment_layout)
            watchlist.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
            watchlist.setCancelable(false)
            watchlist.setCanceledOnTouchOutside(true)
            val bottomSheet =
                watchlist.findViewById<View>(R.id.design_bottom_sheet)!! as FrameLayout

            BottomSheetBehavior.from<View>(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from<View>(bottomSheet).isDraggable = false
            comment_recyerler = watchlist.findViewById(R.id.comment_recyerler)
            val et_message = watchlist.findViewById<EditText>(R.id.et_message)
            val iv_send = watchlist.findViewById<ImageView>(R.id.iv_send)

            commentAdapter = CommentAdapter(context, commentlist)

            val llm = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            comment_recyerler?.apply {
                adapter = commentAdapter
                llm.also {
                    it.stackFromEnd = true
                    it.isAutoMeasureEnabled = true
                    this.layoutManager = it
                }
            }
            iv_send!!.setOnClickListener {
                comment_txt = et_message!!.text.toString().trim()
                if (comment_txt.isNotEmpty() && !TextUtils.isEmpty(comment_txt)) {
                    item_pos = position
                    et_message.text.clear()
                    createBodyData("AddComment")
                } else {
                    Toast.makeText(context, "Comment Should not be blank", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            if (!watchlist.isShowing) {
                watchlist.show()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun makeLinks(textView: TextView, link: String) {
        val spannableString = SpannableString(textView.text)
        val startIndexOfLink = textView.text.toString().indexOf(link)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(
            boldSpan,
            0,
            startIndexOfLink + link.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(context!!.getColor(R.color.link_color)),
            startIndexOfLink,
            startIndexOfLink + link.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.setText(spannableString, TextView.BufferType.SPANNABLE)
    }


    override fun getItemViewType(position: Int): Int {
        if (feedatalist[position].post_type.equals("1089")) {
            return 1089
        } else if (feedatalist[position].post_type.equals("1090")) {
            return 1090
        } else if (feedatalist[position].post_type.equals("1091")) {
            return 1091
        } else if (feedatalist[position].post_type.equals("1092")) {
            return 1092
        } else if (feedatalist[position].post_type.equals("1093")) {
            return 1093
        } else if (feedatalist[position].post_type.equals("1")) {
            return 1
        } else if (feedatalist[position].post_type.equals("2")) {
            return 2
        } else if (feedatalist[position].post_type.equals("3")) {
            return 3
        } else if (feedatalist[position].post_type.equals("4")) {
            return 4
        } else if (feedatalist[position].post_type.equals("5")) {
            return 5
        } else if (feedatalist[position].post_type.equals("6")) {
            return 6
        } else if (feedatalist[position].post_type.equals("8")) {
            return 8
        } else if (feedatalist[position].post_type.equals("7")) {
            return 7
        } else {
            return 0
        }
    }


    override fun getItemCount(): Int {
        Log.d("itemcount", "getItemCount: " + feedatalist.size)
        return feedatalist.size
    }

    inner class NewCourseVm(private val newCourseVmBinding: NewCourseVmBinding) :
        RecyclerView.ViewHolder(newCourseVmBinding.root) {
        fun bind(data: Data) {
            with(newCourseVmBinding)
            {
                coursedata = data
            }
        }
    }

    inner class Banner_Vm(private val bannerViewBinding: BannerViewBinding) :
        RecyclerView.ViewHolder(bannerViewBinding.root) {
        fun bind(data: Data) {
            bannerViewBinding.bannerdata = data
            try {
                if (data.bannerlist!!.size > 1) {
                    if (timer != null) {
                        timer!!.cancel()
                        timer!!.purge()
                        currentPage = 0
                    }

                    val handler = Handler()
                    val Update = Runnable {
                        if (currentPage == data.bannerlist!!.size) {
                            currentPage = 0
                        }
                        Log.d("abcdee", "$currentPage")
                        bannerViewBinding.viewPager.setCurrentItem(currentPage++, true)
                    }
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(Update)
                        }
                    }, DELAY_MS, PERIOD_MS)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

    inner class NewTestResultVm(private val newlivetestresult: NewTestresultVmBinding) :
        RecyclerView.ViewHolder(newlivetestresult.root) {
        fun bind(testResult: Data) {
            with(newlivetestresult)
            {
                livetestresult = testResult
            }
        }
    }


    inner class NewLiveTestVm(private var liveTestVmBinding: LiveTestVmBinding) :
        RecyclerView.ViewHolder(liveTestVmBinding.root) {
        fun bind(data: Data) {
            liveTestVmBinding.apply {
                this.livetest = data
                feedadapter = this@FeedAdapter
            }
        }

    }

    inner class NewLiveclassVm(private val liveClassVmBinding: LiveClassVmBinding) :
        RecyclerView.ViewHolder(liveClassVmBinding.root) {
        fun bind(data: Data) {
            with(liveClassVmBinding)
            {
                liveclass = data
                feedadapter = this@FeedAdapter
            }
        }

    }

    inner class ArticleVm(private val articleVm: ArticleVmBinding) :
        RecyclerView.ViewHolder(articleVm.root) {
        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun bind(data: Data) {
            articleVm.articlebind = data

            if (data.text.isEmpty()) {
                articleVm.articleTxtTop.visibility = View.GONE
            } else {
                articleVm.articleTxtTop.visibility = View.VISIBLE
                articleVm.articleTxtTop.text = data.text
            }

            textView = articleVm.articleTxt
            val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data.meta_url, Html.FROM_HTML_MODE_LEGACY, this@FeedAdapter, null)
            } else {
                Html.fromHtml(data.meta_url)
            }

            if (htmldata.length > 200) {
                articleVm.articleTxt.text = htmldata.substring(0, 200) + "..."
                articleVm.readMore.visibility = View.VISIBLE
                articleVm.readMore.text = "Read More"
            } else {
                articleVm.articleTxt.text = htmldata
                articleVm.readMore.visibility = View.GONE
            }
            articleVm.articleTxt.movementMethod = LinkMovementMethod.getInstance()

            articleVm.postComment.setOnClickListener {
                if (feedatalist[adapterPosition].is_comment_enable == "1") {
                    item_pos = adapterPosition
                    Log.d("pos", "bind: $item_pos")
                    commentlist.clear()
                    createBodyData("GetComment")

                    open_comment_layout(context!!, adapterPosition)
                } else {
                    Toast.makeText(context, "Comment is disabled for this post", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            /*    articleVm.like.setOnClickListener {
                    item_pos = adapterPosition
                    Log.d("pos", "bind: $item_pos")

                    createBodyData("Like")
                }
    */
            articleVm.like.setOnClickListener(OnSingleClickListener {
                if (!booleanlike) {
                    item_pos = adapterPosition
                    createBodyData("Like")
                }

            })


            articleVm.pinIV.setOnClickListener {
                item_pos = adapterPosition
                Log.d("pos", "bind: $item_pos")
                if (data.my_pinned == "1") {
                    articleVm.pinIV.setImageResource(R.drawable.unpinned)
                    createBodyData("Unpin")
                } else {
                    articleVm.pinIV.setImageResource(R.drawable.pinned)
                    createBodyData("Pin")
                }
            }
            articleVm.postShare.setOnClickListener {
                item_pos = adapterPosition
                Helper.sharePost(context as Activity, feedatalist[item_pos].id, "", "Article")
            }

            articleVm.readMore.setOnClickListener {
                if (articleVm.readMore.text.equals("Read More")) {
                    articleVm.articleTxt.text = htmldata
                    articleVm.readMore.text = "Read Less"
                } else {
                    articleVm.articleTxt.text = htmldata.substring(0, 200) + "..."
                    articleVm.readMore.text = "Read More"
                }
            }
        }

    }

    inner class LinkVM(private val linkvm: LinkViewBinding) : RecyclerView.ViewHolder(linkvm.root) {
        fun bind(data: Data) {
            linkvm.linkbind = data
            if (data.meta_url.isEmpty()) {
                linkvm.linkTxt.isEnabled = false
                linkvm.linkTxt.visibility = View.INVISIBLE
            } else {
                linkvm.linkTxt.isEnabled = true
                linkvm.linkTxt.visibility = View.VISIBLE
                linkvm.linkTxt.text = data.meta_url
                makeLinks(linkvm.linkTxt, data.meta_url)
            }

            linkvm.linkTxt.setOnClickListener {
                if (Helper.isValidUrl(feedatalist[adapterPosition].meta_url)) {
                    val data = feedatalist[adapterPosition]
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.meta_url))
                    Helper.gotoActivity(browserIntent, context!! as Activity)
                } else {
                    Toast.makeText(context!! as Activity, "Invalid Url", Toast.LENGTH_SHORT).show()
                }

            }
            linkvm.postShare.setOnClickListener {
                item_pos = adapterPosition
                Helper.sharePost(context as Activity, feedatalist[item_pos].id, "", "Link")
            }

            /* linkvm.like.setOnClickListener {
                 item_pos = adapterPosition
                 createBodyData("Like")
             }*/
            linkvm.like.setOnClickListener(OnSingleClickListener {
                if (!booleanlike) {
                    item_pos = adapterPosition
                    createBodyData("Like")
                }


            })
            linkvm.pinIV.setOnClickListener {
                item_pos = adapterPosition
                Log.d("pos", "bind: $item_pos")
                if (data.my_pinned == "1") {
                    linkvm.pinIV.setImageResource(R.drawable.unpinned)
                    createBodyData("Unpin")
                } else {
                    linkvm.pinIV.setImageResource(R.drawable.pinned)
                    createBodyData("Pin")
                }
            }


            linkvm.postComment.setOnClickListener {
                if (feedatalist[adapterPosition].is_comment_enable == "1") {
                    item_pos = adapterPosition
                    commentlist.clear()
                    createBodyData("GetComment")
                    open_comment_layout(context!!, adapterPosition)
                } else {
                    Toast.makeText(context, "Comment is disabled for this post", Toast.LENGTH_SHORT)
                        .show()

                }

            }
        }

    }

    inner class ImageVm(private val imageVm: PostImageBinding) :
        RecyclerView.ViewHolder(imageVm.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
            imageVm.imagebind = data

            if (data.description.isEmpty()) {
                imageVm.imageText.visibility = View.GONE
                imageVm.readMore.visibility = View.GONE

            } else {
                imageVm.imageText.visibility = View.VISIBLE
                textView = imageVm.imageText

                val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(
                        data.description,
                        Html.FROM_HTML_MODE_LEGACY,
                        this@FeedAdapter,
                        null
                    )
                } else {
                    Html.fromHtml(data.description)
                }
                imageVm.imageText.movementMethod = LinkMovementMethod.getInstance();

                if (htmldata.length > 200) {
                    imageVm.imageText.text = htmldata.substring(0, 180) + "..."
                    imageVm.readMore.visibility = View.VISIBLE
                } else {
                    imageVm.imageText.text = htmldata
                    imageVm.readMore.visibility = View.GONE
                }
                imageVm.readMore.setOnClickListener {

                    if (imageVm.readMore.text.equals("Read More")) {
                        imageVm.imageText.text = htmldata
                        imageVm.readMore.text = "Read Less"
                    } else {
                        imageVm.imageText.text = htmldata.substring(0, 180) + "..."
                        imageVm.readMore.text = "Read More"
                    }
                }
            }

            imageVm.postShare.setOnClickListener {
                item_pos = adapterPosition
                Helper.sharePost(context as Activity, feedatalist[item_pos].id, "", "Image")
            }


            imageVm.like.setOnClickListener(OnSingleClickListener {
                if (!booleanlike) {
                    item_pos = adapterPosition
                    createBodyData("Like")

                }


            })


            imageVm.pinIV.setOnClickListener {
                item_pos = adapterPosition
                Log.d("pos", "bind: $item_pos")
                if (data.my_pinned == "1") {
                    imageVm.pinIV.setImageResource(R.drawable.unpinned)
                    createBodyData("Unpin")
                } else {
                    imageVm.pinIV.setImageResource(R.drawable.pinned)
                    createBodyData("Pin")
                }
            }


            imageVm.postComment.setOnClickListener {

                if (feedatalist[adapterPosition].is_comment_enable == "1") {
                    item_pos = adapterPosition
                    commentlist.clear()
                    createBodyData("GetComment")

                    open_comment_layout(context!!, adapterPosition)
                } else {
                    Toast.makeText(context, "Comment is disabled for this post", Toast.LENGTH_SHORT)
                        .show()

                }


            }
        }

    }

    inner class VideoVm(private val videoVm: VideoPostBinding) :
        RecyclerView.ViewHolder(videoVm.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
            videoVm.videopostbind = data

            if (data.description.isEmpty()) {
                videoVm.videoText.visibility = View.GONE
            } else {
                videoVm.videoText.visibility = View.VISIBLE
                videoVm.videoText.text = data.description

                textView = videoVm.videoText

                val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(
                        data.description,
                        Html.FROM_HTML_MODE_LEGACY,
                        this@FeedAdapter,
                        null
                    )
                } else {
                    Html.fromHtml(data.description)
                }

                if (htmldata.length > 200) {
                    videoVm.videoText.text = htmldata.substring(0, 200) + "..."
                    videoVm.readMore.visibility = View.VISIBLE
                } else {
                    videoVm.videoText.text = htmldata
                    videoVm.readMore.visibility = View.GONE
                }
                videoVm.videoText.movementMethod = LinkMovementMethod.getInstance()

                videoVm.readMore.setOnClickListener {
                    if (videoVm.readMore.text.equals("Read More")) {
                        videoVm.videoText.text = htmldata
                        videoVm.readMore.text = "Read Less"
                    } else {
                        videoVm.videoText.text = htmldata.substring(0, 200) + "..."
                        videoVm.readMore.text = "Read More"
                    }
                }

            }
            videoVm.postShare.setOnClickListener {
                item_pos = adapterPosition
                Helper.sharePost(context as Activity, feedatalist[item_pos].id, "", "VideoPost")
            }


            videoVm.playVideo.setOnClickListener {
                if (feedatalist[adapterPosition].meta_url.isNotEmpty()) {
                    val intent = Intent(context, FeedVideoPlayer::class.java)
                    intent.putExtra("url", feedatalist[adapterPosition].meta_url)
                    intent.putExtra("des", feedatalist[adapterPosition].description)
                    intent.putExtra("view_type", feedatalist[adapterPosition].json.view_type)
                    Helper.gotoActivity(intent, context as Activity)
                } else {
                    Toast.makeText(context, "No Video Found", Toast.LENGTH_SHORT).show()
                }
            }
            /*  videoVm.like.setOnClickListener {
                  item_pos = adapterPosition
                  createBodyData("Like")

              }
  */
            videoVm.like.setOnClickListener(OnSingleClickListener {
                if (!booleanlike) {
                    item_pos = adapterPosition
                    createBodyData("Like")
                }


            })

            videoVm.pinIV.setOnClickListener {
                item_pos = adapterPosition
                Log.d("pos", "bind: $item_pos")
                if (data.my_pinned == "1") {
                    videoVm.pinIV.setImageResource(R.drawable.unpinned)
                    createBodyData("Unpin")
                } else {
                    videoVm.pinIV.setImageResource(R.drawable.pinned)
                    createBodyData("Pin")
                }
            }

            videoVm.postComment.setOnClickListener {

                if (feedatalist[adapterPosition].is_comment_enable == "1") {
                    item_pos = adapterPosition
                    commentlist.clear()
                    createBodyData("GetComment")

                    open_comment_layout(context!!, adapterPosition)
                } else {
                    Toast.makeText(context, "Comment is disabled for this post", Toast.LENGTH_SHORT)
                        .show()

                }


            }
        }
    }

    @SuppressLint("SetTextI18n")
    inner class AudioVM(private val audioVM: AudioPostBinding) :
        RecyclerView.ViewHolder(audioVM.root) {

        fun bind(data: Data) {
            audioVM.audiobind = data
            if (data.text.isEmpty()) {
                audioVM.audioText.visibility = View.GONE
            } else {
                audioVM.audioText.visibility = View.VISIBLE
                textView = audioVM.audioText
                val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(
                        data.description,
                        Html.FROM_HTML_MODE_LEGACY,
                        this@FeedAdapter,
                        null
                    )
                } else {
                    Html.fromHtml(data.description)
                }

                if (htmldata.length > 200) {
                    audioVM.audioText.text = htmldata.substring(0, 200) + "..."
                    audioVM.readMore.visibility = View.VISIBLE
                } else {
                    audioVM.audioText.text = htmldata
                    audioVM.readMore.visibility = View.GONE
                }
                audioVM.audioText.movementMethod = LinkMovementMethod.getInstance()

                audioVM.readMore.setOnClickListener {
                    if (audioVM.readMore.text.equals("Read More")) {
                        audioVM.audioText.text = htmldata
                        audioVM.readMore.text = "Read Less"
                    } else {
                        audioVM.audioText.text = htmldata.substring(0, 200) + "..."
                        audioVM.readMore.text = "Read More"
                    }
                }
            }

            audioVM.postShare.setOnClickListener {
                item_pos = adapterPosition
                Helper.sharePost(context as Activity, feedatalist[item_pos].id, "", "AudioPost")
            }

            audioVM.auidoLayout.setOnClickListener {
                if (feedatalist[adapterPosition].meta_url.isNotEmpty()) {
                    val intent = Intent(context!!, FeedVideoPlayer::class.java)
                    intent.putExtra("url", feedatalist[adapterPosition].meta_url)
                    intent.putExtra("des", feedatalist[adapterPosition].description)
                    intent.putExtra("view_type", feedatalist[adapterPosition].json.view_type)

                    Helper.gotoActivity(intent, context!! as Activity)
                } else {
                    Toast.makeText(context!!, "No Audio Found", Toast.LENGTH_SHORT).show()
                }
            }
            audioVM.pinIV.setOnClickListener {
                item_pos = adapterPosition
                Log.d("pos", "bind: $item_pos")
                if (data.my_pinned == "1") {
                    audioVM.pinIV.setImageResource(R.drawable.unpinned)
                    createBodyData("Unpin")
                } else {
                    audioVM.pinIV.setImageResource(R.drawable.pinned)
                    createBodyData("Pin")
                }
            }
            /*  audioVM.like.setOnClickListener {
                  item_pos = adapterPosition
                  createBodyData("Like")
              }
  */
            audioVM.like.setOnClickListener(OnSingleClickListener {
                if (!booleanlike) {
                    item_pos = adapterPosition
                    createBodyData("Like")
                }


            })


            audioVM.postComment.setOnClickListener {

                if (feedatalist[adapterPosition].is_comment_enable == "1") {
                    item_pos = adapterPosition
                    commentlist.clear()
                    createBodyData("GetComment")
                    open_comment_layout(context!!, adapterPosition)
                } else {
                    Toast.makeText(context, "Comment is disabled for this post", Toast.LENGTH_SHORT)
                        .show()

                }


            }
        }

    }

    inner class QuestionVM(private val questionVM: QuestionViewBinding) :
        RecyclerView.ViewHolder(questionVM.root) {
        fun bind(data: Data) {
            questionVM.questionbind = data
            questionVM.postShare.setOnClickListener {
                item_pos = adapterPosition
                Helper.sharePost(context as Activity, feedatalist[item_pos].id, "", "Post Qestion")
            }
            if (data.post_type == "8") {
                questionVM.recyerclerView.visibility = View.GONE
                questionVM.recyerclerViewWebview.visibility = View.VISIBLE
                val optionWebAdapter = OptionWebAdapter(
                    context!!,
                    data.json.options,
                    this@FeedAdapter,
                    adapterPosition,
                    data.json
                )
                questionVM.optionwebadapter = optionWebAdapter
            } else {
                questionVM.recyerclerView.isVisible = true
                questionVM.recyerclerViewWebview.isVisible = false
                val optionAdapter = OptionAdapter(
                    context!!,
                    data.json.options,
                    this@FeedAdapter,
                    adapterPosition,
                    data.json
                )
                questionVM.optionadapter = optionAdapter
            }

            questionVM.like.setOnClickListener(OnSingleClickListener {
                if (!booleanlike) {
                    item_pos = adapterPosition
                    createBodyData("Like")
                }


            })


            /* questionVM.like.setOnClickListener {
                 item_pos = adapterPosition
                 createBodyData("Like")
             }*/
            questionVM.postComment.setOnClickListener {

                if (feedatalist[adapterPosition].is_comment_enable == "1") {
                    item_pos = adapterPosition
                    commentlist.clear()
                    createBodyData("GetComment")
                    open_comment_layout(context!!, adapterPosition)
                } else {
                    Toast.makeText(context, "Comment is disabled for this post", Toast.LENGTH_SHORT)
                        .show()

                }


            }

            questionVM.pinIV.setOnClickListener {
                item_pos = adapterPosition
                Log.d("pos", "bind: $item_pos")
                if (data.my_pinned == "1") {
                    questionVM.pinIV.setImageResource(R.drawable.unpinned)
                    createBodyData("Unpin")
                } else {
                    questionVM.pinIV.setImageResource(R.drawable.pinned)
                    createBodyData("Pin")
                }
            }
        }

    }

    inner class QuizVM(private val quizVM: QuizViewBinding) : RecyclerView.ViewHolder(quizVM.root) {
        fun bind(data: Data) {
            quizVM.quizbind = data


            quizVM.like.setOnClickListener(OnSingleClickListener {
                if (!booleanlike) {
                    booleanlike = true
                    item_pos = adapterPosition
                    createBodyData("Like")
                }
            })


            quizVM.postShare.setOnClickListener {
                item_pos = adapterPosition
                Helper.sharePost(context as Activity, feedatalist[item_pos].id, "", "Post Quiz")
            }

            quizVM.pinIV.setOnClickListener {
                item_pos = adapterPosition
                Log.d("pos", "bind: $item_pos")
                if (data.my_pinned == "1") {
                    quizVM.pinIV.setImageResource(R.drawable.unpinned)
                    createBodyData("Unpin")
                } else {
                    quizVM.pinIV.setImageResource(R.drawable.pinned)
                    createBodyData("Pin")
                }
            }



            quizVM.startQuiz.setOnClickListener {
                if (data.json.state != null && data.json.state.equals("1")) {
                    SharedPreference.getInstance().putString(Const.ID, "FEEDS")
                    item_pos = adapterPosition
                    val resultScreen = Intent(context!!, QuizActivity::class.java)
                    resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN)
                    resultScreen.putExtra(Const.STATUS, data.meta_url)
                    resultScreen.putExtra(Const.NAME, data.json.test_series_name)
                    resultScreen.putExtra("first_attempt", data.json.state)
                    Helper.gotoActivity(resultScreen, context!! as Activity)
                } else {
                    item_pos = adapterPosition
                    networkCall!!.NetworkAPICall(
                        API.API_GET_TEST_INSTRUCTION_DATA, "", true, false
                    )
                }
            }




            quizVM.postComment.setOnClickListener {

                if (feedatalist[adapterPosition].is_comment_enable == "1") {
                    item_pos = adapterPosition
                    commentlist.clear()
                    createBodyData("GetComment")

                    open_comment_layout(context!!, adapterPosition)
                } else {
                    Toast.makeText(context, "Comment is disabled for this post", Toast.LENGTH_SHORT)
                        .show()

                }


            }
        }


    }

    override fun getAPIB(apitype: String?, typeApi: String?, service: APIInterface?): Call<String> {
        when (apitype) {

            API.API_GET_INFO_TEST_SERIES -> {
                val masterdataencryptionData = EncryptionData()
                masterdataencryptionData.course_id = "FEEDS"
                masterdataencryptionData.test_id = feedatalist[item_pos].meta_url
                val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
                val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
                return service!!.API_GET_INFO_TEST_SERIES(masterdatadoseStrScr)
            }

            API.API_GET_TEST_INSTRUCTION_DATA -> {
                val masterdataencryptionData = EncryptionData()
                masterdataencryptionData.test_id = feedatalist[item_pos].meta_url
                masterdataencryptionData.course_id = "FEEDS"
                val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
                val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
                return service!!.API_GET_TEST_INSTRUCTION_DATA(masterdatadoseStrScr)
            }
        }
        return null!!
    }

    override fun SuccessCallBack(
        jsonstring: JSONObject?,
        apitype: String?,
        typeApi: String?,
        showprogress: Boolean
    ) {
        MakeMyExam.setTime_server(jsonstring!!.optString("time").toLong() * 1000)
        when (apitype) {
            API.API_GET_TEST_INSTRUCTION_DATA -> {
                try {
                    if (jsonstring.optString(Const.STATUS) == Const.TRUE) {
                        val gson = Gson()
                        val data1 = jsonstring.getJSONObject("data")
                        val instructionData =
                            gson.fromJson(data1.toString(), InstructionData::class.java)
                        showPopUp(instructionData)
                    } else if (jsonstring.optString("status") == Const.FALSE) {
                        if (jsonstring.optString(Const.AUTH_CODE)
                                .equals(Const.EXPIRY_AUTH_CODE, ignoreCase = true)
                        ) {
                            return
                        }
                        RetrofitResponse.GetApiData(
                            context!!,
                            if (jsonstring.has(Const.AUTH_CODE)) jsonstring.getString(Const.AUTH_CODE) else "",
                            jsonstring.getString(Const.MESSAGE),
                            false
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            API.API_GET_INFO_TEST_SERIES -> {
                if (jsonstring.optString(Const.STATUS) == Const.TRUE) {
                    val time = jsonstring.optLong("time")
                    val testseriesBase: TestseriesBase?
                    try {
                        val gson = Gson()
                        val postData = when (context) {
                            is FeedsActivity -> {
                                (context as FeedsActivity).utkashRoom.feedDao.retriveObject(
                                    (context as FeedsActivity).main_cat,
                                    feedatalist[item_pos].id
                                )
                            }
                            is PinnedPostActivity -> {
                                (context as PinnedPostActivity).utkashRoom.feedDao.retriveObject(
                                    (context as PinnedPostActivity).main_cat,
                                    feedatalist[item_pos].id
                                )
                            }
                            else -> {
                                (context as FeedsActivity).utkashRoom.feedDao.retriveObject(
                                    (context as FeedsActivity).main_cat,
                                    feedatalist[item_pos].id
                                )
                            }
                        }

                        testseriesBase =
                            gson.fromJson(jsonstring.toString(), TestseriesBase::class.java)
                        if (testseriesBase.data.questions != null && testseriesBase.data.questions.size > 0 && lang == 1) {
                            val quizView = Intent(context!!, TestBaseActivity::class.java)
                            quizView.putExtra(Const.STATUS, false)
                            quizView.putExtra(Const.TEST_SERIES_ID, feedatalist[item_pos].meta_url)
                            quizView.putExtra(
                                Const.TEST_SERIES_Name,
                                feedatalist[item_pos].json.test_series_name
                            )
                            SharedPreference.getInstance()
                                .putString("test_series", jsonstring.toString())
                            quizView.putExtra("course_id", "FEEDS")
                            quizView.putExtra(
                                "TOTAL_QUESTIONS",
                                feedatalist[item_pos].json.total_questions
                            )
                            quizView.putExtra("first_attempt", "1")
                            quizView.putExtra("result_date", "")
                            quizView.putExtra("test_submission", "1")
                            quizView.putExtra("time", time)
                            quizView.putExtra("enddate", "")
                            quizView.putExtra(Const.LANG, lang)
                            quizView.putExtra("post_json", Gson().toJson(postData))
                            Helper.gotoActivity_finish(quizView, context!! as Activity)
                        } else if (testseriesBase.data.questionsHindi != null && testseriesBase.data.questionsHindi.size > 0 && lang == 2) {
                            testseriesBase.data.questions = testseriesBase.data.questionsHindi
                            val quizView = Intent(
                                context!!,
                                TestBaseActivity::class.java
                            )
                            quizView.putExtra(Const.STATUS, false)
                            quizView.putExtra(Const.TEST_SERIES_ID, feedatalist[item_pos].meta_url)
                            quizView.putExtra(
                                Const.TEST_SERIES_Name,
                                feedatalist[item_pos].json.test_series_name
                            )
                            SharedPreference.getInstance()
                                .putString("test_series", jsonstring.toString())
                            quizView.putExtra("course_id", "FEEDS")
                            quizView.putExtra(
                                "TOTAL_QUESTIONS",
                                feedatalist[item_pos].json.total_questions
                            )
                            quizView.putExtra("first_attempt", "1")
                            quizView.putExtra("result_date", "")
                            quizView.putExtra("test_submission", "1")
                            quizView.putExtra(Const.LANG, lang)
                            quizView.putExtra("time", time)
                            quizView.putExtra("enddate", "")
                            quizView.putExtra("post_json", Gson().toJson(postData))

                            Helper.gotoActivity_finish(quizView, context!! as Activity)
                        } else {
                            Toast.makeText(context!!, "No Question Found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(context!!, "Something went wrong.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    if (jsonstring.optString(Const.AUTH_CODE)
                            .equals(Const.EXPIRY_AUTH_CODE, ignoreCase = true)
                    ) {
                        return
                    }
                    RetrofitResponse.GetApiData(
                        context!!, if (jsonstring.has(Const.AUTH_CODE)) jsonstring.getString(
                            Const.AUTH_CODE
                        ) else "", jsonstring.getString(Const.MESSAGE), false
                    )
                }
            }
        }
    }

    fun showPopMenuForLangauge(v: View, testBasicInst: TestBasicInst) {
        val popup = PopupMenu(context!!, v)
        popup.setOnMenuItemClickListener { item ->
            (v as TextView).text = item.title.toString()
            if (item.title.toString() == context!!.getString(R.string.hindi))
                lang = 2
            else if (item.title.toString() == context!!.getString(R.string.english))
                lang = 1
            false
        }
        for (i in testBasicInst.lang_id.split(",".toRegex()).toTypedArray().indices) {
            if (testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[i] == "1") popup.menu.add(
                context!!.resources.getStringArray(R.array.dialog_choose_language_array).get(0)
            ) else if (testBasicInst.lang_id.split(",".toRegex())
                    .toTypedArray()[i] == "2"
            ) popup.menu.add(
                context!!.resources.getStringArray(R.array.dialog_choose_language_array).get(1)
            )
        }
        popup.show()
    }


    private fun getintotestseries() {
        networkCall!!.NetworkAPICall(API.API_GET_INFO_TEST_SERIES, "", true, false)
    }

    @SuppressLint("SetTextI18n")
    private fun showPopUp(instructionData: InstructionData) {
        val li = context!!.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.popup_basicinfo_quiz_career, null, false)
        val quizBasicInfoDialog = Dialog(context!!, R.style.CustomAlertDialog)
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        quizBasicInfoDialog.setCanceledOnTouchOutside(true)
        quizBasicInfoDialog.setContentView(v)
        quizBasicInfoDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        quizBasicInfoDialog.show()
        val testBasicInst = instructionData.testBasic
        val quizTitle: TextView = v.findViewById(R.id.quizTitleTV) as TextView
        val quizTotalMarks: TextView = v.findViewById(R.id.marksTextValueTV) as TextView
        val quizQuesNumTv: TextView = v.findViewById(R.id.numQuesValueTV) as TextView
        val quizsectionValueTV: TextView = v.findViewById(R.id.sectionValueTV) as TextView
        val languageSpinnerTV: TextView = v.findViewById(R.id.languageSpinnerTV) as TextView
        val quizTimeTV: TextView = v.findViewById(R.id.quizTimeValueTV) as TextView
        val reAttempt: TextView = v.findViewById(R.id.remarksTV) as TextView
        val check_box: CheckBox = v.findViewById(R.id.check_box) as CheckBox
        val generalInstrValueTV: TextView = v.findViewById(R.id.generalInstrValueTV) as TextView
        val startQuiz: Button = v.findViewById(R.id.startQuizBtn) as Button
        val sectionListLL: LinearLayout = v.findViewById(R.id.sectionListLL) as LinearLayout
        val general_layout: LinearLayout = v.findViewById(R.id.general_layout) as LinearLayout
        val section_time: LinearLayout = v.findViewById(R.id.section_time) as LinearLayout
        if (testBasicInst.test_assets != null) {
            if (testBasicInst.test_assets.hide_inst_time.equals("0", ignoreCase = true)) {
                section_time.visibility = View.VISIBLE
            } else {
                section_time.visibility = View.INVISIBLE
            }
        }
        addSectionView(sectionListLL, instructionData)
        if (SharedPreference.getInstance().getBoolean(Const.RE_ATTEMPT)) {
            reAttempt.visibility = View.GONE
        } else reAttempt.visibility = View.GONE
        if (testBasicInst.lang_id.length == 3) {
            languageSpinnerTV.setOnClickListener {
                showPopMenuForLangauge(languageSpinnerTV, testBasicInst)
            }
        }
        if (testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[0] == "1") {
            languageSpinnerTV.text =
                context!!.resources.getStringArray(R.array.dialog_choose_language_array).get(0)
            lang = testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[0].toInt()
        } else if (testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[0] == "2") {
            languageSpinnerTV.text =
                context!!.resources.getStringArray(R.array.dialog_choose_language_array).get(1)
            lang = testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[0].toInt()
        }
        quizTitle.text = testBasicInst.testSeriesName
        quizQuesNumTv.text = testBasicInst.totalQuestions
        quizTimeTV.text = testBasicInst.timeInMins
        quizTotalMarks.text = testBasicInst.totalMarks
        if (testBasicInst.description.isEmpty()) {
            general_layout.visibility = View.GONE
        } else {
            general_layout.visibility = View.VISIBLE
            generalInstrValueTV.text = Html.fromHtml(testBasicInst.description)
        }
        quizsectionValueTV.text = "" + instructionData.testSections.size
        startQuiz.tag = testBasicInst
        startQuiz.setOnClickListener {
            if (testBasicInst.totalQuestions.equals("0", ignoreCase = true)) {
                Toast.makeText(context!!, "Please add Question.", Toast.LENGTH_SHORT).show()
            } else {
                if (check_box.isChecked) {
                    quizBasicInfoDialog.dismiss()
                    getintotestseries()
                } else {
                    Toast.makeText(
                        context!!,
                        "Please check following instructions.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        quizBasicInfoDialog.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                quizBasicInfoDialog.dismiss()
            }
            true
        }
    }

    private fun addSectionView(sectionListLL: LinearLayout, instructionData: InstructionData) {
        var count = 0
        for (testSectionInst in instructionData.testSections) {
            sectionListLL.addView(
                initSectionListView(
                    testSectionInst,
                    count,
                    if (instructionData.testBasic.test_assets == null) "" else instructionData.testBasic.test_assets.hide_inst_time
                )
            )
            count++
        }
    }

    @SuppressLint("SetTextI18n")
    fun initSectionListView(
        testSectionInst: TestSectionInst,
        tag: Int,
        hide_inst_time: String
    ): LinearLayout {
        val LinearLayoutList: MutableList<View> = ArrayList()
        val v =
            View.inflate(context!!, R.layout.layout_option_section_list_view, null) as LinearLayout
        val secNameTV = v.findViewById(R.id.secNameTV) as TextView
        val totQuesTV = v.findViewById(R.id.totQuesTV) as TextView
        val totTimeTV = v.findViewById(R.id.totTimeTV) as TextView
        val maxMarksTV = v.findViewById(R.id.maxMarksTV) as TextView
        val markPerQuesTV = v.findViewById(R.id.markPerQuesTV) as TextView
        val negMarkPerQuesTV = v.findViewById(R.id.negMarkPerQuesTV) as TextView
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 0, 0, 0)
        v.layoutParams = lp
        if (!hide_inst_time.equals("", ignoreCase = true)) {
            if (hide_inst_time.equals("0", ignoreCase = true)) {
                totTimeTV.visibility = View.VISIBLE
            } else {
                totTimeTV.visibility = View.INVISIBLE
            }
        }
        secNameTV.text =
            """${testSectionInst.name}  (${testSectionInst.sectionPart})""".trimIndent()
        totQuesTV.text = testSectionInst.totalQuestions
        totTimeTV.text = testSectionInst.sectionTiming
        maxMarksTV.text = String.format(
            "%.2f",
            testSectionInst.marksPerQuestion.toFloat() * testSectionInst.totalQuestions.toInt()
        )
        markPerQuesTV.text = testSectionInst.marksPerQuestion
        negMarkPerQuesTV.text = "" + testSectionInst.negativeMarks.toFloat()
        v.tag = tag
        LinearLayoutList.add(v)
        return v
    }


    override fun ErrorCallBack(jsonstring: String?, apitype: String?, typeApi: String?) {
    }

    override fun itemSelect(option: Option, index: Int, feedlistpos: Int) {
        option_index = index
        item_pos = feedlistpos
        createBodyData("Attempt Mcq")
    }

    fun addFeed(datalist: ArrayList<Data>) {
        feedatalist = datalist
        if (networkCall != null)
            networkCall = NetworkCall(this, context)

    }

    fun change_posiiton() {
        val totallike = feedatalist[item_pos].total_likes
        if (feedatalist[item_pos].my_like.equals("1")) {
            feedatalist[item_pos].my_like = "0"
            if (!totallike.isEmpty() && totallike.toInt() > 0) {
                feedatalist[item_pos].total_likes = "${totallike.toInt() - 1}"
            }
        } else {
            if (!totallike.isEmpty()) {
                feedatalist[item_pos].total_likes = "${totallike.toInt() + 1}"
            }
            feedatalist[item_pos].my_like = "1"
        }
        CoroutineScope(Dispatchers.IO).launch {
            when (context) {
                is FeedsActivity -> {
                    if ((context as FeedsActivity).utkashRoom.feedDao.isFeedExist(feedatalist[item_pos].id)) {
                        (context as FeedsActivity).utkashRoom.feedDao.updateMyLike(
                            feedatalist[item_pos].id,
                            feedatalist[item_pos].my_like,
                            feedatalist[item_pos].total_likes
                        )
                    }
                }
                is PinnedPostActivity -> {
                    if ((context as PinnedPostActivity).utkashRoom.feedDao.isFeedExist(feedatalist[item_pos].id)) {
                        (context as PinnedPostActivity).utkashRoom.feedDao.updateMyLike(
                            feedatalist[item_pos].id,
                            feedatalist[item_pos].my_like,
                            feedatalist[item_pos].total_likes
                        )
                    }
                }
            }
        }
        notifyItemChanged(item_pos)
        booleanlike = false
    }


    fun getCommentList(dataJsonObject: JSONArray) {
        commentlist.clear()
        for (i in 0 until dataJsonObject.length()) {
            val dataObj: JSONObject = dataJsonObject.optJSONObject(i)
            val feeddata = Gson().fromJson(
                dataObj.toString(),
                com.utkarshnew.android.feeds.dataclass.comment.Data::class.java
            )
            commentlist.add(feeddata)
        }
        commentAdapter?.apply {
            notifydata(commentlist)
        }
    }

    fun addComment(jsonObject1: JSONObject) {
        val feeddata = Gson().fromJson(
            jsonObject1.toString(),
            com.utkarshnew.android.feeds.dataclass.comment.Data::class.java
        )
        commentlist.add(feeddata)
        commentAdapter!!.addToExistingList(commentlist)
        Helper.hideKeyboard(context!! as Activity)
        comment_recyerler!!.scrollToPosition(comment_recyerler!!.adapter!!.itemCount - 1)
        notifyItemChanged(item_pos)
    }

    fun attempt_mcq() {
        var option_attempt_count =
            feedatalist[item_pos].json.options[option_index].attempt_count
        if (option_attempt_count.equals("")) {
            option_attempt_count = "0"
        }
        feedatalist[item_pos].json.options[option_index].attempt_count =
            "${option_attempt_count.toInt() + 1}"
        feedatalist[item_pos].json.attempt_index = (option_index + 1).toString()
        var total_attempt = feedatalist[item_pos].json.total_attempt
        if (total_attempt == null || total_attempt.isEmpty()) {
            total_attempt = "0"
        }
        feedatalist[item_pos].json.total_attempt = "${total_attempt.toInt() + 1}"


        CoroutineScope(Dispatchers.IO).launch {
            when (context) {
                is FeedsActivity -> {

                    if ((context as FeedsActivity).utkashRoom.feedDao.isFeedExist(feedatalist[item_pos].id)) {
                        (context as FeedsActivity).utkashRoom.feedDao.updateMyjson(
                            feedatalist[item_pos].id,
                            Gson().toJson(feedatalist[item_pos].json)
                        )
                    }
                }
                is PinnedPostActivity -> {
                    if ((context as PinnedPostActivity).utkashRoom.feedDao.isFeedExist(feedatalist[item_pos].id)) {
                        (context as PinnedPostActivity).utkashRoom.feedDao.updateMyjson(
                            feedatalist[item_pos].id,
                            Gson().toJson(feedatalist[item_pos].json)
                        )
                    }
                }
            }
        }

        notifyItemChanged(item_pos)
    }

    fun viewAllclass(view: View) {
        Helper.gotoActivity_withour_intent(context!! as Activity, LiveClassActivity::class.java)
    }

    fun viewAllTest(view: View) {
        Helper.gotoActivity_withour_intent(context!! as Activity, LivetestActivity::class.java)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(s: String?): Drawable {
        val displayMetrics = context.getResources().getDisplayMetrics()
        val dpWidth: Int = displayMetrics.widthPixels
        val padding = 10

        val d = LevelListDrawable()
        val empty = context.resources.getDrawable(R.mipmap.course_placeholder)
        d.addLevel(0, 0, empty)
        d.setBounds(0, 0, empty.intrinsicWidth, empty.intrinsicHeight)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val `is` = URL(s).openStream()
                val bitmap = BitmapFactory.decodeStream(`is`)
                if (bitmap != null) {
                    val bitmapDrawable = BitmapDrawable(bitmap)
                    d.addLevel(1, 1, bitmapDrawable)
                    val realWidth: Int = dpWidth - padding * padding
                    val realHeight: Int =  bitmap.height * realWidth / bitmap.width
                    d.setBounds(0, 0, realWidth, realHeight)
                    d.level = 1
                    withContext(Dispatchers.Main)
                    {
                        val t = textView!!.text
                        textView!!.text = t
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

    fun pinPost() {
        val postId = feedatalist[item_pos].id
        feedatalist[item_pos].my_pinned = "1"
        CoroutineScope(Dispatchers.IO).launch {
            when (context) {
                is FeedsActivity -> {
                    if ((context as FeedsActivity).utkashRoom.feedDao.isFeedExist(postId)) {
                        (context as FeedsActivity).utkashRoom.feedDao.updatePinnedPost("1", postId)
                    }
                }
                is PinnedPostActivity -> {
                    if ((context as PinnedPostActivity).utkashRoom.feedDao.isFeedExist(postId)) {
                        (context as PinnedPostActivity).utkashRoom.feedDao.updatePinnedPost(
                            "1",
                            postId
                        )
                    }
                }
            }
        }
    }

    fun unPinPost() {
        val postId = feedatalist[item_pos].id
        feedatalist[item_pos].my_pinned = "0"
        CoroutineScope(Dispatchers.IO).launch {
            when (context) {
                is FeedsActivity -> {
                    if ((context as FeedsActivity).utkashRoom.feedDao.isFeedExist(postId)) {
                        (context as FeedsActivity).utkashRoom.feedDao.updatePinnedPost(
                            "0",
                            postId
                        )
                    }
                }
                is PinnedPostActivity -> {
                    if ((context as PinnedPostActivity).utkashRoom.feedDao.isFeedExist(postId)) {
                        (context as PinnedPostActivity).utkashRoom.feedDao.updatePinnedPost(
                            "0",
                            postId
                        )
                    }
                }
            }
        }
    }
}