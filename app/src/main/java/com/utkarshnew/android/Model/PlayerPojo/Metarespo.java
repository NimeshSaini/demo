package com.utkarshnew.android.Model.PlayerPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Metarespo {

    @SerializedName("pdf")
    @Expose
    private List<Pdf> pdf = null;
    @SerializedName("poll")
    @Expose
    private List<Polldata> poll = null;
    @SerializedName("index")
    @Expose
    private List<VideoTimeFramePojo> index = null;
    @SerializedName("bookmark")
    @Expose
    private List<VideoTimeFramePojo> bookmark = null;

    public List<Pdf> getPdf() {
        return pdf;
    }

    public void setPdf(List<Pdf> pdf) {
        this.pdf = pdf;
    }

    public List<Polldata> getPoll() {
        return poll;
    }

    public void setPoll(List<Polldata> poll) {
        this.poll = poll;
    }

    public List<VideoTimeFramePojo> getIndex() {
        return index;
    }

    public void setIndex(List<VideoTimeFramePojo> index) {
        this.index = index;
    }

    public List<VideoTimeFramePojo> getBookmark() {
        return bookmark;
    }

    public void setBookmark(List<VideoTimeFramePojo> bookmark) {
        this.bookmark = bookmark;
    }

}
