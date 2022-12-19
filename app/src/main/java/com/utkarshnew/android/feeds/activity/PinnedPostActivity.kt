package com.utkarshnew.android.feeds.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utkarshnew.android.EncryptionModel.EncryptionData
import com.utkarshnew.android.OnSingleClickListener
import com.utkarshnew.android.R
import com.utkarshnew.android.Room.UtkashRoom
import com.utkarshnew.android.Utils.AES
import com.utkarshnew.android.Utils.Const
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.Utils.MakeMyExam
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse
import com.utkarshnew.android.feeds.adapters.FeedAdapter
import com.utkarshnew.android.feeds.dataclass.*
import com.utkarshnew.android.feeds.showToast
import com.utkarshnew.android.feeds.viewmodel.FeedViewModel
import com.utkarshnew.android.home.Constants
import com.utkarshnew.android.home.liveclasses.Datum
import com.utkarshnew.android.home.livetest.LiveTestData
import com.utkarshnew.android.table.MasteAllCatTable
import com.utkarshnew.android.table.MasterCat
import com.utkarshnew.android.table.PostDataTable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class PinnedPostActivity : AppCompatActivity(),
    PopupMenu.OnMenuItemClickListener {
    var backBtn: Button? = null
    private lateinit var feedJsonObject: JSONObject
    var isPullToRefresh = false
    var liveClassStatus: String = "0"
    var liveTestStatus: String = "0"
    var section_posiiton = "0"
    var utkashRoom: UtkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext())

    var response_booelan = false
    var limitdata: Int = 0

    var feedAdapter: FeedAdapter? = null
    var loading = true
    var no_data_found_RL: RelativeLayout? = null
    val feedViewModel: FeedViewModel by viewModels()
    var main_cat = "";
    var manager: LinearLayoutManager? = null
    var sub_cat = "";
    var master_cat = "";
    var selected_master_cat = ArrayList<MasteAllCatTable>()
    var selectedsub_all_cat = ArrayList<MasteAllCatTable>()

    var datalist = ArrayList<Data>()
    var pinnedPostList = ArrayList<Data>()
    private var pinnedPost: String = "0"
    var feedlist = ArrayList<Data>()
    var newCourseData = ArrayList<NewCourseData>()
    var testResultList = ArrayList<TestResult>()
    var liveTestData = ArrayList<LiveTestData>()
    var liveClassData = ArrayList<Datum>()
    var bannert_list = ArrayList<BannerData>()
    var masterAllCatTables: List<MasteAllCatTable> = java.util.ArrayList()
    var mastercatlist: List<MasterCat> = java.util.ArrayList()
    var page: Int = 1
    var feedsBinding: com.utkarshnew.android.databinding.ActivityFeedsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            feedsBinding =
                DataBindingUtil.setContentView(this@PinnedPostActivity, R.layout.activity_feeds)
            feedsBinding?.apply {
                feedsBinding?.feedbind = feedViewModel
                lifecycleOwner = this@PinnedPostActivity
            }

            Helper.enableScreenShot(this)
            no_data_found_RL = findViewById(R.id.no_data_found_RL)
            backBtn = findViewById(R.id.backBtn)
            feedsBinding!!.pulltoReferesh.isRefreshing = false
            feedsBinding!!.titleinnerRL.isVisible = false
            //feedsBinding!!.downarrowIV!!.isVisible = false
            feedsBinding!!.pinedPost.isVisible = false
            feedsBinding!!.filter!!.isVisible = false

            master_cat = intent.getStringExtra("mastercatid").toString()
            main_cat = intent.getStringExtra("maincat").toString()
            sub_cat = intent.getStringExtra("subcatid").toString()

            feedViewModel.progressvalue.value = "1"

            observer()

            manager = LinearLayoutManager(this@PinnedPostActivity)
            feedsBinding!!.feedRecyerlview.apply {
                feedAdapter = FeedAdapter(this@PinnedPostActivity)
                layoutManager = manager

                adapter = feedAdapter
            }

            feedsBinding!!.nestedScroll.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    if (loading) {
                        if (limitdata <= datalist.size) {
                            if (Helper.isConnected(this@PinnedPostActivity)) {
                                showProgressView()
                                page++
                                createApiBodyData()
                                loading = false
                            }

                        }


                    }
                }
            }

            feedsBinding!!.imageBack.setOnClickListener {
                finish()
            }
            backBtn!!.setOnClickListener {
                finish()
            }

            feedsBinding!!.pulltoReferesh.setOnRefreshListener {
                if (Helper.isConnected(this@PinnedPostActivity)) {
                    if (!response_booelan) {
                        response_booelan = true
                        if (feedAdapter!!.timer != null) {
                            feedAdapter!!.timer!!.cancel()
                            feedAdapter!!.timer!!.purge()
                        }
                        isPullToRefresh = true
                        feedsBinding!!.pulltoReferesh.isRefreshing = false
                        page = 1
                        section_posiiton = "0"
                        loading = true
                        datalist.clear()
                        feedlist.clear()
                        pinnedPostList.clear()
                        createApiBodyData()
                    } else {
                        feedsBinding!!.pulltoReferesh.isRefreshing = false

                    }

                } else {
                    Toast.makeText(
                        this@PinnedPostActivity,
                        "No Internet Connection",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
            createApiBodyData()
            //  }
            //createApiBodyData()
        } catch (e: Exception) {
            feedViewModel.progressvalue.value = "0"
            e.printStackTrace()
        }


    }


    fun createApiBodyData() {
        val masterdataencryptionData = EncryptionData()
        masterdataencryptionData.page = "$page"
        masterdataencryptionData.master_cat = master_cat
        masterdataencryptionData.main_cat = main_cat
        masterdataencryptionData.sub_cat = sub_cat
        masterdataencryptionData.is_pin = "1"
        val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
        val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
        feedViewModel.bodydata.value = masterdatadoseStrScr
    }


    fun observer() {
        feedViewModel.progressvalue.observe(this) {
            if (it.equals("0")) {
                Helper.dismissProgressDialog()
            } else {
                Helper.showProgressDialog(this@PinnedPostActivity)
            }
        }

        feedViewModel.bodydata.observe(this) {
            if (it != null) {
                feedViewModel.getFeedData()
            }
        }
        feedViewModel.adapter_bodydata.observe(this) {
            if (it != null) {
                when (feedViewModel.type.value) {
                    "Like" -> {
                        feedViewModel.getcourutine_adapter_post()
                    }
                    "GetComment" -> {
                        feedViewModel.getcourutine_adapter_post()
                    }
                    "AddComment" -> {
                        feedViewModel.getcourutine_adapter_post()
                    }
                    "Attempt Mcq" -> {
                        feedViewModel.getcourutine_adapter_post()
                    }
                    "Pin", "Unpin" -> {
                        feedViewModel.getcourutine_adapter_post()
                    }
                }

            }
        }
        feedViewModel.adapter_response.observe(this) {
            try {
                if (it != null && it.optString(Const.STATUS) == Const.TRUE) {
                    Constants.REFRESHFEED = "1"
                    when (feedViewModel.type.value) {
                        "Like" -> {
                            with(feedAdapter!!) {
                                this.change_posiiton()
                            }
                        }
                        "Pin" -> {
                            showToast("Post Pinned")
                            with(feedAdapter!!)
                            {
                                this.pinPost()
                            }
                        }

                        "Unpin" -> {
                            showToast("Post Unpinned")
                            with(feedAdapter!!)
                            {
                                this.unPinPost()
                            }
                        }
                        "GetComment" -> {
                            with(feedAdapter!!)
                            {
                                this.getCommentList(it.getJSONArray(Const.DATA))
                            }
                        }
                        "AddComment" -> {
                            with(feedAdapter!!)
                            {
                                this.addComment(it.optJSONObject(Const.DATA))
                            }
                        }
                        "Attempt Mcq" -> {
                            with(feedAdapter!!)
                            {
                                this.attempt_mcq()
                            }
                        }
                        else -> {
                        }
                    }

                } else {
                    RetrofitResponse.GetApiData(
                        this@PinnedPostActivity!!, if (it.has(Const.AUTH_CODE)) it.getString(
                            Const.AUTH_CODE
                        ) else "", it.getString(Const.MESSAGE), false
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        feedViewModel.jsonObjectmutable.observe(this) {
            if (it != null) {
                Log.d("viewmodel", "vm")
                hideProgressView()

                response_booelan = false

                if (it.optString(Const.STATUS) == Const.TRUE) {
                    val data: JSONObject = it.getJSONObject(Const.DATA)
                    feedJsonObject = data
                    limitdata += it.optInt("limit")
                    Log.d("limitdata", "$limitdata")
                    val dataJsonObject: JSONArray = feedJsonObject.getJSONArray(Const.posts)
                    if (dataJsonObject.length() > 0) {
                        if (page == 1) {


                            datalist.clear()
                            feedlist.clear()

                            for (i in 0 until dataJsonObject.length()) {
                                val dataObj: JSONObject = dataJsonObject.optJSONObject(i)
                                val feeddata = Gson().fromJson(dataObj.toString(), Data::class.java)
                                datalist.add(feeddata)
                            }
                            with(feedAdapter!!) {

                                feedlist.clear()
                                addFeed(datalist)
                                notifyDataSetChanged()
                            }
                        } else if (page > 1) {
                            loading = true
                            for (i in 0 until dataJsonObject.length()) {
                                val dataObj: JSONObject = dataJsonObject.optJSONObject(i)
                                val feeddata = Gson().fromJson(dataObj.toString(), Data::class.java)
                                datalist.add(feeddata)
                            }

                            feedAdapter!!.apply {
                                val currentSize: Int = itemCount
                                addFeed(datalist)
                                notifyItemRangeChanged(currentSize+1, itemCount);
                            }
                        }
                    } else {
                        if (datalist.size == 0) {

                            no_data_found_RL!!.visibility = View.VISIBLE
                            feedsBinding!!.pulltoReferesh.visibility = View.GONE
                        } else {
                            Toast.makeText(this, "No More Post found", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    limitdata = 0
                    if (datalist.size == 0) {
                        no_data_found_RL!!.visibility = View.VISIBLE
                        feedsBinding!!.pulltoReferesh.visibility = View.GONE
                    }
                    RetrofitResponse.GetApiData(
                        this,
                        if (it.has(Const.AUTH_CODE)) it.getString(Const.AUTH_CODE) else "",
                        it.getString(Const.MESSAGE),
                        false
                    )
                }
            }
        }
    }

    fun showProgressView() {
        feedsBinding!!.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressView() {
        if (feedsBinding!!.progressBar.visibility == View.VISIBLE)
            feedsBinding!!.progressBar.visibility = View.INVISIBLE
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (!item!!.title.equals(feedsBinding!!.toolbartitleTV.text.toString())) {
            for (selected_master_cat in selected_master_cat) {
                if (selected_master_cat.name.equals(item.title)) {
                    if (no_data_found_RL!!.visibility == View.VISIBLE) {
                        no_data_found_RL!!.visibility = View.GONE
                        feedsBinding!!.pulltoReferesh.visibility = View.VISIBLE
                    }

                    loading = true
                    main_cat = selected_master_cat.id
                    selectedsub_all_cat.clear()
                    for (masteAllCatTable1 in masterAllCatTables) {
                        if (main_cat.equals(masteAllCatTable1.parent_id, ignoreCase = true)) {
                            selectedsub_all_cat.add(masteAllCatTable1)
                        }
                    }
                    if (selectedsub_all_cat.size > 0)
                        sub_cat = selectedsub_all_cat[0].id

                    datalist.clear()
                    feedsBinding!!.toolbartitleTV.text = item.title
                    page = 1
                    pinnedPostList.clear()

                    if (feedAdapter!!.timer != null) {
                        feedAdapter!!.timer!!.cancel()
                        feedAdapter!!.timer!!.purge()
                        Log.d("abcdee", "cancel")
                    }
                    feedViewModel.progressvalue.value = "1"
                    createApiBodyData()
                    break
                }
            }
        }
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (feedAdapter!!.timer != null) {
            feedAdapter!!.timer!!.cancel()
            feedAdapter!!.timer!!.purge()
            Log.d("abcdee", "cancel")


        }
    }
}