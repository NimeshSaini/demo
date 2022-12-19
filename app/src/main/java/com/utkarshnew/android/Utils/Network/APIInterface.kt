package com.utkarshnew.android.Utils.Network

import com.utkarshnew.android.Utils.Const
import com.utkarshnew.android.Utils.Network.API
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Admin on 15-05-2019.
 */
interface APIInterface {


    @POST(API.post_feed_type)
    fun postFeedDot(@Body data: String): Call<String>


    @POST(API.get_liveclasses_data)
    suspend  fun getLiveClass(@Body data: String?): Response<String?>?

    @POST(API.get_testclasses_data)
    suspend  fun getLiveTest(@Body data: String?): Response<String?>?

    @POST(API.getchangedetecter)
    fun getchangedetector(@Body data: String): Call<String>

    @POST(API.get_video_link_concept)
    fun get_video_link_concept(@Body data: String): Call<String>

    @POST(API.create_annotation)
    fun create_annotation(@Body data: String): Call<String>

    @POST(API.delete_annotation)
    fun delete_annotation(@Body data: String): Call<String>

    @POST(API.add_video_index)
    fun addvideoindex(@Body data: String): Call<String>

    @POST(API.delete_video_index)
    fun deletevideoindex(@Body data: String): Call<String>

    @GET(API.MOBILE_CALCULATOR)
    fun GET_CALCULATOR(): Call<String>

    @POST(API.submitpoll)
    fun sendpoll(@Body data: String): Call<String>

    @POST(API.mark_as_read)
    fun setread(@Body data: String): Call<String>

    @POST(API.mark_as_allread)
    fun MarkAllRead(@Body data: String): Call<String>

    @POST(API.delete_notification)
    fun DeleteNotification(@Body data: String): Call<String>

    @POST(API.get_meta)
    fun getmeta(@Body data: String): Call<String>

    @POST(API.get_meta)
    fun getmetaData(@Body data: String): Call<String>

    @POST(API.get_video_link)
    fun getVideoLink(@Body data: String): Call<String>

    @POST(API.get_sign_test_link)
    fun getvideo_test_link(@Body data: String): Call<String>

    @POST(API.get_notification_data)
    fun getNotification(@Body data: String): Call<String>

    @POST(API.API_SUBMIT_TEST_SERIES)
    fun submitTestSeries(@Body body: String): Call<String>

    @POST(API.challenge_submission)
    fun challenge_submission(@Body body: String): Call<String>

    @POST(API.API_SUBMIT_QUERY)
    fun getresponseofsubmitquery(@Body body: String): Call<String>

    @POST(API.API_TEST_RESULT)
    fun getTestResult(@Body body: String): Call<String>

    @POST(API.API_TEST_LEADERBOARD)
    fun get_test_leaderboard(@Body body: String): Call<String>

    @POST(API.API_TEST_LEARN)
    fun getTestlearn(@Body body: String): Call<String>

    @POST(API.delete_revision)
    fun delete_revision(@Body body: String): Call<String>

    @POST(API.API_GET_TEST_INSTRUCTION_DATA)
    fun API_GET_TEST_INSTRUCTION_DATA(@Body body: String): Call<String>

    @POST(API.API_GET_INFO_TEST_SERIES)
    fun API_GET_INFO_TEST_SERIES(@Body body: String): Call<String>

    @POST(API.API_GET_SOLUTION_TESTSERIES)
    fun API_GET_SOLUTION_TESTSERIES(@Body body: String): Call<String>

    @POST(API.API_CREATE_TEST_RETRIVE_COURSE)
    fun API_CREATE_TEST_RETRIVE_COURSE(@Body data: String): Call<String>

    @POST(API.API_CREATE_TEST_GET_SUBJECT)
    fun API_CREATE_TEST_GET_SUBJECT(@Body data: String): Call<String>

    @POST(API.API_CREATE_TEST_GET_QUES_COUNT)
    fun API_CREATE_TEST_GET_QUES_COUNT(@Body data: String): Call<String>

    @POST(API.API_CREATE_TEST_GET_TEST)
    fun API_CREATE_TEST_GET_TEST(@Body data: String): Call<String>

    @POST(API.user_logout)
    fun getUserLogout(@Body data: String): Call<String>

    @POST(API.get_file_names)
    fun get_file_names(@Body data: String): Call<String>

    @POST(API.get_liveclasses_data)
    fun getLiveClassesData(@Body data: String): Call<String>

    @POST(API.GET_MY_QUIRES)
    fun getMyHelpQuires(@Body data: String): Call<String>

    @POST(API.GET_SUBMIT_MY_QUIRES)
    fun submitHelpQuires(@Body data: String): Call<String>

    @POST(API.GET_QUIRES_REPLIES)
    fun getQuiresReply(@Body data: String): Call<String>

    @POST(API.GET_SUBMIT_QUIRES_REPLIES)
    fun sendSupportQuiry(@Body data: String): Call<String>

    @POST(API.get_testclasses_data)
    fun getLiveTestsData(@Body data: String): Call<String>

    @POST(API.apply_coupon)
    fun apply_coupon(@Body data: String): Call<String>

    @POST(API.int_payment)
    fun int_payment(@Body data: String): Call<String>

    @POST(API.remove_course)
    fun remove_course(@Body data: String): Call<String>

    @POST(API.free_transaction)
    fun free_transaction(@Body data: String): Call<String>
    @POST(API.add_update_address)
    fun add_update_address(@Body data: String): Call<String>
   @POST(API.get_my_addresses)
    fun get_my_addresses(@Body data: String): Call<String>
 @POST(API.delete_my_address)
    fun delete_my_address(@Body data: String): Call<String>
@POST(API.get_delivery_charges)
    fun get_delivery_charges(@Body data: String): Call<String>

    @POST(API.payment_gateway_credentials)
    fun payment_gateway_credentials(@Body data: String): Call<String>

    @POST(API.free_transaction_layer1)
    fun free_transaction_course(@Body data: String): Call<String>

    @POST(API.get_filter_data)
    fun getFilterData(@Body data: String): Call<String>

    @POST(API.get_demo_data)
    fun getvideo(@Body data: String): Call<String>

