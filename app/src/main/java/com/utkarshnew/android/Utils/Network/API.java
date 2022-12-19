package com.utkarshnew.android.Utils.Network;

import com.utkarshnew.android.Utils.SharedPreference;

import java.util.Calendar;

/**
 * Created by Cbc-03 on 08/10/16.
 */

public interface API {


    //------------------------------development/testing path for DEV SERVER-------------------------------------------------


      /*  String BASE_URL = "https://staging.utkarshapp.com/";
    String API_AMAZON_S3_BUCKET_NAME_JSON_IMAGE = "ut-prod-test-meta/result_json/";
    String CONGNITO_POOL_ID = "ap-south-1:d82ba936-abab-4a10-b2ff-557de37ef885";*/
    String BASE_URL = "https://application.utkarshapp.com/";
    String API_AMAZON_S3_BUCKET_NAME_JSON_IMAGE = "ut-production-test/result_json_prod/";
    String CONGNITO_POOL_ID = "ap-south-1:49571264-db17-4339-af0f-1556167a834a";
  /*  String BASE_URL = "https://erp.utkarshapp.com/";
    String API_AMAZON_S3_BUCKET_NAME_JSON_IMAGE = "ut-production-test/result_json_prod/";
    String CONGNITO_POOL_ID = "ap-south-1:49571264-db17-4339-af0f-1556167a834a";*/

    //


//    String BASE_URL = "https://application.utkarshapp.com/";

    /* */ /*String BASE_URL = "https://erp.utkarshapp.com/";
    String API_AMAZON_S3_BUCKET_NAME_JSON_IMAGE = "ut-production-test/result_json_prod/";
    String CONGNITO_POOL_ID = "ap-south-1:49571264-db17-4339-af0f-1556167a834a";*/
    ///RAZORPAY keys
    String live_key_id_razor = "rzp_live_vLkWUZkSGpABUz";

    String BASE_URL_WEB = "https://online.utkarsh.com/";

    // String BASE_URL_WEB = "https://staging-web.utkarshapp.com/";

/*
   String APITOKEN = "1291986318644515";
   String Bearer = "Bearer 129#Nerglnw3@@285jfhf*D7s@@";
*/


/*
    String APITOKEN = "1301886318644515";
    String Bearer = "Bearer 130#Nerglnw3@@285jfhf*Dm'g@";
*/

/*
   String APITOKEN = "1321834318644515";
   String Bearer = "Bearer 132#Nergl3f34c285jfhf*Dm'g@";*/
 /*  String APITOKEN = "1331886524644515";
   String Bearer = "Bearer 133#Nerglnw3@@43fhssf*Dm'g@";*/

/*   String APITOKEN = "1351886345674515";
   String Bearer = "Bearer 135#Nerglnw3@@285jfh3wdneg@"; */


/*    String APITOKEN = "1371886318644632";
    String Bearer = "Bearer 137#Nerglwcres345tgde456ygd";*/


   /* String APITOKEN = "1401886584224515";
    String Bearer = "Bearer 140#345thvcde456ui87yhjk76t";*/

//    String APITOKEN = "1447555347534232";
    String APITOKEN = "1457856534423316";
//    String Bearer = "Bearer 144#scsdgfdd3r32cwegvvhjk76t";
    String Bearer = "Bearer 145#zxcsdghtr3tvdstfeddjk76t";


/*
    String APITOKEN = "1421257653443234";
    String Bearer = "Bearer 142#345thvcdedsfcehdrhjk76t";
*/

//


    String API_AMAZON_S3_BUCKET_NAME_PROFILE_IMAGES = "ut-production-app/utkarsh_profile_images";
    String API_AMAZON_S3_BUCKET_NAME_FANWALL_IMAGES = "ut-production-app/utkarsh_fanwall_images";
    String API_AMAZON_S3_BUCKET_NAME_VIDEO_IMAGES = "ut-production-app/video_thumbnails";
    String API_AMAZON_S3_BUCKET_NAME_DOCUMENT = "ut-production-app/utkarsh_doc_folder";
    String API_AMAZON_S3_BUCKET_NAME_FEEDBACK = "ut-production-app/feedback_images";
    String API_AMAZON_S3_FILE_NAME_CREATION = SharedPreference.getInstance().getLoggedInUser().getId() + "sample_" + Calendar.getInstance().getTimeInMillis();
    String API_AMAZON_S3_BUCKET_NAME_USER_PROFILE = "ut-production-app/application/profile/";
    String API_AMAZON_S3_BUCKET_NAME_CHAT_IMAGE = "ut-production-app/application/chat_system/";


