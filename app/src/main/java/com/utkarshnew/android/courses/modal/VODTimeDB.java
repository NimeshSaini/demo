package com.utkarshnew.android.courses.modal;

import java.io.Serializable;

public class VODTimeDB implements Serializable {
    String userId;
    String type;
    String courseId;
    String videoid;
    String totalTime;
    String leftTime;
    String tileId;
    //long totalRemainingtime;

    public VODTimeDB(String userId, String type, String courseId, String videoid, String totalTime, String leftTime, String tileId) {
        this.userId = userId;
        this.type = type;
        this.courseId = courseId;
        this.videoid = videoid;
        this.totalTime = totalTime;
        this.leftTime = leftTime;
        this.tileId = tileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public String getTileId() {
        return tileId;
    }

    public void setTileId(String tileId) {
        this.tileId = tileId;
    }
}
