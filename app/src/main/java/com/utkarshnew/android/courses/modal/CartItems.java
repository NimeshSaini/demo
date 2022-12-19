package com.utkarshnew.android.courses.modal;

import java.io.Serializable;

/**
 * Created by Cbc-03 on 10/14/17.
 */

public class CartItems implements Serializable {

    private String user_id;
    private String course_id;

    public CartItems(String user_id, String course_id) {
        this.user_id = user_id;
        this.course_id = course_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}

