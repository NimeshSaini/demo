package com.utkarshnew.android.Model.Courses;

import com.utkarshnew.android.Model.Course_subject_master;

import java.io.Serializable;
import java.util.ArrayList;

public class SinglestudyModel implements Serializable {

    private ArrayList<Cards> tiles;

    private Basic basic;

    private String is_purchased;

    public SinglestudyModel() {
    }

    public SinglestudyModel(Basic basic) {
        this.basic = basic;
    }

    public SinglestudyModel(ArrayList<Cards> tiles, Basic basic, String is_purchased, ArrayList<Course_subject_master> subjects) {
        this.tiles = tiles;
        this.basic = basic;
        this.is_purchased = is_purchased;
        this.subjects = subjects;
    }

    private ArrayList<Course_subject_master> subjects;

    public ArrayList<Course_subject_master> getSubjects() {
        return subjects;
    }

    public String getIs_purchased() {
        return is_purchased;
    }

    public void setIs_purchased(String is_purchased) {
        this.is_purchased = is_purchased;
    }

    public ArrayList<Cards> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Cards> tiles) {
        this.tiles = tiles;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

}
