package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserHistroyTable")
public class UserHistroyTable {
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPdf_name() {
        return pdf_name;
    }

    public void setPdf_name(String pdf_name) {
        this.pdf_name = pdf_name;
    }

    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "video_id")
    private String video_id;

    @ColumnInfo(name = "video_name")
    private String video_name;

    @ColumnInfo(name = "user_id")
    private String user_id;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "pdf_name")
    private String pdf_name;

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

    @ColumnInfo(name = "youtube_url")
    private String youtube_url;


    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    @ColumnInfo(name = "pdf_url")
    private String pdf_url;


    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    @ColumnInfo(name = "current_time")
    private String current_time;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @ColumnInfo(name = "course_id")
    private String course_id;


    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    @ColumnInfo(name = "valid_to")
    private String valid_to;


    @ColumnInfo(name = "coursename")
    private String coursename;


    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getTileid() {
        return tileid;
    }

    public void setTileid(String tileid) {
        this.tileid = tileid;
    }

    @ColumnInfo(name = "tileid")
    private String tileid;

    @ColumnInfo(name = "testid")
    private String testid;

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    @ColumnInfo(name = "testname")
    private String testname;


    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    @ColumnInfo(name = "topicname")
    private String topicname;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ColumnInfo(name = "url")
    private String url;

}
