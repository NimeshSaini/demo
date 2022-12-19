package com.utkarshnew.android.Model;

import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.home.liveclasses.PayloadData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin1 on 28/10/17.
 */

public class Video implements Serializable {
    public String getResult_date() {
        return result_date;
    }

    public void setResult_date(String result_date) {
        this.result_date = result_date;
    }

    private String result_date;
    private String initial_view;
    private String tags;
    private String for_non_dams;
    private String screen_tag;

    public String getIs_chat_lock() {
        return is_chat_locked;
    }

    public void setIs_chat_lock(String is_chat_lock) {
        this.is_chat_locked = is_chat_lock;
    }

    private String is_chat_locked;

    public PayloadData getPayloadData() {
        return payload;
    }

    public void setPayloadData(PayloadData payloadData) {
        this.payload = payloadData;
    }

    private PayloadData payload;

    public String getIs_reattempt() {
        return is_reattempt;
    }

    public void setIs_reattempt(String is_reattempt) {
        this.is_reattempt = is_reattempt;
    }

    private String is_reattempt;

    private String video_type;
    private String file_type;
    private String topic_id;
    private String subject_id;
    private String allow_comments;
    private String is_like;
    private String video_title;
    private String title;
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    private String modified;
    private String featured;
    private String video_desc;
    private String is_viewed;

    public ArrayList<UrlObject> getBitrate_urls() {
        return bitrate_urls;
    }

    public void setBitrate_urls(ArrayList<UrlObject> bitrate_urls) {
        this.bitrate_urls = bitrate_urls;
    }

