package com.utkarshnew.android.Intro.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.utkarshnew.android.EncryptionModel.EncryptionData
import com.utkarshnew.android.Intro.Fragment.ChildCategoryFragment
import com.utkarshnew.android.Intro.Fragment.LanguageFragment
import com.utkarshnew.android.Intro.Fragment.MainCategoryFragment
import com.utkarshnew.android.Intro.Mastercat
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.Model.Courses.Cards
import com.utkarshnew.android.R
import com.utkarshnew.android.Room.UtkashRoom
import com.utkarshnew.android.Utils.*
import com.utkarshnew.android.Utils.Network.API
import com.utkarshnew.android.Utils.Network.APIInterface
import com.utkarshnew.android.Utils.Network.NetworkCall
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse
import com.utkarshnew.android.home.Activity.HomeActivity
import com.utkarshnew.android.pojo.Userinfo.Data
import com.utkarshnew.android.table.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call


class IntroActivity : AppCompatActivity(), NetworkCall.MyNetworkCallBack {
    var progressBar: ProgressBar? = null
    var container: FrameLayout? = null
    var backPressed: Long = 0
    var maincatlist: ArrayList<SubCat> = ArrayList()
    val prefencelist = ArrayList<Data.Preferences>()
    var fragmentManager: FragmentManager? = null
    var networkCall: NetworkCall? = null
    var fragment: Fragment? = null
    var next_layout: LinearLayout? = null
    var next_back: LinearLayout? = null
    var step: TextView? = null
    var bundle: Bundle? = null
    var prefence = ""
    var back_text: TextView? = null
    var next_text: TextView? = null
    var utkashRoom: UtkashRoom? = null

    var courseTypeMasterTables: ArrayList<CourseTypeMasterTable> = ArrayList()
    var masterAllCatTables: ArrayList<MasteAllCatTable> = ArrayList()
    var langlist: ArrayList<LanguagesTable> = ArrayList()
    var mastercatlist: ArrayList<Mastercat> = ArrayList()
    var cardsArrayList = ArrayList<Cards>()
    var masterjsonlist: String? = ""

    var data: Data? = null
    var mastercat: Mastercat? = null
    var subCat: SubCat? = null
    var allsubCat: SubCat? = null
    var master_main_sub: SubCat? = null
    var is_lang: String = ""

    private var selected_master_cat = ArrayList<MasteAllCatTable>()
    private var selectedsub_all_cat = ArrayList<MasteAllCatTable>()
    var subids = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        Helper.enableScreenShot(this)

        utkashRoom = UtkashRoom.getAppDatabase(this)
        progressBar = findViewById(R.id.progressBar)
        container = findViewById(R.id.container)

        next_layout = findViewById(R.id.next_layout)
        next_back = findViewById(R.id.next_back)
        networkCall = NetworkCall(this, this)

        next_text = findViewById(R.id.next_text)
        back_text = findViewById(R.id.back_text)

        prefence = SharedPreference.getInstance().getString("prefence")


        step = findViewById(R.id.step)
        bundle = intent.extras

        hit_api_master_data()

        next_layout!!.setOnClickListener { v: View? ->
            fragmentManager = this.supportFragmentManager
            fragment = fragmentManager!!.findFragmentById(R.id.container)
            if (fragment is MainCategoryFragment) {
                if (maincatlist.size > 0) {
                    prefencelist.clear()
                    subids = ""
                    for (i in 0 until maincatlist.size) {
                        val data = Data.Preferences()
                        subids += maincatlist[i].id + ","
                        data.main_cat = maincatlist[i].parenid
                        data.sub_cat = maincatlist[i].id
                        prefencelist.add(data)
                    }
                    if (subids.endsWith(",")) {
                        subids = subids.substring(0, subids.length - 1);
                    }
                    Log.d("happy", "subids: $subids")
                    Log.d("happy", "subids: ${Gson().toJson(prefencelist)}")

                    networkCall!!.NetworkAPICall(API.update_preference, "", true, false)


                 /*   next_layout!!.visibility = View.GONE
                    next_back!!.visibility = View.VISIBLE*/
                 /*   step!!.text = "Step 2/2"
                    progressBar!!.progress = 2*/
                   // loadFragment(LanguageFragment(), true)

                } else {
                    Toast.makeText(this@IntroActivity, "Please select category", Toast.LENGTH_SHORT).show()

                }


            }
        }

