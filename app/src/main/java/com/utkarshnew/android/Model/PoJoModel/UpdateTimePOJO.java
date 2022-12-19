
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class UpdateTimePOJO implements Serializable {

    private String user_id;
    private String type;
    private String course_id;
    private String video_id;
    private String total_time;
    private String view_time;
    private String tile_id;

    public UpdateTimePOJO(String user_id, String type, String course_id, String video_id, String total_time, String view_time, String tile_id) {
        this.user_id = user_id;
        this.type = type;
        this.course_id = course_id;
        this.video_id = video_id;
        this.total_time = total_time;
        this.view_time = view_time;
        this.tile_id = tile_id;
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

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getView_time() {
        return view_time;
    }

    public void setView_time(String view_time) {
        this.view_time = view_time;
    }

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }
}
