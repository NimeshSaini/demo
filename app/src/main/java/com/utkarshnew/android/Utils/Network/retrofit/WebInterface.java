package com.utkarshnew.android.Utils.Network.retrofit;


import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Network.API;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface WebInterface {

    @POST(API.API_REQUEST_VIDEO_LINK)
    Call<String> API_REQUEST_VIDEO_LINK(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_REQUEST_VIDEO_LINK)
    Call<JsonObject> API_REQUEST_VIDEO_LINK(@Field(Const.NAME) String video_url,
                                            @Field(Const.COURSE_ID) String course_id,
                                            @Field(Const.TILE_ID) String tile_id);*/

    @POST(API.API_REQUEST_VIDEO_LINK_V2)
    Call<String> API_REQUEST_VIDEO_LINK_V2(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_REQUEST_VIDEO_LINK_V2)
    Call<JsonObject> API_REQUEST_VIDEO_LINK_V2(@Field(Const.NAME) String video_url,
                                            @Field(Const.COURSE_ID) String course_id,
                                            @Field(Const.TYPE) String type,
                                            @Field(Const.TILE_ID) String tile_id);*/

    @POST(API.API_GET_MASTER_DATA)
    Call<String> API_GET_VIDEO_DATA_FIRST2(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<JsonObject> API_GET_VIDEO_DATA_FIRST2(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                               @Field(Const.ID) String ID,
                                               @Field(Const.LAYER) String LAYER,
                                               @Field(Const.TOPIC_ID) String TOPIC_ID,
                                               @Field(Const.FileID) String FileID,
                                               @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                               @Field(Const.TYPE) String TYPE,
                                               @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/

    @FormUrlEncoded
    @POST(API.API_GET_TOPIC_DATA_FIRST)
    Call<JsonObject> API_GET_TOPIC_DATA_FIRST(@Field(Const.SUBJECT_ID) String CHAPTER_ID,
                                              @Field(Const.COURSE_ID) String COURSE_ID);

    @FormUrlEncoded
    @POST(API.API_GET_UNIT_DATA_FIRST)
    Call<JsonObject> API_GET_UNIT_DATA_FIRST(@Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                             @Field(Const.COURSE_ID) String COURSE_ID);

    @FormUrlEncoded
    @POST(API.API_GET_SINGLE_CAT_VIDEO_DATA)
    Call<JsonObject> API_GET_SINGLE_CAT_VIDEO_DATA(@Field(Const.USER_ID) String USER_ID,
                                                   @Field(Const.SUB_CAT) String SUB_CAT,
                                                   @Field(Const.LAST_VIDEO_ID) String LAST_VIDEO_ID,
                                                   @Field(Const.SORT_BY) String SORT_BY,
                                                   @Field(Const.PAGE_SEGMENT) String PAGE_SEGMENT,
                                                   @Field(Const.SEARCH_CONTENT) String SEARCH_CONTENT);

    @POST(API.API_GET_MASTER_DATA)
    Call<String> API_GET_CONCEPT_DATA_FIRST(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<JsonObject> API_GET_CONCEPT_DATA_FIRST(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                                @Field(Const.ID) String ID,
                                                @Field(Const.LAYER) String LAYER,
                                                @Field(Const.TOPIC_ID) String TOPIC_ID,
                                                @Field(Const.TYPE) String TYPE,
                                                @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/

    @FormUrlEncoded
    @POST(API.API_GET_COMPLETE_INFO_TEST_SERIES)
    Call<JsonObject> API_GET_COMPLETE_INFO_TEST_SERIES(@Field(Const.USER_ID) String USER_ID,
                                                       @Field(Const.TESTSERIES_ID) String TESTSERIES_ID);

    @FormUrlEncoded
    @POST(API.API_REQUEST_TEST_DATA)
    Call<JsonObject> API_REQUEST_TEST_DATA(@Field(Const.USER_ID) String USER_ID,
                                           @Field(Const.VIDEO_ID) String VIDEO_ID);

    @FormUrlEncoded
    @POST(API.API_GET_SINGLE_VIDEO_DATA)
    Call<JsonObject> getSingleVideoData(@Field(Const.USER_ID) String USER_ID,
                                        @Field(Const.VIDEO_ID) String VIDEO_ID);

    @POST(API.API_MAKE_FREE_COURSE_TRANSACTION)
    Call<String> API_MAKE_FREE_COURSE_TRANSACTION(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_MAKE_FREE_COURSE_TRANSACTION)
    Call<JsonObject> API_MAKE_FREE_COURSE_TRANSACTION(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                                      @Field(Const.COURSE_ID) String COURSE_ID);*/

    /*@FormUrlEncoded
    @POST(API.API_MAKE_FREE_COURSE_TRANSACTION)
    Call<JsonObject> API_MAKE_FREE_COURSE_TRANSACTION(@Field(Const.USER_ID) String USER_ID,
                                                      @Field(Const.DOUBT_PLAN_PRICE_id) String DOUBT_PLAN_PRICE_id,
                                                      @Field(Const.PAYMENT_FOR) String PAYMENT_FOR,
                                                      @Field(Const.COURSE_ID) String COURSE_ID);*/

    @POST(API.API_GET_BASIC_COURSE)
    Call<String> API_GET_BASIC_COURSE(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_BASIC_COURSE)
    Call<JsonObject> API_GET_BASIC_COURSE(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                          @Field(Const.ID) String ID,
                                          @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                          @Field(Const.data_required) String data_required);*/


    @POST(API.API_GET_BASIC_COURSE)
    Call<String> API_GET_BASIC_COURSE1(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_BASIC_COURSE)
    Call<JsonObject> API_GET_BASIC_COURSE(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                          @Field(Const.ID) String ID,
                                          @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                          @Field(Const.UNIT_ID) String UNIT_ID,
                                          @Field(Const.CHAPTER_ID) String CHAPTER_ID,
                                          @Field(Const.TOPIC_ID) String TOPIC_ID,
                                          @Field(Const.SUBTOPIC_ID) String SUBTOPIC_ID,
                                          @Field(Const.data_required) String data_required);*/


    @POST(API.API_GET_BASIC_COURSE)
    Call<String> API_GET_BASIC_COURSE2(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_BASIC_COURSE)
    Call<JsonObject> API_GET_BASIC_COURSE2(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                          @Field(Const.ID) String ID,
                                          @Field(Const.data_required) String data_required);*/


    @POST(API.API_GET_MASTER_DATA)
    Call<String> API_GET_TEST_DATA_FIRST(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<JsonObject> API_GET_TEST_DATA_FIRST(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                             @Field(Const.ID) String ID,
                                             @Field(Const.SUBJECT_ID1) String SUBJECT_ID,
                                             @Field(Const.UNIT_ID) String UNIT_ID,
                                             @Field(Const.CHAPTER_ID) String CHAPTER_ID,
                                             @Field(Const.TOPIC_ID) String TOPIC_ID,
                                             @Field(Const.SUBTOPIC_ID) String SUBTOPIC_ID,
                                             @Field(Const.LAYER) String LAYER,
                                             @Field(Const.TYPE) String TYPE,
                                             @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/

    @POST(API.API_GET_MASTER_DATA)
    Call<String> API_GET_VIDEO_DATA_FIRST(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<JsonObject> API_GET_VIDEO_DATA_FIRST(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                              @Field(Const.ID) String ID,
                                              @Field(Const.SUBJECT_ID1) String SUBJECT_ID,
                                              @Field(Const.UNIT_ID) String UNIT_ID,
                                              @Field(Const.CHAPTER_ID) String CHAPTER_ID,
                                              @Field(Const.TOPIC_ID) String TOPIC_ID,
                                              @Field(Const.SUBTOPIC_ID) String SUBTOPIC_ID,
                                              @Field(Const.LAYER) String LAYER,
                                              @Field(Const.TYPE) String TYPE,
                                              @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/

    @POST(API.API_GET_MASTER_DATA)
    Call<String> API_GET_CONCEPT_DATA_FIRST1(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<JsonObject> API_GET_CONCEPT_DATA_FIRST1(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                                @Field(Const.ID) String ID,
                                                @Field(Const.SUBJECT_ID1) String SUBJECT_ID,
                                                @Field(Const.UNIT_ID) String UNIT_ID,
                                                @Field(Const.CHAPTER_ID) String CHAPTER_ID,
                                                @Field(Const.TOPIC_ID) String TOPIC_ID,
                                                @Field(Const.SUBTOPIC_ID) String SUBTOPIC_ID,
                                                @Field(Const.LAYER) String LAYER,
                                                @Field(Const.TYPE) String TYPE,
                                                @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/


    @POST(API.API_GET_MASTER_DATA)
    Call<String> API_GET_PRACTICE_DATA_FIRST(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<JsonObject> API_GET_PRACTICE_DATA_FIRST(*//*@Field(Const.USER_ID) String USER_ID,*//*
                                                 @Field(Const.ID) String ID,
                                                 @Field(Const.SUBJECT_ID1) String SUBJECT_ID,
                                                 @Field(Const.UNIT_ID) String UNIT_ID,
                                                 @Field(Const.CHAPTER_ID) String CHAPTER_ID,
                                                 @Field(Const.TOPIC_ID) String TOPIC_ID,
                                                 @Field(Const.SUBTOPIC_ID) String SUBTOPIC_ID,
                                                 @Field(Const.LAYER) String LAYER,
                                                 @Field(Const.TYPE) String TYPE,
                                                 @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/

    @FormUrlEncoded
    @POST
    Call<JsonObject> API_GET_FEEDS_FOR_USER(@Url String url, @Field(Const.USER_ID) String USER_ID,
                                            @Field(Const.LAST_POST_ID) String LAST_POST_ID,
                                            @Field(Const.TAG_ID) String TAG_ID,
                                            @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                            @Field(Const.SEARCH_TEXT) String SEARCH_TEXT);

    @FormUrlEncoded
    @POST
    Call<JsonObject> API_GET_FEEDS_FOR_USER(@Url String url, @Field(Const.USER_ID) String USER_ID,
                                            @Field(Const.LAST_POST_ID) String LAST_POST_ID,
                                            @Field(Const.SEARCH_TEXT) String SEARCH_TEXT);

    @FormUrlEncoded
    @POST
    Call<JsonObject> API_GET_FEEDS_FOR_USER(@Url String url, @Field(Const.USER_ID) String USER_ID);

    @FormUrlEncoded
    @POST(API.API_SEND_ROLL_NO_POST)
    Call<JsonObject> API_SEND_ROLL_NO_POST(@Field(Const.USER_ID) String USER_ID,
                                           @Field(Const.ROLL_NO) String ROLL_NO,
                                           @Field(Const.COMPAIGN_ID) String COMPAIGN_ID);

    @FormUrlEncoded
    @POST(API.API_BOOKMARK)
    Call<JsonObject> API_USER_BOOKMARK(
            /*@Field(Const.USER_ID) String USER_ID,*/
            @Field(Const.ID) String ID,
            @Field(Const.VIDEO_ID) String VIDEO_ID,
            @Field(Const.TIME) String TIME,
            @Field(Const.INFO) String INFO,
            @Field("for") String STATE
    );

    @FormUrlEncoded
    @POST(API.API_UPDATE_DEVICE_TOKEN)
    Call<JsonObject> API_UPDATE_DEVICE_TOKEN(
            @Field(Const.DEVICE_TYPE) String DEVICE_TYPE,
            @Field(Const.DEVICE_TOKEN) String DEVICE_TOKEN);

    @POST(API.API_UPDATE_VIDEO_VIEW)
    Call<String> API_UPDATE_VIDEO_VIEW(@Body String data);

    /*@FormUrlEncoded
    @POST(API.API_UPDATE_VIDEO_VIEW)
    Call<JsonObject> API_UPDATE_VIDEO_VIEW(
            @Field(Const.USER_ID) String USER_ID,
            @Field(Const.TYPE) String type,
            @Field(Const.COURSE_ID) String COURSE_ID,
            @Field(Const.VIDEO_ID) String VIDEO_ID,
            @Field(Const.TOTAL_TIME) String TOTAL_TIME,
            @Field(Const.VIEW_TIME) String VIEW_TIME,
            @Field(Const.TILE_ID) String TILE_ID);*/

    @FormUrlEncoded
    @POST(API.API_SUBJECTIVE_QUESTIONS)
    Call<JsonObject> API_GET_SUBJECTIVE_QUESTIONS(
            @Field(Const.TEST_ID) String TEST_ID,
            @Field(Const.USER_ID) String userid
    );

    @FormUrlEncoded
    @POST(API.API_SUBJECTIVE_SUBMIT)
    Call<JsonObject> Submitanswer(
            @Field(Const.TEST_ID) String TEST_ID,
            @Field(Const.USER_ID) String userid,
            @Field(Const.ANSWERS) String answers
    );



}
