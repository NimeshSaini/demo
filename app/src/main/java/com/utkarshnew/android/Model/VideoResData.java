package com.utkarshnew.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoResData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("video_title")
    @Expose
    private String videoTitle;
    @SerializedName("video_type")
    @Expose
    private String videoType;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("video_desc")
    @Expose
    private String videoDesc;
    @SerializedName("main_cat")
    @Expose
    private String mainCat;
    @SerializedName("sub_cat")
    @Expose
    private String subCat;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("initial_view")
    @Expose
    private String initialView;
    @SerializedName("screen_tag")
    @Expose
    private String screenTag;
    @SerializedName("featured")
    @Expose
    private String featured;
    @SerializedName("allow_comments")
    @Expose
    private String allowComments;
    @SerializedName("is_new")
    @Expose
    private String isNew;
    @SerializedName("for_dams")
    @Expose
    private String forDams;
    @SerializedName("for_non_dams")
    @Expose
    private String forNonDams;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("creation_time")
    @Expose
    private String creationTime;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("is_like")
    @Expose
    private String isLike;
    @SerializedName("is_viewed")
    @Expose
    private String isViewed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getMainCat() {
        return mainCat;
    }

    public void setMainCat(String mainCat) {
        this.mainCat = mainCat;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInitialView() {
        return initialView;
    }

    public void setInitialView(String initialView) {
        this.initialView = initialView;
    }

    public String getScreenTag() {
        return screenTag;
    }

    public void setScreenTag(String screenTag) {
        this.screenTag = screenTag;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getAllowComments() {
        return allowComments;
    }

    public void setAllowComments(String allowComments) {
        this.allowComments = allowComments;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getForDams() {
        return forDams;
    }

    public void setForDams(String forDams) {
        this.forDams = forDams;
    }

    public String getForNonDams() {
        return forNonDams;
    }

    public void setForNonDams(String forNonDams) {
        this.forNonDams = forNonDams;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(String isViewed) {
        this.isViewed = isViewed;
    }

}
