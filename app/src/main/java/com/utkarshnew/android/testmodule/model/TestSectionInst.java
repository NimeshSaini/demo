package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestSectionInst implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("section_part")
    @Expose
    private String sectionPart;
    @SerializedName("section_timing")
    @Expose
    private String sectionTiming;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_2")
    @Expose
    private String name2;

    public String getOptional_que() {
        return optional_que;
    }

    public void setOptional_que(String optional_que) {
        this.optional_que = optional_que;
    }

    @SerializedName("optional_que")
    @Expose
    private String optional_que;

    @SerializedName("marks_per_question")
    @Expose
    private String marksPerQuestion;
    @SerializedName("negative_marks")
    @Expose
    private String negativeMarks;
    @SerializedName("total_questions")
    @Expose
    private String totalQuestions;

    public TestSectionInst(String id, String sectionId, String sectionPart, String sectionTiming, String name, String name2, String marksPerQuestion, String negativeMarks, String totalQuestions) {
        this.id = id;
        this.sectionId = sectionId;
        this.sectionPart = sectionPart;
        this.sectionTiming = sectionTiming;
        this.name = name;
        this.name2 = name2;
        this.marksPerQuestion = marksPerQuestion;
        this.negativeMarks = negativeMarks;
        this.totalQuestions = totalQuestions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionPart() {
        return sectionPart;
    }

    public void setSectionPart(String sectionPart) {
        this.sectionPart = sectionPart;
    }

    public String getSectionTiming() {
        return sectionTiming;
    }

    public void setSectionTiming(String sectionTiming) {
        this.sectionTiming = sectionTiming;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getMarksPerQuestion() {
        return marksPerQuestion;
    }

    public void setMarksPerQuestion(String marksPerQuestion) {
        this.marksPerQuestion = marksPerQuestion;
    }

    public String getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(String negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

}
