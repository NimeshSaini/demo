package com.utkarshnew.android.Model.Courses.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Cbc-03 on 11/01/17.
 */

public class Quiz_Basic_Info implements Serializable {


    @SerializedName("pass_percentage")
    @Expose
    private String pass_percentage;

    @SerializedName("test_series_name")
    @Expose
    private String test_series_name;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("negative_marking")
    @Expose
    private String negative_marking;

    @SerializedName("test_type")
    @Expose
    private String test_type;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("end_date")
    @Expose
    private String end_date;

    @SerializedName("allow_duplicate_rank")
    @Expose
    private String allow_duplicate_rank;

    @SerializedName("difficulty_level")
    @Expose
    private String difficulty_level;

    @SerializedName("marks_per_question")
    @Expose
    private String marks_per_question;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("description_2")
    @Expose
    private String description_2;

    @SerializedName("test_code")
    @Expose
    private String test_code;

    @SerializedName("skip_rank")
    @Expose
    private String skip_rank;

    @SerializedName("show_question_time")
    @Expose
    private String show_question_time;

    @SerializedName("time_in_mins")
    @Expose
    private String time_in_mins;

    @SerializedName("test_price")
    @Expose
    private String test_price;

    @SerializedName("mandatory_check")
    @Expose
    private String mandatory_check;

    @SerializedName("general_message")
    @Expose
    private String general_message;

    @SerializedName("fail_message")
    @Expose
    private String fail_message;

    @SerializedName("answer_shuffle")
    @Expose
    private String answer_shuffle;

    @SerializedName("time_boundation")
    @Expose
    private String time_boundation;

    @SerializedName("consider_time")
    @Expose
    private String consider_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("session")
    @Expose
    private String session;

    @SerializedName("pass_message")
    @Expose
    private String pass_message;

    @SerializedName("shuffle")
    @Expose
    private String shuffle;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("allow_user_move")
    @Expose
    private String allow_user_move;

    @SerializedName("start_date")
    @Expose
    private String start_date;

    @SerializedName("total_questions")
    @Expose
    private String total_questions;

    @SerializedName("total_marks")
    @Expose
    private String total_marks;

    @SerializedName("lang_id")
    @Expose
    private String lang_id;

    @SerializedName("no_of_subjects")
    @Expose
    private String no_of_subjects;

    @SerializedName("is_locked")
    @Expose
    private String is_locked;

    @SerializedName("test_segment_id")
    @Expose
    private String test_segment_id;

    @SerializedName("marks")
    @Expose
    private String marks;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("set_type")
    @Expose
    private String set_type;

    @SerializedName("correct_count")
    @Expose
    private String correctCount;

    @SerializedName("incorrect_count")
    @Expose
    private String incorrectCount;

    @SerializedName("lang_used")
    @Expose
    private String langUsed;

    @SerializedName("report_id")
    @Expose
    private String report_id;

    public String getSet_type() {
        return set_type;
    }

    public void setSet_type(String set_type) {
        this.set_type = set_type;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    public String getNo_of_subjects() {
        return no_of_subjects;
    }

    public void setNo_of_subjects(String no_of_subjects) {
        this.no_of_subjects = no_of_subjects;
    }

    public String getLang_id() {

        return lang_id;
    }

    public void setLang_id(String lang_id) {
        this.lang_id = lang_id;
    }

    public String getPass_percentage() {
        return pass_percentage;
    }

    public void setPass_percentage(String pass_percentage) {
        this.pass_percentage = pass_percentage;
    }

    public String getTest_series_name() {
        return test_series_name;
    }

    public void setTest_series_name(String test_series_name) {
        this.test_series_name = test_series_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNegative_marking() {
        return negative_marking;
    }

    public void setNegative_marking(String negative_marking) {
        this.negative_marking = negative_marking;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getAllow_duplicate_rank() {
        return allow_duplicate_rank;
    }

    public void setAllow_duplicate_rank(String allow_duplicate_rank) {
        this.allow_duplicate_rank = allow_duplicate_rank;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getMarks_per_question() {
        return marks_per_question;
    }

    public void setMarks_per_question(String marks_per_question) {
        this.marks_per_question = marks_per_question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_2() {
        return description_2;
    }

    public void setDescription_2(String description_2) {
        this.description_2 = description_2;
    }

    public String getTest_code() {
        return test_code;
    }

    public void setTest_code(String test_code) {
        this.test_code = test_code;
    }

    public String getSkip_rank() {
        return skip_rank;
    }

    public void setSkip_rank(String skip_rank) {
        this.skip_rank = skip_rank;
    }

    public String getShow_question_time() {
        return show_question_time;
    }

    public void setShow_question_time(String show_question_time) {
        this.show_question_time = show_question_time;
    }

    public String getTime_in_mins() {
        return time_in_mins;
    }

    public void setTime_in_mins(String time_in_mins) {
        this.time_in_mins = time_in_mins;
    }

    public String getTest_price() {
        return test_price;
    }

    public void setTest_price(String test_price) {
        this.test_price = test_price;
    }

    public String getMandatory_check() {
        return mandatory_check;
    }

    public void setMandatory_check(String mandatory_check) {
        this.mandatory_check = mandatory_check;
    }

    public String getGeneral_message() {
        return general_message;
    }

    public void setGeneral_message(String general_message) {
        this.general_message = general_message;
    }

    public String getFail_message() {
        return fail_message;
    }

    public void setFail_message(String fail_message) {
        this.fail_message = fail_message;
    }

    public String getAnswer_shuffle() {
        return answer_shuffle;
    }

    public void setAnswer_shuffle(String answer_shuffle) {
        this.answer_shuffle = answer_shuffle;
    }

    public String getTime_boundation() {
        return time_boundation;
    }

    public void setTime_boundation(String time_boundation) {
        this.time_boundation = time_boundation;
    }

    public String getConsider_time() {
        return consider_time;
    }

    public void setConsider_time(String consider_time) {
        this.consider_time = consider_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getPass_message() {
        return pass_message;
    }

    public void setPass_message(String pass_message) {
        this.pass_message = pass_message;
    }

    public String getShuffle() {
        return shuffle;
    }

    public void setShuffle(String shuffle) {
        this.shuffle = shuffle;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getAllow_user_move() {
        return allow_user_move;
    }

    public void setAllow_user_move(String allow_user_move) {
        this.allow_user_move = allow_user_move;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTotal_questions() {
        return total_questions;
    }

    public void setTotal_questions(String total_questions) {
        this.total_questions = total_questions;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }

    public String getTest_segment_id() {
        return test_segment_id;
    }

    public void setTest_segment_id(String test_segment_id) {
        this.test_segment_id = test_segment_id;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(String correctCount) {
        this.correctCount = correctCount;
    }

    public String getIncorrectCount() {
        return incorrectCount;
    }

    public void setIncorrectCount(String incorrectCount) {
        this.incorrectCount = incorrectCount;
    }

    public String getLangUsed() {
        return langUsed;
    }

    public void setLangUsed(String langUsed) {
        this.langUsed = langUsed;
    }
}