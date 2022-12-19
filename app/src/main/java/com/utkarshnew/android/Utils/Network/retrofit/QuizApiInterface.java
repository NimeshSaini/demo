package com.utkarshnew.android.Utils.Network.retrofit;

import com.utkarshnew.android.Model.TestseriesBase.TestseriesBase;
import com.utkarshnew.android.Utils.Const;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuizApiInterface {

    @FormUrlEncoded
    @POST("data_model/courses/Test_series/save_test")
    Call<JsonObject> getinstructorData(
            @Field("user_id") String userid,
            @Field(Const.TESTSERIES_ID) String TESTSERIES_ID,
            @Field(Const.TIME_SPENT) String TIME_SPENT,
            @Field(Const.QUESTION_JSON) String QUESTION_JSON
    );

    @FormUrlEncoded
    @POST("data_model/courses/Test_series/get_test_series_basic_result_v2")
    Call<JsonObject> getUserleaderBoardResult_v2(
            @Field("user_id") String userid,
            @Field("test_segment_id") String test_segment_id
    );

    @FormUrlEncoded
    @POST("data_model/courses/Test_series/get_test_series_basic_result_v3")
    Call<JsonObject> getUserleaderBoardResult_v3(
            @Field("user_id") String userid,
            @Field("test_segment_id") String test_segment_id
    );


    @FormUrlEncoded
    @POST("data_model/courses/Test_series/get_test_series_result")
    Call<JsonObject> getTestSeriesSolutionResult(
            @Field("user_id") String userid,
            @Field(Const.TESTSEGMENT_ID) String test_segment_id
    );

    @FormUrlEncoded
    @POST("data_model/courses/Test_series/get_test_series_video_solution")
    Call<JsonObject> getVideoSolutionData(
            @Field("user_id") String userid,
            @Field("test_segment_id") String test_segment_id
    );

    @FormUrlEncoded
    @POST("data_model/courses/Test_series/get_test_series_subject_result")
    Call<JsonObject> getSubjectWiseResult(
            @Field("user_id") String userid,
            @Field("test_segment_id") String test_segment_id
    );

    @FormUrlEncoded
    @POST("data_model/courses/Test_series/get_test_series_with_id")
    Call<TestseriesBase> getTestSeries(
            @Field("user_id") String userid,
            @Field("test_series_id") String test_series_id

    );

    @FormUrlEncoded
    @POST("data_model/courses/Test_series/question_bookmark")
    Call<JsonObject> bookmarkTestSeries(
            @Field("user_id") String userid,
            @Field("question_id") String question_id,
            @Field("q_type") String q_type

    );

    @FormUrlEncoded
    @POST("data_model/courses/test_series/save_test_guess")
    Call<JsonObject> submitTestSeries(
            @FieldMap HashMap<String, String> finalResponse


    );

    @FormUrlEncoded
    @POST("data_model/courses/custom_qbank/save_quiz")
    Call<JsonObject> submitCustomTestSeries(
            @FieldMap HashMap<String, String> finalResponse


    );

    @FormUrlEncoded
    @POST("data_model/courses/test_series/rate_test_series")
    Call<JsonObject> rateQbank(
            @Field(Const.USER_ID) String userid,
            @Field("test_series_id") String test_series_id,
            @Field("rating") String rating

    );
}
