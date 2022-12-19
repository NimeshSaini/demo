package com.utkarshnew.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoursePDF implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("pdf_title")
    @Expose
    private String pdfTitle;
    @SerializedName("pdf_url")
    @Expose
    private String pdfUrl;
    @SerializedName("pdf_thumbnail")
    @Expose
    private String pdfThumbnail;
    @SerializedName("page_count")
    @Expose
    private String pageCount;
    @SerializedName("creation_time")
    @Expose
    private String creationTime;
    @SerializedName("is_downloadable")
    @Expose
    private String is_downloadable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfThumbnail() {
        return pdfThumbnail;
    }

    public void setPdfThumbnail(String pdfThumbnail) {
        this.pdfThumbnail = pdfThumbnail;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getIs_downloadable() {
        return is_downloadable;
    }

    public void setIs_downloadable(String is_downloadable) {
        this.is_downloadable = is_downloadable;
    }
}