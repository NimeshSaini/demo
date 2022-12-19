package com.utkarshnew.android.Model;

import java.io.Serializable;

public class Course_subject_master implements Serializable {
    private String id;

    private String name;

    private String image;

    private String color_code;
    private String is_live;

    public String getColor() {
        return color_code;
    }

    public void setColor(String color) {
        this.color_code = color;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", name = " + name + ", image = " + image + "]";
    }
}