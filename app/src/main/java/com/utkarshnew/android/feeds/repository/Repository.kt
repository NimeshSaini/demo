package com.appsquadz.mvvmwithretrofit.repository

import androidx.lifecycle.MutableLiveData
import com.utkarshnew.android.Utils.Network.APIInterface
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(val apiInterface: APIInterface) {

    suspend fun getFeedApidata(bodydata: String): Response<String?>? {
        return apiInterface.getPost(bodydata)
    }

    suspend fun getPostdata(bodydata: String): Response<String?>? {
        return apiInterface.get_post_detail(bodydata)
    }

    suspend fun getLiveClassApidata(bodydata: String): Response<String?>? {
        return apiInterface.getLiveClass(bodydata)
    }

    suspend fun getLiveTestApidata(bodydata: String): Response<String?>? {
        return apiInterface.getLiveTest(bodydata)
    }

    suspend fun getChangeDetector(bodydata: String): Response<String?>? {
        return apiInterface.getChangedetectot(bodydata)
    }

    suspend fun getpostLike(bodydata: String): Response<String?>? {
        return apiInterface.courutine_like_unlike_post(bodydata)
    }

    suspend fun getCommentList(bodydata: String): Response<String?>? {
        return apiInterface.courutine_get_feed_comments(bodydata)
    }

    suspend fun addComment(bodydata: String): Response<String?>? {
        return apiInterface.courutine_add_comments(bodydata)
    }

    suspend fun attempt_mcq(bodydata: String): Response<String?>? {
        return apiInterface.courutine_attempt_mcq(bodydata)
    }

    suspend fun pinPost(bodydata: String): Response<String?>? {
        return apiInterface.courutinePinPost(bodydata)
    }
}