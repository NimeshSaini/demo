package com.utkarshnew.android.Model.Courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.home.liveclasses.PayloadData;
import com.utkarshnew.android.Model.UrlObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Lists implements Serializable {
    public PayloadData getPayload() {
        return payload;
    }

    public void setPayload(PayloadData payload) {
        this.payload = payload;
    }

    @SerializedName("payload")
    @Expose
    private PayloadData payload;
    private String total;
    private String image_icon;
    private String test_image;
    private String id;
    private String title;
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    private String modified;
    private String count;
    private String text;
    private String main_id;
    private String c_code;
    private String subject_id;
    private String is_live;
    private String vedio_url;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private String file_type;
    private String result_date;
    private String is_reattempt;

    public String getIs_chat_lock() {
        return is_chat_locked;
    }

    public void setIs_chat_lock(String is_chat_lock) {
        this.is_chat_locked = is_chat_lock;
    }

    private String is_chat_locked;

    public String getIs_reattempt() {
        return is_reattempt;
    }

    public void setIs_reattempt(String is_reattempt) {
        this.is_reattempt = is_reattempt;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    private String file_url;
    private String is_vod;
    private String thumbnail_url;
    private String description;
    private String video_type;
    private String is_locked;
    private String live_on;
    private String video_length;
    private String chat_node;
    private String live_status;
    private String open_in_app;
    private String playtime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private String image;
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

    private Long video_currentpos;

    public String getVideo_time() {
        return video_time;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }

    private String video_time;


    private String video_status;

    private boolean video_download = false;


    @SerializedName("is_download")
    private String is_download_available;


    public ArrayList<UrlObject> getBitrate_urls() {
        return bitrate_urls;
    }

    public void setBitrate_urls(ArrayList<UrlObject> bitrate_urls) {
        this.bitrate_urls = bitrate_urls;
    }

    private ArrayList<UrlObject> bitrate_urls;


    public Lists() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getTest_image() {
        return test_image;
    }

    public void setTest_image(String test_image) {
        this.test_image = test_image;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    public String getVedio_url() {
        return vedio_url;
    }

    public void setVedio_url(String vedio_url) {
        this.vedio_url = vedio_url;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getIs_vod() {
        return is_vod;
    }

    public void setIs_vod(String is_vod) {
        this.is_vod = is_vod;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    public String getLive_on() {
        return live_on;
    }

    public void setLive_on(String live_on) {
        this.live_on = live_on;
    }

    public String getVideo_length() {
        return video_length;
    }

    public void setVideo_length(String video_length) {
        this.video_length = video_length;
    }

    public String getChat_node() {
        return chat_node;
    }

    public void setChat_node(String chat_node) {
        this.chat_node = chat_node;
    }

    public String getLive_status() {
        return live_status;
    }

    public void setLive_status(String live_status) {
        this.live_status = live_status;
    }

    public String getOpen_in_app() {
        return open_in_app;
    }

    public void setOpen_in_app(String open_in_app) {
        this.open_in_app = open_in_app;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getResult_date() {
        return result_date;
    }

    public void setResult_date(String result_date) {
        this.result_date = result_date;
    }


    public String getSubmission_type() {
        return submission_type;
    }

    public void setSubmission_type(String submission_type) {
        this.submission_type = submission_type;
    }

    private String submission_type;


    private ArrayList<Lists> list;

    public ArrayList<Lists> getList() {
        return list;
    }

    public void setList(ArrayList<Lists> list) {
        this.list = list;
    }
}
