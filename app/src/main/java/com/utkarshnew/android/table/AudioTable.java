package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "AudioTable")
public class AudioTable implements Serializable {

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

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }


    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "video_id")
    private String video_id;
    @ColumnInfo(name = "video_name")
    private String video_name;
    @ColumnInfo(name = "user_id")
    private String user_id;

    @ColumnInfo(name = "audio_url")
    private String audio_url;

    public Long getAudio_currentpos() {
        return audio_currentpos;
    }

    public void setAudio_currentpos(Long audio_currentpos) {
        this.audio_currentpos = audio_currentpos;
    }

    @ColumnInfo(name = "audio_currentpos")
    private Long audio_currentpos;

    public String getJw_url() {
        return jw_url;
    }

    public void setJw_url(String jw_url) {
        this.jw_url = jw_url;
    }

    @ColumnInfo(name = "jw_url")
    private String jw_url;


    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    @ColumnInfo(name = "valid_to")
    private String valid_to;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }


    @ColumnInfo(name = "course_id")
    private String course_id;


}
