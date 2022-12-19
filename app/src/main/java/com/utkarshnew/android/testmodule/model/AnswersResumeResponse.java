package com.utkarshnew.android.testmodule.model;

import java.io.Serializable;
import java.util.ArrayList;

public class AnswersResumeResponse implements Serializable {
    String config_id;
    String index;
    String is_bookmarked;
    String on_screen;
    String section_id;

    public String getConfig_id() {
        return config_id;
    }

    public void setConfig_id(String config_id) {
        this.config_id = config_id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(String is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    public String getOn_screen() {
        return on_screen;
    }

    public void setOn_screen(String on_screen) {
        this.on_screen = on_screen;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(int is_correct) {
        this.is_correct = is_correct;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    String state;
    int is_correct;
    ArrayList<String> answers;
}
