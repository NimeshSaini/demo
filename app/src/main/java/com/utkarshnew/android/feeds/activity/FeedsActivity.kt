package com.utkarshnew.android.feeds.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import android.app.Application.ActivityLifecycleCallbacks
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utkarshnew.android.EncryptionModel.EncryptionData
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.OnSingleClickListener
import com.utkarshnew.android.R
import com.utkarshnew.android.Room.UtkashRoom
import com.utkarshnew.android.Utils.*
import com.utkarshnew.android.Utils.Network.API
import com.utkarshnew.android.Utils.Network.APIInterface
import com.utkarshnew.android.Utils.Network.NetworkCall
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse
import com.utkarshnew.android.databinding.ActivityFeedsBinding
import com.utkarshnew.android.feeds.adapters.FeedAdapter
import com.utkarshnew.android.feeds.adapters.MainCatAdapter
import com.utkarshnew.android.feeds.dataclass.*
import com.utkarshnew.android.feeds.showToast
import com.utkarshnew.android.feeds.viewmodel.FeedViewModel
import com.utkarshnew.android.home.Constants.REFRESHFEED
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
import retrofit2.Call
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.LinkedHashSet


@AndroidEntryPoint
class FeedsActivity : AppCompatActivity(), NetworkCall.MyNetworkCallBack, PopupMenu.OnMenuItemClickListener {
    private var refreshCount: Int = 0
    var backBtn: Button? = null
    private lateinit var feedJsonObject: JSONObject
    var isPullToRefresh = false
    var liveClassStatus: String = "0"
    var liveTestStatus: String = "0"
    var section_posiiton = "0"
    var response_booelan = false
    var limitdata: Int = 0


    var type_subcatfilter = false
    var type_posttypefilter = false
    var subcatspinner: TextView? = null
    var posttypeytext: TextView? = null

    var posttypename = "All"
    var posttypeid = "0"

    /*  @Inject
      lateinit var feedAdapter: FeedAdapter*/

    var feedAdapter: FeedAdapter? = null
    var loading = true
    var no_data_found_RL: RelativeLayout? = null
    val feedViewModel: FeedViewModel by viewModels()
    var main_cat = "";
    var main_cat_name = "";
    var manager: LinearLayoutManager? = null
    var sub_cat = "";
    var sub_cat_name = "";


    var sub_cat_filter = "";
    var sub_cat_name_filter = "";

    var is_filterbutton = false;


    var master_cat = "";
    var master_cat_name = "";
    var selected_master_cat = ArrayList<MasteAllCatTable>()
    var selectedsub_all_cat = ArrayList<MasteAllCatTable>()

