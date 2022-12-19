package com.utkarshnew.android.home.model;

import android.os.Parcel;

import java.io.Serializable;

public class CategoryLeft implements Serializable {
    private String id;
    private String name;

    public CategoryLeft(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected CategoryLeft(Parcel in) {
        id = in.readString();
        name = in.readString();
    }
}
