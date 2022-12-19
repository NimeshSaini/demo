package com.utkarshnew.android.home.model.bannerHomeData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("list")
    @Expose
    private ArrayList<BannerData> data = null;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArrayList<BannerData> getData() {
        return data;
    }

    public void setData(ArrayList<BannerData> data) {
        this.data = data;
    }
}