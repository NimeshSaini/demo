package com.utkarshnew.android.CreateTest.Model;

import java.io.Serializable;

public class QuestionCount implements Serializable {
    private String easy;
    private String medium;
    private String hard;

    public String getEasy() {
        return easy;
    }

    public void setEasy(String easy) {
        this.easy = easy;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getHard() {
        return hard;
    }

    public void setHard(String hard) {
        this.hard = hard;
    }
}