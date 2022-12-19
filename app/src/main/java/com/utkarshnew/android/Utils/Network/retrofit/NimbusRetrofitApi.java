package com.utkarshnew.android.Utils.Network.retrofit;

import com.utkarshnew.android.Login.Pojo.NimbusLogin;
import com.utkarshnew.android.Model.VideoRes;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NimbusRetrofitApi {

    @FormUrlEncoded
    @POST("nimbus_user_verification")
    Call<NimbusLogin> signinWithNimbus(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("data_model/video/Video_channel/get_single_video_data")
    Call<List<VideoRes>> getVideoResponse(@FieldMap HashMap<String, String> map);
/*
    @FormUrlEncoded
    @POST("data_model/courses/course/get_subject_by_category")
    Call<Course_Subject> getCourseSubjectData(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("nimbus_login")
    Call<NimbusLogin> loginWithNimbus(@FieldMap HashMap<String, String> map);

    @POST("data_model/user/Registration/nimbus_user_verification")
    Call<NimbusLogin> signinWithNimbus(@Query("user_tokken") String user_tokken,@Query("is_nimbus") String is_nimbus);*/
}