    String MAIN_SERVER_URL = BASE_URL + "index.php/";
    String MAIN_SERVER_URL1 = BASE_URL + "index.php/";


    String SERVER_URL = "";


    String API_SEND_OTP_VERIFICATION = MAIN_SERVER_URL + "data_model/users/send_verification_otp";
    String API_CITY = MAIN_SERVER_URL + "data_model/master_hit/get_cities";
    String API_STATE = MAIN_SERVER_URL + "data_model/master_hit/get_states";


    String API_UPDATE_PASSWORD_WITH_OTP = MAIN_SERVER_URL + "data_model/users/update_password";

    String API_UPDATE_MOBILE_NUMBER = MAIN_SERVER_URL + "data_model/user/User_mobile_change/update_mobile_number";

    String API_REGISTER_USER = MAIN_SERVER_URL + "data_model/users/registration";

    //String API_REGISTER_NIMBUS_USER = MAIN_SERVER_URL_NIMBUS + "data_model/user/registration";

    String API_USER_DAMS_VERIFICATION = MAIN_SERVER_URL + "data_model/user/dams_user_verification/user_verification";

    String API_USER_PROFILE_WITH_TOKEN = MAIN_SERVER_URL + "data_model/user/user_profile/user_profile_with_token";

    String API_USER_LOGOUT_WITH_TOKEN = MAIN_SERVER_URL + "data_model/user/user_profile/user_logout";

    String API_USER_LOGIN_AUTHENTICATION = MAIN_SERVER_URL + "data_model/users/login_auth";

    ///UTKASH API BY PRIYANSHU///
    String master_content = MAIN_SERVER_URL + "data_model/master_hit/content";
    String get_courses = MAIN_SERVER_URL + "data_model/course/get_courses";
    String get_my_profile = MAIN_SERVER_URL + "data_model/users/get_my_profile";

    String API_COURSE_SUBJECT_DATA = MAIN_SERVER_URL + "data_model/courses/course/get_subject_by_category";

    String API_POST_MCQ = MAIN_SERVER_URL + "data_model/user/post_mcq/add_mcq";

    String API_POST_NORMAL_VIDEO = MAIN_SERVER_URL + "data_model/user/post_text/add_post";

    String API_GET_FEEDS_FOR_USER = MAIN_SERVER_URL + "data_model/fanwall/fan_wall/get_fan_wall_for_user";

    String API_GET_MASTER_HIT = MAIN_SERVER_URL + "data_model/fanwall/master_hit/content";

    String API_LIKE_POST = MAIN_SERVER_URL + "data_model/user/post_like/like_post";

    String API_DISLIKE_POST = MAIN_SERVER_URL + "data_model/user/post_like/dislike_post";

    String API_REPORT_POST = MAIN_SERVER_URL + "data_model/user/Post_report_abuse/report";

    String API_DELETE_POST = MAIN_SERVER_URL + "data_model/user/Post_delete/delete_post";

    String API_SHARE_POST = MAIN_SERVER_URL + "data_model/user/post_share/share_post";

    String API_ADD_QUES_ANS_BOOKMARK = MAIN_SERVER_URL + "data_model/courses/test_series/bookmark_question";

    String API_ADD_BOOKMARK = MAIN_SERVER_URL + "data_model/user/post_bookmarks/add_to_bookmarks";

    String API_REMOVE_BOOKMARK = MAIN_SERVER_URL + "data_model/user/post_bookmarks/remove_from_bookmarks";

    String API_ADD_COMMENT = MAIN_SERVER_URL + "data_model/user/post_comment/add_comment";

    String API_EDIT_COMMENT = MAIN_SERVER_URL + "data_model/user/post_comment/update_comment";

    String API_DELETE_COMMENT = MAIN_SERVER_URL + "data_model/user/post_comment/delete_comment";

    String API_GET_COMMENT_LIST = MAIN_SERVER_URL + "data_model/user/post_comment/get_post_comment";

    String API_GET_MASTER_REGISTRATION_HIT = MAIN_SERVER_URL + "data_model/user/registration/get_all_category_db";

    String API_GET_COURSE_LIST_ZERO_LEVEL = MAIN_SERVER_URL + "data_model/user/User_category_handling/get_category_basic";

    String API_GET_COURSE_LIST_FIRST_LEVEL = MAIN_SERVER_URL + "data_model/user/User_category_handling/get_category_basic_level_one";

    String API_GET_COURSE_LIST_SECOND_LEVEL = MAIN_SERVER_URL + "data_model/user/User_category_handling/get_category_basic_level_two";

