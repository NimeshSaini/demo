package com.utkarshnew.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyNotesResponse implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("article_id")
    @Expose
    private String articleId;
    @SerializedName("dose_type")
    @Expose
    private String doseType;
    @SerializedName("note_data")
    @Expose
    private String noteData;
    @SerializedName("creation_time")
    @Expose
    private String creationTime;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;
    @SerializedName("article_data")
    @Expose
    private PostResponse articleData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getDoseType() {
        return doseType;
    }

    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    public String getNoteData() {
        return noteData;
    }

    public void setNoteData(String noteData) {
        this.noteData = noteData;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public PostResponse getArticleData() {
        return articleData;
    }

    public void setArticleData(PostResponse articleData) {
        this.articleData = articleData;
    }

}