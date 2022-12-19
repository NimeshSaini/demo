package com.utkarshnew.android.courses.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OnlineUser implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("profile_picture")
    @Expose
    private String profile_picture;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @SerializedName("online")
    @Expose
    private String online;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getInteract() {
        return interact;
    }

    public void setInteract(String interact) {
        this.interact = interact;
    }

    @SerializedName("interact")
    @Expose
    private String interact;

    public String getIs_chat_locked() {
        return is_chat_locked;
    }

    public void setIs_chat_locked(String is_chat_locked) {
        this.is_chat_locked = is_chat_locked;
    }

    @SerializedName("is_chat_locked")
    @Expose
    private String is_chat_locked;


    public long getJoined_at() {
        return joined_at;
    }

    public void setJoined_at(long joined_at) {
        this.joined_at = joined_at;
    }

    @SerializedName("joined_at")
    @Expose
    private long joined_at;


    public OnlineUser() {

    }

    public OnlineUser(String name, String profile, String type, String online, String id, String mobile, String interact) {
        this.id = id;
        this.name = name;
        this.profile_picture = profile;
        this.type = type;
        this.online = online;
        this.mobile = mobile;
        this.interact = interact;
    }

    public OnlineUser(String name, String profile, String type, String online, String id, String mobile, String interact, long joined_at,String is_chat_locked) {
        this.id = id;
        this.name = name;
        this.profile_picture = profile;
        this.type = type;
        this.online = online;
        this.mobile = mobile;
        this.interact = interact;
        this.joined_at = joined_at;
        this.is_chat_locked = is_chat_locked;
    }
}
