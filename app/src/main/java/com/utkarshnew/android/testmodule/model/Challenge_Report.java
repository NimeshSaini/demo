package com.utkarshnew.android.testmodule.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Challenge_Report implements Serializable {

    String status;
    private String message;

    public ArrayList<QuestionDumps> getData() {
        return data;
    }

    public void setData(ArrayList<QuestionDumps> data) {
        this.data = data;
    }

    private ArrayList<QuestionDumps> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getIs_android_price() {
        return is_android_price;
    }

    public void setIs_android_price(String is_android_price) {
        this.is_android_price = is_android_price;
    }

    public ArrayList<Errors> getError() {
        return error;
    }

    public void setError(ArrayList<Errors> error) {
        this.error = error;
    }


    private String is_android_price;
    private ArrayList<Errors> error;


    public class Errors implements Serializable {

    }


}