    var utkashRoom: UtkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext())
    var datalist = ArrayList<Data>()
    var posttypelist = ArrayList<PostType>()
    var posiitonwiselist = ArrayList<Data>()
    var pinnedPostList = ArrayList<Data>()
    private var pinnedPost: String = "0"
    var feedlist = ArrayList<Data>()
    var newCourseData = ArrayList<NewCourseData>()
    var testResultList = ArrayList<TestResult>()
    var liveTestData = ArrayList<LiveTestData>()
    var liveClassData = ArrayList<Datum>()
    var bannert_list = ArrayList<BannerData>()
    var masterAllCatTables: List<MasteAllCatTable> = ArrayList()
    var mastercatlist = ArrayList<MasterCat>()
    var preferencesArrayList = ArrayList<com.utkarshnew.android.pojo.Userinfo.Data.Preferences>()

    var popUp: PopupWindow? = null
    var locale_time = 0L
    var page: Int = 1
    var feedsBinding: ActivityFeedsBinding? = null
    var feedParentData: List<PostDataTable> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            feedsBinding =
                DataBindingUtil.setContentView(this@FeedsActivity, R.layout.activity_feeds)
            feedsBinding?.apply {
                feedsBinding?.feedbind = feedViewModel
                lifecycleOwner = this@FeedsActivity
            }

            Helper.enableScreenShot(this)
            no_data_found_RL = findViewById(R.id.no_data_found_RL)
            backBtn = findViewById(R.id.backBtn)
            feedsBinding!!.pulltoReferesh.isRefreshing = false
            val data = SharedPreference.getInstance().loggedInUser
            preferencesArrayList = data.preferences
            locale_time = SharedPreference.getInstance().getLong("time")


            feedViewModel.progressvalue.value = "1"

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (utkashRoom.masterAllCatDao.isRecordExistsUserId(MakeMyExam.userId)) {
                        if (locale_time.toInt() > 0) {
                            val millis: Long = MakeMyExam.time_server - locale_time
                            val mins = (millis / (1000 * 60) % 60).toInt()
                            Log.d("mins", "onCreate: " + mins)
                            if (mins > 10) {
                                utkashRoom.feedDao.deletedata()
                            }
                        }
                        masterAllCatTables =
                            utkashRoom.masterAllCatDao.getmaster_allcat(MakeMyExam.userId)
                        if (preferencesArrayList.size > 0) {
                            val tempmastercastlist: MutableList<MasterCat> =
                                ArrayList(utkashRoom.mastercatDao.getmastercat(MakeMyExam.userId))
                            val subcat_prefence = ArrayList<MasteAllCatTable>()

                            val maincat_prefence: MutableSet<MasteAllCatTable> = LinkedHashSet()
                            val masterset: MutableSet<String> = HashSet()
                            for (masteAllCatTable in masterAllCatTables) {
                                for (preferences in preferencesArrayList) {
                                    if (masteAllCatTable.id.equals(
                                            preferences.sub_cat,
                                            ignoreCase = true
                                        )
                                    ) {
                                        subcat_prefence.add(masteAllCatTable)
                                        masterset.add(masteAllCatTable.master_type)
                                    }
                                }
                            }
                            for (masterCat in tempmastercastlist) {
                                for (masterid in masterset) {
                                    if (masterCat.id.equals(masterid, ignoreCase = true)) {
                                        mastercatlist.add(masterCat)
                                    }
                                }
                            }

                            if (mastercatlist.size > 0) {
                                master_cat = mastercatlist[0].id
                                master_cat_name = mastercatlist[0].cat
                            }
                            val getmaster_allcat_parentid =
                                utkashRoom.masterAllCatDao.getmaster_allcat_parentid("0")
                            for (masteAllCatTable1 in getmaster_allcat_parentid) {
                                for (name_sublist in subcat_prefence) {
                                    if (masteAllCatTable1.id == name_sublist.parent_id && masteAllCatTable1.parent_id.equals(
                                            "0",
                                            ignoreCase = true
                                        ) && masteAllCatTable1.master_type.equals(
                                            name_sublist.master_type,
                                            ignoreCase = true
                                        )
                                    ) {
                                        maincat_prefence.add(masteAllCatTable1)
                                    }
                                }
                            }
                            if (maincat_prefence.size > 0) {
                                selected_master_cat.addAll(maincat_prefence)
                            }
                            if (selected_master_cat.size > 0) {
                                main_cat_name = selected_master_cat[0].name
                                main_cat = selected_master_cat[0].id

                                for (masteAllCatTable1 in subcat_prefence) {
                                    if (main_cat.equals(
                                            masteAllCatTable1.parent_id,
                                            ignoreCase = true
                                        )
                                    ) {
                                        selectedsub_all_cat.add(masteAllCatTable1)
                                    }
                                }
                                if (selectedsub_all_cat.size > 0) {
                                    sub_cat_name = "All"
                                    sub_cat = "0"
                                    withContext(Dispatchers.Main) {
                                        feedsBinding!!.toolbartitleTV.text = main_cat_name
                                        popUp = popupWindowPart()
                                        local_data(false)
                                    }
                                }
                            }
                        }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main)
                    {
                        feedViewModel.progressvalue.value = "0"
                    }
                    e.printStackTrace()
                }
            }




            observer()
            manager = LinearLayoutManager(this@FeedsActivity)
            feedsBinding!!.feedRecyerlview.apply {
                feedAdapter = FeedAdapter(this@FeedsActivity)
                layoutManager = manager

                adapter = feedAdapter

            }



            feedsBinding!!.nestedScroll.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    if (loading) {
                        if (limitdata <= datalist.size) {
                            if (Helper.isConnected(this@FeedsActivity)) {
                                showProgressView()
                                page++
                                createApiBodyData()
                                loading = false
                            }

                        }


                    }
                }
            }

            feedsBinding!!.pinedPost.setOnClickListener {
                Intent(this, PinnedPostActivity::class.java).apply {
                    putExtra("maincat", main_cat)
                    putExtra("mastercatid", master_cat)
                    putExtra("subcatid", sub_cat)
                }.also {
                    Helper.gotoActivity(it, this)
                }


            }

            feedsBinding!!.filter!!.setOnClickListener(OnSingleClickListener {
                filterDailog()

            })

            feedsBinding!!.imageBack.setOnClickListener {
                finish()
            }
            backBtn!!.setOnClickListener {
                finish()
            }

            feedsBinding!!.titleinnerRL.setOnClickListener {
                if (!Helper.isNetworkConnected(this@FeedsActivity)) {
                    Helper.showInternetToast(this@FeedsActivity)
                } else {
                    popUp!!.showAsDropDown(feedsBinding!!.titleinnerRL, 0, 0)
                }
            }
            feedsBinding!!.pulltoReferesh.setOnRefreshListener {
                if (Helper.isConnected(this@FeedsActivity)) {
                    if (!response_booelan) {
                        response_booelan = true
                        if (feedAdapter!!.timer != null) {
                            feedAdapter!!.timer!!.cancel()
                            feedAdapter!!.timer!!.purge()
                        }
                        posttypename = "All"
                        posttypeid = "0"
                        isPullToRefresh = true
                        feedsBinding!!.filter!!.setImageResource(R.drawable.filter_icon)

                        feedsBinding!!.pulltoReferesh.isRefreshing = false
                        page = 1
                        sub_cat_name = "All"
                        sub_cat = "0"
                        section_posiiton = "0"
                        loading = true
                        datalist.clear()
                        feedlist.clear()
                        posiitonwiselist.clear()
                        pinnedPostList.clear()
                        createApiBodyData()
                    } else {
                        feedsBinding!!.pulltoReferesh.isRefreshing = false

                    }

                } else {
                    Toast.makeText(this@FeedsActivity, "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        } catch (e: Exception) {
            feedViewModel.progressvalue.value = "0"
            e.printStackTrace()
        }


    }

    private fun filterDailog() {
        val watchlist = BottomSheetDialog(this, R.style.videosheetDialogTheme)
        watchlist.setContentView(R.layout.feed_filter)
        Objects.requireNonNull<Window>(watchlist.getWindow()).attributes.windowAnimations =
            R.style.PauseDialogAnimation
        watchlist.setCancelable(false)
        watchlist.setCanceledOnTouchOutside(false)
        val cancel = watchlist.findViewById<ImageView>(R.id.cancel)
        val filterdata = watchlist.findViewById<Button>(R.id.filterdata)
        val subcat = watchlist.findViewById<RelativeLayout>(R.id.subcat)
        val posttype = watchlist.findViewById<RelativeLayout>(R.id.posttype)
        posttypeytext = watchlist.findViewById<TextView>(R.id.posttypeytext)
        subcatspinner = watchlist.findViewById<TextView>(R.id.subcatspinner)

        subcatspinner!!.text = sub_cat_name
        if (posttypelist.size > 0) {
            if (posttypeid.equals("0")) {
                posttypeytext!!.text = posttypelist[0].title
                posttypename = posttypelist[0].title
                posttypeid = posttypelist[0].id
            } else {
                posttypeytext!!.text = posttypename
            }
        }

        subcat!!.setOnClickListener {
            val popupMenu = PopupMenu(this, subcat, Gravity.LEFT)
            popupMenu.menu.add("All")
            for (i in selectedsub_all_cat.indices) {
                popupMenu.menu.add(selectedsub_all_cat[i].name)
            }
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
            type_subcatfilter = true
        }
        posttype!!.setOnClickListener {
            val popupMenu = PopupMenu(this, posttype, Gravity.LEFT)
            for (i in posttypelist.indices) {
                popupMenu.menu.add(posttypelist[i].title)
            }
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
            type_posttypefilter = true
        }
        posttypeytext!!.setOnClickListener {
            val popupMenu = PopupMenu(this, posttype, Gravity.LEFT)
            for (i in posttypelist.indices) {
                popupMenu.menu.add(posttypelist[i].title)
            }
            popupMenu.setOnMenuItemClickListener(this)
            popupMenu.show()
            type_posttypefilter = true
        }
        cancel!!.setOnClickListener {
            type_subcatfilter = false
            type_posttypefilter = false
            sub_cat_filter = ""
            sub_cat_name_filter = ""
            is_filterbutton = false
            watchlist.dismiss()
            watchlist.cancel()
        }
        filterdata!!.setOnClickListener {

            feedViewModel.progressvalue.value = "1"
            if (feedAdapter!!.timer != null) {
                feedAdapter!!.timer!!.cancel()
                feedAdapter!!.timer!!.purge()
            }
            if (sub_cat_filter.isNotEmpty()) {
                sub_cat = sub_cat_filter
                sub_cat_name = sub_cat_name_filter
            }
            feedsBinding!!.filter!!.setImageResource(R.drawable.filter_icon_tick)
            page = 1
            sub_cat_filter = ""
            sub_cat_name_filter = ""
            datalist.clear()
            feedlist.clear()
            posiitonwiselist.clear()
            pinnedPostList.clear()
            is_filterbutton = true
            createApiBodyData()
            watchlist.dismiss()
            watchlist.cancel()
        }
        watchlist.setOnCancelListener {
            it.dismiss()
            it.cancel()
            type_subcatfilter = false
            type_posttypefilter = false
        }
        watchlist.show()
    }

    private fun local_data(filter: Boolean) {
        if (sub_cat.equals("0")) {
            if (filter) {
                feedParentData = utkashRoom.feedDao.retrievePostData_viaposttype_withoutsubcat(
                    master_cat,
                    main_cat,
                    posttypeid
                )
            } else
                feedParentData =
                    utkashRoom.feedDao.retrievePostData_withoutsubcat(master_cat, main_cat)

        } else
            if (filter) {
                feedParentData = utkashRoom.feedDao.retrievePostData_viaposttype(
                    master_cat,
                    main_cat,
                    sub_cat,
                    posttypeid
                )
            } else
                feedParentData = utkashRoom.feedDao.retrievePostData(master_cat, main_cat, sub_cat)

        if (feedParentData.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                posttypelist.clear()
                if (SharedPreference.getInstance()
                        .getString("post_type") != null && SharedPreference.getInstance()
                        .getString("post_type").isNotEmpty()
                ) {
                    val posttypearray =
                        JSONArray(SharedPreference.getInstance().getString("post_type"))

                    (0 until posttypearray.length()).forEach {
                        val dataObj: JSONObject = posttypearray.optJSONObject(it)
                        val posttype = Gson().fromJson(dataObj.toString(), PostType::class.java)
                        posttypelist.add(posttype)
                    }
                    val postType = PostType("0", "All")
                    posttypelist.add(0, postType)
                }
            }
            if (no_data_found_RL!!.isVisible) {
                no_data_found_RL!!.visibility = View.GONE
                feedsBinding!!.pulltoReferesh.visibility = View.VISIBLE
            }
            section_posiiton = feedParentData.get(0).sectionposiiton
            if (section_posiiton.equals("")) {
                section_posiiton = "0"
            }
            page = feedParentData.get(feedParentData.size - 1).page.toInt()
            limitdata = feedParentData.get(feedParentData.size - 1).limit.toInt()

            for (postDataTable in feedParentData) {
                val data = Data(
                    postDataTable.created,
                    postDataTable.id,
                    Gson().fromJson(postDataTable.json, object : TypeToken<Json?>() {}.type),
                    postDataTable.meta_url,
                    postDataTable.thumbnail,
                    postDataTable.url,
                    postDataTable.modified,
                    postDataTable.my_like,
                    postDataTable.name,
                    postDataTable.post_type,
                    postDataTable.profile_picture,
                    postDataTable.status,
                    postDataTable.sub_cat_id,
                    postDataTable.text,
                    postDataTable.total_comments,
                    postDataTable.total_likes,
                    postDataTable.user_id,
                    Gson().fromJson(
                        postDataTable.newCourseData,
                        object : TypeToken<List<NewCourseData>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.livetest,
                        object : TypeToken<List<LiveTestData>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.liveclass,
                        object : TypeToken<List<Datum>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.testResult,
                        object : TypeToken<List<TestResult>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.bannerlist,
                        object : TypeToken<List<BannerData>?>() {}.type
                    ),
                    postDataTable.iscommentenable,
                    my_pinned = postDataTable.my_pinned ?: "0",
                    description = postDataTable.description ?: "",
                    master_cat_id = postDataTable.parentId,
                    main_cat = postDataTable.masterCat,
                    sub_cat = postDataTable.sub_cat_id
                )
                datalist.add(data)

                if (postDataTable.liveClassStatus == "1") {
                    liveClassData = Gson().fromJson(
                        postDataTable.liveclass,
                        object : TypeToken<List<Datum>?>() {}.type
                    )
                }


                if (postDataTable.liveTestStatus == "1") {
                    liveTestData = Gson().fromJson(
                        postDataTable.livetest,
                        object : TypeToken<List<LiveTestData>?>() {}.type
                    )
                }
            }

        } else {
            page = 1
        }
        if (datalist.size > 0) {
            feedViewModel.progressvalue.value = "0"
            with(feedAdapter!!) {
                posiitonwiselist.clear()
                pinnedPostList.clear()
                feedlist.clear()
                for (data in datalist) {
                    if (data.post_type.equals("1090") || data.post_type.equals("1091") || data.post_type.equals(
                            "1092"
                        ) || data.post_type.equals("1093")
                    ) {
                        posiitonwiselist.add(data)
                    } else {
                        feedlist.add(data)
                    }
                }
                datalist.clear()
                datalist.addAll(feedlist)
                feedlist.clear()

                if (datalist.size > section_posiiton.toInt()) {
                    datalist.addAll(section_posiiton.toInt() + 1, posiitonwiselist)
                } else {
                    datalist.addAll(posiitonwiselist);
                }

                posiitonwiselist.clear()
                pinnedPostList.clear()

                addFeed(datalist)
                notifyDataSetChanged()
            }
        } else {
            createApiBodyData()
        }
    }


    fun createApiBodyData() {
        val masterdataencryptionData = EncryptionData()
        masterdataencryptionData.page = "$page"
        masterdataencryptionData.master_cat = master_cat
        masterdataencryptionData.main_cat = main_cat
        masterdataencryptionData.is_pin = "0"
        masterdataencryptionData.post_type = posttypeid
        masterdataencryptionData.sub_cat = sub_cat
        val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
        Log.d("asdfgh", "createApiBodyData: " + masterdataencryptionDatadoseStr)
        val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
        feedViewModel.bodydata.value = masterdatadoseStrScr
    }


    fun observer() {
        feedViewModel.progressvalue.observe(this) {
            if (it.equals("0")) {
                Helper.dismissProgressDialog()
            } else {
                Helper.showProgressDialog(this@FeedsActivity)
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
                    when (feedViewModel.type.value) {
                        "Like" -> {
                            with(feedAdapter) {
                                this!!.change_posiiton()
                            }
                        }
                        "Pin" -> {
                            showToast(it.optString(Const.MESSAGE))
                            with(feedAdapter)
                            {
                                this!!.pinPost()
                            }
                        }

                        "Unpin" -> {
                            showToast(it.optString(Const.MESSAGE))
                            with(feedAdapter)
                            {
                                this!!.unPinPost()
                            }
                        }
                        "GetComment" -> {
                            with(feedAdapter)
                            {
                                this!!.getCommentList(it.getJSONArray(Const.DATA))
                            }
                        }
                        "AddComment" -> {
                            with(feedAdapter)
                            {
                                this!!.addComment(it.optJSONObject(Const.DATA))
                            }
                        }
                        "Attempt Mcq" -> {
                            with(feedAdapter)
                            {
                                this!!.attempt_mcq()
                            }
                        }
                        else -> {
                        }
                    }

                } else {
                    RetrofitResponse.GetApiData(
                        this@FeedsActivity!!, if (it.has(Const.AUTH_CODE)) it.getString(
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


                    val dataJsonObject: JSONArray = feedJsonObject.getJSONArray(Const.posts)
                    if (dataJsonObject.length() > 0) {
                        if (no_data_found_RL!!.isVisible) {
                            no_data_found_RL!!.visibility = View.GONE
                            feedsBinding!!.pulltoReferesh.visibility = View.VISIBLE
                        }
                        if (isPullToRefresh) {
                            limitdata = 0
                        }
                        limitdata += it.optInt("limit")
                        Log.d("limitdata", "$limitdata")

                        if (page == 1) {
                            if (feedJsonObject.has("post_type")) {
                                val posttypearray: JSONArray =
                                    feedJsonObject.getJSONArray("post_type")
                                SharedPreference.getInstance()
                                    .putString("post_type", posttypearray.toString())
                                posttypelist.clear()
                                (0 until posttypearray.length()).forEach {
                                    val dataObj: JSONObject = posttypearray.optJSONObject(it)
                                    val posttype =
                                        Gson().fromJson(dataObj.toString(), PostType::class.java)
                                    posttypelist.add(posttype)
                                }
                            }

                            var postType = PostType("", "All")
                            posttypelist.add(0, postType)


                            if (isPullToRefresh) {
                                if (refreshCount >= 2) {
                                    utkashRoom.feedDao.deletePosts_via_id(main_cat)
                                } else {
                                    utkashRoom.feedDao.deleteSubCatFeed(
                                        main_cat,
                                        sub_cat,
                                        "1092",
                                        "1093"
                                    )
                                }
                            }

                            if (feedJsonObject.has("section_position")) {
                                section_posiiton = feedJsonObject.optString("section_position")
                            }

                            val dataBanner: JSONArray = feedJsonObject.getJSONArray(Const.banners)
                            bannert_list.clear()
                            datalist.clear()
                            newCourseData.clear()
                            testResultList.clear()

                            posiitonwiselist.clear()
                            feedlist.clear()


                            if (dataBanner.length() > 0) {
                                for (i in 0 until dataBanner.length()) {
                                    val dataObj: JSONObject = dataBanner.optJSONObject(i)
                                    val feeddata =
                                        Gson().fromJson(dataObj.toString(), BannerData::class.java)
                                    bannert_list.add(feeddata)
                                }

                                val optionlist = ArrayList<Option>()
                                val json = Json("", optionlist, "", "", "", "", "", "", "")
                                val data = Data(
                                    "",
                                    "",
                                    json,
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "1089",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    newCourseData,
                                    liveTestData,
                                    liveClassData,
                                    testResultList,
                                    bannert_list, "0", section_posiiton, limitdata, my_pinned = "0"
                                )
                                datalist.add(data)
                                if (!is_filterbutton) {
                                    val postDataTable = PostDataTable()
                                    postDataTable.created = ""
                                    postDataTable.id = ""
                                    postDataTable.parentId = master_cat
                                    postDataTable.masterCat = main_cat
                                    postDataTable.sub_cat_id = sub_cat
                                    postDataTable.json = Gson().toJson(json)
                                    postDataTable.meta_url = ""
                                    postDataTable.thumbnail = ""
                                    postDataTable.url = ""
                                    postDataTable.modified = ""
                                    postDataTable.my_like = ""
                                    postDataTable.name = ""
                                    postDataTable.post_type = "1089"
                                    postDataTable.profile_picture = ""
                                    postDataTable.status = ""
                                    postDataTable.text = ""
                                    postDataTable.total_comments = ""
                                    postDataTable.total_likes = ""
                                    postDataTable.user_id = ""
                                    postDataTable.newCourseData = Gson().toJson(newCourseData)
                                    postDataTable.livetest = Gson().toJson(liveTestData)
                                    postDataTable.liveclass = Gson().toJson(liveClassData)
                                    postDataTable.testResult = Gson().toJson(testResultList)
                                    postDataTable.bannerlist = Gson().toJson(bannert_list)
                                    postDataTable.liveClassStatus = "0"
                                    postDataTable.liveTestStatus = "0"
                                    postDataTable.page = "$page"
                                    postDataTable.iscommentenable = "0"
                                    postDataTable.sectionposiiton = section_posiiton
                                    postDataTable.limit = "$limitdata"
                                    postDataTable.my_pinned = pinnedPost

                                    utkashRoom.feedDao.insertPost(postDataTable)
                                }

                            }

                            if (is_filterbutton) {
                                for (i in 0 until dataJsonObject.length()) {
                                    val dataObj: JSONObject = dataJsonObject.optJSONObject(i)
                                    val feeddata =
                                        Gson().fromJson(dataObj.toString(), Data::class.java)
                                    datalist.add(feeddata)
                                }
                            } else {
                                for (i in 0 until dataJsonObject.length()) {
                                    val dataObj: JSONObject = dataJsonObject.optJSONObject(i)
                                    val feeddata =
                                        Gson().fromJson(dataObj.toString(), Data::class.java)
                                    datalist.add(feeddata)

                                    val postDataTable = PostDataTable()
                                    postDataTable.created = feeddata.created
                                    postDataTable.id = feeddata.id
                                    postDataTable.json = Gson().toJson(feeddata.json)
                                    postDataTable.meta_url = feeddata.meta_url
                                    postDataTable.thumbnail = feeddata.thumbnail

                                    postDataTable.url = feeddata.url ?: ""
                                    postDataTable.modified = feeddata.modified
                                    postDataTable.my_like = feeddata.my_like
                                    postDataTable.name = feeddata.name
                                    postDataTable.post_type = feeddata.post_type
                                    postDataTable.profile_picture = feeddata.profile_picture
                                    postDataTable.status = feeddata.status

                                    postDataTable.parentId = master_cat
                                    postDataTable.masterCat = main_cat
                                    postDataTable.sub_cat_id = sub_cat


                                    postDataTable.text = feeddata.text
                                    postDataTable.total_comments = feeddata.total_comments
                                    postDataTable.total_likes = feeddata.total_likes
                                    postDataTable.user_id = feeddata.user_id
                                    postDataTable.newCourseData = Gson().toJson(
                                        feeddata.newCourseData ?: emptyList<NewCourseData>()
                                    )
                                    postDataTable.livetest = Gson().toJson(feeddata.livetest)
                                    postDataTable.liveclass = Gson().toJson(feeddata.liveclass)
                                    postDataTable.testResult =
                                        Gson().toJson(
                                            feeddata.testResult ?: emptyList<TestResult>()
                                        )
                                    postDataTable.bannerlist =
                                        Gson().toJson(
                                            feeddata.bannerlist ?: emptyList<BannerData>()
                                        )
                                    postDataTable.liveClassStatus = "0"
                                    postDataTable.liveTestStatus = "0"
                                    postDataTable.page = "$page"
                                    postDataTable.iscommentenable = feeddata.is_comment_enable
                                    postDataTable.sectionposiiton = section_posiiton
                                    postDataTable.limit = "$limitdata"
                                    postDataTable.my_pinned = feeddata.my_pinned
                                    postDataTable.description = feeddata.description
                                    utkashRoom.feedDao.insertPost(postDataTable)
                                }

                            }

                            val new_courses: JSONArray = feedJsonObject.getJSONArray("new_courses")
                            if (new_courses.length() > 0) {
                                var bannert_list = ArrayList<BannerData>()
                                for (i in 0 until new_courses.length()) {
                                    val dataObj: JSONObject = new_courses.optJSONObject(i)
                                    val feeddata = Gson().fromJson(
                                        dataObj.toString(),
                                        NewCourseData::class.java
                                    )
                                    newCourseData.add(feeddata)
                                }

                                val optionlist = ArrayList<Option>()
                                val json = Json("", optionlist, "", "", "", "", "", "", "")
                                val data = Data(
                                    "",
                                    "",
                                    json,
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "1090",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    newCourseData,
                                    liveTestData,
                                    liveClassData,
                                    testResultList,
                                    bannert_list,
                                    "0",
                                    section_posiiton, limitdata, my_pinned = "0"
                                )
                                posiitonwiselist.add(data)

                                /**
                                 * Save Into Database
                                 */
                                if (!is_filterbutton) {
                                    val postDataTable = PostDataTable()
                                    postDataTable.created = ""
                                    postDataTable.id = ""
                                    postDataTable.json = Gson().toJson(json)
                                    postDataTable.meta_url = ""
                                    postDataTable.thumbnail = ""
                                    postDataTable.url = ""
                                    postDataTable.modified = ""
                                    postDataTable.my_like = ""
                                    postDataTable.name = ""
                                    postDataTable.post_type = "1090"
                                    postDataTable.profile_picture = ""
                                    postDataTable.status = ""

                                    postDataTable.parentId = master_cat
                                    postDataTable.masterCat = main_cat
                                    postDataTable.sub_cat_id = sub_cat

                                    postDataTable.text = ""
                                    postDataTable.total_comments = ""
                                    postDataTable.total_likes = ""
                                    postDataTable.user_id = ""
                                    postDataTable.newCourseData = Gson().toJson(newCourseData)
                                    postDataTable.livetest = Gson().toJson(liveTestData)
                                    postDataTable.liveclass = Gson().toJson(liveClassData)
                                    postDataTable.testResult = Gson().toJson(testResultList)
                                    postDataTable.bannerlist = Gson().toJson(bannert_list)
                                    postDataTable.liveClassStatus = "0"
                                    postDataTable.liveTestStatus = "0"
                                    postDataTable.page = "$page"
                                    postDataTable.iscommentenable = "0"
                                    postDataTable.sectionposiiton = section_posiiton
                                    postDataTable.limit = "$limitdata"
                                    postDataTable.my_pinned = pinnedPost

                                    utkashRoom.feedDao.insertPost(postDataTable)
                                }

                            }

                            val results: JSONArray = feedJsonObject.getJSONArray("results")
                            if (results.length() > 0) {
                                var bannert_list = ArrayList<BannerData>()

                                val newCourseData = ArrayList<NewCourseData>()
                                for (i in 0 until results.length()) {
                                    val dataObj: JSONObject = results.optJSONObject(i)
                                    val feeddata = Gson().fromJson(
                                        dataObj.toString(),
                                        TestResult::class.java
                                    )
                                    testResultList.add(feeddata)
                                }
                                val optionlist = ArrayList<Option>()
                                val json = Json("", optionlist, "", "", "", "", "", "", "")
                                val data = Data(
                                    "",
                                    "",
                                    json,
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "1091",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    newCourseData,
                                    liveTestData,
                                    liveClassData,
                                    testResultList,
                                    bannert_list, "0",
                                    section_posiiton, limitdata, my_pinned = "0"
                                )
                                posiitonwiselist.add(data)

                                /**
                                 * Save Into Database
                                 */
                                if (!is_filterbutton) {
                                    val postDataTable = PostDataTable()
                                    postDataTable.created = ""
                                    postDataTable.id = ""
                                    //postDataTable.masterCat = main_cat
                                    postDataTable.json = Gson().toJson(json)
                                    postDataTable.meta_url = ""
                                    postDataTable.thumbnail = ""
                                    postDataTable.url = ""
                                    postDataTable.modified = ""
                                    postDataTable.my_like = ""
                                    postDataTable.name = ""
                                    postDataTable.post_type = "1091"
                                    postDataTable.parentId = master_cat
                                    postDataTable.masterCat = main_cat
                                    postDataTable.sub_cat_id = sub_cat

                                    postDataTable.profile_picture = ""
                                    postDataTable.status = ""
                                    // postDataTable.sub_cat_id = ""
                                    postDataTable.text = ""
                                    postDataTable.total_comments = ""
                                    postDataTable.total_likes = ""
                                    postDataTable.user_id = ""
                                    postDataTable.newCourseData = Gson().toJson(newCourseData)
                                    postDataTable.livetest = Gson().toJson(liveTestData)
                                    postDataTable.liveclass = Gson().toJson(liveClassData)
                                    postDataTable.testResult = Gson().toJson(testResultList)
                                    postDataTable.bannerlist = Gson().toJson(bannert_list)
                                    postDataTable.liveClassStatus = "0"
                                    postDataTable.liveTestStatus = "0"
                                    postDataTable.page = "$page"
                                    postDataTable.iscommentenable = "0"
                                    postDataTable.sectionposiiton = section_posiiton
                                    postDataTable.limit = "$limitdata"
                                    postDataTable.my_pinned = pinnedPost

                                    utkashRoom.feedDao.insertPost(postDataTable)
                                }

                            }


                            val now: Long = (data.optLong("time") * 1000)
                            var then: Long = 0
                            then = when {
                                liveClassData.isNotEmpty() -> {
                                    liveClassData[liveClassData.size - 1].cd_time
                                }
                                liveTestData.isNotEmpty() -> {
                                    liveTestData[liveTestData.size - 1].cd_time
                                }
                                else -> {
                                    data.optLong("cd_time")
                                }
                            }


                            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now - then)
                            if (isPullToRefresh) {
                                isPullToRefresh = false
                                if (refreshCount == 2 || minutes >= 10) {
                                    refreshCount = 0
                                    hitApiForLiveClass()

                                } else {
                                    refreshCount++
                                    val bannert_list = ArrayList<BannerData>()
                                    val optionlist = ArrayList<Option>()
                                    if (posttypeid.equals("0")) {
                                        val postData = utkashRoom.feedDao.retrievePostData(
                                            master_cat,
                                            main_cat,
                                            sub_cat
                                        )
                                        updatePostData(postData)
                                    } else {
                                        val postData =
                                            utkashRoom.feedDao.retrievePostData_viaposttype(
                                                master_cat,
                                                main_cat,
                                                sub_cat,
                                                posttypeid
                                            )
                                        updatePostData(postData)
                                    }

                                    if (liveClassData.isNotEmpty()) {
                                        val json = Json("", optionlist, "", "", "", "", "", "", "")
                                        val data = Data(
                                            "",
                                            "",
                                            json,
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "1093",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            newCourseData,
                                            liveTestData,
                                            liveClassData,
                                            testResultList,
                                            bannert_list,
                                            "0",
                                            section_posiiton,
                                            limitdata,
                                            my_pinned = "0"
                                        )
                                        posiitonwiselist.add(data)


                                    }
                                    if (liveTestData.isNotEmpty()) {
                                        val json = Json("", optionlist, "", "", "", "", "", "", "")
                                        val data = Data(
                                            "",
                                            "",
                                            json,
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "1092",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            newCourseData,
                                            liveTestData,
                                            liveClassData,
                                            testResultList,
                                            bannert_list, "0",
                                            section_posiiton, limitdata, my_pinned = "0"
                                        )
                                        posiitonwiselist.add(data)
                                    }
                                    with(feedAdapter!!) {

                                        if (datalist.size > section_posiiton.toInt()) {
                                            datalist.addAll(
                                                section_posiiton.toInt() + 1,
                                                posiitonwiselist
                                            )
                                        } else {
                                            datalist.addAll(posiitonwiselist);
                                        }

                                        posiitonwiselist.clear()
                                        feedlist.clear()

                                        addFeed(datalist)
                                        notifyDataSetChanged()


                                    }
                                }
                            } else {
                                if (!is_filterbutton) {
                                    hitApiForLiveClass()
                                } else {
                                    with(feedAdapter!!) {
                                        if (datalist.size > section_posiiton.toInt()) {
                                            datalist.addAll(
                                                section_posiiton.toInt() + 1,
                                                posiitonwiselist
                                            )
                                        } else {
                                            datalist.addAll(posiitonwiselist);
                                        }
                                        addFeed(datalist)
                                        notifyDataSetChanged()

                                    }
                                }

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
                                notifyItemRangeChanged(currentSize + 1, itemCount);
                            }

                            CoroutineScope(Dispatchers.IO).launch {

                                if (!is_filterbutton) {

                                    for (i in 0 until dataJsonObject.length()) {
                                        val dataObj: JSONObject = dataJsonObject.optJSONObject(i)
                                        val feeddata =
                                            Gson().fromJson(dataObj.toString(), Data::class.java)

                                        val postDataTable = PostDataTable()
                                        postDataTable.created = feeddata.created
                                        postDataTable.id = feeddata.id
                                        postDataTable.json = Gson().toJson(feeddata.json)
                                        postDataTable.meta_url = feeddata.meta_url
                                        postDataTable.thumbnail = feeddata.thumbnail
                                        postDataTable.url = feeddata.url ?: ""
                                        postDataTable.modified = feeddata.modified
                                        postDataTable.my_like = feeddata.my_like
                                        postDataTable.name = feeddata.name
                                        postDataTable.post_type = feeddata.post_type
                                        postDataTable.profile_picture = feeddata.profile_picture
                                        postDataTable.status = feeddata.status
                                        postDataTable.text = feeddata.text
                                        postDataTable.total_comments = feeddata.total_comments
                                        postDataTable.total_likes = feeddata.total_likes
                                        postDataTable.user_id = feeddata.user_id
                                        postDataTable.parentId = master_cat
                                        postDataTable.masterCat = main_cat
                                        postDataTable.sub_cat_id = sub_cat


                                        postDataTable.newCourseData = Gson().toJson(
                                            feeddata.newCourseData ?: emptyList<NewCourseData>()
                                        )
                                        postDataTable.livetest = Gson().toJson(feeddata.livetest)
                                        postDataTable.liveclass = Gson().toJson(feeddata.liveclass)
                                        postDataTable.testResult =
                                            Gson().toJson(
                                                feeddata.testResult ?: emptyList<TestResult>()
                                            )
                                        postDataTable.bannerlist =
                                            Gson().toJson(
                                                feeddata.bannerlist ?: emptyList<BannerData>()
                                            )
                                        postDataTable.liveClassStatus = "0"
                                        postDataTable.liveTestStatus = "0"
                                        postDataTable.page = "$page"
                                        postDataTable.iscommentenable = feeddata.is_comment_enable
                                        postDataTable.limit = "$limitdata"
                                        postDataTable.my_pinned = feeddata.my_pinned
                                        postDataTable.description = feeddata.description
                                        utkashRoom.feedDao.insertPost(postDataTable)

                                    }
                                }
                            }

                        }
                    } else {
                        if (datalist.size == 0) {
                            //   utkashRoom.feedDao.deletePost(main_cat, sub_cat)
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

        feedViewModel.mutableLiveClassData.observe(this) {
            if (it.optString(Const.STATUS) == Const.TRUE) {
                val liveClassJsonArray: JSONArray = it.getJSONArray(Const.DATA)
                if (liveClassJsonArray.length() > 0) {
                    liveClassData.clear()
                    val bannert_list = ArrayList<BannerData>()
                    for (i in 0 until liveClassJsonArray.length()) {
                        val dataObj: JSONObject = liveClassJsonArray.optJSONObject(i)
                        val feeddata = Gson().fromJson(
                            dataObj.toString(),
                            Datum::class.java
                        )
                        feeddata.cd_time = it.optLong("cd_time")
                        liveClassData.add(feeddata)
                    }

                    val optionlist = ArrayList<Option>()
                    val json = Json("", optionlist, "", "", "", "", "", "", "")
                    val data = Data(
                        "",
                        "",
                        json,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "1093",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        newCourseData,
                        liveTestData,
                        liveClassData,
                        testResultList,
                        bannert_list, "0", section_posiiton, limitdata, my_pinned = "0"
                    )
                    posiitonwiselist.add(data)

                    if (!is_filterbutton) {
                        val postDataTable = PostDataTable()
                        postDataTable.created = ""
                        postDataTable.id = ""
                        //postDataTable.masterCat = main_cat
                        postDataTable.json = Gson().toJson(json)
                        postDataTable.meta_url = ""
                        postDataTable.thumbnail = ""
                        postDataTable.url = ""
                        postDataTable.modified = ""
                        postDataTable.my_like = ""
                        postDataTable.name = ""
                        postDataTable.post_type = "1093"
                        postDataTable.profile_picture = ""
                        postDataTable.status = ""
                        postDataTable.parentId = master_cat
                        postDataTable.masterCat = main_cat
                        postDataTable.sub_cat_id = sub_cat
                        postDataTable.text = ""
                        postDataTable.total_comments = ""
                        postDataTable.total_likes = ""
                        postDataTable.user_id = ""
                        postDataTable.newCourseData = Gson().toJson(newCourseData)
                        postDataTable.livetest = Gson().toJson(liveTestData)
                        postDataTable.liveclass = Gson().toJson(liveClassData)
                        postDataTable.testResult = Gson().toJson(testResultList)
                        postDataTable.bannerlist = Gson().toJson(bannert_list)
                        postDataTable.liveClassStatus = "1"
                        postDataTable.liveTestStatus = "0"
                        postDataTable.page = "$page"
                        postDataTable.iscommentenable = "0"
                        postDataTable.sectionposiiton = section_posiiton
                        postDataTable.limit = "$limitdata"
                        postDataTable.my_pinned = pinnedPost
                        CoroutineScope(Dispatchers.IO).launch {
                            if (!utkashRoom.feedDao.isFeedExistviamaincat("1093", sub_cat))
                                utkashRoom.feedDao.insertPost(postDataTable)
                        }
                    }


                }
            }

            hitApiForLiveTest()
        }

        feedViewModel.mutableLiveTestData.observe(this) {
            if (it.optString(Const.STATUS) == Const.TRUE) {
                val liveTestJsonArray: JSONArray = it.getJSONArray(Const.DATA)
                if (liveTestJsonArray.length() > 0) {
                    liveTestData.clear()
                    var bannert_list = ArrayList<BannerData>()

                    for (i in 0 until liveTestJsonArray.length()) {
                        val dataObj: JSONObject = liveTestJsonArray.optJSONObject(i)
                        val feeddata = Gson().fromJson(
                            dataObj.toString(),
                            LiveTestData::class.java
                        )
                        feeddata.cd_time = it.optLong("cd_time")
                        liveTestData.add(feeddata)
                    }

                    val optionlist = ArrayList<Option>()
                    val json = Json("", optionlist, "", "", "", "", "", "", "")
                    val data = Data(
                        "",
                        "",
                        json,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "1092",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        newCourseData,
                        liveTestData,
                        liveClassData,
                        testResultList,
                        bannert_list, "0",
                        section_posiiton, limitdata, my_pinned = "0"
                    )
                    posiitonwiselist.add(data)


                    if (!is_filterbutton) {
                        val postDataTable = PostDataTable()
                        postDataTable.created = ""
                        postDataTable.id = ""
                        postDataTable.json = Gson().toJson(json)
                        postDataTable.meta_url = ""
                        postDataTable.thumbnail = ""
                        postDataTable.url = ""
                        postDataTable.modified = ""
                        postDataTable.my_like = ""
                        postDataTable.name = ""
                        postDataTable.post_type = "1092"
                        postDataTable.profile_picture = ""
                        postDataTable.status = ""
                        postDataTable.parentId = master_cat
                        postDataTable.masterCat = main_cat
                        postDataTable.sub_cat_id = sub_cat
                        postDataTable.text = ""
                        postDataTable.total_comments = ""
                        postDataTable.total_likes = ""
                        postDataTable.user_id = ""
                        postDataTable.newCourseData = Gson().toJson(newCourseData)
                        postDataTable.livetest = Gson().toJson(liveTestData)
                        postDataTable.liveclass = Gson().toJson(liveClassData)
                        postDataTable.testResult = Gson().toJson(testResultList)
                        postDataTable.bannerlist = Gson().toJson(bannert_list)
                        postDataTable.liveClassStatus = "0"
                        postDataTable.liveTestStatus = "1"
                        postDataTable.page = "$page"
                        postDataTable.iscommentenable = "0"
                        postDataTable.sectionposiiton = section_posiiton
                        postDataTable.limit = "$limitdata"
                        postDataTable.my_pinned = pinnedPost

                        CoroutineScope(Dispatchers.IO).launch {
                            if (!utkashRoom.feedDao.isFeedExistviamaincat("1092", sub_cat))
                                utkashRoom.feedDao.insertPost(postDataTable)
                        }
                    }


                }
            }
            with(feedAdapter!!) {
                if (datalist.size > section_posiiton.toInt()) {
                    datalist.addAll(section_posiiton.toInt() + 1, posiitonwiselist)
                } else {
                    datalist.addAll(posiitonwiselist);
                }

                addFeed(datalist)
                notifyDataSetChanged()
            }
        }
    }

    private fun updatePostData(postData: MutableList<PostDataTable>) {
        if (postData.isNotEmpty()) {
            page = postData.get(postData.size - 1).page.toInt()
            limitdata = postData.get(postData.size - 1).limit.toInt()

            if (liveTestData.isEmpty()) {
                for (postDataTable in postData) {
                    if (postDataTable.post_type.equals("1092")) {
                        liveTestData = Gson().fromJson(
                            postDataTable.livetest,
                            object :
                                TypeToken<List<LiveTestData>?>() {}.type
                        )
                        break
                    }
                }
            }
            if (liveClassData.isEmpty()) {
                for (postDataTable in postData) {
                    if (postDataTable.post_type.equals("1093")) {
                        liveClassData = Gson().fromJson(
                            postDataTable.liveclass,
                            object :
                                TypeToken<List<LiveTestData>?>() {}.type
                        )
                        break
                    }
                }
            }

        }
    }


    private fun catregoryPost(postData: MutableList<PostDataTable>) {
        if (postData.isNotEmpty()) {
            section_posiiton = postData.get(0).sectionposiiton
            if (section_posiiton.equals("")) {
                section_posiiton = "0"
            }
            page = postData.get(postData.size - 1).page.toInt()
            limitdata = postData.get(postData.size - 1).limit.toInt()

            for (postDataTable in postData) {
                val data = Data(
                    postDataTable.created,
                    postDataTable.id,
                    Gson().fromJson(
                        postDataTable.json,
                        object : TypeToken<Json?>() {}.type
                    ),
                    postDataTable.meta_url,
                    postDataTable.thumbnail,
                    postDataTable.url,
                    postDataTable.modified,
                    postDataTable.my_like,
                    postDataTable.name,
                    postDataTable.post_type,
                    postDataTable.profile_picture,
                    postDataTable.status,
                    postDataTable.sub_cat_id,
                    postDataTable.text,
                    postDataTable.total_comments,
                    postDataTable.total_likes,
                    postDataTable.user_id,
                    Gson().fromJson(
                        postDataTable.newCourseData,
                        object : TypeToken<List<NewCourseData>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.livetest,
                        object : TypeToken<List<LiveTestData>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.liveclass,
                        object : TypeToken<List<Datum>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.testResult,
                        object : TypeToken<List<TestResult>?>() {}.type
                    ),
                    Gson().fromJson(
                        postDataTable.bannerlist,
                        object : TypeToken<List<BannerData>?>() {}.type
                    ),
                    postDataTable.iscommentenable,
                    my_pinned = postDataTable.my_pinned ?: "0",
                    description = postDataTable.description ?: "",

                    master_cat_id = postDataTable.parentId,
                    main_cat = postDataTable.masterCat,
                    sub_cat = postDataTable.sub_cat_id
                )
                datalist.add(data)
                if (postDataTable.liveClassStatus == "1") {
                    liveClassData = Gson().fromJson(
                        postDataTable.liveclass,
                        object : TypeToken<List<Datum>?>() {}.type
                    )
                }


                if (postDataTable.liveTestStatus == "1") {
                    liveTestData = Gson().fromJson(
                        postDataTable.livetest,
                        object : TypeToken<List<LiveTestData>?>() {}.type
                    )
                }
            }
            if (datalist.size > 0) {
                feedViewModel.progressvalue.value = "0"
                with(feedAdapter!!) {
                    posiitonwiselist.clear()
                    pinnedPostList.clear()
                    feedlist.clear()

                    for (data in datalist) {
                        /*if (data.my_pinned == "1") {
                            pinnedPostList.add(data)
                        } else*/ if (data.post_type.equals("1090") || data.post_type.equals(
                                "1091"
                            ) || data.post_type.equals(
                                "1092"
                            ) || data.post_type.equals("1093")
                        ) {
                            posiitonwiselist.add(data)
                        } else {
                            feedlist.add(data)
                        }
                    }
                    datalist.clear()
                    datalist.addAll(feedlist)
                    feedlist.clear()
                    if (datalist.size > section_posiiton.toInt()) {
                        datalist.addAll(
                            section_posiiton.toInt() + 1,
                            posiitonwiselist
                        )
                        //  feedatalist = datalist

                    } else {
                        datalist.addAll(posiitonwiselist);
                    }
                    posiitonwiselist.clear()
                    pinnedPostList.clear()

                    addFeed(datalist)
                    notifyDataSetChanged()
                }
            } else {
                createApiBodyData()
            }
        } else {
            createApiBodyData()
        }
    }

    private fun hitApiForLiveClass() {
        val getcoursedataencryptionData = EncryptionData()
        getcoursedataencryptionData.page = "" + 1
        getcoursedataencryptionData.type = "1"
        val getcoursedataencryptionDatadoseStr = Gson().toJson(getcoursedataencryptionData)
        val getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr)
        feedViewModel.getLiveClassData(getcoursedatadoseStrScr)
    }

    private fun hitApiForLiveTest() {
        val getcoursedataencryptionData = EncryptionData()
        getcoursedataencryptionData.page = "" + 1
        getcoursedataencryptionData.type = "1"
        val getcoursedataencryptionDatadoseStr = Gson().toJson(getcoursedataencryptionData)
        val getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr)
        feedViewModel.getLiveTestData(getcoursedatadoseStrScr)
    }

    fun showProgressView() {
        feedsBinding!!.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressView() {
        if (feedsBinding!!.progressBar.visibility == View.VISIBLE)
            feedsBinding!!.progressBar.visibility = View.INVISIBLE
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (type_posttypefilter) {
            if (!item!!.title.equals(posttypename)) {
                for (i in posttypelist.indices) {
                    if (item.title.equals(posttypelist[i].title)) {
                        posttypename = posttypelist[i].title
                        posttypeid = posttypelist[i].id
                        posttypeytext!!.text = posttypename
                        type_posttypefilter = false
                        break
                    }
                }
            }
        } else if (type_subcatfilter) {
            if (!item!!.title.equals(sub_cat_name_filter)) {
                if (item.title.equals("All")) {
                    sub_cat_name_filter = "All"
                    sub_cat_filter = "0"
                    subcatspinner!!.text = sub_cat_name_filter
                    type_subcatfilter = false
                } else
                    for (i in selectedsub_all_cat.indices) {
                        if (item.title.equals(selectedsub_all_cat[i].name)) {
                            sub_cat_name_filter = selectedsub_all_cat[i].name
                            sub_cat_filter = selectedsub_all_cat[i].id
                            subcatspinner!!.text = sub_cat_name_filter
                            type_subcatfilter = false
                            break
                        }
                    }
            }
        } else
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
                        val subcat_prefence = ArrayList<MasteAllCatTable>()
                        for (masteAllCatTable in masterAllCatTables) {
                            for (preferences in preferencesArrayList) {
                                if (masteAllCatTable.id.equals(
                                        preferences.sub_cat,
                                        ignoreCase = true
                                    )
                                ) {
                                    subcat_prefence.add(masteAllCatTable)
                                }
                            }
                        }
                        for (masteAllCatTable1 in subcat_prefence) {
                            if (main_cat.equals(masteAllCatTable1.parent_id, ignoreCase = true)) {
                                selectedsub_all_cat.add(masteAllCatTable1)
                            }
                        }

                        if (selectedsub_all_cat.size > 0) {
                            sub_cat_name = selectedsub_all_cat[0].name
                            sub_cat = selectedsub_all_cat[0].id
                        }

                        posttypeid = "0"
                        posttypename = "All"
                        sub_cat_filter = ""
                        sub_cat_name_filter = ""
                        datalist.clear()
                        feedsBinding!!.toolbartitleTV.text = item.title
                        page = 1
                        section_posiiton = "0"
                        limitdata = 0
                        posiitonwiselist.clear()
                        pinnedPostList.clear()

                        if (feedAdapter!!.timer != null) {
                            feedAdapter!!.timer!!.cancel()
                            feedAdapter!!.timer!!.purge()
                            Log.d("abcdee", "cancel")
                        }
                        feedViewModel.progressvalue.value = "1"
                        if (posttypeid.equals("0")) {
                            val postData =
                                utkashRoom.feedDao.retrievePostData(master_cat, main_cat, sub_cat)
                            catregoryPost(postData)
                        } else {
                            val postData = utkashRoom.feedDao.retrievePostData_viaposttype(
                                master_cat,
                                main_cat,
                                sub_cat,
                                posttypeid
                            )
                            catregoryPost(postData)
                        }




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


    override fun onPause() {
        super.onPause()

        /*  try {
              val firstCompletelyVisibleItemPosition =
                  manager!!.findFirstCompletelyVisibleItemPosition()
              val videoModel = datalist.get(firstCompletelyVisibleItemPosition)
              if (videoModel.post_type == "3") {
                  val video_view = manager!!.findViewByPosition(firstCompletelyVisibleItemPosition)!!
                      .findViewById(R.id.video_view) as SimpleVideoView
                  video_view.release()
              }

          } catch (e: java.lang.NullPointerException) {
              // Sometimes you scroll so fast that the views are not attached so it gives a NullPointerException
          } catch (e: ArrayIndexOutOfBoundsException) {
          }*/
    }

    override fun onRestart() {
        super.onRestart()
        if (REFRESHFEED.equals("1")) {
            REFRESHFEED = ""
            if (feedAdapter!!.timer != null) {
                feedAdapter!!.timer!!.cancel()
                feedAdapter!!.timer!!.purge()
            }
            CoroutineScope(Dispatchers.Main).launch {
                val jobrerutn = CoroutineScope(Dispatchers.IO).launch {

                    if (posttypeid.equals("0")) {
                        feedParentData =
                            utkashRoom.feedDao.retrievePostData(master_cat, main_cat, sub_cat)
                    } else {
                        feedParentData = utkashRoom.feedDao.retrievePostData_viaposttype(
                            master_cat,
                            main_cat,
                            sub_cat,
                            posttypeid
                        )
                    }
                }
                jobrerutn.join()
                if (feedParentData.isNotEmpty()) {
                    section_posiiton = feedParentData.get(0).sectionposiiton
                    if (section_posiiton.equals("")) {
                        section_posiiton = "0"
                    }
                    page = feedParentData.get(feedParentData.size - 1).page.toInt()
                    limitdata = feedParentData.get(feedParentData.size - 1).limit.toInt()

                    datalist.clear()

                    for (postDataTable in feedParentData) {
                        val data = Data(
                            postDataTable.created,
                            postDataTable.id,
                            Gson().fromJson(
                                postDataTable.json,
                                object : TypeToken<Json?>() {}.type
                            ),
                            postDataTable.meta_url,
                            postDataTable.thumbnail,
                            postDataTable.url,
                            postDataTable.modified,
                            postDataTable.my_like,
                            postDataTable.name,
                            postDataTable.post_type,
                            postDataTable.profile_picture,
                            postDataTable.status,
                            postDataTable.sub_cat_id,
                            postDataTable.text,
                            postDataTable.total_comments,
                            postDataTable.total_likes,
                            postDataTable.user_id,
                            Gson().fromJson(
                                postDataTable.newCourseData,
                                object : TypeToken<List<NewCourseData>?>() {}.type
                            ),
                            Gson().fromJson(
                                postDataTable.livetest,
                                object : TypeToken<List<LiveTestData>?>() {}.type
                            ),
                            Gson().fromJson(
                                postDataTable.liveclass,
                                object : TypeToken<List<Datum>?>() {}.type
                            ),
                            Gson().fromJson(
                                postDataTable.testResult,
                                object : TypeToken<List<TestResult>?>() {}.type
                            ),
                            Gson().fromJson(
                                postDataTable.bannerlist,
                                object : TypeToken<List<BannerData>?>() {}.type
                            ),
                            postDataTable.iscommentenable,
                            my_pinned = postDataTable.my_pinned ?: "0",
                            description = postDataTable.description ?: "",
                            master_cat_id = postDataTable.parentId,
                            main_cat = postDataTable.masterCat,
                            sub_cat = postDataTable.sub_cat_id
                        )
                        datalist.add(data)

                        if (postDataTable.liveClassStatus == "1") {
                            liveClassData = Gson().fromJson(
                                postDataTable.liveclass,
                                object : TypeToken<List<Datum>?>() {}.type
                            )
                        }


                        if (postDataTable.liveTestStatus == "1") {
                            liveTestData = Gson().fromJson(
                                postDataTable.livetest,
                                object : TypeToken<List<LiveTestData>?>() {}.type
                            )
                        }
                    }

                }

                if (datalist.size > 0) {
                    feedViewModel.progressvalue.value = "0"
                    with(feedAdapter!!) {
                        posiitonwiselist.clear()
                        pinnedPostList.clear()
                        feedlist.clear()
                        for (data in datalist) {
                            if (data.post_type.equals("1090") || data.post_type.equals("1091") || data.post_type.equals(
                                    "1092"
                                ) || data.post_type.equals("1093")
                            ) {
                                posiitonwiselist.add(data)
                            } else {
                                feedlist.add(data)
                            }
                        }
                        datalist.clear()
                        datalist.addAll(feedlist)
                        feedlist.clear()

                        if (datalist.size > section_posiiton.toInt()) {
                            datalist.addAll(section_posiiton.toInt() + 1, posiitonwiselist)
                            //        feedatalist = datalist

                        } else {
                            datalist.addAll(posiitonwiselist);
                        }
                        posiitonwiselist.clear()
                        pinnedPostList.clear()
                        addFeed(datalist)
                        notifyDataSetChanged()
                    }
                } else {
                    createApiBodyData()
                }
            }


        }
    }


    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun popupWindowPart(): PopupWindow {
        val popupWindow = PopupWindow(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.dialog_popup_feed, null)
        val main_recyclerview = view.findViewById<ListView>(R.id.main_recyclerview)
        val layput = view.findViewById<RelativeLayout>(R.id.layput)
        val change_prefence = view.findViewById<TextView>(R.id.change_prefence)
        val partAdapter = MainCatAdapter(this, selected_master_cat)
        if (selected_master_cat.size > 0 && selected_master_cat.size == 1) {
            val scale: Float = resources.displayMetrics.density
            val pixels = (50 * scale + 0.5f).toInt()
            val layoutParams =
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels)
            layput!!.layoutParams = layoutParams
        } else if (selected_master_cat.size > 0 && selected_master_cat.size == 2) {
            val scale: Float = resources.displayMetrics.density
            val pixels = (100 * scale + 0.5f).toInt()
            val layoutParams =
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels)
            layput!!.layoutParams = layoutParams
        } else if (selected_master_cat.size > 0 && selected_master_cat.size >= 3) {
            val scale: Float = resources.displayMetrics.density
            val pixels = (150 * scale + 0.5f).toInt()
            val layoutParams =
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels)
            layput!!.layoutParams = layoutParams
        }
        change_prefence.setOnClickListener {
            popUp!!.dismiss()
            val intent = Intent(this, IntroActivity::class.java)
            Helper.gotoActivity(intent, this)
        }
        main_recyclerview.adapter = partAdapter
        main_recyclerview.setOnItemClickListener { adapterView: AdapterView<*>?, view1: View?, i: Int, l: Long ->
            if (posttypelist.size > 0) {
                val item = selected_master_cat[i]
                if (!item.name.equals(feedsBinding!!.toolbartitleTV.text.toString())) {
                    for (selected_master_cat in selected_master_cat) {
                        if (selected_master_cat.name.equals(item.name)) {
                            if (no_data_found_RL!!.visibility == View.VISIBLE) {
                                no_data_found_RL!!.visibility = View.GONE
                                feedsBinding!!.pulltoReferesh.visibility = View.VISIBLE
                            }
                            loading = true
                            main_cat = selected_master_cat.id
                            selectedsub_all_cat.clear()
                            val subcat_prefence = ArrayList<MasteAllCatTable>()
                            for (masteAllCatTable in masterAllCatTables) {
                                for (preferences in preferencesArrayList) {
                                    if (masteAllCatTable.id.equals(
                                            preferences.sub_cat,
                                            ignoreCase = true
                                        )
                                    ) {
                                        subcat_prefence.add(masteAllCatTable)
                                    }
                                }
                            }
                            for (masteAllCatTable1 in subcat_prefence) {
                                if (main_cat.equals(
                                        masteAllCatTable1.parent_id,
                                        ignoreCase = true
                                    )
                                ) {
                                    selectedsub_all_cat.add(masteAllCatTable1)
                                }
                            }

                            if (selectedsub_all_cat.size > 0) {
                                /* sub_cat_name = selectedsub_all_cat[0].name
                                 sub_cat = selectedsub_all_cat[0].id*/
                                sub_cat_name = "All"
                                sub_cat = "0"
                            }

                            posttypeid = "0"
                            posttypename = "All"
                            sub_cat_filter = ""
                            is_filterbutton = false
                            sub_cat_name_filter = ""
                            datalist.clear()
                            feedsBinding!!.toolbartitleTV.text = item.name
                            page = 1
                            section_posiiton = "0"
                            limitdata = 0
                            posiitonwiselist.clear()
                            pinnedPostList.clear()
                            feedsBinding!!.filter!!.setImageResource(R.drawable.filter_icon)

                            if (feedAdapter!!.timer != null) {
                                feedAdapter!!.timer!!.cancel()
                                feedAdapter!!.timer!!.purge()
                                Log.d("abcdee", "cancel")
                            }
                            feedViewModel.progressvalue.value = "1"
                            if (posttypeid.equals("0")) {
                                val postData =
                                    utkashRoom.feedDao.retrievePostData(
                                        master_cat,
                                        main_cat,
                                        sub_cat
                                    )
                                catregoryPost(postData)
                            } else {
                                val postData = utkashRoom.feedDao.retrievePostData_viaposttype(
                                    master_cat,
                                    main_cat,
                                    sub_cat,
                                    posttypeid
                                )
                                catregoryPost(postData)
                            }




                            break
                        }
                    }
                }

                popupWindow.dismiss()
            }
        }
        try {
            val wm = view.context.getSystemService(WINDOW_SERVICE) as WindowManager
            popupWindow.isFocusable = true
            popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.background_rectangle))
            popupWindow.width = wm.defaultDisplay.width * 4 / 6
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
            popupWindow.contentView = view
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }


        return popupWindow
    }


    override fun onStop() {
        try {
            hit_api_for_feed_dot()
        }catch (ex:Exception){}
        super.onStop()
    }

    private fun hit_api_for_feed_dot() {
        val networkCall = NetworkCall(this, this)
        networkCall.NetworkAPICall(API.post_feed_type, "", false, false)
    }

    override fun getAPIB(apitype: String?, typeApi: String?, service: APIInterface?): Call<String>? {
        when (apitype) {

            API.post_feed_type -> {
                val data = EncryptionData()
                data.type = "1"
                val datajson = Gson().toJson(data)
                val encryptjson = AES.encrypt(datajson)
                return service!!.postFeedDot(encryptjson)
            }

        }
        return null
    }

    override fun SuccessCallBack(
        jsonstring: JSONObject?,
        apitype: String?,
        typeApi: String?,
        showprogress: Boolean
    ) {
        Log.d("dhdhhdhd",jsonstring.toString())
    }

    override fun ErrorCallBack(jsonstring: String?, apitype: String?, typeApi: String?) {
    }
}