        next_text!!.setOnClickListener { v: View? ->
            fragmentManager = this.supportFragmentManager
            fragment = fragmentManager!!.findFragmentById(R.id.container)

            if (fragment is MainCategoryFragment) {
                if ((fragment as MainCategoryFragment).is_select_main_cat!!) {
                    step!!.text = "Step 2/4"
                    progressBar!!.progress = 2
                    mastercat = (fragment as MainCategoryFragment).mastercat!!
                    loadFragment(ChildCategoryFragment(), true)
                } else {
                    Toast.makeText(
                        this@IntroActivity,
                        "Please Select to Proceed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else if (fragment is LanguageFragment) {
                if ((fragment as LanguageFragment).is_lang_select!!) {
                    networkCall!!.NetworkAPICall(API.update_preference, "", true, false)
                } else {

                    Toast.makeText(
                        this@IntroActivity,
                        "Please Select to Proceed",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }

        back_text!!.setOnClickListener { v: View? ->
            onCustomBackPress()
        }

    }

    fun loadFragment(fragment: Fragment, isaddbackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
        if (isaddbackstack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
    }

    override fun onBackPressed() {
        onCustomBackPress()
    }

    fun hit_api_master_data() {
        networkCall!!.NetworkAPICall(API.master_content, "", true, false)
    }

    fun prefencemanage() {
        data = SharedPreference.getInstance().loggedInUser

        if (data?.preferences != null && data?.preferences!!.size > 0) {
            CoroutineScope(Dispatchers.IO).launch {
                val masterAllCatTables =
                    utkashRoom?.masterAllCatDao?.getmaster_allcat(MakeMyExam.userId)
                val name_sublist = ArrayList<MasteAllCatTable>()

                for (i in 0 until masterAllCatTables!!.size) {
                    for (j in 0 until data?.preferences!!.size) {
                        if (masterAllCatTables[i].id == data?.preferences!![j].sub_cat) {
                            name_sublist.add(masterAllCatTables[i])

                            master_main_sub = SubCat(
                                masterAllCatTables[i].id,
                                masterAllCatTables[i].name,
                                masterAllCatTables[i].parent_id,
                                masterAllCatTables[i].master_type,
                                false
                            )

                            master_main_sub?.let {
                                maincatlist.add(it)
                            }
                        }
                    }
                }
                for (i in 0 until mastercatlist.size) {
                    for (j in 0 until name_sublist.size) {
                        if (mastercatlist[i].catid == name_sublist[j].master_type) {
                         //   mastercatlist[i].is_expand_maincat = true
                        }
                    }
                }
                Log.d("happy", "sublistname: ${Gson().toJson(name_sublist)}")

                withContext(Dispatchers.Main) {
                    loadFragment(MainCategoryFragment(), true)
                }
            }
            //


        } else
            if (prefence.contains("#@")) {
                val arr: List<String> = prefence.split("#@")

                for (i in 0 until mastercatlist.size) {
                    if (arr[1] == mastercatlist[i].catid) {
                        mastercat = Mastercat(arr[1], arr[0], true)
                        mastercatlist[i].is_select = true
                      //  mastercatlist[i].is_expand_maincat = true
                        break
                    }
                }

                val selected_master_cat = ArrayList<SubCat>()
                for (masteAllCatTable in masterAllCatTables) {
                    if (masteAllCatTable.master_type == mastercat!!.catid && masteAllCatTable.parent_id.equals(
                            "0",
                            ignoreCase = true
                        )
                    ) {
                        val subCat = SubCat(
                            masteAllCatTable.id,
                            masteAllCatTable.name,
                            masteAllCatTable.parent_id,
                            masteAllCatTable.master_type
                        )
                        selected_master_cat.add(subCat)
                    }
                }

                for (i in 0 until selected_master_cat.size) {
                    if (arr[3] == selected_master_cat[i].id) {
                        subCat = mastercat?.let { SubCat(arr[3], arr[2], "", it.catid, true) }
                        break
                    }
                }
                val selectedsub_all_cat = ArrayList<SubCat>()
                for (masteAllCatTable1 in masterAllCatTables) {
                    if (subCat!!.id.equals(masteAllCatTable1.parent_id, ignoreCase = true)) {
                        val subCat = SubCat(
                            masteAllCatTable1.id,
                            masteAllCatTable1.name,
                            masteAllCatTable1.parent_id,
                            masteAllCatTable1.master_type
                        )
                        selectedsub_all_cat.add(subCat)
                    }
                }
                for (i in 0 until selectedsub_all_cat.size) {
                    if (arr[5] == selectedsub_all_cat[i].id) {
                        master_main_sub = subCat?.let {
                            SubCat(arr[5], arr[4], it.id, it.mastertype, false)
                        }

                        selectedsub_all_cat[i].is_selct = true
                        break
                    }
                }
                master_main_sub?.let {
                    maincatlist.add(it)
                }
                loadFragment(MainCategoryFragment(), true)

            }else{
                loadFragment(MainCategoryFragment(), true)

            }


    }

    @SuppressLint("SetTextI18n")
    private fun onCustomBackPress() {
        fragmentManager = this.supportFragmentManager
        fragment = fragmentManager!!.findFragmentById(R.id.container)
        if (fragment is MainCategoryFragment) {
            finish()
        } else if (fragment is LanguageFragment) {

            next_layout!!.visibility = View.VISIBLE
            next_back!!.visibility = View.GONE
            step!!.text = "Step 1/2"
            progressBar!!.progress = 1
            fragmentManager!!.popBackStack(
                fragment!!.javaClass.simpleName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

        } else
            super.onBackPressed()
    }


    override fun getAPIB(
        apitype: String?,
        typeApi: String?,
        service: APIInterface
    ): Call<String?>? {
        when (apitype) {
            API.master_content -> {
                val masterdataencryptionData = EncryptionData()
                masterdataencryptionData.user_id = MakeMyExam.getUserId()
                val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
                val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
                return service.master_content(masterdatadoseStrScr)
            }

            API.update_preference -> {
                var langname = "";
                if (is_lang.equals("1")) {
                    langname = "English"
                } else if (is_lang.equals("2")) {
                    langname = "Hindi"
                } else if (is_lang.equals("1,2")) {
                    is_lang = "0"
                    langname = "multi"
                }

                val masterdataencryptionData = EncryptionData()
                masterdataencryptionData.sub_cat_ids = subids
                masterdataencryptionData.lang = "0"
                val masterdataencryptionDatadoseStr = Gson().toJson(masterdataencryptionData)
                val masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr)
                return service.update_preference(masterdatadoseStrScr)
            }
        }
        return null
    }


    override fun SuccessCallBack(
        jsonObject: JSONObject?,
        apitype: String?,
        typeApi: String?,
        showprogress: Boolean
    ) {
        when (apitype) {
            API.update_preference -> {
                try {
                    if (jsonObject!!.optString(Const.STATUS) == Const.TRUE) {

                        val intent = Intent(this@IntroActivity, HomeActivity::class.java)
                        val newbundle = Bundle()

                        data = SharedPreference.getInstance().loggedInUser
                        if (data!!.preferences != null && data!!.preferences.size >= 0) {
                            data?.preferences = prefencelist
                            data?.lang="0"
                            SharedPreference.getInstance().setLoggedInUserr(data)
                        }
                        if (bundle != null) {
                            newbundle.putAll(bundle!!)
                        } else {
                            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        intent.putExtras(newbundle)
                        Helper.gotoActivity_finish(intent, this)
                    } else {
                        RetrofitResponse.GetApiData(
                            this,
                            if (jsonObject.has(Const.AUTH_CODE)) jsonObject.getString(Const.AUTH_CODE) else "",
                            jsonObject.getString(Const.MESSAGE),
                            false
                        )

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            API.master_content -> {
                try {
                    if (jsonObject!!.optString(Const.STATUS) == Const.TRUE) {
                        val dataJsonObject: JSONObject = jsonObject.getJSONObject(Const.DATA)
                        try {
                            utkashRoom!!.getcoursetypemaster().deletedata()
                            utkashRoom!!.launguages.deletedata()
                            utkashRoom!!.masterAllCatDao.deletedata()
                            utkashRoom!!.mastercatDao.deletedata()

                            courseTypeMasterTables.clear()
                            masterAllCatTables.clear()
                            mastercatlist.clear()
                            selectedsub_all_cat.clear()
                            selected_master_cat.clear()



                            if (dataJsonObject.has("languages")) {
                                val launguage = dataJsonObject.optJSONArray("languages")
                                if (launguage != null && launguage.length() > 0) {
                                    for (i in 0 until launguage.length()) {
                                        val languagesTable = Gson().fromJson(
                                            launguage[i].toString(),
                                            LanguagesTable::class.java
                                        )

                                        langlist.add(languagesTable)

                                        utkashRoom!!.getLaunguages().addLaunguage(languagesTable)
                                    }
                                }
                            }
                            if (dataJsonObject.has("all_cat")) {
                                val jsonArray = dataJsonObject.optJSONArray("all_cat")
                                if (jsonArray != null && jsonArray.length() > 0) {
                                    for (i in 0 until jsonArray.length()) {
                                        val getMasterData_allcat = Gson().fromJson(
                                            jsonArray[i].toString(),
                                            MasteAllCatTable::class.java
                                        )
                                        getMasterData_allcat.user_id = MakeMyExam.userId

                                        utkashRoom!!.masterAllCatDao.addUser(getMasterData_allcat)

                                        masterAllCatTables.add(getMasterData_allcat)

                                    }
                                }
                            }


                            if (dataJsonObject.has("master_cat")) {
                                val jsonArray = dataJsonObject.optJSONArray("master_cat")
                                if (jsonArray != null && jsonArray.length() > 0) {
                                    for (i in 0 until jsonArray.length()) {
                                        val masterCat = Gson().fromJson(
                                            jsonArray[i].toString(),
                                            MasterCat::class.java
                                        )
                                        masterCat.user_id = MakeMyExam.userId
                                        utkashRoom!!.mastercatDao.addUser(masterCat)
                                        val mastercat = Mastercat(masterCat.id, masterCat.cat)
                                        mastercatlist.add(mastercat)

                                    }

                                }
                            }




                            if (dataJsonObject.has("course_type_master")) {
                                val jsonArray = dataJsonObject.optJSONArray("course_type_master")
                                if (jsonArray != null && jsonArray.length() > 0) {
                                    for (i in 0 until jsonArray.length()) {
                                        val courseTypeMasterTable = Gson().fromJson(
                                            jsonArray[i].toString(),
                                            CourseTypeMasterTable::class.java
                                        )
                                        courseTypeMasterTable.user_id = MakeMyExam.userId
                                        utkashRoom!!.getcoursetypemaster().addUser(courseTypeMasterTable)
                                        courseTypeMasterTables.add(courseTypeMasterTable)

                                    }
                                }
                            }

                            for (courseTypeMasterTables in courseTypeMasterTables) {
                                cardsArrayList.add(
                                    Cards(
                                        courseTypeMasterTables.id,
                                        courseTypeMasterTables.name,
                                        "#000000",
                                        courseTypeMasterTables.name,
                                        "0#0#0#0#0#0",
                                        "0"
                                    )
                                )
                            }


                            prefencemanage()


                            /* if (cardsArrayList.size > 0) {
                                 getTileItems(cardsArrayList, 0)
                             }*/
                            try {
                                if (utkashRoom!!.getapidao()
                                        .is_api_code_exits(MakeMyExam.userId, "ut_009")
                                ) {

                                    utkashRoom!!.getapidao().update_api_version(
                                        "ut_009",
                                        MakeMyExam.userId,
                                        jsonObject.optLong("time").toString(),
                                        jsonObject.optLong("interval").toString(),
                                        jsonObject.optLong("cd_time").toString()
                                    )
                                } else {
                                    val apitable = APITABLE()
                                    apitable.apicode = "ut_009"
                                    apitable.apiname = "master_content"
                                    apitable.interval = jsonObject.optLong("interval").toString()
                                    apitable.user_id = MakeMyExam.userId
                                    apitable.timestamp = jsonObject.optLong("time").toString()
                                    apitable.cdtimestamp = jsonObject.optLong("cd_time").toString()
                                    apitable.version = "0.000"
                                    utkashRoom!!.getapidao().addUser(apitable)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        RetrofitResponse.GetApiData(
                            this,
                            if (jsonObject.has(Const.AUTH_CODE)) jsonObject.getString(Const.AUTH_CODE) else "",
                            jsonObject.getString(Const.MESSAGE),
                            false
                        )
                    }
                } catch (ex: Exception) {
                    ErrorCallBack(ex.message + " : " + ex.localizedMessage, apitype, typeApi)
                    ex.printStackTrace()
                }
            }

        }
    }

    override fun ErrorCallBack(jsonstring: String?, apitype: String?, typeApi: String?) {

    }


}