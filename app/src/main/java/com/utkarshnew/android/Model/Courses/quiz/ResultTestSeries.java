package com.utkarshnew.android.Model.Courses.quiz;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultTestSeries implements Serializable {
    private String result;

    private String non_attempt;

    private String marks;

    private String total_user_attempt;

    private String total_test_series_time;

    private String id;

    private String incorrect_count;

    private String question_dump;

    private String test_series_id;

    private String time_spent;

    private String user_rank;

    private String skip_rank;

    private String image;

    private String reward_points;

    private String creation_time;

    private String user_id;

    private String test_type;

    private String test_series_name;

    private String correct_count;

    private String test_series_marks;

    private ArrayList<LeaderBoardUserModel> top_ten_list;

    private ArrayList<LeaderBoardUserModel> top_list;

    public ArrayList<LeaderBoardUserModel> getTop_list() {
        return top_list;
    }

    public void setTop_list(ArrayList<LeaderBoardUserModel> top_list) {
        this.top_list = top_list;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSkip_rank() {
        return skip_rank;
    }

    public void setSkip_rank(String skip_rank) {
        this.skip_rank = skip_rank;
    }

    public ArrayList<LeaderBoardUserModel> getTop_ten_list() {
        return top_ten_list;
    }

    public void setTop_ten_list(ArrayList<LeaderBoardUserModel> top_ten_list) {
        this.top_ten_list = top_ten_list;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getTest_series_name() {
        return test_series_name;
    }

    public void setTest_series_name(String test_series_name) {
        this.test_series_name = test_series_name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getTotal_user_attempt() {
        return total_user_attempt;
    }

    public void setTotal_user_attempt(String total_user_attempt) {
        this.total_user_attempt = total_user_attempt;
    }

    public String getTotal_test_series_time() {
        return total_test_series_time;
    }

    public void setTotal_test_series_time(String total_test_series_time) {
        this.total_test_series_time = total_test_series_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncorrect_count() {
        return incorrect_count;
    }

    public void setIncorrect_count(String incorrect_count) {
        this.incorrect_count = incorrect_count;
    }

    public String getQuestion_dump() {
        return question_dump;
    }

    public void setQuestion_dump(String question_dump) {
        this.question_dump = question_dump;
    }

    public String getTest_series_id() {
        return test_series_id;
    }

    public void setTest_series_id(String test_series_id) {
        this.test_series_id = test_series_id;
    }

    public String getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(String time_spent) {
        this.time_spent = time_spent;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public String getReward_points() {
        return reward_points;
    }

    public void setReward_points(String reward_points) {
        this.reward_points = reward_points;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCorrect_count() {
        return correct_count;
    }

    public void setCorrect_count(String correct_count) {
        this.correct_count = correct_count;
    }

    public String getTest_series_marks() {
        return test_series_marks;
    }

    public void setTest_series_marks(String test_series_marks) {
        this.test_series_marks = test_series_marks;
    }
}
