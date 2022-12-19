package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestBasicInst implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_series_name")
    @Expose
    private String testSeriesName;
    @SerializedName("total_questions")
    @Expose
    private String totalQuestions;

    public String getOptional_que() {
        return optional_que;
    }

    public void setOptional_que(String optional_que) {
        this.optional_que = optional_que;
    }

    @SerializedName("optional_que")
    @Expose
    private String optional_que;
    @SerializedName("time_in_mins")
    @Expose
    private String timeInMins;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("watermark")
    @Expose
    private String watermark;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lang_id")
    @Expose
    private String lang_id;

    public Test_assets getTest_assets() {
        return test_assets;
    }

    public void setTest_assets(Test_assets test_assets) {
        this.test_assets = test_assets;
    }

    @SerializedName("test_assets")
    @Expose
    private Test_assets test_assets;

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @SerializedName("instruction")
    @Expose
    private String instruction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestSeriesName() {
        return testSeriesName;
    }

    public void setTestSeriesName(String testSeriesName) {
        this.testSeriesName = testSeriesName;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTimeInMins() {
        return timeInMins;
    }

    public void setTimeInMins(String timeInMins) {
        this.timeInMins = timeInMins;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLang_id() {
        return lang_id;
    }

    public void setLang_id(String lang_id) {
        this.lang_id = lang_id;
    }

    public static class Test_assets implements Serializable {
        public String getHide_inst_time() {
            return hide_inst_time;
        }

        public void setHide_inst_time(String hide_inst_time) {
            this.hide_inst_time = hide_inst_time;
        }

        public String getDisable_sec_click() {
            return disable_sec_click;
        }

        public void setDisable_sec_click(String disable_sec_click) {
            this.disable_sec_click = disable_sec_click;
        }

        @SerializedName("hide_inst_time")
        @Expose
        private String hide_inst_time;

        @SerializedName("disable_sec_click")
        @Expose
        private String disable_sec_click;
    }
}