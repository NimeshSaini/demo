package com.utkarshnew.android.home.model;


import com.utkarshnew.android.Model.Courselist;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseResponse implements Serializable {


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Courselist> getData() {
        return data;
    }

    public void setData(ArrayList<Courselist> data) {
        this.data = data;
    }

    private boolean status;
    private String message;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private int limit;
    private ArrayList<Courselist> data = new ArrayList<>();


}
