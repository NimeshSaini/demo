package com.utkarshnew.android.testmodule.model;

import java.io.Serializable;

public class ResumeDump implements Serializable {

    private String id;
    private String course_id;
    private String test_series_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getTest_series_id() {
        return test_series_id;
    }

    public void setTest_series_id(String test_series_id) {
        this.test_series_id = test_series_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult_cut_off() {
        return result_cut_off;
    }

    public void setResult_cut_off(String result_cut_off) {
        this.result_cut_off = result_cut_off;
    }

    public String getCut_off() {
        return cut_off;
    }

    public void setCut_off(String cut_off) {
        this.cut_off = cut_off;
    }

    public String getQuestion_dump() {
        return question_dump;
    }

    public void setQuestion_dump(String question_dump) {
        this.question_dump = question_dump;
    }

    public String getResume_dump() {
        return resume_dump;
    }

    public void setResume_dump(String resume_dump) {
        this.resume_dump = resume_dump;
    }

    public String getTime_remain() {
        return time_remain;
    }

    public void setTime_remain(String time_remain) {
        this.time_remain = time_remain;
    }

    public String getCorrect_count() {
        return correct_count;
    }

    public void setCorrect_count(String correct_count) {
        this.correct_count = correct_count;
    }

    public String getIncorrect_count() {
        return incorrect_count;
    }

    public void setIncorrect_count(String incorrect_count) {
        this.incorrect_count = incorrect_count;
    }

    public String getNon_attempt() {
        return non_attempt;
    }

    public void setNon_attempt(String non_attempt) {
        this.non_attempt = non_attempt;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getTest_series_marks() {
        return test_series_marks;
    }

    public void setTest_series_marks(String test_series_marks) {
        this.test_series_marks = test_series_marks;
    }

    public String getTotal_test_series_time() {
        return total_test_series_time;
    }

    public void setTotal_test_series_time(String total_test_series_time) {
        this.total_test_series_time = total_test_series_time;
    }

    public String getReward_points() {
        return reward_points;
    }

    public void setReward_points(String reward_points) {
        this.reward_points = reward_points;
    }

    public String getFirst_attempt() {
        return first_attempt;
    }

    public void setFirst_attempt(String first_attempt) {
        this.first_attempt = first_attempt;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReport_origin() {
        return report_origin;
    }

    public void setReport_origin(String report_origin) {
        this.report_origin = report_origin;
    }

    public String getLast_view() {
        return last_view;
    }

    public void setLast_view(String last_view) {
        this.last_view = last_view;
    }

    public String getLang_used() {
        return lang_used;
    }

    public void setLang_used(String lang_used) {
        this.lang_used = lang_used;
    }

    private String user_id;
    private String result;
    private String result_cut_off;
    private String cut_off;
    private String question_dump;
    private String resume_dump;
    private String time_remain;
    private String correct_count;
    private String incorrect_count;
    private String non_attempt;
    private String marks;
    private String test_series_marks;
    private String total_test_series_time;
    private String reward_points;
    private String first_attempt;
    private String creation_time;
    private String state;
    private String report_origin;
    private String last_view;
    private String lang_used;


}