    String API_GET_COURSE_INTERESTED_IN = MAIN_SERVER_URL + "data_model/user/User_category_handling/get_intersted_courses";

    String API_STREAM_REGISTRATION = MAIN_SERVER_URL + "data_model/user/registration/stream_registration";

    String API_GET_USER = MAIN_SERVER_URL + "data_model/user/Registration/get_active_user/";

    String API_FOLLOW = MAIN_SERVER_URL + "data_model/user/user_follow/follow_user";

    String API_UNFOLLOW = MAIN_SERVER_URL + "data_model/user/user_follow/unfollow_user";

    String API_MAKE_AN_EXPERT = MAIN_SERVER_URL + "data_model/user/user_expert_control/make_user_expert";

    String API_REMOVE_EXPERT = MAIN_SERVER_URL + "data_model/user/user_expert_control/remove_user_expert";

    // String API_SUBMIT_QUERY = MAIN_SERVER_URL + "data_model/user/user_queries/submit_query";

    String API_FOLLOWING_LIST = MAIN_SERVER_URL + "data_model/user/user_follow/following_list";

    String API_FOLLOWERS_LIST = MAIN_SERVER_URL + "data_model/user/user_follow/followers_list";

    String API_SINGLE_POST_FOR_USER = MAIN_SERVER_URL + "data_model/fanwall/fan_wall/get_single_post_data_for_user";

    String API_GET_TAG_LISTS = MAIN_SERVER_URL + "data_model/user/post_meta_tags/get_list_tags/";

//    String API_GET_USER_NOTIFICATIONS = MAIN_SERVER_URL + "data_model/notification_genrator/activity_logger/get_user_activity";

    String API_GET_USER_NOTIFICATIONS = MAIN_SERVER_URL + "data_model/notification_genrator/activity_logger/get_user_activities";

    String API_CHANGE_NOTIFICATION_STATE = MAIN_SERVER_URL + "data_model/notification_genrator/activity_logger/make_activity_viewed";

    String API_GET_LIVE_STREAM = MAIN_SERVER_URL + "data_model/fanwall/live_stream/top_video_stream";

    String API_GET_APP_VERSION = MAIN_SERVER_URL + "data_model/version/get_version";

    //String API_GET_APP_VERSION = MAIN_SERVER_URL + "";

    String API_GET_NOTIFICATION_COUNT = MAIN_SERVER_URL + "data_model/notification_genrator/activity_logger/all_notification_counter";

    String API_UPDATE_DEVICE_TOKEN = MAIN_SERVER_URL + "data_model/user/registration/update_device_info";

    String API_UPDATE_DAMS_TOKEN = MAIN_SERVER_URL + "data_model/user/registration/update_dams_id_user";

    String API_REQUEST_VIDEO_LINK = MAIN_SERVER_URL + "data_model/fanwall/fan_wall/on_request_create_video_link";

    String API_REQUEST_TEST_DATA = MAIN_SERVER_URL + "data_model/courses/exam/get_video_study_meta";

    String API_REQUEST_VIDEO_LINK_V2 = MAIN_SERVER_URL + "data_model/fanwall/fan_wall/on_request_create_video_link_v2";

    String API_EDIT_MCQ_POST = MAIN_SERVER_URL + "data_model/user/post_mcq/edit_mcq";

    String API_EDIT_NORMAL_POST = MAIN_SERVER_URL + "data_model/user/post_text/edit_post";

    String API_ALL_NOTIFICATION_READ = MAIN_SERVER_URL + "data_model/notification_genrator/Activity_logger/set_all_read";

    String API_LIKES_COUNT_LIST = MAIN_SERVER_URL + "data_model/user/post_like/get_post_like_users";

    String API_SINGLE_COMMENT_DATA = MAIN_SERVER_URL + "data_model/user/post_comment/get_single_comment_data";

    String API_UPDATE_BANNER_HIT_COUNT = MAIN_SERVER_URL + "data_model/fanwall/fan_wall_banner/update_banner_hit_count";

    String API_SUBMIT_ANSWER_POST_MCQ = MAIN_SERVER_URL + "data_model/user/post_mcq/answer_post_mcq";

    String SAVE_EXAM_PREFERENCE = MAIN_SERVER_URL + "data_model/user/registration/update_user_prefrences";

    String GENERATE_HASH = MAIN_SERVER_URL + "data_model/Transaction/hash_generate";

    String PRIVACY_POLICY = MAIN_SERVER_URL + "web_panel/home/privacy_app";
    String TERMS_AND_CONDITIONS = "https://online.utkarsh.com/terms-conditions";
    String privacy_policy = "https://online.utkarsh.com/policy";

