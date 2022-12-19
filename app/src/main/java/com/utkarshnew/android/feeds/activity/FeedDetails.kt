package com.utkarshnew.android.feeds.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utkarshnew.android.EncryptionModel.EncryptionData
import com.utkarshnew.android.OnSingleClickListener
import com.utkarshnew.android.R
import com.utkarshnew.android.Room.UtkashRoom
import com.utkarshnew.android.Utils.*
import com.utkarshnew.android.Utils.Network.API
import com.utkarshnew.android.Utils.Network.APIInterface
import com.utkarshnew.android.Utils.Network.NetworkCall
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse
import com.utkarshnew.android.courses.Activity.QuizActivity
import com.utkarshnew.android.databinding.ActivityFeedDetailsBinding
import com.utkarshnew.android.feeds.OptionItem
import com.utkarshnew.android.feeds.adapters.CommentAdapter
import com.utkarshnew.android.feeds.adapters.OptionAdapter
import com.utkarshnew.android.feeds.adapters.OptionWebAdapter
import com.utkarshnew.android.feeds.attempts
import com.utkarshnew.android.feeds.dataclass.*
import com.utkarshnew.android.feeds.dataclass.comment.Data
import com.utkarshnew.android.feeds.showToast
import com.utkarshnew.android.feeds.viewmodel.FeedDetailViewModel
import com.utkarshnew.android.feeds.viewmodel.FeedViewModel
import com.utkarshnew.android.home.liveclasses.Datum
import com.utkarshnew.android.home.livetest.LiveTestData
import com.utkarshnew.android.table.PostDataTable
import com.utkarshnew.android.testmodule.activity.TestBaseActivity
import com.utkarshnew.android.testmodule.model.InstructionData
import com.utkarshnew.android.testmodule.model.TestBasicInst
import com.utkarshnew.android.testmodule.model.TestSectionInst
import com.utkarshnew.android.testmodule.model.TestseriesBase
import dagger.hilt.android.AndroidEntryPoint
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
import java.util.ArrayList
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FeedDetails : AppCompatActivity(), Html.ImageGetter, OptionItem,
    NetworkCall.MyNetworkCallBack {
    private var feedDetailsBinding: ActivityFeedDetailsBinding? = null
    private var utkashRoom: UtkashRoom? = null
    var networkCall: NetworkCall? = null
    var optiondata: Json? = null
    var lang = 0
    var booleanlike = false
    var no_data_found_RL: RelativeLayout? = null
    var backBtn: Button? = null

    var is_postexist = false
    private var postId: String? = null
    private var postDataTable: PostDataTable? = null
    private var comment_txt = ""
    val feedDetailViewModel: FeedDetailViewModel by viewModels()
    val commentlist: ArrayList<Data> = ArrayList()
    private var commentAdapter: CommentAdapter? = null
    private var comment_recyerler: RecyclerView? = null
    private var option_index = 0

    private var test_id = "";

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Helper.enableScreenShot(this)

        feedDetailsBinding =
            DataBindingUtil.setContentView(this@FeedDetails, R.layout.activity_feed_details)
        feedDetailsBinding!!.feeddetailVm = feedDetailViewModel
        feedDetailsBinding!!.lifecycleOwner = this
        no_data_found_RL = findViewById(R.id.no_data_found_RL)
        backBtn = findViewById(R.id.backBtn)
        Helper.enableScreenShot(this)

        utkashRoom = UtkashRoom.getAppDatabase(this@FeedDetails)

        backBtn!!.setOnClickListener {
            finish()
        }
        observer()
        with(intent)
        {
            postId = getStringExtra("postId")

            CoroutineScope(Dispatchers.IO).launch {
                is_postexist = utkashRoom?.feedDao?.isFeedExist(postId)!!
            }

            getPostData()
        }





        feedDetailViewModel.adapter_bodydata.observe(this) {
            if (it != null) {
                when (feedDetailViewModel.type.value) {
                    "Like" -> {
                        feedDetailViewModel.getcourutine_adapter_post()
                    }
                    "GetComment" -> {
                        feedDetailViewModel.getcourutine_adapter_post()
                    }
                    "AddComment" -> {
                        feedDetailViewModel.getcourutine_adapter_post()
                    }
                    "Attempt Mcq" -> {
                        feedDetailViewModel.getcourutine_adapter_post()
                    }
                    "Pin" -> {
                        feedDetailViewModel.getcourutine_adapter_post()
                    }
                    "Unpin" -> {
                        feedDetailViewModel.getcourutine_adapter_post()
                    }
                }

            }
        }


        feedDetailViewModel.adapter_response.observe(this) {
            try {
                if (it != null && it.optString(Const.STATUS) == Const.TRUE) {
                    when (feedDetailViewModel.type.value) {
                        "Like" -> {
                            var like_count = postDataTable!!.total_likes.toInt()
                            if (postDataTable!!.my_like.equals("1")) {
                                postDataTable!!.my_like = "0"
                                feedDetailsBinding!!.like.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.favorite_border,
                                    0,
                                    0,
                                    0
                                )
                                like_count -= 1
                            } else {
                                feedDetailsBinding!!.like.setCompoundDrawablesWithIntrinsicBounds(
                                    R.drawable.favorite,
                                    0,
                                    0,
                                    0
                                )
                                postDataTable!!.my_like = "1"
                                like_count += 1
                            }
                            postDataTable!!.total_likes = "$like_count"
                            feedDetailsBinding!!.postLikeCount.text = "$like_count Likes"
                            booleanlike = false
                            CoroutineScope(Dispatchers.IO).launch {
                                if (is_postexist)
                                    utkashRoom!!.feedDao.updateMyLike(
                                        postId,
                                        postDataTable!!.my_like,
                                        postDataTable!!.total_likes
                                    )
                            }

                        }
                        "Pin" -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (is_postexist)
                                    utkashRoom!!.feedDao.updatePinnedPost("1", postId)
                            }
                            postDataTable!!.my_pinned = "1"
                            showToast( it.optString(Const.MESSAGE))
                        }
                        "Unpin" -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (is_postexist)
                                    utkashRoom!!.feedDao.updatePinnedPost("0", postId)
                            }
                            postDataTable!!.my_pinned = "0"
                            showToast( it.optString(Const.MESSAGE))

                        }
                        "GetComment" -> {
                            for (i in 0 until it.getJSONArray(Const.DATA).length()) {
                                val dataObj: JSONObject =
                                    it.getJSONArray(Const.DATA).optJSONObject(i)
                                val feeddata = Gson().fromJson(dataObj.toString(), Data::class.java)
                                commentlist.add(feeddata)
                            }
                            commentAdapter?.apply {
                                notifydata(commentlist)
                            }
                        }
                        "AddComment" -> {
                            val feeddata = Gson().fromJson(
                                it.optJSONObject(Const.DATA).toString(),
                                Data::class.java
                            )
                            commentlist.add(feeddata)
                            commentAdapter!!.addToExistingList(commentlist)
                            Helper.hideKeyboard(this@FeedDetails)
                            comment_recyerler!!.scrollToPosition(comment_recyerler!!.adapter!!.itemCount - 1)
                            commentAdapter!!.notifyDataSetChanged()
                        }
                        "Attempt Mcq" -> {
                            val optiondata = Gson().fromJson(postDataTable!!.json, Json::class.java)

                            var option_attempt_count =
                                optiondata.options[option_index].attempt_count
                            if (option_attempt_count.equals("")) {
                                option_attempt_count = "0"
                            }
                            optiondata.options[option_index].attempt_count =
                                "${option_attempt_count.toInt() + 1}"
                            optiondata!!.attempt_index = (option_index + 1).toString()
                            var total_attempt = optiondata.total_attempt
                            if (total_attempt == null || total_attempt.isEmpty()) {
                                total_attempt = "0"
                            }
                            optiondata.total_attempt = "${total_attempt.toInt() + 1}"

                            postDataTable!!.json = Gson().toJson(optiondata)

                            CoroutineScope(Dispatchers.IO).launch {
                                if (is_postexist)
                                    utkashRoom!!.feedDao.updateMyjson(postId, postDataTable!!.json)
                            }
                            setQuestion(postDataTable!!)
                        }
                    }

                } else {
                    RetrofitResponse.GetApiData(
                        this@FeedDetails!!,
                        if (it.has(Const.AUTH_CODE)) it.getString(Const.AUTH_CODE) else "",
                        it.getString(Const.MESSAGE),
                        false
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }


    }

    fun createApiBodyData() {
        val masterdataencryptionData = EncryptionData()
        masterdataencryptionData.post_id = postId
        val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
        val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
        feedDetailViewModel.bodydata.value = masterdatadoseStrScr
    }


    fun observer() {
        try {
            feedDetailViewModel.progressvalue.observe(this) {
                if (it.equals("0")) {
                    Helper.dismissProgressDialog()
                } else {
                    Helper.showProgressDialog(this@FeedDetails)
                }
            }
            feedDetailViewModel.jsonObjectmutable.observe(this) {

                if (it.optString(Const.STATUS) == Const.TRUE) {
                    val dataobj = it?.getJSONObject(Const.DATA)
                    val data = dataobj?.getJSONObject(Const.DATA)
                    val jsonObject = JSONObject(data.toString())
                    val related_courselist = dataobj?.getJSONArray("related_courses")
                    if (related_courselist!!.length() > 0) {
                        feedDetailsBinding!!.realted.isVisible = true

                        val list: List<NewCourseData> = Gson().fromJson(
                            related_courselist.toString(),
                            object : TypeToken<List<NewCourseData>?>() {}.type
                        )
                        feedDetailViewModel._relatedCourse.value = list
                    }
                    if (jsonObject.has("id")) {

                        val feeddata = Gson().fromJson(
                            data.toString(),
                            com.utkarshnew.android.feeds.dataclass.Data::class.java
                        )
                        postDataTable = PostDataTable()
                        postDataTable!!.created = feeddata.created
                        postDataTable!!.id = feeddata.id
                        postDataTable!!.masterCat = ""
                        postDataTable!!.json = Gson().toJson(feeddata.json)
                        postDataTable!!.meta_url = feeddata.meta_url
                        postDataTable!!.thumbnail = feeddata.thumbnail

                        postDataTable!!.url = feeddata.url ?: ""
                        postDataTable!!.modified = feeddata.modified
                        postDataTable!!.my_like = feeddata.my_like
                        postDataTable!!.name = feeddata.name
                        postDataTable!!.post_type = feeddata.post_type
                        postDataTable!!.profile_picture = feeddata.profile_picture
                        postDataTable!!.status = feeddata.status
                        postDataTable!!.sub_cat_id = ""
                        postDataTable!!.text = feeddata.text
                        postDataTable!!.total_comments = feeddata.total_comments
                        postDataTable!!.total_likes = feeddata.total_likes
                        postDataTable!!.user_id = feeddata.user_id
                        postDataTable!!.newCourseData = Gson().toJson(
                            feeddata.newCourseData ?: emptyList<NewCourseData>()
                        )
                        postDataTable!!.livetest = Gson().toJson(feeddata.livetest)
                        postDataTable!!.liveclass = Gson().toJson(feeddata.liveclass)
                        postDataTable!!.testResult =
                            Gson().toJson(feeddata.testResult ?: emptyList<TestResult>())
                        postDataTable!!.bannerlist =
                            Gson().toJson(feeddata.bannerlist ?: emptyList<BannerData>())
                        postDataTable!!.liveClassStatus = "0"
                        postDataTable!!.liveTestStatus = "0"
                        postDataTable!!.page = ""
                        postDataTable!!.iscommentenable = feeddata.is_comment_enable
                        postDataTable!!.sectionposiiton = ""
                        postDataTable!!.limit = ""
                        postDataTable!!.my_pinned = feeddata.my_pinned ?: "0"
                        postDataTable!!.description = feeddata.description ?: ""
                        visibileViewType()


                    } else {
                        no_data_found_RL!!.isVisible = true
                        feedDetailsBinding!!.scrollNested.isVisible = false
                    }


                }

            }

            feedDetailViewModel.bodydata.observe(this) {
                if (it != null) {
                    feedDetailViewModel.getPostData()
                }
            }

        } catch (e: Exception) {

        }

    }


    fun getPostData() {
        feedDetailViewModel.progressvalue.value = "1"
        createApiBodyData()
    }

    private fun visibileViewType() {
        when (postDataTable!!.post_type) {
            "1" -> {
                feedDetailsBinding!!.articleLayout.visibility = View.VISIBLE

            }
            "2" -> {
                feedDetailsBinding!!.imageLayout.visibility = View.VISIBLE

            }
            "5" -> {
                feedDetailsBinding!!.linkLayout.visibility = View.VISIBLE

            }
            "6", "8" -> {
                feedDetailsBinding!!.questionLayout.visibility = View.VISIBLE
            }
            "7" -> {
                feedDetailsBinding!!.quizLayout.visibility = View.VISIBLE

            }
            "3" -> {
                feedDetailsBinding!!.videoLayout.visibility = View.VISIBLE

            }
            "4" -> {
                feedDetailsBinding!!.audioLayout.visibility = View.VISIBLE
            }
        }
        feedDetailsBinding?.apply {
            feeddatatable = postDataTable
            bind(postDataTable!!)
        }
    }

    fun bind(data: PostDataTable) {
        when (data.post_type) {
            "1" -> {
                setArticle(data)
            }
            "2" -> {
                setImage(data)
            }
            "5" -> {
                setLink(data)
            }
            "6", "8" -> {
                setQuestion(data)
            }
            "7" -> {
                setQuiz(data)
            }
            "3" -> {
                setvideo(data)
            }
            "4" -> {
                setaudio(data)
            }
        }

        feedDetailsBinding!!.postComment.setOnClickListener {
            if (postDataTable!!.iscommentenable.equals("1")) {
                commentlist.clear()
                createBodyData("GetComment")
                open_comment_layout(this@FeedDetails)
            } else {
                Toast.makeText(this, "Comment is disabled for this post", Toast.LENGTH_SHORT).show()

            }
        }


        feedDetailsBinding!!.like.setOnClickListener(OnSingleClickListener {
            if (!booleanlike) {
                booleanlike = true
                createBodyData("Like")
            }


        })
        feedDetailsBinding!!.imageBack.setOnClickListener {
            finish()
        }

        feedDetailsBinding!!.postShare.setOnClickListener {
            Helper.sharePost(this@FeedDetails, postId, "", "Post")
        }
        feedDetailsBinding!!.pinIV.setOnClickListener {
            if (data.my_pinned == "1") {
                feedDetailsBinding!!.pinIV.setImageResource(R.drawable.unpinned)
                createBodyData("Unpin")
            } else {
                feedDetailsBinding!!.pinIV.setImageResource(R.drawable.pinned)
                createBodyData("Pin")
            }
        }
    }

    fun open_comment_layout(context: Context) {
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
            comment_recyerler = watchlist.findViewById<RecyclerView>(R.id.comment_recyerler)
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
                if (comment_txt.isNotEmpty() && comment_txt.length > 0) {
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


    private fun setaudio(data: PostDataTable) {
        if (data.description.isEmpty()) {
            feedDetailsBinding!!.audioText.visibility = View.GONE
        } else {
            feedDetailsBinding!!.audioText.visibility = View.VISIBLE

            val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY, this, null)
            } else {
                Html.fromHtml(data.description)
            }

            feedDetailsBinding!!.audioText.text = htmldata
            feedDetailsBinding!!.audioText.setMovementMethod(LinkMovementMethod.getInstance());
        }


        feedDetailsBinding!!.auidoLayout.setOnClickListener {
            if (data.meta_url.isNotEmpty()) {
                val intent = Intent(this, FeedVideoPlayer::class.java)
                intent.putExtra("url", data.meta_url)
                Helper.gotoActivity(intent, this)
            } else {
                Toast.makeText(this, "No Video Found", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setvideo(data: PostDataTable) {


        if (data.description.isEmpty()) {
            feedDetailsBinding!!.videoText.visibility = View.GONE
        } else {
            feedDetailsBinding!!.videoText.visibility = View.VISIBLE

            val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY, this, null)
            } else {
                Html.fromHtml(data.description)
            }

            feedDetailsBinding!!.videoText.text = htmldata
            feedDetailsBinding!!.videoText.setMovementMethod(LinkMovementMethod.getInstance());


        }

        feedDetailsBinding!!.playVideo.setOnClickListener {
            if (data.meta_url.isNotEmpty()) {
                optiondata = Gson().fromJson(data.json, Json::class.java)

                val intent = Intent(this, FeedVideoPlayer::class.java)
                intent.putExtra("url", data.meta_url)
                intent.putExtra("des", data.description)
                intent.putExtra("view_type", optiondata!!.view_type)

                Helper.gotoActivity(intent, this)
            } else {
                Toast.makeText(this, "No Video Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuiz(data: PostDataTable) {
        optiondata = Gson().fromJson(data.json, Json::class.java)
        if (optiondata!!.test_series_name!!.isEmpty()) {
            feedDetailsBinding!!.testName.visibility = View.GONE
        } else {
            feedDetailsBinding!!.testName.visibility = View.VISIBLE
            feedDetailsBinding!!.testName.text = optiondata!!.test_series_name
        }
        if (optiondata!!.state!!.equals("1")) {
            feedDetailsBinding!!.startQuiz.text = "View Result"
        } else {
            feedDetailsBinding!!.startQuiz.text = "Start Quiz"
        }

        if (optiondata!!.time_in_mins!!.isEmpty()) {
            feedDetailsBinding!!.totalMin.visibility = View.GONE
        } else {
            feedDetailsBinding!!.totalMin.visibility = View.VISIBLE
            feedDetailsBinding!!.totalMin.text = optiondata!!.time_in_mins + " Minutes"
        }



        if (optiondata!!.total_questions!!.isEmpty()) {
            feedDetailsBinding!!.totalQuestion.visibility = View.GONE
        } else {
            feedDetailsBinding!!.totalQuestion.visibility = View.VISIBLE
            feedDetailsBinding!!.totalQuestion.text = optiondata!!.total_questions + " Questions"
        }

        feedDetailsBinding!!.startQuiz.setOnClickListener {
            if (optiondata!!.state != null && optiondata!!.state.equals("1")) {
                SharedPreference.getInstance().putString(Const.ID, "FEEDS")
                val resultScreen = Intent(this@FeedDetails, QuizActivity::class.java)
                resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN)
                resultScreen.putExtra(Const.STATUS, data.meta_url)
                resultScreen.putExtra(Const.NAME, optiondata!!.test_series_name)
                resultScreen.putExtra("first_attempt", optiondata!!.state)
                Helper.gotoActivity(resultScreen, this@FeedDetails)
            } else {
                networkCall = NetworkCall(this, this)
                networkCall!!.NetworkAPICall(API.API_GET_TEST_INSTRUCTION_DATA, "", true, false)

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(data: PostDataTable) {

        val optiondata = Gson().fromJson(data.json, Json::class.java)
        feedDetailsBinding!!.postAttempt.isVisible = true
        feedDetailsBinding!!.postAttempt.attempts(optiondata.total_attempt)

        if (data.post_type.equals("8")) {
            feedDetailsBinding!!.recyerclerView.adapter =
                OptionWebAdapter(this, optiondata.options, this@FeedDetails, 0, optiondata)
        } else {
            feedDetailsBinding!!.recyerclerView.adapter =
                OptionAdapter(this, optiondata.options, this@FeedDetails, 0, optiondata)
        }

    }

    private fun setLink(data: PostDataTable) {
        if (data.meta_url.isEmpty()) {
            feedDetailsBinding!!.linkTxt.isEnabled = false
            feedDetailsBinding!!.linkTxt.visibility = View.INVISIBLE
        } else {
            feedDetailsBinding!!.linkTxt.isEnabled = true
            feedDetailsBinding!!.linkTxt.visibility = View.VISIBLE
            feedDetailsBinding!!.linkTxt.text = data.meta_url
            makeLinks(feedDetailsBinding!!.linkTxt, data.meta_url)
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
            ForegroundColorSpan(getColor(R.color.link_color)),
            startIndexOfLink,
            startIndexOfLink + link.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.setText(spannableString, TextView.BufferType.SPANNABLE)
    }


    private fun setImage(data: PostDataTable) {
        if (data.description.isEmpty()) {
            feedDetailsBinding!!.imageText.visibility = View.GONE
        } else {
            feedDetailsBinding!!.imageText.visibility = View.VISIBLE


            val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY, this, null)
            } else {
                Html.fromHtml(data.description)
            }

            feedDetailsBinding!!.imageText.text = htmldata
            feedDetailsBinding!!.imageText.setMovementMethod(LinkMovementMethod.getInstance());


        }
        feedDetailsBinding!!.imageReadMore.setOnClickListener {
            if (feedDetailsBinding!!.imageReadMore.text.equals("Read More")) {
                feedDetailsBinding!!.imageText.text = data.text
                feedDetailsBinding!!.imageReadMore.text = "Read Less"
            } else {
                feedDetailsBinding!!.imageText.text = data.text.substring(0, 200) + "..."
                feedDetailsBinding!!.imageReadMore.text = "Read More"
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(s: String?): Drawable {
        val displayMetrics = getResources().getDisplayMetrics()
        val dpWidth: Int = displayMetrics.widthPixels
        val padding = 10

        val d = LevelListDrawable()
        val empty = resources.getDrawable(R.mipmap.course_placeholder)
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
                        val t = feedDetailsBinding!!.articleTxt.text
                        feedDetailsBinding!!.articleTxt.text = t
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


    fun setArticle(data: PostDataTable) {
        if (data.text.isEmpty()) {
            feedDetailsBinding!!.articleTxtTop.visibility = View.GONE
        } else {
            feedDetailsBinding!!.articleTxtTop.visibility = View.VISIBLE
            feedDetailsBinding!!.articleTxtTop.text = data.text
        }

        val htmldata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(data.meta_url, Html.FROM_HTML_MODE_LEGACY, this, null)
        } else {
            Html.fromHtml(data.meta_url)
        }

        feedDetailsBinding!!.articleTxt.text = htmldata
        feedDetailsBinding!!.articleTxt.setMovementMethod(LinkMovementMethod.getInstance());

        feedDetailsBinding!!.readMore.setOnClickListener {
            if (feedDetailsBinding!!.readMore.text.equals("Read More")) {
                feedDetailsBinding!!.articleTxt.text = htmldata
                feedDetailsBinding!!.readMore.text = "Read Less"
            } else {
                feedDetailsBinding!!.articleTxt.text = htmldata.substring(0, 200) + "..."
                feedDetailsBinding!!.readMore.text = "Read More"
            }
        }
    }

    override fun itemSelect(option: Option, index: Int, feedlistpos: Int) {
        option_index = index
        createBodyData("Attempt Mcq")
    }

    private fun createBodyData(type: String) {
        val masterdataencryptionData = EncryptionData()
        var result = ""
        when (type) {
            "Like" -> {
                var like_unlike = ""
                if (postDataTable!!.my_like.equals("1")) {
                    like_unlike = "0"
                } else {
                    like_unlike = "1"
                }
                masterdataencryptionData.my_like = like_unlike
                masterdataencryptionData.post_id = postDataTable!!.id
                result = Gson().toJson(masterdataencryptionData)
            }
            "GetComment" -> {
                masterdataencryptionData.post_id = postDataTable!!.id
                masterdataencryptionData.parent_id = "0"
                result = Gson().toJson(masterdataencryptionData)
            }
            "AddComment" -> {
                masterdataencryptionData.id = ""
                masterdataencryptionData.parent_id = "0"
                masterdataencryptionData.post_id = postDataTable!!.id
                masterdataencryptionData.comment = comment_txt
                result = Gson().toJson(masterdataencryptionData)
            }
            "Attempt Mcq" -> {
                masterdataencryptionData.post_id = postDataTable!!.id
                masterdataencryptionData.index = (option_index + 1).toString()
                result = Gson().toJson(masterdataencryptionData)
            }
            "Pin" -> {
                masterdataencryptionData.post_id = postDataTable!!.id
                masterdataencryptionData.post_pin = "1"
                result = Gson().toJson(masterdataencryptionData)
            }
            "Unpin" -> {
                masterdataencryptionData.post_id = postDataTable!!.id
                masterdataencryptionData.post_pin = "0"
                result = Gson().toJson(masterdataencryptionData)
            }
            else -> {

            }
        }
        val masterdatadoseStrScr = AES.encrypt(result)
        feedDetailViewModel.type.value = type
        feedDetailViewModel.adapter_bodydata.value = masterdatadoseStrScr
    }


    override fun getAPIB(apitype: String?, typeApi: String?, service: APIInterface?): Call<String> {
        when (apitype) {

            API.API_GET_INFO_TEST_SERIES -> {
                val masterdataencryptionData = EncryptionData()
                masterdataencryptionData.course_id = "FEEDS"
                masterdataencryptionData.test_id = postDataTable!!.meta_url
                val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
                val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
                return service!!.API_GET_INFO_TEST_SERIES(masterdatadoseStrScr)
            }

            API.API_GET_TEST_INSTRUCTION_DATA -> {
                val masterdataencryptionData = EncryptionData()
                masterdataencryptionData.test_id = postDataTable!!.meta_url
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
                            this,
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
                    var testseriesBase: TestseriesBase? = null
                    try {
                        val gson = Gson()

                        testseriesBase =
                            gson.fromJson(jsonstring.toString(), TestseriesBase::class.java)
                        if (testseriesBase.data.questions != null && testseriesBase.data.questions.size > 0 && lang == 1) {
                            val quizView = Intent(this, TestBaseActivity::class.java)
                            quizView.putExtra(Const.STATUS, false)
                            quizView.putExtra(Const.TEST_SERIES_ID, postDataTable!!.meta_url)
                            quizView.putExtra(
                                Const.TEST_SERIES_Name,
                                feedDetailsBinding!!.testName.text.toString()
                            )
                            SharedPreference.getInstance()
                                .putString("test_series", jsonstring.toString())
                            quizView.putExtra("course_id", "FEEDS")
                            quizView.putExtra("TOTAL_QUESTIONS", optiondata!!.total_questions)
                            quizView.putExtra("first_attempt", "1")
                            quizView.putExtra("result_date", "")
                            quizView.putExtra("test_submission", "1")
                            quizView.putExtra("time", time)
                            quizView.putExtra("enddate", "")
                            quizView.putExtra(Const.LANG, lang)
                            quizView.putExtra("post_json", Gson().toJson(postDataTable))
                            Helper.gotoActivity_finish(quizView, this as Activity)
                        } else if (testseriesBase.data.questionsHindi != null && testseriesBase.data.questionsHindi.size > 0 && lang == 2) {
                            testseriesBase.data.questions = testseriesBase.data.questionsHindi
                            val quizView = Intent(
                                this, TestBaseActivity::class.java
                            )
                            quizView.putExtra(Const.STATUS, false)
                            quizView.putExtra(Const.TEST_SERIES_ID, postDataTable!!.meta_url)
                            quizView.putExtra(
                                Const.TEST_SERIES_Name,
                                feedDetailsBinding!!.testName.text.toString()
                            )
                            SharedPreference.getInstance()
                                .putString("test_series", jsonstring.toString())
                            quizView.putExtra("course_id", "FEEDS")
                            quizView.putExtra("TOTAL_QUESTIONS", optiondata!!.total_questions)
                            quizView.putExtra("first_attempt", "1")
                            quizView.putExtra("result_date", "")
                            quizView.putExtra("test_submission", "1")
                            quizView.putExtra(Const.LANG, lang)
                            quizView.putExtra("time", time)
                            quizView.putExtra("enddate", "")
                            quizView.putExtra("post_json", Gson().toJson(postDataTable))

                            Helper.gotoActivity_finish(quizView, this as Activity)
                        } else {
                            Toast.makeText(this, "No Question Found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    if (jsonstring.optString(Const.AUTH_CODE)
                            .equals(Const.EXPIRY_AUTH_CODE, ignoreCase = true)
                    ) {
                        return
                    }
                    RetrofitResponse.GetApiData(
                        this, if (jsonstring.has(Const.AUTH_CODE)) jsonstring.getString(
                            Const.AUTH_CODE
                        ) else "", jsonstring.getString(Const.MESSAGE), false
                    )
                }
            }
        }
    }

    override fun ErrorCallBack(jsonstring: String?, apitype: String?, typeApi: String?) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SetTextI18n")
    private fun showPopUp(instructionData: InstructionData) {
        val li = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li.inflate(R.layout.popup_basicinfo_quiz_career, null, false)
        val quizBasicInfoDialog = Dialog(this, R.style.CustomAlertDialog)
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        quizBasicInfoDialog.setCanceledOnTouchOutside(true)
        quizBasicInfoDialog.setContentView(v)
        quizBasicInfoDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        quizBasicInfoDialog.show()
        val quizTitle: TextView
        val quizQuesNumTv: TextView
        val quizTimeTV: TextView
        val quizTotalMarks: TextView
        val generalInstrValueTV: TextView
        val reAttempt: TextView
        val languageSpinnerTV: TextView
        val quizsectionValueTV: TextView
        val check_box: CheckBox
        val startQuiz: Button
        val sectionListLL: LinearLayout
        val general_layout: LinearLayout
        val section_time: LinearLayout
        val testBasicInst = instructionData.testBasic
        quizTitle = v.findViewById(R.id.quizTitleTV) as TextView
        quizTotalMarks = v.findViewById(R.id.marksTextValueTV) as TextView
        quizQuesNumTv = v.findViewById(R.id.numQuesValueTV) as TextView
        quizsectionValueTV = v.findViewById(R.id.sectionValueTV) as TextView
        languageSpinnerTV = v.findViewById(R.id.languageSpinnerTV) as TextView
        quizTimeTV = v.findViewById(R.id.quizTimeValueTV) as TextView
        reAttempt = v.findViewById(R.id.remarksTV) as TextView
        check_box = v.findViewById(R.id.check_box) as CheckBox
        generalInstrValueTV = v.findViewById(R.id.generalInstrValueTV) as TextView
        startQuiz = v.findViewById(R.id.startQuizBtn) as Button
        sectionListLL = v.findViewById(R.id.sectionListLL) as LinearLayout
        general_layout = v.findViewById(R.id.general_layout) as LinearLayout
        section_time = v.findViewById(R.id.section_time) as LinearLayout
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
                this.resources.getStringArray(R.array.dialog_choose_language_array).get(0)
            lang = testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[0].toInt()
        } else if (testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[0] == "2") {
            languageSpinnerTV.text =
                this.resources.getStringArray(R.array.dialog_choose_language_array).get(1)
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
                Toast.makeText(this, "Please add Question.", Toast.LENGTH_SHORT).show()
            } else {
                if (check_box.isChecked) {
                    quizBasicInfoDialog.dismiss()
                    getintotestseries()
                } else {
                    Toast.makeText(
                        this,
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

    fun showPopMenuForLangauge(v: View, testBasicInst: TestBasicInst) {
        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener { item ->
            (v as TextView).text = item.title.toString()
            if (item.title.toString() == getString(R.string.hindi))
                lang = 2
            else if (item.title.toString() == getString(R.string.english))
                lang = 1
            false
        }
        for (i in testBasicInst.lang_id.split(",".toRegex()).toTypedArray().indices) {
            if (testBasicInst.lang_id.split(",".toRegex()).toTypedArray()[i] == "1") popup.menu.add(
                resources.getStringArray(R.array.dialog_choose_language_array).get(0)
            ) else if (testBasicInst.lang_id.split(",".toRegex())
                    .toTypedArray()[i] == "2"
            ) popup.menu.add(
                resources.getStringArray(R.array.dialog_choose_language_array).get(1)
            )
        }
        popup.show()
    }


    private fun getintotestseries() {
        networkCall!!.NetworkAPICall(API.API_GET_INFO_TEST_SERIES, "", true, false)
    }

    @SuppressLint("SetTextI18n")
    fun initSectionListView(
        testSectionInst: TestSectionInst,
        tag: Int,
        hide_inst_time: String
    ): LinearLayout? {
        val LinearLayoutList: MutableList<View> = ArrayList()
        val v =
            View.inflate(this, R.layout.layout_option_section_list_view, null) as LinearLayout
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


}