    @POST(API.API_UPDATE_MOBILE_NUMBER)
    fun updateMobileNumber(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_UPDATE_MOBILE_NUMBER)
    Call<String> updateMobileNumber(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                    @Field(Const.MOBILE) String MOBILE,
                                    @Field(Const.OTP) String OTP);*/
    @POST(API.API_DELETE_USER_ACCOUNT)
    fun deleteUserAccount(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_DELETE_USER_ACCOUNT)
    Call<String> deleteUserAccount(@Field(Const.USER_ID) String USER_ID,
                                    @Field(Const.REASON) String REASON);*/
    @POST(API.API_MOBILE_MENU)
    fun getMobileMenu(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_MOBILE_MENU)
    Call<String> getMobileMenu(@Field("id") String id);*/
    @POST(API.GET_MY_QUIRES)
    fun getMyQuires(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.GET_MY_QUIRES)
    Call<String> getMyQuires(@Field(Const.USER_ID) String USER_ID);*/
    @POST(API.API_GET_MASTER_REGISTRATION_HIT)
    fun getMasterRegistrationHit(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_REGISTRATION_HIT)
    Call<String> getMasterRegistrationHit(@Field("id") String id);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_NOTIFICATION_COUNT)
    Call<String> getNotificationCount(@Field(Const.USER_ID) String userId);*/
    @POST(API.API_GET_NOTIFICATION_COUNT)
    fun getNotificationCount(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_HIT)
    Call<String> getMasterHit(*/
    /*@Field(Const.USER_ID) String userId,*/ /*
                              @Field(Const.DEVICE_TOKEN) String deviceToken);*/
    @POST(API.API_GET_MASTER_HIT)
    fun getMasterHit(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_APP_VERSION)
    Call<String> getAppVersion(@Field("id") String userId);*/
    @get:GET(API.API_GET_APP_VERSION)
    val appVersion: Call<String>

    @POST(API.API_USER_PROFILE_WITH_TOKEN)
    fun getUserProfileWithToken(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_UPDATE_BANNER_HIT_COUNT)
    Call<String> updateBannerHitCount(@Field(Const.BANNER_ID) String bannerId);*/
    @POST(API.API_UPDATE_BANNER_HIT_COUNT)
    fun updateBannerHitCount(@Body data: String): Call<String>

    @FormUrlEncoded
    @POST
    fun getFeedsForUser(
        @Url url: String,
        @Field(Const.USER_ID) userId: String,
        @Field(Const.LAST_POST_ID) lastPostId: String,
        @Field(Const.TAG_ID) tagId: String,
        @Field(Const.SEARCH_TEXT) searchedQuery: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun getFeedsForUser(
        @Url url: String,
        @Field(Const.USER_ID) userId: String,
        @Field(Const.LAST_POST_ID) lastPostId: String,
        @Field(Const.SEARCH_TEXT) searchedQuery: String
    ): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_LIVE_STREAM)
    Call<String> getLiveStream(@Field(Const.USER_ID) String userId);*/
    @POST(API.API_GET_LIVE_STREAM)
    fun getLiveStream(@Body data: String): Call<String>

    @POST(API.API_GET_MASTER_HIT)
    fun getMasterHits(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_HIT)
    Call<String> getMasterHits(*/
    /*@Field(Const.USER_ID) String userId,*/ /*@Field(Const.SUB_CAT) String subCatId);*/
    @GET
    fun getUser(@Url url: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_MOBILE_BANNER)
    Call<String> getMobileBanner(@Field("id") String id);*/
    @POST(API.API_MOBILE_BANNER)
    fun getMobileBanner(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_ALL_CATEGORY_DATA_EXAM)
    Call<String> getAllCategoryDataExam(*/
    /*@Field(Const.USER_ID) String userId,*/ /*
                                        @Field(Const.COURSE_TYPE) String courseType,
                                        @Field(Const.CAT_ID) String catId,
                                        @Field(Const.SUB_CAT) String subCatId,
                                        @Field(Const.STUDY_TYPE) String STUDY_TYPE);*/
    @POST(API.API_GET_ALL_CATEGORY_DATA_EXAM)
    fun getAllCategoryDataExam(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_LANDING_PAGE_DATA)
    Call<String> getLandingPageData(@Field(Const.USER_ID) String USER_ID);*/
    @get:GET(API.API_GET_LANDING_PAGE_DATA)
    val landingPageData: Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_ALL_CATEGORY_DATA)
    Call<String> getAllCategoryData(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                    @Field(Const.CATEGORY_ID) String CATEGORY_ID);*/
    @POST(API.API_GET_ALL_CATEGORY_DATA)
    fun getAllCategoryData(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_ALL_CATEGORY_DATA)
    Call<String> getAllCategoryDataSub(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                       @Field(Const.SUB_CAT) String SUB_CAT);*/
    @POST(API.API_GET_ALL_CATEGORY_DATA)
    fun getAllCategoryDataSub(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_ALL_CATEGORY_DATA)
    Call<String> getAllCategoryDataSub(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                       @Field(Const.SUB_CAT) String SUB_CAT,
                                       @Field(Const.STUDY_TYPE) String STUDY_TYPE);*/
    @POST(API.API_COURSE_META_ALL)
    fun getAllStudyData(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_COURSE_META_ALL)
    Call<String> getAllStudyData(@Field(Const.USER_ID) String USER_ID,
                                 @Field(Const.COURSE_ID) String SUB_CAT,
                                 @Field(Const.STUDY_TYPE) String STUDY_TYPE,
                                 @Field(Const.TAG_TYPE) String TAG_TYPE,
                                 @Field(Const.PAGE) int PAGE);*/
    @FormUrlEncoded
    @POST(API.API_SEARCH_COURSE)
    fun searchCourse( /*@Field(Const.USER_ID) String USER_ID,*/
        @Field(Const.SEARCH_CONTENT) SEARCH_CONTENT: String,
        @Field(Const.LAST_COURSE_ID) LAST_COURSE_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_FAQ_DATA)
    fun getFaqData(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.COURSE_ID) COURSE_ID: String
    ): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MY_COURSE_DATA)
    Call<String> getMyCourseData(@Field(Const.USER_ID) String USER_ID);*/
    @get:GET(API.API_GET_MY_COURSE_DATA)
    val myCourseData: Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_USER_GIVEN_TESTSERIES)
    fun getUserGivenTestSeries(@Field(Const.USER_ID) USER_ID: String): Call<String>

    @POST(API.API_APPLY_COUPON_CODE)
    fun getApplyCouponCode(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_APPLY_COUPON_CODE)
    Call<String> getApplyCouponCode(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                    @Field(Const.COURSE_ID) String COURSE_ID,
                                    @Field(Const.COUPON_CODE) String COUPON_CODE);*/
    @POST(API.API_INITIALIZE_COURSE_PAYMENT)
    fun initializeCoursePayment(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_INITIALIZE_COURSE_PAYMENT)
    Call<String> initializeCoursePayment(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                         @Field(Const.COURSE_ID) String COURSE_ID,
                                         @Field(Const.POINTS_RATE) String POINTS_RATE,
                                         @Field(Const.TAX) String TAX,
                                         @Field(Const.PAY_VIA) String PAY_VIA,
                                         @Field(Const.POINTS_USED) String POINTS_USED,
                                         @Field(Const.COURSE_PRICE) String COURSE_PRICE,
                                         @Field(Const.ADDRESS) String ADDRESS,
                                         @Field(Const.COURSE_APPLIED) String COURSE_APPLIED);*/
    @FormUrlEncoded
    @POST(API.API_ADD_UPDATE_ADDRESS)
    fun addUpdateUserAddress( /*@Field(Const.USER_ID) String USER_ID,*/
        @Field(Const.ID) ID: String,
        @Field(Const.ADDRESS) ADDRESS: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_CHECKSUM_FOR_PAYTM)
    fun getChecksumForPayment(
        @Field(Const.MID) MID: String,
        @Field(Const.ORDER_ID) ORDER_ID: String,
        @Field(Const.INDUSTRY_TYPE_ID) INDUSTRY_TYPE_ID: String,
        @Field(Const.CUST_ID) CUST_ID: String,
        @Field(Const.CHANNEL_ID) CHANNEL_ID: String,
        @Field(Const.TXN_AMOUNT) TXN_AMOUNT: String,
        @Field(Const.WEBSITE) WEBSITE: String,
        @Field(Const.CALLBACK_URL) CALLBACK_URL: String
    ): Call<String>

    @POST(API.API_COMPLETE_COURSE_PAYMENT)
    fun completeCoursePayment(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_COMPLETE_COURSE_PAYMENT)
    Call<String> completeCoursePayment(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                       @Field(Const.COURSE_ID) String COURSE_ID,
                                       @Field(Const.TRANSACTION_STATUS) String TRANSACTION_STATUS,
                                       @Field(Const.COURSE_INIT_PAYMENT_TOKEN) String COURSE_INIT_PAYMENT_TOKEN,
                                       @Field(Const.COURSE_FINAL_PAYMENT_TOKEN) String COURSE_FINAL_PAYMENT_TOKEN);*/
    @FormUrlEncoded
    @POST(API.API_FINAL_TRANSACTION_FOR_PAYTM)
    fun finalTransactionForPaytm( /*@Field(Const.USER_ID) String USER_ID,
                                        @Field(Const.COURSE_ID) String COURSE_ID,
                                        @Field(Const.COURSE_INIT_PAYMENT_TOKEN) String COURSE_INIT_PAYMENT_TOKEN,
                                        @Field(Const.COURSE_FINAL_PAYMENT_TOKEN) String COURSE_FINAL_PAYMENT_TOKEN*/
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_REWARD_POINTS)
    fun getRewardPoints(@Field(Const.USER_ID) USER_ID: String): Call<String>

    @POST(API.API_MAKE_FREE_COURSE_TRANSACTION)
    fun makeFreeCourseTransaction(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_MAKE_FREE_COURSE_TRANSACTION)
    Call<String> makeFreeCourseTransaction(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                           @Field(Const.COURSE_ID) String COURSE_ID);*/
    /*@FormUrlEncoded
    @POST(API.API_MAKE_FREE_COURSE_TRANSACTION)
    Call<String> makeFreeCourseTransaction(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                           @Field(Const.COURSE_ID) String COURSE_ID,
                                           @Field(Const.ADDRESS) String ADDRESS,
                                           @Field(Const.COURSE_APPLIED) String COURSE_APPLIED);*/
    @FormUrlEncoded
    @POST(API.API_ADD_REVIEW_COURSE)
    fun addReviewCourse(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.COURSE_ID) COURSE_ID: String,
        @Field(Const.RATINGS) RATINGS: String,
        @Field(Const.TEXT) TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_INSTRUCTOR_REVIEW_LIST)
    fun getInstructorReviewList(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.INSTR_ID) INSTR_ID: String,
        @Field(Const.LAST_REVIEW_ID) LAST_REVIEW_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_COURSE_REVIEW_LIST)
    fun getCourseReviewList(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.COURSE_ID) COURSE_ID: String,
        @Field(Const.LAST_REVIEW_ID) LAST_REVIEW_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_FILE_LIST_CURRICULUM)
    fun getFileListCurriculum(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.COURSE_ID) COURSE_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_COMPLETE_INFO_TEST_SERIES)
    fun getCompleteInfoTestSeries(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.TESTSERIES_ID) TESTSERIES_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_INSTRUCTOR_DATA)
    fun getInstructorData(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.INSTR_ID) INSTR_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_INSTRUCTOR_REVIEW_COURSE)
    fun addInstructorReviewCourse(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.INSTR_ID) INSTR_ID: String,
        @Field(Const.RATINGS) RATINGS: String,
        @Field(Const.TEXT) TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DELETE_USER_INSTRUCTOR_REVIEWS)
    fun deleteUserInstructorReviews(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.INSTR_ID) INSTR_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_USER_INSTRUCTOR_REVIEWS)
    fun editUserInstructorReview(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.INSTR_ID) INSTR_ID: String,
        @Field(Const.RATINGS) RATINGS: String,
        @Field(Const.TEXT) TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_USER_RESULT)
    fun getUserResult(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.TESTSEGMENT_ID) TEST_SEGMENT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_COMPLETE_INFO_TESTSERIES)
    fun completeInfoTestSeries(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.TESTSERIES_ID) TESTSERIES_ID: String,
        @Field(Const.TIME_SPENT) TIME_SPENT: String,
        @Field(Const.QUESTION_JSON) QUESTION_JSON: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_USER_LEADERBOARD_RESULT)
    fun getUserLeaderBoardResult(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.TESTSEGMENT_ID) TESTSEGMENT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_SOLUTION_TESTSERIES)
    fun getSolutionTestseries(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.TESTSEGMENT_ID) TESTSEGMENT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_SINGLE_COURSE_INFO_RAW)
    fun singleCourseInfoRaw( /*@Field(Const.USER_ID) String USER_ID,*/
        @Field(Const.COURSE_ID) COURSE_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DELETE_USER_COURSE_REVIEWS)
    fun deleteUserCourseReviews(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.COURSE_ID) COURSE_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_USER_COURSE_REVIEWS)
    fun editUserCourseReviews(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.COURSE_ID) COURSE_ID: String,
        @Field(Const.RATINGS) RATINGS: String,
        @Field(Const.TEXT) TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_QUES_ANS_BOOKMARK)
    fun addQuesAnsBookmark(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.TEST_ID) TEST_ID: String,
        @Field(Const.CONFIG_ID) CONFIG_ID: String,
        @Field(Const.BOOKMARK_ID) BOOKMARK_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_BOOKMARK)
    fun addBookmark(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.POST_ID) POST_ID: String,
        @Field(Const.AREA) AREA: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_REMOVE_BOOKMARK)
    fun removeBookmark(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.POST_ID) POST_ID: String,
        @Field(Const.AREA) AREA: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_REMOVE_FAV)
    fun removeFav(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_FEEDS_FOR_USER)
    fun getFeedsForUserAffair(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.LAST_POST_ID) LAST_POST_ID: String,
        @Field(Const.TAG_ID) TAG_ID: String,
        @Field(Const.SEARCH_TEXT) SEARCH_TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_FEEDS_FOR_USER)
    fun getFeedsForUserAffair(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.LAST_POST_ID) LAST_POST_ID: String,
        @Field(Const.SEARCH_TEXT) SEARCH_TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun getFeedsForUserAffairUrl(
        @Url url: String,
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.LAST_POST_ID) LAST_POST_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun likeCountListUrl(
        @Url url: String,
        @Field(Const.POST_ID) POST_ID: String,
        @Field(Const.LAST_LIKE_ID) LAST_LIKE_ID: String
    ): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_DAILY_DOSE)
    Call<String> dailyDose(@Field(Const.USER_ID) String USER_ID,
                           @Field(Const.MENU_ID) String MENU_ID);*/
    @POST(API.API_DAILY_DOSE)
    fun dailyDose(@Body MENU_ID: String): Call<String>

    @FormUrlEncoded
    @POST
    fun getFeedForUserExam(
        @Url url: String,
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.LAST_POST_ID) LAST_POST_ID: String,
        @Field(Const.TAG_ID) TAG_ID: String,
        @Field(Const.SUBJECT_ID) SUBJECT_ID: String,
        @Field(Const.SEARCH_TEXT) SEARCH_TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun getFeedForUserExpert(
        @Url url: String,
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.LAST_POST_ID) LAST_POST_ID: String,
        @Field(Const.TAG_ID) TAG_ID: String,
        @Field(Const.SEARCH_TEXT) SEARCH_TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_COURSE_SUBJECT_DATA)
    fun courseSubjectData(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.MAIN_ID) MAIN_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun courseSubjectDataUrl(
        @Url url: String,
        @Field(Const.MAIN_ID) MAIN_ID: String
    ): Call<String>

    @get:GET(API.API_USER_LOGOUT_WITH_TOKEN)
    val logoutUser: Call<String>

    @get:GET(API.API_GET_USER)
    val user: Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_SINGLE_CAT_VIDEO_DATA)
    fun getSingleCalVideoData(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.SUB_CAT) SUB_CAT: String,
        @Field(Const.LAST_VIDEO_ID) LAST_VIDEO_ID: String,
        @Field(Const.SORT_BY) SORT_BY: String,
        @Field(Const.PAGE_SEGMENT) PAGE_SEGMENT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_SINGLE_CAT_VIDEO_DATA)
    fun getSingleCalVideoData(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.SUB_CAT) SUB_CAT: String,
        @Field(Const.LAST_VIDEO_ID) LAST_VIDEO_ID: String,
        @Field(Const.SORT_BY) SORT_BY: String,
        @Field(Const.PAGE_SEGMENT) PAGE_SEGMENT: String,
        @Field(Const.SEARCH_CONTENT) SEARCH_CONTENT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_SINGLE_CAT_VIDEO_DATA)
    fun getSingleCalVideoData(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.SUB_CAT) SUB_CAT: String,
        @Field(Const.LAST_VIDEO_ID) LAST_VIDEO_ID: String,
        @Field(Const.SORT_BY) SORT_BY: String,
        @Field(Const.PAGE_SEGMENT) PAGE_SEGMENT: String,
        @Field(Const.SEARCH_CONTENT) SEARCH_CONTENT: String,
        @Field(Const.MAIN_CAT_ID) MAIN_CAT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_SINGLE_CAT_VIDEO_DATA)
    fun getSingleCalVideoDataIBT(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.SUB_CAT) SUB_CAT: String,
        @Field(Const.LAST_VIDEO_ID) LAST_VIDEO_ID: String,
        @Field(Const.SORT_BY) SORT_BY: String,
        @Field(Const.PAGE_SEGMENT) PAGE_SEGMENT: String,
        @Field(Const.RELATED_VIDEO_ID) RELATED_VIDEO_ID: String,
        @Field(Const.SEARCH_CONTENT) SEARCH_CONTENT: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun getFeedForUserDailyNews(
        @Url url: String,
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.LAST_POST_ID) LAST_POST_ID: String,  /*@Field(Const.TAG_ID) String TAG_ID,*/
        @Field(Const.TAG_TYPE) TAG_TYPE: String,
        @Field(Const.SEARCH_TEXT) SEARCH_TEXT: String
    ): Call<String>

    @POST(API.API_UPDATE_PASSWORD_WITH_OTP)
    fun getSingleCalVideoData(@Body data: String): Call<String>

    @FormUrlEncoded
    @POST(API.SAVE_EXAM_PREFERENCE)
    fun saveExamPreference( /*@Field(Const.USER_ID) String USER_ID,*/
        @Field(Const.PREFERENCE) PREFERENCE: String
    ): Call<String>

    @get:GET(API.API_GET_MASTER_REGISTRATION_HIT)
    val masterRegistrationHit: Call<String>

    @POST(API.API_USER_LOGIN_AUTHENTICATION)
    fun userLoginAuthentication(@Body data: String): Call<String>

    @POST(API.get_my_profile)
    fun userProfile(@Body data: String): Call<String>

    @POST(API.API_STATE)
    fun GetState(@Body data: String): Call<String>

    @POST(API.API_CITY)
    fun GetCity(@Body data: String): Call<String>

    @POST(API.master_content)
    fun master_content(@Body data: String?): Call<String?>?

    @POST(API.getPost)
    suspend  fun getPost(@Body data: String?): Response<String?>?

  @POST(API.get_post_detail)
    suspend  fun get_post_detail(@Body data: String?): Response<String?>?

    @POST(API.like_unlike_post)
    suspend  fun courutine_like_unlike_post(@Body data: String?): Response<String?>?
  @POST(API.get_feed_comments)
    suspend  fun courutine_get_feed_comments(@Body data: String?): Response<String?>?

    @POST(API.comment_post)
    suspend  fun courutine_add_comments(@Body data: String?): Response<String?>?
  @POST(API.attempt_mcq)
    suspend  fun courutine_attempt_mcq(@Body data: String?): Response<String?>?

    @POST(API.pin_post)
    suspend  fun courutinePinPost(@Body data: String?): Response<String?>?


    @POST(API.getchangedetecter)
    suspend  fun getChangedetectot(@Body data: String?): Response<String?>?

    @POST(API.like_unlike_post)
    fun like_unlike_post(@Body data: String): Call<String>

    @POST(API.get_feed_comments)
    fun get_feed_comments(@Body data: String): Call<String>

    @POST(API.comment_post)
    fun comment_post(@Body data: String): Call<String>

    @POST(API.attempt_mcq)
    fun attempt_mcq(@Body data: String): Call<String>

    @POST(API.update_preference)
    fun update_preference(@Body data: String?): Call<String?>?

    @POST(API.get_courses)
    fun get_courses(@Body data: String): Call<String>

    @POST(API.video_link)
    fun video_link(@Body data: String): Call<String>

    @POST(API.GET_COUPON_OVER_COURSE)
    fun GET_COUPON_OVER_COURSE(@Body data: String): Call<String>

    //    @POST(API.API_USER_LOGIN_AUTHENTICATION)
    //    Call<String> userLoginAuthentication(@Body String data);
    /*@FormUrlEncoded
        @POST(API.API_USER_LOGIN_AUTHENTICATION)
        Call<String> userLoginAuthentication(@Field(Const.EMAIL) String EMAIL,
                                                       @Field(Const.PASSWORD) String PASSWORD,
                                                       @Field(Const.IS_SOCIAL) String IS_SOCIAL,
                                                       @Field(Const.SOCIAL_TYPE) String SOCIAL_TYPE,
                                                       @Field(Const.SOCIAL_TOKEN) String SOCIAL_TOKEN,
                                                       @Field(Const.DEVICE_TYPE) String DEVICE_TYPE,
                                                       @Field(Const.LOCATION) String Location,
                                                       @Field(Const.DEVICEID) String DEVICE_id,
                                                       @Field(Const.DEVICE_TOKEN) String DEVICE_TOKEN,
                                                       @Field(Const.PROFILE_PICTURE) String PROFILE_PICTURE);*/
    @get:GET(API.API_USER_PROFILE_WITH_TOKEN)
    val userProfileWithToken: Call<String>

    @FormUrlEncoded
    @POST(API.API_STREAM_REGISTRATION)
    fun streamRegistration(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.MASTER_ID) MASTER_ID: String,
        @Field(Const.MASTER_ID_LEVEL_ONE) MASTER_ID_LEVEL_ONE: String,
        @Field(Const.MASTER_ID_LEVEL_TWO) MASTER_ID_LEVEL_TWO: String,
        @Field(Const.OPTIONAL_TEXT) OPTIONAL_TEXT: String,
        @Field(Const.INTERESTED_COURSE) INTERESTED_COURSE: String,
        @Field(Const.NAME) NAME: String,
        @Field(Const.EMAIL) EMAIL: String,
        @Field(Const.PROFILE_PICTURE) PROFILE_PICTURE: String
    ): Call<String>

    @POST(API.API_SEND_OTP_VERIFICATION)
    fun getOtpVerification(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_OTP)
    Call<String> getOtp(@Field(Const.MOBILE) String MOBILE,
                        @Field(Const.COUNTRY_CODE) String COUNTRY_CODE,
                        @Field(Const.EMAIL) String EMAIL);

    @FormUrlEncoded
    @POST(API.API_OTP)
    Call<String> getOtp(@Field(Const.EMAIL) String EMAIL,
                        @Field(Const.MOBILE) String MOBILE,
                        @Field(Const.COUNTRY_CODE) String COUNTRY_CODE,
                        @Field(Const.NAME) String NAME,
                        @Field(Const.PASSWORD) String PASSWORD,
                        @Field(Const.IS_SOCIAL) String IS_SOCIAL,
                        @Field(Const.SOCIAL_TYPE) String SOCIAL_TYPE,
                        @Field(Const.SOCIAL_TOKEN) String SOCIAL_TOKEN,
                        @Field(Const.DEVICE_TYPE) String DEVICE_TYPE,
                        @Field(Const.DEVICE_TOKEN) String DEVICE_TOKEN);

    @FormUrlEncoded
    @POST(API.API_CHANGE_PASSWORD_OTP)
    Call<String> getChangePasswordOtp(@Field(Const.MOBILE) String mobileNumber,
                                      @Field(Const.OTP) String OTP);

    @FormUrlEncoded
    @POST(API.API_CHANGE_PASSWORD_OTP)
    Call<String> getChangePasswordOTP(@Field(Const.COUNTRY_CODE) String COUNTRY_CODE,
                                      @Field(Const.MOBILE) String mobileNumber);*/
    /*@FormUrlEncoded
    @POST(API.API_OTP_FOR_MOBILE_CHANGE)
    Call<String> otpForMobileChange(@Field(Const.USER_ID) String USER_ID,
                                    @Field(Const.MOBILE) String MOBILE,
                                    @Field(Const.COUNTRY_CODE) String COUNTRY_CODE);*/
    @POST(API.API_REGISTER_USER)
    fun registerUser(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_REGISTER_USER)
    Call<String> registerUser(@Field(Const.NAME) String NAME,
                              @Field(Const.EMAIL) String EMAIL,
                              @Field(Const.PASSWORD) String PASSWORD,
                              @Field(Const.MOBILE) String MOBILE,
                              @Field(Const.COUNTRY_CODE) String COUNTRY_CODE,
                              @Field(Const.IS_SOCIAL) String IS_SOCIAL,
                              @Field(Const.SOCIAL_TYPE) String SOCIAL_TYPE,
                              @Field(Const.SOCIAL_TOKEN) String SOCIAL_TOKEN,
                              @Field(Const.DEVICE_TYPE) String DEVICE_TYPE,
                              @Field(Const.DEVICEID) String DEVICE_id,
                              @Field(Const.DEVICE_TOKEN) String DEVICE_TOKEN,
                              @Field(Const.PROFILE_PICTURE) String PROFILE_PICTURE);*/
    @POST(API.API_REGISTER_USER)
    fun registerUser1(@Body data: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_REGISTER_USER)
    fun registerUser1(
        @Field(Const.NAME) NAME: String,
        @Field(Const.EMAIL) EMAIL: String,
        @Field(Const.PASSWORD) PASSWORD: String,
        @Field(Const.MOBILE) MOBILE: String,
        @Field(Const.IS_SOCIAL) IS_SOCIAL: String,
        @Field(Const.DEVICEID) DEVICE_id: String,
        @Field("city") city: String,
        @Field(Const.STATE) state: String,
        @Field(Const.OTP) otp: String,
        @Field(Const.DEVICE_TOKEN) devicetoken: String
    ): Call<String>

    @POST(API.API_REGISTER_USER)
    fun registerUserSign(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_REGISTER_USER)
    Call<String> registerUserSign(@Field(Const.NAME) String NAME,
                                  @Field(Const.EMAIL) String EMAIL,
                                  @Field(Const.LOCATION) String LOCATION,
                                  @Field(Const.AGE) String AGE,
                                  @Field(Const.GENDER) String GENDER,
                                  @Field(Const.PASSWORD) String PASSWORD,
                                  @Field(Const.MOBILE) String MOBILE,
                                  @Field(Const.COUNTRY_CODE) String COUNTRY_CODE,
                                  @Field(Const.IS_SOCIAL) String IS_SOCIAL,
                                  @Field(Const.SOCIAL_TYPE) String SOCIAL_TYPE,
                                  @Field(Const.SOCIAL_TOKEN) String SOCIAL_TOKEN,
                                  @Field(Const.DEVICE_TYPE) String DEVICE_TYPE,
                                  @Field(Const.DEVICEID) String DEVICE_id,
                                  @Field(Const.DEVICE_TOKEN) String DEVICE_TOKEN,
                                  @Field(Const.USER_TOKEN) String USER_TOKEN,
                                  @Field(Const.OTP) String OTP);*/
    @FormUrlEncoded
    @POST(API.API_USER_DAMS_VERIFICATION)
    fun userDamsVerification(@Field(Const.USER_TOKEN) USER_TOKEN: String): Call<String>

    @POST(API.API_GET_MASTER_DATA)
    fun getMasterDataFirst(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getMasterDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                    @Field(Const.ID) String ID,
                                    @Field(Const.LAYER) String LAYER,
                                    @Field(Const.TYPE) String TYPE,
                                    @Field(Const.TILE_ID) String TILE_ID,
                                    @Field(Const.REVERT_API) String REVERT_API,
                                    @Field(Const.COMBO_COURSE_ID) String COMBO_COURSE_ID);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getTestDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                  @Field(Const.ID) String ID,
                                  @Field(Const.LAYER) String LAYER,
                                  @Field(Const.TYPE) String TYPE,
                                  @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    //    @POST(API.API_GET_MASTER_DATA)
    //    Call<String> getTestDataFirst(@Body String data);
    @FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    fun getTestDataFirst(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.ID) ID: String,
        @Field(Const.LAYER) LAYER: String,
        @Field(Const.MAIN_ID) MAIN_ID: String,
        @Field(Const.TYPE) TYPE: String,
        @Field(Const.TILE_ID) TILE_ID: String,
        @Field(Const.REVERT_API) REVERT_API: String
    ): Call<String>

    @POST(API.API_GET_MASTER_DATA)
    fun getTestDataFirsts(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getTestDataFirsts(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                  @Field(Const.ID) String ID,
                                  @Field(Const.LAYER) String LAYER,
                                  @Field(Const.MAIN_ID) String MAIN_ID,
                                  @Field(Const.SUB_ID) String SUB_ID,
                                  @Field(Const.TEST_TYPE) String TEST_TYPE,
                                  @Field(Const.TYPE) String TYPE,
                                  @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API,
                                  @Field(Const.TEST_FILTER) String TEST_FILTER,
                                  @Field(Const.PAGE) int PAGE);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getVideoDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                   @Field(Const.ID) String ID,
                                   @Field(Const.LAYER) String LAYER,
                                   @Field(Const.TYPE) String TYPE,
                                   @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    @POST(API.API_GET_MASTER_DATA)
    fun getVideoDataFirst(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getVideoDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                   @Field(Const.ID) String ID,
                                   @Field(Const.LAYER) String LAYER,
                                   @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                   @Field(Const.TYPE) String TYPE,
                                   @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getVideoDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                   @Field(Const.ID) String ID,
                                   @Field(Const.LAYER) String LAYER,
                                   @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                   @Field(Const.TOPIC_ID) String TOPIC_ID,
                                   @Field(Const.TYPE) String TYPE,
                                   @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    @POST(API.API_GET_MASTER_DATA)
    fun getVideoDataFirstt(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getVideoDataFirstt(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                   @Field(Const.ID) String ID,
                                   @Field(Const.LAYER) String LAYER,
                                   @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                   @Field(Const.TOPIC_ID) String TOPIC_ID,
                                   @Field(Const.TYPE) String TYPE,
                                   @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API,
                                   @Field(Const.TEST_FILTER) String TEST_FILTER,
                                   @Field(Const.PAGE) int PAGE);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getConceptFirstData(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                     @Field(Const.ID) String ID,
                                     @Field(Const.LAYER) String LAYER,
                                     @Field(Const.TYPE) String TYPE,
                                     @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getFreeMagazineFirstData(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                      @Field(Const.ID) String ID,
                                      @Field(Const.LAYER) String LAYER,
                                          @Field(Const.TYPE) String TYPE,
                                          @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getMagazineFirstData(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                     @Field(Const.ID) String ID,
                                     @Field(Const.LAYER) String LAYER,
                                      @Field(Const.TYPE) String TYPE,
                                      @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getConceptFirstData(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                     @Field(Const.ID) String ID,
                                     @Field(Const.LAYER) String LAYER,
                                     @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                     @Field(Const.TYPE) String TYPE,
                                     @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getFreeMagazineFirstData(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                      @Field(Const.ID) String ID,
                                      @Field(Const.LAYER) String LAYER,
                                      @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                          @Field(Const.TYPE) String TYPE,
                                          @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getMagazineFirstData(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                     @Field(Const.ID) String ID,
                                     @Field(Const.LAYER) String LAYER,
                                     @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                      @Field(Const.TYPE) String TYPE,
                                      @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    @POST(API.API_GET_MASTER_DATA)
    fun getConceptDataFirst(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getConceptDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                     @Field(Const.ID) String ID,
                                     @Field(Const.LAYER) String LAYER,
                                     @Field(Const.TOPIC_ID) String TOPIC_ID,
                                     @Field(Const.TYPE) String TYPE,
                                     @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    @POST(API.API_GET_MASTER_DATA)
    fun getConceptDataFirsst(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getConceptDataFirsst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                     @Field(Const.ID) String ID,
                                     @Field(Const.LAYER) String LAYER,
                                     @Field(Const.TOPIC_ID) String TOPIC_ID,
                                     @Field(Const.TYPE) String TYPE,
                                     @Field(Const.TILE_ID) String TILE_ID,
                                     @Field(Const.REVERT_API) String REVERT_API,
                                     @Field(Const.TEST_FILTER) String TEST_FILTER,
                                     @Field(Const.PAGE) int PAGE);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getFreeMagazineDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                      @Field(Const.ID) String ID,
                                      @Field(Const.LAYER) String LAYER,
                                      @Field(Const.TOPIC_ID) String TOPIC_ID,
                                          @Field(Const.TYPE) String TYPE,
                                          @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getMagazineDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                     @Field(Const.ID) String ID,
                                     @Field(Const.LAYER) String LAYER,
                                     @Field(Const.TOPIC_ID) String TOPIC_ID,
                                      @Field(Const.TYPE) String TYPE,
                                      @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getPracticeDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                      @Field(Const.ID) String ID,
                                      @Field(Const.LAYER) String LAYER,
                                      @Field(Const.TYPE) String TYPE,
                                      @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getPracticeDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                      @Field(Const.ID) String ID,
                                      @Field(Const.LAYER) String LAYER,
                                      @Field(Const.SUBJECT_ID) String SUBJECT_ID,
                                      @Field(Const.TYPE) String TYPE,
                                      @Field(Const.TILE_ID) String TILE_ID,
                                  @Field(Const.REVERT_API) String REVERT_API);*/
    @POST(API.API_GET_MASTER_DATA)
    fun getPracticeDataFirst(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_MASTER_DATA)
    Call<String> getPracticeDataFirst(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                      @Field(Const.ID) String ID,
                                      @Field(Const.LAYER) String LAYER,
                                      @Field(Const.MAIN_ID) String MAIN_ID,
                                      @Field(Const.SUB_ID) String SUB_ID,
                                      @Field(Const.TEST_TYPE) String TEST_TYPE,
                                      @Field(Const.TYPE) String TYPE,
                                      @Field(Const.TILE_ID) String TILE_ID,
                                      @Field(Const.REVERT_API) String REVERT_API,
                                      @Field(Const.TEST_FILTER) String TEST_FILTER,
                                  @Field(Const.PAGE) int PAGE);*/
    @POST(API.API_GET_LANDING_PAGE_DATA_EXAM)
    fun getLandingPageDataExam(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_LANDING_PAGE_DATA_EXAM)
    Call<String> getLandingPageDataExam(*/
    /*@Field(Const.USER_ID) String USER_ID,*/ /*
                                        @Field(Const.COURSE_TYPE) String COURSE_TYPE,
                                        @Field(Const.CAT_ID) String CAT_ID,
                                        @Field(Const.STUDY_TYPE) String STUDY_TYPE);*/
    @FormUrlEncoded
    @POST(API.API_STUDY_SLIDER_IMAGES)
    fun getStudySliderImages(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.SLIDER_ID) SLIDER_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_SEARCH_COURSE_EXAM)
    fun searchCourseExam(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.SEARCH_CONTENT) SEARCH_CONTENT: String,
        @Field(Const.LAST_COURSE_ID) LAST_COURSE_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_CART_COURSE_EXAM)
    fun cartCourseExam(@Field(Const.COURSE_ID) COURSE_ID: String): Call<String>

    @POST(API.API_GET_BASIC_COURSE)
    fun getBasicCourse(@Body data: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_POPUP_DATA_COLLECTION)
    fun getPopupDataCollection(
        @Field("popup_id") popup_id: String,
        @Field("course_id") course_id: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_BASIC_COURSE)
    fun getBasicCourse(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.ID) ID: String,
        @Field(Const.PARENT_ID) PARENT_ID: String
    ): Call<String>

    @POST(API.API_GET_COURSE_DETAIL)
    fun getCourseDetail(@Body data: String): Call<String>

    @POST(API.API_GET_MASTER_DATA)
    fun getMasterDataOverviewFAQ(@Body data: String): Call<String>

    @POST(API.API_GET_MASTER_DATA)
    fun getMasterDataVideo(@Body data: String): Call<String>

    @POST(API.API_GET_MASTER_DATA)
    fun getMasterDataVideoTwo(@Body data: String): Call<String>

    @POST(API.API_GET_MASTER_DATA)
    fun getMasterDataVideoThree(@Body data: String): Call<String>

    @POST(API.API_GET_MASTER_DATA)
    fun getMasterDataTest(@Body data: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_VIDEO_FEEDBACK)
    fun getVideoFeedback( /*@Field(Const.USER_ID) String USER_ID,*/
        @Field(Const.ID) ID: String,
        @Field(Const.POINT) POINT: String,
        @Field(Const.TEXT) TEXT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.MAKE_LEARNER)
    fun makeLearner( /*@Field(Const.USER_ID) String USER_ID,*/
        @Field(Const.SEGMENT_TYPE) SEGMENT_TYPE: String,
        @Field(Const.SEGMENT_ID) SEGMENT_ID: String,
        @Field(Const.SECTION_ID) SECTION_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_PRACTICE_STATUS)
    fun getPracticeStatus(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.PRACTICE_ID) PRACTICE_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_FAV_VIDEOS)
    fun getFavVideos(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.SUB_CAT) SUB_CAT: String,
        @Field(Const.LAST_VIDEO_ID) LAST_VIDEO_ID: String,
        @Field(Const.SORT_BY) SORT_BY: String,
        @Field(Const.FAVOURITE) FAVOURITE: String,
        @Field(Const.PAGE_SEGMENT) PAGE_SEGMENT: String,
        @Field(Const.SEARCH_CONTENT) SEARCH_CONTENT: String
    ): Call<String>

    //@FormUrlEncoded
    @POST(API.API_REQUEST_VIDEO_LINK)
    fun requestVideoLink(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_REQUEST_VIDEO_LINK)
    Call<String> requestVideoLink(@Field(Const.NAME) String NAME);*/
    @FormUrlEncoded
    @POST(API.API_GET_SINGLE_VIDEO_DATA)
    fun getSingleVideoData(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_VIDEO_COMMENT)
    fun getVideoComment(
        @Field(Const.VIDEO_ID) VIDEO_ID: String,
        @Field(Const.LAST_VIDEO_COMMENT_ID) LAST_VIDEO_COMMENT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_VIDEO_COMMENT)
    fun getVideoComment(
        @Field(Const.VIDEO_ID) VIDEO_ID: String,
        @Field(Const.PARENT_ID) PARENT_ID: String,
        @Field(Const.LAST_VIDEO_COMMENT_ID) LAST_VIDEO_COMMENT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_VIDEO_COMMENT)
    fun addVideoComment(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String,
        @Field(Const.COMMENT) COMMENT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_VIDEO_COMMENT)
    fun addVideoComment(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String,
        @Field(Const.PARENT_ID) PARENT_ID: String,
        @Field(Const.COMMENT) COMMENT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_LIKE_VIDEO)
    fun likeVideo(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DISLIKE_VIDEO)
    fun disLikeVideo(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_VIEW_VIDEO)
    fun addViewVideo(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_VIDEO_COMMENT)
    fun editVideoComment(
        @Field(Const.ID) ID: String,
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String,
        @Field(Const.POST_COMMENT) POST_COMMENT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DELETE_VIDEO_COMMENT)
    fun deleteVideoComment(
        @Field(Const.ID) ID: String,
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String
    ): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_NOTIFICATION_SETTING)
    Call<String> getNotificationSettings(*/
    /*@Field(Const.USER_ID) String userId*/ /*);*/
    @get:GET(API.API_GET_NOTIFICATION_SETTING)
    val notificationSettings: Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_NOTIFICATION_SETTING)
    fun editNotificationSettings( /*@Field(Const.USER_ID) String USER_ID,*/
        @Field(Const.OPTION) option: String,
        @Field(Const.OPTION_VALUE) optionValue: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_ALLOW_TAGGING)
    fun editAllowSettings(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.STATUS) option: String
    ): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_GET_PREFERENCES)
    Call<String> getPreferences(@Field(Const.USER_ID) String USER_ID);*/
    @get:GET(API.API_GET_PREFERENCES)
    val preferences: Call<String>

    @FormUrlEncoded
    @POST(API.API_SUBMIT_QUERY)
    fun submitQuery(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.TITLE) title: String,
        @Field(Const.DESCRIPTION) description: String,
        @Field(Const.CATEGORY) category: String,
        @Field(Const.FILE) file: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun getFollowingList(
        @Url url: String,
        @Field(Const.USER_ID) userId: String,
        @Field(Const.LAST_FOLLOW_ID) lastFollowId: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun getFollowingList(@Url url: String, @Field(Const.USER_ID) userId: String): Call<String>

    @FormUrlEncoded
    @POST
    fun getMentorList(@Url url: String, @Field(Const.USER_ID) userId: String): Call<String>

    @FormUrlEncoded
    @POST
    fun getMmeExpertList(@Url url: String, @Field(Const.USER_ID) userId: String): Call<String>

    @FormUrlEncoded
    @POST
    fun getUserListBasedCategory(
        @Url url: String, @Field(Const.USER_ID) userId: String, @Field(
            Const.USER_TYPE
        ) userType: String, @Field(Const.LAST_ID) lastId: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun getFollowersList(
        @Url url: String,
        @Field(Const.USER_ID) userId: String,
        @Field(Const.LAST_FOLLOW_ID) LAST_FOLLOW_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_FOLLOW)
    fun follow(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.FOLLOWER_ID) followerId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_COMMENT)
    fun editComment(
        @Field(Const.ID) id: String,
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String,
        @Field(Const.POST_COMMENT) postComment: String,
        @Field(Const.TAG_PEOPLE) tagPeople: String,
        @Field(Const.REMOVE_TAG_PEOPLE) removeTagpeople: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DELETE_COMMENT)
    fun deleteComment(
        @Field(Const.ID) id: String,
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_CHANGE_NOTIFICATION_STATE)
    fun deleteComment(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.ID) id: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_UNFOLLOW)
    fun unFollow(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.FOLLOWER_ID) followerId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_MAKE_AN_EXPERT)
    fun makeAnExpert(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.USER_FOR) userFor: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_REMOVE_EXPERT)
    fun removeExpert(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.USER_FOR) userFor: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_UPDATE_PROFILE_PICTURE)
    fun updateProfilePicture(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.URL) url: String,
        @Field(Const.VIA) via: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_SINGLE_COMMENT_DATA)
    fun singleCommentData(@Field(Const.COMMENT_ID) commentId: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_COMMENT)
    fun addComment(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String,
        @Field(Const.POST_COMMENT) postComment: String,
        @Field(Const.TAG_PEOPLE) tagPeople: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ADD_COMMENT)
    fun addComment(
        @Field(Const.PARENT_ID) parentId: String,
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String,
        @Field(Const.POST_COMMENT) postComment: String,
        @Field(Const.TAG_PEOPLE) tagPeople: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_COMMENT_LIST)
    fun getCommentList(
        @Field(Const.POST_ID) postId: String,
        @Field(Const.LAST_COMMENT_ID) lastCommentId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_COMMENT_LIST)
    fun getCommentList(
        @Field(Const.POST_ID) postId: String,
        @Field(Const.PARENT_ID) parentId: String,
        @Field(Const.LAST_COMMENT_ID) lastCommentId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_UPDATE_DAMS_TOKEN)
    fun updateDamsToken(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.DAMS_USERNAME) DAMS_USERNAME: String,
        @Field(Const.DAMS_PASSWORD) DAMS_PASSWORD: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun followingList(
        @Url url: String,
        @Field(Const.USER_ID) USER_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_POST_MCQ)
    fun postMcq(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.QUESTION) QUESTION: String,
        @Field(Const.ANSWER_ONE) ANSWER_ONE: String,
        @Field(Const.ANSWER_TWO) ANSWER_TWO: String,
        @Field(Const.ANSWER_THREE) ANSWER_THREE: String,
        @Field(Const.ANSWER_FOUR) ANSWER_FOUR: String,
        @Field(Const.ANSWER_FIVE) ANSWER_FIVE: String,
        @Field(Const.RIGHT_ANSWER) RIGHT_ANSWER: String,
        @Field(Const.POST_TAG) POST_TAG: String,
        @Field(Const.TAG_PEOPLE) TAG_PEOPLE: String,
        @Field(Const.FILE) FILE: String,
        @Field(Const.SUBJECT_ID) SUBJECT_ID: String,
        @Field(Const.SUB_CAT_ID) SUB_CAT_ID: String,
        @Field(Const.LAT) LAT: String,
        @Field(Const.LON) LON: String,
        @Field(Const.LOCATION) LOCATION: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_MCQ_POST)
    fun editMcqPost(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.POST_ID) POST_ID: String,
        @Field(Const.QUESTION) QUESTION: String,
        @Field(Const.ANSWER_ONE) ANSWER_ONE: String,
        @Field(Const.ANSWER_TWO) ANSWER_TWO: String,
        @Field(Const.ANSWER_THREE) ANSWER_THREE: String,
        @Field(Const.ANSWER_FOUR) ANSWER_FOUR: String,
        @Field(Const.ANSWER_FIVE) ANSWER_FIVE: String,
        @Field(Const.RIGHT_ANSWER) RIGHT_ANSWER: String,
        @Field(Const.POST_TAG) POST_TAG: String,
        @Field(Const.TAG_PEOPLE) TAG_PEOPLE: String,
        @Field(Const.REMOVE_TAG_PEOPLE) REMOVE_TAG_PEOPLE: String,
        @Field(Const.FILE) FILE: String,
        @Field(Const.DELETE_META) DELETE_META: String,
        @Field(Const.SUBJECT_ID) SUBJECT_ID: String,
        @Field(Const.SUB_CAT_ID) SUB_CAT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_POST_NORMAL_VIDEO)
    fun postNormalVideo(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.TEXT) TEXT: String,
        @Field(Const.POST_TYPE) POST_TYPE: String,
        @Field(Const.POST_TAG) POST_TAG: String,
        @Field(Const.TAG_PEOPLE) TAG_PEOPLE: String,
        @Field(Const.FILE) FILE: String,
        @Field(Const.SUB_CAT_ID) SUB_CAT_ID: String,
        @Field(Const.LAT) LAT: String,
        @Field(Const.LON) LON: String,
        @Field(Const.LOCATION) LOCATION: String,
        @Field(Const.SUBJECT_ID) SUBJECT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_NORMAL_POST)
    fun editNormalPost(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.POST_ID) POST_ID: String,
        @Field(Const.TEXT) TEXT: String,
        @Field(Const.POST_TYPE) POST_TYPE: String,
        @Field(Const.POST_TAG) POST_TAG: String,
        @Field(Const.TAG_PEOPLE) TAG_PEOPLE: String,
        @Field(Const.REMOVE_TAG_PEOPLE) REMOVE_TAG_PEOPLE: String,
        @Field(Const.FILE) FILE: String,
        @Field(Const.DELETE_META) DELETE_META: String,
        @Field(Const.SUB_CAT_ID) SUB_CAT_ID: String,
        @Field(Const.SUBJECT_ID) SUBJECT_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_MENTOR_DETAIL)
    fun getMentorDetail(@Field(Const.USER_ID) USER_ID: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_SUGGESTED_POST)
    fun suggestedPost(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.STRING_TO_MATCH) POST_ID: String,
        @Field(Const.CATEGORY_ID) CATEGORY_ID: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_USER_NOTIFICATIONS)
    fun getUserNotification(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.LAST_ACTIVITY_ID) last_activity_id: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_REWARD_TRANSACTION)
    fun getRewardTransaction(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.LAST_ID) lastId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ALL_NOTIFICATION_READ)
    fun getAllNotificationRead(@Field(Const.USER_ID) userId: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_SINGLE_POST_FOR_USER)
    fun getSinglePostForUser(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @get:GET(API.API_TOPPER_DESK)
    val topperDesk: Call<String>

    @FormUrlEncoded
    @POST(API.API_LIKE_POST)
    fun likePost(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DISLIKE_POST)
    fun disLikePost(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_REPORT_POST)
    fun reportPost(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String,
        @Field(Const.REASON_ID) REASON_ID: String,
        @Field(Const.COMMENT) COMMENT: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DELETE_POST)
    fun deletePost(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @get:GET(API.API_GET_REPORT_ABUSE_LIST)
    val reportAbuseList: Call<String>

    @FormUrlEncoded
    @POST(API.API_SET_POST_AS_UNPINNED)
    fun setPostAsUnpinned(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_SET_POST_AS_PINNED)
    fun setPostAsPinned(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_HIDE_POST)
    fun hidePost(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.POST_ID) postId: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_SUBMIT_ANSWER_POST_MCQ)
    fun submitAnswerPostMcq(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.MCQ_ID) MCQ_ID: String,
        @Field(Const.ANSWER) ANSWER: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_MY_TRANSACTIONS)
    fun getMyTransactions(@Field(Const.PAGE) PAGE: Int): Call<String>

    @get:GET(API.API_MY_TRANSACTIONS)
    val myTransactions: Call<String>

    @POST(API.API_HELP_AND_SUPPORT)
    fun sendHelpSupportFeedback(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_HELP_AND_SUPPORT)
    Call<String> sendHelpSupportFeedback(*/
    /*@Field(Const.USER_ID) String userId,*/ /*
                                         @Field(Const.COMMENT_TYPE) String commentType,
                                         @Field(Const.COMMENT_MSG) String commentMsg);*/
    /*@FormUrlEncoded
    @POST(API.API_ADD_MY_NOTES)
    Call<String> addMyNotesData(@Field(Const.USER_ID) String userId,
                                         @Field(Const.NOTE_DATA) String notes,
                                         @Field(Const.DOSE_TYPE) String doseId,
                                         @Field(Const.ARTICLE_ID) String articleId);*/
    @POST(API.API_ADD_MY_NOTES)
    fun addMyNotesData(@Body data: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_GET_MY_NOTES)
    fun getMyNotesData(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.LAST_POST_ID) last_post_id: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_DELETE_MY_NOTES)
    fun deleteMyNotesData(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.NOTE_ID) notes_id: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_EDIT_MY_NOTES)
    fun editMyNotesData(
        @Field(Const.USER_ID) userId: String,
        @Field(Const.NOTE_DATA) notes: String,
        @Field(Const.NOTE_ID) notes_id: String
    ): Call<String>

    @FormUrlEncoded
    @POST(API.API_ALL_DLP)
    fun getAllDLPData(@Field(Const.LAST_POST_ID) last_post_id: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_DLP_CETEGORIES_DATA)
    fun getDLPCategoryData(@Field(Const.PARENT_ID) parent_id: String): Call<String>

    @FormUrlEncoded
    @POST(API.API_BOOKMARK)
    fun API_USER_BOOKMARK(
        @Field(Const.USER_ID) USER_ID: String,
        @Field(Const.ID) ID: String,
        @Field(Const.VIDEO_ID) VIDEO_ID: String,
        @Field(Const.TIME) TIME: String,
        @Field(Const.INFO) INFO: String,
        @Field("for") STATE: String
    ): Call<String>

    @POST(API.API_UPDATE_VIDEO_VIEW)
    fun API_UPDATE_VIDEO_VIEW(@Body data: String): Call<String>

    /*@FormUrlEncoded
    @POST(API.API_UPDATE_VIDEO_VIEW)
    Call<String> API_UPDATE_VIDEO_VIEW(
            @Field(Const.USER_ID) String USER_ID,
            @Field(Const.TYPE) String type,
            @Field(Const.COURSE_ID) String COURSE_ID,
            @Field(Const.VIDEO_ID) String VIDEO_ID,
            @Field(Const.TOTAL_TIME) String TOTAL_TIME,
            @Field(Const.VIEW_TIME) String VIEW_TIME,
            @Field(Const.TILE_ID) String TILE_ID);*/
    @FormUrlEncoded
    @POST(API.API_GENERATE_ACTIVITY)
    fun API_GENERATE_ACTIVITY(
        @Field(Const.LEAD_ID) LEAD_ID: String,
        @Field(Const.LEAD_NOTE) LEAD_NOTE: String,
        @Field(Const.LEAD_TYPE) LEAD_TYPE: String
    ): Call<String>

    @GET(API.API_GENERATE_LEAD)
    fun API_GENERATE_LEAD(): Call<String>

    @get:GET(API.API_GET_POPUP_DATA)
    val popupData: Call<String>

    @POST(API.get_my_courses)
    fun get_my_courses(@Body data: String): Call<String>

    @POST(API.fetch_user)
    fun fetch_user(@Body data: String): Call<String>

    @POST(API.transfer_course)
    fun transfer_course(@Body data: String): Call<String>

    @POST(API.update_profile)
    fun updateprofile(@Body data: String): Call<String>

    @POST(API.get_my_orders)
    fun get_my_orders(@Body data: String): Call<String>

    @POST(API.CourseDetail_JS)
    fun getCourseData(@Body data: String): Call<String>

    @POST(API.API_GET_COUPON)
    fun API_GET_COUPON(@Body data: String): Call<String>

    @POST(API.IS_COUPON_AVAILABLE)
    fun IS_COUPON_AVAILABLE(@Body data: String): Call<String>

    @POST(API.loginAuthenticationWithOtp)
    fun userLoginAuthenticationWithOtp(@Body data: String): Call<String>

    @POST(API.changePassword)
    fun changePassword(@Body data: String): Call<String>

    @POST(API.markAttendence)
    fun markAttendence(@Body data: String): Call<String>

}