    /*###########################################################
        ######  #######  #     #  ######  ######  ######  ######
        #       #     #  #     #  #    #  #       #       #
        #       #     #  #     #  ######  ######  ######  ######
        #       #     #  #     #  # #          #  #            #
        ######	#######  #######  #   #   ######  ######  ######
    ########################################################### */


    String API_GET_LANDING_PAGE_DATA = MAIN_SERVER_URL + "data_model/courses/course/get_landing_page_data";

    String API_GET_LANDING_PAGE_DATA_EXAM = MAIN_SERVER_URL + "data_model/courses/course/get_landing_page_data_exam";

    String API_SINGLE_COURSE_INFO_RAW = MAIN_SERVER_URL + "data_model/courses/course/get_single_course_info_raw";

    String API_GET_ALL_CATEGORY_DATA = MAIN_SERVER_URL + "data_model/courses/course/get_all_category_data";

    String API_GET_ALL_CATEGORY_DATA_EXAM = MAIN_SERVER_URL + "data_model/courses/course/get_all_category_data_exam";

    String API_GET_MY_COURSE_DATA = MAIN_SERVER_URL + "data_model/courses/my_courses/get_list_of_my_courses";

    String API_GET_INSTRUCTOR_DATA = MAIN_SERVER_URL + "data_model/courses/Instructor/get_instructor_profile";

    String API_GET_FILE_LIST_CURRICULUM = MAIN_SERVER_URL + "data_model/courses/Course/get_all_file_info";

    String API_GET_FAQ_DATA = MAIN_SERVER_URL + "data_model/courses/course/get_faq";

    //String API_APPLY_COUPON_CODE = MAIN_SERVER_URL + "data_model/courses/course/apply_coupon_code";
    String API_APPLY_COUPON_CODE = MAIN_SERVER_URL + "data_model/payment/apply_coupon";

    String API_SET_POST_AS_PINNED = MAIN_SERVER_URL + "data_model/user/post_pinning/pin_a_post";

    String API_SET_POST_AS_UNPINNED = MAIN_SERVER_URL + "data_model/user/Post_pinning/pin_a_post_remove";

    String API_SEARCH_COURSE = MAIN_SERVER_URL + "data_model/courses/course/search_course";

    String API_SEARCH_COURSE_EXAM = MAIN_SERVER_URL + "data_model/courses/course/search_course_exam";

    String API_CART_COURSE_EXAM = MAIN_SERVER_URL + "data_model/courses/course/get_multi_courses_by_id";

    String API_GET_SOLUTION_TESTSERIES = API.MAIN_SERVER_URL + "data_model/courses/Test_series/get_test_series_result";

    String API_GET_MENTOR_LIST = API.MAIN_SERVER_URL + "data_model/user/user_follow/mme_mentor_list";

    String API_GET_MME_EXPERT_LIST = API.MAIN_SERVER_URL + "data_model/user/user_follow/mme_expert_list";

    String API_GET_BASIC_COURSE = API.MAIN_SERVER_URL + "data_model/courses/exam/get_basic_data";

    String API_GET_COURSE_DETAIL = API.MAIN_SERVER_URL + "data_model/course/get_course_detail";

    String API_GET_MASTER_DATA = API.MAIN_SERVER_URL + "data_model/course/get_master_data";
    /*String API_GET_TEST_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/courses/exam/get_test_data";
    String API_GET_VIDEO_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/courses/exam/get_video_data";
    String API_GET_CONCEPT_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/courses/exam/get_concept";
    String API_GET_MAGAZINE_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/courses/exam/get_pdf_data";
    String API_GET_FREE_MAGAZINE_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/courses/exam/get_free_pdf_data";
    String API_GET_PRACTICE_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/courses/exam/get_practice";*/

    String API_VIDEO_FEEDBACK = API.MAIN_SERVER_URL + "data_model/courses/exam/video_feedback";

    String API_DAILY_DOSE = API.MAIN_SERVER_URL + "data_model/fanwall/master_hit/daily_dose_menu";

    String get_my_courses = MAIN_SERVER_URL + "data_model/course/get_my_courses";


    String fetch_user = MAIN_SERVER_URL + "data_model/users/search_user";
    String transfer_course = MAIN_SERVER_URL + "data_model/payment/transfer_course";





    /*################################################

        Videos

    ################################################ */


    String API_GET_VIDEO_COMMENT = MAIN_SERVER_URL + "data_model/video/video_comment/get_video_comment";
    //End Videos

