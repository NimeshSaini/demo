package com.utkarshnew.android.CreateTest.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateTestData implements Serializable {

    private boolean status;
    private String message;
    private ArrayList<CreateTestSubject> data;

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

    public ArrayList<CreateTestSubject> getData() {
        return data;
    }

    public void setData(ArrayList<CreateTestSubject> data) {
        this.data = data;
    }
}