package com.utkarshnew.android.testmodule.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Answers implements Serializable {
    String config_id;
    String section_id;
    String index;

    public String getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(String is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    String is_bookmarked = "0";

    public String getConfig_id() {
        return config_id;
    }

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList answers) {
        this.answers = answers;
    }

    public String getOn_screen() {
        return on_screen;
    }

    public void setOn_screen(String on_screen) {
        this.on_screen = on_screen;
    }

    String state;
    ArrayList<String> answers;
    private String answer = "1";
    String on_screen;
    String user_answer;

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
