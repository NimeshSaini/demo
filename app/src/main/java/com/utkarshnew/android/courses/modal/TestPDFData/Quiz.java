
package com.utkarshnew.android.courses.modal.TestPDFData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.Model.Courses.quiz.Quiz_Basic_Info;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("font_color")
    @Expose
    private String font_color;
    @SerializedName("bg_color")
    @Expose
    private String bg_color;
    @SerializedName("test_list")
    @Expose
    private ArrayList<Quiz_Basic_Info> testList = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFont_color() {
        return font_color;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public ArrayList<Quiz_Basic_Info> getTestList() {
        return testList;
    }

    public void setTestList(ArrayList<Quiz_Basic_Info> testList) {
        this.testList = testList;
    }

}
