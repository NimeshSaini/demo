package com.utkarshnew.android.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "YoutubePlayerTable")
public class YoutubePlayerTable {

    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "youtubeid")
    private String youtubeid;

    @ColumnInfo(name = "youtubetime")
    private long youtubetime;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getYoutubeid() {
        return youtubeid;
    }

    public void setYoutubeid(String youtubeid) {
        this.youtubeid = youtubeid;
    }

    public long getYoutubetime() {
        return youtubetime;
    }

    public void setYoutubetime(long youtubetime) {
        this.youtubetime = youtubetime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @ColumnInfo(name = "userid")
    private String userid;

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    @ColumnInfo(name = "videoid")
    private String videoid;

    public String getIsaudio() {
        return isaudio;
    }

    public void setIsaudio(String isaudio) {
        this.isaudio = isaudio;
    }

    @ColumnInfo(name = "isaudio")
    private String isaudio;

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    @ColumnInfo(name = "videoname")
    private String videoname;

    @ColumnInfo(name = "valid_to")
    private String valid_to;

    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @ColumnInfo(name = "course_id")
    private String course_id;
}
