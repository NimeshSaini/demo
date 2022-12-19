package com.utkarshnew.android.pojo.Userinfo.StatesCities;

import java.util.List;

public class StatesCities {
    private List<StatesCitiesData> data;
    private String message;
    private boolean status;

    public List<StatesCitiesData> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setData(List<StatesCitiesData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}