    String API_ADD_REVIEW_COURSE = MAIN_SERVER_URL + "data_model/courses/course_rating_reviews/add_review_to_course";

    String API_GET_INSTRUCTOR_REVIEW_LIST = MAIN_SERVER_URL + "data_model/courses/course_instructor_reviews/get_list_of_reviews";

    String API_GET_COURSE_REVIEW_LIST = MAIN_SERVER_URL + "data_model/courses/course_rating_reviews/get_list_of_reviews";

    //String API_INITIALIZE_COURSE_PAYMENT = MAIN_SERVER_URL + "data_model/courses/course/initialize_course_transaction";
    String API_INITIALIZE_COURSE_PAYMENT = MAIN_SERVER_URL + "data_model/payment/initialize_transaction";

    String API_ADD_UPDATE_ADDRESS = MAIN_SERVER_URL + "data_model/user/registration/add_update_address";

    //String API_MAKE_FREE_COURSE_TRANSACTION = MAIN_SERVER_URL + "data_model/courses/course/free_course_transaction";
    String API_MAKE_FREE_COURSE_TRANSACTION = MAIN_SERVER_URL + "data_model/payment/free_transaction";

    //String API_COMPLETE_COURSE_PAYMENT = MAIN_SERVER_URL + "data_model/courses/course/complete_course_transaction";
    String API_COMPLETE_COURSE_PAYMENT = MAIN_SERVER_URL + "data_model/payment/complete_transaction";

    String API_CCAVANUE_GET_RSA = MAIN_SERVER_URL + "data_model/payment_request/get_rsa";

    String API_CCAVANUE_REDIRECT_URL = MAIN_SERVER_URL + "data_model/payment_request/process_status";

    String API_DELETE_USER_COURSE_REVIEWS = MAIN_SERVER_URL + "data_model/courses/course_rating_reviews/delete_review_from_course";

    String API_EDIT_USER_COURSE_REVIEWS = MAIN_SERVER_URL + "data_model/courses/course_rating_reviews/edit_review_to_course";

    String API_GET_COMPLETE_INFO_TEST_SERIES = MAIN_SERVER_URL + "data_model/courses/test_series/get_test_series_with_id?pro_data=1";
    //String API_GET_COMPLETE_INFO_TEST_SERIES = MAIN_SERVER_URL + "data_model/courses/test_series/get_test_series_with_id_app";

    String API_COMPLETE_INFO_TESTSERIES = MAIN_SERVER_URL + "data_model/courses/Test_series/save_test";

    String API_GET_USER_GIVEN_TESTSERIES = MAIN_SERVER_URL + "data_model/courses/Test_series/get_user_given_test_series";

    String API_GET_USER_LEADERBOARD_RESULT = MAIN_SERVER_URL + "data_model/courses/Test_series/get_test_series_basic_result";

    String API_GET_USER_RESULT = MAIN_SERVER_URL + "data_model/courses/test_series/top_list";

    String API_ADD_INSTRUCTOR_REVIEW_COURSE = MAIN_SERVER_URL + "data_model/courses/course_instructor_reviews/add_review_to_instructor";

    String API_EDIT_USER_INSTRUCTOR_REVIEWS = MAIN_SERVER_URL + "data_model/courses/course_instructor_reviews/edit_review_to_instructor";

    String API_DELETE_USER_INSTRUCTOR_REVIEWS = MAIN_SERVER_URL + "data_model/courses/course_instructor_reviews/delete_review_from_instructor";

    String API_GET_REWARD_POINTS = MAIN_SERVER_URL + "data_model/user/user_reward/get_user_rewards";

    //    String API_GET_CHECKSUM_FOR_PAYTM = "http://emedicoz.com/production_dams/Paytm/generateChecksum.php?SERVER=%s";
    String API_GET_CHECKSUM_FOR_PAYTM = BASE_URL + "Paytm/generateChecksum.php?SERVER=%s";

    // Staging Paytm Trn API
    // String API_FINAL_TRANSACTION_FOR_PAYTM = "http://emedicoz.com/Paytm/TxnStatus.php?MID=%s&ORDERID=%s&SERVER=%s";
    String API_FINAL_TRANSACTION_FOR_PAYTM = BASE_URL + "Paytm/TxnStatus.php?MID=%s&ORDERID=%s&SERVER=%s";

    String API_GET_REPORT_ABUSE_LIST = API.MAIN_SERVER_URL + "data_model/user/Post_report_abuse/get_all_report_reasons";


    /*################################################

        Videos

    ################################################*/
    String API_GET_SINGLE_VIDEO_DATA = MAIN_SERVER_URL + "data_model/video/Video_channel/get_single_video_data";

