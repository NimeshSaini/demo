package com.utkarshnew.android.home.model.bannerHomeData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannerData implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image_link")
    @Expose
    private String imageLink;
    @SerializedName("web_link")
    @Expose
    private String webLink;
    @SerializedName("course_link")
    @Expose
    private String courseLink;
    @SerializedName("study_type")
    @Expose
    private String studyType;
    @SerializedName("stude_type_detail")
    @Expose
    private String studeTypeDetail;
    @SerializedName("feed_type")
    @Expose
    private String feedType;
    @SerializedName("link_level")
    @Expose
    private String linkLevel;
    @SerializedName("type_link")
    @Expose
    private String typeLink;
    @SerializedName("button_text")
    @Expose
    private String buttonText;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("creation_time")
    @Expose
    private String creationTime;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("banner_title")
    @Expose
    private String bannerTitle;
    @SerializedName("hit_count")
    @Expose
    private String hitCount;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getStudeTypeDetail() {
        return studeTypeDetail;
    }

    public void setStudeTypeDetail(String studeTypeDetail) {
        this.studeTypeDetail = studeTypeDetail;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getLinkLevel() {
        return linkLevel;
    }

    public void setLinkLevel(String linkLevel) {
        this.linkLevel = linkLevel;
    }

    public String getTypeLink() {
        return typeLink;
    }

    public void setTypeLink(String typeLink) {
        this.typeLink = typeLink;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getHitCount() {
        return hitCount;
    }

    public void setHitCount(String hitCount) {
        this.hitCount = hitCount;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}