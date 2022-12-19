
package com.utkarshnew.android.home.liveclasses;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.makeramen.roundedimageview.RoundedImageView;
import com.utkarshnew.android.Model.UrlObject;
import com.utkarshnew.android.R;
import com.utkarshnew.android.home.livetest.LiveTestData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Datum implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    public String getIs_att_done() {
        return is_att_done;
    }

    public void setIs_att_done(String is_att_done) {
        this.is_att_done = is_att_done;
    }

    @SerializedName("is_att_done")
    @Expose
    private String is_att_done;


    public String getPre_attendance_second() {
        return pre_attendance_minute;
    }

    public void setPre_attendance_second(String pre_attendance_minute) {
        this.pre_attendance_minute = pre_attendance_minute;
    }

    @SerializedName("pre_attendance_seconds")
    @Expose
    private String pre_attendance_minute;

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    @SerializedName("is_locked")
    @Expose
    private String is_locked;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("video_type")
    @Expose
    private String videoType;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @SerializedName("course_name")
    @Expose
    private String course_name;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    @SerializedName("start_date")
    @Expose
    private String startdate;

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @SerializedName("end_date")
    @Expose
    private String end_date;

    @SerializedName("file_url")
    @Expose
    private String fileUrl;
    @SerializedName("is_download")
    @Expose
    private String isDownload;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;

    public ArrayList<UrlObject> getBitrate_urls() {
        return bitrate_urls;
    }

    public void setBitrate_urls(ArrayList<UrlObject> bitrate_urls) {
        this.bitrate_urls = bitrate_urls;
    }

    private ArrayList<UrlObject> bitrate_urls;


    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("video_length")
    @Expose
    private String videoLength;
    @SerializedName("chat_node")
    @Expose
    private String chatNode;
    @SerializedName("live_status")
    @Expose
    private String liveStatus;
    @SerializedName("open_in_app")
    @Expose
    private String openInApp;

    @SerializedName("live_on")
    private String live_on;

    public String getLive_on() {
        return live_on;
    }

    public void setLive_on(String live_on) {
        this.live_on = live_on;
    }

    @SerializedName("playtime")
    @Expose
    private String playtime;

    public PayloadData getPayload() {
        return payload;
    }

    public void setPayload(PayloadData payload) {
        this.payload = payload;
    }

    @SerializedName("payload")
    @Expose
    private PayloadData payload;

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    @SerializedName("is_live")
    @Expose
    private String is_live;

    private long cd_time = 0;


    public String getIschatlock() {
        return ischatlock;
    }

    public void setIschatlock(String ischatlock) {
        this.ischatlock = ischatlock;
    }

    @SerializedName("is_chat_locked")
    @Expose
    private String ischatlock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(String isDownload) {
        this.isDownload = isDownload;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public String getChatNode() {
        return chatNode;
    }

    public void setChatNode(String chatNode) {
        this.chatNode = chatNode;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getOpenInApp() {
        return openInApp;
    }

    public void setOpenInApp(String openInApp) {
        this.openInApp = openInApp;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public long getCd_time() {
        return cd_time;
    }

    public void setCd_time(long cd_time) {
        this.cd_time = cd_time;
    }

/*
    @SuppressLint("CheckResult")
@BindingAdapter("linkimage")
public static void  loadImage(RoundedImageView imageView,String url){
            Glide.with(imageView.getContext()).load(url).apply(new RequestOptions().placeholder(R.drawable.square_thumbnail).error(R.drawable.square_thumbnail));

}
*/


    @BindingAdapter("time")
    public static void  time(TextView textView, Datum data){
        String time="";
        time = getDate(Long.parseLong(data.getStartdate())*1000, "dd-MMM-yyyy hh:mm a");
        time = time+" - "+getDate(Long.parseLong(data.getEnd_date())*1000, "dd-MMM-yyyy hh:mm a");
        textView.setText(time);
    }

    private static String getDate(long start_msecond, String dateFormat) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dateFormat);
        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(start_msecond);
        return simpleDateFormat.format(calendar.getTime());
    }




}
