package com.utkarshnew.android.home.model.myNotesData;

import java.io.Serializable;

public class NoteTags implements Serializable {

    private String tag_name;

    public NoteTags(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}