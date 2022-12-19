package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class QuestionDumps implements Serializable {
    @SerializedName("answers")
    @Expose
    private List<Object> answers = null;
    @SerializedName("config_id")
    @Expose
    private String configId;
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("on_screen")
    @Expose
    private String onScreen;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("state")
    @Expose
    private String state;

    public String getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(String is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    @SerializedName("is_bookmarked")
    @Expose
    private String is_bookmarked;

    @SerializedName("is_challenge")
    @Expose
    private int is_challenge;

    public int getIs_challenge() {
        return is_challenge;
    }

    public void setIs_challenge(int is_challenge) {
        this.is_challenge = is_challenge;
    }


    public List<Object> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Object> answers) {
        this.answers = answers;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getOnScreen() {
        return onScreen;
    }

    public void setOnScreen(String onScreen) {
        this.onScreen = onScreen;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}