    private ArrayList<UrlObject> bitrate_urls;
    private String id;
    private String chat_mode;
    private String is_new;
    private String thumbnail_url;
    private String file_url;
    private String file_url_enc;
    private String end_date;
    private String sub_cat;
    private String views;
    private String likes;
    private String for_dams;
    private String author_name;
    private String creation_time;
    private String main_cat;
    private String URL;
    private String URL_ENC;
    private String start_date;
    private String comments;
    private String is_favourite;
    private String is_locked;
    private String description;
    private String chat_node;
    private String live_status;
    private String video_length;
    private String video_creation_time;

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    private boolean is_deleted;


    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }

    public Long getVideo_currentpos() {
        return video_currentpos;
    }

    public void setVideo_currentpos(Long video_currentpos) {
        this.video_currentpos = video_currentpos;
    }

    public String getVideo_status() {
        return video_status;
    }

    public void setVideo_status(String video_status) {
        this.video_status = video_status;
    }

    public boolean isVideo_download() {
        return video_download;
    }

    public void setVideo_download(boolean video_download) {
        this.video_download = video_download;
    }

    public String getIs_download_available() {
        return is_download_available;
    }

    public void setIs_download_available(String is_download_available) {
        this.is_download_available = is_download_available;
    }

    private String videotime;


    private Long video_currentpos;


    private String video_status;



    private boolean video_download = false;


    @SerializedName("is_download")
    private String is_download_available;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public String getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(String remaining_time) {
        this.remaining_time = remaining_time;
    }

    private String playtime;
    private String remaining_time;

    public String getIs_vod() {
        return is_vod;
    }

    public void setIs_vod(String is_vod) {
        this.is_vod = is_vod;
    }

    public String getIs_Download() {
        return is_download_available;
    }

    public void setIs_Download(String is_download_available) {
        this.is_download_available = is_download_available;
    }

    public List<EncryptedUrl> getEncrypted_urls() {
        return encrypted_urls;
    }

    public void setEncrypted_urls(List<EncryptedUrl> encrypted_urls) {
        this.encrypted_urls = encrypted_urls;
    }

    private String is_vod;
    private String open_in_app;

    public String getOpen_in_app() {
        return open_in_app;
    }

    public void setOpen_in_app(String open_in_app) {
        this.open_in_app = open_in_app;
    }


    public String getLive_on() {
        return live_on;
    }

    public void setLive_on(String live_on) {
        this.live_on = live_on;
    }

    private String live_on;

    private List<EncryptedUrl> encrypted_urls = null;

    private String is_live;

    private String is_purchased;

    public String getIs_purchased() {
        return is_purchased;
    }

    public void setIs_purchased(String is_purchased) {
        this.is_purchased = is_purchased;
    }

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(String is_favourite) {
        this.is_favourite = is_favourite;
    }


    public String getInitial_view() {
        return initial_view;
    }

    public void setInitial_view(String initial_view) {
        this.initial_view = initial_view;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFor_non_dams() {
        return for_non_dams;
    }

    public void setFor_non_dams(String for_non_dams) {
        this.for_non_dams = for_non_dams;
    }

    public String getScreen_tag() {
        return screen_tag;
    }

    public void setScreen_tag(String screen_tag) {
        this.screen_tag = screen_tag;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getAllow_comments() {
        return allow_comments;
    }

    public void setAllow_comments(String allow_comments) {
        this.allow_comments = allow_comments;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getIs_viewed() {
        return is_viewed;
    }

    public void setIs_viewed(String is_viewed) {
        this.is_viewed = is_viewed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChat_mode() {
        return chat_mode;
    }

    public void setChat_mode(String chat_mode) {
        this.chat_mode = chat_mode;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getFor_dams() {
        return for_dams;
    }

    public void setFor_dams(String for_dams) {
        this.for_dams = for_dams;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getMain_cat() {
        return main_cat;
    }

    public void setMain_cat(String main_cat) {
        this.main_cat = main_cat;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL_ENC() {
        return URL_ENC;
    }

    public void setURL_ENC(String URL_ENC) {
        this.URL_ENC = URL_ENC;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getChat_node() {
        return chat_node;
    }

    public void setChat_node(String chat_node) {
        this.chat_node = chat_node;
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    public String getLive_status() {
        return live_status;
    }

    public void setLive_status(String live_status) {
        this.live_status = live_status;
    }

    public String getFile_url_enc() {
        return file_url_enc;
    }

    public void setFile_url_enc(String file_url_enc) {
        this.file_url_enc = file_url_enc;
    }

    public String getVideo_length() {
        return video_length;
    }

    public void setVideo_length(String video_length) {
        this.video_length = video_length;
    }

    public String getVideo_creation_time() {
        return video_creation_time;
    }

    public void setVideo_creation_time(String video_creation_time) {
        this.video_creation_time = video_creation_time;
    }

    private String image;
    private String description_2;
    private String test_series_name;
    private String test_code;
    private String test_type;
    private String set_type;
    private String total_marks;
    private String lang_id;
    private String total_questions;
    private String time_in_mins;
    private String report_id;
    private String marks;
    private String state;
    private String correct_count;
    private String incorrect_count;
    private String lang_used;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription_2() {
        return description_2;
    }

    public void setDescription_2(String description_2) {
        this.description_2 = description_2;
    }

    public String getTest_series_name() {
        return test_series_name;
    }

    public void setTest_series_name(String test_series_name) {
        this.test_series_name = test_series_name;
    }

    public String getTest_code() {
        return test_code;
    }

    public void setTest_code(String test_code) {
        this.test_code = test_code;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getSet_type() {
        return set_type;
    }

    public void setSet_type(String set_type) {
        this.set_type = set_type;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }

    public String getLang_id() {
        return lang_id;
    }

    public void setLang_id(String lang_id) {
        this.lang_id = lang_id;
    }

    public String getTotal_questions() {
        return total_questions;
    }

    public void setTotal_questions(String total_questions) {
        this.total_questions = total_questions;
    }

    public String getTime_in_mins() {
        return time_in_mins;
    }

    public void setTime_in_mins(String time_in_mins) {
        this.time_in_mins = time_in_mins;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCorrect_count() {
        return correct_count;
    }

    public void setCorrect_count(String correct_count) {
        this.correct_count = correct_count;
    }

    public String getIncorrect_count() {
        return incorrect_count;
    }

    public void setIncorrect_count(String incorrect_count) {
        this.incorrect_count = incorrect_count;
    }

    public String getLang_used() {
        return lang_used;
    }

    public void setLang_used(String lang_used) {
        this.lang_used = lang_used;
    }

    public String getSubmission_type() {
        return submission_type;
    }

    public void setSubmission_type(String submission_type) {
        this.submission_type = submission_type;
    }

    private String submission_type;
}