    String API_GET_SINGLE_VIDEO_DATA2 = "data_model/video/Video_channel/get_single_video_data";
    String API_GET_SINGLE_CAT_VIDEO_DATA = MAIN_SERVER_URL + "data_model/video/Video_channel/get_videos_for_tag_list";

    String API_GET_POST_COMMENT = MAIN_SERVER_URL + "data_model/video/video_comment/get_video_comment";
    String API_ADD_VIDEO_COMMENT = MAIN_SERVER_URL + "data_model/video/video_comment/add_comment";
    String API_LIKE_VIDEO = MAIN_SERVER_URL + "data_model/video/Video_like/like_video";
    String API_DISLIKE_VIDEO = MAIN_SERVER_URL + "data_model/video/Video_like/dislike_video";
    String API_ADD_VIEW_VIDEO = MAIN_SERVER_URL + "data_model/video/Video_channel/add_video_counter";

    String API_DELETE_VIDEO_COMMENT = MAIN_SERVER_URL + "data_model/video/video_comment/delete_comment";
    String API_EDIT_VIDEO_COMMENT = MAIN_SERVER_URL + "data_model/video/video_comment/update_comment";

    String API_ADD_FAV = MAIN_SERVER_URL + "data_model/video/Video_channel/add_favourite_video";
    String API_REMOVE_FAV = MAIN_SERVER_URL + "data_model/video/Video_channel/remove_favourite_video";
    String API_GET_FAV_VIDEOS = MAIN_SERVER_URL + "data_model/video/Video_channel/get_favourite_video_list";

    //NOTIFICATION SETTINGS
    String API_GET_NOTIFICATION_SETTING = MAIN_SERVER_URL + "data_model/user/registration/user_notification_settings";
    String API_EDIT_NOTIFICATION_SETTING = MAIN_SERVER_URL + "data_model/user/registration/edit_user_notification";
    String API_EDIT_ALLOW_TAGGING = MAIN_SERVER_URL + "data_model/user/registration/allow_tagging";

    String API_GET_REWARD_TRANSACTION = MAIN_SERVER_URL + "data_model/user/user_reward/get_reward_transaction";

    String API_STUDY_SLIDER_IMAGES = MAIN_SERVER_URL + "data_model/slider/get_slider";
    String MAKE_LEARNER = MAIN_SERVER_URL + "data_model/courses/course/complete_element";
    String API_COURSE_META_ALL = MAIN_SERVER_URL + "data_model/courses/course/get_course_meta";
    String API_GET_PRACTICE_STATUS = MAIN_SERVER_URL + "data_model/courses/Test_series/get_practice_status";

    String API_HIDE_POST = MAIN_SERVER_URL + "data_model/user/Post_hide/hide_post";
    String API_UPDATE_PROFILE_PICTURE = MAIN_SERVER_URL + "data_model/user/Profile_picture/update_profile_picture";
    String API_GET_PREFERENCES = MAIN_SERVER_URL + "data_model/user/registration/get_prefrences";
    String API_GET_MENTOR_DETAIL = MAIN_SERVER_URL + "data_model/user/registration/set_mentor";
    String API_SUGGESTED_POST = MAIN_SERVER_URL + "data_model/fanwall/Fan_wall/suggested_post";

    String API_MOBILE_MENU = MAIN_SERVER_URL + "data_model/mobile_menu/index";


    String GET_NEW_CONTACT_US = API.MAIN_SERVER_URL + "data_model/fanwall/master_hit/get_app_contact_us";


    String API_MOBILE_BANNER = MAIN_SERVER_URL + "data_model/fanwall/fan_wall_banner/get_fan_wall_banner";
    String API_TOPPER_DESK = MAIN_SERVER_URL + "data_model/toppers_desk";
    String API_MY_TRANSACTIONS = MAIN_SERVER_URL + "data_model/courses/my_courses/my_transactions";

    String API_DELETE_USER_ACCOUNT = MAIN_SERVER_URL + "data_model/user/user_profile/delete_profile_request";

    String API_GET_TOPIC_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/hierarchy/get_topic_subtopic";

    String API_GET_UNIT_DATA_FIRST = API.MAIN_SERVER_URL + "data_model/hierarchy/get_unit_chapter";
    String API_DOUBTS_PLAN_POST = MAIN_SERVER_URL + "data_model/doubt_plan/get_plans";

