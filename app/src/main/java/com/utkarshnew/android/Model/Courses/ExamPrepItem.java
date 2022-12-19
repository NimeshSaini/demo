package com.utkarshnew.android.Model.Courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ExamPrepItem implements Serializable {

    @SerializedName("layer")
    @Expose
    private String layer;

    @SerializedName("list")
    @Expose
    private ArrayList<Lists> list;

    @SerializedName("study_analytics")
    @Expose
    private StudyAnalytics studyAnalytics;


    public ExamPrepItem() {
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public ArrayList<Lists> getList() {
        return list;
    }

    public void setList(ArrayList<Lists> list) {
        this.list = list;
    }

    public StudyAnalytics getStudyAnalytics() {
        return studyAnalytics;
    }

    public void setStudyAnalytics(StudyAnalytics studyAnalytics) {
        this.studyAnalytics = studyAnalytics;
    }
}


