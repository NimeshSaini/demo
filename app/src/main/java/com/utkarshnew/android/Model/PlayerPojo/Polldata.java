package com.utkarshnew.android.Model.PlayerPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Polldata {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option_1")
    @Expose
    private String option1;
    @SerializedName("option_2")
    @Expose
    private String option2;
    @SerializedName("option_3")
    @Expose
    private String option3;
    @SerializedName("option_4")
    @Expose
    private String option4;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("attempt_1")
    @Expose
    private Long attempt1;
    @SerializedName("attempt_2")
    @Expose
    private Long attempt2;
    @SerializedName("attempt_3")
    @Expose
    private Long attempt3;
    @SerializedName("attempt_4")
    @Expose
    private Long attempt4;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("my_answer")
    @Expose
    private String myAnswer;

    public String getRendomkey() {
        return rendomkey;
    }

    public void setRendomkey(String rendomkey) {
        this.rendomkey = rendomkey;
    }

    @SerializedName("firebase_key")
    @Expose
    private String rendomkey;

    public String getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(String isvisible) {
        this.isvisible = isvisible;
    }

    @SerializedName("isvisible")
    @Expose
    private String isvisible;

    public String getDisable_result() {
        return disable_result;
    }

    public void setDisable_result(String disable_result) {
        this.disable_result = disable_result;
    }

    @SerializedName("disable_result")
    @Expose
    private String disable_result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getAttempt1() {
        return attempt1;
    }

    public void setAttempt1(Long attempt1) {
        this.attempt1 = attempt1;
    }

    public Long getAttempt2() {
        return attempt2;
    }

    public void setAttempt2(Long attempt2) {
        this.attempt2 = attempt2;
    }

    public Long getAttempt3() {
        return attempt3;
    }

    public void setAttempt3(Long attempt3) {
        this.attempt3 = attempt3;
    }

    public Long getAttempt4() {
        return attempt4;
    }

    public void setAttempt4(Long attempt4) {
        this.attempt4 = attempt4;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }
}