package com.utkarshnew.android.feeds.viewmodel

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.appsquadz.mvvmwithretrofit.repository.Repository
import com.google.gson.Gson
import com.utkarshnew.android.BuildConfig
import com.utkarshnew.android.EncryptionModel.EncryptionData
import com.utkarshnew.android.Room.UtkashRoom
import com.utkarshnew.android.table.APITABLE
import com.utkarshnew.android.table.UserWiseCourseTable
import com.utkarshnew.android.Utils.AES
import com.utkarshnew.android.Utils.Const
import com.utkarshnew.android.Utils.MakeMyExam
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse
import com.utkarshnew.android.Utils.SharedPreference
import com.utkarshnew.android.feeds.adapters.FeedAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(var repository: Repository, var utkashRoom: UtkashRoom) :
    ViewModel() {
    var jsonObjectmutable: MutableLiveData<JSONObject> = MutableLiveData()

    var mutableLiveTestData: MutableLiveData<JSONObject> = MutableLiveData()

    var mutableLiveClassData: MutableLiveData<JSONObject> = MutableLiveData()

    var adapter_response: MutableLiveData<JSONObject> = MutableLiveData()

    var bodydata: MutableLiveData<String> = MutableLiveData()

    var adapter_bodydata: MutableLiveData<String> = MutableLiveData()

    var type: MutableLiveData<String> = MutableLiveData()

    var progressvalue: MutableLiveData<String> = MutableLiveData()

    val metaindexencryptionData = EncryptionData()
    var userid: String = ""

    init {
        metaindexencryptionData.user_id = "1"
        userid = MakeMyExam.userId
    }

    val handler = CoroutineExceptionHandler { context, exception ->
        println("Caught $exception")
    }

    var responseString: LiveData<String> = MutableLiveData()
    fun getFeedData() {
        viewModelScope.launch(handler) {
            val result = repository.getFeedApidata(bodydata.value.toString())
            try {
                progressvalue.value = "0"
                if (result?.isSuccessful!!) {
                    var jsonObject: JSONObject? = null
                    try {
                        Log.d("abhi", "" + result.body())
                        val decryptdata = AES.decrypt(
                            result.body(),
                            AES.generatekeyAPI(),
                            AES.generateVectorAPI()
                        )

                        jsonObject = JSONObject(decryptdata)
                        SharedPreference.getInstance().putLong("time", jsonObject.optString("time").toLong() * 1000)

                        MakeMyExam.setTime_server(jsonObject.optString("time").toLong() * 1000)

                        checkchnageDetector(jsonObject)
                    } catch (e: Exception) {
                        Log.d("abcd", "" + e.printStackTrace())
                        e.printStackTrace()
                    } finally {
                        jsonObjectmutable.postValue(jsonObject)
                    }

                }
            } catch (e: Exception) {
                Log.d("prince", "" + e.printStackTrace())
            }
        }
    }

    fun getLiveClassData(bodyParams: String) {
        viewModelScope.launch(handler) {
            val result = repository.getLiveClassApidata(bodyParams)
            try {
                progressvalue.value = "0"
                if (result?.isSuccessful!!) {
                    var jsonObject: JSONObject? = null
                    try {
                        Log.d("abhi", "" + result.body())
                        val decryptdata = AES.decrypt(
                            result.body(),
                            AES.generatekeyAPI(),
                            AES.generateVectorAPI()
                        )
                        Log.d("abcd", "" + decryptdata)
                        jsonObject = JSONObject(decryptdata)
                        MakeMyExam.setTime_server(jsonObject.optString("time").toLong() * 1000)
                        checkchnageDetector(jsonObject)
                    } catch (e: Exception) {
                        Log.d("abcd", "" + e.printStackTrace())
                        e.printStackTrace()
                    } finally {
                        mutableLiveClassData.postValue(jsonObject)
                    }

                }
            } catch (e: Exception) {
                Log.d("prince", "" + e.printStackTrace())
            }
        }
    }

    fun getLiveTestData(bodyParams: String) {
        viewModelScope.launch(handler) {
            val result = repository.getLiveTestApidata(bodyParams)
            try {
                progressvalue.value = "0"
                if (result?.isSuccessful!!) {
                    var jsonObject: JSONObject? = null
                    try {
                        Log.d("abhi", "" + result.body())
                        val decryptdata = AES.decrypt(
                            result.body(),
                            AES.generatekeyAPI(),
                            AES.generateVectorAPI()
                        )
                        Log.d("abcd", "" + decryptdata)
                        jsonObject = JSONObject(decryptdata)
                        MakeMyExam.setTime_server(jsonObject.optString("time").toLong() * 1000)
                        checkchnageDetector(jsonObject)
                    } catch (e: Exception) {
                        Log.d("abcd", "" + e.printStackTrace())
                        e.printStackTrace()
                    } finally {
                        mutableLiveTestData.postValue(jsonObject)
                    }

                }
            } catch (e: Exception) {
                Log.d("prince", "" + e.printStackTrace())
            }
        }
    }

    fun getcourutine_adapter_post() {

        viewModelScope.launch(handler) {
            val result = when (type.value) {
                "Like" -> {
                    repository.getpostLike(adapter_bodydata.value.toString())
                }
                "GetComment" -> {
                    repository.getCommentList(adapter_bodydata.value.toString())
                }
                "AddComment" -> {
                    repository.addComment(adapter_bodydata.value.toString())
                }
                "Attempt Mcq" -> {
                    repository.attempt_mcq(adapter_bodydata.value.toString())
                }
                "Pin" -> {
                    repository.pinPost(adapter_bodydata.value.toString())
                }
                "Unpin" -> {
                    repository.pinPost(adapter_bodydata.value.toString())
                }
                else -> {
                    repository.getpostLike(adapter_bodydata.value.toString())
                }
            }
            try {
                if (result?.isSuccessful!!) {
                    val decryptdata = AES.decrypt(result.body(), AES.generatekeyAPI(), AES.generateVectorAPI())
                    val jsonObject = JSONObject(decryptdata)
                    MakeMyExam.setTime_server(jsonObject.optString("time").toLong() * 1000)
                    checkchnageDetector(jsonObject)
                    adapter_response.postValue(jsonObject)
                }
            } catch (e: Exception) {
                Log.d("prince", "" + e.printStackTrace())
            }
        }
    }


    suspend fun checkchnageDetector(jsonObject: JSONObject) {
        if (utkashRoom.getpigibag().isRecordExistsUserId(userid)) {
            if (utkashRoom.getpigibag().getpigidetail(userid).getCdtimestamp()
                    .toLong() < jsonObject.optLong("cd_time")
            ) {
                val chnage_detector_result =
                    repository.getChangeDetector(AES.encrypt(Gson().toJson(metaindexencryptionData)))
                if (chnage_detector_result?.isSuccessful!!) {
                    val decrypt_chnage_detector = AES.decrypt(
                        chnage_detector_result.body(),
                        AES.generatekeyAPI(),
                        AES.generateVectorAPI()
                    )
                    val jsonObject_decrypt_chnage = JSONObject(decrypt_chnage_detector)
                    MakeMyExam.setTime_server(
                        jsonObject_decrypt_chnage.optString("time").toLong() * 1000
                    )
                    if (!userid.equals("0", ignoreCase = true)) {
                        checkandupdateversion(jsonObject_decrypt_chnage)
                    }
                }
            }
            utkashRoom.getpigibag().updaterecord(userid, jsonObject.optLong("cd_time").toString())
        }
    }

    private suspend fun checkandupdateversion(jsonObject: JSONObject) {
        try {
            if (jsonObject.has("auth_code")) {
                if (jsonObject.optString("auth_code") == "100100") {
                    withContext(Dispatchers.Main)
                    {
                        RetrofitResponse.GetApiData(
                            MakeMyExam.getAppContext(),
                            if (jsonObject.has(Const.AUTH_CODE)) jsonObject.getString(Const.AUTH_CODE) else "",
                            jsonObject.getString(Const.MESSAGE),
                            false
                        )
                    }
                }
            } else {
                if (jsonObject.getJSONObject("data").getJSONObject("master").has("ut_009")) {
                    if (utkashRoom.getapidao().is_api_code_exits(userid, "ut_009")) {
                        if (utkashRoom.getapidao().getapidetail(
                                "ut_009",
                                userid
                            ).version.toFloat() < jsonObject.getJSONObject("data")
                                .getJSONObject("master").getString("ut_009").toFloat()
                        ) {
                            utkashRoom.getapidao().updateversion(
                                jsonObject.getJSONObject("data").getJSONObject("master")
                                    .getString("ut_009"), userid, "ut_009"
                            )
                            utkashRoom.launguages.deletedata()
                            utkashRoom.masterAllCatDao.deletedata()
                            utkashRoom.mastercatDao.deletedata()
                            utkashRoom.getcoursetypemaster().deletedata()
                        }
                    } else {
                        val apiMangerTable = APITABLE()
                        apiMangerTable.apicode = "ut_009"
                        apiMangerTable.apiname = "master_content"
                        apiMangerTable.interval = "0"
                        apiMangerTable.user_id = userid
                        apiMangerTable.timestamp = jsonObject.optLong("time").toString()
                        apiMangerTable.cdtimestamp = "0"
                        apiMangerTable.version = "0.000"
                        utkashRoom.getapidao().addUser(apiMangerTable)
                    }
                }
                if (jsonObject.getJSONObject("data").getJSONObject("master").has("ut_012")) {
                    val version_code = SharedPreference.getInstance().getString("version_code")
                    var my_course_version =
                        jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_012")
                            .toFloat()
                    if (version_code == null || version_code.equals(
                            "",
                            ignoreCase = true
                        ) || version_code.toInt() < BuildConfig.VERSION_CODE
                    ) {
                        SharedPreference.getInstance()
                            .putString("version_code", "" + BuildConfig.VERSION_CODE)
                        my_course_version = (my_course_version + 0.001).toString().toFloat()
                    }
                    if (utkashRoom.getapidao().is_api_code_exits(userid, "ut_012")) {
                        if (utkashRoom.getapidao().getapidetail(
                                "ut_012",
                                userid
                            ).version.toFloat() < my_course_version
                        ) {
                            utkashRoom.getapidao()
                                .updateversion("" + my_course_version, userid, "ut_012")
                            utkashRoom.myCourseDao.deletedata()
                        }
                    } else {
                        val apiMangerTable = APITABLE()
                        apiMangerTable.apicode = "ut_012"
                        apiMangerTable.apiname = "get_my_courses"
                        apiMangerTable.interval = "0"
                        apiMangerTable.user_id = userid
                        apiMangerTable.timestamp = jsonObject.optLong("time").toString()
                        apiMangerTable.cdtimestamp = "0"
                        apiMangerTable.version = "0.000"
                        utkashRoom.getapidao().addUser(apiMangerTable)
                    }
                }
                if (jsonObject.getJSONObject("data").getJSONObject("master").has("ut_010")) {
                    if (utkashRoom.getapidao().is_api_code_exits(userid, "ut_010")) {
                        if (utkashRoom.getapidao().getapidetail(
                                "ut_010",
                                userid
                            ).version.toFloat() < jsonObject.getJSONObject("data")
                                .getJSONObject("master").getString("ut_010").toFloat()
                        ) {
                            utkashRoom.getapidao().updateversion(
                                jsonObject.getJSONObject("data").getJSONObject("master")
                                    .getString("ut_010"), userid, "ut_010"
                            )
                            utkashRoom.coursedata.deletedata()
                            utkashRoom.homeApiStatusdata.deletedata()
                        }
                    } else {
                        val apiMangerTable = APITABLE()
                        apiMangerTable.apicode = "ut_010"
                        apiMangerTable.apiname = "get_courses"
                        apiMangerTable.interval = "0"
                        apiMangerTable.user_id = userid
                        apiMangerTable.timestamp = jsonObject.optLong("time").toString()
                        apiMangerTable.cdtimestamp = "0"
                        apiMangerTable.version = "0.000"
                        utkashRoom.getapidao().addUser(apiMangerTable)
                    }
                }
                if (jsonObject.getJSONObject("data").has("uw_master")) {
                    for (i in 0 until jsonObject.getJSONObject("data").getJSONArray("uw_master")
                        .length()) {
                        val userWiseCourseTable = Gson().fromJson(
                            jsonObject.getJSONObject("data")
                                .getJSONArray("uw_master")[i].toString(),
                            UserWiseCourseTable::class.java
                        )
                        if (utkashRoom.courseDetaildata.isRecordExistsUserId(
                                userid,
                                userWiseCourseTable.meta_id
                            )
                        ) {
                            if (userWiseCourseTable.exp.toLong() < jsonObject.optLong("time")) {
                                utkashRoom.courseDetaildata.deletecoursedetail(
                                    userWiseCourseTable.meta_id,
                                    userid
                                )
                            } else {
                                if (utkashRoom.getuserwisecourse().is_api_code_exits(
                                        userid,
                                        userWiseCourseTable.meta_id
                                    )
                                ) {
                                    if (utkashRoom.getuserwisecourse().getapidetail(
                                            userWiseCourseTable.meta_id,
                                            userid
                                        ).version.toFloat() < userWiseCourseTable.version.toFloat()
                                    ) {
                                        utkashRoom.courseDetaildata.deletecoursedetail(
                                            userWiseCourseTable.meta_id,
                                            userid
                                        )
                                    }
                                    utkashRoom.getuserwisecourse().update_api_version(
                                        userWiseCourseTable.meta_id,
                                        userid,
                                        userWiseCourseTable.version
                                    )
                                } else {
                                    userWiseCourseTable.userid = userid
                                    utkashRoom.getuserwisecourse().addUser(userWiseCourseTable)
                                }
                            }
                        } else {
                            utkashRoom.courseDetaildata.deletecoursedetail(
                                userWiseCourseTable.meta_id,
                                userid
                            )
                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }


    companion object {
        @BindingAdapter("feedadapter")
        @JvmStatic
        fun setAdapter(recyclerView: RecyclerView, feedadapter: FeedAdapter) {
            feedadapter.let {
                recyclerView.adapter = feedadapter
            }
        }
    }

}