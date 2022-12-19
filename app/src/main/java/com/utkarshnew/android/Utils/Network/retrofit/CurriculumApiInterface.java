package com.utkarshnew.android.Utils.Network.retrofit;

import com.utkarshnew.android.Utils.Const;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CurriculumApiInterface {
    @FormUrlEncoded
    @POST("data_model/courses/Course/get_all_file_info")
    Call<JsonObject> getFileListCurriculum(
            @Field("user_id") String userid,
            @Field(Const.COURSE_ID) String COURSE_ID
    );

    @FormUrlEncoded
    @POST("data_model/courses/test_series/get_test_series_with_id_app")
    Call<JsonObject> getCompleteInfTestSeries(
            @Field("user_id") String userid,
            @Field(Const.TESTSERIES_ID) String TESTSERIES_ID
    );

    @FormUrlEncoded
    @POST("data_model/courses/test_series/get_test_series_results")
    Call<JsonObject> getCompleteInfTestSeriesResult(
            @Field("user_id") String userid,
            @Field(Const.TESTSERIES_ID) String TESTSERIES_ID
    );

    @FormUrlEncoded
    @POST("data_model/courses/Course/get_quiz")
    Call<JsonObject> getSubjectData(
            @Field("user_id") String userid
    );
}
