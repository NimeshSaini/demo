package com.utkarshnew.android.Utils.Network.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BookMarkApiInterface {
    @FormUrlEncoded
    @POST("data_model/fanwall/Fan_wall/bookmark_category_list")
    Call<JsonObject> getbookmarkcategories(
            @Field("user_id") String userid,
            @Field("type") String type,
            @Field("stream") String stream
    );

    @FormUrlEncoded
    @POST("data_model/fanwall/Fan_wall/bookmark_list")
    Call<JsonObject> getbookmarklist(
            @Field("user_id") String userid,
            @Field("type") String type,
            @Field("stream") String stream,
            @Field("last_post_id") String last_post_id,
            @Field("tag_id") String tag_id,
            @Field("search_text") String search_text
    );

    @FormUrlEncoded
    @POST("data_model/fanwall/fan_wall/remove_bookmark")
    Call<JsonObject> removeBookmark(
            @Field("user_id") String userid,
            @Field("question_id") String questionId
    );

    @FormUrlEncoded
    @POST("data_model/fanwall/Fan_wall/single_question_bookmark")
    Call<JsonObject> singleQuestionData(
            @Field("user_id") String userid,
            @Field("question_id") String question_id
    );

    @FormUrlEncoded
    @POST("data_model/fanwall/Fan_wall/get_question_bookmark_by_subject")
    Call<JsonObject> allSubjectBookmark(
            @Field("user_id") String userid,
            @Field("subject_id") String subject_id,
            @Field("last_id") String last_post_id

    );
}
