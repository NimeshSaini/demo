package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopTenList implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("name")
    @Expose
    private String name;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}