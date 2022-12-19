package com.utkarshnew.android.Model.FAQs;

import java.util.ArrayList;
import java.util.List;

public class Faqs {
    private Boolean status;
    private String message;
    private ArrayList<FaqData> data = new ArrayList<FaqData>();

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FaqData> getData() {
        return data;
    }

    public void setData(ArrayList<FaqData> data) {
        this.data = data;
    }
}
