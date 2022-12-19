
package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestSection implements Serializable {
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

    public String getOptional_que() {
        return optional_que;
    }

    public void setOptional_que(String optional_que) {
        this.optional_que = optional_que;
    }

    @SerializedName("optional_que")
    @Expose
    private String optional_que;

    private  int limit_count=0;

    @SerializedName("name")
    @Expose
    private String name;

    public String getNo_of_questions() {
        return no_of_questions;
    }

    public void setNo_of_questions(String no_of_questions) {
        this.no_of_questions = no_of_questions;
    }

    @SerializedName("no_of_questions")
    @Expose
    private String no_of_questions;
    @SerializedName("name_2")
    @Expose
    private String name2;
    @SerializedName("marks_per_question")
    @Expose
    private String marksPerQuestion;

    public String getSection_title() {
        return section_title;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    @SerializedName("section_title")
    @Expose
    private String section_title;
    @SerializedName("is_partial_marking")
    @Expose
    private String isPartialMarking;
    @SerializedName("negative_marks")
    @Expose
    private String negativeMarks;
    @SerializedName("section_cutoff")
    @Expose
    private String sectionCutoff;

    public int getIndexOf() {
        return indexOf;
    }

    public void setIndexOf(int indexOf) {
        this.indexOf = indexOf;
    }

    private int indexOf;

    public void setIndex(int indexOf) {
        this.indexOf = indexOf;
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

    public String getIsPartialMarking() {
        return isPartialMarking;
    }

    public void setIsPartialMarking(String isPartialMarking) {
        this.isPartialMarking = isPartialMarking;
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

}