    String API_SEND_ROLL_NO_POST = MAIN_SERVER_URL + "data_model/courses/exam/submit_roll_no";
    String API_HELP_AND_SUPPORT = MAIN_SERVER_URL + "data_model/courses/exam/help_support_data";
    String API_ADD_MY_NOTES = MAIN_SERVER_URL + "data_model/notes/notes/notes";
    String API_GET_MY_NOTES = MAIN_SERVER_URL + "data_model/notes/notes/get_notes_my_notes";
    String API_DELETE_MY_NOTES = MAIN_SERVER_URL + "data_model/notes/notes/delete_my_notes";
    String API_EDIT_MY_NOTES = MAIN_SERVER_URL + "data_model/notes/notes/edit_my_notes";
    String API_ALL_DLP = MAIN_SERVER_URL + "data_model/dlp/dlp/get_dlp";
    String API_DLP_CETEGORIES_DATA = MAIN_SERVER_URL + "data_model/dlp/dlp/get_dlp_categories";
    String API_UPDATE_VIDEO_VIEW = MAIN_SERVER_URL + "data_model/courses/course/update_video_view_time";
    String API_BOOKMARK = MAIN_SERVER_URL + "data_model/courses/exam/set_video_bookmark";
    String API_SUBJECTIVE_QUESTIONS = MAIN_SERVER_URL + "data_model/courses/test_series/retrieve_subjective_copies";
    String API_SUBJECTIVE_SUBMIT = MAIN_SERVER_URL + "data_model/courses/test_series/submit_subjective_copies";
    String API_SUBJECTIVE_RESULT = MAIN_SERVER_URL + "data_model/courses/test_web/subjective_result";

    String API_GENERATE_LEAD = MAIN_SERVER_URL + "data_model/lead_squared/generate_lead";
    String API_GENERATE_ACTIVITY = MAIN_SERVER_URL + "data_model/lead_squared/generate_activity";


    // popup data
    String get_filter_data = MAIN_SERVER_URL + "data_model/course/get_course_filters";
    String API_GET_POPUP_DATA = "data_model/popup/get_popupdata";
    String API_POPUP_DATA_COLLECTION = "data_model/popup/popupdata_collection";
    String get_demo_data = MAIN_SERVER_URL + "data_model/version/get_demo_data";

    String get_liveclasses_data = MAIN_SERVER_URL + "data_model/course/get_live_videos";
    String get_testclasses_data = MAIN_SERVER_URL + "data_model/course/get_live_tests";


    String int_payment = MAIN_SERVER_URL + "data_model/payment/f_payment";
    String payment_gateway_credentials = MAIN_SERVER_URL + "data_model/master_hit/get_pay_gateway";
    String remove_course = MAIN_SERVER_URL + "data_model/payment/remove_course";
    String free_transaction = MAIN_SERVER_URL + "data_model/payment/free_transaction";
    String free_transaction_layer1 = MAIN_SERVER_URL + "data_model/payment/free_transaction";
    String video_link = MAIN_SERVER_URL + "data_model/revision/add_revision";

    String GET_COUPON_OVER_COURSE = "data_model/coupon/get_coupon_over_course";
    ////////////////
    String API_SUBMIT_TEST_SERIES = MAIN_SERVER_URL + "data_model/test/save_test";
    String API_GET_TEST_INSTRUCTION_DATA = MAIN_SERVER_URL + "data_model/test/get_instructions";
    String API_GET_INFO_TEST_SERIES = MAIN_SERVER_URL + "data_model/test/get_test_data";
    String MOBILE_CALCULATOR = MAIN_SERVER_URL + "data_model/test/mobile_calculator";
    String challenge_submission = MAIN_SERVER_URL + "data_model/test/test_challenge";
    String API_SUBMIT_QUERY = MAIN_SERVER_URL + "data_model/test/submit_query";
    String API_TEST_RESULT = MAIN_SERVER_URL + "data_model/test/get_test_result";
    String API_TEST_LEARN = MAIN_SERVER_URL + "data_model/test/get_test_learn";
    String API_TEST_LEADERBOARD = MAIN_SERVER_URL + "data_model/test/get_test_leaderboard";

    String API_CREATE_TEST_RETRIVE_COURSE = MAIN_SERVER_URL + "data_model/test/retrieve_courses";
    String API_CREATE_TEST_GET_SUBJECT = MAIN_SERVER_URL + "data_model/test/gen_get_course_subjects";
    String API_CREATE_TEST_GET_QUES_COUNT = MAIN_SERVER_URL + "data_model/test/gen_get_que_count";
    String API_CREATE_TEST_GET_TEST = MAIN_SERVER_URL + "data_model/test/gen_get_questions";

