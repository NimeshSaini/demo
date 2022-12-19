package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestSections implements Serializable {

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

    public String getSection_title() {
        return section_title;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    @SerializedName("section_title")
    @Expose
    private String section_title;

    @SerializedName("marks_per_question")
    @Expose
    private String marksPerQuestion;
    @SerializedName("is_partial_marking")
    @Expose
    private String isPartialMarking;
    @SerializedName("no_of_questions")
    @Expose
    private String noOfQuestions;
    @SerializedName("negative_marks")
    @Expose
    private String negativeMarks;
    @SerializedName("section_cutoff")
    @Expose
    private String sectionCutoff;

    public String getUserMarks() {
        return userMarks;
    }

    public void setUserMarks(String userMarks) {
        this.userMarks = userMarks;
    }

    /* public float getUserMarks() {
            return userMarks;
        }

        public void setUserMarks(float userMarks) {
            this.userMarks = userMarks;
        }
    */
    @SerializedName("user_marks")
    @Expose
    private String userMarks;
    @SerializedName("right_answered")
    @Expose
    private Integer rightAnswered;
    @SerializedName("total_attempt")
    @Expose
    private Integer totalAttempt;
    @SerializedName("time_spent")
    @Expose
    private Integer timeSpent;
    @SerializedName("accuracy")
    @Expose
    private Double accuracy;

    public boolean isSelected_section() {
        return selected_section;
    }

    public void setSelected_section(boolean selected_section) {
        this.selected_section = selected_section;
    }

    private boolean selected_section =false;

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


    public String getMarksPerQuestion() {
        return marksPerQuestion;
    }

    public void setMarksPerQuestion(String marksPerQuestion) {
        this.marksPerQuestion = marksPerQuestion;
    }

    public String getIsPartialMarking() {
        return isPartialMarking;
    }

    public void setIsPartialMarking(String isPartialMarking) {
        this.isPartialMarking = isPartialMarking;
    }

    public String getNoOfQuestions() {
        return noOfQuestions;
    }
    public String getOptional_que() {
        return optional_que;
    }

    public void setOptional_que(String optional_que) {
        this.optional_que = optional_que;
    }

    @SerializedName("optional_que")
    @Expose
    private String optional_que;



    public void setNoOfQuestions(String noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public String getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(String negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public String getSectionCutoff() {
        return sectionCutoff;
    }

    public void setSectionCutoff(String sectionCutoff) {
        this.sectionCutoff = sectionCutoff;
    }


    public Integer getRightAnswered() {
        return rightAnswered;
    }

    public void setRightAnswered(Integer rightAnswered) {
        this.rightAnswered = rightAnswered;
    }

    public Integer getTotalAttempt() {
        return totalAttempt;
    }

    public void setTotalAttempt(Integer totalAttempt) {
        this.totalAttempt = totalAttempt;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

}