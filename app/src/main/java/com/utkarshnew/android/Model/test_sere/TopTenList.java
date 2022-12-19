package com.utkarshnew.android.Model.test_sere;

import com.google.gson.annotations.SerializedName;

public class TopTenList {

    @SerializedName("creation_time")
    private String mCreationTime;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_picture")
    private String mProfilePicture;
    @SerializedName("user_id")
    private String mUserId;

    public String getCreationTime() {
        return mCreationTime;
    }

    public void setCreationTime(String creationTime) {
        mCreationTime = creationTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        mProfilePicture = profilePicture;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
