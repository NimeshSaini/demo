package com.utkarshnew.android.Utils.Network.retrofit;

import com.utkarshnew.android.Utils.Const;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VideoDetailApiInterface {
    @FormUrlEncoded
    @POST("data_model/fanwall/fan_wall/on_request_create_video_link")
    Call<JsonObject> requestVideoLink(
            @Field(Const.NAME) String name
    );

    @FormUrlEncoded
    @POST("data_model/video/Video_channel/get_single_video_data")
    Call<JsonObject> getSingleVideoData(
            @Field(Const.USER_ID) String userid,
            @Field(Const.VIDEO_ID) String VIDEO_ID
    );

    @FormUrlEncoded
    @POST("data_model/video/video_comment/get_video_comment")
    Call<JsonObject> getSingleVideoCommmentList(
            @Field(Const.USER_ID) String user_id,
            @Field(Const.VIDEO_ID) String VIDEO_ID,
            @Field(Const.PARENT_ID) String PARENT_ID,
            @Field(Const.LAST_VIDEO_COMMENT_ID) String LAST_VIDEO_COMMENT_ID
    );

    @FormUrlEncoded
    @POST("data_model/video/video_comment/get_video_comment")
    Call<JsonObject> getSingleVideoComment(
            @Field(Const.USER_ID) String user_id,
            @Field(Const.VIDEO_ID) String VIDEO_ID,
            @Field(Const.LAST_VIDEO_COMMENT_ID) String LAST_VIDEO_COMMENT_ID
    );

    @FormUrlEncoded
    @POST("data_model/video/video_comment/add_comment")
    Call<JsonObject> addVideoComment(
            @Field(Const.USER_ID) String USER_ID,
            @Field(Const.VIDEO_ID) String VIDEO_ID,
            @Field(Const.COMMENT) String COMMENT
    );

    @FormUrlEncoded
    @POST("data_model/video/video_comment/add_comment")
    Call<JsonObject> addVideoCommentList(
            @Field(Const.USER_ID) String USER_ID,
            @Field(Const.VIDEO_ID) String VIDEO_ID,
            @Field(Const.PARENT_ID) String PARENT_ID,
            @Field(Const.COMMENT) String COMMENT
    );

    @FormUrlEncoded
    @POST("data_model/video/Video_like/like_video")
    Call<JsonObject> likeVideoData(
            @Field(Const.USER_ID) String userid,
            @Field(Const.VIDEO_ID) String VIDEO_ID
    );

    @FormUrlEncoded
    @POST("data_model/video/Video_like/dislike_video")
    Call<JsonObject> dislikeVideoData(
            @Field(Const.USER_ID) String userid,
            @Field(Const.VIDEO_ID) String VIDEO_ID
    );

    @FormUrlEncoded
    @POST("data_model/video/Video_channel/add_video_counter")
    Call<JsonObject> addViewVideo(
            @Field(Const.USER_ID) String userid,
            @Field(Const.VIDEO_ID) String VIDEO_ID
    );

    @FormUrlEncoded
    @POST("data_model/video/video_comment/like_video_comment")
    Call<JsonObject> likeVideoComment(
            @Field(Const.USER_ID) String userid,
            @Field(Const.COMMENT_ID) String comment_id
    );

    @FormUrlEncoded
    @POST("data_model/video/video_comment/dislike_video_comment")
    Call<JsonObject> dislikeVideoComment(
            @Field(Const.USER_ID) String userid,
            @Field(Const.COMMENT_ID) String comment_id
    );

    @FormUrlEncoded
    @POST("data_model/video/Video_channel/add_solution_video_counter")
    Call<JsonObject> getVideoSolutionCounter(
            @Field(Const.USER_ID) String userid,
            @Field(Const.VIDEO_ID) String video_id
    );

}
