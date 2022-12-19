package com.utkarshnew.android.Model.Courses;

import java.io.Serializable;

public class PopupCourse implements Serializable {

    private String id;
    private String course;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
