package com.utkarshnew.android.Model.PlayerPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addindex {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private VideoTimeFramePojo data;
    @SerializedName("time")
    @Expose
    private Integer time;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VideoTimeFramePojo getData() {
        return data;
    }

    public void setData(VideoTimeFramePojo data) {
        this.data = data;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
