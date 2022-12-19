package com.utkarshnew.android.Model.COURSEDETAIL;

import java.io.Serializable;

public class CourseDetail implements Serializable {
    private Data data;
    private String message;
    private boolean status;


    public void setData(Data data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }


}
