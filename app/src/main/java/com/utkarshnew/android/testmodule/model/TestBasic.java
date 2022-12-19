
package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestBasic implements Serializable {


    @SerializedName("is_calc_allowed")
    @Expose
    private String is_calc_allowed;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_series_name")
    @Expose
    private String testSeriesName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("test_type")
    @Expose
    private String testType;
    @SerializedName("total_questions")
    @Expose
    private String totalQuestions;

    private String allow_user_move;

    public String getAllow_user_move() {
        return allow_user_move;
    }

    public void setAllow_user_move(String allow_user_move) {
        this.allow_user_move = allow_user_move;
    }

    public String getIs_calc_allowed() {
        return is_calc_allowed;
    }

    public void setIs_calc_allowed(String is_calc_allowed) {
        this.is_calc_allowed = is_calc_allowed;
    }

    @SerializedName("consider_time")
    @Expose
    private String considerTime;
    @SerializedName("time_in_mins")
    @Expose
    private String timeInMins;
    @SerializedName("total_marks")
    @Expose
    private String totalMarks;
    @SerializedName("shuffle")
    @Expose
    private String shuffle;
    @SerializedName("answer_shuffle")
    @Expose
    private String answerShuffle;
    @SerializedName("time_boundation")
    @Expose
    private String timeBoundation;
    @SerializedName("pass_message")
    @Expose
    private String passMessage;
    @SerializedName("general_message")
    @Expose
    private String generalMessage;
    @SerializedName("fail_message")
    @Expose
    private String failMessage;
    @SerializedName("pass_percentage")
    @Expose
    private String passPercentage;
    @SerializedName("reward_points")
    @Expose
    private String rewardPoints;
    @SerializedName("set_type")
    @Expose
    private String setType;
    @SerializedName("lang_id")
    @Expose
    private List<String> langId = null;

    @SerializedName("watermark")
    @Expose
    private String watermark;
    private long timeRemaining = 0;
    private String display_qid;

    public String getDisplay_qid() {
        return display_qid;
    }

    public void setDisplay_qid(String display_qid) {
        this.display_qid = display_qid;
    }

    public Test_assets getTest_assets() {
        return test_assets;
    }

    public void setTest_assets(Test_assets test_assets) {
        this.test_assets = test_assets;
    }

    @SerializedName("test_assets")
    @Expose
    private Test_assets test_assets;

    private String display_bubble;

    public long getTimeRemaining() {
        return timeRemaining;
    }


    public String getDisplay_bubble() {
        return display_bubble;
    }

    public void setDisplay_bubble(String display_bubble) {
        this.display_bubble = display_bubble;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getConsiderTime() {
        return considerTime;
    }

    public void setConsiderTime(String considerTime) {
        this.considerTime = considerTime;
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

    public String getShuffle() {
        return shuffle;
    }

    public void setShuffle(String shuffle) {
        this.shuffle = shuffle;
    }

    public String getAnswerShuffle() {
        return answerShuffle;
    }

    public void setAnswerShuffle(String answerShuffle) {
        this.answerShuffle = answerShuffle;
    }

    public String getTimeBoundation() {
        return timeBoundation;
    }

    public void setTimeBoundation(String timeBoundation) {
        this.timeBoundation = timeBoundation;
    }

    public String getPassMessage() {
        return passMessage;
    }

    public void setPassMessage(String passMessage) {
        this.passMessage = passMessage;
    }

    public String getGeneralMessage() {
        return generalMessage;
    }

    public void setGeneralMessage(String generalMessage) {
        this.generalMessage = generalMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(String passPercentage) {
        this.passPercentage = passPercentage;
    }

    public String getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(String rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public List<String> getLangId() {
        return langId;
    }

    public void setLangId(List<String> langId) {
        this.langId = langId;
    }


    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
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