package com.utkarshnew.android.home.liveclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayloadData implements Serializable {

    @SerializedName("topic_id")
    @Expose
    private String topic_id;

    @SerializedName("tile_id")
    @Expose
    private String tile_id;

    @SerializedName("tile_type")
    @Expose
    private String tile_type;


    public String getTile_name() {
        return tile_name;
    }

    public void setTile_name(String tile_name) {
        this.tile_name = tile_name;
    }

    @SerializedName("tile_name")
    @Expose
    private String tile_name;


    @SerializedName("revert_api")
    @Expose
    private String revert_api;

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }

    public String getTile_type() {
        return tile_type;
    }

    public void setTile_type(String tile_type) {
        this.tile_type = tile_type;
    }

    public String getRevert_api() {
        return revert_api;
    }

    public void setRevert_api(String revert_api) {
        this.revert_api = revert_api;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @SerializedName("course_id")
    @Expose
    private String course_id;
}
