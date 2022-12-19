package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CutOff implements Serializable {
    @SerializedName("section_id")
    @Expose
    private String sectionId;

    public void setUserMarks(float userMarks) {
        this.userMarks = userMarks;
    }

    @SerializedName("user_marks")
    @Expose
    private float userMarks;
    @SerializedName("total_attempt")
    @Expose
    private Integer totalAttempt;
    @SerializedName("right_answered")
    @Expose
    private Integer rightAnswered;
    @SerializedName("time_spent")
    @Expose
    private Integer timeSpent;
    @SerializedName("section_cutoff")
    @Expose
    private String sectionCutoff;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }


    public Integer getTotalAttempt() {
        return totalAttempt;
    }

    public void setTotalAttempt(Integer totalAttempt) {
        this.totalAttempt = totalAttempt;
    }

    public Integer getRightAnswered() {
        return rightAnswered;
    }

    public void setRightAnswered(Integer rightAnswered) {
        this.rightAnswered = rightAnswered;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getSectionCutoff() {
        return sectionCutoff;
    }

    public void setSectionCutoff(String sectionCutoff) {
        this.sectionCutoff = sectionCutoff;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}