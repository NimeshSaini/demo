package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VideoDownload")
public class VideosDownload {

    @PrimaryKey(autoGenerate = true)
    private int autoid;


    @ColumnInfo(name = "video_id")
    private String video_id;


    @ColumnInfo(name = "video_name")
    private String video_name;


    @ColumnInfo(name = "originalFileLengthString")
    private String originalFileLengthString;

    @ColumnInfo(name = "videotime")
    private String videotime;

    @ColumnInfo(name = "total")
    private Long toal_downloadlocale;

    @ColumnInfo(name = "lengthInMb")
    private String lengthInMb;
    @ColumnInfo(name = "percentage")
    private int percentage;
    @ColumnInfo(name = "user_id")
    private String user_id;


    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @ColumnInfo(name = "course_id")
    private String course_id;

    public int getValid_to() {
        return valid_to;
    }

    public void setValid_to(int valid_to) {
        this.valid_to = valid_to;
    }

    @ColumnInfo(name = "valid_to")
    private int valid_to;


    @ColumnInfo(name = "position")
    private int position;

    @ColumnInfo(name = "mp4_download_url")
    private String mp4_download_url;
    @ColumnInfo(name = "video_status")
    private String video_status;

    public String getVideo_enc() {
        return video_enc;
    }

    public void setVideo_enc(String video_enc) {
        this.video_enc = video_enc;
    }

    @ColumnInfo(name = "video_enc")
    private String video_enc;

    public String getVideo_token() {
        return video_token;
    }

    public void setVideo_token(String video_token) {
        this.video_token = video_token;
    }

    @ColumnInfo(name = "video_token")
    private String video_token;


    @ColumnInfo(name = "thumbnail_url")
    private String thumbnail_url;
    @ColumnInfo(name = "is_complete")
    private String is_complete;


    @ColumnInfo(name = "videoCurrentPosition")
    private Long videoCurrentPosition;

    @ColumnInfo(name = "jw_url")
    private String jw_url;


    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

    @ColumnInfo(name = "is_selected")
    private String is_selected;


    public String getVideo_history() {
        return video_history;
    }

    public void setVideo_history(String video_history) {
        this.video_history = video_history;
    }

    @ColumnInfo(name = "video_history")
    private String video_history;


    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }


    public String getJw_url() {
        return jw_url;
    }

    public void setJw_url(String jw_url) {
        this.jw_url = jw_url;
    }


    public Long getVideoCurrentPosition() {
        return videoCurrentPosition;
    }

    public void setVideoCurrentPosition(Long videoCurrentPosition) {
        this.videoCurrentPosition = videoCurrentPosition;
    }

    public String getMp4_download_url() {
        return mp4_download_url;
    }

    public void setMp4_download_url(String mp4_download_url) {
        this.mp4_download_url = mp4_download_url;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(String is_complete) {
        this.is_complete = is_complete;
    }


    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }


    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getOriginalFileLengthString() {
        return originalFileLengthString;
    }

    public void setOriginalFileLengthString(String originalFileLengthString) {
        this.originalFileLengthString = originalFileLengthString;
    }

    public Long getToal_downloadlocale() {
        return toal_downloadlocale;
    }

    public void setToal_downloadlocale(Long toal_downloadlocale) {
        this.toal_downloadlocale = toal_downloadlocale;
    }

    public String getLengthInMb() {
        return lengthInMb;
    }

    public void setLengthInMb(String lengthInMb) {
        this.lengthInMb = lengthInMb;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getVideo_status() {
        return video_status;
    }

    public void setVideo_status(String video_status) {
        this.video_status = video_status;
    }

}