    String get_notification_data = MAIN_SERVER_URL + "data_model/notification/get_notifications";
    String mark_as_read = MAIN_SERVER_URL + "data_model/notification/mark_as_read";
    String mark_as_allread = MAIN_SERVER_URL + "data_model/notification/set_all_read";
    String delete_notification = MAIN_SERVER_URL + "data_model/notification/delete_notification";

    String get_meta = MAIN_SERVER_URL + "data_model/poll/get_content_meta";

    String get_file_names = MAIN_SERVER_URL + "data_model/course/get_file_names";

    String add_video_index = MAIN_SERVER_URL + "data_model/poll/add_video_index";

    ///by mohit
    String get_sign_test_link = MAIN_SERVER_URL + "data_model/meta_distributer/on_request_meta_source_direct";

    String submitpoll = MAIN_SERVER_URL + "data_model/poll/submit_poll";

    String delete_revision = MAIN_SERVER_URL + "data_model/revision/delete_revision";
    String apply_coupon = MAIN_SERVER_URL + "data_model/payment/apply_coupon";

    String GET_MY_QUIRES = API.MAIN_SERVER_URL + "data_model/help_desk/get_my_queries";
    String GET_SUBMIT_MY_QUIRES = API.MAIN_SERVER_URL + "data_model/help_desk/submit_query";
    String GET_QUIRES_REPLIES = API.MAIN_SERVER_URL + "data_model/help_desk/get_query_replies";
    String GET_SUBMIT_QUIRES_REPLIES = API.MAIN_SERVER_URL + "data_model/help_desk/submit_query_reply";

    String update_profile = MAIN_SERVER_URL + "data_model/users/update_profile";
    String get_my_orders = MAIN_SERVER_URL + "data_model/course/get_my_orders";
    String delete_video_index = MAIN_SERVER_URL + "data_model/poll/delete_video_index";
    String user_logout = MAIN_SERVER_URL + "data_model/users/logout";

    String get_video_link_concept = MAIN_SERVER_URL + "data_model/meta_distributer/on_request_meta_source";
    String create_annotation = MAIN_SERVER_URL + "data_model/meta_distributer/create_annotation";
    String delete_annotation = MAIN_SERVER_URL + "data_model/meta_distributer/delete_annotation";
    String getchangedetecter = MAIN_SERVER_URL + "data_model/change_detector/get_api_versions";
    String CourseDetail_JS = MAIN_SERVER_URL + "data_model/course_deprecated/get_course_detail";


    //COUPON||||||||||||||||||||\\\\\\\\\\\\\\\\
    String IS_COUPON_AVAILABLE = MAIN_SERVER_URL + "data_model/coupon/is_coupon_available";
    String API_GET_COUPON = MAIN_SERVER_URL + "data_model/coupon/get_coupon";

    String API_REQUEST_VIDEO_LICENSE = MAIN_SERVER_URL + "data_model/meta_distributer/on_request_drm_license";
    String get_video_link = MAIN_SERVER_URL + "data_model/meta_distributer/on_request_meta_source";
    String update_preference = MAIN_SERVER_URL + "data_model/users/update_preference";

    //////////////feed/////////////
    String getPost = MAIN_SERVER_URL + "data_model/post/get_post_list";
    String get_post_detail = MAIN_SERVER_URL + "data_model/post/get_post_detail";
    String like_unlike_post = MAIN_SERVER_URL + "data_model/post/like_unlike_post";
    String attempt_mcq = MAIN_SERVER_URL + "data_model/post/attempt_mcq";
    String get_feed_comments = MAIN_SERVER_URL + "data_model/post/get_feed_comments";
    String comment_post = MAIN_SERVER_URL + "data_model/post/comment_post";
    String pin_post = MAIN_SERVER_URL + "data_model/post/post_pinned";
    String add_update_address = MAIN_SERVER_URL + "data_model/users/add_update_address";
    String get_my_addresses = MAIN_SERVER_URL + "data_model/users/get_my_addresses";
    String delete_my_address = MAIN_SERVER_URL + "data_model/users/delete_my_address";
    String get_delivery_charges = MAIN_SERVER_URL + "data_model/payment/get_delivery_charges";

    // utkarsh new development
    String loginAuthenticationWithOtp = MAIN_SERVER_URL + "data_model/users/login_with_otp";
    String changePassword = MAIN_SERVER_URL + "data_model/users/change_password";
    String markAttendence = MAIN_SERVER_URL + "data_model/attendance/mark_attendance";
    String post_feed_type = MAIN_SERVER_URL + "data_model/post/post_update";

}