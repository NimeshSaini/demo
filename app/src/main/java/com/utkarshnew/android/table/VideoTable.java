package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VideoTable")
public class VideoTable {

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

    public Long getVideo_currentpos() {
        return video_currentpos;
    }

    public void setVideo_currentpos(Long video_currentpos) {
        this.video_currentpos = video_currentpos;
    }

    public String getJw_url() {
        return jw_url;
    }

    public void setJw_url(String jw_url) {
        this.jw_url = jw_url;
    }

    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "video_id")
    private String video_id;
    @ColumnInfo(name = "video_name")
    private String video_name;
    @ColumnInfo(name = "user_id")
    private String user_id;

    @ColumnInfo(name = "video_currentpos")
    private Long video_currentpos;

    @ColumnInfo(name = "jw_url")
    private String jw_url;

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


}
