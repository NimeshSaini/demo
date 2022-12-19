package com.utkarshnew.android.Model.Overview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.Model.Courselist;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    @SerializedName("visibility")
    @Expose
    private String visibility;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("description_2")
    @Expose
    private Description description2;

    @SerializedName("ceo_message")
    @Expose
    private CeoMessage ceoMessage;

    @SerializedName("related_courses")
    @Expose
    private ArrayList<Courselist> relatedCourses = null;

    public ArrayList<Courselist> getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(ArrayList<Courselist> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    public CeoMessage getCeoMessage() {
        return ceoMessage;
    }

    public void setCeoMessage(CeoMessage ceoMessage) {
        this.ceoMessage = ceoMessage;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Description getDescription2() {
        return description2;
    }

    public void setDescription2(Description description2) {
        this.description2 = description2;
    }
}
